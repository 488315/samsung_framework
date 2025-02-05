package android.hardware.location;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.content.NativeLibraryHelper;

@SystemApi
/* loaded from: classes2.dex */
public class MemoryRegion implements Parcelable {
    public static final Parcelable.Creator<MemoryRegion> CREATOR = new Parcelable.Creator<MemoryRegion>() { // from class: android.hardware.location.MemoryRegion.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MemoryRegion createFromParcel(Parcel in) {
            return new MemoryRegion(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MemoryRegion[] newArray(int size) {
            return new MemoryRegion[size];
        }
    };
    private boolean mIsExecutable;
    private boolean mIsReadable;
    private boolean mIsWritable;
    private int mSizeBytes;
    private int mSizeBytesFree;

    public int getCapacityBytes() {
        return this.mSizeBytes;
    }

    public int getFreeCapacityBytes() {
        return this.mSizeBytesFree;
    }

    public boolean isReadable() {
        return this.mIsReadable;
    }

    public boolean isWritable() {
        return this.mIsWritable;
    }

    public boolean isExecutable() {
        return this.mIsExecutable;
    }

    public String toString() {
        String mask;
        String mask2;
        String mask3 = isReadable() ? "r" : "" + NativeLibraryHelper.CLEAR_ABI_OVERRIDE;
        if (isWritable()) {
            mask = mask3 + "w";
        } else {
            mask = mask3 + NativeLibraryHelper.CLEAR_ABI_OVERRIDE;
        }
        if (isExecutable()) {
            mask2 = mask + "x";
        } else {
            mask2 = mask + NativeLibraryHelper.CLEAR_ABI_OVERRIDE;
        }
        String retVal = "[ " + this.mSizeBytesFree + "/ " + this.mSizeBytes + " ] : " + mask2;
        return retVal;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof MemoryRegion)) {
            return false;
        }
        MemoryRegion other = (MemoryRegion) object;
        boolean isEqual = other.getCapacityBytes() == this.mSizeBytes && other.getFreeCapacityBytes() == this.mSizeBytesFree && other.isReadable() == this.mIsReadable && other.isWritable() == this.mIsWritable && other.isExecutable() == this.mIsExecutable;
        return isEqual;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mSizeBytes);
        parcel.writeInt(this.mSizeBytesFree);
        parcel.writeInt(this.mIsReadable ? 1 : 0);
        parcel.writeInt(this.mIsWritable ? 1 : 0);
        parcel.writeInt(this.mIsExecutable ? 1 : 0);
    }

    public MemoryRegion(Parcel source) {
        this.mSizeBytes = source.readInt();
        this.mSizeBytesFree = source.readInt();
        this.mIsReadable = source.readInt() != 0;
        this.mIsWritable = source.readInt() != 0;
        this.mIsExecutable = source.readInt() != 0;
    }
}
