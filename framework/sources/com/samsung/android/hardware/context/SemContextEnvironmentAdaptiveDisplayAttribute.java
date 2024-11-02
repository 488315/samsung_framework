package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/* loaded from: classes5.dex */
public class SemContextEnvironmentAdaptiveDisplayAttribute extends SemContextAttribute {
    public static final Parcelable.Creator<SemContextEnvironmentAdaptiveDisplayAttribute> CREATOR = new Parcelable.Creator<SemContextEnvironmentAdaptiveDisplayAttribute>() { // from class: com.samsung.android.hardware.context.SemContextEnvironmentAdaptiveDisplayAttribute.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public SemContextEnvironmentAdaptiveDisplayAttribute createFromParcel(Parcel in) {
            return new SemContextEnvironmentAdaptiveDisplayAttribute(in);
        }

        @Override // android.os.Parcelable.Creator
        public SemContextEnvironmentAdaptiveDisplayAttribute[] newArray(int size) {
            return new SemContextEnvironmentAdaptiveDisplayAttribute[size];
        }
    };
    private static final String TAG = "SemContextEnvironmentAdaptiveDisplayAttribute";
    private float mColorThreshold;
    private int mDuration;

    /* renamed from: com.samsung.android.hardware.context.SemContextEnvironmentAdaptiveDisplayAttribute$1 */
    /* loaded from: classes5.dex */
    class AnonymousClass1 implements Parcelable.Creator<SemContextEnvironmentAdaptiveDisplayAttribute> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public SemContextEnvironmentAdaptiveDisplayAttribute createFromParcel(Parcel in) {
            return new SemContextEnvironmentAdaptiveDisplayAttribute(in);
        }

        @Override // android.os.Parcelable.Creator
        public SemContextEnvironmentAdaptiveDisplayAttribute[] newArray(int size) {
            return new SemContextEnvironmentAdaptiveDisplayAttribute[size];
        }
    }

    SemContextEnvironmentAdaptiveDisplayAttribute() {
        this.mColorThreshold = 0.07f;
        this.mDuration = 35;
        setAttribute();
    }

    SemContextEnvironmentAdaptiveDisplayAttribute(Parcel src) {
        super(src);
        this.mColorThreshold = 0.07f;
        this.mDuration = 35;
    }

    public SemContextEnvironmentAdaptiveDisplayAttribute(float colorThreshold, int duration) {
        this.mColorThreshold = 0.07f;
        this.mDuration = 35;
        this.mColorThreshold = colorThreshold;
        this.mDuration = duration;
        setAttribute();
    }

    @Override // com.samsung.android.hardware.context.SemContextAttribute
    public boolean checkAttribute() {
        if (this.mColorThreshold < 0.0f) {
            Log.e(TAG, "The color threshold value is wrong.");
            return false;
        }
        int i = this.mDuration;
        if (i < 0 || i > 255) {
            Log.e(TAG, "The duration value is wrong.");
            return false;
        }
        return true;
    }

    private void setAttribute() {
        Bundle attribute = new Bundle();
        attribute.putFloat("color_threshold", this.mColorThreshold);
        attribute.putInt("duration", this.mDuration);
        Log.d(TAG, "setAttribute() mColorThreshold : " + attribute.getFloat("color_threshold"));
        Log.d(TAG, "setAttribute() mDuration : " + attribute.getInt("duration"));
        super.setAttribute(44, attribute);
    }
}
