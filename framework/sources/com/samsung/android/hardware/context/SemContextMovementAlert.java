package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@Deprecated
/* loaded from: classes5.dex */
public class SemContextMovementAlert extends SemContextEventContext {
    public static final Parcelable.Creator<SemContextMovementAlert> CREATOR = new Parcelable.Creator<SemContextMovementAlert>() { // from class: com.samsung.android.hardware.context.SemContextMovementAlert.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public SemContextMovementAlert createFromParcel(Parcel in) {
            return new SemContextMovementAlert(in);
        }

        @Override // android.os.Parcelable.Creator
        public SemContextMovementAlert[] newArray(int size) {
            return new SemContextMovementAlert[size];
        }
    };

    @Deprecated
    public static final int MOVE = 1;

    @Deprecated
    public static final int NO_MOVE = 2;

    @Deprecated
    public static final int UNKNOWN = 0;
    private Bundle mContext;

    /* renamed from: com.samsung.android.hardware.context.SemContextMovementAlert$1 */
    /* loaded from: classes5.dex */
    class AnonymousClass1 implements Parcelable.Creator<SemContextMovementAlert> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public SemContextMovementAlert createFromParcel(Parcel in) {
            return new SemContextMovementAlert(in);
        }

        @Override // android.os.Parcelable.Creator
        public SemContextMovementAlert[] newArray(int size) {
            return new SemContextMovementAlert[size];
        }
    }

    public SemContextMovementAlert() {
        this.mContext = new Bundle();
    }

    SemContextMovementAlert(Parcel src) {
        readFromParcel(src);
    }

    public int getAction() {
        return this.mContext.getInt("Action");
    }

    @Override // com.samsung.android.hardware.context.SemContextEventContext, android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeBundle(this.mContext);
    }

    private void readFromParcel(Parcel src) {
        this.mContext = src.readBundle(getClass().getClassLoader());
    }
}
