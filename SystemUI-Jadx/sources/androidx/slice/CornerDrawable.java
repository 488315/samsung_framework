package androidx.slice;

import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class CornerDrawable extends InsetDrawable {
    public final float mCornerRadius;
    public final Path mPath;

    public CornerDrawable(Drawable drawable, float f) {
        super(drawable, 0);
        this.mPath = new Path();
        this.mCornerRadius = f;
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public final void draw(Canvas canvas) {
        int save = canvas.save();
        canvas.clipPath(this.mPath);
        super.draw(canvas);
        canvas.restoreToCount(save);
    }

    @Override // android.graphics.drawable.InsetDrawable, android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public final void onBoundsChange(Rect rect) {
        Path path = this.mPath;
        if (path != null) {
            path.reset();
            Path path2 = this.mPath;
            RectF rectF = new RectF(rect);
            float f = this.mCornerRadius;
            path2.addRoundRect(rectF, f, f, Path.Direction.CW);
        }
        super.onBoundsChange(rect);
    }
}