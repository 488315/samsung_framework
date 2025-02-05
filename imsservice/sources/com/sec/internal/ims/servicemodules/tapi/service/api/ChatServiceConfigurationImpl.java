package com.sec.internal.ims.servicemodules.tapi.service.api;

import android.os.RemoteException;
import android.util.Log;
import com.gsma.services.rcs.chat.IChatServiceConfiguration;
import com.sec.internal.constants.ims.servicemodules.im.ImDefaultConst;
import com.sec.internal.constants.ims.servicemodules.im.ImSettings;
import com.sec.internal.ims.servicemodules.im.ImConfig;
import com.sec.internal.ims.util.RcsSettingsUtils;

/* loaded from: classes.dex */
public class ChatServiceConfigurationImpl extends IChatServiceConfiguration.Stub {
    private final String LOG_TAG = ChatServiceConfigurationImpl.class.getSimpleName();
    private ImConfig mImConfig;
    private RcsSettingsUtils rcsSetting;

    public int getGeolocExpirationTime() throws RemoteException {
        return 1800;
    }

    public int getGeolocLabelMaxLength() throws RemoteException {
        return 200;
    }

    public int getGroupChatMinParticipants() throws RemoteException {
        return 3;
    }

    public int getGroupChatSubjectMaxLength() throws RemoteException {
        return 100;
    }

    public int getIsComposingTimeout() throws RemoteException {
        return 20;
    }

    public ChatServiceConfigurationImpl(ImConfig imConfig) {
        this.rcsSetting = null;
        this.mImConfig = imConfig;
        this.rcsSetting = RcsSettingsUtils.getInstance();
    }

    public int getChatTimeout() throws RemoteException {
        ImConfig imConfig = this.mImConfig;
        if (imConfig == null) {
            return 0;
        }
        imConfig.getTimerIdle();
        return 0;
    }

    public int getGroupChatMaxParticipants() throws RemoteException {
        ImConfig imConfig = this.mImConfig;
        if (imConfig == null) {
            return 0;
        }
        imConfig.getMaxAdhocGroupSize();
        return 0;
    }

    public int getGroupChatMessageMaxLength() throws RemoteException {
        ImConfig imConfig = this.mImConfig;
        if (imConfig != null) {
            return (int) imConfig.getMaxSize1ToM();
        }
        return 0;
    }

    public int getOneToOneChatMessageMaxLength() throws RemoteException {
        ImConfig imConfig = this.mImConfig;
        if (imConfig != null) {
            return (int) imConfig.getMaxSize1To1();
        }
        return 0;
    }

    public boolean isChatSf() throws RemoteException {
        ImConfig imConfig = this.mImConfig;
        if (imConfig == null) {
            return false;
        }
        imConfig.isImCapAlwaysOn();
        return false;
    }

    public boolean isChatWarnSF() throws RemoteException {
        ImConfig imConfig = this.mImConfig;
        if (imConfig == null) {
            return false;
        }
        imConfig.isImWarnSf();
        return false;
    }

    public boolean isGroupChatSupported() throws RemoteException {
        ImConfig imConfig = this.mImConfig;
        if (imConfig == null) {
            return false;
        }
        imConfig.getGroupChatEnabled();
        return false;
    }

    public boolean isRespondToDisplayReportsEnabled() throws RemoteException {
        boolean booleanValue = ImDefaultConst.DEFAULT_CHAT_RESPOND_TO_DISPLAY_REPORTS.booleanValue();
        RcsSettingsUtils rcsSettingsUtils = this.rcsSetting;
        return rcsSettingsUtils != null ? Boolean.parseBoolean(rcsSettingsUtils.readParameter(ImSettings.CHAT_RESPOND_TO_DISPLAY_REPORTS)) : booleanValue;
    }

    public boolean isSmsFallback() throws RemoteException {
        ImConfig imConfig = this.mImConfig;
        if (imConfig == null) {
            return false;
        }
        imConfig.isSmsFallbackAuth();
        return false;
    }

    public void setRespondToDisplayReports(boolean z) throws RemoteException {
        Log.d(this.LOG_TAG, "setRespondToDisplayReports() enable=" + z);
        RcsSettingsUtils rcsSettingsUtils = this.rcsSetting;
        if (rcsSettingsUtils != null) {
            rcsSettingsUtils.writeBoolean(ImSettings.CHAT_RESPOND_TO_DISPLAY_REPORTS, z);
        }
    }
}
