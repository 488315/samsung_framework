package android.media.tv;

import android.annotation.SystemApi;
import android.app.ActivityManager;
import android.app.Service;
import android.content.AttributionSource;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.hardware.hdmi.HdmiDeviceInfo;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.AudioPresentation;
import android.media.PlaybackParams;
import android.media.tv.ITvInputService;
import android.media.tv.TvInputManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Process;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import android.view.InputChannel;
import android.view.InputEvent;
import android.view.InputEventReceiver;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;
import android.view.ViewRootImpl;
import android.view.WindowManager;
import android.widget.FrameLayout;
import com.android.internal.os.SomeArgs;
import com.android.internal.util.Preconditions;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes3.dex */
public abstract class TvInputService extends Service {
    private static final boolean DEBUG = false;
    private static final int DETACH_OVERLAY_VIEW_TIMEOUT_MS = 5000;
    public static final int PRIORITY_HINT_USE_CASE_TYPE_BACKGROUND = 100;
    public static final int PRIORITY_HINT_USE_CASE_TYPE_LIVE = 400;
    public static final int PRIORITY_HINT_USE_CASE_TYPE_PLAYBACK = 300;
    public static final int PRIORITY_HINT_USE_CASE_TYPE_RECORD = 500;
    public static final int PRIORITY_HINT_USE_CASE_TYPE_SCAN = 200;
    public static final String SERVICE_INTERFACE = "android.media.tv.TvInputService";
    public static final String SERVICE_META_DATA = "android.media.tv.input";
    private static final String TAG = "TvInputService";
    private TvInputManager mTvInputManager;
    private final Handler mServiceHandler = new ServiceHandler();
    private final RemoteCallbackList<ITvInputServiceCallback> mCallbacks = new RemoteCallbackList<>();

    @Retention(RetentionPolicy.SOURCE)
    public @interface PriorityHintUseCaseType {
    }

    public abstract Session onCreateSession(String str);

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        ITvInputService.Stub tvInputServiceBinder = new ITvInputService.Stub() { // from class: android.media.tv.TvInputService.1
            @Override // android.media.tv.ITvInputService
            public void registerCallback(ITvInputServiceCallback cb) {
                if (cb != null) {
                    TvInputService.this.mCallbacks.register(cb);
                }
            }

            @Override // android.media.tv.ITvInputService
            public void unregisterCallback(ITvInputServiceCallback cb) {
                if (cb != null) {
                    TvInputService.this.mCallbacks.unregister(cb);
                }
            }

            @Override // android.media.tv.ITvInputService
            public void createSession(InputChannel channel, ITvInputSessionCallback cb, String inputId, String sessionId, AttributionSource tvAppAttributionSource) {
                if (channel == null) {
                    Log.w(TvInputService.TAG, "Creating session without input channel");
                }
                if (cb == null) {
                    return;
                }
                SomeArgs args = SomeArgs.obtain();
                args.arg1 = channel;
                args.arg2 = cb;
                args.arg3 = inputId;
                args.arg4 = sessionId;
                args.arg5 = tvAppAttributionSource;
                TvInputService.this.mServiceHandler.obtainMessage(1, args).sendToTarget();
            }

            @Override // android.media.tv.ITvInputService
            public void createRecordingSession(ITvInputSessionCallback cb, String inputId, String sessionId) {
                if (cb == null) {
                    return;
                }
                SomeArgs args = SomeArgs.obtain();
                args.arg1 = cb;
                args.arg2 = inputId;
                args.arg3 = sessionId;
                TvInputService.this.mServiceHandler.obtainMessage(3, args).sendToTarget();
            }

            @Override // android.media.tv.ITvInputService
            public List<String> getAvailableExtensionInterfaceNames() {
                return TvInputService.this.getAvailableExtensionInterfaceNames();
            }

            @Override // android.media.tv.ITvInputService
            public IBinder getExtensionInterface(String name) {
                return TvInputService.this.getExtensionInterface(name);
            }

            @Override // android.media.tv.ITvInputService
            public String getExtensionInterfacePermission(String name) {
                return TvInputService.this.getExtensionInterfacePermission(name);
            }

            @Override // android.media.tv.ITvInputService
            public void notifyHardwareAdded(TvInputHardwareInfo hardwareInfo) {
                TvInputService.this.mServiceHandler.obtainMessage(4, hardwareInfo).sendToTarget();
            }

            @Override // android.media.tv.ITvInputService
            public void notifyHardwareRemoved(TvInputHardwareInfo hardwareInfo) {
                TvInputService.this.mServiceHandler.obtainMessage(5, hardwareInfo).sendToTarget();
            }

            @Override // android.media.tv.ITvInputService
            public void notifyHdmiDeviceAdded(HdmiDeviceInfo deviceInfo) {
                TvInputService.this.mServiceHandler.obtainMessage(6, deviceInfo).sendToTarget();
            }

            @Override // android.media.tv.ITvInputService
            public void notifyHdmiDeviceRemoved(HdmiDeviceInfo deviceInfo) {
                TvInputService.this.mServiceHandler.obtainMessage(7, deviceInfo).sendToTarget();
            }

            @Override // android.media.tv.ITvInputService
            public void notifyHdmiDeviceUpdated(HdmiDeviceInfo deviceInfo) {
                TvInputService.this.mServiceHandler.obtainMessage(8, deviceInfo).sendToTarget();
            }
        };
        IBinder ext = createExtension();
        if (ext != null) {
            tvInputServiceBinder.setExtension(ext);
        }
        return tvInputServiceBinder;
    }

    @SystemApi
    public IBinder createExtension() {
        return null;
    }

    @SystemApi
    public List<String> getAvailableExtensionInterfaceNames() {
        return new ArrayList();
    }

    @SystemApi
    public IBinder getExtensionInterface(String name) {
        return null;
    }

    @SystemApi
    public String getExtensionInterfacePermission(String name) {
        return null;
    }

    public RecordingSession onCreateRecordingSession(String inputId) {
        return null;
    }

    public Session onCreateSession(String inputId, String sessionId) {
        return onCreateSession(inputId);
    }

    public Session onCreateSession(String inputId, String sessionId, AttributionSource tvAppAttributionSource) {
        return onCreateSession(inputId, sessionId);
    }

    public RecordingSession onCreateRecordingSession(String inputId, String sessionId) {
        return onCreateRecordingSession(inputId);
    }

    @SystemApi
    public TvInputInfo onHardwareAdded(TvInputHardwareInfo hardwareInfo) {
        return null;
    }

    @SystemApi
    public String onHardwareRemoved(TvInputHardwareInfo hardwareInfo) {
        return null;
    }

    @SystemApi
    public TvInputInfo onHdmiDeviceAdded(HdmiDeviceInfo deviceInfo) {
        return null;
    }

    @SystemApi
    public String onHdmiDeviceRemoved(HdmiDeviceInfo deviceInfo) {
        return null;
    }

    @SystemApi
    public void onHdmiDeviceUpdated(HdmiDeviceInfo deviceInfo) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isPassthroughInput(String inputId) {
        if (this.mTvInputManager == null) {
            this.mTvInputManager = (TvInputManager) getSystemService(Context.TV_INPUT_SERVICE);
        }
        TvInputInfo info = this.mTvInputManager.getTvInputInfo(inputId);
        return info != null && info.isPassthroughInput();
    }

    public static abstract class Session implements KeyEvent.Callback {
        private static final int POSITION_UPDATE_INTERVAL_MS = 1000;
        private final Context mContext;
        final Handler mHandler;
        private Rect mOverlayFrame;
        private View mOverlayView;
        private OverlayViewCleanUpTask mOverlayViewCleanUpTask;
        private FrameLayout mOverlayViewContainer;
        private boolean mOverlayViewEnabled;
        private ITvInputSessionCallback mSessionCallback;
        private Surface mSurface;
        private final WindowManager mWindowManager;
        private WindowManager.LayoutParams mWindowParams;
        private IBinder mWindowToken;
        private final KeyEvent.DispatcherState mDispatcherState = new KeyEvent.DispatcherState();
        private long mStartPositionMs = Long.MIN_VALUE;
        private long mCurrentPositionMs = Long.MIN_VALUE;
        private final TimeShiftPositionTrackingRunnable mTimeShiftPositionTrackingRunnable = new TimeShiftPositionTrackingRunnable();
        private final Object mLock = new Object();
        private final List<Runnable> mPendingActions = new ArrayList();

        public abstract void onRelease();

        public abstract void onSetCaptionEnabled(boolean z);

        public abstract void onSetStreamVolume(float f);

        public abstract boolean onSetSurface(Surface surface);

        public abstract boolean onTune(Uri uri);

        public Session(Context context) {
            this.mContext = context;
            this.mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            this.mHandler = new Handler(context.getMainLooper());
        }

        public void setOverlayViewEnabled(final boolean enable) {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.TvInputService.Session.1
                @Override // java.lang.Runnable
                public void run() {
                    if (enable == Session.this.mOverlayViewEnabled) {
                        return;
                    }
                    Session.this.mOverlayViewEnabled = enable;
                    if (enable) {
                        if (Session.this.mWindowToken != null) {
                            Session.this.createOverlayView(Session.this.mWindowToken, Session.this.mOverlayFrame);
                            return;
                        }
                        return;
                    }
                    Session.this.removeOverlayView(false);
                }
            });
        }

        @SystemApi
        public void notifySessionEvent(final String eventType, final Bundle eventArgs) {
            Preconditions.checkNotNull(eventType);
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.TvInputService.Session.2
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onSessionEvent(eventType, eventArgs);
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInputService.TAG, "error in sending event (event=" + eventType + NavigationBarInflaterView.KEY_CODE_END, e);
                    }
                }
            });
        }

        public void notifyChannelRetuned(final Uri channelUri) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.TvInputService.Session.3
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onChannelRetuned(channelUri);
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInputService.TAG, "error in notifyChannelRetuned", e);
                    }
                }
            });
        }

        public void notifyTuned(final Uri channelUri) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.TvInputService.Session.4
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onTuned(channelUri);
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInputService.TAG, "error in notifyTuned", e);
                    }
                }
            });
        }

        public void notifyTracksChanged(List<TvTrackInfo> tracks) {
            final List<TvTrackInfo> tracksCopy = new ArrayList<>(tracks);
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.TvInputService.Session.5
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onTracksChanged(tracksCopy);
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInputService.TAG, "error in notifyTracksChanged", e);
                    }
                }
            });
        }

        public void notifyTrackSelected(final int type, final String trackId) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.TvInputService.Session.6
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onTrackSelected(type, trackId);
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInputService.TAG, "error in notifyTrackSelected", e);
                    }
                }
            });
        }

        public void notifyVideoAvailable() {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.TvInputService.Session.7
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onVideoAvailable();
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInputService.TAG, "error in notifyVideoAvailable", e);
                    }
                }
            });
        }

        public void notifyVideoUnavailable(final int reason) {
            if (reason < 0 || reason > 18) {
                Log.e(TvInputService.TAG, "notifyVideoUnavailable - unknown reason: " + reason);
            }
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.TvInputService.Session.8
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onVideoUnavailable(reason);
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInputService.TAG, "error in notifyVideoUnavailable", e);
                    }
                }
            });
        }

        public void notifyVideoFreezeUpdated(final boolean isFrozen) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.TvInputService.Session.9
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onVideoFreezeUpdated(isFrozen);
                        }
                    } catch (RemoteException e) {
                        Log.e(TvInputService.TAG, "error in notifyVideoFreezeUpdated", e);
                    }
                }
            });
        }

        public void notifyAudioPresentationChanged(List<AudioPresentation> audioPresentations) {
            final List<AudioPresentation> ap = new ArrayList<>(audioPresentations);
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.TvInputService.Session.10
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onAudioPresentationsChanged(ap);
                        }
                    } catch (RemoteException e) {
                        Log.e(TvInputService.TAG, "error in notifyAudioPresentationsChanged", e);
                    }
                }
            });
        }

        public void notifyAudioPresentationSelected(final int presentationId, final int programId) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.TvInputService.Session.11
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onAudioPresentationSelected(presentationId, programId);
                        }
                    } catch (RemoteException e) {
                        Log.e(TvInputService.TAG, "error in notifyAudioPresentationSelected", e);
                    }
                }
            });
        }

        public void notifyContentAllowed() {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.TvInputService.Session.12
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onContentAllowed();
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInputService.TAG, "error in notifyContentAllowed", e);
                    }
                }
            });
        }

        public void notifyContentBlocked(final TvContentRating rating) {
            Preconditions.checkNotNull(rating);
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.TvInputService.Session.13
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onContentBlocked(rating.flattenToString());
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInputService.TAG, "error in notifyContentBlocked", e);
                    }
                }
            });
        }

        public void notifyTimeShiftStatusChanged(final int status) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.TvInputService.Session.14
                @Override // java.lang.Runnable
                public void run() {
                    Session.this.timeShiftEnablePositionTracking(status == 3);
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onTimeShiftStatusChanged(status);
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInputService.TAG, "error in notifyTimeShiftStatusChanged", e);
                    }
                }
            });
        }

        public void notifyBroadcastInfoResponse(final BroadcastInfoResponse response) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.TvInputService.Session.15
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onBroadcastInfoResponse(response);
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInputService.TAG, "error in notifyBroadcastInfoResponse", e);
                    }
                }
            });
        }

        public void notifyAdResponse(final AdResponse response) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.TvInputService.Session.16
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onAdResponse(response);
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInputService.TAG, "error in notifyAdResponse", e);
                    }
                }
            });
        }

        public void notifyAdBufferConsumed(AdBuffer buffer) {
            try {
                final AdBuffer dupBuffer = AdBuffer.dupAdBuffer(buffer);
                executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.TvInputService.Session.17
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            try {
                                if (Session.this.mSessionCallback != null) {
                                    Session.this.mSessionCallback.onAdBufferConsumed(dupBuffer);
                                }
                                if (dupBuffer == null) {
                                    return;
                                }
                            } catch (RemoteException e) {
                                Log.w(TvInputService.TAG, "error in notifyAdBufferConsumed", e);
                                if (dupBuffer == null) {
                                    return;
                                }
                            }
                            dupBuffer.getSharedMemory().close();
                        } catch (Throwable th) {
                            if (dupBuffer != null) {
                                dupBuffer.getSharedMemory().close();
                            }
                            throw th;
                        }
                    }
                });
            } catch (IOException e) {
                Log.w(TvInputService.TAG, "dup AdBuffer error in notifyAdBufferConsumed:", e);
            }
        }

        public void notifyTvMessage(final int type, final Bundle data) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.TvInputService.Session.18
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onTvMessage(type, data);
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInputService.TAG, "error in notifyTvMessage", e);
                    }
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void notifyTimeShiftStartPositionChanged(final long timeMs) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.TvInputService.Session.19
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onTimeShiftStartPositionChanged(timeMs);
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInputService.TAG, "error in notifyTimeShiftStartPositionChanged", e);
                    }
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void notifyTimeShiftCurrentPositionChanged(final long timeMs) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.TvInputService.Session.20
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onTimeShiftCurrentPositionChanged(timeMs);
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInputService.TAG, "error in notifyTimeShiftCurrentPositionChanged", e);
                    }
                }
            });
        }

        public void notifyAitInfoUpdated(final AitInfo aitInfo) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.TvInputService.Session.21
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onAitInfoUpdated(aitInfo);
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInputService.TAG, "error in notifyAitInfoUpdated", e);
                    }
                }
            });
        }

        public void notifyTimeShiftMode(final int mode) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.TvInputService.Session.22
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onTimeShiftMode(mode);
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInputService.TAG, "error in notifyTimeShiftMode", e);
                    }
                }
            });
        }

        public void notifyAvailableSpeeds(final float[] speeds) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.TvInputService.Session.23
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Arrays.sort(speeds);
                            Session.this.mSessionCallback.onAvailableSpeeds(speeds);
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInputService.TAG, "error in notifyAvailableSpeeds", e);
                    }
                }
            });
        }

        public void notifySignalStrength(final int strength) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.TvInputService.Session.24
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onSignalStrength(strength);
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInputService.TAG, "error in notifySignalStrength", e);
                    }
                }
            });
        }

        public void notifyCueingMessageAvailability(final boolean available) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.TvInputService.Session.25
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onCueingMessageAvailability(available);
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInputService.TAG, "error in notifyCueingMessageAvailability", e);
                    }
                }
            });
        }

        public void sendTvInputSessionData(final String type, final Bundle data) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.TvInputService.Session.26
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onTvInputSessionData(type, data);
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInputService.TAG, "error in sendTvInputSessionData", e);
                    }
                }
            });
        }

        public void layoutSurface(final int left, final int top, final int right, final int bottom) {
            if (left > right || top > bottom) {
                throw new IllegalArgumentException("Invalid parameter");
            }
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.TvInputService.Session.27
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onLayoutSurface(left, top, right, bottom);
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInputService.TAG, "error in layoutSurface", e);
                    }
                }
            });
        }

        @SystemApi
        public void onSetMain(boolean isMain) {
        }

        public void onSurfaceChanged(int format, int width, int height) {
        }

        public void onOverlayViewSizeChanged(int width, int height) {
        }

        public void onRequestBroadcastInfo(BroadcastInfoRequest request) {
        }

        public void onRemoveBroadcastInfo(int requestId) {
        }

        public void onRequestAd(AdRequest request) {
        }

        public void onAdBufferReady(AdBuffer buffer) {
        }

        public void onTvAdSessionData(String type, Bundle data) {
        }

        public boolean onTune(Uri channelUri, Bundle params) {
            return onTune(channelUri);
        }

        public void onUnblockContent(TvContentRating unblockedRating) {
        }

        public boolean onSelectTrack(int type, String trackId) {
            return false;
        }

        public void onSetInteractiveAppNotificationEnabled(boolean enabled) {
        }

        public boolean onSelectAudioPresentation(int presentationId, int programId) {
            return false;
        }

        public void onAppPrivateCommand(String action, Bundle data) {
        }

        public View onCreateOverlayView() {
            return null;
        }

        public void onSetTvMessageEnabled(int type, boolean enabled) {
        }

        public void onTvMessage(int type, Bundle data) {
        }

        public void onStopPlayback(int mode) {
        }

        public void onResumePlayback() {
        }

        public void onSetVideoFrozen(boolean isFrozen) {
        }

        public void onTimeShiftPlay(Uri recordedProgramUri) {
        }

        public void onTimeShiftPause() {
        }

        public void onTimeShiftResume() {
        }

        public void onTimeShiftSeekTo(long timeMs) {
        }

        public void onTimeShiftSetPlaybackParams(PlaybackParams params) {
        }

        public void onTimeShiftSetMode(int mode) {
        }

        public long onTimeShiftGetStartPosition() {
            return Long.MIN_VALUE;
        }

        public long onTimeShiftGetCurrentPosition() {
            return Long.MIN_VALUE;
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

        void release() {
            onRelease();
            if (this.mSurface != null) {
                this.mSurface.release();
                this.mSurface = null;
            }
            synchronized (this.mLock) {
                this.mSessionCallback = null;
                this.mPendingActions.clear();
            }
            removeOverlayView(true);
            this.mHandler.removeCallbacks(this.mTimeShiftPositionTrackingRunnable);
        }

        void setMain(boolean isMain) {
            onSetMain(isMain);
        }

        void setSurface(Surface surface) {
            onSetSurface(surface);
            if (this.mSurface != null) {
                this.mSurface.release();
            }
            this.mSurface = surface;
        }

        void dispatchSurfaceChanged(int format, int width, int height) {
            onSurfaceChanged(format, width, height);
        }

        void setStreamVolume(float volume) {
            onSetStreamVolume(volume);
        }

        void tune(Uri channelUri, Bundle params) {
            this.mCurrentPositionMs = Long.MIN_VALUE;
            onTune(channelUri, params);
        }

        void setCaptionEnabled(boolean enabled) {
            onSetCaptionEnabled(enabled);
        }

        void selectAudioPresentation(int presentationId, int programId) {
            onSelectAudioPresentation(presentationId, programId);
        }

        void selectTrack(int type, String trackId) {
            onSelectTrack(type, trackId);
        }

        void unblockContent(String unblockedRating) {
            onUnblockContent(TvContentRating.unflattenFromString(unblockedRating));
        }

        void setInteractiveAppNotificationEnabled(boolean enabled) {
            onSetInteractiveAppNotificationEnabled(enabled);
        }

        void setTvMessageEnabled(int type, boolean enabled) {
            onSetTvMessageEnabled(type, enabled);
        }

        void appPrivateCommand(String action, Bundle data) {
            onAppPrivateCommand(action, data);
        }

        void createOverlayView(IBinder windowToken, Rect frame) {
            if (this.mOverlayViewContainer != null) {
                removeOverlayView(false);
            }
            this.mWindowToken = windowToken;
            this.mOverlayFrame = frame;
            onOverlayViewSizeChanged(frame.right - frame.left, frame.bottom - frame.top);
            if (!this.mOverlayViewEnabled) {
                return;
            }
            this.mOverlayView = onCreateOverlayView();
            if (this.mOverlayView == null) {
                return;
            }
            if (this.mOverlayViewCleanUpTask != null) {
                this.mOverlayViewCleanUpTask.cancel(true);
                this.mOverlayViewCleanUpTask = null;
            }
            this.mOverlayViewContainer = new FrameLayout(this.mContext.getApplicationContext());
            this.mOverlayViewContainer.addView(this.mOverlayView);
            int flags = ActivityManager.isHighEndGfx() ? 536 | 16777216 : 536;
            this.mWindowParams = new WindowManager.LayoutParams(frame.right - frame.left, frame.bottom - frame.top, frame.left, frame.top, 1004, flags, -2);
            this.mWindowParams.privateFlags |= 64;
            this.mWindowParams.gravity = 8388659;
            this.mWindowParams.token = windowToken;
            this.mWindowManager.addView(this.mOverlayViewContainer, this.mWindowParams);
        }

        void relayoutOverlayView(Rect frame) {
            if (this.mOverlayFrame == null || this.mOverlayFrame.width() != frame.width() || this.mOverlayFrame.height() != frame.height()) {
                onOverlayViewSizeChanged(frame.right - frame.left, frame.bottom - frame.top);
            }
            this.mOverlayFrame = frame;
            if (!this.mOverlayViewEnabled || this.mOverlayViewContainer == null) {
                return;
            }
            this.mWindowParams.x = frame.left;
            this.mWindowParams.y = frame.top;
            this.mWindowParams.width = frame.right - frame.left;
            this.mWindowParams.height = frame.bottom - frame.top;
            this.mWindowManager.updateViewLayout(this.mOverlayViewContainer, this.mWindowParams);
        }

        void removeOverlayView(boolean clearWindowToken) {
            if (clearWindowToken) {
                this.mWindowToken = null;
                this.mOverlayFrame = null;
            }
            if (this.mOverlayViewContainer != null) {
                this.mOverlayViewContainer.removeView(this.mOverlayView);
                this.mOverlayView = null;
                this.mWindowManager.removeView(this.mOverlayViewContainer);
                this.mOverlayViewContainer = null;
                this.mWindowParams = null;
            }
        }

        void stopPlayback(int mode) {
            onStopPlayback(mode);
        }

        void resumePlayback() {
            onResumePlayback();
        }

        void setVideoFrozen(boolean isFrozen) {
            onSetVideoFrozen(isFrozen);
        }

        void timeShiftPlay(Uri recordedProgramUri) {
            this.mCurrentPositionMs = 0L;
            onTimeShiftPlay(recordedProgramUri);
        }

        void timeShiftPause() {
            onTimeShiftPause();
        }

        void timeShiftResume() {
            onTimeShiftResume();
        }

        void timeShiftSeekTo(long timeMs) {
            onTimeShiftSeekTo(timeMs);
        }

        void timeShiftSetPlaybackParams(PlaybackParams params) {
            onTimeShiftSetPlaybackParams(params);
        }

        void timeShiftSetMode(int mode) {
            onTimeShiftSetMode(mode);
        }

        void timeShiftEnablePositionTracking(boolean enable) {
            if (enable) {
                this.mHandler.post(this.mTimeShiftPositionTrackingRunnable);
                return;
            }
            this.mHandler.removeCallbacks(this.mTimeShiftPositionTrackingRunnable);
            this.mStartPositionMs = Long.MIN_VALUE;
            this.mCurrentPositionMs = Long.MIN_VALUE;
        }

        void scheduleOverlayViewCleanup() {
            View overlayViewParent = this.mOverlayViewContainer;
            if (overlayViewParent != null) {
                this.mOverlayViewCleanUpTask = new OverlayViewCleanUpTask();
                this.mOverlayViewCleanUpTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, overlayViewParent);
            }
        }

        void requestBroadcastInfo(BroadcastInfoRequest request) {
            onRequestBroadcastInfo(request);
        }

        void removeBroadcastInfo(int requestId) {
            onRemoveBroadcastInfo(requestId);
        }

        void requestAd(AdRequest request) {
            onRequestAd(request);
        }

        void notifyAdBufferReady(AdBuffer buffer) {
            onAdBufferReady(buffer);
        }

        void notifyTvAdSessionData(String type, Bundle data) {
            onTvAdSessionData(type, data);
        }

        void onTvMessageReceived(int type, Bundle data) {
            onTvMessage(type, data);
        }

        int dispatchInputEvent(InputEvent event, InputEventReceiver receiver) {
            boolean isNavigationKey = false;
            boolean skipDispatchToOverlayView = false;
            if (event instanceof KeyEvent) {
                KeyEvent keyEvent = (KeyEvent) event;
                if (keyEvent.dispatch(this, this.mDispatcherState, this)) {
                    return 1;
                }
                isNavigationKey = TvInputService.isNavigationKey(keyEvent.getKeyCode());
                skipDispatchToOverlayView = KeyEvent.isMediaSessionKey(keyEvent.getKeyCode()) || keyEvent.getKeyCode() == 222;
            } else if (event instanceof MotionEvent) {
                MotionEvent motionEvent = (MotionEvent) event;
                int source = motionEvent.getSource();
                if (motionEvent.isTouchEvent()) {
                    if (onTouchEvent(motionEvent)) {
                        return 1;
                    }
                } else if ((source & 4) != 0) {
                    if (onTrackballEvent(motionEvent)) {
                        return 1;
                    }
                } else if (onGenericMotionEvent(motionEvent)) {
                    return 1;
                }
            }
            if (this.mOverlayViewContainer == null || !this.mOverlayViewContainer.isAttachedToWindow() || skipDispatchToOverlayView) {
                return 0;
            }
            if (!this.mOverlayViewContainer.hasWindowFocus()) {
                ViewRootImpl viewRoot = this.mOverlayViewContainer.getViewRootImpl();
                viewRoot.windowFocusChanged(true);
            }
            if (isNavigationKey && this.mOverlayViewContainer.hasFocusable()) {
                this.mOverlayViewContainer.getViewRootImpl().dispatchInputEvent(event);
                return 1;
            }
            this.mOverlayViewContainer.getViewRootImpl().dispatchInputEvent(event, receiver);
            return -1;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void initialize(ITvInputSessionCallback callback) {
            synchronized (this.mLock) {
                this.mSessionCallback = callback;
                for (Runnable runnable : this.mPendingActions) {
                    runnable.run();
                }
                this.mPendingActions.clear();
            }
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

        private final class TimeShiftPositionTrackingRunnable implements Runnable {
            private TimeShiftPositionTrackingRunnable() {
            }

            @Override // java.lang.Runnable
            public void run() {
                long startPositionMs = Session.this.onTimeShiftGetStartPosition();
                if (Session.this.mStartPositionMs == Long.MIN_VALUE || Session.this.mStartPositionMs != startPositionMs) {
                    Session.this.mStartPositionMs = startPositionMs;
                    Session.this.notifyTimeShiftStartPositionChanged(startPositionMs);
                }
                long currentPositionMs = Session.this.onTimeShiftGetCurrentPosition();
                if (currentPositionMs < Session.this.mStartPositionMs) {
                    Log.w(TvInputService.TAG, "Current position (" + currentPositionMs + ") cannot be earlier than start position (" + Session.this.mStartPositionMs + "). Reset to the start position.");
                    currentPositionMs = Session.this.mStartPositionMs;
                }
                if (Session.this.mCurrentPositionMs == Long.MIN_VALUE || Session.this.mCurrentPositionMs != currentPositionMs) {
                    Session.this.mCurrentPositionMs = currentPositionMs;
                    Session.this.notifyTimeShiftCurrentPositionChanged(currentPositionMs);
                }
                Session.this.mHandler.removeCallbacks(Session.this.mTimeShiftPositionTrackingRunnable);
                Session.this.mHandler.postDelayed(Session.this.mTimeShiftPositionTrackingRunnable, 1000L);
            }
        }
    }

    private static final class OverlayViewCleanUpTask extends AsyncTask<View, Void, Void> {
        private OverlayViewCleanUpTask() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public Void doInBackground(View... views) {
            View overlayViewParent = views[0];
            try {
                Thread.sleep(5000L);
                if (!isCancelled() && overlayViewParent.isAttachedToWindow()) {
                    Log.e(TvInputService.TAG, "Time out on releasing overlay view. Killing " + overlayViewParent.getContext().getPackageName());
                    Process.killProcess(Process.myPid());
                }
                return null;
            } catch (InterruptedException e) {
                return null;
            }
        }
    }

    public static abstract class RecordingSession {
        final Handler mHandler;
        private final Object mLock = new Object();
        private final List<Runnable> mPendingActions = new ArrayList();
        private ITvInputSessionCallback mSessionCallback;

        public abstract void onRelease();

        public abstract void onStartRecording(Uri uri);

        public abstract void onStopRecording();

        public abstract void onTune(Uri uri);

        public RecordingSession(Context context) {
            this.mHandler = new Handler(context.getMainLooper());
        }

        public void notifyTuned(final Uri channelUri) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.TvInputService.RecordingSession.1
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (RecordingSession.this.mSessionCallback != null) {
                            RecordingSession.this.mSessionCallback.onTuned(channelUri);
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInputService.TAG, "error in notifyTuned", e);
                    }
                }
            });
        }

        public void notifyRecordingStopped(final Uri recordedProgramUri) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.TvInputService.RecordingSession.2
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (RecordingSession.this.mSessionCallback != null) {
                            RecordingSession.this.mSessionCallback.onRecordingStopped(recordedProgramUri);
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInputService.TAG, "error in notifyRecordingStopped", e);
                    }
                }
            });
        }

        public void notifyError(int error) {
            if (error < 0 || error > 2) {
                Log.w(TvInputService.TAG, "notifyError - invalid error code (" + error + ") is changed to RECORDING_ERROR_UNKNOWN.");
                error = 0;
            }
            final int validError = error;
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.TvInputService.RecordingSession.3
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (RecordingSession.this.mSessionCallback != null) {
                            RecordingSession.this.mSessionCallback.onError(validError);
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInputService.TAG, "error in notifyError", e);
                    }
                }
            });
        }

        @SystemApi
        public void notifySessionEvent(final String eventType, final Bundle eventArgs) {
            Preconditions.checkNotNull(eventType);
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.TvInputService.RecordingSession.4
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (RecordingSession.this.mSessionCallback != null) {
                            RecordingSession.this.mSessionCallback.onSessionEvent(eventType, eventArgs);
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInputService.TAG, "error in sending event (event=" + eventType + NavigationBarInflaterView.KEY_CODE_END, e);
                    }
                }
            });
        }

        public void onTune(Uri channelUri, Bundle params) {
            onTune(channelUri);
        }

        public void onStartRecording(Uri programUri, Bundle params) {
            onStartRecording(programUri);
        }

        public void onPauseRecording(Bundle params) {
        }

        public void onResumeRecording(Bundle params) {
        }

        public void onAppPrivateCommand(String action, Bundle data) {
        }

        void tune(Uri channelUri, Bundle params) {
            onTune(channelUri, params);
        }

        void release() {
            onRelease();
        }

        void startRecording(Uri programUri, Bundle params) {
            onStartRecording(programUri, params);
        }

        void stopRecording() {
            onStopRecording();
        }

        void pauseRecording(Bundle params) {
            onPauseRecording(params);
        }

        void resumeRecording(Bundle params) {
            onResumeRecording(params);
        }

        void appPrivateCommand(String action, Bundle data) {
            onAppPrivateCommand(action, data);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void initialize(ITvInputSessionCallback callback) {
            synchronized (this.mLock) {
                this.mSessionCallback = callback;
                for (Runnable runnable : this.mPendingActions) {
                    runnable.run();
                }
                this.mPendingActions.clear();
            }
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
    }

    public static abstract class HardwareSession extends Session {
        private TvInputManager.Session mHardwareSession;
        private final TvInputManager.SessionCallback mHardwareSessionCallback;
        private ITvInputSession mProxySession;
        private ITvInputSessionCallback mProxySessionCallback;
        private Handler mServiceHandler;

        public abstract String getHardwareInputId();

        public HardwareSession(Context context) {
            super(context);
            this.mHardwareSessionCallback = new TvInputManager.SessionCallback() { // from class: android.media.tv.TvInputService.HardwareSession.1
                @Override // android.media.tv.TvInputManager.SessionCallback
                public void onSessionCreated(TvInputManager.Session session) {
                    HardwareSession.this.mHardwareSession = session;
                    SomeArgs args = SomeArgs.obtain();
                    if (session != null) {
                        args.arg1 = HardwareSession.this;
                        args.arg2 = HardwareSession.this.mProxySession;
                        args.arg3 = HardwareSession.this.mProxySessionCallback;
                        args.arg4 = session.getToken();
                        session.tune(TvContract.buildChannelUriForPassthroughInput(HardwareSession.this.getHardwareInputId()));
                    } else {
                        args.arg1 = null;
                        args.arg2 = null;
                        args.arg3 = HardwareSession.this.mProxySessionCallback;
                        args.arg4 = null;
                        HardwareSession.this.onRelease();
                    }
                    HardwareSession.this.mServiceHandler.obtainMessage(2, args).sendToTarget();
                }

                @Override // android.media.tv.TvInputManager.SessionCallback
                public void onVideoAvailable(TvInputManager.Session session) {
                    if (HardwareSession.this.mHardwareSession == session) {
                        HardwareSession.this.onHardwareVideoAvailable();
                    }
                }

                @Override // android.media.tv.TvInputManager.SessionCallback
                public void onVideoUnavailable(TvInputManager.Session session, int reason) {
                    if (HardwareSession.this.mHardwareSession == session) {
                        HardwareSession.this.onHardwareVideoUnavailable(reason);
                    }
                }
            };
        }

        @Override // android.media.tv.TvInputService.Session
        public final boolean onSetSurface(Surface surface) {
            Log.e(TvInputService.TAG, "onSetSurface() should not be called in HardwareProxySession.");
            return false;
        }

        public void onHardwareVideoAvailable() {
        }

        public void onHardwareVideoUnavailable(int reason) {
        }

        @Override // android.media.tv.TvInputService.Session
        void release() {
            if (this.mHardwareSession != null) {
                this.mHardwareSession.release();
                this.mHardwareSession = null;
            }
            super.release();
        }
    }

    public static boolean isNavigationKey(int keyCode) {
        switch (keyCode) {
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 61:
            case 62:
            case 66:
            case 92:
            case 93:
            case 122:
            case 123:
                return true;
            default:
                return false;
        }
    }

    private final class ServiceHandler extends Handler {
        private static final int DO_ADD_HARDWARE_INPUT = 4;
        private static final int DO_ADD_HDMI_INPUT = 6;
        private static final int DO_CREATE_RECORDING_SESSION = 3;
        private static final int DO_CREATE_SESSION = 1;
        private static final int DO_NOTIFY_SESSION_CREATED = 2;
        private static final int DO_REMOVE_HARDWARE_INPUT = 5;
        private static final int DO_REMOVE_HDMI_INPUT = 7;
        private static final int DO_UPDATE_HDMI_INPUT = 8;

        private ServiceHandler() {
        }

        private void broadcastAddHardwareInput(int deviceId, TvInputInfo inputInfo) {
            int n = TvInputService.this.mCallbacks.beginBroadcast();
            for (int i = 0; i < n; i++) {
                try {
                    ((ITvInputServiceCallback) TvInputService.this.mCallbacks.getBroadcastItem(i)).addHardwareInput(deviceId, inputInfo);
                } catch (RemoteException e) {
                    Log.e(TvInputService.TAG, "error in broadcastAddHardwareInput", e);
                }
            }
            TvInputService.this.mCallbacks.finishBroadcast();
        }

        private void broadcastAddHdmiInput(int id, TvInputInfo inputInfo) {
            int n = TvInputService.this.mCallbacks.beginBroadcast();
            for (int i = 0; i < n; i++) {
                try {
                    ((ITvInputServiceCallback) TvInputService.this.mCallbacks.getBroadcastItem(i)).addHdmiInput(id, inputInfo);
                } catch (RemoteException e) {
                    Log.e(TvInputService.TAG, "error in broadcastAddHdmiInput", e);
                }
            }
            TvInputService.this.mCallbacks.finishBroadcast();
        }

        private void broadcastRemoveHardwareInput(String inputId) {
            int n = TvInputService.this.mCallbacks.beginBroadcast();
            for (int i = 0; i < n; i++) {
                try {
                    ((ITvInputServiceCallback) TvInputService.this.mCallbacks.getBroadcastItem(i)).removeHardwareInput(inputId);
                } catch (RemoteException e) {
                    Log.e(TvInputService.TAG, "error in broadcastRemoveHardwareInput", e);
                }
            }
            TvInputService.this.mCallbacks.finishBroadcast();
        }

        @Override // android.os.Handler
        public final void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    SomeArgs args = (SomeArgs) msg.obj;
                    InputChannel channel = (InputChannel) args.arg1;
                    ITvInputSessionCallback cb = (ITvInputSessionCallback) args.arg2;
                    String inputId = (String) args.arg3;
                    String sessionId = (String) args.arg4;
                    AttributionSource tvAppAttributionSource = (AttributionSource) args.arg5;
                    args.recycle();
                    Session sessionImpl = TvInputService.this.onCreateSession(inputId, sessionId, tvAppAttributionSource);
                    if (sessionImpl == null) {
                        try {
                            cb.onSessionCreated(null, null);
                            break;
                        } catch (RemoteException e) {
                            Log.e(TvInputService.TAG, "error in onSessionCreated", e);
                            return;
                        }
                    } else {
                        ITvInputSession stub = new ITvInputSessionWrapper(TvInputService.this, sessionImpl, channel);
                        if (sessionImpl instanceof HardwareSession) {
                            HardwareSession proxySession = (HardwareSession) sessionImpl;
                            String hardwareInputId = proxySession.getHardwareInputId();
                            if (TextUtils.isEmpty(hardwareInputId) || !TvInputService.this.isPassthroughInput(hardwareInputId)) {
                                if (TextUtils.isEmpty(hardwareInputId)) {
                                    Log.w(TvInputService.TAG, "Hardware input id is not setup yet.");
                                } else {
                                    Log.w(TvInputService.TAG, "Invalid hardware input id : " + hardwareInputId);
                                }
                                sessionImpl.onRelease();
                                try {
                                    cb.onSessionCreated(null, null);
                                    break;
                                } catch (RemoteException e2) {
                                    Log.e(TvInputService.TAG, "error in onSessionCreated", e2);
                                    return;
                                }
                            } else {
                                proxySession.mProxySession = stub;
                                proxySession.mProxySessionCallback = cb;
                                proxySession.mServiceHandler = TvInputService.this.mServiceHandler;
                                TvInputManager manager = (TvInputManager) TvInputService.this.getSystemService(Context.TV_INPUT_SERVICE);
                                manager.createSession(hardwareInputId, tvAppAttributionSource, proxySession.mHardwareSessionCallback, TvInputService.this.mServiceHandler);
                                break;
                            }
                        } else {
                            SomeArgs someArgs = SomeArgs.obtain();
                            someArgs.arg1 = sessionImpl;
                            someArgs.arg2 = stub;
                            someArgs.arg3 = cb;
                            someArgs.arg4 = null;
                            TvInputService.this.mServiceHandler.obtainMessage(2, someArgs).sendToTarget();
                            break;
                        }
                    }
                    break;
                case 2:
                    SomeArgs args2 = (SomeArgs) msg.obj;
                    Session sessionImpl2 = (Session) args2.arg1;
                    ITvInputSession stub2 = (ITvInputSession) args2.arg2;
                    ITvInputSessionCallback cb2 = (ITvInputSessionCallback) args2.arg3;
                    IBinder hardwareSessionToken = (IBinder) args2.arg4;
                    try {
                        cb2.onSessionCreated(stub2, hardwareSessionToken);
                    } catch (RemoteException e3) {
                        Log.e(TvInputService.TAG, "error in onSessionCreated", e3);
                    }
                    if (sessionImpl2 != null) {
                        sessionImpl2.initialize(cb2);
                    }
                    args2.recycle();
                    break;
                case 3:
                    SomeArgs args3 = (SomeArgs) msg.obj;
                    ITvInputSessionCallback cb3 = (ITvInputSessionCallback) args3.arg1;
                    String inputId2 = (String) args3.arg2;
                    String sessionId2 = (String) args3.arg3;
                    args3.recycle();
                    RecordingSession recordingSessionImpl = TvInputService.this.onCreateRecordingSession(inputId2, sessionId2);
                    if (recordingSessionImpl == null) {
                        try {
                            cb3.onSessionCreated(null, null);
                            break;
                        } catch (RemoteException e4) {
                            Log.e(TvInputService.TAG, "error in onSessionCreated", e4);
                            return;
                        }
                    } else {
                        try {
                            cb3.onSessionCreated(new ITvInputSessionWrapper(TvInputService.this, recordingSessionImpl), null);
                        } catch (RemoteException e5) {
                            Log.e(TvInputService.TAG, "error in onSessionCreated", e5);
                        }
                        recordingSessionImpl.initialize(cb3);
                        break;
                    }
                case 4:
                    TvInputHardwareInfo hardwareInfo = (TvInputHardwareInfo) msg.obj;
                    TvInputInfo inputInfo = TvInputService.this.onHardwareAdded(hardwareInfo);
                    if (inputInfo != null) {
                        broadcastAddHardwareInput(hardwareInfo.getDeviceId(), inputInfo);
                        break;
                    }
                    break;
                case 5:
                    String inputId3 = TvInputService.this.onHardwareRemoved((TvInputHardwareInfo) msg.obj);
                    if (inputId3 != null) {
                        broadcastRemoveHardwareInput(inputId3);
                        break;
                    }
                    break;
                case 6:
                    HdmiDeviceInfo deviceInfo = (HdmiDeviceInfo) msg.obj;
                    TvInputInfo inputInfo2 = TvInputService.this.onHdmiDeviceAdded(deviceInfo);
                    if (inputInfo2 != null) {
                        broadcastAddHdmiInput(deviceInfo.getId(), inputInfo2);
                        break;
                    }
                    break;
                case 7:
                    String inputId4 = TvInputService.this.onHdmiDeviceRemoved((HdmiDeviceInfo) msg.obj);
                    if (inputId4 != null) {
                        broadcastRemoveHardwareInput(inputId4);
                        break;
                    }
                    break;
                case 8:
                    TvInputService.this.onHdmiDeviceUpdated((HdmiDeviceInfo) msg.obj);
                    break;
                default:
                    Log.w(TvInputService.TAG, "Unhandled message code: " + msg.what);
                    break;
            }
        }
    }
}
