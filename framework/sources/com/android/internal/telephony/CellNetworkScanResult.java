package com.android.internal.telephony;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class CellNetworkScanResult implements Parcelable {
    public static final Parcelable.Creator<CellNetworkScanResult> CREATOR = new Parcelable.Creator<CellNetworkScanResult>() { // from class: com.android.internal.telephony.CellNetworkScanResult.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public CellNetworkScanResult createFromParcel(Parcel in) {
            return new CellNetworkScanResult(in);
        }

        @Override // android.os.Parcelable.Creator
        public CellNetworkScanResult[] newArray(int size) {
            return new CellNetworkScanResult[size];
        }
    };
    public static final int STATUS_RADIO_GENERIC_FAILURE = 3;
    public static final int STATUS_RADIO_NOT_AVAILABLE = 2;
    public static final int STATUS_SUCCESS = 1;
    public static final int STATUS_UNKNOWN_ERROR = 4;
    private final List<OperatorInfo> mOperators;
    private final int mStatus;

    /* synthetic */ CellNetworkScanResult(Parcel parcel, CellNetworkScanResultIA cellNetworkScanResultIA) {
        this(parcel);
    }

    public CellNetworkScanResult(int status, List<OperatorInfo> operators) {
        this.mStatus = status;
        this.mOperators = operators;
    }

    private CellNetworkScanResult(Parcel in) {
        this.mStatus = in.readInt();
        int len = in.readInt();
        if (len > 0) {
            this.mOperators = new ArrayList();
            for (int i = 0; i < len; i++) {
                this.mOperators.add(OperatorInfo.CREATOR.createFromParcel(in));
            }
            return;
        }
        this.mOperators = null;
    }

    public int getStatus() {
        return this.mStatus;
    }

    public List<OperatorInfo> getOperators() {
        return this.mOperators;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.mStatus);
        List<OperatorInfo> list = this.mOperators;
        if (list != null && list.size() > 0) {
            out.writeInt(this.mOperators.size());
            for (OperatorInfo network : this.mOperators) {
                network.writeToParcel(out, flags);
            }
            return;
        }
        out.writeInt(0);
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("CellNetworkScanResult: {");
        sb.append(" status:").append(this.mStatus);
        List<OperatorInfo> list = this.mOperators;
        if (list != null) {
            for (OperatorInfo network : list) {
                sb.append(" network:").append(network);
            }
        }
        sb.append("}");
        return sb.toString();
    }

    /* renamed from: com.android.internal.telephony.CellNetworkScanResult$1 */
    /* loaded from: classes5.dex */
    class AnonymousClass1 implements Parcelable.Creator<CellNetworkScanResult> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public CellNetworkScanResult createFromParcel(Parcel in) {
            return new CellNetworkScanResult(in);
        }

        @Override // android.os.Parcelable.Creator
        public CellNetworkScanResult[] newArray(int size) {
            return new CellNetworkScanResult[size];
        }
    }
}
