package android.content.pm;

import android.annotation.SystemApi;
import android.app.compat.CompatChanges;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.os.UserHandle;
import android.os.storage.StorageManager;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Printer;
import android.util.SparseArray;
import android.util.proto.ProtoOutputStream;
import com.android.internal.R;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.Parcelling;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/* loaded from: classes.dex */
public class ApplicationInfo extends PackageItemInfo implements Parcelable {
    public static final int AUTO_REVOKE_ALLOWED = 0;
    public static final int AUTO_REVOKE_DISALLOWED = 2;
    public static final int AUTO_REVOKE_DISCOURAGED = 1;
    public static final int CATEGORY_ACCESSIBILITY = 8;
    public static final int CATEGORY_AUDIO = 1;
    public static final int CATEGORY_GAME = 0;
    public static final int CATEGORY_IMAGE = 3;
    public static final int CATEGORY_MAPS = 6;
    public static final int CATEGORY_NEWS = 5;
    public static final int CATEGORY_PRODUCTIVITY = 7;
    public static final int CATEGORY_SOCIAL = 4;
    public static final int CATEGORY_UNDEFINED = -1;
    public static final int CATEGORY_VIDEO = 2;
    public static final int FLAG_ALLOW_BACKUP = 32768;
    public static final int FLAG_ALLOW_CLEAR_USER_DATA = 64;
    public static final int FLAG_ALLOW_TASK_REPARENTING = 32;
    public static final int FLAG_DEBUGGABLE = 2;
    public static final int FLAG_EXTERNAL_STORAGE = 262144;
    public static final int FLAG_EXTRACT_NATIVE_LIBS = 268435456;
    public static final int FLAG_FACTORY_TEST = 16;
    public static final int FLAG_FULL_BACKUP_ONLY = 67108864;
    public static final int FLAG_HARDWARE_ACCELERATED = 536870912;
    public static final int FLAG_HAS_CODE = 4;
    public static final int FLAG_INSTALLED = 8388608;
    public static final int FLAG_IS_DATA_ONLY = 16777216;

    @Deprecated
    public static final int FLAG_IS_GAME = 33554432;
    public static final int FLAG_KILL_AFTER_RESTORE = 65536;
    public static final int FLAG_LARGE_HEAP = 1048576;
    public static final int FLAG_MULTIARCH = Integer.MIN_VALUE;
    public static final int FLAG_PERSISTENT = 8;
    public static final int FLAG_RESIZEABLE_FOR_SCREENS = 4096;
    public static final int FLAG_RESTORE_ANY_VERSION = 131072;
    public static final int FLAG_STOPPED = 2097152;
    public static final int FLAG_SUPPORTS_LARGE_SCREENS = 2048;
    public static final int FLAG_SUPPORTS_NORMAL_SCREENS = 1024;
    public static final int FLAG_SUPPORTS_RTL = 4194304;

    @Deprecated
    public static final int FLAG_SUPPORTS_SCREEN_DENSITIES = 8192;
    public static final int FLAG_SUPPORTS_SMALL_SCREENS = 512;
    public static final int FLAG_SUPPORTS_XLARGE_SCREENS = 524288;
    public static final int FLAG_SUSPENDED = 1073741824;
    public static final int FLAG_SYSTEM = 1;
    public static final int FLAG_TEST_ONLY = 256;
    public static final int FLAG_UPDATED_SYSTEM_APP = 128;
    public static final int FLAG_USES_CLEARTEXT_TRAFFIC = 134217728;
    public static final int FLAG_VM_SAFE_MODE = 16384;
    public static final int GWP_ASAN_ALWAYS = 1;
    public static final int GWP_ASAN_DEFAULT = -1;
    public static final int GWP_ASAN_NEVER = 0;

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static final int HIDDEN_API_ENFORCEMENT_DEFAULT = -1;

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static final int HIDDEN_API_ENFORCEMENT_DISABLED = 0;

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static final int HIDDEN_API_ENFORCEMENT_ENABLED = 2;

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static final int HIDDEN_API_ENFORCEMENT_JUST_WARN = 1;
    private static final int HIDDEN_API_ENFORCEMENT_MAX = 2;
    private static final int HIDDEN_API_ENFORCEMENT_MIN = -1;
    public static final int MEMTAG_ASYNC = 1;
    public static final int MEMTAG_DEFAULT = -1;
    public static final int MEMTAG_OFF = 0;
    public static final int MEMTAG_SYNC = 2;
    public static final String METADATA_PRELOADED_FONTS = "preloaded_fonts";
    public static final int PRIVATE_FLAG_ACTIVITIES_RESIZE_MODE_RESIZEABLE = 1024;
    public static final int PRIVATE_FLAG_ACTIVITIES_RESIZE_MODE_RESIZEABLE_VIA_SDK_VERSION = 4096;
    public static final int PRIVATE_FLAG_ACTIVITIES_RESIZE_MODE_UNRESIZEABLE = 2048;
    public static final int PRIVATE_FLAG_ALLOW_AUDIO_PLAYBACK_CAPTURE = 134217728;
    public static final int PRIVATE_FLAG_ALLOW_CLEAR_USER_DATA_ON_FAILED_RESTORE = 67108864;
    public static final int PRIVATE_FLAG_ALLOW_NATIVE_HEAP_POINTER_TAGGING = Integer.MIN_VALUE;
    public static final int PRIVATE_FLAG_BACKUP_IN_FOREGROUND = 8192;
    public static final int PRIVATE_FLAG_CANT_SAVE_STATE = 2;
    public static final int PRIVATE_FLAG_DEFAULT_TO_DEVICE_PROTECTED_STORAGE = 32;
    public static final int PRIVATE_FLAG_DIRECT_BOOT_AWARE = 64;
    public static final int PRIVATE_FLAG_EXT_ALLOWLISTED_FOR_HIDDEN_APIS = 16;
    public static final int PRIVATE_FLAG_EXT_ATTRIBUTIONS_ARE_USER_VISIBLE = 4;
    public static final int PRIVATE_FLAG_EXT_CPU_OVERRIDE = 32;
    public static final int PRIVATE_FLAG_EXT_ENABLE_ON_BACK_INVOKED_CALLBACK = 8;
    public static final int PRIVATE_FLAG_EXT_NOT_LAUNCHED = 64;
    public static final int PRIVATE_FLAG_EXT_PROFILEABLE = 1;
    public static final int PRIVATE_FLAG_EXT_REQUEST_FOREGROUND_SERVICE_EXEMPTION = 2;
    public static final int PRIVATE_FLAG_HAS_DOMAIN_URLS = 16;
    public static final int PRIVATE_FLAG_HAS_FRAGILE_USER_DATA = 16777216;
    public static final int PRIVATE_FLAG_HIDDEN = 1;
    public static final int PRIVATE_FLAG_INSTANT = 128;
    public static final int PRIVATE_FLAG_ISOLATED_SPLIT_LOADING = 32768;
    public static final int PRIVATE_FLAG_IS_RESOURCE_OVERLAY = 268435456;
    public static final int PRIVATE_FLAG_ODM = 1073741824;
    public static final int PRIVATE_FLAG_OEM = 131072;
    public static final int PRIVATE_FLAG_PARTIALLY_DIRECT_BOOT_AWARE = 256;
    public static final int PRIVATE_FLAG_PRIVILEGED = 8;
    public static final int PRIVATE_FLAG_PRODUCT = 524288;
    public static final int PRIVATE_FLAG_PROFILEABLE_BY_SHELL = 8388608;
    public static final int PRIVATE_FLAG_REQUEST_LEGACY_EXTERNAL_STORAGE = 536870912;
    public static final int PRIVATE_FLAG_REQUIRED_FOR_SYSTEM_USER = 512;
    public static final int PRIVATE_FLAG_SIGNED_WITH_PLATFORM_KEY = 1048576;
    public static final int PRIVATE_FLAG_STATIC_SHARED_LIBRARY = 16384;
    public static final int PRIVATE_FLAG_SYSTEM_EXT = 2097152;
    public static final int PRIVATE_FLAG_USES_NON_SDK_API = 4194304;
    public static final int PRIVATE_FLAG_USE_EMBEDDED_DEX = 33554432;
    public static final int PRIVATE_FLAG_VENDOR = 262144;
    public static final int PRIVATE_FLAG_VIRTUAL_PRELOAD = 65536;
    public static final int RAW_EXTERNAL_STORAGE_ACCESS_DEFAULT = 0;
    public static final int RAW_EXTERNAL_STORAGE_ACCESS_NOT_REQUESTED = 2;
    public static final int RAW_EXTERNAL_STORAGE_ACCESS_REQUESTED = 1;
    public static final int ZEROINIT_DEFAULT = -1;
    public static final int ZEROINIT_DISABLED = 0;
    public static final int ZEROINIT_ENABLED = 1;
    public boolean allowCrossUidActivitySwitchFromBelow;
    public String appComponentFactory;
    public String backupAgentName;
    public int category;
    public String classLoaderName;
    public String className;
    public int compatibleWidthLimitDp;
    public int compileSdkVersion;
    public String compileSdkVersionCodename;
    public long createTimestamp;

    @SystemApi
    public String credentialProtectedDataDir;
    public boolean crossProfile;
    public String dataDir;
    public int dataExtractionRulesRes;
    public int descriptionRes;
    public String deviceProtectedDataDir;
    public boolean enabled;
    public int enabledSetting;
    public int flags;
    public int fullBackupContent;
    private int gwpAsanMode;
    public boolean hiddenUntilInstalled;
    public int iconRes;
    public int installLocation;
    public int largestWidthLimitDp;
    private int localeConfigRes;
    public long longVersionCode;
    private ArrayMap<String, String> mAppClassNamesByProcess;
    private int mHiddenApiPolicy;
    private Set<String> mKnownActivityEmbeddingCerts;
    public String manageSpaceActivityName;
    public float maxAspectRatio;
    private int memtagMode;
    public float minAspectRatio;
    public int minSdkVersion;
    private int nativeHeapZeroInitialized;
    public String nativeLibraryDir;
    public String nativeLibraryRootDir;
    public boolean nativeLibraryRootRequiresIsa;
    public int networkSecurityConfigRes;
    public List<SharedLibraryInfo> optionalSharedLibraryInfos;
    public String[] overlayPaths;
    public String permission;
    public String primaryCpuAbi;
    public int privateFlags;
    public int privateFlagsExt;
    public String processName;
    public String publicSourceDir;
    private Boolean requestRawExternalStorageAccess;
    public int requiresSmallestWidthDp;
    public String[] resourceDirs;
    public int roundIconRes;
    public String scanPublicSourceDir;
    public String scanSourceDir;
    public String seInfo;
    public String seInfoUser;
    public String secondaryCpuAbi;
    public String secondaryNativeLibraryDir;
    public String[] sharedLibraryFiles;
    public List<SharedLibraryInfo> sharedLibraryInfos;
    public String sourceDir;
    public String[] splitClassLoaderNames;
    public SparseArray<int[]> splitDependencies;
    public String[] splitNames;
    public String[] splitPublicSourceDirs;
    public String[] splitSourceDirs;
    public UUID storageUuid;

    @SystemApi
    public int targetSandboxVersion;
    public int targetSdkVersion;
    public String taskAffinity;
    public int theme;
    public int uiOptions;
    public int uid;

    @Deprecated
    public int versionCode;
    public String volumeUuid;
    public String zygotePreloadName;
    private static final Parcelling.BuiltIn.ForBoolean sForBoolean = (Parcelling.BuiltIn.ForBoolean) Parcelling.Cache.getOrCreate(Parcelling.BuiltIn.ForBoolean.class);
    private static final Parcelling.BuiltIn.ForStringSet sForStringSet = (Parcelling.BuiltIn.ForStringSet) Parcelling.Cache.getOrCreate(Parcelling.BuiltIn.ForStringSet.class);
    public static final Parcelable.Creator<ApplicationInfo> CREATOR = new AnonymousClass1();

    @Retention(RetentionPolicy.SOURCE)
    public @interface ApplicationInfoPrivateFlags {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ApplicationInfoPrivateFlagsExt {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Category {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface GwpAsanMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface HiddenApiEnforcementPolicy {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface MemtagMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface NativeHeapZeroInitialized {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface RawExternalStorage {
    }

    public static CharSequence getCategoryTitle(Context context, int category) {
        switch (category) {
            case 0:
                return context.getText(R.string.app_category_game);
            case 1:
                return context.getText(R.string.app_category_audio);
            case 2:
                return context.getText(R.string.app_category_video);
            case 3:
                return context.getText(R.string.app_category_image);
            case 4:
                return context.getText(R.string.app_category_social);
            case 5:
                return context.getText(R.string.app_category_news);
            case 6:
                return context.getText(R.string.app_category_maps);
            case 7:
                return context.getText(R.string.app_category_productivity);
            case 8:
                return context.getText(R.string.app_category_accessibility);
            default:
                return null;
        }
    }

    public static boolean isValidHiddenApiEnforcementPolicy(int policy) {
        return policy >= -1 && policy <= 2;
    }

    public void dump(Printer pw, String prefix) {
        dump(pw, prefix, 3);
    }

    public void dump(Printer pw, String prefix, int dumpFlags) {
        super.dumpFront(pw, prefix);
        if ((dumpFlags & 1) != 0) {
            if (this.className != null) {
                pw.println(prefix + "className=" + this.className);
            }
            for (int i = 0; i < ArrayUtils.size(this.mAppClassNamesByProcess); i++) {
                pw.println(prefix + "  process=" + this.mAppClassNamesByProcess.keyAt(i) + " className=" + this.mAppClassNamesByProcess.valueAt(i));
            }
        }
        if (this.permission != null) {
            pw.println(prefix + "permission=" + this.permission);
        }
        pw.println(prefix + "processName=" + this.processName);
        if ((dumpFlags & 1) != 0) {
            pw.println(prefix + "taskAffinity=" + this.taskAffinity);
        }
        pw.println(prefix + "uid=" + this.uid + " flags=0x" + Integer.toHexString(this.flags) + " privateFlags=0x" + Integer.toHexString(this.privateFlags) + " theme=0x" + Integer.toHexString(this.theme));
        if ((dumpFlags & 1) != 0) {
            pw.println(prefix + "requiresSmallestWidthDp=" + this.requiresSmallestWidthDp + " compatibleWidthLimitDp=" + this.compatibleWidthLimitDp + " largestWidthLimitDp=" + this.largestWidthLimitDp);
        }
        pw.println(prefix + "sourceDir=" + this.sourceDir);
        if (!Objects.equals(this.sourceDir, this.publicSourceDir)) {
            pw.println(prefix + "publicSourceDir=" + this.publicSourceDir);
        }
        if (!ArrayUtils.isEmpty(this.splitSourceDirs)) {
            pw.println(prefix + "splitSourceDirs=" + Arrays.toString(this.splitSourceDirs));
        }
        if (!ArrayUtils.isEmpty(this.splitPublicSourceDirs) && !Arrays.equals(this.splitSourceDirs, this.splitPublicSourceDirs)) {
            pw.println(prefix + "splitPublicSourceDirs=" + Arrays.toString(this.splitPublicSourceDirs));
        }
        if (this.resourceDirs != null) {
            pw.println(prefix + "resourceDirs=" + Arrays.toString(this.resourceDirs));
        }
        if (this.overlayPaths != null) {
            pw.println(prefix + "overlayPaths=" + Arrays.toString(this.overlayPaths));
        }
        if ((dumpFlags & 1) != 0 && this.seInfo != null) {
            pw.println(prefix + "seinfo=" + this.seInfo);
            pw.println(prefix + "seinfoUser=" + this.seInfoUser);
        }
        pw.println(prefix + "dataDir=" + this.dataDir);
        if ((dumpFlags & 1) != 0) {
            pw.println(prefix + "deviceProtectedDataDir=" + this.deviceProtectedDataDir);
            pw.println(prefix + "credentialProtectedDataDir=" + this.credentialProtectedDataDir);
            if (this.sharedLibraryFiles != null) {
                pw.println(prefix + "sharedLibraryFiles=" + Arrays.toString(this.sharedLibraryFiles));
            }
        }
        if (this.classLoaderName != null) {
            pw.println(prefix + "classLoaderName=" + this.classLoaderName);
        }
        if (!ArrayUtils.isEmpty(this.splitClassLoaderNames)) {
            pw.println(prefix + "splitClassLoaderNames=" + Arrays.toString(this.splitClassLoaderNames));
        }
        pw.println(prefix + "enabled=" + this.enabled + " minSdkVersion=" + this.minSdkVersion + " targetSdkVersion=" + this.targetSdkVersion + " versionCode=" + this.longVersionCode + " targetSandboxVersion=" + this.targetSandboxVersion);
        if ((dumpFlags & 1) != 0) {
            if (this.manageSpaceActivityName != null) {
                pw.println(prefix + "manageSpaceActivityName=" + this.manageSpaceActivityName);
            }
            if (this.descriptionRes != 0) {
                pw.println(prefix + "description=0x" + Integer.toHexString(this.descriptionRes));
            }
            if (this.uiOptions != 0) {
                pw.println(prefix + "uiOptions=0x" + Integer.toHexString(this.uiOptions));
            }
            pw.println(prefix + "supportsRtl=" + (hasRtlSupport() ? "true" : "false"));
            if (this.fullBackupContent > 0) {
                pw.println(prefix + "fullBackupContent=@xml/" + this.fullBackupContent);
            } else {
                pw.println(prefix + "fullBackupContent=" + (this.fullBackupContent < 0 ? "false" : "true"));
            }
            if (this.dataExtractionRulesRes != 0) {
                pw.println(prefix + "dataExtractionRules=@xml/" + this.dataExtractionRulesRes);
            }
            pw.println(prefix + "crossProfile=" + (this.crossProfile ? "true" : "false"));
            if (this.networkSecurityConfigRes != 0) {
                pw.println(prefix + "networkSecurityConfigRes=0x" + Integer.toHexString(this.networkSecurityConfigRes));
            }
            if (this.category != -1) {
                pw.println(prefix + "category=" + this.category);
            }
            pw.println(prefix + "HiddenApiEnforcementPolicy=" + getHiddenApiEnforcementPolicy());
            pw.println(prefix + "usesNonSdkApi=" + usesNonSdkApi());
            pw.println(prefix + "allowsPlaybackCapture=" + (isAudioPlaybackCaptureAllowed() ? "true" : "false"));
            if (this.gwpAsanMode != -1) {
                pw.println(prefix + "gwpAsanMode=" + this.gwpAsanMode);
            }
            if (this.memtagMode != -1) {
                pw.println(prefix + "memtagMode=" + this.memtagMode);
            }
            if (this.nativeHeapZeroInitialized != -1) {
                pw.println(prefix + "nativeHeapZeroInitialized=" + this.nativeHeapZeroInitialized);
            }
            if (this.requestRawExternalStorageAccess != null) {
                pw.println(prefix + "requestRawExternalStorageAccess=" + this.requestRawExternalStorageAccess);
            }
            if (this.localeConfigRes != 0) {
                pw.println(prefix + "localeConfigRes=0x" + Integer.toHexString(this.localeConfigRes));
            }
            pw.println(prefix + "enableOnBackInvokedCallback=" + isOnBackInvokedCallbackEnabled());
            pw.println(prefix + "allowCrossUidActivitySwitchFromBelow=" + this.allowCrossUidActivitySwitchFromBelow);
        }
        pw.println(prefix + "createTimestamp=" + this.createTimestamp);
        if (this.mKnownActivityEmbeddingCerts != null) {
            pw.println(prefix + "knownActivityEmbeddingCerts=" + this.mKnownActivityEmbeddingCerts);
        }
        super.dumpBack(pw, prefix);
    }

    @Override // android.content.pm.PackageItemInfo
    public void dumpDebug(ProtoOutputStream proto, long fieldId, int dumpFlags) {
        long token = proto.start(fieldId);
        super.dumpDebug(proto, 1146756268033L, dumpFlags);
        proto.write(1138166333442L, this.permission);
        proto.write(1138166333443L, this.processName);
        proto.write(1120986464260L, this.uid);
        proto.write(1120986464261L, this.flags);
        proto.write(1120986464262L, this.privateFlags);
        proto.write(1120986464263L, this.theme);
        proto.write(1138166333448L, this.sourceDir);
        if (!Objects.equals(this.sourceDir, this.publicSourceDir)) {
            proto.write(1138166333449L, this.publicSourceDir);
        }
        if (!ArrayUtils.isEmpty(this.splitSourceDirs)) {
            for (String dir : this.splitSourceDirs) {
                proto.write(2237677961226L, dir);
            }
        }
        if (!ArrayUtils.isEmpty(this.splitPublicSourceDirs) && !Arrays.equals(this.splitSourceDirs, this.splitPublicSourceDirs)) {
            for (String dir2 : this.splitPublicSourceDirs) {
                proto.write(2237677961227L, dir2);
            }
        }
        if (this.resourceDirs != null) {
            for (String dir3 : this.resourceDirs) {
                proto.write(2237677961228L, dir3);
            }
        }
        if (this.overlayPaths != null) {
            for (String dir4 : this.overlayPaths) {
                proto.write(ApplicationInfoProto.OVERLAY_PATHS, dir4);
            }
        }
        proto.write(1138166333453L, this.dataDir);
        proto.write(1138166333454L, this.classLoaderName);
        if (!ArrayUtils.isEmpty(this.splitClassLoaderNames)) {
            for (String name : this.splitClassLoaderNames) {
                proto.write(ApplicationInfoProto.SPLIT_CLASS_LOADER_NAMES, name);
            }
        }
        long versionToken = proto.start(1146756268048L);
        proto.write(1133871366145L, this.enabled);
        proto.write(1120986464258L, this.minSdkVersion);
        proto.write(1120986464259L, this.targetSdkVersion);
        proto.write(1120986464260L, this.longVersionCode);
        proto.write(1120986464261L, this.targetSandboxVersion);
        proto.end(versionToken);
        if ((dumpFlags & 1) != 0) {
            long detailToken = proto.start(1146756268049L);
            if (this.className != null) {
                proto.write(1138166333441L, this.className);
            }
            proto.write(1138166333442L, this.taskAffinity);
            proto.write(1120986464259L, this.requiresSmallestWidthDp);
            proto.write(1120986464260L, this.compatibleWidthLimitDp);
            proto.write(1120986464261L, this.largestWidthLimitDp);
            if (this.seInfo != null) {
                proto.write(1138166333446L, this.seInfo);
                proto.write(1138166333447L, this.seInfoUser);
            }
            proto.write(1138166333448L, this.deviceProtectedDataDir);
            proto.write(1138166333449L, this.credentialProtectedDataDir);
            if (this.sharedLibraryFiles != null) {
                for (String f : this.sharedLibraryFiles) {
                    proto.write(2237677961226L, f);
                }
            }
            if (this.manageSpaceActivityName != null) {
                proto.write(1138166333451L, this.manageSpaceActivityName);
            }
            if (this.descriptionRes != 0) {
                proto.write(1120986464268L, this.descriptionRes);
            }
            if (this.uiOptions != 0) {
                proto.write(1120986464269L, this.uiOptions);
            }
            proto.write(1133871366158L, hasRtlSupport());
            if (this.fullBackupContent > 0) {
                proto.write(1138166333455L, "@xml/" + this.fullBackupContent);
            } else {
                proto.write(1133871366160L, this.fullBackupContent == 0);
            }
            if (this.networkSecurityConfigRes != 0) {
                proto.write(1120986464273L, this.networkSecurityConfigRes);
            }
            if (this.category != -1) {
                proto.write(1120986464274L, this.category);
            }
            if (this.gwpAsanMode != -1) {
                proto.write(1120986464275L, this.gwpAsanMode);
            }
            if (this.memtagMode != -1) {
                proto.write(1120986464276L, this.memtagMode);
            }
            if (this.nativeHeapZeroInitialized != -1) {
                proto.write(1133871366165L, this.nativeHeapZeroInitialized);
            }
            proto.write(1133871366166L, this.allowCrossUidActivitySwitchFromBelow);
            proto.end(detailToken);
        }
        if (!ArrayUtils.isEmpty(this.mKnownActivityEmbeddingCerts)) {
            for (String knownCert : this.mKnownActivityEmbeddingCerts) {
                proto.write(ApplicationInfoProto.KNOWN_ACTIVITY_EMBEDDING_CERTS, knownCert);
            }
        }
        proto.end(token);
    }

    public boolean hasRtlSupport() {
        return (this.flags & 4194304) == 4194304;
    }

    public boolean hasCode() {
        return (this.flags & 4) != 0;
    }

    public static class DisplayNameComparator implements Comparator<ApplicationInfo> {
        private final PackageManager mPM;
        private final Collator sCollator = Collator.getInstance();

        public DisplayNameComparator(PackageManager pm) {
            this.mPM = pm;
        }

        @Override // java.util.Comparator
        public final int compare(ApplicationInfo aa, ApplicationInfo ab) {
            CharSequence sa = this.mPM.getApplicationLabel(aa);
            if (sa == null) {
                sa = aa.packageName;
            }
            CharSequence sb = this.mPM.getApplicationLabel(ab);
            if (sb == null) {
                sb = ab.packageName;
            }
            return this.sCollator.compare(sa.toString(), sb.toString());
        }
    }

    public ApplicationInfo() {
        this.fullBackupContent = 0;
        this.dataExtractionRulesRes = 0;
        this.uiOptions = 0;
        this.flags = 0;
        this.requiresSmallestWidthDp = 0;
        this.compatibleWidthLimitDp = 0;
        this.largestWidthLimitDp = 0;
        this.enabled = true;
        this.enabledSetting = 0;
        this.installLocation = -1;
        this.category = -1;
        this.gwpAsanMode = -1;
        this.memtagMode = -1;
        this.nativeHeapZeroInitialized = -1;
        this.allowCrossUidActivitySwitchFromBelow = true;
        this.mHiddenApiPolicy = -1;
        this.createTimestamp = SystemClock.uptimeMillis();
    }

    public ApplicationInfo(ApplicationInfo orig) {
        super(orig);
        this.fullBackupContent = 0;
        this.dataExtractionRulesRes = 0;
        this.uiOptions = 0;
        this.flags = 0;
        this.requiresSmallestWidthDp = 0;
        this.compatibleWidthLimitDp = 0;
        this.largestWidthLimitDp = 0;
        this.enabled = true;
        this.enabledSetting = 0;
        this.installLocation = -1;
        this.category = -1;
        this.gwpAsanMode = -1;
        this.memtagMode = -1;
        this.nativeHeapZeroInitialized = -1;
        this.allowCrossUidActivitySwitchFromBelow = true;
        this.mHiddenApiPolicy = -1;
        this.taskAffinity = orig.taskAffinity;
        this.permission = orig.permission;
        this.mKnownActivityEmbeddingCerts = orig.mKnownActivityEmbeddingCerts;
        this.processName = orig.processName;
        this.className = orig.className;
        this.theme = orig.theme;
        this.flags = orig.flags;
        this.privateFlags = orig.privateFlags;
        this.privateFlagsExt = orig.privateFlagsExt;
        this.requiresSmallestWidthDp = orig.requiresSmallestWidthDp;
        this.compatibleWidthLimitDp = orig.compatibleWidthLimitDp;
        this.largestWidthLimitDp = orig.largestWidthLimitDp;
        this.volumeUuid = orig.volumeUuid;
        this.storageUuid = orig.storageUuid;
        this.scanSourceDir = orig.scanSourceDir;
        this.scanPublicSourceDir = orig.scanPublicSourceDir;
        this.sourceDir = orig.sourceDir;
        this.publicSourceDir = orig.publicSourceDir;
        this.splitNames = orig.splitNames;
        this.splitSourceDirs = orig.splitSourceDirs;
        this.splitPublicSourceDirs = orig.splitPublicSourceDirs;
        this.splitDependencies = orig.splitDependencies;
        this.nativeLibraryDir = orig.nativeLibraryDir;
        this.secondaryNativeLibraryDir = orig.secondaryNativeLibraryDir;
        this.nativeLibraryRootDir = orig.nativeLibraryRootDir;
        this.nativeLibraryRootRequiresIsa = orig.nativeLibraryRootRequiresIsa;
        this.primaryCpuAbi = orig.primaryCpuAbi;
        this.secondaryCpuAbi = orig.secondaryCpuAbi;
        this.resourceDirs = orig.resourceDirs;
        this.overlayPaths = orig.overlayPaths;
        this.seInfo = orig.seInfo;
        this.seInfoUser = orig.seInfoUser;
        this.sharedLibraryFiles = orig.sharedLibraryFiles;
        this.sharedLibraryInfos = orig.sharedLibraryInfos;
        this.optionalSharedLibraryInfos = orig.optionalSharedLibraryInfos;
        this.dataDir = orig.dataDir;
        this.deviceProtectedDataDir = orig.deviceProtectedDataDir;
        this.credentialProtectedDataDir = orig.credentialProtectedDataDir;
        this.uid = orig.uid;
        this.minSdkVersion = orig.minSdkVersion;
        this.targetSdkVersion = orig.targetSdkVersion;
        setVersionCode(orig.longVersionCode);
        this.enabled = orig.enabled;
        this.enabledSetting = orig.enabledSetting;
        this.installLocation = orig.installLocation;
        this.manageSpaceActivityName = orig.manageSpaceActivityName;
        this.descriptionRes = orig.descriptionRes;
        this.uiOptions = orig.uiOptions;
        this.backupAgentName = orig.backupAgentName;
        this.fullBackupContent = orig.fullBackupContent;
        this.dataExtractionRulesRes = orig.dataExtractionRulesRes;
        this.crossProfile = orig.crossProfile;
        this.networkSecurityConfigRes = orig.networkSecurityConfigRes;
        this.category = orig.category;
        this.targetSandboxVersion = orig.targetSandboxVersion;
        this.classLoaderName = orig.classLoaderName;
        this.splitClassLoaderNames = orig.splitClassLoaderNames;
        this.appComponentFactory = orig.appComponentFactory;
        this.iconRes = orig.iconRes;
        this.roundIconRes = orig.roundIconRes;
        this.compileSdkVersion = orig.compileSdkVersion;
        this.compileSdkVersionCodename = orig.compileSdkVersionCodename;
        this.mHiddenApiPolicy = orig.mHiddenApiPolicy;
        this.hiddenUntilInstalled = orig.hiddenUntilInstalled;
        this.zygotePreloadName = orig.zygotePreloadName;
        this.gwpAsanMode = orig.gwpAsanMode;
        this.memtagMode = orig.memtagMode;
        this.nativeHeapZeroInitialized = orig.nativeHeapZeroInitialized;
        this.requestRawExternalStorageAccess = orig.requestRawExternalStorageAccess;
        this.localeConfigRes = orig.localeConfigRes;
        this.allowCrossUidActivitySwitchFromBelow = orig.allowCrossUidActivitySwitchFromBelow;
        this.createTimestamp = SystemClock.uptimeMillis();
    }

    public String toString() {
        return "ApplicationInfo{" + Integer.toHexString(System.identityHashCode(this)) + " " + this.packageName + "}";
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.content.pm.PackageItemInfo, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        if (parcel.maybeWriteSquashed(this)) {
            return;
        }
        super.writeToParcel(parcel, i);
        parcel.writeString8(this.taskAffinity);
        parcel.writeString8(this.permission);
        parcel.writeString8(this.processName);
        parcel.writeString8(this.className);
        parcel.writeInt(this.theme);
        parcel.writeInt(this.flags);
        parcel.writeInt(this.privateFlags);
        parcel.writeInt(this.privateFlagsExt);
        parcel.writeInt(this.requiresSmallestWidthDp);
        parcel.writeInt(this.compatibleWidthLimitDp);
        parcel.writeInt(this.largestWidthLimitDp);
        if (this.storageUuid != null) {
            parcel.writeInt(1);
            parcel.writeLong(this.storageUuid.getMostSignificantBits());
            parcel.writeLong(this.storageUuid.getLeastSignificantBits());
        } else {
            parcel.writeInt(0);
        }
        parcel.writeString8(this.scanSourceDir);
        parcel.writeString8(this.scanPublicSourceDir);
        parcel.writeString8(this.sourceDir);
        parcel.writeString8(this.publicSourceDir);
        parcel.writeString8Array(this.splitNames);
        parcel.writeString8Array(this.splitSourceDirs);
        parcel.writeString8Array(this.splitPublicSourceDirs);
        parcel.writeSparseArray(this.splitDependencies);
        parcel.writeString8(this.nativeLibraryDir);
        parcel.writeString8(this.secondaryNativeLibraryDir);
        parcel.writeString8(this.nativeLibraryRootDir);
        parcel.writeInt(this.nativeLibraryRootRequiresIsa ? 1 : 0);
        parcel.writeString8(this.primaryCpuAbi);
        parcel.writeString8(this.secondaryCpuAbi);
        parcel.writeString8Array(this.resourceDirs);
        parcel.writeString8Array(this.overlayPaths);
        parcel.writeString8(this.seInfo);
        parcel.writeString8(this.seInfoUser);
        parcel.writeString8Array(this.sharedLibraryFiles);
        parcel.writeTypedList(this.sharedLibraryInfos);
        parcel.writeTypedList(this.optionalSharedLibraryInfos);
        parcel.writeString8(this.dataDir);
        parcel.writeString8(this.deviceProtectedDataDir);
        parcel.writeString8(this.credentialProtectedDataDir);
        parcel.writeInt(this.uid);
        parcel.writeInt(this.minSdkVersion);
        parcel.writeInt(this.targetSdkVersion);
        parcel.writeLong(this.longVersionCode);
        parcel.writeInt(this.enabled ? 1 : 0);
        parcel.writeInt(this.enabledSetting);
        parcel.writeInt(this.installLocation);
        parcel.writeString8(this.manageSpaceActivityName);
        parcel.writeString8(this.backupAgentName);
        parcel.writeInt(this.descriptionRes);
        parcel.writeInt(this.uiOptions);
        parcel.writeInt(this.fullBackupContent);
        parcel.writeInt(this.dataExtractionRulesRes);
        parcel.writeBoolean(this.crossProfile);
        parcel.writeInt(this.networkSecurityConfigRes);
        parcel.writeInt(this.category);
        parcel.writeInt(this.targetSandboxVersion);
        parcel.writeString8(this.classLoaderName);
        parcel.writeString8Array(this.splitClassLoaderNames);
        parcel.writeInt(this.compileSdkVersion);
        parcel.writeString8(this.compileSdkVersionCodename);
        parcel.writeString8(this.appComponentFactory);
        parcel.writeInt(this.iconRes);
        parcel.writeInt(this.roundIconRes);
        parcel.writeInt(this.mHiddenApiPolicy);
        parcel.writeInt(this.hiddenUntilInstalled ? 1 : 0);
        parcel.writeString8(this.zygotePreloadName);
        parcel.writeInt(this.gwpAsanMode);
        parcel.writeInt(this.memtagMode);
        parcel.writeInt(this.nativeHeapZeroInitialized);
        sForBoolean.parcel(this.requestRawExternalStorageAccess, parcel, i);
        parcel.writeLong(this.createTimestamp);
        if (this.mAppClassNamesByProcess == null) {
            parcel.writeInt(0);
        } else {
            int size = this.mAppClassNamesByProcess.size();
            parcel.writeInt(size);
            for (int i2 = 0; i2 < size; i2++) {
                parcel.writeString(this.mAppClassNamesByProcess.keyAt(i2));
                parcel.writeString(this.mAppClassNamesByProcess.valueAt(i2));
            }
        }
        parcel.writeInt(this.localeConfigRes);
        parcel.writeInt(this.allowCrossUidActivitySwitchFromBelow ? 1 : 0);
        sForStringSet.parcel(this.mKnownActivityEmbeddingCerts, parcel, this.flags);
    }

    /* renamed from: android.content.pm.ApplicationInfo$1, reason: invalid class name */
    class AnonymousClass1 implements Parcelable.Creator<ApplicationInfo> {
        /* renamed from: $r8$lambda$1E1P6HJEl7Ns7qcxzJ0zM-xcHGA, reason: not valid java name */
        public static /* synthetic */ ApplicationInfo m925$r8$lambda$1E1P6HJEl7Ns7qcxzJ0zMxcHGA(Parcel parcel) {
            return new ApplicationInfo(parcel);
        }

        AnonymousClass1() {
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ApplicationInfo createFromParcel(Parcel source) {
            return (ApplicationInfo) source.readSquashed(new Parcel.SquashReadHelper() { // from class: android.content.pm.ApplicationInfo$1$$ExternalSyntheticLambda0
                @Override // android.os.Parcel.SquashReadHelper
                public final Object readRawParceled(Parcel parcel) {
                    return ApplicationInfo.AnonymousClass1.m925$r8$lambda$1E1P6HJEl7Ns7qcxzJ0zMxcHGA(parcel);
                }
            });
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ApplicationInfo[] newArray(int size) {
            return new ApplicationInfo[size];
        }
    }

    private ApplicationInfo(Parcel source) {
        super(source);
        boolean z;
        boolean z2;
        boolean z3;
        this.fullBackupContent = 0;
        this.dataExtractionRulesRes = 0;
        this.uiOptions = 0;
        this.flags = 0;
        this.requiresSmallestWidthDp = 0;
        this.compatibleWidthLimitDp = 0;
        this.largestWidthLimitDp = 0;
        this.enabled = true;
        this.enabledSetting = 0;
        this.installLocation = -1;
        this.category = -1;
        this.gwpAsanMode = -1;
        this.memtagMode = -1;
        this.nativeHeapZeroInitialized = -1;
        this.allowCrossUidActivitySwitchFromBelow = true;
        this.mHiddenApiPolicy = -1;
        this.taskAffinity = source.readString8();
        this.permission = source.readString8();
        this.processName = source.readString8();
        this.className = source.readString8();
        this.theme = source.readInt();
        this.flags = source.readInt();
        this.privateFlags = source.readInt();
        this.privateFlagsExt = source.readInt();
        this.requiresSmallestWidthDp = source.readInt();
        this.compatibleWidthLimitDp = source.readInt();
        this.largestWidthLimitDp = source.readInt();
        if (source.readInt() != 0) {
            this.storageUuid = new UUID(source.readLong(), source.readLong());
            this.volumeUuid = StorageManager.convert(this.storageUuid);
        }
        this.scanSourceDir = source.readString8();
        this.scanPublicSourceDir = source.readString8();
        this.sourceDir = source.readString8();
        this.publicSourceDir = source.readString8();
        this.splitNames = source.createString8Array();
        this.splitSourceDirs = source.createString8Array();
        this.splitPublicSourceDirs = source.createString8Array();
        this.splitDependencies = source.readSparseArray(null, int[].class);
        this.nativeLibraryDir = source.readString8();
        this.secondaryNativeLibraryDir = source.readString8();
        this.nativeLibraryRootDir = source.readString8();
        if (source.readInt() == 0) {
            z = false;
        } else {
            z = true;
        }
        this.nativeLibraryRootRequiresIsa = z;
        this.primaryCpuAbi = source.readString8();
        this.secondaryCpuAbi = source.readString8();
        this.resourceDirs = source.createString8Array();
        this.overlayPaths = source.createString8Array();
        this.seInfo = source.readString8();
        this.seInfoUser = source.readString8();
        this.sharedLibraryFiles = source.createString8Array();
        this.sharedLibraryInfos = source.createTypedArrayList(SharedLibraryInfo.CREATOR);
        this.optionalSharedLibraryInfos = source.createTypedArrayList(SharedLibraryInfo.CREATOR);
        this.dataDir = source.readString8();
        this.deviceProtectedDataDir = source.readString8();
        this.credentialProtectedDataDir = source.readString8();
        this.uid = source.readInt();
        this.minSdkVersion = source.readInt();
        this.targetSdkVersion = source.readInt();
        setVersionCode(source.readLong());
        if (source.readInt() == 0) {
            z2 = false;
        } else {
            z2 = true;
        }
        this.enabled = z2;
        this.enabledSetting = source.readInt();
        this.installLocation = source.readInt();
        this.manageSpaceActivityName = source.readString8();
        this.backupAgentName = source.readString8();
        this.descriptionRes = source.readInt();
        this.uiOptions = source.readInt();
        this.fullBackupContent = source.readInt();
        this.dataExtractionRulesRes = source.readInt();
        this.crossProfile = source.readBoolean();
        this.networkSecurityConfigRes = source.readInt();
        this.category = source.readInt();
        this.targetSandboxVersion = source.readInt();
        this.classLoaderName = source.readString8();
        this.splitClassLoaderNames = source.createString8Array();
        this.compileSdkVersion = source.readInt();
        this.compileSdkVersionCodename = source.readString8();
        this.appComponentFactory = source.readString8();
        this.iconRes = source.readInt();
        this.roundIconRes = source.readInt();
        this.mHiddenApiPolicy = source.readInt();
        if (source.readInt() == 0) {
            z3 = false;
        } else {
            z3 = true;
        }
        this.hiddenUntilInstalled = z3;
        this.zygotePreloadName = source.readString8();
        this.gwpAsanMode = source.readInt();
        this.memtagMode = source.readInt();
        this.nativeHeapZeroInitialized = source.readInt();
        this.requestRawExternalStorageAccess = sForBoolean.unparcel(source);
        this.createTimestamp = source.readLong();
        int allClassesSize = source.readInt();
        if (allClassesSize > 0) {
            this.mAppClassNamesByProcess = new ArrayMap<>(allClassesSize);
            for (int i = 0; i < allClassesSize; i++) {
                this.mAppClassNamesByProcess.put(source.readString(), source.readString());
            }
        }
        int i2 = source.readInt();
        this.localeConfigRes = i2;
        this.allowCrossUidActivitySwitchFromBelow = source.readInt() != 0;
        this.mKnownActivityEmbeddingCerts = sForStringSet.unparcel(source);
        if (this.mKnownActivityEmbeddingCerts.isEmpty()) {
            this.mKnownActivityEmbeddingCerts = null;
        }
    }

    public CharSequence loadDescription(PackageManager pm) {
        CharSequence label;
        if (this.descriptionRes != 0 && (label = pm.getText(this.packageName, this.descriptionRes, this)) != null) {
            return label;
        }
        return null;
    }

    public void disableCompatibilityMode() {
        this.flags |= 540160;
    }

    public boolean usesCompatibilityMode() {
        return this.targetSdkVersion < 4 || (this.flags & 540160) == 0;
    }

    public void initForUser(int userId) {
        this.uid = UserHandle.getUid(userId, UserHandle.getAppId(this.uid));
        if ("android".equals(this.packageName)) {
            this.dataDir = Environment.getDataSystemDirectory().getAbsolutePath();
            return;
        }
        this.deviceProtectedDataDir = Environment.getDataUserDePackageDirectory(this.volumeUuid, userId, this.packageName).getAbsolutePath();
        this.credentialProtectedDataDir = Environment.getDataUserCePackageDirectory(this.volumeUuid, userId, this.packageName).getAbsolutePath();
        if ((this.privateFlags & 32) != 0) {
            this.dataDir = this.deviceProtectedDataDir;
        } else {
            this.dataDir = this.credentialProtectedDataDir;
        }
    }

    private boolean isPackageWhitelistedForHiddenApis() {
        return (this.privateFlagsExt & 16) != 0;
    }

    public boolean usesNonSdkApi() {
        return (this.privateFlags & 4194304) != 0;
    }

    @SystemApi
    public boolean hasFragileUserData() {
        return (this.privateFlags & 16777216) != 0;
    }

    public boolean isAudioPlaybackCaptureAllowed() {
        return (this.privateFlags & 134217728) != 0;
    }

    public boolean hasRequestedLegacyExternalStorage() {
        return (this.privateFlags & 536870912) != 0;
    }

    public int getRequestRawExternalStorageAccess() {
        if (this.requestRawExternalStorageAccess == null) {
            return 0;
        }
        return this.requestRawExternalStorageAccess.booleanValue() ? 1 : 2;
    }

    public boolean allowsNativeHeapPointerTagging() {
        return (this.privateFlags & Integer.MIN_VALUE) != 0;
    }

    private boolean isAllowedToUseHiddenApis() {
        if (isSignedWithPlatformKey()) {
            return true;
        }
        if (isSystemApp() || isUpdatedSystemApp()) {
            return usesNonSdkApi() || isPackageWhitelistedForHiddenApis();
        }
        return false;
    }

    public int getHiddenApiEnforcementPolicy() {
        if (isAllowedToUseHiddenApis()) {
            return 0;
        }
        if (this.mHiddenApiPolicy != -1) {
            return this.mHiddenApiPolicy;
        }
        return 2;
    }

    public void setHiddenApiEnforcementPolicy(int policy) {
        if (!isValidHiddenApiEnforcementPolicy(policy)) {
            throw new IllegalArgumentException("Invalid API enforcement policy: " + policy);
        }
        this.mHiddenApiPolicy = policy;
    }

    public void maybeUpdateHiddenApiEnforcementPolicy(int policy) {
        if (isPackageWhitelistedForHiddenApis()) {
            return;
        }
        setHiddenApiEnforcementPolicy(policy);
    }

    public void setVersionCode(long newVersionCode) {
        this.longVersionCode = newVersionCode;
        this.versionCode = (int) newVersionCode;
    }

    @Override // android.content.pm.PackageItemInfo
    public Drawable loadDefaultIcon(PackageManager pm) {
        if ((this.flags & 262144) != 0 && isPackageUnavailable(pm)) {
            return Resources.getSystem().getDrawable(R.drawable.sym_app_on_sd_unavailable_icon);
        }
        return pm.getDefaultActivityIcon();
    }

    private boolean isPackageUnavailable(PackageManager pm) {
        try {
            return pm.getPackageInfo(this.packageName, 0) == null;
        } catch (PackageManager.NameNotFoundException e) {
            return true;
        }
    }

    public boolean isDefaultToDeviceProtectedStorage() {
        return (this.privateFlags & 32) != 0;
    }

    public boolean isDirectBootAware() {
        return (this.privateFlags & 64) != 0;
    }

    @SystemApi
    public boolean isEncryptionAware() {
        return isDirectBootAware() || isPartiallyDirectBootAware();
    }

    public boolean isExternal() {
        return (this.flags & 262144) != 0;
    }

    public boolean isExternalAsec() {
        return TextUtils.isEmpty(this.volumeUuid) && isExternal();
    }

    @SystemApi
    public boolean isInstantApp() {
        return (this.privateFlags & 128) != 0;
    }

    public boolean isInternal() {
        return (this.flags & 262144) == 0;
    }

    @SystemApi
    public boolean isOem() {
        return (this.privateFlags & 131072) != 0;
    }

    public boolean isOdm() {
        return (this.privateFlags & 1073741824) != 0;
    }

    public boolean isPartiallyDirectBootAware() {
        return (this.privateFlags & 256) != 0;
    }

    public boolean isSignedWithPlatformKey() {
        return (this.privateFlags & 1048576) != 0;
    }

    @SystemApi
    public boolean isPrivilegedApp() {
        return (this.privateFlags & 8) != 0;
    }

    public boolean isRequiredForSystemUser() {
        return (this.privateFlags & 512) != 0;
    }

    public boolean isStaticSharedLibrary() {
        return (this.privateFlags & 16384) != 0;
    }

    public boolean isSystemApp() {
        return (this.flags & 1) != 0;
    }

    public boolean isUpdatedSystemApp() {
        return (this.flags & 128) != 0;
    }

    @SystemApi
    public boolean isVendor() {
        return (this.privateFlags & 262144) != 0;
    }

    @SystemApi
    public boolean isProduct() {
        return (this.privateFlags & 524288) != 0;
    }

    public boolean isSystemExt() {
        return (this.privateFlags & 2097152) != 0;
    }

    public boolean isEmbeddedDexUsed() {
        return (this.privateFlags & 33554432) != 0;
    }

    public boolean isVirtualPreload() {
        return (this.privateFlags & 65536) != 0;
    }

    public boolean isProfileableByShell() {
        return (this.privateFlags & 8388608) != 0;
    }

    public boolean isProfileable() {
        return (this.privateFlagsExt & 1) != 0;
    }

    public boolean areAttributionsUserVisible() {
        return (this.privateFlagsExt & 4) != 0;
    }

    public boolean requestsIsolatedSplitLoading() {
        return (this.privateFlags & 32768) != 0;
    }

    public boolean isResourceOverlay() {
        return (this.privateFlags & 268435456) != 0;
    }

    public boolean isStopped() {
        return (this.flags & 2097152) != 0;
    }

    public boolean isNotLaunched() {
        return (this.privateFlagsExt & 64) != 0;
    }

    public boolean isChangeEnabled(long changeId) {
        return CompatChanges.isChangeEnabled(changeId, this.packageName, UserHandle.getUserHandleForUid(this.uid));
    }

    public boolean hasRequestForegroundServiceExemption() {
        return (this.privateFlagsExt & 2) != 0;
    }

    public boolean isOnBackInvokedCallbackEnabled() {
        return (this.privateFlagsExt & 8) != 0;
    }

    @Override // android.content.pm.PackageItemInfo
    public ApplicationInfo getApplicationInfo() {
        return this;
    }

    public String[] getAllApkPaths() {
        String[][] inputLists = {this.splitSourceDirs, this.sharedLibraryFiles, this.resourceDirs, this.overlayPaths};
        List<String> output = new ArrayList<>(10);
        if (this.sourceDir != null) {
            output.add(this.sourceDir);
        }
        for (String[] inputList : inputLists) {
            if (inputList != null) {
                for (String input : inputList) {
                    output.add(input);
                }
            }
        }
        return (String[]) output.toArray(new String[output.size()]);
    }

    public void setCodePath(String codePath) {
        this.scanSourceDir = codePath;
    }

    public void setBaseCodePath(String baseCodePath) {
        this.sourceDir = baseCodePath;
    }

    public void setSplitCodePaths(String[] splitCodePaths) {
        this.splitSourceDirs = splitCodePaths;
    }

    public void setResourcePath(String resourcePath) {
        this.scanPublicSourceDir = resourcePath;
    }

    public void setBaseResourcePath(String baseResourcePath) {
        this.publicSourceDir = baseResourcePath;
    }

    public void setSplitResourcePaths(String[] splitResourcePaths) {
        this.splitPublicSourceDirs = splitResourcePaths;
    }

    public void setGwpAsanMode(int value) {
        this.gwpAsanMode = value;
    }

    public void setMemtagMode(int value) {
        this.memtagMode = value;
    }

    public void setNativeHeapZeroInitialized(int value) {
        this.nativeHeapZeroInitialized = value;
    }

    public void setRequestRawExternalStorageAccess(Boolean value) {
        this.requestRawExternalStorageAccess = value;
    }

    public void setAppClassNamesByProcess(ArrayMap<String, String> value) {
        if (ArrayUtils.size(value) == 0) {
            this.mAppClassNamesByProcess = null;
        } else {
            this.mAppClassNamesByProcess = value;
        }
    }

    public String getCodePath() {
        return this.scanSourceDir;
    }

    public String getBaseCodePath() {
        return this.sourceDir;
    }

    public String[] getSplitCodePaths() {
        return this.splitSourceDirs;
    }

    public String getResourcePath() {
        return this.scanPublicSourceDir;
    }

    public String getBaseResourcePath() {
        return this.publicSourceDir;
    }

    public String[] getSplitResourcePaths() {
        return this.splitPublicSourceDirs;
    }

    public int getGwpAsanMode() {
        return this.gwpAsanMode;
    }

    public int getMemtagMode() {
        return this.memtagMode;
    }

    public int getNativeHeapZeroInitialized() {
        return this.nativeHeapZeroInitialized;
    }

    public String getCustomApplicationClassNameForProcess(String processName) {
        String byProcess;
        if (this.mAppClassNamesByProcess != null && (byProcess = this.mAppClassNamesByProcess.get(processName)) != null) {
            return byProcess;
        }
        return this.className;
    }

    public void setLocaleConfigRes(int value) {
        this.localeConfigRes = value;
    }

    public int getLocaleConfigRes() {
        return this.localeConfigRes;
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public List<SharedLibraryInfo> getSharedLibraryInfos() {
        if (this.sharedLibraryInfos == null) {
            return Collections.EMPTY_LIST;
        }
        return this.sharedLibraryInfos;
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public List<SharedLibraryInfo> getOptionalSharedLibraryInfos() {
        if (this.optionalSharedLibraryInfos == null) {
            return Collections.EMPTY_LIST;
        }
        return this.optionalSharedLibraryInfos;
    }

    public Set<String> getKnownActivityEmbeddingCerts() {
        return this.mKnownActivityEmbeddingCerts == null ? Collections.emptySet() : this.mKnownActivityEmbeddingCerts;
    }

    public void setKnownActivityEmbeddingCerts(Set<String> knownActivityEmbeddingCerts) {
        this.mKnownActivityEmbeddingCerts = new ArraySet();
        for (String knownCert : knownActivityEmbeddingCerts) {
            this.mKnownActivityEmbeddingCerts.add(knownCert.toUpperCase(Locale.US));
        }
    }

    public void setEnableOnBackInvokedCallback(boolean isEnable) {
        if (isEnable) {
            this.privateFlagsExt |= 8;
        } else {
            this.privateFlagsExt &= -9;
        }
    }
}
