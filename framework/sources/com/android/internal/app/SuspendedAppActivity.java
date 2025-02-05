package com.android.internal.app;

import android.Manifest;
import android.app.ActivityOptions;
import android.app.AppGlobals;
import android.app.KeyguardManager;
import android.app.admin.flags.Flags;
import android.app.usage.UsageStatsManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.IPackageManager;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.SuspendDialogInfo;
import android.content.pm.UserPackage;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Slog;
import com.android.internal.R;
import com.android.internal.app.AlertController;
import com.android.internal.util.ArrayUtils;

/* loaded from: classes5.dex */
public class SuspendedAppActivity extends AlertActivity implements DialogInterface.OnClickListener {
    private static final String DIGITAL_WELLBEING_PACKAGE = "com.samsung.android.forest";
    public static final String EXTRA_ACTIVITY_OPTIONS = "com.android.internal.app.extra.ACTIVITY_OPTIONS";
    public static final String EXTRA_DIALOG_INFO = "com.android.internal.app.extra.DIALOG_INFO";
    public static final String EXTRA_SUSPENDED_PACKAGE = "com.android.internal.app.extra.SUSPENDED_PACKAGE";
    public static final String EXTRA_SUSPENDING_PACKAGE = "com.android.internal.app.extra.SUSPENDING_PACKAGE";
    public static final String EXTRA_SUSPENDING_USER = "com.android.internal.app.extra.SUSPENDING_USER";
    public static final String EXTRA_UNSUSPEND_INTENT = "com.android.internal.app.extra.UNSUSPEND_INTENT";
    private static final String PACKAGE_NAME = "com.android.internal.app";
    private static final String TAG = SuspendedAppActivity.class.getSimpleName();
    private Intent mMoreDetailsIntent;
    private int mNeutralButtonAction;
    private IntentSender mOnUnsuspend;
    private Bundle mOptions;
    private PackageManager mPm;
    private SuspendDialogInfo mSuppliedDialogInfo;
    private BroadcastReceiver mSuspendModifiedReceiver = new BroadcastReceiver() { // from class: com.android.internal.app.SuspendedAppActivity.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (Intent.ACTION_PACKAGES_SUSPENSION_CHANGED.equals(intent.getAction())) {
                String[] modified = intent.getStringArrayExtra(Intent.EXTRA_CHANGED_PACKAGE_LIST);
                if (ArrayUtils.contains(modified, SuspendedAppActivity.this.mSuspendedPackage) && !SuspendedAppActivity.this.isPackageSuspended(SuspendedAppActivity.this.mSuspendedPackage) && !SuspendedAppActivity.this.isFinishing()) {
                    Slog.w(SuspendedAppActivity.TAG, "Package " + SuspendedAppActivity.this.mSuspendedPackage + " has modified suspension conditions while dialog was visible. Finishing.");
                    SuspendedAppActivity.this.finish();
                }
            }
        }
    };
    private String mSuspendedPackage;
    private Resources mSuspendingAppResources;
    private String mSuspendingPackage;
    private int mSuspendingUserId;
    private int mUserId;
    private UsageStatsManager mUsm;

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isPackageSuspended(String packageName) {
        try {
            return this.mPm.isPackageSuspended(packageName);
        } catch (PackageManager.NameNotFoundException ne) {
            Slog.e(TAG, "Package " + packageName + " not found", ne);
            return false;
        }
    }

    private CharSequence getAppLabel(String packageName) {
        try {
            return this.mPm.getApplicationInfoAsUser(packageName, 0, this.mUserId).loadLabel(this.mPm);
        } catch (PackageManager.NameNotFoundException ne) {
            Slog.e(TAG, "Package " + packageName + " not found", ne);
            return packageName;
        }
    }

    private Intent getMoreDetailsActivity() {
        Intent moreDetailsIntent = new Intent(Intent.ACTION_SHOW_SUSPENDED_APP_DETAILS).setPackage(this.mSuspendingPackage);
        ResolveInfo resolvedInfo = this.mPm.resolveActivityAsUser(moreDetailsIntent, 786432, this.mSuspendingUserId);
        if (resolvedInfo != null && resolvedInfo.activityInfo != null && Manifest.permission.SEND_SHOW_SUSPENDED_APP_DETAILS.equals(resolvedInfo.activityInfo.permission)) {
            if (isDigitalWellbingPackage(this.mSuspendingPackage)) {
                moreDetailsIntent.putExtra("android.intent.extra.PACKAGE_NAME", this.mSuspendedPackage).setFlags(335577088);
            } else {
                moreDetailsIntent.putExtra("android.intent.extra.PACKAGE_NAME", this.mSuspendedPackage).setFlags(335544320);
            }
            return moreDetailsIntent;
        }
        return null;
    }

    private Drawable resolveIcon() {
        int iconId = this.mSuppliedDialogInfo != null ? this.mSuppliedDialogInfo.getIconResId() : 0;
        if (iconId != 0 && this.mSuspendingAppResources != null) {
            try {
                return this.mSuspendingAppResources.getDrawable(iconId, getTheme());
            } catch (Resources.NotFoundException e) {
                Slog.e(TAG, "Could not resolve drawable resource id " + iconId);
                return null;
            }
        }
        return null;
    }

    private String resolveTitle() {
        if (this.mSuppliedDialogInfo != null) {
            int titleId = this.mSuppliedDialogInfo.getTitleResId();
            String title = this.mSuppliedDialogInfo.getTitle();
            if (titleId != 0 && this.mSuspendingAppResources != null) {
                try {
                    return this.mSuspendingAppResources.getString(titleId);
                } catch (Resources.NotFoundException e) {
                    Slog.e(TAG, "Could not resolve string resource id " + titleId);
                }
            } else if (title != null) {
                return title;
            }
        }
        return getString(R.string.app_suspended_title);
    }

    private String resolveDialogMessage() {
        CharSequence suspendedAppLabel = getAppLabel(this.mSuspendedPackage);
        if (this.mSuppliedDialogInfo != null) {
            int messageId = this.mSuppliedDialogInfo.getDialogMessageResId();
            String message = this.mSuppliedDialogInfo.getDialogMessage();
            if (messageId != 0 && this.mSuspendingAppResources != null) {
                try {
                    return this.mSuspendingAppResources.getString(messageId, suspendedAppLabel);
                } catch (Resources.NotFoundException e) {
                    Slog.e(TAG, "Could not resolve string resource id " + messageId);
                }
            } else if (message != null) {
                return String.format(getResources().getConfiguration().getLocales().get(0), message, suspendedAppLabel);
            }
        }
        return getString(R.string.app_suspended_default_message, suspendedAppLabel, getAppLabel(this.mSuspendingPackage));
    }

    private String resolveNeutralButtonText() {
        int defaultButtonTextId;
        switch (this.mNeutralButtonAction) {
            case 0:
                if (this.mMoreDetailsIntent != null) {
                    defaultButtonTextId = R.string.app_suspended_more_details;
                    break;
                } else {
                    return null;
                }
            case 1:
                defaultButtonTextId = R.string.app_suspended_unsuspend_message;
                break;
            default:
                Slog.w(TAG, "Unknown neutral button action: " + this.mNeutralButtonAction);
                return null;
        }
        if (this.mSuppliedDialogInfo != null) {
            int buttonTextId = this.mSuppliedDialogInfo.getNeutralButtonTextResId();
            String buttonText = this.mSuppliedDialogInfo.getNeutralButtonText();
            if (buttonTextId != 0 && this.mSuspendingAppResources != null) {
                try {
                    return this.mSuspendingAppResources.getString(buttonTextId);
                } catch (Resources.NotFoundException e) {
                    Slog.e(TAG, "Could not resolve string resource id " + buttonTextId);
                }
            } else if (buttonText != null) {
                return buttonText;
            }
        }
        return getString(defaultButtonTextId);
    }

    @Override // com.android.internal.app.AlertActivity, android.app.Activity
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        this.mPm = getPackageManager();
        this.mUsm = (UsageStatsManager) getSystemService(UsageStatsManager.class);
        getWindow().setType(2008);
        Intent intent = getIntent();
        this.mOptions = intent.getBundleExtra(EXTRA_ACTIVITY_OPTIONS);
        this.mUserId = intent.getIntExtra("android.intent.extra.USER_ID", -1);
        if (this.mUserId < 0) {
            Slog.wtf(TAG, "Invalid user: " + this.mUserId);
            finish();
            return;
        }
        this.mSuspendedPackage = intent.getStringExtra(EXTRA_SUSPENDED_PACKAGE);
        this.mSuspendingPackage = intent.getStringExtra(EXTRA_SUSPENDING_PACKAGE);
        if (Flags.crossUserSuspensionEnabledRo()) {
            this.mSuspendingUserId = intent.getIntExtra(EXTRA_SUSPENDING_USER, this.mUserId);
        } else {
            this.mSuspendingUserId = this.mUserId;
        }
        this.mSuppliedDialogInfo = (SuspendDialogInfo) intent.getParcelableExtra(EXTRA_DIALOG_INFO, SuspendDialogInfo.class);
        this.mOnUnsuspend = (IntentSender) intent.getParcelableExtra(EXTRA_UNSUSPEND_INTENT, IntentSender.class);
        if (isDigitalWellbingPackage(this.mSuspendingPackage) && (getResources().getConfiguration().uiMode & 32) != 0) {
            setTheme(16974545);
        }
        if (this.mSuppliedDialogInfo != null) {
            try {
                this.mSuspendingAppResources = createContextAsUser(UserHandle.of(this.mSuspendingUserId), 0).getPackageManager().getResourcesForApplication(this.mSuspendingPackage);
            } catch (PackageManager.NameNotFoundException ne) {
                Slog.e(TAG, "Could not find resources for " + this.mSuspendingPackage, ne);
            }
        }
        this.mNeutralButtonAction = this.mSuppliedDialogInfo != null ? this.mSuppliedDialogInfo.getNeutralButtonAction() : 0;
        this.mMoreDetailsIntent = this.mNeutralButtonAction == 0 ? getMoreDetailsActivity() : null;
        AlertController.AlertParams ap = this.mAlertParams;
        ap.mIcon = resolveIcon();
        ap.mTitle = resolveTitle();
        ap.mMessage = resolveDialogMessage();
        ap.mPositiveButtonText = getString(17039370);
        ap.mNeutralButtonText = resolveNeutralButtonText();
        ap.mNeutralButtonListener = this;
        ap.mPositiveButtonListener = this;
        getWindow().setGravity(80);
        requestDismissKeyguardIfNeeded(ap.mMessage);
        setupAlert();
        IntentFilter suspendModifiedFilter = new IntentFilter(Intent.ACTION_PACKAGES_SUSPENSION_CHANGED);
        registerReceiverAsUser(this.mSuspendModifiedReceiver, UserHandle.of(this.mUserId), suspendModifiedFilter, null, null);
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(this.mSuspendModifiedReceiver);
    }

    private void requestDismissKeyguardIfNeeded(CharSequence dismissMessage) {
        KeyguardManager km = (KeyguardManager) getSystemService(KeyguardManager.class);
        if (km.isKeyguardLocked()) {
            km.requestDismissKeyguard(this, dismissMessage, new KeyguardManager.KeyguardDismissCallback() { // from class: com.android.internal.app.SuspendedAppActivity.2
                @Override // android.app.KeyguardManager.KeyguardDismissCallback
                public void onDismissError() {
                    Slog.e(SuspendedAppActivity.TAG, "Error while dismissing keyguard. Keeping the dialog visible.");
                }

                @Override // android.app.KeyguardManager.KeyguardDismissCallback
                public void onDismissCancelled() {
                    Slog.w(SuspendedAppActivity.TAG, "Keyguard dismiss was cancelled. Finishing.");
                    SuspendedAppActivity.this.finish();
                }
            });
        }
    }

    @Override // android.content.DialogInterface.OnClickListener
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case -3:
                switch (this.mNeutralButtonAction) {
                    case 0:
                        if (this.mMoreDetailsIntent != null) {
                            startActivityAsUser(this.mMoreDetailsIntent, this.mOptions, UserHandle.of(this.mSuspendingUserId));
                            break;
                        } else {
                            Slog.wtf(TAG, "Neutral button should not have existed!");
                            break;
                        }
                    case 1:
                        IPackageManager ipm = AppGlobals.getPackageManager();
                        try {
                            String[] errored = ipm.setPackagesSuspendedAsUser(new String[]{this.mSuspendedPackage}, false, null, null, null, 0, this.mSuspendingPackage, this.mUserId, this.mUserId);
                            if (ArrayUtils.contains(errored, this.mSuspendedPackage)) {
                                Slog.e(TAG, "Could not unsuspend " + this.mSuspendedPackage);
                                break;
                            } else {
                                Intent reportUnsuspend = new Intent().setAction(Intent.ACTION_PACKAGE_UNSUSPENDED_MANUALLY).putExtra("android.intent.extra.PACKAGE_NAME", this.mSuspendedPackage).setPackage(this.mSuspendingPackage).addFlags(16777216);
                                sendBroadcastAsUser(reportUnsuspend, UserHandle.of(this.mSuspendingUserId));
                                if (this.mOnUnsuspend != null) {
                                    Bundle activityOptions = ActivityOptions.makeBasic().setPendingIntentBackgroundActivityStartMode(1).toBundle();
                                    try {
                                        this.mOnUnsuspend.sendIntent(this, 0, null, null, null, null, activityOptions);
                                        break;
                                    } catch (IntentSender.SendIntentException e) {
                                        Slog.e(TAG, "Error while starting intent " + this.mOnUnsuspend, e);
                                        break;
                                    }
                                }
                            }
                        } catch (RemoteException re) {
                            Slog.e(TAG, "Can't talk to system process", re);
                            break;
                        }
                        break;
                    default:
                        Slog.e(TAG, "Unexpected action on neutral button: " + this.mNeutralButtonAction);
                        break;
                }
        }
        this.mUsm.reportUserInteraction(this.mSuspendingPackage, this.mUserId);
        finish();
    }

    @Override // android.app.Activity
    protected void onPause() {
        super.onPause();
        finish();
    }

    public static Intent createSuspendedAppInterceptIntent(String suspendedPackage, UserPackage suspendingPackage, SuspendDialogInfo dialogInfo, Bundle options, IntentSender onUnsuspend, int userId) {
        if (suspendingPackage != null && isDigitalWellbingPackage(suspendingPackage.packageName)) {
            Intent intent = new Intent().setClassName("android", SuspendedAppActivity.class.getName()).putExtra(EXTRA_SUSPENDED_PACKAGE, suspendedPackage).putExtra(EXTRA_DIALOG_INFO, dialogInfo).putExtra(EXTRA_SUSPENDING_PACKAGE, suspendingPackage.packageName).putExtra(EXTRA_UNSUSPEND_INTENT, onUnsuspend).putExtra(EXTRA_ACTIVITY_OPTIONS, options).putExtra("android.intent.extra.USER_ID", userId).setFlags(276889600);
            if (Flags.crossUserSuspensionEnabledRo()) {
                intent.putExtra(EXTRA_SUSPENDING_USER, suspendingPackage.userId);
            }
            return intent;
        }
        Intent intent2 = new Intent().setClassName("android", SuspendedAppActivity.class.getName()).putExtra(EXTRA_SUSPENDED_PACKAGE, suspendedPackage).putExtra(EXTRA_DIALOG_INFO, dialogInfo).putExtra(EXTRA_SUSPENDING_PACKAGE, suspendingPackage != null ? suspendingPackage.packageName : null).putExtra(EXTRA_UNSUSPEND_INTENT, onUnsuspend).putExtra(EXTRA_ACTIVITY_OPTIONS, options).putExtra("android.intent.extra.USER_ID", userId).setFlags(276824064);
        if (Flags.crossUserSuspensionEnabledRo() && suspendingPackage != null) {
            intent2.putExtra(EXTRA_SUSPENDING_USER, suspendingPackage.userId);
        }
        return intent2;
    }

    private static boolean isDigitalWellbingPackage(String suspendingPackage) {
        return DIGITAL_WELLBEING_PACKAGE.equals(suspendingPackage);
    }
}
