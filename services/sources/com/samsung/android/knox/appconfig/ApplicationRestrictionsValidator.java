package com.samsung.android.knox.appconfig;

import android.app.AppGlobals;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.Settings;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.pm.PackageManagerShellCommandDataLoader;
import com.samsung.android.gesture.SemMotionRecognitionManager;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.appconfig.info.KeyInfo;
import com.samsung.android.knox.appconfig.info.ResultInfo;
import com.samsung.android.knox.custom.utils.KnoxsdkFileLog;
import com.samsung.android.vibrator.VibRune;
import java.util.List;

/* loaded from: classes2.dex */
public class ApplicationRestrictionsValidator {
    public static final String TAG = "ApplicationRestrictionsValidator";
    public static final String[] unusedBundleKeys = {"wificonfiguration", "skip_welcome_screen", "flow_pointer_is_on_dex", "flow_pointer_from_where_dex", "app_config_skip_overscan", "app_config_hidden", "app_config_disable_ctx_menu", "app_config_disable_dex_labs_button", "app_config_disable_exit_dex_button", "dex_disable_file_copy_from_pc", "dex_disable_file_copy_from_mobile", "startActivity", "sendBroadcast"};

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to find 'out' block for switch in B:85:0x0aba. Please report as an issue. */
    public static Bundle validate(Context context, Bundle bundle) {
        char c;
        Bundle bundle2 = bundle;
        if (bundle2 == null) {
            return new Bundle();
        }
        String callerPackage = getCallerPackage(context);
        KeyInfo.KEY key = KeyInfo.KEY.NONE;
        Bundle bundle3 = new Bundle();
        for (String str : bundle.keySet()) {
            KeyInfo.KEY key2 = (KeyInfo.KEY) KeyInfo.KEYMAP.get(str);
            if (key2 != null) {
                int checkVersion = checkVersion(key2);
                if (ResultInfo.ERROR_NONE != checkVersion) {
                    bundle3.putInt(str, checkVersion);
                } else {
                    int checkPermission = checkPermission(context, callerPackage, key2);
                    if (ResultInfo.ERROR_NONE != checkPermission) {
                        bundle3.putInt(str, checkPermission);
                    } else if (checkWPCODMode(context) && ("location_settings".equals(str) || "top_level_location".equals(str) || "bluetooth_always_scanning".equals(str) || "wifi_always_scanning".equals(str) || "location_services_bluetooth_scanning".equals(str) || "location_services_wifi_scanning".equals(str))) {
                        bundle3.putInt(str, ResultInfo.ERROR_NOT_SUPPORTED);
                    } else if (!checkKeyType(str)) {
                        Bundle bundle4 = bundle2.getBundle(str);
                        if (bundle4 != null) {
                            if (!bundle4.isEmpty()) {
                                String string = bundle4.getString("value");
                                if (string != null) {
                                    if (!string.isEmpty()) {
                                        try {
                                            switch (str.hashCode()) {
                                                case -2136780833:
                                                    if (str.equals("screen_locking_sounds")) {
                                                        c = '1';
                                                        break;
                                                    }
                                                    break;
                                                case -2020611014:
                                                    if (str.equals("keyboard_vibration")) {
                                                        c = '.';
                                                        break;
                                                    }
                                                    break;
                                                case -1927858184:
                                                    if (str.equals("verizon_data_on_off")) {
                                                        c = 163;
                                                        break;
                                                    }
                                                    break;
                                                case -1894292218:
                                                    if (str.equals("sec_screen_size")) {
                                                        c = 25;
                                                        break;
                                                    }
                                                    break;
                                                case -1892429092:
                                                    if (str.equals("disableEmoticonInput")) {
                                                        c = 146;
                                                        break;
                                                    }
                                                    break;
                                                case -1885290769:
                                                    if (str.equals("accessibility_advanced_settings")) {
                                                        c = 159;
                                                        break;
                                                    }
                                                    break;
                                                case -1806682971:
                                                    if (str.equals("onehand_operation_settings")) {
                                                        c = 'u';
                                                        break;
                                                    }
                                                    break;
                                                case -1802964271:
                                                    if (str.equals("show_battery_percent")) {
                                                        c = '(';
                                                        break;
                                                    }
                                                    break;
                                                case -1796996731:
                                                    if (str.equals("notification_icons_only")) {
                                                        c = 'i';
                                                        break;
                                                    }
                                                    break;
                                                case -1780348439:
                                                    if (str.equals("top_level_accessibility")) {
                                                        c = 'Q';
                                                        break;
                                                    }
                                                    break;
                                                case -1772640019:
                                                    if (str.equals("telephonyui_access_point_names_menu")) {
                                                        c = 173;
                                                        break;
                                                    }
                                                    break;
                                                case -1731697920:
                                                    if (str.equals("disableToolbarMelon")) {
                                                        c = 158;
                                                        break;
                                                    }
                                                    break;
                                                case -1701915136:
                                                    if (str.equals("adaptive_brightness_no_ls")) {
                                                        c = 18;
                                                        break;
                                                    }
                                                    break;
                                                case -1678716405:
                                                    if (str.equals("facewidget_where_to_show")) {
                                                        c = 31;
                                                        break;
                                                    }
                                                    break;
                                                case -1675513663:
                                                    if (str.equals("domestic_roaming_voice_text")) {
                                                        c = 164;
                                                        break;
                                                    }
                                                    break;
                                                case -1655574749:
                                                    if (str.equals("external_storage_transfer")) {
                                                        c = 132;
                                                        break;
                                                    }
                                                    break;
                                                case -1528850031:
                                                    if (str.equals("startActivity")) {
                                                        c = 198;
                                                        break;
                                                    }
                                                    break;
                                                case -1490378172:
                                                    if (str.equals("touch_sounds")) {
                                                        c = '2';
                                                        break;
                                                    }
                                                    break;
                                                case -1489956946:
                                                    if (str.equals("telephonyui_domestic_roaming_voice_text")) {
                                                        c = 170;
                                                        break;
                                                    }
                                                    break;
                                                case -1485694789:
                                                    if (str.equals("notification_sound")) {
                                                        c = 'G';
                                                        break;
                                                    }
                                                    break;
                                                case -1464780595:
                                                    if (str.equals("key_notification_icons_on_status_bar")) {
                                                        c = 'h';
                                                        break;
                                                    }
                                                    break;
                                                case -1459426583:
                                                    if (str.equals("top_level_airplane_mode_upsm")) {
                                                        c = 'M';
                                                        break;
                                                    }
                                                    break;
                                                case -1437550723:
                                                    if (str.equals("dashboard_tile_pref_com.android.settings.Settings$DevelopmentSettingsDashboardActivity")) {
                                                        c = '|';
                                                        break;
                                                    }
                                                    break;
                                                case -1411698151:
                                                    if (str.equals("sendBroadcast")) {
                                                        c = 199;
                                                        break;
                                                    }
                                                    break;
                                                case -1392796800:
                                                    if (str.equals("noti_view_style")) {
                                                        c = '!';
                                                        break;
                                                    }
                                                    break;
                                                case -1383120273:
                                                    if (str.equals("sec_font_style")) {
                                                        c = 23;
                                                        break;
                                                    }
                                                    break;
                                                case -1346146434:
                                                    if (str.equals("active_key_on_lockscreen_key")) {
                                                        c = 'V';
                                                        break;
                                                    }
                                                    break;
                                                case -1337778787:
                                                    if (str.equals("show_password")) {
                                                        c = '*';
                                                        break;
                                                    }
                                                    break;
                                                case -1331687151:
                                                    if (str.equals("mobile_network_settings")) {
                                                        c = 138;
                                                        break;
                                                    }
                                                    break;
                                                case -1321667581:
                                                    if (str.equals("xcover_key_settings")) {
                                                        c = '^';
                                                        break;
                                                    }
                                                    break;
                                                case -1292529966:
                                                    if (str.equals("gesture_preview")) {
                                                        c = 's';
                                                        break;
                                                    }
                                                    break;
                                                case -1288522450:
                                                    if (str.equals("blue_light_filter_auto_schedule")) {
                                                        c = '?';
                                                        break;
                                                    }
                                                    break;
                                                case -1272944269:
                                                    if (str.equals("disableLiveMessage")) {
                                                        c = 150;
                                                        break;
                                                    }
                                                    break;
                                                case -1266806294:
                                                    if (str.equals("telephonyui_simcard_manager_more_settings_preference")) {
                                                        c = 183;
                                                        break;
                                                    }
                                                    break;
                                                case -1259116938:
                                                    if (str.equals("long_press_power_off")) {
                                                        c = 'd';
                                                        break;
                                                    }
                                                    break;
                                                case -1250129644:
                                                    if (str.equals("palm_swipe_to_capture")) {
                                                        c = 1;
                                                        break;
                                                    }
                                                    break;
                                                case -1244981252:
                                                    if (str.equals("current_input_method")) {
                                                        c = 'g';
                                                        break;
                                                    }
                                                    break;
                                                case -1231673117:
                                                    if (str.equals("android_beam_settings")) {
                                                        c = ';';
                                                        break;
                                                    }
                                                    break;
                                                case -1213951486:
                                                    if (str.equals("dex_disable_file_copy_from_pc")) {
                                                        c = 197;
                                                        break;
                                                    }
                                                    break;
                                                case -1207069265:
                                                    if (str.equals("vibrate_on_touch")) {
                                                        c = '4';
                                                        break;
                                                    }
                                                    break;
                                                case -1114732385:
                                                    if (str.equals("disableToolbarNetflix")) {
                                                        c = 157;
                                                        break;
                                                    }
                                                    break;
                                                case -1113710613:
                                                    if (str.equals("top_level_bluetooth_upsm")) {
                                                        c = 'L';
                                                        break;
                                                    }
                                                    break;
                                                case -1073571094:
                                                    if (str.equals("tts_default_rate")) {
                                                        c = ':';
                                                        break;
                                                    }
                                                    break;
                                                case -1057254394:
                                                    if (str.equals("outdoor_mode")) {
                                                        c = 19;
                                                        break;
                                                    }
                                                    break;
                                                case -1050227953:
                                                    if (str.equals("set_visibility")) {
                                                        c = ' ';
                                                        break;
                                                    }
                                                    break;
                                                case -1049616469:
                                                    if (str.equals("top_level_sounds_upsm")) {
                                                        c = 'O';
                                                        break;
                                                    }
                                                    break;
                                                case -1002669784:
                                                    if (str.equals("disableModes")) {
                                                        c = 153;
                                                        break;
                                                    }
                                                    break;
                                                case -1001942051:
                                                    if (str.equals("toggle_airplane")) {
                                                        c = 'z';
                                                        break;
                                                    }
                                                    break;
                                                case -992744142:
                                                    if (str.equals("app_config_skip_overscan")) {
                                                        c = 190;
                                                        break;
                                                    }
                                                    break;
                                                case -940730143:
                                                    if (str.equals("app_icon_dot")) {
                                                        c = 'D';
                                                        break;
                                                    }
                                                    break;
                                                case -893666734:
                                                    if (str.equals("phone_vibration_pattern")) {
                                                        c = 'p';
                                                        break;
                                                    }
                                                    break;
                                                case -827165234:
                                                    if (str.equals("homecity_timezone")) {
                                                        c = 28;
                                                        break;
                                                    }
                                                    break;
                                                case -825905508:
                                                    if (str.equals("top_level_lockscreen")) {
                                                        c = '{';
                                                        break;
                                                    }
                                                    break;
                                                case -796979427:
                                                    if (str.equals("double_press_open_bixby")) {
                                                        c = 'b';
                                                        break;
                                                    }
                                                    break;
                                                case -796109488:
                                                    if (str.equals("bluetooth_always_scanning")) {
                                                        c = 7;
                                                        break;
                                                    }
                                                    break;
                                                case -782541527:
                                                    if (str.equals("swipe_to_call_or_send_messages")) {
                                                        c = 5;
                                                        break;
                                                    }
                                                    break;
                                                case -762784073:
                                                    if (str.equals("keyboard_sound")) {
                                                        c = '-';
                                                        break;
                                                    }
                                                    break;
                                                case -738136912:
                                                    if (str.equals("eye_comfort_custom_mode")) {
                                                        c = 'm';
                                                        break;
                                                    }
                                                    break;
                                                case -734007736:
                                                    if (str.equals("disableSetting")) {
                                                        c = 143;
                                                        break;
                                                    }
                                                    break;
                                                case -728312859:
                                                    if (str.equals("telephonyui_verizon_data_on_off")) {
                                                        c = 169;
                                                        break;
                                                    }
                                                    break;
                                                case -722468721:
                                                    if (str.equals("notification_vibration_pattern")) {
                                                        c = 'q';
                                                        break;
                                                    }
                                                    break;
                                                case -716777878:
                                                    if (str.equals("sync_vibration_with_ringtone")) {
                                                        c = 136;
                                                        break;
                                                    }
                                                    break;
                                                case -701092546:
                                                    if (str.equals("disableAllToolbarItems")) {
                                                        c = 145;
                                                        break;
                                                    }
                                                    break;
                                                case -687279591:
                                                    if (str.equals("flow_pointer_is_on_dex")) {
                                                        c = 188;
                                                        break;
                                                    }
                                                    break;
                                                case -680098698:
                                                    if (str.equals("telephonyui_network_operator_menu")) {
                                                        c = 174;
                                                        break;
                                                    }
                                                    break;
                                                case -661464121:
                                                    if (str.equals("disableToolbarSpotify")) {
                                                        c = 155;
                                                        break;
                                                    }
                                                    break;
                                                case -612881513:
                                                    if (str.equals("blue_light_filter_turn_on_as_scheduled")) {
                                                        c = '>';
                                                        break;
                                                    }
                                                    break;
                                                case -583868981:
                                                    if (str.equals("reset_preference")) {
                                                        c = 127;
                                                        break;
                                                    }
                                                    break;
                                                case -561099790:
                                                    if (str.equals("accessibility_flash_notificaitons")) {
                                                        c = 161;
                                                        break;
                                                    }
                                                    break;
                                                case -532501408:
                                                    if (str.equals("short_press_key")) {
                                                        c = 'T';
                                                        break;
                                                    }
                                                    break;
                                                case -500279786:
                                                    if (str.equals("show_virtual_keyboard_switch")) {
                                                        c = 128;
                                                        break;
                                                    }
                                                    break;
                                                case -489405083:
                                                    if (str.equals("disableTextEditPanel")) {
                                                        c = 154;
                                                        break;
                                                    }
                                                    break;
                                                case -487132668:
                                                    if (str.equals("recent_notifications")) {
                                                        c = 'E';
                                                        break;
                                                    }
                                                    break;
                                                case -462237043:
                                                    if (str.equals("xcover_top_key_on_lockscreen_key")) {
                                                        c = 'Y';
                                                        break;
                                                    }
                                                    break;
                                                case -450351423:
                                                    if (str.equals("wifi_switch_for_individual_apps")) {
                                                        c = 11;
                                                        break;
                                                    }
                                                    break;
                                                case -420780531:
                                                    if (str.equals("wifi_settings")) {
                                                        c = 'x';
                                                        break;
                                                    }
                                                    break;
                                                case -412627900:
                                                    if (str.equals("location_services_wifi_scanning")) {
                                                        c = 135;
                                                        break;
                                                    }
                                                    break;
                                                case -405257407:
                                                    if (str.equals("wifi_poor_network_detection")) {
                                                        c = '\n';
                                                        break;
                                                    }
                                                    break;
                                                case -399222995:
                                                    if (str.equals("app_config_disable_ctx_menu")) {
                                                        c = 192;
                                                        break;
                                                    }
                                                    break;
                                                case -396251135:
                                                    if (str.equals("wificonfiguration")) {
                                                        c = 185;
                                                        break;
                                                    }
                                                    break;
                                                case -385517726:
                                                    if (str.equals("secbrightness")) {
                                                        c = 142;
                                                        break;
                                                    }
                                                    break;
                                                case -362486819:
                                                    if (str.equals("function_key_double_press_type")) {
                                                        c = '[';
                                                        break;
                                                    }
                                                    break;
                                                case -350802259:
                                                    if (str.equals("xcover_top_key_settings")) {
                                                        c = '_';
                                                        break;
                                                    }
                                                    break;
                                                case -338362191:
                                                    if (str.equals("app_icon_number")) {
                                                        c = '\'';
                                                        break;
                                                    }
                                                    break;
                                                case -315233995:
                                                    if (str.equals("disableSticker")) {
                                                        c = 147;
                                                        break;
                                                    }
                                                    break;
                                                case -310192179:
                                                    if (str.equals("telephonyui_simcard_manager_add_esim_preference")) {
                                                        c = 181;
                                                        break;
                                                    }
                                                    break;
                                                case -304857296:
                                                    if (str.equals("telephonyui_simcard_manager_text_preference")) {
                                                        c = 178;
                                                        break;
                                                    }
                                                    break;
                                                case -290284924:
                                                    if (str.equals("telephonyui_international_roaming_voice_text")) {
                                                        c = 172;
                                                        break;
                                                    }
                                                    break;
                                                case -240572106:
                                                    if (str.equals("blue_light_filter_off_time")) {
                                                        c = 'B';
                                                        break;
                                                    }
                                                    break;
                                                case -216034709:
                                                    if (str.equals("ds_notification_sound")) {
                                                        c = 'H';
                                                        break;
                                                    }
                                                    break;
                                                case -164739226:
                                                    if (str.equals("smart_alert")) {
                                                        c = 2;
                                                        break;
                                                    }
                                                    break;
                                                case -147951540:
                                                    if (str.equals("notification_badging")) {
                                                        c = '%';
                                                        break;
                                                    }
                                                    break;
                                                case -134203171:
                                                    if (str.equals("wifi_hs20_profile")) {
                                                        c = 14;
                                                        break;
                                                    }
                                                    break;
                                                case -127405033:
                                                    if (str.equals("MobileWIPS")) {
                                                        c = 15;
                                                        break;
                                                    }
                                                    break;
                                                case -124654970:
                                                    if (str.equals("blue_light_filter")) {
                                                        c = 21;
                                                        break;
                                                    }
                                                    break;
                                                case -96862508:
                                                    if (str.equals("proxy_settings")) {
                                                        c = 'w';
                                                        break;
                                                    }
                                                    break;
                                                case -92229716:
                                                    if (str.equals("tts_engine_preference")) {
                                                        c = '8';
                                                        break;
                                                    }
                                                    break;
                                                case -69276269:
                                                    if (str.equals("lock_screen_dualclock")) {
                                                        c = 27;
                                                        break;
                                                    }
                                                    break;
                                                case -59617644:
                                                    if (str.equals("bluetooth_settings")) {
                                                        c = 'y';
                                                        break;
                                                    }
                                                    break;
                                                case -49524087:
                                                    if (str.equals("function_key_setting")) {
                                                        c = '`';
                                                        break;
                                                    }
                                                    break;
                                                case -45000570:
                                                    if (str.equals("dial_pad_tones")) {
                                                        c = ',';
                                                        break;
                                                    }
                                                    break;
                                                case 6952340:
                                                    if (str.equals("telephonyui_international_roaming_data")) {
                                                        c = 168;
                                                        break;
                                                    }
                                                    break;
                                                case 11914836:
                                                    if (str.equals("verizon_dedicated_ptt")) {
                                                        c = 'S';
                                                        break;
                                                    }
                                                    break;
                                                case 19301783:
                                                    if (str.equals("disablePrediction")) {
                                                        c = 144;
                                                        break;
                                                    }
                                                    break;
                                                case 49358306:
                                                    if (str.equals("blue_light_filter_seekbar")) {
                                                        c = '<';
                                                        break;
                                                    }
                                                    break;
                                                case 52007263:
                                                    if (str.equals("telephonyui_simcard_manager_call_preference")) {
                                                        c = 177;
                                                        break;
                                                    }
                                                    break;
                                                case 152339455:
                                                    if (str.equals("increse_touch_sensetivity")) {
                                                        c = 'r';
                                                        break;
                                                    }
                                                    break;
                                                case 163703465:
                                                    if (str.equals("skip_welcome_screen")) {
                                                        c = 187;
                                                        break;
                                                    }
                                                    break;
                                                case 163880846:
                                                    if (str.equals("disableClipboard")) {
                                                        c = 152;
                                                        break;
                                                    }
                                                    break;
                                                case 171381576:
                                                    if (str.equals("navigation_Bar")) {
                                                        c = 'C';
                                                        break;
                                                    }
                                                    break;
                                                case 176165913:
                                                    if (str.equals("screen_off_pocket")) {
                                                        c = 20;
                                                        break;
                                                    }
                                                    break;
                                                case 213893897:
                                                    if (str.equals("phone_language")) {
                                                        c = '~';
                                                        break;
                                                    }
                                                    break;
                                                case 232467331:
                                                    if (str.equals("sec_font_size")) {
                                                        c = 22;
                                                        break;
                                                    }
                                                    break;
                                                case 245912486:
                                                    if (str.equals("accessibility_power_and_volume_up_keys")) {
                                                        c = 160;
                                                        break;
                                                    }
                                                    break;
                                                case 247426586:
                                                    if (str.equals("spen_detachment_sound")) {
                                                        c = 'v';
                                                        break;
                                                    }
                                                    break;
                                                case 277464755:
                                                    if (str.equals("eye_comfort_seekbar_color_temperature")) {
                                                        c = 'o';
                                                        break;
                                                    }
                                                    break;
                                                case 289966019:
                                                    if (str.equals("disableGifKeyboard")) {
                                                        c = 148;
                                                        break;
                                                    }
                                                    break;
                                                case 314433665:
                                                    if (str.equals("doemstic_roaming_data")) {
                                                        c = 165;
                                                        break;
                                                    }
                                                    break;
                                                case 346401221:
                                                    if (str.equals("picture_in_picture")) {
                                                        c = 'e';
                                                        break;
                                                    }
                                                    break;
                                                case 352248151:
                                                    if (str.equals("screenshots_and_screen_recorder")) {
                                                        c = 4;
                                                        break;
                                                    }
                                                    break;
                                                case 354990205:
                                                    if (str.equals("vibrate_when_ringing")) {
                                                        c = '6';
                                                        break;
                                                    }
                                                    break;
                                                case 373467976:
                                                    if (str.equals("show_notification_icons")) {
                                                        c = ')';
                                                        break;
                                                    }
                                                    break;
                                                case 392597729:
                                                    if (str.equals("auto_brightness")) {
                                                        c = 16;
                                                        break;
                                                    }
                                                    break;
                                                case 418800293:
                                                    if (str.equals("app_config_disable_exit_dex_button")) {
                                                        c = 194;
                                                        break;
                                                    }
                                                    break;
                                                case 471418703:
                                                    if (str.equals("disableHWRInput")) {
                                                        c = 151;
                                                        break;
                                                    }
                                                    break;
                                                case 595233003:
                                                    if (str.equals("notification")) {
                                                        c = '/';
                                                        break;
                                                    }
                                                    break;
                                                case 644413707:
                                                    if (str.equals("eye_comfort_adaptive_mode")) {
                                                        c = 'l';
                                                        break;
                                                    }
                                                    break;
                                                case 691482773:
                                                    if (str.equals("device_name_edit")) {
                                                        c = 137;
                                                        break;
                                                    }
                                                    break;
                                                case 702451240:
                                                    if (str.equals("dashboard_tile_pref_com.samsung.android.app.telephonyui.netsettings.ui.NetSettingsActivity")) {
                                                        c = 'R';
                                                        break;
                                                    }
                                                    break;
                                                case 735557889:
                                                    if (str.equals("phone_vibration")) {
                                                        c = '5';
                                                        break;
                                                    }
                                                    break;
                                                case 763955732:
                                                    if (str.equals("accessibility_installed_services")) {
                                                        c = 162;
                                                        break;
                                                    }
                                                    break;
                                                case 770600426:
                                                    if (str.equals("wifi_hs20_list")) {
                                                        c = 'I';
                                                        break;
                                                    }
                                                    break;
                                                case 783912215:
                                                    if (str.equals("category_samsungservices")) {
                                                        c = 130;
                                                        break;
                                                    }
                                                    break;
                                                case 788268510:
                                                    if (str.equals("notification_2")) {
                                                        c = '0';
                                                        break;
                                                    }
                                                    break;
                                                case 812225463:
                                                    if (str.equals("wifi_always_scanning")) {
                                                        c = '\b';
                                                        break;
                                                    }
                                                    break;
                                                case 860063886:
                                                    if (str.equals("screen_timeout")) {
                                                        c = 24;
                                                        break;
                                                    }
                                                    break;
                                                case 901111610:
                                                    if (str.equals("top_level_wifi_upsm")) {
                                                        c = 'K';
                                                        break;
                                                    }
                                                    break;
                                                case 904792806:
                                                    if (str.equals("gesture_detailed")) {
                                                        c = 't';
                                                        break;
                                                    }
                                                    break;
                                                case 931187994:
                                                    if (str.equals("top_level_location_upsm")) {
                                                        c = 'N';
                                                        break;
                                                    }
                                                    break;
                                                case 943572905:
                                                    if (str.equals("app_config_hidden")) {
                                                        c = 191;
                                                        break;
                                                    }
                                                    break;
                                                case 947064463:
                                                    if (str.equals("resolution_user_setting")) {
                                                        c = 195;
                                                        break;
                                                    }
                                                    break;
                                                case 967563987:
                                                    if (str.equals("sec_high_refresh_rate")) {
                                                        c = 141;
                                                        break;
                                                    }
                                                    break;
                                                case 1046707316:
                                                    if (str.equals("blue_light_filter_on_time")) {
                                                        c = 'A';
                                                        break;
                                                    }
                                                    break;
                                                case 1077425782:
                                                    if (str.equals("tts_default_pitch")) {
                                                        c = '9';
                                                        break;
                                                    }
                                                    break;
                                                case 1090825909:
                                                    if (str.equals("eye_comfort_set_schedule")) {
                                                        c = 'n';
                                                        break;
                                                    }
                                                    break;
                                                case 1112368493:
                                                    if (str.equals("app_config_disable_dex_labs_button")) {
                                                        c = 193;
                                                        break;
                                                    }
                                                    break;
                                                case 1131282994:
                                                    if (str.equals("auto_brightness_no_pac")) {
                                                        c = 17;
                                                        break;
                                                    }
                                                    break;
                                                case 1149978917:
                                                    if (str.equals("notification_content")) {
                                                        c = 140;
                                                        break;
                                                    }
                                                    break;
                                                case 1192174138:
                                                    if (str.equals("top_level_location")) {
                                                        c = 'J';
                                                        break;
                                                    }
                                                    break;
                                                case 1202202989:
                                                    if (str.equals("location_settings")) {
                                                        c = 6;
                                                        break;
                                                    }
                                                    break;
                                                case 1203272325:
                                                    if (str.equals("noti_inverse_text")) {
                                                        c = '$';
                                                        break;
                                                    }
                                                    break;
                                                case 1213719336:
                                                    if (str.equals("key_show_keyboard_button")) {
                                                        c = '7';
                                                        break;
                                                    }
                                                    break;
                                                case 1241904703:
                                                    if (str.equals("blue_light_filter_turn_on_now")) {
                                                        c = '=';
                                                        break;
                                                    }
                                                    break;
                                                case 1256224049:
                                                    if (str.equals("dex_disable_file_copy_from_mobile")) {
                                                        c = 196;
                                                        break;
                                                    }
                                                    break;
                                                case 1287579369:
                                                    if (str.equals("where_to_show")) {
                                                        c = 29;
                                                        break;
                                                    }
                                                    break;
                                                case 1288594433:
                                                    if (str.equals("button_order")) {
                                                        c = 26;
                                                        break;
                                                    }
                                                    break;
                                                case 1337014256:
                                                    if (str.equals("default_autofill")) {
                                                        c = 129;
                                                        break;
                                                    }
                                                    break;
                                                case 1350624969:
                                                    if (str.equals("all_notification")) {
                                                        c = 'F';
                                                        break;
                                                    }
                                                    break;
                                                case 1390950212:
                                                    if (str.equals("telephonyui_simcard_manager_data_switching_preference")) {
                                                        c = 182;
                                                        break;
                                                    }
                                                    break;
                                                case 1400622160:
                                                    if (str.equals("wifi_adps")) {
                                                        c = '\r';
                                                        break;
                                                    }
                                                    break;
                                                case 1440318162:
                                                    if (str.equals("charging_sounds")) {
                                                        c = '+';
                                                        break;
                                                    }
                                                    break;
                                                case 1459847093:
                                                    if (str.equals("restriction_app_suggestion")) {
                                                        c = 184;
                                                        break;
                                                    }
                                                    break;
                                                case 1510242103:
                                                    if (str.equals("disable_connectivity_check")) {
                                                        c = 186;
                                                        break;
                                                    }
                                                    break;
                                                case 1524832700:
                                                    if (str.equals("function_key_double_press")) {
                                                        c = 'Z';
                                                        break;
                                                    }
                                                    break;
                                                case 1539072733:
                                                    if (str.equals("flow_pointer_from_where_dex")) {
                                                        c = 189;
                                                        break;
                                                    }
                                                    break;
                                                case 1539665504:
                                                    if (str.equals("long_press_key")) {
                                                        c = 'U';
                                                        break;
                                                    }
                                                    break;
                                                case 1541216154:
                                                    if (str.equals("telephonyui_simcard_manager_general_settings_esim")) {
                                                        c = 180;
                                                        break;
                                                    }
                                                    break;
                                                case 1541623682:
                                                    if (str.equals("telephonyui_simcard_manager_general_settings_sim1")) {
                                                        c = 179;
                                                        break;
                                                    }
                                                    break;
                                                case 1541623683:
                                                    if (str.equals("telephonyui_simcard_manager_general_settings_sim2")) {
                                                        c = 175;
                                                        break;
                                                    }
                                                    break;
                                                case 1566068391:
                                                    if (str.equals("lock_screen_menu_notifications")) {
                                                        c = '\"';
                                                        break;
                                                    }
                                                    break;
                                                case 1590945141:
                                                    if (str.equals("double_press_quick_launch_camera")) {
                                                        c = 'a';
                                                        break;
                                                    }
                                                    break;
                                                case 1617687232:
                                                    if (str.equals("disableVoiceInput")) {
                                                        c = 149;
                                                        break;
                                                    }
                                                    break;
                                                case 1648397374:
                                                    if (str.equals("notification_vibration")) {
                                                        c = 'k';
                                                        break;
                                                    }
                                                    break;
                                                case 1661386661:
                                                    if (str.equals("auto_wifi")) {
                                                        c = '\f';
                                                        break;
                                                    }
                                                    break;
                                                case 1663534625:
                                                    if (str.equals("international_roaming_data")) {
                                                        c = 167;
                                                        break;
                                                    }
                                                    break;
                                                case 1677906651:
                                                    if (str.equals("backup_category")) {
                                                        c = 131;
                                                        break;
                                                    }
                                                    break;
                                                case 1680822134:
                                                    if (str.equals("xcover_top_short_press_key")) {
                                                        c = 'W';
                                                        break;
                                                    }
                                                    break;
                                                case 1701364505:
                                                    if (str.equals("location_services_bluetooth_scanning")) {
                                                        c = 134;
                                                        break;
                                                    }
                                                    break;
                                                case 1710442968:
                                                    if (str.equals("volume_key_control")) {
                                                        c = '3';
                                                        break;
                                                    }
                                                    break;
                                                case 1714349619:
                                                    if (str.equals("homescreen_noti_preview")) {
                                                        c = PackageManagerShellCommandDataLoader.ARGS_DELIM;
                                                        break;
                                                    }
                                                    break;
                                                case 1715154558:
                                                    if (str.equals("change_language_shortcut")) {
                                                        c = 'f';
                                                        break;
                                                    }
                                                    break;
                                                case 1756169966:
                                                    if (str.equals("notification_details")) {
                                                        c = 'j';
                                                        break;
                                                    }
                                                    break;
                                                case 1770112658:
                                                    if (str.equals("blue_light_filter_user_schedule")) {
                                                        c = '@';
                                                        break;
                                                    }
                                                    break;
                                                case 1774003687:
                                                    if (str.equals("function_key_long_press")) {
                                                        c = ']';
                                                        break;
                                                    }
                                                    break;
                                                case 1775383005:
                                                    if (str.equals("double_press_open_apps")) {
                                                        c = '\\';
                                                        break;
                                                    }
                                                    break;
                                                case 1818930513:
                                                    if (str.equals("international_roaming_voice_text")) {
                                                        c = 166;
                                                        break;
                                                    }
                                                    break;
                                                case 1841849239:
                                                    if (str.equals("top_level_display_upsm")) {
                                                        c = 'P';
                                                        break;
                                                    }
                                                    break;
                                                case 1848989559:
                                                    if (str.equals("top_level_apps")) {
                                                        c = '}';
                                                        break;
                                                    }
                                                    break;
                                                case 1864459797:
                                                    if (str.equals("noti_card_seekbar")) {
                                                        c = '#';
                                                        break;
                                                    }
                                                    break;
                                                case 1884978176:
                                                    if (str.equals("lockscreen_notifications")) {
                                                        c = 139;
                                                        break;
                                                    }
                                                    break;
                                                case 1923536950:
                                                    if (str.equals("easy_mute")) {
                                                        c = 0;
                                                        break;
                                                    }
                                                    break;
                                                case 1934515591:
                                                    if (str.equals("lock_screen_additional_info")) {
                                                        c = 30;
                                                        break;
                                                    }
                                                    break;
                                                case 1958933559:
                                                    if (str.equals("nfc_settings")) {
                                                        c = '\t';
                                                        break;
                                                    }
                                                    break;
                                                case 2026255662:
                                                    if (str.equals("telephonyui_doemstic_roaming_data")) {
                                                        c = 171;
                                                        break;
                                                    }
                                                    break;
                                                case 2026705034:
                                                    if (str.equals("xcover_top_long_press_key")) {
                                                        c = 'X';
                                                        break;
                                                    }
                                                    break;
                                                case 2069703376:
                                                    if (str.equals("smart_capture")) {
                                                        c = 3;
                                                        break;
                                                    }
                                                    break;
                                                case 2087392403:
                                                    if (str.equals("telephonyui_simcard_manager_data_preference")) {
                                                        c = 176;
                                                        break;
                                                    }
                                                    break;
                                                case 2095779756:
                                                    if (str.equals("long_press_wake_bixby")) {
                                                        c = 'c';
                                                        break;
                                                    }
                                                    break;
                                                case 2121000432:
                                                    if (str.equals("disableToolbarYoutube ")) {
                                                        c = 156;
                                                        break;
                                                    }
                                                    break;
                                                case 2143739896:
                                                    if (str.equals("wifi_qrcode")) {
                                                        c = 133;
                                                        break;
                                                    }
                                                    break;
                                            }
                                            c = 65535;
                                            if (c != 0) {
                                                if (c != 1) {
                                                    if (c != 2) {
                                                        if (c != 3) {
                                                            if (c != 4) {
                                                                if (c == 5) {
                                                                    bundle3.putInt(str, ResultInfo.ERROR_NOT_SUPPORTED);
                                                                } else if (c == 6) {
                                                                    if (!"3".equals(string) && !"2".equals(string) && !"1".equals(string) && !"0".equals(string)) {
                                                                        bundle3.putInt(str, ResultInfo.ERROR_INVALID_VALUE);
                                                                    }
                                                                } else {
                                                                    if (c != 7) {
                                                                        if (c != '6') {
                                                                            if (c != '7') {
                                                                                if (c != '9') {
                                                                                    if (c != ':') {
                                                                                        if (c != 'I') {
                                                                                            if (c != 'J') {
                                                                                                if (c != 'r') {
                                                                                                    if (c != 's') {
                                                                                                        if (c != 'u') {
                                                                                                            if (c != 'v') {
                                                                                                                switch (c) {
                                                                                                                    case 7:
                                                                                                                        break;
                                                                                                                    case '\b':
                                                                                                                        if (!"1".equals(string) && !"0".equals(string)) {
                                                                                                                            bundle3.putInt(str, ResultInfo.ERROR_INVALID_VALUE);
                                                                                                                            break;
                                                                                                                        }
                                                                                                                        break;
                                                                                                                    case '\t':
                                                                                                                        if (!"1".equals(string) && !"0".equals(string)) {
                                                                                                                            bundle3.putInt(str, ResultInfo.ERROR_INVALID_VALUE);
                                                                                                                            break;
                                                                                                                        }
                                                                                                                        break;
                                                                                                                    case '\n':
                                                                                                                        if (!"3".equals(string) && !"2".equals(string)) {
                                                                                                                            bundle3.putInt(str, ResultInfo.ERROR_INVALID_VALUE);
                                                                                                                            break;
                                                                                                                        }
                                                                                                                        break;
                                                                                                                    case 11:
                                                                                                                        if (!"1".equals(string) && !"0".equals(string)) {
                                                                                                                            bundle3.putInt(str, ResultInfo.ERROR_INVALID_VALUE);
                                                                                                                            break;
                                                                                                                        }
                                                                                                                        break;
                                                                                                                    case '\f':
                                                                                                                        if (!"1".equals(string) && !"0".equals(string)) {
                                                                                                                            bundle3.putInt(str, ResultInfo.ERROR_INVALID_VALUE);
                                                                                                                            break;
                                                                                                                        }
                                                                                                                        break;
                                                                                                                    case '\r':
                                                                                                                        if (!"1".equals(string) && !"0".equals(string)) {
                                                                                                                            bundle3.putInt(str, ResultInfo.ERROR_INVALID_VALUE);
                                                                                                                            break;
                                                                                                                        }
                                                                                                                        break;
                                                                                                                    case 14:
                                                                                                                        if (!"1".equals(string) && !"0".equals(string)) {
                                                                                                                            bundle3.putInt(str, ResultInfo.ERROR_INVALID_VALUE);
                                                                                                                            break;
                                                                                                                        }
                                                                                                                        break;
                                                                                                                    case 15:
                                                                                                                        if (!"1".equals(string) && !"0".equals(string)) {
                                                                                                                            bundle3.putInt(str, ResultInfo.ERROR_INVALID_VALUE);
                                                                                                                            break;
                                                                                                                        }
                                                                                                                        break;
                                                                                                                    case 16:
                                                                                                                        if (!supportAutoBrightness(context)) {
                                                                                                                            bundle3.putInt(str, ResultInfo.ERROR_NOT_SUPPORTED);
                                                                                                                        }
                                                                                                                        if (!"1".equals(string) && !"0".equals(string)) {
                                                                                                                            bundle3.putInt(str, ResultInfo.ERROR_INVALID_VALUE);
                                                                                                                        }
                                                                                                                        if (bundle4.containsKey("level")) {
                                                                                                                            long clearCallingIdentity = Binder.clearCallingIdentity();
                                                                                                                            try {
                                                                                                                                try {
                                                                                                                                    Settings.System.putInt(context.getContentResolver(), "screen_brightness", Integer.parseInt(bundle4.getString("level")));
                                                                                                                                } catch (Exception e) {
                                                                                                                                    KnoxsdkFileLog.e(TAG, "auto brightness level fail :  " + e);
                                                                                                                                }
                                                                                                                                Binder.restoreCallingIdentity(clearCallingIdentity);
                                                                                                                                break;
                                                                                                                            } catch (Throwable th) {
                                                                                                                                Binder.restoreCallingIdentity(clearCallingIdentity);
                                                                                                                                throw th;
                                                                                                                            }
                                                                                                                        }
                                                                                                                        break;
                                                                                                                    case 17:
                                                                                                                        if (!"1".equals(string) && !"0".equals(string)) {
                                                                                                                            bundle3.putInt(str, ResultInfo.ERROR_INVALID_VALUE);
                                                                                                                            break;
                                                                                                                        }
                                                                                                                        break;
                                                                                                                    case 18:
                                                                                                                        if (!"1".equals(string) && !"0".equals(string)) {
                                                                                                                            bundle3.putInt(str, ResultInfo.ERROR_INVALID_VALUE);
                                                                                                                            break;
                                                                                                                        }
                                                                                                                        break;
                                                                                                                    case 19:
                                                                                                                        if (!"1".equals(string) && !"0".equals(string)) {
                                                                                                                            bundle3.putInt(str, ResultInfo.ERROR_INVALID_VALUE);
                                                                                                                            break;
                                                                                                                        }
                                                                                                                        break;
                                                                                                                    case 20:
                                                                                                                        if (!supportPocketMode(context)) {
                                                                                                                            bundle3.putInt(str, ResultInfo.ERROR_NOT_SUPPORTED);
                                                                                                                        }
                                                                                                                        if (!"1".equals(string) && !"0".equals(string)) {
                                                                                                                            bundle3.putInt(str, ResultInfo.ERROR_INVALID_VALUE);
                                                                                                                            break;
                                                                                                                        }
                                                                                                                        break;
                                                                                                                    case 21:
                                                                                                                        if (!"1".equals(string) && !"0".equals(string)) {
                                                                                                                            bundle3.putInt(str, ResultInfo.ERROR_INVALID_VALUE);
                                                                                                                            break;
                                                                                                                        }
                                                                                                                        break;
                                                                                                                    case 29:
                                                                                                                        if (!"1".equals(string) && !"0".equals(string)) {
                                                                                                                            bundle3.putInt(str, ResultInfo.ERROR_INVALID_VALUE);
                                                                                                                            break;
                                                                                                                        }
                                                                                                                        break;
                                                                                                                    case 'S':
                                                                                                                        if (!"1".equals(string) && !"0".equals(string)) {
                                                                                                                            bundle3.putInt(str, ResultInfo.ERROR_INVALID_VALUE);
                                                                                                                            break;
                                                                                                                        }
                                                                                                                        break;
                                                                                                                    case 'V':
                                                                                                                        if (!"1".equals(string) && !"0".equals(string)) {
                                                                                                                            bundle3.putInt(str, ResultInfo.ERROR_INVALID_VALUE);
                                                                                                                            break;
                                                                                                                        }
                                                                                                                        break;
                                                                                                                    case ']':
                                                                                                                        if (!"2".equals(string) && !"1".equals(string) && !"0".equals(string)) {
                                                                                                                            bundle3.putInt(str, ResultInfo.ERROR_INVALID_VALUE);
                                                                                                                            break;
                                                                                                                        }
                                                                                                                        break;
                                                                                                                    case 'f':
                                                                                                                        if (!"7".equals(string) && !"6".equals(string) && !"5".equals(string) && !"4".equals(string) && !"3".equals(string) && !"2".equals(string) && !"1".equals(string)) {
                                                                                                                            bundle3.putInt(str, ResultInfo.ERROR_INVALID_VALUE);
                                                                                                                            break;
                                                                                                                        }
                                                                                                                        break;
                                                                                                                    case 'h':
                                                                                                                        if (!"3".equals(string) && !"2".equals(string) && !"1".equals(string) && !"0".equals(string)) {
                                                                                                                            bundle3.putInt(str, ResultInfo.ERROR_INVALID_VALUE);
                                                                                                                            break;
                                                                                                                        }
                                                                                                                        break;
                                                                                                                    case 128:
                                                                                                                        if (!"1".equals(string) && !"0".equals(string)) {
                                                                                                                            bundle3.putInt(str, ResultInfo.ERROR_INVALID_VALUE);
                                                                                                                            break;
                                                                                                                        }
                                                                                                                        break;
                                                                                                                    case 134:
                                                                                                                        if (!"1".equals(string) && !"0".equals(string)) {
                                                                                                                            bundle3.putInt(str, ResultInfo.ERROR_INVALID_VALUE);
                                                                                                                            break;
                                                                                                                        }
                                                                                                                        break;
                                                                                                                    case 135:
                                                                                                                        if (!"1".equals(string) && !"0".equals(string)) {
                                                                                                                            bundle3.putInt(str, ResultInfo.ERROR_INVALID_VALUE);
                                                                                                                            break;
                                                                                                                        }
                                                                                                                        break;
                                                                                                                    case 136:
                                                                                                                        if (!"1".equals(string) && !"0".equals(string)) {
                                                                                                                            bundle3.putInt(str, ResultInfo.ERROR_INVALID_VALUE);
                                                                                                                            break;
                                                                                                                        }
                                                                                                                        break;
                                                                                                                    case 139:
                                                                                                                        if (!"1".equals(string) && !"0".equals(string)) {
                                                                                                                            bundle3.putInt(str, ResultInfo.ERROR_INVALID_VALUE);
                                                                                                                            break;
                                                                                                                        }
                                                                                                                        break;
                                                                                                                    case 140:
                                                                                                                        if (!"1".equals(string) && !"0".equals(string)) {
                                                                                                                            bundle3.putInt(str, ResultInfo.ERROR_INVALID_VALUE);
                                                                                                                            break;
                                                                                                                        }
                                                                                                                        break;
                                                                                                                    case 141:
                                                                                                                        if (!"1".equals(string) && !"0".equals(string) && !"2".equals(string)) {
                                                                                                                            bundle3.putInt(str, ResultInfo.ERROR_INVALID_VALUE);
                                                                                                                            break;
                                                                                                                        }
                                                                                                                        break;
                                                                                                                    case FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__CROSS_PROFILE_SETTINGS_PAGE_LAUNCHED_FROM_SETTINGS /* 163 */:
                                                                                                                        if (!"1".equals(string) && !"0".equals(string)) {
                                                                                                                            bundle3.putInt(str, ResultInfo.ERROR_INVALID_VALUE);
                                                                                                                            break;
                                                                                                                        }
                                                                                                                        break;
                                                                                                                    case FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__CROSS_PROFILE_SETTINGS_PAGE_ADMIN_RESTRICTED /* 164 */:
                                                                                                                        if (!"1".equals(string) && !"0".equals(string)) {
                                                                                                                            bundle3.putInt(str, ResultInfo.ERROR_INVALID_VALUE);
                                                                                                                            break;
                                                                                                                        }
                                                                                                                        break;
                                                                                                                    case FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__CROSS_PROFILE_SETTINGS_PAGE_MISSING_WORK_APP /* 165 */:
                                                                                                                        if (!"1".equals(string) && !"0".equals(string)) {
                                                                                                                            bundle3.putInt(str, ResultInfo.ERROR_INVALID_VALUE);
                                                                                                                            break;
                                                                                                                        }
                                                                                                                        break;
                                                                                                                    case FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__CROSS_PROFILE_SETTINGS_PAGE_MISSING_PERSONAL_APP /* 166 */:
                                                                                                                        if (!"1".equals(string) && !"0".equals(string)) {
                                                                                                                            bundle3.putInt(str, ResultInfo.ERROR_INVALID_VALUE);
                                                                                                                            break;
                                                                                                                        }
                                                                                                                        break;
                                                                                                                    case FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__CROSS_PROFILE_SETTINGS_PAGE_MISSING_INSTALL_BANNER_INTENT /* 167 */:
                                                                                                                        if (!"1".equals(string) && !"0".equals(string)) {
                                                                                                                            bundle3.putInt(str, ResultInfo.ERROR_INVALID_VALUE);
                                                                                                                            break;
                                                                                                                        }
                                                                                                                        break;
                                                                                                                    case 168:
                                                                                                                        if (!"1".equals(string) && !"0".equals(string)) {
                                                                                                                            bundle3.putInt(str, ResultInfo.ERROR_INVALID_VALUE);
                                                                                                                            break;
                                                                                                                        }
                                                                                                                        break;
                                                                                                                    case 169:
                                                                                                                        if (!"1".equals(string) && !"0".equals(string)) {
                                                                                                                            bundle3.putInt(str, ResultInfo.ERROR_INVALID_VALUE);
                                                                                                                            break;
                                                                                                                        }
                                                                                                                        break;
                                                                                                                    case 170:
                                                                                                                        if (!"1".equals(string) && !"0".equals(string)) {
                                                                                                                            bundle3.putInt(str, ResultInfo.ERROR_INVALID_VALUE);
                                                                                                                            break;
                                                                                                                        }
                                                                                                                        break;
                                                                                                                    case FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__CROSS_PROFILE_SETTINGS_PAGE_USER_DECLINED_CONSENT /* 171 */:
                                                                                                                        if (!"1".equals(string) && !"0".equals(string)) {
                                                                                                                            bundle3.putInt(str, ResultInfo.ERROR_INVALID_VALUE);
                                                                                                                            break;
                                                                                                                        }
                                                                                                                        break;
                                                                                                                    case FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__CROSS_PROFILE_SETTINGS_PAGE_PERMISSION_REVOKED /* 172 */:
                                                                                                                        if (!"1".equals(string) && !"0".equals(string)) {
                                                                                                                            bundle3.putInt(str, ResultInfo.ERROR_INVALID_VALUE);
                                                                                                                            break;
                                                                                                                        }
                                                                                                                        break;
                                                                                                                    case FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__DOCSUI_LAUNCH_OTHER_APP /* 175 */:
                                                                                                                        bundle3.putInt(str, ResultInfo.ERROR_NOT_SUPPORTED);
                                                                                                                        break;
                                                                                                                    case 180:
                                                                                                                        if (!"0".equals(string)) {
                                                                                                                            bundle3.putInt(str, ResultInfo.ERROR_INVALID_VALUE);
                                                                                                                            break;
                                                                                                                        }
                                                                                                                        break;
                                                                                                                    case 182:
                                                                                                                        if (!"1".equals(string) && !"0".equals(string)) {
                                                                                                                            bundle3.putInt(str, ResultInfo.ERROR_INVALID_VALUE);
                                                                                                                        }
                                                                                                                        long clearCallingIdentity2 = Binder.clearCallingIdentity();
                                                                                                                        try {
                                                                                                                            try {
                                                                                                                                Settings.Secure.putInt(context.getContentResolver(), "data_preferred_mode_during_calling", Integer.parseInt(string));
                                                                                                                            } catch (Exception e2) {
                                                                                                                                KnoxsdkFileLog.e(TAG, "auto brightness level fail :  " + e2);
                                                                                                                            }
                                                                                                                            Binder.restoreCallingIdentity(clearCallingIdentity2);
                                                                                                                            break;
                                                                                                                        } catch (Throwable th2) {
                                                                                                                            Binder.restoreCallingIdentity(clearCallingIdentity2);
                                                                                                                            throw th2;
                                                                                                                        }
                                                                                                                    case 184:
                                                                                                                        if (!"1".equals(string) && !"0".equals(string)) {
                                                                                                                            bundle3.putInt(str, ResultInfo.ERROR_INVALID_VALUE);
                                                                                                                            break;
                                                                                                                        }
                                                                                                                        break;
                                                                                                                    case 186:
                                                                                                                        if (!"1".equals(string) && !"0".equals(string)) {
                                                                                                                            bundle3.putInt(str, ResultInfo.ERROR_INVALID_VALUE);
                                                                                                                            break;
                                                                                                                        }
                                                                                                                        break;
                                                                                                                    case 195:
                                                                                                                        if (!"UWQHD".equals(string) && !"WQXGA".equals(string) && !"WQHD".equals(string) && !"UWFHD".equals(string) && !"WUXGA".equals(string) && !"FHD".equals(string) && !"HD".equals(string) && !"null".equals(string)) {
                                                                                                                            bundle3.putInt(str, ResultInfo.ERROR_INVALID_VALUE);
                                                                                                                            break;
                                                                                                                        }
                                                                                                                        break;
                                                                                                                    default:
                                                                                                                        switch (c) {
                                                                                                                            case 25:
                                                                                                                                if (Integer.parseInt(string) < 0 || Integer.parseInt(string) > 4) {
                                                                                                                                    bundle3.putInt(str, ResultInfo.ERROR_INVALID_VALUE);
                                                                                                                                    break;
                                                                                                                                }
                                                                                                                                break;
                                                                                                                            case 26:
                                                                                                                                if (!"1".equals(string) && !"0".equals(string)) {
                                                                                                                                    bundle3.putInt(str, ResultInfo.ERROR_INVALID_VALUE);
                                                                                                                                    break;
                                                                                                                                }
                                                                                                                                break;
                                                                                                                            case 27:
                                                                                                                                if (!"1".equals(string) && !"0".equals(string)) {
                                                                                                                                    bundle3.putInt(str, ResultInfo.ERROR_INVALID_VALUE);
                                                                                                                                    break;
                                                                                                                                }
                                                                                                                                break;
                                                                                                                            default:
                                                                                                                                switch (c) {
                                                                                                                                    case 31:
                                                                                                                                        if (!"1".equals(string) && !"0".equals(string)) {
                                                                                                                                            bundle3.putInt(str, ResultInfo.ERROR_INVALID_VALUE);
                                                                                                                                            break;
                                                                                                                                        }
                                                                                                                                        break;
                                                                                                                                    case ' ':
                                                                                                                                        if (!"1".equals(string) && !"0".equals(string)) {
                                                                                                                                            bundle3.putInt(str, ResultInfo.ERROR_INVALID_VALUE);
                                                                                                                                            break;
                                                                                                                                        }
                                                                                                                                        break;
                                                                                                                                    case '!':
                                                                                                                                        if (!"2".equals(string) && !"1".equals(string) && !"0".equals(string)) {
                                                                                                                                            bundle3.putInt(str, ResultInfo.ERROR_INVALID_VALUE);
                                                                                                                                            break;
                                                                                                                                        }
                                                                                                                                        break;
                                                                                                                                    case '\"':
                                                                                                                                        if (!"1".equals(string) && !"0".equals(string)) {
                                                                                                                                            bundle3.putInt(str, ResultInfo.ERROR_INVALID_VALUE);
                                                                                                                                            break;
                                                                                                                                        }
                                                                                                                                        break;
                                                                                                                                    case '#':
                                                                                                                                        if (Integer.parseInt(string) < 0 || Integer.parseInt(string) > 75) {
                                                                                                                                            bundle3.putInt(str, ResultInfo.ERROR_INVALID_VALUE);
                                                                                                                                            break;
                                                                                                                                        }
                                                                                                                                        break;
                                                                                                                                    case '$':
                                                                                                                                        if (!"1".equals(string) && !"0".equals(string)) {
                                                                                                                                            bundle3.putInt(str, ResultInfo.ERROR_INVALID_VALUE);
                                                                                                                                            break;
                                                                                                                                        }
                                                                                                                                        break;
                                                                                                                                    case '%':
                                                                                                                                        if (!"1".equals(string) && !"0".equals(string)) {
                                                                                                                                            bundle3.putInt(str, ResultInfo.ERROR_INVALID_VALUE);
                                                                                                                                            break;
                                                                                                                                        }
                                                                                                                                        break;
                                                                                                                                    case '&':
                                                                                                                                        if (!"1".equals(string) && !"0".equals(string)) {
                                                                                                                                            bundle3.putInt(str, ResultInfo.ERROR_INVALID_VALUE);
                                                                                                                                            break;
                                                                                                                                        }
                                                                                                                                        break;
                                                                                                                                    case '\'':
                                                                                                                                        if (!"1".equals(string) && !"0".equals(string)) {
                                                                                                                                            bundle3.putInt(str, ResultInfo.ERROR_INVALID_VALUE);
                                                                                                                                            break;
                                                                                                                                        }
                                                                                                                                        break;
                                                                                                                                    case '(':
                                                                                                                                        if (!"1".equals(string) && !"0".equals(string)) {
                                                                                                                                            bundle3.putInt(str, ResultInfo.ERROR_INVALID_VALUE);
                                                                                                                                            break;
                                                                                                                                        }
                                                                                                                                        break;
                                                                                                                                    case ')':
                                                                                                                                        if (!"3".equals(string) && !"2".equals(string) && !"1".equals(string) && !"0".equals(string)) {
                                                                                                                                            bundle3.putInt(str, ResultInfo.ERROR_INVALID_VALUE);
                                                                                                                                            break;
                                                                                                                                        }
                                                                                                                                        break;
                                                                                                                                    case '*':
                                                                                                                                        if (!"1".equals(string) && !"0".equals(string)) {
                                                                                                                                            bundle3.putInt(str, ResultInfo.ERROR_INVALID_VALUE);
                                                                                                                                            break;
                                                                                                                                        }
                                                                                                                                        break;
                                                                                                                                    case '+':
                                                                                                                                        if (!"1".equals(string) && !"0".equals(string)) {
                                                                                                                                            bundle3.putInt(str, ResultInfo.ERROR_INVALID_VALUE);
                                                                                                                                            break;
                                                                                                                                        }
                                                                                                                                        break;
                                                                                                                                    case ',':
                                                                                                                                        if (!"1".equals(string) && !"0".equals(string)) {
                                                                                                                                            bundle3.putInt(str, ResultInfo.ERROR_INVALID_VALUE);
                                                                                                                                            break;
                                                                                                                                        }
                                                                                                                                        break;
                                                                                                                                    case '-':
                                                                                                                                        if (!"1".equals(string) && !"0".equals(string)) {
                                                                                                                                            bundle3.putInt(str, ResultInfo.ERROR_INVALID_VALUE);
                                                                                                                                            break;
                                                                                                                                        }
                                                                                                                                        break;
                                                                                                                                    case '.':
                                                                                                                                        if (!"1".equals(string) && !"0".equals(string)) {
                                                                                                                                            bundle3.putInt(str, ResultInfo.ERROR_INVALID_VALUE);
                                                                                                                                            break;
                                                                                                                                        }
                                                                                                                                        break;
                                                                                                                                    default:
                                                                                                                                        switch (c) {
                                                                                                                                            case '1':
                                                                                                                                                if (!"1".equals(string) && !"0".equals(string)) {
                                                                                                                                                    bundle3.putInt(str, ResultInfo.ERROR_INVALID_VALUE);
                                                                                                                                                    break;
                                                                                                                                                }
                                                                                                                                                break;
                                                                                                                                            case '2':
                                                                                                                                                if (!"1".equals(string) && !"0".equals(string)) {
                                                                                                                                                    bundle3.putInt(str, ResultInfo.ERROR_INVALID_VALUE);
                                                                                                                                                    break;
                                                                                                                                                }
                                                                                                                                                break;
                                                                                                                                            case '3':
                                                                                                                                                if (!"1".equals(string) && !"0".equals(string)) {
                                                                                                                                                    bundle3.putInt(str, ResultInfo.ERROR_INVALID_VALUE);
                                                                                                                                                    break;
                                                                                                                                                }
                                                                                                                                                break;
                                                                                                                                            case '4':
                                                                                                                                                if (!hasSystemVibrationMenu(context)) {
                                                                                                                                                    bundle3.putInt(str, ResultInfo.ERROR_NOT_SUPPORTED);
                                                                                                                                                }
                                                                                                                                                if (!"1".equals(string) && !"0".equals(string)) {
                                                                                                                                                    bundle3.putInt(str, ResultInfo.ERROR_INVALID_VALUE);
                                                                                                                                                    break;
                                                                                                                                                }
                                                                                                                                                break;
                                                                                                                                            default:
                                                                                                                                                switch (c) {
                                                                                                                                                    case 'Y':
                                                                                                                                                        if (!"1".equals(string) && !"0".equals(string)) {
                                                                                                                                                            bundle3.putInt(str, ResultInfo.ERROR_INVALID_VALUE);
                                                                                                                                                            break;
                                                                                                                                                        }
                                                                                                                                                        break;
                                                                                                                                                    case 'Z':
                                                                                                                                                        if (!"1".equals(string) && !"0".equals(string)) {
                                                                                                                                                            bundle3.putInt(str, ResultInfo.ERROR_INVALID_VALUE);
                                                                                                                                                            break;
                                                                                                                                                        }
                                                                                                                                                        break;
                                                                                                                                                    case '[':
                                                                                                                                                        if (!"2".equals(string) && !"1".equals(string) && !"0".equals(string)) {
                                                                                                                                                            bundle3.putInt(str, ResultInfo.ERROR_INVALID_VALUE);
                                                                                                                                                            break;
                                                                                                                                                        }
                                                                                                                                                        break;
                                                                                                                                                }
                                                                                                                                        }
                                                                                                                                }
                                                                                                                        }
                                                                                                                }
                                                                                                            } else if (!"true".equals(string) && !"false".equals(string)) {
                                                                                                                bundle3.putInt(str, ResultInfo.ERROR_INVALID_VALUE);
                                                                                                            }
                                                                                                        } else if (!"1".equals(string) && !"0".equals(string)) {
                                                                                                            bundle3.putInt(str, ResultInfo.ERROR_INVALID_VALUE);
                                                                                                        }
                                                                                                    } else if (!"1".equals(string) && !"0".equals(string)) {
                                                                                                        bundle3.putInt(str, ResultInfo.ERROR_INVALID_VALUE);
                                                                                                    }
                                                                                                } else if (!"1".equals(string) && !"0".equals(string)) {
                                                                                                    bundle3.putInt(str, ResultInfo.ERROR_INVALID_VALUE);
                                                                                                }
                                                                                            } else if (!"3".equals(string) && !"2".equals(string) && !"1".equals(string) && !"0".equals(string)) {
                                                                                                bundle3.putInt(str, ResultInfo.ERROR_INVALID_VALUE);
                                                                                            }
                                                                                        } else if (!"1".equals(string) && !"0".equals(string)) {
                                                                                            bundle3.putInt(str, ResultInfo.ERROR_INVALID_VALUE);
                                                                                        }
                                                                                    } else if (Integer.parseInt(string) < 10 || Integer.parseInt(string) > 600) {
                                                                                        bundle3.putInt(str, ResultInfo.ERROR_INVALID_VALUE);
                                                                                    }
                                                                                } else if (Integer.parseInt(string) < 25 || Integer.parseInt(string) > 400) {
                                                                                    bundle3.putInt(str, ResultInfo.ERROR_INVALID_VALUE);
                                                                                }
                                                                            } else if (!"1".equals(string) && !"0".equals(string)) {
                                                                                bundle3.putInt(str, ResultInfo.ERROR_INVALID_VALUE);
                                                                            }
                                                                        } else if (!"1".equals(string) && !"0".equals(string)) {
                                                                            bundle3.putInt(str, ResultInfo.ERROR_INVALID_VALUE);
                                                                        }
                                                                    }
                                                                    if (!"1".equals(string) && !"0".equals(string)) {
                                                                        bundle3.putInt(str, ResultInfo.ERROR_INVALID_VALUE);
                                                                    }
                                                                }
                                                            } else if (!"1".equals(string) && !"0".equals(string)) {
                                                                bundle3.putInt(str, ResultInfo.ERROR_INVALID_VALUE);
                                                            }
                                                        } else if (!"1".equals(string) && !"0".equals(string)) {
                                                            bundle3.putInt(str, ResultInfo.ERROR_INVALID_VALUE);
                                                        }
                                                    } else if (!"1".equals(string) && !"0".equals(string)) {
                                                        bundle3.putInt(str, ResultInfo.ERROR_INVALID_VALUE);
                                                    }
                                                } else if (!"1".equals(string) && !"0".equals(string)) {
                                                    bundle3.putInt(str, ResultInfo.ERROR_INVALID_VALUE);
                                                }
                                            } else if (!"1".equals(string) && !"0".equals(string)) {
                                                bundle3.putInt(str, ResultInfo.ERROR_INVALID_VALUE);
                                            }
                                        } catch (Exception e3) {
                                            KnoxsdkFileLog.e(TAG, "fail to validate " + e3);
                                        }
                                    }
                                }
                            }
                        }
                        bundle2 = bundle;
                    }
                }
            }
        }
        return bundle3;
    }

    public static int checkVersion(KeyInfo.KEY key) {
        try {
            if (key.getVersion() > EnterpriseDeviceManager.getAPILevelForInternal()) {
                return ResultInfo.ERROR_NOT_SUPPORTED;
            }
            return ResultInfo.ERROR_NONE;
        } catch (Exception e) {
            KnoxsdkFileLog.e(TAG, "fail to checkVersion " + e);
            return ResultInfo.ERROR_UNKNOWN;
        }
    }

    public static int checkPermission(Context context, String str, KeyInfo.KEY key) {
        try {
            if (AppGlobals.getPackageManager().checkPermission(key.getPermission(), str, context.getUserId()) != 0) {
                return ResultInfo.ERROR_PERMISSION_DENIED;
            }
            return ResultInfo.ERROR_NONE;
        } catch (Exception e) {
            KnoxsdkFileLog.e(TAG, "fail to checkPermission  " + e);
            return ResultInfo.ERROR_UNKNOWN;
        }
    }

    public static boolean checkWPCODMode(Context context) {
        return ((DevicePolicyManager) context.getSystemService("device_policy")).isOrganizationOwnedDeviceWithManagedProfile();
    }

    public static boolean checkKeyType(String str) {
        for (String str2 : unusedBundleKeys) {
            if (str2.equals(str)) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasSystemVibrationMenu(Context context) {
        if (isSupportDcHaptic(context)) {
            return true;
        }
        return hasVibrator(context) && isEnableIntensity(context);
    }

    public static boolean isSupportDcHaptic(Context context) {
        return VibRune.SUPPORT_HAPTIC_FEEDBACK_ON_DC_MOTOR && hasVibrator(context) && !isEnableIntensity(context);
    }

    public static boolean hasVibrator(Context context) {
        Vibrator vibrator = (Vibrator) context.getSystemService("vibrator");
        return vibrator != null && vibrator.hasVibrator();
    }

    public static boolean isEnableIntensity(Context context) {
        Vibrator vibrator = (Vibrator) context.getSystemService("vibrator");
        return vibrator != null && vibrator.semGetSupportedVibrationType() > 1;
    }

    public static boolean supportLightSensor(Context context) {
        SensorManager sensorManager = (SensorManager) context.getSystemService("sensor");
        if (sensorManager == null) {
            return false;
        }
        List<Sensor> sensorList = sensorManager.getSensorList(-1);
        for (int i = 0; i < sensorList.size(); i++) {
            int type = sensorList.get(i).getType();
            if (type == 5 || type == 65601) {
                return true;
            }
        }
        return false;
    }

    public static boolean supportCameraSensor(Context context) {
        SensorManager sensorManager = (SensorManager) context.getSystemService("sensor");
        return (sensorManager == null || sensorManager.getDefaultSensor(5) != null || sensorManager.getDefaultSensor(65604) == null) ? false : true;
    }

    public static boolean supportAutoBrightness(Context context) {
        return supportLightSensor(context) || supportCameraSensor(context);
    }

    public static boolean supportPocketMode(Context context) {
        SemMotionRecognitionManager semMotionRecognitionManager = (SemMotionRecognitionManager) context.getSystemService("motion_recognition");
        if (semMotionRecognitionManager == null) {
            return false;
        }
        return semMotionRecognitionManager.isAvailable(8388608);
    }

    public static String getCallerPackage(Context context) {
        return context.getPackageName();
    }
}
