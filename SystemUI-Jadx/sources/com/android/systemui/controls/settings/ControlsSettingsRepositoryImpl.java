package com.android.systemui.controls.settings;

import com.android.systemui.user.data.repository.UserRepository;
import com.android.systemui.user.data.repository.UserRepositoryImpl;
import com.android.systemui.util.settings.SecureSettings;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class ControlsSettingsRepositoryImpl implements ControlsSettingsRepository {
    public final CoroutineDispatcher backgroundDispatcher;
    public final CoroutineScope scope;
    public final SecureSettings secureSettings;
    public final UserRepository userRepository;
    public final ReadonlyStateFlow canShowControlsInLockscreen = makeFlowForSetting("lockscreen_show_controls");
    public final ReadonlyStateFlow allowActionOnTrivialControlsInLockscreen = makeFlowForSetting("lockscreen_allow_trivial_controls");

    public ControlsSettingsRepositoryImpl(CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, UserRepository userRepository, SecureSettings secureSettings) {
        this.scope = coroutineScope;
        this.backgroundDispatcher = coroutineDispatcher;
        this.userRepository = userRepository;
        this.secureSettings = secureSettings;
    }

    public final ReadonlyStateFlow makeFlowForSetting(String str) {
        ChannelFlowTransformLatest transformLatest = FlowKt.transformLatest(FlowKt.distinctUntilChanged(((UserRepositoryImpl) this.userRepository).selectedUserInfo), new ControlsSettingsRepositoryImpl$makeFlowForSetting$$inlined$flatMapLatest$1(null, this, str));
        SharingStarted.Companion.getClass();
        return FlowKt.stateIn(transformLatest, this.scope, SharingStarted.Companion.Eagerly, Boolean.FALSE);
    }
}
