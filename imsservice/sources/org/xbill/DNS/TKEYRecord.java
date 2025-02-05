package org.xbill.DNS;

import com.sec.internal.helper.httpclient.HttpController;
import java.io.IOException;
import java.util.Date;
import org.xbill.DNS.utils.base64;

/* loaded from: classes.dex */
public class TKEYRecord extends Record {
    private static final long serialVersionUID = 8828458121926391756L;
    private Name alg;
    private int error;
    private byte[] key;
    private int mode;
    private byte[] other;
    private Date timeExpire;
    private Date timeInception;

    TKEYRecord() {
    }

    @Override // org.xbill.DNS.Record
    Record getObject() {
        return new TKEYRecord();
    }

    @Override // org.xbill.DNS.Record
    void rrFromWire(DNSInput dNSInput) throws IOException {
        this.alg = new Name(dNSInput);
        this.timeInception = new Date(dNSInput.readU32() * 1000);
        this.timeExpire = new Date(dNSInput.readU32() * 1000);
        this.mode = dNSInput.readU16();
        this.error = dNSInput.readU16();
        int readU16 = dNSInput.readU16();
        if (readU16 > 0) {
            this.key = dNSInput.readByteArray(readU16);
        } else {
            this.key = null;
        }
        int readU162 = dNSInput.readU16();
        if (readU162 > 0) {
            this.other = dNSInput.readByteArray(readU162);
        } else {
            this.other = null;
        }
    }

    protected String modeString() {
        int i = this.mode;
        return i != 1 ? i != 2 ? i != 3 ? i != 4 ? i != 5 ? Integer.toString(i) : HttpController.METHOD_DELETE : "RESOLVERASSIGNED" : "GSSAPI" : "DIFFIEHELLMAN" : "SERVERASSIGNED";
    }

    @Override // org.xbill.DNS.Record
    String rrToString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(this.alg);
        stringBuffer.append(" ");
        if (Options.check("multiline")) {
            stringBuffer.append("(\n\t");
        }
        stringBuffer.append(FormattedTime.format(this.timeInception));
        stringBuffer.append(" ");
        stringBuffer.append(FormattedTime.format(this.timeExpire));
        stringBuffer.append(" ");
        stringBuffer.append(modeString());
        stringBuffer.append(" ");
        stringBuffer.append(Rcode.TSIGstring(this.error));
        if (Options.check("multiline")) {
            stringBuffer.append("\n");
            byte[] bArr = this.key;
            if (bArr != null) {
                stringBuffer.append(base64.formatString(bArr, 64, "\t", false));
                stringBuffer.append("\n");
            }
            byte[] bArr2 = this.other;
            if (bArr2 != null) {
                stringBuffer.append(base64.formatString(bArr2, 64, "\t", false));
            }
            stringBuffer.append(" )");
        } else {
            stringBuffer.append(" ");
            byte[] bArr3 = this.key;
            if (bArr3 != null) {
                stringBuffer.append(base64.toString(bArr3));
                stringBuffer.append(" ");
            }
            byte[] bArr4 = this.other;
            if (bArr4 != null) {
                stringBuffer.append(base64.toString(bArr4));
            }
        }
        return stringBuffer.toString();
    }

    @Override // org.xbill.DNS.Record
    void rrToWire(DNSOutput dNSOutput, Compression compression, boolean z) {
        this.alg.toWire(dNSOutput, null, z);
        dNSOutput.writeU32(this.timeInception.getTime() / 1000);
        dNSOutput.writeU32(this.timeExpire.getTime() / 1000);
        dNSOutput.writeU16(this.mode);
        dNSOutput.writeU16(this.error);
        byte[] bArr = this.key;
        if (bArr != null) {
            dNSOutput.writeU16(bArr.length);
            dNSOutput.writeByteArray(this.key);
        } else {
            dNSOutput.writeU16(0);
        }
        byte[] bArr2 = this.other;
        if (bArr2 != null) {
            dNSOutput.writeU16(bArr2.length);
            dNSOutput.writeByteArray(this.other);
        } else {
            dNSOutput.writeU16(0);
        }
    }
}
