package com.sec.internal.ims.servicemodules.csh.event;

/* loaded from: classes.dex */
public class IshTransferProgressEvent {
    public long mProgress;
    public int mSessionId;

    public IshTransferProgressEvent(int i, long j) {
        this.mSessionId = i;
        this.mProgress = j;
    }
}
