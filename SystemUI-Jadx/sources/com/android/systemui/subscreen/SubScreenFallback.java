package com.android.systemui.subscreen;

import android.app.Activity;
import android.content.ComponentName;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class SubScreenFallback extends Activity {
    public final SubScreenManager subScreenManager;

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
    }

    public SubScreenFallback(SubScreenManager subScreenManager) {
        this.subScreenManager = subScreenManager;
    }

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (!LsRune.SUPPORT_LARGE_FRONT_SUB_DISPLAY) {
            Log.w("SubScreenFallBack", "onCreate() finish - not support this phone");
            ComponentName componentName = new ComponentName("com.android.systemui", "com.android.systemui.subscreen.SubScreenFallback");
            try {
                if (getPackageManager().getComponentEnabledSetting(componentName) != 2) {
                    getPackageManager().setComponentEnabledSetting(componentName, 2, 1);
                }
            } catch (IllegalArgumentException e) {
                MotionLayout$$ExternalSyntheticOutline0.m("There is no component  ", e.getMessage(), "SubScreenFallBack");
            }
            finish();
            return;
        }
        Log.i("SubScreenFallBack", " onCreate");
    }

    @Override // android.app.Activity
    public final void onDestroy() {
        super.onDestroy();
        Log.i("SubScreenFallBack", " onDestroy");
        SubScreenManager subScreenManager = this.subScreenManager;
        subScreenManager.getClass();
        Log.d("SubScreenManager", "setFallbackActivity() null");
        subScreenManager.mFallback = null;
    }

    @Override // android.app.Activity
    public final void onResume() {
        super.onResume();
        Log.i("SubScreenFallBack", " onResume");
        View inflate = getLayoutInflater().inflate(R.layout.subscreen_fallback_finishing_boot, (ViewGroup) null);
        setContentView(inflate);
        setShowWhenLocked(true);
        WindowInsetsController insetsController = getWindow().getInsetsController();
        if (insetsController != null) {
            insetsController.hide(WindowInsets.Type.navigationBars());
            insetsController.setSystemBarsBehavior(1);
        }
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.layoutInDisplayCutoutMode = 3;
        attributes.windowAnimations = 0;
        getWindow().setAttributes(attributes);
        SubScreenManager subScreenManager = this.subScreenManager;
        subScreenManager.getClass();
        Log.d("SubScreenManager", "setFallbackActivity() " + this);
        subScreenManager.mFallback = this;
        View decorView = getWindow().getDecorView();
        if (decorView != null) {
            decorView.setSystemUiVisibility(1024);
        }
        inflate.setAlpha(0.0f);
        inflate.animate().alpha(1.0f).setDuration(500L).setInterpolator(AnimationUtils.loadInterpolator(this, android.R.interpolator.fast_out_slow_in)).start();
        getWindow().addFlags(128);
    }
}
