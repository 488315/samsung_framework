package com.sec.internal.constants.ims.servicemodules.im.event;

/* loaded from: classes.dex */
public class ChatbotAnonymizeNotifyEvent {
    public String mChatbotUri;
    public String mCommandId;
    public String mResult;

    public ChatbotAnonymizeNotifyEvent(String str, String str2, String str3) {
        this.mChatbotUri = str;
        this.mResult = str2;
        this.mCommandId = str3;
    }

    public String toString() {
        return "ChatbotAnonymizeNotifyEvent   [mResult = " + this.mResult + ", mCommandId = " + this.mCommandId + "]";
    }
}
