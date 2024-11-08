package android.hardware.biometrics;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public enum BiometricSourceType implements Parcelable {
    FINGERPRINT,
    FACE,
    IRIS;

    public static final Parcelable.Creator<BiometricSourceType> CREATOR = new Parcelable.Creator<BiometricSourceType>() { // from class: android.hardware.biometrics.BiometricSourceType.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public BiometricSourceType createFromParcel(Parcel source) {
            return BiometricSourceType.valueOf(source.readString());
        }

        @Override // android.os.Parcelable.Creator
        public BiometricSourceType[] newArray(int size) {
            return new BiometricSourceType[size];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name());
    }

    /* renamed from: android.hardware.biometrics.BiometricSourceType$1 */
    /* loaded from: classes.dex */
    class AnonymousClass1 implements Parcelable.Creator<BiometricSourceType> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public BiometricSourceType createFromParcel(Parcel source) {
            return BiometricSourceType.valueOf(source.readString());
        }

        @Override // android.os.Parcelable.Creator
        public BiometricSourceType[] newArray(int size) {
            return new BiometricSourceType[size];
        }
    }
}
