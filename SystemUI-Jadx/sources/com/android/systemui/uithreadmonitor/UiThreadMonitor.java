package com.android.systemui.uithreadmonitor;

import android.hardware.display.DisplayManager;
import android.os.Build;
import android.os.Debug;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.util.LogUtil;
import java.io.PrintWriter;
import java.util.concurrent.LinkedBlockingDeque;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class UiThreadMonitor implements Dumpable {
    public static final boolean DEBUG_LOG;
    public static final boolean ENABLE_PAUSE;
    public int anrCount;
    public int awakeCount;
    public final Handler bgHandler;
    public final DisplayManager displayManager;
    public final DumpManager dumpManager;
    public final Handler handler;
    public long lastAsyncMsgHandledTimed;
    public long lastAwakeTime;
    public int lastChoreographerLogCount;
    public long lastChoreographerLogTime;
    public int lastChoreographerTotalDrawCount;
    public int lastDisplayState;
    public String lastStackTrace;
    public long lastStackTraceTime;
    public final LooperSlowLogController looperLogController;
    public boolean looperMsgLog;
    public boolean looperSlowLog;
    public final LinkedBlockingDeque blockingDeque = new LinkedBlockingDeque(1);
    public final Lazy mainThread$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.uithreadmonitor.UiThreadMonitor$mainThread$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return UiThreadMonitor.this.looper.getThread();
        }
    });
    public final Lazy monitorThread$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.uithreadmonitor.UiThreadMonitor$monitorThread$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            final UiThreadMonitor uiThreadMonitor = UiThreadMonitor.this;
            Thread thread = new Thread(new Runnable() { // from class: com.android.systemui.uithreadmonitor.UiThreadMonitor$monitorThread$2.1
                /* JADX WARN: Can't wrap try/catch for region: R(22:2|(2:4|(1:6))(2:71|(1:73))|7|(2:9|(17:11|12|(1:16)|17|(1:19)(1:65)|20|(1:22)|23|(1:25)|26|27|(1:29)|30|(4:33|(1:35)|36|37)|38|(1:40)|(3:43|44|45)(1:42)))(2:68|(18:70|67|12|(2:14|16)|17|(0)(0)|20|(0)|23|(0)|26|27|(0)|30|(4:33|(0)|36|37)|38|(0)|(0)(0)))|66|67|12|(0)|17|(0)(0)|20|(0)|23|(0)|26|27|(0)|30|(0)|38|(0)|(0)(0)) */
                /* JADX WARN: Code restructure failed: missing block: B:48:0x013f, code lost:
                
                    r0 = move-exception;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:64:0x0239, code lost:
                
                    throw r0;
                 */
                /* JADX WARN: Removed duplicated region for block: B:14:0x006c  */
                /* JADX WARN: Removed duplicated region for block: B:19:0x00a0  */
                /* JADX WARN: Removed duplicated region for block: B:22:0x00b1  */
                /* JADX WARN: Removed duplicated region for block: B:25:0x00bd  */
                /* JADX WARN: Removed duplicated region for block: B:29:0x00d8 A[Catch: all -> 0x013f, TryCatch #1 {all -> 0x013f, blocks: (B:27:0x00c4, B:29:0x00d8, B:30:0x00db), top: B:26:0x00c4 }] */
                /* JADX WARN: Removed duplicated region for block: B:32:0x00e7 A[ADDED_TO_REGION] */
                /* JADX WARN: Removed duplicated region for block: B:35:0x0128 A[LOOP:1: B:34:0x0126->B:35:0x0128, LOOP_END] */
                /* JADX WARN: Removed duplicated region for block: B:40:0x01c3  */
                /* JADX WARN: Removed duplicated region for block: B:42:0x01ce A[LOOP:0: B:2:0x0018->B:42:0x01ce, LOOP_END] */
                /* JADX WARN: Removed duplicated region for block: B:43:0x01ca A[SYNTHETIC] */
                /* JADX WARN: Removed duplicated region for block: B:65:0x00a4  */
                @Override // java.lang.Runnable
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final void run() {
                    /*
                        Method dump skipped, instructions count: 570
                        To view this dump change 'Code comments level' option to 'DEBUG'
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.uithreadmonitor.UiThreadMonitor$monitorThread$2.AnonymousClass1.run():void");
                }
            });
            thread.setName("UiThreadMonitor");
            return thread;
        }
    });
    public final Looper looper = Looper.getMainLooper();
    public volatile boolean isPaused = true;
    public final Lazy display$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.uithreadmonitor.UiThreadMonitor$display$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return UiThreadMonitor.this.displayManager.getDisplay(0);
        }
    });
    public final Function2 onChoreographerLog = new Function2() { // from class: com.android.systemui.uithreadmonitor.UiThreadMonitor$onChoreographerLog$1
        {
            super(2);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            int i;
            String str = (String) obj2;
            long currentTimeMillis = System.currentTimeMillis();
            UiThreadMonitor uiThreadMonitor = UiThreadMonitor.this;
            int i2 = uiThreadMonitor.lastChoreographerTotalDrawCount + 1;
            uiThreadMonitor.lastChoreographerTotalDrawCount = i2;
            if (currentTimeMillis - uiThreadMonitor.lastChoreographerLogTime >= 1000 && (i = uiThreadMonitor.lastChoreographerLogCount) < 10) {
                uiThreadMonitor.lastChoreographerLogTime = currentTimeMillis;
                int i3 = i + 1;
                uiThreadMonitor.lastChoreographerLogCount = i3;
                ExifInterface$$ExternalSyntheticOutline0.m(GridLayoutManager$$ExternalSyntheticOutline0.m("DOZE_SUSPEND draw count=", i3, " totalCount=", i2, "\n"), str, "UiThreadMonitor");
            }
            return Unit.INSTANCE;
        }
    };
    public final UiThreadMonitor$runnable$1 runnable = new Runnable() { // from class: com.android.systemui.uithreadmonitor.UiThreadMonitor$runnable$1
        @Override // java.lang.Runnable
        public final void run() {
            UiThreadMonitor uiThreadMonitor = UiThreadMonitor.this;
            boolean z = UiThreadMonitor.DEBUG_LOG;
            uiThreadMonitor.setAwake(0);
        }
    };
    public final UiThreadMonitor$asyncRunnable$1 asyncRunnable = new Runnable() { // from class: com.android.systemui.uithreadmonitor.UiThreadMonitor$asyncRunnable$1
        @Override // java.lang.Runnable
        public final void run() {
            UiThreadMonitor uiThreadMonitor = UiThreadMonitor.this;
            boolean z = UiThreadMonitor.DEBUG_LOG;
            uiThreadMonitor.getClass();
            if (UiThreadMonitor.DEBUG_LOG) {
                Log.d("UiThreadMonitor", "handleAsyncMsg");
            }
            UiThreadMonitor.this.lastAsyncMsgHandledTimed = System.currentTimeMillis();
            if (!UiThreadMonitor.this.isPaused) {
                UiThreadMonitor uiThreadMonitor2 = UiThreadMonitor.this;
                Handler handler = uiThreadMonitor2.handler;
                UiThreadMonitor$asyncRunnable$1 uiThreadMonitor$asyncRunnable$1 = uiThreadMonitor2.asyncRunnable;
                handler.removeCallbacks(uiThreadMonitor$asyncRunnable$1);
                Message obtain = Message.obtain(handler, uiThreadMonitor$asyncRunnable$1);
                obtain.setAsynchronous(true);
                handler.sendMessageDelayed(obtain, 3000L);
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
        boolean z;
        new Companion(null);
        DEBUG_LOG = Log.isLoggable("UiThreadMonitor", 3);
        if (Intrinsics.areEqual("user", Build.TYPE) && !Debug.semIsProductDev()) {
            z = true;
        } else {
            z = false;
        }
        ENABLE_PAUSE = z;
    }

    /* JADX WARN: Type inference failed for: r1v10, types: [com.android.systemui.uithreadmonitor.UiThreadMonitor$runnable$1] */
    /* JADX WARN: Type inference failed for: r1v11, types: [com.android.systemui.uithreadmonitor.UiThreadMonitor$asyncRunnable$1] */
    public UiThreadMonitor(Handler handler, Handler handler2, DisplayManager displayManager, DumpManager dumpManager, LooperSlowLogController looperSlowLogController) {
        this.handler = handler;
        this.bgHandler = handler2;
        this.displayManager = displayManager;
        this.dumpManager = dumpManager;
        this.looperLogController = looperSlowLogController;
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        String str = "UiThreadMonitor state:\n  monitorThread state=" + ((Thread) this.monitorThread$delegate.getValue()).getState() + ", paused=" + this.isPaused + ", count=" + this.anrCount + "\n  mainThread state=" + ((Thread) this.mainThread$delegate.getValue()).getState() + "\n  lastAsyncMsgHandledTime=" + LogUtil.makeTimeStr(this.lastAsyncMsgHandledTimed);
        String str2 = this.lastStackTrace;
        if (str2 != null) {
            str = ((Object) str) + "\n  lastStackTrace=[\n" + str2 + "  ], " + LogUtil.makeTimeStr(this.lastStackTraceTime);
        }
        printWriter.println(str);
    }

    public final void setAwake(int i) {
        int i2;
        if (i == 0) {
            long elapsedRealtime = SystemClock.elapsedRealtime();
            if (DEBUG_LOG || (i2 = this.lastDisplayState) == 2 || i2 == 3) {
                Log.i("UiThreadMonitor", "onTick count=" + this.awakeCount + " " + (elapsedRealtime - this.lastAwakeTime) + " " + (System.currentTimeMillis() - this.lastAsyncMsgHandledTimed));
            }
            this.lastAwakeTime = elapsedRealtime;
            this.awakeCount++;
        }
        try {
            this.blockingDeque.put(Boolean.TRUE);
        } catch (Throwable unused) {
        }
    }
}
