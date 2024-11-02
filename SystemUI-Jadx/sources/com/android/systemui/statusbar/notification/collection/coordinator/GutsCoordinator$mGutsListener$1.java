package com.android.systemui.statusbar.notification.collection.coordinator;

import androidx.constraintlayout.motion.widget.KeyAttributes$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessage;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.NotificationGuts;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Reflection;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class GutsCoordinator$mGutsListener$1 {
    public final /* synthetic */ GutsCoordinator this$0;

    public GutsCoordinator$mGutsListener$1(GutsCoordinator gutsCoordinator) {
        this.this$0 = gutsCoordinator;
    }

    public final void onGutsClose(NotificationEntry notificationEntry) {
        GutsCoordinator gutsCoordinator = this.this$0;
        GutsCoordinatorLogger gutsCoordinatorLogger = gutsCoordinator.logger;
        String str = notificationEntry.mKey;
        gutsCoordinatorLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        GutsCoordinatorLogger$logGutsClosed$2 gutsCoordinatorLogger$logGutsClosed$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.GutsCoordinatorLogger$logGutsClosed$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return KeyAttributes$$ExternalSyntheticOutline0.m("Guts closed for class ", ((LogMessage) obj).getStr1());
            }
        };
        LogBuffer logBuffer = gutsCoordinatorLogger.buffer;
        LogMessage obtain = logBuffer.obtain("GutsCoordinator", logLevel, gutsCoordinatorLogger$logGutsClosed$2, null);
        obtain.setStr1(str);
        logBuffer.commit(obtain);
        GutsCoordinator.access$closeGutsAndEndLifetimeExtension(gutsCoordinator, notificationEntry);
    }

    public final void onGutsOpen(NotificationEntry notificationEntry, NotificationGuts notificationGuts) {
        boolean z;
        GutsCoordinator gutsCoordinator = this.this$0;
        GutsCoordinatorLogger gutsCoordinatorLogger = gutsCoordinator.logger;
        String str = notificationEntry.mKey;
        gutsCoordinatorLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        GutsCoordinatorLogger$logGutsOpened$2 gutsCoordinatorLogger$logGutsOpened$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.GutsCoordinatorLogger$logGutsOpened$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return "Guts of type " + logMessage.getStr2() + " (leave behind: " + logMessage.getBool1() + ") opened for class " + logMessage.getStr1();
            }
        };
        LogBuffer logBuffer = gutsCoordinatorLogger.buffer;
        LogMessage obtain = logBuffer.obtain("GutsCoordinator", logLevel, gutsCoordinatorLogger$logGutsOpened$2, null);
        obtain.setStr1(str);
        obtain.setStr2(Reflection.getOrCreateKotlinClass(notificationGuts.mGutsContent.getClass()).getSimpleName());
        NotificationGuts.GutsContent gutsContent = notificationGuts.mGutsContent;
        boolean z2 = true;
        if (gutsContent != null && gutsContent.isLeavebehind()) {
            z = true;
        } else {
            z = false;
        }
        obtain.setBool1(z);
        logBuffer.commit(obtain);
        NotificationGuts.GutsContent gutsContent2 = notificationGuts.mGutsContent;
        if (gutsContent2 == null || !gutsContent2.isLeavebehind()) {
            z2 = false;
        }
        if (z2) {
            GutsCoordinator.access$closeGutsAndEndLifetimeExtension(gutsCoordinator, notificationEntry);
        } else {
            gutsCoordinator.notifsWithOpenGuts.add(str);
        }
    }
}
