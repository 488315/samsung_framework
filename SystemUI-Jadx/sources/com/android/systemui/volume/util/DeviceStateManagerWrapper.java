package com.android.systemui.volume.util;

import android.content.Context;
import android.hardware.devicestate.DeviceStateManager;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class DeviceStateManagerWrapper {
    public final Context context;
    public DeviceStateManager.FoldStateListener foldStateListener;
    public boolean isFolded;

    public DeviceStateManagerWrapper(Context context) {
        this.context = context;
    }
}