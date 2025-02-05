package com.sec.internal.ims.cmstore.omanetapi.nms;

import android.util.Log;
import com.sec.internal.constants.ims.cmstore.omanetapi.SyncMsgType;
import com.sec.internal.helper.httpclient.HttpRequestParams;
import com.sec.internal.helper.httpclient.HttpResponseParams;
import com.sec.internal.ims.cmstore.MessageStoreClient;
import com.sec.internal.ims.cmstore.params.BufferDBChangeParam;
import com.sec.internal.ims.cmstore.params.ParamOMAresponseforBufDB;
import com.sec.internal.interfaces.ims.cmstore.IAPICallFlowListener;
import com.sec.internal.omanetapi.nms.BaseNMSRequest;
import com.sec.internal.omanetapi.nms.IndividualFlag;
import java.io.IOException;

/* loaded from: classes.dex */
public class CloudMessageDeleteObjectFlag extends IndividualFlag {
    private static final long serialVersionUID = 8158555957984259234L;
    private String TAG;

    public CloudMessageDeleteObjectFlag(final IAPICallFlowListener iAPICallFlowListener, String str, String str2, final BufferDBChangeParam bufferDBChangeParam, MessageStoreClient messageStoreClient) {
        super(messageStoreClient.getCloudMessageStrategyManager().getStrategy().getNmsHost(), messageStoreClient.getCloudMessageStrategyManager().getStrategy().getOMAApiVersion(), messageStoreClient.getCloudMessageStrategyManager().getStrategy().getStoreName(), bufferDBChangeParam.mLine, str, str2, messageStoreClient);
        this.TAG = CloudMessageDeleteObjectFlag.class.getSimpleName();
        this.TAG += "[" + messageStoreClient.getClientID() + "]";
        initCommonRequestHeaders(this.mStoreClient.getCloudMessageStrategyManager().getStrategy().getContentType(), this.mStoreClient.getCloudMessageStrategyManager().getStrategy().getValidTokenByLine(bufferDBChangeParam.mLine));
        initDeleteRequest();
        setCallback(new HttpRequestParams.HttpRequestCallback() { // from class: com.sec.internal.ims.cmstore.omanetapi.nms.CloudMessageDeleteObjectFlag.1
            @Override // com.sec.internal.helper.httpclient.HttpRequestParams.HttpRequestCallback
            public void onComplete(HttpResponseParams httpResponseParams) {
                if (httpResponseParams.getStatusCode() == 401) {
                    if (CloudMessageDeleteObjectFlag.this.handleUnAuthorized(httpResponseParams)) {
                        return;
                    }
                } else {
                    httpResponseParams.setStatusCode(204);
                }
                ParamOMAresponseforBufDB build = httpResponseParams.getStatusCode() == 204 ? new ParamOMAresponseforBufDB.Builder().setActionType(ParamOMAresponseforBufDB.ActionType.OBJECT_FLAGS_UPDATE_COMPLETE).setBufferDBChangeParam(bufferDBChangeParam).build() : null;
                if (!((BaseNMSRequest) CloudMessageDeleteObjectFlag.this).mStoreClient.getCloudMessageStrategyManager().getStrategy().isEnableATTHeader() && ((BaseNMSRequest) CloudMessageDeleteObjectFlag.this).mStoreClient.getCloudMessageStrategyManager().getStrategy().isRetryRequired(httpResponseParams.getStatusCode())) {
                    iAPICallFlowListener.onFailedCall(this, bufferDBChangeParam, SyncMsgType.VM, httpResponseParams.getStatusCode());
                    return;
                }
                if (CloudMessageDeleteObjectFlag.this.shouldCareAfterResponsePreProcess(iAPICallFlowListener, httpResponseParams, build, bufferDBChangeParam, Integer.MIN_VALUE)) {
                    iAPICallFlowListener.onMoveOnToNext(CloudMessageDeleteObjectFlag.this, build);
                }
            }

            @Override // com.sec.internal.helper.httpclient.HttpRequestParams.HttpRequestCallback
            public void onFail(IOException iOException) {
                Log.e(CloudMessageDeleteObjectFlag.this.TAG, "Http request onFail: " + iOException.getMessage());
                iAPICallFlowListener.onFailedCall(this, bufferDBChangeParam);
            }
        });
    }
}
