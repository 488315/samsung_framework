package com.sec.internal.ims.cmstore.ambs.cloudrequest;

import android.text.TextUtils;
import android.util.Log;
import com.sec.internal.constants.ims.cmstore.ATTConstants;
import com.sec.internal.constants.ims.cmstore.enumprovision.EnumProvision;
import com.sec.internal.helper.httpclient.HttpRequestParams;
import com.sec.internal.helper.httpclient.HttpResponseParams;
import com.sec.internal.ims.cmstore.MessageStoreClient;
import com.sec.internal.ims.cmstore.ambs.globalsetting.BaseProvisionAPIRequest;
import com.sec.internal.ims.cmstore.helper.ATTGlobalVariables;
import com.sec.internal.ims.core.cmc.CmcConstants;
import com.sec.internal.interfaces.ims.cmstore.IAPICallFlowListener;
import com.sec.internal.interfaces.ims.cmstore.ICloudMessageManagerHelper;
import com.sec.internal.interfaces.ims.cmstore.IRetryStackAdapterHelper;
import com.sec.internal.log.IMSLog;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import okhttp3.Cookie;
import okhttp3.HttpUrl;

/* loaded from: classes.dex */
public class RequestHUIToken extends BaseProvisionAPIRequest {
    private static final long serialVersionUID = -5155400496558292974L;
    private String TAG;
    private transient Cookie cookieServerIDInBody;
    private transient Cookie cookieTokenInBody;

    public RequestHUIToken(final IAPICallFlowListener iAPICallFlowListener, MessageStoreClient messageStoreClient, ICloudMessageManagerHelper iCloudMessageManagerHelper) {
        super(iAPICallFlowListener, messageStoreClient);
        this.TAG = RequestHUIToken.class.getSimpleName();
        this.TAG += "[" + messageStoreClient.getClientID() + "]";
        setMethod(HttpRequestParams.Method.GET);
        updateUrl();
        setCallback(new HttpRequestParams.HttpRequestCallback() { // from class: com.sec.internal.ims.cmstore.ambs.cloudrequest.RequestHUIToken.1
            @Override // com.sec.internal.helper.httpclient.HttpRequestParams.HttpRequestCallback
            public void onComplete(HttpResponseParams httpResponseParams) {
                String dataString = httpResponseParams.getDataString();
                Log.d(RequestHUIToken.this.TAG, "onComplete StatusCode: " + httpResponseParams.getStatusCode() + " strbody: " + IMSLog.checker(dataString));
                if (httpResponseParams.getStatusCode() == 200 && !TextUtils.isEmpty(dataString)) {
                    if (dataString.indexOf(ATTConstants.ATTErrorNames.encoreesb) < 0) {
                        Log.d(RequestHUIToken.this.TAG, "NOT 6014");
                        ((BaseProvisionAPIRequest) RequestHUIToken.this).mStoreClient.getPrerenceManager().saveIfHUI6014Err(false);
                        String parameter = RequestHUIToken.this.getParameter(dataString, "msToken=\"", CmcConstants.E_NUM_STR_QUOTE);
                        String parameter2 = RequestHUIToken.this.getParameter(dataString, "serverID=\"", CmcConstants.E_NUM_STR_QUOTE);
                        String parameter3 = RequestHUIToken.this.getParameter(dataString, "redirectDomain=\"", CmcConstants.E_NUM_STR_QUOTE);
                        String parameter4 = RequestHUIToken.this.getParameter(dataString, "cometRedirectDomain=\"", CmcConstants.E_NUM_STR_QUOTE);
                        Log.d(RequestHUIToken.this.TAG, "msToken=" + parameter + ", serverID=" + parameter2 + ", redirectDomain=" + parameter3 + ", cometRedirectDomain" + parameter4);
                        if (!ATTGlobalVariables.isGcmReplacePolling()) {
                            Cookie.Builder builder = new Cookie.Builder();
                            builder.domain(parameter3).name("MSToken").value(parameter);
                            RequestHUIToken.this.cookieTokenInBody = builder.build();
                            Cookie.Builder builder2 = new Cookie.Builder();
                            builder2.domain(parameter3).name("SERVERID").value(parameter2);
                            RequestHUIToken.this.cookieServerIDInBody = builder2.build();
                        }
                        if (TextUtils.isEmpty(parameter) || TextUtils.isEmpty(parameter3)) {
                            return;
                        }
                        ((BaseProvisionAPIRequest) RequestHUIToken.this).mStoreClient.getPrerenceManager().saveMsgStoreSessionId(parameter);
                        if (ATTGlobalVariables.isGcmReplacePolling()) {
                            Log.d(RequestHUIToken.this.TAG, "nms value in SP =" + ((BaseProvisionAPIRequest) RequestHUIToken.this).mStoreClient.getPrerenceManager().getNmsHost());
                            ((BaseProvisionAPIRequest) RequestHUIToken.this).mStoreClient.getPrerenceManager().saveNmsHost(parameter3);
                        } else {
                            RequestHUIToken requestHUIToken = RequestHUIToken.this;
                            requestHUIToken.updateCookie(requestHUIToken.getUrl());
                            ((BaseProvisionAPIRequest) RequestHUIToken.this).mStoreClient.getPrerenceManager().saveNmsHost(parameter3);
                            ((BaseProvisionAPIRequest) RequestHUIToken.this).mStoreClient.getPrerenceManager().saveNcHost(parameter4);
                        }
                        String redirectDomain = ((BaseProvisionAPIRequest) RequestHUIToken.this).mStoreClient.getPrerenceManager().getRedirectDomain();
                        ((BaseProvisionAPIRequest) RequestHUIToken.this).mStoreClient.getPrerenceManager().saveRedirectDomain(parameter3);
                        if (!TextUtils.isEmpty(parameter3) && !TextUtils.isEmpty(redirectDomain) && !parameter3.equals(redirectDomain)) {
                            Log.d(RequestHUIToken.this.TAG, "redirect domain changed, need mail reset.");
                            iAPICallFlowListener.onGoToEvent(EnumProvision.ProvisionEventType.MAILBOX_MIGRATION_RESET.getId(), null);
                        }
                        ((BaseProvisionAPIRequest) RequestHUIToken.this).mStoreClient.getPrerenceManager().saveLastApiRequestCreateAccount(false);
                        RequestHUIToken.this.goSuccessfulCall();
                        return;
                    }
                    if (dataString.indexOf(ATTConstants.ATTErrorNames.ERR_ENCORE_METASWITCH_ACCOUNT_NOT_PROVISIONED) >= 0) {
                        ((BaseProvisionAPIRequest) RequestHUIToken.this).mStoreClient.getPrerenceManager().saveIfHUI6014Err(true);
                        if (!((BaseProvisionAPIRequest) RequestHUIToken.this).mStoreClient.getPrerenceManager().isLastAPIRequestCreateAccount()) {
                            RequestHUIToken.this.goFailedCall(ATTConstants.ATTErrorNames.ERR_ENCORE_METASWITCH_ACCOUNT_NOT_PROVISIONED);
                            return;
                        } else {
                            Log.d(RequestHUIToken.this.TAG, "Last successful API call was CreateServiceAccount");
                            RequestHUIToken.this.goFailedCall(ATTConstants.ATTErrorNames.LAST_RETRY_CREATE_ACCOUNT);
                            return;
                        }
                    }
                    RequestHUIToken.this.goFailedCall(ATTConstants.ATTErrorNames.ERR_HUI_JSON);
                    return;
                }
                if (httpResponseParams.getStatusCode() == 503 || httpResponseParams.getStatusCode() == 429) {
                    int checkRetryAfter = RequestHUIToken.this.checkRetryAfter(httpResponseParams);
                    if (checkRetryAfter > 0) {
                        iAPICallFlowListener.onOverRequest(this, ATTConstants.ATTErrorNames.ERR_RETRY_AFTER, checkRetryAfter);
                        return;
                    }
                    return;
                }
                RequestHUIToken.this.goFailedCall(ATTConstants.ATTErrorNames.ERR_HUI_JSON);
            }

            @Override // com.sec.internal.helper.httpclient.HttpRequestParams.HttpRequestCallback
            public void onFail(IOException iOException) {
                Log.e(RequestHUIToken.this.TAG, "Http request onFail: " + iOException.getMessage());
                RequestHUIToken.this.goFailedCall();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getParameter(String str, String str2, String str3) {
        int indexOf = str.indexOf(str2);
        if (indexOf < 0) {
            return null;
        }
        int length = indexOf + str2.length();
        int indexOf2 = str.indexOf(str3, length);
        return indexOf2 > 0 ? str.substring(length, indexOf2) : str.substring(length);
    }

    public void updateUrl() {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("https://");
            sb.append(ATTGlobalVariables.MSG_PROXY_HOST_NAME);
            sb.append("/encore/security/GetHUIMSToken?clientType=handset&ApplicationId=");
            sb.append(URLEncoder.encode(ATTGlobalVariables.APPLICATION_ID, "UTF-8"));
            sb.append("&ContextInfo=");
            sb.append(URLEncoder.encode("version=" + ATTGlobalVariables.VERSION_NAME, "UTF-8"));
            setUrl(sb.toString());
        } catch (UnsupportedEncodingException e) {
            Log.e(this.TAG, e.getMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateCookie(String str) {
        Log.d(this.TAG, "updateCookie");
        try {
            this.mCookieJar.removeAll();
            HttpUrl httpUrl = HttpUrl.get(new URI(str));
            ArrayList arrayList = new ArrayList();
            arrayList.add(this.cookieTokenInBody);
            this.mCookieJar.saveFromResponse(httpUrl, arrayList);
            arrayList.clear();
            arrayList.add(this.cookieServerIDInBody);
            this.mCookieJar.saveFromResponse(httpUrl, arrayList);
        } catch (URISyntaxException e) {
            Log.e(this.TAG, e.getMessage());
        }
    }

    @Override // com.sec.internal.ims.cmstore.ambs.globalsetting.BaseProvisionAPIRequest, com.sec.internal.interfaces.ims.cmstore.IHttpAPICommonInterface
    public HttpRequestParams getRetryInstance(IAPICallFlowListener iAPICallFlowListener, MessageStoreClient messageStoreClient, ICloudMessageManagerHelper iCloudMessageManagerHelper, IRetryStackAdapterHelper iRetryStackAdapterHelper) {
        return new RequestHUIToken(iAPICallFlowListener, messageStoreClient, iCloudMessageManagerHelper);
    }
}
