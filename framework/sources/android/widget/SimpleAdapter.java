package android.widget;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public class SimpleAdapter extends BaseAdapter implements Filterable, ThemedSpinnerAdapter {
    private List<? extends Map<String, ?>> mData;
    private LayoutInflater mDropDownInflater;
    private int mDropDownResource;
    private SimpleFilter mFilter;
    private String[] mFrom;
    private final LayoutInflater mInflater;
    private int mResource;
    private int[] mTo;
    private ArrayList<Map<String, ?>> mUnfilteredData;
    private ViewBinder mViewBinder;

    public interface ViewBinder {
        boolean setViewValue(View view, Object obj, String str);
    }

    public SimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        this.mData = data;
        this.mDropDownResource = resource;
        this.mResource = resource;
        this.mFrom = from;
        this.mTo = to;
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.mData.size();
    }

    @Override // android.widget.Adapter
    public Object getItem(int position) {
        return this.mData.get(position);
    }

    @Override // android.widget.Adapter
    public long getItemId(int position) {
        return position;
    }

    @Override // android.widget.Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        return createViewFromResource(this.mInflater, position, convertView, parent, this.mResource);
    }

    private View createViewFromResource(LayoutInflater inflater, int position, View convertView, ViewGroup parent, int resource) {
        View v;
        if (convertView == null) {
            v = inflater.inflate(resource, parent, false);
        } else {
            v = convertView;
        }
        bindView(position, v);
        return v;
    }

    public void setDropDownViewResource(int resource) {
        this.mDropDownResource = resource;
    }

    @Override // android.widget.ThemedSpinnerAdapter
    public void setDropDownViewTheme(Resources.Theme theme) {
        if (theme == null) {
            this.mDropDownInflater = null;
        } else if (theme == this.mInflater.getContext().getTheme()) {
            this.mDropDownInflater = this.mInflater;
        } else {
            Context context = new ContextThemeWrapper(this.mInflater.getContext(), theme);
            this.mDropDownInflater = LayoutInflater.from(context);
        }
    }

    @Override // android.widget.ThemedSpinnerAdapter
    public Resources.Theme getDropDownViewTheme() {
        if (this.mDropDownInflater == null) {
            return null;
        }
        return this.mDropDownInflater.getContext().getTheme();
    }

    @Override // android.widget.BaseAdapter, android.widget.SpinnerAdapter
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = this.mDropDownInflater == null ? this.mInflater : this.mDropDownInflater;
        return createViewFromResource(inflater, position, convertView, parent, this.mDropDownResource);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void bindView(int position, View view) {
        Map dataSet = this.mData.get(position);
        if (dataSet == null) {
            return;
        }
        ViewBinder viewBinder = this.mViewBinder;
        String[] from = this.mFrom;
        int[] to = this.mTo;
        int count = to.length;
        for (int i = 0; i < count; i++) {
            View findViewById = view.findViewById(to[i]);
            if (findViewById != 0) {
                Object data = dataSet.get(from[i]);
                String text = data == null ? "" : data.toString();
                if (text == null) {
                    text = "";
                }
                boolean bound = false;
                if (viewBinder != null) {
                    bound = viewBinder.setViewValue(findViewById, data, text);
                }
                if (bound) {
                    continue;
                } else if (findViewById instanceof Checkable) {
                    if (data instanceof Boolean) {
                        ((Checkable) findViewById).setChecked(((Boolean) data).booleanValue());
                    } else if (findViewById instanceof TextView) {
                        setViewText((TextView) findViewById, text);
                    } else {
                        throw new IllegalStateException(findViewById.getClass().getName() + " should be bound to a Boolean, not a " + (data == null ? "<unknown type>" : data.getClass()));
                    }
                } else if (findViewById instanceof TextView) {
                    setViewText((TextView) findViewById, text);
                } else if (findViewById instanceof ImageView) {
                    if (data instanceof Integer) {
                        setViewImage((ImageView) findViewById, ((Integer) data).intValue());
                    } else {
                        setViewImage((ImageView) findViewById, text);
                    }
                } else {
                    throw new IllegalStateException(findViewById.getClass().getName() + " is not a  view that can be bounds by this SimpleAdapter");
                }
            }
        }
    }

    public ViewBinder getViewBinder() {
        return this.mViewBinder;
    }

    public void setViewBinder(ViewBinder viewBinder) {
        this.mViewBinder = viewBinder;
    }

    public void setViewImage(ImageView v, int value) {
        v.setImageResource(value);
    }

    public void setViewImage(ImageView v, String value) {
        try {
            v.setImageResource(Integer.parseInt(value));
        } catch (NumberFormatException e) {
            v.setImageURI(Uri.parse(value));
        }
    }

    public void setViewText(TextView v, String text) {
        v.lambda$setTextAsync$0(text);
    }

    @Override // android.widget.Filterable
    public Filter getFilter() {
        if (this.mFilter == null) {
            this.mFilter = new SimpleFilter();
        }
        return this.mFilter;
    }

    private class SimpleFilter extends Filter {
        private SimpleFilter() {
        }

        @Override // android.widget.Filter
        protected Filter.FilterResults performFiltering(CharSequence prefix) {
            Filter.FilterResults results = new Filter.FilterResults();
            if (SimpleAdapter.this.mUnfilteredData == null) {
                SimpleAdapter.this.mUnfilteredData = new ArrayList(SimpleAdapter.this.mData);
            }
            if (prefix == null || prefix.length() == 0) {
                ArrayList<Map<String, ?>> list = SimpleAdapter.this.mUnfilteredData;
                results.values = list;
                results.count = list.size();
            } else {
                String prefixString = prefix.toString().toLowerCase();
                ArrayList<Map<String, ?>> unfilteredValues = SimpleAdapter.this.mUnfilteredData;
                int count = unfilteredValues.size();
                ArrayList<Map<String, ?>> newValues = new ArrayList<>(count);
                for (int i = 0; i < count; i++) {
                    Map<String, ?> h = unfilteredValues.get(i);
                    if (h != null) {
                        int len = SimpleAdapter.this.mTo.length;
                        for (int j = 0; j < len; j++) {
                            String str = (String) h.get(SimpleAdapter.this.mFrom[j]);
                            String[] words = str.split(" ");
                            int wordCount = words.length;
                            int k = 0;
                            while (true) {
                                if (k < wordCount) {
                                    String word = words[k];
                                    if (!word.toLowerCase().startsWith(prefixString)) {
                                        k++;
                                    } else {
                                        newValues.add(h);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
                results.values = newValues;
                results.count = newValues.size();
            }
            return results;
        }

        @Override // android.widget.Filter
        protected void publishResults(CharSequence constraint, Filter.FilterResults results) {
            SimpleAdapter.this.mData = (List) results.values;
            if (results.count > 0) {
                SimpleAdapter.this.notifyDataSetChanged();
            } else {
                SimpleAdapter.this.notifyDataSetInvalidated();
            }
        }
    }
}
