package com.android.internal.statusbar;

import android.app.ITransientNotificationCallback;
import android.content.ComponentName;
import android.graphics.Rect;
import android.graphics.drawable.Icon;
import android.hardware.biometrics.IBiometricContextListener;
import android.hardware.biometrics.IBiometricSysuiReceiver;
import android.hardware.biometrics.PromptInfo;
import android.hardware.display.SemWifiDisplayParameter;
import android.hardware.fingerprint.IUdfpsRefreshRateRequestCallback;
import android.media.INearbyMediaDevicesProvider;
import android.media.MediaRoute2Info;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.widget.RemoteViews;
import com.android.internal.statusbar.IAddTileResultCallback;
import com.android.internal.statusbar.IUndoMediaTransferCallback;
import com.android.internal.view.AppearanceRegion;

/* loaded from: classes5.dex */
public interface IStatusBar extends IInterface {
    void abortTransient(int i, int i2) throws RemoteException;

    void addQsTile(ComponentName componentName) throws RemoteException;

    void animateCollapsePanels() throws RemoteException;

    void animateExpandNotificationsPanel() throws RemoteException;

    void animateExpandSettingsPanel(String str) throws RemoteException;

    void appTransitionCancelled(int i) throws RemoteException;

    void appTransitionFinished(int i) throws RemoteException;

    void appTransitionPending(int i) throws RemoteException;

    void appTransitionStarting(int i, long j, long j2) throws RemoteException;

    void cancelPreloadRecentApps() throws RemoteException;

    void cancelRequestAddTile(String str) throws RemoteException;

    void clickQsTile(ComponentName componentName) throws RemoteException;

    void disable(int i, int i2, int i3) throws RemoteException;

    void dismissInattentiveSleepWarning(boolean z) throws RemoteException;

    void dismissKeyboardShortcutsMenu() throws RemoteException;

    void dumpProto(String[] strArr, ParcelFileDescriptor parcelFileDescriptor) throws RemoteException;

    void enterStageSplitFromRunningApp(boolean z) throws RemoteException;

    void goToFullscreenFromSplit() throws RemoteException;

    void handleSystemKey(KeyEvent keyEvent) throws RemoteException;

    void hideAuthenticationDialog(long j) throws RemoteException;

    void hideRecentApps(boolean z, boolean z2) throws RemoteException;

    void hideToast(String str, IBinder iBinder) throws RemoteException;

    void notifyRequestedGameToolsWin(boolean z) throws RemoteException;

    void notifyRequestedSystemKey(boolean z, boolean z2) throws RemoteException;

    void notifySamsungPayInfo(int i, boolean z, Rect rect) throws RemoteException;

    void onBiometricAuthenticated(int i) throws RemoteException;

    void onBiometricError(int i, int i2, int i3) throws RemoteException;

    void onBiometricHelp(int i, String str) throws RemoteException;

    void onCameraLaunchGestureDetected(int i) throws RemoteException;

    void onDisplayReady(int i) throws RemoteException;

    void onEmergencyActionLaunchGestureDetected() throws RemoteException;

    void onFlashlightKeyPressed(int i) throws RemoteException;

    void onFocusedDisplayChanged(int i) throws RemoteException;

    void onProposedRotationChanged(int i, boolean z) throws RemoteException;

    void onRecentsAnimationStateChanged(boolean z) throws RemoteException;

    void onSystemBarAttributesChanged(int i, int i2, AppearanceRegion[] appearanceRegionArr, boolean z, int i3, int i4, String str, LetterboxDetails[] letterboxDetailsArr) throws RemoteException;

    void passThroughShellCommand(String[] strArr, ParcelFileDescriptor parcelFileDescriptor) throws RemoteException;

    void preloadRecentApps() throws RemoteException;

    void registerNearbyMediaDevicesProvider(INearbyMediaDevicesProvider iNearbyMediaDevicesProvider) throws RemoteException;

    void remQsTile(ComponentName componentName) throws RemoteException;

    void removeIcon(String str) throws RemoteException;

    void requestAddTile(ComponentName componentName, CharSequence charSequence, CharSequence charSequence2, Icon icon, IAddTileResultCallback iAddTileResultCallback) throws RemoteException;

    void requestTileServiceListeningState(ComponentName componentName) throws RemoteException;

    void requestWindowMagnificationConnection(boolean z) throws RemoteException;

    void resetScheduleAutoHide() throws RemoteException;

    void runGcForTest() throws RemoteException;

    void sendKeyEventToDesktopTaskbar(KeyEvent keyEvent) throws RemoteException;

    void sendThreeFingerGestureKeyEvent(KeyEvent keyEvent) throws RemoteException;

    void setBiometicContextListener(IBiometricContextListener iBiometricContextListener) throws RemoteException;

    void setBlueLightFilter(boolean z, int i) throws RemoteException;

    void setIcon(String str, StatusBarIcon statusBarIcon) throws RemoteException;

    void setImeWindowStatus(int i, IBinder iBinder, int i2, int i3, boolean z) throws RemoteException;

    void setNavigationBarLumaSamplingEnabled(int i, boolean z) throws RemoteException;

    void setNavigationBarShortcut(String str, RemoteViews remoteViews, int i, int i2) throws RemoteException;

    void setTopAppHidesStatusBar(boolean z) throws RemoteException;

    void setUdfpsRefreshRateCallback(IUdfpsRefreshRateRequestCallback iUdfpsRefreshRateRequestCallback) throws RemoteException;

    void setWindowState(int i, int i2, int i3) throws RemoteException;

    void showAssistDisclosure() throws RemoteException;

    void showAuthenticationDialog(PromptInfo promptInfo, IBiometricSysuiReceiver iBiometricSysuiReceiver, int[] iArr, boolean z, boolean z2, int i, long j, String str, long j2) throws RemoteException;

    void showGlobalActionsMenu(int i) throws RemoteException;

    void showInattentiveSleepWarning() throws RemoteException;

    void showMediaOutputSwitcher(String str) throws RemoteException;

    void showPictureInPictureMenu() throws RemoteException;

    void showPinningEnterExitToast(boolean z) throws RemoteException;

    void showPinningEscapeToast() throws RemoteException;

    void showRearDisplayDialog(int i) throws RemoteException;

    void showRecentApps(boolean z) throws RemoteException;

    void showScreenPinningRequest(int i) throws RemoteException;

    void showShutdownUi(boolean z, String str) throws RemoteException;

    void showToast(int i, String str, IBinder iBinder, CharSequence charSequence, IBinder iBinder2, int i2, ITransientNotificationCallback iTransientNotificationCallback, int i3) throws RemoteException;

    void showTransient(int i, int i2, boolean z) throws RemoteException;

    void showWirelessChargingAnimation(int i) throws RemoteException;

    void startAssist(Bundle bundle) throws RemoteException;

    void startSearcleByHomeKey(boolean z, boolean z2) throws RemoteException;

    void startTracing() throws RemoteException;

    void stopTracing() throws RemoteException;

    void suppressAmbientDisplay(boolean z) throws RemoteException;

    void toggleKeyboardShortcutsMenu(int i) throws RemoteException;

    void togglePanel() throws RemoteException;

    void toggleRecentApps() throws RemoteException;

    void toggleSplitScreen() throws RemoteException;

    void toggleTaskbar() throws RemoteException;

    void unregisterNearbyMediaDevicesProvider(INearbyMediaDevicesProvider iNearbyMediaDevicesProvider) throws RemoteException;

    void updateMediaTapToTransferReceiverDisplay(int i, MediaRoute2Info mediaRoute2Info, Icon icon, CharSequence charSequence) throws RemoteException;

    void updateMediaTapToTransferSenderDisplay(int i, MediaRoute2Info mediaRoute2Info, IUndoMediaTransferCallback iUndoMediaTransferCallback) throws RemoteException;

    /* loaded from: classes5.dex */
    public static class Default implements IStatusBar {
        @Override // com.android.internal.statusbar.IStatusBar
        public void setIcon(String slot, StatusBarIcon icon) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void removeIcon(String slot) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void disable(int displayId, int state1, int state2) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void animateExpandNotificationsPanel() throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void animateExpandSettingsPanel(String subPanel) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void animateCollapsePanels() throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void togglePanel() throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void showWirelessChargingAnimation(int batteryLevel) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void setImeWindowStatus(int displayId, IBinder token, int vis, int backDisposition, boolean showImeSwitcher) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void setWindowState(int display, int window, int state) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void showRecentApps(boolean triggeredFromAltTab) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void hideRecentApps(boolean triggeredFromAltTab, boolean triggeredFromHomeKey) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void toggleRecentApps() throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void toggleTaskbar() throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void toggleSplitScreen() throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void preloadRecentApps() throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void cancelPreloadRecentApps() throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void showScreenPinningRequest(int taskId) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void dismissKeyboardShortcutsMenu() throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void toggleKeyboardShortcutsMenu(int deviceId) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void appTransitionPending(int displayId) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void appTransitionCancelled(int displayId) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void appTransitionStarting(int displayId, long statusBarAnimationsStartTime, long statusBarAnimationsDuration) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void appTransitionFinished(int displayId) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void showAssistDisclosure() throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void startAssist(Bundle args) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void onCameraLaunchGestureDetected(int source) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void onEmergencyActionLaunchGestureDetected() throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void showPictureInPictureMenu() throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void showGlobalActionsMenu(int sideKeyType) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void onProposedRotationChanged(int rotation, boolean isValid) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void setTopAppHidesStatusBar(boolean hidesStatusBar) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void addQsTile(ComponentName tile) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void remQsTile(ComponentName tile) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void clickQsTile(ComponentName tile) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void handleSystemKey(KeyEvent key) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void showPinningEnterExitToast(boolean entering) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void showPinningEscapeToast() throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void showShutdownUi(boolean isReboot, String reason) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void showAuthenticationDialog(PromptInfo promptInfo, IBiometricSysuiReceiver sysuiReceiver, int[] sensorIds, boolean credentialAllowed, boolean requireConfirmation, int userId, long operationId, String opPackageName, long requestId) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void onBiometricAuthenticated(int modality) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void onBiometricHelp(int modality, String message) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void onBiometricError(int modality, int error, int vendorCode) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void hideAuthenticationDialog(long requestId) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void setBiometicContextListener(IBiometricContextListener listener) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void setUdfpsRefreshRateCallback(IUdfpsRefreshRateRequestCallback callback) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void onDisplayReady(int displayId) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void onRecentsAnimationStateChanged(boolean running) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void onSystemBarAttributesChanged(int displayId, int appearance, AppearanceRegion[] appearanceRegions, boolean navbarColorManagedByIme, int behavior, int requestedVisibleTypes, String packageName, LetterboxDetails[] letterboxDetails) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void showTransient(int displayId, int types, boolean isGestureOnSystemBar) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void abortTransient(int displayId, int types) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void showInattentiveSleepWarning() throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void dismissInattentiveSleepWarning(boolean animated) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void showToast(int uid, String packageName, IBinder token, CharSequence text, IBinder windowToken, int duration, ITransientNotificationCallback callback, int displayId) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void hideToast(String packageName, IBinder token) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void startTracing() throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void stopTracing() throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void suppressAmbientDisplay(boolean suppress) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void requestWindowMagnificationConnection(boolean connect) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void passThroughShellCommand(String[] args, ParcelFileDescriptor pfd) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void setNavigationBarLumaSamplingEnabled(int displayId, boolean enable) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void runGcForTest() throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void requestTileServiceListeningState(ComponentName componentName) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void requestAddTile(ComponentName componentName, CharSequence appName, CharSequence label, Icon icon, IAddTileResultCallback callback) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void cancelRequestAddTile(String packageName) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void updateMediaTapToTransferSenderDisplay(int displayState, MediaRoute2Info routeInfo, IUndoMediaTransferCallback undoCallback) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void updateMediaTapToTransferReceiverDisplay(int displayState, MediaRoute2Info routeInfo, Icon appIcon, CharSequence appName) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void registerNearbyMediaDevicesProvider(INearbyMediaDevicesProvider provider) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void unregisterNearbyMediaDevicesProvider(INearbyMediaDevicesProvider provider) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void dumpProto(String[] args, ParcelFileDescriptor pfd) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void showRearDisplayDialog(int currentBaseState) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void goToFullscreenFromSplit() throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void enterStageSplitFromRunningApp(boolean leftOrTop) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void showMediaOutputSwitcher(String packageName) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void setBlueLightFilter(boolean on, int intensity) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void onFlashlightKeyPressed(int key) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void setNavigationBarShortcut(String requestClass, RemoteViews remoteViews, int position, int priority) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void resetScheduleAutoHide() throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void notifySamsungPayInfo(int displayId, boolean visible, Rect frame) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void onFocusedDisplayChanged(int focusedDisplayId) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void notifyRequestedSystemKey(boolean isRequestedRecentKey, boolean isRequestedHomeKey) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void sendThreeFingerGestureKeyEvent(KeyEvent event) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void notifyRequestedGameToolsWin(boolean attached) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void sendKeyEventToDesktopTaskbar(KeyEvent event) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void startSearcleByHomeKey(boolean down, boolean longPress) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes5.dex */
    public static abstract class Stub extends Binder implements IStatusBar {
        public static final String DESCRIPTOR = "com.android.internal.statusbar.IStatusBar";
        static final int TRANSACTION_abortTransient = 51;
        static final int TRANSACTION_addQsTile = 33;
        static final int TRANSACTION_animateCollapsePanels = 6;
        static final int TRANSACTION_animateExpandNotificationsPanel = 4;
        static final int TRANSACTION_animateExpandSettingsPanel = 5;
        static final int TRANSACTION_appTransitionCancelled = 22;
        static final int TRANSACTION_appTransitionFinished = 24;
        static final int TRANSACTION_appTransitionPending = 21;
        static final int TRANSACTION_appTransitionStarting = 23;
        static final int TRANSACTION_cancelPreloadRecentApps = 17;
        static final int TRANSACTION_cancelRequestAddTile = 65;
        static final int TRANSACTION_clickQsTile = 35;
        static final int TRANSACTION_disable = 3;
        static final int TRANSACTION_dismissInattentiveSleepWarning = 53;
        static final int TRANSACTION_dismissKeyboardShortcutsMenu = 19;
        static final int TRANSACTION_dumpProto = 70;
        static final int TRANSACTION_enterStageSplitFromRunningApp = 73;
        static final int TRANSACTION_goToFullscreenFromSplit = 72;
        static final int TRANSACTION_handleSystemKey = 36;
        static final int TRANSACTION_hideAuthenticationDialog = 44;
        static final int TRANSACTION_hideRecentApps = 12;
        static final int TRANSACTION_hideToast = 55;
        static final int TRANSACTION_notifyRequestedGameToolsWin = 83;
        static final int TRANSACTION_notifyRequestedSystemKey = 81;
        static final int TRANSACTION_notifySamsungPayInfo = 79;
        static final int TRANSACTION_onBiometricAuthenticated = 41;
        static final int TRANSACTION_onBiometricError = 43;
        static final int TRANSACTION_onBiometricHelp = 42;
        static final int TRANSACTION_onCameraLaunchGestureDetected = 27;
        static final int TRANSACTION_onDisplayReady = 47;
        static final int TRANSACTION_onEmergencyActionLaunchGestureDetected = 28;
        static final int TRANSACTION_onFlashlightKeyPressed = 76;
        static final int TRANSACTION_onFocusedDisplayChanged = 80;
        static final int TRANSACTION_onProposedRotationChanged = 31;
        static final int TRANSACTION_onRecentsAnimationStateChanged = 48;
        static final int TRANSACTION_onSystemBarAttributesChanged = 49;
        static final int TRANSACTION_passThroughShellCommand = 60;
        static final int TRANSACTION_preloadRecentApps = 16;
        static final int TRANSACTION_registerNearbyMediaDevicesProvider = 68;
        static final int TRANSACTION_remQsTile = 34;
        static final int TRANSACTION_removeIcon = 2;
        static final int TRANSACTION_requestAddTile = 64;
        static final int TRANSACTION_requestTileServiceListeningState = 63;
        static final int TRANSACTION_requestWindowMagnificationConnection = 59;
        static final int TRANSACTION_resetScheduleAutoHide = 78;
        static final int TRANSACTION_runGcForTest = 62;
        static final int TRANSACTION_sendKeyEventToDesktopTaskbar = 84;
        static final int TRANSACTION_sendThreeFingerGestureKeyEvent = 82;
        static final int TRANSACTION_setBiometicContextListener = 45;
        static final int TRANSACTION_setBlueLightFilter = 75;
        static final int TRANSACTION_setIcon = 1;
        static final int TRANSACTION_setImeWindowStatus = 9;
        static final int TRANSACTION_setNavigationBarLumaSamplingEnabled = 61;
        static final int TRANSACTION_setNavigationBarShortcut = 77;
        static final int TRANSACTION_setTopAppHidesStatusBar = 32;
        static final int TRANSACTION_setUdfpsRefreshRateCallback = 46;
        static final int TRANSACTION_setWindowState = 10;
        static final int TRANSACTION_showAssistDisclosure = 25;
        static final int TRANSACTION_showAuthenticationDialog = 40;
        static final int TRANSACTION_showGlobalActionsMenu = 30;
        static final int TRANSACTION_showInattentiveSleepWarning = 52;
        static final int TRANSACTION_showMediaOutputSwitcher = 74;
        static final int TRANSACTION_showPictureInPictureMenu = 29;
        static final int TRANSACTION_showPinningEnterExitToast = 37;
        static final int TRANSACTION_showPinningEscapeToast = 38;
        static final int TRANSACTION_showRearDisplayDialog = 71;
        static final int TRANSACTION_showRecentApps = 11;
        static final int TRANSACTION_showScreenPinningRequest = 18;
        static final int TRANSACTION_showShutdownUi = 39;
        static final int TRANSACTION_showToast = 54;
        static final int TRANSACTION_showTransient = 50;
        static final int TRANSACTION_showWirelessChargingAnimation = 8;
        static final int TRANSACTION_startAssist = 26;
        static final int TRANSACTION_startSearcleByHomeKey = 85;
        static final int TRANSACTION_startTracing = 56;
        static final int TRANSACTION_stopTracing = 57;
        static final int TRANSACTION_suppressAmbientDisplay = 58;
        static final int TRANSACTION_toggleKeyboardShortcutsMenu = 20;
        static final int TRANSACTION_togglePanel = 7;
        static final int TRANSACTION_toggleRecentApps = 13;
        static final int TRANSACTION_toggleSplitScreen = 15;
        static final int TRANSACTION_toggleTaskbar = 14;
        static final int TRANSACTION_unregisterNearbyMediaDevicesProvider = 69;
        static final int TRANSACTION_updateMediaTapToTransferReceiverDisplay = 67;
        static final int TRANSACTION_updateMediaTapToTransferSenderDisplay = 66;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IStatusBar asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IStatusBar)) {
                return (IStatusBar) iin;
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
                    return "setIcon";
                case 2:
                    return "removeIcon";
                case 3:
                    return SemWifiDisplayParameter.VALUE_DISABLE;
                case 4:
                    return "animateExpandNotificationsPanel";
                case 5:
                    return "animateExpandSettingsPanel";
                case 6:
                    return "animateCollapsePanels";
                case 7:
                    return "togglePanel";
                case 8:
                    return "showWirelessChargingAnimation";
                case 9:
                    return "setImeWindowStatus";
                case 10:
                    return "setWindowState";
                case 11:
                    return "showRecentApps";
                case 12:
                    return "hideRecentApps";
                case 13:
                    return "toggleRecentApps";
                case 14:
                    return "toggleTaskbar";
                case 15:
                    return "toggleSplitScreen";
                case 16:
                    return "preloadRecentApps";
                case 17:
                    return "cancelPreloadRecentApps";
                case 18:
                    return "showScreenPinningRequest";
                case 19:
                    return "dismissKeyboardShortcutsMenu";
                case 20:
                    return "toggleKeyboardShortcutsMenu";
                case 21:
                    return "appTransitionPending";
                case 22:
                    return "appTransitionCancelled";
                case 23:
                    return "appTransitionStarting";
                case 24:
                    return "appTransitionFinished";
                case 25:
                    return "showAssistDisclosure";
                case 26:
                    return "startAssist";
                case 27:
                    return "onCameraLaunchGestureDetected";
                case 28:
                    return "onEmergencyActionLaunchGestureDetected";
                case 29:
                    return "showPictureInPictureMenu";
                case 30:
                    return "showGlobalActionsMenu";
                case 31:
                    return "onProposedRotationChanged";
                case 32:
                    return "setTopAppHidesStatusBar";
                case 33:
                    return "addQsTile";
                case 34:
                    return "remQsTile";
                case 35:
                    return "clickQsTile";
                case 36:
                    return "handleSystemKey";
                case 37:
                    return "showPinningEnterExitToast";
                case 38:
                    return "showPinningEscapeToast";
                case 39:
                    return "showShutdownUi";
                case 40:
                    return "showAuthenticationDialog";
                case 41:
                    return "onBiometricAuthenticated";
                case 42:
                    return "onBiometricHelp";
                case 43:
                    return "onBiometricError";
                case 44:
                    return "hideAuthenticationDialog";
                case 45:
                    return "setBiometicContextListener";
                case 46:
                    return "setUdfpsRefreshRateCallback";
                case 47:
                    return "onDisplayReady";
                case 48:
                    return "onRecentsAnimationStateChanged";
                case 49:
                    return "onSystemBarAttributesChanged";
                case 50:
                    return "showTransient";
                case 51:
                    return "abortTransient";
                case 52:
                    return "showInattentiveSleepWarning";
                case 53:
                    return "dismissInattentiveSleepWarning";
                case 54:
                    return "showToast";
                case 55:
                    return "hideToast";
                case 56:
                    return "startTracing";
                case 57:
                    return "stopTracing";
                case 58:
                    return "suppressAmbientDisplay";
                case 59:
                    return "requestWindowMagnificationConnection";
                case 60:
                    return "passThroughShellCommand";
                case 61:
                    return "setNavigationBarLumaSamplingEnabled";
                case 62:
                    return "runGcForTest";
                case 63:
                    return "requestTileServiceListeningState";
                case 64:
                    return "requestAddTile";
                case 65:
                    return "cancelRequestAddTile";
                case 66:
                    return "updateMediaTapToTransferSenderDisplay";
                case 67:
                    return "updateMediaTapToTransferReceiverDisplay";
                case 68:
                    return "registerNearbyMediaDevicesProvider";
                case 69:
                    return "unregisterNearbyMediaDevicesProvider";
                case 70:
                    return "dumpProto";
                case 71:
                    return "showRearDisplayDialog";
                case 72:
                    return "goToFullscreenFromSplit";
                case 73:
                    return "enterStageSplitFromRunningApp";
                case 74:
                    return "showMediaOutputSwitcher";
                case 75:
                    return "setBlueLightFilter";
                case 76:
                    return "onFlashlightKeyPressed";
                case 77:
                    return "setNavigationBarShortcut";
                case 78:
                    return "resetScheduleAutoHide";
                case 79:
                    return "notifySamsungPayInfo";
                case 80:
                    return "onFocusedDisplayChanged";
                case 81:
                    return "notifyRequestedSystemKey";
                case 82:
                    return "sendThreeFingerGestureKeyEvent";
                case 83:
                    return "notifyRequestedGameToolsWin";
                case 84:
                    return "sendKeyEventToDesktopTaskbar";
                case 85:
                    return "startSearcleByHomeKey";
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
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            String _arg0 = data.readString();
                            StatusBarIcon _arg1 = (StatusBarIcon) data.readTypedObject(StatusBarIcon.CREATOR);
                            data.enforceNoDataAvail();
                            setIcon(_arg0, _arg1);
                            return true;
                        case 2:
                            String _arg02 = data.readString();
                            data.enforceNoDataAvail();
                            removeIcon(_arg02);
                            return true;
                        case 3:
                            int _arg03 = data.readInt();
                            int _arg12 = data.readInt();
                            int _arg2 = data.readInt();
                            data.enforceNoDataAvail();
                            disable(_arg03, _arg12, _arg2);
                            return true;
                        case 4:
                            animateExpandNotificationsPanel();
                            return true;
                        case 5:
                            String _arg04 = data.readString();
                            data.enforceNoDataAvail();
                            animateExpandSettingsPanel(_arg04);
                            return true;
                        case 6:
                            animateCollapsePanels();
                            return true;
                        case 7:
                            togglePanel();
                            return true;
                        case 8:
                            int _arg05 = data.readInt();
                            data.enforceNoDataAvail();
                            showWirelessChargingAnimation(_arg05);
                            return true;
                        case 9:
                            int _arg06 = data.readInt();
                            IBinder _arg13 = data.readStrongBinder();
                            int _arg22 = data.readInt();
                            int _arg3 = data.readInt();
                            boolean _arg4 = data.readBoolean();
                            data.enforceNoDataAvail();
                            setImeWindowStatus(_arg06, _arg13, _arg22, _arg3, _arg4);
                            return true;
                        case 10:
                            int _arg07 = data.readInt();
                            int _arg14 = data.readInt();
                            int _arg23 = data.readInt();
                            data.enforceNoDataAvail();
                            setWindowState(_arg07, _arg14, _arg23);
                            return true;
                        case 11:
                            boolean _arg08 = data.readBoolean();
                            data.enforceNoDataAvail();
                            showRecentApps(_arg08);
                            return true;
                        case 12:
                            boolean _arg09 = data.readBoolean();
                            boolean _arg15 = data.readBoolean();
                            data.enforceNoDataAvail();
                            hideRecentApps(_arg09, _arg15);
                            return true;
                        case 13:
                            toggleRecentApps();
                            return true;
                        case 14:
                            toggleTaskbar();
                            return true;
                        case 15:
                            toggleSplitScreen();
                            return true;
                        case 16:
                            preloadRecentApps();
                            return true;
                        case 17:
                            cancelPreloadRecentApps();
                            return true;
                        case 18:
                            int _arg010 = data.readInt();
                            data.enforceNoDataAvail();
                            showScreenPinningRequest(_arg010);
                            return true;
                        case 19:
                            dismissKeyboardShortcutsMenu();
                            return true;
                        case 20:
                            int _arg011 = data.readInt();
                            data.enforceNoDataAvail();
                            toggleKeyboardShortcutsMenu(_arg011);
                            return true;
                        case 21:
                            int _arg012 = data.readInt();
                            data.enforceNoDataAvail();
                            appTransitionPending(_arg012);
                            return true;
                        case 22:
                            int _arg013 = data.readInt();
                            data.enforceNoDataAvail();
                            appTransitionCancelled(_arg013);
                            return true;
                        case 23:
                            int _arg014 = data.readInt();
                            long _arg16 = data.readLong();
                            long _arg24 = data.readLong();
                            data.enforceNoDataAvail();
                            appTransitionStarting(_arg014, _arg16, _arg24);
                            return true;
                        case 24:
                            int _arg015 = data.readInt();
                            data.enforceNoDataAvail();
                            appTransitionFinished(_arg015);
                            return true;
                        case 25:
                            showAssistDisclosure();
                            return true;
                        case 26:
                            Bundle _arg016 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                            data.enforceNoDataAvail();
                            startAssist(_arg016);
                            return true;
                        case 27:
                            int _arg017 = data.readInt();
                            data.enforceNoDataAvail();
                            onCameraLaunchGestureDetected(_arg017);
                            return true;
                        case 28:
                            onEmergencyActionLaunchGestureDetected();
                            return true;
                        case 29:
                            showPictureInPictureMenu();
                            return true;
                        case 30:
                            int _arg018 = data.readInt();
                            data.enforceNoDataAvail();
                            showGlobalActionsMenu(_arg018);
                            return true;
                        case 31:
                            int _arg019 = data.readInt();
                            boolean _arg17 = data.readBoolean();
                            data.enforceNoDataAvail();
                            onProposedRotationChanged(_arg019, _arg17);
                            return true;
                        case 32:
                            boolean _arg020 = data.readBoolean();
                            data.enforceNoDataAvail();
                            setTopAppHidesStatusBar(_arg020);
                            return true;
                        case 33:
                            ComponentName _arg021 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                            data.enforceNoDataAvail();
                            addQsTile(_arg021);
                            return true;
                        case 34:
                            ComponentName _arg022 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                            data.enforceNoDataAvail();
                            remQsTile(_arg022);
                            return true;
                        case 35:
                            ComponentName _arg023 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                            data.enforceNoDataAvail();
                            clickQsTile(_arg023);
                            return true;
                        case 36:
                            KeyEvent _arg024 = (KeyEvent) data.readTypedObject(KeyEvent.CREATOR);
                            data.enforceNoDataAvail();
                            handleSystemKey(_arg024);
                            return true;
                        case 37:
                            boolean _arg025 = data.readBoolean();
                            data.enforceNoDataAvail();
                            showPinningEnterExitToast(_arg025);
                            return true;
                        case 38:
                            showPinningEscapeToast();
                            return true;
                        case 39:
                            boolean _arg026 = data.readBoolean();
                            String _arg18 = data.readString();
                            data.enforceNoDataAvail();
                            showShutdownUi(_arg026, _arg18);
                            return true;
                        case 40:
                            PromptInfo _arg027 = (PromptInfo) data.readTypedObject(PromptInfo.CREATOR);
                            IBiometricSysuiReceiver _arg19 = IBiometricSysuiReceiver.Stub.asInterface(data.readStrongBinder());
                            int[] _arg25 = data.createIntArray();
                            boolean _arg32 = data.readBoolean();
                            boolean _arg42 = data.readBoolean();
                            int _arg5 = data.readInt();
                            long _arg6 = data.readLong();
                            String _arg7 = data.readString();
                            long _arg8 = data.readLong();
                            data.enforceNoDataAvail();
                            showAuthenticationDialog(_arg027, _arg19, _arg25, _arg32, _arg42, _arg5, _arg6, _arg7, _arg8);
                            return true;
                        case 41:
                            int _arg028 = data.readInt();
                            data.enforceNoDataAvail();
                            onBiometricAuthenticated(_arg028);
                            return true;
                        case 42:
                            int _arg029 = data.readInt();
                            String _arg110 = data.readString();
                            data.enforceNoDataAvail();
                            onBiometricHelp(_arg029, _arg110);
                            return true;
                        case 43:
                            int _arg030 = data.readInt();
                            int _arg111 = data.readInt();
                            int _arg26 = data.readInt();
                            data.enforceNoDataAvail();
                            onBiometricError(_arg030, _arg111, _arg26);
                            return true;
                        case 44:
                            long _arg031 = data.readLong();
                            data.enforceNoDataAvail();
                            hideAuthenticationDialog(_arg031);
                            return true;
                        case 45:
                            IBiometricContextListener _arg032 = IBiometricContextListener.Stub.asInterface(data.readStrongBinder());
                            data.enforceNoDataAvail();
                            setBiometicContextListener(_arg032);
                            return true;
                        case 46:
                            IUdfpsRefreshRateRequestCallback _arg033 = IUdfpsRefreshRateRequestCallback.Stub.asInterface(data.readStrongBinder());
                            data.enforceNoDataAvail();
                            setUdfpsRefreshRateCallback(_arg033);
                            return true;
                        case 47:
                            int _arg034 = data.readInt();
                            data.enforceNoDataAvail();
                            onDisplayReady(_arg034);
                            return true;
                        case 48:
                            boolean _arg035 = data.readBoolean();
                            data.enforceNoDataAvail();
                            onRecentsAnimationStateChanged(_arg035);
                            return true;
                        case 49:
                            int _arg036 = data.readInt();
                            int _arg112 = data.readInt();
                            AppearanceRegion[] _arg27 = (AppearanceRegion[]) data.createTypedArray(AppearanceRegion.CREATOR);
                            boolean _arg33 = data.readBoolean();
                            int _arg43 = data.readInt();
                            int _arg52 = data.readInt();
                            String _arg62 = data.readString();
                            LetterboxDetails[] _arg72 = (LetterboxDetails[]) data.createTypedArray(LetterboxDetails.CREATOR);
                            data.enforceNoDataAvail();
                            onSystemBarAttributesChanged(_arg036, _arg112, _arg27, _arg33, _arg43, _arg52, _arg62, _arg72);
                            return true;
                        case 50:
                            int _arg037 = data.readInt();
                            int _arg113 = data.readInt();
                            boolean _arg28 = data.readBoolean();
                            data.enforceNoDataAvail();
                            showTransient(_arg037, _arg113, _arg28);
                            return true;
                        case 51:
                            int _arg038 = data.readInt();
                            int _arg114 = data.readInt();
                            data.enforceNoDataAvail();
                            abortTransient(_arg038, _arg114);
                            return true;
                        case 52:
                            showInattentiveSleepWarning();
                            return true;
                        case 53:
                            boolean _arg039 = data.readBoolean();
                            data.enforceNoDataAvail();
                            dismissInattentiveSleepWarning(_arg039);
                            return true;
                        case 54:
                            int _arg040 = data.readInt();
                            String _arg115 = data.readString();
                            IBinder _arg29 = data.readStrongBinder();
                            CharSequence _arg34 = (CharSequence) data.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                            IBinder _arg44 = data.readStrongBinder();
                            int _arg53 = data.readInt();
                            ITransientNotificationCallback _arg63 = ITransientNotificationCallback.Stub.asInterface(data.readStrongBinder());
                            int _arg73 = data.readInt();
                            data.enforceNoDataAvail();
                            showToast(_arg040, _arg115, _arg29, _arg34, _arg44, _arg53, _arg63, _arg73);
                            return true;
                        case 55:
                            String _arg041 = data.readString();
                            IBinder _arg116 = data.readStrongBinder();
                            data.enforceNoDataAvail();
                            hideToast(_arg041, _arg116);
                            return true;
                        case 56:
                            startTracing();
                            return true;
                        case 57:
                            stopTracing();
                            return true;
                        case 58:
                            boolean _arg042 = data.readBoolean();
                            data.enforceNoDataAvail();
                            suppressAmbientDisplay(_arg042);
                            return true;
                        case 59:
                            boolean _arg043 = data.readBoolean();
                            data.enforceNoDataAvail();
                            requestWindowMagnificationConnection(_arg043);
                            return true;
                        case 60:
                            String[] _arg044 = data.createStringArray();
                            ParcelFileDescriptor _arg117 = (ParcelFileDescriptor) data.readTypedObject(ParcelFileDescriptor.CREATOR);
                            data.enforceNoDataAvail();
                            passThroughShellCommand(_arg044, _arg117);
                            return true;
                        case 61:
                            int _arg045 = data.readInt();
                            boolean _arg118 = data.readBoolean();
                            data.enforceNoDataAvail();
                            setNavigationBarLumaSamplingEnabled(_arg045, _arg118);
                            return true;
                        case 62:
                            runGcForTest();
                            return true;
                        case 63:
                            ComponentName _arg046 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                            data.enforceNoDataAvail();
                            requestTileServiceListeningState(_arg046);
                            return true;
                        case 64:
                            ComponentName _arg047 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                            CharSequence _arg119 = (CharSequence) data.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                            CharSequence _arg210 = (CharSequence) data.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                            Icon _arg35 = (Icon) data.readTypedObject(Icon.CREATOR);
                            IAddTileResultCallback _arg45 = IAddTileResultCallback.Stub.asInterface(data.readStrongBinder());
                            data.enforceNoDataAvail();
                            requestAddTile(_arg047, _arg119, _arg210, _arg35, _arg45);
                            return true;
                        case 65:
                            String _arg048 = data.readString();
                            data.enforceNoDataAvail();
                            cancelRequestAddTile(_arg048);
                            return true;
                        case 66:
                            int _arg049 = data.readInt();
                            MediaRoute2Info _arg120 = (MediaRoute2Info) data.readTypedObject(MediaRoute2Info.CREATOR);
                            IUndoMediaTransferCallback _arg211 = IUndoMediaTransferCallback.Stub.asInterface(data.readStrongBinder());
                            data.enforceNoDataAvail();
                            updateMediaTapToTransferSenderDisplay(_arg049, _arg120, _arg211);
                            return true;
                        case 67:
                            int _arg050 = data.readInt();
                            MediaRoute2Info _arg121 = (MediaRoute2Info) data.readTypedObject(MediaRoute2Info.CREATOR);
                            Icon _arg212 = (Icon) data.readTypedObject(Icon.CREATOR);
                            CharSequence _arg36 = (CharSequence) data.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                            data.enforceNoDataAvail();
                            updateMediaTapToTransferReceiverDisplay(_arg050, _arg121, _arg212, _arg36);
                            return true;
                        case 68:
                            INearbyMediaDevicesProvider _arg051 = INearbyMediaDevicesProvider.Stub.asInterface(data.readStrongBinder());
                            data.enforceNoDataAvail();
                            registerNearbyMediaDevicesProvider(_arg051);
                            return true;
                        case 69:
                            INearbyMediaDevicesProvider _arg052 = INearbyMediaDevicesProvider.Stub.asInterface(data.readStrongBinder());
                            data.enforceNoDataAvail();
                            unregisterNearbyMediaDevicesProvider(_arg052);
                            return true;
                        case 70:
                            String[] _arg053 = data.createStringArray();
                            ParcelFileDescriptor _arg122 = (ParcelFileDescriptor) data.readTypedObject(ParcelFileDescriptor.CREATOR);
                            data.enforceNoDataAvail();
                            dumpProto(_arg053, _arg122);
                            return true;
                        case 71:
                            int _arg054 = data.readInt();
                            data.enforceNoDataAvail();
                            showRearDisplayDialog(_arg054);
                            return true;
                        case 72:
                            goToFullscreenFromSplit();
                            return true;
                        case 73:
                            boolean _arg055 = data.readBoolean();
                            data.enforceNoDataAvail();
                            enterStageSplitFromRunningApp(_arg055);
                            return true;
                        case 74:
                            String _arg056 = data.readString();
                            data.enforceNoDataAvail();
                            showMediaOutputSwitcher(_arg056);
                            return true;
                        case 75:
                            boolean _arg057 = data.readBoolean();
                            int _arg123 = data.readInt();
                            data.enforceNoDataAvail();
                            setBlueLightFilter(_arg057, _arg123);
                            return true;
                        case 76:
                            int _arg058 = data.readInt();
                            data.enforceNoDataAvail();
                            onFlashlightKeyPressed(_arg058);
                            return true;
                        case 77:
                            String _arg059 = data.readString();
                            RemoteViews _arg124 = (RemoteViews) data.readTypedObject(RemoteViews.CREATOR);
                            int _arg213 = data.readInt();
                            int _arg37 = data.readInt();
                            data.enforceNoDataAvail();
                            setNavigationBarShortcut(_arg059, _arg124, _arg213, _arg37);
                            return true;
                        case 78:
                            resetScheduleAutoHide();
                            return true;
                        case 79:
                            int _arg060 = data.readInt();
                            boolean _arg125 = data.readBoolean();
                            Rect _arg214 = (Rect) data.readTypedObject(Rect.CREATOR);
                            data.enforceNoDataAvail();
                            notifySamsungPayInfo(_arg060, _arg125, _arg214);
                            return true;
                        case 80:
                            int _arg061 = data.readInt();
                            data.enforceNoDataAvail();
                            onFocusedDisplayChanged(_arg061);
                            return true;
                        case 81:
                            boolean _arg062 = data.readBoolean();
                            boolean _arg126 = data.readBoolean();
                            data.enforceNoDataAvail();
                            notifyRequestedSystemKey(_arg062, _arg126);
                            return true;
                        case 82:
                            KeyEvent _arg063 = (KeyEvent) data.readTypedObject(KeyEvent.CREATOR);
                            data.enforceNoDataAvail();
                            sendThreeFingerGestureKeyEvent(_arg063);
                            return true;
                        case 83:
                            boolean _arg064 = data.readBoolean();
                            data.enforceNoDataAvail();
                            notifyRequestedGameToolsWin(_arg064);
                            return true;
                        case 84:
                            KeyEvent _arg065 = (KeyEvent) data.readTypedObject(KeyEvent.CREATOR);
                            data.enforceNoDataAvail();
                            sendKeyEventToDesktopTaskbar(_arg065);
                            return true;
                        case 85:
                            boolean _arg066 = data.readBoolean();
                            boolean _arg127 = data.readBoolean();
                            data.enforceNoDataAvail();
                            startSearcleByHomeKey(_arg066, _arg127);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        /* loaded from: classes5.dex */
        private static class Proxy implements IStatusBar {
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

            @Override // com.android.internal.statusbar.IStatusBar
            public void setIcon(String slot, StatusBarIcon icon) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(slot);
                    _data.writeTypedObject(icon, 0);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void removeIcon(String slot) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(slot);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void disable(int displayId, int state1, int state2) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeInt(state1);
                    _data.writeInt(state2);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void animateExpandNotificationsPanel() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void animateExpandSettingsPanel(String subPanel) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(subPanel);
                    this.mRemote.transact(5, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void animateCollapsePanels() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(6, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void togglePanel() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(7, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void showWirelessChargingAnimation(int batteryLevel) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(batteryLevel);
                    this.mRemote.transact(8, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void setImeWindowStatus(int displayId, IBinder token, int vis, int backDisposition, boolean showImeSwitcher) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeStrongBinder(token);
                    _data.writeInt(vis);
                    _data.writeInt(backDisposition);
                    _data.writeBoolean(showImeSwitcher);
                    this.mRemote.transact(9, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void setWindowState(int display, int window, int state) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(display);
                    _data.writeInt(window);
                    _data.writeInt(state);
                    this.mRemote.transact(10, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void showRecentApps(boolean triggeredFromAltTab) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(triggeredFromAltTab);
                    this.mRemote.transact(11, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void hideRecentApps(boolean triggeredFromAltTab, boolean triggeredFromHomeKey) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(triggeredFromAltTab);
                    _data.writeBoolean(triggeredFromHomeKey);
                    this.mRemote.transact(12, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void toggleRecentApps() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(13, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void toggleTaskbar() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(14, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void toggleSplitScreen() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(15, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void preloadRecentApps() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(16, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void cancelPreloadRecentApps() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(17, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void showScreenPinningRequest(int taskId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(taskId);
                    this.mRemote.transact(18, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void dismissKeyboardShortcutsMenu() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(19, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void toggleKeyboardShortcutsMenu(int deviceId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(deviceId);
                    this.mRemote.transact(20, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void appTransitionPending(int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    this.mRemote.transact(21, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void appTransitionCancelled(int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    this.mRemote.transact(22, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void appTransitionStarting(int displayId, long statusBarAnimationsStartTime, long statusBarAnimationsDuration) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeLong(statusBarAnimationsStartTime);
                    _data.writeLong(statusBarAnimationsDuration);
                    this.mRemote.transact(23, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void appTransitionFinished(int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    this.mRemote.transact(24, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void showAssistDisclosure() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(25, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void startAssist(Bundle args) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(args, 0);
                    this.mRemote.transact(26, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void onCameraLaunchGestureDetected(int source) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(source);
                    this.mRemote.transact(27, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void onEmergencyActionLaunchGestureDetected() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(28, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void showPictureInPictureMenu() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(29, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void showGlobalActionsMenu(int sideKeyType) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sideKeyType);
                    this.mRemote.transact(30, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void onProposedRotationChanged(int rotation, boolean isValid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(rotation);
                    _data.writeBoolean(isValid);
                    this.mRemote.transact(31, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void setTopAppHidesStatusBar(boolean hidesStatusBar) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(hidesStatusBar);
                    this.mRemote.transact(32, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void addQsTile(ComponentName tile) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(tile, 0);
                    this.mRemote.transact(33, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void remQsTile(ComponentName tile) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(tile, 0);
                    this.mRemote.transact(34, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void clickQsTile(ComponentName tile) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(tile, 0);
                    this.mRemote.transact(35, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void handleSystemKey(KeyEvent key) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(key, 0);
                    this.mRemote.transact(36, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void showPinningEnterExitToast(boolean entering) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(entering);
                    this.mRemote.transact(37, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void showPinningEscapeToast() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(38, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void showShutdownUi(boolean isReboot, String reason) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(isReboot);
                    _data.writeString(reason);
                    this.mRemote.transact(39, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void showAuthenticationDialog(PromptInfo promptInfo, IBiometricSysuiReceiver sysuiReceiver, int[] sensorIds, boolean credentialAllowed, boolean requireConfirmation, int userId, long operationId, String opPackageName, long requestId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(promptInfo, 0);
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    _data.writeStrongInterface(sysuiReceiver);
                } catch (Throwable th2) {
                    th = th2;
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeIntArray(sensorIds);
                } catch (Throwable th3) {
                    th = th3;
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeBoolean(credentialAllowed);
                    try {
                        _data.writeBoolean(requireConfirmation);
                    } catch (Throwable th4) {
                        th = th4;
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(userId);
                    } catch (Throwable th5) {
                        th = th5;
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeLong(operationId);
                    } catch (Throwable th6) {
                        th = th6;
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th7) {
                    th = th7;
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeString(opPackageName);
                    try {
                        _data.writeLong(requestId);
                    } catch (Throwable th8) {
                        th = th8;
                    }
                    try {
                        this.mRemote.transact(40, _data, null, 1);
                        _data.recycle();
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
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void onBiometricAuthenticated(int modality) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(modality);
                    this.mRemote.transact(41, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void onBiometricHelp(int modality, String message) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(modality);
                    _data.writeString(message);
                    this.mRemote.transact(42, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void onBiometricError(int modality, int error, int vendorCode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(modality);
                    _data.writeInt(error);
                    _data.writeInt(vendorCode);
                    this.mRemote.transact(43, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void hideAuthenticationDialog(long requestId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(requestId);
                    this.mRemote.transact(44, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void setBiometicContextListener(IBiometricContextListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(45, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void setUdfpsRefreshRateCallback(IUdfpsRefreshRateRequestCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(46, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void onDisplayReady(int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    this.mRemote.transact(47, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void onRecentsAnimationStateChanged(boolean running) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(running);
                    this.mRemote.transact(48, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void onSystemBarAttributesChanged(int displayId, int appearance, AppearanceRegion[] appearanceRegions, boolean navbarColorManagedByIme, int behavior, int requestedVisibleTypes, String packageName, LetterboxDetails[] letterboxDetails) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeInt(appearance);
                    _data.writeTypedArray(appearanceRegions, 0);
                    _data.writeBoolean(navbarColorManagedByIme);
                    _data.writeInt(behavior);
                    _data.writeInt(requestedVisibleTypes);
                    _data.writeString(packageName);
                    _data.writeTypedArray(letterboxDetails, 0);
                    this.mRemote.transact(49, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void showTransient(int displayId, int types, boolean isGestureOnSystemBar) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeInt(types);
                    _data.writeBoolean(isGestureOnSystemBar);
                    this.mRemote.transact(50, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void abortTransient(int displayId, int types) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeInt(types);
                    this.mRemote.transact(51, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void showInattentiveSleepWarning() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(52, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void dismissInattentiveSleepWarning(boolean animated) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(animated);
                    this.mRemote.transact(53, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void showToast(int uid, String packageName, IBinder token, CharSequence text, IBinder windowToken, int duration, ITransientNotificationCallback callback, int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeString(packageName);
                    _data.writeStrongBinder(token);
                    if (text != null) {
                        _data.writeInt(1);
                        TextUtils.writeToParcel(text, _data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStrongBinder(windowToken);
                    _data.writeInt(duration);
                    _data.writeStrongInterface(callback);
                    _data.writeInt(displayId);
                    this.mRemote.transact(54, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void hideToast(String packageName, IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeStrongBinder(token);
                    this.mRemote.transact(55, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void startTracing() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(56, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void stopTracing() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(57, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void suppressAmbientDisplay(boolean suppress) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(suppress);
                    this.mRemote.transact(58, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void requestWindowMagnificationConnection(boolean connect) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(connect);
                    this.mRemote.transact(59, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void passThroughShellCommand(String[] args, ParcelFileDescriptor pfd) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStringArray(args);
                    _data.writeTypedObject(pfd, 0);
                    this.mRemote.transact(60, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void setNavigationBarLumaSamplingEnabled(int displayId, boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeBoolean(enable);
                    this.mRemote.transact(61, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void runGcForTest() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(62, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void requestTileServiceListeningState(ComponentName componentName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(componentName, 0);
                    this.mRemote.transact(63, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void requestAddTile(ComponentName componentName, CharSequence appName, CharSequence label, Icon icon, IAddTileResultCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(componentName, 0);
                    if (appName != null) {
                        _data.writeInt(1);
                        TextUtils.writeToParcel(appName, _data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (label != null) {
                        _data.writeInt(1);
                        TextUtils.writeToParcel(label, _data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeTypedObject(icon, 0);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(64, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void cancelRequestAddTile(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    this.mRemote.transact(65, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void updateMediaTapToTransferSenderDisplay(int displayState, MediaRoute2Info routeInfo, IUndoMediaTransferCallback undoCallback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayState);
                    _data.writeTypedObject(routeInfo, 0);
                    _data.writeStrongInterface(undoCallback);
                    this.mRemote.transact(66, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void updateMediaTapToTransferReceiverDisplay(int displayState, MediaRoute2Info routeInfo, Icon appIcon, CharSequence appName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayState);
                    _data.writeTypedObject(routeInfo, 0);
                    _data.writeTypedObject(appIcon, 0);
                    if (appName != null) {
                        _data.writeInt(1);
                        TextUtils.writeToParcel(appName, _data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    this.mRemote.transact(67, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void registerNearbyMediaDevicesProvider(INearbyMediaDevicesProvider provider) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(provider);
                    this.mRemote.transact(68, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void unregisterNearbyMediaDevicesProvider(INearbyMediaDevicesProvider provider) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(provider);
                    this.mRemote.transact(69, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void dumpProto(String[] args, ParcelFileDescriptor pfd) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStringArray(args);
                    _data.writeTypedObject(pfd, 0);
                    this.mRemote.transact(70, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void showRearDisplayDialog(int currentBaseState) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(currentBaseState);
                    this.mRemote.transact(71, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void goToFullscreenFromSplit() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(72, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void enterStageSplitFromRunningApp(boolean leftOrTop) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(leftOrTop);
                    this.mRemote.transact(73, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void showMediaOutputSwitcher(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    this.mRemote.transact(74, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void setBlueLightFilter(boolean on, int intensity) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(on);
                    _data.writeInt(intensity);
                    this.mRemote.transact(75, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void onFlashlightKeyPressed(int key) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(key);
                    this.mRemote.transact(76, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void setNavigationBarShortcut(String requestClass, RemoteViews remoteViews, int position, int priority) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(requestClass);
                    _data.writeTypedObject(remoteViews, 0);
                    _data.writeInt(position);
                    _data.writeInt(priority);
                    this.mRemote.transact(77, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void resetScheduleAutoHide() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(78, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void notifySamsungPayInfo(int displayId, boolean visible, Rect frame) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeBoolean(visible);
                    _data.writeTypedObject(frame, 0);
                    this.mRemote.transact(79, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void onFocusedDisplayChanged(int focusedDisplayId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(focusedDisplayId);
                    this.mRemote.transact(80, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void notifyRequestedSystemKey(boolean isRequestedRecentKey, boolean isRequestedHomeKey) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(isRequestedRecentKey);
                    _data.writeBoolean(isRequestedHomeKey);
                    this.mRemote.transact(81, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void sendThreeFingerGestureKeyEvent(KeyEvent event) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(event, 0);
                    this.mRemote.transact(82, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void notifyRequestedGameToolsWin(boolean attached) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(attached);
                    this.mRemote.transact(83, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void sendKeyEventToDesktopTaskbar(KeyEvent event) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(event, 0);
                    this.mRemote.transact(84, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void startSearcleByHomeKey(boolean down, boolean longPress) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(down);
                    _data.writeBoolean(longPress);
                    this.mRemote.transact(85, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 84;
        }
    }
}