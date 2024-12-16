package com.android.internal.org.bouncycastle.asn1;

import java.io.InputStream;

/* loaded from: classes5.dex */
abstract class LimitedInputStream extends InputStream {
    protected final InputStream _in;
    private int _limit;

    LimitedInputStream(InputStream in, int limit) {
        this._in = in;
        this._limit = limit;
    }

    int getLimit() {
        return this._limit;
    }

    protected void setParentEofDetect(boolean on) {
        if (this._in instanceof IndefiniteLengthInputStream) {
            ((IndefiniteLengthInputStream) this._in).setEofOn00(on);
        }
    }
}
