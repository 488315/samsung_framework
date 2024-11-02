package com.android.systemui.statusbar.pipeline.satellite.ui.viewmodel;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.statusbar.pipeline.airplane.data.repository.AirplaneModeRepository;
import com.android.systemui.statusbar.pipeline.satellite.domain.interactor.DeviceBasedSatelliteInteractor;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class DeviceBasedSatelliteViewModel {
    public static final long DELAY_DURATION;
    public final ReadonlyStateFlow icon;
    public final ReadonlyStateFlow shouldActuallyShowIcon;
    public final ChannelFlowTransformLatest shouldShowIcon;

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
        Duration.Companion companion = Duration.Companion;
        DELAY_DURATION = DurationKt.toDuration(10, DurationUnit.SECONDS);
    }

    public DeviceBasedSatelliteViewModel(DeviceBasedSatelliteInteractor deviceBasedSatelliteInteractor, CoroutineScope coroutineScope, AirplaneModeRepository airplaneModeRepository, LogBuffer logBuffer) {
        ChannelFlowTransformLatest transformLatest = FlowKt.transformLatest(deviceBasedSatelliteInteractor.areAllConnectionsOutOfService, new DeviceBasedSatelliteViewModel$special$$inlined$flatMapLatest$1(null, deviceBasedSatelliteInteractor, airplaneModeRepository));
        this.shouldShowIcon = transformLatest;
        ChannelFlowTransformLatest transformLatest2 = FlowKt.transformLatest(FlowKt.distinctUntilChanged(transformLatest), new DeviceBasedSatelliteViewModel$special$$inlined$flatMapLatest$2(null, logBuffer));
        SharingStarted.Companion companion = SharingStarted.Companion;
        ReadonlyStateFlow stateIn = FlowKt.stateIn(transformLatest2, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), Boolean.FALSE);
        this.shouldActuallyShowIcon = stateIn;
        this.icon = FlowKt.stateIn(FlowKt.combine(stateIn, deviceBasedSatelliteInteractor.connectionState, deviceBasedSatelliteInteractor.signalStrength, new DeviceBasedSatelliteViewModel$icon$1(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), null);
    }
}
