package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public class SemContextBounceShortMotion extends SemContextEventContext {
    public static final Parcelable.Creator<SemContextBounceShortMotion> CREATOR = new Parcelable.Creator<SemContextBounceShortMotion>() { // from class: com.samsung.android.hardware.context.SemContextBounceShortMotion.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public SemContextBounceShortMotion createFromParcel(Parcel in) {
            return new SemContextBounceShortMotion(in);
        }

        @Override // android.os.Parcelable.Creator
        public SemContextBounceShortMotion[] newArray(int size) {
            return new SemContextBounceShortMotion[size];
        }
    };
    public static final int LEFT = 2;
    public static final int NONE = 0;
    public static final int RIGHT = 1;
    private Bundle mContext;

    /* renamed from: com.samsung.android.hardware.context.SemContextBounceShortMotion$1 */
    /* loaded from: classes5.dex */
    class AnonymousClass1 implements Parcelable.Creator<SemContextBounceShortMotion> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public SemContextBounceShortMotion createFromParcel(Parcel in) {
            return new SemContextBounceShortMotion(in);
        }

        @Override // android.os.Parcelable.Creator
        public SemContextBounceShortMotion[] newArray(int size) {
            return new SemContextBounceShortMotion[size];
        }
    }

    public SemContextBounceShortMotion() {
        this.mContext = new Bundle();
    }

    SemContextBounceShortMotion(Parcel src) {
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
