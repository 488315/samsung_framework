package com.android.wm.shell.fullscreen;

import android.R;
import android.animation.Keyframe;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.hardware.display.DisplayManager;
import android.util.FloatProperty;
import android.util.Slog;
import android.view.SurfaceControl;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import com.android.internal.policy.ScreenDecorationsUtils;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class AffordanceAnimController {
    public final Animation mAnimation;
    public ValueAnimator mAnimator;
    public final Context mDisplayContext;
    public final float mRadius;
    public final SurfaceControl.Transaction mTransaction = new SurfaceControl.Transaction();
    public final Transformation mTmpTransformation = new Transformation();
    public final float[] mTmpFloat9 = new float[9];
    public final Rect mBounds = new Rect();
    public final float[][] mAnimSpecArray = {new float[]{0.0f, -4.5f, 3.375f, -2.25f, 1.125f, 0.0f}, new float[]{0.0f, -3.15f, 1.1025f, -0.735f, 0.3675f, 0.0f}, new float[]{0.0f, 4.5f, -3.375f, 2.25f, -1.125f, 0.0f}, new float[]{0.0f, 3.15f, -1.1025f, 0.735f, -0.3675f, 0.0f}};

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public final class AnimTarget implements ValueAnimator.AnimatorUpdateListener {
        public static final AnonymousClass1 X = new FloatProperty("x") { // from class: com.android.wm.shell.fullscreen.AffordanceAnimController.AnimTarget.1
            @Override // android.util.Property
            public final Float get(Object obj) {
                return Float.valueOf(((AnimTarget) obj).x);
            }

            @Override // android.util.FloatProperty
            public final void setValue(Object obj, float f) {
                ((AnimTarget) obj).x = f;
            }
        };
        public static final AnonymousClass2 Y = new FloatProperty("y") { // from class: com.android.wm.shell.fullscreen.AffordanceAnimController.AnimTarget.2
            @Override // android.util.Property
            public final Float get(Object obj) {
                return Float.valueOf(((AnimTarget) obj).y);
            }

            @Override // android.util.FloatProperty
            public final void setValue(Object obj, float f) {
                ((AnimTarget) obj).y = f;
            }
        };
        public final SurfaceControl mLeash;
        public final SurfaceControl.Transaction mTransaction = new SurfaceControl.Transaction();
        public float x;
        public float y;

        public AnimTarget(SurfaceControl surfaceControl) {
            this.mLeash = surfaceControl;
        }

        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
            Slog.d("AffordanceAnimController", "(" + this.x + ", " + this.y + ") playTime=" + valueAnimator.getCurrentPlayTime());
            this.mTransaction.setPosition(this.mLeash, this.x, this.y);
            this.mTransaction.apply();
        }
    }

    public AffordanceAnimController(Context context, int i) {
        Context createDisplayContext = context.createDisplayContext(((DisplayManager) context.getSystemService(DisplayManager.class)).getDisplay(i));
        this.mDisplayContext = createDisplayContext;
        this.mAnimation = AnimationUtils.loadAnimation(context, R.anim.voice_activity_open_exit);
        this.mRadius = ScreenDecorationsUtils.getWindowCornerRadius(createDisplayContext);
    }

    public final Keyframe[] getKeyFrames(float f, boolean z, boolean z2) {
        int i;
        int i2;
        if (z) {
            i = 2;
        } else {
            i = 0;
        }
        if (z2) {
            i2 = 1;
        } else {
            i2 = 0;
        }
        float[] fArr = this.mAnimSpecArray[i + i2];
        Keyframe[] keyframeArr = new Keyframe[fArr.length];
        int length = fArr.length;
        if (length == 0) {
            return null;
        }
        if (length == 1) {
            keyframeArr[0] = Keyframe.ofFloat(0.0f, fArr[0] * f);
        } else {
            for (int i3 = 0; i3 < length; i3++) {
                keyframeArr[i3] = Keyframe.ofFloat(i3 / (length - 1), fArr[i3] * f);
            }
        }
        return keyframeArr;
    }
}
