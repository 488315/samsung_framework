package android.content.pm;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public class IncrementalStatesInfo implements Parcelable {
    public static final Parcelable.Creator<IncrementalStatesInfo> CREATOR = new Parcelable.Creator<IncrementalStatesInfo>() { // from class: android.content.pm.IncrementalStatesInfo.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public IncrementalStatesInfo createFromParcel(Parcel source) {
            return new IncrementalStatesInfo(source);
        }

        @Override // android.os.Parcelable.Creator
        public IncrementalStatesInfo[] newArray(int size) {
            return new IncrementalStatesInfo[size];
        }
    };
    private boolean mIsLoading;
    private long mLoadingCompletedTime;
    private float mProgress;

    /* synthetic */ IncrementalStatesInfo(Parcel parcel, IncrementalStatesInfoIA incrementalStatesInfoIA) {
        this(parcel);
    }

    public IncrementalStatesInfo(boolean isLoading, float progress, long loadingCompletedTime) {
        this.mIsLoading = isLoading;
        this.mProgress = progress;
        this.mLoadingCompletedTime = loadingCompletedTime;
    }

    private IncrementalStatesInfo(Parcel source) {
        this.mIsLoading = source.readBoolean();
        this.mProgress = source.readFloat();
        this.mLoadingCompletedTime = source.readLong();
    }

    public boolean isLoading() {
        return this.mIsLoading;
    }

    public float getProgress() {
        return this.mProgress;
    }

    public long getLoadingCompletedTime() {
        return this.mLoadingCompletedTime;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeBoolean(this.mIsLoading);
        dest.writeFloat(this.mProgress);
        dest.writeLong(this.mLoadingCompletedTime);
    }

    /* renamed from: android.content.pm.IncrementalStatesInfo$1 */
    /* loaded from: classes.dex */
    class AnonymousClass1 implements Parcelable.Creator<IncrementalStatesInfo> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public IncrementalStatesInfo createFromParcel(Parcel source) {
            return new IncrementalStatesInfo(source);
        }

        @Override // android.os.Parcelable.Creator
        public IncrementalStatesInfo[] newArray(int size) {
            return new IncrementalStatesInfo[size];
        }
    }
}
