package androidx.leanback.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import com.android.systemui.R;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
class MediaRowFocusView extends View {
    public final Paint mPaint;
    public final RectF mRoundRectF;
    public int mRoundRectRadius;

    public MediaRowFocusView(Context context) {
        super(context);
        this.mRoundRectF = new RectF();
        this.mPaint = createPaint(context);
    }

    public static Paint createPaint(Context context) {
        Paint paint = new Paint();
        paint.setColor(context.getResources().getColor(R.color.lb_playback_media_row_highlight_color));
        return paint;
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = getHeight() / 2;
        this.mRoundRectRadius = height;
        int height2 = ((height * 2) - getHeight()) / 2;
        this.mRoundRectF.set(0.0f, -height2, getWidth(), getHeight() + height2);
        RectF rectF = this.mRoundRectF;
        int i = this.mRoundRectRadius;
        canvas.drawRoundRect(rectF, i, i, this.mPaint);
    }

    public MediaRowFocusView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mRoundRectF = new RectF();
        this.mPaint = createPaint(context);
    }

    public MediaRowFocusView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mRoundRectF = new RectF();
        this.mPaint = createPaint(context);
    }
}
