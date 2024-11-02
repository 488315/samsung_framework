package com.android.systemui.qp;

import android.content.Context;
import com.android.systemui.R;
import com.android.systemui.util.ViewController;
import java.util.Objects;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class SubscreenBaseViewControllerBase extends ViewController implements SubscreenQSControllerContract$BaseViewController {
    public final Context mContext;
    public final SubroomQuickSettingsBaseView mView;

    public SubscreenBaseViewControllerBase(SubroomQuickSettingsBaseView subroomQuickSettingsBaseView) {
        super(subroomQuickSettingsBaseView);
        this.mView = subroomQuickSettingsBaseView;
        this.mContext = getContext();
    }

    @Override // com.android.systemui.util.ViewController
    public final void onInit() {
        SubroomQuickSettingsBaseView subroomQuickSettingsBaseView = this.mView;
        Objects.toString(subroomQuickSettingsBaseView);
        new SubscreenBrightnessController(this.mContext, (SubroomBrightnessSettingsView) subroomQuickSettingsBaseView.findViewById(R.id.subroom_brightness_settings)).init();
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() {
        Objects.toString(this.mView);
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewDetached() {
        Objects.toString(this.mView);
    }
}
