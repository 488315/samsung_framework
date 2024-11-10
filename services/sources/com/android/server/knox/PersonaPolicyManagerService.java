package com.android.server.knox;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Environment;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.util.Log;
import android.util.SparseArray;
import com.android.internal.util.FastXmlSerializer;
import com.android.internal.util.JournaledFile;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.enterprise.container.KnoxMUMRCPPolicyService;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.container.KnoxConfigurationType;
import com.samsung.android.knox.container.KnoxContainerManager;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes2.dex */
public class PersonaPolicyManagerService {
    public static volatile PersonaPolicyManagerService mPersonaPolicyManagerService;
    public static Context sContext;
    public Context mContext;
    public final SparseArray mPersonaData = new SparseArray();
    public BroadcastReceiver mReceiver = new BroadcastReceiver() { // from class: com.android.server.knox.PersonaPolicyManagerService.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            int intExtra = intent.getIntExtra("android.intent.extra.user_handle", getSendingUserId());
            if ("android.intent.action.USER_REMOVED".equals(action) && SemPersonaManager.isKnoxId(intExtra)) {
                PersonaPolicyManagerService.this.removePersonaData(intExtra);
            }
        }
    };

    public final String getDefaultRCPPolicy(boolean z, String str) {
        return z ? "false" : "true";
    }

    public static int checkCallerPermissionFor(String str) {
        ContainerDependencyWrapper.checkCallerPermissionFor(sContext, "PersonaPolicyManagerService", str);
        return 0;
    }

    /* loaded from: classes2.dex */
    public class PersonaPolicyData {
        public HashMap mSecureFolderPolicies;
        public String mPersonalModeLabel = null;
        public String mCustomPersonaName = null;

        public PersonaPolicyData() {
            this.mSecureFolderPolicies = null;
            this.mSecureFolderPolicies = new HashMap();
        }
    }

    public PersonaPolicyManagerService(Context context) {
        this.mContext = context;
        sContext = context;
    }

    public static PersonaPolicyManagerService getInstance(Context context) {
        if (mPersonaPolicyManagerService == null) {
            synchronized (PersonaPolicyManagerService.class) {
                if (mPersonaPolicyManagerService == null) {
                    mPersonaPolicyManagerService = new PersonaPolicyManagerService(context);
                }
            }
        }
        return mPersonaPolicyManagerService;
    }

    public PersonaPolicyData getPersonaData(int i) {
        PersonaPolicyData personaPolicyData;
        synchronized (this) {
            personaPolicyData = (PersonaPolicyData) this.mPersonaData.get(i);
            if (personaPolicyData == null) {
                personaPolicyData = new PersonaPolicyData();
                this.mPersonaData.append(i, personaPolicyData);
                loadSettingsLocked(personaPolicyData, i);
            }
        }
        return personaPolicyData;
    }

    public boolean setCustomNamePersonalMode(int i, String str) {
        checkCallerPermissionFor("setAllowCustomBadgeIcon");
        getPersonaData(0).mPersonalModeLabel = str;
        saveSettingsLocked(0);
        return true;
    }

    public String getCustomNamePersonalMode(int i) {
        String str;
        synchronized (this) {
            str = getPersonaData(0).mPersonalModeLabel;
        }
        return str;
    }

    public boolean setCustomNamePersona(int i, String str) {
        checkCallerPermissionFor("setAllowCustomBadgeIcon");
        getPersonaData(i).mCustomPersonaName = str;
        saveSettingsLocked(i);
        return true;
    }

    public String getCustomNamePersona(int i) {
        String str;
        synchronized (this) {
            str = getPersonaData(i).mCustomPersonaName;
        }
        return str;
    }

    public List getSecureFolderPolicy(String str, int i) {
        List list;
        synchronized (this) {
            list = (List) getPersonaData(i).mSecureFolderPolicies.get(str);
        }
        return list;
    }

    public boolean setSecureFolderPolicy(String str, List list, int i) {
        synchronized (this) {
            getPersonaData(i).mSecureFolderPolicies.put(str, list);
            saveSettingsLocked(i);
        }
        if (list == null || !"DisallowPackage".equals(str)) {
            return true;
        }
        ContainerDependencyWrapper.setApplicationBlackList(sContext, list, i);
        return true;
    }

    public static JournaledFile makeJournaledFile(int i) {
        String absolutePath = new File(Environment.getUserSystemDirectory(i), "persona_policies.xml").getAbsolutePath();
        return new JournaledFile(new File(absolutePath), new File(absolutePath + ".tmp"));
    }

    /* JADX WARN: Removed duplicated region for block: B:58:0x0156 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:65:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void loadSettingsLocked(com.android.server.knox.PersonaPolicyManagerService.PersonaPolicyData r11, int r12) {
        /*
            Method dump skipped, instructions count: 346
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.knox.PersonaPolicyManagerService.loadSettingsLocked(com.android.server.knox.PersonaPolicyManagerService$PersonaPolicyData, int):void");
    }

    public final void saveSettingsLocked(int i) {
        PersonaPolicyData personaData = getPersonaData(i);
        JournaledFile makeJournaledFile = makeJournaledFile(i);
        FileOutputStream fileOutputStream = null;
        try {
            FileOutputStream fileOutputStream2 = new FileOutputStream(makeJournaledFile.chooseForWrite(), false);
            try {
                FastXmlSerializer fastXmlSerializer = new FastXmlSerializer();
                fastXmlSerializer.setOutput(fileOutputStream2, "UTF-8");
                fastXmlSerializer.startDocument("UTF-8", Boolean.TRUE);
                fastXmlSerializer.startTag(null, "policies");
                HashMap hashMap = personaData.mSecureFolderPolicies;
                if (hashMap != null && !hashMap.isEmpty()) {
                    fastXmlSerializer.startTag(null, "managed-applications");
                    fastXmlSerializer.startTag(null, "secure-folder");
                    for (String str : personaData.mSecureFolderPolicies.keySet()) {
                        for (String str2 : (List) personaData.mSecureFolderPolicies.get(str)) {
                            fastXmlSerializer.startTag(null, str);
                            fastXmlSerializer.attribute(null, "name", str2);
                            fastXmlSerializer.endTag(null, str);
                        }
                    }
                    fastXmlSerializer.endTag(null, "secure-folder");
                    fastXmlSerializer.endTag(null, "managed-applications");
                }
                fastXmlSerializer.endTag(null, "policies");
                fastXmlSerializer.endDocument();
                fileOutputStream2.close();
                makeJournaledFile.commit();
            } catch (IOException unused) {
                fileOutputStream = fileOutputStream2;
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                    } catch (IOException unused2) {
                    }
                }
                makeJournaledFile.rollback();
            }
        } catch (IOException unused3) {
        }
    }

    public final void removePersonaData(int i) {
        synchronized (this) {
            if (getPersonaData(i) != null) {
                this.mPersonaData.remove(i);
            }
            File file = new File(Environment.getUserSystemDirectory(i), "persona_policies.xml");
            file.delete();
            Log.d("PersonaPolicyManagerService", "Removed persona policy file " + file.getAbsolutePath());
        }
    }

    public final void enforceSystemServiceOrSystemUI(int i) {
        int i2;
        if (Binder.getCallingUid() != 1000) {
            try {
                i2 = this.mContext.getPackageManager().getPackageUid("com.android.systemui", 0);
            } catch (PackageManager.NameNotFoundException e) {
                Log.e("PersonaPolicyManagerService", "Unable to resolve SystemUI's UID.", e);
                i2 = -1;
            }
            if (UserHandle.getAppId(i) != i2) {
                throw new SecurityException("Only system can call this API. Are you Process.SYSTEM_UID!!");
            }
        }
    }

    public final String getDataSyncPolicy(int i, String str, String str2) {
        KnoxConfigurationType configurationType;
        boolean isKnoxId = SemPersonaManager.isKnoxId(i);
        String defaultRCPPolicy = getDefaultRCPPolicy(isKnoxId, str2);
        if (isKnoxId && (configurationType = KnoxContainerManager.getConfigurationType(i)) != null) {
            String dataSyncPolicy = configurationType.getDataSyncPolicy(str, str2);
            Log.d("PersonaPolicyManagerService", "configuration value set by MDM : " + dataSyncPolicy);
            if (dataSyncPolicy != null) {
                return dataSyncPolicy;
            }
        }
        return defaultRCPPolicy;
    }

    public String getRCPDataPolicyForUser(int i, String str, String str2) {
        checkCallerPermissionFor("getRCPDataPolicyForUser");
        enforceSystemServiceOrSystemUI(Binder.getCallingUid());
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return getDataSyncPolicy(i, str, str2);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public String getRCPDataPolicy(String str, String str2) {
        checkCallerPermissionFor("getRCPDataPolicy");
        int userId = UserHandle.getUserId(Binder.getCallingUid());
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return getDataSyncPolicy(userId, str, str2);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void sendRCPPolicyChangedBroadcast(int i) {
        try {
            KnoxMUMRCPPolicyService knoxMUMRCPPolicyService = (KnoxMUMRCPPolicyService) ServiceManager.getService("mum_container_rcp_policy");
            if (knoxMUMRCPPolicyService != null) {
                knoxMUMRCPPolicyService.sendRCPPolicyChangedBroadcast(i);
            }
        } catch (Exception e) {
            Log.e("PersonaPolicyManagerService", "Exception: " + e.getMessage());
        }
    }

    public boolean setRCPDataPolicy(String str, String str2, String str3) {
        int userId;
        checkCallerPermissionFor("setRCPDataPolicy");
        if (str3.startsWith("EDM")) {
            String[] split = str3.split(XmlUtils.STRING_ARRAY_SEPARATOR);
            userId = Integer.parseInt(split[1]);
            str3 = split[2];
        } else {
            userId = UserHandle.getUserId(Binder.getCallingUid());
        }
        boolean rCPDataPolicyForUser = setRCPDataPolicyForUser(userId, str, str2, str3);
        if (rCPDataPolicyForUser) {
            sendRCPPolicyChangedBroadcast(userId);
        }
        return rCPDataPolicyForUser;
    }

    public final boolean setRCPDataPolicyForUser(int i, String str, String str2, String str3) {
        synchronized (this) {
            if (SemPersonaManager.isKnoxId(i)) {
                return getPersonaData(i) != null;
            }
            return false;
        }
    }
}
