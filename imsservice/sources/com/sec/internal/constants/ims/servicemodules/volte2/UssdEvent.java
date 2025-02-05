package com.sec.internal.constants.ims.servicemodules.volte2;

import com.sec.ims.util.SipError;

/* loaded from: classes.dex */
public class UssdEvent {
    private int mSessionID = -1;
    private USSD_STATE mState = USSD_STATE.NOT_INITIALIZED;
    private int mStatus = -1;
    private int mDCS = -1;
    private byte[] mData = null;
    private SipError mSipErrorCode = null;

    public enum USSD_STATE {
        NOT_INITIALIZED,
        USSD_INDICATION,
        USSD_RESPONSE,
        USSD_ERROR
    }

    public void setSessionID(int i) {
        this.mSessionID = i;
    }

    public int getSessionID() {
        return this.mSessionID;
    }

    public void setState(USSD_STATE ussd_state) {
        this.mState = ussd_state;
    }

    public USSD_STATE getState() {
        return this.mState;
    }

    public void setStatus(int i) {
        this.mStatus = i;
    }

    public int getStatus() {
        return this.mStatus;
    }

    public void setDCS(int i) {
        this.mDCS = i;
    }

    public int getDCS() {
        return this.mDCS;
    }

    private void setData(byte[] bArr) {
        this.mData = bArr;
    }

    public void setData(Object obj) {
        if (obj instanceof String) {
            setData(((String) obj).getBytes());
        } else if (obj instanceof byte[]) {
            setData((byte[]) obj);
        } else if (obj instanceof Integer) {
            setData(((Integer) obj).toString().getBytes());
        }
    }

    public byte[] getData() {
        return this.mData;
    }

    public void setErrorCode(SipError sipError) {
        this.mSipErrorCode = sipError;
    }

    public SipError getErrorCode() {
        return this.mSipErrorCode;
    }
}
