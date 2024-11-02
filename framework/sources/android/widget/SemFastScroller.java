package android.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.IntProperty;
import android.util.Log;
import android.util.MathUtils;
import android.util.Property;
import android.util.TypedValue;
import android.view.HapticFeedbackConstants;
import android.view.MotionEvent;
import android.view.PointerIcon;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;
import android.view.animation.PathInterpolator;
import android.widget.ImageView;
import com.android.internal.R;
import com.samsung.android.wallpaperbackup.GenerateXML;

/* loaded from: classes4.dex */
public class SemFastScroller {
    private static final int DURATION_CROSS_FADE = 0;
    private static final int DURATION_FADE_IN = 167;
    private static final int DURATION_FADE_OUT = 167;
    private static final int DURATION_RESIZE = 100;
    public static final int EFFECT_STATE_CLOSE = 0;
    public static final int EFFECT_STATE_OPEN = 1;
    private static final long FADE_TIMEOUT = 2500;
    private static final int FASTSCROLL_VIBRATE_INDEX = 26;
    private static final int MIN_PAGES = 1;
    private static final int OVERLAY_ABOVE_THUMB = 2;
    private static final int OVERLAY_AT_THUMB = 1;
    private static final int OVERLAY_FLOATING = 0;
    private static final int PREVIEW_LEFT = 0;
    private static final int PREVIEW_RIGHT = 1;
    private static final int STATE_DRAGGING = 2;
    private static final int STATE_NONE = 0;
    private static final int STATE_VISIBLE = 1;
    private static final String TAG = "SemFastScroller";
    private static final int THUMB_POSITION_INSIDE = 1;
    private static final int THUMB_POSITION_MIDPOINT = 0;
    private int mAdditionalBottomPadding;
    private float mAdditionalTouchArea;
    private boolean mAlwaysShow;
    private Context mContext;
    private AnimatorSet mDecorAnimation;
    private boolean mEnabled;
    private int mFirstVisibleItem;
    private int mHeaderCount;
    private float mInitialTouchY;
    private boolean mLayoutFromRight;
    private final AbsListView mList;
    private Adapter mListAdapter;
    private boolean mLongList;
    private boolean mMatchDragPosition;
    private int mOldChildCount;
    private int mOldItemCount;
    private int mOrientation;
    private final ViewGroupOverlay mOverlay;
    private int mOverlayPosition;
    private AnimatorSet mPreviewAnimation;
    private final View mPreviewImage;
    private int mPreviewMarginEnd;
    private int mPreviewMinHeight;
    private int mPreviewMinWidth;
    private int mPreviewPadding;
    private final TextView mPrimaryText;
    private int mScaledTouchSlop;
    private int mScrollBarStyle;
    private boolean mScrollCompleted;
    private final TextView mSecondaryText;
    private SectionIndexer mSectionIndexer;
    private Object[] mSections;
    private boolean mShowingPreview;
    private boolean mShowingPrimary;
    private int mState;
    private int mTextAppearance;
    private ColorStateList mTextColor;
    private float mTextSize;
    private Drawable mThumbDrawable;
    private final ImageView mThumbImage;
    private int mThumbMarginEnd;
    private int mThumbMinHeight;
    private int mThumbMinWidth;
    private float mThumbOffset;
    private int mThumbPosition;
    private float mThumbRange;
    private Drawable mTrackDrawable;
    private final ImageView mTrackImage;
    private int mTrackPadding;
    private boolean mUpdatingLayout;
    private int mWidth;
    private static final long TAP_TIMEOUT = ViewConfiguration.getTapTimeout();
    private static Property<View, Integer> LEFT = new IntProperty<View>("left") { // from class: android.widget.SemFastScroller.3
        AnonymousClass3(String name) {
            super(name);
        }

        @Override // android.util.IntProperty
        public void setValue(View object, int value) {
            object.setLeft(value);
        }

        @Override // android.util.Property
        public Integer get(View object) {
            return Integer.valueOf(object.getLeft());
        }
    };
    private static Property<View, Integer> TOP = new IntProperty<View>(GenerateXML.TOP) { // from class: android.widget.SemFastScroller.4
        AnonymousClass4(String name) {
            super(name);
        }

        @Override // android.util.IntProperty
        public void setValue(View object, int value) {
            object.setTop(value);
        }

        @Override // android.util.Property
        public Integer get(View object) {
            return Integer.valueOf(object.getTop());
        }
    };
    private static Property<View, Integer> RIGHT = new IntProperty<View>("right") { // from class: android.widget.SemFastScroller.5
        AnonymousClass5(String name) {
            super(name);
        }

        @Override // android.util.IntProperty
        public void setValue(View object, int value) {
            object.setRight(value);
        }

        @Override // android.util.Property
        public Integer get(View object) {
            return Integer.valueOf(object.getRight());
        }
    };
    private static Property<View, Integer> BOTTOM = new IntProperty<View>(GenerateXML.BOTTOM) { // from class: android.widget.SemFastScroller.6
        AnonymousClass6(String name) {
            super(name);
        }

        @Override // android.util.IntProperty
        public void setValue(View object, int value) {
            object.setBottom(value);
        }

        @Override // android.util.Property
        public Integer get(View object) {
            return Integer.valueOf(object.getBottom());
        }
    };
    private final Rect mTempBounds = new Rect();
    private final Rect mTempMargins = new Rect();
    private final Rect mContainerRect = new Rect();
    private final int[] mPreviewResId = new int[2];
    private int mCurrentSection = -1;
    private int mScrollbarPosition = -1;
    private long mPendingDrag = -1;
    private int mColorPrimary = -1;
    private int mThumbBackgroundColor = -1;
    private float mScrollY = 0.0f;
    private int mEffectState = 0;
    private float mOldThumbPosition = -1.0f;
    private int mScrollBarBottomPadding = 0;
    private int mScrollBarTopPadding = 0;
    private boolean mUseOpenThemeResources = true;
    private final Runnable mDeferHide = new Runnable() { // from class: android.widget.SemFastScroller.1
        AnonymousClass1() {
        }

        @Override // java.lang.Runnable
        public void run() {
            SemFastScroller.this.setState(0);
        }
    };
    private final Animator.AnimatorListener mSwitchPrimaryListener = new AnimatorListenerAdapter() { // from class: android.widget.SemFastScroller.2
        AnonymousClass2() {
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animation) {
            SemFastScroller.this.mShowingPrimary = !r0.mShowingPrimary;
        }
    };

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: android.widget.SemFastScroller$1 */
    /* loaded from: classes4.dex */
    public class AnonymousClass1 implements Runnable {
        AnonymousClass1() {
        }

        @Override // java.lang.Runnable
        public void run() {
            SemFastScroller.this.setState(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: android.widget.SemFastScroller$2 */
    /* loaded from: classes4.dex */
    public class AnonymousClass2 extends AnimatorListenerAdapter {
        AnonymousClass2() {
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animation) {
            SemFastScroller.this.mShowingPrimary = !r0.mShowingPrimary;
        }
    }

    public SemFastScroller(AbsListView listView, int styleResId) {
        this.mAdditionalTouchArea = 0.0f;
        this.mList = listView;
        this.mOldItemCount = listView.getCount();
        this.mOldChildCount = listView.getChildCount();
        Context context = listView.getContext();
        this.mContext = context;
        this.mScaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        this.mScrollBarStyle = listView.getScrollBarStyle();
        this.mScrollCompleted = true;
        this.mState = 1;
        this.mMatchDragPosition = this.mContext.getApplicationInfo().targetSdkVersion >= 11;
        ImageView imageView = new ImageView(this.mContext);
        this.mTrackImage = imageView;
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        ImageView imageView2 = new ImageView(this.mContext);
        this.mThumbImage = imageView2;
        imageView2.setScaleType(ImageView.ScaleType.FIT_XY);
        View view = new View(this.mContext);
        this.mPreviewImage = view;
        view.setAlpha(0.0f);
        TextView createPreviewTextView = createPreviewTextView(this.mContext);
        this.mPrimaryText = createPreviewTextView;
        TextView createPreviewTextView2 = createPreviewTextView(this.mContext);
        this.mSecondaryText = createPreviewTextView2;
        setStyle(styleResId);
        ViewGroupOverlay overlay = listView.getOverlay();
        this.mOverlay = overlay;
        overlay.add(imageView);
        overlay.add(imageView2);
        overlay.add(view);
        overlay.add(createPreviewTextView);
        overlay.add(createPreviewTextView2);
        this.mPreviewMarginEnd = this.mContext.getResources().getDimensionPixelOffset(R.dimen.fastscroll_preview_margin_end);
        this.mThumbMarginEnd = this.mContext.getResources().getDimensionPixelOffset(R.dimen.fastscroll_thumb_margin_end);
        this.mAdditionalTouchArea = this.mContext.getResources().getDimension(R.dimen.tw_fluid_scroller_additional_touch_area);
        this.mTrackPadding = this.mContext.getResources().getDimensionPixelOffset(R.dimen.sem_fast_scroller_track_padding);
        this.mAdditionalBottomPadding = this.mContext.getResources().getDimensionPixelOffset(R.dimen.sem_fast_scroller_additional_bottom_padding);
        int i = this.mPreviewPadding;
        createPreviewTextView.setPadding(i, 0, i, 0);
        int i2 = this.mPreviewPadding;
        createPreviewTextView2.setPadding(i2, 0, i2, 0);
        getSectionsFromIndexer();
        updateLongList(this.mOldChildCount, this.mOldItemCount);
        setScrollbarPosition(listView.getVerticalScrollbarPosition());
        postAutoHide();
    }

    private void updateAppearance() {
        TypedValue outValue = new TypedValue();
        this.mContext.getTheme().resolveAttribute(16843827, outValue, true);
        this.mColorPrimary = this.mContext.getResources().getColor(outValue.resourceId, null);
        this.mThumbBackgroundColor = this.mUseOpenThemeResources ? this.mContext.getResources().getColor(R.color.sem_fast_scrollbar_bg_color) : this.mContext.getResources().getColor(R.color.sem_fast_scrollbar_bg_color_app_widget);
        this.mTrackImage.lambda$setImageURIAsync$2(this.mTrackDrawable);
        Drawable drawable = this.mTrackDrawable;
        int width = drawable != null ? Math.max(0, drawable.getIntrinsicWidth()) : 0;
        Drawable drawable2 = this.mThumbDrawable;
        if (drawable2 != null) {
            drawable2.setTint(this.mThumbBackgroundColor);
        }
        this.mThumbImage.lambda$setImageURIAsync$2(this.mThumbDrawable);
        this.mThumbImage.setMinimumWidth(this.mThumbMinWidth);
        this.mThumbImage.setMinimumHeight(this.mThumbMinHeight);
        Drawable drawable3 = this.mThumbDrawable;
        if (drawable3 != null) {
            width = Math.max(width, drawable3.getIntrinsicWidth());
        }
        this.mWidth = Math.max(width, this.mThumbMinWidth);
        this.mPreviewImage.setMinimumWidth(this.mPreviewMinWidth);
        this.mPreviewImage.setMinimumHeight(this.mPreviewMinHeight);
        int i = this.mTextAppearance;
        if (i != 0) {
            this.mPrimaryText.setTextAppearance(this.mContext, i);
            this.mSecondaryText.setTextAppearance(this.mContext, this.mTextAppearance);
        }
        ColorStateList colorStateList = this.mTextColor;
        if (colorStateList != null) {
            this.mPrimaryText.setTextColor(colorStateList);
            this.mSecondaryText.setTextColor(this.mTextColor);
        }
        float f = this.mTextSize;
        if (f > 0.0f) {
            this.mPrimaryText.setTextSize(0, f);
            this.mSecondaryText.setTextSize(0, this.mTextSize);
        }
        int textMinSize = Math.max(0, this.mPreviewMinHeight);
        this.mPrimaryText.setMinimumWidth(this.mPreviewMinWidth);
        this.mPrimaryText.setMinimumHeight(textMinSize);
        this.mPrimaryText.setIncludeFontPadding(false);
        this.mSecondaryText.setMinimumWidth(this.mPreviewMinWidth);
        this.mSecondaryText.setMinimumHeight(textMinSize);
        this.mSecondaryText.setIncludeFontPadding(false);
        this.mOrientation = this.mContext.getResources().getConfiguration().orientation;
        refreshDrawablePressedState();
    }

    public void setStyle(int resId) {
        TypedArray ta = this.mContext.obtainStyledAttributes(null, R.styleable.FastScroll, 16843767, resId);
        int N = ta.getIndexCount();
        for (int i = 0; i < N; i++) {
            int index = ta.getIndex(i);
            switch (index) {
                case 0:
                    this.mTextAppearance = ta.getResourceId(index, 0);
                    break;
                case 1:
                    this.mTextSize = ta.getDimensionPixelSize(index, 0);
                    break;
                case 2:
                    this.mTextColor = ta.getColorStateList(index);
                    break;
                case 3:
                    this.mPreviewPadding = ta.getDimensionPixelSize(index, 0);
                    break;
                case 4:
                    this.mPreviewMinWidth = ta.getDimensionPixelSize(index, 0);
                    break;
                case 5:
                    this.mPreviewMinHeight = ta.getDimensionPixelSize(index, 0);
                    break;
                case 6:
                    this.mThumbPosition = ta.getInt(index, 0);
                    break;
                case 7:
                    this.mPreviewResId[0] = ta.getResourceId(index, 0);
                    break;
                case 8:
                    this.mPreviewResId[1] = ta.getResourceId(index, 0);
                    break;
                case 9:
                    this.mOverlayPosition = ta.getInt(index, 0);
                    break;
                case 10:
                    this.mThumbDrawable = ta.getDrawable(index);
                    break;
                case 11:
                    this.mThumbMinHeight = ta.getDimensionPixelSize(index, 0);
                    break;
                case 12:
                    this.mThumbMinWidth = ta.getDimensionPixelSize(index, 0);
                    break;
                case 13:
                    this.mTrackDrawable = ta.getDrawable(index);
                    break;
            }
        }
        ta.recycle();
        updateAppearance();
    }

    public void remove() {
        this.mOverlay.remove(this.mTrackImage);
        this.mOverlay.remove(this.mThumbImage);
        this.mOverlay.remove(this.mPreviewImage);
        this.mOverlay.remove(this.mPrimaryText);
        this.mOverlay.remove(this.mSecondaryText);
    }

    public void setEnabled(boolean enabled) {
        Log.d(TAG, "setEnabled() enabled = " + enabled);
        if (this.mEnabled != enabled) {
            this.mEnabled = enabled;
            onStateDependencyChanged(true);
        }
    }

    public boolean isEnabled() {
        return this.mEnabled && (this.mLongList || this.mAlwaysShow);
    }

    public void setAlwaysShow(boolean alwaysShow) {
        if (this.mAlwaysShow != alwaysShow) {
            this.mAlwaysShow = alwaysShow;
            onStateDependencyChanged(false);
        }
    }

    public boolean isAlwaysShowEnabled() {
        return this.mAlwaysShow;
    }

    private void onStateDependencyChanged(boolean peekIfEnabled) {
        if (isEnabled()) {
            if (isAlwaysShowEnabled()) {
                setState(1);
            } else if (this.mState == 1) {
                postAutoHide();
            } else if (peekIfEnabled) {
                setState(1);
                postAutoHide();
            }
        } else {
            stop();
        }
        this.mList.resolvePadding();
    }

    public void setScrollBarStyle(int style) {
        if (this.mScrollBarStyle != style) {
            this.mScrollBarStyle = style;
            updateLayout();
        }
    }

    public void stop() {
        setState(0);
    }

    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1, types: [boolean] */
    /* JADX WARN: Type inference failed for: r0v3 */
    public void setScrollbarPosition(int position) {
        if (position == 0) {
            position = this.mList.isLayoutRtl() ? 1 : 2;
        }
        if (this.mScrollbarPosition != position) {
            this.mScrollbarPosition = position;
            ?? r0 = position == 1 ? 0 : 1;
            this.mLayoutFromRight = r0;
            int previewResId = this.mPreviewResId[r0];
            this.mPreviewImage.setBackgroundResource(previewResId);
            this.mPreviewImage.getBackground().setTintMode(PorterDuff.Mode.MULTIPLY);
            this.mPreviewImage.getBackground().setTint(this.mColorPrimary);
            updateLayout();
        }
    }

    public int getWidth() {
        return this.mWidth;
    }

    public int getEffectState() {
        return this.mEffectState;
    }

    public float getScrollY() {
        return this.mScrollY;
    }

    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        updateLayout();
    }

    public void onItemCountChanged(int childCount, int itemCount) {
        if (this.mOldItemCount != itemCount || this.mOldChildCount != childCount) {
            this.mOldItemCount = itemCount;
            this.mOldChildCount = childCount;
            boolean hasMoreItems = itemCount - childCount > 0;
            if (hasMoreItems && this.mState != 2) {
                int firstVisibleItem = this.mList.getFirstVisiblePosition();
                setThumbPos(getPosFromItemCount(firstVisibleItem, childCount, itemCount));
            }
            updateLongList(childCount, itemCount);
        }
    }

    private void updateLongList(int childCount, int itemCount) {
        boolean longList = childCount > 0 && (this.mList.canScrollList(1) || this.mList.canScrollList(-1));
        if (this.mLongList != longList) {
            this.mLongList = longList;
            onStateDependencyChanged(true);
        }
    }

    private TextView createPreviewTextView(Context context) {
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(-2, -2);
        TextView textView = new TextView(context);
        textView.setLayoutParams(params);
        textView.setSingleLine(true);
        textView.setEllipsize(TextUtils.TruncateAt.MIDDLE);
        textView.setGravity(17);
        textView.setAlpha(0.0f);
        textView.setLayoutDirection(this.mList.getLayoutDirection());
        return textView;
    }

    public void updateLayout() {
        if (this.mUpdatingLayout) {
            return;
        }
        this.mUpdatingLayout = true;
        updateContainerRect();
        layoutThumb();
        layoutTrack();
        updateOffsetAndRange();
        this.mUpdatingLayout = false;
        Rect bounds = this.mTempBounds;
        measurePreview(this.mPrimaryText, bounds);
        applyLayout(this.mPrimaryText, bounds);
        measurePreview(this.mSecondaryText, bounds);
        applyLayout(this.mSecondaryText, bounds);
        bounds.left -= this.mPreviewImage.getPaddingLeft();
        bounds.top -= this.mPreviewImage.getPaddingTop();
        bounds.right += this.mPreviewImage.getPaddingRight();
        bounds.bottom += this.mPreviewImage.getPaddingBottom();
        applyLayout(this.mPreviewImage, bounds);
    }

    private void applyLayout(View view, Rect bounds) {
        view.layout(bounds.left, bounds.top, bounds.right, bounds.bottom);
        view.setPivotX(this.mLayoutFromRight ? bounds.right - bounds.left : 0.0f);
    }

    private void measurePreview(View v, Rect out) {
        Rect margins = this.mTempMargins;
        margins.left = this.mPreviewImage.getPaddingLeft();
        margins.top = this.mPreviewImage.getPaddingTop();
        margins.right = this.mPreviewImage.getPaddingRight();
        margins.bottom = this.mPreviewImage.getPaddingBottom();
        if (this.mOverlayPosition == 0) {
            measureFloating(v, margins, out);
        } else {
            measureViewToSide(v, this.mThumbImage, margins, out);
        }
    }

    private void measureViewToSide(View view, View adjacent, Rect margins, Rect out) {
        int marginLeft;
        int marginRight;
        int maxWidth;
        int left;
        int right;
        if (this.mLayoutFromRight) {
            if (adjacent == null) {
                marginRight = this.mThumbMarginEnd;
                marginLeft = 0;
            } else {
                marginRight = this.mPreviewMarginEnd;
                marginLeft = 0;
            }
        } else if (adjacent == null) {
            marginLeft = this.mThumbMarginEnd;
            marginRight = 0;
        } else {
            marginLeft = this.mPreviewMarginEnd;
            marginRight = 0;
        }
        Rect container = this.mContainerRect;
        int containerWidth = container.width();
        if (adjacent == null) {
            maxWidth = containerWidth;
        } else if (this.mLayoutFromRight) {
            maxWidth = adjacent.getLeft();
        } else {
            int maxWidth2 = adjacent.getRight();
            maxWidth = containerWidth - maxWidth2;
        }
        int adjMaxHeight = Math.max(0, container.height());
        int adjMaxWidth = Math.max(0, (maxWidth - marginLeft) - marginRight);
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(adjMaxWidth, Integer.MIN_VALUE);
        int heightMeasureSpec = View.MeasureSpec.makeSafeMeasureSpec(adjMaxHeight, 0);
        view.measure(widthMeasureSpec, heightMeasureSpec);
        int width = Math.min(adjMaxWidth, view.getMeasuredWidth());
        if (this.mLayoutFromRight) {
            right = (adjacent == null ? container.right : adjacent.getLeft()) - marginRight;
            left = right - width;
        } else {
            left = (adjacent == null ? container.left : adjacent.getRight()) + marginLeft;
            right = left + width;
        }
        int bottom = 0 + view.getMeasuredHeight();
        out.set(left, 0, right, bottom);
    }

    private void measureFloating(View preview, Rect margins, Rect out) {
        int marginLeft;
        int marginTop;
        int marginRight;
        if (margins == null) {
            marginLeft = 0;
            marginTop = 0;
            marginRight = 0;
        } else {
            marginLeft = margins.left;
            marginTop = margins.top;
            marginRight = margins.right;
        }
        Rect container = this.mContainerRect;
        int containerWidth = container.width();
        int adjMaxHeight = Math.max(0, container.height());
        int adjMaxWidth = Math.max(0, (containerWidth - marginLeft) - marginRight);
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(adjMaxWidth, Integer.MIN_VALUE);
        int heightMeasureSpec = View.MeasureSpec.makeSafeMeasureSpec(adjMaxHeight, 0);
        preview.measure(widthMeasureSpec, heightMeasureSpec);
        int containerHeight = container.height();
        int width = preview.getMeasuredWidth();
        int top = (containerHeight / 10) + marginTop + container.top;
        int bottom = preview.getMeasuredHeight() + top;
        int left = ((containerWidth - width) / 2) + container.left;
        int marginLeft2 = left + width;
        out.set(left, top, marginLeft2, bottom);
    }

    private void updateContainerRect() {
        AbsListView list = this.mList;
        list.resolvePadding();
        Rect container = this.mContainerRect;
        container.left = 0;
        container.top = this.mScrollBarTopPadding;
        container.right = list.getWidth();
        container.bottom = list.getHeight() - this.mScrollBarBottomPadding;
        int scrollbarStyle = this.mScrollBarStyle;
        if (scrollbarStyle == 16777216 || scrollbarStyle == 0) {
            container.left += list.getPaddingLeft();
            container.top += list.getPaddingTop();
            container.right -= list.getPaddingRight();
            container.bottom -= list.getPaddingBottom();
            if (scrollbarStyle == 16777216) {
                int width = getWidth();
                if (this.mScrollbarPosition == 2) {
                    container.right += width;
                } else {
                    container.left -= width;
                }
            }
        }
    }

    private void layoutThumb() {
        Rect bounds = this.mTempBounds;
        measureViewToSide(this.mThumbImage, null, null, bounds);
        applyLayout(this.mThumbImage, bounds);
    }

    private void layoutTrack() {
        int thumbHalfHeight;
        int top;
        View track = this.mTrackImage;
        View thumb = this.mThumbImage;
        Rect container = this.mContainerRect;
        int maxWidth = Math.max(0, container.width());
        int maxHeight = Math.max(0, container.height());
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(maxWidth, Integer.MIN_VALUE);
        int heightMeasureSpec = View.MeasureSpec.makeSafeMeasureSpec(maxHeight, 0);
        track.measure(widthMeasureSpec, heightMeasureSpec);
        if (this.mThumbPosition == 1) {
            thumbHalfHeight = container.top + this.mTrackPadding;
            top = (container.bottom - this.mTrackPadding) - this.mAdditionalBottomPadding;
        } else {
            int top2 = thumb.getHeight();
            int thumbHalfHeight2 = top2 / 2;
            int top3 = container.top + thumbHalfHeight2 + this.mTrackPadding;
            int i = ((container.bottom - thumbHalfHeight2) - this.mTrackPadding) - this.mAdditionalBottomPadding;
            thumbHalfHeight = top3;
            top = i;
        }
        int trackWidth = track.getMeasuredWidth();
        int left = thumb.getLeft() + ((thumb.getWidth() - trackWidth) / 2);
        int right = left + trackWidth;
        track.layout(left, thumbHalfHeight, right, top);
    }

    private void updateOffsetAndRange() {
        float min;
        float max;
        View trackImage = this.mTrackImage;
        View thumbImage = this.mThumbImage;
        if (this.mThumbPosition == 1) {
            float halfThumbHeight = thumbImage.getHeight() / 2.0f;
            min = trackImage.getTop() + halfThumbHeight;
            max = trackImage.getBottom() - halfThumbHeight;
        } else {
            min = trackImage.getTop();
            max = trackImage.getBottom();
        }
        this.mThumbOffset = min;
        this.mThumbRange = max - min;
    }

    public void setState(int state) {
        this.mList.removeCallbacks(this.mDeferHide);
        if (this.mAlwaysShow && state == 0) {
            state = 1;
        }
        if (state == this.mState) {
            return;
        }
        switch (state) {
            case 0:
                transitionToHidden();
                break;
            case 1:
                Drawable drawable = this.mThumbDrawable;
                if (drawable != null) {
                    drawable.setTint(this.mThumbBackgroundColor);
                }
                transitionToVisible();
                break;
            case 2:
                Drawable drawable2 = this.mThumbDrawable;
                if (drawable2 != null) {
                    drawable2.setTint(this.mColorPrimary);
                }
                transitionPreviewLayout(this.mCurrentSection);
                break;
        }
        this.mState = state;
        refreshDrawablePressedState();
    }

    private void refreshDrawablePressedState() {
        boolean isPressed = this.mState == 2;
        this.mThumbImage.setPressed(isPressed);
        this.mTrackImage.setPressed(isPressed);
    }

    private void transitionToHidden() {
        Log.d(TAG, "transitionToHidden() mState = " + this.mState);
        if (this.mState != 2) {
            this.mList.semSetupGoToTop(0);
        } else {
            this.mList.semAutoHide(1);
        }
        int duration = 0;
        this.mShowingPreview = false;
        this.mCurrentSection = -1;
        AnimatorSet animatorSet = this.mDecorAnimation;
        if (animatorSet != null) {
            animatorSet.cancel();
            duration = 167;
        }
        Animator fadeOut = groupAnimatorOfFloat(View.ALPHA, 0.0f, this.mThumbImage, this.mTrackImage, this.mPreviewImage, this.mPrimaryText, this.mSecondaryText).setDuration(duration);
        AnimatorSet animatorSet2 = new AnimatorSet();
        this.mDecorAnimation = animatorSet2;
        animatorSet2.playTogether(fadeOut);
        this.mDecorAnimation.setInterpolator(new PathInterpolator(0.33f, 0.0f, 0.3f, 1.0f));
        this.mDecorAnimation.start();
    }

    private void transitionToVisible() {
        Log.d(TAG, "transitionToVisible()");
        AnimatorSet animatorSet = this.mDecorAnimation;
        if (animatorSet != null) {
            animatorSet.cancel();
        }
        this.mList.semSetupGoToTop(1);
        Animator fadeIn = groupAnimatorOfFloat(View.ALPHA, 1.0f, this.mThumbImage, this.mTrackImage).setDuration(167L);
        Animator fadeOut = groupAnimatorOfFloat(View.ALPHA, 0.0f, this.mPreviewImage, this.mPrimaryText, this.mSecondaryText).setDuration(167L);
        AnimatorSet animatorSet2 = new AnimatorSet();
        this.mDecorAnimation = animatorSet2;
        animatorSet2.playTogether(fadeIn, fadeOut);
        this.mDecorAnimation.setInterpolator(new PathInterpolator(0.33f, 0.0f, 0.3f, 1.0f));
        this.mShowingPreview = false;
        this.mDecorAnimation.start();
    }

    private void transitionToDragging() {
        Log.d(TAG, "transitionToDragging()");
        AnimatorSet animatorSet = this.mDecorAnimation;
        if (animatorSet != null) {
            animatorSet.cancel();
        }
        Animator fadeIn = groupAnimatorOfFloat(View.ALPHA, 1.0f, this.mThumbImage, this.mTrackImage, this.mPreviewImage).setDuration(167L);
        AnimatorSet animatorSet2 = new AnimatorSet();
        this.mDecorAnimation = animatorSet2;
        animatorSet2.playTogether(fadeIn);
        this.mDecorAnimation.setInterpolator(new PathInterpolator(0.33f, 0.0f, 0.3f, 1.0f));
        this.mDecorAnimation.start();
        this.mShowingPreview = true;
    }

    private void postAutoHide() {
        this.mList.removeCallbacks(this.mDeferHide);
        this.mList.postDelayed(this.mDeferHide, FADE_TIMEOUT);
    }

    public void onScroll(int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (!isEnabled()) {
            setState(0);
            return;
        }
        if ((this.mList.canScrollList(1) || this.mList.canScrollList(-1)) && this.mState != 2) {
            float f = this.mOldThumbPosition;
            if (f != -1.0f) {
                setThumbPos(f);
                this.mOldThumbPosition = -1.0f;
            } else {
                setThumbPos(getPosFromItemCount(firstVisibleItem, visibleItemCount, totalItemCount));
            }
        }
        this.mScrollCompleted = true;
        if (this.mState != 2) {
            setState(1);
            postAutoHide();
        }
    }

    private void getSectionsFromIndexer() {
        this.mSectionIndexer = null;
        Adapter adapter = this.mList.getAdapter();
        if (adapter instanceof HeaderViewListAdapter) {
            this.mHeaderCount = ((HeaderViewListAdapter) adapter).getHeadersCount();
            adapter = ((HeaderViewListAdapter) adapter).getWrappedAdapter();
        }
        if (adapter instanceof ExpandableListConnector) {
            ExpandableListAdapter expAdapter = ((ExpandableListConnector) adapter).getAdapter();
            if (expAdapter instanceof SectionIndexer) {
                SectionIndexer sectionIndexer = (SectionIndexer) expAdapter;
                this.mSectionIndexer = sectionIndexer;
                this.mListAdapter = adapter;
                this.mSections = sectionIndexer.getSections();
                return;
            }
            return;
        }
        if (adapter instanceof SectionIndexer) {
            this.mListAdapter = adapter;
            SectionIndexer sectionIndexer2 = (SectionIndexer) adapter;
            this.mSectionIndexer = sectionIndexer2;
            this.mSections = sectionIndexer2.getSections();
            return;
        }
        this.mListAdapter = adapter;
        this.mSections = null;
    }

    public void onSectionsChanged() {
        this.mListAdapter = null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x003c, code lost:
    
        if (r7 <= 0) goto L129;
     */
    /* JADX WARN: Code restructure failed: missing block: B:11:0x003e, code lost:
    
        r7 = r7 - 1;
        r11 = r21.mSectionIndexer.getPositionForSection(r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x0046, code lost:
    
        if (r11 == r8) goto L84;
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x004b, code lost:
    
        if (r7 != 0) goto L130;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x004d, code lost:
    
        r9 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0048, code lost:
    
        r12 = r7;
        r9 = r7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x004f, code lost:
    
        r14 = r13 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0051, code lost:
    
        if (r14 >= r5) goto L131;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0059, code lost:
    
        if (r21.mSectionIndexer.getPositionForSection(r14) != r10) goto L132;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x005b, code lost:
    
        r14 = r14 + 1;
        r13 = r13 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0060, code lost:
    
        r15 = r12 / r5;
        r2 = r13 / r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0068, code lost:
    
        if (r3 != 0) goto L94;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x006a, code lost:
    
        r4 = Float.MAX_VALUE;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0076, code lost:
    
        if (r12 != r6) goto L99;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x007c, code lost:
    
        if ((r22 - r15) >= r4) goto L99;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x007e, code lost:
    
        r5 = r11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x008d, code lost:
    
        r5 = android.util.MathUtils.constrain(r5, 0, r3 - 1);
        r2 = r21.mList;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x009a, code lost:
    
        if ((r2 instanceof android.widget.ExpandableListView) == false) goto L103;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x009c, code lost:
    
        r2 = (android.widget.ExpandableListView) r2;
        r2.setSelectionFromTop(r2.getFlatListPosition(android.widget.ExpandableListView.getPackedPositionForGroup(r21.mHeaderCount + r5)), 0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x00b9, code lost:
    
        if ((r2 instanceof android.widget.ListView) == false) goto L106;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x00bb, code lost:
    
        ((android.widget.ListView) r2).setSelectionFromTop(r21.mHeaderCount + r5, 0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x00c4, code lost:
    
        r2.setSelection(r21.mHeaderCount + r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x0080, code lost:
    
        r5 = ((int) (((r10 - r11) * (r22 - r15)) / (r2 - r15))) + r11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x0070, code lost:
    
        r4 = 0.125f / r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x003a, code lost:
    
        if (r10 == r8) goto L80;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void scrollTo(float r22) {
        /*
            Method dump skipped, instructions count: 336
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.widget.SemFastScroller.scrollTo(float):void");
    }

    private boolean transitionPreviewLayout(int sectionIndex) {
        TextView showing;
        TextView target;
        AnimatorSet.Builder builder;
        Object section;
        Object[] sections = this.mSections;
        String text = null;
        if (sections != null && sectionIndex >= 0 && sectionIndex < sections.length && (section = sections[sectionIndex]) != null) {
            text = section.toString();
        }
        Rect bounds = this.mTempBounds;
        View preview = this.mPreviewImage;
        if (this.mShowingPrimary) {
            showing = this.mPrimaryText;
            target = this.mSecondaryText;
        } else {
            showing = this.mSecondaryText;
            target = this.mPrimaryText;
        }
        target.setText(text);
        measurePreview(target, bounds);
        applyLayout(target, bounds);
        int i = this.mState;
        if (i == 1) {
            showing.setText("");
        } else if (i == 2 && target.getText() == showing.getText()) {
            return !TextUtils.isEmpty(text);
        }
        AnimatorSet animatorSet = this.mPreviewAnimation;
        if (animatorSet != null) {
            animatorSet.cancel();
        }
        Animator showTarget = animateAlpha(target, 1.0f).setDuration(0L);
        Animator hideShowing = animateAlpha(showing, 0.0f).setDuration(0L);
        hideShowing.addListener(this.mSwitchPrimaryListener);
        bounds.left -= preview.getPaddingLeft();
        bounds.top -= preview.getPaddingTop();
        bounds.right += preview.getPaddingRight();
        bounds.bottom += preview.getPaddingBottom();
        Animator resizePreview = animateBounds(preview, bounds);
        resizePreview.setDuration(100L);
        AnimatorSet animatorSet2 = new AnimatorSet();
        this.mPreviewAnimation = animatorSet2;
        AnimatorSet.Builder builder2 = animatorSet2.play(hideShowing).with(showTarget);
        builder2.with(resizePreview);
        int previewWidth = (preview.getWidth() - preview.getPaddingLeft()) - preview.getPaddingRight();
        int targetWidth = target.getWidth();
        if (targetWidth > previewWidth) {
            target.setScaleX(previewWidth / targetWidth);
            Animator scaleAnim = animateScaleX(target, 1.0f).setDuration(100L);
            builder = builder2;
            builder.with(scaleAnim);
        } else {
            builder = builder2;
            target.setScaleX(1.0f);
        }
        int showingWidth = showing.getWidth();
        if (showingWidth > targetWidth) {
            float scale = targetWidth / showingWidth;
            Animator scaleAnim2 = animateScaleX(showing, scale).setDuration(100L);
            builder.with(scaleAnim2);
        }
        this.mPreviewAnimation.setInterpolator(new PathInterpolator(0.33f, 0.0f, 0.3f, 1.0f));
        this.mPreviewAnimation.start();
        return !TextUtils.isEmpty(text);
    }

    private void setThumbPos(float position) {
        Rect container = this.mContainerRect;
        int top = container.top;
        int bottom = container.bottom;
        if (position > 1.0f) {
            position = 1.0f;
        } else if (position < 0.0f) {
            position = 0.0f;
        }
        float thumbMiddle = (this.mThumbRange * position) + this.mThumbOffset;
        this.mThumbImage.setTranslationY(thumbMiddle - (r4.getHeight() / 2.0f));
        View previewImage = this.mPreviewImage;
        float previewHalfHeight = previewImage.getHeight() / 2.0f;
        float minP = top + previewHalfHeight;
        float maxP = bottom - previewHalfHeight;
        float previewMiddle = MathUtils.constrain(thumbMiddle, minP, maxP);
        float previewTop = previewMiddle - previewHalfHeight;
        previewImage.setTranslationY(previewTop);
        this.mPrimaryText.setTranslationY(previewTop);
        this.mSecondaryText.setTranslationY(previewTop);
    }

    private float getPosFromMotionEvent(float y) {
        float f = this.mThumbRange;
        if (f <= 0.0f) {
            return 0.0f;
        }
        return MathUtils.constrain((y - this.mThumbOffset) / f, 0.0f, 1.0f);
    }

    private float getPosFromItemCount(int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        float incrementalPos;
        float result;
        int maxSize;
        int currentVisibleSize;
        int nextSectionPos;
        float posWithinSection;
        int nextSectionPos2;
        Object[] objArr;
        SectionIndexer sectionIndexer = this.mSectionIndexer;
        if (sectionIndexer == null || this.mListAdapter == null) {
            getSectionsFromIndexer();
        }
        if (visibleItemCount == 0 || totalItemCount == 0) {
            return 0.0f;
        }
        boolean hasSections = false;
        View child = this.mList.getChildAt(0);
        if (child == null || child.getHeight() == 0) {
            incrementalPos = 0.0f;
        } else {
            incrementalPos = (this.mList.getPaddingTop() - child.getTop()) / child.getHeight();
        }
        if (sectionIndexer != null && (objArr = this.mSections) != null && objArr.length > 0) {
            hasSections = true;
        }
        if (!hasSections || !this.mMatchDragPosition) {
            if (visibleItemCount == totalItemCount) {
                return 0.0f;
            }
            int span = 1;
            AbsListView absListView = this.mList;
            if (absListView instanceof GridView) {
                span = ((GridView) absListView).getNumColumns();
            }
            result = (firstVisibleItem + (span * incrementalPos)) / totalItemCount;
        } else {
            int i = this.mHeaderCount;
            firstVisibleItem -= i;
            if (firstVisibleItem < 0) {
                return 0.0f;
            }
            totalItemCount -= i;
            int section = sectionIndexer.getSectionForPosition(firstVisibleItem);
            int sectionPos = sectionIndexer.getPositionForSection(section);
            int sectionCount = this.mSections.length;
            if (section < sectionCount - 1) {
                if (section + 1 < sectionCount) {
                    nextSectionPos2 = sectionIndexer.getPositionForSection(section + 1);
                } else {
                    nextSectionPos2 = totalItemCount - 1;
                }
                nextSectionPos = nextSectionPos2 - sectionPos;
            } else {
                nextSectionPos = totalItemCount - sectionPos;
            }
            if (nextSectionPos == 0) {
                posWithinSection = 0.0f;
            } else {
                float posWithinSection2 = firstVisibleItem;
                posWithinSection = ((posWithinSection2 + incrementalPos) - sectionPos) / nextSectionPos;
            }
            result = (section + posWithinSection) / sectionCount;
        }
        if (firstVisibleItem > 0 && firstVisibleItem + visibleItemCount == totalItemCount) {
            View lastChild = this.mList.getChildAt(visibleItemCount - 1);
            int bottomPadding = this.mList.getPaddingBottom();
            if (this.mList.getClipToPadding()) {
                maxSize = lastChild.getHeight();
                currentVisibleSize = (this.mList.getHeight() - bottomPadding) - lastChild.getTop();
            } else {
                int maxSize2 = lastChild.getHeight();
                maxSize = maxSize2 + bottomPadding;
                currentVisibleSize = this.mList.getHeight() - lastChild.getTop();
            }
            if (currentVisibleSize > 0 && maxSize > 0) {
                return result + ((1.0f - result) * (currentVisibleSize / maxSize));
            }
            return result;
        }
        return result;
    }

    private void cancelFling() {
        MotionEvent cancelFling = MotionEvent.obtain(0L, 0L, 3, 0.0f, 0.0f, 0);
        this.mList.onTouchEvent(cancelFling);
        cancelFling.recycle();
    }

    private void cancelPendingDrag() {
        this.mPendingDrag = -1L;
    }

    private void startPendingDrag() {
        this.mPendingDrag = SystemClock.uptimeMillis() + TAP_TIMEOUT;
    }

    private void beginDrag() {
        Log.d(TAG, "beginDrag() !!!");
        this.mPendingDrag = -1L;
        if (this.mListAdapter == null) {
            getSectionsFromIndexer();
        }
        this.mList.requestDisallowInterceptTouchEvent(true);
        this.mList.reportScrollStateChange(1);
        cancelFling();
        setState(2);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!isEnabled()) {
            return false;
        }
        switch (ev.getActionMasked()) {
            case 0:
                Log.d(TAG, "onInterceptTouchEvent() ACTION_DOWN ev.getY() = " + ev.getY());
                if (isPointInside(ev.getX(), ev.getY())) {
                    this.mList.performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(26));
                    if (!this.mList.isInScrollingContainer()) {
                        return true;
                    }
                    this.mInitialTouchY = ev.getY();
                    startPendingDrag();
                }
                return false;
            case 1:
            case 3:
                cancelPendingDrag();
                return false;
            case 2:
                if (!isPointInside(ev.getX(), ev.getY())) {
                    cancelPendingDrag();
                } else {
                    long j = this.mPendingDrag;
                    if (j >= 0 && j <= SystemClock.uptimeMillis()) {
                        beginDrag();
                        float pos = getPosFromMotionEvent(this.mInitialTouchY);
                        this.mOldThumbPosition = pos;
                        scrollTo(pos);
                        Log.d(TAG, "onInterceptTouchEvent() ACTION_MOVE pendingdrag open()");
                        return onTouchEvent(ev);
                    }
                }
                return false;
            default:
                return false;
        }
    }

    public boolean onInterceptHoverEvent(MotionEvent ev) {
        if (!isEnabled()) {
            return false;
        }
        int actionMasked = ev.getActionMasked();
        if ((actionMasked == 9 || actionMasked == 7) && this.mState == 0 && isPointInside(ev.getX(), ev.getY())) {
            setState(1);
            postAutoHide();
        }
        return false;
    }

    public PointerIcon onResolvePointerIcon(MotionEvent event, int pointerIndex) {
        if (this.mState == 2 || isPointInside(event.getX(), event.getY())) {
            return PointerIcon.getSystemIcon(this.mList.getContext(), 1000);
        }
        return null;
    }

    public boolean onTouchEvent(MotionEvent me) {
        Rect container = this.mContainerRect;
        int top = container.top;
        int bottom = container.bottom;
        View trackImage = this.mTrackImage;
        float min = trackImage.getTop();
        float max = trackImage.getBottom();
        this.mScrollY = me.getY();
        if (!isEnabled()) {
            return false;
        }
        switch (me.getActionMasked()) {
            case 0:
                if (isPointInside(me.getX(), me.getY()) && !this.mList.isInScrollingContainer()) {
                    beginDrag();
                    this.mEffectState = 1;
                    Log.d(TAG, "onTouchEvent() ACTION_DOWN.. open() called with posY " + me.getY());
                    return true;
                }
                return false;
            case 1:
                if (this.mPendingDrag >= 0) {
                    beginDrag();
                    float pos = getPosFromMotionEvent(me.getY());
                    this.mOldThumbPosition = pos;
                    setThumbPos(pos);
                    scrollTo(pos);
                    this.mEffectState = 1;
                    Log.d(TAG, "onTouchEvent() ACTION_UP.. open() called with posY " + me.getY());
                }
                if (this.mState == 2) {
                    this.mList.requestDisallowInterceptTouchEvent(false);
                    this.mList.reportScrollStateChange(0);
                    setState(1);
                    postAutoHide();
                    this.mEffectState = 0;
                    this.mScrollY = 0.0f;
                    return true;
                }
                return false;
            case 2:
                Log.d(TAG, "onTouchEvent() ACTION_MOVE.. mState= " + this.mState + ", mInitialTouchY=" + this.mInitialTouchY);
                if (this.mPendingDrag >= 0 && Math.abs(me.getY() - this.mInitialTouchY) > this.mScaledTouchSlop) {
                    beginDrag();
                    float f = this.mScrollY;
                    if (f > top && f < bottom) {
                        Log.d(TAG, "onTouchEvent() ACTION_MOVE 1 mScrollY=" + this.mScrollY + ", min=" + min + ", max=" + max);
                        float f2 = this.mScrollY;
                        if (f2 < top + min) {
                            this.mScrollY = top + min;
                        } else if (f2 > max) {
                            this.mScrollY = max;
                        }
                        this.mEffectState = 1;
                    }
                }
                if (this.mState == 2) {
                    float pos2 = getPosFromMotionEvent(me.getY());
                    this.mOldThumbPosition = pos2;
                    setThumbPos(pos2);
                    if (this.mScrollCompleted) {
                        scrollTo(pos2);
                    }
                    float f3 = this.mScrollY;
                    if (f3 > top && f3 < bottom) {
                        Log.d(TAG, "onTouchEvent() ACTION_MOVE 2 mScrollY=" + this.mScrollY + ", min=" + min + ", max=" + max);
                        float f4 = this.mScrollY;
                        if (f4 < top + min) {
                            this.mScrollY = top + min;
                        } else if (f4 > max) {
                            this.mScrollY = max;
                        }
                        this.mEffectState = 1;
                    }
                    return true;
                }
                return false;
            case 3:
                cancelPendingDrag();
                if (this.mState == 2) {
                    setState(0);
                }
                this.mEffectState = 0;
                this.mScrollY = 0.0f;
                return false;
            default:
                return false;
        }
    }

    private boolean isPointInside(float x, float y) {
        return isPointInsideX(x) && isPointInsideY(y) && this.mState != 0;
    }

    private boolean isPointInsideX(float x) {
        return this.mLayoutFromRight ? x >= ((float) this.mThumbImage.getLeft()) - this.mAdditionalTouchArea : x <= ((float) this.mThumbImage.getRight()) + this.mAdditionalTouchArea;
    }

    private boolean isPointInsideY(float y) {
        float offset = this.mThumbImage.getTranslationY();
        float top = this.mThumbImage.getTop() + offset;
        float bottom = this.mThumbImage.getBottom() + offset;
        return y >= top && y <= bottom;
    }

    private static Animator groupAnimatorOfFloat(Property<View, Float> property, float value, View... views) {
        AnimatorSet animSet = new AnimatorSet();
        AnimatorSet.Builder builder = null;
        for (int i = views.length - 1; i >= 0; i--) {
            Animator anim = ObjectAnimator.ofFloat(views[i], property, value);
            if (builder == null) {
                builder = animSet.play(anim);
            } else {
                builder.with(anim);
            }
        }
        return animSet;
    }

    private static Animator animateScaleX(View v, float target) {
        return ObjectAnimator.ofFloat(v, View.SCALE_X, target);
    }

    private static Animator animateAlpha(View v, float alpha) {
        return ObjectAnimator.ofFloat(v, View.ALPHA, alpha);
    }

    /* renamed from: android.widget.SemFastScroller$3 */
    /* loaded from: classes4.dex */
    class AnonymousClass3 extends IntProperty<View> {
        AnonymousClass3(String name) {
            super(name);
        }

        @Override // android.util.IntProperty
        public void setValue(View object, int value) {
            object.setLeft(value);
        }

        @Override // android.util.Property
        public Integer get(View object) {
            return Integer.valueOf(object.getLeft());
        }
    }

    /* renamed from: android.widget.SemFastScroller$4 */
    /* loaded from: classes4.dex */
    class AnonymousClass4 extends IntProperty<View> {
        AnonymousClass4(String name) {
            super(name);
        }

        @Override // android.util.IntProperty
        public void setValue(View object, int value) {
            object.setTop(value);
        }

        @Override // android.util.Property
        public Integer get(View object) {
            return Integer.valueOf(object.getTop());
        }
    }

    /* renamed from: android.widget.SemFastScroller$5 */
    /* loaded from: classes4.dex */
    class AnonymousClass5 extends IntProperty<View> {
        AnonymousClass5(String name) {
            super(name);
        }

        @Override // android.util.IntProperty
        public void setValue(View object, int value) {
            object.setRight(value);
        }

        @Override // android.util.Property
        public Integer get(View object) {
            return Integer.valueOf(object.getRight());
        }
    }

    /* renamed from: android.widget.SemFastScroller$6 */
    /* loaded from: classes4.dex */
    class AnonymousClass6 extends IntProperty<View> {
        AnonymousClass6(String name) {
            super(name);
        }

        @Override // android.util.IntProperty
        public void setValue(View object, int value) {
            object.setBottom(value);
        }

        @Override // android.util.Property
        public Integer get(View object) {
            return Integer.valueOf(object.getBottom());
        }
    }

    private static Animator animateBounds(View v, Rect bounds) {
        PropertyValuesHolder left = PropertyValuesHolder.ofInt(LEFT, bounds.left);
        PropertyValuesHolder top = PropertyValuesHolder.ofInt(TOP, bounds.top);
        PropertyValuesHolder right = PropertyValuesHolder.ofInt(RIGHT, bounds.right);
        PropertyValuesHolder bottom = PropertyValuesHolder.ofInt(BOTTOM, bounds.bottom);
        return ObjectAnimator.ofPropertyValuesHolder(v, left, top, right, bottom);
    }

    private int getColorWithAlpha(int color, float ratio) {
        int alpha = Math.round(Color.alpha(color) * ratio);
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);
        int newColor = Color.argb(alpha, r, g, b);
        return newColor;
    }

    public void semSetScrollBarBottomPadding(int scrollBarBottomPadding) {
        this.mScrollBarBottomPadding = scrollBarBottomPadding;
        updateLayout();
    }

    public void semSetScrollBarTopPadding(int scrollBarTopPadding) {
        this.mScrollBarTopPadding = scrollBarTopPadding;
        updateLayout();
    }

    public void semSetUseOpenThemeResources(boolean useOpenThemeResources) {
        this.mUseOpenThemeResources = useOpenThemeResources;
        updateAppearance();
    }
}
