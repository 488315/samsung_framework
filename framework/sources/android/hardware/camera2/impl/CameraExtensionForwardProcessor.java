package android.hardware.camera2.impl;

import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.extension.IPreviewImageProcessorImpl;
import android.hardware.camera2.extension.IProcessResultImpl;
import android.hardware.camera2.extension.ParcelImage;
import android.media.Image;
import android.media.ImageReader;
import android.media.ImageWriter;
import android.os.Handler;
import android.os.RemoteException;
import android.util.Log;
import android.util.Size;
import android.view.Surface;

/* loaded from: classes2.dex */
public class CameraExtensionForwardProcessor {
    private static final int FORWARD_QUEUE_SIZE = 3;
    public static final String TAG = "CameraExtensionForward";
    private final Handler mHandler;
    private final int mOutputSurfaceFormat;
    private final long mOutputSurfaceUsage;
    private final IPreviewImageProcessorImpl mProcessor;
    private ImageReader mIntermediateReader = null;
    private Surface mIntermediateSurface = null;
    private Size mResolution = null;
    private Surface mOutputSurface = null;
    private ImageWriter mOutputWriter = null;
    private boolean mOutputAbandoned = false;

    public CameraExtensionForwardProcessor(IPreviewImageProcessorImpl processor, int format, long surfaceUsage, Handler handler) {
        this.mProcessor = processor;
        this.mOutputSurfaceUsage = surfaceUsage;
        this.mOutputSurfaceFormat = format;
        this.mHandler = handler;
    }

    public void close() {
        if (this.mOutputWriter != null) {
            this.mOutputWriter.close();
            this.mOutputWriter = null;
        }
        if (this.mIntermediateReader != null) {
            this.mIntermediateReader.close();
            this.mIntermediateReader = null;
        }
    }

    public void onOutputSurface(Surface surface, int format) {
        this.mOutputSurface = surface;
        try {
            initializePipeline();
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to initialize forward processor, extension service does not respond!");
        }
    }

    public void onResolutionUpdate(Size size) {
        this.mResolution = size;
    }

    public void onImageFormatUpdate(int format) {
        if (format != 35) {
            Log.e(TAG, "Unsupported input format: " + format);
        }
    }

    private void initializePipeline() throws RemoteException {
        if (this.mOutputWriter != null) {
            this.mOutputWriter.close();
            this.mOutputWriter = null;
        }
        if (this.mIntermediateReader == null) {
            this.mIntermediateReader = ImageReader.newInstance(this.mResolution.getWidth(), this.mResolution.getHeight(), 35, 3, this.mOutputSurfaceUsage);
            this.mIntermediateSurface = this.mIntermediateReader.getSurface();
            this.mIntermediateReader.setOnImageAvailableListener(new ForwardCallback(), this.mHandler);
            this.mProcessor.onOutputSurface(this.mIntermediateSurface, this.mOutputSurfaceFormat);
            this.mProcessor.onImageFormatUpdate(35);
            android.hardware.camera2.extension.Size sz = new android.hardware.camera2.extension.Size();
            sz.width = this.mResolution.getWidth();
            sz.height = this.mResolution.getHeight();
            this.mProcessor.onResolutionUpdate(sz);
        }
    }

    public void process(ParcelImage image, TotalCaptureResult totalCaptureResult, IProcessResultImpl resultCallback) throws RemoteException {
        if (this.mIntermediateSurface != null && this.mIntermediateSurface.isValid() && !this.mOutputAbandoned) {
            this.mProcessor.process(image, totalCaptureResult.getNativeMetadata(), totalCaptureResult.getSequenceId(), resultCallback);
        }
    }

    private class ForwardCallback implements ImageReader.OnImageAvailableListener {
        private ForwardCallback() {
        }

        @Override // android.media.ImageReader.OnImageAvailableListener
        public void onImageAvailable(ImageReader reader) {
            try {
                Image processedImage = reader.acquireNextImage();
                if (processedImage == null) {
                    Log.e(CameraExtensionForwardProcessor.TAG, "Invalid image");
                    return;
                }
                if (CameraExtensionForwardProcessor.this.mOutputSurface != null && CameraExtensionForwardProcessor.this.mOutputSurface.isValid() && !CameraExtensionForwardProcessor.this.mOutputAbandoned) {
                    if (CameraExtensionForwardProcessor.this.mOutputWriter == null) {
                        CameraExtensionForwardProcessor.this.mOutputWriter = ImageWriter.newInstance(CameraExtensionForwardProcessor.this.mOutputSurface, 3, processedImage.getFormat());
                    }
                    try {
                        CameraExtensionForwardProcessor.this.mOutputWriter.queueInputImage(processedImage);
                        return;
                    } catch (IllegalStateException e) {
                        Log.e(CameraExtensionForwardProcessor.TAG, "Failed to queue processed buffer!");
                        processedImage.close();
                        CameraExtensionForwardProcessor.this.mOutputAbandoned = true;
                        return;
                    }
                }
                processedImage.close();
            } catch (IllegalStateException e2) {
                Log.e(CameraExtensionForwardProcessor.TAG, "Failed to acquire processed image!");
            }
        }
    }
}
