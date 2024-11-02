package com.android.systemui.volume;

import android.util.Log;
import androidx.lifecycle.Observer;
import com.android.systemui.plugins.VolumeDialogController;
import com.android.systemui.volume.VolumeDialogControllerImpl;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final /* synthetic */ class VolumeDialogControllerImpl$RingerModeObservers$1$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Observer f$0;
    public final /* synthetic */ Integer f$1;

    public /* synthetic */ VolumeDialogControllerImpl$RingerModeObservers$1$$ExternalSyntheticLambda0(Observer observer, Integer num, int i) {
        this.$r8$classId = i;
        this.f$0 = observer;
        this.f$1 = num;
    }

    @Override // java.lang.Runnable
    public final void run() {
        boolean z;
        switch (this.$r8$classId) {
            case 0:
                VolumeDialogControllerImpl.RingerModeObservers.AnonymousClass1 anonymousClass1 = (VolumeDialogControllerImpl.RingerModeObservers.AnonymousClass1) this.f$0;
                Integer num = this.f$1;
                anonymousClass1.getClass();
                int intValue = num.intValue();
                VolumeDialogControllerImpl.RingerModeObservers ringerModeObservers = anonymousClass1.this$1;
                boolean z2 = ringerModeObservers.mRingerMode.initialSticky;
                VolumeDialogControllerImpl volumeDialogControllerImpl = ringerModeObservers.this$0;
                if (z2) {
                    volumeDialogControllerImpl.mState.ringerModeExternal = intValue;
                }
                if (D.BUG) {
                    Log.d(VolumeDialogControllerImpl.TAG, "onChange ringer_mode rm=" + Util.ringerModeToString(intValue));
                }
                String str = VolumeDialogControllerImpl.TAG;
                VolumeDialogController.State state = volumeDialogControllerImpl.mState;
                if (intValue == state.ringerModeExternal) {
                    z = false;
                } else {
                    state.ringerModeExternal = intValue;
                    Events.writeEvent(12, Integer.valueOf(intValue));
                    z = true;
                }
                if (z) {
                    volumeDialogControllerImpl.updateStreamLevelW(2, volumeDialogControllerImpl.getLastAudibleStreamVolume(2));
                    volumeDialogControllerImpl.updateStreamLevelW(5, volumeDialogControllerImpl.getLastAudibleStreamVolume(5));
                    volumeDialogControllerImpl.updateStreamLevelW(1, volumeDialogControllerImpl.getLastAudibleStreamVolume(1));
                    volumeDialogControllerImpl.mCallbacks.onStateChanged(volumeDialogControllerImpl.mState);
                    return;
                }
                return;
            default:
                VolumeDialogControllerImpl.RingerModeObservers.AnonymousClass2 anonymousClass2 = (VolumeDialogControllerImpl.RingerModeObservers.AnonymousClass2) this.f$0;
                Integer num2 = this.f$1;
                anonymousClass2.getClass();
                int intValue2 = num2.intValue();
                VolumeDialogControllerImpl.RingerModeObservers ringerModeObservers2 = anonymousClass2.this$1;
                boolean z3 = ringerModeObservers2.mRingerModeInternal.initialSticky;
                VolumeDialogControllerImpl volumeDialogControllerImpl2 = ringerModeObservers2.this$0;
                if (z3) {
                    volumeDialogControllerImpl2.mState.ringerModeInternal = intValue2;
                }
                if (D.BUG) {
                    Log.d(VolumeDialogControllerImpl.TAG, "onChange internal_ringer_mode rm=" + Util.ringerModeToString(intValue2));
                }
                String str2 = VolumeDialogControllerImpl.TAG;
                if (volumeDialogControllerImpl2.updateRingerModeInternalW(intValue2)) {
                    volumeDialogControllerImpl2.updateStreamLevelW(2, volumeDialogControllerImpl2.getLastAudibleStreamVolume(2));
                    volumeDialogControllerImpl2.updateStreamLevelW(5, volumeDialogControllerImpl2.getLastAudibleStreamVolume(5));
                    volumeDialogControllerImpl2.updateStreamLevelW(1, volumeDialogControllerImpl2.getLastAudibleStreamVolume(1));
                    volumeDialogControllerImpl2.mCallbacks.onStateChanged(volumeDialogControllerImpl2.mState);
                    return;
                }
                return;
        }
    }
}
