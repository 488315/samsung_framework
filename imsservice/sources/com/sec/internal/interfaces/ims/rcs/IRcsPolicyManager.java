package com.sec.internal.interfaces.ims.rcs;

import android.os.Bundle;
import com.sec.ims.settings.ImsProfile;
import com.sec.ims.util.ImsUri;
import com.sec.internal.constants.Mno;
import com.sec.internal.helper.RegiConfig;
import com.sec.internal.ims.core.RcsRegistration;
import com.sec.internal.ims.servicemodules.im.ImConfig;
import com.sec.internal.interfaces.ims.core.IPdnController;
import com.sec.internal.interfaces.ims.core.IRegisterTask;
import com.sec.internal.interfaces.ims.core.ISequentialInitializable;
import java.util.List;

/* loaded from: classes.dex */
public interface IRcsPolicyManager extends ISequentialInitializable {
    public static final String LOG_TAG = "RcsPolicyMgr";

    String changeRcsIfacename(IRegisterTask iRegisterTask, IPdnController iPdnController, String str);

    boolean doRcsConfig(IRegisterTask iRegisterTask, List<IRegisterTask> list);

    Bundle getRcsConfigForUserAgent(ImsProfile imsProfile, Mno mno, int i, int i2, ImConfig imConfig, RcsRegistration.Builder builder);

    String getRcsHomeNetworkDomain(ImsProfile imsProfile, int i);

    ImsUri.UriType getRcsNetworkUriType(int i, String str, boolean z);

    String getRcsPcscfAddress(ImsProfile imsProfile, int i);

    String getRcsPrivateUserIdentity(String str, ImsProfile imsProfile, int i);

    String getRcsPublicUserIdentity(int i);

    RegiConfig getRegiConfig(int i);

    boolean isRcsRoamingPref(IRegisterTask iRegisterTask, boolean z);

    boolean pendingRcsRegister(IRegisterTask iRegisterTask, List<IRegisterTask> list, int i);

    String selectRcsDnsType(IRegisterTask iRegisterTask, List<String> list);

    String selectRcsTransportType(IRegisterTask iRegisterTask, String str);

    boolean tryRcsConfig(IRegisterTask iRegisterTask);

    void updateDualRcsPcscfIp(IRegisterTask iRegisterTask, List<String> list);

    void updateRegiConfig(int i);
}
