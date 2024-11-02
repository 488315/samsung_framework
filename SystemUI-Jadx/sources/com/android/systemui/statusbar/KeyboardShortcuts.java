package com.android.systemui.statusbar;

import android.R;
import android.app.AppGlobals;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.hardware.input.InputManager;
import android.os.Handler;
import android.os.Looper;
import android.util.SparseArray;
import android.view.ContextThemeWrapper;
import android.view.InputDevice;
import android.view.KeyboardShortcutGroup;
import android.view.KeyboardShortcutInfo;
import android.view.WindowManager;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.Dependency;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.statusbar.model.KshData;
import com.android.systemui.statusbar.model.KshData$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.model.KshData$$ExternalSyntheticLambda1;
import com.android.systemui.statusbar.model.SamsungAppShortcutsEnum;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.sec.ims.configuration.DATA;
import com.sec.ims.settings.ImsProfile;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class KeyboardShortcuts {
    public static long mShowTime;
    static KeyboardShortcuts sInstance;
    public static final Object sLock = new Object();
    Context mContext;
    Dialog mKeyboardShortcutsDialog;
    public KshPresenter mKshPresenter;

    public KeyboardShortcuts(Context context, WindowManager windowManager) {
        SparseArray sparseArray = new SparseArray();
        SparseArray sparseArray2 = new SparseArray();
        SparseArray sparseArray3 = new SparseArray();
        SparseArray sparseArray4 = new SparseArray();
        new Handler(Looper.getMainLooper());
        new DialogInterface.OnClickListener() { // from class: com.android.systemui.statusbar.KeyboardShortcuts.1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                KeyboardShortcuts keyboardShortcuts = KeyboardShortcuts.this;
                Object obj = KeyboardShortcuts.sLock;
                keyboardShortcuts.dismissKeyboardShortcuts();
            }
        };
        new Comparator(this) { // from class: com.android.systemui.statusbar.KeyboardShortcuts.2
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                boolean z;
                boolean z2;
                KeyboardShortcutInfo keyboardShortcutInfo = (KeyboardShortcutInfo) obj;
                KeyboardShortcutInfo keyboardShortcutInfo2 = (KeyboardShortcutInfo) obj2;
                if (keyboardShortcutInfo.getLabel() != null && !keyboardShortcutInfo.getLabel().toString().isEmpty()) {
                    z = false;
                } else {
                    z = true;
                }
                if (keyboardShortcutInfo2.getLabel() != null && !keyboardShortcutInfo2.getLabel().toString().isEmpty()) {
                    z2 = false;
                } else {
                    z2 = true;
                }
                if (z && z2) {
                    return 0;
                }
                if (z) {
                    return 1;
                }
                if (z2) {
                    return -1;
                }
                return keyboardShortcutInfo.getLabel().toString().compareToIgnoreCase(keyboardShortcutInfo2.getLabel().toString());
            }
        };
        this.mContext = new ContextThemeWrapper(context, R.style.Theme.DeviceDefault.Settings);
        AppGlobals.getPackageManager();
        if (windowManager == null) {
        }
        sparseArray.put(4, KeyboardShortcutListSearch$$ExternalSyntheticOutline0.m(context, com.android.systemui.R.string.keyboard_key_home, sparseArray, 3, com.android.systemui.R.string.keyboard_key_back));
        sparseArray.put(20, KeyboardShortcutListSearch$$ExternalSyntheticOutline0.m(context, com.android.systemui.R.string.keyboard_key_dpad_up, sparseArray, 19, com.android.systemui.R.string.keyboard_key_dpad_down));
        sparseArray.put(22, KeyboardShortcutListSearch$$ExternalSyntheticOutline0.m(context, com.android.systemui.R.string.keyboard_key_dpad_left, sparseArray, 21, com.android.systemui.R.string.keyboard_key_dpad_right));
        sparseArray.put(23, context.getString(com.android.systemui.R.string.keyboard_key_dpad_center));
        sparseArray.put(56, ".");
        sparseArray.put(62, KeyboardShortcutListSearch$$ExternalSyntheticOutline0.m(context, com.android.systemui.R.string.keyboard_key_tab, sparseArray, 61, com.android.systemui.R.string.keyboard_key_space));
        sparseArray.put(67, KeyboardShortcutListSearch$$ExternalSyntheticOutline0.m(context, com.android.systemui.R.string.keyboard_key_enter, sparseArray, 66, com.android.systemui.R.string.keyboard_key_backspace));
        sparseArray.put(86, KeyboardShortcutListSearch$$ExternalSyntheticOutline0.m(context, com.android.systemui.R.string.keyboard_key_media_play_pause, sparseArray, 85, com.android.systemui.R.string.keyboard_key_media_stop));
        sparseArray.put(88, KeyboardShortcutListSearch$$ExternalSyntheticOutline0.m(context, com.android.systemui.R.string.keyboard_key_media_next, sparseArray, 87, com.android.systemui.R.string.keyboard_key_media_previous));
        sparseArray.put(90, KeyboardShortcutListSearch$$ExternalSyntheticOutline0.m(context, com.android.systemui.R.string.keyboard_key_media_rewind, sparseArray, 89, com.android.systemui.R.string.keyboard_key_media_fast_forward));
        sparseArray.put(93, KeyboardShortcutListSearch$$ExternalSyntheticOutline0.m(context, com.android.systemui.R.string.keyboard_key_page_up, sparseArray, 92, com.android.systemui.R.string.keyboard_key_page_down));
        sparseArray.put(96, context.getString(com.android.systemui.R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_A));
        sparseArray.put(97, context.getString(com.android.systemui.R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_B));
        sparseArray.put(98, context.getString(com.android.systemui.R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_C));
        sparseArray.put(99, context.getString(com.android.systemui.R.string.keyboard_key_button_template, "X"));
        sparseArray.put(100, context.getString(com.android.systemui.R.string.keyboard_key_button_template, "Y"));
        sparseArray.put(101, context.getString(com.android.systemui.R.string.keyboard_key_button_template, "Z"));
        sparseArray.put(102, context.getString(com.android.systemui.R.string.keyboard_key_button_template, "L1"));
        sparseArray.put(103, context.getString(com.android.systemui.R.string.keyboard_key_button_template, "R1"));
        sparseArray.put(104, context.getString(com.android.systemui.R.string.keyboard_key_button_template, "L2"));
        sparseArray.put(105, context.getString(com.android.systemui.R.string.keyboard_key_button_template, "R2"));
        sparseArray.put(108, context.getString(com.android.systemui.R.string.keyboard_key_button_template, "Start"));
        sparseArray.put(109, context.getString(com.android.systemui.R.string.keyboard_key_button_template, "Select"));
        sparseArray.put(110, context.getString(com.android.systemui.R.string.keyboard_key_button_template, "Mode"));
        sparseArray.put(112, context.getString(com.android.systemui.R.string.keyboard_key_forward_del));
        sparseArray.put(111, "Esc");
        sparseArray.put(120, "SysRq");
        sparseArray.put(121, "Break");
        sparseArray.put(116, "Scroll Lock");
        sparseArray.put(123, KeyboardShortcutListSearch$$ExternalSyntheticOutline0.m(context, com.android.systemui.R.string.keyboard_key_move_home, sparseArray, 122, com.android.systemui.R.string.keyboard_key_move_end));
        sparseArray.put(124, context.getString(com.android.systemui.R.string.keyboard_key_insert));
        sparseArray.put(131, "F1");
        sparseArray.put(132, "F2");
        sparseArray.put(133, "F3");
        sparseArray.put(134, "F4");
        sparseArray.put(135, "F5");
        sparseArray.put(136, "F6");
        sparseArray.put(137, "F7");
        sparseArray.put(138, "F8");
        sparseArray.put(139, "F9");
        sparseArray.put(140, "F10");
        sparseArray.put(141, "F11");
        sparseArray.put(142, "F12");
        sparseArray.put(143, context.getString(com.android.systemui.R.string.keyboard_key_num_lock));
        sparseArray.put(144, context.getString(com.android.systemui.R.string.keyboard_key_numpad_template, DATA.DM_FIELD_INDEX.PCSCF_DOMAIN));
        sparseArray.put(145, context.getString(com.android.systemui.R.string.keyboard_key_numpad_template, "1"));
        sparseArray.put(146, context.getString(com.android.systemui.R.string.keyboard_key_numpad_template, "2"));
        sparseArray.put(147, context.getString(com.android.systemui.R.string.keyboard_key_numpad_template, DATA.DM_FIELD_INDEX.PUBLIC_USER_ID));
        sparseArray.put(148, context.getString(com.android.systemui.R.string.keyboard_key_numpad_template, "4"));
        sparseArray.put(149, context.getString(com.android.systemui.R.string.keyboard_key_numpad_template, DATA.DM_FIELD_INDEX.LBO_PCSCF_ADDRESS_TYPE));
        sparseArray.put(150, context.getString(com.android.systemui.R.string.keyboard_key_numpad_template, DATA.DM_FIELD_INDEX.AMR_AUDIO_BITRATE));
        sparseArray.put(151, context.getString(com.android.systemui.R.string.keyboard_key_numpad_template, DATA.DM_FIELD_INDEX.AMR_AUDIO_BITRATE_WB));
        sparseArray.put(152, context.getString(com.android.systemui.R.string.keyboard_key_numpad_template, DATA.DM_FIELD_INDEX.SIP_SESSION_TIMER));
        sparseArray.put(153, context.getString(com.android.systemui.R.string.keyboard_key_numpad_template, DATA.DM_FIELD_INDEX.SMS_FORMAT));
        sparseArray.put(154, context.getString(com.android.systemui.R.string.keyboard_key_numpad_template, "/"));
        sparseArray.put(155, context.getString(com.android.systemui.R.string.keyboard_key_numpad_template, "*"));
        sparseArray.put(156, context.getString(com.android.systemui.R.string.keyboard_key_numpad_template, "-"));
        sparseArray.put(157, context.getString(com.android.systemui.R.string.keyboard_key_numpad_template, "+"));
        sparseArray.put(158, context.getString(com.android.systemui.R.string.keyboard_key_numpad_template, "."));
        sparseArray.put(159, context.getString(com.android.systemui.R.string.keyboard_key_numpad_template, ","));
        sparseArray.put(160, context.getString(com.android.systemui.R.string.keyboard_key_numpad_template, context.getString(com.android.systemui.R.string.keyboard_key_enter)));
        sparseArray.put(161, context.getString(com.android.systemui.R.string.keyboard_key_numpad_template, "="));
        sparseArray.put(162, context.getString(com.android.systemui.R.string.keyboard_key_numpad_template, "("));
        sparseArray.put(163, context.getString(com.android.systemui.R.string.keyboard_key_numpad_template, ")"));
        sparseArray.put(IKnoxCustomManager.Stub.TRANSACTION_getWifiHotspotEnabledState, "半角/全角");
        sparseArray.put(IKnoxCustomManager.Stub.TRANSACTION_getWifiState, "英数");
        sparseArray.put(IKnoxCustomManager.Stub.TRANSACTION_addAutoCallNumber, "無変換");
        sparseArray.put(IKnoxCustomManager.Stub.TRANSACTION_removeAutoCallNumber, "変換");
        sparseArray.put(IKnoxCustomManager.Stub.TRANSACTION_getAutoCallNumberDelay, "かな");
        sparseArray2.put(65536, "Meta");
        sparseArray2.put(4096, "Ctrl");
        sparseArray2.put(2, "Alt");
        sparseArray2.put(1, "Shift");
        sparseArray2.put(4, "Sym");
        sparseArray2.put(8, "Fn");
        sparseArray3.put(67, context.getDrawable(com.android.systemui.R.drawable.ic_ksh_key_backspace));
        sparseArray3.put(66, context.getDrawable(com.android.systemui.R.drawable.ic_ksh_key_enter));
        sparseArray3.put(19, context.getDrawable(com.android.systemui.R.drawable.ic_ksh_key_up));
        sparseArray3.put(22, context.getDrawable(com.android.systemui.R.drawable.ic_ksh_key_right));
        sparseArray3.put(20, context.getDrawable(com.android.systemui.R.drawable.ic_ksh_key_down));
        sparseArray3.put(21, context.getDrawable(com.android.systemui.R.drawable.ic_ksh_key_left));
        sparseArray4.put(65536, context.getDrawable(com.android.systemui.R.drawable.ic_ksh_key_meta));
        this.mKshPresenter = new KshPresenter(context);
    }

    public static void dismiss() {
        synchronized (sLock) {
            KeyboardShortcuts keyboardShortcuts = sInstance;
            if (keyboardShortcuts != null) {
                MetricsLogger.hidden(keyboardShortcuts.mContext, 500);
                sInstance.dismissKeyboardShortcuts();
                sInstance = null;
            }
        }
    }

    public static void show(int i, Context context) {
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - mShowTime < 500) {
            return;
        }
        mShowTime = currentTimeMillis;
        MetricsLogger.visible(context, 500);
        synchronized (sLock) {
            KeyboardShortcuts keyboardShortcuts = sInstance;
            if (keyboardShortcuts != null && !keyboardShortcuts.mContext.equals(context)) {
                dismiss();
            }
            if (sInstance == null) {
                sInstance = new KeyboardShortcuts(context, null);
            }
            sInstance.showKeyboardShortcuts(i);
        }
    }

    public static void toggle(int i, Context context) {
        boolean z;
        Dialog dialog;
        synchronized (sLock) {
            KeyboardShortcuts keyboardShortcuts = sInstance;
            if (keyboardShortcuts != null && (dialog = keyboardShortcuts.mKeyboardShortcutsDialog) != null && dialog.isShowing()) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                dismiss();
            } else {
                show(i, context);
            }
        }
    }

    public final void dismissKeyboardShortcuts() {
        final KshPresenter kshPresenter = this.mKshPresenter;
        ((ConfigurationControllerImpl) kshPresenter.mConfigurationController).removeCallback(kshPresenter.mConfigurationListener);
        ((BroadcastDispatcher) Dependency.get(BroadcastDispatcher.class)).unregisterReceiver(kshPresenter.mPogoKeyboardChangedReceiver);
        kshPresenter.mHandler.post(new Runnable() { // from class: com.android.systemui.statusbar.KshPresenter$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                KshPresenter kshPresenter2 = KshPresenter.this;
                KshView kshView = kshPresenter2.mKshView;
                Dialog dialog = kshView.mKeyboardShortcutsDialog;
                if (dialog != null) {
                    dialog.dismiss();
                    kshView.mKeyboardShortcutsDialog.setOnKeyListener(null);
                    kshView.mKeyboardShortcutsDialog = null;
                }
                kshPresenter2.mKshView = null;
            }
        });
        kshPresenter.mKshData = null;
        this.mKshPresenter = null;
    }

    public void showKeyboardShortcuts(int i) {
        InputDevice inputDevice;
        final KshPresenter kshPresenter = this.mKshPresenter;
        ((ConfigurationControllerImpl) kshPresenter.mConfigurationController).addCallback(kshPresenter.mConfigurationListener);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.samsung.android.input.POGO_KEYBOARD_CHANGED");
        ((BroadcastDispatcher) Dependency.get(BroadcastDispatcher.class)).registerReceiver(intentFilter, kshPresenter.mPogoKeyboardChangedReceiver);
        KshData kshData = kshPresenter.mKshData;
        kshData.getClass();
        InputManager inputManager = InputManager.getInstance();
        kshData.mBackupKeyCharacterMap = inputManager.getInputDevice(-1).getKeyCharacterMap();
        if (i != -1 && (inputDevice = inputManager.getInputDevice(i)) != null) {
            kshData.mKeyCharacterMap = inputDevice.getKeyCharacterMap();
        } else {
            int[] inputDeviceIds = inputManager.getInputDeviceIds();
            int i2 = 0;
            while (true) {
                if (i2 < inputDeviceIds.length) {
                    InputDevice inputDevice2 = inputManager.getInputDevice(inputDeviceIds[i2]);
                    if (inputDevice2.getId() != -1 && inputDevice2.isFullKeyboard()) {
                        kshData.mKeyCharacterMap = inputDevice2.getKeyCharacterMap();
                        break;
                    }
                    i2++;
                } else {
                    kshData.mKeyCharacterMap = kshData.mBackupKeyCharacterMap;
                    break;
                }
            }
        }
        ((WindowManager) kshPresenter.mContext.getSystemService("window")).requestAppKeyboardShortcuts(new WindowManager.KeyboardShortcutsReceiver() { // from class: com.android.systemui.statusbar.KshPresenter.3
            public AnonymousClass3() {
            }

            public final void onKeyboardShortcutsReceived(final List list) {
                Optional ofNullable;
                list.add(KshPresenter.this.mKshData.getSamsungSystemShortcuts());
                KshData kshData2 = KshPresenter.this.mKshData;
                kshData2.getClass();
                List list2 = (List) Arrays.stream((SamsungAppShortcutsEnum[]) SamsungAppShortcutsEnum.class.getEnumConstants()).map(new KshData$$ExternalSyntheticLambda0(kshData2, 1)).filter(new KshData$$ExternalSyntheticLambda1(1)).collect(Collectors.toList());
                kshData2.mDefaultIcons = kshData2.mKshDataUtils.mDefaultIcons;
                if (list2.size() == 0) {
                    ofNullable = Optional.empty();
                } else {
                    ofNullable = Optional.ofNullable(new KeyboardShortcutGroup(kshData2.mContext.getString(com.android.systemui.R.string.ksh_group_applications), list2, true));
                }
                ofNullable.ifPresent(new Consumer() { // from class: com.android.systemui.statusbar.KshPresenter$3$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        list.add((KeyboardShortcutGroup) obj);
                    }
                });
                final KshPresenter kshPresenter2 = KshPresenter.this;
                kshPresenter2.mKshData.mKshGroups = list;
                kshPresenter2.mHandler.post(new Runnable() { // from class: com.android.systemui.statusbar.KshPresenter$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        KshPresenter kshPresenter3 = KshPresenter.this;
                        List list3 = list;
                        KshView kshView = kshPresenter3.mKshView;
                        if (kshView != null) {
                            Dialog dialog = kshView.mKeyboardShortcutsDialog;
                            if (dialog != null) {
                                dialog.dismiss();
                                kshView.mKeyboardShortcutsDialog.setOnKeyListener(null);
                                kshView.mKeyboardShortcutsDialog = null;
                            }
                            if (kshPresenter3.mKshData != null) {
                                kshPresenter3.mKshView.showKshDialog(list3);
                            }
                        }
                    }
                });
            }
        }, i);
    }
}
