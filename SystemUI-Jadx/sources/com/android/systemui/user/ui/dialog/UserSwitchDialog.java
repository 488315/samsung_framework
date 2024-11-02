package com.android.systemui.user.ui.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.animation.DialogLaunchAnimator;
import com.android.systemui.animation.DialogLaunchAnimator$createActivityLaunchController$1;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.qs.PseudoGridView;
import com.android.systemui.qs.QSUserSwitcherEvent;
import com.android.systemui.qs.tiles.UserDetailView;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.util.DeviceState;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class UserSwitchDialog extends SystemUIDialog {
    public static final Intent USER_SETTINGS_INTENT;
    public static final Intent USER_SETTINGS_KT_TWO_PHONE_INTENT;

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
        USER_SETTINGS_INTENT = new Intent("android.settings.USER_SETTINGS");
        USER_SETTINGS_KT_TWO_PHONE_INTENT = new Intent("com.kt.menu.action.KT_TWOPHONE_SETTINGS");
    }

    public UserSwitchDialog(Context context, UserDetailView.Adapter adapter, final UiEventLogger uiEventLogger, final FalsingManager falsingManager, final ActivityStarter activityStarter, final DialogLaunchAnimator dialogLaunchAnimator) {
        super(context, 2132018528);
        SystemUIDialog.setShowForAllUsers(this);
        setCanceledOnTouchOutside(true);
        setTitle(R.string.qs_user_switch_dialog_title);
        setPositiveButton(R.string.quick_settings_done, new DialogInterface.OnClickListener() { // from class: com.android.systemui.user.ui.dialog.UserSwitchDialog.1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                uiEventLogger.log(QSUserSwitcherEvent.QS_USER_DETAIL_CLOSE);
            }
        });
        setButton(-3, R.string.quick_settings_more_user_settings, new DialogInterface.OnClickListener() { // from class: com.android.systemui.user.ui.dialog.UserSwitchDialog.2
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                Intent intent;
                if (!FalsingManager.this.isFalseTap(1)) {
                    uiEventLogger.log(QSUserSwitcherEvent.QS_USER_MORE_SETTINGS);
                    DialogLaunchAnimator$createActivityLaunchController$1 createActivityLaunchController$default = DialogLaunchAnimator.createActivityLaunchController$default(dialogLaunchAnimator, this.getButton(-3));
                    if (createActivityLaunchController$default == null) {
                        this.dismiss();
                    }
                    ActivityStarter activityStarter2 = activityStarter;
                    if (QpRune.QUICK_MANAGE_TWO_PHONE && DeviceState.supportsMultipleUsers()) {
                        intent = UserSwitchDialog.USER_SETTINGS_KT_TWO_PHONE_INTENT;
                    } else {
                        intent = UserSwitchDialog.USER_SETTINGS_INTENT;
                    }
                    activityStarter2.postStartActivityDismissingKeyguard(intent, 0, createActivityLaunchController$default);
                }
            }
        }, false);
        Window window = getWindow();
        if (window != null) {
            window.setGravity(81);
        }
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.qs_user_dialog_content, (ViewGroup) null);
        setView(inflate);
        PseudoGridView.ViewGroupAdapterBridge.link((ViewGroup) inflate.findViewById(R.id.grid), adapter);
        adapter.mDialogShower = new DialogShowerImpl(this, dialogLaunchAnimator);
    }
}
