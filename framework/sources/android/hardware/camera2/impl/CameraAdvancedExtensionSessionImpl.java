package android.hardware.camera2.impl;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.SyncFence;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraExtensionCharacteristics;
import android.hardware.camera2.CameraExtensionSession;
import android.hardware.camera2.CaptureFailure;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.extension.CameraOutputConfig;
import android.hardware.camera2.extension.CameraSessionConfig;
import android.hardware.camera2.extension.IAdvancedExtenderImpl;
import android.hardware.camera2.extension.ICaptureCallback;
import android.hardware.camera2.extension.IImageProcessorImpl;
import android.hardware.camera2.extension.IInitializeSessionCallback;
import android.hardware.camera2.extension.IRequestCallback;
import android.hardware.camera2.extension.IRequestProcessorImpl;
import android.hardware.camera2.extension.ISessionProcessorImpl;
import android.hardware.camera2.extension.LatencyPair;
import android.hardware.camera2.extension.OutputConfigId;
import android.hardware.camera2.extension.OutputSurface;
import android.hardware.camera2.extension.ParcelCaptureResult;
import android.hardware.camera2.extension.ParcelImage;
import android.hardware.camera2.extension.ParcelTotalCaptureResult;
import android.hardware.camera2.extension.Request;
import android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl;
import android.hardware.camera2.impl.CameraExtensionUtils;
import android.hardware.camera2.params.ExtensionSessionConfiguration;
import android.hardware.camera2.params.OutputConfiguration;
import android.hardware.camera2.params.SessionConfiguration;
import android.hardware.camera2.utils.ExtensionSessionStatsAggregator;
import android.hardware.camera2.utils.SurfaceUtils;
import android.media.Image;
import android.media.ImageReader;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.util.Size;
import android.view.Surface;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

/* loaded from: classes.dex */
public final class CameraAdvancedExtensionSessionImpl extends CameraExtensionSession {
    private static final String TAG = "CameraAdvancedExtensionSessionImpl";
    private IAdvancedExtenderImpl mAdvancedExtender;
    private final CameraExtensionSession.StateCallback mCallbacks;
    private CameraDevice mCameraDevice;
    private final Map<String, CameraMetadataNative> mCharacteristicsMap;
    private Surface mClientCaptureSurface;
    private Surface mClientPostviewSurface;
    private Surface mClientRepeatingRequestSurface;
    private final Context mContext;
    private final Executor mExecutor;
    private final Handler mHandler;
    private final HandlerThread mHandlerThread;
    private final InitializeSessionHandler mInitializeHandler;
    private boolean mInitialized;
    final Object mInterfaceLock;
    private boolean mSessionClosed;
    private final int mSessionId;
    private final ExtensionSessionStatsAggregator mStatsAggregator;
    private IBinder mToken;
    private final HashMap<Surface, CameraOutputConfig> mCameraConfigMap = new HashMap<>();
    private final HashMap<Integer, ImageReader> mReaderMap = new HashMap<>();
    private RequestProcessor mRequestProcessor = new RequestProcessor();
    private CameraCaptureSession mCaptureSession = null;
    private ISessionProcessorImpl mSessionProcessor = null;

    public static CameraAdvancedExtensionSessionImpl createCameraAdvancedExtensionSession(CameraDeviceImpl cameraDevice, Map<String, CameraCharacteristics> characteristicsMap, Context ctx, ExtensionSessionConfiguration config, int sessionId, IBinder token) throws CameraAccessException, RemoteException {
        int suitableSurfaceCount;
        Surface postviewSurface;
        CameraExtensionCharacteristics extensionChars;
        String cameraId = cameraDevice.getId();
        CameraExtensionCharacteristics extensionChars2 = new CameraExtensionCharacteristics(ctx, cameraId, characteristicsMap);
        Map<String, CameraMetadataNative> characteristicsMapNative = CameraExtensionUtils.getCharacteristicsMapNative(characteristicsMap);
        if (!CameraExtensionCharacteristics.isExtensionSupported(cameraDevice.getId(), config.getExtension(), characteristicsMapNative)) {
            throw new UnsupportedOperationException("Unsupported extension type: " + config.getExtension());
        }
        if (!config.getOutputConfigurations().isEmpty() && config.getOutputConfigurations().size() <= 2) {
            for (OutputConfiguration c : config.getOutputConfigurations()) {
                if (c.getDynamicRangeProfile() != 1) {
                    throw new IllegalArgumentException("Unsupported dynamic range profile: " + c.getDynamicRangeProfile());
                }
                if (c.getStreamUseCase() != 0) {
                    throw new IllegalArgumentException("Unsupported stream use case: " + c.getStreamUseCase());
                }
            }
            int suitableSurfaceCount2 = 0;
            List<Size> supportedPreviewSizes = extensionChars2.getExtensionSupportedSizes(config.getExtension(), SurfaceTexture.class);
            Surface repeatingRequestSurface = CameraExtensionUtils.getRepeatingRequestSurface(config.getOutputConfigurations(), supportedPreviewSizes);
            if (repeatingRequestSurface != null) {
                suitableSurfaceCount2 = 0 + 1;
            }
            HashMap<Integer, List<Size>> supportedCaptureSizes = new HashMap<>();
            int i = 0;
            for (int format : CameraExtensionUtils.SUPPORTED_CAPTURE_OUTPUT_FORMATS) {
                List<Size> supportedSizes = extensionChars2.getExtensionSupportedSizes(config.getExtension(), format);
                if (supportedSizes != null) {
                    supportedCaptureSizes.put(Integer.valueOf(format), supportedSizes);
                }
            }
            Surface burstCaptureSurface = CameraExtensionUtils.getBurstCaptureSurface(config.getOutputConfigurations(), supportedCaptureSizes);
            if (burstCaptureSurface == null) {
                suitableSurfaceCount = suitableSurfaceCount2;
            } else {
                suitableSurfaceCount = suitableSurfaceCount2 + 1;
            }
            if (suitableSurfaceCount != config.getOutputConfigurations().size()) {
                throw new IllegalArgumentException("One or more unsupported output surfaces found!");
            }
            if (burstCaptureSurface != null && config.getPostviewOutputConfiguration() != null) {
                CameraExtensionUtils.SurfaceInfo burstCaptureSurfaceInfo = CameraExtensionUtils.querySurface(burstCaptureSurface);
                Size burstCaptureSurfaceSize = new Size(burstCaptureSurfaceInfo.mWidth, burstCaptureSurfaceInfo.mHeight);
                HashMap<Integer, List<Size>> supportedPostviewSizes = new HashMap<>();
                int[] iArr = CameraExtensionUtils.SUPPORTED_CAPTURE_OUTPUT_FORMATS;
                int length = iArr.length;
                while (i < length) {
                    int format2 = iArr[i];
                    List<Size> supportedSizesPostview = extensionChars2.getPostviewSupportedSizes(config.getExtension(), burstCaptureSurfaceSize, format2);
                    if (supportedSizesPostview == null) {
                        extensionChars = extensionChars2;
                    } else {
                        extensionChars = extensionChars2;
                        supportedPostviewSizes.put(Integer.valueOf(format2), supportedSizesPostview);
                    }
                    i++;
                    extensionChars2 = extensionChars;
                }
                Surface postviewSurface2 = CameraExtensionUtils.getPostviewSurface(config.getPostviewOutputConfiguration(), supportedPostviewSizes, burstCaptureSurfaceInfo.mFormat);
                if (postviewSurface2 == null) {
                    throw new IllegalArgumentException("Unsupported output surface for postview!");
                }
                postviewSurface = postviewSurface2;
            } else {
                postviewSurface = null;
            }
            IAdvancedExtenderImpl extender = CameraExtensionCharacteristics.initializeAdvancedExtension(config.getExtension());
            extender.init(cameraId, characteristicsMapNative);
            CameraAdvancedExtensionSessionImpl ret = new CameraAdvancedExtensionSessionImpl(ctx, extender, cameraDevice, characteristicsMapNative, repeatingRequestSurface, burstCaptureSurface, postviewSurface, config.getStateCallback(), config.getExecutor(), sessionId, token);
            ret.mStatsAggregator.setClientName(ctx.getOpPackageName());
            ret.mStatsAggregator.setExtensionType(config.getExtension());
            ret.initialize();
            return ret;
        }
        throw new IllegalArgumentException("Unexpected amount of output surfaces, received: " + config.getOutputConfigurations().size() + " expected <= 2");
    }

    private CameraAdvancedExtensionSessionImpl(Context context, IAdvancedExtenderImpl iAdvancedExtenderImpl, CameraDeviceImpl cameraDeviceImpl, Map<String, CameraMetadataNative> map, Surface surface, Surface surface2, Surface surface3, CameraExtensionSession.StateCallback stateCallback, Executor executor, int i, IBinder iBinder) {
        this.mToken = null;
        this.mContext = context;
        this.mAdvancedExtender = iAdvancedExtenderImpl;
        this.mCameraDevice = cameraDeviceImpl;
        this.mCharacteristicsMap = map;
        this.mCallbacks = stateCallback;
        this.mExecutor = executor;
        this.mClientRepeatingRequestSurface = surface;
        this.mClientCaptureSurface = surface2;
        this.mClientPostviewSurface = surface3;
        HandlerThread handlerThread = new HandlerThread(TAG);
        this.mHandlerThread = handlerThread;
        handlerThread.start();
        this.mHandler = new Handler(handlerThread.getLooper());
        this.mInitialized = false;
        this.mSessionClosed = false;
        this.mInitializeHandler = new InitializeSessionHandler();
        this.mSessionId = i;
        this.mToken = iBinder;
        this.mInterfaceLock = cameraDeviceImpl.mInterfaceLock;
        this.mStatsAggregator = new ExtensionSessionStatsAggregator(this.mCameraDevice.getId(), true);
    }

    public synchronized void initialize() throws CameraAccessException, RemoteException {
        if (this.mInitialized) {
            Log.d(TAG, "Session already initialized");
            return;
        }
        OutputSurface previewSurface = initializeParcelable(this.mClientRepeatingRequestSurface);
        OutputSurface captureSurface = initializeParcelable(this.mClientCaptureSurface);
        OutputSurface postviewSurface = initializeParcelable(this.mClientPostviewSurface);
        ISessionProcessorImpl sessionProcessor = this.mAdvancedExtender.getSessionProcessor();
        this.mSessionProcessor = sessionProcessor;
        CameraSessionConfig sessionConfig = sessionProcessor.initSession(this.mToken, this.mCameraDevice.getId(), this.mCharacteristicsMap, previewSurface, captureSurface, postviewSurface);
        List<CameraOutputConfig> outputConfigs = sessionConfig.outputConfigs;
        ArrayList<OutputConfiguration> outputList = new ArrayList<>();
        for (CameraOutputConfig output : outputConfigs) {
            Surface outputSurface = initializeSurfrace(output);
            if (outputSurface != null) {
                OutputConfiguration cameraOutput = new OutputConfiguration(output.surfaceGroupId, outputSurface);
                if (output.isMultiResolutionOutput) {
                    cameraOutput.setMultiResolutionOutput();
                }
                if (output.sharedSurfaceConfigs != null && !output.sharedSurfaceConfigs.isEmpty()) {
                    cameraOutput.enableSurfaceSharing();
                    for (CameraOutputConfig sharedOutputConfig : output.sharedSurfaceConfigs) {
                        Surface sharedSurface = initializeSurfrace(sharedOutputConfig);
                        if (sharedSurface != null) {
                            cameraOutput.addSurface(sharedSurface);
                            this.mCameraConfigMap.put(sharedSurface, sharedOutputConfig);
                        }
                    }
                }
                cameraOutput.setTimestampBase(1);
                cameraOutput.setReadoutTimestampEnabled(false);
                cameraOutput.setPhysicalCameraId(output.physicalCameraId);
                outputList.add(cameraOutput);
                this.mCameraConfigMap.put(cameraOutput.getSurface(), output);
            }
        }
        int sessionType = 0;
        if (sessionConfig.sessionType != -1 && sessionConfig.sessionType != 1) {
            sessionType = sessionConfig.sessionType;
            Log.v(TAG, "Using session type: " + sessionType);
        }
        SessionConfiguration sessionConfiguration = new SessionConfiguration(sessionType, outputList, new CameraExtensionUtils.HandlerExecutor(this.mHandler), new SessionStateHandler());
        if (sessionConfig.sessionParameter != null && !sessionConfig.sessionParameter.isEmpty()) {
            CaptureRequest.Builder requestBuilder = this.mCameraDevice.createCaptureRequest(sessionConfig.sessionTemplateId);
            CaptureRequest sessionRequest = requestBuilder.build();
            CameraMetadataNative.update(sessionRequest.getNativeMetadata(), sessionConfig.sessionParameter);
            sessionConfiguration.setSessionParameters(sessionRequest);
        }
        this.mCameraDevice.createCaptureSession(sessionConfiguration);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static ParcelCaptureResult initializeParcelable(CaptureResult result) {
        ParcelCaptureResult ret = new ParcelCaptureResult();
        ret.cameraId = result.getCameraId();
        ret.results = result.getNativeMetadata();
        ret.parent = result.getRequest();
        ret.sequenceId = result.getSequenceId();
        ret.frameNumber = result.getFrameNumber();
        return ret;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static ParcelTotalCaptureResult initializeParcelable(TotalCaptureResult totalResult) {
        ParcelTotalCaptureResult ret = new ParcelTotalCaptureResult();
        ret.logicalCameraId = totalResult.getCameraId();
        ret.results = totalResult.getNativeMetadata();
        ret.parent = totalResult.getRequest();
        ret.sequenceId = totalResult.getSequenceId();
        ret.frameNumber = totalResult.getFrameNumber();
        ret.sessionId = totalResult.getSessionId();
        ret.partials = new ArrayList(totalResult.getPartialResults().size());
        for (CaptureResult partial : totalResult.getPartialResults()) {
            ret.partials.add(initializeParcelable(partial));
        }
        Map<String, TotalCaptureResult> physicalResults = totalResult.getPhysicalCameraTotalResults();
        ret.physicalResult = new ArrayList(physicalResults.size());
        for (TotalCaptureResult physicalResult : physicalResults.values()) {
            ret.physicalResult.add(new PhysicalCaptureResultInfo(physicalResult.getCameraId(), physicalResult.getNativeMetadata()));
        }
        return ret;
    }

    private static OutputSurface initializeParcelable(Surface s) {
        OutputSurface ret = new OutputSurface();
        if (s != null) {
            ret.surface = s;
            ret.size = new android.hardware.camera2.extension.Size();
            Size surfaceSize = SurfaceUtils.getSurfaceSize(s);
            ret.size.width = surfaceSize.getWidth();
            ret.size.height = surfaceSize.getHeight();
            ret.imageFormat = SurfaceUtils.getSurfaceFormat(s);
        } else {
            ret.surface = null;
            ret.size = new android.hardware.camera2.extension.Size();
            ret.size.width = -1;
            ret.size.height = -1;
            ret.imageFormat = 0;
        }
        return ret;
    }

    @Override // android.hardware.camera2.CameraExtensionSession
    public CameraDevice getDevice() {
        CameraDevice cameraDevice;
        synchronized (this.mInterfaceLock) {
            cameraDevice = this.mCameraDevice;
        }
        return cameraDevice;
    }

    @Override // android.hardware.camera2.CameraExtensionSession
    public CameraExtensionSession.StillCaptureLatency getRealtimeStillCaptureLatency() throws CameraAccessException {
        synchronized (this.mInterfaceLock) {
            if (!this.mInitialized) {
                throw new IllegalStateException("Uninitialized component");
            }
            try {
                LatencyPair latency = this.mSessionProcessor.getRealtimeCaptureLatency();
                if (latency == null) {
                    return null;
                }
                return new CameraExtensionSession.StillCaptureLatency(latency.first, latency.second);
            } catch (RemoteException e) {
                Log.e(TAG, "Failed to query realtime latency! Extension service does not respond");
                throw new CameraAccessException(3);
            }
        }
    }

    @Override // android.hardware.camera2.CameraExtensionSession
    public int setRepeatingRequest(CaptureRequest request, Executor executor, CameraExtensionSession.ExtensionCaptureCallback listener) throws CameraAccessException {
        int seqId;
        synchronized (this.mInterfaceLock) {
            if (!this.mInitialized) {
                throw new IllegalStateException("Uninitialized component");
            }
            Surface surface = this.mClientRepeatingRequestSurface;
            if (surface == null) {
                throw new IllegalArgumentException("No registered preview surface");
            }
            if (!request.containsTarget(surface) || request.getTargets().size() != 1) {
                throw new IllegalArgumentException("Invalid repeating request output target!");
            }
            try {
                this.mSessionProcessor.setParameters(request);
                seqId = this.mSessionProcessor.startRepeating(new RequestCallbackHandler(request, executor, listener, this.mCameraDevice.getId()));
            } catch (RemoteException e) {
                throw new CameraAccessException(3, "Failed to enable repeating request, extension service failed to respond!");
            }
        }
        return seqId;
    }

    @Override // android.hardware.camera2.CameraExtensionSession
    public int capture(CaptureRequest request, Executor executor, CameraExtensionSession.ExtensionCaptureCallback listener) throws CameraAccessException {
        int seqId;
        synchronized (this.mInterfaceLock) {
            if (!this.mInitialized) {
                throw new IllegalStateException("Uninitialized component");
            }
            validateCaptureRequestTargets(request);
            Surface surface = this.mClientCaptureSurface;
            if (surface != null && request.containsTarget(surface)) {
                try {
                    boolean isPostviewRequested = request.containsTarget(this.mClientPostviewSurface);
                    this.mSessionProcessor.setParameters(request);
                    int seqId2 = this.mSessionProcessor.startCapture(new RequestCallbackHandler(request, executor, listener, this.mCameraDevice.getId()), isPostviewRequested);
                    seqId = seqId2;
                } catch (RemoteException e) {
                    throw new CameraAccessException(3, "Failed  to submit capture request, extension service failed to respond!");
                }
            } else {
                Surface surface2 = this.mClientRepeatingRequestSurface;
                if (surface2 != null && request.containsTarget(surface2)) {
                    try {
                        int seqId3 = this.mSessionProcessor.startTrigger(request, new RequestCallbackHandler(request, executor, listener, this.mCameraDevice.getId()));
                        seqId = seqId3;
                    } catch (RemoteException e2) {
                        throw new CameraAccessException(3, "Failed  to submit trigger request, extension service failed to respond!");
                    }
                } else {
                    throw new IllegalArgumentException("Invalid single capture output target!");
                }
            }
        }
        return seqId;
    }

    private void validateCaptureRequestTargets(CaptureRequest request) {
        if (request.getTargets().size() == 1) {
            Surface surface = this.mClientCaptureSurface;
            boolean containsCaptureTarget = surface != null && request.containsTarget(surface);
            Surface surface2 = this.mClientRepeatingRequestSurface;
            boolean containsRepeatingTarget = surface2 != null && request.containsTarget(surface2);
            if (!containsCaptureTarget && !containsRepeatingTarget) {
                throw new IllegalArgumentException("Target output combination requested is not supported!");
            }
        }
        if (request.getTargets().size() == 2 && !request.getTargets().containsAll(Arrays.asList(this.mClientCaptureSurface, this.mClientPostviewSurface))) {
            throw new IllegalArgumentException("Target output combination requested is not supported!");
        }
        if (request.getTargets().size() > 2) {
            throw new IllegalArgumentException("Target output combination requested is not supported!");
        }
    }

    @Override // android.hardware.camera2.CameraExtensionSession
    public void stopRepeating() throws CameraAccessException {
        synchronized (this.mInterfaceLock) {
            if (!this.mInitialized) {
                throw new IllegalStateException("Uninitialized component");
            }
            this.mCaptureSession.stopRepeating();
            try {
                this.mSessionProcessor.stopRepeating();
            } catch (RemoteException e) {
                throw new CameraAccessException(3, "Failed to notify about the end of repeating request, extension service failed to respond!");
            }
        }
    }

    @Override // android.hardware.camera2.CameraExtensionSession, java.lang.AutoCloseable
    public void close() throws CameraAccessException {
        synchronized (this.mInterfaceLock) {
            if (this.mInitialized) {
                try {
                    try {
                        this.mCaptureSession.stopRepeating();
                    } catch (RemoteException e) {
                        Log.e(TAG, "Failed to stop the repeating request or end the session, , extension service does not respond!");
                    }
                } catch (IllegalStateException e2) {
                }
                this.mSessionProcessor.stopRepeating();
                this.mSessionProcessor.onCaptureSessionEnd();
                this.mSessionClosed = true;
                this.mStatsAggregator.commit(true);
                this.mCaptureSession.close();
            }
        }
    }

    public void commitStats() {
        synchronized (this.mInterfaceLock) {
            if (this.mInitialized) {
                this.mStatsAggregator.commit(true);
            }
        }
    }

    public void release(boolean skipCloseNotification) {
        boolean notifyClose = false;
        synchronized (this.mInterfaceLock) {
            this.mHandlerThread.quitSafely();
            ISessionProcessorImpl iSessionProcessorImpl = this.mSessionProcessor;
            if (iSessionProcessorImpl != null) {
                try {
                    if (!this.mSessionClosed) {
                        iSessionProcessorImpl.onCaptureSessionEnd();
                    }
                    this.mSessionProcessor.deInitSession(this.mToken);
                } catch (RemoteException e) {
                    Log.e(TAG, "Failed to de-initialize session processor, extension service does not respond!");
                }
                this.mSessionProcessor = null;
            }
            if (this.mToken != null) {
                if (this.mInitialized || this.mCaptureSession != null) {
                    notifyClose = true;
                    CameraExtensionCharacteristics.releaseSession();
                }
                CameraExtensionCharacteristics.unregisterClient(this.mContext, this.mToken);
            }
            this.mInitialized = false;
            this.mToken = null;
            for (ImageReader reader : this.mReaderMap.values()) {
                reader.close();
            }
            this.mReaderMap.clear();
            this.mClientRepeatingRequestSurface = null;
            this.mClientCaptureSurface = null;
            this.mCaptureSession = null;
            this.mRequestProcessor = null;
            this.mCameraDevice = null;
            this.mAdvancedExtender = null;
        }
        if (notifyClose && !skipCloseNotification) {
            long ident = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new Runnable() { // from class: android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        CameraAdvancedExtensionSessionImpl.this.lambda$release$0();
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(ident);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$release$0() {
        this.mCallbacks.onClosed(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyConfigurationFailure() {
        synchronized (this.mInterfaceLock) {
            if (this.mInitialized) {
                return;
            }
            release(true);
            long ident = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new Runnable() { // from class: android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        CameraAdvancedExtensionSessionImpl.this.lambda$notifyConfigurationFailure$1();
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(ident);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifyConfigurationFailure$1() {
        this.mCallbacks.onConfigureFailed(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class SessionStateHandler extends CameraCaptureSession.StateCallback {
        private SessionStateHandler() {
        }

        @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
        public void onClosed(CameraCaptureSession session) {
            CameraAdvancedExtensionSessionImpl.this.release(false);
        }

        @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
        public void onConfigureFailed(CameraCaptureSession session) {
            CameraAdvancedExtensionSessionImpl.this.notifyConfigurationFailure();
        }

        @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
        public void onConfigured(CameraCaptureSession session) {
            synchronized (CameraAdvancedExtensionSessionImpl.this.mInterfaceLock) {
                CameraAdvancedExtensionSessionImpl.this.mCaptureSession = session;
                CameraAdvancedExtensionSessionImpl.this.mStatsAggregator.commit(false);
            }
            try {
                CameraExtensionCharacteristics.initializeSession(CameraAdvancedExtensionSessionImpl.this.mInitializeHandler);
            } catch (RemoteException e) {
                Log.e(CameraAdvancedExtensionSessionImpl.TAG, "Failed to initialize session! Extension service does not respond!");
                CameraAdvancedExtensionSessionImpl.this.notifyConfigurationFailure();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class InitializeSessionHandler extends IInitializeSessionCallback.Stub {
        private InitializeSessionHandler() {
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl$InitializeSessionHandler$1, reason: invalid class name */
        /* loaded from: classes.dex */
        public class AnonymousClass1 implements Runnable {
            AnonymousClass1() {
            }

            @Override // java.lang.Runnable
            public void run() {
                boolean status = true;
                synchronized (CameraAdvancedExtensionSessionImpl.this.mInterfaceLock) {
                    try {
                        if (CameraAdvancedExtensionSessionImpl.this.mSessionProcessor != null) {
                            CameraAdvancedExtensionSessionImpl.this.mInitialized = true;
                            CameraAdvancedExtensionSessionImpl.this.mSessionProcessor.onCaptureSessionStart(CameraAdvancedExtensionSessionImpl.this.mRequestProcessor);
                        } else {
                            Log.v(CameraAdvancedExtensionSessionImpl.TAG, "Failed to start capture session, session  released before extension start!");
                            status = false;
                        }
                    } catch (RemoteException e) {
                        Log.e(CameraAdvancedExtensionSessionImpl.TAG, "Failed to start capture session, extension service does not respond!");
                        status = false;
                        CameraAdvancedExtensionSessionImpl.this.mInitialized = false;
                    }
                }
                if (status) {
                    long ident = Binder.clearCallingIdentity();
                    try {
                        CameraAdvancedExtensionSessionImpl.this.mExecutor.execute(new Runnable() { // from class: android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl$InitializeSessionHandler$1$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                CameraAdvancedExtensionSessionImpl.InitializeSessionHandler.AnonymousClass1.this.lambda$run$0();
                            }
                        });
                        return;
                    } finally {
                        Binder.restoreCallingIdentity(ident);
                    }
                }
                InitializeSessionHandler.this.onFailure();
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$run$0() {
                CameraAdvancedExtensionSessionImpl.this.mCallbacks.onConfigured(CameraAdvancedExtensionSessionImpl.this);
            }
        }

        @Override // android.hardware.camera2.extension.IInitializeSessionCallback
        public void onSuccess() {
            CameraAdvancedExtensionSessionImpl.this.mHandler.post(new AnonymousClass1());
        }

        @Override // android.hardware.camera2.extension.IInitializeSessionCallback
        public void onFailure() {
            CameraAdvancedExtensionSessionImpl.this.mHandler.post(new Runnable() { // from class: android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl.InitializeSessionHandler.2
                @Override // java.lang.Runnable
                public void run() {
                    CameraAdvancedExtensionSessionImpl.this.mCaptureSession.close();
                    Log.e(CameraAdvancedExtensionSessionImpl.TAG, "Failed to initialize proxy service session! This can happen when trying to configure multiple concurrent extension sessions!");
                    CameraAdvancedExtensionSessionImpl.this.notifyConfigurationFailure();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public final class RequestCallbackHandler extends ICaptureCallback.Stub {
        private final String mCameraId;
        private final CameraExtensionSession.ExtensionCaptureCallback mClientCallbacks;
        private final Executor mClientExecutor;
        private final CaptureRequest mClientRequest;

        private RequestCallbackHandler(CaptureRequest clientRequest, Executor clientExecutor, CameraExtensionSession.ExtensionCaptureCallback clientCallbacks, String cameraId) {
            this.mClientRequest = clientRequest;
            this.mClientExecutor = clientExecutor;
            this.mClientCallbacks = clientCallbacks;
            this.mCameraId = cameraId;
        }

        @Override // android.hardware.camera2.extension.ICaptureCallback
        public void onCaptureStarted(int captureSequenceId, final long timestamp) {
            long ident = Binder.clearCallingIdentity();
            try {
                this.mClientExecutor.execute(new Runnable() { // from class: android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl$RequestCallbackHandler$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        CameraAdvancedExtensionSessionImpl.RequestCallbackHandler.this.lambda$onCaptureStarted$0(timestamp);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(ident);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureStarted$0(long timestamp) {
            this.mClientCallbacks.onCaptureStarted(CameraAdvancedExtensionSessionImpl.this, this.mClientRequest, timestamp);
        }

        @Override // android.hardware.camera2.extension.ICaptureCallback
        public void onCaptureProcessStarted(int captureSequenceId) {
            long ident = Binder.clearCallingIdentity();
            try {
                this.mClientExecutor.execute(new Runnable() { // from class: android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl$RequestCallbackHandler$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        CameraAdvancedExtensionSessionImpl.RequestCallbackHandler.this.lambda$onCaptureProcessStarted$1();
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(ident);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureProcessStarted$1() {
            this.mClientCallbacks.onCaptureProcessStarted(CameraAdvancedExtensionSessionImpl.this, this.mClientRequest);
        }

        @Override // android.hardware.camera2.extension.ICaptureCallback
        public void onCaptureFailed(int captureSequenceId) {
            long ident = Binder.clearCallingIdentity();
            try {
                this.mClientExecutor.execute(new Runnable() { // from class: android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl$RequestCallbackHandler$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        CameraAdvancedExtensionSessionImpl.RequestCallbackHandler.this.lambda$onCaptureFailed$2();
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(ident);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureFailed$2() {
            this.mClientCallbacks.onCaptureFailed(CameraAdvancedExtensionSessionImpl.this, this.mClientRequest);
        }

        @Override // android.hardware.camera2.extension.ICaptureCallback
        public void onCaptureSequenceCompleted(final int captureSequenceId) {
            long ident = Binder.clearCallingIdentity();
            try {
                this.mClientExecutor.execute(new Runnable() { // from class: android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl$RequestCallbackHandler$$ExternalSyntheticLambda6
                    @Override // java.lang.Runnable
                    public final void run() {
                        CameraAdvancedExtensionSessionImpl.RequestCallbackHandler.this.lambda$onCaptureSequenceCompleted$3(captureSequenceId);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(ident);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureSequenceCompleted$3(int captureSequenceId) {
            this.mClientCallbacks.onCaptureSequenceCompleted(CameraAdvancedExtensionSessionImpl.this, captureSequenceId);
        }

        @Override // android.hardware.camera2.extension.ICaptureCallback
        public void onCaptureSequenceAborted(final int captureSequenceId) {
            long ident = Binder.clearCallingIdentity();
            try {
                this.mClientExecutor.execute(new Runnable() { // from class: android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl$RequestCallbackHandler$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        CameraAdvancedExtensionSessionImpl.RequestCallbackHandler.this.lambda$onCaptureSequenceAborted$4(captureSequenceId);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(ident);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureSequenceAborted$4(int captureSequenceId) {
            this.mClientCallbacks.onCaptureSequenceAborted(CameraAdvancedExtensionSessionImpl.this, captureSequenceId);
        }

        @Override // android.hardware.camera2.extension.ICaptureCallback
        public void onCaptureCompleted(long timestamp, int requestId, CameraMetadataNative result) {
            if (result == null) {
                Log.e(CameraAdvancedExtensionSessionImpl.TAG, "Invalid capture result!");
                return;
            }
            result.set((CaptureResult.Key<CaptureResult.Key<Long>>) CaptureResult.SENSOR_TIMESTAMP, (CaptureResult.Key<Long>) Long.valueOf(timestamp));
            final TotalCaptureResult totalResult = new TotalCaptureResult(this.mCameraId, result, this.mClientRequest, requestId, timestamp, new ArrayList(), CameraAdvancedExtensionSessionImpl.this.mSessionId, new PhysicalCaptureResultInfo[0]);
            long ident = Binder.clearCallingIdentity();
            try {
                CameraAdvancedExtensionSessionImpl.this.mExecutor.execute(new Runnable() { // from class: android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl$RequestCallbackHandler$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        CameraAdvancedExtensionSessionImpl.RequestCallbackHandler.this.lambda$onCaptureCompleted$5(totalResult);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(ident);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureCompleted$5(TotalCaptureResult totalResult) {
            this.mClientCallbacks.onCaptureResultAvailable(CameraAdvancedExtensionSessionImpl.this, this.mClientRequest, totalResult);
        }

        @Override // android.hardware.camera2.extension.ICaptureCallback
        public void onCaptureProcessProgressed(final int progress) {
            long ident = Binder.clearCallingIdentity();
            try {
                CameraAdvancedExtensionSessionImpl.this.mExecutor.execute(new Runnable() { // from class: android.hardware.camera2.impl.CameraAdvancedExtensionSessionImpl$RequestCallbackHandler$$ExternalSyntheticLambda5
                    @Override // java.lang.Runnable
                    public final void run() {
                        CameraAdvancedExtensionSessionImpl.RequestCallbackHandler.this.lambda$onCaptureProcessProgressed$6(progress);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(ident);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureProcessProgressed$6(int progress) {
            this.mClientCallbacks.onCaptureProcessProgressed(CameraAdvancedExtensionSessionImpl.this, this.mClientRequest, progress);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public final class CaptureCallbackHandler extends CameraCaptureSession.CaptureCallback {
        private final IRequestCallback mCallback;

        public CaptureCallbackHandler(IRequestCallback callback) {
            this.mCallback = callback;
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureBufferLost(CameraCaptureSession session, CaptureRequest request, Surface target, long frameNumber) {
            try {
                if (!(request.getTag() instanceof Integer)) {
                    Log.e(CameraAdvancedExtensionSessionImpl.TAG, "Invalid capture request tag!");
                } else {
                    Integer requestId = (Integer) request.getTag();
                    this.mCallback.onCaptureBufferLost(requestId.intValue(), frameNumber, ((CameraOutputConfig) CameraAdvancedExtensionSessionImpl.this.mCameraConfigMap.get(target)).outputId.id);
                }
            } catch (RemoteException e) {
                Log.e(CameraAdvancedExtensionSessionImpl.TAG, "Failed to notify lost capture buffer, extension service doesn't respond!");
            }
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureCompleted(CameraCaptureSession session, CaptureRequest request, TotalCaptureResult result) {
            try {
                if (!(request.getTag() instanceof Integer)) {
                    Log.e(CameraAdvancedExtensionSessionImpl.TAG, "Invalid capture request tag!");
                } else {
                    Integer requestId = (Integer) request.getTag();
                    this.mCallback.onCaptureCompleted(requestId.intValue(), CameraAdvancedExtensionSessionImpl.initializeParcelable(result));
                }
            } catch (RemoteException e) {
                Log.e(CameraAdvancedExtensionSessionImpl.TAG, "Failed to notify capture result, extension service doesn't respond!");
            }
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureFailed(CameraCaptureSession session, CaptureRequest request, CaptureFailure failure) {
            try {
                if (!(request.getTag() instanceof Integer)) {
                    Log.e(CameraAdvancedExtensionSessionImpl.TAG, "Invalid capture request tag!");
                } else {
                    Integer requestId = (Integer) request.getTag();
                    android.hardware.camera2.extension.CaptureFailure captureFailure = new android.hardware.camera2.extension.CaptureFailure();
                    captureFailure.request = request;
                    captureFailure.reason = failure.getReason();
                    captureFailure.errorPhysicalCameraId = failure.getPhysicalCameraId();
                    captureFailure.frameNumber = failure.getFrameNumber();
                    captureFailure.sequenceId = failure.getSequenceId();
                    captureFailure.dropped = !failure.wasImageCaptured();
                    this.mCallback.onCaptureFailed(requestId.intValue(), captureFailure);
                }
            } catch (RemoteException e) {
                Log.e(CameraAdvancedExtensionSessionImpl.TAG, "Failed to notify capture failure, extension service doesn't respond!");
            }
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureProgressed(CameraCaptureSession session, CaptureRequest request, CaptureResult partialResult) {
            try {
                if (!(request.getTag() instanceof Integer)) {
                    Log.e(CameraAdvancedExtensionSessionImpl.TAG, "Invalid capture request tag!");
                } else {
                    Integer requestId = (Integer) request.getTag();
                    this.mCallback.onCaptureProgressed(requestId.intValue(), CameraAdvancedExtensionSessionImpl.initializeParcelable(partialResult));
                }
            } catch (RemoteException e) {
                Log.e(CameraAdvancedExtensionSessionImpl.TAG, "Failed to notify capture partial result, extension service doesn't respond!");
            }
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureSequenceAborted(CameraCaptureSession session, int sequenceId) {
            try {
                this.mCallback.onCaptureSequenceAborted(sequenceId);
            } catch (RemoteException e) {
                Log.e(CameraAdvancedExtensionSessionImpl.TAG, "Failed to notify aborted sequence, extension service doesn't respond!");
            }
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureSequenceCompleted(CameraCaptureSession session, int sequenceId, long frameNumber) {
            try {
                this.mCallback.onCaptureSequenceCompleted(sequenceId, frameNumber);
            } catch (RemoteException e) {
                Log.e(CameraAdvancedExtensionSessionImpl.TAG, "Failed to notify sequence complete, extension service doesn't respond!");
            }
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureStarted(CameraCaptureSession session, CaptureRequest request, long timestamp, long frameNumber) {
            try {
                if (!(request.getTag() instanceof Integer)) {
                    Log.e(CameraAdvancedExtensionSessionImpl.TAG, "Invalid capture request tag!");
                } else {
                    Integer requestId = (Integer) request.getTag();
                    this.mCallback.onCaptureStarted(requestId.intValue(), frameNumber, timestamp);
                }
            } catch (RemoteException e) {
                Log.e(CameraAdvancedExtensionSessionImpl.TAG, "Failed to notify capture started, extension service doesn't respond!");
            }
        }
    }

    /* loaded from: classes.dex */
    private static final class ImageReaderHandler implements ImageReader.OnImageAvailableListener {
        private final IImageProcessorImpl mIImageProcessor;
        private final OutputConfigId mOutputConfigId;
        private final String mPhysicalCameraId;

        private ImageReaderHandler(int outputConfigId, IImageProcessorImpl iImageProcessor, String physicalCameraId) {
            OutputConfigId outputConfigId2 = new OutputConfigId();
            this.mOutputConfigId = outputConfigId2;
            outputConfigId2.id = outputConfigId;
            this.mIImageProcessor = iImageProcessor;
            this.mPhysicalCameraId = physicalCameraId;
        }

        @Override // android.media.ImageReader.OnImageAvailableListener
        public void onImageAvailable(ImageReader reader) {
            if (this.mIImageProcessor == null) {
                return;
            }
            try {
                Image img = reader.acquireNextImage();
                if (img == null) {
                    Log.e(CameraAdvancedExtensionSessionImpl.TAG, "Invalid image!");
                    return;
                }
                try {
                    reader.detachImage(img);
                    ParcelImage parcelImage = new ParcelImage();
                    parcelImage.buffer = img.getHardwareBuffer();
                    try {
                        SyncFence fd = img.getFence();
                        if (fd.isValid()) {
                            parcelImage.fence = fd.getFdDup();
                        }
                    } catch (IOException e) {
                        Log.e(CameraAdvancedExtensionSessionImpl.TAG, "Failed to parcel buffer fence!");
                    }
                    parcelImage.width = img.getWidth();
                    parcelImage.height = img.getHeight();
                    parcelImage.format = img.getFormat();
                    parcelImage.timestamp = img.getTimestamp();
                    parcelImage.transform = img.getTransform();
                    parcelImage.scalingMode = img.getScalingMode();
                    parcelImage.planeCount = img.getPlaneCount();
                    parcelImage.crop = img.getCropRect();
                    try {
                        try {
                            this.mIImageProcessor.onNextImageAvailable(this.mOutputConfigId, parcelImage, this.mPhysicalCameraId);
                        } finally {
                            parcelImage.buffer.close();
                            img.close();
                        }
                    } catch (RemoteException e2) {
                        Log.e(CameraAdvancedExtensionSessionImpl.TAG, "Failed to propagate image buffer on output surface id: " + this.mOutputConfigId + " extension service does not respond!");
                    }
                } catch (Exception e3) {
                    Log.e(CameraAdvancedExtensionSessionImpl.TAG, "Failed to detach image");
                    img.close();
                }
            } catch (IllegalStateException e4) {
                Log.e(CameraAdvancedExtensionSessionImpl.TAG, "Failed to acquire image, too many images pending!");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public final class RequestProcessor extends IRequestProcessorImpl.Stub {
        private RequestProcessor() {
        }

        @Override // android.hardware.camera2.extension.IRequestProcessorImpl
        public void setImageProcessor(OutputConfigId outputConfigId, IImageProcessorImpl imageProcessor) {
            synchronized (CameraAdvancedExtensionSessionImpl.this.mInterfaceLock) {
                if (CameraAdvancedExtensionSessionImpl.this.mReaderMap.containsKey(Integer.valueOf(outputConfigId.id))) {
                    ImageReader reader = (ImageReader) CameraAdvancedExtensionSessionImpl.this.mReaderMap.get(Integer.valueOf(outputConfigId.id));
                    if (CameraAdvancedExtensionSessionImpl.this.mCameraConfigMap.containsKey(reader.getSurface())) {
                        String physicalCameraId = ((CameraOutputConfig) CameraAdvancedExtensionSessionImpl.this.mCameraConfigMap.get(reader.getSurface())).physicalCameraId;
                        reader.setOnImageAvailableListener(new ImageReaderHandler(outputConfigId.id, imageProcessor, physicalCameraId), CameraAdvancedExtensionSessionImpl.this.mHandler);
                    } else {
                        Log.e(CameraAdvancedExtensionSessionImpl.TAG, "Camera output configuration for ImageReader with  config Id " + outputConfigId.id + " not found!");
                    }
                } else {
                    Log.e(CameraAdvancedExtensionSessionImpl.TAG, "ImageReader with output config id: " + outputConfigId.id + " not found!");
                }
            }
        }

        @Override // android.hardware.camera2.extension.IRequestProcessorImpl
        public int submit(Request request, IRequestCallback callback) {
            ArrayList<Request> captureList = new ArrayList<>();
            captureList.add(request);
            return submitBurst(captureList, callback);
        }

        @Override // android.hardware.camera2.extension.IRequestProcessorImpl
        public int submitBurst(List<Request> requests, IRequestCallback callback) {
            int seqId = -1;
            try {
            } catch (CameraAccessException e) {
                Log.e(CameraAdvancedExtensionSessionImpl.TAG, "Failed to submit capture requests!");
            } catch (IllegalStateException e2) {
                Log.e(CameraAdvancedExtensionSessionImpl.TAG, "Capture session closed!");
            }
            synchronized (CameraAdvancedExtensionSessionImpl.this.mInterfaceLock) {
                if (!CameraAdvancedExtensionSessionImpl.this.mInitialized) {
                    return -1;
                }
                CaptureCallbackHandler captureCallback = new CaptureCallbackHandler(callback);
                ArrayList<CaptureRequest> captureRequests = new ArrayList<>();
                for (Request request : requests) {
                    captureRequests.add(CameraAdvancedExtensionSessionImpl.initializeCaptureRequest(CameraAdvancedExtensionSessionImpl.this.mCameraDevice, request, CameraAdvancedExtensionSessionImpl.this.mCameraConfigMap));
                }
                seqId = CameraAdvancedExtensionSessionImpl.this.mCaptureSession.captureBurstRequests(captureRequests, new CameraExtensionUtils.HandlerExecutor(CameraAdvancedExtensionSessionImpl.this.mHandler), captureCallback);
                return seqId;
            }
        }

        @Override // android.hardware.camera2.extension.IRequestProcessorImpl
        public int setRepeating(Request request, IRequestCallback callback) {
            int seqId = -1;
            try {
            } catch (CameraAccessException e) {
                Log.e(CameraAdvancedExtensionSessionImpl.TAG, "Failed to enable repeating request!");
            } catch (IllegalStateException e2) {
                Log.e(CameraAdvancedExtensionSessionImpl.TAG, "Capture session closed!");
            }
            synchronized (CameraAdvancedExtensionSessionImpl.this.mInterfaceLock) {
                if (!CameraAdvancedExtensionSessionImpl.this.mInitialized) {
                    return -1;
                }
                CaptureRequest repeatingRequest = CameraAdvancedExtensionSessionImpl.initializeCaptureRequest(CameraAdvancedExtensionSessionImpl.this.mCameraDevice, request, CameraAdvancedExtensionSessionImpl.this.mCameraConfigMap);
                CaptureCallbackHandler captureCallback = new CaptureCallbackHandler(callback);
                seqId = CameraAdvancedExtensionSessionImpl.this.mCaptureSession.setSingleRepeatingRequest(repeatingRequest, new CameraExtensionUtils.HandlerExecutor(CameraAdvancedExtensionSessionImpl.this.mHandler), captureCallback);
                return seqId;
            }
        }

        @Override // android.hardware.camera2.extension.IRequestProcessorImpl
        public void abortCaptures() {
            try {
                synchronized (CameraAdvancedExtensionSessionImpl.this.mInterfaceLock) {
                    if (CameraAdvancedExtensionSessionImpl.this.mInitialized) {
                        CameraAdvancedExtensionSessionImpl.this.mCaptureSession.abortCaptures();
                    }
                }
            } catch (CameraAccessException e) {
                Log.e(CameraAdvancedExtensionSessionImpl.TAG, "Failed during capture abort!");
            } catch (IllegalStateException e2) {
                Log.e(CameraAdvancedExtensionSessionImpl.TAG, "Capture session closed!");
            }
        }

        @Override // android.hardware.camera2.extension.IRequestProcessorImpl
        public void stopRepeating() {
            try {
                synchronized (CameraAdvancedExtensionSessionImpl.this.mInterfaceLock) {
                    if (CameraAdvancedExtensionSessionImpl.this.mInitialized) {
                        CameraAdvancedExtensionSessionImpl.this.mCaptureSession.stopRepeating();
                    }
                }
            } catch (CameraAccessException e) {
                Log.e(CameraAdvancedExtensionSessionImpl.TAG, "Failed during repeating capture stop!");
            } catch (IllegalStateException e2) {
                Log.e(CameraAdvancedExtensionSessionImpl.TAG, "Capture session closed!");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static CaptureRequest initializeCaptureRequest(CameraDevice cameraDevice, Request request, HashMap<Surface, CameraOutputConfig> surfaceIdMap) throws CameraAccessException {
        CaptureRequest.Builder builder = cameraDevice.createCaptureRequest(request.templateId);
        for (OutputConfigId configId : request.targetOutputConfigIds) {
            boolean found = false;
            Iterator<Map.Entry<Surface, CameraOutputConfig>> it = surfaceIdMap.entrySet().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Map.Entry<Surface, CameraOutputConfig> entry = it.next();
                if (entry.getValue().outputId.id == configId.id) {
                    builder.addTarget(entry.getKey());
                    found = true;
                    break;
                }
            }
            if (!found) {
                Log.e(TAG, "Surface with output id: " + configId.id + " not found among registered camera outputs!");
            }
        }
        builder.setTag(Integer.valueOf(request.requestId));
        CaptureRequest ret = builder.build();
        CameraMetadataNative.update(ret.getNativeMetadata(), request.parameters);
        return ret;
    }

    private Surface initializeSurfrace(CameraOutputConfig output) {
        switch (output.type) {
            case 0:
                if (output.surface == null) {
                    Log.w(TAG, "Unsupported client output id: " + output.outputId.id + ", skipping!");
                    return null;
                }
                return output.surface;
            case 1:
                if (output.imageFormat == 0 || output.size.width <= 0 || output.size.height <= 0) {
                    Log.w(TAG, "Unsupported client output id: " + output.outputId.id + ", skipping!");
                    return null;
                }
                ImageReader reader = ImageReader.newInstance(output.size.width, output.size.height, output.imageFormat, output.capacity);
                this.mReaderMap.put(Integer.valueOf(output.outputId.id), reader);
                return reader.getSurface();
            default:
                throw new IllegalArgumentException("Unsupported output config type: " + output.type);
        }
    }
}
