package com.android.systemui.statusbar.notification;

import android.app.INotificationManager;
import android.app.NotificationChannel;
import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Slog;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class NotificationChannelHelper {
    public static NotificationChannel createConversationChannelIfNeeded(Context context, INotificationManager iNotificationManager, NotificationEntry notificationEntry, NotificationChannel notificationChannel) {
        if (!TextUtils.isEmpty(notificationChannel.getConversationId())) {
            return notificationChannel;
        }
        String shortcutId = notificationEntry.mSbn.getShortcutId();
        String packageName = notificationEntry.mSbn.getPackageName();
        int uid = notificationEntry.mSbn.getUid();
        if (!TextUtils.isEmpty(shortcutId) && !TextUtils.isEmpty(packageName) && notificationEntry.mRanking.getConversationShortcutInfo() != null) {
            try {
                notificationChannel.setName(getName(notificationEntry));
                iNotificationManager.createConversationNotificationChannelForPackage(packageName, uid, notificationChannel, shortcutId);
                return iNotificationManager.getConversationNotificationChannel(context.getOpPackageName(), UserHandle.getUserId(uid), packageName, notificationChannel.getId(), false, shortcutId);
            } catch (RemoteException e) {
                Slog.e("NotificationChannelHelper", "Could not create conversation channel", e);
                return notificationChannel;
            }
        }
        return notificationChannel;
    }

    public static CharSequence getName(NotificationEntry notificationEntry) {
        if (notificationEntry.mRanking.getConversationShortcutInfo().getLabel() != null) {
            return notificationEntry.mRanking.getConversationShortcutInfo().getLabel().toString();
        }
        Bundle bundle = notificationEntry.mSbn.getNotification().extras;
        CharSequence charSequence = bundle.getCharSequence("android.conversationTitle");
        if (TextUtils.isEmpty(charSequence)) {
            charSequence = bundle.getCharSequence("android.title");
        }
        if (TextUtils.isEmpty(charSequence)) {
            return "fallback";
        }
        return charSequence;
    }
}
