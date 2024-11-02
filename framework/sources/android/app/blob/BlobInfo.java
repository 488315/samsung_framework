package android.app.blob;

import android.app.AppGlobals;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.format.Formatter;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
public final class BlobInfo implements Parcelable {
    public static final Parcelable.Creator<BlobInfo> CREATOR = new Parcelable.Creator<BlobInfo>() { // from class: android.app.blob.BlobInfo.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public BlobInfo createFromParcel(Parcel source) {
            return new BlobInfo(source);
        }

        @Override // android.os.Parcelable.Creator
        public BlobInfo[] newArray(int size) {
            return new BlobInfo[size];
        }
    };
    private final long mExpiryTimeMs;
    private final long mId;
    private final CharSequence mLabel;
    private final List<LeaseInfo> mLeaseInfos;
    private final long mSizeBytes;

    /* synthetic */ BlobInfo(Parcel parcel, BlobInfoIA blobInfoIA) {
        this(parcel);
    }

    public BlobInfo(long id, long expiryTimeMs, CharSequence label, long sizeBytes, List<LeaseInfo> leaseInfos) {
        this.mId = id;
        this.mExpiryTimeMs = expiryTimeMs;
        this.mLabel = label;
        this.mSizeBytes = sizeBytes;
        this.mLeaseInfos = leaseInfos;
    }

    private BlobInfo(Parcel in) {
        this.mId = in.readLong();
        this.mExpiryTimeMs = in.readLong();
        this.mLabel = in.readCharSequence();
        this.mSizeBytes = in.readLong();
        this.mLeaseInfos = in.readArrayList(null);
    }

    public long getId() {
        return this.mId;
    }

    public long getExpiryTimeMs() {
        return this.mExpiryTimeMs;
    }

    public CharSequence getLabel() {
        return this.mLabel;
    }

    public long getSizeBytes() {
        return this.mSizeBytes;
    }

    public List<LeaseInfo> getLeases() {
        return Collections.unmodifiableList(this.mLeaseInfos);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.mId);
        dest.writeLong(this.mExpiryTimeMs);
        dest.writeCharSequence(this.mLabel);
        dest.writeLong(this.mSizeBytes);
        dest.writeList(this.mLeaseInfos);
    }

    public String toString() {
        return toShortString();
    }

    private String toShortString() {
        return "BlobInfo {id: " + this.mId + ",expiryMs: " + this.mExpiryTimeMs + ",label: " + ((Object) this.mLabel) + ",size: " + formatBlobSize(this.mSizeBytes) + ",leases: " + LeaseInfo.toShortString(this.mLeaseInfos) + ",}";
    }

    private static String formatBlobSize(long sizeBytes) {
        return Formatter.formatFileSize(AppGlobals.getInitialApplication(), sizeBytes, 8);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    /* renamed from: android.app.blob.BlobInfo$1 */
    /* loaded from: classes.dex */
    class AnonymousClass1 implements Parcelable.Creator<BlobInfo> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public BlobInfo createFromParcel(Parcel source) {
            return new BlobInfo(source);
        }

        @Override // android.os.Parcelable.Creator
        public BlobInfo[] newArray(int size) {
            return new BlobInfo[size];
        }
    }
}
