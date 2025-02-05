package com.android.systemui.media.taptotransfer.sender;

import com.android.internal.logging.UiEventLogger;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public enum MediaTttSenderUiEvents implements UiEventLogger.UiEventEnum {
    MEDIA_TTT_SENDER_UNDO_TRANSFER_TO_RECEIVER_CLICKED(971),
    MEDIA_TTT_SENDER_UNDO_TRANSFER_TO_THIS_DEVICE_CLICKED(972),
    MEDIA_TTT_SENDER_ALMOST_CLOSE_TO_START_CAST(973),
    MEDIA_TTT_SENDER_ALMOST_CLOSE_TO_END_CAST(974),
    MEDIA_TTT_SENDER_TRANSFER_TO_RECEIVER_TRIGGERED(975),
    MEDIA_TTT_SENDER_TRANSFER_TO_THIS_DEVICE_TRIGGERED(976),
    MEDIA_TTT_SENDER_TRANSFER_TO_RECEIVER_SUCCEEDED(977),
    MEDIA_TTT_SENDER_TRANSFER_TO_THIS_DEVICE_SUCCEEDED(978),
    MEDIA_TTT_SENDER_TRANSFER_TO_RECEIVER_FAILED(979),
    MEDIA_TTT_SENDER_TRANSFER_TO_THIS_DEVICE_FAILED(980),
    MEDIA_TTT_SENDER_FAR_FROM_RECEIVER(981);

    private final int metricId;

    MediaTttSenderUiEvents(int i) {
        this.metricId = i;
    }

    public final int getId() {
        return this.metricId;
    }
}
