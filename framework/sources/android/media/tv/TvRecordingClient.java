package android.media.tv;

import android.annotation.SystemApi;
import android.content.Context;
import android.media.tv.TvInputManager;
import android.media.tv.interactive.TvInteractiveAppView;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import java.util.ArrayDeque;
import java.util.Objects;
import java.util.Queue;

/* loaded from: classes3.dex */
public class TvRecordingClient {
    private static final boolean DEBUG = false;
    private static final String TAG = "TvRecordingClient";
    private final RecordingCallback mCallback;
    private final Handler mHandler;
    private boolean mIsPaused;
    private boolean mIsRecordingStarted;
    private boolean mIsRecordingStopping;
    private boolean mIsTuned;
    private final Queue<Pair<String, Bundle>> mPendingAppPrivateCommands = new ArrayDeque();
    private String mRecordingId;
    private TvInputManager.Session mSession;
    private MySessionCallback mSessionCallback;
    private TvInteractiveAppView mTvIAppView;
    private final TvInputManager mTvInputManager;

    public TvRecordingClient(Context context, String tag, RecordingCallback callback, Handler handler) {
        this.mCallback = callback;
        this.mHandler = handler == null ? new Handler(Looper.getMainLooper()) : handler;
        this.mTvInputManager = (TvInputManager) context.getSystemService(Context.TV_INPUT_SERVICE);
    }

    public void setTvInteractiveAppView(TvInteractiveAppView view, String recordingId) {
        if (view != null && recordingId == null) {
            throw new IllegalArgumentException("null recordingId is not allowed only when the view is not null");
        }
        if (view == null && recordingId != null) {
            throw new IllegalArgumentException("recordingId should be null when the view is null");
        }
        this.mTvIAppView = view;
        this.mRecordingId = recordingId;
    }

    public void tune(String inputId, Uri channelUri) {
        tune(inputId, channelUri, null);
    }

    public void tune(String inputId, Uri channelUri, Bundle params) {
        if (TextUtils.isEmpty(inputId)) {
            throw new IllegalArgumentException("inputId cannot be null or an empty string");
        }
        if (this.mIsRecordingStarted && !this.mIsPaused) {
            throw new IllegalStateException("tune failed - recording already started");
        }
        if (this.mSessionCallback != null && TextUtils.equals(this.mSessionCallback.mInputId, inputId)) {
            if (this.mSession != null) {
                this.mSessionCallback.mChannelUri = channelUri;
                this.mSession.tune(channelUri, params);
            } else {
                this.mSessionCallback.mChannelUri = channelUri;
                this.mSessionCallback.mConnectionParams = params;
            }
            this.mIsTuned = false;
            return;
        }
        if (this.mIsPaused) {
            throw new IllegalStateException("tune failed - inputId is changed during pause");
        }
        resetInternal();
        this.mSessionCallback = new MySessionCallback(inputId, channelUri, params);
        if (this.mTvInputManager != null) {
            this.mTvInputManager.createRecordingSession(inputId, this.mSessionCallback, this.mHandler);
        }
    }

    public void release() {
        resetInternal();
    }

    private void resetInternal() {
        this.mSessionCallback = null;
        this.mPendingAppPrivateCommands.clear();
        if (this.mSession != null) {
            this.mSession.release();
            this.mIsTuned = false;
            this.mIsRecordingStarted = false;
            this.mIsPaused = false;
            this.mIsRecordingStopping = false;
            this.mSession = null;
        }
    }

    public void startRecording(Uri programUri) {
        startRecording(programUri, Bundle.EMPTY);
    }

    public void startRecording(Uri programUri, Bundle params) {
        if (this.mIsRecordingStopping || !this.mIsTuned || this.mIsPaused) {
            throw new IllegalStateException("startRecording failed -recording not yet stopped or not yet tuned or paused");
        }
        if (this.mIsRecordingStarted) {
            Log.w(TAG, "startRecording failed - recording already started");
        }
        if (this.mSession != null) {
            this.mSession.startRecording(programUri, params);
            this.mIsRecordingStarted = true;
        }
    }

    public void stopRecording() {
        if (!this.mIsRecordingStarted) {
            Log.w(TAG, "stopRecording failed - recording not yet started");
        }
        if (this.mSession != null) {
            this.mSession.stopRecording();
            if (this.mIsRecordingStarted) {
                this.mIsRecordingStopping = true;
            }
        }
    }

    public void pauseRecording() {
        pauseRecording(Bundle.EMPTY);
    }

    public void pauseRecording(Bundle params) {
        if (!this.mIsRecordingStarted || this.mIsRecordingStopping) {
            throw new IllegalStateException("pauseRecording failed - recording not yet started or stopping");
        }
        TvInputInfo info = this.mTvInputManager.getTvInputInfo(this.mSessionCallback.mInputId);
        if (info == null || !info.canPauseRecording()) {
            throw new UnsupportedOperationException("pauseRecording failed - operation not supported");
        }
        if (this.mIsPaused) {
            Log.w(TAG, "pauseRecording failed - recording already paused");
        }
        if (this.mSession != null) {
            this.mSession.pauseRecording(params);
            this.mIsPaused = true;
        }
    }

    public void resumeRecording() {
        resumeRecording(Bundle.EMPTY);
    }

    public void resumeRecording(Bundle params) {
        if (!this.mIsRecordingStarted || this.mIsRecordingStopping || !this.mIsTuned) {
            throw new IllegalStateException("resumeRecording failed - recording not yet started or stopping or not yet tuned");
        }
        if (!this.mIsPaused) {
            Log.w(TAG, "resumeRecording failed - recording not yet paused");
        }
        if (this.mSession != null) {
            this.mSession.resumeRecording(params);
            this.mIsPaused = false;
        }
    }

    public void sendAppPrivateCommand(String action, Bundle data) {
        if (TextUtils.isEmpty(action)) {
            throw new IllegalArgumentException("action cannot be null or an empty string");
        }
        if (this.mSession != null) {
            this.mSession.sendAppPrivateCommand(action, data);
        } else {
            Log.w(TAG, "sendAppPrivateCommand - session not yet created (action \"" + action + "\" pending)");
            this.mPendingAppPrivateCommands.add(Pair.create(action, data));
        }
    }

    public TvInputManager.SessionCallback getSessionCallback() {
        return this.mSessionCallback;
    }

    public static abstract class RecordingCallback {
        public void onConnectionFailed(String inputId) {
        }

        public void onDisconnected(String inputId) {
        }

        public void onTuned(Uri channelUri) {
        }

        public void onRecordingStopped(Uri recordedProgramUri) {
        }

        public void onError(int error) {
        }

        @SystemApi
        public void onEvent(String inputId, String eventType, Bundle eventArgs) {
        }
    }

    private class MySessionCallback extends TvInputManager.SessionCallback {
        Uri mChannelUri;
        Bundle mConnectionParams;
        final String mInputId;

        MySessionCallback(String inputId, Uri channelUri, Bundle connectionParams) {
            this.mInputId = inputId;
            this.mChannelUri = channelUri;
            this.mConnectionParams = connectionParams;
        }

        @Override // android.media.tv.TvInputManager.SessionCallback
        public void onSessionCreated(TvInputManager.Session session) {
            if (this != TvRecordingClient.this.mSessionCallback) {
                Log.w(TvRecordingClient.TAG, "onSessionCreated - session already created");
                if (session != null) {
                    session.release();
                    return;
                }
                return;
            }
            TvRecordingClient.this.mSession = session;
            if (session != null) {
                for (Pair<String, Bundle> command : TvRecordingClient.this.mPendingAppPrivateCommands) {
                    TvRecordingClient.this.mSession.sendAppPrivateCommand(command.first, command.second);
                }
                TvRecordingClient.this.mPendingAppPrivateCommands.clear();
                TvRecordingClient.this.mSession.tune(this.mChannelUri, this.mConnectionParams);
                return;
            }
            TvRecordingClient.this.mSessionCallback = null;
            if (TvRecordingClient.this.mCallback != null) {
                TvRecordingClient.this.mCallback.onConnectionFailed(this.mInputId);
            }
            if (TvRecordingClient.this.mTvIAppView != null) {
                TvRecordingClient.this.mTvIAppView.notifyRecordingConnectionFailed(TvRecordingClient.this.mRecordingId, this.mInputId);
            }
        }

        @Override // android.media.tv.TvInputManager.SessionCallback
        public void onTuned(TvInputManager.Session session, Uri channelUri) {
            if (this != TvRecordingClient.this.mSessionCallback) {
                Log.w(TvRecordingClient.TAG, "onTuned - session not created");
                return;
            }
            if (TvRecordingClient.this.mIsTuned || !Objects.equals(this.mChannelUri, channelUri)) {
                Log.w(TvRecordingClient.TAG, "onTuned - already tuned or not yet tuned to last channel");
                return;
            }
            TvRecordingClient.this.mIsTuned = true;
            if (TvRecordingClient.this.mCallback != null) {
                TvRecordingClient.this.mCallback.onTuned(channelUri);
            }
            if (TvRecordingClient.this.mTvIAppView != null) {
                TvRecordingClient.this.mTvIAppView.notifyRecordingTuned(TvRecordingClient.this.mRecordingId, channelUri);
            }
        }

        @Override // android.media.tv.TvInputManager.SessionCallback
        public void onSessionReleased(TvInputManager.Session session) {
            if (this != TvRecordingClient.this.mSessionCallback) {
                Log.w(TvRecordingClient.TAG, "onSessionReleased - session not created");
                return;
            }
            TvRecordingClient.this.mIsTuned = false;
            TvRecordingClient.this.mIsRecordingStarted = false;
            TvRecordingClient.this.mIsPaused = false;
            TvRecordingClient.this.mIsRecordingStopping = false;
            TvRecordingClient.this.mSessionCallback = null;
            TvRecordingClient.this.mSession = null;
            if (TvRecordingClient.this.mCallback != null) {
                TvRecordingClient.this.mCallback.onDisconnected(this.mInputId);
            }
            if (TvRecordingClient.this.mTvIAppView != null) {
                TvRecordingClient.this.mTvIAppView.notifyRecordingDisconnected(TvRecordingClient.this.mRecordingId, this.mInputId);
            }
        }

        @Override // android.media.tv.TvInputManager.SessionCallback
        public void onRecordingStopped(TvInputManager.Session session, Uri recordedProgramUri) {
            if (this != TvRecordingClient.this.mSessionCallback) {
                Log.w(TvRecordingClient.TAG, "onRecordingStopped - session not created");
                return;
            }
            if (!TvRecordingClient.this.mIsRecordingStarted) {
                Log.w(TvRecordingClient.TAG, "onRecordingStopped - recording not yet started");
                return;
            }
            TvRecordingClient.this.mIsRecordingStarted = false;
            TvRecordingClient.this.mIsPaused = false;
            TvRecordingClient.this.mIsRecordingStopping = false;
            if (TvRecordingClient.this.mCallback != null) {
                TvRecordingClient.this.mCallback.onRecordingStopped(recordedProgramUri);
            }
            if (TvRecordingClient.this.mTvIAppView != null) {
                TvRecordingClient.this.mTvIAppView.notifyRecordingStopped(TvRecordingClient.this.mRecordingId);
            }
        }

        @Override // android.media.tv.TvInputManager.SessionCallback
        public void onError(TvInputManager.Session session, int error) {
            if (this != TvRecordingClient.this.mSessionCallback) {
                Log.w(TvRecordingClient.TAG, "onError - session not created");
                return;
            }
            if (TvRecordingClient.this.mCallback != null) {
                TvRecordingClient.this.mCallback.onError(error);
            }
            if (TvRecordingClient.this.mTvIAppView != null) {
                TvRecordingClient.this.mTvIAppView.notifyRecordingError(TvRecordingClient.this.mRecordingId, error);
            }
        }

        @Override // android.media.tv.TvInputManager.SessionCallback
        public void onSessionEvent(TvInputManager.Session session, String eventType, Bundle eventArgs) {
            if (this != TvRecordingClient.this.mSessionCallback) {
                Log.w(TvRecordingClient.TAG, "onSessionEvent - session not created");
            } else if (TvRecordingClient.this.mCallback != null) {
                TvRecordingClient.this.mCallback.onEvent(this.mInputId, eventType, eventArgs);
            }
        }
    }
}
