package com.android.systemui.qs.bar.soundcraft.viewmodel.noisecontrol;

import android.content.Context;
import androidx.lifecycle.MutableLiveData;
import com.android.systemui.qs.bar.soundcraft.model.ModelProvider;
import com.android.systemui.qs.bar.soundcraft.viewmodel.base.BaseViewModel;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class NoiseControlBoxViewModel extends BaseViewModel {
    public final Context context;
    public final ModelProvider modelProvider;
    public final MutableLiveData showActiveNoiseCancelingBarOnly;
    public final MutableLiveData showNoiseEffectBoxView;

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

    public NoiseControlBoxViewModel(Context context, ModelProvider modelProvider) {
        this.context = context;
        this.modelProvider = modelProvider;
        Boolean bool = Boolean.FALSE;
        this.showActiveNoiseCancelingBarOnly = new MutableLiveData(bool);
        this.showNoiseEffectBoxView = new MutableLiveData(bool);
    }

    public final MutableLiveData getShowActiveNoiseCancelingBarOnly() {
        return this.showActiveNoiseCancelingBarOnly;
    }

    public final MutableLiveData getShowNoiseEffectBoxView() {
        return this.showNoiseEffectBoxView;
    }

    @Override // com.android.systemui.qs.bar.soundcraft.viewmodel.base.BaseViewModel
    public final void notifyChange() {
        boolean areEqual = Intrinsics.areEqual(this.modelProvider.settings.budsPluginPackageName, "com.samsung.accessory.jellymgr");
        MutableLiveData mutableLiveData = this.showNoiseEffectBoxView;
        MutableLiveData mutableLiveData2 = this.showActiveNoiseCancelingBarOnly;
        if (areEqual) {
            mutableLiveData2.setValue(Boolean.TRUE);
            mutableLiveData.setValue(Boolean.FALSE);
        } else {
            mutableLiveData2.setValue(Boolean.FALSE);
            mutableLiveData.setValue(Boolean.TRUE);
        }
    }

    public final String toString() {
        return "[showActiveNoiseCancelingBarOnly=" + this.showActiveNoiseCancelingBarOnly.getValue() + ", showNoiseEffectBoxView=" + this.showNoiseEffectBoxView.getValue() + ", ]";
    }
}
