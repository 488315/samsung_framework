package android.widget;

import android.app.PendingIntent;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.Settings;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.CollapsibleActionView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewRootImpl;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import android.widget.AdapterView;
import android.widget.SearchView;
import android.widget.TextView;
import com.android.internal.R;
import com.samsung.android.rune.ViewRune;
import java.util.WeakHashMap;

/* loaded from: classes4.dex */
public class SearchView extends LinearLayout implements CollapsibleActionView {
    private static final boolean DBG = false;
    private static final String IME_OPTION_NO_MICROPHONE = "nm";
    private static final String LOG_TAG = "SearchView";
    private static final String SEM_AUTHORITY_SVI_APP = "com.samsung.android.honeyboard.provider.VoiceLanguageListProvider";
    private static final String SEM_KEY_SVI_APP_LOCALE = "is_honeyvoice_locale_supported";
    private static final String SEM_SVI_ACTION = "samsung.honeyboard.honeyvoice.action.RECOGNIZE_SPEECH";
    private static final String SEM_SVI_PACKAGE = "com.samsung.android.honeyboard";
    private static final int SEM_SVI_VERSION_SUPPORTING_SEARCH_QUERY = 220002001;
    private static final int SHOW_IME_WITH_HARDKEY = 1;
    private static final int TW_SEARCH_ICON_RES_ID = 17304542;
    private Bundle mAppSearchData;
    private final ImageView mBackButton;
    private boolean mClearingFocus;
    private final ImageView mCloseButton;
    private final ImageView mCollapsedIcon;
    private int mCollapsedImeOptions;
    private Context mContext;
    private final CharSequence mDefaultQueryHint;
    private final View mDropDownAnchor;
    private boolean mExpandedInActionView;
    private final ImageView mGoButton;
    private boolean mIconified;
    private boolean mIconifiedByDefault;
    private boolean mIsNight;
    private boolean mIsPenSupport;
    private int mMaxWidth;
    private final ImageView mMoreButton;
    private CharSequence mOldQueryText;
    private final View.OnClickListener mOnClickListener;
    private OnCloseListener mOnCloseListener;
    private final TextView.OnEditorActionListener mOnEditorActionListener;
    private final AdapterView.OnItemClickListener mOnItemClickListener;
    private final AdapterView.OnItemSelectedListener mOnItemSelectedListener;
    private OnQueryTextListener mOnQueryChangeListener;
    private View.OnFocusChangeListener mOnQueryTextFocusChangeListener;
    private View.OnClickListener mOnSearchClickListener;
    private OnSuggestionListener mOnSuggestionListener;
    private final WeakHashMap<String, Drawable.ConstantState> mOutsideDrawablesCache;
    private CharSequence mQueryHint;
    private boolean mQueryRefinement;
    private Runnable mReleaseCursorRunnable;
    private Intent mSVISearchIntent;
    private final ImageView mSearchButton;
    private final View mSearchEditFrame;
    private final Drawable mSearchHintIcon;
    private final int mSearchIconResId;
    private final View mSearchPlate;
    private final SearchAutoComplete mSearchSrcTextView;
    private Rect mSearchSrcTextViewBounds;
    private Rect mSearchSrtTextViewBoundsExpanded;
    private SearchableInfo mSearchable;
    private final View mSubmitArea;
    private boolean mSubmitButtonEnabled;
    private final int mSuggestionCommitIconResId;
    private final int mSuggestionRowLayout;
    private CursorAdapter mSuggestionsAdapter;
    private int[] mTemp;
    private int[] mTemp2;
    View.OnKeyListener mTextKeyListener;
    private TextWatcher mTextWatcher;
    private boolean mThemeIsDeviceDefault;
    private UpdatableTouchDelegate mTouchDelegate;
    private Runnable mUpdateDrawableStateRunnable;
    private boolean mUseSVI;
    private CharSequence mUserQuery;
    private final Intent mVoiceAppSearchIntent;
    private final ImageView mVoiceButton;
    private boolean mVoiceButtonEnabled;
    private final Intent mVoiceWebSearchIntent;

    /* loaded from: classes4.dex */
    public interface OnCloseListener {
        boolean onClose();
    }

    /* loaded from: classes4.dex */
    public interface OnQueryTextListener {
        boolean onQueryTextChange(String str);

        boolean onQueryTextSubmit(String str);
    }

    /* loaded from: classes4.dex */
    public interface OnSuggestionListener {
        boolean onSuggestionClick(int i);

        boolean onSuggestionSelect(int i);
    }

    /* loaded from: classes4.dex */
    public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<SearchView> {
        private int mIconifiedByDefaultId;
        private int mIconifiedId;
        private int mMaxWidthId;
        private boolean mPropertiesMapped = false;
        private int mQueryHintId;
        private int mQueryId;

        @Override // android.view.inspector.InspectionCompanion
        public void mapProperties(PropertyMapper propertyMapper) {
            this.mIconifiedId = propertyMapper.mapBoolean("iconified", 0);
            this.mIconifiedByDefaultId = propertyMapper.mapBoolean("iconifiedByDefault", 16843514);
            this.mMaxWidthId = propertyMapper.mapInt("maxWidth", 16843039);
            this.mQueryId = propertyMapper.mapObject("query", 0);
            this.mQueryHintId = propertyMapper.mapObject("queryHint", 16843608);
            this.mPropertiesMapped = true;
        }

        @Override // android.view.inspector.InspectionCompanion
        public void readProperties(SearchView node, PropertyReader propertyReader) {
            if (!this.mPropertiesMapped) {
                throw new InspectionCompanion.UninitializedPropertyMapException();
            }
            propertyReader.readBoolean(this.mIconifiedId, node.isIconified());
            propertyReader.readBoolean(this.mIconifiedByDefaultId, node.isIconifiedByDefault());
            propertyReader.readInt(this.mMaxWidthId, node.getMaxWidth());
            propertyReader.readObject(this.mQueryId, node.getQuery());
            propertyReader.readObject(this.mQueryHintId, node.getQueryHint());
        }
    }

    public AutoCompleteTextView semGetAutoCompleteView() {
        return this.mSearchSrcTextView;
    }

    public void semSetUpButtonIcon(Drawable drawable) {
        ImageView imageView = this.mBackButton;
        if (imageView != null) {
            imageView.lambda$setImageURIAsync$2(drawable);
        }
    }

    public void semSetOverflowMenuButtonIcon(Drawable drawable) {
        ImageView imageView = this.mMoreButton;
        if (imageView != null) {
            imageView.lambda$setImageURIAsync$2(drawable);
        }
    }

    public void semSetUpButtonVisibility(int visibility) {
        ImageView imageView = this.mBackButton;
        if (imageView != null) {
            imageView.setVisibility(visibility);
        }
    }

    public void semSetOverflowMenuButtonVisibility(int visibility) {
        ImageView imageView = this.mMoreButton;
        if (imageView != null) {
            imageView.setVisibility(visibility);
        }
    }

    public void semSetOnUpButtonClickListener(View.OnClickListener listener) {
        ImageView imageView = this.mBackButton;
        if (imageView != null) {
            imageView.setOnClickListener(listener);
        }
    }

    public void semSetOnOverflowMenuButtonClickListener(View.OnClickListener listener) {
        ImageView imageView = this.mMoreButton;
        if (imageView != null) {
            imageView.setOnClickListener(listener);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: android.widget.SearchView$1 */
    /* loaded from: classes4.dex */
    public class AnonymousClass1 implements Runnable {
        AnonymousClass1() {
        }

        @Override // java.lang.Runnable
        public void run() {
            SearchView.this.updateFocusedState();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: android.widget.SearchView$2 */
    /* loaded from: classes4.dex */
    public class AnonymousClass2 implements Runnable {
        AnonymousClass2() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (SearchView.this.mSuggestionsAdapter != null && (SearchView.this.mSuggestionsAdapter instanceof SuggestionsAdapter)) {
                SearchView.this.mSuggestionsAdapter.changeCursor(null);
            }
        }
    }

    public SearchView(Context context) {
        this(context, null);
    }

    public SearchView(Context context, AttributeSet attrs) {
        this(context, attrs, 16843904);
    }

    public SearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public SearchView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        boolean z;
        boolean z2;
        ImageView imageView;
        ImageView imageView2;
        this.mSearchSrcTextViewBounds = new Rect();
        this.mSearchSrtTextViewBoundsExpanded = new Rect();
        this.mTemp = new int[2];
        this.mTemp2 = new int[2];
        this.mThemeIsDeviceDefault = false;
        this.mIsPenSupport = false;
        this.mUseSVI = false;
        this.mSVISearchIntent = null;
        this.mUpdateDrawableStateRunnable = new Runnable() { // from class: android.widget.SearchView.1
            AnonymousClass1() {
            }

            @Override // java.lang.Runnable
            public void run() {
                SearchView.this.updateFocusedState();
            }
        };
        this.mReleaseCursorRunnable = new Runnable() { // from class: android.widget.SearchView.2
            AnonymousClass2() {
            }

            @Override // java.lang.Runnable
            public void run() {
                if (SearchView.this.mSuggestionsAdapter != null && (SearchView.this.mSuggestionsAdapter instanceof SuggestionsAdapter)) {
                    SearchView.this.mSuggestionsAdapter.changeCursor(null);
                }
            }
        };
        this.mOutsideDrawablesCache = new WeakHashMap<>();
        AnonymousClass5 anonymousClass5 = new View.OnClickListener() { // from class: android.widget.SearchView.5
            AnonymousClass5() {
            }

            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (v == SearchView.this.mSearchButton) {
                    SearchView.this.onSearchClicked();
                    return;
                }
                if (v == SearchView.this.mCloseButton) {
                    SearchView.this.onCloseClicked();
                    return;
                }
                if (v == SearchView.this.mGoButton) {
                    SearchView.this.onSubmitQuery();
                } else if (v == SearchView.this.mVoiceButton) {
                    SearchView.this.onVoiceClicked();
                } else if (v == SearchView.this.mSearchSrcTextView) {
                    SearchView.this.forceSuggestionQuery();
                }
            }
        };
        this.mOnClickListener = anonymousClass5;
        this.mTextKeyListener = new View.OnKeyListener() { // from class: android.widget.SearchView.6
            AnonymousClass6() {
            }

            @Override // android.view.View.OnKeyListener
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                SearchableInfo.ActionKeyInfo actionKey;
                boolean isFolderTypeFeature = SearchView.this.getContext().getPackageManager().hasSystemFeature(PackageManager.SEM_FEATURE_FOLDER_TYPE);
                if (isFolderTypeFeature) {
                    InputMethodManager imm = (InputMethodManager) SearchView.this.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (keyCode == 23) {
                        imm.viewClicked(v);
                        imm.showSoftInput(v, 1);
                    }
                }
                if (SearchView.this.mSearchable == null) {
                    return false;
                }
                if (SearchView.this.mSearchSrcTextView.isPopupShowing() && SearchView.this.mSearchSrcTextView.getListSelection() != -1) {
                    return SearchView.this.onSuggestionsKey(v, keyCode, event);
                }
                if (!SearchView.this.mSearchSrcTextView.isEmpty() && event.hasNoModifiers()) {
                    if (event.getAction() == 1 && (keyCode == 66 || keyCode == 160)) {
                        v.cancelLongPress();
                        SearchView searchView = SearchView.this;
                        searchView.launchQuerySearch(0, null, searchView.mSearchSrcTextView.getText().toString());
                        return true;
                    }
                    if (event.getAction() == 0 && (actionKey = SearchView.this.mSearchable.findActionKey(keyCode)) != null && actionKey.getQueryActionMsg() != null) {
                        SearchView.this.launchQuerySearch(keyCode, actionKey.getQueryActionMsg(), SearchView.this.mSearchSrcTextView.getText().toString());
                        return true;
                    }
                }
                return false;
            }
        };
        AnonymousClass7 anonymousClass7 = new TextView.OnEditorActionListener() { // from class: android.widget.SearchView.7
            AnonymousClass7() {
            }

            @Override // android.widget.TextView.OnEditorActionListener
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                SearchView.this.onSubmitQuery();
                return true;
            }
        };
        this.mOnEditorActionListener = anonymousClass7;
        AnonymousClass8 anonymousClass8 = new AdapterView.OnItemClickListener() { // from class: android.widget.SearchView.8
            AnonymousClass8() {
            }

            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SearchView.this.onItemClicked(position, 0, null);
            }
        };
        this.mOnItemClickListener = anonymousClass8;
        AnonymousClass9 anonymousClass9 = new AdapterView.OnItemSelectedListener() { // from class: android.widget.SearchView.9
            AnonymousClass9() {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SearchView.this.onItemSelected(position);
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
        this.mOnItemSelectedListener = anonymousClass9;
        this.mTextWatcher = new TextWatcher() { // from class: android.widget.SearchView.10
            AnonymousClass10() {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int start, int before, int after) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int start, int before, int after) {
                SearchView.this.onTextChanged(s);
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s) {
            }
        };
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SearchView, defStyleAttr, defStyleRes);
        saveAttributeDataForStyleable(context, R.styleable.SearchView, attrs, a, defStyleAttr, defStyleRes);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        int layoutResId = a.getResourceId(0, R.layout.search_view);
        inflater.inflate(layoutResId, (ViewGroup) this, true);
        TypedValue themeValue = new TypedValue();
        this.mContext = context;
        context.getTheme().resolveAttribute(R.attr.parentIsDeviceDefault, themeValue, false);
        if (themeValue.data == 0) {
            z = false;
        } else {
            z = true;
        }
        this.mThemeIsDeviceDefault = z;
        if ((getResources().getConfiguration().uiMode & 48) != 32) {
            z2 = false;
        } else {
            z2 = true;
        }
        this.mIsNight = z2;
        SearchAutoComplete searchAutoComplete = (SearchAutoComplete) findViewById(R.id.search_src_text);
        this.mSearchSrcTextView = searchAutoComplete;
        searchAutoComplete.setSearchView(this);
        this.mSearchEditFrame = findViewById(R.id.search_edit_frame);
        View findViewById = findViewById(R.id.search_plate);
        this.mSearchPlate = findViewById;
        View findViewById2 = findViewById(R.id.submit_area);
        this.mSubmitArea = findViewById2;
        ImageView imageView3 = (ImageView) findViewById(R.id.search_button);
        this.mSearchButton = imageView3;
        ImageView imageView4 = (ImageView) findViewById(R.id.search_go_btn);
        this.mGoButton = imageView4;
        ImageView imageView5 = (ImageView) findViewById(R.id.search_close_btn);
        this.mCloseButton = imageView5;
        ImageView imageView6 = (ImageView) findViewById(R.id.search_voice_btn);
        this.mVoiceButton = imageView6;
        ImageView imageView7 = (ImageView) findViewById(R.id.search_mag_icon);
        this.mCollapsedIcon = imageView7;
        ImageView imageView8 = (ImageView) findViewById(R.id.search_more_btn);
        this.mMoreButton = imageView8;
        ImageView imageView9 = (ImageView) findViewById(R.id.search_back_btn);
        this.mBackButton = imageView9;
        findViewById.setBackground(a.getDrawable(12));
        findViewById2.setBackground(a.getDrawable(13));
        int resourceId = a.getResourceId(8, 0);
        this.mSearchIconResId = resourceId;
        imageView3.setImageResource(resourceId);
        imageView4.lambda$setImageURIAsync$2(a.getDrawable(7));
        imageView5.lambda$setImageURIAsync$2(a.getDrawable(6));
        imageView6.lambda$setImageURIAsync$2(a.getDrawable(9));
        imageView7.lambda$setImageURIAsync$2(a.getDrawable(8));
        if (a.hasValueOrEmpty(14)) {
            this.mSearchHintIcon = a.getDrawable(14);
        } else {
            this.mSearchHintIcon = a.getDrawable(8);
        }
        this.mSuggestionRowLayout = a.getResourceId(11, R.layout.search_dropdown_item_icons_2line);
        this.mSuggestionCommitIconResId = a.getResourceId(10, 0);
        imageView3.setOnClickListener(anonymousClass5);
        imageView5.setOnClickListener(anonymousClass5);
        imageView4.setOnClickListener(anonymousClass5);
        imageView6.setOnClickListener(anonymousClass5);
        searchAutoComplete.setOnClickListener(anonymousClass5);
        searchAutoComplete.addTextChangedListener(this.mTextWatcher);
        searchAutoComplete.setOnEditorActionListener(anonymousClass7);
        searchAutoComplete.setOnItemClickListener(anonymousClass8);
        searchAutoComplete.setOnItemSelectedListener(anonymousClass9);
        searchAutoComplete.setOnKeyListener(this.mTextKeyListener);
        imageView3.setTooltipText(imageView3.getContentDescription());
        imageView5.setTooltipText(imageView5.getContentDescription());
        imageView4.setTooltipText(imageView4.getContentDescription());
        imageView6.setTooltipText(imageView6.getContentDescription());
        if (layoutResId != 17367491) {
            imageView = imageView8;
            imageView2 = imageView9;
        } else {
            CharSequence contentDescription = imageView8.getContentDescription();
            imageView = imageView8;
            imageView.setTooltipText(contentDescription);
            imageView2 = imageView9;
            imageView2.setTooltipText(imageView9.getContentDescription());
        }
        searchAutoComplete.setOnFocusChangeListener(new View.OnFocusChangeListener() { // from class: android.widget.SearchView.3
            AnonymousClass3() {
            }

            @Override // android.view.View.OnFocusChangeListener
            public void onFocusChange(View v, boolean hasFocus) {
                if (SearchView.this.mOnQueryTextFocusChangeListener != null) {
                    SearchView.this.mOnQueryTextFocusChangeListener.onFocusChange(SearchView.this, hasFocus);
                }
            }
        });
        setIconifiedByDefault(a.getBoolean(4, true));
        int maxWidth = a.getDimensionPixelSize(1, -1);
        if (maxWidth != -1) {
            setMaxWidth(maxWidth);
        }
        this.mDefaultQueryHint = a.getText(15);
        this.mQueryHint = a.getText(5);
        int imeOptions = a.getInt(3, -1);
        if (imeOptions != -1) {
            setImeOptions(imeOptions);
        }
        int inputType = a.getInt(2, -1);
        if (inputType != -1) {
            setInputType(inputType);
        }
        if (getFocusable() == 16) {
            setFocusable(1);
        }
        if (this.mThemeIsDeviceDefault) {
            if (resourceId == 0) {
                imageView7.lambda$setImageURIAsync$2(getContext().getResources().getDrawable(17304542));
                imageView3.lambda$setImageURIAsync$2(getContext().getResources().getDrawable(17304542));
            }
            Resources resources = this.mContext.getResources();
            if (!this.mIsNight) {
                if (findViewById.getBackground() == null) {
                    searchAutoComplete.setTextColor(resources.getColor(R.color.sem_search_view_no_background_text_color_light));
                    searchAutoComplete.setHintTextColor(resources.getColor(R.color.sem_search_view_no_background_hint_text_color_light));
                    imageView4.setColorFilter(resources.getColor(R.color.sem_search_view_no_background_icon_color_light));
                    imageView5.setColorFilter(resources.getColor(R.color.sem_search_view_no_background_icon_color_light));
                    imageView6.setColorFilter(resources.getColor(R.color.sem_search_view_no_background_icon_color_light));
                    imageView.setColorFilter(resources.getColor(R.color.sem_search_view_no_background_icon_color_light));
                    imageView2.setColorFilter(resources.getColor(R.color.sem_search_view_no_background_icon_color_light));
                    imageView3.setColorFilter(resources.getColor(R.color.sem_search_view_no_background_icon_color_light));
                } else {
                    searchAutoComplete.setTextColor(resources.getColor(R.color.tw_searchview_text_material));
                    searchAutoComplete.setHintTextColor(resources.getColor(R.color.tw_searchview_hint_text_material));
                    imageView4.setColorFilter(resources.getColor(R.color.tw_searchview_search_icon_color_material));
                    imageView5.setColorFilter(resources.getColor(R.color.tw_searchview_search_icon_color_material));
                    imageView6.setColorFilter(resources.getColor(R.color.tw_searchview_search_icon_color_material));
                    imageView.setColorFilter(resources.getColor(R.color.tw_searchview_search_icon_color_material));
                    imageView2.setColorFilter(resources.getColor(R.color.tw_searchview_search_icon_color_material));
                    imageView3.setColorFilter(resources.getColor(R.color.tw_searchview_search_icon_color_material));
                }
            } else if (findViewById.getBackground() == null) {
                searchAutoComplete.setTextColor(resources.getColor(R.color.sem_search_view_no_background_text_color_dark));
                searchAutoComplete.setHintTextColor(resources.getColor(R.color.sem_search_view_no_background_hint_text_color_dark));
                imageView4.setColorFilter(resources.getColor(R.color.sem_search_view_no_background_icon_color_dark));
                imageView5.setColorFilter(resources.getColor(R.color.sem_search_view_no_background_icon_color_dark));
                imageView6.setColorFilter(resources.getColor(R.color.sem_search_view_no_background_icon_color_dark));
                imageView.setColorFilter(resources.getColor(R.color.sem_search_view_no_background_icon_color_dark));
                imageView2.setColorFilter(resources.getColor(R.color.sem_search_view_no_background_icon_color_dark));
                imageView3.setColorFilter(resources.getColor(R.color.sem_search_view_no_background_icon_color_dark));
            } else {
                searchAutoComplete.setTextColor(resources.getColor(R.color.tw_searchview_text_material_dark));
                searchAutoComplete.setHintTextColor(resources.getColor(R.color.tw_searchview_hint_text_material_dark));
                imageView4.setColorFilter(resources.getColor(R.color.tw_searchview_search_icon_color_material_dark));
                imageView5.setColorFilter(resources.getColor(R.color.tw_searchview_search_icon_color_material_dark));
                imageView6.setColorFilter(resources.getColor(R.color.tw_searchview_search_icon_color_material_dark));
                imageView.setColorFilter(resources.getColor(R.color.tw_searchview_search_icon_color_material_dark));
                imageView2.setColorFilter(resources.getColor(R.color.tw_searchview_search_icon_color_material_dark));
                imageView3.setColorFilter(resources.getColor(R.color.tw_searchview_search_icon_color_material_dark));
            }
        }
        a.recycle();
        Intent intent = new Intent(RecognizerIntent.ACTION_WEB_SEARCH);
        this.mVoiceWebSearchIntent = intent;
        intent.addFlags(268435456);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
        Intent intent2 = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        this.mVoiceAppSearchIntent = intent2;
        intent2.addFlags(268435456);
        View findViewById3 = findViewById(searchAutoComplete.getDropDownAnchor());
        this.mDropDownAnchor = findViewById3;
        if (findViewById3 != null) {
            findViewById3.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: android.widget.SearchView.4
                AnonymousClass4() {
                }

                @Override // android.view.View.OnLayoutChangeListener
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    SearchView.this.adjustDropDownSizeAndPosition();
                }
            });
        }
        updateViewsVisibility(this.mIconifiedByDefault);
        updateQueryHint();
        if (ViewRune.WIDGET_SEARCHVIEW_USE_SVI) {
            Intent intent3 = new Intent(SEM_SVI_ACTION);
            this.mSVISearchIntent = intent3;
            intent3.addFlags(268435456);
        }
        if (this.mThemeIsDeviceDefault) {
            semCheckMaxFontSize();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: android.widget.SearchView$3 */
    /* loaded from: classes4.dex */
    public class AnonymousClass3 implements View.OnFocusChangeListener {
        AnonymousClass3() {
        }

        @Override // android.view.View.OnFocusChangeListener
        public void onFocusChange(View v, boolean hasFocus) {
            if (SearchView.this.mOnQueryTextFocusChangeListener != null) {
                SearchView.this.mOnQueryTextFocusChangeListener.onFocusChange(SearchView.this, hasFocus);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: android.widget.SearchView$4 */
    /* loaded from: classes4.dex */
    public class AnonymousClass4 implements View.OnLayoutChangeListener {
        AnonymousClass4() {
        }

        @Override // android.view.View.OnLayoutChangeListener
        public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
            SearchView.this.adjustDropDownSizeAndPosition();
        }
    }

    public int getSuggestionRowLayout() {
        return this.mSuggestionRowLayout;
    }

    public int getSuggestionCommitIconResId() {
        return this.mSuggestionCommitIconResId;
    }

    public void setSearchableInfo(SearchableInfo searchable) {
        this.mSearchable = searchable;
        if (searchable != null) {
            updateSearchAutoComplete();
            updateQueryHint();
        }
        boolean hasVoiceSearch = hasVoiceSearch();
        this.mVoiceButtonEnabled = hasVoiceSearch;
        if (hasVoiceSearch && !this.mThemeIsDeviceDefault) {
            this.mSearchSrcTextView.setPrivateImeOptions(IME_OPTION_NO_MICROPHONE);
        }
        updateViewsVisibility(isIconified());
    }

    public void setAppSearchData(Bundle appSearchData) {
        this.mAppSearchData = appSearchData;
    }

    public void setImeOptions(int imeOptions) {
        this.mSearchSrcTextView.setImeOptions(imeOptions);
    }

    public int getImeOptions() {
        return this.mSearchSrcTextView.getImeOptions();
    }

    public void setInputType(int inputType) {
        this.mSearchSrcTextView.setInputType(inputType);
    }

    public int getInputType() {
        return this.mSearchSrcTextView.getInputType();
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean requestFocus(int direction, Rect previouslyFocusedRect) {
        if (this.mClearingFocus || !isFocusable()) {
            return false;
        }
        if (!isIconified()) {
            if (direction == 1) {
                View found = focusSearch(1);
                if (found != null) {
                    return found.requestFocus(1, previouslyFocusedRect);
                }
                return false;
            }
            boolean result = this.mSearchSrcTextView.requestFocus(direction, previouslyFocusedRect);
            if (result) {
                updateViewsVisibility(false);
            }
            return result;
        }
        return super.requestFocus(direction, previouslyFocusedRect);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void clearFocus() {
        this.mClearingFocus = true;
        super.clearFocus();
        this.mSearchSrcTextView.clearFocus();
        this.mSearchSrcTextView.setImeVisibility(false);
        this.mClearingFocus = false;
    }

    public void setOnQueryTextListener(OnQueryTextListener listener) {
        this.mOnQueryChangeListener = listener;
    }

    public void setOnCloseListener(OnCloseListener listener) {
        this.mOnCloseListener = listener;
    }

    public void setOnQueryTextFocusChangeListener(View.OnFocusChangeListener listener) {
        this.mOnQueryTextFocusChangeListener = listener;
    }

    public void setOnSuggestionListener(OnSuggestionListener listener) {
        this.mOnSuggestionListener = listener;
    }

    public void setOnSearchClickListener(View.OnClickListener listener) {
        this.mOnSearchClickListener = listener;
    }

    public CharSequence getQuery() {
        return this.mSearchSrcTextView.getText();
    }

    public void setQuery(CharSequence query, boolean submit) {
        this.mSearchSrcTextView.setText(query);
        if (query != null) {
            SearchAutoComplete searchAutoComplete = this.mSearchSrcTextView;
            searchAutoComplete.setSelection(searchAutoComplete.length());
            this.mUserQuery = query;
        }
        if (submit && !TextUtils.isEmpty(query)) {
            onSubmitQuery();
        }
    }

    public void setQueryHint(CharSequence hint) {
        this.mQueryHint = hint;
        updateQueryHint();
    }

    public CharSequence getQueryHint() {
        if (this.mQueryHint != null) {
            CharSequence hint = this.mQueryHint;
            return hint;
        }
        SearchableInfo searchableInfo = this.mSearchable;
        if (searchableInfo != null && searchableInfo.getHintId() != 0) {
            CharSequence hint2 = getContext().getText(this.mSearchable.getHintId());
            return hint2;
        }
        CharSequence hint3 = this.mDefaultQueryHint;
        return hint3;
    }

    public void setIconifiedByDefault(boolean iconified) {
        if (this.mIconifiedByDefault == iconified) {
            return;
        }
        this.mIconifiedByDefault = iconified;
        updateViewsVisibility(iconified);
        updateQueryHint();
    }

    @Deprecated
    public boolean isIconfiedByDefault() {
        return this.mIconifiedByDefault;
    }

    public boolean isIconifiedByDefault() {
        return this.mIconifiedByDefault;
    }

    public void setIconified(boolean iconify) {
        if (iconify) {
            onCloseClicked();
        } else {
            onSearchClicked();
        }
    }

    public boolean isIconified() {
        return this.mIconified;
    }

    public void setSubmitButtonEnabled(boolean enabled) {
        this.mSubmitButtonEnabled = enabled;
        updateViewsVisibility(isIconified());
    }

    public boolean isSubmitButtonEnabled() {
        return this.mSubmitButtonEnabled;
    }

    public void setQueryRefinementEnabled(boolean enable) {
        this.mQueryRefinement = enable;
        CursorAdapter cursorAdapter = this.mSuggestionsAdapter;
        if (cursorAdapter instanceof SuggestionsAdapter) {
            ((SuggestionsAdapter) cursorAdapter).setQueryRefinement(enable ? 2 : 1);
        }
    }

    public boolean isQueryRefinementEnabled() {
        return this.mQueryRefinement;
    }

    public void setSuggestionsAdapter(CursorAdapter adapter) {
        this.mSuggestionsAdapter = adapter;
        this.mSearchSrcTextView.setAdapter(adapter);
    }

    public CursorAdapter getSuggestionsAdapter() {
        return this.mSuggestionsAdapter;
    }

    public void setMaxWidth(int maxpixels) {
        this.mMaxWidth = maxpixels;
        requestLayout();
    }

    public int getMaxWidth() {
        return this.mMaxWidth;
    }

    @Override // android.widget.LinearLayout, android.view.View
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (isIconified()) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }
        int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
        int width = View.MeasureSpec.getSize(widthMeasureSpec);
        switch (widthMode) {
            case Integer.MIN_VALUE:
                int i = this.mMaxWidth;
                if (i > 0) {
                    width = Math.min(i, width);
                    break;
                } else if (!this.mThemeIsDeviceDefault) {
                    width = Math.min(getPreferredWidth(), width);
                    break;
                }
                break;
            case 0:
                int i2 = this.mMaxWidth;
                if (i2 <= 0) {
                    i2 = getPreferredWidth();
                }
                width = i2;
                break;
            case 1073741824:
                int i3 = this.mMaxWidth;
                if (i3 > 0) {
                    width = Math.min(i3, width);
                    break;
                }
                break;
        }
        int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);
        int height = View.MeasureSpec.getSize(heightMeasureSpec);
        switch (heightMode) {
            case Integer.MIN_VALUE:
                height = Math.min(getPreferredHeight(), height);
                break;
            case 0:
                height = getPreferredHeight();
                break;
        }
        super.onMeasure(View.MeasureSpec.makeMeasureSpec(width, 1073741824), View.MeasureSpec.makeMeasureSpec(height, 1073741824));
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            getChildBoundsWithinSearchView(this.mSearchSrcTextView, this.mSearchSrcTextViewBounds);
            this.mSearchSrtTextViewBoundsExpanded.set(this.mSearchSrcTextViewBounds.left, 0, this.mSearchSrcTextViewBounds.right, bottom - top);
            UpdatableTouchDelegate updatableTouchDelegate = this.mTouchDelegate;
            if (updatableTouchDelegate == null) {
                UpdatableTouchDelegate updatableTouchDelegate2 = new UpdatableTouchDelegate(this.mSearchSrtTextViewBoundsExpanded, this.mSearchSrcTextViewBounds, this.mSearchSrcTextView);
                this.mTouchDelegate = updatableTouchDelegate2;
                setTouchDelegate(updatableTouchDelegate2);
                return;
            }
            updatableTouchDelegate.setBounds(this.mSearchSrtTextViewBoundsExpanded, this.mSearchSrcTextViewBounds);
        }
    }

    private void getChildBoundsWithinSearchView(View view, Rect rect) {
        view.getLocationInWindow(this.mTemp);
        getLocationInWindow(this.mTemp2);
        int[] iArr = this.mTemp;
        int i = iArr[1];
        int[] iArr2 = this.mTemp2;
        int top = i - iArr2[1];
        int left = iArr[0] - iArr2[0];
        rect.set(left, top, view.getWidth() + left, view.getHeight() + top);
    }

    private int getPreferredWidth() {
        return getContext().getResources().getDimensionPixelSize(R.dimen.search_view_preferred_width);
    }

    private int getPreferredHeight() {
        return getContext().getResources().getDimensionPixelSize(R.dimen.search_view_preferred_height);
    }

    private void updateViewsVisibility(boolean collapsed) {
        int iconVisibility;
        this.mIconified = collapsed;
        int visCollapsed = collapsed ? 0 : 8;
        boolean hasText = !TextUtils.isEmpty(this.mSearchSrcTextView.getText());
        this.mSearchButton.setVisibility(visCollapsed);
        updateSubmitButton(hasText);
        this.mSearchEditFrame.setVisibility(collapsed ? 8 : 0);
        if (this.mCollapsedIcon.getDrawable() == null || this.mIconifiedByDefault || this.mThemeIsDeviceDefault) {
            iconVisibility = 8;
        } else {
            iconVisibility = 0;
        }
        this.mCollapsedIcon.setVisibility(iconVisibility);
        updateCloseButton();
        updateVoiceButton(hasText ? false : true);
        updateSubmitArea();
    }

    private boolean hasVoiceSearch() {
        SearchableInfo searchableInfo = this.mSearchable;
        if (searchableInfo != null && searchableInfo.getVoiceSearchEnabled()) {
            Intent testIntent = null;
            if (this.mSearchable.getVoiceSearchLaunchWebSearch()) {
                testIntent = this.mVoiceWebSearchIntent;
            } else if (this.mSearchable.getVoiceSearchLaunchRecognizer()) {
                if (ViewRune.WIDGET_SEARCHVIEW_USE_SVI && this.mUseSVI) {
                    testIntent = this.mSVISearchIntent;
                } else {
                    testIntent = this.mVoiceAppSearchIntent;
                }
            }
            if (testIntent != null) {
                ResolveInfo ri = getContext().getPackageManager().resolveActivity(testIntent, 65536);
                return ri != null;
            }
        }
        return false;
    }

    private boolean isSubmitAreaEnabled() {
        return (this.mSubmitButtonEnabled || this.mVoiceButtonEnabled) && !isIconified();
    }

    private void updateSubmitButton(boolean hasText) {
        int visibility = 8;
        if (this.mSubmitButtonEnabled && isSubmitAreaEnabled() && hasFocus() && (hasText || !this.mVoiceButtonEnabled)) {
            visibility = 0;
        }
        this.mGoButton.setVisibility(visibility);
    }

    private void updateSubmitArea() {
        int visibility = 8;
        if (isSubmitAreaEnabled() && (this.mGoButton.getVisibility() == 0 || this.mVoiceButton.getVisibility() == 0)) {
            visibility = 0;
        }
        this.mSubmitArea.setVisibility(visibility);
    }

    private void updateCloseButton() {
        boolean showClose = true;
        boolean hasText = !TextUtils.isEmpty(this.mSearchSrcTextView.getText());
        if (!hasText && (!this.mIconifiedByDefault || this.mExpandedInActionView)) {
            showClose = false;
        }
        if (this.mThemeIsDeviceDefault) {
            this.mCloseButton.setVisibility(hasText ? 0 : 8);
        } else {
            this.mCloseButton.setVisibility(showClose ? 0 : 8);
        }
        Drawable closeButtonImg = this.mCloseButton.getDrawable();
        if (closeButtonImg != null) {
            closeButtonImg.setState(hasText ? ENABLED_STATE_SET : EMPTY_STATE_SET);
        }
    }

    private void postUpdateFocusedState() {
        post(this.mUpdateDrawableStateRunnable);
    }

    public void updateFocusedState() {
        boolean focused = this.mSearchSrcTextView.hasFocus();
        int[] stateSet = focused ? FOCUSED_STATE_SET : EMPTY_STATE_SET;
        Drawable searchPlateBg = this.mSearchPlate.getBackground();
        if (searchPlateBg != null) {
            searchPlateBg.setState(stateSet);
        }
        Drawable submitAreaBg = this.mSubmitArea.getBackground();
        if (submitAreaBg != null) {
            submitAreaBg.setState(stateSet);
        }
        invalidate();
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        removeCallbacks(this.mUpdateDrawableStateRunnable);
        post(this.mReleaseCursorRunnable);
        super.onDetachedFromWindow();
    }

    public void onQueryRefine(CharSequence queryText) {
        setQuery(queryText);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: android.widget.SearchView$5 */
    /* loaded from: classes4.dex */
    public class AnonymousClass5 implements View.OnClickListener {
        AnonymousClass5() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View v) {
            if (v == SearchView.this.mSearchButton) {
                SearchView.this.onSearchClicked();
                return;
            }
            if (v == SearchView.this.mCloseButton) {
                SearchView.this.onCloseClicked();
                return;
            }
            if (v == SearchView.this.mGoButton) {
                SearchView.this.onSubmitQuery();
            } else if (v == SearchView.this.mVoiceButton) {
                SearchView.this.onVoiceClicked();
            } else if (v == SearchView.this.mSearchSrcTextView) {
                SearchView.this.forceSuggestionQuery();
            }
        }
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        SearchableInfo searchableInfo = this.mSearchable;
        if (searchableInfo == null) {
            return false;
        }
        SearchableInfo.ActionKeyInfo actionKey = searchableInfo.findActionKey(keyCode);
        if (actionKey != null && actionKey.getQueryActionMsg() != null) {
            launchQuerySearch(keyCode, actionKey.getQueryActionMsg(), this.mSearchSrcTextView.getText().toString());
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: android.widget.SearchView$6 */
    /* loaded from: classes4.dex */
    public class AnonymousClass6 implements View.OnKeyListener {
        AnonymousClass6() {
        }

        @Override // android.view.View.OnKeyListener
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            SearchableInfo.ActionKeyInfo actionKey;
            boolean isFolderTypeFeature = SearchView.this.getContext().getPackageManager().hasSystemFeature(PackageManager.SEM_FEATURE_FOLDER_TYPE);
            if (isFolderTypeFeature) {
                InputMethodManager imm = (InputMethodManager) SearchView.this.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (keyCode == 23) {
                    imm.viewClicked(v);
                    imm.showSoftInput(v, 1);
                }
            }
            if (SearchView.this.mSearchable == null) {
                return false;
            }
            if (SearchView.this.mSearchSrcTextView.isPopupShowing() && SearchView.this.mSearchSrcTextView.getListSelection() != -1) {
                return SearchView.this.onSuggestionsKey(v, keyCode, event);
            }
            if (!SearchView.this.mSearchSrcTextView.isEmpty() && event.hasNoModifiers()) {
                if (event.getAction() == 1 && (keyCode == 66 || keyCode == 160)) {
                    v.cancelLongPress();
                    SearchView searchView = SearchView.this;
                    searchView.launchQuerySearch(0, null, searchView.mSearchSrcTextView.getText().toString());
                    return true;
                }
                if (event.getAction() == 0 && (actionKey = SearchView.this.mSearchable.findActionKey(keyCode)) != null && actionKey.getQueryActionMsg() != null) {
                    SearchView.this.launchQuerySearch(keyCode, actionKey.getQueryActionMsg(), SearchView.this.mSearchSrcTextView.getText().toString());
                    return true;
                }
            }
            return false;
        }
    }

    public boolean onSuggestionsKey(View v, int keyCode, KeyEvent event) {
        SearchableInfo.ActionKeyInfo actionKey;
        int position;
        String actionMsg;
        if (this.mSearchable != null && this.mSuggestionsAdapter != null && event.getAction() == 0 && event.hasNoModifiers()) {
            if (keyCode == 66 || keyCode == 84 || keyCode == 61 || keyCode == 160) {
                return onItemClicked(this.mSearchSrcTextView.getListSelection(), 0, null);
            }
            if (keyCode == 21 || keyCode == 22) {
                int selPoint = keyCode == 21 ? 0 : this.mSearchSrcTextView.length();
                this.mSearchSrcTextView.setSelection(selPoint);
                this.mSearchSrcTextView.setListSelection(0);
                this.mSearchSrcTextView.clearListSelection();
                this.mSearchSrcTextView.ensureImeVisible(true);
                return true;
            }
            if ((keyCode != 19 || this.mSearchSrcTextView.getListSelection() != 0) && (actionKey = this.mSearchable.findActionKey(keyCode)) != null && ((actionKey.getSuggestActionMsg() != null || actionKey.getSuggestActionMsgColumn() != null) && (position = this.mSearchSrcTextView.getListSelection()) != -1)) {
                Cursor c = this.mSuggestionsAdapter.getCursor();
                if (c.moveToPosition(position) && (actionMsg = getActionKeyMessage(c, actionKey)) != null && actionMsg.length() > 0) {
                    return onItemClicked(position, keyCode, actionMsg);
                }
            }
        }
        return false;
    }

    private static String getActionKeyMessage(Cursor c, SearchableInfo.ActionKeyInfo actionKey) {
        String result = null;
        String column = actionKey.getSuggestActionMsgColumn();
        if (column != null) {
            result = SuggestionsAdapter.getColumnString(c, column);
        }
        if (result == null) {
            String result2 = actionKey.getSuggestActionMsg();
            return result2;
        }
        return result;
    }

    private CharSequence getDecoratedHint(CharSequence hintText) {
        int searchIconResId = this.mSearchIconResId;
        if (this.mThemeIsDeviceDefault) {
            return hintText;
        }
        if (!this.mIconifiedByDefault || this.mSearchHintIcon == null) {
            return hintText;
        }
        int textSize = (int) (this.mSearchSrcTextView.getTextSize() * 1.25d);
        if (this.mThemeIsDeviceDefault) {
            Drawable searchIcon = getContext().getDrawable(searchIconResId);
            searchIcon.setBounds(1, -15, textSize + 1, textSize - 15);
            SpannableStringBuilder ssb = new SpannableStringBuilder("  ");
            ssb.append(hintText);
            ssb.setSpan(new ImageSpan(searchIcon), 0, 1, 33);
            return ssb;
        }
        this.mSearchHintIcon.setBounds(0, 0, textSize, textSize);
        SpannableStringBuilder ssb2 = new SpannableStringBuilder("   ");
        ssb2.setSpan(new ImageSpan(this.mSearchHintIcon), 1, 2, 33);
        ssb2.append(hintText);
        return ssb2;
    }

    private void updateQueryHint() {
        CharSequence hint = getQueryHint();
        this.mSearchSrcTextView.setHint(getDecoratedHint(hint == null ? "" : hint));
    }

    private void updateSearchAutoComplete() {
        this.mSearchSrcTextView.setDropDownAnimationStyle(0);
        this.mSearchSrcTextView.setThreshold(this.mSearchable.getSuggestThreshold());
        this.mSearchSrcTextView.setImeOptions(this.mSearchable.getImeOptions());
        int inputType = this.mSearchable.getInputType();
        if ((inputType & 15) == 1) {
            inputType &= -65537;
            if (this.mSearchable.getSuggestAuthority() != null) {
                inputType |= 65536;
                if (!this.mThemeIsDeviceDefault) {
                    inputType |= 524288;
                }
            }
        }
        this.mSearchSrcTextView.setInputType(inputType);
        CursorAdapter cursorAdapter = this.mSuggestionsAdapter;
        if (cursorAdapter != null) {
            cursorAdapter.changeCursor(null);
        }
        if (this.mSearchable.getSuggestAuthority() != null) {
            SuggestionsAdapter suggestionsAdapter = new SuggestionsAdapter(getContext(), this, this.mSearchable, this.mOutsideDrawablesCache);
            this.mSuggestionsAdapter = suggestionsAdapter;
            this.mSearchSrcTextView.setAdapter(suggestionsAdapter);
            ((SuggestionsAdapter) this.mSuggestionsAdapter).setQueryRefinement(this.mQueryRefinement ? 2 : 1);
        }
    }

    private void updateVoiceButton(boolean empty) {
        int visibility = 8;
        if (this.mVoiceButtonEnabled && !isIconified() && empty) {
            visibility = 0;
            this.mGoButton.setVisibility(8);
        }
        this.mVoiceButton.setVisibility(visibility);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: android.widget.SearchView$7 */
    /* loaded from: classes4.dex */
    public class AnonymousClass7 implements TextView.OnEditorActionListener {
        AnonymousClass7() {
        }

        @Override // android.widget.TextView.OnEditorActionListener
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            SearchView.this.onSubmitQuery();
            return true;
        }
    }

    public void onTextChanged(CharSequence newText) {
        CharSequence text = this.mSearchSrcTextView.getText();
        this.mUserQuery = text;
        boolean hasText = !TextUtils.isEmpty(text);
        updateSubmitButton(hasText);
        updateVoiceButton(hasText ? false : true);
        updateCloseButton();
        updateSubmitArea();
        if (!TextUtils.equals(newText, this.mOldQueryText)) {
            this.mOldQueryText = newText.toString();
            OnQueryTextListener onQueryTextListener = this.mOnQueryChangeListener;
            if (onQueryTextListener != null) {
                onQueryTextListener.onQueryTextChange(newText.toString());
            }
        }
    }

    public void onSubmitQuery() {
        CharSequence query = this.mSearchSrcTextView.getText();
        if (query != null && TextUtils.getTrimmedLength(query) > 0) {
            OnQueryTextListener onQueryTextListener = this.mOnQueryChangeListener;
            if (onQueryTextListener == null || !onQueryTextListener.onQueryTextSubmit(query.toString())) {
                if (this.mSearchable != null) {
                    launchQuerySearch(0, null, query.toString());
                }
                this.mSearchSrcTextView.setImeVisibility(false);
                dismissSuggestions();
            }
        }
    }

    private void dismissSuggestions() {
        this.mSearchSrcTextView.dismissDropDown();
    }

    public void onCloseClicked() {
        CharSequence text = this.mSearchSrcTextView.getText();
        if (TextUtils.isEmpty(text)) {
            if (this.mIconifiedByDefault) {
                OnCloseListener onCloseListener = this.mOnCloseListener;
                if (onCloseListener == null || !onCloseListener.onClose()) {
                    clearFocus();
                    updateViewsVisibility(true);
                    return;
                }
                return;
            }
            return;
        }
        this.mSearchSrcTextView.setText("");
        this.mSearchSrcTextView.requestFocus();
        if (semIsForceHideSoftInput()) {
            this.mSearchSrcTextView.setImeVisibility(false);
        } else {
            this.mSearchSrcTextView.setImeVisibility(true);
        }
    }

    boolean semIsForceHideSoftInput() {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        int mKeyboard = imm.isAccessoryKeyboardState();
        Resources res = getContext().getResources();
        ViewRootImpl viewRootImpl = getViewRootImpl();
        if (viewRootImpl != null && res.getConfiguration().semDesktopModeEnabled != 1) {
            return mKeyboard != 0;
        }
        int mShowImeWithHardKeyboard = Settings.Secure.getInt(getContext().getContentResolver(), Settings.Secure.SHOW_IME_WITH_HARD_KEYBOARD, 0);
        return (mKeyboard == 0 || mShowImeWithHardKeyboard == 1) ? false : true;
    }

    public void onSearchClicked() {
        updateViewsVisibility(false);
        this.mSearchSrcTextView.requestFocus();
        if (semIsForceHideSoftInput()) {
            this.mSearchSrcTextView.setImeVisibility(false);
        } else {
            this.mSearchSrcTextView.setImeVisibility(true);
        }
        View.OnClickListener onClickListener = this.mOnSearchClickListener;
        if (onClickListener != null) {
            onClickListener.onClick(this);
        }
    }

    public void onVoiceClicked() {
        if (this.mSearchable == null) {
            return;
        }
        SearchableInfo searchable = this.mSearchable;
        try {
            if (searchable.getVoiceSearchLaunchWebSearch()) {
                Intent webSearchIntent = createVoiceWebSearchIntent(this.mVoiceWebSearchIntent, searchable);
                getContext().startActivity(webSearchIntent);
            } else if (searchable.getVoiceSearchLaunchRecognizer()) {
                if (this.mUseSVI) {
                    Intent sVoiceIntent = createSVoiceSearchIntent(this.mSVISearchIntent, searchable);
                    getContext().startActivity(sVoiceIntent);
                } else {
                    Intent appSearchIntent = createVoiceAppSearchIntent(this.mVoiceAppSearchIntent, searchable);
                    getContext().startActivity(appSearchIntent);
                }
            }
        } catch (ActivityNotFoundException e) {
            Log.w(LOG_TAG, "Could not find voice search activity");
        }
    }

    void onTextFocusChanged() {
        updateViewsVisibility(isIconified());
        postUpdateFocusedState();
        if (this.mSearchSrcTextView.hasFocus()) {
            forceSuggestionQuery();
        }
    }

    @Override // android.view.View
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        postUpdateFocusedState();
    }

    @Override // android.view.View
    public void setBackground(Drawable background) {
        if (this.mThemeIsDeviceDefault) {
            this.mSearchPlate.setBackground(background);
        } else {
            super.setBackground(background);
        }
    }

    @Override // android.view.View
    public void setBackgroundResource(int resid) {
        if (this.mThemeIsDeviceDefault) {
            this.mSearchPlate.setBackground(getContext().getResources().getDrawable(resid));
        } else {
            super.setBackgroundResource(resid);
        }
    }

    @Override // android.view.View
    public void setElevation(float elevation) {
        if (this.mThemeIsDeviceDefault) {
            this.mSearchPlate.setElevation(elevation);
        } else {
            super.setElevation(elevation);
        }
    }

    @Override // android.view.CollapsibleActionView
    public void onActionViewCollapsed() {
        setQuery("", false);
        clearFocus();
        updateViewsVisibility(true);
        this.mSearchSrcTextView.setImeOptions(this.mCollapsedImeOptions);
        this.mExpandedInActionView = false;
    }

    @Override // android.view.CollapsibleActionView
    public void onActionViewExpanded() {
        if (this.mExpandedInActionView) {
            return;
        }
        this.mExpandedInActionView = true;
        int imeOptions = this.mSearchSrcTextView.getImeOptions();
        this.mCollapsedImeOptions = imeOptions;
        this.mSearchSrcTextView.setImeOptions(imeOptions | 33554432);
        this.mSearchSrcTextView.setText("");
        setIconified(false);
    }

    /* loaded from: classes4.dex */
    public static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() { // from class: android.widget.SearchView.SavedState.1
            AnonymousClass1() {
            }

            @Override // android.os.Parcelable.Creator
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            @Override // android.os.Parcelable.Creator
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
        boolean isIconified;

        SavedState(Parcelable superState) {
            super(superState);
        }

        public SavedState(Parcel source) {
            super(source);
            this.isIconified = ((Boolean) source.readValue(null)).booleanValue();
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeValue(Boolean.valueOf(this.isIconified));
        }

        public String toString() {
            return "SearchView.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " isIconified=" + this.isIconified + "}";
        }

        /* renamed from: android.widget.SearchView$SavedState$1 */
        /* loaded from: classes4.dex */
        class AnonymousClass1 implements Parcelable.Creator<SavedState> {
            AnonymousClass1() {
            }

            @Override // android.os.Parcelable.Creator
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            @Override // android.os.Parcelable.Creator
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        }
    }

    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState ss = new SavedState(superState);
        ss.isIconified = isIconified();
        return ss;
    }

    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        updateViewsVisibility(ss.isIconified);
        requestLayout();
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    public CharSequence getAccessibilityClassName() {
        return SearchView.class.getName();
    }

    public void adjustDropDownSizeAndPosition() {
        int iconOffset;
        int offset;
        if (this.mDropDownAnchor.getWidth() > 1) {
            Resources res = getContext().getResources();
            int anchorPadding = this.mSearchPlate.getPaddingLeft();
            Rect dropDownPadding = new Rect();
            boolean isLayoutRtl = isLayoutRtl();
            if (this.mIconifiedByDefault) {
                iconOffset = res.getDimensionPixelSize(R.dimen.dropdownitem_icon_width) + res.getDimensionPixelSize(R.dimen.dropdownitem_text_padding_left);
            } else {
                iconOffset = 0;
            }
            if (this.mThemeIsDeviceDefault) {
                iconOffset = 0;
                anchorPadding = 0;
            }
            this.mSearchSrcTextView.getDropDownBackground().getPadding(dropDownPadding);
            if (isLayoutRtl) {
                offset = -dropDownPadding.left;
            } else {
                int offset2 = dropDownPadding.left;
                offset = anchorPadding - (offset2 + iconOffset);
            }
            this.mSearchSrcTextView.setDropDownHorizontalOffset(offset);
            int width = (((this.mDropDownAnchor.getWidth() + dropDownPadding.left) + dropDownPadding.right) + iconOffset) - anchorPadding;
            this.mSearchSrcTextView.setDropDownWidth(width);
            if (this.mSearchSrcTextView.isPopupShowing()) {
                this.mSearchSrcTextView.showDropDown();
            }
        }
    }

    public boolean onItemClicked(int position, int actionKey, String actionMsg) {
        OnSuggestionListener onSuggestionListener = this.mOnSuggestionListener;
        if (onSuggestionListener != null && onSuggestionListener.onSuggestionClick(position)) {
            return false;
        }
        launchSuggestion(position, 0, null);
        this.mSearchSrcTextView.setImeVisibility(false);
        dismissSuggestions();
        return true;
    }

    public boolean onItemSelected(int position) {
        OnSuggestionListener onSuggestionListener = this.mOnSuggestionListener;
        if (onSuggestionListener == null || !onSuggestionListener.onSuggestionSelect(position)) {
            rewriteQueryFromSuggestion(position);
            return true;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: android.widget.SearchView$8 */
    /* loaded from: classes4.dex */
    public class AnonymousClass8 implements AdapterView.OnItemClickListener {
        AnonymousClass8() {
        }

        @Override // android.widget.AdapterView.OnItemClickListener
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            SearchView.this.onItemClicked(position, 0, null);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: android.widget.SearchView$9 */
    /* loaded from: classes4.dex */
    public class AnonymousClass9 implements AdapterView.OnItemSelectedListener {
        AnonymousClass9() {
        }

        @Override // android.widget.AdapterView.OnItemSelectedListener
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            SearchView.this.onItemSelected(position);
        }

        @Override // android.widget.AdapterView.OnItemSelectedListener
        public void onNothingSelected(AdapterView<?> parent) {
        }
    }

    private void rewriteQueryFromSuggestion(int position) {
        CharSequence oldQuery = this.mSearchSrcTextView.getText();
        Cursor c = this.mSuggestionsAdapter.getCursor();
        if (c == null) {
            return;
        }
        if (c.moveToPosition(position)) {
            CharSequence newQuery = this.mSuggestionsAdapter.convertToString(c);
            if (newQuery != null) {
                setQuery(newQuery);
                return;
            } else {
                setQuery(oldQuery);
                return;
            }
        }
        setQuery(oldQuery);
    }

    private boolean launchSuggestion(int position, int actionKey, String actionMsg) {
        Cursor c = this.mSuggestionsAdapter.getCursor();
        if (c != null && c.moveToPosition(position)) {
            Intent intent = createIntentFromSuggestion(c, actionKey, actionMsg);
            launchIntent(intent);
            return true;
        }
        return false;
    }

    private void launchIntent(Intent intent) {
        if (intent == null) {
            return;
        }
        try {
            getContext().startActivity(intent);
        } catch (RuntimeException ex) {
            Log.e(LOG_TAG, "Failed launch activity: " + intent, ex);
        }
    }

    private void setQuery(CharSequence query) {
        this.mSearchSrcTextView.setText(query, true);
        this.mSearchSrcTextView.setSelection(TextUtils.isEmpty(query) ? 0 : query.length());
    }

    public void launchQuerySearch(int actionKey, String actionMsg, String query) {
        Intent intent = createIntent(Intent.ACTION_SEARCH, null, null, query, actionKey, actionMsg);
        getContext().startActivity(intent);
    }

    private Intent createIntent(String action, Uri data, String extraData, String query, int actionKey, String actionMsg) {
        Intent intent = new Intent(action);
        intent.addFlags(268435456);
        if (data != null) {
            intent.setData(data);
        }
        intent.putExtra(SearchManager.USER_QUERY, this.mUserQuery);
        if (query != null) {
            intent.putExtra("query", query);
        }
        if (extraData != null) {
            intent.putExtra(SearchManager.EXTRA_DATA_KEY, extraData);
        }
        Bundle bundle = this.mAppSearchData;
        if (bundle != null) {
            intent.putExtra(SearchManager.APP_DATA, bundle);
        }
        if (actionKey != 0) {
            intent.putExtra(SearchManager.ACTION_KEY, actionKey);
            intent.putExtra(SearchManager.ACTION_MSG, actionMsg);
        }
        intent.setComponent(this.mSearchable.getSearchActivity());
        return intent;
    }

    private Intent createVoiceWebSearchIntent(Intent baseIntent, SearchableInfo searchable) {
        Intent voiceIntent = new Intent(baseIntent);
        ComponentName searchActivity = searchable.getSearchActivity();
        voiceIntent.putExtra("calling_package", searchActivity == null ? null : searchActivity.flattenToShortString());
        return voiceIntent;
    }

    private Intent createVoiceAppSearchIntent(Intent baseIntent, SearchableInfo searchable) {
        ComponentName searchActivity = searchable.getSearchActivity();
        Intent queryIntent = new Intent(Intent.ACTION_SEARCH);
        queryIntent.setComponent(searchActivity);
        PendingIntent pending = PendingIntent.getActivity(getContext(), 0, queryIntent, 1107296256);
        Bundle queryExtras = new Bundle();
        Bundle bundle = this.mAppSearchData;
        if (bundle != null) {
            queryExtras.putParcelable(SearchManager.APP_DATA, bundle);
        }
        Intent voiceIntent = new Intent(baseIntent);
        String languageModel = RecognizerIntent.LANGUAGE_MODEL_FREE_FORM;
        String prompt = null;
        String language = null;
        int maxResults = 1;
        Resources resources = getResources();
        if (searchable.getVoiceLanguageModeId() != 0) {
            languageModel = resources.getString(searchable.getVoiceLanguageModeId());
        }
        if (searchable.getVoicePromptTextId() != 0) {
            prompt = resources.getString(searchable.getVoicePromptTextId());
        }
        if (searchable.getVoiceLanguageId() != 0) {
            language = resources.getString(searchable.getVoiceLanguageId());
        }
        if (searchable.getVoiceMaxResults() != 0) {
            maxResults = searchable.getVoiceMaxResults();
        }
        voiceIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, languageModel);
        voiceIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, prompt);
        voiceIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, language);
        voiceIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, maxResults);
        voiceIntent.putExtra("calling_package", searchActivity == null ? null : searchActivity.flattenToShortString());
        voiceIntent.putExtra(RecognizerIntent.EXTRA_RESULTS_PENDINGINTENT, pending);
        voiceIntent.putExtra(RecognizerIntent.EXTRA_RESULTS_PENDINGINTENT_BUNDLE, queryExtras);
        return voiceIntent;
    }

    private Intent createIntentFromSuggestion(Cursor c, int actionKey, String actionMsg) {
        int rowNum;
        String data;
        String id;
        try {
            String action = SuggestionsAdapter.getColumnString(c, SearchManager.SUGGEST_COLUMN_INTENT_ACTION);
            if (action == null) {
                action = this.mSearchable.getSuggestIntentAction();
            }
            if (action == null) {
                action = Intent.ACTION_SEARCH;
            }
            String data2 = SuggestionsAdapter.getColumnString(c, SearchManager.SUGGEST_COLUMN_INTENT_DATA);
            if (data2 == null) {
                data2 = this.mSearchable.getSuggestIntentData();
            }
            if (data2 != null && (id = SuggestionsAdapter.getColumnString(c, SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID)) != null) {
                data = data2 + "/" + Uri.encode(id);
            } else {
                data = data2;
            }
            Uri dataUri = data == null ? null : Uri.parse(data);
            String query = SuggestionsAdapter.getColumnString(c, SearchManager.SUGGEST_COLUMN_QUERY);
            String extraData = SuggestionsAdapter.getColumnString(c, SearchManager.SUGGEST_COLUMN_INTENT_EXTRA_DATA);
            return createIntent(action, dataUri, extraData, query, actionKey, actionMsg);
        } catch (RuntimeException e) {
            try {
                rowNum = c.getPosition();
            } catch (RuntimeException e2) {
                rowNum = -1;
            }
            Log.w(LOG_TAG, "Search suggestions cursor at row " + rowNum + " returned exception.", e);
            return null;
        }
    }

    public void forceSuggestionQuery() {
        this.mSearchSrcTextView.doBeforeTextChanged();
        this.mSearchSrcTextView.doAfterTextChanged();
    }

    static boolean isLandscapeMode(Context context) {
        return context.getResources().getConfiguration().orientation == 2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: android.widget.SearchView$10 */
    /* loaded from: classes4.dex */
    public class AnonymousClass10 implements TextWatcher {
        AnonymousClass10() {
        }

        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence s, int start, int before, int after) {
        }

        @Override // android.text.TextWatcher
        public void onTextChanged(CharSequence s, int start, int before, int after) {
            SearchView.this.onTextChanged(s);
        }

        @Override // android.text.TextWatcher
        public void afterTextChanged(Editable s) {
        }
    }

    /* loaded from: classes4.dex */
    private static class UpdatableTouchDelegate extends TouchDelegate {
        private final Rect mActualBounds;
        private boolean mDelegateTargeted;
        private final View mDelegateView;
        private final int mSlop;
        private final Rect mSlopBounds;
        private final Rect mTargetBounds;

        public UpdatableTouchDelegate(Rect targetBounds, Rect actualBounds, View delegateView) {
            super(targetBounds, delegateView);
            this.mSlop = ViewConfiguration.get(delegateView.getContext()).getScaledTouchSlop();
            this.mTargetBounds = new Rect();
            this.mSlopBounds = new Rect();
            this.mActualBounds = new Rect();
            setBounds(targetBounds, actualBounds);
            this.mDelegateView = delegateView;
        }

        public void setBounds(Rect desiredBounds, Rect actualBounds) {
            this.mTargetBounds.set(desiredBounds);
            this.mSlopBounds.set(desiredBounds);
            Rect rect = this.mSlopBounds;
            int i = this.mSlop;
            rect.inset(-i, -i);
            this.mActualBounds.set(actualBounds);
        }

        @Override // android.view.TouchDelegate
        public boolean onTouchEvent(MotionEvent event) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            boolean sendToDelegate = false;
            boolean hit = true;
            switch (event.getAction()) {
                case 0:
                    if (this.mTargetBounds.contains(x, y)) {
                        this.mDelegateTargeted = true;
                        sendToDelegate = true;
                        break;
                    }
                    break;
                case 1:
                case 2:
                    sendToDelegate = this.mDelegateTargeted;
                    if (sendToDelegate && !this.mSlopBounds.contains(x, y)) {
                        hit = false;
                        break;
                    }
                    break;
                case 3:
                    sendToDelegate = this.mDelegateTargeted;
                    this.mDelegateTargeted = false;
                    break;
            }
            if (!sendToDelegate) {
                return false;
            }
            if (hit && !this.mActualBounds.contains(x, y)) {
                event.setLocation(this.mDelegateView.getWidth() / 2, this.mDelegateView.getHeight() / 2);
            } else {
                event.setLocation(x - this.mActualBounds.left, y - this.mActualBounds.top);
            }
            boolean handled = this.mDelegateView.dispatchTouchEvent(event);
            return handled;
        }
    }

    /* loaded from: classes4.dex */
    public static class SearchAutoComplete extends AutoCompleteTextView {
        private boolean mHasPendingShowSoftInputRequest;
        final Runnable mRunShowSoftInputIfNecessary;
        private SearchView mSearchView;
        private int mThreshold;

        public SearchAutoComplete(Context context) {
            super(context);
            this.mRunShowSoftInputIfNecessary = new Runnable() { // from class: android.widget.SearchView$SearchAutoComplete$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    SearchView.SearchAutoComplete.this.lambda$new$0();
                }
            };
            this.mThreshold = getThreshold();
        }

        public SearchAutoComplete(Context context, AttributeSet attrs) {
            super(context, attrs);
            this.mRunShowSoftInputIfNecessary = new Runnable() { // from class: android.widget.SearchView$SearchAutoComplete$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    SearchView.SearchAutoComplete.this.lambda$new$0();
                }
            };
            this.mThreshold = getThreshold();
        }

        public SearchAutoComplete(Context context, AttributeSet attrs, int defStyleAttrs) {
            super(context, attrs, defStyleAttrs);
            this.mRunShowSoftInputIfNecessary = new Runnable() { // from class: android.widget.SearchView$SearchAutoComplete$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    SearchView.SearchAutoComplete.this.lambda$new$0();
                }
            };
            this.mThreshold = getThreshold();
        }

        public SearchAutoComplete(Context context, AttributeSet attrs, int defStyleAttrs, int defStyleRes) {
            super(context, attrs, defStyleAttrs, defStyleRes);
            this.mRunShowSoftInputIfNecessary = new Runnable() { // from class: android.widget.SearchView$SearchAutoComplete$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    SearchView.SearchAutoComplete.this.lambda$new$0();
                }
            };
            this.mThreshold = getThreshold();
        }

        @Override // android.view.View
        public void onFinishInflate() {
            super.onFinishInflate();
            DisplayMetrics metrics = getResources().getDisplayMetrics();
            setMinWidth((int) TypedValue.applyDimension(1, getSearchViewTextMinWidthDp(), metrics));
        }

        void setSearchView(SearchView searchView) {
            this.mSearchView = searchView;
        }

        @Override // android.widget.AutoCompleteTextView
        public void setThreshold(int threshold) {
            super.setThreshold(threshold);
            this.mThreshold = threshold;
        }

        public boolean isEmpty() {
            return TextUtils.getTrimmedLength(getText()) == 0;
        }

        @Override // android.widget.AutoCompleteTextView
        protected void replaceText(CharSequence text) {
        }

        @Override // android.widget.AutoCompleteTextView
        public void performCompletion() {
        }

        @Override // android.widget.AutoCompleteTextView, android.widget.TextView, android.view.View
        public void onWindowFocusChanged(boolean hasWindowFocus) {
            super.onWindowFocusChanged(hasWindowFocus);
            if (!this.mSearchView.semIsForceHideSoftInput() && hasWindowFocus && this.mSearchView.hasFocus() && getVisibility() == 0) {
                this.mHasPendingShowSoftInputRequest = true;
                if (SearchView.isLandscapeMode(getContext())) {
                    ensureImeVisible(true);
                }
            }
        }

        @Override // android.widget.AutoCompleteTextView, android.widget.TextView, android.view.View
        public void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
            super.onFocusChanged(focused, direction, previouslyFocusedRect);
            this.mSearchView.onTextFocusChanged();
        }

        @Override // android.widget.AutoCompleteTextView
        public boolean enoughToFilter() {
            return this.mThreshold <= 0 || super.enoughToFilter();
        }

        @Override // android.widget.AutoCompleteTextView, android.widget.TextView, android.view.View
        public boolean onKeyPreIme(int keyCode, KeyEvent event) {
            boolean consume = super.onKeyPreIme(keyCode, event);
            if (this.mSearchView.mThemeIsDeviceDefault) {
                return consume;
            }
            if (consume && keyCode == 4 && event.getAction() == 1) {
                setImeVisibility(false);
            }
            return consume;
        }

        private int getSearchViewTextMinWidthDp() {
            Configuration configuration = getResources().getConfiguration();
            int width = configuration.screenWidthDp;
            int height = configuration.screenHeightDp;
            int orientation = configuration.orientation;
            if (width >= 960 && height >= 720 && orientation == 2) {
                return 256;
            }
            if (width >= 600) {
                return 192;
            }
            if (width >= 640 && height >= 480) {
                return 192;
            }
            return 160;
        }

        @Override // android.widget.TextView, android.view.View
        public InputConnection onCreateInputConnection(EditorInfo editorInfo) {
            InputConnection ic = super.onCreateInputConnection(editorInfo);
            if (this.mHasPendingShowSoftInputRequest) {
                removeCallbacks(this.mRunShowSoftInputIfNecessary);
                post(this.mRunShowSoftInputIfNecessary);
            }
            return ic;
        }

        @Override // android.view.View
        public boolean checkInputConnectionProxy(View view) {
            return view == this.mSearchView;
        }

        /* renamed from: showSoftInputIfNecessary */
        public void lambda$new$0() {
            if (this.mHasPendingShowSoftInputRequest) {
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(InputMethodManager.class);
                imm.showSoftInput(this, 0);
                this.mHasPendingShowSoftInputRequest = false;
            }
        }

        public void setImeVisibility(boolean visible) {
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(InputMethodManager.class);
            if (!visible) {
                this.mHasPendingShowSoftInputRequest = false;
                removeCallbacks(this.mRunShowSoftInputIfNecessary);
                imm.hideSoftInputFromWindow(getWindowToken(), 0);
            } else {
                if (imm.isActive(this)) {
                    this.mHasPendingShowSoftInputRequest = false;
                    removeCallbacks(this.mRunShowSoftInputIfNecessary);
                    imm.showSoftInput(this, 0);
                    return;
                }
                this.mHasPendingShowSoftInputRequest = true;
            }
        }
    }

    private Intent createSVoiceSearchIntent(Intent baseIntent, SearchableInfo searchable) {
        ComponentName searchActivity = searchable.getSearchActivity();
        Intent queryIntent = new Intent(Intent.ACTION_SEARCH);
        queryIntent.setComponent(searchActivity);
        PendingIntent pending = PendingIntent.getActivity(getContext(), 0, queryIntent, 1073741824);
        Bundle queryExtras = new Bundle();
        Bundle bundle = this.mAppSearchData;
        if (bundle != null) {
            queryExtras.putParcelable(SearchManager.APP_DATA, bundle);
        }
        Intent voiceIntent = new Intent(baseIntent);
        voiceIntent.putExtra("calling_package", searchActivity == null ? null : searchActivity.flattenToShortString());
        voiceIntent.putExtra(RecognizerIntent.EXTRA_RESULTS_PENDINGINTENT, pending);
        voiceIntent.putExtra(RecognizerIntent.EXTRA_RESULTS_PENDINGINTENT_BUNDLE, queryExtras);
        return voiceIntent;
    }

    public boolean semSetSviEnabled(boolean enabled) {
        if (!ViewRune.WIDGET_SEARCHVIEW_USE_SVI) {
            Log.w(LOG_TAG, "semSetSviEnabled: WIDGET_SEARCHVIEW_USE_SVI is false");
            this.mUseSVI = false;
            return false;
        }
        this.mUseSVI = enabled;
        if (enabled) {
            try {
                PackageManager packageManager = getContext().getPackageManager();
                PackageInfo packageInfo = packageManager.getPackageInfo("com.samsung.android.honeyboard", 0);
                long version = packageInfo != null ? packageInfo.getLongVersionCode() : -1L;
                if (version < 220002001) {
                    Log.w(LOG_TAG, "semSetSviEnabled: not supported SVI version");
                    this.mUseSVI = false;
                }
                if (!isSystemLocaleSupported()) {
                    Log.w(LOG_TAG, "semSetSviEnabled: not supported system locale");
                    this.mUseSVI = false;
                }
            } catch (Exception e) {
                Log.w(LOG_TAG, "Exception " + e);
                this.mUseSVI = false;
            }
        }
        return this.mUseSVI;
    }

    public boolean semIsSviEnabled() {
        return this.mUseSVI;
    }

    private boolean isSystemLocaleSupported() {
        Cursor cursor;
        Uri uri = Uri.parse("content://com.samsung.android.honeyboard.provider.VoiceLanguageListProvider/is_honeyvoice_locale_supported");
        int isLocalSupported = 0;
        try {
            cursor = this.mContext.getContentResolver().query(uri, null, null, null, null);
        } catch (Exception e) {
            Log.w(LOG_TAG, "isSystemLocaleSupported: exception!!" + e);
        }
        if (cursor != null) {
            while (cursor.moveToNext()) {
                try {
                    isLocalSupported = cursor.getInt(cursor.getColumnIndex(SEM_KEY_SVI_APP_LOCALE));
                } finally {
                }
            }
            if (cursor != null) {
                cursor.close();
            }
            if (isLocalSupported != 1) {
                return false;
            }
            return true;
        }
        if (cursor != null) {
            cursor.close();
        }
        return false;
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (this.mThemeIsDeviceDefault) {
            semCheckMaxFontSize();
        }
    }

    private void semCheckMaxFontSize() {
        float currentFontScale = getContext().getResources().getConfiguration().fontScale;
        int searchSrcTextSize = getContext().getResources().getDimensionPixelSize(R.dimen.tw_searchview_search_text_size);
        if (currentFontScale > 1.3f) {
            float scaleBase = searchSrcTextSize / currentFontScale;
            this.mSearchSrcTextView.setTextSize(0, 1.3f * scaleBase);
        } else {
            this.mSearchSrcTextView.setTextSize(0, searchSrcTextSize);
        }
    }
}
