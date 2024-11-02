package com.samsung.android.knox.util;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public class SemCertByte implements Parcelable {
    public static final Parcelable.Creator<SemCertByte> CREATOR = new Parcelable.Creator<SemCertByte>() { // from class: com.samsung.android.knox.util.SemCertByte.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public SemCertByte createFromParcel(Parcel source) {
            return new SemCertByte(source);
        }

        @Override // android.os.Parcelable.Creator
        public SemCertByte[] newArray(int size) {
            return null;
        }
    };
    public byte[] caCertBytes;
    public int caSize;
    public byte[] certBytes;
    public int certsize;

    public SemCertByte() {
    }

    public SemCertByte(Parcel source) {
        int readInt = source.readInt();
        this.certsize = readInt;
        byte[] bArr = new byte[readInt];
        this.certBytes = bArr;
        readByteArray(source, bArr);
        int readInt2 = source.readInt();
        this.caSize = readInt2;
        byte[] bArr2 = new byte[readInt2];
        this.caCertBytes = bArr2;
        readByteArray(source, bArr2);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.certsize);
        dest.writeByteArray(this.certBytes);
        dest.writeInt(this.caSize);
        dest.writeByteArray(this.caCertBytes);
    }

    private final void readByteArray(Parcel parcel, byte[] val) {
        byte[] ba = parcel.createByteArray();
        if (ba.length == val.length) {
            System.arraycopy(ba, 0, val, 0, ba.length);
            return;
        }
        throw new RuntimeException("bad array lengths");
    }

    /* renamed from: com.samsung.android.knox.util.SemCertByte$1 */
    /* loaded from: classes5.dex */
    class AnonymousClass1 implements Parcelable.Creator<SemCertByte> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public SemCertByte createFromParcel(Parcel source) {
            return new SemCertByte(source);
        }

        @Override // android.os.Parcelable.Creator
        public SemCertByte[] newArray(int size) {
            return null;
        }
    }
}
