package android.app.admin;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public final class FlagUnion extends ResolutionMechanism<Integer> {
    public static final FlagUnion FLAG_UNION = new FlagUnion();
    public static final Parcelable.Creator<FlagUnion> CREATOR = new Parcelable.Creator<FlagUnion>() { // from class: android.app.admin.FlagUnion.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FlagUnion createFromParcel(Parcel source) {
            return FlagUnion.FLAG_UNION;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FlagUnion[] newArray(int size) {
            return new FlagUnion[size];
        }
    };

    private FlagUnion() {
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        return o != null && getClass() == o.getClass();
    }

    public int hashCode() {
        return 0;
    }

    public String toString() {
        return "FlagUnion {}";
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
    }
}
