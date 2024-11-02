package com.android.systemui.wallpaper;

import android.app.IWallpaperManager;
import android.app.IWallpaperManagerCallback;
import android.app.SemWallpaperColors;
import android.app.WallpaperColors;
import android.app.WallpaperManager;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.os.RemoteException;
import android.provider.Settings;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import android.util.SparseIntArray;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.picker.adapter.AbsAdapter$1$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.LsRune;
import com.android.systemui.pluginlock.PluginLockDelegateApp;
import com.android.systemui.pluginlock.PluginLockMediator;
import com.android.systemui.pluginlock.PluginLockMediatorImpl;
import com.android.systemui.pluginlock.PluginLockUtils;
import com.android.systemui.pluginlock.PluginWallpaperManager;
import com.android.systemui.pluginlock.PluginWallpaperManagerImpl;
import com.android.systemui.pluginlock.component.PluginHomeWallpaper;
import com.android.systemui.pluginlock.component.PluginWallpaperCallback;
import com.android.systemui.pluginlock.utils.BitmapUtils;
import com.android.systemui.plugins.subscreen.PluginSubScreen;
import com.android.systemui.subscreen.SubScreenManager;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.wallpaper.accessory.SmartCardController;
import com.android.systemui.wallpaper.accessory.SmartCardController$getMainHandler$1;
import com.android.systemui.wallpaper.accessory.SmartCardController$settingObserver$1;
import com.android.systemui.wallpaper.log.WallpaperLogger;
import com.android.systemui.wallpaper.utils.WhichChecker;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class CoverWallpaperController extends IWallpaperManagerCallback.Stub implements CoverWallpaper, PluginWallpaperCallback {
    public static CoverWallpaperController sInstance;
    public final Context mContext;
    public boolean mIsFbeColorSet;
    public MultiPackDispatcher mMultiPackDispatcher;
    public final PluginLockUtils mPluginLockUtils;
    public final PluginWallpaperManager mPluginWallpaperManager;
    public final SmartCardController mSmartCardController;
    public final SubScreenManager mSubScreenManager;
    public Consumer mWallpaperConsumer;
    public int mWallpaperId;
    public final WallpaperLogger mWallpaperLogger;
    public final WallpaperManager mWallpaperManager;
    public int mFirstWallpaperType = -2;
    public DelayedUpdate mDelayedUpdate = DelayedUpdate.NOT_REQUIRED;
    public final KeyguardUpdateMonitorCallback mMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.wallpaper.CoverWallpaperController.1
        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onUserSwitchComplete(int i) {
            ListPopupWindow$$ExternalSyntheticOutline0.m("onUserSwitchComplete, userId: ", i, "CoverWallpaperController");
            try {
                SmartCardController smartCardController = CoverWallpaperController.this.mSmartCardController;
                smartCardController.getClass();
                Log.d("SmartCardController", "onUserSwitchCompleted, " + i);
                smartCardController.sendUpdateState(0L, false);
            } catch (Exception e) {
                AbsAdapter$1$$ExternalSyntheticOutline0.m("onUserSwitchComplete, e: ", e, "CoverWallpaperController");
            }
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onUserSwitching(int i) {
            ListPopupWindow$$ExternalSyntheticOutline0.m("onUserSwitching, userId: ", i, "CoverWallpaperController");
            try {
                CoverWallpaperController.this.mSmartCardController.getClass();
                Log.d("SmartCardController", "onUserSwitching, " + i);
            } catch (Exception e) {
                AbsAdapter$1$$ExternalSyntheticOutline0.m("onUserSwitching, e: ", e, "CoverWallpaperController");
            }
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onUserUnlocked() {
            Log.d("CoverWallpaperController", "onUserUnlocked");
            SmartCardController smartCardController = CoverWallpaperController.this.mSmartCardController;
            smartCardController.getClass();
            Log.d("SmartCardController", "onUserUnlocked");
            if (smartCardController.mainHandler == null) {
                smartCardController.mainHandler = new SmartCardController$getMainHandler$1(smartCardController, Looper.getMainLooper());
            }
            SmartCardController$getMainHandler$1 smartCardController$getMainHandler$1 = smartCardController.mainHandler;
            smartCardController$getMainHandler$1.sendMessageDelayed(smartCardController$getMainHandler$1.obtainMessage(20230526, Boolean.FALSE), 3000L);
        }
    };

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public enum DelayedUpdate {
        NOT_REQUIRED,
        FIRST,
        NON_FIRST
    }

    public CoverWallpaperController(Context context, WallpaperManager wallpaperManager, WallpaperLogger wallpaperLogger, PluginWallpaperManager pluginWallpaperManager, PluginLockUtils pluginLockUtils, IWallpaperManager iWallpaperManager, SubScreenManager subScreenManager, SettingsHelper settingsHelper, KeyguardUpdateMonitor keyguardUpdateMonitor, WallpaperChangeNotifier wallpaperChangeNotifier) {
        sInstance = this;
        this.mIsFbeColorSet = false;
        this.mContext = context;
        this.mPluginWallpaperManager = pluginWallpaperManager;
        PluginHomeWallpaper pluginHomeWallpaper = ((PluginLockMediatorImpl) ((PluginWallpaperManagerImpl) pluginWallpaperManager).mMediator).mHomeWallpaper;
        if (pluginHomeWallpaper != null) {
            pluginHomeWallpaper.mWallpaperUpdateCallback = this;
        }
        this.mPluginLockUtils = pluginLockUtils;
        this.mWallpaperManager = wallpaperManager;
        this.mWallpaperLogger = wallpaperLogger;
        this.mSubScreenManager = subScreenManager;
        this.mSmartCardController = new SmartCardController(context, settingsHelper, keyguardUpdateMonitor, pluginWallpaperManager);
        this.mWallpaperId = wallpaperManager.getWallpaperId(getCoverWhich());
        if (LsRune.WALLPAPER_SUB_WATCHFACE && iWallpaperManager != null) {
            try {
                iWallpaperManager.setCoverWallpaperCallback(this);
            } catch (RemoteException e) {
                Log.e("CoverWallpaperController", "System dead?" + e);
            }
        }
        if (LsRune.WALLPAPER_SUPPORT_SUIT_CASE) {
            SmartCardController smartCardController = this.mSmartCardController;
            Context context2 = smartCardController.context;
            ContentResolver contentResolver = context2.getContentResolver();
            Uri uriFor = Settings.System.getUriFor("accessory_cover_uri");
            SmartCardController$settingObserver$1 smartCardController$settingObserver$1 = smartCardController.settingObserver;
            contentResolver.registerContentObserver(uriFor, false, smartCardController$settingObserver$1);
            context2.getContentResolver().registerContentObserver(Settings.Secure.getUriFor("user_setup_complete"), false, smartCardController$settingObserver$1);
            context2.getContentResolver().registerContentObserver(Settings.System.getUriFor("dls_state"), false, smartCardController$settingObserver$1);
            this.mSmartCardController.getClass();
            keyguardUpdateMonitor.registerCallback(this.mMonitorCallback);
        }
        if (this.mWallpaperManager.semGetWallpaperType(getCoverWhich()) == 3 && !MultiPackDispatcher.enableDlsIfDisabled(this.mContext)) {
            Log.e("CoverWallpaperController", "Failed to enable DLS.");
        }
    }

    public static int getCoverMode() {
        if (!LsRune.WALLPAPER_SUB_WATCHFACE) {
            if (LsRune.WALLPAPER_VIRTUAL_DISPLAY) {
                return 32;
            }
            Log.w("CoverWallpaperController", "getCoverMode, abnormal state ");
        }
        return 16;
    }

    public final int getCoverWhich() {
        if (LsRune.WALLPAPER_SUB_WATCHFACE) {
            return 17;
        }
        if (LsRune.WALLPAPER_VIRTUAL_DISPLAY) {
            return 33;
        }
        Log.w("CoverWallpaperController", "getCoverWhich, abnormal state ");
        return 1;
    }

    public final Bitmap getWallpaperBitmap(boolean z) {
        boolean z2 = true;
        if (isFbeAvailable()) {
            if (!this.mIsFbeColorSet) {
                try {
                    this.mIsFbeColorSet = true;
                    this.mWallpaperManager.semSetDLSWallpaperColors(((PluginWallpaperManagerImpl) this.mPluginWallpaperManager).getFbeSemWallpaperColors(1), getCoverWhich());
                } catch (IllegalArgumentException e) {
                    Log.e("CoverWallpaperController", "getWallpaperBitmap: " + e.getMessage());
                }
            } else {
                Log.d("CoverWallpaperController", "getWallpaperBitmap: SemWallpaperColors for FBE was already set.");
            }
            return ((PluginWallpaperManagerImpl) this.mPluginWallpaperManager).getFbeWallpaper(1, !z);
        }
        PluginWallpaperManager pluginWallpaperManager = this.mPluginWallpaperManager;
        int coverMode = getCoverMode();
        PluginHomeWallpaper pluginHomeWallpaper = ((PluginLockMediatorImpl) ((PluginWallpaperManagerImpl) pluginWallpaperManager).mMediator).mHomeWallpaper;
        if (pluginHomeWallpaper != null) {
            int key = PluginHomeWallpaper.getKey(coverMode);
            PluginHomeWallpaper.WallpaperData wallpaperData = (PluginHomeWallpaper.WallpaperData) ((HashMap) pluginHomeWallpaper.mWallpaperDataList).get(Integer.valueOf(key));
            if (wallpaperData != null) {
                String str = wallpaperData.mPath;
                Uri uri = wallpaperData.mUri;
                MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("getWallpaperBitmap() path:", str, "PluginHomeWallpaper");
                Context context = pluginHomeWallpaper.mContext;
                if (str != null) {
                    if (key != 1) {
                        z2 = false;
                    }
                    return BitmapUtils.getBitmapFromPath(context, str, z, z2);
                }
                if (uri != null) {
                    if (key != 1) {
                        z2 = false;
                    }
                    return BitmapUtils.getBitmapFromUri(context, uri, z, z2);
                }
                Log.w("PluginHomeWallpaper", "getWallpaperBitmap() no available data");
            }
        }
        return null;
    }

    public final String getWallpaperIntelligentCrop() {
        if (isFbeAvailable()) {
            return ((PluginWallpaperManagerImpl) this.mPluginWallpaperManager).getFbeWallpaperIntelligentCrop(1);
        }
        PluginWallpaperManager pluginWallpaperManager = this.mPluginWallpaperManager;
        int coverMode = getCoverMode();
        PluginHomeWallpaper pluginHomeWallpaper = ((PluginLockMediatorImpl) ((PluginWallpaperManagerImpl) pluginWallpaperManager).mMediator).mHomeWallpaper;
        if (pluginHomeWallpaper != null) {
            PluginHomeWallpaper.WallpaperData wallpaperData = (PluginHomeWallpaper.WallpaperData) ((HashMap) pluginHomeWallpaper.mWallpaperDataList).get(Integer.valueOf(PluginHomeWallpaper.getKey(coverMode)));
            if (wallpaperData != null) {
                return wallpaperData.mIntelligentCrops;
            }
        }
        return null;
    }

    public final String getWallpaperPath() {
        if (isFbeAvailable()) {
            return ((PluginWallpaperManagerImpl) this.mPluginWallpaperManager).getFbeWallpaperPath(1);
        }
        return ((PluginWallpaperManagerImpl) this.mPluginWallpaperManager).getHomeWallpaperPath(getCoverMode());
    }

    public final Rect getWallpaperRect() {
        FileInputStream fileInputStream;
        InputStreamReader inputStreamReader;
        Rect rect = null;
        if (isFbeAvailable()) {
            ((PluginWallpaperManagerImpl) this.mPluginWallpaperManager).getClass();
            try {
                fileInputStream = new FileInputStream(PluginWallpaperManagerImpl.getFbeFile(1, "rect"));
                try {
                    inputStreamReader = new InputStreamReader(fileInputStream);
                } finally {
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                try {
                    rect = Rect.unflattenFromString(bufferedReader.readLine());
                    bufferedReader.close();
                    inputStreamReader.close();
                    fileInputStream.close();
                    Log.d("PluginWallpaperManagerImpl", "getFbeWallpaperRect, rect: " + rect);
                    return rect;
                } finally {
                }
            } finally {
            }
        }
        PluginWallpaperManager pluginWallpaperManager = this.mPluginWallpaperManager;
        int coverMode = getCoverMode();
        PluginHomeWallpaper pluginHomeWallpaper = ((PluginLockMediatorImpl) ((PluginWallpaperManagerImpl) pluginWallpaperManager).mMediator).mHomeWallpaper;
        if (pluginHomeWallpaper == null) {
            return null;
        }
        PluginHomeWallpaper.WallpaperData wallpaperData = (PluginHomeWallpaper.WallpaperData) ((HashMap) pluginHomeWallpaper.mWallpaperDataList).get(Integer.valueOf(PluginHomeWallpaper.getKey(coverMode)));
        if (wallpaperData == null) {
            return null;
        }
        return wallpaperData.mRect;
    }

    public final int getWallpaperType() {
        if (isFbeAvailable()) {
            return ((PluginWallpaperManagerImpl) this.mPluginWallpaperManager).getSubFbeWallpaperType();
        }
        PluginWallpaperManager pluginWallpaperManager = this.mPluginWallpaperManager;
        int coverMode = getCoverMode();
        PluginHomeWallpaper pluginHomeWallpaper = ((PluginLockMediatorImpl) ((PluginWallpaperManagerImpl) pluginWallpaperManager).mMediator).mHomeWallpaper;
        if (pluginHomeWallpaper != null) {
            return pluginHomeWallpaper.getWallpaperType(coverMode);
        }
        return -2;
    }

    public final boolean isCoverWallpaperRequired() {
        int i;
        boolean z;
        boolean z2;
        int currentUser = KeyguardUpdateMonitor.getCurrentUser();
        boolean z3 = false;
        if (currentUser > 0) {
            ListPopupWindow$$ExternalSyntheticOutline0.m("isCoverWallpaperRequired: currentUser = ", currentUser, "CoverWallpaperController");
            return false;
        }
        PluginLockMediator pluginLockMediator = ((PluginWallpaperManagerImpl) this.mPluginWallpaperManager).mMediator;
        if (((PluginLockMediatorImpl) pluginLockMediator).mHomeWallpaper != null) {
            i = PluginHomeWallpaper.sScreenType;
        } else {
            i = 0;
        }
        if (i == 1) {
            z = true;
        } else {
            z = false;
        }
        if (((PluginLockMediatorImpl) pluginLockMediator).mHomeWallpaper.getWallpaperType(1) > 10) {
            z2 = true;
        } else {
            z2 = false;
        }
        boolean isFbeAvailable = isFbeAvailable();
        if (z2 || isFbeAvailable) {
            z3 = true;
        }
        StringBuilder m = KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("isCoverWallpaperRequired: ", z3, ", [homeWallpaperReq:", z2, ", isFbeAvailable:");
        m.append(isFbeAvailable);
        m.append(", isSubScreen:");
        m.append(z);
        m.append("]");
        Log.d("CoverWallpaperController", m.toString());
        return z3;
    }

    public final boolean isFbeAvailable() {
        boolean z;
        PluginWallpaperManagerImpl pluginWallpaperManagerImpl = (PluginWallpaperManagerImpl) this.mPluginWallpaperManager;
        int i = pluginWallpaperManagerImpl.mScreenType;
        if (pluginWallpaperManagerImpl.isFbeRequired(i) && pluginWallpaperManagerImpl.isFbeWallpaperAvailable(i)) {
            z = true;
        } else {
            z = false;
        }
        if (!z || !((PluginWallpaperManagerImpl) this.mPluginWallpaperManager).isFbeRequired(1) || !((PluginWallpaperManagerImpl) this.mPluginWallpaperManager).isFbeWallpaperAvailable(1)) {
            return false;
        }
        return true;
    }

    public final void onHomeWallpaperDestroyed() {
        int wallpaperId = this.mWallpaperManager.getWallpaperId(getCoverWhich());
        RecyclerView$$ExternalSyntheticOutline0.m(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("onHomeWallpaperDestroyed: wallpaperId = ", wallpaperId, ", mWallpaperId = "), this.mWallpaperId, "CoverWallpaperController");
        if (wallpaperId != this.mWallpaperId) {
            this.mWallpaperConsumer = null;
            this.mFirstWallpaperType = -2;
            this.mDelayedUpdate = DelayedUpdate.NOT_REQUIRED;
            PluginWallpaperManager pluginWallpaperManager = this.mPluginWallpaperManager;
            int coverMode = getCoverMode();
            PluginLockMediatorImpl pluginLockMediatorImpl = (PluginLockMediatorImpl) ((PluginWallpaperManagerImpl) pluginWallpaperManager).mMediator;
            PluginHomeWallpaper pluginHomeWallpaper = pluginLockMediatorImpl.mHomeWallpaper;
            if (pluginHomeWallpaper != null) {
                int key = PluginHomeWallpaper.getKey(coverMode);
                try {
                    PluginHomeWallpaper.WallpaperData wallpaperData = (PluginHomeWallpaper.WallpaperData) ((HashMap) pluginHomeWallpaper.mWallpaperDataList).get(Integer.valueOf(PluginHomeWallpaper.getKey(key)));
                    wallpaperData.mType = -2;
                    wallpaperData.mPath = null;
                    wallpaperData.mUri = null;
                    wallpaperData.mHints = null;
                    wallpaperData.mRect = null;
                    Log.d("PluginHomeWallpaper", "resetWallpaper()");
                    PluginWallpaperCallback pluginWallpaperCallback = pluginHomeWallpaper.mWallpaperUpdateCallback;
                    if (pluginWallpaperCallback != null) {
                        pluginWallpaperCallback.onWallpaperHintUpdate(null);
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                PluginLockDelegateApp pluginLockDelegateApp = pluginLockMediatorImpl.mBasicListener;
                if (pluginLockDelegateApp != null) {
                    pluginLockDelegateApp.onWallpaperChanged(key);
                }
            }
        }
    }

    @Override // com.android.systemui.pluginlock.component.PluginWallpaperCallback
    public final void onReady() {
        Log.d("CoverWallpaperController", "onReady");
        Consumer consumer = this.mWallpaperConsumer;
        if (consumer != null) {
            consumer.accept(Boolean.FALSE);
        }
    }

    public final void onSemWallpaperChanged(int i, int i2, Bundle bundle) {
        Log.d("CoverWallpaperController", "onSemWallpaperChanged: type = " + i + ", which = " + i2);
        if (WhichChecker.isWatchFace(i2)) {
            PluginSubScreen pluginSubScreen = this.mSubScreenManager.mSubScreenPlugin;
            if (pluginSubScreen == null) {
                Log.w("SubScreenManager", "onSemWallpaperChanged() no plugin");
            } else {
                pluginSubScreen.onSemWallpaperChanged(null);
            }
        }
    }

    @Override // com.android.systemui.pluginlock.component.PluginWallpaperCallback
    public final void onWallpaperHintUpdate(SemWallpaperColors semWallpaperColors) {
        if (semWallpaperColors != null && (semWallpaperColors.getWhich() & 2) != 0) {
            Log.d("CoverWallpaperController", "onWallpaperHintUpdate: invalid which. which = " + semWallpaperColors.getWhich());
            return;
        }
        this.mWallpaperManager.semSetDLSWallpaperColors(semWallpaperColors, getCoverWhich());
    }

    @Override // com.android.systemui.pluginlock.component.PluginWallpaperCallback
    public final void onWallpaperUpdate(boolean z) {
        DelayedUpdate delayedUpdate;
        Log.d("CoverWallpaperController", "onWallpaperUpdate:" + this.mWallpaperConsumer + ", isFirst:" + z);
        Consumer consumer = this.mWallpaperConsumer;
        if (consumer != null) {
            consumer.accept(Boolean.valueOf(z));
            this.mDelayedUpdate = DelayedUpdate.NOT_REQUIRED;
        } else {
            if (z) {
                delayedUpdate = DelayedUpdate.FIRST;
            } else {
                delayedUpdate = DelayedUpdate.NON_FIRST;
            }
            this.mDelayedUpdate = delayedUpdate;
        }
        if (z && WhichChecker.isWatchFace(getCoverWhich())) {
            PluginSubScreen pluginSubScreen = this.mSubScreenManager.mSubScreenPlugin;
            if (pluginSubScreen == null) {
                Log.w("SubScreenManager", "onSemWallpaperChanged() no plugin");
            } else {
                pluginSubScreen.onSemWallpaperChanged(null);
            }
        }
    }

    public final void setWallpaperUpdateConsumer(Consumer consumer) {
        DelayedUpdate delayedUpdate;
        DelayedUpdate delayedUpdate2;
        boolean z;
        Log.d("CoverWallpaperController", "setWallpaperUpdateConsumer: consumer = " + consumer);
        this.mWallpaperConsumer = consumer;
        if (consumer != null && (delayedUpdate = this.mDelayedUpdate) != (delayedUpdate2 = DelayedUpdate.NOT_REQUIRED)) {
            if (delayedUpdate == DelayedUpdate.FIRST) {
                z = true;
            } else {
                z = false;
            }
            Log.d("CoverWallpaperController", "setWallpaperUpdateConsumer, delayed: " + this.mDelayedUpdate);
            this.mWallpaperConsumer.accept(Boolean.valueOf(z));
            this.mDelayedUpdate = delayedUpdate2;
        }
    }

    public final void startMultiPack(int i) {
        String group;
        if (this.mMultiPackDispatcher == null) {
            this.mMultiPackDispatcher = new MultiPackDispatcher(this.mContext, this.mWallpaperLogger, this.mPluginLockUtils);
        }
        if (this.mMultiPackDispatcher.startMultipack(i)) {
            MultiPackDispatcher multiPackDispatcher = this.mMultiPackDispatcher;
            multiPackDispatcher.getClass();
            SparseIntArray sparseIntArray = new SparseIntArray();
            try {
                Uri semGetUri = WallpaperManager.getInstance(multiPackDispatcher.mContext).semGetUri(i);
                File[] listFiles = new File("/data/overlays/homewallpaper/" + (semGetUri.getHost() + semGetUri.getPath())).listFiles();
                if (listFiles != null && listFiles.length > 0) {
                    for (File file : listFiles) {
                        String name = file.getName();
                        if (Pattern.matches("wallpaper_[\\d][.][\\w]+", name)) {
                            Matcher matcher = Pattern.compile("wallpaper_[\\d][.]").matcher(name);
                            if (matcher.find() && (group = matcher.group(0)) != null) {
                                sparseIntArray.append(Integer.parseInt(group.replaceAll("[^0-9]", "")), MultiPackDispatcher.getContentType(i, name));
                            }
                        }
                    }
                }
            } catch (IndexOutOfBoundsException | NullPointerException | NumberFormatException e) {
                e.printStackTrace();
            }
            this.mFirstWallpaperType = sparseIntArray.get(0);
        }
    }

    public final void onSemMultipackApplied(int i) {
    }

    @Override // com.android.systemui.pluginlock.component.PluginWallpaperCallback
    public final void onDataCleared() {
    }

    public final void onWallpaperChanged() {
    }

    public final void onSemWallpaperColorsAnalysisRequested(int i, int i2) {
    }

    public final void onSemBackupStatusChanged(int i, int i2, int i3) {
    }

    public final void onSemWallpaperColorsChanged(SemWallpaperColors semWallpaperColors, int i, int i2) {
    }

    public final void onWallpaperColorsChanged(WallpaperColors wallpaperColors, int i, int i2) {
    }
}
