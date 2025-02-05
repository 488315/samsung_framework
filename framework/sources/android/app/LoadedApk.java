package android.app;

import android.app.IServiceConnection;
import android.app.LoadedApk;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.IIntentReceiver;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.SharedLibraryInfo;
import android.content.pm.dex.ArtManager;
import android.content.pm.split.SplitDependencyLoader;
import android.content.res.AssetManager;
import android.content.res.CompatibilityInfo;
import android.content.res.Resources;
import android.content.res.loader.ResourcesLoader;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Process;
import android.os.RemoteException;
import android.os.StrictMode;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.Trace;
import android.os.UserHandle;
import android.security.net.config.NetworkSecurityConfigProvider;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Pair;
import android.util.PerfLog;
import android.util.Slog;
import android.util.SparseArray;
import android.view.DisplayAdjustments;
import com.android.internal.R;
import com.android.internal.util.ArrayUtils;
import com.samsung.android.rune.CoreRune;
import dalvik.system.VMRuntime;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executor;

/* loaded from: classes.dex */
public final class LoadedApk {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final boolean DEBUG = false;
    static final String TAG = "LoadedApk";
    private static final String TAG_SPEG = "SPEG";
    private static final ArrayMap<String, Application> sApplications = new ArrayMap<>(4);
    private final ActivityThread mActivityThread;
    private AppComponentFactory mAppComponentFactory;
    private String mAppDir;
    private Application mApplication;
    private ApplicationInfo mApplicationInfo;
    private final ClassLoader mBaseClassLoader;
    private ClassLoader mClassLoader;
    private File mCredentialProtectedDataDirFile;
    private String mDataDir;
    private File mDataDirFile;
    private ClassLoader mDefaultClassLoader;
    private File mDeviceProtectedDataDirFile;
    private final DisplayAdjustments mDisplayAdjustments;
    private final boolean mIncludeCode;
    private String[] mLegacyOverlayDirs;
    private String mLibDir;
    private final Object mLock;
    private String[] mOverlayPaths;
    final String mPackageName;
    private final ArrayMap<Context, ArrayMap<BroadcastReceiver, ReceiverDispatcher>> mReceivers;
    private final boolean mRegisterPackage;
    private String mResDir;
    Resources mResources;
    private final boolean mSecurityViolation;
    private final ArrayMap<Context, ArrayMap<ServiceConnection, ServiceDispatcher>> mServices;
    private String[] mSplitAppDirs;
    private String[] mSplitClassLoaderNames;
    private SplitDependencyLoaderImpl mSplitLoader;
    private String[] mSplitNames;
    private String[] mSplitResDirs;
    private final ArrayMap<Context, ArrayMap<ServiceConnection, ServiceDispatcher>> mUnboundServices;
    private final ArrayMap<Context, ArrayMap<BroadcastReceiver, ReceiverDispatcher>> mUnregisteredReceivers;

    Application getApplication() {
        return this.mApplication;
    }

    public LoadedApk(ActivityThread activityThread, ApplicationInfo aInfo, CompatibilityInfo compatInfo, ClassLoader baseLoader, boolean securityViolation, boolean includeCode, boolean registerPackage) {
        this.mDisplayAdjustments = new DisplayAdjustments();
        this.mReceivers = new ArrayMap<>();
        this.mUnregisteredReceivers = new ArrayMap<>();
        this.mServices = new ArrayMap<>();
        this.mUnboundServices = new ArrayMap<>();
        this.mLock = new Object();
        this.mActivityThread = activityThread;
        setApplicationInfo(aInfo);
        this.mPackageName = aInfo.packageName;
        this.mBaseClassLoader = baseLoader;
        this.mSecurityViolation = securityViolation;
        this.mIncludeCode = includeCode;
        this.mRegisterPackage = registerPackage;
        this.mDisplayAdjustments.setCompatibilityInfo(compatInfo);
        this.mAppComponentFactory = createAppFactory(this.mApplicationInfo, this.mBaseClassLoader);
    }

    private static ApplicationInfo adjustNativeLibraryPaths(ApplicationInfo info) {
        if (info.primaryCpuAbi != null && info.secondaryCpuAbi != null) {
            String runtimeIsa = VMRuntime.getRuntime().vmInstructionSet();
            String secondaryIsa = VMRuntime.getInstructionSet(info.secondaryCpuAbi);
            String secondaryDexCodeIsa = SystemProperties.get("ro.dalvik.vm.isa." + secondaryIsa);
            if (runtimeIsa.equals(secondaryDexCodeIsa.isEmpty() ? secondaryIsa : secondaryDexCodeIsa)) {
                ApplicationInfo modified = new ApplicationInfo(info);
                modified.nativeLibraryDir = modified.secondaryNativeLibraryDir;
                modified.primaryCpuAbi = modified.secondaryCpuAbi;
                return modified;
            }
        }
        return info;
    }

    LoadedApk(ActivityThread activityThread) {
        this.mDisplayAdjustments = new DisplayAdjustments();
        this.mReceivers = new ArrayMap<>();
        this.mUnregisteredReceivers = new ArrayMap<>();
        this.mServices = new ArrayMap<>();
        this.mUnboundServices = new ArrayMap<>();
        this.mLock = new Object();
        this.mActivityThread = activityThread;
        this.mApplicationInfo = new ApplicationInfo();
        this.mApplicationInfo.packageName = "android";
        this.mPackageName = "android";
        this.mAppDir = null;
        this.mResDir = null;
        this.mSplitAppDirs = null;
        this.mSplitResDirs = null;
        this.mSplitClassLoaderNames = null;
        this.mLegacyOverlayDirs = null;
        this.mOverlayPaths = null;
        this.mDataDir = null;
        this.mDataDirFile = null;
        this.mDeviceProtectedDataDirFile = null;
        this.mCredentialProtectedDataDirFile = null;
        this.mLibDir = null;
        this.mBaseClassLoader = null;
        this.mSecurityViolation = false;
        this.mIncludeCode = true;
        this.mRegisterPackage = false;
        this.mResources = Resources.getSystem();
        this.mDefaultClassLoader = ClassLoader.getSystemClassLoader();
        this.mAppComponentFactory = createAppFactory(this.mApplicationInfo, this.mDefaultClassLoader);
        this.mClassLoader = this.mAppComponentFactory.instantiateClassLoader(this.mDefaultClassLoader, new ApplicationInfo(this.mApplicationInfo));
    }

    void installSystemApplicationInfo(ApplicationInfo info, ClassLoader classLoader) {
        this.mApplicationInfo = info;
        this.mDefaultClassLoader = classLoader;
        this.mAppComponentFactory = createAppFactory(info, this.mDefaultClassLoader);
        this.mClassLoader = this.mAppComponentFactory.instantiateClassLoader(this.mDefaultClassLoader, new ApplicationInfo(this.mApplicationInfo));
    }

    private AppComponentFactory createAppFactory(ApplicationInfo appInfo, ClassLoader cl) {
        if (this.mIncludeCode && appInfo.appComponentFactory != null && cl != null) {
            try {
                return (AppComponentFactory) cl.loadClass(appInfo.appComponentFactory).newInstance();
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                Slog.e(TAG, "Unable to instantiate appComponentFactory", e);
            }
        }
        return AppComponentFactory.DEFAULT;
    }

    public AppComponentFactory getAppFactory() {
        return this.mAppComponentFactory;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public ApplicationInfo getApplicationInfo() {
        return this.mApplicationInfo;
    }

    public int getTargetSdkVersion() {
        return this.mApplicationInfo.targetSdkVersion;
    }

    public boolean isSecurityViolation() {
        return this.mSecurityViolation;
    }

    public CompatibilityInfo getCompatibilityInfo() {
        return this.mDisplayAdjustments.getCompatibilityInfo();
    }

    public void setCompatibilityInfo(CompatibilityInfo compatInfo) {
        this.mDisplayAdjustments.setCompatibilityInfo(compatInfo);
    }

    private static String[] getLibrariesFor(String packageName) {
        try {
            ApplicationInfo ai = ActivityThread.getPackageManager().getApplicationInfo(packageName, 1024L, UserHandle.myUserId());
            if (ai == null) {
                return null;
            }
            return ai.sharedLibraryFiles;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void updateApplicationInfo(ApplicationInfo aInfo, List<String> oldPaths) {
        List<ResourcesLoader> loaders;
        if (!setApplicationInfo(aInfo)) {
            return;
        }
        ArrayList<String> arrayList = new ArrayList();
        makePaths(this.mActivityThread, aInfo, arrayList);
        List<String> addedPaths = new ArrayList<>(arrayList.size());
        if (oldPaths != null) {
            for (String path : arrayList) {
                String apkName = path.substring(path.lastIndexOf(File.separator));
                boolean match = false;
                Iterator<String> it = oldPaths.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    String oldPath = it.next();
                    Object oldApkName = oldPath.substring(oldPath.lastIndexOf(File.separator));
                    if (apkName.equals(oldApkName)) {
                        match = true;
                        break;
                    }
                }
                if (!match) {
                    addedPaths.add(path);
                }
            }
        } else {
            addedPaths.addAll(arrayList);
        }
        synchronized (this.mLock) {
            createOrUpdateClassLoaderLocked(addedPaths);
            if (this.mResources != null) {
                try {
                    String[] splitPaths = getSplitPaths(null);
                    ResourcesManager resourcesManager = ResourcesManager.getInstance();
                    String str = this.mResDir;
                    String[] strArr = this.mLegacyOverlayDirs;
                    String[] strArr2 = this.mOverlayPaths;
                    String[] strArr3 = this.mApplicationInfo.sharedLibraryFiles;
                    CompatibilityInfo compatibilityInfo = getCompatibilityInfo();
                    ClassLoader classLoader = getClassLoader();
                    if (this.mApplication == null) {
                        loaders = null;
                    } else {
                        loaders = this.mApplication.getResources().getLoaders();
                    }
                    this.mResources = resourcesManager.getResources(null, str, splitPaths, strArr, strArr2, strArr3, null, null, compatibilityInfo, classLoader, loaders);
                } catch (PackageManager.NameNotFoundException e) {
                    throw new AssertionError("null split not found");
                }
            }
        }
        this.mAppComponentFactory = createAppFactory(aInfo, this.mDefaultClassLoader);
    }

    private boolean setApplicationInfo(ApplicationInfo aInfo) {
        if (this.mApplicationInfo != null && this.mApplicationInfo.createTimestamp > aInfo.createTimestamp) {
            Slog.w(TAG, "New application info for package " + aInfo.packageName + " is out of date with TS " + aInfo.createTimestamp + " < the current TS " + this.mApplicationInfo.createTimestamp);
            return false;
        }
        int myUid = Process.myUid();
        ApplicationInfo aInfo2 = adjustNativeLibraryPaths(aInfo);
        this.mApplicationInfo = aInfo2;
        this.mAppDir = aInfo2.sourceDir;
        this.mResDir = aInfo2.uid == myUid ? aInfo2.sourceDir : aInfo2.publicSourceDir;
        this.mLegacyOverlayDirs = aInfo2.resourceDirs;
        this.mOverlayPaths = aInfo2.overlayPaths;
        this.mDataDir = aInfo2.dataDir;
        this.mLibDir = aInfo2.nativeLibraryDir;
        this.mDataDirFile = FileUtils.newFileOrNull(aInfo2.dataDir);
        this.mDeviceProtectedDataDirFile = FileUtils.newFileOrNull(aInfo2.deviceProtectedDataDir);
        this.mCredentialProtectedDataDirFile = FileUtils.newFileOrNull(aInfo2.credentialProtectedDataDir);
        this.mSplitNames = aInfo2.splitNames;
        this.mSplitAppDirs = aInfo2.splitSourceDirs;
        this.mSplitResDirs = aInfo2.uid == myUid ? aInfo2.splitSourceDirs : aInfo2.splitPublicSourceDirs;
        this.mSplitClassLoaderNames = aInfo2.splitClassLoaderNames;
        if (aInfo2.requestsIsolatedSplitLoading() && !ArrayUtils.isEmpty(this.mSplitNames)) {
            this.mSplitLoader = new SplitDependencyLoaderImpl(aInfo2.splitDependencies);
            return true;
        }
        return true;
    }

    void setSdkSandboxStorage(String sdkSandboxClientAppVolumeUuid, String sdkSandboxClientAppPackage) {
        int userId = UserHandle.myUserId();
        this.mDeviceProtectedDataDirFile = Environment.getDataMiscDeSharedSdkSandboxDirectory(sdkSandboxClientAppVolumeUuid, userId, sdkSandboxClientAppPackage).getAbsoluteFile();
        this.mCredentialProtectedDataDirFile = Environment.getDataMiscCeSharedSdkSandboxDirectory(sdkSandboxClientAppVolumeUuid, userId, sdkSandboxClientAppPackage).getAbsoluteFile();
        if ((this.mApplicationInfo.privateFlags & 32) != 0) {
            this.mDataDirFile = this.mDeviceProtectedDataDirFile;
        } else {
            this.mDataDirFile = this.mCredentialProtectedDataDirFile;
        }
        this.mDataDir = this.mDataDirFile.getAbsolutePath();
    }

    public static void makePaths(ActivityThread activityThread, ApplicationInfo aInfo, List<String> outZipPaths) {
        makePaths(activityThread, false, aInfo, outZipPaths, null);
    }

    private static void appendSharedLibrariesLibPathsIfNeeded(List<SharedLibraryInfo> sharedLibraries, ApplicationInfo aInfo, Set<String> outSeenPaths, List<String> outLibPaths) {
        if (sharedLibraries == null) {
            return;
        }
        for (SharedLibraryInfo lib : sharedLibraries) {
            if (!lib.isNative()) {
                List<String> paths = lib.getAllCodePaths();
                outSeenPaths.addAll(paths);
                for (String path : paths) {
                    appendApkLibPathIfNeeded(path, aInfo, outLibPaths);
                }
                appendSharedLibrariesLibPathsIfNeeded(lib.getDependencies(), aInfo, outSeenPaths, outLibPaths);
            }
        }
    }

    public static void makePaths(ActivityThread activityThread, boolean isBundledApp, ApplicationInfo aInfo, List<String> list, List<String> outLibPaths) {
        String str = aInfo.sourceDir;
        String libDir = aInfo.nativeLibraryDir;
        list.clear();
        list.add(str);
        if (aInfo.splitSourceDirs != null && !aInfo.requestsIsolatedSplitLoading()) {
            Collections.addAll(list, aInfo.splitSourceDirs);
        }
        if (outLibPaths != null) {
            outLibPaths.clear();
        }
        String[] instrumentationLibs = null;
        if (activityThread != null) {
            String instrumentationPackageName = activityThread.mInstrumentationPackageName;
            String str2 = activityThread.mInstrumentationAppDir;
            String[] instrumentationSplitAppDirs = activityThread.mInstrumentationSplitAppDirs;
            String instrumentationLibDir = activityThread.mInstrumentationLibDir;
            String str3 = activityThread.mInstrumentedAppDir;
            String[] instrumentedSplitAppDirs = activityThread.mInstrumentedSplitAppDirs;
            String instrumentedLibDir = activityThread.mInstrumentedLibDir;
            if (str.equals(str2) || str.equals(str3)) {
                list.clear();
                list.add(str2);
                if (!str2.equals(str3)) {
                    list.add(str3);
                }
                if (!aInfo.requestsIsolatedSplitLoading()) {
                    if (instrumentationSplitAppDirs != null) {
                        Collections.addAll(list, instrumentationSplitAppDirs);
                    }
                    if (!str2.equals(str3) && instrumentedSplitAppDirs != null) {
                        Collections.addAll(list, instrumentedSplitAppDirs);
                    }
                }
                if (outLibPaths != null) {
                    outLibPaths.add(instrumentationLibDir);
                    if (!instrumentationLibDir.equals(instrumentedLibDir)) {
                        outLibPaths.add(instrumentedLibDir);
                    }
                }
                if (!str3.equals(str2)) {
                    instrumentationLibs = getLibrariesFor(instrumentationPackageName);
                }
            }
        }
        if (outLibPaths != null) {
            if (outLibPaths.isEmpty()) {
                outLibPaths.add(libDir);
            }
            if (aInfo.primaryCpuAbi != null) {
                if (aInfo.targetSdkVersion < 24) {
                    outLibPaths.add("/system/fake-libs" + (VMRuntime.is64BitAbi(aInfo.primaryCpuAbi) ? "64" : ""));
                }
                for (String apk : list) {
                    outLibPaths.add(apk + "!/lib/" + aInfo.primaryCpuAbi);
                }
            }
            if (isBundledApp) {
                outLibPaths.add(System.getProperty("java.library.path"));
            }
        }
        Set<String> outSeenPaths = new LinkedHashSet<>();
        appendSharedLibrariesLibPathsIfNeeded(aInfo.sharedLibraryInfos, aInfo, outSeenPaths, outLibPaths);
        if (aInfo.sharedLibraryFiles != null) {
            int index = 0;
            for (String lib : aInfo.sharedLibraryFiles) {
                if (lib.endsWith(".apk") && !outSeenPaths.contains(lib) && !list.contains(lib)) {
                    list.add(index, lib);
                    index++;
                    appendApkLibPathIfNeeded(lib, aInfo, outLibPaths);
                }
            }
        }
        if (instrumentationLibs != null) {
            for (String lib2 : instrumentationLibs) {
                if (!list.contains(lib2)) {
                    list.add(0, lib2);
                    appendApkLibPathIfNeeded(lib2, aInfo, outLibPaths);
                }
            }
        }
    }

    private static void appendApkLibPathIfNeeded(String path, ApplicationInfo applicationInfo, List<String> outLibPaths) {
        if (outLibPaths != null && applicationInfo.primaryCpuAbi != null && path.endsWith(".apk") && applicationInfo.targetSdkVersion >= 26) {
            outLibPaths.add(path + "!/lib/" + applicationInfo.primaryCpuAbi);
        }
    }

    private class SplitDependencyLoaderImpl extends SplitDependencyLoader<PackageManager.NameNotFoundException> {
        private final ClassLoader[] mCachedClassLoaders;
        private final String[][] mCachedResourcePaths;

        SplitDependencyLoaderImpl(SparseArray<int[]> dependencies) {
            super(dependencies);
            this.mCachedResourcePaths = new String[LoadedApk.this.mSplitNames.length + 1][];
            this.mCachedClassLoaders = new ClassLoader[LoadedApk.this.mSplitNames.length + 1];
        }

        @Override // android.content.pm.split.SplitDependencyLoader
        protected boolean isSplitCached(int splitIdx) {
            boolean z;
            synchronized (LoadedApk.this.mLock) {
                z = this.mCachedClassLoaders[splitIdx] != null;
            }
            return z;
        }

        @Override // android.content.pm.split.SplitDependencyLoader
        protected void constructSplit(int splitIdx, int[] configSplitIndices, int parentSplitIdx) throws PackageManager.NameNotFoundException {
            synchronized (LoadedApk.this.mLock) {
                ArrayList<String> splitPaths = new ArrayList<>();
                if (splitIdx == 0) {
                    LoadedApk.this.createOrUpdateClassLoaderLocked(null);
                    this.mCachedClassLoaders[0] = LoadedApk.this.mClassLoader;
                    for (int configSplitIdx : configSplitIndices) {
                        splitPaths.add(LoadedApk.this.mSplitResDirs[configSplitIdx - 1]);
                    }
                    this.mCachedResourcePaths[0] = (String[]) splitPaths.toArray(new String[splitPaths.size()]);
                    return;
                }
                ClassLoader parent = this.mCachedClassLoaders[parentSplitIdx];
                this.mCachedClassLoaders[splitIdx] = ApplicationLoaders.getDefault().getClassLoader(LoadedApk.this.mSplitAppDirs[splitIdx - 1], LoadedApk.this.getTargetSdkVersion(), false, null, null, parent, LoadedApk.this.mSplitClassLoaderNames[splitIdx - 1]);
                Collections.addAll(splitPaths, this.mCachedResourcePaths[parentSplitIdx]);
                splitPaths.add(LoadedApk.this.mSplitResDirs[splitIdx - 1]);
                for (int configSplitIdx2 : configSplitIndices) {
                    splitPaths.add(LoadedApk.this.mSplitResDirs[configSplitIdx2 - 1]);
                }
                this.mCachedResourcePaths[splitIdx] = (String[]) splitPaths.toArray(new String[splitPaths.size()]);
            }
        }

        private int ensureSplitLoaded(String splitName) throws PackageManager.NameNotFoundException {
            int idx = 0;
            if (splitName != null) {
                int idx2 = Arrays.binarySearch(LoadedApk.this.mSplitNames, splitName);
                if (idx2 < 0) {
                    throw new PackageManager.NameNotFoundException("Split name '" + splitName + "' is not installed");
                }
                idx = idx2 + 1;
            }
            loadDependenciesForSplit(idx);
            return idx;
        }

        ClassLoader getClassLoaderForSplit(String splitName) throws PackageManager.NameNotFoundException {
            ClassLoader classLoader;
            int idx = ensureSplitLoaded(splitName);
            synchronized (LoadedApk.this.mLock) {
                classLoader = this.mCachedClassLoaders[idx];
            }
            return classLoader;
        }

        String[] getSplitPathsForSplit(String splitName) throws PackageManager.NameNotFoundException {
            String[] strArr;
            int idx = ensureSplitLoaded(splitName);
            synchronized (LoadedApk.this.mLock) {
                strArr = this.mCachedResourcePaths[idx];
            }
            return strArr;
        }
    }

    ClassLoader getSplitClassLoader(String splitName) throws PackageManager.NameNotFoundException {
        if (this.mSplitLoader == null) {
            return this.mClassLoader;
        }
        return this.mSplitLoader.getClassLoaderForSplit(splitName);
    }

    String[] getSplitPaths(String splitName) throws PackageManager.NameNotFoundException {
        if (this.mSplitLoader == null) {
            return this.mSplitResDirs;
        }
        return this.mSplitLoader.getSplitPathsForSplit(splitName);
    }

    ClassLoader createSharedLibraryLoader(SharedLibraryInfo sharedLibrary, boolean isBundledApp, String librarySearchPath, String libraryPermittedPath) {
        List<String> paths = sharedLibrary.getAllCodePaths();
        Pair<List<ClassLoader>, List<ClassLoader>> sharedLibraries = createSharedLibrariesLoaders(sharedLibrary.getDependencies(), isBundledApp, librarySearchPath, libraryPermittedPath);
        String jars = paths.size() == 1 ? paths.get(0) : TextUtils.join(File.pathSeparator, paths);
        return ApplicationLoaders.getDefault().getSharedLibraryClassLoaderWithSharedLibraries(jars, this.mApplicationInfo.targetSdkVersion, isBundledApp, librarySearchPath, libraryPermittedPath, null, null, sharedLibraries.first, sharedLibraries.second);
    }

    private Pair<List<ClassLoader>, List<ClassLoader>> createSharedLibrariesLoaders(List<SharedLibraryInfo> sharedLibraries, boolean isBundledApp, String librarySearchPath, String libraryPermittedPath) {
        if (sharedLibraries == null || sharedLibraries.isEmpty()) {
            return new Pair<>(null, null);
        }
        HashSet<String> libsToLoadAfter = new HashSet<>();
        Resources systemR = Resources.getSystem();
        Collections.addAll(libsToLoadAfter, systemR.getStringArray(R.array.config_sharedLibrariesLoadedAfterApp));
        List<ClassLoader> loaders = new ArrayList<>();
        List<ClassLoader> after = new ArrayList<>();
        for (SharedLibraryInfo info : sharedLibraries) {
            if (!info.isNative() && !info.isSdk()) {
                if (libsToLoadAfter.contains(info.getName())) {
                    after.add(createSharedLibraryLoader(info, isBundledApp, librarySearchPath, libraryPermittedPath));
                } else {
                    loaders.add(createSharedLibraryLoader(info, isBundledApp, librarySearchPath, libraryPermittedPath));
                }
            }
        }
        return new Pair<>(loaders, after);
    }

    private StrictMode.ThreadPolicy allowThreadDiskReads() {
        if (this.mActivityThread == null) {
            return null;
        }
        return StrictMode.allowThreadDiskReads();
    }

    private void setThreadPolicy(StrictMode.ThreadPolicy policy) {
        if (this.mActivityThread != null && policy != null) {
            StrictMode.setThreadPolicy(policy);
        }
    }

    private StrictMode.VmPolicy allowVmViolations() {
        if (this.mActivityThread == null) {
            return null;
        }
        return StrictMode.allowVmViolations();
    }

    private void setVmPolicy(StrictMode.VmPolicy policy) {
        if (this.mActivityThread != null && policy != null) {
            StrictMode.setVmPolicy(policy);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Finally extract failed */
    /* JADX WARN: Removed duplicated region for block: B:66:0x01b3  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x01f9  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void createOrUpdateClassLoaderLocked(java.util.List<java.lang.String> r26) {
        /*
            Method dump skipped, instructions count: 771
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.LoadedApk.createOrUpdateClassLoaderLocked(java.util.List):void");
    }

    private boolean canAccessDataDir() {
        if (this.mActivityThread == null) {
            return false;
        }
        if (Objects.equals(this.mPackageName, ActivityThread.currentPackageName())) {
            return true;
        }
        if (this.mDataDir == null) {
            return false;
        }
        StrictMode.ThreadPolicy oldThreadPolicy = allowThreadDiskReads();
        StrictMode.VmPolicy oldVmPolicy = allowVmViolations();
        try {
            return new File(this.mDataDir).canExecute();
        } finally {
            setThreadPolicy(oldThreadPolicy);
            setVmPolicy(oldVmPolicy);
        }
    }

    public ClassLoader getClassLoader() {
        ClassLoader classLoader;
        synchronized (this.mLock) {
            if (this.mClassLoader == null) {
                createOrUpdateClassLoaderLocked(null);
            }
            classLoader = this.mClassLoader;
        }
        return classLoader;
    }

    private boolean isSpeg() {
        String apkFile;
        if (!CoreRune.SYSFW_APP_SPEG || (apkFile = this.mApplicationInfo.sourceDir) == null) {
            return false;
        }
        String spegFile = apkFile.substring(0, apkFile.lastIndexOf("/")) + "/base.speg" + this.mApplicationInfo.uid;
        StrictMode.ThreadPolicy oldThreadPolicy = allowThreadDiskReads();
        try {
            return new File(spegFile).exists();
        } finally {
            setThreadPolicy(oldThreadPolicy);
        }
    }

    private void registerAppInfoToArt() {
        int codePathType;
        if (this.mApplicationInfo.uid != Process.myUid()) {
            return;
        }
        List<String> codePaths = new ArrayList<>();
        if ((this.mApplicationInfo.flags & 4) != 0) {
            codePaths.add(this.mApplicationInfo.sourceDir);
        }
        if (this.mApplicationInfo.splitSourceDirs != null) {
            Collections.addAll(codePaths, this.mApplicationInfo.splitSourceDirs);
        }
        if (codePaths.isEmpty()) {
            return;
        }
        int i = codePaths.size() - 1;
        while (i >= 0) {
            String splitName = i == 0 ? null : this.mApplicationInfo.splitNames[i - 1];
            String curProfileFile = ArtManager.getCurrentProfilePath(this.mPackageName, UserHandle.myUserId(), splitName);
            String refProfileFile = ArtManager.getReferenceProfilePath(this.mPackageName, UserHandle.myUserId(), splitName);
            if (codePaths.get(i).equals(this.mApplicationInfo.sourceDir)) {
                codePathType = 1;
            } else {
                codePathType = 2;
            }
            VMRuntime.registerAppInfo(this.mPackageName, curProfileFile, refProfileFile, new String[]{codePaths.get(i)}, codePathType);
            i--;
        }
        if (isSpeg()) {
            try {
                System.loadLibrary("speg");
            } catch (UnsatisfiedLinkError e) {
                Log.e("SPEG", "Library not found: " + e);
            }
        }
        DexLoadReporter.getInstance().registerAppDataDir(this.mPackageName, this.mDataDir);
    }

    private void initializeJavaContextClassLoader() {
        ClassLoader contextClassLoader;
        ActivityThread.getPackageManager();
        PackageInfo pi = PackageManager.getPackageInfoAsUserCached(this.mPackageName, 268435456L, UserHandle.myUserId());
        if (pi == null) {
            throw new IllegalStateException("Unable to get package info for " + this.mPackageName + "; is package not installed?");
        }
        boolean sharable = true;
        boolean sharedUserIdSet = pi.sharedUserId != null;
        boolean processNameNotDefault = (pi.applicationInfo == null || this.mPackageName.equals(pi.applicationInfo.processName)) ? false : true;
        if (!sharedUserIdSet && !processNameNotDefault) {
            sharable = false;
        }
        if (sharable) {
            contextClassLoader = new WarningContextClassLoader();
        } else {
            contextClassLoader = this.mClassLoader;
        }
        Thread.currentThread().setContextClassLoader(contextClassLoader);
    }

    private static class WarningContextClassLoader extends ClassLoader {
        private static boolean warned = false;

        private WarningContextClassLoader() {
        }

        private void warn(String methodName) {
            if (warned) {
                return;
            }
            warned = true;
            Thread.currentThread().setContextClassLoader(getParent());
            Slog.w(ActivityThread.TAG, "ClassLoader." + methodName + ": The class loader returned by Thread.getContextClassLoader() may fail for processes that host multiple applications. You should explicitly specify a context class loader. For example: Thread.setContextClassLoader(getClass().getClassLoader());");
        }

        @Override // java.lang.ClassLoader
        public URL getResource(String resName) {
            warn("getResource");
            return getParent().getResource(resName);
        }

        @Override // java.lang.ClassLoader
        public Enumeration<URL> getResources(String resName) throws IOException {
            warn("getResources");
            return getParent().getResources(resName);
        }

        @Override // java.lang.ClassLoader
        public InputStream getResourceAsStream(String resName) {
            warn("getResourceAsStream");
            return getParent().getResourceAsStream(resName);
        }

        @Override // java.lang.ClassLoader
        public Class<?> loadClass(String className) throws ClassNotFoundException {
            warn("loadClass");
            return getParent().loadClass(className);
        }

        @Override // java.lang.ClassLoader
        public void setClassAssertionStatus(String cname, boolean enable) {
            warn("setClassAssertionStatus");
            getParent().setClassAssertionStatus(cname, enable);
        }

        @Override // java.lang.ClassLoader
        public void setPackageAssertionStatus(String pname, boolean enable) {
            warn("setPackageAssertionStatus");
            getParent().setPackageAssertionStatus(pname, enable);
        }

        @Override // java.lang.ClassLoader
        public void setDefaultAssertionStatus(boolean enable) {
            warn("setDefaultAssertionStatus");
            getParent().setDefaultAssertionStatus(enable);
        }

        @Override // java.lang.ClassLoader
        public void clearAssertionStatus() {
            warn("clearAssertionStatus");
            getParent().clearAssertionStatus();
        }
    }

    public String getAppDir() {
        return this.mAppDir;
    }

    public String getLibDir() {
        return this.mLibDir;
    }

    public String getResDir() {
        return this.mResDir;
    }

    public String[] getSplitAppDirs() {
        return this.mSplitAppDirs;
    }

    public String[] getSplitResDirs() {
        return this.mSplitResDirs;
    }

    public String[] getOverlayDirs() {
        return this.mLegacyOverlayDirs;
    }

    public String[] getOverlayPaths() {
        return this.mOverlayPaths;
    }

    public String getDataDir() {
        return this.mDataDir;
    }

    public File getDataDirFile() {
        return this.mDataDirFile;
    }

    public File getDeviceProtectedDataDirFile() {
        return this.mDeviceProtectedDataDirFile;
    }

    public File getCredentialProtectedDataDirFile() {
        return this.mCredentialProtectedDataDirFile;
    }

    public AssetManager getAssets() {
        return getResources().getAssets();
    }

    public Resources getResources() {
        if (this.mResources == null) {
            try {
                String[] splitPaths = getSplitPaths(null);
                if (Process.myUid() == this.mApplicationInfo.uid) {
                    ResourcesManager.getInstance().initializeApplicationPaths(this.mResDir, splitPaths);
                }
                this.mResources = ResourcesManager.getInstance().getResources(null, this.mResDir, splitPaths, this.mLegacyOverlayDirs, this.mOverlayPaths, this.mApplicationInfo.sharedLibraryFiles, null, null, getCompatibilityInfo(), getClassLoader(), null);
            } catch (PackageManager.NameNotFoundException e) {
                throw new AssertionError("null split not found");
            }
        }
        return this.mResources;
    }

    public Application makeApplication(boolean forceDefaultAppClass, Instrumentation instrumentation) {
        return makeApplicationInner(forceDefaultAppClass, instrumentation, true);
    }

    public Application makeApplicationInner(boolean forceDefaultAppClass, Instrumentation instrumentation) {
        return makeApplicationInner(forceDefaultAppClass, instrumentation, false);
    }

    private Application makeApplicationInner(boolean forceDefaultAppClass, Instrumentation instrumentation, boolean allowDuplicateInstances) {
        if (this.mApplication != null) {
            return this.mApplication;
        }
        if (Trace.isTagEnabled(64L)) {
            Trace.traceBegin(64L, "makeApplication");
        }
        try {
            synchronized (sApplications) {
                Application cached = sApplications.get(this.mPackageName);
                if (cached != null) {
                    if (!"android".equals(this.mPackageName)) {
                        Slog.wtfStack(TAG, "App instance already created for package=" + this.mPackageName + " instance=" + cached);
                    }
                    if (!allowDuplicateInstances) {
                        this.mApplication = cached;
                        return cached;
                    }
                }
                Application app = null;
                String myProcessName = Process.myProcessName();
                String appClass = this.mApplicationInfo.getCustomApplicationClassNameForProcess(myProcessName);
                if (forceDefaultAppClass || appClass == null) {
                    appClass = "android.app.Application";
                }
                try {
                    ClassLoader cl = getClassLoader();
                    if (!this.mPackageName.equals("android")) {
                        Trace.traceBegin(64L, "initializeJavaContextClassLoader");
                        initializeJavaContextClassLoader();
                        Trace.traceEnd(64L);
                    }
                    SparseArray<String> packageIdentifiers = getAssets().getAssignedPackageIdentifiers(false, false);
                    int n = packageIdentifiers.size();
                    for (int i = 0; i < n; i++) {
                        int id = packageIdentifiers.keyAt(i);
                        if (id != 1 && id != 127) {
                            rewriteRValues(cl, packageIdentifiers.valueAt(i), id);
                        }
                    }
                    ContextImpl appContext = ContextImpl.createAppContext(this.mActivityThread, this);
                    NetworkSecurityConfigProvider.handleNewApplication(appContext);
                    app = this.mActivityThread.mInstrumentation.newApplication(cl, appClass, appContext);
                    appContext.setOuterContext(app);
                } catch (Exception e) {
                    if (!this.mActivityThread.mInstrumentation.onException(app, e)) {
                        throw new RuntimeException("Unable to instantiate application " + appClass + " package " + this.mPackageName + ": " + e.toString(), e);
                    }
                }
                this.mActivityThread.mAllApplications.add(app);
                this.mApplication = app;
                if (!allowDuplicateInstances) {
                    synchronized (sApplications) {
                        sApplications.put(this.mPackageName, app);
                    }
                }
                if (instrumentation != null) {
                    try {
                        instrumentation.callApplicationOnCreate(app);
                    } catch (Exception e2) {
                        if (!instrumentation.onException(app, e2)) {
                            throw new RuntimeException("Unable to create application " + app.getClass().getName() + ": " + e2.toString(), e2);
                        }
                    }
                }
                return app;
            }
        } finally {
            Trace.traceEnd(64L);
        }
    }

    private void rewriteRValues(ClassLoader cl, String packageName, int id) {
        Throwable cause;
        try {
            Class<?> rClazz = cl.loadClass(packageName + ".R");
            try {
                Method callback = rClazz.getMethod("onResourcesLoaded", Integer.TYPE);
                try {
                    callback.invoke(null, Integer.valueOf(id));
                } catch (IllegalAccessException e) {
                    cause = e;
                    throw new RuntimeException("Failed to rewrite resource references for " + packageName, cause);
                } catch (InvocationTargetException e2) {
                    cause = e2.getCause();
                    throw new RuntimeException("Failed to rewrite resource references for " + packageName, cause);
                }
            } catch (NoSuchMethodException e3) {
            }
        } catch (ClassNotFoundException e4) {
            Log.i(TAG, "No resource references to update in package " + packageName);
        }
    }

    public void removeContextRegistrations(Context context, String who, String what) {
        boolean reportRegistrationLeaks = StrictMode.vmRegistrationLeaksEnabled();
        synchronized (this.mReceivers) {
            ArrayMap<BroadcastReceiver, ReceiverDispatcher> rmap = this.mReceivers.remove(context);
            if (rmap != null) {
                for (int i = 0; i < rmap.size(); i++) {
                    ReceiverDispatcher rd = rmap.valueAt(i);
                    IntentReceiverLeaked leak = new IntentReceiverLeaked(what + " " + who + " has leaked IntentReceiver " + rd.getIntentReceiver() + " that was originally registered here. Are you missing a call to unregisterReceiver()?");
                    leak.setStackTrace(rd.getLocation().getStackTrace());
                    Slog.e(ActivityThread.TAG, leak.getMessage(), leak);
                    if (reportRegistrationLeaks) {
                        StrictMode.onIntentReceiverLeaked(leak);
                    }
                    try {
                        ActivityManager.getService().unregisterReceiver(rd.getIIntentReceiver());
                    } catch (RemoteException e) {
                        throw e.rethrowFromSystemServer();
                    }
                }
            }
            this.mUnregisteredReceivers.remove(context);
        }
        synchronized (this.mServices) {
            ArrayMap<ServiceConnection, ServiceDispatcher> smap = this.mServices.remove(context);
            if (smap != null) {
                for (int i2 = 0; i2 < smap.size(); i2++) {
                    ServiceDispatcher sd = smap.valueAt(i2);
                    ServiceConnectionLeaked leak2 = new ServiceConnectionLeaked(what + " " + who + " has leaked ServiceConnection " + sd.getServiceConnection() + " that was originally bound here");
                    leak2.setStackTrace(sd.getLocation().getStackTrace());
                    Slog.e(ActivityThread.TAG, leak2.getMessage(), leak2);
                    if (reportRegistrationLeaks) {
                        StrictMode.onServiceConnectionLeaked(leak2);
                    }
                    try {
                        ActivityManager.getService().unbindService(sd.getIServiceConnection());
                        sd.doForget();
                    } catch (RemoteException e2) {
                        throw e2.rethrowFromSystemServer();
                    }
                }
            }
            this.mUnboundServices.remove(context);
        }
    }

    public IIntentReceiver getReceiverDispatcher(BroadcastReceiver r, Context context, Handler handler, Instrumentation instrumentation, boolean registered) {
        ArrayMap<BroadcastReceiver, ReceiverDispatcher> map;
        synchronized (this.mReceivers) {
            ReceiverDispatcher rd = null;
            try {
                if (!registered) {
                    map = null;
                } else {
                    try {
                        ArrayMap<BroadcastReceiver, ReceiverDispatcher> map2 = this.mReceivers.get(context);
                        if (map2 == null) {
                            map = map2;
                        } else {
                            rd = map2.get(r);
                            map = map2;
                        }
                    } catch (Throwable th) {
                        th = th;
                        throw th;
                    }
                }
                if (rd == null) {
                    rd = new ReceiverDispatcher(this.mActivityThread.getApplicationThread(), r, context, handler, instrumentation, registered);
                    if (registered) {
                        if (map == null) {
                            ArrayMap<BroadcastReceiver, ReceiverDispatcher> map3 = new ArrayMap<>();
                            this.mReceivers.put(context, map3);
                            map = map3;
                        }
                        map.put(r, rd);
                    }
                } else {
                    rd.validate(context, handler);
                }
                rd.mForgotten = false;
                return rd.getIIntentReceiver();
            } catch (Throwable th2) {
                th = th2;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:30:0x00b4, code lost:
    
        throw new java.lang.IllegalStateException("Unbinding Receiver " + r10 + " from Context that is no longer in use: " + r9);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.content.IIntentReceiver forgetReceiverDispatcher(android.content.Context r9, android.content.BroadcastReceiver r10) {
        /*
            r8 = this;
            android.util.ArrayMap<android.content.Context, android.util.ArrayMap<android.content.BroadcastReceiver, android.app.LoadedApk$ReceiverDispatcher>> r0 = r8.mReceivers
            monitor-enter(r0)
            android.util.ArrayMap<android.content.Context, android.util.ArrayMap<android.content.BroadcastReceiver, android.app.LoadedApk$ReceiverDispatcher>> r1 = r8.mReceivers     // Catch: java.lang.Throwable -> Lce
            java.lang.Object r1 = r1.get(r9)     // Catch: java.lang.Throwable -> Lce
            android.util.ArrayMap r1 = (android.util.ArrayMap) r1     // Catch: java.lang.Throwable -> Lce
            r2 = 0
            if (r1 == 0) goto L59
            java.lang.Object r3 = r1.get(r10)     // Catch: java.lang.Throwable -> Lce
            android.app.LoadedApk$ReceiverDispatcher r3 = (android.app.LoadedApk.ReceiverDispatcher) r3     // Catch: java.lang.Throwable -> Lce
            r2 = r3
            if (r2 == 0) goto L59
            r1.remove(r10)     // Catch: java.lang.Throwable -> Lce
            int r3 = r1.size()     // Catch: java.lang.Throwable -> Lce
            if (r3 != 0) goto L25
            android.util.ArrayMap<android.content.Context, android.util.ArrayMap<android.content.BroadcastReceiver, android.app.LoadedApk$ReceiverDispatcher>> r3 = r8.mReceivers     // Catch: java.lang.Throwable -> Lce
            r3.remove(r9)     // Catch: java.lang.Throwable -> Lce
        L25:
            boolean r3 = r10.getDebugUnregister()     // Catch: java.lang.Throwable -> Lce
            if (r3 == 0) goto L50
            android.util.ArrayMap<android.content.Context, android.util.ArrayMap<android.content.BroadcastReceiver, android.app.LoadedApk$ReceiverDispatcher>> r3 = r8.mUnregisteredReceivers     // Catch: java.lang.Throwable -> Lce
            java.lang.Object r3 = r3.get(r9)     // Catch: java.lang.Throwable -> Lce
            android.util.ArrayMap r3 = (android.util.ArrayMap) r3     // Catch: java.lang.Throwable -> Lce
            if (r3 != 0) goto L40
            android.util.ArrayMap r4 = new android.util.ArrayMap     // Catch: java.lang.Throwable -> Lce
            r4.<init>()     // Catch: java.lang.Throwable -> Lce
            r3 = r4
            android.util.ArrayMap<android.content.Context, android.util.ArrayMap<android.content.BroadcastReceiver, android.app.LoadedApk$ReceiverDispatcher>> r4 = r8.mUnregisteredReceivers     // Catch: java.lang.Throwable -> Lce
            r4.put(r9, r3)     // Catch: java.lang.Throwable -> Lce
        L40:
            java.lang.IllegalArgumentException r4 = new java.lang.IllegalArgumentException     // Catch: java.lang.Throwable -> Lce
            java.lang.String r5 = "Originally unregistered here:"
            r4.<init>(r5)     // Catch: java.lang.Throwable -> Lce
            r4.fillInStackTrace()     // Catch: java.lang.Throwable -> Lce
            r2.setUnregisterLocation(r4)     // Catch: java.lang.Throwable -> Lce
            r3.put(r10, r2)     // Catch: java.lang.Throwable -> Lce
        L50:
            r3 = 1
            r2.mForgotten = r3     // Catch: java.lang.Throwable -> Lce
            android.content.IIntentReceiver r3 = r2.getIIntentReceiver()     // Catch: java.lang.Throwable -> Lce
            monitor-exit(r0)     // Catch: java.lang.Throwable -> Lce
            return r3
        L59:
            android.util.ArrayMap<android.content.Context, android.util.ArrayMap<android.content.BroadcastReceiver, android.app.LoadedApk$ReceiverDispatcher>> r3 = r8.mUnregisteredReceivers     // Catch: java.lang.Throwable -> Lce
            java.lang.Object r3 = r3.get(r9)     // Catch: java.lang.Throwable -> Lce
            android.util.ArrayMap r3 = (android.util.ArrayMap) r3     // Catch: java.lang.Throwable -> Lce
            if (r3 == 0) goto L90
            java.lang.Object r4 = r3.get(r10)     // Catch: java.lang.Throwable -> Lce
            android.app.LoadedApk$ReceiverDispatcher r4 = (android.app.LoadedApk.ReceiverDispatcher) r4     // Catch: java.lang.Throwable -> Lce
            r2 = r4
            if (r2 != 0) goto L6d
            goto L90
        L6d:
            java.lang.RuntimeException r4 = r2.getUnregisterLocation()     // Catch: java.lang.Throwable -> Lce
            java.lang.IllegalArgumentException r5 = new java.lang.IllegalArgumentException     // Catch: java.lang.Throwable -> Lce
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Lce
            r6.<init>()     // Catch: java.lang.Throwable -> Lce
            java.lang.String r7 = "Unregistering Receiver "
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch: java.lang.Throwable -> Lce
            java.lang.StringBuilder r6 = r6.append(r10)     // Catch: java.lang.Throwable -> Lce
            java.lang.String r7 = " that was already unregistered"
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch: java.lang.Throwable -> Lce
            java.lang.String r6 = r6.toString()     // Catch: java.lang.Throwable -> Lce
            r5.<init>(r6, r4)     // Catch: java.lang.Throwable -> Lce
            throw r5     // Catch: java.lang.Throwable -> Lce
        L90:
            if (r9 != 0) goto Lb5
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException     // Catch: java.lang.Throwable -> Lce
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Lce
            r5.<init>()     // Catch: java.lang.Throwable -> Lce
            java.lang.String r6 = "Unbinding Receiver "
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch: java.lang.Throwable -> Lce
            java.lang.StringBuilder r5 = r5.append(r10)     // Catch: java.lang.Throwable -> Lce
            java.lang.String r6 = " from Context that is no longer in use: "
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch: java.lang.Throwable -> Lce
            java.lang.StringBuilder r5 = r5.append(r9)     // Catch: java.lang.Throwable -> Lce
            java.lang.String r5 = r5.toString()     // Catch: java.lang.Throwable -> Lce
            r4.<init>(r5)     // Catch: java.lang.Throwable -> Lce
            throw r4     // Catch: java.lang.Throwable -> Lce
        Lb5:
            java.lang.IllegalArgumentException r4 = new java.lang.IllegalArgumentException     // Catch: java.lang.Throwable -> Lce
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Lce
            r5.<init>()     // Catch: java.lang.Throwable -> Lce
            java.lang.String r6 = "Receiver not registered: "
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch: java.lang.Throwable -> Lce
            java.lang.StringBuilder r5 = r5.append(r10)     // Catch: java.lang.Throwable -> Lce
            java.lang.String r5 = r5.toString()     // Catch: java.lang.Throwable -> Lce
            r4.<init>(r5)     // Catch: java.lang.Throwable -> Lce
            throw r4     // Catch: java.lang.Throwable -> Lce
        Lce:
            r1 = move-exception
            monitor-exit(r0)     // Catch: java.lang.Throwable -> Lce
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.LoadedApk.forgetReceiverDispatcher(android.content.Context, android.content.BroadcastReceiver):android.content.IIntentReceiver");
    }

    static final class ReceiverDispatcher {
        final Handler mActivityThread;
        final IApplicationThread mAppThread;
        final Context mContext;
        boolean mForgotten;
        final IIntentReceiver.Stub mIIntentReceiver;
        final Instrumentation mInstrumentation;
        final IntentReceiverLeaked mLocation;
        final BroadcastReceiver mReceiver;
        final boolean mRegistered;
        RuntimeException mUnregisterLocation;

        static final class InnerReceiver extends IIntentReceiver.Stub {
            final IApplicationThread mApplicationThread;
            final WeakReference<ReceiverDispatcher> mDispatcher;
            final ReceiverDispatcher mStrongRef;

            InnerReceiver(IApplicationThread thread, ReceiverDispatcher rd, boolean strong) {
                this.mApplicationThread = thread;
                this.mDispatcher = new WeakReference<>(rd);
                this.mStrongRef = strong ? rd : null;
            }

            @Override // android.content.IIntentReceiver
            public void performReceive(Intent intent, int resultCode, String data, Bundle extras, boolean ordered, boolean sticky, int sendingUser) {
                Log.wtf(LoadedApk.TAG, "performReceive() called targeting raw IIntentReceiver for " + intent);
                performReceive(intent, resultCode, data, extras, ordered, sticky, BroadcastReceiver.PendingResult.guessAssumeDelivered(1, ordered), sendingUser, -1, null);
            }

            public void performReceive(Intent intent, int resultCode, String data, Bundle extras, boolean ordered, boolean sticky, boolean assumeDelivered, int sendingUser, int sendingUid, String sendingPackage) {
                ReceiverDispatcher rd;
                if (intent == null) {
                    Log.wtf(LoadedApk.TAG, "Null intent received");
                    rd = null;
                } else {
                    rd = this.mDispatcher.get();
                }
                if (rd != null) {
                    rd.performReceive(intent, resultCode, data, extras, ordered, sticky, assumeDelivered, sendingUser, sendingUid, sendingPackage);
                    return;
                }
                if (!assumeDelivered) {
                    IActivityManager mgr = ActivityManager.getService();
                    if (extras != null) {
                        try {
                            extras.setAllowFds(false);
                        } catch (RemoteException e) {
                            throw e.rethrowFromSystemServer();
                        }
                    }
                    mgr.finishReceiver(this.mApplicationThread.asBinder(), resultCode, data, extras, false, intent != null ? intent.getFlags() : 0);
                }
            }

            public String toString() {
                ReceiverDispatcher rd = this.mDispatcher.get();
                if (rd != null) {
                    return String.valueOf(rd.getIntentReceiver());
                }
                return "";
            }
        }

        final class Args extends BroadcastReceiver.PendingResult {
            private Intent mCurIntent;
            private boolean mDispatched;
            private long mHandleOnSystemMainOLOGThresMs;
            private boolean mRunCalled;

            public Args(Intent intent, int resultCode, String resultData, Bundle resultExtras, boolean ordered, boolean sticky, boolean assumeDelivered, int sendingUser, int sendingUid, String sendingPackage) {
                super(resultCode, resultData, resultExtras, ReceiverDispatcher.this.mRegistered ? 1 : 2, ordered, sticky, assumeDelivered, ReceiverDispatcher.this.mAppThread.asBinder(), sendingUser, intent.getFlags(), sendingUid, sendingPackage);
                this.mHandleOnSystemMainOLOGThresMs = 100L;
                this.mCurIntent = intent;
            }

            public final Runnable getRunnable() {
                return new Runnable() { // from class: android.app.LoadedApk$ReceiverDispatcher$Args$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        LoadedApk.ReceiverDispatcher.Args.this.lambda$getRunnable$0();
                    }
                };
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$getRunnable$0() {
                BroadcastReceiver receiver = ReceiverDispatcher.this.mReceiver;
                IActivityManager mgr = ActivityManager.getService();
                Intent intent = this.mCurIntent;
                if (intent == null) {
                    Log.wtf(LoadedApk.TAG, "Null intent being dispatched, mDispatched=" + this.mDispatched + (this.mRunCalled ? ", run() has already been called" : ""));
                }
                this.mCurIntent = null;
                this.mDispatched = true;
                this.mRunCalled = true;
                if (receiver == null || intent == null || ReceiverDispatcher.this.mForgotten) {
                    sendFinished(mgr);
                    return;
                }
                long dispatchStart = SystemClock.elapsedRealtime();
                if (Trace.isTagEnabled(64L)) {
                    Trace.traceBegin(64L, "broadcastReceiveReg: " + ReceiverDispatcher.this.mReceiver + ", for " + intent.getAction());
                }
                try {
                    ClassLoader cl = ReceiverDispatcher.this.mReceiver.getClass().getClassLoader();
                    intent.setExtrasClassLoader(cl);
                    intent.prepareToEnterProcess(ActivityThread.isProtectedBroadcast(intent), ReceiverDispatcher.this.mContext.getAttributionSource());
                    setExtrasClassLoader(cl);
                    receiver.setPendingResult(this);
                    receiver.onReceive(ReceiverDispatcher.this.mContext, intent);
                } catch (Exception e) {
                    sendFinished(mgr);
                    if (ReceiverDispatcher.this.mInstrumentation == null || !ReceiverDispatcher.this.mInstrumentation.onException(ReceiverDispatcher.this.mReceiver, e)) {
                        Trace.traceEnd(64L);
                        throw new RuntimeException("Error receiving broadcast " + intent + " in " + ReceiverDispatcher.this.mReceiver, e);
                    }
                }
                if (receiver.getPendingResult() != null) {
                    finish();
                }
                Trace.traceEnd(64L);
                long dispatchEnd = SystemClock.elapsedRealtime();
                long handleTime = dispatchEnd - dispatchStart;
                try {
                    boolean mPerfLogEnable = Looper.myLooper().isPerfLogEnable();
                    if (mPerfLogEnable && handleTime > this.mHandleOnSystemMainOLOGThresMs) {
                        PerfLog.d(5, " system_server main thread handled for " + intent.getAction() + " took " + handleTime + " ms, Receiver = " + ReceiverDispatcher.this.mReceiver);
                        Slog.w(LoadedApk.TAG, "Slow system_server main thread handled for " + intent.getAction() + " took " + handleTime + " ms, Receiver = " + ReceiverDispatcher.this.mReceiver);
                    }
                } catch (Exception e2) {
                    Slog.e(LoadedApk.TAG, "Exception : " + e2.toString());
                }
            }
        }

        ReceiverDispatcher(IApplicationThread appThread, BroadcastReceiver receiver, Context context, Handler activityThread, Instrumentation instrumentation, boolean registered) {
            if (activityThread == null) {
                throw new NullPointerException("Handler must not be null");
            }
            this.mAppThread = appThread;
            this.mIIntentReceiver = new InnerReceiver(this.mAppThread, this, !registered);
            this.mReceiver = receiver;
            this.mContext = context;
            this.mActivityThread = activityThread;
            this.mInstrumentation = instrumentation;
            this.mRegistered = registered;
            this.mLocation = new IntentReceiverLeaked(null);
            this.mLocation.fillInStackTrace();
        }

        void validate(Context context, Handler activityThread) {
            if (this.mContext != context) {
                throw new IllegalStateException("Receiver " + this.mReceiver + " registered with differing Context (was " + this.mContext + " now " + context + NavigationBarInflaterView.KEY_CODE_END);
            }
            if (this.mActivityThread != activityThread) {
                throw new IllegalStateException("Receiver " + this.mReceiver + " registered with differing handler (was " + this.mActivityThread + " now " + activityThread + NavigationBarInflaterView.KEY_CODE_END);
            }
        }

        IntentReceiverLeaked getLocation() {
            return this.mLocation;
        }

        BroadcastReceiver getIntentReceiver() {
            return this.mReceiver;
        }

        IIntentReceiver getIIntentReceiver() {
            return this.mIIntentReceiver;
        }

        void setUnregisterLocation(RuntimeException ex) {
            this.mUnregisterLocation = ex;
        }

        RuntimeException getUnregisterLocation() {
            return this.mUnregisterLocation;
        }

        public void performReceive(Intent intent, int resultCode, String data, Bundle extras, boolean ordered, boolean sticky, boolean assumeDelivered, int sendingUser, int sendingUid, String sendingPackage) {
            Args args = new Args(intent, resultCode, data, extras, ordered, sticky, assumeDelivered, sendingUser, sendingUid, sendingPackage);
            if (intent == null) {
                Log.wtf(LoadedApk.TAG, "Null intent received");
            }
            if (intent != null && this.mActivityThread.post(args.getRunnable())) {
                return;
            }
            IActivityManager mgr = ActivityManager.getService();
            args.sendFinished(mgr);
        }
    }

    public final IServiceConnection getServiceDispatcher(ServiceConnection c, Context context, Handler handler, long flags) {
        return getServiceDispatcherCommon(c, context, handler, null, flags);
    }

    public final IServiceConnection getServiceDispatcher(ServiceConnection c, Context context, Executor executor, long flags) {
        return getServiceDispatcherCommon(c, context, null, executor, flags);
    }

    private IServiceConnection getServiceDispatcherCommon(ServiceConnection c, Context context, Handler handler, Executor executor, long flags) {
        synchronized (this.mServices) {
            ServiceDispatcher sd = null;
            try {
                try {
                    ArrayMap<ServiceConnection, ServiceDispatcher> map = this.mServices.get(context);
                    if (map != null) {
                        sd = map.get(c);
                    }
                    if (sd == null) {
                        if (executor != null) {
                            sd = new ServiceDispatcher(c, context, executor, flags);
                        } else {
                            sd = new ServiceDispatcher(c, context, handler, flags);
                        }
                        if (map == null) {
                            ArrayMap<ServiceConnection, ServiceDispatcher> map2 = new ArrayMap<>();
                            this.mServices.put(context, map2);
                            map = map2;
                        }
                        map.put(c, sd);
                    } else {
                        sd.validate(context, handler, executor);
                    }
                    return sd.getIServiceConnection();
                } catch (Throwable th) {
                    th = th;
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                throw th;
            }
        }
    }

    public IServiceConnection lookupServiceDispatcher(ServiceConnection c, Context context) {
        IServiceConnection iServiceConnection;
        synchronized (this.mServices) {
            ServiceDispatcher sd = null;
            ArrayMap<ServiceConnection, ServiceDispatcher> map = this.mServices.get(context);
            if (map != null) {
                sd = map.get(c);
            }
            iServiceConnection = sd != null ? sd.getIServiceConnection() : null;
        }
        return iServiceConnection;
    }

    /* JADX WARN: Code restructure failed: missing block: B:30:0x00bb, code lost:
    
        throw new java.lang.IllegalStateException("Unbinding Service " + r10 + " from Context that is no longer in use: " + r9);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.app.IServiceConnection forgetServiceDispatcher(android.content.Context r9, android.content.ServiceConnection r10) {
        /*
            r8 = this;
            android.util.ArrayMap<android.content.Context, android.util.ArrayMap<android.content.ServiceConnection, android.app.LoadedApk$ServiceDispatcher>> r0 = r8.mServices
            monitor-enter(r0)
            android.util.ArrayMap<android.content.Context, android.util.ArrayMap<android.content.ServiceConnection, android.app.LoadedApk$ServiceDispatcher>> r1 = r8.mServices     // Catch: java.lang.Throwable -> Ld5
            java.lang.Object r1 = r1.get(r9)     // Catch: java.lang.Throwable -> Ld5
            android.util.ArrayMap r1 = (android.util.ArrayMap) r1     // Catch: java.lang.Throwable -> Ld5
            r2 = 0
            if (r1 == 0) goto L60
            java.lang.Object r3 = r1.get(r10)     // Catch: java.lang.Throwable -> Ld5
            android.app.LoadedApk$ServiceDispatcher r3 = (android.app.LoadedApk.ServiceDispatcher) r3     // Catch: java.lang.Throwable -> Ld5
            r2 = r3
            if (r2 == 0) goto L60
            r1.remove(r10)     // Catch: java.lang.Throwable -> Ld5
            r2.doForget()     // Catch: java.lang.Throwable -> Ld5
            int r3 = r1.size()     // Catch: java.lang.Throwable -> Ld5
            if (r3 != 0) goto L28
            android.util.ArrayMap<android.content.Context, android.util.ArrayMap<android.content.ServiceConnection, android.app.LoadedApk$ServiceDispatcher>> r3 = r8.mServices     // Catch: java.lang.Throwable -> Ld5
            r3.remove(r9)     // Catch: java.lang.Throwable -> Ld5
        L28:
            long r3 = r2.getFlags()     // Catch: java.lang.Throwable -> Ld5
            r5 = 2
            long r3 = r3 & r5
            r5 = 0
            int r3 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r3 == 0) goto L5a
            android.util.ArrayMap<android.content.Context, android.util.ArrayMap<android.content.ServiceConnection, android.app.LoadedApk$ServiceDispatcher>> r3 = r8.mUnboundServices     // Catch: java.lang.Throwable -> Ld5
            java.lang.Object r3 = r3.get(r9)     // Catch: java.lang.Throwable -> Ld5
            android.util.ArrayMap r3 = (android.util.ArrayMap) r3     // Catch: java.lang.Throwable -> Ld5
            if (r3 != 0) goto L4a
            android.util.ArrayMap r4 = new android.util.ArrayMap     // Catch: java.lang.Throwable -> Ld5
            r4.<init>()     // Catch: java.lang.Throwable -> Ld5
            r3 = r4
            android.util.ArrayMap<android.content.Context, android.util.ArrayMap<android.content.ServiceConnection, android.app.LoadedApk$ServiceDispatcher>> r4 = r8.mUnboundServices     // Catch: java.lang.Throwable -> Ld5
            r4.put(r9, r3)     // Catch: java.lang.Throwable -> Ld5
        L4a:
            java.lang.IllegalArgumentException r4 = new java.lang.IllegalArgumentException     // Catch: java.lang.Throwable -> Ld5
            java.lang.String r5 = "Originally unbound here:"
            r4.<init>(r5)     // Catch: java.lang.Throwable -> Ld5
            r4.fillInStackTrace()     // Catch: java.lang.Throwable -> Ld5
            r2.setUnbindLocation(r4)     // Catch: java.lang.Throwable -> Ld5
            r3.put(r10, r2)     // Catch: java.lang.Throwable -> Ld5
        L5a:
            android.app.IServiceConnection r3 = r2.getIServiceConnection()     // Catch: java.lang.Throwable -> Ld5
            monitor-exit(r0)     // Catch: java.lang.Throwable -> Ld5
            return r3
        L60:
            android.util.ArrayMap<android.content.Context, android.util.ArrayMap<android.content.ServiceConnection, android.app.LoadedApk$ServiceDispatcher>> r3 = r8.mUnboundServices     // Catch: java.lang.Throwable -> Ld5
            java.lang.Object r3 = r3.get(r9)     // Catch: java.lang.Throwable -> Ld5
            android.util.ArrayMap r3 = (android.util.ArrayMap) r3     // Catch: java.lang.Throwable -> Ld5
            if (r3 == 0) goto L97
            java.lang.Object r4 = r3.get(r10)     // Catch: java.lang.Throwable -> Ld5
            android.app.LoadedApk$ServiceDispatcher r4 = (android.app.LoadedApk.ServiceDispatcher) r4     // Catch: java.lang.Throwable -> Ld5
            r2 = r4
            if (r2 != 0) goto L74
            goto L97
        L74:
            java.lang.RuntimeException r4 = r2.getUnbindLocation()     // Catch: java.lang.Throwable -> Ld5
            java.lang.IllegalArgumentException r5 = new java.lang.IllegalArgumentException     // Catch: java.lang.Throwable -> Ld5
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Ld5
            r6.<init>()     // Catch: java.lang.Throwable -> Ld5
            java.lang.String r7 = "Unbinding Service "
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch: java.lang.Throwable -> Ld5
            java.lang.StringBuilder r6 = r6.append(r10)     // Catch: java.lang.Throwable -> Ld5
            java.lang.String r7 = " that was already unbound"
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch: java.lang.Throwable -> Ld5
            java.lang.String r6 = r6.toString()     // Catch: java.lang.Throwable -> Ld5
            r5.<init>(r6, r4)     // Catch: java.lang.Throwable -> Ld5
            throw r5     // Catch: java.lang.Throwable -> Ld5
        L97:
            if (r9 != 0) goto Lbc
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException     // Catch: java.lang.Throwable -> Ld5
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Ld5
            r5.<init>()     // Catch: java.lang.Throwable -> Ld5
            java.lang.String r6 = "Unbinding Service "
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch: java.lang.Throwable -> Ld5
            java.lang.StringBuilder r5 = r5.append(r10)     // Catch: java.lang.Throwable -> Ld5
            java.lang.String r6 = " from Context that is no longer in use: "
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch: java.lang.Throwable -> Ld5
            java.lang.StringBuilder r5 = r5.append(r9)     // Catch: java.lang.Throwable -> Ld5
            java.lang.String r5 = r5.toString()     // Catch: java.lang.Throwable -> Ld5
            r4.<init>(r5)     // Catch: java.lang.Throwable -> Ld5
            throw r4     // Catch: java.lang.Throwable -> Ld5
        Lbc:
            java.lang.IllegalArgumentException r4 = new java.lang.IllegalArgumentException     // Catch: java.lang.Throwable -> Ld5
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Ld5
            r5.<init>()     // Catch: java.lang.Throwable -> Ld5
            java.lang.String r6 = "Service not registered: "
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch: java.lang.Throwable -> Ld5
            java.lang.StringBuilder r5 = r5.append(r10)     // Catch: java.lang.Throwable -> Ld5
            java.lang.String r5 = r5.toString()     // Catch: java.lang.Throwable -> Ld5
            r4.<init>(r5)     // Catch: java.lang.Throwable -> Ld5
            throw r4     // Catch: java.lang.Throwable -> Ld5
        Ld5:
            r1 = move-exception
            monitor-exit(r0)     // Catch: java.lang.Throwable -> Ld5
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.LoadedApk.forgetServiceDispatcher(android.content.Context, android.content.ServiceConnection):android.app.IServiceConnection");
    }

    static final class ServiceDispatcher {
        private final ArrayMap<ComponentName, ConnectionInfo> mActiveConnections;
        private final Executor mActivityExecutor;
        private final Handler mActivityThread;
        private final ServiceConnection mConnection;
        private final Context mContext;
        private final long mFlags;
        private boolean mForgotten;
        private final InnerConnection mIServiceConnection;
        private final ServiceConnectionLeaked mLocation;
        private RuntimeException mUnbindLocation;

        private static class ConnectionInfo {
            IBinder binder;
            IBinder.DeathRecipient deathMonitor;

            private ConnectionInfo() {
            }
        }

        private static class InnerConnection extends IServiceConnection.Stub {
            final WeakReference<ServiceDispatcher> mDispatcher;

            InnerConnection(ServiceDispatcher sd) {
                this.mDispatcher = new WeakReference<>(sd);
            }

            @Override // android.app.IServiceConnection
            public void connected(ComponentName name, IBinder service, boolean dead) throws RemoteException {
                ServiceDispatcher sd = this.mDispatcher.get();
                if (sd != null) {
                    sd.connected(name, service, dead);
                }
            }
        }

        ServiceDispatcher(ServiceConnection conn, Context context, Handler activityThread, long flags) {
            this.mActiveConnections = new ArrayMap<>();
            this.mIServiceConnection = new InnerConnection(this);
            this.mConnection = conn;
            this.mContext = context;
            this.mActivityThread = activityThread;
            this.mActivityExecutor = null;
            this.mLocation = new ServiceConnectionLeaked(null);
            this.mLocation.fillInStackTrace();
            this.mFlags = flags;
        }

        ServiceDispatcher(ServiceConnection conn, Context context, Executor activityExecutor, long flags) {
            this.mActiveConnections = new ArrayMap<>();
            this.mIServiceConnection = new InnerConnection(this);
            this.mConnection = conn;
            this.mContext = context;
            this.mActivityThread = null;
            this.mActivityExecutor = activityExecutor;
            this.mLocation = new ServiceConnectionLeaked(null);
            this.mLocation.fillInStackTrace();
            this.mFlags = flags;
        }

        void validate(Context context, Handler activityThread, Executor activityExecutor) {
            if (this.mContext != context) {
                throw new RuntimeException("ServiceConnection " + this.mConnection + " registered with differing Context (was " + this.mContext + " now " + context + NavigationBarInflaterView.KEY_CODE_END);
            }
            if (this.mActivityThread != activityThread) {
                throw new RuntimeException("ServiceConnection " + this.mConnection + " registered with differing handler (was " + this.mActivityThread + " now " + activityThread + NavigationBarInflaterView.KEY_CODE_END);
            }
            if (this.mActivityExecutor != activityExecutor) {
                throw new RuntimeException("ServiceConnection " + this.mConnection + " registered with differing executor (was " + this.mActivityExecutor + " now " + activityExecutor + NavigationBarInflaterView.KEY_CODE_END);
            }
        }

        void doForget() {
            synchronized (this) {
                for (int i = 0; i < this.mActiveConnections.size(); i++) {
                    ConnectionInfo ci = this.mActiveConnections.valueAt(i);
                    try {
                        ci.binder.unlinkToDeath(ci.deathMonitor, 0);
                    } catch (NoSuchElementException e) {
                        ComponentName name = this.mActiveConnections.keyAt(i);
                        Log.e(LoadedApk.TAG, "Error during unlinkToDeath, " + name.toString(), e);
                    }
                }
                this.mActiveConnections.clear();
                this.mForgotten = true;
            }
        }

        ServiceConnectionLeaked getLocation() {
            return this.mLocation;
        }

        ServiceConnection getServiceConnection() {
            return this.mConnection;
        }

        IServiceConnection getIServiceConnection() {
            return this.mIServiceConnection;
        }

        long getFlags() {
            return this.mFlags;
        }

        void setUnbindLocation(RuntimeException ex) {
            this.mUnbindLocation = ex;
        }

        RuntimeException getUnbindLocation() {
            return this.mUnbindLocation;
        }

        public void connected(ComponentName name, IBinder service, boolean dead) {
            if (this.mActivityExecutor != null) {
                this.mActivityExecutor.execute(new RunConnection(name, service, 0, dead));
            } else if (this.mActivityThread != null) {
                this.mActivityThread.post(new RunConnection(name, service, 0, dead));
            } else {
                doConnected(name, service, dead);
            }
        }

        public void death(ComponentName name, IBinder service) {
            if (this.mActivityExecutor != null) {
                this.mActivityExecutor.execute(new RunConnection(name, service, 1, false));
            } else if (this.mActivityThread != null) {
                this.mActivityThread.post(new RunConnection(name, service, 1, false));
            } else {
                doDeath(name, service);
            }
        }

        public void doConnected(ComponentName name, IBinder service, boolean dead) {
            synchronized (this) {
                if (this.mForgotten) {
                    return;
                }
                ConnectionInfo old = this.mActiveConnections.get(name);
                if (old == null || old.binder != service) {
                    if (service != null) {
                        ConnectionInfo info = new ConnectionInfo();
                        info.binder = service;
                        info.deathMonitor = new DeathMonitor(name, service);
                        try {
                            service.linkToDeath(info.deathMonitor, 0);
                            this.mActiveConnections.put(name, info);
                        } catch (RemoteException e) {
                            this.mActiveConnections.remove(name);
                            return;
                        }
                    } else {
                        this.mActiveConnections.remove(name);
                    }
                    if (old != null) {
                        try {
                            old.binder.unlinkToDeath(old.deathMonitor, 0);
                        } catch (NoSuchElementException e2) {
                            Log.e(LoadedApk.TAG, "Error during unlinkToDeath, " + name.toString(), e2);
                        }
                    }
                    if (old != null) {
                        this.mConnection.onServiceDisconnected(name);
                    }
                    if (dead) {
                        this.mConnection.onBindingDied(name);
                    } else if (service != null) {
                        this.mConnection.onServiceConnected(name, service);
                    } else {
                        this.mConnection.onNullBinding(name);
                    }
                }
            }
        }

        public void doDeath(ComponentName name, IBinder service) {
            synchronized (this) {
                ConnectionInfo old = this.mActiveConnections.get(name);
                if (old != null && old.binder == service) {
                    this.mActiveConnections.remove(name);
                    try {
                        old.binder.unlinkToDeath(old.deathMonitor, 0);
                    } catch (NoSuchElementException e) {
                        Log.e(LoadedApk.TAG, "Error during unlinkToDeath, " + name.toString(), e);
                    }
                    this.mConnection.onServiceDisconnected(name);
                }
            }
        }

        private final class RunConnection implements Runnable {
            final int mCommand;
            final boolean mDead;
            final ComponentName mName;
            final IBinder mService;

            RunConnection(ComponentName name, IBinder service, int command, boolean dead) {
                this.mName = name;
                this.mService = service;
                this.mCommand = command;
                this.mDead = dead;
            }

            @Override // java.lang.Runnable
            public void run() {
                if (this.mCommand == 0) {
                    ServiceDispatcher.this.doConnected(this.mName, this.mService, this.mDead);
                } else if (this.mCommand == 1) {
                    ServiceDispatcher.this.doDeath(this.mName, this.mService);
                }
            }
        }

        private final class DeathMonitor implements IBinder.DeathRecipient {
            final ComponentName mName;
            final IBinder mService;

            DeathMonitor(ComponentName name, IBinder service) {
                this.mName = name;
                this.mService = service;
            }

            @Override // android.os.IBinder.DeathRecipient
            public void binderDied() {
                ServiceDispatcher.this.death(this.mName, this.mService);
            }
        }
    }

    public static void checkAndUpdateApkPaths(ApplicationInfo expectedAppInfo) {
        ActivityThread activityThread = ActivityThread.currentActivityThread();
        if (activityThread == null) {
            Log.e(TAG, "Cannot find activity thread");
        } else {
            checkAndUpdateApkPaths(activityThread, expectedAppInfo, true);
            checkAndUpdateApkPaths(activityThread, expectedAppInfo, false);
        }
    }

    private static void checkAndUpdateApkPaths(ActivityThread activityThread, ApplicationInfo expectedAppInfo, boolean cacheWithCode) {
        String expectedCodePath = expectedAppInfo.getCodePath();
        LoadedApk loadedApk = activityThread.peekPackageInfo(expectedAppInfo.packageName, cacheWithCode);
        if (loadedApk == null || loadedApk.getApplicationInfo() == null || loadedApk.getApplicationInfo().getCodePath().equals(expectedCodePath)) {
            return;
        }
        List<String> oldPaths = new ArrayList<>();
        makePaths(activityThread, expectedAppInfo, oldPaths);
        loadedApk.updateApplicationInfo(expectedAppInfo, oldPaths);
    }
}
