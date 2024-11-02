package gov.nist.javax.sip.parser;

import gov.nist.core.LexerCore;
import gov.nist.core.Token;
import gov.nist.javax.sip.header.AuthenticationHeader;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes3.dex */
public abstract class ChallengeParser extends HeaderParser {
    public ChallengeParser(String str) {
        super(str);
    }

    public final void parse(AuthenticationHeader authenticationHeader) {
        this.lexer.SPorHT();
        this.lexer.match(4095);
        LexerCore lexerCore = this.lexer;
        Token token = lexerCore.currentMatch;
        lexerCore.SPorHT();
        authenticationHeader.setScheme(token.tokenValue);
        while (this.lexer.lookAhead(0) != '\n') {
            authenticationHeader.setParameter(nameValue());
            this.lexer.SPorHT();
            char lookAhead = this.lexer.lookAhead(0);
            if (lookAhead != '\n' && lookAhead != 0) {
                this.lexer.match(44);
                this.lexer.SPorHT();
            } else {
                return;
            }
        }
    }

    public ChallengeParser(Lexer lexer) {
        super(lexer);
    }
}
