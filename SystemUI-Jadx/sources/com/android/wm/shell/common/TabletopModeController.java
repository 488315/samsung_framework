package com.android.wm.shell.common;

import android.content.Context;
import android.content.res.Configuration;
import android.os.SystemProperties;
import android.util.ArraySet;
import com.android.wm.shell.common.DevicePostureController;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.pip.phone.PipController$$ExternalSyntheticLambda10;
import com.android.wm.shell.protolog.ShellProtoLogCache;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.protolog.ShellProtoLogImpl;
import com.android.wm.shell.sysui.ShellInit;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class TabletopModeController implements DevicePostureController.OnDevicePostureChangedListener, DisplayController.OnDisplaysChangedListener {
    public static final boolean PREFER_TOP_HALF_IN_TABLETOP = SystemProperties.getBoolean("persist.wm.debug.prefer_top_half_in_tabletop", true);
    public final Context mContext;
    public final DevicePostureController mDevicePostureController;
    public final DisplayController mDisplayController;
    public Boolean mLastIsInTabletopModeForCallback;
    public final ShellExecutor mMainExecutor;
    final Runnable mOnEnterTabletopModeCallback;
    public final Set mTabletopModeRotations = new ArraySet();
    public final List mListeners = new ArrayList();
    public int mDevicePosture = 0;
    public int mDisplayRotation = -1;

    public TabletopModeController(Context context, ShellInit shellInit, DevicePostureController devicePostureController, DisplayController displayController, ShellExecutor shellExecutor) {
        final int i = 0;
        this.mOnEnterTabletopModeCallback = new Runnable(this) { // from class: com.android.wm.shell.common.TabletopModeController$$ExternalSyntheticLambda0
            public final /* synthetic */ TabletopModeController f$0;

            {
                this.f$0 = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                switch (i) {
                    case 0:
                        TabletopModeController tabletopModeController = this.f$0;
                        if (tabletopModeController.isInTabletopMode()) {
                            tabletopModeController.mayBroadcastOnTabletopModeChange(true);
                            return;
                        }
                        return;
                    default:
                        this.f$0.onInit();
                        return;
                }
            }
        };
        this.mContext = context;
        this.mDevicePostureController = devicePostureController;
        this.mDisplayController = displayController;
        this.mMainExecutor = shellExecutor;
        final int i2 = 1;
        shellInit.addInitCallback(new Runnable(this) { // from class: com.android.wm.shell.common.TabletopModeController$$ExternalSyntheticLambda0
            public final /* synthetic */ TabletopModeController f$0;

            {
                this.f$0 = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                switch (i2) {
                    case 0:
                        TabletopModeController tabletopModeController = this.f$0;
                        if (tabletopModeController.isInTabletopMode()) {
                            tabletopModeController.mayBroadcastOnTabletopModeChange(true);
                            return;
                        }
                        return;
                    default:
                        this.f$0.onInit();
                        return;
                }
            }
        }, this);
    }

    public final boolean isInTabletopMode() {
        boolean z;
        if (this.mDevicePosture == 2) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            if (((ArraySet) this.mTabletopModeRotations).contains(Integer.valueOf(this.mDisplayRotation))) {
                return true;
            }
        }
        return false;
    }

    public final void mayBroadcastOnTabletopModeChange(final boolean z) {
        Boolean bool = this.mLastIsInTabletopModeForCallback;
        if (bool == null || bool.booleanValue() != z) {
            ((ArrayList) this.mListeners).forEach(new Consumer() { // from class: com.android.wm.shell.common.TabletopModeController$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((PipController$$ExternalSyntheticLambda10) obj).onTabletopModeChanged(z);
                }
            });
            this.mLastIsInTabletopModeForCallback = Boolean.valueOf(z);
        }
    }

    public final void onDevicePostureOrDisplayRotationChanged(int i, int i2) {
        boolean isInTabletopMode = isInTabletopMode();
        this.mDevicePosture = i;
        this.mDisplayRotation = i2;
        boolean isInTabletopMode2 = isInTabletopMode();
        Runnable runnable = this.mOnEnterTabletopModeCallback;
        ShellExecutor shellExecutor = this.mMainExecutor;
        ((HandlerExecutor) shellExecutor).removeCallbacks(runnable);
        if (!isInTabletopMode && isInTabletopMode2) {
            ((HandlerExecutor) shellExecutor).executeDelayed(1000L, this.mOnEnterTabletopModeCallback);
        } else {
            mayBroadcastOnTabletopModeChange(false);
        }
    }

    @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
    public final void onDisplayConfigurationChanged(int i, Configuration configuration) {
        int displayRotation = configuration.windowConfiguration.getDisplayRotation();
        if (i == 0 && displayRotation != this.mDisplayRotation) {
            onDevicePostureOrDisplayRotationChanged(this.mDevicePosture, displayRotation);
        }
    }

    public void onInit() {
        DevicePostureController devicePostureController = this.mDevicePostureController;
        ArrayList arrayList = (ArrayList) devicePostureController.mListeners;
        if (!arrayList.contains(this)) {
            arrayList.add(this);
            int i = devicePostureController.mDevicePosture;
            if (this.mDevicePosture != i) {
                onDevicePostureOrDisplayRotationChanged(i, this.mDisplayRotation);
            }
        }
        this.mDisplayController.addDisplayWindowListener(this);
        int[] intArray = this.mContext.getResources().getIntArray(17236168);
        if (intArray != null && intArray.length != 0) {
            for (int i2 : intArray) {
                Set set = this.mTabletopModeRotations;
                if (i2 != 0) {
                    if (i2 != 90) {
                        if (i2 != 180) {
                            if (i2 != 270) {
                                if (ShellProtoLogCache.WM_SHELL_FOLDABLE_enabled) {
                                    ShellProtoLogImpl.e(ShellProtoLogGroup.WM_SHELL_FOLDABLE, 233743459, 1, null, Long.valueOf(i2));
                                }
                            } else {
                                ((ArraySet) set).add(3);
                            }
                        } else {
                            ((ArraySet) set).add(2);
                        }
                    } else {
                        ((ArraySet) set).add(1);
                    }
                } else {
                    ((ArraySet) set).add(0);
                }
            }
            return;
        }
        if (ShellProtoLogCache.WM_SHELL_FOLDABLE_enabled) {
            ShellProtoLogImpl.e(ShellProtoLogGroup.WM_SHELL_FOLDABLE, 1945591550, 0, null, null);
        }
    }
}
