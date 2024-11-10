package com.android.server.tv;

import android.app.ActivityManager;
import android.content.AttributionSource;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.pm.UserInfo;
import android.graphics.Rect;
import android.hardware.hdmi.HdmiDeviceInfo;
import android.media.PlaybackParams;
import android.media.tv.AdBuffer;
import android.media.tv.AdRequest;
import android.media.tv.AdResponse;
import android.media.tv.AitInfo;
import android.media.tv.BroadcastInfoRequest;
import android.media.tv.BroadcastInfoResponse;
import android.media.tv.DvbDeviceInfo;
import android.media.tv.ITvInputClient;
import android.media.tv.ITvInputHardware;
import android.media.tv.ITvInputHardwareCallback;
import android.media.tv.ITvInputManager;
import android.media.tv.ITvInputManagerCallback;
import android.media.tv.ITvInputService;
import android.media.tv.ITvInputServiceCallback;
import android.media.tv.ITvInputSession;
import android.media.tv.ITvInputSessionCallback;
import android.media.tv.TunedInfo;
import android.media.tv.TvContentRating;
import android.media.tv.TvContentRatingSystemInfo;
import android.media.tv.TvContract;
import android.media.tv.TvInputHardwareInfo;
import android.media.tv.TvInputInfo;
import android.media.tv.TvStreamConfig;
import android.media.tv.tunerresourcemanager.TunerResourceManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.SharedMemory;
import android.os.UserHandle;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import android.view.InputChannel;
import android.view.Surface;
import com.android.internal.content.PackageMonitor;
import com.android.internal.os.SomeArgs;
import com.android.internal.util.CollectionUtils;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.IndentingPrintWriter;
import com.android.server.IoThread;
import com.android.server.SystemService;
import com.android.server.display.DisplayPowerController2;
import com.android.server.tv.TvInputHardwareManager;
import dalvik.annotation.optimization.NeverCompile;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes3.dex */
public final class TvInputManagerService extends SystemService {
    public final ActivityManager mActivityManager;
    public final Context mContext;
    public int mCurrentUserId;
    public final Object mLock;
    public final Set mRunningProfiles;
    public final Map mSessionIdToSessionStateMap;
    public final TvInputHardwareManager mTvInputHardwareManager;
    public final UserManager mUserManager;
    public final SparseArray mUserStates;
    public final WatchLogHandler mWatchLogHandler;
    public static final Pattern sFrontEndDevicePattern = Pattern.compile("^dvb([0-9]+)\\.frontend([0-9]+)$");
    public static final Pattern sAdapterDirPattern = Pattern.compile("^adapter([0-9]+)$");
    public static final Pattern sFrontEndInAdapterDirPattern = Pattern.compile("^frontend([0-9]+)$");

    public static int getVideoUnavailableReasonForStatsd(int i) {
        int i2 = i + 100;
        if (i2 < 100 || i2 > 118) {
            return 100;
        }
        return i2;
    }

    public TvInputManagerService(Context context) {
        super(context);
        Object obj = new Object();
        this.mLock = obj;
        this.mCurrentUserId = 0;
        this.mRunningProfiles = new HashSet();
        this.mUserStates = new SparseArray();
        this.mSessionIdToSessionStateMap = new HashMap();
        this.mContext = context;
        this.mWatchLogHandler = new WatchLogHandler(context.getContentResolver(), IoThread.get().getLooper());
        this.mTvInputHardwareManager = new TvInputHardwareManager(context, new HardwareListener());
        this.mActivityManager = (ActivityManager) getContext().getSystemService("activity");
        this.mUserManager = (UserManager) getContext().getSystemService("user");
        synchronized (obj) {
            getOrCreateUserStateLocked(this.mCurrentUserId);
        }
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        publishBinderService("tv_input", new BinderService());
    }

    @Override // com.android.server.SystemService
    public void onBootPhase(int i) {
        if (i == 500) {
            registerBroadcastReceivers();
        } else if (i == 600) {
            synchronized (this.mLock) {
                buildTvInputListLocked(this.mCurrentUserId, null);
                buildTvContentRatingSystemListLocked(this.mCurrentUserId);
            }
        }
        this.mTvInputHardwareManager.onBootPhase(i);
    }

    @Override // com.android.server.SystemService
    public void onUserUnlocking(SystemService.TargetUser targetUser) {
        synchronized (this.mLock) {
            if (this.mCurrentUserId != targetUser.getUserIdentifier()) {
                return;
            }
            buildTvInputListLocked(this.mCurrentUserId, null);
            buildTvContentRatingSystemListLocked(this.mCurrentUserId);
        }
    }

    public final void registerBroadcastReceivers() {
        new PackageMonitor() { // from class: com.android.server.tv.TvInputManagerService.1
            public boolean onPackageChanged(String str, int i, String[] strArr) {
                return true;
            }

            public final void buildTvInputList(String[] strArr) {
                int changingUserId = getChangingUserId();
                synchronized (TvInputManagerService.this.mLock) {
                    if (TvInputManagerService.this.mCurrentUserId == changingUserId || TvInputManagerService.this.mRunningProfiles.contains(Integer.valueOf(changingUserId))) {
                        TvInputManagerService.this.buildTvInputListLocked(changingUserId, strArr);
                        TvInputManagerService.this.buildTvContentRatingSystemListLocked(changingUserId);
                    }
                }
            }

            public void onPackageUpdateFinished(String str, int i) {
                buildTvInputList(new String[]{str});
            }

            public void onPackagesAvailable(String[] strArr) {
                if (isReplacing()) {
                    buildTvInputList(strArr);
                }
            }

            public void onPackagesUnavailable(String[] strArr) {
                if (isReplacing()) {
                    buildTvInputList(strArr);
                }
            }

            public void onSomePackagesChanged() {
                if (isReplacing()) {
                    return;
                }
                buildTvInputList(null);
            }
        }.register(this.mContext, (Looper) null, UserHandle.ALL, true);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.USER_SWITCHED");
        intentFilter.addAction("android.intent.action.USER_REMOVED");
        intentFilter.addAction("android.intent.action.USER_STARTED");
        intentFilter.addAction("android.intent.action.USER_STOPPED");
        this.mContext.registerReceiverAsUser(new BroadcastReceiver() { // from class: com.android.server.tv.TvInputManagerService.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if ("android.intent.action.USER_SWITCHED".equals(action)) {
                    TvInputManagerService.this.switchUser(intent.getIntExtra("android.intent.extra.user_handle", 0));
                    return;
                }
                if ("android.intent.action.USER_REMOVED".equals(action)) {
                    TvInputManagerService.this.removeUser(intent.getIntExtra("android.intent.extra.user_handle", 0));
                    return;
                }
                if ("android.intent.action.USER_STARTED".equals(action)) {
                    TvInputManagerService.this.startUser(intent.getIntExtra("android.intent.extra.user_handle", 0));
                } else if ("android.intent.action.USER_STOPPED".equals(action)) {
                    TvInputManagerService.this.stopUser(intent.getIntExtra("android.intent.extra.user_handle", 0));
                }
            }
        }, UserHandle.ALL, intentFilter, null, null);
    }

    public static boolean hasHardwarePermission(PackageManager packageManager, ComponentName componentName) {
        return packageManager.checkPermission("android.permission.TV_INPUT_HARDWARE", componentName.getPackageName()) == 0;
    }

    public final void buildTvInputListLocked(int i, String[] strArr) {
        int intValue;
        UserState orCreateUserStateLocked = getOrCreateUserStateLocked(i);
        orCreateUserStateLocked.packageSet.clear();
        PackageManager packageManager = this.mContext.getPackageManager();
        List queryIntentServicesAsUser = packageManager.queryIntentServicesAsUser(new Intent("android.media.tv.TvInputService"), 132, i);
        ArrayList<TvInputInfo> arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        Iterator it = queryIntentServicesAsUser.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            ResolveInfo resolveInfo = (ResolveInfo) it.next();
            ServiceInfo serviceInfo = resolveInfo.serviceInfo;
            if ("android.permission.BIND_TV_INPUT".equals(serviceInfo.permission)) {
                ComponentName componentName = new ComponentName(serviceInfo.packageName, serviceInfo.name);
                if (hasHardwarePermission(packageManager, componentName)) {
                    arrayList2.add(componentName);
                    ServiceState serviceState = (ServiceState) orCreateUserStateLocked.serviceStateMap.get(componentName);
                    if (serviceState == null) {
                        orCreateUserStateLocked.serviceStateMap.put(componentName, new ServiceState(componentName, i));
                        updateServiceConnectionLocked(componentName, i);
                    } else {
                        arrayList.addAll(serviceState.hardwareInputMap.values());
                    }
                } else {
                    try {
                        arrayList.add(new TvInputInfo.Builder(this.mContext, resolveInfo).build());
                    } catch (Exception e) {
                        Slog.e("TvInputManagerService", "failed to load TV input " + serviceInfo.name, e);
                    }
                }
                orCreateUserStateLocked.packageSet.add(serviceInfo.packageName);
            } else {
                Slog.w("TvInputManagerService", "Skipping TV input " + serviceInfo.name + ": it does not require the permission android.permission.BIND_TV_INPUT");
            }
        }
        Collections.sort(arrayList, Comparator.comparing(new Function() { // from class: com.android.server.tv.TvInputManagerService$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((TvInputInfo) obj).getId();
            }
        }));
        HashMap hashMap = new HashMap();
        ArrayMap arrayMap = new ArrayMap(hashMap.size());
        for (TvInputInfo tvInputInfo : arrayList) {
            String id = tvInputInfo.getId();
            Integer num = (Integer) arrayMap.get(id);
            if (num == null) {
                Integer num2 = 1;
                intValue = num2.intValue();
            } else {
                intValue = num.intValue() + 1;
            }
            Integer valueOf = Integer.valueOf(intValue);
            arrayMap.put(id, valueOf);
            TvInputState tvInputState = (TvInputState) orCreateUserStateLocked.inputMap.get(id);
            if (tvInputState == null) {
                tvInputState = new TvInputState();
            }
            tvInputState.info = tvInputInfo;
            tvInputState.uid = getInputUid(tvInputInfo);
            hashMap.put(id, tvInputState);
            tvInputState.inputNumber = valueOf.intValue();
        }
        for (String str : hashMap.keySet()) {
            if (!orCreateUserStateLocked.inputMap.containsKey(str)) {
                notifyInputAddedLocked(orCreateUserStateLocked, str);
            } else if (strArr != null) {
                ComponentName component = ((TvInputState) hashMap.get(str)).info.getComponent();
                int length = strArr.length;
                int i2 = 0;
                while (true) {
                    if (i2 < length) {
                        if (component.getPackageName().equals(strArr[i2])) {
                            updateServiceConnectionLocked(component, i);
                            notifyInputUpdatedLocked(orCreateUserStateLocked, str);
                            break;
                        }
                        i2++;
                    }
                }
            }
        }
        for (String str2 : orCreateUserStateLocked.inputMap.keySet()) {
            if (!hashMap.containsKey(str2)) {
                ServiceState serviceState2 = (ServiceState) orCreateUserStateLocked.serviceStateMap.get(((TvInputState) orCreateUserStateLocked.inputMap.get(str2)).info.getComponent());
                if (serviceState2 != null) {
                    abortPendingCreateSessionRequestsLocked(serviceState2, str2, i);
                }
                notifyInputRemovedLocked(orCreateUserStateLocked, str2);
            }
        }
        Iterator it2 = orCreateUserStateLocked.serviceStateMap.values().iterator();
        while (it2.hasNext()) {
            ServiceState serviceState3 = (ServiceState) it2.next();
            if (serviceState3.isHardware && !arrayList2.contains(serviceState3.component)) {
                it2.remove();
            }
        }
        orCreateUserStateLocked.inputMap.clear();
        orCreateUserStateLocked.inputMap = hashMap;
    }

    public final int getInputUid(TvInputInfo tvInputInfo) {
        try {
            return getContext().getPackageManager().getApplicationInfo(tvInputInfo.getServiceInfo().packageName, 0).uid;
        } catch (PackageManager.NameNotFoundException e) {
            Slog.w("TvInputManagerService", "Unable to get UID for  " + tvInputInfo, e);
            return -1;
        }
    }

    public final void buildTvContentRatingSystemListLocked(int i) {
        UserState orCreateUserStateLocked = getOrCreateUserStateLocked(i);
        orCreateUserStateLocked.contentRatingSystemList.clear();
        Iterator<ResolveInfo> it = this.mContext.getPackageManager().queryBroadcastReceivers(new Intent("android.media.tv.action.QUERY_CONTENT_RATING_SYSTEMS"), 128).iterator();
        while (it.hasNext()) {
            ActivityInfo activityInfo = it.next().activityInfo;
            Bundle bundle = activityInfo.metaData;
            if (bundle != null) {
                int i2 = bundle.getInt("android.media.tv.metadata.CONTENT_RATING_SYSTEMS");
                if (i2 == 0) {
                    Slog.w("TvInputManagerService", "Missing meta-data 'android.media.tv.metadata.CONTENT_RATING_SYSTEMS' on receiver " + activityInfo.packageName + "/" + activityInfo.name);
                } else {
                    orCreateUserStateLocked.contentRatingSystemList.add(TvContentRatingSystemInfo.createTvContentRatingSystemInfo(i2, activityInfo.applicationInfo));
                }
            }
        }
    }

    public final void startUser(int i) {
        synchronized (this.mLock) {
            if (i != this.mCurrentUserId && !this.mRunningProfiles.contains(Integer.valueOf(i))) {
                UserInfo userInfo = this.mUserManager.getUserInfo(i);
                UserInfo profileParent = this.mUserManager.getProfileParent(i);
                if (userInfo.isProfile() && profileParent != null && profileParent.id == this.mCurrentUserId) {
                    startProfileLocked(i);
                }
            }
        }
    }

    public final void stopUser(int i) {
        synchronized (this.mLock) {
            if (i == this.mCurrentUserId) {
                switchUser(ActivityManager.getCurrentUser());
                return;
            }
            releaseSessionOfUserLocked(i);
            unbindServiceOfUserLocked(i);
            this.mRunningProfiles.remove(Integer.valueOf(i));
        }
    }

    public final void startProfileLocked(int i) {
        this.mRunningProfiles.add(Integer.valueOf(i));
        buildTvInputListLocked(i, null);
        buildTvContentRatingSystemListLocked(i);
    }

    public final void switchUser(int i) {
        synchronized (this.mLock) {
            if (this.mCurrentUserId == i) {
                return;
            }
            if (this.mUserManager.getUserInfo(i).isProfile()) {
                Slog.w("TvInputManagerService", "cannot switch to a profile!");
                return;
            }
            Iterator it = this.mRunningProfiles.iterator();
            while (it.hasNext()) {
                int intValue = ((Integer) it.next()).intValue();
                releaseSessionOfUserLocked(intValue);
                unbindServiceOfUserLocked(intValue);
            }
            this.mRunningProfiles.clear();
            releaseSessionOfUserLocked(this.mCurrentUserId);
            unbindServiceOfUserLocked(this.mCurrentUserId);
            this.mCurrentUserId = i;
            buildTvInputListLocked(i, null);
            buildTvContentRatingSystemListLocked(i);
            this.mWatchLogHandler.obtainMessage(3, getContentResolverForUser(i)).sendToTarget();
        }
    }

    public final void releaseSessionOfUserLocked(int i) {
        UserState userStateLocked = getUserStateLocked(i);
        if (userStateLocked == null) {
            return;
        }
        ArrayList<SessionState> arrayList = new ArrayList();
        for (SessionState sessionState : userStateLocked.sessionStateMap.values()) {
            if (sessionState.session != null && !sessionState.isRecordingSession) {
                arrayList.add(sessionState);
            }
        }
        boolean z = false;
        for (SessionState sessionState2 : arrayList) {
            try {
                try {
                    sessionState2.session.release();
                    sessionState2.currentChannel = null;
                    if (sessionState2.isCurrent) {
                        sessionState2.isCurrent = false;
                        z = true;
                    }
                } catch (RemoteException e) {
                    Slog.e("TvInputManagerService", "error in release", e);
                    if (!z) {
                    }
                }
                if (!z) {
                    clearSessionAndNotifyClientLocked(sessionState2);
                }
                notifyCurrentChannelInfosUpdatedLocked(userStateLocked);
                clearSessionAndNotifyClientLocked(sessionState2);
            } catch (Throwable th) {
                if (z) {
                    notifyCurrentChannelInfosUpdatedLocked(userStateLocked);
                }
                throw th;
            }
        }
    }

    public final void unbindServiceOfUserLocked(int i) {
        UserState userStateLocked = getUserStateLocked(i);
        if (userStateLocked == null) {
            return;
        }
        Iterator it = userStateLocked.serviceStateMap.keySet().iterator();
        while (it.hasNext()) {
            ServiceState serviceState = (ServiceState) userStateLocked.serviceStateMap.get((ComponentName) it.next());
            if (serviceState != null && serviceState.sessionTokens.isEmpty()) {
                if (serviceState.callback != null) {
                    try {
                        serviceState.service.unregisterCallback(serviceState.callback);
                    } catch (RemoteException e) {
                        Slog.e("TvInputManagerService", "error in unregisterCallback", e);
                    }
                }
                this.mContext.unbindService(serviceState.connection);
                it.remove();
            }
        }
    }

    public final void clearSessionAndNotifyClientLocked(SessionState sessionState) {
        if (sessionState.client != null) {
            try {
                sessionState.client.onSessionReleased(sessionState.seq);
            } catch (RemoteException e) {
                Slog.e("TvInputManagerService", "error in onSessionReleased", e);
            }
        }
        for (SessionState sessionState2 : getOrCreateUserStateLocked(sessionState.userId).sessionStateMap.values()) {
            if (sessionState.sessionToken == sessionState2.hardwareSessionToken) {
                releaseSessionLocked(sessionState2.sessionToken, 1000, sessionState.userId);
                try {
                    sessionState2.client.onSessionReleased(sessionState2.seq);
                } catch (RemoteException e2) {
                    Slog.e("TvInputManagerService", "error in onSessionReleased", e2);
                }
            }
        }
        removeSessionStateLocked(sessionState.sessionToken, sessionState.userId);
    }

    public final void removeUser(int i) {
        synchronized (this.mLock) {
            UserState userStateLocked = getUserStateLocked(i);
            if (userStateLocked == null) {
                return;
            }
            boolean z = false;
            for (SessionState sessionState : userStateLocked.sessionStateMap.values()) {
                if (sessionState.session != null) {
                    try {
                        try {
                            sessionState.session.release();
                            sessionState.currentChannel = null;
                            if (sessionState.isCurrent) {
                                sessionState.isCurrent = false;
                                z = true;
                            }
                        } catch (RemoteException e) {
                            Slog.e("TvInputManagerService", "error in release", e);
                            if (z) {
                            }
                        }
                        if (z) {
                            notifyCurrentChannelInfosUpdatedLocked(userStateLocked);
                        }
                    } catch (Throwable th) {
                        if (z) {
                            notifyCurrentChannelInfosUpdatedLocked(userStateLocked);
                        }
                        throw th;
                    }
                }
            }
            userStateLocked.sessionStateMap.clear();
            for (ServiceState serviceState : userStateLocked.serviceStateMap.values()) {
                if (serviceState.service != null) {
                    if (serviceState.callback != null) {
                        try {
                            serviceState.service.unregisterCallback(serviceState.callback);
                        } catch (RemoteException e2) {
                            Slog.e("TvInputManagerService", "error in unregisterCallback", e2);
                        }
                    }
                    this.mContext.unbindService(serviceState.connection);
                }
            }
            userStateLocked.serviceStateMap.clear();
            userStateLocked.inputMap.clear();
            userStateLocked.packageSet.clear();
            userStateLocked.contentRatingSystemList.clear();
            userStateLocked.clientStateMap.clear();
            userStateLocked.mCallbacks.kill();
            userStateLocked.mainSessionToken = null;
            this.mRunningProfiles.remove(Integer.valueOf(i));
            this.mUserStates.remove(i);
            if (i == this.mCurrentUserId) {
                switchUser(0);
            }
        }
    }

    public final ContentResolver getContentResolverForUser(int i) {
        Context context;
        UserHandle userHandle = new UserHandle(i);
        try {
            context = this.mContext.createPackageContextAsUser("android", 0, userHandle);
        } catch (PackageManager.NameNotFoundException unused) {
            Slog.e("TvInputManagerService", "failed to create package context as user " + userHandle);
            context = this.mContext;
        }
        return context.getContentResolver();
    }

    public final UserState getOrCreateUserStateLocked(int i) {
        UserState userStateLocked = getUserStateLocked(i);
        if (userStateLocked != null) {
            return userStateLocked;
        }
        UserState userState = new UserState(this.mContext, i);
        this.mUserStates.put(i, userState);
        return userState;
    }

    public final ServiceState getServiceStateLocked(ComponentName componentName, int i) {
        ServiceState serviceState = (ServiceState) getOrCreateUserStateLocked(i).serviceStateMap.get(componentName);
        if (serviceState != null) {
            return serviceState;
        }
        throw new IllegalStateException("Service state not found for " + componentName + " (userId=" + i + ")");
    }

    public final SessionState getSessionStateLocked(IBinder iBinder, int i, int i2) {
        return getSessionStateLocked(iBinder, i, getOrCreateUserStateLocked(i2));
    }

    public final SessionState getSessionStateLocked(IBinder iBinder, int i, UserState userState) {
        SessionState sessionState = (SessionState) userState.sessionStateMap.get(iBinder);
        if (sessionState == null) {
            throw new SessionNotFoundException("Session state not found for token " + iBinder);
        }
        if (i == 1000 || i == sessionState.callingUid) {
            return sessionState;
        }
        throw new SecurityException("Illegal access to the session with token " + iBinder + " from uid " + i);
    }

    public final ITvInputSession getSessionLocked(IBinder iBinder, int i, int i2) {
        return getSessionLocked(getSessionStateLocked(iBinder, i, i2));
    }

    public final ITvInputSession getSessionLocked(SessionState sessionState) {
        ITvInputSession iTvInputSession = sessionState.session;
        if (iTvInputSession != null) {
            return iTvInputSession;
        }
        throw new IllegalStateException("Session not yet created for token " + sessionState.sessionToken);
    }

    public final int resolveCallingUserId(int i, int i2, int i3, String str) {
        return ActivityManager.handleIncomingUser(i, i2, i3, false, false, str, null);
    }

    public final void updateServiceConnectionLocked(ComponentName componentName, int i) {
        boolean z;
        UserState orCreateUserStateLocked = getOrCreateUserStateLocked(i);
        ServiceState serviceState = (ServiceState) orCreateUserStateLocked.serviceStateMap.get(componentName);
        if (serviceState == null) {
            return;
        }
        if (serviceState.reconnecting) {
            if (!serviceState.sessionTokens.isEmpty()) {
                return;
            } else {
                serviceState.reconnecting = false;
            }
        }
        if (i == this.mCurrentUserId || this.mRunningProfiles.contains(Integer.valueOf(i))) {
            z = !serviceState.sessionTokens.isEmpty() || serviceState.isHardware;
        } else {
            z = !serviceState.sessionTokens.isEmpty();
        }
        if (serviceState.service == null && z) {
            if (serviceState.bound) {
                return;
            }
            serviceState.bound = this.mContext.bindServiceAsUser(new Intent("android.media.tv.TvInputService").setComponent(componentName), serviceState.connection, 33554433, new UserHandle(i));
        } else {
            if (serviceState.service == null || z) {
                return;
            }
            this.mContext.unbindService(serviceState.connection);
            orCreateUserStateLocked.serviceStateMap.remove(componentName);
        }
    }

    public final void abortPendingCreateSessionRequestsLocked(ServiceState serviceState, String str, int i) {
        UserState orCreateUserStateLocked = getOrCreateUserStateLocked(i);
        ArrayList<SessionState> arrayList = new ArrayList();
        Iterator it = serviceState.sessionTokens.iterator();
        while (it.hasNext()) {
            SessionState sessionState = (SessionState) orCreateUserStateLocked.sessionStateMap.get((IBinder) it.next());
            if (sessionState.session == null && (str == null || sessionState.inputId.equals(str))) {
                arrayList.add(sessionState);
            }
        }
        for (SessionState sessionState2 : arrayList) {
            removeSessionStateLocked(sessionState2.sessionToken, sessionState2.userId);
            sendSessionTokenToClientLocked(sessionState2.client, sessionState2.inputId, null, null, sessionState2.seq);
        }
        updateServiceConnectionLocked(serviceState.component, i);
    }

    public final boolean createSessionInternalLocked(ITvInputService iTvInputService, IBinder iBinder, int i) {
        boolean z;
        SessionState sessionState = (SessionState) getOrCreateUserStateLocked(i).sessionStateMap.get(iBinder);
        InputChannel[] openInputChannelPair = InputChannel.openInputChannelPair(iBinder.toString());
        SessionCallback sessionCallback = new SessionCallback(sessionState, openInputChannelPair);
        try {
            if (sessionState.isRecordingSession) {
                iTvInputService.createRecordingSession(sessionCallback, sessionState.inputId, sessionState.sessionId);
            } else {
                iTvInputService.createSession(openInputChannelPair[1], sessionCallback, sessionState.inputId, sessionState.sessionId, sessionState.tvAppAttributionSource);
            }
            z = true;
        } catch (RemoteException e) {
            Slog.e("TvInputManagerService", "error in createSession", e);
            sendSessionTokenToClientLocked(sessionState.client, sessionState.inputId, null, null, sessionState.seq);
            z = false;
        }
        openInputChannelPair[1].dispose();
        return z;
    }

    public final void sendSessionTokenToClientLocked(ITvInputClient iTvInputClient, String str, IBinder iBinder, InputChannel inputChannel, int i) {
        try {
            iTvInputClient.onSessionCreated(str, iBinder, inputChannel, i);
        } catch (RemoteException e) {
            Slog.e("TvInputManagerService", "error in onSessionCreated", e);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x003a, code lost:
    
        if (r1 != null) goto L14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x003c, code lost:
    
        r1.session = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0053, code lost:
    
        removeSessionStateLocked(r6, r8);
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0056, code lost:
    
        return r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0050, code lost:
    
        if (r1 == null) goto L26;
     */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0059  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.android.server.tv.TvInputManagerService.SessionState releaseSessionLocked(android.os.IBinder r6, int r7, int r8) {
        /*
            r5 = this;
            r0 = 0
            com.android.server.tv.TvInputManagerService$SessionState r1 = r5.getSessionStateLocked(r6, r7, r8)     // Catch: java.lang.Throwable -> L44 java.lang.Throwable -> L47
            com.android.server.tv.TvInputManagerService$UserState r2 = r5.getOrCreateUserStateLocked(r8)     // Catch: java.lang.Throwable -> L40 java.lang.Throwable -> L42
            android.media.tv.ITvInputSession r3 = com.android.server.tv.TvInputManagerService.SessionState.m11995$$Nest$fgetsession(r1)     // Catch: java.lang.Throwable -> L40 java.lang.Throwable -> L42
            r4 = 0
            if (r3 == 0) goto L2b
            android.os.IBinder r3 = com.android.server.tv.TvInputManagerService.UserState.m12021$$Nest$fgetmainSessionToken(r2)     // Catch: java.lang.Throwable -> L40 java.lang.Throwable -> L42
            if (r6 != r3) goto L19
            r5.setMainLocked(r6, r4, r7, r8)     // Catch: java.lang.Throwable -> L40 java.lang.Throwable -> L42
        L19:
            android.media.tv.ITvInputSession r7 = com.android.server.tv.TvInputManagerService.SessionState.m11995$$Nest$fgetsession(r1)     // Catch: java.lang.Throwable -> L40 java.lang.Throwable -> L42
            android.os.IBinder r7 = r7.asBinder()     // Catch: java.lang.Throwable -> L40 java.lang.Throwable -> L42
            r7.unlinkToDeath(r1, r4)     // Catch: java.lang.Throwable -> L40 java.lang.Throwable -> L42
            android.media.tv.ITvInputSession r7 = com.android.server.tv.TvInputManagerService.SessionState.m11995$$Nest$fgetsession(r1)     // Catch: java.lang.Throwable -> L40 java.lang.Throwable -> L42
            r7.release()     // Catch: java.lang.Throwable -> L40 java.lang.Throwable -> L42
        L2b:
            com.android.server.tv.TvInputManagerService.SessionState.m12000$$Nest$fputcurrentChannel(r1, r0)     // Catch: java.lang.Throwable -> L40 java.lang.Throwable -> L42
            boolean r7 = com.android.server.tv.TvInputManagerService.SessionState.m11990$$Nest$fgetisCurrent(r1)     // Catch: java.lang.Throwable -> L40 java.lang.Throwable -> L42
            if (r7 == 0) goto L3a
            com.android.server.tv.TvInputManagerService.SessionState.m12002$$Nest$fputisCurrent(r1, r4)     // Catch: java.lang.Throwable -> L40 java.lang.Throwable -> L42
            r5.notifyCurrentChannelInfosUpdatedLocked(r2)     // Catch: java.lang.Throwable -> L40 java.lang.Throwable -> L42
        L3a:
            if (r1 == 0) goto L53
        L3c:
            com.android.server.tv.TvInputManagerService.SessionState.m12005$$Nest$fputsession(r1, r0)
            goto L53
        L40:
            r5 = move-exception
            goto L57
        L42:
            r7 = move-exception
            goto L49
        L44:
            r5 = move-exception
            r1 = r0
            goto L57
        L47:
            r7 = move-exception
            r1 = r0
        L49:
            java.lang.String r2 = "TvInputManagerService"
            java.lang.String r3 = "error in releaseSession"
            android.util.Slog.e(r2, r3, r7)     // Catch: java.lang.Throwable -> L40
            if (r1 == 0) goto L53
            goto L3c
        L53:
            r5.removeSessionStateLocked(r6, r8)
            return r1
        L57:
            if (r1 == 0) goto L5c
            com.android.server.tv.TvInputManagerService.SessionState.m12005$$Nest$fputsession(r1, r0)
        L5c:
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.tv.TvInputManagerService.releaseSessionLocked(android.os.IBinder, int, int):com.android.server.tv.TvInputManagerService$SessionState");
    }

    public final void removeSessionStateLocked(IBinder iBinder, int i) {
        UserState orCreateUserStateLocked = getOrCreateUserStateLocked(i);
        if (iBinder == orCreateUserStateLocked.mainSessionToken) {
            orCreateUserStateLocked.mainSessionToken = null;
        }
        SessionState sessionState = (SessionState) orCreateUserStateLocked.sessionStateMap.remove(iBinder);
        if (sessionState == null) {
            Slog.e("TvInputManagerService", "sessionState null, no more remove session action!");
            return;
        }
        ClientState clientState = (ClientState) orCreateUserStateLocked.clientStateMap.get(sessionState.client.asBinder());
        if (clientState != null) {
            clientState.sessionTokens.remove(iBinder);
            if (clientState.isEmpty()) {
                orCreateUserStateLocked.clientStateMap.remove(sessionState.client.asBinder());
                sessionState.client.asBinder().unlinkToDeath(clientState, 0);
            }
        }
        this.mSessionIdToSessionStateMap.remove(sessionState.sessionId);
        ServiceState serviceState = (ServiceState) orCreateUserStateLocked.serviceStateMap.get(sessionState.componentName);
        if (serviceState != null) {
            serviceState.sessionTokens.remove(iBinder);
        }
        updateServiceConnectionLocked(sessionState.componentName, i);
        SomeArgs obtain = SomeArgs.obtain();
        obtain.arg1 = iBinder;
        obtain.arg2 = Long.valueOf(System.currentTimeMillis());
        this.mWatchLogHandler.obtainMessage(2, obtain).sendToTarget();
    }

    public final void setMainLocked(IBinder iBinder, boolean z, int i, int i2) {
        try {
            SessionState sessionStateLocked = getSessionStateLocked(iBinder, i, i2);
            if (sessionStateLocked.hardwareSessionToken != null) {
                sessionStateLocked = getSessionStateLocked(sessionStateLocked.hardwareSessionToken, 1000, i2);
            }
            if (getServiceStateLocked(sessionStateLocked.componentName, i2).isHardware) {
                getSessionLocked(sessionStateLocked).setMain(z);
                if (sessionStateLocked.isMainSession != z) {
                    UserState userStateLocked = getUserStateLocked(i2);
                    sessionStateLocked.isMainSession = z;
                    notifyCurrentChannelInfosUpdatedLocked(userStateLocked);
                }
            }
        } catch (RemoteException | SessionNotFoundException e) {
            Slog.e("TvInputManagerService", "error in setMain", e);
        }
    }

    public final void notifyInputAddedLocked(UserState userState, String str) {
        int beginBroadcast = userState.mCallbacks.beginBroadcast();
        for (int i = 0; i < beginBroadcast; i++) {
            try {
                userState.mCallbacks.getBroadcastItem(i).onInputAdded(str);
            } catch (RemoteException e) {
                Slog.e("TvInputManagerService", "failed to report added input to callback", e);
            }
        }
        userState.mCallbacks.finishBroadcast();
    }

    public final void notifyInputRemovedLocked(UserState userState, String str) {
        int beginBroadcast = userState.mCallbacks.beginBroadcast();
        for (int i = 0; i < beginBroadcast; i++) {
            try {
                userState.mCallbacks.getBroadcastItem(i).onInputRemoved(str);
            } catch (RemoteException e) {
                Slog.e("TvInputManagerService", "failed to report removed input to callback", e);
            }
        }
        userState.mCallbacks.finishBroadcast();
    }

    public final void notifyInputUpdatedLocked(UserState userState, String str) {
        int beginBroadcast = userState.mCallbacks.beginBroadcast();
        for (int i = 0; i < beginBroadcast; i++) {
            try {
                userState.mCallbacks.getBroadcastItem(i).onInputUpdated(str);
            } catch (RemoteException e) {
                Slog.e("TvInputManagerService", "failed to report updated input to callback", e);
            }
        }
        userState.mCallbacks.finishBroadcast();
    }

    public final void notifyInputStateChangedLocked(UserState userState, String str, int i, ITvInputManagerCallback iTvInputManagerCallback) {
        if (iTvInputManagerCallback == null) {
            int beginBroadcast = userState.mCallbacks.beginBroadcast();
            for (int i2 = 0; i2 < beginBroadcast; i2++) {
                try {
                    userState.mCallbacks.getBroadcastItem(i2).onInputStateChanged(str, i);
                } catch (RemoteException e) {
                    Slog.e("TvInputManagerService", "failed to report state change to callback", e);
                }
            }
            userState.mCallbacks.finishBroadcast();
            return;
        }
        try {
            iTvInputManagerCallback.onInputStateChanged(str, i);
        } catch (RemoteException e2) {
            Slog.e("TvInputManagerService", "failed to report state change to callback", e2);
        }
    }

    public final void notifyCurrentChannelInfosUpdatedLocked(UserState userState) {
        int beginBroadcast = userState.mCallbacks.beginBroadcast();
        for (int i = 0; i < beginBroadcast; i++) {
            try {
                ITvInputManagerCallback broadcastItem = userState.mCallbacks.getBroadcastItem(i);
                Pair pair = (Pair) userState.callbackPidUidMap.get(broadcastItem);
                if (this.mContext.checkPermission("android.permission.ACCESS_TUNED_INFO", ((Integer) pair.first).intValue(), ((Integer) pair.second).intValue()) == 0) {
                    broadcastItem.onCurrentTunedInfosUpdated(getCurrentTunedInfosInternalLocked(userState, ((Integer) pair.first).intValue(), ((Integer) pair.second).intValue()));
                }
            } catch (RemoteException e) {
                Slog.e("TvInputManagerService", "failed to report updated current channel infos to callback", e);
            }
        }
        userState.mCallbacks.finishBroadcast();
    }

    public final void updateTvInputInfoLocked(UserState userState, TvInputInfo tvInputInfo) {
        String id = tvInputInfo.getId();
        TvInputState tvInputState = (TvInputState) userState.inputMap.get(id);
        if (tvInputState == null) {
            Slog.e("TvInputManagerService", "failed to set input info - unknown input id " + id);
            return;
        }
        tvInputState.info = tvInputInfo;
        tvInputState.uid = getInputUid(tvInputInfo);
        int beginBroadcast = userState.mCallbacks.beginBroadcast();
        for (int i = 0; i < beginBroadcast; i++) {
            try {
                userState.mCallbacks.getBroadcastItem(i).onTvInputInfoUpdated(tvInputInfo);
            } catch (RemoteException e) {
                Slog.e("TvInputManagerService", "failed to report updated input info to callback", e);
            }
        }
        userState.mCallbacks.finishBroadcast();
    }

    public final void setStateLocked(String str, int i, int i2) {
        UserState orCreateUserStateLocked = getOrCreateUserStateLocked(i2);
        TvInputState tvInputState = (TvInputState) orCreateUserStateLocked.inputMap.get(str);
        if (tvInputState == null) {
            Slog.e("TvInputManagerService", "failed to setStateLocked - unknown input id " + str);
            return;
        }
        ServiceState serviceState = (ServiceState) orCreateUserStateLocked.serviceStateMap.get(tvInputState.info.getComponent());
        int i3 = tvInputState.state;
        tvInputState.state = i;
        if ((serviceState == null || serviceState.service != null || (serviceState.sessionTokens.isEmpty() && !serviceState.isHardware)) && i3 != i) {
            notifyInputStateChangedLocked(orCreateUserStateLocked, str, i, null);
        }
    }

    /* loaded from: classes3.dex */
    public final class BinderService extends ITvInputManager.Stub {
        public BinderService() {
        }

        public List getTvInputList(int i) {
            ArrayList arrayList;
            int resolveCallingUserId = TvInputManagerService.this.resolveCallingUserId(Binder.getCallingPid(), Binder.getCallingUid(), i, "getTvInputList");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    UserState orCreateUserStateLocked = TvInputManagerService.this.getOrCreateUserStateLocked(resolveCallingUserId);
                    arrayList = new ArrayList();
                    Iterator it = orCreateUserStateLocked.inputMap.values().iterator();
                    while (it.hasNext()) {
                        arrayList.add(((TvInputState) it.next()).info);
                    }
                }
                return arrayList;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public TvInputInfo getTvInputInfo(String str, int i) {
            TvInputInfo tvInputInfo;
            int resolveCallingUserId = TvInputManagerService.this.resolveCallingUserId(Binder.getCallingPid(), Binder.getCallingUid(), i, "getTvInputInfo");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    TvInputState tvInputState = (TvInputState) TvInputManagerService.this.getOrCreateUserStateLocked(resolveCallingUserId).inputMap.get(str);
                    tvInputInfo = tvInputState == null ? null : tvInputState.info;
                }
                return tvInputInfo;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void updateTvInputInfo(TvInputInfo tvInputInfo, int i) {
            String str = tvInputInfo.getServiceInfo().packageName;
            String callingPackageName = getCallingPackageName();
            if (!TextUtils.equals(str, callingPackageName) && TvInputManagerService.this.mContext.checkCallingPermission("android.permission.WRITE_SECURE_SETTINGS") != 0) {
                throw new IllegalArgumentException("calling package " + callingPackageName + " is not allowed to change TvInputInfo for " + str);
            }
            int resolveCallingUserId = TvInputManagerService.this.resolveCallingUserId(Binder.getCallingPid(), Binder.getCallingUid(), i, "updateTvInputInfo");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    TvInputManagerService.this.updateTvInputInfoLocked(TvInputManagerService.this.getOrCreateUserStateLocked(resolveCallingUserId), tvInputInfo);
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final String getCallingPackageName() {
            String[] packagesForUid = TvInputManagerService.this.mContext.getPackageManager().getPackagesForUid(Binder.getCallingUid());
            return (packagesForUid == null || packagesForUid.length <= 0) ? "unknown" : packagesForUid[0];
        }

        public int getTvInputState(String str, int i) {
            int i2;
            int resolveCallingUserId = TvInputManagerService.this.resolveCallingUserId(Binder.getCallingPid(), Binder.getCallingUid(), i, "getTvInputState");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    TvInputState tvInputState = (TvInputState) TvInputManagerService.this.getOrCreateUserStateLocked(resolveCallingUserId).inputMap.get(str);
                    i2 = tvInputState == null ? 0 : tvInputState.state;
                }
                return i2;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public List getAvailableExtensionInterfaceNames(String str, int i) {
            ITvInputService iTvInputService;
            ServiceState serviceState;
            ensureTisExtensionInterfacePermission();
            int callingUid = Binder.getCallingUid();
            int callingPid = Binder.getCallingPid();
            int resolveCallingUserId = TvInputManagerService.this.resolveCallingUserId(callingPid, callingUid, i, "getAvailableExtensionInterfaceNames");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    UserState orCreateUserStateLocked = TvInputManagerService.this.getOrCreateUserStateLocked(resolveCallingUserId);
                    TvInputState tvInputState = (TvInputState) orCreateUserStateLocked.inputMap.get(str);
                    iTvInputService = (tvInputState == null || (serviceState = (ServiceState) orCreateUserStateLocked.serviceStateMap.get(tvInputState.info.getComponent())) == null || !serviceState.isHardware || serviceState.service == null) ? null : serviceState.service;
                }
                if (iTvInputService != null) {
                    try {
                        ArrayList arrayList = new ArrayList();
                        for (String str2 : CollectionUtils.emptyIfNull(iTvInputService.getAvailableExtensionInterfaceNames())) {
                            String extensionInterfacePermission = iTvInputService.getExtensionInterfacePermission(str2);
                            if (extensionInterfacePermission == null || TvInputManagerService.this.mContext.checkPermission(extensionInterfacePermission, callingPid, callingUid) == 0) {
                                arrayList.add(str2);
                            }
                        }
                        return arrayList;
                    } catch (RemoteException e) {
                        Slog.e("TvInputManagerService", "error in getAvailableExtensionInterfaceNames or getExtensionInterfacePermission", e);
                    }
                }
                return new ArrayList();
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public IBinder getExtensionInterface(String str, String str2, int i) {
            ITvInputService iTvInputService;
            ServiceState serviceState;
            ensureTisExtensionInterfacePermission();
            int callingUid = Binder.getCallingUid();
            int callingPid = Binder.getCallingPid();
            int resolveCallingUserId = TvInputManagerService.this.resolveCallingUserId(callingPid, callingUid, i, "getExtensionInterface");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    UserState orCreateUserStateLocked = TvInputManagerService.this.getOrCreateUserStateLocked(resolveCallingUserId);
                    TvInputState tvInputState = (TvInputState) orCreateUserStateLocked.inputMap.get(str);
                    iTvInputService = (tvInputState == null || (serviceState = (ServiceState) orCreateUserStateLocked.serviceStateMap.get(tvInputState.info.getComponent())) == null || !serviceState.isHardware || serviceState.service == null) ? null : serviceState.service;
                }
                if (iTvInputService != null) {
                    try {
                        String extensionInterfacePermission = iTvInputService.getExtensionInterfacePermission(str2);
                        if (extensionInterfacePermission == null || TvInputManagerService.this.mContext.checkPermission(extensionInterfacePermission, callingPid, callingUid) == 0) {
                            return iTvInputService.getExtensionInterface(str2);
                        }
                    } catch (RemoteException e) {
                        Slog.e("TvInputManagerService", "error in getExtensionInterfacePermission or getExtensionInterface", e);
                    }
                }
                return null;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public List getTvContentRatingSystemList(int i) {
            List list;
            if (TvInputManagerService.this.mContext.checkCallingPermission("android.permission.READ_CONTENT_RATING_SYSTEMS") != 0) {
                throw new SecurityException("The caller does not have permission to read content rating systems");
            }
            int resolveCallingUserId = TvInputManagerService.this.resolveCallingUserId(Binder.getCallingPid(), Binder.getCallingUid(), i, "getTvContentRatingSystemList");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    list = TvInputManagerService.this.getOrCreateUserStateLocked(resolveCallingUserId).contentRatingSystemList;
                }
                return list;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void sendTvInputNotifyIntent(Intent intent, int i) {
            if (TvInputManagerService.this.mContext.checkCallingPermission("android.permission.NOTIFY_TV_INPUTS") != 0) {
                throw new SecurityException("The caller: " + getCallingPackageName() + " doesn't have permission: android.permission.NOTIFY_TV_INPUTS");
            }
            if (TextUtils.isEmpty(intent.getPackage())) {
                throw new IllegalArgumentException("Must specify package name to notify.");
            }
            String action = intent.getAction();
            action.hashCode();
            char c = 65535;
            switch (action.hashCode()) {
                case -160295064:
                    if (action.equals("android.media.tv.action.WATCH_NEXT_PROGRAM_BROWSABLE_DISABLED")) {
                        c = 0;
                        break;
                    }
                    break;
                case 1568780589:
                    if (action.equals("android.media.tv.action.PREVIEW_PROGRAM_BROWSABLE_DISABLED")) {
                        c = 1;
                        break;
                    }
                    break;
                case 2011523553:
                    if (action.equals("android.media.tv.action.PREVIEW_PROGRAM_ADDED_TO_WATCH_NEXT")) {
                        c = 2;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                    if (intent.getLongExtra("android.media.tv.extra.WATCH_NEXT_PROGRAM_ID", -1L) < 0) {
                        throw new IllegalArgumentException("Invalid watch next program ID.");
                    }
                    break;
                case 1:
                    if (intent.getLongExtra("android.media.tv.extra.PREVIEW_PROGRAM_ID", -1L) < 0) {
                        throw new IllegalArgumentException("Invalid preview program ID.");
                    }
                    break;
                case 2:
                    if (intent.getLongExtra("android.media.tv.extra.PREVIEW_PROGRAM_ID", -1L) < 0) {
                        throw new IllegalArgumentException("Invalid preview program ID.");
                    }
                    if (intent.getLongExtra("android.media.tv.extra.WATCH_NEXT_PROGRAM_ID", -1L) < 0) {
                        throw new IllegalArgumentException("Invalid watch next program ID.");
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Invalid TV input notifying action: " + intent.getAction());
            }
            int resolveCallingUserId = TvInputManagerService.this.resolveCallingUserId(Binder.getCallingPid(), Binder.getCallingUid(), i, "sendTvInputNotifyIntent");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                TvInputManagerService.this.getContext().sendBroadcastAsUser(intent, new UserHandle(resolveCallingUserId));
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void registerCallback(ITvInputManagerCallback iTvInputManagerCallback, int i) {
            int callingPid = Binder.getCallingPid();
            int callingUid = Binder.getCallingUid();
            int resolveCallingUserId = TvInputManagerService.this.resolveCallingUserId(callingPid, callingUid, i, "registerCallback");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    UserState orCreateUserStateLocked = TvInputManagerService.this.getOrCreateUserStateLocked(resolveCallingUserId);
                    if (!orCreateUserStateLocked.mCallbacks.register(iTvInputManagerCallback)) {
                        Slog.e("TvInputManagerService", "client process has already died");
                    } else {
                        orCreateUserStateLocked.callbackPidUidMap.put(iTvInputManagerCallback, Pair.create(Integer.valueOf(callingPid), Integer.valueOf(callingUid)));
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void unregisterCallback(ITvInputManagerCallback iTvInputManagerCallback, int i) {
            int resolveCallingUserId = TvInputManagerService.this.resolveCallingUserId(Binder.getCallingPid(), Binder.getCallingUid(), i, "unregisterCallback");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    UserState orCreateUserStateLocked = TvInputManagerService.this.getOrCreateUserStateLocked(resolveCallingUserId);
                    orCreateUserStateLocked.mCallbacks.unregister(iTvInputManagerCallback);
                    orCreateUserStateLocked.callbackPidUidMap.remove(iTvInputManagerCallback);
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public boolean isParentalControlsEnabled(int i) {
            boolean isParentalControlsEnabled;
            int resolveCallingUserId = TvInputManagerService.this.resolveCallingUserId(Binder.getCallingPid(), Binder.getCallingUid(), i, "isParentalControlsEnabled");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    isParentalControlsEnabled = TvInputManagerService.this.getOrCreateUserStateLocked(resolveCallingUserId).persistentDataStore.isParentalControlsEnabled();
                }
                return isParentalControlsEnabled;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void setParentalControlsEnabled(boolean z, int i) {
            ensureParentalControlsPermission();
            int resolveCallingUserId = TvInputManagerService.this.resolveCallingUserId(Binder.getCallingPid(), Binder.getCallingUid(), i, "setParentalControlsEnabled");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    TvInputManagerService.this.getOrCreateUserStateLocked(resolveCallingUserId).persistentDataStore.setParentalControlsEnabled(z);
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public boolean isRatingBlocked(String str, int i) {
            boolean isRatingBlocked;
            int resolveCallingUserId = TvInputManagerService.this.resolveCallingUserId(Binder.getCallingPid(), Binder.getCallingUid(), i, "isRatingBlocked");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    isRatingBlocked = TvInputManagerService.this.getOrCreateUserStateLocked(resolveCallingUserId).persistentDataStore.isRatingBlocked(TvContentRating.unflattenFromString(str));
                }
                return isRatingBlocked;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public List getBlockedRatings(int i) {
            ArrayList arrayList;
            int resolveCallingUserId = TvInputManagerService.this.resolveCallingUserId(Binder.getCallingPid(), Binder.getCallingUid(), i, "getBlockedRatings");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    UserState orCreateUserStateLocked = TvInputManagerService.this.getOrCreateUserStateLocked(resolveCallingUserId);
                    arrayList = new ArrayList();
                    for (TvContentRating tvContentRating : orCreateUserStateLocked.persistentDataStore.getBlockedRatings()) {
                        arrayList.add(tvContentRating.flattenToString());
                    }
                }
                return arrayList;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void addBlockedRating(String str, int i) {
            ensureParentalControlsPermission();
            int resolveCallingUserId = TvInputManagerService.this.resolveCallingUserId(Binder.getCallingPid(), Binder.getCallingUid(), i, "addBlockedRating");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    TvInputManagerService.this.getOrCreateUserStateLocked(resolveCallingUserId).persistentDataStore.addBlockedRating(TvContentRating.unflattenFromString(str));
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void removeBlockedRating(String str, int i) {
            ensureParentalControlsPermission();
            int resolveCallingUserId = TvInputManagerService.this.resolveCallingUserId(Binder.getCallingPid(), Binder.getCallingUid(), i, "removeBlockedRating");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    TvInputManagerService.this.getOrCreateUserStateLocked(resolveCallingUserId).persistentDataStore.removeBlockedRating(TvContentRating.unflattenFromString(str));
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void ensureParentalControlsPermission() {
            if (TvInputManagerService.this.mContext.checkCallingPermission("android.permission.MODIFY_PARENTAL_CONTROLS") != 0) {
                throw new SecurityException("The caller does not have parental controls permission");
            }
        }

        public void createSession(ITvInputClient iTvInputClient, String str, AttributionSource attributionSource, boolean z, int i, int i2) {
            int callingUid = Binder.getCallingUid();
            int callingPid = Binder.getCallingPid();
            int resolveCallingUserId = TvInputManagerService.this.resolveCallingUserId(callingPid, callingUid, i2, "createSession");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            String uuid = UUID.randomUUID().toString();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    if (i2 != TvInputManagerService.this.mCurrentUserId && !TvInputManagerService.this.mRunningProfiles.contains(Integer.valueOf(i2)) && !z) {
                        TvInputManagerService.this.sendSessionTokenToClientLocked(iTvInputClient, str, null, null, i);
                        return;
                    }
                    UserState orCreateUserStateLocked = TvInputManagerService.this.getOrCreateUserStateLocked(resolveCallingUserId);
                    TvInputState tvInputState = (TvInputState) orCreateUserStateLocked.inputMap.get(str);
                    if (tvInputState == null) {
                        Slog.w("TvInputManagerService", "Failed to find input state for inputId=" + str);
                        TvInputManagerService.this.sendSessionTokenToClientLocked(iTvInputClient, str, null, null, i);
                        return;
                    }
                    TvInputInfo tvInputInfo = tvInputState.info;
                    ServiceState serviceState = (ServiceState) orCreateUserStateLocked.serviceStateMap.get(tvInputInfo.getComponent());
                    if (serviceState == null) {
                        int i3 = PackageManager.getApplicationInfoAsUserCached(tvInputInfo.getComponent().getPackageName(), 0L, resolveCallingUserId).uid;
                        serviceState = new ServiceState(tvInputInfo.getComponent(), resolveCallingUserId);
                        orCreateUserStateLocked.serviceStateMap.put(tvInputInfo.getComponent(), serviceState);
                    }
                    ServiceState serviceState2 = serviceState;
                    if (serviceState2.reconnecting) {
                        TvInputManagerService.this.sendSessionTokenToClientLocked(iTvInputClient, str, null, null, i);
                        return;
                    }
                    Binder binder = new Binder();
                    SessionState sessionState = new SessionState(binder, tvInputInfo.getId(), tvInputInfo.getComponent(), z, iTvInputClient, i, callingUid, callingPid, resolveCallingUserId, uuid, attributionSource);
                    orCreateUserStateLocked.sessionStateMap.put(binder, sessionState);
                    TvInputManagerService.this.mSessionIdToSessionStateMap.put(uuid, sessionState);
                    serviceState2.sessionTokens.add(binder);
                    if (serviceState2.service == null) {
                        TvInputManagerService.this.updateServiceConnectionLocked(tvInputInfo.getComponent(), resolveCallingUserId);
                    } else if (!TvInputManagerService.this.createSessionInternalLocked(serviceState2.service, binder, resolveCallingUserId)) {
                        TvInputManagerService.this.removeSessionStateLocked(binder, resolveCallingUserId);
                    }
                    TvInputManagerService.this.logTuneStateChanged(1, sessionState, tvInputState);
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void releaseSession(IBinder iBinder, int i) {
            SessionState releaseSessionLocked;
            UserState userStateLocked;
            int callingUid = Binder.getCallingUid();
            int resolveCallingUserId = TvInputManagerService.this.resolveCallingUserId(Binder.getCallingPid(), callingUid, i, "releaseSession");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    releaseSessionLocked = TvInputManagerService.this.releaseSessionLocked(iBinder, callingUid, resolveCallingUserId);
                    userStateLocked = TvInputManagerService.this.getUserStateLocked(i);
                }
                if (releaseSessionLocked != null) {
                    TvInputManagerService.this.logTuneStateChanged(4, releaseSessionLocked, TvInputManagerService.getTvInputState(releaseSessionLocked, userStateLocked));
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void setMainSession(IBinder iBinder, int i) {
            if (TvInputManagerService.this.mContext.checkCallingPermission("android.permission.CHANGE_HDMI_CEC_ACTIVE_SOURCE") != 0) {
                throw new SecurityException("The caller does not have CHANGE_HDMI_CEC_ACTIVE_SOURCE permission");
            }
            int callingUid = Binder.getCallingUid();
            int resolveCallingUserId = TvInputManagerService.this.resolveCallingUserId(Binder.getCallingPid(), callingUid, i, "setMainSession");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    UserState orCreateUserStateLocked = TvInputManagerService.this.getOrCreateUserStateLocked(resolveCallingUserId);
                    if (orCreateUserStateLocked.mainSessionToken == iBinder) {
                        return;
                    }
                    IBinder iBinder2 = orCreateUserStateLocked.mainSessionToken;
                    orCreateUserStateLocked.mainSessionToken = iBinder;
                    if (iBinder != null) {
                        TvInputManagerService.this.setMainLocked(iBinder, true, callingUid, i);
                    }
                    if (iBinder2 != null) {
                        TvInputManagerService.this.setMainLocked(iBinder2, false, 1000, i);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:21:0x006f  */
        /* JADX WARN: Removed duplicated region for block: B:23:0x0074  */
        /* JADX WARN: Type inference failed for: r12v4 */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void setSurface(android.os.IBinder r10, android.view.Surface r11, int r12) {
            /*
                r9 = this;
                int r0 = android.os.Binder.getCallingUid()
                com.android.server.tv.TvInputManagerService r1 = com.android.server.tv.TvInputManagerService.this
                int r2 = android.os.Binder.getCallingPid()
                java.lang.String r3 = "setSurface"
                int r1 = com.android.server.tv.TvInputManagerService.m11953$$Nest$mresolveCallingUserId(r1, r2, r0, r12, r3)
                long r2 = android.os.Binder.clearCallingIdentity()
                r4 = 3
                r5 = 2
                r6 = 0
                com.android.server.tv.TvInputManagerService r7 = com.android.server.tv.TvInputManagerService.this     // Catch: java.lang.Throwable -> L8a
                java.lang.Object r7 = com.android.server.tv.TvInputManagerService.m11928$$Nest$fgetmLock(r7)     // Catch: java.lang.Throwable -> L8a
                monitor-enter(r7)     // Catch: java.lang.Throwable -> L8a
                com.android.server.tv.TvInputManagerService r8 = com.android.server.tv.TvInputManagerService.this     // Catch: java.lang.Throwable -> L60 java.lang.Throwable -> L63
                com.android.server.tv.TvInputManagerService$UserState r12 = com.android.server.tv.TvInputManagerService.m11946$$Nest$mgetUserStateLocked(r8, r12)     // Catch: java.lang.Throwable -> L60 java.lang.Throwable -> L63
                com.android.server.tv.TvInputManagerService r8 = com.android.server.tv.TvInputManagerService.this     // Catch: java.lang.Throwable -> L5e java.lang.Throwable -> L85
                com.android.server.tv.TvInputManagerService$SessionState r6 = com.android.server.tv.TvInputManagerService.m11944$$Nest$mgetSessionStateLocked(r8, r10, r0, r1)     // Catch: java.lang.Throwable -> L5e java.lang.Throwable -> L85
                android.os.IBinder r10 = com.android.server.tv.TvInputManagerService.SessionState.m11988$$Nest$fgethardwareSessionToken(r6)     // Catch: java.lang.Throwable -> L5e java.lang.Throwable -> L85
                if (r10 != 0) goto L3b
                com.android.server.tv.TvInputManagerService r10 = com.android.server.tv.TvInputManagerService.this     // Catch: java.lang.Throwable -> L5e java.lang.Throwable -> L85
                android.media.tv.ITvInputSession r10 = com.android.server.tv.TvInputManagerService.m11943$$Nest$mgetSessionLocked(r10, r6)     // Catch: java.lang.Throwable -> L5e java.lang.Throwable -> L85
                r10.setSurface(r11)     // Catch: java.lang.Throwable -> L5e java.lang.Throwable -> L85
                goto L4a
            L3b:
                com.android.server.tv.TvInputManagerService r10 = com.android.server.tv.TvInputManagerService.this     // Catch: java.lang.Throwable -> L5e java.lang.Throwable -> L85
                android.os.IBinder r0 = com.android.server.tv.TvInputManagerService.SessionState.m11988$$Nest$fgethardwareSessionToken(r6)     // Catch: java.lang.Throwable -> L5e java.lang.Throwable -> L85
                r8 = 1000(0x3e8, float:1.401E-42)
                android.media.tv.ITvInputSession r10 = com.android.server.tv.TvInputManagerService.m11942$$Nest$mgetSessionLocked(r10, r0, r8, r1)     // Catch: java.lang.Throwable -> L5e java.lang.Throwable -> L85
                r10.setSurface(r11)     // Catch: java.lang.Throwable -> L5e java.lang.Throwable -> L85
            L4a:
                if (r11 != 0) goto L4e
                r10 = 1
                goto L4f
            L4e:
                r10 = 0
            L4f:
                boolean r0 = com.android.server.tv.TvInputManagerService.SessionState.m11993$$Nest$fgetisVisible(r6)     // Catch: java.lang.Throwable -> L5e java.lang.Throwable -> L85
                if (r0 == r10) goto L6c
                com.android.server.tv.TvInputManagerService.SessionState.m12004$$Nest$fputisVisible(r6, r10)     // Catch: java.lang.Throwable -> L5e java.lang.Throwable -> L85
                com.android.server.tv.TvInputManagerService r10 = com.android.server.tv.TvInputManagerService.this     // Catch: java.lang.Throwable -> L5e java.lang.Throwable -> L85
                com.android.server.tv.TvInputManagerService.m11948$$Nest$mnotifyCurrentChannelInfosUpdatedLocked(r10, r12)     // Catch: java.lang.Throwable -> L5e java.lang.Throwable -> L85
                goto L6c
            L5e:
                r10 = move-exception
                goto L65
            L60:
                r10 = move-exception
                r12 = r6
                goto L86
            L63:
                r10 = move-exception
                r12 = r6
            L65:
                java.lang.String r0 = "TvInputManagerService"
                java.lang.String r1 = "error in setSurface"
                android.util.Slog.e(r0, r1, r10)     // Catch: java.lang.Throwable -> L85
            L6c:
                monitor-exit(r7)     // Catch: java.lang.Throwable -> L85
                if (r11 == 0) goto L72
                r11.release()
            L72:
                if (r6 == 0) goto L81
                if (r11 != 0) goto L77
                goto L78
            L77:
                r4 = r5
            L78:
                com.android.server.tv.TvInputManagerService r9 = com.android.server.tv.TvInputManagerService.this
                com.android.server.tv.TvInputManagerService$TvInputState r10 = com.android.server.tv.TvInputManagerService.m11965$$Nest$smgetTvInputState(r6, r12)
                com.android.server.tv.TvInputManagerService.m11947$$Nest$mlogTuneStateChanged(r9, r4, r6, r10)
            L81:
                android.os.Binder.restoreCallingIdentity(r2)
                return
            L85:
                r10 = move-exception
            L86:
                monitor-exit(r7)     // Catch: java.lang.Throwable -> L85
                throw r10     // Catch: java.lang.Throwable -> L88
            L88:
                r10 = move-exception
                goto L8c
            L8a:
                r10 = move-exception
                r12 = r6
            L8c:
                if (r11 == 0) goto L91
                r11.release()
            L91:
                if (r6 == 0) goto La0
                if (r11 != 0) goto L96
                goto L97
            L96:
                r4 = r5
            L97:
                com.android.server.tv.TvInputManagerService r9 = com.android.server.tv.TvInputManagerService.this
                com.android.server.tv.TvInputManagerService$TvInputState r11 = com.android.server.tv.TvInputManagerService.m11965$$Nest$smgetTvInputState(r6, r12)
                com.android.server.tv.TvInputManagerService.m11947$$Nest$mlogTuneStateChanged(r9, r4, r6, r11)
            La0:
                android.os.Binder.restoreCallingIdentity(r2)
                throw r10
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.tv.TvInputManagerService.BinderService.setSurface(android.os.IBinder, android.view.Surface, int):void");
        }

        public void dispatchSurfaceChanged(IBinder iBinder, int i, int i2, int i3, int i4) {
            int callingUid = Binder.getCallingUid();
            int resolveCallingUserId = TvInputManagerService.this.resolveCallingUserId(Binder.getCallingPid(), callingUid, i4, "dispatchSurfaceChanged");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    try {
                        SessionState sessionStateLocked = TvInputManagerService.this.getSessionStateLocked(iBinder, callingUid, resolveCallingUserId);
                        TvInputManagerService.this.getSessionLocked(sessionStateLocked).dispatchSurfaceChanged(i, i2, i3);
                        if (sessionStateLocked.hardwareSessionToken != null) {
                            TvInputManagerService.this.getSessionLocked(sessionStateLocked.hardwareSessionToken, 1000, resolveCallingUserId).dispatchSurfaceChanged(i, i2, i3);
                        }
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slog.e("TvInputManagerService", "error in dispatchSurfaceChanged", e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void setVolume(IBinder iBinder, float f, int i) {
            int callingUid = Binder.getCallingUid();
            int resolveCallingUserId = TvInputManagerService.this.resolveCallingUserId(Binder.getCallingPid(), callingUid, i, "setVolume");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    try {
                        SessionState sessionStateLocked = TvInputManagerService.this.getSessionStateLocked(iBinder, callingUid, resolveCallingUserId);
                        TvInputManagerService.this.getSessionLocked(sessionStateLocked).setVolume(f);
                        if (sessionStateLocked.hardwareSessionToken != null) {
                            ITvInputSession sessionLocked = TvInputManagerService.this.getSessionLocked(sessionStateLocked.hardwareSessionToken, 1000, resolveCallingUserId);
                            float f2 = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
                            if (f > DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
                                f2 = 1.0f;
                            }
                            sessionLocked.setVolume(f2);
                        }
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slog.e("TvInputManagerService", "error in setVolume", e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void tune(IBinder iBinder, Uri uri, Bundle bundle, int i) {
            UserState orCreateUserStateLocked;
            SessionState sessionStateLocked;
            int callingUid = Binder.getCallingUid();
            int resolveCallingUserId = TvInputManagerService.this.resolveCallingUserId(Binder.getCallingPid(), callingUid, i, "tune");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    try {
                        TvInputManagerService.this.getSessionLocked(iBinder, callingUid, resolveCallingUserId).tune(uri, bundle);
                        orCreateUserStateLocked = TvInputManagerService.this.getOrCreateUserStateLocked(resolveCallingUserId);
                        sessionStateLocked = TvInputManagerService.this.getSessionStateLocked(iBinder, callingUid, orCreateUserStateLocked);
                        if (!sessionStateLocked.isCurrent || !Objects.equals(sessionStateLocked.currentChannel, uri)) {
                            sessionStateLocked.isCurrent = true;
                            sessionStateLocked.currentChannel = uri;
                            TvInputManagerService.this.notifyCurrentChannelInfosUpdatedLocked(orCreateUserStateLocked);
                        }
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slog.e("TvInputManagerService", "error in tune", e);
                    }
                    if (TvContract.isChannelUriForPassthroughInput(uri)) {
                        return;
                    }
                    if (sessionStateLocked.isRecordingSession) {
                        return;
                    }
                    TvInputManagerService.this.logTuneStateChanged(5, sessionStateLocked, TvInputManagerService.getTvInputState(sessionStateLocked, orCreateUserStateLocked));
                    SomeArgs obtain = SomeArgs.obtain();
                    obtain.arg1 = sessionStateLocked.componentName.getPackageName();
                    obtain.arg2 = Long.valueOf(System.currentTimeMillis());
                    obtain.arg3 = Long.valueOf(ContentUris.parseId(uri));
                    obtain.arg4 = bundle;
                    obtain.arg5 = iBinder;
                    TvInputManagerService.this.mWatchLogHandler.obtainMessage(1, obtain).sendToTarget();
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void unblockContent(IBinder iBinder, String str, int i) {
            ensureParentalControlsPermission();
            int callingUid = Binder.getCallingUid();
            int resolveCallingUserId = TvInputManagerService.this.resolveCallingUserId(Binder.getCallingPid(), callingUid, i, "unblockContent");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    try {
                        TvInputManagerService.this.getSessionLocked(iBinder, callingUid, resolveCallingUserId).unblockContent(str);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slog.e("TvInputManagerService", "error in unblockContent", e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void setCaptionEnabled(IBinder iBinder, boolean z, int i) {
            int callingUid = Binder.getCallingUid();
            int resolveCallingUserId = TvInputManagerService.this.resolveCallingUserId(Binder.getCallingPid(), callingUid, i, "setCaptionEnabled");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    try {
                        TvInputManagerService.this.getSessionLocked(iBinder, callingUid, resolveCallingUserId).setCaptionEnabled(z);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slog.e("TvInputManagerService", "error in setCaptionEnabled", e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void selectAudioPresentation(IBinder iBinder, int i, int i2, int i3) {
            int callingUid = Binder.getCallingUid();
            int resolveCallingUserId = TvInputManagerService.this.resolveCallingUserId(Binder.getCallingPid(), callingUid, i3, "selectAudioPresentation");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    try {
                        TvInputManagerService.this.getSessionLocked(iBinder, callingUid, resolveCallingUserId).selectAudioPresentation(i, i2);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slog.e("TvInputManagerService", "error in selectAudioPresentation", e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void selectTrack(IBinder iBinder, int i, String str, int i2) {
            int callingUid = Binder.getCallingUid();
            int resolveCallingUserId = TvInputManagerService.this.resolveCallingUserId(Binder.getCallingPid(), callingUid, i2, "selectTrack");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    try {
                        TvInputManagerService.this.getSessionLocked(iBinder, callingUid, resolveCallingUserId).selectTrack(i, str);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slog.e("TvInputManagerService", "error in selectTrack", e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void setInteractiveAppNotificationEnabled(IBinder iBinder, boolean z, int i) {
            int callingUid = Binder.getCallingUid();
            int resolveCallingUserId = TvInputManagerService.this.resolveCallingUserId(Binder.getCallingPid(), callingUid, i, "setInteractiveAppNotificationEnabled");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    try {
                        TvInputManagerService.this.getSessionLocked(iBinder, callingUid, resolveCallingUserId).setInteractiveAppNotificationEnabled(z);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slog.e("TvInputManagerService", "error in setInteractiveAppNotificationEnabled", e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void sendAppPrivateCommand(IBinder iBinder, String str, Bundle bundle, int i) {
            int callingUid = Binder.getCallingUid();
            int resolveCallingUserId = TvInputManagerService.this.resolveCallingUserId(Binder.getCallingPid(), callingUid, i, "sendAppPrivateCommand");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    try {
                        TvInputManagerService.this.getSessionLocked(iBinder, callingUid, resolveCallingUserId).appPrivateCommand(str, bundle);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slog.e("TvInputManagerService", "error in appPrivateCommand", e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void createOverlayView(IBinder iBinder, IBinder iBinder2, Rect rect, int i) {
            int callingUid = Binder.getCallingUid();
            int resolveCallingUserId = TvInputManagerService.this.resolveCallingUserId(Binder.getCallingPid(), callingUid, i, "createOverlayView");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    try {
                        TvInputManagerService.this.getSessionLocked(iBinder, callingUid, resolveCallingUserId).createOverlayView(iBinder2, rect);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slog.e("TvInputManagerService", "error in createOverlayView", e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void relayoutOverlayView(IBinder iBinder, Rect rect, int i) {
            int callingUid = Binder.getCallingUid();
            int resolveCallingUserId = TvInputManagerService.this.resolveCallingUserId(Binder.getCallingPid(), callingUid, i, "relayoutOverlayView");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    try {
                        TvInputManagerService.this.getSessionLocked(iBinder, callingUid, resolveCallingUserId).relayoutOverlayView(rect);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slog.e("TvInputManagerService", "error in relayoutOverlayView", e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void removeOverlayView(IBinder iBinder, int i) {
            int callingUid = Binder.getCallingUid();
            int resolveCallingUserId = TvInputManagerService.this.resolveCallingUserId(Binder.getCallingPid(), callingUid, i, "removeOverlayView");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    try {
                        TvInputManagerService.this.getSessionLocked(iBinder, callingUid, resolveCallingUserId).removeOverlayView();
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slog.e("TvInputManagerService", "error in removeOverlayView", e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void timeShiftPlay(IBinder iBinder, Uri uri, int i) {
            int callingUid = Binder.getCallingUid();
            int resolveCallingUserId = TvInputManagerService.this.resolveCallingUserId(Binder.getCallingPid(), callingUid, i, "timeShiftPlay");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    try {
                        TvInputManagerService.this.getSessionLocked(iBinder, callingUid, resolveCallingUserId).timeShiftPlay(uri);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slog.e("TvInputManagerService", "error in timeShiftPlay", e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void timeShiftPause(IBinder iBinder, int i) {
            int callingUid = Binder.getCallingUid();
            int resolveCallingUserId = TvInputManagerService.this.resolveCallingUserId(Binder.getCallingPid(), callingUid, i, "timeShiftPause");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    try {
                        TvInputManagerService.this.getSessionLocked(iBinder, callingUid, resolveCallingUserId).timeShiftPause();
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slog.e("TvInputManagerService", "error in timeShiftPause", e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void timeShiftResume(IBinder iBinder, int i) {
            int callingUid = Binder.getCallingUid();
            int resolveCallingUserId = TvInputManagerService.this.resolveCallingUserId(Binder.getCallingPid(), callingUid, i, "timeShiftResume");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    try {
                        TvInputManagerService.this.getSessionLocked(iBinder, callingUid, resolveCallingUserId).timeShiftResume();
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slog.e("TvInputManagerService", "error in timeShiftResume", e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void timeShiftSeekTo(IBinder iBinder, long j, int i) {
            int callingUid = Binder.getCallingUid();
            int resolveCallingUserId = TvInputManagerService.this.resolveCallingUserId(Binder.getCallingPid(), callingUid, i, "timeShiftSeekTo");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    try {
                        TvInputManagerService.this.getSessionLocked(iBinder, callingUid, resolveCallingUserId).timeShiftSeekTo(j);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slog.e("TvInputManagerService", "error in timeShiftSeekTo", e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void timeShiftSetPlaybackParams(IBinder iBinder, PlaybackParams playbackParams, int i) {
            int callingUid = Binder.getCallingUid();
            int resolveCallingUserId = TvInputManagerService.this.resolveCallingUserId(Binder.getCallingPid(), callingUid, i, "timeShiftSetPlaybackParams");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    try {
                        TvInputManagerService.this.getSessionLocked(iBinder, callingUid, resolveCallingUserId).timeShiftSetPlaybackParams(playbackParams);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slog.e("TvInputManagerService", "error in timeShiftSetPlaybackParams", e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void timeShiftSetMode(IBinder iBinder, int i, int i2) {
            int callingUid = Binder.getCallingUid();
            int resolveCallingUserId = TvInputManagerService.this.resolveCallingUserId(Binder.getCallingPid(), callingUid, i2, "timeShiftSetMode");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    try {
                        TvInputManagerService.this.getSessionLocked(iBinder, callingUid, resolveCallingUserId).timeShiftSetMode(i);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slog.e("TvInputManagerService", "error in timeShiftSetMode", e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void timeShiftEnablePositionTracking(IBinder iBinder, boolean z, int i) {
            int callingUid = Binder.getCallingUid();
            int resolveCallingUserId = TvInputManagerService.this.resolveCallingUserId(Binder.getCallingPid(), callingUid, i, "timeShiftEnablePositionTracking");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    try {
                        TvInputManagerService.this.getSessionLocked(iBinder, callingUid, resolveCallingUserId).timeShiftEnablePositionTracking(z);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slog.e("TvInputManagerService", "error in timeShiftEnablePositionTracking", e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void notifyTvMessage(IBinder iBinder, int i, Bundle bundle, int i2) {
            int callingUid = Binder.getCallingUid();
            int resolveCallingUserId = TvInputManagerService.this.resolveCallingUserId(Binder.getCallingPid(), callingUid, i2, "notifyTvmessage");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    try {
                        TvInputManagerService.this.getSessionLocked(iBinder, callingUid, resolveCallingUserId).notifyTvMessage(i, bundle);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slog.e("TvInputManagerService", "error in notifyTvMessage", e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void setTvMessageEnabled(IBinder iBinder, int i, boolean z, int i2) {
            int callingUid = Binder.getCallingUid();
            int resolveCallingUserId = TvInputManagerService.this.resolveCallingUserId(Binder.getCallingPid(), callingUid, i2, "setTvMessageEnabled");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    try {
                        TvInputManagerService.this.mTvInputHardwareManager.setTvMessageEnabled(TvInputManagerService.this.getSessionStateLocked(iBinder, callingUid, i2).inputId, i, z);
                        TvInputManagerService.this.getSessionLocked(iBinder, callingUid, resolveCallingUserId).setTvMessageEnabled(i, z);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slog.e("TvInputManagerService", "error in setTvMessageEnabled", e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void startRecording(IBinder iBinder, Uri uri, Bundle bundle, int i) {
            int callingUid = Binder.getCallingUid();
            int resolveCallingUserId = TvInputManagerService.this.resolveCallingUserId(Binder.getCallingPid(), callingUid, i, "startRecording");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    try {
                        TvInputManagerService.this.getSessionLocked(iBinder, callingUid, resolveCallingUserId).startRecording(uri, bundle);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slog.e("TvInputManagerService", "error in startRecording", e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void stopRecording(IBinder iBinder, int i) {
            int callingUid = Binder.getCallingUid();
            int resolveCallingUserId = TvInputManagerService.this.resolveCallingUserId(Binder.getCallingPid(), callingUid, i, "stopRecording");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    try {
                        TvInputManagerService.this.getSessionLocked(iBinder, callingUid, resolveCallingUserId).stopRecording();
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slog.e("TvInputManagerService", "error in stopRecording", e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void pauseRecording(IBinder iBinder, Bundle bundle, int i) {
            int callingUid = Binder.getCallingUid();
            int resolveCallingUserId = TvInputManagerService.this.resolveCallingUserId(Binder.getCallingPid(), callingUid, i, "pauseRecording");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    try {
                        TvInputManagerService.this.getSessionLocked(iBinder, callingUid, resolveCallingUserId).pauseRecording(bundle);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slog.e("TvInputManagerService", "error in pauseRecording", e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void resumeRecording(IBinder iBinder, Bundle bundle, int i) {
            int callingUid = Binder.getCallingUid();
            int resolveCallingUserId = TvInputManagerService.this.resolveCallingUserId(Binder.getCallingPid(), callingUid, i, "resumeRecording");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    try {
                        TvInputManagerService.this.getSessionLocked(iBinder, callingUid, resolveCallingUserId).resumeRecording(bundle);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slog.e("TvInputManagerService", "error in resumeRecording", e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public List getHardwareList() {
            if (TvInputManagerService.this.mContext.checkCallingPermission("android.permission.TV_INPUT_HARDWARE") != 0) {
                return null;
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return TvInputManagerService.this.mTvInputHardwareManager.getHardwareList();
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public ITvInputHardware acquireTvInputHardware(int i, ITvInputHardwareCallback iTvInputHardwareCallback, TvInputInfo tvInputInfo, int i2, String str, int i3) {
            if (TvInputManagerService.this.mContext.checkCallingPermission("android.permission.TV_INPUT_HARDWARE") != 0) {
                return null;
            }
            int callingUid = Binder.getCallingUid();
            int resolveCallingUserId = TvInputManagerService.this.resolveCallingUserId(Binder.getCallingPid(), callingUid, i2, "acquireTvInputHardware");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return TvInputManagerService.this.mTvInputHardwareManager.acquireHardware(i, iTvInputHardwareCallback, tvInputInfo, callingUid, resolveCallingUserId, str, i3);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void releaseTvInputHardware(int i, ITvInputHardware iTvInputHardware, int i2) {
            if (TvInputManagerService.this.mContext.checkCallingPermission("android.permission.TV_INPUT_HARDWARE") != 0) {
                return;
            }
            int callingUid = Binder.getCallingUid();
            int resolveCallingUserId = TvInputManagerService.this.resolveCallingUserId(Binder.getCallingPid(), callingUid, i2, "releaseTvInputHardware");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                TvInputManagerService.this.mTvInputHardwareManager.releaseHardware(i, iTvInputHardware, callingUid, resolveCallingUserId);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public List getDvbDeviceList() {
            List unmodifiableList;
            if (TvInputManagerService.this.mContext.checkCallingPermission("android.permission.DVB_DEVICE") != 0) {
                throw new SecurityException("Requires DVB_DEVICE permission");
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                ArrayList arrayList = new ArrayList();
                boolean z = false;
                for (String str : new File("/dev").list()) {
                    Matcher matcher = TvInputManagerService.sFrontEndDevicePattern.matcher(str);
                    if (matcher.find()) {
                        arrayList.add(new DvbDeviceInfo(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2))));
                    }
                    if (TextUtils.equals("dvb", str)) {
                        z = true;
                    }
                }
                if (!z) {
                    return Collections.unmodifiableList(arrayList);
                }
                File file = new File("/dev/dvb");
                ArrayList arrayList2 = new ArrayList();
                for (String str2 : file.list()) {
                    Matcher matcher2 = TvInputManagerService.sAdapterDirPattern.matcher(str2);
                    if (matcher2.find()) {
                        int parseInt = Integer.parseInt(matcher2.group(1));
                        String[] list = new File("/dev/dvb/" + str2).list();
                        int length = list.length;
                        for (int i = 0; i < length; i++) {
                            Matcher matcher3 = TvInputManagerService.sFrontEndInAdapterDirPattern.matcher(list[i]);
                            if (matcher3.find()) {
                                arrayList2.add(new DvbDeviceInfo(parseInt, Integer.parseInt(matcher3.group(1))));
                            }
                        }
                    }
                }
                if (arrayList2.isEmpty()) {
                    unmodifiableList = Collections.unmodifiableList(arrayList);
                } else {
                    unmodifiableList = Collections.unmodifiableList(arrayList2);
                }
                return unmodifiableList;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public ParcelFileDescriptor openDvbDevice(DvbDeviceInfo dvbDeviceInfo, int i) {
            String format;
            if (TvInputManagerService.this.mContext.checkCallingPermission("android.permission.DVB_DEVICE") != 0) {
                throw new SecurityException("Requires DVB_DEVICE permission");
            }
            boolean z = false;
            for (String str : new File("/dev").list()) {
                if (TextUtils.equals("dvb", str)) {
                    for (String str2 : new File("/dev/dvb").list()) {
                        if (TvInputManagerService.sAdapterDirPattern.matcher(str2).find()) {
                            String[] list = new File("/dev/dvb/" + str2).list();
                            int length = list.length;
                            int i2 = 0;
                            while (true) {
                                if (i2 >= length) {
                                    break;
                                }
                                if (TvInputManagerService.sFrontEndInAdapterDirPattern.matcher(list[i2]).find()) {
                                    z = true;
                                    break;
                                }
                                i2++;
                            }
                        }
                        if (z) {
                            break;
                        }
                    }
                }
                if (z) {
                    break;
                }
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                if (i == 0) {
                    format = String.format(z ? "/dev/dvb/adapter%d/demux%d" : "/dev/dvb%d.demux%d", Integer.valueOf(dvbDeviceInfo.getAdapterId()), Integer.valueOf(dvbDeviceInfo.getDeviceId()));
                } else if (i == 1) {
                    format = String.format(z ? "/dev/dvb/adapter%d/dvr%d" : "/dev/dvb%d.dvr%d", Integer.valueOf(dvbDeviceInfo.getAdapterId()), Integer.valueOf(dvbDeviceInfo.getDeviceId()));
                } else if (i == 2) {
                    format = String.format(z ? "/dev/dvb/adapter%d/frontend%d" : "/dev/dvb%d.frontend%d", Integer.valueOf(dvbDeviceInfo.getAdapterId()), Integer.valueOf(dvbDeviceInfo.getDeviceId()));
                } else {
                    throw new IllegalArgumentException("Invalid DVB device: " + i);
                }
                try {
                    ParcelFileDescriptor open = ParcelFileDescriptor.open(new File(format), 2 == i ? 805306368 : 268435456);
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return open;
                } catch (FileNotFoundException unused) {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return null;
                }
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }

        public List getAvailableTvStreamConfigList(String str, int i) {
            ensureCaptureTvInputPermission();
            int callingUid = Binder.getCallingUid();
            int resolveCallingUserId = TvInputManagerService.this.resolveCallingUserId(Binder.getCallingPid(), callingUid, i, "getAvailableTvStreamConfigList");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return TvInputManagerService.this.mTvInputHardwareManager.getAvailableTvStreamConfigList(str, callingUid, resolveCallingUserId);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public boolean captureFrame(String str, Surface surface, TvStreamConfig tvStreamConfig, int i) {
            String str2;
            ensureCaptureTvInputPermission();
            int callingUid = Binder.getCallingUid();
            int resolveCallingUserId = TvInputManagerService.this.resolveCallingUserId(Binder.getCallingPid(), callingUid, i, "captureFrame");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    UserState orCreateUserStateLocked = TvInputManagerService.this.getOrCreateUserStateLocked(resolveCallingUserId);
                    if (orCreateUserStateLocked.inputMap.get(str) == null) {
                        Slog.e("TvInputManagerService", "input not found for " + str);
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        return false;
                    }
                    Iterator it = orCreateUserStateLocked.sessionStateMap.values().iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            str2 = null;
                            break;
                        }
                        SessionState sessionState = (SessionState) it.next();
                        if (sessionState.inputId.equals(str) && sessionState.hardwareSessionToken != null) {
                            str2 = ((SessionState) orCreateUserStateLocked.sessionStateMap.get(sessionState.hardwareSessionToken)).inputId;
                            break;
                        }
                    }
                    return TvInputManagerService.this.mTvInputHardwareManager.captureFrame(str2 != null ? str2 : str, surface, tvStreamConfig, callingUid, resolveCallingUserId);
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public boolean isSingleSessionActive(int i) {
            ensureCaptureTvInputPermission();
            int resolveCallingUserId = TvInputManagerService.this.resolveCallingUserId(Binder.getCallingPid(), Binder.getCallingUid(), i, "isSingleSessionActive");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    UserState orCreateUserStateLocked = TvInputManagerService.this.getOrCreateUserStateLocked(resolveCallingUserId);
                    boolean z = true;
                    if (orCreateUserStateLocked.sessionStateMap.size() == 1) {
                        return true;
                    }
                    if (orCreateUserStateLocked.sessionStateMap.size() != 2) {
                        return false;
                    }
                    SessionState[] sessionStateArr = (SessionState[]) orCreateUserStateLocked.sessionStateMap.values().toArray(new SessionState[2]);
                    if (sessionStateArr[0].hardwareSessionToken == null && sessionStateArr[1].hardwareSessionToken == null) {
                        z = false;
                    }
                    return z;
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void ensureCaptureTvInputPermission() {
            if (TvInputManagerService.this.mContext.checkCallingPermission("android.permission.CAPTURE_TV_INPUT") != 0) {
                throw new SecurityException("Requires CAPTURE_TV_INPUT permission");
            }
        }

        public void requestChannelBrowsable(Uri uri, int i) {
            String callingPackageName = getCallingPackageName();
            int resolveCallingUserId = TvInputManagerService.this.resolveCallingUserId(Binder.getCallingPid(), Binder.getCallingUid(), i, "requestChannelBrowsable");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Intent intent = new Intent("android.media.tv.action.CHANNEL_BROWSABLE_REQUESTED");
                List<ResolveInfo> queryBroadcastReceivers = TvInputManagerService.this.getContext().getPackageManager().queryBroadcastReceivers(intent, 0);
                if (queryBroadcastReceivers != null) {
                    Iterator<ResolveInfo> it = queryBroadcastReceivers.iterator();
                    while (it.hasNext()) {
                        String str = it.next().activityInfo.packageName;
                        intent.putExtra("android.media.tv.extra.CHANNEL_ID", ContentUris.parseId(uri));
                        intent.putExtra("android.media.tv.extra.PACKAGE_NAME", callingPackageName);
                        intent.setPackage(str);
                        TvInputManagerService.this.getContext().sendBroadcastAsUser(intent, new UserHandle(resolveCallingUserId));
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void requestBroadcastInfo(IBinder iBinder, BroadcastInfoRequest broadcastInfoRequest, int i) {
            int callingUid = Binder.getCallingUid();
            int resolveCallingUserId = TvInputManagerService.this.resolveCallingUserId(Binder.getCallingPid(), callingUid, i, "requestBroadcastInfo");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    try {
                        TvInputManagerService.this.getSessionLocked(TvInputManagerService.this.getSessionStateLocked(iBinder, callingUid, resolveCallingUserId)).requestBroadcastInfo(broadcastInfoRequest);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slog.e("TvInputManagerService", "error in requestBroadcastInfo", e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void removeBroadcastInfo(IBinder iBinder, int i, int i2) {
            int callingUid = Binder.getCallingUid();
            int resolveCallingUserId = TvInputManagerService.this.resolveCallingUserId(Binder.getCallingPid(), callingUid, i2, "removeBroadcastInfo");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    try {
                        TvInputManagerService.this.getSessionLocked(TvInputManagerService.this.getSessionStateLocked(iBinder, callingUid, resolveCallingUserId)).removeBroadcastInfo(i);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slog.e("TvInputManagerService", "error in removeBroadcastInfo", e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void requestAd(IBinder iBinder, AdRequest adRequest, int i) {
            int callingUid = Binder.getCallingUid();
            int resolveCallingUserId = TvInputManagerService.this.resolveCallingUserId(Binder.getCallingPid(), callingUid, i, "requestAd");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    try {
                        TvInputManagerService.this.getSessionLocked(TvInputManagerService.this.getSessionStateLocked(iBinder, callingUid, resolveCallingUserId)).requestAd(adRequest);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slog.e("TvInputManagerService", "error in requestAd", e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void notifyAdBufferReady(IBinder iBinder, AdBuffer adBuffer, int i) {
            SharedMemory sharedMemory;
            int callingUid = Binder.getCallingUid();
            int resolveCallingUserId = TvInputManagerService.this.resolveCallingUserId(Binder.getCallingPid(), callingUid, i, "notifyAdBuffer");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                try {
                    synchronized (TvInputManagerService.this.mLock) {
                        try {
                            TvInputManagerService.this.getSessionLocked(TvInputManagerService.this.getSessionStateLocked(iBinder, callingUid, resolveCallingUserId)).notifyAdBufferReady(adBuffer);
                        } catch (RemoteException | SessionNotFoundException e) {
                            Slog.e("TvInputManagerService", "error in notifyAdBuffer", e);
                            if (adBuffer != null) {
                                sharedMemory = adBuffer.getSharedMemory();
                            }
                        }
                        if (adBuffer != null) {
                            sharedMemory = adBuffer.getSharedMemory();
                            sharedMemory.close();
                        }
                    }
                } catch (Throwable th) {
                    if (adBuffer != null) {
                        adBuffer.getSharedMemory().close();
                    }
                    throw th;
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public int getClientPid(String str) {
            int i;
            ensureTunerResourceAccessPermission();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    try {
                        i = getClientPidLocked(str);
                    } catch (ClientPidNotFoundException e) {
                        Slog.e("TvInputManagerService", "error in getClientPid", e);
                        i = -1;
                    }
                }
                return i;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public int getClientPriority(int i, String str) {
            int i2;
            ensureTunerResourceAccessPermission();
            int callingPid = Binder.getCallingPid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            if (str != null) {
                try {
                    synchronized (TvInputManagerService.this.mLock) {
                        try {
                            i2 = getClientPidLocked(str);
                        } catch (ClientPidNotFoundException e) {
                            Slog.e("TvInputManagerService", "error in getClientPriority", e);
                            i2 = -1;
                        }
                    }
                    callingPid = i2;
                } catch (Throwable th) {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
            }
            int clientPriority = ((TunerResourceManager) TvInputManagerService.this.mContext.getSystemService("tv_tuner_resource_mgr")).getClientPriority(i, callingPid);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return clientPriority;
        }

        public List getCurrentTunedInfos(int i) {
            List currentTunedInfosInternalLocked;
            if (TvInputManagerService.this.mContext.checkCallingPermission("android.permission.ACCESS_TUNED_INFO") != 0) {
                throw new SecurityException("The caller does not have access tuned info permission");
            }
            int callingPid = Binder.getCallingPid();
            int callingUid = Binder.getCallingUid();
            int resolveCallingUserId = TvInputManagerService.this.resolveCallingUserId(callingPid, callingUid, i, "getTvCurrentChannelInfos");
            synchronized (TvInputManagerService.this.mLock) {
                currentTunedInfosInternalLocked = TvInputManagerService.this.getCurrentTunedInfosInternalLocked(TvInputManagerService.this.getOrCreateUserStateLocked(resolveCallingUserId), callingPid, callingUid);
            }
            return currentTunedInfosInternalLocked;
        }

        public void addHardwareDevice(int i) {
            TvInputManagerService.this.mTvInputHardwareManager.onDeviceAvailable(new TvInputHardwareInfo.Builder().deviceId(i).type(9).audioType(0).audioAddress("0").hdmiPortId(0).build(), new TvStreamConfig[]{new TvStreamConfig.Builder().streamId(19001).generation(1).maxHeight(600).maxWidth(800).type(1).build()});
        }

        public void removeHardwareDevice(int i) {
            TvInputManagerService.this.mTvInputHardwareManager.onDeviceUnavailable(i);
        }

        public final int getClientPidLocked(String str) {
            if (TvInputManagerService.this.mSessionIdToSessionStateMap.get(str) == null) {
                throw new ClientPidNotFoundException("Client Pid not found with sessionId " + str);
            }
            return ((SessionState) TvInputManagerService.this.mSessionIdToSessionStateMap.get(str)).callingPid;
        }

        public final void ensureTunerResourceAccessPermission() {
            if (TvInputManagerService.this.mContext.checkCallingPermission("android.permission.TUNER_RESOURCE_ACCESS") != 0) {
                throw new SecurityException("Requires TUNER_RESOURCE_ACCESS permission");
            }
        }

        public final void ensureTisExtensionInterfacePermission() {
            if (TvInputManagerService.this.mContext.checkCallingPermission("android.permission.TIS_EXTENSION_INTERFACE") != 0) {
                throw new SecurityException("Requires TIS_EXTENSION_INTERFACE permission");
            }
        }

        @NeverCompile
        public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "  ");
            if (DumpUtils.checkDumpPermission(TvInputManagerService.this.mContext, "TvInputManagerService", indentingPrintWriter)) {
                synchronized (TvInputManagerService.this.mLock) {
                    indentingPrintWriter.println("User Ids (Current user: " + TvInputManagerService.this.mCurrentUserId + "):");
                    indentingPrintWriter.increaseIndent();
                    for (int i = 0; i < TvInputManagerService.this.mUserStates.size(); i++) {
                        indentingPrintWriter.println(Integer.valueOf(TvInputManagerService.this.mUserStates.keyAt(i)));
                    }
                    indentingPrintWriter.decreaseIndent();
                    for (int i2 = 0; i2 < TvInputManagerService.this.mUserStates.size(); i2++) {
                        int keyAt = TvInputManagerService.this.mUserStates.keyAt(i2);
                        UserState orCreateUserStateLocked = TvInputManagerService.this.getOrCreateUserStateLocked(keyAt);
                        indentingPrintWriter.println("UserState (" + keyAt + "):");
                        indentingPrintWriter.increaseIndent();
                        indentingPrintWriter.println("inputMap: inputId -> TvInputState");
                        indentingPrintWriter.increaseIndent();
                        for (Map.Entry entry : orCreateUserStateLocked.inputMap.entrySet()) {
                            indentingPrintWriter.println(((String) entry.getKey()) + ": " + entry.getValue());
                        }
                        indentingPrintWriter.decreaseIndent();
                        indentingPrintWriter.println("packageSet:");
                        indentingPrintWriter.increaseIndent();
                        Iterator it = orCreateUserStateLocked.packageSet.iterator();
                        while (it.hasNext()) {
                            indentingPrintWriter.println((String) it.next());
                        }
                        indentingPrintWriter.decreaseIndent();
                        indentingPrintWriter.println("clientStateMap: ITvInputClient -> ClientState");
                        indentingPrintWriter.increaseIndent();
                        for (Map.Entry entry2 : orCreateUserStateLocked.clientStateMap.entrySet()) {
                            ClientState clientState = (ClientState) entry2.getValue();
                            indentingPrintWriter.println(entry2.getKey() + ": " + clientState);
                            indentingPrintWriter.increaseIndent();
                            indentingPrintWriter.println("sessionTokens:");
                            indentingPrintWriter.increaseIndent();
                            Iterator it2 = clientState.sessionTokens.iterator();
                            while (it2.hasNext()) {
                                indentingPrintWriter.println("" + ((IBinder) it2.next()));
                            }
                            indentingPrintWriter.decreaseIndent();
                            indentingPrintWriter.println("clientTokens: " + clientState.clientToken);
                            indentingPrintWriter.println("userId: " + clientState.userId);
                            indentingPrintWriter.decreaseIndent();
                        }
                        indentingPrintWriter.decreaseIndent();
                        indentingPrintWriter.println("serviceStateMap: ComponentName -> ServiceState");
                        indentingPrintWriter.increaseIndent();
                        for (Map.Entry entry3 : orCreateUserStateLocked.serviceStateMap.entrySet()) {
                            ServiceState serviceState = (ServiceState) entry3.getValue();
                            indentingPrintWriter.println(entry3.getKey() + ": " + serviceState);
                            indentingPrintWriter.increaseIndent();
                            indentingPrintWriter.println("sessionTokens:");
                            indentingPrintWriter.increaseIndent();
                            Iterator it3 = serviceState.sessionTokens.iterator();
                            while (it3.hasNext()) {
                                indentingPrintWriter.println("" + ((IBinder) it3.next()));
                            }
                            indentingPrintWriter.decreaseIndent();
                            indentingPrintWriter.println("service: " + serviceState.service);
                            indentingPrintWriter.println("callback: " + serviceState.callback);
                            indentingPrintWriter.println("bound: " + serviceState.bound);
                            indentingPrintWriter.println("reconnecting: " + serviceState.reconnecting);
                            indentingPrintWriter.decreaseIndent();
                        }
                        indentingPrintWriter.decreaseIndent();
                        indentingPrintWriter.println("sessionStateMap: ITvInputSession -> SessionState");
                        indentingPrintWriter.increaseIndent();
                        for (Map.Entry entry4 : orCreateUserStateLocked.sessionStateMap.entrySet()) {
                            SessionState sessionState = (SessionState) entry4.getValue();
                            indentingPrintWriter.println(entry4.getKey() + ": " + sessionState);
                            indentingPrintWriter.increaseIndent();
                            indentingPrintWriter.println("inputId: " + sessionState.inputId);
                            indentingPrintWriter.println("sessionId: " + sessionState.sessionId);
                            indentingPrintWriter.println("client: " + sessionState.client);
                            indentingPrintWriter.println("seq: " + sessionState.seq);
                            indentingPrintWriter.println("callingUid: " + sessionState.callingUid);
                            indentingPrintWriter.println("callingPid: " + sessionState.callingPid);
                            indentingPrintWriter.println("userId: " + sessionState.userId);
                            indentingPrintWriter.println("sessionToken: " + sessionState.sessionToken);
                            indentingPrintWriter.println("session: " + sessionState.session);
                            indentingPrintWriter.println("hardwareSessionToken: " + sessionState.hardwareSessionToken);
                            indentingPrintWriter.decreaseIndent();
                        }
                        indentingPrintWriter.decreaseIndent();
                        indentingPrintWriter.println("mCallbacks:");
                        indentingPrintWriter.increaseIndent();
                        int beginBroadcast = orCreateUserStateLocked.mCallbacks.beginBroadcast();
                        for (int i3 = 0; i3 < beginBroadcast; i3++) {
                            indentingPrintWriter.println(orCreateUserStateLocked.mCallbacks.getRegisteredCallbackItem(i3));
                        }
                        orCreateUserStateLocked.mCallbacks.finishBroadcast();
                        indentingPrintWriter.decreaseIndent();
                        indentingPrintWriter.println("mainSessionToken: " + orCreateUserStateLocked.mainSessionToken);
                        indentingPrintWriter.decreaseIndent();
                    }
                }
                TvInputManagerService.this.mTvInputHardwareManager.dump(fileDescriptor, printWriter, strArr);
            }
        }
    }

    public static TvInputState getTvInputState(SessionState sessionState, UserState userState) {
        if (userState != null) {
            return (TvInputState) userState.inputMap.get(sessionState.inputId);
        }
        return null;
    }

    public final List getCurrentTunedInfosInternalLocked(UserState userState, int i, int i2) {
        Integer num;
        int i3;
        ArrayList arrayList = new ArrayList();
        boolean hasAccessWatchedProgramsPermission = hasAccessWatchedProgramsPermission(i, i2);
        for (SessionState sessionState : userState.sessionStateMap.values()) {
            if (sessionState.isCurrent) {
                if (sessionState.callingUid == i2) {
                    num = 0;
                    i3 = 1;
                } else {
                    num = (Integer) userState.mAppTagMap.get(Integer.valueOf(sessionState.callingUid));
                    if (num == null) {
                        int i4 = userState.mNextAppTag;
                        userState.mNextAppTag = i4 + 1;
                        num = Integer.valueOf(i4);
                        userState.mAppTagMap.put(Integer.valueOf(sessionState.callingUid), num);
                    }
                    i3 = isSystemApp(sessionState.componentName.getPackageName()) ? 2 : 3;
                }
                arrayList.add(new TunedInfo(sessionState.inputId, hasAccessWatchedProgramsPermission ? sessionState.currentChannel : null, sessionState.isRecordingSession, sessionState.isVisible, sessionState.isMainSession, i3, num.intValue()));
            }
        }
        return arrayList;
    }

    public final boolean hasAccessWatchedProgramsPermission(int i, int i2) {
        return this.mContext.checkPermission("com.android.providers.tv.permission.ACCESS_WATCHED_PROGRAMS", i, i2) == 0;
    }

    public final boolean isSystemApp(String str) {
        try {
            return (this.mContext.getPackageManager().getApplicationInfo(str, 0).flags & 1) != 0;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    public final void logTuneStateChanged(int i, SessionState sessionState, TvInputState tvInputState) {
        int i2;
        int i3;
        int i4;
        int i5;
        if (tvInputState != null) {
            i2 = tvInputState.uid;
            int type = tvInputState.info.getType();
            if (type == 0) {
                type = 1;
            }
            int i6 = tvInputState.inputNumber;
            HdmiDeviceInfo hdmiDeviceInfo = tvInputState.info.getHdmiDeviceInfo();
            i5 = hdmiDeviceInfo != null ? hdmiDeviceInfo.getPortId() : 0;
            i3 = type;
            i4 = i6;
        } else {
            i2 = -1;
            i3 = 0;
            i4 = 0;
            i5 = 0;
        }
        FrameworkStatsLog.write(FrameworkStatsLog.TIF_TUNE_CHANGED, new int[]{sessionState.callingUid, i2}, new String[]{"tif_player", "tv_input_service"}, i, sessionState.sessionId, i3, i4, i5);
    }

    /* loaded from: classes3.dex */
    public final class UserState {
        public final Map callbackPidUidMap;
        public final Map clientStateMap;
        public final List contentRatingSystemList;
        public Map inputMap;
        public final Map mAppTagMap;
        public final RemoteCallbackList mCallbacks;
        public int mNextAppTag;
        public IBinder mainSessionToken;
        public final Set packageSet;
        public final PersistentDataStore persistentDataStore;
        public final Map serviceStateMap;
        public final Map sessionStateMap;

        public UserState(Context context, int i) {
            this.inputMap = new HashMap();
            this.packageSet = new HashSet();
            this.contentRatingSystemList = new ArrayList();
            this.clientStateMap = new HashMap();
            this.serviceStateMap = new HashMap();
            this.sessionStateMap = new HashMap();
            this.mCallbacks = new RemoteCallbackList();
            this.callbackPidUidMap = new HashMap();
            this.mainSessionToken = null;
            this.mAppTagMap = new HashMap();
            this.mNextAppTag = 1;
            this.persistentDataStore = new PersistentDataStore(context, i);
        }
    }

    /* loaded from: classes3.dex */
    public final class ClientState implements IBinder.DeathRecipient {
        public IBinder clientToken;
        public final List sessionTokens = new ArrayList();
        public final int userId;

        public ClientState(IBinder iBinder, int i) {
            this.clientToken = iBinder;
            this.userId = i;
        }

        public boolean isEmpty() {
            return this.sessionTokens.isEmpty();
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            synchronized (TvInputManagerService.this.mLock) {
                ClientState clientState = (ClientState) TvInputManagerService.this.getOrCreateUserStateLocked(this.userId).clientStateMap.get(this.clientToken);
                if (clientState != null) {
                    while (clientState.sessionTokens.size() > 0) {
                        IBinder iBinder = (IBinder) clientState.sessionTokens.get(0);
                        TvInputManagerService.this.releaseSessionLocked(iBinder, 1000, this.userId);
                        if (clientState.sessionTokens.contains(iBinder)) {
                            Slog.d("TvInputManagerService", "remove sessionToken " + iBinder + " for " + this.clientToken);
                            clientState.sessionTokens.remove(iBinder);
                        }
                    }
                }
                this.clientToken = null;
            }
        }
    }

    /* loaded from: classes3.dex */
    public final class ServiceState {
        public boolean bound;
        public ServiceCallback callback;
        public final ComponentName component;
        public final ServiceConnection connection;
        public final Map hardwareInputMap;
        public final boolean isHardware;
        public boolean reconnecting;
        public ITvInputService service;
        public final List sessionTokens;

        public ServiceState(ComponentName componentName, int i) {
            this.sessionTokens = new ArrayList();
            this.hardwareInputMap = new HashMap();
            this.component = componentName;
            this.connection = new InputServiceConnection(componentName, i);
            this.isHardware = TvInputManagerService.hasHardwarePermission(TvInputManagerService.this.mContext.getPackageManager(), componentName);
        }
    }

    /* loaded from: classes3.dex */
    public final class TvInputState {
        public TvInputInfo info;
        public int inputNumber;
        public int state;
        public int uid;

        public TvInputState() {
            this.state = 0;
        }

        public String toString() {
            return "info: " + this.info + "; state: " + this.state;
        }
    }

    /* loaded from: classes3.dex */
    public final class SessionState implements IBinder.DeathRecipient {
        public final int callingPid;
        public final int callingUid;
        public final ITvInputClient client;
        public final ComponentName componentName;
        public Uri currentChannel;
        public IBinder hardwareSessionToken;
        public final String inputId;
        public boolean isCurrent;
        public boolean isMainSession;
        public final boolean isRecordingSession;
        public boolean isVisible;
        public final int seq;
        public ITvInputSession session;
        public final String sessionId;
        public final IBinder sessionToken;
        public final AttributionSource tvAppAttributionSource;
        public final int userId;

        public SessionState(IBinder iBinder, String str, ComponentName componentName, boolean z, ITvInputClient iTvInputClient, int i, int i2, int i3, int i4, String str2, AttributionSource attributionSource) {
            this.isCurrent = false;
            this.currentChannel = null;
            this.isVisible = false;
            this.isMainSession = false;
            this.sessionToken = iBinder;
            this.inputId = str;
            this.componentName = componentName;
            this.isRecordingSession = z;
            this.client = iTvInputClient;
            this.seq = i;
            this.callingUid = i2;
            this.callingPid = i3;
            this.userId = i4;
            this.sessionId = str2;
            this.tvAppAttributionSource = attributionSource;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            synchronized (TvInputManagerService.this.mLock) {
                this.session = null;
                TvInputManagerService.this.clearSessionAndNotifyClientLocked(this);
            }
        }
    }

    /* loaded from: classes3.dex */
    public final class InputServiceConnection implements ServiceConnection {
        public final ComponentName mComponent;
        public final int mUserId;

        public InputServiceConnection(ComponentName componentName, int i) {
            this.mComponent = componentName;
            this.mUserId = i;
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            synchronized (TvInputManagerService.this.mLock) {
                UserState userStateLocked = TvInputManagerService.this.getUserStateLocked(this.mUserId);
                if (userStateLocked == null) {
                    TvInputManagerService.this.mContext.unbindService(this);
                    return;
                }
                ServiceState serviceState = (ServiceState) userStateLocked.serviceStateMap.get(this.mComponent);
                serviceState.service = ITvInputService.Stub.asInterface(iBinder);
                if (serviceState.isHardware && serviceState.callback == null) {
                    serviceState.callback = new ServiceCallback(this.mComponent, this.mUserId);
                    try {
                        serviceState.service.registerCallback(serviceState.callback);
                    } catch (RemoteException e) {
                        Slog.e("TvInputManagerService", "error in registerCallback", e);
                    }
                }
                ArrayList arrayList = new ArrayList();
                for (IBinder iBinder2 : serviceState.sessionTokens) {
                    if (!TvInputManagerService.this.createSessionInternalLocked(serviceState.service, iBinder2, this.mUserId)) {
                        arrayList.add(iBinder2);
                    }
                }
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    TvInputManagerService.this.removeSessionStateLocked((IBinder) it.next(), this.mUserId);
                }
                for (TvInputState tvInputState : userStateLocked.inputMap.values()) {
                    if (tvInputState.info.getComponent().equals(componentName) && tvInputState.state != 0) {
                        TvInputManagerService.this.notifyInputStateChangedLocked(userStateLocked, tvInputState.info.getId(), tvInputState.state, null);
                    }
                }
                if (serviceState.isHardware) {
                    serviceState.hardwareInputMap.clear();
                    Iterator it2 = TvInputManagerService.this.mTvInputHardwareManager.getHardwareList().iterator();
                    while (it2.hasNext()) {
                        try {
                            serviceState.service.notifyHardwareAdded((TvInputHardwareInfo) it2.next());
                        } catch (RemoteException e2) {
                            Slog.e("TvInputManagerService", "error in notifyHardwareAdded", e2);
                        }
                    }
                    Iterator it3 = TvInputManagerService.this.mTvInputHardwareManager.getHdmiDeviceList().iterator();
                    while (it3.hasNext()) {
                        try {
                            serviceState.service.notifyHdmiDeviceAdded((HdmiDeviceInfo) it3.next());
                        } catch (RemoteException e3) {
                            Slog.e("TvInputManagerService", "error in notifyHdmiDeviceAdded", e3);
                        }
                    }
                }
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            if (!this.mComponent.equals(componentName)) {
                throw new IllegalArgumentException("Mismatched ComponentName: " + this.mComponent + " (expected), " + componentName + " (actual).");
            }
            synchronized (TvInputManagerService.this.mLock) {
                ServiceState serviceState = (ServiceState) TvInputManagerService.this.getOrCreateUserStateLocked(this.mUserId).serviceStateMap.get(this.mComponent);
                if (serviceState != null) {
                    serviceState.reconnecting = true;
                    serviceState.bound = false;
                    serviceState.service = null;
                    serviceState.callback = null;
                    TvInputManagerService.this.abortPendingCreateSessionRequestsLocked(serviceState, null, this.mUserId);
                }
            }
        }
    }

    /* loaded from: classes3.dex */
    public final class ServiceCallback extends ITvInputServiceCallback.Stub {
        public final ComponentName mComponent;
        public final int mUserId;

        public ServiceCallback(ComponentName componentName, int i) {
            this.mComponent = componentName;
            this.mUserId = i;
        }

        public final void ensureHardwarePermission() {
            if (TvInputManagerService.this.mContext.checkCallingPermission("android.permission.TV_INPUT_HARDWARE") != 0) {
                throw new SecurityException("The caller does not have hardware permission");
            }
        }

        public final void ensureValidInput(TvInputInfo tvInputInfo) {
            if (tvInputInfo.getId() == null || !this.mComponent.equals(tvInputInfo.getComponent())) {
                throw new IllegalArgumentException("Invalid TvInputInfo");
            }
        }

        public final void addHardwareInputLocked(TvInputInfo tvInputInfo) {
            TvInputManagerService.this.getServiceStateLocked(this.mComponent, this.mUserId).hardwareInputMap.put(tvInputInfo.getId(), tvInputInfo);
            TvInputManagerService.this.buildTvInputListLocked(this.mUserId, null);
        }

        public void addHardwareInput(int i, TvInputInfo tvInputInfo) {
            ensureHardwarePermission();
            ensureValidInput(tvInputInfo);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    TvInputManagerService.this.mTvInputHardwareManager.addHardwareInput(i, tvInputInfo);
                    addHardwareInputLocked(tvInputInfo);
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void addHdmiInput(int i, TvInputInfo tvInputInfo) {
            ensureHardwarePermission();
            ensureValidInput(tvInputInfo);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    TvInputManagerService.this.mTvInputHardwareManager.addHdmiInput(i, tvInputInfo);
                    addHardwareInputLocked(tvInputInfo);
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void removeHardwareInput(String str) {
            ensureHardwarePermission();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    if (TvInputManagerService.this.getServiceStateLocked(this.mComponent, this.mUserId).hardwareInputMap.remove(str) != null) {
                        TvInputManagerService.this.buildTvInputListLocked(this.mUserId, null);
                        TvInputManagerService.this.mTvInputHardwareManager.removeHardwareInput(str);
                    } else {
                        Slog.e("TvInputManagerService", "failed to remove input " + str);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    /* loaded from: classes3.dex */
    public final class SessionCallback extends ITvInputSessionCallback.Stub {
        public final InputChannel[] mChannels;
        public final SessionState mSessionState;

        public SessionCallback(SessionState sessionState, InputChannel[] inputChannelArr) {
            this.mSessionState = sessionState;
            this.mChannels = inputChannelArr;
        }

        public void onSessionCreated(ITvInputSession iTvInputSession, IBinder iBinder) {
            synchronized (TvInputManagerService.this.mLock) {
                this.mSessionState.session = iTvInputSession;
                this.mSessionState.hardwareSessionToken = iBinder;
                if (iTvInputSession != null && addSessionTokenToClientStateLocked(iTvInputSession)) {
                    TvInputManagerService.this.sendSessionTokenToClientLocked(this.mSessionState.client, this.mSessionState.inputId, this.mSessionState.sessionToken, this.mChannels[0], this.mSessionState.seq);
                } else {
                    TvInputManagerService.this.removeSessionStateLocked(this.mSessionState.sessionToken, this.mSessionState.userId);
                    TvInputManagerService.this.sendSessionTokenToClientLocked(this.mSessionState.client, this.mSessionState.inputId, null, null, this.mSessionState.seq);
                }
                this.mChannels[0].dispose();
            }
        }

        public final boolean addSessionTokenToClientStateLocked(ITvInputSession iTvInputSession) {
            try {
                iTvInputSession.asBinder().linkToDeath(this.mSessionState, 0);
                IBinder asBinder = this.mSessionState.client.asBinder();
                UserState orCreateUserStateLocked = TvInputManagerService.this.getOrCreateUserStateLocked(this.mSessionState.userId);
                ClientState clientState = (ClientState) orCreateUserStateLocked.clientStateMap.get(asBinder);
                if (clientState == null) {
                    clientState = new ClientState(asBinder, this.mSessionState.userId);
                    try {
                        asBinder.linkToDeath(clientState, 0);
                        orCreateUserStateLocked.clientStateMap.put(asBinder, clientState);
                    } catch (RemoteException e) {
                        Slog.e("TvInputManagerService", "client process has already died", e);
                        return false;
                    }
                }
                clientState.sessionTokens.add(this.mSessionState.sessionToken);
                return true;
            } catch (RemoteException e2) {
                Slog.e("TvInputManagerService", "session process has already died", e2);
                return false;
            }
        }

        public void onChannelRetuned(Uri uri) {
            synchronized (TvInputManagerService.this.mLock) {
                if (this.mSessionState.session == null || this.mSessionState.client == null) {
                    return;
                }
                try {
                    this.mSessionState.client.onChannelRetuned(uri, this.mSessionState.seq);
                    if (!this.mSessionState.isCurrent || !Objects.equals(this.mSessionState.currentChannel, uri)) {
                        UserState orCreateUserStateLocked = TvInputManagerService.this.getOrCreateUserStateLocked(this.mSessionState.userId);
                        this.mSessionState.isCurrent = true;
                        this.mSessionState.currentChannel = uri;
                        TvInputManagerService.this.notifyCurrentChannelInfosUpdatedLocked(orCreateUserStateLocked);
                    }
                } catch (RemoteException e) {
                    Slog.e("TvInputManagerService", "error in onChannelRetuned", e);
                }
            }
        }

        public void onAudioPresentationsChanged(List list) {
            synchronized (TvInputManagerService.this.mLock) {
                if (this.mSessionState.session == null || this.mSessionState.client == null) {
                    return;
                }
                try {
                    this.mSessionState.client.onAudioPresentationsChanged(list, this.mSessionState.seq);
                } catch (RemoteException e) {
                    Slog.e("TvInputManagerService", "error in onAudioPresentationsChanged", e);
                }
            }
        }

        public void onAudioPresentationSelected(int i, int i2) {
            synchronized (TvInputManagerService.this.mLock) {
                if (this.mSessionState.session == null || this.mSessionState.client == null) {
                    return;
                }
                try {
                    this.mSessionState.client.onAudioPresentationSelected(i, i2, this.mSessionState.seq);
                } catch (RemoteException e) {
                    Slog.e("TvInputManagerService", "error in onAudioPresentationSelected", e);
                }
            }
        }

        public void onTracksChanged(List list) {
            synchronized (TvInputManagerService.this.mLock) {
                if (this.mSessionState.session == null || this.mSessionState.client == null) {
                    return;
                }
                try {
                    this.mSessionState.client.onTracksChanged(list, this.mSessionState.seq);
                } catch (RemoteException e) {
                    Slog.e("TvInputManagerService", "error in onTracksChanged", e);
                }
            }
        }

        public void onTrackSelected(int i, String str) {
            synchronized (TvInputManagerService.this.mLock) {
                if (this.mSessionState.session == null || this.mSessionState.client == null) {
                    return;
                }
                try {
                    this.mSessionState.client.onTrackSelected(i, str, this.mSessionState.seq);
                } catch (RemoteException e) {
                    Slog.e("TvInputManagerService", "error in onTrackSelected", e);
                }
            }
        }

        public void onVideoAvailable() {
            synchronized (TvInputManagerService.this.mLock) {
                if (this.mSessionState.session != null && this.mSessionState.client != null) {
                    SessionState sessionState = this.mSessionState;
                    TvInputManagerService tvInputManagerService = TvInputManagerService.this;
                    TvInputState tvInputState = TvInputManagerService.getTvInputState(sessionState, tvInputManagerService.getUserStateLocked(tvInputManagerService.mCurrentUserId));
                    try {
                        this.mSessionState.client.onVideoAvailable(this.mSessionState.seq);
                        TvInputManagerService.this.logTuneStateChanged(6, this.mSessionState, tvInputState);
                    } catch (RemoteException e) {
                        Slog.e("TvInputManagerService", "error in onVideoAvailable", e);
                    }
                }
            }
        }

        public void onVideoUnavailable(int i) {
            synchronized (TvInputManagerService.this.mLock) {
                if (this.mSessionState.session != null && this.mSessionState.client != null) {
                    SessionState sessionState = this.mSessionState;
                    TvInputManagerService tvInputManagerService = TvInputManagerService.this;
                    TvInputState tvInputState = TvInputManagerService.getTvInputState(sessionState, tvInputManagerService.getUserStateLocked(tvInputManagerService.mCurrentUserId));
                    try {
                        this.mSessionState.client.onVideoUnavailable(i, this.mSessionState.seq);
                        TvInputManagerService.this.logTuneStateChanged(TvInputManagerService.getVideoUnavailableReasonForStatsd(i), this.mSessionState, tvInputState);
                    } catch (RemoteException e) {
                        Slog.e("TvInputManagerService", "error in onVideoUnavailable", e);
                    }
                }
            }
        }

        public void onContentAllowed() {
            synchronized (TvInputManagerService.this.mLock) {
                if (this.mSessionState.session == null || this.mSessionState.client == null) {
                    return;
                }
                try {
                    this.mSessionState.client.onContentAllowed(this.mSessionState.seq);
                } catch (RemoteException e) {
                    Slog.e("TvInputManagerService", "error in onContentAllowed", e);
                }
            }
        }

        public void onContentBlocked(String str) {
            synchronized (TvInputManagerService.this.mLock) {
                if (this.mSessionState.session == null || this.mSessionState.client == null) {
                    return;
                }
                try {
                    this.mSessionState.client.onContentBlocked(str, this.mSessionState.seq);
                } catch (RemoteException e) {
                    Slog.e("TvInputManagerService", "error in onContentBlocked", e);
                }
            }
        }

        public void onLayoutSurface(int i, int i2, int i3, int i4) {
            synchronized (TvInputManagerService.this.mLock) {
                if (this.mSessionState.session == null || this.mSessionState.client == null) {
                    return;
                }
                try {
                    this.mSessionState.client.onLayoutSurface(i, i2, i3, i4, this.mSessionState.seq);
                } catch (RemoteException e) {
                    Slog.e("TvInputManagerService", "error in onLayoutSurface", e);
                }
            }
        }

        public void onSessionEvent(String str, Bundle bundle) {
            synchronized (TvInputManagerService.this.mLock) {
                if (this.mSessionState.session == null || this.mSessionState.client == null) {
                    return;
                }
                try {
                    this.mSessionState.client.onSessionEvent(str, bundle, this.mSessionState.seq);
                } catch (RemoteException e) {
                    Slog.e("TvInputManagerService", "error in onSessionEvent", e);
                }
            }
        }

        public void onTimeShiftStatusChanged(int i) {
            synchronized (TvInputManagerService.this.mLock) {
                if (this.mSessionState.session == null || this.mSessionState.client == null) {
                    return;
                }
                try {
                    this.mSessionState.client.onTimeShiftStatusChanged(i, this.mSessionState.seq);
                } catch (RemoteException e) {
                    Slog.e("TvInputManagerService", "error in onTimeShiftStatusChanged", e);
                }
            }
        }

        public void onTimeShiftStartPositionChanged(long j) {
            synchronized (TvInputManagerService.this.mLock) {
                if (this.mSessionState.session == null || this.mSessionState.client == null) {
                    return;
                }
                try {
                    this.mSessionState.client.onTimeShiftStartPositionChanged(j, this.mSessionState.seq);
                } catch (RemoteException e) {
                    Slog.e("TvInputManagerService", "error in onTimeShiftStartPositionChanged", e);
                }
            }
        }

        public void onTimeShiftCurrentPositionChanged(long j) {
            synchronized (TvInputManagerService.this.mLock) {
                if (this.mSessionState.session == null || this.mSessionState.client == null) {
                    return;
                }
                try {
                    this.mSessionState.client.onTimeShiftCurrentPositionChanged(j, this.mSessionState.seq);
                } catch (RemoteException e) {
                    Slog.e("TvInputManagerService", "error in onTimeShiftCurrentPositionChanged", e);
                }
            }
        }

        public void onAitInfoUpdated(AitInfo aitInfo) {
            synchronized (TvInputManagerService.this.mLock) {
                if (this.mSessionState.session == null || this.mSessionState.client == null) {
                    return;
                }
                try {
                    this.mSessionState.client.onAitInfoUpdated(aitInfo, this.mSessionState.seq);
                } catch (RemoteException e) {
                    Slog.e("TvInputManagerService", "error in onAitInfoUpdated", e);
                }
            }
        }

        public void onSignalStrength(int i) {
            synchronized (TvInputManagerService.this.mLock) {
                if (this.mSessionState.session == null || this.mSessionState.client == null) {
                    return;
                }
                try {
                    this.mSessionState.client.onSignalStrength(i, this.mSessionState.seq);
                } catch (RemoteException e) {
                    Slog.e("TvInputManagerService", "error in onSignalStrength", e);
                }
            }
        }

        public void onCueingMessageAvailability(boolean z) {
            synchronized (TvInputManagerService.this.mLock) {
                if (this.mSessionState.session == null || this.mSessionState.client == null) {
                    return;
                }
                try {
                    this.mSessionState.client.onCueingMessageAvailability(z, this.mSessionState.seq);
                } catch (RemoteException e) {
                    Slog.e("TvInputManagerService", "error in onCueingMessageAvailability", e);
                }
            }
        }

        public void onTimeShiftMode(int i) {
            synchronized (TvInputManagerService.this.mLock) {
                if (this.mSessionState.session == null || this.mSessionState.client == null) {
                    return;
                }
                try {
                    this.mSessionState.client.onTimeShiftMode(i, this.mSessionState.seq);
                } catch (RemoteException e) {
                    Slog.e("TvInputManagerService", "error in onTimeShiftMode", e);
                }
            }
        }

        public void onAvailableSpeeds(float[] fArr) {
            synchronized (TvInputManagerService.this.mLock) {
                if (this.mSessionState.session == null || this.mSessionState.client == null) {
                    return;
                }
                try {
                    this.mSessionState.client.onAvailableSpeeds(fArr, this.mSessionState.seq);
                } catch (RemoteException e) {
                    Slog.e("TvInputManagerService", "error in onAvailableSpeeds", e);
                }
            }
        }

        public void onTuned(Uri uri) {
            synchronized (TvInputManagerService.this.mLock) {
                if (this.mSessionState.session == null || this.mSessionState.client == null) {
                    return;
                }
                try {
                    this.mSessionState.client.onTuned(uri, this.mSessionState.seq);
                } catch (RemoteException e) {
                    Slog.e("TvInputManagerService", "error in onTuned", e);
                }
            }
        }

        public void onTvMessage(int i, Bundle bundle) {
            synchronized (TvInputManagerService.this.mLock) {
                if (this.mSessionState.session == null || this.mSessionState.client == null) {
                    return;
                }
                try {
                    this.mSessionState.client.onTvMessage(i, bundle, this.mSessionState.seq);
                } catch (RemoteException e) {
                    Slog.e("TvInputManagerService", "error in onTvMessage", e);
                }
            }
        }

        public void onRecordingStopped(Uri uri) {
            synchronized (TvInputManagerService.this.mLock) {
                if (this.mSessionState.session == null || this.mSessionState.client == null) {
                    return;
                }
                try {
                    this.mSessionState.client.onRecordingStopped(uri, this.mSessionState.seq);
                } catch (RemoteException e) {
                    Slog.e("TvInputManagerService", "error in onRecordingStopped", e);
                }
            }
        }

        public void onError(int i) {
            synchronized (TvInputManagerService.this.mLock) {
                if (this.mSessionState.session == null || this.mSessionState.client == null) {
                    return;
                }
                try {
                    this.mSessionState.client.onError(i, this.mSessionState.seq);
                } catch (RemoteException e) {
                    Slog.e("TvInputManagerService", "error in onError", e);
                }
            }
        }

        public void onBroadcastInfoResponse(BroadcastInfoResponse broadcastInfoResponse) {
            synchronized (TvInputManagerService.this.mLock) {
                if (this.mSessionState.session == null || this.mSessionState.client == null) {
                    return;
                }
                try {
                    this.mSessionState.client.onBroadcastInfoResponse(broadcastInfoResponse, this.mSessionState.seq);
                } catch (RemoteException e) {
                    Slog.e("TvInputManagerService", "error in onBroadcastInfoResponse", e);
                }
            }
        }

        public void onAdResponse(AdResponse adResponse) {
            synchronized (TvInputManagerService.this.mLock) {
                if (this.mSessionState.session == null || this.mSessionState.client == null) {
                    return;
                }
                try {
                    this.mSessionState.client.onAdResponse(adResponse, this.mSessionState.seq);
                } catch (RemoteException e) {
                    Slog.e("TvInputManagerService", "error in onAdResponse", e);
                }
            }
        }

        public void onAdBufferConsumed(AdBuffer adBuffer) {
            SharedMemory sharedMemory;
            synchronized (TvInputManagerService.this.mLock) {
                if (this.mSessionState.session != null) {
                    try {
                        if (this.mSessionState.client != null) {
                            try {
                                this.mSessionState.client.onAdBufferConsumed(adBuffer, this.mSessionState.seq);
                            } catch (RemoteException e) {
                                Slog.e("TvInputManagerService", "error in onAdBufferConsumed", e);
                                if (adBuffer != null) {
                                    sharedMemory = adBuffer.getSharedMemory();
                                }
                            }
                            if (adBuffer != null) {
                                sharedMemory = adBuffer.getSharedMemory();
                                sharedMemory.close();
                            }
                        }
                    } catch (Throwable th) {
                        if (adBuffer != null) {
                            adBuffer.getSharedMemory().close();
                        }
                        throw th;
                    }
                }
            }
        }
    }

    public final UserState getUserStateLocked(int i) {
        return (UserState) this.mUserStates.get(i);
    }

    /* loaded from: classes3.dex */
    public final class WatchLogHandler extends Handler {
        public ContentResolver mContentResolver;

        public WatchLogHandler(ContentResolver contentResolver, Looper looper) {
            super(looper);
            this.mContentResolver = contentResolver;
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 1) {
                SomeArgs someArgs = (SomeArgs) message.obj;
                String str = (String) someArgs.arg1;
                long longValue = ((Long) someArgs.arg2).longValue();
                long longValue2 = ((Long) someArgs.arg3).longValue();
                Bundle bundle = (Bundle) someArgs.arg4;
                IBinder iBinder = (IBinder) someArgs.arg5;
                ContentValues contentValues = new ContentValues();
                contentValues.put("package_name", str);
                contentValues.put("watch_start_time_utc_millis", Long.valueOf(longValue));
                contentValues.put("channel_id", Long.valueOf(longValue2));
                if (bundle != null) {
                    contentValues.put("tune_params", encodeTuneParams(bundle));
                }
                contentValues.put("session_token", iBinder.toString());
                try {
                    this.mContentResolver.insert(TvContract.WatchedPrograms.CONTENT_URI, contentValues);
                } catch (IllegalArgumentException e) {
                    Slog.w("TvInputManagerService", "error in insert db for MSG_LOG_WATCH_START", e);
                }
                someArgs.recycle();
                return;
            }
            if (i != 2) {
                if (i == 3) {
                    this.mContentResolver = (ContentResolver) message.obj;
                    return;
                }
                Slog.w("TvInputManagerService", "unhandled message code: " + message.what);
                return;
            }
            SomeArgs someArgs2 = (SomeArgs) message.obj;
            IBinder iBinder2 = (IBinder) someArgs2.arg1;
            long longValue3 = ((Long) someArgs2.arg2).longValue();
            ContentValues contentValues2 = new ContentValues();
            contentValues2.put("watch_end_time_utc_millis", Long.valueOf(longValue3));
            contentValues2.put("session_token", iBinder2.toString());
            try {
                this.mContentResolver.insert(TvContract.WatchedPrograms.CONTENT_URI, contentValues2);
            } catch (IllegalArgumentException e2) {
                Slog.w("TvInputManagerService", "error in insert db for MSG_LOG_WATCH_END", e2);
            }
            someArgs2.recycle();
        }

        public final String encodeTuneParams(Bundle bundle) {
            StringBuilder sb = new StringBuilder();
            Iterator<String> it = bundle.keySet().iterator();
            while (it.hasNext()) {
                String next = it.next();
                Object obj = bundle.get(next);
                if (obj != null) {
                    sb.append(replaceEscapeCharacters(next));
                    sb.append("=");
                    sb.append(replaceEscapeCharacters(obj.toString()));
                    if (it.hasNext()) {
                        sb.append(", ");
                    }
                }
            }
            return sb.toString();
        }

        public final String replaceEscapeCharacters(String str) {
            StringBuilder sb = new StringBuilder();
            for (char c : str.toCharArray()) {
                if ("%=,".indexOf(c) >= 0) {
                    sb.append('%');
                }
                sb.append(c);
            }
            return sb.toString();
        }
    }

    /* loaded from: classes3.dex */
    public final class HardwareListener implements TvInputHardwareManager.Listener {
        public HardwareListener() {
        }

        @Override // com.android.server.tv.TvInputHardwareManager.Listener
        public void onStateChanged(String str, int i) {
            synchronized (TvInputManagerService.this.mLock) {
                TvInputManagerService tvInputManagerService = TvInputManagerService.this;
                tvInputManagerService.setStateLocked(str, i, tvInputManagerService.mCurrentUserId);
            }
        }

        @Override // com.android.server.tv.TvInputHardwareManager.Listener
        public void onHardwareDeviceAdded(TvInputHardwareInfo tvInputHardwareInfo) {
            synchronized (TvInputManagerService.this.mLock) {
                TvInputManagerService tvInputManagerService = TvInputManagerService.this;
                for (ServiceState serviceState : tvInputManagerService.getOrCreateUserStateLocked(tvInputManagerService.mCurrentUserId).serviceStateMap.values()) {
                    if (serviceState.isHardware && serviceState.service != null) {
                        try {
                            serviceState.service.notifyHardwareAdded(tvInputHardwareInfo);
                        } catch (RemoteException e) {
                            Slog.e("TvInputManagerService", "error in notifyHardwareAdded", e);
                        }
                    }
                }
            }
        }

        @Override // com.android.server.tv.TvInputHardwareManager.Listener
        public void onHardwareDeviceRemoved(TvInputHardwareInfo tvInputHardwareInfo) {
            synchronized (TvInputManagerService.this.mLock) {
                TvInputManagerService tvInputManagerService = TvInputManagerService.this;
                for (ServiceState serviceState : tvInputManagerService.getOrCreateUserStateLocked(tvInputManagerService.mCurrentUserId).serviceStateMap.values()) {
                    if (serviceState.isHardware && serviceState.service != null) {
                        try {
                            serviceState.service.notifyHardwareRemoved(tvInputHardwareInfo);
                        } catch (RemoteException e) {
                            Slog.e("TvInputManagerService", "error in notifyHardwareRemoved", e);
                        }
                    }
                }
            }
        }

        @Override // com.android.server.tv.TvInputHardwareManager.Listener
        public void onHdmiDeviceAdded(HdmiDeviceInfo hdmiDeviceInfo) {
            synchronized (TvInputManagerService.this.mLock) {
                TvInputManagerService tvInputManagerService = TvInputManagerService.this;
                for (ServiceState serviceState : tvInputManagerService.getOrCreateUserStateLocked(tvInputManagerService.mCurrentUserId).serviceStateMap.values()) {
                    if (serviceState.isHardware && serviceState.service != null) {
                        try {
                            serviceState.service.notifyHdmiDeviceAdded(hdmiDeviceInfo);
                        } catch (RemoteException e) {
                            Slog.e("TvInputManagerService", "error in notifyHdmiDeviceAdded", e);
                        }
                    }
                }
            }
        }

        @Override // com.android.server.tv.TvInputHardwareManager.Listener
        public void onHdmiDeviceRemoved(HdmiDeviceInfo hdmiDeviceInfo) {
            synchronized (TvInputManagerService.this.mLock) {
                TvInputManagerService tvInputManagerService = TvInputManagerService.this;
                for (ServiceState serviceState : tvInputManagerService.getOrCreateUserStateLocked(tvInputManagerService.mCurrentUserId).serviceStateMap.values()) {
                    if (serviceState.isHardware && serviceState.service != null) {
                        try {
                            serviceState.service.notifyHdmiDeviceRemoved(hdmiDeviceInfo);
                        } catch (RemoteException e) {
                            Slog.e("TvInputManagerService", "error in notifyHdmiDeviceRemoved", e);
                        }
                    }
                }
            }
        }

        @Override // com.android.server.tv.TvInputHardwareManager.Listener
        public void onHdmiDeviceUpdated(String str, HdmiDeviceInfo hdmiDeviceInfo) {
            Integer num;
            synchronized (TvInputManagerService.this.mLock) {
                int devicePowerStatus = hdmiDeviceInfo.getDevicePowerStatus();
                if (devicePowerStatus == 0) {
                    num = 0;
                } else {
                    num = (devicePowerStatus == 1 || devicePowerStatus == 2 || devicePowerStatus == 3) ? 1 : null;
                }
                if (num != null) {
                    TvInputManagerService.this.setStateLocked(str, num.intValue(), TvInputManagerService.this.mCurrentUserId);
                }
                TvInputManagerService tvInputManagerService = TvInputManagerService.this;
                for (ServiceState serviceState : tvInputManagerService.getOrCreateUserStateLocked(tvInputManagerService.mCurrentUserId).serviceStateMap.values()) {
                    if (serviceState.isHardware && serviceState.service != null) {
                        try {
                            serviceState.service.notifyHdmiDeviceUpdated(hdmiDeviceInfo);
                        } catch (RemoteException e) {
                            Slog.e("TvInputManagerService", "error in notifyHdmiDeviceUpdated", e);
                        }
                    }
                }
            }
        }

        @Override // com.android.server.tv.TvInputHardwareManager.Listener
        public void onTvMessage(String str, int i, Bundle bundle) {
            synchronized (TvInputManagerService.this.mLock) {
                TvInputManagerService tvInputManagerService = TvInputManagerService.this;
                UserState orCreateUserStateLocked = tvInputManagerService.getOrCreateUserStateLocked(tvInputManagerService.mCurrentUserId);
                TvInputState tvInputState = (TvInputState) orCreateUserStateLocked.inputMap.get(str);
                if (tvInputState == null) {
                    Slog.e("TvInputManagerService", "failed to send TV message - unknown input id " + str);
                    return;
                }
                Iterator it = ((ServiceState) orCreateUserStateLocked.serviceStateMap.get(tvInputState.info.getComponent())).sessionTokens.iterator();
                while (it.hasNext()) {
                    try {
                        SessionState sessionStateLocked = TvInputManagerService.this.getSessionStateLocked((IBinder) it.next(), Binder.getCallingUid(), TvInputManagerService.this.mCurrentUserId);
                        if (!sessionStateLocked.isRecordingSession && sessionStateLocked.hardwareSessionToken != null) {
                            sessionStateLocked.session.notifyTvMessage(i, bundle);
                        }
                    } catch (RemoteException e) {
                        Slog.e("TvInputManagerService", "error in onTvMessage", e);
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public class SessionNotFoundException extends IllegalArgumentException {
        public SessionNotFoundException(String str) {
            super(str);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public class ClientPidNotFoundException extends IllegalArgumentException {
        public ClientPidNotFoundException(String str) {
            super(str);
        }
    }
}
