package com.sec.internal.ims.servicemodules.im.data.info;

import com.sec.internal.log.IMSLog;
import java.net.URL;

/* loaded from: classes.dex */
public class FtHttpResumeInfo {
    private final long mEnd;
    private final long mStart;
    private final URL mUrl;

    public FtHttpResumeInfo(long j, long j2, URL url) {
        this.mStart = j;
        this.mEnd = j2;
        this.mUrl = url;
    }

    public long getEnd() {
        return this.mEnd;
    }

    public URL getUrl() {
        return this.mUrl;
    }

    public String toString() {
        return "FtHttpResumeInfo [mStart=" + this.mStart + ", mEnd=" + this.mEnd + ", mUrl=" + IMSLog.checker(this.mUrl) + "]";
    }
}
