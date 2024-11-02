package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.CallID;
import gov.nist.javax.sip.header.SIPHeader;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes3.dex */
public final class CallIDParser extends HeaderParser {
    public CallIDParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() {
        this.lexer.match(2088);
        this.lexer.SPorHT();
        this.lexer.match(58);
        this.lexer.SPorHT();
        CallID callID = new CallID();
        this.lexer.SPorHT();
        callID.setCallId(this.lexer.getRest().trim());
        return callID;
    }

    public CallIDParser(Lexer lexer) {
        super(lexer);
    }
}
