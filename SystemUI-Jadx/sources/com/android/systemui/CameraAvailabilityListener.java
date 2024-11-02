package com.android.systemui;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.hardware.camera2.CameraManager;
import android.os.Handler;
import android.util.Log;
import com.android.systemui.CameraAvailabilityListener;
import com.android.systemui.util.concurrency.DelayableExecutor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt__MathJVMKt;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class CameraAvailabilityListener {
    public static final Factory Factory = new Factory(null);
    public final CameraAvailabilityListener$cameraDeviceStateCallback$1 cameraDeviceStateCallback;
    public final HashMap cameraDeviceStates;
    public final CameraManager cameraManager;
    public final Rect cutoutBounds;
    public final Path cutoutProtectionPath;
    public final Set excludedPackageIds;
    public final Executor executor;
    public final Handler handler;
    public final List listeners;
    public final String targetCameraId;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public interface CameraTransitionCallback {
        void onApplyCameraProtection(Path path, Rect rect);

        void onHideCameraProtection();
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class Factory {
        private Factory() {
        }

        public /* synthetic */ Factory(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static CameraAvailabilityListener build(Context context, DelayableExecutor delayableExecutor, Handler handler) {
            CameraManager cameraManager = (CameraManager) context.getSystemService("camera");
            Resources resources = context.getResources();
            resources.getString(R.string.config_frontBuiltInDisplayCutoutProtection);
            return new CameraAvailabilityListener(cameraManager, new Path(), resources.getString(R.string.config_protectedCameraId), resources.getString(R.string.config_cameraProtectionExcludedPackages), delayableExecutor, handler);
        }
    }

    /* JADX WARN: Type inference failed for: r4v4, types: [com.android.systemui.CameraAvailabilityListener$cameraDeviceStateCallback$1] */
    public CameraAvailabilityListener(CameraManager cameraManager, Path path, String str, String str2, Executor executor, Handler handler) {
        this.cameraManager = cameraManager;
        this.cutoutProtectionPath = path;
        this.targetCameraId = str;
        this.executor = executor;
        this.handler = handler;
        Rect rect = new Rect();
        this.cutoutBounds = rect;
        this.listeners = new ArrayList();
        new CameraManager.AvailabilityCallback() { // from class: com.android.systemui.CameraAvailabilityListener$availabilityCallback$1
            public final void onCameraClosed(String str3) {
                if (Intrinsics.areEqual(CameraAvailabilityListener.this.targetCameraId, str3)) {
                    Iterator it = ((ArrayList) CameraAvailabilityListener.this.listeners).iterator();
                    while (it.hasNext()) {
                        ((CameraAvailabilityListener.CameraTransitionCallback) it.next()).onHideCameraProtection();
                    }
                }
            }

            public final void onCameraOpened(String str3, String str4) {
                if (Intrinsics.areEqual(CameraAvailabilityListener.this.targetCameraId, str3) && !CameraAvailabilityListener.this.excludedPackageIds.contains(str4)) {
                    CameraAvailabilityListener.access$notifyCameraActive(CameraAvailabilityListener.this);
                }
            }
        };
        this.cameraDeviceStates = new HashMap();
        this.cameraDeviceStateCallback = new CameraManager.SemCameraDeviceStateCallback() { // from class: com.android.systemui.CameraAvailabilityListener$cameraDeviceStateCallback$1
            public final void onCameraDeviceStateChanged(String str3, int i, int i2, String str4) {
                String str5;
                boolean z;
                CameraAvailabilityListener cameraAvailabilityListener = CameraAvailabilityListener.this;
                CameraAvailabilityListener.Factory factory = CameraAvailabilityListener.Factory;
                cameraAvailabilityListener.getClass();
                if (i2 != 0) {
                    if (i2 != 1) {
                        if (i2 != 2) {
                            if (i2 != 3) {
                                str5 = "";
                            } else {
                                str5 = "CAMERA_STATE_CLOSED";
                            }
                        } else {
                            str5 = "CAMERA_STATE_IDLE";
                        }
                    } else {
                        str5 = "CAMERA_STATE_ACTIVE";
                    }
                } else {
                    str5 = "CAMERA_STATE_OPEN";
                }
                StringBuilder m = CameraAvailabilityListener$cameraDeviceStateCallback$1$$ExternalSyntheticOutline0.m("onCameraDeviceStateChanged: id=", str3, ", facing=", i, ", state=");
                m.append(str5);
                m.append(", client=");
                m.append(str4);
                Log.d("CameraAvailabilityListener", m.toString());
                if (ArraysKt___ArraysKt.contains(CameraAvailabilityListenerKt.CAMERA_DEVICE_STATE_CALLBACK_FILTER_PACKAGE, str4)) {
                    return;
                }
                if (i == 1) {
                    CameraAvailabilityListener.this.cameraDeviceStates.put(str3, Integer.valueOf(i2));
                }
                HashMap hashMap = CameraAvailabilityListener.this.cameraDeviceStates;
                LinkedHashMap linkedHashMap = new LinkedHashMap();
                for (Map.Entry entry : hashMap.entrySet()) {
                    int intValue = ((Number) entry.getValue()).intValue();
                    if (intValue != 1 && intValue != 0) {
                        z = false;
                    } else {
                        z = true;
                    }
                    if (z) {
                        linkedHashMap.put(entry.getKey(), entry.getValue());
                    }
                }
                int size = linkedHashMap.size();
                if (size > 0) {
                    CameraAvailabilityListener.access$notifyCameraActive(CameraAvailabilityListener.this);
                } else if (size == 0 && i2 == 3) {
                    Iterator it = ((ArrayList) CameraAvailabilityListener.this.listeners).iterator();
                    while (it.hasNext()) {
                        ((CameraAvailabilityListener.CameraTransitionCallback) it.next()).onHideCameraProtection();
                    }
                }
            }
        };
        RectF rectF = new RectF();
        path.computeBounds(rectF, false);
        rect.set(MathKt__MathJVMKt.roundToInt(rectF.left), MathKt__MathJVMKt.roundToInt(rectF.top), MathKt__MathJVMKt.roundToInt(rectF.right), MathKt__MathJVMKt.roundToInt(rectF.bottom));
        this.excludedPackageIds = CollectionsKt___CollectionsKt.toSet(StringsKt__StringsKt.split$default(str2, new String[]{","}, 0, 6));
    }

    public static final void access$notifyCameraActive(CameraAvailabilityListener cameraAvailabilityListener) {
        Iterator it = ((ArrayList) cameraAvailabilityListener.listeners).iterator();
        while (it.hasNext()) {
            ((CameraTransitionCallback) it.next()).onApplyCameraProtection(cameraAvailabilityListener.cutoutProtectionPath, cameraAvailabilityListener.cutoutBounds);
        }
    }
}
