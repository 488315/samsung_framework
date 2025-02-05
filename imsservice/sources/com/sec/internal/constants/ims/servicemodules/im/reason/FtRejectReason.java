package com.sec.internal.constants.ims.servicemodules.im.reason;

import com.sec.internal.constants.ims.entitilement.NSDSNamespaces;
import com.sec.internal.ims.core.handler.secims.imsCommonStruc.Id;

/* loaded from: classes.dex */
public enum FtRejectReason {
    FORBIDDEN_MAX_SIZE_EXCEEDED(403, 133, "Size Exceeded"),
    FORBIDDEN_SERVICE_NOT_AUTHORIZED(403, 127, "Service not authorised"),
    DECLINE,
    NOT_ACCEPTABLE_HERE(488, -1, "Not Acceptable Here"),
    SESSION_TIMEOUT(408, -1, "User not responding"),
    TEMPORARILY_UNAVAILABLE(NSDSNamespaces.NSDSHttpResponseCode.TEMPORARILY_UNAVAILABLE, -1, "Temporarily Unavailable"),
    BUSY_HERE(NSDSNamespaces.NSDSHttpResponseCode.BUSY_HERE, -1, "Busy Here");

    private final int mSipCode;
    private final int mWarningCode;
    private final String mWarningText;

    FtRejectReason() {
        this.mSipCode = Id.REQUEST_UPDATE_TIME_IN_PLANI;
        this.mWarningCode = -1;
        this.mWarningText = "Decline";
    }

    FtRejectReason(int i, int i2, String str) {
        this.mSipCode = i;
        this.mWarningCode = i2;
        this.mWarningText = str;
    }

    public int getSipCode() {
        return this.mSipCode;
    }

    public int getWarningCode() {
        return this.mWarningCode;
    }

    public String getWarningText() {
        return this.mWarningText;
    }
}
