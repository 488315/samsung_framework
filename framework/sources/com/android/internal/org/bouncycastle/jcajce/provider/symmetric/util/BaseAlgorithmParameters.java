package com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util;

import java.security.AlgorithmParametersSpi;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;

/* loaded from: classes5.dex */
public abstract class BaseAlgorithmParameters extends AlgorithmParametersSpi {
    protected abstract AlgorithmParameterSpec localEngineGetParameterSpec(Class cls) throws InvalidParameterSpecException;

    public boolean isASN1FormatString(String format) {
        return format == null || format.equals("ASN.1");
    }

    @Override // java.security.AlgorithmParametersSpi
    protected AlgorithmParameterSpec engineGetParameterSpec(Class paramSpec) throws InvalidParameterSpecException {
        if (paramSpec == null) {
            throw new NullPointerException("argument to getParameterSpec must not be null");
        }
        return localEngineGetParameterSpec(paramSpec);
    }
}
