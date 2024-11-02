package android.app.admin;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;

@SystemApi
/* loaded from: classes.dex */
public final class UnknownAuthority extends Authority {
    public static final UnknownAuthority UNKNOWN_AUTHORITY = new UnknownAuthority();
    public static final Parcelable.Creator<UnknownAuthority> CREATOR = new Parcelable.Creator<UnknownAuthority>() { // from class: android.app.admin.UnknownAuthority.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public UnknownAuthority createFromParcel(Parcel source) {
            return UnknownAuthority.UNKNOWN_AUTHORITY;
        }

        @Override // android.os.Parcelable.Creator
        public UnknownAuthority[] newArray(int size) {
            return new UnknownAuthority[size];
        }
    };

    public String toString() {
        return "DefaultAuthority {}";
    }

    @Override // android.app.admin.Authority
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        return o != null && getClass() == o.getClass();
    }

    @Override // android.app.admin.Authority
    public int hashCode() {
        return 0;
    }

    @Override // android.app.admin.Authority, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
    }

    /* renamed from: android.app.admin.UnknownAuthority$1 */
    /* loaded from: classes.dex */
    class AnonymousClass1 implements Parcelable.Creator<UnknownAuthority> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public UnknownAuthority createFromParcel(Parcel source) {
            return UnknownAuthority.UNKNOWN_AUTHORITY;
        }

        @Override // android.os.Parcelable.Creator
        public UnknownAuthority[] newArray(int size) {
            return new UnknownAuthority[size];
        }
    }
}
