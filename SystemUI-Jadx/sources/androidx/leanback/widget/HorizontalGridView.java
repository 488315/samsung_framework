package androidx.leanback.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import androidx.core.view.ViewCompat;
import androidx.leanback.R$styleable;
import java.util.WeakHashMap;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public class HorizontalGridView extends BaseGridView {
    public HorizontalGridView(Context context) {
        this(context, null);
    }

    @Override // androidx.recyclerview.widget.RecyclerView, android.view.View
    public final void draw(Canvas canvas) {
        super.draw(canvas);
    }

    public HorizontalGridView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public HorizontalGridView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        new Paint();
        new Rect();
        this.mLayoutManager.setOrientation(0);
        initBaseGridViewAttributes(context, attributeSet);
        int[] iArr = R$styleable.lbHorizontalGridView;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, iArr);
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api29Impl.saveAttributeDataForStyleable(this, context, iArr, attributeSet, obtainStyledAttributes, 0, 0);
        if (obtainStyledAttributes.peekValue(1) != null) {
            this.mLayoutManager.setRowHeight(obtainStyledAttributes.getLayoutDimension(1, 0));
            requestLayout();
        }
        int i2 = obtainStyledAttributes.getInt(0, 1);
        GridLayoutManager gridLayoutManager = this.mLayoutManager;
        if (i2 >= 0) {
            gridLayoutManager.mNumRowsRequested = i2;
            requestLayout();
            obtainStyledAttributes.recycle();
            setLayerType(0, null);
            setWillNotDraw(true);
            new Paint().setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
            return;
        }
        gridLayoutManager.getClass();
        throw new IllegalArgumentException();
    }
}
