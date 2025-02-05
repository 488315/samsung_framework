package com.android.internal.org.bouncycastle.asn1;

import com.android.internal.org.bouncycastle.util.Arrays;
import com.android.internal.org.bouncycastle.util.Properties;
import java.io.IOException;
import java.math.BigInteger;

/* loaded from: classes5.dex */
public class ASN1Integer extends ASN1Primitive {
    static final int SIGN_EXT_SIGNED = -1;
    static final int SIGN_EXT_UNSIGNED = 255;
    private final byte[] bytes;
    private final int start;

    public static ASN1Integer getInstance(Object obj) {
        if (obj == null || (obj instanceof ASN1Integer)) {
            return (ASN1Integer) obj;
        }
        if (obj instanceof byte[]) {
            try {
                return (ASN1Integer) fromByteArray((byte[]) obj);
            } catch (Exception e) {
                throw new IllegalArgumentException("encoding error in getInstance: " + e.toString());
            }
        }
        throw new IllegalArgumentException("illegal object in getInstance: " + obj.getClass().getName());
    }

    public static ASN1Integer getInstance(ASN1TaggedObject obj, boolean explicit) {
        ASN1Primitive o = obj.getObject();
        if (explicit || (o instanceof ASN1Integer)) {
            return getInstance(o);
        }
        return new ASN1Integer(ASN1OctetString.getInstance(o).getOctets());
    }

    public ASN1Integer(long value) {
        this.bytes = BigInteger.valueOf(value).toByteArray();
        this.start = 0;
    }

    public ASN1Integer(BigInteger value) {
        this.bytes = value.toByteArray();
        this.start = 0;
    }

    public ASN1Integer(byte[] bytes) {
        this(bytes, true);
    }

    ASN1Integer(byte[] bytes, boolean clone) {
        if (isMalformed(bytes)) {
            throw new IllegalArgumentException("malformed integer");
        }
        this.bytes = clone ? Arrays.clone(bytes) : bytes;
        this.start = signBytesToSkip(bytes);
    }

    public BigInteger getPositiveValue() {
        return new BigInteger(1, this.bytes);
    }

    public BigInteger getValue() {
        return new BigInteger(this.bytes);
    }

    public boolean hasValue(BigInteger x) {
        return x != null && intValue(this.bytes, this.start, -1) == x.intValue() && getValue().equals(x);
    }

    public int intPositiveValueExact() {
        int count = this.bytes.length - this.start;
        if (count > 4 || (count == 4 && (this.bytes[this.start] & 128) != 0)) {
            throw new ArithmeticException("ASN.1 Integer out of positive int range");
        }
        return intValue(this.bytes, this.start, 255);
    }

    public int intValueExact() {
        int count = this.bytes.length - this.start;
        if (count > 4) {
            throw new ArithmeticException("ASN.1 Integer out of int range");
        }
        return intValue(this.bytes, this.start, -1);
    }

    public long longValueExact() {
        int count = this.bytes.length - this.start;
        if (count > 8) {
            throw new ArithmeticException("ASN.1 Integer out of long range");
        }
        return longValue(this.bytes, this.start, -1);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    boolean isConstructed() {
        return false;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    int encodedLength() {
        return StreamUtil.calculateBodyLength(this.bytes.length) + 1 + this.bytes.length;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    void encode(ASN1OutputStream out, boolean withTag) throws IOException {
        out.writeEncoded(withTag, 2, this.bytes);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive, com.android.internal.org.bouncycastle.asn1.ASN1Object
    public int hashCode() {
        return Arrays.hashCode(this.bytes);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    boolean asn1Equals(ASN1Primitive o) {
        if (!(o instanceof ASN1Integer)) {
            return false;
        }
        ASN1Integer other = (ASN1Integer) o;
        return Arrays.areEqual(this.bytes, other.bytes);
    }

    public String toString() {
        return getValue().toString();
    }

    static int intValue(byte[] bytes, int start, int signExt) {
        int length = bytes.length;
        int pos = Math.max(start, length - 4);
        int val = bytes[pos] & signExt;
        while (true) {
            pos++;
            if (pos < length) {
                val = (val << 8) | (bytes[pos] & 255);
            } else {
                return val;
            }
        }
    }

    static long longValue(byte[] bytes, int start, int signExt) {
        int length = bytes.length;
        int pos = Math.max(start, length - 8);
        long val = bytes[pos] & signExt;
        while (true) {
            pos++;
            if (pos < length) {
                val = (val << 8) | (bytes[pos] & 255);
            } else {
                return val;
            }
        }
    }

    static boolean isMalformed(byte[] bytes) {
        switch (bytes.length) {
            case 0:
                return true;
            case 1:
                return false;
            default:
                return bytes[0] == (bytes[1] >> 7) && !Properties.isOverrideSet("com.android.internal.org.bouncycastle.asn1.allow_unsafe_integer");
        }
    }

    static int signBytesToSkip(byte[] bytes) {
        int pos = 0;
        int last = bytes.length - 1;
        while (pos < last && bytes[pos] == (bytes[pos + 1] >> 7)) {
            pos++;
        }
        return pos;
    }
}
