package com.android.systemui.user.ui.dialog;

import com.android.systemui.user.ui.dialog.ExitGuestDialog;
import kotlin.Function;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.FunctionAdapter;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final /* synthetic */ class UserSwitcherDialogCoordinator$sam$com_android_systemui_user_ui_dialog_ExitGuestDialog_OnExitGuestUserListener$0 implements ExitGuestDialog.OnExitGuestUserListener, FunctionAdapter {
    public final /* synthetic */ Function3 function;

    public UserSwitcherDialogCoordinator$sam$com_android_systemui_user_ui_dialog_ExitGuestDialog_OnExitGuestUserListener$0(Function3 function3) {
        this.function = function3;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof ExitGuestDialog.OnExitGuestUserListener) || !(obj instanceof FunctionAdapter)) {
            return false;
        }
        return Intrinsics.areEqual(this.function, ((FunctionAdapter) obj).getFunctionDelegate());
    }

    @Override // kotlin.jvm.internal.FunctionAdapter
    public final Function getFunctionDelegate() {
        return this.function;
    }

    public final int hashCode() {
        return this.function.hashCode();
    }

    public final /* synthetic */ void onExitGuestUser(int i, int i2, boolean z) {
        this.function.invoke(Integer.valueOf(i), Integer.valueOf(i2), Boolean.valueOf(z));
    }
}
