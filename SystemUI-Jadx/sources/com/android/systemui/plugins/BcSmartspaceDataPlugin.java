package com.android.systemui.plugins;

import android.app.PendingIntent;
import android.app.smartspace.SmartspaceAction;
import android.app.smartspace.SmartspaceTarget;
import android.app.smartspace.SmartspaceTargetEvent;
import android.app.smartspace.uitemplatedata.TapAction;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.plugins.annotations.ProvidesInterface;
import java.util.List;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
@ProvidesInterface(action = BcSmartspaceDataPlugin.ACTION, version = 1)
/* loaded from: classes2.dex */
public interface BcSmartspaceDataPlugin extends Plugin {
    public static final String ACTION = "com.android.systemui.action.PLUGIN_BC_SMARTSPACE_DATA";
    public static final String TAG = "BcSmartspaceDataPlugin";
    public static final String UI_SURFACE_DREAM = "dream";
    public static final String UI_SURFACE_HOME_SCREEN = "home";
    public static final String UI_SURFACE_LOCK_SCREEN_AOD = "lockscreen";
    public static final String UI_SURFACE_MEDIA = "media_data_manager";
    public static final int VERSION = 1;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public interface SmartspaceEventNotifier {
        void notifySmartspaceEvent(SmartspaceTargetEvent smartspaceTargetEvent);
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public interface SmartspaceTargetListener {
        void onSmartspaceTargetsUpdated(List<? extends Parcelable> list);
    }

    default void addOnAttachStateChangeListener(View.OnAttachStateChangeListener onAttachStateChangeListener) {
        throw new UnsupportedOperationException("Not implemented by " + getClass());
    }

    default SmartspaceView getView(ViewGroup viewGroup) {
        throw new UnsupportedOperationException("Not implemented by " + getClass());
    }

    default void notifySmartspaceEvent(SmartspaceTargetEvent smartspaceTargetEvent) {
        throw new UnsupportedOperationException("Not implemented by " + getClass());
    }

    default void onTargetsAvailable(List<SmartspaceTarget> list) {
        throw new UnsupportedOperationException("Not implemented by " + getClass());
    }

    default void registerListener(SmartspaceTargetListener smartspaceTargetListener) {
        throw new UnsupportedOperationException("Not implemented by " + getClass());
    }

    default void registerSmartspaceEventNotifier(SmartspaceEventNotifier smartspaceEventNotifier) {
        throw new UnsupportedOperationException("Not implemented by " + getClass());
    }

    default void unregisterListener(SmartspaceTargetListener smartspaceTargetListener) {
        throw new UnsupportedOperationException("Not implemented by " + getClass());
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public interface IntentStarter {
        default void startFromAction(SmartspaceAction smartspaceAction, View view, boolean z) {
            try {
                if (smartspaceAction.getIntent() != null) {
                    startIntent(view, smartspaceAction.getIntent(), z);
                } else if (smartspaceAction.getPendingIntent() != null) {
                    startPendingIntent(view, smartspaceAction.getPendingIntent(), z);
                }
            } catch (ActivityNotFoundException e) {
                Log.w(BcSmartspaceDataPlugin.TAG, "Could not launch intent for action: " + smartspaceAction, e);
            }
        }

        void startIntent(View view, Intent intent, boolean z);

        void startPendingIntent(View view, PendingIntent pendingIntent, boolean z);

        default void startFromAction(TapAction tapAction, View view, boolean z) {
            try {
                if (tapAction.getIntent() != null) {
                    startIntent(view, tapAction.getIntent(), z);
                } else if (tapAction.getPendingIntent() != null) {
                    startPendingIntent(view, tapAction.getPendingIntent(), z);
                }
            } catch (ActivityNotFoundException e) {
                Log.w(BcSmartspaceDataPlugin.TAG, "Could not launch intent for action: " + tapAction, e);
            }
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public interface SmartspaceView {
        default int getCurrentCardTopPadding() {
            throw new UnsupportedOperationException("Not implemented by " + getClass());
        }

        default int getSelectedPage() {
            throw new UnsupportedOperationException("Not implemented by " + getClass());
        }

        default void registerConfigProvider(BcSmartspaceConfigPlugin bcSmartspaceConfigPlugin) {
            throw new UnsupportedOperationException("Not implemented by " + getClass());
        }

        void registerDataProvider(BcSmartspaceDataPlugin bcSmartspaceDataPlugin);

        default void setDnd(Drawable drawable, String str) {
            throw new UnsupportedOperationException("Not implemented by " + getClass());
        }

        void setDozeAmount(float f);

        void setFalsingManager(FalsingManager falsingManager);

        void setIntentStarter(IntentStarter intentStarter);

        default void setMediaTarget(SmartspaceTarget smartspaceTarget) {
            throw new UnsupportedOperationException("Not implemented by " + getClass());
        }

        default void setNextAlarm(Drawable drawable, String str) {
            throw new UnsupportedOperationException("Not implemented by " + getClass());
        }

        void setPrimaryTextColor(int i);

        void setUiSurface(String str);

        default void setDozing(boolean z) {
        }

        default void setKeyguardBypassEnabled(boolean z) {
        }

        default void setSplitShadeEnabled(boolean z) {
        }
    }
}
