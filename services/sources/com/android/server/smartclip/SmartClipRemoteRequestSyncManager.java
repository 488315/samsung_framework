package com.android.server.smartclip;

import android.content.Context;
import android.util.Log;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0;
import com.samsung.android.content.smartclip.SmartClipRemoteRequestResult;
import java.util.HashMap;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class SmartClipRemoteRequestSyncManager {
    public static final String TAG;
    public int mNextRequestId = 0;
    public final HashMap mRequestMap = new HashMap();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class RequestInfo {
        public boolean mResponseArrived;
        public SmartClipRemoteRequestResult mResultData;
        public Object mWaitObj;
    }

    static {
        Context context = SpenGestureManagerService.mContext;
        TAG = "SpenGestureManagerService";
    }

    public final int allocateNewRequestId(boolean z) {
        int i = this.mNextRequestId;
        this.mNextRequestId = i + 1;
        if (z) {
            RequestInfo requestInfo = new RequestInfo();
            requestInfo.mWaitObj = new Object();
            requestInfo.mResponseArrived = false;
            synchronized (this.mRequestMap) {
                this.mRequestMap.put(Integer.valueOf(i), requestInfo);
            }
        }
        return i;
    }

    public final RequestInfo getRequestItem(int i) {
        RequestInfo requestInfo;
        synchronized (this.mRequestMap) {
            requestInfo = (RequestInfo) this.mRequestMap.get(Integer.valueOf(i));
        }
        return requestInfo;
    }

    public final SmartClipRemoteRequestResult waitResult(int i) {
        RequestInfo requestInfo;
        RequestInfo requestItem = getRequestItem(i);
        if (requestItem == null) {
            ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(i, "waitResult : Could not find request info!! id = ", TAG);
            return null;
        }
        long currentTimeMillis = System.currentTimeMillis();
        synchronized (requestItem.mWaitObj) {
            if (!requestItem.mResponseArrived) {
                try {
                    requestItem.mWaitObj.wait(500);
                } catch (InterruptedException e) {
                    Log.e(TAG, "waitResult : e=" + e);
                }
            }
        }
        String str = TAG;
        StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(i, "waitResult : Unlocked. Id = ", " Elapsed = ");
        m.append(System.currentTimeMillis() - currentTimeMillis);
        m.append("ms");
        Log.i(str, m.toString());
        synchronized (this.mRequestMap) {
            requestInfo = (RequestInfo) this.mRequestMap.remove(Integer.valueOf(i));
        }
        return requestInfo.mResultData;
    }
}
