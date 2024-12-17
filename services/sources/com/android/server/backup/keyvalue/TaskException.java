package com.android.server.backup.keyvalue;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
class TaskException extends BackupException {
    public static final /* synthetic */ int $r8$clinit = 0;
    private final boolean mStateCompromised;
    private final int mStatus;

    public TaskException(int i, boolean z) {
        this.mStateCompromised = z;
        this.mStatus = i;
    }

    public TaskException(Exception exc, boolean z, int i) {
        super(exc);
        this.mStateCompromised = z;
        this.mStatus = i;
    }

    public static TaskException stateCompromised(Exception exc) {
        return exc instanceof TaskException ? new TaskException(exc, true, ((TaskException) exc).mStatus) : new TaskException(exc, true, -1000);
    }

    public final int getStatus() {
        return this.mStatus;
    }

    public final boolean isStateCompromised() {
        return this.mStateCompromised;
    }
}
