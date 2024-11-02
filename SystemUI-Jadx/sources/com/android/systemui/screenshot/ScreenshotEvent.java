package com.android.systemui.screenshot;

import com.android.internal.logging.UiEventLogger;
import com.samsung.android.knox.net.vpn.VpnErrorValues;
import com.sec.ims.volte2.data.VolteConstants;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public enum ScreenshotEvent implements UiEventLogger.UiEventEnum {
    SCREENSHOT_REQUESTED_GLOBAL_ACTIONS(302),
    SCREENSHOT_REQUESTED_KEY_CHORD(303),
    SCREENSHOT_REQUESTED_KEY_OTHER(384),
    SCREENSHOT_REQUESTED_OVERVIEW(304),
    SCREENSHOT_REQUESTED_ACCESSIBILITY_ACTIONS(VolteConstants.ErrorCode.ALTERNATIVE_SERVICES_EMERGENCY_CSFB),
    SCREENSHOT_REQUESTED_VENDOR_GESTURE(638),
    SCREENSHOT_REQUESTED_OTHER(305),
    SCREENSHOT_SAVED(VpnErrorValues.ERROR_STOPPING_CONNECTION_BEFORE_REMOVING),
    SCREENSHOT_NOT_SAVED(336),
    SCREENSHOT_CAPTURE_FAILED(1281),
    SCREENSHOT_PREVIEW_TAPPED(VpnErrorValues.ERROR_VPN_RECREATE_PROFILE_FAIL),
    SCREENSHOT_EDIT_TAPPED(308),
    SCREENSHOT_SHARE_TAPPED(309),
    SCREENSHOT_SMART_ACTION_TAPPED(374),
    /* JADX INFO: Fake field, exist only in values array */
    SCREENSHOT_LONG_SCREENSHOT_COMPLETED(373),
    SCREENSHOT_INTERACTION_TIMEOUT(310),
    /* JADX INFO: Fake field, exist only in values array */
    SCREENSHOT_LONG_SCREENSHOT_COMPLETED(311),
    SCREENSHOT_SWIPE_DISMISSED(656),
    SCREENSHOT_DISMISSED_OTHER(1076),
    SCREENSHOT_REENTERED(640),
    /* JADX INFO: Fake field, exist only in values array */
    SCREENSHOT_LONG_SCREENSHOT_COMPLETED(687),
    /* JADX INFO: Fake field, exist only in values array */
    SCREENSHOT_LONG_SCREENSHOT_COMPLETED(688),
    SCREENSHOT_LONG_SCREENSHOT_SHARE(689),
    SCREENSHOT_LONG_SCREENSHOT_EDIT(690),
    /* JADX INFO: Fake field, exist only in values array */
    SCREENSHOT_LONG_SCREENSHOT_COMPLETED(880),
    /* JADX INFO: Fake field, exist only in values array */
    SCREENSHOT_LONG_SCREENSHOT_COMPLETED(881),
    /* JADX INFO: Fake field, exist only in values array */
    SCREENSHOT_LONG_SCREENSHOT_COMPLETED(882),
    SCREENSHOT_LONG_SCREENSHOT_ACTIVITY_STARTED(889),
    SCREENSHOT_LONG_SCREENSHOT_ACTIVITY_CACHED_IMAGE_LOADED(890),
    SCREENSHOT_LONG_SCREENSHOT_ACTIVITY_FINISHED(891),
    SCREENSHOT_LONG_SCREENSHOT_SAVED(910),
    SCREENSHOT_LONG_SCREENSHOT_EXIT(911),
    SCREENSHOT_SAVED_TO_WORK_PROFILE(1240);

    private final int mId;

    ScreenshotEvent(int i) {
        this.mId = i;
    }

    public static ScreenshotEvent getScreenshotSource(int i) {
        if (i != 0) {
            if (i != 1) {
                if (i != 2) {
                    if (i != 3) {
                        if (i != 4) {
                            if (i != 6) {
                                return SCREENSHOT_REQUESTED_OTHER;
                            }
                            return SCREENSHOT_REQUESTED_VENDOR_GESTURE;
                        }
                        return SCREENSHOT_REQUESTED_ACCESSIBILITY_ACTIONS;
                    }
                    return SCREENSHOT_REQUESTED_OVERVIEW;
                }
                return SCREENSHOT_REQUESTED_KEY_OTHER;
            }
            return SCREENSHOT_REQUESTED_KEY_CHORD;
        }
        return SCREENSHOT_REQUESTED_GLOBAL_ACTIONS;
    }

    public final int getId() {
        return this.mId;
    }
}
