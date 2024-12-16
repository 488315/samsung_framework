package android.telephony.ims.feature;

import android.os.Parcel;
import android.os.Parcelable;
import android.security.keystore.KeyProperties;
import android.util.SparseArray;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes4.dex */
public final class ConnectionFailureInfo implements Parcelable {
    public static final Parcelable.Creator<ConnectionFailureInfo> CREATOR;
    public static final int REASON_ACCESS_DENIED = 1;
    public static final int REASON_NAS_FAILURE = 2;
    public static final int REASON_NONE = 0;
    public static final int REASON_NO_SERVICE = 7;
    public static final int REASON_PDN_NOT_AVAILABLE = 8;
    public static final int REASON_RACH_FAILURE = 3;
    public static final int REASON_RF_BUSY = 9;
    public static final int REASON_RLC_FAILURE = 4;
    public static final int REASON_RRC_REJECT = 5;
    public static final int REASON_RRC_TIMEOUT = 6;
    public static final int REASON_UNSPECIFIED = 65535;
    private static final SparseArray<String> sReasonMap = new SparseArray<>();
    private final int mCauseCode;
    private final int mReason;
    private final int mWaitTimeMillis;

    @Retention(RetentionPolicy.SOURCE)
    public @interface FailureReason {
    }

    static {
        sReasonMap.set(0, KeyProperties.DIGEST_NONE);
        sReasonMap.set(1, "ACCESS_DENIED");
        sReasonMap.set(2, "NAS_FAILURE");
        sReasonMap.set(3, "RACH_FAILURE");
        sReasonMap.set(4, "RLC_FAILURE");
        sReasonMap.set(5, "RRC_REJECT");
        sReasonMap.set(6, "RRC_TIMEOUT");
        sReasonMap.set(7, "NO_SERVICE");
        sReasonMap.set(8, "PDN_NOT_AVAILABLE");
        sReasonMap.set(9, "RF_BUSY");
        sReasonMap.set(65535, "UNSPECIFIED");
        CREATOR = new Parcelable.Creator<ConnectionFailureInfo>() { // from class: android.telephony.ims.feature.ConnectionFailureInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public ConnectionFailureInfo createFromParcel(Parcel in) {
                return new ConnectionFailureInfo(in);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public ConnectionFailureInfo[] newArray(int size) {
                return new ConnectionFailureInfo[size];
            }
        };
    }

    private ConnectionFailureInfo(Parcel in) {
        this.mReason = in.readInt();
        this.mCauseCode = in.readInt();
        this.mWaitTimeMillis = in.readInt();
    }

    public ConnectionFailureInfo(int reason, int causeCode, int waitTimeMillis) {
        this.mReason = reason;
        this.mCauseCode = causeCode;
        this.mWaitTimeMillis = waitTimeMillis;
    }

    public int getReason() {
        return this.mReason;
    }

    public int getCauseCode() {
        return this.mCauseCode;
    }

    public int getWaitTimeMillis() {
        return this.mWaitTimeMillis;
    }

    public String toString() {
        String reason = sReasonMap.get(this.mReason, "UNKNOWN");
        return "ConnectionFailureInfo :: {" + this.mReason + " : " + reason + ", " + this.mCauseCode + ", " + this.mWaitTimeMillis + "}";
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.mReason);
        out.writeInt(this.mCauseCode);
        out.writeInt(this.mWaitTimeMillis);
    }
}
