package org.xbill.DNS;

import java.io.IOException;
import java.net.InetAddress;

/* loaded from: classes.dex */
public class A6Record extends Record {
    private static final long serialVersionUID = -8815026887337346789L;
    private Name prefix;
    private int prefixBits;
    private InetAddress suffix;

    A6Record() {
    }

    @Override // org.xbill.DNS.Record
    Record getObject() {
        return new A6Record();
    }

    @Override // org.xbill.DNS.Record
    void rrFromWire(DNSInput dNSInput) throws IOException {
        int readU8 = dNSInput.readU8();
        this.prefixBits = readU8;
        int i = ((128 - readU8) + 7) / 8;
        if (readU8 < 128) {
            byte[] bArr = new byte[16];
            dNSInput.readByteArray(bArr, 16 - i, i);
            this.suffix = InetAddress.getByAddress(bArr);
        }
        if (this.prefixBits > 0) {
            this.prefix = new Name(dNSInput);
        }
    }

    @Override // org.xbill.DNS.Record
    String rrToString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(this.prefixBits);
        if (this.suffix != null) {
            stringBuffer.append(" ");
            stringBuffer.append(this.suffix.getHostAddress());
        }
        if (this.prefix != null) {
            stringBuffer.append(" ");
            stringBuffer.append(this.prefix);
        }
        return stringBuffer.toString();
    }

    @Override // org.xbill.DNS.Record
    void rrToWire(DNSOutput dNSOutput, Compression compression, boolean z) {
        dNSOutput.writeU8(this.prefixBits);
        InetAddress inetAddress = this.suffix;
        if (inetAddress != null) {
            int i = ((128 - this.prefixBits) + 7) / 8;
            dNSOutput.writeByteArray(inetAddress.getAddress(), 16 - i, i);
        }
        Name name = this.prefix;
        if (name != null) {
            name.toWire(dNSOutput, null, z);
        }
    }
}
