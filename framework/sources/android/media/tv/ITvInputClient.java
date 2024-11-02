package android.media.tv;

import android.media.AudioPresentation;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.InputChannel;
import java.util.List;

/* loaded from: classes2.dex */
public interface ITvInputClient extends IInterface {
    void onAdBufferConsumed(AdBuffer adBuffer, int i) throws RemoteException;

    void onAdResponse(AdResponse adResponse, int i) throws RemoteException;

    void onAitInfoUpdated(AitInfo aitInfo, int i) throws RemoteException;

    void onAudioPresentationSelected(int i, int i2, int i3) throws RemoteException;

    void onAudioPresentationsChanged(List<AudioPresentation> list, int i) throws RemoteException;

    void onAvailableSpeeds(float[] fArr, int i) throws RemoteException;

    void onBroadcastInfoResponse(BroadcastInfoResponse broadcastInfoResponse, int i) throws RemoteException;

    void onChannelRetuned(Uri uri, int i) throws RemoteException;

    void onContentAllowed(int i) throws RemoteException;

    void onContentBlocked(String str, int i) throws RemoteException;

    void onCueingMessageAvailability(boolean z, int i) throws RemoteException;

    void onError(int i, int i2) throws RemoteException;

    void onLayoutSurface(int i, int i2, int i3, int i4, int i5) throws RemoteException;

    void onRecordingStopped(Uri uri, int i) throws RemoteException;

    void onSessionCreated(String str, IBinder iBinder, InputChannel inputChannel, int i) throws RemoteException;

    void onSessionEvent(String str, Bundle bundle, int i) throws RemoteException;

    void onSessionReleased(int i) throws RemoteException;

    void onSignalStrength(int i, int i2) throws RemoteException;

    void onTimeShiftCurrentPositionChanged(long j, int i) throws RemoteException;

    void onTimeShiftMode(int i, int i2) throws RemoteException;

    void onTimeShiftStartPositionChanged(long j, int i) throws RemoteException;

    void onTimeShiftStatusChanged(int i, int i2) throws RemoteException;

    void onTrackSelected(int i, String str, int i2) throws RemoteException;

    void onTracksChanged(List<TvTrackInfo> list, int i) throws RemoteException;

    void onTuned(Uri uri, int i) throws RemoteException;

    void onTvMessage(int i, Bundle bundle, int i2) throws RemoteException;

    void onVideoAvailable(int i) throws RemoteException;

    void onVideoUnavailable(int i, int i2) throws RemoteException;

    /* loaded from: classes2.dex */
    public static class Default implements ITvInputClient {
        @Override // android.media.tv.ITvInputClient
        public void onSessionCreated(String inputId, IBinder token, InputChannel channel, int seq) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputClient
        public void onSessionReleased(int seq) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputClient
        public void onSessionEvent(String name, Bundle args, int seq) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputClient
        public void onChannelRetuned(Uri channelUri, int seq) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputClient
        public void onAudioPresentationsChanged(List<AudioPresentation> AudioPresentations, int seq) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputClient
        public void onAudioPresentationSelected(int presentationId, int programId, int seq) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputClient
        public void onTracksChanged(List<TvTrackInfo> tracks, int seq) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputClient
        public void onTrackSelected(int type, String trackId, int seq) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputClient
        public void onVideoAvailable(int seq) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputClient
        public void onVideoUnavailable(int reason, int seq) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputClient
        public void onContentAllowed(int seq) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputClient
        public void onContentBlocked(String rating, int seq) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputClient
        public void onLayoutSurface(int left, int top, int right, int bottom, int seq) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputClient
        public void onTimeShiftStatusChanged(int status, int seq) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputClient
        public void onTimeShiftStartPositionChanged(long timeMs, int seq) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputClient
        public void onTimeShiftCurrentPositionChanged(long timeMs, int seq) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputClient
        public void onAitInfoUpdated(AitInfo aitInfo, int seq) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputClient
        public void onSignalStrength(int stength, int seq) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputClient
        public void onCueingMessageAvailability(boolean available, int seq) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputClient
        public void onTimeShiftMode(int mode, int seq) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputClient
        public void onAvailableSpeeds(float[] speeds, int seq) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputClient
        public void onTvMessage(int type, Bundle data, int seq) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputClient
        public void onTuned(Uri channelUri, int seq) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputClient
        public void onRecordingStopped(Uri recordedProgramUri, int seq) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputClient
        public void onError(int error, int seq) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputClient
        public void onBroadcastInfoResponse(BroadcastInfoResponse response, int seq) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputClient
        public void onAdResponse(AdResponse response, int seq) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputClient
        public void onAdBufferConsumed(AdBuffer buffer, int seq) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes2.dex */
    public static abstract class Stub extends Binder implements ITvInputClient {
        public static final String DESCRIPTOR = "android.media.tv.ITvInputClient";
        static final int TRANSACTION_onAdBufferConsumed = 28;
        static final int TRANSACTION_onAdResponse = 27;
        static final int TRANSACTION_onAitInfoUpdated = 17;
        static final int TRANSACTION_onAudioPresentationSelected = 6;
        static final int TRANSACTION_onAudioPresentationsChanged = 5;
        static final int TRANSACTION_onAvailableSpeeds = 21;
        static final int TRANSACTION_onBroadcastInfoResponse = 26;
        static final int TRANSACTION_onChannelRetuned = 4;
        static final int TRANSACTION_onContentAllowed = 11;
        static final int TRANSACTION_onContentBlocked = 12;
        static final int TRANSACTION_onCueingMessageAvailability = 19;
        static final int TRANSACTION_onError = 25;
        static final int TRANSACTION_onLayoutSurface = 13;
        static final int TRANSACTION_onRecordingStopped = 24;
        static final int TRANSACTION_onSessionCreated = 1;
        static final int TRANSACTION_onSessionEvent = 3;
        static final int TRANSACTION_onSessionReleased = 2;
        static final int TRANSACTION_onSignalStrength = 18;
        static final int TRANSACTION_onTimeShiftCurrentPositionChanged = 16;
        static final int TRANSACTION_onTimeShiftMode = 20;
        static final int TRANSACTION_onTimeShiftStartPositionChanged = 15;
        static final int TRANSACTION_onTimeShiftStatusChanged = 14;
        static final int TRANSACTION_onTrackSelected = 8;
        static final int TRANSACTION_onTracksChanged = 7;
        static final int TRANSACTION_onTuned = 23;
        static final int TRANSACTION_onTvMessage = 22;
        static final int TRANSACTION_onVideoAvailable = 9;
        static final int TRANSACTION_onVideoUnavailable = 10;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ITvInputClient asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof ITvInputClient)) {
                return (ITvInputClient) iin;
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
                    return "onSessionEvent";
                case 4:
                    return "onChannelRetuned";
                case 5:
                    return "onAudioPresentationsChanged";
                case 6:
                    return "onAudioPresentationSelected";
                case 7:
                    return "onTracksChanged";
                case 8:
                    return "onTrackSelected";
                case 9:
                    return "onVideoAvailable";
                case 10:
                    return "onVideoUnavailable";
                case 11:
                    return "onContentAllowed";
                case 12:
                    return "onContentBlocked";
                case 13:
                    return "onLayoutSurface";
                case 14:
                    return "onTimeShiftStatusChanged";
                case 15:
                    return "onTimeShiftStartPositionChanged";
                case 16:
                    return "onTimeShiftCurrentPositionChanged";
                case 17:
                    return "onAitInfoUpdated";
                case 18:
                    return "onSignalStrength";
                case 19:
                    return "onCueingMessageAvailability";
                case 20:
                    return "onTimeShiftMode";
                case 21:
                    return "onAvailableSpeeds";
                case 22:
                    return "onTvMessage";
                case 23:
                    return "onTuned";
                case 24:
                    return "onRecordingStopped";
                case 25:
                    return "onError";
                case 26:
                    return "onBroadcastInfoResponse";
                case 27:
                    return "onAdResponse";
                case 28:
                    return "onAdBufferConsumed";
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
                data.enforceInterface(DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(DESCRIPTOR);
                    return true;
                default:
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
                            String _arg03 = data.readString();
                            Bundle _arg12 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                            int _arg22 = data.readInt();
                            data.enforceNoDataAvail();
                            onSessionEvent(_arg03, _arg12, _arg22);
                            return true;
                        case 4:
                            Uri _arg04 = (Uri) data.readTypedObject(Uri.CREATOR);
                            int _arg13 = data.readInt();
                            data.enforceNoDataAvail();
                            onChannelRetuned(_arg04, _arg13);
                            return true;
                        case 5:
                            List<AudioPresentation> _arg05 = data.createTypedArrayList(AudioPresentation.CREATOR);
                            int _arg14 = data.readInt();
                            data.enforceNoDataAvail();
                            onAudioPresentationsChanged(_arg05, _arg14);
                            return true;
                        case 6:
                            int _arg06 = data.readInt();
                            int _arg15 = data.readInt();
                            int _arg23 = data.readInt();
                            data.enforceNoDataAvail();
                            onAudioPresentationSelected(_arg06, _arg15, _arg23);
                            return true;
                        case 7:
                            List<TvTrackInfo> _arg07 = data.createTypedArrayList(TvTrackInfo.CREATOR);
                            int _arg16 = data.readInt();
                            data.enforceNoDataAvail();
                            onTracksChanged(_arg07, _arg16);
                            return true;
                        case 8:
                            int _arg08 = data.readInt();
                            String _arg17 = data.readString();
                            int _arg24 = data.readInt();
                            data.enforceNoDataAvail();
                            onTrackSelected(_arg08, _arg17, _arg24);
                            return true;
                        case 9:
                            int _arg09 = data.readInt();
                            data.enforceNoDataAvail();
                            onVideoAvailable(_arg09);
                            return true;
                        case 10:
                            int _arg010 = data.readInt();
                            int _arg18 = data.readInt();
                            data.enforceNoDataAvail();
                            onVideoUnavailable(_arg010, _arg18);
                            return true;
                        case 11:
                            int _arg011 = data.readInt();
                            data.enforceNoDataAvail();
                            onContentAllowed(_arg011);
                            return true;
                        case 12:
                            String _arg012 = data.readString();
                            int _arg19 = data.readInt();
                            data.enforceNoDataAvail();
                            onContentBlocked(_arg012, _arg19);
                            return true;
                        case 13:
                            int _arg013 = data.readInt();
                            int _arg110 = data.readInt();
                            int _arg25 = data.readInt();
                            int _arg32 = data.readInt();
                            int _arg4 = data.readInt();
                            data.enforceNoDataAvail();
                            onLayoutSurface(_arg013, _arg110, _arg25, _arg32, _arg4);
                            return true;
                        case 14:
                            int _arg014 = data.readInt();
                            int _arg111 = data.readInt();
                            data.enforceNoDataAvail();
                            onTimeShiftStatusChanged(_arg014, _arg111);
                            return true;
                        case 15:
                            long _arg015 = data.readLong();
                            int _arg112 = data.readInt();
                            data.enforceNoDataAvail();
                            onTimeShiftStartPositionChanged(_arg015, _arg112);
                            return true;
                        case 16:
                            long _arg016 = data.readLong();
                            int _arg113 = data.readInt();
                            data.enforceNoDataAvail();
                            onTimeShiftCurrentPositionChanged(_arg016, _arg113);
                            return true;
                        case 17:
                            AitInfo _arg017 = (AitInfo) data.readTypedObject(AitInfo.CREATOR);
                            int _arg114 = data.readInt();
                            data.enforceNoDataAvail();
                            onAitInfoUpdated(_arg017, _arg114);
                            return true;
                        case 18:
                            int _arg018 = data.readInt();
                            int _arg115 = data.readInt();
                            data.enforceNoDataAvail();
                            onSignalStrength(_arg018, _arg115);
                            return true;
                        case 19:
                            boolean _arg019 = data.readBoolean();
                            int _arg116 = data.readInt();
                            data.enforceNoDataAvail();
                            onCueingMessageAvailability(_arg019, _arg116);
                            return true;
                        case 20:
                            int _arg020 = data.readInt();
                            int _arg117 = data.readInt();
                            data.enforceNoDataAvail();
                            onTimeShiftMode(_arg020, _arg117);
                            return true;
                        case 21:
                            float[] _arg021 = data.createFloatArray();
                            int _arg118 = data.readInt();
                            data.enforceNoDataAvail();
                            onAvailableSpeeds(_arg021, _arg118);
                            return true;
                        case 22:
                            int _arg022 = data.readInt();
                            Bundle _arg119 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                            int _arg26 = data.readInt();
                            data.enforceNoDataAvail();
                            onTvMessage(_arg022, _arg119, _arg26);
                            return true;
                        case 23:
                            Uri _arg023 = (Uri) data.readTypedObject(Uri.CREATOR);
                            int _arg120 = data.readInt();
                            data.enforceNoDataAvail();
                            onTuned(_arg023, _arg120);
                            return true;
                        case 24:
                            Uri _arg024 = (Uri) data.readTypedObject(Uri.CREATOR);
                            int _arg121 = data.readInt();
                            data.enforceNoDataAvail();
                            onRecordingStopped(_arg024, _arg121);
                            return true;
                        case 25:
                            int _arg025 = data.readInt();
                            int _arg122 = data.readInt();
                            data.enforceNoDataAvail();
                            onError(_arg025, _arg122);
                            return true;
                        case 26:
                            BroadcastInfoResponse _arg026 = (BroadcastInfoResponse) data.readTypedObject(BroadcastInfoResponse.CREATOR);
                            int _arg123 = data.readInt();
                            data.enforceNoDataAvail();
                            onBroadcastInfoResponse(_arg026, _arg123);
                            return true;
                        case 27:
                            AdResponse _arg027 = (AdResponse) data.readTypedObject(AdResponse.CREATOR);
                            int _arg124 = data.readInt();
                            data.enforceNoDataAvail();
                            onAdResponse(_arg027, _arg124);
                            return true;
                        case 28:
                            AdBuffer _arg028 = (AdBuffer) data.readTypedObject(AdBuffer.CREATOR);
                            int _arg125 = data.readInt();
                            data.enforceNoDataAvail();
                            onAdBufferConsumed(_arg028, _arg125);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* loaded from: classes2.dex */
        public static class Proxy implements ITvInputClient {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.media.tv.ITvInputClient
            public void onSessionCreated(String inputId, IBinder token, InputChannel channel, int seq) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(inputId);
                    _data.writeStrongBinder(token);
                    _data.writeTypedObject(channel, 0);
                    _data.writeInt(seq);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onSessionReleased(int seq) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(seq);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onSessionEvent(String name, Bundle args, int seq) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeTypedObject(args, 0);
                    _data.writeInt(seq);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onChannelRetuned(Uri channelUri, int seq) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(channelUri, 0);
                    _data.writeInt(seq);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onAudioPresentationsChanged(List<AudioPresentation> AudioPresentations, int seq) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedList(AudioPresentations, 0);
                    _data.writeInt(seq);
                    this.mRemote.transact(5, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onAudioPresentationSelected(int presentationId, int programId, int seq) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(presentationId);
                    _data.writeInt(programId);
                    _data.writeInt(seq);
                    this.mRemote.transact(6, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onTracksChanged(List<TvTrackInfo> tracks, int seq) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedList(tracks, 0);
                    _data.writeInt(seq);
                    this.mRemote.transact(7, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onTrackSelected(int type, String trackId, int seq) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeString(trackId);
                    _data.writeInt(seq);
                    this.mRemote.transact(8, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onVideoAvailable(int seq) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(seq);
                    this.mRemote.transact(9, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onVideoUnavailable(int reason, int seq) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(reason);
                    _data.writeInt(seq);
                    this.mRemote.transact(10, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onContentAllowed(int seq) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(seq);
                    this.mRemote.transact(11, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onContentBlocked(String rating, int seq) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(rating);
                    _data.writeInt(seq);
                    this.mRemote.transact(12, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onLayoutSurface(int left, int top, int right, int bottom, int seq) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(left);
                    _data.writeInt(top);
                    _data.writeInt(right);
                    _data.writeInt(bottom);
                    _data.writeInt(seq);
                    this.mRemote.transact(13, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onTimeShiftStatusChanged(int status, int seq) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(status);
                    _data.writeInt(seq);
                    this.mRemote.transact(14, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onTimeShiftStartPositionChanged(long timeMs, int seq) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(timeMs);
                    _data.writeInt(seq);
                    this.mRemote.transact(15, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onTimeShiftCurrentPositionChanged(long timeMs, int seq) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(timeMs);
                    _data.writeInt(seq);
                    this.mRemote.transact(16, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onAitInfoUpdated(AitInfo aitInfo, int seq) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(aitInfo, 0);
                    _data.writeInt(seq);
                    this.mRemote.transact(17, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onSignalStrength(int stength, int seq) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(stength);
                    _data.writeInt(seq);
                    this.mRemote.transact(18, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onCueingMessageAvailability(boolean available, int seq) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(available);
                    _data.writeInt(seq);
                    this.mRemote.transact(19, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onTimeShiftMode(int mode, int seq) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(mode);
                    _data.writeInt(seq);
                    this.mRemote.transact(20, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onAvailableSpeeds(float[] speeds, int seq) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeFloatArray(speeds);
                    _data.writeInt(seq);
                    this.mRemote.transact(21, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onTvMessage(int type, Bundle data, int seq) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeTypedObject(data, 0);
                    _data.writeInt(seq);
                    this.mRemote.transact(22, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onTuned(Uri channelUri, int seq) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(channelUri, 0);
                    _data.writeInt(seq);
                    this.mRemote.transact(23, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onRecordingStopped(Uri recordedProgramUri, int seq) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(recordedProgramUri, 0);
                    _data.writeInt(seq);
                    this.mRemote.transact(24, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onError(int error, int seq) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(error);
                    _data.writeInt(seq);
                    this.mRemote.transact(25, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onBroadcastInfoResponse(BroadcastInfoResponse response, int seq) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(response, 0);
                    _data.writeInt(seq);
                    this.mRemote.transact(26, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onAdResponse(AdResponse response, int seq) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(response, 0);
                    _data.writeInt(seq);
                    this.mRemote.transact(27, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onAdBufferConsumed(AdBuffer buffer, int seq) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(buffer, 0);
                    _data.writeInt(seq);
                    this.mRemote.transact(28, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 27;
        }
    }
}
