package com.android.internal.notification;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import com.android.internal.R;

/* loaded from: classes5.dex */
public final class NotificationAccessConfirmationActivityContract {
    public static final String EXTRA_COMPONENT_NAME = "component_name";
    public static final String EXTRA_USER_ID = "user_id";

    public static Intent launcherIntent(Context context, int userId, ComponentName component) {
        return new Intent().setComponent(ComponentName.unflattenFromString(context.getString(R.string.config_notificationAccessConfirmationActivity))).putExtra("user_id", userId).putExtra(EXTRA_COMPONENT_NAME, component);
    }
}
