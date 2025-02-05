package com.sec.internal.interfaces.ims.config;

import com.sec.ims.IAutoConfigurationListener;

/* loaded from: classes.dex */
public interface ITelephonyAdapter {
    void cleanup();

    String getAppToken(boolean z);

    String getDeviceId(int i);

    String getExistingOtp();

    String getExistingPortOtp();

    String getIdentityByPhoneId(int i);

    String getIidToken();

    String getImei();

    String getImsi();

    String getMcc();

    String getMnc();

    String getMsisdn();

    String getMsisdn(int i);

    String getMsisdnNumber();

    String getNetType();

    String getOtp();

    String getPortOtp();

    String getPrimaryIdentity();

    String getSimCountryCode();

    String getSipUri();

    String getSmsDestPort();

    String getSmsOrigPort();

    String getSubscriberId(int i);

    boolean isReady();

    void notifyAutoConfigurationListener(int i, boolean z);

    void onADSChanged();

    void registerAutoConfigurationListener(IAutoConfigurationListener iAutoConfigurationListener);

    void registerUneregisterForOTP(boolean z);

    void sendIidToken(String str);

    void sendMsisdnNumber(String str);

    void sendVerificationCode(String str);

    void unregisterAutoConfigurationListener(IAutoConfigurationListener iAutoConfigurationListener);
}
