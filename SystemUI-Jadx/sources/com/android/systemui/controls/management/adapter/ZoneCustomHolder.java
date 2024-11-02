package com.android.systemui.controls.management.adapter;

import android.view.View;
import android.widget.TextView;
import com.android.systemui.R;
import com.android.systemui.controls.management.model.CustomElementWrapper;
import com.android.systemui.controls.management.model.CustomZoneNameWrapper;
import com.android.systemui.controls.ui.util.ControlsUtil;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class ZoneCustomHolder extends CustomHolder {
    public final TextView zone;

    public ZoneCustomHolder(View view) {
        super(view, null);
        TextView textView = (TextView) this.itemView.requireViewById(R.id.controls_custom_zone_header);
        ControlsUtil.Companion.updateFontSize$default(ControlsUtil.Companion, textView, R.dimen.basic_interaction_subheader_text_size);
        this.zone = textView;
    }

    @Override // com.android.systemui.controls.management.adapter.CustomHolder
    public final void bindData(CustomElementWrapper customElementWrapper) {
        this.zone.setText(((CustomZoneNameWrapper) customElementWrapper).zoneName);
    }
}
