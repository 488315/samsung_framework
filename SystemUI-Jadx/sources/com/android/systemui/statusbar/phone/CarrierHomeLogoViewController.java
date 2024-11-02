package com.android.systemui.statusbar.phone;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.provider.Settings;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.ArraySet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.plugins.DarkIconDispatcher;
import com.android.systemui.slimindicator.SlimIndicatorViewMediator;
import com.android.systemui.slimindicator.SlimIndicatorViewMediatorImpl;
import com.android.systemui.slimindicator.SlimIndicatorViewSubscriber;
import com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator;
import com.android.systemui.statusbar.pipeline.mobile.util.SimCardInfoUtil;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
import com.android.systemui.util.CarrierConfigTracker;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.ViewController;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.LinkedList;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt__MathJVMKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class CarrierHomeLogoViewController extends ViewController implements Dumpable, ConfigurationController.ConfigurationListener {
    public final CarrierConfigTracker carrierConfigTracker;
    public final CarrierInfraMediator carrierInfraMediator;
    public final CarrierLogoVisibilityManager carrierLogoVisibilityManager;
    public final ConfigurationController configurationController;
    public final DarkIconDispatcher darkIconDispatcher;
    public final CarrierHomeLogoViewController$defaultDataListener$1 defaultDataListener;
    public final DeviceProvisionedController deviceProvisionedController;
    public final CarrierHomeLogoViewController$deviceProvisionedListener$1 deviceProvisionedListener;
    public final DumpManager dumpManager;
    public final IndicatorScaleGardener indicatorScaleGardener;
    public final CarrierLogoView logoView;
    public final CarrierHomeLogoViewController$quickStarListener$1 quickStarListener;
    public final CarrierHomeLogoViewController$special$$inlined$map$1 serviceStateChanged;
    public final SettingsHelper settingsHelper;
    public final CarrierHomeLogoViewController$settingsListener$1 settingsListener;
    public final SimCardInfoUtil simCardInfoUtil;
    public final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 simStateChanged;
    public final SlimIndicatorViewMediator slimIndicatorViewMediator;
    public final int slotId;
    public final Flow spnUpdated;
    public final SubscriptionManager subscriptionManager;
    public boolean userSetup;
    public final LinkedList visibilityHistory;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public final class Factory {
        public final BroadcastDispatcher broadcastDispatcher;
        public final CarrierConfigTracker carrierConfigTracker;
        public final CarrierInfraMediator carrierInfraMediator;
        public final ConfigurationController configurationController;
        public final DarkIconDispatcher darkIconDispatcher;
        public final DeviceProvisionedController deviceProvisionedController;
        public final DumpManager dumpManager;
        public final IndicatorScaleGardener indicatorScaleGardener;
        public final SettingsHelper settingsHelper;
        public final SimCardInfoUtil simCardInfoUtil;
        public final SlimIndicatorViewMediator slimIndicatorViewMediator;
        public final SubscriptionManager subscriptionManager;
        public final TelephonyManager telephonyManager;

        public Factory(CarrierInfraMediator carrierInfraMediator, DarkIconDispatcher darkIconDispatcher, DumpManager dumpManager, SettingsHelper settingsHelper, SlimIndicatorViewMediator slimIndicatorViewMediator, SimCardInfoUtil simCardInfoUtil, ConfigurationController configurationController, IndicatorScaleGardener indicatorScaleGardener, BroadcastDispatcher broadcastDispatcher, SubscriptionManager subscriptionManager, TelephonyManager telephonyManager, CarrierConfigTracker carrierConfigTracker, DeviceProvisionedController deviceProvisionedController) {
            this.carrierInfraMediator = carrierInfraMediator;
            this.darkIconDispatcher = darkIconDispatcher;
            this.dumpManager = dumpManager;
            this.settingsHelper = settingsHelper;
            this.slimIndicatorViewMediator = slimIndicatorViewMediator;
            this.simCardInfoUtil = simCardInfoUtil;
            this.configurationController = configurationController;
            this.indicatorScaleGardener = indicatorScaleGardener;
            this.broadcastDispatcher = broadcastDispatcher;
            this.subscriptionManager = subscriptionManager;
            this.telephonyManager = telephonyManager;
            this.carrierConfigTracker = carrierConfigTracker;
            this.deviceProvisionedController = deviceProvisionedController;
        }
    }

    /* JADX WARN: Type inference failed for: r1v10, types: [com.android.systemui.statusbar.phone.CarrierHomeLogoViewController$deviceProvisionedListener$1] */
    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.statusbar.phone.CarrierHomeLogoViewController$quickStarListener$1] */
    /* JADX WARN: Type inference failed for: r2v14, types: [com.android.systemui.statusbar.phone.CarrierHomeLogoViewController$settingsListener$1] */
    /* JADX WARN: Type inference failed for: r2v15, types: [com.android.systemui.statusbar.phone.CarrierHomeLogoViewController$defaultDataListener$1] */
    public CarrierHomeLogoViewController(View view, BroadcastDispatcher broadcastDispatcher, DumpManager dumpManager, SettingsHelper settingsHelper, ConfigurationController configurationController, CarrierLogoVisibilityManager carrierLogoVisibilityManager, int i, CarrierInfraMediator carrierInfraMediator, DarkIconDispatcher darkIconDispatcher, SlimIndicatorViewMediator slimIndicatorViewMediator, CarrierLogoView carrierLogoView, IndicatorScaleGardener indicatorScaleGardener, SubscriptionManager subscriptionManager, SimCardInfoUtil simCardInfoUtil, CarrierConfigTracker carrierConfigTracker, DeviceProvisionedController deviceProvisionedController) {
        super(view);
        this.dumpManager = dumpManager;
        this.settingsHelper = settingsHelper;
        this.configurationController = configurationController;
        this.carrierLogoVisibilityManager = carrierLogoVisibilityManager;
        this.slotId = i;
        this.carrierInfraMediator = carrierInfraMediator;
        this.darkIconDispatcher = darkIconDispatcher;
        this.slimIndicatorViewMediator = slimIndicatorViewMediator;
        this.logoView = carrierLogoView;
        this.indicatorScaleGardener = indicatorScaleGardener;
        this.subscriptionManager = subscriptionManager;
        this.simCardInfoUtil = simCardInfoUtil;
        this.carrierConfigTracker = carrierConfigTracker;
        this.deviceProvisionedController = deviceProvisionedController;
        this.visibilityHistory = new LinkedList();
        this.settingsListener = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.statusbar.phone.CarrierHomeLogoViewController$settingsListener$1
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                CarrierHomeLogoViewController carrierHomeLogoViewController = CarrierHomeLogoViewController.this;
                carrierHomeLogoViewController.carrierLogoVisibilityManager.settingEnabled = carrierHomeLogoViewController.settingsHelper.isCarrierLogoEnabled();
                carrierHomeLogoViewController.updateCarrierLogoVisibility();
                KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("Carrier logo setting changed=", carrierHomeLogoViewController.settingsHelper.isCarrierLogoEnabled(), "CarrierHomeLogoViewController");
            }
        };
        this.defaultDataListener = new CarrierConfigTracker.DefaultDataSubscriptionChangedListener() { // from class: com.android.systemui.statusbar.phone.CarrierHomeLogoViewController$defaultDataListener$1
            @Override // com.android.systemui.util.CarrierConfigTracker.DefaultDataSubscriptionChangedListener
            public final void onDefaultSubscriptionChanged(int i2) {
                CarrierHomeLogoViewController carrierHomeLogoViewController = CarrierHomeLogoViewController.this;
                CarrierLogoVisibilityManager carrierLogoVisibilityManager2 = carrierHomeLogoViewController.carrierLogoVisibilityManager;
                int slotIndex = SubscriptionManager.getSlotIndex(i2);
                carrierLogoVisibilityManager2.defaultSubscriptionSlotId = slotIndex;
                CarrierInfraMediator carrierInfraMediator2 = carrierLogoVisibilityManager2.carrierInfraMediator;
                CarrierInfraMediator.Conditions conditions = carrierLogoVisibilityManager2.featureName;
                boolean isEnabled = carrierInfraMediator2.isEnabled(conditions, slotIndex, new Object[0]);
                carrierLogoVisibilityManager2.featureEnabled = isEnabled;
                Log.d("CarrierLogoVisibilityManager", "Default data subscription is changed to slot" + carrierLogoVisibilityManager2.defaultSubscriptionSlotId + " " + conditions + "=" + isEnabled);
                carrierHomeLogoViewController.updateCarrierLogoVisibility();
            }
        };
        this.userSetup = ((DeviceProvisionedControllerImpl) deviceProvisionedController).isCurrentUserSetup();
        this.quickStarListener = new SlimIndicatorViewSubscriber() { // from class: com.android.systemui.statusbar.phone.CarrierHomeLogoViewController$quickStarListener$1
            @Override // com.android.systemui.slimindicator.SlimIndicatorViewSubscriber
            public final void updateQuickStarStyle() {
                boolean z;
                CarrierHomeLogoViewController carrierHomeLogoViewController = CarrierHomeLogoViewController.this;
                CarrierLogoVisibilityManager carrierLogoVisibilityManager2 = carrierHomeLogoViewController.carrierLogoVisibilityManager;
                SlimIndicatorViewMediatorImpl slimIndicatorViewMediatorImpl = (SlimIndicatorViewMediatorImpl) carrierHomeLogoViewController.slimIndicatorViewMediator;
                boolean z2 = false;
                if (slimIndicatorViewMediatorImpl.mPluginMediator.mIsSPluginConnected) {
                    if (slimIndicatorViewMediatorImpl.mCarrierCrew.mIsHomeCarrierDisabled == 1) {
                        z = true;
                    } else {
                        z = false;
                    }
                    if (z) {
                        z2 = true;
                    }
                }
                carrierLogoVisibilityManager2.quickStarEnabled = !z2;
                carrierHomeLogoViewController.updateCarrierLogoVisibility();
            }
        };
        this.simStateChanged = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new CarrierHomeLogoViewController$simStateChanged$1(this, null), BroadcastDispatcher.broadcastFlow$default(broadcastDispatcher, new IntentFilter("android.intent.action.SIM_STATE_CHANGED"), null, 0, null, 14));
        this.serviceStateChanged = new CarrierHomeLogoViewController$special$$inlined$map$1(BroadcastDispatcher.broadcastFlow$default(broadcastDispatcher, new IntentFilter("android.intent.action.SERVICE_STATE"), null, new Function2() { // from class: com.android.systemui.statusbar.phone.CarrierHomeLogoViewController$serviceStateChanged$1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return (Intent) obj;
            }
        }, 14), this);
        this.spnUpdated = BroadcastDispatcher.broadcastFlow$default(broadcastDispatcher, new IntentFilter("android.telephony.action.SERVICE_PROVIDERS_UPDATED"), null, 0, null, 14);
        this.deviceProvisionedListener = new DeviceProvisionedController.DeviceProvisionedListener() { // from class: com.android.systemui.statusbar.phone.CarrierHomeLogoViewController$deviceProvisionedListener$1
            @Override // com.android.systemui.statusbar.policy.DeviceProvisionedController.DeviceProvisionedListener
            public final void onUserSetupChanged() {
                CarrierHomeLogoViewController carrierHomeLogoViewController = CarrierHomeLogoViewController.this;
                carrierHomeLogoViewController.userSetup = ((DeviceProvisionedControllerImpl) carrierHomeLogoViewController.deviceProvisionedController).isCurrentUserSetup();
            }
        };
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0063, code lost:
    
        if (r1 == com.android.systemui.statusbar.pipeline.mobile.data.model.SimType.SKT) goto L41;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x011c, code lost:
    
        r1 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0071, code lost:
    
        if (r1 == com.android.systemui.statusbar.pipeline.mobile.data.model.SimType.KT) goto L41;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x007f, code lost:
    
        if (r1 == com.android.systemui.statusbar.pipeline.mobile.data.model.SimType.LGT) goto L41;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x011a, code lost:
    
        if (r1 == false) goto L42;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void access$updateSimTypes(com.android.systemui.statusbar.phone.CarrierHomeLogoViewController r8) {
        /*
            Method dump skipped, instructions count: 293
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.CarrierHomeLogoViewController.access$updateSimTypes(com.android.systemui.statusbar.phone.CarrierHomeLogoViewController):void");
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println(" userSetup=" + this.userSetup);
        printWriter.println();
        CarrierLogoVisibilityManager carrierLogoVisibilityManager = this.carrierLogoVisibilityManager;
        carrierLogoVisibilityManager.getClass();
        printWriter.println("Last visibility state:");
        printWriter.println(carrierLogoVisibilityManager.toString());
        Iterator it = this.visibilityHistory.iterator();
        while (it.hasNext()) {
            printWriter.println((String) it.next());
        }
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onDensityOrFontScaleChanged() {
        float f = this.indicatorScaleGardener.getLatestScaleModel(getContext()).ratio;
        CarrierLogoView carrierLogoView = this.logoView;
        ViewGroup.LayoutParams layoutParams = carrierLogoView.getLayoutParams();
        layoutParams.width = MathKt__MathJVMKt.roundToInt(carrierLogoView.getDrawable().getIntrinsicWidth() * f);
        layoutParams.height = MathKt__MathJVMKt.roundToInt(carrierLogoView.getDrawable().getIntrinsicHeight() * f);
        carrierLogoView.setLayoutParams(layoutParams);
        this.mView.setPaddingRelative(0, 0, MathKt__MathJVMKt.roundToInt(getResources().getDimensionPixelSize(R.dimen.status_carrier_logo_margin_end) * f), 0);
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onDisplayDeviceTypeChanged() {
        if (BasicRune.BASIC_FOLDABLE_TYPE_FOLD) {
            onDensityOrFontScaleChanged();
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() {
        int i;
        String str;
        boolean z;
        ((ArraySet) this.carrierConfigTracker.mDataListeners).add(this.defaultDataListener);
        SettingsHelper settingsHelper = this.settingsHelper;
        boolean isCarrierLogoEnabled = settingsHelper.isCarrierLogoEnabled();
        CarrierLogoVisibilityManager carrierLogoVisibilityManager = this.carrierLogoVisibilityManager;
        carrierLogoVisibilityManager.settingEnabled = isCarrierLogoEnabled;
        settingsHelper.registerCallback(this.settingsListener, Settings.System.getUriFor("status_bar_show_network_information"));
        CarrierInfraMediator.Values values = CarrierInfraMediator.Values.ICON_BRANDING;
        boolean z2 = false;
        CarrierInfraMediator carrierInfraMediator = this.carrierInfraMediator;
        int i2 = this.slotId;
        Object obj = carrierInfraMediator.get(values, i2, new Object[0]);
        if (Intrinsics.areEqual(obj, "SKT")) {
            i = R.drawable.stat_notify_operator_logo_skt;
        } else if (Intrinsics.areEqual(obj, "KTT")) {
            i = R.drawable.stat_notify_operator_logo_kt;
        } else if (Intrinsics.areEqual(obj, "LGT")) {
            i = R.drawable.stat_notify_operator_logo_lgu;
        } else if (Intrinsics.areEqual(obj, "ORANGE")) {
            i = R.drawable.stat_notify_operator_logo_org;
        } else {
            i = 0;
        }
        CarrierLogoView carrierLogoView = this.logoView;
        carrierLogoView.setImageResource(i);
        String str2 = (String) carrierInfraMediator.get(values, i2, new Object[0]);
        switch (str2.hashCode()) {
            case -1955522002:
                if (str2.equals("ORANGE")) {
                    str = "Orange F";
                    break;
                }
                str = "";
                break;
            case 74763:
                if (str2.equals("KTT")) {
                    str = getContext().getString(R.string.status_bar_carrier_logo_kt_tts);
                    break;
                }
                str = "";
                break;
            case 75321:
                if (str2.equals("LGT")) {
                    str = getContext().getString(R.string.status_bar_carrier_logo_lgu_tts);
                    break;
                }
                str = "";
                break;
            case 82172:
                if (str2.equals("SKT")) {
                    str = getContext().getString(R.string.status_bar_carrier_logo_skt_tts);
                    break;
                }
                str = "";
                break;
            default:
                str = "";
                break;
        }
        carrierLogoView.setContentDescription(str);
        this.darkIconDispatcher.addDarkReceiver(carrierLogoView);
        ((DeviceProvisionedControllerImpl) this.deviceProvisionedController).addCallback(this.deviceProvisionedListener);
        ((ConfigurationControllerImpl) this.configurationController).addCallback(this);
        SlimIndicatorViewMediatorImpl slimIndicatorViewMediatorImpl = (SlimIndicatorViewMediatorImpl) this.slimIndicatorViewMediator;
        if (slimIndicatorViewMediatorImpl.mPluginMediator.mIsSPluginConnected) {
            if (slimIndicatorViewMediatorImpl.mCarrierCrew.mIsHomeCarrierDisabled == 1) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                z2 = true;
            }
        }
        carrierLogoVisibilityManager.quickStarEnabled = !z2;
        slimIndicatorViewMediatorImpl.registerSubscriber("CarrierHomeLogoViewController", this.quickStarListener);
        this.dumpManager.registerNormalDumpable("CarrierHomeLogoViewController", this);
        RepeatWhenAttachedKt.repeatWhenAttached(this.mView, EmptyCoroutineContext.INSTANCE, new CarrierHomeLogoViewController$onViewAttached$1(this, null));
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewDetached() {
        ((ArraySet) this.carrierConfigTracker.mDataListeners).remove(this.defaultDataListener);
        this.settingsHelper.unregisterCallback(this.settingsListener);
        this.darkIconDispatcher.removeDarkReceiver(this.logoView);
        ((DeviceProvisionedControllerImpl) this.deviceProvisionedController).removeCallback(this.deviceProvisionedListener);
        ((ConfigurationControllerImpl) this.configurationController).removeCallback(this);
        ((SlimIndicatorViewMediatorImpl) this.slimIndicatorViewMediator).unregisterSubscriber("CarrierHomeLogoViewController");
        this.dumpManager.unregisterDumpable("CarrierHomeLogoViewController");
    }

    public final void updateCarrierLogoVisibility() {
        CarrierLogoVisibilityManager carrierLogoVisibilityManager = this.carrierLogoVisibilityManager;
        if (carrierLogoVisibilityManager.getVisible() != this.mView.getVisibility()) {
            this.mView.setVisibility(carrierLogoVisibilityManager.getVisible());
            LinkedList linkedList = this.visibilityHistory;
            if (linkedList.size() > 10) {
                linkedList.poll();
            }
            linkedList.offer(new SimpleDateFormat("MM-dd HH:mm:ss.SSS").format(Long.valueOf(System.currentTimeMillis())) + " " + carrierLogoVisibilityManager);
            ListPopupWindow$$ExternalSyntheticOutline0.m("Visibility changed=", carrierLogoVisibilityManager.getVisible(), "CarrierHomeLogoViewController");
        }
    }
}
