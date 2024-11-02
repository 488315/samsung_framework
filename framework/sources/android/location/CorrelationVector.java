package android.location;

import android.annotation.SystemApi;
import android.hardware.scontext.SContextConstants;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.Preconditions;
import java.util.Arrays;
import java.util.Objects;

@SystemApi
/* loaded from: classes2.dex */
public final class CorrelationVector implements Parcelable {
    public static final Parcelable.Creator<CorrelationVector> CREATOR = new Parcelable.Creator<CorrelationVector>() { // from class: android.location.CorrelationVector.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public CorrelationVector createFromParcel(Parcel parcel) {
            return new CorrelationVector(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public CorrelationVector[] newArray(int size) {
            return new CorrelationVector[size];
        }
    };
    private final double mFrequencyOffsetMetersPerSecond;
    private final int[] mMagnitude;
    private final double mSamplingStartMeters;
    private final double mSamplingWidthMeters;

    /* synthetic */ CorrelationVector(Builder builder, CorrelationVectorIA correlationVectorIA) {
        this(builder);
    }

    /* synthetic */ CorrelationVector(Parcel parcel, CorrelationVectorIA correlationVectorIA) {
        this(parcel);
    }

    public double getSamplingWidthMeters() {
        return this.mSamplingWidthMeters;
    }

    public double getSamplingStartMeters() {
        return this.mSamplingStartMeters;
    }

    public double getFrequencyOffsetMetersPerSecond() {
        return this.mFrequencyOffsetMetersPerSecond;
    }

    public int[] getMagnitude() {
        return (int[]) this.mMagnitude.clone();
    }

    private CorrelationVector(Builder builder) {
        Preconditions.checkNotNull(builder.mMagnitude, "Magnitude array must not be null");
        Preconditions.checkArgumentPositive(builder.mMagnitude.length, "Magnitude array must have non-zero length");
        Preconditions.checkArgument(builder.mFrequencyOffsetMetersPerSecond >= SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, "FrequencyOffsetMetersPerSecond must be non-negative (greater than or equal to 0)");
        Preconditions.checkArgument(builder.mSamplingWidthMeters > SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, "SamplingWidthMeters must be positive (greater than 0)");
        this.mMagnitude = builder.mMagnitude;
        this.mFrequencyOffsetMetersPerSecond = builder.mFrequencyOffsetMetersPerSecond;
        this.mSamplingWidthMeters = builder.mSamplingWidthMeters;
        this.mSamplingStartMeters = builder.mSamplingStartMeters;
    }

    private CorrelationVector(Parcel in) {
        this.mSamplingWidthMeters = in.readDouble();
        this.mSamplingStartMeters = in.readDouble();
        this.mFrequencyOffsetMetersPerSecond = in.readDouble();
        int[] iArr = new int[in.readInt()];
        this.mMagnitude = iArr;
        in.readIntArray(iArr);
    }

    /* renamed from: android.location.CorrelationVector$1 */
    /* loaded from: classes2.dex */
    class AnonymousClass1 implements Parcelable.Creator<CorrelationVector> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public CorrelationVector createFromParcel(Parcel parcel) {
            return new CorrelationVector(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public CorrelationVector[] newArray(int size) {
            return new CorrelationVector[size];
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String toString() {
        return "CorrelationVector{FrequencyOffsetMetersPerSecond=" + this.mFrequencyOffsetMetersPerSecond + ", SamplingWidthMeters=" + this.mSamplingWidthMeters + ", SamplingStartMeters=" + this.mSamplingStartMeters + ", Magnitude=" + Arrays.toString(this.mMagnitude) + '}';
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.mSamplingWidthMeters);
        dest.writeDouble(this.mSamplingStartMeters);
        dest.writeDouble(this.mFrequencyOffsetMetersPerSecond);
        dest.writeInt(this.mMagnitude.length);
        dest.writeIntArray(this.mMagnitude);
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof CorrelationVector)) {
            return false;
        }
        CorrelationVector c = (CorrelationVector) object;
        return Arrays.equals(this.mMagnitude, c.getMagnitude()) && Double.compare(this.mSamplingWidthMeters, c.getSamplingWidthMeters()) == 0 && Double.compare(this.mSamplingStartMeters, c.getSamplingStartMeters()) == 0 && Double.compare(this.mFrequencyOffsetMetersPerSecond, c.getFrequencyOffsetMetersPerSecond()) == 0;
    }

    public int hashCode() {
        return Objects.hash(Double.valueOf(this.mSamplingWidthMeters), Double.valueOf(this.mSamplingStartMeters), Double.valueOf(this.mFrequencyOffsetMetersPerSecond), Integer.valueOf(Arrays.hashCode(this.mMagnitude)));
    }

    /* loaded from: classes2.dex */
    public static final class Builder {
        private double mFrequencyOffsetMetersPerSecond;
        private int[] mMagnitude;
        private double mSamplingStartMeters;
        private double mSamplingWidthMeters;

        public Builder setSamplingWidthMeters(double samplingWidthMeters) {
            this.mSamplingWidthMeters = samplingWidthMeters;
            return this;
        }

        public Builder setSamplingStartMeters(double samplingStartMeters) {
            this.mSamplingStartMeters = samplingStartMeters;
            return this;
        }

        public Builder setFrequencyOffsetMetersPerSecond(double frequencyOffsetMetersPerSecond) {
            this.mFrequencyOffsetMetersPerSecond = frequencyOffsetMetersPerSecond;
            return this;
        }

        public Builder setMagnitude(int[] magnitude) {
            this.mMagnitude = magnitude;
            return this;
        }

        public CorrelationVector build() {
            return new CorrelationVector(this);
        }
    }
}
