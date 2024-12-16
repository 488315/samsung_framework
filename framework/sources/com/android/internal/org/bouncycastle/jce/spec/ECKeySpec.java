package com.android.internal.org.bouncycastle.jce.spec;

import java.security.spec.KeySpec;

/* loaded from: classes5.dex */
public class ECKeySpec implements KeySpec {
    private ECParameterSpec spec;

    protected ECKeySpec(ECParameterSpec spec) {
        this.spec = spec;
    }

    public ECParameterSpec getParams() {
        return this.spec;
    }
}
