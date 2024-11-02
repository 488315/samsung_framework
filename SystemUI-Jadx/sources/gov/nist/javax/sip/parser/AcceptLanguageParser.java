package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.AcceptLanguage;
import gov.nist.javax.sip.header.AcceptLanguageList;
import gov.nist.javax.sip.header.SIPHeader;
import javax.sip.InvalidArgumentException;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes3.dex */
public final class AcceptLanguageParser extends HeaderParser {
    public AcceptLanguageParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() {
        AcceptLanguageList acceptLanguageList = new AcceptLanguageList();
        headerName(2095);
        while (this.lexer.lookAhead(0) != '\n') {
            AcceptLanguage acceptLanguage = new AcceptLanguage();
            acceptLanguage.setHeaderName("Accept-Language");
            if (this.lexer.lookAhead(0) != ';') {
                this.lexer.match(4095);
                acceptLanguage.setLanguageRange(this.lexer.currentMatch.tokenValue);
            }
            while (this.lexer.lookAhead(0) == ';') {
                this.lexer.match(59);
                this.lexer.SPorHT();
                this.lexer.match(113);
                this.lexer.SPorHT();
                this.lexer.match(61);
                this.lexer.SPorHT();
                this.lexer.match(4095);
                try {
                    acceptLanguage.setQValue(Float.parseFloat(this.lexer.currentMatch.tokenValue));
                    this.lexer.SPorHT();
                } catch (NumberFormatException e) {
                    throw createParseException(e.getMessage());
                } catch (InvalidArgumentException e2) {
                    throw createParseException(e2.getMessage());
                }
            }
            acceptLanguageList.add((SIPHeader) acceptLanguage);
            if (this.lexer.lookAhead(0) == ',') {
                this.lexer.match(44);
                this.lexer.SPorHT();
            } else {
                this.lexer.SPorHT();
            }
        }
        return acceptLanguageList;
    }

    public AcceptLanguageParser(Lexer lexer) {
        super(lexer);
    }
}
