package com.android.systemui.assist;

import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.metrics.LogMaker;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Process;
import android.os.SystemClock;
import android.os.UserHandle;
import android.provider.Settings;
import android.service.voice.VoiceInteractionServiceInfo;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.internal.app.AssistUtils;
import com.android.internal.app.IVoiceInteractionSessionListener;
import com.android.internal.app.IVoiceInteractionSessionShowCallback;
import com.android.internal.logging.MetricsLogger;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.Prefs;
import com.android.systemui.R;
import com.android.systemui.assist.AssistDisclosure;
import com.android.systemui.assist.AssistManager;
import com.android.systemui.assist.ui.DefaultUiController;
import com.android.systemui.assist.ui.InvocationLightsView;
import com.android.systemui.model.SysUiState;
import com.android.systemui.recents.OverviewProxyService;
import com.android.systemui.settings.DisplayTracker;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.settings.SecureSettings;
import com.samsung.android.app.SemRoleManager;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class AssistManager {
    public final AssistDisclosure mAssistDisclosure;
    public final AssistLogger mAssistLogger;
    public boolean mAssistPopupPositiveClicked;
    public final AssistUtils mAssistUtils;
    public AlertDialog mAssistanceAppSettingAlertDialog;
    public final CommandQueue mCommandQueue;
    public final Context mContext;
    public final DeviceProvisionedController mDeviceProvisionedController;
    public int mDisabledFlags;
    public final DisplayTracker mDisplayTracker;
    public boolean mIsAssistAppAvailable = true;
    public LayoutInflater mLayoutInflater;
    public final PhoneStateMonitor mPhoneStateMonitor;
    public final SecureSettings mSecureSettings;
    public final Lazy mSysUiState;
    public final DefaultUiController mUiController;
    public final UserTracker mUserTracker;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class AssistanceAppItemList {
        public final Drawable mAssistanceAppIcon;
        public final String mAssistanceAppName;
        public final int mAssistanceAppType;
        public final ComponentName mAssistanceComponent;
        public final VoiceInteractionServiceInfo mAssistanceVoiceInteractionService;

        public AssistanceAppItemList(ComponentName componentName, VoiceInteractionServiceInfo voiceInteractionServiceInfo, Drawable drawable, String str, int i) {
            this.mAssistanceComponent = componentName;
            this.mAssistanceVoiceInteractionService = voiceInteractionServiceInfo;
            this.mAssistanceAppIcon = drawable;
            this.mAssistanceAppName = str;
            this.mAssistanceAppType = i;
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class AssistanceAppItemListAdapter extends ArrayAdapter {
        public final int mResourceId;
        public int mSelectedItem;

        public AssistanceAppItemListAdapter(Context context, int i, ArrayList<AssistanceAppItemList> arrayList) {
            super(context, i, arrayList);
            this.mResourceId = i;
        }

        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        public final View getView(int i, View view, ViewGroup viewGroup) {
            LinearLayout linearLayout = (LinearLayout) view;
            if (linearLayout == null) {
                linearLayout = (LinearLayout) AssistManager.this.mLayoutInflater.inflate(this.mResourceId, viewGroup, false);
            }
            AssistanceAppItemList assistanceAppItemList = (AssistanceAppItemList) getItem(i);
            if (assistanceAppItemList != null) {
                CheckedTextView checkedTextView = (CheckedTextView) linearLayout.findViewById(R.id.check_box);
                ImageView imageView = (ImageView) linearLayout.findViewById(R.id.app_icon);
                TextView textView = (TextView) linearLayout.findViewById(R.id.app_name);
                if (textView != null) {
                    textView.setText(assistanceAppItemList.mAssistanceAppName);
                }
                if (imageView != null) {
                    imageView.setImageDrawable(assistanceAppItemList.mAssistanceAppIcon);
                }
                if (checkedTextView != null) {
                    if (this.mSelectedItem == i) {
                        checkedTextView.setChecked(true);
                    } else {
                        checkedTextView.setChecked(false);
                    }
                }
            }
            return linearLayout;
        }
    }

    public AssistManager(DeviceProvisionedController deviceProvisionedController, Context context, AssistUtils assistUtils, CommandQueue commandQueue, PhoneStateMonitor phoneStateMonitor, OverviewProxyService overviewProxyService, Lazy lazy, DefaultUiController defaultUiController, AssistLogger assistLogger, Handler handler, UserTracker userTracker, DisplayTracker displayTracker, SecureSettings secureSettings) {
        this.mContext = context;
        this.mDeviceProvisionedController = deviceProvisionedController;
        this.mCommandQueue = commandQueue;
        this.mAssistUtils = assistUtils;
        this.mAssistDisclosure = new AssistDisclosure(context, handler);
        this.mPhoneStateMonitor = phoneStateMonitor;
        this.mAssistLogger = assistLogger;
        this.mUserTracker = userTracker;
        this.mDisplayTracker = displayTracker;
        this.mSecureSettings = secureSettings;
        assistUtils.registerVoiceInteractionSessionListener(new IVoiceInteractionSessionListener.Stub() { // from class: com.android.systemui.assist.AssistManager.2
            public final void onSetUiHints(Bundle bundle) {
                if ("set_assist_gesture_constrained".equals(bundle.getString("action"))) {
                    SysUiState sysUiState = (SysUiState) AssistManager.this.mSysUiState.get();
                    sysUiState.setFlag(8192L, bundle.getBoolean("should_constrain", false));
                    AssistManager.this.mDisplayTracker.getClass();
                    sysUiState.commitUpdate(0);
                }
            }

            public final void onVoiceSessionHidden() {
                AssistManager.this.mAssistLogger.reportAssistantSessionEvent(AssistantSessionEvent.ASSISTANT_SESSION_CLOSE);
            }

            public final void onVoiceSessionShown() {
                AssistManager.this.mAssistLogger.reportAssistantSessionEvent(AssistantSessionEvent.ASSISTANT_SESSION_UPDATE);
            }

            public final void onVoiceSessionWindowVisibilityChanged(boolean z) {
                if (z) {
                    AssistManager assistManager = AssistManager.this;
                    assistManager.getClass();
                    if (assistManager.mAssistUtils.getAssistComponentForUser(KeyguardUpdateMonitor.getCurrentUser()) != null) {
                        AssistManager assistManager2 = AssistManager.this;
                        assistManager2.getClass();
                        if (assistManager2.mAssistUtils.getAssistComponentForUser(KeyguardUpdateMonitor.getCurrentUser()).getPackageName() != null) {
                            String str = SystemUIAnalytics.sCurrentScreenID;
                            AssistManager assistManager3 = AssistManager.this;
                            assistManager3.getClass();
                            SystemUIAnalytics.sendEventLog(str, "747005", assistManager3.mAssistUtils.getAssistComponentForUser(KeyguardUpdateMonitor.getCurrentUser()).getPackageName());
                        }
                    }
                }
            }
        });
        this.mUiController = defaultUiController;
        this.mSysUiState = lazy;
        overviewProxyService.addCallback(new OverviewProxyService.OverviewProxyListener() { // from class: com.android.systemui.assist.AssistManager.1
            @Override // com.android.systemui.recents.OverviewProxyService.OverviewProxyListener
            public final void onAssistantGestureCompletion(float f) {
                DefaultUiController defaultUiController2 = AssistManager.this.mUiController;
                defaultUiController2.animateInvocationCompletion();
                defaultUiController2.logInvocationProgressMetrics(1.0f, defaultUiController2.mInvocationInProgress);
            }

            @Override // com.android.systemui.recents.OverviewProxyService.OverviewProxyListener
            public final void onAssistantProgress(float f) {
                DefaultUiController defaultUiController2 = AssistManager.this.mUiController;
                boolean z = defaultUiController2.mInvocationInProgress;
                if (f == 1.0f) {
                    defaultUiController2.animateInvocationCompletion();
                } else if (f == 0.0f) {
                    defaultUiController2.hide();
                } else {
                    if (!z) {
                        if (!defaultUiController2.mAttached) {
                            boolean z2 = BasicRune.ASSIST_INVOCATION_SWITCH;
                            FrameLayout frameLayout = defaultUiController2.mRoot;
                            if (z2) {
                                frameLayout.removeAllViews();
                                InvocationLightsView invocationLightsView = (InvocationLightsView) LayoutInflater.from(defaultUiController2.mContext).inflate(R.layout.invocation_lights, (ViewGroup) frameLayout, false);
                                defaultUiController2.mInvocationLightsView = invocationLightsView;
                                frameLayout.addView(invocationLightsView);
                            }
                            defaultUiController2.mWindowManager.addView(frameLayout, defaultUiController2.mLayoutParams);
                            defaultUiController2.mAttached = true;
                        }
                        defaultUiController2.mInvocationInProgress = true;
                    }
                    defaultUiController2.setProgressInternal(f);
                }
                defaultUiController2.mLastInvocationProgress = f;
                defaultUiController2.logInvocationProgressMetrics(f, z);
            }
        });
    }

    public final String getDefaultRecognizer() {
        ResolveInfo resolveService = this.mContext.getPackageManager().resolveService(new Intent("android.speech.RecognitionService"), 128);
        if (resolveService != null && resolveService.serviceInfo != null) {
            ServiceInfo serviceInfo = resolveService.serviceInfo;
            return new ComponentName(serviceInfo.packageName, serviceInfo.name).flattenToShortString();
        }
        Log.w("AssistManager", "Unable to resolve default voice recognition service.");
        return "";
    }

    public final void hideAssist() {
        this.mAssistUtils.hideCurrentSession();
    }

    public final void showAssistanceAppSettingAlertDialog() {
        String str;
        String str2;
        Drawable drawable;
        View view;
        int i;
        Drawable drawable2;
        PackageInfo packageInfo;
        if (this.mAssistanceAppSettingAlertDialog == null) {
            Context context = this.mContext;
            LayoutInflater from = LayoutInflater.from(new ContextThemeWrapper(context, 2132018527));
            this.mLayoutInflater = from;
            View inflate = from.inflate(R.layout.assistance_app_setting_alert_dialog_title, (ViewGroup) null);
            TextView textView = (TextView) inflate.findViewById(R.id.title);
            if (BasicRune.NAVBAR_SUPPORT_SEARCLE) {
                textView.setText(context.getResources().getString(R.string.assistance_app_setting_alert_dialog_title_swipe));
            } else {
                textView.setText(context.getResources().getString(R.string.assistance_app_setting_alert_dialog_title));
            }
            final ArrayList arrayList = new ArrayList();
            final AssistanceAppItemListAdapter assistanceAppItemListAdapter = new AssistanceAppItemListAdapter(context, R.layout.assistance_app_setting_item, arrayList);
            int currentUser = ActivityManager.getCurrentUser();
            AssistUtils assistUtils = this.mAssistUtils;
            ComponentName assistComponentForUser = assistUtils.getAssistComponentForUser(currentUser);
            if (assistComponentForUser == null) {
                str = "";
            } else {
                str = assistUtils.getAssistComponentForUser(ActivityManager.getCurrentUser()).getPackageName();
            }
            PackageManager packageManager = context.getPackageManager();
            ArrayList arrayList2 = new ArrayList();
            List<ResolveInfo> queryIntentServices = packageManager.queryIntentServices(new Intent("android.service.voice.VoiceInteractionService"), 128);
            Drawable drawable3 = null;
            int i2 = 0;
            String str3 = "";
            int i3 = -1;
            while (true) {
                str2 = str3;
                drawable = drawable3;
                if (i2 >= queryIntentServices.size()) {
                    break;
                }
                ResolveInfo resolveInfo = queryIntentServices.get(i2);
                List<ResolveInfo> list = queryIntentServices;
                int i4 = i3;
                VoiceInteractionServiceInfo voiceInteractionServiceInfo = new VoiceInteractionServiceInfo(packageManager, resolveInfo.serviceInfo);
                String str4 = resolveInfo.serviceInfo.packageName;
                if (!voiceInteractionServiceInfo.getSupportsAssist() || arrayList2.contains(str4)) {
                    view = inflate;
                    str3 = str2;
                    drawable3 = drawable;
                    i3 = i4;
                } else {
                    arrayList2.add(str4);
                    if (str.equals(str4)) {
                        view = inflate;
                        i = arrayList2.size() - 1;
                    } else {
                        view = inflate;
                        i = i4;
                    }
                    try {
                        packageInfo = packageManager.getPackageInfo(str4, 0);
                        drawable2 = packageManager.semGetApplicationIconForIconTray(str4, 1);
                    } catch (PackageManager.NameNotFoundException e) {
                        e = e;
                    } catch (Resources.NotFoundException e2) {
                        e = e2;
                        drawable2 = drawable;
                    } catch (NullPointerException e3) {
                        e = e3;
                        drawable2 = drawable;
                    }
                    try {
                        str3 = packageManager.getApplicationLabel(packageInfo.applicationInfo).toString();
                        drawable3 = drawable2;
                    } catch (PackageManager.NameNotFoundException e4) {
                        e = e4;
                        drawable = drawable2;
                        Log.w("AssistManager", "Failed to add assistance app " + str4 + " not found", e);
                        str3 = str2;
                        drawable3 = drawable;
                        arrayList.add(new AssistanceAppItemList(new ComponentName(str4, resolveInfo.serviceInfo.name), voiceInteractionServiceInfo, drawable3, str3, 1));
                        i3 = i;
                        i2++;
                        queryIntentServices = list;
                        inflate = view;
                    } catch (Resources.NotFoundException e5) {
                        e = e5;
                        Log.w("AssistManager", "Failed to add assistance app " + str4, e);
                        drawable3 = drawable2;
                        str3 = str2;
                        arrayList.add(new AssistanceAppItemList(new ComponentName(str4, resolveInfo.serviceInfo.name), voiceInteractionServiceInfo, drawable3, str3, 1));
                        i3 = i;
                        i2++;
                        queryIntentServices = list;
                        inflate = view;
                    } catch (NullPointerException e6) {
                        e = e6;
                        Log.w("AssistManager", "Failed to add assistance app " + str4, e);
                        drawable3 = drawable2;
                        str3 = str2;
                        arrayList.add(new AssistanceAppItemList(new ComponentName(str4, resolveInfo.serviceInfo.name), voiceInteractionServiceInfo, drawable3, str3, 1));
                        i3 = i;
                        i2++;
                        queryIntentServices = list;
                        inflate = view;
                    }
                    arrayList.add(new AssistanceAppItemList(new ComponentName(str4, resolveInfo.serviceInfo.name), voiceInteractionServiceInfo, drawable3, str3, 1));
                    i3 = i;
                }
                i2++;
                queryIntentServices = list;
                inflate = view;
            }
            View view2 = inflate;
            List<ResolveInfo> queryIntentActivities = packageManager.queryIntentActivities(new Intent("android.intent.action.ASSIST"), 65536);
            int i5 = i3;
            for (int i6 = 0; i6 < queryIntentActivities.size(); i6++) {
                ResolveInfo resolveInfo2 = queryIntentActivities.get(i6);
                String str5 = resolveInfo2.activityInfo.packageName;
                if (!arrayList2.contains(str5)) {
                    arrayList2.add(str5);
                    if (str.equals(str5)) {
                        i5 = arrayList2.size() - 1;
                    }
                    int i7 = i5;
                    try {
                        PackageInfo packageInfo2 = packageManager.getPackageInfo(str5, 0);
                        drawable = packageManager.semGetApplicationIconForIconTray(str5, 1);
                        str2 = packageManager.getApplicationLabel(packageInfo2.applicationInfo).toString();
                    } catch (PackageManager.NameNotFoundException e7) {
                        Log.w("AssistManager", "Failed to add assistance app " + str5 + " not found", e7);
                    } catch (Resources.NotFoundException e8) {
                        Log.w("AssistManager", "Failed to add assistance app " + str5, e8);
                    } catch (NullPointerException e9) {
                        Log.w("AssistManager", "Failed to add assistance app " + str5, e9);
                    }
                    arrayList.add(new AssistanceAppItemList(new ComponentName(str5, resolveInfo2.activityInfo.name), null, drawable, str2, 2));
                    i5 = i7;
                }
            }
            arrayList.add(new AssistanceAppItemList(null, null, context.getDrawable(R.drawable.ic_remove_circle), context.getString(R.string.assistance_app_setting_item_none), 0));
            if (i5 < 0) {
                i5 = arrayList2.size();
            }
            assistanceAppItemListAdapter.mSelectedItem = i5;
            Log.d("AssistManager", "Current assistance app - " + assistComponentForUser + " package name - " + str + " defaultItem - " + i5);
            final int i8 = 0;
            DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() { // from class: com.android.systemui.assist.AssistManager$$ExternalSyntheticLambda0
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i9) {
                    String defaultRecognizer;
                    String str6;
                    String str7;
                    String str8;
                    String str9;
                    switch (i8) {
                        case 0:
                            ArrayList arrayList3 = (ArrayList) arrayList;
                            ArrayAdapter arrayAdapter = assistanceAppItemListAdapter;
                            synchronized (arrayList3) {
                                ((AssistManager.AssistanceAppItemListAdapter) arrayAdapter).mSelectedItem = i9;
                                arrayAdapter.notifyDataSetChanged();
                            }
                            return;
                        default:
                            AssistManager assistManager = (AssistManager) arrayList;
                            ArrayAdapter arrayAdapter2 = assistanceAppItemListAdapter;
                            assistManager.mAssistPopupPositiveClicked = true;
                            Context context2 = assistManager.mContext;
                            Prefs.putBoolean(context2, "AssistanceAppSettingAlreadySelected", true);
                            AssistManager.AssistanceAppItemListAdapter assistanceAppItemListAdapter2 = (AssistManager.AssistanceAppItemListAdapter) arrayAdapter2;
                            AssistManager.AssistanceAppItemList assistanceAppItemList = (AssistManager.AssistanceAppItemList) assistanceAppItemListAdapter2.getItem(assistanceAppItemListAdapter2.mSelectedItem);
                            int i10 = assistanceAppItemList.mAssistanceAppType;
                            final String str10 = "";
                            if (i10 != 0) {
                                ComponentName componentName = assistanceAppItemList.mAssistanceComponent;
                                if (i10 != 1) {
                                    if (i10 != 2) {
                                        str9 = "";
                                        str8 = str9;
                                        str7 = str8;
                                    } else {
                                        String packageName = componentName.getPackageName();
                                        str7 = componentName.flattenToShortString();
                                        str8 = assistManager.getDefaultRecognizer();
                                        str10 = packageName;
                                        str9 = "";
                                    }
                                    String str11 = str10;
                                    new SemRoleManager(context2).addRoleHolderAsUser("android.app.role.ASSISTANT", str11, 0, Process.myUserHandle(), context2.getMainExecutor(), new Consumer() { // from class: com.android.systemui.assist.AssistManager$$ExternalSyntheticLambda2
                                        @Override // java.util.function.Consumer
                                        public final void accept(Object obj) {
                                            String str12 = str10;
                                            if (((Boolean) obj).booleanValue()) {
                                                MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("role success = ", str12, "AssistManager");
                                            } else {
                                                MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("role fail = ", str12, "AssistManager");
                                            }
                                        }
                                    });
                                    Settings.Secure.putString(((SettingsHelper) Dependency.get(SettingsHelper.class)).mResolver, "assistant", str7);
                                    Settings.Secure.putString(((SettingsHelper) Dependency.get(SettingsHelper.class)).mResolver, "voice_interaction_service", str9);
                                    Settings.Secure.putString(((SettingsHelper) Dependency.get(SettingsHelper.class)).mResolver, "voice_recognition_service", str8);
                                    SystemUIAnalytics.sendEventLog("980", "9801", str10);
                                    return;
                                }
                                str6 = componentName.getPackageName();
                                str10 = componentName.flattenToShortString();
                                defaultRecognizer = new ComponentName(str6, assistanceAppItemList.mAssistanceVoiceInteractionService.getRecognitionService()).flattenToShortString();
                            } else {
                                defaultRecognizer = assistManager.getDefaultRecognizer();
                                str6 = "None";
                            }
                            str7 = str10;
                            str10 = str6;
                            str8 = defaultRecognizer;
                            str9 = str7;
                            String str112 = str10;
                            new SemRoleManager(context2).addRoleHolderAsUser("android.app.role.ASSISTANT", str112, 0, Process.myUserHandle(), context2.getMainExecutor(), new Consumer() { // from class: com.android.systemui.assist.AssistManager$$ExternalSyntheticLambda2
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    String str12 = str10;
                                    if (((Boolean) obj).booleanValue()) {
                                        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("role success = ", str12, "AssistManager");
                                    } else {
                                        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("role fail = ", str12, "AssistManager");
                                    }
                                }
                            });
                            Settings.Secure.putString(((SettingsHelper) Dependency.get(SettingsHelper.class)).mResolver, "assistant", str7);
                            Settings.Secure.putString(((SettingsHelper) Dependency.get(SettingsHelper.class)).mResolver, "voice_interaction_service", str9);
                            Settings.Secure.putString(((SettingsHelper) Dependency.get(SettingsHelper.class)).mResolver, "voice_recognition_service", str8);
                            SystemUIAnalytics.sendEventLog("980", "9801", str10);
                            return;
                    }
                }
            };
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setCustomTitle(view2);
            builder.setSingleChoiceItems(assistanceAppItemListAdapter, i5, onClickListener);
            builder.setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null);
            final int i9 = 1;
            builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() { // from class: com.android.systemui.assist.AssistManager$$ExternalSyntheticLambda0
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i92) {
                    String defaultRecognizer;
                    String str6;
                    String str7;
                    String str8;
                    String str9;
                    switch (i9) {
                        case 0:
                            ArrayList arrayList3 = (ArrayList) this;
                            ArrayAdapter arrayAdapter = assistanceAppItemListAdapter;
                            synchronized (arrayList3) {
                                ((AssistManager.AssistanceAppItemListAdapter) arrayAdapter).mSelectedItem = i92;
                                arrayAdapter.notifyDataSetChanged();
                            }
                            return;
                        default:
                            AssistManager assistManager = (AssistManager) this;
                            ArrayAdapter arrayAdapter2 = assistanceAppItemListAdapter;
                            assistManager.mAssistPopupPositiveClicked = true;
                            Context context2 = assistManager.mContext;
                            Prefs.putBoolean(context2, "AssistanceAppSettingAlreadySelected", true);
                            AssistManager.AssistanceAppItemListAdapter assistanceAppItemListAdapter2 = (AssistManager.AssistanceAppItemListAdapter) arrayAdapter2;
                            AssistManager.AssistanceAppItemList assistanceAppItemList = (AssistManager.AssistanceAppItemList) assistanceAppItemListAdapter2.getItem(assistanceAppItemListAdapter2.mSelectedItem);
                            int i10 = assistanceAppItemList.mAssistanceAppType;
                            final String str10 = "";
                            if (i10 != 0) {
                                ComponentName componentName = assistanceAppItemList.mAssistanceComponent;
                                if (i10 != 1) {
                                    if (i10 != 2) {
                                        str9 = "";
                                        str8 = str9;
                                        str7 = str8;
                                    } else {
                                        String packageName = componentName.getPackageName();
                                        str7 = componentName.flattenToShortString();
                                        str8 = assistManager.getDefaultRecognizer();
                                        str10 = packageName;
                                        str9 = "";
                                    }
                                    String str112 = str10;
                                    new SemRoleManager(context2).addRoleHolderAsUser("android.app.role.ASSISTANT", str112, 0, Process.myUserHandle(), context2.getMainExecutor(), new Consumer() { // from class: com.android.systemui.assist.AssistManager$$ExternalSyntheticLambda2
                                        @Override // java.util.function.Consumer
                                        public final void accept(Object obj) {
                                            String str12 = str10;
                                            if (((Boolean) obj).booleanValue()) {
                                                MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("role success = ", str12, "AssistManager");
                                            } else {
                                                MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("role fail = ", str12, "AssistManager");
                                            }
                                        }
                                    });
                                    Settings.Secure.putString(((SettingsHelper) Dependency.get(SettingsHelper.class)).mResolver, "assistant", str7);
                                    Settings.Secure.putString(((SettingsHelper) Dependency.get(SettingsHelper.class)).mResolver, "voice_interaction_service", str9);
                                    Settings.Secure.putString(((SettingsHelper) Dependency.get(SettingsHelper.class)).mResolver, "voice_recognition_service", str8);
                                    SystemUIAnalytics.sendEventLog("980", "9801", str10);
                                    return;
                                }
                                str6 = componentName.getPackageName();
                                str10 = componentName.flattenToShortString();
                                defaultRecognizer = new ComponentName(str6, assistanceAppItemList.mAssistanceVoiceInteractionService.getRecognitionService()).flattenToShortString();
                            } else {
                                defaultRecognizer = assistManager.getDefaultRecognizer();
                                str6 = "None";
                            }
                            str7 = str10;
                            str10 = str6;
                            str8 = defaultRecognizer;
                            str9 = str7;
                            String str1122 = str10;
                            new SemRoleManager(context2).addRoleHolderAsUser("android.app.role.ASSISTANT", str1122, 0, Process.myUserHandle(), context2.getMainExecutor(), new Consumer() { // from class: com.android.systemui.assist.AssistManager$$ExternalSyntheticLambda2
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    String str12 = str10;
                                    if (((Boolean) obj).booleanValue()) {
                                        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("role success = ", str12, "AssistManager");
                                    } else {
                                        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("role fail = ", str12, "AssistManager");
                                    }
                                }
                            });
                            Settings.Secure.putString(((SettingsHelper) Dependency.get(SettingsHelper.class)).mResolver, "assistant", str7);
                            Settings.Secure.putString(((SettingsHelper) Dependency.get(SettingsHelper.class)).mResolver, "voice_interaction_service", str9);
                            Settings.Secure.putString(((SettingsHelper) Dependency.get(SettingsHelper.class)).mResolver, "voice_recognition_service", str8);
                            SystemUIAnalytics.sendEventLog("980", "9801", str10);
                            return;
                    }
                }
            });
            builder.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.assist.AssistManager$$ExternalSyntheticLambda1
                @Override // android.content.DialogInterface.OnDismissListener
                public final void onDismiss(DialogInterface dialogInterface) {
                    AssistManager assistManager = AssistManager.this;
                    assistManager.mAssistanceAppSettingAlertDialog = null;
                    if (!assistManager.mAssistPopupPositiveClicked) {
                        SystemUIAnalytics.sendEventLog("980", "9802");
                    }
                }
            });
            this.mAssistPopupPositiveClicked = false;
            AlertDialog create = builder.create();
            this.mAssistanceAppSettingAlertDialog = create;
            create.getWindow().setType(2009);
            this.mAssistanceAppSettingAlertDialog.show();
            SystemUIAnalytics.sendEventLog("980", "9800");
        }
    }

    public final void showDisclosure() {
        boolean z;
        AssistDisclosure assistDisclosure = this.mAssistDisclosure;
        assistDisclosure.getClass();
        if (((SettingsHelper) Dependency.get(SettingsHelper.class)).mItemLists.get("assist_disclosure_enabled").getIntValue() != 0) {
            z = true;
        } else {
            z = false;
        }
        if (!z) {
            Log.d("AssistDisclosure", "AssistDisclosure VI is disabled");
            return;
        }
        Handler handler = assistDisclosure.mHandler;
        AssistDisclosure.AnonymousClass1 anonymousClass1 = assistDisclosure.mShowRunnable;
        handler.removeCallbacks(anonymousClass1);
        handler.post(anonymousClass1);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void startAssist(Bundle bundle) {
        byte b;
        byte b2;
        boolean z;
        final Intent assistIntent;
        byte b3;
        Intent intent;
        DeviceProvisionedControllerImpl deviceProvisionedControllerImpl = (DeviceProvisionedControllerImpl) this.mDeviceProvisionedController;
        boolean z2 = true;
        if (deviceProvisionedControllerImpl.isDeviceProvisioned() && (this.mDisabledFlags & QuickStepContract.SYSUI_STATE_GAME_TOOLS_SHOWING) == 0) {
            b = false;
        } else {
            b = true;
        }
        if (b != false) {
            return;
        }
        if (bundle != null && bundle.getBoolean("android.intent.extra.ASSIST_INPUT_HINT_KEYBOARD")) {
            b2 = true;
        } else {
            b2 = false;
        }
        Context context = this.mContext;
        if (b2 != false && !Prefs.getBoolean(context, "AssistanceAppSettingAlreadySelected", false) && !Prefs.getBoolean(context, "AssistanceMetaKeyPressed", false)) {
            String permissionControllerPackageName = context.getPackageManager().getPermissionControllerPackageName();
            if (permissionControllerPackageName != null) {
                intent = new Intent("android.intent.action.MANAGE_DEFAULT_APP").setPackage(permissionControllerPackageName).putExtra("android.intent.extra.ROLE_NAME", "android.app.role.ASSISTANT");
            } else {
                intent = null;
            }
            if (intent != null) {
                context.startActivityAsUser(intent, UserHandle.CURRENT);
                Prefs.putBoolean(context, "AssistanceMetaKeyPressed", true);
                return;
            }
            return;
        }
        boolean z3 = BasicRune.ASSIST_ASSISTANCE_APP_SETTING_POPUP;
        if (z3 && !Prefs.getBoolean(context, "AssistanceAppSettingAlreadySelected", false)) {
            showAssistanceAppSettingAlertDialog();
            return;
        }
        if (BasicRune.NAVBAR_ENABLED) {
            if (Settings.Secure.getIntForUser(context.getContentResolver(), "assist_long_press_home_enabled", context.getResources().getBoolean(android.R.bool.config_bluetooth_hfp_inband_ringing_support) ? 1 : 0, ((UserTrackerImpl) this.mUserTracker).getUserId()) != 0) {
                b3 = true;
            } else {
                b3 = false;
            }
            if (bundle.getInt("invocation_type", 0) == 5 && b3 == false) {
                return;
            }
        }
        int currentUser = KeyguardUpdateMonitor.getCurrentUser();
        AssistUtils assistUtils = this.mAssistUtils;
        ComponentName assistComponentForUser = assistUtils.getAssistComponentForUser(currentUser);
        if (assistComponentForUser == null) {
            return;
        }
        boolean equals = assistComponentForUser.equals(assistUtils.getActiveServiceComponentName());
        if (bundle == null) {
            bundle = new Bundle();
        }
        Bundle bundle2 = bundle;
        int i = bundle2.getInt("invocation_type", 0);
        int phoneState = this.mPhoneStateMonitor.getPhoneState();
        bundle2.putInt("invocation_phone_state", phoneState);
        bundle2.putLong("invocation_time_ms", SystemClock.elapsedRealtime());
        this.mAssistLogger.reportAssistantInvocationEventFromLegacy(i, true, assistComponentForUser, Integer.valueOf(phoneState));
        MetricsLogger.action(new LogMaker(1716).setType(1).setSubtype((i << 1) | 0 | (phoneState << 4)));
        if (z3 && !this.mIsAssistAppAvailable) {
            showAssistanceAppSettingAlertDialog();
            this.mIsAssistAppAvailable = true;
        }
        if (equals) {
            this.mAssistUtils.showSessionForActiveService(bundle2, 4, context.getAttributionTag(), (IVoiceInteractionSessionShowCallback) null, (IBinder) null);
            return;
        }
        if (deviceProvisionedControllerImpl.isDeviceProvisioned()) {
            this.mCommandQueue.animateCollapsePanels(3, false);
            if (this.mSecureSettings.getIntForUser(1, -2, "assist_structure_enabled") != 0) {
                z = true;
            } else {
                z = false;
            }
            SearchManager searchManager = (SearchManager) context.getSystemService("search");
            if (searchManager != null && (assistIntent = searchManager.getAssistIntent(z)) != null) {
                assistIntent.setComponent(assistComponentForUser);
                assistIntent.putExtras(bundle2);
                if (z) {
                    if (((SettingsHelper) Dependency.get(SettingsHelper.class)).mItemLists.get("assist_disclosure_enabled").getIntValue() == 0) {
                        z2 = false;
                    }
                    if (z2) {
                        showDisclosure();
                    }
                }
                try {
                    final ActivityOptions makeCustomAnimation = ActivityOptions.makeCustomAnimation(context, R.anim.search_launch_enter, R.anim.search_launch_exit);
                    assistIntent.addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
                    AsyncTask.execute(new Runnable() { // from class: com.android.systemui.assist.AssistManager.3
                        @Override // java.lang.Runnable
                        public final void run() {
                            AssistManager.this.mContext.startActivityAsUser(assistIntent, makeCustomAnimation.toBundle(), ((UserTrackerImpl) AssistManager.this.mUserTracker).getUserHandle());
                        }
                    });
                } catch (ActivityNotFoundException unused) {
                    Log.w("AssistManager", "Activity not found for " + assistIntent.getAction());
                }
            }
        }
    }
}
