package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public class SemContextActivityTracker extends SemContextEventContext {
    public static final int ACCURACY_HIGH = 2;
    public static final int ACCURACY_LOW = 0;
    public static final int ACCURACY_MID = 1;
    public static final Parcelable.Creator<SemContextActivityTracker> CREATOR = new Parcelable.Creator<SemContextActivityTracker>() { // from class: com.samsung.android.hardware.context.SemContextActivityTracker.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public SemContextActivityTracker createFromParcel(Parcel in) {
            return new SemContextActivityTracker(in);
        }

        @Override // android.os.Parcelable.Creator
        public SemContextActivityTracker[] newArray(int size) {
            return new SemContextActivityTracker[size];
        }
    };
    public static final int STATUS_BIKE = 5;
    public static final int STATUS_RUN = 3;
    public static final int STATUS_STATIONARY = 1;
    public static final int STATUS_UNKNOWN = 0;
    public static final int STATUS_VEHICLE = 4;
    public static final int STATUS_WALK = 2;
    private Bundle mContext;

    /* renamed from: com.samsung.android.hardware.context.SemContextActivityTracker$1 */
    /* loaded from: classes5.dex */
    class AnonymousClass1 implements Parcelable.Creator<SemContextActivityTracker> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public SemContextActivityTracker createFromParcel(Parcel in) {
            return new SemContextActivityTracker(in);
        }

        @Override // android.os.Parcelable.Creator
        public SemContextActivityTracker[] newArray(int size) {
            return new SemContextActivityTracker[size];
        }
    }

    public SemContextActivityTracker() {
        this.mContext = new Bundle();
    }

    SemContextActivityTracker(Parcel src) {
        readFromParcel(src);
    }

    public long getTimeStamp() {
        return this.mContext.getLong("TimeStamp");
    }

    public int getStatus() {
        return this.mContext.getInt("ActivityType");
    }

    public int getAccuracy() {
        return this.mContext.getInt("Accuracy");
    }

    @Override // com.samsung.android.hardware.context.SemContextEventContext
    public void setValues(Bundle context) {
        this.mContext = context;
    }

    @Override // com.samsung.android.hardware.context.SemContextEventContext, android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeBundle(this.mContext);
    }

    private void readFromParcel(Parcel src) {
        this.mContext = src.readBundle(getClass().getClassLoader());
    }
}
