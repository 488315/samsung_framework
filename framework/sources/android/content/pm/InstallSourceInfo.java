package android.content.pm;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public final class InstallSourceInfo implements Parcelable {
    public static final Parcelable.Creator<InstallSourceInfo> CREATOR = new Parcelable.Creator<InstallSourceInfo>() { // from class: android.content.pm.InstallSourceInfo.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public InstallSourceInfo createFromParcel(Parcel source) {
            return new InstallSourceInfo(source);
        }

        @Override // android.os.Parcelable.Creator
        public InstallSourceInfo[] newArray(int size) {
            return new InstallSourceInfo[size];
        }
    };
    private final String mInitiatingPackageName;
    private final SigningInfo mInitiatingPackageSigningInfo;
    private final String mInstallingPackageName;
    private final String mOriginatingPackageName;
    private final int mPackageSource;
    private final String mUpdateOwnerPackageName;

    /* synthetic */ InstallSourceInfo(Parcel parcel, InstallSourceInfoIA installSourceInfoIA) {
        this(parcel);
    }

    public InstallSourceInfo(String initiatingPackageName, SigningInfo initiatingPackageSigningInfo, String originatingPackageName, String installingPackageName) {
        this(initiatingPackageName, initiatingPackageSigningInfo, originatingPackageName, installingPackageName, null, 0);
    }

    public InstallSourceInfo(String initiatingPackageName, SigningInfo initiatingPackageSigningInfo, String originatingPackageName, String installingPackageName, String updateOwnerPackageName, int packageSource) {
        this.mInitiatingPackageName = initiatingPackageName;
        this.mInitiatingPackageSigningInfo = initiatingPackageSigningInfo;
        this.mOriginatingPackageName = originatingPackageName;
        this.mInstallingPackageName = installingPackageName;
        this.mUpdateOwnerPackageName = updateOwnerPackageName;
        this.mPackageSource = packageSource;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        SigningInfo signingInfo = this.mInitiatingPackageSigningInfo;
        if (signingInfo == null) {
            return 0;
        }
        return signingInfo.describeContents();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mInitiatingPackageName);
        dest.writeParcelable(this.mInitiatingPackageSigningInfo, flags);
        dest.writeString(this.mOriginatingPackageName);
        dest.writeString(this.mInstallingPackageName);
        dest.writeString8(this.mUpdateOwnerPackageName);
        dest.writeInt(this.mPackageSource);
    }

    private InstallSourceInfo(Parcel source) {
        this.mInitiatingPackageName = source.readString();
        this.mInitiatingPackageSigningInfo = (SigningInfo) source.readParcelable(SigningInfo.class.getClassLoader(), SigningInfo.class);
        this.mOriginatingPackageName = source.readString();
        this.mInstallingPackageName = source.readString();
        this.mUpdateOwnerPackageName = source.readString8();
        this.mPackageSource = source.readInt();
    }

    public String getInitiatingPackageName() {
        return this.mInitiatingPackageName;
    }

    public SigningInfo getInitiatingPackageSigningInfo() {
        return this.mInitiatingPackageSigningInfo;
    }

    public String getOriginatingPackageName() {
        return this.mOriginatingPackageName;
    }

    public String getInstallingPackageName() {
        return this.mInstallingPackageName;
    }

    public String getUpdateOwnerPackageName() {
        return this.mUpdateOwnerPackageName;
    }

    public int getPackageSource() {
        return this.mPackageSource;
    }

    /* renamed from: android.content.pm.InstallSourceInfo$1 */
    /* loaded from: classes.dex */
    class AnonymousClass1 implements Parcelable.Creator<InstallSourceInfo> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public InstallSourceInfo createFromParcel(Parcel source) {
            return new InstallSourceInfo(source);
        }

        @Override // android.os.Parcelable.Creator
        public InstallSourceInfo[] newArray(int size) {
            return new InstallSourceInfo[size];
        }
    }
}
