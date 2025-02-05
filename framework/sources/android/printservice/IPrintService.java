package android.printservice;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.print.PrintJobInfo;
import android.print.PrinterId;
import android.printservice.IPrintServiceClient;
import java.util.List;

/* loaded from: classes3.dex */
public interface IPrintService extends IInterface {
    void createPrinterDiscoverySession() throws RemoteException;

    void destroyPrinterDiscoverySession() throws RemoteException;

    void onPrintJobQueued(PrintJobInfo printJobInfo) throws RemoteException;

    void requestCancelPrintJob(PrintJobInfo printJobInfo) throws RemoteException;

    void requestCustomPrinterIcon(PrinterId printerId) throws RemoteException;

    void setClient(IPrintServiceClient iPrintServiceClient) throws RemoteException;

    void startPrinterDiscovery(List<PrinterId> list) throws RemoteException;

    void startPrinterStateTracking(PrinterId printerId) throws RemoteException;

    void stopPrinterDiscovery() throws RemoteException;

    void stopPrinterStateTracking(PrinterId printerId) throws RemoteException;

    void validatePrinters(List<PrinterId> list) throws RemoteException;

    public static class Default implements IPrintService {
        @Override // android.printservice.IPrintService
        public void setClient(IPrintServiceClient client) throws RemoteException {
        }

        @Override // android.printservice.IPrintService
        public void requestCancelPrintJob(PrintJobInfo printJobInfo) throws RemoteException {
        }

        @Override // android.printservice.IPrintService
        public void onPrintJobQueued(PrintJobInfo printJobInfo) throws RemoteException {
        }

        @Override // android.printservice.IPrintService
        public void createPrinterDiscoverySession() throws RemoteException {
        }

        @Override // android.printservice.IPrintService
        public void startPrinterDiscovery(List<PrinterId> priorityList) throws RemoteException {
        }

        @Override // android.printservice.IPrintService
        public void stopPrinterDiscovery() throws RemoteException {
        }

        @Override // android.printservice.IPrintService
        public void validatePrinters(List<PrinterId> printerIds) throws RemoteException {
        }

        @Override // android.printservice.IPrintService
        public void startPrinterStateTracking(PrinterId printerId) throws RemoteException {
        }

        @Override // android.printservice.IPrintService
        public void requestCustomPrinterIcon(PrinterId printerId) throws RemoteException {
        }

        @Override // android.printservice.IPrintService
        public void stopPrinterStateTracking(PrinterId printerId) throws RemoteException {
        }

        @Override // android.printservice.IPrintService
        public void destroyPrinterDiscoverySession() throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IPrintService {
        public static final String DESCRIPTOR = "android.printservice.IPrintService";
        static final int TRANSACTION_createPrinterDiscoverySession = 4;
        static final int TRANSACTION_destroyPrinterDiscoverySession = 11;
        static final int TRANSACTION_onPrintJobQueued = 3;
        static final int TRANSACTION_requestCancelPrintJob = 2;
        static final int TRANSACTION_requestCustomPrinterIcon = 9;
        static final int TRANSACTION_setClient = 1;
        static final int TRANSACTION_startPrinterDiscovery = 5;
        static final int TRANSACTION_startPrinterStateTracking = 8;
        static final int TRANSACTION_stopPrinterDiscovery = 6;
        static final int TRANSACTION_stopPrinterStateTracking = 10;
        static final int TRANSACTION_validatePrinters = 7;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IPrintService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IPrintService)) {
                return (IPrintService) iin;
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
                    return "setClient";
                case 2:
                    return "requestCancelPrintJob";
                case 3:
                    return "onPrintJobQueued";
                case 4:
                    return "createPrinterDiscoverySession";
                case 5:
                    return "startPrinterDiscovery";
                case 6:
                    return "stopPrinterDiscovery";
                case 7:
                    return "validatePrinters";
                case 8:
                    return "startPrinterStateTracking";
                case 9:
                    return "requestCustomPrinterIcon";
                case 10:
                    return "stopPrinterStateTracking";
                case 11:
                    return "destroyPrinterDiscoverySession";
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
                    IPrintServiceClient _arg0 = IPrintServiceClient.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    setClient(_arg0);
                    return true;
                case 2:
                    PrintJobInfo _arg02 = (PrintJobInfo) data.readTypedObject(PrintJobInfo.CREATOR);
                    data.enforceNoDataAvail();
                    requestCancelPrintJob(_arg02);
                    return true;
                case 3:
                    PrintJobInfo _arg03 = (PrintJobInfo) data.readTypedObject(PrintJobInfo.CREATOR);
                    data.enforceNoDataAvail();
                    onPrintJobQueued(_arg03);
                    return true;
                case 4:
                    createPrinterDiscoverySession();
                    return true;
                case 5:
                    List<PrinterId> _arg04 = data.createTypedArrayList(PrinterId.CREATOR);
                    data.enforceNoDataAvail();
                    startPrinterDiscovery(_arg04);
                    return true;
                case 6:
                    stopPrinterDiscovery();
                    return true;
                case 7:
                    List<PrinterId> _arg05 = data.createTypedArrayList(PrinterId.CREATOR);
                    data.enforceNoDataAvail();
                    validatePrinters(_arg05);
                    return true;
                case 8:
                    PrinterId _arg06 = (PrinterId) data.readTypedObject(PrinterId.CREATOR);
                    data.enforceNoDataAvail();
                    startPrinterStateTracking(_arg06);
                    return true;
                case 9:
                    PrinterId _arg07 = (PrinterId) data.readTypedObject(PrinterId.CREATOR);
                    data.enforceNoDataAvail();
                    requestCustomPrinterIcon(_arg07);
                    return true;
                case 10:
                    PrinterId _arg08 = (PrinterId) data.readTypedObject(PrinterId.CREATOR);
                    data.enforceNoDataAvail();
                    stopPrinterStateTracking(_arg08);
                    return true;
                case 11:
                    destroyPrinterDiscoverySession();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IPrintService {
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

            @Override // android.printservice.IPrintService
            public void setClient(IPrintServiceClient client) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(client);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.printservice.IPrintService
            public void requestCancelPrintJob(PrintJobInfo printJobInfo) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(printJobInfo, 0);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.printservice.IPrintService
            public void onPrintJobQueued(PrintJobInfo printJobInfo) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(printJobInfo, 0);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.printservice.IPrintService
            public void createPrinterDiscoverySession() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.printservice.IPrintService
            public void startPrinterDiscovery(List<PrinterId> priorityList) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedList(priorityList, 0);
                    this.mRemote.transact(5, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.printservice.IPrintService
            public void stopPrinterDiscovery() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(6, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.printservice.IPrintService
            public void validatePrinters(List<PrinterId> printerIds) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedList(printerIds, 0);
                    this.mRemote.transact(7, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.printservice.IPrintService
            public void startPrinterStateTracking(PrinterId printerId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(printerId, 0);
                    this.mRemote.transact(8, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.printservice.IPrintService
            public void requestCustomPrinterIcon(PrinterId printerId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(printerId, 0);
                    this.mRemote.transact(9, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.printservice.IPrintService
            public void stopPrinterStateTracking(PrinterId printerId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(printerId, 0);
                    this.mRemote.transact(10, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.printservice.IPrintService
            public void destroyPrinterDiscoverySession() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(11, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 10;
        }
    }
}
