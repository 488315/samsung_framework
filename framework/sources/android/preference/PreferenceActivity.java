package android.preference;

import android.animation.LayoutTransition;
import android.app.Fragment;
import android.app.FragmentBreadCrumbs;
import android.app.FragmentTransaction;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.android.internal.R;
import java.util.ArrayList;
import java.util.List;

@Deprecated
/* loaded from: classes3.dex */
public abstract class PreferenceActivity extends ListActivity implements PreferenceManager.OnPreferenceTreeClickListener, PreferenceFragment.OnPreferenceStartFragmentCallback {
    private static final String BACK_STACK_PREFS = ":android:prefs";
    private static final String CUR_HEADER_TAG = ":android:cur_header";
    public static final String EXTRA_NO_HEADERS = ":android:no_headers";
    private static final String EXTRA_PREFS_SET_BACK_TEXT = "extra_prefs_set_back_text";
    private static final String EXTRA_PREFS_SET_NEXT_TEXT = "extra_prefs_set_next_text";
    private static final String EXTRA_PREFS_SHOW_BUTTON_BAR = "extra_prefs_show_button_bar";
    private static final String EXTRA_PREFS_SHOW_SKIP = "extra_prefs_show_skip";
    public static final String EXTRA_SHOW_FRAGMENT = ":android:show_fragment";
    public static final String EXTRA_SHOW_FRAGMENT_ARGUMENTS = ":android:show_fragment_args";
    public static final String EXTRA_SHOW_FRAGMENT_SHORT_TITLE = ":android:show_fragment_short_title";
    public static final String EXTRA_SHOW_FRAGMENT_TITLE = ":android:show_fragment_title";
    private static final int FIRST_REQUEST_CODE = 100;
    private static final String HEADERS_TAG = ":android:headers";
    public static final long HEADER_ID_UNDEFINED = -1;
    private static final int MSG_BIND_PREFERENCES = 1;
    private static final int MSG_BUILD_HEADERS = 2;
    private static final String PREFERENCES_TAG = ":android:preferences";
    private static final float SPLIT_BAR_MOVEABLE_AREA_MAX = 0.66f;
    private static final float SPLIT_BAR_MOVEABLE_AREA_MIN = 0.2f;
    private static final float SPLIT_BAR_SPLIT_X_IN_FULLVIEW = 20.0f;
    private static final String TAG = "PreferenceActivity";
    private static float mSplitBarMovedLeftWeight = -1.0f;
    private static boolean mUserUpdateSplit = false;
    private CharSequence mActivityTitle;
    private Header mCurHeader;
    private FragmentBreadCrumbs mFragmentBreadCrumbs;
    private ViewGroup mHeadersContainer;
    private boolean mIsDeviceDefault;
    private FrameLayout mListFooter;
    private Button mNextButton;
    private PreferenceManager mPreferenceManager;
    private ViewGroup mPrefsContainer;
    private Bundle mSavedInstanceState;
    private boolean mSinglePane;
    private final ArrayList<Header> mHeaders = new ArrayList<>();
    private int mPreferenceHeaderItemResId = 0;
    private boolean mPreferenceHeaderRemoveEmptyIcon = false;
    private boolean mInsideOnCreate = false;
    private boolean mIsRTL = false;
    private View mSplitBarView = null;
    private boolean mUpdateLayoutBySplitChange = false;
    private View.OnLayoutChangeListener mSplitBarLayoutChangeListner = null;
    private Handler mHandler = new Handler() { // from class: android.preference.PreferenceActivity.1
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            Header mappedHeader;
            switch (msg.what) {
                case 1:
                    PreferenceActivity.this.bindPreferences();
                    break;
                case 2:
                    ArrayList<Header> oldHeaders = new ArrayList<>(PreferenceActivity.this.mHeaders);
                    PreferenceActivity.this.mHeaders.clear();
                    PreferenceActivity.this.onBuildHeaders(PreferenceActivity.this.mHeaders);
                    if (PreferenceActivity.this.mAdapter instanceof BaseAdapter) {
                        ((BaseAdapter) PreferenceActivity.this.mAdapter).notifyDataSetChanged();
                    }
                    Header header = PreferenceActivity.this.onGetNewHeader();
                    if (header != null && header.fragment != null) {
                        Header mappedHeader2 = PreferenceActivity.this.findBestMatchingHeader(header, oldHeaders);
                        if (mappedHeader2 == null || PreferenceActivity.this.mCurHeader != mappedHeader2) {
                            PreferenceActivity.this.switchToHeader(header);
                            break;
                        }
                    } else if (PreferenceActivity.this.mCurHeader != null && (mappedHeader = PreferenceActivity.this.findBestMatchingHeader(PreferenceActivity.this.mCurHeader, PreferenceActivity.this.mHeaders)) != null) {
                        PreferenceActivity.this.setSelectedHeader(mappedHeader);
                        break;
                    }
                    break;
            }
        }
    };
    private boolean mEnableSplitBar = true;
    private boolean mIsMultiPane = false;

    private static class HeaderAdapter extends ArrayAdapter<Header> {
        private LayoutInflater mInflater;
        private int mLayoutResId;
        private boolean mRemoveIconIfEmpty;

        private static class HeaderViewHolder {
            ImageView icon;
            TextView summary;
            TextView title;

            private HeaderViewHolder() {
            }
        }

        public HeaderAdapter(Context context, List<Header> objects, int layoutResId, boolean removeIconBehavior) {
            super(context, 0, objects);
            this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.mLayoutResId = layoutResId;
            this.mRemoveIconIfEmpty = removeIconBehavior;
        }

        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            HeaderViewHolder holder;
            if (convertView == null) {
                view = this.mInflater.inflate(this.mLayoutResId, parent, false);
                holder = new HeaderViewHolder();
                holder.icon = (ImageView) view.findViewById(16908294);
                holder.title = (TextView) view.findViewById(16908310);
                holder.summary = (TextView) view.findViewById(16908304);
                view.setTag(holder);
            } else {
                view = convertView;
                holder = (HeaderViewHolder) view.getTag();
            }
            Header header = getItem(position);
            if (this.mRemoveIconIfEmpty) {
                if (header.iconRes != 0) {
                    holder.icon.setVisibility(0);
                    holder.icon.setImageResource(header.iconRes);
                } else {
                    holder.icon.setVisibility(8);
                }
            } else {
                holder.icon.setImageResource(header.iconRes);
            }
            holder.title.lambda$setTextAsync$0(header.getTitle(getContext().getResources()));
            CharSequence summary = header.getSummary(getContext().getResources());
            if (!TextUtils.isEmpty(summary)) {
                holder.summary.setVisibility(0);
                holder.summary.lambda$setTextAsync$0(summary);
            } else {
                holder.summary.setVisibility(8);
            }
            return view;
        }
    }

    @Deprecated
    public static final class Header implements Parcelable {
        public static final Parcelable.Creator<Header> CREATOR = new Parcelable.Creator<Header>() { // from class: android.preference.PreferenceActivity.Header.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Header createFromParcel(Parcel source) {
                return new Header(source);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Header[] newArray(int size) {
                return new Header[size];
            }
        };
        public CharSequence breadCrumbShortTitle;
        public int breadCrumbShortTitleRes;
        public CharSequence breadCrumbTitle;
        public int breadCrumbTitleRes;
        public Bundle extras;
        public String fragment;
        public Bundle fragmentArguments;
        public int iconRes;
        public long id = -1;
        public Intent intent;
        public CharSequence summary;
        public int summaryRes;
        public CharSequence title;
        public int titleRes;

        public Header() {
        }

        public CharSequence getTitle(Resources res) {
            if (this.titleRes != 0) {
                return res.getText(this.titleRes);
            }
            return this.title;
        }

        public CharSequence getSummary(Resources res) {
            if (this.summaryRes != 0) {
                return res.getText(this.summaryRes);
            }
            return this.summary;
        }

        public CharSequence getBreadCrumbTitle(Resources res) {
            if (this.breadCrumbTitleRes != 0) {
                return res.getText(this.breadCrumbTitleRes);
            }
            return this.breadCrumbTitle;
        }

        public CharSequence getBreadCrumbShortTitle(Resources res) {
            if (this.breadCrumbShortTitleRes != 0) {
                return res.getText(this.breadCrumbShortTitleRes);
            }
            return this.breadCrumbShortTitle;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeLong(this.id);
            dest.writeInt(this.titleRes);
            TextUtils.writeToParcel(this.title, dest, flags);
            dest.writeInt(this.summaryRes);
            TextUtils.writeToParcel(this.summary, dest, flags);
            dest.writeInt(this.breadCrumbTitleRes);
            TextUtils.writeToParcel(this.breadCrumbTitle, dest, flags);
            dest.writeInt(this.breadCrumbShortTitleRes);
            TextUtils.writeToParcel(this.breadCrumbShortTitle, dest, flags);
            dest.writeInt(this.iconRes);
            dest.writeString(this.fragment);
            dest.writeBundle(this.fragmentArguments);
            if (this.intent != null) {
                dest.writeInt(1);
                this.intent.writeToParcel(dest, flags);
            } else {
                dest.writeInt(0);
            }
            dest.writeBundle(this.extras);
        }

        public void readFromParcel(Parcel in) {
            this.id = in.readLong();
            this.titleRes = in.readInt();
            this.title = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(in);
            this.summaryRes = in.readInt();
            this.summary = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(in);
            this.breadCrumbTitleRes = in.readInt();
            this.breadCrumbTitle = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(in);
            this.breadCrumbShortTitleRes = in.readInt();
            this.breadCrumbShortTitle = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(in);
            this.iconRes = in.readInt();
            this.fragment = in.readString();
            this.fragmentArguments = in.readBundle();
            if (in.readInt() != 0) {
                this.intent = Intent.CREATOR.createFromParcel(in);
            }
            this.extras = in.readBundle();
        }

        Header(Parcel in) {
            readFromParcel(in);
        }
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 16908332) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        int i;
        super.onCreate(savedInstanceState);
        this.mInsideOnCreate = true;
        TypedArray sa = obtainStyledAttributes(null, R.styleable.PreferenceActivity, R.attr.preferenceActivityStyle, 0);
        int layoutResId = sa.getResourceId(0, R.layout.preference_list_content);
        this.mPreferenceHeaderItemResId = sa.getResourceId(1, R.layout.preference_header_item);
        this.mPreferenceHeaderRemoveEmptyIcon = sa.getBoolean(2, false);
        TypedValue outValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.parentIsDeviceDefault, outValue, true);
        this.mIsDeviceDefault = outValue.data != 0;
        sa.recycle();
        setContentView(layoutResId);
        this.mListFooter = (FrameLayout) findViewById(R.id.list_footer);
        this.mPrefsContainer = (ViewGroup) findViewById(R.id.prefs_frame);
        this.mHeadersContainer = (ViewGroup) findViewById(R.id.headers);
        boolean hidingHeaders = onIsHidingHeaders();
        this.mSinglePane = hidingHeaders || !onIsMultiPane();
        String initialFragment = getIntent().getStringExtra(EXTRA_SHOW_FRAGMENT);
        Bundle initialArguments = getIntent().getBundleExtra(EXTRA_SHOW_FRAGMENT_ARGUMENTS);
        int initialTitle = getIntent().getIntExtra(EXTRA_SHOW_FRAGMENT_TITLE, 0);
        int initialShortTitle = getIntent().getIntExtra(EXTRA_SHOW_FRAGMENT_SHORT_TITLE, 0);
        this.mActivityTitle = getTitle();
        if (!this.mIsDeviceDefault || this.mSinglePane) {
            this.mSplitBarView = findViewById(R.id.prefs_split_bar);
            if (this.mSplitBarView != null) {
                this.mSplitBarView.setVisibility(8);
                this.mSplitBarView = null;
            }
        } else {
            this.mSplitBarView = findViewById(R.id.prefs_split_bar);
            if (this.mSplitBarView != null) {
                LinearLayout.LayoutParams llp = (LinearLayout.LayoutParams) this.mHeadersContainer.getLayoutParams();
                LinearLayout.LayoutParams rlp = (LinearLayout.LayoutParams) this.mPrefsContainer.getLayoutParams();
                float leftPanelWeight = llp.weight;
                float rightPanelWeight = rlp.weight;
                float weightSum = leftPanelWeight + rightPanelWeight;
                if (mSplitBarMovedLeftWeight > 0.0f) {
                    llp.weight = mSplitBarMovedLeftWeight;
                    rlp.weight = weightSum - mSplitBarMovedLeftWeight;
                    this.mHeadersContainer.setLayoutParams(llp);
                    this.mPrefsContainer.setLayoutParams(rlp);
                }
            }
        }
        if (savedInstanceState != null) {
            ArrayList<Header> headers = savedInstanceState.getParcelableArrayList(HEADERS_TAG, Header.class);
            if (headers == null) {
                showBreadCrumbs(getTitle(), null);
            } else {
                this.mHeaders.addAll(headers);
                int curHeader = savedInstanceState.getInt(CUR_HEADER_TAG, -1);
                if (curHeader >= 0 && curHeader < this.mHeaders.size()) {
                    setSelectedHeader(this.mHeaders.get(curHeader));
                } else if (!this.mSinglePane && initialFragment == null) {
                    switchToHeader(onGetInitialHeader());
                }
            }
        } else {
            if (!onIsHidingHeaders()) {
                onBuildHeaders(this.mHeaders);
            }
            if (initialFragment != null) {
                switchToHeader(initialFragment, initialArguments);
            } else if (!this.mSinglePane && this.mHeaders.size() > 0) {
                switchToHeader(onGetInitialHeader());
            }
        }
        if (this.mHeaders.size() > 0) {
            setListAdapter(new HeaderAdapter(this, this.mHeaders, this.mPreferenceHeaderItemResId, this.mPreferenceHeaderRemoveEmptyIcon));
            if (!this.mSinglePane) {
                getListView().setChoiceMode(1);
            }
        }
        if (this.mSinglePane && initialFragment != null) {
            if (this.mIsDeviceDefault) {
                ViewGroup crumbsLayout = (ViewGroup) this.mPrefsContainer.getChildAt(0);
                crumbsLayout.removeAllViews();
                crumbsLayout.setVisibility(8);
                this.mFragmentBreadCrumbs = null;
            }
            if (initialTitle != 0) {
                CharSequence initialTitleStr = getText(initialTitle);
                CharSequence initialShortTitleStr = initialShortTitle != 0 ? getText(initialShortTitle) : null;
                showBreadCrumbs(initialTitleStr, initialShortTitleStr);
            }
        }
        if (this.mHeaders.size() == 0 && initialFragment == null) {
            if (this.mIsDeviceDefault) {
                i = R.layout.tw_preference_list_content_single;
            } else {
                i = R.layout.preference_list_content_single;
            }
            setContentView(i);
            this.mListFooter = (FrameLayout) findViewById(R.id.list_footer);
            this.mPrefsContainer = (ViewGroup) findViewById(R.id.prefs);
            this.mPreferenceManager = new PreferenceManager(this, 100);
            this.mPreferenceManager.setOnPreferenceTreeClickListener(this);
            this.mHeadersContainer = null;
        } else if (this.mSinglePane) {
            if (initialFragment != null || this.mCurHeader != null) {
                this.mHeadersContainer.setVisibility(8);
            } else {
                this.mPrefsContainer.setVisibility(8);
            }
            ViewGroup container = (ViewGroup) findViewById(R.id.prefs_container);
            container.setLayoutTransition(new LayoutTransition());
        } else if (this.mHeaders.size() > 0 && this.mCurHeader != null) {
            setSelectedHeader(this.mCurHeader);
        }
        Intent intent = getIntent();
        if (intent.getBooleanExtra(EXTRA_PREFS_SHOW_BUTTON_BAR, false)) {
            findViewById(R.id.button_bar).setVisibility(0);
            Button backButton = (Button) findViewById(R.id.back_button);
            backButton.setOnClickListener(new View.OnClickListener() { // from class: android.preference.PreferenceActivity.2
                @Override // android.view.View.OnClickListener
                public void onClick(View v) {
                    PreferenceActivity.this.setResult(0);
                    PreferenceActivity.this.finish();
                }
            });
            Button skipButton = (Button) findViewById(R.id.skip_button);
            skipButton.setOnClickListener(new View.OnClickListener() { // from class: android.preference.PreferenceActivity.3
                @Override // android.view.View.OnClickListener
                public void onClick(View v) {
                    PreferenceActivity.this.setResult(-1);
                    PreferenceActivity.this.finish();
                }
            });
            this.mNextButton = (Button) findViewById(R.id.next_button);
            this.mNextButton.setOnClickListener(new View.OnClickListener() { // from class: android.preference.PreferenceActivity.4
                @Override // android.view.View.OnClickListener
                public void onClick(View v) {
                    PreferenceActivity.this.setResult(-1);
                    PreferenceActivity.this.finish();
                }
            });
            if (intent.hasExtra(EXTRA_PREFS_SET_NEXT_TEXT)) {
                String buttonText = intent.getStringExtra(EXTRA_PREFS_SET_NEXT_TEXT);
                if (TextUtils.isEmpty(buttonText)) {
                    this.mNextButton.setVisibility(8);
                } else {
                    this.mNextButton.lambda$setTextAsync$0(buttonText);
                }
            }
            if (intent.hasExtra(EXTRA_PREFS_SET_BACK_TEXT)) {
                String buttonText2 = intent.getStringExtra(EXTRA_PREFS_SET_BACK_TEXT);
                if (TextUtils.isEmpty(buttonText2)) {
                    backButton.setVisibility(8);
                } else {
                    backButton.lambda$setTextAsync$0(buttonText2);
                }
            }
            if (intent.getBooleanExtra(EXTRA_PREFS_SHOW_SKIP, false)) {
                skipButton.setVisibility(0);
            }
        }
        Preference preference = new Preference(this);
        this.mIsRTL = preference.isRTL() && preference.hasRTL();
        if (this.mIsDeviceDefault && !this.mSinglePane && this.mSplitBarView != null) {
            if (this.mSplitBarLayoutChangeListner == null) {
                this.mSplitBarLayoutChangeListner = new View.OnLayoutChangeListener() { // from class: android.preference.PreferenceActivity.5
                    @Override // android.view.View.OnLayoutChangeListener
                    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                        float mRightLayoutStartPosition;
                        if (!PreferenceActivity.this.mEnableSplitBar) {
                            return;
                        }
                        if (PreferenceActivity.this.mIsRTL) {
                            mRightLayoutStartPosition = PreferenceActivity.this.mHeadersContainer.getX();
                        } else {
                            mRightLayoutStartPosition = PreferenceActivity.this.mPrefsContainer.getX();
                        }
                        if (PreferenceActivity.this.mIsDeviceDefault && PreferenceActivity.this.mSplitBarView != null) {
                            float x = mRightLayoutStartPosition - (PreferenceActivity.this.mSplitBarView.getWidth() / 2.0f);
                            if (x < 0.0f) {
                                x = 0.0f;
                            }
                            if (PreferenceActivity.this.mSplitBarView.getX() != x) {
                                PreferenceActivity.this.mSplitBarView.setX(x);
                            }
                        }
                    }
                };
            }
            this.mSplitBarView.addOnLayoutChangeListener(this.mSplitBarLayoutChangeListner);
            this.mSplitBarView.setOnTouchListener(new View.OnTouchListener() { // from class: android.preference.PreferenceActivity.6
                @Override // android.view.View.OnTouchListener
                public boolean onTouch(View v, MotionEvent event) {
                    if (!PreferenceActivity.this.mEnableSplitBar) {
                        return false;
                    }
                    int action = event.getAction();
                    View splitBar = null;
                    if (v instanceof ViewGroup) {
                        splitBar = ((ViewGroup) v).getChildAt(0);
                    }
                    if (splitBar == null) {
                        return false;
                    }
                    if (action == 0) {
                        splitBar.setVisibility(0);
                        PreferenceActivity.this.mUpdateLayoutBySplitChange = false;
                        return true;
                    }
                    if (action != 2) {
                        if (action != 1) {
                            float x = PreferenceActivity.this.mPrefsContainer.getX() - (PreferenceActivity.this.mSplitBarView.getWidth() / 2.0f);
                            if (x < 0.0f) {
                                x = 0.0f;
                            }
                            if (action != 3 || !PreferenceActivity.this.mIsDeviceDefault) {
                                PreferenceActivity.this.mSplitBarView.setX(x);
                            }
                            PreferenceActivity.this.mUpdateLayoutBySplitChange = false;
                            splitBar.setVisibility(4);
                            return true;
                        }
                        LinearLayout.LayoutParams llp2 = (LinearLayout.LayoutParams) PreferenceActivity.this.mHeadersContainer.getLayoutParams();
                        if (PreferenceActivity.mSplitBarMovedLeftWeight != llp2.weight) {
                            PreferenceActivity.mSplitBarMovedLeftWeight = llp2.weight;
                        }
                        splitBar.setVisibility(4);
                        splitBar.requestLayout();
                        return true;
                    }
                    int splitBarwidth = PreferenceActivity.this.mSplitBarView.getWidth();
                    int parentLayoutWidth = ((View) PreferenceActivity.this.mSplitBarView.getParent()).getWidth();
                    float touchX = event.getX();
                    float newSplitBarPosX = PreferenceActivity.this.mSplitBarView.getX();
                    float newSplitBarCenterPosX = (splitBarwidth / 2.0f) + newSplitBarPosX;
                    float touchXInParentRect = newSplitBarPosX + touchX;
                    if (PreferenceActivity.this.mIsDeviceDefault && PreferenceActivity.this.mIsRTL) {
                        if (touchX > splitBarwidth && touchXInParentRect <= parentLayoutWidth) {
                            newSplitBarCenterPosX += touchX - splitBarwidth;
                            if (newSplitBarCenterPosX / parentLayoutWidth > 0.8f) {
                                DisplayMetrics d = PreferenceActivity.this.getResources().getDisplayMetrics();
                                float splitXinFullview = TypedValue.applyDimension(1, PreferenceActivity.SPLIT_BAR_SPLIT_X_IN_FULLVIEW, d);
                                newSplitBarCenterPosX = parentLayoutWidth - splitXinFullview;
                            }
                            PreferenceActivity.this.mSplitBarView.setX(newSplitBarCenterPosX - (splitBarwidth / 2.0f));
                            PreferenceActivity.this.mUpdateLayoutBySplitChange = true;
                        } else if (touchX < 0.0f && touchXInParentRect >= 0.0f) {
                            newSplitBarCenterPosX += touchX;
                            float splitRatio = newSplitBarCenterPosX / parentLayoutWidth;
                            if (splitRatio < 0.33999997f) {
                                newSplitBarCenterPosX = parentLayoutWidth * 0.33999997f;
                            } else if (splitRatio > 0.8f) {
                                DisplayMetrics d2 = PreferenceActivity.this.getResources().getDisplayMetrics();
                                float splitXinFullview2 = TypedValue.applyDimension(1, PreferenceActivity.SPLIT_BAR_SPLIT_X_IN_FULLVIEW, d2);
                                newSplitBarCenterPosX = parentLayoutWidth - splitXinFullview2;
                            }
                            PreferenceActivity.this.mSplitBarView.setX(newSplitBarCenterPosX - (splitBarwidth / 2.0f));
                            PreferenceActivity.this.mUpdateLayoutBySplitChange = true;
                        }
                    } else if (touchX > splitBarwidth && touchXInParentRect <= parentLayoutWidth) {
                        newSplitBarCenterPosX += touchX - splitBarwidth;
                        float splitRatio2 = newSplitBarCenterPosX / parentLayoutWidth;
                        if (splitRatio2 > PreferenceActivity.SPLIT_BAR_MOVEABLE_AREA_MAX) {
                            newSplitBarCenterPosX = parentLayoutWidth * PreferenceActivity.SPLIT_BAR_MOVEABLE_AREA_MAX;
                        } else if (splitRatio2 < 0.2f) {
                            DisplayMetrics d3 = PreferenceActivity.this.getResources().getDisplayMetrics();
                            float splitXinFullview3 = TypedValue.applyDimension(1, PreferenceActivity.SPLIT_BAR_SPLIT_X_IN_FULLVIEW, d3);
                            newSplitBarCenterPosX = splitXinFullview3;
                        }
                        PreferenceActivity.this.mSplitBarView.setX(newSplitBarCenterPosX - (splitBarwidth / 2.0f));
                        PreferenceActivity.this.mUpdateLayoutBySplitChange = true;
                    } else if (touchX < 0.0f && touchXInParentRect >= 0.0f) {
                        newSplitBarCenterPosX += touchX;
                        if (newSplitBarCenterPosX / parentLayoutWidth < 0.2f) {
                            DisplayMetrics d4 = PreferenceActivity.this.getResources().getDisplayMetrics();
                            float splitXinFullview4 = TypedValue.applyDimension(1, PreferenceActivity.SPLIT_BAR_SPLIT_X_IN_FULLVIEW, d4);
                            newSplitBarCenterPosX = splitXinFullview4;
                        }
                        PreferenceActivity.this.mSplitBarView.setX(newSplitBarCenterPosX - (splitBarwidth / 2.0f));
                        PreferenceActivity.this.mUpdateLayoutBySplitChange = true;
                    }
                    if (PreferenceActivity.this.mUpdateLayoutBySplitChange) {
                        PreferenceActivity.mUserUpdateSplit = true;
                        LinearLayout.LayoutParams llp3 = (LinearLayout.LayoutParams) PreferenceActivity.this.mHeadersContainer.getLayoutParams();
                        LinearLayout.LayoutParams rlp2 = (LinearLayout.LayoutParams) PreferenceActivity.this.mPrefsContainer.getLayoutParams();
                        float leftPanelWeight2 = llp3.weight;
                        float rightPanelWeight2 = rlp2.weight;
                        float weightSum2 = leftPanelWeight2 + rightPanelWeight2;
                        float leftPanelWidthRatio = newSplitBarCenterPosX / parentLayoutWidth;
                        float newLeftPanelWeight = weightSum2 * leftPanelWidthRatio;
                        float newRightPanelWeight = weightSum2 - newLeftPanelWeight;
                        llp3.weight = newLeftPanelWeight;
                        rlp2.weight = newRightPanelWeight;
                        if (PreferenceActivity.this.mIsDeviceDefault) {
                            if (PreferenceActivity.this.mIsRTL) {
                                PreferenceActivity.this.mHeadersContainer.setLayoutParams(rlp2);
                                PreferenceActivity.this.mPrefsContainer.setLayoutParams(llp3);
                            } else {
                                PreferenceActivity.this.mHeadersContainer.setLayoutParams(llp3);
                                PreferenceActivity.this.mPrefsContainer.setLayoutParams(rlp2);
                            }
                        }
                    }
                    PreferenceActivity.this.mUpdateLayoutBySplitChange = false;
                    return true;
                }
            });
        }
        this.mInsideOnCreate = false;
    }

    @Override // android.app.Activity
    public void onBackPressed() {
        if (this.mCurHeader != null && this.mSinglePane && getFragmentManager().getBackStackEntryCount() == 0 && getIntent().getStringExtra(EXTRA_SHOW_FRAGMENT) == null) {
            this.mCurHeader = null;
            this.mPrefsContainer.setVisibility(8);
            this.mHeadersContainer.setVisibility(0);
            if (this.mActivityTitle != null) {
                showBreadCrumbs(this.mActivityTitle, null);
            }
            getListView().clearChoices();
            return;
        }
        super.onBackPressed();
    }

    public boolean hasHeaders() {
        return this.mHeadersContainer != null && this.mHeadersContainer.getVisibility() == 0;
    }

    public List<Header> getHeaders() {
        return this.mHeaders;
    }

    public boolean isMultiPane() {
        return !this.mSinglePane;
    }

    public boolean onIsMultiPane() {
        boolean preferMultiPane = getResources().getBoolean(R.bool.preferences_prefer_dual_pane);
        return preferMultiPane || this.mIsMultiPane;
    }

    protected void semSetMultiPane(boolean isMultiPane) {
        this.mIsMultiPane = isMultiPane;
    }

    public void setEnableSplitBar(boolean enable) {
        this.mEnableSplitBar = enable;
    }

    public boolean onIsHidingHeaders() {
        return getIntent().getBooleanExtra(EXTRA_NO_HEADERS, false);
    }

    public Header onGetInitialHeader() {
        for (int i = 0; i < this.mHeaders.size(); i++) {
            Header h = this.mHeaders.get(i);
            if (h.fragment != null) {
                return h;
            }
        }
        throw new IllegalStateException("Must have at least one header with a fragment");
    }

    public Header onGetNewHeader() {
        return null;
    }

    public void onBuildHeaders(List<Header> target) {
    }

    public void invalidateHeaders() {
        if (!this.mHandler.hasMessages(2)) {
            this.mHandler.sendEmptyMessage(2);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:102:0x0112, code lost:
    
        r16 = r2.getName();
     */
    /* JADX WARN: Code restructure failed: missing block: B:103:0x011e, code lost:
    
        if (r16.equals(com.samsung.android.share.SemShareConstants.SURVEY_CONTENT_EXTRA) == false) goto L80;
     */
    /* JADX WARN: Code restructure failed: missing block: B:104:0x0120, code lost:
    
        getResources().parseBundleExtra(com.samsung.android.share.SemShareConstants.SURVEY_CONTENT_EXTRA, r3, r9);
        com.android.internal.util.XmlUtils.skipCurrentTag(r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:107:0x0132, code lost:
    
        if (r16.equals("intent") == false) goto L83;
     */
    /* JADX WARN: Code restructure failed: missing block: B:108:0x0134, code lost:
    
        r13.intent = android.content.Intent.parseIntent(getResources(), r2, r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:110:0x013f, code lost:
    
        com.android.internal.util.XmlUtils.skipCurrentTag(r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:114:0x00f4, code lost:
    
        r9 = r18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:116:0x015c, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:121:0x015f, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:126:0x0159, code lost:
    
        r0 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:133:0x004e, code lost:
    
        r18 = r9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x004c, code lost:
    
        if (r11 != 4) goto L146;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0061, code lost:
    
        if (android.provider.Downloads.Impl.RequestHeaders.COLUMN_HEADER.equals(r2.getName()) == false) goto L150;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0162, code lost:
    
        r18 = r9;
        com.android.internal.util.XmlUtils.skipCurrentTag(r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x016b, code lost:
    
        r9 = r18;
        r7 = 2;
        r8 = 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x0063, code lost:
    
        r13 = new android.preference.PreferenceActivity.Header();
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x006c, code lost:
    
        r14 = obtainStyledAttributes(r3, com.android.internal.R.styleable.PreferenceHeader);
        r18 = r9;
        r13.id = r14.getResourceId(r8, -1);
        r8 = r14.peekValue(r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x007e, code lost:
    
        if (r8 == null) goto L37;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x0082, code lost:
    
        if (r8.type != 3) goto L37;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x0086, code lost:
    
        if (r8.resourceId == 0) goto L36;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x0088, code lost:
    
        r13.titleRes = r8.resourceId;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x008d, code lost:
    
        r13.title = r8.string;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x0091, code lost:
    
        r9 = r14.peekValue(3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x0096, code lost:
    
        if (r9 == null) goto L45;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x009a, code lost:
    
        if (r9.type != 3) goto L45;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x009e, code lost:
    
        if (r9.resourceId == 0) goto L44;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x00a0, code lost:
    
        r13.summaryRes = r9.resourceId;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x00a5, code lost:
    
        r13.summary = r9.string;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x00a9, code lost:
    
        r9 = r14.peekValue(5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x00af, code lost:
    
        if (r9 == null) goto L53;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x00b3, code lost:
    
        if (r9.type != 3) goto L53;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x00b7, code lost:
    
        if (r9.resourceId == 0) goto L52;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x00b9, code lost:
    
        r13.breadCrumbTitleRes = r9.resourceId;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x00be, code lost:
    
        r13.breadCrumbTitle = r9.string;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x00c2, code lost:
    
        r9 = r14.peekValue(6);
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x00c8, code lost:
    
        if (r9 == null) goto L61;
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x00cc, code lost:
    
        if (r9.type != 3) goto L61;
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x00d0, code lost:
    
        if (r9.resourceId == 0) goto L60;
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x00d2, code lost:
    
        r13.breadCrumbShortTitleRes = r9.resourceId;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x00d7, code lost:
    
        r13.breadCrumbShortTitle = r9.string;
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x00db, code lost:
    
        r13.iconRes = r14.getResourceId(0, 0);
        r13.fragment = r14.getString(4);
        r14.recycle();
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x00ec, code lost:
    
        if (r18 != null) goto L64;
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x00ee, code lost:
    
        r9 = new android.os.Bundle();
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x00f6, code lost:
    
        r12 = r2.getDepth();
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x00fa, code lost:
    
        r7 = r2.next();
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x0100, code lost:
    
        if (r7 == 1) goto L156;
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x0103, code lost:
    
        if (r7 != 3) goto L72;
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x0109, code lost:
    
        if (r2.getDepth() <= r12) goto L157;
     */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x014a, code lost:
    
        if (r9.size() <= 0) goto L90;
     */
    /* JADX WARN: Code restructure failed: missing block: B:90:0x014c, code lost:
    
        r13.fragmentArguments = r9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:91:0x014e, code lost:
    
        r9 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:93:0x0152, code lost:
    
        r21.add(r13);
        r7 = 2;
        r8 = 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:96:0x010c, code lost:
    
        if (r7 == 3) goto L158;
     */
    /* JADX WARN: Code restructure failed: missing block: B:98:0x010f, code lost:
    
        if (r7 != 4) goto L77;
     */
    /* JADX WARN: Removed duplicated region for block: B:130:0x01e9  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void loadHeadersFromResource(int r20, java.util.List<android.preference.PreferenceActivity.Header> r21) {
        /*
            Method dump skipped, instructions count: 493
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.preference.PreferenceActivity.loadHeadersFromResource(int, java.util.List):void");
    }

    protected boolean isValidFragment(String fragmentName) {
        if (getApplicationInfo().targetSdkVersion >= 19) {
            throw new RuntimeException("Subclasses of PreferenceActivity must override isValidFragment(String) to verify that the Fragment class is valid! " + getClass().getName() + " has not checked if fragment " + fragmentName + " is valid.");
        }
        return true;
    }

    public void setListFooter(View view) {
        this.mListFooter.removeAllViews();
        this.mListFooter.addView(view, new FrameLayout.LayoutParams(-1, -2));
    }

    @Override // android.app.Activity
    protected void onStop() {
        super.onStop();
        if (this.mPreferenceManager != null) {
            this.mPreferenceManager.dispatchActivityStop();
        }
    }

    @Override // android.app.ListActivity, android.app.Activity
    protected void onDestroy() {
        this.mHandler.removeMessages(1);
        this.mHandler.removeMessages(2);
        super.onDestroy();
        if (this.mPreferenceManager != null) {
            this.mPreferenceManager.dispatchActivityDestroy();
        }
    }

    @Override // android.app.Activity
    protected void onSaveInstanceState(Bundle outState) {
        PreferenceScreen preferenceScreen;
        int index;
        super.onSaveInstanceState(outState);
        if (this.mHeaders.size() > 0) {
            outState.putParcelableArrayList(HEADERS_TAG, this.mHeaders);
            if (this.mCurHeader != null && (index = this.mHeaders.indexOf(this.mCurHeader)) >= 0) {
                outState.putInt(CUR_HEADER_TAG, index);
            }
        }
        if (this.mPreferenceManager != null && (preferenceScreen = getPreferenceScreen()) != null) {
            Bundle container = new Bundle();
            preferenceScreen.saveHierarchyState(container);
            outState.putBundle(PREFERENCES_TAG, container);
        }
    }

    @Override // android.app.ListActivity, android.app.Activity
    protected void onRestoreInstanceState(Bundle state) {
        Bundle container;
        PreferenceScreen preferenceScreen;
        if (this.mPreferenceManager != null && (container = state.getBundle(PREFERENCES_TAG)) != null && (preferenceScreen = getPreferenceScreen()) != null) {
            preferenceScreen.restoreHierarchyState(container);
            this.mSavedInstanceState = state;
            return;
        }
        super.onRestoreInstanceState(state);
        if (!this.mSinglePane && this.mCurHeader != null) {
            setSelectedHeader(this.mCurHeader);
        }
    }

    @Override // android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (this.mPreferenceManager != null) {
            this.mPreferenceManager.dispatchActivityResult(requestCode, resultCode, data);
        }
    }

    @Override // android.app.ListActivity, android.app.Activity, android.view.Window.Callback
    public void onContentChanged() {
        super.onContentChanged();
        if (this.mPreferenceManager != null) {
            postBindPreferences();
        }
    }

    @Override // android.app.ListActivity
    protected void onListItemClick(ListView l, View v, int position, long id) {
        if (!isResumed()) {
            return;
        }
        super.onListItemClick(l, v, position, id);
        if (this.mAdapter != null) {
            Object item = this.mAdapter.getItem(position);
            if (item instanceof Header) {
                onHeaderClick((Header) item, position);
            }
        }
    }

    public void onHeaderClick(Header header, int position) {
        if (header.fragment != null) {
            switchToHeader(header);
        } else if (header.intent != null) {
            startActivity(header.intent);
        }
    }

    public Intent onBuildStartFragmentIntent(String fragmentName, Bundle args, int titleRes, int shortTitleRes) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setClass(this, getClass());
        intent.putExtra(EXTRA_SHOW_FRAGMENT, fragmentName);
        intent.putExtra(EXTRA_SHOW_FRAGMENT_ARGUMENTS, args);
        intent.putExtra(EXTRA_SHOW_FRAGMENT_TITLE, titleRes);
        intent.putExtra(EXTRA_SHOW_FRAGMENT_SHORT_TITLE, shortTitleRes);
        intent.putExtra(EXTRA_NO_HEADERS, true);
        return intent;
    }

    public void startWithFragment(String fragmentName, Bundle args, Fragment resultTo, int resultRequestCode) {
        startWithFragment(fragmentName, args, resultTo, resultRequestCode, 0, 0);
    }

    public void startWithFragment(String fragmentName, Bundle args, Fragment resultTo, int resultRequestCode, int titleRes, int shortTitleRes) {
        Intent intent = onBuildStartFragmentIntent(fragmentName, args, titleRes, shortTitleRes);
        if (resultTo == null) {
            startActivity(intent);
        } else {
            resultTo.startActivityForResult(intent, resultRequestCode);
        }
    }

    public void showBreadCrumbs(CharSequence title, CharSequence shortTitle) {
        if (this.mFragmentBreadCrumbs == null) {
            View crumbs = findViewById(16908310);
            try {
                this.mFragmentBreadCrumbs = (FragmentBreadCrumbs) crumbs;
                if (this.mFragmentBreadCrumbs == null) {
                    if (title != null) {
                        setTitle(title);
                        return;
                    }
                    return;
                }
                if (this.mSinglePane) {
                    this.mFragmentBreadCrumbs.setVisibility(8);
                    View bcSection = findViewById(R.id.breadcrumb_section);
                    if (bcSection != null) {
                        bcSection.setVisibility(8);
                    }
                    setTitle(title);
                }
                this.mFragmentBreadCrumbs.setMaxVisible(2);
                this.mFragmentBreadCrumbs.setActivity(this);
            } catch (ClassCastException e) {
                setTitle(title);
                return;
            }
        }
        View crumbs2 = this.mFragmentBreadCrumbs;
        if (crumbs2.getVisibility() != 0) {
            setTitle(title);
        } else {
            this.mFragmentBreadCrumbs.setTitle(title, shortTitle);
            this.mFragmentBreadCrumbs.setParentTitle(null, null, null);
        }
    }

    public void setParentTitle(CharSequence title, CharSequence shortTitle, View.OnClickListener listener) {
        if (this.mFragmentBreadCrumbs != null) {
            this.mFragmentBreadCrumbs.setParentTitle(title, shortTitle, listener);
        }
    }

    void setSelectedHeader(Header header) {
        this.mCurHeader = header;
        int index = this.mHeaders.indexOf(header);
        if (index >= 0) {
            getListView().setItemChecked(index, true);
        } else {
            getListView().clearChoices();
        }
        showBreadCrumbs(header);
    }

    void showBreadCrumbs(Header header) {
        if (header != null) {
            CharSequence title = header.getBreadCrumbTitle(getResources());
            if (title == null) {
                title = header.getTitle(getResources());
            }
            if (title == null) {
                title = getTitle();
            }
            showBreadCrumbs(title, header.getBreadCrumbShortTitle(getResources()));
            return;
        }
        showBreadCrumbs(getTitle(), null);
    }

    private void switchToHeaderInner(String fragmentName, Bundle args) {
        int i;
        getFragmentManager().popBackStack(BACK_STACK_PREFS, 1);
        if (!isValidFragment(fragmentName)) {
            throw new IllegalArgumentException("Invalid fragment for this activity: " + fragmentName);
        }
        Fragment f = Fragment.instantiate(this, fragmentName, args);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (!this.mInsideOnCreate) {
            if (this.mSinglePane) {
                i = 0;
            } else {
                i = 4099;
            }
            transaction.setTransition(i);
        }
        transaction.replace(R.id.prefs, f);
        transaction.commitAllowingStateLoss();
        if (this.mSinglePane && this.mPrefsContainer.getVisibility() == 8) {
            this.mPrefsContainer.setVisibility(0);
            this.mHeadersContainer.setVisibility(8);
        }
    }

    public void switchToHeader(String fragmentName, Bundle args) {
        Header selectedHeader = null;
        int i = 0;
        while (true) {
            if (i >= this.mHeaders.size()) {
                break;
            }
            if (!fragmentName.equals(this.mHeaders.get(i).fragment)) {
                i++;
            } else {
                Header selectedHeader2 = this.mHeaders.get(i);
                selectedHeader = selectedHeader2;
                break;
            }
        }
        setSelectedHeader(selectedHeader);
        switchToHeaderInner(fragmentName, args);
    }

    public void switchToHeader(Header header) {
        if (this.mCurHeader == header) {
            getFragmentManager().popBackStack(BACK_STACK_PREFS, 1);
        } else {
            if (header.fragment == null) {
                throw new IllegalStateException("can't switch to header that has no fragment");
            }
            switchToHeaderInner(header.fragment, header.fragmentArguments);
            setSelectedHeader(header);
        }
    }

    Header findBestMatchingHeader(Header cur, ArrayList<Header> from) {
        ArrayList<Header> matches = new ArrayList<>();
        for (int j = 0; j < from.size(); j++) {
            Header oh = from.get(j);
            if (cur == oh || (cur.id != -1 && cur.id == oh.id)) {
                matches.clear();
                matches.add(oh);
                break;
            }
            if (cur.fragment != null) {
                if (cur.fragment.equals(oh.fragment)) {
                    matches.add(oh);
                }
            } else if (cur.intent != null) {
                if (cur.intent.equals(oh.intent)) {
                    matches.add(oh);
                }
            } else if (cur.title != null && cur.title.equals(oh.title)) {
                matches.add(oh);
            }
        }
        int NM = matches.size();
        if (NM == 1) {
            return matches.get(0);
        }
        if (NM > 1) {
            for (int j2 = 0; j2 < NM; j2++) {
                Header oh2 = matches.get(j2);
                if (cur.fragmentArguments != null && cur.fragmentArguments.equals(oh2.fragmentArguments)) {
                    return oh2;
                }
                if (cur.extras != null && cur.extras.equals(oh2.extras)) {
                    return oh2;
                }
                if (cur.title != null && cur.title.equals(oh2.title)) {
                    return oh2;
                }
            }
            return null;
        }
        return null;
    }

    public void startPreferenceFragment(Fragment fragment, boolean push) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.prefs, fragment);
        if (push) {
            transaction.setTransition(4097);
            transaction.addToBackStack(BACK_STACK_PREFS);
        } else {
            transaction.setTransition(4099);
        }
        transaction.commitAllowingStateLoss();
    }

    public void startPreferencePanel(String fragmentClass, Bundle args, int titleRes, CharSequence titleText, Fragment resultTo, int resultRequestCode) {
        Fragment f = Fragment.instantiate(this, fragmentClass, args);
        if (resultTo != null) {
            f.setTargetFragment(resultTo, resultRequestCode);
        }
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.prefs, f);
        if (titleRes != 0) {
            transaction.setBreadCrumbTitle(titleRes);
        } else if (titleText != null) {
            transaction.setBreadCrumbTitle(titleText);
        }
        transaction.setTransition(4097);
        transaction.addToBackStack(BACK_STACK_PREFS);
        transaction.commitAllowingStateLoss();
    }

    public void finishPreferencePanel(Fragment caller, int resultCode, Intent resultData) {
        onBackPressed();
        if (caller != null && caller.getTargetFragment() != null) {
            caller.getTargetFragment().onActivityResult(caller.getTargetRequestCode(), resultCode, resultData);
        }
    }

    @Override // android.preference.PreferenceFragment.OnPreferenceStartFragmentCallback
    public boolean onPreferenceStartFragment(PreferenceFragment caller, Preference pref) {
        startPreferencePanel(pref.getFragment(), pref.getExtras(), pref.getTitleRes(), pref.getTitle(), null, 0);
        return true;
    }

    private void postBindPreferences() {
        if (this.mHandler.hasMessages(1)) {
            return;
        }
        this.mHandler.obtainMessage(1).sendToTarget();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void bindPreferences() {
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        if (preferenceScreen != null) {
            preferenceScreen.bind(getListView());
            if (this.mSavedInstanceState != null) {
                super.onRestoreInstanceState(this.mSavedInstanceState);
                this.mSavedInstanceState = null;
            }
        }
    }

    @Deprecated
    public PreferenceManager getPreferenceManager() {
        return this.mPreferenceManager;
    }

    private void requirePreferenceManager() {
        if (this.mPreferenceManager == null) {
            if (this.mAdapter == null) {
                throw new RuntimeException("This should be called after super.onCreate.");
            }
            throw new RuntimeException("Modern two-pane PreferenceActivity requires use of a PreferenceFragment");
        }
    }

    @Deprecated
    public void setPreferenceScreen(PreferenceScreen preferenceScreen) {
        requirePreferenceManager();
        if (this.mPreferenceManager.setPreferences(preferenceScreen) && preferenceScreen != null) {
            postBindPreferences();
            CharSequence title = getPreferenceScreen().getTitle();
            if (title != null) {
                setTitle(title);
            }
        }
    }

    @Deprecated
    public PreferenceScreen getPreferenceScreen() {
        if (this.mPreferenceManager != null) {
            return this.mPreferenceManager.getPreferenceScreen();
        }
        return null;
    }

    @Deprecated
    public void addPreferencesFromIntent(Intent intent) {
        requirePreferenceManager();
        setPreferenceScreen(this.mPreferenceManager.inflateFromIntent(intent, getPreferenceScreen()));
    }

    @Deprecated
    public void addPreferencesFromResource(int preferencesResId) {
        requirePreferenceManager();
        setPreferenceScreen(this.mPreferenceManager.inflateFromResource(this, preferencesResId, getPreferenceScreen()));
    }

    @Override // android.preference.PreferenceManager.OnPreferenceTreeClickListener
    @Deprecated
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        return false;
    }

    @Deprecated
    public Preference findPreference(CharSequence key) {
        if (this.mPreferenceManager == null) {
            return null;
        }
        return this.mPreferenceManager.findPreference(key);
    }

    @Override // android.app.Activity
    protected void onNewIntent(Intent intent) {
        if (this.mPreferenceManager != null) {
            this.mPreferenceManager.dispatchNewIntent(intent);
        }
    }

    protected boolean hasNextButton() {
        return this.mNextButton != null;
    }

    protected Button getNextButton() {
        return this.mNextButton;
    }
}
