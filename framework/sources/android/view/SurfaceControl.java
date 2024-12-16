package android.view;

import android.content.pm.PackageManager;
import android.graphics.ColorSpace;
import android.graphics.GraphicBuffer;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Region;
import android.gui.StalledTransactionInfo;
import android.hardware.HardwareBuffer;
import android.hardware.OverlayProperties;
import android.hardware.SyncFence;
import android.hardware.display.DeviceProductInfo;
import android.hardware.display.DisplayedContentSample;
import android.hardware.display.DisplayedContentSamplingAttributes;
import android.hardware.graphics.common.DisplayDecorationSupport;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Build;
import android.os.Debug;
import android.os.IBinder;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Slog;
import android.util.SparseIntArray;
import android.util.proto.ProtoOutputStream;
import android.view.Display;
import android.view.DisplayAddress;
import android.view.SemBlurInfo;
import android.view.Surface;
import android.view.SurfaceControl;
import com.android.internal.util.Preconditions;
import com.android.internal.util.VirtualRefBasePtr;
import com.android.window.flags.Flags;
import com.samsung.android.rune.CoreRune;
import dalvik.system.CloseGuard;
import java.io.Closeable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import libcore.util.NativeAllocationRegistry;

/* loaded from: classes4.dex */
public final class SurfaceControl implements Parcelable {
    public static final int BUFFER_TRANSFORM_IDENTITY = 0;
    public static final int BUFFER_TRANSFORM_MIRROR_HORIZONTAL = 1;
    public static final int BUFFER_TRANSFORM_MIRROR_VERTICAL = 2;
    public static final int BUFFER_TRANSFORM_ROTATE_180 = 3;
    public static final int BUFFER_TRANSFORM_ROTATE_270 = 7;
    public static final int BUFFER_TRANSFORM_ROTATE_90 = 4;
    public static final int CACHING_DISABLED = 0;
    public static final int CACHING_ENABLED = 1;
    public static final int CAN_OCCLUDE_PRESENTATION = 4096;
    public static final int CURSOR_WINDOW = 8192;
    public static final int DISABLE_SUPER_HDR = 268435456;
    public static final int DISPLAY_DECORATION = 512;
    public static final int DISPLAY_RECEIVES_INPUT = 1;
    public static final int ENABLE_BACKPRESSURE = 256;
    public static final int FRAME_RATE_SELECTION_STRATEGY_OVERRIDE_CHILDREN = 1;
    public static final int FRAME_RATE_SELECTION_STRATEGY_PROPAGATE = 0;
    public static final int FRAME_RATE_SELECTION_STRATEGY_SELF = 2;
    public static final int FX_SURFACE_BLAST = 262144;
    public static final int FX_SURFACE_CONTAINER = 524288;
    public static final int FX_SURFACE_EFFECT = 131072;
    public static final int FX_SURFACE_MASK = 983040;
    public static final int FX_SURFACE_NORMAL = 0;
    public static final int HIDDEN = 4;
    public static final int IGNORE_DESTINATION_FRAME = 1024;
    public static final int LAYER_IS_REFRESH_RATE_INDICATOR = 2048;
    public static final int METADATA_ACCESSIBILITY_ID = 5;
    public static final int METADATA_GAME_MODE = 8;
    public static final int METADATA_MOUSE_CURSOR = 4;
    public static final int METADATA_OWNER_PID = 6;
    public static final int METADATA_OWNER_UID = 1;
    public static final int METADATA_SEC_CAN_RECEIVE_INPUT = 31;
    public static final int METADATA_SEC_HDR_CUSTOM_DIM_RATIO = 34;
    public static final int METADATA_SEC_HDR_OFF = 33;
    public static final int METADATA_SEC_INTERNAL_ONLY = 32;
    public static final int METADATA_SEC_SURFACE_TYPE = 30;
    public static final int METADATA_TASK_ID = 3;
    public static final int METADATA_WINDOW_TYPE = 2;
    public static final int NON_PREMULTIPLIED = 256;
    public static final int NO_COLOR_FILL = 16384;
    public static final int NO_REMOTECONTROL = 15728640;
    public static final int OPAQUE = 1024;
    public static final int POWER_MODE_DOZE = 1;
    public static final int POWER_MODE_DOZE_SUSPEND = 3;
    public static final int POWER_MODE_NORMAL = 2;
    public static final int POWER_MODE_OFF = 0;
    public static final int POWER_MODE_ON_SUSPEND = 4;
    public static final int PROTECTED_APP = 2048;
    public static final int SECURE = 128;
    public static final int SKIP_SCREENSHOT = 64;
    private static final int SURFACE_HIDDEN = 1;
    private static final int SURFACE_OPAQUE = 2;
    private static final String TAG = "SurfaceControl";
    private String mCallsite;
    private Choreographer mChoreographer;
    private final Object mChoreographerLock;
    private final CloseGuard mCloseGuard;
    private Runnable mFreeNativeResources;
    private int mHeight;
    private boolean mIgnoreToUpdateByOtherProcess;
    private WeakReference<View> mLocalOwnerView;
    private final Object mLock;
    private String mName;
    private long mNativeHandle;
    public long mNativeObject;
    private int mOwnerPid;
    private Throwable mReleaseStack;
    private ArrayList<OnReparentListener> mReparentListeners;
    private TrustedPresentationCallback mTrustedPresentationCallback;
    private int mWidth;
    private static volatile boolean sDebugUsageAfterRelease = false;
    private static final NativeAllocationRegistry sRegistry = NativeAllocationRegistry.createMalloced(SurfaceControl.class.getClassLoader(), nativeGetNativeSurfaceControlFinalizer());
    public static final Parcelable.Creator<SurfaceControl> CREATOR = new Parcelable.Creator<SurfaceControl>() { // from class: android.view.SurfaceControl.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SurfaceControl createFromParcel(Parcel in) {
            return new SurfaceControl(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SurfaceControl[] newArray(int size) {
            return new SurfaceControl[size];
        }
    };

    @Retention(RetentionPolicy.SOURCE)
    public @interface BufferTransform {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface CachingHint {
    }

    public static final class CieXyz {
        public float X;
        public float Y;
        public float Z;
    }

    public static final class DisplayPrimaries {
        public CieXyz blue;
        public CieXyz green;
        public CieXyz red;
        public CieXyz white;
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface FrameRateSelectionStrategy {
    }

    public static abstract class OnJankDataListener {
        private final VirtualRefBasePtr mNativePtr = new VirtualRefBasePtr(SurfaceControl.nativeCreateJankDataListenerWrapper(this));

        public abstract void onJankDataAvailable(JankData[] jankDataArr);
    }

    public interface OnReparentListener {
        void onReparent(Transaction transaction, SurfaceControl surfaceControl);
    }

    public interface TransactionCommittedListener {
        void onTransactionCommitted();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static native long getNativeTrustedPresentationCallbackFinalizer();

    private static native void nativeAddJankDataListener(long j, long j2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeAddTransactionCommittedListener(long j, TransactionCommittedListener transactionCommittedListener);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeAddTransactionCompletedListener(long j, Consumer<TransactionStats> consumer);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeAddWindowInfosReportedListener(long j, Runnable runnable);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeApplyTransaction(long j, boolean z, boolean z2);

    private static native boolean nativeBootFinished();

    private static native boolean nativeClearAnimationFrameStats();

    private static native void nativeClearBootDisplayMode(IBinder iBinder);

    private static native boolean nativeClearContentFrameStats(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeClearTransaction(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeClearTrustedPresentationCallback(long j, long j2);

    private static native long nativeCopyFromSurfaceControl(long j);

    private static native long nativeCreate(SurfaceSession surfaceSession, String str, int i, int i2, int i3, int i4, long j, Parcel parcel) throws Surface.OutOfResourcesException;

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nativeCreateJankDataListenerWrapper(OnJankDataListener onJankDataListener);

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nativeCreateTpc(TrustedPresentationCallback trustedPresentationCallback);

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nativeCreateTransaction();

    private static native void nativeDisconnect(long j);

    private static native boolean nativeGetAnimationFrameStats(WindowAnimationFrameStats windowAnimationFrameStats);

    private static native boolean nativeGetBootDisplayModeSupport();

    private static native int[] nativeGetCompositionDataspaces();

    private static native boolean nativeGetContentFrameStats(long j, WindowContentFrameStats windowContentFrameStats);

    /* JADX INFO: Access modifiers changed from: private */
    public static native IBinder nativeGetDefaultApplyToken();

    private static native DesiredDisplayModeSpecs nativeGetDesiredDisplayModeSpecs(IBinder iBinder);

    private static native boolean nativeGetDisplayBrightnessSupport(IBinder iBinder);

    private static native DisplayDecorationSupport nativeGetDisplayDecorationSupport(IBinder iBinder);

    private static native DisplayPrimaries nativeGetDisplayNativePrimaries(IBinder iBinder);

    private static native DisplayedContentSample nativeGetDisplayedContentSample(IBinder iBinder, long j, long j2);

    private static native DisplayedContentSamplingAttributes nativeGetDisplayedContentSamplingAttributes(IBinder iBinder);

    private static native DynamicDisplayInfo nativeGetDynamicDisplayInfo(long j);

    private static native int nativeGetGPUContextPriority();

    private static native long nativeGetHandle(long j);

    private static native IdleBeginTime nativeGetIdleBeginTime(IBinder iBinder);

    private static native int nativeGetLayerId(long j);

    private static native long nativeGetNativeSurfaceControlFinalizer();

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nativeGetNativeTransactionFinalizer();

    private static native OverlayProperties nativeGetOverlaySupport();

    private static native long[] nativeGetPhysicalDisplayIds();

    private static native IBinder nativeGetPhysicalDisplayToken(long j);

    private static native boolean nativeGetProtectedContentSupport();

    private static native StalledTransactionInfo nativeGetStalledTransactionInfo(int i);

    private static native StaticDisplayInfo nativeGetStaticDisplayInfo(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nativeGetTransactionId(long j);

    private static native int nativeGetTransformHint(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeMergeTransaction(long j, long j2);

    private static native long nativeMirrorSurface(long j);

    private static native void nativeNotifyHFRmode(IBinder iBinder, int i);

    private static native void nativeNotifyShutdown();

    private static native long nativeReadFromParcel(Parcel parcel);

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nativeReadTransactionFromParcel(Parcel parcel);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeRemoveCurrentInputFocus(long j, int i);

    private static native void nativeRemoveJankDataListener(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeReparent(long j, long j2, long j3);

    private static native void nativeRestrictHighRefreshRate(boolean z);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSanitize(long j, int i, int i2);

    private static native boolean nativeSetActiveColorMode(IBinder iBinder, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetAlpha(long j, long j2, float f);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetAnimationTransaction(long j);

    private static native void nativeSetAutoLowLatencyMode(IBinder iBinder, boolean z);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetBackgroundBlurColorCurve(long j, long j2, float[] fArr);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetBackgroundBlurRadius(long j, long j2, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetBlurRegions(long j, long j2, float[][] fArr, int i);

    private static native void nativeSetBootDisplayMode(IBinder iBinder, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetBuffer(long j, long j2, HardwareBuffer hardwareBuffer, long j3, Consumer<SyncFence> consumer);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetBufferTransform(long j, long j2, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetCachingHint(long j, long j2, int i);

    private static native void nativeSetCanOccludePresentation(long j, long j2, boolean z);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetColor(long j, long j2, float[] fArr);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetColorSpaceAgnostic(long j, long j2, boolean z);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetColorTransform(long j, long j2, float[] fArr, float[] fArr2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetCornerRadius(long j, long j2, float f);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetDamageRegion(long j, long j2, Region region);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetDataSpace(long j, long j2, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetDefaultApplyToken(IBinder iBinder);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetDefaultFrameRateCompatibility(long j, long j2, int i);

    private static native boolean nativeSetDesiredDisplayModeSpecs(IBinder iBinder, DesiredDisplayModeSpecs desiredDisplayModeSpecs);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetDesiredHdrHeadroom(long j, long j2, float f);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetDesiredPresentTimeNanos(long j, long j2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetDestinationFrame(long j, long j2, int i, int i2, int i3, int i4);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetDimmingEnabled(long j, long j2, boolean z);

    private static native boolean nativeSetDisplayBrightness(IBinder iBinder, float f, float f2, float f3, float f4);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetDisplayFlags(long j, IBinder iBinder, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetDisplayLayerStack(long j, IBinder iBinder, int i);

    private static native void nativeSetDisplayPowerMode(IBinder iBinder, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetDisplayProjection(long j, IBinder iBinder, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetDisplayReluminoEffect(long j, IBinder iBinder, float f, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetDisplaySize(long j, IBinder iBinder, int i, int i2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetDisplaySurface(long j, IBinder iBinder, long j2);

    private static native boolean nativeSetDisplayedContentSamplingEnabled(IBinder iBinder, boolean z, int i, int i2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetDropInputMode(long j, long j2, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetEarlyWakeupEnd(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetEarlyWakeupStart(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetExtendedRangeBrightness(long j, long j2, float f, float f2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetFixedTransformHint(long j, long j2, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetFlags(long j, long j2, int i, int i2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetFocusedWindow(long j, IBinder iBinder, String str, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetFrameRate(long j, long j2, float f, int i, int i2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetFrameRateCategory(long j, long j2, int i, boolean z);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetFrameRateSelectionPriority(long j, long j2, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetFrameRateSelectionStrategy(long j, long j2, int i);

    private static native void nativeSetFrameTimeline(long j, long j2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetFrameTimelineVsync(long j, long j2);

    private static native void nativeSetGameContentType(IBinder iBinder, boolean z);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetGeometry(long j, long j2, Rect rect, Rect rect2, long j3);

    private static native void nativeSetGlobalShadowSettings(float[] fArr, float[] fArr2, float f, float f2, float f3);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetInputWindowInfo(long j, long j2, InputWindowHandle inputWindowHandle);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetLayer(long j, long j2, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetLayerStack(long j, long j2, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetMatrix(long j, long j2, float f, float f2, float f3, float f4);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetMetadata(long j, long j2, int i, Parcel parcel);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetPosition(long j, long j2, float f, float f2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetRelativeLayer(long j, long j2, long j3, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetScale(long j, long j2, float f, float f2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetShadowRadius(long j, long j2, float f);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetStretchEffect(long j, long j2, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10);

    private static native void nativeSetTransformHint(long j, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetTransparentRegionHint(long j, long j2, Region region);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetTrustedOverlay(long j, long j2, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetTrustedPresentationCallback(long j, long j2, long j3, TrustedPresentationThresholds trustedPresentationThresholds);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetWindowCrop(long j, long j2, int i, int i2, int i3, int i4);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeStartChangeResolution(long j, IBinder iBinder, boolean z);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeStartSurfaceAnimation(long j, long j2, String str);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSurfaceFlushJankData(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeUnsetBuffer(long j, long j2);

    private static native void nativeUpdateDefaultBufferSize(long j, int i, int i2);

    private static native void nativeWriteToParcel(long j, Parcel parcel);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeWriteTransactionToParcel(long j, Parcel parcel);

    public static int rotationToBufferTransform(int rotation) {
        switch (rotation) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            default:
                Log.e(TAG, "Trying to convert unknown rotation=" + rotation);
                break;
        }
        return 0;
    }

    public static class JankData {
        public static final int BUFFER_STUFFING = 64;
        public static final int DISPLAY_HAL = 1;
        public static final int JANK_APP_DEADLINE_MISSED = 8;
        public static final int JANK_NONE = 0;
        public static final int JANK_SURFACEFLINGER_DEADLINE_MISSED = 2;
        public static final int JANK_SURFACEFLINGER_GPU_DEADLINE_MISSED = 4;
        public static final int PREDICTION_ERROR = 16;
        public static final int SURFACE_FLINGER_SCHEDULING = 32;
        public static final int UNKNOWN = 128;
        public final long frameIntervalNs;
        public final long frameVsyncId;
        public final int jankType;

        @Retention(RetentionPolicy.SOURCE)
        public @interface JankType {
        }

        public JankData(long frameVsyncId, int jankType, long frameIntervalNs) {
            this.frameVsyncId = frameVsyncId;
            this.jankType = jankType;
            this.frameIntervalNs = frameIntervalNs;
        }
    }

    public boolean addOnReparentListener(OnReparentListener listener) {
        boolean add;
        synchronized (this.mLock) {
            if (this.mReparentListeners == null) {
                this.mReparentListeners = new ArrayList<>(1);
            }
            add = this.mReparentListeners.add(listener);
        }
        return add;
    }

    public boolean removeOnReparentListener(OnReparentListener listener) {
        boolean removed;
        synchronized (this.mLock) {
            removed = this.mReparentListeners.remove(listener);
            if (this.mReparentListeners.isEmpty()) {
                this.mReparentListeners = null;
            }
        }
        return removed;
    }

    private void assignNativeObject(long nativeObject, String callsite) {
        if (this.mNativeObject != 0) {
            release();
        }
        if (nativeObject != 0) {
            this.mFreeNativeResources = sRegistry.registerNativeAllocation(this, nativeObject);
        }
        this.mNativeObject = nativeObject;
        this.mNativeHandle = this.mNativeObject != 0 ? nativeGetHandle(nativeObject) : 0L;
        if (sDebugUsageAfterRelease && this.mNativeObject == 0) {
            this.mReleaseStack = new Throwable("Assigned invalid nativeObject");
        } else {
            this.mReleaseStack = null;
        }
        setUnreleasedWarningCallSite(callsite);
        if (nativeObject != 0) {
            SurfaceControlRegistry.getProcessInstance().add(this);
        }
    }

    public void copyFrom(SurfaceControl other, String callsite) {
        this.mName = other.mName;
        this.mWidth = other.mWidth;
        this.mHeight = other.mHeight;
        this.mLocalOwnerView = other.mLocalOwnerView;
        assignNativeObject(nativeCopyFromSurfaceControl(other.mNativeObject), callsite);
    }

    public static class Builder {
        private int mHeight;
        private WeakReference<View> mLocalOwnerView;
        private SparseIntArray mMetadata;
        private String mName;
        private SurfaceControl mParent;
        private SurfaceSession mSession;
        private int mWidth;
        private int mFlags = 4;
        private int mFormat = -1;
        private String mCallsite = "SurfaceControl.Builder";

        public Builder(SurfaceSession session) {
            this.mSession = session;
        }

        public Builder() {
        }

        public SurfaceControl build() {
            if (this.mWidth < 0 || this.mHeight < 0) {
                throw new IllegalStateException("width and height must be positive or unset");
            }
            if ((this.mWidth > 0 || this.mHeight > 0) && (isEffectLayer() || isContainerLayer())) {
                throw new IllegalStateException("Only buffer layers can set a valid buffer size.");
            }
            if (this.mName == null) {
                Log.w(SurfaceControl.TAG, "Missing name for SurfaceControl", new Throwable());
            }
            if ((this.mFlags & 983040) == 0) {
                setBLASTLayer();
            }
            return new SurfaceControl(this.mSession, this.mName, this.mWidth, this.mHeight, this.mFormat, this.mFlags, this.mParent, this.mMetadata, this.mLocalOwnerView, this.mCallsite);
        }

        public Builder setName(String name) {
            this.mName = name;
            return this;
        }

        public Builder setLocalOwnerView(View view) {
            this.mLocalOwnerView = new WeakReference<>(view);
            return this;
        }

        public Builder setBufferSize(int width, int height) {
            if (width < 0 || height < 0) {
                throw new IllegalArgumentException("width and height must be positive");
            }
            this.mWidth = width;
            this.mHeight = height;
            return setFlags(0, 983040);
        }

        private void unsetBufferSize() {
            this.mWidth = 0;
            this.mHeight = 0;
        }

        public Builder setFormat(int format) {
            this.mFormat = format;
            return this;
        }

        public Builder setProtected(boolean protectedContent) {
            if (protectedContent) {
                this.mFlags |= 2048;
            } else {
                this.mFlags &= -2049;
            }
            return this;
        }

        public Builder setSecure(boolean secure) {
            if (secure) {
                this.mFlags |= 128;
            } else {
                this.mFlags &= PackageManager.INSTALL_FAILED_PRE_APPROVAL_NOT_AVAILABLE;
            }
            return this;
        }

        public Builder setOpaque(boolean opaque) {
            if (opaque) {
                this.mFlags |= 1024;
            } else {
                this.mFlags &= -1025;
            }
            return this;
        }

        public Builder setHidden(boolean hidden) {
            if (hidden) {
                this.mFlags |= 4;
            } else {
                this.mFlags &= -5;
            }
            return this;
        }

        public Builder setParent(SurfaceControl parent) {
            this.mParent = parent;
            return this;
        }

        public Builder setMetadata(int key, int data) {
            if (this.mMetadata == null) {
                this.mMetadata = new SparseIntArray();
            }
            this.mMetadata.put(key, data);
            return this;
        }

        public Builder setEffectLayer() {
            this.mFlags |= 16384;
            unsetBufferSize();
            return setFlags(131072, 983040);
        }

        public Builder setColorLayer() {
            unsetBufferSize();
            return setFlags(131072, 983040);
        }

        private boolean isEffectLayer() {
            return (this.mFlags & 131072) == 131072;
        }

        public Builder setBLASTLayer() {
            return setFlags(262144, 983040);
        }

        public Builder setContainerLayer() {
            unsetBufferSize();
            return setFlags(524288, 983040);
        }

        private boolean isContainerLayer() {
            return (this.mFlags & 524288) == 524288;
        }

        public Builder setFlags(int flags) {
            this.mFlags = flags;
            return this;
        }

        public Builder setCallsite(String callsite) {
            this.mCallsite = callsite;
            return this;
        }

        private Builder setFlags(int flags, int mask) {
            this.mFlags = (this.mFlags & (~mask)) | flags;
            return this;
        }
    }

    private SurfaceControl(SurfaceSession session, String name, int w, int h, int format, int flags, SurfaceControl parent, SparseIntArray metadata, WeakReference<View> localOwnerView, String callsite) throws Surface.OutOfResourcesException, IllegalArgumentException {
        Parcel metaParcel;
        this.mCloseGuard = CloseGuard.get();
        this.mChoreographerLock = new Object();
        this.mLock = new Object();
        this.mReleaseStack = null;
        this.mOwnerPid = -1;
        this.mIgnoreToUpdateByOtherProcess = false;
        if (name == null) {
            throw new IllegalArgumentException("name must not be null");
        }
        this.mName = name;
        this.mWidth = w;
        this.mHeight = h;
        this.mLocalOwnerView = localOwnerView;
        Parcel metaParcel2 = Parcel.obtain();
        if (metadata != null) {
            try {
                if (metadata.size() > 0) {
                    metaParcel2.writeInt(metadata.size());
                    for (int i = 0; i < metadata.size(); i++) {
                        metaParcel2.writeInt(metadata.keyAt(i));
                        metaParcel2.writeByteArray(ByteBuffer.allocate(4).order(ByteOrder.nativeOrder()).putInt(metadata.valueAt(i)).array());
                    }
                    metaParcel2.setDataPosition(0);
                }
            } catch (Throwable th) {
                th = th;
                metaParcel = metaParcel2;
                metaParcel.recycle();
                throw th;
            }
        }
        metaParcel = metaParcel2;
        try {
            long nativeObject = nativeCreate(session, name, w, h, format, flags, parent != null ? parent.mNativeObject : 0L, metaParcel);
            metaParcel.recycle();
            if (nativeObject == 0) {
                throw new Surface.OutOfResourcesException("Couldn't allocate SurfaceControl native object");
            }
            assignNativeObject(nativeObject, callsite);
            if (CoreRune.FW_SURFACE_DEBUG_CREATION) {
                SurfaceControlRegistry.getProcessInstance().checkCallStackDebugging("SurfaceControl is created", null, this, null, true);
            }
        } catch (Throwable th2) {
            th = th2;
            metaParcel.recycle();
            throw th;
        }
    }

    public SurfaceControl(SurfaceControl other, String callsite) {
        this.mCloseGuard = CloseGuard.get();
        this.mChoreographerLock = new Object();
        this.mLock = new Object();
        this.mReleaseStack = null;
        this.mOwnerPid = -1;
        this.mIgnoreToUpdateByOtherProcess = false;
        copyFrom(other, callsite);
    }

    private SurfaceControl(Parcel in) {
        this.mCloseGuard = CloseGuard.get();
        this.mChoreographerLock = new Object();
        this.mLock = new Object();
        this.mReleaseStack = null;
        this.mOwnerPid = -1;
        this.mIgnoreToUpdateByOtherProcess = false;
        readFromParcel(in);
    }

    public SurfaceControl() {
        this.mCloseGuard = CloseGuard.get();
        this.mChoreographerLock = new Object();
        this.mLock = new Object();
        this.mReleaseStack = null;
        this.mOwnerPid = -1;
        this.mIgnoreToUpdateByOtherProcess = false;
    }

    public void readFromParcel(Parcel in) {
        if (in == null) {
            throw new IllegalArgumentException("source must not be null");
        }
        this.mName = in.readString8();
        this.mWidth = in.readInt();
        this.mHeight = in.readInt();
        long object = 0;
        if (in.readInt() != 0) {
            object = nativeReadFromParcel(in);
        }
        assignNativeObject(object, "readFromParcel");
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        if (sDebugUsageAfterRelease) {
            checkNotReleased();
        }
        dest.writeString8(this.mName);
        dest.writeInt(this.mWidth);
        dest.writeInt(this.mHeight);
        if (this.mNativeObject == 0) {
            dest.writeInt(0);
        } else {
            dest.writeInt(1);
        }
        nativeWriteToParcel(this.mNativeObject, dest);
        if ((flags & 1) != 0) {
            release();
        }
    }

    public static void setDebugUsageAfterRelease(boolean debug) {
        if (!Build.isDebuggable()) {
            return;
        }
        sDebugUsageAfterRelease = debug;
    }

    public void setUnreleasedWarningCallSite(String callsite) {
        if (!isValid()) {
            return;
        }
        this.mCloseGuard.openWithCallSite("release", callsite);
        this.mCallsite = callsite;
    }

    String getCallsite() {
        return this.mCallsite;
    }

    String getName() {
        return this.mName;
    }

    public boolean isSameSurface(SurfaceControl other) {
        return other.mNativeHandle == this.mNativeHandle;
    }

    public Choreographer getChoreographer() {
        checkNotReleased();
        synchronized (this.mChoreographerLock) {
            if (this.mChoreographer == null) {
                return getChoreographer(Looper.myLooper());
            }
            return this.mChoreographer;
        }
    }

    public Choreographer getChoreographer(Looper looper) {
        Choreographer choreographer;
        checkNotReleased();
        synchronized (this.mChoreographerLock) {
            if (this.mChoreographer == null) {
                this.mChoreographer = Choreographer.getInstanceForSurfaceControl(this.mNativeHandle, looper);
            } else if (!this.mChoreographer.isTheLooperSame(looper)) {
                throw new IllegalStateException("Choreographer already exists with a different looper");
            }
            choreographer = this.mChoreographer;
        }
        return choreographer;
    }

    public boolean hasChoreographer() {
        boolean z;
        synchronized (this.mChoreographerLock) {
            z = this.mChoreographer != null;
        }
        return z;
    }

    public void dumpDebug(ProtoOutputStream proto, long fieldId) {
        long token = proto.start(fieldId);
        proto.write(1120986464257L, System.identityHashCode(this));
        proto.write(1138166333442L, this.mName);
        proto.write(1120986464259L, getLayerId());
        proto.end(token);
    }

    protected void finalize() throws Throwable {
        try {
            if (this.mCloseGuard != null) {
                this.mCloseGuard.warnIfOpen();
            }
            SurfaceControlRegistry.getProcessInstance().remove(this);
        } finally {
            super.finalize();
        }
    }

    public void release() {
        if (this.mNativeObject != 0) {
            if (SurfaceControlRegistry.sCallStackDebuggingEnabled) {
                SurfaceControlRegistry.getProcessInstance().checkCallStackDebugging("release", null, this, null);
            }
            this.mFreeNativeResources.run();
            this.mNativeObject = 0L;
            this.mNativeHandle = 0L;
            this.mReleaseStack = new Throwable("Released");
            this.mCloseGuard.close();
            synchronized (this.mChoreographerLock) {
                if (this.mChoreographer != null) {
                    this.mChoreographer.invalidate();
                    this.mChoreographer = null;
                }
            }
            SurfaceControlRegistry.getProcessInstance().remove(this);
        }
    }

    public void disconnect() {
        if (this.mNativeObject != 0) {
            nativeDisconnect(this.mNativeObject);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkNotReleased() {
        if (this.mNativeObject == 0) {
            if (this.mReleaseStack != null) {
                throw new IllegalStateException("Invalid usage after release of " + this, this.mReleaseStack);
            }
            throw new NullPointerException("mNativeObject of " + this + " is null. Have you called release() already?");
        }
    }

    public boolean isValid() {
        return this.mNativeObject != 0;
    }

    @Deprecated
    public static void openTransaction() {
    }

    @Deprecated
    public static void closeTransaction() {
    }

    public boolean clearContentFrameStats() {
        checkNotReleased();
        return nativeClearContentFrameStats(this.mNativeObject);
    }

    public boolean getContentFrameStats(WindowContentFrameStats outStats) {
        checkNotReleased();
        return nativeGetContentFrameStats(this.mNativeObject, outStats);
    }

    public static boolean clearAnimationFrameStats() {
        return nativeClearAnimationFrameStats();
    }

    public static boolean getAnimationFrameStats(WindowAnimationFrameStats outStats) {
        return nativeGetAnimationFrameStats(outStats);
    }

    public int getWidth() {
        int i;
        synchronized (this.mLock) {
            i = this.mWidth;
        }
        return i;
    }

    public int getHeight() {
        int i;
        synchronized (this.mLock) {
            i = this.mHeight;
        }
        return i;
    }

    public View getLocalOwnerView() {
        if (this.mLocalOwnerView != null) {
            return this.mLocalOwnerView.get();
        }
        return null;
    }

    public String toString() {
        return "Surface(name=" + this.mName + ")/@0x" + Integer.toHexString(System.identityHashCode(this));
    }

    public static final class StaticDisplayInfo {
        public float density;
        public DeviceProductInfo deviceProductInfo;
        public int installOrientation;
        public boolean isInternal;
        public boolean secure;

        public String toString() {
            return "StaticDisplayInfo{isInternal=" + this.isInternal + ", density=" + this.density + ", secure=" + this.secure + ", deviceProductInfo=" + this.deviceProductInfo + ", installOrientation=" + this.installOrientation + "}";
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            StaticDisplayInfo that = (StaticDisplayInfo) o;
            if (this.isInternal == that.isInternal && this.density == that.density && this.secure == that.secure && Objects.equals(this.deviceProductInfo, that.deviceProductInfo) && this.installOrientation == that.installOrientation) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(Boolean.valueOf(this.isInternal), Float.valueOf(this.density), Boolean.valueOf(this.secure), this.deviceProductInfo, Integer.valueOf(this.installOrientation));
        }
    }

    public static final class DynamicDisplayInfo {
        public int activeColorMode;
        public int activeDisplayModeId;
        public boolean autoLowLatencyModeSupported;
        public boolean gameContentTypeSupported;
        public Display.HdrCapabilities hdrCapabilities;
        public int preferredBootDisplayMode;
        public float renderFrameRate;
        public int[] supportedColorModes;
        public DisplayMode[] supportedDisplayModes;

        public String toString() {
            return "DynamicDisplayInfo{supportedDisplayModes=" + Arrays.toString(this.supportedDisplayModes) + ", activeDisplayModeId=" + this.activeDisplayModeId + ", renderFrameRate=" + this.renderFrameRate + ", supportedColorModes=" + Arrays.toString(this.supportedColorModes) + ", activeColorMode=" + this.activeColorMode + ", hdrCapabilities=" + this.hdrCapabilities + ", autoLowLatencyModeSupported=" + this.autoLowLatencyModeSupported + ", gameContentTypeSupported" + this.gameContentTypeSupported + ", preferredBootDisplayMode" + this.preferredBootDisplayMode + "}";
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            DynamicDisplayInfo that = (DynamicDisplayInfo) o;
            if (Arrays.equals(this.supportedDisplayModes, that.supportedDisplayModes) && this.activeDisplayModeId == that.activeDisplayModeId && this.renderFrameRate == that.renderFrameRate && Arrays.equals(this.supportedColorModes, that.supportedColorModes) && this.activeColorMode == that.activeColorMode && Objects.equals(this.hdrCapabilities, that.hdrCapabilities) && this.preferredBootDisplayMode == that.preferredBootDisplayMode) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(Integer.valueOf(Arrays.hashCode(this.supportedDisplayModes)), Integer.valueOf(this.activeDisplayModeId), Float.valueOf(this.renderFrameRate), Integer.valueOf(this.activeColorMode), this.hdrCapabilities);
        }
    }

    public static final class DisplayMode {
        public long appVsyncOffsetNanos;
        public int group;
        public int height;
        public int id;
        public float peakRefreshRate;
        public long presentationDeadlineNanos;
        public int[] supportedHdrTypes;
        public float vsyncRate;
        public int width;
        public float xDpi;
        public float yDpi;

        public String toString() {
            return "DisplayMode{id=" + this.id + ", width=" + this.width + ", height=" + this.height + ", xDpi=" + this.xDpi + ", yDpi=" + this.yDpi + ", peakRefreshRate=" + this.peakRefreshRate + ", vsyncRate=" + this.vsyncRate + ", appVsyncOffsetNanos=" + this.appVsyncOffsetNanos + ", presentationDeadlineNanos=" + this.presentationDeadlineNanos + ", supportedHdrTypes=" + Arrays.toString(this.supportedHdrTypes) + ", group=" + this.group + "}";
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            DisplayMode that = (DisplayMode) o;
            if (this.id == that.id && this.width == that.width && this.height == that.height && Float.compare(that.xDpi, this.xDpi) == 0 && Float.compare(that.yDpi, this.yDpi) == 0 && Float.compare(that.peakRefreshRate, this.peakRefreshRate) == 0 && Float.compare(that.vsyncRate, this.vsyncRate) == 0 && this.appVsyncOffsetNanos == that.appVsyncOffsetNanos && this.presentationDeadlineNanos == that.presentationDeadlineNanos && Arrays.equals(this.supportedHdrTypes, that.supportedHdrTypes) && this.group == that.group) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(Integer.valueOf(this.id), Integer.valueOf(this.width), Integer.valueOf(this.height), Float.valueOf(this.xDpi), Float.valueOf(this.yDpi), Float.valueOf(this.peakRefreshRate), Float.valueOf(this.vsyncRate), Long.valueOf(this.appVsyncOffsetNanos), Long.valueOf(this.presentationDeadlineNanos), Integer.valueOf(this.group), Integer.valueOf(Arrays.hashCode(this.supportedHdrTypes)));
        }
    }

    public static void setDisplayPowerMode(IBinder displayToken, int mode) {
        if (displayToken == null) {
            throw new IllegalArgumentException("displayToken must not be null");
        }
        nativeSetDisplayPowerMode(displayToken, mode);
    }

    public static StaticDisplayInfo getStaticDisplayInfo(long displayId) {
        return nativeGetStaticDisplayInfo(displayId);
    }

    public static DynamicDisplayInfo getDynamicDisplayInfo(long displayId) {
        return nativeGetDynamicDisplayInfo(displayId);
    }

    public static DisplayedContentSamplingAttributes getDisplayedContentSamplingAttributes(IBinder displayToken) {
        if (displayToken == null) {
            throw new IllegalArgumentException("displayToken must not be null");
        }
        return nativeGetDisplayedContentSamplingAttributes(displayToken);
    }

    public static boolean setDisplayedContentSamplingEnabled(IBinder displayToken, boolean enable, int componentMask, int maxFrames) {
        if (displayToken == null) {
            throw new IllegalArgumentException("displayToken must not be null");
        }
        if ((componentMask >> 4) != 0) {
            throw new IllegalArgumentException("invalid componentMask when enabling sampling");
        }
        return nativeSetDisplayedContentSamplingEnabled(displayToken, enable, componentMask, maxFrames);
    }

    public static DisplayedContentSample getDisplayedContentSample(IBinder displayToken, long maxFrames, long timestamp) {
        if (displayToken == null) {
            throw new IllegalArgumentException("displayToken must not be null");
        }
        return nativeGetDisplayedContentSample(displayToken, maxFrames, timestamp);
    }

    public static final class RefreshRateRange implements Parcelable {
        public static final Parcelable.Creator<RefreshRateRange> CREATOR = new Parcelable.Creator<RefreshRateRange>() { // from class: android.view.SurfaceControl.RefreshRateRange.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public RefreshRateRange createFromParcel(Parcel in) {
                return new RefreshRateRange(in.readFloat(), in.readFloat());
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public RefreshRateRange[] newArray(int size) {
                return new RefreshRateRange[size];
            }
        };
        public static final float FLOAT_TOLERANCE = 0.01f;
        public static final String TAG = "RefreshRateRange";
        public float max;
        public float min;

        public RefreshRateRange() {
        }

        public RefreshRateRange(float min, float max) {
            if (min < 0.0f || max < 0.0f || min > 0.01f + max) {
                Slog.e(TAG, "Wrong values for min and max when initializing RefreshRateRange : " + min + " " + max);
                this.max = 0.0f;
                this.min = 0.0f;
            } else {
                if (min > max) {
                    min = max;
                    max = min;
                }
                this.min = min;
                this.max = max;
            }
        }

        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }
            if (!(other instanceof RefreshRateRange)) {
                return false;
            }
            RefreshRateRange refreshRateRange = (RefreshRateRange) other;
            return this.min == refreshRateRange.min && this.max == refreshRateRange.max;
        }

        public int hashCode() {
            return Objects.hash(Float.valueOf(this.min), Float.valueOf(this.max));
        }

        public String toString() {
            return NavigationBarInflaterView.KEY_CODE_START + this.min + " " + this.max + NavigationBarInflaterView.KEY_CODE_END;
        }

        public void copyFrom(RefreshRateRange other) {
            this.min = other.min;
            this.max = other.max;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeFloat(this.min);
            dest.writeFloat(this.max);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }
    }

    public static final class RefreshRateRanges {
        public static final String TAG = "RefreshRateRanges";
        public final RefreshRateRange physical;
        public final RefreshRateRange render;

        public RefreshRateRanges() {
            this.physical = new RefreshRateRange();
            this.render = new RefreshRateRange();
        }

        public RefreshRateRanges(RefreshRateRange physical, RefreshRateRange render) {
            this.physical = new RefreshRateRange(physical.min, physical.max);
            this.render = new RefreshRateRange(render.min, render.max);
        }

        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }
            if (!(other instanceof RefreshRateRanges)) {
                return false;
            }
            RefreshRateRanges rates = (RefreshRateRanges) other;
            return this.physical.equals(rates.physical) && this.render.equals(rates.render);
        }

        public int hashCode() {
            return Objects.hash(this.physical, this.render);
        }

        public String toString() {
            return "physical: " + this.physical + " render:  " + this.render;
        }

        public void copyFrom(RefreshRateRanges other) {
            this.physical.copyFrom(other.physical);
            this.render.copyFrom(other.render);
        }
    }

    public static final class IdleScreenRefreshRateConfig {
        public int timeoutMillis;

        public IdleScreenRefreshRateConfig() {
            this.timeoutMillis = -1;
        }

        public IdleScreenRefreshRateConfig(int timeoutMillis) {
            this.timeoutMillis = timeoutMillis;
        }

        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }
            if (!(other instanceof IdleScreenRefreshRateConfig) || other == null) {
                return false;
            }
            IdleScreenRefreshRateConfig idleScreenRefreshRateConfig = (IdleScreenRefreshRateConfig) other;
            return this.timeoutMillis == idleScreenRefreshRateConfig.timeoutMillis;
        }

        public int hashCode() {
            return Objects.hash(Integer.valueOf(this.timeoutMillis));
        }

        public String toString() {
            return "timeoutMillis: " + this.timeoutMillis;
        }

        public void copyFrom(IdleScreenRefreshRateConfig other) {
            if (other != null) {
                this.timeoutMillis = other.timeoutMillis;
            }
        }
    }

    public static final class DesiredDisplayModeSpecs {
        public boolean allowGroupSwitching;
        public final RefreshRateRanges appRequestRanges;
        public int defaultMode;
        public IdleScreenRefreshRateConfig idleScreenRefreshRateConfig;
        public final RefreshRateRanges primaryRanges;

        public DesiredDisplayModeSpecs() {
            this.primaryRanges = new RefreshRateRanges();
            this.appRequestRanges = new RefreshRateRanges();
        }

        public DesiredDisplayModeSpecs(DesiredDisplayModeSpecs other) {
            this.primaryRanges = new RefreshRateRanges();
            this.appRequestRanges = new RefreshRateRanges();
            copyFrom(other);
        }

        public DesiredDisplayModeSpecs(int defaultMode, boolean allowGroupSwitching, RefreshRateRanges primaryRanges, RefreshRateRanges appRequestRanges, IdleScreenRefreshRateConfig idleScreenRefreshRateConfig) {
            this.defaultMode = defaultMode;
            this.allowGroupSwitching = allowGroupSwitching;
            this.primaryRanges = new RefreshRateRanges(primaryRanges.physical, primaryRanges.render);
            this.appRequestRanges = new RefreshRateRanges(appRequestRanges.physical, appRequestRanges.render);
            this.idleScreenRefreshRateConfig = idleScreenRefreshRateConfig == null ? null : new IdleScreenRefreshRateConfig(idleScreenRefreshRateConfig.timeoutMillis);
        }

        public boolean equals(Object o) {
            return (o instanceof DesiredDisplayModeSpecs) && equals((DesiredDisplayModeSpecs) o);
        }

        public boolean equals(DesiredDisplayModeSpecs other) {
            return other != null && this.defaultMode == other.defaultMode && this.allowGroupSwitching == other.allowGroupSwitching && this.primaryRanges.equals(other.primaryRanges) && this.appRequestRanges.equals(other.appRequestRanges) && Objects.equals(this.idleScreenRefreshRateConfig, other.idleScreenRefreshRateConfig);
        }

        public int hashCode() {
            return 0;
        }

        public void copyFrom(DesiredDisplayModeSpecs other) {
            this.defaultMode = other.defaultMode;
            this.allowGroupSwitching = other.allowGroupSwitching;
            this.primaryRanges.copyFrom(other.primaryRanges);
            this.appRequestRanges.copyFrom(other.appRequestRanges);
            copyIdleScreenRefreshRateConfig(other.idleScreenRefreshRateConfig);
        }

        public String toString() {
            return "defaultMode=" + this.defaultMode + " allowGroupSwitching=" + this.allowGroupSwitching + " primaryRanges=" + this.primaryRanges + " appRequestRanges=" + this.appRequestRanges + " idleScreenRefreshRate=" + String.valueOf(this.idleScreenRefreshRateConfig);
        }

        private void copyIdleScreenRefreshRateConfig(IdleScreenRefreshRateConfig other) {
            if (this.idleScreenRefreshRateConfig == null) {
                if (other != null) {
                    this.idleScreenRefreshRateConfig = new IdleScreenRefreshRateConfig(other.timeoutMillis);
                }
            } else if (other == null) {
                this.idleScreenRefreshRateConfig = null;
            } else {
                this.idleScreenRefreshRateConfig.copyFrom(other);
            }
        }
    }

    public static boolean setDesiredDisplayModeSpecs(IBinder displayToken, DesiredDisplayModeSpecs desiredDisplayModeSpecs) {
        if (displayToken == null) {
            throw new IllegalArgumentException("displayToken must not be null");
        }
        if (desiredDisplayModeSpecs == null) {
            throw new IllegalArgumentException("desiredDisplayModeSpecs must not be null");
        }
        if (desiredDisplayModeSpecs.defaultMode < 0) {
            throw new IllegalArgumentException("defaultMode must be non-negative");
        }
        return nativeSetDesiredDisplayModeSpecs(displayToken, desiredDisplayModeSpecs);
    }

    public static DesiredDisplayModeSpecs getDesiredDisplayModeSpecs(IBinder displayToken) {
        if (displayToken == null) {
            throw new IllegalArgumentException("displayToken must not be null");
        }
        return nativeGetDesiredDisplayModeSpecs(displayToken);
    }

    public static DisplayPrimaries getDisplayNativePrimaries(IBinder displayToken) {
        if (displayToken == null) {
            throw new IllegalArgumentException("displayToken must not be null");
        }
        return nativeGetDisplayNativePrimaries(displayToken);
    }

    public static boolean setActiveColorMode(IBinder displayToken, int colorMode) {
        if (displayToken == null) {
            throw new IllegalArgumentException("displayToken must not be null");
        }
        return nativeSetActiveColorMode(displayToken, colorMode);
    }

    public static ColorSpace[] getCompositionColorSpaces() {
        int[] dataspaces = nativeGetCompositionDataspaces();
        ColorSpace srgb = ColorSpace.get(ColorSpace.Named.SRGB);
        ColorSpace[] colorSpaces = new ColorSpace[2];
        colorSpaces[0] = srgb;
        colorSpaces[1] = srgb;
        if (dataspaces.length == 2) {
            for (int i = 0; i < 2; i++) {
                ColorSpace cs = ColorSpace.getFromDataSpace(dataspaces[i]);
                if (cs != null) {
                    colorSpaces[i] = cs;
                }
            }
        }
        return colorSpaces;
    }

    public static OverlayProperties getOverlaySupport() {
        return nativeGetOverlaySupport();
    }

    public static boolean getBootDisplayModeSupport() {
        return nativeGetBootDisplayModeSupport();
    }

    public static void setBootDisplayMode(IBinder displayToken, int displayModeId) {
        if (displayToken == null) {
            throw new IllegalArgumentException("displayToken must not be null");
        }
        nativeSetBootDisplayMode(displayToken, displayModeId);
    }

    public static void clearBootDisplayMode(IBinder displayToken) {
        if (displayToken == null) {
            throw new IllegalArgumentException("displayToken must not be null");
        }
        nativeClearBootDisplayMode(displayToken);
    }

    public static void setAutoLowLatencyMode(IBinder displayToken, boolean on) {
        if (displayToken == null) {
            throw new IllegalArgumentException("displayToken must not be null");
        }
        nativeSetAutoLowLatencyMode(displayToken, on);
    }

    public static void setGameContentType(IBinder displayToken, boolean on) {
        if (displayToken == null) {
            throw new IllegalArgumentException("displayToken must not be null");
        }
        nativeSetGameContentType(displayToken, on);
    }

    public static long[] getPhysicalDisplayIds() {
        return nativeGetPhysicalDisplayIds();
    }

    public static IBinder getPhysicalDisplayToken(long physicalDisplayId) {
        return nativeGetPhysicalDisplayToken(physicalDisplayId);
    }

    public static boolean getProtectedContentSupport() {
        return nativeGetProtectedContentSupport();
    }

    public static boolean getDisplayBrightnessSupport(IBinder displayToken) {
        return nativeGetDisplayBrightnessSupport(displayToken);
    }

    public static boolean setDisplayBrightness(IBinder displayToken, float brightness) {
        return setDisplayBrightness(displayToken, brightness, -1.0f, brightness, -1.0f);
    }

    public static boolean setDisplayBrightness(IBinder displayToken, float sdrBrightness, float sdrBrightnessNits, float displayBrightness, float displayBrightnessNits) {
        Objects.requireNonNull(displayToken);
        if (Float.isNaN(displayBrightness) || displayBrightness > 1.0f || (displayBrightness < 0.0f && displayBrightness != -1.0f)) {
            throw new IllegalArgumentException("displayBrightness must be a number between 0.0f  and 1.0f, or -1 to turn the backlight off: " + displayBrightness);
        }
        if (Float.isNaN(sdrBrightness) || sdrBrightness > 1.0f || (sdrBrightness < 0.0f && sdrBrightness != -1.0f)) {
            throw new IllegalArgumentException("sdrBrightness must be a number between 0.0f and 1.0f, or -1 to turn the backlight off: " + sdrBrightness);
        }
        return nativeSetDisplayBrightness(displayToken, sdrBrightness, sdrBrightnessNits, displayBrightness, displayBrightnessNits);
    }

    public static SurfaceControl mirrorSurface(SurfaceControl mirrorOf) {
        long nativeObj = nativeMirrorSurface(mirrorOf.mNativeObject);
        SurfaceControl sc = new SurfaceControl();
        sc.mName = mirrorOf.mName + " (mirror)";
        sc.assignNativeObject(nativeObj, "mirrorSurface");
        return sc;
    }

    private static void validateColorArg(float[] color) {
        if (color.length != 4) {
            throw new IllegalArgumentException("Color must be specified as a float array with four values to represent r, g, b, a in range [0..1]");
        }
        for (float c : color) {
            if (c < 0.0f || c > 1.0f) {
                throw new IllegalArgumentException("Color must be specified as a float array with four values to represent r, g, b, a in range [0..1]");
            }
        }
    }

    public static void setGlobalShadowSettings(float[] ambientColor, float[] spotColor, float lightPosY, float lightPosZ, float lightRadius) {
        validateColorArg(ambientColor);
        validateColorArg(spotColor);
        nativeSetGlobalShadowSettings(ambientColor, spotColor, lightPosY, lightPosZ, lightRadius);
    }

    public static DisplayDecorationSupport getDisplayDecorationSupport(IBinder displayToken) {
        return nativeGetDisplayDecorationSupport(displayToken);
    }

    public static void addJankDataListener(OnJankDataListener listener, SurfaceControl surface) {
        nativeAddJankDataListener(listener.mNativePtr.get(), surface.mNativeObject);
    }

    public static void removeJankDataListener(OnJankDataListener listener) {
        nativeRemoveJankDataListener(listener.mNativePtr.get());
    }

    public static int getGPUContextPriority() {
        return nativeGetGPUContextPriority();
    }

    public static final class IdleBeginTime {
        public long beginTimeIdle;

        public String toString() {
            return "TimerInfo {beginTimeIdle=" + this.beginTimeIdle + "}";
        }
    }

    public static IdleBeginTime getIdleBeginTime(IBinder displayToken) {
        if (displayToken == null) {
            throw new IllegalArgumentException("displayToken must not be null");
        }
        return nativeGetIdleBeginTime(displayToken);
    }

    public static boolean bootFinished() {
        return nativeBootFinished();
    }

    public static final class TransactionStats {
        private long mLatchTimeNanos;
        private SyncFence mSyncFence;

        private TransactionStats(long latchTimeNanos, long presentFencePtr) {
            this.mLatchTimeNanos = latchTimeNanos;
            this.mSyncFence = new SyncFence(presentFencePtr);
        }

        public void close() {
            this.mSyncFence.close();
        }

        public long getLatchTimeNanos() {
            return this.mLatchTimeNanos;
        }

        public SyncFence getPresentFence() {
            return new SyncFence(this.mSyncFence);
        }
    }

    @Deprecated
    public static final class TrustedPresentationThresholds {
        private final float mMinAlpha;
        private final float mMinFractionRendered;
        private final int mStabilityRequirementMs;

        public TrustedPresentationThresholds(float minAlpha, float minFractionRendered, int stabilityRequirementMs) {
            this.mMinAlpha = minAlpha;
            this.mMinFractionRendered = minFractionRendered;
            this.mStabilityRequirementMs = stabilityRequirementMs;
            checkValid();
        }

        private void checkValid() {
            if (this.mMinAlpha <= 0.0f || this.mMinFractionRendered <= 0.0f || this.mStabilityRequirementMs < 1) {
                throw new IllegalArgumentException("TrustedPresentationThresholds values are invalid");
            }
        }
    }

    public static abstract class TrustedPresentationCallback {
        private static final NativeAllocationRegistry sRegistry = NativeAllocationRegistry.createMalloced(TrustedPresentationCallback.class.getClassLoader(), SurfaceControl.getNativeTrustedPresentationCallbackFinalizer());
        private final Runnable mFreeNativeResources;
        private final long mNativeObject;

        public abstract void onTrustedPresentationChanged(boolean z);

        private TrustedPresentationCallback() {
            this.mNativeObject = SurfaceControl.nativeCreateTpc(this);
            this.mFreeNativeResources = sRegistry.registerNativeAllocation(this, this.mNativeObject);
        }
    }

    public static class Transaction implements Closeable, Parcelable {
        public String mDebugName;
        Runnable mFreeNativeResources;
        public String mLowDebugName;
        public long mNativeObject;
        private final ArrayMap<SurfaceControl, SurfaceControl> mReparentedSurfaces;
        private final ArrayMap<SurfaceControl, Point> mResizedSurfaces;
        public static final NativeAllocationRegistry sRegistry = new NativeAllocationRegistry(Transaction.class.getClassLoader(), SurfaceControl.nativeGetNativeTransactionFinalizer(), 512);
        private static final float[] INVALID_COLOR = {-1.0f, -1.0f, -1.0f};
        public static final Parcelable.Creator<Transaction> CREATOR = new Parcelable.Creator<Transaction>() { // from class: android.view.SurfaceControl.Transaction.2
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Transaction createFromParcel(Parcel in) {
                return new Transaction(in);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Transaction[] newArray(int size) {
                return new Transaction[size];
            }
        };

        public void addDebugName(String debugName) {
            if (TextUtils.isEmpty(this.mDebugName)) {
                this.mDebugName = debugName;
            } else {
                this.mDebugName += " " + debugName;
            }
        }

        public void addLowDebugName(String lowDebugName) {
            if (TextUtils.isEmpty(this.mLowDebugName)) {
                this.mLowDebugName = lowDebugName;
            } else {
                this.mLowDebugName += " " + lowDebugName;
            }
        }

        protected void checkPreconditions(SurfaceControl sc) {
            sc.checkNotReleased();
        }

        public Transaction() {
            this(SurfaceControl.nativeCreateTransaction());
        }

        private Transaction(long nativeObject) {
            this.mResizedSurfaces = new ArrayMap<>();
            this.mReparentedSurfaces = new ArrayMap<>();
            this.mNativeObject = nativeObject;
            this.mFreeNativeResources = sRegistry.registerNativeAllocation(this, this.mNativeObject);
            if (!SurfaceControlRegistry.sCallStackDebuggingInitialized) {
                SurfaceControlRegistry.initializeCallStackDebugging();
            }
            if (CoreRune.FW_SURFACE_DEBUG_APPLY) {
                this.mDebugName = null;
                this.mLowDebugName = null;
            }
        }

        private Transaction(Parcel in) {
            this.mResizedSurfaces = new ArrayMap<>();
            this.mReparentedSurfaces = new ArrayMap<>();
            readFromParcel(in);
        }

        public static void setDefaultApplyToken(IBinder token) {
            if (CoreRune.FW_SHELL_TRANSITION_LOG) {
                Log.d(SurfaceControl.TAG, "setDefaultApplyToken, caller=" + Debug.getCallers(5));
            }
            SurfaceControl.nativeSetDefaultApplyToken(token);
        }

        public static IBinder getDefaultApplyToken() {
            return SurfaceControl.nativeGetDefaultApplyToken();
        }

        public void apply() {
            apply(false);
        }

        public void applyAsyncUnsafe() {
            apply(false, true);
        }

        public void clear() {
            this.mResizedSurfaces.clear();
            this.mReparentedSurfaces.clear();
            if (this.mNativeObject != 0) {
                SurfaceControl.nativeClearTransaction(this.mNativeObject);
            }
        }

        @Override // java.io.Closeable, java.lang.AutoCloseable
        public void close() {
            this.mResizedSurfaces.clear();
            this.mReparentedSurfaces.clear();
            this.mFreeNativeResources.run();
            this.mNativeObject = 0L;
        }

        public void apply(boolean sync) {
            apply(sync, false);
        }

        private void apply(boolean sync, boolean oneWay) {
            applyResizedSurfaces();
            notifyReparentedSurfaces();
            SurfaceControl.nativeApplyTransaction(this.mNativeObject, sync, oneWay);
            if (!SurfaceControlRegistry.sCallStackDebuggingEnabled) {
                if (CoreRune.FW_SURFACE_DEBUG_APPLY) {
                    if (TextUtils.isEmpty(this.mDebugName) && TextUtils.isEmpty(this.mLowDebugName)) {
                        return;
                    }
                } else {
                    return;
                }
            }
            SurfaceControlRegistry.getProcessInstance().checkCallStackDebugging("apply", this, null, null, CoreRune.FW_SURFACE_DEBUG_APPLY && !TextUtils.isEmpty(this.mDebugName));
            if (CoreRune.FW_SURFACE_DEBUG_APPLY) {
                if (!TextUtils.isEmpty(this.mLowDebugName)) {
                    Log.i(SurfaceControl.TAG, "apply, lowDebugName=" + this.mLowDebugName + ", caller=" + Debug.getCallers(6));
                }
                this.mDebugName = null;
                this.mLowDebugName = null;
            }
        }

        protected void applyResizedSurfaces() {
            for (int i = this.mResizedSurfaces.size() - 1; i >= 0; i--) {
                Point size = this.mResizedSurfaces.valueAt(i);
                SurfaceControl surfaceControl = this.mResizedSurfaces.keyAt(i);
                synchronized (surfaceControl.mLock) {
                    surfaceControl.resize(size.x, size.y);
                }
            }
            this.mResizedSurfaces.clear();
        }

        protected void notifyReparentedSurfaces() {
            int reparentCount = this.mReparentedSurfaces.size();
            for (int i = reparentCount - 1; i >= 0; i--) {
                SurfaceControl child = this.mReparentedSurfaces.keyAt(i);
                synchronized (child.mLock) {
                    int listenerCount = child.mReparentListeners != null ? child.mReparentListeners.size() : 0;
                    for (int j = 0; j < listenerCount; j++) {
                        OnReparentListener listener = (OnReparentListener) child.mReparentListeners.get(j);
                        listener.onReparent(this, this.mReparentedSurfaces.valueAt(i));
                    }
                    this.mReparentedSurfaces.removeAt(i);
                }
            }
        }

        public Transaction setVisibility(SurfaceControl sc, boolean visible) {
            checkPreconditions(sc);
            if (visible) {
                return show(sc);
            }
            return hide(sc);
        }

        public Transaction setFrameRateSelectionPriority(SurfaceControl sc, int priority) {
            checkPreconditions(sc);
            SurfaceControl.nativeSetFrameRateSelectionPriority(this.mNativeObject, sc.mNativeObject, priority);
            return this;
        }

        public Transaction show(SurfaceControl sc) {
            checkPreconditions(sc);
            if (SurfaceControlRegistry.sCallStackDebuggingEnabled || CoreRune.FW_SURFACE_DEBUG_VISIBILITY || !TextUtils.isEmpty(this.mDebugName)) {
                SurfaceControlRegistry.getProcessInstance().checkCallStackDebugging(ThreadedRenderer.OVERDRAW_PROPERTY_SHOW, this, sc, null, CoreRune.FW_SURFACE_DEBUG_VISIBILITY || !TextUtils.isEmpty(this.mDebugName));
            }
            SurfaceControl.nativeSetFlags(this.mNativeObject, sc.mNativeObject, 0, 1);
            return this;
        }

        public Transaction hide(SurfaceControl sc) {
            checkPreconditions(sc);
            if (SurfaceControlRegistry.sCallStackDebuggingEnabled || CoreRune.FW_SURFACE_DEBUG_VISIBILITY || !TextUtils.isEmpty(this.mDebugName)) {
                SurfaceControlRegistry.getProcessInstance().checkCallStackDebugging("hide", this, sc, null, CoreRune.FW_SURFACE_DEBUG_VISIBILITY || !TextUtils.isEmpty(this.mDebugName));
            }
            SurfaceControl.nativeSetFlags(this.mNativeObject, sc.mNativeObject, 1, 1);
            return this;
        }

        public Transaction setPosition(SurfaceControl sc, float x, float y) {
            checkPreconditions(sc);
            if (SurfaceControlRegistry.sCallStackDebuggingEnabled || CoreRune.FW_SURFACE_DEBUG_TRANSFORM) {
                SurfaceControlRegistry.getProcessInstance().checkCallStackDebugging("setPosition", this, sc, "x=" + x + " y=" + y, CoreRune.FW_SURFACE_DEBUG_TRANSFORM);
            }
            SurfaceControl.nativeSetPosition(this.mNativeObject, sc.mNativeObject, x, y);
            return this;
        }

        public Transaction setScale(SurfaceControl sc, float scaleX, float scaleY) {
            checkPreconditions(sc);
            Preconditions.checkArgument(scaleX >= 0.0f, "Negative value passed in for scaleX");
            Preconditions.checkArgument(scaleY >= 0.0f, "Negative value passed in for scaleY");
            if (SurfaceControlRegistry.sCallStackDebuggingEnabled) {
                SurfaceControlRegistry.getProcessInstance().checkCallStackDebugging("setScale", this, sc, "sx=" + scaleX + " sy=" + scaleY);
            }
            SurfaceControl.nativeSetScale(this.mNativeObject, sc.mNativeObject, scaleX, scaleY);
            return this;
        }

        public Transaction setBufferSize(SurfaceControl sc, int w, int h) {
            checkPreconditions(sc);
            if (SurfaceControlRegistry.sCallStackDebuggingEnabled) {
                SurfaceControlRegistry.getProcessInstance().checkCallStackDebugging("setBufferSize", this, sc, "w=" + w + " h=" + h);
            }
            this.mResizedSurfaces.put(sc, new Point(w, h));
            return this;
        }

        public Transaction setFixedTransformHint(SurfaceControl sc, int transformHint) {
            checkPreconditions(sc);
            if (SurfaceControlRegistry.sCallStackDebuggingEnabled) {
                SurfaceControlRegistry.getProcessInstance().checkCallStackDebugging("setFixedTransformHint", this, sc, "hint=" + transformHint);
            }
            SurfaceControl.nativeSetFixedTransformHint(this.mNativeObject, sc.mNativeObject, transformHint);
            return this;
        }

        public Transaction unsetFixedTransformHint(SurfaceControl sc) {
            checkPreconditions(sc);
            if (SurfaceControlRegistry.sCallStackDebuggingEnabled) {
                SurfaceControlRegistry.getProcessInstance().checkCallStackDebugging("unsetFixedTransformHint", this, sc, null);
            }
            SurfaceControl.nativeSetFixedTransformHint(this.mNativeObject, sc.mNativeObject, -1);
            return this;
        }

        public Transaction setLayer(SurfaceControl sc, int z) {
            checkPreconditions(sc);
            if (SurfaceControlRegistry.sCallStackDebuggingEnabled || CoreRune.FW_SURFACE_DEBUG_LAYER) {
                SurfaceControlRegistry.getProcessInstance().checkCallStackDebugging("setLayer", this, sc, "z=" + z, CoreRune.FW_SURFACE_DEBUG_LAYER);
            }
            SurfaceControl.nativeSetLayer(this.mNativeObject, sc.mNativeObject, z);
            return this;
        }

        public Transaction setRelativeLayer(SurfaceControl sc, SurfaceControl relativeTo, int z) {
            checkPreconditions(sc);
            if (SurfaceControlRegistry.sCallStackDebuggingEnabled || CoreRune.FW_SURFACE_DEBUG_LAYER) {
                SurfaceControlRegistry.getProcessInstance().checkCallStackDebugging("setRelativeLayer", this, sc, "relTo=" + relativeTo + " z=" + z, CoreRune.FW_SURFACE_DEBUG_LAYER);
            }
            SurfaceControl.nativeSetRelativeLayer(this.mNativeObject, sc.mNativeObject, relativeTo.mNativeObject, z);
            return this;
        }

        public Transaction setTransparentRegionHint(SurfaceControl sc, Region transparentRegion) {
            checkPreconditions(sc);
            if (SurfaceControlRegistry.sCallStackDebuggingEnabled) {
                SurfaceControlRegistry.getProcessInstance().checkCallStackDebugging("unsetFixedTransformHint", this, sc, "region=" + transparentRegion);
            }
            SurfaceControl.nativeSetTransparentRegionHint(this.mNativeObject, sc.mNativeObject, transparentRegion);
            return this;
        }

        public Transaction setAlpha(SurfaceControl sc, float alpha) {
            checkPreconditions(sc);
            if (SurfaceControlRegistry.sCallStackDebuggingEnabled || CoreRune.FW_SURFACE_DEBUG_ALPHA) {
                SurfaceControlRegistry.getProcessInstance().checkCallStackDebugging("setAlpha", this, sc, "alpha=" + alpha, CoreRune.FW_SURFACE_DEBUG_ALPHA);
            }
            SurfaceControl.nativeSetAlpha(this.mNativeObject, sc.mNativeObject, alpha);
            return this;
        }

        public Transaction setInputWindowInfo(SurfaceControl sc, InputWindowHandle handle) {
            checkPreconditions(sc);
            SurfaceControl.nativeSetInputWindowInfo(this.mNativeObject, sc.mNativeObject, handle);
            return this;
        }

        public Transaction addWindowInfosReportedListener(Runnable listener) {
            SurfaceControl.nativeAddWindowInfosReportedListener(this.mNativeObject, listener);
            return this;
        }

        public Transaction setGeometry(SurfaceControl sc, Rect sourceCrop, Rect destFrame, int orientation) {
            checkPreconditions(sc);
            SurfaceControl.nativeSetGeometry(this.mNativeObject, sc.mNativeObject, sourceCrop, destFrame, orientation);
            return this;
        }

        public Transaction setMatrix(SurfaceControl sc, float dsdx, float dtdx, float dtdy, float dsdy) {
            checkPreconditions(sc);
            if (SurfaceControlRegistry.sCallStackDebuggingEnabled || CoreRune.FW_SURFACE_DEBUG_TRANSFORM) {
                SurfaceControlRegistry.getProcessInstance().checkCallStackDebugging("setMatrix", this, sc, "dsdx=" + dsdx + " dtdx=" + dtdx + " dtdy=" + dtdy + " dsdy=" + dsdy, CoreRune.FW_SURFACE_DEBUG_TRANSFORM);
            }
            SurfaceControl.nativeSetMatrix(this.mNativeObject, sc.mNativeObject, dsdx, dtdx, dtdy, dsdy);
            return this;
        }

        public Transaction setMatrix(SurfaceControl sc, Matrix matrix, float[] float9) {
            matrix.getValues(float9);
            setMatrix(sc, float9[0], float9[3], float9[1], float9[4]);
            setPosition(sc, float9[2], float9[5]);
            return this;
        }

        public Transaction setColorTransform(SurfaceControl sc, float[] matrix, float[] translation) {
            checkPreconditions(sc);
            SurfaceControl.nativeSetColorTransform(this.mNativeObject, sc.mNativeObject, matrix, translation);
            return this;
        }

        public Transaction setColorSpaceAgnostic(SurfaceControl sc, boolean agnostic) {
            checkPreconditions(sc);
            SurfaceControl.nativeSetColorSpaceAgnostic(this.mNativeObject, sc.mNativeObject, agnostic);
            return this;
        }

        @Deprecated
        public Transaction setWindowCrop(SurfaceControl sc, Rect crop) {
            checkPreconditions(sc);
            if (SurfaceControlRegistry.sCallStackDebuggingEnabled || CoreRune.FW_SURFACE_DEBUG_CROP) {
                SurfaceControlRegistry.getProcessInstance().checkCallStackDebugging("setWindowCrop", this, sc, "crop=" + crop, CoreRune.FW_SURFACE_DEBUG_CROP);
            }
            if (crop != null) {
                SurfaceControl.nativeSetWindowCrop(this.mNativeObject, sc.mNativeObject, crop.left, crop.top, crop.right, crop.bottom);
            } else {
                SurfaceControl.nativeSetWindowCrop(this.mNativeObject, sc.mNativeObject, 0, 0, 0, 0);
            }
            return this;
        }

        public Transaction setCrop(SurfaceControl sc, Rect crop) {
            checkPreconditions(sc);
            if (SurfaceControlRegistry.sCallStackDebuggingEnabled || CoreRune.FW_SURFACE_DEBUG_CROP) {
                SurfaceControlRegistry.getProcessInstance().checkCallStackDebugging("setCrop", this, sc, "crop=" + crop, CoreRune.FW_SURFACE_DEBUG_CROP);
            }
            if (crop != null) {
                Preconditions.checkArgument(crop.isValid(), "Crop " + crop + " isn't valid");
                SurfaceControl.nativeSetWindowCrop(this.mNativeObject, sc.mNativeObject, crop.left, crop.top, crop.right, crop.bottom);
            } else {
                SurfaceControl.nativeSetWindowCrop(this.mNativeObject, sc.mNativeObject, 0, 0, 0, 0);
            }
            return this;
        }

        public Transaction setWindowCrop(SurfaceControl sc, int width, int height) {
            checkPreconditions(sc);
            if (SurfaceControlRegistry.sCallStackDebuggingEnabled || CoreRune.FW_SURFACE_DEBUG_CROP) {
                SurfaceControlRegistry.getProcessInstance().checkCallStackDebugging("setWindowCrop", this, sc, "w=" + width + " h=" + height, CoreRune.FW_SURFACE_DEBUG_CROP);
            }
            SurfaceControl.nativeSetWindowCrop(this.mNativeObject, sc.mNativeObject, 0, 0, width, height);
            return this;
        }

        public Transaction setCornerRadius(SurfaceControl sc, float cornerRadius) {
            checkPreconditions(sc);
            if (SurfaceControlRegistry.sCallStackDebuggingEnabled) {
                SurfaceControlRegistry.getProcessInstance().checkCallStackDebugging("setCornerRadius", this, sc, "cornerRadius=" + cornerRadius);
            }
            SurfaceControl.nativeSetCornerRadius(this.mNativeObject, sc.mNativeObject, cornerRadius);
            return this;
        }

        public Transaction setBackgroundBlurRadius(SurfaceControl sc, int radius) {
            checkPreconditions(sc);
            if (SurfaceControlRegistry.sCallStackDebuggingEnabled) {
                SurfaceControlRegistry.getProcessInstance().checkCallStackDebugging("setBackgroundBlurRadius", this, sc, "radius=" + radius);
            }
            SurfaceControl.nativeSetBackgroundBlurRadius(this.mNativeObject, sc.mNativeObject, radius);
            return this;
        }

        public Transaction setBlurRegions(SurfaceControl sc, float[][] regions) {
            checkPreconditions(sc);
            SurfaceControl.nativeSetBlurRegions(this.mNativeObject, sc.mNativeObject, regions, regions.length);
            return this;
        }

        public Transaction setStretchEffect(SurfaceControl sc, float width, float height, float vecX, float vecY, float maxStretchAmountX, float maxStretchAmountY, float childRelativeLeft, float childRelativeTop, float childRelativeRight, float childRelativeBottom) {
            checkPreconditions(sc);
            SurfaceControl.nativeSetStretchEffect(this.mNativeObject, sc.mNativeObject, width, height, vecX, vecY, maxStretchAmountX, maxStretchAmountY, childRelativeLeft, childRelativeTop, childRelativeRight, childRelativeBottom);
            return this;
        }

        public Transaction startSurfaceAnimation(SurfaceControl sc, String args) {
            checkPreconditions(sc);
            SurfaceControl.nativeStartSurfaceAnimation(this.mNativeObject, sc.mNativeObject, args);
            return this;
        }

        public Transaction setBackgroundBlurColorCurve(SurfaceControl sc, SemBlurInfo.ColorCurve colorCurve) {
            checkPreconditions(sc);
            float[] colorArgs = {colorCurve.mMinX, colorCurve.mMinY, colorCurve.mMaxX, colorCurve.mMaxY, colorCurve.mCurveBias, colorCurve.mSaturation};
            SurfaceControl.nativeSetBackgroundBlurColorCurve(this.mNativeObject, sc.mNativeObject, colorArgs);
            return this;
        }

        public Transaction setDisplayReluminoEffect(long physicalDisplayId, float edgeWidth, int colorId) {
            IBinder displayToken = SurfaceControl.getPhysicalDisplayToken(physicalDisplayId);
            if (displayToken == null) {
                throw new IllegalArgumentException("displayToken must not be null");
            }
            SurfaceControl.nativeSetDisplayReluminoEffect(this.mNativeObject, displayToken, edgeWidth, colorId);
            return this;
        }

        public Transaction setLayerStack(SurfaceControl sc, int layerStack) {
            checkPreconditions(sc);
            SurfaceControl.nativeSetLayerStack(this.mNativeObject, sc.mNativeObject, layerStack);
            return this;
        }

        public Transaction reparent(SurfaceControl sc, SurfaceControl newParent) {
            checkPreconditions(sc);
            if (SurfaceControlRegistry.sCallStackDebuggingEnabled || CoreRune.FW_SURFACE_DEBUG_REPARENT) {
                SurfaceControlRegistry.getProcessInstance().checkCallStackDebugging("reparent", this, sc, "newParent=" + newParent, CoreRune.FW_SURFACE_DEBUG_REPARENT);
            }
            long otherObject = 0;
            if (newParent != null) {
                newParent.checkNotReleased();
                otherObject = newParent.mNativeObject;
            }
            SurfaceControl.nativeReparent(this.mNativeObject, sc.mNativeObject, otherObject);
            this.mReparentedSurfaces.put(sc, newParent);
            return this;
        }

        public Transaction setColor(SurfaceControl sc, float[] color) {
            checkPreconditions(sc);
            if (SurfaceControlRegistry.sCallStackDebuggingEnabled) {
                SurfaceControlRegistry.getProcessInstance().checkCallStackDebugging("reparent", this, sc, "r=" + color[0] + " g=" + color[1] + " b=" + color[2]);
            }
            SurfaceControl.nativeSetColor(this.mNativeObject, sc.mNativeObject, color);
            return this;
        }

        public Transaction unsetColor(SurfaceControl sc) {
            checkPreconditions(sc);
            if (SurfaceControlRegistry.sCallStackDebuggingEnabled) {
                SurfaceControlRegistry.getProcessInstance().checkCallStackDebugging("unsetColor", this, sc, null);
            }
            SurfaceControl.nativeSetColor(this.mNativeObject, sc.mNativeObject, INVALID_COLOR);
            return this;
        }

        public Transaction setSecure(SurfaceControl sc, boolean isSecure) {
            checkPreconditions(sc);
            if (SurfaceControlRegistry.sCallStackDebuggingEnabled) {
                SurfaceControlRegistry.getProcessInstance().checkCallStackDebugging("setSecure", this, sc, "secure=" + isSecure);
            }
            if (isSecure) {
                SurfaceControl.nativeSetFlags(this.mNativeObject, sc.mNativeObject, 128, 128);
            } else {
                SurfaceControl.nativeSetFlags(this.mNativeObject, sc.mNativeObject, 0, 128);
            }
            return this;
        }

        public Transaction setDisplayDecoration(SurfaceControl sc, boolean displayDecoration) {
            checkPreconditions(sc);
            if (displayDecoration) {
                SurfaceControl.nativeSetFlags(this.mNativeObject, sc.mNativeObject, 512, 512);
            } else {
                SurfaceControl.nativeSetFlags(this.mNativeObject, sc.mNativeObject, 0, 512);
            }
            return this;
        }

        public Transaction setOpaque(SurfaceControl sc, boolean isOpaque) {
            checkPreconditions(sc);
            if (SurfaceControlRegistry.sCallStackDebuggingEnabled) {
                SurfaceControlRegistry.getProcessInstance().checkCallStackDebugging("setOpaque", this, sc, "opaque=" + isOpaque);
            }
            if (isOpaque) {
                SurfaceControl.nativeSetFlags(this.mNativeObject, sc.mNativeObject, 2, 2);
            } else {
                SurfaceControl.nativeSetFlags(this.mNativeObject, sc.mNativeObject, 0, 2);
            }
            return this;
        }

        public Transaction setDisplaySurface(IBinder displayToken, Surface surface) {
            if (displayToken == null) {
                throw new IllegalArgumentException("displayToken must not be null");
            }
            if (surface != null) {
                synchronized (surface.mLock) {
                    SurfaceControl.nativeSetDisplaySurface(this.mNativeObject, displayToken, surface.mNativeObject);
                }
            } else {
                SurfaceControl.nativeSetDisplaySurface(this.mNativeObject, displayToken, 0L);
            }
            return this;
        }

        public Transaction setDisplayLayerStack(IBinder displayToken, int layerStack) {
            if (displayToken == null) {
                throw new IllegalArgumentException("displayToken must not be null");
            }
            SurfaceControl.nativeSetDisplayLayerStack(this.mNativeObject, displayToken, layerStack);
            return this;
        }

        public Transaction setDisplayFlags(IBinder displayToken, int flags) {
            if (displayToken == null) {
                throw new IllegalArgumentException("displayToken must not be null");
            }
            SurfaceControl.nativeSetDisplayFlags(this.mNativeObject, displayToken, flags);
            return this;
        }

        public Transaction setDisplayProjection(IBinder displayToken, int orientation, Rect layerStackRect, Rect displayRect) {
            if (displayToken == null) {
                throw new IllegalArgumentException("displayToken must not be null");
            }
            if (layerStackRect == null) {
                throw new IllegalArgumentException("layerStackRect must not be null");
            }
            if (displayRect == null) {
                throw new IllegalArgumentException("displayRect must not be null");
            }
            SurfaceControl.nativeSetDisplayProjection(this.mNativeObject, displayToken, orientation, layerStackRect.left, layerStackRect.top, layerStackRect.right, layerStackRect.bottom, displayRect.left, displayRect.top, displayRect.right, displayRect.bottom);
            return this;
        }

        public Transaction setDisplaySize(IBinder displayToken, int width, int height) {
            if (displayToken == null) {
                throw new IllegalArgumentException("displayToken must not be null");
            }
            if (width <= 0 || height <= 0) {
                throw new IllegalArgumentException("width and height must be positive");
            }
            SurfaceControl.nativeSetDisplaySize(this.mNativeObject, displayToken, width, height);
            return this;
        }

        public Transaction setAnimationTransaction() {
            SurfaceControl.nativeSetAnimationTransaction(this.mNativeObject);
            return this;
        }

        public Transaction setEarlyWakeupStart() {
            SurfaceControl.nativeSetEarlyWakeupStart(this.mNativeObject);
            return this;
        }

        public Transaction setEarlyWakeupEnd() {
            SurfaceControl.nativeSetEarlyWakeupEnd(this.mNativeObject);
            return this;
        }

        public long getId() {
            return SurfaceControl.nativeGetTransactionId(this.mNativeObject);
        }

        public Transaction setMetadata(SurfaceControl sc, int key, int data) {
            Parcel parcel = Parcel.obtain();
            parcel.writeInt(data);
            try {
                setMetadata(sc, key, parcel);
                return this;
            } finally {
                parcel.recycle();
            }
        }

        public Transaction setMetadata(SurfaceControl sc, int key, Parcel data) {
            checkPreconditions(sc);
            SurfaceControl.nativeSetMetadata(this.mNativeObject, sc.mNativeObject, key, data);
            return this;
        }

        public Transaction setShadowRadius(SurfaceControl sc, float shadowRadius) {
            checkPreconditions(sc);
            if (SurfaceControlRegistry.sCallStackDebuggingEnabled) {
                SurfaceControlRegistry.getProcessInstance().checkCallStackDebugging("setShadowRadius", this, sc, "radius=" + shadowRadius);
            }
            SurfaceControl.nativeSetShadowRadius(this.mNativeObject, sc.mNativeObject, shadowRadius);
            return this;
        }

        public Transaction setFrameRate(SurfaceControl sc, float frameRate, int compatibility) {
            return setFrameRate(sc, frameRate, compatibility, 0);
        }

        public Transaction setFrameRate(SurfaceControl sc, float frameRate, int compatibility, int changeFrameRateStrategy) {
            checkPreconditions(sc);
            SurfaceControl.nativeSetFrameRate(this.mNativeObject, sc.mNativeObject, frameRate, compatibility, changeFrameRateStrategy);
            return this;
        }

        public Transaction clearFrameRate(SurfaceControl sc) {
            checkPreconditions(sc);
            SurfaceControl.nativeSetFrameRate(this.mNativeObject, sc.mNativeObject, 0.0f, 0, 1);
            return this;
        }

        public Transaction setDefaultFrameRateCompatibility(SurfaceControl sc, int compatibility) {
            checkPreconditions(sc);
            SurfaceControl.nativeSetDefaultFrameRateCompatibility(this.mNativeObject, sc.mNativeObject, compatibility);
            return this;
        }

        public Transaction setFrameRateCategory(SurfaceControl sc, int category, boolean smoothSwitchOnly) {
            checkPreconditions(sc);
            SurfaceControl.nativeSetFrameRateCategory(this.mNativeObject, sc.mNativeObject, category, smoothSwitchOnly);
            return this;
        }

        public Transaction setFrameRateSelectionStrategy(SurfaceControl sc, int strategy) {
            checkPreconditions(sc);
            SurfaceControl.nativeSetFrameRateSelectionStrategy(this.mNativeObject, sc.mNativeObject, strategy);
            return this;
        }

        public Transaction setFocusedWindow(IBinder token, String windowName, int displayId) {
            SurfaceControl.nativeSetFocusedWindow(this.mNativeObject, token, windowName, displayId);
            return this;
        }

        public Transaction removeCurrentInputFocus(int displayId) {
            SurfaceControl.nativeRemoveCurrentInputFocus(this.mNativeObject, displayId);
            return this;
        }

        public Transaction setSkipScreenshot(SurfaceControl sc, boolean skipScrenshot) {
            checkPreconditions(sc);
            if (skipScrenshot) {
                SurfaceControl.nativeSetFlags(this.mNativeObject, sc.mNativeObject, 64, 64);
            } else {
                SurfaceControl.nativeSetFlags(this.mNativeObject, sc.mNativeObject, 0, 64);
            }
            return this;
        }

        public Transaction setDisableSuperHDR(SurfaceControl sc, boolean disableSuperHdr) {
            checkPreconditions(sc);
            if (disableSuperHdr) {
                SurfaceControl.nativeSetFlags(this.mNativeObject, sc.mNativeObject, 268435456, 268435456);
            } else {
                SurfaceControl.nativeSetFlags(this.mNativeObject, sc.mNativeObject, 0, 268435456);
            }
            return this;
        }

        @Deprecated
        public Transaction setBuffer(SurfaceControl sc, GraphicBuffer buffer) {
            return setBuffer(sc, HardwareBuffer.createFromGraphicBuffer(buffer));
        }

        public Transaction setBuffer(SurfaceControl sc, HardwareBuffer buffer) {
            return setBuffer(sc, buffer, null);
        }

        public Transaction unsetBuffer(SurfaceControl sc) {
            SurfaceControl.nativeUnsetBuffer(this.mNativeObject, sc.mNativeObject);
            return this;
        }

        public Transaction setBuffer(SurfaceControl sc, HardwareBuffer buffer, SyncFence fence) {
            return setBuffer(sc, buffer, fence, null);
        }

        public Transaction setBuffer(SurfaceControl sc, HardwareBuffer buffer, SyncFence fence, Consumer<SyncFence> releaseCallback) {
            checkPreconditions(sc);
            if (fence != null) {
                synchronized (fence.getLock()) {
                    SurfaceControl.nativeSetBuffer(this.mNativeObject, sc.mNativeObject, buffer, fence.getNativeFence(), releaseCallback);
                }
            } else {
                SurfaceControl.nativeSetBuffer(this.mNativeObject, sc.mNativeObject, buffer, 0L, releaseCallback);
            }
            return this;
        }

        public Transaction setBufferTransform(SurfaceControl sc, int transform) {
            checkPreconditions(sc);
            SurfaceControl.nativeSetBufferTransform(this.mNativeObject, sc.mNativeObject, transform);
            return this;
        }

        public Transaction setDamageRegion(SurfaceControl sc, Region region) {
            SurfaceControl.nativeSetDamageRegion(this.mNativeObject, sc.mNativeObject, region);
            return this;
        }

        public Transaction setDimmingEnabled(SurfaceControl sc, boolean dimmingEnabled) {
            checkPreconditions(sc);
            SurfaceControl.nativeSetDimmingEnabled(this.mNativeObject, sc.mNativeObject, dimmingEnabled);
            return this;
        }

        @Deprecated
        public Transaction setColorSpace(SurfaceControl sc, ColorSpace colorSpace) {
            checkPreconditions(sc);
            if (colorSpace.getId() == ColorSpace.Named.DISPLAY_P3.ordinal()) {
                setDataSpace(sc, 143261696);
            } else {
                setDataSpace(sc, 142671872);
            }
            return this;
        }

        public Transaction setDataSpace(SurfaceControl sc, int dataspace) {
            checkPreconditions(sc);
            SurfaceControl.nativeSetDataSpace(this.mNativeObject, sc.mNativeObject, dataspace);
            return this;
        }

        public Transaction setExtendedRangeBrightness(SurfaceControl sc, float currentBufferRatio, float desiredRatio) {
            checkPreconditions(sc);
            if (!Float.isFinite(currentBufferRatio) || currentBufferRatio < 1.0f) {
                throw new IllegalArgumentException("currentBufferRatio must be finite && >= 1.0f; got " + currentBufferRatio);
            }
            if (!Float.isFinite(desiredRatio) || desiredRatio < 1.0f) {
                throw new IllegalArgumentException("desiredRatio must be finite && >= 1.0f; got " + desiredRatio);
            }
            SurfaceControl.nativeSetExtendedRangeBrightness(this.mNativeObject, sc.mNativeObject, currentBufferRatio, desiredRatio);
            return this;
        }

        public Transaction setDesiredHdrHeadroom(SurfaceControl sc, float desiredRatio) {
            checkPreconditions(sc);
            if (!Float.isFinite(desiredRatio) || (desiredRatio != 0.0f && desiredRatio < 1.0f)) {
                throw new IllegalArgumentException("desiredRatio must be finite && >= 1.0f or 0; got " + desiredRatio);
            }
            SurfaceControl.nativeSetDesiredHdrHeadroom(this.mNativeObject, sc.mNativeObject, desiredRatio);
            return this;
        }

        public Transaction setCachingHint(SurfaceControl sc, int cachingHint) {
            checkPreconditions(sc);
            SurfaceControl.nativeSetCachingHint(this.mNativeObject, sc.mNativeObject, cachingHint);
            return this;
        }

        public Transaction setTrustedOverlay(SurfaceControl sc, boolean isTrustedOverlay) {
            return setTrustedOverlay(sc, isTrustedOverlay ? 2 : 0);
        }

        public Transaction setTrustedOverlay(SurfaceControl sc, int trustedOverlay) {
            checkPreconditions(sc);
            SurfaceControl.nativeSetTrustedOverlay(this.mNativeObject, sc.mNativeObject, trustedOverlay);
            return this;
        }

        public Transaction setDropInputMode(SurfaceControl sc, int mode) {
            checkPreconditions(sc);
            SurfaceControl.nativeSetDropInputMode(this.mNativeObject, sc.mNativeObject, mode);
            return this;
        }

        public Transaction setCanOccludePresentation(SurfaceControl sc, boolean canOccludePresentation) {
            checkPreconditions(sc);
            int value = canOccludePresentation ? 4096 : 0;
            SurfaceControl.nativeSetFlags(this.mNativeObject, sc.mNativeObject, value, 4096);
            return this;
        }

        public static void sendSurfaceFlushJankData(SurfaceControl sc) {
            sc.checkNotReleased();
            SurfaceControl.nativeSurfaceFlushJankData(sc.mNativeObject);
        }

        public void sanitize(int pid, int uid) {
            SurfaceControl.nativeSanitize(this.mNativeObject, pid, uid);
        }

        public Transaction setDesintationFrame(SurfaceControl sc, Rect destinationFrame) {
            checkPreconditions(sc);
            SurfaceControl.nativeSetDestinationFrame(this.mNativeObject, sc.mNativeObject, destinationFrame.left, destinationFrame.top, destinationFrame.right, destinationFrame.bottom);
            return this;
        }

        public Transaction setDesintationFrame(SurfaceControl sc, int width, int height) {
            checkPreconditions(sc);
            SurfaceControl.nativeSetDestinationFrame(this.mNativeObject, sc.mNativeObject, 0, 0, width, height);
            return this;
        }

        public Transaction merge(Transaction other) {
            if (this == other) {
                return this;
            }
            if (CoreRune.FW_SURFACE_DEBUG_APPLY) {
                if (!TextUtils.isEmpty(other.mDebugName)) {
                    if (this.mDebugName == null) {
                        this.mDebugName = "";
                    } else {
                        this.mDebugName += ", ";
                    }
                    this.mDebugName += other.mDebugName;
                    other.mDebugName = null;
                }
                if (!TextUtils.isEmpty(other.mLowDebugName)) {
                    if (this.mLowDebugName == null) {
                        this.mLowDebugName = "";
                    } else {
                        this.mLowDebugName += ", ";
                    }
                    this.mLowDebugName += other.mLowDebugName;
                    other.mLowDebugName = null;
                }
            }
            this.mResizedSurfaces.putAll((ArrayMap<? extends SurfaceControl, ? extends Point>) other.mResizedSurfaces);
            other.mResizedSurfaces.clear();
            this.mReparentedSurfaces.putAll((ArrayMap<? extends SurfaceControl, ? extends SurfaceControl>) other.mReparentedSurfaces);
            other.mReparentedSurfaces.clear();
            SurfaceControl.nativeMergeTransaction(this.mNativeObject, other.mNativeObject);
            return this;
        }

        public Transaction remove(SurfaceControl sc) {
            reparent(sc, null);
            sc.release();
            return this;
        }

        public Transaction setFrameTimeline(long vsyncId) {
            if (!Flags.sdkDesiredPresentTime()) {
                Log.w(SurfaceControl.TAG, "addTransactionCompletedListener was called but flag is disabled");
                return this;
            }
            SurfaceControl.nativeSetFrameTimelineVsync(this.mNativeObject, vsyncId);
            return this;
        }

        public Transaction setFrameTimelineVsync(long frameTimelineVsyncId) {
            SurfaceControl.nativeSetFrameTimelineVsync(this.mNativeObject, frameTimelineVsyncId);
            return this;
        }

        public Transaction addTransactionCommittedListener(final Executor executor, final TransactionCommittedListener listener) {
            TransactionCommittedListener listenerInner = new TransactionCommittedListener() { // from class: android.view.SurfaceControl$Transaction$$ExternalSyntheticLambda2
                @Override // android.view.SurfaceControl.TransactionCommittedListener
                public final void onTransactionCommitted() {
                    SurfaceControl.Transaction.lambda$addTransactionCommittedListener$0(executor, listener);
                }
            };
            SurfaceControl.nativeAddTransactionCommittedListener(this.mNativeObject, listenerInner);
            return this;
        }

        static /* synthetic */ void lambda$addTransactionCommittedListener$0(Executor executor, final TransactionCommittedListener listener) {
            Objects.requireNonNull(listener);
            executor.execute(new Runnable() { // from class: android.view.SurfaceControl$Transaction$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    SurfaceControl.TransactionCommittedListener.this.onTransactionCommitted();
                }
            });
        }

        public Transaction addTransactionCompletedListener(final Executor executor, final Consumer<TransactionStats> listener) {
            if (!Flags.sdkDesiredPresentTime()) {
                Log.w(SurfaceControl.TAG, "addTransactionCompletedListener was called but flag is disabled");
                return this;
            }
            Consumer<TransactionStats> listenerInner = new Consumer() { // from class: android.view.SurfaceControl$Transaction$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    executor.execute(new Runnable() { // from class: android.view.SurfaceControl$Transaction$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            r1.andThen(new Consumer() { // from class: android.view.SurfaceControl$Transaction$$ExternalSyntheticLambda0
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj2) {
                                    ((SurfaceControl.TransactionStats) obj2).close();
                                }
                            }).accept(r2);
                        }
                    });
                }
            };
            SurfaceControl.nativeAddTransactionCompletedListener(this.mNativeObject, listenerInner);
            return this;
        }

        /* renamed from: android.view.SurfaceControl$Transaction$1, reason: invalid class name */
        class AnonymousClass1 extends TrustedPresentationCallback {
            final /* synthetic */ Executor val$executor;
            final /* synthetic */ Consumer val$listener;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AnonymousClass1(Executor executor, Consumer consumer) {
                super();
                this.val$executor = executor;
                this.val$listener = consumer;
            }

            @Override // android.view.SurfaceControl.TrustedPresentationCallback
            public void onTrustedPresentationChanged(final boolean inTrustedPresentationState) {
                Executor executor = this.val$executor;
                final Consumer consumer = this.val$listener;
                executor.execute(new Runnable() { // from class: android.view.SurfaceControl$Transaction$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        consumer.accept(Boolean.valueOf(inTrustedPresentationState));
                    }
                });
            }
        }

        @Deprecated
        public Transaction setTrustedPresentationCallback(SurfaceControl sc, TrustedPresentationThresholds thresholds, Executor executor, Consumer<Boolean> listener) {
            checkPreconditions(sc);
            TrustedPresentationCallback tpc = new AnonymousClass1(executor, listener);
            if (sc.mTrustedPresentationCallback != null) {
                sc.mTrustedPresentationCallback.mFreeNativeResources.run();
            }
            SurfaceControl.nativeSetTrustedPresentationCallback(this.mNativeObject, sc.mNativeObject, tpc.mNativeObject, thresholds);
            sc.mTrustedPresentationCallback = tpc;
            return this;
        }

        @Deprecated
        public Transaction clearTrustedPresentationCallback(SurfaceControl sc) {
            checkPreconditions(sc);
            SurfaceControl.nativeClearTrustedPresentationCallback(this.mNativeObject, sc.mNativeObject);
            if (sc.mTrustedPresentationCallback != null) {
                sc.mTrustedPresentationCallback.mFreeNativeResources.run();
                sc.mTrustedPresentationCallback = null;
            }
            return this;
        }

        public Transaction setDesiredPresentTimeNanos(long desiredPresentTimeNanos) {
            if (!Flags.sdkDesiredPresentTime()) {
                Log.w(SurfaceControl.TAG, "addTransactionCompletedListener was called but flag is disabled");
                return this;
            }
            SurfaceControl.nativeSetDesiredPresentTimeNanos(this.mNativeObject, desiredPresentTimeNanos);
            return this;
        }

        public Transaction startChangeResolution(IBinder displayToken, boolean enabled) {
            if (displayToken == null) {
                throw new IllegalArgumentException("displayToken must not be null");
            }
            Log.i(SurfaceControl.TAG, "startChangeResolution, enabled=" + enabled + ", caller=" + Debug.getCallers(5));
            SurfaceControl.nativeStartChangeResolution(this.mNativeObject, displayToken, enabled);
            return this;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            if (this.mNativeObject == 0) {
                dest.writeInt(0);
                return;
            }
            dest.writeInt(1);
            SurfaceControl.nativeWriteTransactionToParcel(this.mNativeObject, dest);
            if ((flags & 1) != 0) {
                SurfaceControl.nativeClearTransaction(this.mNativeObject);
            }
            if (CoreRune.FW_SURFACE_DEBUG_APPLY) {
                dest.writeString(this.mDebugName);
                this.mDebugName = null;
                dest.writeString(this.mLowDebugName);
                this.mLowDebugName = null;
            }
        }

        private void readFromParcel(Parcel in) {
            this.mNativeObject = 0L;
            if (in.readInt() != 0) {
                this.mNativeObject = SurfaceControl.nativeReadTransactionFromParcel(in);
                this.mFreeNativeResources = sRegistry.registerNativeAllocation(this, this.mNativeObject);
                if (CoreRune.FW_SURFACE_DEBUG_APPLY) {
                    this.mDebugName = in.readString();
                    this.mLowDebugName = in.readString();
                }
            }
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }
    }

    public static class LockDebuggingTransaction extends Transaction {
        Object mMonitor;

        public LockDebuggingTransaction(Object o) {
            this.mMonitor = o;
        }

        @Override // android.view.SurfaceControl.Transaction
        protected void checkPreconditions(SurfaceControl sc) {
            super.checkPreconditions(sc);
            if (!Thread.holdsLock(this.mMonitor)) {
                throw new RuntimeException("Unlocked access to synchronized SurfaceControl.Transaction");
            }
        }
    }

    public void resize(int w, int h) {
        this.mWidth = w;
        this.mHeight = h;
        nativeUpdateDefaultBufferSize(this.mNativeObject, w, h);
    }

    public int getTransformHint() {
        checkNotReleased();
        return nativeGetTransformHint(this.mNativeObject);
    }

    public void setTransformHint(int transformHint) {
        nativeSetTransformHint(this.mNativeObject, transformHint);
    }

    public int getLayerId() {
        if (this.mNativeObject != 0) {
            return nativeGetLayerId(this.mNativeObject);
        }
        return -1;
    }

    private static void invokeReleaseCallback(Consumer<SyncFence> callback, long nativeFencePtr) {
        SyncFence fence = new SyncFence(nativeFencePtr);
        callback.accept(fence);
    }

    public static StalledTransactionInfo getStalledTransactionInfo(int pid) {
        return nativeGetStalledTransactionInfo(pid);
    }

    public static void notifyShutdown() {
        nativeNotifyShutdown();
    }

    public void enableIgnoreToUpdateByOtherProcess() {
        this.mIgnoreToUpdateByOtherProcess = true;
    }

    public static IBinder getDisplayToken(DisplayAddress displayAddress) {
        if (displayAddress instanceof DisplayAddress.Physical) {
            return getPhysicalDisplayToken(((DisplayAddress.Physical) displayAddress).getPhysicalDisplayId());
        }
        return null;
    }

    public static void notifyHFRmode(IBinder displayToken, int hfrMode) {
        if (CoreRune.FW_VRR_POLICY) {
            Log.d(TAG, "notifyHFRmode, displayToken=" + displayToken + ", hfrMode=" + Settings.Secure.refreshRateModeToString(hfrMode));
            if (displayToken == null) {
                throw new IllegalArgumentException("displayToken must not be null");
            }
            nativeNotifyHFRmode(displayToken, hfrMode);
        }
    }

    public static void restrictHighRefreshRate(boolean enabled) {
        Log.d(TAG, "restrictHighRefreshRate, enabled=" + enabled);
        nativeRestrictHighRefreshRate(enabled);
    }
}
