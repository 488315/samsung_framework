package com.android.systemui.qrcodescanner.controller;

import android.R;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.os.Handler;
import android.provider.DeviceConfig;
import android.provider.Settings;
import androidx.core.widget.NestedScrollView$$ExternalSyntheticOutline0;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.policy.CallbackController;
import com.android.systemui.util.DeviceConfigProxy;
import com.android.systemui.util.settings.SecureSettings;
import com.android.systemui.util.settings.SecureSettingsImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class QRCodeScannerController implements CallbackController {
    public final boolean mConfigEnableLockScreenButton;
    public final Context mContext;
    public final DeviceConfigProxy mDeviceConfigProxy;
    public final Executor mExecutor;
    public boolean mQRCodeScannerEnabled;
    public final SecureSettings mSecureSettings;
    public final UserTracker mUserTracker;
    public final ArrayList mCallbacks = new ArrayList();
    public HashMap mQRCodeScannerPreferenceObserver = new HashMap();
    public QRCodeScannerController$$ExternalSyntheticLambda2 mOnDefaultQRCodeScannerChangedListener = null;
    public UserTracker.Callback mUserChangedListener = null;
    public Intent mIntent = null;
    public String mQRCodeScannerActivity = null;
    public final AtomicInteger mQRCodeScannerPreferenceChangeEvents = new AtomicInteger(0);
    public final AtomicInteger mDefaultQRCodeScannerChangeEvents = new AtomicInteger(0);
    public Boolean mIsCameraAvailable = null;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* renamed from: com.android.systemui.qrcodescanner.controller.QRCodeScannerController$1, reason: invalid class name */
    /* loaded from: classes2.dex */
    public final class AnonymousClass1 extends ContentObserver {
        public AnonymousClass1(Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            QRCodeScannerController.this.mExecutor.execute(new QRCodeScannerController$$ExternalSyntheticLambda1(this, 2));
        }
    }

    public QRCodeScannerController(Context context, Executor executor, SecureSettings secureSettings, DeviceConfigProxy deviceConfigProxy, UserTracker userTracker) {
        this.mContext = context;
        this.mExecutor = executor;
        this.mSecureSettings = secureSettings;
        this.mDeviceConfigProxy = deviceConfigProxy;
        this.mUserTracker = userTracker;
        this.mConfigEnableLockScreenButton = context.getResources().getBoolean(R.bool.config_LTE_eri_for_network_name);
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void addCallback(Object obj) {
        Callback callback = (Callback) obj;
        if (isCameraAvailable()) {
            synchronized (this.mCallbacks) {
                this.mCallbacks.add(callback);
            }
        }
    }

    public final boolean isAbleToOpenCameraApp() {
        boolean z;
        Intent intent = this.mIntent;
        if (intent == null) {
            return false;
        }
        if (intent.getComponent() == null) {
            z = false;
        } else {
            z = !this.mContext.getPackageManager().queryIntentActivities(intent, 819200).isEmpty();
        }
        if (!z) {
            return false;
        }
        return true;
    }

    public final boolean isCameraAvailable() {
        if (this.mIsCameraAvailable == null) {
            this.mIsCameraAvailable = Boolean.valueOf(this.mContext.getPackageManager().hasSystemFeature("android.hardware.camera"));
        }
        return this.mIsCameraAvailable.booleanValue();
    }

    public final void registerQRCodePreferenceObserver() {
        if (!this.mConfigEnableLockScreenButton) {
            return;
        }
        int userId = ((UserTrackerImpl) this.mUserTracker).getUserId();
        if (this.mQRCodeScannerPreferenceObserver.getOrDefault(Integer.valueOf(userId), null) != null) {
            return;
        }
        this.mExecutor.execute(new QRCodeScannerController$$ExternalSyntheticLambda1(this, 1));
        this.mQRCodeScannerPreferenceObserver.put(Integer.valueOf(userId), new AnonymousClass1(null));
        SecureSettings secureSettings = this.mSecureSettings;
        ((SecureSettingsImpl) secureSettings).getClass();
        secureSettings.registerContentObserverForUser(Settings.Secure.getUriFor("lock_screen_show_qr_code_scanner"), false, (ContentObserver) this.mQRCodeScannerPreferenceObserver.get(Integer.valueOf(userId)), userId);
    }

    /* JADX WARN: Type inference failed for: r3v4, types: [com.android.systemui.qrcodescanner.controller.QRCodeScannerController$$ExternalSyntheticLambda2, android.provider.DeviceConfig$OnPropertiesChangedListener] */
    public final void registerQRCodeScannerChangeObservers(int... iArr) {
        if (!isCameraAvailable()) {
            return;
        }
        for (int i : iArr) {
            Executor executor = this.mExecutor;
            if (i != 0) {
                if (i != 1) {
                    NestedScrollView$$ExternalSyntheticOutline0.m("Unrecognised event: ", i, "QRCodeScannerController");
                } else {
                    this.mQRCodeScannerPreferenceChangeEvents.incrementAndGet();
                    registerQRCodePreferenceObserver();
                    if (this.mUserChangedListener == null) {
                        UserTracker.Callback callback = new UserTracker.Callback() { // from class: com.android.systemui.qrcodescanner.controller.QRCodeScannerController.2
                            @Override // com.android.systemui.settings.UserTracker.Callback
                            public final void onUserChanged(int i2, Context context) {
                                QRCodeScannerController qRCodeScannerController = QRCodeScannerController.this;
                                qRCodeScannerController.registerQRCodePreferenceObserver();
                                qRCodeScannerController.updateQRCodeScannerPreferenceDetails(true);
                            }
                        };
                        this.mUserChangedListener = callback;
                        ((UserTrackerImpl) this.mUserTracker).addCallback(callback, executor);
                    }
                }
            } else {
                this.mDefaultQRCodeScannerChangeEvents.incrementAndGet();
                if (this.mOnDefaultQRCodeScannerChangedListener == null) {
                    executor.execute(new QRCodeScannerController$$ExternalSyntheticLambda1(this, 0));
                    ?? r3 = new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.systemui.qrcodescanner.controller.QRCodeScannerController$$ExternalSyntheticLambda2
                        public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                            QRCodeScannerController qRCodeScannerController = QRCodeScannerController.this;
                            qRCodeScannerController.getClass();
                            if ("systemui".equals(properties.getNamespace()) && properties.getKeyset().contains("default_qr_code_scanner")) {
                                qRCodeScannerController.updateQRCodeScannerActivityDetails();
                                qRCodeScannerController.updateQRCodeScannerPreferenceDetails(true);
                            }
                        }
                    };
                    this.mOnDefaultQRCodeScannerChangedListener = r3;
                    this.mDeviceConfigProxy.getClass();
                    DeviceConfig.addOnPropertiesChangedListener("systemui", executor, (DeviceConfig.OnPropertiesChangedListener) r3);
                }
            }
        }
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void removeCallback(Object obj) {
        Callback callback = (Callback) obj;
        if (isCameraAvailable()) {
            synchronized (this.mCallbacks) {
                this.mCallbacks.remove(callback);
            }
        }
    }

    public final void unregisterQRCodeScannerChangeObservers(int... iArr) {
        if (!isCameraAvailable()) {
            return;
        }
        for (int i : iArr) {
            if (i != 0) {
                if (i != 1) {
                    NestedScrollView$$ExternalSyntheticOutline0.m("Unrecognised event: ", i, "QRCodeScannerController");
                } else {
                    UserTracker userTracker = this.mUserTracker;
                    if (userTracker != null && this.mQRCodeScannerPreferenceChangeEvents.decrementAndGet() == 0) {
                        if (this.mConfigEnableLockScreenButton) {
                            this.mQRCodeScannerPreferenceObserver.forEach(new BiConsumer() { // from class: com.android.systemui.qrcodescanner.controller.QRCodeScannerController$$ExternalSyntheticLambda0
                                @Override // java.util.function.BiConsumer
                                public final void accept(Object obj, Object obj2) {
                                    QRCodeScannerController.this.mSecureSettings.unregisterContentObserver((ContentObserver) obj2);
                                }
                            });
                            this.mQRCodeScannerPreferenceObserver = new HashMap();
                            ((SecureSettingsImpl) this.mSecureSettings).putStringForUser(((UserTrackerImpl) userTracker).getUserId(), "show_qr_code_scanner_setting", null);
                        }
                        ((UserTrackerImpl) userTracker).removeCallback(this.mUserChangedListener);
                        this.mUserChangedListener = null;
                        this.mQRCodeScannerEnabled = false;
                    }
                }
            } else if (this.mOnDefaultQRCodeScannerChangedListener != null && this.mDefaultQRCodeScannerChangeEvents.decrementAndGet() == 0) {
                QRCodeScannerController$$ExternalSyntheticLambda2 qRCodeScannerController$$ExternalSyntheticLambda2 = this.mOnDefaultQRCodeScannerChangedListener;
                this.mDeviceConfigProxy.getClass();
                DeviceConfig.removeOnPropertiesChangedListener(qRCodeScannerController$$ExternalSyntheticLambda2);
                this.mOnDefaultQRCodeScannerChangedListener = null;
                this.mQRCodeScannerActivity = null;
                this.mIntent = null;
            }
        }
    }

    public final void updateQRCodeScannerActivityDetails() {
        boolean z;
        ArrayList arrayList;
        this.mDeviceConfigProxy.getClass();
        String string = DeviceConfig.getString("systemui", "default_qr_code_scanner", "");
        if (Objects.equals(string, "")) {
            string = this.mContext.getResources().getString(R.string.face_acquired_too_close);
        }
        String str = this.mQRCodeScannerActivity;
        Intent intent = new Intent();
        if (string != null) {
            intent.setComponent(ComponentName.unflattenFromString(string));
            intent.addFlags(335544320);
        }
        if (intent.getComponent() == null) {
            z = false;
        } else {
            z = !this.mContext.getPackageManager().queryIntentActivities(intent, 537698816).isEmpty();
        }
        if (z) {
            this.mQRCodeScannerActivity = string;
            this.mIntent = intent;
        } else {
            this.mQRCodeScannerActivity = null;
            this.mIntent = null;
        }
        if (!Objects.equals(this.mQRCodeScannerActivity, str)) {
            synchronized (this.mCallbacks) {
                arrayList = (ArrayList) this.mCallbacks.clone();
            }
            arrayList.forEach(new QRCodeScannerController$$ExternalSyntheticLambda3(0));
        }
    }

    public final void updateQRCodeScannerPreferenceDetails(boolean z) {
        ArrayList arrayList;
        if (!this.mConfigEnableLockScreenButton) {
            return;
        }
        boolean z2 = this.mQRCodeScannerEnabled;
        boolean z3 = false;
        if (this.mSecureSettings.getIntForUser(0, ((UserTrackerImpl) this.mUserTracker).getUserId(), "lock_screen_show_qr_code_scanner") != 0) {
            z3 = true;
        }
        this.mQRCodeScannerEnabled = z3;
        if (z) {
            ((SecureSettingsImpl) this.mSecureSettings).putStringForUser(((UserTrackerImpl) this.mUserTracker).getUserId(), "show_qr_code_scanner_setting", this.mQRCodeScannerActivity);
        }
        if (!Objects.equals(Boolean.valueOf(this.mQRCodeScannerEnabled), Boolean.valueOf(z2))) {
            synchronized (this.mCallbacks) {
                arrayList = (ArrayList) this.mCallbacks.clone();
            }
            arrayList.forEach(new QRCodeScannerController$$ExternalSyntheticLambda3(1));
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public interface Callback {
        void onQRCodeScannerActivityChanged();

        default void onQRCodeScannerPreferenceChanged() {
        }
    }
}
