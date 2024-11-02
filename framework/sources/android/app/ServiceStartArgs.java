package android.app;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public class ServiceStartArgs implements Parcelable {
    public static final Parcelable.Creator<ServiceStartArgs> CREATOR = new Parcelable.Creator<ServiceStartArgs>() { // from class: android.app.ServiceStartArgs.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public ServiceStartArgs createFromParcel(Parcel in) {
            return new ServiceStartArgs(in);
        }

        @Override // android.os.Parcelable.Creator
        public ServiceStartArgs[] newArray(int size) {
            return new ServiceStartArgs[size];
        }
    };
    public final Intent args;
    public final int flags;
    public final int startId;
    public final boolean taskRemoved;

    public ServiceStartArgs(boolean _taskRemoved, int _startId, int _flags, Intent _args) {
        this.taskRemoved = _taskRemoved;
        this.startId = _startId;
        this.flags = _flags;
        this.args = _args;
    }

    public String toString() {
        return "ServiceStartArgs{taskRemoved=" + this.taskRemoved + ", startId=" + this.startId + ", flags=0x" + Integer.toHexString(this.flags) + ", args=" + this.args + "}";
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.taskRemoved ? 1 : 0);
        parcel.writeInt(this.startId);
        parcel.writeInt(this.flags);
        if (this.args != null) {
            parcel.writeInt(1);
            this.args.writeToParcel(parcel, 0);
        } else {
            parcel.writeInt(0);
        }
    }

    /* renamed from: android.app.ServiceStartArgs$1 */
    /* loaded from: classes.dex */
    class AnonymousClass1 implements Parcelable.Creator<ServiceStartArgs> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public ServiceStartArgs createFromParcel(Parcel in) {
            return new ServiceStartArgs(in);
        }

        @Override // android.os.Parcelable.Creator
        public ServiceStartArgs[] newArray(int size) {
            return new ServiceStartArgs[size];
        }
    }

    public ServiceStartArgs(Parcel in) {
        this.taskRemoved = in.readInt() != 0;
        this.startId = in.readInt();
        this.flags = in.readInt();
        if (in.readInt() != 0) {
            this.args = Intent.CREATOR.createFromParcel(in);
        } else {
            this.args = null;
        }
    }
}
