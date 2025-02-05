package com.sec.internal.log;

import android.system.ErrnoException;
import android.system.Os;
import android.text.TextUtils;
import com.sec.internal.helper.FileUtils;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileAttribute;
import java.util.Locale;

/* loaded from: classes.dex */
class LogFileManager {
    private static String LOG_TAG = "LogFileManager";
    private String mFullPath;
    private int mMaxCount;
    private int mMaxSize;
    private MeteredWriter mMeter;
    private Path[] mPaths;

    LogFileManager(String str, int i, int i2) {
        if (TextUtils.isEmpty(str) || i < 0 || i2 < 1) {
            throw new IllegalArgumentException();
        }
        this.mFullPath = str;
        this.mMaxSize = i;
        this.mMaxCount = i2;
    }

    void init() {
        this.mPaths = new Path[this.mMaxCount];
        for (int i = 0; i < this.mMaxCount; i++) {
            this.mPaths[i] = Paths.get(String.format(Locale.US, "%s.%d", this.mFullPath, Integer.valueOf(i)), new String[0]);
        }
        cleanupLegacyLogs();
        try {
            open(this.mPaths[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cleanupLegacyLogs() {
        if (isLogGroup(this.mPaths[0].getParent())) {
            return;
        }
        FileUtils.deleteDirectory(this.mPaths[0].getParent());
    }

    private boolean isLogGroup(Path path) {
        try {
            return Os.stat(path.toAbsolutePath().toString()).st_gid == 1007;
        } catch (ErrnoException e) {
            IMSLog.e(LOG_TAG, "isLogGroup exception : " + e.getMessage());
            return false;
        }
    }

    private void setPermission(Path path) {
        try {
            Os.chown(path.toAbsolutePath().toString(), 1000, 1007);
            Os.chmod(path.toAbsolutePath().toString(), 488);
        } catch (ErrnoException e) {
            IMSLog.e(LOG_TAG, "setPermission exception : " + e.getMessage());
        }
    }

    private void open(Path path) throws IOException {
        long j;
        StandardOpenOption standardOpenOption = StandardOpenOption.WRITE;
        if (Files.exists(path, new LinkOption[0])) {
            long size = Files.size(path);
            standardOpenOption = StandardOpenOption.APPEND;
            j = size;
        } else {
            Files.createDirectories(path.getParent(), new FileAttribute[0]);
            setPermission(path.getParent());
            Files.createFile(path, new FileAttribute[0]);
            setPermission(path);
            j = 0;
        }
        this.mMeter = new MeteredWriter(Files.newBufferedWriter(path, standardOpenOption), j);
    }

    private synchronized void rotate() throws IOException {
        for (int i = this.mMaxCount - 2; i >= 0; i--) {
            if (Files.exists(this.mPaths[i], new LinkOption[0])) {
                Path[] pathArr = this.mPaths;
                Files.move(pathArr[i], pathArr[i + 1], StandardCopyOption.REPLACE_EXISTING);
            }
        }
        open(this.mPaths[0]);
    }

    synchronized void write(String str) {
        boolean z;
        try {
            if (this.mMeter == null || Files.notExists(this.mPaths[0], new LinkOption[0])) {
                open(this.mPaths[0]);
            }
            this.mMeter.write(str);
            z = true;
        } catch (IOException unused) {
            z = false;
        }
        if (!z) {
            try {
                open(this.mPaths[0]);
                this.mMeter.write(str);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (this.mMeter.written > this.mMaxSize) {
            rotate();
        }
    }

    private static class MeteredWriter {
        final Writer writer;
        long written;

        MeteredWriter(Writer writer, long j) {
            this.writer = writer;
            this.written = j;
        }

        public void write(String str) throws IOException {
            this.writer.write(str);
            this.writer.flush();
            this.written += str.length();
        }
    }
}
