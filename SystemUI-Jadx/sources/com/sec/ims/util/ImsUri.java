package com.sec.ims.util;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import com.sec.ims.IMSParameter;
import gov.nist.javax.sip.address.GenericURI;
import gov.nist.javax.sip.address.SipUri;
import gov.nist.javax.sip.address.TelURLImpl;
import gov.nist.javax.sip.parser.URLParser;
import java.text.ParseException;
import java.util.Iterator;
import java.util.regex.Pattern;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes3.dex */
public class ImsUri implements Parcelable {
    public static ImsUri EMPTY = null;
    private static final String LOG_TAG = "ImsUri";
    private SipUri mSipUri;
    private TelURLImpl mTelUri;
    private String mUrn;
    private static final boolean DBG = "eng".equals(Build.TYPE);
    private static final Pattern PATTERN_WHITE_SPACES = Pattern.compile("\\s+");
    public static final Parcelable.Creator<ImsUri> CREATOR = new Parcelable.Creator<ImsUri>() { // from class: com.sec.ims.util.ImsUri.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ImsUri createFromParcel(Parcel parcel) {
            return new ImsUri(parcel, 0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ImsUri[] newArray(int i) {
            return new ImsUri[i];
        }
    };

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes3.dex */
    public enum UriType {
        TEL_URI,
        SIP_URI,
        URN
    }

    public /* synthetic */ ImsUri(Parcel parcel, int i) {
        this(parcel);
    }

    public static ImsUri parse(String str) {
        URLParser uRLParser;
        if (str == null) {
            return null;
        }
        String replaceAll = PATTERN_WHITE_SPACES.matcher(str).replaceAll("");
        int indexOf = replaceAll.indexOf(58);
        if (indexOf < 0) {
            if (!DBG) {
                replaceAll = "xxxxx";
            }
            Log.e(LOG_TAG, "parse: illegal Uri - ".concat(replaceAll));
            return null;
        }
        String substring = replaceAll.substring(0, indexOf);
        try {
            uRLParser = new URLParser(replaceAll);
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder("parse: failured. uri=");
            if (!DBG) {
                replaceAll = "xxxxx";
            }
            sb.append(replaceAll);
            sb.append(" e=");
            sb.append(e);
            Log.e(LOG_TAG, sb.toString());
            e.printStackTrace();
        }
        if (!"sip".equalsIgnoreCase(substring) && !"sips".equalsIgnoreCase(substring)) {
            if ("tel".equalsIgnoreCase(substring)) {
                return new ImsUri(uRLParser.telURL(true));
            }
            if (IMSParameter.CALL.URN.equalsIgnoreCase(substring)) {
                return new ImsUri(replaceAll);
            }
            return null;
        }
        return new ImsUri(uRLParser.sipURL(true));
    }

    public static void removeUriParametersAndHeaders(ImsUri imsUri) {
        if (imsUri == null) {
            return;
        }
        imsUri.removeParams();
        imsUri.removeHeaders();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String encode() {
        String str = this.mUrn;
        if (str != null) {
            return str;
        }
        return uri().encode();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return uri().equals(((ImsUri) obj).uri());
    }

    public String getHost() {
        SipUri sipUri = this.mSipUri;
        if (sipUri == null) {
            return null;
        }
        return sipUri.getHost();
    }

    public String getMsisdn() {
        String user;
        TelURLImpl telURLImpl = this.mTelUri;
        if (telURLImpl != null) {
            if (telURLImpl.isGlobal()) {
                return "+" + this.mTelUri.getPhoneNumber();
            }
            return this.mTelUri.getPhoneNumber();
        }
        if (this.mUrn != null || (user = this.mSipUri.getUser()) == null) {
            return "";
        }
        int indexOf = user.indexOf(59);
        if (indexOf > 0) {
            return user.substring(0, indexOf);
        }
        return user;
    }

    public String getParam(String str) {
        SipUri sipUri = this.mSipUri;
        if (sipUri == null) {
            return null;
        }
        return sipUri.getParameter(str);
    }

    public String getPhoneContext() {
        TelURLImpl telURLImpl = this.mTelUri;
        if (telURLImpl != null) {
            return telURLImpl.getParameter("phone-context");
        }
        SipUri sipUri = this.mSipUri;
        if (sipUri != null) {
            return sipUri.getHost();
        }
        return "";
    }

    public int getPort() {
        SipUri sipUri = this.mSipUri;
        if (sipUri == null) {
            return 0;
        }
        return sipUri.getPort();
    }

    public String getScheme() {
        return uri().getScheme();
    }

    public UriType getUriType() {
        if (this.mSipUri != null) {
            return UriType.SIP_URI;
        }
        if (this.mTelUri != null) {
            return UriType.TEL_URI;
        }
        if (this.mUrn != null) {
            return UriType.URN;
        }
        return UriType.SIP_URI;
    }

    public String getUser() {
        SipUri sipUri = this.mSipUri;
        if (sipUri == null) {
            return null;
        }
        return sipUri.getUser();
    }

    public int hashCode() {
        int hashCode;
        SipUri sipUri = this.mSipUri;
        int i = 0;
        if (sipUri == null) {
            hashCode = 0;
        } else {
            hashCode = sipUri.hashCode();
        }
        int i2 = (hashCode + 31) * 31;
        TelURLImpl telURLImpl = this.mTelUri;
        if (telURLImpl != null) {
            i = telURLImpl.hashCode();
        }
        return i2 + i;
    }

    public void removeHeaders() {
        SipUri sipUri = this.mSipUri;
        if (sipUri == null) {
            return;
        }
        sipUri.removeHeaders();
    }

    public void removeParam(String str) {
        SipUri sipUri = this.mSipUri;
        if (sipUri == null) {
            return;
        }
        sipUri.removeParameter(str);
    }

    public void removeParams() {
        SipUri sipUri = this.mSipUri;
        if (sipUri == null) {
            return;
        }
        sipUri.removeParameters();
    }

    public void removeTelParams() {
        TelURLImpl telURLImpl = this.mTelUri;
        if (telURLImpl == null) {
            return;
        }
        Iterator parameterNames = telURLImpl.getParameterNames();
        while (parameterNames.hasNext()) {
            this.mTelUri.removeParameter((String) parameterNames.next());
        }
    }

    public void removeUserParam() {
        SipUri sipUri = this.mSipUri;
        if (sipUri == null) {
            return;
        }
        sipUri.removeParameter("user");
    }

    public void setParam(String str, String str2) {
        try {
            if (getUriType() == UriType.TEL_URI) {
                this.mTelUri.setParameter(str, str2);
            } else {
                SipUri sipUri = this.mSipUri;
                if (sipUri != null) {
                    sipUri.setParameter(str, str2);
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void setUserParam(String str) {
        SipUri sipUri = this.mSipUri;
        if (sipUri == null) {
            return;
        }
        sipUri.setUserParam(str);
    }

    public String toString() {
        String str = this.mUrn;
        if (str != null) {
            return str;
        }
        return uri().toString();
    }

    public String toStringLimit() {
        String msisdn = getMsisdn();
        if (msisdn != null && msisdn.length() > 2) {
            return msisdn.substring(msisdn.length() - 2);
        }
        return "";
    }

    public GenericURI uri() {
        TelURLImpl telURLImpl = this.mTelUri;
        if (telURLImpl != null) {
            return telURLImpl;
        }
        return this.mSipUri;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(uri().toString());
    }

    public ImsUri(String str) {
        this.mSipUri = null;
        this.mTelUri = null;
        this.mUrn = str;
    }

    public ImsUri(SipUri sipUri) {
        this.mUrn = null;
        this.mTelUri = null;
        this.mSipUri = sipUri;
    }

    public ImsUri(TelURLImpl telURLImpl) {
        this.mUrn = null;
        this.mSipUri = null;
        this.mTelUri = telURLImpl;
    }

    private ImsUri(Parcel parcel) {
        this.mUrn = null;
        this.mSipUri = null;
        this.mTelUri = null;
        ImsUri parse = parse(parcel.readString());
        this.mSipUri = parse.mSipUri;
        this.mTelUri = parse.mTelUri;
    }
}
