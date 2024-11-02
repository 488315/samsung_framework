package com.android.keyguard;

import android.os.PowerManager;
import com.android.internal.logging.UiEventLogger;
import com.sec.ims.volte2.data.VolteConstants;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX WARN: Enum visitor error
jadx.core.utils.exceptions.JadxRuntimeException: Init of enum field 'FACE_AUTH_TRIGGERED_OCCLUDING_APP_REQUESTED' uses external variables
	at jadx.core.dex.visitors.EnumVisitor.createEnumFieldByConstructor(EnumVisitor.java:451)
	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByRegister(EnumVisitor.java:395)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromFilledArray(EnumVisitor.java:324)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:262)
	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:151)
	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:100)
 */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public class FaceAuthUiEvent implements UiEventLogger.UiEventEnum {
    public static final /* synthetic */ FaceAuthUiEvent[] $VALUES;
    public static final FaceAuthUiEvent FACE_AUTH_NON_STRONG_BIOMETRIC_ALLOWED_CHANGED;
    public static final FaceAuthUiEvent FACE_AUTH_STARTED_LOCK_EDIT_MODE_FINISHED;
    public static final FaceAuthUiEvent FACE_AUTH_STOPPED_DREAM_STARTED;
    public static final FaceAuthUiEvent FACE_AUTH_STOPPED_FACE_CANCEL_NOT_RECEIVED;
    public static final FaceAuthUiEvent FACE_AUTH_STOPPED_FACE_ERROR;
    public static final FaceAuthUiEvent FACE_AUTH_STOPPED_FACE_FAILED;
    public static final FaceAuthUiEvent FACE_AUTH_STOPPED_FINISHED_GOING_TO_SLEEP;
    public static final FaceAuthUiEvent FACE_AUTH_STOPPED_KEYGUARD_GOING_AWAY;
    public static final FaceAuthUiEvent FACE_AUTH_STOPPED_PREV_CREDENTIAL_VIEW;
    public static final FaceAuthUiEvent FACE_AUTH_STOPPED_SESSION_CLOSE;
    public static final FaceAuthUiEvent FACE_AUTH_STOPPED_TRUST_ENABLED;
    public static final FaceAuthUiEvent FACE_AUTH_STOPPED_USER_INPUT_ON_BOUNCER;
    public static final FaceAuthUiEvent FACE_AUTH_TRIGGERED_ALL_AUTHENTICATORS_REGISTERED;
    public static final FaceAuthUiEvent FACE_AUTH_TRIGGERED_ALTERNATE_BIOMETRIC_BOUNCER_SHOWN;
    public static final FaceAuthUiEvent FACE_AUTH_TRIGGERED_DURING_CANCELLATION;
    public static final FaceAuthUiEvent FACE_AUTH_TRIGGERED_ENROLLMENTS_CHANGED;
    public static final FaceAuthUiEvent FACE_AUTH_TRIGGERED_FACE_LOCKOUT_RESET;
    public static final FaceAuthUiEvent FACE_AUTH_TRIGGERED_NOTIFICATION_PANEL_CLICKED;
    public static final FaceAuthUiEvent FACE_AUTH_TRIGGERED_OCCLUDING_APP_REQUESTED;
    public static final FaceAuthUiEvent FACE_AUTH_TRIGGERED_ON_REACH_GESTURE_ON_AOD;
    public static final FaceAuthUiEvent FACE_AUTH_TRIGGERED_PICK_UP_GESTURE_TRIGGERED;
    public static final FaceAuthUiEvent FACE_AUTH_TRIGGERED_QS_EXPANDED;
    public static final FaceAuthUiEvent FACE_AUTH_TRIGGERED_RETRY_AFTER_HW_UNAVAILABLE;
    public static final FaceAuthUiEvent FACE_AUTH_TRIGGERED_RETRY_BUTTON_CLICKED;
    public static final FaceAuthUiEvent FACE_AUTH_TRIGGERED_SWIPE_UP_ON_BOUNCER;
    public static final FaceAuthUiEvent FACE_AUTH_TRIGGERED_TRUST_DISABLED;
    public static final FaceAuthUiEvent FACE_AUTH_TRIGGERED_UDFPS_POINTER_DOWN;
    public static final FaceAuthUiEvent FACE_AUTH_UPDATED_ASSISTANT_VISIBILITY_CHANGED;
    public static final FaceAuthUiEvent FACE_AUTH_UPDATED_BIOMETRIC_ENABLED_ON_KEYGUARD;
    public static final FaceAuthUiEvent FACE_AUTH_UPDATED_BIOMETRIC_LOCKOUT_DEADLINE;
    public static final FaceAuthUiEvent FACE_AUTH_UPDATED_CAMERA_LAUNCHED;
    public static final FaceAuthUiEvent FACE_AUTH_UPDATED_COCKTAIL_BAR_SHOWING_CHANGED;
    public static final FaceAuthUiEvent FACE_AUTH_UPDATED_COVER_STATE_CHANGED;
    public static final FaceAuthUiEvent FACE_AUTH_UPDATED_DYNAMIC_LOCK;
    public static final FaceAuthUiEvent FACE_AUTH_UPDATED_FOLDER_STATE_CHANGED;
    public static final FaceAuthUiEvent FACE_AUTH_UPDATED_FP_AUTHENTICATED;
    public static final FaceAuthUiEvent FACE_AUTH_UPDATED_FULL_SCREEN_FACE_WIDGET;
    public static final FaceAuthUiEvent FACE_AUTH_UPDATED_GOING_TO_SLEEP;
    public static final FaceAuthUiEvent FACE_AUTH_UPDATED_KEYGUARD_OCCLUSION_CHANGED;
    public static final FaceAuthUiEvent FACE_AUTH_UPDATED_KEYGUARD_RESET;
    public static final FaceAuthUiEvent FACE_AUTH_UPDATED_KEYGUARD_UNLOCKING;
    public static final FaceAuthUiEvent FACE_AUTH_UPDATED_KEYGUARD_VISIBILITY_CHANGED;
    public static final FaceAuthUiEvent FACE_AUTH_UPDATED_LOCKOUT_DEADLINE;
    public static final FaceAuthUiEvent FACE_AUTH_UPDATED_LOCK_ICON_PRESSED;
    public static final FaceAuthUiEvent FACE_AUTH_UPDATED_NOTI_STAR_STATE_CHANGED;
    public static final FaceAuthUiEvent FACE_AUTH_UPDATED_ON_FACE_AUTHENTICATED;
    public static final FaceAuthUiEvent FACE_AUTH_UPDATED_POSTURE_CHANGED;
    public static final FaceAuthUiEvent FACE_AUTH_UPDATED_PRIMARY_BOUNCER_SHOWN;
    public static final FaceAuthUiEvent FACE_AUTH_UPDATED_PRIMARY_BOUNCER_SHOWN_OR_WILL_BE_SHOWN;
    public static final FaceAuthUiEvent FACE_AUTH_UPDATED_QS_FULLY_EXPANDED;
    public static final FaceAuthUiEvent FACE_AUTH_UPDATED_SECURE_STATE_UNLOCK_CHANGED;
    public static final FaceAuthUiEvent FACE_AUTH_UPDATED_STARTED_WAKING_UP;
    public static final FaceAuthUiEvent FACE_AUTH_UPDATED_STRONG_AUTH_CHANGED;
    public static final FaceAuthUiEvent FACE_AUTH_UPDATED_SUB_SCREEN;
    public static final FaceAuthUiEvent FACE_AUTH_UPDATED_USER_SWITCHING;
    public static final FaceAuthUiEvent FACE_AUTH_UPDATED_WINDOW_FOCUS_CHANGED;
    private int extraInfo;
    private final int id;
    private final String reason;

    static {
        int i = 0;
        int i2 = 4;
        DefaultConstructorMarker defaultConstructorMarker = null;
        FaceAuthUiEvent faceAuthUiEvent = new FaceAuthUiEvent("FACE_AUTH_TRIGGERED_OCCLUDING_APP_REQUESTED", 0, 1146, "Face auth due to request from occluding app.", i, i2, defaultConstructorMarker);
        FACE_AUTH_TRIGGERED_OCCLUDING_APP_REQUESTED = faceAuthUiEvent;
        FaceAuthUiEvent faceAuthUiEvent2 = new FaceAuthUiEvent("FACE_AUTH_TRIGGERED_UDFPS_POINTER_DOWN", 1, 1147, "Face auth triggered due to finger down on UDFPS", 0, 4, null);
        FACE_AUTH_TRIGGERED_UDFPS_POINTER_DOWN = faceAuthUiEvent2;
        int i3 = 0;
        int i4 = 4;
        DefaultConstructorMarker defaultConstructorMarker2 = null;
        FaceAuthUiEvent faceAuthUiEvent3 = new FaceAuthUiEvent("FACE_AUTH_TRIGGERED_SWIPE_UP_ON_BOUNCER", 2, 1148, "Face auth due to swipe up on bouncer", i3, i4, defaultConstructorMarker2);
        FACE_AUTH_TRIGGERED_SWIPE_UP_ON_BOUNCER = faceAuthUiEvent3;
        FaceAuthUiEvent faceAuthUiEvent4 = new FaceAuthUiEvent("FACE_AUTH_TRIGGERED_ON_REACH_GESTURE_ON_AOD", 3, 1149, "Face auth requested when user reaches for the device on AoD.", 0, 4, null);
        FACE_AUTH_TRIGGERED_ON_REACH_GESTURE_ON_AOD = faceAuthUiEvent4;
        FaceAuthUiEvent faceAuthUiEvent5 = new FaceAuthUiEvent("FACE_AUTH_TRIGGERED_FACE_LOCKOUT_RESET", 4, 1150, "Face auth due to face lockout reset.", i3, i4, defaultConstructorMarker2);
        FACE_AUTH_TRIGGERED_FACE_LOCKOUT_RESET = faceAuthUiEvent5;
        FaceAuthUiEvent faceAuthUiEvent6 = new FaceAuthUiEvent("FACE_AUTH_TRIGGERED_QS_EXPANDED", 5, 1151, "Face auth due to QS expansion.", 0, 4, null);
        FACE_AUTH_TRIGGERED_QS_EXPANDED = faceAuthUiEvent6;
        FaceAuthUiEvent faceAuthUiEvent7 = new FaceAuthUiEvent("FACE_AUTH_TRIGGERED_NOTIFICATION_PANEL_CLICKED", 6, 1152, "Face auth due to notification panel click.", i3, i4, defaultConstructorMarker2);
        FACE_AUTH_TRIGGERED_NOTIFICATION_PANEL_CLICKED = faceAuthUiEvent7;
        FaceAuthUiEvent faceAuthUiEvent8 = new FaceAuthUiEvent("FACE_AUTH_TRIGGERED_PICK_UP_GESTURE_TRIGGERED", 7, 1153, "Face auth due to pickup gesture triggered when the device is awake and not from AOD.", 0, 4, null);
        FACE_AUTH_TRIGGERED_PICK_UP_GESTURE_TRIGGERED = faceAuthUiEvent8;
        FaceAuthUiEvent faceAuthUiEvent9 = new FaceAuthUiEvent("FACE_AUTH_TRIGGERED_ALTERNATE_BIOMETRIC_BOUNCER_SHOWN", 8, 1154, "Face auth due to alternate bouncer shown.", 0, 4, null);
        FACE_AUTH_TRIGGERED_ALTERNATE_BIOMETRIC_BOUNCER_SHOWN = faceAuthUiEvent9;
        FaceAuthUiEvent faceAuthUiEvent10 = new FaceAuthUiEvent("FACE_AUTH_UPDATED_PRIMARY_BOUNCER_SHOWN", 9, 1155, "Face auth started/stopped due to primary bouncer shown.", 0, 4, null);
        FACE_AUTH_UPDATED_PRIMARY_BOUNCER_SHOWN = faceAuthUiEvent10;
        FaceAuthUiEvent faceAuthUiEvent11 = new FaceAuthUiEvent("FACE_AUTH_UPDATED_PRIMARY_BOUNCER_SHOWN_OR_WILL_BE_SHOWN", 10, 1197, "Face auth started/stopped due to bouncer being shown or will be shown.", 0, 4, null);
        FACE_AUTH_UPDATED_PRIMARY_BOUNCER_SHOWN_OR_WILL_BE_SHOWN = faceAuthUiEvent11;
        int i5 = 0;
        int i6 = 4;
        DefaultConstructorMarker defaultConstructorMarker3 = null;
        FaceAuthUiEvent faceAuthUiEvent12 = new FaceAuthUiEvent("FACE_AUTH_TRIGGERED_RETRY_AFTER_HW_UNAVAILABLE", 11, 1156, "Face auth due to retry after hardware unavailable.", i5, i6, defaultConstructorMarker3);
        FACE_AUTH_TRIGGERED_RETRY_AFTER_HW_UNAVAILABLE = faceAuthUiEvent12;
        FaceAuthUiEvent faceAuthUiEvent13 = new FaceAuthUiEvent("FACE_AUTH_TRIGGERED_TRUST_DISABLED", 12, 1158, "Face auth started due to trust disabled.", 0, 4, null);
        FACE_AUTH_TRIGGERED_TRUST_DISABLED = faceAuthUiEvent13;
        FaceAuthUiEvent faceAuthUiEvent14 = new FaceAuthUiEvent("FACE_AUTH_STOPPED_TRUST_ENABLED", 13, 1173, "Face auth stopped due to trust enabled.", i5, i6, defaultConstructorMarker3);
        FACE_AUTH_STOPPED_TRUST_ENABLED = faceAuthUiEvent14;
        FaceAuthUiEvent faceAuthUiEvent15 = new FaceAuthUiEvent("FACE_AUTH_UPDATED_KEYGUARD_OCCLUSION_CHANGED", 14, 1159, "Face auth started/stopped due to keyguard occlusion change.", 0, 4, null);
        FACE_AUTH_UPDATED_KEYGUARD_OCCLUSION_CHANGED = faceAuthUiEvent15;
        FaceAuthUiEvent faceAuthUiEvent16 = new FaceAuthUiEvent("FACE_AUTH_UPDATED_ASSISTANT_VISIBILITY_CHANGED", 15, 1160, "Face auth started/stopped due to assistant visibility change.", 0, 4, null);
        FACE_AUTH_UPDATED_ASSISTANT_VISIBILITY_CHANGED = faceAuthUiEvent16;
        FaceAuthUiEvent faceAuthUiEvent17 = new FaceAuthUiEvent("FACE_AUTH_UPDATED_STARTED_WAKING_UP", 16) { // from class: com.android.keyguard.FaceAuthUiEvent.FACE_AUTH_UPDATED_STARTED_WAKING_UP
            {
                int i7 = 1161;
                String str = "Face auth started/stopped due to device starting to wake up.";
                int i8 = 0;
                int i9 = 4;
                DefaultConstructorMarker defaultConstructorMarker4 = null;
            }

            @Override // com.android.keyguard.FaceAuthUiEvent
            public final String extraInfoToString() {
                return PowerManager.wakeReasonToString(getExtraInfo());
            }
        };
        FACE_AUTH_UPDATED_STARTED_WAKING_UP = faceAuthUiEvent17;
        FaceAuthUiEvent faceAuthUiEvent18 = new FaceAuthUiEvent("FACE_AUTH_UPDATED_POSTURE_CHANGED", 17, 1265, "Face auth started/stopped due to device posture changed.", 0, 4, null);
        FACE_AUTH_UPDATED_POSTURE_CHANGED = faceAuthUiEvent18;
        FaceAuthUiEvent faceAuthUiEvent19 = new FaceAuthUiEvent("FACE_AUTH_TRIGGERED_DREAM_STOPPED", 18, 1162, "Face auth due to dream stopped.", i5, i6, defaultConstructorMarker3);
        FaceAuthUiEvent faceAuthUiEvent20 = new FaceAuthUiEvent("FACE_AUTH_TRIGGERED_ALL_AUTHENTICATORS_REGISTERED", 19, 1163, "Face auth due to all authenticators registered.", 0, 4, null);
        FACE_AUTH_TRIGGERED_ALL_AUTHENTICATORS_REGISTERED = faceAuthUiEvent20;
        int i7 = 0;
        int i8 = 4;
        DefaultConstructorMarker defaultConstructorMarker4 = null;
        FaceAuthUiEvent faceAuthUiEvent21 = new FaceAuthUiEvent("FACE_AUTH_TRIGGERED_ENROLLMENTS_CHANGED", 20, 1164, "Face auth due to enrolments changed.", i7, i8, defaultConstructorMarker4);
        FACE_AUTH_TRIGGERED_ENROLLMENTS_CHANGED = faceAuthUiEvent21;
        FaceAuthUiEvent faceAuthUiEvent22 = new FaceAuthUiEvent("FACE_AUTH_UPDATED_KEYGUARD_VISIBILITY_CHANGED", 21, 1165, "Face auth stopped or started due to keyguard visibility changed.", 0, 4, null);
        FACE_AUTH_UPDATED_KEYGUARD_VISIBILITY_CHANGED = faceAuthUiEvent22;
        FaceAuthUiEvent faceAuthUiEvent23 = new FaceAuthUiEvent("FACE_AUTH_STOPPED_FACE_CANCEL_NOT_RECEIVED", 22, 1174, "Face auth stopped due to face cancel signal not received.", i7, i8, defaultConstructorMarker4);
        FACE_AUTH_STOPPED_FACE_CANCEL_NOT_RECEIVED = faceAuthUiEvent23;
        FaceAuthUiEvent faceAuthUiEvent24 = new FaceAuthUiEvent("FACE_AUTH_TRIGGERED_DURING_CANCELLATION", 23, 1175, "Another request to start face auth received while cancelling face auth", 0, 4, null);
        FACE_AUTH_TRIGGERED_DURING_CANCELLATION = faceAuthUiEvent24;
        FaceAuthUiEvent faceAuthUiEvent25 = new FaceAuthUiEvent("FACE_AUTH_STOPPED_DREAM_STARTED", 24, 1176, "Face auth stopped because dreaming started", i7, i8, defaultConstructorMarker4);
        FACE_AUTH_STOPPED_DREAM_STARTED = faceAuthUiEvent25;
        FaceAuthUiEvent faceAuthUiEvent26 = new FaceAuthUiEvent("FACE_AUTH_STOPPED_FP_LOCKED_OUT", 25, 1177, "Face auth stopped because fp locked out", 0, 4, null);
        FaceAuthUiEvent faceAuthUiEvent27 = new FaceAuthUiEvent("FACE_AUTH_STOPPED_USER_INPUT_ON_BOUNCER", 26, 1178, "Face auth stopped because user started typing password/pin", i, i2, defaultConstructorMarker);
        FACE_AUTH_STOPPED_USER_INPUT_ON_BOUNCER = faceAuthUiEvent27;
        FaceAuthUiEvent faceAuthUiEvent28 = new FaceAuthUiEvent("FACE_AUTH_STOPPED_KEYGUARD_GOING_AWAY", 27, 1179, "Face auth stopped because keyguard going away", 0, 4, null);
        FACE_AUTH_STOPPED_KEYGUARD_GOING_AWAY = faceAuthUiEvent28;
        int i9 = 0;
        int i10 = 4;
        DefaultConstructorMarker defaultConstructorMarker5 = null;
        int i11 = 0;
        int i12 = 4;
        DefaultConstructorMarker defaultConstructorMarker6 = null;
        FaceAuthUiEvent faceAuthUiEvent29 = new FaceAuthUiEvent("FACE_AUTH_UPDATED_CAMERA_LAUNCHED", 28, 1180, "Face auth started/stopped because camera launched", i11, i12, defaultConstructorMarker6);
        FACE_AUTH_UPDATED_CAMERA_LAUNCHED = faceAuthUiEvent29;
        FaceAuthUiEvent faceAuthUiEvent30 = new FaceAuthUiEvent("FACE_AUTH_UPDATED_FP_AUTHENTICATED", 29, 1181, "Face auth started/stopped because fingerprint launched", 0, 4, null);
        FACE_AUTH_UPDATED_FP_AUTHENTICATED = faceAuthUiEvent30;
        FaceAuthUiEvent faceAuthUiEvent31 = new FaceAuthUiEvent("FACE_AUTH_UPDATED_GOING_TO_SLEEP", 30, 1182, "Face auth started/stopped because going to sleep", i11, i12, defaultConstructorMarker6);
        FACE_AUTH_UPDATED_GOING_TO_SLEEP = faceAuthUiEvent31;
        FaceAuthUiEvent faceAuthUiEvent32 = new FaceAuthUiEvent("FACE_AUTH_STOPPED_FINISHED_GOING_TO_SLEEP", 31, 1183, "Face auth stopped because finished going to sleep", 0, 4, null);
        FACE_AUTH_STOPPED_FINISHED_GOING_TO_SLEEP = faceAuthUiEvent32;
        FaceAuthUiEvent faceAuthUiEvent33 = new FaceAuthUiEvent("FACE_AUTH_UPDATED_ON_KEYGUARD_INIT", 32, 1189, "Face auth started/stopped because Keyguard is initialized", i11, i12, defaultConstructorMarker6);
        FaceAuthUiEvent faceAuthUiEvent34 = new FaceAuthUiEvent("FACE_AUTH_UPDATED_KEYGUARD_RESET", 33, 1185, "Face auth started/stopped because Keyguard is reset", i, i2, defaultConstructorMarker);
        FACE_AUTH_UPDATED_KEYGUARD_RESET = faceAuthUiEvent34;
        FaceAuthUiEvent faceAuthUiEvent35 = new FaceAuthUiEvent("FACE_AUTH_UPDATED_USER_SWITCHING", 34, 1186, "Face auth started/stopped because user is switching", i9, i10, defaultConstructorMarker5);
        FACE_AUTH_UPDATED_USER_SWITCHING = faceAuthUiEvent35;
        FaceAuthUiEvent faceAuthUiEvent36 = new FaceAuthUiEvent("FACE_AUTH_UPDATED_ON_FACE_AUTHENTICATED", 35, 1187, "Face auth started/stopped because face is authenticated", 0, 4, null);
        FACE_AUTH_UPDATED_ON_FACE_AUTHENTICATED = faceAuthUiEvent36;
        int i13 = 0;
        int i14 = 4;
        DefaultConstructorMarker defaultConstructorMarker7 = null;
        FaceAuthUiEvent faceAuthUiEvent37 = new FaceAuthUiEvent("FACE_AUTH_UPDATED_BIOMETRIC_ENABLED_ON_KEYGUARD", 36, 1188, "Face auth started/stopped because biometric is enabled on keyguard", i13, i14, defaultConstructorMarker7);
        FACE_AUTH_UPDATED_BIOMETRIC_ENABLED_ON_KEYGUARD = faceAuthUiEvent37;
        int i15 = 0;
        int i16 = 4;
        DefaultConstructorMarker defaultConstructorMarker8 = null;
        FaceAuthUiEvent faceAuthUiEvent38 = new FaceAuthUiEvent("FACE_AUTH_UPDATED_STRONG_AUTH_CHANGED", 37, 1255, "Face auth stopped because strong auth allowed changed", i15, i16, defaultConstructorMarker8);
        FACE_AUTH_UPDATED_STRONG_AUTH_CHANGED = faceAuthUiEvent38;
        FaceAuthUiEvent faceAuthUiEvent39 = new FaceAuthUiEvent("FACE_AUTH_NON_STRONG_BIOMETRIC_ALLOWED_CHANGED", 38, 1256, "Face auth stopped because non strong biometric allowed changed", 0, 4, null);
        FACE_AUTH_NON_STRONG_BIOMETRIC_ALLOWED_CHANGED = faceAuthUiEvent39;
        FaceAuthUiEvent faceAuthUiEvent40 = new FaceAuthUiEvent("FACE_AUTH_TRIGGERED_RETRY_BUTTON_CLICKED", 39, VolteConstants.ErrorCode.UT_RETRY_TO_CDMA_DIAL, "Face auth triggered due to retry button click.", i15, i16, defaultConstructorMarker8);
        FACE_AUTH_TRIGGERED_RETRY_BUTTON_CLICKED = faceAuthUiEvent40;
        FaceAuthUiEvent faceAuthUiEvent41 = new FaceAuthUiEvent("FACE_AUTH_UPDATED_QS_FULLY_EXPANDED", 40, 5002, "Face auth because qs is fully expanded", 0, 4, null);
        FACE_AUTH_UPDATED_QS_FULLY_EXPANDED = faceAuthUiEvent41;
        FaceAuthUiEvent faceAuthUiEvent42 = new FaceAuthUiEvent("FACE_AUTH_UPDATED_LOCKOUT_DEADLINE", 41, 5003, "Face auth started/stopped because PPP lockout deadline is set", i15, i16, defaultConstructorMarker8);
        FACE_AUTH_UPDATED_LOCKOUT_DEADLINE = faceAuthUiEvent42;
        FaceAuthUiEvent faceAuthUiEvent43 = new FaceAuthUiEvent("FACE_AUTH_UPDATED_BIOMETRIC_LOCKOUT_DEADLINE", 42, 5004, "Face auth started/stopped because biometric lockout deadline is set", 0, 4, null);
        FACE_AUTH_UPDATED_BIOMETRIC_LOCKOUT_DEADLINE = faceAuthUiEvent43;
        FaceAuthUiEvent faceAuthUiEvent44 = new FaceAuthUiEvent("FACE_AUTH_UPDATED_COCKTAIL_BAR_SHOWING_CHANGED", 43, 5005, "Face auth started/stopped because cocktail bar showing state is changed", i13, i14, defaultConstructorMarker7);
        FACE_AUTH_UPDATED_COCKTAIL_BAR_SHOWING_CHANGED = faceAuthUiEvent44;
        FaceAuthUiEvent faceAuthUiEvent45 = new FaceAuthUiEvent("FACE_AUTH_UPDATED_SECURE_STATE_UNLOCK_CHANGED", 44, 5006, "Face auth started/stopped because secure state unlock is changed", i9, i10, defaultConstructorMarker5);
        FACE_AUTH_UPDATED_SECURE_STATE_UNLOCK_CHANGED = faceAuthUiEvent45;
        FaceAuthUiEvent faceAuthUiEvent46 = new FaceAuthUiEvent("FACE_AUTH_UPDATED_SUB_SCREEN", 45, 5007, "Face auth started/stopped because sub screen requests biometrics", i15, i16, defaultConstructorMarker8);
        FACE_AUTH_UPDATED_SUB_SCREEN = faceAuthUiEvent46;
        FaceAuthUiEvent faceAuthUiEvent47 = new FaceAuthUiEvent("FACE_AUTH_UPDATED_KEYGUARD_UNLOCKING", 46, 5008, "Face auth started/stopped because keyguard is unlocking", 0, 4, null);
        FACE_AUTH_UPDATED_KEYGUARD_UNLOCKING = faceAuthUiEvent47;
        int i17 = 0;
        int i18 = 4;
        DefaultConstructorMarker defaultConstructorMarker9 = null;
        FaceAuthUiEvent faceAuthUiEvent48 = new FaceAuthUiEvent("FACE_AUTH_STOPPED_SESSION_CLOSE", 47, 5009, "Face auth stopped because face manager session is closed", i17, i18, defaultConstructorMarker9);
        FACE_AUTH_STOPPED_SESSION_CLOSE = faceAuthUiEvent48;
        FaceAuthUiEvent faceAuthUiEvent49 = new FaceAuthUiEvent("FACE_AUTH_UPDATED_WINDOW_FOCUS_CHANGED", 48, 5010, "Face auth started/stopped because window focus is changed", 0, i10, defaultConstructorMarker5);
        FACE_AUTH_UPDATED_WINDOW_FOCUS_CHANGED = faceAuthUiEvent49;
        FaceAuthUiEvent faceAuthUiEvent50 = new FaceAuthUiEvent("FACE_AUTH_STOPPED_FACE_ERROR", 49, 5011, "Face auth stopped due to face recognition error", i17, i18, defaultConstructorMarker9);
        FACE_AUTH_STOPPED_FACE_ERROR = faceAuthUiEvent50;
        FaceAuthUiEvent faceAuthUiEvent51 = new FaceAuthUiEvent("FACE_AUTH_STOPPED_FACE_FAILED", 50, 5012, "Face auth stopped due to face recognition failed", 0, 4, null);
        FACE_AUTH_STOPPED_FACE_FAILED = faceAuthUiEvent51;
        FaceAuthUiEvent faceAuthUiEvent52 = new FaceAuthUiEvent("FACE_AUTH_UPDATED_COVER_STATE_CHANGED", 51, 5013, "Face auth started/stopped because cover state is changed", i17, i18, defaultConstructorMarker9);
        FACE_AUTH_UPDATED_COVER_STATE_CHANGED = faceAuthUiEvent52;
        FaceAuthUiEvent faceAuthUiEvent53 = new FaceAuthUiEvent("FACE_AUTH_UPDATED_FULL_SCREEN_FACE_WIDGET", 52, 5014, "Face auth started/stopped because full screen face widget is changed", 0, 4, null);
        FACE_AUTH_UPDATED_FULL_SCREEN_FACE_WIDGET = faceAuthUiEvent53;
        FaceAuthUiEvent faceAuthUiEvent54 = new FaceAuthUiEvent("FACE_AUTH_UPDATED_DYNAMIC_LOCK", 53, 5015, "Face auth started/stopped because dynamic lock screen is changed", 0, 4, null);
        FACE_AUTH_UPDATED_DYNAMIC_LOCK = faceAuthUiEvent54;
        FaceAuthUiEvent faceAuthUiEvent55 = new FaceAuthUiEvent("FACE_AUTH_UPDATED_NOTI_STAR_STATE_CHANGED", 54, 5016, "Face auth started/stopped because noti star state is changed", 0, 4, null);
        FACE_AUTH_UPDATED_NOTI_STAR_STATE_CHANGED = faceAuthUiEvent55;
        FaceAuthUiEvent faceAuthUiEvent56 = new FaceAuthUiEvent("FACE_AUTH_UPDATED_LOCK_ICON_PRESSED", 55, 5017, "Face auth started/stopped because lock icon is pressed", 0, 4, null);
        FACE_AUTH_UPDATED_LOCK_ICON_PRESSED = faceAuthUiEvent56;
        FaceAuthUiEvent faceAuthUiEvent57 = new FaceAuthUiEvent("FACE_AUTH_UPDATED_FOLDER_STATE_CHANGED", 56, 5018, "Face auth started/stopped because folder state is changed", 0, 4, null);
        FACE_AUTH_UPDATED_FOLDER_STATE_CHANGED = faceAuthUiEvent57;
        FaceAuthUiEvent faceAuthUiEvent58 = new FaceAuthUiEvent("FACE_AUTH_STOPPED_PREV_CREDENTIAL_VIEW", 57, 5019, "Face auth stopped because showing prev credential view is changed", 0, 4, null);
        FACE_AUTH_STOPPED_PREV_CREDENTIAL_VIEW = faceAuthUiEvent58;
        FaceAuthUiEvent faceAuthUiEvent59 = new FaceAuthUiEvent("FACE_AUTH_STARTED_LOCK_EDIT_MODE_FINISHED", 58, 5020, "Face auth started because lock edit mode is finished.", 0, 4, null);
        FACE_AUTH_STARTED_LOCK_EDIT_MODE_FINISHED = faceAuthUiEvent59;
        $VALUES = new FaceAuthUiEvent[]{faceAuthUiEvent, faceAuthUiEvent2, faceAuthUiEvent3, faceAuthUiEvent4, faceAuthUiEvent5, faceAuthUiEvent6, faceAuthUiEvent7, faceAuthUiEvent8, faceAuthUiEvent9, faceAuthUiEvent10, faceAuthUiEvent11, faceAuthUiEvent12, faceAuthUiEvent13, faceAuthUiEvent14, faceAuthUiEvent15, faceAuthUiEvent16, faceAuthUiEvent17, faceAuthUiEvent18, faceAuthUiEvent19, faceAuthUiEvent20, faceAuthUiEvent21, faceAuthUiEvent22, faceAuthUiEvent23, faceAuthUiEvent24, faceAuthUiEvent25, faceAuthUiEvent26, faceAuthUiEvent27, faceAuthUiEvent28, faceAuthUiEvent29, faceAuthUiEvent30, faceAuthUiEvent31, faceAuthUiEvent32, faceAuthUiEvent33, faceAuthUiEvent34, faceAuthUiEvent35, faceAuthUiEvent36, faceAuthUiEvent37, faceAuthUiEvent38, faceAuthUiEvent39, faceAuthUiEvent40, faceAuthUiEvent41, faceAuthUiEvent42, faceAuthUiEvent43, faceAuthUiEvent44, faceAuthUiEvent45, faceAuthUiEvent46, faceAuthUiEvent47, faceAuthUiEvent48, faceAuthUiEvent49, faceAuthUiEvent50, faceAuthUiEvent51, faceAuthUiEvent52, faceAuthUiEvent53, faceAuthUiEvent54, faceAuthUiEvent55, faceAuthUiEvent56, faceAuthUiEvent57, faceAuthUiEvent58, faceAuthUiEvent59};
    }

    private FaceAuthUiEvent(String str, int i, int i2, String str2, int i3) {
        this.id = i2;
        this.reason = str2;
        this.extraInfo = i3;
    }

    public static FaceAuthUiEvent valueOf(String str) {
        return (FaceAuthUiEvent) Enum.valueOf(FaceAuthUiEvent.class, str);
    }

    public static FaceAuthUiEvent[] values() {
        return (FaceAuthUiEvent[]) $VALUES.clone();
    }

    public String extraInfoToString() {
        return "";
    }

    public final int getExtraInfo() {
        return this.extraInfo;
    }

    public final int getId() {
        return this.id;
    }

    public final String getReason() {
        return this.reason;
    }

    public final void setExtraInfo(int i) {
        this.extraInfo = i;
    }

    public /* synthetic */ FaceAuthUiEvent(String str, int i, int i2, String str2, int i3, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, i, i2, str2, (i4 & 4) != 0 ? 0 : i3);
    }
}
