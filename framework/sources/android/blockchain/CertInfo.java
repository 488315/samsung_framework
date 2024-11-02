package android.blockchain;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public class CertInfo implements Parcelable {
    public static final Parcelable.Creator<CertInfo> CREATOR = new Parcelable.Creator<CertInfo>() { // from class: android.blockchain.CertInfo.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public CertInfo createFromParcel(Parcel in) {
            return new CertInfo(in);
        }

        @Override // android.os.Parcelable.Creator
        public CertInfo[] newArray(int size) {
            return new CertInfo[size];
        }
    };
    private static final String TAG = "CertInfo";
    public Map<String, byte[]> mCerts;

    /* synthetic */ CertInfo(Parcel parcel, CertInfoIA certInfoIA) {
        this(parcel);
    }

    public CertInfo() {
        this.mCerts = new HashMap();
    }

    /* renamed from: android.blockchain.CertInfo$1 */
    /* loaded from: classes.dex */
    class AnonymousClass1 implements Parcelable.Creator<CertInfo> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public CertInfo createFromParcel(Parcel in) {
            return new CertInfo(in);
        }

        @Override // android.os.Parcelable.Creator
        public CertInfo[] newArray(int size) {
            return new CertInfo[size];
        }
    }

    private CertInfo(Parcel in) {
        this.mCerts = new HashMap();
        readFromParcel(in);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flag) {
        Log.d(TAG, "Writing Certificates = " + this.mCerts.size());
        out.writeInt(this.mCerts.size());
        for (String s : this.mCerts.keySet()) {
            Log.d(TAG, "Certificate = " + s);
            out.writeString(s);
            byte[] certdata = this.mCerts.get(s);
            Log.d(TAG, "certdata = " + Arrays.toString(certdata));
            if (certdata == null || certdata.length == 0) {
                out.writeInt(0);
            } else {
                out.writeInt(certdata.length);
                out.writeByteArray(certdata);
            }
        }
    }

    public void readFromParcel(Parcel in) {
        int count = in.readInt();
        Log.d(TAG, "Reading Certificates = " + count);
        for (int i = 0; i < count; i++) {
            String name = in.readString();
            Log.d(TAG, "Reading Certificate = " + name);
            int certdatalen = in.readInt();
            Log.d(TAG, "Reading Certificate Len = " + certdatalen);
            if (certdatalen == 0) {
                this.mCerts.put(name, null);
            } else {
                byte[] certdata = new byte[certdatalen];
                in.readByteArray(certdata);
                this.mCerts.put(name, certdata);
                Log.d(TAG, "certdata = " + Arrays.toString(certdata));
            }
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
