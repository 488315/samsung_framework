package com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect;

import android.content.Context;
import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import com.android.systemui.R;
import com.android.systemui.qs.bar.soundcraft.interfaces.routine.manager.RoutineManager;
import com.android.systemui.qs.bar.soundcraft.interfaces.wearable.WearableManager;
import com.android.systemui.qs.bar.soundcraft.model.ModelProvider;
import com.android.systemui.qs.bar.soundcraft.viewmodel.base.BaseToggleViewModel;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class VoiceBoostViewModel extends BaseToggleViewModel {
    public final ModelProvider modelProvider;
    public final RoutineManager routineManager;
    public final WearableManager wearableManager;

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
    }

    public VoiceBoostViewModel(Context context, ModelProvider modelProvider, WearableManager wearableManager, RoutineManager routineManager) {
        this.modelProvider = modelProvider;
        this.wearableManager = wearableManager;
        this.routineManager = routineManager;
    }

    @Override // com.android.systemui.qs.bar.soundcraft.viewmodel.base.BaseToggleViewModel
    public final MutableLiveData getIcon() {
        return new MutableLiveData(Integer.valueOf(R.drawable.soundcraft_ic_voice_boost));
    }

    @Override // com.android.systemui.qs.bar.soundcraft.viewmodel.base.BaseViewModel
    public final void notifyChange() {
        this.name.setValue("Voice Boost");
        MutableLiveData mutableLiveData = this.isSelected;
        Boolean voiceBoost = this.modelProvider.budsInfo.getVoiceBoost();
        if (voiceBoost == null) {
            voiceBoost = Boolean.FALSE;
        }
        mutableLiveData.setValue(voiceBoost);
        Log.d("SoundCraft.VoiceBoostViewModel", "isSelected=" + mutableLiveData);
    }

    @Override // com.android.systemui.qs.bar.soundcraft.viewmodel.base.BaseToggleViewModel
    public final void onClick() {
        ModelProvider modelProvider = this.modelProvider;
        if (modelProvider.budsInfo.getVoiceBoost() != null) {
            modelProvider.budsInfo.setVoiceBoost(Boolean.valueOf(!r1.booleanValue()));
            String str = modelProvider.playingAudioPackageNameForAppSetting;
            Unit unit = null;
            if (str != null) {
                RoutineManager routineManager = this.routineManager;
                String routineId = routineManager.getRoutineId(str);
                if (routineId != null) {
                    routineManager.updateRoutine(str, routineId, modelProvider.budsInfo);
                    unit = Unit.INSTANCE;
                }
                if (unit == null) {
                    routineManager.createRoutine(str, modelProvider.budsInfo);
                }
                unit = Unit.INSTANCE;
            }
            if (unit == null) {
                this.wearableManager.updateBudsInfo(modelProvider.budsInfo);
            }
        }
        notifyChange();
    }
}
