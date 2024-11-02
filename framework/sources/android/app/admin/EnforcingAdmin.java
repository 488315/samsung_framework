package android.app.admin;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.UserHandle;
import java.util.Objects;

@SystemApi
/* loaded from: classes.dex */
public final class EnforcingAdmin implements Parcelable {
    public static final Parcelable.Creator<EnforcingAdmin> CREATOR = new Parcelable.Creator<EnforcingAdmin>() { // from class: android.app.admin.EnforcingAdmin.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public EnforcingAdmin createFromParcel(Parcel source) {
            return new EnforcingAdmin(source);
        }

        @Override // android.os.Parcelable.Creator
        public EnforcingAdmin[] newArray(int size) {
            return new EnforcingAdmin[size];
        }
    };
    private final Authority mAuthority;
    private final String mPackageName;
    private final UserHandle mUserHandle;

    /* synthetic */ EnforcingAdmin(Parcel parcel, EnforcingAdminIA enforcingAdminIA) {
        this(parcel);
    }

    public EnforcingAdmin(String packageName, Authority authority, UserHandle userHandle) {
        this.mPackageName = (String) Objects.requireNonNull(packageName);
        this.mAuthority = (Authority) Objects.requireNonNull(authority);
        this.mUserHandle = (UserHandle) Objects.requireNonNull(userHandle);
    }

    private EnforcingAdmin(Parcel source) {
        this.mPackageName = (String) Objects.requireNonNull(source.readString());
        this.mUserHandle = new UserHandle(source.readInt());
        this.mAuthority = (Authority) Objects.requireNonNull((Authority) source.readParcelable(Authority.class.getClassLoader()));
    }

    public Authority getAuthority() {
        return this.mAuthority;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public UserHandle getUserHandle() {
        return this.mUserHandle;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EnforcingAdmin other = (EnforcingAdmin) o;
        if (Objects.equals(this.mPackageName, other.mPackageName) && Objects.equals(this.mAuthority, other.mAuthority) && Objects.equals(this.mUserHandle, other.mUserHandle)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.mPackageName, this.mAuthority, this.mUserHandle);
    }

    public String toString() {
        return "EnforcingAdmin { mPackageName= " + this.mPackageName + ", mAuthority= " + this.mAuthority + ", mUserHandle= " + this.mUserHandle + " }";
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mPackageName);
        dest.writeInt(this.mUserHandle.getIdentifier());
        dest.writeParcelable(this.mAuthority, flags);
    }

    /* renamed from: android.app.admin.EnforcingAdmin$1 */
    /* loaded from: classes.dex */
    class AnonymousClass1 implements Parcelable.Creator<EnforcingAdmin> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public EnforcingAdmin createFromParcel(Parcel source) {
            return new EnforcingAdmin(source);
        }

        @Override // android.os.Parcelable.Creator
        public EnforcingAdmin[] newArray(int size) {
            return new EnforcingAdmin[size];
        }
    }
}
