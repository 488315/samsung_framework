package android.security.keystore2;

import android.app.AppGlobals;
import android.hardware.security.keymint.KeyParameter;
import android.os.Build;
import android.os.StrictMode;
import android.security.Flags;
import android.security.KeyPairGeneratorSpec;
import android.security.KeyStore2;
import android.security.KeyStoreException;
import android.security.KeyStoreSecurityLevel;
import android.security.keymaster.KeymasterArguments;
import android.security.keystore.ArrayUtils;
import android.security.keystore.DeviceIdAttestationException;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.security.keystore.SecureKeyImportUnavailableException;
import android.security.keystore.StrongBoxUnavailableException;
import android.system.keystore2.Authorization;
import android.system.keystore2.KeyDescriptor;
import android.system.keystore2.KeyEntryResponse;
import android.system.keystore2.KeyMetadata;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Log;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGeneratorSpi;
import java.security.ProviderException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.NamedParameterSpec;
import java.security.spec.RSAKeyGenParameterSpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public abstract class AndroidKeyStoreKeyPairGeneratorSpi extends KeyPairGeneratorSpi {
    private static final int ALGORITHM_ED25519 = 1204;
    private static final int ALGORITHM_XDH = 1203;
    private static final int EC_DEFAULT_KEY_SIZE = 256;
    private static final int RSA_DEFAULT_KEY_SIZE = 2048;
    private static final int RSA_MAX_KEY_SIZE = 8192;
    private static final int RSA_MIN_KEY_SIZE = 512;
    private static final String TAG = "AndroidKeyStoreKeyPairGeneratorSpi";
    private KeyDescriptor mAttestKeyDescriptor;
    private String mEcCurveName;
    private String mEntryAlias;
    private int mEntryNamespace;
    private String mJcaKeyAlgorithm;
    private int mKeySizeBits;
    private KeyStore2 mKeyStore;
    private int mKeymasterAlgorithm = -1;
    private int[] mKeymasterBlockModes;
    private int[] mKeymasterDigests;
    private int[] mKeymasterEncryptionPaddings;
    private int[] mKeymasterMgf1Digests;
    private int[] mKeymasterPurposes;
    private int[] mKeymasterSignaturePaddings;
    private final int mOriginalKeymasterAlgorithm;
    private Long mRSAPublicExponent;
    private SecureRandom mRng;
    private KeyGenParameterSpec mSpec;
    private static final Map<String, Integer> SUPPORTED_EC_CURVE_NAME_TO_SIZE = new HashMap();
    private static final List<String> SUPPORTED_EC_CURVE_NAMES = new ArrayList();
    private static final List<Integer> SUPPORTED_EC_CURVE_SIZES = new ArrayList();
    private static final String CURVE_X_25519 = NamedParameterSpec.X25519.getName();
    private static final String CURVE_ED_25519 = NamedParameterSpec.ED25519.getName();

    public static class RSA extends AndroidKeyStoreKeyPairGeneratorSpi {
        public RSA() {
            super(1);
        }
    }

    public static class EC extends AndroidKeyStoreKeyPairGeneratorSpi {
        public EC() {
            super(3);
        }
    }

    public static class XDH extends AndroidKeyStoreKeyPairGeneratorSpi {
        public XDH() {
            super(1203);
        }
    }

    public static class ED25519 extends AndroidKeyStoreKeyPairGeneratorSpi {
        public ED25519() {
            super(1204);
        }
    }

    static {
        SUPPORTED_EC_CURVE_NAME_TO_SIZE.put("p-224", 224);
        SUPPORTED_EC_CURVE_NAME_TO_SIZE.put("secp224r1", 224);
        SUPPORTED_EC_CURVE_NAME_TO_SIZE.put("p-256", 256);
        SUPPORTED_EC_CURVE_NAME_TO_SIZE.put("secp256r1", 256);
        SUPPORTED_EC_CURVE_NAME_TO_SIZE.put("prime256v1", 256);
        SUPPORTED_EC_CURVE_NAME_TO_SIZE.put(CURVE_X_25519.toLowerCase(Locale.US), 256);
        SUPPORTED_EC_CURVE_NAME_TO_SIZE.put(CURVE_ED_25519.toLowerCase(Locale.US), 256);
        SUPPORTED_EC_CURVE_NAME_TO_SIZE.put("p-384", 384);
        SUPPORTED_EC_CURVE_NAME_TO_SIZE.put("secp384r1", 384);
        SUPPORTED_EC_CURVE_NAME_TO_SIZE.put("p-521", 521);
        SUPPORTED_EC_CURVE_NAME_TO_SIZE.put("secp521r1", 521);
        SUPPORTED_EC_CURVE_NAMES.addAll(SUPPORTED_EC_CURVE_NAME_TO_SIZE.keySet());
        Collections.sort(SUPPORTED_EC_CURVE_NAMES);
        SUPPORTED_EC_CURVE_SIZES.addAll(new HashSet(SUPPORTED_EC_CURVE_NAME_TO_SIZE.values()));
        Collections.sort(SUPPORTED_EC_CURVE_SIZES);
    }

    protected AndroidKeyStoreKeyPairGeneratorSpi(int keymasterAlgorithm) {
        this.mOriginalKeymasterAlgorithm = keymasterAlgorithm;
    }

    private static int keySizeAndNameToEcCurve(int keySizeBits, String ecCurveName) throws InvalidAlgorithmParameterException {
        switch (keySizeBits) {
            case 224:
                return 0;
            case 256:
                if (isCurve25519(ecCurveName)) {
                    return 4;
                }
                return 1;
            case 384:
                return 2;
            case 521:
                return 3;
            default:
                throw new InvalidAlgorithmParameterException("Unsupported EC curve keysize: " + keySizeBits);
        }
    }

    @Override // java.security.KeyPairGeneratorSpi
    public void initialize(int keysize, SecureRandom random) {
        throw new IllegalArgumentException(KeyGenParameterSpec.class.getName() + " or " + KeyPairGeneratorSpec.class.getName() + " required to initialize this KeyPairGenerator");
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x001e A[Catch: all -> 0x022e, TryCatch #2 {all -> 0x022e, blocks: (B:5:0x0009, B:7:0x000f, B:10:0x0016, B:11:0x001a, B:13:0x001e, B:14:0x0034, B:16:0x0052, B:17:0x0058, B:23:0x006b, B:25:0x0096, B:27:0x009c, B:29:0x00a2, B:31:0x00ab, B:33:0x00ae, B:34:0x00e0, B:36:0x00e1, B:38:0x00f2, B:39:0x0101, B:41:0x0107, B:42:0x0118, B:44:0x011e, B:47:0x013d, B:48:0x0148, B:54:0x0131, B:55:0x00fd, B:19:0x016c, B:20:0x0173, B:57:0x0166, B:58:0x016b, B:59:0x0022, B:61:0x0026, B:63:0x0029, B:67:0x0175, B:68:0x017a, B:69:0x017b, B:71:0x017f, B:73:0x0192, B:76:0x01a3, B:77:0x01bf, B:78:0x01c0, B:79:0x01ec, B:80:0x01ed, B:81:0x022d, B:83:0x0230, B:84:0x025c), top: B:2:0x0006, inners: #0, #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0052 A[Catch: all -> 0x022e, TryCatch #2 {all -> 0x022e, blocks: (B:5:0x0009, B:7:0x000f, B:10:0x0016, B:11:0x001a, B:13:0x001e, B:14:0x0034, B:16:0x0052, B:17:0x0058, B:23:0x006b, B:25:0x0096, B:27:0x009c, B:29:0x00a2, B:31:0x00ab, B:33:0x00ae, B:34:0x00e0, B:36:0x00e1, B:38:0x00f2, B:39:0x0101, B:41:0x0107, B:42:0x0118, B:44:0x011e, B:47:0x013d, B:48:0x0148, B:54:0x0131, B:55:0x00fd, B:19:0x016c, B:20:0x0173, B:57:0x0166, B:58:0x016b, B:59:0x0022, B:61:0x0026, B:63:0x0029, B:67:0x0175, B:68:0x017a, B:69:0x017b, B:71:0x017f, B:73:0x0192, B:76:0x01a3, B:77:0x01bf, B:78:0x01c0, B:79:0x01ec, B:80:0x01ed, B:81:0x022d, B:83:0x0230, B:84:0x025c), top: B:2:0x0006, inners: #0, #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:19:0x016c A[Catch: all -> 0x022e, TryCatch #2 {all -> 0x022e, blocks: (B:5:0x0009, B:7:0x000f, B:10:0x0016, B:11:0x001a, B:13:0x001e, B:14:0x0034, B:16:0x0052, B:17:0x0058, B:23:0x006b, B:25:0x0096, B:27:0x009c, B:29:0x00a2, B:31:0x00ab, B:33:0x00ae, B:34:0x00e0, B:36:0x00e1, B:38:0x00f2, B:39:0x0101, B:41:0x0107, B:42:0x0118, B:44:0x011e, B:47:0x013d, B:48:0x0148, B:54:0x0131, B:55:0x00fd, B:19:0x016c, B:20:0x0173, B:57:0x0166, B:58:0x016b, B:59:0x0022, B:61:0x0026, B:63:0x0029, B:67:0x0175, B:68:0x017a, B:69:0x017b, B:71:0x017f, B:73:0x0192, B:76:0x01a3, B:77:0x01bf, B:78:0x01c0, B:79:0x01ec, B:80:0x01ed, B:81:0x022d, B:83:0x0230, B:84:0x025c), top: B:2:0x0006, inners: #0, #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:22:0x006b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0022 A[Catch: all -> 0x022e, TryCatch #2 {all -> 0x022e, blocks: (B:5:0x0009, B:7:0x000f, B:10:0x0016, B:11:0x001a, B:13:0x001e, B:14:0x0034, B:16:0x0052, B:17:0x0058, B:23:0x006b, B:25:0x0096, B:27:0x009c, B:29:0x00a2, B:31:0x00ab, B:33:0x00ae, B:34:0x00e0, B:36:0x00e1, B:38:0x00f2, B:39:0x0101, B:41:0x0107, B:42:0x0118, B:44:0x011e, B:47:0x013d, B:48:0x0148, B:54:0x0131, B:55:0x00fd, B:19:0x016c, B:20:0x0173, B:57:0x0166, B:58:0x016b, B:59:0x0022, B:61:0x0026, B:63:0x0029, B:67:0x0175, B:68:0x017a, B:69:0x017b, B:71:0x017f, B:73:0x0192, B:76:0x01a3, B:77:0x01bf, B:78:0x01c0, B:79:0x01ec, B:80:0x01ed, B:81:0x022d, B:83:0x0230, B:84:0x025c), top: B:2:0x0006, inners: #0, #1 }] */
    @Override // java.security.KeyPairGeneratorSpi
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void initialize(java.security.spec.AlgorithmParameterSpec r12, java.security.SecureRandom r13) throws java.security.InvalidAlgorithmParameterException {
        /*
            Method dump skipped, instructions count: 611
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.security.keystore2.AndroidKeyStoreKeyPairGeneratorSpi.initialize(java.security.spec.AlgorithmParameterSpec, java.security.SecureRandom):void");
    }

    private void checkAttestKeyPurpose(KeyGenParameterSpec spec) throws InvalidAlgorithmParameterException {
        if ((spec.getPurposes() & 128) != 0 && spec.getPurposes() != 128) {
            throw new InvalidAlgorithmParameterException("PURPOSE_ATTEST_KEY may not be specified with any other purposes");
        }
    }

    private void checkCorrectKeyPurposeForCurve(KeyGenParameterSpec spec) throws InvalidAlgorithmParameterException {
        if (!isCurve25519(this.mEcCurveName)) {
            return;
        }
        if (this.mEcCurveName.equalsIgnoreCase(CURVE_X_25519) && spec.getPurposes() != 64) {
            throw new InvalidAlgorithmParameterException("x25519 may only be used for key agreement.");
        }
        if (this.mEcCurveName.equalsIgnoreCase(CURVE_ED_25519) && !hasOnlyAllowedPurposeForEd25519(spec.getPurposes())) {
            throw new InvalidAlgorithmParameterException("ed25519 may not be used for key agreement.");
        }
    }

    private static boolean isCurve25519(String ecCurveName) {
        if (ecCurveName == null) {
            return false;
        }
        return ecCurveName.equalsIgnoreCase(CURVE_X_25519) || ecCurveName.equalsIgnoreCase(CURVE_ED_25519);
    }

    private static boolean hasOnlyAllowedPurposeForEd25519(int purpose) {
        boolean hasAllowedPurpose = (purpose & 140) != 0;
        boolean hasDisallowedPurpose = (purpose & (-141)) != 0;
        return hasAllowedPurpose && !hasDisallowedPurpose;
    }

    private KeyDescriptor buildAndCheckAttestKeyDescriptor(KeyGenParameterSpec spec) throws InvalidAlgorithmParameterException {
        if (spec.getAttestKeyAlias() != null) {
            KeyDescriptor attestKeyDescriptor = new KeyDescriptor();
            attestKeyDescriptor.domain = 0;
            attestKeyDescriptor.alias = spec.getAttestKeyAlias();
            try {
                KeyEntryResponse attestKey = this.mKeyStore.getKeyEntry(attestKeyDescriptor);
                checkAttestKeyChallenge(spec);
                checkAttestKeyPurpose(attestKey.metadata.authorizations);
                checkAttestKeySecurityLevel(spec, attestKey);
                return attestKeyDescriptor;
            } catch (KeyStoreException e) {
                throw new InvalidAlgorithmParameterException("Invalid attestKeyAlias", e);
            }
        }
        return null;
    }

    private void checkAttestKeyChallenge(KeyGenParameterSpec spec) throws InvalidAlgorithmParameterException {
        if (spec.getAttestationChallenge() == null) {
            throw new InvalidAlgorithmParameterException("AttestKey specified but no attestation challenge provided");
        }
    }

    private void checkAttestKeyPurpose(Authorization[] keyAuths) throws InvalidAlgorithmParameterException {
        Predicate<Authorization> isAttestKeyPurpose = new Predicate() { // from class: android.security.keystore2.AndroidKeyStoreKeyPairGeneratorSpi$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return AndroidKeyStoreKeyPairGeneratorSpi.lambda$checkAttestKeyPurpose$0((Authorization) obj);
            }
        };
        if (Arrays.stream(keyAuths).noneMatch(isAttestKeyPurpose)) {
            throw new InvalidAlgorithmParameterException("Invalid attestKey, does not have PURPOSE_ATTEST_KEY");
        }
    }

    static /* synthetic */ boolean lambda$checkAttestKeyPurpose$0(Authorization x) {
        return x.keyParameter.tag == 536870913 && x.keyParameter.value.getKeyPurpose() == 7;
    }

    private void checkAttestKeySecurityLevel(KeyGenParameterSpec spec, KeyEntryResponse key) throws InvalidAlgorithmParameterException {
        boolean attestKeyInStrongBox = key.metadata.keySecurityLevel == 2;
        if (spec.isStrongBoxBacked() != attestKeyInStrongBox) {
            if (attestKeyInStrongBox) {
                throw new InvalidAlgorithmParameterException("Invalid security level: Cannot sign non-StrongBox key with StrongBox attestKey");
            }
            throw new InvalidAlgorithmParameterException("Invalid security level: Cannot sign StrongBox key with non-StrongBox attestKey");
        }
    }

    private int getKeymasterAlgorithmFromLegacy(int keymasterAlgorithm, KeyPairGeneratorSpec legacySpec) throws InvalidAlgorithmParameterException {
        String specKeyAlgorithm = legacySpec.getKeyType();
        if (specKeyAlgorithm != null) {
            try {
                int keymasterAlgorithm2 = KeyProperties.KeyAlgorithm.toKeymasterAsymmetricKeyAlgorithm(specKeyAlgorithm);
                return keymasterAlgorithm2;
            } catch (IllegalArgumentException e) {
                throw new InvalidAlgorithmParameterException("Invalid key type in parameters", e);
            }
        }
        return keymasterAlgorithm;
    }

    private KeyGenParameterSpec buildKeyGenParameterSpecFromLegacy(KeyPairGeneratorSpec legacySpec, int keymasterAlgorithm) {
        KeyGenParameterSpec.Builder specBuilder;
        switch (keymasterAlgorithm) {
            case 1:
                specBuilder = new KeyGenParameterSpec.Builder(legacySpec.getKeystoreAlias(), 15);
                specBuilder.setDigests(KeyProperties.DIGEST_NONE, KeyProperties.DIGEST_MD5, "SHA-1", KeyProperties.DIGEST_SHA224, "SHA-256", KeyProperties.DIGEST_SHA384, KeyProperties.DIGEST_SHA512);
                specBuilder.setEncryptionPaddings("NoPadding", KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1, KeyProperties.ENCRYPTION_PADDING_RSA_OAEP);
                specBuilder.setSignaturePaddings(KeyProperties.SIGNATURE_PADDING_RSA_PKCS1, KeyProperties.SIGNATURE_PADDING_RSA_PSS);
                specBuilder.setRandomizedEncryptionRequired(false);
                break;
            case 2:
            default:
                throw new ProviderException("Unsupported algorithm: " + this.mKeymasterAlgorithm);
            case 3:
                specBuilder = new KeyGenParameterSpec.Builder(legacySpec.getKeystoreAlias(), 12);
                specBuilder.setDigests(KeyProperties.DIGEST_NONE, "SHA-1", KeyProperties.DIGEST_SHA224, "SHA-256", KeyProperties.DIGEST_SHA384, KeyProperties.DIGEST_SHA512);
                break;
        }
        if (legacySpec.getKeySize() != -1) {
            specBuilder.setKeySize(legacySpec.getKeySize());
        }
        if (legacySpec.getAlgorithmParameterSpec() != null) {
            specBuilder.setAlgorithmParameterSpec(legacySpec.getAlgorithmParameterSpec());
        }
        specBuilder.setCertificateSubject(legacySpec.getSubjectDN());
        specBuilder.setCertificateSerialNumber(legacySpec.getSerialNumber());
        specBuilder.setCertificateNotBefore(legacySpec.getStartDate());
        specBuilder.setCertificateNotAfter(legacySpec.getEndDate());
        specBuilder.setUserAuthenticationRequired(false);
        return specBuilder.build();
    }

    private void resetAll() {
        this.mEntryAlias = null;
        this.mEntryNamespace = -1;
        this.mJcaKeyAlgorithm = null;
        this.mKeymasterAlgorithm = -1;
        this.mKeymasterPurposes = null;
        this.mKeymasterBlockModes = null;
        this.mKeymasterEncryptionPaddings = null;
        this.mKeymasterSignaturePaddings = null;
        this.mKeymasterDigests = null;
        this.mKeymasterMgf1Digests = null;
        this.mKeySizeBits = 0;
        this.mSpec = null;
        this.mRSAPublicExponent = null;
        this.mRng = null;
        this.mKeyStore = null;
        this.mEcCurveName = null;
    }

    private void initAlgorithmSpecificParameters() throws InvalidAlgorithmParameterException {
        AlgorithmParameterSpec algSpecificSpec = this.mSpec.getAlgorithmParameterSpec();
        switch (this.mKeymasterAlgorithm) {
            case 1:
                BigInteger publicExponent = null;
                if (algSpecificSpec instanceof RSAKeyGenParameterSpec) {
                    RSAKeyGenParameterSpec rsaSpec = (RSAKeyGenParameterSpec) algSpecificSpec;
                    if (this.mKeySizeBits == -1) {
                        this.mKeySizeBits = rsaSpec.getKeysize();
                    } else if (this.mKeySizeBits != rsaSpec.getKeysize()) {
                        throw new InvalidAlgorithmParameterException("RSA key size must match  between " + this.mSpec + " and " + algSpecificSpec + ": " + this.mKeySizeBits + " vs " + rsaSpec.getKeysize());
                    }
                    publicExponent = rsaSpec.getPublicExponent();
                } else if (algSpecificSpec != null) {
                    throw new InvalidAlgorithmParameterException("RSA may only use RSAKeyGenParameterSpec");
                }
                if (publicExponent == null) {
                    publicExponent = RSAKeyGenParameterSpec.F4;
                }
                if (publicExponent.compareTo(BigInteger.ZERO) < 1) {
                    throw new InvalidAlgorithmParameterException("RSA public exponent must be positive: " + publicExponent);
                }
                if (publicExponent.signum() == -1 || publicExponent.compareTo(KeymasterArguments.UINT64_MAX_VALUE) > 0) {
                    throw new InvalidAlgorithmParameterException("Unsupported RSA public exponent: " + publicExponent + ". Maximum supported value: " + KeymasterArguments.UINT64_MAX_VALUE);
                }
                this.mRSAPublicExponent = Long.valueOf(publicExponent.longValue());
                return;
            case 2:
            default:
                throw new ProviderException("Unsupported algorithm: " + this.mKeymasterAlgorithm);
            case 3:
                if (algSpecificSpec instanceof ECGenParameterSpec) {
                    ECGenParameterSpec ecSpec = (ECGenParameterSpec) algSpecificSpec;
                    this.mEcCurveName = ecSpec.getName();
                    if (this.mOriginalKeymasterAlgorithm == 1203 && !this.mEcCurveName.equalsIgnoreCase("x25519")) {
                        throw new InvalidAlgorithmParameterException("XDH algorithm only supports x25519 curve.");
                    }
                    if (this.mOriginalKeymasterAlgorithm == 1204 && !this.mEcCurveName.equalsIgnoreCase("ed25519")) {
                        throw new InvalidAlgorithmParameterException("Ed25519 algorithm only supports ed25519 curve.");
                    }
                    Integer ecSpecKeySizeBits = SUPPORTED_EC_CURVE_NAME_TO_SIZE.get(this.mEcCurveName.toLowerCase(Locale.US));
                    if (ecSpecKeySizeBits == null) {
                        throw new InvalidAlgorithmParameterException("Unsupported EC curve name: " + this.mEcCurveName + ". Supported: " + SUPPORTED_EC_CURVE_NAMES);
                    }
                    if (this.mKeySizeBits == -1) {
                        this.mKeySizeBits = ecSpecKeySizeBits.intValue();
                        return;
                    } else {
                        if (this.mKeySizeBits != ecSpecKeySizeBits.intValue()) {
                            throw new InvalidAlgorithmParameterException("EC key size must match  between " + this.mSpec + " and " + algSpecificSpec + ": " + this.mKeySizeBits + " vs " + ecSpecKeySizeBits);
                        }
                        return;
                    }
                }
                if (algSpecificSpec != null) {
                    throw new InvalidAlgorithmParameterException("EC may only use ECGenParameterSpec");
                }
                return;
        }
    }

    @Override // java.security.KeyPairGeneratorSpi
    public KeyPair generateKeyPair() {
        int securityLevel;
        int flags;
        StrictMode.noteSlowCall("generateKeyPair");
        if (this.mKeyStore == null || this.mSpec == null) {
            throw new IllegalStateException("Not initialized");
        }
        if (this.mSpec.isStrongBoxBacked()) {
            securityLevel = 2;
        } else {
            securityLevel = 1;
        }
        if (this.mSpec.isCriticalToDeviceEncryption()) {
            flags = 1;
        } else {
            flags = 0;
        }
        byte[] additionalEntropy = KeyStoreCryptoOperationUtils.getRandomBytesToMixIntoKeystoreRng(this.mRng, (this.mKeySizeBits + 7) / 8);
        KeyDescriptor descriptor = new KeyDescriptor();
        descriptor.alias = this.mEntryAlias;
        descriptor.domain = this.mEntryNamespace == -1 ? 0 : 2;
        descriptor.nspace = this.mEntryNamespace;
        descriptor.blob = null;
        boolean success = false;
        try {
            try {
                KeyStoreSecurityLevel iSecurityLevel = this.mKeyStore.getSecurityLevel(securityLevel);
                KeyMetadata metadata = iSecurityLevel.generateKey(descriptor, this.mAttestKeyDescriptor, constructKeyGenerationArguments(), flags, additionalEntropy);
                AndroidKeyStorePublicKey publicKey = AndroidKeyStoreProvider.makeAndroidKeyStorePublicKeyFromKeyEntryResponse(descriptor, metadata, iSecurityLevel, this.mKeymasterAlgorithm);
                success = true;
                return new KeyPair(publicKey, publicKey.getPrivateKey());
            } catch (KeyStoreException e) {
                switch (e.getErrorCode()) {
                    case -68:
                        throw new StrongBoxUnavailableException("Failed to generated key pair.", e);
                    default:
                        ProviderException p = new ProviderException("Failed to generate key pair.", e);
                        if ((this.mSpec.getPurposes() & 32) != 0) {
                            throw new SecureKeyImportUnavailableException(p);
                        }
                        throw p;
                }
            } catch (DeviceIdAttestationException | IllegalArgumentException | InvalidAlgorithmParameterException | UnrecoverableKeyException e2) {
                throw new ProviderException("Failed to construct key object from newly generated key pair.", e2);
            }
        } finally {
            if (!success) {
                try {
                    this.mKeyStore.deleteKey(descriptor);
                } catch (KeyStoreException e3) {
                    if (e3.getErrorCode() != 7) {
                        Log.e(TAG, "Failed to delete newly generated key after generation failed unexpectedly.", e3);
                    }
                }
            }
        }
    }

    private void addAttestationParameters(List<KeyParameter> params) throws ProviderException, IllegalArgumentException, DeviceIdAttestationException {
        byte[] challenge = this.mSpec.getAttestationChallenge();
        if (challenge != null) {
            params.add(KeyStore2ParameterUtils.makeBytes(-1879047484, challenge));
            if (this.mSpec.isDevicePropertiesAttestationIncluded()) {
                String platformReportedBrand = isPropertyEmptyOrUnknown(Build.BRAND_FOR_ATTESTATION) ? Build.BRAND : Build.BRAND_FOR_ATTESTATION;
                params.add(KeyStore2ParameterUtils.makeBytes(-1879047482, platformReportedBrand.getBytes(StandardCharsets.UTF_8)));
                String platformReportedDevice = isPropertyEmptyOrUnknown(Build.DEVICE_FOR_ATTESTATION) ? Build.DEVICE : Build.DEVICE_FOR_ATTESTATION;
                params.add(KeyStore2ParameterUtils.makeBytes(-1879047481, platformReportedDevice.getBytes(StandardCharsets.UTF_8)));
                String platformReportedProduct = isPropertyEmptyOrUnknown(Build.PRODUCT_FOR_ATTESTATION) ? Build.PRODUCT : Build.PRODUCT_FOR_ATTESTATION;
                params.add(KeyStore2ParameterUtils.makeBytes(-1879047480, platformReportedProduct.getBytes(StandardCharsets.UTF_8)));
                String platformReportedManufacturer = isPropertyEmptyOrUnknown(Build.MANUFACTURER_FOR_ATTESTATION) ? Build.MANUFACTURER : Build.MANUFACTURER_FOR_ATTESTATION;
                params.add(KeyStore2ParameterUtils.makeBytes(-1879047476, platformReportedManufacturer.getBytes(StandardCharsets.UTF_8)));
                String platformReportedModel = isPropertyEmptyOrUnknown(Build.MODEL_FOR_ATTESTATION) ? Build.MODEL : Build.MODEL_FOR_ATTESTATION;
                params.add(KeyStore2ParameterUtils.makeBytes(-1879047475, platformReportedModel.getBytes(StandardCharsets.UTF_8)));
            }
            int[] idTypes = this.mSpec.getAttestationIds();
            if (idTypes.length == 0) {
                return;
            }
            Set<Integer> idTypesSet = new ArraySet<>(idTypes.length);
            for (int i : idTypes) {
                idTypesSet.add(Integer.valueOf(i));
            }
            TelephonyManager telephonyService = null;
            if ((idTypesSet.contains(2) || idTypesSet.contains(3)) && (telephonyService = (TelephonyManager) AppGlobals.getInitialApplication().getSystemService("phone")) == null) {
                throw new DeviceIdAttestationException("Unable to access telephony service");
            }
            for (Integer idType : idTypesSet) {
                switch (idType.intValue()) {
                    case 1:
                        params.add(KeyStore2ParameterUtils.makeBytes(-1879047479, Build.getSerial().getBytes(StandardCharsets.UTF_8)));
                        break;
                    case 2:
                        String imei = telephonyService.getImei(0);
                        if (imei == null) {
                            throw new DeviceIdAttestationException("Unable to retrieve IMEI");
                        }
                        params.add(KeyStore2ParameterUtils.makeBytes(-1879047478, imei.getBytes(StandardCharsets.UTF_8)));
                        String secondImei = telephonyService.getImei(1);
                        if (TextUtils.isEmpty(secondImei)) {
                            break;
                        } else {
                            params.add(KeyStore2ParameterUtils.makeBytes(-1879047469, secondImei.getBytes(StandardCharsets.UTF_8)));
                            break;
                        }
                    case 3:
                        String meid = telephonyService.getMeid(0);
                        if (meid == null) {
                            throw new DeviceIdAttestationException("Unable to retrieve MEID");
                        }
                        params.add(KeyStore2ParameterUtils.makeBytes(-1879047477, meid.getBytes(StandardCharsets.UTF_8)));
                        break;
                    case 4:
                        params.add(KeyStore2ParameterUtils.makeBool(1879048912));
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown device ID type " + idType);
                }
            }
        }
    }

    private Collection<KeyParameter> constructKeyGenerationArguments() throws DeviceIdAttestationException, IllegalArgumentException, InvalidAlgorithmParameterException {
        final List<KeyParameter> params = new ArrayList<>();
        params.add(KeyStore2ParameterUtils.makeInt(805306371, this.mKeySizeBits));
        params.add(KeyStore2ParameterUtils.makeEnum(268435458, this.mKeymasterAlgorithm));
        if (this.mKeymasterAlgorithm == 3) {
            params.add(KeyStore2ParameterUtils.makeEnum(268435466, keySizeAndNameToEcCurve(this.mKeySizeBits, this.mEcCurveName)));
        }
        ArrayUtils.forEach(this.mKeymasterPurposes, new Consumer() { // from class: android.security.keystore2.AndroidKeyStoreKeyPairGeneratorSpi$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                params.add(KeyStore2ParameterUtils.makeEnum(536870913, ((Integer) obj).intValue()));
            }
        });
        ArrayUtils.forEach(this.mKeymasterBlockModes, new Consumer() { // from class: android.security.keystore2.AndroidKeyStoreKeyPairGeneratorSpi$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                params.add(KeyStore2ParameterUtils.makeEnum(536870916, ((Integer) obj).intValue()));
            }
        });
        ArrayUtils.forEach(this.mKeymasterEncryptionPaddings, new Consumer() { // from class: android.security.keystore2.AndroidKeyStoreKeyPairGeneratorSpi$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                AndroidKeyStoreKeyPairGeneratorSpi.this.lambda$constructKeyGenerationArguments$5(params, (Integer) obj);
            }
        });
        ArrayUtils.forEach(this.mKeymasterSignaturePaddings, new Consumer() { // from class: android.security.keystore2.AndroidKeyStoreKeyPairGeneratorSpi$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                params.add(KeyStore2ParameterUtils.makeEnum(536870918, ((Integer) obj).intValue()));
            }
        });
        ArrayUtils.forEach(this.mKeymasterDigests, new Consumer() { // from class: android.security.keystore2.AndroidKeyStoreKeyPairGeneratorSpi$$ExternalSyntheticLambda7
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                params.add(KeyStore2ParameterUtils.makeEnum(536870917, ((Integer) obj).intValue()));
            }
        });
        KeyStore2ParameterUtils.addUserAuthArgs(params, this.mSpec);
        if (this.mSpec.getKeyValidityStart() != null) {
            params.add(KeyStore2ParameterUtils.makeDate(1610613136, this.mSpec.getKeyValidityStart()));
        }
        if (this.mSpec.getKeyValidityForOriginationEnd() != null) {
            params.add(KeyStore2ParameterUtils.makeDate(1610613137, this.mSpec.getKeyValidityForOriginationEnd()));
        }
        if (this.mSpec.getKeyValidityForConsumptionEnd() != null) {
            params.add(KeyStore2ParameterUtils.makeDate(1610613138, this.mSpec.getKeyValidityForConsumptionEnd()));
        }
        if (this.mSpec.getCertificateNotAfter() != null) {
            params.add(KeyStore2ParameterUtils.makeDate(1610613745, this.mSpec.getCertificateNotAfter()));
        }
        if (this.mSpec.getCertificateNotBefore() != null) {
            params.add(KeyStore2ParameterUtils.makeDate(1610613744, this.mSpec.getCertificateNotBefore()));
        }
        if (this.mSpec.getCertificateSerialNumber() != null) {
            params.add(KeyStore2ParameterUtils.makeBignum(-2147482642, this.mSpec.getCertificateSerialNumber()));
        }
        if (this.mSpec.getCertificateSubject() != null) {
            params.add(KeyStore2ParameterUtils.makeBytes(-1879047185, this.mSpec.getCertificateSubject().getEncoded()));
        }
        if (this.mSpec.getMaxUsageCount() != -1) {
            params.add(KeyStore2ParameterUtils.makeInt(805306773, this.mSpec.getMaxUsageCount()));
        }
        addAlgorithmSpecificParameters(params);
        if (this.mSpec.isUniqueIdIncluded()) {
            params.add(KeyStore2ParameterUtils.makeBool(1879048394));
        }
        addAttestationParameters(params);
        return params;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$constructKeyGenerationArguments$5(final List params, Integer padding) {
        int securityLevel;
        params.add(KeyStore2ParameterUtils.makeEnum(536870918, padding.intValue()));
        if (this.mSpec.isStrongBoxBacked()) {
            securityLevel = 2;
        } else {
            securityLevel = 1;
        }
        if (padding.intValue() == 2 && KeymasterUtils.isKeyMintDevice(securityLevel)) {
            ArrayUtils.forEach(this.mKeymasterMgf1Digests, new Consumer() { // from class: android.security.keystore2.AndroidKeyStoreKeyPairGeneratorSpi$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    params.add(KeyStore2ParameterUtils.makeEnum(536871115, ((Integer) obj).intValue()));
                }
            });
            if (!getMgf1DigestSetterFlag()) {
                final int defaultMgf1Digest = KeyProperties.Digest.toKeymaster("SHA-1");
                ArrayUtils.forEach(this.mKeymasterDigests, new Consumer() { // from class: android.security.keystore2.AndroidKeyStoreKeyPairGeneratorSpi$$ExternalSyntheticLambda1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        AndroidKeyStoreKeyPairGeneratorSpi.lambda$constructKeyGenerationArguments$4(defaultMgf1Digest, params, (Integer) obj);
                    }
                });
            }
        }
    }

    static /* synthetic */ void lambda$constructKeyGenerationArguments$4(int defaultMgf1Digest, List params, Integer digest) {
        if (digest.intValue() != defaultMgf1Digest) {
            params.add(KeyStore2ParameterUtils.makeEnum(536871115, digest.intValue()));
        }
    }

    private static boolean getMgf1DigestSetterFlag() {
        try {
            return Flags.mgf1DigestSetterV2();
        } catch (SecurityException e) {
            Log.w(TAG, "Cannot read MGF1 Digest setter flag value", e);
            return false;
        }
    }

    private void addAlgorithmSpecificParameters(List<KeyParameter> params) {
        switch (this.mKeymasterAlgorithm) {
            case 1:
                params.add(KeyStore2ParameterUtils.makeLong(1342177480, this.mRSAPublicExponent.longValue()));
                return;
            case 2:
            default:
                throw new ProviderException("Unsupported algorithm: " + this.mKeymasterAlgorithm);
            case 3:
                return;
        }
    }

    private static int getDefaultKeySize(int keymasterAlgorithm) {
        switch (keymasterAlgorithm) {
            case 1:
                return 2048;
            case 2:
            default:
                throw new ProviderException("Unsupported algorithm: " + keymasterAlgorithm);
            case 3:
                return 256;
        }
    }

    private static void checkValidKeySize(int keymasterAlgorithm, int keySize, boolean isStrongBoxBacked, String mEcCurveName) throws InvalidAlgorithmParameterException {
        switch (keymasterAlgorithm) {
            case 1:
                if (keySize < 512 || keySize > 8192) {
                    throw new InvalidAlgorithmParameterException("RSA key size must be >= 512 and <= 8192");
                }
                return;
            case 2:
            default:
                throw new ProviderException("Unsupported algorithm: " + keymasterAlgorithm);
            case 3:
                if (isStrongBoxBacked && keySize != 256) {
                    throw new InvalidAlgorithmParameterException("Unsupported StrongBox EC key size: " + keySize + " bits. Supported: 256");
                }
                if (isStrongBoxBacked && isCurve25519(mEcCurveName)) {
                    throw new InvalidAlgorithmParameterException("Unsupported StrongBox EC: " + mEcCurveName);
                }
                if (!SUPPORTED_EC_CURVE_SIZES.contains(Integer.valueOf(keySize))) {
                    throw new InvalidAlgorithmParameterException("Unsupported EC key size: " + keySize + " bits. Supported: " + SUPPORTED_EC_CURVE_SIZES);
                }
                return;
        }
    }

    private static String getCertificateSignatureAlgorithm(int keymasterAlgorithm, int keySizeBits, KeyGenParameterSpec spec) {
        if ((spec.getPurposes() & 4) == 0 || spec.isUserAuthenticationRequired() || !spec.isDigestsSpecified()) {
            return null;
        }
        switch (keymasterAlgorithm) {
            case 1:
                boolean pkcs1SignaturePaddingSupported = com.android.internal.util.ArrayUtils.contains(KeyProperties.SignaturePadding.allToKeymaster(spec.getSignaturePaddings()), 5);
                if (!pkcs1SignaturePaddingSupported) {
                    return null;
                }
                Set<Integer> availableKeymasterDigests = getAvailableKeymasterSignatureDigests(spec.getDigests(), AndroidKeyStoreBCWorkaroundProvider.getSupportedEcdsaSignatureDigests());
                int maxDigestOutputSizeBits = keySizeBits - 240;
                int bestKeymasterDigest = -1;
                int bestDigestOutputSizeBits = -1;
                Iterator<Integer> it = availableKeymasterDigests.iterator();
                while (it.hasNext()) {
                    int keymasterDigest = it.next().intValue();
                    int outputSizeBits = KeymasterUtils.getDigestOutputSizeBits(keymasterDigest);
                    if (outputSizeBits <= maxDigestOutputSizeBits) {
                        if (bestKeymasterDigest == -1) {
                            bestKeymasterDigest = keymasterDigest;
                            bestDigestOutputSizeBits = outputSizeBits;
                        } else if (outputSizeBits > bestDigestOutputSizeBits) {
                            bestKeymasterDigest = keymasterDigest;
                            bestDigestOutputSizeBits = outputSizeBits;
                        }
                    }
                }
                if (bestKeymasterDigest == -1) {
                    return null;
                }
                return KeyProperties.Digest.fromKeymasterToSignatureAlgorithmDigest(bestKeymasterDigest) + "WithRSA";
            case 2:
            default:
                throw new ProviderException("Unsupported algorithm: " + keymasterAlgorithm);
            case 3:
                Set<Integer> availableKeymasterDigests2 = getAvailableKeymasterSignatureDigests(spec.getDigests(), AndroidKeyStoreBCWorkaroundProvider.getSupportedEcdsaSignatureDigests());
                int bestKeymasterDigest2 = -1;
                int bestDigestOutputSizeBits2 = -1;
                Iterator<Integer> it2 = availableKeymasterDigests2.iterator();
                while (true) {
                    if (it2.hasNext()) {
                        int keymasterDigest2 = it2.next().intValue();
                        int outputSizeBits2 = KeymasterUtils.getDigestOutputSizeBits(keymasterDigest2);
                        if (outputSizeBits2 == keySizeBits) {
                            bestKeymasterDigest2 = keymasterDigest2;
                        } else if (bestKeymasterDigest2 == -1) {
                            bestKeymasterDigest2 = keymasterDigest2;
                            bestDigestOutputSizeBits2 = outputSizeBits2;
                        } else if (bestDigestOutputSizeBits2 < keySizeBits) {
                            if (outputSizeBits2 > bestDigestOutputSizeBits2) {
                                bestKeymasterDigest2 = keymasterDigest2;
                                bestDigestOutputSizeBits2 = outputSizeBits2;
                            }
                        } else if (outputSizeBits2 < bestDigestOutputSizeBits2 && outputSizeBits2 >= keySizeBits) {
                            bestKeymasterDigest2 = keymasterDigest2;
                            bestDigestOutputSizeBits2 = outputSizeBits2;
                        }
                    }
                }
                if (bestKeymasterDigest2 == -1) {
                    return null;
                }
                return KeyProperties.Digest.fromKeymasterToSignatureAlgorithmDigest(bestKeymasterDigest2) + "WithECDSA";
        }
    }

    private static Set<Integer> getAvailableKeymasterSignatureDigests(String[] authorizedKeyDigests, String[] supportedSignatureDigests) {
        Set<Integer> authorizedKeymasterKeyDigests = new HashSet<>();
        for (int keymasterDigest : KeyProperties.Digest.allToKeymaster(authorizedKeyDigests)) {
            authorizedKeymasterKeyDigests.add(Integer.valueOf(keymasterDigest));
        }
        Set<Integer> supportedKeymasterSignatureDigests = new HashSet<>();
        for (int keymasterDigest2 : KeyProperties.Digest.allToKeymaster(supportedSignatureDigests)) {
            supportedKeymasterSignatureDigests.add(Integer.valueOf(keymasterDigest2));
        }
        Set<Integer> result = new HashSet<>(supportedKeymasterSignatureDigests);
        result.retainAll(authorizedKeymasterKeyDigests);
        return result;
    }

    private boolean isPropertyEmptyOrUnknown(String property) {
        return TextUtils.isEmpty(property) || property.equals("unknown");
    }
}
