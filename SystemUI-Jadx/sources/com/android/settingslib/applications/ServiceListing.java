package com.android.settingslib.applications;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.util.Slog;
import com.android.systemui.controls.ControlsServiceInfo;
import com.android.systemui.controls.management.ControlsListingControllerImpl;
import com.android.systemui.controls.management.ControlsListingControllerImpl$serviceListingCallback$1;
import com.android.systemui.settings.UserTrackerImpl;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import kotlin.collections.CollectionsKt__IterablesKt;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class ServiceListing {
    public final boolean mAddDeviceLockedFlags;
    public final List mCallbacks;
    public final ContentResolver mContentResolver;
    public final Context mContext;
    public final HashSet mEnabledServices;
    public final String mIntentAction;
    public boolean mListening;
    public final String mNoun;
    public final AnonymousClass2 mPackageReceiver;
    public final String mPermission;
    public final List mServices;
    public final String mSetting;
    public final AnonymousClass1 mSettingsObserver;
    public final String mTag;
    public final Predicate mValidator;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class Builder {
        public final Context mContext;

        public Builder(Context context) {
            this.mContext = context;
        }
    }

    public /* synthetic */ ServiceListing(Context context, String str, String str2, String str3, String str4, String str5, boolean z) {
        this(context, str, str2, str3, str4, str5, z, null);
    }

    public final void reload() {
        int i;
        ControlsListingControllerImpl controlsListingControllerImpl;
        HashSet hashSet = this.mEnabledServices;
        hashSet.clear();
        String string = Settings.Secure.getString(this.mContentResolver, this.mSetting);
        if (string != null && !"".equals(string)) {
            for (String str : string.split(":")) {
                ComponentName unflattenFromString = ComponentName.unflattenFromString(str);
                if (unflattenFromString != null) {
                    hashSet.add(unflattenFromString);
                }
            }
        }
        List list = this.mServices;
        ((ArrayList) list).clear();
        int currentUser = ActivityManager.getCurrentUser();
        if (this.mAddDeviceLockedFlags) {
            i = 786564;
        } else {
            i = 132;
        }
        Iterator it = this.mContext.getPackageManager().queryIntentServicesAsUser(new Intent(this.mIntentAction), i, currentUser).iterator();
        while (it.hasNext()) {
            ServiceInfo serviceInfo = ((ResolveInfo) it.next()).serviceInfo;
            String str2 = serviceInfo.permission;
            String str3 = this.mPermission;
            if (!str3.equals(str2)) {
                Slog.w(this.mTag, "Skipping " + this.mNoun + " service " + serviceInfo.packageName + "/" + serviceInfo.name + ": it does not require the permission " + str3);
            } else {
                Predicate predicate = this.mValidator;
                if (predicate == null || predicate.test(serviceInfo)) {
                    ((ArrayList) list).add(serviceInfo);
                }
            }
        }
        Iterator it2 = ((ArrayList) this.mCallbacks).iterator();
        while (it2.hasNext()) {
            ControlsListingControllerImpl$serviceListingCallback$1 controlsListingControllerImpl$serviceListingCallback$1 = (ControlsListingControllerImpl$serviceListingCallback$1) it2.next();
            controlsListingControllerImpl$serviceListingCallback$1.getClass();
            ArrayList arrayList = (ArrayList) list;
            Log.d("ControlsListingControllerImpl", "ServiceConfig reloaded, count: " + arrayList.size());
            ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList, 10));
            Iterator it3 = arrayList.iterator();
            while (true) {
                boolean hasNext = it3.hasNext();
                controlsListingControllerImpl = controlsListingControllerImpl$serviceListingCallback$1.this$0;
                if (hasNext) {
                    arrayList2.add(new ControlsServiceInfo(((UserTrackerImpl) controlsListingControllerImpl.userTracker).getUserContext(), (ServiceInfo) it3.next()));
                }
            }
            controlsListingControllerImpl.backgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.controls.management.ControlsListingControllerImpl$serviceListingCallback$1.1
                public final /* synthetic */ List $newServices;

                public AnonymousClass1(List<? extends ControlsServiceInfo> arrayList22) {
                    r2 = arrayList22;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    if (ControlsListingControllerImpl.this.userChangeInProgress.get() > 0) {
                        return;
                    }
                    ControlsListingControllerImpl.this.updateServices(r2);
                }
            });
        }
    }

    public final void setListening(boolean z) {
        if (this.mListening == z) {
            return;
        }
        this.mListening = z;
        AnonymousClass1 anonymousClass1 = this.mSettingsObserver;
        ContentResolver contentResolver = this.mContentResolver;
        AnonymousClass2 anonymousClass2 = this.mPackageReceiver;
        Context context = this.mContext;
        if (z) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
            intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
            intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
            intentFilter.addAction("android.intent.action.PACKAGE_REPLACED");
            intentFilter.addDataScheme("package");
            context.registerReceiver(anonymousClass2, intentFilter);
            contentResolver.registerContentObserver(Settings.Secure.getUriFor(this.mSetting), false, anonymousClass1);
            return;
        }
        context.unregisterReceiver(anonymousClass2);
        contentResolver.unregisterContentObserver(anonymousClass1);
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.settingslib.applications.ServiceListing$1] */
    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.settingslib.applications.ServiceListing$2] */
    private ServiceListing(Context context, String str, String str2, String str3, String str4, String str5, boolean z, Predicate predicate) {
        this.mEnabledServices = new HashSet();
        this.mServices = new ArrayList();
        this.mCallbacks = new ArrayList();
        this.mSettingsObserver = new ContentObserver(new Handler()) { // from class: com.android.settingslib.applications.ServiceListing.1
            @Override // android.database.ContentObserver
            public final void onChange(boolean z2, Uri uri) {
                ServiceListing.this.reload();
            }
        };
        this.mPackageReceiver = new BroadcastReceiver() { // from class: com.android.settingslib.applications.ServiceListing.2
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                ServiceListing.this.reload();
            }
        };
        this.mContentResolver = context.getContentResolver();
        this.mContext = context;
        this.mTag = str;
        this.mSetting = str2;
        this.mIntentAction = str3;
        this.mPermission = str4;
        this.mNoun = str5;
        this.mAddDeviceLockedFlags = z;
        this.mValidator = predicate;
    }
}
