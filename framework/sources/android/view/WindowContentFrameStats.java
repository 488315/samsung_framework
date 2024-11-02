package android.view;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes4.dex */
public final class WindowContentFrameStats extends FrameStats implements Parcelable {
    public static final Parcelable.Creator<WindowContentFrameStats> CREATOR = new Parcelable.Creator<WindowContentFrameStats>() { // from class: android.view.WindowContentFrameStats.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public WindowContentFrameStats createFromParcel(Parcel parcel) {
            return new WindowContentFrameStats(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public WindowContentFrameStats[] newArray(int size) {
            return new WindowContentFrameStats[size];
        }
    };
    private long[] mFramesPostedTimeNano;
    private long[] mFramesReadyTimeNano;

    /* synthetic */ WindowContentFrameStats(Parcel parcel, WindowContentFrameStatsIA windowContentFrameStatsIA) {
        this(parcel);
    }

    public WindowContentFrameStats() {
    }

    public void init(long refreshPeriodNano, long[] framesPostedTimeNano, long[] framesPresentedTimeNano, long[] framesReadyTimeNano) {
        this.mRefreshPeriodNano = refreshPeriodNano;
        this.mFramesPostedTimeNano = framesPostedTimeNano;
        this.mFramesPresentedTimeNano = framesPresentedTimeNano;
        this.mFramesReadyTimeNano = framesReadyTimeNano;
    }

    private WindowContentFrameStats(Parcel parcel) {
        this.mRefreshPeriodNano = parcel.readLong();
        this.mFramesPostedTimeNano = parcel.createLongArray();
        this.mFramesPresentedTimeNano = parcel.createLongArray();
        this.mFramesReadyTimeNano = parcel.createLongArray();
    }

    public long getFramePostedTimeNano(int index) {
        long[] jArr = this.mFramesPostedTimeNano;
        if (jArr == null) {
            throw new IndexOutOfBoundsException();
        }
        return jArr[index];
    }

    public long getFrameReadyTimeNano(int index) {
        long[] jArr = this.mFramesReadyTimeNano;
        if (jArr == null) {
            throw new IndexOutOfBoundsException();
        }
        return jArr[index];
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeLong(this.mRefreshPeriodNano);
        parcel.writeLongArray(this.mFramesPostedTimeNano);
        parcel.writeLongArray(this.mFramesPresentedTimeNano);
        parcel.writeLongArray(this.mFramesReadyTimeNano);
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("WindowContentFrameStats[");
        builder.append("frameCount:" + getFrameCount());
        builder.append(", fromTimeNano:" + getStartTimeNano());
        builder.append(", toTimeNano:" + getEndTimeNano());
        builder.append(']');
        return builder.toString();
    }

    /* renamed from: android.view.WindowContentFrameStats$1 */
    /* loaded from: classes4.dex */
    class AnonymousClass1 implements Parcelable.Creator<WindowContentFrameStats> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public WindowContentFrameStats createFromParcel(Parcel parcel) {
            return new WindowContentFrameStats(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public WindowContentFrameStats[] newArray(int size) {
            return new WindowContentFrameStats[size];
        }
    }
}
