package com.android.internal.org.bouncycastle.crypto.modes;

import com.android.internal.org.bouncycastle.crypto.BlockCipher;
import com.android.internal.org.bouncycastle.crypto.CipherParameters;
import com.android.internal.org.bouncycastle.crypto.DataLengthException;
import com.android.internal.org.bouncycastle.crypto.params.ParametersWithIV;
import com.android.internal.org.bouncycastle.util.Arrays;

/* loaded from: classes5.dex */
public class CBCBlockCipher implements BlockCipher {
    private byte[] IV;
    private int blockSize;
    private byte[] cbcNextV;
    private byte[] cbcV;
    private BlockCipher cipher;
    private boolean encrypting;

    public CBCBlockCipher(BlockCipher cipher) {
        this.cipher = null;
        this.cipher = cipher;
        this.blockSize = cipher.getBlockSize();
        this.IV = new byte[this.blockSize];
        this.cbcV = new byte[this.blockSize];
        this.cbcNextV = new byte[this.blockSize];
    }

    public BlockCipher getUnderlyingCipher() {
        return this.cipher;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.BlockCipher
    public void init(boolean encrypting, CipherParameters params) throws IllegalArgumentException {
        boolean oldEncrypting = this.encrypting;
        this.encrypting = encrypting;
        if (params instanceof ParametersWithIV) {
            ParametersWithIV ivParam = (ParametersWithIV) params;
            byte[] iv = ivParam.getIV();
            if (iv.length != this.blockSize) {
                throw new IllegalArgumentException("initialisation vector must be the same length as block size");
            }
            System.arraycopy(iv, 0, this.IV, 0, iv.length);
            reset();
            if (ivParam.getParameters() != null) {
                this.cipher.init(encrypting, ivParam.getParameters());
                return;
            } else {
                if (oldEncrypting != encrypting) {
                    throw new IllegalArgumentException("cannot change encrypting state without providing key.");
                }
                return;
            }
        }
        reset();
        if (params != null) {
            this.cipher.init(encrypting, params);
        } else if (oldEncrypting != encrypting) {
            throw new IllegalArgumentException("cannot change encrypting state without providing key.");
        }
    }

    @Override // com.android.internal.org.bouncycastle.crypto.BlockCipher
    public String getAlgorithmName() {
        return this.cipher.getAlgorithmName() + "/CBC";
    }

    @Override // com.android.internal.org.bouncycastle.crypto.BlockCipher
    public int getBlockSize() {
        return this.cipher.getBlockSize();
    }

    @Override // com.android.internal.org.bouncycastle.crypto.BlockCipher
    public int processBlock(byte[] in, int inOff, byte[] out, int outOff) throws DataLengthException, IllegalStateException {
        return this.encrypting ? encryptBlock(in, inOff, out, outOff) : decryptBlock(in, inOff, out, outOff);
    }

    @Override // com.android.internal.org.bouncycastle.crypto.BlockCipher
    public void reset() {
        System.arraycopy(this.IV, 0, this.cbcV, 0, this.IV.length);
        Arrays.fill(this.cbcNextV, (byte) 0);
        this.cipher.reset();
    }

    private int encryptBlock(byte[] in, int inOff, byte[] out, int outOff) throws DataLengthException, IllegalStateException {
        if (this.blockSize + inOff > in.length) {
            throw new DataLengthException("input buffer too short");
        }
        for (int i = 0; i < this.blockSize; i++) {
            byte[] bArr = this.cbcV;
            bArr[i] = (byte) (bArr[i] ^ in[inOff + i]);
        }
        int length = this.cipher.processBlock(this.cbcV, 0, out, outOff);
        System.arraycopy(out, outOff, this.cbcV, 0, this.cbcV.length);
        return length;
    }

    private int decryptBlock(byte[] in, int inOff, byte[] out, int outOff) throws DataLengthException, IllegalStateException {
        if (this.blockSize + inOff > in.length) {
            throw new DataLengthException("input buffer too short");
        }
        System.arraycopy(in, inOff, this.cbcNextV, 0, this.blockSize);
        int length = this.cipher.processBlock(in, inOff, out, outOff);
        for (int i = 0; i < this.blockSize; i++) {
            int i2 = outOff + i;
            out[i2] = (byte) (out[i2] ^ this.cbcV[i]);
        }
        byte[] tmp = this.cbcV;
        this.cbcV = this.cbcNextV;
        this.cbcNextV = tmp;
        return length;
    }
}
