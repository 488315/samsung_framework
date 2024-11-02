package com.android.systemui.statusbar.notification.collection.coordinator;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.NotiRune;
import com.android.systemui.facewidget.plugin.FaceWidgetContainerWrapper;
import com.android.systemui.pluginlock.PluginLockMediator;
import com.android.systemui.pluginlock.PluginLockMediatorImpl;
import com.android.systemui.pluginlock.listener.PluginLockListener$State;
import com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.LockscreenNotificationInfo;
import com.android.systemui.statusbar.LockscreenNotificationManager;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.notification.SubscreenNotificationController;
import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeFinalizeFilterListener;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeRenderListListener;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter;
import com.android.systemui.statusbar.notification.collection.render.GroupMembershipManager;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.DesktopManager;
import com.android.systemui.util.DesktopManagerImpl;
import com.android.systemui.util.SettingsHelper;
import com.android.wm.shell.bubbles.BubbleController;
import com.android.wm.shell.bubbles.Bubbles;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class LockScreenNotiIconCoordinator implements Coordinator {
    public final Optional mBubblesOptional;
    public boolean mHasVisibleNotificationOnKeyguard;
    public final LockscreenNotificationManager mLockscreenNotificationManager;
    public final NotificationLockscreenUserManager mLockscreenUserManager;
    public final AnonymousClass1 mNotifFilter = new NotifFilter(this, "LockScreenNotiIconCoordinator") { // from class: com.android.systemui.statusbar.notification.collection.coordinator.LockScreenNotiIconCoordinator.1
        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter
        public final boolean shouldFilterOut(NotificationEntry notificationEntry, long j) {
            return false;
        }
    };
    public ArrayList mNotificationInfoArray;
    public final AnonymousClass2 mPluginLockListener;
    public int mPluginLockMode;
    public final SettingsHelper mSettingsHelper;
    public final SysuiStatusBarStateController mStatusBarStateController;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v1, types: [com.android.systemui.statusbar.notification.collection.coordinator.LockScreenNotiIconCoordinator$1] */
    /* JADX WARN: Type inference failed for: r2v2, types: [com.android.systemui.pluginlock.listener.PluginLockListener$State, com.android.systemui.statusbar.notification.collection.coordinator.LockScreenNotiIconCoordinator$2] */
    public LockScreenNotiIconCoordinator(LockscreenNotificationManager lockscreenNotificationManager, KeyguardStateController keyguardStateController, NotificationLockscreenUserManager notificationLockscreenUserManager, GroupMembershipManager groupMembershipManager, PluginLockMediator pluginLockMediator, StatusBarStateController statusBarStateController, SettingsHelper settingsHelper, Optional<Bubbles> optional) {
        ?? r2 = new PluginLockListener$State() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.LockScreenNotiIconCoordinator.2
            @Override // com.android.systemui.pluginlock.listener.PluginLockListener$State
            public final void onViewModeChanged(int i) {
                ListPopupWindow$$ExternalSyntheticOutline0.m("onViewModeChanged, mode: ", i, "LockScreenNotiIconCoordinator");
                LockScreenNotiIconCoordinator lockScreenNotiIconCoordinator = LockScreenNotiIconCoordinator.this;
                lockScreenNotiIconCoordinator.mPluginLockMode = i;
                lockScreenNotiIconCoordinator.mNotifFilter.invalidateList("LockScreenNotiStateChanged");
            }
        };
        this.mPluginLockListener = r2;
        this.mLockscreenNotificationManager = lockscreenNotificationManager;
        this.mLockscreenUserManager = notificationLockscreenUserManager;
        lockscreenNotificationManager.getClass();
        this.mNotificationInfoArray = new ArrayList();
        this.mStatusBarStateController = (SysuiStatusBarStateController) statusBarStateController;
        this.mSettingsHelper = settingsHelper;
        ((PluginLockMediatorImpl) pluginLockMediator).registerStateCallback(r2);
        if (NotiRune.NOTI_SUBSCREEN_SUPPORT_NOTIFICATION_HISTORY) {
            this.mBubblesOptional = optional;
        }
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public final void attach(NotifPipeline notifPipeline) {
        notifPipeline.addOnBeforeFinalizeFilterListener(new OnBeforeFinalizeFilterListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.LockScreenNotiIconCoordinator$$ExternalSyntheticLambda0
            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeFinalizeFilterListener
            public final void onBeforeFinalizeFilter(List list) {
                LockScreenNotiIconCoordinator lockScreenNotiIconCoordinator = LockScreenNotiIconCoordinator.this;
                lockScreenNotiIconCoordinator.mLockscreenNotificationManager.getClass();
                lockScreenNotiIconCoordinator.mNotificationInfoArray = new ArrayList();
            }
        });
        notifPipeline.addOnBeforeRenderListListener(new OnBeforeRenderListListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.LockScreenNotiIconCoordinator$$ExternalSyntheticLambda1
            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeRenderListListener
            public final void onBeforeRenderList(List list) {
                boolean z;
                boolean z2;
                boolean z3;
                boolean z4;
                boolean z5;
                boolean z6;
                LockScreenNotiIconCoordinator lockScreenNotiIconCoordinator = LockScreenNotiIconCoordinator.this;
                LockscreenNotificationManager lockscreenNotificationManager = lockScreenNotiIconCoordinator.mLockscreenNotificationManager;
                boolean z7 = lockscreenNotificationManager.mIsCovered;
                boolean z8 = lockscreenNotificationManager.mIsFolded;
                boolean z9 = true;
                if (((StatusBarStateControllerImpl) lockScreenNotiIconCoordinator.mStatusBarStateController).mUpcomingState == 1) {
                    z = true;
                } else {
                    z = false;
                }
                if (!z && !z7 && !z8) {
                    z2 = false;
                } else {
                    z2 = true;
                }
                if (NotiRune.NOTI_SUBSCREEN_NOTIFICATION_COMMON && z8) {
                    ((SubscreenNotificationController) Dependency.get(SubscreenNotificationController.class)).mDeviceModel.setKeyguardStateWhenAddLockscreenNotificationInfoArray(z);
                }
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    ListEntry listEntry = (ListEntry) it.next();
                    NotificationEntry representativeEntry = listEntry.getRepresentativeEntry();
                    ExpandableNotificationRow expandableNotificationRow = representativeEntry.row;
                    if (lockScreenNotiIconCoordinator.mPluginLockMode == 1) {
                        expandableNotificationRow.setVisibility(8);
                    } else if (z7 && !((NotificationLockscreenUserManagerImpl) lockScreenNotiIconCoordinator.mLockscreenUserManager).mShowLockscreenNotifications) {
                        expandableNotificationRow.setVisibility(8);
                    } else {
                        if (z2) {
                            expandableNotificationRow.getClass();
                            if (((ArrayList) representativeEntry.mDismissInterceptors).size() > 0) {
                                z6 = true;
                            } else {
                                z6 = false;
                            }
                            if (!z6 && NotiRune.NOTI_SUBSCREEN_SUPPORT_NOTIFICATION_HISTORY) {
                                Optional optional = lockScreenNotiIconCoordinator.mBubblesOptional;
                                if (optional.isPresent()) {
                                    Bubbles bubbles = (Bubbles) optional.get();
                                    String groupKey = representativeEntry.mSbn.getGroupKey();
                                    String str = representativeEntry.mKey;
                                    if (!((BubbleController.BubblesImpl) bubbles).isBubbleNotificationSuppressedFromShade(str, groupKey)) {
                                        ArrayList arrayList = lockScreenNotiIconCoordinator.mNotificationInfoArray;
                                        LockscreenNotificationInfo lockscreenNotificationInfo = new LockscreenNotificationInfo();
                                        lockscreenNotificationInfo.mStatusBarIcon = representativeEntry.mIcons.mStatusBarIcon;
                                        lockscreenNotificationInfo.mSbn = representativeEntry.mSbn;
                                        lockscreenNotificationInfo.mKey = str;
                                        lockscreenNotificationInfo.mRow = representativeEntry.row;
                                        arrayList.add(lockscreenNotificationInfo);
                                    }
                                }
                            }
                        }
                        if (z) {
                            int i = lockscreenNotificationManager.mCurrentNotificationType;
                            if (i != 1 && i != 2) {
                                z4 = false;
                            } else {
                                z4 = true;
                            }
                            if (!z4) {
                                if (lockScreenNotiIconCoordinator.mSettingsHelper.mItemLists.get("lock_screen_show_notifications_on_keyguard").getIntValue() == 1) {
                                    z5 = true;
                                } else {
                                    z5 = false;
                                }
                                if (z5 && !((DesktopManagerImpl) ((DesktopManager) Dependency.get(DesktopManager.class))).isStandalone()) {
                                }
                            }
                            expandableNotificationRow.setVisibility(8);
                        }
                        lockScreenNotiIconCoordinator.restoreVisibility(listEntry);
                    }
                }
                if (!lockScreenNotiIconCoordinator.mHasVisibleNotificationOnKeyguard) {
                    lockScreenNotiIconCoordinator.mHasVisibleNotificationOnKeyguard = !list.isEmpty();
                }
                LockscreenNotificationManager lockscreenNotificationManager2 = lockScreenNotiIconCoordinator.mLockscreenNotificationManager;
                ArrayList arrayList2 = lockScreenNotiIconCoordinator.mNotificationInfoArray;
                synchronized (lockscreenNotificationManager2.mLock) {
                    lockscreenNotificationManager2.mHandler.removeMessages(101);
                    lockscreenNotificationManager2.mHandler.obtainMessage(101, 0, 0, arrayList2).sendToTarget();
                }
                if (((StatusBarStateControllerImpl) lockScreenNotiIconCoordinator.mStatusBarStateController).mUpcomingState == 1) {
                    LockscreenNotificationManager lockscreenNotificationManager3 = lockScreenNotiIconCoordinator.mLockscreenNotificationManager;
                    boolean z10 = !lockScreenNotiIconCoordinator.mHasVisibleNotificationOnKeyguard;
                    int size = lockScreenNotiIconCoordinator.mNotificationInfoArray.size();
                    FaceWidgetContainerWrapper faceWidgetContainerWrapper = lockscreenNotificationManager3.mKeyguardStatusBase;
                    if (faceWidgetContainerWrapper != null) {
                        int i2 = lockscreenNotificationManager3.mCurrentNotificationType;
                        if (i2 != 1 && i2 != 2) {
                            z3 = false;
                        } else {
                            z3 = true;
                        }
                        if (!z3) {
                            z9 = z10;
                        }
                        PluginKeyguardStatusView pluginKeyguardStatusView = faceWidgetContainerWrapper.mPluginKeyguardStatusView;
                        if (pluginKeyguardStatusView != null) {
                            pluginKeyguardStatusView.setExpandState(z9, size);
                        }
                    }
                    lockScreenNotiIconCoordinator.mHasVisibleNotificationOnKeyguard = false;
                }
            }
        });
        notifPipeline.addFinalizeFilter(this.mNotifFilter);
        this.mLockscreenNotificationManager.mLockScreenNotificationStateListener = this;
    }

    public final void restoreVisibility(ListEntry listEntry) {
        ExpandableNotificationRow expandableNotificationRow = listEntry.getRepresentativeEntry().row;
        if (expandableNotificationRow.getVisibility() == 8) {
            expandableNotificationRow.setVisibility(0);
        }
        if (listEntry instanceof GroupEntry) {
            ((GroupEntry) listEntry).mUnmodifiableChildren.stream().forEach(new Consumer() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.LockScreenNotiIconCoordinator$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    LockScreenNotiIconCoordinator.this.restoreVisibility((NotificationEntry) obj);
                }
            });
        }
    }
}
