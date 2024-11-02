package com.android.systemui.broadcast.logging;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$logAssistantVisible$2$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt__SequencesKt;
import kotlin.sequences.SequencesKt___SequencesKt;
import kotlin.text.StringsKt__IndentKt;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class BroadcastDispatcherLogger {
    public static final Companion Companion = new Companion(null);
    public final LogBuffer buffer;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static String flagToString(int i) {
            boolean z;
            StringBuilder sb = new StringBuilder("");
            if ((i & 1) != 0) {
                sb.append("instant_apps,");
            }
            if ((i & 4) != 0) {
                sb.append("not_exported,");
            }
            if ((i & 2) != 0) {
                sb.append("exported");
            }
            if (sb.length() == 0) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                sb.append(i);
            }
            return sb.toString();
        }
    }

    public BroadcastDispatcherLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }

    public final void logBroadcastDispatched(int i, String str, BroadcastReceiver broadcastReceiver) {
        String broadcastReceiver2 = broadcastReceiver.toString();
        LogLevel logLevel = LogLevel.DEBUG;
        BroadcastDispatcherLogger$logBroadcastDispatched$2 broadcastDispatcherLogger$logBroadcastDispatched$2 = new Function1() { // from class: com.android.systemui.broadcast.logging.BroadcastDispatcherLogger$logBroadcastDispatched$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                int int1 = logMessage.getInt1();
                String str1 = logMessage.getStr1();
                String str2 = logMessage.getStr2();
                StringBuilder m = KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0.m("Broadcast ", int1, " (", str1, ") dispatched to ");
                m.append(str2);
                return m.toString();
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("BroadcastDispatcherLog", logLevel, broadcastDispatcherLogger$logBroadcastDispatched$2, null);
        obtain.setInt1(i);
        obtain.setStr1(str);
        obtain.setStr2(broadcastReceiver2);
        logBuffer.commit(obtain);
    }

    public final void logBroadcastReceived(int i, int i2, Intent intent) {
        String intent2 = intent.toString();
        LogLevel logLevel = LogLevel.INFO;
        BroadcastDispatcherLogger$logBroadcastReceived$2 broadcastDispatcherLogger$logBroadcastReceived$2 = new Function1() { // from class: com.android.systemui.broadcast.logging.BroadcastDispatcherLogger$logBroadcastReceived$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                int int1 = logMessage.getInt1();
                int int2 = logMessage.getInt2();
                String str1 = logMessage.getStr1();
                StringBuilder m = GridLayoutManager$$ExternalSyntheticOutline0.m("[", int1, "] Broadcast received for user ", int2, ": ");
                m.append(str1);
                return m.toString();
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("BroadcastDispatcherLog", logLevel, broadcastDispatcherLogger$logBroadcastReceived$2, null);
        obtain.setInt1(i);
        obtain.setInt2(i2);
        obtain.setStr1(intent2);
        logBuffer.commit(obtain);
    }

    public final void logClearedAfterRemoval(int i, BroadcastReceiver broadcastReceiver) {
        String broadcastReceiver2 = broadcastReceiver.toString();
        LogLevel logLevel = LogLevel.DEBUG;
        BroadcastDispatcherLogger$logClearedAfterRemoval$2 broadcastDispatcherLogger$logClearedAfterRemoval$2 = new Function1() { // from class: com.android.systemui.broadcast.logging.BroadcastDispatcherLogger$logClearedAfterRemoval$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return "Receiver " + logMessage.getStr1() + " has been completely removed for user " + logMessage.getInt1();
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("BroadcastDispatcherLog", logLevel, broadcastDispatcherLogger$logClearedAfterRemoval$2, null);
        obtain.setInt1(i);
        obtain.setStr1(broadcastReceiver2);
        logBuffer.commit(obtain);
    }

    public final void logContextReceiverRegistered(int i, int i2, IntentFilter intentFilter) {
        String str;
        String joinToString$default = SequencesKt___SequencesKt.joinToString$default(SequencesKt__SequencesKt.asSequence(intentFilter.actionsIterator()), "Actions(");
        if (intentFilter.countCategories() == 0) {
            str = "";
        } else {
            str = SequencesKt___SequencesKt.joinToString$default(SequencesKt__SequencesKt.asSequence(intentFilter.categoriesIterator()), "Categories(");
        }
        LogLevel logLevel = LogLevel.INFO;
        BroadcastDispatcherLogger$logContextReceiverRegistered$2 broadcastDispatcherLogger$logContextReceiverRegistered$2 = new Function1() { // from class: com.android.systemui.broadcast.logging.BroadcastDispatcherLogger$logContextReceiverRegistered$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                int int1 = logMessage.getInt1();
                String str2 = logMessage.getStr2();
                String str1 = logMessage.getStr1();
                StringBuilder m = KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0.m("\n                Receiver registered with Context for user ", int1, ". Flags=", str2, "\n                ");
                m.append(str1);
                m.append("\n            ");
                return StringsKt__IndentKt.trimIndent(m.toString());
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("BroadcastDispatcherLog", logLevel, broadcastDispatcherLogger$logContextReceiverRegistered$2, null);
        obtain.setInt1(i);
        if (!Intrinsics.areEqual(str, "")) {
            joinToString$default = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(joinToString$default, "\n", str);
        }
        obtain.setStr1(joinToString$default);
        Companion.getClass();
        obtain.setStr2(Companion.flagToString(i2));
        logBuffer.commit(obtain);
    }

    public final void logContextReceiverUnregistered(int i, String str) {
        LogLevel logLevel = LogLevel.INFO;
        BroadcastDispatcherLogger$logContextReceiverUnregistered$2 broadcastDispatcherLogger$logContextReceiverUnregistered$2 = new Function1() { // from class: com.android.systemui.broadcast.logging.BroadcastDispatcherLogger$logContextReceiverUnregistered$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return "Receiver unregistered with Context for user " + logMessage.getInt1() + ", action " + logMessage.getStr1();
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("BroadcastDispatcherLog", logLevel, broadcastDispatcherLogger$logContextReceiverUnregistered$2, null);
        obtain.setInt1(i);
        obtain.setStr1(str);
        logBuffer.commit(obtain);
    }

    public final void logReceiverRegistered(int i, BroadcastReceiver broadcastReceiver, int i2) {
        String broadcastReceiver2 = broadcastReceiver.toString();
        Companion.getClass();
        String flagToString = Companion.flagToString(i2);
        LogLevel logLevel = LogLevel.INFO;
        BroadcastDispatcherLogger$logReceiverRegistered$2 broadcastDispatcherLogger$logReceiverRegistered$2 = new Function1() { // from class: com.android.systemui.broadcast.logging.BroadcastDispatcherLogger$logReceiverRegistered$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                String str1 = logMessage.getStr1();
                String str2 = logMessage.getStr2();
                int int1 = logMessage.getInt1();
                StringBuilder m = KeyguardUpdateMonitorLogger$logAssistantVisible$2$$ExternalSyntheticOutline0.m("Receiver ", str1, " (", str2, ") registered for user ");
                m.append(int1);
                return m.toString();
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("BroadcastDispatcherLog", logLevel, broadcastDispatcherLogger$logReceiverRegistered$2, null);
        obtain.setInt1(i);
        obtain.setStr1(broadcastReceiver2);
        obtain.setStr2(flagToString);
        logBuffer.commit(obtain);
    }

    public final void logReceiverUnregistered(int i, BroadcastReceiver broadcastReceiver) {
        String broadcastReceiver2 = broadcastReceiver.toString();
        LogLevel logLevel = LogLevel.INFO;
        BroadcastDispatcherLogger$logReceiverUnregistered$2 broadcastDispatcherLogger$logReceiverUnregistered$2 = new Function1() { // from class: com.android.systemui.broadcast.logging.BroadcastDispatcherLogger$logReceiverUnregistered$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return "Receiver " + logMessage.getStr1() + " unregistered for user " + logMessage.getInt1();
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("BroadcastDispatcherLog", logLevel, broadcastDispatcherLogger$logReceiverUnregistered$2, null);
        obtain.setInt1(i);
        obtain.setStr1(broadcastReceiver2);
        logBuffer.commit(obtain);
    }

    public final void logTagForRemoval(BroadcastReceiver broadcastReceiver) {
        String broadcastReceiver2 = broadcastReceiver.toString();
        LogLevel logLevel = LogLevel.DEBUG;
        BroadcastDispatcherLogger$logTagForRemoval$2 broadcastDispatcherLogger$logTagForRemoval$2 = new Function1() { // from class: com.android.systemui.broadcast.logging.BroadcastDispatcherLogger$logTagForRemoval$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return "Receiver " + logMessage.getStr1() + " tagged for removal from user " + logMessage.getInt1();
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("BroadcastDispatcherLog", logLevel, broadcastDispatcherLogger$logTagForRemoval$2, null);
        obtain.setInt1(-1);
        obtain.setStr1(broadcastReceiver2);
        logBuffer.commit(obtain);
    }
}
