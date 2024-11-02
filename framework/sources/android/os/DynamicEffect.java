package android.os;

import android.os.Parcelable;

/* loaded from: classes3.dex */
public final class DynamicEffect extends VibrationEffect implements Parcelable {
    public static final Parcelable.Creator<DynamicEffect> CREATOR = new Parcelable.Creator<DynamicEffect>() { // from class: android.os.DynamicEffect.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public DynamicEffect createFromParcel(Parcel in) {
            return new DynamicEffect(in);
        }

        @Override // android.os.Parcelable.Creator
        public DynamicEffect[] newArray(int size) {
            return new DynamicEffect[size];
        }
    };
    private String mJson;

    public static DynamicEffect create(String json) {
        return new DynamicEffect(json);
    }

    DynamicEffect(Parcel in) {
    }

    public DynamicEffect(String json) {
        this.mJson = json;
    }

    public String getEffectInfo() {
        return this.mJson;
    }

    @Override // android.os.VibrationEffect
    public void validate() {
    }

    @Override // android.os.VibrationEffect
    public long getDuration() {
        return 0L;
    }

    @Override // android.os.VibrationEffect
    public DynamicEffect resolve(int defaultAmplitude) {
        return this;
    }

    @Override // android.os.VibrationEffect
    public DynamicEffect scale(float scaleFactor) {
        return this;
    }

    public boolean equals(Object o) {
        if (!(o instanceof DynamicEffect)) {
            return false;
        }
        return ((DynamicEffect) o).mJson.equals(this.mJson);
    }

    public int hashCode() {
        return this.mJson.hashCode();
    }

    public String toString() {
        return "DynamicEffect{mJson=" + this.mJson + "}";
    }

    @Override // android.os.VibrationEffect
    public long[] computeCreateWaveformOffOnTimingsOrNull() {
        return new long[0];
    }

    @Override // android.os.VibrationEffect, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
    }

    @Override // android.os.VibrationEffect
    public boolean areVibrationFeaturesSupported(Vibrator vibrator) {
        return true;
    }

    /* renamed from: android.os.DynamicEffect$1 */
    /* loaded from: classes3.dex */
    class AnonymousClass1 implements Parcelable.Creator<DynamicEffect> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public DynamicEffect createFromParcel(Parcel in) {
            return new DynamicEffect(in);
        }

        @Override // android.os.Parcelable.Creator
        public DynamicEffect[] newArray(int size) {
            return new DynamicEffect[size];
        }
    }
}
