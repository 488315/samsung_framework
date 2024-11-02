package com.samsung.android.knox.application;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes3.dex */
public final class NetworkStats implements Parcelable {
    public static final Parcelable.Creator<NetworkStats> CREATOR = new Parcelable.Creator<NetworkStats>() { // from class: com.samsung.android.knox.application.NetworkStats.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final NetworkStats createFromParcel(Parcel parcel) {
            return new NetworkStats(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final NetworkStats[] newArray(int i) {
            return new NetworkStats[i];
        }

        @Override // android.os.Parcelable.Creator
        public final NetworkStats createFromParcel(Parcel parcel) {
            return new NetworkStats(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public final NetworkStats[] newArray(int i) {
            return new NetworkStats[i];
        }
    };
    public int uid = 0;
    public long wifiRxBytes = 0;
    public long wifiTxBytes = 0;
    public long mobileRxBytes = 0;
    public long mobileTxBytes = 0;

    public NetworkStats() {
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final void readFromParcel(Parcel parcel) {
        this.uid = parcel.readInt();
        this.wifiRxBytes = parcel.readLong();
        this.wifiTxBytes = parcel.readLong();
        this.mobileRxBytes = parcel.readLong();
        this.mobileTxBytes = parcel.readLong();
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.uid);
        parcel.writeLong(this.wifiRxBytes);
        parcel.writeLong(this.wifiTxBytes);
        parcel.writeLong(this.mobileRxBytes);
        parcel.writeLong(this.mobileTxBytes);
    }

    public NetworkStats(Parcel parcel) {
        readFromParcel(parcel);
    }
}
