package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/* loaded from: classes5.dex */
public class SemContextFlatMotionForTableModeAttribute extends SemContextAttribute {
    public static final Parcelable.Creator<SemContextFlatMotionForTableModeAttribute> CREATOR = new Parcelable.Creator<SemContextFlatMotionForTableModeAttribute>() { // from class: com.samsung.android.hardware.context.SemContextFlatMotionForTableModeAttribute.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public SemContextFlatMotionForTableModeAttribute createFromParcel(Parcel in) {
            return new SemContextFlatMotionForTableModeAttribute(in);
        }

        @Override // android.os.Parcelable.Creator
        public SemContextFlatMotionForTableModeAttribute[] newArray(int size) {
            return new SemContextFlatMotionForTableModeAttribute[size];
        }
    };
    private static final String TAG = "SemContextFlatMotionForTableModeAttribute";
    private int mDuration;

    /* renamed from: com.samsung.android.hardware.context.SemContextFlatMotionForTableModeAttribute$1 */
    /* loaded from: classes5.dex */
    class AnonymousClass1 implements Parcelable.Creator<SemContextFlatMotionForTableModeAttribute> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public SemContextFlatMotionForTableModeAttribute createFromParcel(Parcel in) {
            return new SemContextFlatMotionForTableModeAttribute(in);
        }

        @Override // android.os.Parcelable.Creator
        public SemContextFlatMotionForTableModeAttribute[] newArray(int size) {
            return new SemContextFlatMotionForTableModeAttribute[size];
        }
    }

    public SemContextFlatMotionForTableModeAttribute() {
        this.mDuration = 500;
        setAttribute();
    }

    SemContextFlatMotionForTableModeAttribute(Parcel src) {
        super(src);
        this.mDuration = 500;
    }

    public SemContextFlatMotionForTableModeAttribute(int duration) {
        this.mDuration = 500;
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
        super.setAttribute(36, attribute);
    }
}
