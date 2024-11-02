package android.blockchain;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public class BlockchainTZServiceCommnInfo implements Parcelable {
    public static final Parcelable.Creator<BlockchainTZServiceCommnInfo> CREATOR = new Parcelable.Creator<BlockchainTZServiceCommnInfo>() { // from class: android.blockchain.BlockchainTZServiceCommnInfo.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public BlockchainTZServiceCommnInfo createFromParcel(Parcel in) {
            return new BlockchainTZServiceCommnInfo(in);
        }

        @Override // android.os.Parcelable.Creator
        public BlockchainTZServiceCommnInfo[] newArray(int size) {
            return new BlockchainTZServiceCommnInfo[size];
        }
    };
    public int mServiceVersion;
    public Map<Integer, IBinder> mTAs;

    /* synthetic */ BlockchainTZServiceCommnInfo(Parcel parcel, BlockchainTZServiceCommnInfoIA blockchainTZServiceCommnInfoIA) {
        this(parcel);
    }

    public BlockchainTZServiceCommnInfo() {
        this.mTAs = new HashMap();
    }

    /* renamed from: android.blockchain.BlockchainTZServiceCommnInfo$1 */
    /* loaded from: classes.dex */
    class AnonymousClass1 implements Parcelable.Creator<BlockchainTZServiceCommnInfo> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public BlockchainTZServiceCommnInfo createFromParcel(Parcel in) {
            return new BlockchainTZServiceCommnInfo(in);
        }

        @Override // android.os.Parcelable.Creator
        public BlockchainTZServiceCommnInfo[] newArray(int size) {
            return new BlockchainTZServiceCommnInfo[size];
        }
    }

    private BlockchainTZServiceCommnInfo(Parcel in) {
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
