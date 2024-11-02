package android.os;

import android.os.Parcelable;

/* loaded from: classes3.dex */
public class CcuStatsInfo implements Parcelable {
    public static final Parcelable.Creator<CcuStatsInfo> CREATOR = new Parcelable.Creator<CcuStatsInfo>() { // from class: android.os.CcuStatsInfo.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public CcuStatsInfo createFromParcel(Parcel in) {
            return new CcuStatsInfo(in);
        }

        @Override // android.os.Parcelable.Creator
        public CcuStatsInfo[] newArray(int size) {
            return new CcuStatsInfo[size];
        }
    };
    public long[] cpuFreqTimeMs;
    public int uid;

    public CcuStatsInfo() {
    }

    public CcuStatsInfo(Parcel in) {
        this.uid = in.readInt();
        this.cpuFreqTimeMs = in.createLongArray();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.uid);
        dest.writeLongArray(this.cpuFreqTimeMs);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    /* renamed from: android.os.CcuStatsInfo$1 */
    /* loaded from: classes3.dex */
    class AnonymousClass1 implements Parcelable.Creator<CcuStatsInfo> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public CcuStatsInfo createFromParcel(Parcel in) {
            return new CcuStatsInfo(in);
        }

        @Override // android.os.Parcelable.Creator
        public CcuStatsInfo[] newArray(int size) {
            return new CcuStatsInfo[size];
        }
    }
}
