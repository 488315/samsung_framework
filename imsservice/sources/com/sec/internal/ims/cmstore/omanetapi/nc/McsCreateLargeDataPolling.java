package com.sec.internal.ims.cmstore.omanetapi.nc;

import android.text.TextUtils;
import android.util.Log;
import com.google.gson.Gson;
import com.sec.internal.constants.ims.cmstore.omanetapi.OMASyncEventType;
import com.sec.internal.constants.ims.entitilement.SoftphoneNamespaces;
import com.sec.internal.helper.httpclient.HttpRequestParams;
import com.sec.internal.helper.httpclient.HttpResponseParams;
import com.sec.internal.ims.cmstore.MessageStoreClient;
import com.sec.internal.ims.cmstore.helper.EventLogHelper;
import com.sec.internal.ims.cmstore.params.ParamOMAresponseforBufDB;
import com.sec.internal.ims.cmstore.utils.McsNotificationListContainer;
import com.sec.internal.ims.cmstore.utils.Util;
import com.sec.internal.interfaces.ims.cmstore.IAPICallFlowListener;
import com.sec.internal.interfaces.ims.cmstore.IControllerCommonInterface;
import com.sec.internal.interfaces.ims.cmstore.IHttpAPICommonInterface;
import com.sec.internal.log.IMSLog;
import com.sec.internal.omanetapi.common.data.McsOMAApiResponseParam;
import com.sec.internal.omanetapi.nc.BaseNCRequest;
import com.sec.internal.omanetapi.nc.NotificationList;
import com.sec.internal.omanetapi.nms.data.NmsEventList;
import java.io.IOException;
import java.util.Map;

/* loaded from: classes.dex */
public class McsCreateLargeDataPolling extends NotificationList {
    private static final long serialVersionUID = 1;
    private String TAG;
    private final IHttpAPICommonInterface mHttpInterface;
    private final transient IAPICallFlowListener mIAPICallFlowListener;
    private final transient IControllerCommonInterface mIControllerCommonInterface;
    private final int mPhoneId;

    public McsCreateLargeDataPolling(IAPICallFlowListener iAPICallFlowListener, IControllerCommonInterface iControllerCommonInterface, final String str, MessageStoreClient messageStoreClient) {
        super(str, messageStoreClient);
        this.TAG = McsCreateLargeDataPolling.class.getSimpleName();
        int clientID = messageStoreClient.getClientID();
        this.mPhoneId = clientID;
        this.TAG += "[" + clientID + "]";
        this.mIControllerCommonInterface = iControllerCommonInterface;
        this.mIAPICallFlowListener = iAPICallFlowListener;
        this.mHttpInterface = this;
        initMcsCommonRequestHeaders("application/json", this.mStoreClient.getCloudMessageStrategyManager().getStrategy().getAuthorizationBearer());
        initPostRequest(null, true);
        setCallback(new HttpRequestParams.HttpRequestCallback() { // from class: com.sec.internal.ims.cmstore.omanetapi.nc.McsCreateLargeDataPolling.1
            @Override // com.sec.internal.helper.httpclient.HttpRequestParams.HttpRequestCallback
            public void onComplete(HttpResponseParams httpResponseParams) {
                NmsEventList nmsEventList;
                int statusCode = httpResponseParams.getStatusCode();
                IMSLog.i(McsCreateLargeDataPolling.this.TAG, "onComplete: statusCode: " + statusCode);
                if (statusCode == 201 || statusCode == 200) {
                    ((BaseNCRequest) McsCreateLargeDataPolling.this).mStoreClient.getMcsRetryMapAdapter().remove(McsCreateLargeDataPolling.this.mHttpInterface);
                    String dataString = httpResponseParams.getDataString();
                    if (((BaseNCRequest) McsCreateLargeDataPolling.this).mStoreClient.getCloudMessageStrategyManager().getStrategy().isEncrypted()) {
                        String decrypt = ((BaseNCRequest) McsCreateLargeDataPolling.this).mStoreClient.getCloudMessageStrategyManager().getStrategy().decrypt(dataString, true);
                        IMSLog.s(McsCreateLargeDataPolling.this.TAG, "onComplete: decryptedData: " + decrypt);
                        if (TextUtils.isEmpty(decrypt)) {
                            EventLogHelper.add(McsCreateLargeDataPolling.this.TAG, McsCreateLargeDataPolling.this.mPhoneId, "decryptedData is empty");
                        } else {
                            dataString = decrypt;
                        }
                    }
                    try {
                        McsOMAApiResponseParam mcsOMAApiResponseParam = (McsOMAApiResponseParam) new Gson().fromJson(dataString, McsOMAApiResponseParam.class);
                        if (mcsOMAApiResponseParam == null || (nmsEventList = mcsOMAApiResponseParam.nmsEventList) == null) {
                            IMSLog.i(McsCreateLargeDataPolling.this.TAG, "onComplete: response or nmsEventList is null");
                            McsCreateLargeDataPolling.this.mIAPICallFlowListener.onGoToEvent(OMASyncEventType.SEND_LARGE_DATA_POLLING_FINISHED.getId(), null);
                            return;
                        }
                        boolean z = mcsOMAApiResponseParam.ncListComplete;
                        boolean z2 = false;
                        if (Util.isMatchedSubscriptionID(nmsEventList, ((BaseNCRequest) McsCreateLargeDataPolling.this).mStoreClient)) {
                            long longValue = mcsOMAApiResponseParam.nmsEventList.index.longValue();
                            long oMASubscriptionIndex = ((BaseNCRequest) McsCreateLargeDataPolling.this).mStoreClient.getPrerenceManager().getOMASubscriptionIndex();
                            IMSLog.i(McsCreateLargeDataPolling.this.TAG, "onComplete: currIndex: " + longValue + " savedIndex:" + oMASubscriptionIndex + " ncListComplete:" + z);
                            if (longValue > oMASubscriptionIndex + McsCreateLargeDataPolling.serialVersionUID) {
                                boolean isEmpty = McsNotificationListContainer.getInstance(((BaseNCRequest) McsCreateLargeDataPolling.this).mStoreClient.getClientID()).isEmpty();
                                McsNotificationListContainer.getInstance(((BaseNCRequest) McsCreateLargeDataPolling.this).mStoreClient.getClientID()).insertContainer(Long.valueOf(longValue), nmsEventList, ((BaseNCRequest) McsCreateLargeDataPolling.this).mStoreClient.getClientID(), Long.valueOf(oMASubscriptionIndex));
                                z2 = isEmpty;
                            } else {
                                ((BaseNCRequest) McsCreateLargeDataPolling.this).mStoreClient.getPrerenceManager().saveOMASubscriptionRestartToken(nmsEventList.restartToken);
                                ((BaseNCRequest) McsCreateLargeDataPolling.this).mStoreClient.getPrerenceManager().saveOMASubscriptionIndex(longValue);
                                IMSLog.i(McsCreateLargeDataPolling.this.TAG, "onComplete: NmsEventList being processed");
                                ((BaseNCRequest) McsCreateLargeDataPolling.this).mStoreClient.getCloudMessageBufferSchedulingHandler().onNativeChannelReceived(new ParamOMAresponseforBufDB.Builder().setActionType(ParamOMAresponseforBufDB.ActionType.RECEIVE_NOTIFICATION).setMcsNmsEventList(nmsEventList).build());
                                long oMASubscriptionIndex2 = ((BaseNCRequest) McsCreateLargeDataPolling.this).mStoreClient.getPrerenceManager().getOMASubscriptionIndex();
                                while (true) {
                                    if (McsNotificationListContainer.getInstance(((BaseNCRequest) McsCreateLargeDataPolling.this).mStoreClient.getClientID()).isEmpty() || McsNotificationListContainer.getInstance(((BaseNCRequest) McsCreateLargeDataPolling.this).mStoreClient.getClientID()).peekFirstIndex() != oMASubscriptionIndex2 + McsCreateLargeDataPolling.serialVersionUID) {
                                        break;
                                    }
                                    Map.Entry<Long, NmsEventList> popFirstEntry = McsNotificationListContainer.getInstance(((BaseNCRequest) McsCreateLargeDataPolling.this).mStoreClient.getClientID()).popFirstEntry();
                                    if (popFirstEntry == null) {
                                        Log.e(McsCreateLargeDataPolling.this.TAG, "handleNmsEvent: firstEntry is null");
                                    } else {
                                        IMSLog.i(McsCreateLargeDataPolling.this.TAG, "onComplete: Process nmsEventList from the NotificationListContainer, savedIndex: " + oMASubscriptionIndex2 + " currIndex:" + longValue);
                                        NmsEventList value = popFirstEntry.getValue();
                                        String str2 = value.restartToken;
                                        longValue = value.index.longValue();
                                        ((BaseNCRequest) McsCreateLargeDataPolling.this).mStoreClient.getPrerenceManager().saveOMASubscriptionRestartToken(str2);
                                        ((BaseNCRequest) McsCreateLargeDataPolling.this).mStoreClient.getPrerenceManager().saveOMASubscriptionIndex(longValue);
                                        ((BaseNCRequest) McsCreateLargeDataPolling.this).mStoreClient.getCloudMessageBufferSchedulingHandler().onNativeChannelReceived(new ParamOMAresponseforBufDB.Builder().setActionType(ParamOMAresponseforBufDB.ActionType.RECEIVE_NOTIFICATION).setMcsNmsEventList(value).build());
                                        oMASubscriptionIndex2 = ((BaseNCRequest) McsCreateLargeDataPolling.this).mStoreClient.getPrerenceManager().getOMASubscriptionIndex();
                                        if (McsNotificationListContainer.getInstance(((BaseNCRequest) McsCreateLargeDataPolling.this).mStoreClient.getClientID()).isEmpty()) {
                                            McsCreateLargeDataPolling.this.mIControllerCommonInterface.update(OMASyncEventType.REMOVE_UPDATE_SUBSCRIPTION_CHANNEL.getId());
                                            break;
                                        }
                                    }
                                }
                            }
                        } else {
                            Log.e(McsCreateLargeDataPolling.this.TAG, "subscription url did not match with clientId: " + ((BaseNCRequest) McsCreateLargeDataPolling.this).mStoreClient.getClientID());
                        }
                        if (z2) {
                            McsCreateLargeDataPolling.this.mIControllerCommonInterface.updateDelay(OMASyncEventType.UPDATE_SUBSCRIPTION_CHANNEL_DELAY.getId(), SoftphoneNamespaces.SoftphoneSettings.LONG_BACKOFF);
                        }
                        if (z) {
                            McsCreateLargeDataPolling.this.mIAPICallFlowListener.onSuccessfulEvent(this, OMASyncEventType.SEND_LARGE_DATA_POLLING_FINISHED.getId(), null);
                            return;
                        } else {
                            McsCreateLargeDataPolling.this.mIAPICallFlowListener.onSuccessfulEvent(this, OMASyncEventType.SEND_LARGE_DATA_POLLING_REQUEST.getId(), str);
                            return;
                        }
                    } catch (Exception e) {
                        IMSLog.i(McsCreateLargeDataPolling.this.TAG, "onComplete: Exception: " + e.getMessage());
                        McsCreateLargeDataPolling.this.mIAPICallFlowListener.onGoToEvent(OMASyncEventType.SEND_LARGE_DATA_POLLING_FINISHED.getId(), null);
                        return;
                    }
                }
                EventLogHelper.add(McsCreateLargeDataPolling.this.TAG, McsCreateLargeDataPolling.this.mPhoneId, "onComplete: statusCode: " + statusCode);
                if (McsCreateLargeDataPolling.this.isErrorCodeSupported(statusCode)) {
                    if (((BaseNCRequest) McsCreateLargeDataPolling.this).mStoreClient.getCloudMessageStrategyManager().getStrategy().handleNCCommonError(McsCreateLargeDataPolling.this.mIAPICallFlowListener, McsCreateLargeDataPolling.this.mHttpInterface, statusCode, McsCreateLargeDataPolling.this.checkRetryAfter(httpResponseParams, ((BaseNCRequest) McsCreateLargeDataPolling.this).mStoreClient.getMcsRetryMapAdapter().getRetryCount(McsCreateLargeDataPolling.this.mHttpInterface.getClass().getSimpleName())))) {
                        return;
                    }
                    McsCreateLargeDataPolling.this.mIControllerCommonInterface.update(OMASyncEventType.UPDATE_SUBSCRIPTION_CHANNEL.getId());
                } else {
                    if (statusCode < 400 || statusCode >= 500) {
                        return;
                    }
                    McsCreateLargeDataPolling.this.mIControllerCommonInterface.update(OMASyncEventType.UPDATE_SUBSCRIPTION_CHANNEL.getId());
                }
            }

            @Override // com.sec.internal.helper.httpclient.HttpRequestParams.HttpRequestCallback
            public void onFail(IOException iOException) {
                IMSLog.e(McsCreateLargeDataPolling.this.TAG, "onFail: exception " + iOException.getMessage());
                EventLogHelper.add(McsCreateLargeDataPolling.this.TAG, McsCreateLargeDataPolling.this.mPhoneId, "onFail: IOException");
            }
        });
    }
}
