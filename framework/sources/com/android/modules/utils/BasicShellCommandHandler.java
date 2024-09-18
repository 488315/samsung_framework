package com.android.modules.utils;

import android.os.Binder;
import com.android.internal.content.NativeLibraryHelper;
import java.io.BufferedInputStream;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/* loaded from: classes5.dex */
public abstract class BasicShellCommandHandler {
    protected static final boolean DEBUG = false;
    protected static final String TAG = "ShellCommand";
    private int mArgPos;
    private String[] mArgs;
    private String mCmd;
    private String mCurArgData;
    private FileDescriptor mErr;
    private PrintWriter mErrPrintWriter;
    private FileOutputStream mFileErr;
    private FileInputStream mFileIn;
    private FileOutputStream mFileOut;
    private FileDescriptor mIn;
    private InputStream mInputStream;
    private FileDescriptor mOut;
    private PrintWriter mOutPrintWriter;
    private Binder mTarget;

    public abstract int onCommand(String str);

    public abstract void onHelp();

    public void init(Binder target, FileDescriptor in, FileDescriptor out, FileDescriptor err, String[] args, int firstArgPos) {
        this.mTarget = target;
        this.mIn = in;
        this.mOut = out;
        this.mErr = err;
        this.mArgs = args;
        this.mCmd = null;
        this.mArgPos = firstArgPos;
        this.mCurArgData = null;
        this.mFileIn = null;
        this.mFileOut = null;
        this.mFileErr = null;
        this.mOutPrintWriter = null;
        this.mErrPrintWriter = null;
        this.mInputStream = null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x0029, code lost:            if (r2 != null) goto L14;     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x002b, code lost:            r2.flush();     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0064, code lost:            return r1;     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0061, code lost:            if (r2 == null) goto L23;     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int exec(android.os.Binder r10, java.io.FileDescriptor r11, java.io.FileDescriptor r12, java.io.FileDescriptor r13, java.lang.String[] r14) {
        /*
            r9 = this;
            if (r14 == 0) goto Lb
            int r0 = r14.length
            if (r0 <= 0) goto Lb
            r0 = 0
            r0 = r14[r0]
            r1 = 1
            r8 = r1
            goto Le
        Lb:
            r0 = 0
            r1 = 0
            r8 = r1
        Le:
            r1 = r9
            r2 = r10
            r3 = r11
            r4 = r12
            r5 = r13
            r6 = r14
            r7 = r8
            r1.init(r2, r3, r4, r5, r6, r7)
            r9.mCmd = r0
            r1 = -1
            int r2 = r9.onCommand(r0)     // Catch: java.lang.Throwable -> L2f
            r1 = r2
            java.io.PrintWriter r2 = r9.mOutPrintWriter
            if (r2 == 0) goto L27
            r2.flush()
        L27:
            java.io.PrintWriter r2 = r9.mErrPrintWriter
            if (r2 == 0) goto L64
        L2b:
            r2.flush()
            goto L64
        L2f:
            r2 = move-exception
            java.io.PrintWriter r3 = r9.getErrPrintWriter()     // Catch: java.lang.Throwable -> L65
            r3.println()     // Catch: java.lang.Throwable -> L65
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L65
            r4.<init>()     // Catch: java.lang.Throwable -> L65
            java.lang.String r5 = "Exception occurred while executing '"
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch: java.lang.Throwable -> L65
            java.lang.String r5 = r9.mCmd     // Catch: java.lang.Throwable -> L65
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch: java.lang.Throwable -> L65
            java.lang.String r5 = "':"
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch: java.lang.Throwable -> L65
            java.lang.String r4 = r4.toString()     // Catch: java.lang.Throwable -> L65
            r3.println(r4)     // Catch: java.lang.Throwable -> L65
            r2.printStackTrace(r3)     // Catch: java.lang.Throwable -> L65
            java.io.PrintWriter r2 = r9.mOutPrintWriter
            if (r2 == 0) goto L5f
            r2.flush()
        L5f:
            java.io.PrintWriter r2 = r9.mErrPrintWriter
            if (r2 == 0) goto L64
            goto L2b
        L64:
            return r1
        L65:
            r2 = move-exception
            java.io.PrintWriter r3 = r9.mOutPrintWriter
            if (r3 == 0) goto L6d
            r3.flush()
        L6d:
            java.io.PrintWriter r3 = r9.mErrPrintWriter
            if (r3 == 0) goto L74
            r3.flush()
        L74:
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.modules.utils.BasicShellCommandHandler.exec(android.os.Binder, java.io.FileDescriptor, java.io.FileDescriptor, java.io.FileDescriptor, java.lang.String[]):int");
    }

    public FileDescriptor getOutFileDescriptor() {
        return this.mOut;
    }

    public OutputStream getRawOutputStream() {
        if (this.mFileOut == null) {
            this.mFileOut = new FileOutputStream(this.mOut);
        }
        return this.mFileOut;
    }

    public PrintWriter getOutPrintWriter() {
        if (this.mOutPrintWriter == null) {
            this.mOutPrintWriter = new PrintWriter(getRawOutputStream());
        }
        return this.mOutPrintWriter;
    }

    public FileDescriptor getErrFileDescriptor() {
        return this.mErr;
    }

    public OutputStream getRawErrorStream() {
        if (this.mFileErr == null) {
            this.mFileErr = new FileOutputStream(this.mErr);
        }
        return this.mFileErr;
    }

    public PrintWriter getErrPrintWriter() {
        if (this.mErr == null) {
            return getOutPrintWriter();
        }
        if (this.mErrPrintWriter == null) {
            this.mErrPrintWriter = new PrintWriter(getRawErrorStream());
        }
        return this.mErrPrintWriter;
    }

    public FileDescriptor getInFileDescriptor() {
        return this.mIn;
    }

    public InputStream getRawInputStream() {
        if (this.mFileIn == null) {
            this.mFileIn = new FileInputStream(this.mIn);
        }
        return this.mFileIn;
    }

    public InputStream getBufferedInputStream() {
        if (this.mInputStream == null) {
            this.mInputStream = new BufferedInputStream(getRawInputStream());
        }
        return this.mInputStream;
    }

    public String getNextOption() {
        if (this.mCurArgData != null) {
            String prev = this.mArgs[this.mArgPos - 1];
            throw new IllegalArgumentException("No argument expected after \"" + prev + "\"");
        }
        int i = this.mArgPos;
        String[] strArr = this.mArgs;
        if (i >= strArr.length) {
            return null;
        }
        String arg = strArr[i];
        if (!arg.startsWith(NativeLibraryHelper.CLEAR_ABI_OVERRIDE)) {
            return null;
        }
        this.mArgPos++;
        if (arg.equals("--")) {
            return null;
        }
        if (arg.length() > 1 && arg.charAt(1) != '-') {
            if (arg.length() > 2) {
                this.mCurArgData = arg.substring(2);
                return arg.substring(0, 2);
            }
            this.mCurArgData = null;
            return arg;
        }
        this.mCurArgData = null;
        return arg;
    }

    public String getNextArg() {
        if (this.mCurArgData != null) {
            String arg = this.mCurArgData;
            this.mCurArgData = null;
            return arg;
        }
        int i = this.mArgPos;
        String[] strArr = this.mArgs;
        if (i >= strArr.length) {
            return null;
        }
        this.mArgPos = i + 1;
        return strArr[i];
    }

    public String peekNextArg() {
        String str = this.mCurArgData;
        if (str != null) {
            return str;
        }
        int i = this.mArgPos;
        String[] strArr = this.mArgs;
        if (i < strArr.length) {
            return strArr[i];
        }
        return null;
    }

    public String[] peekRemainingArgs() {
        int remaining = getRemainingArgsCount();
        String[] args = new String[remaining];
        int pos = this.mArgPos;
        while (true) {
            String[] strArr = this.mArgs;
            if (pos < strArr.length) {
                args[pos - this.mArgPos] = strArr[pos];
                pos++;
            } else {
                return args;
            }
        }
    }

    public int getRemainingArgsCount() {
        int i = this.mArgPos;
        String[] strArr = this.mArgs;
        if (i >= strArr.length) {
            return 0;
        }
        return strArr.length - i;
    }

    public String getNextArgRequired() {
        String arg = getNextArg();
        if (arg == null) {
            String prev = this.mArgs[this.mArgPos - 1];
            throw new IllegalArgumentException("Argument expected after \"" + prev + "\"");
        }
        return arg;
    }

    public int handleDefaultCommands(String cmd) {
        if (cmd == null || "help".equals(cmd) || "-h".equals(cmd)) {
            onHelp();
            return -1;
        }
        getOutPrintWriter().println("Unknown command: " + cmd);
        return -1;
    }

    public Binder getTarget() {
        return this.mTarget;
    }

    public String[] getAllArgs() {
        return this.mArgs;
    }
}
