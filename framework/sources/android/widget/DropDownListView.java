package android.widget;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import com.android.internal.widget.AutoScrollHelper;

/* loaded from: classes4.dex */
public class DropDownListView extends ListView {
    private boolean mDrawsInPressedState;
    private boolean mHijackFocus;
    boolean mIsAutoCompleteTextPopup;
    private boolean mListSelectionHidden;
    private ResolveHoverRunnable mResolveHoverRunnable;
    private AutoScrollHelper.AbsListViewAutoScroller mScrollHelper;

    public DropDownListView(Context context, boolean hijackFocus) {
        this(context, hijackFocus, 16842861);
    }

    public DropDownListView(Context context, boolean hijackFocus, int defStyleAttr) {
        super(context, null, defStyleAttr);
        this.mIsAutoCompleteTextPopup = false;
        this.mHijackFocus = hijackFocus;
        setCacheColorHint(0);
    }

    @Override // android.widget.AbsListView
    boolean shouldShowSelector() {
        return isHovered() || super.shouldShowSelector();
    }

    @Override // android.widget.ListView, android.widget.AbsListView, android.view.View
    public boolean onTouchEvent(MotionEvent ev) {
        if (this.mResolveHoverRunnable != null) {
            this.mResolveHoverRunnable.cancel();
        }
        return super.onTouchEvent(ev);
    }

    @Override // android.view.View
    public boolean onHoverEvent(MotionEvent ev) {
        int action = ev.getActionMasked();
        if (action == 10 && this.mResolveHoverRunnable == null) {
            this.mResolveHoverRunnable = new ResolveHoverRunnable();
            this.mResolveHoverRunnable.post();
        }
        boolean handled = super.onHoverEvent(ev);
        if (action == 9 || action == 7) {
            int position = pointToPosition((int) ev.getX(), (int) ev.getY());
            if (position != -1 && position != this.mSelectedPosition) {
                View hoveredItem = getChildAt(position - getFirstVisiblePosition());
                if (hoveredItem.isEnabled()) {
                    requestFocus();
                    boolean talkbackEnabled = false;
                    AccessibilityManager accessibilityManager = (AccessibilityManager) this.mContext.getSystemService(Context.ACCESSIBILITY_SERVICE);
                    if (accessibilityManager != null) {
                        talkbackEnabled = accessibilityManager.semIsScreenReaderEnabled();
                    }
                    if (!talkbackEnabled) {
                        if (!isHovered()) {
                            setHovered(true);
                        }
                        positionSelector(position, hoveredItem);
                        setSelectedPositionInt(position);
                        setNextSelectedPositionInt(position);
                    }
                }
                updateSelectorState();
            }
        } else if (!super.shouldShowSelector()) {
            setSelectedPositionInt(-1);
            setNextSelectedPositionInt(-1);
        }
        return handled;
    }

    @Override // android.widget.AbsListView, android.view.ViewGroup, android.view.View
    protected void drawableStateChanged() {
        if (this.mResolveHoverRunnable == null) {
            super.drawableStateChanged();
        }
    }

    public boolean onForwardedEvent(MotionEvent event, int activePointerId) {
        boolean handledEvent = true;
        boolean clearPressedItem = false;
        int actionMasked = event.getActionMasked();
        switch (actionMasked) {
            case 1:
                handledEvent = false;
            case 2:
                int activeIndex = event.findPointerIndex(activePointerId);
                if (activeIndex < 0) {
                    handledEvent = false;
                    break;
                } else {
                    int x = (int) event.getX(activeIndex);
                    int y = (int) event.getY(activeIndex);
                    if (y >= 0) {
                        int position = pointToPosition(x, y);
                        if (position == -1) {
                            clearPressedItem = true;
                            break;
                        } else {
                            View child = getChildAt(position - getFirstVisiblePosition());
                            setPressedItem(child, position, x, y);
                            handledEvent = true;
                            if (actionMasked == 1) {
                                long id = getItemIdAtPosition(position);
                                performItemClick(child, position, id);
                                break;
                            }
                        }
                    }
                }
                break;
            case 3:
                handledEvent = false;
                break;
        }
        if (!handledEvent || clearPressedItem) {
            clearPressedItem();
        }
        if (handledEvent) {
            if (this.mScrollHelper == null) {
                this.mScrollHelper = new AutoScrollHelper.AbsListViewAutoScroller(this);
            }
            this.mScrollHelper.setEnabled(true);
            this.mScrollHelper.onTouch(this, event);
        } else if (this.mScrollHelper != null) {
            this.mScrollHelper.setEnabled(false);
        }
        return handledEvent;
    }

    public void setListSelectionHidden(boolean hideListSelection) {
        this.mListSelectionHidden = hideListSelection;
    }

    private void clearPressedItem() {
        this.mDrawsInPressedState = false;
        setPressed(false);
        updateSelectorState();
        View motionView = getChildAt(this.mMotionPosition - this.mFirstPosition);
        if (motionView != null) {
            motionView.setPressed(false);
        }
    }

    private void setPressedItem(View child, int position, float x, float y) {
        this.mDrawsInPressedState = true;
        drawableHotspotChanged(x, y);
        if (!isPressed()) {
            setPressed(true);
        }
        if (this.mDataChanged) {
            layoutChildren();
        }
        View motionView = getChildAt(this.mMotionPosition - this.mFirstPosition);
        if (motionView != null && motionView != child && motionView.isPressed()) {
            motionView.setPressed(false);
        }
        this.mMotionPosition = position;
        float childX = x - child.getLeft();
        float childY = y - child.getTop();
        child.drawableHotspotChanged(childX, childY);
        if (!child.isPressed()) {
            child.setPressed(true);
        }
        setSelectedPositionInt(position);
        positionSelectorLikeTouch(position, child, x, y);
        refreshDrawableState();
    }

    @Override // android.widget.AbsListView
    boolean touchModeDrawsInPressedState() {
        return this.mDrawsInPressedState || super.touchModeDrawsInPressedState();
    }

    @Override // android.widget.AbsListView
    View obtainView(int position, boolean[] isScrap) {
        View view = super.obtainView(position, isScrap);
        if (view instanceof TextView) {
            ((TextView) view).setHorizontallyScrolling(true);
        }
        return view;
    }

    @Override // android.view.View
    public boolean isInTouchMode() {
        return (this.mHijackFocus && this.mListSelectionHidden) || super.isInTouchMode();
    }

    @Override // android.view.View
    public boolean hasWindowFocus() {
        return this.mHijackFocus || super.hasWindowFocus();
    }

    @Override // android.view.View
    public boolean isFocused() {
        return this.mHijackFocus || super.isFocused();
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean hasFocus() {
        return this.mHijackFocus || super.hasFocus();
    }

    private class ResolveHoverRunnable implements Runnable {
        private ResolveHoverRunnable() {
        }

        @Override // java.lang.Runnable
        public void run() {
            DropDownListView.this.mResolveHoverRunnable = null;
            DropDownListView.this.drawableStateChanged();
        }

        public void cancel() {
            DropDownListView.this.mResolveHoverRunnable = null;
            DropDownListView.this.removeCallbacks(this);
        }

        public void post() {
            DropDownListView.this.post(this);
        }
    }
}
