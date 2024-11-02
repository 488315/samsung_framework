package com.android.systemui.statusbar.notification;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.telephony.SmsMessage;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Property;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.widget.SystemUIEditText;
import com.samsung.android.desktopsystemui.sharedlib.system.ActivityManagerWrapper;
import com.samsung.android.knox.accounts.Account;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class SubscreenNotificationReplyActivity extends Activity implements CommandQueue.Callbacks {
    public static final String TAG;
    public final SubscreenNotificationController controller;
    public SystemUIEditText editText;
    public NotificationEntry entry;
    public InputMethodManager imm;
    public boolean isForce;
    public boolean isSent;
    public boolean isSms;
    public int maxLength;
    public final NotifPipeline notifPipeLine;
    public LinearLayout replyLayout;
    public ImageView sendButton;
    public SubscreenSubRoomNotification subRoomNoti;
    public Toast toast;
    public String key = "";
    public CharSequence prevText = "";
    public String signature = "";
    public final SubscreenNotificationReplyActivity$displayListener$1 displayListener = new DisplayLifecycle.Observer() { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationReplyActivity$displayListener$1
        @Override // com.android.systemui.keyguard.DisplayLifecycle.Observer
        public final void onFolderStateChanged(boolean z) {
            KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("isFolderOpened: ", z, SubscreenNotificationReplyActivity.TAG);
            if (z) {
                SubscreenNotificationReplyActivity subscreenNotificationReplyActivity = SubscreenNotificationReplyActivity.this;
                subscreenNotificationReplyActivity.isForce = true;
                subscreenNotificationReplyActivity.finish();
            }
        }
    };
    public final SubscreenNotificationReplyActivity$broadcastReceiver$1 broadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationReplyActivity$broadcastReceiver$1
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if (intent != null) {
                SubscreenNotificationReplyActivity subscreenNotificationReplyActivity = SubscreenNotificationReplyActivity.this;
                if (Intrinsics.areEqual(intent.getAction(), "android.intent.action.CLOSE_SYSTEM_DIALOGS") && Intrinsics.areEqual(intent.getStringExtra("reason"), ActivityManagerWrapper.CLOSE_SYSTEM_WINDOWS_REASON_HOME_KEY)) {
                    InputMethodManager inputMethodManager = subscreenNotificationReplyActivity.imm;
                    if (inputMethodManager != null) {
                        inputMethodManager.semForceHideSoftInput();
                    }
                    subscreenNotificationReplyActivity.isForce = true;
                    subscreenNotificationReplyActivity.finish();
                }
            }
        }
    };

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
        TAG = "SubscreenNotificationReplyActivity";
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.statusbar.notification.SubscreenNotificationReplyActivity$displayListener$1] */
    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.statusbar.notification.SubscreenNotificationReplyActivity$broadcastReceiver$1] */
    public SubscreenNotificationReplyActivity(SubscreenNotificationController subscreenNotificationController, NotifPipeline notifPipeline) {
        this.controller = subscreenNotificationController;
        this.notifPipeLine = notifPipeline;
        Log.d(TAG, "SubscreenNotificationReplyActivity()");
    }

    public static final void access$performBackClicked(final SubscreenNotificationReplyActivity subscreenNotificationReplyActivity) {
        InputMethodManager inputMethodManager = subscreenNotificationReplyActivity.imm;
        if (inputMethodManager != null) {
            inputMethodManager.semForceHideSoftInput();
        }
        LinearLayout linearLayout = subscreenNotificationReplyActivity.replyLayout;
        if (linearLayout == null) {
            linearLayout = null;
        }
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(linearLayout, (Property<LinearLayout, Float>) View.ALPHA, 1.0f, 0.0f);
        ofFloat.setDuration(300L);
        ofFloat.addListener(new Animator.AnimatorListener() { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationReplyActivity$performBackClicked$lambda$10$$inlined$doOnEnd$1
            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                SubscreenNotificationReplyActivity.this.finish();
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
            }
        });
        ofFloat.start();
    }

    public static final void access$showExceedTextLimitToast(SubscreenNotificationReplyActivity subscreenNotificationReplyActivity) {
        String string = subscreenNotificationReplyActivity.getResources().getString(R.string.noti_direct_reply_exceed_text_limit_toast);
        Toast toast = subscreenNotificationReplyActivity.toast;
        if (toast != null) {
            toast.cancel();
        }
        Toast makeText = Toast.makeText(subscreenNotificationReplyActivity, string, 1);
        subscreenNotificationReplyActivity.toast = makeText;
        if (makeText != null) {
            makeText.show();
        }
        Log.d(TAG, "showExceedTextLimitToast. current text = " + ((Object) subscreenNotificationReplyActivity.prevText));
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public final boolean dispatchKeyEvent(KeyEvent keyEvent) {
        Integer num;
        ImageView imageView;
        if (keyEvent != null) {
            num = Integer.valueOf(keyEvent.getAction());
        } else {
            num = null;
        }
        if (num != null) {
            boolean z = true;
            if (num.intValue() == 1 && keyEvent.getKeyCode() == 66) {
                ImageView imageView2 = this.sendButton;
                if (imageView2 == null || !imageView2.isFocused()) {
                    z = false;
                }
                if (z && (imageView = this.sendButton) != null) {
                    imageView.performClick();
                }
            }
        }
        return super.dispatchKeyEvent(keyEvent);
    }

    public final void enableSendButton() {
        Editable editable;
        boolean z;
        float f;
        ImageView imageView = this.sendButton;
        if (imageView != null) {
            SystemUIEditText systemUIEditText = this.editText;
            if (systemUIEditText != null) {
                editable = systemUIEditText.getText();
            } else {
                editable = null;
            }
            if (String.valueOf(editable).length() > 0) {
                z = true;
            } else {
                z = false;
            }
            imageView.setEnabled(z);
            if (z) {
                f = 1.0f;
            } else {
                f = 0.4f;
            }
            imageView.setAlpha(f);
        }
    }

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        SubscreenSubRoomNotification subscreenSubRoomNotification;
        super.onCreate(bundle);
        String str = TAG;
        Log.d(str, "onCreate()");
        this.imm = (InputMethodManager) getSystemService("input_method");
        setContentView(R.layout.subscreen_notification_reply_activity);
        setShowWhenLocked(true);
        SubscreenNotificationController subscreenNotificationController = this.controller;
        SubscreenDeviceModelParent subscreenDeviceModelParent = subscreenNotificationController.mDeviceModel;
        LinearLayout linearLayout = null;
        if (subscreenDeviceModelParent != null) {
            subscreenSubRoomNotification = subscreenDeviceModelParent.getSubRoomNotification();
        } else {
            subscreenSubRoomNotification = null;
        }
        this.subRoomNoti = subscreenSubRoomNotification;
        subscreenSubRoomNotification.mSubscreenMainLayout.setVisibility(8);
        LinearLayout linearLayout2 = (LinearLayout) findViewById(R.id.reply_activity_main_layout);
        this.replyLayout = linearLayout2;
        SubscreenDeviceModelParent subscreenDeviceModelParent2 = subscreenNotificationController.mDeviceModel;
        if (subscreenDeviceModelParent2 != null) {
            subscreenDeviceModelParent2.updateMainHeaderView(linearLayout2);
        }
        SubscreenDeviceModelParent subscreenDeviceModelParent3 = subscreenNotificationController.mDeviceModel;
        if (subscreenDeviceModelParent3 != null) {
            Context baseContext = getBaseContext();
            SubscreenSubRoomNotification subscreenSubRoomNotification2 = this.subRoomNoti;
            if (subscreenSubRoomNotification2 == null) {
                subscreenSubRoomNotification2 = null;
            }
            subscreenDeviceModelParent3.initMainHeaderViewItems(baseContext, subscreenSubRoomNotification2.mNotificationDetailAdapter.mSelectNotificationInfo, true);
        }
        SubscreenDeviceModelParent subscreenDeviceModelParent4 = subscreenNotificationController.mDeviceModel;
        if (subscreenDeviceModelParent4 != null) {
            subscreenDeviceModelParent4.updateMainHeaderViewVisibility(0);
        }
        SubscreenDeviceModelParent subscreenDeviceModelParent5 = subscreenNotificationController.mDeviceModel;
        if (subscreenDeviceModelParent5 != null) {
            subscreenDeviceModelParent5.setStartedReplyActivity();
        }
        LinearLayout linearLayout3 = this.replyLayout;
        if (linearLayout3 != null) {
            linearLayout = linearLayout3;
        }
        ObjectAnimator.ofFloat(linearLayout, (Property<LinearLayout, Float>) View.ALPHA, 0.5f, 1.0f).setDuration(250L).start();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.key = extras.getString("key", "");
            this.maxLength = extras.getInt("maxLength");
            this.isSms = extras.getBoolean("isSms");
            this.signature = extras.getString(Account.SIGNATURE);
        }
        NotificationEntry entry = this.notifPipeLine.getEntry(this.key);
        this.entry = entry;
        if (entry != null) {
            this.prevText = entry.remoteInputText;
        }
        String str2 = this.key;
        CharSequence charSequence = this.prevText;
        Log.d(str, "Reply Info. key = " + str2 + ", prevText = " + ((Object) charSequence) + ", maxLength = " + this.maxLength + ", isSms = " + this.isSms + ", signature = " + this.signature);
        int intForUser = Settings.System.getIntForUser(getContentResolver(), "screen_off_timeout", 10000, -2);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        if (attributes != null) {
            attributes.semSetScreenTimeout(intForUser);
            attributes.semSetScreenDimDuration(0L);
            attributes.privateFlags |= 16;
            attributes.layoutInDisplayCutoutMode = 3;
            getWindow().setAttributes(attributes);
        }
        getWindow().setBackgroundDrawableResource(R.drawable.subscreen_notification_main_layout_background_b5);
        getWindow().setSoftInputMode(4);
        ((LinearLayout) findViewById(R.id.back_key)).setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationReplyActivity$initView$1$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SubscreenNotificationReplyActivity.access$performBackClicked(SubscreenNotificationReplyActivity.this);
            }
        });
        ((FrameLayout) findViewById(R.id.header_app_icon_layout)).setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationReplyActivity$initView$2$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SubscreenNotificationReplyActivity.access$performBackClicked(SubscreenNotificationReplyActivity.this);
            }
        });
        final SystemUIEditText systemUIEditText = (SystemUIEditText) findViewById(R.id.edit_responses);
        systemUIEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationReplyActivity$initView$3$1
            @Override // android.view.View.OnFocusChangeListener
            public final void onFocusChange(View view, boolean z) {
                InputMethodManager inputMethodManager;
                if (z && view.isPressed() && (inputMethodManager = SubscreenNotificationReplyActivity.this.imm) != null) {
                    inputMethodManager.showSoftInput(view, 1);
                }
            }
        });
        systemUIEditText.addTextChangedListener(new TextWatcher() { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationReplyActivity$initView$3$2
            @Override // android.text.TextWatcher
            public final void onTextChanged(CharSequence charSequence2, int i, int i2, int i3) {
                SubscreenNotificationReplyActivity subscreenNotificationReplyActivity = SubscreenNotificationReplyActivity.this;
                String str3 = SubscreenNotificationReplyActivity.TAG;
                subscreenNotificationReplyActivity.enableSendButton();
                Editable text = systemUIEditText.getText();
                String str4 = ((Object) text) + SubscreenNotificationReplyActivity.this.signature;
                int length = str4.length();
                SubscreenNotificationReplyActivity subscreenNotificationReplyActivity2 = SubscreenNotificationReplyActivity.this;
                if (subscreenNotificationReplyActivity2.isSms) {
                    if (SmsMessage.calculateLength(str4, false)[0] > 1) {
                        SubscreenNotificationReplyActivity.this.setPrevText();
                        SubscreenNotificationReplyActivity.access$showExceedTextLimitToast(SubscreenNotificationReplyActivity.this);
                        if (SubscreenNotificationReplyActivity.this.prevText == null) {
                            systemUIEditText.setText((CharSequence) null);
                            return;
                        }
                        return;
                    }
                } else if (length > subscreenNotificationReplyActivity2.maxLength) {
                    subscreenNotificationReplyActivity2.setPrevText();
                    SubscreenNotificationReplyActivity.access$showExceedTextLimitToast(SubscreenNotificationReplyActivity.this);
                    if (SubscreenNotificationReplyActivity.this.prevText == null) {
                        systemUIEditText.setText((CharSequence) null);
                        return;
                    }
                    return;
                }
                SubscreenNotificationReplyActivity.this.prevText = systemUIEditText.getText().toString();
            }

            @Override // android.text.TextWatcher
            public final void afterTextChanged(Editable editable) {
            }

            @Override // android.text.TextWatcher
            public final void beforeTextChanged(CharSequence charSequence2, int i, int i2, int i3) {
            }
        });
        this.editText = systemUIEditText;
        final ImageView imageView = (ImageView) findViewById(R.id.reply_send_button);
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationReplyActivity$initView$4$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                Editable editable;
                Editable text;
                SubscreenNotificationReplyActivity subscreenNotificationReplyActivity = SubscreenNotificationReplyActivity.this;
                if (subscreenNotificationReplyActivity.isSent) {
                    return;
                }
                InputMethodManager inputMethodManager = subscreenNotificationReplyActivity.imm;
                if (inputMethodManager != null) {
                    inputMethodManager.hideSoftInputFromWindow(imageView.getWindowToken(), 0);
                }
                SystemUIEditText systemUIEditText2 = SubscreenNotificationReplyActivity.this.editText;
                Integer num = null;
                if (systemUIEditText2 != null) {
                    editable = systemUIEditText2.getText();
                } else {
                    editable = null;
                }
                String valueOf = String.valueOf(editable);
                SubscreenNotificationReplyActivity subscreenNotificationReplyActivity2 = SubscreenNotificationReplyActivity.this;
                final boolean useHistory = subscreenNotificationReplyActivity2.controller.useHistory(subscreenNotificationReplyActivity2.entry);
                SubscreenNotificationReplyActivity subscreenNotificationReplyActivity3 = SubscreenNotificationReplyActivity.this;
                SubscreenNotificationController subscreenNotificationController2 = subscreenNotificationReplyActivity3.controller;
                String str3 = subscreenNotificationReplyActivity3.key;
                Intrinsics.checkNotNull(str3);
                subscreenNotificationController2.replyNotification(str3, valueOf);
                SubscreenNotificationReplyActivity subscreenNotificationReplyActivity4 = SubscreenNotificationReplyActivity.this;
                subscreenNotificationReplyActivity4.isSent = true;
                LinearLayout linearLayout4 = subscreenNotificationReplyActivity4.replyLayout;
                if (linearLayout4 == null) {
                    linearLayout4 = null;
                }
                ObjectAnimator ofFloat = ObjectAnimator.ofFloat(linearLayout4, (Property<LinearLayout, Float>) View.ALPHA, 1.0f, 0.0f);
                final SubscreenNotificationReplyActivity subscreenNotificationReplyActivity5 = SubscreenNotificationReplyActivity.this;
                ofFloat.setDuration(300L);
                ofFloat.addListener(new Animator.AnimatorListener() { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationReplyActivity$initView$4$1$onClick$lambda$1$$inlined$doOnEnd$1
                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator) {
                        SubscreenDeviceModelParent subscreenDeviceModelParent6;
                        if (!useHistory && (subscreenDeviceModelParent6 = subscreenNotificationReplyActivity5.controller.mDeviceModel) != null) {
                            subscreenDeviceModelParent6.hideDetailNotificationAnimated(0, true);
                        }
                        subscreenNotificationReplyActivity5.finish();
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationCancel(Animator animator) {
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationRepeat(Animator animator) {
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationStart(Animator animator) {
                    }
                });
                ofFloat.start();
                SystemUIEditText systemUIEditText3 = SubscreenNotificationReplyActivity.this.editText;
                if (systemUIEditText3 != null && (text = systemUIEditText3.getText()) != null) {
                    num = Integer.valueOf(text.length());
                }
                SystemUIAnalytics.sendEventCDLog("QPN102", "QPNE0210", "length", String.valueOf(num));
            }
        });
        this.sendButton = imageView;
        setPrevText();
        enableSendButton();
        SystemUIEditText systemUIEditText2 = this.editText;
        if (systemUIEditText2 != null) {
            systemUIEditText2.requestFocus();
        }
        subscreenNotificationController.replyActivity = this;
        ((DisplayLifecycle) Dependency.get(DisplayLifecycle.class)).addObserver(this.displayListener);
        ((CommandQueue) Dependency.get(CommandQueue.class)).addCallback((CommandQueue.Callbacks) this);
        registerReceiver(this.broadcastReceiver, new IntentFilter("android.intent.action.CLOSE_SYSTEM_DIALOGS"), 2);
    }

    @Override // android.app.Activity
    public final void onDestroy() {
        Editable editable;
        String valueOf;
        Log.d(TAG, "onDestroy()");
        NotificationEntry notificationEntry = this.entry;
        if (notificationEntry != null) {
            if (this.isSent) {
                valueOf = "";
            } else {
                SystemUIEditText systemUIEditText = this.editText;
                if (systemUIEditText != null) {
                    editable = systemUIEditText.getText();
                } else {
                    editable = null;
                }
                valueOf = String.valueOf(editable);
            }
            notificationEntry.remoteInputText = valueOf;
        }
        SubscreenSubRoomNotification subscreenSubRoomNotification = this.subRoomNoti;
        if (subscreenSubRoomNotification == null) {
            subscreenSubRoomNotification = null;
        }
        subscreenSubRoomNotification.mSubscreenMainLayout.setVisibility(0);
        SubscreenNotificationController subscreenNotificationController = this.controller;
        subscreenNotificationController.replyActivity = null;
        SubscreenDeviceModelParent subscreenDeviceModelParent = subscreenNotificationController.mDeviceModel;
        if (subscreenDeviceModelParent != null) {
            subscreenDeviceModelParent.replyActivityFinished(this.isForce);
        }
        ((DisplayLifecycle) Dependency.get(DisplayLifecycle.class)).removeObserver(this.displayListener);
        unregisterReceiver(this.broadcastReceiver);
        super.onDestroy();
    }

    @Override // android.app.Activity
    public final void onResume() {
        super.onResume();
    }

    @Override // android.app.Activity
    public final void onStop() {
        Toast toast = this.toast;
        if (toast != null) {
            toast.cancel();
        }
        super.onStop();
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void setImeWindowStatus(int i, IBinder iBinder, int i2, int i3, boolean z) {
        if ((i2 & 2) != 0) {
            SystemUIEditText systemUIEditText = this.editText;
            if (systemUIEditText != null) {
                systemUIEditText.setMaxLines(2);
                return;
            }
            return;
        }
        SystemUIEditText systemUIEditText2 = this.editText;
        if (systemUIEditText2 != null) {
            systemUIEditText2.setMaxLines(4);
        }
    }

    public final void setPrevText() {
        CharSequence charSequence = this.prevText;
        if (charSequence != null) {
            SystemUIEditText systemUIEditText = this.editText;
            if (systemUIEditText != null) {
                systemUIEditText.setText(charSequence);
            }
            SystemUIEditText systemUIEditText2 = this.editText;
            if (systemUIEditText2 != null) {
                systemUIEditText2.setSelection(charSequence.length());
            }
        }
    }
}
