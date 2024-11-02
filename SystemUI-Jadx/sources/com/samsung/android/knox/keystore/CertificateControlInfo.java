package com.samsung.android.knox.keystore;

import android.os.Parcel;
import android.os.Parcelable;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes3.dex */
public final class CertificateControlInfo implements Parcelable {
    public static final Parcelable.Creator<CertificateControlInfo> CREATOR = new Parcelable.Creator<CertificateControlInfo>() { // from class: com.samsung.android.knox.keystore.CertificateControlInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final CertificateControlInfo[] newArray(int i) {
            return new CertificateControlInfo[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final CertificateControlInfo createFromParcel(Parcel parcel) {
            return new CertificateControlInfo(parcel, 0);
        }

        @Override // android.os.Parcelable.Creator
        public final CertificateControlInfo[] newArray(int i) {
            return new CertificateControlInfo[i];
        }
    };
    public String adminPackageName;
    public List<X509Certificate> entries;

    public /* synthetic */ CertificateControlInfo(Parcel parcel, int i) {
        this(parcel);
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final void readFromParcel(Parcel parcel) {
        this.adminPackageName = parcel.readString();
        int readInt = parcel.readInt();
        this.entries = new ArrayList();
        for (int i = 0; i < readInt; i++) {
            this.entries.add((X509Certificate) parcel.readSerializable());
        }
    }

    public final String toString() {
        return "adminPackageName: " + this.adminPackageName + " ,entries: " + this.entries;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.adminPackageName);
        List<X509Certificate> list = this.entries;
        if (list != null) {
            int size = list.size();
            parcel.writeInt(size);
            for (int i2 = 0; i2 < size; i2++) {
                parcel.writeSerializable(this.entries.get(i2));
            }
            return;
        }
        parcel.writeInt(0);
    }

    public CertificateControlInfo() {
        this.adminPackageName = null;
        this.entries = null;
    }

    private CertificateControlInfo(Parcel parcel) {
        this.adminPackageName = null;
        this.entries = null;
        readFromParcel(parcel);
    }
}
