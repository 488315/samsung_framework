package com.android.systemui.statusbar.policy;

import android.app.ActivityOptions;
import android.app.INotificationManager;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.Intent;
import android.content.pm.ShortcutManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Log;
import android.view.ContentInfo;
import android.view.View;
import androidx.appcompat.app.ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0;
import com.android.internal.logging.InstanceId;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.NotiRune;
import com.android.systemui.R;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.plugins.subscreen.SubRoom;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.NotificationRemoteInputManager$$ExternalSyntheticLambda1;
import com.android.systemui.statusbar.RemoteInputController;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.policy.RemoteInputView;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;
import kotlin.collections.CollectionsKt___CollectionsKt;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class RemoteInputViewControllerImpl implements RemoteInputViewController {
    public NotificationRemoteInputManager$$ExternalSyntheticLambda1 bouncerChecker;
    public final NotificationEntry entry;
    public boolean isBound;
    public final FeatureFlags mFlags;
    public final INotificationManager notifManager;
    public final RemoteInputViewControllerImpl$onFocusChangeListener$1 onFocusChangeListener;
    public final ArraySet onSendListeners = new ArraySet();
    public final RemoteInputViewControllerImpl$onSendRemoteInputListener$1 onSendRemoteInputListener;
    public PendingIntent pendingIntent;
    public RemoteInput remoteInput;
    public final RemoteInputController remoteInputController;
    public final RemoteInputQuickSettingsDisabler remoteInputQuickSettingsDisabler;
    public RemoteInput[] remoteInputs;
    public RemoteInputView.RevealParams revealParams;
    public final ShortcutManager shortcutManager;
    public final UiEventLogger uiEventLogger;
    public final RemoteInputView view;

    /* JADX WARN: Type inference failed for: r1v5, types: [com.android.systemui.statusbar.policy.RemoteInputViewControllerImpl$onFocusChangeListener$1] */
    /* JADX WARN: Type inference failed for: r1v6, types: [com.android.systemui.statusbar.policy.RemoteInputViewControllerImpl$onSendRemoteInputListener$1] */
    public RemoteInputViewControllerImpl(RemoteInputView remoteInputView, NotificationEntry notificationEntry, RemoteInputQuickSettingsDisabler remoteInputQuickSettingsDisabler, RemoteInputController remoteInputController, ShortcutManager shortcutManager, UiEventLogger uiEventLogger, FeatureFlags featureFlags) {
        INotificationManager iNotificationManager;
        this.view = remoteInputView;
        this.entry = notificationEntry;
        this.remoteInputQuickSettingsDisabler = remoteInputQuickSettingsDisabler;
        this.remoteInputController = remoteInputController;
        this.shortcutManager = shortcutManager;
        this.uiEventLogger = uiEventLogger;
        this.mFlags = featureFlags;
        if (NotiRune.NOTI_SUBSCREEN_SUPPORT_NOTIFICATION_HISTORY) {
            iNotificationManager = INotificationManager.Stub.asInterface(ServiceManager.getService(SubRoom.EXTRA_VALUE_NOTIFICATION));
        } else {
            iNotificationManager = null;
        }
        this.notifManager = iNotificationManager;
        this.onFocusChangeListener = new View.OnFocusChangeListener() { // from class: com.android.systemui.statusbar.policy.RemoteInputViewControllerImpl$onFocusChangeListener$1
            @Override // android.view.View.OnFocusChangeListener
            public final void onFocusChange(View view, boolean z) {
                RemoteInputQuickSettingsDisabler remoteInputQuickSettingsDisabler2 = RemoteInputViewControllerImpl.this.remoteInputQuickSettingsDisabler;
                if (remoteInputQuickSettingsDisabler2.remoteInputActive != z) {
                    remoteInputQuickSettingsDisabler2.remoteInputActive = z;
                }
            }
        };
        this.onSendRemoteInputListener = new Runnable() { // from class: com.android.systemui.statusbar.policy.RemoteInputViewControllerImpl$onSendRemoteInputListener$1
            @Override // java.lang.Runnable
            public final void run() {
                int i;
                Intent intent;
                INotificationManager iNotificationManager2;
                int i2;
                RemoteInputViewControllerImpl remoteInputViewControllerImpl = RemoteInputViewControllerImpl.this;
                RemoteInput remoteInput = remoteInputViewControllerImpl.remoteInput;
                if (remoteInput == null) {
                    Log.e("RemoteInput", "cannot send remote input, RemoteInput data is null");
                    return;
                }
                PendingIntent pendingIntent = remoteInputViewControllerImpl.pendingIntent;
                if (pendingIntent == null) {
                    Log.e("RemoteInput", "cannot send remote input, PendingIntent is null");
                    return;
                }
                NotificationEntry notificationEntry2 = remoteInputViewControllerImpl.entry;
                ContentInfo contentInfo = notificationEntry2.remoteInputAttachment;
                RemoteInputView remoteInputView2 = remoteInputViewControllerImpl.view;
                if (contentInfo == null) {
                    Bundle bundle = new Bundle();
                    bundle.putString(remoteInput.getResultKey(), remoteInputView2.getText().toString());
                    Intent addFlags = new Intent().addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
                    RemoteInput.addResultsToIntent(remoteInputViewControllerImpl.remoteInputs, addFlags, bundle);
                    notificationEntry2.remoteInputText = remoteInputView2.getText();
                    remoteInputView2.setAttachment(null);
                    notificationEntry2.remoteInputUri = null;
                    notificationEntry2.remoteInputMimeType = null;
                    if (notificationEntry2.editedSuggestionInfo != null) {
                        i2 = 1;
                    } else {
                        i2 = 0;
                    }
                    RemoteInput.setResultsSource(addFlags, i2);
                    intent = addFlags;
                } else {
                    String str = notificationEntry2.remoteInputMimeType;
                    Uri uri = notificationEntry2.remoteInputUri;
                    HashMap hashMap = new HashMap();
                    hashMap.put(str, uri);
                    StatusBarNotification statusBarNotification = notificationEntry2.mSbn;
                    RemoteInputUriController remoteInputUriController = remoteInputViewControllerImpl.remoteInputController.mRemoteInputUriController;
                    remoteInputUriController.getClass();
                    try {
                        remoteInputUriController.mStatusBarManagerService.grantInlineReplyUriPermission(statusBarNotification.getKey(), uri, statusBarNotification.getUser(), statusBarNotification.getPackageName());
                    } catch (Exception e) {
                        Log.e("RemoteInputUriController", "Failed to grant URI permissions:" + e.getMessage(), e);
                    }
                    Intent addFlags2 = new Intent().addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
                    RemoteInput.addDataResultToIntent(remoteInput, addFlags2, hashMap);
                    Bundle bundle2 = new Bundle();
                    bundle2.putString(remoteInput.getResultKey(), remoteInputView2.getText().toString());
                    RemoteInput.addResultsToIntent(remoteInputViewControllerImpl.remoteInputs, addFlags2, bundle2);
                    CharSequence label = notificationEntry2.remoteInputAttachment.getClip().getDescription().getLabel();
                    if (TextUtils.isEmpty(label)) {
                        label = remoteInputView2.getResources().getString(R.string.remote_input_image_insertion_text);
                    }
                    if (!TextUtils.isEmpty(remoteInputView2.getText())) {
                        label = "\"" + ((Object) label) + "\" " + ((Object) remoteInputView2.getText());
                    }
                    notificationEntry2.remoteInputText = label;
                    if (notificationEntry2.editedSuggestionInfo != null) {
                        i = 1;
                    } else {
                        i = 0;
                    }
                    RemoteInput.setResultsSource(addFlags2, i);
                    intent = addFlags2;
                }
                RemoteInputViewControllerImpl remoteInputViewControllerImpl2 = RemoteInputViewControllerImpl.this;
                NotificationRemoteInputManager$$ExternalSyntheticLambda1 notificationRemoteInputManager$$ExternalSyntheticLambda1 = remoteInputViewControllerImpl2.bouncerChecker;
                if (notificationRemoteInputManager$$ExternalSyntheticLambda1 == null) {
                    RemoteInputView remoteInputView3 = remoteInputViewControllerImpl2.view;
                    remoteInputView3.mEditText.setEnabled(false);
                    remoteInputView3.mSending = true;
                    remoteInputView3.mSendButton.setVisibility(4);
                    remoteInputView3.mProgressBar.setVisibility(0);
                    remoteInputView3.mEditText.mShowImeOnInputConnection = false;
                    long elapsedRealtime = SystemClock.elapsedRealtime();
                    NotificationEntry notificationEntry3 = remoteInputViewControllerImpl2.entry;
                    notificationEntry3.lastRemoteInputSent = elapsedRealtime;
                    notificationEntry3.mRemoteEditImeAnimatingAway = true;
                    Object obj = remoteInputView3.mToken;
                    RemoteInputController remoteInputController2 = remoteInputViewControllerImpl2.remoteInputController;
                    remoteInputController2.getClass();
                    String str2 = notificationEntry3.mKey;
                    Objects.requireNonNull(str2);
                    Objects.requireNonNull(obj);
                    remoteInputController2.mSpinning.put(str2, obj);
                    remoteInputController2.removeRemoteInput(notificationEntry3, remoteInputView3.mToken);
                    ArrayList arrayList = remoteInputController2.mCallbacks;
                    int size = arrayList.size();
                    for (int i3 = 0; i3 < size; i3++) {
                        ((RemoteInputController.Callback) arrayList.get(i3)).onRemoteInputSent(notificationEntry3);
                    }
                    notificationEntry3.hasSentReply = true;
                    Iterator it = CollectionsKt___CollectionsKt.toList(remoteInputViewControllerImpl2.onSendListeners).iterator();
                    if (!it.hasNext()) {
                        remoteInputViewControllerImpl2.shortcutManager.onApplicationActive(notificationEntry3.mSbn.getPackageName(), notificationEntry3.mSbn.getUser().getIdentifier());
                        RemoteInputView.NotificationRemoteInputEvent notificationRemoteInputEvent = RemoteInputView.NotificationRemoteInputEvent.NOTIFICATION_REMOTE_INPUT_SEND;
                        int uid = notificationEntry3.mSbn.getUid();
                        String packageName = notificationEntry3.mSbn.getPackageName();
                        InstanceId instanceId = notificationEntry3.mSbn.getInstanceId();
                        UiEventLogger uiEventLogger2 = remoteInputViewControllerImpl2.uiEventLogger;
                        uiEventLogger2.logWithInstanceId(notificationRemoteInputEvent, uid, packageName, instanceId);
                        try {
                            if (NotiRune.NOTI_SUBSCREEN_SUPPORT_NOTIFICATION_HISTORY && (iNotificationManager2 = remoteInputViewControllerImpl2.notifManager) != null) {
                                iNotificationManager2.addReplyHistory(1, notificationEntry3.mKey, notificationEntry3.mSbn.getPackageName(), notificationEntry3.mSbn.getUser().getIdentifier(), "NOUI_2023", remoteInputView3.getText().toString());
                            }
                            ActivityOptions makeBasic = ActivityOptions.makeBasic();
                            makeBasic.setPendingIntentBackgroundActivityStartMode(1);
                            pendingIntent.send(remoteInputView3.getContext(), 0, intent, null, null, null, makeBasic.toBundle());
                        } catch (PendingIntent.CanceledException e2) {
                            Log.i("RemoteInput", "Unable to send remote input result", e2);
                            uiEventLogger2.logWithInstanceId(RemoteInputView.NotificationRemoteInputEvent.NOTIFICATION_REMOTE_INPUT_FAILURE, notificationEntry3.mSbn.getUid(), notificationEntry3.mSbn.getPackageName(), notificationEntry3.mSbn.getInstanceId());
                        } catch (Exception unused) {
                        }
                        remoteInputView3.setAttachment(null);
                        RemoteInputQuickSettingsDisabler remoteInputQuickSettingsDisabler2 = remoteInputViewControllerImpl2.remoteInputQuickSettingsDisabler;
                        boolean z = remoteInputQuickSettingsDisabler2.remoteInputActive;
                        if (z && z) {
                            remoteInputQuickSettingsDisabler2.remoteInputActive = false;
                            return;
                        }
                        return;
                    }
                    ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(it.next());
                    throw null;
                }
                NotificationRemoteInputManager notificationRemoteInputManager = (NotificationRemoteInputManager) notificationRemoteInputManager$$ExternalSyntheticLambda1.f$0;
                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(notificationRemoteInputManager$$ExternalSyntheticLambda1.f$1);
                notificationRemoteInputManager.getClass();
                throw null;
            }
        };
    }

    public final void bind() {
        if (this.isBound) {
            return;
        }
        this.isBound = true;
        RemoteInput remoteInput = this.remoteInput;
        RemoteInputView remoteInputView = this.view;
        if (remoteInput != null) {
            remoteInputView.mEditText.setHint(remoteInput.getLabel());
            remoteInputView.setSupportedMimeTypes(remoteInput.getAllowedDataTypes());
            remoteInputView.updateRemoteInputLimitToastResources(remoteInput);
        }
        remoteInputView.mRevealParams = this.revealParams;
        Flags flags = Flags.INSTANCE;
        this.mFlags.getClass();
        remoteInputView.mIsFocusAnimationFlagActive = false;
        remoteInputView.mEditTextFocusChangeListeners.add(this.onFocusChangeListener);
        remoteInputView.mOnSendListeners.add(this.onSendRemoteInputListener);
    }

    public final void setRemoteInput(RemoteInput remoteInput) {
        this.remoteInput = remoteInput;
        if (remoteInput != null) {
            if (!this.isBound) {
                remoteInput = null;
            }
            if (remoteInput != null) {
                CharSequence label = remoteInput.getLabel();
                RemoteInputView remoteInputView = this.view;
                remoteInputView.mEditText.setHint(label);
                remoteInputView.setSupportedMimeTypes(remoteInput.getAllowedDataTypes());
                remoteInputView.updateRemoteInputLimitToastResources(remoteInput);
            }
        }
    }

    public final void stealFocusFrom(RemoteInputViewController remoteInputViewController) {
        RemoteInputViewControllerImpl remoteInputViewControllerImpl = (RemoteInputViewControllerImpl) remoteInputViewController;
        RemoteInputView.RemoteEditText remoteEditText = remoteInputViewControllerImpl.view.mEditText;
        int i = RemoteInputView.RemoteEditText.$r8$clinit;
        remoteEditText.defocusIfNeeded(false);
        setRemoteInput(remoteInputViewControllerImpl.remoteInput);
        this.remoteInputs = remoteInputViewControllerImpl.remoteInputs;
        RemoteInputView.RevealParams revealParams = remoteInputViewControllerImpl.revealParams;
        this.revealParams = revealParams;
        boolean z = this.isBound;
        RemoteInputView remoteInputView = this.view;
        if (z) {
            remoteInputView.mRevealParams = revealParams;
        }
        this.pendingIntent = remoteInputViewControllerImpl.pendingIntent;
        remoteInputView.focus();
    }

    public final void unbind() {
        if (!this.isBound) {
            return;
        }
        this.isBound = false;
        RemoteInputView remoteInputView = this.view;
        remoteInputView.mEditTextFocusChangeListeners.remove(this.onFocusChangeListener);
        remoteInputView.mOnSendListeners.remove(this.onSendRemoteInputListener);
    }
}
