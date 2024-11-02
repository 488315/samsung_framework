package android.os;

import android.os.Parcelable;

/* loaded from: classes3.dex */
public final class CarrierAssociatedAppEntry implements Parcelable {
    public static final Parcelable.Creator<CarrierAssociatedAppEntry> CREATOR = new Parcelable.Creator<CarrierAssociatedAppEntry>() { // from class: android.os.CarrierAssociatedAppEntry.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public CarrierAssociatedAppEntry createFromParcel(Parcel source) {
            return new CarrierAssociatedAppEntry(source);
        }

        @Override // android.os.Parcelable.Creator
        public CarrierAssociatedAppEntry[] newArray(int size) {
            return new CarrierAssociatedAppEntry[size];
        }
    };
    public static final int SDK_UNSPECIFIED = -1;
    public final int addedInSdk;
    public final String packageName;

    public CarrierAssociatedAppEntry(String packageName, int addedInSdk) {
        this.packageName = packageName;
        this.addedInSdk = addedInSdk;
    }

    public CarrierAssociatedAppEntry(Parcel in) {
        this.packageName = in.readString();
        this.addedInSdk = in.readInt();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.packageName);
        dest.writeInt(this.addedInSdk);
    }

    /* renamed from: android.os.CarrierAssociatedAppEntry$1 */
    /* loaded from: classes3.dex */
    class AnonymousClass1 implements Parcelable.Creator<CarrierAssociatedAppEntry> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public CarrierAssociatedAppEntry createFromParcel(Parcel source) {
            return new CarrierAssociatedAppEntry(source);
        }

        @Override // android.os.Parcelable.Creator
        public CarrierAssociatedAppEntry[] newArray(int size) {
            return new CarrierAssociatedAppEntry[size];
        }
    }
}
