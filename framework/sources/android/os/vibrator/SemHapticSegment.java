package android.os.vibrator;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Vibrator;
import com.samsung.android.vibrator.SemHapticFeedbackConstants;

/* loaded from: classes3.dex */
public final class SemHapticSegment extends VibrationEffectSegment {
    public static final Parcelable.Creator<SemHapticSegment> CREATOR = new Parcelable.Creator<SemHapticSegment>() { // from class: android.os.vibrator.SemHapticSegment.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public SemHapticSegment createFromParcel(Parcel in) {
            in.readInt();
            return new SemHapticSegment(in);
        }

        @Override // android.os.Parcelable.Creator
        public SemHapticSegment[] newArray(int size) {
            return new SemHapticSegment[size];
        }
    };
    private int mMagnitude;
    private int mType;

    public SemHapticSegment(Parcel in) {
        this(in.readInt(), in.readInt());
    }

    public SemHapticSegment(int type) {
        this.mMagnitude = -1;
        this.mType = type;
    }

    public SemHapticSegment(int type, int effectStrength) {
        this.mMagnitude = -1;
        this.mType = type;
        this.mMagnitude = effectStrength;
    }

    public int getType() {
        return this.mType;
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public long getDuration() {
        return 5000L;
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public boolean isHapticFeedbackCandidate() {
        return true;
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public boolean hasNonZeroAmplitude() {
        return true;
    }

    public boolean equals(Object o) {
        if (!(o instanceof SemHapticSegment)) {
            return false;
        }
        SemHapticSegment other = (SemHapticSegment) o;
        return this.mType == other.mType && this.mMagnitude == other.mMagnitude;
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public void validate() {
        if (!SemHapticFeedbackConstants.isValidatedVibeIndex(this.mType)) {
            throw new IllegalArgumentException("invalid haptic type=" + this.mType);
        }
    }

    public int hashCode() {
        return super.hashCode();
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public SemHapticSegment resolve(int defaultAmplitude) {
        return this;
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public SemHapticSegment scale(float scaleFactor) {
        return this;
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public SemHapticSegment applyEffectStrength(int effectStrength) {
        if (isValidEffectStrength(effectStrength)) {
            return new SemHapticSegment(this.mType, effectStrength);
        }
        return this;
    }

    private boolean isValidEffectStrength(int strength) {
        return strength >= 0 && strength <= 10000;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(5);
        dest.writeInt(this.mType);
        dest.writeInt(this.mMagnitude);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String toString() {
        return "SemHaptic{mType=" + this.mType + ", mMagnitude=" + this.mMagnitude + "}";
    }

    /* renamed from: android.os.vibrator.SemHapticSegment$1 */
    /* loaded from: classes3.dex */
    class AnonymousClass1 implements Parcelable.Creator<SemHapticSegment> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public SemHapticSegment createFromParcel(Parcel in) {
            in.readInt();
            return new SemHapticSegment(in);
        }

        @Override // android.os.Parcelable.Creator
        public SemHapticSegment[] newArray(int size) {
            return new SemHapticSegment[size];
        }
    }

    public int getSepIndex() {
        return this.mType - SemHapticFeedbackConstants.INTERNAL_INDEX_OFFSET;
    }

    public int getMagnitude() {
        return this.mMagnitude;
    }

    public String getCategoryPath() {
        return SemHapticFeedbackConstants.getCategoryPath(this.mType);
    }

    public int getDefaultSepIndex() {
        return SemHapticFeedbackConstants.getDefaultSepIndex(this.mType);
    }

    public boolean isCustomIndexValid() {
        return SemHapticFeedbackConstants.isCustomIndexValid(this.mType);
    }

    public boolean isRamIndexValid() {
        return SemHapticFeedbackConstants.isRamIndexValid(this.mType);
    }

    public boolean isResourceIndexValid() {
        return SemHapticFeedbackConstants.isResourceIndexValid(this.mType);
    }

    public boolean isHybridIndexValid() {
        return SemHapticFeedbackConstants.isHybridIndexValid(this.mType);
    }

    public boolean isEffectClickReservedDC() {
        return this.mType == 50124;
    }

    public boolean isEffectSilent() {
        return this.mType == 50084;
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public boolean areVibrationFeaturesSupported(Vibrator vibrator) {
        return true;
    }
}
