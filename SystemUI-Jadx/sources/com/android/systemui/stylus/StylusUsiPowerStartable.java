package com.android.systemui.stylus;

import android.content.IntentFilter;
import android.hardware.BatteryState;
import android.hardware.input.InputManager;
import android.os.Build;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.view.InputDevice;
import com.android.systemui.CoreStartable;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.FeatureFlagsRelease;
import com.android.systemui.flags.Flags;
import com.android.systemui.log.DebugLogger;
import com.android.systemui.shared.hardware.InputManagerKt;
import com.android.systemui.stylus.StylusManager;
import kotlin.collections.ArraysKt___ArraysKt$asSequence$$inlined$Sequence$4;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Reflection;
import kotlin.sequences.EmptySequence;
import kotlin.sequences.FilteringSequence$iterator$1;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt___SequencesKt;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class StylusUsiPowerStartable implements CoreStartable, StylusManager.StylusCallback {
    public final FeatureFlags featureFlags;
    public final InputManager inputManager;
    public final StylusManager stylusManager;
    public final StylusUsiPowerUI stylusUsiPowerUi;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
        Reflection.getOrCreateKotlinClass(StylusUsiPowerStartable.class).getSimpleName();
    }

    public StylusUsiPowerStartable(StylusManager stylusManager, InputManager inputManager, StylusUsiPowerUI stylusUsiPowerUI, FeatureFlags featureFlags) {
        this.stylusManager = stylusManager;
        this.inputManager = inputManager;
        this.stylusUsiPowerUi = stylusUsiPowerUI;
        this.featureFlags = featureFlags;
    }

    @Override // com.android.systemui.stylus.StylusManager.StylusCallback
    public final void onStylusAdded(int i) {
        InputDevice inputDevice = this.inputManager.getInputDevice(i);
        if (inputDevice != null && !inputDevice.isExternal()) {
            StylusUsiPowerUI stylusUsiPowerUI = this.stylusUsiPowerUi;
            stylusUsiPowerUI.getClass();
            stylusUsiPowerUI.handler.post(new StylusUsiPowerUI$updateSuppression$1(stylusUsiPowerUI, false));
        }
    }

    @Override // com.android.systemui.stylus.StylusManager.StylusCallback
    public final void onStylusUsiBatteryStateChanged(final int i, final BatteryState batteryState) {
        if (batteryState.isPresent() && batteryState.getCapacity() > 0.0f) {
            final StylusUsiPowerUI stylusUsiPowerUI = this.stylusUsiPowerUi;
            stylusUsiPowerUI.getClass();
            stylusUsiPowerUI.handler.post(new Runnable() { // from class: com.android.systemui.stylus.StylusUsiPowerUI$updateBatteryState$1
                @Override // java.lang.Runnable
                public final void run() {
                    boolean z;
                    if (batteryState.getCapacity() == stylusUsiPowerUI.batteryCapacity) {
                        z = true;
                    } else {
                        z = false;
                    }
                    if (!z && batteryState.getCapacity() > 0.0f) {
                        stylusUsiPowerUI.inputDeviceId = Integer.valueOf(i);
                        stylusUsiPowerUI.batteryCapacity = batteryState.getCapacity();
                        int i2 = DebugLogger.$r8$clinit;
                        StylusUsiPowerUI stylusUsiPowerUI2 = stylusUsiPowerUI;
                        boolean z2 = Build.IS_DEBUGGABLE;
                        Reflection.getOrCreateKotlinClass(stylusUsiPowerUI2.getClass()).getSimpleName();
                        StylusUsiPowerUI stylusUsiPowerUI3 = stylusUsiPowerUI;
                        stylusUsiPowerUI3.getClass();
                        stylusUsiPowerUI3.handler.post(new StylusUsiPowerUI$refresh$1(stylusUsiPowerUI3));
                    }
                }
            });
        }
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        boolean z;
        Sequence arraysKt___ArraysKt$asSequence$$inlined$Sequence$4;
        boolean z2;
        if (!((FeatureFlagsRelease) this.featureFlags).isEnabled(Flags.ENABLE_USI_BATTERY_NOTIFICATIONS)) {
            return;
        }
        int[] inputDeviceIds = this.inputManager.getInputDeviceIds();
        boolean z3 = true;
        if (inputDeviceIds.length == 0) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            arraysKt___ArraysKt$asSequence$$inlined$Sequence$4 = EmptySequence.INSTANCE;
        } else {
            arraysKt___ArraysKt$asSequence$$inlined$Sequence$4 = new ArraysKt___ArraysKt$asSequence$$inlined$Sequence$4(inputDeviceIds);
        }
        FilteringSequence$iterator$1 filteringSequence$iterator$1 = new FilteringSequence$iterator$1(SequencesKt___SequencesKt.mapNotNull(arraysKt___ArraysKt$asSequence$$inlined$Sequence$4, new Function1() { // from class: com.android.systemui.stylus.StylusUsiPowerStartable$hostDeviceSupportsStylusInput$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return StylusUsiPowerStartable.this.inputManager.getInputDevice(((Number) obj).intValue());
            }
        }));
        while (true) {
            if (filteringSequence$iterator$1.hasNext()) {
                InputDevice inputDevice = (InputDevice) filteringSequence$iterator$1.next();
                if (inputDevice.supportsSource(16386) && !inputDevice.isExternal()) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                if (z2) {
                    break;
                }
            } else {
                z3 = false;
                break;
            }
        }
        if (!z3) {
            return;
        }
        StylusUsiPowerUI stylusUsiPowerUI = this.stylusUsiPowerUi;
        stylusUsiPowerUI.getClass();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("StylusUsiPowerUI.dismiss");
        intentFilter.addAction("StylusUsiPowerUI.click");
        stylusUsiPowerUI.context.registerReceiverAsUser(stylusUsiPowerUI.receiver, UserHandle.ALL, intentFilter, "android.permission.DEVICE_POWER", stylusUsiPowerUI.handler, 4);
        final StylusManager stylusManager = this.stylusManager;
        stylusManager.stylusCallbacks.add(this);
        stylusManager.handler.post(new Runnable() { // from class: com.android.systemui.stylus.StylusManager$startListener$1
            @Override // java.lang.Runnable
            public final void run() {
                if (StylusManager.this.hasStarted) {
                    return;
                }
                int i = DebugLogger.$r8$clinit;
                boolean z4 = Build.IS_DEBUGGABLE;
                Reflection.getOrCreateKotlinClass(StylusManager.class).getSimpleName();
                final StylusManager stylusManager2 = StylusManager.this;
                stylusManager2.hasStarted = true;
                InputManagerKt.hasInputDevice(stylusManager2.inputManager, new Function1() { // from class: com.android.systemui.stylus.StylusManager$startListener$1.2
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        boolean z5;
                        boolean z6;
                        InputDevice inputDevice2 = (InputDevice) obj;
                        boolean z7 = false;
                        if (inputDevice2.supportsSource(16386) && !inputDevice2.isExternal()) {
                            z5 = true;
                        } else {
                            z5 = false;
                        }
                        if (z5) {
                            StylusManager stylusManager3 = StylusManager.this;
                            BatteryState batteryState = inputDevice2.getBatteryState();
                            String str = StylusManager.TAG;
                            stylusManager3.getClass();
                            if (batteryState.isPresent() && batteryState.getCapacity() > 0.0f) {
                                z6 = true;
                            } else {
                                z6 = false;
                            }
                            if (z6) {
                                z7 = true;
                            }
                        }
                        return Boolean.valueOf(z7);
                    }
                });
                StylusManager stylusManager3 = StylusManager.this;
                for (int i2 : stylusManager3.inputManager.getInputDeviceIds()) {
                    InputDevice inputDevice2 = stylusManager3.inputManager.getInputDevice(i2);
                    if (inputDevice2 != null && inputDevice2.supportsSource(16386)) {
                        ((ArrayMap) stylusManager3.inputDeviceAddressMap).put(Integer.valueOf(i2), inputDevice2.getBluetoothAddress());
                        if (!inputDevice2.isExternal()) {
                            stylusManager3.registerBatteryListener(i2);
                        } else if (inputDevice2.getBluetoothAddress() != null) {
                            stylusManager3.onStylusBluetoothConnected(i2, inputDevice2.getBluetoothAddress());
                        }
                    }
                }
                StylusManager stylusManager4 = StylusManager.this;
                stylusManager4.inputManager.registerInputDeviceListener(stylusManager4, stylusManager4.handler);
            }
        });
    }

    @Override // com.android.systemui.stylus.StylusManager.StylusCallback
    public final void onStylusBluetoothChargingStateChanged() {
    }

    @Override // com.android.systemui.stylus.StylusManager.StylusCallback
    public final void onStylusBluetoothConnected() {
    }

    @Override // com.android.systemui.stylus.StylusManager.StylusCallback
    public final void onStylusBluetoothDisconnected() {
    }

    @Override // com.android.systemui.stylus.StylusManager.StylusCallback
    public final void onStylusFirstUsed() {
    }

    @Override // com.android.systemui.stylus.StylusManager.StylusCallback
    public final void onStylusRemoved() {
    }
}
