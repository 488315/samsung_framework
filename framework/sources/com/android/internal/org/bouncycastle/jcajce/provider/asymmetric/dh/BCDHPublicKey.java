package com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.dh;

import com.android.internal.org.bouncycastle.asn1.ASN1Integer;
import com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.internal.org.bouncycastle.asn1.ASN1Primitive;
import com.android.internal.org.bouncycastle.asn1.ASN1Sequence;
import com.android.internal.org.bouncycastle.asn1.pkcs.DHParameter;
import com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import com.android.internal.org.bouncycastle.asn1.x9.DomainParameters;
import com.android.internal.org.bouncycastle.asn1.x9.ValidationParams;
import com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import com.android.internal.org.bouncycastle.crypto.params.DHParameters;
import com.android.internal.org.bouncycastle.crypto.params.DHPublicKeyParameters;
import com.android.internal.org.bouncycastle.crypto.params.DHValidationParameters;
import com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.KeyUtil;
import com.android.internal.org.bouncycastle.jcajce.spec.DHDomainParameterSpec;
import com.android.internal.org.bouncycastle.jcajce.spec.DHExtendedPublicKeySpec;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.DHPublicKeySpec;

/* loaded from: classes5.dex */
public class BCDHPublicKey implements DHPublicKey {
    static final long serialVersionUID = -216691575254424324L;
    private transient DHPublicKeyParameters dhPublicKey;
    private transient DHParameterSpec dhSpec;
    private transient SubjectPublicKeyInfo info;
    private BigInteger y;

    BCDHPublicKey(DHPublicKeySpec spec) {
        this.y = spec.getY();
        if (spec instanceof DHExtendedPublicKeySpec) {
            this.dhSpec = ((DHExtendedPublicKeySpec) spec).getParams();
        } else {
            this.dhSpec = new DHParameterSpec(spec.getP(), spec.getG());
        }
        if (this.dhSpec instanceof DHDomainParameterSpec) {
            DHDomainParameterSpec dhSp = (DHDomainParameterSpec) this.dhSpec;
            this.dhPublicKey = new DHPublicKeyParameters(this.y, dhSp.getDomainParameters());
        } else {
            this.dhPublicKey = new DHPublicKeyParameters(this.y, new DHParameters(spec.getP(), spec.getG()));
        }
    }

    BCDHPublicKey(DHPublicKey key) {
        this.y = key.getY();
        this.dhSpec = key.getParams();
        if (this.dhSpec instanceof DHDomainParameterSpec) {
            DHDomainParameterSpec dhSp = (DHDomainParameterSpec) this.dhSpec;
            this.dhPublicKey = new DHPublicKeyParameters(this.y, dhSp.getDomainParameters());
        } else {
            this.dhPublicKey = new DHPublicKeyParameters(this.y, new DHParameters(this.dhSpec.getP(), this.dhSpec.getG()));
        }
    }

    BCDHPublicKey(DHPublicKeyParameters params) {
        this.y = params.getY();
        this.dhSpec = new DHDomainParameterSpec(params.getParameters());
        this.dhPublicKey = params;
    }

    BCDHPublicKey(BigInteger y, DHParameterSpec dhSpec) {
        this.y = y;
        this.dhSpec = dhSpec;
        if (dhSpec instanceof DHDomainParameterSpec) {
            this.dhPublicKey = new DHPublicKeyParameters(y, ((DHDomainParameterSpec) dhSpec).getDomainParameters());
        } else {
            this.dhPublicKey = new DHPublicKeyParameters(y, new DHParameters(dhSpec.getP(), dhSpec.getG()));
        }
    }

    public BCDHPublicKey(SubjectPublicKeyInfo info) {
        this.info = info;
        try {
            ASN1Integer derY = (ASN1Integer) info.parsePublicKey();
            this.y = derY.getValue();
            ASN1Sequence seq = ASN1Sequence.getInstance(info.getAlgorithm().getParameters());
            ASN1ObjectIdentifier id = info.getAlgorithm().getAlgorithm();
            if (!id.equals((ASN1Primitive) PKCSObjectIdentifiers.dhKeyAgreement) && !isPKCSParam(seq)) {
                if (!id.equals((ASN1Primitive) X9ObjectIdentifiers.dhpublicnumber)) {
                    throw new IllegalArgumentException("unknown algorithm type: " + id);
                }
                DomainParameters params = DomainParameters.getInstance(seq);
                ValidationParams validationParams = params.getValidationParams();
                if (validationParams != null) {
                    this.dhPublicKey = new DHPublicKeyParameters(this.y, new DHParameters(params.getP(), params.getG(), params.getQ(), params.getJ(), new DHValidationParameters(validationParams.getSeed(), validationParams.getPgenCounter().intValue())));
                } else {
                    this.dhPublicKey = new DHPublicKeyParameters(this.y, new DHParameters(params.getP(), params.getG(), params.getQ(), params.getJ(), (DHValidationParameters) null));
                }
                this.dhSpec = new DHDomainParameterSpec(this.dhPublicKey.getParameters());
                return;
            }
            DHParameter params2 = DHParameter.getInstance(seq);
            if (params2.getL() != null) {
                this.dhSpec = new DHParameterSpec(params2.getP(), params2.getG(), params2.getL().intValue());
                this.dhPublicKey = new DHPublicKeyParameters(this.y, new DHParameters(this.dhSpec.getP(), this.dhSpec.getG(), null, this.dhSpec.getL()));
            } else {
                this.dhSpec = new DHParameterSpec(params2.getP(), params2.getG());
                this.dhPublicKey = new DHPublicKeyParameters(this.y, new DHParameters(this.dhSpec.getP(), this.dhSpec.getG()));
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("invalid info structure in DH public key");
        }
    }

    @Override // java.security.Key
    public String getAlgorithm() {
        return "DH";
    }

    @Override // java.security.Key
    public String getFormat() {
        return "X.509";
    }

    @Override // java.security.Key
    public byte[] getEncoded() {
        if (this.info != null) {
            return KeyUtil.getEncodedSubjectPublicKeyInfo(this.info);
        }
        if ((this.dhSpec instanceof DHDomainParameterSpec) && ((DHDomainParameterSpec) this.dhSpec).getQ() != null) {
            DHParameters params = ((DHDomainParameterSpec) this.dhSpec).getDomainParameters();
            DHValidationParameters validationParameters = params.getValidationParameters();
            ValidationParams vParams = null;
            if (validationParameters != null) {
                vParams = new ValidationParams(validationParameters.getSeed(), validationParameters.getCounter());
            }
            return KeyUtil.getEncodedSubjectPublicKeyInfo(new AlgorithmIdentifier(X9ObjectIdentifiers.dhpublicnumber, new DomainParameters(params.getP(), params.getG(), params.getQ(), params.getJ(), vParams).toASN1Primitive()), new ASN1Integer(this.y));
        }
        return KeyUtil.getEncodedSubjectPublicKeyInfo(new AlgorithmIdentifier(PKCSObjectIdentifiers.dhKeyAgreement, new DHParameter(this.dhSpec.getP(), this.dhSpec.getG(), this.dhSpec.getL()).toASN1Primitive()), new ASN1Integer(this.y));
    }

    public String toString() {
        return DHUtil.publicKeyToString("DH", this.y, new DHParameters(this.dhSpec.getP(), this.dhSpec.getG()));
    }

    @Override // javax.crypto.interfaces.DHKey
    public DHParameterSpec getParams() {
        return this.dhSpec;
    }

    @Override // javax.crypto.interfaces.DHPublicKey
    public BigInteger getY() {
        return this.y;
    }

    public DHPublicKeyParameters engineGetKeyParameters() {
        return this.dhPublicKey;
    }

    private boolean isPKCSParam(ASN1Sequence seq) {
        if (seq.size() == 2) {
            return true;
        }
        if (seq.size() > 3) {
            return false;
        }
        ASN1Integer l = ASN1Integer.getInstance(seq.getObjectAt(2));
        ASN1Integer p = ASN1Integer.getInstance(seq.getObjectAt(0));
        return l.getValue().compareTo(BigInteger.valueOf((long) p.getValue().bitLength())) <= 0;
    }

    public int hashCode() {
        return ((getY().hashCode() ^ getParams().getG().hashCode()) ^ getParams().getP().hashCode()) ^ getParams().getL();
    }

    public boolean equals(Object o) {
        if (!(o instanceof DHPublicKey)) {
            return false;
        }
        DHPublicKey other = (DHPublicKey) o;
        return getY().equals(other.getY()) && getParams().getG().equals(other.getParams().getG()) && getParams().getP().equals(other.getParams().getP()) && getParams().getL() == other.getParams().getL();
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        this.dhSpec = new DHParameterSpec((BigInteger) in.readObject(), (BigInteger) in.readObject(), in.readInt());
        this.info = null;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeObject(this.dhSpec.getP());
        out.writeObject(this.dhSpec.getG());
        out.writeInt(this.dhSpec.getL());
    }
}
