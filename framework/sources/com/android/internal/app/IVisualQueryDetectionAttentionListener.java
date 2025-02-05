package com.android.internal.app;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.service.voice.VisualQueryAttentionResult;

/* loaded from: classes5.dex */
public interface IVisualQueryDetectionAttentionListener extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.app.IVisualQueryDetectionAttentionListener";

    void onAttentionGained(VisualQueryAttentionResult visualQueryAttentionResult) throws RemoteException;

    void onAttentionLost(int i) throws RemoteException;

    public static class Default implements IVisualQueryDetectionAttentionListener {
        @Override // com.android.internal.app.IVisualQueryDetectionAttentionListener
        public void onAttentionGained(VisualQueryAttentionResult attentionResult) throws RemoteException {
        }

        @Override // com.android.internal.app.IVisualQueryDetectionAttentionListener
        public void onAttentionLost(int interactionIntention) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IVisualQueryDetectionAttentionListener {
        static final int TRANSACTION_onAttentionGained = 1;
        static final int TRANSACTION_onAttentionLost = 2;

        public Stub() {
            attachInterface(this, IVisualQueryDetectionAttentionListener.DESCRIPTOR);
        }

        public static IVisualQueryDetectionAttentionListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IVisualQueryDetectionAttentionListener.DESCRIPTOR);
            if (iin != null && (iin instanceof IVisualQueryDetectionAttentionListener)) {
                return (IVisualQueryDetectionAttentionListener) iin;
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
                    return "onAttentionGained";
                case 2:
                    return "onAttentionLost";
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
                data.enforceInterface(IVisualQueryDetectionAttentionListener.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IVisualQueryDetectionAttentionListener.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    VisualQueryAttentionResult _arg0 = (VisualQueryAttentionResult) data.readTypedObject(VisualQueryAttentionResult.CREATOR);
                    data.enforceNoDataAvail();
                    onAttentionGained(_arg0);
                    return true;
                case 2:
                    int _arg02 = data.readInt();
                    data.enforceNoDataAvail();
                    onAttentionLost(_arg02);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IVisualQueryDetectionAttentionListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IVisualQueryDetectionAttentionListener.DESCRIPTOR;
            }

            @Override // com.android.internal.app.IVisualQueryDetectionAttentionListener
            public void onAttentionGained(VisualQueryAttentionResult attentionResult) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IVisualQueryDetectionAttentionListener.DESCRIPTOR);
                    _data.writeTypedObject(attentionResult, 0);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IVisualQueryDetectionAttentionListener
            public void onAttentionLost(int interactionIntention) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IVisualQueryDetectionAttentionListener.DESCRIPTOR);
                    _data.writeInt(interactionIntention);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }
    }
}
