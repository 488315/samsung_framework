package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.WWWAuthenticate;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes3.dex */
public final class WWWAuthenticateParser extends ChallengeParser {
    public WWWAuthenticateParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() {
        headerName(2096);
        WWWAuthenticate wWWAuthenticate = new WWWAuthenticate();
        parse(wWWAuthenticate);
        return wWWAuthenticate;
    }

    public WWWAuthenticateParser(Lexer lexer) {
        super(lexer);
    }
}