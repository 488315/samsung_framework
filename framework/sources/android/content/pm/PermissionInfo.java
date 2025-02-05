package android.content.pm;

import android.annotation.SystemApi;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Environment;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.NtpTrustedTime;
import com.android.internal.util.Parcelling;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collections;
import java.util.Set;

/* loaded from: classes.dex */
public class PermissionInfo extends PackageItemInfo implements Parcelable {
    public static final int FLAG_COSTS_MONEY = 1;
    public static final int FLAG_HARD_RESTRICTED = 4;
    public static final int FLAG_IMMUTABLY_RESTRICTED = 16;
    public static final int FLAG_INSTALLED = 1073741824;

    @SystemApi
    public static final int FLAG_REMOVED = 2;
    public static final int FLAG_SOFT_RESTRICTED = 8;
    public static final int PROTECTION_DANGEROUS = 1;
    public static final int PROTECTION_FLAG_APPOP = 64;

    @SystemApi
    public static final int PROTECTION_FLAG_APP_PREDICTOR = 2097152;

    @SystemApi
    public static final int PROTECTION_FLAG_COMPANION = 8388608;

    @SystemApi
    public static final int PROTECTION_FLAG_CONFIGURATOR = 524288;
    public static final int PROTECTION_FLAG_DEVELOPMENT = 32;

    @SystemApi
    public static final int PROTECTION_FLAG_DOCUMENTER = 262144;

    @SystemApi
    public static final int PROTECTION_FLAG_INCIDENT_REPORT_APPROVER = 1048576;
    public static final int PROTECTION_FLAG_INSTALLER = 256;
    public static final int PROTECTION_FLAG_INSTANT = 4096;

    @SystemApi
    public static final int PROTECTION_FLAG_KNOWN_SIGNER = 134217728;

    @SystemApi
    public static final int PROTECTION_FLAG_MODULE = 4194304;

    @SystemApi
    public static final int PROTECTION_FLAG_OEM = 16384;
    public static final int PROTECTION_FLAG_PRE23 = 128;
    public static final int PROTECTION_FLAG_PREINSTALLED = 1024;
    public static final int PROTECTION_FLAG_PRIVILEGED = 16;

    @SystemApi
    public static final int PROTECTION_FLAG_RECENTS = 33554432;

    @SystemApi
    public static final int PROTECTION_FLAG_RETAIL_DEMO = 16777216;

    @SystemApi
    public static final int PROTECTION_FLAG_ROLE = 67108864;
    public static final int PROTECTION_FLAG_RUNTIME_ONLY = 8192;
    public static final int PROTECTION_FLAG_SETUP = 2048;

    @Deprecated
    public static final int PROTECTION_FLAG_SYSTEM = 16;

    @SystemApi
    public static final int PROTECTION_FLAG_SYSTEM_TEXT_CLASSIFIER = 65536;

    @SystemApi
    public static final int PROTECTION_FLAG_VENDOR_PRIVILEGED = 32768;
    public static final int PROTECTION_FLAG_VERIFIER = 512;

    @SystemApi
    public static final int PROTECTION_FLAG_WELLBEING = 131072;
    public static final int PROTECTION_INTERNAL = 4;

    @Deprecated
    public static final int PROTECTION_MASK_BASE = 15;

    @Deprecated
    public static final int PROTECTION_MASK_FLAGS = 65520;
    public static final int PROTECTION_NORMAL = 0;
    public static final int PROTECTION_SIGNATURE = 2;

    @Deprecated
    public static final int PROTECTION_SIGNATURE_OR_SYSTEM = 3;

    @SystemApi
    public final String backgroundPermission;
    public int descriptionRes;
    public int flags;
    public String group;

    @SystemApi
    public Set<String> knownCerts;
    public CharSequence nonLocalizedDescription;

    @Deprecated
    public int protectionLevel;

    @SystemApi
    public int requestRes;
    private static final Parcelling.BuiltIn.ForStringSet sForStringSet = (Parcelling.BuiltIn.ForStringSet) Parcelling.Cache.getOrCreate(Parcelling.BuiltIn.ForStringSet.class);
    public static final Parcelable.Creator<PermissionInfo> CREATOR = new Parcelable.Creator<PermissionInfo>() { // from class: android.content.pm.PermissionInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PermissionInfo createFromParcel(Parcel source) {
            return new PermissionInfo(source);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PermissionInfo[] newArray(int size) {
            return new PermissionInfo[size];
        }
    };

    @Retention(RetentionPolicy.SOURCE)
    public @interface Flags {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Protection {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ProtectionFlags {
    }

    public static int fixProtectionLevel(int level) {
        if (level == 3) {
            level = 18;
        }
        if ((32768 & level) != 0 && (level & 16) == 0) {
            return level & (-32769);
        }
        return level;
    }

    public static String protectionToString(int level) {
        StringBuilder protLevel = new StringBuilder();
        switch (level & 15) {
            case 0:
                protLevel.append("normal");
                break;
            case 1:
                protLevel.append("dangerous");
                break;
            case 2:
                protLevel.append("signature");
                break;
            case 3:
                protLevel.append("signatureOrSystem");
                break;
            case 4:
                protLevel.append("internal");
                break;
            default:
                protLevel.append("????");
                break;
        }
        if ((level & 16) != 0) {
            protLevel.append("|privileged");
        }
        if ((level & 32) != 0) {
            protLevel.append("|development");
        }
        if ((level & 64) != 0) {
            protLevel.append("|appop");
        }
        if ((level & 128) != 0) {
            protLevel.append("|pre23");
        }
        if ((level & 256) != 0) {
            protLevel.append("|installer");
        }
        if ((level & 512) != 0) {
            protLevel.append("|verifier");
        }
        if ((level & 1024) != 0) {
            protLevel.append("|preinstalled");
        }
        if ((level & 2048) != 0) {
            protLevel.append("|setup");
        }
        if ((level & 4096) != 0) {
            protLevel.append("|instant");
        }
        if ((level & 8192) != 0) {
            protLevel.append("|runtime");
        }
        if ((level & 16384) != 0) {
            protLevel.append("|oem");
        }
        if ((32768 & level) != 0) {
            protLevel.append("|vendorPrivileged");
        }
        if ((65536 & level) != 0) {
            protLevel.append("|textClassifier");
        }
        if ((524288 & level) != 0) {
            protLevel.append("|configurator");
        }
        if ((1048576 & level) != 0) {
            protLevel.append("|incidentReportApprover");
        }
        if ((2097152 & level) != 0) {
            protLevel.append("|appPredictor");
        }
        if ((8388608 & level) != 0) {
            protLevel.append("|companion");
        }
        if ((16777216 & level) != 0) {
            protLevel.append("|retailDemo");
        }
        if ((33554432 & level) != 0) {
            protLevel.append("|recents");
        }
        if ((67108864 & level) != 0) {
            protLevel.append("|role");
        }
        if ((134217728 & level) != 0) {
            protLevel.append("|knownSigner");
        }
        if ((4194304 & level) != 0) {
            protLevel.append("|module");
        }
        return protLevel.toString();
    }

    public static String flagsToString(int flags) {
        StringBuilder sb = new StringBuilder(NavigationBarInflaterView.SIZE_MOD_START);
        while (flags != 0) {
            int flag = 1 << Integer.numberOfTrailingZeros(flags);
            flags &= ~flag;
            switch (flag) {
                case 1:
                    sb.append("costsMoney");
                    break;
                case 2:
                    sb.append(Environment.MEDIA_REMOVED);
                    break;
                case 4:
                    sb.append("hardRestricted");
                    break;
                case 8:
                    sb.append("softRestricted");
                    break;
                case 16:
                    sb.append("immutablyRestricted");
                    break;
                case 1073741824:
                    sb.append("installed");
                    break;
                default:
                    sb.append(flag);
                    break;
            }
            if (flags != 0) {
                sb.append(NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER);
            }
        }
        return sb.append(NavigationBarInflaterView.SIZE_MOD_END).toString();
    }

    public PermissionInfo(String backgroundPermission) {
        this.knownCerts = Collections.emptySet();
        this.backgroundPermission = backgroundPermission;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    @Deprecated
    public PermissionInfo() {
        this((String) null);
    }

    @Deprecated
    public PermissionInfo(PermissionInfo orig) {
        super(orig);
        this.knownCerts = Collections.emptySet();
        this.protectionLevel = orig.protectionLevel;
        this.flags = orig.flags;
        this.group = orig.group;
        this.backgroundPermission = orig.backgroundPermission;
        this.descriptionRes = orig.descriptionRes;
        this.requestRes = orig.requestRes;
        this.nonLocalizedDescription = orig.nonLocalizedDescription;
        this.knownCerts = orig.knownCerts;
    }

    public CharSequence loadDescription(PackageManager pm) {
        CharSequence label;
        if (this.nonLocalizedDescription != null) {
            return this.nonLocalizedDescription;
        }
        if (this.descriptionRes == 0 || (label = pm.getText(this.packageName, this.descriptionRes, null)) == null) {
            return null;
        }
        return label;
    }

    public int getProtection() {
        return this.protectionLevel & 15;
    }

    public int getProtectionFlags() {
        return this.protectionLevel & (-16);
    }

    public String toString() {
        return "PermissionInfo{" + Integer.toHexString(System.identityHashCode(this)) + " " + this.name + "}";
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.content.pm.PackageItemInfo, android.os.Parcelable
    public void writeToParcel(Parcel dest, int parcelableFlags) {
        super.writeToParcel(dest, parcelableFlags);
        dest.writeInt(this.protectionLevel);
        dest.writeInt(this.flags);
        dest.writeString8(this.group);
        dest.writeString8(this.backgroundPermission);
        dest.writeInt(this.descriptionRes);
        dest.writeInt(this.requestRes);
        TextUtils.writeToParcel(this.nonLocalizedDescription, dest, parcelableFlags);
        sForStringSet.parcel(this.knownCerts, dest, parcelableFlags);
    }

    public int calculateFootprint() {
        int size = this.name.length();
        if (this.nonLocalizedLabel != null) {
            size += this.nonLocalizedLabel.length();
        }
        if (this.nonLocalizedDescription != null) {
            return size + this.nonLocalizedDescription.length();
        }
        return size;
    }

    public boolean isHardRestricted() {
        return (this.flags & 4) != 0;
    }

    public boolean isSoftRestricted() {
        return (this.flags & 8) != 0;
    }

    public boolean isRestricted() {
        return isHardRestricted() || isSoftRestricted();
    }

    public boolean isAppOp() {
        return (this.protectionLevel & 64) != 0;
    }

    public boolean isRuntime() {
        return getProtection() == 1;
    }

    private PermissionInfo(Parcel source) {
        super(source);
        this.knownCerts = Collections.emptySet();
        this.protectionLevel = source.readInt();
        this.flags = source.readInt();
        this.group = source.readString8();
        this.backgroundPermission = source.readString8();
        this.descriptionRes = source.readInt();
        this.requestRes = source.readInt();
        this.nonLocalizedDescription = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(source);
        this.knownCerts = sForStringSet.unparcel(source);
    }
}
