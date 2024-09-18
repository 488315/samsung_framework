package com.samsung.android.widget;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.MediaMetrics;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.HapticFeedbackConstants;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroupOverlay;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import com.android.internal.R;

/* loaded from: classes6.dex */
public class SemIndexScrollView extends FrameLayout implements AbsListView.OnScrollListener {
    public static final int GRAVITY_INDEX_BAR_LEFT = 0;
    public static final int GRAVITY_INDEX_BAR_RIGHT = 1;
    private static final float OUT_OF_BOUNDARY = -9999.0f;
    private static final String TAG = "SemIndexScrollView";
    private static final float TRANSPARENCY_VALUE = 0.8f;
    private final boolean debug;
    private Context mContext;
    private String mCurrentIndex;
    private boolean mHasOverlayChild;
    private int mIndexBarGravity;
    IndexScroll mIndexScroll;
    private IndexScrollPreview mIndexScrollPreview;
    private SemAbstractIndexer mIndexer;
    private final IndexerObserver mIndexerObserver;
    private boolean mIsSimpleIndexScroll;
    private int mNumberOfLanguages;
    private OnIndexBarEventListener mOnIndexBarEventListener;
    private final Runnable mPreviewDelayRunnable;
    private boolean mRegisteredDataSetObserver;
    private Typeface mSECRobotoLightRegularFont;
    private long mStartTouchDown;
    private float mTouchY;
    private ViewGroupOverlay mViewGroupOverlay;

    @Deprecated
    /* loaded from: classes6.dex */
    public interface OnIndexBarEventListener {
        @Deprecated
        void onIndexChanged(int i);

        @Deprecated
        void onPressed(float f);

        @Deprecated
        void onReleased(float f);
    }

    @Deprecated
    public SemIndexScrollView(Context context) {
        super(context);
        this.debug = false;
        this.mIndexBarGravity = 1;
        this.mIndexerObserver = new IndexerObserver();
        this.mIsSimpleIndexScroll = false;
        this.mOnIndexBarEventListener = null;
        this.mRegisteredDataSetObserver = false;
        this.mHasOverlayChild = false;
        this.mTouchY = OUT_OF_BOUNDARY;
        this.mStartTouchDown = 0L;
        this.mPreviewDelayRunnable = new Runnable() { // from class: com.samsung.android.widget.SemIndexScrollView.1
            @Override // java.lang.Runnable
            public void run() {
                if (SemIndexScrollView.this.mIndexScrollPreview != null) {
                    SemIndexScrollView.this.mIndexScrollPreview.fadeOutAnimation();
                }
            }
        };
        this.mContext = context;
        this.mCurrentIndex = null;
        init(context, this.mIndexBarGravity);
    }

    @Deprecated
    public SemIndexScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.debug = false;
        this.mIndexBarGravity = 1;
        this.mIndexerObserver = new IndexerObserver();
        this.mIsSimpleIndexScroll = false;
        this.mOnIndexBarEventListener = null;
        this.mRegisteredDataSetObserver = false;
        this.mHasOverlayChild = false;
        this.mTouchY = OUT_OF_BOUNDARY;
        this.mStartTouchDown = 0L;
        this.mPreviewDelayRunnable = new Runnable() { // from class: com.samsung.android.widget.SemIndexScrollView.1
            @Override // java.lang.Runnable
            public void run() {
                if (SemIndexScrollView.this.mIndexScrollPreview != null) {
                    SemIndexScrollView.this.mIndexScrollPreview.fadeOutAnimation();
                }
            }
        };
        this.mContext = context;
        this.mIndexBarGravity = 1;
        init(context, 1);
    }

    private void init(Context context, int gravity) {
        this.mViewGroupOverlay = getOverlay();
        if (this.mIndexScrollPreview == null) {
            IndexScrollPreview indexScrollPreview = new IndexScrollPreview(this.mContext);
            this.mIndexScrollPreview = indexScrollPreview;
            indexScrollPreview.setLayout(0, 0, getWidth(), getHeight());
            this.mViewGroupOverlay.add(this.mIndexScrollPreview);
        }
        this.mHasOverlayChild = true;
        this.mIndexScroll = new IndexScroll(this.mContext, getHeight(), getWidth(), gravity);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.ViewGroup, android.view.View
    public void dispatchDraw(Canvas canvas) {
        IndexScrollPreview indexScrollPreview;
        super.dispatchDraw(canvas);
        IndexScroll indexScroll = this.mIndexScroll;
        if (indexScroll == null) {
            return;
        }
        indexScroll.setDimensions(getWidth(), getHeight());
        String str = this.mCurrentIndex;
        if (str != null && str.length() != 0 && (indexScrollPreview = this.mIndexScrollPreview) != null) {
            indexScrollPreview.setLayout(0, 0, getWidth(), getHeight());
            this.mIndexScrollPreview.invalidate();
        }
        IndexScroll indexScroll2 = this.mIndexScroll;
        if (indexScroll2 != null && indexScroll2.isAlphabetInit()) {
            this.mIndexScroll.draw(canvas);
        }
    }

    @Deprecated
    public void setIndexer(SemAbstractIndexer indexer) {
        SemAbstractIndexer semAbstractIndexer;
        if (indexer == null) {
            throw new IllegalArgumentException("SemIndexView.setIndexer(indexer) : indexer=null.");
        }
        SemAbstractIndexer semAbstractIndexer2 = this.mIndexer;
        if (semAbstractIndexer2 != null && this.mRegisteredDataSetObserver) {
            semAbstractIndexer2.unregisterDataSetObserver(this.mIndexerObserver);
            this.mRegisteredDataSetObserver = false;
        }
        this.mIsSimpleIndexScroll = false;
        this.mIndexer = indexer;
        indexer.registerDataSetObserver(this.mIndexerObserver);
        this.mRegisteredDataSetObserver = true;
        if (this.mIndexScroll.mScrollThumbBgDrawable != null) {
            this.mIndexScroll.mScrollThumbBgDrawable.setColorFilter(this.mIndexScroll.mThumbColor, PorterDuff.Mode.MULTIPLY);
        }
        this.mIndexer.cacheIndexInfo();
        this.mIndexScroll.setAlphabetArray(this.mIndexer.getAlphabetArray(), getFirstAlphabetCharacterIndex(), getLastAlphabetCharacterIndex());
        if (!this.mIsSimpleIndexScroll && (semAbstractIndexer = this.mIndexer) != null && semAbstractIndexer.getLangAlphabetArray() != null) {
            this.mNumberOfLanguages = this.mIndexer.getLangAlphabetArray().length;
        }
    }

    @Deprecated
    public void setSimpleIndexScroll(String[] indexBarChar, int width) {
        if (indexBarChar == null) {
            throw new IllegalArgumentException("SemIndexView.setSimpleIndexScroll(indexBarChar)");
        }
        this.mIsSimpleIndexScroll = true;
        Resources rsrc = this.mContext.getResources();
        setSimpleIndexWidth((int) rsrc.getDimension(R.dimen.sem_indexbar_simpleindex_width));
        if (width != 0) {
            setSimpleIndexWidth(width);
        }
        if (this.mIndexScroll.mScrollThumbBgDrawable != null) {
            this.mIndexScroll.mScrollThumbBgDrawable.setColorFilter(this.mIndexScroll.mThumbColor, PorterDuff.Mode.MULTIPLY);
        }
        this.mIndexScroll.setAlphabetArray(indexBarChar, -1, -1);
    }

    private void setSimpleIndexWidth(int width) {
        IndexScroll indexScroll = this.mIndexScroll;
        if (indexScroll != null) {
            indexScroll.setSimpleIndexScrollWidth(width);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mHasOverlayChild) {
            this.mViewGroupOverlay.remove(this.mIndexScrollPreview);
            this.mHasOverlayChild = false;
        }
        SemAbstractIndexer semAbstractIndexer = this.mIndexer;
        if (semAbstractIndexer != null && this.mRegisteredDataSetObserver) {
            semAbstractIndexer.unregisterDataSetObserver(this.mIndexerObserver);
            this.mRegisteredDataSetObserver = false;
        }
        Runnable runnable = this.mPreviewDelayRunnable;
        if (runnable != null) {
            removeCallbacks(runnable);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.ViewGroup, android.view.View
    @Deprecated
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!this.mHasOverlayChild) {
            this.mViewGroupOverlay.add(this.mIndexScrollPreview);
            this.mHasOverlayChild = true;
        }
        SemAbstractIndexer semAbstractIndexer = this.mIndexer;
        if (semAbstractIndexer != null && !this.mRegisteredDataSetObserver) {
            semAbstractIndexer.registerDataSetObserver(this.mIndexerObserver);
            this.mRegisteredDataSetObserver = true;
        }
    }

    @Deprecated
    public void setIndexBarBackgroundDrawable(Drawable bgDrawable) {
        this.mIndexScroll.mBgDrawableDefault = bgDrawable;
    }

    @Deprecated
    public void setIndexBarTextColor(int textColor) {
        this.mIndexScroll.mTextColorDimmed = textColor;
    }

    @Deprecated
    public void setIndexBarPressedTextColor(int pressedTextColor) {
        this.mIndexScroll.mScrollThumbBgDrawable.setColorFilter(pressedTextColor, PorterDuff.Mode.MULTIPLY);
        this.mIndexScroll.mThumbColor = pressedTextColor;
    }

    @Deprecated
    public void setEffectTextColor(int effectTextColor) {
        this.mIndexScrollPreview.setTextColor(effectTextColor);
    }

    @Deprecated
    public void setEffectBackgroundColor(int effectBackgroundColor) {
        this.mIndexScrollPreview.setBackgroundColor(this.mIndexScroll.getColorWithAlpha(effectBackgroundColor, 0.8f));
    }

    @Deprecated
    public void setIndexBarGravity(int gravity) {
        this.mIndexBarGravity = gravity;
        this.mIndexScroll.setPosition(gravity);
    }

    private int getListViewPosition(String indexPath) {
        SemAbstractIndexer semAbstractIndexer;
        if (indexPath != null && (semAbstractIndexer = this.mIndexer) != null) {
            return semAbstractIndexer.getCachingValue(this.mIndexScroll.getSelectedIndex());
        }
        return -1;
    }

    @Deprecated
    public void setIndexScrollMargin(int topMargin, int bottomMargin) {
        IndexScroll indexScroll = this.mIndexScroll;
        if (indexScroll != null) {
            indexScroll.setIndexScrollBgMargin(topMargin, bottomMargin);
        }
    }

    @Override // android.view.View
    @Deprecated
    public boolean onTouchEvent(MotionEvent ev) {
        super.onTouchEvent(ev);
        return handleMotionEvent(ev);
    }

    private boolean handleMotionEvent(MotionEvent ev) {
        int position;
        String str;
        int position2;
        String str2;
        int position3;
        int action = ev.getAction();
        float y = ev.getY();
        float x = ev.getX();
        switch (action) {
            case 0:
                this.mCurrentIndex = this.mIndexScroll.getIndexByPosition((int) x, (int) y);
                this.mStartTouchDown = System.currentTimeMillis();
                if (this.mCurrentIndex != null) {
                    if (this.mIndexScroll.isAlphabetInit() && (str = this.mCurrentIndex) != null && str.length() != 0) {
                        this.mIndexScroll.setEffectText(this.mCurrentIndex);
                        this.mIndexScroll.drawEffect(y);
                        this.mIndexScrollPreview.setLayout(0, 0, getWidth(), getHeight());
                        this.mIndexScrollPreview.invalidate();
                        this.mTouchY = y;
                    }
                    if (!this.mIsSimpleIndexScroll) {
                        position = getListViewPosition(this.mCurrentIndex);
                    } else {
                        position = this.mIndexScroll.getSelectedIndex();
                    }
                    if (position != -1) {
                        notifyIndexChange(position);
                        break;
                    }
                } else {
                    return false;
                }
                break;
            case 1:
            case 3:
                this.mCurrentIndex = null;
                this.mIndexScroll.resetSelectedIndex();
                this.mIndexScrollPreview.close();
                this.mTouchY = OUT_OF_BOUNDARY;
                OnIndexBarEventListener onIndexBarEventListener = this.mOnIndexBarEventListener;
                if (onIndexBarEventListener != null) {
                    onIndexBarEventListener.onReleased(y);
                    break;
                }
                break;
            case 2:
                String calculatedIndexStr = this.mIndexScroll.getIndexByPosition((int) x, (int) y);
                String str3 = this.mCurrentIndex;
                if (str3 != null && calculatedIndexStr == null && !this.mIsSimpleIndexScroll) {
                    String calculatedIndexStr2 = this.mIndexScroll.getIndexByPosition((int) x, (int) y);
                    this.mCurrentIndex = this.mIndexScroll.getIndexByPosition((int) x, (int) y);
                    int position4 = getListViewPosition(calculatedIndexStr2);
                    if (position4 != -1) {
                        notifyIndexChange(position4);
                        break;
                    }
                } else if (str3 != null && calculatedIndexStr != null && calculatedIndexStr.length() < this.mCurrentIndex.length()) {
                    String indexByPosition = this.mIndexScroll.getIndexByPosition((int) x, (int) y);
                    this.mCurrentIndex = indexByPosition;
                    if (!this.mIsSimpleIndexScroll) {
                        position3 = getListViewPosition(indexByPosition);
                    } else {
                        position3 = this.mIndexScroll.getSelectedIndex();
                    }
                    if (position3 != -1) {
                        notifyIndexChange(position3);
                        break;
                    }
                } else {
                    this.mCurrentIndex = this.mIndexScroll.getIndexByPosition((int) x, (int) y);
                    if (this.mIndexScroll.isAlphabetInit() && (str2 = this.mCurrentIndex) != null && str2.length() != 0) {
                        this.mIndexScroll.setEffectText(this.mCurrentIndex);
                        this.mIndexScroll.drawEffect(y);
                        this.mTouchY = y;
                    }
                    if (!this.mIsSimpleIndexScroll) {
                        position2 = getListViewPosition(this.mCurrentIndex);
                    } else {
                        position2 = this.mIndexScroll.getSelectedIndex();
                    }
                    if (position2 != -1) {
                        notifyIndexChange(position2);
                        break;
                    }
                }
                break;
            default:
                return false;
        }
        invalidate();
        return true;
    }

    private int getFirstAlphabetCharacterIndex() {
        int currentLang = this.mIndexer.getCurrentLang();
        int indexerAlphabetSize = this.mIndexer.getAlphabetArray().length;
        int index = 0;
        while (index < indexerAlphabetSize && currentLang != this.mIndexer.getLangbyIndex(index)) {
            index++;
        }
        if (index < indexerAlphabetSize) {
            return index;
        }
        return -1;
    }

    private int getLastAlphabetCharacterIndex() {
        SemAbstractIndexer semAbstractIndexer = this.mIndexer;
        if (semAbstractIndexer == null) {
            return -1;
        }
        int currentLang = semAbstractIndexer.getCurrentLang();
        int indexerAlphabetSize = this.mIndexer.getAlphabetArray().length;
        int index = indexerAlphabetSize - 1;
        while (index >= 0) {
            SemAbstractIndexer semAbstractIndexer2 = this.mIndexer;
            if (semAbstractIndexer2 == null || currentLang == semAbstractIndexer2.getLangbyIndex(index)) {
                break;
            }
            index--;
        }
        if (index > 0) {
            return (indexerAlphabetSize - index) - 1;
        }
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.View
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    private void notifyIndexChange(int pos) {
        OnIndexBarEventListener onIndexBarEventListener = this.mOnIndexBarEventListener;
        if (onIndexBarEventListener != null) {
            onIndexBarEventListener.onIndexChanged(pos);
        }
    }

    @Deprecated
    public void setOnIndexBarEventListener(OnIndexBarEventListener iOnIndexBarEventListener) {
        this.mOnIndexBarEventListener = iOnIndexBarEventListener;
    }

    /* loaded from: classes6.dex */
    class IndexerObserver extends DataSetObserver {
        private final long INDEX_UPDATE_DELAY = 200;
        boolean mDataInvalid = false;
        Runnable mUpdateIndex = new Runnable() { // from class: com.samsung.android.widget.SemIndexScrollView.IndexerObserver.1
            @Override // java.lang.Runnable
            public void run() {
                IndexerObserver.this.mDataInvalid = false;
            }
        };

        IndexerObserver() {
        }

        @Override // android.database.DataSetObserver
        public void onChanged() {
            super.onChanged();
            notifyDataSetChange();
        }

        @Override // android.database.DataSetObserver
        public void onInvalidated() {
            super.onInvalidated();
            notifyDataSetChange();
        }

        public boolean hasIndexerDataValid() {
            return !this.mDataInvalid;
        }

        private void notifyDataSetChange() {
            this.mDataInvalid = true;
            SemIndexScrollView.this.removeCallbacks(this.mUpdateIndex);
            SemIndexScrollView.this.postDelayed(this.mUpdateIndex, 200L);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes6.dex */
    public class IndexScroll {
        public static final int FIRST_LETTER_NOT_RELEVANT_NOT_MULTI_LANGUAGE = -1;
        public static final int GRAVITY_INDEX_BAR_LEFT = 0;
        public static final int GRAVITY_INDEX_BAR_RIGHT = 1;
        public static final int LAST_LETTER_NOT_RELEVANT_NOT_MULTI_LANGUAGE = -1;
        public static final int NO_SELECTED_INDEX = -1;
        private static final String TAG = "IndexScroll";
        private final boolean debug;
        private int mAdditionalSpace;
        private String[] mAlphabetArray;
        private int mAlphabetArrayFirstLetterIndex;
        private int mAlphabetArrayLastLetterIndex;
        private String[] mAlphabetArrayToDraw;
        private int mAlphabetSize;
        private int mAlphabetToDrawSize;
        private Drawable mBgDrawableDefault;
        private Rect mBgRect;
        private boolean mBgRectParamsSet;
        private int mBgRectWidth;
        private int mBgTintColor;
        private String mBigText;
        private float mContentMinHeight;
        private int mContentPadding;
        private Context mContext;
        private int mDotHeight;
        LangAttributeValues mFirstLang;
        private int mHeight;
        private float mIndexScrollPreviewRadius;
        private boolean mIsAlphabetInit;
        private boolean mIsSetDimensions;
        private float mItemHeight;
        private int mItemWidth;
        private int mItemWidthGap;
        private Paint mPaint;
        private int mPosition;
        private float mPreviewLimitY;
        private int mScreenHeight;
        private int mScrollBottom;
        private int mScrollBottomMargin;
        private int mScrollThumbAdditionalHeight;
        private Drawable mScrollThumbBgDrawable;
        private Rect mScrollThumbBgRect;
        private int mScrollThumbBgRectHeight;
        private int mScrollThumbBgRectPadding;
        private int mScrollTop;
        private int mScrollTopMargin;
        LangAttributeValues mSecondLang;
        private int mSelectedIndex;
        private float mSeparatorHeight;
        private String mSmallText;
        private Rect mTextBounds;
        private int mTextColorDimmed;
        private int mTextSize;
        private int mThumbColor;
        private int mWidth;
        private int mWidthShift;

        /* JADX INFO: Access modifiers changed from: package-private */
        /* loaded from: classes6.dex */
        public class LangAttributeValues {
            String[] alphabetArray;
            int dotCount;
            float height;
            int indexCount;
            float separatorHeight;
            int totalCount;

            public LangAttributeValues(int indexCount, int dotCount, int totalCount, float height, float separatorHeight) {
                this.indexCount = indexCount;
                this.dotCount = dotCount;
                this.totalCount = totalCount;
                this.height = height;
                this.separatorHeight = separatorHeight;
            }
        }

        public IndexScroll(Context context, int height, int width) {
            this.debug = false;
            this.mAlphabetArray = null;
            this.mAlphabetArrayFirstLetterIndex = -1;
            this.mAlphabetArrayLastLetterIndex = -1;
            this.mSelectedIndex = -1;
            this.mPosition = 0;
            this.mBgDrawableDefault = null;
            this.mScrollThumbBgDrawable = null;
            this.mThumbColor = 0;
            this.mIsAlphabetInit = false;
            this.mHeight = height;
            this.mWidth = width;
            this.mWidthShift = 0;
            this.mScrollTop = 0;
            this.mTextBounds = new Rect();
            this.mBgRectParamsSet = false;
            this.mContext = context;
            init();
        }

        public IndexScroll(Context context, int height, int width, int position) {
            this.debug = false;
            this.mAlphabetArray = null;
            this.mAlphabetArrayFirstLetterIndex = -1;
            this.mAlphabetArrayLastLetterIndex = -1;
            this.mSelectedIndex = -1;
            this.mPosition = 0;
            this.mBgDrawableDefault = null;
            this.mScrollThumbBgDrawable = null;
            this.mThumbColor = 0;
            this.mIsAlphabetInit = false;
            this.mHeight = height;
            this.mWidth = width;
            this.mPosition = position;
            this.mWidthShift = 0;
            this.mScrollTop = 0;
            this.mTextBounds = new Rect();
            this.mBgRectParamsSet = false;
            this.mContext = context;
            init();
        }

        public boolean isAlphabetInit() {
            return this.mIsAlphabetInit;
        }

        public int getPosition() {
            return this.mPosition;
        }

        public int getSelectedIndex() {
            return this.mSelectedIndex;
        }

        public int getHeight() {
            return this.mHeight;
        }

        public void setSimpleIndexScrollWidth(int itemWidth) {
            if (itemWidth <= 0) {
                return;
            }
            this.mItemWidth = itemWidth;
            this.mBgRectWidth = itemWidth;
            allocateBgRectangle();
        }

        public void setIndexScrollBgMargin(int topMargin, int bottomMargin) {
            this.mScrollTopMargin = topMargin;
            this.mScrollBottomMargin = bottomMargin;
        }

        public void setPosition(int position) {
            this.mPosition = position;
            setBgRectParams();
        }

        public void setDimensions(int width, int height) {
            if (this.mIsAlphabetInit) {
                if (this.mWidth == width && this.mHeight == height && !this.mIsSetDimensions) {
                    return;
                }
                this.mIsSetDimensions = false;
                this.mWidth = width;
                int i = height - (((this.mScrollTop + this.mScrollBottom) + this.mScrollTopMargin) + this.mScrollBottomMargin);
                this.mHeight = i;
                this.mScreenHeight = height;
                float f = i / this.mAlphabetSize;
                this.mItemHeight = f;
                this.mSeparatorHeight = Math.max(f, this.mContentMinHeight);
                setBgRectParams();
                LangAttributeValues langAttributeValues = this.mFirstLang;
                if (langAttributeValues != null && this.mSecondLang != null) {
                    langAttributeValues.separatorHeight = this.mContentMinHeight;
                    this.mSecondLang.separatorHeight = this.mContentMinHeight;
                    manageIndexScrollHeight();
                }
            }
        }

        private void init() {
            Resources rsrc = this.mContext.getResources();
            Paint paint = new Paint();
            this.mPaint = paint;
            paint.setAntiAlias(true);
            if (SemIndexScrollView.this.mSECRobotoLightRegularFont == null) {
                SemIndexScrollView.this.mSECRobotoLightRegularFont = Typeface.create("sec-roboto-light", 0);
            }
            this.mPaint.setTypeface(SemIndexScrollView.this.mSECRobotoLightRegularFont);
            this.mScrollTopMargin = 0;
            this.mScrollBottomMargin = 0;
            this.mItemWidth = 1;
            this.mItemWidthGap = 1;
            this.mBgRectWidth = (int) rsrc.getDimension(R.dimen.sem_indexbar_width);
            this.mTextSize = (int) rsrc.getDimension(R.dimen.sem_indexbar_textsize);
            this.mScrollTop = (int) rsrc.getDimension(R.dimen.sem_indexbar_top_margin);
            this.mScrollBottom = (int) rsrc.getDimension(R.dimen.sem_indexbar_bottom_margin);
            this.mWidthShift = (int) rsrc.getDimension(R.dimen.sem_indexbar_side_margin);
            this.mContentPadding = (int) rsrc.getDimension(R.dimen.sem_indexbar_content_padding);
            this.mContentMinHeight = rsrc.getDimension(R.dimen.sem_indexbar_content_min_height);
            this.mAdditionalSpace = (int) rsrc.getDimension(R.dimen.sem_indexbar_additional_touch_boundary);
            this.mIndexScrollPreviewRadius = rsrc.getDimension(R.dimen.sem_index_scroll_preview_radius);
            this.mPreviewLimitY = rsrc.getDimension(R.dimen.sem_index_scroll_preview_ypos_limit);
            TypedValue outValue = new TypedValue();
            this.mContext.getTheme().resolveAttribute(16843827, outValue, true);
            int colorPrimary = outValue.resourceId != 0 ? rsrc.getColor(outValue.resourceId, null) : outValue.data;
            this.mFirstLang = new LangAttributeValues(0, 0, 0, 0.0f, 0.0f);
            this.mSecondLang = new LangAttributeValues(0, 0, 0, 0.0f, 0.0f);
            this.mScrollThumbBgRectPadding = (int) rsrc.getDimension(R.dimen.sem_indexbar_thumb_padding);
            this.mScrollThumbAdditionalHeight = (int) rsrc.getDimension(R.dimen.sem_indexbar_thumb_additional_height);
            this.mDotHeight = (int) rsrc.getDimension(R.dimen.sem_indexbar_dot_separator_height);
            SemIndexScrollView.this.mIndexScrollPreview.setBackgroundColor(getColorWithAlpha(colorPrimary, 0.8f));
            Drawable drawable = rsrc.getDrawable(R.drawable.sem_indexbar_thumb_mtrl_shape);
            this.mScrollThumbBgDrawable = drawable;
            drawable.setColorFilter(colorPrimary, PorterDuff.Mode.MULTIPLY);
            this.mThumbColor = colorPrimary;
            this.mContext.getTheme().resolveAttribute(16844176, outValue, true);
            if (outValue.data != 0) {
                this.mTextColorDimmed = rsrc.getColor(R.color.sem_indexbar_text_color, null);
                this.mBgTintColor = rsrc.getColor(R.color.sem_indexbar_bg_tint_color, null);
            } else {
                this.mTextColorDimmed = rsrc.getColor(R.color.sem_indexbar_text_color_dark, null);
                this.mBgTintColor = rsrc.getColor(R.color.sem_indexbar_bg_tint_color_dark, null);
            }
            Drawable drawable2 = rsrc.getDrawable(R.drawable.sem_indexbar_bg_mtrl);
            this.mBgDrawableDefault = drawable2;
            drawable2.setColorFilter(this.mBgTintColor, PorterDuff.Mode.MULTIPLY);
            setBgRectParams();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getColorWithAlpha(int color, float ratio) {
            int alpha = Math.round(Color.alpha(color) * ratio);
            int r = Color.red(color);
            int g = Color.green(color);
            int b = Color.blue(color);
            int newColor = Color.argb(alpha, r, g, b);
            return newColor;
        }

        public void setAlphabetArray(String[] alphabetArray, int alphabetArrayFirstLetterIndex, int alphabetArrayLastLetterIndex) {
            if (alphabetArray == null) {
                return;
            }
            this.mAlphabetArray = alphabetArray;
            int length = alphabetArray.length;
            this.mAlphabetSize = length;
            this.mAlphabetArrayFirstLetterIndex = alphabetArrayFirstLetterIndex;
            this.mAlphabetArrayLastLetterIndex = alphabetArrayLastLetterIndex;
            float f = this.mHeight / length;
            this.mItemHeight = f;
            this.mSeparatorHeight = Math.max(f, this.mContentMinHeight);
            this.mIsAlphabetInit = true;
            this.mIsSetDimensions = true;
        }

        private void adjustSeparatorHeight() {
            if (SemIndexScrollView.this.mNumberOfLanguages == 1) {
                this.mFirstLang.separatorHeight = (this.mHeight - (this.mDotHeight * r0.dotCount)) / this.mFirstLang.indexCount;
                this.mFirstLang.height = this.mHeight;
                return;
            }
            float f = this.mFirstLang.height;
            int i = this.mHeight;
            if (f > i * 0.6f) {
                this.mFirstLang.separatorHeight = ((i * 0.6f) - (this.mDotHeight * r0.dotCount)) / this.mFirstLang.indexCount;
                this.mSecondLang.separatorHeight = ((this.mHeight * 0.4f) - (this.mDotHeight * r0.dotCount)) / this.mSecondLang.indexCount;
                this.mFirstLang.height = this.mHeight * 0.6f;
                this.mSecondLang.height = this.mHeight * 0.4f;
            } else {
                float f2 = this.mFirstLang.height;
                int i2 = this.mHeight;
                if (f2 <= i2 * 0.5f) {
                    this.mFirstLang.separatorHeight = ((i2 * 0.5f) - (this.mDotHeight * r0.dotCount)) / this.mFirstLang.indexCount;
                    this.mSecondLang.separatorHeight = ((this.mHeight * 0.5f) - (this.mDotHeight * r0.dotCount)) / this.mSecondLang.indexCount;
                    LangAttributeValues langAttributeValues = this.mFirstLang;
                    float f3 = this.mHeight * 0.5f;
                    this.mSecondLang.height = f3;
                    langAttributeValues.height = f3;
                } else {
                    LangAttributeValues langAttributeValues2 = this.mFirstLang;
                    langAttributeValues2.separatorHeight = (langAttributeValues2.height - (this.mDotHeight * this.mFirstLang.dotCount)) / this.mFirstLang.indexCount;
                    LangAttributeValues langAttributeValues3 = this.mSecondLang;
                    langAttributeValues3.separatorHeight = (langAttributeValues3.height - (this.mDotHeight * this.mSecondLang.dotCount)) / this.mSecondLang.indexCount;
                }
            }
            if (this.mSecondLang.totalCount == 0) {
                this.mFirstLang.separatorHeight = (this.mHeight - (this.mDotHeight * r0.dotCount)) / this.mFirstLang.indexCount;
                this.mFirstLang.height = this.mHeight;
                this.mSecondLang.separatorHeight = 0.0f;
                this.mSecondLang.height = 0.0f;
                return;
            }
            if (this.mFirstLang.totalCount == 0) {
                this.mSecondLang.separatorHeight = (this.mHeight - (this.mDotHeight * r0.dotCount)) / this.mSecondLang.indexCount;
                this.mSecondLang.height = this.mHeight;
                this.mFirstLang.separatorHeight = 0.0f;
                this.mFirstLang.height = 0.0f;
            }
        }

        private void manageIndexScrollHeight() {
            if (!this.mIsAlphabetInit || SemIndexScrollView.this.mNumberOfLanguages > 2) {
                return;
            }
            if (this.mAlphabetArrayFirstLetterIndex == -1) {
                this.mAlphabetArrayFirstLetterIndex = 0;
            }
            if (this.mAlphabetArrayLastLetterIndex == -1) {
                this.mAlphabetArrayLastLetterIndex = 0;
            }
            this.mFirstLang.indexCount = this.mAlphabetSize - this.mAlphabetArrayLastLetterIndex;
            LangAttributeValues langAttributeValues = this.mFirstLang;
            langAttributeValues.totalCount = langAttributeValues.indexCount;
            LangAttributeValues langAttributeValues2 = this.mFirstLang;
            langAttributeValues2.alphabetArray = new String[langAttributeValues2.totalCount];
            this.mFirstLang.dotCount = 0;
            this.mSecondLang.indexCount = this.mAlphabetSize - this.mFirstLang.indexCount;
            LangAttributeValues langAttributeValues3 = this.mSecondLang;
            langAttributeValues3.totalCount = langAttributeValues3.indexCount;
            LangAttributeValues langAttributeValues4 = this.mSecondLang;
            langAttributeValues4.alphabetArray = new String[langAttributeValues4.totalCount];
            this.mSecondLang.dotCount = 0;
            this.mFirstLang.height = r0.indexCount * this.mContentMinHeight;
            this.mSecondLang.height = this.mHeight - this.mFirstLang.height;
            this.mAlphabetArrayToDraw = this.mAlphabetArray;
            this.mAlphabetToDrawSize = this.mAlphabetSize;
            adjustSeparatorHeight();
            int digitIndexCount = 0;
            if (this.mAlphabetArrayFirstLetterIndex > 0 && SemIndexScrollView.this.mIndexer.isUseDigitIndex()) {
                digitIndexCount = 1;
            }
            if (SemIndexScrollView.this.mNumberOfLanguages == 1) {
                calcDotPosition(this.mFirstLang, this.mAlphabetArrayFirstLetterIndex, 0, digitIndexCount);
            } else {
                calcDotPosition(this.mFirstLang, this.mAlphabetArrayFirstLetterIndex, 0, 0);
                calcDotPosition(this.mSecondLang, 0, this.mAlphabetSize - this.mAlphabetArrayLastLetterIndex, digitIndexCount);
            }
        }

        private void calcDotPosition(LangAttributeValues language, int specialIndexCount, int startIndexPosition, int digitIndexCount) {
            int earlyLangCount;
            int endIndexPosition;
            int i;
            int i2 = language.indexCount - specialIndexCount;
            int endIndexPosition2 = language.totalCount + startIndexPosition;
            System.arraycopy(this.mAlphabetArray, startIndexPosition, language.alphabetArray, 0, endIndexPosition2 - startIndexPosition);
            int numberOfMissingElements = 0;
            boolean isFullCountState = false;
            int numberOfMissingElements2 = specialIndexCount;
            int digitIndexCount2 = digitIndexCount;
            while (language.separatorHeight < this.mContentMinHeight && this.mAlphabetArrayToDraw.length > 0) {
                int langCount = i2 - digitIndexCount2;
                int fullDotCount = (langCount / 2) - 1;
                if (language.dotCount >= fullDotCount || isFullCountState) {
                    earlyLangCount = i2;
                    endIndexPosition = endIndexPosition2;
                    boolean isDotPosition = false;
                    int dotCount = 0;
                    switch ((language.totalCount - numberOfMissingElements2) - digitIndexCount2) {
                        case 0:
                            if (digitIndexCount2 <= 0) {
                                if (numberOfMissingElements2 > 0) {
                                    numberOfMissingElements2--;
                                    break;
                                } else {
                                    break;
                                }
                            } else {
                                digitIndexCount2--;
                                break;
                            }
                        case 1:
                            if (numberOfMissingElements2 != 0 && language.dotCount == 0) {
                                language.indexCount--;
                                language.dotCount++;
                                isDotPosition = true;
                            } else {
                                if (numberOfMissingElements2 != 0) {
                                    i = 1;
                                    if (language.dotCount == 1) {
                                        language.dotCount--;
                                        language.totalCount--;
                                    }
                                } else {
                                    i = 1;
                                }
                                language.indexCount -= i;
                                language.totalCount -= i;
                            }
                            numberOfMissingElements++;
                            break;
                        case 2:
                            language.dotCount--;
                            language.totalCount--;
                            break;
                        case 3:
                            language.indexCount--;
                            language.totalCount--;
                            numberOfMissingElements++;
                            break;
                        default:
                            if (((language.indexCount - language.dotCount) - numberOfMissingElements2) - digitIndexCount2 != 1) {
                                language.indexCount--;
                                language.totalCount--;
                                numberOfMissingElements++;
                                break;
                            } else {
                                language.dotCount--;
                                language.totalCount--;
                                break;
                            }
                    }
                    if (language.totalCount > 0 && language.dotCount >= 0) {
                        if (language.indexCount >= 0) {
                            String[] alphabetArrWithDots = new String[language.totalCount];
                            int averageElementsEachDot = 0;
                            int extraMissingElements = 0;
                            if (language.dotCount > 0) {
                                averageElementsEachDot = numberOfMissingElements / language.dotCount;
                                extraMissingElements = numberOfMissingElements % language.dotCount;
                            }
                            System.arraycopy(this.mAlphabetArray, 0, alphabetArrWithDots, 0, numberOfMissingElements2);
                            int targetIndex = numberOfMissingElements2;
                            int indexCount = language.totalCount - digitIndexCount2;
                            int specialIndexCount2 = numberOfMissingElements2;
                            int specialIndexCount3 = specialIndexCount2;
                            while (specialIndexCount3 < indexCount) {
                                int numberOfMissingElements3 = numberOfMissingElements;
                                String[] strArr = this.mAlphabetArray;
                                int langCount2 = langCount;
                                if (targetIndex < strArr.length - digitIndexCount2) {
                                    if (!isDotPosition) {
                                        alphabetArrWithDots[specialIndexCount3] = strArr[targetIndex + startIndexPosition];
                                        targetIndex++;
                                        if (dotCount < language.dotCount) {
                                            isDotPosition = true;
                                        }
                                    } else {
                                        alphabetArrWithDots[specialIndexCount3] = MediaMetrics.SEPARATOR;
                                        dotCount++;
                                        targetIndex += averageElementsEachDot;
                                        if (extraMissingElements > 0) {
                                            extraMissingElements--;
                                            targetIndex++;
                                        }
                                        isDotPosition = false;
                                    }
                                }
                                specialIndexCount3++;
                                numberOfMissingElements = numberOfMissingElements3;
                                langCount = langCount2;
                            }
                            int numberOfMissingElements4 = numberOfMissingElements;
                            if (digitIndexCount2 > 0) {
                                String[] strArr2 = this.mAlphabetArray;
                                alphabetArrWithDots[indexCount] = strArr2[strArr2.length - 1];
                            }
                            language.alphabetArray = alphabetArrWithDots;
                            isFullCountState = true;
                            numberOfMissingElements2 = specialIndexCount2;
                            numberOfMissingElements = numberOfMissingElements4;
                        }
                    }
                    adjustSeparatorHeight();
                    return;
                }
                String[] alphabetArrWithDots2 = new String[language.totalCount];
                language.dotCount++;
                language.indexCount--;
                numberOfMissingElements++;
                int gapOfDot = (langCount / (language.dotCount + 1)) + 1;
                if (language.dotCount == fullDotCount) {
                    gapOfDot = 2;
                }
                int remainDotCount = language.dotCount;
                int indexShift = 0;
                while (remainDotCount != 0) {
                    if (remainDotCount != language.dotCount) {
                        remainDotCount = language.dotCount;
                    }
                    int earlyLangCount2 = i2;
                    int earlyLangCount3 = endIndexPosition2 - startIndexPosition;
                    int endIndexPosition3 = endIndexPosition2;
                    System.arraycopy(this.mAlphabetArray, startIndexPosition, alphabetArrWithDots2, 0, earlyLangCount3);
                    for (int i3 = 1; i3 < language.dotCount + 1; i3++) {
                        int targetIndex2 = (gapOfDot * i3) - (indexShift * i3);
                        if (numberOfMissingElements2 > 1) {
                            targetIndex2 += numberOfMissingElements2 - 1;
                        }
                        if (targetIndex2 > 0 && targetIndex2 < langCount) {
                            alphabetArrWithDots2[targetIndex2] = MediaMetrics.SEPARATOR;
                            remainDotCount--;
                        } else if (targetIndex2 >= langCount && remainDotCount > 0) {
                            if (targetIndex2 - (gapOfDot / 2) < langCount) {
                                alphabetArrWithDots2[targetIndex2 - (gapOfDot / 2)] = MediaMetrics.SEPARATOR;
                                remainDotCount--;
                            } else {
                                indexShift = 1;
                            }
                        }
                    }
                    i2 = earlyLangCount2;
                    endIndexPosition2 = endIndexPosition3;
                }
                earlyLangCount = i2;
                endIndexPosition = endIndexPosition2;
                language.alphabetArray = alphabetArrWithDots2;
                adjustSeparatorHeight();
                i2 = earlyLangCount;
                endIndexPosition2 = endIndexPosition;
            }
        }

        public String getIndexByPosition(int x, int y) {
            int i;
            int i2;
            Rect rect = this.mBgRect;
            if (rect == null || !this.mIsAlphabetInit) {
                return "";
            }
            if ((this.mPosition == 0 && x < rect.left - this.mAdditionalSpace) || (this.mPosition == 1 && x > this.mBgRect.right + this.mAdditionalSpace)) {
                return "";
            }
            if (x >= this.mBgRect.left - this.mAdditionalSpace && x <= this.mBgRect.right + this.mAdditionalSpace) {
                if (isInSelectedIndexRect(y)) {
                    String[] strArr = this.mAlphabetArrayToDraw;
                    return (strArr == null || (i2 = this.mSelectedIndex) < 0 || i2 >= strArr.length) ? "" : getIndexByY(y);
                }
                return getIndexByY(y);
            }
            int i3 = this.mPosition;
            if (i3 == 0 && x >= this.mWidthShift + this.mItemWidth + this.mItemWidthGap) {
                return null;
            }
            if (i3 == 1 && x <= (this.mWidth - this.mWidthShift) - (this.mItemWidth + this.mItemWidthGap)) {
                return null;
            }
            if (isInSelectedIndexRect(y)) {
                String[] strArr2 = this.mAlphabetArrayToDraw;
                return (strArr2 == null || (i = this.mSelectedIndex) < 0 || i >= strArr2.length) ? "" : strArr2[i];
            }
            return getIndexByY(y);
        }

        private int getIndex(int y) {
            int index;
            float firstLangCount = this.mAlphabetSize - this.mAlphabetArrayLastLetterIndex;
            if (y < this.mScrollTop + this.mScrollTopMargin + this.mFirstLang.height) {
                float indexTouchBoundary = this.mFirstLang.height / firstLangCount;
                index = (int) (((y - this.mScrollTop) - this.mScrollTopMargin) / indexTouchBoundary);
            } else {
                float indexTouchBoundary2 = this.mSecondLang.height / this.mAlphabetArrayLastLetterIndex;
                int index2 = (int) ((((y - this.mScrollTop) - this.mScrollTopMargin) - this.mFirstLang.height) / indexTouchBoundary2);
                index = (int) (index2 + firstLangCount);
            }
            if (index < 0) {
                return 0;
            }
            int i = this.mAlphabetToDrawSize;
            if (index >= i) {
                int index3 = i - 1;
                return index3;
            }
            return index;
        }

        private String getIndexByY(int y) {
            int i;
            if (y <= this.mBgRect.top - this.mAdditionalSpace || y >= this.mBgRect.bottom + this.mAdditionalSpace) {
                return "";
            }
            if (y < this.mBgRect.top) {
                this.mSelectedIndex = 0;
            } else if (y > this.mBgRect.bottom) {
                this.mSelectedIndex = this.mAlphabetToDrawSize - 1;
            } else {
                int index = getIndex(y);
                this.mSelectedIndex = index;
                if (index == this.mAlphabetToDrawSize) {
                    this.mSelectedIndex = index - 1;
                }
            }
            int i2 = this.mSelectedIndex;
            int i3 = this.mAlphabetToDrawSize;
            if (i2 == i3 || i2 == i3 + 1) {
                this.mSelectedIndex = i3 - 1;
            }
            String[] strArr = this.mAlphabetArrayToDraw;
            return (strArr == null || (i = this.mSelectedIndex) <= -1 || i > i3) ? "" : strArr[i];
        }

        private boolean isInSelectedIndexRect(int y) {
            int i = this.mSelectedIndex;
            if (i == -1 || i >= this.mAlphabetToDrawSize) {
                return false;
            }
            int i2 = this.mScrollTop;
            int i3 = this.mScrollTopMargin;
            float f = this.mSeparatorHeight;
            return y >= ((int) (((float) (i2 + i3)) + (((float) i) * f))) && y <= ((int) (((float) (i2 + i3)) + (f * ((float) (i + 1)))));
        }

        public void resetSelectedIndex() {
            this.mSelectedIndex = -1;
        }

        public void draw(Canvas canvas) {
            if (!this.mIsAlphabetInit) {
                return;
            }
            drawScroll(canvas);
        }

        public void drawScroll(Canvas canvas) {
            drawBgRectangle(canvas);
            drawAlphabetCharacters(canvas);
            int i = this.mSelectedIndex;
            if (i < 0 || i >= this.mAlphabetSize) {
                if (SemIndexScrollView.this.mIndexScrollPreview != null) {
                    SemIndexScrollView.this.mIndexScrollPreview.close();
                }
                if (SemIndexScrollView.this.mOnIndexBarEventListener != null) {
                    SemIndexScrollView.this.mOnIndexBarEventListener.onReleased(0.0f);
                }
            }
        }

        public void setEffectText(String effectText) {
            this.mBigText = effectText;
        }

        public void drawEffect(float effectPositionY) {
            int i = this.mSelectedIndex;
            if (i != -1) {
                String str = this.mAlphabetArrayToDraw[i];
                this.mSmallText = str;
                this.mPaint.getTextBounds(str, 0, str.length(), this.mTextBounds);
                int i2 = this.mScrollTopMargin;
                float f = this.mPreviewLimitY;
                float f2 = this.mIndexScrollPreviewRadius;
                float topDrawY = i2 + f + f2;
                int i3 = this.mScreenHeight;
                int i4 = this.mScrollBottomMargin;
                float bottomDrawY = ((i3 - i4) - f) - f2;
                if (i3 <= (f2 * 2.0f) + f + i2 + i4) {
                    topDrawY = this.mScrollTop + i2 + ((float) (this.mFirstLang.separatorHeight * 0.5d));
                    bottomDrawY = ((((this.mScrollTop + this.mScrollTopMargin) - this.mScrollBottomMargin) + this.mFirstLang.height) + this.mSecondLang.height) - ((float) (this.mFirstLang.separatorHeight * 0.5d));
                }
                float drawY = SemIndexScrollView.OUT_OF_BOUNDARY;
                if (effectPositionY > topDrawY && effectPositionY < bottomDrawY) {
                    drawY = effectPositionY;
                } else if (effectPositionY <= topDrawY) {
                    drawY = topDrawY;
                } else if (effectPositionY >= bottomDrawY) {
                    drawY = bottomDrawY;
                }
                if (drawY != SemIndexScrollView.OUT_OF_BOUNDARY) {
                    SemIndexScrollView.this.mIndexScrollPreview.open(drawY, this.mBigText);
                    if (SemIndexScrollView.this.mOnIndexBarEventListener != null) {
                        SemIndexScrollView.this.mOnIndexBarEventListener.onPressed(drawY);
                    }
                }
            }
        }

        private void allocateBgRectangle() {
            int right;
            int left;
            if (this.mPosition == 1) {
                right = this.mWidth - this.mWidthShift;
                int right2 = this.mBgRectWidth;
                left = right - right2;
            } else {
                int right3 = this.mWidthShift;
                right = right3 + this.mBgRectWidth;
                left = this.mWidthShift;
            }
            Rect rect = this.mBgRect;
            if (rect == null) {
                int i = this.mScrollTop;
                int i2 = this.mScrollTopMargin;
                int i3 = this.mContentPadding;
                this.mBgRect = new Rect(left, (i + i2) - i3, right, this.mHeight + i + i2 + i3);
            } else {
                int i4 = this.mScrollTop;
                int i5 = this.mScrollTopMargin;
                int i6 = this.mContentPadding;
                rect.set(left, (i4 + i5) - i6, right, this.mHeight + i4 + i5 + i6);
            }
            this.mScrollThumbBgRectHeight = ((int) (this.mContentMinHeight * 3.0f)) + this.mScrollThumbAdditionalHeight;
            int i7 = this.mScrollThumbBgRectPadding;
            int left2 = left + i7;
            int right4 = right - i7;
            int top = (int) (SemIndexScrollView.this.mTouchY - (this.mScrollThumbBgRectHeight / 2));
            int bottom = (int) (SemIndexScrollView.this.mTouchY + (this.mScrollThumbBgRectHeight / 2));
            if ((top < this.mBgRect.top + this.mScrollThumbBgRectPadding && bottom > this.mBgRect.bottom - this.mScrollThumbBgRectPadding) || this.mScrollThumbBgRectHeight >= (this.mBgRect.bottom - this.mBgRect.top) - (this.mScrollThumbBgRectPadding * 2)) {
                top = this.mBgRect.top + this.mScrollThumbBgRectPadding;
                bottom = this.mBgRect.bottom - this.mScrollThumbBgRectPadding;
            } else if (top < this.mBgRect.top + this.mScrollThumbBgRectPadding) {
                top = this.mBgRect.top + this.mScrollThumbBgRectPadding;
                bottom = top + this.mScrollThumbBgRectHeight;
            } else if (bottom > this.mBgRect.bottom - this.mScrollThumbBgRectPadding) {
                bottom = this.mBgRect.bottom - this.mScrollThumbBgRectPadding;
                top = bottom - this.mScrollThumbBgRectHeight;
            }
            Rect rect2 = this.mScrollThumbBgRect;
            if (rect2 == null) {
                this.mScrollThumbBgRect = new Rect(left2, top, right4, bottom);
            } else {
                rect2.set(left2, top, right4, bottom);
            }
        }

        private void drawBgRectangle(Canvas canvas) {
            if (!this.mBgRectParamsSet) {
                setBgRectParams();
                this.mBgRectParamsSet = true;
            }
            this.mBgDrawableDefault.draw(canvas);
            if (SemIndexScrollView.this.mTouchY != SemIndexScrollView.OUT_OF_BOUNDARY) {
                this.mScrollThumbBgDrawable.draw(canvas);
            }
        }

        private void setBgRectParams() {
            allocateBgRectangle();
            this.mBgDrawableDefault.setBounds(this.mBgRect);
            this.mScrollThumbBgDrawable.setBounds(this.mScrollThumbBgRect);
        }

        private void drawAlphabetCharacters(Canvas canvas) {
            String text;
            float separatorHeight;
            float textPosY;
            this.mPaint.setColor(this.mTextColorDimmed);
            this.mPaint.setTextSize(this.mTextSize);
            if (this.mAlphabetArrayToDraw != null && this.mFirstLang.totalCount != 0) {
                float startPosY = this.mScrollTop + this.mScrollTopMargin;
                int indexCount = this.mFirstLang.totalCount + this.mSecondLang.totalCount;
                for (int index = 0; index < indexCount; index++) {
                    if (index < this.mFirstLang.totalCount) {
                        text = this.mFirstLang.alphabetArray[index];
                        separatorHeight = this.mFirstLang.separatorHeight;
                    } else {
                        text = this.mSecondLang.alphabetArray[index - this.mFirstLang.totalCount];
                        separatorHeight = this.mSecondLang.separatorHeight;
                    }
                    this.mPaint.getTextBounds(text, 0, text.length(), this.mTextBounds);
                    float width = this.mPaint.measureText(text);
                    float textPosX = this.mBgRect.centerX() - (width * 0.5f);
                    if (MediaMetrics.SEPARATOR.equals(text)) {
                        textPosY = ((this.mDotHeight * 0.5f) - (this.mTextBounds.top * 0.5f)) + startPosY;
                        startPosY += this.mDotHeight;
                    } else {
                        float textPosY2 = separatorHeight * 0.5f;
                        textPosY = (textPosY2 - (this.mTextBounds.top * 0.5f)) + startPosY;
                        startPosY += separatorHeight;
                    }
                    canvas.drawText(text, textPosX, textPosY, this.mPaint);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes6.dex */
    public class IndexScrollPreview extends View {
        private static final int FASTSCROLL_VIBRATE_INDEX = 26;
        private boolean mIsOpen;
        private float mPreviewCenterMargin;
        private float mPreviewCenterX;
        private float mPreviewCenterY;
        private float mPreviewRadius;
        private String mPreviewText;
        private Paint mShapePaint;
        private Rect mTextBounds;
        private Paint mTextPaint;
        private int mTextSize;
        private int mTextWidhtLimit;

        public IndexScrollPreview(Context context) {
            super(context);
            this.mIsOpen = false;
            init(context);
        }

        private void init(Context context) {
            Resources rsrc = context.getResources();
            Paint paint = new Paint();
            this.mShapePaint = paint;
            paint.setStyle(Paint.Style.FILL);
            this.mShapePaint.setAntiAlias(true);
            this.mTextSize = (int) rsrc.getDimension(R.dimen.sem_index_scroll_preview_text_size);
            this.mTextWidhtLimit = (int) rsrc.getDimension(R.dimen.sem_index_scroll_preview_text_width_limit);
            Paint paint2 = new Paint();
            this.mTextPaint = paint2;
            paint2.setAntiAlias(true);
            this.mTextPaint.setTypeface(SemIndexScrollView.this.mSECRobotoLightRegularFont);
            this.mTextPaint.setTextAlign(Paint.Align.CENTER);
            this.mTextPaint.setTextSize(this.mTextSize);
            this.mTextPaint.setColor(rsrc.getColor(R.color.sem_index_scroll_preview_text_color, null));
            this.mTextBounds = new Rect();
            this.mPreviewRadius = rsrc.getDimension(R.dimen.sem_index_scroll_preview_radius);
            this.mPreviewCenterMargin = rsrc.getDimension(R.dimen.sem_index_scroll_preview_center_margin);
            this.mIsOpen = false;
        }

        public void setLayout(int l, int t, int r, int b) {
            layout(l, t, r, b);
            if (SemIndexScrollView.this.mIndexBarGravity == 0) {
                this.mPreviewCenterX = this.mPreviewCenterMargin;
            } else {
                this.mPreviewCenterX = r - this.mPreviewCenterMargin;
            }
        }

        @Override // android.view.View
        public void setBackgroundColor(int bgColor) {
            this.mShapePaint.setColor(bgColor);
        }

        public void setTextColor(int txtColor) {
            this.mTextPaint.setColor(txtColor);
        }

        public void open(float y, String text) {
            int textSize = this.mTextSize;
            this.mPreviewCenterY = y;
            if (!this.mIsOpen || this.mPreviewText != text) {
                performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(26));
            }
            this.mPreviewText = text;
            this.mTextPaint.setTextSize(textSize);
            while (this.mTextPaint.measureText(text) > this.mTextWidhtLimit) {
                textSize--;
                this.mTextPaint.setTextSize(textSize);
            }
            if (!this.mIsOpen) {
                startAnimation();
                this.mIsOpen = true;
            }
        }

        public void close() {
            long gap = System.currentTimeMillis() - SemIndexScrollView.this.mStartTouchDown;
            removeCallbacks(SemIndexScrollView.this.mPreviewDelayRunnable);
            if (gap <= 100) {
                postDelayed(SemIndexScrollView.this.mPreviewDelayRunnable, 100L);
            } else {
                fadeOutAnimation();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void fadeOutAnimation() {
            if (this.mIsOpen) {
                startAnimation();
                this.mIsOpen = false;
            }
        }

        public void startAnimation() {
            ObjectAnimator anim;
            if (!this.mIsOpen) {
                anim = ObjectAnimator.ofFloat(SemIndexScrollView.this.mIndexScrollPreview, "alpha", 0.0f, 1.0f);
            } else {
                anim = ObjectAnimator.ofFloat(SemIndexScrollView.this.mIndexScrollPreview, "alpha", 1.0f, 0.0f);
            }
            anim.setDuration(167L);
            AnimatorSet set = new AnimatorSet();
            set.play(anim);
            set.start();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.view.View
        public void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            if (this.mIsOpen) {
                canvas.drawCircle(this.mPreviewCenterX, this.mPreviewCenterY, this.mPreviewRadius, this.mShapePaint);
                this.mTextPaint.getTextBounds(this.mPreviewText, 0, r1.length() - 1, this.mTextBounds);
                float textY = this.mPreviewCenterY - ((this.mTextPaint.descent() + this.mTextPaint.ascent()) / 2.0f);
                canvas.drawText(this.mPreviewText, this.mPreviewCenterX, textY, this.mTextPaint);
            }
        }
    }

    @Override // android.widget.AbsListView.OnScrollListener
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
    }

    @Override // android.widget.AbsListView.OnScrollListener
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }
}
