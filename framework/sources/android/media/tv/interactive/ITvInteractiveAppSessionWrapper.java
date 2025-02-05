package android.media.tv.interactive;

import android.content.Context;
import android.graphics.Rect;
import android.media.PlaybackParams;
import android.media.tv.AdBuffer;
import android.media.tv.AdResponse;
import android.media.tv.BroadcastInfoResponse;
import android.media.tv.TvContentRating;
import android.media.tv.TvRecordingInfo;
import android.media.tv.TvTrackInfo;
import android.media.tv.interactive.ITvInteractiveAppSession;
import android.media.tv.interactive.TvInteractiveAppService;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.InputChannel;
import android.view.InputEvent;
import android.view.InputEventReceiver;
import android.view.Surface;
import com.android.internal.os.HandlerCaller;
import com.android.internal.os.SomeArgs;
import java.util.List;

/* loaded from: classes3.dex */
public class ITvInteractiveAppSessionWrapper extends ITvInteractiveAppSession.Stub implements HandlerCaller.Callback {
    private static final int DO_CREATE_BI_INTERACTIVE_APP = 5;
    private static final int DO_CREATE_MEDIA_VIEW = 27;
    private static final int DO_DESTROY_BI_INTERACTIVE_APP = 6;
    private static final int DO_DISPATCH_SURFACE_CHANGED = 24;
    private static final int DO_NOTIFY_AD_BUFFER_CONSUMED = 32;
    private static final int DO_NOTIFY_AD_RESPONSE = 26;
    private static final int DO_NOTIFY_BROADCAST_INFO_RESPONSE = 25;
    private static final int DO_NOTIFY_CONTENT_ALLOWED = 20;
    private static final int DO_NOTIFY_CONTENT_BLOCKED = 21;
    private static final int DO_NOTIFY_ERROR = 14;
    private static final int DO_NOTIFY_RECORDING_CONNECTION_FAILED = 41;
    private static final int DO_NOTIFY_RECORDING_DISCONNECTED = 42;
    private static final int DO_NOTIFY_RECORDING_ERROR = 44;
    private static final int DO_NOTIFY_RECORDING_SCHEDULED = 45;
    private static final int DO_NOTIFY_RECORDING_STARTED = 30;
    private static final int DO_NOTIFY_RECORDING_STOPPED = 31;
    private static final int DO_NOTIFY_RECORDING_TUNED = 43;
    private static final int DO_NOTIFY_SIGNAL_STRENGTH = 22;
    private static final int DO_NOTIFY_TIME_SHIFT_CURRENT_POSITION_CHANGED = 39;
    private static final int DO_NOTIFY_TIME_SHIFT_PLAYBACK_PARAMS = 36;
    private static final int DO_NOTIFY_TIME_SHIFT_START_POSITION_CHANGED = 38;
    private static final int DO_NOTIFY_TIME_SHIFT_STATUS_CHANGED = 37;
    private static final int DO_NOTIFY_TRACKS_CHANGED = 17;
    private static final int DO_NOTIFY_TRACK_SELECTED = 16;
    private static final int DO_NOTIFY_TUNED = 15;
    private static final int DO_NOTIFY_TV_MESSAGE = 33;
    private static final int DO_NOTIFY_VIDEO_AVAILABLE = 18;
    private static final int DO_NOTIFY_VIDEO_FREEZE_UPDATED = 49;
    private static final int DO_NOTIFY_VIDEO_UNAVAILABLE = 19;
    private static final int DO_RELAYOUT_MEDIA_VIEW = 28;
    private static final int DO_RELEASE = 1;
    private static final int DO_REMOVE_MEDIA_VIEW = 29;
    private static final int DO_RESET_INTERACTIVE_APP = 4;
    private static final int DO_SEND_AVAILABLE_SPEEDS = 47;
    private static final int DO_SEND_CERTIFICATE = 50;
    private static final int DO_SEND_CURRENT_CHANNEL_LCN = 9;
    private static final int DO_SEND_CURRENT_CHANNEL_URI = 8;
    private static final int DO_SEND_CURRENT_TV_INPUT_ID = 12;
    private static final int DO_SEND_CURRENT_VIDEO_BOUNDS = 40;
    private static final int DO_SEND_RECORDING_INFO = 34;
    private static final int DO_SEND_RECORDING_INFO_LIST = 35;
    private static final int DO_SEND_SELECTED_TRACK_INFO = 48;
    private static final int DO_SEND_SIGNING_RESULT = 13;
    private static final int DO_SEND_STREAM_VOLUME = 10;
    private static final int DO_SEND_TIME_SHIFT_MODE = 46;
    private static final int DO_SEND_TRACK_INFO_LIST = 11;
    private static final int DO_SET_SURFACE = 23;
    private static final int DO_SET_TELETEXT_APP_ENABLED = 7;
    private static final int DO_START_INTERACTIVE_APP = 2;
    private static final int DO_STOP_INTERACTIVE_APP = 3;
    private static final int EXECUTE_MESSAGE_TIMEOUT_LONG_MILLIS = 5000;
    private static final int EXECUTE_MESSAGE_TIMEOUT_SHORT_MILLIS = 1000;
    private static final String TAG = "ITvInteractiveAppSessionWrapper";
    private final HandlerCaller mCaller;
    private InputChannel mChannel;
    private TvInteractiveAppEventReceiver mReceiver;
    private TvInteractiveAppService.Session mSessionImpl;

    public ITvInteractiveAppSessionWrapper(Context context, TvInteractiveAppService.Session mSessionImpl, InputChannel channel) {
        this.mSessionImpl = mSessionImpl;
        this.mCaller = new HandlerCaller(context, null, this, true);
        this.mChannel = channel;
        if (channel != null) {
            this.mReceiver = new TvInteractiveAppEventReceiver(channel, context.getMainLooper());
        }
    }

    @Override // com.android.internal.os.HandlerCaller.Callback
    public void executeMessage(Message msg) {
        if (this.mSessionImpl == null) {
            return;
        }
        long startTime = System.nanoTime();
        switch (msg.what) {
            case 1:
                this.mSessionImpl.release();
                this.mSessionImpl = null;
                if (this.mReceiver != null) {
                    this.mReceiver.dispose();
                    this.mReceiver = null;
                }
                if (this.mChannel != null) {
                    this.mChannel.dispose();
                    this.mChannel = null;
                    break;
                }
                break;
            case 2:
                this.mSessionImpl.startInteractiveApp();
                break;
            case 3:
                this.mSessionImpl.stopInteractiveApp();
                break;
            case 4:
                this.mSessionImpl.resetInteractiveApp();
                break;
            case 5:
                SomeArgs args = (SomeArgs) msg.obj;
                this.mSessionImpl.createBiInteractiveApp((Uri) args.arg1, (Bundle) args.arg2);
                args.recycle();
                break;
            case 6:
                this.mSessionImpl.destroyBiInteractiveApp((String) msg.obj);
                break;
            case 7:
                this.mSessionImpl.setTeletextAppEnabled(((Boolean) msg.obj).booleanValue());
                break;
            case 8:
                this.mSessionImpl.sendCurrentChannelUri((Uri) msg.obj);
                break;
            case 9:
                this.mSessionImpl.sendCurrentChannelLcn(((Integer) msg.obj).intValue());
                break;
            case 10:
                this.mSessionImpl.sendStreamVolume(((Float) msg.obj).floatValue());
                break;
            case 11:
                this.mSessionImpl.sendTrackInfoList((List) msg.obj);
                break;
            case 12:
                this.mSessionImpl.sendCurrentTvInputId((String) msg.obj);
                break;
            case 13:
                SomeArgs args2 = (SomeArgs) msg.obj;
                this.mSessionImpl.sendSigningResult((String) args2.arg1, (byte[]) args2.arg2);
                args2.recycle();
                break;
            case 14:
                SomeArgs args3 = (SomeArgs) msg.obj;
                this.mSessionImpl.notifyError((String) args3.arg1, (Bundle) args3.arg2);
                args3.recycle();
                break;
            case 15:
                this.mSessionImpl.notifyTuned((Uri) msg.obj);
                break;
            case 16:
                SomeArgs args4 = (SomeArgs) msg.obj;
                this.mSessionImpl.notifyTrackSelected(((Integer) args4.arg1).intValue(), (String) args4.arg2);
                args4.recycle();
                break;
            case 17:
                this.mSessionImpl.notifyTracksChanged((List) msg.obj);
                break;
            case 18:
                this.mSessionImpl.notifyVideoAvailable();
                break;
            case 19:
                this.mSessionImpl.notifyVideoUnavailable(((Integer) msg.obj).intValue());
                break;
            case 20:
                this.mSessionImpl.notifyContentAllowed();
                break;
            case 21:
                this.mSessionImpl.notifyContentBlocked((TvContentRating) msg.obj);
                break;
            case 22:
                this.mSessionImpl.notifySignalStrength(((Integer) msg.obj).intValue());
                break;
            case 23:
                this.mSessionImpl.setSurface((Surface) msg.obj);
                break;
            case 24:
                SomeArgs args5 = (SomeArgs) msg.obj;
                this.mSessionImpl.dispatchSurfaceChanged(Integer.valueOf(args5.argi1).intValue(), Integer.valueOf(args5.argi2).intValue(), Integer.valueOf(args5.argi3).intValue());
                args5.recycle();
                break;
            case 25:
                this.mSessionImpl.notifyBroadcastInfoResponse((BroadcastInfoResponse) msg.obj);
                break;
            case 26:
                this.mSessionImpl.notifyAdResponse((AdResponse) msg.obj);
                break;
            case 27:
                SomeArgs args6 = (SomeArgs) msg.obj;
                this.mSessionImpl.createMediaView((IBinder) args6.arg1, (Rect) args6.arg2);
                args6.recycle();
                break;
            case 28:
                this.mSessionImpl.relayoutMediaView((Rect) msg.obj);
                break;
            case 29:
                this.mSessionImpl.removeMediaView(true);
                break;
            case 30:
                SomeArgs args7 = (SomeArgs) msg.obj;
                this.mSessionImpl.notifyRecordingStarted((String) args7.arg1, (String) args7.arg2);
                args7.recycle();
                break;
            case 31:
                this.mSessionImpl.notifyRecordingStopped((String) msg.obj);
                break;
            case 32:
                this.mSessionImpl.notifyAdBufferConsumed((AdBuffer) msg.obj);
                break;
            case 33:
                SomeArgs args8 = (SomeArgs) msg.obj;
                this.mSessionImpl.notifyTvMessage(((Integer) args8.arg1).intValue(), (Bundle) args8.arg2);
                args8.recycle();
                break;
            case 34:
                this.mSessionImpl.sendTvRecordingInfo((TvRecordingInfo) msg.obj);
                break;
            case 35:
                this.mSessionImpl.sendTvRecordingInfoList((List) msg.obj);
                break;
            case 36:
                this.mSessionImpl.notifyTimeShiftPlaybackParams((PlaybackParams) msg.obj);
                break;
            case 37:
                SomeArgs args9 = (SomeArgs) msg.obj;
                this.mSessionImpl.notifyTimeShiftStatusChanged((String) args9.arg1, ((Integer) args9.arg2).intValue());
                args9.recycle();
                break;
            case 38:
                SomeArgs args10 = (SomeArgs) msg.obj;
                this.mSessionImpl.notifyTimeShiftStartPositionChanged((String) args10.arg1, ((Long) args10.arg2).longValue());
                args10.recycle();
                break;
            case 39:
                SomeArgs args11 = (SomeArgs) msg.obj;
                this.mSessionImpl.notifyTimeShiftCurrentPositionChanged((String) args11.arg1, ((Long) args11.arg2).longValue());
                args11.recycle();
                break;
            case 40:
                this.mSessionImpl.sendCurrentVideoBounds((Rect) msg.obj);
                break;
            case 41:
                SomeArgs args12 = (SomeArgs) msg.obj;
                this.mSessionImpl.notifyRecordingConnectionFailed((String) args12.arg1, (String) args12.arg2);
                args12.recycle();
                break;
            case 42:
                SomeArgs args13 = (SomeArgs) msg.obj;
                this.mSessionImpl.notifyRecordingDisconnected((String) args13.arg1, (String) args13.arg2);
                args13.recycle();
                break;
            case 43:
                SomeArgs args14 = (SomeArgs) msg.obj;
                this.mSessionImpl.notifyRecordingTuned((String) args14.arg1, (Uri) args14.arg2);
                args14.recycle();
                break;
            case 44:
                SomeArgs args15 = (SomeArgs) msg.obj;
                this.mSessionImpl.notifyRecordingError((String) args15.arg1, ((Integer) args15.arg2).intValue());
                args15.recycle();
                break;
            case 45:
                SomeArgs args16 = (SomeArgs) msg.obj;
                this.mSessionImpl.notifyRecordingScheduled((String) args16.arg1, (String) args16.arg2);
                args16.recycle();
                break;
            case 46:
                this.mSessionImpl.sendTimeShiftMode(((Integer) msg.obj).intValue());
                break;
            case 47:
                this.mSessionImpl.sendAvailableSpeeds((float[]) msg.obj);
                break;
            case 48:
                this.mSessionImpl.sendSelectedTrackInfo((List) msg.obj);
                break;
            case 49:
                this.mSessionImpl.notifyVideoFreezeUpdated(((Boolean) msg.obj).booleanValue());
                break;
            case 50:
                SomeArgs args17 = (SomeArgs) msg.obj;
                this.mSessionImpl.sendCertificate((String) args17.arg1, ((Integer) args17.arg2).intValue(), (Bundle) args17.arg3);
                args17.recycle();
                break;
            default:
                Log.w(TAG, "Unhandled message code: " + msg.what);
                break;
        }
        long durationMs = (System.nanoTime() - startTime) / 1000000;
        if (durationMs > 1000) {
            Log.w(TAG, "Handling message (" + msg.what + ") took too long time (duration=" + durationMs + "ms)");
        }
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void startInteractiveApp() {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessage(2));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void stopInteractiveApp() {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessage(3));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void resetInteractiveApp() {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessage(4));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void createBiInteractiveApp(Uri biIAppUri, Bundle params) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageOO(5, biIAppUri, params));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void destroyBiInteractiveApp(String biIAppId) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(6, biIAppId));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void setTeletextAppEnabled(boolean enable) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(7, Boolean.valueOf(enable)));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void sendCurrentVideoBounds(Rect bounds) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(40, bounds));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void sendCurrentChannelUri(Uri channelUri) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(8, channelUri));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void sendCurrentChannelLcn(int lcn) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(9, Integer.valueOf(lcn)));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void sendStreamVolume(float volume) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(10, Float.valueOf(volume)));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void sendTrackInfoList(List<TvTrackInfo> tracks) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(11, tracks));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void sendCurrentTvInputId(String inputId) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(12, inputId));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void sendTimeShiftMode(int mode) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(46, Integer.valueOf(mode)));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void sendAvailableSpeeds(float[] speeds) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(47, speeds));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void sendTvRecordingInfo(TvRecordingInfo recordingInfo) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(34, recordingInfo));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void sendTvRecordingInfoList(List<TvRecordingInfo> recordingInfoList) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(35, recordingInfoList));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void sendSigningResult(String signingId, byte[] result) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageOO(13, signingId, result));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void sendCertificate(String host, int port, Bundle certBundle) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageOOO(50, host, Integer.valueOf(port), certBundle));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void notifyError(String errMsg, Bundle params) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageOO(14, errMsg, params));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void notifyTimeShiftPlaybackParams(PlaybackParams params) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(36, params));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void notifyTimeShiftStatusChanged(String inputId, int status) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageOO(37, inputId, Integer.valueOf(status)));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void notifyTimeShiftStartPositionChanged(String inputId, long timeMs) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageOO(38, inputId, Long.valueOf(timeMs)));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void notifyTimeShiftCurrentPositionChanged(String inputId, long timeMs) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageOO(39, inputId, Long.valueOf(timeMs)));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void release() {
        this.mSessionImpl.scheduleMediaViewCleanup();
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessage(1));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void notifyTuned(Uri channelUri) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(15, channelUri));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void notifyTrackSelected(int type, String trackId) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageOO(16, Integer.valueOf(type), trackId));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void notifyTvMessage(int type, Bundle data) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageOO(33, Integer.valueOf(type), data));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void sendSelectedTrackInfo(List<TvTrackInfo> tracks) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(48, tracks));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void notifyTracksChanged(List<TvTrackInfo> tracks) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(17, tracks));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void notifyVideoAvailable() {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessage(18));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void notifyVideoUnavailable(int reason) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(19, Integer.valueOf(reason)));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void notifyVideoFreezeUpdated(boolean isFrozen) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(49, Boolean.valueOf(isFrozen)));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void notifyContentAllowed() {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessage(20));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void notifyContentBlocked(String rating) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(21, rating));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void notifySignalStrength(int strength) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(22, Integer.valueOf(strength)));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void notifyRecordingStarted(String recordingId, String requestId) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageOO(30, recordingId, requestId));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void notifyRecordingStopped(String recordingId) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(31, recordingId));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void notifyRecordingConnectionFailed(String recordingId, String inputId) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageOO(41, recordingId, inputId));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void notifyRecordingDisconnected(String recordingId, String inputId) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageOO(42, recordingId, inputId));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void notifyRecordingTuned(String recordingId, Uri channelUri) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageOO(43, recordingId, channelUri));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void notifyRecordingError(String recordingId, int err) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageOO(44, recordingId, Integer.valueOf(err)));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void notifyRecordingScheduled(String recordingId, String requestId) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageOO(45, recordingId, requestId));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void setSurface(Surface surface) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(23, surface));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void dispatchSurfaceChanged(int format, int width, int height) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageIIII(24, format, width, height, 0));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void notifyBroadcastInfoResponse(BroadcastInfoResponse response) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(25, response));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void notifyAdResponse(AdResponse response) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(26, response));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void notifyAdBufferConsumed(AdBuffer buffer) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(32, buffer));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void createMediaView(IBinder windowToken, Rect frame) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageOO(27, windowToken, frame));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void relayoutMediaView(Rect frame) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(28, frame));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void removeMediaView() {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessage(29));
    }

    private final class TvInteractiveAppEventReceiver extends InputEventReceiver {
        TvInteractiveAppEventReceiver(InputChannel inputChannel, Looper looper) {
            super(inputChannel, looper);
        }

        @Override // android.view.InputEventReceiver
        public void onInputEvent(InputEvent event) {
            if (ITvInteractiveAppSessionWrapper.this.mSessionImpl == null) {
                finishInputEvent(event, false);
                return;
            }
            int handled = ITvInteractiveAppSessionWrapper.this.mSessionImpl.dispatchInputEvent(event, this);
            if (handled != -1) {
                finishInputEvent(event, handled == 1);
            }
        }
    }
}
