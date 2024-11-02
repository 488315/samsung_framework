package com.android.systemui.qs.customize;

import android.view.View;
import com.android.systemui.qs.customize.SecQSCustomizerBase;
import java.util.function.Consumer;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final /* synthetic */ class SecQSCustomizerController$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SecQSCustomizerController f$0;

    public /* synthetic */ SecQSCustomizerController$$ExternalSyntheticLambda1(SecQSCustomizerController secQSCustomizerController, int i) {
        this.$r8$classId = i;
        this.f$0 = secQSCustomizerController;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                SecQSCustomizerController secQSCustomizerController = this.f$0;
                View view = (View) obj;
                CustomActionMoveItem customActionMoveItem = secQSCustomizerController.mCustomActionMoveItem;
                if (customActionMoveItem != null) {
                    customActionMoveItem.actionFinish();
                }
                secQSCustomizerController.mCustomActionMoveItem = secQSCustomizerController.createCustomActionMoveItem((SecQSCustomizerBase.CustomTileInfo) view.getTag(), secQSCustomizerController.mAvailableTileLayout, secQSCustomizerController.mActiveTileLayout);
                return;
            case 1:
                SecQSCustomizerController secQSCustomizerController2 = this.f$0;
                View view2 = (View) obj;
                CustomActionMoveItem customActionMoveItem2 = secQSCustomizerController2.mCustomActionMoveItem;
                if (customActionMoveItem2 != null) {
                    customActionMoveItem2.actionFinish();
                }
                secQSCustomizerController2.mCustomActionMoveItem = secQSCustomizerController2.createCustomActionMoveItem((SecQSCustomizerBase.CustomTileInfo) view2.getTag(), secQSCustomizerController2.mAvailableTileLayout, secQSCustomizerController2.mActiveTileLayout);
                return;
            case 2:
                this.f$0.animationDropOtherPage((SecQSCustomizerBase.CustomTileInfo) obj);
                return;
            default:
                this.f$0.animationDropOtherPage((SecQSCustomizerBase.CustomTileInfo) obj);
                return;
        }
    }
}
