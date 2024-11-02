package com.android.systemui.wallpaper;

import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import com.android.keyguard.KeyguardFaceListenModel$$ExternalSyntheticOutline0;
import com.android.systemui.LsRune;
import com.android.systemui.pluginlock.PluginWallpaperManagerImpl;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.LogUtil;
import com.android.systemui.wallpaper.view.KeyguardTransitionWallpaper;
import com.android.systemui.wallpaper.view.SystemUIWallpaperBase;
import java.util.function.Consumer;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final /* synthetic */ class KeyguardWallpaperController$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ KeyguardWallpaperController f$0;

    public /* synthetic */ KeyguardWallpaperController$$ExternalSyntheticLambda2(KeyguardWallpaperController keyguardWallpaperController, int i) {
        this.$r8$classId = i;
        this.f$0 = keyguardWallpaperController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        Consumer consumer;
        int i;
        Object obj;
        ViewGroup viewGroup;
        boolean z = false;
        boolean z2 = true;
        switch (this.$r8$classId) {
            case 0:
                KeyguardWallpaperController keyguardWallpaperController = this.f$0;
                keyguardWallpaperController.getClass();
                try {
                    Log.d("KeyguardWallpaperController", "setLockWallpaperCallback()");
                    keyguardWallpaperController.mService.setLockWallpaperCallback(keyguardWallpaperController);
                    return;
                } catch (RemoteException e) {
                    Log.e("KeyguardWallpaperController", "System dead?" + e);
                    return;
                }
            case 1:
                this.f$0.showBlurredViewIfNeededOnUiThread();
                return;
            case 2:
                ((PluginWallpaperManagerImpl) this.f$0.mPluginWallpaperManager).onLockWallpaperChanged(1);
                return;
            case 3:
                SystemUIWallpaperBase systemUIWallpaperBase = this.f$0.mWallpaperView;
                if (systemUIWallpaperBase != null) {
                    systemUIWallpaperBase.onBackDropLayoutChange();
                    return;
                }
                return;
            case 4:
                this.f$0.cleanUpBlurredViewOnUiThread();
                return;
            case 5:
                KeyguardWallpaperController keyguardWallpaperController2 = this.f$0;
                keyguardWallpaperController2.handleDlsViewMode(keyguardWallpaperController2.mDlsViewMode, true);
                return;
            case 6:
                KeyguardWallpaperController keyguardWallpaperController3 = this.f$0;
                if (keyguardWallpaperController3.mSemDisplaySolutionManager != null) {
                    Log.d("KeyguardWallpaperController", "mSemDisplaySolutionManager is called : true");
                    keyguardWallpaperController3.mSemDisplaySolutionManager.onAutoCurrentLimitStateChanged(true);
                    return;
                }
                return;
            case 7:
                KeyguardWallpaperController keyguardWallpaperController4 = this.f$0;
                SystemUIWallpaperBase systemUIWallpaperBase2 = keyguardWallpaperController4.mWallpaperView;
                if (systemUIWallpaperBase2 != null) {
                    systemUIWallpaperBase2.updateDrawState(true);
                    keyguardWallpaperController4.mWallpaperView.onResume();
                    return;
                }
                return;
            case 8:
                KeyguardWallpaperController keyguardWallpaperController5 = this.f$0;
                if (keyguardWallpaperController5.mOccluded && keyguardWallpaperController5.mBlurredView != null && keyguardWallpaperController5.mRootView.getVisibility() != 0) {
                    keyguardWallpaperController5.mRootView.setVisibility(0);
                    return;
                }
                return;
            case 9:
                KeyguardWallpaperController keyguardWallpaperController6 = this.f$0;
                SystemUIWallpaperBase systemUIWallpaperBase3 = keyguardWallpaperController6.mWallpaperView;
                if (systemUIWallpaperBase3 != null) {
                    WallpaperUtils.sDrawState = false;
                    systemUIWallpaperBase3.updateDrawState(false);
                    keyguardWallpaperController6.mWallpaperView.onUnlock();
                }
                if (keyguardWallpaperController6.mOldWallpaperView != null) {
                    Log.i("KeyguardWallpaperController", "mOldWallpaperView : " + keyguardWallpaperController6.mOldWallpaperView + " is removed");
                    keyguardWallpaperController6.mOldWallpaperView = null;
                    return;
                }
                return;
            case 10:
                KeyguardWallpaperController keyguardWallpaperController7 = this.f$0;
                keyguardWallpaperController7.getClass();
                boolean z3 = WallpaperUtils.mIsUltraPowerSavingMode;
                boolean z4 = WallpaperUtils.mIsEmergencyMode;
                boolean isLiveWallpaperEnabled = WallpaperUtils.isLiveWallpaperEnabled(KeyguardWallpaperController.isSubDisplay());
                boolean isLockScreenRotationAllowed = keyguardWallpaperController7.mSettingsHelper.isLockScreenRotationAllowed();
                boolean isVideoWallpaper = WallpaperUtils.isVideoWallpaper();
                if (!isLockScreenRotationAllowed) {
                    isVideoWallpaper = true;
                } else if (z3 || z4 || isLiveWallpaperEnabled) {
                    isVideoWallpaper = false;
                }
                StringBuilder sb = new StringBuilder("disableRotateIfNeeded: video = ");
                sb.append(WallpaperUtils.isVideoWallpaper());
                sb.append(" , rotate support = ");
                boolean z5 = LsRune.WALLPAPER_ROTATABLE_WALLPAPER;
                sb.append(z5);
                sb.append(" , sub = ");
                sb.append(KeyguardWallpaperController.isSubDisplay());
                sb.append(" , enabled = ");
                sb.append(keyguardWallpaperController7.mSettingsHelper.isLockScreenRotationAllowed());
                sb.append(" , isUpsMode = ");
                sb.append(z3);
                sb.append(" , isEmergencyMode = ");
                KeyguardFaceListenModel$$ExternalSyntheticOutline0.m(sb, z4, " , isLiveWallpaperSettingEnabled = ", isLiveWallpaperEnabled, " , isLockScreenRotationAllowed = ");
                sb.append(isLockScreenRotationAllowed);
                keyguardWallpaperController7.printLognAddHistory(sb.toString());
                if (LsRune.WALLPAPER_VIDEO_WALLPAPER && (consumer = keyguardWallpaperController7.mNoSensorConsumer) != null) {
                    if (isVideoWallpaper && (!z5 || KeyguardWallpaperController.isSubDisplay())) {
                        z = true;
                    }
                    consumer.accept(Boolean.valueOf(z));
                    return;
                }
                return;
            case 11:
                KeyguardWallpaperController keyguardWallpaperController8 = this.f$0;
                boolean isAODShown = keyguardWallpaperController8.mSettingsHelper.isAODShown();
                boolean isAODShowLockWallpaperEnabled = WallpaperUtils.isAODShowLockWallpaperEnabled();
                if (!keyguardWallpaperController8.mUpdateMonitor.mKeyguardShowing || !isAODShowLockWallpaperEnabled || keyguardWallpaperController8.mScreenOn || !isAODShown) {
                    z2 = false;
                }
                if (!keyguardWallpaperController8.mIsKeyguardShowing && !z2) {
                    i = 4;
                } else {
                    i = 0;
                }
                StringBuilder sb2 = new StringBuilder("updateVisibility: mIsKeyguardShowing=");
                KeyguardFaceListenModel$$ExternalSyntheticOutline0.m(sb2, keyguardWallpaperController8.mIsKeyguardShowing, ", showWallpaperOnAod=", z2, ", isKeyguardShowing()=");
                KeyguardFaceListenModel$$ExternalSyntheticOutline0.m(sb2, keyguardWallpaperController8.mUpdateMonitor.mKeyguardShowing, ", isAodWithWallpaperEnabled=", isAODShowLockWallpaperEnabled, ", mScreenOn=");
                sb2.append(keyguardWallpaperController8.mScreenOn);
                sb2.append(", mIsGoingToSleep=");
                KeyguardFaceListenModel$$ExternalSyntheticOutline0.m(sb2, keyguardWallpaperController8.mIsGoingToSleep, ", isAodShown=", isAODShown, ", mOccluded=");
                sb2.append(keyguardWallpaperController8.mOccluded);
                sb2.append(", isKeyguardOccluded()=");
                sb2.append(keyguardWallpaperController8.mUpdateMonitor.mKeyguardOccluded);
                sb2.append(" -> decidedVisibility=");
                sb2.append(i);
                LogUtil.i("KeyguardWallpaperController", sb2.toString(), new Object[0]);
                ViewGroup viewGroup2 = keyguardWallpaperController8.mRootView;
                if (viewGroup2 != null) {
                    viewGroup2.setVisibility(i);
                }
                if (LsRune.WALLPAPER_CAPTURED_BLUR && DeviceState.isCapturedBlurAllowed() && (obj = keyguardWallpaperController8.mWallpaperView) != null) {
                    ((View) obj).setVisibility(i);
                }
                if (keyguardWallpaperController8.mVisibility != i) {
                    if (i == 0 && keyguardWallpaperController8.mScreenOn && !keyguardWallpaperController8.mIsGoingToSleep) {
                        keyguardWallpaperController8.onResume();
                        if (keyguardWallpaperController8.mWallpaperView != null) {
                            keyguardWallpaperController8.mVisibility = i;
                            return;
                        }
                        return;
                    }
                    if (i != 0) {
                        keyguardWallpaperController8.onPause();
                        keyguardWallpaperController8.mVisibility = 4;
                        return;
                    }
                    return;
                }
                return;
            case 12:
                this.f$0.mUpdateMonitor.setBackDropViewShowing(true, true);
                return;
            case 13:
                KeyguardWallpaperController keyguardWallpaperController9 = this.f$0;
                keyguardWallpaperController9.mRunnableUpdate = null;
                if (WallpaperUtils.isAODShowLockWallpaperEnabled() && ((PluginWallpaperManagerImpl) keyguardWallpaperController9.mPluginWallpaperManager).isDynamicWallpaperEnabled() && keyguardWallpaperController9.mTransitionView != null && (viewGroup = keyguardWallpaperController9.mRootView) != null && viewGroup.getChildCount() == 1) {
                    Log.i("KeyguardWallpaperController", "handleWallpaperResourceUpdated: TransitionView is not added. Add view again.");
                    keyguardWallpaperController9.mRootView.addView((View) keyguardWallpaperController9.mTransitionView);
                }
                SystemUIWallpaperBase systemUIWallpaperBase4 = keyguardWallpaperController9.mWallpaperView;
                if (systemUIWallpaperBase4 != null) {
                    systemUIWallpaperBase4.update();
                    return;
                }
                return;
            default:
                KeyguardWallpaperController keyguardWallpaperController10 = this.f$0;
                if (keyguardWallpaperController10.mTransitionView == null) {
                    Log.d("KeyguardWallpaperController", "removeTransitionView: KeyguardTransitionWallpaper is already gone.");
                    return;
                }
                ViewGroup viewGroup3 = keyguardWallpaperController10.mRootView;
                if (viewGroup3 != null) {
                    int childCount = viewGroup3.getChildCount();
                    while (true) {
                        childCount--;
                        if (childCount >= 0) {
                            View childAt = keyguardWallpaperController10.mRootView.getChildAt(childCount);
                            if (childAt != null && (childAt instanceof KeyguardTransitionWallpaper)) {
                                try {
                                    keyguardWallpaperController10.mRootView.removeView(childAt);
                                    Log.d("KeyguardWallpaperController", "removeTransitionView: KeyguardTransitionWallpaper is removed.");
                                } catch (Throwable th) {
                                    Log.e("KeyguardWallpaperController", "removeTransitionView : e = " + th, th);
                                }
                            }
                        }
                    }
                }
                keyguardWallpaperController10.mTransitionView.cleanUp();
                keyguardWallpaperController10.mTransitionView = null;
                Log.d("KeyguardWallpaperController", "removeTransitionView: Completed!");
                return;
        }
    }
}
