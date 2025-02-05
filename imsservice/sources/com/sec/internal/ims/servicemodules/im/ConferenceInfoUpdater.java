package com.sec.internal.ims.servicemodules.im;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import com.sec.ims.options.CapabilityRefreshType;
import com.sec.ims.util.ImsUri;
import com.sec.internal.constants.ims.ImsConstants;
import com.sec.internal.constants.ims.servicemodules.im.ImConferenceParticipantInfo;
import com.sec.internal.constants.ims.servicemodules.im.ImError;
import com.sec.internal.constants.ims.servicemodules.im.ImIconData;
import com.sec.internal.constants.ims.servicemodules.im.ImParticipant;
import com.sec.internal.constants.ims.servicemodules.im.ImSubjectData;
import com.sec.internal.constants.ims.servicemodules.im.event.ImSessionConferenceInfoUpdateEvent;
import com.sec.internal.constants.ims.servicemodules.im.reason.CancelReason;
import com.sec.internal.ims.core.sim.SimManagerFactory;
import com.sec.internal.ims.registry.ImsRegistry;
import com.sec.internal.ims.servicemodules.im.DownloadFileTask;
import com.sec.internal.ims.servicemodules.im.data.event.ImSessionEvent;
import com.sec.internal.ims.servicemodules.im.listener.ImSessionListener;
import com.sec.internal.ims.servicemodules.im.strategy.IMnoStrategy;
import com.sec.internal.ims.servicemodules.im.translator.TranslatorCollection;
import com.sec.internal.ims.servicemodules.im.util.AsyncFileTask;
import com.sec.internal.ims.settings.RcsPolicySettings;
import com.sec.internal.ims.util.StringIdGenerator;
import com.sec.internal.ims.util.UriGenerator;
import com.sec.internal.interfaces.ims.servicemodules.options.ICapabilityDiscoveryModule;
import com.sec.internal.log.IMSLog;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* loaded from: classes.dex */
public class ConferenceInfoUpdater {
    protected static final String LOG_TAG = "ConferenceInfoUpdater";
    Map<ImParticipant, Date> mAddedParticipants;
    Context mContext;
    Set<ImParticipant> mDeletedParticipants;
    ImSession mImSession;
    Set<ImParticipant> mInsertedParticipants;
    boolean mIsLeaderChange;
    Map<ImParticipant, Date> mJoinedParticipants;
    Map<ImParticipant, Date> mKickedOutParticipants;
    Map<ImParticipant, Date> mLeftParticipants;
    private ImSessionListener mListener;
    IMnoStrategy mMnoStrategy;
    String mNewLeader;
    ImsUri mOwnUri;
    int mPhoneId;
    Set<ImParticipant> mUpdatedParticipants;
    UriGenerator mUriGenerator;

    protected ConferenceInfoUpdater(Context context, ImSession imSession, int i, ImsUri imsUri, IMnoStrategy iMnoStrategy, UriGenerator uriGenerator, ImSessionListener imSessionListener) {
        this.mContext = context;
        this.mPhoneId = i;
        this.mOwnUri = imsUri;
        this.mMnoStrategy = iMnoStrategy;
        this.mUriGenerator = uriGenerator;
        this.mListener = imSessionListener;
        this.mImSession = imSession;
    }

    protected void onConferenceInfoUpdated(ImSessionConferenceInfoUpdateEvent imSessionConferenceInfoUpdateEvent, String str) {
        if (this.mMnoStrategy == null) {
            IMSLog.e(LOG_TAG, "onConferenceInfoUpdated : Fail!! Strategy is null");
            return;
        }
        this.mAddedParticipants = new HashMap();
        this.mJoinedParticipants = new HashMap();
        this.mLeftParticipants = new HashMap();
        this.mKickedOutParticipants = new HashMap();
        this.mInsertedParticipants = new HashSet();
        this.mUpdatedParticipants = new HashSet();
        this.mDeletedParticipants = new HashSet();
        this.mIsLeaderChange = false;
        this.mNewLeader = null;
        handleParticipantUpdated(imSessionConferenceInfoUpdateEvent, str);
        if (imSessionConferenceInfoUpdateEvent.mConferenceInfoType == ImSessionConferenceInfoUpdateEvent.ImConferenceInfoType.FULL) {
            findAbsentParticipant(imSessionConferenceInfoUpdateEvent);
        }
        if (!this.mImSession.isVoluntaryDeparture()) {
            notifyParticipantsInfo();
        }
        if (isSubjectChanged(this.mImSession.getSubjectData(), imSessionConferenceInfoUpdateEvent.mSubjectData)) {
            IMSLog.s(LOG_TAG, "onConferenceInfoUpdated, event.mSubjectData= " + imSessionConferenceInfoUpdateEvent.mSubjectData + ", mChatData.getSubjectData()= " + this.mImSession.getSubjectData());
            this.mImSession.updateSubjectData(imSessionConferenceInfoUpdateEvent.mSubjectData);
            if (!this.mImSession.isVoluntaryDeparture()) {
                this.mListener.onChatSubjectUpdated(this.mImSession.getChatId(), imSessionConferenceInfoUpdateEvent.mSubjectData);
            }
        }
        if (imSessionConferenceInfoUpdateEvent.mIconData != null && !this.mImSession.isVoluntaryDeparture()) {
            onGroupChatIconUpdated(imSessionConferenceInfoUpdateEvent.mIconData);
        }
        if (TextUtils.isEmpty(imSessionConferenceInfoUpdateEvent.mTimeStamp)) {
            return;
        }
        IMSLog.s(LOG_TAG, "update timestamp " + imSessionConferenceInfoUpdateEvent.mTimeStamp);
        this.mImSession.updateConferenceTimestamp(imSessionConferenceInfoUpdateEvent);
    }

    private void handleParticipantUpdated(ImSessionConferenceInfoUpdateEvent imSessionConferenceInfoUpdateEvent, String str) {
        for (ImConferenceParticipantInfo imConferenceParticipantInfo : imSessionConferenceInfoUpdateEvent.mParticipantsInfo) {
            if (imConferenceParticipantInfo != null && imConferenceParticipantInfo.mUri != null) {
                String str2 = LOG_TAG;
                IMSLog.s(str2, "onConferenceInfoUpdated : " + imConferenceParticipantInfo.mUri);
                ImsUri normalizeUri = this.mImSession.mGetter.normalizeUri(imConferenceParticipantInfo.mUri);
                imConferenceParticipantInfo.mUri = normalizeUri;
                if (imConferenceParticipantInfo.mIsOwn || normalizeUri.equals(this.mOwnUri)) {
                    ownInfoUpdated(imConferenceParticipantInfo, str);
                } else {
                    ImParticipant participant = this.mImSession.getParticipant(imConferenceParticipantInfo.mUri);
                    ImParticipant.Status translateEngineParticipantInfo = TranslatorCollection.translateEngineParticipantInfo(imConferenceParticipantInfo, participant);
                    if (participant == null) {
                        newParticipantAdded(imConferenceParticipantInfo, translateEngineParticipantInfo);
                    } else {
                        ImParticipant.Status status = participant.getStatus();
                        IMSLog.s(str2, imConferenceParticipantInfo.mUri + " prevStatus=" + status + " status=" + translateEngineParticipantInfo);
                        if (translateEngineParticipantInfo != null && !translateEngineParticipantInfo.equals(status)) {
                            participantStatusUpdated(imConferenceParticipantInfo, participant, translateEngineParticipantInfo, status);
                        }
                        ImParticipant.Type type = imConferenceParticipantInfo.mIsChairman ? ImParticipant.Type.CHAIRMAN : ImParticipant.Type.REGULAR;
                        if (type != participant.getType()) {
                            this.mIsLeaderChange = true;
                            this.mImSession.logi("onConferenceInfoUpdated, mIsLeaderChange=true.");
                            participant.setType(type);
                            this.mUpdatedParticipants.add(participant);
                            if (type == ImParticipant.Type.CHAIRMAN) {
                                this.mNewLeader = participant.getUri().toString();
                            }
                        }
                        if (!TextUtils.isEmpty(imConferenceParticipantInfo.mDispName) || this.mImSession.mConfig.getRealtimeUserAliasAuth()) {
                            if (!imConferenceParticipantInfo.mDispName.equals(participant.getUserAlias())) {
                                participant.setUserAlias(imConferenceParticipantInfo.mDispName);
                                this.mUpdatedParticipants.add(participant);
                                if (!this.mImSession.isVoluntaryDeparture()) {
                                    this.mListener.onParticipantAliasUpdated(this.mImSession.getChatId(), participant);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean needToNotifyParticipantUpdates(ImConferenceParticipantInfo.ImConferenceUserElemState imConferenceUserElemState) {
        return (this.mMnoStrategy.boolSetting(RcsPolicySettings.RcsPolicy.CHECK_PARTICIPANT_OF_PARTIAL_STATE) && imConferenceUserElemState == ImConferenceParticipantInfo.ImConferenceUserElemState.PARTIAL) ? false : true;
    }

    private void downloadGroupIcon(final ImIconData imIconData) {
        ImConfig imConfig = this.mImSession.mConfig;
        if (!imConfig.getFtHttpEnabled()) {
            this.mImSession.logi("downloadGroupIcon: FT HTTP is not enabled.");
            return;
        }
        String str = this.mImSession.mGetter.onRequestIncomingFtTransferPath() + File.separatorChar + "received_files";
        File file = new File(str);
        if (!file.exists() && !file.mkdir()) {
            Log.e(LOG_TAG, "downloadGroupIcon: cannot create dir. Use default download directory.");
            str = this.mContext.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getPath();
        }
        final String str2 = str + File.separatorChar + StringIdGenerator.generateFileTransferId();
        HashMap hashMap = new HashMap();
        String iconUri = imIconData.getIconUri();
        if (!TextUtils.isEmpty(imConfig.getFtHttpDLUrl())) {
            hashMap.put(ImsConstants.FtDlParams.FT_DL_URL, iconUri);
            if (!TextUtils.isEmpty(this.mImSession.getConversationId())) {
                hashMap.put(ImsConstants.FtDlParams.FT_DL_CONV_ID, this.mImSession.getConversationId());
            }
        }
        DownloadFileTask.DownloadRequest downloadRequest = new DownloadFileTask.DownloadRequest(iconUri, 1L, 0L, str2, null, this.mMnoStrategy.getFtHttpUserAgent(imConfig), this.mImSession.mGetter.getNetwork(0), imConfig.isFtHttpTrustAllCerts(), imConfig.getFtHttpDLUrl(), hashMap, new DownloadFileTask.DownloadTaskCallback() { // from class: com.sec.internal.ims.servicemodules.im.ConferenceInfoUpdater.1
            @Override // com.sec.internal.ims.servicemodules.im.DownloadFileTask.DownloadTaskCallback
            public void onProgressUpdate(long j) {
                Log.i(ConferenceInfoUpdater.LOG_TAG, "Downloading Group Icon : " + j);
            }

            @Override // com.sec.internal.ims.servicemodules.im.DownloadFileTask.DownloadTaskCallback
            public void onCompleted(long j) {
                Log.i(ConferenceInfoUpdater.LOG_TAG, "Downloading Group Icon has been completed : " + j);
                imIconData.setIconLocation(str2);
                ImSession imSession = ConferenceInfoUpdater.this.mImSession;
                imSession.sendMessage(imSession.obtainMessage(ImSessionEvent.DOWNLOAD_GROUP_ICON_DONE, imIconData));
            }

            @Override // com.sec.internal.ims.servicemodules.im.DownloadFileTask.DownloadTaskCallback
            public void onCanceled(CancelReason cancelReason, int i, int i2) {
                Log.i(ConferenceInfoUpdater.LOG_TAG, "Downloading Group Icon was failed : " + cancelReason + " " + i + " " + i2);
            }
        });
        if (downloadRequest.isValid()) {
            new DownloadFileTask(this.mPhoneId, this.mContext, this.mImSession.getHandler().getLooper(), downloadRequest).execute(AsyncFileTask.THREAD_POOL_EXECUTOR);
        } else {
            Log.i(LOG_TAG, "Downloading Group Icon was failed due to invalid parameters");
        }
    }

    private void triggerCapability(ImParticipant imParticipant, CapabilityRefreshType capabilityRefreshType) {
        ICapabilityDiscoveryModule capabilityDiscoveryModule = ImsRegistry.getServiceModuleManager().getCapabilityDiscoveryModule();
        if (capabilityDiscoveryModule != null) {
            capabilityDiscoveryModule.getCapabilities(imParticipant.getUri(), capabilityRefreshType, SimManagerFactory.getPhoneId(this.mImSession.getChatData().getOwnIMSI()));
        }
    }

    private void ownInfoUpdated(ImConferenceParticipantInfo imConferenceParticipantInfo, String str) {
        String str2 = LOG_TAG;
        IMSLog.s(str2, "ownInfoUpdated");
        if (imConferenceParticipantInfo.mIsChairman) {
            String imsUri = imConferenceParticipantInfo.mUri.toString();
            this.mNewLeader = imsUri;
            if (str == null || !imsUri.equals(str)) {
                this.mListener.onGroupChatLeaderInformed(this.mImSession, this.mNewLeader);
                this.mImSession.mLeaderParticipant = this.mNewLeader;
            }
        }
        if (TextUtils.isEmpty(imConferenceParticipantInfo.mDispName) || imConferenceParticipantInfo.mDispName.equals(this.mImSession.getChatData().getOwnGroupAlias())) {
            return;
        }
        IMSLog.s(str2, "onConferenceInfoUpdated, old ownGroupAlias= " + this.mImSession.getChatData().getOwnGroupAlias() + ", new DispName=" + imConferenceParticipantInfo.mDispName);
        this.mImSession.getChatData().updateOwnGroupAlias(imConferenceParticipantInfo.mDispName);
    }

    private void newParticipantAdded(ImConferenceParticipantInfo imConferenceParticipantInfo, ImParticipant.Status status) {
        ImParticipant.Type type;
        ImParticipant.Status status2 = ImParticipant.Status.ACCEPTED;
        if (status == status2 || status == ImParticipant.Status.TO_INVITE) {
            ImParticipant.Type type2 = ImParticipant.Type.REGULAR;
            if (imConferenceParticipantInfo.mIsChairman) {
                String imsUri = imConferenceParticipantInfo.mUri.toString();
                this.mNewLeader = imsUri;
                ImParticipant.Type type3 = ImParticipant.Type.CHAIRMAN;
                this.mListener.onGroupChatLeaderChanged(this.mImSession, imsUri);
                this.mImSession.mLeaderParticipant = this.mNewLeader;
                type = type3;
            } else {
                type = type2;
            }
            ImParticipant imParticipant = new ImParticipant(this.mImSession.getChatId(), status, type, imConferenceParticipantInfo.mUri, imConferenceParticipantInfo.mDispName);
            this.mInsertedParticipants.add(imParticipant);
            if (status == status2) {
                this.mAddedParticipants.put(imParticipant, imConferenceParticipantInfo.mJoiningTime);
                return;
            }
            return;
        }
        this.mImSession.logi("Participant doesn't exist, nor status is connected/pending...ignore");
    }

    private void participantStatusUpdated(ImConferenceParticipantInfo imConferenceParticipantInfo, ImParticipant imParticipant, ImParticipant.Status status, ImParticipant.Status status2) {
        if (isJoinedParticipant(status, status2)) {
            this.mJoinedParticipants.put(imParticipant, imConferenceParticipantInfo.mJoiningTime);
        }
        if (isKickedOutParticipant(status2, imConferenceParticipantInfo.mUserElemState, imConferenceParticipantInfo.mDisconnectionReason)) {
            this.mKickedOutParticipants.put(imParticipant, imConferenceParticipantInfo.mDisconnectionTime);
        } else if (isLeftParticipant(status, status2, imConferenceParticipantInfo.mUserElemState)) {
            this.mLeftParticipants.put(imParticipant, imConferenceParticipantInfo.mDisconnectionTime);
        }
        ImParticipant.Status status3 = ImParticipant.Status.FAILED;
        if (status == status3 && imConferenceParticipantInfo.mDisconnectionCause == ImError.REMOTE_USER_INVALID) {
            this.mImSession.logi("invitation has failed with 404. update capability");
            triggerCapability(imParticipant, CapabilityRefreshType.ALWAYS_FORCE_REFRESH);
        }
        if (status == ImParticipant.Status.DECLINED || (status == status3 && this.mMnoStrategy.boolSetting(RcsPolicySettings.RcsPolicy.REMOVE_FAILED_PARTICIPANT_GROUPCHAT))) {
            imParticipant.setStatus(status);
            this.mDeletedParticipants.add(imParticipant);
        } else {
            if (isNonUpdateState(status, status2)) {
                return;
            }
            imParticipant.setStatus(status);
            this.mUpdatedParticipants.add(imParticipant);
        }
    }

    public void findAbsentParticipant(ImSessionConferenceInfoUpdateEvent imSessionConferenceInfoUpdateEvent) {
        boolean z;
        for (ImParticipant imParticipant : this.mImSession.getParticipants()) {
            Iterator<ImConferenceParticipantInfo> it = imSessionConferenceInfoUpdateEvent.mParticipantsInfo.iterator();
            while (true) {
                if (!it.hasNext()) {
                    z = true;
                    break;
                }
                ImConferenceParticipantInfo next = it.next();
                if (imParticipant.getUri() != null && imParticipant.getUri().equals(next.mUri)) {
                    z = false;
                    break;
                }
            }
            if (z) {
                ImParticipant.Status status = imParticipant.getStatus();
                ImParticipant.Status status2 = ImParticipant.Status.INVITED;
                if (status != status2 || this.mMnoStrategy.boolSetting(RcsPolicySettings.RcsPolicy.IGNORE_STATE_TO_FIND_ABSENT_PARTICIPANT)) {
                    this.mImSession.logi("onConferenceInfoUpdated, " + IMSLog.numberChecker(imParticipant.getUri()) + " is absent from updated full list.");
                    if (imParticipant.getStatus() != status2) {
                        this.mLeftParticipants.put(imParticipant, null);
                    }
                    imParticipant.setStatus(ImParticipant.Status.DECLINED);
                    this.mDeletedParticipants.add(imParticipant);
                }
            }
        }
    }

    public void notifyParticipantsInfo() {
        if (!this.mInsertedParticipants.isEmpty()) {
            this.mListener.onParticipantsInserted(this.mImSession, this.mInsertedParticipants);
        }
        if (!this.mUpdatedParticipants.isEmpty()) {
            this.mListener.onParticipantsUpdated(this.mImSession, this.mUpdatedParticipants);
        }
        if (!this.mDeletedParticipants.isEmpty()) {
            this.mListener.onParticipantsDeleted(this.mImSession, this.mDeletedParticipants);
            if (this.mImSession.getParticipants().isEmpty()) {
                this.mImSession.setSessionUri(null);
            }
        }
        if (!this.mAddedParticipants.isEmpty()) {
            this.mListener.onNotifyParticipantsAdded(this.mImSession, this.mAddedParticipants);
        }
        if (!this.mJoinedParticipants.isEmpty()) {
            this.mListener.onNotifyParticipantsJoined(this.mImSession, this.mJoinedParticipants);
        }
        if (!this.mLeftParticipants.isEmpty()) {
            this.mListener.onNotifyParticipantsLeft(this.mImSession, this.mLeftParticipants);
        }
        if (!this.mKickedOutParticipants.isEmpty()) {
            this.mListener.onNotifyParticipantsKickedOut(this.mImSession, this.mKickedOutParticipants);
        }
        if (!this.mIsLeaderChange || TextUtils.isEmpty(this.mNewLeader)) {
            return;
        }
        this.mListener.onGroupChatLeaderChanged(this.mImSession, this.mNewLeader);
        this.mImSession.mLeaderParticipant = this.mNewLeader;
    }

    public void onGroupChatIconUpdated(ImIconData imIconData) {
        IMSLog.s(LOG_TAG, "onConferenceInfoUpdated, event.mIconData= " + imIconData + ", mChatData.getIconData()= " + this.mImSession.getIconData());
        if (imIconData.getIconType() == ImIconData.IconType.ICON_TYPE_URI) {
            if (TextUtils.isEmpty(imIconData.getIconUri())) {
                this.mImSession.updateIconData(imIconData);
                this.mListener.onGroupChatIconDeleted(this.mImSession.getChatId());
                return;
            } else if (this.mImSession.getIconData() == null || !imIconData.getIconUri().equals(this.mImSession.getIconData().getIconUri()) || TextUtils.isEmpty(this.mImSession.getIconData().getIconLocation())) {
                downloadGroupIcon(imIconData);
                return;
            } else if (!new File(this.mImSession.getIconData().getIconLocation()).exists()) {
                downloadGroupIcon(imIconData);
                return;
            } else {
                imIconData.setIconLocation(this.mImSession.getIconData().getIconLocation());
                this.mImSession.updateIconData(imIconData);
                return;
            }
        }
        this.mImSession.updateIconData(imIconData);
        this.mListener.onGroupChatIconUpdated(this.mImSession.getChatId(), imIconData);
    }

    private boolean isJoinedParticipant(ImParticipant.Status status, ImParticipant.Status status2) {
        return status == ImParticipant.Status.ACCEPTED && (status2 == ImParticipant.Status.INITIAL || status2 == ImParticipant.Status.TO_INVITE || (status2 == ImParticipant.Status.INVITED && !this.mMnoStrategy.boolSetting(RcsPolicySettings.RcsPolicy.DISPLAY_INVITED_SYSTEMMESSAGE)));
    }

    private boolean isKickedOutParticipant(ImParticipant.Status status, ImConferenceParticipantInfo.ImConferenceUserElemState imConferenceUserElemState, ImConferenceParticipantInfo.ImConferenceDisconnectionReason imConferenceDisconnectionReason) {
        return imConferenceUserElemState == ImConferenceParticipantInfo.ImConferenceUserElemState.DELETED && imConferenceDisconnectionReason == ImConferenceParticipantInfo.ImConferenceDisconnectionReason.BOOTED && (status == ImParticipant.Status.ACCEPTED || status == ImParticipant.Status.PENDING || (status == ImParticipant.Status.INVITED && this.mMnoStrategy.boolSetting(RcsPolicySettings.RcsPolicy.DISPLAY_INVITED_SYSTEMMESSAGE)));
    }

    private boolean isLeftParticipant(ImParticipant.Status status, ImParticipant.Status status2, ImConferenceParticipantInfo.ImConferenceUserElemState imConferenceUserElemState) {
        return needToNotifyParticipantUpdates(imConferenceUserElemState) && status == ImParticipant.Status.DECLINED && (status2 == ImParticipant.Status.ACCEPTED || status2 == ImParticipant.Status.GONE || status2 == ImParticipant.Status.PENDING || (status2 == ImParticipant.Status.INVITED && this.mMnoStrategy.boolSetting(RcsPolicySettings.RcsPolicy.DISPLAY_INVITED_SYSTEMMESSAGE)));
    }

    private boolean isNonUpdateState(ImParticipant.Status status, ImParticipant.Status status2) {
        return status == ImParticipant.Status.TO_INVITE || (status == ImParticipant.Status.INVITED && (status2 == ImParticipant.Status.ACCEPTED || status2 == ImParticipant.Status.PENDING)) || !(status != ImParticipant.Status.GONE || status2 == ImParticipant.Status.ACCEPTED || status2 == ImParticipant.Status.PENDING);
    }

    private boolean isSubjectChanged(ImSubjectData imSubjectData, ImSubjectData imSubjectData2) {
        if (imSubjectData2 == null) {
            return false;
        }
        return !((imSubjectData == null || TextUtils.isEmpty(imSubjectData.getSubject())) ? "" : imSubjectData.getSubject()).equals(TextUtils.isEmpty(imSubjectData2.getSubject()) ? "" : imSubjectData2.getSubject());
    }
}
