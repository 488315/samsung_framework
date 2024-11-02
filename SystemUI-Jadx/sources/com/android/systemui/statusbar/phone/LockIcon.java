package com.android.systemui.statusbar.phone;

import android.R;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Trace;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.ViewTreeObserver;
import com.android.systemui.statusbar.KeyguardAffordanceView;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public class LockIcon extends KeyguardAffordanceView {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final SparseArray mDrawableCache;

    public LockIcon(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mDrawableCache = new SparseArray();
        new ViewTreeObserver.OnPreDrawListener() { // from class: com.android.systemui.statusbar.phone.LockIcon.1
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public final boolean onPreDraw() {
                LockIcon.this.getViewTreeObserver().removeOnPreDrawListener(this);
                LockIcon lockIcon = LockIcon.this;
                int i = LockIcon.$r8$clinit;
                lockIcon.getClass();
                LockIcon.this.getClass();
                LockIcon lockIcon2 = LockIcon.this;
                lockIcon2.getClass();
                if (!lockIcon2.mDrawableCache.contains(R.drawable.ic_media_route_connected_dark_22_mtrl)) {
                    lockIcon2.mDrawableCache.put(R.drawable.ic_media_route_connected_dark_22_mtrl, lockIcon2.getContext().getDrawable(R.drawable.ic_media_route_connected_dark_22_mtrl));
                }
                Drawable drawable = (Drawable) lockIcon2.mDrawableCache.get(R.drawable.ic_media_route_connected_dark_22_mtrl);
                LockIcon.this.setImageDrawable$1(drawable);
                if (drawable instanceof AnimatedVectorDrawable) {
                    final AnimatedVectorDrawable animatedVectorDrawable = (AnimatedVectorDrawable) drawable;
                    animatedVectorDrawable.forceAnimationOnUI();
                    animatedVectorDrawable.clearAnimationCallbacks();
                    final int i2 = 0;
                    animatedVectorDrawable.registerAnimationCallback(new Animatable2.AnimationCallback() { // from class: com.android.systemui.statusbar.phone.LockIcon.1.1
                        @Override // android.graphics.drawable.Animatable2.AnimationCallback
                        public final void onAnimationEnd(Drawable drawable2) {
                            if (LockIcon.this.getDrawable() == animatedVectorDrawable) {
                                int i3 = i2;
                                LockIcon lockIcon3 = LockIcon.this;
                                int i4 = LockIcon.$r8$clinit;
                                lockIcon3.getClass();
                                if (i3 == 0 && i2 == 2) {
                                    animatedVectorDrawable.start();
                                    return;
                                }
                            }
                            Trace.endAsyncSection("LockIcon#Animation", i2);
                        }
                    });
                    Trace.beginAsyncSection("LockIcon#Animation", 0);
                    animatedVectorDrawable.start();
                    return true;
                }
                return true;
            }
        };
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mDrawableCache.clear();
    }
}
