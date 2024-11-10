package com.android.server.blob;

import android.app.ActivityManager;
import android.app.ActivityManagerInternal;
import android.app.StatsManager;
import android.app.blob.BlobHandle;
import android.app.blob.BlobInfo;
import android.app.blob.IBlobStoreManager;
import android.app.blob.IBlobStoreSession;
import android.app.blob.LeaseInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManagerInternal;
import android.content.pm.PackageStats;
import android.content.res.ResourceId;
import android.content.res.Resources;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.LimitExceededException;
import android.os.ParcelFileDescriptor;
import android.os.ParcelableException;
import android.os.Process;
import android.os.RemoteCallback;
import android.os.SystemClock;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.AtomicFile;
import android.util.ExceptionUtils;
import android.util.IndentingPrintWriter;
import android.util.LongSparseArray;
import android.util.Slog;
import android.util.SparseArray;
import android.util.Xml;
import com.android.internal.os.BackgroundThread;
import com.android.internal.util.CollectionUtils;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.FastXmlSerializer;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.Preconditions;
import com.android.internal.util.XmlUtils;
import com.android.internal.util.function.LongObjPredicate;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.server.LocalManagerRegistry;
import com.android.server.LocalServices;
import com.android.server.ServiceThread;
import com.android.server.SystemService;
import com.android.server.Watchdog;
import com.android.server.blob.BlobMetadata;
import com.android.server.blob.BlobStoreManagerService;
import com.android.server.pm.UserManagerInternal;
import com.android.server.usage.StorageStatsManagerLocal;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

/* loaded from: classes.dex */
public class BlobStoreManagerService extends SystemService {
    public final ArraySet mActiveBlobIds;
    public final Handler mBackgroundHandler;
    public final Object mBlobsLock;
    public final ArrayMap mBlobsMap;
    public final Context mContext;
    public long mCurrentMaxSessionId;
    public final Handler mHandler;
    public final Injector mInjector;
    public final ArraySet mKnownBlobIds;
    public PackageManagerInternal mPackageManagerInternal;
    public final Random mRandom;
    public final Runnable mSaveBlobsInfoRunnable;
    public final Runnable mSaveSessionsRunnable;
    public final SessionStateChangeListener mSessionStateChangeListener;
    public final SparseArray mSessions;
    public StatsPullAtomCallbackImpl mStatsCallbackImpl;
    public StatsManager mStatsManager;

    /* renamed from: -$$Nest$sminitializeMessageHandler, reason: not valid java name */
    public static /* bridge */ /* synthetic */ Handler m3774$$Nest$sminitializeMessageHandler() {
        return initializeMessageHandler();
    }

    public BlobStoreManagerService(Context context) {
        this(context, new Injector());
    }

    public BlobStoreManagerService(Context context, Injector injector) {
        super(context);
        this.mBlobsLock = new Object();
        this.mSessions = new SparseArray();
        this.mBlobsMap = new ArrayMap();
        this.mActiveBlobIds = new ArraySet();
        this.mKnownBlobIds = new ArraySet();
        this.mRandom = new SecureRandom();
        this.mSessionStateChangeListener = new SessionStateChangeListener();
        this.mStatsCallbackImpl = new StatsPullAtomCallbackImpl();
        this.mSaveBlobsInfoRunnable = new Runnable() { // from class: com.android.server.blob.BlobStoreManagerService$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                BlobStoreManagerService.this.writeBlobsInfo();
            }
        };
        this.mSaveSessionsRunnable = new Runnable() { // from class: com.android.server.blob.BlobStoreManagerService$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                BlobStoreManagerService.this.writeBlobSessions();
            }
        };
        this.mContext = context;
        this.mInjector = injector;
        this.mHandler = injector.initializeMessageHandler();
        this.mBackgroundHandler = injector.getBackgroundHandler();
    }

    public static Handler initializeMessageHandler() {
        ServiceThread serviceThread = new ServiceThread("BlobStore", 0, true);
        serviceThread.start();
        Handler handler = new Handler(serviceThread.getLooper());
        Watchdog.getInstance().addThread(handler);
        return handler;
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        publishBinderService("blob_store", new Stub());
        LocalServices.addService(BlobStoreManagerInternal.class, new LocalService());
        this.mPackageManagerInternal = (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
        this.mStatsManager = (StatsManager) getContext().getSystemService(StatsManager.class);
        registerReceivers();
        ((StorageStatsManagerLocal) LocalManagerRegistry.getManager(StorageStatsManagerLocal.class)).registerStorageStatsAugmenter(new BlobStorageStatsAugmenter(), "BlobStore");
    }

    @Override // com.android.server.SystemService
    public void onBootPhase(int i) {
        if (i == 550) {
            BlobStoreConfig.initialize(this.mContext);
            return;
        }
        if (i != 600) {
            if (i == 1000) {
                BlobStoreIdleJobService.schedule(this.mContext);
            }
        } else {
            synchronized (this.mBlobsLock) {
                SparseArray allPackages = getAllPackages();
                readBlobSessionsLocked(allPackages);
                readBlobsInfoLocked(allPackages);
            }
            registerBlobStorePuller();
        }
    }

    public final long generateNextSessionIdLocked() {
        int i = 0;
        while (true) {
            long nextLong = this.mRandom.nextLong();
            long abs = nextLong == Long.MIN_VALUE ? 0L : Math.abs(nextLong);
            if (this.mKnownBlobIds.indexOf(Long.valueOf(abs)) < 0 && abs != 0) {
                return abs;
            }
            int i2 = i + 1;
            if (i >= 32) {
                throw new IllegalStateException("Failed to allocate session ID");
            }
            i = i2;
        }
    }

    public final void registerReceivers() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_FULLY_REMOVED");
        intentFilter.addAction("android.intent.action.PACKAGE_DATA_CLEARED");
        intentFilter.addDataScheme("package");
        this.mContext.registerReceiverAsUser(new PackageChangedReceiver(), UserHandle.ALL, intentFilter, null, this.mHandler);
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("android.intent.action.USER_REMOVED");
        this.mContext.registerReceiverAsUser(new UserActionReceiver(), UserHandle.ALL, intentFilter2, null, this.mHandler);
    }

    public final LongSparseArray getUserSessionsLocked(int i) {
        LongSparseArray longSparseArray = (LongSparseArray) this.mSessions.get(i);
        if (longSparseArray != null) {
            return longSparseArray;
        }
        LongSparseArray longSparseArray2 = new LongSparseArray();
        this.mSessions.put(i, longSparseArray2);
        return longSparseArray2;
    }

    public void addUserSessionsForTest(LongSparseArray longSparseArray, int i) {
        synchronized (this.mBlobsLock) {
            this.mSessions.put(i, longSparseArray);
        }
    }

    public BlobMetadata getBlobForTest(BlobHandle blobHandle) {
        BlobMetadata blobMetadata;
        synchronized (this.mBlobsLock) {
            blobMetadata = (BlobMetadata) this.mBlobsMap.get(blobHandle);
        }
        return blobMetadata;
    }

    public int getBlobsCountForTest() {
        int size;
        synchronized (this.mBlobsLock) {
            size = this.mBlobsMap.size();
        }
        return size;
    }

    public void addActiveIdsForTest(long... jArr) {
        synchronized (this.mBlobsLock) {
            for (long j : jArr) {
                addActiveBlobIdLocked(j);
            }
        }
    }

    public Set getActiveIdsForTest() {
        ArraySet arraySet;
        synchronized (this.mBlobsLock) {
            arraySet = this.mActiveBlobIds;
        }
        return arraySet;
    }

    public Set getKnownIdsForTest() {
        ArraySet arraySet;
        synchronized (this.mBlobsLock) {
            arraySet = this.mKnownBlobIds;
        }
        return arraySet;
    }

    public final void addSessionForUserLocked(BlobStoreSession blobStoreSession, int i) {
        getUserSessionsLocked(i).put(blobStoreSession.getSessionId(), blobStoreSession);
        addActiveBlobIdLocked(blobStoreSession.getSessionId());
    }

    public void addBlobLocked(BlobMetadata blobMetadata) {
        this.mBlobsMap.put(blobMetadata.getBlobHandle(), blobMetadata);
        addActiveBlobIdLocked(blobMetadata.getBlobId());
    }

    public final void addActiveBlobIdLocked(long j) {
        this.mActiveBlobIds.add(Long.valueOf(j));
        this.mKnownBlobIds.add(Long.valueOf(j));
    }

    public final int getSessionsCountLocked(final int i, final String str) {
        final AtomicInteger atomicInteger = new AtomicInteger(0);
        forEachSessionInUser(new Consumer() { // from class: com.android.server.blob.BlobStoreManagerService$$ExternalSyntheticLambda12
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                BlobStoreManagerService.lambda$getSessionsCountLocked$0(i, str, atomicInteger, (BlobStoreSession) obj);
            }
        }, UserHandle.getUserId(i));
        return atomicInteger.get();
    }

    public static /* synthetic */ void lambda$getSessionsCountLocked$0(int i, String str, AtomicInteger atomicInteger, BlobStoreSession blobStoreSession) {
        if (blobStoreSession.getOwnerUid() == i && blobStoreSession.getOwnerPackageName().equals(str)) {
            atomicInteger.getAndIncrement();
        }
    }

    public final long createSessionInternal(BlobHandle blobHandle, int i, String str) {
        long generateNextSessionIdLocked;
        synchronized (this.mBlobsLock) {
            int sessionsCountLocked = getSessionsCountLocked(i, str);
            if (sessionsCountLocked >= BlobStoreConfig.getMaxActiveSessions()) {
                throw new LimitExceededException("Too many active sessions for the caller: " + sessionsCountLocked);
            }
            generateNextSessionIdLocked = generateNextSessionIdLocked();
            addSessionForUserLocked(new BlobStoreSession(this.mContext, generateNextSessionIdLocked, blobHandle, i, str, this.mSessionStateChangeListener), UserHandle.getUserId(i));
            if (BlobStoreConfig.LOGV) {
                Slog.v("BlobStore", "Created session for " + blobHandle + "; callingUid=" + i + ", callingPackage=" + str);
            }
            writeBlobSessionsAsync();
        }
        return generateNextSessionIdLocked;
    }

    public final BlobStoreSession openSessionInternal(long j, int i, String str) {
        BlobStoreSession blobStoreSession;
        synchronized (this.mBlobsLock) {
            blobStoreSession = (BlobStoreSession) getUserSessionsLocked(UserHandle.getUserId(i)).get(j);
            if (blobStoreSession == null || !blobStoreSession.hasAccess(i, str) || blobStoreSession.isFinalized()) {
                throw new SecurityException("Session not found: " + j);
            }
        }
        blobStoreSession.open();
        return blobStoreSession;
    }

    public final void abandonSessionInternal(long j, int i, String str) {
        synchronized (this.mBlobsLock) {
            BlobStoreSession openSessionInternal = openSessionInternal(j, i, str);
            openSessionInternal.open();
            openSessionInternal.abandon();
            if (BlobStoreConfig.LOGV) {
                Slog.v("BlobStore", "Abandoned session with id " + j + "; callingUid=" + i + ", callingPackage=" + str);
            }
            writeBlobSessionsAsync();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x002b, code lost:
    
        com.android.internal.util.FrameworkStatsLog.write(300, r11, 0L, 0L, 2);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.os.ParcelFileDescriptor openBlobInternal(android.app.blob.BlobHandle r10, int r11, java.lang.String r12) {
        /*
            r9 = this;
            java.lang.Object r0 = r9.mBlobsLock
            monitor-enter(r0)
            android.util.ArrayMap r9 = r9.mBlobsMap     // Catch: java.lang.Throwable -> L6d
            java.lang.Object r9 = r9.get(r10)     // Catch: java.lang.Throwable -> L6d
            com.android.server.blob.BlobMetadata r9 = (com.android.server.blob.BlobMetadata) r9     // Catch: java.lang.Throwable -> L6d
            if (r9 == 0) goto L29
            boolean r1 = r9.isAccessAllowedForCaller(r12, r11)     // Catch: java.lang.Throwable -> L6d
            if (r1 != 0) goto L14
            goto L29
        L14:
            r2 = 300(0x12c, float:4.2E-43)
            long r4 = r9.getBlobId()     // Catch: java.lang.Throwable -> L6d
            long r6 = r9.getSize()     // Catch: java.lang.Throwable -> L6d
            r8 = 1
            r3 = r11
            com.android.internal.util.FrameworkStatsLog.write(r2, r3, r4, r6, r8)     // Catch: java.lang.Throwable -> L6d
            android.os.ParcelFileDescriptor r9 = r9.openForRead(r12, r11)     // Catch: java.lang.Throwable -> L6d
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L6d
            return r9
        L29:
            if (r9 != 0) goto L37
            r1 = 300(0x12c, float:4.2E-43)
            r3 = 0
            r5 = 0
            r7 = 2
            r2 = r11
            com.android.internal.util.FrameworkStatsLog.write(r1, r2, r3, r5, r7)     // Catch: java.lang.Throwable -> L6d
            goto L46
        L37:
            r1 = 300(0x12c, float:4.2E-43)
            long r3 = r9.getBlobId()     // Catch: java.lang.Throwable -> L6d
            long r5 = r9.getSize()     // Catch: java.lang.Throwable -> L6d
            r7 = 3
            r2 = r11
            com.android.internal.util.FrameworkStatsLog.write(r1, r2, r3, r5, r7)     // Catch: java.lang.Throwable -> L6d
        L46:
            java.lang.SecurityException r9 = new java.lang.SecurityException     // Catch: java.lang.Throwable -> L6d
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L6d
            r1.<init>()     // Catch: java.lang.Throwable -> L6d
            java.lang.String r2 = "Caller not allowed to access "
            r1.append(r2)     // Catch: java.lang.Throwable -> L6d
            r1.append(r10)     // Catch: java.lang.Throwable -> L6d
            java.lang.String r10 = "; callingUid="
            r1.append(r10)     // Catch: java.lang.Throwable -> L6d
            r1.append(r11)     // Catch: java.lang.Throwable -> L6d
            java.lang.String r10 = ", callingPackage="
            r1.append(r10)     // Catch: java.lang.Throwable -> L6d
            r1.append(r12)     // Catch: java.lang.Throwable -> L6d
            java.lang.String r10 = r1.toString()     // Catch: java.lang.Throwable -> L6d
            r9.<init>(r10)     // Catch: java.lang.Throwable -> L6d
            throw r9     // Catch: java.lang.Throwable -> L6d
        L6d:
            r9 = move-exception
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L6d
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.blob.BlobStoreManagerService.openBlobInternal(android.app.blob.BlobHandle, int, java.lang.String):android.os.ParcelFileDescriptor");
    }

    public final int getCommittedBlobsCountLocked(final int i, final String str) {
        final AtomicInteger atomicInteger = new AtomicInteger(0);
        forEachBlobLocked(new Consumer() { // from class: com.android.server.blob.BlobStoreManagerService$$ExternalSyntheticLambda21
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                BlobStoreManagerService.lambda$getCommittedBlobsCountLocked$1(str, i, atomicInteger, (BlobMetadata) obj);
            }
        });
        return atomicInteger.get();
    }

    public static /* synthetic */ void lambda$getCommittedBlobsCountLocked$1(String str, int i, AtomicInteger atomicInteger, BlobMetadata blobMetadata) {
        if (blobMetadata.isACommitter(str, i)) {
            atomicInteger.getAndIncrement();
        }
    }

    public final int getLeasedBlobsCountLocked(final int i, final String str) {
        final AtomicInteger atomicInteger = new AtomicInteger(0);
        forEachBlobLocked(new Consumer() { // from class: com.android.server.blob.BlobStoreManagerService$$ExternalSyntheticLambda9
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                BlobStoreManagerService.lambda$getLeasedBlobsCountLocked$2(str, i, atomicInteger, (BlobMetadata) obj);
            }
        });
        return atomicInteger.get();
    }

    public static /* synthetic */ void lambda$getLeasedBlobsCountLocked$2(String str, int i, AtomicInteger atomicInteger, BlobMetadata blobMetadata) {
        if (blobMetadata.isALeasee(str, i)) {
            atomicInteger.getAndIncrement();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:32:0x00bc, code lost:
    
        com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.BLOB_LEASED, r19, 0L, 0L, 2);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void acquireLeaseInternal(android.app.blob.BlobHandle r14, int r15, java.lang.CharSequence r16, long r17, int r19, java.lang.String r20) {
        /*
            Method dump skipped, instructions count: 294
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.blob.BlobStoreManagerService.acquireLeaseInternal(android.app.blob.BlobHandle, int, java.lang.CharSequence, long, int, java.lang.String):void");
    }

    public long getTotalUsageBytesLocked(final int i, final String str) {
        final AtomicLong atomicLong = new AtomicLong(0L);
        forEachBlobLocked(new Consumer() { // from class: com.android.server.blob.BlobStoreManagerService$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                BlobStoreManagerService.lambda$getTotalUsageBytesLocked$3(str, i, atomicLong, (BlobMetadata) obj);
            }
        });
        return atomicLong.get();
    }

    public static /* synthetic */ void lambda$getTotalUsageBytesLocked$3(String str, int i, AtomicLong atomicLong, BlobMetadata blobMetadata) {
        if (blobMetadata.isALeasee(str, i)) {
            atomicLong.getAndAdd(blobMetadata.getSize());
        }
    }

    public final void releaseLeaseInternal(final BlobHandle blobHandle, int i, String str) {
        synchronized (this.mBlobsLock) {
            final BlobMetadata blobMetadata = (BlobMetadata) this.mBlobsMap.get(blobHandle);
            if (blobMetadata == null || !blobMetadata.isAccessAllowedForCaller(str, i)) {
                throw new SecurityException("Caller not allowed to access " + blobHandle + "; callingUid=" + i + ", callingPackage=" + str);
            }
            blobMetadata.removeLeasee(str, i);
            if (BlobStoreConfig.LOGV) {
                Slog.v("BlobStore", "Released lease on " + blobHandle + "; callingUid=" + i + ", callingPackage=" + str);
            }
            if (!blobMetadata.hasValidLeases()) {
                this.mHandler.postDelayed(new Runnable() { // from class: com.android.server.blob.BlobStoreManagerService$$ExternalSyntheticLambda7
                    @Override // java.lang.Runnable
                    public final void run() {
                        BlobStoreManagerService.this.lambda$releaseLeaseInternal$4(blobHandle, blobMetadata);
                    }
                }, BlobStoreConfig.getDeletionOnLastLeaseDelayMs());
            }
            writeBlobsInfoAsync();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$releaseLeaseInternal$4(BlobHandle blobHandle, BlobMetadata blobMetadata) {
        synchronized (this.mBlobsLock) {
            if (Objects.equals(this.mBlobsMap.get(blobHandle), blobMetadata)) {
                if (blobMetadata.shouldBeDeleted(true)) {
                    deleteBlobLocked(blobMetadata);
                    this.mBlobsMap.remove(blobHandle);
                }
                writeBlobsInfoAsync();
            }
        }
    }

    public final void releaseAllLeasesInternal(final int i, final String str) {
        synchronized (this.mBlobsLock) {
            this.mBlobsMap.forEach(new BiConsumer() { // from class: com.android.server.blob.BlobStoreManagerService$$ExternalSyntheticLambda14
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    ((BlobMetadata) obj2).removeLeasee(str, i);
                }
            });
            writeBlobsInfoAsync();
            if (BlobStoreConfig.LOGV) {
                Slog.v("BlobStore", "Release all leases associated with pkg=" + str + ", uid=" + i);
            }
        }
    }

    public final long getRemainingLeaseQuotaBytesInternal(int i, String str) {
        long appDataBytesLimit;
        synchronized (this.mBlobsLock) {
            appDataBytesLimit = BlobStoreConfig.getAppDataBytesLimit() - getTotalUsageBytesLocked(i, str);
            if (appDataBytesLimit <= 0) {
                appDataBytesLimit = 0;
            }
        }
        return appDataBytesLimit;
    }

    public final List queryBlobsForUserInternal(final int i) {
        final ArrayList arrayList = new ArrayList();
        synchronized (this.mBlobsLock) {
            final ArrayMap arrayMap = new ArrayMap();
            final Function function = new Function() { // from class: com.android.server.blob.BlobStoreManagerService$$ExternalSyntheticLambda10
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    Resources lambda$queryBlobsForUserInternal$6;
                    lambda$queryBlobsForUserInternal$6 = BlobStoreManagerService.this.lambda$queryBlobsForUserInternal$6(arrayMap, i, (String) obj);
                    return lambda$queryBlobsForUserInternal$6;
                }
            };
            forEachBlobLocked(new BiConsumer() { // from class: com.android.server.blob.BlobStoreManagerService$$ExternalSyntheticLambda11
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    BlobStoreManagerService.lambda$queryBlobsForUserInternal$8(i, function, arrayList, (BlobHandle) obj, (BlobMetadata) obj2);
                }
            });
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Resources lambda$queryBlobsForUserInternal$6(ArrayMap arrayMap, int i, String str) {
        WeakReference weakReference = (WeakReference) arrayMap.get(str);
        Resources resources = weakReference == null ? null : (Resources) weakReference.get();
        if (resources != null) {
            return resources;
        }
        Resources packageResources = BlobStoreUtils.getPackageResources(this.mContext, str, i);
        arrayMap.put(str, new WeakReference(packageResources));
        return packageResources;
    }

    public static /* synthetic */ void lambda$queryBlobsForUserInternal$8(final int i, final Function function, ArrayList arrayList, final BlobHandle blobHandle, BlobMetadata blobMetadata) {
        if (blobMetadata.hasACommitterOrLeaseeInUser(i)) {
            final ArrayList arrayList2 = new ArrayList();
            blobMetadata.forEachLeasee(new Consumer() { // from class: com.android.server.blob.BlobStoreManagerService$$ExternalSyntheticLambda18
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    BlobStoreManagerService.lambda$queryBlobsForUserInternal$7(i, function, blobHandle, arrayList2, (BlobMetadata.Leasee) obj);
                }
            });
            arrayList.add(new BlobInfo(blobMetadata.getBlobId(), blobHandle.getExpiryTimeMillis(), blobHandle.getLabel(), blobMetadata.getSize(), arrayList2));
        }
    }

    public static /* synthetic */ void lambda$queryBlobsForUserInternal$7(int i, Function function, BlobHandle blobHandle, ArrayList arrayList, BlobMetadata.Leasee leasee) {
        if (leasee.isStillValid() && i == UserHandle.getUserId(leasee.uid)) {
            int descriptionResourceId = leasee.descriptionResEntryName == null ? 0 : BlobStoreUtils.getDescriptionResourceId((Resources) function.apply(leasee.packageName), leasee.descriptionResEntryName, leasee.packageName);
            long j = leasee.expiryTimeMillis;
            if (j == 0) {
                j = blobHandle.getExpiryTimeMillis();
            }
            arrayList.add(new LeaseInfo(leasee.packageName, j, descriptionResourceId, leasee.description));
        }
    }

    public final void deleteBlobInternal(final long j) {
        synchronized (this.mBlobsLock) {
            this.mBlobsMap.entrySet().removeIf(new Predicate() { // from class: com.android.server.blob.BlobStoreManagerService$$ExternalSyntheticLambda16
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    boolean lambda$deleteBlobInternal$9;
                    lambda$deleteBlobInternal$9 = BlobStoreManagerService.this.lambda$deleteBlobInternal$9(j, (Map.Entry) obj);
                    return lambda$deleteBlobInternal$9;
                }
            });
            writeBlobsInfoAsync();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$deleteBlobInternal$9(long j, Map.Entry entry) {
        BlobMetadata blobMetadata = (BlobMetadata) entry.getValue();
        if (blobMetadata.getBlobId() != j) {
            return false;
        }
        deleteBlobLocked(blobMetadata);
        return true;
    }

    public final List getLeasedBlobsInternal(final int i, final String str) {
        final ArrayList arrayList = new ArrayList();
        synchronized (this.mBlobsLock) {
            forEachBlobLocked(new Consumer() { // from class: com.android.server.blob.BlobStoreManagerService$$ExternalSyntheticLambda15
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    BlobStoreManagerService.lambda$getLeasedBlobsInternal$10(str, i, arrayList, (BlobMetadata) obj);
                }
            });
        }
        return arrayList;
    }

    public static /* synthetic */ void lambda$getLeasedBlobsInternal$10(String str, int i, ArrayList arrayList, BlobMetadata blobMetadata) {
        if (blobMetadata.isALeasee(str, i)) {
            arrayList.add(blobMetadata.getBlobHandle());
        }
    }

    public final LeaseInfo getLeaseInfoInternal(BlobHandle blobHandle, int i, String str) {
        LeaseInfo leaseInfo;
        synchronized (this.mBlobsLock) {
            BlobMetadata blobMetadata = (BlobMetadata) this.mBlobsMap.get(blobHandle);
            if (blobMetadata == null || !blobMetadata.isAccessAllowedForCaller(str, i)) {
                throw new SecurityException("Caller not allowed to access " + blobHandle + "; callingUid=" + i + ", callingPackage=" + str);
            }
            leaseInfo = blobMetadata.getLeaseInfo(str, i);
        }
        return leaseInfo;
    }

    public final void verifyCallingPackage(int i, String str) {
        if (this.mPackageManagerInternal.getPackageUid(str, 0L, UserHandle.getUserId(i)) == i) {
            return;
        }
        throw new SecurityException("Specified calling package [" + str + "] does not match the calling uid " + i);
    }

    /* loaded from: classes.dex */
    public class SessionStateChangeListener {
        public SessionStateChangeListener() {
        }

        public void onStateChanged(BlobStoreSession blobStoreSession) {
            BlobStoreManagerService.this.mHandler.post(PooledLambda.obtainRunnable(new BiConsumer() { // from class: com.android.server.blob.BlobStoreManagerService$SessionStateChangeListener$$ExternalSyntheticLambda0
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    ((BlobStoreManagerService) obj).onStateChangedInternal((BlobStoreSession) obj2);
                }
            }, BlobStoreManagerService.this, blobStoreSession).recycleOnUse());
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x01dc A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onStateChangedInternal(final com.android.server.blob.BlobStoreSession r24) {
        /*
            Method dump skipped, instructions count: 489
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.blob.BlobStoreManagerService.onStateChangedInternal(com.android.server.blob.BlobStoreSession):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onStateChangedInternal$11(BlobStoreSession blobStoreSession) {
        blobStoreSession.computeDigest();
        this.mHandler.post(PooledLambda.obtainRunnable(new Consumer() { // from class: com.android.server.blob.BlobStoreManagerService$$ExternalSyntheticLambda20
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((BlobStoreSession) obj).verifyBlobData();
            }
        }, blobStoreSession).recycleOnUse());
    }

    public final void writeBlobSessionsLocked() {
        AtomicFile prepareSessionsIndexFile = prepareSessionsIndexFile();
        if (prepareSessionsIndexFile == null) {
            Slog.wtf("BlobStore", "Error creating sessions index file");
            return;
        }
        FileOutputStream fileOutputStream = null;
        try {
            FileOutputStream startWrite = prepareSessionsIndexFile.startWrite(SystemClock.uptimeMillis());
            try {
                XmlSerializer fastXmlSerializer = new FastXmlSerializer();
                fastXmlSerializer.setOutput(startWrite, StandardCharsets.UTF_8.name());
                fastXmlSerializer.startDocument(null, Boolean.TRUE);
                fastXmlSerializer.startTag(null, "ss");
                XmlUtils.writeIntAttribute(fastXmlSerializer, "v", 6);
                int size = this.mSessions.size();
                for (int i = 0; i < size; i++) {
                    LongSparseArray longSparseArray = (LongSparseArray) this.mSessions.valueAt(i);
                    int size2 = longSparseArray.size();
                    for (int i2 = 0; i2 < size2; i2++) {
                        fastXmlSerializer.startTag(null, "s");
                        ((BlobStoreSession) longSparseArray.valueAt(i2)).writeToXml(fastXmlSerializer);
                        fastXmlSerializer.endTag(null, "s");
                    }
                }
                fastXmlSerializer.endTag(null, "ss");
                fastXmlSerializer.endDocument();
                prepareSessionsIndexFile.finishWrite(startWrite);
                if (BlobStoreConfig.LOGV) {
                    Slog.v("BlobStore", "Finished persisting sessions data");
                }
            } catch (Exception e) {
                e = e;
                fileOutputStream = startWrite;
                prepareSessionsIndexFile.failWrite(fileOutputStream);
                Slog.wtf("BlobStore", "Error writing sessions data", e);
                throw e;
            }
        } catch (Exception e2) {
            e = e2;
        }
    }

    public final void readBlobSessionsLocked(SparseArray sparseArray) {
        BlobStoreSession createFromXml;
        if (BlobStoreConfig.getBlobStoreRootDir().exists()) {
            AtomicFile prepareSessionsIndexFile = prepareSessionsIndexFile();
            if (prepareSessionsIndexFile == null) {
                Slog.wtf("BlobStore", "Error creating sessions index file");
                return;
            }
            if (!prepareSessionsIndexFile.exists()) {
                Slog.w("BlobStore", "Sessions index file not available: " + prepareSessionsIndexFile.getBaseFile());
                return;
            }
            this.mSessions.clear();
            try {
                FileInputStream openRead = prepareSessionsIndexFile.openRead();
                try {
                    XmlPullParser newPullParser = Xml.newPullParser();
                    newPullParser.setInput(openRead, StandardCharsets.UTF_8.name());
                    XmlUtils.beginDocument(newPullParser, "ss");
                    int readIntAttribute = XmlUtils.readIntAttribute(newPullParser, "v");
                    while (true) {
                        XmlUtils.nextElement(newPullParser);
                        if (newPullParser.getEventType() == 1) {
                            break;
                        }
                        if ("s".equals(newPullParser.getName()) && (createFromXml = BlobStoreSession.createFromXml(newPullParser, readIntAttribute, this.mContext, this.mSessionStateChangeListener)) != null) {
                            SparseArray sparseArray2 = (SparseArray) sparseArray.get(UserHandle.getUserId(createFromXml.getOwnerUid()));
                            if (sparseArray2 != null && createFromXml.getOwnerPackageName().equals(sparseArray2.get(createFromXml.getOwnerUid()))) {
                                addSessionForUserLocked(createFromXml, UserHandle.getUserId(createFromXml.getOwnerUid()));
                            } else {
                                createFromXml.getSessionFile().delete();
                            }
                            this.mCurrentMaxSessionId = Math.max(this.mCurrentMaxSessionId, createFromXml.getSessionId());
                        }
                    }
                    if (BlobStoreConfig.LOGV) {
                        Slog.v("BlobStore", "Finished reading sessions data");
                    }
                    if (openRead != null) {
                        openRead.close();
                    }
                } finally {
                }
            } catch (Exception e) {
                Slog.wtf("BlobStore", "Error reading sessions data", e);
            }
        }
    }

    public final void writeBlobsInfoLocked() {
        AtomicFile prepareBlobsIndexFile = prepareBlobsIndexFile();
        if (prepareBlobsIndexFile == null) {
            Slog.wtf("BlobStore", "Error creating blobs index file");
            return;
        }
        FileOutputStream fileOutputStream = null;
        try {
            FileOutputStream startWrite = prepareBlobsIndexFile.startWrite(SystemClock.uptimeMillis());
            try {
                XmlSerializer fastXmlSerializer = new FastXmlSerializer();
                fastXmlSerializer.setOutput(startWrite, StandardCharsets.UTF_8.name());
                fastXmlSerializer.startDocument(null, Boolean.TRUE);
                fastXmlSerializer.startTag(null, "bs");
                XmlUtils.writeIntAttribute(fastXmlSerializer, "v", 6);
                int size = this.mBlobsMap.size();
                for (int i = 0; i < size; i++) {
                    fastXmlSerializer.startTag(null, "b");
                    ((BlobMetadata) this.mBlobsMap.valueAt(i)).writeToXml(fastXmlSerializer);
                    fastXmlSerializer.endTag(null, "b");
                }
                fastXmlSerializer.endTag(null, "bs");
                fastXmlSerializer.endDocument();
                prepareBlobsIndexFile.finishWrite(startWrite);
                if (BlobStoreConfig.LOGV) {
                    Slog.v("BlobStore", "Finished persisting blobs data");
                }
            } catch (Exception e) {
                e = e;
                fileOutputStream = startWrite;
                prepareBlobsIndexFile.failWrite(fileOutputStream);
                Slog.wtf("BlobStore", "Error writing blobs data", e);
                throw e;
            }
        } catch (Exception e2) {
            e = e2;
        }
    }

    public final void readBlobsInfoLocked(SparseArray sparseArray) {
        if (BlobStoreConfig.getBlobStoreRootDir().exists()) {
            AtomicFile prepareBlobsIndexFile = prepareBlobsIndexFile();
            if (prepareBlobsIndexFile == null) {
                Slog.wtf("BlobStore", "Error creating blobs index file");
                return;
            }
            if (!prepareBlobsIndexFile.exists()) {
                Slog.w("BlobStore", "Blobs index file not available: " + prepareBlobsIndexFile.getBaseFile());
                return;
            }
            this.mBlobsMap.clear();
            try {
                FileInputStream openRead = prepareBlobsIndexFile.openRead();
                try {
                    XmlPullParser newPullParser = Xml.newPullParser();
                    newPullParser.setInput(openRead, StandardCharsets.UTF_8.name());
                    XmlUtils.beginDocument(newPullParser, "bs");
                    int readIntAttribute = XmlUtils.readIntAttribute(newPullParser, "v");
                    while (true) {
                        XmlUtils.nextElement(newPullParser);
                        if (newPullParser.getEventType() == 1) {
                            break;
                        }
                        if ("b".equals(newPullParser.getName())) {
                            BlobMetadata createFromXml = BlobMetadata.createFromXml(newPullParser, readIntAttribute, this.mContext);
                            createFromXml.removeCommittersFromUnknownPkgs(sparseArray);
                            createFromXml.removeLeaseesFromUnknownPkgs(sparseArray);
                            this.mCurrentMaxSessionId = Math.max(this.mCurrentMaxSessionId, createFromXml.getBlobId());
                            if (readIntAttribute >= 6) {
                                addBlobLocked(createFromXml);
                            } else {
                                BlobMetadata blobMetadata = (BlobMetadata) this.mBlobsMap.get(createFromXml.getBlobHandle());
                                if (blobMetadata == null) {
                                    addBlobLocked(createFromXml);
                                } else {
                                    blobMetadata.addCommittersAndLeasees(createFromXml);
                                    createFromXml.getBlobFile().delete();
                                }
                            }
                        }
                    }
                    if (BlobStoreConfig.LOGV) {
                        Slog.v("BlobStore", "Finished reading blobs data");
                    }
                    if (openRead != null) {
                        openRead.close();
                    }
                } finally {
                }
            } catch (Exception e) {
                Slog.wtf("BlobStore", "Error reading blobs data", e);
            }
        }
    }

    public final void writeBlobsInfo() {
        synchronized (this.mBlobsLock) {
            try {
                writeBlobsInfoLocked();
            } catch (Exception unused) {
            }
        }
    }

    public final void writeBlobsInfoAsync() {
        if (this.mHandler.hasCallbacks(this.mSaveBlobsInfoRunnable)) {
            return;
        }
        this.mHandler.post(this.mSaveBlobsInfoRunnable);
    }

    public final void writeBlobSessions() {
        synchronized (this.mBlobsLock) {
            try {
                writeBlobSessionsLocked();
            } catch (Exception unused) {
            }
        }
    }

    public final void writeBlobSessionsAsync() {
        if (this.mHandler.hasCallbacks(this.mSaveSessionsRunnable)) {
            return;
        }
        this.mHandler.post(this.mSaveSessionsRunnable);
    }

    public final SparseArray getAllPackages() {
        SparseArray sparseArray = new SparseArray();
        for (int i : ((UserManagerInternal) LocalServices.getService(UserManagerInternal.class)).getUserIds()) {
            SparseArray sparseArray2 = new SparseArray();
            sparseArray.put(i, sparseArray2);
            List installedApplications = this.mPackageManagerInternal.getInstalledApplications(794624L, i, Process.myUid());
            int size = installedApplications.size();
            for (int i2 = 0; i2 < size; i2++) {
                ApplicationInfo applicationInfo = (ApplicationInfo) installedApplications.get(i2);
                sparseArray2.put(applicationInfo.uid, applicationInfo.packageName);
            }
        }
        return sparseArray;
    }

    public final AtomicFile prepareSessionsIndexFile() {
        File prepareSessionIndexFile = BlobStoreConfig.prepareSessionIndexFile();
        if (prepareSessionIndexFile == null) {
            return null;
        }
        return new AtomicFile(prepareSessionIndexFile, "session_index");
    }

    public final AtomicFile prepareBlobsIndexFile() {
        File prepareBlobsIndexFile = BlobStoreConfig.prepareBlobsIndexFile();
        if (prepareBlobsIndexFile == null) {
            return null;
        }
        return new AtomicFile(prepareBlobsIndexFile, "blobs_index");
    }

    public void handlePackageRemoved(final String str, final int i) {
        synchronized (this.mBlobsLock) {
            getUserSessionsLocked(UserHandle.getUserId(i)).removeIf(new LongObjPredicate() { // from class: com.android.server.blob.BlobStoreManagerService$$ExternalSyntheticLambda5
                public final boolean test(long j, Object obj) {
                    boolean lambda$handlePackageRemoved$12;
                    lambda$handlePackageRemoved$12 = BlobStoreManagerService.this.lambda$handlePackageRemoved$12(i, str, j, (BlobStoreSession) obj);
                    return lambda$handlePackageRemoved$12;
                }
            });
            writeBlobSessionsAsync();
            this.mBlobsMap.entrySet().removeIf(new Predicate() { // from class: com.android.server.blob.BlobStoreManagerService$$ExternalSyntheticLambda6
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    boolean lambda$handlePackageRemoved$13;
                    lambda$handlePackageRemoved$13 = BlobStoreManagerService.this.lambda$handlePackageRemoved$13(str, i, (Map.Entry) obj);
                    return lambda$handlePackageRemoved$13;
                }
            });
            writeBlobsInfoAsync();
            if (BlobStoreConfig.LOGV) {
                Slog.v("BlobStore", "Removed blobs data associated with pkg=" + str + ", uid=" + i);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$handlePackageRemoved$12(int i, String str, long j, BlobStoreSession blobStoreSession) {
        if (blobStoreSession.getOwnerUid() != i || !blobStoreSession.getOwnerPackageName().equals(str)) {
            return false;
        }
        deleteSessionLocked(blobStoreSession);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$handlePackageRemoved$13(String str, int i, Map.Entry entry) {
        BlobMetadata blobMetadata = (BlobMetadata) entry.getValue();
        boolean isACommitter = blobMetadata.isACommitter(str, i);
        if (isACommitter) {
            blobMetadata.removeCommitter(str, i);
        }
        blobMetadata.removeLeasee(str, i);
        if (!blobMetadata.shouldBeDeleted(isACommitter)) {
            return false;
        }
        deleteBlobLocked(blobMetadata);
        return true;
    }

    public final void handleUserRemoved(final int i) {
        synchronized (this.mBlobsLock) {
            LongSparseArray longSparseArray = (LongSparseArray) this.mSessions.removeReturnOld(i);
            if (longSparseArray != null) {
                int size = longSparseArray.size();
                for (int i2 = 0; i2 < size; i2++) {
                    deleteSessionLocked((BlobStoreSession) longSparseArray.valueAt(i2));
                }
            }
            this.mBlobsMap.entrySet().removeIf(new Predicate() { // from class: com.android.server.blob.BlobStoreManagerService$$ExternalSyntheticLambda13
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    boolean lambda$handleUserRemoved$14;
                    lambda$handleUserRemoved$14 = BlobStoreManagerService.this.lambda$handleUserRemoved$14(i, (Map.Entry) obj);
                    return lambda$handleUserRemoved$14;
                }
            });
            if (BlobStoreConfig.LOGV) {
                Slog.v("BlobStore", "Removed blobs data in user " + i);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$handleUserRemoved$14(int i, Map.Entry entry) {
        BlobMetadata blobMetadata = (BlobMetadata) entry.getValue();
        blobMetadata.removeDataForUser(i);
        if (!blobMetadata.shouldBeDeleted(true)) {
            return false;
        }
        deleteBlobLocked(blobMetadata);
        return true;
    }

    public void handleIdleMaintenanceLocked() {
        final ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        File blobsDir = BlobStoreConfig.getBlobsDir();
        if (blobsDir.exists()) {
            for (File file : blobsDir.listFiles()) {
                try {
                    long parseLong = Long.parseLong(file.getName());
                    if (this.mActiveBlobIds.indexOf(Long.valueOf(parseLong)) < 0) {
                        arrayList2.add(file);
                        arrayList.add(Long.valueOf(parseLong));
                    }
                } catch (NumberFormatException e) {
                    Slog.wtf("BlobStore", "Error parsing the file name: " + file, e);
                    arrayList2.add(file);
                }
            }
            int size = arrayList2.size();
            for (int i = 0; i < size; i++) {
                ((File) arrayList2.get(i)).delete();
            }
        }
        this.mBlobsMap.entrySet().removeIf(new Predicate() { // from class: com.android.server.blob.BlobStoreManagerService$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$handleIdleMaintenanceLocked$15;
                lambda$handleIdleMaintenanceLocked$15 = BlobStoreManagerService.this.lambda$handleIdleMaintenanceLocked$15(arrayList, (Map.Entry) obj);
                return lambda$handleIdleMaintenanceLocked$15;
            }
        });
        writeBlobsInfoAsync();
        int size2 = this.mSessions.size();
        for (int i2 = 0; i2 < size2; i2++) {
            ((LongSparseArray) this.mSessions.valueAt(i2)).removeIf(new LongObjPredicate() { // from class: com.android.server.blob.BlobStoreManagerService$$ExternalSyntheticLambda2
                public final boolean test(long j, Object obj) {
                    boolean lambda$handleIdleMaintenanceLocked$16;
                    lambda$handleIdleMaintenanceLocked$16 = BlobStoreManagerService.this.lambda$handleIdleMaintenanceLocked$16(arrayList, j, (BlobStoreSession) obj);
                    return lambda$handleIdleMaintenanceLocked$16;
                }
            });
        }
        Slog.d("BlobStore", "Completed idle maintenance; deleted " + Arrays.toString(arrayList.toArray()));
        writeBlobSessionsAsync();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$handleIdleMaintenanceLocked$15(ArrayList arrayList, Map.Entry entry) {
        BlobMetadata blobMetadata = (BlobMetadata) entry.getValue();
        blobMetadata.removeExpiredLeases();
        if (!blobMetadata.shouldBeDeleted(true)) {
            return false;
        }
        deleteBlobLocked(blobMetadata);
        arrayList.add(Long.valueOf(blobMetadata.getBlobId()));
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$handleIdleMaintenanceLocked$16(ArrayList arrayList, long j, BlobStoreSession blobStoreSession) {
        boolean isExpired = blobStoreSession.isExpired();
        if (blobStoreSession.getBlobHandle().isExpired()) {
            isExpired = true;
        }
        if (isExpired) {
            deleteSessionLocked(blobStoreSession);
            arrayList.add(Long.valueOf(blobStoreSession.getSessionId()));
        }
        return isExpired;
    }

    public final void deleteSessionLocked(BlobStoreSession blobStoreSession) {
        blobStoreSession.destroy();
        this.mActiveBlobIds.remove(Long.valueOf(blobStoreSession.getSessionId()));
    }

    public final void deleteBlobLocked(BlobMetadata blobMetadata) {
        blobMetadata.destroy();
        this.mActiveBlobIds.remove(Long.valueOf(blobMetadata.getBlobId()));
    }

    public void runClearAllSessions(int i) {
        synchronized (this.mBlobsLock) {
            int size = this.mSessions.size();
            for (int i2 = 0; i2 < size; i2++) {
                int keyAt = this.mSessions.keyAt(i2);
                if (i == -1 || i == keyAt) {
                    LongSparseArray longSparseArray = (LongSparseArray) this.mSessions.valueAt(i2);
                    int size2 = longSparseArray.size();
                    for (int i3 = 0; i3 < size2; i3++) {
                        this.mActiveBlobIds.remove(Long.valueOf(((BlobStoreSession) longSparseArray.valueAt(i3)).getSessionId()));
                    }
                }
            }
            if (i == -1) {
                this.mSessions.clear();
            } else {
                this.mSessions.remove(i);
            }
            writeBlobSessionsAsync();
        }
    }

    public void runClearAllBlobs(final int i) {
        synchronized (this.mBlobsLock) {
            this.mBlobsMap.entrySet().removeIf(new Predicate() { // from class: com.android.server.blob.BlobStoreManagerService$$ExternalSyntheticLambda8
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    boolean lambda$runClearAllBlobs$17;
                    lambda$runClearAllBlobs$17 = BlobStoreManagerService.this.lambda$runClearAllBlobs$17(i, (Map.Entry) obj);
                    return lambda$runClearAllBlobs$17;
                }
            });
            writeBlobsInfoAsync();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$runClearAllBlobs$17(int i, Map.Entry entry) {
        BlobMetadata blobMetadata = (BlobMetadata) entry.getValue();
        if (i == -1) {
            this.mActiveBlobIds.remove(Long.valueOf(blobMetadata.getBlobId()));
            return true;
        }
        blobMetadata.removeDataForUser(i);
        if (!blobMetadata.shouldBeDeleted(false)) {
            return false;
        }
        this.mActiveBlobIds.remove(Long.valueOf(blobMetadata.getBlobId()));
        return true;
    }

    public void deleteBlob(BlobHandle blobHandle, int i) {
        synchronized (this.mBlobsLock) {
            BlobMetadata blobMetadata = (BlobMetadata) this.mBlobsMap.get(blobHandle);
            if (blobMetadata == null) {
                return;
            }
            blobMetadata.removeDataForUser(i);
            if (blobMetadata.shouldBeDeleted(false)) {
                deleteBlobLocked(blobMetadata);
                this.mBlobsMap.remove(blobHandle);
            }
            writeBlobsInfoAsync();
        }
    }

    public void runIdleMaintenance() {
        synchronized (this.mBlobsLock) {
            handleIdleMaintenanceLocked();
        }
    }

    public boolean isBlobAvailable(long j, int i) {
        synchronized (this.mBlobsLock) {
            int size = this.mBlobsMap.size();
            for (int i2 = 0; i2 < size; i2++) {
                BlobMetadata blobMetadata = (BlobMetadata) this.mBlobsMap.valueAt(i2);
                if (blobMetadata.getBlobId() == j) {
                    return blobMetadata.hasACommitterInUser(i);
                }
            }
            return false;
        }
    }

    public final void dumpSessionsLocked(IndentingPrintWriter indentingPrintWriter, DumpArgs dumpArgs) {
        int size = this.mSessions.size();
        for (int i = 0; i < size; i++) {
            int keyAt = this.mSessions.keyAt(i);
            if (dumpArgs.shouldDumpUser(keyAt)) {
                LongSparseArray longSparseArray = (LongSparseArray) this.mSessions.valueAt(i);
                indentingPrintWriter.println("List of sessions in user #" + keyAt + " (" + longSparseArray.size() + "):");
                indentingPrintWriter.increaseIndent();
                int size2 = longSparseArray.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    long keyAt2 = longSparseArray.keyAt(i2);
                    BlobStoreSession blobStoreSession = (BlobStoreSession) longSparseArray.valueAt(i2);
                    if (dumpArgs.shouldDumpSession(blobStoreSession.getOwnerPackageName(), blobStoreSession.getOwnerUid(), blobStoreSession.getSessionId())) {
                        indentingPrintWriter.println("Session #" + keyAt2);
                        indentingPrintWriter.increaseIndent();
                        blobStoreSession.dump(indentingPrintWriter, dumpArgs);
                        indentingPrintWriter.decreaseIndent();
                    }
                }
                indentingPrintWriter.decreaseIndent();
            }
        }
    }

    public final void dumpBlobsLocked(IndentingPrintWriter indentingPrintWriter, DumpArgs dumpArgs) {
        indentingPrintWriter.println("List of blobs (" + this.mBlobsMap.size() + "):");
        indentingPrintWriter.increaseIndent();
        int size = this.mBlobsMap.size();
        for (int i = 0; i < size; i++) {
            BlobMetadata blobMetadata = (BlobMetadata) this.mBlobsMap.valueAt(i);
            if (dumpArgs.shouldDumpBlob(blobMetadata.getBlobId())) {
                indentingPrintWriter.println("Blob #" + blobMetadata.getBlobId());
                indentingPrintWriter.increaseIndent();
                blobMetadata.dump(indentingPrintWriter, dumpArgs);
                indentingPrintWriter.decreaseIndent();
            }
        }
        if (this.mBlobsMap.isEmpty()) {
            indentingPrintWriter.println("<empty>");
        }
        indentingPrintWriter.decreaseIndent();
    }

    /* loaded from: classes.dex */
    public class BlobStorageStatsAugmenter implements StorageStatsManagerLocal.StorageStatsAugmenter {
        public BlobStorageStatsAugmenter() {
        }

        @Override // com.android.server.usage.StorageStatsManagerLocal.StorageStatsAugmenter
        public void augmentStatsForPackageForUser(PackageStats packageStats, final String str, final UserHandle userHandle, final boolean z) {
            final AtomicLong atomicLong = new AtomicLong(0L);
            BlobStoreManagerService.this.forEachSessionInUser(new Consumer() { // from class: com.android.server.blob.BlobStoreManagerService$BlobStorageStatsAugmenter$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    BlobStoreManagerService.BlobStorageStatsAugmenter.lambda$augmentStatsForPackageForUser$0(str, atomicLong, (BlobStoreSession) obj);
                }
            }, userHandle.getIdentifier());
            BlobStoreManagerService.this.forEachBlob(new Consumer() { // from class: com.android.server.blob.BlobStoreManagerService$BlobStorageStatsAugmenter$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    BlobStoreManagerService.BlobStorageStatsAugmenter.lambda$augmentStatsForPackageForUser$1(str, userHandle, z, atomicLong, (BlobMetadata) obj);
                }
            });
            packageStats.dataSize += atomicLong.get();
        }

        public static /* synthetic */ void lambda$augmentStatsForPackageForUser$0(String str, AtomicLong atomicLong, BlobStoreSession blobStoreSession) {
            if (blobStoreSession.getOwnerPackageName().equals(str)) {
                atomicLong.getAndAdd(blobStoreSession.getSize());
            }
        }

        public static /* synthetic */ void lambda$augmentStatsForPackageForUser$1(String str, UserHandle userHandle, boolean z, AtomicLong atomicLong, BlobMetadata blobMetadata) {
            if (blobMetadata.shouldAttributeToLeasee(str, userHandle.getIdentifier(), z)) {
                atomicLong.getAndAdd(blobMetadata.getSize());
            }
        }

        @Override // com.android.server.usage.StorageStatsManagerLocal.StorageStatsAugmenter
        public void augmentStatsForUid(PackageStats packageStats, final int i, final boolean z) {
            int userId = UserHandle.getUserId(i);
            final AtomicLong atomicLong = new AtomicLong(0L);
            BlobStoreManagerService.this.forEachSessionInUser(new Consumer() { // from class: com.android.server.blob.BlobStoreManagerService$BlobStorageStatsAugmenter$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    BlobStoreManagerService.BlobStorageStatsAugmenter.lambda$augmentStatsForUid$2(i, atomicLong, (BlobStoreSession) obj);
                }
            }, userId);
            BlobStoreManagerService.this.forEachBlob(new Consumer() { // from class: com.android.server.blob.BlobStoreManagerService$BlobStorageStatsAugmenter$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    BlobStoreManagerService.BlobStorageStatsAugmenter.lambda$augmentStatsForUid$3(i, z, atomicLong, (BlobMetadata) obj);
                }
            });
            packageStats.dataSize += atomicLong.get();
        }

        public static /* synthetic */ void lambda$augmentStatsForUid$2(int i, AtomicLong atomicLong, BlobStoreSession blobStoreSession) {
            if (blobStoreSession.getOwnerUid() == i) {
                atomicLong.getAndAdd(blobStoreSession.getSize());
            }
        }

        public static /* synthetic */ void lambda$augmentStatsForUid$3(int i, boolean z, AtomicLong atomicLong, BlobMetadata blobMetadata) {
            if (blobMetadata.shouldAttributeToLeasee(i, z)) {
                atomicLong.getAndAdd(blobMetadata.getSize());
            }
        }

        @Override // com.android.server.usage.StorageStatsManagerLocal.StorageStatsAugmenter
        public void augmentStatsForUser(PackageStats packageStats, final UserHandle userHandle) {
            final AtomicLong atomicLong = new AtomicLong(0L);
            BlobStoreManagerService.this.forEachSessionInUser(new Consumer() { // from class: com.android.server.blob.BlobStoreManagerService$BlobStorageStatsAugmenter$$ExternalSyntheticLambda4
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    BlobStoreManagerService.BlobStorageStatsAugmenter.lambda$augmentStatsForUser$4(atomicLong, (BlobStoreSession) obj);
                }
            }, userHandle.getIdentifier());
            BlobStoreManagerService.this.forEachBlob(new Consumer() { // from class: com.android.server.blob.BlobStoreManagerService$BlobStorageStatsAugmenter$$ExternalSyntheticLambda5
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    BlobStoreManagerService.BlobStorageStatsAugmenter.lambda$augmentStatsForUser$5(userHandle, atomicLong, (BlobMetadata) obj);
                }
            });
            packageStats.dataSize += atomicLong.get();
        }

        public static /* synthetic */ void lambda$augmentStatsForUser$4(AtomicLong atomicLong, BlobStoreSession blobStoreSession) {
            atomicLong.getAndAdd(blobStoreSession.getSize());
        }

        public static /* synthetic */ void lambda$augmentStatsForUser$5(UserHandle userHandle, AtomicLong atomicLong, BlobMetadata blobMetadata) {
            if (blobMetadata.shouldAttributeToUser(userHandle.getIdentifier())) {
                atomicLong.getAndAdd(blobMetadata.getSize());
            }
        }
    }

    public final void forEachSessionInUser(Consumer consumer, int i) {
        synchronized (this.mBlobsLock) {
            LongSparseArray userSessionsLocked = getUserSessionsLocked(i);
            int size = userSessionsLocked.size();
            for (int i2 = 0; i2 < size; i2++) {
                consumer.accept((BlobStoreSession) userSessionsLocked.valueAt(i2));
            }
        }
    }

    public final void forEachBlob(Consumer consumer) {
        synchronized (this.mBlobsMap) {
            forEachBlobLocked(consumer);
        }
    }

    public final void forEachBlobLocked(Consumer consumer) {
        int size = this.mBlobsMap.size();
        for (int i = 0; i < size; i++) {
            consumer.accept((BlobMetadata) this.mBlobsMap.valueAt(i));
        }
    }

    public final void forEachBlobLocked(BiConsumer biConsumer) {
        int size = this.mBlobsMap.size();
        for (int i = 0; i < size; i++) {
            biConsumer.accept((BlobHandle) this.mBlobsMap.keyAt(i), (BlobMetadata) this.mBlobsMap.valueAt(i));
        }
    }

    public final boolean isAllowedBlobStoreAccess(int i, String str) {
        return (Process.isSdkSandboxUid(i) || Process.isIsolated(i) || this.mPackageManagerInternal.isInstantApp(str, UserHandle.getUserId(i))) ? false : true;
    }

    /* loaded from: classes.dex */
    public class PackageChangedReceiver extends BroadcastReceiver {
        public PackageChangedReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (BlobStoreConfig.LOGV) {
                Slog.v("BlobStore", "Received " + intent);
            }
            String action = intent.getAction();
            action.hashCode();
            if (action.equals("android.intent.action.PACKAGE_DATA_CLEARED") || action.equals("android.intent.action.PACKAGE_FULLY_REMOVED")) {
                String schemeSpecificPart = intent.getData().getSchemeSpecificPart();
                if (schemeSpecificPart == null) {
                    Slog.wtf("BlobStore", "Package name is missing in the intent: " + intent);
                    return;
                }
                int intExtra = intent.getIntExtra("android.intent.extra.UID", -1);
                if (intExtra == -1) {
                    Slog.wtf("BlobStore", "uid is missing in the intent: " + intent);
                    return;
                }
                BlobStoreManagerService.this.handlePackageRemoved(schemeSpecificPart, intExtra);
                return;
            }
            Slog.wtf("BlobStore", "Received unknown intent: " + intent);
        }
    }

    /* loaded from: classes.dex */
    public class UserActionReceiver extends BroadcastReceiver {
        public UserActionReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (BlobStoreConfig.LOGV) {
                Slog.v("BlobStore", "Received: " + intent);
            }
            String action = intent.getAction();
            action.hashCode();
            if (action.equals("android.intent.action.USER_REMOVED")) {
                int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -10000);
                if (intExtra == -10000) {
                    Slog.wtf("BlobStore", "userId is missing in the intent: " + intent);
                    return;
                }
                BlobStoreManagerService.this.handleUserRemoved(intExtra);
                return;
            }
            Slog.wtf("BlobStore", "Received unknown intent: " + intent);
        }
    }

    /* loaded from: classes.dex */
    public class Stub extends IBlobStoreManager.Stub {
        public Stub() {
        }

        public long createSession(BlobHandle blobHandle, String str) {
            Objects.requireNonNull(blobHandle, "blobHandle must not be null");
            blobHandle.assertIsValid();
            Objects.requireNonNull(str, "packageName must not be null");
            int callingUid = Binder.getCallingUid();
            BlobStoreManagerService.this.verifyCallingPackage(callingUid, str);
            if (!BlobStoreManagerService.this.isAllowedBlobStoreAccess(callingUid, str)) {
                throw new SecurityException("Caller not allowed to create session; callingUid=" + callingUid + ", callingPackage=" + str);
            }
            try {
                return BlobStoreManagerService.this.createSessionInternal(blobHandle, callingUid, str);
            } catch (LimitExceededException e) {
                throw new ParcelableException(e);
            }
        }

        public IBlobStoreSession openSession(long j, String str) {
            Preconditions.checkArgumentPositive((float) j, "sessionId must be positive: " + j);
            Objects.requireNonNull(str, "packageName must not be null");
            int callingUid = Binder.getCallingUid();
            BlobStoreManagerService.this.verifyCallingPackage(callingUid, str);
            return BlobStoreManagerService.this.openSessionInternal(j, callingUid, str);
        }

        public void abandonSession(long j, String str) {
            Preconditions.checkArgumentPositive((float) j, "sessionId must be positive: " + j);
            Objects.requireNonNull(str, "packageName must not be null");
            int callingUid = Binder.getCallingUid();
            BlobStoreManagerService.this.verifyCallingPackage(callingUid, str);
            BlobStoreManagerService.this.abandonSessionInternal(j, callingUid, str);
        }

        public ParcelFileDescriptor openBlob(BlobHandle blobHandle, String str) {
            Objects.requireNonNull(blobHandle, "blobHandle must not be null");
            blobHandle.assertIsValid();
            Objects.requireNonNull(str, "packageName must not be null");
            int callingUid = Binder.getCallingUid();
            BlobStoreManagerService.this.verifyCallingPackage(callingUid, str);
            if (!BlobStoreManagerService.this.isAllowedBlobStoreAccess(callingUid, str)) {
                throw new SecurityException("Caller not allowed to open blob; callingUid=" + callingUid + ", callingPackage=" + str);
            }
            try {
                return BlobStoreManagerService.this.openBlobInternal(blobHandle, callingUid, str);
            } catch (IOException e) {
                throw ExceptionUtils.wrap(e);
            }
        }

        public void acquireLease(BlobHandle blobHandle, int i, CharSequence charSequence, long j, String str) {
            Objects.requireNonNull(blobHandle, "blobHandle must not be null");
            blobHandle.assertIsValid();
            Preconditions.checkArgument(ResourceId.isValid(i) || charSequence != null, "Description must be valid; descriptionId=" + i + ", description=" + ((Object) charSequence));
            Preconditions.checkArgumentNonnegative(j, "leaseExpiryTimeMillis must not be negative");
            Objects.requireNonNull(str, "packageName must not be null");
            CharSequence truncatedLeaseDescription = BlobStoreConfig.getTruncatedLeaseDescription(charSequence);
            int callingUid = Binder.getCallingUid();
            BlobStoreManagerService.this.verifyCallingPackage(callingUid, str);
            if (!BlobStoreManagerService.this.isAllowedBlobStoreAccess(callingUid, str)) {
                throw new SecurityException("Caller not allowed to open blob; callingUid=" + callingUid + ", callingPackage=" + str);
            }
            try {
                BlobStoreManagerService.this.acquireLeaseInternal(blobHandle, i, truncatedLeaseDescription, j, callingUid, str);
            } catch (Resources.NotFoundException e) {
                throw new IllegalArgumentException(e);
            } catch (LimitExceededException e2) {
                throw new ParcelableException(e2);
            }
        }

        public void releaseLease(BlobHandle blobHandle, String str) {
            Objects.requireNonNull(blobHandle, "blobHandle must not be null");
            blobHandle.assertIsValid();
            Objects.requireNonNull(str, "packageName must not be null");
            int callingUid = Binder.getCallingUid();
            BlobStoreManagerService.this.verifyCallingPackage(callingUid, str);
            if (!BlobStoreManagerService.this.isAllowedBlobStoreAccess(callingUid, str)) {
                throw new SecurityException("Caller not allowed to open blob; callingUid=" + callingUid + ", callingPackage=" + str);
            }
            BlobStoreManagerService.this.releaseLeaseInternal(blobHandle, callingUid, str);
        }

        public void releaseAllLeases(String str) {
            Objects.requireNonNull(str, "packageName must not be null");
            int callingUid = Binder.getCallingUid();
            BlobStoreManagerService.this.verifyCallingPackage(callingUid, str);
            if (!BlobStoreManagerService.this.isAllowedBlobStoreAccess(callingUid, str)) {
                throw new SecurityException("Caller not allowed to open blob; callingUid=" + callingUid + ", callingPackage=" + str);
            }
            BlobStoreManagerService.this.releaseAllLeasesInternal(callingUid, str);
        }

        public long getRemainingLeaseQuotaBytes(String str) {
            int callingUid = Binder.getCallingUid();
            BlobStoreManagerService.this.verifyCallingPackage(callingUid, str);
            return BlobStoreManagerService.this.getRemainingLeaseQuotaBytesInternal(callingUid, str);
        }

        public void waitForIdle(final RemoteCallback remoteCallback) {
            Objects.requireNonNull(remoteCallback, "remoteCallback must not be null");
            BlobStoreManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.DUMP", "Caller is not allowed to call this; caller=" + Binder.getCallingUid());
            BlobStoreManagerService.this.mHandler.post(new Runnable() { // from class: com.android.server.blob.BlobStoreManagerService$Stub$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    BlobStoreManagerService.Stub.this.lambda$waitForIdle$1(remoteCallback);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$waitForIdle$1(final RemoteCallback remoteCallback) {
            BlobStoreManagerService.this.mBackgroundHandler.post(new Runnable() { // from class: com.android.server.blob.BlobStoreManagerService$Stub$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    BlobStoreManagerService.Stub.this.lambda$waitForIdle$0(remoteCallback);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$waitForIdle$0(final RemoteCallback remoteCallback) {
            Handler handler = BlobStoreManagerService.this.mHandler;
            Objects.requireNonNull(remoteCallback);
            handler.post(PooledLambda.obtainRunnable(new Consumer() { // from class: com.android.server.blob.BlobStoreManagerService$Stub$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    remoteCallback.sendResult((Bundle) obj);
                }
            }, (Object) null).recycleOnUse());
        }

        public List queryBlobsForUser(int i) {
            verifyCallerIsSystemUid("queryBlobsForUser");
            if (i == -2) {
                i = ActivityManager.getCurrentUser();
            }
            ((ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class)).ensureNotSpecialUser(i);
            return BlobStoreManagerService.this.queryBlobsForUserInternal(i);
        }

        public void deleteBlob(long j) {
            verifyCallerIsSystemUid("deleteBlob");
            BlobStoreManagerService.this.deleteBlobInternal(j);
        }

        public List getLeasedBlobs(String str) {
            Objects.requireNonNull(str, "packageName must not be null");
            int callingUid = Binder.getCallingUid();
            BlobStoreManagerService.this.verifyCallingPackage(callingUid, str);
            return BlobStoreManagerService.this.getLeasedBlobsInternal(callingUid, str);
        }

        public LeaseInfo getLeaseInfo(BlobHandle blobHandle, String str) {
            Objects.requireNonNull(blobHandle, "blobHandle must not be null");
            blobHandle.assertIsValid();
            Objects.requireNonNull(str, "packageName must not be null");
            int callingUid = Binder.getCallingUid();
            BlobStoreManagerService.this.verifyCallingPackage(callingUid, str);
            if (!BlobStoreManagerService.this.isAllowedBlobStoreAccess(callingUid, str)) {
                throw new SecurityException("Caller not allowed to open blob; callingUid=" + callingUid + ", callingPackage=" + str);
            }
            return BlobStoreManagerService.this.getLeaseInfoInternal(blobHandle, callingUid, str);
        }

        public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            if (DumpUtils.checkDumpAndUsageStatsPermission(BlobStoreManagerService.this.mContext, "BlobStore", printWriter)) {
                DumpArgs parse = DumpArgs.parse(strArr);
                IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "    ");
                if (parse.shouldDumpHelp()) {
                    printWriter.println("dumpsys blob_store [options]:");
                    indentingPrintWriter.increaseIndent();
                    parse.dumpArgsUsage(indentingPrintWriter);
                    indentingPrintWriter.decreaseIndent();
                    return;
                }
                synchronized (BlobStoreManagerService.this.mBlobsLock) {
                    if (parse.shouldDumpAllSections()) {
                        indentingPrintWriter.println("mCurrentMaxSessionId: " + BlobStoreManagerService.this.mCurrentMaxSessionId);
                        indentingPrintWriter.println();
                    }
                    if (parse.shouldDumpSessions()) {
                        BlobStoreManagerService.this.dumpSessionsLocked(indentingPrintWriter, parse);
                        indentingPrintWriter.println();
                    }
                    if (parse.shouldDumpBlobs()) {
                        BlobStoreManagerService.this.dumpBlobsLocked(indentingPrintWriter, parse);
                        indentingPrintWriter.println();
                    }
                }
                if (parse.shouldDumpConfig()) {
                    indentingPrintWriter.println("BlobStore config:");
                    indentingPrintWriter.increaseIndent();
                    BlobStoreConfig.dump(indentingPrintWriter, BlobStoreManagerService.this.mContext);
                    indentingPrintWriter.decreaseIndent();
                    indentingPrintWriter.println();
                }
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public int handleShellCommand(ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2, ParcelFileDescriptor parcelFileDescriptor3, String[] strArr) {
            return new BlobStoreManagerShellCommand(BlobStoreManagerService.this).exec(this, parcelFileDescriptor.getFileDescriptor(), parcelFileDescriptor2.getFileDescriptor(), parcelFileDescriptor3.getFileDescriptor(), strArr);
        }

        public final void verifyCallerIsSystemUid(String str) {
            if (UserHandle.getCallingAppId() == 1000 && ((UserManager) BlobStoreManagerService.this.mContext.getSystemService(UserManager.class)).isUserAdmin(UserHandle.getCallingUserId())) {
                return;
            }
            throw new SecurityException("Only admin user's app with system uidare allowed to call #" + str);
        }
    }

    /* loaded from: classes.dex */
    public final class DumpArgs {
        public boolean mDumpAll;
        public boolean mDumpHelp;
        public boolean mDumpUnredacted;
        public int mSelectedSectionFlags;
        public final ArrayList mDumpPackages = new ArrayList();
        public final ArrayList mDumpUids = new ArrayList();
        public final ArrayList mDumpUserIds = new ArrayList();
        public final ArrayList mDumpBlobIds = new ArrayList();

        public boolean shouldDumpSession(String str, int i, long j) {
            if (!CollectionUtils.isEmpty(this.mDumpPackages) && this.mDumpPackages.indexOf(str) < 0) {
                return false;
            }
            if (CollectionUtils.isEmpty(this.mDumpUids) || this.mDumpUids.indexOf(Integer.valueOf(i)) >= 0) {
                return CollectionUtils.isEmpty(this.mDumpBlobIds) || this.mDumpBlobIds.indexOf(Long.valueOf(j)) >= 0;
            }
            return false;
        }

        public boolean shouldDumpAllSections() {
            return this.mDumpAll || this.mSelectedSectionFlags == 0;
        }

        public void allowDumpSessions() {
            this.mSelectedSectionFlags |= 1;
        }

        public boolean shouldDumpSessions() {
            return shouldDumpAllSections() || (this.mSelectedSectionFlags & 1) != 0;
        }

        public void allowDumpBlobs() {
            this.mSelectedSectionFlags |= 2;
        }

        public boolean shouldDumpBlobs() {
            return shouldDumpAllSections() || (this.mSelectedSectionFlags & 2) != 0;
        }

        public void allowDumpConfig() {
            this.mSelectedSectionFlags |= 4;
        }

        public boolean shouldDumpConfig() {
            return shouldDumpAllSections() || (this.mSelectedSectionFlags & 4) != 0;
        }

        public boolean shouldDumpBlob(long j) {
            return CollectionUtils.isEmpty(this.mDumpBlobIds) || this.mDumpBlobIds.indexOf(Long.valueOf(j)) >= 0;
        }

        public boolean shouldDumpFull() {
            return this.mDumpUnredacted;
        }

        public boolean shouldDumpUser(int i) {
            return CollectionUtils.isEmpty(this.mDumpUserIds) || this.mDumpUserIds.indexOf(Integer.valueOf(i)) >= 0;
        }

        public boolean shouldDumpHelp() {
            return this.mDumpHelp;
        }

        public static DumpArgs parse(String[] strArr) {
            DumpArgs dumpArgs = new DumpArgs();
            if (strArr == null) {
                return dumpArgs;
            }
            int i = 0;
            while (i < strArr.length) {
                String str = strArr[i];
                if ("--all".equals(str) || "-a".equals(str)) {
                    dumpArgs.mDumpAll = true;
                } else if ("--unredacted".equals(str) || "-u".equals(str)) {
                    int callingUid = Binder.getCallingUid();
                    if (callingUid == 2000 || callingUid == 0) {
                        dumpArgs.mDumpUnredacted = true;
                    }
                } else if ("--sessions".equals(str)) {
                    dumpArgs.allowDumpSessions();
                } else if ("--blobs".equals(str)) {
                    dumpArgs.allowDumpBlobs();
                } else if ("--config".equals(str)) {
                    dumpArgs.allowDumpConfig();
                } else if ("--package".equals(str) || "-p".equals(str)) {
                    i++;
                    dumpArgs.mDumpPackages.add(getStringArgRequired(strArr, i, "packageName"));
                } else if ("--uid".equals(str)) {
                    i++;
                    dumpArgs.mDumpUids.add(Integer.valueOf(getIntArgRequired(strArr, i, "uid")));
                } else if ("--user".equals(str)) {
                    i++;
                    dumpArgs.mDumpUserIds.add(Integer.valueOf(getIntArgRequired(strArr, i, "userId")));
                } else if ("--blob".equals(str) || "-b".equals(str)) {
                    i++;
                    dumpArgs.mDumpBlobIds.add(Long.valueOf(getLongArgRequired(strArr, i, "blobId")));
                } else if ("--help".equals(str) || "-h".equals(str)) {
                    dumpArgs.mDumpHelp = true;
                } else {
                    dumpArgs.mDumpBlobIds.add(Long.valueOf(getLongArgRequired(strArr, i, "blobId")));
                }
                i++;
            }
            return dumpArgs;
        }

        public static String getStringArgRequired(String[] strArr, int i, String str) {
            if (i >= strArr.length) {
                throw new IllegalArgumentException("Missing " + str);
            }
            return strArr[i];
        }

        public static int getIntArgRequired(String[] strArr, int i, String str) {
            if (i >= strArr.length) {
                throw new IllegalArgumentException("Missing " + str);
            }
            try {
                return Integer.parseInt(strArr[i]);
            } catch (NumberFormatException unused) {
                throw new IllegalArgumentException("Invalid " + str + ": " + strArr[i]);
            }
        }

        public static long getLongArgRequired(String[] strArr, int i, String str) {
            if (i >= strArr.length) {
                throw new IllegalArgumentException("Missing " + str);
            }
            try {
                return Long.parseLong(strArr[i]);
            } catch (NumberFormatException unused) {
                throw new IllegalArgumentException("Invalid " + str + ": " + strArr[i]);
            }
        }

        public final void dumpArgsUsage(IndentingPrintWriter indentingPrintWriter) {
            indentingPrintWriter.println("--help | -h");
            printWithIndent(indentingPrintWriter, "Dump this help text");
            indentingPrintWriter.println("--sessions");
            printWithIndent(indentingPrintWriter, "Dump only the sessions info");
            indentingPrintWriter.println("--blobs");
            printWithIndent(indentingPrintWriter, "Dump only the committed blobs info");
            indentingPrintWriter.println("--config");
            printWithIndent(indentingPrintWriter, "Dump only the config values");
            indentingPrintWriter.println("--package | -p [package-name]");
            printWithIndent(indentingPrintWriter, "Dump blobs info associated with the given package");
            indentingPrintWriter.println("--uid | -u [uid]");
            printWithIndent(indentingPrintWriter, "Dump blobs info associated with the given uid");
            indentingPrintWriter.println("--user [user-id]");
            printWithIndent(indentingPrintWriter, "Dump blobs info in the given user");
            indentingPrintWriter.println("--blob | -b [session-id | blob-id]");
            printWithIndent(indentingPrintWriter, "Dump blob info corresponding to the given ID");
            indentingPrintWriter.println("--full | -f");
            printWithIndent(indentingPrintWriter, "Dump full unredacted blobs data");
        }

        public final void printWithIndent(IndentingPrintWriter indentingPrintWriter, String str) {
            indentingPrintWriter.increaseIndent();
            indentingPrintWriter.println(str);
            indentingPrintWriter.decreaseIndent();
        }
    }

    public final void registerBlobStorePuller() {
        this.mStatsManager.setPullAtomCallback(FrameworkStatsLog.BLOB_INFO, (StatsManager.PullAtomMetadata) null, BackgroundThread.getExecutor(), this.mStatsCallbackImpl);
    }

    /* loaded from: classes.dex */
    public class StatsPullAtomCallbackImpl implements StatsManager.StatsPullAtomCallback {
        public StatsPullAtomCallbackImpl() {
        }

        public int onPullAtom(int i, List list) {
            if (i == 10081) {
                return BlobStoreManagerService.this.pullBlobData(i, list);
            }
            throw new UnsupportedOperationException("Unknown tagId=" + i);
        }
    }

    public static /* synthetic */ void lambda$pullBlobData$18(List list, int i, BlobMetadata blobMetadata) {
        list.add(blobMetadata.dumpAsStatsEvent(i));
    }

    public final int pullBlobData(final int i, final List list) {
        forEachBlob(new Consumer() { // from class: com.android.server.blob.BlobStoreManagerService$$ExternalSyntheticLambda17
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                BlobStoreManagerService.lambda$pullBlobData$18(list, i, (BlobMetadata) obj);
            }
        });
        return 0;
    }

    /* loaded from: classes.dex */
    public class LocalService extends BlobStoreManagerInternal {
        public LocalService() {
        }

        @Override // com.android.server.blob.BlobStoreManagerInternal
        public void onIdleMaintenance() {
            BlobStoreManagerService.this.runIdleMaintenance();
        }
    }

    /* loaded from: classes.dex */
    class Injector {
        public Handler initializeMessageHandler() {
            return BlobStoreManagerService.m3774$$Nest$sminitializeMessageHandler();
        }

        public Handler getBackgroundHandler() {
            return BackgroundThread.getHandler();
        }
    }
}
