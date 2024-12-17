package com.samsung.android.server.hwrs.utils;

import android.util.Log;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class FileUtil {
    public static void deleteFile(String str) {
        Log.d("[HWRS_SYS]FileUtil", "deleteFile: ".concat(str));
        try {
            Files.deleteIfExists(Paths.get(str, new String[0]));
        } catch (IOException unused) {
            throw new StorageServiceException("deleteFile failed");
        }
    }
}
