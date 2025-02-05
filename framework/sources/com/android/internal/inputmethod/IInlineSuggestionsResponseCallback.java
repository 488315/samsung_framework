package com.android.internal.inputmethod;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.autofill.AutofillId;
import android.view.inputmethod.InlineSuggestionsResponse;

/* loaded from: classes5.dex */
public interface IInlineSuggestionsResponseCallback extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.inputmethod.IInlineSuggestionsResponseCallback";

    void onInlineSuggestionsResponse(AutofillId autofillId, InlineSuggestionsResponse inlineSuggestionsResponse) throws RemoteException;

    public static class Default implements IInlineSuggestionsResponseCallback {
        @Override // com.android.internal.inputmethod.IInlineSuggestionsResponseCallback
        public void onInlineSuggestionsResponse(AutofillId fieldId, InlineSuggestionsResponse response) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IInlineSuggestionsResponseCallback {
        static final int TRANSACTION_onInlineSuggestionsResponse = 1;

        public Stub() {
            attachInterface(this, IInlineSuggestionsResponseCallback.DESCRIPTOR);
        }

        public static IInlineSuggestionsResponseCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IInlineSuggestionsResponseCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IInlineSuggestionsResponseCallback)) {
                return (IInlineSuggestionsResponseCallback) iin;
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
                    return "onInlineSuggestionsResponse";
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
                data.enforceInterface(IInlineSuggestionsResponseCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IInlineSuggestionsResponseCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    AutofillId _arg0 = (AutofillId) data.readTypedObject(AutofillId.CREATOR);
                    InlineSuggestionsResponse _arg1 = (InlineSuggestionsResponse) data.readTypedObject(InlineSuggestionsResponse.CREATOR);
                    data.enforceNoDataAvail();
                    onInlineSuggestionsResponse(_arg0, _arg1);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IInlineSuggestionsResponseCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IInlineSuggestionsResponseCallback.DESCRIPTOR;
            }

            @Override // com.android.internal.inputmethod.IInlineSuggestionsResponseCallback
            public void onInlineSuggestionsResponse(AutofillId fieldId, InlineSuggestionsResponse response) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IInlineSuggestionsResponseCallback.DESCRIPTOR);
                    _data.writeTypedObject(fieldId, 0);
                    _data.writeTypedObject(response, 0);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }
    }
}
