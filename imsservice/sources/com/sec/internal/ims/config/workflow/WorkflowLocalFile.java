package com.sec.internal.ims.config.workflow;

import android.content.Context;
import android.database.sqlite.SQLiteFullException;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.sec.ims.settings.ImsProfile;
import com.sec.internal.constants.Mno;
import com.sec.internal.google.SecImsNotifier;
import com.sec.internal.helper.OmcCode;
import com.sec.internal.helper.SimUtil;
import com.sec.internal.ims.config.exception.InvalidXmlException;
import com.sec.internal.ims.config.exception.NoInitialDataException;
import com.sec.internal.ims.config.exception.UnknownStatusException;
import com.sec.internal.ims.config.workflow.WorkflowBase;
import com.sec.internal.ims.rcs.util.RcsUtils;
import com.sec.internal.ims.servicemodules.ss.UtStateMachine;
import com.sec.internal.ims.util.ConfigUtil;
import com.sec.internal.interfaces.ims.config.IConfigModule;
import com.sec.internal.log.IMSLog;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

/* loaded from: classes.dex */
public class WorkflowLocalFile extends WorkflowBase {
    private static final String LOG_TAG = WorkflowLocalFile.class.getSimpleName();
    String mClientVendor;
    boolean mIsAcsSkipped;

    @Override // com.sec.internal.ims.config.workflow.WorkflowBase, com.sec.internal.interfaces.ims.config.IWorkflow
    public boolean checkNetworkConnectivity() {
        return false;
    }

    public WorkflowLocalFile(Looper looper, Context context, IConfigModule iConfigModule, Mno mno, int i) {
        super(looper, context, iConfigModule, mno, i);
        this.mIsAcsSkipped = false;
        this.mClientVendor = "";
    }

    @Override // com.sec.internal.ims.config.workflow.WorkflowBase, android.os.Handler
    public void handleMessage(Message message) {
        String str = LOG_TAG;
        IMSLog.i(str, this.mPhoneId, "message: " + message.what);
        if (message.what == 16) {
            Object obj = message.obj;
            if (obj == null) {
                IMSLog.i(str, this.mPhoneId, "AutoConfig: client info is not available");
                return;
            }
            if (this.mParamHandler.setRcsClientConfiguration(((Bundle) obj).getString("rcsVersion"), ((Bundle) message.obj).getString("rcsProfile"), ((Bundle) message.obj).getString("clientVendor"), ((Bundle) message.obj).getString("clientVersion"))) {
                IMSLog.i(str, this.mPhoneId, "AutoConfig: client info is changed: need auto config to use the changed client info");
                this.mModule.getHandler().sendMessage(obtainMessage(1, this.mPhoneId, 0, null));
                return;
            }
            return;
        }
        super.handleMessage(message);
    }

    @Override // com.sec.internal.ims.config.workflow.WorkflowBase
    void work() {
        WorkflowBase.Workflow nextWorkflow;
        WorkflowBase.Workflow nextWorkflow2 = getNextWorkflow(1);
        if (RcsUtils.isImsSingleRegiRequired(this.mContext, this.mPhoneId) && ConfigUtil.isGoogDmaPackageInuse(this.mContext, this.mPhoneId) && this.mParamHandler.isRcsClientConfigurationInfoNotSet()) {
            IMSLog.i(LOG_TAG, this.mPhoneId, "RCS client info was not set yet: skip autoconfig");
            nextWorkflow2 = getNextWorkflow(8);
            this.mIsAcsSkipped = true;
        }
        for (int i = WorkflowBase.AUTO_CONFIG_MAX_FLOWCOUNT; nextWorkflow2 != null && i > 0; i--) {
            try {
                nextWorkflow2 = nextWorkflow2.run();
            } catch (SQLiteFullException e) {
                Log.i(LOG_TAG, "SQLiteFullException occur:" + e.getMessage());
                Log.i(LOG_TAG, "finish workflow");
                nextWorkflow = getNextWorkflow(8);
                e.printStackTrace();
                nextWorkflow2 = nextWorkflow;
            } catch (NoInitialDataException e2) {
                Log.i(LOG_TAG, "NoInitialDataException occur:" + e2.getMessage());
                Log.i(LOG_TAG, "wait 10 sec. and retry");
                sleep(10000L);
                nextWorkflow = getNextWorkflow(1);
                e2.printStackTrace();
                nextWorkflow2 = nextWorkflow;
            } catch (UnknownStatusException e3) {
                Log.i(LOG_TAG, "UnknownStatusException occur:" + e3.getMessage());
                Log.i(LOG_TAG, "wait 2 sec. and retry");
                sleep(UtStateMachine.HTTP_READ_TIMEOUT_GCF);
                nextWorkflow = getNextWorkflow(1);
                e3.printStackTrace();
                nextWorkflow2 = nextWorkflow;
            } catch (Exception e4) {
                if (e4.getMessage() != null) {
                    Log.i(LOG_TAG, "unknown exception occur:" + e4.getMessage());
                }
                Log.i(LOG_TAG, "wait 1 sec. and retry");
                sleep(1000L);
                nextWorkflow = getNextWorkflow(1);
                e4.printStackTrace();
                nextWorkflow2 = nextWorkflow;
            }
        }
    }

    @Override // com.sec.internal.ims.config.workflow.WorkflowBase
    protected void scheduleAutoconfig(int i) {
        IMSLog.i(LOG_TAG, this.mPhoneId, "Load config from the local file");
        this.mRcsProfile = this.mParamHandler.getRcsProfile(false);
        this.mRcsVersion = this.mParamHandler.getRcsVersion(false);
        this.mClientVersion = this.mParamHandler.getClientVersion(false);
        this.mClientVendor = this.mParamHandler.getClientVendor(false);
        work();
    }

    @Override // com.sec.internal.interfaces.ims.config.IWorkflow
    public void setRcsClientConfiguration(String str, String str2, String str3, String str4, String str5) {
        if (RcsUtils.isImsSingleRegiRequired(this.mContext, this.mPhoneId) && ConfigUtil.isGoogDmaPackageInuse(this.mContext, this.mPhoneId)) {
            Bundle bundle = new Bundle();
            bundle.putString("rcsVersion", str);
            bundle.putString("rcsProfile", str2);
            bundle.putString("clientVendor", str3);
            bundle.putString("clientVersion", str4);
            bundle.putString("rcsEnabledByUser", str5);
            sendMessage(obtainMessage(16, bundle));
        }
    }

    @Override // com.sec.internal.ims.config.workflow.WorkflowBase
    protected WorkflowBase.Workflow getNextWorkflow(int i) {
        if (i == 1) {
            return new WorkflowBase.Initialize() { // from class: com.sec.internal.ims.config.workflow.WorkflowLocalFile.1
                @Override // com.sec.internal.ims.config.workflow.WorkflowBase.Initialize, com.sec.internal.ims.config.workflow.WorkflowBase.Workflow
                public WorkflowBase.Workflow run() throws Exception {
                    WorkflowLocalFile.this.addEventLog(WorkflowLocalFile.LOG_TAG + "local filename: " + ConfigUtil.LOCAL_CONFIG_FILE);
                    WorkflowLocalFile workflowLocalFile = WorkflowLocalFile.this;
                    workflowLocalFile.mSharedInfo.setXml(ConfigUtil.getResourcesFromFile(workflowLocalFile.mContext, workflowLocalFile.mPhoneId, ConfigUtil.LOCAL_CONFIG_FILE, "utf-8"));
                    return WorkflowLocalFile.this.getNextWorkflow(6);
                }
            };
        }
        if (i == 6) {
            return new WorkflowBase.Parse() { // from class: com.sec.internal.ims.config.workflow.WorkflowLocalFile.2
                @Override // com.sec.internal.ims.config.workflow.WorkflowBase.Parse, com.sec.internal.ims.config.workflow.WorkflowBase.Workflow
                public WorkflowBase.Workflow run() throws Exception {
                    String matchedSalesCode;
                    if (SimUtil.isSoftphoneEnabled()) {
                        matchedSalesCode = Mno.ATT.getMatchedSalesCode(OmcCode.get());
                    } else {
                        matchedSalesCode = WorkflowLocalFile.this.mMno.getMatchedSalesCode("");
                    }
                    Log.i(WorkflowLocalFile.LOG_TAG, "salesCode from mno = " + matchedSalesCode);
                    String lowerCase = matchedSalesCode.toLowerCase(Locale.US);
                    if (ImsProfile.isRcsUpProfile(WorkflowLocalFile.this.mRcsProfile)) {
                        lowerCase = lowerCase + "_up";
                    }
                    Map<String, String> parseJson = WorkflowLocalFile.parseJson(WorkflowLocalFile.this.mSharedInfo.getXml(), lowerCase);
                    if (parseJson == null) {
                        throw new InvalidXmlException("no parsed xml data.");
                    }
                    if (parseJson.get("root/vers/version") == null || parseJson.get("root/vers/validity") == null) {
                        throw new InvalidXmlException("config xml must contain atleast 2 items(version & validity).");
                    }
                    WorkflowLocalFile.this.mParamHandler.parseParamForLocalFile(parseJson);
                    WorkflowLocalFile.this.mSharedInfo.setParsedXml(parseJson);
                    return WorkflowLocalFile.this.getNextWorkflow(7);
                }
            };
        }
        if (i == 7) {
            return new WorkflowBase.Store() { // from class: com.sec.internal.ims.config.workflow.WorkflowLocalFile.3
                @Override // com.sec.internal.ims.config.workflow.WorkflowBase.Store, com.sec.internal.ims.config.workflow.WorkflowBase.Workflow
                public WorkflowBase.Workflow run() throws Exception {
                    Map<String, String> parsedXml = WorkflowLocalFile.this.mSharedInfo.getParsedXml();
                    WorkflowParamHandler workflowParamHandler = WorkflowLocalFile.this.mParamHandler;
                    workflowParamHandler.setOpModeWithUserAccept(workflowParamHandler.getUserAccept(parsedXml), parsedXml, WorkflowBase.OpMode.DISABLE);
                    return WorkflowLocalFile.this.getNextWorkflow(8);
                }
            };
        }
        if (i != 8) {
            return null;
        }
        return new WorkflowBase.Finish() { // from class: com.sec.internal.ims.config.workflow.WorkflowLocalFile.4
            @Override // com.sec.internal.ims.config.workflow.WorkflowBase.Finish, com.sec.internal.ims.config.workflow.WorkflowBase.Workflow
            public WorkflowBase.Workflow run() throws Exception {
                WorkflowLocalFile workflowLocalFile = WorkflowLocalFile.this;
                workflowLocalFile.setLastErrorCode(workflowLocalFile.mLastErrorCodeNonRemote);
                IMSLog.i(WorkflowLocalFile.LOG_TAG, WorkflowLocalFile.this.mPhoneId, "all workflow finished");
                WorkflowLocalFile workflowLocalFile2 = WorkflowLocalFile.this;
                if (RcsUtils.isImsSingleRegiRequired(workflowLocalFile2.mContext, workflowLocalFile2.mPhoneId)) {
                    WorkflowLocalFile workflowLocalFile3 = WorkflowLocalFile.this;
                    if (ConfigUtil.isGoogDmaPackageInuse(workflowLocalFile3.mContext, workflowLocalFile3.mPhoneId) && !WorkflowLocalFile.this.mIsAcsSkipped) {
                        IMSLog.i(WorkflowLocalFile.LOG_TAG, WorkflowLocalFile.this.mPhoneId, "notifyRcsAutoConfigurationReceived: local file");
                        SecImsNotifier secImsNotifier = SecImsNotifier.getInstance();
                        WorkflowLocalFile workflowLocalFile4 = WorkflowLocalFile.this;
                        secImsNotifier.notifyRcsAutoConfigurationReceived(workflowLocalFile4.mPhoneId, workflowLocalFile4.mParamHandler.getProvisioningXml(false), false);
                    }
                }
                WorkflowLocalFile.this.mIsAcsSkipped = false;
                return null;
            }
        };
    }

    public static void path(JsonElement jsonElement, String str, Map<String, String> map) {
        if (!jsonElement.isJsonPrimitive()) {
            for (Map.Entry entry : jsonElement.getAsJsonObject().entrySet()) {
                path((JsonElement) entry.getValue(), str + "/" + ((String) entry.getKey()), map);
            }
            return;
        }
        map.put(str, jsonElement.getAsString());
    }

    public static Map<String, String> parseJson(String str, String str2) {
        if (str == null) {
            return null;
        }
        JsonParser jsonParser = new JsonParser();
        JsonReader jsonReader = new JsonReader(new BufferedReader(new StringReader(str)));
        JsonElement parse = jsonParser.parse(jsonReader);
        try {
            jsonReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JsonObject asJsonObject = parse.getAsJsonObject();
        JsonObject jsonObject = null;
        for (Map.Entry entry : asJsonObject.entrySet()) {
            String[] split = ((String) entry.getKey()).trim().split(",");
            int length = split.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                }
                if (split[i].equals(str2)) {
                    jsonObject = ((JsonElement) entry.getValue()).getAsJsonObject();
                    break;
                }
                i++;
            }
        }
        if (jsonObject == null) {
            return null;
        }
        TreeMap treeMap = new TreeMap();
        for (Map.Entry entry2 : asJsonObject.get("base").getAsJsonObject().entrySet()) {
            path((JsonElement) entry2.getValue(), "root/" + ((String) entry2.getKey()), treeMap);
        }
        for (Map.Entry entry3 : jsonObject.entrySet()) {
            path((JsonElement) entry3.getValue(), "root/" + ((String) entry3.getKey()), treeMap);
        }
        return treeMap;
    }
}
