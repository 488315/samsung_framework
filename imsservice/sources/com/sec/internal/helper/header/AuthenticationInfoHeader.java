package com.sec.internal.helper.header;

/* loaded from: classes.dex */
public class AuthenticationInfoHeader extends AuthenticationHeaders {
    public static final String HEADER_PARAM_NEXTNONCE = "nextnonce";
    public static final String HEADER_PARAM_RSP_AUTH = "rspauth";
    private String cnonce;
    private String nextNonce;
    private String nonceCount;
    private String qop;
    private String rspauth;

    public void setQop(String str) {
        this.qop = str;
    }

    public void setRspauth(String str) {
        this.rspauth = str;
    }

    public void setCnonce(String str) {
        this.cnonce = str;
    }

    public String getNextNonce() {
        return this.nextNonce;
    }

    public void setNextNonce(String str) {
        this.nextNonce = str;
    }

    public String toString() {
        String str;
        String str2;
        String str3;
        String str4;
        StringBuilder sb = new StringBuilder();
        sb.append("AuthenticationInfoHeader [");
        String str5 = "";
        if (this.qop != null) {
            str = "qop=" + this.qop + ", ";
        } else {
            str = "";
        }
        sb.append(str);
        if (this.rspauth != null) {
            str2 = "rspauth=" + this.rspauth + ", ";
        } else {
            str2 = "";
        }
        sb.append(str2);
        if (this.cnonce != null) {
            str3 = "cnonce=" + this.cnonce + ", ";
        } else {
            str3 = "";
        }
        sb.append(str3);
        if (this.nonceCount != null) {
            str4 = "nonceCount=" + this.nonceCount;
        } else {
            str4 = "";
        }
        sb.append(str4);
        if (this.nextNonce != null) {
            str5 = "nextNonce=" + this.nextNonce;
        }
        sb.append(str5);
        sb.append("]");
        return sb.toString();
    }
}
