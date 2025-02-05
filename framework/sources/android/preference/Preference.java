package android.preference;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.AbsSavedState;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.internal.R;
import com.android.internal.util.CharSequences;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Deprecated
/* loaded from: classes3.dex */
public class Preference implements Comparable<Preference> {
    public static final int DEFAULT_ORDER = Integer.MAX_VALUE;
    protected static final float FONT_SCALE_LARGE = 1.3f;
    protected static final float FONT_SCALE_MEDIUM = 1.1f;
    private static final int MAX_LOOP_COUNT = 100;
    private static final String SAMSUNG_BASIC_INTERACTION_METADATA_NAME = "SamsungBasicInteraction";
    private static final String SAMSUNG_BASIC_INTERACTION_METADATA_VALUE_SEP10 = "SEP10";
    private static final String SAMSUNG_BASIC_INTERACTION_METADATA_VALUE_SEP11 = "SEP11";
    private boolean mBaseMethodCalled;
    private int mCategoryBGColor;
    private ColorStateList mColorPrimaryDark;
    private Context mContext;
    private Object mDefaultValue;
    private String mDependencyKey;
    private boolean mDependencyMet;
    private List<Preference> mDependents;
    private boolean mEnabled;
    private Bundle mExtras;
    private String mFragment;
    private boolean mHasSingleLineTitleAttr;
    private Drawable mIcon;
    private int mIconResId;
    private boolean mIconSpaceReserved;
    private long mId;
    private Intent mIntent;
    private boolean mIsChangedCategoryBG;
    boolean mIsDeviceDefault;
    boolean mIsDeviceDefaultDark;
    private boolean mIsMetaDataInActivity;
    private boolean mIsSummaryColorPrimaryDark;
    private String mKey;
    private int mLayoutResId;
    private OnPreferenceChangeInternalListener mListener;
    private OnPreferenceChangeListener mOnChangeListener;
    private OnPreferenceClickListener mOnClickListener;
    private int mOrder;
    private boolean mParentDependencyMet;
    private PreferenceGroup mParentGroup;
    private boolean mPersistent;
    private PreferenceDataStore mPreferenceDataStore;
    private PreferenceManager mPreferenceManager;
    private boolean mRecycleEnabled;
    private boolean mRequiresKey;
    private boolean mSelectable;
    private boolean mShouldDisableView;
    private boolean mSingleLineTitle;
    private CharSequence mSummary;
    private ColorStateList mTextColorSecondary;
    private CharSequence mTitle;
    private CharSequence mTitleDescription;
    private int mTitleRes;
    private int mWhere;
    private int mWidgetLayoutResId;

    interface OnPreferenceChangeInternalListener {
        void onPreferenceChange(Preference preference);

        void onPreferenceHierarchyChange(Preference preference);
    }

    @Deprecated
    public interface OnPreferenceChangeListener {
        boolean onPreferenceChange(Preference preference, Object obj);
    }

    @Deprecated
    public interface OnPreferenceClickListener {
        boolean onPreferenceClick(Preference preference);
    }

    public Preference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        boolean z;
        boolean z2;
        ActivityInfo ai;
        this.mOrder = Integer.MAX_VALUE;
        boolean z3 = true;
        this.mEnabled = true;
        this.mSelectable = true;
        this.mPersistent = true;
        this.mDependencyMet = true;
        this.mParentDependencyMet = true;
        this.mRecycleEnabled = true;
        this.mSingleLineTitle = true;
        this.mIsMetaDataInActivity = View.sIsSamsungBasicInteraction;
        this.mIsDeviceDefault = false;
        this.mIsDeviceDefaultDark = false;
        this.mShouldDisableView = true;
        this.mLayoutResId = R.layout.preference;
        this.mIsSummaryColorPrimaryDark = false;
        this.mIsChangedCategoryBG = false;
        this.mCategoryBGColor = 0;
        this.mWhere = 0;
        this.mContext = context;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Preference, defStyleAttr, defStyleRes);
        for (int i = a.getIndexCount() - 1; i >= 0; i--) {
            int attr = a.getIndex(i);
            switch (attr) {
                case 0:
                    this.mIconResId = a.getResourceId(attr, 0);
                    break;
                case 1:
                    this.mPersistent = a.getBoolean(attr, this.mPersistent);
                    break;
                case 2:
                    this.mEnabled = a.getBoolean(attr, true);
                    break;
                case 3:
                    this.mLayoutResId = a.getResourceId(attr, this.mLayoutResId);
                    break;
                case 4:
                    this.mTitleRes = a.getResourceId(attr, 0);
                    this.mTitle = a.getText(attr);
                    break;
                case 5:
                    this.mSelectable = a.getBoolean(attr, true);
                    break;
                case 6:
                    this.mKey = a.getString(attr);
                    break;
                case 7:
                    this.mSummary = a.getText(attr);
                    break;
                case 8:
                    this.mOrder = a.getInt(attr, this.mOrder);
                    break;
                case 9:
                    this.mWidgetLayoutResId = a.getResourceId(attr, this.mWidgetLayoutResId);
                    break;
                case 10:
                    this.mDependencyKey = a.getString(attr);
                    break;
                case 11:
                    this.mDefaultValue = onGetDefaultValue(a, attr);
                    break;
                case 12:
                    this.mShouldDisableView = a.getBoolean(attr, this.mShouldDisableView);
                    break;
                case 13:
                    this.mFragment = a.getString(attr);
                    break;
                case 14:
                    this.mRecycleEnabled = a.getBoolean(attr, this.mRecycleEnabled);
                    break;
                case 15:
                    this.mSingleLineTitle = a.getBoolean(attr, this.mSingleLineTitle);
                    this.mHasSingleLineTitleAttr = true;
                    break;
                case 16:
                    this.mIconSpaceReserved = a.getBoolean(attr, this.mIconSpaceReserved);
                    break;
            }
        }
        a.recycle();
        TypedValue outValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.parentIsDeviceDefault, outValue, true);
        if (outValue.data != 0) {
            z = true;
        } else {
            z = false;
        }
        this.mIsDeviceDefault = z;
        context.getTheme().resolveAttribute(R.attr.parentIsDeviceDefaultDark, outValue, true);
        if (outValue.data != 0) {
            z2 = true;
        } else {
            z2 = false;
        }
        this.mIsDeviceDefaultDark = z2;
        if (this.mIsDeviceDefault) {
            context.getTheme().resolveAttribute(16842808, outValue, true);
            if (outValue.resourceId > 0) {
                this.mTextColorSecondary = context.getResources().getColorStateList(outValue.resourceId);
            }
            this.mColorPrimaryDark = context.getResources().getColorStateList(R.color.tw_secondary_text_user_setting_value_material_light, context.getTheme());
        }
        Activity activity = getActivityContext(getContext());
        if (activity != null && (ai = activity.getActivityInfo()) != null && ai.metaData != null) {
            String data = ai.metaData.getString(SAMSUNG_BASIC_INTERACTION_METADATA_NAME);
            if (!SAMSUNG_BASIC_INTERACTION_METADATA_VALUE_SEP10.equals(data) && !SAMSUNG_BASIC_INTERACTION_METADATA_VALUE_SEP11.equals(data)) {
                z3 = false;
            }
            this.mIsMetaDataInActivity = z3;
        }
    }

    public Preference(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public Preference(Context context, AttributeSet attrs) {
        this(context, attrs, 16842894);
    }

    public Preference(Context context) {
        this(context, null);
    }

    protected Object onGetDefaultValue(TypedArray a, int index) {
        return null;
    }

    public void setIntent(Intent intent) {
        this.mIntent = intent;
    }

    public Intent getIntent() {
        return this.mIntent;
    }

    public void setFragment(String fragment) {
        this.mFragment = fragment;
    }

    public String getFragment() {
        return this.mFragment;
    }

    public void setPreferenceDataStore(PreferenceDataStore dataStore) {
        this.mPreferenceDataStore = dataStore;
    }

    public PreferenceDataStore getPreferenceDataStore() {
        if (this.mPreferenceDataStore != null) {
            return this.mPreferenceDataStore;
        }
        if (this.mPreferenceManager != null) {
            return this.mPreferenceManager.getPreferenceDataStore();
        }
        return null;
    }

    public Bundle getExtras() {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        return this.mExtras;
    }

    public Bundle peekExtras() {
        return this.mExtras;
    }

    public void setLayoutResource(int layoutResId) {
        if (layoutResId != this.mLayoutResId) {
            this.mRecycleEnabled = false;
        }
        this.mLayoutResId = layoutResId;
    }

    public int getLayoutResource() {
        return this.mLayoutResId;
    }

    public void setWidgetLayoutResource(int widgetLayoutResId) {
        if (widgetLayoutResId != this.mWidgetLayoutResId) {
            this.mRecycleEnabled = false;
        }
        this.mWidgetLayoutResId = widgetLayoutResId;
    }

    public int getWidgetLayoutResource() {
        return this.mWidgetLayoutResId;
    }

    public View getView(View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = onCreateView(parent);
        }
        onBindView(convertView);
        if (this.mIsDeviceDefault && (this instanceof PreferenceCategory) && (convertView instanceof TextView)) {
            TextView tempConvertView = (TextView) convertView;
            convertView.setContentDescription(tempConvertView.getText().toString() + ", " + this.mContext.getString(R.string.tw_preferencecategory_added_title));
        }
        return convertView;
    }

    protected View onCreateView(ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(this.mLayoutResId, parent, false);
        ViewGroup widgetFrame = (ViewGroup) layout.findViewById(16908312);
        if (widgetFrame != null) {
            if (this.mWidgetLayoutResId != 0) {
                layoutInflater.inflate(this.mWidgetLayoutResId, widgetFrame);
            } else {
                widgetFrame.setVisibility(8);
            }
        }
        return layout;
    }

    protected void onBindView(View view) {
        int i;
        TextView titleView = (TextView) view.findViewById(16908310);
        if (this.mIsMetaDataInActivity) {
            if (!(this instanceof PreferenceCategory)) {
                if (this.mIsChangedCategoryBG && this.mWhere != 0) {
                    view.semSetRoundedCornerColor(15, this.mCategoryBGColor);
                }
                view.semSetRoundedCorners(this.mWhere);
            } else if (this.mIsChangedCategoryBG) {
                view.setBackgroundColor(this.mCategoryBGColor);
            }
        }
        int i2 = 8;
        if (titleView != null) {
            CharSequence title = getTitle();
            getTitleDescription();
            if (!TextUtils.isEmpty(title)) {
                if (this.mIsMetaDataInActivity && !(this instanceof PreferenceCategory)) {
                    titleView.setSingleLine(false);
                    titleView.setMaxLines(5);
                    titleView.setEllipsize(TextUtils.TruncateAt.END);
                }
                titleView.lambda$setTextAsync$0(title);
                titleView.setVisibility(0);
                if (this.mHasSingleLineTitleAttr) {
                    titleView.setSingleLine(this.mSingleLineTitle);
                }
            } else if (TextUtils.isEmpty(title) && (this instanceof PreferenceCategory)) {
                titleView.setVisibility(0);
            } else {
                titleView.setVisibility(8);
            }
        }
        TextView summaryView = (TextView) view.findViewById(16908304);
        if (summaryView != null) {
            CharSequence summary = getSummary();
            if (!TextUtils.isEmpty(summary)) {
                summaryView.lambda$setTextAsync$0(summary);
                if (this.mIsDeviceDefault) {
                    if (semGetSummaryColorToColorPrimaryDark() || (this instanceof ListPreference)) {
                        summaryView.setTextColor(this.mColorPrimaryDark);
                    } else if (this.mTextColorSecondary != null) {
                        summaryView.setTextColor(this.mTextColorSecondary);
                    }
                }
                summaryView.setVisibility(0);
            } else {
                summaryView.setVisibility(8);
            }
        }
        ImageView imageView = (ImageView) view.findViewById(16908294);
        if (imageView != null) {
            if (this.mIconResId != 0 || this.mIcon != null) {
                if (this.mIcon == null) {
                    this.mIcon = getContext().getDrawable(this.mIconResId);
                }
                if (this.mIcon != null) {
                    imageView.setImageDrawable(this.mIcon);
                }
            }
            if (this.mIcon != null) {
                imageView.setVisibility(0);
            } else {
                if (!this.mIconSpaceReserved) {
                    i = 8;
                } else {
                    i = 4;
                }
                imageView.setVisibility(i);
            }
        }
        View imageFrame = view.findViewById(16908350);
        if (imageFrame != null) {
            if (this.mIcon != null) {
                imageFrame.setVisibility(0);
            } else {
                if (this.mIconSpaceReserved) {
                    i2 = 4;
                }
                imageFrame.setVisibility(i2);
            }
        }
        if (this.mShouldDisableView) {
            setEnabledStateOnViews(view, isEnabled());
        }
    }

    private void setEnabledStateOnViews(View v, boolean enabled) {
        v.setEnabled(enabled);
        if (v instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) v;
            for (int i = vg.getChildCount() - 1; i >= 0; i--) {
                setEnabledStateOnViews(vg.getChildAt(i), enabled);
            }
        }
    }

    public void setOrder(int order) {
        if (order != this.mOrder) {
            this.mOrder = order;
            notifyHierarchyChanged();
        }
    }

    public int getOrder() {
        return this.mOrder;
    }

    public void setTitle(CharSequence title) {
        if ((title == null && this.mTitle != null) || (title != null && !title.equals(this.mTitle))) {
            this.mTitleRes = 0;
            this.mTitle = title;
            notifyChanged();
        }
    }

    public void setTitle(int titleResId) {
        setTitle(this.mContext.getString(titleResId));
        this.mTitleRes = titleResId;
    }

    public int getTitleRes() {
        return this.mTitleRes;
    }

    public CharSequence getTitle() {
        return this.mTitle;
    }

    public void setIcon(Drawable icon) {
        if ((icon == null && this.mIcon != null) || (icon != null && this.mIcon != icon)) {
            this.mIcon = icon;
            notifyChanged();
        }
    }

    public void setIcon(int iconResId) {
        if (this.mIconResId != iconResId) {
            this.mIconResId = iconResId;
            setIcon(this.mContext.getDrawable(iconResId));
        }
    }

    public Drawable getIcon() {
        if (this.mIcon == null && this.mIconResId != 0) {
            this.mIcon = getContext().getDrawable(this.mIconResId);
        }
        return this.mIcon;
    }

    public CharSequence getSummary() {
        return this.mSummary;
    }

    public void setSummary(CharSequence summary) {
        if ((summary == null && this.mSummary != null) || (summary != null && !summary.equals(this.mSummary))) {
            this.mSummary = summary;
            notifyChanged();
        }
    }

    public void setSummary(int summaryResId) {
        setSummary(this.mContext.getString(summaryResId));
    }

    public void setEnabled(boolean enabled) {
        if (this.mEnabled != enabled) {
            this.mEnabled = enabled;
            notifyDependencyChange(shouldDisableDependents());
            notifyChanged();
        }
    }

    public boolean isEnabled() {
        return this.mEnabled && this.mDependencyMet && this.mParentDependencyMet;
    }

    public void setSelectable(boolean selectable) {
        if (this.mSelectable != selectable) {
            this.mSelectable = selectable;
            notifyChanged();
        }
    }

    public boolean isSelectable() {
        return this.mSelectable;
    }

    public void setShouldDisableView(boolean shouldDisableView) {
        this.mShouldDisableView = shouldDisableView;
        notifyChanged();
    }

    public boolean getShouldDisableView() {
        return this.mShouldDisableView;
    }

    public void setRecycleEnabled(boolean enabled) {
        this.mRecycleEnabled = enabled;
        notifyChanged();
    }

    public boolean isRecycleEnabled() {
        return this.mRecycleEnabled;
    }

    public void setSingleLineTitle(boolean singleLineTitle) {
        this.mHasSingleLineTitleAttr = true;
        this.mSingleLineTitle = singleLineTitle;
        notifyChanged();
    }

    public boolean isSingleLineTitle() {
        return this.mSingleLineTitle;
    }

    public void setIconSpaceReserved(boolean iconSpaceReserved) {
        this.mIconSpaceReserved = iconSpaceReserved;
        notifyChanged();
    }

    public boolean isIconSpaceReserved() {
        return this.mIconSpaceReserved;
    }

    long getId() {
        return this.mId;
    }

    protected void onClick() {
    }

    public void setKey(String key) {
        this.mKey = key;
        if (this.mRequiresKey && !hasKey()) {
            requireKey();
        }
    }

    public String getKey() {
        return this.mKey;
    }

    void requireKey() {
        if (this.mKey == null) {
            throw new IllegalStateException("Preference does not have a key assigned.");
        }
        this.mRequiresKey = true;
    }

    public boolean hasKey() {
        return !TextUtils.isEmpty(this.mKey);
    }

    public boolean isPersistent() {
        return this.mPersistent;
    }

    protected boolean shouldPersist() {
        return this.mPreferenceManager != null && isPersistent() && hasKey();
    }

    public void setPersistent(boolean persistent) {
        this.mPersistent = persistent;
    }

    protected boolean callChangeListener(Object newValue) {
        return this.mOnChangeListener == null || this.mOnChangeListener.onPreferenceChange(this, newValue);
    }

    public void setOnPreferenceChangeListener(OnPreferenceChangeListener onPreferenceChangeListener) {
        this.mOnChangeListener = onPreferenceChangeListener;
    }

    public OnPreferenceChangeListener getOnPreferenceChangeListener() {
        return this.mOnChangeListener;
    }

    public void setOnPreferenceClickListener(OnPreferenceClickListener onPreferenceClickListener) {
        this.mOnClickListener = onPreferenceClickListener;
    }

    public OnPreferenceClickListener getOnPreferenceClickListener() {
        return this.mOnClickListener;
    }

    public void performClick(PreferenceScreen preferenceScreen) {
        if (!isEnabled()) {
            return;
        }
        onClick();
        if (this.mOnClickListener != null && this.mOnClickListener.onPreferenceClick(this)) {
            return;
        }
        PreferenceManager preferenceManager = getPreferenceManager();
        if (preferenceManager != null) {
            PreferenceManager.OnPreferenceTreeClickListener listener = preferenceManager.getOnPreferenceTreeClickListener();
            if (preferenceScreen != null && listener != null && listener.onPreferenceTreeClick(preferenceScreen, this)) {
                return;
            }
        }
        if (this.mIntent != null) {
            Context context = getContext();
            context.startActivity(this.mIntent);
        }
    }

    public boolean onKey(View v, int keyCode, KeyEvent event) {
        return false;
    }

    public Context getContext() {
        return this.mContext;
    }

    public SharedPreferences getSharedPreferences() {
        if (this.mPreferenceManager == null || getPreferenceDataStore() != null) {
            return null;
        }
        return this.mPreferenceManager.getSharedPreferences();
    }

    public SharedPreferences.Editor getEditor() {
        if (this.mPreferenceManager == null || getPreferenceDataStore() != null) {
            return null;
        }
        return this.mPreferenceManager.getEditor();
    }

    public boolean shouldCommit() {
        if (this.mPreferenceManager == null) {
            return false;
        }
        return this.mPreferenceManager.shouldCommit();
    }

    @Override // java.lang.Comparable
    public int compareTo(Preference another) {
        if (this.mOrder != another.mOrder) {
            return this.mOrder - another.mOrder;
        }
        if (this.mTitle == another.mTitle) {
            return 0;
        }
        if (this.mTitle == null) {
            return 1;
        }
        if (another.mTitle == null) {
            return -1;
        }
        return CharSequences.compareToIgnoreCase(this.mTitle, another.mTitle);
    }

    final void setOnPreferenceChangeInternalListener(OnPreferenceChangeInternalListener listener) {
        this.mListener = listener;
    }

    protected void notifyChanged() {
        if (this.mListener != null) {
            this.mListener.onPreferenceChange(this);
        }
    }

    protected void notifyHierarchyChanged() {
        if (this.mListener != null) {
            this.mListener.onPreferenceHierarchyChange(this);
        }
    }

    public PreferenceManager getPreferenceManager() {
        return this.mPreferenceManager;
    }

    protected void onAttachedToHierarchy(PreferenceManager preferenceManager) {
        this.mPreferenceManager = preferenceManager;
        this.mId = preferenceManager.getNextId();
        dispatchSetInitialValue();
    }

    protected void onAttachedToActivity() {
        registerDependency();
    }

    void assignParent(PreferenceGroup parentGroup) {
        this.mParentGroup = parentGroup;
    }

    private void registerDependency() {
        if (TextUtils.isEmpty(this.mDependencyKey)) {
            return;
        }
        Preference preference = findPreferenceInHierarchy(this.mDependencyKey);
        if (preference != null) {
            preference.registerDependent(this);
            return;
        }
        throw new IllegalStateException("Dependency \"" + this.mDependencyKey + "\" not found for preference \"" + this.mKey + "\" (title: \"" + ((Object) this.mTitle) + "\"");
    }

    private void unregisterDependency() {
        Preference oldDependency;
        if (this.mDependencyKey != null && (oldDependency = findPreferenceInHierarchy(this.mDependencyKey)) != null) {
            oldDependency.unregisterDependent(this);
        }
    }

    protected Preference findPreferenceInHierarchy(String key) {
        if (TextUtils.isEmpty(key) || this.mPreferenceManager == null) {
            return null;
        }
        return this.mPreferenceManager.findPreference(key);
    }

    private void registerDependent(Preference dependent) {
        if (this.mDependents == null) {
            this.mDependents = new ArrayList();
        }
        this.mDependents.add(dependent);
        dependent.onDependencyChanged(this, shouldDisableDependents());
    }

    private void unregisterDependent(Preference dependent) {
        if (this.mDependents != null) {
            this.mDependents.remove(dependent);
        }
    }

    public void notifyDependencyChange(boolean disableDependents) {
        List<Preference> dependents = this.mDependents;
        if (dependents == null) {
            return;
        }
        int dependentsCount = dependents.size();
        for (int i = 0; i < dependentsCount; i++) {
            dependents.get(i).onDependencyChanged(this, disableDependents);
        }
    }

    public void onDependencyChanged(Preference dependency, boolean disableDependent) {
        if (this.mDependencyMet == disableDependent) {
            this.mDependencyMet = !disableDependent;
            notifyDependencyChange(shouldDisableDependents());
            notifyChanged();
        }
    }

    public void onParentChanged(Preference parent, boolean disableChild) {
        if (this.mParentDependencyMet == disableChild) {
            this.mParentDependencyMet = !disableChild;
            notifyDependencyChange(shouldDisableDependents());
            notifyChanged();
        }
    }

    public boolean shouldDisableDependents() {
        return !isEnabled();
    }

    public void setDependency(String dependencyKey) {
        unregisterDependency();
        this.mDependencyKey = dependencyKey;
        registerDependency();
    }

    public String getDependency() {
        return this.mDependencyKey;
    }

    public PreferenceGroup getParent() {
        return this.mParentGroup;
    }

    protected void onPrepareForRemoval() {
        unregisterDependency();
    }

    public void setDefaultValue(Object defaultValue) {
        this.mDefaultValue = defaultValue;
    }

    private void dispatchSetInitialValue() {
        if (getPreferenceDataStore() != null) {
            onSetInitialValue(true, this.mDefaultValue);
            return;
        }
        boolean shouldPersist = shouldPersist();
        if (!shouldPersist || !getSharedPreferences().contains(this.mKey)) {
            if (this.mDefaultValue != null) {
                onSetInitialValue(false, this.mDefaultValue);
                return;
            }
            return;
        }
        onSetInitialValue(true, null);
    }

    protected void onSetInitialValue(boolean restorePersistedValue, Object defaultValue) {
    }

    private void tryCommit(SharedPreferences.Editor editor) {
        if (this.mPreferenceManager.shouldCommit()) {
            try {
                editor.apply();
            } catch (AbstractMethodError e) {
                editor.commit();
            }
        }
    }

    protected boolean persistString(String value) {
        if (!shouldPersist()) {
            return false;
        }
        if (TextUtils.equals(value, getPersistedString(null))) {
            return true;
        }
        PreferenceDataStore dataStore = getPreferenceDataStore();
        if (dataStore != null) {
            dataStore.putString(this.mKey, value);
        } else {
            SharedPreferences.Editor editor = this.mPreferenceManager.getEditor();
            editor.putString(this.mKey, value);
            tryCommit(editor);
        }
        return true;
    }

    protected String getPersistedString(String defaultReturnValue) {
        if (!shouldPersist()) {
            return defaultReturnValue;
        }
        PreferenceDataStore dataStore = getPreferenceDataStore();
        if (dataStore != null) {
            return dataStore.getString(this.mKey, defaultReturnValue);
        }
        return this.mPreferenceManager.getSharedPreferences().getString(this.mKey, defaultReturnValue);
    }

    public boolean persistStringSet(Set<String> values) {
        if (!shouldPersist()) {
            return false;
        }
        if (values.equals(getPersistedStringSet(null))) {
            return true;
        }
        PreferenceDataStore dataStore = getPreferenceDataStore();
        if (dataStore != null) {
            dataStore.putStringSet(this.mKey, values);
        } else {
            SharedPreferences.Editor editor = this.mPreferenceManager.getEditor();
            editor.putStringSet(this.mKey, values);
            tryCommit(editor);
        }
        return true;
    }

    public Set<String> getPersistedStringSet(Set<String> defaultReturnValue) {
        if (!shouldPersist()) {
            return defaultReturnValue;
        }
        PreferenceDataStore dataStore = getPreferenceDataStore();
        if (dataStore != null) {
            return dataStore.getStringSet(this.mKey, defaultReturnValue);
        }
        return this.mPreferenceManager.getSharedPreferences().getStringSet(this.mKey, defaultReturnValue);
    }

    protected boolean persistInt(int value) {
        if (!shouldPersist()) {
            return false;
        }
        if (value == getPersistedInt(~value)) {
            return true;
        }
        PreferenceDataStore dataStore = getPreferenceDataStore();
        if (dataStore != null) {
            dataStore.putInt(this.mKey, value);
        } else {
            SharedPreferences.Editor editor = this.mPreferenceManager.getEditor();
            editor.putInt(this.mKey, value);
            tryCommit(editor);
        }
        return true;
    }

    protected int getPersistedInt(int defaultReturnValue) {
        if (!shouldPersist()) {
            return defaultReturnValue;
        }
        PreferenceDataStore dataStore = getPreferenceDataStore();
        if (dataStore != null) {
            return dataStore.getInt(this.mKey, defaultReturnValue);
        }
        return this.mPreferenceManager.getSharedPreferences().getInt(this.mKey, defaultReturnValue);
    }

    protected boolean persistFloat(float value) {
        if (!shouldPersist()) {
            return false;
        }
        if (value == getPersistedFloat(Float.NaN)) {
            return true;
        }
        PreferenceDataStore dataStore = getPreferenceDataStore();
        if (dataStore != null) {
            dataStore.putFloat(this.mKey, value);
        } else {
            SharedPreferences.Editor editor = this.mPreferenceManager.getEditor();
            editor.putFloat(this.mKey, value);
            tryCommit(editor);
        }
        return true;
    }

    protected float getPersistedFloat(float defaultReturnValue) {
        if (!shouldPersist()) {
            return defaultReturnValue;
        }
        PreferenceDataStore dataStore = getPreferenceDataStore();
        if (dataStore != null) {
            return dataStore.getFloat(this.mKey, defaultReturnValue);
        }
        return this.mPreferenceManager.getSharedPreferences().getFloat(this.mKey, defaultReturnValue);
    }

    protected boolean persistLong(long value) {
        if (!shouldPersist()) {
            return false;
        }
        if (value == getPersistedLong(~value)) {
            return true;
        }
        PreferenceDataStore dataStore = getPreferenceDataStore();
        if (dataStore != null) {
            dataStore.putLong(this.mKey, value);
        } else {
            SharedPreferences.Editor editor = this.mPreferenceManager.getEditor();
            editor.putLong(this.mKey, value);
            tryCommit(editor);
        }
        return true;
    }

    protected long getPersistedLong(long defaultReturnValue) {
        if (!shouldPersist()) {
            return defaultReturnValue;
        }
        PreferenceDataStore dataStore = getPreferenceDataStore();
        if (dataStore != null) {
            return dataStore.getLong(this.mKey, defaultReturnValue);
        }
        return this.mPreferenceManager.getSharedPreferences().getLong(this.mKey, defaultReturnValue);
    }

    protected boolean persistBoolean(boolean value) {
        if (!shouldPersist()) {
            return false;
        }
        if (value == getPersistedBoolean(!value)) {
            return true;
        }
        PreferenceDataStore dataStore = getPreferenceDataStore();
        if (dataStore != null) {
            dataStore.putBoolean(this.mKey, value);
        } else {
            SharedPreferences.Editor editor = this.mPreferenceManager.getEditor();
            editor.putBoolean(this.mKey, value);
            tryCommit(editor);
        }
        return true;
    }

    protected boolean getPersistedBoolean(boolean defaultReturnValue) {
        if (!shouldPersist()) {
            return defaultReturnValue;
        }
        PreferenceDataStore dataStore = getPreferenceDataStore();
        if (dataStore != null) {
            return dataStore.getBoolean(this.mKey, defaultReturnValue);
        }
        return this.mPreferenceManager.getSharedPreferences().getBoolean(this.mKey, defaultReturnValue);
    }

    public String toString() {
        return getFilterableStringBuilder().toString();
    }

    StringBuilder getFilterableStringBuilder() {
        StringBuilder sb = new StringBuilder();
        CharSequence title = getTitle();
        if (!TextUtils.isEmpty(title)) {
            sb.append(title).append(' ');
        }
        CharSequence summary = getSummary();
        if (!TextUtils.isEmpty(summary)) {
            sb.append(summary).append(' ');
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);
        }
        return sb;
    }

    public void saveHierarchyState(Bundle container) {
        dispatchSaveInstanceState(container);
    }

    void dispatchSaveInstanceState(Bundle container) {
        if (hasKey()) {
            this.mBaseMethodCalled = false;
            Parcelable state = onSaveInstanceState();
            if (!this.mBaseMethodCalled) {
                throw new IllegalStateException("Derived class did not call super.onSaveInstanceState()");
            }
            if (state != null) {
                container.putParcelable(this.mKey, state);
            }
        }
    }

    protected Parcelable onSaveInstanceState() {
        this.mBaseMethodCalled = true;
        return BaseSavedState.EMPTY_STATE;
    }

    public void restoreHierarchyState(Bundle container) {
        dispatchRestoreInstanceState(container);
    }

    void dispatchRestoreInstanceState(Bundle container) {
        Parcelable state;
        if (hasKey() && (state = container.getParcelable(this.mKey)) != null) {
            this.mBaseMethodCalled = false;
            onRestoreInstanceState(state);
            if (!this.mBaseMethodCalled) {
                throw new IllegalStateException("Derived class did not call super.onRestoreInstanceState()");
            }
        }
    }

    protected void onRestoreInstanceState(Parcelable state) {
        this.mBaseMethodCalled = true;
        if (state != BaseSavedState.EMPTY_STATE && state != null) {
            throw new IllegalArgumentException("Wrong state class -- expecting Preference State");
        }
    }

    @Deprecated
    public static class BaseSavedState extends AbsSavedState {
        public static final Parcelable.Creator<BaseSavedState> CREATOR = new Parcelable.Creator<BaseSavedState>() { // from class: android.preference.Preference.BaseSavedState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public BaseSavedState createFromParcel(Parcel in) {
                return new BaseSavedState(in);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public BaseSavedState[] newArray(int size) {
                return new BaseSavedState[size];
            }
        };

        public BaseSavedState(Parcel source) {
            super(source);
        }

        public BaseSavedState(Parcelable superState) {
            super(superState);
        }
    }

    public void setTitleDescription(CharSequence titleDescription) {
        if ((titleDescription == null && this.mTitleDescription != null) || (titleDescription != null && !titleDescription.equals(this.mTitle))) {
            this.mTitleDescription = titleDescription;
            notifyChanged();
        }
    }

    public void setTitleDescription(int titleDescriptionResId) {
        setTitleDescription(this.mContext.getString(titleDescriptionResId));
    }

    public CharSequence getTitleDescription() {
        return this.mTitleDescription;
    }

    public void semSetSummaryColorToColorPrimaryDark(boolean color) {
        this.mIsSummaryColorPrimaryDark = color;
    }

    private boolean semGetSummaryColorToColorPrimaryDark() {
        return this.mIsSummaryColorPrimaryDark;
    }

    boolean hasRTL() {
        return this.mContext.getApplicationInfo().hasRtlSupport();
    }

    boolean isRTL() {
        Configuration config = this.mContext.getResources().getConfiguration();
        return config.getLayoutDirection() == 1;
    }

    void setRoundCorner(int where) {
        this.mWhere = where;
    }

    void setCategoryBGColor(int color) {
        this.mIsChangedCategoryBG = true;
        this.mCategoryBGColor = color;
    }

    private Activity getActivityContext(Context context) {
        Activity activity = null;
        Context tempContext = context;
        for (int count = 0; activity == null && tempContext != null && count < 100; count++) {
            if (tempContext instanceof Activity) {
                activity = (Activity) tempContext;
            } else {
                tempContext = tempContext instanceof ContextWrapper ? ((ContextWrapper) tempContext).getBaseContext() : null;
            }
        }
        return activity;
    }
}
