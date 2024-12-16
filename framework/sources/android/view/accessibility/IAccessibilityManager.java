package android.view.accessibility;

import android.Manifest;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.accessibilityservice.IAccessibilityServiceClient;
import android.app.ActivityThread;
import android.app.RemoteAction;
import android.content.ComponentName;
import android.content.pm.ParceledListSlice;
import android.graphics.Rect;
import android.os.BadParcelableException;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PermissionEnforcer;
import android.os.RemoteException;
import android.view.IWindow;
import android.view.InputEvent;
import android.view.MagnificationSpec;
import android.view.SurfaceControl;
import android.view.accessibility.IAccessibilityInteractionConnection;
import android.view.accessibility.IAccessibilityManagerClient;
import android.view.accessibility.IMagnificationConnection;
import java.util.List;

/* loaded from: classes4.dex */
public interface IAccessibilityManager extends IInterface {
    boolean OnStartGestureWakeup() throws RemoteException;

    boolean OnStopGestureWakeup() throws RemoteException;

    int addAccessibilityInteractionConnection(IWindow iWindow, IBinder iBinder, IAccessibilityInteractionConnection iAccessibilityInteractionConnection, String str, int i) throws RemoteException;

    long addClient(IAccessibilityManagerClient iAccessibilityManagerClient, int i) throws RemoteException;

    void associateEmbeddedHierarchy(IBinder iBinder, IBinder iBinder2) throws RemoteException;

    void attachAccessibilityOverlayToDisplay(int i, SurfaceControl surfaceControl) throws RemoteException;

    int convertPixelToDpi(float f) throws RemoteException;

    void disassociateEmbeddedHierarchy(IBinder iBinder) throws RemoteException;

    void enableShortcutsForTargets(boolean z, int i, List<String> list, int i2) throws RemoteException;

    Bundle getA11yFeatureToTileMap(int i) throws RemoteException;

    List<String> getAccessibilityShortcutTargets(int i) throws RemoteException;

    int getAccessibilityWindowId(IBinder iBinder) throws RemoteException;

    List<AccessibilityServiceInfo> getEnabledAccessibilityServiceList(int i, int i2) throws RemoteException;

    int getFocusColor() throws RemoteException;

    int getFocusStrokeWidth() throws RemoteException;

    ParceledListSlice<AccessibilityServiceInfo> getInstalledAccessibilityServiceList(int i) throws RemoteException;

    long getRecommendedTimeoutMillis() throws RemoteException;

    String getScreenReaderName() throws RemoteException;

    IBinder getWindowToken(int i, int i2) throws RemoteException;

    WindowTransformationSpec getWindowTransformationSpec(int i) throws RemoteException;

    void injectInputEventToInputFilter(InputEvent inputEvent) throws RemoteException;

    void interrupt(int i) throws RemoteException;

    boolean isAccessibilityServiceWarningRequired(AccessibilityServiceInfo accessibilityServiceInfo) throws RemoteException;

    boolean isAccessibilityTargetAllowed(String str, int i, int i2) throws RemoteException;

    boolean isActivatedMagnification() throws RemoteException;

    boolean isAudioDescriptionByDefaultEnabled() throws RemoteException;

    boolean isCameraFlashNotificationRunning() throws RemoteException;

    boolean isScreenReaderEnabled() throws RemoteException;

    boolean isSystemAudioCaptioningUiEnabled(int i) throws RemoteException;

    boolean isTwoFingerGestureRecognitionEnabled() throws RemoteException;

    void notifyAccessibilityButtonClicked(int i, String str) throws RemoteException;

    void notifyAccessibilityButtonVisibilityChanged(boolean z) throws RemoteException;

    void notifyQuickSettingsTilesChanged(int i, List<ComponentName> list) throws RemoteException;

    void performAccessibilityDirectAccess(String str) throws RemoteException;

    void performAccessibilityShortcut(String str) throws RemoteException;

    boolean registerProxyForDisplay(IAccessibilityServiceClient iAccessibilityServiceClient, int i) throws RemoteException;

    void registerSystemAction(RemoteAction remoteAction, int i) throws RemoteException;

    void registerUiTestAutomationService(IBinder iBinder, IAccessibilityServiceClient iAccessibilityServiceClient, AccessibilityServiceInfo accessibilityServiceInfo, int i, int i2) throws RemoteException;

    void removeAccessibilityInteractionConnection(IWindow iWindow) throws RemoteException;

    boolean removeClient(IAccessibilityManagerClient iAccessibilityManagerClient, int i) throws RemoteException;

    boolean semCheckMdnieColorBlind(int[] iArr) throws RemoteException;

    boolean semDisableMdnieColorFilter() throws RemoteException;

    void semDisableWindowMagnification() throws RemoteException;

    void semDumpCallStack(String str) throws RemoteException;

    boolean semEnableMdnieColorFilter(int i, int i2) throws RemoteException;

    void semEnableWindowMagnification(int i, int i2) throws RemoteException;

    Rect semGetWindowMagnificationBounds() throws RemoteException;

    float semGetWindowMagnificationScale() throws RemoteException;

    void semInjectInputEventToInputFilter(InputEvent inputEvent, int i) throws RemoteException;

    boolean semIsAccessibilityButtonShown() throws RemoteException;

    boolean semIsAccessibilityServiceEnabled(int i) throws RemoteException;

    boolean semIsDarkScreenMode() throws RemoteException;

    boolean semIsWindowMagnificationEnabled() throws RemoteException;

    void semLockNow() throws RemoteException;

    void semMoveWindowMagnification(float f, float f2) throws RemoteException;

    void semOpenDeviceOptions() throws RemoteException;

    void semPerformAccessibilityButtonClick(int i, int i2, String str) throws RemoteException;

    void semRegisterAssistantMenu(IBinder iBinder) throws RemoteException;

    boolean semSetColorBlind(boolean z, float f) throws RemoteException;

    boolean semSetMdnieAccessibilityMode(int i, boolean z) throws RemoteException;

    void semSetTwoFingerGestureRecognitionEnabled(boolean z) throws RemoteException;

    void semToggleDarkScreenMode() throws RemoteException;

    void semTurnOffAccessibilityService(int i) throws RemoteException;

    void semTurnOnAccessibilityService(int i) throws RemoteException;

    void semUpdateAssitantMenu(Bundle bundle) throws RemoteException;

    void sendAccessibilityEvent(AccessibilityEvent accessibilityEvent, int i) throws RemoteException;

    boolean sendFingerprintGesture(int i) throws RemoteException;

    boolean sendRestrictedDialogIntent(String str, int i, int i2) throws RemoteException;

    void setAccessibilityWindowAttributes(int i, int i2, int i3, AccessibilityWindowAttributes accessibilityWindowAttributes) throws RemoteException;

    void setMagnificationConnection(IMagnificationConnection iMagnificationConnection) throws RemoteException;

    void setMagnificationDisactivate() throws RemoteException;

    void setPictureInPictureActionReplacingConnection(IAccessibilityInteractionConnection iAccessibilityInteractionConnection) throws RemoteException;

    void setScreenReaderEnabled(boolean z) throws RemoteException;

    void setSystemAudioCaptioningEnabled(boolean z, int i) throws RemoteException;

    void setSystemAudioCaptioningUiEnabled(boolean z, int i) throws RemoteException;

    void setTalkbackMode() throws RemoteException;

    boolean startFlashNotificationEvent(String str, int i, String str2) throws RemoteException;

    boolean startFlashNotificationSequence(String str, int i, IBinder iBinder) throws RemoteException;

    boolean stopFlashNotificationSequence(String str) throws RemoteException;

    boolean unregisterProxyForDisplay(int i) throws RemoteException;

    void unregisterSystemAction(int i) throws RemoteException;

    void unregisterUiTestAutomationService(IAccessibilityServiceClient iAccessibilityServiceClient) throws RemoteException;

    public static class Default implements IAccessibilityManager {
        @Override // android.view.accessibility.IAccessibilityManager
        public void interrupt(int userId) throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void sendAccessibilityEvent(AccessibilityEvent uiEvent, int userId) throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public long addClient(IAccessibilityManagerClient client, int userId) throws RemoteException {
            return 0L;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public boolean removeClient(IAccessibilityManagerClient client, int userId) throws RemoteException {
            return false;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public ParceledListSlice<AccessibilityServiceInfo> getInstalledAccessibilityServiceList(int userId) throws RemoteException {
            return null;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public List<AccessibilityServiceInfo> getEnabledAccessibilityServiceList(int feedbackType, int userId) throws RemoteException {
            return null;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public int addAccessibilityInteractionConnection(IWindow windowToken, IBinder leashToken, IAccessibilityInteractionConnection connection, String packageName, int userId) throws RemoteException {
            return 0;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void removeAccessibilityInteractionConnection(IWindow windowToken) throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void setPictureInPictureActionReplacingConnection(IAccessibilityInteractionConnection connection) throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void registerUiTestAutomationService(IBinder owner, IAccessibilityServiceClient client, AccessibilityServiceInfo info, int userId, int flags) throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void unregisterUiTestAutomationService(IAccessibilityServiceClient client) throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public IBinder getWindowToken(int windowId, int userId) throws RemoteException {
            return null;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void notifyAccessibilityButtonClicked(int displayId, String targetName) throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void notifyAccessibilityButtonVisibilityChanged(boolean available) throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void performAccessibilityShortcut(String targetName) throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public List<String> getAccessibilityShortcutTargets(int shortcutType) throws RemoteException {
            return null;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public boolean sendFingerprintGesture(int gestureKeyCode) throws RemoteException {
            return false;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public int getAccessibilityWindowId(IBinder windowToken) throws RemoteException {
            return 0;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public long getRecommendedTimeoutMillis() throws RemoteException {
            return 0L;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void registerSystemAction(RemoteAction action, int actionId) throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void unregisterSystemAction(int actionId) throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void setMagnificationConnection(IMagnificationConnection connection) throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void associateEmbeddedHierarchy(IBinder host, IBinder embedded) throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void disassociateEmbeddedHierarchy(IBinder token) throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public int getFocusStrokeWidth() throws RemoteException {
            return 0;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public int getFocusColor() throws RemoteException {
            return 0;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public boolean isAudioDescriptionByDefaultEnabled() throws RemoteException {
            return false;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void setSystemAudioCaptioningEnabled(boolean isEnabled, int userId) throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public boolean isSystemAudioCaptioningUiEnabled(int userId) throws RemoteException {
            return false;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void setSystemAudioCaptioningUiEnabled(boolean isEnabled, int userId) throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void setAccessibilityWindowAttributes(int displayId, int windowId, int userId, AccessibilityWindowAttributes attributes) throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public boolean registerProxyForDisplay(IAccessibilityServiceClient proxy, int displayId) throws RemoteException {
            return false;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public boolean unregisterProxyForDisplay(int displayId) throws RemoteException {
            return false;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void injectInputEventToInputFilter(InputEvent event) throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public boolean startFlashNotificationSequence(String opPkg, int reason, IBinder token) throws RemoteException {
            return false;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public boolean stopFlashNotificationSequence(String opPkg) throws RemoteException {
            return false;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public boolean startFlashNotificationEvent(String opPkg, int reason, String reasonPkg) throws RemoteException {
            return false;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public boolean isAccessibilityTargetAllowed(String packageName, int uid, int userId) throws RemoteException {
            return false;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public boolean sendRestrictedDialogIntent(String packageName, int uid, int userId) throws RemoteException {
            return false;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public boolean isAccessibilityServiceWarningRequired(AccessibilityServiceInfo info) throws RemoteException {
            return false;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public WindowTransformationSpec getWindowTransformationSpec(int windowId) throws RemoteException {
            return null;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void attachAccessibilityOverlayToDisplay(int displayId, SurfaceControl surfaceControl) throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void notifyQuickSettingsTilesChanged(int userId, List<ComponentName> tileComponentNames) throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void enableShortcutsForTargets(boolean enable, int shortcutTypes, List<String> shortcutTargets, int userId) throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public Bundle getA11yFeatureToTileMap(int userId) throws RemoteException {
            return null;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public boolean semIsAccessibilityServiceEnabled(int stateFlags) throws RemoteException {
            return false;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void semTurnOffAccessibilityService(int stateFlags) throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void semTurnOnAccessibilityService(int stateFlags) throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public boolean semSetColorBlind(boolean enable, float userParameter) throws RemoteException {
            return false;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public boolean semCheckMdnieColorBlind(int[] nums) throws RemoteException {
            return false;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public boolean semSetMdnieAccessibilityMode(int mode, boolean enable) throws RemoteException {
            return false;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public boolean semEnableMdnieColorFilter(int color, int opacity) throws RemoteException {
            return false;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public boolean semDisableMdnieColorFilter() throws RemoteException {
            return false;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public boolean semIsDarkScreenMode() throws RemoteException {
            return false;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void semToggleDarkScreenMode() throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void semUpdateAssitantMenu(Bundle bundle) throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void semRegisterAssistantMenu(IBinder iBinder) throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void semOpenDeviceOptions() throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void semSetTwoFingerGestureRecognitionEnabled(boolean enable) throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public boolean isTwoFingerGestureRecognitionEnabled() throws RemoteException {
            return false;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public boolean isScreenReaderEnabled() throws RemoteException {
            return false;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public String getScreenReaderName() throws RemoteException {
            return null;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void setScreenReaderEnabled(boolean enable) throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public int convertPixelToDpi(float pixels) throws RemoteException {
            return 0;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void setTalkbackMode() throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void semLockNow() throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public boolean OnStartGestureWakeup() throws RemoteException {
            return false;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public boolean OnStopGestureWakeup() throws RemoteException {
            return false;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public boolean semIsAccessibilityButtonShown() throws RemoteException {
            return false;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void setMagnificationDisactivate() throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void semDumpCallStack(String callStack) throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void performAccessibilityDirectAccess(String targetName) throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public Rect semGetWindowMagnificationBounds() throws RemoteException {
            return null;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public float semGetWindowMagnificationScale() throws RemoteException {
            return 0.0f;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void semEnableWindowMagnification(int center_x, int center_y) throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void semDisableWindowMagnification() throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void semMoveWindowMagnification(float offsetX, float offsetY) throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public boolean semIsWindowMagnificationEnabled() throws RemoteException {
            return false;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public boolean isActivatedMagnification() throws RemoteException {
            return false;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void semPerformAccessibilityButtonClick(int displayId, int shortcutType, String targetName) throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void semInjectInputEventToInputFilter(InputEvent event, int policyFlags) throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public boolean isCameraFlashNotificationRunning() throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IAccessibilityManager {
        public static final String DESCRIPTOR = "android.view.accessibility.IAccessibilityManager";
        static final String[] PERMISSIONS_notifyQuickSettingsTilesChanged = {Manifest.permission.STATUS_BAR_SERVICE, Manifest.permission.MANAGE_ACCESSIBILITY};
        static final int TRANSACTION_OnStartGestureWakeup = 67;
        static final int TRANSACTION_OnStopGestureWakeup = 68;
        static final int TRANSACTION_addAccessibilityInteractionConnection = 7;
        static final int TRANSACTION_addClient = 3;
        static final int TRANSACTION_associateEmbeddedHierarchy = 23;
        static final int TRANSACTION_attachAccessibilityOverlayToDisplay = 42;
        static final int TRANSACTION_convertPixelToDpi = 64;
        static final int TRANSACTION_disassociateEmbeddedHierarchy = 24;
        static final int TRANSACTION_enableShortcutsForTargets = 44;
        static final int TRANSACTION_getA11yFeatureToTileMap = 45;
        static final int TRANSACTION_getAccessibilityShortcutTargets = 16;
        static final int TRANSACTION_getAccessibilityWindowId = 18;
        static final int TRANSACTION_getEnabledAccessibilityServiceList = 6;
        static final int TRANSACTION_getFocusColor = 26;
        static final int TRANSACTION_getFocusStrokeWidth = 25;
        static final int TRANSACTION_getInstalledAccessibilityServiceList = 5;
        static final int TRANSACTION_getRecommendedTimeoutMillis = 19;
        static final int TRANSACTION_getScreenReaderName = 62;
        static final int TRANSACTION_getWindowToken = 12;
        static final int TRANSACTION_getWindowTransformationSpec = 41;
        static final int TRANSACTION_injectInputEventToInputFilter = 34;
        static final int TRANSACTION_interrupt = 1;
        static final int TRANSACTION_isAccessibilityServiceWarningRequired = 40;
        static final int TRANSACTION_isAccessibilityTargetAllowed = 38;
        static final int TRANSACTION_isActivatedMagnification = 79;
        static final int TRANSACTION_isAudioDescriptionByDefaultEnabled = 27;
        static final int TRANSACTION_isCameraFlashNotificationRunning = 82;
        static final int TRANSACTION_isScreenReaderEnabled = 61;
        static final int TRANSACTION_isSystemAudioCaptioningUiEnabled = 29;
        static final int TRANSACTION_isTwoFingerGestureRecognitionEnabled = 60;
        static final int TRANSACTION_notifyAccessibilityButtonClicked = 13;
        static final int TRANSACTION_notifyAccessibilityButtonVisibilityChanged = 14;
        static final int TRANSACTION_notifyQuickSettingsTilesChanged = 43;
        static final int TRANSACTION_performAccessibilityDirectAccess = 72;
        static final int TRANSACTION_performAccessibilityShortcut = 15;
        static final int TRANSACTION_registerProxyForDisplay = 32;
        static final int TRANSACTION_registerSystemAction = 20;
        static final int TRANSACTION_registerUiTestAutomationService = 10;
        static final int TRANSACTION_removeAccessibilityInteractionConnection = 8;
        static final int TRANSACTION_removeClient = 4;
        static final int TRANSACTION_semCheckMdnieColorBlind = 50;
        static final int TRANSACTION_semDisableMdnieColorFilter = 53;
        static final int TRANSACTION_semDisableWindowMagnification = 76;
        static final int TRANSACTION_semDumpCallStack = 71;
        static final int TRANSACTION_semEnableMdnieColorFilter = 52;
        static final int TRANSACTION_semEnableWindowMagnification = 75;
        static final int TRANSACTION_semGetWindowMagnificationBounds = 73;
        static final int TRANSACTION_semGetWindowMagnificationScale = 74;
        static final int TRANSACTION_semInjectInputEventToInputFilter = 81;
        static final int TRANSACTION_semIsAccessibilityButtonShown = 69;
        static final int TRANSACTION_semIsAccessibilityServiceEnabled = 46;
        static final int TRANSACTION_semIsDarkScreenMode = 54;
        static final int TRANSACTION_semIsWindowMagnificationEnabled = 78;
        static final int TRANSACTION_semLockNow = 66;
        static final int TRANSACTION_semMoveWindowMagnification = 77;
        static final int TRANSACTION_semOpenDeviceOptions = 58;
        static final int TRANSACTION_semPerformAccessibilityButtonClick = 80;
        static final int TRANSACTION_semRegisterAssistantMenu = 57;
        static final int TRANSACTION_semSetColorBlind = 49;
        static final int TRANSACTION_semSetMdnieAccessibilityMode = 51;
        static final int TRANSACTION_semSetTwoFingerGestureRecognitionEnabled = 59;
        static final int TRANSACTION_semToggleDarkScreenMode = 55;
        static final int TRANSACTION_semTurnOffAccessibilityService = 47;
        static final int TRANSACTION_semTurnOnAccessibilityService = 48;
        static final int TRANSACTION_semUpdateAssitantMenu = 56;
        static final int TRANSACTION_sendAccessibilityEvent = 2;
        static final int TRANSACTION_sendFingerprintGesture = 17;
        static final int TRANSACTION_sendRestrictedDialogIntent = 39;
        static final int TRANSACTION_setAccessibilityWindowAttributes = 31;
        static final int TRANSACTION_setMagnificationConnection = 22;
        static final int TRANSACTION_setMagnificationDisactivate = 70;
        static final int TRANSACTION_setPictureInPictureActionReplacingConnection = 9;
        static final int TRANSACTION_setScreenReaderEnabled = 63;
        static final int TRANSACTION_setSystemAudioCaptioningEnabled = 28;
        static final int TRANSACTION_setSystemAudioCaptioningUiEnabled = 30;
        static final int TRANSACTION_setTalkbackMode = 65;
        static final int TRANSACTION_startFlashNotificationEvent = 37;
        static final int TRANSACTION_startFlashNotificationSequence = 35;
        static final int TRANSACTION_stopFlashNotificationSequence = 36;
        static final int TRANSACTION_unregisterProxyForDisplay = 33;
        static final int TRANSACTION_unregisterSystemAction = 21;
        static final int TRANSACTION_unregisterUiTestAutomationService = 11;
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

        public static IAccessibilityManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IAccessibilityManager)) {
                return (IAccessibilityManager) iin;
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
                    return "interrupt";
                case 2:
                    return "sendAccessibilityEvent";
                case 3:
                    return "addClient";
                case 4:
                    return "removeClient";
                case 5:
                    return "getInstalledAccessibilityServiceList";
                case 6:
                    return "getEnabledAccessibilityServiceList";
                case 7:
                    return "addAccessibilityInteractionConnection";
                case 8:
                    return "removeAccessibilityInteractionConnection";
                case 9:
                    return "setPictureInPictureActionReplacingConnection";
                case 10:
                    return "registerUiTestAutomationService";
                case 11:
                    return "unregisterUiTestAutomationService";
                case 12:
                    return "getWindowToken";
                case 13:
                    return "notifyAccessibilityButtonClicked";
                case 14:
                    return "notifyAccessibilityButtonVisibilityChanged";
                case 15:
                    return "performAccessibilityShortcut";
                case 16:
                    return "getAccessibilityShortcutTargets";
                case 17:
                    return "sendFingerprintGesture";
                case 18:
                    return "getAccessibilityWindowId";
                case 19:
                    return "getRecommendedTimeoutMillis";
                case 20:
                    return "registerSystemAction";
                case 21:
                    return "unregisterSystemAction";
                case 22:
                    return "setMagnificationConnection";
                case 23:
                    return "associateEmbeddedHierarchy";
                case 24:
                    return "disassociateEmbeddedHierarchy";
                case 25:
                    return "getFocusStrokeWidth";
                case 26:
                    return "getFocusColor";
                case 27:
                    return "isAudioDescriptionByDefaultEnabled";
                case 28:
                    return "setSystemAudioCaptioningEnabled";
                case 29:
                    return "isSystemAudioCaptioningUiEnabled";
                case 30:
                    return "setSystemAudioCaptioningUiEnabled";
                case 31:
                    return "setAccessibilityWindowAttributes";
                case 32:
                    return "registerProxyForDisplay";
                case 33:
                    return "unregisterProxyForDisplay";
                case 34:
                    return "injectInputEventToInputFilter";
                case 35:
                    return "startFlashNotificationSequence";
                case 36:
                    return "stopFlashNotificationSequence";
                case 37:
                    return "startFlashNotificationEvent";
                case 38:
                    return "isAccessibilityTargetAllowed";
                case 39:
                    return "sendRestrictedDialogIntent";
                case 40:
                    return "isAccessibilityServiceWarningRequired";
                case 41:
                    return "getWindowTransformationSpec";
                case 42:
                    return "attachAccessibilityOverlayToDisplay";
                case 43:
                    return "notifyQuickSettingsTilesChanged";
                case 44:
                    return "enableShortcutsForTargets";
                case 45:
                    return "getA11yFeatureToTileMap";
                case 46:
                    return "semIsAccessibilityServiceEnabled";
                case 47:
                    return "semTurnOffAccessibilityService";
                case 48:
                    return "semTurnOnAccessibilityService";
                case 49:
                    return "semSetColorBlind";
                case 50:
                    return "semCheckMdnieColorBlind";
                case 51:
                    return "semSetMdnieAccessibilityMode";
                case 52:
                    return "semEnableMdnieColorFilter";
                case 53:
                    return "semDisableMdnieColorFilter";
                case 54:
                    return "semIsDarkScreenMode";
                case 55:
                    return "semToggleDarkScreenMode";
                case 56:
                    return "semUpdateAssitantMenu";
                case 57:
                    return "semRegisterAssistantMenu";
                case 58:
                    return "semOpenDeviceOptions";
                case 59:
                    return "semSetTwoFingerGestureRecognitionEnabled";
                case 60:
                    return "isTwoFingerGestureRecognitionEnabled";
                case 61:
                    return "isScreenReaderEnabled";
                case 62:
                    return "getScreenReaderName";
                case 63:
                    return "setScreenReaderEnabled";
                case 64:
                    return "convertPixelToDpi";
                case 65:
                    return "setTalkbackMode";
                case 66:
                    return "semLockNow";
                case 67:
                    return "OnStartGestureWakeup";
                case 68:
                    return "OnStopGestureWakeup";
                case 69:
                    return "semIsAccessibilityButtonShown";
                case 70:
                    return "setMagnificationDisactivate";
                case 71:
                    return "semDumpCallStack";
                case 72:
                    return "performAccessibilityDirectAccess";
                case 73:
                    return "semGetWindowMagnificationBounds";
                case 74:
                    return "semGetWindowMagnificationScale";
                case 75:
                    return "semEnableWindowMagnification";
                case 76:
                    return "semDisableWindowMagnification";
                case 77:
                    return "semMoveWindowMagnification";
                case 78:
                    return "semIsWindowMagnificationEnabled";
                case 79:
                    return "isActivatedMagnification";
                case 80:
                    return "semPerformAccessibilityButtonClick";
                case 81:
                    return "semInjectInputEventToInputFilter";
                case 82:
                    return "isCameraFlashNotificationRunning";
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
                    data.enforceNoDataAvail();
                    interrupt(_arg0);
                    return true;
                case 2:
                    AccessibilityEvent _arg02 = (AccessibilityEvent) data.readTypedObject(AccessibilityEvent.CREATOR);
                    int _arg1 = data.readInt();
                    data.enforceNoDataAvail();
                    sendAccessibilityEvent(_arg02, _arg1);
                    return true;
                case 3:
                    IAccessibilityManagerClient _arg03 = IAccessibilityManagerClient.Stub.asInterface(data.readStrongBinder());
                    int _arg12 = data.readInt();
                    data.enforceNoDataAvail();
                    long _result = addClient(_arg03, _arg12);
                    reply.writeNoException();
                    reply.writeLong(_result);
                    return true;
                case 4:
                    IAccessibilityManagerClient _arg04 = IAccessibilityManagerClient.Stub.asInterface(data.readStrongBinder());
                    int _arg13 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result2 = removeClient(_arg04, _arg13);
                    reply.writeNoException();
                    reply.writeBoolean(_result2);
                    return true;
                case 5:
                    int _arg05 = data.readInt();
                    data.enforceNoDataAvail();
                    ParceledListSlice<AccessibilityServiceInfo> _result3 = getInstalledAccessibilityServiceList(_arg05);
                    reply.writeNoException();
                    reply.writeTypedObject(_result3, 1);
                    return true;
                case 6:
                    int _arg06 = data.readInt();
                    int _arg14 = data.readInt();
                    data.enforceNoDataAvail();
                    List<AccessibilityServiceInfo> _result4 = getEnabledAccessibilityServiceList(_arg06, _arg14);
                    reply.writeNoException();
                    reply.writeTypedList(_result4, 1);
                    return true;
                case 7:
                    IWindow _arg07 = IWindow.Stub.asInterface(data.readStrongBinder());
                    IBinder _arg15 = data.readStrongBinder();
                    IAccessibilityInteractionConnection _arg2 = IAccessibilityInteractionConnection.Stub.asInterface(data.readStrongBinder());
                    String _arg3 = data.readString();
                    int _arg4 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result5 = addAccessibilityInteractionConnection(_arg07, _arg15, _arg2, _arg3, _arg4);
                    reply.writeNoException();
                    reply.writeInt(_result5);
                    return true;
                case 8:
                    IWindow _arg08 = IWindow.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    removeAccessibilityInteractionConnection(_arg08);
                    reply.writeNoException();
                    return true;
                case 9:
                    IAccessibilityInteractionConnection _arg09 = IAccessibilityInteractionConnection.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    setPictureInPictureActionReplacingConnection(_arg09);
                    reply.writeNoException();
                    return true;
                case 10:
                    IBinder _arg010 = data.readStrongBinder();
                    IAccessibilityServiceClient _arg16 = IAccessibilityServiceClient.Stub.asInterface(data.readStrongBinder());
                    AccessibilityServiceInfo _arg22 = (AccessibilityServiceInfo) data.readTypedObject(AccessibilityServiceInfo.CREATOR);
                    int _arg32 = data.readInt();
                    int _arg42 = data.readInt();
                    data.enforceNoDataAvail();
                    registerUiTestAutomationService(_arg010, _arg16, _arg22, _arg32, _arg42);
                    reply.writeNoException();
                    return true;
                case 11:
                    IAccessibilityServiceClient _arg011 = IAccessibilityServiceClient.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    unregisterUiTestAutomationService(_arg011);
                    reply.writeNoException();
                    return true;
                case 12:
                    int _arg012 = data.readInt();
                    int _arg17 = data.readInt();
                    data.enforceNoDataAvail();
                    IBinder _result6 = getWindowToken(_arg012, _arg17);
                    reply.writeNoException();
                    reply.writeStrongBinder(_result6);
                    return true;
                case 13:
                    int _arg013 = data.readInt();
                    String _arg18 = data.readString();
                    data.enforceNoDataAvail();
                    notifyAccessibilityButtonClicked(_arg013, _arg18);
                    reply.writeNoException();
                    return true;
                case 14:
                    boolean _arg014 = data.readBoolean();
                    data.enforceNoDataAvail();
                    notifyAccessibilityButtonVisibilityChanged(_arg014);
                    reply.writeNoException();
                    return true;
                case 15:
                    String _arg015 = data.readString();
                    data.enforceNoDataAvail();
                    performAccessibilityShortcut(_arg015);
                    reply.writeNoException();
                    return true;
                case 16:
                    int _arg016 = data.readInt();
                    data.enforceNoDataAvail();
                    List<String> _result7 = getAccessibilityShortcutTargets(_arg016);
                    reply.writeNoException();
                    reply.writeStringList(_result7);
                    return true;
                case 17:
                    int _arg017 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result8 = sendFingerprintGesture(_arg017);
                    reply.writeNoException();
                    reply.writeBoolean(_result8);
                    return true;
                case 18:
                    IBinder _arg018 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    int _result9 = getAccessibilityWindowId(_arg018);
                    reply.writeNoException();
                    reply.writeInt(_result9);
                    return true;
                case 19:
                    long _result10 = getRecommendedTimeoutMillis();
                    reply.writeNoException();
                    reply.writeLong(_result10);
                    return true;
                case 20:
                    RemoteAction _arg019 = (RemoteAction) data.readTypedObject(RemoteAction.CREATOR);
                    int _arg19 = data.readInt();
                    data.enforceNoDataAvail();
                    registerSystemAction(_arg019, _arg19);
                    return true;
                case 21:
                    int _arg020 = data.readInt();
                    data.enforceNoDataAvail();
                    unregisterSystemAction(_arg020);
                    return true;
                case 22:
                    IBinder _arg021 = data.readStrongBinder();
                    IMagnificationConnection _arg022 = IMagnificationConnection.Stub.asInterface(_arg021);
                    data.enforceNoDataAvail();
                    setMagnificationConnection(_arg022);
                    return true;
                case 23:
                    IBinder _arg023 = data.readStrongBinder();
                    IBinder _arg110 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    associateEmbeddedHierarchy(_arg023, _arg110);
                    reply.writeNoException();
                    return true;
                case 24:
                    IBinder _arg024 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    disassociateEmbeddedHierarchy(_arg024);
                    reply.writeNoException();
                    return true;
                case 25:
                    int _result11 = getFocusStrokeWidth();
                    reply.writeNoException();
                    reply.writeInt(_result11);
                    return true;
                case 26:
                    int _result12 = getFocusColor();
                    reply.writeNoException();
                    reply.writeInt(_result12);
                    return true;
                case 27:
                    boolean _result13 = isAudioDescriptionByDefaultEnabled();
                    reply.writeNoException();
                    reply.writeBoolean(_result13);
                    return true;
                case 28:
                    boolean _arg025 = data.readBoolean();
                    int _arg111 = data.readInt();
                    data.enforceNoDataAvail();
                    setSystemAudioCaptioningEnabled(_arg025, _arg111);
                    reply.writeNoException();
                    return true;
                case 29:
                    int _arg026 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result14 = isSystemAudioCaptioningUiEnabled(_arg026);
                    reply.writeNoException();
                    reply.writeBoolean(_result14);
                    return true;
                case 30:
                    boolean _arg027 = data.readBoolean();
                    int _arg112 = data.readInt();
                    data.enforceNoDataAvail();
                    setSystemAudioCaptioningUiEnabled(_arg027, _arg112);
                    reply.writeNoException();
                    return true;
                case 31:
                    int _arg028 = data.readInt();
                    int _arg113 = data.readInt();
                    int _arg23 = data.readInt();
                    AccessibilityWindowAttributes _arg33 = (AccessibilityWindowAttributes) data.readTypedObject(AccessibilityWindowAttributes.CREATOR);
                    data.enforceNoDataAvail();
                    setAccessibilityWindowAttributes(_arg028, _arg113, _arg23, _arg33);
                    return true;
                case 32:
                    IAccessibilityServiceClient _arg029 = IAccessibilityServiceClient.Stub.asInterface(data.readStrongBinder());
                    int _arg114 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result15 = registerProxyForDisplay(_arg029, _arg114);
                    reply.writeNoException();
                    reply.writeBoolean(_result15);
                    return true;
                case 33:
                    int _arg030 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result16 = unregisterProxyForDisplay(_arg030);
                    reply.writeNoException();
                    reply.writeBoolean(_result16);
                    return true;
                case 34:
                    InputEvent _arg031 = (InputEvent) data.readTypedObject(InputEvent.CREATOR);
                    data.enforceNoDataAvail();
                    injectInputEventToInputFilter(_arg031);
                    reply.writeNoException();
                    return true;
                case 35:
                    String _arg032 = data.readString();
                    int _arg115 = data.readInt();
                    IBinder _arg24 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    boolean _result17 = startFlashNotificationSequence(_arg032, _arg115, _arg24);
                    reply.writeNoException();
                    reply.writeBoolean(_result17);
                    return true;
                case 36:
                    String _arg033 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result18 = stopFlashNotificationSequence(_arg033);
                    reply.writeNoException();
                    reply.writeBoolean(_result18);
                    return true;
                case 37:
                    String _arg034 = data.readString();
                    int _arg116 = data.readInt();
                    String _arg25 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result19 = startFlashNotificationEvent(_arg034, _arg116, _arg25);
                    reply.writeNoException();
                    reply.writeBoolean(_result19);
                    return true;
                case 38:
                    String _arg035 = data.readString();
                    int _arg117 = data.readInt();
                    int _arg26 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result20 = isAccessibilityTargetAllowed(_arg035, _arg117, _arg26);
                    reply.writeNoException();
                    reply.writeBoolean(_result20);
                    return true;
                case 39:
                    String _arg036 = data.readString();
                    int _arg118 = data.readInt();
                    int _arg27 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result21 = sendRestrictedDialogIntent(_arg036, _arg118, _arg27);
                    reply.writeNoException();
                    reply.writeBoolean(_result21);
                    return true;
                case 40:
                    AccessibilityServiceInfo _arg037 = (AccessibilityServiceInfo) data.readTypedObject(AccessibilityServiceInfo.CREATOR);
                    data.enforceNoDataAvail();
                    boolean _result22 = isAccessibilityServiceWarningRequired(_arg037);
                    reply.writeNoException();
                    reply.writeBoolean(_result22);
                    return true;
                case 41:
                    int _arg038 = data.readInt();
                    data.enforceNoDataAvail();
                    WindowTransformationSpec _result23 = getWindowTransformationSpec(_arg038);
                    reply.writeNoException();
                    reply.writeTypedObject(_result23, 1);
                    return true;
                case 42:
                    int _arg039 = data.readInt();
                    SurfaceControl _arg119 = (SurfaceControl) data.readTypedObject(SurfaceControl.CREATOR);
                    data.enforceNoDataAvail();
                    attachAccessibilityOverlayToDisplay(_arg039, _arg119);
                    reply.writeNoException();
                    return true;
                case 43:
                    int _arg040 = data.readInt();
                    List<ComponentName> _arg120 = data.createTypedArrayList(ComponentName.CREATOR);
                    data.enforceNoDataAvail();
                    notifyQuickSettingsTilesChanged(_arg040, _arg120);
                    return true;
                case 44:
                    boolean _arg041 = data.readBoolean();
                    int _arg121 = data.readInt();
                    List<String> _arg28 = data.createStringArrayList();
                    int _arg34 = data.readInt();
                    data.enforceNoDataAvail();
                    enableShortcutsForTargets(_arg041, _arg121, _arg28, _arg34);
                    return true;
                case 45:
                    int _arg042 = data.readInt();
                    data.enforceNoDataAvail();
                    Bundle _result24 = getA11yFeatureToTileMap(_arg042);
                    reply.writeNoException();
                    reply.writeTypedObject(_result24, 1);
                    return true;
                case 46:
                    int _arg043 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result25 = semIsAccessibilityServiceEnabled(_arg043);
                    reply.writeNoException();
                    reply.writeBoolean(_result25);
                    return true;
                case 47:
                    int _arg044 = data.readInt();
                    data.enforceNoDataAvail();
                    semTurnOffAccessibilityService(_arg044);
                    reply.writeNoException();
                    return true;
                case 48:
                    int _arg045 = data.readInt();
                    data.enforceNoDataAvail();
                    semTurnOnAccessibilityService(_arg045);
                    reply.writeNoException();
                    return true;
                case 49:
                    boolean _arg046 = data.readBoolean();
                    float _arg122 = data.readFloat();
                    data.enforceNoDataAvail();
                    boolean _result26 = semSetColorBlind(_arg046, _arg122);
                    reply.writeNoException();
                    reply.writeBoolean(_result26);
                    return true;
                case 50:
                    int[] _arg047 = data.createIntArray();
                    data.enforceNoDataAvail();
                    boolean _result27 = semCheckMdnieColorBlind(_arg047);
                    reply.writeNoException();
                    reply.writeBoolean(_result27);
                    return true;
                case 51:
                    int _arg048 = data.readInt();
                    boolean _arg123 = data.readBoolean();
                    data.enforceNoDataAvail();
                    boolean _result28 = semSetMdnieAccessibilityMode(_arg048, _arg123);
                    reply.writeNoException();
                    reply.writeBoolean(_result28);
                    return true;
                case 52:
                    int _arg049 = data.readInt();
                    int _arg124 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result29 = semEnableMdnieColorFilter(_arg049, _arg124);
                    reply.writeNoException();
                    reply.writeBoolean(_result29);
                    return true;
                case 53:
                    boolean _result30 = semDisableMdnieColorFilter();
                    reply.writeNoException();
                    reply.writeBoolean(_result30);
                    return true;
                case 54:
                    boolean _result31 = semIsDarkScreenMode();
                    reply.writeNoException();
                    reply.writeBoolean(_result31);
                    return true;
                case 55:
                    semToggleDarkScreenMode();
                    reply.writeNoException();
                    return true;
                case 56:
                    Bundle _arg050 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    data.enforceNoDataAvail();
                    semUpdateAssitantMenu(_arg050);
                    reply.writeNoException();
                    return true;
                case 57:
                    IBinder _arg051 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    semRegisterAssistantMenu(_arg051);
                    reply.writeNoException();
                    return true;
                case 58:
                    semOpenDeviceOptions();
                    reply.writeNoException();
                    return true;
                case 59:
                    boolean _arg052 = data.readBoolean();
                    data.enforceNoDataAvail();
                    semSetTwoFingerGestureRecognitionEnabled(_arg052);
                    reply.writeNoException();
                    return true;
                case 60:
                    boolean _result32 = isTwoFingerGestureRecognitionEnabled();
                    reply.writeNoException();
                    reply.writeBoolean(_result32);
                    return true;
                case 61:
                    boolean _result33 = isScreenReaderEnabled();
                    reply.writeNoException();
                    reply.writeBoolean(_result33);
                    return true;
                case 62:
                    String _result34 = getScreenReaderName();
                    reply.writeNoException();
                    reply.writeString(_result34);
                    return true;
                case 63:
                    boolean _arg053 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setScreenReaderEnabled(_arg053);
                    reply.writeNoException();
                    return true;
                case 64:
                    float _arg054 = data.readFloat();
                    data.enforceNoDataAvail();
                    int _result35 = convertPixelToDpi(_arg054);
                    reply.writeNoException();
                    reply.writeInt(_result35);
                    return true;
                case 65:
                    setTalkbackMode();
                    reply.writeNoException();
                    return true;
                case 66:
                    semLockNow();
                    reply.writeNoException();
                    return true;
                case 67:
                    boolean _result36 = OnStartGestureWakeup();
                    reply.writeNoException();
                    reply.writeBoolean(_result36);
                    return true;
                case 68:
                    boolean _result37 = OnStopGestureWakeup();
                    reply.writeNoException();
                    reply.writeBoolean(_result37);
                    return true;
                case 69:
                    boolean _result38 = semIsAccessibilityButtonShown();
                    reply.writeNoException();
                    reply.writeBoolean(_result38);
                    return true;
                case 70:
                    setMagnificationDisactivate();
                    reply.writeNoException();
                    return true;
                case 71:
                    String _arg055 = data.readString();
                    data.enforceNoDataAvail();
                    semDumpCallStack(_arg055);
                    reply.writeNoException();
                    return true;
                case 72:
                    String _arg056 = data.readString();
                    data.enforceNoDataAvail();
                    performAccessibilityDirectAccess(_arg056);
                    reply.writeNoException();
                    return true;
                case 73:
                    Rect _result39 = semGetWindowMagnificationBounds();
                    reply.writeNoException();
                    reply.writeTypedObject(_result39, 1);
                    return true;
                case 74:
                    float _result40 = semGetWindowMagnificationScale();
                    reply.writeNoException();
                    reply.writeFloat(_result40);
                    return true;
                case 75:
                    int _arg057 = data.readInt();
                    int _arg125 = data.readInt();
                    data.enforceNoDataAvail();
                    semEnableWindowMagnification(_arg057, _arg125);
                    reply.writeNoException();
                    return true;
                case 76:
                    semDisableWindowMagnification();
                    reply.writeNoException();
                    return true;
                case 77:
                    float _arg058 = data.readFloat();
                    float _arg126 = data.readFloat();
                    data.enforceNoDataAvail();
                    semMoveWindowMagnification(_arg058, _arg126);
                    reply.writeNoException();
                    return true;
                case 78:
                    boolean _result41 = semIsWindowMagnificationEnabled();
                    reply.writeNoException();
                    reply.writeBoolean(_result41);
                    return true;
                case 79:
                    boolean _result42 = isActivatedMagnification();
                    reply.writeNoException();
                    reply.writeBoolean(_result42);
                    return true;
                case 80:
                    int _arg059 = data.readInt();
                    int _arg127 = data.readInt();
                    String _arg29 = data.readString();
                    data.enforceNoDataAvail();
                    semPerformAccessibilityButtonClick(_arg059, _arg127, _arg29);
                    reply.writeNoException();
                    return true;
                case 81:
                    InputEvent _arg060 = (InputEvent) data.readTypedObject(InputEvent.CREATOR);
                    int _arg128 = data.readInt();
                    data.enforceNoDataAvail();
                    semInjectInputEventToInputFilter(_arg060, _arg128);
                    reply.writeNoException();
                    return true;
                case 82:
                    boolean _result43 = isCameraFlashNotificationRunning();
                    reply.writeNoException();
                    reply.writeBoolean(_result43);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IAccessibilityManager {
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

            @Override // android.view.accessibility.IAccessibilityManager
            public void interrupt(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void sendAccessibilityEvent(AccessibilityEvent uiEvent, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(uiEvent, 0);
                    _data.writeInt(userId);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public long addClient(IAccessibilityManagerClient client, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(client);
                    _data.writeInt(userId);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean removeClient(IAccessibilityManagerClient client, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(client);
                    _data.writeInt(userId);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public ParceledListSlice<AccessibilityServiceInfo> getInstalledAccessibilityServiceList(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    ParceledListSlice<AccessibilityServiceInfo> _result = (ParceledListSlice) _reply.readTypedObject(ParceledListSlice.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public List<AccessibilityServiceInfo> getEnabledAccessibilityServiceList(int feedbackType, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(feedbackType);
                    _data.writeInt(userId);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    List<AccessibilityServiceInfo> _result = _reply.createTypedArrayList(AccessibilityServiceInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public int addAccessibilityInteractionConnection(IWindow windowToken, IBinder leashToken, IAccessibilityInteractionConnection connection, String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(windowToken);
                    _data.writeStrongBinder(leashToken);
                    _data.writeStrongInterface(connection);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void removeAccessibilityInteractionConnection(IWindow windowToken) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(windowToken);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void setPictureInPictureActionReplacingConnection(IAccessibilityInteractionConnection connection) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(connection);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void registerUiTestAutomationService(IBinder owner, IAccessibilityServiceClient client, AccessibilityServiceInfo info, int userId, int flags) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(owner);
                    _data.writeStrongInterface(client);
                    _data.writeTypedObject(info, 0);
                    _data.writeInt(userId);
                    _data.writeInt(flags);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void unregisterUiTestAutomationService(IAccessibilityServiceClient client) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(client);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public IBinder getWindowToken(int windowId, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(windowId);
                    _data.writeInt(userId);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                    IBinder _result = _reply.readStrongBinder();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void notifyAccessibilityButtonClicked(int displayId, String targetName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeString(targetName);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void notifyAccessibilityButtonVisibilityChanged(boolean available) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(available);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void performAccessibilityShortcut(String targetName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(targetName);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public List<String> getAccessibilityShortcutTargets(int shortcutType) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(shortcutType);
                    this.mRemote.transact(16, _data, _reply, 0);
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean sendFingerprintGesture(int gestureKeyCode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(gestureKeyCode);
                    this.mRemote.transact(17, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public int getAccessibilityWindowId(IBinder windowToken) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(windowToken);
                    this.mRemote.transact(18, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public long getRecommendedTimeoutMillis() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(19, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void registerSystemAction(RemoteAction action, int actionId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(action, 0);
                    _data.writeInt(actionId);
                    this.mRemote.transact(20, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void unregisterSystemAction(int actionId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(actionId);
                    this.mRemote.transact(21, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void setMagnificationConnection(IMagnificationConnection connection) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(connection);
                    this.mRemote.transact(22, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void associateEmbeddedHierarchy(IBinder host, IBinder embedded) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(host);
                    _data.writeStrongBinder(embedded);
                    this.mRemote.transact(23, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void disassociateEmbeddedHierarchy(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    this.mRemote.transact(24, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public int getFocusStrokeWidth() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(25, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public int getFocusColor() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(26, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean isAudioDescriptionByDefaultEnabled() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(27, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void setSystemAudioCaptioningEnabled(boolean isEnabled, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(isEnabled);
                    _data.writeInt(userId);
                    this.mRemote.transact(28, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean isSystemAudioCaptioningUiEnabled(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(29, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void setSystemAudioCaptioningUiEnabled(boolean isEnabled, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(isEnabled);
                    _data.writeInt(userId);
                    this.mRemote.transact(30, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void setAccessibilityWindowAttributes(int displayId, int windowId, int userId, AccessibilityWindowAttributes attributes) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeInt(windowId);
                    _data.writeInt(userId);
                    _data.writeTypedObject(attributes, 0);
                    this.mRemote.transact(31, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean registerProxyForDisplay(IAccessibilityServiceClient proxy, int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(proxy);
                    _data.writeInt(displayId);
                    this.mRemote.transact(32, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean unregisterProxyForDisplay(int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    this.mRemote.transact(33, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void injectInputEventToInputFilter(InputEvent event) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(event, 0);
                    this.mRemote.transact(34, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean startFlashNotificationSequence(String opPkg, int reason, IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(opPkg);
                    _data.writeInt(reason);
                    _data.writeStrongBinder(token);
                    this.mRemote.transact(35, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean stopFlashNotificationSequence(String opPkg) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(opPkg);
                    this.mRemote.transact(36, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean startFlashNotificationEvent(String opPkg, int reason, String reasonPkg) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(opPkg);
                    _data.writeInt(reason);
                    _data.writeString(reasonPkg);
                    this.mRemote.transact(37, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean isAccessibilityTargetAllowed(String packageName, int uid, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(uid);
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

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean sendRestrictedDialogIntent(String packageName, int uid, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(uid);
                    _data.writeInt(userId);
                    this.mRemote.transact(39, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean isAccessibilityServiceWarningRequired(AccessibilityServiceInfo info) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
                    this.mRemote.transact(40, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public WindowTransformationSpec getWindowTransformationSpec(int windowId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(windowId);
                    this.mRemote.transact(41, _data, _reply, 0);
                    _reply.readException();
                    WindowTransformationSpec _result = (WindowTransformationSpec) _reply.readTypedObject(WindowTransformationSpec.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void attachAccessibilityOverlayToDisplay(int displayId, SurfaceControl surfaceControl) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeTypedObject(surfaceControl, 0);
                    this.mRemote.transact(42, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void notifyQuickSettingsTilesChanged(int userId, List<ComponentName> tileComponentNames) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeTypedList(tileComponentNames, 0);
                    this.mRemote.transact(43, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void enableShortcutsForTargets(boolean enable, int shortcutTypes, List<String> shortcutTargets, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(enable);
                    _data.writeInt(shortcutTypes);
                    _data.writeStringList(shortcutTargets);
                    _data.writeInt(userId);
                    this.mRemote.transact(44, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public Bundle getA11yFeatureToTileMap(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(45, _data, _reply, 0);
                    _reply.readException();
                    Bundle _result = (Bundle) _reply.readTypedObject(Bundle.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean semIsAccessibilityServiceEnabled(int stateFlags) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(stateFlags);
                    this.mRemote.transact(46, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void semTurnOffAccessibilityService(int stateFlags) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(stateFlags);
                    this.mRemote.transact(47, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void semTurnOnAccessibilityService(int stateFlags) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(stateFlags);
                    this.mRemote.transact(48, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean semSetColorBlind(boolean enable, float userParameter) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(enable);
                    _data.writeFloat(userParameter);
                    this.mRemote.transact(49, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean semCheckMdnieColorBlind(int[] nums) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeIntArray(nums);
                    this.mRemote.transact(50, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean semSetMdnieAccessibilityMode(int mode, boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(mode);
                    _data.writeBoolean(enable);
                    this.mRemote.transact(51, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean semEnableMdnieColorFilter(int color, int opacity) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(color);
                    _data.writeInt(opacity);
                    this.mRemote.transact(52, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean semDisableMdnieColorFilter() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(53, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean semIsDarkScreenMode() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(54, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void semToggleDarkScreenMode() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(55, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void semUpdateAssitantMenu(Bundle bundle) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(bundle, 0);
                    this.mRemote.transact(56, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void semRegisterAssistantMenu(IBinder iBinder) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(iBinder);
                    this.mRemote.transact(57, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void semOpenDeviceOptions() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(58, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void semSetTwoFingerGestureRecognitionEnabled(boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(enable);
                    this.mRemote.transact(59, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean isTwoFingerGestureRecognitionEnabled() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(60, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean isScreenReaderEnabled() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(61, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public String getScreenReaderName() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(62, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void setScreenReaderEnabled(boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(enable);
                    this.mRemote.transact(63, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public int convertPixelToDpi(float pixels) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeFloat(pixels);
                    this.mRemote.transact(64, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void setTalkbackMode() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(65, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void semLockNow() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(66, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean OnStartGestureWakeup() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(67, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean OnStopGestureWakeup() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(68, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean semIsAccessibilityButtonShown() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(69, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void setMagnificationDisactivate() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(70, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void semDumpCallStack(String callStack) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callStack);
                    this.mRemote.transact(71, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void performAccessibilityDirectAccess(String targetName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(targetName);
                    this.mRemote.transact(72, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public Rect semGetWindowMagnificationBounds() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(73, _data, _reply, 0);
                    _reply.readException();
                    Rect _result = (Rect) _reply.readTypedObject(Rect.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public float semGetWindowMagnificationScale() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(74, _data, _reply, 0);
                    _reply.readException();
                    float _result = _reply.readFloat();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void semEnableWindowMagnification(int center_x, int center_y) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(center_x);
                    _data.writeInt(center_y);
                    this.mRemote.transact(75, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void semDisableWindowMagnification() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(76, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void semMoveWindowMagnification(float offsetX, float offsetY) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeFloat(offsetX);
                    _data.writeFloat(offsetY);
                    this.mRemote.transact(77, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean semIsWindowMagnificationEnabled() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(78, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean isActivatedMagnification() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(79, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void semPerformAccessibilityButtonClick(int displayId, int shortcutType, String targetName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeInt(shortcutType);
                    _data.writeString(targetName);
                    this.mRemote.transact(80, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void semInjectInputEventToInputFilter(InputEvent event, int policyFlags) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(event, 0);
                    _data.writeInt(policyFlags);
                    this.mRemote.transact(81, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean isCameraFlashNotificationRunning() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(82, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        protected void setPictureInPictureActionReplacingConnection_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_ACCESSIBILITY_DATA, getCallingPid(), getCallingUid());
        }

        protected void registerUiTestAutomationService_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.RETRIEVE_WINDOW_CONTENT, getCallingPid(), getCallingUid());
        }

        protected void getWindowToken_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.RETRIEVE_WINDOW_CONTENT, getCallingPid(), getCallingUid());
        }

        protected void notifyAccessibilityButtonClicked_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.STATUS_BAR_SERVICE, getCallingPid(), getCallingUid());
        }

        protected void notifyAccessibilityButtonVisibilityChanged_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.STATUS_BAR_SERVICE, getCallingPid(), getCallingUid());
        }

        protected void performAccessibilityShortcut_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_ACCESSIBILITY, getCallingPid(), getCallingUid());
        }

        protected void getAccessibilityShortcutTargets_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_ACCESSIBILITY, getCallingPid(), getCallingUid());
        }

        protected void registerSystemAction_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_ACCESSIBILITY, getCallingPid(), getCallingUid());
        }

        protected void unregisterSystemAction_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_ACCESSIBILITY, getCallingPid(), getCallingUid());
        }

        protected void setMagnificationConnection_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.STATUS_BAR_SERVICE, getCallingPid(), getCallingUid());
        }

        protected void setSystemAudioCaptioningEnabled_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.SET_SYSTEM_AUDIO_CAPTION, getCallingPid(), getCallingUid());
        }

        protected void setSystemAudioCaptioningUiEnabled_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.SET_SYSTEM_AUDIO_CAPTION, getCallingPid(), getCallingUid());
        }

        protected void registerProxyForDisplay_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CREATE_VIRTUAL_DEVICE, getCallingPid(), getCallingUid());
        }

        protected void unregisterProxyForDisplay_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CREATE_VIRTUAL_DEVICE, getCallingPid(), getCallingUid());
        }

        protected void injectInputEventToInputFilter_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission("android.permission.INJECT_EVENTS", getCallingPid(), getCallingUid());
        }

        protected void isAccessibilityServiceWarningRequired_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_ACCESSIBILITY, getCallingPid(), getCallingUid());
        }

        protected void attachAccessibilityOverlayToDisplay_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.INTERNAL_SYSTEM_WINDOW, getCallingPid(), getCallingUid());
        }

        protected void notifyQuickSettingsTilesChanged_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermissionAllOf(PERMISSIONS_notifyQuickSettingsTilesChanged, getCallingPid(), getCallingUid());
        }

        protected void enableShortcutsForTargets_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_ACCESSIBILITY, getCallingPid(), getCallingUid());
        }

        protected void getA11yFeatureToTileMap_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_ACCESSIBILITY, getCallingPid(), getCallingUid());
        }

        protected void semEnableMdnieColorFilter_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CHANGE_DISPLAY_COLOR, getCallingPid(), getCallingUid());
        }

        protected void semDisableMdnieColorFilter_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CHANGE_DISPLAY_COLOR, getCallingPid(), getCallingUid());
        }

        protected void semIsDarkScreenMode_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CHANGE_DISPLAY_COLOR, getCallingPid(), getCallingUid());
        }

        protected void semToggleDarkScreenMode_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CHANGE_DISPLAY_COLOR, getCallingPid(), getCallingUid());
        }

        protected void setScreenReaderEnabled_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.WRITE_SECURE_SETTINGS, getCallingPid(), getCallingUid());
        }

        protected void setMagnificationDisactivate_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_ACCESSIBILITY, getCallingPid(), getCallingUid());
        }

        protected void performAccessibilityDirectAccess_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_ACCESSIBILITY, getCallingPid(), getCallingUid());
        }

        protected void semGetWindowMagnificationBounds_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_ACCESSIBILITY, getCallingPid(), getCallingUid());
        }

        protected void semGetWindowMagnificationScale_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_ACCESSIBILITY, getCallingPid(), getCallingUid());
        }

        protected void semEnableWindowMagnification_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_ACCESSIBILITY, getCallingPid(), getCallingUid());
        }

        protected void semDisableWindowMagnification_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_ACCESSIBILITY, getCallingPid(), getCallingUid());
        }

        protected void semMoveWindowMagnification_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_ACCESSIBILITY, getCallingPid(), getCallingUid());
        }

        protected void semIsWindowMagnificationEnabled_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_ACCESSIBILITY, getCallingPid(), getCallingUid());
        }

        protected void isActivatedMagnification_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_ACCESSIBILITY, getCallingPid(), getCallingUid());
        }

        protected void semInjectInputEventToInputFilter_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission("android.permission.INJECT_EVENTS", getCallingPid(), getCallingUid());
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 81;
        }
    }

    public static class WindowTransformationSpec implements Parcelable {
        public static final Parcelable.Creator<WindowTransformationSpec> CREATOR = new Parcelable.Creator<WindowTransformationSpec>() { // from class: android.view.accessibility.IAccessibilityManager.WindowTransformationSpec.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public WindowTransformationSpec createFromParcel(Parcel _aidl_source) {
                WindowTransformationSpec _aidl_out = new WindowTransformationSpec();
                _aidl_out.readFromParcel(_aidl_source);
                return _aidl_out;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public WindowTransformationSpec[] newArray(int _aidl_size) {
                return new WindowTransformationSpec[_aidl_size];
            }
        };
        public MagnificationSpec magnificationSpec;
        public float[] transformationMatrix;

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel _aidl_parcel, int _aidl_flag) {
            int _aidl_start_pos = _aidl_parcel.dataPosition();
            _aidl_parcel.writeInt(0);
            _aidl_parcel.writeFloatArray(this.transformationMatrix);
            _aidl_parcel.writeTypedObject(this.magnificationSpec, _aidl_flag);
            int _aidl_end_pos = _aidl_parcel.dataPosition();
            _aidl_parcel.setDataPosition(_aidl_start_pos);
            _aidl_parcel.writeInt(_aidl_end_pos - _aidl_start_pos);
            _aidl_parcel.setDataPosition(_aidl_end_pos);
        }

        public final void readFromParcel(Parcel _aidl_parcel) {
            int _aidl_start_pos = _aidl_parcel.dataPosition();
            int _aidl_parcelable_size = _aidl_parcel.readInt();
            try {
                if (_aidl_parcelable_size < 4) {
                    throw new BadParcelableException("Parcelable too small");
                }
                if (_aidl_parcel.dataPosition() - _aidl_start_pos >= _aidl_parcelable_size) {
                    if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                    _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
                    return;
                }
                this.transformationMatrix = _aidl_parcel.createFloatArray();
                if (_aidl_parcel.dataPosition() - _aidl_start_pos >= _aidl_parcelable_size) {
                    if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                    _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
                } else {
                    this.magnificationSpec = (MagnificationSpec) _aidl_parcel.readTypedObject(MagnificationSpec.CREATOR);
                    if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                    _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
                }
            } catch (Throwable th) {
                if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
                throw th;
            }
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            int _mask = 0 | describeContents(this.magnificationSpec);
            return _mask;
        }

        private int describeContents(Object _v) {
            if (_v == null || !(_v instanceof Parcelable)) {
                return 0;
            }
            return ((Parcelable) _v).describeContents();
        }
    }
}
