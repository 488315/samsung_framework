package com.android.internal.org.bouncycastle.math.ec.custom.sec;

import com.android.internal.org.bouncycastle.math.ec.AbstractECLookupTable;
import com.android.internal.org.bouncycastle.math.ec.ECConstants;
import com.android.internal.org.bouncycastle.math.ec.ECCurve;
import com.android.internal.org.bouncycastle.math.ec.ECFieldElement;
import com.android.internal.org.bouncycastle.math.ec.ECLookupTable;
import com.android.internal.org.bouncycastle.math.ec.ECPoint;
import com.android.internal.org.bouncycastle.math.raw.Nat;
import com.android.internal.org.bouncycastle.util.encoders.Hex;
import java.math.BigInteger;
import java.security.SecureRandom;

/* loaded from: classes5.dex */
public class SecP384R1Curve extends ECCurve.AbstractFp {
    private static final int SECP384R1_DEFAULT_COORDS = 2;
    protected SecP384R1Point infinity;
    public static final BigInteger q = SecP384R1FieldElement.Q;
    private static final ECFieldElement[] SECP384R1_AFFINE_ZS = {new SecP384R1FieldElement(ECConstants.ONE)};

    public SecP384R1Curve() {
        super(q);
        this.infinity = new SecP384R1Point(this, null, null);
        this.a = fromBigInteger(new BigInteger(1, Hex.decodeStrict("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFFFFF0000000000000000FFFFFFFC")));
        this.b = fromBigInteger(new BigInteger(1, Hex.decodeStrict("B3312FA7E23EE7E4988E056BE3F82D19181D9C6EFE8141120314088F5013875AC656398D8A2ED19D2A85C8EDD3EC2AEF")));
        this.order = new BigInteger(1, Hex.decodeStrict("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFC7634D81F4372DDF581A0DB248B0A77AECEC196ACCC52973"));
        this.cofactor = BigInteger.valueOf(1L);
        this.coord = 2;
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECCurve
    protected ECCurve cloneCurve() {
        return new SecP384R1Curve();
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECCurve
    public boolean supportsCoordinateSystem(int coord) {
        switch (coord) {
            case 2:
                return true;
            default:
                return false;
        }
    }

    public BigInteger getQ() {
        return q;
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECCurve
    public int getFieldSize() {
        return q.bitLength();
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECCurve
    public ECFieldElement fromBigInteger(BigInteger x) {
        return new SecP384R1FieldElement(x);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECCurve
    protected ECPoint createRawPoint(ECFieldElement x, ECFieldElement y) {
        return new SecP384R1Point(this, x, y);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECCurve
    protected ECPoint createRawPoint(ECFieldElement x, ECFieldElement y, ECFieldElement[] zs) {
        return new SecP384R1Point(this, x, y, zs);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECCurve
    public ECPoint getInfinity() {
        return this.infinity;
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECCurve
    public ECLookupTable createCacheSafeLookupTable(ECPoint[] points, int off, final int len) {
        final int[] table = new int[len * 12 * 2];
        int pos = 0;
        for (int i = 0; i < len; i++) {
            ECPoint p = points[off + i];
            Nat.copy(12, ((SecP384R1FieldElement) p.getRawXCoord()).x, 0, table, pos);
            int pos2 = pos + 12;
            Nat.copy(12, ((SecP384R1FieldElement) p.getRawYCoord()).x, 0, table, pos2);
            pos = pos2 + 12;
        }
        return new AbstractECLookupTable() { // from class: com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP384R1Curve.1
            @Override // com.android.internal.org.bouncycastle.math.ec.ECLookupTable
            public int getSize() {
                return len;
            }

            @Override // com.android.internal.org.bouncycastle.math.ec.ECLookupTable
            public ECPoint lookup(int index) {
                int[] x = Nat.create(12);
                int[] y = Nat.create(12);
                int pos3 = 0;
                for (int i2 = 0; i2 < len; i2++) {
                    int MASK = ((i2 ^ index) - 1) >> 31;
                    for (int j = 0; j < 12; j++) {
                        x[j] = x[j] ^ (table[pos3 + j] & MASK);
                        y[j] = y[j] ^ (table[(pos3 + 12) + j] & MASK);
                    }
                    pos3 += 24;
                }
                return createPoint(x, y);
            }

            @Override // com.android.internal.org.bouncycastle.math.ec.AbstractECLookupTable, com.android.internal.org.bouncycastle.math.ec.ECLookupTable
            public ECPoint lookupVar(int index) {
                int[] x = Nat.create(12);
                int[] y = Nat.create(12);
                int pos3 = index * 12 * 2;
                for (int j = 0; j < 12; j++) {
                    x[j] = table[pos3 + j];
                    y[j] = table[pos3 + 12 + j];
                }
                return createPoint(x, y);
            }

            private ECPoint createPoint(int[] x, int[] y) {
                return SecP384R1Curve.this.createRawPoint(new SecP384R1FieldElement(x), new SecP384R1FieldElement(y), SecP384R1Curve.SECP384R1_AFFINE_ZS);
            }
        };
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECCurve.AbstractFp, com.android.internal.org.bouncycastle.math.ec.ECCurve
    public ECFieldElement randomFieldElement(SecureRandom r) {
        int[] x = Nat.create(12);
        SecP384R1Field.random(r, x);
        return new SecP384R1FieldElement(x);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECCurve.AbstractFp, com.android.internal.org.bouncycastle.math.ec.ECCurve
    public ECFieldElement randomFieldElementMult(SecureRandom r) {
        int[] x = Nat.create(12);
        SecP384R1Field.randomMult(r, x);
        return new SecP384R1FieldElement(x);
    }
}
