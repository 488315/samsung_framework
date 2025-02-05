package com.sec.internal.ims.mdmi;

import android.os.RemoteException;
import com.sec.ims.mdmi.IMdmiEventListener;
import com.sec.ims.mdmi.IMdmiService;
import com.sec.internal.ims.servicemodules.base.ServiceModuleBase;

/* loaded from: classes.dex */
public class MdmiService extends IMdmiService.Stub {
    MdmiServiceModule mdmiServiceModule;

    public MdmiService(ServiceModuleBase serviceModuleBase) {
        this.mdmiServiceModule = null;
        this.mdmiServiceModule = (MdmiServiceModule) serviceModuleBase;
    }

    public void registerMdmiEventListener(IMdmiEventListener iMdmiEventListener) throws RemoteException {
        this.mdmiServiceModule.setMdmiEventListener(iMdmiEventListener);
    }
}
