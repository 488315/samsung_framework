package android.content.integrity;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Map;

/* loaded from: classes.dex */
public class InstallerAllowedByManifestFormula extends IntegrityFormula implements Parcelable {
    public static final Parcelable.Creator<InstallerAllowedByManifestFormula> CREATOR = new Parcelable.Creator<InstallerAllowedByManifestFormula>() { // from class: android.content.integrity.InstallerAllowedByManifestFormula.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public InstallerAllowedByManifestFormula createFromParcel(Parcel in) {
            return new InstallerAllowedByManifestFormula(in);
        }

        @Override // android.os.Parcelable.Creator
        public InstallerAllowedByManifestFormula[] newArray(int size) {
            return new InstallerAllowedByManifestFormula[size];
        }
    };
    public static final String INSTALLER_CERTIFICATE_NOT_EVALUATED = "";

    /* synthetic */ InstallerAllowedByManifestFormula(Parcel parcel, InstallerAllowedByManifestFormulaIA installerAllowedByManifestFormulaIA) {
        this(parcel);
    }

    public InstallerAllowedByManifestFormula() {
    }

    private InstallerAllowedByManifestFormula(Parcel in) {
    }

    /* renamed from: android.content.integrity.InstallerAllowedByManifestFormula$1 */
    /* loaded from: classes.dex */
    class AnonymousClass1 implements Parcelable.Creator<InstallerAllowedByManifestFormula> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public InstallerAllowedByManifestFormula createFromParcel(Parcel in) {
            return new InstallerAllowedByManifestFormula(in);
        }

        @Override // android.os.Parcelable.Creator
        public InstallerAllowedByManifestFormula[] newArray(int size) {
            return new InstallerAllowedByManifestFormula[size];
        }
    }

    @Override // android.content.integrity.IntegrityFormula
    public int getTag() {
        return 4;
    }

    @Override // android.content.integrity.IntegrityFormula
    public boolean matches(AppInstallMetadata appInstallMetadata) {
        Map<String, String> allowedInstallersAndCertificates = appInstallMetadata.getAllowedInstallersAndCertificates();
        return allowedInstallersAndCertificates.isEmpty() || installerInAllowedInstallersFromManifest(appInstallMetadata, allowedInstallersAndCertificates);
    }

    @Override // android.content.integrity.IntegrityFormula
    public boolean isAppCertificateFormula() {
        return false;
    }

    @Override // android.content.integrity.IntegrityFormula
    public boolean isAppCertificateLineageFormula() {
        return false;
    }

    @Override // android.content.integrity.IntegrityFormula
    public boolean isInstallerFormula() {
        return true;
    }

    private static boolean installerInAllowedInstallersFromManifest(AppInstallMetadata appInstallMetadata, Map<String, String> allowedInstallersAndCertificates) {
        String installerPackage = appInstallMetadata.getInstallerName();
        if (!allowedInstallersAndCertificates.containsKey(installerPackage)) {
            return false;
        }
        if (!allowedInstallersAndCertificates.get(installerPackage).equals("")) {
            return appInstallMetadata.getInstallerCertificates().contains(allowedInstallersAndCertificates.get(appInstallMetadata.getInstallerName()));
        }
        return true;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
    }
}
