package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public class SemContextApproachAttribute extends SemContextAttribute {
    public static final Parcelable.Creator<SemContextApproachAttribute> CREATOR = new Parcelable.Creator<SemContextApproachAttribute>() { // from class: com.samsung.android.hardware.context.SemContextApproachAttribute.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public SemContextApproachAttribute createFromParcel(Parcel in) {
            return new SemContextApproachAttribute(in);
        }

        @Override // android.os.Parcelable.Creator
        public SemContextApproachAttribute[] newArray(int size) {
            return new SemContextApproachAttribute[size];
        }
    };
    private int mUserID;

    /* renamed from: com.samsung.android.hardware.context.SemContextApproachAttribute$1 */
    /* loaded from: classes5.dex */
    class AnonymousClass1 implements Parcelable.Creator<SemContextApproachAttribute> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public SemContextApproachAttribute createFromParcel(Parcel in) {
            return new SemContextApproachAttribute(in);
        }

        @Override // android.os.Parcelable.Creator
        public SemContextApproachAttribute[] newArray(int size) {
            return new SemContextApproachAttribute[size];
        }
    }

    public SemContextApproachAttribute() {
        this.mUserID = -1;
        setAttribute();
    }

    SemContextApproachAttribute(Parcel src) {
        super(src);
        this.mUserID = -1;
    }

    public SemContextApproachAttribute(int userID) {
        this.mUserID = -1;
        this.mUserID = userID;
        setAttribute();
    }

    @Override // com.samsung.android.hardware.context.SemContextAttribute
    public boolean checkAttribute() {
        return true;
    }

    private void setAttribute() {
        Bundle attribute = new Bundle();
        attribute.putInt("UserID", this.mUserID);
        super.setAttribute(1, attribute);
    }
}
