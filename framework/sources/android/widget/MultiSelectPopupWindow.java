package android.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.pm.ComponentInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.provider.Settings;
import android.text.Layout;
import android.text.MultiSelection;
import android.text.Spannable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.PathInterpolator;
import com.android.internal.R;
import com.android.internal.view.FloatingActionMode;
import java.util.List;

/* loaded from: classes4.dex */
public class MultiSelectPopupWindow {
    private static final String TAG = "MultiSelectPopupWindow";
    private static final float[] TEMP_POSITION = new float[2];
    private static final int TW_MENU_ITEM_ORDER_CLOSE = 0;
    private static final int TW_MENU_ITEM_ORDER_COPY = 2;
    private static final int TW_MENU_ITEM_ORDER_SELECT_ALL = 1;
    private static final int TW_MENU_ITEM_ORDER_SHARE = 3;
    private static final int TW_MENU_ITEM_ORDER_TRANSLATE = 5;
    private static MultiSelectPopupWindow sInstance;
    private static ActionMode sTextActionMode;
    private static TextView sTextView;
    private PositionListener mPositionListener;
    private Drawable mSelectHandleLeft;
    private Drawable mSelectHandleRight;
    private SelectionController mSelectionController;
    private final Runnable mShowFloatingToolbar = new Runnable() { // from class: android.widget.MultiSelectPopupWindow.1
        AnonymousClass1() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (MultiSelectPopupWindow.sTextActionMode != null) {
                MultiSelectPopupWindow.sTextActionMode.hide(0L);
            }
        }
    };

    /* loaded from: classes4.dex */
    private interface CursorController extends ViewTreeObserver.OnTouchModeChangeListener {
        void hide();

        void onDetached();

        void show();
    }

    /* loaded from: classes4.dex */
    public interface TextViewPositionListener {
        void updatePosition(int i, int i2, boolean z, boolean z2);
    }

    public static MultiSelectPopupWindow getInstance() {
        if (sInstance == null) {
            sInstance = new MultiSelectPopupWindow();
        }
        return sInstance;
    }

    private MultiSelectPopupWindow() {
        sTextView = null;
        sTextActionMode = null;
    }

    public void showMultiSelectPopupWindow() {
        if (getSelectionController() != null) {
            getSelectionController().hide();
            getSelectionController().show();
        }
        ActionMode actionMode = sTextActionMode;
        if (actionMode != null) {
            actionMode.invalidate();
        } else {
            ActionMode.Callback actionModeCallback = new TextActionModeCallback(true);
            sTextActionMode = sTextView.startActionMode(actionModeCallback, 1);
        }
    }

    public void hideMultiSelectPopupWindow() {
        if (getSelectionController() != null) {
            getSelectionController().hide();
        }
        ActionMode actionMode = sTextActionMode;
        if (actionMode != null) {
            actionMode.finish();
        }
    }

    public void changeCurrentSelectedView(TextView textView) {
        if (sTextView == textView) {
            return;
        }
        sTextView = textView;
    }

    void onScrollChanged() {
        PositionListener positionListener = this.mPositionListener;
        if (positionListener != null) {
            positionListener.onScrollChanged();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: android.widget.MultiSelectPopupWindow$1 */
    /* loaded from: classes4.dex */
    public class AnonymousClass1 implements Runnable {
        AnonymousClass1() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (MultiSelectPopupWindow.sTextActionMode != null) {
                MultiSelectPopupWindow.sTextActionMode.hide(0L);
            }
        }
    }

    private void hideFloatingToolbar() {
        if (sTextActionMode != null) {
            sTextView.removeCallbacks(this.mShowFloatingToolbar);
            sTextActionMode.hide(-1L);
        }
    }

    private void showFloatingToolbar() {
        if (sTextActionMode != null) {
            int delay = ViewConfiguration.getDoubleTapTimeout();
            sTextView.postDelayed(this.mShowFloatingToolbar, delay);
        }
    }

    public void updateFloatingToolbarVisibility(MotionEvent event) {
        if (sTextActionMode != null) {
            switch (event.getActionMasked()) {
                case 1:
                case 3:
                    showFloatingToolbar();
                    return;
                case 2:
                    hideFloatingToolbar();
                    return;
                default:
                    return;
            }
        }
    }

    /* loaded from: classes4.dex */
    public class TextActionModeCallback extends ActionMode.Callback2 {
        private int mHandleHeight;
        private final Path mSelectionPath = new Path();
        private final RectF mSelectionBounds = new RectF();

        public TextActionModeCallback(boolean hasSelection) {
            if (hasSelection) {
                SelectionController selectionController = MultiSelectPopupWindow.this.getSelectionController();
                if (selectionController != null && selectionController.mStartHandle == null) {
                    selectionController.initDrawables();
                    selectionController.initHandles();
                    selectionController.hide();
                }
                this.mHandleHeight = Math.max(MultiSelectPopupWindow.this.mSelectHandleLeft.getMinimumHeight(), MultiSelectPopupWindow.this.mSelectHandleRight.getMinimumHeight());
            }
        }

        @Override // android.view.ActionMode.Callback
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.setTitle((CharSequence) null);
            mode.setSubtitle((CharSequence) null);
            mode.setTitleOptionalHint(true);
            populateMenuWithItems(menu);
            if (menu.size() > 1) {
                return true;
            }
            return false;
        }

        private void populateMenuWithItems(Menu menu) {
            updateSelectAllItem(menu);
            menu.add(0, R.id.floatingToolbarClose, 0, R.string.close).setIcon(MultiSelectPopupWindow.sTextView.getContext().getResources().getDrawable(R.drawable.tw_floating_popup_button_ic_close));
            menu.add(0, R.id.multiSelectCopy, 2, 17039361).setIcon(MultiSelectPopupWindow.sTextView.getContext().getResources().getDrawable(R.drawable.tw_floating_popup_button_ic_copy)).setShowAsAction(2);
            if (MultiSelectPopupWindow.this.isShareViaEnable()) {
                menu.add(0, R.id.multiSelectShare, 3, R.string.share).setIcon(MultiSelectPopupWindow.sTextView.getContext().getResources().getDrawable(R.drawable.tw_floating_popup_button_ic_share)).setShowAsAction(1);
            }
            PackageManager pm = MultiSelectPopupWindow.sTextView.getContext().getPackageManager();
            List<ResolveInfo> activities = pm.queryIntentActivities(new Intent().setAction(Intent.ACTION_PROCESS_TEXT).setType("text/plain"), 0);
            int i = 0;
            if (!MultiSelectPopupWindow.this.isEmergencyMode()) {
                for (ResolveInfo resolveInfo : activities) {
                    ComponentInfo info = resolveInfo.getComponentInfo();
                    if (info.packageName.contains("com.sec.android.app.translator") || info.packageName.contains("com.google.android.apps.translate")) {
                        menu.add(0, R.id.multiSelectTranslate, i + 5, resolveInfo.loadLabel(pm)).setIcon(MultiSelectPopupWindow.sTextView.getContext().getResources().getDrawable(R.drawable.tw_floating_popup_button_ic_translate)).setIntent(new Intent().setAction(Intent.ACTION_PROCESS_TEXT).setType("text/plain").putExtra(Intent.EXTRA_PROCESS_TEXT_READONLY, true).setClassName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name)).setShowAsAction(1);
                        i++;
                    }
                }
            }
        }

        @Override // android.view.ActionMode.Callback
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            updateSelectAllItem(menu);
            return true;
        }

        private void updateSelectAllItem(Menu menu) {
            boolean selectAllItemExists = menu.findItem(R.id.multiSelectAll) != null;
            boolean selectAllEnable = MultiSelectPopupWindow.this.isSelectAllEnable();
            if (selectAllEnable && !selectAllItemExists) {
                menu.add(0, R.id.multiSelectAll, 1, 17039373).setIcon(MultiSelectPopupWindow.sTextView.getContext().getResources().getDrawable(R.drawable.tw_floating_popup_button_ic_selectall)).setShowAsAction(1);
            } else if (!selectAllEnable && selectAllItemExists) {
                menu.removeItem(R.id.multiSelectAll);
            }
        }

        @Override // android.view.ActionMode.Callback
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            if (item.getItemId() == 16909348 && (mode instanceof FloatingActionMode)) {
                Point touch = ((FloatingActionMode) mode).getContentRectOnScreen();
                MultiSelectPopupWindow.sTextView.startChooserPopupActivity(touch, true);
                return true;
            }
            return MultiSelectPopupWindow.sTextView.onMultiSelectMenuItem(item);
        }

        @Override // android.view.ActionMode.Callback
        public void onDestroyActionMode(ActionMode mode) {
            MultiSelectPopupWindow.sTextActionMode = null;
            if (MultiSelectPopupWindow.this.mSelectionController != null) {
                MultiSelectPopupWindow.this.mSelectionController.hide();
            }
        }

        @Override // android.view.ActionMode.Callback2
        public void onGetContentRect(ActionMode mode, View view, Rect outRect) {
            if (!view.equals(MultiSelectPopupWindow.sTextView) || MultiSelectPopupWindow.sTextView.getLayout() == null) {
                super.onGetContentRect(mode, view, outRect);
                return;
            }
            int popupTopMargin = MultiSelectPopupWindow.sTextView.getResources().getDimensionPixelSize(R.dimen.tw_floating_popup_top_margin);
            int popupBottomMargin = MultiSelectPopupWindow.sTextView.getResources().getDimensionPixelSize(R.dimen.tw_floating_popup_bottom_margin);
            CharSequence text = MultiSelectPopupWindow.sTextView.getTextForMultiSelection();
            if (text == null) {
                Log.e(MultiSelectPopupWindow.TAG, "getTextFormultiSelection() text is null");
                return;
            }
            if (MultiSelection.getSelectionStart(text) != MultiSelection.getSelectionEnd(text)) {
                this.mSelectionPath.reset();
                MultiSelectPopupWindow.sTextView.getLayout().getSelectionPath(MultiSelection.getSelectionStart(text), MultiSelection.getSelectionEnd(text), this.mSelectionPath);
                this.mSelectionPath.computeBounds(this.mSelectionBounds, true);
                this.mSelectionBounds.top -= popupBottomMargin;
                this.mSelectionBounds.bottom += this.mHandleHeight + popupTopMargin;
            }
            int textHorizontalOffset = MultiSelectPopupWindow.sTextView.viewportToContentHorizontalOffset();
            int textVerticalOffset = MultiSelectPopupWindow.sTextView.viewportToContentVerticalOffset();
            outRect.set((int) Math.floor(this.mSelectionBounds.left + textHorizontalOffset), (int) Math.floor(this.mSelectionBounds.top + textVerticalOffset), (int) Math.ceil(this.mSelectionBounds.right + textHorizontalOffset), (int) Math.ceil(this.mSelectionBounds.bottom + textVerticalOffset));
        }
    }

    public boolean isSelectAllEnable() {
        CharSequence text = sTextView.getTextForMultiSelection();
        if (text != null) {
            return (MultiSelection.getSelectionStart(text) == 0 && MultiSelection.getSelectionEnd(text) == text.length()) ? false : true;
        }
        Log.e(TAG, "getTextFormultiSelection() text is null");
        return false;
    }

    public boolean isShareViaEnable() {
        if (isEmergencyMode()) {
            return false;
        }
        return true;
    }

    private boolean isDictionaryEnable() {
        PackageManager pm = sTextView.getContext().getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(new Intent("com.sec.android.app.dictionary.SEARCH"), 0);
        return (activities.size() == 0 || isEmergencyMode()) ? false : true;
    }

    private boolean isTranslatorEnable() {
        PackageManager pm = sTextView.getContext().getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(new Intent().setAction(Intent.ACTION_PROCESS_TEXT).setType("text/plain"), 0);
        if (activities.size() != 0 && !isEmergencyMode()) {
            for (ResolveInfo resolveInfo : activities) {
                String resolveInfoString = resolveInfo.toString();
                if (resolveInfoString.contains("com.sec.android.app.translator") || resolveInfoString.contains("com.google.android.apps.translate")) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isEmergencyMode() {
        boolean isEmergencyMode = Settings.System.getInt(sTextView.getContext().getContentResolver(), Settings.System.SEM_EMERGENCY_MODE, 0) == 1;
        boolean isUPSMode = Settings.System.getInt(sTextView.getContext().getContentResolver(), Settings.System.SEM_ULTRA_POWERSAVING_MODE, 0) == 1;
        if (!isEmergencyMode && !isUPSMode) {
            return false;
        }
        Log.d(TAG, "isEmergencyMode = " + isEmergencyMode + ", isUPSMode = " + isUPSMode);
        return true;
    }

    public PositionListener getPositionListener() {
        if (this.mPositionListener == null) {
            this.mPositionListener = new PositionListener();
        }
        return this.mPositionListener;
    }

    public void initSelectionControllerPosition() {
        if (getSelectionController() != null) {
            getSelectionController().initPreviousOffset();
        }
    }

    SelectionController getSelectionController() {
        if (sTextView == null) {
            return null;
        }
        if (this.mSelectionController == null) {
            this.mSelectionController = new SelectionController();
            ViewTreeObserver observer = sTextView.getViewTreeObserver();
            observer.addOnTouchModeChangeListener(this.mSelectionController);
        }
        return this.mSelectionController;
    }

    /* loaded from: classes4.dex */
    public class PositionListener implements ViewTreeObserver.OnPreDrawListener {
        private final int MAXIMUM_NUMBER_OF_LISTENERS;
        private int[] mNewRect;
        private int mNumberOfListeners;
        private boolean mPositionHasChanged;
        private TextViewPositionListener[] mPositionListeners;
        private int mPositionX;
        private int mPositionY;
        private int[] mRect;
        private boolean mScrollHasChanged;
        final int[] mTempCoords;

        /* synthetic */ PositionListener(MultiSelectPopupWindow multiSelectPopupWindow, PositionListenerIA positionListenerIA) {
            this();
        }

        private PositionListener() {
            this.MAXIMUM_NUMBER_OF_LISTENERS = 2;
            this.mPositionListeners = new TextViewPositionListener[2];
            this.mPositionHasChanged = true;
            this.mRect = new int[2];
            this.mNewRect = new int[2];
            this.mTempCoords = new int[2];
        }

        public void addSubscriber(TextViewPositionListener positionListener) {
            if (this.mNumberOfListeners == 0) {
                updatePosition();
                ViewTreeObserver vto = MultiSelectPopupWindow.sTextView.getViewTreeObserver();
                vto.addOnPreDrawListener(this);
            }
            int emptySlotIndex = -1;
            for (int i = 0; i < 2; i++) {
                TextViewPositionListener listener = this.mPositionListeners[i];
                if (listener == positionListener) {
                    return;
                }
                if (emptySlotIndex < 0 && listener == null) {
                    emptySlotIndex = i;
                }
            }
            this.mPositionListeners[emptySlotIndex] = positionListener;
            this.mNumberOfListeners++;
        }

        public void removeSubscriber(TextViewPositionListener positionListener) {
            int i = 0;
            while (true) {
                if (i >= 2) {
                    break;
                }
                TextViewPositionListener[] textViewPositionListenerArr = this.mPositionListeners;
                if (textViewPositionListenerArr[i] != positionListener) {
                    i++;
                } else {
                    textViewPositionListenerArr[i] = null;
                    this.mNumberOfListeners--;
                    break;
                }
            }
            int i2 = this.mNumberOfListeners;
            if (i2 == 0) {
                ViewTreeObserver vto = MultiSelectPopupWindow.sTextView.getViewTreeObserver();
                vto.removeOnPreDrawListener(this);
            }
        }

        public int getPositionX() {
            return this.mPositionX;
        }

        public int getPositionY() {
            return this.mPositionY;
        }

        @Override // android.view.ViewTreeObserver.OnPreDrawListener
        public boolean onPreDraw() {
            if (MultiSelectPopupWindow.sTextView == null) {
                if (0 < 2) {
                    this.mPositionListeners[0] = null;
                }
                this.mNumberOfListeners = 0;
                return true;
            }
            updatePosition();
            if (!MultiSelectPopupWindow.sTextView.checkValidMultiSelectionForPreDraw()) {
                MultiSelectPopupWindow.sTextView.clearMultiSelection();
                return true;
            }
            for (int i = 0; i < 2; i++) {
                TextViewPositionListener positionListener = this.mPositionListeners[i];
                if (positionListener != null) {
                    positionListener.updatePosition(this.mPositionX, this.mPositionY, this.mPositionHasChanged, this.mScrollHasChanged);
                }
            }
            this.mScrollHasChanged = false;
            return true;
        }

        private void updatePosition() {
            boolean z;
            MultiSelectPopupWindow.sTextView.getLocationInWindow(this.mTempCoords);
            this.mNewRect[0] = MultiSelectPopupWindow.sTextView.getWidth();
            this.mNewRect[1] = MultiSelectPopupWindow.sTextView.getHeight();
            int[] iArr = this.mTempCoords;
            int i = iArr[0];
            if (i == this.mPositionX && iArr[1] == this.mPositionY) {
                int[] iArr2 = this.mRect;
                int i2 = iArr2[0];
                int[] iArr3 = this.mNewRect;
                if (i2 == iArr3[0] && iArr2[1] == iArr3[1]) {
                    z = false;
                    this.mPositionHasChanged = z;
                    this.mPositionX = i;
                    this.mPositionY = iArr[1];
                    int[] iArr4 = this.mRect;
                    int[] iArr5 = this.mNewRect;
                    iArr4[0] = iArr5[0];
                    iArr4[1] = iArr5[1];
                }
            }
            z = true;
            this.mPositionHasChanged = z;
            this.mPositionX = i;
            this.mPositionY = iArr[1];
            int[] iArr42 = this.mRect;
            int[] iArr52 = this.mNewRect;
            iArr42[0] = iArr52[0];
            iArr42[1] = iArr52[1];
        }

        public void onScrollChanged() {
            this.mScrollHasChanged = true;
        }
    }

    /* loaded from: classes4.dex */
    public class SelectionController implements CursorController {
        private SelectionEndHandleView mEndHandle;
        private SelectionStartHandleView mStartHandle;

        /* synthetic */ SelectionController(MultiSelectPopupWindow multiSelectPopupWindow, SelectionControllerIA selectionControllerIA) {
            this();
        }

        private SelectionController() {
        }

        @Override // android.widget.MultiSelectPopupWindow.CursorController
        public void show() {
            initDrawables();
            initHandles();
        }

        public void initDrawables() {
            if (MultiSelectPopupWindow.this.mSelectHandleLeft == null) {
                MultiSelectPopupWindow.this.mSelectHandleLeft = MultiSelectPopupWindow.sTextView.getContext().getResources().getDrawable(MultiSelectPopupWindow.sTextView.mTextSelectHandleLeftRes);
            }
            if (MultiSelectPopupWindow.this.mSelectHandleRight == null) {
                MultiSelectPopupWindow.this.mSelectHandleRight = MultiSelectPopupWindow.sTextView.getContext().getResources().getDrawable(MultiSelectPopupWindow.sTextView.mTextSelectHandleRightRes);
            }
        }

        public void initHandles() {
            if (this.mStartHandle == null) {
                MultiSelectPopupWindow multiSelectPopupWindow = MultiSelectPopupWindow.this;
                this.mStartHandle = new SelectionStartHandleView(multiSelectPopupWindow.mSelectHandleLeft, MultiSelectPopupWindow.this.mSelectHandleRight);
            }
            if (this.mEndHandle == null) {
                MultiSelectPopupWindow multiSelectPopupWindow2 = MultiSelectPopupWindow.this;
                this.mEndHandle = new SelectionEndHandleView(multiSelectPopupWindow2.mSelectHandleRight, MultiSelectPopupWindow.this.mSelectHandleLeft);
            }
            this.mStartHandle.show();
            this.mEndHandle.show();
        }

        @Override // android.widget.MultiSelectPopupWindow.CursorController
        public void hide() {
            SelectionStartHandleView selectionStartHandleView = this.mStartHandle;
            if (selectionStartHandleView != null) {
                selectionStartHandleView.hide();
            }
            SelectionEndHandleView selectionEndHandleView = this.mEndHandle;
            if (selectionEndHandleView != null) {
                selectionEndHandleView.hide();
            }
        }

        public boolean isSelectionStartDragged() {
            SelectionStartHandleView selectionStartHandleView = this.mStartHandle;
            return selectionStartHandleView != null && selectionStartHandleView.isDragging();
        }

        public boolean isSelectionEndDragged() {
            SelectionEndHandleView selectionEndHandleView = this.mEndHandle;
            return selectionEndHandleView != null && selectionEndHandleView.isDragging();
        }

        @Override // android.view.ViewTreeObserver.OnTouchModeChangeListener
        public void onTouchModeChanged(boolean isInTouchMode) {
            if (!isInTouchMode) {
                hide();
            }
        }

        @Override // android.widget.MultiSelectPopupWindow.CursorController
        public void onDetached() {
            ViewTreeObserver observer = MultiSelectPopupWindow.sTextView.getViewTreeObserver();
            observer.removeOnTouchModeChangeListener(this);
            SelectionStartHandleView selectionStartHandleView = this.mStartHandle;
            if (selectionStartHandleView != null) {
                selectionStartHandleView.onDetached();
            }
            SelectionEndHandleView selectionEndHandleView = this.mEndHandle;
            if (selectionEndHandleView != null) {
                selectionEndHandleView.onDetached();
            }
        }

        public void initPreviousOffset() {
            SelectionStartHandleView selectionStartHandleView = this.mStartHandle;
            if (selectionStartHandleView != null) {
                selectionStartHandleView.initPreviousOffset();
            }
            SelectionEndHandleView selectionEndHandleView = this.mEndHandle;
            if (selectionEndHandleView != null) {
                selectionEndHandleView.initPreviousOffset();
            }
        }
    }

    /* loaded from: classes4.dex */
    public abstract class HandleView extends View implements TextViewPositionListener {
        static final int HANDLE_TYPE_END = 2;
        static final int HANDLE_TYPE_NONE = 0;
        static final int HANDLE_TYPE_START = 1;
        static final String HEIGHT = "height";
        static final float MAGNIFYING_FACTOR = 1.5f;
        static final String WIDTH = "width";
        protected int mBaselineY;
        private final PopupWindow mContainer;
        protected Drawable mDrawable;
        protected Drawable mDrawableLtr;
        protected Drawable mDrawableRtl;
        protected int mEndRange;
        public int mHandleType;
        protected int mHorizontalGravity;
        protected int mHotspotX;
        private float mIdealVerticalOffset;
        protected boolean mIsDragging;
        private boolean mIsResetAnimating;
        private int mLastParentX;
        private int mLastParentY;
        private ValueAnimator mMagnifySizeAnimator;
        private int mMinSize;
        protected boolean mPositionHasChanged;
        protected int mPositionX;
        protected int mPositionY;
        private int mPreviousOffset;
        private ValueAnimator mResetAnimator;
        protected int mStartRange;
        private float mTouchOffsetY;
        private float mTouchToWindowOffsetX;
        private float mTouchToWindowOffsetY;
        protected boolean mbSwitchCursor;

        public abstract int getCurrentCursorOffset();

        protected abstract int getHotspotX(Drawable drawable, boolean z);

        public abstract void updatePosition(float f, float f2);

        protected abstract void updateSelection(int i);

        public HandleView(Drawable drawableLtr, Drawable drawableRtl) {
            super(MultiSelectPopupWindow.sTextView.getContext());
            this.mPreviousOffset = -1;
            this.mPositionHasChanged = true;
            this.mResetAnimator = null;
            this.mMagnifySizeAnimator = null;
            this.mHandleType = 0;
            LinearLayout contentHolder = new LinearLayout(MultiSelectPopupWindow.sTextView.getContext());
            contentHolder.setGravity(3);
            PopupWindow popupWindow = new PopupWindow(MultiSelectPopupWindow.sTextView.getContext(), (AttributeSet) null, 16843464);
            this.mContainer = popupWindow;
            popupWindow.setSplitTouchEnabled(true);
            popupWindow.setClippingEnabled(false);
            popupWindow.setWindowLayoutType(1002);
            popupWindow.setContentView(contentHolder);
            contentHolder.addView(this);
            this.mDrawableLtr = drawableLtr;
            this.mDrawableRtl = drawableRtl;
            updateDrawable();
            recalHandleView();
            this.mMinSize = MultiSelectPopupWindow.sTextView.getContext().getResources().getDimensionPixelSize(R.dimen.text_handle_min_size);
            popupWindow.setWidth(Math.max((int) (this.mDrawable.getIntrinsicWidth() * 1.5f), this.mMinSize));
            popupWindow.setHeight(Math.max((int) (this.mDrawable.getIntrinsicHeight() * 1.5f), this.mMinSize));
        }

        protected void updateDrawable() {
            int offset = getCurrentCursorOffset();
            boolean isRtlCharAtOffset = MultiSelectPopupWindow.sTextView.getLayout().isRtlCharAt(offset);
            Drawable drawable = isRtlCharAtOffset ? this.mDrawableRtl : this.mDrawableLtr;
            this.mDrawable = drawable;
            this.mHotspotX = getHotspotX(drawable, isRtlCharAtOffset);
            this.mHorizontalGravity = getHorizontalGravity(isRtlCharAtOffset);
        }

        protected int getHorizontalGravity(boolean isRtlRun) {
            return isRtlRun == (this.mHandleType == 1) ? 3 : 5;
        }

        protected int getHorizontalOffset() {
            int width = getPreferredWidth();
            int drawWidth = this.mDrawable.getIntrinsicWidth();
            switch (this.mHorizontalGravity) {
                case 3:
                    return 0;
                case 4:
                default:
                    int left = (width - drawWidth) / 2;
                    return left;
                case 5:
                    int left2 = width - drawWidth;
                    return left2;
            }
        }

        public Rect getDrawableBounds(int width, int height) {
            int left = getHorizontalOffset();
            Drawable drawable = this.mDrawable;
            int hotspot = getHotspotX(drawable, drawable == this.mDrawableRtl);
            int offset = 0;
            switch (this.mHorizontalGravity) {
                case 1:
                    offset = width / 2;
                    break;
                case 3:
                    offset = width / 4;
                    break;
                case 5:
                    offset = (width * 3) / 4;
                    break;
            }
            return new Rect(left - (offset - hotspot), 0, (left - (offset - hotspot)) + width, height);
        }

        @Override // android.view.View
        public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            if (this.mIsDragging || this.mIsResetAnimating) {
                setMeasuredDimension((int) Math.ceil(getPreferredWidth() * 1.5f), (int) Math.ceil(getPreferredHeight() * 1.5f));
            } else {
                setMeasuredDimension(getPreferredWidth(), getPreferredHeight());
            }
        }

        private int getPreferredWidth() {
            return Math.max(this.mDrawable.getIntrinsicWidth(), this.mMinSize);
        }

        private int getPreferredHeight() {
            return Math.max(this.mDrawable.getIntrinsicHeight(), this.mMinSize);
        }

        public void show() {
            if (isShowing()) {
                return;
            }
            MultiSelectPopupWindow.this.getPositionListener().addSubscriber(this);
            this.mPreviousOffset = -1;
            positionAtCursorOffset(getCurrentCursorOffset(), false, false);
            int[] window = new int[2];
            int[] screen = new int[2];
            MultiSelectPopupWindow.sTextView.getLocationInWindow(window);
            MultiSelectPopupWindow.sTextView.getLocationOnScreen(screen);
            int positionX = this.mPositionX + window[0];
            int positionY = this.mPositionY + window[1];
            int gapX = screen[0] - window[0];
            int gapY = screen[1] - window[1];
            if (isShowing()) {
                if (MultiSelectPopupWindow.sTextView.getApplicationWindowToken() != null && MultiSelectPopupWindow.sTextView.getApplicationWindowToken() != MultiSelectPopupWindow.sTextView.getWindowToken()) {
                    positionX += gapX;
                    positionY += gapY;
                }
                this.mContainer.update(positionX, positionY, -1, -1);
                return;
            }
            if (MultiSelectPopupWindow.sTextView.getApplicationWindowToken() != null && MultiSelectPopupWindow.sTextView.getApplicationWindowToken() != MultiSelectPopupWindow.sTextView.getWindowToken()) {
                this.mContainer.setLayoutInScreenEnabled(true);
                this.mContainer.showAtLocation(MultiSelectPopupWindow.sTextView.getApplicationWindowToken(), 0, positionX + gapX, positionY + gapY);
                return;
            }
            this.mContainer.setLayoutInScreenEnabled(false);
            try {
                this.mContainer.showAtLocation(MultiSelectPopupWindow.sTextView, 0, positionX, positionY);
            } catch (WindowManager.BadTokenException e) {
                MultiSelectPopupWindow.sTextView.clearAllMultiSelection();
                Log.e(MultiSelectPopupWindow.TAG, "showAtLocation occur BadTokenException");
            }
        }

        protected void dismiss() {
            this.mIsDragging = false;
            this.mIsResetAnimating = false;
            this.mContainer.dismiss();
            onDetached();
            this.mbSwitchCursor = false;
        }

        public void hide() {
            dismiss();
            MultiSelectPopupWindow.this.getPositionListener().removeSubscriber(this);
        }

        public boolean isShowing() {
            return this.mContainer.isShowing();
        }

        private boolean isVisible() {
            if (this.mIsDragging) {
                return true;
            }
            return isPositionVisible(this.mPositionX, this.mBaselineY);
        }

        private boolean isPositionVisible(int positionX, int positionY) {
            synchronized (MultiSelectPopupWindow.TEMP_POSITION) {
                float[] position = MultiSelectPopupWindow.TEMP_POSITION;
                position[0] = positionX;
                position[1] = positionY;
                View view = MultiSelectPopupWindow.sTextView;
                while (view != null) {
                    if (view != MultiSelectPopupWindow.sTextView) {
                        position[0] = position[0] - view.getScrollX();
                        position[1] = position[1] - view.getScrollY();
                    }
                    if (position[0] + this.mContainer.getWidth() >= 0.0f && position[1] >= 0.0f && position[0] <= view.getWidth() && position[1] <= view.getHeight()) {
                        if (!view.getMatrix().isIdentity()) {
                            view.getMatrix().mapPoints(position);
                        }
                        position[0] = position[0] + view.getLeft();
                        position[1] = position[1] + view.getTop();
                        Object parent = view.getParent();
                        if (parent instanceof View) {
                            view = (View) parent;
                        } else {
                            view = null;
                        }
                    }
                    return false;
                }
                return true;
            }
        }

        protected void positionAtCursorOffset(int offset, boolean parentPositionChanged, boolean parentScrolled) {
            Layout layout = MultiSelectPopupWindow.sTextView.getLayout();
            if (layout == null) {
                return;
            }
            boolean offsetChanged = offset != this.mPreviousOffset;
            if (offsetChanged || parentPositionChanged || parentScrolled) {
                if (offsetChanged) {
                    updateSelection(offset);
                }
                int line = layout.getLineForOffset(offset);
                float compensation = layout.getParagraphDirection(line) == -1 ? 0.5f : -0.5f;
                this.mPositionX = (int) (((layout.getPrimaryHorizontal(offset) + compensation) - this.mHotspotX) - getHorizontalOffset());
                this.mPositionY = layout.getLineBottom(line);
                this.mBaselineY = layout.getLineBaseline(line);
                this.mPositionX += MultiSelectPopupWindow.sTextView.viewportToContentHorizontalOffset();
                this.mPositionY += MultiSelectPopupWindow.sTextView.viewportToContentVerticalOffset();
                this.mBaselineY += MultiSelectPopupWindow.sTextView.viewportToContentVerticalOffset();
                this.mPreviousOffset = offset;
                this.mPositionHasChanged = true;
            }
        }

        @Override // android.widget.MultiSelectPopupWindow.TextViewPositionListener
        public void updatePosition(int parentPositionX, int parentPositionY, boolean parentPositionChanged, boolean parentScrolled) {
            positionAtCursorOffset(getCurrentCursorOffset(), parentPositionChanged, parentScrolled);
            if (parentPositionChanged || this.mPositionHasChanged) {
                if (this.mIsDragging) {
                    if (parentPositionX != this.mLastParentX || parentPositionY != this.mLastParentY) {
                        this.mTouchToWindowOffsetX += parentPositionX - r0;
                        this.mTouchToWindowOffsetY += parentPositionY - this.mLastParentY;
                        this.mLastParentX = parentPositionX;
                        this.mLastParentY = parentPositionY;
                    }
                    onHandleMoved();
                }
                if (isVisible() && !parentPositionChanged) {
                    int[] window = new int[2];
                    int[] screen = new int[2];
                    MultiSelectPopupWindow.sTextView.getLocationInWindow(window);
                    MultiSelectPopupWindow.sTextView.getLocationOnScreen(screen);
                    int gapX = screen[0] - window[0];
                    int gapY = screen[1] - window[1];
                    int positionX = this.mPositionX + parentPositionX;
                    int positionY = this.mPositionY + parentPositionY;
                    if (isShowing()) {
                        if (MultiSelectPopupWindow.sTextView.getApplicationWindowToken() != null && MultiSelectPopupWindow.sTextView.getApplicationWindowToken() != MultiSelectPopupWindow.sTextView.getWindowToken()) {
                            positionX += gapX;
                            positionY += gapY;
                        }
                        this.mContainer.update(positionX, positionY, -1, -1);
                    } else if (MultiSelectPopupWindow.sTextView.getApplicationWindowToken() != null && MultiSelectPopupWindow.sTextView.getApplicationWindowToken() != MultiSelectPopupWindow.sTextView.getWindowToken()) {
                        this.mContainer.setLayoutInScreenEnabled(true);
                        this.mContainer.showAtLocation(MultiSelectPopupWindow.sTextView.getApplicationWindowToken(), 0, positionX + gapX, positionY + gapY);
                    } else {
                        this.mContainer.setLayoutInScreenEnabled(false);
                        try {
                            this.mContainer.showAtLocation(MultiSelectPopupWindow.sTextView, 0, positionX, positionY);
                        } catch (WindowManager.BadTokenException e) {
                            MultiSelectPopupWindow.sTextView.clearAllMultiSelection();
                            Log.e(MultiSelectPopupWindow.TAG, "showAtLocation occur BadTokenException");
                        }
                    }
                } else if (isShowing()) {
                    dismiss();
                }
                this.mPositionHasChanged = false;
            }
        }

        @Override // android.view.View
        public void onDraw(Canvas c) {
            int drawWidth = this.mDrawable.getIntrinsicWidth();
            int left = getHorizontalOffset();
            if (!this.mIsDragging && !this.mIsResetAnimating) {
                Drawable drawable = this.mDrawable;
                drawable.setBounds(left, 0, left + drawWidth, drawable.getIntrinsicHeight());
            }
            this.mDrawable.draw(c);
        }

        @Override // android.view.View
        public boolean onTouchEvent(MotionEvent ev) {
            float newVerticalOffset;
            CharSequence text = MultiSelectPopupWindow.sTextView.getTextForMultiSelection();
            if (text == null) {
                Log.e(MultiSelectPopupWindow.TAG, "getTextFormultiSelection() text is null");
                return true;
            }
            MultiSelectPopupWindow.this.updateFloatingToolbarVisibility(ev);
            switch (ev.getActionMasked()) {
                case 0:
                    this.mTouchToWindowOffsetX = ev.getRawXForScaledWindow() - this.mPositionX;
                    this.mTouchToWindowOffsetY = ev.getRawYForScaledWindow() - this.mPositionY;
                    int[] range = new int[2];
                    boolean flag = MultiSelectPopupWindow.sTextView.getVisibleTextRange(range);
                    if (flag) {
                        this.mStartRange = range[0];
                        this.mEndRange = range[1];
                    } else {
                        this.mStartRange = 0;
                        this.mEndRange = text.length();
                    }
                    PositionListener positionListener = MultiSelectPopupWindow.this.getPositionListener();
                    this.mLastParentX = positionListener.getPositionX();
                    this.mLastParentY = positionListener.getPositionY();
                    this.mIsDragging = true;
                    magnifyHandleView();
                    MultiSelectPopupWindow.sTextView.mIsTouchDown = true;
                    break;
                case 1:
                    this.mIsDragging = false;
                    this.mIsResetAnimating = true;
                    resetHandleView();
                    MultiSelectPopupWindow.sTextView.mIsTouchDown = false;
                    refreshForSwitchingCursor();
                    int selStart = MultiSelection.getSelectionStart(text);
                    int selEnd = MultiSelection.getSelectionEnd(text);
                    if (selStart > selEnd) {
                        MultiSelection.setSelection((Spannable) text, selEnd, selStart);
                        break;
                    }
                    break;
                case 2:
                    float rawX = ev.getRawXForScaledWindow();
                    float rawY = ev.getRawYForScaledWindow();
                    float f = this.mTouchToWindowOffsetY;
                    int i = this.mLastParentY;
                    float previousVerticalOffset = f - i;
                    float currentVerticalOffset = (rawY - this.mPositionY) - i;
                    float newVerticalOffset2 = this.mIdealVerticalOffset;
                    if (previousVerticalOffset < newVerticalOffset2) {
                        newVerticalOffset = Math.max(Math.min(currentVerticalOffset, newVerticalOffset2), previousVerticalOffset);
                    } else if (currentVerticalOffset < previousVerticalOffset) {
                        newVerticalOffset = Math.max(Math.max(currentVerticalOffset, newVerticalOffset2), previousVerticalOffset);
                    } else {
                        newVerticalOffset = Math.min(Math.max(currentVerticalOffset, newVerticalOffset2), previousVerticalOffset);
                    }
                    this.mTouchToWindowOffsetY = this.mLastParentY + newVerticalOffset;
                    float newPosX = (rawX - this.mTouchToWindowOffsetX) + this.mHotspotX + getHorizontalOffset();
                    float newPosY = (rawY - this.mTouchToWindowOffsetY) + this.mTouchOffsetY;
                    updatePosition(newPosX, newPosY);
                    break;
                case 3:
                    this.mIsDragging = false;
                    this.mIsResetAnimating = true;
                    resetHandleView();
                    break;
            }
            return true;
        }

        public boolean isDragging() {
            return this.mIsDragging;
        }

        void onHandleMoved() {
        }

        public void onDetached() {
        }

        protected boolean calculateForSwitchingCursor() {
            return true;
        }

        public boolean refreshForSwitchingCursor() {
            return true;
        }

        public void initPreviousOffset() {
            this.mPreviousOffset = -1;
        }

        public void recalHandleView() {
            int handleHeight = this.mDrawable.getIntrinsicHeight();
            this.mTouchOffsetY = handleHeight * (-0.3f);
            this.mIdealVerticalOffset = handleHeight * 0.7f;
        }

        private void magnifyHandleView() {
            requestLayout();
            int drawableStartWidth = this.mDrawable.getIntrinsicWidth();
            int drawableStartHeight = this.mDrawable.getIntrinsicHeight();
            int drawableTargetWidth = (int) (drawableStartWidth * 1.5f);
            int drawableTargetHeight = (int) (drawableStartHeight * 1.5f);
            PropertyValuesHolder[] holders = {PropertyValuesHolder.ofInt("width", drawableStartWidth, drawableTargetWidth), PropertyValuesHolder.ofInt("height", drawableStartHeight, drawableTargetHeight)};
            ValueAnimator ofPropertyValuesHolder = ValueAnimator.ofPropertyValuesHolder(holders);
            this.mMagnifySizeAnimator = ofPropertyValuesHolder;
            ofPropertyValuesHolder.setDuration(250L);
            this.mMagnifySizeAnimator.setInterpolator(new PathInterpolator(0.25f, 0.46f, 0.45f, 1.0f));
            this.mMagnifySizeAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: android.widget.MultiSelectPopupWindow.HandleView.1
                AnonymousClass1() {
                }

                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public void onAnimationUpdate(ValueAnimator animation) {
                    int width = ((Integer) animation.getAnimatedValue("width")).intValue();
                    int height = ((Integer) animation.getAnimatedValue("height")).intValue();
                    HandleView.this.mDrawable.setBounds(HandleView.this.getDrawableBounds(width, height));
                    HandleView.this.invalidate();
                }
            });
            this.mMagnifySizeAnimator.addListener(new AnimatorListenerAdapter() { // from class: android.widget.MultiSelectPopupWindow.HandleView.2
                final /* synthetic */ int val$drawableTargetHeight;
                final /* synthetic */ int val$drawableTargetWidth;

                AnonymousClass2(int drawableTargetWidth2, int drawableTargetHeight2) {
                    drawableTargetWidth = drawableTargetWidth2;
                    drawableTargetHeight = drawableTargetHeight2;
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animation) {
                    HandleView.this.mDrawable.setBounds(HandleView.this.getDrawableBounds(drawableTargetWidth, drawableTargetHeight));
                    HandleView.this.invalidate();
                }
            });
            this.mMagnifySizeAnimator.start();
        }

        /* renamed from: android.widget.MultiSelectPopupWindow$HandleView$1 */
        /* loaded from: classes4.dex */
        public class AnonymousClass1 implements ValueAnimator.AnimatorUpdateListener {
            AnonymousClass1() {
            }

            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator animation) {
                int width = ((Integer) animation.getAnimatedValue("width")).intValue();
                int height = ((Integer) animation.getAnimatedValue("height")).intValue();
                HandleView.this.mDrawable.setBounds(HandleView.this.getDrawableBounds(width, height));
                HandleView.this.invalidate();
            }
        }

        /* renamed from: android.widget.MultiSelectPopupWindow$HandleView$2 */
        /* loaded from: classes4.dex */
        public class AnonymousClass2 extends AnimatorListenerAdapter {
            final /* synthetic */ int val$drawableTargetHeight;
            final /* synthetic */ int val$drawableTargetWidth;

            AnonymousClass2(int drawableTargetWidth2, int drawableTargetHeight2) {
                drawableTargetWidth = drawableTargetWidth2;
                drawableTargetHeight = drawableTargetHeight2;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                HandleView.this.mDrawable.setBounds(HandleView.this.getDrawableBounds(drawableTargetWidth, drawableTargetHeight));
                HandleView.this.invalidate();
            }
        }

        private void resetHandleView() {
            if (this.mMagnifySizeAnimator.isStarted()) {
                this.mMagnifySizeAnimator.pause();
            }
            Rect r = this.mDrawable.getBounds();
            int drawableStartWidth = r.right - r.left;
            int drawableStartHeight = r.bottom - r.top;
            int drawableTargetWidth = this.mDrawable.getIntrinsicWidth();
            int drawableTargetHeight = this.mDrawable.getIntrinsicHeight();
            PropertyValuesHolder[] holders = {PropertyValuesHolder.ofInt("width", drawableStartWidth, drawableTargetWidth), PropertyValuesHolder.ofInt("height", drawableStartHeight, drawableTargetHeight)};
            ValueAnimator ofPropertyValuesHolder = ValueAnimator.ofPropertyValuesHolder(holders);
            this.mResetAnimator = ofPropertyValuesHolder;
            ofPropertyValuesHolder.setDuration(250L);
            this.mResetAnimator.setInterpolator(new PathInterpolator(0.25f, 0.46f, 0.45f, 1.0f));
            this.mResetAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: android.widget.MultiSelectPopupWindow.HandleView.3
                AnonymousClass3() {
                }

                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public void onAnimationUpdate(ValueAnimator animation) {
                    if (!HandleView.this.mIsResetAnimating) {
                        return;
                    }
                    int width = ((Integer) animation.getAnimatedValue("width")).intValue();
                    int height = ((Integer) animation.getAnimatedValue("height")).intValue();
                    HandleView.this.mDrawable.setBounds(HandleView.this.getDrawableBounds(width, height));
                    HandleView.this.invalidate();
                }
            });
            this.mResetAnimator.addListener(new AnimatorListenerAdapter() { // from class: android.widget.MultiSelectPopupWindow.HandleView.4
                AnonymousClass4() {
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animation) {
                    if (!HandleView.this.mIsResetAnimating) {
                        return;
                    }
                    HandleView.this.mIsResetAnimating = false;
                    HandleView.this.requestLayout();
                    HandleView.this.invalidate();
                }
            });
            this.mResetAnimator.start();
        }

        /* renamed from: android.widget.MultiSelectPopupWindow$HandleView$3 */
        /* loaded from: classes4.dex */
        public class AnonymousClass3 implements ValueAnimator.AnimatorUpdateListener {
            AnonymousClass3() {
            }

            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator animation) {
                if (!HandleView.this.mIsResetAnimating) {
                    return;
                }
                int width = ((Integer) animation.getAnimatedValue("width")).intValue();
                int height = ((Integer) animation.getAnimatedValue("height")).intValue();
                HandleView.this.mDrawable.setBounds(HandleView.this.getDrawableBounds(width, height));
                HandleView.this.invalidate();
            }
        }

        /* renamed from: android.widget.MultiSelectPopupWindow$HandleView$4 */
        /* loaded from: classes4.dex */
        public class AnonymousClass4 extends AnimatorListenerAdapter {
            AnonymousClass4() {
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                if (!HandleView.this.mIsResetAnimating) {
                    return;
                }
                HandleView.this.mIsResetAnimating = false;
                HandleView.this.requestLayout();
                HandleView.this.invalidate();
            }
        }
    }

    /* loaded from: classes4.dex */
    public class SelectionStartHandleView extends HandleView {
        public SelectionStartHandleView(Drawable drawableLtr, Drawable drawableRtl) {
            super(drawableLtr, drawableRtl);
            this.mHandleType = 1;
        }

        @Override // android.widget.MultiSelectPopupWindow.HandleView
        protected int getHotspotX(Drawable drawable, boolean isRtlRun) {
            if (isRtlRun) {
                return drawable.getIntrinsicWidth() / 4;
            }
            return (drawable.getIntrinsicWidth() * 3) / 4;
        }

        @Override // android.widget.MultiSelectPopupWindow.HandleView
        public int getCurrentCursorOffset() {
            CharSequence text = MultiSelectPopupWindow.sTextView.getTextForMultiSelection();
            if (text == null) {
                Log.e(MultiSelectPopupWindow.TAG, "getTextFormultiSelection() text is null");
            }
            return MultiSelection.getSelectionStart(text);
        }

        @Override // android.widget.MultiSelectPopupWindow.HandleView
        public void updateSelection(int offset) {
            CharSequence text = MultiSelectPopupWindow.sTextView.getTextForMultiSelection();
            if (text == null) {
                Log.e(MultiSelectPopupWindow.TAG, "getTextFormultiSelection() text is null");
                return;
            }
            MultiSelection.setSelection((Spannable) text, offset, MultiSelection.getSelectionEnd(text));
            updateDrawable();
            if (MultiSelectPopupWindow.sTextActionMode != null) {
                MultiSelectPopupWindow.sTextActionMode.invalidate();
            }
        }

        @Override // android.widget.MultiSelectPopupWindow.HandleView
        public void updatePosition(float x, float y) {
            int offset = MultiSelectPopupWindow.sTextView.getOffsetForPosition(x, y);
            CharSequence text = MultiSelectPopupWindow.sTextView.getTextForMultiSelection();
            int selectionEnd = MultiSelection.getSelectionEnd(text);
            if (offset == selectionEnd) {
                return;
            }
            if (offset < this.mStartRange) {
                offset = this.mStartRange;
            }
            positionAtCursorOffset(offset, false, false);
        }

        @Override // android.widget.MultiSelectPopupWindow.HandleView
        protected void positionAtCursorOffset(int offset, boolean parentPositionChanged, boolean parentScrolled) {
            super.positionAtCursorOffset(offset, parentPositionChanged, parentScrolled);
            calculateForSwitchingCursor();
            this.mPositionHasChanged = true;
            invalidate();
        }

        @Override // android.widget.MultiSelectPopupWindow.HandleView
        public boolean refreshForSwitchingCursor() {
            if (isHandleViewScreenOut() && !this.mbSwitchCursor) {
                MultiSelectPopupWindow.sTextView.invalidate();
                return true;
            }
            return false;
        }

        @Override // android.widget.MultiSelectPopupWindow.HandleView
        protected boolean calculateForSwitchingCursor() {
            boolean bSwitchCursor = this.mbSwitchCursor;
            this.mbSwitchCursor = false;
            if (isHandleViewScreenOut()) {
                this.mbSwitchCursor = true;
            }
            if (bSwitchCursor == this.mbSwitchCursor) {
                return false;
            }
            updateDrawable();
            Layout layout = MultiSelectPopupWindow.sTextView.getLayout();
            this.mPositionX = (int) ((layout.getPrimaryHorizontal(getCurrentCursorOffset()) - 0.5f) - this.mHotspotX);
            this.mPositionX += MultiSelectPopupWindow.sTextView.viewportToContentHorizontalOffset();
            return true;
        }

        @Override // android.widget.MultiSelectPopupWindow.HandleView
        protected void updateDrawable() {
            int offset = getCurrentCursorOffset();
            Drawable oldDrawable = this.mDrawable;
            boolean isRtlCharAtOffset = MultiSelectPopupWindow.sTextView.getLayout().isRtlCharAt(offset);
            if (this.mbSwitchCursor) {
                isRtlCharAtOffset = !isRtlCharAtOffset;
            }
            this.mDrawable = isRtlCharAtOffset ? this.mDrawableRtl : this.mDrawableLtr;
            this.mHotspotX = getHotspotX(this.mDrawable, isRtlCharAtOffset);
            this.mHorizontalGravity = getHorizontalGravity(isRtlCharAtOffset);
            if (oldDrawable != this.mDrawable) {
                recalHandleView();
                invalidate();
            }
        }

        private boolean isHandleViewScreenOut() {
            PositionListener positionListener = MultiSelectPopupWindow.this.getPositionListener();
            int iconSize = this.mDrawableRtl.getIntrinsicWidth() / 2;
            return (((this.mPositionX + positionListener.getPositionX()) + this.mHotspotX) + getHorizontalOffset()) - iconSize < 0;
        }
    }

    /* loaded from: classes4.dex */
    public class SelectionEndHandleView extends HandleView {
        public SelectionEndHandleView(Drawable drawableLtr, Drawable drawableRtl) {
            super(drawableLtr, drawableRtl);
            this.mHandleType = 2;
        }

        @Override // android.widget.MultiSelectPopupWindow.HandleView
        protected int getHotspotX(Drawable drawable, boolean isRtlRun) {
            if (isRtlRun) {
                return (drawable.getIntrinsicWidth() * 3) / 4;
            }
            return drawable.getIntrinsicWidth() / 4;
        }

        @Override // android.widget.MultiSelectPopupWindow.HandleView
        public int getCurrentCursorOffset() {
            CharSequence text = MultiSelectPopupWindow.sTextView.getTextForMultiSelection();
            if (text == null) {
                Log.e(MultiSelectPopupWindow.TAG, "getTextFormultiSelection() text is null");
            }
            return MultiSelection.getSelectionEnd(text);
        }

        @Override // android.widget.MultiSelectPopupWindow.HandleView
        public void updateSelection(int offset) {
            CharSequence text = MultiSelectPopupWindow.sTextView.getTextForMultiSelection();
            if (text == null) {
                Log.e(MultiSelectPopupWindow.TAG, "getTextFormultiSelection() text is null");
                return;
            }
            MultiSelection.setSelection((Spannable) text, MultiSelection.getSelectionStart(text), offset);
            updateDrawable();
            if (MultiSelectPopupWindow.sTextActionMode != null) {
                MultiSelectPopupWindow.sTextActionMode.invalidate();
            }
        }

        @Override // android.widget.MultiSelectPopupWindow.HandleView
        public void updatePosition(float x, float y) {
            int offset = MultiSelectPopupWindow.sTextView.getOffsetForPosition(x, y);
            CharSequence text = MultiSelectPopupWindow.sTextView.getTextForMultiSelection();
            int selectionStart = MultiSelection.getSelectionStart(text);
            if (offset == selectionStart) {
                return;
            }
            if (offset > this.mEndRange) {
                offset = this.mEndRange;
            }
            positionAtCursorOffset(offset, false, false);
        }

        @Override // android.widget.MultiSelectPopupWindow.HandleView
        protected void positionAtCursorOffset(int offset, boolean parentPositionChanged, boolean parentScrolled) {
            super.positionAtCursorOffset(offset, parentPositionChanged, parentScrolled);
            if (!this.mIsDragging) {
                calculateForSwitchingCursor();
                this.mPositionHasChanged = true;
                invalidate();
            }
        }

        @Override // android.widget.MultiSelectPopupWindow.HandleView
        public boolean refreshForSwitchingCursor() {
            if (this.mbSwitchCursor || (isHandleViewScreenOut() && !this.mbSwitchCursor)) {
                MultiSelectPopupWindow.sTextView.invalidate();
                return true;
            }
            return false;
        }

        @Override // android.widget.MultiSelectPopupWindow.HandleView
        protected boolean calculateForSwitchingCursor() {
            boolean bSwitchCursor = this.mbSwitchCursor;
            this.mbSwitchCursor = false;
            if (isHandleViewScreenOut()) {
                this.mbSwitchCursor = true;
            }
            if (bSwitchCursor == this.mbSwitchCursor) {
                return false;
            }
            updateDrawable();
            Layout layout = MultiSelectPopupWindow.sTextView.getLayout();
            this.mPositionX = (int) ((layout.getPrimaryHorizontal(getCurrentCursorOffset()) - 0.5f) - this.mHotspotX);
            this.mPositionX += MultiSelectPopupWindow.sTextView.viewportToContentHorizontalOffset();
            return true;
        }

        @Override // android.widget.MultiSelectPopupWindow.HandleView
        protected void updateDrawable() {
            int offset = getCurrentCursorOffset();
            Drawable oldDrawable = this.mDrawable;
            boolean isRtlCharAtOffset = MultiSelectPopupWindow.sTextView.getLayout().isRtlCharAt(offset);
            if (this.mbSwitchCursor) {
                isRtlCharAtOffset = !isRtlCharAtOffset;
            }
            this.mDrawable = isRtlCharAtOffset ? this.mDrawableRtl : this.mDrawableLtr;
            this.mHotspotX = getHotspotX(this.mDrawable, isRtlCharAtOffset);
            this.mHorizontalGravity = getHorizontalGravity(isRtlCharAtOffset);
            if (oldDrawable != this.mDrawable) {
                recalHandleView();
                invalidate();
            }
        }

        private boolean isHandleViewScreenOut() {
            DisplayMetrics displayMetrics = this.mContext.getResources().getDisplayMetrics();
            PositionListener positionListener = MultiSelectPopupWindow.this.getPositionListener();
            int iconSize = this.mDrawableRtl.getIntrinsicWidth() / 2;
            return (((this.mPositionX + positionListener.getPositionX()) + this.mHotspotX) + getHorizontalOffset()) + iconSize > displayMetrics.widthPixels;
        }
    }
}
