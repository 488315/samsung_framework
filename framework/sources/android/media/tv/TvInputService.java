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

/* loaded from: classes2.dex */
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
    /* loaded from: classes2.dex */
    public @interface PriorityHintUseCaseType {
    }

    public abstract Session onCreateSession(String str);

    /* renamed from: android.media.tv.TvInputService$1 */
    /* loaded from: classes2.dex */
    class AnonymousClass1 extends ITvInputService.Stub {
        AnonymousClass1() {
        }

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
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        ITvInputService.Stub tvInputServiceBinder = new ITvInputService.Stub() { // from class: android.media.tv.TvInputService.1
            AnonymousClass1() {
            }

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

    public boolean isPassthroughInput(String inputId) {
        if (this.mTvInputManager == null) {
            this.mTvInputManager = (TvInputManager) getSystemService(Context.TV_INPUT_SERVICE);
        }
        TvInputInfo info = this.mTvInputManager.getTvInputInfo(inputId);
        return info != null && info.isPassthroughInput();
    }

    /* loaded from: classes2.dex */
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

        /* renamed from: android.media.tv.TvInputService$Session$1 */
        /* loaded from: classes2.dex */
        class AnonymousClass1 implements Runnable {
            final /* synthetic */ boolean val$enable;

            AnonymousClass1(boolean z) {
                enable = z;
            }

            @Override // java.lang.Runnable
            public void run() {
                if (enable == Session.this.mOverlayViewEnabled) {
                    return;
                }
                Session.this.mOverlayViewEnabled = enable;
                if (enable) {
                    if (Session.this.mWindowToken != null) {
                        Session session = Session.this;
                        session.createOverlayView(session.mWindowToken, Session.this.mOverlayFrame);
                        return;
                    }
                    return;
                }
                Session.this.removeOverlayView(false);
            }
        }

        public void setOverlayViewEnabled(boolean enable) {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.TvInputService.Session.1
                final /* synthetic */ boolean val$enable;

                AnonymousClass1(boolean enable2) {
                    enable = enable2;
                }

                @Override // java.lang.Runnable
                public void run() {
                    if (enable == Session.this.mOverlayViewEnabled) {
                        return;
                    }
                    Session.this.mOverlayViewEnabled = enable;
                    if (enable) {
                        if (Session.this.mWindowToken != null) {
                            Session session = Session.this;
                            session.createOverlayView(session.mWindowToken, Session.this.mOverlayFrame);
                            return;
                        }
                        return;
                    }
                    Session.this.removeOverlayView(false);
                }
            });
        }

        /* renamed from: android.media.tv.TvInputService$Session$2 */
        /* loaded from: classes2.dex */
        class AnonymousClass2 implements Runnable {
            final /* synthetic */ Bundle val$eventArgs;
            final /* synthetic */ String val$eventType;

            AnonymousClass2(String str, Bundle bundle) {
                eventType = str;
                eventArgs = bundle;
            }

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
        }

        @SystemApi
        public void notifySessionEvent(String eventType, Bundle eventArgs) {
            Preconditions.checkNotNull(eventType);
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.TvInputService.Session.2
                final /* synthetic */ Bundle val$eventArgs;
                final /* synthetic */ String val$eventType;

                AnonymousClass2(String eventType2, Bundle eventArgs2) {
                    eventType = eventType2;
                    eventArgs = eventArgs2;
                }

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

        /* renamed from: android.media.tv.TvInputService$Session$3 */
        /* loaded from: classes2.dex */
        class AnonymousClass3 implements Runnable {
            final /* synthetic */ Uri val$channelUri;

            AnonymousClass3(Uri uri) {
                channelUri = uri;
            }

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
        }

        public void notifyChannelRetuned(Uri channelUri) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.TvInputService.Session.3
                final /* synthetic */ Uri val$channelUri;

                AnonymousClass3(Uri channelUri2) {
                    channelUri = channelUri2;
                }

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

        /* renamed from: android.media.tv.TvInputService$Session$4 */
        /* loaded from: classes2.dex */
        class AnonymousClass4 implements Runnable {
            final /* synthetic */ Uri val$channelUri;

            AnonymousClass4(Uri uri) {
                channelUri = uri;
            }

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
        }

        public void notifyTuned(Uri channelUri) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.TvInputService.Session.4
                final /* synthetic */ Uri val$channelUri;

                AnonymousClass4(Uri channelUri2) {
                    channelUri = channelUri2;
                }

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

        /* renamed from: android.media.tv.TvInputService$Session$5 */
        /* loaded from: classes2.dex */
        class AnonymousClass5 implements Runnable {
            final /* synthetic */ List val$tracksCopy;

            AnonymousClass5(List list) {
                tracksCopy = list;
            }

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
        }

        public void notifyTracksChanged(List<TvTrackInfo> tracks) {
            List<TvTrackInfo> tracksCopy = new ArrayList<>(tracks);
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.TvInputService.Session.5
                final /* synthetic */ List val$tracksCopy;

                AnonymousClass5(List tracksCopy2) {
                    tracksCopy = tracksCopy2;
                }

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

        /* renamed from: android.media.tv.TvInputService$Session$6 */
        /* loaded from: classes2.dex */
        class AnonymousClass6 implements Runnable {
            final /* synthetic */ String val$trackId;
            final /* synthetic */ int val$type;

            AnonymousClass6(int i, String str) {
                type = i;
                trackId = str;
            }

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
        }

        public void notifyTrackSelected(int type, String trackId) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.TvInputService.Session.6
                final /* synthetic */ String val$trackId;
                final /* synthetic */ int val$type;

                AnonymousClass6(int type2, String trackId2) {
                    type = type2;
                    trackId = trackId2;
                }

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

        /* renamed from: android.media.tv.TvInputService$Session$7 */
        /* loaded from: classes2.dex */
        class AnonymousClass7 implements Runnable {
            AnonymousClass7() {
            }

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
        }

        public void notifyVideoAvailable() {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.TvInputService.Session.7
                AnonymousClass7() {
                }

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

        public void notifyVideoUnavailable(int reason) {
            if (reason < 0 || reason > 18) {
                Log.e(TvInputService.TAG, "notifyVideoUnavailable - unknown reason: " + reason);
            }
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.TvInputService.Session.8
                final /* synthetic */ int val$reason;

                AnonymousClass8(int reason2) {
                    reason = reason2;
                }

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

        /* renamed from: android.media.tv.TvInputService$Session$8 */
        /* loaded from: classes2.dex */
        class AnonymousClass8 implements Runnable {
            final /* synthetic */ int val$reason;

            AnonymousClass8(int reason2) {
                reason = reason2;
            }

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
        }

        /* renamed from: android.media.tv.TvInputService$Session$9 */
        /* loaded from: classes2.dex */
        class AnonymousClass9 implements Runnable {
            final /* synthetic */ List val$ap;

            AnonymousClass9(List list) {
                ap = list;
            }

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
        }

        public void notifyAudioPresentationChanged(List<AudioPresentation> audioPresentations) {
            List<AudioPresentation> ap = new ArrayList<>(audioPresentations);
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.TvInputService.Session.9
                final /* synthetic */ List val$ap;

                AnonymousClass9(List ap2) {
                    ap = ap2;
                }

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

        /* renamed from: android.media.tv.TvInputService$Session$10 */
        /* loaded from: classes2.dex */
        class AnonymousClass10 implements Runnable {
            final /* synthetic */ int val$presentationId;
            final /* synthetic */ int val$programId;

            AnonymousClass10(int i, int i2) {
                presentationId = i;
                programId = i2;
            }

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
        }

        public void notifyAudioPresentationSelected(int presentationId, int programId) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.TvInputService.Session.10
                final /* synthetic */ int val$presentationId;
                final /* synthetic */ int val$programId;

                AnonymousClass10(int presentationId2, int programId2) {
                    presentationId = presentationId2;
                    programId = programId2;
                }

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

        /* renamed from: android.media.tv.TvInputService$Session$11 */
        /* loaded from: classes2.dex */
        class AnonymousClass11 implements Runnable {
            AnonymousClass11() {
            }

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
        }

        public void notifyContentAllowed() {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.TvInputService.Session.11
                AnonymousClass11() {
                }

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

        /* renamed from: android.media.tv.TvInputService$Session$12 */
        /* loaded from: classes2.dex */
        class AnonymousClass12 implements Runnable {
            final /* synthetic */ TvContentRating val$rating;

            AnonymousClass12(TvContentRating tvContentRating) {
                rating = tvContentRating;
            }

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
        }

        public void notifyContentBlocked(TvContentRating rating) {
            Preconditions.checkNotNull(rating);
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.TvInputService.Session.12
                final /* synthetic */ TvContentRating val$rating;

                AnonymousClass12(TvContentRating rating2) {
                    rating = rating2;
                }

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

        /* renamed from: android.media.tv.TvInputService$Session$13 */
        /* loaded from: classes2.dex */
        class AnonymousClass13 implements Runnable {
            final /* synthetic */ int val$status;

            AnonymousClass13(int i) {
                status = i;
            }

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
        }

        public void notifyTimeShiftStatusChanged(int status) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.TvInputService.Session.13
                final /* synthetic */ int val$status;

                AnonymousClass13(int status2) {
                    status = status2;
                }

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

        /* renamed from: android.media.tv.TvInputService$Session$14 */
        /* loaded from: classes2.dex */
        class AnonymousClass14 implements Runnable {
            final /* synthetic */ BroadcastInfoResponse val$response;

            AnonymousClass14(BroadcastInfoResponse broadcastInfoResponse) {
                response = broadcastInfoResponse;
            }

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
        }

        public void notifyBroadcastInfoResponse(BroadcastInfoResponse response) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.TvInputService.Session.14
                final /* synthetic */ BroadcastInfoResponse val$response;

                AnonymousClass14(BroadcastInfoResponse response2) {
                    response = response2;
                }

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

        /* renamed from: android.media.tv.TvInputService$Session$15 */
        /* loaded from: classes2.dex */
        class AnonymousClass15 implements Runnable {
            final /* synthetic */ AdResponse val$response;

            AnonymousClass15(AdResponse adResponse) {
                response = adResponse;
            }

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
        }

        public void notifyAdResponse(AdResponse response) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.TvInputService.Session.15
                final /* synthetic */ AdResponse val$response;

                AnonymousClass15(AdResponse response2) {
                    response = response2;
                }

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
                AdBuffer dupBuffer = AdBuffer.dupAdBuffer(buffer);
                executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.TvInputService.Session.16
                    final /* synthetic */ AdBuffer val$dupBuffer;

                    AnonymousClass16(AdBuffer dupBuffer2) {
                        dupBuffer = dupBuffer2;
                    }

                    @Override // java.lang.Runnable
                    public void run() {
                        AdBuffer adBuffer;
                        try {
                            try {
                                if (Session.this.mSessionCallback != null) {
                                    Session.this.mSessionCallback.onAdBufferConsumed(dupBuffer);
                                }
                                adBuffer = dupBuffer;
                                if (adBuffer == null) {
                                    return;
                                }
                            } catch (RemoteException e) {
                                Log.w(TvInputService.TAG, "error in notifyAdBufferConsumed", e);
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
                Log.w(TvInputService.TAG, "dup AdBuffer error in notifyAdBufferConsumed:", e);
            }
        }

        /* renamed from: android.media.tv.TvInputService$Session$16 */
        /* loaded from: classes2.dex */
        class AnonymousClass16 implements Runnable {
            final /* synthetic */ AdBuffer val$dupBuffer;

            AnonymousClass16(AdBuffer dupBuffer2) {
                dupBuffer = dupBuffer2;
            }

            @Override // java.lang.Runnable
            public void run() {
                AdBuffer adBuffer;
                try {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onAdBufferConsumed(dupBuffer);
                        }
                        adBuffer = dupBuffer;
                        if (adBuffer == null) {
                            return;
                        }
                    } catch (RemoteException e) {
                        Log.w(TvInputService.TAG, "error in notifyAdBufferConsumed", e);
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

        /* renamed from: android.media.tv.TvInputService$Session$17 */
        /* loaded from: classes2.dex */
        class AnonymousClass17 implements Runnable {
            final /* synthetic */ Bundle val$data;
            final /* synthetic */ int val$type;

            AnonymousClass17(int i, Bundle bundle) {
                type = i;
                data = bundle;
            }

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
        }

        public void notifyTvMessage(int type, Bundle data) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.TvInputService.Session.17
                final /* synthetic */ Bundle val$data;
                final /* synthetic */ int val$type;

                AnonymousClass17(int type2, Bundle data2) {
                    type = type2;
                    data = data2;
                }

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

        /* renamed from: android.media.tv.TvInputService$Session$18 */
        /* loaded from: classes2.dex */
        public class AnonymousClass18 implements Runnable {
            final /* synthetic */ long val$timeMs;

            AnonymousClass18(long j) {
                timeMs = j;
            }

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
        }

        public void notifyTimeShiftStartPositionChanged(long timeMs) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.TvInputService.Session.18
                final /* synthetic */ long val$timeMs;

                AnonymousClass18(long timeMs2) {
                    timeMs = timeMs2;
                }

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

        /* renamed from: android.media.tv.TvInputService$Session$19 */
        /* loaded from: classes2.dex */
        public class AnonymousClass19 implements Runnable {
            final /* synthetic */ long val$timeMs;

            AnonymousClass19(long j) {
                timeMs = j;
            }

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
        }

        public void notifyTimeShiftCurrentPositionChanged(long timeMs) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.TvInputService.Session.19
                final /* synthetic */ long val$timeMs;

                AnonymousClass19(long timeMs2) {
                    timeMs = timeMs2;
                }

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

        /* renamed from: android.media.tv.TvInputService$Session$20 */
        /* loaded from: classes2.dex */
        class AnonymousClass20 implements Runnable {
            final /* synthetic */ AitInfo val$aitInfo;

            AnonymousClass20(AitInfo aitInfo) {
                aitInfo = aitInfo;
            }

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
        }

        public void notifyAitInfoUpdated(AitInfo aitInfo) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.TvInputService.Session.20
                final /* synthetic */ AitInfo val$aitInfo;

                AnonymousClass20(AitInfo aitInfo2) {
                    aitInfo = aitInfo2;
                }

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

        /* renamed from: android.media.tv.TvInputService$Session$21 */
        /* loaded from: classes2.dex */
        class AnonymousClass21 implements Runnable {
            final /* synthetic */ int val$mode;

            AnonymousClass21(int i) {
                mode = i;
            }

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
        }

        public void notifyTimeShiftMode(int mode) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.TvInputService.Session.21
                final /* synthetic */ int val$mode;

                AnonymousClass21(int mode2) {
                    mode = mode2;
                }

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

        /* renamed from: android.media.tv.TvInputService$Session$22 */
        /* loaded from: classes2.dex */
        class AnonymousClass22 implements Runnable {
            final /* synthetic */ float[] val$speeds;

            AnonymousClass22(float[] fArr) {
                speeds = fArr;
            }

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
        }

        public void notifyAvailableSpeeds(float[] speeds) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.TvInputService.Session.22
                final /* synthetic */ float[] val$speeds;

                AnonymousClass22(float[] speeds2) {
                    speeds = speeds2;
                }

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

        /* renamed from: android.media.tv.TvInputService$Session$23 */
        /* loaded from: classes2.dex */
        class AnonymousClass23 implements Runnable {
            final /* synthetic */ int val$strength;

            AnonymousClass23(int i) {
                strength = i;
            }

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
        }

        public void notifySignalStrength(int strength) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.TvInputService.Session.23
                final /* synthetic */ int val$strength;

                AnonymousClass23(int strength2) {
                    strength = strength2;
                }

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

        /* renamed from: android.media.tv.TvInputService$Session$24 */
        /* loaded from: classes2.dex */
        class AnonymousClass24 implements Runnable {
            final /* synthetic */ boolean val$available;

            AnonymousClass24(boolean z) {
                available = z;
            }

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
        }

        public void notifyCueingMessageAvailability(boolean available) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.TvInputService.Session.24
                final /* synthetic */ boolean val$available;

                AnonymousClass24(boolean available2) {
                    available = available2;
                }

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

        public void layoutSurface(int left, int top, int right, int bottom) {
            if (left > right || top > bottom) {
                throw new IllegalArgumentException("Invalid parameter");
            }
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.TvInputService.Session.25
                final /* synthetic */ int val$bottom;
                final /* synthetic */ int val$left;
                final /* synthetic */ int val$right;
                final /* synthetic */ int val$top;

                AnonymousClass25(int left2, int top2, int right2, int bottom2) {
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
                        Log.w(TvInputService.TAG, "error in layoutSurface", e);
                    }
                }
            });
        }

        /* renamed from: android.media.tv.TvInputService$Session$25 */
        /* loaded from: classes2.dex */
        class AnonymousClass25 implements Runnable {
            final /* synthetic */ int val$bottom;
            final /* synthetic */ int val$left;
            final /* synthetic */ int val$right;
            final /* synthetic */ int val$top;

            AnonymousClass25(int left2, int top2, int right2, int bottom2) {
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
                    Log.w(TvInputService.TAG, "error in layoutSurface", e);
                }
            }
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
            removeOverlayView(true);
            this.mHandler.removeCallbacks(this.mTimeShiftPositionTrackingRunnable);
        }

        public void setMain(boolean isMain) {
            onSetMain(isMain);
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

        public void setStreamVolume(float volume) {
            onSetStreamVolume(volume);
        }

        public void tune(Uri channelUri, Bundle params) {
            this.mCurrentPositionMs = Long.MIN_VALUE;
            onTune(channelUri, params);
        }

        public void setCaptionEnabled(boolean enabled) {
            onSetCaptionEnabled(enabled);
        }

        public void selectAudioPresentation(int presentationId, int programId) {
            onSelectAudioPresentation(presentationId, programId);
        }

        public void selectTrack(int type, String trackId) {
            onSelectTrack(type, trackId);
        }

        public void unblockContent(String unblockedRating) {
            onUnblockContent(TvContentRating.unflattenFromString(unblockedRating));
        }

        public void setInteractiveAppNotificationEnabled(boolean enabled) {
            onSetInteractiveAppNotificationEnabled(enabled);
        }

        public void setTvMessageEnabled(int type, boolean enabled) {
            onSetTvMessageEnabled(type, enabled);
        }

        public void appPrivateCommand(String action, Bundle data) {
            onAppPrivateCommand(action, data);
        }

        public void createOverlayView(IBinder windowToken, Rect frame) {
            if (this.mOverlayViewContainer != null) {
                removeOverlayView(false);
            }
            this.mWindowToken = windowToken;
            this.mOverlayFrame = frame;
            onOverlayViewSizeChanged(frame.right - frame.left, frame.bottom - frame.top);
            if (!this.mOverlayViewEnabled) {
                return;
            }
            View onCreateOverlayView = onCreateOverlayView();
            this.mOverlayView = onCreateOverlayView;
            if (onCreateOverlayView == null) {
                return;
            }
            OverlayViewCleanUpTask overlayViewCleanUpTask = this.mOverlayViewCleanUpTask;
            if (overlayViewCleanUpTask != null) {
                overlayViewCleanUpTask.cancel(true);
                this.mOverlayViewCleanUpTask = null;
            }
            FrameLayout frameLayout = new FrameLayout(this.mContext.getApplicationContext());
            this.mOverlayViewContainer = frameLayout;
            frameLayout.addView(this.mOverlayView);
            int flags = ActivityManager.isHighEndGfx() ? 536 | 16777216 : 536;
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(frame.right - frame.left, frame.bottom - frame.top, frame.left, frame.top, 1004, flags, -2);
            this.mWindowParams = layoutParams;
            layoutParams.privateFlags |= 64;
            this.mWindowParams.gravity = 8388659;
            this.mWindowParams.token = windowToken;
            this.mWindowManager.addView(this.mOverlayViewContainer, this.mWindowParams);
        }

        public void relayoutOverlayView(Rect frame) {
            Rect rect = this.mOverlayFrame;
            if (rect == null || rect.width() != frame.width() || this.mOverlayFrame.height() != frame.height()) {
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

        public void removeOverlayView(boolean clearWindowToken) {
            if (clearWindowToken) {
                this.mWindowToken = null;
                this.mOverlayFrame = null;
            }
            FrameLayout frameLayout = this.mOverlayViewContainer;
            if (frameLayout != null) {
                frameLayout.removeView(this.mOverlayView);
                this.mOverlayView = null;
                this.mWindowManager.removeView(this.mOverlayViewContainer);
                this.mOverlayViewContainer = null;
                this.mWindowParams = null;
            }
        }

        public void timeShiftPlay(Uri recordedProgramUri) {
            this.mCurrentPositionMs = 0L;
            onTimeShiftPlay(recordedProgramUri);
        }

        public void timeShiftPause() {
            onTimeShiftPause();
        }

        public void timeShiftResume() {
            onTimeShiftResume();
        }

        public void timeShiftSeekTo(long timeMs) {
            onTimeShiftSeekTo(timeMs);
        }

        public void timeShiftSetPlaybackParams(PlaybackParams params) {
            onTimeShiftSetPlaybackParams(params);
        }

        public void timeShiftSetMode(int mode) {
            onTimeShiftSetMode(mode);
        }

        public void timeShiftEnablePositionTracking(boolean enable) {
            if (enable) {
                this.mHandler.post(this.mTimeShiftPositionTrackingRunnable);
                return;
            }
            this.mHandler.removeCallbacks(this.mTimeShiftPositionTrackingRunnable);
            this.mStartPositionMs = Long.MIN_VALUE;
            this.mCurrentPositionMs = Long.MIN_VALUE;
        }

        public void scheduleOverlayViewCleanup() {
            View overlayViewParent = this.mOverlayViewContainer;
            if (overlayViewParent != null) {
                OverlayViewCleanUpTask overlayViewCleanUpTask = new OverlayViewCleanUpTask();
                this.mOverlayViewCleanUpTask = overlayViewCleanUpTask;
                overlayViewCleanUpTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, overlayViewParent);
            }
        }

        public void requestBroadcastInfo(BroadcastInfoRequest request) {
            onRequestBroadcastInfo(request);
        }

        public void removeBroadcastInfo(int requestId) {
            onRemoveBroadcastInfo(requestId);
        }

        public void requestAd(AdRequest request) {
            onRequestAd(request);
        }

        public void notifyAdBufferReady(AdBuffer buffer) {
            onAdBufferReady(buffer);
        }

        public void onTvMessageReceived(int type, Bundle data) {
            onTvMessage(type, data);
        }

        public int dispatchInputEvent(InputEvent event, InputEventReceiver receiver) {
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
            FrameLayout frameLayout = this.mOverlayViewContainer;
            if (frameLayout == null || !frameLayout.isAttachedToWindow() || skipDispatchToOverlayView) {
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

        /* loaded from: classes2.dex */
        public final class TimeShiftPositionTrackingRunnable implements Runnable {
            /* synthetic */ TimeShiftPositionTrackingRunnable(Session session, TimeShiftPositionTrackingRunnableIA timeShiftPositionTrackingRunnableIA) {
                this();
            }

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

    /* loaded from: classes2.dex */
    public static final class OverlayViewCleanUpTask extends AsyncTask<View, Void, Void> {
        /* synthetic */ OverlayViewCleanUpTask(OverlayViewCleanUpTaskIA overlayViewCleanUpTaskIA) {
            this();
        }

        private OverlayViewCleanUpTask() {
        }

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

    /* loaded from: classes2.dex */
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

        /* renamed from: android.media.tv.TvInputService$RecordingSession$1 */
        /* loaded from: classes2.dex */
        class AnonymousClass1 implements Runnable {
            final /* synthetic */ Uri val$channelUri;

            AnonymousClass1(Uri uri) {
                channelUri = uri;
            }

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
        }

        public void notifyTuned(Uri channelUri) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.TvInputService.RecordingSession.1
                final /* synthetic */ Uri val$channelUri;

                AnonymousClass1(Uri channelUri2) {
                    channelUri = channelUri2;
                }

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

        /* renamed from: android.media.tv.TvInputService$RecordingSession$2 */
        /* loaded from: classes2.dex */
        class AnonymousClass2 implements Runnable {
            final /* synthetic */ Uri val$recordedProgramUri;

            AnonymousClass2(Uri uri) {
                recordedProgramUri = uri;
            }

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
        }

        public void notifyRecordingStopped(Uri recordedProgramUri) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.TvInputService.RecordingSession.2
                final /* synthetic */ Uri val$recordedProgramUri;

                AnonymousClass2(Uri recordedProgramUri2) {
                    recordedProgramUri = recordedProgramUri2;
                }

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
            int validError = error;
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.TvInputService.RecordingSession.3
                final /* synthetic */ int val$validError;

                AnonymousClass3(int validError2) {
                    validError = validError2;
                }

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

        /* renamed from: android.media.tv.TvInputService$RecordingSession$3 */
        /* loaded from: classes2.dex */
        class AnonymousClass3 implements Runnable {
            final /* synthetic */ int val$validError;

            AnonymousClass3(int validError2) {
                validError = validError2;
            }

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
        }

        /* renamed from: android.media.tv.TvInputService$RecordingSession$4 */
        /* loaded from: classes2.dex */
        class AnonymousClass4 implements Runnable {
            final /* synthetic */ Bundle val$eventArgs;
            final /* synthetic */ String val$eventType;

            AnonymousClass4(String str, Bundle bundle) {
                eventType = str;
                eventArgs = bundle;
            }

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
        }

        @SystemApi
        public void notifySessionEvent(String eventType, Bundle eventArgs) {
            Preconditions.checkNotNull(eventType);
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.TvInputService.RecordingSession.4
                final /* synthetic */ Bundle val$eventArgs;
                final /* synthetic */ String val$eventType;

                AnonymousClass4(String eventType2, Bundle eventArgs2) {
                    eventType = eventType2;
                    eventArgs = eventArgs2;
                }

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

        public void tune(Uri channelUri, Bundle params) {
            onTune(channelUri, params);
        }

        public void release() {
            onRelease();
        }

        public void startRecording(Uri programUri, Bundle params) {
            onStartRecording(programUri, params);
        }

        public void stopRecording() {
            onStopRecording();
        }

        public void pauseRecording(Bundle params) {
            onPauseRecording(params);
        }

        public void resumeRecording(Bundle params) {
            onResumeRecording(params);
        }

        public void appPrivateCommand(String action, Bundle data) {
            onAppPrivateCommand(action, data);
        }

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

    /* loaded from: classes2.dex */
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
                AnonymousClass1() {
                }

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

        /* renamed from: android.media.tv.TvInputService$HardwareSession$1 */
        /* loaded from: classes2.dex */
        class AnonymousClass1 extends TvInputManager.SessionCallback {
            AnonymousClass1() {
            }

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

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // android.media.tv.TvInputService.Session
        public void release() {
            TvInputManager.Session session = this.mHardwareSession;
            if (session != null) {
                session.release();
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

    /* loaded from: classes2.dex */
    private final class ServiceHandler extends Handler {
        private static final int DO_ADD_HARDWARE_INPUT = 4;
        private static final int DO_ADD_HDMI_INPUT = 6;
        private static final int DO_CREATE_RECORDING_SESSION = 3;
        private static final int DO_CREATE_SESSION = 1;
        private static final int DO_NOTIFY_SESSION_CREATED = 2;
        private static final int DO_REMOVE_HARDWARE_INPUT = 5;
        private static final int DO_REMOVE_HDMI_INPUT = 7;
        private static final int DO_UPDATE_HDMI_INPUT = 8;

        /* synthetic */ ServiceHandler(TvInputService tvInputService, ServiceHandlerIA serviceHandlerIA) {
            this();
        }

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
                            return;
                        } catch (RemoteException e) {
                            Log.e(TvInputService.TAG, "error in onSessionCreated", e);
                            return;
                        }
                    }
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
                                return;
                            } catch (RemoteException e2) {
                                Log.e(TvInputService.TAG, "error in onSessionCreated", e2);
                                return;
                            }
                        }
                        proxySession.mProxySession = stub;
                        proxySession.mProxySessionCallback = cb;
                        proxySession.mServiceHandler = TvInputService.this.mServiceHandler;
                        TvInputManager manager = (TvInputManager) TvInputService.this.getSystemService(Context.TV_INPUT_SERVICE);
                        manager.createSession(hardwareInputId, tvAppAttributionSource, proxySession.mHardwareSessionCallback, TvInputService.this.mServiceHandler);
                        return;
                    }
                    SomeArgs someArgs = SomeArgs.obtain();
                    someArgs.arg1 = sessionImpl;
                    someArgs.arg2 = stub;
                    someArgs.arg3 = cb;
                    someArgs.arg4 = null;
                    TvInputService.this.mServiceHandler.obtainMessage(2, someArgs).sendToTarget();
                    return;
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
                    return;
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
                            return;
                        } catch (RemoteException e4) {
                            Log.e(TvInputService.TAG, "error in onSessionCreated", e4);
                            return;
                        }
                    }
                    try {
                        cb3.onSessionCreated(new ITvInputSessionWrapper(TvInputService.this, recordingSessionImpl), null);
                    } catch (RemoteException e5) {
                        Log.e(TvInputService.TAG, "error in onSessionCreated", e5);
                    }
                    recordingSessionImpl.initialize(cb3);
                    return;
                case 4:
                    TvInputHardwareInfo hardwareInfo = (TvInputHardwareInfo) msg.obj;
                    TvInputInfo inputInfo = TvInputService.this.onHardwareAdded(hardwareInfo);
                    if (inputInfo != null) {
                        broadcastAddHardwareInput(hardwareInfo.getDeviceId(), inputInfo);
                        return;
                    }
                    return;
                case 5:
                    String inputId3 = TvInputService.this.onHardwareRemoved((TvInputHardwareInfo) msg.obj);
                    if (inputId3 != null) {
                        broadcastRemoveHardwareInput(inputId3);
                        return;
                    }
                    return;
                case 6:
                    HdmiDeviceInfo deviceInfo = (HdmiDeviceInfo) msg.obj;
                    TvInputInfo inputInfo2 = TvInputService.this.onHdmiDeviceAdded(deviceInfo);
                    if (inputInfo2 != null) {
                        broadcastAddHdmiInput(deviceInfo.getId(), inputInfo2);
                        return;
                    }
                    return;
                case 7:
                    String inputId4 = TvInputService.this.onHdmiDeviceRemoved((HdmiDeviceInfo) msg.obj);
                    if (inputId4 != null) {
                        broadcastRemoveHardwareInput(inputId4);
                        return;
                    }
                    return;
                case 8:
                    TvInputService.this.onHdmiDeviceUpdated((HdmiDeviceInfo) msg.obj);
                    return;
                default:
                    Log.w(TvInputService.TAG, "Unhandled message code: " + msg.what);
                    return;
            }
        }
    }
}
