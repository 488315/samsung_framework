package android.app;

import android.accessibilityservice.AccessibilityGestureEvent;
import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.accessibilityservice.IAccessibilityServiceClient;
import android.accessibilityservice.IAccessibilityServiceConnection;
import android.accessibilityservice.MagnificationConfig;
import android.app.UiAutomation;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Region;
import android.hardware.HardwareBuffer;
import android.hardware.display.DisplayManagerGlobal;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.os.Process;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.ArraySet;
import android.util.Log;
import android.util.SparseArray;
import android.view.Display;
import android.view.InputEvent;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceControl;
import android.view.View;
import android.view.ViewRootImpl;
import android.view.Window;
import android.view.WindowAnimationFrameStats;
import android.view.WindowContentFrameStats;
import android.view.accessibility.AccessibilityCache;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityInteractionClient;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityWindowInfo;
import android.view.inputmethod.EditorInfo;
import android.window.ScreenCapture;
import com.android.internal.inputmethod.IAccessibilityInputMethodSessionCallback;
import com.android.internal.inputmethod.RemoteAccessibilityInputConnection;
import com.android.internal.util.Preconditions;
import com.android.internal.util.function.pooled.PooledLambda;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeoutException;
import java.util.function.BiConsumer;
import libcore.io.IoUtils;

/* loaded from: classes.dex */
public final class UiAutomation {
    private static final int CONNECTION_ID_UNDEFINED = -1;
    private static final long CONNECT_TIMEOUT_MILLIS = 5000;
    private static final boolean DEBUG = false;
    public static final int FLAG_DONT_SUPPRESS_ACCESSIBILITY_SERVICES = 1;
    public static final int FLAG_DONT_USE_ACCESSIBILITY = 2;
    public static final int FLAG_NOT_ACCESSIBILITY_TOOL = 4;
    public static final int ROTATION_FREEZE_0 = 0;
    public static final int ROTATION_FREEZE_180 = 2;
    public static final int ROTATION_FREEZE_270 = 3;
    public static final int ROTATION_FREEZE_90 = 1;
    public static final int ROTATION_FREEZE_CURRENT = -1;
    public static final int ROTATION_UNFREEZE = -2;
    private static final boolean VERBOSE = false;
    private IAccessibilityServiceClient mClient;
    private int mConnectionId;
    private int mConnectionState;
    private final int mDisplayId;
    private final ArrayList<AccessibilityEvent> mEventQueue;
    private int mFlags;
    private int mGenerationId;
    private boolean mIsDestroyed;
    private long mLastEventTimeMillis;
    private final Handler mLocalCallbackHandler;
    private final Object mLock;
    private OnAccessibilityEventListener mOnAccessibilityEventListener;
    private HandlerThread mRemoteCallbackThread;
    private final IUiAutomationConnection mUiAutomationConnection;
    private boolean mWaitingForEventDelivery;
    private static final String LOG_TAG = UiAutomation.class.getSimpleName();
    public static final Set<String> ALL_PERMISSIONS = Set.of("_ALL_PERMISSIONS_");

    public interface AccessibilityEventFilter {
        boolean accept(AccessibilityEvent accessibilityEvent);
    }

    @Retention(RetentionPolicy.SOURCE)
    private @interface ConnectionState {
        public static final int CONNECTED = 2;
        public static final int CONNECTING = 1;
        public static final int DISCONNECTED = 0;
        public static final int FAILED = 3;
    }

    public interface OnAccessibilityEventListener {
        void onAccessibilityEvent(AccessibilityEvent accessibilityEvent);
    }

    public UiAutomation(Context context, IUiAutomationConnection connection) {
        this(getDisplayId(context), context.getMainLooper(), connection);
    }

    @Deprecated
    public UiAutomation(Looper looper, IUiAutomationConnection connection) {
        this(0, looper, connection);
        Log.w(LOG_TAG, "Created with deprecatead constructor, assumes DEFAULT_DISPLAY");
    }

    private UiAutomation(int displayId, Looper looper, IUiAutomationConnection connection) {
        boolean z;
        this.mLock = new Object();
        this.mEventQueue = new ArrayList<>();
        this.mConnectionId = -1;
        this.mConnectionState = 0;
        this.mGenerationId = 0;
        if (looper == null) {
            z = false;
        } else {
            z = true;
        }
        Preconditions.checkArgument(z, "Looper cannot be null!");
        Preconditions.checkArgument(connection != null, "Connection cannot be null!");
        this.mLocalCallbackHandler = new Handler(looper);
        this.mUiAutomationConnection = connection;
        this.mDisplayId = displayId;
        Log.i(LOG_TAG, "Initialized for user " + Process.myUserHandle().getIdentifier() + " on display " + this.mDisplayId);
    }

    public void connect() {
        try {
            connectWithTimeout(0, 5000L);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    public void connect(int flags) {
        try {
            connectWithTimeout(flags, 5000L);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    public void connectWithTimeout(int flags, long timeoutMillis) throws TimeoutException {
        synchronized (this.mLock) {
            throwIfConnectedLocked();
            if (this.mConnectionState == 1) {
                return;
            }
            this.mConnectionState = 1;
            this.mRemoteCallbackThread = new HandlerThread("UiAutomation");
            this.mRemoteCallbackThread.start();
            Looper looper = this.mRemoteCallbackThread.getLooper();
            int i = this.mGenerationId + 1;
            this.mGenerationId = i;
            this.mClient = new IAccessibilityServiceClientImpl(looper, i);
            try {
                this.mUiAutomationConnection.connect(this.mClient, flags);
                this.mFlags = flags;
                if (!useAccessibility()) {
                    this.mConnectionState = 0;
                    return;
                }
                synchronized (this.mLock) {
                    long startTimeMillis = SystemClock.uptimeMillis();
                    while (this.mConnectionState != 2) {
                        long elapsedTimeMillis = SystemClock.uptimeMillis() - startTimeMillis;
                        long remainingTimeMillis = timeoutMillis - elapsedTimeMillis;
                        if (remainingTimeMillis <= 0) {
                            this.mConnectionState = 3;
                            throw new TimeoutException("Timeout while connecting " + this);
                        }
                        try {
                            this.mLock.wait(remainingTimeMillis);
                        } catch (InterruptedException e) {
                        }
                    }
                }
            } catch (RemoteException re) {
                throw new RuntimeException("Error while connecting " + this, re);
            }
        }
    }

    public int getFlags() {
        return this.mFlags;
    }

    public void disconnect() {
        synchronized (this.mLock) {
            if (this.mConnectionState == 1) {
                throw new IllegalStateException("Cannot call disconnect() while connecting " + this);
            }
            if (useAccessibility() && this.mConnectionState == 0) {
                return;
            }
            this.mConnectionState = 0;
            this.mConnectionId = -1;
            this.mGenerationId++;
            try {
                try {
                    this.mUiAutomationConnection.disconnect();
                } catch (RemoteException re) {
                    throw new RuntimeException("Error while disconnecting " + this, re);
                }
            } finally {
                if (this.mRemoteCallbackThread != null) {
                    this.mRemoteCallbackThread.quit();
                    this.mRemoteCallbackThread = null;
                }
            }
        }
    }

    public int getConnectionId() {
        int i;
        synchronized (this.mLock) {
            throwIfNotConnectedLocked();
            i = this.mConnectionId;
        }
        return i;
    }

    public boolean isDestroyed() {
        return this.mIsDestroyed;
    }

    public void setOnAccessibilityEventListener(OnAccessibilityEventListener listener) {
        synchronized (this.mLock) {
            throwIfNotConnectedLocked();
            this.mOnAccessibilityEventListener = listener;
        }
    }

    public void destroy() {
        disconnect();
        this.mIsDestroyed = true;
    }

    public boolean clearCache() {
        int connectionId;
        synchronized (this.mLock) {
            throwIfNotConnectedLocked();
            connectionId = this.mConnectionId;
        }
        AccessibilityCache cache = AccessibilityInteractionClient.getCache(connectionId);
        if (cache == null) {
            return false;
        }
        cache.clear();
        return true;
    }

    public boolean isNodeInCache(AccessibilityNodeInfo node) {
        int connectionId;
        synchronized (this.mLock) {
            throwIfNotConnectedLocked();
            connectionId = this.mConnectionId;
        }
        AccessibilityCache cache = AccessibilityInteractionClient.getCache(connectionId);
        if (cache == null) {
            return false;
        }
        return cache.isNodeInCache(node);
    }

    public AccessibilityCache getCache() {
        int connectionId;
        synchronized (this.mLock) {
            throwIfNotConnectedLocked();
            connectionId = this.mConnectionId;
        }
        return AccessibilityInteractionClient.getCache(connectionId);
    }

    public void adoptShellPermissionIdentity() {
        try {
            this.mUiAutomationConnection.adoptShellPermissionIdentity(Process.myUid(), null);
        } catch (RemoteException re) {
            throw re.rethrowFromSystemServer();
        }
    }

    public void adoptShellPermissionIdentity(String... permissions) {
        try {
            this.mUiAutomationConnection.adoptShellPermissionIdentity(Process.myUid(), permissions);
        } catch (RemoteException re) {
            throw re.rethrowFromSystemServer();
        }
    }

    public void dropShellPermissionIdentity() {
        try {
            this.mUiAutomationConnection.dropShellPermissionIdentity();
        } catch (RemoteException re) {
            throw re.rethrowFromSystemServer();
        }
    }

    public Set<String> getAdoptedShellPermissions() {
        try {
            List<String> permissions = this.mUiAutomationConnection.getAdoptedShellPermissions();
            return permissions == null ? ALL_PERMISSIONS : new ArraySet(permissions);
        } catch (RemoteException re) {
            throw re.rethrowFromSystemServer();
        }
    }

    public void addOverridePermissionState(int uid, String permission, int result) {
        try {
            this.mUiAutomationConnection.addOverridePermissionState(uid, permission, result);
        } catch (RemoteException re) {
            re.rethrowFromSystemServer();
        }
    }

    public void removeOverridePermissionState(int uid, String permission) {
        try {
            this.mUiAutomationConnection.removeOverridePermissionState(uid, permission);
        } catch (RemoteException re) {
            re.rethrowFromSystemServer();
        }
    }

    public void clearOverridePermissionStates(int uid) {
        try {
            this.mUiAutomationConnection.clearOverridePermissionStates(uid);
        } catch (RemoteException re) {
            re.rethrowFromSystemServer();
        }
    }

    public void clearAllOverridePermissionStates() {
        try {
            this.mUiAutomationConnection.clearAllOverridePermissionStates();
        } catch (RemoteException re) {
            re.rethrowFromSystemServer();
        }
    }

    public final boolean performGlobalAction(int action) {
        IAccessibilityServiceConnection connection;
        synchronized (this.mLock) {
            throwIfNotConnectedLocked();
            AccessibilityInteractionClient.getInstance();
            connection = AccessibilityInteractionClient.getConnection(this.mConnectionId);
        }
        if (connection != null) {
            try {
                return connection.performGlobalAction(action);
            } catch (RemoteException re) {
                Log.w(LOG_TAG, "Error while calling performGlobalAction", re);
                return false;
            }
        }
        return false;
    }

    public AccessibilityNodeInfo findFocus(int focus) {
        synchronized (this.mLock) {
            throwIfNotConnectedLocked();
        }
        return AccessibilityInteractionClient.getInstance().findFocus(this.mConnectionId, -2, AccessibilityNodeInfo.ROOT_NODE_ID, focus);
    }

    public final AccessibilityServiceInfo getServiceInfo() {
        IAccessibilityServiceConnection connection;
        synchronized (this.mLock) {
            throwIfNotConnectedLocked();
            AccessibilityInteractionClient.getInstance();
            connection = AccessibilityInteractionClient.getConnection(this.mConnectionId);
        }
        if (connection != null) {
            try {
                return connection.getServiceInfo();
            } catch (RemoteException re) {
                Log.w(LOG_TAG, "Error while getting AccessibilityServiceInfo", re);
                return null;
            }
        }
        return null;
    }

    public final void setServiceInfo(AccessibilityServiceInfo info) {
        IAccessibilityServiceConnection connection;
        synchronized (this.mLock) {
            throwIfNotConnectedLocked();
            AccessibilityInteractionClient.getInstance().clearCache(this.mConnectionId);
            AccessibilityInteractionClient.getInstance();
            connection = AccessibilityInteractionClient.getConnection(this.mConnectionId);
        }
        if (connection != null) {
            try {
                connection.setServiceInfo(info);
            } catch (RemoteException re) {
                Log.w(LOG_TAG, "Error while setting AccessibilityServiceInfo", re);
            }
        }
    }

    public List<AccessibilityWindowInfo> getWindows() {
        int connectionId;
        synchronized (this.mLock) {
            throwIfNotConnectedLocked();
            connectionId = this.mConnectionId;
        }
        return AccessibilityInteractionClient.getInstance().getWindowsOnDisplay(connectionId, this.mDisplayId);
    }

    public SparseArray<List<AccessibilityWindowInfo>> getWindowsOnAllDisplays() {
        int connectionId;
        synchronized (this.mLock) {
            throwIfNotConnectedLocked();
            connectionId = this.mConnectionId;
        }
        return AccessibilityInteractionClient.getInstance().getWindowsOnAllDisplays(connectionId);
    }

    public AccessibilityNodeInfo getRootInActiveWindow() {
        return getRootInActiveWindow(4);
    }

    public AccessibilityNodeInfo getRootInActiveWindow(int prefetchingStrategy) {
        int connectionId;
        synchronized (this.mLock) {
            throwIfNotConnectedLocked();
            connectionId = this.mConnectionId;
        }
        return AccessibilityInteractionClient.getInstance().getRootInActiveWindow(connectionId, prefetchingStrategy);
    }

    public boolean injectInputEvent(InputEvent event, boolean sync) {
        return injectInputEvent(event, sync, true);
    }

    public boolean injectInputEvent(InputEvent event, boolean sync, boolean waitForAnimations) {
        try {
            return this.mUiAutomationConnection.injectInputEvent(event, sync, waitForAnimations);
        } catch (RemoteException re) {
            Log.e(LOG_TAG, "Error while injecting input event!", re);
            return false;
        }
    }

    public void injectInputEventToInputFilter(InputEvent event) {
        try {
            this.mUiAutomationConnection.injectInputEventToInputFilter(event);
        } catch (RemoteException re) {
            Log.e(LOG_TAG, "Error while injecting input event to input filter", re);
        }
    }

    public void setAnimationScale(float scale) {
        AccessibilityInteractionClient.getInstance();
        IAccessibilityServiceConnection connection = AccessibilityInteractionClient.getConnection(this.mConnectionId);
        if (connection != null) {
            try {
                connection.setAnimationScale(scale);
            } catch (RemoteException re) {
                throw new RuntimeException(re);
            }
        }
    }

    public void syncInputTransactions() {
        try {
            this.mUiAutomationConnection.syncInputTransactions(true);
        } catch (RemoteException re) {
            Log.e(LOG_TAG, "Error while syncing input transactions!", re);
        }
    }

    public void syncInputTransactions(boolean waitForAnimations) {
        try {
            this.mUiAutomationConnection.syncInputTransactions(waitForAnimations);
        } catch (RemoteException re) {
            Log.e(LOG_TAG, "Error while syncing input transactions!", re);
        }
    }

    public boolean setRotation(int rotation) {
        switch (rotation) {
            case -2:
            case -1:
            case 0:
            case 1:
            case 2:
            case 3:
                try {
                    this.mUiAutomationConnection.setRotation(rotation);
                    return true;
                } catch (RemoteException re) {
                    Log.e(LOG_TAG, "Error while setting rotation!", re);
                    return false;
                }
            default:
                throw new IllegalArgumentException("Invalid rotation.");
        }
    }

    /* JADX WARN: Finally extract failed */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00ef A[LOOP:3: B:49:0x00ed->B:50:0x00ef, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00fe  */
    /* JADX WARN: Type inference failed for: r4v1 */
    /* JADX WARN: Type inference failed for: r4v10 */
    /* JADX WARN: Type inference failed for: r4v8, types: [boolean, int] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.view.accessibility.AccessibilityEvent executeAndWaitForEvent(java.lang.Runnable r20, android.app.UiAutomation.AccessibilityEventFilter r21, long r22) throws java.util.concurrent.TimeoutException {
        /*
            Method dump skipped, instructions count: 279
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.UiAutomation.executeAndWaitForEvent(java.lang.Runnable, android.app.UiAutomation$AccessibilityEventFilter, long):android.view.accessibility.AccessibilityEvent");
    }

    public void waitForIdle(long idleTimeoutMillis, long globalTimeoutMillis) throws TimeoutException {
        synchronized (this.mLock) {
            throwIfNotConnectedLocked();
            long startTimeMillis = SystemClock.uptimeMillis();
            long elapsedIdleTimeMillis = 0;
            if (this.mLastEventTimeMillis <= 0) {
                this.mLastEventTimeMillis = startTimeMillis;
            }
            while (true) {
                long currentTimeMillis = SystemClock.uptimeMillis();
                long elapsedGlobalTimeMillis = currentTimeMillis - startTimeMillis;
                long remainingGlobalTimeMillis = globalTimeoutMillis - elapsedGlobalTimeMillis;
                if (remainingGlobalTimeMillis <= elapsedIdleTimeMillis) {
                    throw new TimeoutException("No idle state with idle timeout: " + idleTimeoutMillis + " within global timeout: " + globalTimeoutMillis);
                }
                long elapsedIdleTimeMillis2 = currentTimeMillis - this.mLastEventTimeMillis;
                long startTimeMillis2 = startTimeMillis;
                long startTimeMillis3 = idleTimeoutMillis - elapsedIdleTimeMillis2;
                if (startTimeMillis3 > 0) {
                    try {
                        this.mLock.wait(startTimeMillis3);
                    } catch (InterruptedException e) {
                    }
                    elapsedIdleTimeMillis = 0;
                    startTimeMillis = startTimeMillis2;
                }
            }
        }
    }

    public Bitmap takeScreenshot() {
        Display display = DisplayManagerGlobal.getInstance().getRealDisplay(this.mDisplayId);
        Point displaySize = new Point();
        display.getRealSize(displaySize);
        ScreenCapture.SynchronousScreenCaptureListener syncScreenCapture = ScreenCapture.createSyncCaptureListener();
        try {
            if (!this.mUiAutomationConnection.takeScreenshot(new Rect(0, 0, displaySize.x, displaySize.y), syncScreenCapture)) {
                return null;
            }
            ScreenCapture.ScreenshotHardwareBuffer screenshotBuffer = syncScreenCapture.getBuffer();
            if (screenshotBuffer == null) {
                Log.e(LOG_TAG, "Failed to take screenshot for display=" + this.mDisplayId);
                return null;
            }
            Bitmap screenShot = screenshotBuffer.asBitmap();
            if (screenShot == null) {
                Log.e(LOG_TAG, "Failed to take screenshot for display=" + this.mDisplayId);
                return null;
            }
            HardwareBuffer buffer = screenshotBuffer.getHardwareBuffer();
            try {
                Bitmap swBitmap = screenShot.copy(Bitmap.Config.ARGB_8888, false);
                if (buffer != null) {
                    buffer.close();
                }
                screenShot.recycle();
                swBitmap.setHasAlpha(false);
                return swBitmap;
            } catch (Throwable th) {
                if (buffer != null) {
                    try {
                        buffer.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (RemoteException re) {
            Log.e(LOG_TAG, "Error while taking screenshot of display " + this.mDisplayId, re);
            return null;
        }
    }

    public Bitmap takeScreenshot(Window window) {
        View decorView;
        ViewRootImpl viewRoot;
        if (window == null || (decorView = window.peekDecorView()) == null || (viewRoot = decorView.getViewRootImpl()) == null) {
            return null;
        }
        SurfaceControl sc = viewRoot.getSurfaceControl();
        if (!sc.isValid()) {
            return null;
        }
        new SurfaceControl.Transaction().apply(true);
        ScreenCapture.SynchronousScreenCaptureListener syncScreenCapture = ScreenCapture.createSyncCaptureListener();
        try {
            if (!this.mUiAutomationConnection.takeSurfaceControlScreenshot(sc, syncScreenCapture)) {
                Log.e(LOG_TAG, "Failed to take screenshot for window=" + window);
                return null;
            }
            ScreenCapture.ScreenshotHardwareBuffer captureBuffer = syncScreenCapture.getBuffer();
            if (captureBuffer == null) {
                Log.e(LOG_TAG, "Failed to take screenshot for window=" + window);
                return null;
            }
            Bitmap screenShot = captureBuffer.asBitmap();
            if (screenShot == null) {
                Log.e(LOG_TAG, "Failed to take screenshot for window=" + window);
                return null;
            }
            HardwareBuffer buffer = captureBuffer.getHardwareBuffer();
            try {
                Bitmap swBitmap = screenShot.copy(Bitmap.Config.ARGB_8888, false);
                if (buffer != null) {
                    buffer.close();
                }
                screenShot.recycle();
                return swBitmap;
            } catch (Throwable th) {
                if (buffer != null) {
                    try {
                        buffer.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (RemoteException re) {
            Log.e(LOG_TAG, "Error while taking screenshot!", re);
            return null;
        }
    }

    public void setRunAsMonkey(boolean enable) {
        try {
            ActivityManager.getService().setUserIsMonkey(enable);
        } catch (RemoteException re) {
            Log.e(LOG_TAG, "Error while setting run as monkey!", re);
        }
    }

    public boolean clearWindowContentFrameStats(int windowId) {
        synchronized (this.mLock) {
            throwIfNotConnectedLocked();
        }
        try {
            return this.mUiAutomationConnection.clearWindowContentFrameStats(windowId);
        } catch (RemoteException re) {
            Log.e(LOG_TAG, "Error clearing window content frame stats!", re);
            return false;
        }
    }

    public WindowContentFrameStats getWindowContentFrameStats(int windowId) {
        synchronized (this.mLock) {
            throwIfNotConnectedLocked();
        }
        try {
            return this.mUiAutomationConnection.getWindowContentFrameStats(windowId);
        } catch (RemoteException re) {
            Log.e(LOG_TAG, "Error getting window content frame stats!", re);
            return null;
        }
    }

    @Deprecated
    public void clearWindowAnimationFrameStats() {
        try {
            this.mUiAutomationConnection.clearWindowAnimationFrameStats();
        } catch (RemoteException re) {
            Log.e(LOG_TAG, "Error clearing window animation frame stats!", re);
        }
    }

    @Deprecated
    public WindowAnimationFrameStats getWindowAnimationFrameStats() {
        try {
            return this.mUiAutomationConnection.getWindowAnimationFrameStats();
        } catch (RemoteException re) {
            Log.e(LOG_TAG, "Error getting window animation frame stats!", re);
            return null;
        }
    }

    public void grantRuntimePermission(String packageName, String permission) {
        grantRuntimePermissionAsUser(packageName, permission, Process.myUserHandle());
    }

    @Deprecated
    public boolean grantRuntimePermission(String packageName, String permission, UserHandle userHandle) {
        grantRuntimePermissionAsUser(packageName, permission, userHandle);
        return true;
    }

    public void grantRuntimePermissionAsUser(String packageName, String permission, UserHandle userHandle) {
        try {
            this.mUiAutomationConnection.grantRuntimePermission(packageName, permission, userHandle.getIdentifier());
        } catch (Exception e) {
            throw new SecurityException("Error granting runtime permission", e);
        }
    }

    public void revokeRuntimePermission(String packageName, String permission) {
        revokeRuntimePermissionAsUser(packageName, permission, Process.myUserHandle());
    }

    @Deprecated
    public boolean revokeRuntimePermission(String packageName, String permission, UserHandle userHandle) {
        revokeRuntimePermissionAsUser(packageName, permission, userHandle);
        return true;
    }

    public void revokeRuntimePermissionAsUser(String packageName, String permission, UserHandle userHandle) {
        try {
            this.mUiAutomationConnection.revokeRuntimePermission(packageName, permission, userHandle.getIdentifier());
        } catch (Exception e) {
            throw new SecurityException("Error granting runtime permission", e);
        }
    }

    public ParcelFileDescriptor executeShellCommand(String command) {
        warnIfBetterCommand(command);
        ParcelFileDescriptor source = null;
        ParcelFileDescriptor sink = null;
        try {
            try {
                ParcelFileDescriptor[] pipe = ParcelFileDescriptor.createPipe();
                source = pipe[0];
                sink = pipe[1];
                this.mUiAutomationConnection.executeShellCommand(command, sink, null);
            } catch (RemoteException re) {
                Log.e(LOG_TAG, "Error executing shell command!", re);
            } catch (IOException ioe) {
                Log.e(LOG_TAG, "Error executing shell command!", ioe);
            }
            return source;
        } finally {
            IoUtils.closeQuietly(sink);
        }
    }

    public ParcelFileDescriptor[] executeShellCommandRw(String command) {
        return executeShellCommandInternal(command, false);
    }

    public ParcelFileDescriptor[] executeShellCommandRwe(String command) {
        return executeShellCommandInternal(command, true);
    }

    public int getDisplayId() {
        return this.mDisplayId;
    }

    private ParcelFileDescriptor[] executeShellCommandInternal(String command, boolean includeStderr) {
        warnIfBetterCommand(command);
        ParcelFileDescriptor source_read = null;
        ParcelFileDescriptor sink_read = null;
        ParcelFileDescriptor source_write = null;
        ParcelFileDescriptor sink_write = null;
        ParcelFileDescriptor stderr_source_read = null;
        ParcelFileDescriptor stderr_sink_read = null;
        try {
            try {
                try {
                    ParcelFileDescriptor[] pipe_read = ParcelFileDescriptor.createPipe();
                    source_read = pipe_read[0];
                    sink_read = pipe_read[1];
                    ParcelFileDescriptor[] pipe_write = ParcelFileDescriptor.createPipe();
                    source_write = pipe_write[0];
                    sink_write = pipe_write[1];
                    if (includeStderr) {
                        ParcelFileDescriptor[] stderr_read = ParcelFileDescriptor.createPipe();
                        stderr_source_read = stderr_read[0];
                        stderr_sink_read = stderr_read[1];
                    }
                    this.mUiAutomationConnection.executeShellCommandWithStderr(command, sink_read, source_write, stderr_sink_read);
                } catch (RemoteException re) {
                    Log.e(LOG_TAG, "Error executing shell command!", re);
                }
            } catch (IOException ioe) {
                Log.e(LOG_TAG, "Error executing shell command!", ioe);
            }
            IoUtils.closeQuietly(sink_read);
            IoUtils.closeQuietly(source_write);
            IoUtils.closeQuietly(stderr_sink_read);
            ParcelFileDescriptor[] result = new ParcelFileDescriptor[includeStderr ? 3 : 2];
            result[0] = source_read;
            result[1] = sink_write;
            if (includeStderr) {
                result[2] = stderr_source_read;
            }
            return result;
        } catch (Throwable th) {
            IoUtils.closeQuietly(sink_read);
            IoUtils.closeQuietly(source_write);
            IoUtils.closeQuietly(stderr_sink_read);
            throw th;
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("UiAutomation@").append(Integer.toHexString(hashCode()));
        stringBuilder.append("[id=").append(this.mConnectionId);
        stringBuilder.append(", displayId=").append(this.mDisplayId);
        stringBuilder.append(", flags=").append(this.mFlags);
        stringBuilder.append(NavigationBarInflaterView.SIZE_MOD_END);
        return stringBuilder.toString();
    }

    private void throwIfConnectedLocked() {
        if (this.mConnectionState == 2) {
            throw new IllegalStateException("UiAutomation connected, " + this);
        }
    }

    private void throwIfNotConnectedLocked() {
        String msg;
        if (this.mConnectionState != 2) {
            if (useAccessibility()) {
                msg = "UiAutomation not connected, ";
            } else {
                msg = "UiAutomation not connected: Accessibility-dependent method called with FLAG_DONT_USE_ACCESSIBILITY set, ";
            }
            throw new IllegalStateException(msg + this);
        }
    }

    private void warnIfBetterCommand(String cmd) {
        if (cmd.startsWith("pm grant ")) {
            Log.w(LOG_TAG, "UiAutomation.grantRuntimePermission() is more robust and should be used instead of 'pm grant'");
        } else if (cmd.startsWith("pm revoke ")) {
            Log.w(LOG_TAG, "UiAutomation.revokeRuntimePermission() is more robust and should be used instead of 'pm revoke'");
        }
    }

    private boolean useAccessibility() {
        return (this.mFlags & 2) == 0;
    }

    private static int getDisplayId(Context context) {
        Preconditions.checkArgument(context != null, "Context cannot be null!");
        UserManager userManager = (UserManager) context.getSystemService(UserManager.class);
        if (!userManager.isVisibleBackgroundUsersSupported()) {
            return 0;
        }
        int displayId = context.getDisplayId();
        if (displayId == -1) {
            Log.e(LOG_TAG, "UiAutomation created UI context with invalid display id, assuming it's running in the display assigned to the user");
            return getMainDisplayIdAssignedToUser(context, userManager);
        }
        if (displayId != 0) {
            return displayId;
        }
        int userDisplayId = getMainDisplayIdAssignedToUser(context, userManager);
        return userDisplayId;
    }

    private static int getMainDisplayIdAssignedToUser(Context context, UserManager userManager) {
        if (!userManager.isUserVisible()) {
            Log.e(LOG_TAG, "User (" + context.getUserId() + ") is not visible, using DEFAULT_DISPLAY");
            return 0;
        }
        return userManager.getMainDisplayIdAssignedToUser();
    }

    private class IAccessibilityServiceClientImpl extends AccessibilityService.IAccessibilityServiceClientWrapper {
        public IAccessibilityServiceClientImpl(Looper looper, final int generationId) {
            super((Context) null, looper, new AccessibilityService.Callbacks() { // from class: android.app.UiAutomation.IAccessibilityServiceClientImpl.1
                private final int mGenerationId;

                {
                    this.mGenerationId = generationId;
                }

                private boolean isGenerationChangedLocked() {
                    return this.mGenerationId != UiAutomation.this.mGenerationId;
                }

                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public void init(int connectionId, IBinder windowToken) {
                    synchronized (UiAutomation.this.mLock) {
                        if (isGenerationChangedLocked()) {
                            return;
                        }
                        UiAutomation.this.mConnectionState = 2;
                        UiAutomation.this.mConnectionId = connectionId;
                        UiAutomation.this.mLock.notifyAll();
                        if (Build.IS_DEBUGGABLE) {
                            Log.v(UiAutomation.LOG_TAG, "Init " + UiAutomation.this);
                        }
                    }
                }

                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public void onServiceConnected() {
                }

                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public void onInterrupt() {
                }

                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public void onSystemActionsChanged() {
                }

                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public void createImeSession(IAccessibilityInputMethodSessionCallback callback) {
                }

                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public void startInput(RemoteAccessibilityInputConnection inputConnection, EditorInfo editorInfo, boolean restarting) {
                }

                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public boolean onGesture(AccessibilityGestureEvent gestureEvent) {
                    return false;
                }

                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public void onMotionEvent(MotionEvent event) {
                }

                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public void onTouchStateChanged(int displayId, int state) {
                }

                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public void onAccessibilityEvent(AccessibilityEvent event) {
                    synchronized (UiAutomation.this.mLock) {
                        if (isGenerationChangedLocked()) {
                            return;
                        }
                        UiAutomation.this.mLastEventTimeMillis = Math.max(UiAutomation.this.mLastEventTimeMillis, event.getEventTime());
                        if (UiAutomation.this.mWaitingForEventDelivery) {
                            UiAutomation.this.mEventQueue.add(AccessibilityEvent.obtain(event));
                        }
                        UiAutomation.this.mLock.notifyAll();
                        OnAccessibilityEventListener listener = UiAutomation.this.mOnAccessibilityEventListener;
                        if (listener != null) {
                            UiAutomation.this.mLocalCallbackHandler.sendMessage(PooledLambda.obtainMessage(new BiConsumer() { // from class: android.app.UiAutomation$IAccessibilityServiceClientImpl$1$$ExternalSyntheticLambda0
                                @Override // java.util.function.BiConsumer
                                public final void accept(Object obj, Object obj2) {
                                    ((UiAutomation.OnAccessibilityEventListener) obj).onAccessibilityEvent((AccessibilityEvent) obj2);
                                }
                            }, listener, AccessibilityEvent.obtain(event)));
                        }
                    }
                }

                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public boolean onKeyEvent(KeyEvent event) {
                    return false;
                }

                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public void onMagnificationChanged(int displayId, Region region, MagnificationConfig config) {
                }

                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public void onSoftKeyboardShowModeChanged(int showMode) {
                }

                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public void onPerformGestureResult(int sequence, boolean completedSuccessfully) {
                }

                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public void onFingerprintCapturingGesturesChanged(boolean active) {
                }

                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public void onFingerprintGesture(int gesture) {
                }

                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public void onAccessibilityButtonClicked(int displayId) {
                }

                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public void onAccessibilityButtonAvailabilityChanged(boolean available) {
                }
            });
        }
    }
}
