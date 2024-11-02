package com.android.systemui.statusbar.phone;

import android.util.DisplayMetrics;
import androidx.constraintlayout.motion.widget.KeyAttributes$$ExternalSyntheticOutline0;
import androidx.core.animation.ValueAnimator$$ExternalSyntheticOutline0;
import com.android.internal.logging.UiEventLoggerImpl;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessage;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.phone.LockscreenGestureLogger;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class LSShadeTransitionLogger {
    public final LogBuffer buffer;
    public final DisplayMetrics displayMetrics;
    public final LockscreenGestureLogger lockscreenGestureLogger;

    public LSShadeTransitionLogger(LogBuffer logBuffer, LockscreenGestureLogger lockscreenGestureLogger, DisplayMetrics displayMetrics) {
        this.buffer = logBuffer;
        this.lockscreenGestureLogger = lockscreenGestureLogger;
        this.displayMetrics = displayMetrics;
    }

    public final void logAnimationCancelled(boolean z) {
        LogBuffer logBuffer = this.buffer;
        if (z) {
            logBuffer.commit(logBuffer.obtain("LockscreenShadeTransitionController", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.phone.LSShadeTransitionLogger$logAnimationCancelled$2
                @Override // kotlin.jvm.functions.Function1
                public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
                    return "Pulse animation cancelled";
                }
            }, null));
        } else {
            logBuffer.commit(logBuffer.obtain("LockscreenShadeTransitionController", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.phone.LSShadeTransitionLogger$logAnimationCancelled$4
                @Override // kotlin.jvm.functions.Function1
                public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
                    return "drag down animation cancelled";
                }
            }, null));
        }
    }

    public final void logDefaultGoToFullShadeAnimation(long j) {
        LogLevel logLevel = LogLevel.DEBUG;
        LSShadeTransitionLogger$logDefaultGoToFullShadeAnimation$2 lSShadeTransitionLogger$logDefaultGoToFullShadeAnimation$2 = new Function1() { // from class: com.android.systemui.statusbar.phone.LSShadeTransitionLogger$logDefaultGoToFullShadeAnimation$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ValueAnimator$$ExternalSyntheticOutline0.m("Default animation started to full shade with delay ", ((LogMessage) obj).getLong1());
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("LockscreenShadeTransitionController", logLevel, lSShadeTransitionLogger$logDefaultGoToFullShadeAnimation$2, null);
        obtain.setLong1(j);
        logBuffer.commit(obtain);
    }

    public final void logDragDownAborted() {
        LogLevel logLevel = LogLevel.INFO;
        LSShadeTransitionLogger$logDragDownAborted$2 lSShadeTransitionLogger$logDragDownAborted$2 = new Function1() { // from class: com.android.systemui.statusbar.phone.LSShadeTransitionLogger$logDragDownAborted$2
            @Override // kotlin.jvm.functions.Function1
            public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
                return "The drag down was aborted and reset to 0f.";
            }
        };
        LogBuffer logBuffer = this.buffer;
        logBuffer.commit(logBuffer.obtain("LockscreenShadeTransitionController", logLevel, lSShadeTransitionLogger$logDragDownAborted$2, null));
    }

    public final void logDragDownAmountReset() {
        LogLevel logLevel = LogLevel.DEBUG;
        LSShadeTransitionLogger$logDragDownAmountReset$2 lSShadeTransitionLogger$logDragDownAmountReset$2 = new Function1() { // from class: com.android.systemui.statusbar.phone.LSShadeTransitionLogger$logDragDownAmountReset$2
            @Override // kotlin.jvm.functions.Function1
            public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
                return "The drag down amount has been reset to 0f.";
            }
        };
        LogBuffer logBuffer = this.buffer;
        logBuffer.commit(logBuffer.obtain("LockscreenShadeTransitionController", logLevel, lSShadeTransitionLogger$logDragDownAmountReset$2, null));
    }

    public final void logDragDownAmountResetWhenFullyCollapsed() {
        LogLevel logLevel = LogLevel.WARNING;
        LSShadeTransitionLogger$logDragDownAmountResetWhenFullyCollapsed$2 lSShadeTransitionLogger$logDragDownAmountResetWhenFullyCollapsed$2 = new Function1() { // from class: com.android.systemui.statusbar.phone.LSShadeTransitionLogger$logDragDownAmountResetWhenFullyCollapsed$2
            @Override // kotlin.jvm.functions.Function1
            public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
                return "Drag down amount stuck and reset after shade was fully collapsed";
            }
        };
        LogBuffer logBuffer = this.buffer;
        logBuffer.commit(logBuffer.obtain("LockscreenShadeTransitionController", logLevel, lSShadeTransitionLogger$logDragDownAmountResetWhenFullyCollapsed$2, null));
    }

    public final void logDragDownAnimation(float f) {
        LogLevel logLevel = LogLevel.DEBUG;
        LSShadeTransitionLogger$logDragDownAnimation$2 lSShadeTransitionLogger$logDragDownAnimation$2 = new Function1() { // from class: com.android.systemui.statusbar.phone.LSShadeTransitionLogger$logDragDownAnimation$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return "Drag down amount animating to " + ((LogMessage) obj).getDouble1();
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("LockscreenShadeTransitionController", logLevel, lSShadeTransitionLogger$logDragDownAnimation$2, null);
        obtain.setDouble1(f);
        logBuffer.commit(obtain);
    }

    public final void logDragDownStarted(ExpandableView expandableView) {
        ExpandableNotificationRow expandableNotificationRow;
        NotificationEntry notificationEntry;
        String str;
        if (expandableView instanceof ExpandableNotificationRow) {
            expandableNotificationRow = (ExpandableNotificationRow) expandableView;
        } else {
            expandableNotificationRow = null;
        }
        if (expandableNotificationRow != null) {
            notificationEntry = expandableNotificationRow.mEntry;
        } else {
            notificationEntry = null;
        }
        LogLevel logLevel = LogLevel.INFO;
        LSShadeTransitionLogger$logDragDownStarted$2 lSShadeTransitionLogger$logDragDownStarted$2 = new Function1() { // from class: com.android.systemui.statusbar.phone.LSShadeTransitionLogger$logDragDownStarted$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return KeyAttributes$$ExternalSyntheticOutline0.m("The drag down has started on ", ((LogMessage) obj).getStr1());
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("LockscreenShadeTransitionController", logLevel, lSShadeTransitionLogger$logDragDownStarted$2, null);
        if (notificationEntry == null || (str = notificationEntry.mKey) == null) {
            str = "no entry";
        }
        obtain.setStr1(str);
        logBuffer.commit(obtain);
    }

    public final void logDraggedDown(ExpandableView expandableView, int i) {
        ExpandableNotificationRow expandableNotificationRow;
        NotificationEntry notificationEntry;
        String str;
        if (expandableView instanceof ExpandableNotificationRow) {
            expandableNotificationRow = (ExpandableNotificationRow) expandableView;
        } else {
            expandableNotificationRow = null;
        }
        if (expandableNotificationRow != null) {
            notificationEntry = expandableNotificationRow.mEntry;
        } else {
            notificationEntry = null;
        }
        LogLevel logLevel = LogLevel.INFO;
        LSShadeTransitionLogger$logDraggedDown$2 lSShadeTransitionLogger$logDraggedDown$2 = new Function1() { // from class: com.android.systemui.statusbar.phone.LSShadeTransitionLogger$logDraggedDown$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return KeyAttributes$$ExternalSyntheticOutline0.m("Drag down succeeded on ", ((LogMessage) obj).getStr1());
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("LockscreenShadeTransitionController", logLevel, lSShadeTransitionLogger$logDraggedDown$2, null);
        if (notificationEntry == null || (str = notificationEntry.mKey) == null) {
            str = "no entry";
        }
        obtain.setStr1(str);
        logBuffer.commit(obtain);
        this.lockscreenGestureLogger.write(187, (int) (i / this.displayMetrics.density), 0);
        new UiEventLoggerImpl().log(LockscreenGestureLogger.LockscreenUiEvent.LOCKSCREEN_PULL_SHADE_OPEN);
    }

    public final void logDraggedDownLockDownShade(ExpandableView expandableView) {
        ExpandableNotificationRow expandableNotificationRow;
        NotificationEntry notificationEntry;
        String str;
        if (expandableView instanceof ExpandableNotificationRow) {
            expandableNotificationRow = (ExpandableNotificationRow) expandableView;
        } else {
            expandableNotificationRow = null;
        }
        if (expandableNotificationRow != null) {
            notificationEntry = expandableNotificationRow.mEntry;
        } else {
            notificationEntry = null;
        }
        LogLevel logLevel = LogLevel.INFO;
        LSShadeTransitionLogger$logDraggedDownLockDownShade$2 lSShadeTransitionLogger$logDraggedDownLockDownShade$2 = new Function1() { // from class: com.android.systemui.statusbar.phone.LSShadeTransitionLogger$logDraggedDownLockDownShade$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return KeyAttributes$$ExternalSyntheticOutline0.m("Dragged down in locked down shade on ", ((LogMessage) obj).getStr1());
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("LockscreenShadeTransitionController", logLevel, lSShadeTransitionLogger$logDraggedDownLockDownShade$2, null);
        if (notificationEntry == null || (str = notificationEntry.mKey) == null) {
            str = "no entry";
        }
        obtain.setStr1(str);
        logBuffer.commit(obtain);
    }

    public final void logGoingToLockedShade(final boolean z) {
        LogLevel logLevel = LogLevel.INFO;
        Function1 function1 = new Function1() { // from class: com.android.systemui.statusbar.phone.LSShadeTransitionLogger$logGoingToLockedShade$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                String str;
                if (z) {
                    str = "with";
                } else {
                    str = "without a custom handler";
                }
                return "Going to locked shade ".concat(str);
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("LockscreenShadeTransitionController", logLevel, function1, null);
        obtain.setBool1(z);
        logBuffer.commit(obtain);
    }

    public final void logGoingToLockedShadeAborted() {
        LogLevel logLevel = LogLevel.INFO;
        LSShadeTransitionLogger$logGoingToLockedShadeAborted$2 lSShadeTransitionLogger$logGoingToLockedShadeAborted$2 = new Function1() { // from class: com.android.systemui.statusbar.phone.LSShadeTransitionLogger$logGoingToLockedShadeAborted$2
            @Override // kotlin.jvm.functions.Function1
            public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
                return "Going to the Locked Shade has been aborted";
            }
        };
        LogBuffer logBuffer = this.buffer;
        logBuffer.commit(logBuffer.obtain("LockscreenShadeTransitionController", logLevel, lSShadeTransitionLogger$logGoingToLockedShadeAborted$2, null));
    }

    public final void logOnHideKeyguard() {
        LogLevel logLevel = LogLevel.INFO;
        LSShadeTransitionLogger$logOnHideKeyguard$2 lSShadeTransitionLogger$logOnHideKeyguard$2 = new Function1() { // from class: com.android.systemui.statusbar.phone.LSShadeTransitionLogger$logOnHideKeyguard$2
            @Override // kotlin.jvm.functions.Function1
            public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
                return "Notified that the keyguard is being hidden";
            }
        };
        LogBuffer logBuffer = this.buffer;
        logBuffer.commit(logBuffer.obtain("LockscreenShadeTransitionController", logLevel, lSShadeTransitionLogger$logOnHideKeyguard$2, null));
    }

    public final void logPulseExpansionFinished(boolean z) {
        LogBuffer logBuffer = this.buffer;
        if (z) {
            logBuffer.commit(logBuffer.obtain("LockscreenShadeTransitionController", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.phone.LSShadeTransitionLogger$logPulseExpansionFinished$2
                @Override // kotlin.jvm.functions.Function1
                public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
                    return "Pulse Expansion is requested to cancel";
                }
            }, null));
        } else {
            logBuffer.commit(logBuffer.obtain("LockscreenShadeTransitionController", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.phone.LSShadeTransitionLogger$logPulseExpansionFinished$4
                @Override // kotlin.jvm.functions.Function1
                public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
                    return "Pulse Expansion is requested to finish";
                }
            }, null));
        }
    }

    public final void logPulseExpansionStarted() {
        LogLevel logLevel = LogLevel.INFO;
        LSShadeTransitionLogger$logPulseExpansionStarted$2 lSShadeTransitionLogger$logPulseExpansionStarted$2 = new Function1() { // from class: com.android.systemui.statusbar.phone.LSShadeTransitionLogger$logPulseExpansionStarted$2
            @Override // kotlin.jvm.functions.Function1
            public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
                return "Pulse Expansion has started";
            }
        };
        LogBuffer logBuffer = this.buffer;
        logBuffer.commit(logBuffer.obtain("LockscreenShadeTransitionController", logLevel, lSShadeTransitionLogger$logPulseExpansionStarted$2, null));
    }

    public final void logPulseHeightNotResetWhenFullyCollapsed() {
        LogLevel logLevel = LogLevel.WARNING;
        LSShadeTransitionLogger$logPulseHeightNotResetWhenFullyCollapsed$2 lSShadeTransitionLogger$logPulseHeightNotResetWhenFullyCollapsed$2 = new Function1() { // from class: com.android.systemui.statusbar.phone.LSShadeTransitionLogger$logPulseHeightNotResetWhenFullyCollapsed$2
            @Override // kotlin.jvm.functions.Function1
            public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
                return "Pulse height stuck and reset after shade was fully collapsed";
            }
        };
        LogBuffer logBuffer = this.buffer;
        logBuffer.commit(logBuffer.obtain("LockscreenShadeTransitionController", logLevel, lSShadeTransitionLogger$logPulseHeightNotResetWhenFullyCollapsed$2, null));
    }

    public final void logShadeDisabledOnGoToLockedShade() {
        LogLevel logLevel = LogLevel.WARNING;
        LSShadeTransitionLogger$logShadeDisabledOnGoToLockedShade$2 lSShadeTransitionLogger$logShadeDisabledOnGoToLockedShade$2 = new Function1() { // from class: com.android.systemui.statusbar.phone.LSShadeTransitionLogger$logShadeDisabledOnGoToLockedShade$2
            @Override // kotlin.jvm.functions.Function1
            public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
                return "The shade was disabled when trying to go to the locked shade";
            }
        };
        LogBuffer logBuffer = this.buffer;
        logBuffer.commit(logBuffer.obtain("LockscreenShadeTransitionController", logLevel, lSShadeTransitionLogger$logShadeDisabledOnGoToLockedShade$2, null));
    }

    public final void logShowBouncerOnGoToLockedShade() {
        LogLevel logLevel = LogLevel.INFO;
        LSShadeTransitionLogger$logShowBouncerOnGoToLockedShade$2 lSShadeTransitionLogger$logShowBouncerOnGoToLockedShade$2 = new Function1() { // from class: com.android.systemui.statusbar.phone.LSShadeTransitionLogger$logShowBouncerOnGoToLockedShade$2
            @Override // kotlin.jvm.functions.Function1
            public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
                return "Showing bouncer when trying to go to the locked shade";
            }
        };
        LogBuffer logBuffer = this.buffer;
        logBuffer.commit(logBuffer.obtain("LockscreenShadeTransitionController", logLevel, lSShadeTransitionLogger$logShowBouncerOnGoToLockedShade$2, null));
    }

    public final void logTryGoToLockedShade(boolean z) {
        LogLevel logLevel = LogLevel.INFO;
        LSShadeTransitionLogger$logTryGoToLockedShade$2 lSShadeTransitionLogger$logTryGoToLockedShade$2 = new Function1() { // from class: com.android.systemui.statusbar.phone.LSShadeTransitionLogger$logTryGoToLockedShade$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                String str;
                if (((LogMessage) obj).getBool1()) {
                    str = "from keyguard";
                } else {
                    str = "not from keyguard";
                }
                return "Trying to go to locked shade ".concat(str);
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("LockscreenShadeTransitionController", logLevel, lSShadeTransitionLogger$logTryGoToLockedShade$2, null);
        obtain.setBool1(z);
        logBuffer.commit(obtain);
    }

    public final void logUnSuccessfulDragDown(ExpandableView expandableView) {
        ExpandableNotificationRow expandableNotificationRow;
        NotificationEntry notificationEntry;
        String str;
        if (expandableView instanceof ExpandableNotificationRow) {
            expandableNotificationRow = (ExpandableNotificationRow) expandableView;
        } else {
            expandableNotificationRow = null;
        }
        if (expandableNotificationRow != null) {
            notificationEntry = expandableNotificationRow.mEntry;
        } else {
            notificationEntry = null;
        }
        LogLevel logLevel = LogLevel.INFO;
        LSShadeTransitionLogger$logUnSuccessfulDragDown$2 lSShadeTransitionLogger$logUnSuccessfulDragDown$2 = new Function1() { // from class: com.android.systemui.statusbar.phone.LSShadeTransitionLogger$logUnSuccessfulDragDown$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return KeyAttributes$$ExternalSyntheticOutline0.m("Tried to drag down but can't drag down on ", ((LogMessage) obj).getStr1());
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("LockscreenShadeTransitionController", logLevel, lSShadeTransitionLogger$logUnSuccessfulDragDown$2, null);
        if (notificationEntry == null || (str = notificationEntry.mKey) == null) {
            str = "no entry";
        }
        obtain.setStr1(str);
        logBuffer.commit(obtain);
    }
}
