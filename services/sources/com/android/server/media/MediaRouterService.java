package com.android.server.media;

import android.R;
import android.app.ActivityManager;
import android.app.UserSwitchObserver;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.media.AudioPlaybackConfiguration;
import android.media.AudioRoutesInfo;
import android.media.IAudioRoutesObserver;
import android.media.IAudioService;
import android.media.IMediaRouter2;
import android.media.IMediaRouter2Manager;
import android.media.IMediaRouterClient;
import android.media.IMediaRouterService;
import android.media.MediaRoute2Info;
import android.media.MediaRouterClientState;
import android.media.RemoteDisplayState;
import android.media.RouteDiscoveryPreference;
import android.media.RouteListingPreference;
import android.media.RoutingSessionInfo;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.IntArray;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;
import android.util.TimeUtils;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.LocalServices;
import com.android.server.Watchdog;
import com.android.server.media.AudioPlayerStateMonitor;
import com.android.server.media.RemoteDisplayProviderProxy;
import com.android.server.media.RemoteDisplayProviderWatcher;
import com.android.server.pm.UserManagerInternal;
import com.android.server.statusbar.StatusBarManagerInternal;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class MediaRouterService extends IMediaRouterService.Stub implements Watchdog.Monitor {
    public static final boolean DEBUG = Log.isLoggable("MediaRouterService", 3);
    public BluetoothDevice mActiveBluetoothDevice;
    public final IntArray mActivePlayerMinPriorityQueue;
    public final IntArray mActivePlayerUidMinPriorityQueue;
    public final AudioPlayerStateMonitor mAudioPlayerStateMonitor;
    public int mAudioRouteMainType;
    public final IAudioService mAudioService;
    public final String mBluetoothA2dpRouteId;
    public final Context mContext;
    public final String mDefaultAudioRouteId;
    public boolean mForceBluetoothA2dpOn;
    public boolean mGlobalBluetoothA2dpOn;
    public final Handler mHandler;
    public final BroadcastReceiver mReceiver;
    public final MediaRouter2ServiceImpl mService2;
    public final UserManagerInternal mUserManagerInternal;
    public final Object mLock = new Object();
    public final SparseArray mUserRecords = new SparseArray();
    public final ArrayMap mAllClientRecords = new ArrayMap();
    public int mCurrentActiveUserId = -1;

    public MediaRouterService(Context context) {
        Handler handler = new Handler();
        this.mHandler = handler;
        this.mActivePlayerMinPriorityQueue = new IntArray();
        this.mActivePlayerUidMinPriorityQueue = new IntArray();
        this.mReceiver = new MediaRouterServiceBroadcastReceiver();
        this.mAudioRouteMainType = 0;
        this.mGlobalBluetoothA2dpOn = false;
        this.mForceBluetoothA2dpOn = false;
        this.mService2 = new MediaRouter2ServiceImpl(context);
        this.mContext = context;
        Watchdog.getInstance().addMonitor(this);
        Resources resources = context.getResources();
        this.mDefaultAudioRouteId = resources.getString(R.string.kg_pin_instructions);
        this.mBluetoothA2dpRouteId = resources.getString(R.string.config_ntpServer);
        this.mUserManagerInternal = (UserManagerInternal) LocalServices.getService(UserManagerInternal.class);
        IAudioService asInterface = IAudioService.Stub.asInterface(ServiceManager.getService("audio"));
        this.mAudioService = asInterface;
        AudioPlayerStateMonitor audioPlayerStateMonitor = AudioPlayerStateMonitor.getInstance(context);
        this.mAudioPlayerStateMonitor = audioPlayerStateMonitor;
        byte b = 0;
        audioPlayerStateMonitor.registerListener(new AudioPlayerActiveStateChangedListenerImpl(), handler);
        try {
            asInterface.startWatchingRoutes(new AudioRoutesObserverImpl());
        } catch (RemoteException unused) {
            Slog.w("MediaRouterService", "RemoteException in the audio service.");
        }
        context.registerReceiverAsUser(this.mReceiver, UserHandle.ALL, new IntentFilter("android.bluetooth.a2dp.profile.action.ACTIVE_DEVICE_CHANGED"), null, null);
    }

    public void systemRunning() {
        ActivityManager.getService().registerUserSwitchObserver(new UserSwitchObserver() { // from class: com.android.server.media.MediaRouterService.1
            public void onUserSwitchComplete(int i) {
                MediaRouterService.this.updateRunningUserAndProfiles(i);
            }
        }, "MediaRouterService");
        updateRunningUserAndProfiles(ActivityManager.getCurrentUser());
    }

    @Override // com.android.server.Watchdog.Monitor
    public void monitor() {
        synchronized (this.mLock) {
        }
    }

    public void registerClientAsUser(IMediaRouterClient iMediaRouterClient, String str, int i) {
        int callingUid = Binder.getCallingUid();
        if (!validatePackageName(callingUid, str)) {
            throw new SecurityException("packageName must match the calling uid");
        }
        int callingPid = Binder.getCallingPid();
        int handleIncomingUser = ActivityManager.handleIncomingUser(callingPid, callingUid, i, false, true, "registerClientAsUser", str);
        boolean z = this.mContext.checkCallingOrSelfPermission("android.permission.CONFIGURE_WIFI_DISPLAY") == 0;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                registerClientLocked(iMediaRouterClient, callingUid, callingPid, str, handleIncomingUser, z);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void registerClientGroupId(IMediaRouterClient iMediaRouterClient, String str) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.CONFIGURE_WIFI_DISPLAY") != 0) {
            Log.w("MediaRouterService", "Ignoring client group request because the client doesn't have the CONFIGURE_WIFI_DISPLAY permission.");
            return;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                registerClientGroupIdLocked(iMediaRouterClient, str);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void unregisterClient(IMediaRouterClient iMediaRouterClient) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                unregisterClientLocked(iMediaRouterClient, false);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean showMediaOutputSwitcher(String str) {
        if (!validatePackageName(Binder.getCallingUid(), str)) {
            throw new SecurityException("packageName must match the calling identity");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (((ActivityManager) this.mContext.getSystemService(ActivityManager.class)).getPackageImportance(str) > 100) {
                Slog.w("MediaRouterService", "showMediaOutputSwitcher only works when called from foreground");
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            }
            synchronized (this.mLock) {
                ((StatusBarManagerInternal) LocalServices.getService(StatusBarManagerInternal.class)).showMediaOutputSwitcher(str);
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return true;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public MediaRouterClientState getState(IMediaRouterClient iMediaRouterClient) {
        MediaRouterClientState stateLocked;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                stateLocked = getStateLocked(iMediaRouterClient);
            }
            return stateLocked;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean isPlaybackActive(IMediaRouterClient iMediaRouterClient) {
        ClientRecord clientRecord;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                clientRecord = (ClientRecord) this.mAllClientRecords.get(iMediaRouterClient.asBinder());
            }
            if (clientRecord != null) {
                return this.mAudioPlayerStateMonitor.isPlaybackActive(clientRecord.mUid);
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return false;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void setBluetoothA2dpOn(IMediaRouterClient iMediaRouterClient, boolean z) {
        if (iMediaRouterClient == null) {
            throw new IllegalArgumentException("client must not be null");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                this.mAudioService.setBluetoothA2dpOn(z);
            } catch (RemoteException unused) {
                Slog.w("MediaRouterService", "RemoteException while calling setBluetoothA2dpOn. on=" + z);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void setDiscoveryRequest(IMediaRouterClient iMediaRouterClient, int i, boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                setDiscoveryRequestLocked(iMediaRouterClient, i, z);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void setSelectedRoute(IMediaRouterClient iMediaRouterClient, String str, boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                setSelectedRouteLocked(iMediaRouterClient, str, z);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void requestSetVolume(IMediaRouterClient iMediaRouterClient, String str, int i) {
        Objects.requireNonNull(str, "routeId must not be null");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                requestSetVolumeLocked(iMediaRouterClient, str, i);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void requestUpdateVolume(IMediaRouterClient iMediaRouterClient, String str, int i) {
        Objects.requireNonNull(str, "routeId must not be null");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                requestUpdateVolumeLocked(iMediaRouterClient, str, i);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (DumpUtils.checkDumpPermission(this.mContext, "MediaRouterService", printWriter)) {
            printWriter.println("MEDIA ROUTER SERVICE (dumpsys media_router)");
            printWriter.println();
            printWriter.println("Global state");
            printWriter.println("  mCurrentUserId=" + this.mCurrentActiveUserId);
            synchronized (this.mLock) {
                int size = this.mUserRecords.size();
                for (int i = 0; i < size; i++) {
                    UserRecord userRecord = (UserRecord) this.mUserRecords.valueAt(i);
                    printWriter.println();
                    userRecord.dump(printWriter, "");
                }
            }
            printWriter.println();
            this.mService2.dump(printWriter, "");
        }
    }

    public boolean verifyPackageExists(String str) {
        return this.mService2.verifyPackageExists(str);
    }

    public List getSystemRoutes() {
        return this.mService2.getSystemRoutes();
    }

    public RoutingSessionInfo getSystemSessionInfo() {
        return this.mService2.getSystemSessionInfo(null, false);
    }

    public void registerRouter2(IMediaRouter2 iMediaRouter2, String str) {
        if (!validatePackageName(Binder.getCallingUid(), str)) {
            throw new SecurityException("packageName must match the calling uid");
        }
        this.mService2.registerRouter2(iMediaRouter2, str);
    }

    public void unregisterRouter2(IMediaRouter2 iMediaRouter2) {
        this.mService2.unregisterRouter2(iMediaRouter2);
    }

    public void setDiscoveryRequestWithRouter2(IMediaRouter2 iMediaRouter2, RouteDiscoveryPreference routeDiscoveryPreference) {
        this.mService2.setDiscoveryRequestWithRouter2(iMediaRouter2, routeDiscoveryPreference);
    }

    public void setRouteListingPreference(IMediaRouter2 iMediaRouter2, RouteListingPreference routeListingPreference) {
        this.mService2.setRouteListingPreference(iMediaRouter2, routeListingPreference);
    }

    public void setRouteVolumeWithRouter2(IMediaRouter2 iMediaRouter2, MediaRoute2Info mediaRoute2Info, int i) {
        this.mService2.setRouteVolumeWithRouter2(iMediaRouter2, mediaRoute2Info, i);
    }

    public void requestCreateSessionWithRouter2(IMediaRouter2 iMediaRouter2, int i, long j, RoutingSessionInfo routingSessionInfo, MediaRoute2Info mediaRoute2Info, Bundle bundle) {
        this.mService2.requestCreateSessionWithRouter2(iMediaRouter2, i, j, routingSessionInfo, mediaRoute2Info, bundle);
    }

    public void selectRouteWithRouter2(IMediaRouter2 iMediaRouter2, String str, MediaRoute2Info mediaRoute2Info) {
        this.mService2.selectRouteWithRouter2(iMediaRouter2, str, mediaRoute2Info);
    }

    public void deselectRouteWithRouter2(IMediaRouter2 iMediaRouter2, String str, MediaRoute2Info mediaRoute2Info) {
        this.mService2.deselectRouteWithRouter2(iMediaRouter2, str, mediaRoute2Info);
    }

    public void transferToRouteWithRouter2(IMediaRouter2 iMediaRouter2, String str, MediaRoute2Info mediaRoute2Info) {
        this.mService2.transferToRouteWithRouter2(iMediaRouter2, str, mediaRoute2Info);
    }

    public void setSessionVolumeWithRouter2(IMediaRouter2 iMediaRouter2, String str, int i) {
        this.mService2.setSessionVolumeWithRouter2(iMediaRouter2, str, i);
    }

    public void releaseSessionWithRouter2(IMediaRouter2 iMediaRouter2, String str) {
        this.mService2.releaseSessionWithRouter2(iMediaRouter2, str);
    }

    public List getRemoteSessions(IMediaRouter2Manager iMediaRouter2Manager) {
        return this.mService2.getRemoteSessions(iMediaRouter2Manager);
    }

    public RoutingSessionInfo getSystemSessionInfoForPackage(IMediaRouter2Manager iMediaRouter2Manager, String str) {
        boolean z;
        int identifier = UserHandle.getUserHandleForUid(Binder.getCallingUid()).getIdentifier();
        synchronized (this.mLock) {
            UserRecord userRecord = (UserRecord) this.mUserRecords.get(identifier);
            Iterator it = (userRecord != null ? userRecord.mClientRecords : Collections.emptyList()).iterator();
            while (true) {
                if (!it.hasNext()) {
                    z = false;
                    break;
                }
                ClientRecord clientRecord = (ClientRecord) it.next();
                if (TextUtils.equals(clientRecord.mPackageName, str) && this.mDefaultAudioRouteId.equals(clientRecord.mSelectedRouteId)) {
                    z = true;
                    break;
                }
            }
        }
        return this.mService2.getSystemSessionInfo(str, z);
    }

    public void registerManager(IMediaRouter2Manager iMediaRouter2Manager, String str) {
        if (!validatePackageName(Binder.getCallingUid(), str)) {
            throw new SecurityException("packageName must match the calling uid");
        }
        this.mService2.registerManager(iMediaRouter2Manager, str);
    }

    public void unregisterManager(IMediaRouter2Manager iMediaRouter2Manager) {
        this.mService2.unregisterManager(iMediaRouter2Manager);
    }

    public void startScan(IMediaRouter2Manager iMediaRouter2Manager) {
        this.mService2.startScan(iMediaRouter2Manager);
    }

    public void stopScan(IMediaRouter2Manager iMediaRouter2Manager) {
        this.mService2.stopScan(iMediaRouter2Manager);
    }

    public void setRouteVolumeWithManager(IMediaRouter2Manager iMediaRouter2Manager, int i, MediaRoute2Info mediaRoute2Info, int i2) {
        this.mService2.setRouteVolumeWithManager(iMediaRouter2Manager, i, mediaRoute2Info, i2);
    }

    public void requestCreateSessionWithManager(IMediaRouter2Manager iMediaRouter2Manager, int i, RoutingSessionInfo routingSessionInfo, MediaRoute2Info mediaRoute2Info) {
        this.mService2.requestCreateSessionWithManager(iMediaRouter2Manager, i, routingSessionInfo, mediaRoute2Info);
    }

    public void selectRouteWithManager(IMediaRouter2Manager iMediaRouter2Manager, int i, String str, MediaRoute2Info mediaRoute2Info) {
        this.mService2.selectRouteWithManager(iMediaRouter2Manager, i, str, mediaRoute2Info);
    }

    public void deselectRouteWithManager(IMediaRouter2Manager iMediaRouter2Manager, int i, String str, MediaRoute2Info mediaRoute2Info) {
        this.mService2.deselectRouteWithManager(iMediaRouter2Manager, i, str, mediaRoute2Info);
    }

    public void transferToRouteWithManager(IMediaRouter2Manager iMediaRouter2Manager, int i, String str, MediaRoute2Info mediaRoute2Info) {
        this.mService2.transferToRouteWithManager(iMediaRouter2Manager, i, str, mediaRoute2Info);
    }

    public void setSessionVolumeWithManager(IMediaRouter2Manager iMediaRouter2Manager, int i, String str, int i2) {
        this.mService2.setSessionVolumeWithManager(iMediaRouter2Manager, i, str, i2);
    }

    public void releaseSessionWithManager(IMediaRouter2Manager iMediaRouter2Manager, int i, String str) {
        this.mService2.releaseSessionWithManager(iMediaRouter2Manager, i, str);
    }

    public void restoreBluetoothA2dp() {
        boolean z;
        BluetoothDevice bluetoothDevice;
        try {
            synchronized (this.mLock) {
                if (!this.mGlobalBluetoothA2dpOn && !this.mForceBluetoothA2dpOn) {
                    z = false;
                    bluetoothDevice = this.mActiveBluetoothDevice;
                }
                z = true;
                bluetoothDevice = this.mActiveBluetoothDevice;
            }
            if (bluetoothDevice != null) {
                if (DEBUG) {
                    Slog.d("MediaRouterService", "restoreBluetoothA2dp(" + z + ")");
                }
                this.mAudioService.setBluetoothA2dpOn(z);
                return;
            }
            synchronized (this.mLock) {
                this.mForceBluetoothA2dpOn = false;
            }
        } catch (RemoteException unused) {
            Slog.w("MediaRouterService", "RemoteException while calling setBluetoothA2dpOn.");
        }
    }

    public void restoreRoute(int i) {
        ClientRecord clientRecord;
        ArrayList arrayList;
        synchronized (this.mLock) {
            UserRecord userRecord = (UserRecord) this.mUserRecords.get(UserHandle.getUserHandleForUid(i).getIdentifier());
            if (userRecord != null && (arrayList = userRecord.mClientRecords) != null) {
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    clientRecord = (ClientRecord) it.next();
                    if (validatePackageName(i, clientRecord.mPackageName)) {
                        break;
                    }
                }
            }
            clientRecord = null;
        }
        if (clientRecord != null) {
            try {
                clientRecord.mClient.onRestoreRoute();
                return;
            } catch (RemoteException unused) {
                Slog.w("MediaRouterService", "Failed to call onRestoreRoute. Client probably died.");
                return;
            }
        }
        restoreBluetoothA2dp();
    }

    public final void updateRunningUserAndProfiles(int i) {
        synchronized (this.mLock) {
            if (this.mCurrentActiveUserId != i) {
                this.mCurrentActiveUserId = i;
                SparseArray clone = this.mUserRecords.clone();
                for (int i2 = 0; i2 < clone.size(); i2++) {
                    int keyAt = clone.keyAt(i2);
                    UserRecord userRecord = (UserRecord) clone.valueAt(i2);
                    if (isUserActiveLocked(keyAt)) {
                        userRecord.mHandler.sendEmptyMessage(1);
                    } else {
                        userRecord.mHandler.sendEmptyMessage(2);
                        disposeUserIfNeededLocked(userRecord);
                    }
                }
            }
        }
        this.mService2.updateRunningUserAndProfiles(i);
    }

    public void clientDied(ClientRecord clientRecord) {
        synchronized (this.mLock) {
            unregisterClientLocked(clientRecord.mClient, true);
        }
    }

    public final void registerClientLocked(IMediaRouterClient iMediaRouterClient, int i, int i2, String str, int i3, boolean z) {
        UserRecord userRecord;
        boolean z2;
        IBinder asBinder = iMediaRouterClient.asBinder();
        if (((ClientRecord) this.mAllClientRecords.get(asBinder)) == null) {
            UserRecord userRecord2 = (UserRecord) this.mUserRecords.get(i3);
            if (userRecord2 == null) {
                userRecord = new UserRecord(i3);
                z2 = true;
            } else {
                userRecord = userRecord2;
                z2 = false;
            }
            ClientRecord clientRecord = new ClientRecord(userRecord, iMediaRouterClient, i, i2, str, z);
            try {
                asBinder.linkToDeath(clientRecord, 0);
                if (z2) {
                    this.mUserRecords.put(i3, userRecord);
                    initializeUserLocked(userRecord);
                }
                userRecord.mClientRecords.add(clientRecord);
                this.mAllClientRecords.put(asBinder, clientRecord);
                initializeClientLocked(clientRecord);
            } catch (RemoteException e) {
                throw new RuntimeException("Media router client died prematurely.", e);
            }
        }
    }

    public final void registerClientGroupIdLocked(IMediaRouterClient iMediaRouterClient, String str) {
        ClientRecord clientRecord = (ClientRecord) this.mAllClientRecords.get(iMediaRouterClient.asBinder());
        if (clientRecord == null) {
            Log.w("MediaRouterService", "Ignoring group id register request of a unregistered client.");
            return;
        }
        if (TextUtils.equals(clientRecord.mGroupId, str)) {
            return;
        }
        UserRecord userRecord = clientRecord.mUserRecord;
        String str2 = clientRecord.mGroupId;
        if (str2 != null) {
            userRecord.removeFromGroup(str2, clientRecord);
        }
        clientRecord.mGroupId = str;
        if (str != null) {
            userRecord.addToGroup(str, clientRecord);
            userRecord.mHandler.obtainMessage(10, str).sendToTarget();
        }
    }

    public final void unregisterClientLocked(IMediaRouterClient iMediaRouterClient, boolean z) {
        ClientRecord clientRecord = (ClientRecord) this.mAllClientRecords.remove(iMediaRouterClient.asBinder());
        if (clientRecord != null) {
            UserRecord userRecord = clientRecord.mUserRecord;
            userRecord.mClientRecords.remove(clientRecord);
            String str = clientRecord.mGroupId;
            if (str != null) {
                userRecord.removeFromGroup(str, clientRecord);
                clientRecord.mGroupId = null;
            }
            disposeClientLocked(clientRecord, z);
            disposeUserIfNeededLocked(userRecord);
        }
    }

    public final MediaRouterClientState getStateLocked(IMediaRouterClient iMediaRouterClient) {
        ClientRecord clientRecord = (ClientRecord) this.mAllClientRecords.get(iMediaRouterClient.asBinder());
        if (clientRecord != null) {
            return clientRecord.getState();
        }
        return null;
    }

    public final void setDiscoveryRequestLocked(IMediaRouterClient iMediaRouterClient, int i, boolean z) {
        ClientRecord clientRecord = (ClientRecord) this.mAllClientRecords.get(iMediaRouterClient.asBinder());
        if (clientRecord != null) {
            if (!clientRecord.mTrusted) {
                i &= -5;
            }
            if (clientRecord.mRouteTypes == i && clientRecord.mActiveScan == z) {
                return;
            }
            if (DEBUG) {
                Slog.d("MediaRouterService", clientRecord + ": Set discovery request, routeTypes=0x" + Integer.toHexString(i) + ", activeScan=" + z);
            }
            clientRecord.mRouteTypes = i;
            clientRecord.mActiveScan = z;
            clientRecord.mUserRecord.mHandler.sendEmptyMessage(3);
        }
    }

    public final void setSelectedRouteLocked(IMediaRouterClient iMediaRouterClient, String str, boolean z) {
        ClientGroup clientGroup;
        ClientRecord clientRecord = (ClientRecord) this.mAllClientRecords.get(iMediaRouterClient.asBinder());
        if (clientRecord != null) {
            String str2 = (this.mDefaultAudioRouteId.equals(clientRecord.mSelectedRouteId) || this.mBluetoothA2dpRouteId.equals(clientRecord.mSelectedRouteId)) ? null : clientRecord.mSelectedRouteId;
            clientRecord.mSelectedRouteId = str;
            if (this.mDefaultAudioRouteId.equals(str) || this.mBluetoothA2dpRouteId.equals(str)) {
                str = null;
            }
            if (Objects.equals(str, str2)) {
                return;
            }
            if (DEBUG) {
                Slog.d("MediaRouterService", clientRecord + ": Set selected route, routeId=" + str + ", oldRouteId=" + str2 + ", explicit=" + z);
            }
            if (z && clientRecord.mTrusted) {
                if (KnoxCustomManagerService.SMARTMIRRORING_PACKAGE_NAME.equals(clientRecord.mPackageName)) {
                    clientRecord.mUserRecord.mHandler.obtainMessage(20, Boolean.TRUE).sendToTarget();
                } else {
                    clientRecord.mUserRecord.mHandler.obtainMessage(20, Boolean.FALSE).sendToTarget();
                }
                if (str2 != null) {
                    clientRecord.mUserRecord.mHandler.obtainMessage(5, str2).sendToTarget();
                }
                if (str != null) {
                    clientRecord.mUserRecord.mHandler.obtainMessage(4, str).sendToTarget();
                }
                if (clientRecord.mGroupId == null || (clientGroup = (ClientGroup) clientRecord.mUserRecord.mClientGroupMap.get(clientRecord.mGroupId)) == null) {
                    return;
                }
                clientGroup.mSelectedRouteId = str;
                clientRecord.mUserRecord.mHandler.obtainMessage(10, clientRecord.mGroupId).sendToTarget();
            }
        }
    }

    public final void requestSetVolumeLocked(IMediaRouterClient iMediaRouterClient, String str, int i) {
        ClientRecord clientRecord = (ClientRecord) this.mAllClientRecords.get(iMediaRouterClient.asBinder());
        if (clientRecord != null) {
            clientRecord.mUserRecord.mHandler.obtainMessage(6, i, 0, str).sendToTarget();
        }
    }

    public final void requestUpdateVolumeLocked(IMediaRouterClient iMediaRouterClient, String str, int i) {
        ClientRecord clientRecord = (ClientRecord) this.mAllClientRecords.get(iMediaRouterClient.asBinder());
        if (clientRecord != null) {
            clientRecord.mUserRecord.mHandler.obtainMessage(7, i, 0, str).sendToTarget();
        }
    }

    public final void initializeUserLocked(UserRecord userRecord) {
        if (DEBUG) {
            Slog.d("MediaRouterService", userRecord + ": Initialized");
        }
        if (isUserActiveLocked(userRecord.mUserId)) {
            userRecord.mHandler.sendEmptyMessage(1);
        }
    }

    public final void disposeUserIfNeededLocked(UserRecord userRecord) {
        if (isUserActiveLocked(userRecord.mUserId) || !userRecord.mClientRecords.isEmpty()) {
            return;
        }
        if (DEBUG) {
            Slog.d("MediaRouterService", userRecord + ": Disposed");
        }
        this.mUserRecords.remove(userRecord.mUserId);
    }

    public final boolean isUserActiveLocked(int i) {
        return this.mUserManagerInternal.getProfileParentId(i) == this.mCurrentActiveUserId;
    }

    public final void initializeClientLocked(ClientRecord clientRecord) {
        if (DEBUG) {
            Slog.d("MediaRouterService", clientRecord + ": Registered");
        }
    }

    public final void disposeClientLocked(ClientRecord clientRecord, boolean z) {
        if (DEBUG) {
            if (z) {
                Slog.d("MediaRouterService", clientRecord + ": Died!");
            } else {
                Slog.d("MediaRouterService", clientRecord + ": Unregistered");
            }
        }
        if (clientRecord.mRouteTypes != 0 || clientRecord.mActiveScan) {
            clientRecord.mUserRecord.mHandler.sendEmptyMessage(3);
        }
        clientRecord.dispose();
    }

    public final boolean validatePackageName(int i, String str) {
        String[] packagesForUid;
        if (str != null && (packagesForUid = this.mContext.getPackageManager().getPackagesForUid(i)) != null) {
            for (String str2 : packagesForUid) {
                if (str2.equals(str)) {
                    return true;
                }
            }
        }
        return false;
    }

    /* loaded from: classes2.dex */
    public final class MediaRouterServiceBroadcastReceiver extends BroadcastReceiver {
        public MediaRouterServiceBroadcastReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.bluetooth.a2dp.profile.action.ACTIVE_DEVICE_CHANGED")) {
                BluetoothDevice bluetoothDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE", BluetoothDevice.class);
                synchronized (MediaRouterService.this.mLock) {
                    MediaRouterService mediaRouterService = MediaRouterService.this;
                    mediaRouterService.mActiveBluetoothDevice = bluetoothDevice;
                    mediaRouterService.mGlobalBluetoothA2dpOn = bluetoothDevice != null;
                }
            }
        }
    }

    /* loaded from: classes2.dex */
    public final class ClientRecord implements IBinder.DeathRecipient {
        public boolean mActiveScan;
        public final IMediaRouterClient mClient;
        public String mGroupId;
        public final String mPackageName;
        public final int mPid;
        public int mRouteTypes;
        public String mSelectedRouteId;
        public final boolean mTrusted;
        public final int mUid;
        public final UserRecord mUserRecord;

        public ClientRecord(UserRecord userRecord, IMediaRouterClient iMediaRouterClient, int i, int i2, String str, boolean z) {
            this.mUserRecord = userRecord;
            this.mClient = iMediaRouterClient;
            this.mUid = i;
            this.mPid = i2;
            this.mPackageName = str;
            this.mTrusted = z;
        }

        public void dispose() {
            this.mClient.asBinder().unlinkToDeath(this, 0);
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            MediaRouterService.this.clientDied(this);
        }

        public MediaRouterClientState getState() {
            if (this.mTrusted) {
                return this.mUserRecord.mRouterState;
            }
            return null;
        }

        public void dump(PrintWriter printWriter, String str) {
            printWriter.println(str + this);
            String str2 = str + "  ";
            printWriter.println(str2 + "mTrusted=" + this.mTrusted);
            printWriter.println(str2 + "mRouteTypes=0x" + Integer.toHexString(this.mRouteTypes));
            printWriter.println(str2 + "mActiveScan=" + this.mActiveScan);
            printWriter.println(str2 + "mSelectedRouteId=" + this.mSelectedRouteId);
        }

        public String toString() {
            return "Client " + this.mPackageName + " (pid " + this.mPid + ")";
        }
    }

    /* loaded from: classes2.dex */
    public final class ClientGroup {
        public final List mClientRecords = new ArrayList();
        public String mSelectedRouteId;

        public ClientGroup() {
        }
    }

    /* loaded from: classes2.dex */
    public final class UserRecord {
        public final UserHandler mHandler;
        public MediaRouterClientState mRouterState;
        public final int mUserId;
        public final ArrayList mClientRecords = new ArrayList();
        public final ArrayMap mClientGroupMap = new ArrayMap();

        public UserRecord(int i) {
            this.mUserId = i;
            this.mHandler = new UserHandler(MediaRouterService.this, this);
        }

        public void dump(final PrintWriter printWriter, String str) {
            printWriter.println(str + this);
            final String str2 = str + "  ";
            int size = this.mClientRecords.size();
            if (size != 0) {
                for (int i = 0; i < size; i++) {
                    ((ClientRecord) this.mClientRecords.get(i)).dump(printWriter, str2);
                }
            } else {
                printWriter.println(str2 + "<no clients>");
            }
            printWriter.println(str2 + "State");
            printWriter.println(str2 + "mRouterState=" + this.mRouterState);
            if (this.mHandler.runWithScissors(new Runnable() { // from class: com.android.server.media.MediaRouterService.UserRecord.1
                @Override // java.lang.Runnable
                public void run() {
                    UserRecord.this.mHandler.dump(printWriter, str2);
                }
            }, 1000L)) {
                return;
            }
            printWriter.println(str2 + "<could not dump handler state>");
        }

        public void addToGroup(String str, ClientRecord clientRecord) {
            ClientGroup clientGroup = (ClientGroup) this.mClientGroupMap.get(str);
            if (clientGroup == null) {
                clientGroup = new ClientGroup();
                this.mClientGroupMap.put(str, clientGroup);
            }
            clientGroup.mClientRecords.add(clientRecord);
        }

        public void removeFromGroup(String str, ClientRecord clientRecord) {
            ClientGroup clientGroup = (ClientGroup) this.mClientGroupMap.get(str);
            if (clientGroup != null) {
                clientGroup.mClientRecords.remove(clientRecord);
                if (clientGroup.mClientRecords.size() == 0) {
                    this.mClientGroupMap.remove(str);
                }
            }
        }

        public String toString() {
            return "User " + this.mUserId;
        }
    }

    /* loaded from: classes2.dex */
    public final class UserHandler extends Handler implements RemoteDisplayProviderWatcher.Callback, RemoteDisplayProviderProxy.Callback {
        public boolean mClientStateUpdateScheduled;
        public int mConnectionPhase;
        public int mConnectionTimeoutReason;
        public long mConnectionTimeoutStartTime;
        public int mDiscoveryMode;
        public final ArrayList mProviderRecords;
        public boolean mRunning;
        public RouteRecord mSelectedRouteRecord;
        public final MediaRouterService mService;
        public final ArrayList mTempClients;
        public boolean mTriggeredBySmartView;
        public final UserRecord mUserRecord;
        public final RemoteDisplayProviderWatcher mWatcher;

        public static int getConnectionPhase(int i) {
            if (i != 0) {
                if (i == 1) {
                    return 0;
                }
                if (i == 2) {
                    return 1;
                }
                if (i == 3) {
                    return 0;
                }
                if (i != 6) {
                    return -1;
                }
            }
            return 2;
        }

        public UserHandler(MediaRouterService mediaRouterService, UserRecord userRecord) {
            super(Looper.getMainLooper(), null, true);
            this.mProviderRecords = new ArrayList();
            this.mTempClients = new ArrayList();
            this.mDiscoveryMode = 0;
            this.mConnectionPhase = -1;
            this.mService = mediaRouterService;
            this.mUserRecord = userRecord;
            this.mWatcher = new RemoteDisplayProviderWatcher(mediaRouterService.mContext, this, this, userRecord.mUserId);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            if (i != 20) {
                switch (i) {
                    case 1:
                        start();
                        return;
                    case 2:
                        stop();
                        return;
                    case 3:
                        updateDiscoveryRequest();
                        return;
                    case 4:
                        selectRoute((String) message.obj);
                        return;
                    case 5:
                        unselectRoute((String) message.obj);
                        return;
                    case 6:
                        requestSetVolume((String) message.obj, message.arg1);
                        return;
                    case 7:
                        requestUpdateVolume((String) message.obj, message.arg1);
                        return;
                    case 8:
                        updateClientState();
                        return;
                    case 9:
                        connectionTimedOut();
                        return;
                    case 10:
                        notifyGroupRouteSelected((String) message.obj);
                        return;
                    default:
                        return;
                }
            }
            this.mTriggeredBySmartView = ((Boolean) message.obj).booleanValue();
        }

        public void dump(PrintWriter printWriter, String str) {
            printWriter.println(str + "Handler");
            String str2 = str + "  ";
            printWriter.println(str2 + "mRunning=" + this.mRunning);
            printWriter.println(str2 + "mDiscoveryMode=" + this.mDiscoveryMode);
            printWriter.println(str2 + "mSelectedRouteRecord=" + this.mSelectedRouteRecord);
            printWriter.println(str2 + "mConnectionPhase=" + this.mConnectionPhase);
            printWriter.println(str2 + "mConnectionTimeoutReason=" + this.mConnectionTimeoutReason);
            StringBuilder sb = new StringBuilder();
            sb.append(str2);
            sb.append("mConnectionTimeoutStartTime=");
            sb.append(this.mConnectionTimeoutReason != 0 ? TimeUtils.formatUptime(this.mConnectionTimeoutStartTime) : "<n/a>");
            printWriter.println(sb.toString());
            this.mWatcher.dump(printWriter, str);
            int size = this.mProviderRecords.size();
            if (size != 0) {
                for (int i = 0; i < size; i++) {
                    ((ProviderRecord) this.mProviderRecords.get(i)).dump(printWriter, str);
                }
                return;
            }
            printWriter.println(str2 + "<no providers>");
        }

        public final void start() {
            if (this.mRunning) {
                return;
            }
            this.mRunning = true;
            this.mWatcher.start();
        }

        public final void stop() {
            if (this.mRunning) {
                this.mRunning = false;
                unselectSelectedRoute();
                this.mWatcher.stop();
            }
        }

        public final void updateDiscoveryRequest() {
            int i;
            int i2;
            boolean z;
            synchronized (this.mService.mLock) {
                int size = this.mUserRecord.mClientRecords.size();
                i2 = 0;
                z = false;
                for (int i3 = 0; i3 < size; i3++) {
                    ClientRecord clientRecord = (ClientRecord) this.mUserRecord.mClientRecords.get(i3);
                    i2 |= clientRecord.mRouteTypes;
                    z |= clientRecord.mActiveScan;
                }
            }
            int i4 = (i2 & 4) != 0 ? z ? 2 : 1 : 0;
            if (this.mDiscoveryMode != i4) {
                this.mDiscoveryMode = i4;
                int size2 = this.mProviderRecords.size();
                for (i = 0; i < size2; i++) {
                    ((ProviderRecord) this.mProviderRecords.get(i)).getProvider().setDiscoveryMode(this.mDiscoveryMode);
                }
            }
        }

        public final void selectRoute(String str) {
            RouteRecord findRouteRecord;
            if (str != null) {
                RouteRecord routeRecord = this.mSelectedRouteRecord;
                if ((routeRecord == null || !str.equals(routeRecord.getUniqueId())) && (findRouteRecord = findRouteRecord(str)) != null) {
                    unselectSelectedRoute();
                    Slog.i("MediaRouterService", "Selected route:" + findRouteRecord);
                    this.mSelectedRouteRecord = findRouteRecord;
                    checkSelectedRouteState();
                    findRouteRecord.getProvider().setSelectedDisplay(findRouteRecord.getDescriptorId());
                    scheduleUpdateClientState();
                }
            }
        }

        public final void unselectRoute(String str) {
            RouteRecord routeRecord;
            if (str == null || (routeRecord = this.mSelectedRouteRecord) == null || !str.equals(routeRecord.getUniqueId())) {
                return;
            }
            unselectSelectedRoute();
        }

        public final void unselectSelectedRoute() {
            if (this.mSelectedRouteRecord != null) {
                Slog.i("MediaRouterService", "Unselected route:" + this.mSelectedRouteRecord);
                this.mSelectedRouteRecord.getProvider().setSelectedDisplay(null);
                this.mSelectedRouteRecord = null;
                checkSelectedRouteState();
                scheduleUpdateClientState();
            }
        }

        public final void requestSetVolume(String str, int i) {
            RouteRecord routeRecord = this.mSelectedRouteRecord;
            if (routeRecord == null || !str.equals(routeRecord.getUniqueId())) {
                return;
            }
            this.mSelectedRouteRecord.getProvider().setDisplayVolume(i);
        }

        public final void requestUpdateVolume(String str, int i) {
            RouteRecord routeRecord = this.mSelectedRouteRecord;
            if (routeRecord == null || !str.equals(routeRecord.getUniqueId())) {
                return;
            }
            this.mSelectedRouteRecord.getProvider().adjustDisplayVolume(i);
        }

        @Override // com.android.server.media.RemoteDisplayProviderWatcher.Callback
        public void addProvider(RemoteDisplayProviderProxy remoteDisplayProviderProxy) {
            remoteDisplayProviderProxy.setCallback(this);
            remoteDisplayProviderProxy.setDiscoveryMode(this.mDiscoveryMode);
            remoteDisplayProviderProxy.setSelectedDisplay(null);
            ProviderRecord providerRecord = new ProviderRecord(remoteDisplayProviderProxy);
            this.mProviderRecords.add(providerRecord);
            providerRecord.updateDescriptor(remoteDisplayProviderProxy.getDisplayState());
            scheduleUpdateClientState();
        }

        @Override // com.android.server.media.RemoteDisplayProviderWatcher.Callback
        public void removeProvider(RemoteDisplayProviderProxy remoteDisplayProviderProxy) {
            int findProviderRecord = findProviderRecord(remoteDisplayProviderProxy);
            if (findProviderRecord >= 0) {
                ((ProviderRecord) this.mProviderRecords.remove(findProviderRecord)).updateDescriptor(null);
                remoteDisplayProviderProxy.setCallback(null);
                remoteDisplayProviderProxy.setDiscoveryMode(0);
                checkSelectedRouteState();
                scheduleUpdateClientState();
            }
        }

        @Override // com.android.server.media.RemoteDisplayProviderProxy.Callback
        public void onDisplayStateChanged(RemoteDisplayProviderProxy remoteDisplayProviderProxy, RemoteDisplayState remoteDisplayState) {
            updateProvider(remoteDisplayProviderProxy, remoteDisplayState);
        }

        public final void updateProvider(RemoteDisplayProviderProxy remoteDisplayProviderProxy, RemoteDisplayState remoteDisplayState) {
            int findProviderRecord = findProviderRecord(remoteDisplayProviderProxy);
            if (findProviderRecord < 0 || !((ProviderRecord) this.mProviderRecords.get(findProviderRecord)).updateDescriptor(remoteDisplayState)) {
                return;
            }
            checkSelectedRouteState();
            scheduleUpdateClientState();
        }

        public final void checkSelectedRouteState() {
            RouteRecord routeRecord = this.mSelectedRouteRecord;
            if (routeRecord == null) {
                this.mConnectionPhase = -1;
                updateConnectionTimeout(0);
                return;
            }
            if (!routeRecord.isValid() || !this.mSelectedRouteRecord.isEnabled()) {
                updateConnectionTimeout(1);
                return;
            }
            int i = this.mConnectionPhase;
            int connectionPhase = getConnectionPhase(this.mSelectedRouteRecord.getStatus());
            this.mConnectionPhase = connectionPhase;
            if (i >= 1 && connectionPhase < 1) {
                updateConnectionTimeout(2);
                return;
            }
            Slog.i("MediaRouterService", "checkSelectedRouteState: mTriggeredBySmartView=" + this.mTriggeredBySmartView);
            if (this.mTriggeredBySmartView) {
                return;
            }
            int i2 = this.mConnectionPhase;
            if (i2 == 0) {
                updateConnectionTimeout(3);
                return;
            }
            if (i2 == 1) {
                if (i != 1) {
                    Slog.i("MediaRouterService", "Connecting to route: " + this.mSelectedRouteRecord);
                }
                updateConnectionTimeout(4);
                return;
            }
            if (i2 == 2) {
                if (i != 2) {
                    Slog.i("MediaRouterService", "Connected to route: " + this.mSelectedRouteRecord);
                }
                updateConnectionTimeout(0);
                return;
            }
            updateConnectionTimeout(1);
        }

        public final void updateConnectionTimeout(int i) {
            int i2 = this.mConnectionTimeoutReason;
            if (i != i2) {
                if (i2 != 0) {
                    removeMessages(9);
                }
                this.mConnectionTimeoutReason = i;
                this.mConnectionTimeoutStartTime = SystemClock.uptimeMillis();
                if (i == 1 || i == 2) {
                    sendEmptyMessage(9);
                } else if (i == 3) {
                    sendEmptyMessageDelayed(9, 5000L);
                } else {
                    if (i != 4) {
                        return;
                    }
                    sendEmptyMessageDelayed(9, 60000L);
                }
            }
        }

        public final void connectionTimedOut() {
            int i = this.mConnectionTimeoutReason;
            if (i == 0 || this.mSelectedRouteRecord == null) {
                Log.wtf("MediaRouterService", "Handled connection timeout for no reason.");
                return;
            }
            if (i == 1) {
                Slog.i("MediaRouterService", "Selected route no longer available: " + this.mSelectedRouteRecord);
            } else if (i == 2) {
                Slog.i("MediaRouterService", "Selected route connection lost: " + this.mSelectedRouteRecord);
            } else if (i == 3) {
                Slog.i("MediaRouterService", "Selected route timed out while waiting for connection attempt to begin after " + (SystemClock.uptimeMillis() - this.mConnectionTimeoutStartTime) + " ms: " + this.mSelectedRouteRecord);
            } else if (i == 4) {
                Slog.i("MediaRouterService", "Selected route timed out while connecting after " + (SystemClock.uptimeMillis() - this.mConnectionTimeoutStartTime) + " ms: " + this.mSelectedRouteRecord);
            }
            this.mConnectionTimeoutReason = 0;
            unselectSelectedRoute();
        }

        public final void scheduleUpdateClientState() {
            if (this.mClientStateUpdateScheduled) {
                return;
            }
            this.mClientStateUpdateScheduled = true;
            sendEmptyMessage(8);
        }

        public final void updateClientState() {
            this.mClientStateUpdateScheduled = false;
            MediaRouterClientState mediaRouterClientState = new MediaRouterClientState();
            int size = this.mProviderRecords.size();
            for (int i = 0; i < size; i++) {
                ((ProviderRecord) this.mProviderRecords.get(i)).appendClientState(mediaRouterClientState);
            }
            try {
                synchronized (this.mService.mLock) {
                    UserRecord userRecord = this.mUserRecord;
                    userRecord.mRouterState = mediaRouterClientState;
                    int size2 = userRecord.mClientRecords.size();
                    for (int i2 = 0; i2 < size2; i2++) {
                        this.mTempClients.add(((ClientRecord) this.mUserRecord.mClientRecords.get(i2)).mClient);
                    }
                }
                int size3 = this.mTempClients.size();
                for (int i3 = 0; i3 < size3; i3++) {
                    try {
                        ((IMediaRouterClient) this.mTempClients.get(i3)).onStateChanged();
                    } catch (RemoteException unused) {
                        Slog.w("MediaRouterService", "Failed to call onStateChanged. Client probably died.");
                    }
                }
            } finally {
                this.mTempClients.clear();
            }
        }

        public final void notifyGroupRouteSelected(String str) {
            try {
                synchronized (this.mService.mLock) {
                    ClientGroup clientGroup = (ClientGroup) this.mUserRecord.mClientGroupMap.get(str);
                    if (clientGroup == null) {
                        return;
                    }
                    String str2 = clientGroup.mSelectedRouteId;
                    int size = clientGroup.mClientRecords.size();
                    for (int i = 0; i < size; i++) {
                        ClientRecord clientRecord = (ClientRecord) clientGroup.mClientRecords.get(i);
                        if (!TextUtils.equals(str2, clientRecord.mSelectedRouteId)) {
                            this.mTempClients.add(clientRecord.mClient);
                        }
                    }
                    int size2 = this.mTempClients.size();
                    for (int i2 = 0; i2 < size2; i2++) {
                        try {
                            ((IMediaRouterClient) this.mTempClients.get(i2)).onGroupRouteSelected(str2);
                        } catch (RemoteException unused) {
                            Slog.w("MediaRouterService", "Failed to call onSelectedRouteChanged. Client probably died.");
                        }
                    }
                }
            } finally {
                this.mTempClients.clear();
            }
        }

        public final int findProviderRecord(RemoteDisplayProviderProxy remoteDisplayProviderProxy) {
            int size = this.mProviderRecords.size();
            for (int i = 0; i < size; i++) {
                if (((ProviderRecord) this.mProviderRecords.get(i)).getProvider() == remoteDisplayProviderProxy) {
                    return i;
                }
            }
            return -1;
        }

        public final RouteRecord findRouteRecord(String str) {
            int size = this.mProviderRecords.size();
            for (int i = 0; i < size; i++) {
                RouteRecord findRouteByUniqueId = ((ProviderRecord) this.mProviderRecords.get(i)).findRouteByUniqueId(str);
                if (findRouteByUniqueId != null) {
                    return findRouteByUniqueId;
                }
            }
            return null;
        }

        /* loaded from: classes2.dex */
        public final class ProviderRecord {
            public RemoteDisplayState mDescriptor;
            public final RemoteDisplayProviderProxy mProvider;
            public final ArrayList mRoutes = new ArrayList();
            public final String mUniquePrefix;

            public ProviderRecord(RemoteDisplayProviderProxy remoteDisplayProviderProxy) {
                this.mProvider = remoteDisplayProviderProxy;
                this.mUniquePrefix = remoteDisplayProviderProxy.getFlattenedComponentName() + XmlUtils.STRING_ARRAY_SEPARATOR;
            }

            public RemoteDisplayProviderProxy getProvider() {
                return this.mProvider;
            }

            /* JADX WARN: Removed duplicated region for block: B:23:0x0095 A[LOOP:1: B:22:0x0093->B:23:0x0095, LOOP_END] */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public boolean updateDescriptor(android.media.RemoteDisplayState r12) {
                /*
                    r11 = this;
                    android.media.RemoteDisplayState r0 = r11.mDescriptor
                    r1 = 0
                    if (r0 == r12) goto La5
                    r11.mDescriptor = r12
                    r0 = 1
                    if (r12 == 0) goto L8b
                    boolean r2 = r12.isValid()
                    java.lang.String r3 = "MediaRouterService"
                    if (r2 == 0) goto L71
                    java.util.ArrayList r12 = r12.displays
                    int r2 = r12.size()
                    r4 = r1
                    r5 = r4
                L1a:
                    if (r1 >= r2) goto L6f
                    java.lang.Object r6 = r12.get(r1)
                    android.media.RemoteDisplayState$RemoteDisplayInfo r6 = (android.media.RemoteDisplayState.RemoteDisplayInfo) r6
                    java.lang.String r7 = r6.id
                    int r8 = r11.findRouteByDescriptorId(r7)
                    if (r8 >= 0) goto L40
                    java.lang.String r4 = r11.assignRouteUniqueId(r7)
                    com.android.server.media.MediaRouterService$UserHandler$RouteRecord r8 = new com.android.server.media.MediaRouterService$UserHandler$RouteRecord
                    r8.<init>(r11, r7, r4)
                    java.util.ArrayList r4 = r11.mRoutes
                    int r7 = r5 + 1
                    r4.add(r5, r8)
                    r8.updateDescriptor(r6)
                    r4 = r0
                    r5 = r7
                    goto L6c
                L40:
                    if (r8 >= r5) goto L57
                    java.lang.StringBuilder r7 = new java.lang.StringBuilder
                    r7.<init>()
                    java.lang.String r8 = "Ignoring route descriptor with duplicate id: "
                    r7.append(r8)
                    r7.append(r6)
                    java.lang.String r6 = r7.toString()
                    android.util.Slog.w(r3, r6)
                    goto L6c
                L57:
                    java.util.ArrayList r7 = r11.mRoutes
                    java.lang.Object r7 = r7.get(r8)
                    com.android.server.media.MediaRouterService$UserHandler$RouteRecord r7 = (com.android.server.media.MediaRouterService.UserHandler.RouteRecord) r7
                    java.util.ArrayList r9 = r11.mRoutes
                    int r10 = r5 + 1
                    java.util.Collections.swap(r9, r8, r5)
                    boolean r5 = r7.updateDescriptor(r6)
                    r4 = r4 | r5
                    r5 = r10
                L6c:
                    int r1 = r1 + 1
                    goto L1a
                L6f:
                    r1 = r4
                    goto L8c
                L71:
                    java.lang.StringBuilder r12 = new java.lang.StringBuilder
                    r12.<init>()
                    java.lang.String r2 = "Ignoring invalid descriptor from media route provider: "
                    r12.append(r2)
                    com.android.server.media.RemoteDisplayProviderProxy r2 = r11.mProvider
                    java.lang.String r2 = r2.getFlattenedComponentName()
                    r12.append(r2)
                    java.lang.String r12 = r12.toString()
                    android.util.Slog.w(r3, r12)
                L8b:
                    r5 = r1
                L8c:
                    java.util.ArrayList r12 = r11.mRoutes
                    int r12 = r12.size()
                    int r12 = r12 - r0
                L93:
                    if (r12 < r5) goto La5
                    java.util.ArrayList r1 = r11.mRoutes
                    java.lang.Object r1 = r1.remove(r12)
                    com.android.server.media.MediaRouterService$UserHandler$RouteRecord r1 = (com.android.server.media.MediaRouterService.UserHandler.RouteRecord) r1
                    r2 = 0
                    r1.updateDescriptor(r2)
                    int r12 = r12 + (-1)
                    r1 = r0
                    goto L93
                La5:
                    return r1
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.server.media.MediaRouterService.UserHandler.ProviderRecord.updateDescriptor(android.media.RemoteDisplayState):boolean");
            }

            public void appendClientState(MediaRouterClientState mediaRouterClientState) {
                int size = this.mRoutes.size();
                for (int i = 0; i < size; i++) {
                    mediaRouterClientState.routes.add(((RouteRecord) this.mRoutes.get(i)).getInfo());
                }
            }

            public RouteRecord findRouteByUniqueId(String str) {
                int size = this.mRoutes.size();
                for (int i = 0; i < size; i++) {
                    RouteRecord routeRecord = (RouteRecord) this.mRoutes.get(i);
                    if (routeRecord.getUniqueId().equals(str)) {
                        return routeRecord;
                    }
                }
                return null;
            }

            public final int findRouteByDescriptorId(String str) {
                int size = this.mRoutes.size();
                for (int i = 0; i < size; i++) {
                    if (((RouteRecord) this.mRoutes.get(i)).getDescriptorId().equals(str)) {
                        return i;
                    }
                }
                return -1;
            }

            public void dump(PrintWriter printWriter, String str) {
                printWriter.println(str + this);
                String str2 = str + "  ";
                this.mProvider.dump(printWriter, str2);
                int size = this.mRoutes.size();
                if (size != 0) {
                    for (int i = 0; i < size; i++) {
                        ((RouteRecord) this.mRoutes.get(i)).dump(printWriter, str2);
                    }
                    return;
                }
                printWriter.println(str2 + "<no routes>");
            }

            public String toString() {
                return "Provider " + this.mProvider.getFlattenedComponentName();
            }

            public final String assignRouteUniqueId(String str) {
                return this.mUniquePrefix + str;
            }
        }

        /* loaded from: classes2.dex */
        public final class RouteRecord {
            public RemoteDisplayState.RemoteDisplayInfo mDescriptor;
            public final String mDescriptorId;
            public MediaRouterClientState.RouteInfo mImmutableInfo;
            public final MediaRouterClientState.RouteInfo mMutableInfo;
            public final ProviderRecord mProviderRecord;

            public static int computePlaybackStream(RemoteDisplayState.RemoteDisplayInfo remoteDisplayInfo) {
                return 3;
            }

            public static int computePlaybackType(RemoteDisplayState.RemoteDisplayInfo remoteDisplayInfo) {
                return 1;
            }

            public static int computeSupportedTypes(RemoteDisplayState.RemoteDisplayInfo remoteDisplayInfo) {
                return 7;
            }

            public RouteRecord(ProviderRecord providerRecord, String str, String str2) {
                this.mProviderRecord = providerRecord;
                this.mDescriptorId = str;
                this.mMutableInfo = new MediaRouterClientState.RouteInfo(str2);
            }

            public RemoteDisplayProviderProxy getProvider() {
                return this.mProviderRecord.getProvider();
            }

            public String getDescriptorId() {
                return this.mDescriptorId;
            }

            public String getUniqueId() {
                return this.mMutableInfo.id;
            }

            public MediaRouterClientState.RouteInfo getInfo() {
                if (this.mImmutableInfo == null) {
                    this.mImmutableInfo = new MediaRouterClientState.RouteInfo(this.mMutableInfo);
                }
                return this.mImmutableInfo;
            }

            public boolean isValid() {
                return this.mDescriptor != null;
            }

            public boolean isEnabled() {
                return this.mMutableInfo.enabled;
            }

            public int getStatus() {
                return this.mMutableInfo.statusCode;
            }

            public boolean updateDescriptor(RemoteDisplayState.RemoteDisplayInfo remoteDisplayInfo) {
                boolean z = false;
                if (this.mDescriptor != remoteDisplayInfo) {
                    this.mDescriptor = remoteDisplayInfo;
                    if (remoteDisplayInfo != null) {
                        String computeName = computeName(remoteDisplayInfo);
                        if (!Objects.equals(this.mMutableInfo.name, computeName)) {
                            this.mMutableInfo.name = computeName;
                            z = true;
                        }
                        String computeDescription = computeDescription(remoteDisplayInfo);
                        if (!Objects.equals(this.mMutableInfo.description, computeDescription)) {
                            this.mMutableInfo.description = computeDescription;
                            z = true;
                        }
                        int computeSupportedTypes = computeSupportedTypes(remoteDisplayInfo);
                        MediaRouterClientState.RouteInfo routeInfo = this.mMutableInfo;
                        if (routeInfo.supportedTypes != computeSupportedTypes) {
                            routeInfo.supportedTypes = computeSupportedTypes;
                            z = true;
                        }
                        boolean computeEnabled = computeEnabled(remoteDisplayInfo);
                        MediaRouterClientState.RouteInfo routeInfo2 = this.mMutableInfo;
                        if (routeInfo2.enabled != computeEnabled) {
                            routeInfo2.enabled = computeEnabled;
                            z = true;
                        }
                        int computeStatusCode = computeStatusCode(remoteDisplayInfo);
                        MediaRouterClientState.RouteInfo routeInfo3 = this.mMutableInfo;
                        if (routeInfo3.statusCode != computeStatusCode) {
                            routeInfo3.statusCode = computeStatusCode;
                            z = true;
                        }
                        int computePlaybackType = computePlaybackType(remoteDisplayInfo);
                        MediaRouterClientState.RouteInfo routeInfo4 = this.mMutableInfo;
                        if (routeInfo4.playbackType != computePlaybackType) {
                            routeInfo4.playbackType = computePlaybackType;
                            z = true;
                        }
                        int computePlaybackStream = computePlaybackStream(remoteDisplayInfo);
                        MediaRouterClientState.RouteInfo routeInfo5 = this.mMutableInfo;
                        if (routeInfo5.playbackStream != computePlaybackStream) {
                            routeInfo5.playbackStream = computePlaybackStream;
                            z = true;
                        }
                        int computeVolume = computeVolume(remoteDisplayInfo);
                        MediaRouterClientState.RouteInfo routeInfo6 = this.mMutableInfo;
                        if (routeInfo6.volume != computeVolume) {
                            routeInfo6.volume = computeVolume;
                            z = true;
                        }
                        int computeVolumeMax = computeVolumeMax(remoteDisplayInfo);
                        MediaRouterClientState.RouteInfo routeInfo7 = this.mMutableInfo;
                        if (routeInfo7.volumeMax != computeVolumeMax) {
                            routeInfo7.volumeMax = computeVolumeMax;
                            z = true;
                        }
                        int computeVolumeHandling = computeVolumeHandling(remoteDisplayInfo);
                        MediaRouterClientState.RouteInfo routeInfo8 = this.mMutableInfo;
                        if (routeInfo8.volumeHandling != computeVolumeHandling) {
                            routeInfo8.volumeHandling = computeVolumeHandling;
                            z = true;
                        }
                        int computePresentationDisplayId = computePresentationDisplayId(remoteDisplayInfo);
                        MediaRouterClientState.RouteInfo routeInfo9 = this.mMutableInfo;
                        if (routeInfo9.presentationDisplayId != computePresentationDisplayId) {
                            routeInfo9.presentationDisplayId = computePresentationDisplayId;
                            z = true;
                        }
                    }
                }
                if (z) {
                    this.mImmutableInfo = null;
                }
                return z;
            }

            public void dump(PrintWriter printWriter, String str) {
                printWriter.println(str + this);
                String str2 = str + "  ";
                printWriter.println(str2 + "mMutableInfo=" + this.mMutableInfo);
                printWriter.println(str2 + "mDescriptorId=" + this.mDescriptorId);
                printWriter.println(str2 + "mDescriptor=" + this.mDescriptor);
            }

            public String toString() {
                return "Route " + this.mMutableInfo.name + " (" + this.mMutableInfo.id + ")";
            }

            public static String computeName(RemoteDisplayState.RemoteDisplayInfo remoteDisplayInfo) {
                return remoteDisplayInfo.name;
            }

            public static String computeDescription(RemoteDisplayState.RemoteDisplayInfo remoteDisplayInfo) {
                String str = remoteDisplayInfo.description;
                if (TextUtils.isEmpty(str)) {
                    return null;
                }
                return str;
            }

            public static boolean computeEnabled(RemoteDisplayState.RemoteDisplayInfo remoteDisplayInfo) {
                int i = remoteDisplayInfo.status;
                return i == 2 || i == 3 || i == 4;
            }

            public static int computeStatusCode(RemoteDisplayState.RemoteDisplayInfo remoteDisplayInfo) {
                int i = remoteDisplayInfo.status;
                if (i == 0) {
                    return 4;
                }
                if (i == 1) {
                    return 5;
                }
                if (i == 2) {
                    return 3;
                }
                if (i != 3) {
                    return i != 4 ? 0 : 6;
                }
                return 2;
            }

            public static int computeVolume(RemoteDisplayState.RemoteDisplayInfo remoteDisplayInfo) {
                int i = remoteDisplayInfo.volume;
                int i2 = remoteDisplayInfo.volumeMax;
                if (i < 0) {
                    return 0;
                }
                return i > i2 ? i2 : i;
            }

            public static int computeVolumeMax(RemoteDisplayState.RemoteDisplayInfo remoteDisplayInfo) {
                int i = remoteDisplayInfo.volumeMax;
                if (i > 0) {
                    return i;
                }
                return 0;
            }

            public static int computeVolumeHandling(RemoteDisplayState.RemoteDisplayInfo remoteDisplayInfo) {
                return remoteDisplayInfo.volumeHandling != 1 ? 0 : 1;
            }

            public static int computePresentationDisplayId(RemoteDisplayState.RemoteDisplayInfo remoteDisplayInfo) {
                int i = remoteDisplayInfo.presentationDisplayId;
                if (i < 0) {
                    return -1;
                }
                return i;
            }
        }
    }

    /* loaded from: classes2.dex */
    public class AudioPlayerActiveStateChangedListenerImpl implements AudioPlayerStateMonitor.OnAudioPlayerActiveStateChangedListener {
        public final Runnable mRestoreBluetoothA2dpRunnable;

        public AudioPlayerActiveStateChangedListenerImpl() {
            this.mRestoreBluetoothA2dpRunnable = new Runnable() { // from class: com.android.server.media.MediaRouterService$AudioPlayerActiveStateChangedListenerImpl$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    MediaRouterService.this.restoreBluetoothA2dp();
                }
            };
        }

        @Override // com.android.server.media.AudioPlayerStateMonitor.OnAudioPlayerActiveStateChangedListener
        public void onAudioPlayerActiveStateChanged(AudioPlaybackConfiguration audioPlaybackConfiguration, boolean z) {
            int i;
            boolean z2 = !z && audioPlaybackConfiguration.isActive();
            int clientUid = audioPlaybackConfiguration.getClientUid();
            int indexOf = MediaRouterService.this.mActivePlayerMinPriorityQueue.indexOf(audioPlaybackConfiguration.getPlayerInterfaceId());
            if (indexOf >= 0) {
                MediaRouterService.this.mActivePlayerMinPriorityQueue.remove(indexOf);
                MediaRouterService.this.mActivePlayerUidMinPriorityQueue.remove(indexOf);
            }
            if (z2) {
                MediaRouterService.this.mActivePlayerMinPriorityQueue.add(audioPlaybackConfiguration.getPlayerInterfaceId());
                MediaRouterService.this.mActivePlayerUidMinPriorityQueue.add(clientUid);
                i = clientUid;
            } else {
                i = MediaRouterService.this.mActivePlayerUidMinPriorityQueue.size() > 0 ? MediaRouterService.this.mActivePlayerUidMinPriorityQueue.get(MediaRouterService.this.mActivePlayerUidMinPriorityQueue.size() - 1) : -1;
            }
            MediaRouterService.this.mHandler.removeCallbacks(this.mRestoreBluetoothA2dpRunnable);
            if (i >= 0) {
                MediaRouterService.this.restoreRoute(i);
                if (MediaRouterService.DEBUG) {
                    Slog.d("MediaRouterService", "onAudioPlayerActiveStateChanged: uid=" + clientUid + ", active=" + z2 + ", restoreUid=" + i);
                    return;
                }
                return;
            }
            MediaRouterService.this.mHandler.postDelayed(this.mRestoreBluetoothA2dpRunnable, 500L);
            if (MediaRouterService.DEBUG) {
                Slog.d("MediaRouterService", "onAudioPlayerActiveStateChanged: uid=" + clientUid + ", active=" + z2 + ", delaying");
            }
        }
    }

    /* loaded from: classes2.dex */
    public class AudioRoutesObserverImpl extends IAudioRoutesObserver.Stub {
        public AudioRoutesObserverImpl() {
        }

        public void dispatchAudioRoutesChanged(AudioRoutesInfo audioRoutesInfo) {
            synchronized (MediaRouterService.this.mLock) {
                boolean z = true;
                if ("BT".equals(audioRoutesInfo.getSetForcePath()) && audioRoutesInfo.bluetoothName != null) {
                    MediaRouterService.this.mForceBluetoothA2dpOn = true;
                } else if ("OTHERS".equals(audioRoutesInfo.getSetForcePath())) {
                    MediaRouterService.this.mForceBluetoothA2dpOn = false;
                }
                Log.i("MediaRouterService", "dispatchAudioRoutesChanged setForcePath = " + audioRoutesInfo.getSetForcePath() + " mForceBluetoothA2dpOn = " + MediaRouterService.this.mForceBluetoothA2dpOn);
                int i = audioRoutesInfo.mainType;
                MediaRouterService mediaRouterService = MediaRouterService.this;
                if (i != mediaRouterService.mAudioRouteMainType) {
                    if ((i & 19) == 0) {
                        CharSequence charSequence = audioRoutesInfo.bluetoothName;
                        if (charSequence == null && mediaRouterService.mActiveBluetoothDevice == null) {
                            z = false;
                        }
                        mediaRouterService.mGlobalBluetoothA2dpOn = z;
                        if (charSequence != null && z) {
                            Log.w("MediaRouterService", "force restoreBluetoothA2dp()");
                            MediaRouterService.this.restoreBluetoothA2dp();
                            MediaRouterService.this.mForceBluetoothA2dpOn = false;
                        }
                    } else {
                        mediaRouterService.mGlobalBluetoothA2dpOn = false;
                    }
                    MediaRouterService.this.mAudioRouteMainType = audioRoutesInfo.mainType;
                }
            }
        }
    }
}
