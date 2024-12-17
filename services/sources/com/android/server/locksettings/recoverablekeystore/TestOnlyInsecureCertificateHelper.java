package com.android.server.locksettings.recoverablekeystore;

import android.os.ServiceSpecificException;
import android.security.keystore.recovery.TrustedRootCertificates;
import android.util.Log;
import java.security.cert.X509Certificate;
import java.util.Date;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class TestOnlyInsecureCertificateHelper {
    public static String getDefaultCertificateAliasIfEmpty(String str) {
        if (str != null && !str.isEmpty()) {
            return str;
        }
        Log.e("TestCertHelper", "rootCertificateAlias is null or empty - use secure default value");
        return "GoogleCloudKeyVaultServiceV1";
    }

    public static X509Certificate getRootCertificate(String str) {
        String defaultCertificateAliasIfEmpty = getDefaultCertificateAliasIfEmpty(str);
        if ("TEST_ONLY_INSECURE_CERTIFICATE_ALIAS".equals(defaultCertificateAliasIfEmpty)) {
            return TrustedRootCertificates.getTestOnlyInsecureCertificate();
        }
        X509Certificate rootCertificate = TrustedRootCertificates.getRootCertificate(defaultCertificateAliasIfEmpty);
        if (rootCertificate != null) {
            return rootCertificate;
        }
        throw new ServiceSpecificException(28, "The provided root certificate alias is invalid");
    }

    public static Date getValidationDate(String str) {
        if ("TEST_ONLY_INSECURE_CERTIFICATE_ALIAS".equals(str)) {
            return new Date(119, 1, 30);
        }
        return null;
    }
}
