package com.android.systemui.statusbar.policy;

import android.content.Context;
import android.net.NetworkPolicyManager;
import android.os.Handler;
import android.os.Looper;
import com.android.systemui.statusbar.policy.DataSaverController;
import java.util.ArrayList;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class DataSaverControllerImpl implements DataSaverController {
    public final Handler mHandler = new Handler(Looper.getMainLooper());
    public final ArrayList mListeners = new ArrayList();
    public final AnonymousClass1 mPolicyListener = new AnonymousClass1();
    public final NetworkPolicyManager mPolicyManager;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* renamed from: com.android.systemui.statusbar.policy.DataSaverControllerImpl$1, reason: invalid class name */
    /* loaded from: classes2.dex */
    public final class AnonymousClass1 extends NetworkPolicyManager.Listener {
        public AnonymousClass1() {
        }

        public final void onRestrictBackgroundChanged(final boolean z) {
            DataSaverControllerImpl.this.mHandler.post(new Runnable() { // from class: com.android.systemui.statusbar.policy.DataSaverControllerImpl.1.1
                @Override // java.lang.Runnable
                public final void run() {
                    DataSaverControllerImpl dataSaverControllerImpl = DataSaverControllerImpl.this;
                    boolean z2 = z;
                    synchronized (dataSaverControllerImpl.mListeners) {
                        for (int i = 0; i < dataSaverControllerImpl.mListeners.size(); i++) {
                            ((DataSaverController.Listener) dataSaverControllerImpl.mListeners.get(i)).onDataSaverChanged(z2);
                        }
                    }
                }
            });
        }
    }

    public DataSaverControllerImpl(Context context) {
        this.mPolicyManager = NetworkPolicyManager.from(context);
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void addCallback(Object obj) {
        DataSaverController.Listener listener = (DataSaverController.Listener) obj;
        synchronized (this.mListeners) {
            this.mListeners.add(listener);
            if (this.mListeners.size() == 1) {
                this.mPolicyManager.registerListener(this.mPolicyListener);
            }
        }
        listener.onDataSaverChanged(isDataSaverEnabled());
    }

    public final boolean isDataSaverEnabled() {
        return this.mPolicyManager.getRestrictBackground();
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void removeCallback(Object obj) {
        DataSaverController.Listener listener = (DataSaverController.Listener) obj;
        synchronized (this.mListeners) {
            this.mListeners.remove(listener);
            if (this.mListeners.size() == 0) {
                this.mPolicyManager.unregisterListener(this.mPolicyListener);
            }
        }
    }
}
