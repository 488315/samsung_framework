package android.service.autofill.augmented;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ICancellationSignal;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.service.autofill.Dataset;
import java.util.List;

/* loaded from: classes3.dex */
public interface IFillCallback extends IInterface {
    public static final String DESCRIPTOR = "android.service.autofill.augmented.IFillCallback";

    void cancel() throws RemoteException;

    boolean isCompleted() throws RemoteException;

    void onCancellable(ICancellationSignal iCancellationSignal) throws RemoteException;

    void onSuccess(List<Dataset> list, Bundle bundle, boolean z) throws RemoteException;

    public static class Default implements IFillCallback {
        @Override // android.service.autofill.augmented.IFillCallback
        public void onCancellable(ICancellationSignal cancellation) throws RemoteException {
        }

        @Override // android.service.autofill.augmented.IFillCallback
        public void onSuccess(List<Dataset> inlineSuggestionsData, Bundle clientState, boolean showingFillWindow) throws RemoteException {
        }

        @Override // android.service.autofill.augmented.IFillCallback
        public boolean isCompleted() throws RemoteException {
            return false;
        }

        @Override // android.service.autofill.augmented.IFillCallback
        public void cancel() throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IFillCallback {
        static final int TRANSACTION_cancel = 4;
        static final int TRANSACTION_isCompleted = 3;
        static final int TRANSACTION_onCancellable = 1;
        static final int TRANSACTION_onSuccess = 2;

        public Stub() {
            attachInterface(this, IFillCallback.DESCRIPTOR);
        }

        public static IFillCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IFillCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IFillCallback)) {
                return (IFillCallback) iin;
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
                    return "onCancellable";
                case 2:
                    return "onSuccess";
                case 3:
                    return "isCompleted";
                case 4:
                    return "cancel";
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
                data.enforceInterface(IFillCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IFillCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    ICancellationSignal _arg0 = ICancellationSignal.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    onCancellable(_arg0);
                    reply.writeNoException();
                    return true;
                case 2:
                    List<Dataset> _arg02 = data.createTypedArrayList(Dataset.CREATOR);
                    Bundle _arg1 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    boolean _arg2 = data.readBoolean();
                    data.enforceNoDataAvail();
                    onSuccess(_arg02, _arg1, _arg2);
                    reply.writeNoException();
                    return true;
                case 3:
                    boolean _result = isCompleted();
                    reply.writeNoException();
                    reply.writeBoolean(_result);
                    return true;
                case 4:
                    cancel();
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IFillCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IFillCallback.DESCRIPTOR;
            }

            @Override // android.service.autofill.augmented.IFillCallback
            public void onCancellable(ICancellationSignal cancellation) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IFillCallback.DESCRIPTOR);
                    _data.writeStrongInterface(cancellation);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.service.autofill.augmented.IFillCallback
            public void onSuccess(List<Dataset> inlineSuggestionsData, Bundle clientState, boolean showingFillWindow) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IFillCallback.DESCRIPTOR);
                    _data.writeTypedList(inlineSuggestionsData, 0);
                    _data.writeTypedObject(clientState, 0);
                    _data.writeBoolean(showingFillWindow);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.service.autofill.augmented.IFillCallback
            public boolean isCompleted() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IFillCallback.DESCRIPTOR);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.service.autofill.augmented.IFillCallback
            public void cancel() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IFillCallback.DESCRIPTOR);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }
    }
}
