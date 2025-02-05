package com.sec.internal.ims.config.adapters;

import com.sec.internal.helper.Preconditions;
import com.sec.internal.ims.core.sim.SimManagerFactory;
import com.sec.internal.ims.servicemodules.base.ModuleChannel;
import com.sec.internal.ims.servicemodules.euc.EucModule;
import com.sec.internal.ims.servicemodules.euc.data.AutoconfUserConsentData;
import com.sec.internal.interfaces.ims.config.IDialogAdapter;
import com.sec.internal.interfaces.ims.core.ISimManager;
import com.sec.internal.log.IMSLog;

/* loaded from: classes.dex */
public class DialogAdapterConsentDecorator implements IDialogAdapter {
    private static final String LOG_TAG = "DialogAdapterConsentDecorator";
    private final IDialogAdapter mDialogAdapter;
    private final int mPhoneId;

    public DialogAdapterConsentDecorator(IDialogAdapter iDialogAdapter, int i) {
        this.mDialogAdapter = (IDialogAdapter) Preconditions.checkNotNull(iDialogAdapter);
        this.mPhoneId = i;
    }

    @Override // com.sec.internal.interfaces.ims.config.IDialogAdapter
    public boolean getAcceptReject(String str, String str2, String str3, String str4) {
        boolean acceptReject = this.mDialogAdapter.getAcceptReject(str, str2, str3, str4);
        String ownIdentity = getOwnIdentity();
        if (ownIdentity != null) {
            ModuleChannel.createChannel(EucModule.class.getSimpleName(), null).sendEvent(7, new AutoconfUserConsentData(System.currentTimeMillis(), acceptReject, str, str2, ownIdentity), null);
        } else {
            IMSLog.i(LOG_TAG, "Could not obtain own identity! Ignoring user consent for EULA!");
        }
        return acceptReject;
    }

    @Override // com.sec.internal.interfaces.ims.config.IDialogAdapter
    public boolean getAcceptReject(String str, String str2, String str3, String str4, int i) {
        boolean acceptReject = this.mDialogAdapter.getAcceptReject(str, str2, str3, str4, i);
        String ownIdentity = getOwnIdentity();
        if (ownIdentity != null) {
            ModuleChannel.createChannel(EucModule.class.getSimpleName(), null).sendEvent(7, new AutoconfUserConsentData(System.currentTimeMillis(), acceptReject, str, str2, ownIdentity), null);
        } else {
            IMSLog.i(LOG_TAG, "Could not obtain own identity! Ignoring user consent for EULA!");
        }
        return acceptReject;
    }

    @Override // com.sec.internal.interfaces.ims.config.IDialogAdapter
    public String getMsisdn(String str) {
        return this.mDialogAdapter.getMsisdn(str);
    }

    @Override // com.sec.internal.interfaces.ims.config.IDialogAdapter
    public String getMsisdn(String str, String str2) {
        return this.mDialogAdapter.getMsisdn(str, str2);
    }

    @Override // com.sec.internal.interfaces.ims.config.IDialogAdapter
    public boolean getNextCancel() {
        return this.mDialogAdapter.getNextCancel();
    }

    private String getOwnIdentity() {
        ISimManager simManagerFromSimSlot = SimManagerFactory.getSimManagerFromSimSlot(this.mPhoneId);
        if (simManagerFromSimSlot != null) {
            return simManagerFromSimSlot.getImsi();
        }
        return null;
    }

    @Override // com.sec.internal.interfaces.ims.config.IDialogAdapter
    public void cleanup() {
        this.mDialogAdapter.cleanup();
    }
}
