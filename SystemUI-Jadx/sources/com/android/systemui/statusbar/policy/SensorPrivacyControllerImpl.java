package com.android.systemui.statusbar.policy;

import android.hardware.SensorPrivacyManager;
import com.android.systemui.statusbar.phone.PhoneStatusBarPolicy;
import com.android.systemui.statusbar.phone.PhoneStatusBarPolicy$6$$ExternalSyntheticLambda0;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class SensorPrivacyControllerImpl implements SensorPrivacyController, SensorPrivacyManager.OnAllSensorPrivacyChangedListener {
    public final List mListeners = new ArrayList(1);
    public final Object mLock = new Object();
    public boolean mSensorPrivacyEnabled;
    public final SensorPrivacyManager mSensorPrivacyManager;

    public SensorPrivacyControllerImpl(SensorPrivacyManager sensorPrivacyManager) {
        this.mSensorPrivacyManager = sensorPrivacyManager;
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void addCallback(Object obj) {
        PhoneStatusBarPolicy.AnonymousClass6 anonymousClass6 = (PhoneStatusBarPolicy.AnonymousClass6) obj;
        synchronized (this.mLock) {
            ((ArrayList) this.mListeners).add(anonymousClass6);
            PhoneStatusBarPolicy.this.mHandler.post(new PhoneStatusBarPolicy$6$$ExternalSyntheticLambda0(anonymousClass6, this.mSensorPrivacyEnabled));
        }
    }

    public final void onAllSensorPrivacyChanged(boolean z) {
        synchronized (this.mLock) {
            this.mSensorPrivacyEnabled = z;
            Iterator it = ((ArrayList) this.mListeners).iterator();
            while (it.hasNext()) {
                PhoneStatusBarPolicy.AnonymousClass6 anonymousClass6 = (PhoneStatusBarPolicy.AnonymousClass6) it.next();
                PhoneStatusBarPolicy.this.mHandler.post(new PhoneStatusBarPolicy$6$$ExternalSyntheticLambda0(anonymousClass6, this.mSensorPrivacyEnabled));
            }
        }
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void removeCallback(Object obj) {
        PhoneStatusBarPolicy.AnonymousClass6 anonymousClass6 = (PhoneStatusBarPolicy.AnonymousClass6) obj;
        synchronized (this.mLock) {
            ((ArrayList) this.mListeners).remove(anonymousClass6);
        }
    }
}
