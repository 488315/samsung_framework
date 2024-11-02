package androidx.constraintlayout.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.SparseArray;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.ConstraintWidgetContainer;
import androidx.constraintlayout.core.widgets.HelperWidget;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.Constraints;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public class Barrier extends ConstraintHelper {
    public androidx.constraintlayout.core.widgets.Barrier mBarrier;
    public int mIndicatedType;
    public int mResolvedType;

    public Barrier(Context context) {
        super(context);
        super.setVisibility(8);
    }

    @Override // androidx.constraintlayout.widget.ConstraintHelper
    public final void init(AttributeSet attributeSet) {
        super.init(attributeSet);
        this.mBarrier = new androidx.constraintlayout.core.widgets.Barrier();
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R$styleable.ConstraintLayout_Layout);
            int indexCount = obtainStyledAttributes.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int index = obtainStyledAttributes.getIndex(i);
                if (index == 26) {
                    this.mIndicatedType = obtainStyledAttributes.getInt(index, 0);
                } else if (index == 25) {
                    this.mBarrier.mAllowsGoneWidget = obtainStyledAttributes.getBoolean(index, true);
                } else if (index == 27) {
                    this.mBarrier.mMargin = obtainStyledAttributes.getDimensionPixelSize(index, 0);
                }
            }
            obtainStyledAttributes.recycle();
        }
        this.mHelperWidget = this.mBarrier;
        validateParams();
    }

    @Override // androidx.constraintlayout.widget.ConstraintHelper
    public final void loadParameters(ConstraintSet.Constraint constraint, HelperWidget helperWidget, Constraints.LayoutParams layoutParams, SparseArray sparseArray) {
        super.loadParameters(constraint, helperWidget, layoutParams, sparseArray);
        if (helperWidget instanceof androidx.constraintlayout.core.widgets.Barrier) {
            androidx.constraintlayout.core.widgets.Barrier barrier = (androidx.constraintlayout.core.widgets.Barrier) helperWidget;
            boolean z = ((ConstraintWidgetContainer) helperWidget.mParent).mIsRtl;
            ConstraintSet.Layout layout = constraint.layout;
            updateType(barrier, layout.mBarrierDirection, z);
            barrier.mAllowsGoneWidget = layout.mBarrierAllowsGoneWidgets;
            barrier.mMargin = layout.mBarrierMargin;
        }
    }

    @Override // androidx.constraintlayout.widget.ConstraintHelper
    public final void resolveRtl(ConstraintWidget constraintWidget, boolean z) {
        updateType(constraintWidget, this.mIndicatedType, z);
    }

    public final void updateType(ConstraintWidget constraintWidget, int i, boolean z) {
        this.mResolvedType = i;
        if (z) {
            int i2 = this.mIndicatedType;
            if (i2 == 5) {
                this.mResolvedType = 1;
            } else if (i2 == 6) {
                this.mResolvedType = 0;
            }
        } else {
            int i3 = this.mIndicatedType;
            if (i3 == 5) {
                this.mResolvedType = 0;
            } else if (i3 == 6) {
                this.mResolvedType = 1;
            }
        }
        if (constraintWidget instanceof androidx.constraintlayout.core.widgets.Barrier) {
            ((androidx.constraintlayout.core.widgets.Barrier) constraintWidget).mBarrierType = this.mResolvedType;
        }
    }

    public Barrier(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        super.setVisibility(8);
    }

    public Barrier(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        super.setVisibility(8);
    }
}
