package com.android.systemui.bixby2.controller.mediacontrol;

import android.content.Intent;
import android.os.Binder;
import android.os.UserHandle;
import com.android.systemui.bixby2.CommandActionResponse;
import com.android.systemui.bixby2.actionresult.ActionResults;
import com.android.systemui.bixby2.controller.mediacontrol.MediaCommandType;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class StopController extends MediaCommandType {
    public static final Companion Companion = new Companion(null);
    private static final String[] NOISY_INTENT_DENIED_LIST = {"com.netease.cloudmusic", "com.kugou.android"};

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    private final boolean isAllowedAppReceiveNoisyIntent(String str) {
        return !ArraysKt___ArraysKt.contains(NOISY_INTENT_DENIED_LIST, str);
    }

    @Override // com.android.systemui.bixby2.controller.mediacontrol.MediaCommandType
    public CommandActionResponse action() {
        MediaCommandType.Companion companion = MediaCommandType.Companion;
        if (companion.isMediaControlActive(companion.getMediaInfo().isMediaActive)) {
            companion.getMediaController().getTransportControls().pause();
            if (isAllowedAppReceiveNoisyIntent(companion.getMediaController().getPackageName())) {
                stopMedia();
            }
            return new CommandActionResponse(1, "success");
        }
        return new CommandActionResponse(2, ActionResults.RESULT_NO_MEDIA_EXISTS);
    }

    public final void stopMedia() {
        Intent intent = new Intent("android.media.AUDIO_BECOMING_NOISY_SEC");
        intent.addFlags(QuickStepContract.SYSUI_STATE_REQUESTED_RECENT_KEY);
        intent.addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
        Intent intent2 = new Intent("android.media.AUDIO_BECOMING_NOISY");
        intent2.addFlags(QuickStepContract.SYSUI_STATE_REQUESTED_RECENT_KEY);
        intent2.addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            MediaCommandType.Companion companion = MediaCommandType.Companion;
            companion.getContext().sendBroadcastAsUser(intent, UserHandle.ALL);
            companion.getContext().sendBroadcastAsUser(intent2, UserHandle.ALL);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }
}
