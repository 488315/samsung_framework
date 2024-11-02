package gov.nist.javax.sip.header;

import androidx.core.animation.ValueAnimator$$ExternalSyntheticOutline0;
import gov.nist.javax.sip.message.SIPRequest;
import javax.sip.InvalidArgumentException;
import javax.sip.header.CSeqHeader;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes3.dex */
public class CSeq extends SIPHeader implements CSeqHeader {
    private static final long serialVersionUID = -5405798080040422910L;
    protected String method;
    protected Long seqno;

    public CSeq() {
        super("CSeq");
    }

    @Override // gov.nist.javax.sip.header.SIPHeader, gov.nist.core.GenericObject
    public final String encode() {
        return this.headerName + ": " + encodeBody() + "\r\n";
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final String encodeBody() {
        StringBuffer stringBuffer = new StringBuffer();
        encodeBody(stringBuffer);
        return stringBuffer.toString();
    }

    @Override // gov.nist.javax.sip.header.SIPObject, gov.nist.core.GenericObject
    public final boolean equals(Object obj) {
        if (obj instanceof CSeqHeader) {
            CSeq cSeq = (CSeq) ((CSeqHeader) obj);
            if (this.seqno.longValue() == cSeq.seqno.longValue() && this.method.equals(cSeq.method)) {
                return true;
            }
        }
        return false;
    }

    public final String getMethod() {
        return this.method;
    }

    public final void setMethod(String str) {
        if (str != null) {
            this.method = SIPRequest.getCannonicalName(str);
            return;
        }
        throw new NullPointerException("JAIN-SIP Exception, CSeq, setMethod(), the meth parameter is null");
    }

    public final void setSeqNumber(long j) {
        if (j >= 0) {
            if (j <= 2147483648L) {
                this.seqno = Long.valueOf(j);
                return;
            }
            throw new InvalidArgumentException(ValueAnimator$$ExternalSyntheticOutline0.m("JAIN-SIP Exception, CSeq, setSequenceNumber(), the sequence number parameter is too large : ", j));
        }
        throw new InvalidArgumentException(ValueAnimator$$ExternalSyntheticOutline0.m("JAIN-SIP Exception, CSeq, setSequenceNumber(), the sequence number parameter is < 0 : ", j));
    }

    public CSeq(long j, String str) {
        this();
        this.seqno = Long.valueOf(j);
        this.method = SIPRequest.getCannonicalName(str);
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final void encodeBody(StringBuffer stringBuffer) {
        stringBuffer.append(this.seqno);
        stringBuffer.append(" ");
        stringBuffer.append(this.method.toUpperCase());
    }
}
