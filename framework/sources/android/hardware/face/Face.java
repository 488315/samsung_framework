package android.hardware.face;

import android.hardware.biometrics.BiometricAuthenticator;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public final class Face extends BiometricAuthenticator.Identifier {
    public static final Parcelable.Creator<Face> CREATOR = new Parcelable.Creator<Face>() { // from class: android.hardware.face.Face.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public Face createFromParcel(Parcel in) {
            return new Face(in);
        }

        @Override // android.os.Parcelable.Creator
        public Face[] newArray(int size) {
            return new Face[size];
        }
    };

    /* synthetic */ Face(Parcel parcel, FaceIA faceIA) {
        this(parcel);
    }

    public Face(CharSequence name, int faceId, long deviceId) {
        super(name, faceId, deviceId);
    }

    private Face(Parcel in) {
        super(in.readString(), in.readInt(), in.readLong());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(getName().toString());
        out.writeInt(getBiometricId());
        out.writeLong(getDeviceId());
    }

    /* renamed from: android.hardware.face.Face$1 */
    /* loaded from: classes2.dex */
    class AnonymousClass1 implements Parcelable.Creator<Face> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public Face createFromParcel(Parcel in) {
            return new Face(in);
        }

        @Override // android.os.Parcelable.Creator
        public Face[] newArray(int size) {
            return new Face[size];
        }
    }
}
