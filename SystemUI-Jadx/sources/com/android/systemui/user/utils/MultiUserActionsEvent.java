package com.android.systemui.user.utils;

import com.android.internal.logging.UiEventLogger;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public enum MultiUserActionsEvent implements UiEventLogger.UiEventEnum {
    CREATE_USER_FROM_USER_SWITCHER(1257),
    CREATE_GUEST_FROM_USER_SWITCHER(1258),
    CREATE_RESTRICTED_USER_FROM_USER_SWITCHER(1259),
    SWITCH_TO_USER_FROM_USER_SWITCHER(1266),
    SWITCH_TO_GUEST_FROM_USER_SWITCHER(1267),
    SWITCH_TO_RESTRICTED_USER_FROM_USER_SWITCHER(1268),
    /* JADX INFO: Fake field, exist only in values array */
    GRANT_ADMIN_FROM_USER_SWITCHER_CREATION_DIALOG(1278),
    /* JADX INFO: Fake field, exist only in values array */
    NOT_GRANT_ADMIN_FROM_USER_SWITCHER_CREATION_DIALOG(1279);

    private final int value;

    MultiUserActionsEvent(int i) {
        this.value = i;
    }

    public final int getId() {
        return this.value;
    }
}
