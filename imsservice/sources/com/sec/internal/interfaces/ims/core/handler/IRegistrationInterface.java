package com.sec.internal.interfaces.ims.core.handler;

import android.os.Bundle;
import com.sec.ims.options.Capabilities;
import com.sec.ims.settings.ImsProfile;
import com.sec.ims.util.SipError;
import com.sec.internal.constants.ims.gls.LocationInfo;
import com.sec.internal.helper.SimpleEventLog;
import com.sec.internal.ims.core.RegisterTask;
import com.sec.internal.interfaces.ims.core.IPdnController;
import com.sec.internal.interfaces.ims.core.IRegisterTask;
import com.sec.internal.interfaces.ims.core.IRegistrationHandlerNotifiable;
import com.sec.internal.interfaces.ims.core.ISimManager;
import com.sec.internal.interfaces.ims.core.IUserAgent;
import java.util.List;
import java.util.Set;

/* loaded from: classes.dex */
public interface IRegistrationInterface {
    void configure(int i);

    void deregisterInternal(IRegisterTask iRegisterTask, boolean z);

    void dump();

    String getImsiByUserAgent(IUserAgent iUserAgent);

    String getImsiByUserAgentHandle(int i);

    IUserAgent getUserAgent(int i);

    IUserAgent getUserAgent(String str);

    IUserAgent getUserAgent(String str, int i);

    IUserAgent getUserAgentByImsi(String str, String str2);

    IUserAgent[] getUserAgentByPhoneId(int i, String str);

    IUserAgent getUserAgentByRegId(int i);

    IUserAgent getUserAgentOnPdn(int i, int i2);

    void handleInactiveCiaOnMobileConnected(int i, RegisterTask registerTask);

    void handleInactiveCiaOnMobileDisconnected(int i, RegisterTask registerTask);

    boolean isUserAgentInRegistered(IRegisterTask iRegisterTask);

    void onDeregistered(IRegisterTask iRegisterTask, SipError sipError, long j, boolean z);

    void onRegisterError(IRegisterTask iRegisterTask, int i, SipError sipError, long j);

    boolean registerInternal(IRegisterTask iRegisterTask, String str, String str2, Set<String> set, Capabilities capabilities, String str3, String str4, String str5, String str6, String str7, List<String> list, Bundle bundle, Bundle bundle2, boolean z);

    void removePreviousLastPani(int i);

    void removeUserAgent(IRegisterTask iRegisterTask);

    void sendDmState(int i, boolean z);

    void sendDnsQuery(int i, String str, String str2, List<String> list, String str3, String str4, String str5, long j);

    void setEventLog(SimpleEventLog simpleEventLog);

    void setPdnController(IPdnController iPdnController);

    void setRegistrationHandler(IRegistrationHandlerNotifiable iRegistrationHandlerNotifiable);

    void setSilentLogEnabled(boolean z);

    void setSimManagers(List<ISimManager> list);

    boolean suspended(IRegisterTask iRegisterTask, boolean z);

    void updateGeolocation(IRegisterTask iRegisterTask, LocationInfo locationInfo);

    void updatePani(IRegisterTask iRegisterTask);

    void updateRat(IRegisterTask iRegisterTask, int i);

    void updateTimeInPlani(int i, ImsProfile imsProfile);

    void updateVceConfig(IRegisterTask iRegisterTask, boolean z);
}
