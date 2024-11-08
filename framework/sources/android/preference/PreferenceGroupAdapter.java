package android.preference;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.preference.Preference;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Deprecated
/* loaded from: classes3.dex */
public class PreferenceGroupAdapter extends BaseAdapter implements Preference.OnPreferenceChangeInternalListener {
    private static final String TAG = "PreferenceGroupAdapter";
    private static ViewGroup.LayoutParams sWrapperLayoutParams = new ViewGroup.LayoutParams(-1, -2);
    private Drawable mHighlightedDrawable;
    private PreferenceGroup mPreferenceGroup;
    private ArrayList<PreferenceLayout> mPreferenceLayouts;
    private List<Preference> mPreferenceList;
    private PreferenceLayout mTempPreferenceLayout = new PreferenceLayout();
    private boolean mHasReturnedViewTypeCount = false;
    boolean mIsCategoryAfter = false;
    Preference mNextPreference = null;
    Preference mNextGroupPreference = null;
    private volatile boolean mIsSyncing = false;
    private Handler mHandler = new Handler();
    private Runnable mSyncRunnable = new Runnable() { // from class: android.preference.PreferenceGroupAdapter.1
        AnonymousClass1() {
        }

        @Override // java.lang.Runnable
        public void run() {
            PreferenceGroupAdapter.this.syncMyPreferences();
        }
    };
    private int mHighlightedPosition = -1;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: android.preference.PreferenceGroupAdapter$1 */
    /* loaded from: classes3.dex */
    public class AnonymousClass1 implements Runnable {
        AnonymousClass1() {
        }

        @Override // java.lang.Runnable
        public void run() {
            PreferenceGroupAdapter.this.syncMyPreferences();
        }
    }

    /* loaded from: classes3.dex */
    public static class PreferenceLayout implements Comparable<PreferenceLayout> {
        private String name;
        private int resId;
        private int widgetResId;

        /* synthetic */ PreferenceLayout(PreferenceLayoutIA preferenceLayoutIA) {
            this();
        }

        private PreferenceLayout() {
        }

        @Override // java.lang.Comparable
        public int compareTo(PreferenceLayout other) {
            int compareNames = this.name.compareTo(other.name);
            if (compareNames == 0) {
                int i = this.resId;
                int i2 = other.resId;
                if (i == i2) {
                    int i3 = this.widgetResId;
                    int i4 = other.widgetResId;
                    if (i3 == i4) {
                        return 0;
                    }
                    return i3 - i4;
                }
                return i - i2;
            }
            return compareNames;
        }
    }

    public PreferenceGroupAdapter(PreferenceGroup preferenceGroup) {
        this.mPreferenceGroup = preferenceGroup;
        preferenceGroup.setOnPreferenceChangeInternalListener(this);
        this.mPreferenceList = new ArrayList();
        this.mPreferenceLayouts = new ArrayList<>();
        syncMyPreferences();
    }

    public void syncMyPreferences() {
        synchronized (this) {
            if (this.mIsSyncing) {
                return;
            }
            this.mIsSyncing = true;
            this.mIsCategoryAfter = false;
            this.mNextPreference = null;
            this.mNextGroupPreference = null;
            List<Preference> newPreferenceList = new ArrayList<>(this.mPreferenceList.size());
            flattenPreferenceGroup(newPreferenceList, this.mPreferenceGroup);
            this.mPreferenceList = newPreferenceList;
            notifyDataSetChanged();
            synchronized (this) {
                this.mIsSyncing = false;
                notifyAll();
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:27:0x0051, code lost:
    
        if (r6 != null) goto L104;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void flattenPreferenceGroup(java.util.List<android.preference.Preference> r9, android.preference.PreferenceGroup r10) {
        /*
            Method dump skipped, instructions count: 220
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.preference.PreferenceGroupAdapter.flattenPreferenceGroup(java.util.List, android.preference.PreferenceGroup):void");
    }

    private PreferenceLayout createPreferenceLayout(Preference preference, PreferenceLayout in) {
        PreferenceLayout pl = in != null ? in : new PreferenceLayout();
        pl.name = preference.getClass().getName();
        pl.resId = preference.getLayoutResource();
        pl.widgetResId = preference.getWidgetLayoutResource();
        return pl;
    }

    private void addPreferenceClassName(Preference preference) {
        PreferenceLayout pl = createPreferenceLayout(preference, null);
        int insertPos = Collections.binarySearch(this.mPreferenceLayouts, pl);
        if (insertPos < 0) {
            this.mPreferenceLayouts.add((insertPos * (-1)) - 1, pl);
        }
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.mPreferenceList.size();
    }

    @Override // android.widget.Adapter
    public Preference getItem(int position) {
        if (position < 0 || position >= getCount()) {
            return null;
        }
        return this.mPreferenceList.get(position);
    }

    @Override // android.widget.Adapter
    public long getItemId(int position) {
        if (position < 0 || position >= getCount()) {
            return Long.MIN_VALUE;
        }
        return getItem(position).getId();
    }

    public void setHighlighted(int position) {
        this.mHighlightedPosition = position;
    }

    public void setHighlightedDrawable(Drawable drawable) {
        this.mHighlightedDrawable = drawable;
    }

    @Override // android.widget.Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        Preference preference = getItem(position);
        PreferenceLayout createPreferenceLayout = createPreferenceLayout(preference, this.mTempPreferenceLayout);
        this.mTempPreferenceLayout = createPreferenceLayout;
        if (Collections.binarySearch(this.mPreferenceLayouts, createPreferenceLayout) < 0 || getItemViewType(position) == getHighlightItemViewType()) {
            convertView = null;
        }
        View result = preference.getView(convertView, parent);
        if (position == this.mHighlightedPosition && this.mHighlightedDrawable != null) {
            ViewGroup wrapper = new FrameLayout(parent.getContext());
            wrapper.setLayoutParams(sWrapperLayoutParams);
            wrapper.setBackgroundDrawable(this.mHighlightedDrawable);
            wrapper.addView(result);
            return wrapper;
        }
        return result;
    }

    @Override // android.widget.BaseAdapter, android.widget.ListAdapter
    public boolean isEnabled(int position) {
        if (position < 0 || position >= getCount()) {
            return true;
        }
        return getItem(position).isSelectable();
    }

    @Override // android.widget.BaseAdapter, android.widget.ListAdapter
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override // android.preference.Preference.OnPreferenceChangeInternalListener
    public void onPreferenceChange(Preference preference) {
        notifyDataSetChanged();
    }

    @Override // android.preference.Preference.OnPreferenceChangeInternalListener
    public void onPreferenceHierarchyChange(Preference preference) {
        this.mHandler.removeCallbacks(this.mSyncRunnable);
        this.mHandler.post(this.mSyncRunnable);
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public boolean hasStableIds() {
        return true;
    }

    private int getHighlightItemViewType() {
        return getViewTypeCount() - 1;
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public int getItemViewType(int position) {
        if (position == this.mHighlightedPosition) {
            return getHighlightItemViewType();
        }
        if (!this.mHasReturnedViewTypeCount) {
            this.mHasReturnedViewTypeCount = true;
        }
        Preference preference = getItem(position);
        if (!preference.isRecycleEnabled()) {
            return -1;
        }
        PreferenceLayout createPreferenceLayout = createPreferenceLayout(preference, this.mTempPreferenceLayout);
        this.mTempPreferenceLayout = createPreferenceLayout;
        int viewType = Collections.binarySearch(this.mPreferenceLayouts, createPreferenceLayout);
        if (viewType < 0) {
            return -1;
        }
        return viewType;
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public int getViewTypeCount() {
        if (!this.mHasReturnedViewTypeCount) {
            this.mHasReturnedViewTypeCount = true;
        }
        return Math.max(1, this.mPreferenceLayouts.size()) + 1;
    }
}
