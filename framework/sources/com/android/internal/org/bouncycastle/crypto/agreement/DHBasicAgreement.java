package com.android.internal.org.bouncycastle.crypto.agreement;

import com.android.internal.org.bouncycastle.crypto.BasicAgreement;
import com.android.internal.org.bouncycastle.crypto.CipherParameters;
import com.android.internal.org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import com.android.internal.org.bouncycastle.crypto.params.DHParameters;
import com.android.internal.org.bouncycastle.crypto.params.DHPrivateKeyParameters;
import com.android.internal.org.bouncycastle.crypto.params.DHPublicKeyParameters;
import com.android.internal.org.bouncycastle.crypto.params.ParametersWithRandom;
import java.math.BigInteger;

/* loaded from: classes5.dex */
public class DHBasicAgreement implements BasicAgreement {
    private static final BigInteger ONE = BigInteger.valueOf(1);
    private DHParameters dhParams;
    private DHPrivateKeyParameters key;

    @Override // com.android.internal.org.bouncycastle.crypto.BasicAgreement
    public void init(CipherParameters param) {
        AsymmetricKeyParameter kParam;
        if (param instanceof ParametersWithRandom) {
            ParametersWithRandom rParam = (ParametersWithRandom) param;
            kParam = (AsymmetricKeyParameter) rParam.getParameters();
        } else {
            kParam = (AsymmetricKeyParameter) param;
        }
        if (!(kParam instanceof DHPrivateKeyParameters)) {
            throw new IllegalArgumentException("DHEngine expects DHPrivateKeyParameters");
        }
        this.key = (DHPrivateKeyParameters) kParam;
        this.dhParams = this.key.getParameters();
    }

    @Override // com.android.internal.org.bouncycastle.crypto.BasicAgreement
    public int getFieldSize() {
        return (this.key.getParameters().getP().bitLength() + 7) / 8;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.BasicAgreement
    public BigInteger calculateAgreement(CipherParameters pubKey) {
        DHPublicKeyParameters pub = (DHPublicKeyParameters) pubKey;
        if (!pub.getParameters().equals(this.dhParams)) {
            throw new IllegalArgumentException("Diffie-Hellman public key has wrong parameters.");
        }
        BigInteger p = this.dhParams.getP();
        BigInteger peerY = pub.getY();
        if (peerY == null || peerY.compareTo(ONE) <= 0 || peerY.compareTo(p.subtract(ONE)) >= 0) {
            throw new IllegalArgumentException("Diffie-Hellman public key is weak");
        }
        BigInteger result = peerY.modPow(this.key.getX(), p);
        if (result.equals(ONE)) {
            throw new IllegalStateException("Shared key can't be 1");
        }
        return result;
    }
}
