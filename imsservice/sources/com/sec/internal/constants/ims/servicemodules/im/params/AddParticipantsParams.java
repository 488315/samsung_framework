package com.sec.internal.constants.ims.servicemodules.im.params;

import android.os.Message;
import com.sec.ims.util.ImsUri;
import com.sec.internal.log.IMSLog;
import java.util.List;
import java.util.UUID;

/* loaded from: classes.dex */
public class AddParticipantsParams {
    public final Message mCallback;
    public final Object mRawHandle;
    public final List<ImsUri> mReceivers;
    public final String mReqKey = UUID.randomUUID().toString();
    public final String mSubject;

    public AddParticipantsParams(Object obj, List<ImsUri> list, Message message, String str) {
        this.mRawHandle = obj;
        this.mReceivers = list;
        this.mCallback = message;
        this.mSubject = str;
    }

    public String toString() {
        return "AddParticipantsParams [mRawHandle=" + this.mRawHandle + ", mReceivers=" + IMSLog.numberChecker(this.mReceivers) + ", mCallback=" + this.mCallback + ", mSubject=" + IMSLog.checker(this.mSubject) + ", mReqKey=" + this.mReqKey + "]";
    }
}
