package com.android.systemui.navigationbar.gestural;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.VectorDrawable;
import com.samsung.android.graphics.spr.SemPathRenderingDrawable;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class GestureHintDrawable extends LayerDrawable {
    public static final Companion Companion = new Companion(null);

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static Drawable rotateDrawable(Resources resources, Drawable drawable, int i) {
            Bitmap bitmap;
            Bitmap bitmap2;
            Matrix matrix = new Matrix();
            if (i != 1) {
                if (i == 3) {
                    matrix.postRotate(90.0f);
                }
            } else {
                matrix.postRotate(-90.0f);
            }
            if (drawable instanceof BitmapDrawable) {
                bitmap2 = ((BitmapDrawable) drawable).getBitmap();
            } else if (drawable instanceof SemPathRenderingDrawable) {
                bitmap2 = ((SemPathRenderingDrawable) drawable).getBitmap();
            } else if (!(drawable instanceof VectorDrawable) && !(drawable instanceof GradientDrawable)) {
                bitmap2 = null;
            } else {
                Bitmap createBitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(createBitmap);
                drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                drawable.draw(canvas);
                bitmap = createBitmap;
                Intrinsics.checkNotNull(bitmap);
                return new BitmapDrawable(resources, Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true));
            }
            bitmap = bitmap2;
            Intrinsics.checkNotNull(bitmap);
            return new BitmapDrawable(resources, Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true));
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GestureHintDrawable(Drawable[] drawableArr) {
        super(drawableArr);
        Intrinsics.checkNotNull(drawableArr);
    }

    public final void setDarkIntensity(float f) {
        getDrawable(0).mutate().setAlpha((int) ((1 - f) * 255.0f));
        getDrawable(1).mutate().setAlpha((int) (f * 255.0f));
        invalidateSelf();
    }
}
