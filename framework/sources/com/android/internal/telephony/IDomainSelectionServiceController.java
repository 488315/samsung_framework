package com.android.internal.telephony;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.BarringInfo;
import android.telephony.DomainSelectionService;
import android.telephony.ServiceState;
import com.android.internal.telephony.ITransportSelectorCallback;

/* loaded from: classes5.dex */
public interface IDomainSelectionServiceController extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.telephony.IDomainSelectionServiceController";

    void selectDomain(DomainSelectionService.SelectionAttributes selectionAttributes, ITransportSelectorCallback iTransportSelectorCallback) throws RemoteException;

    void updateBarringInfo(int i, int i2, BarringInfo barringInfo) throws RemoteException;

    void updateServiceState(int i, int i2, ServiceState serviceState) throws RemoteException;

    public static class Default implements IDomainSelectionServiceController {
        @Override // com.android.internal.telephony.IDomainSelectionServiceController
        public void selectDomain(DomainSelectionService.SelectionAttributes attr, ITransportSelectorCallback callback) throws RemoteException {
        }

        @Override // com.android.internal.telephony.IDomainSelectionServiceController
        public void updateServiceState(int slotId, int subId, ServiceState serviceState) throws RemoteException {
        }

        @Override // com.android.internal.telephony.IDomainSelectionServiceController
        public void updateBarringInfo(int slotId, int subId, BarringInfo info) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IDomainSelectionServiceController {
        static final int TRANSACTION_selectDomain = 1;
        static final int TRANSACTION_updateBarringInfo = 3;
        static final int TRANSACTION_updateServiceState = 2;

        public Stub() {
            attachInterface(this, IDomainSelectionServiceController.DESCRIPTOR);
        }

        public static IDomainSelectionServiceController asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IDomainSelectionServiceController.DESCRIPTOR);
            if (iin != null && (iin instanceof IDomainSelectionServiceController)) {
                return (IDomainSelectionServiceController) iin;
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
                    return "selectDomain";
                case 2:
                    return "updateServiceState";
                case 3:
                    return "updateBarringInfo";
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
                data.enforceInterface(IDomainSelectionServiceController.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IDomainSelectionServiceController.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    DomainSelectionService.SelectionAttributes _arg0 = (DomainSelectionService.SelectionAttributes) data.readTypedObject(DomainSelectionService.SelectionAttributes.CREATOR);
                    ITransportSelectorCallback _arg1 = ITransportSelectorCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    selectDomain(_arg0, _arg1);
                    return true;
                case 2:
                    int _arg02 = data.readInt();
                    int _arg12 = data.readInt();
                    ServiceState _arg2 = (ServiceState) data.readTypedObject(ServiceState.CREATOR);
                    data.enforceNoDataAvail();
                    updateServiceState(_arg02, _arg12, _arg2);
                    return true;
                case 3:
                    int _arg03 = data.readInt();
                    int _arg13 = data.readInt();
                    BarringInfo _arg22 = (BarringInfo) data.readTypedObject(BarringInfo.CREATOR);
                    data.enforceNoDataAvail();
                    updateBarringInfo(_arg03, _arg13, _arg22);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IDomainSelectionServiceController {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDomainSelectionServiceController.DESCRIPTOR;
            }

            @Override // com.android.internal.telephony.IDomainSelectionServiceController
            public void selectDomain(DomainSelectionService.SelectionAttributes attr, ITransportSelectorCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IDomainSelectionServiceController.DESCRIPTOR);
                    _data.writeTypedObject(attr, 0);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.IDomainSelectionServiceController
            public void updateServiceState(int slotId, int subId, ServiceState serviceState) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IDomainSelectionServiceController.DESCRIPTOR);
                    _data.writeInt(slotId);
                    _data.writeInt(subId);
                    _data.writeTypedObject(serviceState, 0);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.IDomainSelectionServiceController
            public void updateBarringInfo(int slotId, int subId, BarringInfo info) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IDomainSelectionServiceController.DESCRIPTOR);
                    _data.writeInt(slotId);
                    _data.writeInt(subId);
                    _data.writeTypedObject(info, 0);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }
    }
}
