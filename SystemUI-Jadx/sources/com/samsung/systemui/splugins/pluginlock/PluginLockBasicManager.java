package com.samsung.systemui.splugins.pluginlock;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes3.dex */
public interface PluginLockBasicManager {

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes3.dex */
    public interface Callback {
        void dispatchEvent(Bundle bundle);

        String getDynamicLockData();

        void goToLockedShade();

        boolean isSecure();

        void makeExpandedInvisible();

        void onLaunchTransitionFadingEnded();

        void requestDismissKeyguard(Intent intent);

        void setBiometricRecognition(boolean z);

        void setDynamicLockData(String str);

        void setLockscreenTimer(long j);

        void setPluginLockWallpaper(int i, int i2, String str);

        void setPluginWallpaper(int i, int i2, int i3, String str);

        void setPluginWallpaper(int i, int i2, int i3, String str, String str2);

        void setPluginWallpaperHints(int i, String str);

        @Deprecated
        void setRotationAllowed(boolean z);

        void setScreenOrientation(boolean z, boolean z2);

        void setTimeOut(boolean z);

        void setViewMode(int i);

        void setWallpaperHints(String str);

        void updateDynamicLockData(String str);

        void updateWindowSecureState(boolean z);

        void userActivity();
    }

    int getMode();

    int getServiceType();

    boolean hasBackupWallpaper(int i);

    void onAodTransitionEnd();

    void onDensityOrFontScaleChanged();

    void onEventReceived(Bundle bundle);

    void onFolderStateChanged(boolean z);

    void onFolderStateChanged(boolean z, boolean z2);

    void onLocaleChanged();

    void onLockWallpaperChanged(int i);

    void onPluginLockModeChanged(int i, boolean z);

    void onPluginLockModeChanged(boolean z);

    void onSemBackupStatusChanged(Bundle bundle);

    void onStartedGoingToSleep(boolean z);

    void onStartedWakingUp();

    void onWallpaperConsumed(int i, boolean z);

    void reset();

    void setAllowedNumber(int i);

    void setBarState(int i);

    void setCallback(Callback callback);

    void setPanelView(ViewGroup viewGroup);

    void setQsExpansion(float f);

    void updateWhiteWallpaperState(boolean z);
}
