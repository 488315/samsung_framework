package android.hardware;

import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import java.io.FileDescriptor;
import java.io.IOException;
import java.time.Duration;
import libcore.util.NativeAllocationRegistry;

/* loaded from: classes.dex */
public final class SyncFence implements AutoCloseable, Parcelable {
    public static final long SIGNAL_TIME_INVALID = -1;
    public static final long SIGNAL_TIME_PENDING = Long.MAX_VALUE;
    private final Runnable mCloser;
    private long mNativePtr;
    private static final NativeAllocationRegistry sRegistry = NativeAllocationRegistry.createNonmalloced(SyncFence.class.getClassLoader(), nGetDestructor(), 4);
    public static final Parcelable.Creator<SyncFence> CREATOR = new Parcelable.Creator<SyncFence>() { // from class: android.hardware.SyncFence.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SyncFence createFromParcel(Parcel in) {
            return new SyncFence(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SyncFence[] newArray(int size) {
            return new SyncFence[size];
        }
    };

    private static native long nCreate(int i);

    private static native long nGetDestructor();

    private static native int nGetFd(long j);

    private static native long nGetSignalTime(long j);

    private static native void nIncRef(long j);

    private static native boolean nIsValid(long j);

    private static native boolean nWait(long j, long j2);

    private SyncFence(int fileDescriptor) {
        this.mNativePtr = nCreate(fileDescriptor);
        this.mCloser = sRegistry.registerNativeAllocation(this, this.mNativePtr);
    }

    private SyncFence(Parcel parcel) {
        boolean valid = parcel.readBoolean();
        FileDescriptor fileDescriptor = valid ? parcel.readRawFileDescriptor() : null;
        if (fileDescriptor != null) {
            this.mNativePtr = nCreate(fileDescriptor.getInt$());
            this.mCloser = sRegistry.registerNativeAllocation(this, this.mNativePtr);
        } else {
            this.mCloser = new Runnable() { // from class: android.hardware.SyncFence$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    SyncFence.lambda$new$0();
                }
            };
        }
    }

    static /* synthetic */ void lambda$new$0() {
    }

    public SyncFence(long nativeFencePtr) {
        this.mNativePtr = nativeFencePtr;
        if (nativeFencePtr != 0) {
            this.mCloser = sRegistry.registerNativeAllocation(this, this.mNativePtr);
        } else {
            this.mCloser = new Runnable() { // from class: android.hardware.SyncFence$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    SyncFence.lambda$new$1();
                }
            };
        }
    }

    static /* synthetic */ void lambda$new$1() {
    }

    public SyncFence(SyncFence other) {
        this(other.mNativePtr);
        if (this.mNativePtr != 0) {
            nIncRef(this.mNativePtr);
        }
    }

    private SyncFence() {
        this.mCloser = new Runnable() { // from class: android.hardware.SyncFence$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                SyncFence.lambda$new$2();
            }
        };
    }

    static /* synthetic */ void lambda$new$2() {
    }

    public static SyncFence createEmpty() {
        return new SyncFence();
    }

    public static SyncFence create(ParcelFileDescriptor wrapped) {
        return new SyncFence(wrapped.detachFd());
    }

    public static SyncFence adopt(int fileDescriptor) {
        return new SyncFence(fileDescriptor);
    }

    public ParcelFileDescriptor getFdDup() throws IOException {
        ParcelFileDescriptor fromFd;
        synchronized (this.mCloser) {
            int fd = this.mNativePtr != 0 ? nGetFd(this.mNativePtr) : -1;
            if (fd == -1) {
                throw new IllegalStateException("Cannot dup the FD of an invalid SyncFence");
            }
            fromFd = ParcelFileDescriptor.fromFd(fd);
        }
        return fromFd;
    }

    public boolean isValid() {
        boolean z;
        synchronized (this.mCloser) {
            z = this.mNativePtr != 0 && nIsValid(this.mNativePtr);
        }
        return z;
    }

    public boolean await(Duration timeout) {
        long timeoutNanos;
        if (timeout.isNegative()) {
            timeoutNanos = -1;
        } else {
            timeoutNanos = timeout.toNanos();
        }
        return await(timeoutNanos);
    }

    public boolean awaitForever() {
        return await(-1L);
    }

    private boolean await(long timeoutNanos) {
        boolean z;
        synchronized (this.mCloser) {
            z = this.mNativePtr != 0 && nWait(this.mNativePtr, timeoutNanos);
        }
        return z;
    }

    public long getSignalTime() {
        long nGetSignalTime;
        synchronized (this.mCloser) {
            nGetSignalTime = this.mNativePtr != 0 ? nGetSignalTime(this.mNativePtr) : -1L;
        }
        return nGetSignalTime;
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        synchronized (this.mCloser) {
            if (this.mNativePtr == 0) {
                return;
            }
            this.mNativePtr = 0L;
            this.mCloser.run();
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 1;
    }

    public Object getLock() {
        return this.mCloser;
    }

    public long getNativeFence() {
        return this.mNativePtr;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        synchronized (this.mCloser) {
            int fd = this.mNativePtr != 0 ? nGetFd(this.mNativePtr) : -1;
            if (fd == -1) {
                out.writeBoolean(false);
            } else {
                out.writeBoolean(true);
                FileDescriptor temp = new FileDescriptor();
                temp.setInt$(fd);
                out.writeFileDescriptor(temp);
            }
        }
    }
}
