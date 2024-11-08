package android.media.tv.interactive;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.media.PlaybackParams;
import android.media.tv.AdBuffer;
import android.media.tv.AdRequest;
import android.media.tv.AdResponse;
import android.media.tv.BroadcastInfoRequest;
import android.media.tv.BroadcastInfoResponse;
import android.media.tv.TvContentRating;
import android.media.tv.TvRecordingInfo;
import android.media.tv.TvTrackInfo;
import android.media.tv.interactive.ITvInteractiveAppService;
import android.media.tv.interactive.TvInteractiveAppService;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Process;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;
import android.view.InputChannel;
import android.view.InputEvent;
import android.view.InputEventReceiver;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import com.android.internal.os.SomeArgs;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public abstract class TvInteractiveAppService extends Service {
    public static final String COMMAND_PARAMETER_KEY_CHANGE_CHANNEL_QUIETLY = "command_change_channel_quietly";
    public static final String COMMAND_PARAMETER_KEY_CHANNEL_URI = "command_channel_uri";
    public static final String COMMAND_PARAMETER_KEY_INPUT_ID = "command_input_id";
    public static final String COMMAND_PARAMETER_KEY_PLAYBACK_PARAMS = "command_playback_params";
    public static final String COMMAND_PARAMETER_KEY_PROGRAM_URI = "command_program_uri";
    public static final String COMMAND_PARAMETER_KEY_STOP_MODE = "command_stop_mode";
    public static final String COMMAND_PARAMETER_KEY_TIME_POSITION = "command_time_position";
    public static final String COMMAND_PARAMETER_KEY_TIME_SHIFT_MODE = "command_time_shift_mode";
    public static final String COMMAND_PARAMETER_KEY_TRACK_ID = "command_track_id";
    public static final String COMMAND_PARAMETER_KEY_TRACK_TYPE = "command_track_type";
    public static final String COMMAND_PARAMETER_KEY_VOLUME = "command_volume";
    public static final int COMMAND_PARAMETER_VALUE_STOP_MODE_BLANK = 1;
    public static final int COMMAND_PARAMETER_VALUE_STOP_MODE_FREEZE = 2;
    private static final boolean DEBUG = false;
    private static final int DETACH_MEDIA_VIEW_TIMEOUT_MS = 5000;
    public static final String PLAYBACK_COMMAND_TYPE_SELECT_TRACK = "select_track";
    public static final String PLAYBACK_COMMAND_TYPE_SET_STREAM_VOLUME = "set_stream_volume";
    public static final String PLAYBACK_COMMAND_TYPE_STOP = "stop";
    public static final String PLAYBACK_COMMAND_TYPE_TUNE = "tune";
    public static final String PLAYBACK_COMMAND_TYPE_TUNE_NEXT = "tune_next";
    public static final String PLAYBACK_COMMAND_TYPE_TUNE_PREV = "tune_previous";
    public static final String SERVICE_INTERFACE = "android.media.tv.interactive.TvInteractiveAppService";
    public static final String SERVICE_META_DATA = "android.media.tv.interactive.app";
    private static final String TAG = "TvInteractiveAppService";
    public static final String TIME_SHIFT_COMMAND_TYPE_PAUSE = "pause";
    public static final String TIME_SHIFT_COMMAND_TYPE_PLAY = "play";
    public static final String TIME_SHIFT_COMMAND_TYPE_RESUME = "resume";
    public static final String TIME_SHIFT_COMMAND_TYPE_SEEK_TO = "seek_to";
    public static final String TIME_SHIFT_COMMAND_TYPE_SET_MODE = "set_mode";
    public static final String TIME_SHIFT_COMMAND_TYPE_SET_PLAYBACK_PARAMS = "set_playback_params";
    private final Handler mServiceHandler = new ServiceHandler();
    private final RemoteCallbackList<ITvInteractiveAppServiceCallback> mCallbacks = new RemoteCallbackList<>();

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface PlaybackCommandStopMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface PlaybackCommandType {
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface TimeShiftCommandType {
    }

    public abstract Session onCreateSession(String str, int i);

    /* renamed from: android.media.tv.interactive.TvInteractiveAppService$1 */
    /* loaded from: classes2.dex */
    class AnonymousClass1 extends ITvInteractiveAppService.Stub {
        AnonymousClass1() {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppService
        public void registerCallback(ITvInteractiveAppServiceCallback cb) {
            if (cb != null) {
                TvInteractiveAppService.this.mCallbacks.register(cb);
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppService
        public void unregisterCallback(ITvInteractiveAppServiceCallback cb) {
            if (cb != null) {
                TvInteractiveAppService.this.mCallbacks.unregister(cb);
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppService
        public void createSession(InputChannel channel, ITvInteractiveAppSessionCallback cb, String iAppServiceId, int type) {
            if (cb == null) {
                return;
            }
            SomeArgs args = SomeArgs.obtain();
            args.arg1 = channel;
            args.arg2 = cb;
            args.arg3 = iAppServiceId;
            args.arg4 = Integer.valueOf(type);
            TvInteractiveAppService.this.mServiceHandler.obtainMessage(1, args).sendToTarget();
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppService
        public void registerAppLinkInfo(AppLinkInfo appLinkInfo) {
            TvInteractiveAppService.this.onRegisterAppLinkInfo(appLinkInfo);
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppService
        public void unregisterAppLinkInfo(AppLinkInfo appLinkInfo) {
            TvInteractiveAppService.this.onUnregisterAppLinkInfo(appLinkInfo);
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppService
        public void sendAppLinkCommand(Bundle command) {
            TvInteractiveAppService.this.onAppLinkCommand(command);
        }
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        ITvInteractiveAppService.Stub tvIAppServiceBinder = new ITvInteractiveAppService.Stub() { // from class: android.media.tv.interactive.TvInteractiveAppService.1
            AnonymousClass1() {
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppService
            public void registerCallback(ITvInteractiveAppServiceCallback cb) {
                if (cb != null) {
                    TvInteractiveAppService.this.mCallbacks.register(cb);
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppService
            public void unregisterCallback(ITvInteractiveAppServiceCallback cb) {
                if (cb != null) {
                    TvInteractiveAppService.this.mCallbacks.unregister(cb);
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppService
            public void createSession(InputChannel channel, ITvInteractiveAppSessionCallback cb, String iAppServiceId, int type) {
                if (cb == null) {
                    return;
                }
                SomeArgs args = SomeArgs.obtain();
                args.arg1 = channel;
                args.arg2 = cb;
                args.arg3 = iAppServiceId;
                args.arg4 = Integer.valueOf(type);
                TvInteractiveAppService.this.mServiceHandler.obtainMessage(1, args).sendToTarget();
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppService
            public void registerAppLinkInfo(AppLinkInfo appLinkInfo) {
                TvInteractiveAppService.this.onRegisterAppLinkInfo(appLinkInfo);
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppService
            public void unregisterAppLinkInfo(AppLinkInfo appLinkInfo) {
                TvInteractiveAppService.this.onUnregisterAppLinkInfo(appLinkInfo);
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppService
            public void sendAppLinkCommand(Bundle command) {
                TvInteractiveAppService.this.onAppLinkCommand(command);
            }
        };
        return tvIAppServiceBinder;
    }

    public void onRegisterAppLinkInfo(AppLinkInfo appLinkInfo) {
    }

    public void onUnregisterAppLinkInfo(AppLinkInfo appLinkInfo) {
    }

    public void onAppLinkCommand(Bundle command) {
    }

    public final void notifyStateChanged(int type, int state, int error) {
        SomeArgs args = SomeArgs.obtain();
        args.arg1 = Integer.valueOf(type);
        args.arg2 = Integer.valueOf(state);
        args.arg3 = Integer.valueOf(error);
        this.mServiceHandler.obtainMessage(3, args).sendToTarget();
    }

    /* loaded from: classes2.dex */
    public static abstract class Session implements KeyEvent.Callback {
        private final Context mContext;
        final Handler mHandler;
        private Rect mMediaFrame;
        private View mMediaView;
        private MediaViewCleanUpTask mMediaViewCleanUpTask;
        private FrameLayout mMediaViewContainer;
        private boolean mMediaViewEnabled;
        private ITvInteractiveAppSessionCallback mSessionCallback;
        private Surface mSurface;
        private final WindowManager mWindowManager;
        private WindowManager.LayoutParams mWindowParams;
        private IBinder mWindowToken;
        private final KeyEvent.DispatcherState mDispatcherState = new KeyEvent.DispatcherState();
        private final Object mLock = new Object();
        private final List<Runnable> mPendingActions = new ArrayList();

        public abstract void onRelease();

        public abstract boolean onSetSurface(Surface surface);

        public Session(Context context) {
            this.mContext = context;
            this.mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            this.mHandler = new Handler(context.getMainLooper());
        }

        /* renamed from: android.media.tv.interactive.TvInteractiveAppService$Session$1 */
        /* loaded from: classes2.dex */
        class AnonymousClass1 implements Runnable {
            final /* synthetic */ boolean val$enable;

            AnonymousClass1(boolean z) {
                enable = z;
            }

            @Override // java.lang.Runnable
            public void run() {
                if (enable == Session.this.mMediaViewEnabled) {
                    return;
                }
                Session.this.mMediaViewEnabled = enable;
                if (enable) {
                    if (Session.this.mWindowToken != null) {
                        Session session = Session.this;
                        session.createMediaView(session.mWindowToken, Session.this.mMediaFrame);
                        return;
                    }
                    return;
                }
                Session.this.removeMediaView(false);
            }
        }

        public void setMediaViewEnabled(boolean enable) {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService.Session.1
                final /* synthetic */ boolean val$enable;

                AnonymousClass1(boolean enable2) {
                    enable = enable2;
                }

                @Override // java.lang.Runnable
                public void run() {
                    if (enable == Session.this.mMediaViewEnabled) {
                        return;
                    }
                    Session.this.mMediaViewEnabled = enable;
                    if (enable) {
                        if (Session.this.mWindowToken != null) {
                            Session session = Session.this;
                            session.createMediaView(session.mWindowToken, Session.this.mMediaFrame);
                            return;
                        }
                        return;
                    }
                    Session.this.removeMediaView(false);
                }
            });
        }

        public boolean isMediaViewEnabled() {
            return this.mMediaViewEnabled;
        }

        public void onStartInteractiveApp() {
        }

        public void onStopInteractiveApp() {
        }

        public void onResetInteractiveApp() {
        }

        public void onCreateBiInteractiveAppRequest(Uri biIAppUri, Bundle params) {
        }

        public void onDestroyBiInteractiveAppRequest(String biIAppId) {
        }

        public void onSetTeletextAppEnabled(boolean enable) {
        }

        public void onCurrentVideoBounds(Rect bounds) {
        }

        public void onCurrentChannelUri(Uri channelUri) {
        }

        public void onCurrentChannelLcn(int lcn) {
        }

        public void onStreamVolume(float volume) {
        }

        public void onTrackInfoList(List<TvTrackInfo> tracks) {
        }

        public void onCurrentTvInputId(String inputId) {
        }

        public void onTimeShiftMode(int mode) {
        }

        public void onAvailableSpeeds(float[] speeds) {
        }

        public void onTvRecordingInfo(TvRecordingInfo recordingInfo) {
        }

        public void onTvRecordingInfoList(List<TvRecordingInfo> recordingInfoList) {
        }

        public void onRecordingStarted(String recordingId, String requestId) {
        }

        public void onRecordingStopped(String recordingId) {
        }

        public void onRecordingConnectionFailed(String recordingId, String inputId) {
        }

        public void onRecordingDisconnected(String recordingId, String inputId) {
        }

        public void onRecordingTuned(String recordingId, Uri channelUri) {
        }

        public void onRecordingError(String recordingId, int err) {
        }

        public void onRecordingScheduled(String recordingId, String requestId) {
        }

        public void onSigningResult(String signingId, byte[] result) {
        }

        public void onError(String errMsg, Bundle params) {
        }

        public void onTimeShiftPlaybackParams(PlaybackParams params) {
        }

        public void onTimeShiftStatusChanged(String inputId, int status) {
        }

        public void onTimeShiftStartPositionChanged(String inputId, long timeMs) {
        }

        public void onTimeShiftCurrentPositionChanged(String inputId, long timeMs) {
        }

        public void onSurfaceChanged(int format, int width, int height) {
        }

        public void onMediaViewSizeChanged(int width, int height) {
        }

        public View onCreateMediaView() {
            return null;
        }

        public void onTuned(Uri channelUri) {
        }

        public void onTrackSelected(int type, String trackId) {
        }

        public void onTracksChanged(List<TvTrackInfo> tracks) {
        }

        public void onVideoAvailable() {
        }

        public void onVideoUnavailable(int reason) {
        }

        public void onContentAllowed() {
        }

        public void onContentBlocked(TvContentRating rating) {
        }

        public void onSignalStrength(int strength) {
        }

        public void onBroadcastInfoResponse(BroadcastInfoResponse response) {
        }

        public void onAdResponse(AdResponse response) {
        }

        public void onAdBufferConsumed(AdBuffer buffer) {
        }

        public void onTvMessage(int type, Bundle data) {
        }

        @Override // android.view.KeyEvent.Callback
        public boolean onKeyDown(int keyCode, KeyEvent event) {
            return false;
        }

        @Override // android.view.KeyEvent.Callback
        public boolean onKeyLongPress(int keyCode, KeyEvent event) {
            return false;
        }

        @Override // android.view.KeyEvent.Callback
        public boolean onKeyMultiple(int keyCode, int count, KeyEvent event) {
            return false;
        }

        @Override // android.view.KeyEvent.Callback
        public boolean onKeyUp(int keyCode, KeyEvent event) {
            return false;
        }

        public boolean onTouchEvent(MotionEvent event) {
            return false;
        }

        public boolean onTrackballEvent(MotionEvent event) {
            return false;
        }

        public boolean onGenericMotionEvent(MotionEvent event) {
            return false;
        }

        public void layoutSurface(int left, int top, int right, int bottom) {
            if (left > right || top > bottom) {
                throw new IllegalArgumentException("Invalid parameter");
            }
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService.Session.2
                final /* synthetic */ int val$bottom;
                final /* synthetic */ int val$left;
                final /* synthetic */ int val$right;
                final /* synthetic */ int val$top;

                AnonymousClass2(int left2, int top2, int right2, int bottom2) {
                    left = left2;
                    top = top2;
                    right = right2;
                    bottom = bottom2;
                }

                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onLayoutSurface(left, top, right, bottom);
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInteractiveAppService.TAG, "error in layoutSurface", e);
                    }
                }
            });
        }

        /* renamed from: android.media.tv.interactive.TvInteractiveAppService$Session$2 */
        /* loaded from: classes2.dex */
        class AnonymousClass2 implements Runnable {
            final /* synthetic */ int val$bottom;
            final /* synthetic */ int val$left;
            final /* synthetic */ int val$right;
            final /* synthetic */ int val$top;

            AnonymousClass2(int left2, int top2, int right2, int bottom2) {
                left = left2;
                top = top2;
                right = right2;
                bottom = bottom2;
            }

            @Override // java.lang.Runnable
            public void run() {
                try {
                    if (Session.this.mSessionCallback != null) {
                        Session.this.mSessionCallback.onLayoutSurface(left, top, right, bottom);
                    }
                } catch (RemoteException e) {
                    Log.w(TvInteractiveAppService.TAG, "error in layoutSurface", e);
                }
            }
        }

        /* renamed from: android.media.tv.interactive.TvInteractiveAppService$Session$3 */
        /* loaded from: classes2.dex */
        class AnonymousClass3 implements Runnable {
            final /* synthetic */ BroadcastInfoRequest val$request;

            AnonymousClass3(BroadcastInfoRequest broadcastInfoRequest) {
                request = broadcastInfoRequest;
            }

            @Override // java.lang.Runnable
            public void run() {
                try {
                    if (Session.this.mSessionCallback != null) {
                        Session.this.mSessionCallback.onBroadcastInfoRequest(request);
                    }
                } catch (RemoteException e) {
                    Log.w(TvInteractiveAppService.TAG, "error in requestBroadcastInfo", e);
                }
            }
        }

        public void requestBroadcastInfo(BroadcastInfoRequest request) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService.Session.3
                final /* synthetic */ BroadcastInfoRequest val$request;

                AnonymousClass3(BroadcastInfoRequest request2) {
                    request = request2;
                }

                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onBroadcastInfoRequest(request);
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInteractiveAppService.TAG, "error in requestBroadcastInfo", e);
                    }
                }
            });
        }

        /* renamed from: android.media.tv.interactive.TvInteractiveAppService$Session$4 */
        /* loaded from: classes2.dex */
        class AnonymousClass4 implements Runnable {
            final /* synthetic */ int val$requestId;

            AnonymousClass4(int i) {
                requestId = i;
            }

            @Override // java.lang.Runnable
            public void run() {
                try {
                    if (Session.this.mSessionCallback != null) {
                        Session.this.mSessionCallback.onRemoveBroadcastInfo(requestId);
                    }
                } catch (RemoteException e) {
                    Log.w(TvInteractiveAppService.TAG, "error in removeBroadcastInfo", e);
                }
            }
        }

        public void removeBroadcastInfo(int requestId) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService.Session.4
                final /* synthetic */ int val$requestId;

                AnonymousClass4(int requestId2) {
                    requestId = requestId2;
                }

                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onRemoveBroadcastInfo(requestId);
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInteractiveAppService.TAG, "error in removeBroadcastInfo", e);
                    }
                }
            });
        }

        /* renamed from: android.media.tv.interactive.TvInteractiveAppService$Session$5 */
        /* loaded from: classes2.dex */
        class AnonymousClass5 implements Runnable {
            final /* synthetic */ String val$cmdType;
            final /* synthetic */ Bundle val$parameters;

            AnonymousClass5(String str, Bundle bundle) {
                cmdType = str;
                parameters = bundle;
            }

            @Override // java.lang.Runnable
            public void run() {
                try {
                    if (Session.this.mSessionCallback != null) {
                        Session.this.mSessionCallback.onCommandRequest(cmdType, parameters);
                    }
                } catch (RemoteException e) {
                    Log.w(TvInteractiveAppService.TAG, "error in requestCommand", e);
                }
            }
        }

        public void sendPlaybackCommandRequest(String cmdType, Bundle parameters) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService.Session.5
                final /* synthetic */ String val$cmdType;
                final /* synthetic */ Bundle val$parameters;

                AnonymousClass5(String cmdType2, Bundle parameters2) {
                    cmdType = cmdType2;
                    parameters = parameters2;
                }

                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onCommandRequest(cmdType, parameters);
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInteractiveAppService.TAG, "error in requestCommand", e);
                    }
                }
            });
        }

        /* renamed from: android.media.tv.interactive.TvInteractiveAppService$Session$6 */
        /* loaded from: classes2.dex */
        class AnonymousClass6 implements Runnable {
            final /* synthetic */ String val$cmdType;
            final /* synthetic */ Bundle val$parameters;

            AnonymousClass6(String str, Bundle bundle) {
                cmdType = str;
                parameters = bundle;
            }

            @Override // java.lang.Runnable
            public void run() {
                try {
                    if (Session.this.mSessionCallback != null) {
                        Session.this.mSessionCallback.onTimeShiftCommandRequest(cmdType, parameters);
                    }
                } catch (RemoteException e) {
                    Log.w(TvInteractiveAppService.TAG, "error in requestTimeShiftCommand", e);
                }
            }
        }

        public void sendTimeShiftCommandRequest(String cmdType, Bundle parameters) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService.Session.6
                final /* synthetic */ String val$cmdType;
                final /* synthetic */ Bundle val$parameters;

                AnonymousClass6(String cmdType2, Bundle parameters2) {
                    cmdType = cmdType2;
                    parameters = parameters2;
                }

                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onTimeShiftCommandRequest(cmdType, parameters);
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInteractiveAppService.TAG, "error in requestTimeShiftCommand", e);
                    }
                }
            });
        }

        /* renamed from: android.media.tv.interactive.TvInteractiveAppService$Session$7 */
        /* loaded from: classes2.dex */
        class AnonymousClass7 implements Runnable {
            final /* synthetic */ Rect val$rect;

            AnonymousClass7(Rect rect) {
                rect = rect;
            }

            @Override // java.lang.Runnable
            public void run() {
                try {
                    if (Session.this.mSessionCallback != null) {
                        Session.this.mSessionCallback.onSetVideoBounds(rect);
                    }
                } catch (RemoteException e) {
                    Log.w(TvInteractiveAppService.TAG, "error in setVideoBounds", e);
                }
            }
        }

        public void setVideoBounds(Rect rect) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService.Session.7
                final /* synthetic */ Rect val$rect;

                AnonymousClass7(Rect rect2) {
                    rect = rect2;
                }

                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onSetVideoBounds(rect);
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInteractiveAppService.TAG, "error in setVideoBounds", e);
                    }
                }
            });
        }

        /* renamed from: android.media.tv.interactive.TvInteractiveAppService$Session$8 */
        /* loaded from: classes2.dex */
        class AnonymousClass8 implements Runnable {
            AnonymousClass8() {
            }

            @Override // java.lang.Runnable
            public void run() {
                try {
                    if (Session.this.mSessionCallback != null) {
                        Session.this.mSessionCallback.onRequestCurrentVideoBounds();
                    }
                } catch (RemoteException e) {
                    Log.w(TvInteractiveAppService.TAG, "error in requestCurrentVideoBounds", e);
                }
            }
        }

        public void requestCurrentVideoBounds() {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService.Session.8
                AnonymousClass8() {
                }

                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onRequestCurrentVideoBounds();
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInteractiveAppService.TAG, "error in requestCurrentVideoBounds", e);
                    }
                }
            });
        }

        /* renamed from: android.media.tv.interactive.TvInteractiveAppService$Session$9 */
        /* loaded from: classes2.dex */
        class AnonymousClass9 implements Runnable {
            AnonymousClass9() {
            }

            @Override // java.lang.Runnable
            public void run() {
                try {
                    if (Session.this.mSessionCallback != null) {
                        Session.this.mSessionCallback.onRequestCurrentChannelUri();
                    }
                } catch (RemoteException e) {
                    Log.w(TvInteractiveAppService.TAG, "error in requestCurrentChannelUri", e);
                }
            }
        }

        public void requestCurrentChannelUri() {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService.Session.9
                AnonymousClass9() {
                }

                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onRequestCurrentChannelUri();
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInteractiveAppService.TAG, "error in requestCurrentChannelUri", e);
                    }
                }
            });
        }

        /* renamed from: android.media.tv.interactive.TvInteractiveAppService$Session$10 */
        /* loaded from: classes2.dex */
        class AnonymousClass10 implements Runnable {
            AnonymousClass10() {
            }

            @Override // java.lang.Runnable
            public void run() {
                try {
                    if (Session.this.mSessionCallback != null) {
                        Session.this.mSessionCallback.onRequestCurrentChannelLcn();
                    }
                } catch (RemoteException e) {
                    Log.w(TvInteractiveAppService.TAG, "error in requestCurrentChannelLcn", e);
                }
            }
        }

        public void requestCurrentChannelLcn() {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService.Session.10
                AnonymousClass10() {
                }

                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onRequestCurrentChannelLcn();
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInteractiveAppService.TAG, "error in requestCurrentChannelLcn", e);
                    }
                }
            });
        }

        /* renamed from: android.media.tv.interactive.TvInteractiveAppService$Session$11 */
        /* loaded from: classes2.dex */
        class AnonymousClass11 implements Runnable {
            AnonymousClass11() {
            }

            @Override // java.lang.Runnable
            public void run() {
                try {
                    if (Session.this.mSessionCallback != null) {
                        Session.this.mSessionCallback.onRequestStreamVolume();
                    }
                } catch (RemoteException e) {
                    Log.w(TvInteractiveAppService.TAG, "error in requestStreamVolume", e);
                }
            }
        }

        public void requestStreamVolume() {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService.Session.11
                AnonymousClass11() {
                }

                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onRequestStreamVolume();
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInteractiveAppService.TAG, "error in requestStreamVolume", e);
                    }
                }
            });
        }

        /* renamed from: android.media.tv.interactive.TvInteractiveAppService$Session$12 */
        /* loaded from: classes2.dex */
        class AnonymousClass12 implements Runnable {
            AnonymousClass12() {
            }

            @Override // java.lang.Runnable
            public void run() {
                try {
                    if (Session.this.mSessionCallback != null) {
                        Session.this.mSessionCallback.onRequestTrackInfoList();
                    }
                } catch (RemoteException e) {
                    Log.w(TvInteractiveAppService.TAG, "error in requestTrackInfoList", e);
                }
            }
        }

        public void requestTrackInfoList() {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService.Session.12
                AnonymousClass12() {
                }

                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onRequestTrackInfoList();
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInteractiveAppService.TAG, "error in requestTrackInfoList", e);
                    }
                }
            });
        }

        /* renamed from: android.media.tv.interactive.TvInteractiveAppService$Session$13 */
        /* loaded from: classes2.dex */
        class AnonymousClass13 implements Runnable {
            AnonymousClass13() {
            }

            @Override // java.lang.Runnable
            public void run() {
                try {
                    if (Session.this.mSessionCallback != null) {
                        Session.this.mSessionCallback.onRequestCurrentTvInputId();
                    }
                } catch (RemoteException e) {
                    Log.w(TvInteractiveAppService.TAG, "error in requestCurrentTvInputId", e);
                }
            }
        }

        public void requestCurrentTvInputId() {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService.Session.13
                AnonymousClass13() {
                }

                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onRequestCurrentTvInputId();
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInteractiveAppService.TAG, "error in requestCurrentTvInputId", e);
                    }
                }
            });
        }

        /* renamed from: android.media.tv.interactive.TvInteractiveAppService$Session$14 */
        /* loaded from: classes2.dex */
        class AnonymousClass14 implements Runnable {
            AnonymousClass14() {
            }

            @Override // java.lang.Runnable
            public void run() {
                try {
                    if (Session.this.mSessionCallback != null) {
                        Session.this.mSessionCallback.onRequestTimeShiftMode();
                    }
                } catch (RemoteException e) {
                    Log.w(TvInteractiveAppService.TAG, "error in requestTimeShiftMode", e);
                }
            }
        }

        public void requestTimeShiftMode() {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService.Session.14
                AnonymousClass14() {
                }

                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onRequestTimeShiftMode();
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInteractiveAppService.TAG, "error in requestTimeShiftMode", e);
                    }
                }
            });
        }

        /* renamed from: android.media.tv.interactive.TvInteractiveAppService$Session$15 */
        /* loaded from: classes2.dex */
        class AnonymousClass15 implements Runnable {
            AnonymousClass15() {
            }

            @Override // java.lang.Runnable
            public void run() {
                try {
                    if (Session.this.mSessionCallback != null) {
                        Session.this.mSessionCallback.onRequestAvailableSpeeds();
                    }
                } catch (RemoteException e) {
                    Log.w(TvInteractiveAppService.TAG, "error in requestAvailableSpeeds", e);
                }
            }
        }

        public void requestAvailableSpeeds() {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService.Session.15
                AnonymousClass15() {
                }

                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onRequestAvailableSpeeds();
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInteractiveAppService.TAG, "error in requestAvailableSpeeds", e);
                    }
                }
            });
        }

        public void requestStartRecording(final String requestId, final Uri programUri) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService$Session$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    TvInteractiveAppService.Session.this.lambda$requestStartRecording$0(requestId, programUri);
                }
            });
        }

        public /* synthetic */ void lambda$requestStartRecording$0(String requestId, Uri programUri) {
            try {
                ITvInteractiveAppSessionCallback iTvInteractiveAppSessionCallback = this.mSessionCallback;
                if (iTvInteractiveAppSessionCallback != null) {
                    iTvInteractiveAppSessionCallback.onRequestStartRecording(requestId, programUri);
                }
            } catch (RemoteException e) {
                Log.w(TvInteractiveAppService.TAG, "error in requestStartRecording", e);
            }
        }

        public void requestStopRecording(final String recordingId) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService$Session$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    TvInteractiveAppService.Session.this.lambda$requestStopRecording$1(recordingId);
                }
            });
        }

        public /* synthetic */ void lambda$requestStopRecording$1(String recordingId) {
            try {
                ITvInteractiveAppSessionCallback iTvInteractiveAppSessionCallback = this.mSessionCallback;
                if (iTvInteractiveAppSessionCallback != null) {
                    iTvInteractiveAppSessionCallback.onRequestStopRecording(recordingId);
                }
            } catch (RemoteException e) {
                Log.w(TvInteractiveAppService.TAG, "error in requestStopRecording", e);
            }
        }

        public void requestScheduleRecording(final String requestId, final String inputId, final Uri channelUri, final Uri programUri, final Bundle params) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService$Session$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    TvInteractiveAppService.Session.this.lambda$requestScheduleRecording$2(requestId, inputId, channelUri, programUri, params);
                }
            });
        }

        public /* synthetic */ void lambda$requestScheduleRecording$2(String requestId, String inputId, Uri channelUri, Uri programUri, Bundle params) {
            try {
                ITvInteractiveAppSessionCallback iTvInteractiveAppSessionCallback = this.mSessionCallback;
                if (iTvInteractiveAppSessionCallback != null) {
                    iTvInteractiveAppSessionCallback.onRequestScheduleRecording(requestId, inputId, channelUri, programUri, params);
                }
            } catch (RemoteException e) {
                Log.w(TvInteractiveAppService.TAG, "error in requestScheduleRecording", e);
            }
        }

        public void requestScheduleRecording(final String requestId, final String inputId, final Uri channelUri, final long startTime, final long duration, final int repeatDays, final Bundle params) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService$Session$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    TvInteractiveAppService.Session.this.lambda$requestScheduleRecording$3(requestId, inputId, channelUri, startTime, duration, repeatDays, params);
                }
            });
        }

        public /* synthetic */ void lambda$requestScheduleRecording$3(String requestId, String inputId, Uri channelUri, long startTime, long duration, int repeatDays, Bundle params) {
            try {
                ITvInteractiveAppSessionCallback iTvInteractiveAppSessionCallback = this.mSessionCallback;
                if (iTvInteractiveAppSessionCallback != null) {
                    iTvInteractiveAppSessionCallback.onRequestScheduleRecording2(requestId, inputId, channelUri, startTime, duration, repeatDays, params);
                }
            } catch (RemoteException e) {
                Log.w(TvInteractiveAppService.TAG, "error in requestScheduleRecording", e);
            }
        }

        public void setTvRecordingInfo(final String recordingId, final TvRecordingInfo recordingInfo) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService$Session$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    TvInteractiveAppService.Session.this.lambda$setTvRecordingInfo$4(recordingId, recordingInfo);
                }
            });
        }

        public /* synthetic */ void lambda$setTvRecordingInfo$4(String recordingId, TvRecordingInfo recordingInfo) {
            try {
                ITvInteractiveAppSessionCallback iTvInteractiveAppSessionCallback = this.mSessionCallback;
                if (iTvInteractiveAppSessionCallback != null) {
                    iTvInteractiveAppSessionCallback.onSetTvRecordingInfo(recordingId, recordingInfo);
                }
            } catch (RemoteException e) {
                Log.w(TvInteractiveAppService.TAG, "error in setTvRecordingInfo", e);
            }
        }

        public void requestTvRecordingInfo(final String recordingId) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService$Session$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    TvInteractiveAppService.Session.this.lambda$requestTvRecordingInfo$5(recordingId);
                }
            });
        }

        public /* synthetic */ void lambda$requestTvRecordingInfo$5(String recordingId) {
            try {
                ITvInteractiveAppSessionCallback iTvInteractiveAppSessionCallback = this.mSessionCallback;
                if (iTvInteractiveAppSessionCallback != null) {
                    iTvInteractiveAppSessionCallback.onRequestTvRecordingInfo(recordingId);
                }
            } catch (RemoteException e) {
                Log.w(TvInteractiveAppService.TAG, "error in requestTvRecordingInfo", e);
            }
        }

        public void requestTvRecordingInfoList(final int type) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService$Session$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    TvInteractiveAppService.Session.this.lambda$requestTvRecordingInfoList$6(type);
                }
            });
        }

        public /* synthetic */ void lambda$requestTvRecordingInfoList$6(int type) {
            try {
                ITvInteractiveAppSessionCallback iTvInteractiveAppSessionCallback = this.mSessionCallback;
                if (iTvInteractiveAppSessionCallback != null) {
                    iTvInteractiveAppSessionCallback.onRequestTvRecordingInfoList(type);
                }
            } catch (RemoteException e) {
                Log.w(TvInteractiveAppService.TAG, "error in requestTvRecordingInfoList", e);
            }
        }

        /* renamed from: android.media.tv.interactive.TvInteractiveAppService$Session$16 */
        /* loaded from: classes2.dex */
        class AnonymousClass16 implements Runnable {
            final /* synthetic */ String val$algorithm;
            final /* synthetic */ String val$alias;
            final /* synthetic */ byte[] val$data;
            final /* synthetic */ String val$signingId;

            AnonymousClass16(String str, String str2, String str3, byte[] bArr) {
                signingId = str;
                algorithm = str2;
                alias = str3;
                data = bArr;
            }

            @Override // java.lang.Runnable
            public void run() {
                try {
                    if (Session.this.mSessionCallback != null) {
                        Session.this.mSessionCallback.onRequestSigning(signingId, algorithm, alias, data);
                    }
                } catch (RemoteException e) {
                    Log.w(TvInteractiveAppService.TAG, "error in requestSigning", e);
                }
            }
        }

        public void requestSigning(String signingId, String algorithm, String alias, byte[] data) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService.Session.16
                final /* synthetic */ String val$algorithm;
                final /* synthetic */ String val$alias;
                final /* synthetic */ byte[] val$data;
                final /* synthetic */ String val$signingId;

                AnonymousClass16(String signingId2, String algorithm2, String alias2, byte[] data2) {
                    signingId = signingId2;
                    algorithm = algorithm2;
                    alias = alias2;
                    data = data2;
                }

                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onRequestSigning(signingId, algorithm, alias, data);
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInteractiveAppService.TAG, "error in requestSigning", e);
                    }
                }
            });
        }

        /* renamed from: android.media.tv.interactive.TvInteractiveAppService$Session$17 */
        /* loaded from: classes2.dex */
        class AnonymousClass17 implements Runnable {
            final /* synthetic */ AdRequest val$request;

            AnonymousClass17(AdRequest adRequest) {
                request = adRequest;
            }

            @Override // java.lang.Runnable
            public void run() {
                try {
                    if (Session.this.mSessionCallback != null) {
                        Session.this.mSessionCallback.onAdRequest(request);
                    }
                } catch (RemoteException e) {
                    Log.w(TvInteractiveAppService.TAG, "error in requestAd", e);
                }
            }
        }

        public void requestAd(AdRequest request) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService.Session.17
                final /* synthetic */ AdRequest val$request;

                AnonymousClass17(AdRequest request2) {
                    request = request2;
                }

                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onAdRequest(request);
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInteractiveAppService.TAG, "error in requestAd", e);
                    }
                }
            });
        }

        public void startInteractiveApp() {
            onStartInteractiveApp();
        }

        public void stopInteractiveApp() {
            onStopInteractiveApp();
        }

        public void resetInteractiveApp() {
            onResetInteractiveApp();
        }

        public void createBiInteractiveApp(Uri biIAppUri, Bundle params) {
            onCreateBiInteractiveAppRequest(biIAppUri, params);
        }

        public void destroyBiInteractiveApp(String biIAppId) {
            onDestroyBiInteractiveAppRequest(biIAppId);
        }

        public void setTeletextAppEnabled(boolean enable) {
            onSetTeletextAppEnabled(enable);
        }

        public void sendCurrentVideoBounds(Rect bounds) {
            onCurrentVideoBounds(bounds);
        }

        public void sendCurrentChannelUri(Uri channelUri) {
            onCurrentChannelUri(channelUri);
        }

        public void sendCurrentChannelLcn(int lcn) {
            onCurrentChannelLcn(lcn);
        }

        public void sendStreamVolume(float volume) {
            onStreamVolume(volume);
        }

        public void sendTrackInfoList(List<TvTrackInfo> tracks) {
            onTrackInfoList(tracks);
        }

        public void sendCurrentTvInputId(String inputId) {
            onCurrentTvInputId(inputId);
        }

        public void sendTimeShiftMode(int mode) {
            onTimeShiftMode(mode);
        }

        public void sendAvailableSpeeds(float[] speeds) {
            onAvailableSpeeds(speeds);
        }

        public void sendTvRecordingInfo(TvRecordingInfo recordingInfo) {
            onTvRecordingInfo(recordingInfo);
        }

        public void sendTvRecordingInfoList(List<TvRecordingInfo> recordingInfoList) {
            onTvRecordingInfoList(recordingInfoList);
        }

        public void sendSigningResult(String signingId, byte[] result) {
            onSigningResult(signingId, result);
        }

        public void notifyError(String errMsg, Bundle params) {
            onError(errMsg, params);
        }

        public void release() {
            onRelease();
            Surface surface = this.mSurface;
            if (surface != null) {
                surface.release();
                this.mSurface = null;
            }
            synchronized (this.mLock) {
                this.mSessionCallback = null;
                this.mPendingActions.clear();
            }
            removeMediaView(true);
        }

        public void notifyTuned(Uri channelUri) {
            onTuned(channelUri);
        }

        public void notifyTrackSelected(int type, String trackId) {
            onTrackSelected(type, trackId);
        }

        public void notifyTracksChanged(List<TvTrackInfo> tracks) {
            onTracksChanged(tracks);
        }

        public void notifyVideoAvailable() {
            onVideoAvailable();
        }

        public void notifyVideoUnavailable(int reason) {
            onVideoUnavailable(reason);
        }

        public void notifyContentAllowed() {
            onContentAllowed();
        }

        public void notifyContentBlocked(TvContentRating rating) {
            onContentBlocked(rating);
        }

        public void notifySignalStrength(int strength) {
            onSignalStrength(strength);
        }

        public void notifyBroadcastInfoResponse(BroadcastInfoResponse response) {
            onBroadcastInfoResponse(response);
        }

        public void notifyAdResponse(AdResponse response) {
            onAdResponse(response);
        }

        public void notifyTvMessage(int type, Bundle data) {
            onTvMessage(type, data);
        }

        public void notifyAdBufferConsumed(AdBuffer buffer) {
            onAdBufferConsumed(buffer);
        }

        public void notifyRecordingStarted(String recordingId, String requestId) {
            onRecordingStarted(recordingId, requestId);
        }

        public void notifyRecordingStopped(String recordingId) {
            onRecordingStopped(recordingId);
        }

        public void notifyRecordingConnectionFailed(String recordingId, String inputId) {
            onRecordingConnectionFailed(recordingId, inputId);
        }

        public void notifyRecordingDisconnected(String recordingId, String inputId) {
            onRecordingDisconnected(recordingId, inputId);
        }

        public void notifyRecordingTuned(String recordingId, Uri channelUri) {
            onRecordingTuned(recordingId, channelUri);
        }

        public void notifyRecordingError(String recordingId, int err) {
            onRecordingError(recordingId, err);
        }

        public void notifyRecordingScheduled(String recordingId, String requestId) {
            onRecordingScheduled(recordingId, requestId);
        }

        public void notifyTimeShiftPlaybackParams(PlaybackParams params) {
            onTimeShiftPlaybackParams(params);
        }

        public void notifyTimeShiftStatusChanged(String inputId, int status) {
            onTimeShiftStatusChanged(inputId, status);
        }

        public void notifyTimeShiftStartPositionChanged(String inputId, long timeMs) {
            onTimeShiftStartPositionChanged(inputId, timeMs);
        }

        public void notifyTimeShiftCurrentPositionChanged(String inputId, long timeMs) {
            onTimeShiftCurrentPositionChanged(inputId, timeMs);
        }

        /* renamed from: android.media.tv.interactive.TvInteractiveAppService$Session$18 */
        /* loaded from: classes2.dex */
        class AnonymousClass18 implements Runnable {
            final /* synthetic */ int val$err;
            final /* synthetic */ int val$state;

            AnonymousClass18(int i, int i2) {
                state = i;
                err = i2;
            }

            @Override // java.lang.Runnable
            public void run() {
                try {
                    if (Session.this.mSessionCallback != null) {
                        Session.this.mSessionCallback.onSessionStateChanged(state, err);
                    }
                } catch (RemoteException e) {
                    Log.w(TvInteractiveAppService.TAG, "error in notifySessionStateChanged", e);
                }
            }
        }

        public void notifySessionStateChanged(int state, int err) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService.Session.18
                final /* synthetic */ int val$err;
                final /* synthetic */ int val$state;

                AnonymousClass18(int state2, int err2) {
                    state = state2;
                    err = err2;
                }

                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onSessionStateChanged(state, err);
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInteractiveAppService.TAG, "error in notifySessionStateChanged", e);
                    }
                }
            });
        }

        /* renamed from: android.media.tv.interactive.TvInteractiveAppService$Session$19 */
        /* loaded from: classes2.dex */
        class AnonymousClass19 implements Runnable {
            final /* synthetic */ String val$biIAppId;
            final /* synthetic */ Uri val$biIAppUri;

            AnonymousClass19(String str, Uri uri) {
                biIAppId = str;
                biIAppUri = uri;
            }

            @Override // java.lang.Runnable
            public void run() {
                try {
                    if (Session.this.mSessionCallback != null) {
                        Session.this.mSessionCallback.onBiInteractiveAppCreated(biIAppUri, biIAppId);
                    }
                } catch (RemoteException e) {
                    Log.w(TvInteractiveAppService.TAG, "error in notifyBiInteractiveAppCreated", e);
                }
            }
        }

        public final void notifyBiInteractiveAppCreated(Uri biIAppUri, String biIAppId) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService.Session.19
                final /* synthetic */ String val$biIAppId;
                final /* synthetic */ Uri val$biIAppUri;

                AnonymousClass19(String biIAppId2, Uri biIAppUri2) {
                    biIAppId = biIAppId2;
                    biIAppUri = biIAppUri2;
                }

                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onBiInteractiveAppCreated(biIAppUri, biIAppId);
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInteractiveAppService.TAG, "error in notifyBiInteractiveAppCreated", e);
                    }
                }
            });
        }

        /* renamed from: android.media.tv.interactive.TvInteractiveAppService$Session$20 */
        /* loaded from: classes2.dex */
        class AnonymousClass20 implements Runnable {
            final /* synthetic */ int val$state;

            AnonymousClass20(int i) {
                state = i;
            }

            @Override // java.lang.Runnable
            public void run() {
                try {
                    if (Session.this.mSessionCallback != null) {
                        Session.this.mSessionCallback.onTeletextAppStateChanged(state);
                    }
                } catch (RemoteException e) {
                    Log.w(TvInteractiveAppService.TAG, "error in notifyTeletextAppState", e);
                }
            }
        }

        public final void notifyTeletextAppStateChanged(int state) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService.Session.20
                final /* synthetic */ int val$state;

                AnonymousClass20(int state2) {
                    state = state2;
                }

                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onTeletextAppStateChanged(state);
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInteractiveAppService.TAG, "error in notifyTeletextAppState", e);
                    }
                }
            });
        }

        public void notifyAdBufferReady(AdBuffer buffer) {
            try {
                AdBuffer dupBuffer = AdBuffer.dupAdBuffer(buffer);
                executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService.Session.21
                    final /* synthetic */ AdBuffer val$buffer;
                    final /* synthetic */ AdBuffer val$dupBuffer;

                    AnonymousClass21(AdBuffer buffer2, AdBuffer dupBuffer2) {
                        buffer = buffer2;
                        dupBuffer = dupBuffer2;
                    }

                    @Override // java.lang.Runnable
                    public void run() {
                        AdBuffer adBuffer;
                        try {
                            try {
                                if (Session.this.mSessionCallback != null) {
                                    Session.this.mSessionCallback.onAdBufferReady(dupBuffer);
                                }
                                adBuffer = dupBuffer;
                                if (adBuffer == null) {
                                    return;
                                }
                            } catch (RemoteException e) {
                                Log.w(TvInteractiveAppService.TAG, "error in notifyAdBuffer", e);
                                adBuffer = dupBuffer;
                                if (adBuffer == null) {
                                    return;
                                }
                            }
                            adBuffer.getSharedMemory().close();
                        } catch (Throwable th) {
                            AdBuffer adBuffer2 = dupBuffer;
                            if (adBuffer2 != null) {
                                adBuffer2.getSharedMemory().close();
                            }
                            throw th;
                        }
                    }
                });
            } catch (IOException e) {
                Log.w(TvInteractiveAppService.TAG, "dup AdBuffer error in notifyAdBufferReady:", e);
            }
        }

        /* renamed from: android.media.tv.interactive.TvInteractiveAppService$Session$21 */
        /* loaded from: classes2.dex */
        class AnonymousClass21 implements Runnable {
            final /* synthetic */ AdBuffer val$buffer;
            final /* synthetic */ AdBuffer val$dupBuffer;

            AnonymousClass21(AdBuffer buffer2, AdBuffer dupBuffer2) {
                buffer = buffer2;
                dupBuffer = dupBuffer2;
            }

            @Override // java.lang.Runnable
            public void run() {
                AdBuffer adBuffer;
                try {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onAdBufferReady(dupBuffer);
                        }
                        adBuffer = dupBuffer;
                        if (adBuffer == null) {
                            return;
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInteractiveAppService.TAG, "error in notifyAdBuffer", e);
                        adBuffer = dupBuffer;
                        if (adBuffer == null) {
                            return;
                        }
                    }
                    adBuffer.getSharedMemory().close();
                } catch (Throwable th) {
                    AdBuffer adBuffer2 = dupBuffer;
                    if (adBuffer2 != null) {
                        adBuffer2.getSharedMemory().close();
                    }
                    throw th;
                }
            }
        }

        public int dispatchInputEvent(InputEvent event, InputEventReceiver receiver) {
            if (event instanceof KeyEvent) {
                KeyEvent keyEvent = (KeyEvent) event;
                return keyEvent.dispatch(this, this.mDispatcherState, this) ? 1 : 0;
            }
            if (event instanceof MotionEvent) {
                MotionEvent motionEvent = (MotionEvent) event;
                int source = motionEvent.getSource();
                return motionEvent.isTouchEvent() ? onTouchEvent(motionEvent) ? 1 : 0 : (source & 4) != 0 ? onTrackballEvent(motionEvent) ? 1 : 0 : onGenericMotionEvent(motionEvent) ? 1 : 0;
            }
            return 0;
        }

        public void initialize(ITvInteractiveAppSessionCallback callback) {
            synchronized (this.mLock) {
                this.mSessionCallback = callback;
                for (Runnable runnable : this.mPendingActions) {
                    runnable.run();
                }
                this.mPendingActions.clear();
            }
        }

        public void setSurface(Surface surface) {
            onSetSurface(surface);
            Surface surface2 = this.mSurface;
            if (surface2 != null) {
                surface2.release();
            }
            this.mSurface = surface;
        }

        public void dispatchSurfaceChanged(int format, int width, int height) {
            onSurfaceChanged(format, width, height);
        }

        private void executeOrPostRunnableOnMainThread(Runnable action) {
            synchronized (this.mLock) {
                if (this.mSessionCallback == null) {
                    this.mPendingActions.add(action);
                } else if (this.mHandler.getLooper().isCurrentThread()) {
                    action.run();
                } else {
                    this.mHandler.post(action);
                }
            }
        }

        public void createMediaView(IBinder windowToken, Rect frame) {
            if (this.mMediaViewContainer != null) {
                removeMediaView(false);
            }
            this.mWindowToken = windowToken;
            this.mMediaFrame = frame;
            onMediaViewSizeChanged(frame.right - frame.left, frame.bottom - frame.top);
            if (!this.mMediaViewEnabled) {
                return;
            }
            View onCreateMediaView = onCreateMediaView();
            this.mMediaView = onCreateMediaView;
            if (onCreateMediaView == null) {
                return;
            }
            MediaViewCleanUpTask mediaViewCleanUpTask = this.mMediaViewCleanUpTask;
            if (mediaViewCleanUpTask != null) {
                mediaViewCleanUpTask.cancel(true);
                this.mMediaViewCleanUpTask = null;
            }
            FrameLayout frameLayout = new FrameLayout(this.mContext.getApplicationContext());
            this.mMediaViewContainer = frameLayout;
            frameLayout.addView(this.mMediaView);
            int flags = ActivityManager.isHighEndGfx() ? 536 | 16777216 : 536;
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(frame.right - frame.left, frame.bottom - frame.top, frame.left, frame.top, 1001, flags, -2);
            this.mWindowParams = layoutParams;
            layoutParams.privateFlags |= 64;
            this.mWindowParams.gravity = 8388659;
            this.mWindowParams.token = windowToken;
            this.mWindowManager.addView(this.mMediaViewContainer, this.mWindowParams);
        }

        public void relayoutMediaView(Rect frame) {
            Rect rect = this.mMediaFrame;
            if (rect == null || rect.width() != frame.width() || this.mMediaFrame.height() != frame.height()) {
                onMediaViewSizeChanged(frame.right - frame.left, frame.bottom - frame.top);
            }
            this.mMediaFrame = frame;
            if (!this.mMediaViewEnabled || this.mMediaViewContainer == null) {
                return;
            }
            this.mWindowParams.x = frame.left;
            this.mWindowParams.y = frame.top;
            this.mWindowParams.width = frame.right - frame.left;
            this.mWindowParams.height = frame.bottom - frame.top;
            this.mWindowManager.updateViewLayout(this.mMediaViewContainer, this.mWindowParams);
        }

        public void removeMediaView(boolean clearWindowToken) {
            if (clearWindowToken) {
                this.mWindowToken = null;
                this.mMediaFrame = null;
            }
            FrameLayout frameLayout = this.mMediaViewContainer;
            if (frameLayout != null) {
                frameLayout.removeView(this.mMediaView);
                this.mMediaView = null;
                this.mWindowManager.removeView(this.mMediaViewContainer);
                this.mMediaViewContainer = null;
                this.mWindowParams = null;
            }
        }

        public void scheduleMediaViewCleanup() {
            View mediaViewParent = this.mMediaViewContainer;
            if (mediaViewParent != null) {
                MediaViewCleanUpTask mediaViewCleanUpTask = new MediaViewCleanUpTask();
                this.mMediaViewCleanUpTask = mediaViewCleanUpTask;
                mediaViewCleanUpTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, mediaViewParent);
            }
        }
    }

    /* loaded from: classes2.dex */
    public static final class MediaViewCleanUpTask extends AsyncTask<View, Void, Void> {
        /* synthetic */ MediaViewCleanUpTask(MediaViewCleanUpTaskIA mediaViewCleanUpTaskIA) {
            this();
        }

        private MediaViewCleanUpTask() {
        }

        @Override // android.os.AsyncTask
        public Void doInBackground(View... views) {
            View mediaViewParent = views[0];
            try {
                Thread.sleep(5000L);
                if (!isCancelled() && mediaViewParent.isAttachedToWindow()) {
                    Log.e(TvInteractiveAppService.TAG, "Time out on releasing media view. Killing " + mediaViewParent.getContext().getPackageName());
                    Process.killProcess(Process.myPid());
                }
                return null;
            } catch (InterruptedException e) {
                return null;
            }
        }
    }

    /* loaded from: classes2.dex */
    private final class ServiceHandler extends Handler {
        private static final int DO_CREATE_SESSION = 1;
        private static final int DO_NOTIFY_RTE_STATE_CHANGED = 3;
        private static final int DO_NOTIFY_SESSION_CREATED = 2;

        /* synthetic */ ServiceHandler(TvInteractiveAppService tvInteractiveAppService, ServiceHandlerIA serviceHandlerIA) {
            this();
        }

        private ServiceHandler() {
        }

        private void broadcastRteStateChanged(int type, int state, int error) {
            int n = TvInteractiveAppService.this.mCallbacks.beginBroadcast();
            for (int i = 0; i < n; i++) {
                try {
                    ((ITvInteractiveAppServiceCallback) TvInteractiveAppService.this.mCallbacks.getBroadcastItem(i)).onStateChanged(type, state, error);
                } catch (RemoteException e) {
                    Log.e(TvInteractiveAppService.TAG, "error in broadcastRteStateChanged", e);
                }
            }
            TvInteractiveAppService.this.mCallbacks.finishBroadcast();
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    SomeArgs args = (SomeArgs) msg.obj;
                    InputChannel channel = (InputChannel) args.arg1;
                    ITvInteractiveAppSessionCallback cb = (ITvInteractiveAppSessionCallback) args.arg2;
                    String iAppServiceId = (String) args.arg3;
                    int type = ((Integer) args.arg4).intValue();
                    args.recycle();
                    Session sessionImpl = TvInteractiveAppService.this.onCreateSession(iAppServiceId, type);
                    if (sessionImpl == null) {
                        try {
                            cb.onSessionCreated(null);
                            return;
                        } catch (RemoteException e) {
                            Log.e(TvInteractiveAppService.TAG, "error in onSessionCreated", e);
                            return;
                        }
                    }
                    ITvInteractiveAppSession stub = new ITvInteractiveAppSessionWrapper(TvInteractiveAppService.this, sessionImpl, channel);
                    SomeArgs someArgs = SomeArgs.obtain();
                    someArgs.arg1 = sessionImpl;
                    someArgs.arg2 = stub;
                    someArgs.arg3 = cb;
                    TvInteractiveAppService.this.mServiceHandler.obtainMessage(2, someArgs).sendToTarget();
                    return;
                case 2:
                    SomeArgs args2 = (SomeArgs) msg.obj;
                    Session sessionImpl2 = (Session) args2.arg1;
                    ITvInteractiveAppSession stub2 = (ITvInteractiveAppSession) args2.arg2;
                    ITvInteractiveAppSessionCallback cb2 = (ITvInteractiveAppSessionCallback) args2.arg3;
                    try {
                        cb2.onSessionCreated(stub2);
                    } catch (RemoteException e2) {
                        Log.e(TvInteractiveAppService.TAG, "error in onSessionCreated", e2);
                    }
                    if (sessionImpl2 != null) {
                        sessionImpl2.initialize(cb2);
                    }
                    args2.recycle();
                    return;
                case 3:
                    SomeArgs args3 = (SomeArgs) msg.obj;
                    int type2 = ((Integer) args3.arg1).intValue();
                    int state = ((Integer) args3.arg2).intValue();
                    int error = ((Integer) args3.arg3).intValue();
                    broadcastRteStateChanged(type2, state, error);
                    return;
                default:
                    Log.w(TvInteractiveAppService.TAG, "Unhandled message code: " + msg.what);
                    return;
            }
        }
    }
}
