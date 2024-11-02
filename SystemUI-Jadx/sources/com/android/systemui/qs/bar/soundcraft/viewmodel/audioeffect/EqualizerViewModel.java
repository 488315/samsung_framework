package com.android.systemui.qs.bar.soundcraft.viewmodel.audioeffect;

import android.content.Context;
import android.sec.clipboard.util.Log;
import androidx.lifecycle.MutableLiveData;
import com.android.systemui.R;
import com.android.systemui.qs.bar.soundcraft.interfaces.routine.manager.RoutineManager;
import com.android.systemui.qs.bar.soundcraft.interfaces.wearable.WearableManager;
import com.android.systemui.qs.bar.soundcraft.model.Equalizer;
import com.android.systemui.qs.bar.soundcraft.model.ModelProvider;
import com.android.systemui.qs.bar.soundcraft.viewmodel.base.BaseSingleChoiceViewModel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class EqualizerViewModel extends BaseSingleChoiceViewModel {
    public final Context context;
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

    public EqualizerViewModel(Context context, ModelProvider modelProvider, WearableManager wearableManager, RoutineManager routineManager) {
        this.context = context;
        this.modelProvider = modelProvider;
        this.wearableManager = wearableManager;
        this.routineManager = routineManager;
    }

    @Override // com.android.systemui.qs.bar.soundcraft.viewmodel.base.BaseSingleChoiceViewModel
    public final MutableLiveData getIcon() {
        return new MutableLiveData(Integer.valueOf(R.drawable.soundcraft_ic_equalizer));
    }

    @Override // com.android.systemui.qs.bar.soundcraft.viewmodel.base.BaseSingleChoiceViewModel
    public final MutableLiveData getOptionNames() {
        ArrayList arrayList;
        List equalizerList = this.modelProvider.budsInfo.getEqualizerList();
        if (equalizerList != null) {
            arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(equalizerList, 10));
            Iterator it = equalizerList.iterator();
            while (it.hasNext()) {
                arrayList.add(((Equalizer) it.next()).getName());
            }
        } else {
            arrayList = null;
        }
        Log.d("SoundCraft.EqualizerViewModel", "options=" + arrayList);
        return new MutableLiveData(arrayList);
    }

    @Override // com.android.systemui.qs.bar.soundcraft.viewmodel.base.BaseSingleChoiceViewModel
    public final MutableLiveData getTitle() {
        return new MutableLiveData(this.context.getString(R.string.soundcraft_equalizer_title));
    }

    @Override // com.android.systemui.qs.bar.soundcraft.viewmodel.base.BaseViewModel
    public final void notifyChange() {
        String str;
        Object obj;
        Log.d("SoundCraft.EqualizerViewModel", "notifyChange");
        List equalizerList = this.modelProvider.budsInfo.getEqualizerList();
        if (equalizerList != null) {
            MutableLiveData optionNames = getOptionNames();
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(equalizerList, 10));
            Iterator it = equalizerList.iterator();
            while (it.hasNext()) {
                arrayList.add(((Equalizer) it.next()).getName());
            }
            Log.d("SoundCraft.EqualizerViewModel", "options=" + arrayList);
            optionNames.setValue(arrayList);
            MutableLiveData mutableLiveData = this.selectedOptionName;
            Iterator it2 = equalizerList.iterator();
            while (true) {
                str = null;
                if (it2.hasNext()) {
                    obj = it2.next();
                    if (((Equalizer) obj).getState()) {
                        break;
                    }
                } else {
                    obj = null;
                    break;
                }
            }
            Equalizer equalizer = (Equalizer) obj;
            if (equalizer != null) {
                str = equalizer.getName();
            }
            Log.d("SoundCraft.EqualizerViewModel", "selectedOption=" + str);
            mutableLiveData.setValue(str);
        }
    }

    @Override // com.android.systemui.qs.bar.soundcraft.viewmodel.base.BaseSingleChoiceViewModel
    public final void onChooserDismiss() {
        this.showChooser.setValue(Boolean.FALSE);
    }

    @Override // com.android.systemui.qs.bar.soundcraft.viewmodel.base.BaseSingleChoiceViewModel
    public final void onClick() {
        Log.d("SoundCraft.EqualizerViewModel", "onClick");
        this.showChooser.setValue(Boolean.TRUE);
    }

    @Override // com.android.systemui.qs.bar.soundcraft.viewmodel.base.BaseSingleChoiceViewModel
    public final void onItemSelected(int i) {
        Unit unit;
        Object obj;
        Log.d("SoundCraft.EqualizerViewModel", "onItemSelected : position=" + i);
        ModelProvider modelProvider = this.modelProvider;
        List equalizerList = modelProvider.budsInfo.getEqualizerList();
        if (equalizerList != null) {
            Iterator it = equalizerList.iterator();
            while (true) {
                unit = null;
                if (it.hasNext()) {
                    obj = it.next();
                    if (((Equalizer) obj).getState()) {
                        break;
                    }
                } else {
                    obj = null;
                    break;
                }
            }
            Equalizer equalizer = (Equalizer) obj;
            if (equalizer != null) {
                equalizer.setState(false);
            }
            ((Equalizer) equalizerList.get(i)).setState(true);
            String str = modelProvider.playingAudioPackageNameForAppSetting;
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
        onChooserDismiss();
    }
}
