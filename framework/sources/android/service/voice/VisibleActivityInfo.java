package android.service.voice;

import android.annotation.NonNull;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.service.voice.VoiceInteractionSession;
import com.android.internal.util.AnnotationValidations;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class VisibleActivityInfo implements Parcelable {
    public static final Parcelable.Creator<VisibleActivityInfo> CREATOR = new Parcelable.Creator<VisibleActivityInfo>() { // from class: android.service.voice.VisibleActivityInfo.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public VisibleActivityInfo[] newArray(int size) {
            return new VisibleActivityInfo[size];
        }

        @Override // android.os.Parcelable.Creator
        public VisibleActivityInfo createFromParcel(Parcel in) {
            return new VisibleActivityInfo(in);
        }
    };
    public static final int TYPE_ACTIVITY_ADDED = 1;
    public static final int TYPE_ACTIVITY_REMOVED = 2;
    private final IBinder mAssistToken;
    private final int mTaskId;

    public VisibleActivityInfo(int taskId, IBinder assistToken) {
        Objects.requireNonNull(assistToken);
        this.mTaskId = taskId;
        this.mAssistToken = assistToken;
    }

    public VoiceInteractionSession.ActivityId getActivityId() {
        return new VoiceInteractionSession.ActivityId(this.mTaskId, this.mAssistToken);
    }

    public String toString() {
        return "VisibleActivityInfo { taskId = " + this.mTaskId + ", assistToken = " + this.mAssistToken + " }";
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        VisibleActivityInfo that = (VisibleActivityInfo) o;
        if (this.mTaskId == that.mTaskId && Objects.equals(this.mAssistToken, that.mAssistToken)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int _hash = (1 * 31) + this.mTaskId;
        return (_hash * 31) + Objects.hashCode(this.mAssistToken);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mTaskId);
        dest.writeStrongBinder(this.mAssistToken);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    VisibleActivityInfo(Parcel in) {
        int taskId = in.readInt();
        IBinder assistToken = in.readStrongBinder();
        this.mTaskId = taskId;
        this.mAssistToken = assistToken;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) assistToken);
    }

    /* renamed from: android.service.voice.VisibleActivityInfo$1 */
    /* loaded from: classes3.dex */
    class AnonymousClass1 implements Parcelable.Creator<VisibleActivityInfo> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public VisibleActivityInfo[] newArray(int size) {
            return new VisibleActivityInfo[size];
        }

        @Override // android.os.Parcelable.Creator
        public VisibleActivityInfo createFromParcel(Parcel in) {
            return new VisibleActivityInfo(in);
        }
    }

    @Deprecated
    private void __metadata() {
    }
}
