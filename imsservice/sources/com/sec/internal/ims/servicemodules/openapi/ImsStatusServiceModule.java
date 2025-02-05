package com.sec.internal.ims.servicemodules.openapi;

import android.content.Intent;
import android.os.Looper;
import android.os.RemoteException;
import com.sec.ims.IImsRegistrationListener;
import com.sec.ims.ImsRegistration;
import com.sec.ims.volte2.IImsCallEventListener;
import com.sec.ims.volte2.IVolteService;
import com.sec.internal.ims.registry.ImsRegistry;
import com.sec.internal.ims.servicemodules.base.ServiceModuleBase;
import com.sec.internal.interfaces.ims.servicemodules.openapi.IImsStatusServiceModule;

/* loaded from: classes.dex */
public class ImsStatusServiceModule extends ServiceModuleBase implements IImsStatusServiceModule {
    private IVolteService mVolteService;

    @Override // com.sec.internal.ims.servicemodules.base.ServiceModuleBase
    public void handleIntent(Intent intent) {
    }

    @Override // com.sec.internal.ims.servicemodules.base.ServiceModuleBase
    public void onDeregistering(ImsRegistration imsRegistration) {
    }

    public ImsStatusServiceModule(Looper looper, IVolteService iVolteService) {
        super(looper);
        this.mVolteService = iVolteService;
    }

    @Override // com.sec.internal.interfaces.ims.servicemodules.base.IServiceModule
    public String[] getServicesRequiring() {
        return new String[]{"mmtel"};
    }

    @Override // com.sec.internal.ims.servicemodules.base.ServiceModuleBase, com.sec.internal.interfaces.ims.servicemodules.base.IServiceModule
    public void start() {
        super.start();
    }

    @Override // com.sec.internal.ims.servicemodules.base.ServiceModuleBase, com.sec.internal.interfaces.ims.servicemodules.base.IServiceModule
    public void onRegistered(ImsRegistration imsRegistration) {
        super.onRegistered(imsRegistration);
    }

    @Override // com.sec.internal.ims.servicemodules.base.ServiceModuleBase, com.sec.internal.interfaces.ims.servicemodules.base.IServiceModule
    public void onDeregistered(ImsRegistration imsRegistration, int i) {
        super.onDeregistered(imsRegistration, i);
    }

    public void registerImsRegistrationListener(IImsRegistrationListener iImsRegistrationListener) throws RemoteException {
        if (ImsRegistry.isReady()) {
            ImsRegistry.registerImsRegistrationListener(iImsRegistrationListener);
        }
    }

    public void unregisterImsRegistrationListener(IImsRegistrationListener iImsRegistrationListener) throws RemoteException {
        if (ImsRegistry.isReady()) {
            ImsRegistry.unregisterImsRegistrationListener(iImsRegistrationListener);
        }
    }

    public void registerImsCallEventListener(IImsCallEventListener iImsCallEventListener) throws RemoteException {
        this.mVolteService.registerForCallStateEvent(iImsCallEventListener);
    }

    public void unregisterImsCallEventListener(IImsCallEventListener iImsCallEventListener) throws RemoteException {
        this.mVolteService.deregisterForCallStateEvent(iImsCallEventListener);
    }

    public int[] getCallCount() throws RemoteException {
        if (ImsRegistry.isReady()) {
            return ImsRegistry.getCallCount(-1);
        }
        return null;
    }
}
