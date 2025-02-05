package android.hardware.soundtrigger3;

import android.media.soundtrigger.PhraseRecognitionEvent;
import android.media.soundtrigger.RecognitionEvent;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface ISoundTriggerHwCallback extends IInterface {
    public static final String DESCRIPTOR = "android$hardware$soundtrigger3$ISoundTriggerHwCallback".replace('$', '.');
    public static final String HASH = "6b24e60ad261e3ff56106efd86ce6aa7ef5621b0";
    public static final int VERSION = 2;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void modelUnloaded(int i) throws RemoteException;

    void phraseRecognitionCallback(int i, PhraseRecognitionEvent phraseRecognitionEvent) throws RemoteException;

    void recognitionCallback(int i, RecognitionEvent recognitionEvent) throws RemoteException;

    public static class Default implements ISoundTriggerHwCallback {
        @Override // android.hardware.soundtrigger3.ISoundTriggerHwCallback
        public void modelUnloaded(int model) throws RemoteException {
        }

        @Override // android.hardware.soundtrigger3.ISoundTriggerHwCallback
        public void phraseRecognitionCallback(int model, PhraseRecognitionEvent event) throws RemoteException {
        }

        @Override // android.hardware.soundtrigger3.ISoundTriggerHwCallback
        public void recognitionCallback(int model, RecognitionEvent event) throws RemoteException {
        }

        @Override // android.hardware.soundtrigger3.ISoundTriggerHwCallback
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.soundtrigger3.ISoundTriggerHwCallback
        public String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ISoundTriggerHwCallback {
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_modelUnloaded = 1;
        static final int TRANSACTION_phraseRecognitionCallback = 2;
        static final int TRANSACTION_recognitionCallback = 3;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static ISoundTriggerHwCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof ISoundTriggerHwCallback)) {
                return (ISoundTriggerHwCallback) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            String descriptor = DESCRIPTOR;
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(descriptor);
            }
            if (code == 1598968902) {
                reply.writeString(descriptor);
                return true;
            }
            if (code == 16777215) {
                reply.writeNoException();
                reply.writeInt(getInterfaceVersion());
                return true;
            }
            if (code == 16777214) {
                reply.writeNoException();
                reply.writeString(getInterfaceHash());
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    data.enforceNoDataAvail();
                    modelUnloaded(_arg0);
                    reply.writeNoException();
                    return true;
                case 2:
                    int _arg02 = data.readInt();
                    PhraseRecognitionEvent _arg1 = (PhraseRecognitionEvent) data.readTypedObject(PhraseRecognitionEvent.CREATOR);
                    data.enforceNoDataAvail();
                    phraseRecognitionCallback(_arg02, _arg1);
                    reply.writeNoException();
                    return true;
                case 3:
                    int _arg03 = data.readInt();
                    RecognitionEvent _arg12 = (RecognitionEvent) data.readTypedObject(RecognitionEvent.CREATOR);
                    data.enforceNoDataAvail();
                    recognitionCallback(_arg03, _arg12);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISoundTriggerHwCallback {
            private IBinder mRemote;
            private int mCachedVersion = -1;
            private String mCachedHash = "-1";

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return DESCRIPTOR;
            }

            @Override // android.hardware.soundtrigger3.ISoundTriggerHwCallback
            public void modelUnloaded(int model) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(model);
                    boolean _status = this.mRemote.transact(1, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method modelUnloaded is unimplemented.");
                    }
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.soundtrigger3.ISoundTriggerHwCallback
            public void phraseRecognitionCallback(int model, PhraseRecognitionEvent event) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(model);
                    _data.writeTypedObject(event, 0);
                    boolean _status = this.mRemote.transact(2, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method phraseRecognitionCallback is unimplemented.");
                    }
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.soundtrigger3.ISoundTriggerHwCallback
            public void recognitionCallback(int model, RecognitionEvent event) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(model);
                    _data.writeTypedObject(event, 0);
                    boolean _status = this.mRemote.transact(3, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method recognitionCallback is unimplemented.");
                    }
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.soundtrigger3.ISoundTriggerHwCallback
            public int getInterfaceVersion() throws RemoteException {
                if (this.mCachedVersion == -1) {
                    Parcel data = Parcel.obtain(asBinder());
                    Parcel reply = Parcel.obtain();
                    try {
                        data.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777215, data, reply, 0);
                        reply.readException();
                        this.mCachedVersion = reply.readInt();
                    } finally {
                        reply.recycle();
                        data.recycle();
                    }
                }
                return this.mCachedVersion;
            }

            @Override // android.hardware.soundtrigger3.ISoundTriggerHwCallback
            public synchronized String getInterfaceHash() throws RemoteException {
                if ("-1".equals(this.mCachedHash)) {
                    Parcel data = Parcel.obtain(asBinder());
                    Parcel reply = Parcel.obtain();
                    try {
                        data.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777214, data, reply, 0);
                        reply.readException();
                        this.mCachedHash = reply.readString();
                        reply.recycle();
                        data.recycle();
                    } catch (Throwable th) {
                        reply.recycle();
                        data.recycle();
                        throw th;
                    }
                }
                return this.mCachedHash;
            }
        }
    }
}
