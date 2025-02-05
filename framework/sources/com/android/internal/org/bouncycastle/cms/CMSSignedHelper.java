package com.android.internal.org.bouncycastle.cms;

import com.android.internal.org.bouncycastle.asn1.ASN1Encodable;
import com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.internal.org.bouncycastle.asn1.ASN1Primitive;
import com.android.internal.org.bouncycastle.asn1.ASN1Sequence;
import com.android.internal.org.bouncycastle.asn1.ASN1Set;
import com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.internal.org.bouncycastle.asn1.DERNull;
import com.android.internal.org.bouncycastle.asn1.eac.EACObjectIdentifiers;
import com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import com.android.internal.org.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import com.android.internal.org.bouncycastle.asn1.teletrust.TeleTrusTObjectIdentifiers;
import com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import com.android.internal.org.bouncycastle.asn1.x509.AttributeCertificate;
import com.android.internal.org.bouncycastle.asn1.x509.Certificate;
import com.android.internal.org.bouncycastle.asn1.x509.CertificateList;
import com.android.internal.org.bouncycastle.asn1.x509.X509ObjectIdentifiers;
import com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import com.android.internal.org.bouncycastle.cert.X509AttributeCertificateHolder;
import com.android.internal.org.bouncycastle.cert.X509CRLHolder;
import com.android.internal.org.bouncycastle.cert.X509CertificateHolder;
import com.android.internal.org.bouncycastle.util.CollectionStore;
import com.android.internal.org.bouncycastle.util.Store;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes5.dex */
class CMSSignedHelper {
    static final CMSSignedHelper INSTANCE = new CMSSignedHelper();
    private static final Map encryptionAlgs = new HashMap();

    CMSSignedHelper() {
    }

    static {
        addEntries(NISTObjectIdentifiers.dsa_with_sha224, "DSA");
        addEntries(NISTObjectIdentifiers.dsa_with_sha256, "DSA");
        addEntries(NISTObjectIdentifiers.dsa_with_sha384, "DSA");
        addEntries(NISTObjectIdentifiers.dsa_with_sha512, "DSA");
        addEntries(OIWObjectIdentifiers.dsaWithSHA1, "DSA");
        addEntries(OIWObjectIdentifiers.md5WithRSA, "RSA");
        addEntries(OIWObjectIdentifiers.sha1WithRSA, "RSA");
        addEntries(PKCSObjectIdentifiers.md5WithRSAEncryption, "RSA");
        addEntries(PKCSObjectIdentifiers.sha1WithRSAEncryption, "RSA");
        addEntries(PKCSObjectIdentifiers.sha224WithRSAEncryption, "RSA");
        addEntries(PKCSObjectIdentifiers.sha256WithRSAEncryption, "RSA");
        addEntries(PKCSObjectIdentifiers.sha384WithRSAEncryption, "RSA");
        addEntries(PKCSObjectIdentifiers.sha512WithRSAEncryption, "RSA");
        addEntries(X9ObjectIdentifiers.ecdsa_with_SHA1, "ECDSA");
        addEntries(X9ObjectIdentifiers.ecdsa_with_SHA224, "ECDSA");
        addEntries(X9ObjectIdentifiers.ecdsa_with_SHA256, "ECDSA");
        addEntries(X9ObjectIdentifiers.ecdsa_with_SHA384, "ECDSA");
        addEntries(X9ObjectIdentifiers.ecdsa_with_SHA512, "ECDSA");
        addEntries(X9ObjectIdentifiers.id_dsa_with_sha1, "DSA");
        addEntries(EACObjectIdentifiers.id_TA_ECDSA_SHA_1, "ECDSA");
        addEntries(EACObjectIdentifiers.id_TA_ECDSA_SHA_224, "ECDSA");
        addEntries(EACObjectIdentifiers.id_TA_ECDSA_SHA_256, "ECDSA");
        addEntries(EACObjectIdentifiers.id_TA_ECDSA_SHA_384, "ECDSA");
        addEntries(EACObjectIdentifiers.id_TA_ECDSA_SHA_512, "ECDSA");
        addEntries(EACObjectIdentifiers.id_TA_RSA_v1_5_SHA_1, "RSA");
        addEntries(EACObjectIdentifiers.id_TA_RSA_v1_5_SHA_256, "RSA");
        addEntries(EACObjectIdentifiers.id_TA_RSA_PSS_SHA_1, "RSAandMGF1");
        addEntries(EACObjectIdentifiers.id_TA_RSA_PSS_SHA_256, "RSAandMGF1");
        addEntries(X9ObjectIdentifiers.id_dsa, "DSA");
        addEntries(PKCSObjectIdentifiers.rsaEncryption, "RSA");
        addEntries(TeleTrusTObjectIdentifiers.teleTrusTRSAsignatureAlgorithm, "RSA");
        addEntries(X509ObjectIdentifiers.id_ea_rsa, "RSA");
    }

    private static void addEntries(ASN1ObjectIdentifier alias, String encryption) {
        encryptionAlgs.put(alias.getId(), encryption);
    }

    String getEncryptionAlgName(String encryptionAlgOID) {
        String algName = (String) encryptionAlgs.get(encryptionAlgOID);
        if (algName != null) {
            return algName;
        }
        return encryptionAlgOID;
    }

    AlgorithmIdentifier fixAlgID(AlgorithmIdentifier algId) {
        if (algId.getParameters() == null) {
            return new AlgorithmIdentifier(algId.getAlgorithm(), DERNull.INSTANCE);
        }
        return algId;
    }

    void setSigningEncryptionAlgorithmMapping(ASN1ObjectIdentifier oid, String algorithmName) {
        addEntries(oid, algorithmName);
    }

    Store getCertificates(ASN1Set certSet) {
        if (certSet != null) {
            List certList = new ArrayList(certSet.size());
            Enumeration en = certSet.getObjects();
            while (en.hasMoreElements()) {
                ASN1Primitive obj = ((ASN1Encodable) en.nextElement()).toASN1Primitive();
                if (obj instanceof ASN1Sequence) {
                    certList.add(new X509CertificateHolder(Certificate.getInstance(obj)));
                }
            }
            return new CollectionStore(certList);
        }
        return new CollectionStore(new ArrayList());
    }

    Store getAttributeCertificates(ASN1Set certSet) {
        if (certSet != null) {
            List certList = new ArrayList(certSet.size());
            Enumeration en = certSet.getObjects();
            while (en.hasMoreElements()) {
                ASN1Primitive obj = ((ASN1Encodable) en.nextElement()).toASN1Primitive();
                if (obj instanceof ASN1TaggedObject) {
                    certList.add(new X509AttributeCertificateHolder(AttributeCertificate.getInstance(((ASN1TaggedObject) obj).getObject())));
                }
            }
            return new CollectionStore(certList);
        }
        return new CollectionStore(new ArrayList());
    }

    Store getCRLs(ASN1Set crlSet) {
        if (crlSet != null) {
            List crlList = new ArrayList(crlSet.size());
            Enumeration en = crlSet.getObjects();
            while (en.hasMoreElements()) {
                ASN1Primitive obj = ((ASN1Encodable) en.nextElement()).toASN1Primitive();
                if (obj instanceof ASN1Sequence) {
                    crlList.add(new X509CRLHolder(CertificateList.getInstance(obj)));
                }
            }
            return new CollectionStore(crlList);
        }
        return new CollectionStore(new ArrayList());
    }
}
