package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.Unsupported;
import gov.nist.javax.sip.header.UnsupportedList;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes3.dex */
public final class UnsupportedParser extends HeaderParser {
    public UnsupportedParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() {
        UnsupportedList unsupportedList = new UnsupportedList();
        headerName(2076);
        while (this.lexer.lookAhead(0) != '\n') {
            this.lexer.SPorHT();
            Unsupported unsupported = new Unsupported();
            unsupported.setHeaderName("Unsupported");
            this.lexer.match(4095);
            unsupported.setOptionTag(this.lexer.currentMatch.tokenValue);
            this.lexer.SPorHT();
            unsupportedList.add((SIPHeader) unsupported);
            while (this.lexer.lookAhead(0) == ',') {
                this.lexer.match(44);
                this.lexer.SPorHT();
                Unsupported unsupported2 = new Unsupported();
                this.lexer.match(4095);
                unsupported2.setOptionTag(this.lexer.currentMatch.tokenValue);
                this.lexer.SPorHT();
                unsupportedList.add((SIPHeader) unsupported2);
            }
        }
        return unsupportedList;
    }

    public UnsupportedParser(Lexer lexer) {
        super(lexer);
    }
}
