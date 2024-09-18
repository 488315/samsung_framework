package com.samsung.android.knox.dar.ddar.securesession;

import android.security.keystore.KeyProperties;
import com.samsung.android.security.mdf.MdfUtils;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.security.auth.DestroyFailedException;

/* loaded from: classes5.dex */
class SecureSessionManager {
    private static final String CRYPTO_PROVIDER = "AndroidOpenSSL";

    SecureSessionManager() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static class PrivateSessionEndpoint {
        private PrivateKey privateKey;
        private PublicKey publicKey;

        /* JADX INFO: Access modifiers changed from: package-private */
        public PrivateSessionEndpoint() throws Exception {
            try {
                KeyPair keyPair = createKeyPair();
                this.publicKey = keyPair.getPublic();
                this.privateKey = keyPair.getPrivate();
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("Error: PrivateSessionEndpoint creation failure");
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public String getPublicKeyString() {
            return Util.byteArrayToHexString(this.publicKey.getEncoded());
        }

        PublicKey getPublicKey() {
            return this.publicKey;
        }

        PrivateKey getPrivateKey() {
            return this.privateKey;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public void destroy() throws Exception {
            try {
                this.privateKey.destroy();
                this.privateKey = null;
                this.publicKey = null;
            } catch (DestroyFailedException e) {
            }
        }

        private KeyPair createKeyPair() throws Exception {
            KeyPairGenerator generator = KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_EC, SecureSessionManager.CRYPTO_PROVIDER);
            generator.initialize(new ECGenParameterSpec("secp521r1"));
            return generator.generateKeyPair();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static class PublicSessionEndpoint {
        private PublicKey publicKey;

        /* JADX INFO: Access modifiers changed from: package-private */
        public PublicSessionEndpoint(String publicKeyString) throws Exception {
            try {
                this.publicKey = createPublicKey(publicKeyString);
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("Error: PublicSessionEndpoint creation failure");
            }
        }

        String getPublicKeyString() {
            return Util.byteArrayToHexString(this.publicKey.getEncoded());
        }

        PublicKey getPublicKey() {
            return this.publicKey;
        }

        private PublicKey createPublicKey(String publicKeyString) throws Exception {
            byte[] publicKey = Util.fromHexString(publicKeyString);
            X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(publicKey);
            KeyFactory keyFactory = KeyFactory.getInstance(KeyProperties.KEY_ALGORITHM_EC, SecureSessionManager.CRYPTO_PROVIDER);
            PublicKey pubKey = keyFactory.generatePublic(pubKeySpec);
            return pubKey;
        }
    }

    /* loaded from: classes5.dex */
    static class SecureSession {
        private PrivateSessionEndpoint privateSessionEndpoint;
        private PublicSessionEndpoint publicSessionEndpoint;
        private SecretKey sessionKey;
        private byte[] xorMask;

        /* JADX INFO: Access modifiers changed from: package-private */
        public SecureSession(PrivateSessionEndpoint privSessionEndPoint, PublicSessionEndpoint pubSessionEndPoint) throws Exception {
            this.privateSessionEndpoint = privSessionEndPoint;
            this.publicSessionEndpoint = pubSessionEndPoint;
            generateSessionKey();
        }

        private void generateSessionKey() throws Exception {
            KeyAgreement agreement = KeyAgreement.getInstance("ECDH", SecureSessionManager.CRYPTO_PROVIDER);
            agreement.init(this.privateSessionEndpoint.getPrivateKey());
            agreement.doPhase(this.publicSessionEndpoint.getPublicKey(), true);
            byte[] fullKey = agreement.generateSecret();
            byte[] trunckey = Arrays.copyOf(fullKey, 16);
            this.xorMask = Arrays.copyOfRange(fullKey, 16, fullKey.length);
            this.sessionKey = new SessionSecretKeySpec(trunckey, "AES");
            Wiper.wipe(fullKey);
            Wiper.wipe(trunckey);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public void destroySessionkey() throws Exception {
            Wiper.wipe(this.xorMask);
            this.sessionKey.destroy();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public String encryptString(String plaintext) throws Exception {
            if (plaintext == null) {
                return null;
            }
            return encryptData(generateIV(), plaintext.getBytes());
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public byte[] encryptBytes(byte[] plaintext) throws Exception {
            if (plaintext == null) {
                return null;
            }
            return encryptData(generateIV(), plaintext).getBytes();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public String decryptString(String ciphertext) throws Exception {
            if (ciphertext == null) {
                return null;
            }
            return new String(decryptData(ciphertext));
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public byte[] decryptBytes(byte[] ciphertext) throws Exception {
            if (ciphertext == null) {
                return null;
            }
            return decryptData(new String(ciphertext));
        }

        private String encryptData(byte[] iv, byte[] plaintext) throws Exception {
            byte[] encrypted = encrypt(iv, plaintext);
            return Util.encodeBase64(iv) + ":" + Util.encodeBase64(encrypted);
        }

        private byte[] decryptData(String ciphertext) throws Exception {
            String[] parts = ciphertext.split(":");
            byte[] iv = Util.decodeBase64(parts[0]);
            byte[] encrypted = Util.decodeBase64(parts[1]);
            byte[] decrypted = decrypt(iv, encrypted);
            return decrypted;
        }

        private byte[] generateIV() {
            SecureRandom random = new SecureRandom();
            byte[] iv = new byte[12];
            random.nextBytes(iv);
            return iv;
        }

        private byte[] encrypt(byte[] iv, byte[] plaintext) throws Exception {
            applyXorMask(plaintext);
            Cipher cipher = Cipher.getInstance(MdfUtils.MDF_CIPHER_MODE, Security.getProvider(SecureSessionManager.CRYPTO_PROVIDER));
            cipher.init(1, this.sessionKey, new IvParameterSpec(iv));
            return cipher.doFinal(plaintext);
        }

        private byte[] decrypt(byte[] iv, byte[] ciphertext) throws Exception {
            Cipher cipher = Cipher.getInstance(MdfUtils.MDF_CIPHER_MODE, Security.getProvider(SecureSessionManager.CRYPTO_PROVIDER));
            cipher.init(2, this.sessionKey, new IvParameterSpec(iv));
            byte[] plaintext = cipher.doFinal(ciphertext);
            applyXorMask(plaintext);
            return plaintext;
        }

        private void applyXorMask(byte[] data) {
            int i = 0;
            int j = 0;
            while (i < data.length) {
                byte[] bArr = this.xorMask;
                if (j >= bArr.length) {
                    j = 0;
                }
                data[i] = (byte) (bArr[j] ^ data[i]);
                i++;
                j++;
            }
        }
    }
}
