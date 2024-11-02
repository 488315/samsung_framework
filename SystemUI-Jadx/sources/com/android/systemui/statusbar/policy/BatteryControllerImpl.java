package com.android.systemui.statusbar.policy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.util.Log;
import com.android.settingslib.fuelgauge.Estimate;
import com.android.systemui.Dumpable;
import com.android.systemui.bixby2.actionresult.ActionResults;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.demomode.DemoModeController;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.power.EnhancedEstimates;
import com.android.systemui.statusbar.policy.BatteryController;
import com.sec.ims.IMSParameter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class BatteryControllerImpl extends BroadcastReceiver implements BatteryController, Dumpable {
    public static final boolean DEBUG = Log.isLoggable("BatteryController", 3);
    public boolean mAodPowerSave;
    public final Handler mBgHandler;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public boolean mCharged;
    public boolean mCharging;
    public final Context mContext;
    public final DemoModeController mDemoModeController;
    public final DumpManager mDumpManager;
    public Estimate mEstimate;
    public final EnhancedEstimates mEstimates;
    public int mLevel;
    public final Handler mMainHandler;
    public boolean mPluggedIn;
    public final PowerManager mPowerManager;
    public boolean mPowerSave;
    public boolean mWirelessCharging;
    public final ArrayList mChangeCallbacks = new ArrayList();
    public final ArrayList mFetchCallbacks = new ArrayList();
    public int mBatteryStatus = 1;
    public int mBatteryHealth = 1;
    public int mBatteryOnline = 1;
    public boolean mIsDirectPowerMode = false;
    public boolean mStateUnknown = false;
    public boolean mIsBatteryDefender = false;
    public boolean mTestMode = false;
    boolean mHasReceivedBattery = false;
    public final Object mEstimateLock = new Object();
    public boolean mFetchingEstimate = false;
    public final AtomicReference mPowerSaverStartView = new AtomicReference();

    public BatteryControllerImpl(Context context, EnhancedEstimates enhancedEstimates, PowerManager powerManager, BroadcastDispatcher broadcastDispatcher, DemoModeController demoModeController, DumpManager dumpManager, Handler handler, Handler handler2) {
        this.mContext = context;
        this.mMainHandler = handler;
        this.mBgHandler = handler2;
        this.mPowerManager = powerManager;
        this.mEstimates = enhancedEstimates;
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mDemoModeController = demoModeController;
        this.mDumpManager = dumpManager;
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void addCallback(Object obj) {
        BatteryController.BatteryStateChangeCallback batteryStateChangeCallback = (BatteryController.BatteryStateChangeCallback) obj;
        synchronized (this.mChangeCallbacks) {
            this.mChangeCallbacks.add(batteryStateChangeCallback);
        }
        if (this.mHasReceivedBattery) {
            batteryStateChangeCallback.onBatteryLevelChanged(this.mLevel, this.mPluggedIn, this.mCharging);
            batteryStateChangeCallback.onBatteryLevelChanged(this.mLevel, this.mPluggedIn, this.mCharging, this.mBatteryStatus, this.mBatteryHealth, this.mBatteryOnline, this.mIsDirectPowerMode);
            batteryStateChangeCallback.onPowerSaveChanged(this.mPowerSave);
            batteryStateChangeCallback.onBatteryUnknownStateChanged(this.mStateUnknown);
            batteryStateChangeCallback.onWirelessChargingChanged(this.mWirelessCharging);
            batteryStateChangeCallback.onIsBatteryDefenderChanged(this.mIsBatteryDefender);
        }
    }

    @Override // com.android.systemui.demomode.DemoMode
    public final List demoCommands() {
        ArrayList arrayList = new ArrayList();
        arrayList.add("battery");
        return arrayList;
    }

    @Override // com.android.systemui.demomode.DemoModeCommandReceiver
    public final void dispatchDemoCommand(Bundle bundle, String str) {
        this.mDemoModeController.getClass();
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("BatteryController state:");
        printWriter.print("  mLevel=");
        printWriter.println(this.mLevel);
        printWriter.print("  mPluggedIn=");
        printWriter.println(this.mPluggedIn);
        printWriter.print("  mCharging=");
        printWriter.println(this.mCharging);
        printWriter.print("  mCharged=");
        printWriter.println(this.mCharged);
        printWriter.print("  mIsBatteryDefender=");
        printWriter.println(this.mIsBatteryDefender);
        printWriter.print("  mPowerSave=");
        printWriter.println(this.mPowerSave);
        printWriter.print("  mStateUnknown=");
        printWriter.println(this.mStateUnknown);
        printWriter.print("  mBatteryOnline=");
        printWriter.println(this.mBatteryOnline);
        printWriter.print("  mBatteryStatus=");
        printWriter.println(this.mBatteryStatus);
        printWriter.print("  mBatteryHealth=");
        printWriter.println(this.mBatteryHealth);
        printWriter.print("  mIsDirectPowerMode=");
        printWriter.println(this.mIsDirectPowerMode);
    }

    public final void fireBatteryLevelChanged() {
        synchronized (this.mChangeCallbacks) {
            int size = this.mChangeCallbacks.size();
            for (int i = 0; i < size; i++) {
                ((BatteryController.BatteryStateChangeCallback) this.mChangeCallbacks.get(i)).onBatteryLevelChanged(this.mLevel, this.mPluggedIn, this.mCharging, this.mBatteryStatus, this.mBatteryHealth, this.mBatteryOnline, this.mIsDirectPowerMode);
                ((BatteryController.BatteryStateChangeCallback) this.mChangeCallbacks.get(i)).onBatteryLevelChanged(this.mLevel, this.mPluggedIn, this.mCharging);
            }
        }
    }

    public final void fireBatteryUnknownStateChanged() {
        synchronized (this.mChangeCallbacks) {
            int size = this.mChangeCallbacks.size();
            for (int i = 0; i < size; i++) {
                ((BatteryController.BatteryStateChangeCallback) this.mChangeCallbacks.get(i)).onBatteryUnknownStateChanged(this.mStateUnknown);
            }
        }
    }

    public final void fireIsBatteryDefenderChanged() {
        synchronized (this.mChangeCallbacks) {
            int size = this.mChangeCallbacks.size();
            for (int i = 0; i < size; i++) {
                ((BatteryController.BatteryStateChangeCallback) this.mChangeCallbacks.get(i)).onIsBatteryDefenderChanged(this.mIsBatteryDefender);
            }
        }
    }

    public final void firePowerSaveChanged() {
        synchronized (this.mChangeCallbacks) {
            int size = this.mChangeCallbacks.size();
            for (int i = 0; i < size; i++) {
                ((BatteryController.BatteryStateChangeCallback) this.mChangeCallbacks.get(i)).onPowerSaveChanged(this.mPowerSave);
            }
        }
    }

    @Override // com.android.systemui.demomode.DemoModeCommandReceiver
    public final void onDemoModeFinished() {
        registerReceiver$2();
        updatePowerSave();
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(final Context context, Intent intent) {
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        String action = intent.getAction();
        boolean z6 = true;
        if (action.equals("android.intent.action.BATTERY_CHANGED")) {
            if (this.mTestMode && !intent.getBooleanExtra("testmode", false)) {
                return;
            }
            this.mHasReceivedBattery = true;
            this.mLevel = (int) ((intent.getIntExtra(ActionResults.RESULT_SET_VOLUME_SUCCESS, 0) * 100.0f) / intent.getIntExtra("scale", 100));
            if (intent.getIntExtra("plugged", 0) != 0) {
                z = true;
            } else {
                z = false;
            }
            this.mPluggedIn = z;
            int intExtra = intent.getIntExtra(IMSParameter.CALL.STATUS, 1);
            if (intExtra == 5) {
                z2 = true;
            } else {
                z2 = false;
            }
            this.mCharged = z2;
            if (!z2 && intExtra != 2) {
                z3 = false;
            } else {
                z3 = true;
            }
            this.mCharging = z3;
            boolean z7 = this.mWirelessCharging;
            if (z3 && intent.getIntExtra("plugged", 0) == 4) {
                z4 = true;
            } else {
                z4 = false;
            }
            if (z7 != z4) {
                this.mWirelessCharging = !this.mWirelessCharging;
                synchronized (this.mChangeCallbacks) {
                    this.mChangeCallbacks.forEach(new Consumer() { // from class: com.android.systemui.statusbar.policy.BatteryControllerImpl$$ExternalSyntheticLambda1
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            ((BatteryController.BatteryStateChangeCallback) obj).onWirelessChargingChanged(BatteryControllerImpl.this.mWirelessCharging);
                        }
                    });
                }
            }
            boolean z8 = !intent.getBooleanExtra("present", true);
            if (z8 != this.mStateUnknown) {
                this.mStateUnknown = z8;
                fireBatteryUnknownStateChanged();
            }
            if (intent.getIntExtra("android.os.extra.CHARGING_STATUS", 1) == 4) {
                z5 = true;
            } else {
                z5 = false;
            }
            if (z5 != this.mIsBatteryDefender) {
                this.mIsBatteryDefender = z5;
                fireIsBatteryDefenderChanged();
            }
            this.mBatteryStatus = intExtra;
            this.mBatteryHealth = intent.getIntExtra("health", 1);
            this.mBatteryOnline = intent.getIntExtra("online", 1);
            if ((intent.getIntExtra("misc_event", 0) & 16384) != 16384) {
                z6 = false;
            }
            this.mIsDirectPowerMode = z6;
            fireBatteryLevelChanged();
            return;
        }
        if (action.equals("android.os.action.POWER_SAVE_MODE_CHANGED")) {
            updatePowerSave();
        } else if (action.equals("com.android.systemui.BATTERY_LEVEL_TEST")) {
            this.mTestMode = true;
            this.mMainHandler.post(new Runnable() { // from class: com.android.systemui.statusbar.policy.BatteryControllerImpl.1
                public final int mSavedLevel;
                public final boolean mSavedPluggedIn;
                public int mCurrentLevel = 0;
                public int mIncrement = 1;
                public final Intent mTestIntent = new Intent("android.intent.action.BATTERY_CHANGED");

                {
                    this.mSavedLevel = BatteryControllerImpl.this.mLevel;
                    this.mSavedPluggedIn = BatteryControllerImpl.this.mPluggedIn;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    int i = this.mCurrentLevel;
                    int i2 = 0;
                    if (i < 0) {
                        BatteryControllerImpl.this.mTestMode = false;
                        this.mTestIntent.putExtra(ActionResults.RESULT_SET_VOLUME_SUCCESS, this.mSavedLevel);
                        this.mTestIntent.putExtra("plugged", this.mSavedPluggedIn);
                        this.mTestIntent.putExtra("testmode", false);
                    } else {
                        this.mTestIntent.putExtra(ActionResults.RESULT_SET_VOLUME_SUCCESS, i);
                        Intent intent2 = this.mTestIntent;
                        if (this.mIncrement > 0) {
                            i2 = 1;
                        }
                        intent2.putExtra("plugged", i2);
                        this.mTestIntent.putExtra("testmode", true);
                    }
                    context.sendBroadcast(this.mTestIntent);
                    BatteryControllerImpl batteryControllerImpl = BatteryControllerImpl.this;
                    if (!batteryControllerImpl.mTestMode) {
                        return;
                    }
                    int i3 = this.mCurrentLevel;
                    int i4 = this.mIncrement;
                    int i5 = i3 + i4;
                    this.mCurrentLevel = i5;
                    if (i5 == 100) {
                        this.mIncrement = i4 * (-1);
                    }
                    batteryControllerImpl.mMainHandler.postDelayed(this, 200L);
                }
            });
        }
    }

    public final void registerReceiver$2() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.BATTERY_CHANGED");
        intentFilter.addAction("android.os.action.POWER_SAVE_MODE_CHANGED");
        intentFilter.addAction("com.android.systemui.BATTERY_LEVEL_TEST");
        this.mBroadcastDispatcher.registerReceiver(intentFilter, this);
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void removeCallback(Object obj) {
        BatteryController.BatteryStateChangeCallback batteryStateChangeCallback = (BatteryController.BatteryStateChangeCallback) obj;
        synchronized (this.mChangeCallbacks) {
            this.mChangeCallbacks.remove(batteryStateChangeCallback);
        }
    }

    public final void updatePowerSave() {
        String str;
        boolean isPowerSaveMode = this.mPowerManager.isPowerSaveMode();
        if (isPowerSaveMode != this.mPowerSave) {
            this.mPowerSave = isPowerSaveMode;
            this.mAodPowerSave = this.mPowerManager.getPowerSaveState(14).batterySaverEnabled;
            if (DEBUG) {
                if (this.mPowerSave) {
                    str = "on";
                } else {
                    str = "off";
                }
                Log.d("BatteryController", "Power save is ".concat(str));
            }
            firePowerSaveChanged();
        }
    }
}
