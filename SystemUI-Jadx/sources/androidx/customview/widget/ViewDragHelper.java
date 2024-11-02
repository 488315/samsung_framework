package androidx.customview.widget;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.Interpolator;
import android.widget.OverScroller;
import androidx.core.view.ViewCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.slidingpanelayout.widget.SlidingPaneLayout;
import com.sec.ims.volte2.data.VolteConstants;
import java.util.Arrays;
import java.util.WeakHashMap;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class ViewDragHelper {
    public static final AnonymousClass1 sInterpolator = new Interpolator() { // from class: androidx.customview.widget.ViewDragHelper.1
        @Override // android.animation.TimeInterpolator
        public final float getInterpolation(float f) {
            float f2 = f - 1.0f;
            return (f2 * f2 * f2 * f2 * f2) + 1.0f;
        }
    };
    public final Callback mCallback;
    public View mCapturedView;
    public final int mDefaultEdgeSize;
    public int mDragState;
    public int[] mEdgeDragsInProgress;
    public int[] mEdgeDragsLocked;
    public int mEdgeSize;
    public int[] mInitialEdgesTouched;
    public float[] mInitialMotionX;
    public float[] mInitialMotionY;
    public float[] mLastMotionX;
    public float[] mLastMotionY;
    public final float mMaxVelocity;
    public float mMinVelocity;
    public final ViewGroup mParentView;
    public int mPointersDown;
    public boolean mReleaseInProgress;
    public final OverScroller mScroller;
    public int mTouchSlop;
    public int mTrackingEdges;
    public VelocityTracker mVelocityTracker;
    public int mActivePointerId = -1;
    public final AnonymousClass2 mSetIdleRunnable = new Runnable() { // from class: androidx.customview.widget.ViewDragHelper.2
        @Override // java.lang.Runnable
        public final void run() {
            ViewDragHelper.this.setDragState(0);
        }
    };
    public boolean mIsUpdateOffsetLR = true;

    /* JADX WARN: Type inference failed for: r0v1, types: [androidx.customview.widget.ViewDragHelper$2] */
    private ViewDragHelper(Context context, ViewGroup viewGroup, Callback callback) {
        if (viewGroup != null) {
            if (callback != null) {
                this.mParentView = viewGroup;
                this.mCallback = callback;
                ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
                int i = (int) ((context.getResources().getDisplayMetrics().density * 20.0f) + 0.5f);
                this.mDefaultEdgeSize = i;
                this.mEdgeSize = i;
                this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
                this.mMaxVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
                this.mMinVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
                this.mScroller = new OverScroller(context, sInterpolator);
                return;
            }
            throw new IllegalArgumentException("Callback may not be null");
        }
        throw new IllegalArgumentException("Parent view may not be null");
    }

    public static ViewDragHelper create(ViewGroup viewGroup, Callback callback) {
        return new ViewDragHelper(viewGroup.getContext(), viewGroup, callback);
    }

    public static boolean isViewUnder(View view, int i, int i2) {
        if (view == null || i < view.getLeft() || i >= view.getRight() || i2 < view.getTop() || i2 >= view.getBottom()) {
            return false;
        }
        return true;
    }

    public static ViewDragHelper seslCreate(ViewGroup viewGroup, SlidingPaneLayout.DragHelperCallback dragHelperCallback) {
        ViewDragHelper viewDragHelper = new ViewDragHelper(viewGroup.getContext(), viewGroup, dragHelperCallback);
        viewDragHelper.mTouchSlop = (int) (2.0f * viewDragHelper.mTouchSlop);
        viewDragHelper.mIsUpdateOffsetLR = false;
        return viewDragHelper;
    }

    public final void abort() {
        cancel();
        if (this.mDragState == 2) {
            OverScroller overScroller = this.mScroller;
            int currX = overScroller.getCurrX();
            overScroller.getCurrY();
            overScroller.abortAnimation();
            int currX2 = overScroller.getCurrX();
            int currY = overScroller.getCurrY();
            this.mCallback.onViewPositionChanged(this.mCapturedView, currX2, currY, currX2 - currX);
        }
        setDragState(0);
    }

    public final void cancel() {
        this.mActivePointerId = -1;
        float[] fArr = this.mInitialMotionX;
        if (fArr != null) {
            Arrays.fill(fArr, 0.0f);
            Arrays.fill(this.mInitialMotionY, 0.0f);
            Arrays.fill(this.mLastMotionX, 0.0f);
            Arrays.fill(this.mLastMotionY, 0.0f);
            Arrays.fill(this.mInitialEdgesTouched, 0);
            Arrays.fill(this.mEdgeDragsInProgress, 0);
            Arrays.fill(this.mEdgeDragsLocked, 0);
            this.mPointersDown = 0;
        }
        VelocityTracker velocityTracker = this.mVelocityTracker;
        if (velocityTracker != null) {
            velocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    public final void captureChildView(View view, int i) {
        ViewParent parent = view.getParent();
        ViewGroup viewGroup = this.mParentView;
        if (parent == viewGroup) {
            this.mCapturedView = view;
            this.mActivePointerId = i;
            this.mCallback.onViewCaptured(view, i);
            setDragState(1);
            return;
        }
        throw new IllegalArgumentException("captureChildView: parameter must be a descendant of the ViewDragHelper's tracked parent view (" + viewGroup + ")");
    }

    public final boolean checkNewEdgeDrag(int i, float f, float f2, int i2) {
        float abs = Math.abs(f);
        float abs2 = Math.abs(f2);
        if ((this.mInitialEdgesTouched[i] & i2) != i2 || (this.mTrackingEdges & i2) == 0 || (this.mEdgeDragsLocked[i] & i2) == i2 || (this.mEdgeDragsInProgress[i] & i2) == i2) {
            return false;
        }
        int i3 = this.mTouchSlop;
        if (abs <= i3 && abs2 <= i3) {
            return false;
        }
        if (abs < abs2 * 0.5f) {
            this.mCallback.getClass();
        }
        if ((this.mEdgeDragsInProgress[i] & i2) != 0 || abs <= this.mTouchSlop) {
            return false;
        }
        return true;
    }

    public final boolean checkTouchSlop(View view, float f, float f2) {
        boolean z;
        boolean z2;
        if (view == null) {
            return false;
        }
        Callback callback = this.mCallback;
        if (callback.getViewHorizontalDragRange(view) > 0) {
            z = true;
        } else {
            z = false;
        }
        if (callback.getViewVerticalDragRange() > 0) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (z && z2) {
            float f3 = (f2 * f2) + (f * f);
            int i = this.mTouchSlop;
            if (f3 <= i * i) {
                return false;
            }
            return true;
        }
        if (z) {
            if (Math.abs(f) <= this.mTouchSlop) {
                return false;
            }
            return true;
        }
        if (!z2 || Math.abs(f2) <= this.mTouchSlop) {
            return false;
        }
        return true;
    }

    public final void clearMotionHistory(int i) {
        float[] fArr = this.mInitialMotionX;
        if (fArr != null) {
            int i2 = this.mPointersDown;
            boolean z = true;
            int i3 = 1 << i;
            if ((i2 & i3) == 0) {
                z = false;
            }
            if (z) {
                fArr[i] = 0.0f;
                this.mInitialMotionY[i] = 0.0f;
                this.mLastMotionX[i] = 0.0f;
                this.mLastMotionY[i] = 0.0f;
                this.mInitialEdgesTouched[i] = 0;
                this.mEdgeDragsInProgress[i] = 0;
                this.mEdgeDragsLocked[i] = 0;
                this.mPointersDown = (~i3) & i2;
            }
        }
    }

    public final int computeAxisDuration(int i, int i2, int i3) {
        int abs;
        if (i == 0) {
            return 0;
        }
        float width = this.mParentView.getWidth() / 2;
        float sin = (((float) Math.sin((Math.min(1.0f, Math.abs(i) / r3) - 0.5f) * 0.47123894f)) * width) + width;
        int abs2 = Math.abs(i2);
        if (abs2 > 0) {
            abs = Math.round(Math.abs(sin / abs2) * 1000.0f) * 4;
        } else {
            abs = (int) (((Math.abs(i) / i3) + 1.0f) * 256.0f);
        }
        return Math.min(abs, VolteConstants.ErrorCode.BUSY_EVERYWHERE);
    }

    public final boolean continueSettling() {
        if (this.mDragState == 2) {
            OverScroller overScroller = this.mScroller;
            boolean computeScrollOffset = overScroller.computeScrollOffset();
            int currX = overScroller.getCurrX();
            int currY = overScroller.getCurrY();
            int left = currX - this.mCapturedView.getLeft();
            int top = currY - this.mCapturedView.getTop();
            if (left != 0 && this.mIsUpdateOffsetLR) {
                View view = this.mCapturedView;
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                view.offsetLeftAndRight(left);
            }
            if (top != 0) {
                View view2 = this.mCapturedView;
                WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                view2.offsetTopAndBottom(top);
            }
            if (left != 0 || top != 0) {
                this.mCallback.onViewPositionChanged(this.mCapturedView, currX, currY, left);
            }
            if (computeScrollOffset && currX == overScroller.getFinalX() && currY == overScroller.getFinalY()) {
                overScroller.abortAnimation();
                computeScrollOffset = false;
            }
            if (!computeScrollOffset) {
                this.mParentView.post(this.mSetIdleRunnable);
            }
        }
        if (this.mDragState != 2) {
            return false;
        }
        return true;
    }

    public final View findTopChildUnder(int i, int i2) {
        ViewGroup viewGroup = this.mParentView;
        for (int childCount = viewGroup.getChildCount() - 1; childCount >= 0; childCount--) {
            this.mCallback.getClass();
            View childAt = viewGroup.getChildAt(childCount);
            if (i >= childAt.getLeft() && i < childAt.getRight() && i2 >= childAt.getTop() && i2 < childAt.getBottom()) {
                return childAt;
            }
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x005d  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0065  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0060  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean forceSettleCapturedViewAt(int r11, int r12, int r13, int r14) {
        /*
            r10 = this;
            android.view.View r0 = r10.mCapturedView
            int r2 = r0.getLeft()
            android.view.View r0 = r10.mCapturedView
            int r3 = r0.getTop()
            int r4 = r11 - r2
            int r5 = r12 - r3
            android.widget.OverScroller r1 = r10.mScroller
            r11 = 0
            if (r4 != 0) goto L1e
            if (r5 != 0) goto L1e
            r1.abortAnimation()
            r10.setDragState(r11)
            return r11
        L1e:
            android.view.View r12 = r10.mCapturedView
            float r0 = r10.mMinVelocity
            int r0 = (int) r0
            float r6 = r10.mMaxVelocity
            int r6 = (int) r6
            int r7 = java.lang.Math.abs(r13)
            if (r7 >= r0) goto L2e
            r13 = r11
            goto L35
        L2e:
            if (r7 <= r6) goto L35
            if (r13 <= 0) goto L34
            r13 = r6
            goto L35
        L34:
            int r13 = -r6
        L35:
            float r0 = r10.mMinVelocity
            int r0 = (int) r0
            int r7 = java.lang.Math.abs(r14)
            if (r7 >= r0) goto L3f
            goto L46
        L3f:
            if (r7 <= r6) goto L47
            if (r14 <= 0) goto L45
            r14 = r6
            goto L47
        L45:
            int r11 = -r6
        L46:
            r14 = r11
        L47:
            int r11 = java.lang.Math.abs(r4)
            int r0 = java.lang.Math.abs(r5)
            int r6 = java.lang.Math.abs(r13)
            int r7 = java.lang.Math.abs(r14)
            int r8 = r6 + r7
            int r9 = r11 + r0
            if (r13 == 0) goto L60
            float r11 = (float) r6
            float r6 = (float) r8
            goto L62
        L60:
            float r11 = (float) r11
            float r6 = (float) r9
        L62:
            float r11 = r11 / r6
            if (r14 == 0) goto L68
            float r0 = (float) r7
            float r6 = (float) r8
            goto L6a
        L68:
            float r0 = (float) r0
            float r6 = (float) r9
        L6a:
            float r0 = r0 / r6
            androidx.customview.widget.ViewDragHelper$Callback r6 = r10.mCallback
            int r12 = r6.getViewHorizontalDragRange(r12)
            int r12 = r10.computeAxisDuration(r4, r13, r12)
            int r13 = r6.getViewVerticalDragRange()
            int r13 = r10.computeAxisDuration(r5, r14, r13)
            float r12 = (float) r12
            float r12 = r12 * r11
            float r11 = (float) r13
            float r11 = r11 * r0
            float r11 = r11 + r12
            int r6 = (int) r11
            r1.startScroll(r2, r3, r4, r5, r6)
            r11 = 2
            r10.setDragState(r11)
            r10 = 1
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.customview.widget.ViewDragHelper.forceSettleCapturedViewAt(int, int, int, int):boolean");
    }

    public final boolean isValidPointerForActionMove(int i) {
        boolean z;
        if ((this.mPointersDown & (1 << i)) != 0) {
            z = true;
        } else {
            z = false;
        }
        if (z && i != -1) {
            return true;
        }
        Log.e("ViewDragHelper", "Ignoring pointerId=" + i + " because ACTION_DOWN was not received for this pointer before ACTION_MOVE. It likely happened because  ViewDragHelper did not receive all the events in the event stream.");
        return false;
    }

    public final void processTouchEvent(MotionEvent motionEvent) {
        int i;
        int actionMasked = motionEvent.getActionMasked();
        int actionIndex = motionEvent.getActionIndex();
        if (actionMasked == 0) {
            cancel();
        }
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(motionEvent);
        int i2 = 0;
        Callback callback = this.mCallback;
        if (actionMasked != 0) {
            if (actionMasked != 1) {
                if (actionMasked != 2) {
                    if (actionMasked != 3) {
                        if (actionMasked != 5) {
                            if (actionMasked == 6) {
                                int pointerId = motionEvent.getPointerId(actionIndex);
                                if (this.mDragState == 1 && pointerId == this.mActivePointerId) {
                                    int pointerCount = motionEvent.getPointerCount();
                                    while (true) {
                                        if (i2 < pointerCount) {
                                            int pointerId2 = motionEvent.getPointerId(i2);
                                            if (pointerId2 != this.mActivePointerId) {
                                                View findTopChildUnder = findTopChildUnder((int) motionEvent.getX(i2), (int) motionEvent.getY(i2));
                                                View view = this.mCapturedView;
                                                if (findTopChildUnder == view && tryCaptureViewForDrag(view, pointerId2)) {
                                                    i = this.mActivePointerId;
                                                    break;
                                                }
                                            }
                                            i2++;
                                        } else {
                                            i = -1;
                                            break;
                                        }
                                    }
                                    if (i == -1) {
                                        releaseViewForPointerUp();
                                    }
                                }
                                clearMotionHistory(pointerId);
                                return;
                            }
                            return;
                        }
                        int pointerId3 = motionEvent.getPointerId(actionIndex);
                        float x = motionEvent.getX(actionIndex);
                        float y = motionEvent.getY(actionIndex);
                        saveInitialMotion(x, y, pointerId3);
                        if (this.mDragState == 0) {
                            tryCaptureViewForDrag(findTopChildUnder((int) x, (int) y), pointerId3);
                            if ((this.mTrackingEdges & this.mInitialEdgesTouched[pointerId3]) != 0) {
                                callback.onEdgeTouched();
                                return;
                            }
                            return;
                        }
                        if (isViewUnder(this.mCapturedView, (int) x, (int) y)) {
                            tryCaptureViewForDrag(this.mCapturedView, pointerId3);
                            return;
                        }
                        return;
                    }
                    if (this.mDragState == 1) {
                        this.mReleaseInProgress = true;
                        callback.onViewReleased(this.mCapturedView, 0.0f, 0.0f);
                        this.mReleaseInProgress = false;
                        if (this.mDragState == 1) {
                            setDragState(0);
                        }
                    }
                    cancel();
                    return;
                }
                if (this.mDragState == 1) {
                    if (isValidPointerForActionMove(this.mActivePointerId)) {
                        int findPointerIndex = motionEvent.findPointerIndex(this.mActivePointerId);
                        float x2 = motionEvent.getX(findPointerIndex);
                        float y2 = motionEvent.getY(findPointerIndex);
                        float[] fArr = this.mLastMotionX;
                        int i3 = this.mActivePointerId;
                        int i4 = (int) (x2 - fArr[i3]);
                        int i5 = (int) (y2 - this.mLastMotionY[i3]);
                        int left = this.mCapturedView.getLeft() + i4;
                        int top = this.mCapturedView.getTop() + i5;
                        int left2 = this.mCapturedView.getLeft();
                        int top2 = this.mCapturedView.getTop();
                        if (i4 != 0) {
                            left = callback.clampViewPositionHorizontal(this.mCapturedView, left);
                            if (this.mIsUpdateOffsetLR || this.mDragState != 2) {
                                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                                this.mCapturedView.offsetLeftAndRight(left - left2);
                            }
                        }
                        if (i5 != 0) {
                            top = callback.clampViewPositionVertical(this.mCapturedView, top);
                            WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                            this.mCapturedView.offsetTopAndBottom(top - top2);
                        }
                        if (i4 != 0 || i5 != 0) {
                            callback.onViewPositionChanged(this.mCapturedView, left, top, left - left2);
                        }
                        saveLastMotion(motionEvent);
                        return;
                    }
                    return;
                }
                int pointerCount2 = motionEvent.getPointerCount();
                while (i2 < pointerCount2) {
                    int pointerId4 = motionEvent.getPointerId(i2);
                    if (isValidPointerForActionMove(pointerId4)) {
                        float x3 = motionEvent.getX(i2);
                        float y3 = motionEvent.getY(i2);
                        float f = x3 - this.mInitialMotionX[pointerId4];
                        float f2 = y3 - this.mInitialMotionY[pointerId4];
                        reportNewEdgeDrags(f, f2, pointerId4);
                        if (this.mDragState != 1) {
                            View findTopChildUnder2 = findTopChildUnder((int) x3, (int) y3);
                            if (checkTouchSlop(findTopChildUnder2, f, f2) && tryCaptureViewForDrag(findTopChildUnder2, pointerId4)) {
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                    i2++;
                }
                saveLastMotion(motionEvent);
                return;
            }
            if (this.mDragState == 1) {
                releaseViewForPointerUp();
            }
            cancel();
            return;
        }
        float x4 = motionEvent.getX();
        float y4 = motionEvent.getY();
        int pointerId5 = motionEvent.getPointerId(0);
        View findTopChildUnder3 = findTopChildUnder((int) x4, (int) y4);
        saveInitialMotion(x4, y4, pointerId5);
        tryCaptureViewForDrag(findTopChildUnder3, pointerId5);
        if ((this.mTrackingEdges & this.mInitialEdgesTouched[pointerId5]) != 0) {
            callback.onEdgeTouched();
        }
    }

    public final void releaseViewForPointerUp() {
        VelocityTracker velocityTracker = this.mVelocityTracker;
        float f = this.mMaxVelocity;
        velocityTracker.computeCurrentVelocity(1000, f);
        float xVelocity = this.mVelocityTracker.getXVelocity(this.mActivePointerId);
        float f2 = this.mMinVelocity;
        float abs = Math.abs(xVelocity);
        float f3 = 0.0f;
        if (abs < f2) {
            xVelocity = 0.0f;
        } else if (abs > f) {
            if (xVelocity > 0.0f) {
                xVelocity = f;
            } else {
                xVelocity = -f;
            }
        }
        float yVelocity = this.mVelocityTracker.getYVelocity(this.mActivePointerId);
        float f4 = this.mMinVelocity;
        float abs2 = Math.abs(yVelocity);
        if (abs2 >= f4) {
            if (abs2 > f) {
                if (yVelocity <= 0.0f) {
                    f = -f;
                }
                f3 = f;
            } else {
                f3 = yVelocity;
            }
        }
        this.mReleaseInProgress = true;
        this.mCallback.onViewReleased(this.mCapturedView, xVelocity, f3);
        this.mReleaseInProgress = false;
        if (this.mDragState == 1) {
            setDragState(0);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v15 */
    /* JADX WARN: Type inference failed for: r0v16 */
    /* JADX WARN: Type inference failed for: r0v4, types: [int] */
    /* JADX WARN: Type inference failed for: r0v6 */
    /* JADX WARN: Type inference failed for: r0v7 */
    /* JADX WARN: Type inference failed for: r2v1, types: [androidx.customview.widget.ViewDragHelper$Callback] */
    public final void reportNewEdgeDrags(float f, float f2, int i) {
        boolean checkNewEdgeDrag = checkNewEdgeDrag(i, f, f2, 1);
        boolean z = checkNewEdgeDrag;
        if (checkNewEdgeDrag(i, f2, f, 4)) {
            z = (checkNewEdgeDrag ? 1 : 0) | 4;
        }
        boolean z2 = z;
        if (checkNewEdgeDrag(i, f, f2, 2)) {
            z2 = (z ? 1 : 0) | 2;
        }
        ?? r0 = z2;
        if (checkNewEdgeDrag(i, f2, f, 8)) {
            r0 = (z2 ? 1 : 0) | 8;
        }
        if (r0 != 0) {
            int[] iArr = this.mEdgeDragsInProgress;
            iArr[i] = iArr[i] | r0;
            this.mCallback.onEdgeDragStarted(r0, i);
        }
    }

    public final void saveInitialMotion(float f, float f2, int i) {
        float[] fArr = this.mInitialMotionX;
        int i2 = 0;
        if (fArr == null || fArr.length <= i) {
            int i3 = i + 1;
            float[] fArr2 = new float[i3];
            float[] fArr3 = new float[i3];
            float[] fArr4 = new float[i3];
            float[] fArr5 = new float[i3];
            int[] iArr = new int[i3];
            int[] iArr2 = new int[i3];
            int[] iArr3 = new int[i3];
            if (fArr != null) {
                System.arraycopy(fArr, 0, fArr2, 0, fArr.length);
                float[] fArr6 = this.mInitialMotionY;
                System.arraycopy(fArr6, 0, fArr3, 0, fArr6.length);
                float[] fArr7 = this.mLastMotionX;
                System.arraycopy(fArr7, 0, fArr4, 0, fArr7.length);
                float[] fArr8 = this.mLastMotionY;
                System.arraycopy(fArr8, 0, fArr5, 0, fArr8.length);
                int[] iArr4 = this.mInitialEdgesTouched;
                System.arraycopy(iArr4, 0, iArr, 0, iArr4.length);
                int[] iArr5 = this.mEdgeDragsInProgress;
                System.arraycopy(iArr5, 0, iArr2, 0, iArr5.length);
                int[] iArr6 = this.mEdgeDragsLocked;
                System.arraycopy(iArr6, 0, iArr3, 0, iArr6.length);
            }
            this.mInitialMotionX = fArr2;
            this.mInitialMotionY = fArr3;
            this.mLastMotionX = fArr4;
            this.mLastMotionY = fArr5;
            this.mInitialEdgesTouched = iArr;
            this.mEdgeDragsInProgress = iArr2;
            this.mEdgeDragsLocked = iArr3;
        }
        float[] fArr9 = this.mInitialMotionX;
        this.mLastMotionX[i] = f;
        fArr9[i] = f;
        float[] fArr10 = this.mInitialMotionY;
        this.mLastMotionY[i] = f2;
        fArr10[i] = f2;
        int[] iArr7 = this.mInitialEdgesTouched;
        int i4 = (int) f;
        int i5 = (int) f2;
        ViewGroup viewGroup = this.mParentView;
        if (i4 < viewGroup.getLeft() + this.mEdgeSize) {
            i2 = 1;
        }
        if (i5 < viewGroup.getTop() + this.mEdgeSize) {
            i2 |= 4;
        }
        if (i4 > viewGroup.getRight() - this.mEdgeSize) {
            i2 |= 2;
        }
        if (i5 > viewGroup.getBottom() - this.mEdgeSize) {
            i2 |= 8;
        }
        iArr7[i] = i2;
        this.mPointersDown |= 1 << i;
    }

    public final void saveLastMotion(MotionEvent motionEvent) {
        int pointerCount = motionEvent.getPointerCount();
        for (int i = 0; i < pointerCount; i++) {
            int pointerId = motionEvent.getPointerId(i);
            if (isValidPointerForActionMove(pointerId)) {
                float x = motionEvent.getX(i);
                float y = motionEvent.getY(i);
                this.mLastMotionX[pointerId] = x;
                this.mLastMotionY[pointerId] = y;
            }
        }
    }

    public final void setDragState(int i) {
        this.mParentView.removeCallbacks(this.mSetIdleRunnable);
        if (this.mDragState != i) {
            this.mDragState = i;
            this.mCallback.onViewDragStateChanged(i);
            if (this.mDragState == 0) {
                this.mCapturedView = null;
            }
        }
    }

    public final boolean settleCapturedViewAt(int i, int i2) {
        if (this.mReleaseInProgress) {
            return forceSettleCapturedViewAt(i, i2, (int) this.mVelocityTracker.getXVelocity(this.mActivePointerId), (int) this.mVelocityTracker.getYVelocity(this.mActivePointerId));
        }
        throw new IllegalStateException("Cannot settleCapturedViewAt outside of a call to Callback#onViewReleased");
    }

    /* JADX WARN: Code restructure failed: missing block: B:50:0x00d4, code lost:
    
        if (r13 != r12) goto L54;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean shouldInterceptTouchEvent(android.view.MotionEvent r19) {
        /*
            Method dump skipped, instructions count: 300
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.customview.widget.ViewDragHelper.shouldInterceptTouchEvent(android.view.MotionEvent):boolean");
    }

    public final boolean smoothSlideViewTo(View view, int i, int i2) {
        this.mCapturedView = view;
        this.mActivePointerId = -1;
        boolean forceSettleCapturedViewAt = forceSettleCapturedViewAt(i, i2, 0, 0);
        if (!forceSettleCapturedViewAt && this.mDragState == 0 && this.mCapturedView != null) {
            this.mCapturedView = null;
        }
        return forceSettleCapturedViewAt;
    }

    public final boolean tryCaptureViewForDrag(View view, int i) {
        if (view == this.mCapturedView && this.mActivePointerId == i) {
            return true;
        }
        if (view != null && this.mCallback.tryCaptureView(view, i)) {
            this.mActivePointerId = i;
            captureChildView(view, i);
            return true;
        }
        return false;
    }

    public static ViewDragHelper create(ViewGroup viewGroup, DrawerLayout.ViewDragCallback viewDragCallback) {
        ViewDragHelper viewDragHelper = new ViewDragHelper(viewGroup.getContext(), viewGroup, viewDragCallback);
        viewDragHelper.mTouchSlop = (int) (1.0f * viewDragHelper.mTouchSlop);
        return viewDragHelper;
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public abstract class Callback {
        public int clampViewPositionHorizontal(View view, int i) {
            return 0;
        }

        public int clampViewPositionVertical(View view, int i) {
            return 0;
        }

        public int getViewHorizontalDragRange(View view) {
            return 0;
        }

        public int getViewVerticalDragRange() {
            return 0;
        }

        public abstract boolean tryCaptureView(View view, int i);

        public void onViewDragStateChanged(int i) {
        }

        public void onEdgeTouched() {
        }

        public void onEdgeDragStarted(int i, int i2) {
        }

        public void onViewCaptured(View view, int i) {
        }

        public void onViewReleased(View view, float f, float f2) {
        }

        public void onViewPositionChanged(View view, int i, int i2, int i3) {
        }
    }
}
