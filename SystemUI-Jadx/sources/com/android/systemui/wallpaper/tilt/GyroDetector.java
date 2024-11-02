package com.android.systemui.wallpaper.tilt;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.animation.PathInterpolator;
import com.android.systemui.coverlauncher.utils.badge.NotificationListener$$ExternalSyntheticOutline0;
import com.android.systemui.wallpaper.tilt.TiltColorController;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class GyroDetector {
    public Sensor mGyroSensor;
    public final GyroSensorChangeListener mGyroSensorChangeListener;
    public boolean mResumed = false;
    public SensorListener mSensorListener;
    public SensorManager mSensorManager;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public interface GyroSensorChangeListener {
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public final class SensorListener implements SensorEventListener {
        public /* synthetic */ SensorListener(GyroDetector gyroDetector, int i) {
            this();
        }

        @Override // android.hardware.SensorEventListener
        public final void onSensorChanged(SensorEvent sensorEvent) {
            float max;
            if (sensorEvent.sensor.getType() != 65579) {
                return;
            }
            float[] fArr = sensorEvent.values;
            float f = fArr[0];
            float f2 = fArr[1];
            float f3 = fArr[2];
            GyroSensorChangeListener gyroSensorChangeListener = GyroDetector.this.mGyroSensorChangeListener;
            if (gyroSensorChangeListener != null) {
                TiltColorController tiltColorController = TiltColorController.this;
                if (tiltColorController.mIsEnable && tiltColorController.mIsGyroAllowed && Math.abs(f2) > 0.0f && !tiltColorController.mEnterAnimator.mIsRunning) {
                    float f4 = tiltColorController.mTotalRotation + f2;
                    tiltColorController.mTotalRotation = f4;
                    float f5 = 160.0f;
                    if (Math.abs(f4) > 160.0f) {
                        if (tiltColorController.mTotalRotation >= 0.0f) {
                            f5 = -160.0f;
                        }
                        tiltColorController.mTotalRotation = f5;
                        tiltColorController.mMaxRotation = f5;
                    } else if (Math.abs(tiltColorController.mTotalRotation) > Math.abs(tiltColorController.mMaxRotation)) {
                        tiltColorController.mMaxRotation = tiltColorController.mTotalRotation;
                    } else if (tiltColorController.mMaxRotation * tiltColorController.mTotalRotation < 0.0f) {
                        tiltColorController.mMaxRotation = 0.0f;
                    }
                    if (Math.abs(tiltColorController.mMaxRotation) > 10.0f) {
                        float abs = Math.abs(tiltColorController.mMaxRotation);
                        max = Math.max(-abs, Math.min(abs, tiltColorController.mTotalRotation)) / abs;
                    } else {
                        max = Math.max(-10.0f, Math.min(10.0f, tiltColorController.mTotalRotation)) / 10.0f;
                    }
                    tiltColorController.mTiltScale.setTarget(max * 1.0f);
                    TiltColorController.AnonymousClass2 anonymousClass2 = tiltColorController.mTiltHandler;
                    if (!anonymousClass2.hasMessages(0)) {
                        anonymousClass2.sendEmptyMessageDelayed(0, 0L);
                    }
                }
                if (!tiltColorController.mIsGyroAllowed || !tiltColorController.mIsEnable) {
                    PathInterpolator pathInterpolator = TiltColorController.BASE_INTERPOLATOR;
                    StringBuilder sb = new StringBuilder("onGyroChanged: pause ");
                    sb.append(tiltColorController.mIsGyroAllowed);
                    sb.append(" isEnable");
                    NotificationListener$$ExternalSyntheticOutline0.m(sb, tiltColorController.mIsEnable, "TiltColorController");
                    tiltColorController.mGyroDetector.pause();
                }
            }
        }

        private SensorListener() {
        }

        @Override // android.hardware.SensorEventListener
        public final void onAccuracyChanged(Sensor sensor, int i) {
        }
    }

    public GyroDetector(Context context, GyroSensorChangeListener gyroSensorChangeListener) {
        this.mSensorManager = null;
        this.mGyroSensor = null;
        this.mGyroSensorChangeListener = gyroSensorChangeListener;
        SensorManager sensorManager = (SensorManager) context.getSystemService("sensor");
        this.mSensorManager = sensorManager;
        this.mGyroSensor = sensorManager.getDefaultSensor(65579);
        this.mSensorListener = new SensorListener(this, 0);
    }

    public final void pause() {
        if (!this.mResumed) {
            return;
        }
        Log.e("GyroDetector", "Sensor paused.");
        this.mResumed = false;
        this.mSensorManager.unregisterListener(this.mSensorListener);
    }

    public final String toString() {
        return "GyroDetector{mResumed=" + this.mResumed + '}';
    }
}
