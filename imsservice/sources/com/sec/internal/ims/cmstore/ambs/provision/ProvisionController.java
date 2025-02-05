package com.sec.internal.ims.cmstore.ambs.provision;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import com.sec.ims.settings.RcsConfigurationReader;
import com.sec.internal.constants.ims.ImsConstants;
import com.sec.internal.constants.ims.aec.AECNamespace;
import com.sec.internal.constants.ims.cmstore.ATTConstants;
import com.sec.internal.constants.ims.cmstore.McsConstants;
import com.sec.internal.constants.ims.cmstore.ReqConstant;
import com.sec.internal.constants.ims.cmstore.enumprovision.EnumProvision;
import com.sec.internal.constants.ims.cmstore.omanetapi.SyncMsgType;
import com.sec.internal.ims.cmstore.MessageStoreClient;
import com.sec.internal.ims.cmstore.ambs.cloudrequest.ReqRetireSession;
import com.sec.internal.ims.cmstore.ambs.cloudrequest.ReqSession;
import com.sec.internal.ims.cmstore.ambs.cloudrequest.ReqToken;
import com.sec.internal.ims.cmstore.ambs.cloudrequest.ReqZCode;
import com.sec.internal.ims.cmstore.ambs.cloudrequest.RequestAccount;
import com.sec.internal.ims.cmstore.ambs.cloudrequest.RequestAccountEligibility;
import com.sec.internal.ims.cmstore.ambs.cloudrequest.RequestCreateAccount;
import com.sec.internal.ims.cmstore.ambs.cloudrequest.RequestDeleteAccount;
import com.sec.internal.ims.cmstore.ambs.cloudrequest.RequestHUIToken;
import com.sec.internal.ims.cmstore.ambs.cloudrequest.RequestPat;
import com.sec.internal.ims.cmstore.ambs.cloudrequest.RequestTC;
import com.sec.internal.ims.cmstore.ambs.globalsetting.AmbsUtils;
import com.sec.internal.ims.cmstore.ambs.receiver.DataSMSReceiver;
import com.sec.internal.ims.cmstore.ambs.receiver.SmsReceiver;
import com.sec.internal.ims.cmstore.callHandling.errorHandling.ErrorRuleHandling;
import com.sec.internal.ims.cmstore.callHandling.successfullCall.SuccessfulCallHandling;
import com.sec.internal.ims.cmstore.helper.ATTGlobalVariables;
import com.sec.internal.ims.cmstore.omanetapi.OMANetAPIHandler;
import com.sec.internal.ims.cmstore.params.BufferDBChangeParam;
import com.sec.internal.ims.cmstore.params.FailedAPICallResponseParam;
import com.sec.internal.ims.cmstore.params.SuccessfulAPICallResponseParam;
import com.sec.internal.ims.cmstore.params.UIEventParam;
import com.sec.internal.ims.cmstore.receiver.NetworkChangeReceiver;
import com.sec.internal.ims.cmstore.utils.CloudMessagePreferenceManager;
import com.sec.internal.ims.cmstore.utils.CmsUtil;
import com.sec.internal.interfaces.ims.cmstore.IAPICallFlowListener;
import com.sec.internal.interfaces.ims.cmstore.ICloudMessageManagerHelper;
import com.sec.internal.interfaces.ims.cmstore.IControllerCommonInterface;
import com.sec.internal.interfaces.ims.cmstore.IHttpAPICommonInterface;
import com.sec.internal.interfaces.ims.cmstore.IRetryStackAdapterHelper;
import com.sec.internal.interfaces.ims.cmstore.IUIEventCallback;
import com.sec.internal.interfaces.ims.cmstore.IWorkingStatusProvisionListener;
import com.sec.internal.log.IMSLog;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
public class ProvisionController extends Handler implements IAPICallFlowListener, IControllerCommonInterface {
    protected static final int EVENT_PAUSE = 6;
    protected static final int EVENT_PAUSE_SERVICE = 8;
    protected static final int EVENT_PROVISIONAPI_FAIL = 4;
    protected static final int EVENT_PROVISIONAPI_SUCCESS = 3;
    protected static final int EVENT_PROVISION_API = 1;
    protected static final int EVENT_RESUME = 5;
    protected static final int EVENT_STOP = 7;
    protected static final int EVENT_UI_ACTIONS = 2;
    private final long DELAY;
    private final long INTERNAL_WAITING;
    public String TAG;
    private final AmbsPhoneStateListener mAmbsPhoneStateListener;
    private final Context mContext;
    private DataSMSReceiver mDataSmsReceiver;
    private BroadcastReceiver mFactoryResetReceiver;
    private boolean mHasUserDeleteAccount;
    private boolean mHasUserOptedIn;
    private final ICloudMessageManagerHelper mICloudMessageManagerHelper;
    public IRetryStackAdapterHelper mIRetryStackAdapterHelper;
    private final IWorkingStatusProvisionListener mIWorkingStatusProvisionListener;
    private boolean mIfSteadyState;
    private boolean mIsInternalRestart;
    private int mLastSavedMessageIdAfterStop;
    private ATTConstants.AttAmbsUIScreenNames mLastScreenUserStopBackup;
    private ATTConstants.AttAmbsUIScreenNames mLastUIScreen;
    private NetworkChangeReceiver mNetworkChangeReceiver;
    private int mNewUserOptInCase;
    private boolean mPaused;
    private SmsReceiver mSmsReceiver;
    private MessageStoreClient mStoreClient;
    private final IUIEventCallback mUIInterface;

    @Override // com.sec.internal.interfaces.ims.cmstore.IAPICallFlowListener
    public void onFailedCall(IHttpAPICommonInterface iHttpAPICommonInterface, int i) {
    }

    @Override // com.sec.internal.interfaces.ims.cmstore.IAPICallFlowListener
    public void onFailedCall(IHttpAPICommonInterface iHttpAPICommonInterface, BufferDBChangeParam bufferDBChangeParam, SyncMsgType syncMsgType, int i) {
    }

    @Override // com.sec.internal.interfaces.ims.cmstore.IAPICallFlowListener
    public void onFailedEvent(int i, Object obj) {
    }

    @Override // com.sec.internal.interfaces.ims.cmstore.IAPICallFlowListener
    public void onFixedFlowWithMessage(Message message) {
    }

    @Override // com.sec.internal.interfaces.ims.cmstore.IAPICallFlowListener
    public void onMoveOnToNext(IHttpAPICommonInterface iHttpAPICommonInterface, Object obj) {
    }

    @Override // com.sec.internal.interfaces.ims.cmstore.IAPICallFlowListener
    public void onOverRequest(IHttpAPICommonInterface iHttpAPICommonInterface, String str, int i, Object obj) {
    }

    @Override // com.sec.internal.interfaces.ims.cmstore.IAPICallFlowListener
    public void onSuccessfulCall(IHttpAPICommonInterface iHttpAPICommonInterface, Object obj) {
    }

    @Override // com.sec.internal.interfaces.ims.cmstore.IAPICallFlowListener
    public void onSuccessfulEvent(IHttpAPICommonInterface iHttpAPICommonInterface, int i, Object obj) {
    }

    @Override // com.sec.internal.interfaces.ims.cmstore.IControllerCommonInterface
    public void setOnApiSucceedOnceListener(OMANetAPIHandler.OnApiSucceedOnceListener onApiSucceedOnceListener) {
    }

    @Override // com.sec.internal.interfaces.ims.cmstore.IControllerCommonInterface
    public boolean updateMessage(Message message) {
        return false;
    }

    public ProvisionController(IWorkingStatusProvisionListener iWorkingStatusProvisionListener, Looper looper, MessageStoreClient messageStoreClient, IUIEventCallback iUIEventCallback, IRetryStackAdapterHelper iRetryStackAdapterHelper, ICloudMessageManagerHelper iCloudMessageManagerHelper) {
        super(looper);
        this.TAG = ProvisionController.class.getSimpleName();
        this.INTERNAL_WAITING = 5000L;
        this.DELAY = 10000L;
        this.mLastSavedMessageIdAfterStop = -1;
        this.mPaused = false;
        this.mIfSteadyState = false;
        this.mHasUserOptedIn = false;
        this.mHasUserDeleteAccount = false;
        this.mIsInternalRestart = false;
        this.mFactoryResetReceiver = new BroadcastReceiver() { // from class: com.sec.internal.ims.cmstore.ambs.provision.ProvisionController.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                Log.i(ProvisionController.this.TAG, "Factory Reset Added received intent : " + intent.getAction());
                String action = intent.getAction();
                action.hashCode();
                if (action.equals(ImsConstants.Intents.ACTION_FACTORY_RESET)) {
                    ProvisionController.this.factoryReset();
                }
            }
        };
        this.mStoreClient = messageStoreClient;
        this.TAG += "[" + messageStoreClient.getClientID() + "]";
        this.mIWorkingStatusProvisionListener = iWorkingStatusProvisionListener;
        this.mUIInterface = iUIEventCallback;
        Context context = messageStoreClient.getContext();
        this.mContext = context;
        this.mAmbsPhoneStateListener = new AmbsPhoneStateListener(this.mStoreClient.getClientID(), this, context);
        this.mIRetryStackAdapterHelper = iRetryStackAdapterHelper;
        this.mICloudMessageManagerHelper = iCloudMessageManagerHelper;
        this.mLastSavedMessageIdAfterStop = -1;
        this.mPaused = false;
        initPrefenceValues();
        registerFactoryResetReceiver();
        if (this.mStoreClient.getPrerenceManager().getAMBSPauseService()) {
            registerDataSmsReceiver();
        }
    }

    private void initPrefenceValues() {
        this.mNewUserOptInCase = this.mStoreClient.getPrerenceManager().getNewUserOptInCase();
        this.mIfSteadyState = this.mStoreClient.getPrerenceManager().ifSteadyState();
        this.mHasUserOptedIn = this.mStoreClient.getPrerenceManager().hasUserOptedIn();
        this.mLastUIScreen = ATTConstants.AttAmbsUIScreenNames.valueOf(this.mStoreClient.getPrerenceManager().getLastScreen());
        this.mLastScreenUserStopBackup = ATTConstants.AttAmbsUIScreenNames.valueOf(this.mStoreClient.getPrerenceManager().getLastScreenUserStopBackup());
        this.mHasUserDeleteAccount = this.mStoreClient.getPrerenceManager().hasUserDeleteAccount();
    }

    private void readNcNmsHost() {
        readNcHost();
        readNmsHost();
    }

    private static boolean isBase64(String str) {
        return Pattern.matches("^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)?$", str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean readNcHost() {
        String string = new RcsConfigurationReader(this.mContext).getString("root/application/1/serviceproviderext/nc_url");
        Log.d(this.TAG, "readNcHost() nc=" + string);
        if (TextUtils.isEmpty(string)) {
            return false;
        }
        String trim = string.trim();
        if (isBase64(trim)) {
            try {
                trim = new String(Base64.decode(trim, 0));
            } catch (IllegalArgumentException unused) {
                Log.e(this.TAG, "Failed to decrypt the NC");
            }
        }
        String ncHost = this.mStoreClient.getPrerenceManager().getNcHost();
        Log.d(this.TAG, "oldnc=" + ncHost + " nc=" + trim);
        if (trim.equals(ncHost)) {
            return false;
        }
        this.mStoreClient.getPrerenceManager().saveNcHost(trim);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean readNmsHost() {
        String string = new RcsConfigurationReader(this.mContext).getString("root/application/1/serviceproviderext/nms_url");
        Log.d(this.TAG, "readNmsHost() nms=" + string);
        if (TextUtils.isEmpty(string)) {
            return false;
        }
        try {
            string = new String(Base64.decode(string, 0));
        } catch (IllegalArgumentException unused) {
            Log.e(this.TAG, "Failed to decrypt the NMS");
        }
        this.mStoreClient.getPrerenceManager().saveAcsNmsHost(string);
        String nmsHost = this.mStoreClient.getPrerenceManager().getNmsHost();
        Log.d(this.TAG, "oldNms=" + nmsHost + " nms=" + string);
        return TextUtils.isEmpty(nmsHost);
    }

    private void registerConfigurationObserver() {
        this.mContext.getContentResolver().registerContentObserver(RcsConfigurationReader.AUTO_CONFIGURATION_URI, true, new ContentObserver(new Handler()) { // from class: com.sec.internal.ims.cmstore.ambs.provision.ProvisionController.1
            @Override // android.database.ContentObserver
            public void onChange(boolean z, Uri uri) {
                super.onChange(z, uri);
                Log.d(ProvisionController.this.TAG, "changed in DB. uri=" + IMSLog.checker(uri));
                if (uri.toString().contains("root/application/1/serviceproviderext/nc_url")) {
                    if (ProvisionController.this.readNcHost()) {
                        Log.d(ProvisionController.this.TAG, "nc host changed, send REQ_SESSION_GEN event");
                    }
                } else if (uri.toString().contains("root/application/1/serviceproviderext/nms_url") && ProvisionController.this.readNmsHost()) {
                    Log.d(ProvisionController.this.TAG, "nms host changed, send REQ_SESSION_GEN event");
                }
            }
        });
    }

    private void registerNetworkChangeReceiver() {
        Log.d(this.TAG, "registerNetworkChangeReceiver");
        if (this.mNetworkChangeReceiver == null) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.net.wifi.STATE_CHANGE");
            intentFilter.setPriority(Integer.MAX_VALUE);
            NetworkChangeReceiver networkChangeReceiver = new NetworkChangeReceiver(this.mIWorkingStatusProvisionListener);
            this.mNetworkChangeReceiver = networkChangeReceiver;
            this.mContext.registerReceiver(networkChangeReceiver, intentFilter);
        }
    }

    private void registerSmsReceiver() {
        IntentFilter intentFilter = new IntentFilter(com.sec.internal.ims.servicemodules.im.SmsReceiver.SMS_RECEIVED);
        intentFilter.setPriority(Integer.MAX_VALUE);
        if (this.mSmsReceiver == null) {
            SmsReceiver smsReceiver = new SmsReceiver(this, this.mStoreClient);
            this.mSmsReceiver = smsReceiver;
            this.mContext.registerReceiver(smsReceiver, intentFilter);
        }
        Log.d(this.TAG, "registerSmsReceiver");
    }

    private void unregisterSmsReceiver() {
        SmsReceiver smsReceiver = this.mSmsReceiver;
        if (smsReceiver != null) {
            this.mContext.unregisterReceiver(smsReceiver);
            this.mSmsReceiver = null;
        }
    }

    private void registerFactoryResetReceiver() {
        Log.i(this.TAG, "Updated with FactoryReset Receiver");
        if (this.mFactoryResetReceiver == null) {
            Log.i(this.TAG, "NULL Receiver");
            return;
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ImsConstants.Intents.ACTION_SOFT_RESET);
        intentFilter.addAction(ImsConstants.Intents.ACTION_RESET_NETWORK_SETTINGS);
        intentFilter.addAction(ImsConstants.Intents.ACTION_FACTORY_RESET);
        this.mContext.registerReceiver(this.mFactoryResetReceiver, intentFilter);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void factoryReset() {
        Log.i(this.TAG, "Factory reset");
        this.mStoreClient.getPrerenceManager().saveAMBSStopService(false);
        this.mUIInterface.notifyAppUIScreen(ATTConstants.AttAmbsUIScreenNames.RestartMenu_Enable_PrmptMsg15.getId(), IUIEventCallback.NON_POP_UP, 0);
        this.mUIInterface.notifyAppUIScreen(ATTConstants.AttAmbsUIScreenNames.Settings_PrmptMsg9.getId(), IUIEventCallback.NON_POP_UP, 0);
        IMSLog.i(this.TAG, "factoryReset Done");
    }

    private void registerDataSmsReceiver() {
        Log.d(this.TAG, "registerDataSmsReceiver");
        IntentFilter intentFilter = new IntentFilter(AECNamespace.Action.RECEIVED_SMS_NOTIFICATION);
        intentFilter.addDataAuthority("*", ATTGlobalVariables.ATT_DATA_MESSAGE_PORT);
        intentFilter.addDataScheme("sms");
        if (this.mDataSmsReceiver == null) {
            DataSMSReceiver dataSMSReceiver = new DataSMSReceiver(this, this.mStoreClient, this.mIWorkingStatusProvisionListener);
            this.mDataSmsReceiver = dataSMSReceiver;
            this.mContext.registerReceiver(dataSMSReceiver, intentFilter);
        }
        if (ATTGlobalVariables.supportSignedBinary() && this.mStoreClient.getPrerenceManager().isDebugEnable()) {
            this.mContext.registerReceiver(this.mDataSmsReceiver, new IntentFilter("android.test.ambsphasev.SIGNEDBINARYSMS"));
        }
    }

    private void unregisterDataSmsReceiver() {
        Log.d(this.TAG, "unregisterDataSmsReceiver");
        DataSMSReceiver dataSMSReceiver = this.mDataSmsReceiver;
        if (dataSMSReceiver != null) {
            this.mContext.unregisterReceiver(dataSMSReceiver);
            this.mDataSmsReceiver = null;
        }
    }

    private void startPhoneStateListener() {
        this.mAmbsPhoneStateListener.startListen();
    }

    private void stopPhoneStateListener() {
        this.mAmbsPhoneStateListener.stopListen();
    }

    public boolean onUIButtonProceed(int i, String str) {
        Log.d(this.TAG, "message: " + str);
        sendMessage(obtainMessage(2, new UIEventParam(ATTConstants.AttAmbsUIScreenNames.valueOf(i), str)));
        return true;
    }

    private boolean isAMBSActive() {
        boolean z = (this.mStoreClient.getPrerenceManager().getAMBSStopService() || this.mStoreClient.getPrerenceManager().getAMBSPauseService()) ? false : true;
        Log.i(this.TAG, "isAMBSActive: " + z);
        return z;
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        int i;
        super.handleMessage(message);
        Log.i(this.TAG, "message: " + message.what);
        logCurrentWorkingStatus();
        if (isAMBSActive() || (i = message.what) == 7 || i == 8) {
            switch (message.what) {
                case 1:
                    EnumProvision.ProvisionEventType provisionEventType = (EnumProvision.ProvisionEventType) message.obj;
                    if (provisionEventType != null) {
                        handleProvisionEvent(provisionEventType);
                        break;
                    }
                    break;
                case 2:
                    UIEventParam uIEventParam = (UIEventParam) message.obj;
                    if (uIEventParam != null) {
                        handleUIEvent(uIEventParam.mUIScreen, uIEventParam.mMessage);
                        break;
                    }
                    break;
                case 3:
                    SuccessfulAPICallResponseParam successfulAPICallResponseParam = (SuccessfulAPICallResponseParam) message.obj;
                    if (successfulAPICallResponseParam != null) {
                        onProvisionAPISuccess(successfulAPICallResponseParam);
                        break;
                    }
                    break;
                case 4:
                    FailedAPICallResponseParam failedAPICallResponseParam = (FailedAPICallResponseParam) message.obj;
                    if (failedAPICallResponseParam != null) {
                        onProvisionAPIFail(failedAPICallResponseParam);
                        break;
                    }
                    break;
                case 5:
                    if (this.mPaused) {
                        this.mPaused = false;
                        int i2 = this.mLastSavedMessageIdAfterStop;
                        if (i2 != -1) {
                            sendMessage(obtainMessage(1, EnumProvision.ProvisionEventType.valueOf(i2)));
                            this.mLastSavedMessageIdAfterStop = -1;
                            Log.i(this.TAG, "resume successfully");
                            break;
                        } else {
                            Log.i(this.TAG, "no saved event");
                            break;
                        }
                    }
                    break;
                case 6:
                    this.mPaused = true;
                    break;
                case 7:
                    this.mPaused = true;
                    this.mLastSavedMessageIdAfterStop = -1;
                    this.mUIInterface.notifyAppUIScreen(ATTConstants.AttAmbsUIScreenNames.AMBS_SERVICE_DISABLE.getId(), IUIEventCallback.NON_POP_UP, 0);
                    break;
                case 8:
                    this.mPaused = true;
                    this.mUIInterface.notifyAppUIScreen(ATTConstants.AttAmbsUIScreenNames.RestartMenu_Disable_PrmptMsg16.getId(), IUIEventCallback.NON_POP_UP, 0);
                    this.mUIInterface.notifyAppUIScreen(ATTConstants.AttAmbsUIScreenNames.Settings_PrmptMsg10.getId(), IUIEventCallback.NON_POP_UP, 0);
                    break;
            }
        }
    }

    private void handleUIEvent(ATTConstants.AttAmbsUIScreenNames attAmbsUIScreenNames, String str) {
        Log.i(this.TAG, "handleUIEvent: " + attAmbsUIScreenNames + " messge: " + str);
        boolean supportSignedBinary = ATTGlobalVariables.supportSignedBinary();
        if (attAmbsUIScreenNames == null) {
            Log.d(this.TAG, "screenName is null");
        }
        switch (AnonymousClass3.$SwitchMap$com$sec$internal$constants$ims$cmstore$ATTConstants$AttAmbsUIScreenNames[attAmbsUIScreenNames.ordinal()]) {
            case 1:
                if (!supportSignedBinary) {
                    unregisterDataSmsReceiver();
                }
                saveUserOptedIn(true);
                saveLastScreen(attAmbsUIScreenNames.getId());
                this.mUIInterface.notifyAppUIScreen(ATTConstants.AttAmbsUIScreenNames.Settings_PrmptMsg11.getId(), IUIEventCallback.NON_POP_UP, 0);
                int i = this.mNewUserOptInCase;
                Log.d(this.TAG, "newUserOptInCase: " + i);
                if (i == EnumProvision.NewUserOptInCase.ERR.getId()) {
                    if (!TextUtils.isEmpty(this.mStoreClient.getPrerenceManager().getAtsToken())) {
                        update(EnumProvision.ProvisionEventType.REQ_SESSION_GEN.getId());
                    } else {
                        update(EnumProvision.ProvisionEventType.CHK_PHONE_ACCOUNT.getId());
                    }
                    saveNewUserOptInCase(EnumProvision.NewUserOptInCase.DEFAULT.getId());
                    break;
                } else if (i == EnumProvision.NewUserOptInCase.DELETE.getId()) {
                    saveNewUserOptInCase(EnumProvision.NewUserOptInCase.DEFAULT.getId());
                    update(EnumProvision.ProvisionEventType.CHK_PHONE_ACCOUNT.getId());
                    break;
                } else {
                    saveNewUserOptInCase(EnumProvision.NewUserOptInCase.DEFAULT.getId());
                    update(EnumProvision.ProvisionEventType.REQ_GET_TC.getId());
                    break;
                }
            case 2:
            case 3:
                if (!supportSignedBinary) {
                    unregisterDataSmsReceiver();
                }
                saveUserOptedIn(true);
                saveLastScreenUserStopBackup(attAmbsUIScreenNames.getId());
                saveLastScreen(attAmbsUIScreenNames.getId());
                this.mUIInterface.notifyAppUIScreen(ATTConstants.AttAmbsUIScreenNames.Settings_PrmptMsg10.getId(), IUIEventCallback.NON_POP_UP, 0);
                update(EnumProvision.ProvisionEventType.REQ_HUI_TOKEN.getId());
                break;
            case 4:
                this.mUIInterface.notifyAppUIScreen(ATTConstants.AttAmbsUIScreenNames.Settings_PrmptMsg10.getId(), IUIEventCallback.NON_POP_UP, 0);
                update(EnumProvision.ProvisionEventType.REQ_DELETE_ACCOUNT.getId());
                break;
            case 5:
                saveLastScreen(attAmbsUIScreenNames.getId());
                this.mUIInterface.notifyAppUIScreen(ATTConstants.AttAmbsUIScreenNames.Settings_PrmptMsg10.getId(), IUIEventCallback.NON_POP_UP, 0);
                if (this.mStoreClient.getPrerenceManager().isLastAPIRequestCreateAccount()) {
                    Log.d(this.TAG, "HUIToken 6014 case");
                    this.mStoreClient.getRetryStackAdapter().clearRetryHistory();
                    update(EnumProvision.ProvisionEventType.REQ_HUI_TOKEN.getId());
                    break;
                } else {
                    IHttpAPICommonInterface lastFailedRequest = this.mStoreClient.getRetryStackAdapter().getLastFailedRequest();
                    if (lastFailedRequest != null) {
                        Log.d(this.TAG, "SteadyStateError - retry api");
                        this.mStoreClient.getRetryStackAdapter().retryApi(lastFailedRequest, this, this.mICloudMessageManagerHelper, this.mIRetryStackAdapterHelper);
                        notifyMsgAppNonPopup(ATTConstants.AttAmbsUIScreenNames.SteadyState_PrmptMsg5.getId(), 0);
                        break;
                    } else {
                        Log.d(this.TAG, "last api is null");
                        notifyMsgAppNonPopup(ATTConstants.AttAmbsUIScreenNames.SteadyState_PrmptMsg5.getId(), 0);
                        this.mStoreClient.getRetryStackAdapter().clearRetryHistory();
                        break;
                    }
                }
            case 6:
                saveLastScreen(attAmbsUIScreenNames.getId());
                this.mUIInterface.notifyAppUIScreen(ATTConstants.AttAmbsUIScreenNames.Settings_PrmptMsg10.getId(), IUIEventCallback.NON_POP_UP, 0);
                IHttpAPICommonInterface lastFailedRequest2 = this.mStoreClient.getRetryStackAdapter().getLastFailedRequest();
                if (lastFailedRequest2 != null) {
                    Log.d(this.TAG, "SteadyStateError - retry api");
                    this.mStoreClient.getRetryStackAdapter().retryApi(lastFailedRequest2, this, this.mICloudMessageManagerHelper, this.mIRetryStackAdapterHelper);
                    notifyMsgAppNonPopup(ATTConstants.AttAmbsUIScreenNames.SteadyState_PrmptMsg5.getId(), 0);
                    break;
                } else {
                    Log.d(this.TAG, "retry stack is empty");
                    update(EnumProvision.ProvisionEventType.REQ_SESSION_GEN.getId());
                    this.mStoreClient.getRetryStackAdapter().clearRetryHistory();
                    break;
                }
            case 7:
                this.mUIInterface.notifyAppUIScreen(ATTConstants.AttAmbsUIScreenNames.Settings_PrmptMsg11.getId(), IUIEventCallback.NON_POP_UP, 0);
                this.mStoreClient.getPrerenceManager().increaseUserInputNumberCount();
                saveUserOptedIn(true);
                if (!TextUtils.isEmpty(str)) {
                    this.mStoreClient.getPrerenceManager().saveUserCtn(str, true);
                    onFixedFlow(EnumProvision.ProvisionEventType.CHECK_PHONE_STATE.getId());
                    break;
                } else {
                    Log.e(this.TAG, "phone number null");
                    this.mUIInterface.notifyAppUIScreen(ATTConstants.AttAmbsUIScreenNames.Settings_PrmptMsg9.getId(), IUIEventCallback.NON_POP_UP, 0);
                    update(EnumProvision.ProvisionEventType.REQ_INPUT_CTN.getId());
                    break;
                }
            case 8:
                ATTConstants.AttAmbsUIScreenNames attAmbsUIScreenNames2 = this.mLastScreenUserStopBackup;
                if (attAmbsUIScreenNames2 != null) {
                    notifyMsgAppNonPopup(attAmbsUIScreenNames2.getId(), 0);
                    break;
                }
                break;
        }
    }

    private void handleProvisionEvent(EnumProvision.ProvisionEventType provisionEventType) {
        Log.i(this.TAG, "handleProvisionEvent: " + provisionEventType + " mHasUserOptedIn:" + this.mHasUserOptedIn + " mIfSteadyState:" + this.mIfSteadyState + " isAMBSActive: " + isAMBSActive());
        if (provisionEventType.getId() != EnumProvision.ProvisionEventType.REQ_DELETE_ACCOUNT.getId() && provisionEventType.getId() != EnumProvision.ProvisionEventType.RESTART_SERVICE.getId() && provisionEventType.getId() != EnumProvision.ProvisionEventType.INTERNAL_RESTART.getId() && !this.mHasUserDeleteAccount && this.mPaused) {
            this.mLastSavedMessageIdAfterStop = provisionEventType.getId();
            Log.i(this.TAG, "handleMessage stop! Pending Message is " + provisionEventType);
            return;
        }
        boolean z = true;
        switch (AnonymousClass3.$SwitchMap$com$sec$internal$constants$ims$cmstore$enumprovision$EnumProvision$ProvisionEventType[provisionEventType.ordinal()]) {
            case 1:
                registerNetworkChangeReceiver();
                registerDataSmsReceiver();
                if (ATTGlobalVariables.isGcmReplacePolling()) {
                    registerConfigurationObserver();
                }
                if (CmsUtil.isSimOrCtnChanged(this.mStoreClient) || ProvisionHelper.isOOBE(this.mStoreClient) || TextUtils.isEmpty(this.mStoreClient.getPrerenceManager().getUserCtn())) {
                    Log.i(this.TAG, "isSimOrCtnChanged || OOBE || empty CTN");
                    startOOBE();
                    this.mStoreClient.getPrerenceManager().saveAppVer(ATTGlobalVariables.VERSION_NAME);
                    return;
                }
                this.mStoreClient.getPrerenceManager().saveAppVer(ATTGlobalVariables.VERSION_NAME);
                if (this.mStoreClient.getPrerenceManager().hasShownPopupOptIn() && !this.mHasUserOptedIn) {
                    Log.i(this.TAG, "has shown popup before, will not bother user and server, non_popup screen : " + this.mLastUIScreen);
                    ATTConstants.AttAmbsUIScreenNames attAmbsUIScreenNames = this.mLastUIScreen;
                    if (attAmbsUIScreenNames != null) {
                        notifyMsgAppNonPopup(attAmbsUIScreenNames.getId(), 0);
                        return;
                    } else {
                        notifyMsgAppNonPopup(ATTConstants.AttAmbsUIScreenNames.NewUserOptIn_PrmptMsg1.getId(), 0);
                        return;
                    }
                }
                if (this.mStoreClient.getRetryStackAdapter().isRetryTimesFinished(this.mICloudMessageManagerHelper)) {
                    Log.i(this.TAG, "isRetryTimesFinished");
                    ATTConstants.AttAmbsUIScreenNames attAmbsUIScreenNames2 = this.mLastUIScreen;
                    if (attAmbsUIScreenNames2 != null) {
                        notifyMsgAppNonPopup(attAmbsUIScreenNames2.getId(), 0);
                        return;
                    }
                    return;
                }
                IHttpAPICommonInterface lastFailedRequest = this.mStoreClient.getRetryStackAdapter().getLastFailedRequest();
                if (lastFailedRequest != null) {
                    Log.i(this.TAG, "retryLastApi");
                    if (lastFailedRequest instanceof ReqZCode) {
                        Log.d(this.TAG, "in order to Auth Z code, register sms receiver");
                        registerSmsReceiver();
                    }
                    this.mStoreClient.getRetryStackAdapter().retryLastApi(this, this.mICloudMessageManagerHelper, this.mIRetryStackAdapterHelper);
                    ATTConstants.AttAmbsUIScreenNames attAmbsUIScreenNames3 = this.mLastUIScreen;
                    if (attAmbsUIScreenNames3 != null) {
                        notifyMsgAppNonPopup(attAmbsUIScreenNames3.getId(), 0);
                        return;
                    }
                    return;
                }
                if (TextUtils.isEmpty(this.mStoreClient.getPrerenceManager().getAtsToken())) {
                    update(EnumProvision.ProvisionEventType.CHK_PHONE_ACCOUNT.getId());
                    return;
                }
                if (TextUtils.isEmpty(this.mStoreClient.getPrerenceManager().getValidPAT())) {
                    this.mUIInterface.notifyAppUIScreen(ATTConstants.AttAmbsUIScreenNames.Settings_PrmptMsg10.getId(), IUIEventCallback.NON_POP_UP, 0);
                    update(EnumProvision.ProvisionEventType.REQ_SESSION_GEN.getId());
                    return;
                } else {
                    if (TextUtils.isEmpty(this.mStoreClient.getPrerenceManager().getValidPAT())) {
                        return;
                    }
                    Log.i(this.TAG, "PAT VALID");
                    onProvisionReady();
                    return;
                }
            case 2:
                ProvisionHelper.readAndSaveSimInformation(this.mStoreClient);
                if (TextUtils.isEmpty(this.mStoreClient.getPrerenceManager().getUserCtn())) {
                    Log.d(this.TAG, "empty CTN");
                    notifyMsgAppNonPopup(ATTConstants.AttAmbsUIScreenNames.NewUserOptIn_PrmptMsg1.getId(), ATTConstants.AttAmbsUIScreenNames.MsisdnEntry_ErrMsg6.getId());
                    return;
                } else {
                    Log.d(this.TAG, "CTN was successfully read");
                    update(EnumProvision.ProvisionEventType.CHECK_PHONE_STATE.getId());
                    return;
                }
            case 3:
                startPhoneStateListener();
                return;
            case 4:
                EnumProvision.ProvisionEventType provisionEventType2 = EnumProvision.ProvisionEventType.EVENT_AUTH_ZCODE_TIMEOUT;
                removeMessages(1, EnumProvision.ProvisionEventType.valueOf(provisionEventType2.getId()));
                String userCtn = this.mStoreClient.getPrerenceManager().getUserCtn();
                String convertPhoneNumberToUserAct = AmbsUtils.convertPhoneNumberToUserAct(this.mStoreClient.getSimManager().getMsisdn());
                if (TextUtils.isEmpty(userCtn)) {
                    Log.i(this.TAG, "empty CTN, phone number:" + IMSLog.checker(convertPhoneNumberToUserAct));
                    if (TextUtils.isEmpty(convertPhoneNumberToUserAct)) {
                        convertPhoneNumberToUserAct = this.mStoreClient.getCloudMessageStrategyManager().getStrategy().getNativeLine();
                        Log.i(this.TAG, "Phone number from DB == " + IMSLog.checker(convertPhoneNumberToUserAct));
                    }
                    this.mStoreClient.getPrerenceManager().saveUserCtn(convertPhoneNumberToUserAct, false);
                } else if (!TextUtils.isEmpty(convertPhoneNumberToUserAct) && !userCtn.equals(convertPhoneNumberToUserAct)) {
                    Log.i(this.TAG, "Phone number was changed!!");
                    if (this.mStoreClient.getCloudMessageStrategyManager().getStrategy().needToHandleSimSwap()) {
                        this.mIWorkingStatusProvisionListener.onRestartService();
                        return;
                    }
                    return;
                }
                registerSmsReceiver();
                updateDelay(provisionEventType2.getId(), 900000L);
                this.mStoreClient.getHttpController().execute(new ReqZCode(this, this.mStoreClient, this.mICloudMessageManagerHelper));
                return;
            case 5:
                removeMessages(1, EnumProvision.ProvisionEventType.valueOf(EnumProvision.ProvisionEventType.EVENT_AUTH_ZCODE_TIMEOUT.getId()));
                unregisterSmsReceiver();
                stopPhoneStateListener();
                this.mStoreClient.getHttpController().execute(new ReqToken(this, this.mStoreClient, this.mICloudMessageManagerHelper));
                return;
            case 6:
                if (ATTGlobalVariables.isGcmReplacePolling()) {
                    readNcNmsHost();
                    initSharedPreference();
                }
                this.mIWorkingStatusProvisionListener.onChannelStateReset();
                this.mStoreClient.getHttpController().execute(new ReqSession(this, this.mStoreClient, this.mIRetryStackAdapterHelper, this.mICloudMessageManagerHelper));
                return;
            case 7:
                this.mStoreClient.getHttpController().execute(new RequestAccount(this, this.mStoreClient));
                return;
            case 8:
                this.mStoreClient.getHttpController().execute(new RequestAccountEligibility(this, this.mStoreClient, this.mICloudMessageManagerHelper));
                return;
            case 9:
                this.mStoreClient.getHttpController().execute(new RequestTC(this, this.mStoreClient, this.mICloudMessageManagerHelper));
                return;
            case 10:
                this.mStoreClient.getHttpController().execute(new RequestCreateAccount(this, this.mStoreClient, this.mICloudMessageManagerHelper));
                return;
            case 11:
                saveUserDeleteAccount(true);
                this.mIWorkingStatusProvisionListener.onUserDeleteAccount(true);
                this.mStoreClient.getHttpController().execute(new RequestDeleteAccount(this, this.mStoreClient, this.mICloudMessageManagerHelper));
                return;
            case 12:
                if (!this.mIfSteadyState) {
                    saveIfSteadyState(true);
                }
                this.mStoreClient.getHttpController().execute(new RequestHUIToken(this, this.mStoreClient, this.mICloudMessageManagerHelper));
                return;
            case 13:
                this.mStoreClient.getHttpController().execute(new RequestPat(this, this.mStoreClient, this.mICloudMessageManagerHelper));
                return;
            case 14:
            default:
                return;
            case 15:
                this.mStoreClient.getHttpController().execute(new ReqRetireSession(this, this.mStoreClient, this.mICloudMessageManagerHelper));
                onProvisionReady();
                return;
            case 16:
                boolean z2 = this.mHasUserOptedIn;
                if (!z2 && !this.mIfSteadyState) {
                    saveNewUserOptInCase(EnumProvision.NewUserOptInCase.ERR.getId());
                    notifyMsgAppNonPopup(ATTConstants.AttAmbsUIScreenNames.NewUserOptIn_PrmptMsg1.getId(), 0);
                    return;
                } else {
                    if (z2 && !this.mIfSteadyState) {
                        this.mUIInterface.notifyAppUIScreen(ATTConstants.AttAmbsUIScreenNames.EligibilityError_ErrMsg1.getId(), IUIEventCallback.POP_UP, 0);
                        saveNewUserOptInCase(EnumProvision.NewUserOptInCase.ERR.getId());
                        notifyMsgAppNonPopup(ATTConstants.AttAmbsUIScreenNames.NewUserOptIn_PrmptMsg1.getId(), 0);
                        return;
                    }
                    this.mUIInterface.notifyAppUIScreen(ATTConstants.AttAmbsUIScreenNames.SteadyStateError_ErrMsg7.getId(), IUIEventCallback.POP_UP, 0);
                    return;
                }
            case 17:
                if (this.mStoreClient.getPrerenceManager().getIsUserInputCtn()) {
                    update(EnumProvision.ProvisionEventType.REQ_INPUT_CTN.getId());
                    return;
                }
                ProvisionHelper.readAndSaveSimInformation(this.mStoreClient);
                if (this.mStoreClient.getPrerenceManager().isZCodeMax2Tries()) {
                    Log.d(this.TAG, "No more chance. Show error screen");
                    update(EnumProvision.ProvisionEventType.AUTH_ERR.getId());
                    this.mStoreClient.getPrerenceManager().removeZCodeCounter();
                    return;
                } else {
                    this.mStoreClient.getPrerenceManager().increazeZCodeCounter();
                    update(EnumProvision.ProvisionEventType.REQ_AUTH_ZCODE.getId());
                    return;
                }
            case 18:
                if (this.mStoreClient.getPrerenceManager().getIsUserInputCtn()) {
                    this.mStoreClient.getPrerenceManager().clearInvalidUserCtn();
                }
                if (this.mStoreClient.getPrerenceManager().isNoMoreChanceUserInputNumber()) {
                    Log.d(this.TAG, "No more chance. Show error screen");
                    update(EnumProvision.ProvisionEventType.AUTH_ERR.getId());
                    return;
                } else {
                    Log.d(this.TAG, "user still has a chance to input the number");
                    notifyMsgAppNonPopup(ATTConstants.AttAmbsUIScreenNames.MsisdnEntry_ErrMsg6.getId(), 0);
                    return;
                }
            case 19:
                boolean z3 = this.mHasUserOptedIn;
                if (!z3 && !this.mIfSteadyState) {
                    saveNewUserOptInCase(EnumProvision.NewUserOptInCase.ERR.getId());
                    notifyMsgAppNonPopup(ATTConstants.AttAmbsUIScreenNames.NewUserOptIn_PrmptMsg1.getId(), 0);
                    return;
                } else {
                    if (z3 && !this.mIfSteadyState) {
                        this.mUIInterface.notifyAppUIScreen(ATTConstants.AttAmbsUIScreenNames.ProvisioningBlockedError_ErrMsg8.getId(), IUIEventCallback.POP_UP, 0);
                        saveNewUserOptInCase(EnumProvision.NewUserOptInCase.ERR.getId());
                        notifyMsgAppNonPopup(ATTConstants.AttAmbsUIScreenNames.NewUserOptIn_PrmptMsg1.getId(), 0);
                        return;
                    }
                    this.mUIInterface.notifyAppUIScreen(ATTConstants.AttAmbsUIScreenNames.SteadyStateError_ErrMsg7.getId(), IUIEventCallback.POP_UP, 0);
                    return;
                }
            case 20:
                if (!this.mHasUserOptedIn) {
                    if (!this.mIfSteadyState) {
                        saveNewUserOptInCase(EnumProvision.NewUserOptInCase.ERR.getId());
                        notifyMsgAppNonPopup(ATTConstants.AttAmbsUIScreenNames.NewUserOptIn_PrmptMsg1.getId(), 0);
                        return;
                    } else {
                        handleProvisionErr();
                        return;
                    }
                }
                if (!this.mIfSteadyState) {
                    this.mUIInterface.notifyAppUIScreen(ATTConstants.AttAmbsUIScreenNames.ProvisioningError_ErrMsg4.getId(), IUIEventCallback.POP_UP, 0);
                    saveNewUserOptInCase(EnumProvision.NewUserOptInCase.ERR.getId());
                    notifyMsgAppNonPopup(ATTConstants.AttAmbsUIScreenNames.NewUserOptIn_PrmptMsg1.getId(), 0);
                    return;
                }
                handleProvisionErr();
                return;
            case 21:
                if (this.mStoreClient.getPrerenceManager().isNoMoreChanceUserInputNumber()) {
                    Log.d(this.TAG, "max 2 tries reached");
                    this.mStoreClient.getPrerenceManager().removeUserInputNumberCount();
                    this.mStoreClient.getRetryStackAdapter().clearRetryHistory();
                    this.mStoreClient.getPrerenceManager().saveUserCtn("", false);
                } else {
                    z = false;
                }
                boolean z4 = this.mHasUserOptedIn;
                if (!z4 && !this.mIfSteadyState) {
                    saveNewUserOptInCase(EnumProvision.NewUserOptInCase.ERR.getId());
                    notifyMsgAppNonPopup(ATTConstants.AttAmbsUIScreenNames.NewUserOptIn_PrmptMsg1.getId(), 0);
                    return;
                }
                if (z4 && !this.mIfSteadyState) {
                    this.mUIInterface.notifyAppUIScreen(ATTConstants.AttAmbsUIScreenNames.AuthenticationError_ErrMsg2.getId(), IUIEventCallback.POP_UP, 0);
                    saveNewUserOptInCase(EnumProvision.NewUserOptInCase.ERR.getId());
                    if (z) {
                        notifyMsgAppNonPopup(ATTConstants.AttAmbsUIScreenNames.NewUserOptIn_PrmptMsg1.getId(), ATTConstants.AttAmbsUIScreenNames.MsisdnEntry_ErrMsg6.getId());
                        return;
                    } else {
                        notifyMsgAppNonPopup(ATTConstants.AttAmbsUIScreenNames.NewUserOptIn_PrmptMsg1.getId(), 0);
                        return;
                    }
                }
                removeMessages(EnumProvision.ProvisionEventType.EVENT_AUTH_ZCODE_TIMEOUT.getId());
                this.mUIInterface.notifyAppUIScreen(ATTConstants.AttAmbsUIScreenNames.SteadyStateError_ErrMsg7.getId(), IUIEventCallback.POP_UP, 0);
                return;
            case 22:
                if (this.mStoreClient.getPrerenceManager().isHUI6014Err()) {
                    this.mStoreClient.getPrerenceManager().saveIfHUI6014Err(false);
                }
                boolean z5 = this.mHasUserOptedIn;
                if (!z5 && !this.mIfSteadyState) {
                    saveNewUserOptInCase(EnumProvision.NewUserOptInCase.ERR.getId());
                    notifyMsgAppNonPopup(ATTConstants.AttAmbsUIScreenNames.NewUserOptIn_PrmptMsg1.getId(), 0);
                    return;
                } else {
                    if (z5 && !this.mIfSteadyState) {
                        this.mUIInterface.notifyAppUIScreen(ATTConstants.AttAmbsUIScreenNames.SteadyStateError_ErrMsg5.getId(), IUIEventCallback.POP_UP, 0);
                        saveNewUserOptInCase(EnumProvision.NewUserOptInCase.ERR.getId());
                        notifyMsgAppNonPopup(ATTConstants.AttAmbsUIScreenNames.NewUserOptIn_PrmptMsg1.getId(), 0);
                        return;
                    }
                    this.mUIInterface.notifyAppUIScreen(ATTConstants.AttAmbsUIScreenNames.SteadyStateError_ErrMsg5.getId(), IUIEventCallback.POP_UP, 0);
                    return;
                }
            case 23:
                if (this.mHasUserDeleteAccount) {
                    saveUserDeleteAccount(false);
                    this.mIWorkingStatusProvisionListener.onUserDeleteAccount(false);
                }
                this.mUIInterface.notifyAppUIScreen(ATTConstants.AttAmbsUIScreenNames.StopBackupError_ErrMsg10.getId(), IUIEventCallback.POP_UP, 0);
                notifyMsgAppNonPopup(this.mLastScreenUserStopBackup.getId(), 0);
                return;
            case 24:
                String userCtn2 = this.mStoreClient.getPrerenceManager().getUserCtn();
                String simImsi = this.mStoreClient.getPrerenceManager().getSimImsi();
                boolean hasUserOptedIn = this.mStoreClient.getPrerenceManager().hasUserOptedIn();
                boolean isUserInputCtn = this.mStoreClient.getPrerenceManager().getIsUserInputCtn();
                this.mIWorkingStatusProvisionListener.onCloudSyncWorkingStopped();
                this.mIWorkingStatusProvisionListener.onUserDeleteAccount(false);
                stopProvisioningAPIs();
                this.mStoreClient.getPrerenceManager().saveIfHasShownPopupOptIn(true);
                this.mStoreClient.getPrerenceManager().saveSimImsi(simImsi);
                this.mStoreClient.getPrerenceManager().saveUserCtn(userCtn2, isUserInputCtn);
                saveUserOptedIn(false);
                this.mStoreClient.getRetryStackAdapter().clearRetryHistory();
                this.mLastSavedMessageIdAfterStop = -1;
                saveNewUserOptInCase(EnumProvision.NewUserOptInCase.DELETE.getId());
                notifyMsgAppNonPopup(ATTConstants.AttAmbsUIScreenNames.NewUserOptIn_PrmptMsg1.getId(), 0);
                initPrefenceValues();
                if (hasUserOptedIn) {
                    registerDataSmsReceiver();
                    return;
                }
                return;
            case 25:
                this.mIsInternalRestart = true;
                if (this.mHasUserOptedIn) {
                    saveUserOptedIn(true);
                }
                this.mUIInterface.notifyAppUIScreen(ATTConstants.AttAmbsUIScreenNames.RestartMenu_Enable_PrmptMsg15.getId(), IUIEventCallback.NON_POP_UP, 0);
                this.mPaused = false;
                break;
            case 26:
                break;
            case 27:
                updateDelay(EnumProvision.ProvisionEventType.REQ_HUI_TOKEN.getId(), 10000L);
                return;
            case 28:
                if (this.mStoreClient.getPrerenceManager().getIsUserInputCtn()) {
                    Log.d(this.TAG, "Wrong CTN, clear user input");
                    this.mStoreClient.getPrerenceManager().clearInvalidUserCtn();
                }
                if (!this.mStoreClient.getPrerenceManager().isZCodeMax2Tries()) {
                    Log.d(this.TAG, "isZCodeMax2Tries: false");
                    this.mStoreClient.getPrerenceManager().increazeZCodeCounter();
                    update(EnumProvision.ProvisionEventType.CHK_PHONE_ACCOUNT.getId());
                } else {
                    Log.d(this.TAG, "isZCodeMax2Tries: true, mHasUserOptedIn:" + this.mHasUserOptedIn);
                    this.mStoreClient.getPrerenceManager().removeZCodeCounter();
                    if (this.mHasUserOptedIn) {
                        this.mUIInterface.notifyAppUIScreen(ATTConstants.AttAmbsUIScreenNames.AuthenticationError_ErrMsg2.getId(), IUIEventCallback.POP_UP, 0);
                        saveNewUserOptInCase(EnumProvision.NewUserOptInCase.ERR.getId());
                        notifyMsgAppNonPopup(ATTConstants.AttAmbsUIScreenNames.NewUserOptIn_PrmptMsg1.getId(), 0);
                    } else {
                        saveNewUserOptInCase(EnumProvision.NewUserOptInCase.ERR.getId());
                        notifyMsgAppNonPopup(ATTConstants.AttAmbsUIScreenNames.NewUserOptIn_PrmptMsg1.getId(), 0);
                    }
                }
                unregisterSmsReceiver();
                stopPhoneStateListener();
                return;
            case 29:
                onMailBoxMigrationReset();
                return;
        }
        stopProvisioningAPIs();
        Log.i(this.TAG, "Provisioning Api's");
        if (!this.mIsInternalRestart) {
            saveUserOptedIn(false);
        }
        this.mLastSavedMessageIdAfterStop = -1;
        registerNetworkChangeReceiver();
        registerDataSmsReceiver();
        update(EnumProvision.ProvisionEventType.CHK_PHONE_ACCOUNT.ordinal());
        this.mUIInterface.notifyAppUIScreen(ATTConstants.AttAmbsUIScreenNames.Settings_PrmptMsg10.getId(), IUIEventCallback.NON_POP_UP, 0);
        initPrefenceValues();
    }

    /* renamed from: com.sec.internal.ims.cmstore.ambs.provision.ProvisionController$3, reason: invalid class name */
    static /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] $SwitchMap$com$sec$internal$constants$ims$cmstore$ATTConstants$AttAmbsUIScreenNames;
        static final /* synthetic */ int[] $SwitchMap$com$sec$internal$constants$ims$cmstore$enumprovision$EnumProvision$ProvisionEventType;

        static {
            int[] iArr = new int[EnumProvision.ProvisionEventType.values().length];
            $SwitchMap$com$sec$internal$constants$ims$cmstore$enumprovision$EnumProvision$ProvisionEventType = iArr;
            try {
                iArr[EnumProvision.ProvisionEventType.CHK_INITIAL_STATE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$sec$internal$constants$ims$cmstore$enumprovision$EnumProvision$ProvisionEventType[EnumProvision.ProvisionEventType.CHK_PHONE_ACCOUNT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$sec$internal$constants$ims$cmstore$enumprovision$EnumProvision$ProvisionEventType[EnumProvision.ProvisionEventType.CHECK_PHONE_STATE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$sec$internal$constants$ims$cmstore$enumprovision$EnumProvision$ProvisionEventType[EnumProvision.ProvisionEventType.REQ_AUTH_ZCODE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$sec$internal$constants$ims$cmstore$enumprovision$EnumProvision$ProvisionEventType[EnumProvision.ProvisionEventType.REQ_ATS_TOKEN.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$sec$internal$constants$ims$cmstore$enumprovision$EnumProvision$ProvisionEventType[EnumProvision.ProvisionEventType.REQ_SESSION_GEN.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$sec$internal$constants$ims$cmstore$enumprovision$EnumProvision$ProvisionEventType[EnumProvision.ProvisionEventType.REQ_SERVICE_ACCOUNT.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$sec$internal$constants$ims$cmstore$enumprovision$EnumProvision$ProvisionEventType[EnumProvision.ProvisionEventType.REQ_ACCOUNT_ELIGIBILITY.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$sec$internal$constants$ims$cmstore$enumprovision$EnumProvision$ProvisionEventType[EnumProvision.ProvisionEventType.REQ_GET_TC.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$sec$internal$constants$ims$cmstore$enumprovision$EnumProvision$ProvisionEventType[EnumProvision.ProvisionEventType.REQ_CREATE_ACCOUNT.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$sec$internal$constants$ims$cmstore$enumprovision$EnumProvision$ProvisionEventType[EnumProvision.ProvisionEventType.REQ_DELETE_ACCOUNT.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$sec$internal$constants$ims$cmstore$enumprovision$EnumProvision$ProvisionEventType[EnumProvision.ProvisionEventType.REQ_HUI_TOKEN.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$sec$internal$constants$ims$cmstore$enumprovision$EnumProvision$ProvisionEventType[EnumProvision.ProvisionEventType.REQ_PAT.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$com$sec$internal$constants$ims$cmstore$enumprovision$EnumProvision$ProvisionEventType[EnumProvision.ProvisionEventType.REQ_RETIRE_SESSION.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$com$sec$internal$constants$ims$cmstore$enumprovision$EnumProvision$ProvisionEventType[EnumProvision.ProvisionEventType.READY_PAT.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$com$sec$internal$constants$ims$cmstore$enumprovision$EnumProvision$ProvisionEventType[EnumProvision.ProvisionEventType.ACCOUNT_NOT_ELIGIBLE.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$com$sec$internal$constants$ims$cmstore$enumprovision$EnumProvision$ProvisionEventType[EnumProvision.ProvisionEventType.ZCODE_ERROR_201.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                $SwitchMap$com$sec$internal$constants$ims$cmstore$enumprovision$EnumProvision$ProvisionEventType[EnumProvision.ProvisionEventType.REQ_INPUT_CTN.ordinal()] = 18;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                $SwitchMap$com$sec$internal$constants$ims$cmstore$enumprovision$EnumProvision$ProvisionEventType[EnumProvision.ProvisionEventType.CPS_PROVISION_SHUTDOWN.ordinal()] = 19;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                $SwitchMap$com$sec$internal$constants$ims$cmstore$enumprovision$EnumProvision$ProvisionEventType[EnumProvision.ProvisionEventType.PROVISION_ERR.ordinal()] = 20;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                $SwitchMap$com$sec$internal$constants$ims$cmstore$enumprovision$EnumProvision$ProvisionEventType[EnumProvision.ProvisionEventType.AUTH_ERR.ordinal()] = 21;
            } catch (NoSuchFieldError unused21) {
            }
            try {
                $SwitchMap$com$sec$internal$constants$ims$cmstore$enumprovision$EnumProvision$ProvisionEventType[EnumProvision.ProvisionEventType.ACCESS_ERR.ordinal()] = 22;
            } catch (NoSuchFieldError unused22) {
            }
            try {
                $SwitchMap$com$sec$internal$constants$ims$cmstore$enumprovision$EnumProvision$ProvisionEventType[EnumProvision.ProvisionEventType.STOP_BACKUP_ERR.ordinal()] = 23;
            } catch (NoSuchFieldError unused23) {
            }
            try {
                $SwitchMap$com$sec$internal$constants$ims$cmstore$enumprovision$EnumProvision$ProvisionEventType[EnumProvision.ProvisionEventType.DELETE_ACCOUNT_SUCCESS.ordinal()] = 24;
            } catch (NoSuchFieldError unused24) {
            }
            try {
                $SwitchMap$com$sec$internal$constants$ims$cmstore$enumprovision$EnumProvision$ProvisionEventType[EnumProvision.ProvisionEventType.INTERNAL_RESTART.ordinal()] = 25;
            } catch (NoSuchFieldError unused25) {
            }
            try {
                $SwitchMap$com$sec$internal$constants$ims$cmstore$enumprovision$EnumProvision$ProvisionEventType[EnumProvision.ProvisionEventType.RESTART_SERVICE.ordinal()] = 26;
            } catch (NoSuchFieldError unused26) {
            }
            try {
                $SwitchMap$com$sec$internal$constants$ims$cmstore$enumprovision$EnumProvision$ProvisionEventType[EnumProvision.ProvisionEventType.LAST_RETRY_CREATE_ACCOUNT.ordinal()] = 27;
            } catch (NoSuchFieldError unused27) {
            }
            try {
                $SwitchMap$com$sec$internal$constants$ims$cmstore$enumprovision$EnumProvision$ProvisionEventType[EnumProvision.ProvisionEventType.EVENT_AUTH_ZCODE_TIMEOUT.ordinal()] = 28;
            } catch (NoSuchFieldError unused28) {
            }
            try {
                $SwitchMap$com$sec$internal$constants$ims$cmstore$enumprovision$EnumProvision$ProvisionEventType[EnumProvision.ProvisionEventType.MAILBOX_MIGRATION_RESET.ordinal()] = 29;
            } catch (NoSuchFieldError unused29) {
            }
            int[] iArr2 = new int[ATTConstants.AttAmbsUIScreenNames.values().length];
            $SwitchMap$com$sec$internal$constants$ims$cmstore$ATTConstants$AttAmbsUIScreenNames = iArr2;
            try {
                iArr2[ATTConstants.AttAmbsUIScreenNames.NewUserOptIn_PrmptMsg1.ordinal()] = 1;
            } catch (NoSuchFieldError unused30) {
            }
            try {
                $SwitchMap$com$sec$internal$constants$ims$cmstore$ATTConstants$AttAmbsUIScreenNames[ATTConstants.AttAmbsUIScreenNames.ExistingUserOptInWithTerms_PrmptMsg3.ordinal()] = 2;
            } catch (NoSuchFieldError unused31) {
            }
            try {
                $SwitchMap$com$sec$internal$constants$ims$cmstore$ATTConstants$AttAmbsUIScreenNames[ATTConstants.AttAmbsUIScreenNames.ExistingUserOptInWoTerms_PrmpMsg4.ordinal()] = 3;
            } catch (NoSuchFieldError unused32) {
            }
            try {
                $SwitchMap$com$sec$internal$constants$ims$cmstore$ATTConstants$AttAmbsUIScreenNames[ATTConstants.AttAmbsUIScreenNames.StopBackup_PrmptMsg13.ordinal()] = 4;
            } catch (NoSuchFieldError unused33) {
            }
            try {
                $SwitchMap$com$sec$internal$constants$ims$cmstore$ATTConstants$AttAmbsUIScreenNames[ATTConstants.AttAmbsUIScreenNames.SteadyStateError_ErrMsg5.ordinal()] = 5;
            } catch (NoSuchFieldError unused34) {
            }
            try {
                $SwitchMap$com$sec$internal$constants$ims$cmstore$ATTConstants$AttAmbsUIScreenNames[ATTConstants.AttAmbsUIScreenNames.SteadyStateError_ErrMsg7.ordinal()] = 6;
            } catch (NoSuchFieldError unused35) {
            }
            try {
                $SwitchMap$com$sec$internal$constants$ims$cmstore$ATTConstants$AttAmbsUIScreenNames[ATTConstants.AttAmbsUIScreenNames.MsisdnEntry_ErrMsg6.ordinal()] = 7;
            } catch (NoSuchFieldError unused36) {
            }
            try {
                $SwitchMap$com$sec$internal$constants$ims$cmstore$ATTConstants$AttAmbsUIScreenNames[ATTConstants.AttAmbsUIScreenNames.StopBackupError_ErrMsg10.ordinal()] = 8;
            } catch (NoSuchFieldError unused37) {
            }
        }
    }

    private void startOOBE() {
        this.mUIInterface.notifyAppUIScreen(ATTConstants.AttAmbsUIScreenNames.Settings_PrmptMsg10.getId(), IUIEventCallback.NON_POP_UP, 0);
        this.mStoreClient.getHttpController().getCookieJar().removeAll();
        this.mStoreClient.getPrerenceManager().clearAll();
        this.mIWorkingStatusProvisionListener.onCleanBufferDbRequired();
        initPrefenceValues();
        update(EnumProvision.ProvisionEventType.CHK_PHONE_ACCOUNT.getId());
    }

    private void onProvisionReady() {
        Log.i(this.TAG, "onProvisionReady");
        saveIfSteadyState(true);
        notifyMsgAppNonPopup(ATTConstants.AttAmbsUIScreenNames.SteadyState_PrmptMsg5.getId(), 0);
        this.mIWorkingStatusProvisionListener.onProvisionSuccess();
        if (ATTGlobalVariables.supportSignedBinary()) {
            return;
        }
        unregisterDataSmsReceiver();
    }

    private void onProvisionAPISuccess(SuccessfulAPICallResponseParam successfulAPICallResponseParam) {
        Log.i(this.TAG, "onProvisionAPISuccess: " + successfulAPICallResponseParam);
        handlerUIonSuccessProvisionAPI(successfulAPICallResponseParam);
        String str = successfulAPICallResponseParam.mCallFlow;
        if (str != null) {
            SuccessfulCallHandling.callHandling(this.mStoreClient, this, successfulAPICallResponseParam.mRequest, str, this.mIRetryStackAdapterHelper, this.mICloudMessageManagerHelper);
        } else {
            SuccessfulCallHandling.callHandling(this.mStoreClient, this, successfulAPICallResponseParam.mRequest, this.mIRetryStackAdapterHelper, this.mICloudMessageManagerHelper);
        }
        if (this.mIsInternalRestart) {
            this.mIsInternalRestart = false;
        }
    }

    private void handleInternalRestart(SuccessfulAPICallResponseParam successfulAPICallResponseParam) {
        Log.i(this.TAG, "handleInternalRestart, restart without optin ui");
        this.mIsInternalRestart = false;
        if (this.mHasUserOptedIn || this.mIfSteadyState) {
            return;
        }
        Log.d(this.TAG, "handlerUIonSuccessProvisionAPI: User has NOT opted in: isOOBE?: " + ProvisionHelper.isOOBE(this.mStoreClient) + " hasUserOptedIn: " + this.mHasUserOptedIn);
        if (ReqConstant.HAPPY_PATH_REQACCOUNT_GET_TC.equalsIgnoreCase(successfulAPICallResponseParam.mCallFlow)) {
            sendMessage(obtainMessage(2, new UIEventParam(ATTConstants.AttAmbsUIScreenNames.NewUserOptIn_PrmptMsg1, null)));
        } else if (ReqConstant.HAPPY_PATH_REQACCOUNT_EXIST_USER.equalsIgnoreCase(successfulAPICallResponseParam.mCallFlow)) {
            sendMessage(obtainMessage(2, new UIEventParam(ATTConstants.AttAmbsUIScreenNames.ExistingUserOptInWoTerms_PrmpMsg4, null)));
        } else {
            Log.i(this.TAG, "illegal returned callflow name");
        }
    }

    private void handlerUIonSuccessProvisionAPI(SuccessfulAPICallResponseParam successfulAPICallResponseParam) {
        Log.i(this.TAG, "handlerUIonSuccessProvisionAPI: " + successfulAPICallResponseParam);
        if (RequestAccount.class.getSimpleName().equals(successfulAPICallResponseParam.getApiName())) {
            if (this.mIsInternalRestart) {
                handleInternalRestart(successfulAPICallResponseParam);
            }
            if (this.mHasUserOptedIn || this.mIfSteadyState) {
                if (ReqConstant.HAPPY_PATH_REQACCOUNT_EXIST_USER.equalsIgnoreCase(successfulAPICallResponseParam.mCallFlow)) {
                    Log.d(this.TAG, "handlerUIonSuccessProvisionAPI: HAPPY_PATH_REQ_ACCOUNT_EXIST_USER");
                    update(EnumProvision.ProvisionEventType.REQ_HUI_TOKEN.getId());
                } else if (ReqConstant.HAPPY_PATH_REQACCOUNT_GET_TC.equalsIgnoreCase(successfulAPICallResponseParam.mCallFlow)) {
                    Log.d(this.TAG, "handlerUIonSuccessProvisionAPI: NEW_USER");
                    if (this.mStoreClient.getPrerenceManager().isHUI6014Err()) {
                        Log.d(this.TAG, "handlerUIonSuccessProvisionAPI: SOC removal");
                        this.mIWorkingStatusProvisionListener.onCloudSyncWorkingStopped();
                        saveNewUserOptInCase(EnumProvision.NewUserOptInCase.DELETE.getId());
                        notifyMsgAppNonPopup(ATTConstants.AttAmbsUIScreenNames.NewUserOptIn_PrmptMsg1.getId(), 0);
                    } else {
                        update(EnumProvision.ProvisionEventType.REQ_GET_TC.getId());
                    }
                } else {
                    Log.d(this.TAG, "handlerUIonSuccessProvisionAPI: TBS_TC");
                    update(EnumProvision.ProvisionEventType.REQ_HUI_TOKEN.getId());
                }
            } else {
                Log.d(this.TAG, "handlerUIonSuccessProvisionAPI: User has NOT opted in: isOOBE?: " + ProvisionHelper.isOOBE(this.mStoreClient) + " hasUserOptedIn: " + this.mHasUserOptedIn);
                if (ReqConstant.HAPPY_PATH_REQACCOUNT_GET_TC.equalsIgnoreCase(successfulAPICallResponseParam.mCallFlow)) {
                    ATTConstants.AttAmbsUIScreenNames attAmbsUIScreenNames = ATTConstants.AttAmbsUIScreenNames.NewUserOptIn_PrmptMsg1;
                    saveLastScreenUserStopBackup(attAmbsUIScreenNames.getId());
                    displayOptIn(attAmbsUIScreenNames.getId());
                } else if (ReqConstant.HAPPY_PATH_REQACCOUNT_EXIST_USER.equalsIgnoreCase(successfulAPICallResponseParam.mCallFlow)) {
                    ATTConstants.AttAmbsUIScreenNames attAmbsUIScreenNames2 = ATTConstants.AttAmbsUIScreenNames.ExistingUserOptInWoTerms_PrmpMsg4;
                    saveLastScreenUserStopBackup(attAmbsUIScreenNames2.getId());
                    displayOptIn(attAmbsUIScreenNames2.getId());
                } else if (ReqConstant.HAPPY_PATH_BINARY_SMS_PROVISIONED.equalsIgnoreCase(successfulAPICallResponseParam.mCallFlow)) {
                    ATTConstants.AttAmbsUIScreenNames attAmbsUIScreenNames3 = ATTConstants.AttAmbsUIScreenNames.ExistingUserOptInWoTerms_PrmpMsg4;
                    saveLastScreenUserStopBackup(attAmbsUIScreenNames3.getId());
                    this.mStoreClient.getPrerenceManager().saveIfHasShownPopupOptIn(false);
                    displayOptIn(attAmbsUIScreenNames3.getId());
                } else if (ReqConstant.HAPPY_PATH_REQACCOUNT_GET_TBS_TC.equalsIgnoreCase(successfulAPICallResponseParam.mCallFlow)) {
                    this.mStoreClient.getPrerenceManager().saveUserTbsRquired(true);
                    ATTConstants.AttAmbsUIScreenNames attAmbsUIScreenNames4 = ATTConstants.AttAmbsUIScreenNames.ExistingUserOptInWithTerms_PrmptMsg3;
                    saveLastScreenUserStopBackup(attAmbsUIScreenNames4.getId());
                    displayOptIn(attAmbsUIScreenNames4.getId());
                } else {
                    Log.i(this.TAG, "illegal returned callflow name");
                }
            }
        }
        if (RequestHUIToken.class.getSimpleName().equals(successfulAPICallResponseParam.getApiName())) {
            Log.i(this.TAG, "handlerUIonSuccessProvisionAPI: RequestHUIToken API success");
            if (!this.mIfSteadyState || this.mHasUserDeleteAccount) {
                return;
            }
            notifyMsgAppNonPopup(ATTConstants.AttAmbsUIScreenNames.SteadyState_PrmptMsg5.getId(), 0);
        }
    }

    private void displayOptIn(int i) {
        boolean hasShownPopupOptIn = this.mStoreClient.getPrerenceManager().hasShownPopupOptIn();
        Log.d(this.TAG, "displayOptIn: hasShownPopUpOptIn? : " + hasShownPopupOptIn + " mHasUserOptedIn:" + this.mHasUserOptedIn);
        if (!hasShownPopupOptIn) {
            if (ProvisionHelper.isOOBE(this.mStoreClient) || !this.mHasUserOptedIn) {
                this.mUIInterface.notifyAppUIScreen(i, IUIEventCallback.POP_UP, 0);
                this.mStoreClient.getPrerenceManager().saveIfHasShownPopupOptIn(true);
                notifyMsgAppNonPopup(i, 0);
                return;
            }
            Log.d(this.TAG, "handlerUIonSuccessProvisionAPI: !isOOBE && UserHasOptedIn - impossible here");
            return;
        }
        if (ProvisionHelper.isOOBE(this.mStoreClient) || !this.mHasUserOptedIn) {
            notifyMsgAppNonPopup(i, 0);
        } else {
            Log.d(this.TAG, "handlerUIonSuccessProvisionAPI: !OOBE && UserOptedIn");
        }
    }

    private void onProvisionAPIFail(FailedAPICallResponseParam failedAPICallResponseParam) {
        Log.i(this.TAG, "onProvisionAPIFail: " + failedAPICallResponseParam);
        handlerUIonFailedProvisionAPI(failedAPICallResponseParam);
        String str = failedAPICallResponseParam.mErrorCode;
        if (str != null) {
            ErrorRuleHandling.handleErrorCode(this.mStoreClient, this, failedAPICallResponseParam.mRequest, str, this.mIRetryStackAdapterHelper, this.mICloudMessageManagerHelper);
        } else {
            ErrorRuleHandling.handleErrorCode(this.mStoreClient, this, failedAPICallResponseParam.mRequest, this.mIRetryStackAdapterHelper, this.mICloudMessageManagerHelper);
        }
    }

    private void handlerUIonFailedProvisionAPI(FailedAPICallResponseParam failedAPICallResponseParam) {
        Log.i(this.TAG, "handlerUIonFailedProvisionAPI: all failed APIs should go here. param: " + failedAPICallResponseParam);
    }

    private void notifyMsgAppNonPopup(int i, int i2) {
        Log.d(this.TAG, "screen to display: " + i);
        if (i == ATTConstants.AttAmbsUIScreenNames.ExistingUserOptInWithTerms_PrmptMsg3.getId() || i == ATTConstants.AttAmbsUIScreenNames.ExistingUserOptInWoTerms_PrmpMsg4.getId() || i == ATTConstants.AttAmbsUIScreenNames.SteadyState_PrmptMsg5.getId()) {
            saveLastScreenUserStopBackup(i);
        }
        saveLastScreen(i);
        this.mUIInterface.notifyAppUIScreen(ATTConstants.AttAmbsUIScreenNames.Settings_PrmptMsg9.getId(), IUIEventCallback.NON_POP_UP, 0);
        if (i2 > 0) {
            this.mUIInterface.notifyAppUIScreen(i, IUIEventCallback.NON_POP_UP, i2);
        } else {
            this.mUIInterface.notifyAppUIScreen(i, IUIEventCallback.NON_POP_UP, 0);
        }
    }

    private void stopProvisioningAPIs() {
        Log.d(this.TAG, "stopProvisioningAPIs");
        for (int i = 1; i <= 4; i++) {
            removeMessages(i);
        }
    }

    private void saveNewUserOptInCase(int i) {
        this.mStoreClient.getPrerenceManager().saveNewUserOptInCase(i);
        this.mNewUserOptInCase = i;
    }

    private void saveUserOptedIn(boolean z) {
        this.mStoreClient.getPrerenceManager().saveUserOptedIn(z);
        this.mHasUserOptedIn = z;
    }

    private void saveIfSteadyState(boolean z) {
        this.mStoreClient.getPrerenceManager().saveIfSteadyState(z);
        this.mIfSteadyState = z;
    }

    private void saveLastScreen(int i) {
        this.mStoreClient.getPrerenceManager().saveLastScreen(i);
        this.mLastUIScreen = ATTConstants.AttAmbsUIScreenNames.valueOf(i);
    }

    private void saveLastScreenUserStopBackup(int i) {
        this.mStoreClient.getPrerenceManager().saveLastScreenUserStopBackup(i);
        this.mLastScreenUserStopBackup = ATTConstants.AttAmbsUIScreenNames.valueOf(i);
    }

    private void saveUserDeleteAccount(boolean z) {
        this.mStoreClient.getPrerenceManager().saveUserDeleteAccount(z);
        this.mHasUserDeleteAccount = z;
    }

    private void logCurrentWorkingStatus() {
        Log.d(this.TAG, "logCurrentWorkingStatus: [mLastSavedMessageIdAfterStop: " + this.mLastSavedMessageIdAfterStop + " mPaused: " + this.mPaused + " mNewUserOptInCase: " + this.mNewUserOptInCase + " mIfSteadyState: " + this.mIfSteadyState + " mHasUserOptedIn: " + this.mHasUserOptedIn + " mLastUIScreen: " + this.mLastUIScreen + " mLastScreenUserStopBackup: " + this.mLastScreenUserStopBackup + " mHasUserDeleteAccount: " + this.mHasUserDeleteAccount + "]");
    }

    @Override // com.sec.internal.interfaces.ims.cmstore.IAPICallFlowListener
    public void onGoToEvent(int i, Object obj) {
        EnumProvision.ProvisionEventType valueOf = EnumProvision.ProvisionEventType.valueOf(i);
        Log.i(this.TAG, "onGoToEvent: " + valueOf);
        sendMessage(obtainMessage(1, valueOf));
    }

    @Override // com.sec.internal.interfaces.ims.cmstore.IAPICallFlowListener
    public void onSuccessfulCall(IHttpAPICommonInterface iHttpAPICommonInterface, String str) {
        sendMessage(obtainMessage(3, new SuccessfulAPICallResponseParam(iHttpAPICommonInterface, str)));
    }

    @Override // com.sec.internal.interfaces.ims.cmstore.IAPICallFlowListener
    public void onSuccessfulCall(IHttpAPICommonInterface iHttpAPICommonInterface) {
        sendMessage(obtainMessage(3, new SuccessfulAPICallResponseParam(iHttpAPICommonInterface, null)));
    }

    @Override // com.sec.internal.interfaces.ims.cmstore.IAPICallFlowListener
    public void onFailedCall(IHttpAPICommonInterface iHttpAPICommonInterface, String str) {
        sendMessage(obtainMessage(4, new FailedAPICallResponseParam(iHttpAPICommonInterface, str)));
    }

    @Override // com.sec.internal.interfaces.ims.cmstore.IAPICallFlowListener
    public void onFailedCall(IHttpAPICommonInterface iHttpAPICommonInterface, BufferDBChangeParam bufferDBChangeParam) {
        sendMessage(obtainMessage(4, new FailedAPICallResponseParam(iHttpAPICommonInterface, null)));
    }

    @Override // com.sec.internal.interfaces.ims.cmstore.IAPICallFlowListener
    public void onFailedCall(IHttpAPICommonInterface iHttpAPICommonInterface) {
        sendMessage(obtainMessage(4, new FailedAPICallResponseParam(iHttpAPICommonInterface, null)));
    }

    @Override // com.sec.internal.interfaces.ims.cmstore.IAPICallFlowListener
    public void onOverRequest(IHttpAPICommonInterface iHttpAPICommonInterface, String str, int i) {
        ErrorRuleHandling.handleErrorHeader(this.mStoreClient, this, iHttpAPICommonInterface, str, i, this.mIRetryStackAdapterHelper, this.mICloudMessageManagerHelper);
    }

    @Override // com.sec.internal.interfaces.ims.cmstore.IAPICallFlowListener
    public void onFixedFlow(int i) {
        EnumProvision.ProvisionEventType valueOf = EnumProvision.ProvisionEventType.valueOf(i);
        Log.i(this.TAG, "onFixedFlow: " + valueOf);
        sendMessage(obtainMessage(1, valueOf));
    }

    @Override // com.sec.internal.interfaces.ims.cmstore.IControllerCommonInterface
    public void start() {
        Log.i(this.TAG, "start");
        updateDelay(EnumProvision.ProvisionEventType.CHK_INITIAL_STATE.getId(), 5000L);
    }

    @Override // com.sec.internal.interfaces.ims.cmstore.IControllerCommonInterface
    public void resume() {
        Log.i(this.TAG, McsConstants.SyncMessageStatus.RESUME);
        sendMessage(obtainMessage(5));
    }

    @Override // com.sec.internal.interfaces.ims.cmstore.IControllerCommonInterface
    public void pause() {
        Log.i(this.TAG, "pause");
        sendMessage(obtainMessage(6));
    }

    public void pauseService() {
        Log.i(this.TAG, "pauseService");
        sendMessage(obtainMessage(8));
    }

    @Override // com.sec.internal.interfaces.ims.cmstore.IControllerCommonInterface
    public void stop() {
        Log.i(this.TAG, "stopService");
        sendMessage(obtainMessage(7));
    }

    @Override // com.sec.internal.interfaces.ims.cmstore.IControllerCommonInterface
    public boolean update(int i) {
        EnumProvision.ProvisionEventType valueOf = EnumProvision.ProvisionEventType.valueOf(i);
        Log.i(this.TAG, "update: " + valueOf);
        return sendMessage(obtainMessage(1, valueOf));
    }

    @Override // com.sec.internal.interfaces.ims.cmstore.IControllerCommonInterface
    public boolean updateDelay(int i, long j) {
        EnumProvision.ProvisionEventType valueOf = EnumProvision.ProvisionEventType.valueOf(i);
        Log.i(this.TAG, "update with " + valueOf + " delayed " + j);
        return sendMessageDelayed(obtainMessage(1, valueOf), j);
    }

    @Override // com.sec.internal.interfaces.ims.cmstore.IControllerCommonInterface
    public boolean updateDelayRetry(int i, long j) {
        this.mUIInterface.notifyAppUIScreen(ATTConstants.AttAmbsUIScreenNames.Settings_PrmptMsg11.getId(), IUIEventCallback.NON_POP_UP, 0);
        EnumProvision.ProvisionEventType valueOf = EnumProvision.ProvisionEventType.valueOf(i);
        Log.i(this.TAG, "update with " + valueOf + " delayed retry " + j);
        return sendMessageDelayed(obtainMessage(1, valueOf), j);
    }

    public void onOmaFailExceedMaxCount() {
        IUIEventCallback iUIEventCallback = this.mUIInterface;
        ATTConstants.AttAmbsUIScreenNames attAmbsUIScreenNames = ATTConstants.AttAmbsUIScreenNames.SteadyStateError_ErrMsg7;
        iUIEventCallback.notifyAppUIScreen(attAmbsUIScreenNames.getId(), IUIEventCallback.POP_UP, 0);
        this.mUIInterface.showInitsyncIndicator(false);
        notifyMsgAppNonPopup(attAmbsUIScreenNames.getId(), 0);
        saveLastScreen(attAmbsUIScreenNames.getId());
    }

    private void initSharedPreference() {
        CloudMessagePreferenceManager prerenceManager = this.mStoreClient.getPrerenceManager();
        prerenceManager.saveOMAChannelResURL("");
        prerenceManager.saveOMAChannelURL("");
        prerenceManager.saveOMACallBackURL("");
        prerenceManager.saveOMAChannelCreateTime(0L);
        prerenceManager.saveOMAChannelLifeTime(0L);
        prerenceManager.clearOMASubscriptionChannelDuration();
        prerenceManager.clearOMASubscriptionTime();
    }

    public void onMailBoxMigrationReset() {
        Log.i(this.TAG, "onMailBoxMigrationReset.");
        this.mIWorkingStatusProvisionListener.onMailBoxMigrationReset();
    }

    private void handleProvisionErr() {
        Log.d(this.TAG, "handleProvisionErr, TBS Case:" + this.mStoreClient.getPrerenceManager().getUserTbs());
        if (!this.mStoreClient.getPrerenceManager().getUserTbs()) {
            this.mUIInterface.notifyAppUIScreen(ATTConstants.AttAmbsUIScreenNames.SteadyStateError_ErrMsg7.getId(), IUIEventCallback.POP_UP, 0);
        } else {
            this.mStoreClient.getPrerenceManager().saveUserTbsRquired(false);
        }
    }

    public void resetDataReceiver() {
        Log.d(this.TAG, "reset DataSmsReceiver ");
        unregisterDataSmsReceiver();
        registerDataSmsReceiver();
    }
}
