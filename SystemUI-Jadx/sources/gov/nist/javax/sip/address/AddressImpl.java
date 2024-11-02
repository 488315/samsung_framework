package gov.nist.javax.sip.address;

import gov.nist.core.Host;
import gov.nist.core.HostPort;
import javax.sip.address.Address;
import javax.sip.address.URI;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes3.dex */
public final class AddressImpl extends NetObject implements Address {
    private static final long serialVersionUID = 429592779568617259L;
    protected GenericURI address;
    protected int addressType = 1;
    protected String displayName;

    @Override // gov.nist.core.GenericObject
    public final Object clone() {
        AddressImpl addressImpl = (AddressImpl) super.clone();
        GenericURI genericURI = this.address;
        if (genericURI != null) {
            addressImpl.address = (GenericURI) genericURI.clone();
        }
        return addressImpl;
    }

    @Override // gov.nist.core.GenericObject
    public final String encode() {
        StringBuffer stringBuffer = new StringBuffer();
        encode(stringBuffer);
        return stringBuffer.toString();
    }

    @Override // gov.nist.javax.sip.address.NetObject, gov.nist.core.GenericObject
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Address) {
            return this.address.equals(((AddressImpl) ((Address) obj)).address);
        }
        return false;
    }

    public final int getAddressType() {
        return this.addressType;
    }

    public final HostPort getHostPort() {
        Host host;
        GenericURI genericURI = this.address;
        if (genericURI instanceof SipUri) {
            SipUri sipUri = (SipUri) genericURI;
            Authority authority = sipUri.authority;
            if (authority == null) {
                return null;
            }
            HostPort hostPort = authority.hostPort;
            if (hostPort == null) {
                host = null;
            } else {
                host = hostPort.getHost();
            }
            if (host == null) {
                return null;
            }
            return sipUri.authority.hostPort;
        }
        throw new RuntimeException("address is not a SipUri");
    }

    public final int hashCode() {
        return this.address.hashCode();
    }

    public final void setAddressType(int i) {
        this.addressType = i;
    }

    public final void setDisplayName(String str) {
        this.displayName = str;
        this.addressType = 1;
    }

    public final void setURI(URI uri) {
        this.address = (GenericURI) uri;
    }

    public final void setWildCardFlag() {
        this.addressType = 3;
        SipUri sipUri = new SipUri();
        this.address = sipUri;
        sipUri.setUser("*");
    }

    @Override // gov.nist.core.GenericObject
    public final StringBuffer encode(StringBuffer stringBuffer) {
        if (this.addressType == 3) {
            stringBuffer.append('*');
        } else {
            if (this.displayName != null) {
                stringBuffer.append("\"");
                stringBuffer.append(this.displayName);
                stringBuffer.append("\"");
                stringBuffer.append(" ");
            }
            if (this.address != null) {
                if (this.addressType == 1 || this.displayName != null) {
                    stringBuffer.append("<");
                }
                this.address.encode(stringBuffer);
                if (this.addressType == 1 || this.displayName != null) {
                    stringBuffer.append(">");
                }
            }
        }
        return stringBuffer;
    }
}
