package gov.nist.javax.sip.parser.extensions;

import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.extensions.MinSE;
import gov.nist.javax.sip.parser.Lexer;
import gov.nist.javax.sip.parser.ParametersParser;
import javax.sip.InvalidArgumentException;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes3.dex */
public final class MinSEParser extends ParametersParser {
    public MinSEParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() {
        MinSE minSE = new MinSE();
        headerName(2134);
        try {
            minSE.setExpires(Integer.parseInt(this.lexer.ttoken()));
            this.lexer.SPorHT();
            parse(minSE);
            return minSE;
        } catch (NumberFormatException unused) {
            throw createParseException("bad integer format");
        } catch (InvalidArgumentException e) {
            throw createParseException(e.getMessage());
        }
    }

    public MinSEParser(Lexer lexer) {
        super(lexer);
    }
}
