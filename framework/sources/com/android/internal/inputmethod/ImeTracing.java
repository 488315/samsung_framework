package com.android.internal.inputmethod;

import android.app.ActivityThread;
import android.os.RemoteException;
import android.tracing.Flags;
import android.util.Log;
import android.util.proto.ProtoOutputStream;
import android.view.inputmethod.InputMethodManager;
import android.view.inputmethod.InputMethodManagerGlobal;
import java.io.PrintWriter;
import java.util.function.Consumer;

/* loaded from: classes5.dex */
public abstract class ImeTracing {
    public static final int IME_TRACING_FROM_CLIENT = 0;
    public static final int IME_TRACING_FROM_IMMS = 2;
    public static final int IME_TRACING_FROM_IMS = 1;
    public static final String PROTO_ARG = "--proto-com-android-imetracing";
    static final String TAG = "imeTracing";
    static boolean sEnabled = false;
    private static ImeTracing sInstance;
    protected boolean mDumpInProgress;
    private final boolean mIsAvailable = InputMethodManagerGlobal.isImeTraceAvailable();
    protected final Object mDumpInProgressLock = new Object();

    @FunctionalInterface
    public interface ServiceDumper {
        void dumpToProto(ProtoOutputStream protoOutputStream, byte[] bArr);
    }

    public abstract void addToBuffer(ProtoOutputStream protoOutputStream, int i);

    public abstract void startTrace(PrintWriter printWriter);

    public abstract void stopTrace(PrintWriter printWriter);

    public abstract void triggerClientDump(String str, InputMethodManager inputMethodManager, byte[] bArr);

    public abstract void triggerManagerServiceDump(String str, ServiceDumper serviceDumper);

    public abstract void triggerServiceDump(String str, ServiceDumper serviceDumper, byte[] bArr);

    public static ImeTracing getInstance() {
        if (sInstance == null) {
            if (Flags.perfettoIme()) {
                sInstance = new ImeTracingPerfettoImpl();
            } else if (isSystemProcess()) {
                sInstance = new ImeTracingServerImpl();
            } else {
                sInstance = new ImeTracingClientImpl();
            }
        }
        return sInstance;
    }

    protected void sendToService(byte[] protoDump, int source, String where) {
        InputMethodManagerGlobal.startProtoDump(protoDump, source, where, new Consumer() { // from class: com.android.internal.inputmethod.ImeTracing$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                Log.e(ImeTracing.TAG, "Exception while sending ime-related dump to server", (RemoteException) obj);
            }
        });
    }

    public final void startImeTrace() {
        InputMethodManagerGlobal.startImeTrace(new Consumer() { // from class: com.android.internal.inputmethod.ImeTracing$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                Log.e(ImeTracing.TAG, "Could not start ime trace.", (RemoteException) obj);
            }
        });
    }

    public final void stopImeTrace() {
        InputMethodManagerGlobal.stopImeTrace(new Consumer() { // from class: com.android.internal.inputmethod.ImeTracing$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                Log.e(ImeTracing.TAG, "Could not stop ime trace.", (RemoteException) obj);
            }
        });
    }

    public void saveForBugreport(PrintWriter pw) {
    }

    public void setEnabled(boolean enabled) {
        sEnabled = enabled;
    }

    public boolean isEnabled() {
        return sEnabled;
    }

    public boolean isAvailable() {
        return this.mIsAvailable;
    }

    private static boolean isSystemProcess() {
        return ActivityThread.isSystem();
    }

    protected void logAndPrintln(PrintWriter pw, String msg) {
        Log.i(TAG, msg);
        if (pw != null) {
            pw.println(msg);
            pw.flush();
        }
    }
}
