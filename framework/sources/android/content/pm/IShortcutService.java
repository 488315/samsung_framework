package android.content.pm;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.text.TextUtils;
import com.android.internal.infra.AndroidFuture;
import com.android.internal.os.IParcelFileDescriptorFactory;
import java.util.List;

/* loaded from: classes.dex */
public interface IShortcutService extends IInterface {
    boolean addDynamicShortcuts(String str, ParceledListSlice parceledListSlice, int i) throws RemoteException;

    void applyRestore(byte[] bArr, int i) throws RemoteException;

    int applyRestoreSmartSwitch(ParcelFileDescriptor parcelFileDescriptor, int i) throws RemoteException;

    void createShortcutResultIntent(String str, ShortcutInfo shortcutInfo, int i, AndroidFuture<Intent> androidFuture) throws RemoteException;

    void disableShortcuts(String str, List<String> list, CharSequence charSequence, int i, int i2) throws RemoteException;

    void enableShortcuts(String str, List<String> list, int i) throws RemoteException;

    byte[] getBackupPayload(int i) throws RemoteException;

    ParcelFileDescriptor getBackupShortcut(int i) throws RemoteException;

    String[] getBitmapPathList(int i) throws RemoteException;

    int getIconMaxDimensions(String str, int i) throws RemoteException;

    int getMaxShortcutCountPerActivity(String str, int i) throws RemoteException;

    long getRateLimitResetTime(String str, int i) throws RemoteException;

    int getRemainingCallCount(String str, int i) throws RemoteException;

    ParceledListSlice getShareTargets(String str, IntentFilter intentFilter, int i) throws RemoteException;

    IParcelFileDescriptorFactory getShortcutBitmapsFileDescriptor() throws RemoteException;

    ParceledListSlice getShortcuts(String str, int i, int i2) throws RemoteException;

    void handleLocaleChanged(boolean z) throws RemoteException;

    boolean hasShareTargets(String str, String str2, int i) throws RemoteException;

    boolean isRequestPinItemSupported(int i, int i2) throws RemoteException;

    void onApplicationActive(String str, int i) throws RemoteException;

    void pushDynamicShortcut(String str, ShortcutInfo shortcutInfo, int i) throws RemoteException;

    void removeAllDynamicShortcuts(String str, int i) throws RemoteException;

    void removeDynamicShortcuts(String str, List<String> list, int i) throws RemoteException;

    void removeLongLivedShortcuts(String str, List<String> list, int i) throws RemoteException;

    void reportShortcutUsed(String str, String str2, int i) throws RemoteException;

    void requestPinShortcut(String str, ShortcutInfo shortcutInfo, IntentSender intentSender, int i, AndroidFuture<String> androidFuture) throws RemoteException;

    void requestPinShortcutAsDisplay(String str, ShortcutInfo shortcutInfo, IntentSender intentSender, int i, int i2, AndroidFuture<String> androidFuture) throws RemoteException;

    void resetThrottling() throws RemoteException;

    void restoreBitmapsFromBackupService(ParcelFileDescriptor parcelFileDescriptor, String str, String str2) throws RemoteException;

    boolean setDynamicShortcuts(String str, ParceledListSlice parceledListSlice, int i) throws RemoteException;

    boolean updateShortcuts(String str, ParceledListSlice parceledListSlice, int i) throws RemoteException;

    public static class Default implements IShortcutService {
        @Override // android.content.pm.IShortcutService
        public boolean setDynamicShortcuts(String packageName, ParceledListSlice shortcutInfoList, int userId) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IShortcutService
        public boolean addDynamicShortcuts(String packageName, ParceledListSlice shortcutInfoList, int userId) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IShortcutService
        public void removeDynamicShortcuts(String packageName, List<String> shortcutIds, int userId) throws RemoteException {
        }

        @Override // android.content.pm.IShortcutService
        public void removeAllDynamicShortcuts(String packageName, int userId) throws RemoteException {
        }

        @Override // android.content.pm.IShortcutService
        public boolean updateShortcuts(String packageName, ParceledListSlice shortcuts, int userId) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IShortcutService
        public void requestPinShortcut(String packageName, ShortcutInfo shortcut, IntentSender resultIntent, int userId, AndroidFuture<String> ret) throws RemoteException {
        }

        @Override // android.content.pm.IShortcutService
        public void createShortcutResultIntent(String packageName, ShortcutInfo shortcut, int userId, AndroidFuture<Intent> ret) throws RemoteException {
        }

        @Override // android.content.pm.IShortcutService
        public void disableShortcuts(String packageName, List<String> shortcutIds, CharSequence disabledMessage, int disabledMessageResId, int userId) throws RemoteException {
        }

        @Override // android.content.pm.IShortcutService
        public void enableShortcuts(String packageName, List<String> shortcutIds, int userId) throws RemoteException {
        }

        @Override // android.content.pm.IShortcutService
        public int getMaxShortcutCountPerActivity(String packageName, int userId) throws RemoteException {
            return 0;
        }

        @Override // android.content.pm.IShortcutService
        public int getRemainingCallCount(String packageName, int userId) throws RemoteException {
            return 0;
        }

        @Override // android.content.pm.IShortcutService
        public long getRateLimitResetTime(String packageName, int userId) throws RemoteException {
            return 0L;
        }

        @Override // android.content.pm.IShortcutService
        public int getIconMaxDimensions(String packageName, int userId) throws RemoteException {
            return 0;
        }

        @Override // android.content.pm.IShortcutService
        public void reportShortcutUsed(String packageName, String shortcutId, int userId) throws RemoteException {
        }

        @Override // android.content.pm.IShortcutService
        public void resetThrottling() throws RemoteException {
        }

        @Override // android.content.pm.IShortcutService
        public void onApplicationActive(String packageName, int userId) throws RemoteException {
        }

        @Override // android.content.pm.IShortcutService
        public byte[] getBackupPayload(int user) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IShortcutService
        public void applyRestore(byte[] payload, int user) throws RemoteException {
        }

        @Override // android.content.pm.IShortcutService
        public boolean isRequestPinItemSupported(int user, int requestType) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IShortcutService
        public ParceledListSlice getShareTargets(String packageName, IntentFilter filter, int userId) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IShortcutService
        public boolean hasShareTargets(String packageName, String packageToCheck, int userId) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IShortcutService
        public void removeLongLivedShortcuts(String packageName, List<String> shortcutIds, int userId) throws RemoteException {
        }

        @Override // android.content.pm.IShortcutService
        public ParceledListSlice getShortcuts(String packageName, int matchFlags, int userId) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IShortcutService
        public void pushDynamicShortcut(String packageName, ShortcutInfo shortcut, int userId) throws RemoteException {
        }

        @Override // android.content.pm.IShortcutService
        public int applyRestoreSmartSwitch(ParcelFileDescriptor payload, int userId) throws RemoteException {
            return 0;
        }

        @Override // android.content.pm.IShortcutService
        public IParcelFileDescriptorFactory getShortcutBitmapsFileDescriptor() throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IShortcutService
        public void restoreBitmapsFromBackupService(ParcelFileDescriptor target, String packageName, String bitmapPath) throws RemoteException {
        }

        @Override // android.content.pm.IShortcutService
        public String[] getBitmapPathList(int userId) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IShortcutService
        public ParcelFileDescriptor getBackupShortcut(int user) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IShortcutService
        public void handleLocaleChanged(boolean force) throws RemoteException {
        }

        @Override // android.content.pm.IShortcutService
        public void requestPinShortcutAsDisplay(String packageName, ShortcutInfo shortcut, IntentSender resultIntent, int userId, int displayId, AndroidFuture<String> ret) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IShortcutService {
        public static final String DESCRIPTOR = "android.content.pm.IShortcutService";
        static final int TRANSACTION_addDynamicShortcuts = 2;
        static final int TRANSACTION_applyRestore = 18;
        static final int TRANSACTION_applyRestoreSmartSwitch = 25;
        static final int TRANSACTION_createShortcutResultIntent = 7;
        static final int TRANSACTION_disableShortcuts = 8;
        static final int TRANSACTION_enableShortcuts = 9;
        static final int TRANSACTION_getBackupPayload = 17;
        static final int TRANSACTION_getBackupShortcut = 29;
        static final int TRANSACTION_getBitmapPathList = 28;
        static final int TRANSACTION_getIconMaxDimensions = 13;
        static final int TRANSACTION_getMaxShortcutCountPerActivity = 10;
        static final int TRANSACTION_getRateLimitResetTime = 12;
        static final int TRANSACTION_getRemainingCallCount = 11;
        static final int TRANSACTION_getShareTargets = 20;
        static final int TRANSACTION_getShortcutBitmapsFileDescriptor = 26;
        static final int TRANSACTION_getShortcuts = 23;
        static final int TRANSACTION_handleLocaleChanged = 30;
        static final int TRANSACTION_hasShareTargets = 21;
        static final int TRANSACTION_isRequestPinItemSupported = 19;
        static final int TRANSACTION_onApplicationActive = 16;
        static final int TRANSACTION_pushDynamicShortcut = 24;
        static final int TRANSACTION_removeAllDynamicShortcuts = 4;
        static final int TRANSACTION_removeDynamicShortcuts = 3;
        static final int TRANSACTION_removeLongLivedShortcuts = 22;
        static final int TRANSACTION_reportShortcutUsed = 14;
        static final int TRANSACTION_requestPinShortcut = 6;
        static final int TRANSACTION_requestPinShortcutAsDisplay = 31;
        static final int TRANSACTION_resetThrottling = 15;
        static final int TRANSACTION_restoreBitmapsFromBackupService = 27;
        static final int TRANSACTION_setDynamicShortcuts = 1;
        static final int TRANSACTION_updateShortcuts = 5;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IShortcutService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IShortcutService)) {
                return (IShortcutService) iin;
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
                    return "setDynamicShortcuts";
                case 2:
                    return "addDynamicShortcuts";
                case 3:
                    return "removeDynamicShortcuts";
                case 4:
                    return "removeAllDynamicShortcuts";
                case 5:
                    return "updateShortcuts";
                case 6:
                    return "requestPinShortcut";
                case 7:
                    return "createShortcutResultIntent";
                case 8:
                    return "disableShortcuts";
                case 9:
                    return "enableShortcuts";
                case 10:
                    return "getMaxShortcutCountPerActivity";
                case 11:
                    return "getRemainingCallCount";
                case 12:
                    return "getRateLimitResetTime";
                case 13:
                    return "getIconMaxDimensions";
                case 14:
                    return "reportShortcutUsed";
                case 15:
                    return "resetThrottling";
                case 16:
                    return "onApplicationActive";
                case 17:
                    return "getBackupPayload";
                case 18:
                    return "applyRestore";
                case 19:
                    return "isRequestPinItemSupported";
                case 20:
                    return "getShareTargets";
                case 21:
                    return "hasShareTargets";
                case 22:
                    return "removeLongLivedShortcuts";
                case 23:
                    return "getShortcuts";
                case 24:
                    return "pushDynamicShortcut";
                case 25:
                    return "applyRestoreSmartSwitch";
                case 26:
                    return "getShortcutBitmapsFileDescriptor";
                case 27:
                    return "restoreBitmapsFromBackupService";
                case 28:
                    return "getBitmapPathList";
                case 29:
                    return "getBackupShortcut";
                case 30:
                    return "handleLocaleChanged";
                case 31:
                    return "requestPinShortcutAsDisplay";
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
                    String _arg0 = data.readString();
                    ParceledListSlice _arg1 = (ParceledListSlice) data.readTypedObject(ParceledListSlice.CREATOR);
                    int _arg2 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result = setDynamicShortcuts(_arg0, _arg1, _arg2);
                    reply.writeNoException();
                    reply.writeBoolean(_result);
                    return true;
                case 2:
                    String _arg02 = data.readString();
                    ParceledListSlice _arg12 = (ParceledListSlice) data.readTypedObject(ParceledListSlice.CREATOR);
                    int _arg22 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result2 = addDynamicShortcuts(_arg02, _arg12, _arg22);
                    reply.writeNoException();
                    reply.writeBoolean(_result2);
                    return true;
                case 3:
                    String _arg03 = data.readString();
                    List<String> _arg13 = data.createStringArrayList();
                    int _arg23 = data.readInt();
                    data.enforceNoDataAvail();
                    removeDynamicShortcuts(_arg03, _arg13, _arg23);
                    reply.writeNoException();
                    return true;
                case 4:
                    String _arg04 = data.readString();
                    int _arg14 = data.readInt();
                    data.enforceNoDataAvail();
                    removeAllDynamicShortcuts(_arg04, _arg14);
                    reply.writeNoException();
                    return true;
                case 5:
                    String _arg05 = data.readString();
                    ParceledListSlice _arg15 = (ParceledListSlice) data.readTypedObject(ParceledListSlice.CREATOR);
                    int _arg24 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result3 = updateShortcuts(_arg05, _arg15, _arg24);
                    reply.writeNoException();
                    reply.writeBoolean(_result3);
                    return true;
                case 6:
                    String _arg06 = data.readString();
                    ShortcutInfo _arg16 = (ShortcutInfo) data.readTypedObject(ShortcutInfo.CREATOR);
                    IntentSender _arg25 = (IntentSender) data.readTypedObject(IntentSender.CREATOR);
                    int _arg3 = data.readInt();
                    AndroidFuture<String> _arg4 = (AndroidFuture) data.readTypedObject(AndroidFuture.CREATOR);
                    data.enforceNoDataAvail();
                    requestPinShortcut(_arg06, _arg16, _arg25, _arg3, _arg4);
                    reply.writeNoException();
                    return true;
                case 7:
                    String _arg07 = data.readString();
                    ShortcutInfo _arg17 = (ShortcutInfo) data.readTypedObject(ShortcutInfo.CREATOR);
                    int _arg26 = data.readInt();
                    AndroidFuture<Intent> _arg32 = (AndroidFuture) data.readTypedObject(AndroidFuture.CREATOR);
                    data.enforceNoDataAvail();
                    createShortcutResultIntent(_arg07, _arg17, _arg26, _arg32);
                    reply.writeNoException();
                    return true;
                case 8:
                    String _arg08 = data.readString();
                    List<String> _arg18 = data.createStringArrayList();
                    CharSequence _arg27 = (CharSequence) data.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    int _arg33 = data.readInt();
                    int _arg42 = data.readInt();
                    data.enforceNoDataAvail();
                    disableShortcuts(_arg08, _arg18, _arg27, _arg33, _arg42);
                    reply.writeNoException();
                    return true;
                case 9:
                    String _arg09 = data.readString();
                    List<String> _arg19 = data.createStringArrayList();
                    int _arg28 = data.readInt();
                    data.enforceNoDataAvail();
                    enableShortcuts(_arg09, _arg19, _arg28);
                    reply.writeNoException();
                    return true;
                case 10:
                    String _arg010 = data.readString();
                    int _arg110 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result4 = getMaxShortcutCountPerActivity(_arg010, _arg110);
                    reply.writeNoException();
                    reply.writeInt(_result4);
                    return true;
                case 11:
                    String _arg011 = data.readString();
                    int _arg111 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result5 = getRemainingCallCount(_arg011, _arg111);
                    reply.writeNoException();
                    reply.writeInt(_result5);
                    return true;
                case 12:
                    String _arg012 = data.readString();
                    int _arg112 = data.readInt();
                    data.enforceNoDataAvail();
                    long _result6 = getRateLimitResetTime(_arg012, _arg112);
                    reply.writeNoException();
                    reply.writeLong(_result6);
                    return true;
                case 13:
                    String _arg013 = data.readString();
                    int _arg113 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result7 = getIconMaxDimensions(_arg013, _arg113);
                    reply.writeNoException();
                    reply.writeInt(_result7);
                    return true;
                case 14:
                    String _arg014 = data.readString();
                    String _arg114 = data.readString();
                    int _arg29 = data.readInt();
                    data.enforceNoDataAvail();
                    reportShortcutUsed(_arg014, _arg114, _arg29);
                    reply.writeNoException();
                    return true;
                case 15:
                    resetThrottling();
                    reply.writeNoException();
                    return true;
                case 16:
                    String _arg015 = data.readString();
                    int _arg115 = data.readInt();
                    data.enforceNoDataAvail();
                    onApplicationActive(_arg015, _arg115);
                    return true;
                case 17:
                    int _arg016 = data.readInt();
                    data.enforceNoDataAvail();
                    byte[] _result8 = getBackupPayload(_arg016);
                    reply.writeNoException();
                    reply.writeByteArray(_result8);
                    return true;
                case 18:
                    byte[] _arg017 = data.createByteArray();
                    int _arg116 = data.readInt();
                    data.enforceNoDataAvail();
                    applyRestore(_arg017, _arg116);
                    reply.writeNoException();
                    return true;
                case 19:
                    int _arg018 = data.readInt();
                    int _arg117 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result9 = isRequestPinItemSupported(_arg018, _arg117);
                    reply.writeNoException();
                    reply.writeBoolean(_result9);
                    return true;
                case 20:
                    String _arg019 = data.readString();
                    IntentFilter _arg118 = (IntentFilter) data.readTypedObject(IntentFilter.CREATOR);
                    int _arg210 = data.readInt();
                    data.enforceNoDataAvail();
                    ParceledListSlice _result10 = getShareTargets(_arg019, _arg118, _arg210);
                    reply.writeNoException();
                    reply.writeTypedObject(_result10, 1);
                    return true;
                case 21:
                    String _arg020 = data.readString();
                    String _arg119 = data.readString();
                    int _arg211 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result11 = hasShareTargets(_arg020, _arg119, _arg211);
                    reply.writeNoException();
                    reply.writeBoolean(_result11);
                    return true;
                case 22:
                    String _arg021 = data.readString();
                    List<String> _arg120 = data.createStringArrayList();
                    int _arg212 = data.readInt();
                    data.enforceNoDataAvail();
                    removeLongLivedShortcuts(_arg021, _arg120, _arg212);
                    reply.writeNoException();
                    return true;
                case 23:
                    String _arg022 = data.readString();
                    int _arg121 = data.readInt();
                    int _arg213 = data.readInt();
                    data.enforceNoDataAvail();
                    ParceledListSlice _result12 = getShortcuts(_arg022, _arg121, _arg213);
                    reply.writeNoException();
                    reply.writeTypedObject(_result12, 1);
                    return true;
                case 24:
                    String _arg023 = data.readString();
                    ShortcutInfo _arg122 = (ShortcutInfo) data.readTypedObject(ShortcutInfo.CREATOR);
                    int _arg214 = data.readInt();
                    data.enforceNoDataAvail();
                    pushDynamicShortcut(_arg023, _arg122, _arg214);
                    reply.writeNoException();
                    return true;
                case 25:
                    ParcelFileDescriptor _arg024 = (ParcelFileDescriptor) data.readTypedObject(ParcelFileDescriptor.CREATOR);
                    int _arg123 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result13 = applyRestoreSmartSwitch(_arg024, _arg123);
                    reply.writeNoException();
                    reply.writeInt(_result13);
                    return true;
                case 26:
                    IParcelFileDescriptorFactory _result14 = getShortcutBitmapsFileDescriptor();
                    reply.writeNoException();
                    reply.writeStrongInterface(_result14);
                    return true;
                case 27:
                    ParcelFileDescriptor _arg025 = (ParcelFileDescriptor) data.readTypedObject(ParcelFileDescriptor.CREATOR);
                    String _arg124 = data.readString();
                    String _arg215 = data.readString();
                    data.enforceNoDataAvail();
                    restoreBitmapsFromBackupService(_arg025, _arg124, _arg215);
                    reply.writeNoException();
                    return true;
                case 28:
                    int _arg026 = data.readInt();
                    data.enforceNoDataAvail();
                    String[] _result15 = getBitmapPathList(_arg026);
                    reply.writeNoException();
                    reply.writeStringArray(_result15);
                    return true;
                case 29:
                    int _arg027 = data.readInt();
                    data.enforceNoDataAvail();
                    ParcelFileDescriptor _result16 = getBackupShortcut(_arg027);
                    reply.writeNoException();
                    reply.writeTypedObject(_result16, 1);
                    return true;
                case 30:
                    boolean _arg028 = data.readBoolean();
                    data.enforceNoDataAvail();
                    handleLocaleChanged(_arg028);
                    reply.writeNoException();
                    return true;
                case 31:
                    String _arg029 = data.readString();
                    ShortcutInfo _arg125 = (ShortcutInfo) data.readTypedObject(ShortcutInfo.CREATOR);
                    IntentSender _arg216 = (IntentSender) data.readTypedObject(IntentSender.CREATOR);
                    int _arg34 = data.readInt();
                    int _arg43 = data.readInt();
                    AndroidFuture<String> _arg5 = (AndroidFuture) data.readTypedObject(AndroidFuture.CREATOR);
                    data.enforceNoDataAvail();
                    requestPinShortcutAsDisplay(_arg029, _arg125, _arg216, _arg34, _arg43, _arg5);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IShortcutService {
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

            @Override // android.content.pm.IShortcutService
            public boolean setDynamicShortcuts(String packageName, ParceledListSlice shortcutInfoList, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeTypedObject(shortcutInfoList, 0);
                    _data.writeInt(userId);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public boolean addDynamicShortcuts(String packageName, ParceledListSlice shortcutInfoList, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeTypedObject(shortcutInfoList, 0);
                    _data.writeInt(userId);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public void removeDynamicShortcuts(String packageName, List<String> shortcutIds, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeStringList(shortcutIds);
                    _data.writeInt(userId);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public void removeAllDynamicShortcuts(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public boolean updateShortcuts(String packageName, ParceledListSlice shortcuts, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeTypedObject(shortcuts, 0);
                    _data.writeInt(userId);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public void requestPinShortcut(String packageName, ShortcutInfo shortcut, IntentSender resultIntent, int userId, AndroidFuture<String> ret) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeTypedObject(shortcut, 0);
                    _data.writeTypedObject(resultIntent, 0);
                    _data.writeInt(userId);
                    _data.writeTypedObject(ret, 0);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public void createShortcutResultIntent(String packageName, ShortcutInfo shortcut, int userId, AndroidFuture<Intent> ret) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeTypedObject(shortcut, 0);
                    _data.writeInt(userId);
                    _data.writeTypedObject(ret, 0);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public void disableShortcuts(String packageName, List<String> shortcutIds, CharSequence disabledMessage, int disabledMessageResId, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeStringList(shortcutIds);
                    if (disabledMessage != null) {
                        _data.writeInt(1);
                        TextUtils.writeToParcel(disabledMessage, _data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(disabledMessageResId);
                    _data.writeInt(userId);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public void enableShortcuts(String packageName, List<String> shortcutIds, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeStringList(shortcutIds);
                    _data.writeInt(userId);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public int getMaxShortcutCountPerActivity(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public int getRemainingCallCount(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public long getRateLimitResetTime(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public int getIconMaxDimensions(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public void reportShortcutUsed(String packageName, String shortcutId, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeString(shortcutId);
                    _data.writeInt(userId);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public void resetThrottling() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public void onApplicationActive(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(16, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public byte[] getBackupPayload(int user) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(user);
                    this.mRemote.transact(17, _data, _reply, 0);
                    _reply.readException();
                    byte[] _result = _reply.createByteArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public void applyRestore(byte[] payload, int user) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByteArray(payload);
                    _data.writeInt(user);
                    this.mRemote.transact(18, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public boolean isRequestPinItemSupported(int user, int requestType) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(user);
                    _data.writeInt(requestType);
                    this.mRemote.transact(19, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public ParceledListSlice getShareTargets(String packageName, IntentFilter filter, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeTypedObject(filter, 0);
                    _data.writeInt(userId);
                    this.mRemote.transact(20, _data, _reply, 0);
                    _reply.readException();
                    ParceledListSlice _result = (ParceledListSlice) _reply.readTypedObject(ParceledListSlice.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public boolean hasShareTargets(String packageName, String packageToCheck, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeString(packageToCheck);
                    _data.writeInt(userId);
                    this.mRemote.transact(21, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public void removeLongLivedShortcuts(String packageName, List<String> shortcutIds, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeStringList(shortcutIds);
                    _data.writeInt(userId);
                    this.mRemote.transact(22, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public ParceledListSlice getShortcuts(String packageName, int matchFlags, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(matchFlags);
                    _data.writeInt(userId);
                    this.mRemote.transact(23, _data, _reply, 0);
                    _reply.readException();
                    ParceledListSlice _result = (ParceledListSlice) _reply.readTypedObject(ParceledListSlice.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public void pushDynamicShortcut(String packageName, ShortcutInfo shortcut, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeTypedObject(shortcut, 0);
                    _data.writeInt(userId);
                    this.mRemote.transact(24, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public int applyRestoreSmartSwitch(ParcelFileDescriptor payload, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(payload, 0);
                    _data.writeInt(userId);
                    this.mRemote.transact(25, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public IParcelFileDescriptorFactory getShortcutBitmapsFileDescriptor() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(26, _data, _reply, 0);
                    _reply.readException();
                    IParcelFileDescriptorFactory _result = IParcelFileDescriptorFactory.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public void restoreBitmapsFromBackupService(ParcelFileDescriptor target, String packageName, String bitmapPath) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(target, 0);
                    _data.writeString(packageName);
                    _data.writeString(bitmapPath);
                    this.mRemote.transact(27, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public String[] getBitmapPathList(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(28, _data, _reply, 0);
                    _reply.readException();
                    String[] _result = _reply.createStringArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public ParcelFileDescriptor getBackupShortcut(int user) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(user);
                    this.mRemote.transact(29, _data, _reply, 0);
                    _reply.readException();
                    ParcelFileDescriptor _result = (ParcelFileDescriptor) _reply.readTypedObject(ParcelFileDescriptor.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public void handleLocaleChanged(boolean force) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(force);
                    this.mRemote.transact(30, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IShortcutService
            public void requestPinShortcutAsDisplay(String packageName, ShortcutInfo shortcut, IntentSender resultIntent, int userId, int displayId, AndroidFuture<String> ret) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeTypedObject(shortcut, 0);
                    _data.writeTypedObject(resultIntent, 0);
                    _data.writeInt(userId);
                    _data.writeInt(displayId);
                    _data.writeTypedObject(ret, 0);
                    this.mRemote.transact(31, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 30;
        }
    }
}
