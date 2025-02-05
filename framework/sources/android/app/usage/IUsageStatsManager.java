package android.app.usage;

import android.Manifest;
import android.app.ActivityThread;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.pm.ParceledListSlice;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.PermissionEnforcer;
import android.os.PersistableBundle;
import android.os.RemoteException;
import com.samsung.android.app.usage.IUsageStatsWatcher;
import java.util.List;

/* loaded from: classes.dex */
public interface IUsageStatsManager extends IInterface {
    void clearBroadcastEvents(String str, int i) throws RemoteException;

    void clearBroadcastResponseStats(String str, long j, String str2, int i) throws RemoteException;

    void deleteUsageStats() throws RemoteException;

    void forceUsageSourceSettingRead() throws RemoteException;

    int getAppMinStandbyBucket(String str, String str2, int i) throws RemoteException;

    int getAppStandbyBucket(String str, String str2, int i) throws RemoteException;

    ParceledListSlice getAppStandbyBuckets(String str, int i) throws RemoteException;

    String getAppStandbyConstant(String str) throws RemoteException;

    long getLastTimeAnyComponentUsed(String str, String str2) throws RemoteException;

    int getUsageSource() throws RemoteException;

    boolean isAppInactive(String str, int i, String str2) throws RemoteException;

    boolean isAppStandbyEnabled() throws RemoteException;

    boolean isPackageExemptedFromBroadcastResponseStats(String str, int i) throws RemoteException;

    void onCarrierPrivilegedAppsChanged() throws RemoteException;

    BroadcastResponseStatsList queryBroadcastResponseStats(String str, long j, String str2, int i) throws RemoteException;

    ParceledListSlice queryConfigurationStats(int i, long j, long j2, String str) throws RemoteException;

    ParceledListSlice queryEventStats(int i, long j, long j2, String str) throws RemoteException;

    UsageEvents queryEvents(long j, long j2, String str) throws RemoteException;

    UsageEvents queryEventsForPackage(long j, long j2, String str) throws RemoteException;

    UsageEvents queryEventsForPackageForUser(long j, long j2, int i, String str, String str2) throws RemoteException;

    UsageEvents queryEventsForUser(long j, long j2, int i, String str) throws RemoteException;

    UsageEvents queryEventsWithFilter(UsageEventsQuery usageEventsQuery, String str) throws RemoteException;

    ParceledListSlice queryUsageStats(int i, long j, long j2, String str, int i2) throws RemoteException;

    void registerAppUsageLimitObserver(int i, String[] strArr, long j, long j2, PendingIntent pendingIntent, String str) throws RemoteException;

    void registerAppUsageObserver(int i, String[] strArr, long j, PendingIntent pendingIntent, String str) throws RemoteException;

    void registerUsageSessionObserver(int i, String[] strArr, long j, long j2, PendingIntent pendingIntent, PendingIntent pendingIntent2, String str) throws RemoteException;

    void registerUsageStatsWatcher(IUsageStatsWatcher iUsageStatsWatcher) throws RemoteException;

    void registerUsageStatsWatcherWithComponent(IUsageStatsWatcher iUsageStatsWatcher, List<ComponentName> list) throws RemoteException;

    void reportChooserSelection(String str, int i, String str2, String[] strArr, String str3) throws RemoteException;

    void reportPastUsageStart(IBinder iBinder, String str, long j, String str2) throws RemoteException;

    void reportUsageStart(IBinder iBinder, String str, String str2) throws RemoteException;

    void reportUsageStop(IBinder iBinder, String str, String str2) throws RemoteException;

    void reportUserInteraction(String str, int i) throws RemoteException;

    void reportUserInteractionWithBundle(String str, int i, PersistableBundle persistableBundle) throws RemoteException;

    void setAppInactive(String str, boolean z, int i) throws RemoteException;

    void setAppStandbyBucket(String str, int i, int i2) throws RemoteException;

    void setAppStandbyBuckets(ParceledListSlice parceledListSlice, int i) throws RemoteException;

    void setEstimatedLaunchTime(String str, long j, int i) throws RemoteException;

    void setEstimatedLaunchTimes(ParceledListSlice parceledListSlice, int i) throws RemoteException;

    void unregisterAppUsageLimitObserver(int i, String str) throws RemoteException;

    void unregisterAppUsageObserver(int i, String str) throws RemoteException;

    void unregisterUsageSessionObserver(int i, String str) throws RemoteException;

    void unregisterUsageStatsWatcher(IUsageStatsWatcher iUsageStatsWatcher) throws RemoteException;

    public static class Default implements IUsageStatsManager {
        @Override // android.app.usage.IUsageStatsManager
        public ParceledListSlice queryUsageStats(int bucketType, long beginTime, long endTime, String callingPackage, int userId) throws RemoteException {
            return null;
        }

        @Override // android.app.usage.IUsageStatsManager
        public ParceledListSlice queryConfigurationStats(int bucketType, long beginTime, long endTime, String callingPackage) throws RemoteException {
            return null;
        }

        @Override // android.app.usage.IUsageStatsManager
        public ParceledListSlice queryEventStats(int bucketType, long beginTime, long endTime, String callingPackage) throws RemoteException {
            return null;
        }

        @Override // android.app.usage.IUsageStatsManager
        public UsageEvents queryEvents(long beginTime, long endTime, String callingPackage) throws RemoteException {
            return null;
        }

        @Override // android.app.usage.IUsageStatsManager
        public UsageEvents queryEventsForPackage(long beginTime, long endTime, String callingPackage) throws RemoteException {
            return null;
        }

        @Override // android.app.usage.IUsageStatsManager
        public UsageEvents queryEventsForUser(long beginTime, long endTime, int userId, String callingPackage) throws RemoteException {
            return null;
        }

        @Override // android.app.usage.IUsageStatsManager
        public UsageEvents queryEventsForPackageForUser(long beginTime, long endTime, int userId, String pkg, String callingPackage) throws RemoteException {
            return null;
        }

        @Override // android.app.usage.IUsageStatsManager
        public UsageEvents queryEventsWithFilter(UsageEventsQuery query, String callingPackage) throws RemoteException {
            return null;
        }

        @Override // android.app.usage.IUsageStatsManager
        public void setAppInactive(String packageName, boolean inactive, int userId) throws RemoteException {
        }

        @Override // android.app.usage.IUsageStatsManager
        public boolean isAppStandbyEnabled() throws RemoteException {
            return false;
        }

        @Override // android.app.usage.IUsageStatsManager
        public boolean isAppInactive(String packageName, int userId, String callingPackage) throws RemoteException {
            return false;
        }

        @Override // android.app.usage.IUsageStatsManager
        public void onCarrierPrivilegedAppsChanged() throws RemoteException {
        }

        @Override // android.app.usage.IUsageStatsManager
        public void reportChooserSelection(String packageName, int userId, String contentType, String[] annotations, String action) throws RemoteException {
        }

        @Override // android.app.usage.IUsageStatsManager
        public int getAppStandbyBucket(String packageName, String callingPackage, int userId) throws RemoteException {
            return 0;
        }

        @Override // android.app.usage.IUsageStatsManager
        public void setAppStandbyBucket(String packageName, int bucket, int userId) throws RemoteException {
        }

        @Override // android.app.usage.IUsageStatsManager
        public ParceledListSlice getAppStandbyBuckets(String callingPackage, int userId) throws RemoteException {
            return null;
        }

        @Override // android.app.usage.IUsageStatsManager
        public void setAppStandbyBuckets(ParceledListSlice appBuckets, int userId) throws RemoteException {
        }

        @Override // android.app.usage.IUsageStatsManager
        public int getAppMinStandbyBucket(String packageName, String callingPackage, int userId) throws RemoteException {
            return 0;
        }

        @Override // android.app.usage.IUsageStatsManager
        public void setEstimatedLaunchTime(String packageName, long estimatedLaunchTime, int userId) throws RemoteException {
        }

        @Override // android.app.usage.IUsageStatsManager
        public void setEstimatedLaunchTimes(ParceledListSlice appLaunchTimes, int userId) throws RemoteException {
        }

        @Override // android.app.usage.IUsageStatsManager
        public void registerAppUsageObserver(int observerId, String[] packages, long timeLimitMs, PendingIntent callback, String callingPackage) throws RemoteException {
        }

        @Override // android.app.usage.IUsageStatsManager
        public void unregisterAppUsageObserver(int observerId, String callingPackage) throws RemoteException {
        }

        @Override // android.app.usage.IUsageStatsManager
        public void registerUsageSessionObserver(int sessionObserverId, String[] observed, long timeLimitMs, long sessionThresholdTimeMs, PendingIntent limitReachedCallbackIntent, PendingIntent sessionEndCallbackIntent, String callingPackage) throws RemoteException {
        }

        @Override // android.app.usage.IUsageStatsManager
        public void unregisterUsageSessionObserver(int sessionObserverId, String callingPackage) throws RemoteException {
        }

        @Override // android.app.usage.IUsageStatsManager
        public void registerAppUsageLimitObserver(int observerId, String[] packages, long timeLimitMs, long timeUsedMs, PendingIntent callback, String callingPackage) throws RemoteException {
        }

        @Override // android.app.usage.IUsageStatsManager
        public void unregisterAppUsageLimitObserver(int observerId, String callingPackage) throws RemoteException {
        }

        @Override // android.app.usage.IUsageStatsManager
        public void reportUsageStart(IBinder activity, String token, String callingPackage) throws RemoteException {
        }

        @Override // android.app.usage.IUsageStatsManager
        public void reportPastUsageStart(IBinder activity, String token, long timeAgoMs, String callingPackage) throws RemoteException {
        }

        @Override // android.app.usage.IUsageStatsManager
        public void reportUsageStop(IBinder activity, String token, String callingPackage) throws RemoteException {
        }

        @Override // android.app.usage.IUsageStatsManager
        public void reportUserInteraction(String packageName, int userId) throws RemoteException {
        }

        @Override // android.app.usage.IUsageStatsManager
        public void reportUserInteractionWithBundle(String packageName, int userId, PersistableBundle eventExtras) throws RemoteException {
        }

        @Override // android.app.usage.IUsageStatsManager
        public int getUsageSource() throws RemoteException {
            return 0;
        }

        @Override // android.app.usage.IUsageStatsManager
        public void forceUsageSourceSettingRead() throws RemoteException {
        }

        @Override // android.app.usage.IUsageStatsManager
        public long getLastTimeAnyComponentUsed(String packageName, String callingPackage) throws RemoteException {
            return 0L;
        }

        @Override // android.app.usage.IUsageStatsManager
        public BroadcastResponseStatsList queryBroadcastResponseStats(String packageName, long id, String callingPackage, int userId) throws RemoteException {
            return null;
        }

        @Override // android.app.usage.IUsageStatsManager
        public void clearBroadcastResponseStats(String packageName, long id, String callingPackage, int userId) throws RemoteException {
        }

        @Override // android.app.usage.IUsageStatsManager
        public void clearBroadcastEvents(String callingPackage, int userId) throws RemoteException {
        }

        @Override // android.app.usage.IUsageStatsManager
        public boolean isPackageExemptedFromBroadcastResponseStats(String packageName, int userId) throws RemoteException {
            return false;
        }

        @Override // android.app.usage.IUsageStatsManager
        public String getAppStandbyConstant(String key) throws RemoteException {
            return null;
        }

        @Override // android.app.usage.IUsageStatsManager
        public void registerUsageStatsWatcher(IUsageStatsWatcher watcher) throws RemoteException {
        }

        @Override // android.app.usage.IUsageStatsManager
        public void registerUsageStatsWatcherWithComponent(IUsageStatsWatcher watcher, List<ComponentName> watchingComponents) throws RemoteException {
        }

        @Override // android.app.usage.IUsageStatsManager
        public void unregisterUsageStatsWatcher(IUsageStatsWatcher watcher) throws RemoteException {
        }

        @Override // android.app.usage.IUsageStatsManager
        public void deleteUsageStats() throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IUsageStatsManager {
        public static final String DESCRIPTOR = "android.app.usage.IUsageStatsManager";
        static final int TRANSACTION_clearBroadcastEvents = 37;
        static final int TRANSACTION_clearBroadcastResponseStats = 36;
        static final int TRANSACTION_deleteUsageStats = 43;
        static final int TRANSACTION_forceUsageSourceSettingRead = 33;
        static final int TRANSACTION_getAppMinStandbyBucket = 18;
        static final int TRANSACTION_getAppStandbyBucket = 14;
        static final int TRANSACTION_getAppStandbyBuckets = 16;
        static final int TRANSACTION_getAppStandbyConstant = 39;
        static final int TRANSACTION_getLastTimeAnyComponentUsed = 34;
        static final int TRANSACTION_getUsageSource = 32;
        static final int TRANSACTION_isAppInactive = 11;
        static final int TRANSACTION_isAppStandbyEnabled = 10;
        static final int TRANSACTION_isPackageExemptedFromBroadcastResponseStats = 38;
        static final int TRANSACTION_onCarrierPrivilegedAppsChanged = 12;
        static final int TRANSACTION_queryBroadcastResponseStats = 35;
        static final int TRANSACTION_queryConfigurationStats = 2;
        static final int TRANSACTION_queryEventStats = 3;
        static final int TRANSACTION_queryEvents = 4;
        static final int TRANSACTION_queryEventsForPackage = 5;
        static final int TRANSACTION_queryEventsForPackageForUser = 7;
        static final int TRANSACTION_queryEventsForUser = 6;
        static final int TRANSACTION_queryEventsWithFilter = 8;
        static final int TRANSACTION_queryUsageStats = 1;
        static final int TRANSACTION_registerAppUsageLimitObserver = 25;
        static final int TRANSACTION_registerAppUsageObserver = 21;
        static final int TRANSACTION_registerUsageSessionObserver = 23;
        static final int TRANSACTION_registerUsageStatsWatcher = 40;
        static final int TRANSACTION_registerUsageStatsWatcherWithComponent = 41;
        static final int TRANSACTION_reportChooserSelection = 13;
        static final int TRANSACTION_reportPastUsageStart = 28;
        static final int TRANSACTION_reportUsageStart = 27;
        static final int TRANSACTION_reportUsageStop = 29;
        static final int TRANSACTION_reportUserInteraction = 30;
        static final int TRANSACTION_reportUserInteractionWithBundle = 31;
        static final int TRANSACTION_setAppInactive = 9;
        static final int TRANSACTION_setAppStandbyBucket = 15;
        static final int TRANSACTION_setAppStandbyBuckets = 17;
        static final int TRANSACTION_setEstimatedLaunchTime = 19;
        static final int TRANSACTION_setEstimatedLaunchTimes = 20;
        static final int TRANSACTION_unregisterAppUsageLimitObserver = 26;
        static final int TRANSACTION_unregisterAppUsageObserver = 22;
        static final int TRANSACTION_unregisterUsageSessionObserver = 24;
        static final int TRANSACTION_unregisterUsageStatsWatcher = 42;
        private final PermissionEnforcer mEnforcer;

        public Stub(PermissionEnforcer enforcer) {
            attachInterface(this, DESCRIPTOR);
            if (enforcer == null) {
                throw new IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = enforcer;
        }

        @Deprecated
        public Stub() {
            this(PermissionEnforcer.fromContext(ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static IUsageStatsManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IUsageStatsManager)) {
                return (IUsageStatsManager) iin;
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
                    return "queryUsageStats";
                case 2:
                    return "queryConfigurationStats";
                case 3:
                    return "queryEventStats";
                case 4:
                    return "queryEvents";
                case 5:
                    return "queryEventsForPackage";
                case 6:
                    return "queryEventsForUser";
                case 7:
                    return "queryEventsForPackageForUser";
                case 8:
                    return "queryEventsWithFilter";
                case 9:
                    return "setAppInactive";
                case 10:
                    return "isAppStandbyEnabled";
                case 11:
                    return "isAppInactive";
                case 12:
                    return "onCarrierPrivilegedAppsChanged";
                case 13:
                    return "reportChooserSelection";
                case 14:
                    return "getAppStandbyBucket";
                case 15:
                    return "setAppStandbyBucket";
                case 16:
                    return "getAppStandbyBuckets";
                case 17:
                    return "setAppStandbyBuckets";
                case 18:
                    return "getAppMinStandbyBucket";
                case 19:
                    return "setEstimatedLaunchTime";
                case 20:
                    return "setEstimatedLaunchTimes";
                case 21:
                    return "registerAppUsageObserver";
                case 22:
                    return "unregisterAppUsageObserver";
                case 23:
                    return "registerUsageSessionObserver";
                case 24:
                    return "unregisterUsageSessionObserver";
                case 25:
                    return "registerAppUsageLimitObserver";
                case 26:
                    return "unregisterAppUsageLimitObserver";
                case 27:
                    return "reportUsageStart";
                case 28:
                    return "reportPastUsageStart";
                case 29:
                    return "reportUsageStop";
                case 30:
                    return "reportUserInteraction";
                case 31:
                    return "reportUserInteractionWithBundle";
                case 32:
                    return "getUsageSource";
                case 33:
                    return "forceUsageSourceSettingRead";
                case 34:
                    return "getLastTimeAnyComponentUsed";
                case 35:
                    return "queryBroadcastResponseStats";
                case 36:
                    return "clearBroadcastResponseStats";
                case 37:
                    return "clearBroadcastEvents";
                case 38:
                    return "isPackageExemptedFromBroadcastResponseStats";
                case 39:
                    return "getAppStandbyConstant";
                case 40:
                    return "registerUsageStatsWatcher";
                case 41:
                    return "registerUsageStatsWatcherWithComponent";
                case 42:
                    return "unregisterUsageStatsWatcher";
                case 43:
                    return "deleteUsageStats";
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
                    int _arg0 = data.readInt();
                    long _arg1 = data.readLong();
                    long _arg2 = data.readLong();
                    String _arg3 = data.readString();
                    int _arg4 = data.readInt();
                    data.enforceNoDataAvail();
                    ParceledListSlice _result = queryUsageStats(_arg0, _arg1, _arg2, _arg3, _arg4);
                    reply.writeNoException();
                    reply.writeTypedObject(_result, 1);
                    return true;
                case 2:
                    int _arg02 = data.readInt();
                    long _arg12 = data.readLong();
                    long _arg22 = data.readLong();
                    String _arg32 = data.readString();
                    data.enforceNoDataAvail();
                    ParceledListSlice _result2 = queryConfigurationStats(_arg02, _arg12, _arg22, _arg32);
                    reply.writeNoException();
                    reply.writeTypedObject(_result2, 1);
                    return true;
                case 3:
                    int _arg03 = data.readInt();
                    long _arg13 = data.readLong();
                    long _arg23 = data.readLong();
                    String _arg33 = data.readString();
                    data.enforceNoDataAvail();
                    ParceledListSlice _result3 = queryEventStats(_arg03, _arg13, _arg23, _arg33);
                    reply.writeNoException();
                    reply.writeTypedObject(_result3, 1);
                    return true;
                case 4:
                    long _arg04 = data.readLong();
                    long _arg14 = data.readLong();
                    String _arg24 = data.readString();
                    data.enforceNoDataAvail();
                    UsageEvents _result4 = queryEvents(_arg04, _arg14, _arg24);
                    reply.writeNoException();
                    reply.writeTypedObject(_result4, 1);
                    return true;
                case 5:
                    long _arg05 = data.readLong();
                    long _arg15 = data.readLong();
                    String _arg25 = data.readString();
                    data.enforceNoDataAvail();
                    UsageEvents _result5 = queryEventsForPackage(_arg05, _arg15, _arg25);
                    reply.writeNoException();
                    reply.writeTypedObject(_result5, 1);
                    return true;
                case 6:
                    long _arg06 = data.readLong();
                    long _arg16 = data.readLong();
                    int _arg26 = data.readInt();
                    String _arg34 = data.readString();
                    data.enforceNoDataAvail();
                    UsageEvents _result6 = queryEventsForUser(_arg06, _arg16, _arg26, _arg34);
                    reply.writeNoException();
                    reply.writeTypedObject(_result6, 1);
                    return true;
                case 7:
                    long _arg07 = data.readLong();
                    long _arg17 = data.readLong();
                    int _arg27 = data.readInt();
                    String _arg35 = data.readString();
                    String _arg42 = data.readString();
                    data.enforceNoDataAvail();
                    UsageEvents _result7 = queryEventsForPackageForUser(_arg07, _arg17, _arg27, _arg35, _arg42);
                    reply.writeNoException();
                    reply.writeTypedObject(_result7, 1);
                    return true;
                case 8:
                    UsageEventsQuery _arg08 = (UsageEventsQuery) data.readTypedObject(UsageEventsQuery.CREATOR);
                    String _arg18 = data.readString();
                    data.enforceNoDataAvail();
                    UsageEvents _result8 = queryEventsWithFilter(_arg08, _arg18);
                    reply.writeNoException();
                    reply.writeTypedObject(_result8, 1);
                    return true;
                case 9:
                    String _arg09 = data.readString();
                    boolean _arg19 = data.readBoolean();
                    int _arg28 = data.readInt();
                    data.enforceNoDataAvail();
                    setAppInactive(_arg09, _arg19, _arg28);
                    reply.writeNoException();
                    return true;
                case 10:
                    boolean _result9 = isAppStandbyEnabled();
                    reply.writeNoException();
                    reply.writeBoolean(_result9);
                    return true;
                case 11:
                    String _arg010 = data.readString();
                    int _arg110 = data.readInt();
                    String _arg29 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result10 = isAppInactive(_arg010, _arg110, _arg29);
                    reply.writeNoException();
                    reply.writeBoolean(_result10);
                    return true;
                case 12:
                    onCarrierPrivilegedAppsChanged();
                    reply.writeNoException();
                    return true;
                case 13:
                    String _arg011 = data.readString();
                    int _arg111 = data.readInt();
                    String _arg210 = data.readString();
                    String[] _arg36 = data.createStringArray();
                    String _arg43 = data.readString();
                    data.enforceNoDataAvail();
                    reportChooserSelection(_arg011, _arg111, _arg210, _arg36, _arg43);
                    reply.writeNoException();
                    return true;
                case 14:
                    String _arg012 = data.readString();
                    String _arg112 = data.readString();
                    int _arg211 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result11 = getAppStandbyBucket(_arg012, _arg112, _arg211);
                    reply.writeNoException();
                    reply.writeInt(_result11);
                    return true;
                case 15:
                    String _arg013 = data.readString();
                    int _arg113 = data.readInt();
                    int _arg212 = data.readInt();
                    data.enforceNoDataAvail();
                    setAppStandbyBucket(_arg013, _arg113, _arg212);
                    reply.writeNoException();
                    return true;
                case 16:
                    String _arg014 = data.readString();
                    int _arg114 = data.readInt();
                    data.enforceNoDataAvail();
                    ParceledListSlice _result12 = getAppStandbyBuckets(_arg014, _arg114);
                    reply.writeNoException();
                    reply.writeTypedObject(_result12, 1);
                    return true;
                case 17:
                    ParceledListSlice _arg015 = (ParceledListSlice) data.readTypedObject(ParceledListSlice.CREATOR);
                    int _arg115 = data.readInt();
                    data.enforceNoDataAvail();
                    setAppStandbyBuckets(_arg015, _arg115);
                    reply.writeNoException();
                    return true;
                case 18:
                    String _arg016 = data.readString();
                    String _arg116 = data.readString();
                    int _arg213 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result13 = getAppMinStandbyBucket(_arg016, _arg116, _arg213);
                    reply.writeNoException();
                    reply.writeInt(_result13);
                    return true;
                case 19:
                    String _arg017 = data.readString();
                    long _arg117 = data.readLong();
                    int _arg214 = data.readInt();
                    data.enforceNoDataAvail();
                    setEstimatedLaunchTime(_arg017, _arg117, _arg214);
                    reply.writeNoException();
                    return true;
                case 20:
                    ParceledListSlice _arg018 = (ParceledListSlice) data.readTypedObject(ParceledListSlice.CREATOR);
                    int _arg118 = data.readInt();
                    data.enforceNoDataAvail();
                    setEstimatedLaunchTimes(_arg018, _arg118);
                    reply.writeNoException();
                    return true;
                case 21:
                    int _arg019 = data.readInt();
                    String[] _arg119 = data.createStringArray();
                    long _arg215 = data.readLong();
                    PendingIntent _arg37 = (PendingIntent) data.readTypedObject(PendingIntent.CREATOR);
                    String _arg44 = data.readString();
                    data.enforceNoDataAvail();
                    registerAppUsageObserver(_arg019, _arg119, _arg215, _arg37, _arg44);
                    reply.writeNoException();
                    return true;
                case 22:
                    int _arg020 = data.readInt();
                    String _arg120 = data.readString();
                    data.enforceNoDataAvail();
                    unregisterAppUsageObserver(_arg020, _arg120);
                    reply.writeNoException();
                    return true;
                case 23:
                    int _arg021 = data.readInt();
                    String[] _arg121 = data.createStringArray();
                    long _arg216 = data.readLong();
                    long _arg38 = data.readLong();
                    PendingIntent _arg45 = (PendingIntent) data.readTypedObject(PendingIntent.CREATOR);
                    PendingIntent _arg5 = (PendingIntent) data.readTypedObject(PendingIntent.CREATOR);
                    String _arg6 = data.readString();
                    data.enforceNoDataAvail();
                    registerUsageSessionObserver(_arg021, _arg121, _arg216, _arg38, _arg45, _arg5, _arg6);
                    reply.writeNoException();
                    return true;
                case 24:
                    int _arg022 = data.readInt();
                    String _arg122 = data.readString();
                    data.enforceNoDataAvail();
                    unregisterUsageSessionObserver(_arg022, _arg122);
                    reply.writeNoException();
                    return true;
                case 25:
                    int _arg023 = data.readInt();
                    String[] _arg123 = data.createStringArray();
                    long _arg217 = data.readLong();
                    long _arg39 = data.readLong();
                    PendingIntent _arg46 = (PendingIntent) data.readTypedObject(PendingIntent.CREATOR);
                    String _arg52 = data.readString();
                    data.enforceNoDataAvail();
                    registerAppUsageLimitObserver(_arg023, _arg123, _arg217, _arg39, _arg46, _arg52);
                    reply.writeNoException();
                    return true;
                case 26:
                    int _arg024 = data.readInt();
                    String _arg124 = data.readString();
                    data.enforceNoDataAvail();
                    unregisterAppUsageLimitObserver(_arg024, _arg124);
                    reply.writeNoException();
                    return true;
                case 27:
                    IBinder _arg025 = data.readStrongBinder();
                    String _arg125 = data.readString();
                    String _arg218 = data.readString();
                    data.enforceNoDataAvail();
                    reportUsageStart(_arg025, _arg125, _arg218);
                    reply.writeNoException();
                    return true;
                case 28:
                    IBinder _arg026 = data.readStrongBinder();
                    String _arg126 = data.readString();
                    long _arg219 = data.readLong();
                    String _arg310 = data.readString();
                    data.enforceNoDataAvail();
                    reportPastUsageStart(_arg026, _arg126, _arg219, _arg310);
                    reply.writeNoException();
                    return true;
                case 29:
                    IBinder _arg027 = data.readStrongBinder();
                    String _arg127 = data.readString();
                    String _arg220 = data.readString();
                    data.enforceNoDataAvail();
                    reportUsageStop(_arg027, _arg127, _arg220);
                    reply.writeNoException();
                    return true;
                case 30:
                    String _arg028 = data.readString();
                    int _arg128 = data.readInt();
                    data.enforceNoDataAvail();
                    reportUserInteraction(_arg028, _arg128);
                    reply.writeNoException();
                    return true;
                case 31:
                    String _arg029 = data.readString();
                    int _arg129 = data.readInt();
                    PersistableBundle _arg221 = (PersistableBundle) data.readTypedObject(PersistableBundle.CREATOR);
                    data.enforceNoDataAvail();
                    reportUserInteractionWithBundle(_arg029, _arg129, _arg221);
                    reply.writeNoException();
                    return true;
                case 32:
                    int _result14 = getUsageSource();
                    reply.writeNoException();
                    reply.writeInt(_result14);
                    return true;
                case 33:
                    forceUsageSourceSettingRead();
                    reply.writeNoException();
                    return true;
                case 34:
                    String _arg030 = data.readString();
                    String _arg130 = data.readString();
                    data.enforceNoDataAvail();
                    long _result15 = getLastTimeAnyComponentUsed(_arg030, _arg130);
                    reply.writeNoException();
                    reply.writeLong(_result15);
                    return true;
                case 35:
                    String _arg031 = data.readString();
                    long _arg131 = data.readLong();
                    String _arg222 = data.readString();
                    int _arg311 = data.readInt();
                    data.enforceNoDataAvail();
                    BroadcastResponseStatsList _result16 = queryBroadcastResponseStats(_arg031, _arg131, _arg222, _arg311);
                    reply.writeNoException();
                    reply.writeTypedObject(_result16, 1);
                    return true;
                case 36:
                    String _arg032 = data.readString();
                    long _arg132 = data.readLong();
                    String _arg223 = data.readString();
                    int _arg312 = data.readInt();
                    data.enforceNoDataAvail();
                    clearBroadcastResponseStats(_arg032, _arg132, _arg223, _arg312);
                    reply.writeNoException();
                    return true;
                case 37:
                    String _arg033 = data.readString();
                    int _arg133 = data.readInt();
                    data.enforceNoDataAvail();
                    clearBroadcastEvents(_arg033, _arg133);
                    reply.writeNoException();
                    return true;
                case 38:
                    String _arg034 = data.readString();
                    int _arg134 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result17 = isPackageExemptedFromBroadcastResponseStats(_arg034, _arg134);
                    reply.writeNoException();
                    reply.writeBoolean(_result17);
                    return true;
                case 39:
                    String _arg035 = data.readString();
                    data.enforceNoDataAvail();
                    String _result18 = getAppStandbyConstant(_arg035);
                    reply.writeNoException();
                    reply.writeString(_result18);
                    return true;
                case 40:
                    IUsageStatsWatcher _arg036 = IUsageStatsWatcher.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    registerUsageStatsWatcher(_arg036);
                    reply.writeNoException();
                    return true;
                case 41:
                    IUsageStatsWatcher _arg037 = IUsageStatsWatcher.Stub.asInterface(data.readStrongBinder());
                    List<ComponentName> _arg135 = data.createTypedArrayList(ComponentName.CREATOR);
                    data.enforceNoDataAvail();
                    registerUsageStatsWatcherWithComponent(_arg037, _arg135);
                    reply.writeNoException();
                    return true;
                case 42:
                    IUsageStatsWatcher _arg038 = IUsageStatsWatcher.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    unregisterUsageStatsWatcher(_arg038);
                    reply.writeNoException();
                    return true;
                case 43:
                    deleteUsageStats();
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IUsageStatsManager {
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

            @Override // android.app.usage.IUsageStatsManager
            public ParceledListSlice queryUsageStats(int bucketType, long beginTime, long endTime, String callingPackage, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(bucketType);
                    _data.writeLong(beginTime);
                    _data.writeLong(endTime);
                    _data.writeString(callingPackage);
                    _data.writeInt(userId);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    ParceledListSlice _result = (ParceledListSlice) _reply.readTypedObject(ParceledListSlice.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public ParceledListSlice queryConfigurationStats(int bucketType, long beginTime, long endTime, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(bucketType);
                    _data.writeLong(beginTime);
                    _data.writeLong(endTime);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    ParceledListSlice _result = (ParceledListSlice) _reply.readTypedObject(ParceledListSlice.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public ParceledListSlice queryEventStats(int bucketType, long beginTime, long endTime, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(bucketType);
                    _data.writeLong(beginTime);
                    _data.writeLong(endTime);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    ParceledListSlice _result = (ParceledListSlice) _reply.readTypedObject(ParceledListSlice.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public UsageEvents queryEvents(long beginTime, long endTime, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(beginTime);
                    _data.writeLong(endTime);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    UsageEvents _result = (UsageEvents) _reply.readTypedObject(UsageEvents.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public UsageEvents queryEventsForPackage(long beginTime, long endTime, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(beginTime);
                    _data.writeLong(endTime);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    UsageEvents _result = (UsageEvents) _reply.readTypedObject(UsageEvents.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public UsageEvents queryEventsForUser(long beginTime, long endTime, int userId, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(beginTime);
                    _data.writeLong(endTime);
                    _data.writeInt(userId);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    UsageEvents _result = (UsageEvents) _reply.readTypedObject(UsageEvents.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public UsageEvents queryEventsForPackageForUser(long beginTime, long endTime, int userId, String pkg, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(beginTime);
                    _data.writeLong(endTime);
                    _data.writeInt(userId);
                    _data.writeString(pkg);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    UsageEvents _result = (UsageEvents) _reply.readTypedObject(UsageEvents.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public UsageEvents queryEventsWithFilter(UsageEventsQuery query, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(query, 0);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                    UsageEvents _result = (UsageEvents) _reply.readTypedObject(UsageEvents.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void setAppInactive(String packageName, boolean inactive, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeBoolean(inactive);
                    _data.writeInt(userId);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public boolean isAppStandbyEnabled() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public boolean isAppInactive(String packageName, int userId, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void onCarrierPrivilegedAppsChanged() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void reportChooserSelection(String packageName, int userId, String contentType, String[] annotations, String action) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    _data.writeString(contentType);
                    _data.writeStringArray(annotations);
                    _data.writeString(action);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public int getAppStandbyBucket(String packageName, String callingPackage, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeString(callingPackage);
                    _data.writeInt(userId);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void setAppStandbyBucket(String packageName, int bucket, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(bucket);
                    _data.writeInt(userId);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public ParceledListSlice getAppStandbyBuckets(String callingPackage, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeInt(userId);
                    this.mRemote.transact(16, _data, _reply, 0);
                    _reply.readException();
                    ParceledListSlice _result = (ParceledListSlice) _reply.readTypedObject(ParceledListSlice.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void setAppStandbyBuckets(ParceledListSlice appBuckets, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(appBuckets, 0);
                    _data.writeInt(userId);
                    this.mRemote.transact(17, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public int getAppMinStandbyBucket(String packageName, String callingPackage, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeString(callingPackage);
                    _data.writeInt(userId);
                    this.mRemote.transact(18, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void setEstimatedLaunchTime(String packageName, long estimatedLaunchTime, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeLong(estimatedLaunchTime);
                    _data.writeInt(userId);
                    this.mRemote.transact(19, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void setEstimatedLaunchTimes(ParceledListSlice appLaunchTimes, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(appLaunchTimes, 0);
                    _data.writeInt(userId);
                    this.mRemote.transact(20, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void registerAppUsageObserver(int observerId, String[] packages, long timeLimitMs, PendingIntent callback, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(observerId);
                    _data.writeStringArray(packages);
                    _data.writeLong(timeLimitMs);
                    _data.writeTypedObject(callback, 0);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(21, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void unregisterAppUsageObserver(int observerId, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(observerId);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(22, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void registerUsageSessionObserver(int sessionObserverId, String[] observed, long timeLimitMs, long sessionThresholdTimeMs, PendingIntent limitReachedCallbackIntent, PendingIntent sessionEndCallbackIntent, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sessionObserverId);
                    _data.writeStringArray(observed);
                    _data.writeLong(timeLimitMs);
                    _data.writeLong(sessionThresholdTimeMs);
                    _data.writeTypedObject(limitReachedCallbackIntent, 0);
                    _data.writeTypedObject(sessionEndCallbackIntent, 0);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(23, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void unregisterUsageSessionObserver(int sessionObserverId, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sessionObserverId);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(24, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void registerAppUsageLimitObserver(int observerId, String[] packages, long timeLimitMs, long timeUsedMs, PendingIntent callback, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(observerId);
                    _data.writeStringArray(packages);
                    _data.writeLong(timeLimitMs);
                    _data.writeLong(timeUsedMs);
                    _data.writeTypedObject(callback, 0);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(25, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void unregisterAppUsageLimitObserver(int observerId, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(observerId);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(26, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void reportUsageStart(IBinder activity, String token, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(activity);
                    _data.writeString(token);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(27, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void reportPastUsageStart(IBinder activity, String token, long timeAgoMs, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(activity);
                    _data.writeString(token);
                    _data.writeLong(timeAgoMs);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(28, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void reportUsageStop(IBinder activity, String token, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(activity);
                    _data.writeString(token);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(29, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void reportUserInteraction(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(30, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void reportUserInteractionWithBundle(String packageName, int userId, PersistableBundle eventExtras) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    _data.writeTypedObject(eventExtras, 0);
                    this.mRemote.transact(31, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public int getUsageSource() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(32, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void forceUsageSourceSettingRead() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(33, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public long getLastTimeAnyComponentUsed(String packageName, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(34, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public BroadcastResponseStatsList queryBroadcastResponseStats(String packageName, long id, String callingPackage, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeLong(id);
                    _data.writeString(callingPackage);
                    _data.writeInt(userId);
                    this.mRemote.transact(35, _data, _reply, 0);
                    _reply.readException();
                    BroadcastResponseStatsList _result = (BroadcastResponseStatsList) _reply.readTypedObject(BroadcastResponseStatsList.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void clearBroadcastResponseStats(String packageName, long id, String callingPackage, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeLong(id);
                    _data.writeString(callingPackage);
                    _data.writeInt(userId);
                    this.mRemote.transact(36, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void clearBroadcastEvents(String callingPackage, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeInt(userId);
                    this.mRemote.transact(37, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public boolean isPackageExemptedFromBroadcastResponseStats(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(38, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public String getAppStandbyConstant(String key) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(key);
                    this.mRemote.transact(39, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void registerUsageStatsWatcher(IUsageStatsWatcher watcher) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(watcher);
                    this.mRemote.transact(40, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void registerUsageStatsWatcherWithComponent(IUsageStatsWatcher watcher, List<ComponentName> watchingComponents) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(watcher);
                    _data.writeTypedList(watchingComponents, 0);
                    this.mRemote.transact(41, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void unregisterUsageStatsWatcher(IUsageStatsWatcher watcher) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(watcher);
                    this.mRemote.transact(42, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void deleteUsageStats() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(43, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        protected void setAppStandbyBucket_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CHANGE_APP_IDLE_STATE, getCallingPid(), getCallingUid());
        }

        protected void setAppStandbyBuckets_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CHANGE_APP_IDLE_STATE, getCallingPid(), getCallingUid());
        }

        protected void setEstimatedLaunchTime_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CHANGE_APP_LAUNCH_TIME_ESTIMATE, getCallingPid(), getCallingUid());
        }

        protected void setEstimatedLaunchTimes_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CHANGE_APP_LAUNCH_TIME_ESTIMATE, getCallingPid(), getCallingUid());
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 42;
        }
    }
}
