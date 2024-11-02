package com.android.systemui.util.drawable;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimatedImageDrawable;
import android.graphics.drawable.AnimatedRotateDrawable;
import android.graphics.drawable.AnimatedStateListDrawable;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class DrawableSize {
    public static final Companion Companion = new Companion(null);

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static boolean isSimpleBitmap(Drawable drawable) {
            boolean z;
            if (drawable.isStateful()) {
                return false;
            }
            if (!(drawable instanceof Animatable) && !(drawable instanceof Animatable2) && !(drawable instanceof AnimatedImageDrawable) && !(drawable instanceof AnimatedRotateDrawable) && !(drawable instanceof AnimatedStateListDrawable) && !(drawable instanceof AnimatedVectorDrawable)) {
                z = false;
            } else {
                z = true;
            }
            if (z) {
                return false;
            }
            return true;
        }
    }
}
