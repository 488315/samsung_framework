package com.android.internal.org.bouncycastle.math.ec.custom.sec;

import com.android.internal.org.bouncycastle.math.ec.ECCurve;
import com.android.internal.org.bouncycastle.math.ec.ECFieldElement;
import com.android.internal.org.bouncycastle.math.ec.ECPoint;
import com.android.internal.org.bouncycastle.math.raw.Nat;
import com.android.internal.org.bouncycastle.math.raw.Nat192;

/* loaded from: classes5.dex */
public class SecP192R1Point extends ECPoint.AbstractFp {
    SecP192R1Point(ECCurve curve, ECFieldElement x, ECFieldElement y) {
        super(curve, x, y);
    }

    SecP192R1Point(ECCurve curve, ECFieldElement x, ECFieldElement y, ECFieldElement[] zs) {
        super(curve, x, y, zs);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECPoint
    protected ECPoint detach() {
        return new SecP192R1Point(null, getAffineXCoord(), getAffineYCoord());
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
        SecP192R1FieldElement X1 = (SecP192R1FieldElement) this.x;
        SecP192R1FieldElement Y1 = (SecP192R1FieldElement) this.y;
        SecP192R1FieldElement X2 = (SecP192R1FieldElement) b.getXCoord();
        SecP192R1FieldElement Y2 = (SecP192R1FieldElement) b.getYCoord();
        SecP192R1FieldElement Z1 = (SecP192R1FieldElement) this.zs[0];
        SecP192R1FieldElement Z2 = (SecP192R1FieldElement) b.getZCoord(0);
        int[] tt1 = Nat192.createExt();
        int[] t2 = Nat192.create();
        int[] t3 = Nat192.create();
        int[] t4 = Nat192.create();
        boolean Z1IsOne = Z1.isOne();
        if (Z1IsOne) {
            U2 = X2.x;
            S2 = Y2.x;
        } else {
            S2 = t3;
            SecP192R1Field.square(Z1.x, S2);
            U2 = t2;
            SecP192R1Field.multiply(S2, X2.x, U2);
            SecP192R1Field.multiply(S2, Z1.x, S2);
            SecP192R1Field.multiply(S2, Y2.x, S2);
        }
        boolean Z2IsOne = Z2.isOne();
        if (Z2IsOne) {
            int[] U1 = X1.x;
            int[] U12 = Y1.x;
            S1 = U12;
            S12 = U1;
        } else {
            SecP192R1Field.square(Z2.x, t4);
            SecP192R1Field.multiply(t4, X1.x, tt1);
            SecP192R1Field.multiply(t4, Z2.x, t4);
            SecP192R1Field.multiply(t4, Y1.x, t4);
            S1 = t4;
            S12 = tt1;
        }
        int[] H = Nat192.create();
        SecP192R1Field.subtract(S12, U2, H);
        SecP192R1Field.subtract(S1, S2, t2);
        if (Nat192.isZero(H)) {
            if (Nat192.isZero(t2)) {
                return twice();
            }
            return curve.getInfinity();
        }
        SecP192R1Field.square(H, t3);
        int[] G = Nat192.create();
        SecP192R1Field.multiply(t3, H, G);
        SecP192R1Field.multiply(t3, S12, t3);
        SecP192R1Field.negate(G, G);
        Nat192.mul(S1, G, tt1);
        int c = Nat192.addBothTo(t3, t3, G);
        SecP192R1Field.reduce32(c, G);
        SecP192R1FieldElement X3 = new SecP192R1FieldElement(t4);
        int[] HSquared = X3.x;
        SecP192R1Field.square(t2, HSquared);
        int[] iArr = X3.x;
        int[] S13 = X3.x;
        SecP192R1Field.subtract(iArr, G, S13);
        SecP192R1FieldElement Y3 = new SecP192R1FieldElement(G);
        SecP192R1Field.subtract(t3, X3.x, Y3.x);
        SecP192R1Field.multiplyAddToExt(Y3.x, t2, tt1);
        SecP192R1Field.reduce(tt1, Y3.x);
        SecP192R1FieldElement Z3 = new SecP192R1FieldElement(H);
        if (!Z1IsOne) {
            int[] iArr2 = Z3.x;
            int[] R = Z1.x;
            SecP192R1Field.multiply(iArr2, R, Z3.x);
        }
        if (!Z2IsOne) {
            SecP192R1Field.multiply(Z3.x, Z2.x, Z3.x);
        }
        ECFieldElement[] zs = {Z3};
        return new SecP192R1Point(curve, X3, Y3, zs);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECPoint
    public ECPoint twice() {
        if (isInfinity()) {
            return this;
        }
        ECCurve curve = getCurve();
        SecP192R1FieldElement Y1 = (SecP192R1FieldElement) this.y;
        if (Y1.isZero()) {
            return curve.getInfinity();
        }
        SecP192R1FieldElement X1 = (SecP192R1FieldElement) this.x;
        SecP192R1FieldElement Z1 = (SecP192R1FieldElement) this.zs[0];
        int[] t1 = Nat192.create();
        int[] t2 = Nat192.create();
        int[] Y1Squared = Nat192.create();
        SecP192R1Field.square(Y1.x, Y1Squared);
        int[] T = Nat192.create();
        SecP192R1Field.square(Y1Squared, T);
        boolean Z1IsOne = Z1.isOne();
        int[] Z1Squared = Z1.x;
        if (!Z1IsOne) {
            Z1Squared = t2;
            SecP192R1Field.square(Z1.x, Z1Squared);
        }
        SecP192R1Field.subtract(X1.x, Z1Squared, t1);
        SecP192R1Field.add(X1.x, Z1Squared, t2);
        SecP192R1Field.multiply(t2, t1, t2);
        int c = Nat192.addBothTo(t2, t2, t2);
        SecP192R1Field.reduce32(c, t2);
        SecP192R1Field.multiply(Y1Squared, X1.x, Y1Squared);
        int c2 = Nat.shiftUpBits(6, Y1Squared, 2, 0);
        SecP192R1Field.reduce32(c2, Y1Squared);
        int c3 = Nat.shiftUpBits(6, T, 3, 0, t1);
        SecP192R1Field.reduce32(c3, t1);
        SecP192R1FieldElement X3 = new SecP192R1FieldElement(T);
        SecP192R1Field.square(t2, X3.x);
        SecP192R1Field.subtract(X3.x, Y1Squared, X3.x);
        SecP192R1Field.subtract(X3.x, Y1Squared, X3.x);
        SecP192R1FieldElement Y3 = new SecP192R1FieldElement(Y1Squared);
        SecP192R1Field.subtract(Y1Squared, X3.x, Y3.x);
        SecP192R1Field.multiply(Y3.x, t2, Y3.x);
        SecP192R1Field.subtract(Y3.x, t1, Y3.x);
        SecP192R1FieldElement Z3 = new SecP192R1FieldElement(t2);
        SecP192R1Field.twice(Y1.x, Z3.x);
        if (!Z1IsOne) {
            SecP192R1Field.multiply(Z3.x, Z1.x, Z3.x);
        }
        return new SecP192R1Point(curve, X3, Y3, new ECFieldElement[]{Z3});
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
        return new SecP192R1Point(this.curve, this.x, this.y.negate(), this.zs);
    }
}
