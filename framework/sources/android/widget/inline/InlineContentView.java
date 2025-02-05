package android.widget.inline;

import android.content.Context;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.SurfaceControl;
import android.view.SurfaceControlViewHost;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import java.lang.ref.WeakReference;
import java.util.function.Consumer;

/* loaded from: classes4.dex */
public class InlineContentView extends ViewGroup {
    private static final boolean DEBUG = false;
    private static final String TAG = "InlineContentView";
    private final ViewTreeObserver.OnDrawListener mOnDrawListener;
    private final SurfaceControl.OnReparentListener mOnReparentListener;
    private int[] mParentPosition;
    private PointF mParentScale;
    private WeakReference<SurfaceView> mParentSurfaceOwnerView;
    private final SurfaceHolder.Callback mSurfaceCallback;
    private SurfaceControlCallback mSurfaceControlCallback;
    private SurfacePackageUpdater mSurfacePackageUpdater;
    private final SurfaceView mSurfaceView;

    public interface SurfaceControlCallback {
        void onCreated(SurfaceControl surfaceControl);

        void onDestroyed(SurfaceControl surfaceControl);
    }

    public interface SurfacePackageUpdater {
        void getSurfacePackage(Consumer<SurfaceControlViewHost.SurfacePackage> consumer);

        void onSurfacePackageReleased();
    }

    public InlineContentView(Context context) {
        this(context, null);
    }

    public InlineContentView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InlineContentView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
        this.mSurfaceView.setEnableSurfaceClipping(true);
    }

    public SurfaceControl getSurfaceControl() {
        return this.mSurfaceView.getSurfaceControl();
    }

    @Override // android.view.View
    public void setClipBounds(Rect clipBounds) {
        super.setClipBounds(clipBounds);
        this.mSurfaceView.setClipBounds(clipBounds);
    }

    public InlineContentView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mSurfaceCallback = new SurfaceHolder.Callback() { // from class: android.widget.inline.InlineContentView.1
            @Override // android.view.SurfaceHolder.Callback
            public void surfaceCreated(SurfaceHolder holder) {
                SurfaceControl surfaceControl = InlineContentView.this.mSurfaceView.getSurfaceControl();
                surfaceControl.addOnReparentListener(InlineContentView.this.mOnReparentListener);
                InlineContentView.this.mSurfaceControlCallback.onCreated(surfaceControl);
            }

            @Override // android.view.SurfaceHolder.Callback
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override // android.view.SurfaceHolder.Callback
            public void surfaceDestroyed(SurfaceHolder holder) {
                SurfaceControl surfaceControl = InlineContentView.this.mSurfaceView.getSurfaceControl();
                surfaceControl.removeOnReparentListener(InlineContentView.this.mOnReparentListener);
                InlineContentView.this.mSurfaceControlCallback.onDestroyed(surfaceControl);
            }
        };
        this.mOnReparentListener = new SurfaceControl.OnReparentListener() { // from class: android.widget.inline.InlineContentView.2
            @Override // android.view.SurfaceControl.OnReparentListener
            public void onReparent(SurfaceControl.Transaction transaction, SurfaceControl parent) {
                View parentSurfaceOwnerView;
                if (parent == null) {
                    parentSurfaceOwnerView = null;
                } else {
                    parentSurfaceOwnerView = parent.getLocalOwnerView();
                }
                if (!(parentSurfaceOwnerView instanceof SurfaceView)) {
                    InlineContentView.this.mParentSurfaceOwnerView = null;
                } else {
                    InlineContentView.this.mParentSurfaceOwnerView = new WeakReference((SurfaceView) parentSurfaceOwnerView);
                }
            }
        };
        this.mOnDrawListener = new ViewTreeObserver.OnDrawListener() { // from class: android.widget.inline.InlineContentView.3
            @Override // android.view.ViewTreeObserver.OnDrawListener
            public void onDraw() {
                InlineContentView.this.computeParentPositionAndScale();
                int visibility = InlineContentView.this.isShown() ? 0 : 8;
                InlineContentView.this.mSurfaceView.setVisibility(visibility);
            }
        };
        this.mSurfaceView = new SurfaceView(context, attrs, defStyleAttr, defStyleRes) { // from class: android.widget.inline.InlineContentView.4
            @Override // android.view.SurfaceView
            protected void onSetSurfacePositionAndScale(SurfaceControl.Transaction transaction, SurfaceControl surface, int positionLeft, int positionTop, float postScaleX, float postScaleY) {
                if (InlineContentView.this.mParentPosition != null) {
                    positionLeft = (int) ((positionLeft - InlineContentView.this.mParentPosition[0]) / InlineContentView.this.mParentScale.x);
                    positionTop = (int) ((positionTop - InlineContentView.this.mParentPosition[1]) / InlineContentView.this.mParentScale.y);
                }
                float postScaleX2 = InlineContentView.this.getScaleX();
                float postScaleY2 = InlineContentView.this.getScaleY();
                super.onSetSurfacePositionAndScale(transaction, surface, positionLeft, positionTop, postScaleX2, postScaleY2);
            }
        };
        this.mSurfaceView.setZOrderOnTop(true);
        this.mSurfaceView.getHolder().setFormat(-2);
        addView(this.mSurfaceView);
        setImportantForAccessibility(2);
    }

    public void setChildSurfacePackageUpdater(SurfacePackageUpdater surfacePackageUpdater) {
        this.mSurfacePackageUpdater = surfacePackageUpdater;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mSurfacePackageUpdater != null) {
            this.mSurfacePackageUpdater.getSurfacePackage(new Consumer() { // from class: android.widget.inline.InlineContentView$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    InlineContentView.this.lambda$onAttachedToWindow$0((SurfaceControlViewHost.SurfacePackage) obj);
                }
            });
        }
        this.mSurfaceView.setVisibility(getVisibility());
        getViewTreeObserver().addOnDrawListener(this.mOnDrawListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onAttachedToWindow$0(SurfaceControlViewHost.SurfacePackage sp) {
        if (getViewRootImpl() != null) {
            this.mSurfaceView.setChildSurfacePackage(sp);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mSurfacePackageUpdater != null) {
            this.mSurfacePackageUpdater.onSurfacePackageReleased();
        }
        getViewTreeObserver().removeOnDrawListener(this.mOnDrawListener);
        this.mSurfaceView.setVisibility(8);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onLayout(boolean changed, int l, int t, int r, int b) {
        this.mSurfaceView.layout(0, 0, getMeasuredWidth(), getMeasuredHeight());
    }

    public void setSurfaceControlCallback(SurfaceControlCallback callback) {
        if (this.mSurfaceControlCallback != null) {
            this.mSurfaceView.getHolder().removeCallback(this.mSurfaceCallback);
        }
        this.mSurfaceControlCallback = callback;
        if (this.mSurfaceControlCallback != null) {
            this.mSurfaceView.getHolder().addCallback(this.mSurfaceCallback);
        }
    }

    public boolean isZOrderedOnTop() {
        return this.mSurfaceView.isZOrderedOnTop();
    }

    public boolean setZOrderedOnTop(boolean onTop) {
        return this.mSurfaceView.setZOrderedOnTop(onTop, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void computeParentPositionAndScale() {
        boolean contentPositionOrScaleChanged = false;
        SurfaceView parentSurfaceOwnerView = this.mParentSurfaceOwnerView != null ? this.mParentSurfaceOwnerView.get() : null;
        if (parentSurfaceOwnerView != null) {
            if (this.mParentPosition == null) {
                this.mParentPosition = new int[2];
            }
            int oldParentPositionX = this.mParentPosition[0];
            int oldParentPositionY = this.mParentPosition[1];
            parentSurfaceOwnerView.getLocationInSurface(this.mParentPosition);
            if (oldParentPositionX != this.mParentPosition[0] || oldParentPositionY != this.mParentPosition[1]) {
                contentPositionOrScaleChanged = true;
            }
            if (this.mParentScale == null) {
                this.mParentScale = new PointF();
            }
            float lastParentSurfaceWidth = parentSurfaceOwnerView.getSurfaceRenderPosition().width();
            float oldParentScaleX = this.mParentScale.x;
            if (lastParentSurfaceWidth > 0.0f) {
                this.mParentScale.x = lastParentSurfaceWidth / parentSurfaceOwnerView.getWidth();
            } else {
                this.mParentScale.x = 1.0f;
            }
            if (!contentPositionOrScaleChanged && Float.compare(oldParentScaleX, this.mParentScale.x) != 0) {
                contentPositionOrScaleChanged = true;
            }
            float lastParentSurfaceHeight = parentSurfaceOwnerView.getSurfaceRenderPosition().height();
            float oldParentScaleY = this.mParentScale.y;
            if (lastParentSurfaceHeight > 0.0f) {
                this.mParentScale.y = lastParentSurfaceHeight / parentSurfaceOwnerView.getHeight();
            } else {
                this.mParentScale.y = 1.0f;
            }
            if (!contentPositionOrScaleChanged && Float.compare(oldParentScaleY, this.mParentScale.y) != 0) {
                contentPositionOrScaleChanged = true;
            }
        } else if (this.mParentPosition != null || this.mParentScale != null) {
            contentPositionOrScaleChanged = true;
            this.mParentPosition = null;
            this.mParentScale = null;
        }
        if (contentPositionOrScaleChanged) {
            this.mSurfaceView.requestUpdateSurfacePositionAndScale();
        }
    }
}
