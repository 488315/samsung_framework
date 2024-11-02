package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public class SemContextCallMotion extends SemContextEventContext {
    public static final int ACTION = 1;
    public static final Parcelable.Creator<SemContextCallMotion> CREATOR = new Parcelable.Creator<SemContextCallMotion>() { // from class: com.samsung.android.hardware.context.SemContextCallMotion.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public SemContextCallMotion createFromParcel(Parcel in) {
            return new SemContextCallMotion(in);
        }

        @Override // android.os.Parcelable.Creator
        public SemContextCallMotion[] newArray(int size) {
            return new SemContextCallMotion[size];
        }
    };
    public static final int NONE = 0;
    private Bundle mContext;

    /* renamed from: com.samsung.android.hardware.context.SemContextCallMotion$1 */
    /* loaded from: classes5.dex */
    class AnonymousClass1 implements Parcelable.Creator<SemContextCallMotion> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public SemContextCallMotion createFromParcel(Parcel in) {
            return new SemContextCallMotion(in);
        }

        @Override // android.os.Parcelable.Creator
        public SemContextCallMotion[] newArray(int size) {
            return new SemContextCallMotion[size];
        }
    }

    public SemContextCallMotion() {
        this.mContext = new Bundle();
    }

    SemContextCallMotion(Parcel src) {
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
