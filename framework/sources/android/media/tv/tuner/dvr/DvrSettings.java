package android.media.tv.tuner.dvr;

import android.annotation.SystemApi;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SystemApi
/* loaded from: classes2.dex */
public class DvrSettings {
    public static final int DATA_FORMAT_ES = 2;
    public static final int DATA_FORMAT_PES = 1;
    public static final int DATA_FORMAT_SHV_TLV = 3;
    public static final int DATA_FORMAT_TS = 0;
    private final int mDataFormat;
    private final long mHighThreshold;
    private final long mLowThreshold;
    private final long mPacketSize;
    private final int mStatusMask;

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface DataFormat {
    }

    /* synthetic */ DvrSettings(int i, long j, long j2, long j3, int i2, DvrSettingsIA dvrSettingsIA) {
        this(i, j, j2, j3, i2);
    }

    private DvrSettings(int statusMask, long lowThreshold, long highThreshold, long packetSize, int dataFormat) {
        this.mStatusMask = statusMask;
        this.mLowThreshold = lowThreshold;
        this.mHighThreshold = highThreshold;
        this.mPacketSize = packetSize;
        this.mDataFormat = dataFormat;
    }

    public int getStatusMask() {
        return this.mStatusMask;
    }

    public long getLowThreshold() {
        return this.mLowThreshold;
    }

    public long getHighThreshold() {
        return this.mHighThreshold;
    }

    public long getPacketSize() {
        return this.mPacketSize;
    }

    public int getDataFormat() {
        return this.mDataFormat;
    }

    public static Builder builder() {
        return new Builder();
    }

    /* loaded from: classes2.dex */
    public static final class Builder {
        private int mDataFormat;
        private long mHighThreshold;
        private long mLowThreshold;
        private long mPacketSize;
        private int mStatusMask;

        public Builder setStatusMask(int statusMask) {
            this.mStatusMask = statusMask;
            return this;
        }

        public Builder setLowThreshold(long lowThreshold) {
            this.mLowThreshold = lowThreshold;
            return this;
        }

        public Builder setHighThreshold(long highThreshold) {
            this.mHighThreshold = highThreshold;
            return this;
        }

        public Builder setPacketSize(long packetSize) {
            this.mPacketSize = packetSize;
            return this;
        }

        public Builder setDataFormat(int dataFormat) {
            this.mDataFormat = dataFormat;
            return this;
        }

        public DvrSettings build() {
            return new DvrSettings(this.mStatusMask, this.mLowThreshold, this.mHighThreshold, this.mPacketSize, this.mDataFormat);
        }
    }
}
