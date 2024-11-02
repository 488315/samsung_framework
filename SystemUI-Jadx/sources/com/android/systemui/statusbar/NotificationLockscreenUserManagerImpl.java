package com.android.systemui.statusbar;

import android.app.ActivityOptions;
import android.app.KeyguardManager;
import android.app.admin.DevicePolicyManager;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.UserInfo;
import android.os.Handler;
import android.os.RemoteException;
import android.os.UserManager;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import com.android.internal.statusbar.NotificationVisibility;
import com.android.internal.widget.LockPatternUtils;
import com.android.systemui.DejankUtils;
import com.android.systemui.Dumpable;
import com.android.systemui.NotiRune;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.doze.PluginAODManager;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.FeatureFlagsRelease;
import com.android.systemui.flags.Flags;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.recents.OverviewProxyService;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.notification.collection.NotifLiveDataStoreImpl;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.statusbar.notification.collection.provider.NotificationVisibilityProviderImpl;
import com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider;
import com.android.systemui.statusbar.phone.StatusBarNotificationPresenter;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.ListenerSet;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.settings.SecureSettings;
import com.samsung.android.knox.SemPersonaManager;
import dagger.Lazy;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class NotificationLockscreenUserManagerImpl implements Dumpable, NotificationLockscreenUserManager, StatusBarStateController.StateListener {
    public boolean mAllowLockscreenRemoteInput;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final NotificationClickNotifier mClickNotifier;
    public final Lazy mCommonNotifCollectionLazy;
    public final Context mContext;
    public int mCurrentUserId;
    public final DevicePolicyManager mDevicePolicyManager;
    public final DeviceProvisionedController mDeviceProvisionedController;
    public final FeatureFlags mFeatureFlags;
    public final KeyguardManager mKeyguardManager;
    public final KeyguardStateController mKeyguardStateController;
    public final LockPatternUtils mLockPatternUtils;
    public AnonymousClass4 mLockscreenSettingsObserver;
    public final Handler mMainHandler;
    public final Lazy mOverviewProxyServiceLazy;
    public Lazy mPluginAODManagerLazy;
    public NotificationPresenter mPresenter;
    public final SecureSettings mSecureSettings;
    public final Lazy mSettingsHelperLazy;
    public AnonymousClass5 mSettingsObserver;
    public boolean mShowLockscreenNotifications;
    public int mShowNotifications;
    public int mShowPrivateNotifications;
    public final UserManager mUserManager;
    public final UserTracker mUserTracker;
    public final Lazy mVisibilityProviderLazy;
    public final Object mLock = new Object();
    public final SparseBooleanArray mLockscreenPublicMode = new SparseBooleanArray();
    public final SparseBooleanArray mUsersWithSeparateWorkChallenge = new SparseBooleanArray();
    public final SparseBooleanArray mUsersAllowingPrivateNotifications = new SparseBooleanArray();
    public final SparseBooleanArray mUsersAllowingNotifications = new SparseBooleanArray();
    public final SparseBooleanArray mUsersInLockdownLatestResult = new SparseBooleanArray();
    public final SparseBooleanArray mShouldHideNotifsLatestResult = new SparseBooleanArray();
    public final List mListeners = new ArrayList();
    public int mState = 0;
    public final ListenerSet mNotifStateChangedListeners = new ListenerSet();
    public final AnonymousClass1 mAllUsersReceiver = new BroadcastReceiver() { // from class: com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl.1
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if ("android.app.action.DEVICE_POLICY_MANAGER_STATE_CHANGED".equals(intent.getAction()) && NotificationLockscreenUserManagerImpl.this.isCurrentProfile(getSendingUserId())) {
                NotificationLockscreenUserManagerImpl.this.mUsersAllowingPrivateNotifications.clear();
                NotificationLockscreenUserManagerImpl.this.updateLockscreenNotificationSetting();
            }
        }
    };
    public final AnonymousClass2 mBaseBroadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl.2
        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            char c;
            NotificationVisibility obtain;
            String action = intent.getAction();
            action.getClass();
            switch (action.hashCode()) {
                case -2061058799:
                    if (action.equals("android.intent.action.USER_REMOVED")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case -1238404651:
                    if (action.equals("android.intent.action.MANAGED_PROFILE_UNAVAILABLE")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case -864107122:
                    if (action.equals("android.intent.action.MANAGED_PROFILE_AVAILABLE")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case -598152660:
                    if (action.equals("com.android.systemui.statusbar.work_challenge_unlocked_notification_action")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 833559602:
                    if (action.equals("android.intent.action.USER_UNLOCKED")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case 1121780209:
                    if (action.equals("android.intent.action.USER_ADDED")) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -1);
                    if (intExtra != -1) {
                        Iterator it = ((ArrayList) NotificationLockscreenUserManagerImpl.this.mListeners).iterator();
                        while (it.hasNext()) {
                            ((NotificationLockscreenUserManager.UserChangedListener) it.next()).onUserRemoved(intExtra);
                        }
                    }
                    NotificationLockscreenUserManagerImpl.this.updateCurrentProfilesCache();
                    return;
                case 1:
                case 2:
                case 5:
                    NotificationLockscreenUserManagerImpl.this.updateCurrentProfilesCache();
                    return;
                case 3:
                    IntentSender intentSender = (IntentSender) intent.getParcelableExtra("android.intent.extra.INTENT");
                    String stringExtra = intent.getStringExtra("android.intent.extra.INDEX");
                    if (intentSender != null) {
                        try {
                            ActivityOptions makeBasic = ActivityOptions.makeBasic();
                            makeBasic.setPendingIntentBackgroundActivityStartMode(1);
                            NotificationLockscreenUserManagerImpl.this.mContext.startIntentSender(intentSender, null, 0, 0, 0, makeBasic.toBundle());
                        } catch (ActivityNotFoundException | IntentSender.SendIntentException unused) {
                        }
                    }
                    if (stringExtra != null) {
                        NotificationVisibilityProviderImpl notificationVisibilityProviderImpl = (NotificationVisibilityProviderImpl) ((NotificationVisibilityProvider) NotificationLockscreenUserManagerImpl.this.mVisibilityProviderLazy.get());
                        NotificationEntry entry = ((NotifPipeline) notificationVisibilityProviderImpl.notifCollection).getEntry(stringExtra);
                        if (entry != null) {
                            obtain = notificationVisibilityProviderImpl.obtain(entry);
                        } else {
                            obtain = NotificationVisibility.obtain(stringExtra, -1, ((Number) ((NotifLiveDataStoreImpl) notificationVisibilityProviderImpl.notifDataStore).activeNotifCount.getValue()).intValue(), false);
                        }
                        NotificationClickNotifier notificationClickNotifier = NotificationLockscreenUserManagerImpl.this.mClickNotifier;
                        notificationClickNotifier.getClass();
                        try {
                            notificationClickNotifier.barService.onNotificationClick(stringExtra, obtain);
                        } catch (RemoteException unused2) {
                        }
                        notificationClickNotifier.mainExecutor.execute(new NotificationClickNotifier$onNotificationClick$1(notificationClickNotifier, stringExtra));
                        return;
                    }
                    return;
                case 4:
                    if (((OverviewProxyService) NotificationLockscreenUserManagerImpl.this.mOverviewProxyServiceLazy.get()).mOverviewProxy == null) {
                        ((OverviewProxyService) NotificationLockscreenUserManagerImpl.this.mOverviewProxyServiceLazy.get()).startConnectionToCurrentUser();
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    };
    public final AnonymousClass3 mUserChangedCallback = new UserTracker.Callback() { // from class: com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl.3
        public final void handleUserChange(int i) {
            NotificationLockscreenUserManagerImpl notificationLockscreenUserManagerImpl = NotificationLockscreenUserManagerImpl.this;
            notificationLockscreenUserManagerImpl.mCurrentUserId = i;
            notificationLockscreenUserManagerImpl.updateCurrentProfilesCache();
            int i2 = notificationLockscreenUserManagerImpl.mCurrentUserId;
            notificationLockscreenUserManagerImpl.updateLockscreenNotificationSetting();
            notificationLockscreenUserManagerImpl.updatePublicMode();
            ((StatusBarNotificationPresenter) notificationLockscreenUserManagerImpl.mPresenter).onUserSwitched(notificationLockscreenUserManagerImpl.mCurrentUserId);
            Iterator it = ((ArrayList) notificationLockscreenUserManagerImpl.mListeners).iterator();
            while (it.hasNext()) {
                ((NotificationLockscreenUserManager.UserChangedListener) it.next()).onUserChanged(notificationLockscreenUserManagerImpl.mCurrentUserId);
            }
        }

        @Override // com.android.systemui.settings.UserTracker.Callback
        public final void onUserChanged(int i, Context context) {
            if (!((FeatureFlagsRelease) NotificationLockscreenUserManagerImpl.this.mFeatureFlags).isEnabled(Flags.LOAD_NOTIFICATIONS_BEFORE_THE_USER_SWITCH_IS_COMPLETE)) {
                handleUserChange(i);
            }
        }

        @Override // com.android.systemui.settings.UserTracker.Callback
        public final void onUserChanging(int i) {
            if (((FeatureFlagsRelease) NotificationLockscreenUserManagerImpl.this.mFeatureFlags).isEnabled(Flags.LOAD_NOTIFICATIONS_BEFORE_THE_USER_SWITCH_IS_COMPLETE)) {
                handleUserChange(i);
            }
        }
    };
    public final SparseArray mCurrentProfiles = new SparseArray();
    public final SparseArray mCurrentManagedProfiles = new SparseArray();

    /* JADX WARN: Type inference failed for: r3v1, types: [com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl$1] */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl$2] */
    /* JADX WARN: Type inference failed for: r3v3, types: [com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl$3] */
    public NotificationLockscreenUserManagerImpl(Context context, BroadcastDispatcher broadcastDispatcher, DevicePolicyManager devicePolicyManager, UserManager userManager, UserTracker userTracker, Lazy lazy, Lazy lazy2, NotificationClickNotifier notificationClickNotifier, Lazy lazy3, KeyguardManager keyguardManager, StatusBarStateController statusBarStateController, Handler handler, DeviceProvisionedController deviceProvisionedController, KeyguardStateController keyguardStateController, SecureSettings secureSettings, DumpManager dumpManager, LockPatternUtils lockPatternUtils, FeatureFlags featureFlags, Lazy lazy4) {
        this.mCurrentUserId = 0;
        this.mContext = context;
        this.mMainHandler = handler;
        this.mDevicePolicyManager = devicePolicyManager;
        this.mUserManager = userManager;
        this.mUserTracker = userTracker;
        this.mCurrentUserId = ((UserTrackerImpl) userTracker).getUserId();
        this.mVisibilityProviderLazy = lazy;
        this.mCommonNotifCollectionLazy = lazy2;
        this.mClickNotifier = notificationClickNotifier;
        this.mOverviewProxyServiceLazy = lazy3;
        statusBarStateController.addCallback(this);
        this.mLockPatternUtils = lockPatternUtils;
        this.mKeyguardManager = keyguardManager;
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mDeviceProvisionedController = deviceProvisionedController;
        this.mSecureSettings = secureSettings;
        this.mKeyguardStateController = keyguardStateController;
        this.mFeatureFlags = featureFlags;
        if (NotiRune.NOTI_LOCKSCREEN_ALWAYS_HIDE_SENSITIVE) {
            this.mSettingsHelperLazy = lazy4;
        }
        dumpManager.registerDumpable(this);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("NotificationLockscreenUserManager state:");
        printWriter.print("  mCurrentUserId=");
        printWriter.println(this.mCurrentUserId);
        printWriter.print("  mShowLockscreenNotifications=");
        printWriter.println(this.mShowLockscreenNotifications);
        printWriter.print("  mAllowLockscreenRemoteInput=");
        printWriter.println(this.mAllowLockscreenRemoteInput);
        printWriter.print("  mCurrentProfiles=");
        synchronized (this.mLock) {
            for (int size = this.mCurrentProfiles.size() - 1; size >= 0; size += -1) {
                printWriter.print("" + ((UserInfo) this.mCurrentProfiles.valueAt(size)).id + " ");
            }
        }
        printWriter.println();
        printWriter.print("  mCurrentManagedProfiles=");
        synchronized (this.mLock) {
            for (int size2 = this.mCurrentManagedProfiles.size() - 1; size2 >= 0; size2 += -1) {
                printWriter.print("" + ((UserInfo) this.mCurrentManagedProfiles.valueAt(size2)).id + " ");
            }
        }
        printWriter.println();
        printWriter.print("  mLockscreenPublicMode=");
        printWriter.println(this.mLockscreenPublicMode);
        printWriter.print("  mUsersWithSeparateWorkChallenge=");
        printWriter.println(this.mUsersWithSeparateWorkChallenge);
        printWriter.print("  mUsersAllowingPrivateNotifications=");
        printWriter.println(this.mUsersAllowingPrivateNotifications);
        printWriter.print("  mUsersAllowingNotifications=");
        printWriter.println(this.mUsersAllowingNotifications);
        printWriter.print("  mUsersInLockdownLatestResult=");
        printWriter.println(this.mUsersInLockdownLatestResult);
        printWriter.print("  mShouldHideNotifsLatestResult=");
        printWriter.println(this.mShouldHideNotifsLatestResult);
    }

    public final boolean isAnyProfilePublicMode() {
        synchronized (this.mLock) {
            for (int size = this.mCurrentProfiles.size() - 1; size >= 0; size--) {
                if (isLockscreenPublicMode(((UserInfo) this.mCurrentProfiles.valueAt(size)).id)) {
                    return true;
                }
            }
            return false;
        }
    }

    public final boolean isCurrentProfile(int i) {
        boolean z;
        synchronized (this.mLock) {
            if (i != -1) {
                try {
                    if (this.mCurrentProfiles.get(i) == null) {
                        z = false;
                    }
                } finally {
                }
            }
            z = true;
        }
        return z;
    }

    public final boolean isLockscreenPublicMode(int i) {
        SparseBooleanArray sparseBooleanArray = this.mLockscreenPublicMode;
        if (i == -1) {
            return sparseBooleanArray.get(this.mCurrentUserId, false);
        }
        return sparseBooleanArray.get(i, false);
    }

    public final boolean needsRedaction(NotificationEntry notificationEntry) {
        boolean z;
        boolean z2;
        int userId = notificationEntry.mSbn.getUserId();
        boolean z3 = !userAllowsPrivateNotificationsInPublic(this.mCurrentUserId);
        boolean contains = this.mCurrentManagedProfiles.contains(userId);
        boolean z4 = !userAllowsPrivateNotificationsInPublic(userId);
        if ((contains || !z3) && !z4 && (!z3 || !isLockscreenPublicMode(this.mCurrentUserId) || !SemPersonaManager.isSecureFolderId(userId))) {
            z = false;
        } else {
            z = true;
        }
        if ((NotiRune.NOTI_LOCKSCREEN_ALWAYS_HIDE_SENSITIVE && ((SettingsHelper) this.mSettingsHelperLazy.get()).isAllowPrivateNotificationsWhenUnsecure(userId)) || notificationEntry.mSbn.getNotification().visibility == 0) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (packageHasVisibilityOverride(notificationEntry.mSbn.getKey())) {
            return true;
        }
        if (z2 && z) {
            return true;
        }
        return false;
    }

    @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
    public final void onStateChanged(int i) {
        this.mState = i;
        updatePublicMode();
    }

    public final boolean packageHasVisibilityOverride(String str) {
        Lazy lazy = this.mCommonNotifCollectionLazy;
        if (lazy.get() == null) {
            Log.wtf("LockscreenUserManager", "mEntryManager was null!", new Throwable());
            return true;
        }
        NotificationEntry entry = ((NotifPipeline) ((CommonNotifCollection) lazy.get())).getEntry(str);
        if (entry != null && entry.mRanking.getLockscreenVisibilityOverride() == 0) {
            return true;
        }
        return false;
    }

    public void setLockscreenPublicMode(boolean z, int i) {
        this.mLockscreenPublicMode.put(i, z);
    }

    public final void updateCurrentProfilesCache() {
        synchronized (this.mLock) {
            this.mCurrentProfiles.clear();
            this.mCurrentManagedProfiles.clear();
            UserManager userManager = this.mUserManager;
            if (userManager != null) {
                for (UserInfo userInfo : userManager.getProfiles(this.mCurrentUserId)) {
                    this.mCurrentProfiles.put(userInfo.id, userInfo);
                    if ("android.os.usertype.profile.MANAGED".equals(userInfo.userType)) {
                        this.mCurrentManagedProfiles.put(userInfo.id, userInfo);
                    }
                }
            }
        }
        this.mMainHandler.post(new Runnable() { // from class: com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                NotificationLockscreenUserManagerImpl notificationLockscreenUserManagerImpl = NotificationLockscreenUserManagerImpl.this;
                Iterator it = ((ArrayList) notificationLockscreenUserManagerImpl.mListeners).iterator();
                while (it.hasNext()) {
                    ((NotificationLockscreenUserManager.UserChangedListener) it.next()).onCurrentProfilesChanged(notificationLockscreenUserManagerImpl.mCurrentProfiles);
                }
            }
        });
    }

    public final void updateLockscreenNotificationSetting() {
        boolean z;
        boolean z2;
        boolean z3;
        int i = this.mCurrentUserId;
        SecureSettings secureSettings = this.mSecureSettings;
        boolean z4 = true;
        this.mShowNotifications = secureSettings.getIntForUser(1, i, "lock_screen_show_notifications");
        this.mShowPrivateNotifications = secureSettings.getIntForUser(0, this.mCurrentUserId, "lock_screen_allow_private_notifications");
        if (this.mShowNotifications != 0) {
            z = true;
        } else {
            z = false;
        }
        if ((this.mDevicePolicyManager.getKeyguardDisabledFeatures(null, this.mCurrentUserId) & 4) == 0) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (z && z2) {
            z3 = true;
        } else {
            z3 = false;
        }
        this.mShowLockscreenNotifications = z3;
        PluginAODManager pluginAODManager = (PluginAODManager) this.mPluginAODManagerLazy.get();
        if (!z || !z2) {
            z4 = false;
        }
        pluginAODManager.mShowAODNotifications = z4;
        this.mAllowLockscreenRemoteInput = false;
    }

    public final void updatePublicMode() {
        boolean z;
        boolean z2;
        if (this.mState == 0 && !((KeyguardStateControllerImpl) this.mKeyguardStateController).mShowing) {
            z = false;
        } else {
            z = true;
        }
        SparseArray sparseArray = this.mCurrentProfiles;
        SparseBooleanArray sparseBooleanArray = this.mLockscreenPublicMode;
        SparseBooleanArray clone = sparseBooleanArray.clone();
        SparseBooleanArray sparseBooleanArray2 = this.mUsersWithSeparateWorkChallenge;
        SparseBooleanArray clone2 = sparseBooleanArray2.clone();
        sparseBooleanArray2.clear();
        for (int size = sparseArray.size() - 1; size >= 0; size--) {
            final int i = ((UserInfo) sparseArray.valueAt(size)).id;
            boolean booleanValue = ((Boolean) DejankUtils.whitelistIpcs(new Supplier() { // from class: com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl$$ExternalSyntheticLambda0
                @Override // java.util.function.Supplier
                public final Object get() {
                    NotificationLockscreenUserManagerImpl notificationLockscreenUserManagerImpl = NotificationLockscreenUserManagerImpl.this;
                    return Boolean.valueOf(notificationLockscreenUserManagerImpl.mLockPatternUtils.isSeparateProfileChallengeEnabled(i));
                }
            })).booleanValue();
            if (!z && i != this.mCurrentUserId && booleanValue && this.mLockPatternUtils.isSecure(i)) {
                if (!z && !this.mKeyguardManager.isDeviceLocked(i)) {
                    z2 = false;
                } else {
                    z2 = true;
                }
            } else {
                z2 = z;
            }
            setLockscreenPublicMode(z2, i);
            sparseBooleanArray2.put(i, booleanValue);
        }
        if (!sparseBooleanArray.equals(clone) || !sparseBooleanArray2.equals(clone2)) {
            Iterator it = this.mNotifStateChangedListeners.iterator();
            while (it.hasNext()) {
                ((NotificationLockscreenUserManager.NotificationStateChangedListener) it.next()).onNotificationStateChanged();
            }
        }
    }

    public final boolean userAllowsNotificationsInPublic(int i) {
        int intForUser;
        boolean z;
        boolean z2;
        boolean z3 = true;
        if (isCurrentProfile(i) && i != this.mCurrentUserId) {
            return true;
        }
        SparseBooleanArray sparseBooleanArray = this.mUsersAllowingNotifications;
        if (sparseBooleanArray.indexOfKey(i) < 0) {
            if (i == this.mCurrentUserId) {
                intForUser = this.mShowNotifications;
            } else {
                intForUser = this.mSecureSettings.getIntForUser(0, i, "lock_screen_show_notifications");
            }
            if (intForUser != 0) {
                z = true;
            } else {
                z = false;
            }
            if (i == -1 || (this.mDevicePolicyManager.getKeyguardDisabledFeatures(null, i) & 4) == 0) {
                z2 = true;
            } else {
                z2 = false;
            }
            boolean privateNotificationsAllowed = this.mKeyguardManager.getPrivateNotificationsAllowed();
            if (!z || !z2 || !privateNotificationsAllowed) {
                z3 = false;
            }
            sparseBooleanArray.append(i, z3);
            return z3;
        }
        return sparseBooleanArray.get(i);
    }

    public final boolean userAllowsPrivateNotificationsInPublic(int i) {
        int intForUser;
        boolean z;
        boolean z2;
        boolean z3 = true;
        if (i == -1) {
            return true;
        }
        SparseBooleanArray sparseBooleanArray = this.mUsersAllowingPrivateNotifications;
        if (sparseBooleanArray.indexOfKey(i) < 0) {
            if (i == this.mCurrentUserId) {
                intForUser = this.mShowPrivateNotifications;
            } else {
                intForUser = this.mSecureSettings.getIntForUser(0, i, "lock_screen_allow_private_notifications");
            }
            if (intForUser != 0) {
                z = true;
            } else {
                z = false;
            }
            if (i == -1 || (this.mDevicePolicyManager.getKeyguardDisabledFeatures(null, i) & 8) == 0) {
                z2 = true;
            } else {
                z2 = false;
            }
            if (!z || !z2) {
                z3 = false;
            }
            sparseBooleanArray.append(i, z3);
            return z3;
        }
        return sparseBooleanArray.get(i);
    }
}
