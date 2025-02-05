package com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509;

import com.android.internal.org.bouncycastle.asn1.x509.BasicConstraints;
import com.android.internal.org.bouncycastle.asn1.x509.Certificate;
import com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper;
import java.security.cert.CertificateEncodingException;

/* loaded from: classes5.dex */
class X509CertificateInternal extends X509CertificateImpl {
    private final byte[] encoding;

    X509CertificateInternal(JcaJceHelper bcHelper, Certificate c, BasicConstraints basicConstraints, boolean[] keyUsage, String sigAlgName, byte[] sigAlgParams, byte[] encoding) {
        super(bcHelper, c, basicConstraints, keyUsage, sigAlgName, sigAlgParams);
        this.encoding = encoding;
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.X509CertificateImpl, java.security.cert.Certificate
    public byte[] getEncoded() throws CertificateEncodingException {
        if (this.encoding == null) {
            throw new CertificateEncodingException();
        }
        return this.encoding;
    }
}
