package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

@Deprecated(forRemoval = true, since = "15.5")
/* loaded from: classes5.dex */
public class SemContextStepLevelMonitorAttribute extends SemContextAttribute {
    public static final Parcelable.Creator<SemContextStepLevelMonitorAttribute> CREATOR = new Parcelable.Creator<SemContextStepLevelMonitorAttribute>() { // from class: com.samsung.android.hardware.context.SemContextStepLevelMonitorAttribute.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public SemContextStepLevelMonitorAttribute createFromParcel(Parcel in) {
            return new SemContextStepLevelMonitorAttribute(in);
        }

        @Override // android.os.Parcelable.Creator
        public SemContextStepLevelMonitorAttribute[] newArray(int size) {
            return new SemContextStepLevelMonitorAttribute[size];
        }
    };
    private static final String TAG = "SemContextStepLevelMonitorAttribute";
    private int mDuration;

    /* renamed from: com.samsung.android.hardware.context.SemContextStepLevelMonitorAttribute$1 */
    /* loaded from: classes5.dex */
    class AnonymousClass1 implements Parcelable.Creator<SemContextStepLevelMonitorAttribute> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public SemContextStepLevelMonitorAttribute createFromParcel(Parcel in) {
            return new SemContextStepLevelMonitorAttribute(in);
        }

        @Override // android.os.Parcelable.Creator
        public SemContextStepLevelMonitorAttribute[] newArray(int size) {
            return new SemContextStepLevelMonitorAttribute[size];
        }
    }

    public SemContextStepLevelMonitorAttribute() {
        this.mDuration = 300;
        setAttribute();
    }

    SemContextStepLevelMonitorAttribute(Parcel src) {
        super(src);
        this.mDuration = 300;
    }

    public SemContextStepLevelMonitorAttribute(int duration) {
        this.mDuration = 300;
        this.mDuration = duration;
        setAttribute();
    }

    @Override // com.samsung.android.hardware.context.SemContextAttribute
    public boolean checkAttribute() {
        if (this.mDuration < 0) {
            Log.e(TAG, "The duration is wrong.");
            return false;
        }
        return true;
    }

    private void setAttribute() {
        Bundle attribute = new Bundle();
        attribute.putInt("duration", this.mDuration);
        super.setAttribute(33, attribute);
    }
}
