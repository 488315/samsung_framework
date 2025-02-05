package com.android.internal.content;

import android.content.ContentResolver;
import android.os.Environment;
import android.os.incremental.IncrementalManager;
import android.provider.Settings;
import android.text.TextUtils;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public final class F2fsUtils {
    private static final String COMPRESSION_FEATURE = "compression";
    private static final boolean DEBUG_F2FS = false;
    private static final String HEIMDALLFS_FEATURE = "sec_heimdallfs";
    private static final String RELOCATION_FEATURE = "sec_dnode_relocation";
    private static final String TAG = "F2fsUtils";
    private static boolean sKernelCompressionAvailable;
    private static final boolean sUserDataCompressionAvailable;
    private static final File sKernelFeatures = new File("/sys/fs/f2fs/features");
    private static final File sUserDataFeatures = new File("/dev/sys/fs/by-name/userdata/features");
    private static final File sDataDirectory = Environment.getDataDirectory();

    private static native long nativeReleaseCompressedBlocks(String str);

    static {
        sKernelCompressionAvailable = isFeatureEnabledInKernel(COMPRESSION_FEATURE);
        if (isFeatureEnabledInKernel(RELOCATION_FEATURE) || isFeatureEnabledInKernel(HEIMDALLFS_FEATURE)) {
            sKernelCompressionAvailable = false;
        }
        sUserDataCompressionAvailable = isCompressionEnabledOnUserData();
    }

    public static void releaseCompressedBlocks(ContentResolver resolver, File file) {
        File[] files;
        if (!sKernelCompressionAvailable || !sUserDataCompressionAvailable) {
            return;
        }
        boolean releaseCompressBlocks = Settings.Secure.getInt(resolver, Settings.Secure.RELEASE_COMPRESS_BLOCKS_ON_INSTALL, 1) != 0;
        if (!releaseCompressBlocks || !isCompressionAllowed(file) || (files = getFilesToRelease(file)) == null || files.length == 0) {
            return;
        }
        for (int i = files.length - 1; i >= 0; i--) {
            nativeReleaseCompressedBlocks(files[i].getAbsolutePath());
        }
    }

    private static boolean isCompressionAllowed(File file) {
        try {
            String filePath = file.getCanonicalPath();
            if (IncrementalManager.isIncrementalPath(filePath) || !isChild(sDataDirectory, filePath)) {
                return false;
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private static boolean isChild(File base, String childPath) {
        try {
            File base2 = base.getCanonicalFile();
            for (File parentFile = new File(childPath).getCanonicalFile(); parentFile != null; parentFile = parentFile.getParentFile()) {
                if (base2.equals(parentFile)) {
                    return true;
                }
            }
            return false;
        } catch (IOException e) {
            return false;
        }
    }

    private static boolean isFeatureEnabledInKernel(String targetFeature) {
        File[] features = sKernelFeatures.listFiles();
        if (features == null || features.length == 0) {
            return false;
        }
        for (int i = features.length - 1; i >= 0; i--) {
            File file = features[i];
            if (targetFeature.equals(features[i].getName())) {
                return true;
            }
        }
        return false;
    }

    private static boolean isCompressionEnabledOnUserData() {
        if (!sUserDataFeatures.exists() || !sUserDataFeatures.isFile() || !sUserDataFeatures.canRead()) {
            return false;
        }
        try {
            List<String> configLines = Files.readAllLines(sUserDataFeatures.toPath());
            if (configLines == null || configLines.size() > 1 || TextUtils.isEmpty(configLines.get(0))) {
                return false;
            }
            String[] features = configLines.get(0).split(",");
            for (int i = features.length - 1; i >= 0; i--) {
                if (COMPRESSION_FEATURE.equals(features[i].trim())) {
                    return true;
                }
            }
            return false;
        } catch (IOException e) {
            return false;
        }
    }

    private static List<File> getFilesRecursive(File path) {
        File[] allFiles = path.listFiles();
        if (allFiles == null) {
            return null;
        }
        ArrayList<File> files = new ArrayList<>();
        for (File f : allFiles) {
            if (f.isDirectory()) {
                files.addAll(getFilesRecursive(f));
            } else if (f.isFile()) {
                files.add(f);
            }
        }
        return files;
    }

    private static File[] getFilesToRelease(File codePath) {
        List<File> files = getFilesRecursive(codePath);
        if (files == null) {
            if (!codePath.isFile()) {
                return null;
            }
            return new File[]{codePath};
        }
        if (files.size() == 0) {
            return null;
        }
        return (File[]) files.toArray(new File[files.size()]);
    }
}
