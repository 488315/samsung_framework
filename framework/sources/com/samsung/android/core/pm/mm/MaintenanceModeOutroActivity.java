package com.samsung.android.core.pm.mm;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.UserManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.internal.R;
import com.samsung.android.core.pm.mm.MaintenanceModeOutroActivity;

/* loaded from: classes5.dex */
public class MaintenanceModeOutroActivity extends Activity {
    private static final long EXIT_PROGRESS_TIMEOUT = 120000;
    private static final String TAG = "MaintenanceMode";
    private String mCallingPackage;
    private Context mContext;
    private Button mExitButton;
    private View mProgressView;
    private Resources mResources;
    private View mRootView;
    private WindowManager.LayoutParams mViewWindowParams;
    private WindowManager mWm;
    private boolean mIsTablet = false;
    private boolean mIsFold = false;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context applicationContext = getApplicationContext();
        this.mContext = applicationContext;
        this.mResources = applicationContext.getResources();
        this.mCallingPackage = getCallingPackage();
        if (ActivityManager.getCurrentUser() != 77) {
            finish();
            return;
        }
        this.mIsTablet = MaintenanceModeUtils.isTablet();
        this.mIsFold = MaintenanceModeUtils.isFold();
        setContentView(getResources().getConfiguration());
        init();
    }

    private void setContentView(Configuration config) {
        ActionBar actionBar;
        MaintenanceModeUtils.configureLayout(this, this.mResources, config, this.mIsTablet, this.mIsFold, R.layout.activity_maintenance_mode_outro, R.layout.activity_maintenance_mode_outro_land, R.id.maintenance_mode_outro_body_container);
        if (this.mCallingPackage != null && (actionBar = getActionBar()) != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        TextView outroTextView = (TextView) findViewById(R.id.maintenance_mode_outro_textview);
        if (this.mIsTablet) {
            outroTextView.setText(R.string.maintenance_mode_outro_textview_message_tablet);
        } else {
            outroTextView.setText(R.string.maintenance_mode_outro_textview_message_phone);
        }
        if (this.mIsTablet) {
            ImageView imageView = (ImageView) findViewById(R.id.maintenance_mode_outro_imageview);
            imageView.setMaxWidth(this.mResources.getDimensionPixelSize(R.dimen.maintenance_mode_image_max_width_tablet));
        }
        Button button = (Button) findViewById(R.id.maintenance_mode_outro_exit_button);
        this.mExitButton = button;
        if (this.mIsTablet) {
            button.setWidth(this.mResources.getDimensionPixelSize(R.dimen.maintenance_mode_body_button_width_tablet));
        }
        this.mExitButton.setTextSize(0, MaintenanceModeUtils.getFontSize(this.mContext, R.dimen.maintenance_mode_common_button_text_size));
        this.mExitButton.setOnClickListener(new View.OnClickListener() { // from class: com.samsung.android.core.pm.mm.MaintenanceModeOutroActivity$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MaintenanceModeOutroActivity.this.lambda$setContentView$0(view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setContentView$0(View v) {
        showDialog();
    }

    private void init() {
        this.mRootView = getWindow().getDecorView();
        this.mWm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        prepareProgressView();
    }

    private void prepareProgressView() {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -1, 0, 0, 2024, 131328, -3);
        this.mViewWindowParams = layoutParams;
        layoutParams.gravity = 17;
        this.mViewWindowParams.privateFlags |= 16;
        this.mViewWindowParams.screenOrientation = 1;
        this.mViewWindowParams.layoutInDisplayCutoutMode = 1;
        this.mViewWindowParams.setFitInsetsSides(0);
        this.mProgressView = LayoutInflater.from(this).inflate(R.layout.view_maintenance_mode_dump, (ViewGroup) null);
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item != null && item.getItemId() == 16908332) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override // android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setContentView(newConfig);
    }

    private void showDialog() {
        int i;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (this.mIsTablet) {
            i = R.string.maintenance_mode_outro_dialog_message_tablet;
        } else {
            i = R.string.maintenance_mode_outro_dialog_message_phone;
        }
        AlertDialog.Builder builder2 = builder.setMessage(i).setPositiveButton(R.string.maintenance_mode_dialog_button_text_restart, new DialogInterface.OnClickListener() { // from class: com.samsung.android.core.pm.mm.MaintenanceModeOutroActivity$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                MaintenanceModeOutroActivity.this.lambda$showDialog$1(dialogInterface, i2);
            }
        }).setNegativeButton(R.string.maintenance_mode_dialog_button_text_cancel, new DialogInterface.OnClickListener() { // from class: com.samsung.android.core.pm.mm.MaintenanceModeOutroActivity$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                MaintenanceModeOutroActivity.lambda$showDialog$2(dialogInterface, i2);
            }
        });
        AlertDialog dialog = builder2.create();
        dialog.getWindow().setGravity(80);
        dialog.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showDialog$1(DialogInterface dialog, int which) {
        confirmSecureLock();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$showDialog$2(DialogInterface dialog, int which) {
    }

    private void confirmSecureLock() {
        BiometricPrompt.Builder builder = new BiometricPrompt.Builder(this.mContext);
        builder.setUseDefaultTitle();
        builder.setAllowedAuthenticators(33023);
        BiometricPrompt biometricPrompt = builder.build();
        biometricPrompt.authenticateUser(new CancellationSignal(), getMainExecutor(), new AnonymousClass1(), 0);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.samsung.android.core.pm.mm.MaintenanceModeOutroActivity$1, reason: invalid class name */
    /* loaded from: classes5.dex */
    public class AnonymousClass1 extends BiometricPrompt.AuthenticationCallback {
        AnonymousClass1() {
        }

        @Override // android.hardware.biometrics.BiometricPrompt.AuthenticationCallback, android.hardware.biometrics.BiometricAuthenticator.AuthenticationCallback
        public void onAuthenticationError(int errorCode, CharSequence errString) {
            super.onAuthenticationError(errorCode, errString);
        }

        @Override // android.hardware.biometrics.BiometricPrompt.AuthenticationCallback
        public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
            super.onAuthenticationSucceeded(result);
            MaintenanceModeOutroActivity.this.mExitButton.setClickable(false);
            MaintenanceModeOutroActivity.this.mWm.addView(MaintenanceModeOutroActivity.this.mProgressView, MaintenanceModeOutroActivity.this.mViewWindowParams);
            new Thread(new Runnable() { // from class: com.samsung.android.core.pm.mm.MaintenanceModeOutroActivity$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    MaintenanceModeOutroActivity.AnonymousClass1.this.lambda$onAuthenticationSucceeded$0();
                }
            }).start();
            MaintenanceModeOutroActivity.this.mRootView.postDelayed(new Runnable() { // from class: com.samsung.android.core.pm.mm.MaintenanceModeOutroActivity$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    MaintenanceModeOutroActivity.AnonymousClass1.this.lambda$onAuthenticationSucceeded$1();
                }
            }, 120000L);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onAuthenticationSucceeded$0() {
            MaintenanceModeOutroActivity.this.exitMaintenanceMode();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onAuthenticationSucceeded$1() {
            MaintenanceModeOutroActivity.this.mWm.removeView(MaintenanceModeOutroActivity.this.mProgressView);
            MaintenanceModeOutroActivity.this.mExitButton.setClickable(true);
        }

        @Override // android.hardware.biometrics.BiometricPrompt.AuthenticationCallback, android.hardware.biometrics.BiometricAuthenticator.AuthenticationCallback
        public void onAuthenticationFailed() {
            super.onAuthenticationFailed();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void exitMaintenanceMode() {
        try {
            UserManager um = (UserManager) this.mContext.getSystemService("user");
            um.removeUser(77);
        } catch (Exception e) {
            Log.i("MaintenanceMode", "Exception", e);
        }
    }
}
