package com.sec.internal.constants.ims;

import com.sec.ims.util.SipError;

/* loaded from: classes.dex */
public class SipErrorSbm extends SipErrorBase {
    public SipErrorSbm() {
        this.mDefaultRejectReason = SipErrorBase.DECLINE;
    }

    @Override // com.sec.internal.constants.ims.SipErrorBase
    public SipError getFromRejectReason(int i) {
        if (i < 0) {
            return SipErrorBase.DECLINE;
        }
        if (i == 3) {
            return SipErrorBase.DECLINE;
        }
        if (i == 12 || i == 14) {
            return SipErrorBase.FORBIDDEN;
        }
        return super.getFromRejectReason(i);
    }
}
