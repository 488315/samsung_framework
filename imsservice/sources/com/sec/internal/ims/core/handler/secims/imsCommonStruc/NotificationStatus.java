package com.sec.internal.ims.core.handler.secims.imsCommonStruc;

/* loaded from: classes.dex */
public final class NotificationStatus {
    public static final int NOTIFICATION_CANCELED = 4;
    public static final int NOTIFICATION_DELIVERED = 0;
    public static final int NOTIFICATION_DISPLAYED = 1;
    public static final int NOTIFICATION_INTERWORKING_MMS = 3;
    public static final int NOTIFICATION_INTERWORKING_SMS = 2;
    public static final String[] names = {"NOTIFICATION_DELIVERED", "NOTIFICATION_DISPLAYED", "NOTIFICATION_INTERWORKING_SMS", "NOTIFICATION_INTERWORKING_MMS", "NOTIFICATION_CANCELED"};

    private NotificationStatus() {
    }

    public static String name(int i) {
        return names[i];
    }
}
