package com.android.systemui.volume.view.subscreen.simple;

import android.view.View;
import com.android.systemui.R;
import com.android.systemui.volume.util.BlurEffect;
import com.android.systemui.volume.util.ViewVisibilityUtil;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final /* synthetic */ class SubDisplayVolumePanelPresentation$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SubDisplayVolumePanelPresentation f$0;

    public /* synthetic */ SubDisplayVolumePanelPresentation$$ExternalSyntheticLambda1(SubDisplayVolumePanelPresentation subDisplayVolumePanelPresentation, int i) {
        this.$r8$classId = i;
        this.f$0 = subDisplayVolumePanelPresentation;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                SubDisplayVolumePanelPresentation subDisplayVolumePanelPresentation = this.f$0;
                BlurEffect blurEffect = subDisplayVolumePanelPresentation.mBlurEffect;
                View view = subDisplayVolumePanelPresentation.mBlurView;
                blurEffect.getClass();
                ViewVisibilityUtil.INSTANCE.getClass();
                view.setVisibility(4);
                view.semSetBlurInfo(null);
                return;
            case 1:
                this.f$0.dismiss();
                return;
            case 2:
                SubDisplayVolumePanelPresentation subDisplayVolumePanelPresentation2 = this.f$0;
                BlurEffect blurEffect2 = subDisplayVolumePanelPresentation2.mBlurEffect;
                View view2 = subDisplayVolumePanelPresentation2.mBlurView;
                int color = subDisplayVolumePanelPresentation2.getContext().getColor(R.color.sub_large_display_volume_expand_panel_bg_color_blur);
                float dimension = subDisplayVolumePanelPresentation2.getContext().getResources().getDimension(R.dimen.volume_sub_large_display_dual_panel_radius);
                blurEffect2.getClass();
                BlurEffect.setRealTimeBlur(dimension, color, view2);
                return;
            default:
                SubDisplayVolumePanelPresentation subDisplayVolumePanelPresentation3 = this.f$0;
                BlurEffect blurEffect3 = subDisplayVolumePanelPresentation3.mBlurEffect;
                View view3 = subDisplayVolumePanelPresentation3.mBlurView;
                int color2 = subDisplayVolumePanelPresentation3.getContext().getColor(R.color.sub_large_display_volume_seekbar_background_color_blur);
                float dimension2 = subDisplayVolumePanelPresentation3.getContext().getResources().getDimension(R.dimen.volume_sub_large_display_seek_bar_radius);
                blurEffect3.getClass();
                BlurEffect.setRealTimeBlur(dimension2, color2, view3);
                return;
        }
    }
}
