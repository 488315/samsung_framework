package com.android.systemui.privacy;

import com.android.internal.logging.UiEventLogger;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public enum PrivacyDialogEvent implements UiEventLogger.UiEventEnum {
    PRIVACY_DIALOG_ITEM_CLICKED_TO_APP_SETTINGS(904),
    PRIVACY_DIALOG_DISMISSED(905);

    private final int _id;

    PrivacyDialogEvent(int i) {
        this._id = i;
    }

    public final int getId() {
        return this._id;
    }
}