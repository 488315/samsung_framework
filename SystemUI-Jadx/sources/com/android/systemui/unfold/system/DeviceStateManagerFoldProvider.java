package com.android.systemui.unfold.system;

import android.content.Context;
import android.hardware.devicestate.DeviceStateManager;
import com.android.systemui.unfold.updates.FoldProvider;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class DeviceStateManagerFoldProvider implements FoldProvider {
    public final Map callbacks = new HashMap();
    public final Context context;
    public final DeviceStateManager deviceStateManager;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public final class FoldStateListener extends DeviceStateManager.FoldStateListener {
        public FoldStateListener(DeviceStateManagerFoldProvider deviceStateManagerFoldProvider, Context context, final FoldProvider.FoldCallback foldCallback) {
            super(context, new Consumer() { // from class: com.android.systemui.unfold.system.DeviceStateManagerFoldProvider.FoldStateListener.1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    FoldProvider.FoldCallback.this.onFoldUpdated(((Boolean) obj).booleanValue());
                }
            });
        }
    }

    public DeviceStateManagerFoldProvider(DeviceStateManager deviceStateManager, Context context) {
        this.deviceStateManager = deviceStateManager;
        this.context = context;
    }

    @Override // com.android.systemui.unfold.updates.FoldProvider
    public final void registerCallback(FoldProvider.FoldCallback foldCallback, Executor executor) {
        FoldStateListener foldStateListener = new FoldStateListener(this, this.context, foldCallback);
        this.deviceStateManager.registerCallback(executor, foldStateListener);
        ((HashMap) this.callbacks).put(foldCallback, foldStateListener);
    }
}
