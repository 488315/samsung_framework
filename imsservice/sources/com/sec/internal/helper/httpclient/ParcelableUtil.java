package com.sec.internal.helper.httpclient;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public class ParcelableUtil {
    public static byte[] marshall(Parcelable parcelable) {
        Parcel obtain = Parcel.obtain();
        parcelable.writeToParcel(obtain, 0);
        byte[] marshall = obtain.marshall();
        obtain.recycle();
        return marshall;
    }

    public static <T extends Parcelable> T unmarshall(byte[] bArr, Parcelable.Creator<T> creator) {
        Parcel unmarshall = unmarshall(bArr);
        T createFromParcel = creator.createFromParcel(unmarshall);
        unmarshall.recycle();
        return createFromParcel;
    }

    public static Parcel unmarshall(byte[] bArr) {
        Parcel obtain = Parcel.obtain();
        obtain.unmarshall(bArr, 0, bArr.length);
        obtain.setDataPosition(0);
        return obtain;
    }
}
