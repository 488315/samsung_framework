package android.app.admin;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@SystemApi
/* loaded from: classes.dex */
public final class RoleAuthority extends Authority {
    public static final Parcelable.Creator<RoleAuthority> CREATOR = new Parcelable.Creator<RoleAuthority>() { // from class: android.app.admin.RoleAuthority.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public RoleAuthority createFromParcel(Parcel source) {
            return new RoleAuthority(source);
        }

        @Override // android.os.Parcelable.Creator
        public RoleAuthority[] newArray(int size) {
            return new RoleAuthority[size];
        }
    };
    private final Set<String> mRoles;

    /* synthetic */ RoleAuthority(Parcel parcel, RoleAuthorityIA roleAuthorityIA) {
        this(parcel);
    }

    public RoleAuthority(Set<String> roles) {
        this.mRoles = new HashSet((Collection) Objects.requireNonNull(roles));
    }

    private RoleAuthority(Parcel source) {
        this.mRoles = new HashSet();
        int size = source.readInt();
        for (int i = 0; i < size; i++) {
            this.mRoles.add(source.readString());
        }
    }

    public Set<String> getRoles() {
        return this.mRoles;
    }

    @Override // android.app.admin.Authority, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mRoles.size());
        for (String role : this.mRoles) {
            dest.writeString(role);
        }
    }

    @Override // android.app.admin.Authority
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RoleAuthority other = (RoleAuthority) o;
        return Objects.equals(this.mRoles, other.mRoles);
    }

    @Override // android.app.admin.Authority
    public int hashCode() {
        return Objects.hash(this.mRoles);
    }

    public String toString() {
        return "RoleAuthority { mRoles= " + this.mRoles + " }";
    }

    /* renamed from: android.app.admin.RoleAuthority$1 */
    /* loaded from: classes.dex */
    class AnonymousClass1 implements Parcelable.Creator<RoleAuthority> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public RoleAuthority createFromParcel(Parcel source) {
            return new RoleAuthority(source);
        }

        @Override // android.os.Parcelable.Creator
        public RoleAuthority[] newArray(int size) {
            return new RoleAuthority[size];
        }
    }
}
