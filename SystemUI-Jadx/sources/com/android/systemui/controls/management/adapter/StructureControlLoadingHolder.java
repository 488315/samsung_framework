package com.android.systemui.controls.management.adapter;

import android.view.View;
import android.widget.TextView;
import com.android.systemui.R;
import com.android.systemui.controls.management.model.LoadingWrapper;
import com.android.systemui.controls.management.model.StructureElementWrapper;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class StructureControlLoadingHolder extends CustomStructureHolder {
    public final TextView subtitleView;

    public StructureControlLoadingHolder(View view) {
        super(view, null);
        this.subtitleView = (TextView) this.itemView.requireViewById(R.id.subtitle);
    }

    @Override // com.android.systemui.controls.management.adapter.CustomStructureHolder
    public final void bindData(StructureElementWrapper structureElementWrapper) {
        this.subtitleView.setText(((LoadingWrapper) structureElementWrapper).subtitle);
    }
}
