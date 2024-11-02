package com.android.systemui.statusbar.connectivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import com.android.systemui.plugins.subscreen.SubRoom;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class SubRoomNetworkInfo implements SubRoom {
    public boolean isAirplane;
    public byte[] modeIcon;
    public int noServiceType;
    public SubRoom.StateChangeListener stateChangeListener;

    @Override // com.android.systemui.plugins.subscreen.SubRoom
    public final View getView(Context context) {
        return null;
    }

    @Override // com.android.systemui.plugins.subscreen.SubRoom
    public final void removeListener() {
        this.stateChangeListener = null;
    }

    @Override // com.android.systemui.plugins.subscreen.SubRoom
    public final Bundle request(String str, Bundle bundle) {
        if (Intrinsics.areEqual(SubRoom.STATE_NETWORK_INFO, str)) {
            Bundle bundle2 = new Bundle();
            bundle2.putBoolean(SubRoom.EXTRA_KEY_AIRPLANE_MODE, this.isAirplane);
            bundle2.putInt(SubRoom.EXTRA_KEY_NO_SIGNAL, this.noServiceType);
            bundle2.putByteArray(SubRoom.EXTRA_KEY_ROUTINE_MODE, this.modeIcon);
            return bundle2;
        }
        return bundle;
    }

    @Override // com.android.systemui.plugins.subscreen.SubRoom
    public final void setListener(SubRoom.StateChangeListener stateChangeListener) {
        this.stateChangeListener = stateChangeListener;
    }

    @Override // com.android.systemui.plugins.subscreen.SubRoom
    public final void onCloseFinished() {
    }

    @Override // com.android.systemui.plugins.subscreen.SubRoom
    public final void onCloseStarted() {
    }

    @Override // com.android.systemui.plugins.subscreen.SubRoom
    public final void onOpenFinished() {
    }

    @Override // com.android.systemui.plugins.subscreen.SubRoom
    public final void onOpenStarted() {
    }
}
