package com.android.internal.org.bouncycastle.crypto.digests;

import com.android.internal.org.bouncycastle.util.Memoable;

/* loaded from: classes5.dex */
public class MD4Digest extends GeneralDigest {
    private static final int DIGEST_LENGTH = 16;
    private static final int S11 = 3;
    private static final int S12 = 7;
    private static final int S13 = 11;
    private static final int S14 = 19;
    private static final int S21 = 3;
    private static final int S22 = 5;
    private static final int S23 = 9;
    private static final int S24 = 13;
    private static final int S31 = 3;
    private static final int S32 = 9;
    private static final int S33 = 11;
    private static final int S34 = 15;
    private int H1;
    private int H2;
    private int H3;
    private int H4;
    private int[] X;
    private int xOff;

    public MD4Digest() {
        this.X = new int[16];
        reset();
    }

    public MD4Digest(MD4Digest t) {
        super(t);
        this.X = new int[16];
        copyIn(t);
    }

    private void copyIn(MD4Digest t) {
        super.copyIn((GeneralDigest) t);
        this.H1 = t.H1;
        this.H2 = t.H2;
        this.H3 = t.H3;
        this.H4 = t.H4;
        System.arraycopy(t.X, 0, this.X, 0, t.X.length);
        this.xOff = t.xOff;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Digest
    public String getAlgorithmName() {
        return "MD4";
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Digest
    public int getDigestSize() {
        return 16;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.digests.GeneralDigest
    protected void processWord(byte[] in, int inOff) {
        int[] iArr = this.X;
        int i = this.xOff;
        this.xOff = i + 1;
        iArr[i] = (in[inOff] & 255) | ((in[inOff + 1] & 255) << 8) | ((in[inOff + 2] & 255) << 16) | ((in[inOff + 3] & 255) << 24);
        if (this.xOff == 16) {
            processBlock();
        }
    }

    @Override // com.android.internal.org.bouncycastle.crypto.digests.GeneralDigest
    protected void processLength(long bitLength) {
        if (this.xOff > 14) {
            processBlock();
        }
        this.X[14] = (int) ((-1) & bitLength);
        this.X[15] = (int) (bitLength >>> 32);
    }

    private void unpackWord(int word, byte[] out, int outOff) {
        out[outOff] = (byte) word;
        out[outOff + 1] = (byte) (word >>> 8);
        out[outOff + 2] = (byte) (word >>> 16);
        out[outOff + 3] = (byte) (word >>> 24);
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Digest
    public int doFinal(byte[] out, int outOff) {
        finish();
        unpackWord(this.H1, out, outOff);
        unpackWord(this.H2, out, outOff + 4);
        unpackWord(this.H3, out, outOff + 8);
        unpackWord(this.H4, out, outOff + 12);
        reset();
        return 16;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.digests.GeneralDigest, com.android.internal.org.bouncycastle.crypto.Digest
    public void reset() {
        super.reset();
        this.H1 = 1732584193;
        this.H2 = -271733879;
        this.H3 = -1732584194;
        this.H4 = 271733878;
        this.xOff = 0;
        for (int i = 0; i != this.X.length; i++) {
            this.X[i] = 0;
        }
    }

    private int rotateLeft(int x, int n) {
        return (x << n) | (x >>> (32 - n));
    }

    private int F(int u, int v, int w) {
        return (u & v) | ((~u) & w);
    }

    private int G(int u, int v, int w) {
        return (u & v) | (u & w) | (v & w);
    }

    private int H(int u, int v, int w) {
        return (u ^ v) ^ w;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.digests.GeneralDigest
    protected void processBlock() {
        int a = this.H1;
        int b = this.H2;
        int c = this.H3;
        int d = this.H4;
        int a2 = rotateLeft(F(b, c, d) + a + this.X[0], 3);
        int d2 = rotateLeft(F(a2, b, c) + d + this.X[1], 7);
        int c2 = rotateLeft(F(d2, a2, b) + c + this.X[2], 11);
        int b2 = rotateLeft(F(c2, d2, a2) + b + this.X[3], 19);
        int a3 = rotateLeft(F(b2, c2, d2) + a2 + this.X[4], 3);
        int d3 = rotateLeft(F(a3, b2, c2) + d2 + this.X[5], 7);
        int c3 = rotateLeft(F(d3, a3, b2) + c2 + this.X[6], 11);
        int b3 = rotateLeft(F(c3, d3, a3) + b2 + this.X[7], 19);
        int a4 = rotateLeft(F(b3, c3, d3) + a3 + this.X[8], 3);
        int d4 = rotateLeft(F(a4, b3, c3) + d3 + this.X[9], 7);
        int c4 = rotateLeft(F(d4, a4, b3) + c3 + this.X[10], 11);
        int b4 = rotateLeft(F(c4, d4, a4) + b3 + this.X[11], 19);
        int a5 = rotateLeft(F(b4, c4, d4) + a4 + this.X[12], 3);
        int d5 = rotateLeft(F(a5, b4, c4) + d4 + this.X[13], 7);
        int c5 = rotateLeft(F(d5, a5, b4) + c4 + this.X[14], 11);
        int b5 = rotateLeft(F(c5, d5, a5) + b4 + this.X[15], 19);
        int a6 = rotateLeft(G(b5, c5, d5) + a5 + this.X[0] + 1518500249, 3);
        int d6 = rotateLeft(G(a6, b5, c5) + d5 + this.X[4] + 1518500249, 5);
        int c6 = rotateLeft(G(d6, a6, b5) + c5 + this.X[8] + 1518500249, 9);
        int b6 = rotateLeft(G(c6, d6, a6) + b5 + this.X[12] + 1518500249, 13);
        int a7 = rotateLeft(G(b6, c6, d6) + a6 + this.X[1] + 1518500249, 3);
        int d7 = rotateLeft(G(a7, b6, c6) + d6 + this.X[5] + 1518500249, 5);
        int c7 = rotateLeft(G(d7, a7, b6) + c6 + this.X[9] + 1518500249, 9);
        int b7 = rotateLeft(G(c7, d7, a7) + b6 + this.X[13] + 1518500249, 13);
        int a8 = rotateLeft(G(b7, c7, d7) + a7 + this.X[2] + 1518500249, 3);
        int d8 = rotateLeft(G(a8, b7, c7) + d7 + this.X[6] + 1518500249, 5);
        int c8 = rotateLeft(G(d8, a8, b7) + c7 + this.X[10] + 1518500249, 9);
        int b8 = rotateLeft(G(c8, d8, a8) + b7 + this.X[14] + 1518500249, 13);
        int a9 = rotateLeft(G(b8, c8, d8) + a8 + this.X[3] + 1518500249, 3);
        int d9 = rotateLeft(G(a9, b8, c8) + d8 + this.X[7] + 1518500249, 5);
        int c9 = rotateLeft(G(d9, a9, b8) + c8 + this.X[11] + 1518500249, 9);
        int b9 = rotateLeft(G(c9, d9, a9) + b8 + this.X[15] + 1518500249, 13);
        int a10 = rotateLeft(H(b9, c9, d9) + a9 + this.X[0] + 1859775393, 3);
        int d10 = rotateLeft(H(a10, b9, c9) + d9 + this.X[8] + 1859775393, 9);
        int c10 = rotateLeft(H(d10, a10, b9) + c9 + this.X[4] + 1859775393, 11);
        int b10 = rotateLeft(H(c10, d10, a10) + b9 + this.X[12] + 1859775393, 15);
        int a11 = rotateLeft(H(b10, c10, d10) + a10 + this.X[2] + 1859775393, 3);
        int d11 = rotateLeft(H(a11, b10, c10) + d10 + this.X[10] + 1859775393, 9);
        int c11 = rotateLeft(H(d11, a11, b10) + c10 + this.X[6] + 1859775393, 11);
        int b11 = rotateLeft(H(c11, d11, a11) + b10 + this.X[14] + 1859775393, 15);
        int a12 = rotateLeft(H(b11, c11, d11) + a11 + this.X[1] + 1859775393, 3);
        int d12 = rotateLeft(H(a12, b11, c11) + d11 + this.X[9] + 1859775393, 9);
        int c12 = rotateLeft(H(d12, a12, b11) + c11 + this.X[5] + 1859775393, 11);
        int b12 = rotateLeft(H(c12, d12, a12) + b11 + this.X[13] + 1859775393, 15);
        int a13 = rotateLeft(H(b12, c12, d12) + a12 + this.X[3] + 1859775393, 3);
        int d13 = rotateLeft(H(a13, b12, c12) + d12 + this.X[11] + 1859775393, 9);
        int c13 = rotateLeft(H(d13, a13, b12) + c12 + this.X[7] + 1859775393, 11);
        int b13 = rotateLeft(H(c13, d13, a13) + b12 + this.X[15] + 1859775393, 15);
        this.H1 += a13;
        this.H2 += b13;
        this.H3 += c13;
        this.H4 += d13;
        this.xOff = 0;
        for (int i = 0; i != this.X.length; i++) {
            this.X[i] = 0;
        }
    }

    @Override // com.android.internal.org.bouncycastle.util.Memoable
    public Memoable copy() {
        return new MD4Digest(this);
    }

    @Override // com.android.internal.org.bouncycastle.util.Memoable
    public void reset(Memoable other) {
        MD4Digest d = (MD4Digest) other;
        copyIn(d);
    }
}
