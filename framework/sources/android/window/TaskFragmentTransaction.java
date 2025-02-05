package android.window;

import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.SurfaceControl;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class TaskFragmentTransaction implements Parcelable {
    public static final Parcelable.Creator<TaskFragmentTransaction> CREATOR = new Parcelable.Creator<TaskFragmentTransaction>() { // from class: android.window.TaskFragmentTransaction.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TaskFragmentTransaction createFromParcel(Parcel in) {
            return new TaskFragmentTransaction(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TaskFragmentTransaction[] newArray(int size) {
            return new TaskFragmentTransaction[size];
        }
    };
    public static final int TYPE_ACTIVITY_REPARENTED_TO_TASK = 6;
    public static final int TYPE_TASK_FRAGMENT_APPEARED = 1;
    public static final int TYPE_TASK_FRAGMENT_ERROR = 5;
    public static final int TYPE_TASK_FRAGMENT_INFO_CHANGED = 2;
    public static final int TYPE_TASK_FRAGMENT_PARENT_INFO_CHANGED = 4;
    public static final int TYPE_TASK_FRAGMENT_VANISHED = 3;
    private final ArrayList<Change> mChanges;
    private final IBinder mTransactionToken;

    @Retention(RetentionPolicy.SOURCE)
    @interface ChangeType {
    }

    public TaskFragmentTransaction() {
        this.mChanges = new ArrayList<>();
        this.mTransactionToken = new Binder();
    }

    private TaskFragmentTransaction(Parcel in) {
        this.mChanges = new ArrayList<>();
        this.mTransactionToken = in.readStrongBinder();
        in.readTypedList(this.mChanges, Change.CREATOR);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStrongBinder(this.mTransactionToken);
        dest.writeTypedList(this.mChanges);
    }

    public IBinder getTransactionToken() {
        return this.mTransactionToken;
    }

    public void addChange(Change change) {
        if (change != null) {
            this.mChanges.add(change);
        }
    }

    public boolean isEmpty() {
        return this.mChanges.isEmpty();
    }

    public List<Change> getChanges() {
        return this.mChanges;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("TaskFragmentTransaction{token=");
        sb.append(this.mTransactionToken);
        sb.append(" changes=[");
        for (int i = 0; i < this.mChanges.size(); i++) {
            if (i > 0) {
                sb.append(',');
            }
            sb.append(this.mChanges.get(i));
        }
        sb.append("]}");
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static final class Change implements Parcelable {
        public static final Parcelable.Creator<Change> CREATOR = new Parcelable.Creator<Change>() { // from class: android.window.TaskFragmentTransaction.Change.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Change createFromParcel(Parcel in) {
                return new Change(in);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Change[] newArray(int size) {
                return new Change[size];
            }
        };
        private Intent mActivityIntent;
        private IBinder mActivityToken;
        private Bundle mErrorBundle;
        private IBinder mErrorCallbackToken;
        private IBinder mOtherActivityToken;
        private SurfaceControl mSurfaceControl;
        private TaskFragmentInfo mTaskFragmentInfo;
        private TaskFragmentParentInfo mTaskFragmentParentInfo;
        private IBinder mTaskFragmentToken;
        private int mTaskId;
        private final int mType;

        public Change(int type) {
            this.mType = type;
        }

        private Change(Parcel in) {
            this.mType = in.readInt();
            this.mTaskFragmentToken = in.readStrongBinder();
            this.mTaskFragmentInfo = (TaskFragmentInfo) in.readTypedObject(TaskFragmentInfo.CREATOR);
            this.mTaskId = in.readInt();
            this.mErrorCallbackToken = in.readStrongBinder();
            this.mErrorBundle = in.readBundle(TaskFragmentTransaction.class.getClassLoader());
            this.mActivityIntent = (Intent) in.readTypedObject(Intent.CREATOR);
            this.mActivityToken = in.readStrongBinder();
            this.mTaskFragmentParentInfo = (TaskFragmentParentInfo) in.readTypedObject(TaskFragmentParentInfo.CREATOR);
            this.mSurfaceControl = (SurfaceControl) in.readTypedObject(SurfaceControl.CREATOR);
            this.mOtherActivityToken = in.readStrongBinder();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.mType);
            dest.writeStrongBinder(this.mTaskFragmentToken);
            dest.writeTypedObject(this.mTaskFragmentInfo, flags);
            dest.writeInt(this.mTaskId);
            dest.writeStrongBinder(this.mErrorCallbackToken);
            dest.writeBundle(this.mErrorBundle);
            dest.writeTypedObject(this.mActivityIntent, flags);
            dest.writeStrongBinder(this.mActivityToken);
            dest.writeTypedObject(this.mTaskFragmentParentInfo, flags);
            dest.writeTypedObject(this.mSurfaceControl, flags);
            dest.writeStrongBinder(this.mOtherActivityToken);
        }

        public Change setTaskFragmentToken(IBinder taskFragmentToken) {
            this.mTaskFragmentToken = (IBinder) Objects.requireNonNull(taskFragmentToken);
            return this;
        }

        public Change setTaskFragmentInfo(TaskFragmentInfo info) {
            this.mTaskFragmentInfo = (TaskFragmentInfo) Objects.requireNonNull(info);
            return this;
        }

        public Change setTaskId(int taskId) {
            this.mTaskId = taskId;
            return this;
        }

        public Change setErrorCallbackToken(IBinder errorCallbackToken) {
            this.mErrorCallbackToken = errorCallbackToken;
            return this;
        }

        public Change setErrorBundle(Bundle errorBundle) {
            this.mErrorBundle = (Bundle) Objects.requireNonNull(errorBundle);
            return this;
        }

        public Change setActivityIntent(Intent intent) {
            this.mActivityIntent = (Intent) Objects.requireNonNull(intent);
            return this;
        }

        public Change setActivityToken(IBinder activityToken) {
            this.mActivityToken = (IBinder) Objects.requireNonNull(activityToken);
            return this;
        }

        public Change setOtherActivityToken(IBinder activityToken) {
            this.mOtherActivityToken = (IBinder) Objects.requireNonNull(activityToken);
            return this;
        }

        public Change setTaskFragmentParentInfo(TaskFragmentParentInfo info) {
            this.mTaskFragmentParentInfo = (TaskFragmentParentInfo) Objects.requireNonNull(info);
            return this;
        }

        public Change setTaskFragmentSurfaceControl(SurfaceControl sc) {
            this.mSurfaceControl = sc;
            return this;
        }

        public int getType() {
            return this.mType;
        }

        public IBinder getTaskFragmentToken() {
            return this.mTaskFragmentToken;
        }

        public TaskFragmentInfo getTaskFragmentInfo() {
            return this.mTaskFragmentInfo;
        }

        public int getTaskId() {
            return this.mTaskId;
        }

        public IBinder getErrorCallbackToken() {
            return this.mErrorCallbackToken;
        }

        public Bundle getErrorBundle() {
            return this.mErrorBundle != null ? this.mErrorBundle : Bundle.EMPTY;
        }

        public Intent getActivityIntent() {
            return this.mActivityIntent;
        }

        public IBinder getActivityToken() {
            return this.mActivityToken;
        }

        public IBinder getOtherActivityToken() {
            return this.mOtherActivityToken;
        }

        public TaskFragmentParentInfo getTaskFragmentParentInfo() {
            return this.mTaskFragmentParentInfo;
        }

        public SurfaceControl getTaskFragmentSurfaceControl() {
            return this.mSurfaceControl;
        }

        public String toString() {
            return "Change{ type=" + this.mType + " }";
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }
    }
}
