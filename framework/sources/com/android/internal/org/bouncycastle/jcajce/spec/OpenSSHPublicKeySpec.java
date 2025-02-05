package com.android.internal.org.bouncycastle.jcajce.spec;

import com.android.internal.org.bouncycastle.util.Arrays;
import com.android.internal.org.bouncycastle.util.Strings;
import java.security.spec.EncodedKeySpec;

/* loaded from: classes5.dex */
public class OpenSSHPublicKeySpec extends EncodedKeySpec {
    private static final String[] allowedTypes = {"ssh-rsa", "ssh-ed25519", "ssh-dss"};
    private final String type;

    public OpenSSHPublicKeySpec(byte[] encodedKey) {
        super(encodedKey);
        int pos = 0 + 1;
        int i = (encodedKey[0] & 255) << 24;
        int pos2 = pos + 1;
        int i2 = i | ((encodedKey[pos] & 255) << 16);
        int pos3 = pos2 + 1;
        int i3 = i2 | ((encodedKey[pos2] & 255) << 8);
        int pos4 = pos3 + 1;
        int i4 = i3 | (encodedKey[pos3] & 255);
        if (pos4 + i4 >= encodedKey.length) {
            throw new IllegalArgumentException("invalid public key blob: type field longer than blob");
        }
        this.type = Strings.fromByteArray(Arrays.copyOfRange(encodedKey, pos4, pos4 + i4));
        if (this.type.startsWith("ecdsa")) {
            return;
        }
        for (int t = 0; t < allowedTypes.length; t++) {
            if (allowedTypes[t].equals(this.type)) {
                return;
            }
        }
        throw new IllegalArgumentException("unrecognised public key type " + this.type);
    }

    @Override // java.security.spec.EncodedKeySpec
    public String getFormat() {
        return "OpenSSH";
    }

    public String getType() {
        return this.type;
    }
}
