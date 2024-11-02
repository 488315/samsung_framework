package com.samsung.android.knox.location;

import android.os.Parcel;
import android.os.Parcelable;
import java.io.Serializable;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes3.dex */
public class LatLongPoint implements Serializable, Parcelable {
    public static final Parcelable.Creator<LatLongPoint> CREATOR = new Parcelable.Creator<LatLongPoint>() { // from class: com.samsung.android.knox.location.LatLongPoint.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final LatLongPoint[] newArray(int i) {
            return new LatLongPoint[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final LatLongPoint createFromParcel(Parcel parcel) {
            return new LatLongPoint(parcel, 0);
        }

        @Override // android.os.Parcelable.Creator
        public final LatLongPoint[] newArray(int i) {
            return new LatLongPoint[i];
        }
    };
    private static final long serialVersionUID = 1;
    public double latitude;
    public double longitude;

    public /* synthetic */ LatLongPoint(Parcel parcel, int i) {
        this(parcel);
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final void readFromParcel(Parcel parcel) {
        this.latitude = parcel.readDouble();
        this.longitude = parcel.readDouble();
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(this.latitude);
        parcel.writeDouble(this.longitude);
    }

    public LatLongPoint(double d, double d2) {
        this.latitude = d;
        this.longitude = d2;
    }

    private LatLongPoint(Parcel parcel) {
        readFromParcel(parcel);
    }
}
