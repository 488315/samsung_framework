package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.Accept;
import gov.nist.javax.sip.header.AcceptList;
import gov.nist.javax.sip.header.SIPHeader;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes3.dex */
public final class AcceptParser extends ParametersParser {
    public AcceptParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() {
        AcceptList acceptList = new AcceptList();
        headerName(2068);
        Accept accept = new Accept();
        accept.setHeaderName("Accept");
        this.lexer.SPorHT();
        this.lexer.match(4095);
        accept.setContentType(this.lexer.currentMatch.tokenValue);
        this.lexer.match(47);
        this.lexer.match(4095);
        accept.setContentSubType(this.lexer.currentMatch.tokenValue);
        this.lexer.SPorHT();
        parse(accept);
        acceptList.add((SIPHeader) accept);
        while (this.lexer.lookAhead(0) == ',') {
            this.lexer.match(44);
            this.lexer.SPorHT();
            Accept accept2 = new Accept();
            this.lexer.match(4095);
            accept2.setContentType(this.lexer.currentMatch.tokenValue);
            this.lexer.match(47);
            this.lexer.match(4095);
            accept2.setContentSubType(this.lexer.currentMatch.tokenValue);
            this.lexer.SPorHT();
            parse(accept2);
            acceptList.add((SIPHeader) accept2);
        }
        return acceptList;
    }

    public AcceptParser(Lexer lexer) {
        super(lexer);
    }
}
