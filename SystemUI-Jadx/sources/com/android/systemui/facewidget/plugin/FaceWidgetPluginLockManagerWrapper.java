package com.android.systemui.facewidget.plugin;

import android.os.Bundle;
import android.util.Log;
import com.android.systemui.lockstar.PluginLockStarManager;
import com.android.systemui.pluginlock.PluginLockMediator;
import com.android.systemui.pluginlock.PluginLockMediatorImpl;
import com.android.systemui.pluginlock.listener.PluginLockListener$State;
import com.android.systemui.plugins.keyguardstatusview.PluginFaceWidgetLockManager;
import com.android.systemui.plugins.keyguardstatusview.PluginLockStarStateCallback;
import com.samsung.systemui.splugins.lockstar.PluginLockStar;
import java.util.HashMap;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class FaceWidgetPluginLockManagerWrapper implements PluginFaceWidgetLockManager {
    public final HashMap mCallbackMap = new HashMap();
    public final PluginLockMediator mPluginLockMediator;
    public final PluginLockStarManager mPluginLockStarManager;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class FaceWidgetLockStarStateCallbackWrapper implements PluginLockListener$State {
        public PluginLockStarStateCallback mCallback;

        public FaceWidgetLockStarStateCallbackWrapper(PluginLockStarStateCallback pluginLockStarStateCallback) {
            this.mCallback = pluginLockStarStateCallback;
        }

        @Override // com.android.systemui.pluginlock.listener.PluginLockListener$State
        public final void onClockChanged(Bundle bundle) {
            PluginLockStarStateCallback pluginLockStarStateCallback = this.mCallback;
            if (pluginLockStarStateCallback != null) {
                pluginLockStarStateCallback.onClockChanged(bundle);
            }
        }

        @Override // com.android.systemui.pluginlock.listener.PluginLockListener$State
        public final void onLockStarEnabled(boolean z) {
            PluginLockStarStateCallback pluginLockStarStateCallback = this.mCallback;
            if (pluginLockStarStateCallback != null) {
                pluginLockStarStateCallback.onLockStarEnabled(z);
            }
        }

        @Override // com.android.systemui.pluginlock.listener.PluginLockListener$State
        public final void onMusicChanged(Bundle bundle) {
            PluginLockStarStateCallback pluginLockStarStateCallback = this.mCallback;
            if (pluginLockStarStateCallback != null) {
                pluginLockStarStateCallback.onMusicChanged(bundle);
            }
        }

        @Override // com.android.systemui.pluginlock.listener.PluginLockListener$State
        public final Bundle onUiInfoRequested(boolean z) {
            PluginLockStarStateCallback pluginLockStarStateCallback = this.mCallback;
            if (pluginLockStarStateCallback != null) {
                return pluginLockStarStateCallback.onUiInfoRequested();
            }
            return new Bundle();
        }

        @Override // com.android.systemui.pluginlock.listener.PluginLockListener$State
        public final void onViewModeChanged(int i) {
            PluginLockStarStateCallback pluginLockStarStateCallback = this.mCallback;
            if (pluginLockStarStateCallback != null) {
                pluginLockStarStateCallback.onViewModeChanged(i);
            }
        }
    }

    public FaceWidgetPluginLockManagerWrapper(PluginLockMediator pluginLockMediator, PluginLockStarManager pluginLockStarManager) {
        this.mPluginLockMediator = pluginLockMediator;
        this.mPluginLockStarManager = pluginLockStarManager;
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginFaceWidgetLockManager
    public final void addLockStarStateCallback(PluginLockStarStateCallback pluginLockStarStateCallback) {
        if (pluginLockStarStateCallback != null) {
            HashMap hashMap = this.mCallbackMap;
            if (!hashMap.containsKey(pluginLockStarStateCallback)) {
                FaceWidgetLockStarStateCallbackWrapper faceWidgetLockStarStateCallbackWrapper = new FaceWidgetLockStarStateCallbackWrapper(pluginLockStarStateCallback);
                ((PluginLockMediatorImpl) this.mPluginLockMediator).registerStateCallback(faceWidgetLockStarStateCallbackWrapper);
                hashMap.put(pluginLockStarStateCallback, faceWidgetLockStarStateCallbackWrapper);
            }
        }
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginFaceWidgetLockManager
    public final Consumer getModifier(String str) {
        PluginLockStarManager pluginLockStarManager = this.mPluginLockStarManager;
        if (pluginLockStarManager == null) {
            return null;
        }
        return pluginLockStarManager.getModifier(str);
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginFaceWidgetLockManager
    public final Supplier getSupplier(String str) {
        PluginLockStar pluginLockStar;
        PluginLockStarManager pluginLockStarManager = this.mPluginLockStarManager;
        if (pluginLockStarManager == null || (pluginLockStar = pluginLockStarManager.mPluginLockStar) == null) {
            return null;
        }
        try {
            return pluginLockStar.getSupplier(str);
        } catch (AbstractMethodError | Exception e) {
            Log.w("LStar|PluginLockStarManager", "getSupplier " + str + e);
            return null;
        }
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginFaceWidgetLockManager
    public final Bundle onSendExtraData(Bundle bundle) {
        PluginLockStarManager pluginLockStarManager = this.mPluginLockStarManager;
        if (pluginLockStarManager == null) {
            return new Bundle();
        }
        PluginLockStar pluginLockStar = pluginLockStarManager.mPluginLockStar;
        if (pluginLockStar != null && bundle != null) {
            try {
                return pluginLockStar.requestExtraData(bundle);
            } catch (Throwable unused) {
                return new Bundle();
            }
        }
        return new Bundle();
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginFaceWidgetLockManager
    public final void removeLockStarStateCallback(PluginLockStarStateCallback pluginLockStarStateCallback) {
        FaceWidgetLockStarStateCallbackWrapper faceWidgetLockStarStateCallbackWrapper;
        if (pluginLockStarStateCallback != null) {
            HashMap hashMap = this.mCallbackMap;
            if (!hashMap.containsKey(pluginLockStarStateCallback) || (faceWidgetLockStarStateCallbackWrapper = (FaceWidgetLockStarStateCallbackWrapper) hashMap.get(pluginLockStarStateCallback)) == null) {
                return;
            }
            faceWidgetLockStarStateCallbackWrapper.mCallback = null;
            hashMap.remove(pluginLockStarStateCallback);
        }
    }
}
