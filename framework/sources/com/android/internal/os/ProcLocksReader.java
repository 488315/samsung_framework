package com.android.internal.os;

import android.util.IntArray;
import com.android.internal.util.ProcFileReader;
import java.io.FileInputStream;
import java.io.IOException;

/* loaded from: classes5.dex */
public class ProcLocksReader {
    private final String mPath;
    private IntArray mPids;
    private ProcFileReader mReader;

    public interface ProcLocksReaderCallback {
        void onBlockingFileLock(IntArray intArray);
    }

    public ProcLocksReader() {
        this.mReader = null;
        this.mPids = new IntArray();
        this.mPath = "/proc/locks";
    }

    public ProcLocksReader(String path) {
        this.mReader = null;
        this.mPids = new IntArray();
        this.mPath = path;
    }

    public void handleBlockingFileLocks(ProcLocksReaderCallback callback) throws IOException {
        long last = -1;
        if (this.mReader == null) {
            this.mReader = new ProcFileReader(new FileInputStream(this.mPath));
        } else {
            this.mReader.rewind();
        }
        this.mPids.clear();
        while (this.mReader.hasMoreData()) {
            long id = this.mReader.nextLong(true);
            if (id == last) {
                this.mReader.nextIgnored();
                this.mReader.nextIgnored();
                this.mReader.nextIgnored();
                this.mReader.nextIgnored();
                int pid = this.mReader.nextInt();
                if (pid > 0) {
                    this.mPids.add(pid);
                }
                this.mReader.finishLine();
            } else {
                if (this.mPids.size() > 1) {
                    callback.onBlockingFileLock(this.mPids);
                    this.mPids.clear();
                }
                this.mReader.nextIgnored();
                this.mReader.nextIgnored();
                this.mReader.nextIgnored();
                int pid2 = this.mReader.nextInt();
                if (pid2 > 0) {
                    if (this.mPids.size() == 0) {
                        this.mPids.add(pid2);
                    } else {
                        this.mPids.set(0, pid2);
                    }
                }
                this.mReader.finishLine();
                last = id;
            }
        }
        if (this.mPids.size() > 1) {
            callback.onBlockingFileLock(this.mPids);
        }
    }
}
