package org.xbill.DNS;

import java.io.IOException;
import org.xbill.DNS.utils.base16;

/* loaded from: classes.dex */
public class NSEC3PARAMRecord extends Record {
    private static final long serialVersionUID = -8689038598776316533L;
    private int flags;
    private int hashAlg;
    private int iterations;
    private byte[] salt;

    NSEC3PARAMRecord() {
    }

    @Override // org.xbill.DNS.Record
    Record getObject() {
        return new NSEC3PARAMRecord();
    }

    @Override // org.xbill.DNS.Record
    void rrFromWire(DNSInput dNSInput) throws IOException {
        this.hashAlg = dNSInput.readU8();
        this.flags = dNSInput.readU8();
        this.iterations = dNSInput.readU16();
        int readU8 = dNSInput.readU8();
        if (readU8 > 0) {
            this.salt = dNSInput.readByteArray(readU8);
        } else {
            this.salt = null;
        }
    }

    @Override // org.xbill.DNS.Record
    void rrToWire(DNSOutput dNSOutput, Compression compression, boolean z) {
        dNSOutput.writeU8(this.hashAlg);
        dNSOutput.writeU8(this.flags);
        dNSOutput.writeU16(this.iterations);
        byte[] bArr = this.salt;
        if (bArr != null) {
            dNSOutput.writeU8(bArr.length);
            dNSOutput.writeByteArray(this.salt);
        } else {
            dNSOutput.writeU8(0);
        }
    }

    @Override // org.xbill.DNS.Record
    String rrToString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(this.hashAlg);
        stringBuffer.append(' ');
        stringBuffer.append(this.flags);
        stringBuffer.append(' ');
        stringBuffer.append(this.iterations);
        stringBuffer.append(' ');
        byte[] bArr = this.salt;
        if (bArr == null) {
            stringBuffer.append('-');
        } else {
            stringBuffer.append(base16.toString(bArr));
        }
        return stringBuffer.toString();
    }
}
