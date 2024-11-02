package com.android.systemui.volume.util;

import android.app.KeyguardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.UserHandle;
import android.view.DisplayCutout;
import android.view.WindowInsets;
import com.android.systemui.Dependency;
import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.statusbar.notification.row.RowInflaterTask$$ExternalSyntheticOutline0;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.window.StatusBarWindowView;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import dagger.Lazy;
import java.util.List;
import java.util.Optional;
import kotlin.Result;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class StatusBarWrapper {
    public final Lazy centralSurfacesLazy;
    public final Context context;
    public final KeyguardManagerWrapper keyguardManagerWrapper;
    public final LogWrapper logWrapper;

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

    public StatusBarWrapper(Context context, LogWrapper logWrapper, KeyguardManagerWrapper keyguardManagerWrapper, Lazy lazy) {
        this.context = context;
        this.logWrapper = logWrapper;
        this.keyguardManagerWrapper = keyguardManagerWrapper;
        this.centralSurfacesLazy = lazy;
    }

    public final int getCutoutHeight() {
        StatusBarWindowView statusBarWindowView;
        WindowInsets rootWindowInsets;
        DisplayCutout displayCutout;
        List<Rect> boundingRects;
        Rect rect;
        CentralSurfaces centralSurfaces = (CentralSurfaces) ((Optional) this.centralSurfacesLazy.get()).orElse(null);
        if (centralSurfaces != null && (statusBarWindowView = ((CentralSurfacesImpl) centralSurfaces).mStatusBarWindowController.mStatusBarWindowView) != null && (rootWindowInsets = statusBarWindowView.getRootWindowInsets()) != null && (displayCutout = rootWindowInsets.getDisplayCutout()) != null && (boundingRects = displayCutout.getBoundingRects()) != null && (rect = (Rect) CollectionsKt___CollectionsKt.firstOrNull((List) boundingRects)) != null) {
            return Math.min(rect.height(), rect.width());
        }
        return 0;
    }

    public final void startHearingEnhancementsActivity() {
        Object failure;
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, "com.android.settings.Settings$AccessibilityHearingEnhancementsActivity"));
        intent.addFlags(335544320);
        Bundle bundle = new Bundle();
        bundle.putString(":settings:fragment_args_key", "all_sound_off_key");
        Unit unit = Unit.INSTANCE;
        intent.putExtra(":settings:show_fragment_args", bundle);
        try {
            int i = Result.$r8$clinit;
            this.context.startActivityAsUser(intent, UserHandle.CURRENT);
            failure = Unit.INSTANCE;
        } catch (Throwable th) {
            int i2 = Result.$r8$clinit;
            failure = new Result.Failure(th);
        }
        Throwable m2571exceptionOrNullimpl = Result.m2571exceptionOrNullimpl(failure);
        if (m2571exceptionOrNullimpl != null) {
            this.logWrapper.e("StatusBarWrapper", "startHearingEnhancementsActivity : Exception = " + m2571exceptionOrNullimpl);
        }
    }

    public final void startLeBroadcastActivity() {
        Object failure;
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, "com.android.settings.Settings$SecBluetoothBroadcastSourceActivity"));
        intent.addFlags(335544320);
        try {
            int i = Result.$r8$clinit;
            this.context.startActivityAsUser(intent, UserHandle.CURRENT);
            failure = Unit.INSTANCE;
        } catch (Throwable th) {
            int i2 = Result.$r8$clinit;
            failure = new Result.Failure(th);
        }
        Throwable m2571exceptionOrNullimpl = Result.m2571exceptionOrNullimpl(failure);
        if (m2571exceptionOrNullimpl != null) {
            this.logWrapper.e("StatusBarWrapper", "startLeBroadcastActivity : Exception = " + m2571exceptionOrNullimpl);
        }
    }

    public final void startSettingsActivity() {
        Object failure;
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.setComponent(new ComponentName(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, "com.android.settings.Settings$SecVolumeLimiterSettingsActivity"));
        intent.addFlags(335544320);
        try {
            int i = Result.$r8$clinit;
            KeyguardManagerWrapper keyguardManagerWrapper = this.keyguardManagerWrapper;
            keyguardManagerWrapper.getClass();
            SystemServiceExtension.INSTANCE.getClass();
            if (((KeyguardManager) keyguardManagerWrapper.context.getSystemService(KeyguardManager.class)).isKeyguardLocked()) {
                ((ActivityStarter) Dependency.get(ActivityStarter.class)).startActivity(intent, false);
            } else {
                this.context.startActivityAsUser(intent, UserHandle.CURRENT);
            }
            failure = Unit.INSTANCE;
        } catch (Throwable th) {
            int i2 = Result.$r8$clinit;
            failure = new Result.Failure(th);
        }
        Throwable m2571exceptionOrNullimpl = Result.m2571exceptionOrNullimpl(failure);
        if (m2571exceptionOrNullimpl != null) {
            this.logWrapper.e("StatusBarWrapper", RowInflaterTask$$ExternalSyntheticOutline0.m("startSettingsActivity : Exception = ", m2571exceptionOrNullimpl));
        }
    }

    public final void startVolumeSettingsActivity() {
        Object failure;
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.setComponent(new ComponentName(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, "com.android.settings.Settings$SecVolumeSettingsActivity"));
        intent.addFlags(335544320);
        try {
            int i = Result.$r8$clinit;
            KeyguardManagerWrapper keyguardManagerWrapper = this.keyguardManagerWrapper;
            keyguardManagerWrapper.getClass();
            SystemServiceExtension.INSTANCE.getClass();
            if (((KeyguardManager) keyguardManagerWrapper.context.getSystemService(KeyguardManager.class)).isKeyguardLocked()) {
                ((ActivityStarter) Dependency.get(ActivityStarter.class)).startActivity(intent, false);
            } else {
                this.context.startActivityAsUser(intent, UserHandle.CURRENT);
            }
            failure = Unit.INSTANCE;
        } catch (Throwable th) {
            int i2 = Result.$r8$clinit;
            failure = new Result.Failure(th);
        }
        Throwable m2571exceptionOrNullimpl = Result.m2571exceptionOrNullimpl(failure);
        if (m2571exceptionOrNullimpl != null) {
            this.logWrapper.e("StatusBarWrapper", RowInflaterTask$$ExternalSyntheticOutline0.m("startVolumeSettingsActivity : Exception = ", m2571exceptionOrNullimpl));
        }
    }
}
