package android.hardware.camera2.utils;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.HashSet;
import java.util.Set;

/* loaded from: classes.dex */
public class ConcurrentCameraIdCombination implements Parcelable {
    public static final Parcelable.Creator<ConcurrentCameraIdCombination> CREATOR = new Parcelable.Creator<ConcurrentCameraIdCombination>() { // from class: android.hardware.camera2.utils.ConcurrentCameraIdCombination.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public ConcurrentCameraIdCombination createFromParcel(Parcel in) {
            return new ConcurrentCameraIdCombination(in);
        }

        @Override // android.os.Parcelable.Creator
        public ConcurrentCameraIdCombination[] newArray(int size) {
            return new ConcurrentCameraIdCombination[size];
        }
    };
    private Set<String> mConcurrentCameraIds;

    /* synthetic */ ConcurrentCameraIdCombination(Parcel parcel, ConcurrentCameraIdCombinationIA concurrentCameraIdCombinationIA) {
        this(parcel);
    }

    /* renamed from: android.hardware.camera2.utils.ConcurrentCameraIdCombination$1 */
    /* loaded from: classes.dex */
    class AnonymousClass1 implements Parcelable.Creator<ConcurrentCameraIdCombination> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public ConcurrentCameraIdCombination createFromParcel(Parcel in) {
            return new ConcurrentCameraIdCombination(in);
        }

        @Override // android.os.Parcelable.Creator
        public ConcurrentCameraIdCombination[] newArray(int size) {
            return new ConcurrentCameraIdCombination[size];
        }
    }

    private ConcurrentCameraIdCombination(Parcel in) {
        this.mConcurrentCameraIds = new HashSet();
        readFromParcel(in);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mConcurrentCameraIds.size());
        for (String cameraId : this.mConcurrentCameraIds) {
            dest.writeString(cameraId);
        }
    }

    public void readFromParcel(Parcel in) {
        this.mConcurrentCameraIds.clear();
        int cameraCombinationSize = in.readInt();
        if (cameraCombinationSize < 0) {
            throw new RuntimeException("cameraCombinationSize " + cameraCombinationSize + " should not be negative");
        }
        for (int i = 0; i < cameraCombinationSize; i++) {
            String cameraId = in.readString();
            if (cameraId == null) {
                throw new RuntimeException("Failed to read camera id from Parcel");
            }
            this.mConcurrentCameraIds.add(cameraId);
        }
    }

    public Set<String> getConcurrentCameraIdCombination() {
        return this.mConcurrentCameraIds;
    }
}
