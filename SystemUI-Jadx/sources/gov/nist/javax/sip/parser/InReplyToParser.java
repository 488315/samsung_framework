package gov.nist.javax.sip.parser;

import gov.nist.core.LexerCore;
import gov.nist.core.Token;
import gov.nist.javax.sip.header.InReplyTo;
import gov.nist.javax.sip.header.InReplyToList;
import gov.nist.javax.sip.header.SIPHeader;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes3.dex */
public final class InReplyToParser extends HeaderParser {
    public InReplyToParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() {
        InReplyToList inReplyToList = new InReplyToList();
        headerName(2059);
        while (this.lexer.lookAhead(0) != '\n') {
            InReplyTo inReplyTo = new InReplyTo();
            inReplyTo.setHeaderName("In-Reply-To");
            this.lexer.match(4095);
            LexerCore lexerCore = this.lexer;
            Token token = lexerCore.currentMatch;
            if (lexerCore.lookAhead(0) == '@') {
                this.lexer.match(64);
                this.lexer.match(4095);
                inReplyTo.setCallId(token.tokenValue + "@" + this.lexer.currentMatch.tokenValue);
            } else {
                inReplyTo.setCallId(token.tokenValue);
            }
            this.lexer.SPorHT();
            inReplyToList.add((SIPHeader) inReplyTo);
            while (this.lexer.lookAhead(0) == ',') {
                this.lexer.match(44);
                this.lexer.SPorHT();
                InReplyTo inReplyTo2 = new InReplyTo();
                this.lexer.match(4095);
                LexerCore lexerCore2 = this.lexer;
                Token token2 = lexerCore2.currentMatch;
                if (lexerCore2.lookAhead(0) == '@') {
                    this.lexer.match(64);
                    this.lexer.match(4095);
                    inReplyTo2.setCallId(token2.tokenValue + "@" + this.lexer.currentMatch.tokenValue);
                } else {
                    inReplyTo2.setCallId(token2.tokenValue);
                }
                inReplyToList.add((SIPHeader) inReplyTo2);
            }
        }
        return inReplyToList;
    }

    public InReplyToParser(Lexer lexer) {
        super(lexer);
    }
}
