package com.android.systemui.volume.middleware;

import com.android.systemui.volume.VolumeDependency;
import com.android.systemui.volume.VolumeDependencyBase;
import com.android.systemui.volume.util.StreamUtil;
import com.samsung.systemui.splugins.volume.VolumeInfraMediator;
import com.samsung.systemui.splugins.volume.VolumeMiddleware;
import com.samsung.systemui.splugins.volume.VolumePanelAction;
import com.samsung.systemui.splugins.volume.VolumePanelState;
import com.samsung.systemui.splugins.volume.VolumeState;
import com.samsung.systemui.splugins.volume.VolumeStreamState;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class BluetoothInteractor implements VolumeMiddleware {
    public final VolumeInfraMediator infraMediator;
    public boolean isPanelShowing;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[VolumePanelAction.ActionType.values().length];
            try {
                iArr[VolumePanelAction.ActionType.ACTION_STATE_CHANGED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[VolumePanelAction.ActionType.ACTION_PANEL_SHOW.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[VolumePanelState.StateType.values().length];
            try {
                iArr2[VolumePanelState.StateType.STATE_DISMISS_VOLUME_PANEL_COMPLETED.ordinal()] = 1;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    public BluetoothInteractor(VolumeDependencyBase volumeDependencyBase) {
        this.infraMediator = (VolumeInfraMediator) ((VolumeDependency) volumeDependencyBase).get(VolumeInfraMediator.class);
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeMiddleware
    public final Object apply(Object obj) {
        VolumePanelAction build;
        int i;
        VolumePanelAction volumePanelAction = (VolumePanelAction) obj;
        int i2 = WhenMappings.$EnumSwitchMapping$0[volumePanelAction.getActionType().ordinal()];
        String str = null;
        boolean z = true;
        if (i2 != 1) {
            if (i2 == 2 && !this.isPanelShowing) {
                this.isPanelShowing = true;
                VolumeInfraMediator volumeInfraMediator = this.infraMediator;
                boolean isBudsTogetherEnabled = volumeInfraMediator.isBudsTogetherEnabled();
                List<Integer> importantStreamList = volumePanelAction.getImportantStreamList();
                List<Integer> unImportantStreamList = volumePanelAction.getUnImportantStreamList();
                List mutableListOf = CollectionsKt__CollectionsKt.mutableListOf(23);
                if (isBudsTogetherEnabled) {
                    importantStreamList.addAll(mutableListOf);
                    str = volumeInfraMediator.getAudioCastDeviceName();
                } else {
                    unImportantStreamList.addAll(mutableListOf);
                }
                return new VolumePanelAction.Builder(volumePanelAction).setImportantStreamList(importantStreamList).setUnImportantStreamList(unImportantStreamList).setStringValue(VolumePanelAction.StringStateKey.AUDIO_SHARING_DEVICE_NAME, str).build();
            }
            return volumePanelAction;
        }
        VolumeState volumeState = volumePanelAction.getVolumeState();
        if (volumeState != null) {
            List<VolumeStreamState> streamStates = volumeState.getStreamStates();
            if (!(streamStates instanceof Collection) || !streamStates.isEmpty()) {
                Iterator<T> it = streamStates.iterator();
                while (it.hasNext()) {
                    if (((VolumeStreamState) it.next()).isEnabled(VolumeStreamState.BooleanStateKey.ROUTED_TO_BT)) {
                        break;
                    }
                }
            }
            z = false;
            List<Integer> mutableListOf2 = CollectionsKt__CollectionsKt.mutableListOf(22);
            if (z) {
                VolumePanelAction.Builder builder = new VolumePanelAction.Builder(volumePanelAction);
                VolumeState volumeState2 = volumePanelAction.getVolumeState();
                if (volumeState2 != null) {
                    List<VolumeStreamState> streamStates2 = volumeState2.getStreamStates();
                    boolean isMultiSoundBt = volumePanelAction.isMultiSoundBt();
                    int i3 = StreamUtil.$r8$clinit;
                    if (isMultiSoundBt) {
                        i = 21;
                    } else {
                        i = 3;
                    }
                    VolumeStreamState volumeStreamState = (VolumeStreamState) CollectionsKt___CollectionsKt.getOrNull(i, streamStates2);
                    if (volumeStreamState != null) {
                        str = volumeStreamState.getDualBtDeviceName();
                    }
                }
                if (str != null) {
                    builder.activeBtDeviceName(str);
                }
                if (volumeState.isDualAudio()) {
                    mutableListOf2.addAll(volumePanelAction.getImportantStreamList());
                    build = builder.setImportantStreamList(mutableListOf2).build();
                } else {
                    mutableListOf2.addAll(volumePanelAction.getUnImportantStreamList());
                    build = builder.setUnImportantStreamList(mutableListOf2).build();
                }
                return build;
            }
            mutableListOf2.addAll(volumePanelAction.getUnImportantStreamList());
            return new VolumePanelAction.Builder(volumePanelAction).setUnImportantStreamList(mutableListOf2).build();
        }
        return volumePanelAction;
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeMiddleware
    public final void applyState(Object obj) {
        if (WhenMappings.$EnumSwitchMapping$1[((VolumePanelState) obj).getStateType().ordinal()] == 1) {
            this.isPanelShowing = false;
        }
    }
}
