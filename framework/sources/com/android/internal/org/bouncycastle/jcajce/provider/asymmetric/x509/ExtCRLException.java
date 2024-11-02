package com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509;

import java.security.cert.CRLException;

/* loaded from: classes5.dex */
class ExtCRLException extends CRLException {
    Throwable cause;

    public ExtCRLException(String message, Throwable cause) {
        super(message);
        this.cause = cause;
    }

    @Override // java.lang.Throwable
    public Throwable getCause() {
        return this.cause;
    }
}
