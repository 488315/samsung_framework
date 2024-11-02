package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.TimeStamp;
import javax.sip.InvalidArgumentException;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes3.dex */
public final class TimeStampParser extends HeaderParser {
    public TimeStampParser(String str) {
        super(str);
    }

    @Override // gov.nist.javax.sip.parser.HeaderParser
    public final SIPHeader parse() {
        TimeStamp timeStamp = new TimeStamp();
        headerName(2103);
        timeStamp.setHeaderName("Timestamp");
        this.lexer.SPorHT();
        String number = this.lexer.number();
        try {
            if (this.lexer.lookAhead(0) == '.') {
                this.lexer.match(46);
                timeStamp.setTimeStamp(Float.parseFloat(number + "." + this.lexer.number()));
            } else {
                timeStamp.setTime(Long.parseLong(number));
            }
            this.lexer.SPorHT();
            if (this.lexer.lookAhead(0) != '\n') {
                String number2 = this.lexer.number();
                try {
                    if (this.lexer.lookAhead(0) == '.') {
                        this.lexer.match(46);
                        timeStamp.setDelay(Float.parseFloat(number2 + "." + this.lexer.number()));
                    } else {
                        timeStamp.setDelay(Integer.parseInt(number2));
                    }
                } catch (NumberFormatException e) {
                    throw createParseException(e.getMessage());
                } catch (InvalidArgumentException e2) {
                    throw createParseException(e2.getMessage());
                }
            }
            return timeStamp;
        } catch (NumberFormatException e3) {
            throw createParseException(e3.getMessage());
        } catch (InvalidArgumentException e4) {
            throw createParseException(e4.getMessage());
        }
    }

    public TimeStampParser(Lexer lexer) {
        super(lexer);
    }
}
