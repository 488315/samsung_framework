package com.sec.internal.constants.ims.servicemodules.im.result;

import com.android.internal.util.Preconditions;
import com.sec.internal.constants.ims.servicemodules.im.ImError;
import com.sec.internal.constants.ims.servicemodules.im.MsrpResponse;
import com.sec.internal.constants.ims.servicemodules.im.SipResponse;
import com.sec.internal.helper.header.AuthenticationHeaders;

/* loaded from: classes.dex */
public class Result {
    private ImError mImError;
    private MsrpResponse mMsrpResponse;
    private ReasonHeader mReasonHdr;
    private SipResponse mSipResponse;
    private Type mType;
    private WarningHeader mWarningHdr;

    public enum Type {
        NONE,
        SUCCESS,
        SIP_ERROR,
        MSRP_ERROR,
        HTTP_ERROR,
        ENGINE_ERROR,
        SESSION_RELEASE,
        NETWORK_ERROR,
        SESSION_RSRC_UNAVAILABLE,
        REMOTE_PARTY_CANCELED,
        DEVICE_UNREGISTERED,
        SIP_PROVISIONAL,
        UNKNOWN_ERROR,
        DEDICATED_BEARER_ERROR
    }

    public Result(ImError imError, Type type) {
        init(imError, type, null, null, null, null);
    }

    public Result(ImError imError, Result result) {
        init(imError, result.mType, result.mSipResponse, result.mMsrpResponse, result.mWarningHdr, result.mReasonHdr);
    }

    public Result(ImError imError, Type type, SipResponse sipResponse, MsrpResponse msrpResponse, WarningHeader warningHeader, ReasonHeader reasonHeader) {
        init(imError, type, sipResponse, msrpResponse, warningHeader, reasonHeader);
    }

    private void init(ImError imError, Type type, SipResponse sipResponse, MsrpResponse msrpResponse, WarningHeader warningHeader, ReasonHeader reasonHeader) {
        this.mImError = (ImError) Preconditions.checkNotNull(imError, "%s", new Object[]{"init: imError is null."});
        this.mType = (Type) Preconditions.checkNotNull(type, "%s", new Object[]{"init: type is null."});
        this.mSipResponse = sipResponse;
        this.mMsrpResponse = msrpResponse;
        this.mWarningHdr = warningHeader;
        this.mReasonHdr = reasonHeader;
    }

    public static class WarningHeader {
        private final int mCode;
        private final String mText;

        public WarningHeader(int i, String str) {
            this.mCode = i;
            this.mText = str;
        }

        public int getCode() {
            return this.mCode;
        }

        public String getText() {
            return this.mText;
        }

        public String toString() {
            return "WarningHeader{mCode=" + this.mCode + ", mText='" + this.mText + "'}";
        }
    }

    public static class ReasonHeader {
        private final int mCode;
        private final String mText;

        public ReasonHeader(int i, String str) {
            this.mCode = i;
            this.mText = str;
        }

        public int getCode() {
            return this.mCode;
        }

        public String getText() {
            return this.mText;
        }

        public String toString() {
            return "ReasonHeader{mCode=" + this.mCode + ", mText='" + this.mText + "'}";
        }
    }

    public ImError getImError() {
        return this.mImError;
    }

    public Type getType() {
        return this.mType;
    }

    public SipResponse getSipResponse() {
        return this.mSipResponse;
    }

    public MsrpResponse getMsrpResponse() {
        return this.mMsrpResponse;
    }

    public WarningHeader getWarningHdr() {
        return this.mWarningHdr;
    }

    public ReasonHeader getReasonHdr() {
        return this.mReasonHdr;
    }

    public String toString() {
        return "Result [" + toString(new Title(false)) + "]";
    }

    private String toString(Title title) {
        String str = title.getTypeTitle() + AuthenticationHeaders.HEADER_PRARAM_SPERATOR + this.mType;
        if (this.mImError != null) {
            str = str + "," + title.getTypeImError() + AuthenticationHeaders.HEADER_PRARAM_SPERATOR + this.mImError;
        }
        if (this.mSipResponse != null) {
            str = str + "," + title.getSipResponseTitle() + AuthenticationHeaders.HEADER_PRARAM_SPERATOR + this.mSipResponse;
        }
        if (this.mMsrpResponse != null) {
            str = str + "," + title.getMsrpResponseTitle() + AuthenticationHeaders.HEADER_PRARAM_SPERATOR + this.mMsrpResponse;
        }
        if (this.mWarningHdr != null) {
            str = str + "," + title.getWarningHeaderTitle() + AuthenticationHeaders.HEADER_PRARAM_SPERATOR + this.mWarningHdr;
        }
        if (this.mReasonHdr == null) {
            return str;
        }
        return str + "," + title.getReasonHeaderTitle() + AuthenticationHeaders.HEADER_PRARAM_SPERATOR + this.mReasonHdr;
    }

    public String toCriticalLog() {
        return toString(new Title(true));
    }
}
