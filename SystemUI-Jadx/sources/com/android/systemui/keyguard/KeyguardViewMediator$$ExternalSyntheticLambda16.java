package com.android.systemui.keyguard;

import android.content.Intent;
import android.os.Bundle;
import android.view.IRemoteAnimationFinishedCallback;
import android.view.RemoteAnimationTarget;
import com.android.systemui.classifier.FalsingCollectorImpl;
import com.android.systemui.keyguard.KeyguardViewMediator;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final /* synthetic */ class KeyguardViewMediator$$ExternalSyntheticLambda16 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ KeyguardViewMediator$$ExternalSyntheticLambda16(int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                KeyguardViewMediator keyguardViewMediator = (KeyguardViewMediator) this.f$0;
                Integer num = (Integer) this.f$1;
                keyguardViewMediator.getClass();
                keyguardViewMediator.playSound(num.intValue());
                return;
            case 1:
                ((KeyguardViewMediator) this.f$0).doKeyguardLocked((Bundle) this.f$1, false);
                return;
            case 2:
                KeyguardViewMediator keyguardViewMediator2 = (KeyguardViewMediator) this.f$0;
                Boolean bool = (Boolean) this.f$1;
                keyguardViewMediator2.getClass();
                keyguardViewMediator2.mHiding = bool.booleanValue();
                return;
            default:
                KeyguardViewMediator.AnonymousClass12 anonymousClass12 = (KeyguardViewMediator.AnonymousClass12) this.f$0;
                KeyguardViewMediator.StartKeyguardExitAnimParams startKeyguardExitAnimParams = (KeyguardViewMediator.StartKeyguardExitAnimParams) this.f$1;
                KeyguardViewMediator keyguardViewMediator3 = KeyguardViewMediator.this;
                long j = startKeyguardExitAnimParams.startTime;
                long j2 = startKeyguardExitAnimParams.fadeoutDuration;
                RemoteAnimationTarget[] remoteAnimationTargetArr = startKeyguardExitAnimParams.mApps;
                RemoteAnimationTarget[] remoteAnimationTargetArr2 = startKeyguardExitAnimParams.mWallpapers;
                RemoteAnimationTarget[] remoteAnimationTargetArr3 = startKeyguardExitAnimParams.mNonApps;
                IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback = startKeyguardExitAnimParams.mFinishedCallback;
                Intent intent = KeyguardViewMediator.USER_PRESENT_INTENT;
                keyguardViewMediator3.handleStartKeyguardExitAnimation(j, j2, remoteAnimationTargetArr, remoteAnimationTargetArr2, remoteAnimationTargetArr3, iRemoteAnimationFinishedCallback);
                FalsingCollectorImpl falsingCollectorImpl = (FalsingCollectorImpl) KeyguardViewMediator.this.mFalsingCollector;
                falsingCollectorImpl.mFalsingManager.onSuccessfulUnlock();
                falsingCollectorImpl.sessionEnd();
                return;
        }
    }
}
