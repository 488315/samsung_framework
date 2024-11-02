package com.android.systemui.flags;

import android.util.Log;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.StandaloneCoroutine;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class ConditionalRestarter implements Restarter {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final CoroutineScope applicationScope;
    public final CoroutineDispatcher backgroundDispatcher;
    public final Set conditions;
    public String pendingReason = "";
    public final long restartDelaySec;
    public StandaloneCoroutine restartJob;
    public final SystemExitRestarter systemExitRestarter;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public interface Condition {
        boolean canRestartNow(Function0 function0);
    }

    static {
        new Companion(null);
    }

    public ConditionalRestarter(SystemExitRestarter systemExitRestarter, Set<Condition> set, long j, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher) {
        this.systemExitRestarter = systemExitRestarter;
        this.conditions = set;
        this.restartDelaySec = j;
        this.applicationScope = coroutineScope;
        this.backgroundDispatcher = coroutineDispatcher;
    }

    @Override // com.android.systemui.flags.Restarter
    public final void restartSystemUI(String str) {
        Log.d("SysUIFlags", "SystemUI Restart requested. Restarting when idle.");
        scheduleRestart(str);
    }

    public final void scheduleRestart(String str) {
        boolean z;
        boolean z2 = false;
        if (str.length() == 0) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            str = this.pendingReason;
        }
        this.pendingReason = str;
        Set set = this.conditions;
        if (!(set instanceof Collection) || !set.isEmpty()) {
            Iterator it = set.iterator();
            while (it.hasNext()) {
                if (!((Condition) it.next()).canRestartNow(new ConditionalRestarter$scheduleRestart$1$1(this))) {
                    break;
                }
            }
        }
        z2 = true;
        if (z2) {
            if (this.restartJob == null) {
                this.restartJob = BuildersKt.launch$default(this.applicationScope, this.backgroundDispatcher, null, new ConditionalRestarter$scheduleRestart$2(this, null), 2);
                return;
            }
            return;
        }
        StandaloneCoroutine standaloneCoroutine = this.restartJob;
        if (standaloneCoroutine != null) {
            standaloneCoroutine.cancel(null);
        }
        this.restartJob = null;
    }
}
