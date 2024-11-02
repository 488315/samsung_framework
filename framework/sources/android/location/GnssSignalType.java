package android.location;

import android.hardware.scontext.SContextConstants;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.Preconditions;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class GnssSignalType implements Parcelable {
    public static final Parcelable.Creator<GnssSignalType> CREATOR = new Parcelable.Creator<GnssSignalType>() { // from class: android.location.GnssSignalType.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public GnssSignalType createFromParcel(Parcel parcel) {
            return new GnssSignalType(parcel.readInt(), parcel.readDouble(), parcel.readString());
        }

        @Override // android.os.Parcelable.Creator
        public GnssSignalType[] newArray(int i) {
            return new GnssSignalType[i];
        }
    };
    private final double mCarrierFrequencyHz;
    private final String mCodeType;
    private final int mConstellationType;

    /* synthetic */ GnssSignalType(int i, double d, String str, GnssSignalTypeIA gnssSignalTypeIA) {
        this(i, d, str);
    }

    public static GnssSignalType create(int constellationType, double carrierFrequencyHz, String codeType) {
        Preconditions.checkArgument(carrierFrequencyHz > SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, "carrierFrequencyHz must be greater than 0.");
        Objects.requireNonNull(codeType);
        return new GnssSignalType(constellationType, carrierFrequencyHz, codeType);
    }

    private GnssSignalType(int constellationType, double carrierFrequencyHz, String codeType) {
        this.mConstellationType = constellationType;
        this.mCarrierFrequencyHz = carrierFrequencyHz;
        this.mCodeType = codeType;
    }

    public int getConstellationType() {
        return this.mConstellationType;
    }

    public double getCarrierFrequencyHz() {
        return this.mCarrierFrequencyHz;
    }

    public String getCodeType() {
        return this.mCodeType;
    }

    /* renamed from: android.location.GnssSignalType$1 */
    /* loaded from: classes2.dex */
    class AnonymousClass1 implements Parcelable.Creator<GnssSignalType> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public GnssSignalType createFromParcel(Parcel parcel) {
            return new GnssSignalType(parcel.readInt(), parcel.readDouble(), parcel.readString());
        }

        @Override // android.os.Parcelable.Creator
        public GnssSignalType[] newArray(int i) {
            return new GnssSignalType[i];
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(this.mConstellationType);
        parcel.writeDouble(this.mCarrierFrequencyHz);
        parcel.writeString(this.mCodeType);
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("GnssSignalType[");
        s.append("Constellation=").append(this.mConstellationType);
        s.append(", CarrierFrequencyHz=").append(this.mCarrierFrequencyHz);
        s.append(", CodeType=").append(this.mCodeType);
        s.append(']');
        return s.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof GnssSignalType)) {
            return false;
        }
        GnssSignalType other = (GnssSignalType) obj;
        if (this.mConstellationType == other.mConstellationType && Double.compare(this.mCarrierFrequencyHz, other.mCarrierFrequencyHz) == 0 && this.mCodeType.equals(other.mCodeType)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mConstellationType), Double.valueOf(this.mCarrierFrequencyHz), this.mCodeType);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
