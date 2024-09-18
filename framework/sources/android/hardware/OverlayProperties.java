package android.hardware;

import android.os.Parcel;
import android.os.Parcelable;
import libcore.util.NativeAllocationRegistry;

/* loaded from: classes.dex */
public final class OverlayProperties implements Parcelable {
    private Runnable mCloser;
    private long mNativeObject;
    private static final NativeAllocationRegistry sRegistry = NativeAllocationRegistry.createMalloced(OverlayProperties.class.getClassLoader(), nGetDestructor());
    public static final Parcelable.Creator<OverlayProperties> CREATOR = new Parcelable.Creator<OverlayProperties>() { // from class: android.hardware.OverlayProperties.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public OverlayProperties createFromParcel(Parcel in) {
            if (in.readInt() != 0) {
                return new OverlayProperties(OverlayProperties.nReadOverlayPropertiesFromParcel(in));
            }
            return null;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public OverlayProperties[] newArray(int size) {
            return new OverlayProperties[size];
        }
    };

    private static native long nGetDestructor();

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nReadOverlayPropertiesFromParcel(Parcel parcel);

    private static native boolean nSupportFp16ForHdr(long j);

    private static native boolean nSupportMixedColorSpaces(long j);

    private static native void nWriteOverlayPropertiesToParcel(long j, Parcel parcel);

    public OverlayProperties(long nativeObject) {
        if (nativeObject != 0) {
            this.mCloser = sRegistry.registerNativeAllocation(this, nativeObject);
        }
        this.mNativeObject = nativeObject;
    }

    public boolean supportFp16ForHdr() {
        long j = this.mNativeObject;
        if (j == 0) {
            return false;
        }
        return nSupportFp16ForHdr(j);
    }

    public boolean supportMixedColorSpaces() {
        long j = this.mNativeObject;
        if (j == 0) {
            return false;
        }
        return nSupportMixedColorSpaces(j);
    }

    public void release() {
        if (this.mNativeObject != 0) {
            this.mCloser.run();
            this.mNativeObject = 0L;
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        if (this.mNativeObject == 0) {
            dest.writeInt(0);
        } else {
            dest.writeInt(1);
            nWriteOverlayPropertiesToParcel(this.mNativeObject, dest);
        }
    }
}
