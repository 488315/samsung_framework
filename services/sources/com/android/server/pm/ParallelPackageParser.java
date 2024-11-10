package com.android.server.pm;

import android.os.Trace;
import com.android.internal.util.ConcurrentUtils;
import com.android.server.pm.parsing.PackageParser2;
import com.android.server.pm.parsing.pkg.ParsedPackage;
import java.io.File;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;

/* loaded from: classes3.dex */
public class ParallelPackageParser {
    public final ExecutorService mExecutorService;
    public volatile String mInterruptedInThread;
    public final PackageParser2 mPackageParser;
    public final BlockingQueue mQueue = new ArrayBlockingQueue(30);

    public static ExecutorService makeExecutorService() {
        return ConcurrentUtils.newFixedThreadPool(4, "package-parsing-thread", -2);
    }

    public ParallelPackageParser(PackageParser2 packageParser2, ExecutorService executorService) {
        this.mPackageParser = packageParser2;
        this.mExecutorService = executorService;
    }

    /* loaded from: classes3.dex */
    public class ParseResult {
        public ParsedPackage parsedPackage;
        public File scanFile;
        public Throwable throwable;

        public String toString() {
            return "ParseResult{parsedPackage=" + this.parsedPackage + ", scanFile=" + this.scanFile + ", throwable=" + this.throwable + '}';
        }
    }

    public ParseResult take() {
        try {
            if (this.mInterruptedInThread != null) {
                throw new InterruptedException("Interrupted in " + this.mInterruptedInThread);
            }
            return (ParseResult) this.mQueue.take();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException(e);
        }
    }

    public void submit(final File file, final int i) {
        this.mExecutorService.submit(new Runnable() { // from class: com.android.server.pm.ParallelPackageParser$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ParallelPackageParser.this.lambda$submit$0(file, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$submit$0(File file, int i) {
        ParseResult parseResult = new ParseResult();
        Trace.traceBegin(262144L, "parallel parsePackage [" + file + "]");
        try {
            try {
                parseResult.scanFile = file;
                parseResult.parsedPackage = parsePackage(file, i);
            } finally {
                try {
                    this.mQueue.put(parseResult);
                } finally {
                }
            }
            this.mQueue.put(parseResult);
        } catch (InterruptedException unused) {
            Thread.currentThread().interrupt();
            this.mInterruptedInThread = Thread.currentThread().getName();
        }
    }

    public ParsedPackage parsePackage(File file, int i) {
        return this.mPackageParser.parsePackage(file, i, true);
    }
}
