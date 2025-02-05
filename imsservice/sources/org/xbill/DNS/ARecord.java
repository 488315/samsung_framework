package org.xbill.DNS;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/* loaded from: classes.dex */
public class ARecord extends Record {
    private static final long serialVersionUID = -2172609200849142323L;
    private int addr;

    private static final byte[] toArray(int i) {
        return new byte[]{(byte) ((i >>> 24) & 255), (byte) ((i >>> 16) & 255), (byte) ((i >>> 8) & 255), (byte) (i & 255)};
    }

    ARecord() {
    }

    @Override // org.xbill.DNS.Record
    Record getObject() {
        return new ARecord();
    }

    private static final int fromArray(byte[] bArr) {
        return (bArr[3] & 255) | ((bArr[0] & 255) << 24) | ((bArr[1] & 255) << 16) | ((bArr[2] & 255) << 8);
    }

    @Override // org.xbill.DNS.Record
    void rrFromWire(DNSInput dNSInput) throws IOException {
        this.addr = fromArray(dNSInput.readByteArray(4));
    }

    @Override // org.xbill.DNS.Record
    String rrToString() {
        return Address.toDottedQuad(toArray(this.addr));
    }

    public InetAddress getAddress() {
        try {
            Name name = this.name;
            if (name == null) {
                return InetAddress.getByAddress(toArray(this.addr));
            }
            return InetAddress.getByAddress(name.toString(), toArray(this.addr));
        } catch (UnknownHostException unused) {
            return null;
        }
    }

    @Override // org.xbill.DNS.Record
    void rrToWire(DNSOutput dNSOutput, Compression compression, boolean z) {
        dNSOutput.writeU32(this.addr & 4294967295L);
    }
}
