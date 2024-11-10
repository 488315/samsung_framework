package com.android.server.enterprise.lso;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Binder;
import android.os.IBinder;
import android.os.ParcelFileDescriptor;
import android.os.Process;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.sec.enterprise.auditlog.AuditLog;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.WindowManager;
import com.android.server.enterprise.EnterpriseServiceCallback;
import com.android.server.enterprise.adapterlayer.SystemUIAdapter;
import com.android.server.enterprise.utils.Utils;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.lockscreen.ILockscreenOverlay;
import com.samsung.android.knox.lockscreen.LSOAttributeSet;
import com.samsung.android.knox.lockscreen.LSOConstants;
import com.samsung.android.knox.lockscreen.LSOItemContainer;
import com.samsung.android.knox.lockscreen.LSOItemData;
import com.samsung.android.knox.lockscreen.LSOItemImage;
import com.samsung.android.knox.lockscreen.LSOUtils;
import java.io.File;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;

/* loaded from: classes2.dex */
public class LSOService extends ILockscreenOverlay.Stub implements EnterpriseServiceCallback {
    public static final boolean DEBUG = "eng".equals(SystemProperties.get("ro.build.type"));
    public LSOAttributeSet lsoPref;
    public final Context mContext;
    public LSOItemData[] mItemData;
    public int mOverlayAdminUid;
    public int mWallpaperAdminUid;
    public Point screenDimesions;
    public final LSOStorageProvider storageProvider;
    public EnterpriseDeviceManager mEDM = null;
    public final BroadcastReceiver mReceiver = new BroadcastReceiver() { // from class: com.android.server.enterprise.lso.LSOService.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if ("com.samsung.android.knox.intent.action.KNOXFRAMEWORK_SYSTEMUI_UPDATE_INTENT_INTERNAL".equals(intent.getAction())) {
                LSOService.this.updateSystemUIMonitor(intent.getIntExtra("com.samsung.android.knox.intent.extra.USER_ID_INTERNAL", 0));
            }
        }
    };

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void notifyToAddSystemService(String str, IBinder iBinder) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void onAdminAdded(int i) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void systemReady() {
    }

    public LSOService(Context context) {
        this.mContext = context;
        LSOStorageProvider lSOStorageProvider = new LSOStorageProvider(context);
        this.storageProvider = lSOStorageProvider;
        this.mOverlayAdminUid = lSOStorageProvider.getOverlayAdminUid();
        this.mWallpaperAdminUid = lSOStorageProvider.getWallpaperAdminUid();
        if (this.mOverlayAdminUid != -1) {
            this.lsoPref = lSOStorageProvider.getAdminPref();
        }
        this.screenDimesions = new Point();
        ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getSize(this.screenDimesions);
        Point point = this.screenDimesions;
        int i = point.x;
        int i2 = point.y;
        if (i > i2) {
            point.y = i;
        } else {
            point.x = i2;
        }
        this.mItemData = new LSOItemData[4];
        for (int i3 = 1; i3 <= 3; i3++) {
            this.mItemData[i3] = this.storageProvider.getOverlay(i3);
        }
        this.mContext.registerReceiver(this.mReceiver, new IntentFilter("com.samsung.android.knox.intent.action.KNOXFRAMEWORK_SYSTEMUI_UPDATE_INTENT_INTERNAL"));
    }

    public synchronized int setData(ContextInfo contextInfo, LSOItemData lSOItemData, int i) {
        Log.d("LSOService", "setData() " + LSOConstants.getLayerName(i));
        ContextInfo enforceOwnerOnlyPermission = enforceOwnerOnlyPermission(contextInfo);
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceOwnerOnlyPermission);
        int i2 = enforceOwnerOnlyPermission.mCallerUid;
        try {
            if (i < 1 || i > 3) {
                throw new InvalidParameterException("Invalid layer. Layer must be 1...3");
            }
            if (!canConfigure(enforceOwnerOnlyPermission, 1)) {
                Log.i("LSOService", "Lockscreen is configured by another admin. Overwrite not allowed.");
                return -1;
            }
            TelephonyManager telephonyManager = (TelephonyManager) this.mContext.getSystemService("phone");
            if (i == 3 && (telephonyManager == null || !telephonyManager.isVoiceCapable())) {
                Log.i("LSOService", "setData() failed because telephony is not supported.");
                return -3;
            }
            if (!copyFiles(i2, lSOItemData, i)) {
                return -4;
            }
            if (!this.storageProvider.setOverlayData(i2, lSOItemData, i, this.lsoPref)) {
                int overlayAdminUid = this.storageProvider.getOverlayAdminUid();
                this.mOverlayAdminUid = overlayAdminUid;
                this.mItemData[i] = null;
                if (overlayAdminUid == -1) {
                    this.lsoPref = null;
                }
                deleteFiles(i);
                return -4;
            }
            this.mOverlayAdminUid = i2;
            this.mItemData[i] = lSOItemData;
            lSOItemData.closeFileDescriptor();
            sendConfigChangeNotification(callingOrCurrentUserId);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                AuditLog.logAsUser(5, 1, true, Process.myPid(), "LSOService", String.format("Admin %d has changed banner settings.", Integer.valueOf(enforceOwnerOnlyPermission.mCallerUid)), callingOrCurrentUserId);
                return 0;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        } finally {
            lSOItemData.closeFileDescriptor();
        }
    }

    public synchronized LSOItemData getData(ContextInfo contextInfo, int i) {
        Log.d("LSOService", "getData() " + LSOConstants.getLayerName(i));
        if (i < 1 || i > 3) {
            throw new InvalidParameterException("Invalid layer. Layer must be 1...3");
        }
        if (DEBUG) {
            Log.d("LSOService", "getData() - " + this.mItemData[i]);
        }
        return this.mItemData[i];
    }

    public void resetData(ContextInfo contextInfo, int i) {
        Log.d("LSOService", "resetData() - " + LSOConstants.getLayerName(i));
        ContextInfo enforceOwnerOnlyPermission = enforceOwnerOnlyPermission(contextInfo);
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceOwnerOnlyPermission);
        if (i < 0 || i > 3) {
            throw new InvalidParameterException("Invalid layer. Layer must be 0...3 but requseted " + i);
        }
        boolean z = true;
        if (!canConfigure(enforceOwnerOnlyPermission, 1)) {
            Log.e("LSOService", "resetData() : Not allowed to reset Overlay");
            return;
        }
        if (enforceOwnerOnlyPermission.mCallerUid == this.mOverlayAdminUid) {
            deleteFiles(i);
            this.storageProvider.resetOverlayData(i);
            int overlayAdminUid = this.storageProvider.getOverlayAdminUid();
            this.mOverlayAdminUid = overlayAdminUid;
            if (overlayAdminUid == -1) {
                this.lsoPref = null;
            }
            if (i == 0) {
                for (int i2 = 1; i2 <= 3; i2++) {
                    this.mItemData[i2] = null;
                }
            } else {
                this.mItemData[i] = null;
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                try {
                    AuditLog.logAsUser(5, 1, true, Process.myPid(), "LSOService", String.format("Admin %d has reset banner settings.", Integer.valueOf(enforceOwnerOnlyPermission.mCallerUid)), callingOrCurrentUserId);
                } catch (Exception e) {
                    if (DEBUG) {
                        e.printStackTrace();
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        } else {
            Log.e("LSOService", "resetData() : requested uid is diffren with present admin = " + this.mOverlayAdminUid + " but " + enforceOwnerOnlyPermission.mCallerUid);
            z = false;
        }
        if (z) {
            sendConfigChangeNotification(callingOrCurrentUserId);
        }
    }

    public void resetWallpaper(ContextInfo contextInfo) {
        Log.d("LSOService", "resetWallpaper()");
        ContextInfo enforceOwnerOnlyPermission = enforceOwnerOnlyPermission(contextInfo);
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceOwnerOnlyPermission);
        if (!canConfigure(enforceOwnerOnlyPermission, 2)) {
            Log.e("LSOService", "Not allowed to reset Wallpaper");
            return;
        }
        boolean z = false;
        if (enforceOwnerOnlyPermission.mCallerUid == this.mWallpaperAdminUid) {
            this.storageProvider.resetWallpaperData();
            deleteWallpaperFiles();
            this.mWallpaperAdminUid = -1;
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                AuditLog.logAsUser(5, 1, true, Process.myPid(), "LSOService", String.format("Admin %d has reset banner wallpaper.", Integer.valueOf(enforceOwnerOnlyPermission.mCallerUid)), callingOrCurrentUserId);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                z = true;
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
        if (z) {
            sendConfigChangeNotification(callingOrCurrentUserId);
        }
    }

    public boolean isConfigured(ContextInfo contextInfo, int i) {
        boolean z = true;
        int i2 = ((-1 == i || i == 0 || 1 == i) && this.mOverlayAdminUid != -1) ? 1 : 0;
        if ((-1 == i || i == 0 || 2 == i) && this.mWallpaperAdminUid != -1) {
            i2++;
        }
        if (-1 != i ? i2 <= 0 : i2 < 2) {
            z = false;
        }
        Log.d("LSOService", "isConfigured(" + LSOConstants.getLSOFeatureName(i) + ") - " + z);
        return z;
    }

    /* JADX WARN: Can't wrap try/catch for region: R(13:1|(2:2|3)|(2:26|(5:29|(3:18|19|(1:22))|(1:13)|14|15))|7|(0)|18|19|(0)|22|(1:13)(1:13)|14|15|(1:(0))) */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x002b, code lost:
    
        r7 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x002f, code lost:
    
        android.util.Log.e("LSOService", "canConfigure() Unhandled exception." + r7);
     */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0045 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:17:0x004a A[ADDED_TO_REGION] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean canConfigure(com.samsung.android.knox.ContextInfo r8, int r9) {
        /*
            r7 = this;
            java.lang.String r0 = "LSOService"
            r1 = 2
            r2 = 1
            r3 = -1
            r4 = 0
            com.samsung.android.knox.ContextInfo r8 = r7.enforceOwnerOnlyPermission(r8)     // Catch: java.lang.Exception -> L2d
            int r8 = r8.mCallerUid     // Catch: java.lang.Exception -> L2d
            if (r3 == r9) goto L12
            if (r9 == 0) goto L12
            if (r2 != r9) goto L19
        L12:
            int r5 = r7.mOverlayAdminUid     // Catch: java.lang.Exception -> L2d
            if (r5 == r3) goto L1b
            if (r5 != r8) goto L19
            goto L1b
        L19:
            r5 = r4
            goto L1c
        L1b:
            r5 = r2
        L1c:
            if (r3 == r9) goto L22
            if (r9 == 0) goto L22
            if (r1 != r9) goto L43
        L22:
            int r7 = r7.mWallpaperAdminUid     // Catch: java.lang.Exception -> L2b
            if (r7 == r3) goto L28
            if (r7 != r8) goto L43
        L28:
            int r5 = r5 + 1
            goto L43
        L2b:
            r7 = move-exception
            goto L2f
        L2d:
            r7 = move-exception
            r5 = r4
        L2f:
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r6 = "canConfigure() Unhandled exception."
            r8.append(r6)
            r8.append(r7)
            java.lang.String r7 = r8.toString()
            android.util.Log.e(r0, r7)
        L43:
            if (r3 != r9) goto L4a
            if (r5 < r1) goto L48
            goto L4c
        L48:
            r2 = r4
            goto L4c
        L4a:
            if (r5 <= 0) goto L48
        L4c:
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r8 = "canConfigure("
            r7.append(r8)
            java.lang.String r8 = com.samsung.android.knox.lockscreen.LSOConstants.getLSOFeatureName(r9)
            r7.append(r8)
            java.lang.String r8 = ") - "
            r7.append(r8)
            r7.append(r2)
            java.lang.String r7 = r7.toString()
            android.util.Log.d(r0, r7)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.lso.LSOService.canConfigure(com.samsung.android.knox.ContextInfo, int):boolean");
    }

    public int setWallpaper(ContextInfo contextInfo, String str, ParcelFileDescriptor parcelFileDescriptor) {
        boolean z;
        Log.d("LSOService", "setWallpaper(" + str + ")");
        ContextInfo enforceOwnerOnlyPermission = enforceOwnerOnlyPermission(contextInfo);
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceOwnerOnlyPermission);
        int i = enforceOwnerOnlyPermission.mCallerUid;
        if (!canConfigure(enforceOwnerOnlyPermission, 2)) {
            Log.d("LSOService", "setWallpaper() : Lockscreen is configured by another admin. Overwrite not allowed.");
            return -1;
        }
        if (str == null || parcelFileDescriptor == null) {
            Log.d("LSOService", "setWallpaper() : imageFilePath or image is null, please check path");
            return -4;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        String copyFileFromParcel = copyFileFromParcel(parcelFileDescriptor, str);
        if (copyFileFromParcel == null) {
            Log.e("LSOService", "file not created");
            return -4;
        }
        try {
            try {
            } catch (Exception e) {
                Log.d("LSOService", "setWallpaper() : error occurs", e);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                z = false;
            }
            if (!new File("/data/data/com.sec.android.gallery3d").exists()) {
                Log.e("LSOService", "/data/data/com.sec.android.gallery3d does not exists");
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -3;
            }
            if (!LSOUtils.mkDir("/data/system/enterprise")) {
                return -4;
            }
            if (!LSOUtils.mkDir("/data/system/enterprise/lso")) {
                return -4;
            }
            this.storageProvider.resetWallpaperData();
            z = LSOUtils.convertImageFormat(copyFileFromParcel, Bitmap.CompressFormat.JPEG, "/data/system/enterprise/lso/lockscreen_wallpaper.jpg", this.screenDimesions);
            if (z) {
                Log.d("LSOService", "setWallpaper() : Create Ripple image");
                z = LSOUtils.createRippleImage("/data/system/enterprise/lso/lockscreen_wallpaper.jpg", Bitmap.CompressFormat.JPEG, "/data/system/enterprise/lso/lockscreen_wallpaper_ripple.jpg");
            }
            if (!z) {
                Log.e("LSOService", "setWallpaper() : Error in copying file");
                LSOUtils.deleteFile(copyFileFromParcel);
                return -4;
            }
            if (!this.storageProvider.setWallpaperData(i, copyFileFromParcel)) {
                Log.e("LSOService", "setWallpaper() : Failed to set wallpaper data. ");
                this.mWallpaperAdminUid = -1;
                LSOUtils.deleteFile(copyFileFromParcel);
                deleteWallpaperFiles();
                return -4;
            }
            this.mWallpaperAdminUid = i;
            sendConfigChangeNotification(callingOrCurrentUserId);
            clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                AuditLog.logAsUser(5, 1, true, Process.myPid(), "LSOService", String.format("Admin %d has changed banner wallpaper to file %s", Integer.valueOf(enforceOwnerOnlyPermission.mCallerUid), str), callingOrCurrentUserId);
            } catch (Exception unused) {
            } catch (Throwable th) {
                throw th;
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            LSOUtils.deleteFile(copyFileFromParcel);
            return 0;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public int setPreferences(ContextInfo contextInfo, LSOAttributeSet lSOAttributeSet) {
        ContextInfo enforceOwnerOnlyPermission = enforceOwnerOnlyPermission(contextInfo);
        int i = this.mOverlayAdminUid;
        if (i == -1) {
            Log.i("LSOService", "setPreferences() : There is no configured data from admin. ");
            return -6;
        }
        if (i != enforceOwnerOnlyPermission.mCallerUid) {
            Log.i("LSOService", "setPreferences() : Lockscreen is configured by another admin. Overwrite not allowed.");
            return -1;
        }
        LSOItemData[] lSOItemDataArr = this.mItemData;
        if (lSOItemDataArr[1] == null && lSOItemDataArr[2] == null) {
            Log.i("LSOService", "setPreferences() : layer doesn't configure so cannot set pref.");
            return -6;
        }
        boolean updateAdminPref = this.storageProvider.updateAdminPref(lSOAttributeSet);
        sendConfigChangeNotification(Utils.getCallingOrCurrentUserId(enforceOwnerOnlyPermission));
        if (updateAdminPref) {
            this.lsoPref = lSOAttributeSet;
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                AuditLog.logAsUser(5, 1, true, Process.myPid(), "LSOService", String.format("Admin %d has changed banner settings.", Integer.valueOf(enforceOwnerOnlyPermission.mCallerUid)), UserHandle.getUserId(enforceOwnerOnlyPermission.mCallerUid));
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        return updateAdminPref ? 0 : -4;
    }

    public LSOAttributeSet getPreferences(ContextInfo contextInfo) {
        if (DEBUG) {
            Log.d("LSOService", "getPreferences() - lsoPref = " + this.lsoPref);
        }
        return this.lsoPref;
    }

    public final EnterpriseDeviceManager getEDM() {
        if (this.mEDM == null) {
            this.mEDM = EnterpriseDeviceManager.getInstance(this.mContext);
        }
        return this.mEDM;
    }

    public final ContextInfo enforceOwnerOnlyPermission(ContextInfo contextInfo) {
        return getEDM().enforceOwnerOnlyAndActiveAdminPermission(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_LOCKSCREEN")));
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void onPreAdminRemoval(int i) {
        if (i == this.mOverlayAdminUid) {
            deleteFiles(0);
            this.storageProvider.resetOverlayData(0);
            this.mOverlayAdminUid = -1;
            this.lsoPref = null;
            for (int i2 = 1; i2 <= 3; i2++) {
                this.mItemData[i2] = null;
            }
        }
        if (i == this.mWallpaperAdminUid) {
            deleteWallpaperFiles();
            this.storageProvider.resetWallpaperData();
            this.mWallpaperAdminUid = -1;
        }
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void onAdminRemoved(int i) {
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(new ContextInfo(i, 0));
        if (callingOrCurrentUserId == ActivityManager.getCurrentUser()) {
            updateSystemUIMonitor(callingOrCurrentUserId);
        }
    }

    public final void sendConfigChangeNotification(int i) {
        boolean isConfigured = isConfigured(null, 2);
        int i2 = 1;
        boolean isConfigured2 = isConfigured(null, 1);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                Intent intent = new Intent("com.samsung.android.knox.intent.action.LSO_CONFIG_CHANGED_INTERNAL");
                if (!isConfigured2) {
                    i2 = 0;
                }
                intent.putExtra("com.samsung.android.knox.intent.extra.KNOX_WALLPAPER_ENABLED_INTERNAL", i2);
                this.mContext.sendBroadcastAsUser(intent, UserHandle.ALL, null);
                Log.d("LSOService", "sendConfigChangeNotification() - done");
            } catch (Exception e) {
                Log.e("LSOService", "sendConfigChangeNotification() : failed to send intent.", e);
            }
            try {
                setLockscreenInvisibleOverlaySystemUI(i, isConfigured2);
                setLockscreenWallpaperSystemUI(i, isConfigured);
            } catch (Exception e2) {
                Log.e("LSOService", "sendConfigChangeNotification() : failed to update system ui.", e2);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void deleteWallpaperFiles() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                LSOUtils.deleteFile("/data/system/enterprise/lso/lockscreen_wallpaper.jpg");
                LSOUtils.deleteFile("/data/system/enterprise/lso/lockscreen_wallpaper_ripple.jpg");
            } catch (Exception e) {
                Log.e("LSOService", "deleteWallpaperFiles() : failed but ignore this error");
                if (DEBUG) {
                    e.printStackTrace();
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final synchronized void deleteFiles(int i) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                if (i == 0) {
                    for (int i2 = 1; i2 <= 3; i2++) {
                        LSOUtils.deleteRecursive(new File("/data/system/enterprise/lso/" + i2));
                    }
                } else {
                    LSOUtils.deleteRecursive(new File("/data/system/enterprise/lso/" + i));
                }
            } catch (Exception e) {
                Log.e("LSOService", "deleteFiles() : failed layer - " + LSOConstants.getLayerName(i), e);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public synchronized boolean copyFiles(int i, LSOItemData lSOItemData, int i2) {
        String str = "/data/system/enterprise/lso/" + i2;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean z = false;
        try {
            try {
                deleteFiles(i2);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        } catch (Exception e) {
            Log.e("LSOService", "copyFiles1() error occurs. ", e);
        }
        if (!LSOUtils.mkDir("/data/system/enterprise")) {
            return false;
        }
        if (!LSOUtils.mkDir("/data/system/enterprise/lso")) {
            return false;
        }
        if (!LSOUtils.mkDir(str)) {
            return false;
        }
        copyFiles(lSOItemData, str + "/" + i, 0);
        Binder.restoreCallingIdentity(clearCallingIdentity);
        z = true;
        if (!z) {
            deleteFiles(i2);
        }
        Log.d("LSOService", "copyFiles1() " + LSOConstants.getLayerName(i2) + "result = " + z);
        return z;
    }

    public synchronized boolean copyFiles(LSOItemData lSOItemData, String str, int i) {
        LSOAttributeSet attrs;
        boolean z = true;
        if (lSOItemData == null) {
            return true;
        }
        boolean z2 = false;
        Integer num = 0;
        try {
            byte type = lSOItemData.getType();
            if (type == 3) {
                LSOItemImage lSOItemImage = (LSOItemImage) lSOItemData;
                if (lSOItemImage.getImagePath() != null) {
                    String copyFileFromParcel = copyFileFromParcel(lSOItemImage.getFileDescriptor(), lSOItemImage.getImagePath());
                    if (copyFileFromParcel == null) {
                        Log.e("LSOService", "copyFiles2 - LSO_ITEM_TYPE_IMAGE : file no created");
                        return false;
                    }
                    String copyFile = copyFile(copyFileFromParcel, str, i, num.intValue());
                    LSOUtils.deleteFile(copyFileFromParcel);
                    if (copyFile != null) {
                        lSOItemImage.setImagePath(copyFile);
                    }
                    z = false;
                }
            } else if (type == 4) {
                LSOItemContainer lSOItemContainer = (LSOItemContainer) lSOItemData;
                if (lSOItemContainer.getBGImagePath() != null) {
                    String copyFileFromParcel2 = copyFileFromParcel(lSOItemContainer.getFileDescriptor(), lSOItemContainer.getBGImagePath());
                    if (copyFileFromParcel2 == null) {
                        Log.e("LSOService", "copyFiles2 - LSO_ITEM_TYPE_CONTAINER : file no created");
                        return false;
                    }
                    String copyFile2 = copyFile(copyFileFromParcel2, str, i, num.intValue());
                    LSOUtils.deleteFile(copyFileFromParcel2);
                    if (copyFile2 == null) {
                        z = false;
                    } else {
                        lSOItemContainer.setBGImage(copyFile2);
                    }
                }
                for (int i2 = 0; i2 < lSOItemContainer.getNumItems() && z; i2++) {
                    z = copyFiles(lSOItemContainer.getItem(i2), str, i + 1);
                }
            }
        } catch (Exception e) {
            Log.e("LSOService", "copyFiles2() - failed. ", e);
        }
        if (z && (attrs = lSOItemData.getAttrs()) != null && attrs.containsKey("android:src")) {
            String copyFileFromParcel3 = copyFileFromParcel(lSOItemData.getFileDescriptor(), attrs.getAsString("android:src"));
            if (copyFileFromParcel3 == null) {
                Log.e("LSOService", "copyFiles2 - attrSet ATTR_IMAGE_SRC : file no created");
                return false;
            }
            String copyFile3 = copyFile(copyFileFromParcel3, str, i, num.intValue());
            LSOUtils.deleteFile(copyFileFromParcel3);
            if (copyFile3 == null) {
                Log.d("LSOService", "copyFiles2() - " + str + ", ret =" + z2);
                return z2;
            }
            lSOItemData.setAttribute("android:src", copyFile3);
        }
        z2 = z;
        Log.d("LSOService", "copyFiles2() - " + str + ", ret =" + z2);
        return z2;
    }

    public synchronized String copyFile(String str, String str2, int i, int i2) {
        return LSOUtils.copyFile(str, String.format("%s_%s%s_%s", str2, String.valueOf(i), String.valueOf(i2), new File(str).getName()));
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0095 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:57:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:58:0x008c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r4v1, types: [java.io.File] */
    /* JADX WARN: Type inference failed for: r4v2 */
    /* JADX WARN: Type inference failed for: r4v3 */
    /* JADX WARN: Type inference failed for: r4v4, types: [java.io.FileInputStream] */
    /* JADX WARN: Type inference failed for: r4v5, types: [java.io.FileInputStream] */
    /* JADX WARN: Type inference failed for: r8v1 */
    /* JADX WARN: Type inference failed for: r8v10 */
    /* JADX WARN: Type inference failed for: r8v3, types: [java.io.FileOutputStream] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.String copyFileFromParcel(android.os.ParcelFileDescriptor r8, java.lang.String r9) {
        /*
            r7 = this;
            java.lang.String r7 = "failed to close outputstream"
            java.lang.String r0 = "failed to close inputstream"
            java.lang.String r1 = "LSOService"
            java.lang.String r2 = "/data/system/enterprise"
            boolean r2 = com.samsung.android.knox.lockscreen.LSOUtils.mkDir(r2)
            r3 = 0
            if (r2 != 0) goto L10
            return r3
        L10:
            java.lang.String r2 = "/data/system/enterprise/temp"
            boolean r2 = com.samsung.android.knox.lockscreen.LSOUtils.mkDir(r2)
            if (r2 != 0) goto L19
            return r3
        L19:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r4 = "/data/system/enterprise/temp/"
            r2.append(r4)
            java.io.File r4 = new java.io.File
            r4.<init>(r9)
            java.lang.String r9 = r4.getName()
            r2.append(r9)
            java.lang.String r9 = r2.toString()
            r2 = 1024(0x400, float:1.435E-42)
            byte[] r2 = new byte[r2]
            java.io.FileInputStream r4 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L6a java.lang.Exception -> L6d
            java.io.FileDescriptor r8 = r8.getFileDescriptor()     // Catch: java.lang.Throwable -> L6a java.lang.Exception -> L6d
            r4.<init>(r8)     // Catch: java.lang.Throwable -> L6a java.lang.Exception -> L6d
            java.io.FileOutputStream r8 = new java.io.FileOutputStream     // Catch: java.lang.Throwable -> L64 java.lang.Exception -> L67
            r8.<init>(r9)     // Catch: java.lang.Throwable -> L64 java.lang.Exception -> L67
        L45:
            int r5 = r4.read(r2)     // Catch: java.lang.Exception -> L62 java.lang.Throwable -> L88
            if (r5 <= 0) goto L50
            r6 = 0
            r8.write(r2, r6, r5)     // Catch: java.lang.Exception -> L62 java.lang.Throwable -> L88
            goto L45
        L50:
            r8.flush()     // Catch: java.lang.Exception -> L62 java.lang.Throwable -> L88
            r4.close()     // Catch: java.io.IOException -> L57
            goto L5a
        L57:
            android.util.Log.e(r1, r0)
        L5a:
            r8.close()     // Catch: java.io.IOException -> L5e
            goto L61
        L5e:
            android.util.Log.e(r1, r7)
        L61:
            return r9
        L62:
            r9 = move-exception
            goto L70
        L64:
            r9 = move-exception
            r8 = r3
            goto L89
        L67:
            r9 = move-exception
            r8 = r3
            goto L70
        L6a:
            r9 = move-exception
            r8 = r3
            goto L8a
        L6d:
            r9 = move-exception
            r8 = r3
            r4 = r8
        L70:
            java.lang.String r2 = "copyFileFromParcel() : failed to copy image from parcel "
            android.util.Log.e(r1, r2, r9)     // Catch: java.lang.Throwable -> L88
            if (r4 == 0) goto L7e
            r4.close()     // Catch: java.io.IOException -> L7b
            goto L7e
        L7b:
            android.util.Log.e(r1, r0)
        L7e:
            if (r8 == 0) goto L87
            r8.close()     // Catch: java.io.IOException -> L84
            goto L87
        L84:
            android.util.Log.e(r1, r7)
        L87:
            return r3
        L88:
            r9 = move-exception
        L89:
            r3 = r4
        L8a:
            if (r3 == 0) goto L93
            r3.close()     // Catch: java.io.IOException -> L90
            goto L93
        L90:
            android.util.Log.e(r1, r0)
        L93:
            if (r8 == 0) goto L9c
            r8.close()     // Catch: java.io.IOException -> L99
            goto L9c
        L99:
            android.util.Log.e(r1, r7)
        L9c:
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.lso.LSOService.copyFileFromParcel(android.os.ParcelFileDescriptor, java.lang.String):java.lang.String");
    }

    public final void updateSystemUIMonitor(int i) {
        setLockscreenInvisibleOverlaySystemUI(i, isConfigured(null, 1));
        setLockscreenWallpaperSystemUI(i, isConfigured(null, 2));
    }

    public final void setLockscreenInvisibleOverlaySystemUI(int i, boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                SystemUIAdapter.getInstance(this.mContext).setLockscreenInvisibleOverlayAsUser(i, z);
            } catch (Exception e) {
                Log.e("LSOService", "setLockscreenInvisibleOverlaySystemUI() failed. ", e);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void setLockscreenWallpaperSystemUI(int i, boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                SystemUIAdapter.getInstance(this.mContext).setLockscreenWallpaperAsUser(i, z);
            } catch (Exception e) {
                Log.e("LSOService", "setLockscreenWallpaperSystemUI() failed. ", e);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.DUMP") != 0) {
            printWriter.println("Permission Denial: can't dump LSOService");
            return;
        }
        StringBuilder sb = new StringBuilder();
        boolean isConfigured = isConfigured(null, 2);
        sb.append("LOCKSCREEN_WALLPAPER : " + isConfigured);
        sb.append(System.lineSeparator());
        if (isConfigured) {
            sb.append("  AdminUid: " + this.mWallpaperAdminUid);
            sb.append(System.lineSeparator());
        }
        boolean isConfigured2 = isConfigured(null, 1);
        sb.append("LOCKSCREEN_OVERLAY : " + isConfigured2);
        sb.append(System.lineSeparator());
        if (isConfigured2) {
            sb.append("  AdminUid: " + this.mOverlayAdminUid);
            for (int i = 1; i <= 3; i++) {
                LSOItemData data = getData(null, i);
                if (data != null) {
                    sb.append(System.lineSeparator());
                    sb.append("    Layer " + LSOConstants.getLayerName(i) + " : " + data.toString());
                }
            }
            sb.append(System.lineSeparator());
        }
        printWriter.write(sb.toString());
    }
}
