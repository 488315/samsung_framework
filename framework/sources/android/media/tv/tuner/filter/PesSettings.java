package android.media.tv.tuner.filter;

import android.annotation.SystemApi;
import android.media.tv.tuner.TunerUtils;

@SystemApi
/* loaded from: classes2.dex */
public class PesSettings extends Settings {
    private final boolean mIsRaw;
    private final int mStreamId;

    /* synthetic */ PesSettings(int i, int i2, boolean z, PesSettingsIA pesSettingsIA) {
        this(i, i2, z);
    }

    private PesSettings(int mainType, int streamId, boolean isRaw) {
        super(TunerUtils.getFilterSubtype(mainType, 2));
        this.mStreamId = streamId;
        this.mIsRaw = isRaw;
    }

    public int getStreamId() {
        return this.mStreamId;
    }

    public boolean isRaw() {
        return this.mIsRaw;
    }

    public static Builder builder(int mainType) {
        return new Builder(mainType);
    }

    /* loaded from: classes2.dex */
    public static class Builder {
        private boolean mIsRaw;
        private final int mMainType;
        private int mStreamId;

        /* synthetic */ Builder(int i, BuilderIA builderIA) {
            this(i);
        }

        private Builder(int mainType) {
            this.mMainType = mainType;
        }

        public Builder setStreamId(int streamId) {
            this.mStreamId = streamId;
            return this;
        }

        public Builder setRaw(boolean isRaw) {
            this.mIsRaw = isRaw;
            return this;
        }

        public PesSettings build() {
            return new PesSettings(this.mMainType, this.mStreamId, this.mIsRaw);
        }
    }
}
