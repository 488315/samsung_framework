package com.android.internal.org.bouncycastle.jcajce.provider.util;

import com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.internal.org.bouncycastle.jcajce.provider.config.ConfigurableProvider;

/* loaded from: classes5.dex */
public abstract class AsymmetricAlgorithmProvider extends AlgorithmProvider {
    protected void addSignatureAlgorithm(ConfigurableProvider provider, String algorithm, String className, ASN1ObjectIdentifier oid) {
        provider.addAlgorithm("Signature." + algorithm, className);
        provider.addAlgorithm("Alg.Alias.Signature." + oid, algorithm);
        provider.addAlgorithm("Alg.Alias.Signature.OID." + oid, algorithm);
    }

    public void addSignatureAlgorithm(ConfigurableProvider provider, String digest, String algorithm, String className, ASN1ObjectIdentifier oid) {
        String mainName = digest + "WITH" + algorithm;
        String jdk11Variation1 = digest + "with" + algorithm;
        String jdk11Variation2 = digest + "With" + algorithm;
        String alias = digest + "/" + algorithm;
        provider.addAlgorithm("Signature." + mainName, className);
        provider.addAlgorithm("Alg.Alias.Signature." + jdk11Variation1, mainName);
        provider.addAlgorithm("Alg.Alias.Signature." + jdk11Variation2, mainName);
        provider.addAlgorithm("Alg.Alias.Signature." + alias, mainName);
        provider.addAlgorithm("Alg.Alias.Signature." + oid, mainName);
        provider.addAlgorithm("Alg.Alias.Signature.OID." + oid, mainName);
    }

    public void registerOid(ConfigurableProvider provider, ASN1ObjectIdentifier oid, String name, AsymmetricKeyInfoConverter keyFactory) {
        provider.addAlgorithm("Alg.Alias.KeyFactory." + oid, name);
        provider.addAlgorithm("Alg.Alias.KeyPairGenerator." + oid, name);
        provider.addKeyInfoConverter(oid, keyFactory);
    }

    protected void registerOidAlgorithmParameters(ConfigurableProvider provider, ASN1ObjectIdentifier oid, String name) {
        provider.addAlgorithm("Alg.Alias.AlgorithmParameters." + oid, name);
    }

    public void registerOidAlgorithmParameterGenerator(ConfigurableProvider provider, ASN1ObjectIdentifier oid, String name) {
        provider.addAlgorithm("Alg.Alias.AlgorithmParameterGenerator." + oid, name);
        provider.addAlgorithm("Alg.Alias.AlgorithmParameters." + oid, name);
    }
}
