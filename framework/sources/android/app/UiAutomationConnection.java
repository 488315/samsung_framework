package android.app;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.accessibilityservice.IAccessibilityServiceClient;
import android.app.IActivityManager;
import android.app.IUiAutomationConnection;
import android.companion.virtual.VirtualDeviceManager;
import android.content.Context;
import android.graphics.Rect;
import android.hardware.input.InputManagerGlobal;
import android.media.MediaMetrics;
import android.os.Binder;
import android.os.IBinder;
import android.os.ParcelFileDescriptor;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.permission.IPermissionManager;
import android.util.Log;
import android.view.IWindowManager;
import android.view.InputEvent;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceControl;
import android.view.WindowAnimationFrameStats;
import android.view.WindowContentFrameStats;
import android.view.accessibility.IAccessibilityManager;
import android.window.ScreenCapture;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import libcore.io.IoUtils;

/* loaded from: classes.dex */
public final class UiAutomationConnection extends IUiAutomationConnection.Stub {
    private static final int INITIAL_FROZEN_ROTATION_UNSPECIFIED = -1;
    private static final String TAG = "UiAutomationConnection";
    private IAccessibilityServiceClient mClient;
    private boolean mIsShutdown;
    private int mOwningUid;
    private final IWindowManager mWindowManager = IWindowManager.Stub.asInterface(ServiceManager.getService(Context.WINDOW_SERVICE));
    private final IAccessibilityManager mAccessibilityManager = IAccessibilityManager.Stub.asInterface(ServiceManager.getService(Context.ACCESSIBILITY_SERVICE));
    private final IPermissionManager mPermissionManager = IPermissionManager.Stub.asInterface(ServiceManager.getService("permissionmgr"));
    private final IActivityManager mActivityManager = IActivityManager.Stub.asInterface(ServiceManager.getService("activity"));
    private final Object mLock = new Object();
    private final Binder mToken = new Binder();
    private int mInitialFrozenRotation = -1;

    public UiAutomationConnection() {
        Log.d(TAG, "Created on user " + Process.myUserHandle());
    }

    @Override // android.app.IUiAutomationConnection
    public void connect(IAccessibilityServiceClient client, int flags) {
        if (client == null) {
            throw new IllegalArgumentException("Client cannot be null!");
        }
        synchronized (this.mLock) {
            throwIfShutdownLocked();
            if (isConnectedLocked()) {
                throw new IllegalStateException("Already connected.");
            }
            this.mOwningUid = Binder.getCallingUid();
            registerUiTestAutomationServiceLocked(client, Binder.getCallingUserHandle().getIdentifier(), flags);
            storeRotationStateLocked();
        }
    }

    @Override // android.app.IUiAutomationConnection
    public void disconnect() {
        synchronized (this.mLock) {
            throwIfCalledByNotTrustedUidLocked();
            throwIfShutdownLocked();
            if (!isConnectedLocked()) {
                throw new IllegalStateException("Already disconnected.");
            }
            this.mOwningUid = -1;
            unregisterUiTestAutomationServiceLocked();
            restoreRotationStateLocked();
        }
    }

    @Override // android.app.IUiAutomationConnection
    public boolean injectInputEvent(InputEvent inputEvent, boolean z, boolean z2) {
        boolean z3;
        boolean z4;
        synchronized (this.mLock) {
            throwIfCalledByNotTrustedUidLocked();
            throwIfShutdownLocked();
            throwIfNotConnectedLocked();
        }
        if (inputEvent instanceof KeyEvent) {
            KeyEvent keyEvent = (KeyEvent) inputEvent;
            boolean z5 = keyEvent.getAction() == 0;
            z3 = keyEvent.getAction() == 1;
            z4 = z5;
        } else {
            MotionEvent motionEvent = (MotionEvent) inputEvent;
            boolean z6 = motionEvent.getAction() == 0 || motionEvent.isFromSource(8194);
            z3 = motionEvent.getAction() == 1;
            z4 = z6;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        if (z4) {
            try {
                this.mWindowManager.syncInputTransactions(z2);
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
                return false;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        boolean injectInputEvent = InputManagerGlobal.getInstance().injectInputEvent(inputEvent, z ? 2 : 0);
        if (z3 != null) {
            this.mWindowManager.syncInputTransactions(z2);
        }
        return injectInputEvent;
    }

    @Override // android.app.IUiAutomationConnection
    public void injectInputEventToInputFilter(InputEvent event) throws RemoteException {
        synchronized (this.mLock) {
            throwIfCalledByNotTrustedUidLocked();
            throwIfShutdownLocked();
            throwIfNotConnectedLocked();
        }
        this.mAccessibilityManager.injectInputEventToInputFilter(event);
    }

    @Override // android.app.IUiAutomationConnection
    public void syncInputTransactions(boolean waitForAnimations) {
        synchronized (this.mLock) {
            throwIfCalledByNotTrustedUidLocked();
            throwIfShutdownLocked();
            throwIfNotConnectedLocked();
        }
        try {
            this.mWindowManager.syncInputTransactions(waitForAnimations);
        } catch (RemoteException e) {
        }
    }

    @Override // android.app.IUiAutomationConnection
    public boolean setRotation(int rotation) {
        synchronized (this.mLock) {
            throwIfCalledByNotTrustedUidLocked();
            throwIfShutdownLocked();
            throwIfNotConnectedLocked();
        }
        long identity = Binder.clearCallingIdentity();
        try {
            if (rotation == -2) {
                this.mWindowManager.thawRotation("UiAutomationConnection#setRotation");
            } else {
                this.mWindowManager.freezeRotation(rotation, "UiAutomationConnection#setRotation");
            }
            Binder.restoreCallingIdentity(identity);
            return true;
        } catch (RemoteException e) {
            Binder.restoreCallingIdentity(identity);
            return false;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(identity);
            throw th;
        }
    }

    @Override // android.app.IUiAutomationConnection
    public boolean takeScreenshot(Rect crop, ScreenCapture.ScreenCaptureListener listener) {
        synchronized (this.mLock) {
            throwIfCalledByNotTrustedUidLocked();
            throwIfShutdownLocked();
            throwIfNotConnectedLocked();
        }
        long identity = Binder.clearCallingIdentity();
        try {
            try {
                ScreenCapture.CaptureArgs captureArgs = new ScreenCapture.CaptureArgs.Builder().setSourceCrop(crop).build();
                this.mWindowManager.captureDisplay(0, captureArgs, listener);
            } catch (RemoteException re) {
                re.rethrowAsRuntimeException();
            }
            Binder.restoreCallingIdentity(identity);
            return true;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(identity);
            throw th;
        }
    }

    @Override // android.app.IUiAutomationConnection
    public boolean takeSurfaceControlScreenshot(SurfaceControl surfaceControl, ScreenCapture.ScreenCaptureListener listener) {
        synchronized (this.mLock) {
            throwIfCalledByNotTrustedUidLocked();
            throwIfShutdownLocked();
            throwIfNotConnectedLocked();
        }
        long identity = Binder.clearCallingIdentity();
        try {
            ScreenCapture.LayerCaptureArgs args = new ScreenCapture.LayerCaptureArgs.Builder(surfaceControl).setChildrenOnly(false).build();
            int status = ScreenCapture.captureLayers(args, listener);
            if (status != 0) {
                return false;
            }
            Binder.restoreCallingIdentity(identity);
            return true;
        } finally {
            Binder.restoreCallingIdentity(identity);
        }
    }

    @Override // android.app.IUiAutomationConnection
    public boolean clearWindowContentFrameStats(int windowId) throws RemoteException {
        synchronized (this.mLock) {
            throwIfCalledByNotTrustedUidLocked();
            throwIfShutdownLocked();
            throwIfNotConnectedLocked();
        }
        int callingUserId = UserHandle.getCallingUserId();
        long identity = Binder.clearCallingIdentity();
        try {
            IBinder token = this.mAccessibilityManager.getWindowToken(windowId, callingUserId);
            if (token != null) {
                return this.mWindowManager.clearWindowContentFrameStats(token);
            }
            Binder.restoreCallingIdentity(identity);
            return false;
        } finally {
            Binder.restoreCallingIdentity(identity);
        }
    }

    @Override // android.app.IUiAutomationConnection
    public WindowContentFrameStats getWindowContentFrameStats(int windowId) throws RemoteException {
        synchronized (this.mLock) {
            throwIfCalledByNotTrustedUidLocked();
            throwIfShutdownLocked();
            throwIfNotConnectedLocked();
        }
        int callingUserId = UserHandle.getCallingUserId();
        long identity = Binder.clearCallingIdentity();
        try {
            IBinder token = this.mAccessibilityManager.getWindowToken(windowId, callingUserId);
            if (token != null) {
                return this.mWindowManager.getWindowContentFrameStats(token);
            }
            Binder.restoreCallingIdentity(identity);
            return null;
        } finally {
            Binder.restoreCallingIdentity(identity);
        }
    }

    @Override // android.app.IUiAutomationConnection
    public void clearWindowAnimationFrameStats() {
        synchronized (this.mLock) {
            throwIfCalledByNotTrustedUidLocked();
            throwIfShutdownLocked();
            throwIfNotConnectedLocked();
        }
        long identity = Binder.clearCallingIdentity();
        try {
            SurfaceControl.clearAnimationFrameStats();
        } finally {
            Binder.restoreCallingIdentity(identity);
        }
    }

    @Override // android.app.IUiAutomationConnection
    public WindowAnimationFrameStats getWindowAnimationFrameStats() {
        synchronized (this.mLock) {
            throwIfCalledByNotTrustedUidLocked();
            throwIfShutdownLocked();
            throwIfNotConnectedLocked();
        }
        long identity = Binder.clearCallingIdentity();
        try {
            WindowAnimationFrameStats stats = new WindowAnimationFrameStats();
            SurfaceControl.getAnimationFrameStats(stats);
            return stats;
        } finally {
            Binder.restoreCallingIdentity(identity);
        }
    }

    @Override // android.app.IUiAutomationConnection
    public void grantRuntimePermission(String packageName, String permission, int userId) throws RemoteException {
        synchronized (this.mLock) {
            throwIfCalledByNotTrustedUidLocked();
            throwIfShutdownLocked();
            throwIfNotConnectedLocked();
        }
        long identity = Binder.clearCallingIdentity();
        try {
            this.mPermissionManager.grantRuntimePermission(packageName, permission, VirtualDeviceManager.PERSISTENT_DEVICE_ID_DEFAULT, userId);
        } finally {
            Binder.restoreCallingIdentity(identity);
        }
    }

    @Override // android.app.IUiAutomationConnection
    public void revokeRuntimePermission(String packageName, String permission, int userId) throws RemoteException {
        synchronized (this.mLock) {
            throwIfCalledByNotTrustedUidLocked();
            throwIfShutdownLocked();
            throwIfNotConnectedLocked();
        }
        long identity = Binder.clearCallingIdentity();
        try {
            this.mPermissionManager.revokeRuntimePermission(packageName, permission, VirtualDeviceManager.PERSISTENT_DEVICE_ID_DEFAULT, userId, null);
        } finally {
            Binder.restoreCallingIdentity(identity);
        }
    }

    @Override // android.app.IUiAutomationConnection
    public void adoptShellPermissionIdentity(int uid, String[] permissions) throws RemoteException {
        synchronized (this.mLock) {
            throwIfCalledByNotTrustedUidLocked();
            throwIfShutdownLocked();
            throwIfNotConnectedLocked();
        }
        long identity = Binder.clearCallingIdentity();
        try {
            this.mActivityManager.startDelegateShellPermissionIdentity(uid, permissions);
        } finally {
            Binder.restoreCallingIdentity(identity);
        }
    }

    @Override // android.app.IUiAutomationConnection
    public void dropShellPermissionIdentity() throws RemoteException {
        synchronized (this.mLock) {
            throwIfCalledByNotTrustedUidLocked();
            throwIfShutdownLocked();
            throwIfNotConnectedLocked();
        }
        long identity = Binder.clearCallingIdentity();
        try {
            this.mActivityManager.stopDelegateShellPermissionIdentity();
        } finally {
            Binder.restoreCallingIdentity(identity);
        }
    }

    @Override // android.app.IUiAutomationConnection
    public List<String> getAdoptedShellPermissions() throws RemoteException {
        synchronized (this.mLock) {
            throwIfCalledByNotTrustedUidLocked();
            throwIfShutdownLocked();
            throwIfNotConnectedLocked();
        }
        long identity = Binder.clearCallingIdentity();
        try {
            return this.mActivityManager.getDelegatedShellPermissions();
        } finally {
            Binder.restoreCallingIdentity(identity);
        }
    }

    @Override // android.app.IUiAutomationConnection
    public void addOverridePermissionState(int uid, String permission, int result) throws RemoteException {
        synchronized (this.mLock) {
            throwIfCalledByNotTrustedUidLocked();
            throwIfShutdownLocked();
            throwIfNotConnectedLocked();
        }
        int callingUid = Binder.getCallingUid();
        long identity = Binder.clearCallingIdentity();
        try {
            this.mActivityManager.addOverridePermissionState(callingUid, uid, permission, result);
        } finally {
            Binder.restoreCallingIdentity(identity);
        }
    }

    @Override // android.app.IUiAutomationConnection
    public void removeOverridePermissionState(int uid, String permission) throws RemoteException {
        synchronized (this.mLock) {
            throwIfCalledByNotTrustedUidLocked();
            throwIfShutdownLocked();
            throwIfNotConnectedLocked();
        }
        int callingUid = Binder.getCallingUid();
        long identity = Binder.clearCallingIdentity();
        try {
            this.mActivityManager.removeOverridePermissionState(callingUid, uid, permission);
        } finally {
            Binder.restoreCallingIdentity(identity);
        }
    }

    @Override // android.app.IUiAutomationConnection
    public void clearOverridePermissionStates(int uid) throws RemoteException {
        synchronized (this.mLock) {
            throwIfCalledByNotTrustedUidLocked();
            throwIfShutdownLocked();
            throwIfNotConnectedLocked();
        }
        int callingUid = Binder.getCallingUid();
        long identity = Binder.clearCallingIdentity();
        try {
            this.mActivityManager.clearOverridePermissionStates(callingUid, uid);
        } finally {
            Binder.restoreCallingIdentity(identity);
        }
    }

    @Override // android.app.IUiAutomationConnection
    public void clearAllOverridePermissionStates() throws RemoteException {
        synchronized (this.mLock) {
            throwIfCalledByNotTrustedUidLocked();
            throwIfShutdownLocked();
            throwIfNotConnectedLocked();
        }
        int callingUid = Binder.getCallingUid();
        long identity = Binder.clearCallingIdentity();
        try {
            this.mActivityManager.clearAllOverridePermissionStates(callingUid);
        } finally {
            Binder.restoreCallingIdentity(identity);
        }
    }

    public class Repeater implements Runnable {
        private final InputStream readFrom;
        private final OutputStream writeTo;

        public Repeater(InputStream readFrom, OutputStream writeTo) {
            this.readFrom = readFrom;
            this.writeTo = writeTo;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                byte[] buffer = new byte[8192];
                while (true) {
                    int readByteCount = this.readFrom.read(buffer);
                    if (readByteCount < 0) {
                        break;
                    }
                    this.writeTo.write(buffer, 0, readByteCount);
                    this.writeTo.flush();
                }
            } catch (IOException e) {
            } catch (Throwable th) {
                IoUtils.closeQuietly(this.readFrom);
                IoUtils.closeQuietly(this.writeTo);
                throw th;
            }
            IoUtils.closeQuietly(this.readFrom);
            IoUtils.closeQuietly(this.writeTo);
        }
    }

    @Override // android.app.IUiAutomationConnection
    public void executeShellCommand(String command, ParcelFileDescriptor sink, ParcelFileDescriptor source) throws RemoteException {
        executeShellCommandWithStderr(command, sink, source, null);
    }

    @Override // android.app.IUiAutomationConnection
    public void executeShellCommandWithStderr(String command, final ParcelFileDescriptor sink, final ParcelFileDescriptor source, final ParcelFileDescriptor stderrSink) throws RemoteException {
        Thread readFromProcess;
        Thread writeToProcess;
        Thread readStderrFromProcess;
        synchronized (this.mLock) {
            throwIfCalledByNotTrustedUidLocked();
            throwIfShutdownLocked();
            throwIfNotConnectedLocked();
        }
        try {
            final Process process = Runtime.getRuntime().exec(command);
            if (sink != null) {
                InputStream sink_in = process.getInputStream();
                OutputStream sink_out = new FileOutputStream(sink.getFileDescriptor());
                Thread readFromProcess2 = new Thread(new Repeater(sink_in, sink_out));
                readFromProcess2.start();
                readFromProcess = readFromProcess2;
            } else {
                readFromProcess = null;
            }
            if (source != null) {
                OutputStream source_out = process.getOutputStream();
                InputStream source_in = new FileInputStream(source.getFileDescriptor());
                Thread writeToProcess2 = new Thread(new Repeater(source_in, source_out));
                writeToProcess2.start();
                writeToProcess = writeToProcess2;
            } else {
                writeToProcess = null;
            }
            if (stderrSink != null) {
                InputStream sink_in2 = process.getErrorStream();
                OutputStream sink_out2 = new FileOutputStream(stderrSink.getFileDescriptor());
                Thread readStderrFromProcess2 = new Thread(new Repeater(sink_in2, sink_out2));
                readStderrFromProcess2.start();
                readStderrFromProcess = readStderrFromProcess2;
            } else {
                readStderrFromProcess = null;
            }
            final Thread thread = writeToProcess;
            final Thread thread2 = readFromProcess;
            final Thread thread3 = readStderrFromProcess;
            Thread cleanup = new Thread(new Runnable() { // from class: android.app.UiAutomationConnection.1
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (thread != null) {
                            thread.join();
                        }
                        if (thread2 != null) {
                            thread2.join();
                        }
                        if (thread3 != null) {
                            thread3.join();
                        }
                    } catch (InterruptedException e) {
                        Log.e(UiAutomationConnection.TAG, "At least one of the threads was interrupted");
                    }
                    IoUtils.closeQuietly(sink);
                    IoUtils.closeQuietly(source);
                    IoUtils.closeQuietly(stderrSink);
                    process.destroy();
                }
            });
            cleanup.start();
        } catch (IOException exc) {
            throw new RuntimeException("Error running shell command '" + command + "'", exc);
        }
    }

    @Override // android.app.IUiAutomationConnection
    public void shutdown() {
        synchronized (this.mLock) {
            if (isConnectedLocked()) {
                throwIfCalledByNotTrustedUidLocked();
            }
            throwIfShutdownLocked();
            this.mIsShutdown = true;
            if (isConnectedLocked()) {
                disconnect();
            }
        }
    }

    private void registerUiTestAutomationServiceLocked(IAccessibilityServiceClient client, int userId, int flags) {
        IAccessibilityManager manager = IAccessibilityManager.Stub.asInterface(ServiceManager.getService(Context.ACCESSIBILITY_SERVICE));
        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
        info.eventTypes = -1;
        info.feedbackType = 16;
        info.flags |= 65554;
        info.setCapabilities(11);
        if ((flags & 4) == 0) {
            info.setAccessibilityTool(true);
        }
        try {
            manager.registerUiTestAutomationService(this.mToken, client, info, userId, flags);
            this.mClient = client;
        } catch (RemoteException re) {
            throw new IllegalStateException("Error while registering UiTestAutomationService for user " + userId + MediaMetrics.SEPARATOR, re);
        }
    }

    private void unregisterUiTestAutomationServiceLocked() {
        IAccessibilityManager manager = IAccessibilityManager.Stub.asInterface(ServiceManager.getService(Context.ACCESSIBILITY_SERVICE));
        try {
            manager.unregisterUiTestAutomationService(this.mClient);
            this.mClient = null;
        } catch (RemoteException re) {
            throw new IllegalStateException("Error while unregistering UiTestAutomationService", re);
        }
    }

    private void storeRotationStateLocked() {
        try {
            if (this.mWindowManager.isRotationFrozen()) {
                this.mInitialFrozenRotation = this.mWindowManager.getDefaultDisplayRotation();
            }
        } catch (RemoteException e) {
        }
    }

    private void restoreRotationStateLocked() {
        try {
            if (this.mInitialFrozenRotation != -1) {
                this.mWindowManager.freezeRotation(this.mInitialFrozenRotation, "UiAutomationConnection#restoreRotationStateLocked");
            } else {
                this.mWindowManager.thawRotation("UiAutomationConnection#restoreRotationStateLocked");
            }
        } catch (RemoteException e) {
        }
    }

    private boolean isConnectedLocked() {
        return this.mClient != null;
    }

    private void throwIfShutdownLocked() {
        if (this.mIsShutdown) {
            throw new IllegalStateException("Connection shutdown!");
        }
    }

    private void throwIfNotConnectedLocked() {
        if (!isConnectedLocked()) {
            throw new IllegalStateException("Not connected!");
        }
    }

    private void throwIfCalledByNotTrustedUidLocked() {
        int callingUid = Binder.getCallingUid();
        if (callingUid != this.mOwningUid && this.mOwningUid != 1000 && callingUid != 0) {
            throw new SecurityException("Calling from not trusted UID!");
        }
    }
}
