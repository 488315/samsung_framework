package android.content.pm;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseArray;

/* loaded from: classes.dex */
public class InstrumentationInfo extends PackageItemInfo implements Parcelable {
    public static final Parcelable.Creator<InstrumentationInfo> CREATOR = new Parcelable.Creator<InstrumentationInfo>() { // from class: android.content.pm.InstrumentationInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InstrumentationInfo createFromParcel(Parcel source) {
            return new InstrumentationInfo(source);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InstrumentationInfo[] newArray(int size) {
            return new InstrumentationInfo[size];
        }
    };
    public String credentialProtectedDataDir;
    public String dataDir;
    public String deviceProtectedDataDir;
    public boolean functionalTest;
    public boolean handleProfiling;
    public String nativeLibraryDir;
    public String primaryCpuAbi;
    public String publicSourceDir;
    public String secondaryCpuAbi;
    public String secondaryNativeLibraryDir;
    public String sourceDir;
    public SparseArray<int[]> splitDependencies;
    public String[] splitNames;
    public String[] splitPublicSourceDirs;
    public String[] splitSourceDirs;
    public String targetPackage;
    public String targetProcesses;

    public InstrumentationInfo() {
    }

    public InstrumentationInfo(InstrumentationInfo orig) {
        super(orig);
        this.targetPackage = orig.targetPackage;
        this.targetProcesses = orig.targetProcesses;
        this.sourceDir = orig.sourceDir;
        this.publicSourceDir = orig.publicSourceDir;
        this.splitNames = orig.splitNames;
        this.splitSourceDirs = orig.splitSourceDirs;
        this.splitPublicSourceDirs = orig.splitPublicSourceDirs;
        this.splitDependencies = orig.splitDependencies;
        this.dataDir = orig.dataDir;
        this.deviceProtectedDataDir = orig.deviceProtectedDataDir;
        this.credentialProtectedDataDir = orig.credentialProtectedDataDir;
        this.primaryCpuAbi = orig.primaryCpuAbi;
        this.secondaryCpuAbi = orig.secondaryCpuAbi;
        this.nativeLibraryDir = orig.nativeLibraryDir;
        this.secondaryNativeLibraryDir = orig.secondaryNativeLibraryDir;
        this.handleProfiling = orig.handleProfiling;
        this.functionalTest = orig.functionalTest;
    }

    public String toString() {
        return "InstrumentationInfo{" + Integer.toHexString(System.identityHashCode(this)) + " " + this.packageName + "}";
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.content.pm.PackageItemInfo, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString8(this.targetPackage);
        parcel.writeString8(this.targetProcesses);
        parcel.writeString8(this.sourceDir);
        parcel.writeString8(this.publicSourceDir);
        parcel.writeString8Array(this.splitNames);
        parcel.writeString8Array(this.splitSourceDirs);
        parcel.writeString8Array(this.splitPublicSourceDirs);
        parcel.writeSparseArray(this.splitDependencies);
        parcel.writeString8(this.dataDir);
        parcel.writeString8(this.deviceProtectedDataDir);
        parcel.writeString8(this.credentialProtectedDataDir);
        parcel.writeString8(this.primaryCpuAbi);
        parcel.writeString8(this.secondaryCpuAbi);
        parcel.writeString8(this.nativeLibraryDir);
        parcel.writeString8(this.secondaryNativeLibraryDir);
        parcel.writeInt(this.handleProfiling ? 1 : 0);
        parcel.writeInt(this.functionalTest ? 1 : 0);
    }

    private InstrumentationInfo(Parcel source) {
        super(source);
        this.targetPackage = source.readString8();
        this.targetProcesses = source.readString8();
        this.sourceDir = source.readString8();
        this.publicSourceDir = source.readString8();
        this.splitNames = source.createString8Array();
        this.splitSourceDirs = source.createString8Array();
        this.splitPublicSourceDirs = source.createString8Array();
        this.splitDependencies = source.readSparseArray(null);
        this.dataDir = source.readString8();
        this.deviceProtectedDataDir = source.readString8();
        this.credentialProtectedDataDir = source.readString8();
        this.primaryCpuAbi = source.readString8();
        this.secondaryCpuAbi = source.readString8();
        this.nativeLibraryDir = source.readString8();
        this.secondaryNativeLibraryDir = source.readString8();
        this.handleProfiling = source.readInt() != 0;
        this.functionalTest = source.readInt() != 0;
    }

    public void copyTo(ApplicationInfo ai) {
        ai.packageName = this.packageName;
        ai.sourceDir = this.sourceDir;
        ai.publicSourceDir = this.publicSourceDir;
        ai.splitNames = this.splitNames;
        ai.splitSourceDirs = this.splitSourceDirs;
        ai.splitPublicSourceDirs = this.splitPublicSourceDirs;
        ai.splitDependencies = this.splitDependencies;
        ai.dataDir = this.dataDir;
        ai.deviceProtectedDataDir = this.deviceProtectedDataDir;
        ai.credentialProtectedDataDir = this.credentialProtectedDataDir;
        ai.primaryCpuAbi = this.primaryCpuAbi;
        ai.secondaryCpuAbi = this.secondaryCpuAbi;
        ai.nativeLibraryDir = this.nativeLibraryDir;
        ai.secondaryNativeLibraryDir = this.secondaryNativeLibraryDir;
    }
}
