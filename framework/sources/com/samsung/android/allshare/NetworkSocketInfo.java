package com.samsung.android.allshare;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: IAppControlAPI.java */
/* loaded from: classes5.dex */
public class NetworkSocketInfo implements Parcelable {
    public static final Parcelable.Creator<NetworkSocketInfo> CREATOR = new Parcelable.Creator<NetworkSocketInfo>() { // from class: com.samsung.android.allshare.NetworkSocketInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NetworkSocketInfo createFromParcel(Parcel source) {
            NetworkSocketInfo netinfo = new NetworkSocketInfo();
            netinfo.mProtocol = source.readInt();
            netinfo.mIpAddress = source.readString();
            netinfo.mPort = source.readInt();
            netinfo.mDeviceClass = source.readString();
            netinfo.mMacAddr = source.readString();
            return netinfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NetworkSocketInfo[] newArray(int arg0) {
            return new NetworkSocketInfo[arg0];
        }
    };
    public String mDeviceClass;
    public String mIpAddress;
    public String mMacAddr;
    public int mPort;
    public int mProtocol;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel arg0, int arg1) {
        arg0.writeInt(this.mProtocol);
        arg0.writeString(this.mIpAddress);
        arg0.writeInt(this.mPort);
        arg0.writeString(this.mDeviceClass);
        arg0.writeString(this.mMacAddr);
    }
}
