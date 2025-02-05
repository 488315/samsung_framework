package com.android.internal.org.bouncycastle.math.ec.custom.sec;

import com.android.internal.org.bouncycastle.math.ec.ECCurve;
import com.android.internal.org.bouncycastle.math.ec.ECFieldElement;
import com.android.internal.org.bouncycastle.math.ec.ECPoint;
import com.android.internal.org.bouncycastle.math.raw.Nat;
import com.android.internal.org.bouncycastle.math.raw.Nat256;

/* loaded from: classes5.dex */
public class SecP256K1Point extends ECPoint.AbstractFp {
    SecP256K1Point(ECCurve curve, ECFieldElement x, ECFieldElement y) {
        super(curve, x, y);
    }

    SecP256K1Point(ECCurve curve, ECFieldElement x, ECFieldElement y, ECFieldElement[] zs) {
        super(curve, x, y, zs);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECPoint
    protected ECPoint detach() {
        return new SecP256K1Point(null, getAffineXCoord(), getAffineYCoord());
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECPoint
    public ECPoint add(ECPoint b) {
        int[] S2;
        int[] U2;
        int[] S1;
        int[] S12;
        if (isInfinity()) {
            return b;
        }
        if (b.isInfinity()) {
            return this;
        }
        if (this == b) {
            return twice();
        }
        ECCurve curve = getCurve();
        SecP256K1FieldElement X1 = (SecP256K1FieldElement) this.x;
        SecP256K1FieldElement Y1 = (SecP256K1FieldElement) this.y;
        SecP256K1FieldElement X2 = (SecP256K1FieldElement) b.getXCoord();
        SecP256K1FieldElement Y2 = (SecP256K1FieldElement) b.getYCoord();
        SecP256K1FieldElement Z1 = (SecP256K1FieldElement) this.zs[0];
        SecP256K1FieldElement Z2 = (SecP256K1FieldElement) b.getZCoord(0);
        int[] tt1 = Nat256.createExt();
        int[] t2 = Nat256.create();
        int[] t3 = Nat256.create();
        int[] t4 = Nat256.create();
        boolean Z1IsOne = Z1.isOne();
        if (Z1IsOne) {
            U2 = X2.x;
            S2 = Y2.x;
        } else {
            S2 = t3;
            SecP256K1Field.square(Z1.x, S2);
            U2 = t2;
            SecP256K1Field.multiply(S2, X2.x, U2);
            SecP256K1Field.multiply(S2, Z1.x, S2);
            SecP256K1Field.multiply(S2, Y2.x, S2);
        }
        boolean Z2IsOne = Z2.isOne();
        if (Z2IsOne) {
            int[] U1 = X1.x;
            int[] U12 = Y1.x;
            S1 = U12;
            S12 = U1;
        } else {
            SecP256K1Field.square(Z2.x, t4);
            SecP256K1Field.multiply(t4, X1.x, tt1);
            SecP256K1Field.multiply(t4, Z2.x, t4);
            SecP256K1Field.multiply(t4, Y1.x, t4);
            S1 = t4;
            S12 = tt1;
        }
        int[] H = Nat256.create();
        SecP256K1Field.subtract(S12, U2, H);
        SecP256K1Field.subtract(S1, S2, t2);
        if (Nat256.isZero(H)) {
            if (Nat256.isZero(t2)) {
                return twice();
            }
            return curve.getInfinity();
        }
        SecP256K1Field.square(H, t3);
        int[] G = Nat256.create();
        SecP256K1Field.multiply(t3, H, G);
        SecP256K1Field.multiply(t3, S12, t3);
        SecP256K1Field.negate(G, G);
        Nat256.mul(S1, G, tt1);
        int c = Nat256.addBothTo(t3, t3, G);
        SecP256K1Field.reduce32(c, G);
        SecP256K1FieldElement X3 = new SecP256K1FieldElement(t4);
        int[] HSquared = X3.x;
        SecP256K1Field.square(t2, HSquared);
        int[] iArr = X3.x;
        int[] S13 = X3.x;
        SecP256K1Field.subtract(iArr, G, S13);
        SecP256K1FieldElement Y3 = new SecP256K1FieldElement(G);
        SecP256K1Field.subtract(t3, X3.x, Y3.x);
        SecP256K1Field.multiplyAddToExt(Y3.x, t2, tt1);
        SecP256K1Field.reduce(tt1, Y3.x);
        SecP256K1FieldElement Z3 = new SecP256K1FieldElement(H);
        if (!Z1IsOne) {
            int[] iArr2 = Z3.x;
            int[] R = Z1.x;
            SecP256K1Field.multiply(iArr2, R, Z3.x);
        }
        if (!Z2IsOne) {
            SecP256K1Field.multiply(Z3.x, Z2.x, Z3.x);
        }
        ECFieldElement[] zs = {Z3};
        return new SecP256K1Point(curve, X3, Y3, zs);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECPoint
    public ECPoint twice() {
        if (isInfinity()) {
            return this;
        }
        ECCurve curve = getCurve();
        SecP256K1FieldElement Y1 = (SecP256K1FieldElement) this.y;
        if (Y1.isZero()) {
            return curve.getInfinity();
        }
        SecP256K1FieldElement X1 = (SecP256K1FieldElement) this.x;
        SecP256K1FieldElement Z1 = (SecP256K1FieldElement) this.zs[0];
        int[] Y1Squared = Nat256.create();
        SecP256K1Field.square(Y1.x, Y1Squared);
        int[] T = Nat256.create();
        SecP256K1Field.square(Y1Squared, T);
        int[] M = Nat256.create();
        SecP256K1Field.square(X1.x, M);
        int c = Nat256.addBothTo(M, M, M);
        SecP256K1Field.reduce32(c, M);
        SecP256K1Field.multiply(Y1Squared, X1.x, Y1Squared);
        int c2 = Nat.shiftUpBits(8, Y1Squared, 2, 0);
        SecP256K1Field.reduce32(c2, Y1Squared);
        int[] t1 = Nat256.create();
        int c3 = Nat.shiftUpBits(8, T, 3, 0, t1);
        SecP256K1Field.reduce32(c3, t1);
        SecP256K1FieldElement X3 = new SecP256K1FieldElement(T);
        SecP256K1Field.square(M, X3.x);
        SecP256K1Field.subtract(X3.x, Y1Squared, X3.x);
        SecP256K1Field.subtract(X3.x, Y1Squared, X3.x);
        SecP256K1FieldElement Y3 = new SecP256K1FieldElement(Y1Squared);
        SecP256K1Field.subtract(Y1Squared, X3.x, Y3.x);
        SecP256K1Field.multiply(Y3.x, M, Y3.x);
        SecP256K1Field.subtract(Y3.x, t1, Y3.x);
        SecP256K1FieldElement Z3 = new SecP256K1FieldElement(M);
        SecP256K1Field.twice(Y1.x, Z3.x);
        if (!Z1.isOne()) {
            SecP256K1Field.multiply(Z3.x, Z1.x, Z3.x);
        }
        return new SecP256K1Point(curve, X3, Y3, new ECFieldElement[]{Z3});
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECPoint
    public ECPoint twicePlus(ECPoint b) {
        if (this == b) {
            return threeTimes();
        }
        if (isInfinity()) {
            return b;
        }
        if (b.isInfinity()) {
            return twice();
        }
        ECFieldElement Y1 = this.y;
        if (Y1.isZero()) {
            return b;
        }
        return twice().add(b);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECPoint
    public ECPoint threeTimes() {
        if (isInfinity() || this.y.isZero()) {
            return this;
        }
        return twice().add(this);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECPoint
    public ECPoint negate() {
        if (isInfinity()) {
            return this;
        }
        return new SecP256K1Point(this.curve, this.x, this.y.negate(), this.zs);
    }
}
