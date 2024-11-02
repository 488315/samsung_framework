package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public class SemContextShakeMotion extends SemContextEventContext {
    public static final Parcelable.Creator<SemContextShakeMotion> CREATOR = new Parcelable.Creator<SemContextShakeMotion>() { // from class: com.samsung.android.hardware.context.SemContextShakeMotion.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public SemContextShakeMotion createFromParcel(Parcel in) {
            return new SemContextShakeMotion(in);
        }

        @Override // android.os.Parcelable.Creator
        public SemContextShakeMotion[] newArray(int size) {
            return new SemContextShakeMotion[size];
        }
    };
    public static final int NONE = 0;
    public static final int START = 1;
    public static final int STOP = 2;
    private Bundle mContext;

    /* renamed from: com.samsung.android.hardware.context.SemContextShakeMotion$1 */
    /* loaded from: classes5.dex */
    class AnonymousClass1 implements Parcelable.Creator<SemContextShakeMotion> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public SemContextShakeMotion createFromParcel(Parcel in) {
            return new SemContextShakeMotion(in);
        }

        @Override // android.os.Parcelable.Creator
        public SemContextShakeMotion[] newArray(int size) {
            return new SemContextShakeMotion[size];
        }
    }

    public SemContextShakeMotion() {
        this.mContext = new Bundle();
    }

    SemContextShakeMotion(Parcel src) {
        readFromParcel(src);
    }

    public int getShakeStatus() {
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
