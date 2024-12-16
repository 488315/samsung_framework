package android.media.tv.interactive;

import android.graphics.Rect;
import android.media.PlaybackParams;
import android.media.tv.AdBuffer;
import android.media.tv.AdRequest;
import android.media.tv.AdResponse;
import android.media.tv.BroadcastInfoRequest;
import android.media.tv.BroadcastInfoResponse;
import android.media.tv.TvContentRating;
import android.media.tv.TvInputManager;
import android.media.tv.TvRecordingInfo;
import android.media.tv.TvTrackInfo;
import android.media.tv.interactive.ITvInteractiveAppClient;
import android.media.tv.interactive.ITvInteractiveAppManagerCallback;
import android.net.Uri;
import android.net.http.SslCertificate;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.util.Pools;
import android.util.SparseArray;
import android.view.InputChannel;
import android.view.InputEvent;
import android.view.InputEventSender;
import android.view.Surface;
import android.view.View;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
public final class TvInteractiveAppManager {
    public static final String ACTION_APP_LINK_COMMAND = "android.media.tv.interactive.action.APP_LINK_COMMAND";
    public static final String APP_LINK_KEY_BACK_URI = "back_uri";
    public static final String APP_LINK_KEY_CLASS_NAME = "class_name";
    public static final String APP_LINK_KEY_COMMAND_TYPE = "command_type";
    public static final String APP_LINK_KEY_PACKAGE_NAME = "package_name";
    public static final String APP_LINK_KEY_SERVICE_ID = "service_id";
    public static final int ERROR_BLOCKED = 5;
    public static final int ERROR_ENCRYPTED = 6;
    public static final int ERROR_NONE = 0;
    public static final int ERROR_NOT_SUPPORTED = 2;
    public static final int ERROR_RESOURCE_UNAVAILABLE = 4;
    public static final int ERROR_UNKNOWN = 1;
    public static final int ERROR_UNKNOWN_CHANNEL = 7;
    public static final int ERROR_WEAK_SIGNAL = 3;
    public static final String INTENT_KEY_BI_INTERACTIVE_APP_TYPE = "bi_interactive_app_type";
    public static final String INTENT_KEY_BI_INTERACTIVE_APP_URI = "bi_interactive_app_uri";
    public static final String INTENT_KEY_CHANNEL_URI = "channel_uri";
    public static final String INTENT_KEY_COMMAND_TYPE = "command_type";
    public static final String INTENT_KEY_INTERACTIVE_APP_SERVICE_ID = "interactive_app_id";
    public static final String INTENT_KEY_TV_INPUT_ID = "tv_input_id";
    public static final int INTERACTIVE_APP_STATE_ERROR = 3;
    public static final int INTERACTIVE_APP_STATE_RUNNING = 2;
    public static final int INTERACTIVE_APP_STATE_STOPPED = 1;
    public static final int SERVICE_STATE_ERROR = 4;
    public static final int SERVICE_STATE_PREPARING = 2;
    public static final int SERVICE_STATE_READY = 3;
    public static final int SERVICE_STATE_UNREALIZED = 1;
    private static final String TAG = "TvInteractiveAppManager";
    public static final int TELETEXT_APP_STATE_ERROR = 3;
    public static final int TELETEXT_APP_STATE_HIDE = 2;
    public static final int TELETEXT_APP_STATE_SHOW = 1;
    private int mNextSeq;
    private final ITvInteractiveAppManager mService;
    private final int mUserId;
    private final SparseArray<SessionCallbackRecord> mSessionCallbackRecordMap = new SparseArray<>();
    private final List<TvInteractiveAppCallbackRecord> mCallbackRecords = new ArrayList();
    private final Object mLock = new Object();
    private final ITvInteractiveAppClient mClient = new ITvInteractiveAppClient.Stub() { // from class: android.media.tv.interactive.TvInteractiveAppManager.1
        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onSessionCreated(String iAppServiceId, IBinder token, InputChannel channel, int seq) {
            synchronized (TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                SessionCallbackRecord record = (SessionCallbackRecord) TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(seq);
                if (record == null) {
                    Log.e(TvInteractiveAppManager.TAG, "Callback not found for " + token);
                    return;
                }
                Session session = null;
                if (token != null) {
                    session = new Session(token, channel, TvInteractiveAppManager.this.mService, TvInteractiveAppManager.this.mUserId, seq, TvInteractiveAppManager.this.mSessionCallbackRecordMap);
                } else {
                    TvInteractiveAppManager.this.mSessionCallbackRecordMap.delete(seq);
                }
                record.postSessionCreated(session);
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onSessionReleased(int seq) {
            synchronized (TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                SessionCallbackRecord record = (SessionCallbackRecord) TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(seq);
                TvInteractiveAppManager.this.mSessionCallbackRecordMap.delete(seq);
                if (record == null) {
                    Log.e(TvInteractiveAppManager.TAG, "Callback not found for seq:" + seq);
                } else {
                    record.mSession.releaseInternal();
                    record.postSessionReleased();
                }
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onLayoutSurface(int left, int top, int right, int bottom, int seq) {
            synchronized (TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                SessionCallbackRecord record = (SessionCallbackRecord) TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(seq);
                if (record == null) {
                    Log.e(TvInteractiveAppManager.TAG, "Callback not found for seq " + seq);
                } else {
                    record.postLayoutSurface(left, top, right, bottom);
                }
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onBroadcastInfoRequest(BroadcastInfoRequest request, int seq) {
            synchronized (TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                SessionCallbackRecord record = (SessionCallbackRecord) TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(seq);
                if (record == null) {
                    Log.e(TvInteractiveAppManager.TAG, "Callback not found for seq " + seq);
                } else {
                    record.postBroadcastInfoRequest(request);
                }
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRemoveBroadcastInfo(int requestId, int seq) {
            synchronized (TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                SessionCallbackRecord record = (SessionCallbackRecord) TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(seq);
                if (record == null) {
                    Log.e(TvInteractiveAppManager.TAG, "Callback not found for seq " + seq);
                } else {
                    record.postRemoveBroadcastInfo(requestId);
                }
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onCommandRequest(String cmdType, Bundle parameters, int seq) {
            synchronized (TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                SessionCallbackRecord record = (SessionCallbackRecord) TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(seq);
                if (record == null) {
                    Log.e(TvInteractiveAppManager.TAG, "Callback not found for seq " + seq);
                } else {
                    record.postCommandRequest(cmdType, parameters);
                }
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onTimeShiftCommandRequest(String cmdType, Bundle parameters, int seq) {
            synchronized (TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                SessionCallbackRecord record = (SessionCallbackRecord) TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(seq);
                if (record == null) {
                    Log.e(TvInteractiveAppManager.TAG, "Callback not found for seq " + seq);
                } else {
                    record.postTimeShiftCommandRequest(cmdType, parameters);
                }
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onSetVideoBounds(Rect rect, int seq) {
            synchronized (TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                SessionCallbackRecord record = (SessionCallbackRecord) TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(seq);
                if (record == null) {
                    Log.e(TvInteractiveAppManager.TAG, "Callback not found for seq " + seq);
                } else {
                    record.postSetVideoBounds(rect);
                }
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onAdRequest(AdRequest request, int seq) {
            synchronized (TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                SessionCallbackRecord record = (SessionCallbackRecord) TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(seq);
                if (record == null) {
                    Log.e(TvInteractiveAppManager.TAG, "Callback not found for seq " + seq);
                } else {
                    record.postAdRequest(request);
                }
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestCurrentVideoBounds(int seq) {
            synchronized (TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                SessionCallbackRecord record = (SessionCallbackRecord) TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(seq);
                if (record == null) {
                    Log.e(TvInteractiveAppManager.TAG, "Callback not found for seq " + seq);
                } else {
                    record.postRequestCurrentVideoBounds();
                }
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestCurrentChannelUri(int seq) {
            synchronized (TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                SessionCallbackRecord record = (SessionCallbackRecord) TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(seq);
                if (record == null) {
                    Log.e(TvInteractiveAppManager.TAG, "Callback not found for seq " + seq);
                } else {
                    record.postRequestCurrentChannelUri();
                }
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestCurrentChannelLcn(int seq) {
            synchronized (TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                SessionCallbackRecord record = (SessionCallbackRecord) TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(seq);
                if (record == null) {
                    Log.e(TvInteractiveAppManager.TAG, "Callback not found for seq " + seq);
                } else {
                    record.postRequestCurrentChannelLcn();
                }
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestStreamVolume(int seq) {
            synchronized (TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                SessionCallbackRecord record = (SessionCallbackRecord) TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(seq);
                if (record == null) {
                    Log.e(TvInteractiveAppManager.TAG, "Callback not found for seq " + seq);
                } else {
                    record.postRequestStreamVolume();
                }
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestTrackInfoList(int seq) {
            synchronized (TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                SessionCallbackRecord record = (SessionCallbackRecord) TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(seq);
                if (record == null) {
                    Log.e(TvInteractiveAppManager.TAG, "Callback not found for seq " + seq);
                } else {
                    record.postRequestTrackInfoList();
                }
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestSelectedTrackInfo(int seq) {
            synchronized (TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                SessionCallbackRecord record = (SessionCallbackRecord) TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(seq);
                if (record == null) {
                    Log.e(TvInteractiveAppManager.TAG, "Callback not found for seq " + seq);
                } else {
                    record.postRequestSelectedTrackInfo();
                }
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestCurrentTvInputId(int seq) {
            synchronized (TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                SessionCallbackRecord record = (SessionCallbackRecord) TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(seq);
                if (record == null) {
                    Log.e(TvInteractiveAppManager.TAG, "Callback not found for seq " + seq);
                } else {
                    record.postRequestCurrentTvInputId();
                }
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestTimeShiftMode(int seq) {
            synchronized (TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                SessionCallbackRecord record = (SessionCallbackRecord) TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(seq);
                if (record == null) {
                    Log.e(TvInteractiveAppManager.TAG, "Callback not found for seq " + seq);
                } else {
                    record.postRequestTimeShiftMode();
                }
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestAvailableSpeeds(int seq) {
            synchronized (TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                SessionCallbackRecord record = (SessionCallbackRecord) TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(seq);
                if (record == null) {
                    Log.e(TvInteractiveAppManager.TAG, "Callback not found for seq " + seq);
                } else {
                    record.postRequestAvailableSpeeds();
                }
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestStartRecording(String requestId, Uri programUri, int seq) {
            synchronized (TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                SessionCallbackRecord record = (SessionCallbackRecord) TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(seq);
                if (record == null) {
                    Log.e(TvInteractiveAppManager.TAG, "Callback not found for seq " + seq);
                } else {
                    record.postRequestStartRecording(requestId, programUri);
                }
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestStopRecording(String recordingId, int seq) {
            synchronized (TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                SessionCallbackRecord record = (SessionCallbackRecord) TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(seq);
                if (record == null) {
                    Log.e(TvInteractiveAppManager.TAG, "Callback not found for seq " + seq);
                } else {
                    record.postRequestStopRecording(recordingId);
                }
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestScheduleRecording(String requestId, String inputId, Uri channelUri, Uri programUri, Bundle params, int seq) {
            synchronized (TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                SessionCallbackRecord record = (SessionCallbackRecord) TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(seq);
                if (record == null) {
                    Log.e(TvInteractiveAppManager.TAG, "Callback not found for seq " + seq);
                } else {
                    record.postRequestScheduleRecording(requestId, inputId, channelUri, programUri, params);
                }
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestScheduleRecording2(String requestId, String inputId, Uri channelUri, long startTime, long duration, int repeatDays, Bundle params, int seq) {
            synchronized (TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                SessionCallbackRecord record = (SessionCallbackRecord) TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(seq);
                if (record == null) {
                    Log.e(TvInteractiveAppManager.TAG, "Callback not found for seq " + seq);
                } else {
                    record.postRequestScheduleRecording(requestId, inputId, channelUri, startTime, duration, repeatDays, params);
                }
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onSetTvRecordingInfo(String recordingId, TvRecordingInfo recordingInfo, int seq) {
            synchronized (TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                SessionCallbackRecord record = (SessionCallbackRecord) TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(seq);
                if (record == null) {
                    Log.e(TvInteractiveAppManager.TAG, "Callback not found for seq " + seq);
                } else {
                    record.postSetTvRecordingInfo(recordingId, recordingInfo);
                }
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestTvRecordingInfo(String recordingId, int seq) {
            synchronized (TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                SessionCallbackRecord record = (SessionCallbackRecord) TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(seq);
                if (record == null) {
                    Log.e(TvInteractiveAppManager.TAG, "Callback not found for seq " + seq);
                } else {
                    record.postRequestTvRecordingInfo(recordingId);
                }
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestTvRecordingInfoList(int type, int seq) {
            synchronized (TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                SessionCallbackRecord record = (SessionCallbackRecord) TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(seq);
                if (record == null) {
                    Log.e(TvInteractiveAppManager.TAG, "Callback not found for seq " + seq);
                } else {
                    record.postRequestTvRecordingInfoList(type);
                }
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestSigning(String id, String algorithm, String alias, byte[] data, int seq) {
            synchronized (TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                SessionCallbackRecord record = (SessionCallbackRecord) TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(seq);
                if (record == null) {
                    Log.e(TvInteractiveAppManager.TAG, "Callback not found for seq " + seq);
                } else {
                    record.postRequestSigning(id, algorithm, alias, data);
                }
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestSigning2(String id, String algorithm, String host, int port, byte[] data, int seq) {
            synchronized (TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                SessionCallbackRecord record = (SessionCallbackRecord) TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(seq);
                if (record == null) {
                    Log.e(TvInteractiveAppManager.TAG, "Callback not found for seq " + seq);
                } else {
                    record.postRequestSigning(id, algorithm, host, port, data);
                }
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestCertificate(String host, int port, int seq) {
            synchronized (TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                SessionCallbackRecord record = (SessionCallbackRecord) TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(seq);
                if (record == null) {
                    Log.e(TvInteractiveAppManager.TAG, "Callback not found for seq " + seq);
                } else {
                    record.postRequestCertificate(host, port);
                }
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onSessionStateChanged(int state, int err, int seq) {
            synchronized (TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                SessionCallbackRecord record = (SessionCallbackRecord) TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(seq);
                if (record == null) {
                    Log.e(TvInteractiveAppManager.TAG, "Callback not found for seq " + seq);
                } else {
                    record.postSessionStateChanged(state, err);
                }
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onBiInteractiveAppCreated(Uri biIAppUri, String biIAppId, int seq) {
            synchronized (TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                SessionCallbackRecord record = (SessionCallbackRecord) TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(seq);
                if (record == null) {
                    Log.e(TvInteractiveAppManager.TAG, "Callback not found for seq " + seq);
                } else {
                    record.postBiInteractiveAppCreated(biIAppUri, biIAppId);
                }
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onTeletextAppStateChanged(int state, int seq) {
            synchronized (TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                SessionCallbackRecord record = (SessionCallbackRecord) TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(seq);
                if (record == null) {
                    Log.e(TvInteractiveAppManager.TAG, "Callback not found for seq " + seq);
                } else {
                    record.postTeletextAppStateChanged(state);
                }
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onAdBufferReady(AdBuffer buffer, int seq) {
            synchronized (TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                SessionCallbackRecord record = (SessionCallbackRecord) TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(seq);
                if (record == null) {
                    Log.e(TvInteractiveAppManager.TAG, "Callback not found for seq " + seq);
                } else {
                    record.postAdBufferReady(buffer);
                }
            }
        }
    };

    @Retention(RetentionPolicy.SOURCE)
    public @interface ErrorCode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface InteractiveAppState {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ServiceState {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface TeletextAppState {
    }

    public TvInteractiveAppManager(ITvInteractiveAppManager service, int userId) {
        this.mService = service;
        this.mUserId = userId;
        ITvInteractiveAppManagerCallback managerCallback = new ITvInteractiveAppManagerCallback.Stub() { // from class: android.media.tv.interactive.TvInteractiveAppManager.2
            @Override // android.media.tv.interactive.ITvInteractiveAppManagerCallback
            public void onInteractiveAppServiceAdded(String iAppServiceId) {
                synchronized (TvInteractiveAppManager.this.mLock) {
                    for (TvInteractiveAppCallbackRecord record : TvInteractiveAppManager.this.mCallbackRecords) {
                        record.postInteractiveAppServiceAdded(iAppServiceId);
                    }
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManagerCallback
            public void onInteractiveAppServiceRemoved(String iAppServiceId) {
                synchronized (TvInteractiveAppManager.this.mLock) {
                    for (TvInteractiveAppCallbackRecord record : TvInteractiveAppManager.this.mCallbackRecords) {
                        record.postInteractiveAppServiceRemoved(iAppServiceId);
                    }
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManagerCallback
            public void onInteractiveAppServiceUpdated(String iAppServiceId) {
                synchronized (TvInteractiveAppManager.this.mLock) {
                    for (TvInteractiveAppCallbackRecord record : TvInteractiveAppManager.this.mCallbackRecords) {
                        record.postInteractiveAppServiceUpdated(iAppServiceId);
                    }
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManagerCallback
            public void onTvInteractiveAppServiceInfoUpdated(TvInteractiveAppServiceInfo iAppInfo) {
                synchronized (TvInteractiveAppManager.this.mLock) {
                    for (TvInteractiveAppCallbackRecord record : TvInteractiveAppManager.this.mCallbackRecords) {
                        record.postTvInteractiveAppServiceInfoUpdated(iAppInfo);
                    }
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManagerCallback
            public void onStateChanged(String iAppServiceId, int type, int state, int err) {
                synchronized (TvInteractiveAppManager.this.mLock) {
                    for (TvInteractiveAppCallbackRecord record : TvInteractiveAppManager.this.mCallbackRecords) {
                        record.postStateChanged(iAppServiceId, type, state, err);
                    }
                }
            }
        };
        try {
            if (this.mService != null) {
                this.mService.registerCallback(managerCallback, this.mUserId);
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static abstract class TvInteractiveAppCallback {
        public void onInteractiveAppServiceAdded(String iAppServiceId) {
        }

        public void onInteractiveAppServiceRemoved(String iAppServiceId) {
        }

        public void onInteractiveAppServiceUpdated(String iAppServiceId) {
        }

        public void onTvInteractiveAppServiceInfoUpdated(TvInteractiveAppServiceInfo iAppInfo) {
        }

        public void onTvInteractiveAppServiceStateChanged(String iAppServiceId, int type, int state, int err) {
        }
    }

    private static final class TvInteractiveAppCallbackRecord {
        private final TvInteractiveAppCallback mCallback;
        private final Executor mExecutor;

        TvInteractiveAppCallbackRecord(TvInteractiveAppCallback callback, Executor executor) {
            this.mCallback = callback;
            this.mExecutor = executor;
        }

        public TvInteractiveAppCallback getCallback() {
            return this.mCallback;
        }

        public void postInteractiveAppServiceAdded(final String iAppServiceId) {
            this.mExecutor.execute(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.TvInteractiveAppCallbackRecord.1
                @Override // java.lang.Runnable
                public void run() {
                    TvInteractiveAppCallbackRecord.this.mCallback.onInteractiveAppServiceAdded(iAppServiceId);
                }
            });
        }

        public void postInteractiveAppServiceRemoved(final String iAppServiceId) {
            this.mExecutor.execute(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.TvInteractiveAppCallbackRecord.2
                @Override // java.lang.Runnable
                public void run() {
                    TvInteractiveAppCallbackRecord.this.mCallback.onInteractiveAppServiceRemoved(iAppServiceId);
                }
            });
        }

        public void postInteractiveAppServiceUpdated(final String iAppServiceId) {
            this.mExecutor.execute(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.TvInteractiveAppCallbackRecord.3
                @Override // java.lang.Runnable
                public void run() {
                    TvInteractiveAppCallbackRecord.this.mCallback.onInteractiveAppServiceUpdated(iAppServiceId);
                }
            });
        }

        public void postTvInteractiveAppServiceInfoUpdated(final TvInteractiveAppServiceInfo iAppInfo) {
            this.mExecutor.execute(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.TvInteractiveAppCallbackRecord.4
                @Override // java.lang.Runnable
                public void run() {
                    TvInteractiveAppCallbackRecord.this.mCallback.onTvInteractiveAppServiceInfoUpdated(iAppInfo);
                }
            });
        }

        public void postStateChanged(final String iAppServiceId, final int type, final int state, final int err) {
            this.mExecutor.execute(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.TvInteractiveAppCallbackRecord.5
                @Override // java.lang.Runnable
                public void run() {
                    TvInteractiveAppCallbackRecord.this.mCallback.onTvInteractiveAppServiceStateChanged(iAppServiceId, type, state, err);
                }
            });
        }
    }

    public void createSession(String iAppServiceId, int type, SessionCallback callback, Handler handler) {
        createSessionInternal(iAppServiceId, type, callback, handler);
    }

    private void createSessionInternal(String iAppServiceId, int type, SessionCallback callback, Handler handler) {
        Preconditions.checkNotNull(iAppServiceId);
        Preconditions.checkNotNull(callback);
        Preconditions.checkNotNull(handler);
        SessionCallbackRecord record = new SessionCallbackRecord(callback, handler);
        synchronized (this.mSessionCallbackRecordMap) {
            int seq = this.mNextSeq;
            this.mNextSeq = seq + 1;
            this.mSessionCallbackRecordMap.put(seq, record);
            try {
                this.mService.createSession(this.mClient, iAppServiceId, type, seq, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public List<TvInteractiveAppServiceInfo> getTvInteractiveAppServiceList() {
        try {
            return this.mService.getTvInteractiveAppServiceList(this.mUserId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<AppLinkInfo> getAppLinkInfoList() {
        try {
            return this.mService.getAppLinkInfoList(this.mUserId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void registerAppLinkInfo(String tvIAppServiceId, AppLinkInfo appLinkInfo) {
        try {
            this.mService.registerAppLinkInfo(tvIAppServiceId, appLinkInfo, this.mUserId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void unregisterAppLinkInfo(String tvIAppServiceId, AppLinkInfo appLinkInfo) {
        try {
            this.mService.unregisterAppLinkInfo(tvIAppServiceId, appLinkInfo, this.mUserId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void sendAppLinkCommand(String tvIAppServiceId, Bundle command) {
        try {
            this.mService.sendAppLinkCommand(tvIAppServiceId, command, this.mUserId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void registerCallback(Executor executor, TvInteractiveAppCallback callback) {
        Preconditions.checkNotNull(callback);
        Preconditions.checkNotNull(executor);
        synchronized (this.mLock) {
            this.mCallbackRecords.add(new TvInteractiveAppCallbackRecord(callback, executor));
        }
    }

    public void unregisterCallback(TvInteractiveAppCallback callback) {
        Preconditions.checkNotNull(callback);
        synchronized (this.mLock) {
            Iterator<TvInteractiveAppCallbackRecord> it = this.mCallbackRecords.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                TvInteractiveAppCallbackRecord record = it.next();
                if (record.getCallback() == callback) {
                    it.remove();
                    break;
                }
            }
        }
    }

    public static final class Session {
        static final int DISPATCH_HANDLED = 1;
        static final int DISPATCH_IN_PROGRESS = -1;
        static final int DISPATCH_NOT_HANDLED = 0;
        private static final long INPUT_SESSION_NOT_RESPONDING_TIMEOUT = 2500;
        private final InputEventHandler mHandler;
        private InputChannel mInputChannel;
        private TvInputManager.Session mInputSession;
        private final Pools.Pool<PendingEvent> mPendingEventPool;
        private final SparseArray<PendingEvent> mPendingEvents;
        private TvInputEventSender mSender;
        private final int mSeq;
        private final ITvInteractiveAppManager mService;
        private final SparseArray<SessionCallbackRecord> mSessionCallbackRecordMap;
        private IBinder mToken;
        private final int mUserId;

        public interface FinishedInputEventCallback {
            void onFinishedInputEvent(Object obj, boolean z);
        }

        private Session(IBinder token, InputChannel channel, ITvInteractiveAppManager service, int userId, int seq, SparseArray<SessionCallbackRecord> sessionCallbackRecordMap) {
            this.mHandler = new InputEventHandler(Looper.getMainLooper());
            this.mPendingEventPool = new Pools.SimplePool(20);
            this.mPendingEvents = new SparseArray<>(20);
            this.mToken = token;
            this.mInputChannel = channel;
            this.mService = service;
            this.mUserId = userId;
            this.mSeq = seq;
            this.mSessionCallbackRecordMap = sessionCallbackRecordMap;
        }

        public TvInputManager.Session getInputSession() {
            return this.mInputSession;
        }

        public void setInputSession(TvInputManager.Session inputSession) {
            this.mInputSession = inputSession;
        }

        void startInteractiveApp() {
            if (this.mToken == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.startInteractiveApp(this.mToken, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void stopInteractiveApp() {
            if (this.mToken == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.stopInteractiveApp(this.mToken, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void resetInteractiveApp() {
            if (this.mToken == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.resetInteractiveApp(this.mToken, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void createBiInteractiveApp(Uri biIAppUri, Bundle params) {
            if (this.mToken == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.createBiInteractiveApp(this.mToken, biIAppUri, params, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void destroyBiInteractiveApp(String biIAppId) {
            if (this.mToken == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.destroyBiInteractiveApp(this.mToken, biIAppId, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void setTeletextAppEnabled(boolean enable) {
            if (this.mToken == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.setTeletextAppEnabled(this.mToken, enable, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void sendCurrentVideoBounds(Rect bounds) {
            if (this.mToken == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.sendCurrentVideoBounds(this.mToken, bounds, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void sendCurrentChannelUri(Uri channelUri) {
            if (this.mToken == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.sendCurrentChannelUri(this.mToken, channelUri, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void sendCurrentChannelLcn(int lcn) {
            if (this.mToken == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.sendCurrentChannelLcn(this.mToken, lcn, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void sendStreamVolume(float volume) {
            if (this.mToken == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.sendStreamVolume(this.mToken, volume, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void sendTrackInfoList(List<TvTrackInfo> tracks) {
            if (this.mToken == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.sendTrackInfoList(this.mToken, tracks, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void sendSelectedTrackInfo(List<TvTrackInfo> tracks) {
            if (this.mToken == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.sendSelectedTrackInfo(this.mToken, tracks, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void sendCurrentTvInputId(String inputId) {
            if (this.mToken == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.sendCurrentTvInputId(this.mToken, inputId, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void sendTimeShiftMode(int mode) {
            if (this.mToken == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.sendTimeShiftMode(this.mToken, mode, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void sendAvailableSpeeds(float[] speeds) {
            if (this.mToken == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.sendAvailableSpeeds(this.mToken, speeds, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void sendTvRecordingInfo(TvRecordingInfo recordingInfo) {
            if (this.mToken == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.sendTvRecordingInfo(this.mToken, recordingInfo, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void sendTvRecordingInfoList(List<TvRecordingInfo> recordingInfoList) {
            if (this.mToken == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.sendTvRecordingInfoList(this.mToken, recordingInfoList, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void notifyRecordingStarted(String recordingId, String requestId) {
            if (this.mToken == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.notifyRecordingStarted(this.mToken, recordingId, requestId, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void notifyRecordingStopped(String recordingId) {
            if (this.mToken == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.notifyRecordingStopped(this.mToken, recordingId, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void sendSigningResult(String signingId, byte[] result) {
            if (this.mToken == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.sendSigningResult(this.mToken, signingId, result, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void sendCertificate(String host, int port, SslCertificate cert) {
            if (this.mToken == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.sendCertificate(this.mToken, host, port, SslCertificate.saveState(cert), this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void notifyError(String errMsg, Bundle params) {
            if (this.mToken == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.notifyError(this.mToken, errMsg, params, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void notifyTimeShiftPlaybackParams(PlaybackParams params) {
            if (this.mToken == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.notifyTimeShiftPlaybackParams(this.mToken, params, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void notifyTimeShiftStatusChanged(String inputId, int status) {
            if (this.mToken == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.notifyTimeShiftStatusChanged(this.mToken, inputId, status, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void notifyTimeShiftStartPositionChanged(String inputId, long timeMs) {
            if (this.mToken == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.notifyTimeShiftStartPositionChanged(this.mToken, inputId, timeMs, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void notifyTimeShiftCurrentPositionChanged(String inputId, long timeMs) {
            if (this.mToken == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.notifyTimeShiftCurrentPositionChanged(this.mToken, inputId, timeMs, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void notifyRecordingConnectionFailed(String recordingId, String inputId) {
            if (this.mToken == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.notifyRecordingConnectionFailed(this.mToken, recordingId, inputId, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void notifyRecordingDisconnected(String recordingId, String inputId) {
            if (this.mToken == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.notifyRecordingDisconnected(this.mToken, recordingId, inputId, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void notifyRecordingTuned(String recordingId, Uri channelUri) {
            if (this.mToken == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.notifyRecordingTuned(this.mToken, recordingId, channelUri, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void notifyRecordingError(String recordingId, int err) {
            if (this.mToken == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.notifyRecordingError(this.mToken, recordingId, err, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void notifyRecordingScheduled(String recordingId, String requestId) {
            if (this.mToken == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.notifyRecordingScheduled(this.mToken, recordingId, requestId, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void setSurface(Surface surface) {
            if (this.mToken == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.setSurface(this.mToken, surface, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void createMediaView(View view, Rect frame) {
            Preconditions.checkNotNull(view);
            Preconditions.checkNotNull(frame);
            if (view.getWindowToken() == null) {
                throw new IllegalStateException("view must be attached to a window");
            }
            if (this.mToken == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.createMediaView(this.mToken, view.getWindowToken(), frame, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void relayoutMediaView(Rect frame) {
            Preconditions.checkNotNull(frame);
            if (this.mToken == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.relayoutMediaView(this.mToken, frame, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void removeMediaView() {
            if (this.mToken == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.removeMediaView(this.mToken, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void dispatchSurfaceChanged(int format, int width, int height) {
            if (this.mToken == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.dispatchSurfaceChanged(this.mToken, format, width, height, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public int dispatchInputEvent(InputEvent event, Object token, FinishedInputEventCallback callback, Handler handler) {
            Preconditions.checkNotNull(event);
            Preconditions.checkNotNull(callback);
            Preconditions.checkNotNull(handler);
            synchronized (this.mHandler) {
                if (this.mInputChannel == null) {
                    return 0;
                }
                PendingEvent p = obtainPendingEventLocked(event, token, callback, handler);
                if (Looper.myLooper() == Looper.getMainLooper()) {
                    return sendInputEventOnMainLooperLocked(p);
                }
                Message msg = this.mHandler.obtainMessage(1, p);
                msg.setAsynchronous(true);
                this.mHandler.sendMessage(msg);
                return -1;
            }
        }

        public void notifyBroadcastInfoResponse(BroadcastInfoResponse response) {
            if (this.mToken == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.notifyBroadcastInfoResponse(this.mToken, response, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void notifyAdResponse(AdResponse response) {
            if (this.mToken == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.notifyAdResponse(this.mToken, response, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void notifyAdBufferConsumed(AdBuffer buffer) {
            if (this.mToken == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                try {
                    this.mService.notifyAdBufferConsumed(this.mToken, buffer, this.mUserId);
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            } finally {
                if (buffer != null) {
                    buffer.getSharedMemory().close();
                }
            }
        }

        public void release() {
            if (this.mToken == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.releaseSession(this.mToken, this.mUserId);
                releaseInternal();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void notifyTuned(Uri channelUri) {
            if (this.mToken == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.notifyTuned(this.mToken, channelUri, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void notifyTrackSelected(int type, String trackId) {
            if (this.mToken == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.notifyTrackSelected(this.mToken, type, trackId, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void notifyTracksChanged(List<TvTrackInfo> tracks) {
            if (this.mToken == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.notifyTracksChanged(this.mToken, tracks, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void notifyVideoAvailable() {
            if (this.mToken == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.notifyVideoAvailable(this.mToken, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void notifyVideoUnavailable(int reason) {
            if (this.mToken == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.notifyVideoUnavailable(this.mToken, reason, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void notifyVideoFreezeUpdated(boolean isFrozen) {
            if (this.mToken == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.notifyVideoFreezeUpdated(this.mToken, isFrozen, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void notifyContentAllowed() {
            if (this.mToken == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.notifyContentAllowed(this.mToken, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void notifyContentBlocked(TvContentRating rating) {
            if (this.mToken == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.notifyContentBlocked(this.mToken, rating.flattenToString(), this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void notifySignalStrength(int strength) {
            if (this.mToken == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.notifySignalStrength(this.mToken, strength, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void notifyTvMessage(int type, Bundle data) {
            if (this.mToken == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.notifyTvMessage(this.mToken, type, data, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        private void flushPendingEventsLocked() {
            this.mHandler.removeMessages(3);
            int count = this.mPendingEvents.size();
            for (int i = 0; i < count; i++) {
                int seq = this.mPendingEvents.keyAt(i);
                Message msg = this.mHandler.obtainMessage(3, seq, 0);
                msg.setAsynchronous(true);
                msg.sendToTarget();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void releaseInternal() {
            this.mToken = null;
            synchronized (this.mHandler) {
                if (this.mInputChannel != null) {
                    if (this.mSender != null) {
                        flushPendingEventsLocked();
                        this.mSender.dispose();
                        this.mSender = null;
                    }
                    this.mInputChannel.dispose();
                    this.mInputChannel = null;
                }
            }
            synchronized (this.mSessionCallbackRecordMap) {
                this.mSessionCallbackRecordMap.delete(this.mSeq);
            }
        }

        private PendingEvent obtainPendingEventLocked(InputEvent event, Object token, FinishedInputEventCallback callback, Handler handler) {
            PendingEvent p = this.mPendingEventPool.acquire();
            if (p == null) {
                p = new PendingEvent();
            }
            p.mEvent = event;
            p.mEventToken = token;
            p.mCallback = callback;
            p.mEventHandler = handler;
            return p;
        }

        void invokeFinishedInputEventCallback(PendingEvent p, boolean handled) {
            p.mHandled = handled;
            if (p.mEventHandler.getLooper().isCurrentThread()) {
                p.run();
                return;
            }
            Message msg = Message.obtain(p.mEventHandler, p);
            msg.setAsynchronous(true);
            msg.sendToTarget();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void sendInputEventAndReportResultOnMainLooper(PendingEvent p) {
            synchronized (this.mHandler) {
                int result = sendInputEventOnMainLooperLocked(p);
                if (result == -1) {
                    return;
                }
                invokeFinishedInputEventCallback(p, false);
            }
        }

        private int sendInputEventOnMainLooperLocked(PendingEvent p) {
            if (this.mInputChannel != null) {
                if (this.mSender == null) {
                    this.mSender = new TvInputEventSender(this.mInputChannel, this.mHandler.getLooper());
                }
                InputEvent event = p.mEvent;
                int seq = event.getSequenceNumber();
                if (this.mSender.sendInputEvent(seq, event)) {
                    this.mPendingEvents.put(seq, p);
                    Message msg = this.mHandler.obtainMessage(2, p);
                    msg.setAsynchronous(true);
                    this.mHandler.sendMessageDelayed(msg, INPUT_SESSION_NOT_RESPONDING_TIMEOUT);
                    return -1;
                }
                Log.w(TvInteractiveAppManager.TAG, "Unable to send input event to session: " + this.mToken + " dropping:" + event);
                return 0;
            }
            return 0;
        }

        void finishedInputEvent(int seq, boolean handled, boolean timeout) {
            synchronized (this.mHandler) {
                int index = this.mPendingEvents.indexOfKey(seq);
                if (index < 0) {
                    return;
                }
                PendingEvent p = this.mPendingEvents.valueAt(index);
                this.mPendingEvents.removeAt(index);
                if (timeout) {
                    Log.w(TvInteractiveAppManager.TAG, "Timeout waiting for session to handle input event after 2500 ms: " + this.mToken);
                } else {
                    this.mHandler.removeMessages(2, p);
                }
                invokeFinishedInputEventCallback(p, handled);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void recyclePendingEventLocked(PendingEvent p) {
            p.recycle();
            this.mPendingEventPool.release(p);
        }

        private final class InputEventHandler extends Handler {
            public static final int MSG_FLUSH_INPUT_EVENT = 3;
            public static final int MSG_SEND_INPUT_EVENT = 1;
            public static final int MSG_TIMEOUT_INPUT_EVENT = 2;

            InputEventHandler(Looper looper) {
                super(looper, null, true);
            }

            @Override // android.os.Handler
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        Session.this.sendInputEventAndReportResultOnMainLooper((PendingEvent) msg.obj);
                        break;
                    case 2:
                        Session.this.finishedInputEvent(msg.arg1, false, true);
                        break;
                    case 3:
                        Session.this.finishedInputEvent(msg.arg1, false, false);
                        break;
                }
            }
        }

        private final class TvInputEventSender extends InputEventSender {
            TvInputEventSender(InputChannel inputChannel, Looper looper) {
                super(inputChannel, looper);
            }

            @Override // android.view.InputEventSender
            public void onInputEventFinished(int seq, boolean handled) {
                Session.this.finishedInputEvent(seq, handled, false);
            }
        }

        private final class PendingEvent implements Runnable {
            public FinishedInputEventCallback mCallback;
            public InputEvent mEvent;
            public Handler mEventHandler;
            public Object mEventToken;
            public boolean mHandled;

            private PendingEvent() {
            }

            public void recycle() {
                this.mEvent = null;
                this.mEventToken = null;
                this.mCallback = null;
                this.mEventHandler = null;
                this.mHandled = false;
            }

            @Override // java.lang.Runnable
            public void run() {
                this.mCallback.onFinishedInputEvent(this.mEventToken, this.mHandled);
                synchronized (this.mEventHandler) {
                    Session.this.recyclePendingEventLocked(this);
                }
            }
        }
    }

    private static final class SessionCallbackRecord {
        private final Handler mHandler;
        private Session mSession;
        private final SessionCallback mSessionCallback;

        SessionCallbackRecord(SessionCallback sessionCallback, Handler handler) {
            this.mSessionCallback = sessionCallback;
            this.mHandler = handler;
        }

        void postSessionCreated(final Session session) {
            this.mSession = session;
            this.mHandler.post(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.1
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onSessionCreated(session);
                }
            });
        }

        void postSessionReleased() {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.2
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onSessionReleased(SessionCallbackRecord.this.mSession);
                }
            });
        }

        void postLayoutSurface(final int left, final int top, final int right, final int bottom) {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.3
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onLayoutSurface(SessionCallbackRecord.this.mSession, left, top, right, bottom);
                }
            });
        }

        void postBroadcastInfoRequest(final BroadcastInfoRequest request) {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.4
                @Override // java.lang.Runnable
                public void run() {
                    if (SessionCallbackRecord.this.mSession.getInputSession() != null) {
                        SessionCallbackRecord.this.mSession.getInputSession().requestBroadcastInfo(request);
                    }
                }
            });
        }

        void postRemoveBroadcastInfo(final int requestId) {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.5
                @Override // java.lang.Runnable
                public void run() {
                    if (SessionCallbackRecord.this.mSession.getInputSession() != null) {
                        SessionCallbackRecord.this.mSession.getInputSession().removeBroadcastInfo(requestId);
                    }
                }
            });
        }

        void postCommandRequest(final String cmdType, final Bundle parameters) {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.6
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onCommandRequest(SessionCallbackRecord.this.mSession, cmdType, parameters);
                }
            });
        }

        void postTimeShiftCommandRequest(final String cmdType, final Bundle parameters) {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.7
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onTimeShiftCommandRequest(SessionCallbackRecord.this.mSession, cmdType, parameters);
                }
            });
        }

        void postSetVideoBounds(final Rect rect) {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.8
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onSetVideoBounds(SessionCallbackRecord.this.mSession, rect);
                }
            });
        }

        void postRequestCurrentVideoBounds() {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.9
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onRequestCurrentVideoBounds(SessionCallbackRecord.this.mSession);
                }
            });
        }

        void postRequestCurrentChannelUri() {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.10
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onRequestCurrentChannelUri(SessionCallbackRecord.this.mSession);
                }
            });
        }

        void postRequestCurrentChannelLcn() {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.11
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onRequestCurrentChannelLcn(SessionCallbackRecord.this.mSession);
                }
            });
        }

        void postRequestStreamVolume() {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.12
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onRequestStreamVolume(SessionCallbackRecord.this.mSession);
                }
            });
        }

        void postRequestTrackInfoList() {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.13
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onRequestTrackInfoList(SessionCallbackRecord.this.mSession);
                }
            });
        }

        void postRequestSelectedTrackInfo() {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.14
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onRequestSelectedTrackInfo(SessionCallbackRecord.this.mSession);
                }
            });
        }

        void postRequestCurrentTvInputId() {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.15
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onRequestCurrentTvInputId(SessionCallbackRecord.this.mSession);
                }
            });
        }

        void postRequestTimeShiftMode() {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.16
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onRequestTimeShiftMode(SessionCallbackRecord.this.mSession);
                }
            });
        }

        void postRequestAvailableSpeeds() {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.17
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onRequestAvailableSpeeds(SessionCallbackRecord.this.mSession);
                }
            });
        }

        void postRequestStartRecording(final String requestId, final Uri programUri) {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.18
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onRequestStartRecording(SessionCallbackRecord.this.mSession, requestId, programUri);
                }
            });
        }

        void postRequestStopRecording(final String recordingId) {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.19
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onRequestStopRecording(SessionCallbackRecord.this.mSession, recordingId);
                }
            });
        }

        void postRequestScheduleRecording(final String requestId, final String inputId, final Uri channelUri, final Uri programUri, final Bundle params) {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.20
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onRequestScheduleRecording(SessionCallbackRecord.this.mSession, requestId, inputId, channelUri, programUri, params);
                }
            });
        }

        void postRequestScheduleRecording(final String requestId, final String inputId, final Uri channelUri, final long startTime, final long duration, final int repeatDays, final Bundle params) {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.21
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onRequestScheduleRecording(SessionCallbackRecord.this.mSession, requestId, inputId, channelUri, startTime, duration, repeatDays, params);
                }
            });
        }

        void postRequestSigning(final String id, final String algorithm, final String alias, final byte[] data) {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.22
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onRequestSigning(SessionCallbackRecord.this.mSession, id, algorithm, alias, data);
                }
            });
        }

        void postRequestSigning(final String id, final String algorithm, final String host, final int port, final byte[] data) {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.23
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onRequestSigning(SessionCallbackRecord.this.mSession, id, algorithm, host, port, data);
                }
            });
        }

        void postRequestCertificate(final String host, final int port) {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.24
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onRequestCertificate(SessionCallbackRecord.this.mSession, host, port);
                }
            });
        }

        void postRequestTvRecordingInfo(final String recordingId) {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.25
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onRequestTvRecordingInfo(SessionCallbackRecord.this.mSession, recordingId);
                }
            });
        }

        void postRequestTvRecordingInfoList(final int type) {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.26
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onRequestTvRecordingInfoList(SessionCallbackRecord.this.mSession, type);
                }
            });
        }

        void postSetTvRecordingInfo(final String recordingId, final TvRecordingInfo recordingInfo) {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.27
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onSetTvRecordingInfo(SessionCallbackRecord.this.mSession, recordingId, recordingInfo);
                }
            });
        }

        void postAdRequest(final AdRequest request) {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.28
                @Override // java.lang.Runnable
                public void run() {
                    if (SessionCallbackRecord.this.mSession.getInputSession() != null) {
                        SessionCallbackRecord.this.mSession.getInputSession().requestAd(request);
                    }
                }
            });
        }

        void postSessionStateChanged(final int state, final int err) {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.29
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onSessionStateChanged(SessionCallbackRecord.this.mSession, state, err);
                }
            });
        }

        void postBiInteractiveAppCreated(final Uri biIAppUri, final String biIAppId) {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.30
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onBiInteractiveAppCreated(SessionCallbackRecord.this.mSession, biIAppUri, biIAppId);
                }
            });
        }

        void postTeletextAppStateChanged(final int state) {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.31
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onTeletextAppStateChanged(SessionCallbackRecord.this.mSession, state);
                }
            });
        }

        void postAdBufferReady(final AdBuffer buffer) {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.32
                @Override // java.lang.Runnable
                public void run() {
                    if (SessionCallbackRecord.this.mSession.getInputSession() != null) {
                        SessionCallbackRecord.this.mSession.getInputSession().notifyAdBufferReady(buffer);
                    }
                }
            });
        }
    }

    public static abstract class SessionCallback {
        public void onSessionCreated(Session session) {
        }

        public void onSessionReleased(Session session) {
        }

        public void onLayoutSurface(Session session, int left, int top, int right, int bottom) {
        }

        public void onCommandRequest(Session session, String cmdType, Bundle parameters) {
        }

        public void onTimeShiftCommandRequest(Session session, String cmdType, Bundle parameters) {
        }

        public void onSetVideoBounds(Session session, Rect rect) {
        }

        public void onRequestCurrentVideoBounds(Session session) {
        }

        public void onRequestCurrentChannelUri(Session session) {
        }

        public void onRequestCurrentChannelLcn(Session session) {
        }

        public void onRequestStreamVolume(Session session) {
        }

        public void onRequestTrackInfoList(Session session) {
        }

        public void onRequestSelectedTrackInfo(Session session) {
        }

        public void onRequestCurrentTvInputId(Session session) {
        }

        public void onRequestTimeShiftMode(Session session) {
        }

        public void onRequestAvailableSpeeds(Session session) {
        }

        public void onRequestStartRecording(Session session, String requestId, Uri programUri) {
        }

        public void onRequestStopRecording(Session session, String recordingId) {
        }

        public void onRequestScheduleRecording(Session session, String requestId, String inputId, Uri channelUri, Uri programUri, Bundle params) {
        }

        public void onRequestScheduleRecording(Session session, String requestId, String inputId, Uri channelUri, long startTime, long duration, int repeatDays, Bundle params) {
        }

        public void onSetTvRecordingInfo(Session session, String recordingId, TvRecordingInfo recordingInfo) {
        }

        public void onRequestTvRecordingInfo(Session session, String recordingId) {
        }

        public void onRequestTvRecordingInfoList(Session session, int type) {
        }

        public void onRequestSigning(Session session, String signingId, String algorithm, String alias, byte[] data) {
        }

        public void onRequestSigning(Session session, String signingId, String algorithm, String host, int port, byte[] data) {
        }

        public void onRequestCertificate(Session session, String host, int port) {
        }

        public void onSessionStateChanged(Session session, int state, int err) {
        }

        public void onBiInteractiveAppCreated(Session session, Uri biIAppUri, String biIAppId) {
        }

        public void onTeletextAppStateChanged(Session session, int state) {
        }
    }
}
