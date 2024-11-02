package com.samsung.android.knox;

import android.app.admin.DeviceAdminInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemProperties;
import android.util.Printer;
import android.util.SparseArray;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.sec.ims.configuration.DATA;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes3.dex */
public final class EnterpriseDeviceAdminInfo implements Parcelable {
    public static final Parcelable.Creator<EnterpriseDeviceAdminInfo> CREATOR;
    public static final String TAG = "EnterpriseDeviceAdminInfo";
    public static final int USES_POLICY_KNOX_ADVANCED_APP_MGMT = 80;
    public static final String USES_POLICY_KNOX_ADVANCED_APP_MGMT_TAG = "com.samsung.android.knox.permission.KNOX_ADVANCED_APP_MGMT";
    public static final int USES_POLICY_KNOX_ADVANCED_SECURITY = 81;
    public static final String USES_POLICY_KNOX_ADVANCED_SECURITY_TAG = "com.samsung.android.knox.permission.KNOX_ADVANCED_SECURITY";
    public static final int USES_POLICY_KNOX_APP_SEPARATION = 112;
    public static final String USES_POLICY_KNOX_APP_SEPARATION_TAG = "com.samsung.android.knox.permission.KNOX_APP_SEPARATION";
    public static final int USES_POLICY_KNOX_AUTHENTICATION_MANAGER = 123;
    public static final String USES_POLICY_KNOX_AUTHENTICATION_MANAGER_TAG = "com.samsung.android.knox.permission.KNOX_AUTH_MGMT";
    public static final int USES_POLICY_KNOX_CAPTURE = 113;
    public static final int USES_POLICY_KNOX_CAPTURE_ADVANCED = 120;
    public static final String USES_POLICY_KNOX_CAPTURE_ADVANCED_TAG = "com.samsung.android.knox.permission.SMART_SCAN_ADVANCED";
    public static final int USES_POLICY_KNOX_CAPTURE_BASIC = 119;
    public static final String USES_POLICY_KNOX_CAPTURE_BASIC_TAG = "com.samsung.android.knox.permission.SMART_SCAN_BASIC";
    public static final String USES_POLICY_KNOX_CAPTURE_TAG = "com.samsung.android.knox.permission.SMART_SCAN";
    public static final int USES_POLICY_KNOX_CCM = 61;
    public static final String USES_POLICY_KNOX_CCM_TAG = "com.sec.enterprise.knox.permission.KNOX_CCM,com.samsung.android.knox.permission.KNOX_CCM_KEYSTORE";
    public static final int USES_POLICY_KNOX_CERTENROL = 66;
    public static final String USES_POLICY_KNOX_CERTENROL_TAG = "com.sec.enterprise.knox.permission.KNOX_CERTENROLL,com.samsung.android.knox.permission.KNOX_CERTIFICATE_ENROLLMENT";
    public static final int USES_POLICY_KNOX_CERT_PROVISIONING = 78;
    public static final String USES_POLICY_KNOX_CERT_PROVISIONING_TAG = "com.samsung.android.knox.permission.KNOX_CERT_PROVISIONING";
    public static final int USES_POLICY_KNOX_CLIPBOARD = 79;
    public static final String USES_POLICY_KNOX_CLIPBOARD_TAG = "com.samsung.android.knox.permission.KNOX_CLIPBOARD";
    public static final int USES_POLICY_KNOX_CONTAINER_VPN = 55;
    public static final String USES_POLICY_KNOX_CONTAINER_VPN_TAG = "com.samsung.android.knox.permission.KNOX_VPN_CONTAINER";
    public static final int USES_POLICY_KNOX_CRITICAL_COMMUNICATIONS = 108;
    public static final String USES_POLICY_KNOX_CRITICAL_COMMUNICATIONS_TAG = "com.samsung.android.knox.permission.KNOX_CRITICAL_COMMUNICATIONS";
    public static final int USES_POLICY_KNOX_CUSTOM_DEX = 85;
    public static final String USES_POLICY_KNOX_CUSTOM_DEX_TAG = "com.samsung.android.knox.permission.KNOX_CUSTOM_DEX";
    public static final int USES_POLICY_KNOX_CUSTOM_PROKIOSK = 70;
    public static final String USES_POLICY_KNOX_CUSTOM_PROKIOSK_TAG = "com.samsung.android.knox.permission.KNOX_CUSTOM_PROKIOSK";
    public static final int USES_POLICY_KNOX_CUSTOM_SEALEDMODE = 65;
    public static final String USES_POLICY_KNOX_CUSTOM_SEALEDMODE_TAG = "com.sec.enterprise.knox.permission.CUSTOM_SEALEDMODE,com.samsung.android.knox.permission.KNOX_CUSTOM_SEALEDMODE";
    public static final int USES_POLICY_KNOX_CUSTOM_SETTING = 63;
    public static final String USES_POLICY_KNOX_CUSTOM_SETTING_TAG = "com.sec.enterprise.knox.permission.CUSTOM_SETTING,com.samsung.android.knox.permission.KNOX_CUSTOM_SETTING";
    public static final int USES_POLICY_KNOX_CUSTOM_SYSTEM = 64;
    public static final String USES_POLICY_KNOX_CUSTOM_SYSTEM_TAG = "com.sec.enterprise.knox.permission.CUSTOM_SYSTEM,com.samsung.android.knox.permission.KNOX_CUSTOM_SYSTEM";
    public static final int USES_POLICY_KNOX_DEACTIVATE_LICENSE = 111;
    public static final String USES_POLICY_KNOX_DEACTIVATE_LICENSE_TAG = "com.sec.enterprise.knox.permission.KNOX_DEACTIVATE_LICENSE";
    public static final int USES_POLICY_KNOX_DEVICE_CONFIGURATION = 109;
    public static final String USES_POLICY_KNOX_DEVICE_CONFIGURATION_TAG = "com.samsung.android.knox.permission.KNOX_DEVICE_CONFIGURATION";
    public static final int USES_POLICY_KNOX_DEX = 84;
    public static final String USES_POLICY_KNOX_DEX_TAG = "com.samsung.android.knox.permission.KNOX_DEX";
    public static final int USES_POLICY_KNOX_DUAL_DAR = 87;
    public static final String USES_POLICY_KNOX_DUAL_DAR_TAG = "com.samsung.android.knox.permission.KNOX_DUAL_DAR";
    public static final int USES_POLICY_KNOX_EBILLING_NOMDM = 83;
    public static final String USES_POLICY_KNOX_EBILLING_NOMDM_TAG = "com.samsung.android.knox.permission.KNOX_EBILLING_NOMDM";
    public static final int USES_POLICY_KNOX_ENHANCED_ATTESTATION = 107;
    public static final String USES_POLICY_KNOX_ENHANCED_ATTESTATION_TAG = "com.samsung.android.knox.permission.KNOX_ENHANCED_ATTESTATION";
    public static final int USES_POLICY_KNOX_ENTERPRISE_BILLING = 68;
    public static final String USES_POLICY_KNOX_ENTERPRISE_BILLING_TAG = "com.samsung.android.knox.permission.KNOX_EBILLING";
    public static final int USES_POLICY_KNOX_FORESIGHT = 122;
    public static final String USES_POLICY_KNOX_FORESIGHT_TAG = "com.samsung.android.knox.permission.KNOX_FORESIGHT";
    public static final int USES_POLICY_KNOX_GENERIC_VPN = 54;
    public static final String USES_POLICY_KNOX_GENERIC_VPN_TAG = "com.samsung.android.knox.permission.KNOX_VPN_GENERIC";
    public static final int USES_POLICY_KNOX_HDM = 110;
    public static final String USES_POLICY_KNOX_HDM_TAG = "com.samsung.android.knox.permission.KNOX_HDM";
    public static final int USES_POLICY_KNOX_KEYSTORE = 62;
    public static final int USES_POLICY_KNOX_KEYSTORE_PER_APP = 75;
    public static final String USES_POLICY_KNOX_KEYSTORE_PER_APP_TAG = "com.samsung.android.knox.permission.KNOX_TIMA_KEYSTORE_PER_APP";
    public static final String USES_POLICY_KNOX_KEYSTORE_TAG = "com.sec.enterprise.knox.permission.KNOX_KEYSTORE,com.samsung.android.knox.permission.KNOX_TIMA_KEYSTORE";
    public static final int USES_POLICY_KNOX_MPOS = 121;
    public static final String USES_POLICY_KNOX_MPOS_TAG = "com.samsung.android.knox.permission.KNOX_MPOS";
    public static final int USES_POLICY_KNOX_NDA_AI = 118;
    public static final String USES_POLICY_KNOX_NDA_AI_TAG = "com.samsung.android.knox.permission.KNOX_NDA_AI";
    public static final int USES_POLICY_KNOX_NDA_DATA_ANALYTICS = 117;
    public static final String USES_POLICY_KNOX_NDA_DATA_ANALYTICS_TAG = "com.samsung.android.knox.permission.KNOX_NDA_DATA_ANALYTICS";
    public static final int USES_POLICY_KNOX_NDA_DEVICE_SETTINGS = 116;
    public static final String USES_POLICY_KNOX_NDA_DEVICE_SETTINGS_TAG = "com.samsung.android.knox.permission.KNOX_NDA_DEVICE_SETTINGS";
    public static final int USES_POLICY_KNOX_NDA_PERIPHERAL = 115;
    public static final String USES_POLICY_KNOX_NDA_PERIPHERAL_TAG = "com.samsung.android.knox.permission.KNOX_NDA_PERIPHERAL";
    public static final int USES_POLICY_KNOX_NETWORK_FILTER_MGMT = 124;
    public static final String USES_POLICY_KNOX_NETWORK_FILTER_MGMT_TAG = "com.samsung.android.knox.permission.KNOX_NETWORK_FILTER_MGMT";
    public static final int USES_POLICY_KNOX_NETWORK_FILTER_SP = 125;
    public static final String USES_POLICY_KNOX_NETWORK_FILTER_SP_TAG = "com.samsung.android.knox.permission.KNOX_NETWORK_FILTER_SERVICE_PROVIDER";
    public static final int USES_POLICY_KNOX_NPA = 82;
    public static final String USES_POLICY_KNOX_NPA_TAG = "com.samsung.android.knox.permission.KNOX_NPA";
    public static final int USES_POLICY_KNOX_RESTRICTION_PERM = 60;
    public static final String USES_POLICY_KNOX_RESTRICTION_PERM_TAG = "com.samsung.android.knox.permission.KNOX_ADVANCED_RESTRICTION";
    public static final int USES_POLICY_KNOX_SDP = 71;
    public static final String USES_POLICY_KNOX_SDP_TAG = "com.samsung.android.knox.permission.KNOX_SENSITIVE_DATA_PROTECTION";
    public static final int USES_POLICY_KNOX_SEAMS_PERM = 58;
    public static final String USES_POLICY_KNOX_SEAMS_PERM_TAG = "com.sec.enterprise.knox.permission.KNOX_SEAMS,com.samsung.android.knox.permission.KNOX_SEAMS_MGMT";
    public static final int USES_POLICY_KNOX_SEAMS_SEPOLICY = 114;
    public static final int USES_POLICY_KNOX_SEAMS_SEPOLICY_PERM = 59;
    public static final String USES_POLICY_KNOX_SEAMS_SEPOLICY_PERM_TAG = "com.samsung.android.knox.permission.KNOX_SEAMS_SEPOLICY_INTERNAL";
    public static final String USES_POLICY_KNOX_SEAMS_SEPOLICY_TAG = "com.sec.enterprise.knox.permission.KNOX_SEAMS_SEPOLICY";
    public static final int USES_POLICY_KNOX_SECURE_TIMER = 88;
    public static final String USES_POLICY_KNOX_SECURE_TIMER_TAG = "com.samsung.android.knox.permission.KNOX_SECURE_TIMER";
    public static final int USES_POLICY_KNOX_SIM_RESTRICTION = 89;
    public static final String USES_POLICY_KNOX_SIM_RESTRICTION_TAG = "com.samsung.android.knox.permission.KNOX_SIM_RESTRICTION";
    public static final int USES_POLICY_KNOX_UCM_MGMT = 86;
    public static final String USES_POLICY_KNOX_UCM_MGMT_TAG = "com.samsung.android.knox.permission.KNOX_UCM_MGMT";
    public static final int USES_POLICY_KNOX_UCM_PRIVILEGED = 76;
    public static final String USES_POLICY_KNOX_UCM_PRIVILEGED_TAG = "com.samsung.android.knox.permission.KNOX_UCM_PRIVILEGED_MGMT";
    public static final int USES_POLICY_KNOX_UCSM_ESE = 72;
    public static final String USES_POLICY_KNOX_UCSM_ESE_TAG = "com.samsung.android.knox.permission.KNOX_UCM_ESE_MGMT";
    public static final int USES_POLICY_KNOX_UCSM_OTHER = 73;
    public static final String USES_POLICY_KNOX_UCSM_OTHER_TAG = "com.samsung.android.knox.permission.KNOX_UCM_OTHER_MGMT";
    public static final int USES_POLICY_KNOX_UCS_PLUGIN = 74;
    public static final String USES_POLICY_KNOX_UCS_PLUGIN_TAG = "com.samsung.android.knox.permission.KNOX_UCM_PLUGIN_SERVICE";
    public static final int USES_POLICY_MDM_APN_SETTINGS = 34;
    public static final String USES_POLICY_MDM_APN_SETTINGS_TAG = "com.samsung.android.knox.permission.KNOX_APN";
    public static final int USES_POLICY_MDM_APPLICATION = 22;
    public static final int USES_POLICY_MDM_APPLICATION_PERMISSION = 90;
    public static final String USES_POLICY_MDM_APPLICATION_PERMISSION_TAG = "com.samsung.android.knox.permission.KNOX_APP_PERMISSION_MGMT";
    public static final String USES_POLICY_MDM_APPLICATION_TAG = "com.samsung.android.knox.permission.KNOX_APP_MGMT";
    public static final int USES_POLICY_MDM_AUDIT_LOG_PERMISSION = 43;
    public static final String USES_POLICY_MDM_AUDIT_LOG_PERMISSION_TAG = "com.samsung.android.knox.permission.KNOX_AUDIT_LOG";
    public static final int USES_POLICY_MDM_BLUETOOTH = 23;
    public static final int USES_POLICY_MDM_BLUETOOTH_SECURE_MODE = 51;
    public static final String USES_POLICY_MDM_BLUETOOTH_SECURE_MODE_TAG = "com.samsung.android.knox.permission.KNOX_BLUETOOTH_SECUREMODE";
    public static final String USES_POLICY_MDM_BLUETOOTH_TAG = "com.samsung.android.knox.permission.KNOX_BLUETOOTH";
    public static final int USES_POLICY_MDM_BROWSER_PROXY = 53;
    public static final String USES_POLICY_MDM_BROWSER_PROXY_TAG = "com.samsung.android.knox.permission.KNOX_BROWSER_PROXY";
    public static final int USES_POLICY_MDM_BROWSER_SETTINGS = 36;
    public static final String USES_POLICY_MDM_BROWSER_SETTINGS_TAG = "com.samsung.android.knox.permission.KNOX_BROWSER_SETTINGS";
    public static final int USES_POLICY_MDM_CERTIFICATE_PERMISSION = 42;
    public static final String USES_POLICY_MDM_CERTIFICATE_PERMISSION_TAG = "com.samsung.android.knox.permission.KNOX_CERTIFICATE";
    public static final int USES_POLICY_MDM_DATE_TIME = 37;
    public static final String USES_POLICY_MDM_DATE_TIME_TAG = "com.samsung.android.knox.permission.KNOX_DATE_TIME";
    public static final int USES_POLICY_MDM_DEVICE_INVENTORY = 24;
    public static final String USES_POLICY_MDM_DEVICE_INVENTORY_TAG = "com.samsung.android.knox.permission.KNOX_INVENTORY";
    public static final int USES_POLICY_MDM_DUAL_SIM = 47;
    public static final String USES_POLICY_MDM_DUAL_SIM_TAG = "com.samsung.android.knox.permission.KNOX_DUAL_SIM";
    public static final int USES_POLICY_MDM_EMAIL_ACCOUNT = 32;
    public static final String USES_POLICY_MDM_EMAIL_ACCOUNT_TAG = "com.samsung.android.knox.permission.KNOX_EMAIL";
    public static final int USES_POLICY_MDM_ENTERPRISE_CONTAINER = 48;
    public static final String USES_POLICY_MDM_ENTERPRISE_CONTAINER_TAG = "com.samsung.android.knox.permission.KNOX_CONTAINER";
    public static final int USES_POLICY_MDM_ENTERPRISE_DEVICE_ADMIN = 39;
    public static final String USES_POLICY_MDM_ENTERPRISE_DEVICE_ADMIN_TAG = "com.samsung.android.knox.permission.KNOX_ENTERPRISE_DEVICE_ADMIN";
    public static final int USES_POLICY_MDM_EXCHANGE_ACCOUNT = 25;
    public static final String USES_POLICY_MDM_EXCHANGE_ACCOUNT_TAG = "com.samsung.android.knox.permission.KNOX_EXCHANGE";
    public static final int USES_POLICY_MDM_FIREWALL = 38;
    public static final String USES_POLICY_MDM_FIREWALL_TAG = "com.samsung.android.knox.permission.KNOX_FIREWALL";
    public static final int USES_POLICY_MDM_GEOFENCING = 45;
    public static final String USES_POLICY_MDM_GEOFENCING_TAG = "com.samsung.android.knox.permission.KNOX_GEOFENCING";
    public static final int USES_POLICY_MDM_GLOBALPROXY = 77;
    public static final String USES_POLICY_MDM_GLOBALPROXY_TAG = "com.samsung.android.knox.permission.KNOX_GLOBALPROXY";
    public static final int USES_POLICY_MDM_HARDWARE_CONTROL = 29;
    public static final String USES_POLICY_MDM_HARDWARE_CONTROL_TAG = "com.samsung.android.knox.permission.KNOX_HW_CONTROL";
    public static final int USES_POLICY_MDM_KIOSK_MODE = 41;
    public static final String USES_POLICY_MDM_KIOSK_MODE_TAG = "com.samsung.android.knox.permission.KNOX_KIOSK_MODE";
    public static final int USES_POLICY_MDM_KNOX_ACTIVATE_DEVICE_PERMISSIONS = 56;
    public static final String USES_POLICY_MDM_KNOX_ACTIVATE_DEVICE_PERMISSIONS_TAG = "com.samsung.android.knox.permission.KNOX_ACTIVATE_DEVICE_PERMISSIONS_INTERNAL";
    public static final int USES_POLICY_MDM_KNOX_MOBILE_THREAT_DEFENSE = 106;
    public static final String USES_POLICY_MDM_KNOX_MOBILE_THREAT_DEFENSE_TAG = "com.samsung.android.knox.permission.KNOX_MOBILE_THREAT_DEFENSE";
    public static final int USES_POLICY_MDM_LDAP_SETTINGS = 44;
    public static final String USES_POLICY_MDM_LDAP_SETTINGS_TAG = "com.samsung.android.knox.permission.KNOX_LDAP";
    public static final int USES_POLICY_MDM_LICENSE_LOG = 49;
    public static final String USES_POLICY_MDM_LICENSE_LOG_TAG = "com.samsung.android.knox.permission.KNOX_LICENSE_LOG";
    public static final int USES_POLICY_MDM_LOCATION = 31;
    public static final String USES_POLICY_MDM_LOCATION_TAG = "com.samsung.android.knox.permission.KNOX_LOCATION";
    public static final int USES_POLICY_MDM_LOCKSCREEN = 46;
    public static final String USES_POLICY_MDM_LOCKSCREEN_TAG = "com.samsung.android.knox.permission.KNOX_LOCKSCREEN";
    public static final int USES_POLICY_MDM_MULTI_USER_MGMT = 50;
    public static final String USES_POLICY_MDM_MULTI_USER_MGMT_TAG = "com.samsung.android.knox.permission.KNOX_MULTI_USER_MGMT";
    public static final int USES_POLICY_MDM_PHONE_RESTRICTION = 35;
    public static final String USES_POLICY_MDM_PHONE_RESTRICTION_TAG = "com.samsung.android.knox.permission.KNOX_PHONE_RESTRICTION";
    public static final int USES_POLICY_MDM_RCP_SYNC_MGMT = 57;
    public static final String USES_POLICY_MDM_RCP_SYNC_MGMT_TAG = "com.sec.enterprise.knox.permission.KNOX_RCP_SYNC_MGMT,com.samsung.android.knox.permission.KNOX_CONTAINER_RCP";
    public static final int USES_POLICY_MDM_REMOTE_CONTROL = 40;
    public static final String USES_POLICY_MDM_REMOTE_CONTROL_TAG = "com.samsung.android.knox.permission.KNOX_REMOTE_CONTROL";
    public static final int USES_POLICY_MDM_RESTRICTION = 30;
    public static final String USES_POLICY_MDM_RESTRICTION_TAG = "com.samsung.android.knox.permission.KNOX_RESTRICTION_MGMT";
    public static final int USES_POLICY_MDM_ROAMING = 26;
    public static final String USES_POLICY_MDM_ROAMING_TAG = "com.samsung.android.knox.permission.KNOX_ROAMING";
    public static final int USES_POLICY_MDM_SECURITY = 28;
    public static final String USES_POLICY_MDM_SECURITY_TAG = "com.samsung.android.knox.permission.KNOX_SECURITY";
    public static final int USES_POLICY_MDM_SMARTCARD = 91;
    public static final String USES_POLICY_MDM_SMARTCARD_TAG = "com.samsung.android.knox.permission.KNOX_SMARTCARD";
    public static final int USES_POLICY_MDM_SSO = 67;
    public static final String USES_POLICY_MDM_SSO_TAG = "com.sec.enterprise.mdm.permission.MDM_SSO,com.samsung.android.knox.permission.KNOX_SSO";
    public static final int USES_POLICY_MDM_VPN = 33;
    public static final String USES_POLICY_MDM_VPN_TAG = "com.samsung.android.knox.permission.KNOX_VPN";
    public static final int USES_POLICY_MDM_WIFI = 27;
    public static final String USES_POLICY_MDM_WIFI_TAG = "com.samsung.android.knox.permission.KNOX_WIFI";
    public boolean mAuthorized;
    public DeviceAdminInfo mDeviceAdminInfo;
    public boolean mIsPseudoAdmin;
    public long mLicenseExpiryTime;
    public final ResolveInfo mReceiver;
    public List<String> mRequestedPermissions;
    public BitSet mUsesPolicies;
    public boolean mVisible;
    public static final boolean timaversion = "3.0".equals(SystemProperties.get("ro.config.timaversion", DATA.DM_FIELD_INDEX.PCSCF_DOMAIN));
    public static HashMap<String, String> sOldToNewPermissionMapping = new HashMap<>();
    public static HashMap<String, String> sNewToOldPermissionMapping = new HashMap<>();
    public static ArrayList<PolicyInfo> sPoliciesDisplayOrder = new ArrayList<>();
    public static HashMap<String, Integer> sKnownPolicies = new HashMap<>();
    public static SparseArray<PolicyInfo> sRevKnownPolicies = new SparseArray<>();

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes3.dex */
    public static class PolicyInfo {
        public final int description;
        public final int descriptionForSecondaryUsers;
        public final int ident;
        public final int label;
        public final int labelForSecondaryUsers;
        public final String tag;

        public PolicyInfo(int i, String str, int i2, int i3) {
            this(i, str, i2, i3, i2, i3);
        }

        public PolicyInfo(int i, String str, int i2, int i3, int i4, int i5) {
            this.ident = i;
            this.tag = str;
            this.label = i2;
            this.description = i3;
            this.labelForSecondaryUsers = i4;
            this.descriptionForSecondaryUsers = i5;
        }
    }

    static {
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(22, USES_POLICY_MDM_APPLICATION_TAG, 17042072, 17041753, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(23, USES_POLICY_MDM_BLUETOOTH_TAG, 17042075, 17041756, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(24, USES_POLICY_MDM_DEVICE_INVENTORY_TAG, 17042087, 17041768, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(25, USES_POLICY_MDM_EXCHANGE_ACCOUNT_TAG, 17042098, 17041779, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(26, USES_POLICY_MDM_ROAMING_TAG, 17042119, 17041804, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(27, USES_POLICY_MDM_WIFI_TAG, 17042137, 17041822, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(28, USES_POLICY_MDM_SECURITY_TAG, 17042126, 17041811, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(29, USES_POLICY_MDM_HARDWARE_CONTROL_TAG, 17042102, 17041783, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(30, USES_POLICY_MDM_RESTRICTION_TAG, 17042118, 17041803, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(31, USES_POLICY_MDM_LOCATION_TAG, 17042112, 17041797, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(32, USES_POLICY_MDM_EMAIL_ACCOUNT_TAG, 17042091, 17041772, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(33, USES_POLICY_MDM_VPN_TAG, 17042136, 17041821, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(34, USES_POLICY_MDM_APN_SETTINGS_TAG, 17042070, 17041751, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(35, USES_POLICY_MDM_PHONE_RESTRICTION_TAG, 17042114, 17041799, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(36, USES_POLICY_MDM_BROWSER_SETTINGS_TAG, 17042078, 17041759, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(53, USES_POLICY_MDM_BROWSER_PROXY_TAG, 17042077, 17041758, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(37, USES_POLICY_MDM_DATE_TIME_TAG, 17042085, 17041766, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(54, "com.samsung.android.knox.permission.KNOX_VPN_GENERIC", 17042056, 17041736, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(55, "com.samsung.android.knox.permission.KNOX_VPN_CONTAINER", 17042048, 17041728, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(38, USES_POLICY_MDM_FIREWALL_TAG, 17042099, 17041780, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(39, USES_POLICY_MDM_ENTERPRISE_DEVICE_ADMIN_TAG, 17042092, 17041773, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(40, USES_POLICY_MDM_REMOTE_CONTROL_TAG, 17042117, 17041802, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(41, USES_POLICY_MDM_KIOSK_MODE_TAG, 17042105, 17041786, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(42, USES_POLICY_MDM_CERTIFICATE_PERMISSION_TAG, 17042082, 17041763, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(43, USES_POLICY_MDM_AUDIT_LOG_PERMISSION_TAG, 17042074, 17041755, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(48, USES_POLICY_MDM_ENTERPRISE_CONTAINER_TAG, 17042093, 17041774, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(44, USES_POLICY_MDM_LDAP_SETTINGS_TAG, 17042110, 17041791, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(46, USES_POLICY_MDM_LOCKSCREEN_TAG, 17042111, 17041792, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(47, USES_POLICY_MDM_DUAL_SIM_TAG, 17042089, 17041770, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(67, USES_POLICY_MDM_SSO_TAG, 17042122, 17041807, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(45, USES_POLICY_MDM_GEOFENCING_TAG, 17042100, 17041781, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(49, USES_POLICY_MDM_LICENSE_LOG_TAG, 17042090, 17041771, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(50, USES_POLICY_MDM_MULTI_USER_MGMT_TAG, 17042113, 17041798, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(51, USES_POLICY_MDM_BLUETOOTH_SECURE_MODE_TAG, 17042076, 17041757, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(107, USES_POLICY_KNOX_ENHANCED_ATTESTATION_TAG, 17042012, 17041692, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(106, "com.samsung.android.knox.permission.KNOX_MOBILE_THREAT_DEFENSE", 17042130, 17041815, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(108, USES_POLICY_KNOX_CRITICAL_COMMUNICATIONS_TAG, 17042004, 17041684, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(57, USES_POLICY_MDM_RCP_SYNC_MGMT_TAG, 17042108, 17041789, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(56, USES_POLICY_MDM_KNOX_ACTIVATE_DEVICE_PERMISSIONS_TAG, 17042106, 17041787, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(111, USES_POLICY_KNOX_DEACTIVATE_LICENSE_TAG, 17042086, 17041767, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(58, USES_POLICY_KNOX_SEAMS_PERM_TAG, 17042124, 17041809, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(59, USES_POLICY_KNOX_SEAMS_SEPOLICY_PERM_TAG, 17042125, 17041810, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(60, USES_POLICY_KNOX_RESTRICTION_PERM_TAG, 17042109, 17041790, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(63, USES_POLICY_KNOX_CUSTOM_SETTING_TAG, 17042051, 17041731, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(64, USES_POLICY_KNOX_CUSTOM_SYSTEM_TAG, 17042052, 17041732, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(65, USES_POLICY_KNOX_CUSTOM_SEALEDMODE_TAG, 17042049, 17041729, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(70, USES_POLICY_KNOX_CUSTOM_PROKIOSK_TAG, 17042049, 17041729, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(68, USES_POLICY_KNOX_ENTERPRISE_BILLING_TAG, 17042013, 17041693, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(61, USES_POLICY_KNOX_CCM_TAG, 17042080, 17041761, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(72, USES_POLICY_KNOX_UCSM_ESE_TAG, 17042134, 17041819, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(73, USES_POLICY_KNOX_UCSM_OTHER_TAG, 17042135, 17041820, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(74, USES_POLICY_KNOX_UCS_PLUGIN_TAG, 17042216, 17041900, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(76, USES_POLICY_KNOX_UCM_PRIVILEGED_TAG, 17042133, 17041818, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(62, USES_POLICY_KNOX_KEYSTORE_TAG, 17042103, 17041784, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(75, USES_POLICY_KNOX_KEYSTORE_PER_APP_TAG, 17042104, 17041785, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(66, USES_POLICY_KNOX_CERTENROL_TAG, 17042123, 17041808, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(71, USES_POLICY_KNOX_SDP_TAG, 17042120, 17041805, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(77, USES_POLICY_MDM_GLOBALPROXY_TAG, 17042101, 17041782, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(78, USES_POLICY_KNOX_CERT_PROVISIONING_TAG, 17042081, 17041762, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(79, USES_POLICY_KNOX_CLIPBOARD_TAG, 17042083, 17041764, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(80, USES_POLICY_KNOX_ADVANCED_APP_MGMT_TAG, 17042044, 17041724, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(81, USES_POLICY_KNOX_ADVANCED_SECURITY_TAG, 17042045, 17041725, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(82, "com.samsung.android.knox.permission.KNOX_NPA", 17042059, 17041739, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(83, USES_POLICY_KNOX_EBILLING_NOMDM_TAG, 17042013, 17041693, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(84, USES_POLICY_KNOX_DEX_TAG, 17042088, 17041769, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(85, USES_POLICY_KNOX_CUSTOM_DEX_TAG, 17042052, 17041732, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(86, USES_POLICY_KNOX_UCM_MGMT_TAG, 17042215, 17041899, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(87, USES_POLICY_KNOX_DUAL_DAR_TAG, 17042054, 17041734, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(89, USES_POLICY_KNOX_SIM_RESTRICTION_TAG, 17042114, 17041799, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(90, USES_POLICY_MDM_APPLICATION_PERMISSION_TAG, 17042073, 17041754, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(91, USES_POLICY_MDM_SMARTCARD_TAG, 17042129, 17041814, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(110, USES_POLICY_KNOX_HDM_TAG, 17042057, 17041737, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(112, USES_POLICY_KNOX_APP_SEPARATION_TAG, 17042043, 17041723, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(113, USES_POLICY_KNOX_CAPTURE_TAG, 17042047, 17041727, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(122, USES_POLICY_KNOX_FORESIGHT_TAG, 17042055, 17041735, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(123, USES_POLICY_KNOX_AUTHENTICATION_MANAGER_TAG, 17042046, 17041726, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(114, USES_POLICY_KNOX_SEAMS_SEPOLICY_TAG, 17042125, 17041810, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(109, "com.samsung.android.knox.permission.KNOX_DEVICE_CONFIGURATION", 17042053, 17041733, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(115, USES_POLICY_KNOX_NDA_PERIPHERAL_TAG, 17042151, 17041836, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(116, USES_POLICY_KNOX_NDA_DEVICE_SETTINGS_TAG, 17042009, 17041689, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(117, USES_POLICY_KNOX_NDA_DATA_ANALYTICS_TAG, 17042008, 17041688, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(118, USES_POLICY_KNOX_NDA_AI_TAG, 17042063, 17041744, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(119, USES_POLICY_KNOX_CAPTURE_BASIC_TAG, 17042047, 17041727, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(120, USES_POLICY_KNOX_CAPTURE_ADVANCED_TAG, 17042047, 17041727, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(121, USES_POLICY_KNOX_MPOS_TAG, 17042058, 17041738, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(124, USES_POLICY_KNOX_NETWORK_FILTER_MGMT_TAG, 17042060, 17041740, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(125, USES_POLICY_KNOX_NETWORK_FILTER_SP_TAG, 17042060, 17041741, sPoliciesDisplayOrder);
        for (int i = 0; i < sPoliciesDisplayOrder.size(); i++) {
            PolicyInfo policyInfo = sPoliciesDisplayOrder.get(i);
            sRevKnownPolicies.put(policyInfo.ident, policyInfo);
            sKnownPolicies.put(policyInfo.tag, Integer.valueOf(policyInfo.ident));
            String[] split = policyInfo.tag.split(",");
            if (split != null && split.length == 2) {
                sOldToNewPermissionMapping.put(split[0], split[1]);
                sNewToOldPermissionMapping.put(split[1], split[0]);
            }
        }
        CREATOR = new Parcelable.Creator<EnterpriseDeviceAdminInfo>() { // from class: com.samsung.android.knox.EnterpriseDeviceAdminInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public final EnterpriseDeviceAdminInfo createFromParcel(Parcel parcel) {
                return new EnterpriseDeviceAdminInfo(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public final EnterpriseDeviceAdminInfo[] newArray(int i2) {
                return new EnterpriseDeviceAdminInfo[i2];
            }

            @Override // android.os.Parcelable.Creator
            public final EnterpriseDeviceAdminInfo createFromParcel(Parcel parcel) {
                return new EnterpriseDeviceAdminInfo(parcel);
            }

            @Override // android.os.Parcelable.Creator
            public final EnterpriseDeviceAdminInfo[] newArray(int i2) {
                return new EnterpriseDeviceAdminInfo[i2];
            }
        };
    }

    public EnterpriseDeviceAdminInfo(Context context, ResolveInfo resolveInfo) {
        this.mRequestedPermissions = new ArrayList();
        this.mDeviceAdminInfo = new DeviceAdminInfo(context, resolveInfo);
        this.mUsesPolicies = new BitSet();
        this.mReceiver = resolveInfo;
        if ("com.android.email".equals(resolveInfo.activityInfo.packageName)) {
            return;
        }
        parseRequestedPermissions(context);
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final void dump(Printer printer, String str) {
        printer.println(str + "Receiver:");
        this.mReceiver.dump(printer, str + "  ");
    }

    public final ActivityInfo getActivityInfo() {
        DeviceAdminInfo deviceAdminInfo = this.mDeviceAdminInfo;
        if (deviceAdminInfo != null) {
            return deviceAdminInfo.getActivityInfo();
        }
        return null;
    }

    public final ComponentName getComponent() {
        DeviceAdminInfo deviceAdminInfo = this.mDeviceAdminInfo;
        if (deviceAdminInfo != null) {
            return deviceAdminInfo.getComponent();
        }
        return null;
    }

    public final long getLicenseExpiry() {
        return this.mLicenseExpiryTime;
    }

    public final String getPackageName() {
        DeviceAdminInfo deviceAdminInfo = this.mDeviceAdminInfo;
        if (deviceAdminInfo != null) {
            return deviceAdminInfo.getPackageName();
        }
        return "NonExist";
    }

    public final ResolveInfo getReceiver() {
        return this.mReceiver;
    }

    public final String getReceiverName() {
        return this.mDeviceAdminInfo.getReceiverName();
    }

    public final List<String> getRequestedPermissions() {
        return this.mRequestedPermissions;
    }

    public final String getTagForPolicy(int i) {
        DeviceAdminInfo deviceAdminInfo = this.mDeviceAdminInfo;
        if (deviceAdminInfo == null) {
            return null;
        }
        if (i < 22) {
            return deviceAdminInfo.getTagForPolicy(i);
        }
        if (sRevKnownPolicies.get(i) == null) {
            return null;
        }
        return sRevKnownPolicies.get(i).tag;
    }

    public final ArrayList<PolicyInfo> getUsedPolicies() {
        ArrayList<PolicyInfo> arrayList = new ArrayList<>();
        ArrayList usedPolicies = this.mDeviceAdminInfo.getUsedPolicies();
        for (int i = 0; i < usedPolicies.size(); i++) {
            arrayList.add(new PolicyInfo(((DeviceAdminInfo.PolicyInfo) usedPolicies.get(i)).ident, ((DeviceAdminInfo.PolicyInfo) usedPolicies.get(i)).tag, ((DeviceAdminInfo.PolicyInfo) usedPolicies.get(i)).label, ((DeviceAdminInfo.PolicyInfo) usedPolicies.get(i)).description));
        }
        for (int i2 = 0; i2 < sPoliciesDisplayOrder.size(); i2++) {
            PolicyInfo policyInfo = sPoliciesDisplayOrder.get(i2);
            if (usesPolicy(policyInfo.ident)) {
                arrayList.add(policyInfo);
            }
        }
        return arrayList;
    }

    public final boolean isAuthorized() {
        return this.mAuthorized;
    }

    public final boolean isProxy() {
        return false;
    }

    public final boolean isPseudo() {
        return this.mIsPseudoAdmin;
    }

    public final boolean isVisible() {
        return this.mDeviceAdminInfo.isVisible();
    }

    public final CharSequence loadDescription(PackageManager packageManager) {
        return this.mDeviceAdminInfo.loadDescription(packageManager);
    }

    public final Drawable loadIcon(PackageManager packageManager) {
        return this.mDeviceAdminInfo.loadIcon(packageManager);
    }

    public final CharSequence loadLabel(PackageManager packageManager) {
        return this.mDeviceAdminInfo.loadLabel(packageManager);
    }

    /* JADX WARN: Code restructure failed: missing block: B:118:0x0119, code lost:
    
        if (r9 == null) goto L68;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x010c, code lost:
    
        if (r9 != null) goto L67;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x011e, code lost:
    
        r2.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0121, code lost:
    
        if (r5 == null) goto L71;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0123, code lost:
    
        r5.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0126, code lost:
    
        r2 = com.samsung.android.knox.license.EnterpriseLicenseManager.getInstance(null);
        r14 = r14.getPackageManager();
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x012e, code lost:
    
        r2 = r2.getELMPermissions(r13.mDeviceAdminInfo.getPackageName());
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x0138, code lost:
    
        if (r2 == null) goto L100;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x013a, code lost:
    
        r2 = r2.iterator();
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x0142, code lost:
    
        if (r2.hasNext() == false) goto L131;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x0144, code lost:
    
        r3 = r2.next();
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x0150, code lost:
    
        if (com.samsung.android.knox.EnterpriseDeviceAdminInfo.sOldToNewPermissionMapping.containsKey(r3) == false) goto L80;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x0152, code lost:
    
        r4 = r3 + "," + com.samsung.android.knox.EnterpriseDeviceAdminInfo.sOldToNewPermissionMapping.get(r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x0191, code lost:
    
        r5 = com.samsung.android.knox.EnterpriseDeviceAdminInfo.sKnownPolicies.get(r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x0199, code lost:
    
        if (r5 == null) goto L136;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x019b, code lost:
    
        r8 = r4.split(",");
        r9 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x01a1, code lost:
    
        if (r9 >= r8.length) goto L140;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x01af, code lost:
    
        if (r14.checkPermission(r8[r9], r13.mDeviceAdminInfo.getPackageName()) != 0) goto L92;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x01b3, code lost:
    
        r9 = r9 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x01b1, code lost:
    
        r8 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x01b7, code lost:
    
        if (r8 == false) goto L137;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x01b9, code lost:
    
        android.util.Log.i(com.samsung.android.knox.EnterpriseDeviceAdminInfo.TAG, "Add Granted permission : " + r4);
        r13.mUsesPolicies.set(r5.intValue());
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x01dc, code lost:
    
        if (r13.mRequestedPermissions.contains(r3) != false) goto L138;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x01de, code lost:
    
        r13.mRequestedPermissions.add(r3.intern());
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x01b6, code lost:
    
        r8 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x0173, code lost:
    
        if (com.samsung.android.knox.EnterpriseDeviceAdminInfo.sNewToOldPermissionMapping.containsKey(r3) == false) goto L83;
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x0175, code lost:
    
        r4 = com.samsung.android.knox.EnterpriseDeviceAdminInfo.sNewToOldPermissionMapping.get(r3) + "," + r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x0190, code lost:
    
        r4 = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x01e9, code lost:
    
        android.util.Log.e(com.samsung.android.knox.EnterpriseDeviceAdminInfo.TAG, "Failed to get ELM permissions");
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x011b, code lost:
    
        r9.recycle();
     */
    /* JADX WARN: Not initialized variable reg: 9, insn: 0x01f2: MOVE (r4 I:??[OBJECT, ARRAY]) = (r9 I:??[OBJECT, ARRAY]) (LINE:499), block:B:122:0x01f2 */
    /* JADX WARN: Removed duplicated region for block: B:10:0x0049  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x01f5  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x004f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.util.List<java.lang.String> parseRequestedPermissions(android.content.Context r14) {
        /*
            Method dump skipped, instructions count: 505
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.knox.EnterpriseDeviceAdminInfo.parseRequestedPermissions(android.content.Context):java.util.List");
    }

    public final BitSet readBitSet(Parcel parcel) {
        int readInt = parcel.readInt();
        BitSet bitSet = new BitSet();
        for (int i = 0; i < readInt; i++) {
            bitSet.set(parcel.readInt());
        }
        return bitSet;
    }

    public final void readPoliciesFromXml(TypedXmlPullParser typedXmlPullParser) {
        this.mDeviceAdminInfo.readPoliciesFromXml(typedXmlPullParser);
    }

    public final void setAuthorized(boolean z) {
        this.mAuthorized = z;
    }

    public final void setLicenseExpiry(long j) {
        this.mLicenseExpiryTime = j;
    }

    public final String toString() {
        if (this.mReceiver != null) {
            return AbstractResolvableFuture$$ExternalSyntheticOutline0.m(new StringBuilder("DeviceAdminInfo{"), this.mReceiver.activityInfo.name, "}");
        }
        return "";
    }

    public final boolean usesMDMPolicy() {
        BitSet bitSet = this.mUsesPolicies;
        if (bitSet != null && !bitSet.isEmpty()) {
            return true;
        }
        return false;
    }

    public final boolean usesPolicy(int i) {
        DeviceAdminInfo deviceAdminInfo = this.mDeviceAdminInfo;
        if (deviceAdminInfo == null) {
            return false;
        }
        if (deviceAdminInfo.usesPolicy(i)) {
            return true;
        }
        return this.mUsesPolicies.get(i);
    }

    public final void writeBitSet(Parcel parcel, BitSet bitSet) {
        parcel.writeInt(bitSet.cardinality());
        int i = -1;
        while (true) {
            i = bitSet.nextSetBit(i + 1);
            if (i != -1) {
                parcel.writeInt(i);
            } else {
                return;
            }
        }
    }

    public final void writePoliciesToXml(TypedXmlSerializer typedXmlSerializer) {
        this.mDeviceAdminInfo.writePoliciesToXml(typedXmlSerializer);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        this.mReceiver.writeToParcel(parcel, i);
        writeBitSet(parcel, this.mUsesPolicies);
        parcel.writeInt(this.mIsPseudoAdmin ? 1 : 0);
    }

    public EnterpriseDeviceAdminInfo(Parcel parcel) {
        this.mRequestedPermissions = new ArrayList();
        this.mReceiver = (ResolveInfo) ResolveInfo.CREATOR.createFromParcel(parcel);
        this.mUsesPolicies = readBitSet(parcel);
        this.mIsPseudoAdmin = parcel.readInt() == 1;
    }

    public EnterpriseDeviceAdminInfo(boolean z) {
        this.mRequestedPermissions = new ArrayList();
        this.mIsPseudoAdmin = z;
        this.mReceiver = null;
    }
}
