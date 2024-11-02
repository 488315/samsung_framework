package android.nfc;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public final class AvailableNfcAntenna implements Parcelable {
    public static final Parcelable.Creator<AvailableNfcAntenna> CREATOR = new Parcelable.Creator<AvailableNfcAntenna>() { // from class: android.nfc.AvailableNfcAntenna.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public AvailableNfcAntenna createFromParcel(Parcel in) {
            return new AvailableNfcAntenna(in);
        }

        @Override // android.os.Parcelable.Creator
        public AvailableNfcAntenna[] newArray(int size) {
            return new AvailableNfcAntenna[size];
        }
    };
    private final int mLocationX;
    private final int mLocationY;

    /* synthetic */ AvailableNfcAntenna(Parcel parcel, AvailableNfcAntennaIA availableNfcAntennaIA) {
        this(parcel);
    }

    public AvailableNfcAntenna(int locationX, int locationY) {
        this.mLocationX = locationX;
        this.mLocationY = locationY;
    }

    public int getLocationX() {
        return this.mLocationX;
    }

    public int getLocationY() {
        return this.mLocationY;
    }

    private AvailableNfcAntenna(Parcel in) {
        this.mLocationX = in.readInt();
        this.mLocationY = in.readInt();
    }

    /* renamed from: android.nfc.AvailableNfcAntenna$1 */
    /* loaded from: classes3.dex */
    class AnonymousClass1 implements Parcelable.Creator<AvailableNfcAntenna> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public AvailableNfcAntenna createFromParcel(Parcel in) {
            return new AvailableNfcAntenna(in);
        }

        @Override // android.os.Parcelable.Creator
        public AvailableNfcAntenna[] newArray(int size) {
            return new AvailableNfcAntenna[size];
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mLocationX);
        dest.writeInt(this.mLocationY);
    }

    public int hashCode() {
        int result = (1 * 31) + this.mLocationX;
        return (result * 31) + this.mLocationY;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        AvailableNfcAntenna other = (AvailableNfcAntenna) obj;
        if (this.mLocationX == other.mLocationX && this.mLocationY == other.mLocationY) {
            return true;
        }
        return false;
    }

    public String toString() {
        return "AvailableNfcAntenna x: " + this.mLocationX + " y: " + this.mLocationY;
    }
}
