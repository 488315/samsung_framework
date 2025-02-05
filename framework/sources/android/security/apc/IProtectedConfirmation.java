package android.security.apc;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.security.apc.IConfirmationCallback;

/* loaded from: classes3.dex */
public interface IProtectedConfirmation extends IInterface {
    public static final String DESCRIPTOR = "android.security.apc.IProtectedConfirmation";
    public static final int FLAG_UI_OPTION_INVERTED = 1;
    public static final int FLAG_UI_OPTION_MAGNIFIED = 2;

    void cancelPrompt(IConfirmationCallback iConfirmationCallback) throws RemoteException;

    boolean isSupported() throws RemoteException;

    void presentPrompt(IConfirmationCallback iConfirmationCallback, String str, byte[] bArr, String str2, int i) throws RemoteException;

    public static class Default implements IProtectedConfirmation {
        @Override // android.security.apc.IProtectedConfirmation
        public void presentPrompt(IConfirmationCallback listener, String promptText, byte[] extraData, String locale, int uiOptionFlags) throws RemoteException {
        }

        @Override // android.security.apc.IProtectedConfirmation
        public void cancelPrompt(IConfirmationCallback listener) throws RemoteException {
        }

        @Override // android.security.apc.IProtectedConfirmation
        public boolean isSupported() throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IProtectedConfirmation {
        static final int TRANSACTION_cancelPrompt = 2;
        static final int TRANSACTION_isSupported = 3;
        static final int TRANSACTION_presentPrompt = 1;

        public Stub() {
            attachInterface(this, IProtectedConfirmation.DESCRIPTOR);
        }

        public static IProtectedConfirmation asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IProtectedConfirmation.DESCRIPTOR);
            if (iin != null && (iin instanceof IProtectedConfirmation)) {
                return (IProtectedConfirmation) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IProtectedConfirmation.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IProtectedConfirmation.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    IConfirmationCallback _arg0 = IConfirmationCallback.Stub.asInterface(data.readStrongBinder());
                    String _arg1 = data.readString();
                    byte[] _arg2 = data.createByteArray();
                    String _arg3 = data.readString();
                    int _arg4 = data.readInt();
                    data.enforceNoDataAvail();
                    presentPrompt(_arg0, _arg1, _arg2, _arg3, _arg4);
                    reply.writeNoException();
                    return true;
                case 2:
                    IConfirmationCallback _arg02 = IConfirmationCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    cancelPrompt(_arg02);
                    reply.writeNoException();
                    return true;
                case 3:
                    boolean _result = isSupported();
                    reply.writeNoException();
                    reply.writeBoolean(_result);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IProtectedConfirmation {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IProtectedConfirmation.DESCRIPTOR;
            }

            @Override // android.security.apc.IProtectedConfirmation
            public void presentPrompt(IConfirmationCallback listener, String promptText, byte[] extraData, String locale, int uiOptionFlags) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IProtectedConfirmation.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    _data.writeString(promptText);
                    _data.writeByteArray(extraData);
                    _data.writeString(locale);
                    _data.writeInt(uiOptionFlags);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.security.apc.IProtectedConfirmation
            public void cancelPrompt(IConfirmationCallback listener) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IProtectedConfirmation.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.security.apc.IProtectedConfirmation
            public boolean isSupported() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IProtectedConfirmation.DESCRIPTOR);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }
    }
}
