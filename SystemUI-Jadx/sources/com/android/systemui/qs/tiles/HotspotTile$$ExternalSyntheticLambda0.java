package com.android.systemui.qs.tiles;

import com.android.systemui.qs.tiles.HotspotTile;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final /* synthetic */ class HotspotTile$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ HotspotTile$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ((HotspotTile) this.f$0).handleSecondaryClick(true);
                return;
            default:
                HotspotTile.HotSpotDetailAdapter hotSpotDetailAdapter = (HotspotTile.HotSpotDetailAdapter) this.f$0;
                int i = HotspotTile.HotSpotDetailAdapter.$r8$clinit;
                hotSpotDetailAdapter.setToggleState(!hotSpotDetailAdapter.getToggleState().booleanValue());
                return;
        }
    }
}