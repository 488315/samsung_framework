package com.android.server.appop;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.PackageManagerInternal;
import android.content.pm.UserPackage;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Handler;
import android.os.RemoteException;
import android.os.UserHandle;
import android.permission.PermissionManager;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.AtomicFile;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.util.Xml;
import com.android.internal.util.XmlUtils;
import com.android.internal.util.function.QuintConsumer;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.LocalServices;
import com.android.server.pm.UserManagerInternal;
import com.android.server.pm.permission.PermissionManagerServiceInternal;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class AppOpsCheckingServiceImpl implements AppOpsCheckingServiceInterface {
    static final int CURRENT_VERSION = 4;
    public final Context mContext;
    public boolean mFastWriteScheduled;
    public final AtomicFile mFile;
    public final Handler mHandler;
    public final Object mLock;
    public final SparseArray mSwitchedOps;
    public boolean mWriteScheduled;
    public int mVersionAtBoot = -2;
    final SparseArray mUidModes = new SparseArray();
    public final SparseArray mUserPackageModes = new SparseArray();
    public final SparseArray mOpModeWatchers = new SparseArray();
    public final ArrayMap mPackageModeWatchers = new ArrayMap();
    public final Runnable mWriteRunner = new Runnable() { // from class: com.android.server.appop.AppOpsCheckingServiceImpl.1
        @Override // java.lang.Runnable
        public void run() {
            synchronized (AppOpsCheckingServiceImpl.this.mLock) {
                AppOpsCheckingServiceImpl appOpsCheckingServiceImpl = AppOpsCheckingServiceImpl.this;
                appOpsCheckingServiceImpl.mWriteScheduled = false;
                appOpsCheckingServiceImpl.mFastWriteScheduled = false;
                new AsyncTask() { // from class: com.android.server.appop.AppOpsCheckingServiceImpl.1.1
                    @Override // android.os.AsyncTask
                    public Void doInBackground(Void... voidArr) {
                        AppOpsCheckingServiceImpl.this.writeState();
                        return null;
                    }
                }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, null);
            }
        }
    };

    public AppOpsCheckingServiceImpl(File file, Object obj, Handler handler, Context context, SparseArray sparseArray) {
        this.mFile = new AtomicFile(file);
        this.mLock = obj;
        this.mHandler = handler;
        this.mContext = context;
        this.mSwitchedOps = sparseArray;
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public void systemReady() {
        synchronized (this.mLock) {
            upgradeLocked(this.mVersionAtBoot);
        }
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public SparseIntArray getNonDefaultUidModes(int i) {
        synchronized (this.mLock) {
            SparseIntArray sparseIntArray = (SparseIntArray) this.mUidModes.get(i, null);
            if (sparseIntArray == null) {
                return new SparseIntArray();
            }
            return sparseIntArray.clone();
        }
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public SparseIntArray getNonDefaultPackageModes(String str, int i) {
        synchronized (this.mLock) {
            ArrayMap arrayMap = (ArrayMap) this.mUserPackageModes.get(i);
            if (arrayMap == null) {
                return new SparseIntArray();
            }
            SparseIntArray sparseIntArray = (SparseIntArray) arrayMap.get(str);
            if (sparseIntArray == null) {
                return new SparseIntArray();
            }
            return sparseIntArray.clone();
        }
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public int getUidMode(int i, int i2) {
        synchronized (this.mLock) {
            SparseIntArray sparseIntArray = (SparseIntArray) this.mUidModes.get(i, null);
            if (sparseIntArray == null) {
                return AppOpsManager.opToDefaultMode(i2);
            }
            return sparseIntArray.get(i2, AppOpsManager.opToDefaultMode(i2));
        }
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public boolean setUidMode(int i, int i2, int i3) {
        int opToDefaultMode = AppOpsManager.opToDefaultMode(i2);
        synchronized (this.mLock) {
            SparseIntArray sparseIntArray = (SparseIntArray) this.mUidModes.get(i, null);
            if (sparseIntArray == null) {
                if (i3 != opToDefaultMode) {
                    SparseIntArray sparseIntArray2 = new SparseIntArray();
                    this.mUidModes.put(i, sparseIntArray2);
                    sparseIntArray2.put(i2, i3);
                    scheduleFastWriteLocked();
                }
            } else {
                if (sparseIntArray.indexOfKey(i2) >= 0 && sparseIntArray.get(i2) == i3) {
                    return false;
                }
                if (i3 == opToDefaultMode) {
                    sparseIntArray.delete(i2);
                    if (sparseIntArray.size() <= 0) {
                        this.mUidModes.delete(i);
                    }
                } else {
                    sparseIntArray.put(i2, i3);
                }
                scheduleFastWriteLocked();
            }
            return true;
        }
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public int getPackageMode(String str, int i, int i2) {
        synchronized (this.mLock) {
            ArrayMap arrayMap = (ArrayMap) this.mUserPackageModes.get(i2, null);
            if (arrayMap == null) {
                return AppOpsManager.opToDefaultMode(i);
            }
            SparseIntArray sparseIntArray = (SparseIntArray) arrayMap.getOrDefault(str, null);
            if (sparseIntArray == null) {
                return AppOpsManager.opToDefaultMode(i);
            }
            return sparseIntArray.get(i, AppOpsManager.opToDefaultMode(i));
        }
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public void setPackageMode(String str, int i, int i2, int i3) {
        int opToDefaultMode = AppOpsManager.opToDefaultMode(i);
        synchronized (this.mLock) {
            ArrayMap arrayMap = (ArrayMap) this.mUserPackageModes.get(i3, null);
            if (arrayMap == null) {
                arrayMap = new ArrayMap();
                this.mUserPackageModes.put(i3, arrayMap);
            }
            SparseIntArray sparseIntArray = (SparseIntArray) arrayMap.get(str);
            if (sparseIntArray == null) {
                if (i2 != opToDefaultMode) {
                    SparseIntArray sparseIntArray2 = new SparseIntArray();
                    arrayMap.put(str, sparseIntArray2);
                    sparseIntArray2.put(i, i2);
                    scheduleFastWriteLocked();
                }
            } else {
                if (sparseIntArray.indexOfKey(i) >= 0 && sparseIntArray.get(i) == i2) {
                    return;
                }
                if (i2 == opToDefaultMode) {
                    sparseIntArray.delete(i);
                    if (sparseIntArray.size() <= 0) {
                        arrayMap.remove(str);
                    }
                } else {
                    sparseIntArray.put(i, i2);
                }
                scheduleFastWriteLocked();
            }
        }
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public void removeUid(int i) {
        synchronized (this.mLock) {
            if (((SparseIntArray) this.mUidModes.get(i)) == null) {
                return;
            }
            this.mUidModes.remove(i);
            scheduleFastWriteLocked();
        }
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public boolean removePackage(String str, int i) {
        synchronized (this.mLock) {
            ArrayMap arrayMap = (ArrayMap) this.mUserPackageModes.get(i, null);
            if (arrayMap == null) {
                return false;
            }
            if (((SparseIntArray) arrayMap.remove(str)) == null) {
                return false;
            }
            scheduleFastWriteLocked();
            return true;
        }
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public void clearAllModes() {
        synchronized (this.mLock) {
            this.mUidModes.clear();
            this.mUserPackageModes.clear();
        }
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public void startWatchingOpModeChanged(OnOpModeChangedListener onOpModeChangedListener, int i) {
        Objects.requireNonNull(onOpModeChangedListener);
        synchronized (this.mLock) {
            ArraySet arraySet = (ArraySet) this.mOpModeWatchers.get(i);
            if (arraySet == null) {
                arraySet = new ArraySet();
                this.mOpModeWatchers.put(i, arraySet);
            }
            arraySet.add(onOpModeChangedListener);
        }
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public void startWatchingPackageModeChanged(OnOpModeChangedListener onOpModeChangedListener, String str) {
        Objects.requireNonNull(onOpModeChangedListener);
        Objects.requireNonNull(str);
        synchronized (this.mLock) {
            ArraySet arraySet = (ArraySet) this.mPackageModeWatchers.get(str);
            if (arraySet == null) {
                arraySet = new ArraySet();
                this.mPackageModeWatchers.put(str, arraySet);
            }
            arraySet.add(onOpModeChangedListener);
        }
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public void removeListener(OnOpModeChangedListener onOpModeChangedListener) {
        Objects.requireNonNull(onOpModeChangedListener);
        synchronized (this.mLock) {
            for (int size = this.mOpModeWatchers.size() - 1; size >= 0; size--) {
                ArraySet arraySet = (ArraySet) this.mOpModeWatchers.valueAt(size);
                arraySet.remove(onOpModeChangedListener);
                if (arraySet.size() <= 0) {
                    this.mOpModeWatchers.removeAt(size);
                }
            }
            for (int size2 = this.mPackageModeWatchers.size() - 1; size2 >= 0; size2--) {
                ArraySet arraySet2 = (ArraySet) this.mPackageModeWatchers.valueAt(size2);
                arraySet2.remove(onOpModeChangedListener);
                if (arraySet2.size() <= 0) {
                    this.mPackageModeWatchers.removeAt(size2);
                }
            }
        }
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public ArraySet getOpModeChangedListeners(int i) {
        synchronized (this.mLock) {
            ArraySet arraySet = (ArraySet) this.mOpModeWatchers.get(i);
            if (arraySet == null) {
                return new ArraySet();
            }
            return new ArraySet(arraySet);
        }
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public ArraySet getPackageModeChangedListeners(String str) {
        Objects.requireNonNull(str);
        synchronized (this.mLock) {
            ArraySet arraySet = (ArraySet) this.mPackageModeWatchers.get(str);
            if (arraySet == null) {
                return new ArraySet();
            }
            return new ArraySet(arraySet);
        }
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public void notifyWatchersOfChange(int i, int i2) {
        ArraySet opModeChangedListeners = getOpModeChangedListeners(i);
        if (opModeChangedListeners == null) {
            return;
        }
        for (int i3 = 0; i3 < opModeChangedListeners.size(); i3++) {
            notifyOpChanged((OnOpModeChangedListener) opModeChangedListeners.valueAt(i3), i, i2, null);
        }
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public void notifyOpChanged(OnOpModeChangedListener onOpModeChangedListener, int i, int i2, String str) {
        int[] iArr;
        Objects.requireNonNull(onOpModeChangedListener);
        if (i2 == -2 || onOpModeChangedListener.getWatchingUid() < 0 || onOpModeChangedListener.getWatchingUid() == i2) {
            if (onOpModeChangedListener.getWatchedOpCode() == -2) {
                iArr = (int[]) this.mSwitchedOps.get(i);
            } else if (onOpModeChangedListener.getWatchedOpCode() == -1) {
                iArr = new int[]{i};
            } else {
                iArr = new int[]{onOpModeChangedListener.getWatchedOpCode()};
            }
            for (int i3 : iArr) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    if (!shouldIgnoreCallback(i3, onOpModeChangedListener.getCallingPid(), onOpModeChangedListener.getCallingUid())) {
                        onOpModeChangedListener.onOpModeChanged(i3, i2, str);
                    }
                } catch (RemoteException unused) {
                } catch (Throwable th) {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public final boolean shouldIgnoreCallback(int i, int i2, int i3) {
        return AppOpsManager.opRestrictsRead(i) && this.mContext.checkPermission("android.permission.MANAGE_APPOPS", i2, i3) != 0;
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public void notifyOpChangedForAllPkgsInUid(int i, int i2, boolean z, OnOpModeChangedListener onOpModeChangedListener) {
        ArrayMap arrayMap;
        String[] packagesForUid = getPackagesForUid(i2);
        synchronized (this.mLock) {
            ArraySet arraySet = (ArraySet) this.mOpModeWatchers.get(i);
            ArrayMap arrayMap2 = null;
            if (arraySet != null) {
                int size = arraySet.size();
                for (int i3 = 0; i3 < size; i3++) {
                    OnOpModeChangedListener onOpModeChangedListener2 = (OnOpModeChangedListener) arraySet.valueAt(i3);
                    if (!z || (onOpModeChangedListener2.getFlags() & 1) != 0) {
                        ArraySet arraySet2 = new ArraySet();
                        Collections.addAll(arraySet2, packagesForUid);
                        if (arrayMap2 == null) {
                            arrayMap2 = new ArrayMap();
                        }
                        arrayMap2.put(onOpModeChangedListener2, arraySet2);
                    }
                }
            }
            arrayMap = arrayMap2;
            for (String str : packagesForUid) {
                ArraySet arraySet3 = (ArraySet) this.mPackageModeWatchers.get(str);
                if (arraySet3 != null) {
                    if (arrayMap == null) {
                        arrayMap = new ArrayMap();
                    }
                    int size2 = arraySet3.size();
                    for (int i4 = 0; i4 < size2; i4++) {
                        OnOpModeChangedListener onOpModeChangedListener3 = (OnOpModeChangedListener) arraySet3.valueAt(i4);
                        if (!z || (onOpModeChangedListener3.getFlags() & 1) != 0) {
                            ArraySet arraySet4 = (ArraySet) arrayMap.get(onOpModeChangedListener3);
                            if (arraySet4 == null) {
                                arraySet4 = new ArraySet();
                                arrayMap.put(onOpModeChangedListener3, arraySet4);
                            }
                            arraySet4.add(str);
                        }
                    }
                }
            }
            if (arrayMap != null && onOpModeChangedListener != null) {
                arrayMap.remove(onOpModeChangedListener);
            }
        }
        if (arrayMap == null) {
            return;
        }
        for (int i5 = 0; i5 < arrayMap.size(); i5++) {
            OnOpModeChangedListener onOpModeChangedListener4 = (OnOpModeChangedListener) arrayMap.keyAt(i5);
            ArraySet arraySet5 = (ArraySet) arrayMap.valueAt(i5);
            if (arraySet5 == null) {
                this.mHandler.sendMessage(PooledLambda.obtainMessage(new QuintConsumer() { // from class: com.android.server.appop.AppOpsCheckingServiceImpl$$ExternalSyntheticLambda0
                    public final void accept(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
                        ((AppOpsCheckingServiceImpl) obj).notifyOpChanged((OnOpModeChangedListener) obj2, ((Integer) obj3).intValue(), ((Integer) obj4).intValue(), (String) obj5);
                    }
                }, this, onOpModeChangedListener4, Integer.valueOf(i), Integer.valueOf(i2), (Object) null));
            } else {
                int size3 = arraySet5.size();
                for (int i6 = 0; i6 < size3; i6++) {
                    this.mHandler.sendMessage(PooledLambda.obtainMessage(new QuintConsumer() { // from class: com.android.server.appop.AppOpsCheckingServiceImpl$$ExternalSyntheticLambda0
                        public final void accept(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
                            ((AppOpsCheckingServiceImpl) obj).notifyOpChanged((OnOpModeChangedListener) obj2, ((Integer) obj3).intValue(), ((Integer) obj4).intValue(), (String) obj5);
                        }
                    }, this, onOpModeChangedListener4, Integer.valueOf(i), Integer.valueOf(i2), (String) arraySet5.valueAt(i6)));
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:5:0x0012  */
    /* JADX WARN: Removed duplicated region for block: B:8:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String[] getPackagesForUid(int r1) {
        /*
            android.content.pm.IPackageManager r0 = android.app.AppGlobals.getPackageManager()
            if (r0 == 0) goto Lf
            android.content.pm.IPackageManager r0 = android.app.AppGlobals.getPackageManager()     // Catch: android.os.RemoteException -> Lf
            java.lang.String[] r1 = r0.getPackagesForUid(r1)     // Catch: android.os.RemoteException -> Lf
            goto L10
        Lf:
            r1 = 0
        L10:
            if (r1 != 0) goto L14
            java.lang.String[] r1 = libcore.util.EmptyArray.STRING
        L14:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.appop.AppOpsCheckingServiceImpl.getPackagesForUid(int):java.lang.String[]");
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public SparseBooleanArray evalForegroundUidOps(int i, SparseBooleanArray sparseBooleanArray) {
        SparseBooleanArray evalForegroundOps;
        synchronized (this.mLock) {
            evalForegroundOps = evalForegroundOps((SparseIntArray) this.mUidModes.get(i), sparseBooleanArray);
        }
        return evalForegroundOps;
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public SparseBooleanArray evalForegroundPackageOps(String str, SparseBooleanArray sparseBooleanArray, int i) {
        SparseBooleanArray evalForegroundOps;
        synchronized (this.mLock) {
            SparseIntArray sparseIntArray = null;
            ArrayMap arrayMap = (ArrayMap) this.mUserPackageModes.get(i, null);
            if (arrayMap != null) {
                sparseIntArray = (SparseIntArray) arrayMap.get(str);
            }
            evalForegroundOps = evalForegroundOps(sparseIntArray, sparseBooleanArray);
        }
        return evalForegroundOps;
    }

    public final SparseBooleanArray evalForegroundOps(SparseIntArray sparseIntArray, SparseBooleanArray sparseBooleanArray) {
        if (sparseIntArray != null) {
            for (int size = sparseIntArray.size() - 1; size >= 0; size--) {
                if (sparseIntArray.valueAt(size) == 4) {
                    if (sparseBooleanArray == null) {
                        sparseBooleanArray = new SparseBooleanArray();
                    }
                    evalForegroundWatchers(sparseIntArray.keyAt(size), sparseBooleanArray);
                }
            }
        }
        return sparseBooleanArray;
    }

    public final void evalForegroundWatchers(int i, SparseBooleanArray sparseBooleanArray) {
        boolean z = sparseBooleanArray.get(i, false);
        ArraySet arraySet = (ArraySet) this.mOpModeWatchers.get(i);
        if (arraySet != null) {
            for (int size = arraySet.size() - 1; !z && size >= 0; size--) {
                if ((((OnOpModeChangedListener) arraySet.valueAt(size)).getFlags() & 1) != 0) {
                    z = true;
                }
            }
        }
        sparseBooleanArray.put(i, z);
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x0055, code lost:
    
        r21.println("  Op mode watchers:");
        r11 = true;
     */
    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean dumpListeners(int r18, int r19, java.lang.String r20, java.io.PrintWriter r21) {
        /*
            Method dump skipped, instructions count: 248
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.appop.AppOpsCheckingServiceImpl.dumpListeners(int, int, java.lang.String, java.io.PrintWriter):boolean");
    }

    public final void scheduleFastWriteLocked() {
        if (this.mFastWriteScheduled) {
            return;
        }
        this.mWriteScheduled = true;
        this.mFastWriteScheduled = true;
        this.mHandler.removeCallbacks(this.mWriteRunner);
        this.mHandler.postDelayed(this.mWriteRunner, 10000L);
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public void writeState() {
        int size;
        int size2;
        synchronized (this.mFile) {
            try {
                try {
                    FileOutputStream startWrite = this.mFile.startWrite();
                    try {
                        TypedXmlSerializer resolveSerializer = Xml.resolveSerializer(startWrite);
                        resolveSerializer.startDocument((String) null, Boolean.TRUE);
                        resolveSerializer.startTag((String) null, "app-ops");
                        resolveSerializer.attributeInt((String) null, "v", 4);
                        SparseArray sparseArray = new SparseArray();
                        SparseArray sparseArray2 = new SparseArray();
                        synchronized (this.mLock) {
                            size = this.mUidModes.size();
                            for (int i = 0; i < size; i++) {
                                sparseArray.put(this.mUidModes.keyAt(i), ((SparseIntArray) this.mUidModes.valueAt(i)).clone());
                            }
                            size2 = this.mUserPackageModes.size();
                            for (int i2 = 0; i2 < size2; i2++) {
                                int keyAt = this.mUserPackageModes.keyAt(i2);
                                ArrayMap arrayMap = (ArrayMap) this.mUserPackageModes.valueAt(i2);
                                ArrayMap arrayMap2 = new ArrayMap();
                                sparseArray2.put(keyAt, arrayMap2);
                                int size3 = arrayMap.size();
                                for (int i3 = 0; i3 < size3; i3++) {
                                    arrayMap2.put((String) arrayMap.keyAt(i3), ((SparseIntArray) arrayMap.valueAt(i3)).clone());
                                }
                            }
                        }
                        for (int i4 = 0; i4 < size; i4++) {
                            int keyAt2 = sparseArray.keyAt(i4);
                            SparseIntArray sparseIntArray = (SparseIntArray) sparseArray.valueAt(i4);
                            resolveSerializer.startTag((String) null, "uid");
                            resolveSerializer.attributeInt((String) null, "n", keyAt2);
                            int size4 = sparseIntArray.size();
                            for (int i5 = 0; i5 < size4; i5++) {
                                int keyAt3 = sparseIntArray.keyAt(i5);
                                int valueAt = sparseIntArray.valueAt(i5);
                                resolveSerializer.startTag((String) null, "op");
                                resolveSerializer.attributeInt((String) null, "n", keyAt3);
                                resolveSerializer.attributeInt((String) null, "m", valueAt);
                                resolveSerializer.endTag((String) null, "op");
                            }
                            resolveSerializer.endTag((String) null, "uid");
                        }
                        for (int i6 = 0; i6 < size2; i6++) {
                            int keyAt4 = sparseArray2.keyAt(i6);
                            ArrayMap arrayMap3 = (ArrayMap) sparseArray2.valueAt(i6);
                            resolveSerializer.startTag((String) null, "user");
                            resolveSerializer.attributeInt((String) null, "n", keyAt4);
                            int size5 = arrayMap3.size();
                            int i7 = 0;
                            while (i7 < size5) {
                                String str = (String) arrayMap3.keyAt(i7);
                                SparseIntArray sparseIntArray2 = (SparseIntArray) arrayMap3.valueAt(i7);
                                resolveSerializer.startTag((String) null, "pkg");
                                resolveSerializer.attribute((String) null, "n", str);
                                int size6 = sparseIntArray2.size();
                                int i8 = 0;
                                while (i8 < size6) {
                                    int keyAt5 = sparseIntArray2.keyAt(i8);
                                    int valueAt2 = sparseIntArray2.valueAt(i8);
                                    resolveSerializer.startTag((String) null, "op");
                                    resolveSerializer.attributeInt((String) null, "n", keyAt5);
                                    resolveSerializer.attributeInt((String) null, "m", valueAt2);
                                    resolveSerializer.endTag((String) null, "op");
                                    i8++;
                                    size5 = size5;
                                }
                                resolveSerializer.endTag((String) null, "pkg");
                                i7++;
                                size5 = size5;
                            }
                            resolveSerializer.endTag((String) null, "user");
                        }
                        resolveSerializer.endTag((String) null, "app-ops");
                        resolveSerializer.endDocument();
                        this.mFile.finishWrite(startWrite);
                    } catch (IOException e) {
                        Slog.w("LegacyAppOpsServiceInterfaceImpl", "Failed to write state, restoring backup.", e);
                        this.mFile.failWrite(startWrite);
                    }
                } catch (IOException e2) {
                    Slog.w("LegacyAppOpsServiceInterfaceImpl", "Failed to write state: " + e2);
                }
            } finally {
            }
        }
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public void readState() {
        int next;
        synchronized (this.mFile) {
            synchronized (this.mLock) {
                try {
                    try {
                        try {
                            try {
                                TypedXmlPullParser resolvePullParser = Xml.resolvePullParser(this.mFile.openRead());
                                do {
                                    next = resolvePullParser.next();
                                    if (next == 2) {
                                        break;
                                    }
                                } while (next != 1);
                                if (next != 2) {
                                    throw new IllegalStateException("no start tag found");
                                }
                                this.mVersionAtBoot = resolvePullParser.getAttributeInt((String) null, "v", -1);
                                int depth = resolvePullParser.getDepth();
                                while (true) {
                                    int next2 = resolvePullParser.next();
                                    if (next2 == 1 || (next2 == 3 && resolvePullParser.getDepth() <= depth)) {
                                        break;
                                    }
                                    if (next2 != 3 && next2 != 4) {
                                        String name = resolvePullParser.getName();
                                        if (name.equals("pkg")) {
                                            readPackage(resolvePullParser);
                                        } else if (name.equals("uid")) {
                                            readUidOps(resolvePullParser);
                                        } else if (name.equals("user")) {
                                            readUser(resolvePullParser);
                                        } else {
                                            Slog.w("LegacyAppOpsServiceInterfaceImpl", "Unknown element under <app-ops>: " + resolvePullParser.getName());
                                            XmlUtils.skipCurrentTag(resolvePullParser);
                                        }
                                    }
                                }
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        } catch (XmlPullParserException e2) {
                            throw new RuntimeException(e2);
                        }
                    } catch (FileNotFoundException unused) {
                        Slog.i("LegacyAppOpsServiceInterfaceImpl", "No existing app ops " + this.mFile.getBaseFile() + "; starting empty");
                        this.mVersionAtBoot = -2;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    @Override // com.android.server.appop.AppOpsCheckingServiceInterface
    public void shutdown() {
        boolean z;
        synchronized (this) {
            z = false;
            if (this.mWriteScheduled) {
                this.mWriteScheduled = false;
                this.mFastWriteScheduled = false;
                this.mHandler.removeCallbacks(this.mWriteRunner);
                z = true;
            }
        }
        if (z) {
            writeState();
        }
    }

    public final void readUidOps(TypedXmlPullParser typedXmlPullParser) {
        int attributeInt = typedXmlPullParser.getAttributeInt((String) null, "n");
        SparseIntArray sparseIntArray = (SparseIntArray) this.mUidModes.get(attributeInt);
        if (sparseIntArray == null) {
            sparseIntArray = new SparseIntArray();
            this.mUidModes.put(attributeInt, sparseIntArray);
        }
        int depth = typedXmlPullParser.getDepth();
        while (true) {
            int next = typedXmlPullParser.next();
            if (next == 1) {
                return;
            }
            if (next == 3 && typedXmlPullParser.getDepth() <= depth) {
                return;
            }
            if (next != 3 && next != 4) {
                if (typedXmlPullParser.getName().equals("op")) {
                    int attributeInt2 = typedXmlPullParser.getAttributeInt((String) null, "n");
                    int attributeInt3 = typedXmlPullParser.getAttributeInt((String) null, "m");
                    if (attributeInt3 != AppOpsManager.opToDefaultMode(attributeInt2)) {
                        sparseIntArray.put(attributeInt2, attributeInt3);
                    }
                } else {
                    Slog.w("LegacyAppOpsServiceInterfaceImpl", "Unknown element under <uid>: " + typedXmlPullParser.getName());
                    XmlUtils.skipCurrentTag(typedXmlPullParser);
                }
            }
        }
    }

    public final void readPackage(TypedXmlPullParser typedXmlPullParser) {
        String attributeValue = typedXmlPullParser.getAttributeValue((String) null, "n");
        int depth = typedXmlPullParser.getDepth();
        while (true) {
            int next = typedXmlPullParser.next();
            if (next == 1) {
                return;
            }
            if (next == 3 && typedXmlPullParser.getDepth() <= depth) {
                return;
            }
            if (next != 3 && next != 4) {
                if (typedXmlPullParser.getName().equals("uid")) {
                    readUid(typedXmlPullParser, attributeValue);
                } else {
                    Slog.w("LegacyAppOpsServiceInterfaceImpl", "Unknown element under <pkg>: " + typedXmlPullParser.getName());
                    XmlUtils.skipCurrentTag(typedXmlPullParser);
                }
            }
        }
    }

    public final void readUid(TypedXmlPullParser typedXmlPullParser, String str) {
        int userId = UserHandle.getUserId(typedXmlPullParser.getAttributeInt((String) null, "n"));
        int depth = typedXmlPullParser.getDepth();
        while (true) {
            int next = typedXmlPullParser.next();
            if (next == 1) {
                return;
            }
            if (next == 3 && typedXmlPullParser.getDepth() <= depth) {
                return;
            }
            if (next != 3 && next != 4) {
                if (typedXmlPullParser.getName().equals("op")) {
                    readOp(typedXmlPullParser, userId, str);
                } else {
                    Slog.w("LegacyAppOpsServiceInterfaceImpl", "Unknown element under <pkg>: " + typedXmlPullParser.getName());
                    XmlUtils.skipCurrentTag(typedXmlPullParser);
                }
            }
        }
    }

    public final void readUser(TypedXmlPullParser typedXmlPullParser) {
        int attributeInt = typedXmlPullParser.getAttributeInt((String) null, "n");
        int depth = typedXmlPullParser.getDepth();
        while (true) {
            int next = typedXmlPullParser.next();
            if (next == 1) {
                return;
            }
            if (next == 3 && typedXmlPullParser.getDepth() <= depth) {
                return;
            }
            if (next != 3 && next != 4) {
                if (typedXmlPullParser.getName().equals("pkg")) {
                    readPackage(typedXmlPullParser, attributeInt);
                } else {
                    Slog.w("LegacyAppOpsServiceInterfaceImpl", "Unknown element under <user>: " + typedXmlPullParser.getName());
                    XmlUtils.skipCurrentTag(typedXmlPullParser);
                }
            }
        }
    }

    public final void readPackage(TypedXmlPullParser typedXmlPullParser, int i) {
        String attributeValue = typedXmlPullParser.getAttributeValue((String) null, "n");
        int depth = typedXmlPullParser.getDepth();
        while (true) {
            int next = typedXmlPullParser.next();
            if (next == 1) {
                return;
            }
            if (next == 3 && typedXmlPullParser.getDepth() <= depth) {
                return;
            }
            if (next != 3 && next != 4) {
                if (typedXmlPullParser.getName().equals("op")) {
                    readOp(typedXmlPullParser, i, attributeValue);
                } else {
                    Slog.w("LegacyAppOpsServiceInterfaceImpl", "Unknown element under <pkg>: " + typedXmlPullParser.getName());
                    XmlUtils.skipCurrentTag(typedXmlPullParser);
                }
            }
        }
    }

    public final void readOp(TypedXmlPullParser typedXmlPullParser, int i, String str) {
        int attributeInt = typedXmlPullParser.getAttributeInt((String) null, "n");
        int opToDefaultMode = AppOpsManager.opToDefaultMode(attributeInt);
        int attributeInt2 = typedXmlPullParser.getAttributeInt((String) null, "m", opToDefaultMode);
        if (attributeInt2 != opToDefaultMode) {
            ArrayMap arrayMap = (ArrayMap) this.mUserPackageModes.get(i);
            if (arrayMap == null) {
                arrayMap = new ArrayMap();
                this.mUserPackageModes.put(i, arrayMap);
            }
            SparseIntArray sparseIntArray = (SparseIntArray) arrayMap.get(str);
            if (sparseIntArray == null) {
                sparseIntArray = new SparseIntArray();
                arrayMap.put(str, sparseIntArray);
            }
            sparseIntArray.put(attributeInt, attributeInt2);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x002f, code lost:
    
        if (r4 != 3) goto L19;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void upgradeLocked(int r4) {
        /*
            r3 = this;
            r0 = -2
            if (r4 == r0) goto L3e
            r0 = 4
            if (r4 < r0) goto L7
            goto L3e
        L7:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Upgrading app-ops xml from version "
            r1.append(r2)
            r1.append(r4)
            java.lang.String r2 = " to "
            r1.append(r2)
            r1.append(r0)
            java.lang.String r0 = r1.toString()
            java.lang.String r1 = "LegacyAppOpsServiceInterfaceImpl"
            android.util.Slog.d(r1, r0)
            r0 = -1
            if (r4 == r0) goto L32
            r0 = 1
            if (r4 == r0) goto L35
            r0 = 2
            if (r4 == r0) goto L38
            r0 = 3
            if (r4 == r0) goto L38
            goto L3b
        L32:
            r3.upgradeRunAnyInBackgroundLocked()
        L35:
            r3.upgradeScheduleExactAlarmLocked()
        L38:
            r3.resetUseFullScreenIntentLocked()
        L3b:
            r3.scheduleFastWriteLocked()
        L3e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.appop.AppOpsCheckingServiceImpl.upgradeLocked(int):void");
    }

    public void upgradeRunAnyInBackgroundLocked() {
        int size = this.mUidModes.size();
        for (int i = 0; i < size; i++) {
            SparseIntArray sparseIntArray = (SparseIntArray) this.mUidModes.valueAt(i);
            int indexOfKey = sparseIntArray.indexOfKey(63);
            if (indexOfKey >= 0) {
                sparseIntArray.put(70, sparseIntArray.valueAt(indexOfKey));
            }
        }
        int size2 = this.mUserPackageModes.size();
        for (int i2 = 0; i2 < size2; i2++) {
            ArrayMap arrayMap = (ArrayMap) this.mUserPackageModes.valueAt(i2);
            int size3 = arrayMap.size();
            for (int i3 = 0; i3 < size3; i3++) {
                SparseIntArray sparseIntArray2 = (SparseIntArray) arrayMap.valueAt(i3);
                int indexOfKey2 = sparseIntArray2.indexOfKey(63);
                if (indexOfKey2 >= 0) {
                    sparseIntArray2.put(70, sparseIntArray2.valueAt(indexOfKey2));
                }
            }
        }
    }

    public void upgradeScheduleExactAlarmLocked() {
        PermissionManagerServiceInternal permissionManagerServiceInternal = (PermissionManagerServiceInternal) LocalServices.getService(PermissionManagerServiceInternal.class);
        UserManagerInternal userManagerInternal = (UserManagerInternal) LocalServices.getService(UserManagerInternal.class);
        PackageManagerInternal packageManagerInternal = (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
        String[] appOpPermissionPackages = permissionManagerServiceInternal.getAppOpPermissionPackages(AppOpsManager.opToPermission(107));
        int[] userIds = userManagerInternal.getUserIds();
        for (String str : appOpPermissionPackages) {
            for (int i : userIds) {
                int packageUid = packageManagerInternal.getPackageUid(str, 0L, i);
                if (getUidMode(packageUid, 107) == AppOpsManager.opToDefaultMode(107)) {
                    setUidMode(packageUid, 107, 0);
                }
            }
        }
    }

    public void resetUseFullScreenIntentLocked() {
        PermissionManagerServiceInternal permissionManagerServiceInternal = (PermissionManagerServiceInternal) LocalServices.getService(PermissionManagerServiceInternal.class);
        UserManagerInternal userManagerInternal = (UserManagerInternal) LocalServices.getService(UserManagerInternal.class);
        PackageManagerInternal packageManagerInternal = (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
        PermissionManager permissionManager = (PermissionManager) this.mContext.getSystemService(PermissionManager.class);
        String opToPermission = AppOpsManager.opToPermission(133);
        String[] appOpPermissionPackages = permissionManagerServiceInternal.getAppOpPermissionPackages(opToPermission);
        int[] userIds = userManagerInternal.getUserIds();
        for (String str : appOpPermissionPackages) {
            for (int i : userIds) {
                int packageUid = packageManagerInternal.getPackageUid(str, 0L, i);
                if ((permissionManager.getPermissionFlags(str, opToPermission, UserHandle.of(i)) & 1) == 0) {
                    setUidMode(packageUid, 133, AppOpsManager.opToDefaultMode(133));
                }
            }
        }
    }

    public List getUidsWithNonDefaultModes() {
        ArrayList arrayList = new ArrayList();
        synchronized (this.mLock) {
            for (int i = 0; i < this.mUidModes.size(); i++) {
                if (((SparseIntArray) this.mUidModes.valueAt(i)).size() > 0) {
                    arrayList.add(Integer.valueOf(this.mUidModes.keyAt(i)));
                }
            }
        }
        return arrayList;
    }

    public List getPackagesWithNonDefaultModes() {
        ArrayList arrayList = new ArrayList();
        synchronized (this.mLock) {
            for (int i = 0; i < this.mUserPackageModes.size(); i++) {
                ArrayMap arrayMap = (ArrayMap) this.mUserPackageModes.valueAt(i);
                for (int i2 = 0; i2 < arrayMap.size(); i2++) {
                    if (((SparseIntArray) arrayMap.valueAt(i2)).size() > 0) {
                        arrayList.add(UserPackage.of(this.mUserPackageModes.keyAt(i), (String) arrayMap.keyAt(i2)));
                    }
                }
            }
        }
        return arrayList;
    }
}
