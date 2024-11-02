package com.android.systemui.animation;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Insets;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.Log;
import android.view.GhostView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.animation.LaunchAnimator;
import java.util.LinkedList;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt__MathJVMKt;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class GhostedViewLaunchAnimatorController implements ActivityLaunchAnimator.Controller {
    public static final Companion Companion = new Companion(null);
    public final Drawable background;
    public WrappedDrawable backgroundDrawable;
    public final Lazy backgroundInsets$delegate;
    public FrameLayout backgroundView;
    public final Integer cujType;
    public GhostView ghostView;
    public final Matrix ghostViewMatrix;
    public final View ghostedView;
    public final int[] ghostedViewLocation;
    public final LaunchAnimator.State ghostedViewState;
    public final float[] initialGhostViewMatrixValues;
    public final InteractionJankMonitor interactionJankMonitor;
    public ViewGroup launchContainer;
    public final int[] launchContainerLocation;
    public int startBackgroundAlpha;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static GradientDrawable findGradientDrawable(Drawable drawable) {
            if (drawable instanceof GradientDrawable) {
                return (GradientDrawable) drawable;
            }
            if (drawable instanceof InsetDrawable) {
                Drawable drawable2 = ((InsetDrawable) drawable).getDrawable();
                if (drawable2 == null) {
                    return null;
                }
                GhostedViewLaunchAnimatorController.Companion.getClass();
                return findGradientDrawable(drawable2);
            }
            if (drawable instanceof LayerDrawable) {
                LayerDrawable layerDrawable = (LayerDrawable) drawable;
                int numberOfLayers = layerDrawable.getNumberOfLayers();
                for (int i = 0; i < numberOfLayers; i++) {
                    Drawable drawable3 = layerDrawable.getDrawable(i);
                    if (drawable3 instanceof GradientDrawable) {
                        return (GradientDrawable) drawable3;
                    }
                }
            }
            if (!(drawable instanceof StateListDrawable)) {
                return null;
            }
            return findGradientDrawable(((StateListDrawable) drawable).getCurrent());
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class WrappedDrawable extends Drawable {
        public final float[] cornerRadii;
        public int currentAlpha = 255;
        public final Rect previousBounds = new Rect();
        public final float[] previousCornerRadii;
        public final Drawable wrapped;

        public WrappedDrawable(Drawable drawable) {
            this.wrapped = drawable;
            float[] fArr = new float[8];
            for (int i = 0; i < 8; i++) {
                fArr[i] = -1.0f;
            }
            this.cornerRadii = fArr;
            this.previousCornerRadii = new float[8];
        }

        public static void applyBackgroundRadii(Drawable drawable, float[] fArr) {
            GradientDrawable gradientDrawable;
            if (drawable instanceof GradientDrawable) {
                ((GradientDrawable) drawable).setCornerRadii(fArr);
                return;
            }
            if (drawable instanceof InsetDrawable) {
                Drawable drawable2 = ((InsetDrawable) drawable).getDrawable();
                if (drawable2 != null) {
                    applyBackgroundRadii(drawable2, fArr);
                    return;
                }
                return;
            }
            if (!(drawable instanceof LayerDrawable)) {
                return;
            }
            LayerDrawable layerDrawable = (LayerDrawable) drawable;
            int numberOfLayers = layerDrawable.getNumberOfLayers();
            for (int i = 0; i < numberOfLayers; i++) {
                Drawable drawable3 = layerDrawable.getDrawable(i);
                if (drawable3 instanceof GradientDrawable) {
                    gradientDrawable = (GradientDrawable) drawable3;
                } else {
                    gradientDrawable = null;
                }
                if (gradientDrawable != null) {
                    gradientDrawable.setCornerRadii(fArr);
                }
            }
        }

        @Override // android.graphics.drawable.Drawable
        public final void draw(Canvas canvas) {
            Drawable drawable;
            Drawable drawable2;
            Drawable drawable3 = this.wrapped;
            if (drawable3 == null) {
                return;
            }
            drawable3.copyBounds(this.previousBounds);
            drawable3.setAlpha(this.currentAlpha);
            drawable3.setBounds(getBounds());
            if (this.cornerRadii[0] >= 0.0f && (drawable2 = this.wrapped) != null) {
                GhostedViewLaunchAnimatorController.Companion.getClass();
                GradientDrawable findGradientDrawable = Companion.findGradientDrawable(drawable2);
                if (findGradientDrawable != null) {
                    float[] cornerRadii = findGradientDrawable.getCornerRadii();
                    if (cornerRadii != null) {
                        System.arraycopy(cornerRadii, 0, this.previousCornerRadii, 0, cornerRadii.length - 0);
                    } else {
                        float cornerRadius = findGradientDrawable.getCornerRadius();
                        float[] fArr = this.previousCornerRadii;
                        fArr[0] = cornerRadius;
                        fArr[1] = cornerRadius;
                        fArr[2] = cornerRadius;
                        fArr[3] = cornerRadius;
                        fArr[4] = cornerRadius;
                        fArr[5] = cornerRadius;
                        fArr[6] = cornerRadius;
                        fArr[7] = cornerRadius;
                    }
                }
                applyBackgroundRadii(this.wrapped, this.cornerRadii);
            }
            drawable3.draw(canvas);
            drawable3.setAlpha(0);
            drawable3.setBounds(this.previousBounds);
            if (this.cornerRadii[0] >= 0.0f && (drawable = this.wrapped) != null) {
                applyBackgroundRadii(drawable, this.previousCornerRadii);
            }
        }

        @Override // android.graphics.drawable.Drawable
        public final int getAlpha() {
            return this.currentAlpha;
        }

        @Override // android.graphics.drawable.Drawable
        public final int getOpacity() {
            Drawable drawable = this.wrapped;
            if (drawable == null) {
                return -2;
            }
            int alpha = drawable.getAlpha();
            drawable.setAlpha(this.currentAlpha);
            int opacity = drawable.getOpacity();
            drawable.setAlpha(alpha);
            return opacity;
        }

        @Override // android.graphics.drawable.Drawable
        public final void setAlpha(int i) {
            if (i != this.currentAlpha) {
                this.currentAlpha = i;
                invalidateSelf();
            }
        }

        @Override // android.graphics.drawable.Drawable
        public final void setColorFilter(ColorFilter colorFilter) {
            Drawable drawable = this.wrapped;
            if (drawable != null) {
                drawable.setColorFilter(colorFilter);
            }
        }
    }

    public GhostedViewLaunchAnimatorController(View view) {
        this(view, null, null, 6, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0031  */
    @Override // com.android.systemui.animation.LaunchAnimator.Controller
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.android.systemui.animation.LaunchAnimator.State createAnimatorState() {
        /*
            r11 = this;
            com.android.systemui.animation.LaunchAnimator$State r9 = new com.android.systemui.animation.LaunchAnimator$State
            r1 = 0
            r2 = 0
            r3 = 0
            r4 = 0
            android.view.View r0 = r11.ghostedView
            com.android.systemui.animation.GhostedViewLaunchAnimatorController$Companion r5 = com.android.systemui.animation.GhostedViewLaunchAnimatorController.Companion
            android.graphics.drawable.Drawable r6 = r11.background
            r7 = 0
            if (r6 != 0) goto L10
            goto L19
        L10:
            r5.getClass()
            android.graphics.drawable.GradientDrawable r8 = com.android.systemui.animation.GhostedViewLaunchAnimatorController.Companion.findGradientDrawable(r6)
            if (r8 != 0) goto L1b
        L19:
            r10 = r7
            goto L2e
        L1b:
            float[] r10 = r8.getCornerRadii()
            if (r10 == 0) goto L25
            r8 = 0
            r8 = r10[r8]
            goto L29
        L25:
            float r8 = r8.getCornerRadius()
        L29:
            float r10 = r0.getScaleX()
            float r10 = r10 * r8
        L2e:
            if (r6 != 0) goto L31
            goto L3a
        L31:
            r5.getClass()
            android.graphics.drawable.GradientDrawable r5 = com.android.systemui.animation.GhostedViewLaunchAnimatorController.Companion.findGradientDrawable(r6)
            if (r5 != 0) goto L3c
        L3a:
            r6 = r7
            goto L50
        L3c:
            float[] r6 = r5.getCornerRadii()
            if (r6 == 0) goto L46
            r5 = 4
            r5 = r6[r5]
            goto L4a
        L46:
            float r5 = r5.getCornerRadius()
        L4a:
            float r0 = r0.getScaleX()
            float r0 = r0 * r5
            r6 = r0
        L50:
            r7 = 15
            r8 = 0
            r0 = r9
            r5 = r10
            r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8)
            r11.fillGhostedViewState(r9)
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.animation.GhostedViewLaunchAnimatorController.createAnimatorState():com.android.systemui.animation.LaunchAnimator$State");
    }

    public final void fillGhostedViewState(LaunchAnimator.State state) {
        View view = this.ghostedView;
        int[] iArr = this.ghostedViewLocation;
        view.getLocationOnScreen(iArr);
        Insets insets = (Insets) this.backgroundInsets$delegate.getValue();
        int i = iArr[1];
        state.top = insets.top + i;
        state.bottom = (MathKt__MathJVMKt.roundToInt(view.getScaleY() * view.getHeight()) + i) - insets.bottom;
        int i2 = iArr[0];
        state.left = insets.left + i2;
        state.right = (MathKt__MathJVMKt.roundToInt(view.getScaleX() * view.getWidth()) + i2) - insets.right;
    }

    @Override // com.android.systemui.animation.LaunchAnimator.Controller
    public final ViewGroup getLaunchContainer() {
        return this.launchContainer;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.systemui.animation.LaunchAnimator.Controller
    public final void onLaunchAnimationEnd(boolean z) {
        Drawable drawable;
        if (this.ghostView == null) {
            return;
        }
        Integer num = this.cujType;
        if (num != null) {
            this.interactionJankMonitor.end(num.intValue());
        }
        WrappedDrawable wrappedDrawable = this.backgroundDrawable;
        if (wrappedDrawable != null) {
            drawable = wrappedDrawable.wrapped;
        } else {
            drawable = null;
        }
        if (drawable != null) {
            drawable.setAlpha(this.startBackgroundAlpha);
        }
        View view = this.ghostedView;
        GhostView.removeGhost(view);
        this.launchContainer.getOverlay().remove(this.backgroundView);
        if (view instanceof LaunchableView) {
            ((LaunchableView) view).setShouldBlockVisibilityChanges(false);
            return;
        }
        view.setVisibility(4);
        view.setVisibility(0);
        view.invalidate();
    }

    @Override // com.android.systemui.animation.LaunchAnimator.Controller
    public final void onLaunchAnimationProgress(LaunchAnimator.State state, float f, float f2) {
        GhostView ghostView = this.ghostView;
        if (ghostView == null) {
            return;
        }
        FrameLayout frameLayout = this.backgroundView;
        Intrinsics.checkNotNull(frameLayout);
        boolean z = state.visible;
        View view = this.ghostedView;
        if (z && view.isAttachedToWindow()) {
            if (ghostView.getVisibility() == 4) {
                ghostView.setVisibility(0);
                frameLayout.setVisibility(0);
            }
            LaunchAnimator.State state2 = this.ghostedViewState;
            fillGhostedViewState(state2);
            int i = state.left - state2.left;
            int i2 = state.right - state2.right;
            int i3 = state.top - state2.top;
            int i4 = state.bottom - state2.bottom;
            float min = Math.min((r11 - r8) / (r12 - r9), (r5 - r14) / (r7 - r15));
            boolean z2 = view.getParent() instanceof ViewGroup;
            Matrix matrix = this.ghostViewMatrix;
            if (z2) {
                GhostView.calculateMatrix(view, this.launchContainer, matrix);
            }
            ViewGroup viewGroup = this.launchContainer;
            int[] iArr = this.launchContainerLocation;
            viewGroup.getLocationOnScreen(iArr);
            matrix.postScale(min, min, (((state2.right - r6) / 2.0f) + state2.left) - iArr[0], (((state2.bottom - r9) / 2.0f) + state2.top) - iArr[1]);
            matrix.postTranslate((i + i2) / 2.0f, (i3 + i4) / 2.0f);
            ghostView.setAnimationMatrix(matrix);
            Insets insets = (Insets) this.backgroundInsets$delegate.getValue();
            int i5 = state.top - insets.top;
            int i6 = state.left - insets.left;
            int i7 = state.right + insets.right;
            int i8 = state.bottom + insets.bottom;
            frameLayout.setTop(i5 - iArr[1]);
            frameLayout.setBottom(i8 - iArr[1]);
            frameLayout.setLeft(i6 - iArr[0]);
            frameLayout.setRight(i7 - iArr[0]);
            WrappedDrawable wrappedDrawable = this.backgroundDrawable;
            Intrinsics.checkNotNull(wrappedDrawable);
            if (wrappedDrawable.wrapped != null) {
                float f3 = state.topCornerRadius;
                float f4 = state.bottomCornerRadius;
                WrappedDrawable wrappedDrawable2 = this.backgroundDrawable;
                if (wrappedDrawable2 != null) {
                    float[] fArr = wrappedDrawable2.cornerRadii;
                    fArr[0] = f3;
                    fArr[1] = f3;
                    fArr[2] = f3;
                    fArr[3] = f3;
                    fArr[4] = f4;
                    fArr[5] = f4;
                    fArr[6] = f4;
                    fArr[7] = f4;
                    wrappedDrawable2.invalidateSelf();
                    return;
                }
                return;
            }
            return;
        }
        if (ghostView.getVisibility() == 0) {
            ghostView.setVisibility(4);
            view.setTransitionVisibility(4);
            frameLayout.setVisibility(4);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.systemui.animation.LaunchAnimator.Controller
    public final void onLaunchAnimationStart(boolean z) {
        int i;
        LaunchableView launchableView;
        Matrix matrix;
        View view = this.ghostedView;
        if (!(view.getParent() instanceof ViewGroup)) {
            Log.w("GhostedViewLaunchAnimatorController", "Skipping animation as ghostedView is not attached to a ViewGroup");
            return;
        }
        this.backgroundView = new FrameLayout(this.launchContainer.getContext());
        this.launchContainer.getOverlay().add(this.backgroundView);
        Drawable drawable = this.background;
        if (drawable != null) {
            i = drawable.getAlpha();
        } else {
            i = 255;
        }
        this.startBackgroundAlpha = i;
        WrappedDrawable wrappedDrawable = new WrappedDrawable(drawable);
        this.backgroundDrawable = wrappedDrawable;
        FrameLayout frameLayout = this.backgroundView;
        if (frameLayout != null) {
            frameLayout.setBackground(wrappedDrawable);
        }
        if (view instanceof LaunchableView) {
            launchableView = (LaunchableView) view;
        } else {
            launchableView = null;
        }
        if (launchableView != null) {
            launchableView.setShouldBlockVisibilityChanges(true);
        }
        GhostView addGhost = GhostView.addGhost(view, this.launchContainer);
        this.ghostView = addGhost;
        if (addGhost == null || (matrix = addGhost.getAnimationMatrix()) == null) {
            matrix = Matrix.IDENTITY_MATRIX;
        }
        matrix.getValues(this.initialGhostViewMatrixValues);
        Integer num = this.cujType;
        if (num != null) {
            this.interactionJankMonitor.begin(view, num.intValue());
        }
    }

    @Override // com.android.systemui.animation.LaunchAnimator.Controller
    public final void setLaunchContainer(ViewGroup viewGroup) {
        this.launchContainer = viewGroup;
    }

    public GhostedViewLaunchAnimatorController(View view, Integer num) {
        this(view, num, null, 4, null);
    }

    public GhostedViewLaunchAnimatorController(View view, Integer num, InteractionJankMonitor interactionJankMonitor) {
        Drawable drawable;
        this.ghostedView = view;
        this.cujType = num;
        this.interactionJankMonitor = interactionJankMonitor;
        this.launchContainer = (ViewGroup) view.getRootView();
        this.launchContainerLocation = new int[2];
        float[] fArr = new float[9];
        for (int i = 0; i < 9; i++) {
            fArr[i] = 0.0f;
        }
        this.initialGhostViewMatrixValues = fArr;
        this.ghostViewMatrix = new Matrix();
        this.backgroundInsets$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.animation.GhostedViewLaunchAnimatorController$backgroundInsets$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Insets opticalInsets;
                Drawable drawable2 = GhostedViewLaunchAnimatorController.this.background;
                if (drawable2 == null || (opticalInsets = drawable2.getOpticalInsets()) == null) {
                    return Insets.NONE;
                }
                return opticalInsets;
            }
        });
        this.startBackgroundAlpha = 255;
        this.ghostedViewLocation = new int[2];
        this.ghostedViewState = new LaunchAnimator.State(0, 0, 0, 0, 0.0f, 0.0f, 63, null);
        View view2 = this.ghostedView;
        if (view2 instanceof LaunchableView) {
            if (view2.getBackground() != null) {
                drawable = view2.getBackground();
            } else {
                LinkedList linkedList = new LinkedList();
                linkedList.add(view2);
                while (true) {
                    if (!(!linkedList.isEmpty())) {
                        drawable = null;
                        break;
                    }
                    View view3 = (View) linkedList.removeFirst();
                    if (view3.getBackground() != null) {
                        drawable = view3.getBackground();
                        break;
                    } else if (view3 instanceof ViewGroup) {
                        ViewGroup viewGroup = (ViewGroup) view3;
                        int childCount = viewGroup.getChildCount();
                        for (int i2 = 0; i2 < childCount; i2++) {
                            linkedList.add(viewGroup.getChildAt(i2));
                        }
                    }
                }
            }
            this.background = drawable;
            return;
        }
        throw new IllegalArgumentException("A GhostedViewLaunchAnimatorController was created from a View that does not implement LaunchableView. This can lead to subtle bugs where the visibility of the View we are launching from is not what we expected.");
    }

    public /* synthetic */ GhostedViewLaunchAnimatorController(View view, Integer num, InteractionJankMonitor interactionJankMonitor, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(view, (i & 2) != 0 ? null : num, (i & 4) != 0 ? InteractionJankMonitor.getInstance() : interactionJankMonitor);
    }
}
