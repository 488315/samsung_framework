package androidx.picker.features.composable.widget;

import android.view.View;
import android.widget.ImageView;
import androidx.picker.adapter.AbsAdapter;
import androidx.picker.features.composable.ActionableComposableViewHolder;
import androidx.picker.model.viewdata.AppInfoViewData;
import androidx.picker.model.viewdata.CategoryViewData;
import androidx.picker.model.viewdata.ViewData;
import com.android.systemui.R;
import java.util.ArrayList;
import java.util.Iterator;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class ComposableExpanderViewHolder extends ActionableComposableViewHolder {
    private CategoryViewData refferalItem;
    private final ImageView toggle;

    public ComposableExpanderViewHolder(View view) {
        super(view);
        View findViewById = view.findViewById(R.id.image_button);
        Intrinsics.checkNotNull(findViewById);
        this.toggle = (ImageView) findViewById;
        Intrinsics.checkNotNull(view.findViewById(R.id.switch_divider_widget));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: bindAdapter$lambda-2$lambda-1, reason: not valid java name */
    public static final void m30bindAdapter$lambda2$lambda1(ComposableExpanderViewHolder composableExpanderViewHolder, AbsAdapter absAdapter, View view) {
        CategoryViewData categoryViewData = composableExpanderViewHolder.refferalItem;
        if (categoryViewData == null) {
            categoryViewData = null;
        }
        view.setSelected(categoryViewData.invisibleChildren.isEmpty());
        composableExpanderViewHolder.checkCollapsed(absAdapter, view.isSelected());
    }

    private final void checkCollapsed(AbsAdapter absAdapter, boolean z) {
        int i;
        ArrayList arrayList = (ArrayList) absAdapter.mDataSetFiltered;
        CategoryViewData categoryViewData = this.refferalItem;
        CategoryViewData categoryViewData2 = null;
        if (categoryViewData == null) {
            categoryViewData = null;
        }
        int indexOf = arrayList.indexOf(categoryViewData);
        if (z) {
            int i2 = 0;
            while (true) {
                i = indexOf + 1;
                if (arrayList.size() <= i || !checkCollapsed$isCanBeCollapsed((ViewData) arrayList.get(i))) {
                    break;
                }
                CategoryViewData categoryViewData3 = this.refferalItem;
                if (categoryViewData3 == null) {
                    categoryViewData3 = null;
                }
                categoryViewData3.invisibleChildren.add(arrayList.remove(i));
                i2++;
            }
            absAdapter.notifyItemRangeRemoved(i, i2);
        } else {
            int i3 = indexOf + 1;
            CategoryViewData categoryViewData4 = this.refferalItem;
            if (categoryViewData4 == null) {
                categoryViewData4 = null;
            }
            Iterator it = categoryViewData4.invisibleChildren.iterator();
            int i4 = i3;
            while (it.hasNext()) {
                arrayList.add(i4, (ViewData) it.next());
                i4++;
            }
            absAdapter.notifyItemRangeInserted(i3, (i4 - indexOf) - 1);
            CategoryViewData categoryViewData5 = this.refferalItem;
            if (categoryViewData5 != null) {
                categoryViewData2 = categoryViewData5;
            }
            categoryViewData2.invisibleChildren.clear();
        }
        absAdapter.notifyItemChanged(indexOf);
    }

    private static final boolean checkCollapsed$isCanBeCollapsed(ViewData viewData) {
        return viewData instanceof AppInfoViewData;
    }

    @Override // androidx.picker.features.composable.ComposableViewHolder
    public void bindAdapter(final AbsAdapter absAdapter) {
        this.toggle.setOnClickListener(new View.OnClickListener() { // from class: androidx.picker.features.composable.widget.ComposableExpanderViewHolder$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ComposableExpanderViewHolder.m30bindAdapter$lambda2$lambda1(ComposableExpanderViewHolder.this, absAdapter, view);
            }
        });
    }

    @Override // androidx.picker.features.composable.ActionableComposableViewHolder, androidx.picker.features.composable.ComposableViewHolder
    public void bindData(ViewData viewData) {
        if (!(viewData instanceof CategoryViewData)) {
            return;
        }
        CategoryViewData categoryViewData = (CategoryViewData) viewData;
        this.refferalItem = categoryViewData;
        this.toggle.setSelected(categoryViewData.invisibleChildren.isEmpty());
    }

    @Override // androidx.picker.features.composable.ActionableComposableViewHolder, androidx.picker.features.composable.ComposableViewHolder
    public void onViewRecycled(View view) {
        super.onViewRecycled(view);
        ImageView imageView = this.toggle;
        imageView.setSelected(false);
        imageView.setOnTouchListener(null);
        imageView.setOnClickListener(null);
    }
}
