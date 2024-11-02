package com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel;

import android.view.View;
import com.android.systemui.statusbar.phone.DoubleShadowStatusBarIconDrawable;
import com.android.systemui.statusbar.pipeline.airplane.domain.interactor.AirplaneModeInteractor;
import com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractor;
import com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl;
import com.android.systemui.statusbar.pipeline.shared.ConnectivityConstants;
import com.android.systemui.util.DesktopManager;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class MobileIconViewModel implements MobileIconViewModelCommon {
    public final ChannelFlowTransformLatest activityContainerVisible;
    public final ChannelFlowTransformLatest activityIcon;
    public final ChannelFlowTransformLatest activityInVisible;
    public final ChannelFlowTransformLatest activityOutVisible;
    public final ChannelFlowTransformLatest anyChanges;
    public final Lazy cellProvider$delegate;
    public final ChannelFlowTransformLatest contentDescription;
    public final DesktopManager desktopManager;
    public final ChannelFlowTransformLatest dexStatusBarIcon;
    public final ChannelFlowTransformLatest icon;
    public final MobileIconInteractor iconInteractor;
    public final ReadonlyStateFlow isVisible;
    public final ChannelFlowTransformLatest networkTypeIcon;
    public final ChannelFlowTransformLatest roaming;
    public final ChannelFlowTransformLatest roamingIcon;
    public final Lazy satelliteProvider$delegate;
    public final int slotId;
    public final int subscriptionId;
    public final ReadonlyStateFlow updateDeXStatusBarIconModel;
    public final ReadonlyStateFlow vmProvider;
    public final ReadonlyStateFlow voiceNoServiceIcon;

    public MobileIconViewModel(int i, MobileIconInteractor mobileIconInteractor, final AirplaneModeInteractor airplaneModeInteractor, final ConnectivityConstants connectivityConstants, final CoroutineScope coroutineScope, DesktopManager desktopManager, final String str) {
        int i2;
        this.subscriptionId = i;
        this.iconInteractor = mobileIconInteractor;
        this.desktopManager = desktopManager;
        Lazy lazy = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModel$cellProvider$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                MobileIconViewModel mobileIconViewModel = MobileIconViewModel.this;
                return new CellularIconViewModel(mobileIconViewModel.subscriptionId, mobileIconViewModel.iconInteractor, airplaneModeInteractor, connectivityConstants, coroutineScope, mobileIconViewModel.desktopManager, str);
            }
        });
        this.cellProvider$delegate = lazy;
        Lazy lazy2 = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModel$satelliteProvider$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                MobileIconViewModel mobileIconViewModel = MobileIconViewModel.this;
                return new CarrierBasedSatelliteViewModelImpl(mobileIconViewModel.subscriptionId, mobileIconViewModel.iconInteractor, coroutineScope);
            }
        });
        this.satelliteProvider$delegate = lazy2;
        MobileIconInteractorImpl mobileIconInteractorImpl = (MobileIconInteractorImpl) mobileIconInteractor;
        ChannelFlowTransformLatest mapLatest = FlowKt.mapLatest(mobileIconInteractorImpl.isNonTerrestrial, new MobileIconViewModel$vmProvider$1(this, null));
        SharingStarted.Companion companion = SharingStarted.Companion;
        ReadonlyStateFlow stateIn = FlowKt.stateIn(mapLatest, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), (CellularIconViewModel) lazy.getValue());
        this.vmProvider = stateIn;
        if (((Boolean) mobileIconInteractorImpl.isNonTerrestrial.getValue()).booleanValue()) {
            i2 = ((CarrierBasedSatelliteViewModelImpl) lazy2.getValue()).slotId;
        } else {
            i2 = ((CellularIconViewModel) lazy.getValue()).slotId;
        }
        this.slotId = i2;
        this.isVisible = FlowKt.stateIn(FlowKt.transformLatest(stateIn, new MobileIconViewModel$special$$inlined$flatMapLatest$1(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), Boolean.FALSE);
        this.icon = FlowKt.transformLatest(stateIn, new MobileIconViewModel$special$$inlined$flatMapLatest$2(null));
        this.contentDescription = FlowKt.transformLatest(stateIn, new MobileIconViewModel$special$$inlined$flatMapLatest$3(null));
        this.roaming = FlowKt.transformLatest(stateIn, new MobileIconViewModel$special$$inlined$flatMapLatest$4(null));
        this.networkTypeIcon = FlowKt.transformLatest(stateIn, new MobileIconViewModel$special$$inlined$flatMapLatest$5(null));
        this.activityInVisible = FlowKt.transformLatest(stateIn, new MobileIconViewModel$special$$inlined$flatMapLatest$6(null));
        this.activityOutVisible = FlowKt.transformLatest(stateIn, new MobileIconViewModel$special$$inlined$flatMapLatest$7(null));
        this.activityContainerVisible = FlowKt.transformLatest(stateIn, new MobileIconViewModel$special$$inlined$flatMapLatest$8(null));
        this.activityIcon = FlowKt.transformLatest(stateIn, new MobileIconViewModel$special$$inlined$flatMapLatest$9(null));
        this.roamingIcon = FlowKt.transformLatest(stateIn, new MobileIconViewModel$special$$inlined$flatMapLatest$10(null));
        this.anyChanges = FlowKt.transformLatest(stateIn, new MobileIconViewModel$special$$inlined$flatMapLatest$11(null));
        this.dexStatusBarIcon = FlowKt.transformLatest(stateIn, new MobileIconViewModel$special$$inlined$flatMapLatest$12(null));
        this.updateDeXStatusBarIconModel = FlowKt.stateIn(FlowKt.transformLatest(stateIn, new MobileIconViewModel$special$$inlined$flatMapLatest$13(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), null);
        this.voiceNoServiceIcon = FlowKt.stateIn(FlowKt.transformLatest(stateIn, new MobileIconViewModel$special$$inlined$flatMapLatest$14(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), 0);
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModelCommon
    public final Flow getActivityContainerVisible() {
        return this.activityContainerVisible;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModelCommon
    public final Flow getActivityIcon() {
        return this.activityIcon;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModelCommon
    public final Flow getActivityInVisible() {
        return this.activityInVisible;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModelCommon
    public final Flow getActivityOutVisible() {
        return this.activityOutVisible;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModelCommon
    public final Flow getAnyChanges() {
        return this.anyChanges;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModelCommon
    public final Flow getContentDescription() {
        return this.contentDescription;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModelCommon
    public final Flow getDexStatusBarIcon() {
        return this.dexStatusBarIcon;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModelCommon
    public final Flow getIcon() {
        return this.icon;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModelCommon
    public final Flow getNetworkTypeIcon() {
        return this.networkTypeIcon;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModelCommon
    public final Flow getRoaming() {
        return this.roaming;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModelCommon
    public final Flow getRoamingIcon() {
        return this.roamingIcon;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModelCommon
    public final DoubleShadowStatusBarIconDrawable getShadowDrawable(View view, int i) {
        if (((Boolean) ((MobileIconInteractorImpl) this.iconInteractor).isNonTerrestrial.getValue()).booleanValue()) {
            ((CarrierBasedSatelliteViewModelImpl) this.satelliteProvider$delegate.getValue()).getClass();
            return null;
        }
        return ((CellularIconViewModel) this.cellProvider$delegate.getValue()).getShadowDrawable(view, i);
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModelCommon
    public final int getSubscriptionId() {
        return this.subscriptionId;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModelCommon
    public final StateFlow getUpdateDeXStatusBarIconModel() {
        return this.updateDeXStatusBarIconModel;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModelCommon
    public final StateFlow getVoiceNoServiceIcon() {
        return this.voiceNoServiceIcon;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModelCommon
    public final StateFlow isVisible() {
        return this.isVisible;
    }
}
