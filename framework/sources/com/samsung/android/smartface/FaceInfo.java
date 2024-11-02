package com.samsung.android.smartface;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public final class FaceInfo implements Parcelable {
    public static final Parcelable.Creator<FaceInfo> CREATOR = new Parcelable.Creator<FaceInfo>() { // from class: com.samsung.android.smartface.FaceInfo.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public FaceInfo createFromParcel(Parcel in) {
            return new FaceInfo(in);
        }

        @Override // android.os.Parcelable.Creator
        public FaceInfo[] newArray(int size) {
            return new FaceInfo[size];
        }
    };
    public static final int NEED_TO_SLEEP = 0;
    public static final int NEED_TO_STAY = 1;
    public int needToRotate;
    public int needToStay;

    public FaceInfo() {
    }

    public FaceInfo(Parcel in) {
        readFromParcel(in);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.needToRotate);
        out.writeInt(this.needToStay);
    }

    public void readFromParcel(Parcel in) {
        this.needToRotate = in.readInt();
        this.needToStay = in.readInt();
    }

    /* renamed from: com.samsung.android.smartface.FaceInfo$1 */
    /* loaded from: classes5.dex */
    class AnonymousClass1 implements Parcelable.Creator<FaceInfo> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public FaceInfo createFromParcel(Parcel in) {
            return new FaceInfo(in);
        }

        @Override // android.os.Parcelable.Creator
        public FaceInfo[] newArray(int size) {
            return new FaceInfo[size];
        }
    }
}
