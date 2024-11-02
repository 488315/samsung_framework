package com.android.systemui.controls.util;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class EncryptDecrypt {
    public static InputStream decryptStream(InputStream inputStream, String str, int i) {
        SecretKeySpec generateSHA256SecretKey;
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] bArr = new byte[cipher.getBlockSize()];
        if (inputStream.read(bArr) <= 0) {
            return null;
        }
        IvParameterSpec ivParameterSpec = new IvParameterSpec(bArr);
        if (i == 1) {
            byte[] bArr2 = new byte[16];
            if (inputStream.read(bArr2) <= 0) {
                return null;
            }
            generateSHA256SecretKey = generatePBKDF2SecretKey(str, bArr2);
        } else {
            generateSHA256SecretKey = generateSHA256SecretKey(str);
        }
        cipher.init(2, generateSHA256SecretKey, ivParameterSpec);
        return new CipherInputStream(inputStream, cipher);
    }

    public static OutputStream encryptStream(OutputStream outputStream, String str, int i) {
        SecretKeySpec generateSHA256SecretKey;
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] bArr = new byte[cipher.getBlockSize()];
        new SecureRandom().nextBytes(bArr);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(bArr);
        outputStream.write(bArr);
        if (i == 1) {
            byte[] bArr2 = new byte[16];
            new SecureRandom().nextBytes(bArr2);
            outputStream.write(bArr2);
            generateSHA256SecretKey = generatePBKDF2SecretKey(str, bArr2);
        } else {
            generateSHA256SecretKey = generateSHA256SecretKey(str);
        }
        cipher.init(1, generateSHA256SecretKey, ivParameterSpec);
        return new CipherOutputStream(outputStream, cipher);
    }

    public static SecretKeySpec generatePBKDF2SecretKey(String str, byte[] bArr) {
        return new SecretKeySpec(SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1").generateSecret(new PBEKeySpec(str.toCharArray(), bArr, 1000, 256)).getEncoded(), "AES");
    }

    public static SecretKeySpec generateSHA256SecretKey(String str) {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(str.getBytes(Charset.forName("UTF-8")));
        byte[] bArr = new byte[16];
        System.arraycopy(messageDigest.digest(), 0, bArr, 0, 16);
        return new SecretKeySpec(bArr, "AES");
    }
}
