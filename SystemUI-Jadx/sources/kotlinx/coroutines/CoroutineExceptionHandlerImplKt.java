package kotlinx.coroutines;

import java.lang.Thread;
import java.util.Iterator;
import java.util.List;
import kotlin.ExceptionsKt__ExceptionsKt;
import kotlin.Result;
import kotlin.Unit;
import kotlin.coroutines.CoroutineContext;
import kotlin.sequences.SequencesKt__SequencesKt;
import kotlin.sequences.SequencesKt___SequencesKt;
import kotlinx.coroutines.android.AndroidExceptionPreHandler;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes3.dex */
public abstract class CoroutineExceptionHandlerImplKt {
    public static final List handlers = SequencesKt___SequencesKt.toList(SequencesKt__SequencesKt.asSequence(CoroutineExceptionHandlerImplKt$$ExternalSyntheticServiceLoad0.m()));

    public static final void handleCoroutineExceptionImpl(Throwable th, CoroutineContext coroutineContext) {
        Throwable runtimeException;
        Iterator it = handlers.iterator();
        while (it.hasNext()) {
            try {
                ((AndroidExceptionPreHandler) ((CoroutineExceptionHandler) it.next())).handleException(coroutineContext, th);
            } catch (Throwable th2) {
                Thread currentThread = Thread.currentThread();
                Thread.UncaughtExceptionHandler uncaughtExceptionHandler = currentThread.getUncaughtExceptionHandler();
                if (th == th2) {
                    runtimeException = th;
                } else {
                    runtimeException = new RuntimeException("Exception while trying to handle coroutine exception", th2);
                    ExceptionsKt__ExceptionsKt.addSuppressed(runtimeException, th);
                }
                uncaughtExceptionHandler.uncaughtException(currentThread, runtimeException);
            }
        }
        Thread currentThread2 = Thread.currentThread();
        try {
            int i = Result.$r8$clinit;
            ExceptionsKt__ExceptionsKt.addSuppressed(th, new DiagnosticCoroutineContextException(coroutineContext));
            Unit unit = Unit.INSTANCE;
        } catch (Throwable th3) {
            int i2 = Result.$r8$clinit;
            new Result.Failure(th3);
        }
        currentThread2.getUncaughtExceptionHandler().uncaughtException(currentThread2, th);
    }
}
