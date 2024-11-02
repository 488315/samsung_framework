package android.spay;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes3.dex */
public class PaymentTZServiceCommnInfo implements Parcelable {
    public static final Parcelable.Creator<PaymentTZServiceCommnInfo> CREATOR = new Parcelable.Creator<PaymentTZServiceCommnInfo>() { // from class: android.spay.PaymentTZServiceCommnInfo.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public PaymentTZServiceCommnInfo createFromParcel(Parcel in) {
            return new PaymentTZServiceCommnInfo(in);
        }

        @Override // android.os.Parcelable.Creator
        public PaymentTZServiceCommnInfo[] newArray(int size) {
            return new PaymentTZServiceCommnInfo[size];
        }
    };
    public int mServiceVersion;
    public Map<Integer, IBinder> mTAs;

    /* synthetic */ PaymentTZServiceCommnInfo(Parcel parcel, PaymentTZServiceCommnInfoIA paymentTZServiceCommnInfoIA) {
        this(parcel);
    }

    public PaymentTZServiceCommnInfo() {
        this.mTAs = new HashMap();
    }

    /* renamed from: android.spay.PaymentTZServiceCommnInfo$1 */
    /* loaded from: classes3.dex */
    class AnonymousClass1 implements Parcelable.Creator<PaymentTZServiceCommnInfo> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public PaymentTZServiceCommnInfo createFromParcel(Parcel in) {
            return new PaymentTZServiceCommnInfo(in);
        }

        @Override // android.os.Parcelable.Creator
        public PaymentTZServiceCommnInfo[] newArray(int size) {
            return new PaymentTZServiceCommnInfo[size];
        }
    }

    private PaymentTZServiceCommnInfo(Parcel in) {
        this.mTAs = new HashMap();
        readFromParcel(in);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flag) {
        out.writeInt(this.mServiceVersion);
        out.writeInt(this.mTAs.size());
        for (Integer s : this.mTAs.keySet()) {
            out.writeInt(s.intValue());
            out.writeStrongBinder(this.mTAs.get(s));
        }
    }

    public void readFromParcel(Parcel in) {
        this.mServiceVersion = in.readInt();
        int count = in.readInt();
        for (int i = 0; i < count; i++) {
            this.mTAs.put(Integer.valueOf(in.readInt()), in.readStrongBinder());
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
