package android.media.tv.tuner.filter;

import android.annotation.SystemApi;

@SystemApi
/* loaded from: classes3.dex */
public class PesEvent extends FilterEvent {
    private final int mDataLength;
    private final int mMpuSequenceNumber;
    private final int mStreamId;

    private PesEvent(int streamId, int dataLength, int mpuSequenceNumber) {
        this.mStreamId = streamId;
        this.mDataLength = dataLength;
        this.mMpuSequenceNumber = mpuSequenceNumber;
    }

    public int getStreamId() {
        return this.mStreamId;
    }

    public int getDataLength() {
        return this.mDataLength;
    }

    public int getMpuSequenceNumber() {
        return this.mMpuSequenceNumber;
    }
}
