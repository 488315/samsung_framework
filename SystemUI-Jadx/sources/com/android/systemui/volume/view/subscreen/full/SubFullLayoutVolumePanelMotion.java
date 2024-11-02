package com.android.systemui.volume.view.subscreen.full;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.PathInterpolator;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import com.android.systemui.R;
import com.android.systemui.volume.store.StoreInteractor;
import com.android.systemui.volume.util.ContextUtils;
import com.android.systemui.volume.util.ViewVisibilityUtil;
import com.samsung.systemui.splugins.volume.VolumePanelAction;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.util.List;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class SubFullLayoutVolumePanelMotion {
    public static final PathInterpolator HIDE_INTERPOLATOR;
    public static final PathInterpolator TITLE_TRANSLATION_INTERPOLATOR;
    public Context context;
    public SpringAnimation singleShowSpringAnimation;
    public final StoreInteractor storeInteractor = new StoreInteractor(null, null, 3, null);

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
        HIDE_INTERPOLATOR = new PathInterpolator(0.7f, 0.0f, 0.83f, 0.83f);
        TITLE_TRANSLATION_INTERPOLATOR = new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f);
    }

    public static SpringAnimation getSeekBarTouchDownAnimation(final View view) {
        SpringAnimation springAnimation = new SpringAnimation(view, DynamicAnimation.SCALE_X);
        springAnimation.addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumePanelMotion$getSeekBarTouchDownAnimation$1$1
            @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationUpdateListener
            public final void onAnimationUpdate(float f, float f2) {
                view.setScaleY(f);
            }
        });
        SpringForce springForce = new SpringForce();
        springForce.setStiffness(300.0f);
        springForce.setDampingRatio(1.0f);
        springAnimation.mSpring = springForce;
        return springAnimation;
    }

    public static SpringAnimation getSeekBarTouchUpAnimation(final View view) {
        SpringAnimation springAnimation = new SpringAnimation(view, DynamicAnimation.SCALE_X);
        springAnimation.addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumePanelMotion$getSeekBarTouchUpAnimation$1$1
            @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationUpdateListener
            public final void onAnimationUpdate(float f, float f2) {
                view.setScaleY(f);
            }
        });
        SpringForce springForce = new SpringForce();
        springForce.setStiffness(200.0f);
        springForce.setDampingRatio(1.0f);
        springAnimation.mSpring = springForce;
        return springAnimation;
    }

    public static Animator getVibrationAnimator(float f, float f2, int i, View view) {
        boolean z;
        float f3 = 0.0f;
        if (f2 == 0.0f) {
            z = true;
        } else {
            z = false;
        }
        if (!z) {
            f3 = (-f) + f2;
        }
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, "translationX", f, f3);
        ofFloat.setDuration(i);
        ofFloat.setInterpolator(new LinearInterpolator());
        return ofFloat;
    }

    public static void startSeekBarTouchDownAnimation(SpringAnimation springAnimation, SpringAnimation springAnimation2, boolean z) {
        float f;
        boolean z2;
        if (springAnimation2 != null) {
            if (springAnimation2.mRunning && springAnimation2.canSkipToEnd()) {
                z2 = true;
            } else {
                z2 = false;
            }
            if (!z2) {
                springAnimation2 = null;
            }
            if (springAnimation2 != null) {
                springAnimation2.skipToEnd();
            }
        }
        if (z) {
            f = 1.05f;
        } else {
            f = 1.07f;
        }
        springAnimation.animateToFinalPosition(f);
    }

    public static void startSeekBarTouchUpAnimation(SpringAnimation springAnimation, SpringAnimation springAnimation2) {
        boolean z;
        if (springAnimation2 != null) {
            if (springAnimation2.mRunning && springAnimation2.canSkipToEnd()) {
                z = true;
            } else {
                z = false;
            }
            if (!z) {
                springAnimation2 = null;
            }
            if (springAnimation2 != null) {
                springAnimation2.skipToEnd();
            }
        }
        springAnimation.animateToFinalPosition(1.0f);
    }

    public static void startSplashAnimation(final View view) {
        view.setScaleX(0.0f);
        SpringAnimation springAnimation = new SpringAnimation(view, DynamicAnimation.SCALE_X);
        springAnimation.cancel();
        springAnimation.mVelocity = 0.0f;
        springAnimation.addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumePanelMotion$startSplashAnimation$1$1
            @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationUpdateListener
            public final void onAnimationUpdate(float f, float f2) {
                boolean z;
                if (f2 == 0.0f) {
                    z = true;
                } else {
                    z = false;
                }
                if (z) {
                    View view2 = view;
                    view2.setPivotX(0.0f);
                    view2.setPivotY(0.0f);
                }
            }
        });
        SpringForce springForce = new SpringForce();
        springForce.setStiffness(300.0f);
        springForce.setDampingRatio(0.58f);
        springAnimation.mSpring = springForce;
        springAnimation.setStartValue(0.0f);
        springAnimation.animateToFinalPosition(1.0f);
    }

    public final void startMaxAnimation(int i, View view, View view2, View view3, View view4, View view5, View view6) {
        ViewVisibilityUtil.INSTANCE.getClass();
        view5.setVisibility(8);
        view.setVisibility(0);
        view6.setVisibility(8);
        if (view4 != null) {
            view4.setVisibility(8);
            view2.setVisibility(0);
            view3.setVisibility(0);
        }
        Context context = this.context;
        Context context2 = null;
        if (context == null) {
            context = null;
        }
        float dimenFloat = ContextUtils.getDimenFloat(R.dimen.sub_full_volume_media_icon_note_max_x, context);
        Context context3 = this.context;
        if (context3 == null) {
            context3 = null;
        }
        float dimenFloat2 = ContextUtils.getDimenFloat(R.dimen.sub_full_volume_media_icon_wave_s_max_x, context3);
        Context context4 = this.context;
        if (context4 == null) {
            context4 = null;
        }
        float dimenFloat3 = ContextUtils.getDimenFloat(R.dimen.sub_full_volume_media_icon_wave_l_max_x, context4);
        if (VolumePanelValues.isRing(i) || VolumePanelValues.isAlarm(i)) {
            Context context5 = this.context;
            if (context5 == null) {
                context5 = null;
            }
            dimenFloat2 = ContextUtils.getDimenFloat(R.dimen.sub_full_volume_sound_icon_wave_s_max_x, context5);
            Context context6 = this.context;
            if (context6 != null) {
                context2 = context6;
            }
            dimenFloat3 = ContextUtils.getDimenFloat(R.dimen.sub_full_volume_sound_icon_wave_l_max_x, context2);
        }
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view2, "alpha", view2.getAlpha(), 0.5f);
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(view3, "alpha", view3.getAlpha(), 0.5f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ofFloat);
        animatorSet.playTogether(ofFloat2);
        animatorSet.setDuration(150L);
        animatorSet.setInterpolator(new LinearInterpolator());
        ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(view, "x", view.getX(), dimenFloat);
        ObjectAnimator ofFloat4 = ObjectAnimator.ofFloat(view2, "x", view2.getX(), dimenFloat2);
        ObjectAnimator ofFloat5 = ObjectAnimator.ofFloat(view3, "x", view3.getX(), dimenFloat3);
        AnimatorSet animatorSet2 = new AnimatorSet();
        animatorSet2.playTogether(ofFloat3);
        animatorSet2.playTogether(ofFloat4);
        animatorSet2.playTogether(ofFloat5);
        animatorSet2.setDuration(200L);
        animatorSet2.setInterpolator(new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f));
        AnimatorSet animatorSet3 = new AnimatorSet();
        animatorSet3.playTogether(animatorSet);
        animatorSet3.playTogether(animatorSet2);
        animatorSet3.start();
    }

    public final void startMidAnimation(final int i, final int i2, View view, View view2, View view3, View view4, View view5, View view6) {
        ViewVisibilityUtil.INSTANCE.getClass();
        view5.setVisibility(8);
        view.setVisibility(0);
        view6.setVisibility(8);
        if (view4 != null) {
            view4.setVisibility(8);
            view2.setVisibility(0);
            view3.setVisibility(0);
        }
        Context context = this.context;
        Context context2 = null;
        if (context == null) {
            context = null;
        }
        float dimenFloat = ContextUtils.getDimenFloat(R.dimen.sub_full_volume_media_icon_note_mid_x, context);
        Context context3 = this.context;
        if (context3 == null) {
            context3 = null;
        }
        float dimenFloat2 = ContextUtils.getDimenFloat(R.dimen.sub_full_volume_media_icon_wave_s_mid_x, context3);
        Context context4 = this.context;
        if (context4 == null) {
            context4 = null;
        }
        float dimenFloat3 = ContextUtils.getDimenFloat(R.dimen.sub_full_volume_media_icon_wave_l_mid_x, context4);
        if (VolumePanelValues.isRing(i) || VolumePanelValues.isAlarm(i)) {
            Context context5 = this.context;
            if (context5 == null) {
                context5 = null;
            }
            dimenFloat2 = ContextUtils.getDimenFloat(R.dimen.sub_full_volume_sound_icon_wave_s_mid_x, context5);
            Context context6 = this.context;
            if (context6 != null) {
                context2 = context6;
            }
            dimenFloat3 = ContextUtils.getDimenFloat(R.dimen.sub_full_volume_sound_icon_wave_l_mid_x, context2);
        }
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view2, "alpha", view2.getAlpha(), 0.5f);
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(view3, "alpha", view3.getAlpha(), 0.1f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ofFloat);
        animatorSet.playTogether(ofFloat2);
        animatorSet.setDuration(100L);
        animatorSet.setInterpolator(new LinearInterpolator());
        ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(view, "x", view.getX(), dimenFloat);
        ObjectAnimator ofFloat4 = ObjectAnimator.ofFloat(view2, "x", view2.getX(), dimenFloat2);
        ObjectAnimator ofFloat5 = ObjectAnimator.ofFloat(view3, "x", view3.getX(), dimenFloat3);
        AnimatorSet animatorSet2 = new AnimatorSet();
        animatorSet2.playTogether(ofFloat3);
        animatorSet2.playTogether(ofFloat4);
        animatorSet2.playTogether(ofFloat5);
        animatorSet2.setDuration(200L);
        animatorSet2.setInterpolator(new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f));
        AnimatorSet animatorSet3 = new AnimatorSet();
        animatorSet3.playTogether(animatorSet2);
        animatorSet3.playTogether(animatorSet);
        animatorSet3.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumePanelMotion$startMidAnimation$2$1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                SubFullLayoutVolumePanelMotion.this.storeInteractor.sendAction(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_VOLUME_ICON_ANIMATION_FINISHED).stream(i).iconTargetState(i2).iconCurrentState(2).build(), false);
            }
        });
        animatorSet3.start();
    }

    public final void startMinAnimation(final int i, final int i2, View view, View view2, View view3, View view4, View view5, View view6) {
        float f;
        ViewVisibilityUtil.INSTANCE.getClass();
        view5.setVisibility(8);
        view.setVisibility(0);
        view6.setVisibility(8);
        if (view4 != null) {
            view4.setVisibility(8);
            view2.setVisibility(0);
            view3.setVisibility(0);
        }
        Context context = this.context;
        Context context2 = null;
        if (context == null) {
            context = null;
        }
        float dimenFloat = ContextUtils.getDimenFloat(R.dimen.sub_full_volume_media_icon_note_min_x, context);
        if (!VolumePanelValues.isRing(i) && !VolumePanelValues.isAlarm(i)) {
            f = 0.0f;
        } else {
            Context context3 = this.context;
            if (context3 == null) {
                context3 = null;
            }
            dimenFloat = ContextUtils.getDimenFloat(R.dimen.sub_full_volume_sound_icon_spk_min_x, context3);
            f = 0.3f;
        }
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view2, "alpha", view2.getAlpha(), f);
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(view3, "alpha", view3.getAlpha(), 0.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ofFloat);
        animatorSet.playTogether(ofFloat2);
        animatorSet.setDuration(100L);
        animatorSet.setInterpolator(new LinearInterpolator());
        ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(view, "x", view.getX(), dimenFloat);
        AnimatorSet animatorSet2 = new AnimatorSet();
        animatorSet2.playTogether(ofFloat3);
        if (VolumePanelValues.isRing(i) || VolumePanelValues.isAlarm(i)) {
            Context context4 = this.context;
            if (context4 != null) {
                context2 = context4;
            }
            animatorSet2.playTogether(ObjectAnimator.ofFloat(view2, "x", view2.getX(), ContextUtils.getDimenFloat(R.dimen.sub_full_volume_sound_icon_wave_s_min_x, context2)));
        }
        animatorSet2.setDuration(200L);
        animatorSet2.setInterpolator(new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f));
        AnimatorSet animatorSet3 = new AnimatorSet();
        animatorSet3.playTogether(animatorSet);
        animatorSet3.playTogether(animatorSet2);
        animatorSet3.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumePanelMotion$startMinAnimation$2$1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                SubFullLayoutVolumePanelMotion.this.storeInteractor.sendAction(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_VOLUME_ICON_ANIMATION_FINISHED).stream(i).iconTargetState(i2).iconCurrentState(1).build(), false);
            }
        });
        animatorSet3.start();
    }

    public final void startMuteAnimation(int i, View view, View view2, View view3, View view4, View view5, View view6) {
        float dimenFloat;
        ViewVisibilityUtil.INSTANCE.getClass();
        view5.setVisibility(0);
        view.setVisibility(4);
        view6.setVisibility(0);
        if (view4 != null) {
            view4.setVisibility(8);
            view2.setVisibility(0);
            view3.setVisibility(0);
        }
        Context context = null;
        if (VolumePanelValues.isRing(i)) {
            Context context2 = this.context;
            if (context2 != null) {
                context = context2;
            }
            dimenFloat = ContextUtils.getDimenFloat(R.dimen.sub_full_volume_sound_icon_spk_min_x, context);
        } else {
            Context context3 = this.context;
            if (context3 != null) {
                context = context3;
            }
            dimenFloat = ContextUtils.getDimenFloat(R.dimen.sub_full_volume_media_icon_note_min_x, context);
        }
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view2, "alpha", view2.getAlpha(), 0.0f);
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(view3, "alpha", view3.getAlpha(), 0.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ofFloat);
        animatorSet.playTogether(ofFloat2);
        animatorSet.setDuration(100L);
        animatorSet.setInterpolator(new LinearInterpolator());
        ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(view, "x", view.getX(), dimenFloat);
        ofFloat3.setDuration(200L);
        ofFloat3.setInterpolator(new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f));
        AnimatorSet animatorSet2 = new AnimatorSet();
        animatorSet2.playTogether(animatorSet);
        animatorSet2.playTogether(ofFloat3);
        animatorSet2.start();
        startSplashAnimation(view6);
    }

    public final void startSoundVibrationAnimation(View view, View view2, View view3, View view4, View view5, View view6) {
        ViewVisibilityUtil.INSTANCE.getClass();
        view.setVisibility(0);
        view5.setVisibility(4);
        view2.setVisibility(4);
        view6.setVisibility(4);
        view3.setVisibility(4);
        view4.setVisibility(4);
        Context context = this.context;
        if (context == null) {
            context = null;
        }
        float dimenFloat = ContextUtils.getDimenFloat(R.dimen.sub_full_volume_sound_icon_spk_min_x, context);
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view3, "alpha", view3.getAlpha(), 0.0f);
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(view4, "alpha", view4.getAlpha(), 0.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ofFloat);
        animatorSet.playTogether(ofFloat2);
        animatorSet.setDuration(50L);
        animatorSet.setInterpolator(new LinearInterpolator());
        ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(view2, "x", view2.getX(), dimenFloat);
        ofFloat3.setDuration(200L);
        ofFloat3.setInterpolator(new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f));
        AnimatorSet animatorSet2 = new AnimatorSet();
        animatorSet2.playTogether(animatorSet);
        animatorSet2.playTogether(ofFloat3);
        animatorSet2.start();
        startVibrationAnimation(view);
    }

    public final void startVibrationAnimation(View view) {
        Context context = this.context;
        Context context2 = null;
        if (context == null) {
            context = null;
        }
        float dimenFloat = ContextUtils.getDimenFloat(R.dimen.sub_full_volume_vibrate_init, context);
        Context context3 = this.context;
        if (context3 != null) {
            context2 = context3;
        }
        float dimenFloat2 = ContextUtils.getDimenFloat(R.dimen.sub_full_volume_vibrate_offset, context2);
        float f = -dimenFloat;
        float f2 = dimenFloat - dimenFloat2;
        float f3 = -(dimenFloat - (dimenFloat2 * 2));
        List<Animator> mutableListOf = CollectionsKt__CollectionsKt.mutableListOf(getVibrationAnimator(0.0f, f, 60, view), getVibrationAnimator(f, f2, 80, view), getVibrationAnimator(f2, f3, 100, view), getVibrationAnimator(f3, 0.0f, 120, view));
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(mutableListOf);
        animatorSet.start();
    }
}
