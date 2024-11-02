package com.android.internal.statusbar;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes5.dex */
public interface IAppClipsService extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.statusbar.IAppClipsService";

    boolean canLaunchCaptureContentActivityForNote(int i) throws RemoteException;

    /* loaded from: classes5.dex */
    public static class Default implements IAppClipsService {
        @Override // com.android.internal.statusbar.IAppClipsService
        public boolean canLaunchCaptureContentActivityForNote(int taskId) throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes5.dex */
    public static abstract class Stub extends Binder implements IAppClipsService {
        static final int TRANSACTION_canLaunchCaptureContentActivityForNote = 1;

        public Stub() {
            attachInterface(this, IAppClipsService.DESCRIPTOR);
        }

        public static IAppClipsService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IAppClipsService.DESCRIPTOR);
            if (iin != null && (iin instanceof IAppClipsService)) {
                return (IAppClipsService) iin;
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
                    return "canLaunchCaptureContentActivityForNote";
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
                data.enforceInterface(IAppClipsService.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(IAppClipsService.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            int _arg0 = data.readInt();
                            data.enforceNoDataAvail();
                            boolean _result = canLaunchCaptureContentActivityForNote(_arg0);
                            reply.writeNoException();
                            reply.writeBoolean(_result);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* loaded from: classes5.dex */
        public static class Proxy implements IAppClipsService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IAppClipsService.DESCRIPTOR;
            }

            @Override // com.android.internal.statusbar.IAppClipsService
            public boolean canLaunchCaptureContentActivityForNote(int taskId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IAppClipsService.DESCRIPTOR);
                    _data.writeInt(taskId);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
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
