package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public class SemContextFlatMotion extends SemContextEventContext {
    public static final Parcelable.Creator<SemContextFlatMotion> CREATOR = new Parcelable.Creator<SemContextFlatMotion>() { // from class: com.samsung.android.hardware.context.SemContextFlatMotion.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public SemContextFlatMotion createFromParcel(Parcel in) {
            return new SemContextFlatMotion(in);
        }

        @Override // android.os.Parcelable.Creator
        public SemContextFlatMotion[] newArray(int size) {
            return new SemContextFlatMotion[size];
        }
    };
    public static final int FALSE = 2;
    public static final int TRUE = 1;
    public static final int UNKNOWN = 0;
    private Bundle mContext;

    /* renamed from: com.samsung.android.hardware.context.SemContextFlatMotion$1 */
    /* loaded from: classes5.dex */
    class AnonymousClass1 implements Parcelable.Creator<SemContextFlatMotion> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public SemContextFlatMotion createFromParcel(Parcel in) {
            return new SemContextFlatMotion(in);
        }

        @Override // android.os.Parcelable.Creator
        public SemContextFlatMotion[] newArray(int size) {
            return new SemContextFlatMotion[size];
        }
    }

    public SemContextFlatMotion() {
        this.mContext = new Bundle();
    }

    SemContextFlatMotion(Parcel src) {
        readFromParcel(src);
    }

    public int getAction() {
        return this.mContext.getInt("Action");
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
