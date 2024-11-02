package com.android.systemui.tuner;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.UserInfo;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.Looper;
import android.os.UserManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import com.android.internal.util.ArrayUtils;
import com.android.systemui.DejankUtils;
import com.android.systemui.R;
import com.android.systemui.demomode.DemoModeController;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.phone.StatusBarIconController;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.leak.LeakDetector;
import com.android.systemui.util.leak.TrackedCollections;
import com.android.systemui.util.settings.GlobalSettings;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public class TunerServiceImpl extends TunerService {
    public static final String[] RESET_EXCEPTION_LIST = {"sysui_qs_tiles", "doze_always_on", "qs_media_resumption", "qs_media_recommend"};
    public final ContentResolver mContentResolver;
    public final Context mContext;
    public int mCurrentUser;
    public UserTracker.Callback mCurrentUserTracker;
    public final DemoModeController mDemoModeController;
    public final LeakDetector mLeakDetector;
    public final ArrayMap mListeningUris;
    public final Observer mObserver;
    public final ConcurrentHashMap mTunableLookup;
    public final HashSet mTunables;
    public final ComponentName mTunerComponent;
    public final UserTracker mUserTracker;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public class Observer extends ContentObserver {
        public Observer() {
            super(new Handler(Looper.getMainLooper()));
        }

        public final void onChange(boolean z, Collection collection, int i, int i2) {
            if (i2 == ((UserTrackerImpl) TunerServiceImpl.this.mUserTracker).getUserId()) {
                Iterator it = collection.iterator();
                while (it.hasNext()) {
                    Uri uri = (Uri) it.next();
                    TunerServiceImpl tunerServiceImpl = TunerServiceImpl.this;
                    String str = (String) tunerServiceImpl.mListeningUris.get(uri);
                    Set<TunerService.Tunable> set = (Set) tunerServiceImpl.mTunableLookup.get(str);
                    if (set != null) {
                        String stringForUser = Settings.Secure.getStringForUser(tunerServiceImpl.mContentResolver, str, tunerServiceImpl.mCurrentUser);
                        for (TunerService.Tunable tunable : set) {
                            if (tunable != null) {
                                tunable.onTuningChanged(str, stringForUser);
                            }
                        }
                    }
                }
            }
        }
    }

    public TunerServiceImpl(Context context, Handler handler, LeakDetector leakDetector, DemoModeController demoModeController, UserTracker userTracker) {
        super(context);
        HashSet hashSet;
        String value;
        this.mObserver = new Observer();
        this.mListeningUris = new ArrayMap();
        this.mTunableLookup = new ConcurrentHashMap();
        if (LeakDetector.ENABLED) {
            hashSet = new HashSet();
        } else {
            hashSet = null;
        }
        this.mTunables = hashSet;
        this.mContext = context;
        this.mContentResolver = context.getContentResolver();
        this.mLeakDetector = leakDetector;
        this.mDemoModeController = demoModeController;
        this.mUserTracker = userTracker;
        this.mTunerComponent = new ComponentName(context, (Class<?>) TunerActivity.class);
        Iterator it = UserManager.get(context).getUsers().iterator();
        while (it.hasNext()) {
            this.mCurrentUser = ((UserInfo) it.next()).getUserHandle().getIdentifier();
            if (getValue(0, "sysui_tuner_version") != 4) {
                int value2 = getValue(0, "sysui_tuner_version");
                if (value2 < 1 && (value = getValue("icon_blacklist")) != null) {
                    ArraySet iconHideList = StatusBarIconController.getIconHideList(this.mContext, value);
                    iconHideList.add("rotate");
                    iconHideList.add("headset");
                    Settings.Secure.putStringForUser(this.mContentResolver, "icon_blacklist", TextUtils.join(",", iconHideList), this.mCurrentUser);
                }
                if (value2 < 2) {
                    ((UserTrackerImpl) this.mUserTracker).getUserContext().getPackageManager().setComponentEnabledSetting(this.mTunerComponent, 2, 1);
                }
                if (value2 < 4) {
                    final int i = this.mCurrentUser;
                    handler.postDelayed(new Runnable() { // from class: com.android.systemui.tuner.TunerServiceImpl$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            TunerServiceImpl.this.clearAllFromUser(i);
                        }
                    }, 5000L);
                }
                setValue(4, "sysui_tuner_version");
            }
        }
        this.mCurrentUser = ((UserTrackerImpl) this.mUserTracker).getUserId();
        UserTracker.Callback callback = new UserTracker.Callback() { // from class: com.android.systemui.tuner.TunerServiceImpl.1
            @Override // com.android.systemui.settings.UserTracker.Callback
            public final void onUserChanged(int i2, Context context2) {
                TunerServiceImpl tunerServiceImpl = TunerServiceImpl.this;
                tunerServiceImpl.mCurrentUser = i2;
                ConcurrentHashMap concurrentHashMap = tunerServiceImpl.mTunableLookup;
                for (String str : concurrentHashMap.keySet()) {
                    String stringForUser = Settings.Secure.getStringForUser(tunerServiceImpl.mContentResolver, str, tunerServiceImpl.mCurrentUser);
                    Iterator it2 = ((Set) concurrentHashMap.get(str)).iterator();
                    while (it2.hasNext()) {
                        ((TunerService.Tunable) it2.next()).onTuningChanged(str, stringForUser);
                    }
                }
                ArrayMap arrayMap = tunerServiceImpl.mListeningUris;
                if (arrayMap.size() != 0) {
                    ContentResolver contentResolver = tunerServiceImpl.mContentResolver;
                    Observer observer = tunerServiceImpl.mObserver;
                    contentResolver.unregisterContentObserver(observer);
                    Iterator it3 = arrayMap.keySet().iterator();
                    while (it3.hasNext()) {
                        contentResolver.registerContentObserver((Uri) it3.next(), false, observer, tunerServiceImpl.mCurrentUser);
                    }
                }
            }
        };
        this.mCurrentUserTracker = callback;
        ((UserTrackerImpl) this.mUserTracker).addCallback(callback, new HandlerExecutor(handler));
    }

    @Override // com.android.systemui.tuner.TunerService
    public final void addTunable(TunerService.Tunable tunable, String... strArr) {
        for (final String str : strArr) {
            ConcurrentHashMap concurrentHashMap = this.mTunableLookup;
            if (!concurrentHashMap.containsKey(str)) {
                concurrentHashMap.put(str, new ArraySet());
            }
            ((Set) concurrentHashMap.get(str)).add(tunable);
            if (LeakDetector.ENABLED) {
                HashSet hashSet = this.mTunables;
                hashSet.add(tunable);
                TrackedCollections trackedCollections = this.mLeakDetector.mTrackedCollections;
                if (trackedCollections != null) {
                    trackedCollections.track(hashSet, "TunerService.mTunables");
                }
            }
            Uri uriFor = Settings.Secure.getUriFor(str);
            ArrayMap arrayMap = this.mListeningUris;
            if (!arrayMap.containsKey(uriFor)) {
                arrayMap.put(uriFor, str);
                this.mContentResolver.registerContentObserver(uriFor, false, this.mObserver, this.mCurrentUser);
            }
            tunable.onTuningChanged(str, (String) DejankUtils.whitelistIpcs(new Supplier() { // from class: com.android.systemui.tuner.TunerServiceImpl$$ExternalSyntheticLambda1
                @Override // java.util.function.Supplier
                public final Object get() {
                    TunerServiceImpl tunerServiceImpl = TunerServiceImpl.this;
                    return Settings.Secure.getStringForUser(tunerServiceImpl.mContentResolver, str, tunerServiceImpl.mCurrentUser);
                }
            }));
        }
    }

    @Override // com.android.systemui.tuner.TunerService
    public final void clearAll() {
        clearAllFromUser(this.mCurrentUser);
    }

    public final void clearAllFromUser(int i) {
        DemoModeController demoModeController = this.mDemoModeController;
        GlobalSettings globalSettings = demoModeController.globalSettings;
        globalSettings.putIntForUser(0, globalSettings.getUserId(), "sysui_tuner_demo_on");
        GlobalSettings globalSettings2 = demoModeController.globalSettings;
        globalSettings2.putIntForUser(0, globalSettings2.getUserId(), "sysui_demo_allowed");
        for (String str : this.mTunableLookup.keySet()) {
            if (!ArrayUtils.contains(RESET_EXCEPTION_LIST, str)) {
                Settings.Secure.putStringForUser(this.mContentResolver, str, null, i);
            }
        }
    }

    @Override // com.android.systemui.tuner.TunerService
    public final String getValue(String str) {
        return Settings.Secure.getStringForUser(this.mContentResolver, str, this.mCurrentUser);
    }

    @Override // com.android.systemui.tuner.TunerService
    public final void removeTunable(TunerService.Tunable tunable) {
        Iterator it = this.mTunableLookup.values().iterator();
        while (it.hasNext()) {
            ((Set) it.next()).remove(tunable);
        }
        if (LeakDetector.ENABLED) {
            this.mTunables.remove(tunable);
        }
    }

    @Override // com.android.systemui.tuner.TunerService
    public final void setValue(String str, String str2) {
        Settings.Secure.putStringForUser(this.mContentResolver, str, str2, this.mCurrentUser);
    }

    @Override // com.android.systemui.tuner.TunerService
    public final void showResetRequest(final TunerFragment$$ExternalSyntheticLambda0 tunerFragment$$ExternalSyntheticLambda0) {
        Context context = this.mContext;
        SystemUIDialog systemUIDialog = new SystemUIDialog(context);
        SystemUIDialog.setShowForAllUsers(systemUIDialog);
        systemUIDialog.setMessage(R.string.remove_from_settings_prompt);
        systemUIDialog.setButton(-2, context.getString(R.string.cancel), (DialogInterface.OnClickListener) null);
        systemUIDialog.setButton(-1, context.getString(R.string.qs_customize_remove), new DialogInterface.OnClickListener() { // from class: com.android.systemui.tuner.TunerServiceImpl$$ExternalSyntheticLambda2
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                TunerServiceImpl tunerServiceImpl = TunerServiceImpl.this;
                Runnable runnable = tunerFragment$$ExternalSyntheticLambda0;
                tunerServiceImpl.getClass();
                Intent intent = new Intent("com.android.systemui.action.CLEAR_TUNER");
                Context context2 = tunerServiceImpl.mContext;
                context2.sendBroadcast(intent);
                ((UserTrackerImpl) tunerServiceImpl.mUserTracker).getUserContext().getPackageManager().setComponentEnabledSetting(tunerServiceImpl.mTunerComponent, 2, 1);
                Settings.Secure.putInt(context2.getContentResolver(), "seen_tuner_warning", 0);
                if (runnable != null) {
                    runnable.run();
                }
            }
        });
        systemUIDialog.show();
    }

    @Override // com.android.systemui.tuner.TunerService
    public final int getValue(int i, String str) {
        return Settings.Secure.getIntForUser(this.mContentResolver, str, i, this.mCurrentUser);
    }

    @Override // com.android.systemui.tuner.TunerService
    public final void setValue(int i, String str) {
        Settings.Secure.putIntForUser(this.mContentResolver, str, i, this.mCurrentUser);
    }
}
