package gov.nist.javax.sip.header;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import javax.sip.InvalidArgumentException;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes3.dex */
public class Expires extends SIPHeader {
    private static final long serialVersionUID = 3134344915465784267L;
    protected int expires;

    public Expires() {
        super("Expires");
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final String encodeBody() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(this.expires);
        return stringBuffer.toString();
    }

    public final void setExpires(int i) {
        if (i >= 0) {
            this.expires = i;
            return;
        }
        throw new InvalidArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("bad argument ", i));
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final void encodeBody(StringBuffer stringBuffer) {
        stringBuffer.append(this.expires);
    }
}
