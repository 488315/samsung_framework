package okio;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import org.jetbrains.annotations.NotNull;

/* compiled from: Sink.kt */
/* loaded from: classes.dex */
public interface Sink extends Closeable, Flushable {
    @Override // java.io.Closeable, java.lang.AutoCloseable
    void close() throws IOException;

    void flush() throws IOException;

    @NotNull
    Timeout timeout();

    void write(@NotNull Buffer buffer, long j) throws IOException;
}
