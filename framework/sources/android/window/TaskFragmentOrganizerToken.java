package android.window;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.window.ITaskFragmentOrganizer;

/* loaded from: classes4.dex */
public final class TaskFragmentOrganizerToken implements Parcelable {
    public static final Parcelable.Creator<TaskFragmentOrganizerToken> CREATOR = new Parcelable.Creator<TaskFragmentOrganizerToken>() { // from class: android.window.TaskFragmentOrganizerToken.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public TaskFragmentOrganizerToken createFromParcel(Parcel in) {
            ITaskFragmentOrganizer realToken = ITaskFragmentOrganizer.Stub.asInterface(in.readStrongBinder());
            if (realToken == null) {
                return null;
            }
            return new TaskFragmentOrganizerToken(realToken);
        }

        @Override // android.os.Parcelable.Creator
        public TaskFragmentOrganizerToken[] newArray(int size) {
            return new TaskFragmentOrganizerToken[size];
        }
    };
    private final ITaskFragmentOrganizer mRealToken;

    public TaskFragmentOrganizerToken(ITaskFragmentOrganizer realToken) {
        this.mRealToken = realToken;
    }

    public IBinder asBinder() {
        return this.mRealToken.asBinder();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStrongInterface(this.mRealToken);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    /* renamed from: android.window.TaskFragmentOrganizerToken$1 */
    /* loaded from: classes4.dex */
    class AnonymousClass1 implements Parcelable.Creator<TaskFragmentOrganizerToken> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public TaskFragmentOrganizerToken createFromParcel(Parcel in) {
            ITaskFragmentOrganizer realToken = ITaskFragmentOrganizer.Stub.asInterface(in.readStrongBinder());
            if (realToken == null) {
                return null;
            }
            return new TaskFragmentOrganizerToken(realToken);
        }

        @Override // android.os.Parcelable.Creator
        public TaskFragmentOrganizerToken[] newArray(int size) {
            return new TaskFragmentOrganizerToken[size];
        }
    }

    public int hashCode() {
        return this.mRealToken.asBinder().hashCode();
    }

    public String toString() {
        return "TaskFragmentOrganizerToken{" + this.mRealToken + "}";
    }

    public boolean equals(Object obj) {
        return (obj instanceof TaskFragmentOrganizerToken) && this.mRealToken.asBinder() == ((TaskFragmentOrganizerToken) obj).asBinder();
    }
}
