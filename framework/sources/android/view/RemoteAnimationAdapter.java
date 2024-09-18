package android.view;

import android.app.IApplicationThread;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.IRemoteAnimationRunner;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.view.IRemoteAnimationMergeCallback;

/* loaded from: classes4.dex */
public class RemoteAnimationAdapter implements Parcelable {
    public static final Parcelable.Creator<RemoteAnimationAdapter> CREATOR = new Parcelable.Creator<RemoteAnimationAdapter>() { // from class: android.view.RemoteAnimationAdapter.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RemoteAnimationAdapter createFromParcel(Parcel in) {
            return new RemoteAnimationAdapter(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RemoteAnimationAdapter[] newArray(int size) {
            return new RemoteAnimationAdapter[size];
        }
    };
    public static final int REMOTE_ANIM_MERGED_NONE = 0;
    public static final int REMOTE_ANIM_MERGED_READY = 1;
    public static final int REMOTE_ANIM_MERGED_SUCCESS = 2;
    public static final int REMOTE_ANIM_MERGED_TIMEOUT = -1;
    private IApplicationThread mCallingApplication;
    private int mCallingPid;
    private int mCallingUid;
    private final boolean mChangeNeedsSnapshot;
    private final long mDuration;
    private IRemoteAnimationMergeCallback mMergeCallback;
    private long mMergeDurationHint;
    public int mMergedState;
    private final IRemoteAnimationRunner mRunner;
    private final long mStatusBarTransitionDelay;

    public RemoteAnimationAdapter(IRemoteAnimationRunner runner, long duration, long statusBarTransitionDelay, boolean changeNeedsSnapshot) {
        this.mMergedState = 0;
        this.mRunner = runner;
        this.mDuration = duration;
        this.mChangeNeedsSnapshot = changeNeedsSnapshot;
        this.mStatusBarTransitionDelay = statusBarTransitionDelay;
    }

    public RemoteAnimationAdapter(IRemoteAnimationRunner runner, long duration, long statusBarTransitionDelay) {
        this(runner, duration, statusBarTransitionDelay, false);
    }

    public RemoteAnimationAdapter(IRemoteAnimationRunner runner, long duration, long statusBarTransitionDelay, IApplicationThread callingApplication) {
        this(runner, duration, statusBarTransitionDelay, false);
        this.mCallingApplication = callingApplication;
    }

    public RemoteAnimationAdapter(Parcel in) {
        this.mMergedState = 0;
        this.mRunner = IRemoteAnimationRunner.Stub.asInterface(in.readStrongBinder());
        this.mDuration = in.readLong();
        this.mStatusBarTransitionDelay = in.readLong();
        this.mChangeNeedsSnapshot = in.readBoolean();
        this.mCallingApplication = IApplicationThread.Stub.asInterface(in.readStrongBinder());
        if (CoreRune.FW_MERGE_REMOTE_ANIMATION) {
            this.mMergeCallback = IRemoteAnimationMergeCallback.Stub.asInterface(in.readStrongBinder());
            this.mMergeDurationHint = in.readLong();
        }
    }

    public IRemoteAnimationRunner getRunner() {
        return this.mRunner;
    }

    public long getDuration() {
        return this.mDuration;
    }

    public long getStatusBarTransitionDelay() {
        return this.mStatusBarTransitionDelay;
    }

    public boolean getChangeNeedsSnapshot() {
        return this.mChangeNeedsSnapshot;
    }

    public void setCallingPidUid(int pid, int uid) {
        this.mCallingPid = pid;
        this.mCallingUid = uid;
    }

    public int getCallingPid() {
        return this.mCallingPid;
    }

    public int getCallingUid() {
        return this.mCallingUid;
    }

    public void registerAnimationMergeCallback(IRemoteAnimationMergeCallback mergeCallback, long mergeDurationHint) {
        this.mMergeCallback = mergeCallback;
        this.mMergeDurationHint = mergeDurationHint;
    }

    public IRemoteAnimationMergeCallback getMergeCallback() {
        return this.mMergeCallback;
    }

    public long getMergeDurationHint() {
        return this.mMergeDurationHint;
    }

    public IApplicationThread getCallingApplication() {
        return this.mCallingApplication;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStrongInterface(this.mRunner);
        dest.writeLong(this.mDuration);
        dest.writeLong(this.mStatusBarTransitionDelay);
        dest.writeBoolean(this.mChangeNeedsSnapshot);
        dest.writeStrongInterface(this.mCallingApplication);
        if (CoreRune.FW_MERGE_REMOTE_ANIMATION) {
            dest.writeStrongInterface(this.mMergeCallback);
            dest.writeLong(this.mMergeDurationHint);
        }
    }
}
