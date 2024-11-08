package android.util.apk;

import android.system.Os;
import android.system.OsConstants;
import java.io.FileDescriptor;

/* loaded from: classes4.dex */
public class MemoryMappedFileDataSource implements DataSource {
    private static final long MEMORY_PAGE_SIZE_BYTES = Os.sysconf(OsConstants._SC_PAGESIZE);
    private final FileDescriptor mFd;
    private final long mFilePosition;
    private final long mSize;

    public MemoryMappedFileDataSource(FileDescriptor fd, long position, long size) {
        this.mFd = fd;
        this.mFilePosition = position;
        this.mSize = size;
    }

    @Override // android.util.apk.DataSource
    public long size() {
        return this.mSize;
    }

    /* JADX WARN: Removed duplicated region for block: B:39:0x00cb  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00c5 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @Override // android.util.apk.DataSource
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void feedIntoDataDigester(android.util.apk.DataDigester r26, long r27, int r29) throws java.io.IOException, java.security.DigestException {
        /*
            Method dump skipped, instructions count: 204
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.util.apk.MemoryMappedFileDataSource.feedIntoDataDigester(android.util.apk.DataDigester, long, int):void");
    }
}
