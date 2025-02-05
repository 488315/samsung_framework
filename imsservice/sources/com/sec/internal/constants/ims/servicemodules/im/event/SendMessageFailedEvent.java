package com.sec.internal.constants.ims.servicemodules.im.event;

import com.android.internal.util.Preconditions;
import com.sec.internal.constants.ims.servicemodules.im.result.Result;

/* loaded from: classes.dex */
public class SendMessageFailedEvent {
    public final String mChatId;
    public final String mImdnId;
    public final Object mRawHandle;
    public final Result mResult;

    public SendMessageFailedEvent(Object obj, String str, String str2, Result result) {
        this.mRawHandle = obj;
        this.mChatId = str;
        this.mImdnId = str2;
        this.mResult = (Result) Preconditions.checkNotNull(result, "%s", new Object[]{"SendMessageFailedEvent: result is null."});
    }

    public String toString() {
        return "SendMessageFailedEvent [mRawHandle=" + this.mRawHandle + ", mChatId=" + this.mChatId + ", mImdnId=" + this.mImdnId + ", mResult=" + this.mResult + "]";
    }
}
