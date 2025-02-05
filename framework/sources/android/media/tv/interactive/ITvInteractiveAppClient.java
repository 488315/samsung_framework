package android.media.tv.interactive;

import android.graphics.Rect;
import android.media.tv.AdBuffer;
import android.media.tv.AdRequest;
import android.media.tv.BroadcastInfoRequest;
import android.media.tv.TvRecordingInfo;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.InputChannel;

/* loaded from: classes3.dex */
public interface ITvInteractiveAppClient extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.interactive.ITvInteractiveAppClient";

    void onAdBufferReady(AdBuffer adBuffer, int i) throws RemoteException;

    void onAdRequest(AdRequest adRequest, int i) throws RemoteException;

    void onBiInteractiveAppCreated(Uri uri, String str, int i) throws RemoteException;

    void onBroadcastInfoRequest(BroadcastInfoRequest broadcastInfoRequest, int i) throws RemoteException;

    void onCommandRequest(String str, Bundle bundle, int i) throws RemoteException;

    void onLayoutSurface(int i, int i2, int i3, int i4, int i5) throws RemoteException;

    void onRemoveBroadcastInfo(int i, int i2) throws RemoteException;

    void onRequestAvailableSpeeds(int i) throws RemoteException;

    void onRequestCertificate(String str, int i, int i2) throws RemoteException;

    void onRequestCurrentChannelLcn(int i) throws RemoteException;

    void onRequestCurrentChannelUri(int i) throws RemoteException;

    void onRequestCurrentTvInputId(int i) throws RemoteException;

    void onRequestCurrentVideoBounds(int i) throws RemoteException;

    void onRequestScheduleRecording(String str, String str2, Uri uri, Uri uri2, Bundle bundle, int i) throws RemoteException;

    void onRequestScheduleRecording2(String str, String str2, Uri uri, long j, long j2, int i, Bundle bundle, int i2) throws RemoteException;

    void onRequestSelectedTrackInfo(int i) throws RemoteException;

    void onRequestSigning(String str, String str2, String str3, byte[] bArr, int i) throws RemoteException;

    void onRequestSigning2(String str, String str2, String str3, int i, byte[] bArr, int i2) throws RemoteException;

    void onRequestStartRecording(String str, Uri uri, int i) throws RemoteException;

    void onRequestStopRecording(String str, int i) throws RemoteException;

    void onRequestStreamVolume(int i) throws RemoteException;

    void onRequestTimeShiftMode(int i) throws RemoteException;

    void onRequestTrackInfoList(int i) throws RemoteException;

    void onRequestTvRecordingInfo(String str, int i) throws RemoteException;

    void onRequestTvRecordingInfoList(int i, int i2) throws RemoteException;

    void onSessionCreated(String str, IBinder iBinder, InputChannel inputChannel, int i) throws RemoteException;

    void onSessionReleased(int i) throws RemoteException;

    void onSessionStateChanged(int i, int i2, int i3) throws RemoteException;

    void onSetTvRecordingInfo(String str, TvRecordingInfo tvRecordingInfo, int i) throws RemoteException;

    void onSetVideoBounds(Rect rect, int i) throws RemoteException;

    void onTeletextAppStateChanged(int i, int i2) throws RemoteException;

    void onTimeShiftCommandRequest(String str, Bundle bundle, int i) throws RemoteException;

    public static class Default implements ITvInteractiveAppClient {
        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onSessionCreated(String iAppServiceId, IBinder token, InputChannel channel, int seq) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onSessionReleased(int seq) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onLayoutSurface(int left, int top, int right, int bottom, int seq) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onBroadcastInfoRequest(BroadcastInfoRequest request, int seq) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRemoveBroadcastInfo(int id, int seq) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onSessionStateChanged(int state, int err, int seq) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onBiInteractiveAppCreated(Uri biIAppUri, String biIAppId, int seq) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onTeletextAppStateChanged(int state, int seq) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onAdBufferReady(AdBuffer buffer, int seq) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onCommandRequest(String cmdType, Bundle parameters, int seq) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onTimeShiftCommandRequest(String cmdType, Bundle parameters, int seq) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onSetVideoBounds(Rect rect, int seq) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestCurrentVideoBounds(int seq) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestCurrentChannelUri(int seq) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestCurrentChannelLcn(int seq) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestStreamVolume(int seq) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestTrackInfoList(int seq) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestSelectedTrackInfo(int seq) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestCurrentTvInputId(int seq) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestTimeShiftMode(int seq) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestAvailableSpeeds(int seq) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestStartRecording(String requestId, Uri programUri, int seq) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestStopRecording(String recordingId, int seq) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestScheduleRecording(String requestId, String inputId, Uri channelUri, Uri programUri, Bundle params, int seq) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestScheduleRecording2(String requestId, String inputId, Uri channelUri, long start, long duration, int repeat, Bundle params, int seq) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onSetTvRecordingInfo(String recordingId, TvRecordingInfo recordingInfo, int seq) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestTvRecordingInfo(String recordingId, int seq) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestTvRecordingInfoList(int type, int seq) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestSigning(String id, String algorithm, String alias, byte[] data, int seq) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestSigning2(String id, String algorithm, String host, int port, byte[] data, int seq) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestCertificate(String host, int port, int seq) throws RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onAdRequest(AdRequest request, int Seq) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ITvInteractiveAppClient {
        static final int TRANSACTION_onAdBufferReady = 9;
        static final int TRANSACTION_onAdRequest = 32;
        static final int TRANSACTION_onBiInteractiveAppCreated = 7;
        static final int TRANSACTION_onBroadcastInfoRequest = 4;
        static final int TRANSACTION_onCommandRequest = 10;
        static final int TRANSACTION_onLayoutSurface = 3;
        static final int TRANSACTION_onRemoveBroadcastInfo = 5;
        static final int TRANSACTION_onRequestAvailableSpeeds = 21;
        static final int TRANSACTION_onRequestCertificate = 31;
        static final int TRANSACTION_onRequestCurrentChannelLcn = 15;
        static final int TRANSACTION_onRequestCurrentChannelUri = 14;
        static final int TRANSACTION_onRequestCurrentTvInputId = 19;
        static final int TRANSACTION_onRequestCurrentVideoBounds = 13;
        static final int TRANSACTION_onRequestScheduleRecording = 24;
        static final int TRANSACTION_onRequestScheduleRecording2 = 25;
        static final int TRANSACTION_onRequestSelectedTrackInfo = 18;
        static final int TRANSACTION_onRequestSigning = 29;
        static final int TRANSACTION_onRequestSigning2 = 30;
        static final int TRANSACTION_onRequestStartRecording = 22;
        static final int TRANSACTION_onRequestStopRecording = 23;
        static final int TRANSACTION_onRequestStreamVolume = 16;
        static final int TRANSACTION_onRequestTimeShiftMode = 20;
        static final int TRANSACTION_onRequestTrackInfoList = 17;
        static final int TRANSACTION_onRequestTvRecordingInfo = 27;
        static final int TRANSACTION_onRequestTvRecordingInfoList = 28;
        static final int TRANSACTION_onSessionCreated = 1;
        static final int TRANSACTION_onSessionReleased = 2;
        static final int TRANSACTION_onSessionStateChanged = 6;
        static final int TRANSACTION_onSetTvRecordingInfo = 26;
        static final int TRANSACTION_onSetVideoBounds = 12;
        static final int TRANSACTION_onTeletextAppStateChanged = 8;
        static final int TRANSACTION_onTimeShiftCommandRequest = 11;

        public Stub() {
            attachInterface(this, ITvInteractiveAppClient.DESCRIPTOR);
        }

        public static ITvInteractiveAppClient asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ITvInteractiveAppClient.DESCRIPTOR);
            if (iin != null && (iin instanceof ITvInteractiveAppClient)) {
                return (ITvInteractiveAppClient) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "onSessionCreated";
                case 2:
                    return "onSessionReleased";
                case 3:
                    return "onLayoutSurface";
                case 4:
                    return "onBroadcastInfoRequest";
                case 5:
                    return "onRemoveBroadcastInfo";
                case 6:
                    return "onSessionStateChanged";
                case 7:
                    return "onBiInteractiveAppCreated";
                case 8:
                    return "onTeletextAppStateChanged";
                case 9:
                    return "onAdBufferReady";
                case 10:
                    return "onCommandRequest";
                case 11:
                    return "onTimeShiftCommandRequest";
                case 12:
                    return "onSetVideoBounds";
                case 13:
                    return "onRequestCurrentVideoBounds";
                case 14:
                    return "onRequestCurrentChannelUri";
                case 15:
                    return "onRequestCurrentChannelLcn";
                case 16:
                    return "onRequestStreamVolume";
                case 17:
                    return "onRequestTrackInfoList";
                case 18:
                    return "onRequestSelectedTrackInfo";
                case 19:
                    return "onRequestCurrentTvInputId";
                case 20:
                    return "onRequestTimeShiftMode";
                case 21:
                    return "onRequestAvailableSpeeds";
                case 22:
                    return "onRequestStartRecording";
                case 23:
                    return "onRequestStopRecording";
                case 24:
                    return "onRequestScheduleRecording";
                case 25:
                    return "onRequestScheduleRecording2";
                case 26:
                    return "onSetTvRecordingInfo";
                case 27:
                    return "onRequestTvRecordingInfo";
                case 28:
                    return "onRequestTvRecordingInfoList";
                case 29:
                    return "onRequestSigning";
                case 30:
                    return "onRequestSigning2";
                case 31:
                    return "onRequestCertificate";
                case 32:
                    return "onAdRequest";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(ITvInteractiveAppClient.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ITvInteractiveAppClient.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    String _arg0 = data.readString();
                    IBinder _arg1 = data.readStrongBinder();
                    InputChannel _arg2 = (InputChannel) data.readTypedObject(InputChannel.CREATOR);
                    int _arg3 = data.readInt();
                    data.enforceNoDataAvail();
                    onSessionCreated(_arg0, _arg1, _arg2, _arg3);
                    return true;
                case 2:
                    int _arg02 = data.readInt();
                    data.enforceNoDataAvail();
                    onSessionReleased(_arg02);
                    return true;
                case 3:
                    int _arg03 = data.readInt();
                    int _arg12 = data.readInt();
                    int _arg22 = data.readInt();
                    int _arg32 = data.readInt();
                    int _arg4 = data.readInt();
                    data.enforceNoDataAvail();
                    onLayoutSurface(_arg03, _arg12, _arg22, _arg32, _arg4);
                    return true;
                case 4:
                    BroadcastInfoRequest _arg04 = (BroadcastInfoRequest) data.readTypedObject(BroadcastInfoRequest.CREATOR);
                    int _arg13 = data.readInt();
                    data.enforceNoDataAvail();
                    onBroadcastInfoRequest(_arg04, _arg13);
                    return true;
                case 5:
                    int _arg05 = data.readInt();
                    int _arg14 = data.readInt();
                    data.enforceNoDataAvail();
                    onRemoveBroadcastInfo(_arg05, _arg14);
                    return true;
                case 6:
                    int _arg06 = data.readInt();
                    int _arg15 = data.readInt();
                    int _arg23 = data.readInt();
                    data.enforceNoDataAvail();
                    onSessionStateChanged(_arg06, _arg15, _arg23);
                    return true;
                case 7:
                    Uri _arg07 = (Uri) data.readTypedObject(Uri.CREATOR);
                    String _arg16 = data.readString();
                    int _arg24 = data.readInt();
                    data.enforceNoDataAvail();
                    onBiInteractiveAppCreated(_arg07, _arg16, _arg24);
                    return true;
                case 8:
                    int _arg08 = data.readInt();
                    int _arg17 = data.readInt();
                    data.enforceNoDataAvail();
                    onTeletextAppStateChanged(_arg08, _arg17);
                    return true;
                case 9:
                    AdBuffer _arg09 = (AdBuffer) data.readTypedObject(AdBuffer.CREATOR);
                    int _arg18 = data.readInt();
                    data.enforceNoDataAvail();
                    onAdBufferReady(_arg09, _arg18);
                    return true;
                case 10:
                    String _arg010 = data.readString();
                    Bundle _arg19 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    int _arg25 = data.readInt();
                    data.enforceNoDataAvail();
                    onCommandRequest(_arg010, _arg19, _arg25);
                    return true;
                case 11:
                    String _arg011 = data.readString();
                    Bundle _arg110 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    int _arg26 = data.readInt();
                    data.enforceNoDataAvail();
                    onTimeShiftCommandRequest(_arg011, _arg110, _arg26);
                    return true;
                case 12:
                    Rect _arg012 = (Rect) data.readTypedObject(Rect.CREATOR);
                    int _arg111 = data.readInt();
                    data.enforceNoDataAvail();
                    onSetVideoBounds(_arg012, _arg111);
                    return true;
                case 13:
                    int _arg013 = data.readInt();
                    data.enforceNoDataAvail();
                    onRequestCurrentVideoBounds(_arg013);
                    return true;
                case 14:
                    int _arg014 = data.readInt();
                    data.enforceNoDataAvail();
                    onRequestCurrentChannelUri(_arg014);
                    return true;
                case 15:
                    int _arg015 = data.readInt();
                    data.enforceNoDataAvail();
                    onRequestCurrentChannelLcn(_arg015);
                    return true;
                case 16:
                    int _arg016 = data.readInt();
                    data.enforceNoDataAvail();
                    onRequestStreamVolume(_arg016);
                    return true;
                case 17:
                    int _arg017 = data.readInt();
                    data.enforceNoDataAvail();
                    onRequestTrackInfoList(_arg017);
                    return true;
                case 18:
                    int _arg018 = data.readInt();
                    data.enforceNoDataAvail();
                    onRequestSelectedTrackInfo(_arg018);
                    return true;
                case 19:
                    int _arg019 = data.readInt();
                    data.enforceNoDataAvail();
                    onRequestCurrentTvInputId(_arg019);
                    return true;
                case 20:
                    int _arg020 = data.readInt();
                    data.enforceNoDataAvail();
                    onRequestTimeShiftMode(_arg020);
                    return true;
                case 21:
                    int _arg021 = data.readInt();
                    data.enforceNoDataAvail();
                    onRequestAvailableSpeeds(_arg021);
                    return true;
                case 22:
                    String _arg022 = data.readString();
                    Uri _arg112 = (Uri) data.readTypedObject(Uri.CREATOR);
                    int _arg27 = data.readInt();
                    data.enforceNoDataAvail();
                    onRequestStartRecording(_arg022, _arg112, _arg27);
                    return true;
                case 23:
                    String _arg023 = data.readString();
                    int _arg113 = data.readInt();
                    data.enforceNoDataAvail();
                    onRequestStopRecording(_arg023, _arg113);
                    return true;
                case 24:
                    String _arg024 = data.readString();
                    String _arg114 = data.readString();
                    Uri _arg28 = (Uri) data.readTypedObject(Uri.CREATOR);
                    Uri _arg33 = (Uri) data.readTypedObject(Uri.CREATOR);
                    Bundle _arg42 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    int _arg5 = data.readInt();
                    data.enforceNoDataAvail();
                    onRequestScheduleRecording(_arg024, _arg114, _arg28, _arg33, _arg42, _arg5);
                    return true;
                case 25:
                    String _arg025 = data.readString();
                    String _arg115 = data.readString();
                    Uri _arg29 = (Uri) data.readTypedObject(Uri.CREATOR);
                    long _arg34 = data.readLong();
                    long _arg43 = data.readLong();
                    int _arg52 = data.readInt();
                    Bundle _arg6 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    int _arg7 = data.readInt();
                    data.enforceNoDataAvail();
                    onRequestScheduleRecording2(_arg025, _arg115, _arg29, _arg34, _arg43, _arg52, _arg6, _arg7);
                    return true;
                case 26:
                    String _arg026 = data.readString();
                    TvRecordingInfo _arg116 = (TvRecordingInfo) data.readTypedObject(TvRecordingInfo.CREATOR);
                    int _arg210 = data.readInt();
                    data.enforceNoDataAvail();
                    onSetTvRecordingInfo(_arg026, _arg116, _arg210);
                    return true;
                case 27:
                    String _arg027 = data.readString();
                    int _arg117 = data.readInt();
                    data.enforceNoDataAvail();
                    onRequestTvRecordingInfo(_arg027, _arg117);
                    return true;
                case 28:
                    int _arg028 = data.readInt();
                    int _arg118 = data.readInt();
                    data.enforceNoDataAvail();
                    onRequestTvRecordingInfoList(_arg028, _arg118);
                    return true;
                case 29:
                    String _arg029 = data.readString();
                    String _arg119 = data.readString();
                    String _arg211 = data.readString();
                    byte[] _arg35 = data.createByteArray();
                    int _arg44 = data.readInt();
                    data.enforceNoDataAvail();
                    onRequestSigning(_arg029, _arg119, _arg211, _arg35, _arg44);
                    return true;
                case 30:
                    String _arg030 = data.readString();
                    String _arg120 = data.readString();
                    String _arg212 = data.readString();
                    int _arg36 = data.readInt();
                    byte[] _arg45 = data.createByteArray();
                    int _arg53 = data.readInt();
                    data.enforceNoDataAvail();
                    onRequestSigning2(_arg030, _arg120, _arg212, _arg36, _arg45, _arg53);
                    return true;
                case 31:
                    String _arg031 = data.readString();
                    int _arg121 = data.readInt();
                    int _arg213 = data.readInt();
                    data.enforceNoDataAvail();
                    onRequestCertificate(_arg031, _arg121, _arg213);
                    return true;
                case 32:
                    AdRequest _arg032 = (AdRequest) data.readTypedObject(AdRequest.CREATOR);
                    int _arg122 = data.readInt();
                    data.enforceNoDataAvail();
                    onAdRequest(_arg032, _arg122);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ITvInteractiveAppClient {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ITvInteractiveAppClient.DESCRIPTOR;
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onSessionCreated(String iAppServiceId, IBinder token, InputChannel channel, int seq) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    _data.writeString(iAppServiceId);
                    _data.writeStrongBinder(token);
                    _data.writeTypedObject(channel, 0);
                    _data.writeInt(seq);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onSessionReleased(int seq) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    _data.writeInt(seq);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onLayoutSurface(int left, int top, int right, int bottom, int seq) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    _data.writeInt(left);
                    _data.writeInt(top);
                    _data.writeInt(right);
                    _data.writeInt(bottom);
                    _data.writeInt(seq);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onBroadcastInfoRequest(BroadcastInfoRequest request, int seq) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    _data.writeTypedObject(request, 0);
                    _data.writeInt(seq);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onRemoveBroadcastInfo(int id, int seq) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    _data.writeInt(id);
                    _data.writeInt(seq);
                    this.mRemote.transact(5, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onSessionStateChanged(int state, int err, int seq) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    _data.writeInt(state);
                    _data.writeInt(err);
                    _data.writeInt(seq);
                    this.mRemote.transact(6, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onBiInteractiveAppCreated(Uri biIAppUri, String biIAppId, int seq) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    _data.writeTypedObject(biIAppUri, 0);
                    _data.writeString(biIAppId);
                    _data.writeInt(seq);
                    this.mRemote.transact(7, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onTeletextAppStateChanged(int state, int seq) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    _data.writeInt(state);
                    _data.writeInt(seq);
                    this.mRemote.transact(8, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onAdBufferReady(AdBuffer buffer, int seq) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    _data.writeTypedObject(buffer, 0);
                    _data.writeInt(seq);
                    this.mRemote.transact(9, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onCommandRequest(String cmdType, Bundle parameters, int seq) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    _data.writeString(cmdType);
                    _data.writeTypedObject(parameters, 0);
                    _data.writeInt(seq);
                    this.mRemote.transact(10, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onTimeShiftCommandRequest(String cmdType, Bundle parameters, int seq) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    _data.writeString(cmdType);
                    _data.writeTypedObject(parameters, 0);
                    _data.writeInt(seq);
                    this.mRemote.transact(11, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onSetVideoBounds(Rect rect, int seq) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    _data.writeTypedObject(rect, 0);
                    _data.writeInt(seq);
                    this.mRemote.transact(12, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onRequestCurrentVideoBounds(int seq) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    _data.writeInt(seq);
                    this.mRemote.transact(13, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onRequestCurrentChannelUri(int seq) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    _data.writeInt(seq);
                    this.mRemote.transact(14, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onRequestCurrentChannelLcn(int seq) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    _data.writeInt(seq);
                    this.mRemote.transact(15, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onRequestStreamVolume(int seq) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    _data.writeInt(seq);
                    this.mRemote.transact(16, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onRequestTrackInfoList(int seq) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    _data.writeInt(seq);
                    this.mRemote.transact(17, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onRequestSelectedTrackInfo(int seq) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    _data.writeInt(seq);
                    this.mRemote.transact(18, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onRequestCurrentTvInputId(int seq) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    _data.writeInt(seq);
                    this.mRemote.transact(19, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onRequestTimeShiftMode(int seq) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    _data.writeInt(seq);
                    this.mRemote.transact(20, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onRequestAvailableSpeeds(int seq) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    _data.writeInt(seq);
                    this.mRemote.transact(21, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onRequestStartRecording(String requestId, Uri programUri, int seq) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    _data.writeString(requestId);
                    _data.writeTypedObject(programUri, 0);
                    _data.writeInt(seq);
                    this.mRemote.transact(22, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onRequestStopRecording(String recordingId, int seq) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    _data.writeString(recordingId);
                    _data.writeInt(seq);
                    this.mRemote.transact(23, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onRequestScheduleRecording(String requestId, String inputId, Uri channelUri, Uri programUri, Bundle params, int seq) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    _data.writeString(requestId);
                    _data.writeString(inputId);
                    _data.writeTypedObject(channelUri, 0);
                    _data.writeTypedObject(programUri, 0);
                    _data.writeTypedObject(params, 0);
                    _data.writeInt(seq);
                    this.mRemote.transact(24, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onRequestScheduleRecording2(String requestId, String inputId, Uri channelUri, long start, long duration, int repeat, Bundle params, int seq) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    _data.writeString(requestId);
                    _data.writeString(inputId);
                    _data.writeTypedObject(channelUri, 0);
                    _data.writeLong(start);
                    _data.writeLong(duration);
                    _data.writeInt(repeat);
                    _data.writeTypedObject(params, 0);
                    _data.writeInt(seq);
                    this.mRemote.transact(25, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onSetTvRecordingInfo(String recordingId, TvRecordingInfo recordingInfo, int seq) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    _data.writeString(recordingId);
                    _data.writeTypedObject(recordingInfo, 0);
                    _data.writeInt(seq);
                    this.mRemote.transact(26, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onRequestTvRecordingInfo(String recordingId, int seq) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    _data.writeString(recordingId);
                    _data.writeInt(seq);
                    this.mRemote.transact(27, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onRequestTvRecordingInfoList(int type, int seq) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeInt(seq);
                    this.mRemote.transact(28, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onRequestSigning(String id, String algorithm, String alias, byte[] data, int seq) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    _data.writeString(id);
                    _data.writeString(algorithm);
                    _data.writeString(alias);
                    _data.writeByteArray(data);
                    _data.writeInt(seq);
                    this.mRemote.transact(29, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onRequestSigning2(String id, String algorithm, String host, int port, byte[] data, int seq) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    _data.writeString(id);
                    _data.writeString(algorithm);
                    _data.writeString(host);
                    _data.writeInt(port);
                    _data.writeByteArray(data);
                    _data.writeInt(seq);
                    this.mRemote.transact(30, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onRequestCertificate(String host, int port, int seq) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    _data.writeString(host);
                    _data.writeInt(port);
                    _data.writeInt(seq);
                    this.mRemote.transact(31, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onAdRequest(AdRequest request, int Seq) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvInteractiveAppClient.DESCRIPTOR);
                    _data.writeTypedObject(request, 0);
                    _data.writeInt(Seq);
                    this.mRemote.transact(32, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 31;
        }
    }
}
