package android.hardware.face;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public final class FaceAuthenticationFrame implements Parcelable {
    public static final Parcelable.Creator<FaceAuthenticationFrame> CREATOR = new Parcelable.Creator<FaceAuthenticationFrame>() { // from class: android.hardware.face.FaceAuthenticationFrame.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FaceAuthenticationFrame createFromParcel(Parcel source) {
            return new FaceAuthenticationFrame(source);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FaceAuthenticationFrame[] newArray(int size) {
            return new FaceAuthenticationFrame[size];
        }
    };
    private final FaceDataFrame mData;

    public FaceAuthenticationFrame(FaceDataFrame data) {
        this.mData = data;
    }

    public FaceDataFrame getData() {
        return this.mData;
    }

    private FaceAuthenticationFrame(Parcel source) {
        this.mData = (FaceDataFrame) source.readParcelable(FaceDataFrame.class.getClassLoader(), FaceDataFrame.class);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.mData, flags);
    }
}
