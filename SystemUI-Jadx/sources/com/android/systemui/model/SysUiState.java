package com.android.systemui.model;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import com.android.systemui.Dumpable;
import com.android.systemui.model.SysUiState;
import com.android.systemui.settings.DisplayTracker;
import com.android.systemui.shared.system.QuickStepContract;
import com.android.systemui.subscreen.SubScreenQuickPanelWindowController;
import com.samsung.android.desktopsystemui.sharedlib.keyguard.SemWallpaperColorsWrapper;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.function.Consumer;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class SysUiState implements Dumpable {
    public final DisplayTracker mDisplayTracker;
    public long mFlags;
    public final List mCallbacks = new ArrayList();
    public long mFlagsToSet = 0;
    public long mFlagsToClear = 0;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public interface SysUiStateCallback {
        void onSystemUiStateChanged(long j);
    }

    public SysUiState(DisplayTracker displayTracker) {
        this.mDisplayTracker = displayTracker;
    }

    public final void addCallback(SysUiStateCallback sysUiStateCallback) {
        ((ArrayList) this.mCallbacks).add(sysUiStateCallback);
        sysUiStateCallback.onSystemUiStateChanged(this.mFlags);
    }

    public final void commitUpdate(int i) {
        this.mDisplayTracker.getClass();
        if (i != 0 && i != SubScreenQuickPanelWindowController.COVER_DISPLAY) {
            Log.w("SysUiState", MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("Ignoring flag update for display: ", i), new Throwable());
        } else {
            long j = this.mFlags;
            final long j2 = (this.mFlagsToSet | j) & (~this.mFlagsToClear);
            if (j2 != j) {
                Log.d("SysUiState", "SysUiState changed: old=0x" + Long.toHexString(j) + " new=0x" + Long.toHexString(j2));
                ((ArrayList) this.mCallbacks).forEach(new Consumer() { // from class: com.android.systemui.model.SysUiState$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ((SysUiState.SysUiStateCallback) obj).onSystemUiStateChanged(j2);
                    }
                });
                this.mFlags = j2;
            }
        }
        this.mFlagsToSet = 0L;
        this.mFlagsToClear = 0L;
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("SysUiState state:");
        printWriter.print("  mSysUiStateFlags=");
        printWriter.println(this.mFlags);
        StringBuilder sb = new StringBuilder("    ");
        long j = this.mFlags;
        boolean z = QuickStepContract.SYSUI_FORCE_SET_BACK_GESTURE_BY_SPLUGIN;
        StringJoiner stringJoiner = new StringJoiner("|");
        if ((1 & j) != 0) {
            stringJoiner.add("screen_pinned");
        }
        if ((128 & j) != 0) {
            stringJoiner.add("overview_disabled");
        }
        if ((256 & j) != 0) {
            stringJoiner.add("home_disabled");
        }
        if ((1024 & j) != 0) {
            stringJoiner.add("search_disabled");
        }
        if ((2 & j) != 0) {
            stringJoiner.add("navbar_hidden");
        }
        if ((4 & j) != 0) {
            stringJoiner.add("notif_expanded");
        }
        if ((2048 & j) != 0) {
            stringJoiner.add("qs_visible");
        }
        if ((64 & j) != 0) {
            stringJoiner.add("keygrd_visible");
        }
        if ((512 & j) != 0) {
            stringJoiner.add("keygrd_occluded");
        }
        if ((8 & j) != 0) {
            stringJoiner.add("bouncer_visible");
        }
        if ((32768 & j) != 0) {
            stringJoiner.add("dialog_showing");
        }
        if ((16 & j) != 0) {
            stringJoiner.add("a11y_click");
        }
        if ((32 & j) != 0) {
            stringJoiner.add("a11y_long_click");
        }
        if ((4096 & j) != 0) {
            stringJoiner.add("tracing");
        }
        if ((8192 & j) != 0) {
            stringJoiner.add("asst_gesture_constrain");
        }
        if ((16384 & j) != 0) {
            stringJoiner.add("bubbles_expanded");
        }
        if ((65536 & j) != 0) {
            stringJoiner.add("one_handed_active");
        }
        if ((131072 & j) != 0) {
            stringJoiner.add("allow_gesture");
        }
        if ((262144 & j) != 0) {
            stringJoiner.add("ime_visible");
        }
        if ((524288 & j) != 0) {
            stringJoiner.add("magnification_overlap");
        }
        if ((1048576 & j) != 0) {
            stringJoiner.add("ime_switcher_showing");
        }
        if ((2097152 & j) != 0) {
            stringJoiner.add("device_dozing");
        }
        if ((4194304 & j) != 0) {
            stringJoiner.add("back_disabled");
        }
        if ((8388608 & j) != 0) {
            stringJoiner.add("bubbles_mange_menu_expanded");
        }
        if ((16777216 & j) != 0) {
            stringJoiner.add("immersive_mode");
        }
        if ((33554432 & j) != 0) {
            stringJoiner.add("vis_win_showing");
        }
        if ((67108864 & j) != 0) {
            stringJoiner.add("freeform_active_in_desktop_mode");
        }
        if ((134217728 & j) != 0) {
            stringJoiner.add("device_dreaming");
        }
        if ((536870912 & j) != 0) {
            stringJoiner.add("wakefulness_transition");
        }
        if ((268435456 & j) != 0) {
            stringJoiner.add("awake");
        }
        if ((1073741824 & j) != 0) {
            stringJoiner.add("notif_visible");
        }
        if ((2147483648L & j) != 0) {
            stringJoiner.add("keygrd_going_away");
        }
        if ((SemWallpaperColorsWrapper.LOCKSCREEN_STATUS_BAR & j) != 0) {
            stringJoiner.add("game_tools_showing");
        }
        if ((SemWallpaperColorsWrapper.LOCKSCREEN_LOCK_ICON & j) != 0) {
            stringJoiner.add("requested_recent_key");
        }
        if ((SemWallpaperColorsWrapper.LOCKSCREEN_CLOCK & j) != 0) {
            stringJoiner.add("requested_home_key");
        }
        if ((SemWallpaperColorsWrapper.LOCKSCREEN_NIO & j) != 0) {
            stringJoiner.add("navbar_gone");
        }
        if ((j & SemWallpaperColorsWrapper.LOCKSCREEN_NIO_TEXT) != 0) {
            stringJoiner.add("knox_hard_key_intent");
        }
        sb.append(stringJoiner.toString());
        printWriter.println(sb.toString());
        printWriter.print("    backGestureDisabled=");
        printWriter.println(QuickStepContract.isBackGestureDisabled(this.mFlags));
        printWriter.print("    assistantGestureDisabled=");
        printWriter.println(QuickStepContract.isAssistantGestureDisabled(this.mFlags));
    }

    public final void setFlag(long j, boolean z) {
        if (z) {
            this.mFlagsToSet = j | this.mFlagsToSet;
        } else {
            this.mFlagsToClear = j | this.mFlagsToClear;
        }
    }
}
