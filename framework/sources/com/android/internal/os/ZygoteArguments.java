package com.android.internal.os;

import java.io.EOFException;
import java.util.ArrayList;

/* loaded from: classes5.dex */
class ZygoteArguments {
    boolean mAbiListQuery;
    String[] mAllowlistedDataInfoList;
    String[] mApiDenylistExemptions;
    String mAppDataDir;
    boolean mBindMountAppDataDirs;
    boolean mBindMountAppStorageDirs;
    boolean mBootCompleted;
    private boolean mCapabilitiesSpecified;
    long mEffectiveCapabilities;
    boolean mGidSpecified;
    int[] mGids;
    String mInstructionSet;
    String mInvokeWith;
    boolean mIsTopApp;
    String mNiceName;
    String mPackageName;
    long mPermittedCapabilities;
    boolean mPidQuery;
    String[] mPkgDataInfoList;
    String mPreloadApp;
    boolean mPreloadDefault;
    String mPreloadPackage;
    String mPreloadPackageCacheKey;
    String mPreloadPackageLibFileName;
    String mPreloadPackageLibs;
    ArrayList<int[]> mRLimits;
    String[] mRemainingArgs;
    int mRuntimeFlags;
    String mSeInfo;
    private boolean mSeInfoSpecified;
    boolean mStartChildZygote;
    int mTargetSdkVersion;
    private boolean mTargetSdkVersionSpecified;
    boolean mUidSpecified;
    boolean mUsapPoolEnabled;
    int mUid = 0;
    int mGid = 0;
    int mMountExternal = 0;
    boolean mUsapPoolStatusSpecified = false;
    int mHiddenApiAccessLogSampleRate = -1;
    int mHiddenApiAccessStatslogSampleRate = -1;
    long[] mDisabledCompatChanges = null;

    private ZygoteArguments(ZygoteCommandBuffer args, int argCount) throws IllegalArgumentException, EOFException {
        parseArgs(args, argCount);
    }

    public static ZygoteArguments getInstance(ZygoteCommandBuffer args) throws IllegalArgumentException, EOFException {
        int argCount = args.getCount();
        if (argCount == 0) {
            return null;
        }
        return new ZygoteArguments(args, argCount);
    }

    /* JADX WARN: Code restructure failed: missing block: B:246:0x0395, code lost:            if (r12.mBootCompleted == false) goto L200;     */
    /* JADX WARN: Code restructure failed: missing block: B:247:0x0397, code lost:            if (r14 > r1) goto L198;     */
    /* JADX WARN: Code restructure failed: missing block: B:249:0x03a2, code lost:            throw new java.lang.IllegalArgumentException("Unexpected arguments after --boot-completed");     */
    /* JADX WARN: Code restructure failed: missing block: B:251:0x040f, code lost:            if (r12.mStartChildZygote == false) goto L244;     */
    /* JADX WARN: Code restructure failed: missing block: B:252:0x0411, code lost:            r5 = false;        r6 = r12.mRemainingArgs;        r7 = r6.length;     */
    /* JADX WARN: Code restructure failed: missing block: B:253:0x0415, code lost:            if (r4 >= r7) goto L305;     */
    /* JADX WARN: Code restructure failed: missing block: B:255:0x041f, code lost:            if (r6[r4].startsWith(com.android.internal.os.Zygote.CHILD_ZYGOTE_SOCKET_NAME_ARG) == false) goto L239;     */
    /* JADX WARN: Code restructure failed: missing block: B:256:0x0423, code lost:            r4 = r4 + 1;     */
    /* JADX WARN: Code restructure failed: missing block: B:258:0x0421, code lost:            r5 = true;     */
    /* JADX WARN: Code restructure failed: missing block: B:259:0x0426, code lost:            if (r5 == false) goto L242;     */
    /* JADX WARN: Code restructure failed: missing block: B:261:0x0430, code lost:            throw new java.lang.IllegalArgumentException("--start-child-zygote specified without --zygote-socket=");     */
    /* JADX WARN: Code restructure failed: missing block: B:262:?, code lost:            return;     */
    /* JADX WARN: Code restructure failed: missing block: B:264:0x0431, code lost:            return;     */
    /* JADX WARN: Code restructure failed: missing block: B:266:0x03a5, code lost:            if (r12.mAbiListQuery != false) goto L231;     */
    /* JADX WARN: Code restructure failed: missing block: B:268:0x03a9, code lost:            if (r12.mPidQuery == false) goto L205;     */
    /* JADX WARN: Code restructure failed: missing block: B:270:0x03ae, code lost:            if (r12.mPreloadPackage == null) goto L211;     */
    /* JADX WARN: Code restructure failed: missing block: B:271:0x03b0, code lost:            if (r14 > r1) goto L209;     */
    /* JADX WARN: Code restructure failed: missing block: B:273:0x03ba, code lost:            throw new java.lang.IllegalArgumentException("Unexpected arguments after --preload-package.");     */
    /* JADX WARN: Code restructure failed: missing block: B:275:0x03bd, code lost:            if (r12.mPreloadApp == null) goto L217;     */
    /* JADX WARN: Code restructure failed: missing block: B:276:0x03bf, code lost:            if (r14 > r1) goto L215;     */
    /* JADX WARN: Code restructure failed: missing block: B:278:0x03c9, code lost:            throw new java.lang.IllegalArgumentException("Unexpected arguments after --preload-app.");     */
    /* JADX WARN: Code restructure failed: missing block: B:279:0x03ca, code lost:            if (r3 == false) goto L232;     */
    /* JADX WARN: Code restructure failed: missing block: B:280:0x03cc, code lost:            if (r2 != false) goto L225;     */
    /* JADX WARN: Code restructure failed: missing block: B:281:0x03ce, code lost:            r5 = new java.lang.StringBuilder().append("Unexpected argument : ");     */
    /* JADX WARN: Code restructure failed: missing block: B:282:0x03db, code lost:            if (r0 != null) goto L222;     */
    /* JADX WARN: Code restructure failed: missing block: B:283:0x03dd, code lost:            r6 = r13.nextArg();     */
    /* JADX WARN: Code restructure failed: missing block: B:285:0x03ee, code lost:            throw new java.lang.IllegalArgumentException(r5.append(r6).toString());     */
    /* JADX WARN: Code restructure failed: missing block: B:286:0x03e2, code lost:            r6 = r0;     */
    /* JADX WARN: Code restructure failed: missing block: B:287:0x03ef, code lost:            r5 = new java.lang.String[r14 - r1];        r12.mRemainingArgs = r5;        r6 = 0;     */
    /* JADX WARN: Code restructure failed: missing block: B:288:0x03f6, code lost:            if (r0 == null) goto L308;     */
    /* JADX WARN: Code restructure failed: missing block: B:289:0x03f8, code lost:            r5[0] = r0;        r6 = 0 + 1;     */
    /* JADX WARN: Code restructure failed: missing block: B:292:0x03fe, code lost:            if (r6 >= (r14 - r1)) goto L307;     */
    /* JADX WARN: Code restructure failed: missing block: B:293:0x0400, code lost:            r12.mRemainingArgs[r6] = r13.nextArg();        r6 = r6 + 1;     */
    /* JADX WARN: Code restructure failed: missing block: B:295:0x040b, code lost:            if (r14 > r1) goto L245;     */
    /* JADX WARN: Code restructure failed: missing block: B:297:0x0439, code lost:            throw new java.lang.IllegalArgumentException("Unexpected arguments after --query-abi-list.");     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void parseArgs(com.android.internal.os.ZygoteCommandBuffer r13, int r14) throws java.lang.IllegalArgumentException, java.io.EOFException {
        /*
            Method dump skipped, instructions count: 1082
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.os.ZygoteArguments.parseArgs(com.android.internal.os.ZygoteCommandBuffer, int):void");
    }

    private static String getAssignmentValue(String arg) {
        return arg.substring(arg.indexOf(61) + 1);
    }

    private static String[] getAssignmentList(String arg) {
        return getAssignmentValue(arg).split(",");
    }
}
