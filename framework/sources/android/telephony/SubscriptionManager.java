package android.telephony;

import android.annotation.SystemApi;
import android.app.PendingIntent;
import android.app.PropertyInvalidatedCache;
import android.compat.Compatibility;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.net.NetworkPolicyManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelUuid;
import android.os.Process;
import android.os.RemoteException;
import android.os.UserHandle;
import android.provider.Telephony;
import android.telephony.SubscriptionManager;
import android.telephony.euicc.EuiccManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.util.LruCache;
import android.util.Pair;
import com.android.internal.telephony.ISetOpportunisticDataCallback;
import com.android.internal.telephony.ISub;
import com.android.internal.telephony.PhoneConstants;
import com.android.internal.telephony.util.HandlerExecutor;
import com.android.internal.util.FunctionalUtils;
import com.android.internal.util.Preconditions;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

/* loaded from: classes4.dex */
public class SubscriptionManager {
    public static final String ACCESS_RULES = "access_rules";
    public static final String ACCESS_RULES_FROM_CARRIER_CONFIGS = "access_rules_from_carrier_configs";
    public static final String ACTION_DEFAULT_SMS_SUBSCRIPTION_CHANGED = "android.telephony.action.DEFAULT_SMS_SUBSCRIPTION_CHANGED";
    public static final String ACTION_DEFAULT_SUBSCRIPTION_CHANGED = "android.telephony.action.DEFAULT_SUBSCRIPTION_CHANGED";
    public static final String ACTION_MANAGE_SUBSCRIPTION_PLANS = "android.telephony.action.MANAGE_SUBSCRIPTION_PLANS";
    public static final String ACTION_REFRESH_SUBSCRIPTION_PLANS = "android.telephony.action.REFRESH_SUBSCRIPTION_PLANS";

    @SystemApi
    public static final String ACTION_SUBSCRIPTION_PLANS_CHANGED = "android.telephony.action.SUBSCRIPTION_PLANS_CHANGED";
    public static final String ALLOWED_NETWORK_TYPES = "allowed_network_types_for_reasons";
    public static final String CARD_ID = "card_id";
    public static final String CARRIER_ID = "carrier_id";
    public static final String CARRIER_NAME = "carrier_name";
    public static final String CB_ALERT_REMINDER_INTERVAL = "alert_reminder_interval";
    public static final String CB_ALERT_SOUND_DURATION = "alert_sound_duration";
    public static final String CB_ALERT_SPEECH = "enable_alert_speech";
    public static final String CB_ALERT_VIBRATE = "enable_alert_vibrate";
    public static final String CB_AMBER_ALERT = "enable_cmas_amber_alerts";
    public static final String CB_CHANNEL_50_ALERT = "enable_channel_50_alerts";
    public static final String CB_CMAS_TEST_ALERT = "enable_cmas_test_alerts";
    public static final String CB_EMERGENCY_ALERT = "enable_emergency_alerts";
    public static final String CB_ETWS_TEST_ALERT = "enable_etws_test_alerts";
    public static final String CB_EXTREME_THREAT_ALERT = "enable_cmas_extreme_threat_alerts";
    public static final String CB_OPT_OUT_DIALOG = "show_cmas_opt_out_dialog";
    public static final String CB_SEVERE_THREAT_ALERT = "enable_cmas_severe_threat_alerts";
    public static final String CROSS_SIM_CALLING_ENABLED = "cross_sim_calling_enabled";
    public static final int D2D_SHARING_ALL = 3;
    public static final int D2D_SHARING_ALL_CONTACTS = 1;
    public static final int D2D_SHARING_DISABLED = 0;
    public static final int D2D_SHARING_SELECTED_CONTACTS = 2;
    public static final String D2D_STATUS_SHARING = "d2d_sharing_status";
    public static final String D2D_STATUS_SHARING_SELECTED_CONTACTS = "d2d_sharing_contacts";
    public static final String DATA_ROAMING = "data_roaming";
    public static final int DATA_ROAMING_DISABLE = 0;
    public static final int DATA_ROAMING_ENABLE = 1;
    private static final boolean DBG = false;
    public static final int DEFAULT_NAME_RES = 17039374;
    public static final int DEFAULT_PHONE_INDEX = Integer.MAX_VALUE;
    public static final int DEFAULT_SIM_SLOT_INDEX = Integer.MAX_VALUE;
    public static final int DEFAULT_SUBSCRIPTION_ID = Integer.MAX_VALUE;
    public static final String DISPLAY_NAME = "display_name";
    public static final String EHPLMNS = "ehplmns";
    public static final String ENABLED_MOBILE_DATA_POLICIES = "enabled_mobile_data_policies";
    public static final String ENHANCED_4G_MODE_ENABLED = "volte_vt_enabled";
    public static final String EXTRA_SLOT_INDEX = "android.telephony.extra.SLOT_INDEX";
    public static final String EXTRA_SUBSCRIPTION_INDEX = "android.telephony.extra.SUBSCRIPTION_INDEX";
    public static final String GET_SIM_SPECIFIC_SETTINGS_METHOD_NAME = "getSimSpecificSettings";
    public static final String GROUP_OWNER = "group_owner";
    public static final String GROUP_UUID = "group_uuid";
    public static final String HPLMNS = "hplmns";
    public static final String HUE = "color";
    public static final String ICC_ID = "icc_id";
    public static final String IMSI = "imsi";
    public static final String IMS_RCS_UCE_ENABLED = "ims_rcs_uce_enabled";
    public static final int INVALID_PHONE_INDEX = -1;
    public static final int INVALID_SIM_SLOT_INDEX = -1;
    public static final int INVALID_SUBSCRIPTION_ID = -1;
    public static final String ISO_COUNTRY_CODE = "iso_country_code";
    public static final String IS_EMBEDDED = "is_embedded";
    public static final String IS_ONLY_NTN = "is_only_ntn";
    public static final String IS_OPPORTUNISTIC = "is_opportunistic";
    public static final String IS_REMOVABLE = "is_removable";
    public static final String IS_SATELLITE_PROVISIONED_FOR_NON_IP_DATAGRAM = "is_satellite_provisioned_for_non_ip_datagram";
    public static final String KEY_SIM_SPECIFIC_SETTINGS_DATA = "KEY_SIM_SPECIFIC_SETTINGS_DATA";
    private static final String LOG_TAG = "SubscriptionManager";
    private static final int MAX_CACHE_SIZE = 4;
    private static final int MAX_RESOURCE_CACHE_ENTRY_COUNT = 1000;
    public static final int MAX_SUBSCRIPTION_ID_VALUE = 2147483646;
    public static final String MCC = "mcc";
    public static final String MCC_STRING = "mcc_string";
    public static final int MIN_SUBSCRIPTION_ID_VALUE = 0;
    public static final String MNC = "mnc";
    public static final String MNC_STRING = "mnc_string";
    public static final String NAME_SOURCE = "name_source";
    public static final int NAME_SOURCE_CARRIER = 3;
    public static final int NAME_SOURCE_CARRIER_ID = 0;
    public static final int NAME_SOURCE_SIM_PNN = 4;
    public static final int NAME_SOURCE_SIM_SPN = 1;
    public static final int NAME_SOURCE_UNKNOWN = -1;
    public static final int NAME_SOURCE_USER_INPUT = 2;
    public static final String NR_ADVANCED_CALLING_ENABLED = "nr_advanced_calling_enabled";
    public static final String NUMBER = "number";
    public static final int PHONE_NUMBER_SOURCE_CARRIER = 2;
    public static final int PHONE_NUMBER_SOURCE_IMS = 3;
    public static final int PHONE_NUMBER_SOURCE_UICC = 1;
    public static final int PLACEHOLDER_SUBSCRIPTION_ID_BASE = -2;
    public static final String PORT_INDEX = "port_index";
    public static final String PROFILE_CLASS = "profile_class";

    @SystemApi
    @Deprecated
    public static final int PROFILE_CLASS_DEFAULT = -1;

    @SystemApi
    public static final int PROFILE_CLASS_OPERATIONAL = 2;

    @SystemApi
    public static final int PROFILE_CLASS_PROVISIONING = 1;

    @SystemApi
    public static final int PROFILE_CLASS_TESTING = 0;

    @SystemApi
    public static final int PROFILE_CLASS_UNSET = -1;
    public static final String RESTORE_SIM_SPECIFIC_SETTINGS_DATABASE_UPDATED = "restoreSimSpecificSettingsDatabaseUpdated";
    public static final String RESTORE_SIM_SPECIFIC_SETTINGS_METHOD_NAME = "restoreSimSpecificSettings";
    public static final String SATELLITE_ATTACH_ENABLED_FOR_CARRIER = "satellite_attach_enabled_for_carrier";
    public static final String SATELLITE_ENABLED = "satellite_enabled";
    public static final String SATELLITE_ENTITLEMENT_PLMNS = "satellite_entitlement_plmns";
    public static final String SATELLITE_ENTITLEMENT_STATUS = "satellite_entitlement_status";
    public static final String SATELLITE_ESOS_SUPPORTED = "satellite_esos_supported";
    public static final int SEM_PROFILE_CLASS_OPERATIONAL = 2;
    public static final int SEM_PROFILE_CLASS_PROVISIONING = 1;
    public static final int SEM_PROFILE_CLASS_TESTING = 0;
    public static final int SEM_PROFILE_CLASS_UNSET = -1;
    public static final String SERVICE_CAPABILITIES = "service_capabilities";
    public static final int SERVICE_CAPABILITY_DATA = 3;
    public static final int SERVICE_CAPABILITY_MAX = 3;
    public static final int SERVICE_CAPABILITY_SMS = 2;
    public static final int SERVICE_CAPABILITY_VOICE = 1;
    public static final int SIM_NOT_INSERTED = -1;
    public static final String SIM_SLOT_INDEX = "sim_id";
    public static final int SLOT_INDEX_FOR_REMOTE_SIM_SUB = -1;
    public static final String SUBSCRIPTION_TYPE = "subscription_type";
    public static final int SUBSCRIPTION_TYPE_LOCAL_SIM = 0;
    public static final int SUBSCRIPTION_TYPE_REMOTE_SIM = 1;
    public static final String SUB_DEFAULT_CHANGED_ACTION = "android.intent.action.SUB_DEFAULT_CHANGED";
    public static final String TP_MESSAGE_REF = "tp_message_ref";
    public static final String TRANSFER_STATUS = "transfer_status";

    @SystemApi
    public static final int TRANSFER_STATUS_CONVERTED = 2;

    @SystemApi
    public static final int TRANSFER_STATUS_NONE = 0;

    @SystemApi
    public static final int TRANSFER_STATUS_TRANSFERRED_OUT = 1;
    public static final String UICC_APPLICATIONS_ENABLED = "uicc_applications_enabled";
    public static final String UNIQUE_KEY_SUBSCRIPTION_ID = "_id";
    public static final String USAGE_SETTING = "usage_setting";
    public static final int USAGE_SETTING_DATA_CENTRIC = 2;
    public static final int USAGE_SETTING_DEFAULT = 0;
    public static final int USAGE_SETTING_UNKNOWN = -1;
    public static final int USAGE_SETTING_VOICE_CENTRIC = 1;
    public static final String USER_HANDLE = "user_handle";
    private static final boolean VDBG = false;
    public static final String VOIMS_OPT_IN_STATUS = "voims_opt_in_status";
    public static final String VT_IMS_ENABLED = "vt_ims_enabled";
    public static final String WFC_IMS_ENABLED = "wfc_ims_enabled";
    public static final String WFC_IMS_MODE = "wfc_ims_mode";
    public static final String WFC_IMS_ROAMING_ENABLED = "wfc_ims_roaming_enabled";
    public static final String WFC_IMS_ROAMING_MODE = "wfc_ims_roaming_mode";
    private final Context mContext;
    private final boolean mIsForAllUserProfiles;
    public static final Uri CONTENT_URI = Telephony.SimInfo.CONTENT_URI;
    private static final String CACHE_KEY_SUBSCRIPTION_MANAGER_SERVICE_PROPERTY = "cache_key.telephony.subscription_manager_service";
    private static IntegerPropertyInvalidatedCache<Integer> sGetDefaultSubIdCacheAsUser = new IntegerPropertyInvalidatedCache<>(new FunctionalUtils.ThrowingBiFunction() { // from class: android.telephony.SubscriptionManager$$ExternalSyntheticLambda9
        @Override // com.android.internal.util.FunctionalUtils.ThrowingBiFunction
        public final Object applyOrThrow(Object obj, Object obj2) {
            return Integer.valueOf(((ISub) obj).getDefaultSubIdAsUser(((Integer) obj2).intValue()));
        }
    }, CACHE_KEY_SUBSCRIPTION_MANAGER_SERVICE_PROPERTY, -1);
    private static VoidPropertyInvalidatedCache<Integer> sGetDefaultDataSubIdCache = new VoidPropertyInvalidatedCache<>(new FunctionalUtils.ThrowingFunction() { // from class: android.telephony.SubscriptionManager$$ExternalSyntheticLambda10
        @Override // com.android.internal.util.FunctionalUtils.ThrowingFunction
        public final Object applyOrThrow(Object obj) {
            return Integer.valueOf(((ISub) obj).getDefaultDataSubId());
        }
    }, CACHE_KEY_SUBSCRIPTION_MANAGER_SERVICE_PROPERTY, -1);
    private static IntegerPropertyInvalidatedCache<Integer> sGetDefaultSmsSubIdCacheAsUser = new IntegerPropertyInvalidatedCache<>(new FunctionalUtils.ThrowingBiFunction() { // from class: android.telephony.SubscriptionManager$$ExternalSyntheticLambda11
        @Override // com.android.internal.util.FunctionalUtils.ThrowingBiFunction
        public final Object applyOrThrow(Object obj, Object obj2) {
            return Integer.valueOf(((ISub) obj).getDefaultSmsSubIdAsUser(((Integer) obj2).intValue()));
        }
    }, CACHE_KEY_SUBSCRIPTION_MANAGER_SERVICE_PROPERTY, -1);
    private static VoidPropertyInvalidatedCache<Integer> sGetActiveDataSubscriptionIdCache = new VoidPropertyInvalidatedCache<>(new FunctionalUtils.ThrowingFunction() { // from class: android.telephony.SubscriptionManager$$ExternalSyntheticLambda12
        @Override // com.android.internal.util.FunctionalUtils.ThrowingFunction
        public final Object applyOrThrow(Object obj) {
            return Integer.valueOf(((ISub) obj).getActiveDataSubscriptionId());
        }
    }, CACHE_KEY_SUBSCRIPTION_MANAGER_SERVICE_PROPERTY, -1);
    private static IntegerPropertyInvalidatedCache<Integer> sGetSlotIndexCache = new IntegerPropertyInvalidatedCache<>(new FunctionalUtils.ThrowingBiFunction() { // from class: android.telephony.SubscriptionManager$$ExternalSyntheticLambda13
        @Override // com.android.internal.util.FunctionalUtils.ThrowingBiFunction
        public final Object applyOrThrow(Object obj, Object obj2) {
            return Integer.valueOf(((ISub) obj).getSlotIndex(((Integer) obj2).intValue()));
        }
    }, CACHE_KEY_SUBSCRIPTION_MANAGER_SERVICE_PROPERTY, -1);
    private static IntegerPropertyInvalidatedCache<Integer> sGetSubIdCache = new IntegerPropertyInvalidatedCache<>(new FunctionalUtils.ThrowingBiFunction() { // from class: android.telephony.SubscriptionManager$$ExternalSyntheticLambda14
        @Override // com.android.internal.util.FunctionalUtils.ThrowingBiFunction
        public final Object applyOrThrow(Object obj, Object obj2) {
            return Integer.valueOf(((ISub) obj).getSubId(((Integer) obj2).intValue()));
        }
    }, CACHE_KEY_SUBSCRIPTION_MANAGER_SERVICE_PROPERTY, -1);
    private static IntegerPropertyInvalidatedCache<Integer> sGetPhoneIdCache = new IntegerPropertyInvalidatedCache<>(new FunctionalUtils.ThrowingBiFunction() { // from class: android.telephony.SubscriptionManager$$ExternalSyntheticLambda15
        @Override // com.android.internal.util.FunctionalUtils.ThrowingBiFunction
        public final Object applyOrThrow(Object obj, Object obj2) {
            return Integer.valueOf(((ISub) obj).getPhoneId(((Integer) obj2).intValue()));
        }
    }, CACHE_KEY_SUBSCRIPTION_MANAGER_SERVICE_PROPERTY, -1);

    @SystemApi
    public static final Uri WFC_ENABLED_CONTENT_URI = Uri.withAppendedPath(CONTENT_URI, "wfc");

    @SystemApi
    public static final Uri ADVANCED_CALLING_ENABLED_CONTENT_URI = Uri.withAppendedPath(CONTENT_URI, "advanced_calling");

    @SystemApi
    public static final Uri WFC_MODE_CONTENT_URI = Uri.withAppendedPath(CONTENT_URI, "wfc_mode");

    @SystemApi
    public static final Uri WFC_ROAMING_MODE_CONTENT_URI = Uri.withAppendedPath(CONTENT_URI, "wfc_roaming_mode");

    @SystemApi
    public static final Uri VT_ENABLED_CONTENT_URI = Uri.withAppendedPath(CONTENT_URI, "vt_enabled");

    @SystemApi
    public static final Uri WFC_ROAMING_ENABLED_CONTENT_URI = Uri.withAppendedPath(CONTENT_URI, "wfc_roaming_enabled");
    public static final Uri SIM_INFO_BACKUP_AND_RESTORE_CONTENT_URI = Uri.withAppendedPath(CONTENT_URI, "backup_and_restore");
    public static final Uri SIM_INFO_SUW_RESTORE_CONTENT_URI = Uri.withAppendedPath(SIM_INFO_BACKUP_AND_RESTORE_CONTENT_URI, "suw_restore");

    @SystemApi
    public static final Uri CROSS_SIM_ENABLED_CONTENT_URI = Uri.withAppendedPath(CONTENT_URI, "cross_sim_calling_enabled");
    public static final int SERVICE_CAPABILITY_VOICE_BITMASK = serviceCapabilityToBitmask(1);
    public static final int SERVICE_CAPABILITY_SMS_BITMASK = serviceCapabilityToBitmask(2);
    public static final int SERVICE_CAPABILITY_DATA_BITMASK = serviceCapabilityToBitmask(3);
    private static final LruCache<Pair<String, Configuration>, Resources> sResourcesCache = new LruCache<>(1000);

    /* JADX INFO: Access modifiers changed from: private */
    interface CallISubMethodHelper {
        int callMethod(ISub iSub) throws RemoteException;
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface DataRoamingMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface DeviceToDeviceStatusSharingPreference {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface PhoneNumberSource {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ProfileClass {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SemProfileClass {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ServiceCapability {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SimDisplayNameSource {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SubscriptionType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface TransferStatus {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface UsageSetting {
    }

    private static class VoidPropertyInvalidatedCache<T> extends PropertyInvalidatedCache<Void, T> {
        private final String mCacheKeyProperty;
        private final T mDefaultValue;
        private final FunctionalUtils.ThrowingFunction<ISub, T> mInterfaceMethod;

        VoidPropertyInvalidatedCache(FunctionalUtils.ThrowingFunction<ISub, T> subscriptionInterfaceMethod, String cacheKeyProperty, T defaultValue) {
            super(4, cacheKeyProperty);
            this.mInterfaceMethod = subscriptionInterfaceMethod;
            this.mCacheKeyProperty = cacheKeyProperty;
            this.mDefaultValue = defaultValue;
        }

        @Override // android.app.PropertyInvalidatedCache
        public T recompute(Void query) {
            try {
                return this.mInterfaceMethod.applyOrThrow(TelephonyManager.getSubscriptionService());
            } catch (Exception re) {
                throw new RuntimeException(re);
            }
        }

        @Override // android.app.PropertyInvalidatedCache
        public T query(Void r5) {
            T t = this.mDefaultValue;
            try {
                if (TelephonyManager.getSubscriptionService() != null) {
                    return (T) super.query((VoidPropertyInvalidatedCache<T>) r5);
                }
                return t;
            } catch (Exception e) {
                com.android.telephony.Rlog.w(SubscriptionManager.LOG_TAG, "Failed to recompute cache key for " + this.mCacheKeyProperty);
                return t;
            }
        }
    }

    private static class IntegerPropertyInvalidatedCache<T> extends PropertyInvalidatedCache<Integer, T> {
        private final String mCacheKeyProperty;
        private final T mDefaultValue;
        private final FunctionalUtils.ThrowingBiFunction<ISub, Integer, T> mInterfaceMethod;

        IntegerPropertyInvalidatedCache(FunctionalUtils.ThrowingBiFunction<ISub, Integer, T> subscriptionInterfaceMethod, String cacheKeyProperty, T defaultValue) {
            super(4, cacheKeyProperty);
            this.mInterfaceMethod = subscriptionInterfaceMethod;
            this.mCacheKeyProperty = cacheKeyProperty;
            this.mDefaultValue = defaultValue;
        }

        @Override // android.app.PropertyInvalidatedCache
        public T recompute(Integer query) {
            try {
                return this.mInterfaceMethod.applyOrThrow(TelephonyManager.getSubscriptionService(), query);
            } catch (Exception re) {
                throw new RuntimeException(re);
            }
        }

        @Override // android.app.PropertyInvalidatedCache
        public T query(Integer num) {
            T t = this.mDefaultValue;
            try {
                if (TelephonyManager.getSubscriptionService() != null) {
                    return (T) super.query((IntegerPropertyInvalidatedCache<T>) num);
                }
                return t;
            } catch (Exception e) {
                com.android.telephony.Rlog.w(SubscriptionManager.LOG_TAG, "Failed to recompute cache key for " + this.mCacheKeyProperty);
                return t;
            }
        }
    }

    public static Uri getUriForSubscriptionId(int subscriptionId) {
        return Uri.withAppendedPath(CONTENT_URI, String.valueOf(subscriptionId));
    }

    public static class OnSubscriptionsChangedListener {
        private static final long LAZY_INITIALIZE_SUBSCRIPTIONS_CHANGED_HANDLER = 278814050;
        private final Looper mCreatorLooper;

        public Looper getCreatorLooper() {
            return this.mCreatorLooper;
        }

        public OnSubscriptionsChangedListener() {
            this.mCreatorLooper = Looper.myLooper();
            if (this.mCreatorLooper == null && !Compatibility.isChangeEnabled(LAZY_INITIALIZE_SUBSCRIPTIONS_CHANGED_HANDLER)) {
                throw new RuntimeException("Can't create handler inside thread " + Thread.currentThread() + " that has not called Looper.prepare()");
            }
        }

        public OnSubscriptionsChangedListener(Looper looper) {
            Objects.requireNonNull(looper);
            this.mCreatorLooper = looper;
        }

        public void onSubscriptionsChanged() {
        }

        public void onAddListenerFailed() {
            com.android.telephony.Rlog.w(SubscriptionManager.LOG_TAG, "onAddListenerFailed not overridden");
        }

        private void log(String s) {
            com.android.telephony.Rlog.d(SubscriptionManager.LOG_TAG, s);
        }
    }

    public SubscriptionManager(Context context) {
        this(context, false);
    }

    private SubscriptionManager(Context context, boolean isForAllUserProfiles) {
        this.mIsForAllUserProfiles = isForAllUserProfiles;
        this.mContext = context;
    }

    private NetworkPolicyManager getNetworkPolicyManager() {
        return (NetworkPolicyManager) this.mContext.getSystemService(Context.NETWORK_POLICY_SERVICE);
    }

    @Deprecated
    public static SubscriptionManager from(Context context) {
        return (SubscriptionManager) context.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);
    }

    @Deprecated
    public void addOnSubscriptionsChangedListener(OnSubscriptionsChangedListener listener) {
        if (listener == null) {
            return;
        }
        Looper looper = listener.getCreatorLooper();
        if (looper == null) {
            throw new RuntimeException("Can't create handler inside thread " + Thread.currentThread() + " that has not called Looper.prepare()");
        }
        addOnSubscriptionsChangedListener(new HandlerExecutor(new Handler(looper)), listener);
    }

    public void addOnSubscriptionsChangedListener(Executor executor, final OnSubscriptionsChangedListener listener) {
        String pkgName = this.mContext != null ? this.mContext.getOpPackageName() : "<unknown>";
        TelephonyRegistryManager telephonyRegistryManager = (TelephonyRegistryManager) this.mContext.getSystemService(Context.TELEPHONY_REGISTRY_SERVICE);
        if (telephonyRegistryManager != null) {
            telephonyRegistryManager.addOnSubscriptionsChangedListener(listener, executor);
        } else {
            loge("addOnSubscriptionsChangedListener: pkgname=" + pkgName + " failed to be added  due to TELEPHONY_REGISTRY_SERVICE being unavailable.");
            executor.execute(new Runnable() { // from class: android.telephony.SubscriptionManager$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    SubscriptionManager.OnSubscriptionsChangedListener.this.onAddListenerFailed();
                }
            });
        }
    }

    public void removeOnSubscriptionsChangedListener(OnSubscriptionsChangedListener listener) {
        if (listener == null) {
            return;
        }
        if (this.mContext != null) {
            this.mContext.getOpPackageName();
        }
        TelephonyRegistryManager telephonyRegistryManager = (TelephonyRegistryManager) this.mContext.getSystemService(Context.TELEPHONY_REGISTRY_SERVICE);
        if (telephonyRegistryManager != null) {
            telephonyRegistryManager.removeOnSubscriptionsChangedListener(listener);
        }
    }

    public static class OnOpportunisticSubscriptionsChangedListener {
        public void onOpportunisticSubscriptionsChanged() {
        }

        private void log(String s) {
            com.android.telephony.Rlog.d(SubscriptionManager.LOG_TAG, s);
        }
    }

    public void addOnOpportunisticSubscriptionsChangedListener(Executor executor, OnOpportunisticSubscriptionsChangedListener listener) {
        if (executor == null || listener == null) {
            return;
        }
        if (this.mContext != null) {
            this.mContext.getOpPackageName();
        }
        TelephonyRegistryManager telephonyRegistryManager = (TelephonyRegistryManager) this.mContext.getSystemService(Context.TELEPHONY_REGISTRY_SERVICE);
        if (telephonyRegistryManager != null) {
            telephonyRegistryManager.addOnOpportunisticSubscriptionsChangedListener(listener, executor);
        }
    }

    public void removeOnOpportunisticSubscriptionsChangedListener(OnOpportunisticSubscriptionsChangedListener listener) {
        Preconditions.checkNotNull(listener, "listener cannot be null");
        if (this.mContext != null) {
            this.mContext.getOpPackageName();
        }
        TelephonyRegistryManager telephonyRegistryManager = (TelephonyRegistryManager) this.mContext.getSystemService(Context.TELEPHONY_REGISTRY_SERVICE);
        if (telephonyRegistryManager != null) {
            telephonyRegistryManager.removeOnOpportunisticSubscriptionsChangedListener(listener);
        }
    }

    public SubscriptionInfo getActiveSubscriptionInfo(int subId) {
        if (!isValidSubscriptionId(subId)) {
            return null;
        }
        try {
            ISub iSub = TelephonyManager.getSubscriptionService();
            if (iSub == null) {
                return null;
            }
            SubscriptionInfo subInfo = iSub.getActiveSubscriptionInfo(subId, this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
            return subInfo;
        } catch (RemoteException e) {
            return null;
        }
    }

    @SystemApi
    public SubscriptionInfo getActiveSubscriptionInfoForIcc(String iccId) {
        if (iccId == null) {
            logd("[getActiveSubscriptionInfoForIccIndex]- null iccid");
            return null;
        }
        try {
            ISub iSub = TelephonyManager.getSubscriptionService();
            if (iSub == null) {
                return null;
            }
            SubscriptionInfo result = iSub.getActiveSubscriptionInfoForIccId(iccId, this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
            return result;
        } catch (RemoteException e) {
            return null;
        }
    }

    public SubscriptionInfo getActiveSubscriptionInfoForSimSlotIndex(int slotIndex) {
        if (!isValidSlotIndex(slotIndex)) {
            logd("[getActiveSubscriptionInfoForSimSlotIndex]- invalid slotIndex");
            return null;
        }
        try {
            ISub iSub = TelephonyManager.getSubscriptionService();
            if (iSub == null) {
                return null;
            }
            SubscriptionInfo result = iSub.getActiveSubscriptionInfoForSimSlotIndex(slotIndex, this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
            return result;
        } catch (RemoteException e) {
            return null;
        }
    }

    public List<SubscriptionInfo> getAllSubscriptionInfoList() {
        List<SubscriptionInfo> result = null;
        try {
            ISub iSub = TelephonyManager.getSubscriptionService();
            if (iSub != null) {
                result = iSub.getAllSubInfoList(this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
            }
        } catch (RemoteException e) {
        }
        if (result == null) {
            return Collections.emptyList();
        }
        return result;
    }

    public List<SubscriptionInfo> getActiveSubscriptionInfoList() {
        List<SubscriptionInfo> activeList = null;
        try {
            ISub iSub = TelephonyManager.getSubscriptionService();
            if (iSub != null) {
                activeList = iSub.getActiveSubscriptionInfoList(this.mContext.getOpPackageName(), this.mContext.getAttributionTag(), this.mIsForAllUserProfiles);
            }
        } catch (RemoteException e) {
        }
        if (activeList != null) {
            return (List) activeList.stream().filter(new Predicate() { // from class: android.telephony.SubscriptionManager$$ExternalSyntheticLambda6
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    boolean lambda$getActiveSubscriptionInfoList$1;
                    lambda$getActiveSubscriptionInfoList$1 = SubscriptionManager.this.lambda$getActiveSubscriptionInfoList$1((SubscriptionInfo) obj);
                    return lambda$getActiveSubscriptionInfoList$1;
                }
            }).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public List<SubscriptionInfo> getCompleteActiveSubscriptionInfoList() {
        return getActiveSubscriptionInfoList(false);
    }

    public SubscriptionManager createForAllUserProfiles() {
        return new SubscriptionManager(this.mContext, true);
    }

    public List<SubscriptionInfo> getActiveSubscriptionInfoList(boolean userVisibleOnly) {
        List<SubscriptionInfo> activeList = null;
        try {
            ISub iSub = TelephonyManager.getSubscriptionService();
            if (iSub != null) {
                activeList = iSub.getActiveSubscriptionInfoList(this.mContext.getOpPackageName(), this.mContext.getAttributionTag(), true);
            }
        } catch (RemoteException e) {
        }
        if (activeList == null || activeList.isEmpty()) {
            return Collections.emptyList();
        }
        if (userVisibleOnly) {
            return (List) activeList.stream().filter(new Predicate() { // from class: android.telephony.SubscriptionManager$$ExternalSyntheticLambda17
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    boolean lambda$getActiveSubscriptionInfoList$2;
                    lambda$getActiveSubscriptionInfoList$2 = SubscriptionManager.this.lambda$getActiveSubscriptionInfoList$2((SubscriptionInfo) obj);
                    return lambda$getActiveSubscriptionInfoList$2;
                }
            }).collect(Collectors.toList());
        }
        return activeList;
    }

    @SystemApi
    public List<SubscriptionInfo> getAvailableSubscriptionInfoList() {
        List<SubscriptionInfo> result = null;
        try {
            ISub iSub = TelephonyManager.getSubscriptionService();
            if (iSub != null) {
                result = iSub.getAvailableSubscriptionInfoList(this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
            }
        } catch (RemoteException e) {
        }
        return result == null ? Collections.emptyList() : result;
    }

    public List<SubscriptionInfo> semGetAvailableSubscriptionInfoListWithSelectable(boolean selectable) {
        if (selectable) {
            return getSelectableSubscriptionInfoList();
        }
        return getAvailableSubscriptionInfoList();
    }

    public List<SubscriptionInfo> getAccessibleSubscriptionInfoList() {
        List<SubscriptionInfo> result = null;
        try {
            ISub iSub = TelephonyManager.getSubscriptionService();
            if (iSub != null) {
                result = iSub.getAccessibleSubscriptionInfoList(this.mContext.getOpPackageName());
            }
        } catch (RemoteException e) {
        }
        return result == null ? Collections.emptyList() : result;
    }

    @SystemApi
    public void requestEmbeddedSubscriptionInfoListRefresh() {
        int cardId = TelephonyManager.from(this.mContext).getCardIdForDefaultEuicc();
        try {
            ISub iSub = TelephonyManager.getSubscriptionService();
            if (iSub != null) {
                iSub.requestEmbeddedSubscriptionInfoListRefresh(cardId);
            }
        } catch (RemoteException e) {
            logd("requestEmbeddedSubscriptionInfoListFresh for card = " + cardId + " failed.");
        }
    }

    @SystemApi
    public void requestEmbeddedSubscriptionInfoListRefresh(int cardId) {
        try {
            ISub iSub = TelephonyManager.getSubscriptionService();
            if (iSub != null) {
                iSub.requestEmbeddedSubscriptionInfoListRefresh(cardId);
            }
        } catch (RemoteException e) {
            logd("requestEmbeddedSubscriptionInfoListFresh for card = " + cardId + " failed.");
        }
    }

    public int getActiveSubscriptionInfoCount() {
        try {
            ISub iSub = TelephonyManager.getSubscriptionService();
            if (iSub == null) {
                return 0;
            }
            int result = iSub.getActiveSubInfoCount(this.mContext.getOpPackageName(), this.mContext.getAttributionTag(), this.mIsForAllUserProfiles);
            return result;
        } catch (RemoteException e) {
            return 0;
        }
    }

    public int getActiveSubscriptionInfoCountMax() {
        try {
            ISub iSub = TelephonyManager.getSubscriptionService();
            if (iSub == null) {
                return 0;
            }
            int result = iSub.getActiveSubInfoCountMax();
            return result;
        } catch (RemoteException e) {
            return 0;
        }
    }

    public Uri addSubscriptionInfoRecord(String iccId, int slotIndex) {
        if (iccId == null) {
            logd("[addSubscriptionInfoRecord]- null iccId");
        }
        if (!isValidSlotIndex(slotIndex)) {
            logd("[addSubscriptionInfoRecord]- invalid slotIndex");
        }
        addSubscriptionInfoRecord(iccId, null, slotIndex, 0);
        return null;
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public void addSubscriptionInfoRecord(String uniqueId, String displayName, int slotIndex, int subscriptionType) {
        if (uniqueId == null) {
            Log.e(LOG_TAG, "[addSubscriptionInfoRecord]- uniqueId is null");
            return;
        }
        try {
            ISub iSub = TelephonyManager.getSubscriptionService();
            if (iSub == null) {
                Log.e(LOG_TAG, "[addSubscriptionInfoRecord]- ISub service is null");
                return;
            }
            int result = iSub.addSubInfo(uniqueId, displayName, slotIndex, subscriptionType);
            if (result < 0) {
                Log.e(LOG_TAG, "Adding of subscription didn't succeed: error = " + result);
            } else {
                logd("successfully added new subscription");
            }
        } catch (RemoteException e) {
        }
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public void removeSubscriptionInfoRecord(String uniqueId, int subscriptionType) {
        if (uniqueId == null) {
            Log.e(LOG_TAG, "[addSubscriptionInfoRecord]- uniqueId is null");
            return;
        }
        try {
            ISub iSub = TelephonyManager.getSubscriptionService();
            if (iSub == null) {
                Log.e(LOG_TAG, "[removeSubscriptionInfoRecord]- ISub service is null");
                return;
            }
            boolean result = iSub.removeSubInfo(uniqueId, subscriptionType);
            if (!result) {
                Log.e(LOG_TAG, "Removal of subscription didn't succeed");
            } else {
                logd("successfully removed subscription");
            }
        } catch (RemoteException e) {
        }
    }

    public int setIconTint(final int tint, final int subId) {
        return setSubscriptionPropertyHelper(subId, "setIconTint", new CallISubMethodHelper() { // from class: android.telephony.SubscriptionManager$$ExternalSyntheticLambda4
            @Override // android.telephony.SubscriptionManager.CallISubMethodHelper
            public final int callMethod(ISub iSub) {
                int iconTint;
                iconTint = iSub.setIconTint(subId, tint);
                return iconTint;
            }
        });
    }

    public int setDisplayName(final String displayName, final int subId, final int nameSource) {
        return setSubscriptionPropertyHelper(subId, "setDisplayName", new CallISubMethodHelper() { // from class: android.telephony.SubscriptionManager$$ExternalSyntheticLambda16
            @Override // android.telephony.SubscriptionManager.CallISubMethodHelper
            public final int callMethod(ISub iSub) {
                int displayNameUsingSrc;
                displayNameUsingSrc = iSub.setDisplayNameUsingSrc(displayName, subId, nameSource);
                return displayNameUsingSrc;
            }
        });
    }

    public int setDisplayNumber(final String number, final int subId) {
        if (number == null) {
            logd("[setDisplayNumber]- fail");
            return -1;
        }
        return setSubscriptionPropertyHelper(subId, "setDisplayNumber", new CallISubMethodHelper() { // from class: android.telephony.SubscriptionManager$$ExternalSyntheticLambda19
            @Override // android.telephony.SubscriptionManager.CallISubMethodHelper
            public final int callMethod(ISub iSub) {
                int displayNumber;
                displayNumber = iSub.setDisplayNumber(number, subId);
                return displayNumber;
            }
        });
    }

    public int setDataRoaming(final int roaming, final int subId) {
        return setSubscriptionPropertyHelper(subId, "setDataRoaming", new CallISubMethodHelper() { // from class: android.telephony.SubscriptionManager$$ExternalSyntheticLambda5
            @Override // android.telephony.SubscriptionManager.CallISubMethodHelper
            public final int callMethod(ISub iSub) {
                int dataRoaming;
                dataRoaming = iSub.setDataRoaming(roaming, subId);
                return dataRoaming;
            }
        });
    }

    public static int getSlotIndex(int subscriptionId) {
        return sGetSlotIndexCache.query(Integer.valueOf(subscriptionId)).intValue();
    }

    @Deprecated
    public int[] getSubscriptionIds(int slotIndex) {
        if (!isValidSlotIndex(slotIndex)) {
            return null;
        }
        return new int[]{getSubscriptionId(slotIndex)};
    }

    @Deprecated
    public static int[] getSubId(int slotIndex) {
        if (!isValidSlotIndex(slotIndex)) {
            return null;
        }
        return new int[]{getSubscriptionId(slotIndex)};
    }

    public static int getSubscriptionId(int slotIndex) {
        if (!isValidSlotIndex(slotIndex)) {
            return -1;
        }
        return sGetSubIdCache.query(Integer.valueOf(slotIndex)).intValue();
    }

    public static int getPhoneId(int subId) {
        return sGetPhoneIdCache.query(Integer.valueOf(subId)).intValue();
    }

    private static void logd(String msg) {
        com.android.telephony.Rlog.d(LOG_TAG, msg);
    }

    private static void loge(String msg) {
        com.android.telephony.Rlog.e(LOG_TAG, msg);
    }

    public static int getDefaultSubscriptionId() {
        return sGetDefaultSubIdCacheAsUser.query(Integer.valueOf(Process.myUserHandle().getIdentifier())).intValue();
    }

    public static int getDefaultVoiceSubscriptionId() {
        try {
            ISub iSub = TelephonyManager.getSubscriptionService();
            if (iSub == null) {
                return -1;
            }
            int subId = iSub.getDefaultVoiceSubIdAsUser(Process.myUserHandle().getIdentifier());
            return subId;
        } catch (RemoteException e) {
            return -1;
        }
    }

    @SystemApi
    public void setDefaultVoiceSubscriptionId(int subscriptionId) {
        try {
            ISub iSub = TelephonyManager.getSubscriptionService();
            if (iSub != null) {
                iSub.setDefaultVoiceSubId(subscriptionId);
            }
        } catch (RemoteException e) {
        }
    }

    public void setDefaultVoiceSubId(int subId) {
        setDefaultVoiceSubscriptionId(subId);
    }

    public SubscriptionInfo getDefaultVoiceSubscriptionInfo() {
        return getActiveSubscriptionInfo(getDefaultVoiceSubscriptionId());
    }

    public static int getDefaultVoicePhoneId() {
        return getPhoneId(getDefaultVoiceSubscriptionId());
    }

    public static int getDefaultSmsSubscriptionId() {
        return sGetDefaultSmsSubIdCacheAsUser.query(Integer.valueOf(Process.myUserHandle().getIdentifier())).intValue();
    }

    @SystemApi
    public void setDefaultSmsSubId(int subscriptionId) {
        try {
            ISub iSub = TelephonyManager.getSubscriptionService();
            if (iSub != null) {
                iSub.setDefaultSmsSubId(subscriptionId);
            }
        } catch (RemoteException ex) {
            ex.rethrowFromSystemServer();
        }
    }

    public static int getDefaultDataSubscriptionId() {
        return sGetDefaultDataSubIdCache.query((Void) null).intValue();
    }

    @SystemApi
    public void setDefaultDataSubId(int subscriptionId) {
        try {
            ISub iSub = TelephonyManager.getSubscriptionService();
            if (iSub != null) {
                iSub.setDefaultDataSubId(subscriptionId);
            }
        } catch (RemoteException e) {
        }
    }

    public SubscriptionInfo getDefaultDataSubscriptionInfo() {
        return getActiveSubscriptionInfo(getDefaultDataSubscriptionId());
    }

    public static boolean isValidSubscriptionId(int subscriptionId) {
        return subscriptionId > -1;
    }

    public static boolean isUsableSubscriptionId(int subscriptionId) {
        return isUsableSubIdValue(subscriptionId);
    }

    public static boolean isUsableSubIdValue(int subId) {
        return subId >= 0 && subId <= 2147483646;
    }

    public static boolean isValidSlotIndex(int slotIndex) {
        return slotIndex >= 0 && slotIndex < TelephonyManager.getDefault().getActiveModemCount();
    }

    public static boolean isValidPhoneId(int phoneId) {
        return phoneId >= 0 && phoneId < TelephonyManager.getDefault().getActiveModemCount();
    }

    public static void putPhoneIdAndSubIdExtra(Intent intent, int phoneId) {
        int subId = getSubscriptionId(phoneId);
        if (isValidSubscriptionId(subId)) {
            putPhoneIdAndSubIdExtra(intent, phoneId, subId);
            return;
        }
        logd("putPhoneIdAndSubIdExtra: no valid subs");
        intent.putExtra("phone", phoneId);
        intent.putExtra("android.telephony.extra.SLOT_INDEX", phoneId);
    }

    public static void putPhoneIdAndSubIdExtra(Intent intent, int phoneId, int subId) {
        intent.putExtra("android.telephony.extra.SLOT_INDEX", phoneId);
        intent.putExtra("phone", phoneId);
        putSubscriptionIdExtra(intent, subId);
    }

    @SystemApi
    public int[] getActiveSubscriptionIdList() {
        return getActiveSubscriptionIdList(true);
    }

    @SystemApi
    public int[] getCompleteActiveSubscriptionIdList() {
        return getActiveSubscriptionIdList(false);
    }

    public int[] getActiveSubscriptionIdList(boolean visibleOnly) {
        try {
            ISub iSub = TelephonyManager.getSubscriptionService();
            if (iSub != null) {
                int[] subId = iSub.getActiveSubIdList(visibleOnly);
                if (subId != null) {
                    return subId;
                }
            }
        } catch (RemoteException e) {
        }
        return new int[0];
    }

    public boolean isNetworkRoaming(int subId) {
        int phoneId = getPhoneId(subId);
        if (phoneId < 0) {
            return false;
        }
        return TelephonyManager.getDefault().isNetworkRoaming(subId);
    }

    public static void setSubscriptionProperty(int subscriptionId, String columnName, String value) {
        try {
            ISub iSub = TelephonyManager.getSubscriptionService();
            if (iSub != null) {
                iSub.setSubscriptionProperty(subscriptionId, columnName, value);
            }
        } catch (RemoteException e) {
        }
    }

    public static String serializeUriLists(List<Uri> uris) {
        List<String> contacts = new ArrayList<>();
        for (Uri uri : uris) {
            contacts.add(uri.toString());
        }
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(contacts);
            oos.flush();
            return Base64.encodeToString(bos.toByteArray(), 0);
        } catch (IOException e) {
            logd("serializeUriLists IO exception");
            return "";
        }
    }

    private static String getStringSubscriptionProperty(Context context, int subscriptionId, String columnName) {
        String resultValue = null;
        try {
            ISub iSub = TelephonyManager.getSubscriptionService();
            if (iSub != null) {
                resultValue = iSub.getSubscriptionProperty(subscriptionId, columnName, context.getOpPackageName(), context.getAttributionTag());
            }
        } catch (RemoteException e) {
        }
        return TextUtils.emptyIfNull(resultValue);
    }

    public static boolean getBooleanSubscriptionProperty(int subscriptionId, String columnName, boolean defaultValue, Context context) {
        String result = getStringSubscriptionProperty(context, subscriptionId, columnName);
        if (!result.isEmpty()) {
            try {
                return Integer.parseInt(result) == 1;
            } catch (NumberFormatException e) {
                logd("getBooleanSubscriptionProperty NumberFormat exception");
            }
        }
        return defaultValue;
    }

    public static int getIntegerSubscriptionProperty(int subscriptionId, String columnName, int defaultValue, Context context) {
        String result = getStringSubscriptionProperty(context, subscriptionId, columnName);
        if (!result.isEmpty()) {
            try {
                return Integer.parseInt(result);
            } catch (NumberFormatException e) {
                logd("getIntegerSubscriptionProperty NumberFormat exception");
            }
        }
        return defaultValue;
    }

    public static long getLongSubscriptionProperty(int subscriptionId, String columnName, long defaultValue, Context context) {
        String result = getStringSubscriptionProperty(context, subscriptionId, columnName);
        if (!result.isEmpty()) {
            try {
                return Long.parseLong(result);
            } catch (NumberFormatException e) {
                logd("getLongSubscriptionProperty NumberFormat exception");
            }
        }
        return defaultValue;
    }

    @SystemApi
    public static Resources getResourcesForSubId(Context context, int subId) {
        return getResourcesForSubId(context, subId, false);
    }

    public static Resources getResourcesForSubId(Context context, int subId, boolean useRootLocale) {
        Pair<String, Configuration> cacheKey = null;
        if (isValidSubscriptionId(subId)) {
            Configuration configurationKey = new Configuration(context.getResources().getConfiguration());
            if (useRootLocale) {
                configurationKey.setLocale(Locale.ROOT);
            }
            Pair<String, Configuration> cacheKey2 = Pair.create(context.getPackageName() + ", subid=" + subId, configurationKey);
            synchronized (sResourcesCache) {
                Resources cached = sResourcesCache.get(cacheKey2);
                if (cached != null) {
                    return cached;
                }
                cacheKey = cacheKey2;
            }
        }
        SubscriptionInfo subInfo = from(context).getActiveSubscriptionInfo(subId);
        Configuration overrideConfig = new Configuration();
        if (subInfo != null) {
            overrideConfig.mcc = subInfo.getMcc();
            overrideConfig.mnc = subInfo.getMnc();
            if (overrideConfig.mnc == 0) {
                overrideConfig.mnc = 65535;
                cacheKey = null;
            }
        } else {
            cacheKey = null;
        }
        if (useRootLocale) {
            overrideConfig.setLocale(Locale.ROOT);
        }
        Context newContext = context.createConfigurationContext(overrideConfig);
        Resources res = newContext.getResources();
        if (cacheKey != null) {
            synchronized (sResourcesCache) {
                sResourcesCache.put(cacheKey, res);
            }
        }
        return res;
    }

    public boolean isActiveSubscriptionId(int subscriptionId) {
        return isActiveSubId(subscriptionId);
    }

    public boolean isActiveSubId(int subId) {
        try {
            ISub iSub = TelephonyManager.getSubscriptionService();
            if (iSub != null) {
                return iSub.isActiveSubId(subId, this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
            }
            return false;
        } catch (RemoteException e) {
            return false;
        }
    }

    public List<SubscriptionPlan> getSubscriptionPlans(int subId) {
        SubscriptionPlan[] subscriptionPlans = getNetworkPolicyManager().getSubscriptionPlans(subId, this.mContext.getOpPackageName());
        return subscriptionPlans == null ? Collections.emptyList() : Arrays.asList(subscriptionPlans);
    }

    @Deprecated
    public void setSubscriptionPlans(int subId, List<SubscriptionPlan> plans) {
        setSubscriptionPlans(subId, plans, 0L);
    }

    public void setSubscriptionPlans(int subId, List<SubscriptionPlan> plans, long expirationDurationMillis) {
        getNetworkPolicyManager().setSubscriptionPlans(subId, (SubscriptionPlan[]) plans.toArray(new SubscriptionPlan[0]), expirationDurationMillis, this.mContext.getOpPackageName());
    }

    public void setSubscriptionOverrideUnmetered(int subId, boolean overrideUnmetered, long expirationDurationMillis) {
        setSubscriptionOverrideUnmetered(subId, overrideUnmetered, TelephonyManager.getAllNetworkTypes(), expirationDurationMillis);
    }

    public void setSubscriptionOverrideUnmetered(int i, boolean z, int[] iArr, long j) {
        getNetworkPolicyManager().setSubscriptionOverride(i, 1, z ? 1 : 0, iArr, j, this.mContext.getOpPackageName());
    }

    public void setSubscriptionOverrideCongested(int subId, boolean overrideCongested, long expirationDurationMillis) {
        setSubscriptionOverrideCongested(subId, overrideCongested, TelephonyManager.getAllNetworkTypes(), expirationDurationMillis);
    }

    public void setSubscriptionOverrideCongested(int subId, boolean overrideCongested, int[] networkTypes, long expirationDurationMillis) {
        int overrideValue = overrideCongested ? 2 : 0;
        getNetworkPolicyManager().setSubscriptionOverride(subId, 2, overrideValue, networkTypes, expirationDurationMillis, this.mContext.getOpPackageName());
    }

    public boolean canManageSubscription(SubscriptionInfo info) {
        return canManageSubscription(info, this.mContext.getPackageName());
    }

    @SystemApi
    public boolean canManageSubscription(SubscriptionInfo info, String packageName) {
        if (info == null || info.getAccessRules() == null || packageName == null) {
            return false;
        }
        PackageManager packageManager = this.mContext.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 134217728);
            for (UiccAccessRule rule : info.getAccessRules()) {
                if (rule.getCarrierPrivilegeStatus(packageInfo) == 1) {
                    return true;
                }
            }
            return false;
        } catch (PackageManager.NameNotFoundException e) {
            logd("Unknown package: " + packageName);
            return false;
        }
    }

    @SystemApi
    public void setPreferredDataSubscriptionId(int subId, boolean needValidation, Executor executor, Consumer<Integer> callback) {
        try {
            ISub iSub = TelephonyManager.getSubscriptionService();
            if (iSub == null) {
                throw new IllegalStateException("subscription manager service is null.");
            }
            ISetOpportunisticDataCallback callbackStub = new AnonymousClass1(executor, callback);
            iSub.setPreferredDataSubscriptionId(subId, needValidation, callbackStub);
        } catch (RemoteException ex) {
            loge("setPreferredDataSubscriptionId RemoteException=" + ex);
            ex.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.telephony.SubscriptionManager$1, reason: invalid class name */
    class AnonymousClass1 extends ISetOpportunisticDataCallback.Stub {
        final /* synthetic */ Consumer val$callback;
        final /* synthetic */ Executor val$executor;

        AnonymousClass1(Executor executor, Consumer consumer) {
            this.val$executor = executor;
            this.val$callback = consumer;
        }

        @Override // com.android.internal.telephony.ISetOpportunisticDataCallback
        public void onComplete(final int result) {
            if (this.val$executor == null || this.val$callback == null) {
                return;
            }
            long identity = Binder.clearCallingIdentity();
            try {
                Executor executor = this.val$executor;
                final Consumer consumer = this.val$callback;
                executor.execute(new Runnable() { // from class: android.telephony.SubscriptionManager$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        consumer.accept(Integer.valueOf(result));
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(identity);
            }
        }
    }

    public int semGetPreferredDataSubscriptionId() {
        return getPreferredDataSubscriptionId();
    }

    public int getPreferredDataSubscriptionId() {
        try {
            ISub iSub = TelephonyManager.getSubscriptionService();
            if (iSub == null) {
                return Integer.MAX_VALUE;
            }
            int preferredSubId = iSub.getPreferredDataSubscriptionId();
            return preferredSubId;
        } catch (RemoteException e) {
            return Integer.MAX_VALUE;
        }
    }

    public List<SubscriptionInfo> getOpportunisticSubscriptions() {
        String contextPkg = this.mContext != null ? this.mContext.getOpPackageName() : "<unknown>";
        String contextAttributionTag = this.mContext != null ? this.mContext.getAttributionTag() : null;
        List<SubscriptionInfo> subInfoList = null;
        try {
            ISub iSub = TelephonyManager.getSubscriptionService();
            if (iSub != null) {
                subInfoList = iSub.getOpportunisticSubscriptions(contextPkg, contextAttributionTag);
            }
        } catch (RemoteException e) {
        }
        if (subInfoList == null) {
            return new ArrayList<>();
        }
        return subInfoList;
    }

    @Deprecated
    public void switchToSubscription(int subId, PendingIntent callbackIntent) {
        Preconditions.checkNotNull(callbackIntent, "callbackIntent cannot be null");
        EuiccManager euiccManager = new EuiccManager(this.mContext);
        euiccManager.switchToSubscription(subId, callbackIntent);
    }

    public boolean setOpportunistic(final boolean opportunistic, final int subId) {
        return setSubscriptionPropertyHelper(subId, "setOpportunistic", new CallISubMethodHelper() { // from class: android.telephony.SubscriptionManager$$ExternalSyntheticLambda18
            @Override // android.telephony.SubscriptionManager.CallISubMethodHelper
            public final int callMethod(ISub iSub) {
                int lambda$setOpportunistic$7;
                lambda$setOpportunistic$7 = SubscriptionManager.this.lambda$setOpportunistic$7(opportunistic, subId, iSub);
                return lambda$setOpportunistic$7;
            }
        }) == 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$setOpportunistic$7(boolean opportunistic, int subId, ISub iSub) throws RemoteException {
        return iSub.setOpportunistic(opportunistic, subId, this.mContext.getOpPackageName());
    }

    public ParcelUuid createSubscriptionGroup(List<Integer> subIdList) {
        Preconditions.checkNotNull(subIdList, "can't create group for null subId list");
        String pkgForDebug = this.mContext != null ? this.mContext.getOpPackageName() : "<unknown>";
        int[] subIdArray = subIdList.stream().mapToInt(new ToIntFunction() { // from class: android.telephony.SubscriptionManager$$ExternalSyntheticLambda7
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                int intValue;
                intValue = ((Integer) obj).intValue();
                return intValue;
            }
        }).toArray();
        try {
            ISub iSub = TelephonyManager.getSubscriptionService();
            if (iSub != null) {
                ParcelUuid groupUuid = iSub.createSubscriptionGroup(subIdArray, pkgForDebug);
                return groupUuid;
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException ex) {
            loge("createSubscriptionGroup RemoteException " + ex);
            ex.rethrowAsRuntimeException();
            return null;
        }
    }

    public void addSubscriptionsIntoGroup(List<Integer> subIdList, ParcelUuid groupUuid) {
        Preconditions.checkNotNull(subIdList, "subIdList can't be null.");
        Preconditions.checkNotNull(groupUuid, "groupUuid can't be null.");
        String pkgForDebug = this.mContext != null ? this.mContext.getOpPackageName() : "<unknown>";
        int[] subIdArray = subIdList.stream().mapToInt(new ToIntFunction() { // from class: android.telephony.SubscriptionManager$$ExternalSyntheticLambda20
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                int intValue;
                intValue = ((Integer) obj).intValue();
                return intValue;
            }
        }).toArray();
        try {
            ISub iSub = TelephonyManager.getSubscriptionService();
            if (iSub != null) {
                iSub.addSubscriptionsIntoGroup(subIdArray, groupUuid, pkgForDebug);
                return;
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException ex) {
            loge("addSubscriptionsIntoGroup RemoteException " + ex);
            ex.rethrowAsRuntimeException();
        }
    }

    private boolean isSystemProcess() {
        return Process.myUid() == 1000;
    }

    public void removeSubscriptionsFromGroup(List<Integer> subIdList, ParcelUuid groupUuid) {
        Preconditions.checkNotNull(subIdList, "subIdList can't be null.");
        Preconditions.checkNotNull(groupUuid, "groupUuid can't be null.");
        String callingPackage = this.mContext != null ? this.mContext.getOpPackageName() : "<unknown>";
        int[] subIdArray = subIdList.stream().mapToInt(new ToIntFunction() { // from class: android.telephony.SubscriptionManager$$ExternalSyntheticLambda0
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                int intValue;
                intValue = ((Integer) obj).intValue();
                return intValue;
            }
        }).toArray();
        try {
            ISub iSub = TelephonyManager.getSubscriptionService();
            if (iSub != null) {
                iSub.removeSubscriptionsFromGroup(subIdArray, groupUuid, callingPackage);
                return;
            }
            throw new IllegalStateException("telephony service is null.");
        } catch (RemoteException ex) {
            loge("removeSubscriptionsFromGroup RemoteException " + ex);
            ex.rethrowAsRuntimeException();
        }
    }

    public List<SubscriptionInfo> getSubscriptionsInGroup(ParcelUuid groupUuid) {
        Preconditions.checkNotNull(groupUuid, "groupUuid can't be null");
        String contextPkg = this.mContext != null ? this.mContext.getOpPackageName() : "<unknown>";
        String contextAttributionTag = this.mContext != null ? this.mContext.getAttributionTag() : null;
        List<SubscriptionInfo> result = null;
        try {
            ISub iSub = TelephonyManager.getSubscriptionService();
            if (iSub != null) {
                result = iSub.getSubscriptionsInGroup(groupUuid, contextPkg, contextAttributionTag);
            } else if (!isSystemProcess()) {
                throw new IllegalStateException("telephony service is null.");
            }
        } catch (RemoteException ex) {
            loge("removeSubscriptionsFromGroup RemoteException " + ex);
            if (!isSystemProcess()) {
                ex.rethrowAsRuntimeException();
            }
        }
        return result == null ? Collections.emptyList() : result;
    }

    /* renamed from: isSubscriptionVisible, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public boolean lambda$getActiveSubscriptionInfoList$2(SubscriptionInfo info) {
        if (info == null) {
            return false;
        }
        if (info.getGroupUuid() == null || !info.isOpportunistic()) {
            return true;
        }
        return TelephonyManager.from(this.mContext).hasCarrierPrivileges(info.getSubscriptionId()) || canManageSubscription(info);
    }

    public List<SubscriptionInfo> getSelectableSubscriptionInfoList() {
        List<SubscriptionInfo> availableList = getAvailableSubscriptionInfoList();
        if (availableList == null) {
            return null;
        }
        List<SubscriptionInfo> selectableList = new ArrayList<>();
        Map<ParcelUuid, SubscriptionInfo> groupMap = new HashMap<>();
        for (SubscriptionInfo info : availableList) {
            if (info.getGroupUuid() == null || !info.isOpportunistic()) {
                ParcelUuid groupUuid = info.getGroupUuid();
                if (groupUuid == null) {
                    selectableList.add(info);
                } else if (!groupMap.containsKey(groupUuid) || (groupMap.get(groupUuid).getSimSlotIndex() == -1 && info.getSimSlotIndex() != -1)) {
                    selectableList.remove(groupMap.get(groupUuid));
                    selectableList.add(info);
                    groupMap.put(groupUuid, info);
                }
            }
        }
        return selectableList;
    }

    @SystemApi
    public boolean setSubscriptionEnabled(int subscriptionId, boolean enable) {
        try {
            ISub iSub = TelephonyManager.getSubscriptionService();
            if (iSub != null) {
                iSub.setUiccApplicationsEnabled(enable, subscriptionId);
                return true;
            }
            return true;
        } catch (RemoteException e) {
            return false;
        }
    }

    public boolean semSetSubscriptionEnabled(int subscriptionId, boolean enable) {
        return setSubscriptionEnabled(subscriptionId, enable);
    }

    @SystemApi
    public void setUiccApplicationsEnabled(int subscriptionId, boolean enabled) {
        try {
            ISub iSub = TelephonyManager.getSubscriptionService();
            if (iSub != null) {
                iSub.setUiccApplicationsEnabled(enabled, subscriptionId);
            }
        } catch (RemoteException e) {
        }
    }

    @SystemApi
    public boolean canDisablePhysicalSubscription() {
        try {
            ISub iSub = TelephonyManager.getSubscriptionService();
            if (iSub != null) {
                return iSub.canDisablePhysicalSubscription();
            }
            return false;
        } catch (RemoteException e) {
            return false;
        }
    }

    @SystemApi
    public boolean isSubscriptionEnabled(int subscriptionId) {
        try {
            ISub iSub = TelephonyManager.getSubscriptionService();
            if (iSub != null) {
                return iSub.isSubscriptionEnabled(subscriptionId);
            }
            return false;
        } catch (RemoteException e) {
            return false;
        }
    }

    public boolean semIsSubscriptionEnabled(int subscriptionId) {
        return isSubscriptionEnabled(subscriptionId);
    }

    public void setDeviceToDeviceStatusSharingPreference(final int subscriptionId, final int sharing) {
        setSubscriptionPropertyHelper(subscriptionId, "setDeviceToDeviceSharingStatus", new CallISubMethodHelper() { // from class: android.telephony.SubscriptionManager$$ExternalSyntheticLambda1
            @Override // android.telephony.SubscriptionManager.CallISubMethodHelper
            public final int callMethod(ISub iSub) {
                int deviceToDeviceStatusSharing;
                deviceToDeviceStatusSharing = iSub.setDeviceToDeviceStatusSharing(sharing, subscriptionId);
                return deviceToDeviceStatusSharing;
            }
        });
    }

    public int getDeviceToDeviceStatusSharingPreference(int subscriptionId) {
        return getIntegerSubscriptionProperty(subscriptionId, "d2d_sharing_status", 0, this.mContext);
    }

    public void setDeviceToDeviceStatusSharingContacts(final int subscriptionId, final List<Uri> contacts) {
        serializeUriLists(contacts);
        setSubscriptionPropertyHelper(subscriptionId, "setDeviceToDeviceSharingStatus", new CallISubMethodHelper() { // from class: android.telephony.SubscriptionManager$$ExternalSyntheticLambda8
            @Override // android.telephony.SubscriptionManager.CallISubMethodHelper
            public final int callMethod(ISub iSub) {
                int deviceToDeviceStatusSharingContacts;
                deviceToDeviceStatusSharingContacts = iSub.setDeviceToDeviceStatusSharingContacts(SubscriptionManager.serializeUriLists(contacts), subscriptionId);
                return deviceToDeviceStatusSharingContacts;
            }
        });
    }

    public List<Uri> getDeviceToDeviceStatusSharingContacts(int subscriptionId) {
        String result = getStringSubscriptionProperty(this.mContext, subscriptionId, "d2d_sharing_contacts");
        if (result != null) {
            try {
                byte[] b = Base64.decode(result, 0);
                ByteArrayInputStream bis = new ByteArrayInputStream(b);
                ObjectInputStream ois = new ObjectInputStream(bis);
                List<String> contacts = (List) ArrayList.class.cast(ois.readObject());
                List<Uri> uris = new ArrayList<>();
                for (String contact : contacts) {
                    uris.add(Uri.parse(contact));
                }
                return uris;
            } catch (IOException e) {
                logd("getDeviceToDeviceStatusSharingContacts IO exception");
            } catch (ClassNotFoundException e2) {
                logd("getDeviceToDeviceStatusSharingContacts ClassNotFound exception");
            }
        }
        return new ArrayList();
    }

    @SystemApi
    public int getEnabledSubscriptionId(int slotIndex) {
        try {
            ISub iSub = TelephonyManager.getSubscriptionService();
            if (iSub == null) {
                return -1;
            }
            int subId = iSub.getEnabledSubscriptionId(slotIndex);
            return subId;
        } catch (RemoteException e) {
            return -1;
        }
    }

    private int setSubscriptionPropertyHelper(int subId, String methodName, CallISubMethodHelper helper) {
        if (!isValidSubscriptionId(subId)) {
            logd(NavigationBarInflaterView.SIZE_MOD_START + methodName + "]- fail");
            return -1;
        }
        try {
            ISub iSub = TelephonyManager.getSubscriptionService();
            if (iSub == null) {
                return 0;
            }
            int result = helper.callMethod(iSub);
            return result;
        } catch (RemoteException e) {
            return 0;
        }
    }

    public static int getActiveDataSubscriptionId() {
        return sGetActiveDataSubscriptionIdCache.query((Void) null).intValue();
    }

    public static void putSubscriptionIdExtra(Intent intent, int subId) {
        intent.putExtra("android.telephony.extra.SUBSCRIPTION_INDEX", subId);
        intent.putExtra(PhoneConstants.SUBSCRIPTION_KEY, subId);
    }

    public static void invalidateSubscriptionManagerServiceCaches() {
        PropertyInvalidatedCache.invalidateCache(CACHE_KEY_SUBSCRIPTION_MANAGER_SERVICE_PROPERTY);
    }

    public static void disableCaching() {
        sGetDefaultSubIdCacheAsUser.disableLocal();
        sGetDefaultDataSubIdCache.disableLocal();
        sGetActiveDataSubscriptionIdCache.disableLocal();
        sGetDefaultSmsSubIdCacheAsUser.disableLocal();
        sGetSlotIndexCache.disableLocal();
        sGetSubIdCache.disableLocal();
        sGetPhoneIdCache.disableLocal();
    }

    public static void clearCaches() {
        sGetDefaultSubIdCacheAsUser.clear();
        sGetDefaultDataSubIdCache.clear();
        sGetActiveDataSubscriptionIdCache.clear();
        sGetDefaultSmsSubIdCacheAsUser.clear();
        sGetSlotIndexCache.clear();
        sGetSubIdCache.clear();
        sGetPhoneIdCache.clear();
    }

    @SystemApi
    public byte[] getAllSimSpecificSettingsForBackup() {
        Bundle bundle = this.mContext.getContentResolver().call(SIM_INFO_BACKUP_AND_RESTORE_CONTENT_URI, GET_SIM_SPECIFIC_SETTINGS_METHOD_NAME, (String) null, (Bundle) null);
        return bundle.getByteArray(KEY_SIM_SPECIFIC_SETTINGS_DATA);
    }

    @SystemApi
    public void restoreAllSimSpecificSettingsFromBackup(byte[] data) {
        try {
            ISub iSub = TelephonyManager.getSubscriptionService();
            if (iSub != null) {
                iSub.restoreAllSimSpecificSettingsFromBackup(data);
                return;
            }
            throw new IllegalStateException("subscription service unavailable.");
        } catch (RemoteException ex) {
            if (!isSystemProcess()) {
                ex.rethrowAsRuntimeException();
            }
        }
    }

    public String getPhoneNumber(int subscriptionId, int source) {
        if (subscriptionId == Integer.MAX_VALUE) {
            subscriptionId = getDefaultSubscriptionId();
        }
        if (source != 1 && source != 2 && source != 3) {
            throw new IllegalArgumentException("invalid source " + source);
        }
        try {
            ISub iSub = TelephonyManager.getSubscriptionService();
            if (iSub != null) {
                return iSub.getPhoneNumber(subscriptionId, source, this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
            }
            throw new IllegalStateException("subscription service unavailable.");
        } catch (RemoteException ex) {
            throw ex.rethrowAsRuntimeException();
        }
    }

    public String getPhoneNumber(int subscriptionId) {
        if (subscriptionId == Integer.MAX_VALUE) {
            subscriptionId = getDefaultSubscriptionId();
        }
        try {
            ISub iSub = TelephonyManager.getSubscriptionService();
            if (iSub != null) {
                return iSub.getPhoneNumberFromFirstAvailableSource(subscriptionId, this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
            }
            throw new IllegalStateException("subscription service unavailable.");
        } catch (RemoteException ex) {
            throw ex.rethrowAsRuntimeException();
        }
    }

    public void setCarrierPhoneNumber(int subscriptionId, String number) {
        if (subscriptionId == Integer.MAX_VALUE) {
            subscriptionId = getDefaultSubscriptionId();
        }
        if (number == null) {
            throw new NullPointerException("invalid number null");
        }
        try {
            ISub iSub = TelephonyManager.getSubscriptionService();
            if (iSub != null) {
                iSub.setPhoneNumber(subscriptionId, 2, number, this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
                return;
            }
            throw new IllegalStateException("subscription service unavailable.");
        } catch (RemoteException ex) {
            throw ex.rethrowAsRuntimeException();
        }
    }

    void setUsageSetting(final int subscriptionId, final int usageSetting) {
        setSubscriptionPropertyHelper(subscriptionId, "setUsageSetting", new CallISubMethodHelper() { // from class: android.telephony.SubscriptionManager$$ExternalSyntheticLambda3
            @Override // android.telephony.SubscriptionManager.CallISubMethodHelper
            public final int callMethod(ISub iSub) {
                int lambda$setUsageSetting$13;
                lambda$setUsageSetting$13 = SubscriptionManager.this.lambda$setUsageSetting$13(usageSetting, subscriptionId, iSub);
                return lambda$setUsageSetting$13;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$setUsageSetting$13(int usageSetting, int subscriptionId, ISub iSub) throws RemoteException {
        return iSub.setUsageSetting(usageSetting, subscriptionId, this.mContext.getOpPackageName());
    }

    public static String phoneNumberSourceToString(int source) {
        switch (source) {
            case 1:
                return "UICC";
            case 2:
                return "CARRIER";
            case 3:
                return "IMS";
            default:
                return "UNKNOWN(" + source + NavigationBarInflaterView.KEY_CODE_END;
        }
    }

    public static String displayNameSourceToString(int source) {
        switch (source) {
            case -1:
                return "UNKNOWN";
            case 0:
                return "CARRIER_ID";
            case 1:
                return "SIM_SPN";
            case 2:
                return "USER_INPUT";
            case 3:
                return "CARRIER";
            case 4:
                return "SIM_PNN";
            default:
                return "UNKNOWN(" + source + NavigationBarInflaterView.KEY_CODE_END;
        }
    }

    public static String subscriptionTypeToString(int type) {
        switch (type) {
            case 0:
                return "LOCAL_SIM";
            case 1:
                return "REMOTE_SIM";
            default:
                return "UNKNOWN(" + type + NavigationBarInflaterView.KEY_CODE_END;
        }
    }

    public static String usageSettingToString(int usageSetting) {
        switch (usageSetting) {
            case -1:
                return "UNKNOWN";
            case 0:
                return "DEFAULT";
            case 1:
                return "VOICE_CENTRIC";
            case 2:
                return "DATA_CENTRIC";
            default:
                return "UNKNOWN(" + usageSetting + NavigationBarInflaterView.KEY_CODE_END;
        }
    }

    public void setGroupOwner(int subscriptionId, String groupOwner) {
        try {
            ISub iSub = TelephonyManager.getSubscriptionService();
            if (iSub != null) {
                iSub.setGroupOwner(subscriptionId, groupOwner);
                return;
            }
            throw new IllegalStateException("[setGroupOwner]: subscription service unavailable");
        } catch (RemoteException ex) {
            ex.rethrowAsRuntimeException();
        }
    }

    public void setSubscriptionUserHandle(int subscriptionId, UserHandle userHandle) {
        if (!isValidSubscriptionId(subscriptionId)) {
            throw new IllegalArgumentException("[setSubscriptionUserHandle]: Invalid subscriptionId: " + subscriptionId);
        }
        try {
            ISub iSub = TelephonyManager.getSubscriptionService();
            if (iSub != null) {
                iSub.setSubscriptionUserHandle(userHandle, subscriptionId);
                return;
            }
            throw new IllegalStateException("[setSubscriptionUserHandle]: subscription service unavailable");
        } catch (RemoteException ex) {
            ex.rethrowAsRuntimeException();
        }
    }

    public UserHandle getSubscriptionUserHandle(int subscriptionId) {
        if (!isValidSubscriptionId(subscriptionId)) {
            throw new IllegalArgumentException("[getSubscriptionUserHandle]: Invalid subscriptionId: " + subscriptionId);
        }
        try {
            ISub iSub = TelephonyManager.getSubscriptionService();
            if (iSub != null) {
                return iSub.getSubscriptionUserHandle(subscriptionId);
            }
            Log.e(LOG_TAG, "[getSubscriptionUserHandle]: subscription service unavailable");
            return null;
        } catch (RemoteException ex) {
            ex.rethrowAsRuntimeException();
            return null;
        }
    }

    public boolean isSubscriptionAssociatedWithUser(int subscriptionId, UserHandle userHandle) {
        if (!isValidSubscriptionId(subscriptionId)) {
            throw new IllegalArgumentException("[isSubscriptionAssociatedWithUser]: Invalid subscriptionId: " + subscriptionId);
        }
        try {
            ISub iSub = TelephonyManager.getSubscriptionService();
            if (iSub != null) {
                return iSub.isSubscriptionAssociatedWithUser(subscriptionId, userHandle);
            }
            Log.e(LOG_TAG, "[isSubscriptionAssociatedWithUser]: subscription service unavailable");
            return false;
        } catch (RemoteException ex) {
            ex.rethrowAsRuntimeException();
            return false;
        }
    }

    public boolean isSubscriptionAssociatedWithUser(int subscriptionId) {
        if (!isValidSubscriptionId(subscriptionId)) {
            throw new IllegalArgumentException("[isSubscriptionAssociatedWithCallingUser]: Invalid subscriptionId: " + subscriptionId);
        }
        try {
            ISub iSub = TelephonyManager.getSubscriptionService();
            if (iSub != null) {
                return iSub.isSubscriptionAssociatedWithCallingUser(subscriptionId);
            }
            throw new IllegalStateException("subscription service unavailable.");
        } catch (RemoteException ex) {
            ex.rethrowAsRuntimeException();
            return false;
        }
    }

    public List<SubscriptionInfo> getSubscriptionInfoListAssociatedWithUser(UserHandle userHandle) {
        ISub iSub;
        try {
            iSub = TelephonyManager.getSubscriptionService();
        } catch (RemoteException ex) {
            ex.rethrowAsRuntimeException();
        }
        if (iSub != null) {
            return iSub.getSubscriptionInfoListAssociatedWithUser(userHandle);
        }
        Log.e(LOG_TAG, "[getSubscriptionInfoListAssociatedWithUser]: subscription service unavailable");
        return new ArrayList();
    }

    public static int getAllServiceCapabilityBitmasks() {
        return SERVICE_CAPABILITY_VOICE_BITMASK | SERVICE_CAPABILITY_SMS_BITMASK | SERVICE_CAPABILITY_DATA_BITMASK;
    }

    public static Set<Integer> getServiceCapabilitiesSet(int combinedServiceCapabilities) {
        Set<Integer> capabilities = new HashSet<>();
        for (int i = 1; i <= 3; i++) {
            int capabilityBitmask = serviceCapabilityToBitmask(i);
            if ((combinedServiceCapabilities & capabilityBitmask) == capabilityBitmask) {
                capabilities.add(Integer.valueOf(i));
            }
        }
        return Collections.unmodifiableSet(capabilities);
    }

    public static int serviceCapabilityToBitmask(int capability) {
        return 1 << (capability - 1);
    }

    @SystemApi
    public void setTransferStatus(int subscriptionId, int status) {
        try {
            ISub iSub = TelephonyManager.getSubscriptionService();
            if (iSub != null) {
                iSub.setTransferStatus(subscriptionId, status);
            }
        } catch (RemoteException ex) {
            logd("setTransferStatus for subId = " + subscriptionId + " failed.");
            throw ex.rethrowFromSystemServer();
        }
    }
}
