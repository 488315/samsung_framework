package com.sec.internal.constants.ims;

/* loaded from: classes.dex */
public class SipReasonRjil extends SipReason {
    public static final SipReason INTER_RAT = new SipReason("SIP", 0, "RAT changed", new String[0]);
    public static final SipReason LOW_BATTERY = new SipReason("SIP", 0, "Low battery", new String[0]);
    public static final SipReason OUT_OF_BATTERY = new SipReason("SIP", 0, "Out of battery", new String[0]);
    public static final SipReason USER_TRIGGERED = new SipReason("SIP", 0, "User Disconnected", new String[0]);
    public static final SipReason VOPS_DISABLED = new SipReason("SIP", 0, "Moved to LTE without VoLTE support", new String[0]);
    public static final SipReason NETWORK_COVERAGE_LOST = new SipReason("SIP", 0, "Network Coverage Lost", new String[0]);
    public static final SipReason UNKNOWN = new SipReason("SIP", 0, "Internal Error", new String[0]);

    @Override // com.sec.internal.constants.ims.SipReason
    public SipReason getFromUserReason(int i) {
        if (i < 0) {
            i = 5;
        }
        if (i == 4) {
            return INTER_RAT;
        }
        if (i == 5) {
            return USER_TRIGGERED;
        }
        if (i == 6) {
            return LOW_BATTERY;
        }
        if (i == 9) {
            return VOPS_DISABLED;
        }
        if (i == 10) {
            return OUT_OF_BATTERY;
        }
        if (i == 24) {
            return NETWORK_COVERAGE_LOST;
        }
        return UNKNOWN;
    }
}
