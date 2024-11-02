package android.os;

import android.net.LocalSocketAddress;

/* loaded from: classes3.dex */
public class ChildZygoteProcess extends ZygoteProcess {
    private final int mPid;

    public ChildZygoteProcess(LocalSocketAddress socketAddress, int pid) {
        super(socketAddress, null);
        this.mPid = pid;
    }

    public int getPid() {
        return this.mPid;
    }
}
