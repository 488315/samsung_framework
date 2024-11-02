package com.android.systemui.contrast;

import android.app.UiModeManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.android.systemui.R;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.util.settings.SecureSettings;
import com.android.systemui.util.settings.SecureSettingsImpl;
import java.util.Map;
import java.util.concurrent.Executor;
import kotlin.Pair;
import kotlin.collections.MapsKt__MapsKt;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class ContrastDialog extends SystemUIDialog implements UiModeManager.ContrastChangeListener {
    public Map contrastButtons;
    public View dialogView;
    public float initialContrast;
    public final Executor mainExecutor;
    public final SecureSettings secureSettings;
    public final UiModeManager uiModeManager;
    public final UserTracker userTracker;

    public ContrastDialog(Context context, Executor executor, UiModeManager uiModeManager, UserTracker userTracker, SecureSettings secureSettings) {
        super(context);
        this.mainExecutor = executor;
        this.uiModeManager = uiModeManager;
        this.userTracker = userTracker;
        this.secureSettings = secureSettings;
        this.initialContrast = UiModeManager.ContrastUtils.fromContrastLevel(0);
    }

    public final void highlightContrast(int i) {
        boolean z;
        Map map = this.contrastButtons;
        if (map == null) {
            map = null;
        }
        for (Map.Entry entry : map.entrySet()) {
            int intValue = ((Number) entry.getKey()).intValue();
            FrameLayout frameLayout = (FrameLayout) entry.getValue();
            if (intValue == i) {
                z = true;
            } else {
                z = false;
            }
            frameLayout.setSelected(z);
        }
    }

    @Override // android.app.UiModeManager.ContrastChangeListener
    public final void onContrastChanged(float f) {
        highlightContrast(UiModeManager.ContrastUtils.toContrastLevel(f));
    }

    @Override // com.android.systemui.statusbar.phone.SystemUIDialog, android.app.AlertDialog, android.app.Dialog
    public final void onCreate(Bundle bundle) {
        ContentResolver contentResolver;
        boolean z;
        Map map = null;
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.contrast_dialog, (ViewGroup) null);
        this.dialogView = inflate;
        setView(inflate);
        setTitle(R.string.quick_settings_contrast_label);
        setNeutralButton(R.string.cancel, new DialogInterface.OnClickListener() { // from class: com.android.systemui.contrast.ContrastDialog$onCreate$1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                ContrastDialog contrastDialog = ContrastDialog.this;
                SecureSettings secureSettings = contrastDialog.secureSettings;
                float f = contrastDialog.initialContrast;
                int userId = ((UserTrackerImpl) contrastDialog.userTracker).getUserId();
                secureSettings.getClass();
                ((SecureSettingsImpl) secureSettings).putStringForUser(userId, "contrast_level", Float.toString(f));
                ContrastDialog.this.dismiss();
            }
        });
        Context context = getContext();
        if (context != null) {
            contentResolver = context.getContentResolver();
        } else {
            contentResolver = null;
        }
        boolean z2 = false;
        if (Settings.System.getInt(contentResolver, "wallpapertheme_state", -1) == 1) {
            z = true;
        } else {
            z = false;
        }
        if (!z) {
            setNegativeButton(R.string.cell_data_off, new DialogInterface.OnClickListener() { // from class: com.android.systemui.contrast.ContrastDialog$onCreate$2
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    ContrastDialog contrastDialog = ContrastDialog.this;
                    SecureSettings secureSettings = contrastDialog.secureSettings;
                    int userId = ((UserTrackerImpl) contrastDialog.userTracker).getUserId();
                    secureSettings.getClass();
                    ((SecureSettingsImpl) secureSettings).putStringForUser(userId, "contrast_level", Float.toString(-1.0f));
                }
            });
        }
        setPositiveButton(R.string.done, new DialogInterface.OnClickListener() { // from class: com.android.systemui.contrast.ContrastDialog$onCreate$3
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                ContrastDialog.this.dismiss();
            }
        });
        super.onCreate(bundle);
        Map mapOf = MapsKt__MapsKt.mapOf(new Pair(0, findViewById(R.id.contrast_button_standard)), new Pair(1, findViewById(R.id.contrast_button_medium)), new Pair(2, findViewById(R.id.contrast_button_high)));
        this.contrastButtons = mapOf;
        if (mapOf != null) {
            map = mapOf;
        }
        for (Map.Entry entry : map.entrySet()) {
            final int intValue = ((Number) entry.getKey()).intValue();
            ((FrameLayout) entry.getValue()).setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.contrast.ContrastDialog$onCreate$4$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    float fromContrastLevel = UiModeManager.ContrastUtils.fromContrastLevel(intValue);
                    ContrastDialog contrastDialog = this;
                    SecureSettings secureSettings = contrastDialog.secureSettings;
                    int userId = ((UserTrackerImpl) contrastDialog.userTracker).getUserId();
                    secureSettings.getClass();
                    ((SecureSettingsImpl) secureSettings).putStringForUser(userId, "contrast_level", Float.toString(fromContrastLevel));
                }
            });
        }
        this.initialContrast = this.uiModeManager.getContrast();
        if (this.secureSettings.getFloatForUser("contrast_level", ((UserTrackerImpl) this.userTracker).getUserId(), -1.0f) == -1.0f) {
            z2 = true;
        }
        if (!z2) {
            highlightContrast(UiModeManager.ContrastUtils.toContrastLevel(this.initialContrast));
        }
    }

    @Override // com.android.systemui.statusbar.phone.SystemUIDialog
    public final void start() {
        this.uiModeManager.addContrastChangeListener(this.mainExecutor, this);
    }

    @Override // com.android.systemui.statusbar.phone.SystemUIDialog
    public final void stop() {
        this.uiModeManager.removeContrastChangeListener(this);
    }

    public static /* synthetic */ void getContrastButtons$annotations() {
    }

    public static /* synthetic */ void getInitialContrast$annotations() {
    }
}
