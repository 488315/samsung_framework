package gov.nist.javax.sip.header.ims;

import gov.nist.javax.sip.address.AddressImpl;
import gov.nist.javax.sip.header.AddressParametersHeader;
import javax.sip.header.HeaderAddress;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes3.dex */
public class PCalledPartyID extends AddressParametersHeader implements HeaderAddress {
    public PCalledPartyID(AddressImpl addressImpl) {
        super("P-Called-Party-ID");
        this.address = addressImpl;
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final String encodeBody() {
        StringBuffer stringBuffer = new StringBuffer();
        if (this.address.getAddressType() == 2) {
            stringBuffer.append("<");
        }
        stringBuffer.append(this.address.encode());
        if (this.address.getAddressType() == 2) {
            stringBuffer.append(">");
        }
        if (!this.parameters.isEmpty()) {
            stringBuffer.append(";" + this.parameters.encode());
        }
        return stringBuffer.toString();
    }

    public PCalledPartyID() {
        super("P-Called-Party-ID");
    }
}