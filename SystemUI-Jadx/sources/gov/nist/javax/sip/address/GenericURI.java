package gov.nist.javax.sip.address;

import java.text.ParseException;
import javax.sip.address.URI;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes3.dex */
public class GenericURI extends NetObject implements URI {
    private static final long serialVersionUID = 3237685256878068790L;
    protected String scheme;
    protected String uriString;

    public GenericURI() {
    }

    @Override // gov.nist.core.GenericObject
    public String encode() {
        return this.uriString;
    }

    @Override // gov.nist.javax.sip.address.NetObject, gov.nist.core.GenericObject
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof URI) {
            return toString().equalsIgnoreCase(((URI) obj).toString());
        }
        return false;
    }

    public String getScheme() {
        return this.scheme;
    }

    public final int hashCode() {
        return toString().hashCode();
    }

    @Override // gov.nist.javax.sip.address.NetObject, javax.sip.address.URI
    public String toString() {
        return encode();
    }

    public GenericURI(String str) {
        try {
            this.uriString = str;
            this.scheme = str.substring(0, str.indexOf(":"));
        } catch (Exception unused) {
            throw new ParseException("GenericURI, Bad URI format", 0);
        }
    }

    @Override // gov.nist.core.GenericObject
    public StringBuffer encode(StringBuffer stringBuffer) {
        stringBuffer.append(this.uriString);
        return stringBuffer;
    }
}
