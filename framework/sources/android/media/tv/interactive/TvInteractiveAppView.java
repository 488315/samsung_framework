package android.media.tv.interactive;

import android.annotation.NonNull;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.PlaybackParams;
import android.media.tv.TvInputManager;
import android.media.tv.TvRecordingInfo;
import android.media.tv.TvTrackInfo;
import android.media.tv.TvView;
import android.media.tv.interactive.TvInteractiveAppManager;
import android.media.tv.interactive.TvInteractiveAppView;
import android.net.Uri;
import android.net.http.SslCertificate;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import android.view.InputEvent;
import android.view.KeyEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewRootImpl;
import com.android.internal.hidden_from_bootclasspath.android.media.tv.flags.Flags;
import com.android.internal.util.AnnotationValidations;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
public class TvInteractiveAppView extends ViewGroup {
    public static final String BI_INTERACTIVE_APP_KEY_ALIAS = "alias";
    public static final String BI_INTERACTIVE_APP_KEY_CERTIFICATE = "certificate";
    public static final String BI_INTERACTIVE_APP_KEY_HTTP_ADDITIONAL_HEADERS = "http_additional_headers";
    public static final String BI_INTERACTIVE_APP_KEY_HTTP_USER_AGENT = "http_user_agent";
    public static final String BI_INTERACTIVE_APP_KEY_PRIVATE_KEY = "private_key";
    private static final boolean DEBUG = false;
    public static final String ERROR_KEY_METHOD_NAME = "method_name";
    private static final int SET_TVVIEW_FAIL = 2;
    private static final int SET_TVVIEW_SUCCESS = 1;
    private static final String TAG = "TvInteractiveAppView";
    private static final int UNSET_TVVIEW_FAIL = 4;
    private static final int UNSET_TVVIEW_SUCCESS = 3;
    private final AttributeSet mAttrs;
    private TvInteractiveAppCallback mCallback;
    private Executor mCallbackExecutor;
    private final Object mCallbackLock;
    private final int mDefStyleAttr;
    private final TvInteractiveAppManager.Session.FinishedInputEventCallback mFinishedInputEventCallback;
    private final Handler mHandler;
    private boolean mMediaViewCreated;
    private Rect mMediaViewFrame;
    private OnUnhandledInputEventListener mOnUnhandledInputEventListener;
    private final XmlResourceParser mParser;
    private TvInteractiveAppManager.Session mSession;
    private MySessionCallback mSessionCallback;
    private Surface mSurface;
    private boolean mSurfaceChanged;
    private int mSurfaceFormat;
    private int mSurfaceHeight;
    private final SurfaceHolder.Callback mSurfaceHolderCallback;
    private SurfaceView mSurfaceView;
    private int mSurfaceViewBottom;
    private int mSurfaceViewLeft;
    private int mSurfaceViewRight;
    private int mSurfaceViewTop;
    private int mSurfaceWidth;
    private final TvInteractiveAppManager mTvInteractiveAppManager;
    private boolean mUseRequestedSurfaceLayout;

    public interface OnUnhandledInputEventListener {
        boolean onUnhandledInputEvent(InputEvent inputEvent);
    }

    public TvInteractiveAppView(Context context) {
        this(context, null, 0);
    }

    public TvInteractiveAppView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TvInteractiveAppView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mHandler = new Handler();
        this.mCallbackLock = new Object();
        this.mSurfaceHolderCallback = new SurfaceHolder.Callback() { // from class: android.media.tv.interactive.TvInteractiveAppView.1
            @Override // android.view.SurfaceHolder.Callback
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                TvInteractiveAppView.this.mSurfaceFormat = format;
                TvInteractiveAppView.this.mSurfaceWidth = width;
                TvInteractiveAppView.this.mSurfaceHeight = height;
                TvInteractiveAppView.this.mSurfaceChanged = true;
                TvInteractiveAppView.this.dispatchSurfaceChanged(TvInteractiveAppView.this.mSurfaceFormat, TvInteractiveAppView.this.mSurfaceWidth, TvInteractiveAppView.this.mSurfaceHeight);
            }

            @Override // android.view.SurfaceHolder.Callback
            public void surfaceCreated(SurfaceHolder holder) {
                TvInteractiveAppView.this.mSurface = holder.getSurface();
                TvInteractiveAppView.this.setSessionSurface(TvInteractiveAppView.this.mSurface);
            }

            @Override // android.view.SurfaceHolder.Callback
            public void surfaceDestroyed(SurfaceHolder holder) {
                TvInteractiveAppView.this.mSurface = null;
                TvInteractiveAppView.this.mSurfaceChanged = false;
                TvInteractiveAppView.this.setSessionSurface(null);
            }
        };
        this.mFinishedInputEventCallback = new TvInteractiveAppManager.Session.FinishedInputEventCallback() { // from class: android.media.tv.interactive.TvInteractiveAppView.3
            @Override // android.media.tv.interactive.TvInteractiveAppManager.Session.FinishedInputEventCallback
            public void onFinishedInputEvent(Object token, boolean handled) {
                ViewRootImpl viewRootImpl;
                if (handled) {
                    return;
                }
                InputEvent event = (InputEvent) token;
                if (!TvInteractiveAppView.this.dispatchUnhandledInputEvent(event) && (viewRootImpl = TvInteractiveAppView.this.getViewRootImpl()) != null) {
                    viewRootImpl.dispatchUnhandledInputEvent(event);
                }
            }
        };
        int sourceResId = Resources.getAttributeSetSourceResId(attrs);
        if (sourceResId != 0) {
            Log.d(TAG, "Build local AttributeSet");
            this.mParser = context.getResources().getXml(sourceResId);
            this.mAttrs = Xml.asAttributeSet(this.mParser);
        } else {
            Log.d(TAG, "Use passed in AttributeSet");
            this.mParser = null;
            this.mAttrs = attrs;
        }
        this.mDefStyleAttr = defStyleAttr;
        resetSurfaceView();
        this.mTvInteractiveAppManager = (TvInteractiveAppManager) getContext().getSystemService(Context.TV_INTERACTIVE_APP_SERVICE);
    }

    public void setCallback(Executor executor, TvInteractiveAppCallback callback) {
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) callback);
        synchronized (this.mCallbackLock) {
            this.mCallbackExecutor = executor;
            this.mCallback = callback;
        }
    }

    public void clearCallback() {
        synchronized (this.mCallbackLock) {
            this.mCallback = null;
            this.mCallbackExecutor = null;
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        createSessionMediaView();
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        removeSessionMediaView();
        super.onDetachedFromWindow();
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (this.mUseRequestedSurfaceLayout) {
            this.mSurfaceView.layout(this.mSurfaceViewLeft, this.mSurfaceViewTop, this.mSurfaceViewRight, this.mSurfaceViewBottom);
        } else {
            this.mSurfaceView.layout(0, 0, right - left, bottom - top);
        }
    }

    @Override // android.view.View
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        this.mSurfaceView.measure(widthMeasureSpec, heightMeasureSpec);
        int width = this.mSurfaceView.getMeasuredWidth();
        int height = this.mSurfaceView.getMeasuredHeight();
        int childState = this.mSurfaceView.getMeasuredState();
        setMeasuredDimension(resolveSizeAndState(width, widthMeasureSpec, childState), resolveSizeAndState(height, heightMeasureSpec, childState << 16));
    }

    @Override // android.view.View
    public void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        this.mSurfaceView.setVisibility(visibility);
        if (visibility == 0) {
            createSessionMediaView();
        } else {
            removeSessionMediaView();
        }
    }

    private void resetSurfaceView() {
        if (this.mSurfaceView != null) {
            this.mSurfaceView.getHolder().removeCallback(this.mSurfaceHolderCallback);
            removeView(this.mSurfaceView);
        }
        this.mSurface = null;
        this.mSurfaceView = new SurfaceView(getContext(), this.mAttrs, this.mDefStyleAttr) { // from class: android.media.tv.interactive.TvInteractiveAppView.2
            @Override // android.view.SurfaceView
            protected void updateSurface() {
                super.updateSurface();
                TvInteractiveAppView.this.relayoutSessionMediaView();
            }
        };
        this.mSurfaceView.setSecure(true);
        this.mSurfaceView.getHolder().addCallback(this.mSurfaceHolderCallback);
        this.mSurfaceView.getHolder().setFormat(-3);
        this.mSurfaceView.setZOrderOnTop(false);
        this.mSurfaceView.setZOrderMediaOverlay(true);
        addView(this.mSurfaceView);
    }

    public void reset() {
        resetInternal();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void createSessionMediaView() {
        if (this.mSession == null || !isAttachedToWindow() || this.mMediaViewCreated) {
            return;
        }
        this.mMediaViewFrame = getViewFrameOnScreen();
        this.mSession.createMediaView(this, this.mMediaViewFrame);
        this.mMediaViewCreated = true;
    }

    private void removeSessionMediaView() {
        if (this.mSession == null || !this.mMediaViewCreated) {
            return;
        }
        this.mSession.removeMediaView();
        this.mMediaViewCreated = false;
        this.mMediaViewFrame = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void relayoutSessionMediaView() {
        if (this.mSession == null || !isAttachedToWindow() || !this.mMediaViewCreated) {
            return;
        }
        Rect viewFrame = getViewFrameOnScreen();
        if (viewFrame.equals(this.mMediaViewFrame)) {
            return;
        }
        this.mSession.relayoutMediaView(viewFrame);
        this.mMediaViewFrame = viewFrame;
    }

    private Rect getViewFrameOnScreen() {
        Rect frame = new Rect();
        getGlobalVisibleRect(frame);
        RectF frameF = new RectF(frame);
        getMatrix().mapRect(frameF);
        frameF.round(frame);
        return frame;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSessionSurface(Surface surface) {
        if (this.mSession == null) {
            return;
        }
        this.mSession.setSurface(surface);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchSurfaceChanged(int format, int width, int height) {
        if (this.mSession == null) {
            return;
        }
        this.mSession.dispatchSurfaceChanged(format, width, height);
    }

    public boolean dispatchUnhandledInputEvent(InputEvent event) {
        if (this.mOnUnhandledInputEventListener != null && this.mOnUnhandledInputEventListener.onUnhandledInputEvent(event)) {
            return true;
        }
        return onUnhandledInputEvent(event);
    }

    public boolean onUnhandledInputEvent(InputEvent event) {
        return false;
    }

    public void setOnUnhandledInputEventListener(Executor executor, OnUnhandledInputEventListener listener) {
        this.mOnUnhandledInputEventListener = listener;
    }

    public OnUnhandledInputEventListener getOnUnhandledInputEventListener() {
        return this.mOnUnhandledInputEventListener;
    }

    public void clearOnUnhandledInputEventListener() {
        this.mOnUnhandledInputEventListener = null;
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (super.dispatchKeyEvent(event)) {
            return true;
        }
        if (this.mSession == null) {
            return false;
        }
        InputEvent copiedEvent = event.copy();
        int ret = this.mSession.dispatchInputEvent(copiedEvent, copiedEvent, this.mFinishedInputEventCallback, this.mHandler);
        return ret != 0;
    }

    public void prepareInteractiveApp(String iAppServiceId, int type) {
        this.mSessionCallback = new MySessionCallback(iAppServiceId, type);
        if (this.mTvInteractiveAppManager != null) {
            this.mTvInteractiveAppManager.createSession(iAppServiceId, type, this.mSessionCallback, this.mHandler);
        }
    }

    public void startInteractiveApp() {
        if (this.mSession != null) {
            this.mSession.startInteractiveApp();
        }
    }

    public void stopInteractiveApp() {
        if (this.mSession != null) {
            this.mSession.stopInteractiveApp();
        }
    }

    public void resetInteractiveApp() {
        if (this.mSession != null) {
            this.mSession.resetInteractiveApp();
        }
    }

    public void sendCurrentVideoBounds(Rect bounds) {
        if (this.mSession != null) {
            this.mSession.sendCurrentVideoBounds(bounds);
        }
    }

    public void sendCurrentChannelUri(Uri channelUri) {
        if (this.mSession != null) {
            this.mSession.sendCurrentChannelUri(channelUri);
        }
    }

    public void sendCurrentChannelLcn(int lcn) {
        if (this.mSession != null) {
            this.mSession.sendCurrentChannelLcn(lcn);
        }
    }

    public void sendStreamVolume(float volume) {
        if (this.mSession != null) {
            this.mSession.sendStreamVolume(volume);
        }
    }

    public void sendTrackInfoList(List<TvTrackInfo> tracks) {
        if (this.mSession != null) {
            this.mSession.sendTrackInfoList(tracks);
        }
    }

    public void sendSelectedTrackInfo(List<TvTrackInfo> tracks) {
        if (this.mSession != null) {
            this.mSession.sendSelectedTrackInfo(tracks);
        }
    }

    public void sendCurrentTvInputId(String inputId) {
        if (this.mSession != null) {
            this.mSession.sendCurrentTvInputId(inputId);
        }
    }

    public void sendTimeShiftMode(int mode) {
        if (this.mSession != null) {
            this.mSession.sendTimeShiftMode(mode);
        }
    }

    public void sendAvailableSpeeds(float[] speeds) {
        if (this.mSession != null) {
            Arrays.sort(speeds);
            this.mSession.sendAvailableSpeeds(speeds);
        }
    }

    public void sendTvRecordingInfo(TvRecordingInfo recordingInfo) {
        if (this.mSession != null) {
            this.mSession.sendTvRecordingInfo(recordingInfo);
        }
    }

    public void sendTvRecordingInfoList(List<TvRecordingInfo> recordingInfoList) {
        if (this.mSession != null) {
            this.mSession.sendTvRecordingInfoList(recordingInfoList);
        }
    }

    public void notifyRecordingStarted(String recordingId, String requestId) {
        if (this.mSession != null) {
            this.mSession.notifyRecordingStarted(recordingId, requestId);
        }
    }

    public void notifyRecordingStopped(String recordingId) {
        if (this.mSession != null) {
            this.mSession.notifyRecordingStopped(recordingId);
        }
    }

    public void notifyVideoFreezeUpdated(boolean isFrozen) {
        if (this.mSession != null) {
            this.mSession.notifyVideoFreezeUpdated(isFrozen);
        }
    }

    public void sendSigningResult(String signingId, byte[] result) {
        if (this.mSession != null) {
            this.mSession.sendSigningResult(signingId, result);
        }
    }

    public void sendCertificate(String host, int port, SslCertificate cert) {
        if (this.mSession != null) {
            this.mSession.sendCertificate(host, port, cert);
        }
    }

    public void notifyError(String errMsg, Bundle params) {
        if (this.mSession != null) {
            this.mSession.notifyError(errMsg, params);
        }
    }

    public void notifyTimeShiftPlaybackParams(PlaybackParams params) {
        if (this.mSession != null) {
            this.mSession.notifyTimeShiftPlaybackParams(params);
        }
    }

    public void notifyTimeShiftStatusChanged(String inputId, int status) {
        if (this.mSession != null) {
            this.mSession.notifyTimeShiftStatusChanged(inputId, status);
        }
    }

    public void notifyTimeShiftStartPositionChanged(String inputId, long timeMs) {
        if (this.mSession != null) {
            this.mSession.notifyTimeShiftStartPositionChanged(inputId, timeMs);
        }
    }

    public void notifyTimeShiftCurrentPositionChanged(String inputId, long timeMs) {
        if (this.mSession != null) {
            this.mSession.notifyTimeShiftCurrentPositionChanged(inputId, timeMs);
        }
    }

    public void notifyRecordingConnectionFailed(String recordingId, String inputId) {
        if (this.mSession != null) {
            this.mSession.notifyRecordingConnectionFailed(recordingId, inputId);
        }
    }

    public void notifyRecordingDisconnected(String recordingId, String inputId) {
        if (this.mSession != null) {
            this.mSession.notifyRecordingDisconnected(recordingId, inputId);
        }
    }

    public void notifyRecordingTuned(String recordingId, Uri channelUri) {
        if (this.mSession != null) {
            this.mSession.notifyRecordingTuned(recordingId, channelUri);
        }
    }

    public void notifyRecordingError(String recordingId, int err) {
        if (this.mSession != null) {
            this.mSession.notifyRecordingError(recordingId, err);
        }
    }

    public void notifyRecordingScheduled(String recordingId, String requestId) {
        if (this.mSession != null) {
            this.mSession.notifyRecordingScheduled(recordingId, requestId);
        }
    }

    public void notifyTvMessage(int type, Bundle data) {
        if (this.mSession != null) {
            this.mSession.notifyTvMessage(type, data);
        }
    }

    private void resetInternal() {
        this.mSessionCallback = null;
        if (this.mSession != null) {
            setSessionSurface(null);
            removeSessionMediaView();
            this.mUseRequestedSurfaceLayout = false;
            this.mSession.release();
            this.mSession = null;
            resetSurfaceView();
        }
    }

    public void createBiInteractiveApp(Uri biIAppUri, Bundle params) {
        if (this.mSession != null) {
            this.mSession.createBiInteractiveApp(biIAppUri, params);
        }
    }

    public void destroyBiInteractiveApp(String biIAppId) {
        if (this.mSession != null) {
            this.mSession.destroyBiInteractiveApp(biIAppId);
        }
    }

    public TvInteractiveAppManager.Session getInteractiveAppSession() {
        return this.mSession;
    }

    public int setTvView(TvView tvView) {
        if (tvView == null) {
            return unsetTvView();
        }
        TvInputManager.Session inputSession = tvView.getInputSession();
        if (inputSession == null || this.mSession == null) {
            return 2;
        }
        this.mSession.setInputSession(inputSession);
        inputSession.setInteractiveAppSession(this.mSession);
        return 1;
    }

    private int unsetTvView() {
        if (this.mSession == null || this.mSession.getInputSession() == null) {
            return 4;
        }
        this.mSession.getInputSession().setInteractiveAppSession(null);
        this.mSession.setInputSession(null);
        return 3;
    }

    public void setTeletextAppEnabled(boolean enable) {
        if (this.mSession != null) {
            this.mSession.setTeletextAppEnabled(enable);
        }
    }

    public static abstract class TvInteractiveAppCallback {
        public void onPlaybackCommandRequest(String iAppServiceId, String cmdType, Bundle parameters) {
        }

        public void onTimeShiftCommandRequest(String iAppServiceId, String cmdType, Bundle parameters) {
        }

        public void onStateChanged(String iAppServiceId, int state, int err) {
        }

        public void onBiInteractiveAppCreated(String iAppServiceId, Uri biIAppUri, String biIAppId) {
        }

        public void onTeletextAppStateChanged(String iAppServiceId, int state) {
        }

        public void onSetVideoBounds(String iAppServiceId, Rect rect) {
        }

        public void onRequestCurrentVideoBounds(String iAppServiceId) {
        }

        public void onRequestCurrentChannelUri(String iAppServiceId) {
        }

        public void onRequestCurrentChannelLcn(String iAppServiceId) {
        }

        public void onRequestStreamVolume(String iAppServiceId) {
        }

        public void onRequestTrackInfoList(String iAppServiceId) {
        }

        public void onRequestSelectedTrackInfo(String iAppServiceId) {
        }

        public void onRequestCurrentTvInputId(String iAppServiceId) {
        }

        public void onRequestTimeShiftMode(String iAppServiceId) {
        }

        public void onRequestAvailableSpeeds(String iAppServiceId) {
        }

        public void onRequestStartRecording(String iAppServiceId, String requestId, Uri programUri) {
        }

        public void onRequestStopRecording(String iAppServiceId, String recordingId) {
        }

        public void onRequestScheduleRecording(String iAppServiceId, String requestId, String inputId, Uri channelUri, Uri programUri, Bundle params) {
        }

        public void onRequestScheduleRecording(String iAppServiceId, String requestId, String inputId, Uri channelUri, long startTime, long duration, int repeatDays, Bundle params) {
        }

        public void onRequestSigning(String iAppServiceId, String signingId, String algorithm, String alias, byte[] data) {
        }

        public void onRequestSigning(String iAppServiceId, String signingId, String algorithm, String host, int port, byte[] data) {
        }

        public void onRequestCertificate(String iAppServiceId, String host, int port) {
        }

        public void onSetTvRecordingInfo(String iAppServiceId, String recordingId, TvRecordingInfo recordingInfo) {
        }

        public void onRequestTvRecordingInfo(String iAppServiceId, String recordingId) {
        }

        public void onRequestTvRecordingInfoList(String iAppServiceId, int type) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class MySessionCallback extends TvInteractiveAppManager.SessionCallback {
        final String mIAppServiceId;
        int mType;

        MySessionCallback(String iAppServiceId, int type) {
            this.mIAppServiceId = iAppServiceId;
            this.mType = type;
        }

        @Override // android.media.tv.interactive.TvInteractiveAppManager.SessionCallback
        public void onSessionCreated(TvInteractiveAppManager.Session session) {
            if (this != TvInteractiveAppView.this.mSessionCallback) {
                Log.w(TvInteractiveAppView.TAG, "onSessionCreated - session already created");
                if (session != null) {
                    session.release();
                    return;
                }
                return;
            }
            TvInteractiveAppView.this.mSession = session;
            if (session != null) {
                if (TvInteractiveAppView.this.mSurface != null) {
                    TvInteractiveAppView.this.setSessionSurface(TvInteractiveAppView.this.mSurface);
                    if (TvInteractiveAppView.this.mSurfaceChanged) {
                        TvInteractiveAppView.this.dispatchSurfaceChanged(TvInteractiveAppView.this.mSurfaceFormat, TvInteractiveAppView.this.mSurfaceWidth, TvInteractiveAppView.this.mSurfaceHeight);
                    }
                }
                TvInteractiveAppView.this.createSessionMediaView();
                return;
            }
            TvInteractiveAppView.this.mSessionCallback = null;
        }

        @Override // android.media.tv.interactive.TvInteractiveAppManager.SessionCallback
        public void onSessionReleased(TvInteractiveAppManager.Session session) {
            if (this != TvInteractiveAppView.this.mSessionCallback) {
                Log.w(TvInteractiveAppView.TAG, "onSessionReleased - session not created");
                return;
            }
            TvInteractiveAppView.this.mMediaViewCreated = false;
            TvInteractiveAppView.this.mMediaViewFrame = null;
            TvInteractiveAppView.this.mSessionCallback = null;
            TvInteractiveAppView.this.mSession = null;
        }

        @Override // android.media.tv.interactive.TvInteractiveAppManager.SessionCallback
        public void onLayoutSurface(TvInteractiveAppManager.Session session, int left, int top, int right, int bottom) {
            if (this != TvInteractiveAppView.this.mSessionCallback) {
                Log.w(TvInteractiveAppView.TAG, "onLayoutSurface - session not created");
                return;
            }
            TvInteractiveAppView.this.mSurfaceViewLeft = left;
            TvInteractiveAppView.this.mSurfaceViewTop = top;
            TvInteractiveAppView.this.mSurfaceViewRight = right;
            TvInteractiveAppView.this.mSurfaceViewBottom = bottom;
            TvInteractiveAppView.this.mUseRequestedSurfaceLayout = true;
            TvInteractiveAppView.this.requestLayout();
        }

        @Override // android.media.tv.interactive.TvInteractiveAppManager.SessionCallback
        public void onCommandRequest(TvInteractiveAppManager.Session session, final String cmdType, final Bundle parameters) {
            if (this != TvInteractiveAppView.this.mSessionCallback) {
                Log.w(TvInteractiveAppView.TAG, "onCommandRequest - session not created");
                return;
            }
            synchronized (TvInteractiveAppView.this.mCallbackLock) {
                if (TvInteractiveAppView.this.mCallbackExecutor != null) {
                    TvInteractiveAppView.this.mCallbackExecutor.execute(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppView$MySessionCallback$$ExternalSyntheticLambda5
                        @Override // java.lang.Runnable
                        public final void run() {
                            TvInteractiveAppView.MySessionCallback.this.lambda$onCommandRequest$0(cmdType, parameters);
                        }
                    });
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCommandRequest$0(String cmdType, Bundle parameters) {
            synchronized (TvInteractiveAppView.this.mCallbackLock) {
                if (TvInteractiveAppView.this.mCallback != null) {
                    TvInteractiveAppView.this.mCallback.onPlaybackCommandRequest(this.mIAppServiceId, cmdType, parameters);
                }
            }
        }

        @Override // android.media.tv.interactive.TvInteractiveAppManager.SessionCallback
        public void onTimeShiftCommandRequest(TvInteractiveAppManager.Session session, final String cmdType, final Bundle parameters) {
            if (this != TvInteractiveAppView.this.mSessionCallback) {
                Log.w(TvInteractiveAppView.TAG, "onTimeShiftCommandRequest - session not created");
                return;
            }
            synchronized (TvInteractiveAppView.this.mCallbackLock) {
                if (TvInteractiveAppView.this.mCallbackExecutor != null) {
                    TvInteractiveAppView.this.mCallbackExecutor.execute(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppView$MySessionCallback$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            TvInteractiveAppView.MySessionCallback.this.lambda$onTimeShiftCommandRequest$1(cmdType, parameters);
                        }
                    });
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onTimeShiftCommandRequest$1(String cmdType, Bundle parameters) {
            synchronized (TvInteractiveAppView.this.mCallbackLock) {
                if (TvInteractiveAppView.this.mCallback != null) {
                    TvInteractiveAppView.this.mCallback.onTimeShiftCommandRequest(this.mIAppServiceId, cmdType, parameters);
                }
            }
        }

        @Override // android.media.tv.interactive.TvInteractiveAppManager.SessionCallback
        public void onSessionStateChanged(TvInteractiveAppManager.Session session, final int state, final int err) {
            if (this != TvInteractiveAppView.this.mSessionCallback) {
                Log.w(TvInteractiveAppView.TAG, "onSessionStateChanged - session not created");
                return;
            }
            synchronized (TvInteractiveAppView.this.mCallbackLock) {
                if (TvInteractiveAppView.this.mCallbackExecutor != null) {
                    TvInteractiveAppView.this.mCallbackExecutor.execute(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppView$MySessionCallback$$ExternalSyntheticLambda8
                        @Override // java.lang.Runnable
                        public final void run() {
                            TvInteractiveAppView.MySessionCallback.this.lambda$onSessionStateChanged$2(state, err);
                        }
                    });
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSessionStateChanged$2(int state, int err) {
            synchronized (TvInteractiveAppView.this.mCallbackLock) {
                if (TvInteractiveAppView.this.mCallback != null) {
                    TvInteractiveAppView.this.mCallback.onStateChanged(this.mIAppServiceId, state, err);
                }
            }
        }

        @Override // android.media.tv.interactive.TvInteractiveAppManager.SessionCallback
        public void onBiInteractiveAppCreated(TvInteractiveAppManager.Session session, final Uri biIAppUri, final String biIAppId) {
            if (this != TvInteractiveAppView.this.mSessionCallback) {
                Log.w(TvInteractiveAppView.TAG, "onBiInteractiveAppCreated - session not created");
                return;
            }
            synchronized (TvInteractiveAppView.this.mCallbackLock) {
                if (TvInteractiveAppView.this.mCallbackExecutor != null) {
                    TvInteractiveAppView.this.mCallbackExecutor.execute(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppView$MySessionCallback$$ExternalSyntheticLambda10
                        @Override // java.lang.Runnable
                        public final void run() {
                            TvInteractiveAppView.MySessionCallback.this.lambda$onBiInteractiveAppCreated$3(biIAppUri, biIAppId);
                        }
                    });
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onBiInteractiveAppCreated$3(Uri biIAppUri, String biIAppId) {
            synchronized (TvInteractiveAppView.this.mCallbackLock) {
                if (TvInteractiveAppView.this.mCallback != null) {
                    TvInteractiveAppView.this.mCallback.onBiInteractiveAppCreated(this.mIAppServiceId, biIAppUri, biIAppId);
                }
            }
        }

        @Override // android.media.tv.interactive.TvInteractiveAppManager.SessionCallback
        public void onTeletextAppStateChanged(TvInteractiveAppManager.Session session, int state) {
            if (this != TvInteractiveAppView.this.mSessionCallback) {
                Log.w(TvInteractiveAppView.TAG, "onTeletextAppStateChanged - session not created");
            } else if (TvInteractiveAppView.this.mCallback != null) {
                TvInteractiveAppView.this.mCallback.onTeletextAppStateChanged(this.mIAppServiceId, state);
            }
        }

        @Override // android.media.tv.interactive.TvInteractiveAppManager.SessionCallback
        public void onSetVideoBounds(TvInteractiveAppManager.Session session, final Rect rect) {
            if (this != TvInteractiveAppView.this.mSessionCallback) {
                Log.w(TvInteractiveAppView.TAG, "onSetVideoBounds - session not created");
                return;
            }
            synchronized (TvInteractiveAppView.this.mCallbackLock) {
                if (TvInteractiveAppView.this.mCallbackExecutor != null) {
                    TvInteractiveAppView.this.mCallbackExecutor.execute(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppView$MySessionCallback$$ExternalSyntheticLambda9
                        @Override // java.lang.Runnable
                        public final void run() {
                            TvInteractiveAppView.MySessionCallback.this.lambda$onSetVideoBounds$4(rect);
                        }
                    });
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSetVideoBounds$4(Rect rect) {
            synchronized (TvInteractiveAppView.this.mCallbackLock) {
                if (TvInteractiveAppView.this.mCallback != null) {
                    TvInteractiveAppView.this.mCallback.onSetVideoBounds(this.mIAppServiceId, rect);
                }
            }
        }

        @Override // android.media.tv.interactive.TvInteractiveAppManager.SessionCallback
        public void onRequestCurrentVideoBounds(TvInteractiveAppManager.Session session) {
            if (this != TvInteractiveAppView.this.mSessionCallback) {
                Log.w(TvInteractiveAppView.TAG, "onRequestCurrentVideoBounds - session not created");
                return;
            }
            synchronized (TvInteractiveAppView.this.mCallbackLock) {
                if (TvInteractiveAppView.this.mCallbackExecutor != null) {
                    TvInteractiveAppView.this.mCallbackExecutor.execute(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppView$MySessionCallback$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            TvInteractiveAppView.MySessionCallback.this.lambda$onRequestCurrentVideoBounds$5();
                        }
                    });
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onRequestCurrentVideoBounds$5() {
            synchronized (TvInteractiveAppView.this.mCallbackLock) {
                if (TvInteractiveAppView.this.mCallback != null) {
                    TvInteractiveAppView.this.mCallback.onRequestCurrentVideoBounds(this.mIAppServiceId);
                }
            }
        }

        @Override // android.media.tv.interactive.TvInteractiveAppManager.SessionCallback
        public void onRequestCurrentChannelUri(TvInteractiveAppManager.Session session) {
            if (this != TvInteractiveAppView.this.mSessionCallback) {
                Log.w(TvInteractiveAppView.TAG, "onRequestCurrentChannelUri - session not created");
                return;
            }
            synchronized (TvInteractiveAppView.this.mCallbackLock) {
                if (TvInteractiveAppView.this.mCallbackExecutor != null) {
                    TvInteractiveAppView.this.mCallbackExecutor.execute(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppView$MySessionCallback$$ExternalSyntheticLambda6
                        @Override // java.lang.Runnable
                        public final void run() {
                            TvInteractiveAppView.MySessionCallback.this.lambda$onRequestCurrentChannelUri$6();
                        }
                    });
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onRequestCurrentChannelUri$6() {
            synchronized (TvInteractiveAppView.this.mCallbackLock) {
                if (TvInteractiveAppView.this.mCallback != null) {
                    TvInteractiveAppView.this.mCallback.onRequestCurrentChannelUri(this.mIAppServiceId);
                }
            }
        }

        @Override // android.media.tv.interactive.TvInteractiveAppManager.SessionCallback
        public void onRequestCurrentChannelLcn(TvInteractiveAppManager.Session session) {
            if (this != TvInteractiveAppView.this.mSessionCallback) {
                Log.w(TvInteractiveAppView.TAG, "onRequestCurrentChannelLcn - session not created");
                return;
            }
            synchronized (TvInteractiveAppView.this.mCallbackLock) {
                if (TvInteractiveAppView.this.mCallbackExecutor != null) {
                    TvInteractiveAppView.this.mCallbackExecutor.execute(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppView$MySessionCallback$$ExternalSyntheticLambda4
                        @Override // java.lang.Runnable
                        public final void run() {
                            TvInteractiveAppView.MySessionCallback.this.lambda$onRequestCurrentChannelLcn$7();
                        }
                    });
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onRequestCurrentChannelLcn$7() {
            synchronized (TvInteractiveAppView.this.mCallbackLock) {
                if (TvInteractiveAppView.this.mCallback != null) {
                    TvInteractiveAppView.this.mCallback.onRequestCurrentChannelLcn(this.mIAppServiceId);
                }
            }
        }

        @Override // android.media.tv.interactive.TvInteractiveAppManager.SessionCallback
        public void onRequestStreamVolume(TvInteractiveAppManager.Session session) {
            if (this != TvInteractiveAppView.this.mSessionCallback) {
                Log.w(TvInteractiveAppView.TAG, "onRequestStreamVolume - session not created");
                return;
            }
            synchronized (TvInteractiveAppView.this.mCallbackLock) {
                if (TvInteractiveAppView.this.mCallbackExecutor != null) {
                    TvInteractiveAppView.this.mCallbackExecutor.execute(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppView$MySessionCallback$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            TvInteractiveAppView.MySessionCallback.this.lambda$onRequestStreamVolume$8();
                        }
                    });
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onRequestStreamVolume$8() {
            synchronized (TvInteractiveAppView.this.mCallbackLock) {
                if (TvInteractiveAppView.this.mCallback != null) {
                    TvInteractiveAppView.this.mCallback.onRequestStreamVolume(this.mIAppServiceId);
                }
            }
        }

        @Override // android.media.tv.interactive.TvInteractiveAppManager.SessionCallback
        public void onRequestTrackInfoList(TvInteractiveAppManager.Session session) {
            if (this != TvInteractiveAppView.this.mSessionCallback) {
                Log.w(TvInteractiveAppView.TAG, "onRequestTrackInfoList - session not created");
                return;
            }
            synchronized (TvInteractiveAppView.this.mCallbackLock) {
                if (TvInteractiveAppView.this.mCallbackExecutor != null) {
                    TvInteractiveAppView.this.mCallbackExecutor.execute(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppView$MySessionCallback$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            TvInteractiveAppView.MySessionCallback.this.lambda$onRequestTrackInfoList$9();
                        }
                    });
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onRequestTrackInfoList$9() {
            synchronized (TvInteractiveAppView.this.mCallbackLock) {
                if (TvInteractiveAppView.this.mCallback != null) {
                    TvInteractiveAppView.this.mCallback.onRequestTrackInfoList(this.mIAppServiceId);
                }
            }
        }

        @Override // android.media.tv.interactive.TvInteractiveAppManager.SessionCallback
        public void onRequestSelectedTrackInfo(TvInteractiveAppManager.Session session) {
            if (this != TvInteractiveAppView.this.mSessionCallback) {
                Log.w(TvInteractiveAppView.TAG, "onRequestSelectedTrackInfo - session not created");
                return;
            }
            synchronized (TvInteractiveAppView.this.mCallbackLock) {
                if (TvInteractiveAppView.this.mCallbackExecutor != null) {
                    TvInteractiveAppView.this.mCallbackExecutor.execute(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppView$MySessionCallback$$ExternalSyntheticLambda7
                        @Override // java.lang.Runnable
                        public final void run() {
                            TvInteractiveAppView.MySessionCallback.this.lambda$onRequestSelectedTrackInfo$10();
                        }
                    });
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onRequestSelectedTrackInfo$10() {
            synchronized (TvInteractiveAppView.this.mCallbackLock) {
                if (TvInteractiveAppView.this.mCallback != null) {
                    TvInteractiveAppView.this.mCallback.onRequestSelectedTrackInfo(this.mIAppServiceId);
                }
            }
        }

        @Override // android.media.tv.interactive.TvInteractiveAppManager.SessionCallback
        public void onRequestCurrentTvInputId(TvInteractiveAppManager.Session session) {
            if (this != TvInteractiveAppView.this.mSessionCallback) {
                Log.w(TvInteractiveAppView.TAG, "onRequestCurrentTvInputId - session not created");
            } else if (TvInteractiveAppView.this.mCallback != null) {
                TvInteractiveAppView.this.mCallback.onRequestCurrentTvInputId(this.mIAppServiceId);
            }
        }

        @Override // android.media.tv.interactive.TvInteractiveAppManager.SessionCallback
        public void onRequestTimeShiftMode(TvInteractiveAppManager.Session session) {
            if (this != TvInteractiveAppView.this.mSessionCallback) {
                Log.w(TvInteractiveAppView.TAG, "onRequestTimeShiftMode - session not created");
            } else if (TvInteractiveAppView.this.mCallback != null) {
                TvInteractiveAppView.this.mCallback.onRequestTimeShiftMode(this.mIAppServiceId);
            }
        }

        @Override // android.media.tv.interactive.TvInteractiveAppManager.SessionCallback
        public void onRequestAvailableSpeeds(TvInteractiveAppManager.Session session) {
            if (this != TvInteractiveAppView.this.mSessionCallback) {
                Log.w(TvInteractiveAppView.TAG, "onRequestAvailableSpeeds - session not created");
            } else if (TvInteractiveAppView.this.mCallback != null) {
                TvInteractiveAppView.this.mCallback.onRequestAvailableSpeeds(this.mIAppServiceId);
            }
        }

        @Override // android.media.tv.interactive.TvInteractiveAppManager.SessionCallback
        public void onRequestStartRecording(TvInteractiveAppManager.Session session, String requestId, Uri programUri) {
            if (this != TvInteractiveAppView.this.mSessionCallback) {
                Log.w(TvInteractiveAppView.TAG, "onRequestStartRecording - session not created");
            } else if (TvInteractiveAppView.this.mCallback != null) {
                TvInteractiveAppView.this.mCallback.onRequestStartRecording(this.mIAppServiceId, requestId, programUri);
            }
        }

        @Override // android.media.tv.interactive.TvInteractiveAppManager.SessionCallback
        public void onRequestStopRecording(TvInteractiveAppManager.Session session, String recordingId) {
            if (this != TvInteractiveAppView.this.mSessionCallback) {
                Log.w(TvInteractiveAppView.TAG, "onRequestStopRecording - session not created");
            } else if (TvInteractiveAppView.this.mCallback != null) {
                TvInteractiveAppView.this.mCallback.onRequestStopRecording(this.mIAppServiceId, recordingId);
            }
        }

        @Override // android.media.tv.interactive.TvInteractiveAppManager.SessionCallback
        public void onSetTvRecordingInfo(TvInteractiveAppManager.Session session, String recordingId, TvRecordingInfo recordingInfo) {
            if (this != TvInteractiveAppView.this.mSessionCallback) {
                Log.w(TvInteractiveAppView.TAG, "onSetRecordingInfo - session not created");
            } else if (TvInteractiveAppView.this.mCallback != null) {
                TvInteractiveAppView.this.mCallback.onSetTvRecordingInfo(this.mIAppServiceId, recordingId, recordingInfo);
            }
        }

        @Override // android.media.tv.interactive.TvInteractiveAppManager.SessionCallback
        public void onRequestScheduleRecording(TvInteractiveAppManager.Session session, String requestId, String inputId, Uri channelUri, Uri programUri, Bundle params) {
            if (this != TvInteractiveAppView.this.mSessionCallback) {
                Log.w(TvInteractiveAppView.TAG, "onRequestScheduleRecording - session not created");
            } else if (TvInteractiveAppView.this.mCallback != null) {
                TvInteractiveAppView.this.mCallback.onRequestScheduleRecording(this.mIAppServiceId, requestId, inputId, channelUri, programUri, params);
            }
        }

        @Override // android.media.tv.interactive.TvInteractiveAppManager.SessionCallback
        public void onRequestScheduleRecording(TvInteractiveAppManager.Session session, String requestId, String inputId, Uri channelUri, long startTime, long duration, int repeatDays, Bundle params) {
            if (this != TvInteractiveAppView.this.mSessionCallback) {
                Log.w(TvInteractiveAppView.TAG, "onRequestScheduleRecording - session not created");
            } else if (TvInteractiveAppView.this.mCallback != null) {
                TvInteractiveAppView.this.mCallback.onRequestScheduleRecording(this.mIAppServiceId, requestId, inputId, channelUri, startTime, duration, repeatDays, params);
            }
        }

        @Override // android.media.tv.interactive.TvInteractiveAppManager.SessionCallback
        public void onRequestTvRecordingInfo(TvInteractiveAppManager.Session session, String recordingId) {
            if (this != TvInteractiveAppView.this.mSessionCallback) {
                Log.w(TvInteractiveAppView.TAG, "onRequestRecordingInfo - session not created");
            } else if (TvInteractiveAppView.this.mCallback != null) {
                TvInteractiveAppView.this.mCallback.onRequestTvRecordingInfo(this.mIAppServiceId, recordingId);
            }
        }

        @Override // android.media.tv.interactive.TvInteractiveAppManager.SessionCallback
        public void onRequestTvRecordingInfoList(TvInteractiveAppManager.Session session, int type) {
            if (this != TvInteractiveAppView.this.mSessionCallback) {
                Log.w(TvInteractiveAppView.TAG, "onRequestRecordingInfoList - session not created");
            } else if (TvInteractiveAppView.this.mCallback != null) {
                TvInteractiveAppView.this.mCallback.onRequestTvRecordingInfoList(this.mIAppServiceId, type);
            }
        }

        @Override // android.media.tv.interactive.TvInteractiveAppManager.SessionCallback
        public void onRequestSigning(TvInteractiveAppManager.Session session, String id, String algorithm, String alias, byte[] data) {
            if (this != TvInteractiveAppView.this.mSessionCallback) {
                Log.w(TvInteractiveAppView.TAG, "onRequestSigning - session not created");
            } else if (TvInteractiveAppView.this.mCallback != null) {
                TvInteractiveAppView.this.mCallback.onRequestSigning(this.mIAppServiceId, id, algorithm, alias, data);
            }
        }

        @Override // android.media.tv.interactive.TvInteractiveAppManager.SessionCallback
        public void onRequestSigning(TvInteractiveAppManager.Session session, String id, String algorithm, String host, int port, byte[] data) {
            if (this != TvInteractiveAppView.this.mSessionCallback) {
                Log.w(TvInteractiveAppView.TAG, "onRequestSigning - session not created");
            } else if (TvInteractiveAppView.this.mCallback != null && Flags.tiafVApis()) {
                TvInteractiveAppView.this.mCallback.onRequestSigning(this.mIAppServiceId, id, algorithm, host, port, data);
            }
        }

        @Override // android.media.tv.interactive.TvInteractiveAppManager.SessionCallback
        public void onRequestCertificate(TvInteractiveAppManager.Session session, String host, int port) {
            if (this != TvInteractiveAppView.this.mSessionCallback) {
                Log.w(TvInteractiveAppView.TAG, "onRequestCertificate - session not created");
            } else if (TvInteractiveAppView.this.mCallback != null && Flags.tiafVApis()) {
                TvInteractiveAppView.this.mCallback.onRequestCertificate(this.mIAppServiceId, host, port);
            }
        }
    }
}
