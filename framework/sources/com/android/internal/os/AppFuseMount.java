package com.android.internal.os;

import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import com.android.internal.util.Preconditions;

/* loaded from: classes5.dex */
public class AppFuseMount implements Parcelable {
    public static final Parcelable.Creator<AppFuseMount> CREATOR = new Parcelable.Creator<AppFuseMount>() { // from class: com.android.internal.os.AppFuseMount.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public AppFuseMount createFromParcel(Parcel in) {
            return new AppFuseMount(in.readInt(), (ParcelFileDescriptor) in.readParcelable(null, ParcelFileDescriptor.class));
        }

        @Override // android.os.Parcelable.Creator
        public AppFuseMount[] newArray(int size) {
            return new AppFuseMount[size];
        }
    };
    public final ParcelFileDescriptor fd;
    public final int mountPointId;

    public AppFuseMount(int mountPointId, ParcelFileDescriptor fd) {
        Preconditions.checkNotNull(fd);
        this.mountPointId = mountPointId;
        this.fd = fd;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mountPointId);
        dest.writeParcelable(this.fd, flags);
    }

    /* renamed from: com.android.internal.os.AppFuseMount$1 */
    /* loaded from: classes5.dex */
    class AnonymousClass1 implements Parcelable.Creator<AppFuseMount> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public AppFuseMount createFromParcel(Parcel in) {
            return new AppFuseMount(in.readInt(), (ParcelFileDescriptor) in.readParcelable(null, ParcelFileDescriptor.class));
        }

        @Override // android.os.Parcelable.Creator
        public AppFuseMount[] newArray(int size) {
            return new AppFuseMount[size];
        }
    }
}
