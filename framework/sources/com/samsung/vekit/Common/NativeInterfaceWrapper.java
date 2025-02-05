package com.samsung.vekit.Common;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.Surface;
import com.samsung.vekit.Common.Object.AnalyzeInfo;
import com.samsung.vekit.Common.Object.DoodlePoint;
import com.samsung.vekit.Common.Object.DoodleStroke;
import com.samsung.vekit.Common.Object.Element;
import com.samsung.vekit.Common.Object.ExportInfo;
import com.samsung.vekit.Common.Object.FrcSupportInfo;
import com.samsung.vekit.Common.Object.PVDetectionInfo;
import com.samsung.vekit.Common.Object.PVKeyFrame;
import com.samsung.vekit.Common.Object.PreviewInfo;
import com.samsung.vekit.Common.State.VEKitState;
import com.samsung.vekit.Common.State.VEStateInterface;
import com.samsung.vekit.Common.Type.AnalyzeType;
import com.samsung.vekit.Common.Type.ElementType;
import com.samsung.vekit.Common.Type.FrameworkType;
import com.samsung.vekit.Common.Type.SeekType;
import com.samsung.vekit.Common.Type.ViewMode;
import com.samsung.vekit.Control.VEController;
import com.samsung.vekit.External.NativeInterface;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

/* loaded from: classes6.dex */
public class NativeInterfaceWrapper {
    private final String TAG = getClass().getSimpleName();
    NativeInterface nativeInterface;
    WeakReference<VEStateInterface> stateInterface;

    public NativeInterfaceWrapper(VEStateInterface stateInterface) {
        this.stateInterface = new WeakReference<>(stateInterface);
    }

    public synchronized void createFramework(VEController controller) throws IllegalAccessException {
        VEStateInterface stateInterface = this.stateInterface.get();
        if (stateInterface.getState() == VEKitState.CREATE) {
            Log.e(this.TAG, "createFramework invalid state. currentState = " + stateInterface.getState());
            return;
        }
        if (this.nativeInterface == null) {
            this.nativeInterface = NativeInterface.getInstance();
            if (this.nativeInterface == null) {
                Log.e(this.TAG, "Native interface is NULL");
                throw new IllegalAccessException("Native interface is NULL");
            }
        }
        this.nativeInterface.createFramework(controller);
        stateInterface.setState(VEKitState.CREATE);
    }

    public synchronized void initializeFramework(Surface surface, int graphicBufferWidth, int graphicBufferHeight, int viewportWidth, int viewportHeight, ViewMode viewMode, FrameworkType frameworkType) {
        VEStateInterface stateInterface = this.stateInterface.get();
        if (stateInterface.getState() != VEKitState.DESTROY && stateInterface.getState() != VEKitState.INITIALIZE) {
            this.nativeInterface.initializeFramework(surface, graphicBufferWidth, graphicBufferHeight, viewportWidth, viewportHeight, viewMode, frameworkType);
            stateInterface.setState(VEKitState.INITIALIZE);
            return;
        }
        Log.e(this.TAG, "initializeFramework invalid state. currentState = " + stateInterface.getState());
    }

    public synchronized void finalizeFramework() {
        VEStateInterface stateInterface = this.stateInterface.get();
        if (stateInterface.getState() != VEKitState.DESTROY && stateInterface.getState() != VEKitState.FINALIZE) {
            this.nativeInterface.finalizeFramework();
            stateInterface.setState(VEKitState.FINALIZE);
            return;
        }
        Log.e(this.TAG, "finalizeFramework invalid state. currentState = " + stateInterface.getState());
    }

    public synchronized void releaseFramework() {
        VEStateInterface stateInterface = this.stateInterface.get();
        if (stateInterface.getState() == VEKitState.DESTROY) {
            Log.e(this.TAG, "releaseFramework invalid state. currentState = " + stateInterface.getState());
            return;
        }
        if (this.nativeInterface != null) {
            this.nativeInterface.releaseFramework();
            NativeInterface.releaseInstance(this.nativeInterface);
            this.nativeInterface = null;
        }
        stateInterface.setState(VEKitState.DESTROY);
    }

    public void updateViewport(int x, int y, int width, int height) {
        synchronized (this) {
            VEStateInterface stateInterface = this.stateInterface.get();
            if (stateInterface.getState() != VEKitState.FINALIZE && stateInterface.getState() != VEKitState.DESTROY) {
                this.nativeInterface.updateViewport(x, y, width, height);
                return;
            }
            Log.e(this.TAG, "updateViewport invalid state. currentState = " + stateInterface.getState());
        }
    }

    public void create(Element element) {
        synchronized (this) {
            VEStateInterface stateInterface = this.stateInterface.get();
            if (stateInterface.getState() == VEKitState.DESTROY) {
                Log.e(this.TAG, "create invalid state. currentState = " + stateInterface.getState());
            } else {
                this.nativeInterface.create(element);
            }
        }
    }

    public void update(Element element) {
        synchronized (this) {
            VEStateInterface stateInterface = this.stateInterface.get();
            if (stateInterface.getState() == VEKitState.DESTROY) {
                Log.e(this.TAG, "update invalid state. currentState = " + stateInterface.getState());
            } else {
                this.nativeInterface.update(element);
            }
        }
    }

    public void remove(ElementType type, int id) {
        synchronized (this) {
            VEStateInterface stateInterface = this.stateInterface.get();
            if (stateInterface.getState() == VEKitState.DESTROY) {
                Log.e(this.TAG, "remove invalid state. currentState = " + stateInterface.getState());
            } else {
                this.nativeInterface.remove(type, id);
            }
        }
    }

    public void attachAnimation(Element element, int animationId) {
        synchronized (this) {
            VEStateInterface stateInterface = this.stateInterface.get();
            if (stateInterface.getState() == VEKitState.DESTROY) {
                Log.e(this.TAG, "attachAnimation invalid state. currentState = " + stateInterface.getState());
            } else {
                this.nativeInterface.attachAnimation(element, animationId);
            }
        }
    }

    public void detachAnimation(Element element, int animationId) {
        synchronized (this) {
            VEStateInterface stateInterface = this.stateInterface.get();
            if (stateInterface.getState() == VEKitState.DESTROY) {
                Log.e(this.TAG, "detachAnimation invalid state. currentState = " + stateInterface.getState());
            } else {
                this.nativeInterface.detachAnimation(element, animationId);
            }
        }
    }

    public void clearAnimations(Element element) {
        synchronized (this) {
            VEStateInterface stateInterface = this.stateInterface.get();
            if (stateInterface.getState() == VEKitState.DESTROY) {
                Log.e(this.TAG, "clearAnimations invalid state. currentState = " + stateInterface.getState());
            } else {
                this.nativeInterface.clearAnimations(element);
            }
        }
    }

    public void cancelAnimation() {
        synchronized (this) {
            VEStateInterface stateInterface = this.stateInterface.get();
            if (stateInterface.getState() != VEKitState.FINALIZE && stateInterface.getState() != VEKitState.DESTROY) {
                this.nativeInterface.cancelAnimation();
                return;
            }
            Log.e(this.TAG, "cancelAnimation  invalid state. currentState = " + stateInterface.getState());
        }
    }

    public void animate() {
        synchronized (this) {
            VEStateInterface stateInterface = this.stateInterface.get();
            if (stateInterface.getState() != VEKitState.FINALIZE && stateInterface.getState() != VEKitState.DESTROY) {
                this.nativeInterface.animate();
                return;
            }
            Log.e(this.TAG, "animate  invalid state. currentState = " + stateInterface.getState());
        }
    }

    public void attach(Element element, int childId) {
        synchronized (this) {
            VEStateInterface stateInterface = this.stateInterface.get();
            if (stateInterface.getState() == VEKitState.DESTROY) {
                Log.e(this.TAG, "attach invalid state. currentState = " + stateInterface.getState());
            } else {
                this.nativeInterface.attach(element, childId);
            }
        }
    }

    public void attach(Element element, int index, int childId) {
        synchronized (this) {
            VEStateInterface stateInterface = this.stateInterface.get();
            if (stateInterface.getState() == VEKitState.DESTROY) {
                Log.e(this.TAG, "attach invalid state. currentState = " + stateInterface.getState());
            } else {
                this.nativeInterface.attach(element, index, childId);
            }
        }
    }

    public void attach(Element element, ArrayList<Integer> list) {
        synchronized (this) {
            VEStateInterface stateInterface = this.stateInterface.get();
            if (stateInterface.getState() == VEKitState.DESTROY) {
                Log.e(this.TAG, "attach invalid state. currentState = " + stateInterface.getState());
            } else {
                this.nativeInterface.attach(element, list);
            }
        }
    }

    public void detach(Element element, int childId) {
        synchronized (this) {
            VEStateInterface stateInterface = this.stateInterface.get();
            if (stateInterface.getState() == VEKitState.DESTROY) {
                Log.e(this.TAG, "detach invalid state. currentState = " + stateInterface.getState());
            } else {
                this.nativeInterface.detach(element, childId);
            }
        }
    }

    public void clear(Element element) {
        synchronized (this) {
            VEStateInterface stateInterface = this.stateInterface.get();
            if (stateInterface.getState() == VEKitState.DESTROY) {
                Log.e(this.TAG, "clear invalid state. currentState = " + stateInterface.getState());
            } else {
                this.nativeInterface.clear(element);
            }
        }
    }

    public void swap(Element element, int from, int to) {
        synchronized (this) {
            VEStateInterface stateInterface = this.stateInterface.get();
            if (stateInterface.getState() == VEKitState.DESTROY) {
                Log.e(this.TAG, "swap invalid state. currentState = " + stateInterface.getState());
            } else {
                this.nativeInterface.swap(element, from, to);
            }
        }
    }

    public void show() {
        synchronized (this) {
            VEStateInterface stateInterface = this.stateInterface.get();
            if (stateInterface.getState() != VEKitState.FINALIZE && stateInterface.getState() != VEKitState.DESTROY) {
                this.nativeInterface.show();
                return;
            }
            Log.e(this.TAG, "show invalid state. currentState = " + stateInterface.getState());
        }
    }

    public void seekTo(long millisecond, SeekType seekType) {
        synchronized (this) {
            VEStateInterface stateInterface = this.stateInterface.get();
            if (stateInterface.getState() != VEKitState.FINALIZE && stateInterface.getState() != VEKitState.DESTROY) {
                this.nativeInterface.seekTo(millisecond, seekType);
                return;
            }
            Log.e(this.TAG, "seekTo invalid state. currentState = " + stateInterface.getState());
        }
    }

    public void play() {
        synchronized (this) {
            VEStateInterface stateInterface = this.stateInterface.get();
            if (stateInterface.getState() != VEKitState.FINALIZE && stateInterface.getState() != VEKitState.DESTROY) {
                this.nativeInterface.play();
                return;
            }
            Log.e(this.TAG, "play invalid state. currentState = " + stateInterface.getState());
        }
    }

    public void pause() {
        synchronized (this) {
            VEStateInterface stateInterface = this.stateInterface.get();
            if (stateInterface.getState() != VEKitState.FINALIZE && stateInterface.getState() != VEKitState.DESTROY) {
                this.nativeInterface.pause();
                return;
            }
            Log.e(this.TAG, "pause invalid state. currentState = " + stateInterface.getState());
        }
    }

    public void stop() {
        synchronized (this) {
            VEStateInterface stateInterface = this.stateInterface.get();
            if (stateInterface.getState() != VEKitState.FINALIZE && stateInterface.getState() != VEKitState.DESTROY) {
                this.nativeInterface.stop();
                return;
            }
            Log.e(this.TAG, "stop invalid state. currentState = " + stateInterface.getState());
        }
    }

    public Bitmap captureAnimatedFrame(Element element, int width, int height) {
        synchronized (this) {
            VEStateInterface stateInterface = this.stateInterface.get();
            if (stateInterface.getState() != VEKitState.FINALIZE && stateInterface.getState() != VEKitState.DESTROY) {
                return this.nativeInterface.captureAnimatedFrame(element, width, height);
            }
            Log.e(this.TAG, "captureAnimatedFrame invalid state. currentState = " + stateInterface.getState());
            return null;
        }
    }

    public Bitmap captureLatestFrame(int width, int height) {
        synchronized (this) {
            VEStateInterface stateInterface = this.stateInterface.get();
            if (stateInterface.getState() != VEKitState.FINALIZE && stateInterface.getState() != VEKitState.DESTROY) {
                return this.nativeInterface.captureLatestFrame(width, height);
            }
            Log.e(this.TAG, "captureLatestFrame invalid state. currentState = " + stateInterface.getState());
            return null;
        }
    }

    public Bitmap captureSuperHDRFrame(Element element, int width, int height, int centerX, int centerY) {
        synchronized (this) {
            VEStateInterface stateInterface = this.stateInterface.get();
            if (stateInterface.getState() != VEKitState.FINALIZE && stateInterface.getState() != VEKitState.DESTROY) {
                return this.nativeInterface.captureSuperHDRFrame(element, width, height, centerX, centerY);
            }
            Log.e(this.TAG, "captureSuperHDRFrame invalid state. currentState = " + stateInterface.getState());
            return null;
        }
    }

    public Bitmap captureStaticDoodle(Element element, int width, int height) {
        synchronized (this) {
            VEStateInterface stateInterface = this.stateInterface.get();
            if (stateInterface.getState() != VEKitState.FINALIZE && stateInterface.getState() != VEKitState.DESTROY) {
                return this.nativeInterface.captureStaticDoodle(element, width, height);
            }
            Log.e(this.TAG, "captureStaticDoodle invalid state. currentState = " + stateInterface.getState());
            return null;
        }
    }

    public long getCurrentMediaPosition() {
        synchronized (this) {
            VEStateInterface stateInterface = this.stateInterface.get();
            if (stateInterface.getState() != VEKitState.FINALIZE && stateInterface.getState() != VEKitState.DESTROY) {
                return this.nativeInterface.getCurrentMediaPosition();
            }
            Log.e(this.TAG, "getCurrentMediaPosition invalid state. currentState = " + stateInterface.getState());
            return 0L;
        }
    }

    public void setPreviewInfo(PreviewInfo previewInfo) {
        synchronized (this) {
            VEStateInterface stateInterface = this.stateInterface.get();
            if (stateInterface.getState() == VEKitState.DESTROY) {
                Log.e(this.TAG, "setPreviewInfo invalid state. currentState = " + stateInterface.getState());
            } else {
                this.nativeInterface.setPreviewInfo(previewInfo);
            }
        }
    }

    public void setExportInfo(ExportInfo info) {
        synchronized (this) {
            VEStateInterface stateInterface = this.stateInterface.get();
            if (stateInterface.getState() == VEKitState.DESTROY) {
                Log.e(this.TAG, "setExportInfo invalid state. currentState = " + stateInterface.getState());
            } else {
                this.nativeInterface.setExportInfo(info);
            }
        }
    }

    public long pauseExport() {
        synchronized (this) {
            VEStateInterface stateInterface = this.stateInterface.get();
            if (stateInterface.getState() != VEKitState.FINALIZE && stateInterface.getState() != VEKitState.DESTROY) {
                return this.nativeInterface.pauseExport();
            }
            Log.e(this.TAG, "pauseExport invalid state. currentState = " + stateInterface.getState());
            return 0L;
        }
    }

    public void resumeExport(long renderTime) {
        synchronized (this) {
            VEStateInterface stateInterface = this.stateInterface.get();
            if (stateInterface.getState() != VEKitState.FINALIZE && stateInterface.getState() != VEKitState.DESTROY) {
                this.nativeInterface.resumeExport(renderTime);
                return;
            }
            Log.e(this.TAG, "resumeExport invalid state. currentState = " + stateInterface.getState());
        }
    }

    public long getExportPosition() {
        synchronized (this) {
            VEStateInterface stateInterface = this.stateInterface.get();
            if (stateInterface.getState() != VEKitState.FINALIZE && stateInterface.getState() != VEKitState.DESTROY) {
                return this.nativeInterface.getExportPosition();
            }
            Log.e(this.TAG, "getExportPosition invalid state. currentState = " + stateInterface.getState());
            return 0L;
        }
    }

    public void startDoodle(Element element, DoodleStroke stroke) {
        synchronized (this) {
            VEStateInterface stateInterface = this.stateInterface.get();
            if (stateInterface.getState() != VEKitState.FINALIZE && stateInterface.getState() != VEKitState.DESTROY) {
                this.nativeInterface.startDoodle(element, stroke);
                return;
            }
            Log.e(this.TAG, "startDoodle invalid state. currentState = " + stateInterface.getState());
        }
    }

    public void drawDoodle(Element element, ArrayList<DoodlePoint> pointList) {
        synchronized (this) {
            VEStateInterface stateInterface = this.stateInterface.get();
            if (stateInterface.getState() != VEKitState.FINALIZE && stateInterface.getState() != VEKitState.DESTROY) {
                this.nativeInterface.drawDoodle(element, pointList);
                return;
            }
            Log.e(this.TAG, "drawDoodle invalid state. currentState = " + stateInterface.getState());
        }
    }

    public void finishDoodle(Element element) {
        synchronized (this) {
            VEStateInterface stateInterface = this.stateInterface.get();
            if (stateInterface.getState() != VEKitState.FINALIZE && stateInterface.getState() != VEKitState.DESTROY) {
                this.nativeInterface.finishDoodle(element);
                return;
            }
            Log.e(this.TAG, "finishDoodle invalid state. currentState = " + stateInterface.getState());
        }
    }

    public void attachStroke(Element element, DoodleStroke stroke) {
        synchronized (this) {
            VEStateInterface stateInterface = this.stateInterface.get();
            if (stateInterface.getState() == VEKitState.DESTROY) {
                Log.e(this.TAG, "attachStroke invalid state. currentState = " + stateInterface.getState());
            } else {
                this.nativeInterface.attachStroke(element, stroke);
            }
        }
    }

    public void detachStroke(Element element) {
        synchronized (this) {
            VEStateInterface stateInterface = this.stateInterface.get();
            if (stateInterface.getState() == VEKitState.DESTROY) {
                Log.e(this.TAG, "detachStroke invalid state. currentState = " + stateInterface.getState());
            } else {
                this.nativeInterface.detachStroke(element);
            }
        }
    }

    public void saveDoodle(Element element) {
        synchronized (this) {
            VEStateInterface stateInterface = this.stateInterface.get();
            if (stateInterface.getState() == VEKitState.DESTROY) {
                Log.e(this.TAG, "saveDoodle invalid state. currentState = " + stateInterface.getState());
            } else {
                this.nativeInterface.saveDoodle(element);
            }
        }
    }

    public void loadDoodle(Element element) {
        synchronized (this) {
            VEStateInterface stateInterface = this.stateInterface.get();
            if (stateInterface.getState() == VEKitState.DESTROY) {
                Log.e(this.TAG, "loadDoodle invalid state. currentState = " + stateInterface.getState());
            } else {
                this.nativeInterface.loadDoodle(element);
            }
        }
    }

    public FrcSupportInfo getFrcSupportInfo(int viewMode) {
        synchronized (this) {
            VEStateInterface stateInterface = this.stateInterface.get();
            if (stateInterface.getState() == VEKitState.DESTROY) {
                Log.e(this.TAG, "getFrcSupportInfo invalid state. currentState = " + stateInterface.getState());
                return null;
            }
            return this.nativeInterface.getFrcSupportInfo(viewMode);
        }
    }

    public void setAnalyzeInfo(AnalyzeInfo info) {
        synchronized (this) {
            VEStateInterface stateInterface = this.stateInterface.get();
            if (stateInterface.getState() == VEKitState.DESTROY) {
                Log.e(this.TAG, "analyze invalid state. currentState = " + stateInterface.getState());
            } else {
                this.nativeInterface.setAnalyzeInfo(info);
            }
        }
    }

    public void startAnalyze() {
        synchronized (this) {
            VEStateInterface stateInterface = this.stateInterface.get();
            if (stateInterface.getState() == VEKitState.DESTROY) {
                Log.e(this.TAG, "analyze invalid state. currentState = " + stateInterface.getState());
            } else {
                this.nativeInterface.startAnalyze();
            }
        }
    }

    public void stopAnalyze() {
        synchronized (this) {
            VEStateInterface stateInterface = this.stateInterface.get();
            if (stateInterface.getState() == VEKitState.DESTROY) {
                Log.e(this.TAG, "analyze invalid state. currentState = " + stateInterface.getState());
            } else {
                this.nativeInterface.stopAnalyze();
            }
        }
    }

    public long pauseAnalyze() {
        synchronized (this) {
            VEStateInterface stateInterface = this.stateInterface.get();
            if (stateInterface.getState() == VEKitState.DESTROY) {
                Log.e(this.TAG, "analyze invalid state. currentState = " + stateInterface.getState());
                return 0L;
            }
            return this.nativeInterface.pauseAnalyze();
        }
    }

    public void resumeAnalyze(long analyzedTime) {
        synchronized (this) {
            VEStateInterface stateInterface = this.stateInterface.get();
            if (stateInterface.getState() == VEKitState.DESTROY) {
                Log.e(this.TAG, "analyze invalid state. currentState = " + stateInterface.getState());
            } else {
                this.nativeInterface.resumeAnalyze(analyzedTime);
            }
        }
    }

    public void loadAnalyzeSolution(AnalyzeType type) {
        synchronized (this) {
            VEStateInterface stateInterface = this.stateInterface.get();
            if (stateInterface.getState() == VEKitState.DESTROY) {
                Log.e(this.TAG, "analyze invalid state. currentState = " + stateInterface.getState());
            } else {
                this.nativeInterface.loadAnalyzeSolution(type);
            }
        }
    }

    public void unloadAnalyzeSolution(AnalyzeType type) {
        synchronized (this) {
            VEStateInterface stateInterface = this.stateInterface.get();
            if (stateInterface.getState() == VEKitState.DESTROY) {
                Log.e(this.TAG, "analyze invalid state. currentState = " + stateInterface.getState());
            } else {
                this.nativeInterface.unloadAnalyzeSolution(type);
            }
        }
    }

    public long getCurrentAnalyzedPosition() {
        synchronized (this) {
            VEStateInterface stateInterface = this.stateInterface.get();
            if (stateInterface.getState() == VEKitState.DESTROY) {
                Log.e(this.TAG, "analyze invalid state. currentState = " + stateInterface.getState());
                return 0L;
            }
            return this.nativeInterface.getCurrentAnalyzedPosition();
        }
    }

    public void changePortraitVideoFocus(Element element, PVDetectionInfo detectionInfo) {
        synchronized (this) {
            VEStateInterface stateInterface = this.stateInterface.get();
            if (stateInterface.getState() == VEKitState.DESTROY) {
                Log.e(this.TAG, "analyze invalid state. currentState = " + stateInterface.getState());
            } else {
                this.nativeInterface.changePortraitVideoFocus(element, detectionInfo);
            }
        }
    }

    public void changePortraitVideoFocus(Element element, int focusX, int focusY) {
        synchronized (this) {
            VEStateInterface stateInterface = this.stateInterface.get();
            if (stateInterface.getState() == VEKitState.DESTROY) {
                Log.e(this.TAG, "analyze invalid state. currentState = " + stateInterface.getState());
            } else {
                this.nativeInterface.changePortraitVideoFocus(element, focusX, focusY);
            }
        }
    }

    public void changePortraitVideoKeyFrame(Element element, PVKeyFrame keyFrame) {
        synchronized (this) {
            VEStateInterface stateInterface = this.stateInterface.get();
            if (stateInterface.getState() == VEKitState.DESTROY) {
                Log.e(this.TAG, "analyze invalid state. currentState = " + stateInterface.getState());
            } else {
                this.nativeInterface.changePortraitVideoKeyFrame(element, keyFrame);
            }
        }
    }

    public void changePortraitVideoKeyFrameList(Element element, ArrayList<PVKeyFrame> keyFrameList) {
        synchronized (this) {
            VEStateInterface stateInterface = this.stateInterface.get();
            if (stateInterface.getState() == VEKitState.DESTROY) {
                Log.e(this.TAG, "analyze invalid state. currentState = " + stateInterface.getState());
            } else {
                this.nativeInterface.changePortraitVideoKeyFrameList(element, keyFrameList);
            }
        }
    }

    public void deletePortraitVideoKeyFrame(Element element, int keyFrameId) {
        synchronized (this) {
            VEStateInterface stateInterface = this.stateInterface.get();
            if (stateInterface.getState() == VEKitState.DESTROY) {
                Log.e(this.TAG, "analyze invalid state. currentState = " + stateInterface.getState());
            } else {
                this.nativeInterface.deletePortraitVideoKeyFrame(element, keyFrameId);
            }
        }
    }
}
