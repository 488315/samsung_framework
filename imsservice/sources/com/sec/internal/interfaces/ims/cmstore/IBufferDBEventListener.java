package com.sec.internal.interfaces.ims.cmstore;

import com.sec.internal.constants.ims.cmstore.CloudMessageBufferDBConstants;

/* loaded from: classes.dex */
public interface IBufferDBEventListener {
    void notifyAppCloudDeleteFail(String str, String str2, String str3);

    void notifyAppInitialSyncStatus(String str, String str2, String str3, CloudMessageBufferDBConstants.InitialSyncStatusFlag initialSyncStatusFlag, boolean z);

    void notifyAppOperationResult(String str, int i);

    void notifyCloudMessageUpdate(String str, String str2, String str3, boolean z);
}
