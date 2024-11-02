package android.security.keystore2;

import android.security.GateKeeper;
import android.security.KeyStore;
import android.security.keymaster.KeymasterArguments;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyInfo;
import android.security.keystore.KeyProperties;
import android.system.keystore2.Authorization;
import com.samsung.android.ims.options.SemCapabilities;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.ProviderException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactorySpi;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes3.dex */
public class AndroidKeyStoreSecretKeyFactorySpi extends SecretKeyFactorySpi {
    private final KeyStore mKeyStore = KeyStore.getInstance();

    /* JADX WARN: Multi-variable type inference failed */
    @Override // javax.crypto.SecretKeyFactorySpi
    protected KeySpec engineGetKeySpec(SecretKey secretKey, Class keySpecClass) throws InvalidKeySpecException {
        if (keySpecClass == null) {
            throw new InvalidKeySpecException("keySpecClass == null");
        }
        if (!(secretKey instanceof AndroidKeyStoreSecretKey)) {
            throw new InvalidKeySpecException("Only Android KeyStore secret keys supported: " + (secretKey != 0 ? secretKey.getClass().getName() : SemCapabilities.FEATURE_TAG_NULL));
        }
        if (SecretKeySpec.class.isAssignableFrom(keySpecClass)) {
            throw new InvalidKeySpecException("Key material export of Android KeyStore keys is not supported");
        }
        if (!KeyInfo.class.equals(keySpecClass)) {
            throw new InvalidKeySpecException("Unsupported key spec: " + keySpecClass.getName());
        }
        AndroidKeyStoreKey keystoreKey = (AndroidKeyStoreKey) secretKey;
        return getKeyInfo(keystoreKey);
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:12:0x0053. Please report as an issue. */
    public static KeyInfo getKeyInfo(AndroidKeyStoreKey key) {
        Date keyValidityStart;
        Date keyValidityForOriginationEnd;
        Date keyValidityForConsumptionEnd;
        int securityLevel;
        int origin = -1;
        int keySize = -1;
        int purposes = 0;
        List<String> digestsList = new ArrayList<>();
        List<String> blockModesList = new ArrayList<>();
        int keymasterSwEnforcedUserAuthenticators = 0;
        int keymasterHwEnforcedUserAuthenticators = 0;
        List<BigInteger> keymasterSecureUserIds = new ArrayList<>();
        List<String> encryptionPaddingsList = new ArrayList<>();
        List<String> signaturePaddingsList = new ArrayList<>();
        Date keyValidityStart2 = null;
        Date keyValidityForOriginationEnd2 = null;
        Date keyValidityForConsumptionEnd2 = null;
        boolean userAuthenticationRequired = true;
        boolean userAuthenticationValidWhileOnBody = false;
        boolean trustedUserPresenceRequired = false;
        boolean trustedUserConfirmationRequired = false;
        int remainingUsageCount = -1;
        try {
            Authorization[] authorizations = key.getAuthorizations();
            int securityLevel2 = 0;
            try {
                int securityLevel3 = authorizations.length;
                boolean insideSecureHardware = false;
                long userAuthenticationValidityDurationSeconds = 0;
                int i = 0;
                while (i < securityLevel3) {
                    try {
                        Authorization a = authorizations[i];
                        Authorization[] authorizationArr = authorizations;
                        int i2 = securityLevel3;
                        switch (a.keyParameter.tag) {
                            case -1610612234:
                                keyValidityStart = keyValidityStart2;
                                keyValidityForOriginationEnd = keyValidityForOriginationEnd2;
                                keyValidityForConsumptionEnd = keyValidityForConsumptionEnd2;
                                keymasterSecureUserIds.add(KeymasterArguments.toUint64(a.keyParameter.value.getLongInteger()));
                                keyValidityForConsumptionEnd2 = keyValidityForConsumptionEnd;
                                keyValidityForOriginationEnd2 = keyValidityForOriginationEnd;
                                keyValidityStart2 = keyValidityStart;
                                i++;
                                securityLevel3 = i2;
                                authorizations = authorizationArr;
                            case 268435960:
                                Date keyValidityStart3 = keyValidityStart2;
                                Date keyValidityForOriginationEnd3 = keyValidityForOriginationEnd2;
                                Date keyValidityForConsumptionEnd3 = keyValidityForConsumptionEnd2;
                                int authenticatorType = a.keyParameter.value.getHardwareAuthenticatorType();
                                if (KeyStore2ParameterUtils.isSecureHardware(a.securityLevel)) {
                                    keymasterHwEnforcedUserAuthenticators = authenticatorType;
                                    keyValidityForConsumptionEnd2 = keyValidityForConsumptionEnd3;
                                    keyValidityForOriginationEnd2 = keyValidityForOriginationEnd3;
                                    keyValidityStart2 = keyValidityStart3;
                                } else {
                                    keymasterSwEnforcedUserAuthenticators = authenticatorType;
                                    keyValidityForConsumptionEnd2 = keyValidityForConsumptionEnd3;
                                    keyValidityForOriginationEnd2 = keyValidityForOriginationEnd3;
                                    keyValidityStart2 = keyValidityStart3;
                                }
                                i++;
                                securityLevel3 = i2;
                                authorizations = authorizationArr;
                            case 268436158:
                                Date keyValidityStart4 = keyValidityStart2;
                                Date keyValidityForOriginationEnd4 = keyValidityForOriginationEnd2;
                                Date keyValidityForConsumptionEnd4 = keyValidityForConsumptionEnd2;
                                boolean insideSecureHardware2 = KeyStore2ParameterUtils.isSecureHardware(a.securityLevel);
                                try {
                                    securityLevel = a.securityLevel;
                                } catch (IllegalArgumentException e) {
                                    e = e;
                                }
                                try {
                                    int origin2 = KeyProperties.Origin.fromKeymaster(a.keyParameter.value.getOrigin());
                                    origin = origin2;
                                    insideSecureHardware = insideSecureHardware2;
                                    securityLevel2 = securityLevel;
                                    keyValidityForConsumptionEnd2 = keyValidityForConsumptionEnd4;
                                    keyValidityForOriginationEnd2 = keyValidityForOriginationEnd4;
                                    keyValidityStart2 = keyValidityStart4;
                                    i++;
                                    securityLevel3 = i2;
                                    authorizations = authorizationArr;
                                } catch (IllegalArgumentException e2) {
                                    e = e2;
                                    throw new ProviderException("Unsupported key characteristic", e);
                                }
                            case 536870913:
                                purposes = KeyProperties.Purpose.fromKeymaster(a.keyParameter.value.getKeyPurpose()) | purposes;
                                keyValidityForConsumptionEnd2 = keyValidityForConsumptionEnd2;
                                keyValidityForOriginationEnd2 = keyValidityForOriginationEnd2;
                                keyValidityStart2 = keyValidityStart2;
                                i++;
                                securityLevel3 = i2;
                                authorizations = authorizationArr;
                            case 536870916:
                                keyValidityStart = keyValidityStart2;
                                keyValidityForOriginationEnd = keyValidityForOriginationEnd2;
                                keyValidityForConsumptionEnd = keyValidityForConsumptionEnd2;
                                blockModesList.add(KeyProperties.BlockMode.fromKeymaster(a.keyParameter.value.getBlockMode()));
                                keyValidityForConsumptionEnd2 = keyValidityForConsumptionEnd;
                                keyValidityForOriginationEnd2 = keyValidityForOriginationEnd;
                                keyValidityStart2 = keyValidityStart;
                                i++;
                                securityLevel3 = i2;
                                authorizations = authorizationArr;
                            case 536870917:
                                keyValidityStart = keyValidityStart2;
                                keyValidityForOriginationEnd = keyValidityForOriginationEnd2;
                                keyValidityForConsumptionEnd = keyValidityForConsumptionEnd2;
                                digestsList.add(KeyProperties.Digest.fromKeymaster(a.keyParameter.value.getDigest()));
                                keyValidityForConsumptionEnd2 = keyValidityForConsumptionEnd;
                                keyValidityForOriginationEnd2 = keyValidityForOriginationEnd;
                                keyValidityStart2 = keyValidityStart;
                                i++;
                                securityLevel3 = i2;
                                authorizations = authorizationArr;
                            case 536870918:
                                keyValidityStart = keyValidityStart2;
                                keyValidityForOriginationEnd = keyValidityForOriginationEnd2;
                                keyValidityForConsumptionEnd = keyValidityForConsumptionEnd2;
                                int paddingMode = a.keyParameter.value.getPaddingMode();
                                if (paddingMode == 5 || paddingMode == 3) {
                                    String padding = KeyProperties.SignaturePadding.fromKeymaster(paddingMode);
                                    signaturePaddingsList.add(padding);
                                } else {
                                    try {
                                        String jcaPadding = KeyProperties.EncryptionPadding.fromKeymaster(paddingMode);
                                        encryptionPaddingsList.add(jcaPadding);
                                    } catch (IllegalArgumentException e3) {
                                        throw new ProviderException("Unsupported padding: " + paddingMode);
                                    }
                                }
                                keyValidityForConsumptionEnd2 = keyValidityForConsumptionEnd;
                                keyValidityForOriginationEnd2 = keyValidityForOriginationEnd;
                                keyValidityStart2 = keyValidityStart;
                                i++;
                                securityLevel3 = i2;
                                authorizations = authorizationArr;
                                break;
                            case 805306371:
                                Date keyValidityStart5 = keyValidityStart2;
                                Date keyValidityForOriginationEnd5 = keyValidityForOriginationEnd2;
                                Date keyValidityForConsumptionEnd5 = keyValidityForConsumptionEnd2;
                                long keySizeUnsigned = KeyStore2ParameterUtils.getUnsignedInt(a);
                                if (keySizeUnsigned > 2147483647L) {
                                    throw new ProviderException("Key too large: " + keySizeUnsigned + " bits");
                                }
                                int keySize2 = (int) keySizeUnsigned;
                                keySize = keySize2;
                                keyValidityForConsumptionEnd2 = keyValidityForConsumptionEnd5;
                                keyValidityForOriginationEnd2 = keyValidityForOriginationEnd5;
                                keyValidityStart2 = keyValidityStart5;
                                i++;
                                securityLevel3 = i2;
                                authorizations = authorizationArr;
                            case 805306773:
                                keyValidityStart = keyValidityStart2;
                                keyValidityForOriginationEnd = keyValidityForOriginationEnd2;
                                keyValidityForConsumptionEnd = keyValidityForConsumptionEnd2;
                                try {
                                    long remainingUsageCountUnsigned = KeyStore2ParameterUtils.getUnsignedInt(a);
                                    if (remainingUsageCountUnsigned > 2147483647L) {
                                        throw new ProviderException("Usage count of limited use key too long: " + remainingUsageCountUnsigned);
                                    }
                                    remainingUsageCount = (int) remainingUsageCountUnsigned;
                                    keyValidityForConsumptionEnd2 = keyValidityForConsumptionEnd;
                                    keyValidityForOriginationEnd2 = keyValidityForOriginationEnd;
                                    keyValidityStart2 = keyValidityStart;
                                    i++;
                                    securityLevel3 = i2;
                                    authorizations = authorizationArr;
                                } catch (IllegalArgumentException e4) {
                                    e = e4;
                                    throw new ProviderException("Unsupported key characteristic", e);
                                }
                            case 805306873:
                                long userAuthenticationValidityDurationSeconds2 = KeyStore2ParameterUtils.getUnsignedInt(a);
                                Date keyValidityForOriginationEnd6 = keyValidityForOriginationEnd2;
                                Date keyValidityForConsumptionEnd6 = keyValidityForConsumptionEnd2;
                                if (userAuthenticationValidityDurationSeconds2 <= 2147483647L) {
                                    userAuthenticationValidityDurationSeconds = userAuthenticationValidityDurationSeconds2;
                                    keyValidityForConsumptionEnd2 = keyValidityForConsumptionEnd6;
                                    keyValidityForOriginationEnd2 = keyValidityForOriginationEnd6;
                                    i++;
                                    securityLevel3 = i2;
                                    authorizations = authorizationArr;
                                } else {
                                    try {
                                        try {
                                            throw new ProviderException("User authentication timeout validity too long: " + userAuthenticationValidityDurationSeconds2 + " seconds");
                                        } catch (IllegalArgumentException e5) {
                                            e = e5;
                                            throw new ProviderException("Unsupported key characteristic", e);
                                        }
                                    } catch (IllegalArgumentException e6) {
                                        e = e6;
                                    }
                                }
                            case 1610613136:
                                Date keyValidityStart6 = KeyStore2ParameterUtils.getDate(a);
                                keyValidityStart2 = keyValidityStart6;
                                i++;
                                securityLevel3 = i2;
                                authorizations = authorizationArr;
                            case 1610613137:
                                Date keyValidityForOriginationEnd7 = KeyStore2ParameterUtils.getDate(a);
                                keyValidityForOriginationEnd2 = keyValidityForOriginationEnd7;
                                i++;
                                securityLevel3 = i2;
                                authorizations = authorizationArr;
                            case 1610613138:
                                Date keyValidityForConsumptionEnd7 = KeyStore2ParameterUtils.getDate(a);
                                keyValidityForConsumptionEnd2 = keyValidityForConsumptionEnd7;
                                i++;
                                securityLevel3 = i2;
                                authorizations = authorizationArr;
                            case 1879048695:
                                userAuthenticationRequired = false;
                                i++;
                                securityLevel3 = i2;
                                authorizations = authorizationArr;
                            case 1879048698:
                                boolean userAuthenticationValidWhileOnBody2 = KeyStore2ParameterUtils.isSecureHardware(a.securityLevel);
                                userAuthenticationValidWhileOnBody = userAuthenticationValidWhileOnBody2;
                                i++;
                                securityLevel3 = i2;
                                authorizations = authorizationArr;
                            case 1879048699:
                                boolean trustedUserPresenceRequired2 = KeyStore2ParameterUtils.isSecureHardware(a.securityLevel);
                                trustedUserPresenceRequired = trustedUserPresenceRequired2;
                                i++;
                                securityLevel3 = i2;
                                authorizations = authorizationArr;
                            case 1879048700:
                                try {
                                    boolean trustedUserConfirmationRequired2 = KeyStore2ParameterUtils.isSecureHardware(a.securityLevel);
                                    trustedUserConfirmationRequired = trustedUserConfirmationRequired2;
                                    i++;
                                    securityLevel3 = i2;
                                    authorizations = authorizationArr;
                                } catch (IllegalArgumentException e7) {
                                    e = e7;
                                    throw new ProviderException("Unsupported key characteristic", e);
                                }
                            default:
                                keyValidityStart = keyValidityStart2;
                                keyValidityForOriginationEnd = keyValidityForOriginationEnd2;
                                keyValidityForConsumptionEnd = keyValidityForConsumptionEnd2;
                                keyValidityForConsumptionEnd2 = keyValidityForConsumptionEnd;
                                keyValidityForOriginationEnd2 = keyValidityForOriginationEnd;
                                keyValidityStart2 = keyValidityStart;
                                i++;
                                securityLevel3 = i2;
                                authorizations = authorizationArr;
                        }
                    } catch (IllegalArgumentException e8) {
                        e = e8;
                    }
                }
                Date keyValidityStart7 = keyValidityStart2;
                Date keyValidityForOriginationEnd8 = keyValidityForOriginationEnd2;
                Date keyValidityForConsumptionEnd8 = keyValidityForConsumptionEnd2;
                if (keySize == -1) {
                    throw new ProviderException("Key size not available");
                }
                if (origin == -1) {
                    throw new ProviderException("Key origin not available");
                }
                String[] encryptionPaddings = (String[]) encryptionPaddingsList.toArray(new String[0]);
                String[] signaturePaddings = (String[]) signaturePaddingsList.toArray(new String[0]);
                boolean userAuthenticationRequirementEnforcedBySecureHardware = userAuthenticationRequired && keymasterHwEnforcedUserAuthenticators != 0 && keymasterSwEnforcedUserAuthenticators == 0;
                String[] digests = (String[]) digestsList.toArray(new String[0]);
                String[] blockModes = (String[]) blockModesList.toArray(new String[0]);
                boolean invalidatedByBiometricEnrollment = false;
                if (keymasterSwEnforcedUserAuthenticators == 2 || keymasterHwEnforcedUserAuthenticators == 2) {
                    invalidatedByBiometricEnrollment = (keymasterSecureUserIds.isEmpty() || keymasterSecureUserIds.contains(getGateKeeperSecureUserId())) ? false : true;
                }
                long userAuthenticationValidityDurationSeconds3 = userAuthenticationValidityDurationSeconds;
                int keymasterHwEnforcedUserAuthenticators2 = keymasterHwEnforcedUserAuthenticators;
                int keymasterHwEnforcedUserAuthenticators3 = (int) userAuthenticationValidityDurationSeconds3;
                return new KeyInfo(key.getUserKeyDescriptor().alias, insideSecureHardware, origin, keySize, keyValidityStart7, keyValidityForOriginationEnd8, keyValidityForConsumptionEnd8, purposes, encryptionPaddings, signaturePaddings, digests, blockModes, userAuthenticationRequired, keymasterHwEnforcedUserAuthenticators3, userAuthenticationRequirementEnforcedBySecureHardware ? keymasterHwEnforcedUserAuthenticators2 : keymasterSwEnforcedUserAuthenticators, userAuthenticationRequirementEnforcedBySecureHardware, userAuthenticationValidWhileOnBody, trustedUserPresenceRequired, invalidatedByBiometricEnrollment, trustedUserConfirmationRequired, securityLevel2, remainingUsageCount);
            } catch (IllegalArgumentException e9) {
                e = e9;
            }
        } catch (IllegalArgumentException e10) {
            e = e10;
        }
    }

    private static BigInteger getGateKeeperSecureUserId() throws ProviderException {
        try {
            return BigInteger.valueOf(GateKeeper.getSecureUserId());
        } catch (IllegalStateException e) {
            throw new ProviderException("Failed to get GateKeeper secure user ID", e);
        }
    }

    @Override // javax.crypto.SecretKeyFactorySpi
    protected SecretKey engineGenerateSecret(KeySpec keySpec) throws InvalidKeySpecException {
        throw new InvalidKeySpecException("To generate secret key in Android Keystore, use KeyGenerator initialized with " + KeyGenParameterSpec.class.getName());
    }

    @Override // javax.crypto.SecretKeyFactorySpi
    protected SecretKey engineTranslateKey(SecretKey key) throws InvalidKeyException {
        if (key == null) {
            throw new InvalidKeyException("key == null");
        }
        if (!(key instanceof AndroidKeyStoreSecretKey)) {
            throw new InvalidKeyException("To import a secret key into Android Keystore, use KeyStore.setEntry");
        }
        return key;
    }
}
