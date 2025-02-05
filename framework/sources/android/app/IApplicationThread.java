package android.app;

import android.app.ActivityOptions;
import android.app.IInstrumentationWatcher;
import android.app.IUiAutomationConnection;
import android.app.servertransaction.ClientTransaction;
import android.content.AutofillOptions;
import android.content.ComponentName;
import android.content.ContentCaptureOptions;
import android.content.IIntentReceiver;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.ParceledListSlice;
import android.content.pm.ProviderInfo;
import android.content.pm.ProviderInfoList;
import android.content.pm.ServiceInfo;
import android.content.res.CompatibilityInfo;
import android.content.res.Configuration;
import android.net.ProxyInfoWrapper;
import android.os.Binder;
import android.os.Bundle;
import android.os.Debug;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.os.SharedMemory;
import android.view.autofill.AutofillId;
import android.view.translation.TranslationSpec;
import android.view.translation.UiTranslationSpec;
import android.window.ITaskFragmentOrganizer;
import android.window.TaskFragmentTransaction;
import com.android.internal.app.IVoiceInteractor;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public interface IApplicationThread extends IInterface {
    void attachAgent(String str) throws RemoteException;

    void attachStartupAgents(String str) throws RemoteException;

    void bindApplication(String str, ApplicationInfo applicationInfo, String str2, String str3, boolean z, ProviderInfoList providerInfoList, ComponentName componentName, ProfilerInfo profilerInfo, Bundle bundle, IInstrumentationWatcher iInstrumentationWatcher, IUiAutomationConnection iUiAutomationConnection, int i, boolean z2, boolean z3, boolean z4, boolean z5, Configuration configuration, CompatibilityInfo compatibilityInfo, Map map, Bundle bundle2, String str4, AutofillOptions autofillOptions, ContentCaptureOptions contentCaptureOptions, long[] jArr, long[] jArr2, SharedMemory sharedMemory, long j, long j2, boolean z6) throws RemoteException;

    void clearDnsCache() throws RemoteException;

    void clearIdsTrainingData(boolean z) throws RemoteException;

    void dispatchPackageBroadcast(int i, String[] strArr) throws RemoteException;

    void dumpActivity(ParcelFileDescriptor parcelFileDescriptor, IBinder iBinder, String str, String[] strArr) throws RemoteException;

    void dumpCacheInfo(ParcelFileDescriptor parcelFileDescriptor, String[] strArr) throws RemoteException;

    void dumpDbInfo(ParcelFileDescriptor parcelFileDescriptor, String[] strArr) throws RemoteException;

    void dumpGfxInfo(ParcelFileDescriptor parcelFileDescriptor, String[] strArr) throws RemoteException;

    void dumpHeap(boolean z, boolean z2, boolean z3, String str, String str2, ParcelFileDescriptor parcelFileDescriptor, RemoteCallback remoteCallback) throws RemoteException;

    void dumpMemInfo(ParcelFileDescriptor parcelFileDescriptor, Debug.MemoryInfo memoryInfo, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, String[] strArr) throws RemoteException;

    void dumpMemInfoProto(ParcelFileDescriptor parcelFileDescriptor, Debug.MemoryInfo memoryInfo, boolean z, boolean z2, boolean z3, boolean z4, String[] strArr) throws RemoteException;

    void dumpProvider(ParcelFileDescriptor parcelFileDescriptor, IBinder iBinder, String[] strArr) throws RemoteException;

    void dumpResources(ParcelFileDescriptor parcelFileDescriptor, RemoteCallback remoteCallback) throws RemoteException;

    void dumpService(ParcelFileDescriptor parcelFileDescriptor, IBinder iBinder, String[] strArr) throws RemoteException;

    void getProfileLength(String str) throws RemoteException;

    void handleTrustStorageUpdate() throws RemoteException;

    void instrumentWithoutRestart(ComponentName componentName, Bundle bundle, IInstrumentationWatcher iInstrumentationWatcher, IUiAutomationConnection iUiAutomationConnection, ApplicationInfo applicationInfo) throws RemoteException;

    void notifyCleartextNetwork(byte[] bArr) throws RemoteException;

    void notifyContentProviderPublishStatus(ContentProviderHolder contentProviderHolder, String str, int i, boolean z) throws RemoteException;

    void performDirectAction(IBinder iBinder, String str, Bundle bundle, RemoteCallback remoteCallback, RemoteCallback remoteCallback2) throws RemoteException;

    void processInBackground() throws RemoteException;

    void profilerControl(boolean z, ProfilerInfo profilerInfo, int i) throws RemoteException;

    void relaunchActivityIfWebViewAttached(IBinder iBinder) throws RemoteException;

    void requestAssistContextExtras(IBinder iBinder, IBinder iBinder2, int i, int i2, int i3) throws RemoteException;

    void requestDirectActions(IBinder iBinder, IVoiceInteractor iVoiceInteractor, RemoteCallback remoteCallback, RemoteCallback remoteCallback2) throws RemoteException;

    void runIsolatedEntryPoint(String str, String[] strArr) throws RemoteException;

    void scheduleApplicationInfoChanged(ApplicationInfo applicationInfo) throws RemoteException;

    void scheduleBindService(IBinder iBinder, Intent intent, boolean z, int i, long j) throws RemoteException;

    void scheduleCrash(String str, int i, Bundle bundle) throws RemoteException;

    void scheduleCreateBackupAgent(ApplicationInfo applicationInfo, int i, int i2, int i3) throws RemoteException;

    void scheduleCreateService(IBinder iBinder, ServiceInfo serviceInfo, CompatibilityInfo compatibilityInfo, int i) throws RemoteException;

    void scheduleDestroyBackupAgent(ApplicationInfo applicationInfo, int i) throws RemoteException;

    void scheduleEnterAnimationComplete(IBinder iBinder) throws RemoteException;

    void scheduleExit() throws RemoteException;

    void scheduleInstallProvider(ProviderInfo providerInfo) throws RemoteException;

    void scheduleLocalVoiceInteractionStarted(IBinder iBinder, IVoiceInteractor iVoiceInteractor) throws RemoteException;

    void scheduleLowMemory() throws RemoteException;

    void scheduleOnNewSceneTransitionInfo(IBinder iBinder, ActivityOptions.SceneTransitionInfo sceneTransitionInfo) throws RemoteException;

    void schedulePing(RemoteCallback remoteCallback) throws RemoteException;

    void scheduleReceiver(Intent intent, ActivityInfo activityInfo, CompatibilityInfo compatibilityInfo, int i, String str, Bundle bundle, boolean z, boolean z2, int i2, int i3, int i4, String str2) throws RemoteException;

    void scheduleReceiverList(List<ReceiverInfo> list) throws RemoteException;

    void scheduleRegisteredReceiver(IIntentReceiver iIntentReceiver, Intent intent, int i, String str, Bundle bundle, boolean z, boolean z2, boolean z3, int i2, int i3, int i4, String str2) throws RemoteException;

    void scheduleServiceArgs(IBinder iBinder, ParceledListSlice parceledListSlice) throws RemoteException;

    void scheduleStopService(IBinder iBinder) throws RemoteException;

    void scheduleSuicide() throws RemoteException;

    void scheduleTaskFragmentTransaction(ITaskFragmentOrganizer iTaskFragmentOrganizer, TaskFragmentTransaction taskFragmentTransaction) throws RemoteException;

    void scheduleTimeoutService(IBinder iBinder, int i) throws RemoteException;

    void scheduleTimeoutServiceForType(IBinder iBinder, int i, int i2) throws RemoteException;

    void scheduleTransaction(ClientTransaction clientTransaction) throws RemoteException;

    void scheduleTranslucentConversionComplete(IBinder iBinder, boolean z) throws RemoteException;

    void scheduleTrimMemory(int i) throws RemoteException;

    void scheduleUnbindService(IBinder iBinder, Intent intent) throws RemoteException;

    void setCoreSettings(Bundle bundle) throws RemoteException;

    void setFlingerFlag(String str) throws RemoteException;

    void setHttpProxyInfo(ProxyInfoWrapper proxyInfoWrapper) throws RemoteException;

    void setNetworkBlockSeq(long j) throws RemoteException;

    void setProcessState(int i) throws RemoteException;

    void setSchedulingGroup(int i) throws RemoteException;

    void startBinderTracking() throws RemoteException;

    void stopBinderTrackingAndDump(ParcelFileDescriptor parcelFileDescriptor) throws RemoteException;

    void stopBinderTrackingAndDumpSystemServer(ParcelFileDescriptor parcelFileDescriptor, String str, String str2, int i, int i2) throws RemoteException;

    void unstableProviderDied(IBinder iBinder) throws RemoteException;

    void updateHttpProxy() throws RemoteException;

    void updatePackageCompatibilityInfo(String str, CompatibilityInfo compatibilityInfo) throws RemoteException;

    void updateTimePrefs(int i) throws RemoteException;

    void updateTimeZone() throws RemoteException;

    void updateUiTranslationState(IBinder iBinder, int i, TranslationSpec translationSpec, TranslationSpec translationSpec2, List<AutofillId> list, UiTranslationSpec uiTranslationSpec) throws RemoteException;

    public static class Default implements IApplicationThread {
        @Override // android.app.IApplicationThread
        public void scheduleReceiver(Intent intent, ActivityInfo info, CompatibilityInfo compatInfo, int resultCode, String data, Bundle extras, boolean ordered, boolean assumeDelivered, int sendingUser, int processState, int sentFromUid, String sentFromPackage) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleReceiverList(List<ReceiverInfo> info) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleCreateService(IBinder token, ServiceInfo info, CompatibilityInfo compatInfo, int processState) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleStopService(IBinder token) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void bindApplication(String packageName, ApplicationInfo info, String sdkSandboxClientAppVolumeUuid, String sdkSandboxClientAppPackage, boolean isSdkInSandbox, ProviderInfoList providerList, ComponentName testName, ProfilerInfo profilerInfo, Bundle testArguments, IInstrumentationWatcher testWatcher, IUiAutomationConnection uiAutomationConnection, int debugMode, boolean enableBinderTracking, boolean trackAllocation, boolean restrictedBackupMode, boolean persistent, Configuration config, CompatibilityInfo compatInfo, Map services, Bundle coreSettings, String buildSerial, AutofillOptions autofillOptions, ContentCaptureOptions contentCaptureOptions, long[] disabledCompatChanges, long[] loggableCompatChanges, SharedMemory serializedSystemFontMap, long startRequestedElapsedTime, long startRequestedUptime, boolean fixedAppContextDisplay) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void runIsolatedEntryPoint(String entryPoint, String[] entryPointArgs) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleExit() throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleServiceArgs(IBinder token, ParceledListSlice args) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void updateTimeZone() throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void processInBackground() throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleBindService(IBinder token, Intent intent, boolean rebind, int processState, long bindSeq) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleUnbindService(IBinder token, Intent intent) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void dumpService(ParcelFileDescriptor fd, IBinder servicetoken, String[] args) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleRegisteredReceiver(IIntentReceiver receiver, Intent intent, int resultCode, String data, Bundle extras, boolean ordered, boolean sticky, boolean assumeDelivered, int sendingUser, int processState, int sentFromUid, String sentFromPackage) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleLowMemory() throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void profilerControl(boolean start, ProfilerInfo profilerInfo, int profileType) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void setSchedulingGroup(int group) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleCreateBackupAgent(ApplicationInfo app, int backupMode, int userId, int operationType) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleDestroyBackupAgent(ApplicationInfo app, int userId) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleOnNewSceneTransitionInfo(IBinder token, ActivityOptions.SceneTransitionInfo info) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleSuicide() throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void dispatchPackageBroadcast(int cmd, String[] packages) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleCrash(String msg, int typeId, Bundle extras) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void dumpHeap(boolean managed, boolean mallocInfo, boolean runGc, String dumpBitmaps, String path, ParcelFileDescriptor fd, RemoteCallback finishCallback) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void dumpActivity(ParcelFileDescriptor fd, IBinder servicetoken, String prefix, String[] args) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void dumpResources(ParcelFileDescriptor fd, RemoteCallback finishCallback) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void clearDnsCache() throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void updateHttpProxy() throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void setHttpProxyInfo(ProxyInfoWrapper wrapper) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void setCoreSettings(Bundle coreSettings) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void updatePackageCompatibilityInfo(String pkg, CompatibilityInfo info) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleTrimMemory(int level) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void dumpMemInfo(ParcelFileDescriptor fd, Debug.MemoryInfo mem, boolean checkin, boolean dumpInfo, boolean dumpDalvik, boolean dumpSummaryOnly, boolean dumpUnreachable, boolean dumpAllocatorLogs, String[] args) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void dumpMemInfoProto(ParcelFileDescriptor fd, Debug.MemoryInfo mem, boolean dumpInfo, boolean dumpDalvik, boolean dumpSummaryOnly, boolean dumpUnreachable, String[] args) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void dumpGfxInfo(ParcelFileDescriptor fd, String[] args) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void dumpCacheInfo(ParcelFileDescriptor fd, String[] args) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void dumpProvider(ParcelFileDescriptor fd, IBinder servicetoken, String[] args) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void dumpDbInfo(ParcelFileDescriptor fd, String[] args) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void unstableProviderDied(IBinder provider) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void requestAssistContextExtras(IBinder activityToken, IBinder requestToken, int requestType, int sessionId, int flags) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleTranslucentConversionComplete(IBinder token, boolean timeout) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void setProcessState(int state) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleInstallProvider(ProviderInfo provider) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void updateTimePrefs(int timeFormatPreference) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleEnterAnimationComplete(IBinder token) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void notifyCleartextNetwork(byte[] firstPacket) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void startBinderTracking() throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void stopBinderTrackingAndDump(ParcelFileDescriptor fd) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void stopBinderTrackingAndDumpSystemServer(ParcelFileDescriptor fd, String processName, String packageName, int pid, int uid) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleLocalVoiceInteractionStarted(IBinder token, IVoiceInteractor voiceInteractor) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void handleTrustStorageUpdate() throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void attachAgent(String path) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void attachStartupAgents(String dataDir) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleApplicationInfoChanged(ApplicationInfo ai) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void setNetworkBlockSeq(long procStateSeq) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleTransaction(ClientTransaction transaction) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleTaskFragmentTransaction(ITaskFragmentOrganizer organizer, TaskFragmentTransaction transaction) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void requestDirectActions(IBinder activityToken, IVoiceInteractor intractor, RemoteCallback cancellationCallback, RemoteCallback callback) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void performDirectAction(IBinder activityToken, String actionId, Bundle arguments, RemoteCallback cancellationCallback, RemoteCallback resultCallback) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void notifyContentProviderPublishStatus(ContentProviderHolder holder, String authorities, int userId, boolean published) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void instrumentWithoutRestart(ComponentName instrumentationName, Bundle instrumentationArgs, IInstrumentationWatcher instrumentationWatcher, IUiAutomationConnection instrumentationUiConnection, ApplicationInfo targetInfo) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void updateUiTranslationState(IBinder activityToken, int state, TranslationSpec sourceSpec, TranslationSpec targetSpec, List<AutofillId> viewIds, UiTranslationSpec uiTranslationSpec) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleTimeoutService(IBinder token, int startId) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void scheduleTimeoutServiceForType(IBinder token, int startId, int fgsType) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void schedulePing(RemoteCallback pong) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void getProfileLength(String pkgName) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void setFlingerFlag(String pkgName) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void clearIdsTrainingData(boolean flag) throws RemoteException {
        }

        @Override // android.app.IApplicationThread
        public void relaunchActivityIfWebViewAttached(IBinder token) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IApplicationThread {
        public static final String DESCRIPTOR = "android.app.IApplicationThread";
        static final int TRANSACTION_attachAgent = 52;
        static final int TRANSACTION_attachStartupAgents = 53;
        static final int TRANSACTION_bindApplication = 5;
        static final int TRANSACTION_clearDnsCache = 27;
        static final int TRANSACTION_clearIdsTrainingData = 68;
        static final int TRANSACTION_dispatchPackageBroadcast = 22;
        static final int TRANSACTION_dumpActivity = 25;
        static final int TRANSACTION_dumpCacheInfo = 36;
        static final int TRANSACTION_dumpDbInfo = 38;
        static final int TRANSACTION_dumpGfxInfo = 35;
        static final int TRANSACTION_dumpHeap = 24;
        static final int TRANSACTION_dumpMemInfo = 33;
        static final int TRANSACTION_dumpMemInfoProto = 34;
        static final int TRANSACTION_dumpProvider = 37;
        static final int TRANSACTION_dumpResources = 26;
        static final int TRANSACTION_dumpService = 13;
        static final int TRANSACTION_getProfileLength = 66;
        static final int TRANSACTION_handleTrustStorageUpdate = 51;
        static final int TRANSACTION_instrumentWithoutRestart = 61;
        static final int TRANSACTION_notifyCleartextNetwork = 46;
        static final int TRANSACTION_notifyContentProviderPublishStatus = 60;
        static final int TRANSACTION_performDirectAction = 59;
        static final int TRANSACTION_processInBackground = 10;
        static final int TRANSACTION_profilerControl = 16;
        static final int TRANSACTION_relaunchActivityIfWebViewAttached = 69;
        static final int TRANSACTION_requestAssistContextExtras = 40;
        static final int TRANSACTION_requestDirectActions = 58;
        static final int TRANSACTION_runIsolatedEntryPoint = 6;
        static final int TRANSACTION_scheduleApplicationInfoChanged = 54;
        static final int TRANSACTION_scheduleBindService = 11;
        static final int TRANSACTION_scheduleCrash = 23;
        static final int TRANSACTION_scheduleCreateBackupAgent = 18;
        static final int TRANSACTION_scheduleCreateService = 3;
        static final int TRANSACTION_scheduleDestroyBackupAgent = 19;
        static final int TRANSACTION_scheduleEnterAnimationComplete = 45;
        static final int TRANSACTION_scheduleExit = 7;
        static final int TRANSACTION_scheduleInstallProvider = 43;
        static final int TRANSACTION_scheduleLocalVoiceInteractionStarted = 50;
        static final int TRANSACTION_scheduleLowMemory = 15;
        static final int TRANSACTION_scheduleOnNewSceneTransitionInfo = 20;
        static final int TRANSACTION_schedulePing = 65;
        static final int TRANSACTION_scheduleReceiver = 1;
        static final int TRANSACTION_scheduleReceiverList = 2;
        static final int TRANSACTION_scheduleRegisteredReceiver = 14;
        static final int TRANSACTION_scheduleServiceArgs = 8;
        static final int TRANSACTION_scheduleStopService = 4;
        static final int TRANSACTION_scheduleSuicide = 21;
        static final int TRANSACTION_scheduleTaskFragmentTransaction = 57;
        static final int TRANSACTION_scheduleTimeoutService = 63;
        static final int TRANSACTION_scheduleTimeoutServiceForType = 64;
        static final int TRANSACTION_scheduleTransaction = 56;
        static final int TRANSACTION_scheduleTranslucentConversionComplete = 41;
        static final int TRANSACTION_scheduleTrimMemory = 32;
        static final int TRANSACTION_scheduleUnbindService = 12;
        static final int TRANSACTION_setCoreSettings = 30;
        static final int TRANSACTION_setFlingerFlag = 67;
        static final int TRANSACTION_setHttpProxyInfo = 29;
        static final int TRANSACTION_setNetworkBlockSeq = 55;
        static final int TRANSACTION_setProcessState = 42;
        static final int TRANSACTION_setSchedulingGroup = 17;
        static final int TRANSACTION_startBinderTracking = 47;
        static final int TRANSACTION_stopBinderTrackingAndDump = 48;
        static final int TRANSACTION_stopBinderTrackingAndDumpSystemServer = 49;
        static final int TRANSACTION_unstableProviderDied = 39;
        static final int TRANSACTION_updateHttpProxy = 28;
        static final int TRANSACTION_updatePackageCompatibilityInfo = 31;
        static final int TRANSACTION_updateTimePrefs = 44;
        static final int TRANSACTION_updateTimeZone = 9;
        static final int TRANSACTION_updateUiTranslationState = 62;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IApplicationThread asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IApplicationThread)) {
                return (IApplicationThread) iin;
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
                    return "scheduleReceiver";
                case 2:
                    return "scheduleReceiverList";
                case 3:
                    return "scheduleCreateService";
                case 4:
                    return "scheduleStopService";
                case 5:
                    return "bindApplication";
                case 6:
                    return "runIsolatedEntryPoint";
                case 7:
                    return "scheduleExit";
                case 8:
                    return "scheduleServiceArgs";
                case 9:
                    return "updateTimeZone";
                case 10:
                    return "processInBackground";
                case 11:
                    return "scheduleBindService";
                case 12:
                    return "scheduleUnbindService";
                case 13:
                    return "dumpService";
                case 14:
                    return "scheduleRegisteredReceiver";
                case 15:
                    return "scheduleLowMemory";
                case 16:
                    return "profilerControl";
                case 17:
                    return "setSchedulingGroup";
                case 18:
                    return "scheduleCreateBackupAgent";
                case 19:
                    return "scheduleDestroyBackupAgent";
                case 20:
                    return "scheduleOnNewSceneTransitionInfo";
                case 21:
                    return "scheduleSuicide";
                case 22:
                    return "dispatchPackageBroadcast";
                case 23:
                    return "scheduleCrash";
                case 24:
                    return "dumpHeap";
                case 25:
                    return "dumpActivity";
                case 26:
                    return "dumpResources";
                case 27:
                    return "clearDnsCache";
                case 28:
                    return "updateHttpProxy";
                case 29:
                    return "setHttpProxyInfo";
                case 30:
                    return "setCoreSettings";
                case 31:
                    return "updatePackageCompatibilityInfo";
                case 32:
                    return "scheduleTrimMemory";
                case 33:
                    return "dumpMemInfo";
                case 34:
                    return "dumpMemInfoProto";
                case 35:
                    return "dumpGfxInfo";
                case 36:
                    return "dumpCacheInfo";
                case 37:
                    return "dumpProvider";
                case 38:
                    return "dumpDbInfo";
                case 39:
                    return "unstableProviderDied";
                case 40:
                    return "requestAssistContextExtras";
                case 41:
                    return "scheduleTranslucentConversionComplete";
                case 42:
                    return "setProcessState";
                case 43:
                    return "scheduleInstallProvider";
                case 44:
                    return "updateTimePrefs";
                case 45:
                    return "scheduleEnterAnimationComplete";
                case 46:
                    return "notifyCleartextNetwork";
                case 47:
                    return "startBinderTracking";
                case 48:
                    return "stopBinderTrackingAndDump";
                case 49:
                    return "stopBinderTrackingAndDumpSystemServer";
                case 50:
                    return "scheduleLocalVoiceInteractionStarted";
                case 51:
                    return "handleTrustStorageUpdate";
                case 52:
                    return "attachAgent";
                case 53:
                    return "attachStartupAgents";
                case 54:
                    return "scheduleApplicationInfoChanged";
                case 55:
                    return "setNetworkBlockSeq";
                case 56:
                    return "scheduleTransaction";
                case 57:
                    return "scheduleTaskFragmentTransaction";
                case 58:
                    return "requestDirectActions";
                case 59:
                    return "performDirectAction";
                case 60:
                    return "notifyContentProviderPublishStatus";
                case 61:
                    return "instrumentWithoutRestart";
                case 62:
                    return "updateUiTranslationState";
                case 63:
                    return "scheduleTimeoutService";
                case 64:
                    return "scheduleTimeoutServiceForType";
                case 65:
                    return "schedulePing";
                case 66:
                    return "getProfileLength";
                case 67:
                    return "setFlingerFlag";
                case 68:
                    return "clearIdsTrainingData";
                case 69:
                    return "relaunchActivityIfWebViewAttached";
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
                    Intent _arg0 = (Intent) data.readTypedObject(Intent.CREATOR);
                    ActivityInfo _arg1 = (ActivityInfo) data.readTypedObject(ActivityInfo.CREATOR);
                    CompatibilityInfo _arg2 = (CompatibilityInfo) data.readTypedObject(CompatibilityInfo.CREATOR);
                    int _arg3 = data.readInt();
                    String _arg4 = data.readString();
                    Bundle _arg5 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    boolean _arg6 = data.readBoolean();
                    boolean _arg7 = data.readBoolean();
                    int _arg8 = data.readInt();
                    int _arg9 = data.readInt();
                    int _arg10 = data.readInt();
                    String _arg11 = data.readString();
                    data.enforceNoDataAvail();
                    scheduleReceiver(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5, _arg6, _arg7, _arg8, _arg9, _arg10, _arg11);
                    return true;
                case 2:
                    List<ReceiverInfo> _arg02 = data.createTypedArrayList(ReceiverInfo.CREATOR);
                    data.enforceNoDataAvail();
                    scheduleReceiverList(_arg02);
                    return true;
                case 3:
                    IBinder _arg03 = data.readStrongBinder();
                    ServiceInfo _arg12 = (ServiceInfo) data.readTypedObject(ServiceInfo.CREATOR);
                    CompatibilityInfo _arg22 = (CompatibilityInfo) data.readTypedObject(CompatibilityInfo.CREATOR);
                    int _arg32 = data.readInt();
                    data.enforceNoDataAvail();
                    scheduleCreateService(_arg03, _arg12, _arg22, _arg32);
                    return true;
                case 4:
                    IBinder _arg04 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    scheduleStopService(_arg04);
                    return true;
                case 5:
                    String _arg05 = data.readString();
                    ApplicationInfo _arg13 = (ApplicationInfo) data.readTypedObject(ApplicationInfo.CREATOR);
                    String _arg23 = data.readString();
                    String _arg33 = data.readString();
                    boolean _arg42 = data.readBoolean();
                    ProviderInfoList _arg52 = (ProviderInfoList) data.readTypedObject(ProviderInfoList.CREATOR);
                    ComponentName _arg62 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    ProfilerInfo _arg72 = (ProfilerInfo) data.readTypedObject(ProfilerInfo.CREATOR);
                    Bundle _arg82 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    IInstrumentationWatcher _arg92 = IInstrumentationWatcher.Stub.asInterface(data.readStrongBinder());
                    IUiAutomationConnection _arg102 = IUiAutomationConnection.Stub.asInterface(data.readStrongBinder());
                    int _arg112 = data.readInt();
                    boolean _arg122 = data.readBoolean();
                    boolean _arg132 = data.readBoolean();
                    boolean _arg14 = data.readBoolean();
                    boolean _arg15 = data.readBoolean();
                    Configuration _arg16 = (Configuration) data.readTypedObject(Configuration.CREATOR);
                    CompatibilityInfo _arg17 = (CompatibilityInfo) data.readTypedObject(CompatibilityInfo.CREATOR);
                    ClassLoader cl = getClass().getClassLoader();
                    Map _arg18 = data.readHashMap(cl);
                    Bundle _arg19 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    String _arg20 = data.readString();
                    AutofillOptions _arg21 = (AutofillOptions) data.readTypedObject(AutofillOptions.CREATOR);
                    ContentCaptureOptions _arg222 = (ContentCaptureOptions) data.readTypedObject(ContentCaptureOptions.CREATOR);
                    long[] _arg232 = data.createLongArray();
                    long[] _arg24 = data.createLongArray();
                    SharedMemory _arg25 = (SharedMemory) data.readTypedObject(SharedMemory.CREATOR);
                    long _arg26 = data.readLong();
                    long _arg27 = data.readLong();
                    boolean _arg28 = data.readBoolean();
                    data.enforceNoDataAvail();
                    bindApplication(_arg05, _arg13, _arg23, _arg33, _arg42, _arg52, _arg62, _arg72, _arg82, _arg92, _arg102, _arg112, _arg122, _arg132, _arg14, _arg15, _arg16, _arg17, _arg18, _arg19, _arg20, _arg21, _arg222, _arg232, _arg24, _arg25, _arg26, _arg27, _arg28);
                    return true;
                case 6:
                    String _arg06 = data.readString();
                    String[] _arg110 = data.createStringArray();
                    data.enforceNoDataAvail();
                    runIsolatedEntryPoint(_arg06, _arg110);
                    return true;
                case 7:
                    scheduleExit();
                    return true;
                case 8:
                    IBinder _arg07 = data.readStrongBinder();
                    ParceledListSlice _arg111 = (ParceledListSlice) data.readTypedObject(ParceledListSlice.CREATOR);
                    data.enforceNoDataAvail();
                    scheduleServiceArgs(_arg07, _arg111);
                    return true;
                case 9:
                    updateTimeZone();
                    return true;
                case 10:
                    processInBackground();
                    return true;
                case 11:
                    IBinder _arg08 = data.readStrongBinder();
                    Intent _arg113 = (Intent) data.readTypedObject(Intent.CREATOR);
                    boolean _arg29 = data.readBoolean();
                    int _arg34 = data.readInt();
                    long _arg43 = data.readLong();
                    data.enforceNoDataAvail();
                    scheduleBindService(_arg08, _arg113, _arg29, _arg34, _arg43);
                    return true;
                case 12:
                    IBinder _arg09 = data.readStrongBinder();
                    Intent _arg114 = (Intent) data.readTypedObject(Intent.CREATOR);
                    data.enforceNoDataAvail();
                    scheduleUnbindService(_arg09, _arg114);
                    return true;
                case 13:
                    ParcelFileDescriptor _arg010 = (ParcelFileDescriptor) data.readTypedObject(ParcelFileDescriptor.CREATOR);
                    IBinder _arg115 = data.readStrongBinder();
                    String[] _arg210 = data.createStringArray();
                    data.enforceNoDataAvail();
                    dumpService(_arg010, _arg115, _arg210);
                    return true;
                case 14:
                    IIntentReceiver _arg011 = IIntentReceiver.Stub.asInterface(data.readStrongBinder());
                    Intent _arg116 = (Intent) data.readTypedObject(Intent.CREATOR);
                    int _arg211 = data.readInt();
                    String _arg35 = data.readString();
                    Bundle _arg44 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    boolean _arg53 = data.readBoolean();
                    boolean _arg63 = data.readBoolean();
                    boolean _arg73 = data.readBoolean();
                    int _arg83 = data.readInt();
                    int _arg93 = data.readInt();
                    int _arg103 = data.readInt();
                    String _arg117 = data.readString();
                    data.enforceNoDataAvail();
                    scheduleRegisteredReceiver(_arg011, _arg116, _arg211, _arg35, _arg44, _arg53, _arg63, _arg73, _arg83, _arg93, _arg103, _arg117);
                    return true;
                case 15:
                    scheduleLowMemory();
                    return true;
                case 16:
                    boolean _arg012 = data.readBoolean();
                    ProfilerInfo _arg118 = (ProfilerInfo) data.readTypedObject(ProfilerInfo.CREATOR);
                    int _arg212 = data.readInt();
                    data.enforceNoDataAvail();
                    profilerControl(_arg012, _arg118, _arg212);
                    return true;
                case 17:
                    int _arg013 = data.readInt();
                    data.enforceNoDataAvail();
                    setSchedulingGroup(_arg013);
                    return true;
                case 18:
                    ApplicationInfo _arg014 = (ApplicationInfo) data.readTypedObject(ApplicationInfo.CREATOR);
                    int _arg119 = data.readInt();
                    int _arg213 = data.readInt();
                    int _arg36 = data.readInt();
                    data.enforceNoDataAvail();
                    scheduleCreateBackupAgent(_arg014, _arg119, _arg213, _arg36);
                    return true;
                case 19:
                    ApplicationInfo _arg015 = (ApplicationInfo) data.readTypedObject(ApplicationInfo.CREATOR);
                    int _arg120 = data.readInt();
                    data.enforceNoDataAvail();
                    scheduleDestroyBackupAgent(_arg015, _arg120);
                    return true;
                case 20:
                    IBinder _arg016 = data.readStrongBinder();
                    ActivityOptions.SceneTransitionInfo _arg121 = (ActivityOptions.SceneTransitionInfo) data.readTypedObject(ActivityOptions.SceneTransitionInfo.CREATOR);
                    data.enforceNoDataAvail();
                    scheduleOnNewSceneTransitionInfo(_arg016, _arg121);
                    return true;
                case 21:
                    scheduleSuicide();
                    return true;
                case 22:
                    int _arg017 = data.readInt();
                    String[] _arg123 = data.createStringArray();
                    data.enforceNoDataAvail();
                    dispatchPackageBroadcast(_arg017, _arg123);
                    return true;
                case 23:
                    String _arg018 = data.readString();
                    int _arg124 = data.readInt();
                    Bundle _arg214 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    data.enforceNoDataAvail();
                    scheduleCrash(_arg018, _arg124, _arg214);
                    return true;
                case 24:
                    boolean _arg019 = data.readBoolean();
                    boolean _arg125 = data.readBoolean();
                    boolean _arg215 = data.readBoolean();
                    String _arg37 = data.readString();
                    String _arg45 = data.readString();
                    ParcelFileDescriptor _arg54 = (ParcelFileDescriptor) data.readTypedObject(ParcelFileDescriptor.CREATOR);
                    RemoteCallback _arg64 = (RemoteCallback) data.readTypedObject(RemoteCallback.CREATOR);
                    data.enforceNoDataAvail();
                    dumpHeap(_arg019, _arg125, _arg215, _arg37, _arg45, _arg54, _arg64);
                    return true;
                case 25:
                    ParcelFileDescriptor _arg020 = (ParcelFileDescriptor) data.readTypedObject(ParcelFileDescriptor.CREATOR);
                    IBinder _arg126 = data.readStrongBinder();
                    String _arg216 = data.readString();
                    String[] _arg38 = data.createStringArray();
                    data.enforceNoDataAvail();
                    dumpActivity(_arg020, _arg126, _arg216, _arg38);
                    return true;
                case 26:
                    ParcelFileDescriptor _arg021 = (ParcelFileDescriptor) data.readTypedObject(ParcelFileDescriptor.CREATOR);
                    RemoteCallback _arg127 = (RemoteCallback) data.readTypedObject(RemoteCallback.CREATOR);
                    data.enforceNoDataAvail();
                    dumpResources(_arg021, _arg127);
                    return true;
                case 27:
                    clearDnsCache();
                    return true;
                case 28:
                    updateHttpProxy();
                    return true;
                case 29:
                    ProxyInfoWrapper _arg022 = (ProxyInfoWrapper) data.readTypedObject(ProxyInfoWrapper.CREATOR);
                    data.enforceNoDataAvail();
                    setHttpProxyInfo(_arg022);
                    return true;
                case 30:
                    Bundle _arg023 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    data.enforceNoDataAvail();
                    setCoreSettings(_arg023);
                    return true;
                case 31:
                    String _arg024 = data.readString();
                    CompatibilityInfo _arg128 = (CompatibilityInfo) data.readTypedObject(CompatibilityInfo.CREATOR);
                    data.enforceNoDataAvail();
                    updatePackageCompatibilityInfo(_arg024, _arg128);
                    return true;
                case 32:
                    int _arg025 = data.readInt();
                    data.enforceNoDataAvail();
                    scheduleTrimMemory(_arg025);
                    return true;
                case 33:
                    ParcelFileDescriptor _arg026 = (ParcelFileDescriptor) data.readTypedObject(ParcelFileDescriptor.CREATOR);
                    Debug.MemoryInfo _arg129 = (Debug.MemoryInfo) data.readTypedObject(Debug.MemoryInfo.CREATOR);
                    boolean _arg217 = data.readBoolean();
                    boolean _arg39 = data.readBoolean();
                    boolean _arg46 = data.readBoolean();
                    boolean _arg55 = data.readBoolean();
                    boolean _arg65 = data.readBoolean();
                    boolean _arg74 = data.readBoolean();
                    String[] _arg84 = data.createStringArray();
                    data.enforceNoDataAvail();
                    dumpMemInfo(_arg026, _arg129, _arg217, _arg39, _arg46, _arg55, _arg65, _arg74, _arg84);
                    return true;
                case 34:
                    ParcelFileDescriptor _arg027 = (ParcelFileDescriptor) data.readTypedObject(ParcelFileDescriptor.CREATOR);
                    Debug.MemoryInfo _arg130 = (Debug.MemoryInfo) data.readTypedObject(Debug.MemoryInfo.CREATOR);
                    boolean _arg218 = data.readBoolean();
                    boolean _arg310 = data.readBoolean();
                    boolean _arg47 = data.readBoolean();
                    boolean _arg56 = data.readBoolean();
                    String[] _arg66 = data.createStringArray();
                    data.enforceNoDataAvail();
                    dumpMemInfoProto(_arg027, _arg130, _arg218, _arg310, _arg47, _arg56, _arg66);
                    return true;
                case 35:
                    ParcelFileDescriptor _arg028 = (ParcelFileDescriptor) data.readTypedObject(ParcelFileDescriptor.CREATOR);
                    String[] _arg131 = data.createStringArray();
                    data.enforceNoDataAvail();
                    dumpGfxInfo(_arg028, _arg131);
                    return true;
                case 36:
                    ParcelFileDescriptor _arg029 = (ParcelFileDescriptor) data.readTypedObject(ParcelFileDescriptor.CREATOR);
                    String[] _arg133 = data.createStringArray();
                    data.enforceNoDataAvail();
                    dumpCacheInfo(_arg029, _arg133);
                    return true;
                case 37:
                    ParcelFileDescriptor _arg030 = (ParcelFileDescriptor) data.readTypedObject(ParcelFileDescriptor.CREATOR);
                    IBinder _arg134 = data.readStrongBinder();
                    String[] _arg219 = data.createStringArray();
                    data.enforceNoDataAvail();
                    dumpProvider(_arg030, _arg134, _arg219);
                    return true;
                case 38:
                    ParcelFileDescriptor _arg031 = (ParcelFileDescriptor) data.readTypedObject(ParcelFileDescriptor.CREATOR);
                    String[] _arg135 = data.createStringArray();
                    data.enforceNoDataAvail();
                    dumpDbInfo(_arg031, _arg135);
                    return true;
                case 39:
                    IBinder _arg032 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    unstableProviderDied(_arg032);
                    return true;
                case 40:
                    IBinder _arg033 = data.readStrongBinder();
                    IBinder _arg136 = data.readStrongBinder();
                    int _arg220 = data.readInt();
                    int _arg311 = data.readInt();
                    int _arg48 = data.readInt();
                    data.enforceNoDataAvail();
                    requestAssistContextExtras(_arg033, _arg136, _arg220, _arg311, _arg48);
                    return true;
                case 41:
                    IBinder _arg034 = data.readStrongBinder();
                    boolean _arg137 = data.readBoolean();
                    data.enforceNoDataAvail();
                    scheduleTranslucentConversionComplete(_arg034, _arg137);
                    return true;
                case 42:
                    int _arg035 = data.readInt();
                    data.enforceNoDataAvail();
                    setProcessState(_arg035);
                    return true;
                case 43:
                    ProviderInfo _arg036 = (ProviderInfo) data.readTypedObject(ProviderInfo.CREATOR);
                    data.enforceNoDataAvail();
                    scheduleInstallProvider(_arg036);
                    return true;
                case 44:
                    int _arg037 = data.readInt();
                    data.enforceNoDataAvail();
                    updateTimePrefs(_arg037);
                    return true;
                case 45:
                    IBinder _arg038 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    scheduleEnterAnimationComplete(_arg038);
                    return true;
                case 46:
                    byte[] _arg039 = data.createByteArray();
                    data.enforceNoDataAvail();
                    notifyCleartextNetwork(_arg039);
                    return true;
                case 47:
                    startBinderTracking();
                    return true;
                case 48:
                    ParcelFileDescriptor _arg040 = (ParcelFileDescriptor) data.readTypedObject(ParcelFileDescriptor.CREATOR);
                    data.enforceNoDataAvail();
                    stopBinderTrackingAndDump(_arg040);
                    return true;
                case 49:
                    ParcelFileDescriptor _arg041 = (ParcelFileDescriptor) data.readTypedObject(ParcelFileDescriptor.CREATOR);
                    String _arg138 = data.readString();
                    String _arg221 = data.readString();
                    int _arg312 = data.readInt();
                    int _arg49 = data.readInt();
                    data.enforceNoDataAvail();
                    stopBinderTrackingAndDumpSystemServer(_arg041, _arg138, _arg221, _arg312, _arg49);
                    return true;
                case 50:
                    IBinder _arg042 = data.readStrongBinder();
                    IVoiceInteractor _arg139 = IVoiceInteractor.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    scheduleLocalVoiceInteractionStarted(_arg042, _arg139);
                    return true;
                case 51:
                    handleTrustStorageUpdate();
                    return true;
                case 52:
                    String _arg043 = data.readString();
                    data.enforceNoDataAvail();
                    attachAgent(_arg043);
                    return true;
                case 53:
                    String _arg044 = data.readString();
                    data.enforceNoDataAvail();
                    attachStartupAgents(_arg044);
                    return true;
                case 54:
                    ApplicationInfo _arg045 = (ApplicationInfo) data.readTypedObject(ApplicationInfo.CREATOR);
                    data.enforceNoDataAvail();
                    scheduleApplicationInfoChanged(_arg045);
                    return true;
                case 55:
                    long _arg046 = data.readLong();
                    data.enforceNoDataAvail();
                    setNetworkBlockSeq(_arg046);
                    return true;
                case 56:
                    ClientTransaction _arg047 = (ClientTransaction) data.readTypedObject(ClientTransaction.CREATOR);
                    data.enforceNoDataAvail();
                    scheduleTransaction(_arg047);
                    return true;
                case 57:
                    IBinder _arg048 = data.readStrongBinder();
                    ITaskFragmentOrganizer _arg049 = ITaskFragmentOrganizer.Stub.asInterface(_arg048);
                    TaskFragmentTransaction _arg140 = (TaskFragmentTransaction) data.readTypedObject(TaskFragmentTransaction.CREATOR);
                    data.enforceNoDataAvail();
                    scheduleTaskFragmentTransaction(_arg049, _arg140);
                    return true;
                case 58:
                    IBinder _arg050 = data.readStrongBinder();
                    IVoiceInteractor _arg141 = IVoiceInteractor.Stub.asInterface(data.readStrongBinder());
                    RemoteCallback _arg223 = (RemoteCallback) data.readTypedObject(RemoteCallback.CREATOR);
                    RemoteCallback _arg313 = (RemoteCallback) data.readTypedObject(RemoteCallback.CREATOR);
                    data.enforceNoDataAvail();
                    requestDirectActions(_arg050, _arg141, _arg223, _arg313);
                    return true;
                case 59:
                    IBinder _arg051 = data.readStrongBinder();
                    String _arg142 = data.readString();
                    Bundle _arg224 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    RemoteCallback _arg314 = (RemoteCallback) data.readTypedObject(RemoteCallback.CREATOR);
                    RemoteCallback _arg410 = (RemoteCallback) data.readTypedObject(RemoteCallback.CREATOR);
                    data.enforceNoDataAvail();
                    performDirectAction(_arg051, _arg142, _arg224, _arg314, _arg410);
                    return true;
                case 60:
                    ContentProviderHolder _arg052 = (ContentProviderHolder) data.readTypedObject(ContentProviderHolder.CREATOR);
                    String _arg143 = data.readString();
                    int _arg225 = data.readInt();
                    boolean _arg315 = data.readBoolean();
                    data.enforceNoDataAvail();
                    notifyContentProviderPublishStatus(_arg052, _arg143, _arg225, _arg315);
                    return true;
                case 61:
                    ComponentName _arg053 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    Bundle _arg144 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    IInstrumentationWatcher _arg226 = IInstrumentationWatcher.Stub.asInterface(data.readStrongBinder());
                    IUiAutomationConnection _arg316 = IUiAutomationConnection.Stub.asInterface(data.readStrongBinder());
                    ApplicationInfo _arg411 = (ApplicationInfo) data.readTypedObject(ApplicationInfo.CREATOR);
                    data.enforceNoDataAvail();
                    instrumentWithoutRestart(_arg053, _arg144, _arg226, _arg316, _arg411);
                    return true;
                case 62:
                    IBinder _arg054 = data.readStrongBinder();
                    int _arg145 = data.readInt();
                    TranslationSpec _arg227 = (TranslationSpec) data.readTypedObject(TranslationSpec.CREATOR);
                    TranslationSpec _arg317 = (TranslationSpec) data.readTypedObject(TranslationSpec.CREATOR);
                    List<AutofillId> _arg412 = data.createTypedArrayList(AutofillId.CREATOR);
                    UiTranslationSpec _arg57 = (UiTranslationSpec) data.readTypedObject(UiTranslationSpec.CREATOR);
                    data.enforceNoDataAvail();
                    updateUiTranslationState(_arg054, _arg145, _arg227, _arg317, _arg412, _arg57);
                    return true;
                case 63:
                    IBinder _arg055 = data.readStrongBinder();
                    int _arg146 = data.readInt();
                    data.enforceNoDataAvail();
                    scheduleTimeoutService(_arg055, _arg146);
                    return true;
                case 64:
                    IBinder _arg056 = data.readStrongBinder();
                    int _arg147 = data.readInt();
                    int _arg228 = data.readInt();
                    data.enforceNoDataAvail();
                    scheduleTimeoutServiceForType(_arg056, _arg147, _arg228);
                    return true;
                case 65:
                    RemoteCallback _arg057 = (RemoteCallback) data.readTypedObject(RemoteCallback.CREATOR);
                    data.enforceNoDataAvail();
                    schedulePing(_arg057);
                    return true;
                case 66:
                    String _arg058 = data.readString();
                    data.enforceNoDataAvail();
                    getProfileLength(_arg058);
                    return true;
                case 67:
                    String _arg059 = data.readString();
                    data.enforceNoDataAvail();
                    setFlingerFlag(_arg059);
                    return true;
                case 68:
                    boolean _arg060 = data.readBoolean();
                    data.enforceNoDataAvail();
                    clearIdsTrainingData(_arg060);
                    return true;
                case 69:
                    IBinder _arg061 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    relaunchActivityIfWebViewAttached(_arg061);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IApplicationThread {
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

            @Override // android.app.IApplicationThread
            public void scheduleReceiver(Intent intent, ActivityInfo info, CompatibilityInfo compatInfo, int resultCode, String data, Bundle extras, boolean ordered, boolean assumeDelivered, int sendingUser, int processState, int sentFromUid, String sentFromPackage) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(intent, 0);
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    _data.writeTypedObject(info, 0);
                    try {
                        _data.writeTypedObject(compatInfo, 0);
                    } catch (Throwable th2) {
                        th = th2;
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(resultCode);
                    } catch (Throwable th3) {
                        th = th3;
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeString(data);
                    } catch (Throwable th4) {
                        th = th4;
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th5) {
                    th = th5;
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeTypedObject(extras, 0);
                    try {
                        _data.writeBoolean(ordered);
                    } catch (Throwable th6) {
                        th = th6;
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeBoolean(assumeDelivered);
                    } catch (Throwable th7) {
                        th = th7;
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(sendingUser);
                    } catch (Throwable th8) {
                        th = th8;
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th9) {
                    th = th9;
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeInt(processState);
                    try {
                        _data.writeInt(sentFromUid);
                        try {
                            _data.writeString(sentFromPackage);
                        } catch (Throwable th10) {
                            th = th10;
                        }
                    } catch (Throwable th11) {
                        th = th11;
                        _data.recycle();
                        throw th;
                    }
                    try {
                        this.mRemote.transact(1, _data, null, 1);
                        _data.recycle();
                    } catch (Throwable th12) {
                        th = th12;
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th13) {
                    th = th13;
                    _data.recycle();
                    throw th;
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleReceiverList(List<ReceiverInfo> info) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedList(info, 0);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleCreateService(IBinder token, ServiceInfo info, CompatibilityInfo compatInfo, int processState) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeTypedObject(info, 0);
                    _data.writeTypedObject(compatInfo, 0);
                    _data.writeInt(processState);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleStopService(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void bindApplication(String packageName, ApplicationInfo info, String sdkSandboxClientAppVolumeUuid, String sdkSandboxClientAppPackage, boolean isSdkInSandbox, ProviderInfoList providerList, ComponentName testName, ProfilerInfo profilerInfo, Bundle testArguments, IInstrumentationWatcher testWatcher, IUiAutomationConnection uiAutomationConnection, int debugMode, boolean enableBinderTracking, boolean trackAllocation, boolean restrictedBackupMode, boolean persistent, Configuration config, CompatibilityInfo compatInfo, Map services, Bundle coreSettings, String buildSerial, AutofillOptions autofillOptions, ContentCaptureOptions contentCaptureOptions, long[] disabledCompatChanges, long[] loggableCompatChanges, SharedMemory serializedSystemFontMap, long startRequestedElapsedTime, long startRequestedUptime, boolean fixedAppContextDisplay) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeTypedObject(info, 0);
                    _data.writeString(sdkSandboxClientAppVolumeUuid);
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    _data.writeString(sdkSandboxClientAppPackage);
                } catch (Throwable th2) {
                    th = th2;
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeBoolean(isSdkInSandbox);
                    try {
                        _data.writeTypedObject(providerList, 0);
                    } catch (Throwable th3) {
                        th = th3;
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeTypedObject(testName, 0);
                    } catch (Throwable th4) {
                        th = th4;
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeTypedObject(profilerInfo, 0);
                    } catch (Throwable th5) {
                        th = th5;
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeTypedObject(testArguments, 0);
                    } catch (Throwable th6) {
                        th = th6;
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeStrongInterface(testWatcher);
                    } catch (Throwable th7) {
                        th = th7;
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeStrongInterface(uiAutomationConnection);
                    } catch (Throwable th8) {
                        th = th8;
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(debugMode);
                    } catch (Throwable th9) {
                        th = th9;
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th10) {
                    th = th10;
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeBoolean(enableBinderTracking);
                    try {
                        _data.writeBoolean(trackAllocation);
                        _data.writeBoolean(restrictedBackupMode);
                        _data.writeBoolean(persistent);
                        _data.writeTypedObject(config, 0);
                        _data.writeTypedObject(compatInfo, 0);
                        _data.writeMap(services);
                        _data.writeTypedObject(coreSettings, 0);
                        _data.writeString(buildSerial);
                        _data.writeTypedObject(autofillOptions, 0);
                        _data.writeTypedObject(contentCaptureOptions, 0);
                        _data.writeLongArray(disabledCompatChanges);
                        _data.writeLongArray(loggableCompatChanges);
                        _data.writeTypedObject(serializedSystemFontMap, 0);
                        _data.writeLong(startRequestedElapsedTime);
                        _data.writeLong(startRequestedUptime);
                        _data.writeBoolean(fixedAppContextDisplay);
                        this.mRemote.transact(5, _data, null, 1);
                        _data.recycle();
                    } catch (Throwable th11) {
                        th = th11;
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th12) {
                    th = th12;
                    _data.recycle();
                    throw th;
                }
            }

            @Override // android.app.IApplicationThread
            public void runIsolatedEntryPoint(String entryPoint, String[] entryPointArgs) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(entryPoint);
                    _data.writeStringArray(entryPointArgs);
                    this.mRemote.transact(6, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleExit() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(7, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleServiceArgs(IBinder token, ParceledListSlice args) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeTypedObject(args, 0);
                    this.mRemote.transact(8, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void updateTimeZone() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(9, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void processInBackground() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(10, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleBindService(IBinder token, Intent intent, boolean rebind, int processState, long bindSeq) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeTypedObject(intent, 0);
                    _data.writeBoolean(rebind);
                    _data.writeInt(processState);
                    _data.writeLong(bindSeq);
                    this.mRemote.transact(11, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleUnbindService(IBinder token, Intent intent) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeTypedObject(intent, 0);
                    this.mRemote.transact(12, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void dumpService(ParcelFileDescriptor fd, IBinder servicetoken, String[] args) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(fd, 0);
                    _data.writeStrongBinder(servicetoken);
                    _data.writeStringArray(args);
                    this.mRemote.transact(13, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleRegisteredReceiver(IIntentReceiver receiver, Intent intent, int resultCode, String data, Bundle extras, boolean ordered, boolean sticky, boolean assumeDelivered, int sendingUser, int processState, int sentFromUid, String sentFromPackage) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(receiver);
                    _data.writeTypedObject(intent, 0);
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    _data.writeInt(resultCode);
                    try {
                        _data.writeString(data);
                    } catch (Throwable th2) {
                        th = th2;
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeTypedObject(extras, 0);
                    } catch (Throwable th3) {
                        th = th3;
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeBoolean(ordered);
                    } catch (Throwable th4) {
                        th = th4;
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th5) {
                    th = th5;
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeBoolean(sticky);
                    try {
                        _data.writeBoolean(assumeDelivered);
                    } catch (Throwable th6) {
                        th = th6;
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(sendingUser);
                    } catch (Throwable th7) {
                        th = th7;
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(processState);
                    } catch (Throwable th8) {
                        th = th8;
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th9) {
                    th = th9;
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeInt(sentFromUid);
                    try {
                        _data.writeString(sentFromPackage);
                    } catch (Throwable th10) {
                        th = th10;
                    }
                    try {
                        this.mRemote.transact(14, _data, null, 1);
                        _data.recycle();
                    } catch (Throwable th11) {
                        th = th11;
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th12) {
                    th = th12;
                    _data.recycle();
                    throw th;
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleLowMemory() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(15, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void profilerControl(boolean start, ProfilerInfo profilerInfo, int profileType) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(start);
                    _data.writeTypedObject(profilerInfo, 0);
                    _data.writeInt(profileType);
                    this.mRemote.transact(16, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void setSchedulingGroup(int group) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(group);
                    this.mRemote.transact(17, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleCreateBackupAgent(ApplicationInfo app, int backupMode, int userId, int operationType) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(app, 0);
                    _data.writeInt(backupMode);
                    _data.writeInt(userId);
                    _data.writeInt(operationType);
                    this.mRemote.transact(18, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleDestroyBackupAgent(ApplicationInfo app, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(app, 0);
                    _data.writeInt(userId);
                    this.mRemote.transact(19, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleOnNewSceneTransitionInfo(IBinder token, ActivityOptions.SceneTransitionInfo info) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeTypedObject(info, 0);
                    this.mRemote.transact(20, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleSuicide() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(21, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void dispatchPackageBroadcast(int cmd, String[] packages) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(cmd);
                    _data.writeStringArray(packages);
                    this.mRemote.transact(22, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleCrash(String msg, int typeId, Bundle extras) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(msg);
                    _data.writeInt(typeId);
                    _data.writeTypedObject(extras, 0);
                    this.mRemote.transact(23, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void dumpHeap(boolean managed, boolean mallocInfo, boolean runGc, String dumpBitmaps, String path, ParcelFileDescriptor fd, RemoteCallback finishCallback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(managed);
                    _data.writeBoolean(mallocInfo);
                    _data.writeBoolean(runGc);
                    _data.writeString(dumpBitmaps);
                    _data.writeString(path);
                    _data.writeTypedObject(fd, 0);
                    _data.writeTypedObject(finishCallback, 0);
                    this.mRemote.transact(24, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void dumpActivity(ParcelFileDescriptor fd, IBinder servicetoken, String prefix, String[] args) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(fd, 0);
                    _data.writeStrongBinder(servicetoken);
                    _data.writeString(prefix);
                    _data.writeStringArray(args);
                    this.mRemote.transact(25, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void dumpResources(ParcelFileDescriptor fd, RemoteCallback finishCallback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(fd, 0);
                    _data.writeTypedObject(finishCallback, 0);
                    this.mRemote.transact(26, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void clearDnsCache() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(27, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void updateHttpProxy() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(28, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void setHttpProxyInfo(ProxyInfoWrapper wrapper) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(wrapper, 0);
                    this.mRemote.transact(29, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void setCoreSettings(Bundle coreSettings) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(coreSettings, 0);
                    this.mRemote.transact(30, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void updatePackageCompatibilityInfo(String pkg, CompatibilityInfo info) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkg);
                    _data.writeTypedObject(info, 0);
                    this.mRemote.transact(31, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleTrimMemory(int level) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(level);
                    this.mRemote.transact(32, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void dumpMemInfo(ParcelFileDescriptor fd, Debug.MemoryInfo mem, boolean checkin, boolean dumpInfo, boolean dumpDalvik, boolean dumpSummaryOnly, boolean dumpUnreachable, boolean dumpAllocatorLogs, String[] args) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(fd, 0);
                    _data.writeTypedObject(mem, 0);
                    _data.writeBoolean(checkin);
                    _data.writeBoolean(dumpInfo);
                    _data.writeBoolean(dumpDalvik);
                    _data.writeBoolean(dumpSummaryOnly);
                    _data.writeBoolean(dumpUnreachable);
                    _data.writeBoolean(dumpAllocatorLogs);
                    _data.writeStringArray(args);
                    this.mRemote.transact(33, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void dumpMemInfoProto(ParcelFileDescriptor fd, Debug.MemoryInfo mem, boolean dumpInfo, boolean dumpDalvik, boolean dumpSummaryOnly, boolean dumpUnreachable, String[] args) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(fd, 0);
                    _data.writeTypedObject(mem, 0);
                    _data.writeBoolean(dumpInfo);
                    _data.writeBoolean(dumpDalvik);
                    _data.writeBoolean(dumpSummaryOnly);
                    _data.writeBoolean(dumpUnreachable);
                    _data.writeStringArray(args);
                    this.mRemote.transact(34, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void dumpGfxInfo(ParcelFileDescriptor fd, String[] args) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(fd, 0);
                    _data.writeStringArray(args);
                    this.mRemote.transact(35, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void dumpCacheInfo(ParcelFileDescriptor fd, String[] args) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(fd, 0);
                    _data.writeStringArray(args);
                    this.mRemote.transact(36, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void dumpProvider(ParcelFileDescriptor fd, IBinder servicetoken, String[] args) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(fd, 0);
                    _data.writeStrongBinder(servicetoken);
                    _data.writeStringArray(args);
                    this.mRemote.transact(37, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void dumpDbInfo(ParcelFileDescriptor fd, String[] args) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(fd, 0);
                    _data.writeStringArray(args);
                    this.mRemote.transact(38, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void unstableProviderDied(IBinder provider) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(provider);
                    this.mRemote.transact(39, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void requestAssistContextExtras(IBinder activityToken, IBinder requestToken, int requestType, int sessionId, int flags) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(activityToken);
                    _data.writeStrongBinder(requestToken);
                    _data.writeInt(requestType);
                    _data.writeInt(sessionId);
                    _data.writeInt(flags);
                    this.mRemote.transact(40, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleTranslucentConversionComplete(IBinder token, boolean timeout) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeBoolean(timeout);
                    this.mRemote.transact(41, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void setProcessState(int state) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(state);
                    this.mRemote.transact(42, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleInstallProvider(ProviderInfo provider) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(provider, 0);
                    this.mRemote.transact(43, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void updateTimePrefs(int timeFormatPreference) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(timeFormatPreference);
                    this.mRemote.transact(44, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleEnterAnimationComplete(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    this.mRemote.transact(45, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void notifyCleartextNetwork(byte[] firstPacket) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByteArray(firstPacket);
                    this.mRemote.transact(46, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void startBinderTracking() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(47, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void stopBinderTrackingAndDump(ParcelFileDescriptor fd) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(fd, 0);
                    this.mRemote.transact(48, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void stopBinderTrackingAndDumpSystemServer(ParcelFileDescriptor fd, String processName, String packageName, int pid, int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(fd, 0);
                    _data.writeString(processName);
                    _data.writeString(packageName);
                    _data.writeInt(pid);
                    _data.writeInt(uid);
                    this.mRemote.transact(49, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleLocalVoiceInteractionStarted(IBinder token, IVoiceInteractor voiceInteractor) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeStrongInterface(voiceInteractor);
                    this.mRemote.transact(50, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void handleTrustStorageUpdate() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(51, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void attachAgent(String path) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(path);
                    this.mRemote.transact(52, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void attachStartupAgents(String dataDir) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(dataDir);
                    this.mRemote.transact(53, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleApplicationInfoChanged(ApplicationInfo ai) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(ai, 0);
                    this.mRemote.transact(54, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void setNetworkBlockSeq(long procStateSeq) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(procStateSeq);
                    this.mRemote.transact(55, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleTransaction(ClientTransaction transaction) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(transaction, 0);
                    this.mRemote.transact(56, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleTaskFragmentTransaction(ITaskFragmentOrganizer organizer, TaskFragmentTransaction transaction) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(organizer);
                    _data.writeTypedObject(transaction, 0);
                    this.mRemote.transact(57, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void requestDirectActions(IBinder activityToken, IVoiceInteractor intractor, RemoteCallback cancellationCallback, RemoteCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(activityToken);
                    _data.writeStrongInterface(intractor);
                    _data.writeTypedObject(cancellationCallback, 0);
                    _data.writeTypedObject(callback, 0);
                    this.mRemote.transact(58, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void performDirectAction(IBinder activityToken, String actionId, Bundle arguments, RemoteCallback cancellationCallback, RemoteCallback resultCallback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(activityToken);
                    _data.writeString(actionId);
                    _data.writeTypedObject(arguments, 0);
                    _data.writeTypedObject(cancellationCallback, 0);
                    _data.writeTypedObject(resultCallback, 0);
                    this.mRemote.transact(59, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void notifyContentProviderPublishStatus(ContentProviderHolder holder, String authorities, int userId, boolean published) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(holder, 0);
                    _data.writeString(authorities);
                    _data.writeInt(userId);
                    _data.writeBoolean(published);
                    this.mRemote.transact(60, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void instrumentWithoutRestart(ComponentName instrumentationName, Bundle instrumentationArgs, IInstrumentationWatcher instrumentationWatcher, IUiAutomationConnection instrumentationUiConnection, ApplicationInfo targetInfo) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(instrumentationName, 0);
                    _data.writeTypedObject(instrumentationArgs, 0);
                    _data.writeStrongInterface(instrumentationWatcher);
                    _data.writeStrongInterface(instrumentationUiConnection);
                    _data.writeTypedObject(targetInfo, 0);
                    this.mRemote.transact(61, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void updateUiTranslationState(IBinder activityToken, int state, TranslationSpec sourceSpec, TranslationSpec targetSpec, List<AutofillId> viewIds, UiTranslationSpec uiTranslationSpec) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(activityToken);
                    _data.writeInt(state);
                    _data.writeTypedObject(sourceSpec, 0);
                    _data.writeTypedObject(targetSpec, 0);
                    _data.writeTypedList(viewIds, 0);
                    _data.writeTypedObject(uiTranslationSpec, 0);
                    this.mRemote.transact(62, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleTimeoutService(IBinder token, int startId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeInt(startId);
                    this.mRemote.transact(63, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void scheduleTimeoutServiceForType(IBinder token, int startId, int fgsType) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeInt(startId);
                    _data.writeInt(fgsType);
                    this.mRemote.transact(64, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void schedulePing(RemoteCallback pong) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(pong, 0);
                    this.mRemote.transact(65, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void getProfileLength(String pkgName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkgName);
                    this.mRemote.transact(66, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void setFlingerFlag(String pkgName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkgName);
                    this.mRemote.transact(67, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void clearIdsTrainingData(boolean flag) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(flag);
                    this.mRemote.transact(68, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IApplicationThread
            public void relaunchActivityIfWebViewAttached(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    this.mRemote.transact(69, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 68;
        }
    }
}
