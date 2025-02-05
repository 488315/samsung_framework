package com.android.server;

import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Slog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class LockGuard {
    public static final Object[] sKnownFixed = new Object[10];
    public static final ArrayMap sKnown = new ArrayMap(0, true);

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class LockInfo {
        public ArraySet children;
        public boolean doWtf;
        public String label;
    }

    public static LockInfo findOrCreateLockInfo(Object obj) {
        ArrayMap arrayMap = sKnown;
        LockInfo lockInfo = (LockInfo) arrayMap.get(obj);
        if (lockInfo != null) {
            return lockInfo;
        }
        LockInfo lockInfo2 = new LockInfo();
        lockInfo2.children = new ArraySet(0, true);
        lockInfo2.label = "0x" + Integer.toHexString(System.identityHashCode(obj)) + " [" + new Throwable().getStackTrace()[2].toString() + "]";
        arrayMap.put(obj, lockInfo2);
        return lockInfo2;
    }

    public static void guard(int i) {
        for (int i2 = 0; i2 < i; i2++) {
            Object[] objArr = sKnownFixed;
            Object obj = objArr[i2];
            if (obj != null && Thread.holdsLock(obj)) {
                Object obj2 = objArr[i];
                String str = "Calling thread " + Thread.currentThread().getName() + " is holding " + lockToString(i2) + " while trying to acquire " + lockToString(i);
                if (obj2 == null || !findOrCreateLockInfo(obj2).doWtf) {
                    Slog.w("LockGuard", str, new Throwable());
                } else {
                    final RuntimeException runtimeException = new RuntimeException(str);
                    new Thread(new Runnable() { // from class: com.android.server.LockGuard$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            Slog.wtf("LockGuard", runtimeException);
                        }
                    }).start();
                }
            }
        }
    }

    public static void installLock(Object obj, int i, boolean z) {
        sKnownFixed[i] = obj;
        LockInfo findOrCreateLockInfo = findOrCreateLockInfo(obj);
        findOrCreateLockInfo.doWtf = z;
        findOrCreateLockInfo.label = "Lock-" + lockToString(i);
    }

    public static Object installNewLock(int i, boolean z) {
        Object obj = new Object();
        installLock(obj, i, z);
        return obj;
    }

    public static String lockToString(int i) {
        switch (i) {
            case 0:
                return "APP_OPS";
            case 1:
                return "POWER";
            case 2:
                return "USER";
            case 3:
                return "PACKAGES";
            case 4:
                return "STORAGE";
            case 5:
                return "WINDOW";
            case 6:
                return "PROCESS";
            case 7:
                return "ACTIVITY";
            case 8:
                return "DPMS";
            case 9:
                return "EDMS";
            default:
                return Integer.toString(i);
        }
    }

    public static String lockToString(Object obj) {
        LockInfo lockInfo = (LockInfo) sKnown.get(obj);
        if (lockInfo != null && !TextUtils.isEmpty(lockInfo.label)) {
            return lockInfo.label;
        }
        return "0x" + Integer.toHexString(System.identityHashCode(obj));
    }
}
