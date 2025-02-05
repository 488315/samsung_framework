package com.sec.internal.ims.servicemodules.im;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import com.sec.ims.settings.ImsProfile;
import com.sec.ims.util.ImsUri;
import com.sec.internal.constants.ims.servicemodules.im.ChatData;
import com.sec.internal.constants.ims.servicemodules.im.ImParticipant;
import com.sec.internal.constants.ims.servicemodules.im.reason.ImErrorReason;
import com.sec.internal.helper.FileUtils;
import com.sec.internal.helper.SimUtil;
import com.sec.internal.ims.servicemodules.im.listener.IChatEventListener;
import com.sec.internal.ims.util.ImsUtil;
import com.sec.internal.imscr.LogClass;
import com.sec.internal.log.IMSLog;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* loaded from: classes.dex */
public class GcmHandler {
    private static final String LOG_TAG = ImSessionProcessor.class.getSimpleName();
    private ImCache mCache;
    private ImModule mImModule;
    private ImSessionProcessor mImSessionProcessor;
    private ImTranslation mImTranslation;

    public GcmHandler(ImModule imModule, ImCache imCache, ImSessionProcessor imSessionProcessor, ImTranslation imTranslation) {
        this.mImModule = imModule;
        this.mCache = imCache;
        this.mImSessionProcessor = imSessionProcessor;
        this.mImTranslation = imTranslation;
    }

    protected void addParticipants(String str, List<ImsUri> list) {
        String str2 = LOG_TAG;
        Log.i(str2, "AddParticipants: chatId=" + str + " participants=" + IMSLog.numberChecker(list));
        ImSession imSession = this.mCache.getImSession(str);
        if (imSession == null) {
            Iterator<IChatEventListener> it = this.mImSessionProcessor.mChatEventListeners.iterator();
            while (it.hasNext()) {
                it.next().onAddParticipantsFailed(str, list, ImErrorReason.ILLEGAL_SESSION_STATE);
            }
            return;
        }
        int phoneIdByIMSI = this.mImModule.getPhoneIdByIMSI(imSession.getOwnImsi());
        if (!this.mImModule.isRegistered(phoneIdByIMSI)) {
            Iterator<IChatEventListener> it2 = this.mImSessionProcessor.mChatEventListeners.iterator();
            while (it2.hasNext()) {
                it2.next().onAddParticipantsFailed(str, list, ImErrorReason.ILLEGAL_SESSION_STATE);
            }
            return;
        }
        ImsUtil.listToDumpFormat(LogClass.IM_ADD_PARTICIPANT, phoneIdByIMSI, str);
        ArrayList arrayList = new ArrayList(list);
        arrayList.removeAll(this.mImModule.getOwnUris(SimUtil.getSimSlotPriority()));
        if (arrayList.isEmpty()) {
            Log.e(str2, "addParticipants: requested for only own uri. Invalid.");
            Iterator<IChatEventListener> it3 = this.mImSessionProcessor.mChatEventListeners.iterator();
            while (it3.hasNext()) {
                it3.next().onAddParticipantsFailed(str, list, ImErrorReason.INVALID);
            }
            return;
        }
        if (imSession.getChatType() == ChatData.ChatType.ONE_TO_MANY_CHAT || imSession.getParticipantsSize() == 0) {
            ArrayList arrayList2 = new ArrayList();
            Iterator<ImsUri> it4 = arrayList.iterator();
            while (it4.hasNext()) {
                ImsUri normalizeUri = this.mImModule.normalizeUri(it4.next());
                if (normalizeUri != null && imSession.getParticipant(normalizeUri) == null) {
                    arrayList2.add(new ImParticipant(str, ImParticipant.Status.INVITED, ImParticipant.Type.REGULAR, normalizeUri, ""));
                }
            }
            if (!arrayList2.isEmpty()) {
                this.mImSessionProcessor.onParticipantsInserted(imSession, arrayList2);
            }
            this.mImSessionProcessor.onAddParticipantsSucceeded(str, arrayList);
            return;
        }
        imSession.addParticipants(arrayList);
    }

    protected void changeGroupAlias(String str, String str2) {
        Log.i(LOG_TAG, "changeGroupAlias: chatId=" + str + " alias=" + IMSLog.checker(str2));
        ImSession imSession = this.mCache.getImSession(str);
        if (imSession == null) {
            this.mImTranslation.onChangeGroupAliasFailed(str, str2, ImErrorReason.NO_SESSION);
            return;
        }
        if (!this.mImModule.isRegistered(this.mImModule.getPhoneIdByIMSI(imSession.getOwnImsi()))) {
            this.mImTranslation.onChangeGroupAliasFailed(str, str2, ImErrorReason.ILLEGAL_SESSION_STATE);
        } else {
            imSession.changeGroupAlias(str2);
        }
    }

    protected void changeGroupChatIcon(Context context, String str, String str2, Uri uri) {
        String str3 = LOG_TAG;
        Log.i(str3, "changeGroupChatIcon: chatId=" + str + " iconUri=" + uri);
        ImSession imSession = this.mCache.getImSession(str);
        if (imSession == null) {
            this.mImSessionProcessor.onChangeGroupChatIconFailed(str, str2, ImErrorReason.NO_SESSION);
            return;
        }
        if (!this.mImModule.isRegistered(this.mImModule.getPhoneIdByIMSI(imSession.getOwnImsi()))) {
            this.mImSessionProcessor.onChangeGroupChatIconFailed(str, str2, ImErrorReason.ILLEGAL_SESSION_STATE);
            return;
        }
        if (uri == null) {
            Log.e(str3, "Delete icon");
            imSession.changeGroupChatIcon(null);
            return;
        }
        String copyFileToCacheFromUri = FileUtils.copyFileToCacheFromUri(context, str2, uri);
        if (TextUtils.isEmpty(copyFileToCacheFromUri)) {
            Log.e(str3, "icon file doesn't exist");
            this.mImSessionProcessor.onChangeGroupChatIconFailed(str, str2, ImErrorReason.INVALID_ICON_PATH);
        } else {
            imSession.changeGroupChatIcon(copyFileToCacheFromUri);
        }
    }

    protected void changeGroupChatLeader(String str, List<ImsUri> list) {
        Log.i(LOG_TAG, "changeGroupChatLeader: chatId=" + str + " participants=" + IMSLog.numberChecker(list));
        ImSession imSession = this.mCache.getImSession(str);
        if (imSession == null) {
            Iterator<IChatEventListener> it = this.mImSessionProcessor.mChatEventListeners.iterator();
            while (it.hasNext()) {
                it.next().onChangeGroupChatLeaderFailed(str, list, ImErrorReason.NO_SESSION);
            }
            return;
        }
        if (!this.mImModule.isRegistered(this.mImModule.getPhoneIdByIMSI(imSession.getOwnImsi()))) {
            Iterator<IChatEventListener> it2 = this.mImSessionProcessor.mChatEventListeners.iterator();
            while (it2.hasNext()) {
                it2.next().onChangeGroupChatLeaderFailed(str, list, ImErrorReason.ILLEGAL_SESSION_STATE);
            }
            return;
        }
        imSession.changeGroupChatLeader(list);
    }

    protected void changeGroupChatSubject(String str, String str2) {
        Log.i(LOG_TAG, "changeGroupChatSubject: chatId=" + str + " subject=" + IMSLog.checker(str2));
        ImSession imSession = this.mCache.getImSession(str);
        if (imSession == null) {
            this.mImTranslation.onChangeGroupChatSubjectFailed(str, str2, ImErrorReason.NO_SESSION);
            return;
        }
        if (!SimUtil.getSimMno(this.mImModule.getPhoneIdByIMSI(imSession.getChatData().getOwnIMSI())).isEur() || ImsProfile.isRcsUp2Profile(this.mImModule.getRcsProfile())) {
            if (str2 == null) {
                str2 = "";
            }
            imSession.changeGroupChatSubject(str2);
            return;
        }
        this.mImTranslation.onChangeGroupChatSubjectFailed(str, str2, ImErrorReason.INVALID);
    }

    protected void removeParticipants(String str, List<ImsUri> list) {
        ImParticipant participant;
        Log.i(LOG_TAG, "removeParticipants: chatId=" + str + " participants=" + IMSLog.numberChecker(list));
        ImSession imSession = this.mCache.getImSession(str);
        if (imSession == null) {
            Iterator<IChatEventListener> it = this.mImSessionProcessor.mChatEventListeners.iterator();
            while (it.hasNext()) {
                it.next().onRemoveParticipantsFailed(str, list, ImErrorReason.NO_SESSION);
            }
            return;
        }
        int phoneIdByIMSI = this.mImModule.getPhoneIdByIMSI(imSession.getOwnImsi());
        ImsUtil.listToDumpFormat(LogClass.IM_REMOVE_PARTICIPANT, phoneIdByIMSI, str);
        if (!this.mImModule.isRegistered(phoneIdByIMSI)) {
            Iterator<IChatEventListener> it2 = this.mImSessionProcessor.mChatEventListeners.iterator();
            while (it2.hasNext()) {
                it2.next().onRemoveParticipantsFailed(str, list, ImErrorReason.ILLEGAL_SESSION_STATE);
            }
            return;
        }
        if (imSession.getChatType() == ChatData.ChatType.ONE_TO_MANY_CHAT) {
            ArrayList arrayList = new ArrayList();
            Iterator<ImsUri> it3 = list.iterator();
            while (it3.hasNext()) {
                ImsUri normalizeUri = this.mImModule.normalizeUri(it3.next());
                if (normalizeUri != null && (participant = imSession.getParticipant(normalizeUri)) != null) {
                    participant.setStatus(ImParticipant.Status.DECLINED);
                    arrayList.add(participant);
                }
            }
            if (!arrayList.isEmpty()) {
                this.mImSessionProcessor.onParticipantsDeleted(imSession, arrayList);
            }
            this.mImSessionProcessor.onRemoveParticipantsSucceeded(str, list);
            return;
        }
        Iterator<ImsUri> it4 = list.iterator();
        while (it4.hasNext()) {
            ImParticipant participant2 = imSession.getParticipant(it4.next());
            if (participant2 == null || (participant2.getStatus() != ImParticipant.Status.ACCEPTED && participant2.getStatus() != ImParticipant.Status.PENDING)) {
                Iterator<IChatEventListener> it5 = this.mImSessionProcessor.mChatEventListeners.iterator();
                while (it5.hasNext()) {
                    it5.next().onRemoveParticipantsFailed(str, list, ImErrorReason.PARTICIPANT_ALREADY_LEFT);
                }
                return;
            }
        }
        imSession.removeParticipants(list);
    }

    protected void updateParticipants(ImSession imSession, Set<ImsUri> set) {
        if (imSession == null || imSession.getChatStateId() != ChatData.State.NONE.getId()) {
            return;
        }
        HashSet hashSet = new HashSet(set);
        hashSet.removeAll(imSession.getParticipantsUri());
        ArrayList arrayList = new ArrayList();
        Iterator it = hashSet.iterator();
        while (it.hasNext()) {
            arrayList.add(new ImParticipant(imSession.getChatId(), (ImsUri) it.next()));
        }
        if (!arrayList.isEmpty()) {
            this.mCache.addParticipant(arrayList);
            imSession.addParticipant(arrayList);
        }
        HashSet hashSet2 = new HashSet(imSession.getParticipantsUri());
        hashSet2.removeAll(set);
        ArrayList arrayList2 = new ArrayList();
        Iterator it2 = hashSet2.iterator();
        while (it2.hasNext()) {
            ImParticipant participant = imSession.getParticipant((ImsUri) it2.next());
            if (participant != null) {
                participant.setStatus(ImParticipant.Status.DECLINED);
                arrayList2.add(participant);
            }
        }
        Log.i(LOG_TAG, "added participants : " + IMSLog.numberChecker(hashSet) + ", removed participants : " + IMSLog.numberChecker(hashSet2));
        if (arrayList2.isEmpty()) {
            return;
        }
        this.mCache.deleteParticipant(arrayList2);
        imSession.deleteParticipant(arrayList2);
    }
}
