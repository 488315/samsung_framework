package android.media;

import android.app.INotificationPlayerOnCompletionListener;
import android.media.VolumeShaper;
import android.media.tv.interactive.TvInteractiveAppService;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.os.UserHandle;

/* loaded from: classes2.dex */
public interface IRingtonePlayer extends IInterface {
    void fadeinRingtone(IBinder iBinder) throws RemoteException;

    void fadeoutRingtone(IBinder iBinder, int i, float f) throws RemoteException;

    String getTitle(Uri uri) throws RemoteException;

    boolean isPlaying(IBinder iBinder) throws RemoteException;

    ParcelFileDescriptor openRingtone(Uri uri) throws RemoteException;

    void play(IBinder iBinder, Uri uri, AudioAttributes audioAttributes, float f, boolean z) throws RemoteException;

    void playAsync(Uri uri, UserHandle userHandle, boolean z, AudioAttributes audioAttributes, float f) throws RemoteException;

    void playWithVolumeShaping(IBinder iBinder, Uri uri, AudioAttributes audioAttributes, float f, boolean z, VolumeShaper.Configuration configuration) throws RemoteException;

    IBinder setOnCompletionListener(INotificationPlayerOnCompletionListener iNotificationPlayerOnCompletionListener) throws RemoteException;

    void setPlaybackProperties(IBinder iBinder, float f, boolean z, boolean z2) throws RemoteException;

    void stop(IBinder iBinder) throws RemoteException;

    void stopAsync() throws RemoteException;

    public static class Default implements IRingtonePlayer {
        @Override // android.media.IRingtonePlayer
        public void play(IBinder token, Uri uri, AudioAttributes aa, float volume, boolean looping) throws RemoteException {
        }

        @Override // android.media.IRingtonePlayer
        public void playWithVolumeShaping(IBinder token, Uri uri, AudioAttributes aa, float volume, boolean looping, VolumeShaper.Configuration volumeShaperConfig) throws RemoteException {
        }

        @Override // android.media.IRingtonePlayer
        public void stop(IBinder token) throws RemoteException {
        }

        @Override // android.media.IRingtonePlayer
        public boolean isPlaying(IBinder token) throws RemoteException {
            return false;
        }

        @Override // android.media.IRingtonePlayer
        public void setPlaybackProperties(IBinder token, float volume, boolean looping, boolean hapticGeneratorEnabled) throws RemoteException {
        }

        @Override // android.media.IRingtonePlayer
        public void playAsync(Uri uri, UserHandle user, boolean looping, AudioAttributes aa, float volume) throws RemoteException {
        }

        @Override // android.media.IRingtonePlayer
        public void stopAsync() throws RemoteException {
        }

        @Override // android.media.IRingtonePlayer
        public String getTitle(Uri uri) throws RemoteException {
            return null;
        }

        @Override // android.media.IRingtonePlayer
        public ParcelFileDescriptor openRingtone(Uri uri) throws RemoteException {
            return null;
        }

        @Override // android.media.IRingtonePlayer
        public void fadeinRingtone(IBinder token) throws RemoteException {
        }

        @Override // android.media.IRingtonePlayer
        public void fadeoutRingtone(IBinder token, int delay, float minVolume) throws RemoteException {
        }

        @Override // android.media.IRingtonePlayer
        public IBinder setOnCompletionListener(INotificationPlayerOnCompletionListener l) throws RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IRingtonePlayer {
        public static final String DESCRIPTOR = "android.media.IRingtonePlayer";
        static final int TRANSACTION_fadeinRingtone = 10;
        static final int TRANSACTION_fadeoutRingtone = 11;
        static final int TRANSACTION_getTitle = 8;
        static final int TRANSACTION_isPlaying = 4;
        static final int TRANSACTION_openRingtone = 9;
        static final int TRANSACTION_play = 1;
        static final int TRANSACTION_playAsync = 6;
        static final int TRANSACTION_playWithVolumeShaping = 2;
        static final int TRANSACTION_setOnCompletionListener = 12;
        static final int TRANSACTION_setPlaybackProperties = 5;
        static final int TRANSACTION_stop = 3;
        static final int TRANSACTION_stopAsync = 7;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IRingtonePlayer asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IRingtonePlayer)) {
                return (IRingtonePlayer) iin;
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
                    return TvInteractiveAppService.TIME_SHIFT_COMMAND_TYPE_PLAY;
                case 2:
                    return "playWithVolumeShaping";
                case 3:
                    return "stop";
                case 4:
                    return "isPlaying";
                case 5:
                    return "setPlaybackProperties";
                case 6:
                    return "playAsync";
                case 7:
                    return "stopAsync";
                case 8:
                    return "getTitle";
                case 9:
                    return "openRingtone";
                case 10:
                    return "fadeinRingtone";
                case 11:
                    return "fadeoutRingtone";
                case 12:
                    return "setOnCompletionListener";
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
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    IBinder _arg0 = data.readStrongBinder();
                    Uri _arg1 = (Uri) data.readTypedObject(Uri.CREATOR);
                    AudioAttributes _arg2 = (AudioAttributes) data.readTypedObject(AudioAttributes.CREATOR);
                    float _arg3 = data.readFloat();
                    boolean _arg4 = data.readBoolean();
                    data.enforceNoDataAvail();
                    play(_arg0, _arg1, _arg2, _arg3, _arg4);
                    return true;
                case 2:
                    IBinder _arg02 = data.readStrongBinder();
                    Uri _arg12 = (Uri) data.readTypedObject(Uri.CREATOR);
                    AudioAttributes _arg22 = (AudioAttributes) data.readTypedObject(AudioAttributes.CREATOR);
                    float _arg32 = data.readFloat();
                    boolean _arg42 = data.readBoolean();
                    VolumeShaper.Configuration _arg5 = (VolumeShaper.Configuration) data.readTypedObject(VolumeShaper.Configuration.CREATOR);
                    data.enforceNoDataAvail();
                    playWithVolumeShaping(_arg02, _arg12, _arg22, _arg32, _arg42, _arg5);
                    return true;
                case 3:
                    IBinder _arg03 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    stop(_arg03);
                    return true;
                case 4:
                    IBinder _arg04 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    boolean _result = isPlaying(_arg04);
                    reply.writeNoException();
                    reply.writeBoolean(_result);
                    return true;
                case 5:
                    IBinder _arg05 = data.readStrongBinder();
                    float _arg13 = data.readFloat();
                    boolean _arg23 = data.readBoolean();
                    boolean _arg33 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setPlaybackProperties(_arg05, _arg13, _arg23, _arg33);
                    return true;
                case 6:
                    Uri _arg06 = (Uri) data.readTypedObject(Uri.CREATOR);
                    UserHandle _arg14 = (UserHandle) data.readTypedObject(UserHandle.CREATOR);
                    boolean _arg24 = data.readBoolean();
                    AudioAttributes _arg34 = (AudioAttributes) data.readTypedObject(AudioAttributes.CREATOR);
                    float _arg43 = data.readFloat();
                    data.enforceNoDataAvail();
                    playAsync(_arg06, _arg14, _arg24, _arg34, _arg43);
                    return true;
                case 7:
                    stopAsync();
                    return true;
                case 8:
                    Uri _arg07 = (Uri) data.readTypedObject(Uri.CREATOR);
                    data.enforceNoDataAvail();
                    String _result2 = getTitle(_arg07);
                    reply.writeNoException();
                    reply.writeString(_result2);
                    return true;
                case 9:
                    Uri _arg08 = (Uri) data.readTypedObject(Uri.CREATOR);
                    data.enforceNoDataAvail();
                    ParcelFileDescriptor _result3 = openRingtone(_arg08);
                    reply.writeNoException();
                    reply.writeTypedObject(_result3, 1);
                    return true;
                case 10:
                    IBinder _arg09 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    fadeinRingtone(_arg09);
                    return true;
                case 11:
                    IBinder _arg010 = data.readStrongBinder();
                    int _arg15 = data.readInt();
                    float _arg25 = data.readFloat();
                    data.enforceNoDataAvail();
                    fadeoutRingtone(_arg010, _arg15, _arg25);
                    return true;
                case 12:
                    INotificationPlayerOnCompletionListener _arg011 = INotificationPlayerOnCompletionListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    IBinder _result4 = setOnCompletionListener(_arg011);
                    reply.writeNoException();
                    reply.writeStrongBinder(_result4);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IRingtonePlayer {
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

            @Override // android.media.IRingtonePlayer
            public void play(IBinder token, Uri uri, AudioAttributes aa, float volume, boolean looping) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeTypedObject(uri, 0);
                    _data.writeTypedObject(aa, 0);
                    _data.writeFloat(volume);
                    _data.writeBoolean(looping);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.IRingtonePlayer
            public void playWithVolumeShaping(IBinder token, Uri uri, AudioAttributes aa, float volume, boolean looping, VolumeShaper.Configuration volumeShaperConfig) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeTypedObject(uri, 0);
                    _data.writeTypedObject(aa, 0);
                    _data.writeFloat(volume);
                    _data.writeBoolean(looping);
                    _data.writeTypedObject(volumeShaperConfig, 0);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.IRingtonePlayer
            public void stop(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.IRingtonePlayer
            public boolean isPlaying(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IRingtonePlayer
            public void setPlaybackProperties(IBinder token, float volume, boolean looping, boolean hapticGeneratorEnabled) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeFloat(volume);
                    _data.writeBoolean(looping);
                    _data.writeBoolean(hapticGeneratorEnabled);
                    this.mRemote.transact(5, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.IRingtonePlayer
            public void playAsync(Uri uri, UserHandle user, boolean looping, AudioAttributes aa, float volume) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(uri, 0);
                    _data.writeTypedObject(user, 0);
                    _data.writeBoolean(looping);
                    _data.writeTypedObject(aa, 0);
                    _data.writeFloat(volume);
                    this.mRemote.transact(6, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.IRingtonePlayer
            public void stopAsync() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(7, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.IRingtonePlayer
            public String getTitle(Uri uri) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(uri, 0);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IRingtonePlayer
            public ParcelFileDescriptor openRingtone(Uri uri) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(uri, 0);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                    ParcelFileDescriptor _result = (ParcelFileDescriptor) _reply.readTypedObject(ParcelFileDescriptor.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IRingtonePlayer
            public void fadeinRingtone(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    this.mRemote.transact(10, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.IRingtonePlayer
            public void fadeoutRingtone(IBinder token, int delay, float minVolume) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeInt(delay);
                    _data.writeFloat(minVolume);
                    this.mRemote.transact(11, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.IRingtonePlayer
            public IBinder setOnCompletionListener(INotificationPlayerOnCompletionListener l) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(l);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                    IBinder _result = _reply.readStrongBinder();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 11;
        }
    }
}
