package android.media;

import android.app.Service;
import android.content.Intent;
import android.media.IMediaRoute2ProviderService;
import android.media.MediaRoute2Info;
import android.media.MediaRoute2ProviderInfo;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import com.android.internal.util.function.QuadConsumer;
import com.android.internal.util.function.QuintConsumer;
import com.android.internal.util.function.TriConsumer;
import com.android.internal.util.function.pooled.PooledLambda;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;

/* loaded from: classes2.dex */
public abstract class MediaRoute2ProviderService extends Service {
    public static final String CATEGORY_SELF_SCAN_ONLY = "android.media.MediaRoute2ProviderService.SELF_SCAN_ONLY";
    private static final int MAX_REQUEST_IDS_SIZE = 500;
    public static final int REASON_INVALID_COMMAND = 4;
    public static final int REASON_NETWORK_ERROR = 2;
    public static final int REASON_REJECTED = 1;
    public static final int REASON_ROUTE_NOT_AVAILABLE = 3;
    public static final int REASON_UNKNOWN_ERROR = 0;
    public static final long REQUEST_ID_NONE = 0;
    public static final String SERVICE_INTERFACE = "android.media.MediaRoute2ProviderService";
    private volatile MediaRoute2ProviderInfo mProviderInfo;
    private IMediaRoute2ProviderServiceCallback mRemoteCallback;
    private MediaRoute2ProviderServiceStub mStub;
    private static final String TAG = "MR2ProviderService";
    private static final boolean DEBUG = Log.isLoggable(TAG, 3);
    private final Object mSessionLock = new Object();
    private final Object mRequestIdsLock = new Object();
    private final AtomicBoolean mStatePublishScheduled = new AtomicBoolean(false);
    private final AtomicBoolean mSessionUpdateScheduled = new AtomicBoolean(false);
    private final Deque<Long> mRequestIds = new ArrayDeque(500);
    private final ArrayMap<String, RoutingSessionInfo> mSessionInfos = new ArrayMap<>();
    private final Handler mHandler = new Handler(Looper.getMainLooper());

    @Retention(RetentionPolicy.SOURCE)
    public @interface Reason {
    }

    public abstract void onCreateSession(long j, String str, String str2, Bundle bundle);

    public abstract void onDeselectRoute(long j, String str, String str2);

    public abstract void onReleaseSession(long j, String str);

    public abstract void onSelectRoute(long j, String str, String str2);

    public abstract void onSetRouteVolume(long j, String str, int i);

    public abstract void onSetSessionVolume(long j, String str, int i);

    public abstract void onTransferToRoute(long j, String str, String str2);

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        if (SERVICE_INTERFACE.equals(intent.getAction())) {
            if (this.mStub == null) {
                this.mStub = new MediaRoute2ProviderServiceStub();
            }
            return this.mStub;
        }
        return null;
    }

    public final RoutingSessionInfo getSessionInfo(String sessionId) {
        RoutingSessionInfo routingSessionInfo;
        if (TextUtils.isEmpty(sessionId)) {
            throw new IllegalArgumentException("sessionId must not be empty");
        }
        synchronized (this.mSessionLock) {
            routingSessionInfo = this.mSessionInfos.get(sessionId);
        }
        return routingSessionInfo;
    }

    public final List<RoutingSessionInfo> getAllSessionInfo() {
        ArrayList arrayList;
        synchronized (this.mSessionLock) {
            arrayList = new ArrayList(this.mSessionInfos.values());
        }
        return arrayList;
    }

    public final void notifySessionCreated(long requestId, RoutingSessionInfo sessionInfo) {
        Objects.requireNonNull(sessionInfo, "sessionInfo must not be null");
        if (DEBUG) {
            Log.d(TAG, "notifySessionCreated: Creating a session. requestId=" + requestId + ", sessionInfo=" + sessionInfo);
        }
        if (requestId != 0 && !removeRequestId(requestId)) {
            Log.w(TAG, "notifySessionCreated: The requestId doesn't exist. requestId=" + requestId);
            return;
        }
        String sessionId = sessionInfo.getId();
        synchronized (this.mSessionLock) {
            if (this.mSessionInfos.containsKey(sessionId)) {
                Log.w(TAG, "notifySessionCreated: Ignoring duplicate session id.");
                return;
            }
            this.mSessionInfos.put(sessionInfo.getId(), sessionInfo);
            if (this.mRemoteCallback == null) {
                return;
            }
            try {
                this.mRemoteCallback.notifySessionCreated(requestId, sessionInfo);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed to notify session created.");
            }
        }
    }

    public final void notifySessionUpdated(RoutingSessionInfo sessionInfo) {
        Objects.requireNonNull(sessionInfo, "sessionInfo must not be null");
        if (DEBUG) {
            Log.d(TAG, "notifySessionUpdated: Updating session id=" + sessionInfo);
        }
        String sessionId = sessionInfo.getId();
        synchronized (this.mSessionLock) {
            if (this.mSessionInfos.containsKey(sessionId)) {
                this.mSessionInfos.put(sessionId, sessionInfo);
                scheduleUpdateSessions();
            } else {
                Log.w(TAG, "notifySessionUpdated: Ignoring unknown session info.");
            }
        }
    }

    public final void notifySessionReleased(String sessionId) {
        if (TextUtils.isEmpty(sessionId)) {
            throw new IllegalArgumentException("sessionId must not be empty");
        }
        if (DEBUG) {
            Log.d(TAG, "notifySessionReleased: Releasing session id=" + sessionId);
        }
        synchronized (this.mSessionLock) {
            RoutingSessionInfo sessionInfo = this.mSessionInfos.remove(sessionId);
            if (sessionInfo == null) {
                Log.w(TAG, "notifySessionReleased: Ignoring unknown session info.");
            } else {
                if (this.mRemoteCallback == null) {
                    return;
                }
                try {
                    this.mRemoteCallback.notifySessionReleased(sessionInfo);
                } catch (RemoteException ex) {
                    Log.w(TAG, "Failed to notify session released.", ex);
                }
            }
        }
    }

    public final void notifyRequestFailed(long requestId, int reason) {
        if (this.mRemoteCallback == null) {
            return;
        }
        if (!removeRequestId(requestId)) {
            Log.w(TAG, "notifyRequestFailed: The requestId doesn't exist. requestId=" + requestId);
            return;
        }
        try {
            this.mRemoteCallback.notifyRequestFailed(requestId, reason);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed to notify that the request has failed.");
        }
    }

    public void onDiscoveryPreferenceChanged(RouteDiscoveryPreference preference) {
    }

    public final void notifyRoutes(Collection<MediaRoute2Info> routes) {
        Objects.requireNonNull(routes, "routes must not be null");
        List<MediaRoute2Info> sanitizedRoutes = new ArrayList<>(routes.size());
        for (MediaRoute2Info route : routes) {
            if (route.isSystemRouteType()) {
                Log.w(TAG, "Attempting to add a system route type from a non-system route provider. Overriding type to TYPE_UNKNOWN. Route: " + route);
                sanitizedRoutes.add(new MediaRoute2Info.Builder(route).setType(0).build());
            } else {
                sanitizedRoutes.add(route);
            }
        }
        this.mProviderInfo = new MediaRoute2ProviderInfo.Builder().addRoutes(sanitizedRoutes).build();
        schedulePublishState();
    }

    void setCallback(IMediaRoute2ProviderServiceCallback callback) {
        this.mRemoteCallback = callback;
        schedulePublishState();
        scheduleUpdateSessions();
    }

    void schedulePublishState() {
        if (this.mStatePublishScheduled.compareAndSet(false, true)) {
            this.mHandler.post(new Runnable() { // from class: android.media.MediaRoute2ProviderService$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    MediaRoute2ProviderService.this.publishState();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void publishState() {
        if (!this.mStatePublishScheduled.compareAndSet(true, false) || this.mRemoteCallback == null || this.mProviderInfo == null) {
            return;
        }
        try {
            this.mRemoteCallback.notifyProviderUpdated(this.mProviderInfo);
        } catch (RemoteException ex) {
            Log.w(TAG, "Failed to publish provider state.", ex);
        }
    }

    void scheduleUpdateSessions() {
        if (this.mSessionUpdateScheduled.compareAndSet(false, true)) {
            this.mHandler.post(new Runnable() { // from class: android.media.MediaRoute2ProviderService$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    MediaRoute2ProviderService.this.updateSessions();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateSessions() {
        List<RoutingSessionInfo> sessions;
        if (!this.mSessionUpdateScheduled.compareAndSet(true, false) || this.mRemoteCallback == null) {
            return;
        }
        synchronized (this.mSessionLock) {
            sessions = new ArrayList<>(this.mSessionInfos.values());
        }
        try {
            this.mRemoteCallback.notifySessionsUpdated(sessions);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed to notify session info changed.");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addRequestId(long requestId) {
        synchronized (this.mRequestIdsLock) {
            if (this.mRequestIds.size() >= 500) {
                this.mRequestIds.removeFirst();
            }
            this.mRequestIds.addLast(Long.valueOf(requestId));
        }
    }

    private boolean removeRequestId(long requestId) {
        boolean removeFirstOccurrence;
        synchronized (this.mRequestIdsLock) {
            removeFirstOccurrence = this.mRequestIds.removeFirstOccurrence(Long.valueOf(requestId));
        }
        return removeFirstOccurrence;
    }

    final class MediaRoute2ProviderServiceStub extends IMediaRoute2ProviderService.Stub {
        MediaRoute2ProviderServiceStub() {
        }

        private boolean checkCallerIsSystem() {
            return Binder.getCallingUid() == 1000;
        }

        private boolean checkSessionIdIsValid(String sessionId, String description) {
            if (TextUtils.isEmpty(sessionId)) {
                Log.w(MediaRoute2ProviderService.TAG, description + ": Ignoring empty sessionId from system service.");
                return false;
            }
            if (MediaRoute2ProviderService.this.getSessionInfo(sessionId) == null) {
                Log.w(MediaRoute2ProviderService.TAG, description + ": Ignoring unknown session from system service. sessionId=" + sessionId);
                return false;
            }
            return true;
        }

        private boolean checkRouteIdIsValid(String routeId, String description) {
            if (TextUtils.isEmpty(routeId)) {
                Log.w(MediaRoute2ProviderService.TAG, description + ": Ignoring empty routeId from system service.");
                return false;
            }
            if (MediaRoute2ProviderService.this.mProviderInfo == null || MediaRoute2ProviderService.this.mProviderInfo.getRoute(routeId) == null) {
                Log.w(MediaRoute2ProviderService.TAG, description + ": Ignoring unknown route from system service. routeId=" + routeId);
                return false;
            }
            return true;
        }

        @Override // android.media.IMediaRoute2ProviderService
        public void setCallback(IMediaRoute2ProviderServiceCallback callback) {
            if (!checkCallerIsSystem()) {
                return;
            }
            MediaRoute2ProviderService.this.mHandler.sendMessage(PooledLambda.obtainMessage(new BiConsumer() { // from class: android.media.MediaRoute2ProviderService$MediaRoute2ProviderServiceStub$$ExternalSyntheticLambda8
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    ((MediaRoute2ProviderService) obj).setCallback((IMediaRoute2ProviderServiceCallback) obj2);
                }
            }, MediaRoute2ProviderService.this, callback));
        }

        @Override // android.media.IMediaRoute2ProviderService
        public void updateDiscoveryPreference(RouteDiscoveryPreference discoveryPreference) {
            if (!checkCallerIsSystem()) {
                return;
            }
            MediaRoute2ProviderService.this.mHandler.sendMessage(PooledLambda.obtainMessage(new BiConsumer() { // from class: android.media.MediaRoute2ProviderService$MediaRoute2ProviderServiceStub$$ExternalSyntheticLambda3
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    ((MediaRoute2ProviderService) obj).onDiscoveryPreferenceChanged((RouteDiscoveryPreference) obj2);
                }
            }, MediaRoute2ProviderService.this, discoveryPreference));
        }

        @Override // android.media.IMediaRoute2ProviderService
        public void setRouteVolume(long requestId, String routeId, int volume) {
            if (!checkCallerIsSystem() || !checkRouteIdIsValid(routeId, "setRouteVolume")) {
                return;
            }
            MediaRoute2ProviderService.this.addRequestId(requestId);
            MediaRoute2ProviderService.this.mHandler.sendMessage(PooledLambda.obtainMessage(new QuadConsumer() { // from class: android.media.MediaRoute2ProviderService$MediaRoute2ProviderServiceStub$$ExternalSyntheticLambda5
                @Override // com.android.internal.util.function.QuadConsumer
                public final void accept(Object obj, Object obj2, Object obj3, Object obj4) {
                    ((MediaRoute2ProviderService) obj).onSetRouteVolume(((Long) obj2).longValue(), (String) obj3, ((Integer) obj4).intValue());
                }
            }, MediaRoute2ProviderService.this, Long.valueOf(requestId), routeId, Integer.valueOf(volume)));
        }

        @Override // android.media.IMediaRoute2ProviderService
        public void requestCreateSession(long requestId, String packageName, String routeId, Bundle requestCreateSession) {
            if (!checkCallerIsSystem() || !checkRouteIdIsValid(routeId, "requestCreateSession")) {
                return;
            }
            MediaRoute2ProviderService.this.addRequestId(requestId);
            MediaRoute2ProviderService.this.mHandler.sendMessage(PooledLambda.obtainMessage(new QuintConsumer() { // from class: android.media.MediaRoute2ProviderService$MediaRoute2ProviderServiceStub$$ExternalSyntheticLambda0
                @Override // com.android.internal.util.function.QuintConsumer
                public final void accept(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
                    ((MediaRoute2ProviderService) obj).onCreateSession(((Long) obj2).longValue(), (String) obj3, (String) obj4, (Bundle) obj5);
                }
            }, MediaRoute2ProviderService.this, Long.valueOf(requestId), packageName, routeId, requestCreateSession));
        }

        @Override // android.media.IMediaRoute2ProviderService
        public void selectRoute(long requestId, String sessionId, String routeId) {
            if (!checkCallerIsSystem() || !checkSessionIdIsValid(sessionId, "selectRoute") || !checkRouteIdIsValid(routeId, "selectRoute")) {
                return;
            }
            MediaRoute2ProviderService.this.addRequestId(requestId);
            MediaRoute2ProviderService.this.mHandler.sendMessage(PooledLambda.obtainMessage(new QuadConsumer() { // from class: android.media.MediaRoute2ProviderService$MediaRoute2ProviderServiceStub$$ExternalSyntheticLambda6
                @Override // com.android.internal.util.function.QuadConsumer
                public final void accept(Object obj, Object obj2, Object obj3, Object obj4) {
                    ((MediaRoute2ProviderService) obj).onSelectRoute(((Long) obj2).longValue(), (String) obj3, (String) obj4);
                }
            }, MediaRoute2ProviderService.this, Long.valueOf(requestId), sessionId, routeId));
        }

        @Override // android.media.IMediaRoute2ProviderService
        public void deselectRoute(long requestId, String sessionId, String routeId) {
            if (!checkCallerIsSystem() || !checkSessionIdIsValid(sessionId, "deselectRoute") || !checkRouteIdIsValid(routeId, "deselectRoute")) {
                return;
            }
            MediaRoute2ProviderService.this.addRequestId(requestId);
            MediaRoute2ProviderService.this.mHandler.sendMessage(PooledLambda.obtainMessage(new QuadConsumer() { // from class: android.media.MediaRoute2ProviderService$MediaRoute2ProviderServiceStub$$ExternalSyntheticLambda2
                @Override // com.android.internal.util.function.QuadConsumer
                public final void accept(Object obj, Object obj2, Object obj3, Object obj4) {
                    ((MediaRoute2ProviderService) obj).onDeselectRoute(((Long) obj2).longValue(), (String) obj3, (String) obj4);
                }
            }, MediaRoute2ProviderService.this, Long.valueOf(requestId), sessionId, routeId));
        }

        @Override // android.media.IMediaRoute2ProviderService
        public void transferToRoute(long requestId, String sessionId, String routeId) {
            if (!checkCallerIsSystem() || !checkSessionIdIsValid(sessionId, "transferToRoute") || !checkRouteIdIsValid(routeId, "transferToRoute")) {
                return;
            }
            MediaRoute2ProviderService.this.addRequestId(requestId);
            MediaRoute2ProviderService.this.mHandler.sendMessage(PooledLambda.obtainMessage(new QuadConsumer() { // from class: android.media.MediaRoute2ProviderService$MediaRoute2ProviderServiceStub$$ExternalSyntheticLambda4
                @Override // com.android.internal.util.function.QuadConsumer
                public final void accept(Object obj, Object obj2, Object obj3, Object obj4) {
                    ((MediaRoute2ProviderService) obj).onTransferToRoute(((Long) obj2).longValue(), (String) obj3, (String) obj4);
                }
            }, MediaRoute2ProviderService.this, Long.valueOf(requestId), sessionId, routeId));
        }

        @Override // android.media.IMediaRoute2ProviderService
        public void setSessionVolume(long requestId, String sessionId, int volume) {
            if (!checkCallerIsSystem() || !checkSessionIdIsValid(sessionId, "setSessionVolume")) {
                return;
            }
            MediaRoute2ProviderService.this.addRequestId(requestId);
            MediaRoute2ProviderService.this.mHandler.sendMessage(PooledLambda.obtainMessage(new QuadConsumer() { // from class: android.media.MediaRoute2ProviderService$MediaRoute2ProviderServiceStub$$ExternalSyntheticLambda1
                @Override // com.android.internal.util.function.QuadConsumer
                public final void accept(Object obj, Object obj2, Object obj3, Object obj4) {
                    ((MediaRoute2ProviderService) obj).onSetSessionVolume(((Long) obj2).longValue(), (String) obj3, ((Integer) obj4).intValue());
                }
            }, MediaRoute2ProviderService.this, Long.valueOf(requestId), sessionId, Integer.valueOf(volume)));
        }

        @Override // android.media.IMediaRoute2ProviderService
        public void releaseSession(long requestId, String sessionId) {
            if (!checkCallerIsSystem() || !checkSessionIdIsValid(sessionId, "releaseSession")) {
                return;
            }
            MediaRoute2ProviderService.this.addRequestId(requestId);
            MediaRoute2ProviderService.this.mHandler.sendMessage(PooledLambda.obtainMessage(new TriConsumer() { // from class: android.media.MediaRoute2ProviderService$MediaRoute2ProviderServiceStub$$ExternalSyntheticLambda7
                @Override // com.android.internal.util.function.TriConsumer
                public final void accept(Object obj, Object obj2, Object obj3) {
                    ((MediaRoute2ProviderService) obj).onReleaseSession(((Long) obj2).longValue(), (String) obj3);
                }
            }, MediaRoute2ProviderService.this, Long.valueOf(requestId), sessionId));
        }
    }
}
