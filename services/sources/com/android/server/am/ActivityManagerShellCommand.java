package com.android.server.am;

import android.app.ActivityManager;
import android.app.ActivityManagerInternal;
import android.app.ActivityTaskManager;
import android.app.AppGlobals;
import android.app.BroadcastOptions;
import android.app.IActivityController;
import android.app.IActivityManager;
import android.app.IActivityTaskManager;
import android.app.IApplicationThread;
import android.app.IProcessObserver;
import android.app.IStopUserCallback;
import android.app.KeyguardManager;
import android.app.ProfilerInfo;
import android.app.UidObserver;
import android.app.UserSwitchObserver;
import android.app.usage.AppStandbyInfo;
import android.app.usage.ConfigurationStats;
import android.app.usage.IUsageStatsManager;
import android.content.ComponentName;
import android.content.IIntentReceiver;
import android.content.Intent;
import android.content.pm.ConfigurationInfo;
import android.content.pm.FeatureInfo;
import android.content.pm.IPackageManager;
import android.content.pm.PackageManager;
import android.content.pm.ParceledListSlice;
import android.content.pm.SharedLibraryInfo;
import android.content.pm.UserInfo;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.hardware.display.DisplayManager;
import android.net.INetd;
import android.opengl.GLES10;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IProgressListener;
import android.os.ParcelFileDescriptor;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.ShellCommand;
import android.os.StrictMode;
import android.os.SystemClock;
import android.os.Trace;
import android.os.UserHandle;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.DebugUtils;
import android.util.DisplayMetrics;
import android.util.TeeWriter;
import android.util.proto.ProtoOutputStream;
import android.view.Display;
import com.android.internal.util.MemInfoReader;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.LocalServices;
import com.android.server.am.ActivityManagerService;
import com.android.server.am.CachedAppOptimizer;
import com.android.server.am.nano.Capabilities;
import com.android.server.am.nano.Capability;
import com.android.server.pm.PackageManagerShellCommandDataLoader;
import com.android.server.pm.UserManagerInternal;
import com.android.server.utils.Slogf;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.rune.CoreRune;
import dalvik.annotation.optimization.NeverCompile;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.URISyntaxException;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;

/* loaded from: classes.dex */
public final class ActivityManagerShellCommand extends ShellCommand {
    public int mActivityType;
    public String mAgent;
    public boolean mAsync;
    public boolean mAttachAgentDuringBind;
    public boolean mAutoStop;
    public BroadcastOptions mBroadcastOptions;
    public int mClockType;
    public boolean mDismissKeyguard;
    public int mDisplayId;
    public final boolean mDumping;
    public boolean mForceLaunchTaskOnHome;
    public int mForceWindowingMode;
    public final IActivityManager mInterface;
    public final ActivityManagerService mInternal;
    public boolean mIsLockTask;
    public boolean mIsTaskOverlay;
    public String mProfileFile;
    public String mReceiverPermission;
    public int mSamplingInterval;
    public boolean mShowSplashScreen;
    public boolean mStreaming;
    public int mTaskDisplayAreaFeatureId;
    public int mTaskId;
    public final IActivityTaskManager mTaskInterface;
    public int mUserId;
    public int mWindowingMode;
    public static final DateTimeFormatter LOG_NAME_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss", Locale.ROOT);
    public static final String[] CAPABILITIES = {"start.suspend"};
    public int mStartFlags = 0;
    public boolean mWaitOption = false;
    public boolean mStopOption = false;
    public int mRepeat = 0;
    public final IPackageManager mPm = AppGlobals.getPackageManager();

    public ActivityManagerShellCommand(ActivityManagerService activityManagerService, boolean z) {
        this.mInterface = activityManagerService;
        this.mTaskInterface = activityManagerService.mActivityTaskManager;
        this.mInternal = activityManagerService;
        this.mDumping = z;
    }

    public int onCommand(String str) {
        char c;
        if (str == null) {
            return handleDefaultCommands(str);
        }
        PrintWriter outPrintWriter = getOutPrintWriter();
        try {
            switch (str.hashCode()) {
                case -2121667104:
                    if (str.equals("dumpheap")) {
                        c = 17;
                        break;
                    }
                    c = 65535;
                    break;
                case -1969672196:
                    if (str.equals("set-debug-app")) {
                        c = 18;
                        break;
                    }
                    c = 65535;
                    break;
                case -1864717225:
                    if (str.equals("set-deterministic-uid-idle")) {
                        c = '!';
                        break;
                    }
                    c = 65535;
                    break;
                case -1860393403:
                    if (str.equals("get-isolated-pids")) {
                        c = 'P';
                        break;
                    }
                    c = 65535;
                    break;
                case -1719979774:
                    if (str.equals("get-inactive")) {
                        c = '9';
                        break;
                    }
                    c = 65535;
                    break;
                case -1710503333:
                    if (str.equals("package-importance")) {
                        c = '(';
                        break;
                    }
                    c = 65535;
                    break;
                case -1667670943:
                    if (str.equals("get-standby-bucket")) {
                        c = ';';
                        break;
                    }
                    c = 65535;
                    break;
                case -1619282346:
                    if (str.equals("start-user")) {
                        c = '.';
                        break;
                    }
                    c = 65535;
                    break;
                case -1618876223:
                    if (str.equals(INetd.IF_FLAG_BROADCAST)) {
                        c = '\n';
                        break;
                    }
                    c = 65535;
                    break;
                case -1514943892:
                    if (str.equals("list-displays-for-starting-users")) {
                        c = 'X';
                        break;
                    }
                    c = 65535;
                    break;
                case -1487597642:
                    if (str.equals("capabilities")) {
                        c = 'Z';
                        break;
                    }
                    c = 65535;
                    break;
                case -1470725846:
                    if (str.equals("reset-dropbox-rate-limiter")) {
                        c = 'W';
                        break;
                    }
                    c = 65535;
                    break;
                case -1354812542:
                    if (str.equals("compat")) {
                        c = 'L';
                        break;
                    }
                    c = 65535;
                    break;
                case -1324660647:
                    if (str.equals("suppress-resize-config-changes")) {
                        c = '7';
                        break;
                    }
                    c = 65535;
                    break;
                case -1303445945:
                    if (str.equals("send-trim-memory")) {
                        c = '<';
                        break;
                    }
                    c = 65535;
                    break;
                case -1275145137:
                    if (str.equals("wait-for-broadcast-barrier")) {
                        c = 'G';
                        break;
                    }
                    c = 65535;
                    break;
                case -1266402665:
                    if (str.equals("freeze")) {
                        c = '\f';
                        break;
                    }
                    c = 65535;
                    break;
                case -1182154244:
                    if (str.equals("set-foreground-service-delegate")) {
                        c = 'Y';
                        break;
                    }
                    c = 65535;
                    break;
                case -1131287478:
                    if (str.equals("start-service")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case -1002578147:
                    if (str.equals("get-uid-state")) {
                        c = '5';
                        break;
                    }
                    c = 65535;
                    break;
                case -965273485:
                    if (str.equals("stopservice")) {
                        c = '\b';
                        break;
                    }
                    c = 65535;
                    break;
                case -930080590:
                    if (str.equals("startfgservice")) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case -907667276:
                    if (str.equals("unlock-user")) {
                        c = '/';
                        break;
                    }
                    c = 65535;
                    break;
                case -892396682:
                    if (str.equals("start-foreground-service")) {
                        c = 6;
                        break;
                    }
                    c = 65535;
                    break;
                case -878494906:
                    if (str.equals("set-bg-restriction-level")) {
                        c = 'T';
                        break;
                    }
                    c = 65535;
                    break;
                case -870018278:
                    if (str.equals("to-uri")) {
                        c = ')';
                        break;
                    }
                    c = 65535;
                    break;
                case -812219210:
                    if (str.equals("get-current-user")) {
                        c = '-';
                        break;
                    }
                    c = 65535;
                    break;
                case -747637291:
                    if (str.equals("set-standby-bucket")) {
                        c = ':';
                        break;
                    }
                    c = 65535;
                    break;
                case -699625063:
                    if (str.equals("get-config")) {
                        c = '6';
                        break;
                    }
                    c = 65535;
                    break;
                case -606123342:
                    if (str.equals("kill-all")) {
                        c = 31;
                        break;
                    }
                    c = 65535;
                    break;
                case -548621938:
                    if (str.equals("is-user-stopped")) {
                        c = '1';
                        break;
                    }
                    c = 65535;
                    break;
                case -541939658:
                    if (str.equals("observe-foreground-process")) {
                        c = 'V';
                        break;
                    }
                    c = 65535;
                    break;
                case -443938379:
                    if (str.equals("fgs-notification-rate-limit")) {
                        c = 28;
                        break;
                    }
                    c = 65535;
                    break;
                case -387147436:
                    if (str.equals("track-associations")) {
                        c = '3';
                        break;
                    }
                    c = 65535;
                    break;
                case -379899280:
                    if (str.equals("unfreeze")) {
                        c = '\r';
                        break;
                    }
                    c = 65535;
                    break;
                case -354890749:
                    if (str.equals("screen-compat")) {
                        c = '\'';
                        break;
                    }
                    c = 65535;
                    break;
                case -309425751:
                    if (str.equals("profile")) {
                        c = 16;
                        break;
                    }
                    c = 65535;
                    break;
                case -225973678:
                    if (str.equals("service-restart-backoff")) {
                        c = 'O';
                        break;
                    }
                    c = 65535;
                    break;
                case -170987146:
                    if (str.equals("set-inactive")) {
                        c = '8';
                        break;
                    }
                    c = 65535;
                    break;
                case -149941524:
                    if (str.equals("list-bg-exemptions-config")) {
                        c = 'S';
                        break;
                    }
                    c = 65535;
                    break;
                case -146027423:
                    if (str.equals("watch-uids")) {
                        c = '#';
                        break;
                    }
                    c = 65535;
                    break;
                case -138040195:
                    if (str.equals("clear-exit-info")) {
                        c = 23;
                        break;
                    }
                    c = 65535;
                    break;
                case -100644880:
                    if (str.equals("startforegroundservice")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case -74413870:
                    if (str.equals("get-bg-restriction-level")) {
                        c = 'U';
                        break;
                    }
                    c = 65535;
                    break;
                case -27715536:
                    if (str.equals("make-uid-idle")) {
                        c = ' ';
                        break;
                    }
                    c = 65535;
                    break;
                case 3194994:
                    if (str.equals("hang")) {
                        c = '$';
                        break;
                    }
                    c = 65535;
                    break;
                case 3291998:
                    if (str.equals("kill")) {
                        c = 30;
                        break;
                    }
                    c = 65535;
                    break;
                case 3552645:
                    if (str.equals("task")) {
                        c = '?';
                        break;
                    }
                    c = 65535;
                    break;
                case 88586660:
                    if (str.equals("force-stop")) {
                        c = 25;
                        break;
                    }
                    c = 65535;
                    break;
                case 94921639:
                    if (str.equals("crash")) {
                        c = 29;
                        break;
                    }
                    c = 65535;
                    break;
                case 109757064:
                    if (str.equals("stack")) {
                        c = '>';
                        break;
                    }
                    c = 65535;
                    break;
                case 109757538:
                    if (str.equals("start")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 113399775:
                    if (str.equals("write")) {
                        c = '@';
                        break;
                    }
                    c = 65535;
                    break;
                case 135017371:
                    if (str.equals("memory-factor")) {
                        c = 'N';
                        break;
                    }
                    c = 65535;
                    break;
                case 185053203:
                    if (str.equals("startservice")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 237240942:
                    if (str.equals("to-app-uri")) {
                        c = '+';
                        break;
                    }
                    c = 65535;
                    break;
                case 549617690:
                    if (str.equals("start-activity")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 622433197:
                    if (str.equals("untrack-associations")) {
                        c = '4';
                        break;
                    }
                    c = 65535;
                    break;
                case 661133534:
                    if (str.equals("wait-for-application-barrier")) {
                        c = 'H';
                        break;
                    }
                    c = 65535;
                    break;
                case 667014829:
                    if (str.equals("bug-report")) {
                        c = 24;
                        break;
                    }
                    c = 65535;
                    break;
                case 680834441:
                    if (str.equals("supports-split-screen-multi-window")) {
                        c = 'C';
                        break;
                    }
                    c = 65535;
                    break;
                case 723112852:
                    if (str.equals("trace-ipc")) {
                        c = 15;
                        break;
                    }
                    c = 65535;
                    break;
                case 764545184:
                    if (str.equals("supports-multiwindow")) {
                        c = 'B';
                        break;
                    }
                    c = 65535;
                    break;
                case 782722708:
                    if (str.equals("set-bg-abusive-uids")) {
                        c = 'R';
                        break;
                    }
                    c = 65535;
                    break;
                case 808179021:
                    if (str.equals("to-intent-uri")) {
                        c = '*';
                        break;
                    }
                    c = 65535;
                    break;
                case 810242677:
                    if (str.equals("set-watch-heap")) {
                        c = 21;
                        break;
                    }
                    c = 65535;
                    break;
                case 817137578:
                    if (str.equals("clear-watch-heap")) {
                        c = 22;
                        break;
                    }
                    c = 65535;
                    break;
                case 822490030:
                    if (str.equals("set-agent-app")) {
                        c = 19;
                        break;
                    }
                    c = 65535;
                    break;
                case 847202110:
                    if (str.equals("clear-ignore-delivery-group-policy")) {
                        c = 'K';
                        break;
                    }
                    c = 65535;
                    break;
                case 900455412:
                    if (str.equals("start-fg-service")) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case 950483747:
                    if (str.equals("compact")) {
                        c = 11;
                        break;
                    }
                    c = 65535;
                    break;
                case 1024703869:
                    if (str.equals("attach-agent")) {
                        c = 'A';
                        break;
                    }
                    c = 65535;
                    break;
                case 1070798153:
                    if (str.equals("set-ignore-delivery-group-policy")) {
                        c = 'J';
                        break;
                    }
                    c = 65535;
                    break;
                case 1078591527:
                    if (str.equals("clear-debug-app")) {
                        c = 20;
                        break;
                    }
                    c = 65535;
                    break;
                case 1097506319:
                    if (str.equals("restart")) {
                        c = '%';
                        break;
                    }
                    c = 65535;
                    break;
                case 1129261387:
                    if (str.equals("update-appinfo")) {
                        c = 'D';
                        break;
                    }
                    c = 65535;
                    break;
                case 1147479778:
                    if (str.equals("wait-for-broadcast-dispatch")) {
                        c = 'I';
                        break;
                    }
                    c = 65535;
                    break;
                case 1180451466:
                    if (str.equals("refresh-settings-cache")) {
                        c = 'M';
                        break;
                    }
                    c = 65535;
                    break;
                case 1219773618:
                    if (str.equals("get-started-user-state")) {
                        c = '2';
                        break;
                    }
                    c = 65535;
                    break;
                case 1236319578:
                    if (str.equals("monitor")) {
                        c = '\"';
                        break;
                    }
                    c = 65535;
                    break;
                case 1395483623:
                    if (str.equals("instrument")) {
                        c = 14;
                        break;
                    }
                    c = 65535;
                    break;
                case 1583986358:
                    if (str.equals("stop-user")) {
                        c = '0';
                        break;
                    }
                    c = 65535;
                    break;
                case 1618908732:
                    if (str.equals("wait-for-broadcast-idle")) {
                        c = 'F';
                        break;
                    }
                    c = 65535;
                    break;
                case 1671764162:
                    if (str.equals("display")) {
                        c = '=';
                        break;
                    }
                    c = 65535;
                    break;
                case 1713645014:
                    if (str.equals("stop-app")) {
                        c = 26;
                        break;
                    }
                    c = 65535;
                    break;
                case 1768693408:
                    if (str.equals("set-stop-user-on-switch")) {
                        c = 'Q';
                        break;
                    }
                    c = 65535;
                    break;
                case 1852789518:
                    if (str.equals("no-home-screen")) {
                        c = 'E';
                        break;
                    }
                    c = 65535;
                    break;
                case 1861559962:
                    if (str.equals("idle-maintenance")) {
                        c = PackageManagerShellCommandDataLoader.ARGS_DELIM;
                        break;
                    }
                    c = 65535;
                    break;
                case 1863290858:
                    if (str.equals("stop-service")) {
                        c = '\t';
                        break;
                    }
                    c = 65535;
                    break;
                case 2030969636:
                    if (str.equals("clear-recent-apps")) {
                        c = 27;
                        break;
                    }
                    c = 65535;
                    break;
                case 2083239620:
                    if (str.equals("switch-user")) {
                        c = ',';
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                case 1:
                    if (CoreRune.BAIDU_CARLIFE) {
                        return runStartActivityForCarlife(outPrintWriter);
                    }
                    return runStartActivity(outPrintWriter);
                case 2:
                case 3:
                    return runStartService(outPrintWriter, false);
                case 4:
                case 5:
                case 6:
                case 7:
                    return runStartService(outPrintWriter, true);
                case '\b':
                case '\t':
                    return runStopService(outPrintWriter);
                case '\n':
                    return runSendBroadcast(outPrintWriter);
                case 11:
                    return runCompact(outPrintWriter);
                case '\f':
                    return runFreeze(outPrintWriter);
                case '\r':
                    return runUnfreeze(outPrintWriter);
                case 14:
                    getOutPrintWriter().println("Error: must be invoked through 'am instrument'.");
                    return -1;
                case 15:
                    return runTraceIpc(outPrintWriter);
                case 16:
                    return runProfile(outPrintWriter);
                case 17:
                    return runDumpHeap(outPrintWriter);
                case 18:
                    return runSetDebugApp(outPrintWriter);
                case 19:
                    return runSetAgentApp(outPrintWriter);
                case 20:
                    return runClearDebugApp(outPrintWriter);
                case 21:
                    return runSetWatchHeap(outPrintWriter);
                case 22:
                    return runClearWatchHeap(outPrintWriter);
                case 23:
                    return runClearExitInfo(outPrintWriter);
                case 24:
                    return runBugReport(outPrintWriter);
                case 25:
                    return runForceStop(outPrintWriter);
                case 26:
                    return runStopApp(outPrintWriter);
                case 27:
                    return runClearRecentApps(outPrintWriter);
                case 28:
                    return runFgsNotificationRateLimit(outPrintWriter);
                case 29:
                    return runCrash(outPrintWriter);
                case 30:
                    return runKill(outPrintWriter);
                case 31:
                    return runKillAll(outPrintWriter);
                case ' ':
                    return runMakeIdle(outPrintWriter);
                case '!':
                    return runSetDeterministicUidIdle(outPrintWriter);
                case '\"':
                    return runMonitor(outPrintWriter);
                case '#':
                    return runWatchUids(outPrintWriter);
                case '$':
                    return runHang(outPrintWriter);
                case '%':
                    return runRestart(outPrintWriter);
                case '&':
                    return runIdleMaintenance(outPrintWriter);
                case '\'':
                    return runScreenCompat(outPrintWriter);
                case '(':
                    return runPackageImportance(outPrintWriter);
                case ')':
                    return runToUri(outPrintWriter, 0);
                case '*':
                    return runToUri(outPrintWriter, 1);
                case '+':
                    return runToUri(outPrintWriter, 2);
                case ',':
                    return runSwitchUser(outPrintWriter);
                case '-':
                    return runGetCurrentUser(outPrintWriter);
                case '.':
                    return runStartUser(outPrintWriter);
                case '/':
                    return runUnlockUser(outPrintWriter);
                case '0':
                    return runStopUser(outPrintWriter);
                case '1':
                    return runIsUserStopped(outPrintWriter);
                case '2':
                    return runGetStartedUserState(outPrintWriter);
                case '3':
                    return runTrackAssociations(outPrintWriter);
                case '4':
                    return runUntrackAssociations(outPrintWriter);
                case '5':
                    return getUidState(outPrintWriter);
                case '6':
                    return runGetConfig(outPrintWriter);
                case '7':
                    return runSuppressResizeConfigChanges(outPrintWriter);
                case '8':
                    return runSetInactive(outPrintWriter);
                case '9':
                    return runGetInactive(outPrintWriter);
                case ':':
                    return runSetStandbyBucket(outPrintWriter);
                case ';':
                    return runGetStandbyBucket(outPrintWriter);
                case '<':
                    return runSendTrimMemory(outPrintWriter);
                case '=':
                    return runDisplay(outPrintWriter);
                case '>':
                    return runStack(outPrintWriter);
                case '?':
                    return runTask(outPrintWriter);
                case '@':
                    return runWrite(outPrintWriter);
                case 'A':
                    return runAttachAgent(outPrintWriter);
                case 'B':
                    return runSupportsMultiwindow(outPrintWriter);
                case 'C':
                    return runSupportsSplitScreenMultiwindow(outPrintWriter);
                case 'D':
                    return runUpdateApplicationInfo(outPrintWriter);
                case 'E':
                    return runNoHomeScreen(outPrintWriter);
                case 'F':
                    return runWaitForBroadcastIdle(outPrintWriter);
                case 'G':
                    return runWaitForBroadcastBarrier(outPrintWriter);
                case 'H':
                    return runWaitForApplicationBarrier(outPrintWriter);
                case 'I':
                    return runWaitForBroadcastDispatch(outPrintWriter);
                case 'J':
                    return runSetIgnoreDeliveryGroupPolicy(outPrintWriter);
                case 'K':
                    return runClearIgnoreDeliveryGroupPolicy(outPrintWriter);
                case 'L':
                    return runCompat(outPrintWriter);
                case 'M':
                    return runRefreshSettingsCache();
                case 'N':
                    return runMemoryFactor(outPrintWriter);
                case 'O':
                    return runServiceRestartBackoff(outPrintWriter);
                case 'P':
                    return runGetIsolatedProcesses(outPrintWriter);
                case 'Q':
                    return runSetStopUserOnSwitch(outPrintWriter);
                case 'R':
                    return runSetBgAbusiveUids(outPrintWriter);
                case 'S':
                    return runListBgExemptionsConfig(outPrintWriter);
                case 'T':
                    return runSetBgRestrictionLevel(outPrintWriter);
                case 'U':
                    return runGetBgRestrictionLevel(outPrintWriter);
                case 'V':
                    return runGetCurrentForegroundProcess(outPrintWriter, this.mInternal, this.mTaskInterface);
                case 'W':
                    return runResetDropboxRateLimiter();
                case 'X':
                    return runListDisplaysForStartingUsers(outPrintWriter);
                case 'Y':
                    return runSetForegroundServiceDelegate(outPrintWriter);
                case 'Z':
                    return runCapabilities(outPrintWriter);
                default:
                    return handleDefaultCommands(str);
            }
        } catch (RemoteException e) {
            outPrintWriter.println("Remote exception: " + e);
            return -1;
        }
    }

    public int runCapabilities(PrintWriter printWriter) {
        PrintWriter errPrintWriter = getErrPrintWriter();
        boolean z = false;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption == null) {
                if (z) {
                    Capabilities capabilities = new Capabilities();
                    capabilities.values = new Capability[CAPABILITIES.length];
                    int i = 0;
                    while (true) {
                        String[] strArr = CAPABILITIES;
                        if (i < strArr.length) {
                            Capability capability = new Capability();
                            capability.name = strArr[i];
                            capabilities.values[i] = capability;
                            i++;
                        } else {
                            try {
                                break;
                            } catch (IOException unused) {
                                printWriter.println("Error while serializing capabilities protobuffer");
                            }
                        }
                    }
                    getRawOutputStream().write(Capabilities.toByteArray(capabilities));
                } else {
                    printWriter.println("Format: 1");
                    for (String str : CAPABILITIES) {
                        printWriter.println(str);
                    }
                }
                return 0;
            }
            if (!nextOption.equals("--protobuf")) {
                errPrintWriter.println("Error: Unknown option: " + nextOption);
                return -1;
            }
            z = true;
        }
    }

    public final Intent makeIntent(int i) {
        this.mStartFlags = 0;
        this.mWaitOption = false;
        this.mStopOption = false;
        this.mRepeat = 0;
        this.mProfileFile = null;
        this.mSamplingInterval = 0;
        this.mAutoStop = false;
        this.mStreaming = false;
        this.mUserId = i;
        this.mDisplayId = -1;
        this.mTaskDisplayAreaFeatureId = -1;
        this.mWindowingMode = 0;
        this.mForceWindowingMode = 0;
        this.mActivityType = 0;
        this.mTaskId = -1;
        this.mIsTaskOverlay = false;
        this.mIsLockTask = false;
        this.mAsync = false;
        this.mBroadcastOptions = null;
        return Intent.parseCommandArgs(this, new Intent.CommandOptionHandler() { // from class: com.android.server.am.ActivityManagerShellCommand.1
            public boolean handleOption(String str, ShellCommand shellCommand) {
                if (str.equals("-D")) {
                    ActivityManagerShellCommand.this.mStartFlags |= 2;
                } else if (str.equals("--suspend")) {
                    ActivityManagerShellCommand.this.mStartFlags |= 16;
                } else if (str.equals("-N")) {
                    ActivityManagerShellCommand.this.mStartFlags |= 8;
                } else if (str.equals("-W")) {
                    ActivityManagerShellCommand.this.mWaitOption = true;
                } else if (str.equals("-P")) {
                    ActivityManagerShellCommand activityManagerShellCommand = ActivityManagerShellCommand.this;
                    activityManagerShellCommand.mProfileFile = activityManagerShellCommand.getNextArgRequired();
                    ActivityManagerShellCommand.this.mAutoStop = true;
                } else if (str.equals("--start-profiler")) {
                    ActivityManagerShellCommand activityManagerShellCommand2 = ActivityManagerShellCommand.this;
                    activityManagerShellCommand2.mProfileFile = activityManagerShellCommand2.getNextArgRequired();
                    ActivityManagerShellCommand.this.mAutoStop = false;
                } else if (str.equals("--sampling")) {
                    ActivityManagerShellCommand activityManagerShellCommand3 = ActivityManagerShellCommand.this;
                    activityManagerShellCommand3.mSamplingInterval = Integer.parseInt(activityManagerShellCommand3.getNextArgRequired());
                } else if (str.equals("--clock-type")) {
                    ActivityManagerShellCommand.this.mClockType = ProfilerInfo.getClockTypeFromString(ActivityManagerShellCommand.this.getNextArgRequired());
                } else if (str.equals("--streaming")) {
                    ActivityManagerShellCommand.this.mStreaming = true;
                } else if (str.equals("--attach-agent")) {
                    if (ActivityManagerShellCommand.this.mAgent != null) {
                        shellCommand.getErrPrintWriter().println("Multiple --attach-agent(-bind) not supported");
                        return false;
                    }
                    ActivityManagerShellCommand activityManagerShellCommand4 = ActivityManagerShellCommand.this;
                    activityManagerShellCommand4.mAgent = activityManagerShellCommand4.getNextArgRequired();
                    ActivityManagerShellCommand.this.mAttachAgentDuringBind = false;
                } else if (str.equals("--attach-agent-bind")) {
                    if (ActivityManagerShellCommand.this.mAgent != null) {
                        shellCommand.getErrPrintWriter().println("Multiple --attach-agent(-bind) not supported");
                        return false;
                    }
                    ActivityManagerShellCommand activityManagerShellCommand5 = ActivityManagerShellCommand.this;
                    activityManagerShellCommand5.mAgent = activityManagerShellCommand5.getNextArgRequired();
                    ActivityManagerShellCommand.this.mAttachAgentDuringBind = true;
                } else if (str.equals("-R")) {
                    ActivityManagerShellCommand activityManagerShellCommand6 = ActivityManagerShellCommand.this;
                    activityManagerShellCommand6.mRepeat = Integer.parseInt(activityManagerShellCommand6.getNextArgRequired());
                } else if (str.equals("-S")) {
                    ActivityManagerShellCommand.this.mStopOption = true;
                } else if (str.equals("--track-allocation")) {
                    ActivityManagerShellCommand.this.mStartFlags |= 4;
                } else if (str.equals("--user")) {
                    ActivityManagerShellCommand activityManagerShellCommand7 = ActivityManagerShellCommand.this;
                    activityManagerShellCommand7.mUserId = UserHandle.parseUserArg(activityManagerShellCommand7.getNextArgRequired());
                } else if (str.equals("--receiver-permission")) {
                    ActivityManagerShellCommand activityManagerShellCommand8 = ActivityManagerShellCommand.this;
                    activityManagerShellCommand8.mReceiverPermission = activityManagerShellCommand8.getNextArgRequired();
                } else if (str.equals("--display")) {
                    ActivityManagerShellCommand activityManagerShellCommand9 = ActivityManagerShellCommand.this;
                    activityManagerShellCommand9.mDisplayId = Integer.parseInt(activityManagerShellCommand9.getNextArgRequired());
                } else if (str.equals("--task-display-area-feature-id")) {
                    ActivityManagerShellCommand activityManagerShellCommand10 = ActivityManagerShellCommand.this;
                    activityManagerShellCommand10.mTaskDisplayAreaFeatureId = Integer.parseInt(activityManagerShellCommand10.getNextArgRequired());
                } else if (str.equals("--windowingMode")) {
                    ActivityManagerShellCommand activityManagerShellCommand11 = ActivityManagerShellCommand.this;
                    activityManagerShellCommand11.mWindowingMode = Integer.parseInt(activityManagerShellCommand11.getNextArgRequired());
                } else if (str.equals("--forceWindowingMode")) {
                    ActivityManagerShellCommand activityManagerShellCommand12 = ActivityManagerShellCommand.this;
                    activityManagerShellCommand12.mForceWindowingMode = Integer.parseInt(activityManagerShellCommand12.getNextArgRequired());
                } else if (CoreRune.MT_NEW_DEX_LAUNCH_POLICY && str.equals("--forceLaunchTaskOnHome")) {
                    ActivityManagerShellCommand.this.mForceLaunchTaskOnHome = true;
                } else if (str.equals("--activityType")) {
                    ActivityManagerShellCommand activityManagerShellCommand13 = ActivityManagerShellCommand.this;
                    activityManagerShellCommand13.mActivityType = Integer.parseInt(activityManagerShellCommand13.getNextArgRequired());
                } else if (str.equals("--task")) {
                    ActivityManagerShellCommand activityManagerShellCommand14 = ActivityManagerShellCommand.this;
                    activityManagerShellCommand14.mTaskId = Integer.parseInt(activityManagerShellCommand14.getNextArgRequired());
                } else if (str.equals("--task-overlay")) {
                    ActivityManagerShellCommand.this.mIsTaskOverlay = true;
                } else if (str.equals("--lock-task")) {
                    ActivityManagerShellCommand.this.mIsLockTask = true;
                } else if (str.equals("--allow-background-activity-starts")) {
                    if (ActivityManagerShellCommand.this.mBroadcastOptions == null) {
                        ActivityManagerShellCommand.this.mBroadcastOptions = BroadcastOptions.makeBasic();
                    }
                    ActivityManagerShellCommand.this.mBroadcastOptions.setBackgroundActivityStartsAllowed(true);
                } else if (str.equals("--async")) {
                    ActivityManagerShellCommand.this.mAsync = true;
                } else if (str.equals("--splashscreen-show-icon")) {
                    ActivityManagerShellCommand.this.mShowSplashScreen = true;
                } else {
                    if (!str.equals("--dismiss-keyguard")) {
                        return false;
                    }
                    ActivityManagerShellCommand.this.mDismissKeyguard = true;
                }
                return true;
            }
        });
    }

    /* loaded from: classes.dex */
    public class ProgressWaiter extends IProgressListener.Stub {
        public final CountDownLatch mFinishedLatch;
        public final int mUserId;

        public void onStarted(int i, Bundle bundle) {
        }

        public ProgressWaiter(int i) {
            this.mFinishedLatch = new CountDownLatch(1);
            this.mUserId = i;
        }

        public void onProgress(int i, int i2, Bundle bundle) {
            Slogf.d("ActivityManager", "ProgressWaiter[user=%d]: onProgress(%d, %d)", Integer.valueOf(this.mUserId), Integer.valueOf(i), Integer.valueOf(i2));
        }

        public void onFinished(int i, Bundle bundle) {
            Slogf.d("ActivityManager", "ProgressWaiter[user=%d]: onFinished(%d)", Integer.valueOf(this.mUserId), Integer.valueOf(i));
            this.mFinishedLatch.countDown();
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("ProgressWaiter[userId=");
            sb.append(this.mUserId);
            sb.append(", finished=");
            sb.append(this.mFinishedLatch.getCount() == 0);
            sb.append("]");
            return sb.toString();
        }

        public boolean waitForFinish(long j) {
            try {
                return this.mFinishedLatch.await(j, TimeUnit.MILLISECONDS);
            } catch (InterruptedException unused) {
                System.err.println("Thread interrupted unexpectedly.");
                return false;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:31:0x00b1, code lost:
    
        getErrPrintWriter().println("Error: Intent does not match any activities: " + r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x00c9, code lost:
    
        return r14;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:125:0x02f2  */
    /* JADX WARN: Removed duplicated region for block: B:128:0x030e  */
    /* JADX WARN: Removed duplicated region for block: B:131:0x033d  */
    /* JADX WARN: Removed duplicated region for block: B:134:0x035f  */
    /* JADX WARN: Removed duplicated region for block: B:136:0x0312  */
    /* JADX WARN: Removed duplicated region for block: B:137:0x02fe  */
    /* JADX WARN: Removed duplicated region for block: B:140:0x039a  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x03a4 A[LOOP:0: B:10:0x0021->B:143:0x03a4, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:144:0x03a3 A[SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r14v0 */
    /* JADX WARN: Type inference failed for: r14v1, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r14v6 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int runStartActivity(java.io.PrintWriter r25) {
        /*
            Method dump skipped, instructions count: 962
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.am.ActivityManagerShellCommand.runStartActivity(java.io.PrintWriter):int");
    }

    /* JADX WARN: Removed duplicated region for block: B:142:0x0317  */
    /* JADX WARN: Removed duplicated region for block: B:145:0x0333  */
    /* JADX WARN: Removed duplicated region for block: B:148:0x0362  */
    /* JADX WARN: Removed duplicated region for block: B:151:0x0384  */
    /* JADX WARN: Removed duplicated region for block: B:153:0x0337  */
    /* JADX WARN: Removed duplicated region for block: B:154:0x0323  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int runStartActivityForCarlife(java.io.PrintWriter r30) {
        /*
            Method dump skipped, instructions count: 994
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.am.ActivityManagerShellCommand.runStartActivityForCarlife(java.io.PrintWriter):int");
    }

    public int runStartService(PrintWriter printWriter, boolean z) {
        PrintWriter errPrintWriter = getErrPrintWriter();
        try {
            Intent makeIntent = makeIntent(-2);
            if (this.mUserId == -1) {
                errPrintWriter.println("Error: Can't start activity with user 'all'");
                return -1;
            }
            printWriter.println("Starting service: " + makeIntent);
            printWriter.flush();
            ComponentName startService = this.mInterface.startService((IApplicationThread) null, makeIntent, makeIntent.getType(), z, "com.android.shell", (String) null, this.mUserId);
            if (startService == null) {
                errPrintWriter.println("Error: Not found; no service started.");
                return -1;
            }
            if (startService.getPackageName().equals("!")) {
                errPrintWriter.println("Error: Requires permission " + startService.getClassName());
                return -1;
            }
            if (startService.getPackageName().equals("!!")) {
                errPrintWriter.println("Error: " + startService.getClassName());
                return -1;
            }
            if (!startService.getPackageName().equals("?")) {
                return 0;
            }
            errPrintWriter.println("Error: " + startService.getClassName());
            return -1;
        } catch (URISyntaxException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public int runStopService(PrintWriter printWriter) {
        PrintWriter errPrintWriter = getErrPrintWriter();
        try {
            Intent makeIntent = makeIntent(-2);
            if (this.mUserId == -1) {
                errPrintWriter.println("Error: Can't stop activity with user 'all'");
                return -1;
            }
            printWriter.println("Stopping service: " + makeIntent);
            printWriter.flush();
            int stopService = this.mInterface.stopService((IApplicationThread) null, makeIntent, makeIntent.getType(), this.mUserId);
            if (stopService == 0) {
                errPrintWriter.println("Service not stopped: was not running.");
                return -1;
            }
            if (stopService == 1) {
                errPrintWriter.println("Service stopped");
                return -1;
            }
            if (stopService != -1) {
                return 0;
            }
            errPrintWriter.println("Error stopping service");
            return -1;
        } catch (URISyntaxException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /* loaded from: classes.dex */
    public final class IntentReceiver extends IIntentReceiver.Stub {
        public boolean mFinished = false;
        public final PrintWriter mPw;

        public IntentReceiver(PrintWriter printWriter) {
            this.mPw = printWriter;
        }

        public void performReceive(Intent intent, int i, String str, Bundle bundle, boolean z, boolean z2, int i2) {
            String str2 = "Broadcast completed: result=" + i;
            if (str != null) {
                str2 = str2 + ", data=\"" + str + "\"";
            }
            if (bundle != null) {
                str2 = str2 + ", extras: " + bundle;
            }
            this.mPw.println(str2);
            this.mPw.flush();
            synchronized (this) {
                this.mFinished = true;
                notifyAll();
            }
        }

        public synchronized void waitForFinish() {
            while (!this.mFinished) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    throw new IllegalStateException(e);
                }
            }
        }
    }

    public int runSendBroadcast(PrintWriter printWriter) {
        PrintWriter printWriter2 = new PrintWriter((Writer) new TeeWriter(new Writer[]{ActivityManagerDebugConfig.LOG_WRITER_INFO, printWriter}));
        try {
            Intent makeIntent = makeIntent(-2);
            makeIntent.addFlags(4194304);
            IntentReceiver intentReceiver = new IntentReceiver(printWriter2);
            String str = this.mReceiverPermission;
            String[] strArr = str == null ? null : new String[]{str};
            printWriter2.println("Broadcasting: " + makeIntent);
            printWriter2.flush();
            BroadcastOptions broadcastOptions = this.mBroadcastOptions;
            int broadcastIntentWithFeature = this.mInterface.broadcastIntentWithFeature((IApplicationThread) null, (String) null, makeIntent, (String) null, intentReceiver, 0, (String) null, (Bundle) null, strArr, (String[]) null, (String[]) null, -1, broadcastOptions == null ? null : broadcastOptions.toBundle(), true, false, this.mUserId);
            Slogf.i("ActivityManager", "Enqueued broadcast %s: " + broadcastIntentWithFeature, makeIntent);
            if (broadcastIntentWithFeature != 0 || this.mAsync) {
                return 0;
            }
            intentReceiver.waitForFinish();
            return 0;
        } catch (URISyntaxException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public int runTraceIpc(PrintWriter printWriter) {
        String nextArgRequired = getNextArgRequired();
        if (nextArgRequired.equals("start")) {
            return runTraceIpcStart(printWriter);
        }
        if (nextArgRequired.equals("stop")) {
            return runTraceIpcStop(printWriter);
        }
        getErrPrintWriter().println("Error: unknown trace ipc command '" + nextArgRequired + "'");
        return -1;
    }

    public int runTraceIpcStart(PrintWriter printWriter) {
        printWriter.println("Starting IPC tracing.");
        printWriter.flush();
        this.mInterface.startBinderTracking();
        return 0;
    }

    public int runTraceIpcStop(PrintWriter printWriter) {
        PrintWriter errPrintWriter = getErrPrintWriter();
        String str = null;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption == null) {
                if (str == null) {
                    errPrintWriter.println("Error: Specify filename to dump logs to.");
                    return -1;
                }
                ParcelFileDescriptor openFileForSystem = openFileForSystem(str, "w");
                if (openFileForSystem == null) {
                    return -1;
                }
                if (!this.mInterface.stopBinderTrackingAndDump(openFileForSystem)) {
                    errPrintWriter.println("STOP TRACE FAILED.");
                    return -1;
                }
                printWriter.println("Stopped IPC tracing. Dumping logs to: " + str);
                return 0;
            }
            if (nextOption.equals("--dump-file")) {
                str = getNextArgRequired();
            } else {
                errPrintWriter.println("Error: Unknown option: " + nextOption);
                return -1;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:36:0x00d0  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00d6  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int runProfile(java.io.PrintWriter r21) {
        /*
            Method dump skipped, instructions count: 313
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.am.ActivityManagerShellCommand.runProfile(java.io.PrintWriter):int");
    }

    @NeverCompile
    public int runCompact(PrintWriter printWriter) {
        String nextArgRequired = getNextArgRequired();
        boolean equals = nextArgRequired.equals("full");
        boolean equals2 = nextArgRequired.equals("some");
        if (equals || equals2) {
            ProcessRecord processFromShell = getProcessFromShell();
            if (processFromShell == null) {
                getErrPrintWriter().println("Error: could not find process");
                return -1;
            }
            printWriter.println("Process record found pid: " + processFromShell.mPid);
            if (equals) {
                printWriter.println("Executing full compaction for " + processFromShell.mPid);
                ActivityManagerGlobalLock activityManagerGlobalLock = this.mInternal.mProcLock;
                ActivityManagerService.boostPriorityForProcLockedSection();
                synchronized (activityManagerGlobalLock) {
                    try {
                        this.mInternal.mOomAdjuster.mCachedAppOptimizer.compactApp(processFromShell, CachedAppOptimizer.CompactProfile.FULL, CachedAppOptimizer.CompactSource.SHELL, true);
                    } finally {
                    }
                }
                ActivityManagerService.resetPriorityAfterProcLockedSection();
                printWriter.println("Finished full compaction for " + processFromShell.mPid);
                return 0;
            }
            if (!equals2) {
                return 0;
            }
            printWriter.println("Executing some compaction for " + processFromShell.mPid);
            ActivityManagerGlobalLock activityManagerGlobalLock2 = this.mInternal.mProcLock;
            ActivityManagerService.boostPriorityForProcLockedSection();
            synchronized (activityManagerGlobalLock2) {
                try {
                    this.mInternal.mOomAdjuster.mCachedAppOptimizer.compactApp(processFromShell, CachedAppOptimizer.CompactProfile.SOME, CachedAppOptimizer.CompactSource.SHELL, true);
                } finally {
                    ActivityManagerService.resetPriorityAfterProcLockedSection();
                }
            }
            ActivityManagerService.resetPriorityAfterProcLockedSection();
            printWriter.println("Finished some compaction for " + processFromShell.mPid);
            return 0;
        }
        if (nextArgRequired.equals("system")) {
            printWriter.println("Executing system compaction");
            ActivityManagerGlobalLock activityManagerGlobalLock3 = this.mInternal.mProcLock;
            ActivityManagerService.boostPriorityForProcLockedSection();
            synchronized (activityManagerGlobalLock3) {
                try {
                    this.mInternal.mOomAdjuster.mCachedAppOptimizer.compactAllSystem();
                } finally {
                }
            }
            ActivityManagerService.resetPriorityAfterProcLockedSection();
            printWriter.println("Finished system compaction");
            return 0;
        }
        if (nextArgRequired.equals("native")) {
            String nextArgRequired2 = getNextArgRequired();
            boolean equals3 = nextArgRequired2.equals("full");
            boolean equals4 = nextArgRequired2.equals("some");
            String nextArgRequired3 = getNextArgRequired();
            try {
                int parseInt = Integer.parseInt(nextArgRequired3);
                if (equals3) {
                    this.mInternal.mOomAdjuster.mCachedAppOptimizer.compactNative(CachedAppOptimizer.CompactProfile.FULL, parseInt);
                    return 0;
                }
                if (equals4) {
                    this.mInternal.mOomAdjuster.mCachedAppOptimizer.compactNative(CachedAppOptimizer.CompactProfile.SOME, parseInt);
                    return 0;
                }
                getErrPrintWriter().println("Error: unknown compaction type '" + nextArgRequired2 + "'");
                return -1;
            } catch (Exception unused) {
                getErrPrintWriter().println("Error: failed to parse '" + nextArgRequired3 + "' as a PID");
                return -1;
            }
        }
        getErrPrintWriter().println("Error: unknown compact command '" + nextArgRequired + "'");
        return -1;
    }

    @NeverCompile
    public int runFreeze(PrintWriter printWriter) {
        String nextOption = getNextOption();
        boolean equals = nextOption != null ? nextOption.equals("--sticky") : false;
        ProcessRecord processFromShell = getProcessFromShell();
        if (processFromShell == null) {
            getErrPrintWriter().println("Error: could not find process");
            return -1;
        }
        printWriter.println("Freezing pid: " + processFromShell.mPid + " sticky=" + equals);
        ActivityManagerService activityManagerService = this.mInternal;
        ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                ActivityManagerGlobalLock activityManagerGlobalLock = this.mInternal.mProcLock;
                ActivityManagerService.boostPriorityForProcLockedSection();
                synchronized (activityManagerGlobalLock) {
                    try {
                        processFromShell.mOptRecord.setFreezeSticky(equals);
                        this.mInternal.mOomAdjuster.mCachedAppOptimizer.freezeAppAsyncInternalLSP(processFromShell, 0L, true);
                    } catch (Throwable th) {
                        ActivityManagerService.resetPriorityAfterProcLockedSection();
                        throw th;
                    }
                }
                ActivityManagerService.resetPriorityAfterProcLockedSection();
            } catch (Throwable th2) {
                ActivityManagerService.resetPriorityAfterLockedSection();
                throw th2;
            }
        }
        ActivityManagerService.resetPriorityAfterLockedSection();
        return 0;
    }

    @NeverCompile
    public int runUnfreeze(PrintWriter printWriter) {
        String nextOption = getNextOption();
        boolean equals = nextOption != null ? nextOption.equals("--sticky") : false;
        ProcessRecord processFromShell = getProcessFromShell();
        if (processFromShell == null) {
            getErrPrintWriter().println("Error: could not find process");
            return -1;
        }
        printWriter.println("Unfreezing pid: " + processFromShell.mPid);
        ActivityManagerService activityManagerService = this.mInternal;
        ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                ActivityManagerGlobalLock activityManagerGlobalLock = this.mInternal.mProcLock;
                ActivityManagerService.boostPriorityForProcLockedSection();
                synchronized (activityManagerGlobalLock) {
                    try {
                        synchronized (this.mInternal.mOomAdjuster.mCachedAppOptimizer.mFreezerLock) {
                            processFromShell.mOptRecord.setFreezeSticky(equals);
                            this.mInternal.mOomAdjuster.mCachedAppOptimizer.unfreezeAppInternalLSP(processFromShell, 0, false);
                        }
                    } catch (Throwable th) {
                        ActivityManagerService.resetPriorityAfterProcLockedSection();
                        throw th;
                    }
                }
                ActivityManagerService.resetPriorityAfterProcLockedSection();
            } catch (Throwable th2) {
                ActivityManagerService.resetPriorityAfterLockedSection();
                throw th2;
            }
        }
        ActivityManagerService.resetPriorityAfterLockedSection();
        return 0;
    }

    @NeverCompile
    public ProcessRecord getProcessFromShell() {
        ProcessRecord processRecordLocked;
        String nextArgRequired = getNextArgRequired();
        ActivityManagerGlobalLock activityManagerGlobalLock = this.mInternal.mProcLock;
        ActivityManagerService.boostPriorityForProcLockedSection();
        synchronized (activityManagerGlobalLock) {
            try {
                processRecordLocked = this.mInternal.getProcessRecordLocked(nextArgRequired, this.mInternal.getPackageManagerInternal().getPackageUid(nextArgRequired, 0L, getUserIdFromShellOrFallback()));
            } catch (Throwable th) {
                ActivityManagerService.resetPriorityAfterProcLockedSection();
                throw th;
            }
        }
        ActivityManagerService.resetPriorityAfterProcLockedSection();
        return processRecordLocked;
    }

    @NeverCompile
    public int getUserIdFromShellOrFallback() {
        int parseUserArg;
        int currentUserId = this.mInterface.getCurrentUserId();
        String nextOption = getNextOption();
        return (nextOption == null || !"--user".equals(nextOption) || (parseUserArg = UserHandle.parseUserArg(getNextArgRequired())) == -2) ? currentUserId : parseUserArg;
    }

    public int runDumpHeap(PrintWriter printWriter) {
        PrintWriter errPrintWriter = getErrPrintWriter();
        boolean z = true;
        boolean z2 = false;
        boolean z3 = false;
        int i = -2;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption != null) {
                if (nextOption.equals("--user")) {
                    i = UserHandle.parseUserArg(getNextArgRequired());
                    if (i == -1) {
                        errPrintWriter.println("Error: Can't dump heap with user 'all'");
                        return -1;
                    }
                } else {
                    if (!nextOption.equals("-n")) {
                        if (nextOption.equals("-g")) {
                            z3 = true;
                        } else {
                            if (!nextOption.equals("-m")) {
                                errPrintWriter.println("Error: Unknown option: " + nextOption);
                                return -1;
                            }
                            z2 = true;
                        }
                    }
                    z = false;
                }
            } else {
                String nextArgRequired = getNextArgRequired();
                String nextArg = getNextArg();
                if (nextArg == null) {
                    nextArg = "/data/local/tmp/heapdump-" + LOG_NAME_TIME_FORMATTER.format(LocalDateTime.now(Clock.systemDefaultZone())) + ".prof";
                }
                String str = nextArg;
                ParcelFileDescriptor openFileForSystem = openFileForSystem(str, "w");
                if (openFileForSystem == null) {
                    return -1;
                }
                printWriter.println("File: " + str);
                printWriter.flush();
                final CountDownLatch countDownLatch = new CountDownLatch(1);
                if (!this.mInterface.dumpHeap(nextArgRequired, i, z, z2, z3, str, openFileForSystem, new RemoteCallback(new RemoteCallback.OnResultListener() { // from class: com.android.server.am.ActivityManagerShellCommand.2
                    public void onResult(Bundle bundle) {
                        countDownLatch.countDown();
                    }
                }, (Handler) null))) {
                    errPrintWriter.println("HEAP DUMP FAILED on process " + nextArgRequired);
                    return -1;
                }
                printWriter.println("Waiting for dump to finish...");
                printWriter.flush();
                try {
                    countDownLatch.await();
                } catch (InterruptedException unused) {
                    errPrintWriter.println("Caught InterruptedException");
                }
                return 0;
            }
        }
    }

    public int runSetDebugApp(PrintWriter printWriter) {
        boolean z = false;
        boolean z2 = false;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption != null) {
                if (nextOption.equals("-w")) {
                    z = true;
                } else {
                    if (!nextOption.equals("--persistent")) {
                        getErrPrintWriter().println("Error: Unknown option: " + nextOption);
                        return -1;
                    }
                    z2 = true;
                }
            } else {
                this.mInterface.setDebugApp(getNextArgRequired(), z, z2);
                return 0;
            }
        }
    }

    public int runSetAgentApp(PrintWriter printWriter) {
        this.mInterface.setAgentApp(getNextArgRequired(), getNextArg());
        return 0;
    }

    public int runClearDebugApp(PrintWriter printWriter) {
        this.mInterface.setDebugApp((String) null, false, true);
        return 0;
    }

    public int runSetWatchHeap(PrintWriter printWriter) {
        this.mInterface.setDumpHeapDebugLimit(getNextArgRequired(), 0, Long.parseLong(getNextArgRequired()), (String) null);
        return 0;
    }

    public int runClearWatchHeap(PrintWriter printWriter) {
        this.mInterface.setDumpHeapDebugLimit(getNextArgRequired(), 0, -1L, (String) null);
        return 0;
    }

    public int runClearExitInfo(PrintWriter printWriter) {
        this.mInternal.enforceCallingPermission("android.permission.WRITE_SECURE_SETTINGS", "runClearExitInfo()");
        String str = null;
        int i = -2;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption == null) {
                break;
            }
            if (nextOption.equals("--user")) {
                i = UserHandle.parseUserArg(getNextArgRequired());
            } else {
                str = nextOption;
            }
        }
        if (i == -2) {
            UserInfo currentUser = this.mInterface.getCurrentUser();
            if (currentUser == null) {
                return -1;
            }
            i = currentUser.id;
        }
        this.mInternal.mProcessList.mAppExitInfoTracker.clearHistoryProcessExitInfo(str, i);
        return 0;
    }

    public int runBugReport(PrintWriter printWriter) {
        boolean z = true;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption != null) {
                if (nextOption.equals("--progress")) {
                    this.mInterface.requestInteractiveBugReport();
                } else if (nextOption.equals("--telephony")) {
                    this.mInterface.requestTelephonyBugReport("", "");
                } else {
                    getErrPrintWriter().println("Error: Unknown option: " + nextOption);
                    return -1;
                }
                z = false;
            } else {
                if (z) {
                    this.mInterface.requestFullBugReport();
                }
                printWriter.println("Your lovely bug report is being created; please be patient.");
                return 0;
            }
        }
    }

    public int runForceStop(PrintWriter printWriter) {
        int i = -1;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption != null) {
                if (nextOption.equals("--user")) {
                    i = UserHandle.parseUserArg(getNextArgRequired());
                } else {
                    getErrPrintWriter().println("Error: Unknown option: " + nextOption);
                    return -1;
                }
            } else {
                this.mInterface.forceStopPackage(getNextArgRequired(), i);
                return 0;
            }
        }
    }

    public int runStopApp(PrintWriter printWriter) {
        int i = 0;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption != null) {
                if (nextOption.equals("--user")) {
                    i = UserHandle.parseUserArg(getNextArgRequired());
                } else {
                    getErrPrintWriter().println("Error: Unknown option: " + nextOption);
                    return -1;
                }
            } else {
                this.mInterface.stopAppForUser(getNextArgRequired(), i);
                return 0;
            }
        }
    }

    public int runClearRecentApps(PrintWriter printWriter) {
        this.mTaskInterface.removeAllVisibleRecentTasks();
        return 0;
    }

    public int runFgsNotificationRateLimit(PrintWriter printWriter) {
        boolean z;
        String nextArgRequired = getNextArgRequired();
        nextArgRequired.hashCode();
        if (nextArgRequired.equals("enable")) {
            z = true;
        } else {
            if (!nextArgRequired.equals("disable")) {
                throw new IllegalArgumentException("Argument must be either 'enable' or 'disable'");
            }
            z = false;
        }
        this.mInterface.enableFgsNotificationRateLimit(z);
        return 0;
    }

    public int runCrash(PrintWriter printWriter) {
        String str;
        int i;
        int[] iArr;
        int i2 = -1;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption != null) {
                if (nextOption.equals("--user")) {
                    i2 = UserHandle.parseUserArg(getNextArgRequired());
                } else {
                    getErrPrintWriter().println("Error: Unknown option: " + nextOption);
                    return -1;
                }
            } else {
                String nextArgRequired = getNextArgRequired();
                try {
                    i = Integer.parseInt(nextArgRequired);
                    str = null;
                } catch (NumberFormatException unused) {
                    str = nextArgRequired;
                    i = -1;
                }
                if (i2 == -1) {
                    iArr = this.mInternal.mUserController.getUserIds();
                } else {
                    iArr = new int[]{i2};
                }
                for (int i3 : iArr) {
                    if (this.mInternal.mUserController.hasUserRestriction("no_debugging_features", i3)) {
                        getOutPrintWriter().println("Shell does not have permission to crash packages for user " + i3);
                    } else {
                        this.mInterface.crashApplicationWithType(-1, i, str, i3, "shell-induced crash", false, 5);
                    }
                }
                return 0;
            }
        }
    }

    public int runKill(PrintWriter printWriter) {
        int i = -1;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption != null) {
                if (nextOption.equals("--user")) {
                    i = UserHandle.parseUserArg(getNextArgRequired());
                } else {
                    getErrPrintWriter().println("Error: Unknown option: " + nextOption);
                    return -1;
                }
            } else {
                this.mInterface.killBackgroundProcesses(getNextArgRequired(), i);
                return 0;
            }
        }
    }

    public int runKillAll(PrintWriter printWriter) {
        this.mInterface.killAllBackgroundProcesses();
        return 0;
    }

    public int runMakeIdle(PrintWriter printWriter) {
        int i = -1;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption != null) {
                if (nextOption.equals("--user")) {
                    i = UserHandle.parseUserArg(getNextArgRequired());
                } else {
                    getErrPrintWriter().println("Error: Unknown option: " + nextOption);
                    return -1;
                }
            } else {
                this.mInterface.makePackageIdle(getNextArgRequired(), i);
                return 0;
            }
        }
    }

    public int runSetDeterministicUidIdle(PrintWriter printWriter) {
        while (true) {
            String nextOption = getNextOption();
            if (nextOption != null) {
                if (nextOption.equals("--user")) {
                    UserHandle.parseUserArg(getNextArgRequired());
                } else {
                    getErrPrintWriter().println("Error: Unknown option: " + nextOption);
                    return -1;
                }
            } else {
                this.mInterface.setDeterministicUidIdle(Boolean.parseBoolean(getNextArgRequired()));
                return 0;
            }
        }
    }

    /* loaded from: classes.dex */
    public final class MyActivityController extends IActivityController.Stub {
        public final boolean mAlwaysContinue;
        public final boolean mAlwaysKill;
        public final String mGdbPort;
        public Process mGdbProcess;
        public Thread mGdbThread;
        public boolean mGotGdbPrint;
        public final InputStream mInput;
        public final IActivityManager mInterface;
        public final boolean mMonkey;
        public final PrintWriter mPw;
        public int mResult;
        public final boolean mSimpleMode;
        public int mState;
        public final String mTarget;

        public MyActivityController(IActivityManager iActivityManager, PrintWriter printWriter, InputStream inputStream, String str, boolean z, boolean z2, String str2, boolean z3, boolean z4) {
            this.mInterface = iActivityManager;
            this.mPw = printWriter;
            this.mInput = inputStream;
            this.mGdbPort = str;
            this.mMonkey = z;
            this.mSimpleMode = z2;
            this.mTarget = str2;
            this.mAlwaysContinue = z3;
            this.mAlwaysKill = z4;
        }

        public final boolean shouldHandlePackageOrProcess(String str) {
            String str2 = this.mTarget;
            if (str2 == null) {
                return true;
            }
            return str2.equals(str);
        }

        public boolean activityResuming(String str) {
            if (!shouldHandlePackageOrProcess(str)) {
                return true;
            }
            synchronized (this) {
                this.mPw.println("** Activity resuming: " + str);
                this.mPw.flush();
            }
            return true;
        }

        public boolean activityStarting(Intent intent, String str) {
            if (!shouldHandlePackageOrProcess(str)) {
                return true;
            }
            synchronized (this) {
                this.mPw.println("** Activity starting: " + str);
                this.mPw.flush();
            }
            return true;
        }

        public boolean appCrashed(String str, int i, String str2, String str3, long j, String str4) {
            if (!shouldHandlePackageOrProcess(str)) {
                return true;
            }
            synchronized (this) {
                if (this.mSimpleMode) {
                    this.mPw.println("** PROCESS CRASHED: " + str);
                } else {
                    this.mPw.println("** ERROR: PROCESS CRASHED");
                    this.mPw.println("processName: " + str);
                    this.mPw.println("processPid: " + i);
                    this.mPw.println("shortMsg: " + str2);
                    this.mPw.println("longMsg: " + str3);
                    this.mPw.println("timeMillis: " + j);
                    this.mPw.println("uptime: " + SystemClock.uptimeMillis());
                    this.mPw.println("stack:");
                    this.mPw.print(str4);
                    this.mPw.println("#");
                }
                this.mPw.flush();
                if (this.mAlwaysContinue) {
                    return true;
                }
                if (this.mAlwaysKill) {
                    return false;
                }
                return waitControllerLocked(i, 1) != 1;
            }
        }

        public int appEarlyNotResponding(String str, int i, String str2) {
            if (!shouldHandlePackageOrProcess(str)) {
                return 0;
            }
            synchronized (this) {
                if (this.mSimpleMode) {
                    this.mPw.println("** EARLY PROCESS NOT RESPONDING: " + str);
                } else {
                    this.mPw.println("** ERROR: EARLY PROCESS NOT RESPONDING");
                    this.mPw.println("processName: " + str);
                    this.mPw.println("processPid: " + i);
                    this.mPw.println("annotation: " + str2);
                    this.mPw.println("uptime: " + SystemClock.uptimeMillis());
                }
                this.mPw.flush();
                if (this.mAlwaysContinue) {
                    return 0;
                }
                if (this.mAlwaysKill) {
                    return -1;
                }
                return waitControllerLocked(i, 2) == 1 ? -1 : 0;
            }
        }

        public int appNotResponding(String str, int i, String str2) {
            if (!shouldHandlePackageOrProcess(str)) {
                return 0;
            }
            synchronized (this) {
                if (this.mSimpleMode) {
                    this.mPw.println("** PROCESS NOT RESPONDING: " + str);
                } else {
                    this.mPw.println("** ERROR: PROCESS NOT RESPONDING");
                    this.mPw.println("processName: " + str);
                    this.mPw.println("processPid: " + i);
                    this.mPw.println("uptime: " + SystemClock.uptimeMillis());
                    this.mPw.println("processStats:");
                    this.mPw.print(str2);
                    this.mPw.println("#");
                }
                this.mPw.flush();
                if (this.mAlwaysContinue) {
                    return 0;
                }
                if (this.mAlwaysKill) {
                    return -1;
                }
                int waitControllerLocked = waitControllerLocked(i, 3);
                if (waitControllerLocked == 1) {
                    return -1;
                }
                return waitControllerLocked == 2 ? 1 : 0;
            }
        }

        public int systemNotResponding(String str) {
            if (this.mTarget != null) {
                return -1;
            }
            synchronized (this) {
                this.mPw.println("** ERROR: PROCESS NOT RESPONDING");
                if (!this.mSimpleMode) {
                    this.mPw.println("message: " + str);
                    this.mPw.println("#");
                    this.mPw.println("Allowing system to die.");
                }
                this.mPw.flush();
            }
            return -1;
        }

        public void killGdbLocked() {
            this.mGotGdbPrint = false;
            if (this.mGdbProcess != null) {
                this.mPw.println("Stopping gdbserver");
                this.mPw.flush();
                this.mGdbProcess.destroy();
                this.mGdbProcess = null;
            }
            Thread thread = this.mGdbThread;
            if (thread != null) {
                thread.interrupt();
                this.mGdbThread = null;
            }
        }

        public int waitControllerLocked(int i, int i2) {
            if (this.mGdbPort != null) {
                killGdbLocked();
                try {
                    this.mPw.println("Starting gdbserver on port " + this.mGdbPort);
                    this.mPw.println("Do the following:");
                    this.mPw.println("  adb forward tcp:" + this.mGdbPort + " tcp:" + this.mGdbPort);
                    PrintWriter printWriter = this.mPw;
                    StringBuilder sb = new StringBuilder();
                    sb.append("  gdbclient app_process :");
                    sb.append(this.mGdbPort);
                    printWriter.println(sb.toString());
                    this.mPw.flush();
                    this.mGdbProcess = Runtime.getRuntime().exec(new String[]{"gdbserver", XmlUtils.STRING_ARRAY_SEPARATOR + this.mGdbPort, "--attach", Integer.toString(i)});
                    final InputStreamReader inputStreamReader = new InputStreamReader(this.mGdbProcess.getInputStream());
                    Thread thread = new Thread() { // from class: com.android.server.am.ActivityManagerShellCommand.MyActivityController.1
                        @Override // java.lang.Thread, java.lang.Runnable
                        public void run() {
                            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                            int i3 = 0;
                            while (true) {
                                synchronized (MyActivityController.this) {
                                    MyActivityController myActivityController = MyActivityController.this;
                                    if (myActivityController.mGdbThread == null) {
                                        return;
                                    }
                                    if (i3 == 2) {
                                        myActivityController.mGotGdbPrint = true;
                                        myActivityController.notifyAll();
                                    }
                                    try {
                                        String readLine = bufferedReader.readLine();
                                        if (readLine == null) {
                                            return;
                                        }
                                        MyActivityController.this.mPw.println("GDB: " + readLine);
                                        MyActivityController.this.mPw.flush();
                                        i3++;
                                    } catch (IOException unused) {
                                        return;
                                    }
                                }
                            }
                        }
                    };
                    this.mGdbThread = thread;
                    thread.start();
                    try {
                        wait(500L);
                    } catch (InterruptedException unused) {
                    }
                } catch (IOException e) {
                    this.mPw.println("Failure starting gdbserver: " + e);
                    this.mPw.flush();
                    killGdbLocked();
                }
            }
            this.mState = i2;
            this.mPw.println("");
            printMessageForState();
            this.mPw.flush();
            while (this.mState != 0) {
                try {
                    wait();
                } catch (InterruptedException unused2) {
                }
            }
            killGdbLocked();
            return this.mResult;
        }

        public void resumeController(int i) {
            synchronized (this) {
                this.mState = 0;
                this.mResult = i;
                notifyAll();
            }
        }

        public void printMessageForState() {
            if ((this.mAlwaysContinue || this.mAlwaysKill) && this.mSimpleMode) {
                return;
            }
            int i = this.mState;
            if (i == 0) {
                this.mPw.println("Monitoring activity manager...  available commands:");
            } else if (i == 1) {
                this.mPw.println("Waiting after crash...  available commands:");
                this.mPw.println("(c)ontinue: show crash dialog");
                this.mPw.println("(k)ill: immediately kill app");
            } else if (i == 2) {
                this.mPw.println("Waiting after early ANR...  available commands:");
                this.mPw.println("(c)ontinue: standard ANR processing");
                this.mPw.println("(k)ill: immediately kill app");
            } else if (i == 3) {
                this.mPw.println("Waiting after ANR...  available commands:");
                this.mPw.println("(c)ontinue: show ANR dialog");
                this.mPw.println("(k)ill: immediately kill app");
                this.mPw.println("(w)ait: wait some more");
            }
            this.mPw.println("(q)uit: finish monitoring");
        }

        public void run() {
            boolean z;
            try {
                try {
                    printMessageForState();
                    this.mPw.flush();
                    this.mInterface.setActivityController(this, this.mMonkey);
                    this.mState = 0;
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.mInput));
                    while (true) {
                        String readLine = bufferedReader.readLine();
                        if (readLine == null) {
                            break;
                        }
                        if (readLine.length() > 0) {
                            if ("q".equals(readLine) || "quit".equals(readLine)) {
                                break;
                            }
                            int i = this.mState;
                            z = true;
                            if (i == 1) {
                                if (!"c".equals(readLine) && !"continue".equals(readLine)) {
                                    if (!"k".equals(readLine) && !"kill".equals(readLine)) {
                                        this.mPw.println("Invalid command: " + readLine);
                                    }
                                    resumeController(1);
                                }
                                resumeController(0);
                            } else if (i == 3) {
                                if (!"c".equals(readLine) && !"continue".equals(readLine)) {
                                    if (!"k".equals(readLine) && !"kill".equals(readLine)) {
                                        if (!"w".equals(readLine) && !"wait".equals(readLine)) {
                                            this.mPw.println("Invalid command: " + readLine);
                                        }
                                        resumeController(2);
                                    }
                                    resumeController(1);
                                }
                                resumeController(0);
                            } else if (i == 2) {
                                if (!"c".equals(readLine) && !"continue".equals(readLine)) {
                                    if (!"k".equals(readLine) && !"kill".equals(readLine)) {
                                        this.mPw.println("Invalid command: " + readLine);
                                    }
                                    resumeController(1);
                                }
                                resumeController(0);
                            } else {
                                this.mPw.println("Invalid command: " + readLine);
                            }
                        } else {
                            z = false;
                        }
                        synchronized (this) {
                            if (z) {
                                this.mPw.println("");
                            }
                            printMessageForState();
                            this.mPw.flush();
                        }
                    }
                    resumeController(0);
                } catch (IOException e) {
                    e.printStackTrace(this.mPw);
                    this.mPw.flush();
                }
            } finally {
                this.mInterface.setActivityController((IActivityController) null, this.mMonkey);
            }
        }
    }

    public int runMonitor(PrintWriter printWriter) {
        String str = null;
        String str2 = null;
        boolean z = false;
        boolean z2 = false;
        boolean z3 = false;
        boolean z4 = false;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption == null) {
                if (z3 && z4) {
                    getErrPrintWriter().println("Error: -k and -c options can't be used together.");
                    return -1;
                }
                new MyActivityController(this.mInterface, printWriter, getRawInputStream(), str, z, z2, str2, z3, z4).run();
                return 0;
            }
            if (nextOption.equals("--gdb")) {
                str = getNextArgRequired();
            } else if (nextOption.equals("-p")) {
                str2 = getNextArgRequired();
            } else if (nextOption.equals("-m")) {
                z = true;
            } else if (nextOption.equals("-s")) {
                z2 = true;
            } else if (nextOption.equals("-c")) {
                z3 = true;
            } else {
                if (!nextOption.equals("-k")) {
                    getErrPrintWriter().println("Error: Unknown option: " + nextOption);
                    return -1;
                }
                z4 = true;
            }
        }
    }

    /* loaded from: classes.dex */
    public final class MyUidObserver extends UidObserver implements ActivityManagerService.OomAdjObserver {
        public final InputStream mInput;
        public final IActivityManager mInterface;
        public final ActivityManagerService mInternal;
        public final int mMask;
        public final PrintWriter mPw;
        public int mState;
        public final int mUid;

        public MyUidObserver(ActivityManagerService activityManagerService, PrintWriter printWriter, InputStream inputStream, int i, int i2) {
            this.mInterface = activityManagerService;
            this.mInternal = activityManagerService;
            this.mPw = printWriter;
            this.mInput = inputStream;
            this.mUid = i;
            this.mMask = i2;
        }

        public void onUidStateChanged(int i, int i2, long j, int i3) {
            synchronized (this) {
                StrictMode.ThreadPolicy allowThreadDiskWrites = StrictMode.allowThreadDiskWrites();
                try {
                    this.mPw.print(i);
                    this.mPw.print(" procstate ");
                    this.mPw.print(ProcessList.makeProcStateString(i2));
                    this.mPw.print(" seq ");
                    this.mPw.print(j);
                    this.mPw.print(" capability ");
                    this.mPw.println(this.mMask & i3);
                    this.mPw.flush();
                } finally {
                    StrictMode.setThreadPolicy(allowThreadDiskWrites);
                }
            }
        }

        public void onUidGone(int i, boolean z) {
            synchronized (this) {
                StrictMode.ThreadPolicy allowThreadDiskWrites = StrictMode.allowThreadDiskWrites();
                try {
                    this.mPw.print(i);
                    this.mPw.print(" gone");
                    if (z) {
                        this.mPw.print(" disabled");
                    }
                    this.mPw.println();
                    this.mPw.flush();
                } finally {
                    StrictMode.setThreadPolicy(allowThreadDiskWrites);
                }
            }
        }

        public void onUidActive(int i) {
            synchronized (this) {
                StrictMode.ThreadPolicy allowThreadDiskWrites = StrictMode.allowThreadDiskWrites();
                try {
                    this.mPw.print(i);
                    this.mPw.println(" active");
                    this.mPw.flush();
                } finally {
                    StrictMode.setThreadPolicy(allowThreadDiskWrites);
                }
            }
        }

        public void onUidIdle(int i, boolean z) {
            synchronized (this) {
                StrictMode.ThreadPolicy allowThreadDiskWrites = StrictMode.allowThreadDiskWrites();
                try {
                    this.mPw.print(i);
                    this.mPw.print(" idle");
                    if (z) {
                        this.mPw.print(" disabled");
                    }
                    this.mPw.println();
                    this.mPw.flush();
                } finally {
                    StrictMode.setThreadPolicy(allowThreadDiskWrites);
                }
            }
        }

        public void onUidCachedChanged(int i, boolean z) {
            synchronized (this) {
                StrictMode.ThreadPolicy allowThreadDiskWrites = StrictMode.allowThreadDiskWrites();
                try {
                    this.mPw.print(i);
                    this.mPw.println(z ? " cached" : " uncached");
                    this.mPw.flush();
                } finally {
                    StrictMode.setThreadPolicy(allowThreadDiskWrites);
                }
            }
        }

        @Override // com.android.server.am.ActivityManagerService.OomAdjObserver
        public void onOomAdjMessage(String str) {
            synchronized (this) {
                StrictMode.ThreadPolicy allowThreadDiskWrites = StrictMode.allowThreadDiskWrites();
                try {
                    this.mPw.print("# ");
                    this.mPw.println(str);
                    this.mPw.flush();
                } finally {
                    StrictMode.setThreadPolicy(allowThreadDiskWrites);
                }
            }
        }

        public void printMessageForState() {
            if (this.mState == 0) {
                this.mPw.println("Watching uid states...  available commands:");
            }
            this.mPw.println("(q)uit: finish watching");
        }

        /* JADX WARN: Code restructure failed: missing block: B:32:0x007a, code lost:
        
            if (r6.mUid >= 0) goto L35;
         */
        /* JADX WARN: Code restructure failed: missing block: B:33:0x0093, code lost:
        
            r6.mInterface.unregisterUidObserver(r6);
         */
        /* JADX WARN: Code restructure failed: missing block: B:34:0x0098, code lost:
        
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:35:0x008e, code lost:
        
            r6.mInternal.clearOomAdjObserver();
         */
        /* JADX WARN: Code restructure failed: missing block: B:41:0x008c, code lost:
        
            if (r6.mUid < 0) goto L36;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void run() {
            /*
                r6 = this;
                r6.printMessageForState()     // Catch: java.lang.Throwable -> L7d java.io.IOException -> L7f
                java.io.PrintWriter r0 = r6.mPw     // Catch: java.lang.Throwable -> L7d java.io.IOException -> L7f
                r0.flush()     // Catch: java.lang.Throwable -> L7d java.io.IOException -> L7f
                android.app.IActivityManager r0 = r6.mInterface     // Catch: java.lang.Throwable -> L7d java.io.IOException -> L7f
                r1 = -1
                r2 = 0
                r3 = 31
                r0.registerUidObserver(r6, r3, r1, r2)     // Catch: java.lang.Throwable -> L7d java.io.IOException -> L7f
                int r0 = r6.mUid     // Catch: java.lang.Throwable -> L7d java.io.IOException -> L7f
                if (r0 < 0) goto L1a
                com.android.server.am.ActivityManagerService r1 = r6.mInternal     // Catch: java.lang.Throwable -> L7d java.io.IOException -> L7f
                r1.setOomAdjObserver(r0, r6)     // Catch: java.lang.Throwable -> L7d java.io.IOException -> L7f
            L1a:
                r0 = 0
                r6.mState = r0     // Catch: java.lang.Throwable -> L7d java.io.IOException -> L7f
                java.io.InputStreamReader r1 = new java.io.InputStreamReader     // Catch: java.lang.Throwable -> L7d java.io.IOException -> L7f
                java.io.InputStream r2 = r6.mInput     // Catch: java.lang.Throwable -> L7d java.io.IOException -> L7f
                r1.<init>(r2)     // Catch: java.lang.Throwable -> L7d java.io.IOException -> L7f
                java.io.BufferedReader r2 = new java.io.BufferedReader     // Catch: java.lang.Throwable -> L7d java.io.IOException -> L7f
                r2.<init>(r1)     // Catch: java.lang.Throwable -> L7d java.io.IOException -> L7f
            L29:
                java.lang.String r1 = r2.readLine()     // Catch: java.lang.Throwable -> L7d java.io.IOException -> L7f
                if (r1 == 0) goto L78
                int r3 = r1.length()     // Catch: java.lang.Throwable -> L7d java.io.IOException -> L7f
                if (r3 > 0) goto L37
                r1 = r0
                goto L61
            L37:
                java.lang.String r3 = "q"
                boolean r3 = r3.equals(r1)     // Catch: java.lang.Throwable -> L7d java.io.IOException -> L7f
                if (r3 != 0) goto L78
                java.lang.String r3 = "quit"
                boolean r3 = r3.equals(r1)     // Catch: java.lang.Throwable -> L7d java.io.IOException -> L7f
                if (r3 == 0) goto L4a
                goto L78
            L4a:
                java.io.PrintWriter r3 = r6.mPw     // Catch: java.lang.Throwable -> L7d java.io.IOException -> L7f
                java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L7d java.io.IOException -> L7f
                r4.<init>()     // Catch: java.lang.Throwable -> L7d java.io.IOException -> L7f
                java.lang.String r5 = "Invalid command: "
                r4.append(r5)     // Catch: java.lang.Throwable -> L7d java.io.IOException -> L7f
                r4.append(r1)     // Catch: java.lang.Throwable -> L7d java.io.IOException -> L7f
                java.lang.String r1 = r4.toString()     // Catch: java.lang.Throwable -> L7d java.io.IOException -> L7f
                r3.println(r1)     // Catch: java.lang.Throwable -> L7d java.io.IOException -> L7f
                r1 = 1
            L61:
                monitor-enter(r6)     // Catch: java.lang.Throwable -> L7d java.io.IOException -> L7f
                if (r1 == 0) goto L6b
                java.io.PrintWriter r1 = r6.mPw     // Catch: java.lang.Throwable -> L75
                java.lang.String r3 = ""
                r1.println(r3)     // Catch: java.lang.Throwable -> L75
            L6b:
                r6.printMessageForState()     // Catch: java.lang.Throwable -> L75
                java.io.PrintWriter r1 = r6.mPw     // Catch: java.lang.Throwable -> L75
                r1.flush()     // Catch: java.lang.Throwable -> L75
                monitor-exit(r6)     // Catch: java.lang.Throwable -> L75
                goto L29
            L75:
                r0 = move-exception
                monitor-exit(r6)     // Catch: java.lang.Throwable -> L75
                throw r0     // Catch: java.lang.Throwable -> L7d java.io.IOException -> L7f
            L78:
                int r0 = r6.mUid
                if (r0 < 0) goto L93
                goto L8e
            L7d:
                r0 = move-exception
                goto L99
            L7f:
                r0 = move-exception
                java.io.PrintWriter r1 = r6.mPw     // Catch: java.lang.Throwable -> L7d
                r0.printStackTrace(r1)     // Catch: java.lang.Throwable -> L7d
                java.io.PrintWriter r0 = r6.mPw     // Catch: java.lang.Throwable -> L7d
                r0.flush()     // Catch: java.lang.Throwable -> L7d
                int r0 = r6.mUid
                if (r0 < 0) goto L93
            L8e:
                com.android.server.am.ActivityManagerService r0 = r6.mInternal
                r0.clearOomAdjObserver()
            L93:
                android.app.IActivityManager r0 = r6.mInterface
                r0.unregisterUidObserver(r6)
                return
            L99:
                int r1 = r6.mUid
                if (r1 < 0) goto La2
                com.android.server.am.ActivityManagerService r1 = r6.mInternal
                r1.clearOomAdjObserver()
            La2:
                android.app.IActivityManager r1 = r6.mInterface
                r1.unregisterUidObserver(r6)
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.am.ActivityManagerShellCommand.MyUidObserver.run():void");
        }
    }

    public int runWatchUids(PrintWriter printWriter) {
        int i = -1;
        int i2 = 15;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption != null) {
                if (nextOption.equals("--oom")) {
                    i = Integer.parseInt(getNextArgRequired());
                } else if (nextOption.equals("--mask")) {
                    i2 = Integer.parseInt(getNextArgRequired());
                } else {
                    getErrPrintWriter().println("Error: Unknown option: " + nextOption);
                    return -1;
                }
            } else {
                new MyUidObserver(this.mInternal, printWriter, getRawInputStream(), i, i2).run();
                return 0;
            }
        }
    }

    public int runHang(PrintWriter printWriter) {
        boolean z = false;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption != null) {
                if (!nextOption.equals("--allow-restart")) {
                    getErrPrintWriter().println("Error: Unknown option: " + nextOption);
                    return -1;
                }
                z = true;
            } else {
                printWriter.println("Hanging the system...");
                printWriter.flush();
                try {
                    this.mInterface.hang(getShellCallback().getShellCallbackBinder(), z);
                    return 0;
                } catch (NullPointerException unused) {
                    printWriter.println("Hanging failed, since caller " + Binder.getCallingPid() + " did not provide a ShellCallback!");
                    printWriter.flush();
                    return 1;
                }
            }
        }
    }

    public int runRestart(PrintWriter printWriter) {
        String nextOption = getNextOption();
        if (nextOption != null) {
            getErrPrintWriter().println("Error: Unknown option: " + nextOption);
            return -1;
        }
        printWriter.println("Restart the system...");
        printWriter.flush();
        this.mInterface.restart();
        return 0;
    }

    public int runIdleMaintenance(PrintWriter printWriter) {
        String nextOption = getNextOption();
        if (nextOption != null) {
            getErrPrintWriter().println("Error: Unknown option: " + nextOption);
            return -1;
        }
        printWriter.println("Performing idle maintenance...");
        this.mInterface.sendIdleJobTrigger();
        return 0;
    }

    public int runScreenCompat(PrintWriter printWriter) {
        boolean z;
        String nextArgRequired = getNextArgRequired();
        if ("on".equals(nextArgRequired)) {
            z = true;
        } else {
            if (!"off".equals(nextArgRequired)) {
                getErrPrintWriter().println("Error: enabled mode must be 'on' or 'off' at " + nextArgRequired);
                return -1;
            }
            z = false;
        }
        String nextArgRequired2 = getNextArgRequired();
        do {
            try {
                this.mInterface.setPackageScreenCompatMode(nextArgRequired2, z ? 1 : 0);
            } catch (RemoteException unused) {
            }
            nextArgRequired2 = getNextArg();
        } while (nextArgRequired2 != null);
        return 0;
    }

    public int runPackageImportance(PrintWriter printWriter) {
        printWriter.println(ActivityManager.RunningAppProcessInfo.procStateToImportance(this.mInterface.getPackageProcessState(getNextArgRequired(), "com.android.shell")));
        return 0;
    }

    public int runToUri(PrintWriter printWriter, int i) {
        try {
            printWriter.println(makeIntent(-2).toUri(i));
            return 0;
        } catch (URISyntaxException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public final boolean switchUserAndWaitForComplete(final int i) {
        UserInfo currentUser = this.mInterface.getCurrentUser();
        if (currentUser != null && i == currentUser.id) {
            return true;
        }
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        UserSwitchObserver userSwitchObserver = new UserSwitchObserver() { // from class: com.android.server.am.ActivityManagerShellCommand.3
            public void onUserSwitchComplete(int i2) {
                if (i == i2) {
                    countDownLatch.countDown();
                }
            }
        };
        try {
            this.mInterface.registerUserSwitchObserver(userSwitchObserver, ActivityManagerShellCommand.class.getName());
            boolean switchUser = this.mInterface.switchUser(i);
            if (switchUser) {
                try {
                    switchUser = countDownLatch.await(120000L, TimeUnit.MILLISECONDS);
                } catch (InterruptedException unused) {
                    getErrPrintWriter().println("Error: Thread interrupted unexpectedly.");
                }
                return switchUser;
            }
            this.mInterface.unregisterUserSwitchObserver(userSwitchObserver);
            return false;
        } finally {
            this.mInterface.unregisterUserSwitchObserver(userSwitchObserver);
        }
    }

    public int runSwitchUser(PrintWriter printWriter) {
        boolean switchUser;
        boolean z = false;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption != null) {
                if (!"-w".equals(nextOption)) {
                    getErrPrintWriter().println("Error: unknown option: " + nextOption);
                    return -1;
                }
                z = true;
            } else {
                int parseInt = Integer.parseInt(getNextArgRequired());
                int userSwitchability = ((UserManager) this.mInternal.mContext.getSystemService(UserManager.class)).getUserSwitchability(UserHandle.of(parseInt));
                if (userSwitchability != 0) {
                    getErrPrintWriter().println("Error: UserSwitchabilityResult=" + userSwitchability);
                    return -1;
                }
                Trace.traceBegin(64L, "shell_runSwitchUser");
                try {
                    if (z) {
                        switchUser = switchUserAndWaitForComplete(parseInt);
                    } else {
                        switchUser = this.mInterface.switchUser(parseInt);
                    }
                    if (switchUser) {
                        return 0;
                    }
                    printWriter.printf("Error: Failed to switch to user %d\n", Integer.valueOf(parseInt));
                    return 1;
                } finally {
                    Trace.traceEnd(64L);
                }
            }
        }
    }

    public int runGetCurrentUser(PrintWriter printWriter) {
        int currentUserId = this.mInterface.getCurrentUserId();
        if (currentUserId == -10000) {
            throw new IllegalStateException("Current user not set");
        }
        printWriter.println(currentUserId);
        return 0;
    }

    public int runStartUser(PrintWriter printWriter) {
        boolean startUserInBackgroundVisibleOnDisplay;
        boolean z = false;
        int i = -1;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption != null) {
                if (nextOption.equals("--display")) {
                    i = getDisplayIdFromNextArg();
                } else {
                    if (!nextOption.equals("-w")) {
                        getErrPrintWriter().println("Error: unknown option: " + nextOption);
                        return -1;
                    }
                    z = true;
                }
            } else {
                int parseInt = Integer.parseInt(getNextArgRequired());
                IProgressListener progressWaiter = z ? new ProgressWaiter(parseInt) : null;
                UserManagerInternal userManagerInternal = (UserManagerInternal) LocalServices.getService(UserManagerInternal.class);
                ActivityManagerInternal activityManagerInternal = (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
                int profileParentId = userManagerInternal.getProfileParentId(parseInt);
                int currentUserId = activityManagerInternal.getCurrentUserId();
                boolean z2 = profileParentId != parseInt;
                boolean z3 = z2 && profileParentId == currentUserId;
                Slogf.d("ActivityManager", "runStartUser(): userId=%d, parentUserId=%d, currentUserId=%d, isProfile=%b, isVisibleProfile=%b, display=%d, waiter=%s", Integer.valueOf(parseInt), Integer.valueOf(profileParentId), Integer.valueOf(currentUserId), Boolean.valueOf(z2), Boolean.valueOf(z3), Integer.valueOf(i), progressWaiter);
                Trace.traceBegin(64L, "shell_runStartUser" + parseInt);
                String str = "";
                try {
                    if (z3) {
                        Slogf.d("ActivityManager", "calling startProfileWithListener(%d, %s)", Integer.valueOf(parseInt), progressWaiter);
                        startUserInBackgroundVisibleOnDisplay = this.mInterface.startProfileWithListener(parseInt, progressWaiter);
                    } else if (i == -1) {
                        Slogf.d("ActivityManager", "calling startUserInBackgroundWithListener(%d)", Integer.valueOf(parseInt));
                        startUserInBackgroundVisibleOnDisplay = this.mInterface.startUserInBackgroundWithListener(parseInt, progressWaiter);
                    } else {
                        if (!UserManager.isVisibleBackgroundUsersEnabled()) {
                            printWriter.println("Not supported");
                            return -1;
                        }
                        Slogf.d("ActivityManager", "calling startUserInBackgroundVisibleOnDisplay(%d, %d, %s)", Integer.valueOf(parseInt), Integer.valueOf(i), progressWaiter);
                        startUserInBackgroundVisibleOnDisplay = this.mInterface.startUserInBackgroundVisibleOnDisplay(parseInt, i, progressWaiter);
                        str = " on display " + i;
                    }
                    if (z && startUserInBackgroundVisibleOnDisplay) {
                        Slogf.d("ActivityManager", "waiting %d ms", 120000);
                        startUserInBackgroundVisibleOnDisplay = progressWaiter.waitForFinish(120000L);
                    }
                    if (startUserInBackgroundVisibleOnDisplay) {
                        printWriter.println("Success: user started" + str);
                    } else {
                        getErrPrintWriter().println("Error: could not start user" + str);
                    }
                    return 0;
                } finally {
                    Trace.traceEnd(64L);
                }
            }
        }
    }

    public int runUnlockUser(PrintWriter printWriter) {
        int parseInt = Integer.parseInt(getNextArgRequired());
        String nextArg = getNextArg();
        if (!TextUtils.isEmpty(nextArg) && !"!".equals(nextArg)) {
            getErrPrintWriter().println("Error: token parameter not supported");
            return -1;
        }
        String nextArg2 = getNextArg();
        if (!TextUtils.isEmpty(nextArg2) && !"!".equals(nextArg2)) {
            getErrPrintWriter().println("Error: secret parameter not supported");
            return -1;
        }
        if (this.mInterface.unlockUser2(parseInt, (IProgressListener) null)) {
            printWriter.println("Success: user unlocked");
            return 0;
        }
        getErrPrintWriter().println("Error: could not unlock user");
        return 0;
    }

    /* loaded from: classes.dex */
    public final class StopUserCallback extends IStopUserCallback.Stub {
        public boolean mFinished;
        public final int mUserId;

        public StopUserCallback(int i) {
            this.mFinished = false;
            this.mUserId = i;
        }

        public synchronized void waitForFinish() {
            while (!this.mFinished) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    throw new IllegalStateException(e);
                }
            }
            Slogf.d("ActivityManager", "user %d finished stopping", Integer.valueOf(this.mUserId));
        }

        public synchronized void userStopped(int i) {
            Slogf.d("ActivityManager", "StopUserCallback: userStopped(%d)", Integer.valueOf(i));
            this.mFinished = true;
            notifyAll();
        }

        public synchronized void userStopAborted(int i) {
            Slogf.d("ActivityManager", "StopUserCallback: userStopAborted(%d)", Integer.valueOf(i));
            this.mFinished = true;
            notifyAll();
        }

        public String toString() {
            return "ProgressWaiter[userId=" + this.mUserId + ", finished=" + this.mFinished + "]";
        }
    }

    public int runStopUser(PrintWriter printWriter) {
        String str;
        boolean z = false;
        boolean z2 = false;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption != null) {
                if ("-w".equals(nextOption)) {
                    z = true;
                } else {
                    if (!"-f".equals(nextOption)) {
                        getErrPrintWriter().println("Error: unknown option: " + nextOption);
                        return -1;
                    }
                    z2 = true;
                }
            } else {
                int parseInt = Integer.parseInt(getNextArgRequired());
                IStopUserCallback stopUserCallback = z ? new StopUserCallback(parseInt) : null;
                Slogf.d("ActivityManager", "Calling stopUser(%d, %b, %s)", Integer.valueOf(parseInt), Boolean.valueOf(z2), stopUserCallback);
                int stopUser = this.mInterface.stopUser(parseInt, z2, stopUserCallback);
                if (stopUser == 0) {
                    if (stopUserCallback != null) {
                        stopUserCallback.waitForFinish();
                    }
                    return 0;
                }
                if (stopUser == -4) {
                    str = " (Can't stop user " + parseInt + " - one of its related users can't be stopped)";
                } else if (stopUser == -3) {
                    str = " (System user cannot be stopped)";
                } else if (stopUser == -2) {
                    str = " (Can't stop current user)";
                } else if (stopUser != -1) {
                    str = "";
                } else {
                    str = " (Unknown user " + parseInt + ")";
                }
                getErrPrintWriter().println("Switch failed: " + stopUser + str);
                return -1;
            }
        }
    }

    public int runIsUserStopped(PrintWriter printWriter) {
        printWriter.println(this.mInternal.isUserStopped(UserHandle.parseUserArg(getNextArgRequired())));
        return 0;
    }

    public int runGetStartedUserState(PrintWriter printWriter) {
        this.mInternal.enforceCallingPermission("android.permission.DUMP", "runGetStartedUserState()");
        int parseInt = Integer.parseInt(getNextArgRequired());
        try {
            printWriter.println(this.mInternal.getStartedUserState(parseInt));
            return 0;
        } catch (NullPointerException unused) {
            printWriter.println("User is not started: " + parseInt);
            return 0;
        }
    }

    public int runTrackAssociations(PrintWriter printWriter) {
        this.mInternal.enforceCallingPermission("android.permission.SET_ACTIVITY_WATCHER", "runTrackAssociations()");
        ActivityManagerService activityManagerService = this.mInternal;
        ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                ActivityManagerService activityManagerService2 = this.mInternal;
                if (!activityManagerService2.mTrackingAssociations) {
                    activityManagerService2.mTrackingAssociations = true;
                    printWriter.println("Association tracking started.");
                } else {
                    printWriter.println("Association tracking already enabled.");
                }
            } catch (Throwable th) {
                ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        ActivityManagerService.resetPriorityAfterLockedSection();
        return 0;
    }

    public int runUntrackAssociations(PrintWriter printWriter) {
        this.mInternal.enforceCallingPermission("android.permission.SET_ACTIVITY_WATCHER", "runUntrackAssociations()");
        ActivityManagerService activityManagerService = this.mInternal;
        ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                ActivityManagerService activityManagerService2 = this.mInternal;
                if (activityManagerService2.mTrackingAssociations) {
                    activityManagerService2.mTrackingAssociations = false;
                    activityManagerService2.mAssociations.clear();
                    printWriter.println("Association tracking stopped.");
                } else {
                    printWriter.println("Association tracking not running.");
                }
            } catch (Throwable th) {
                ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        ActivityManagerService.resetPriorityAfterLockedSection();
        return 0;
    }

    public int getUidState(PrintWriter printWriter) {
        this.mInternal.enforceCallingPermission("android.permission.DUMP", "getUidState()");
        int uidState = this.mInternal.getUidState(Integer.parseInt(getNextArgRequired()));
        printWriter.print(uidState);
        printWriter.print(" (");
        printWriter.printf(DebugUtils.valueToString(ActivityManager.class, "PROCESS_STATE_", uidState), new Object[0]);
        printWriter.println(")");
        return 0;
    }

    public final List getRecentConfigurations(int i) {
        IUsageStatsManager asInterface = IUsageStatsManager.Stub.asInterface(ServiceManager.getService("usagestats"));
        long currentTimeMillis = System.currentTimeMillis();
        try {
            ParceledListSlice queryConfigurationStats = asInterface.queryConfigurationStats(4, currentTimeMillis - ((((i * 24) * 60) * 60) * 1000), currentTimeMillis, "com.android.shell");
            if (queryConfigurationStats == null) {
                return Collections.emptyList();
            }
            final ArrayMap arrayMap = new ArrayMap();
            List list = queryConfigurationStats.getList();
            int size = list.size();
            for (int i2 = 0; i2 < size; i2++) {
                ConfigurationStats configurationStats = (ConfigurationStats) list.get(i2);
                int indexOfKey = arrayMap.indexOfKey(configurationStats.getConfiguration());
                if (indexOfKey < 0) {
                    arrayMap.put(configurationStats.getConfiguration(), Integer.valueOf(configurationStats.getActivationCount()));
                } else {
                    arrayMap.setValueAt(indexOfKey, Integer.valueOf(((Integer) arrayMap.valueAt(indexOfKey)).intValue() + configurationStats.getActivationCount()));
                }
            }
            Comparator comparator = new Comparator() { // from class: com.android.server.am.ActivityManagerShellCommand.4
                @Override // java.util.Comparator
                public int compare(Configuration configuration, Configuration configuration2) {
                    return ((Integer) arrayMap.get(configuration2)).compareTo((Integer) arrayMap.get(configuration));
                }
            };
            ArrayList arrayList = new ArrayList(arrayMap.size());
            arrayList.addAll(arrayMap.keySet());
            Collections.sort(arrayList, comparator);
            return arrayList;
        } catch (RemoteException unused) {
            return Collections.emptyList();
        }
    }

    public static void addExtensionsForConfig(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig eGLConfig, int[] iArr, int[] iArr2, Set set) {
        EGLContext eglCreateContext = egl10.eglCreateContext(eGLDisplay, eGLConfig, EGL10.EGL_NO_CONTEXT, iArr2);
        if (eglCreateContext == EGL10.EGL_NO_CONTEXT) {
            return;
        }
        EGLSurface eglCreatePbufferSurface = egl10.eglCreatePbufferSurface(eGLDisplay, eGLConfig, iArr);
        if (eglCreatePbufferSurface == EGL10.EGL_NO_SURFACE) {
            egl10.eglDestroyContext(eGLDisplay, eglCreateContext);
            return;
        }
        egl10.eglMakeCurrent(eGLDisplay, eglCreatePbufferSurface, eglCreatePbufferSurface, eglCreateContext);
        String glGetString = GLES10.glGetString(7939);
        if (!TextUtils.isEmpty(glGetString)) {
            for (String str : glGetString.split(" ")) {
                set.add(str);
            }
        }
        EGLSurface eGLSurface = EGL10.EGL_NO_SURFACE;
        egl10.eglMakeCurrent(eGLDisplay, eGLSurface, eGLSurface, EGL10.EGL_NO_CONTEXT);
        egl10.eglDestroySurface(eGLDisplay, eglCreatePbufferSurface);
        egl10.eglDestroyContext(eGLDisplay, eglCreateContext);
    }

    public Set getGlExtensionsFromDriver() {
        int i;
        HashSet hashSet = new HashSet();
        EGL10 egl10 = (EGL10) EGLContext.getEGL();
        if (egl10 == null) {
            getErrPrintWriter().println("Warning: couldn't get EGL");
            return hashSet;
        }
        EGLDisplay eglGetDisplay = egl10.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
        egl10.eglInitialize(eglGetDisplay, new int[2]);
        int[] iArr = new int[1];
        if (!egl10.eglGetConfigs(eglGetDisplay, null, 0, iArr)) {
            getErrPrintWriter().println("Warning: couldn't get EGL config count");
            return hashSet;
        }
        int i2 = iArr[0];
        EGLConfig[] eGLConfigArr = new EGLConfig[i2];
        if (!egl10.eglGetConfigs(eglGetDisplay, eGLConfigArr, i2, iArr)) {
            getErrPrintWriter().println("Warning: couldn't get EGL configs");
            return hashSet;
        }
        int[] iArr2 = {12375, 1, 12374, 1, 12344};
        int[] iArr3 = {12440, 2, 12344};
        int[] iArr4 = new int[1];
        for (int i3 = 0; i3 < iArr[0]; i3 = i + 1) {
            egl10.eglGetConfigAttrib(eglGetDisplay, eGLConfigArr[i3], 12327, iArr4);
            if (iArr4[0] != 12368) {
                egl10.eglGetConfigAttrib(eglGetDisplay, eGLConfigArr[i3], 12339, iArr4);
                if ((iArr4[0] & 1) != 0) {
                    egl10.eglGetConfigAttrib(eglGetDisplay, eGLConfigArr[i3], 12352, iArr4);
                    if ((iArr4[0] & 1) != 0) {
                        i = i3;
                        addExtensionsForConfig(egl10, eglGetDisplay, eGLConfigArr[i3], iArr2, null, hashSet);
                    } else {
                        i = i3;
                    }
                    if ((iArr4[0] & 4) != 0) {
                        addExtensionsForConfig(egl10, eglGetDisplay, eGLConfigArr[i], iArr2, iArr3, hashSet);
                    }
                }
            }
            i = i3;
        }
        egl10.eglTerminate(eglGetDisplay);
        return hashSet;
    }

    public final void writeDeviceConfig(ProtoOutputStream protoOutputStream, long j, PrintWriter printWriter, Configuration configuration, DisplayMetrics displayMetrics) {
        long j2;
        if (protoOutputStream != null) {
            j2 = protoOutputStream.start(j);
            protoOutputStream.write(1155346202625L, displayMetrics.widthPixels);
            protoOutputStream.write(1155346202626L, displayMetrics.heightPixels);
            protoOutputStream.write(1155346202627L, DisplayMetrics.DENSITY_DEVICE_STABLE);
        } else {
            j2 = -1;
        }
        if (printWriter != null) {
            printWriter.print("stable-width-px: ");
            printWriter.println(displayMetrics.widthPixels);
            printWriter.print("stable-height-px: ");
            printWriter.println(displayMetrics.heightPixels);
            printWriter.print("stable-density-dpi: ");
            printWriter.println(DisplayMetrics.DENSITY_DEVICE_STABLE);
        }
        MemInfoReader memInfoReader = new MemInfoReader();
        memInfoReader.readMemInfo();
        KeyguardManager keyguardManager = (KeyguardManager) this.mInternal.mContext.getSystemService(KeyguardManager.class);
        if (protoOutputStream != null) {
            protoOutputStream.write(1116691496964L, memInfoReader.getTotalSize());
            protoOutputStream.write(1133871366149L, ActivityManager.isLowRamDeviceStatic());
            protoOutputStream.write(1155346202630L, Runtime.getRuntime().availableProcessors());
            protoOutputStream.write(1133871366151L, keyguardManager.isDeviceSecure());
        }
        if (printWriter != null) {
            printWriter.print("total-ram: ");
            printWriter.println(memInfoReader.getTotalSize());
            printWriter.print("low-ram: ");
            printWriter.println(ActivityManager.isLowRamDeviceStatic());
            printWriter.print("max-cores: ");
            printWriter.println(Runtime.getRuntime().availableProcessors());
            printWriter.print("has-secure-screen-lock: ");
            printWriter.println(keyguardManager.isDeviceSecure());
        }
        try {
            ConfigurationInfo deviceConfigurationInfo = this.mTaskInterface.getDeviceConfigurationInfo();
            int i = deviceConfigurationInfo.reqGlEsVersion;
            if (i != 0) {
                if (protoOutputStream != null) {
                    protoOutputStream.write(1155346202632L, i);
                }
                if (printWriter != null) {
                    printWriter.print("opengl-version: 0x");
                    printWriter.println(Integer.toHexString(deviceConfigurationInfo.reqGlEsVersion));
                }
            }
            Set glExtensionsFromDriver = getGlExtensionsFromDriver();
            String[] strArr = (String[]) glExtensionsFromDriver.toArray(new String[glExtensionsFromDriver.size()]);
            Arrays.sort(strArr);
            for (int i2 = 0; i2 < strArr.length; i2++) {
                if (protoOutputStream != null) {
                    protoOutputStream.write(2237677961225L, strArr[i2]);
                }
                if (printWriter != null) {
                    printWriter.print("opengl-extensions: ");
                    printWriter.println(strArr[i2]);
                }
            }
            PackageManager packageManager = this.mInternal.mContext.getPackageManager();
            List<SharedLibraryInfo> sharedLibraries = packageManager.getSharedLibraries(0);
            Collections.sort(sharedLibraries, Comparator.comparing(new Function() { // from class: com.android.server.am.ActivityManagerShellCommand$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return ((SharedLibraryInfo) obj).getName();
                }
            }));
            for (int i3 = 0; i3 < sharedLibraries.size(); i3++) {
                if (protoOutputStream != null) {
                    protoOutputStream.write(2237677961226L, sharedLibraries.get(i3).getName());
                }
                if (printWriter != null) {
                    printWriter.print("shared-libraries: ");
                    printWriter.println(sharedLibraries.get(i3).getName());
                }
            }
            FeatureInfo[] systemAvailableFeatures = packageManager.getSystemAvailableFeatures();
            Arrays.sort(systemAvailableFeatures, new Comparator() { // from class: com.android.server.am.ActivityManagerShellCommand$$ExternalSyntheticLambda1
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    int lambda$writeDeviceConfig$0;
                    lambda$writeDeviceConfig$0 = ActivityManagerShellCommand.lambda$writeDeviceConfig$0((FeatureInfo) obj, (FeatureInfo) obj2);
                    return lambda$writeDeviceConfig$0;
                }
            });
            for (int i4 = 0; i4 < systemAvailableFeatures.length; i4++) {
                String str = systemAvailableFeatures[i4].name;
                if (str != null) {
                    if (protoOutputStream != null) {
                        protoOutputStream.write(2237677961227L, str);
                    }
                    if (printWriter != null) {
                        printWriter.print("features: ");
                        printWriter.println(systemAvailableFeatures[i4].name);
                    }
                }
            }
            if (protoOutputStream != null) {
                protoOutputStream.end(j2);
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static /* synthetic */ int lambda$writeDeviceConfig$0(FeatureInfo featureInfo, FeatureInfo featureInfo2) {
        String str = featureInfo.name;
        String str2 = featureInfo2.name;
        if (str == str2) {
            return 0;
        }
        if (str == null) {
            return -1;
        }
        if (str2 == null) {
            return 1;
        }
        return str.compareTo(str2);
    }

    public final int getDisplayIdFromNextArg() {
        int parseInt = Integer.parseInt(getNextArgRequired());
        if (parseInt >= 0) {
            return parseInt;
        }
        throw new IllegalArgumentException("--display must be a non-negative integer");
    }

    public int runGetConfig(PrintWriter printWriter) {
        List recentConfigurations;
        int size;
        int i = -1;
        int i2 = 0;
        boolean z = false;
        boolean z2 = false;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption != null) {
                if (nextOption.equals("--days")) {
                    i = Integer.parseInt(getNextArgRequired());
                    if (i <= 0) {
                        throw new IllegalArgumentException("--days must be a positive integer");
                    }
                } else if (nextOption.equals("--proto")) {
                    z = true;
                } else if (nextOption.equals("--device")) {
                    z2 = true;
                } else if (nextOption.equals("--display")) {
                    i2 = getDisplayIdFromNextArg();
                } else {
                    getErrPrintWriter().println("Error: Unknown option: " + nextOption);
                    return -1;
                }
            } else {
                Configuration configuration = this.mInterface.getConfiguration();
                if (configuration == null) {
                    getErrPrintWriter().println("Activity manager has no configuration");
                    return -1;
                }
                Display display = ((DisplayManager) this.mInternal.mContext.getSystemService(DisplayManager.class)).getDisplay(i2);
                if (display == null) {
                    getErrPrintWriter().println("Error: Display does not exist: " + i2);
                    return -1;
                }
                DisplayMetrics displayMetrics = new DisplayMetrics();
                display.getMetrics(displayMetrics);
                if (z) {
                    ProtoOutputStream protoOutputStream = new ProtoOutputStream(getOutFileDescriptor());
                    configuration.writeResConfigToProto(protoOutputStream, 1146756268033L, displayMetrics);
                    if (z2) {
                        writeDeviceConfig(protoOutputStream, 1146756268034L, null, configuration, displayMetrics);
                    }
                    protoOutputStream.flush();
                } else {
                    printWriter.println("config: " + Configuration.resourceQualifierString(configuration, displayMetrics));
                    printWriter.println("abi: " + TextUtils.join(",", Build.SUPPORTED_ABIS));
                    if (z2) {
                        writeDeviceConfig(null, -1L, printWriter, configuration, displayMetrics);
                    }
                    if (i >= 0 && (size = (recentConfigurations = getRecentConfigurations(i)).size()) > 0) {
                        printWriter.println("recentConfigs:");
                        for (int i3 = 0; i3 < size; i3++) {
                            printWriter.println("  config: " + Configuration.resourceQualifierString((Configuration) recentConfigurations.get(i3)));
                        }
                    }
                }
                return 0;
            }
        }
    }

    public int runSuppressResizeConfigChanges(PrintWriter printWriter) {
        this.mTaskInterface.suppressResizeConfigChanges(Boolean.valueOf(getNextArgRequired()).booleanValue());
        return 0;
    }

    public int runSetInactive(PrintWriter printWriter) {
        int i = -2;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption != null) {
                if (nextOption.equals("--user")) {
                    i = UserHandle.parseUserArg(getNextArgRequired());
                } else {
                    getErrPrintWriter().println("Error: Unknown option: " + nextOption);
                    return -1;
                }
            } else {
                IUsageStatsManager.Stub.asInterface(ServiceManager.getService("usagestats")).setAppInactive(getNextArgRequired(), Boolean.parseBoolean(getNextArgRequired()), i);
                return 0;
            }
        }
    }

    public final int bucketNameToBucketValue(String str) {
        String lowerCase = str.toLowerCase();
        if (lowerCase.startsWith("ac")) {
            return 10;
        }
        if (lowerCase.startsWith("wo")) {
            return 20;
        }
        if (lowerCase.startsWith("fr")) {
            return 30;
        }
        if (lowerCase.startsWith("ra")) {
            return 40;
        }
        if (lowerCase.startsWith("re")) {
            return 45;
        }
        if (lowerCase.startsWith("ne")) {
            return 50;
        }
        try {
            return Integer.parseInt(lowerCase);
        } catch (NumberFormatException unused) {
            this.getErrPrintWriter().println("Error: Unknown bucket: " + str);
            return -1;
        }
    }

    public int runSetStandbyBucket(PrintWriter printWriter) {
        int i = -2;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption != null) {
                if (nextOption.equals("--user")) {
                    i = UserHandle.parseUserArg(getNextArgRequired());
                } else {
                    getErrPrintWriter().println("Error: Unknown option: " + nextOption);
                    return -1;
                }
            } else {
                String nextArgRequired = getNextArgRequired();
                String nextArgRequired2 = getNextArgRequired();
                int bucketNameToBucketValue = bucketNameToBucketValue(nextArgRequired2);
                if (bucketNameToBucketValue < 0) {
                    return -1;
                }
                boolean z = peekNextArg() != null;
                IUsageStatsManager asInterface = IUsageStatsManager.Stub.asInterface(ServiceManager.getService("usagestats"));
                if (!z) {
                    asInterface.setAppStandbyBucket(nextArgRequired, bucketNameToBucketValue(nextArgRequired2), i);
                } else {
                    ArrayList arrayList = new ArrayList();
                    arrayList.add(new AppStandbyInfo(nextArgRequired, bucketNameToBucketValue));
                    while (true) {
                        String nextArg = getNextArg();
                        if (nextArg == null) {
                            break;
                        }
                        int bucketNameToBucketValue2 = bucketNameToBucketValue(getNextArgRequired());
                        if (bucketNameToBucketValue2 >= 0) {
                            arrayList.add(new AppStandbyInfo(nextArg, bucketNameToBucketValue2));
                        }
                    }
                    asInterface.setAppStandbyBuckets(new ParceledListSlice(arrayList), i);
                }
                return 0;
            }
        }
    }

    public int runGetStandbyBucket(PrintWriter printWriter) {
        int i = -2;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption != null) {
                if (nextOption.equals("--user")) {
                    i = UserHandle.parseUserArg(getNextArgRequired());
                } else {
                    getErrPrintWriter().println("Error: Unknown option: " + nextOption);
                    return -1;
                }
            } else {
                String nextArg = getNextArg();
                IUsageStatsManager asInterface = IUsageStatsManager.Stub.asInterface(ServiceManager.getService("usagestats"));
                if (nextArg != null) {
                    printWriter.println(asInterface.getAppStandbyBucket(nextArg, (String) null, i));
                    return 0;
                }
                for (AppStandbyInfo appStandbyInfo : asInterface.getAppStandbyBuckets("com.android.shell", i).getList()) {
                    printWriter.print(appStandbyInfo.mPackageName);
                    printWriter.print(": ");
                    printWriter.println(appStandbyInfo.mStandbyBucket);
                }
                return 0;
            }
        }
    }

    public int runGetInactive(PrintWriter printWriter) {
        int i = -2;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption != null) {
                if (nextOption.equals("--user")) {
                    i = UserHandle.parseUserArg(getNextArgRequired());
                } else {
                    getErrPrintWriter().println("Error: Unknown option: " + nextOption);
                    return -1;
                }
            } else {
                printWriter.println("Idle=" + IUsageStatsManager.Stub.asInterface(ServiceManager.getService("usagestats")).isAppInactive(getNextArgRequired(), i, "com.android.shell"));
                return 0;
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public int runSendTrimMemory(PrintWriter printWriter) {
        char c;
        int i = -2;
        do {
            String nextOption = getNextOption();
            if (nextOption != null) {
                if (nextOption.equals("--user")) {
                    i = UserHandle.parseUserArg(getNextArgRequired());
                } else {
                    getErrPrintWriter().println("Error: Unknown option: " + nextOption);
                    return -1;
                }
            } else {
                String nextArgRequired = getNextArgRequired();
                String nextArgRequired2 = getNextArgRequired();
                nextArgRequired2.hashCode();
                int i2 = 5;
                switch (nextArgRequired2.hashCode()) {
                    case -1943119297:
                        if (nextArgRequired2.equals("RUNNING_CRITICAL")) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    case -847101650:
                        if (nextArgRequired2.equals("BACKGROUND")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case -219160669:
                        if (nextArgRequired2.equals("RUNNING_MODERATE")) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    case 163769603:
                        if (nextArgRequired2.equals("MODERATE")) {
                            c = 3;
                            break;
                        }
                        c = 65535;
                        break;
                    case 183181625:
                        if (nextArgRequired2.equals("COMPLETE")) {
                            c = 4;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1072631956:
                        if (nextArgRequired2.equals("RUNNING_LOW")) {
                            c = 5;
                            break;
                        }
                        c = 65535;
                        break;
                    case 2130809258:
                        if (nextArgRequired2.equals("HIDDEN")) {
                            c = 6;
                            break;
                        }
                        c = 65535;
                        break;
                    default:
                        c = 65535;
                        break;
                }
                switch (c) {
                    case 0:
                        i2 = 15;
                        break;
                    case 1:
                        i2 = 40;
                        break;
                    case 2:
                        break;
                    case 3:
                        i2 = 60;
                        break;
                    case 4:
                        i2 = 80;
                        break;
                    case 5:
                        i2 = 10;
                        break;
                    case 6:
                        i2 = 20;
                        break;
                    default:
                        try {
                            i2 = Integer.parseInt(nextArgRequired2);
                            break;
                        } catch (NumberFormatException unused) {
                            getErrPrintWriter().println("Error: Unknown level option: " + nextArgRequired2);
                            return -1;
                        }
                }
                if (this.mInterface.setProcessMemoryTrimLevel(nextArgRequired, i, i2)) {
                    return 0;
                }
                getErrPrintWriter().println("Unknown error: failed to set trim level");
                return -1;
            }
        } while (i != -1);
        getErrPrintWriter().println("Error: Can't use user 'all'");
        return -1;
    }

    public int runDisplay(PrintWriter printWriter) {
        String nextArgRequired = getNextArgRequired();
        nextArgRequired.hashCode();
        if (nextArgRequired.equals("move-stack")) {
            return runDisplayMoveStack(printWriter);
        }
        getErrPrintWriter().println("Error: unknown command '" + nextArgRequired + "'");
        return -1;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public int runStack(PrintWriter printWriter) {
        char c;
        String nextArgRequired = getNextArgRequired();
        nextArgRequired.hashCode();
        switch (nextArgRequired.hashCode()) {
            case -934610812:
                if (nextArgRequired.equals("remove")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 3237038:
                if (nextArgRequired.equals("info")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 3322014:
                if (nextArgRequired.equals("list")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 1022285313:
                if (nextArgRequired.equals("move-task")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                return runRootTaskRemove(printWriter);
            case 1:
                return runRootTaskInfo(printWriter);
            case 2:
                return runStackList(printWriter);
            case 3:
                return runStackMoveTask(printWriter);
            default:
                getErrPrintWriter().println("Error: unknown command '" + nextArgRequired + "'");
                return -1;
        }
    }

    public final Rect getBounds() {
        String nextArgRequired = getNextArgRequired();
        int parseInt = Integer.parseInt(nextArgRequired);
        String nextArgRequired2 = getNextArgRequired();
        int parseInt2 = Integer.parseInt(nextArgRequired2);
        String nextArgRequired3 = getNextArgRequired();
        int parseInt3 = Integer.parseInt(nextArgRequired3);
        String nextArgRequired4 = getNextArgRequired();
        int parseInt4 = Integer.parseInt(nextArgRequired4);
        if (parseInt < 0) {
            getErrPrintWriter().println("Error: bad left arg: " + nextArgRequired);
            return null;
        }
        if (parseInt2 < 0) {
            getErrPrintWriter().println("Error: bad top arg: " + nextArgRequired2);
            return null;
        }
        if (parseInt3 <= 0) {
            getErrPrintWriter().println("Error: bad right arg: " + nextArgRequired3);
            return null;
        }
        if (parseInt4 <= 0) {
            getErrPrintWriter().println("Error: bad bottom arg: " + nextArgRequired4);
            return null;
        }
        return new Rect(parseInt, parseInt2, parseInt3, parseInt4);
    }

    public int runDisplayMoveStack(PrintWriter printWriter) {
        this.mTaskInterface.moveRootTaskToDisplay(Integer.parseInt(getNextArgRequired()), Integer.parseInt(getNextArgRequired()));
        return 0;
    }

    public int runStackMoveTask(PrintWriter printWriter) {
        boolean z;
        int parseInt = Integer.parseInt(getNextArgRequired());
        int parseInt2 = Integer.parseInt(getNextArgRequired());
        String nextArgRequired = getNextArgRequired();
        if ("true".equals(nextArgRequired)) {
            z = true;
        } else {
            if (!"false".equals(nextArgRequired)) {
                getErrPrintWriter().println("Error: bad toTop arg: " + nextArgRequired);
                return -1;
            }
            z = false;
        }
        this.mTaskInterface.moveTaskToRootTask(parseInt, parseInt2, z);
        return 0;
    }

    public int runStackList(PrintWriter printWriter) {
        Iterator it = this.mTaskInterface.getAllRootTaskInfos().iterator();
        while (it.hasNext()) {
            printWriter.println((ActivityTaskManager.RootTaskInfo) it.next());
        }
        return 0;
    }

    public int runRootTaskInfo(PrintWriter printWriter) {
        printWriter.println(this.mTaskInterface.getRootTaskInfo(Integer.parseInt(getNextArgRequired()), Integer.parseInt(getNextArgRequired())));
        return 0;
    }

    public int runRootTaskRemove(PrintWriter printWriter) {
        this.mTaskInterface.removeTask(Integer.parseInt(getNextArgRequired()));
        return 0;
    }

    public int runTask(PrintWriter printWriter) {
        String nextArgRequired = getNextArgRequired();
        if (nextArgRequired.equals("lock")) {
            return runTaskLock(printWriter);
        }
        if (nextArgRequired.equals("resizeable")) {
            return runTaskResizeable(printWriter);
        }
        if (nextArgRequired.equals("resize")) {
            return runTaskResize(printWriter);
        }
        if (nextArgRequired.equals("focus")) {
            return runTaskFocus(printWriter);
        }
        getErrPrintWriter().println("Error: unknown command '" + nextArgRequired + "'");
        return -1;
    }

    public int runTaskLock(PrintWriter printWriter) {
        String nextArgRequired = getNextArgRequired();
        if (nextArgRequired.equals("stop")) {
            this.mTaskInterface.stopSystemLockTaskMode();
        } else {
            this.mTaskInterface.startSystemLockTaskMode(Integer.parseInt(nextArgRequired));
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Activity manager is ");
        sb.append(this.mTaskInterface.isInLockTaskMode() ? "" : "not ");
        sb.append("in lockTaskMode");
        printWriter.println(sb.toString());
        return 0;
    }

    public int runTaskResizeable(PrintWriter printWriter) {
        this.mTaskInterface.setTaskResizeable(Integer.parseInt(getNextArgRequired()), Integer.parseInt(getNextArgRequired()));
        return 0;
    }

    public int runTaskResize(PrintWriter printWriter) {
        int parseInt = Integer.parseInt(getNextArgRequired());
        Rect bounds = getBounds();
        if (bounds == null) {
            getErrPrintWriter().println("Error: invalid input bounds");
            return -1;
        }
        taskResize(parseInt, bounds, 0, false);
        return 0;
    }

    public void taskResize(int i, Rect rect, int i2, boolean z) {
        this.mTaskInterface.resizeTask(i, rect, z ? 1 : 0);
        try {
            Thread.sleep(i2);
        } catch (InterruptedException unused) {
        }
    }

    public int runTaskFocus(PrintWriter printWriter) {
        int parseInt = Integer.parseInt(getNextArgRequired());
        printWriter.println("Setting focus to task " + parseInt);
        this.mTaskInterface.setFocusedTask(parseInt);
        return 0;
    }

    public int runWrite(PrintWriter printWriter) {
        this.mInternal.enforceCallingPermission("android.permission.SET_ACTIVITY_WATCHER", "registerUidObserver()");
        this.mInternal.mAtmInternal.flushRecentTasks();
        printWriter.println("All tasks persisted.");
        return 0;
    }

    public int runAttachAgent(PrintWriter printWriter) {
        this.mInternal.enforceCallingPermission("android.permission.SET_ACTIVITY_WATCHER", "attach-agent");
        String nextArgRequired = getNextArgRequired();
        String nextArgRequired2 = getNextArgRequired();
        String nextArg = getNextArg();
        if (nextArg != null) {
            printWriter.println("Error: Unknown option: " + nextArg);
            return -1;
        }
        this.mInternal.attachAgent(nextArgRequired, nextArgRequired2);
        return 0;
    }

    public int runSupportsMultiwindow(PrintWriter printWriter) {
        if (getResources(printWriter) == null) {
            return -1;
        }
        printWriter.println(ActivityTaskManager.supportsMultiWindow(this.mInternal.mContext));
        return 0;
    }

    public int runSupportsSplitScreenMultiwindow(PrintWriter printWriter) {
        if (getResources(printWriter) == null) {
            return -1;
        }
        printWriter.println(ActivityTaskManager.supportsSplitScreenMultiWindow(this.mInternal.mContext));
        return 0;
    }

    public int runUpdateApplicationInfo(PrintWriter printWriter) {
        int parseUserArg = UserHandle.parseUserArg(getNextArgRequired());
        ArrayList arrayList = new ArrayList();
        arrayList.add(getNextArgRequired());
        while (true) {
            String nextArg = getNextArg();
            if (nextArg != null) {
                arrayList.add(nextArg);
            } else {
                this.mInternal.scheduleApplicationInfoChanged(arrayList, parseUserArg);
                printWriter.println("Packages updated with most recent ApplicationInfos.");
                return 0;
            }
        }
    }

    public int runNoHomeScreen(PrintWriter printWriter) {
        Resources resources = getResources(printWriter);
        if (resources == null) {
            return -1;
        }
        printWriter.println(resources.getBoolean(17891782));
        return 0;
    }

    public int runWaitForBroadcastIdle(PrintWriter printWriter) {
        PrintWriter printWriter2 = new PrintWriter((Writer) new TeeWriter(new Writer[]{ActivityManagerDebugConfig.LOG_WRITER_INFO, printWriter}));
        boolean z = false;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption != null) {
                if (!nextOption.equals("--flush-broadcast-loopers")) {
                    getErrPrintWriter().println("Error: Unknown option: " + nextOption);
                    return -1;
                }
                z = true;
            } else {
                this.mInternal.waitForBroadcastIdle(printWriter2, z);
                return 0;
            }
        }
    }

    public int runWaitForBroadcastBarrier(PrintWriter printWriter) {
        PrintWriter printWriter2 = new PrintWriter((Writer) new TeeWriter(new Writer[]{ActivityManagerDebugConfig.LOG_WRITER_INFO, printWriter}));
        boolean z = false;
        boolean z2 = false;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption != null) {
                if (nextOption.equals("--flush-broadcast-loopers")) {
                    z = true;
                } else {
                    if (!nextOption.equals("--flush-application-threads")) {
                        getErrPrintWriter().println("Error: Unknown option: " + nextOption);
                        return -1;
                    }
                    z2 = true;
                }
            } else {
                this.mInternal.waitForBroadcastBarrier(printWriter2, z, z2);
                return 0;
            }
        }
    }

    public int runWaitForApplicationBarrier(PrintWriter printWriter) {
        this.mInternal.waitForApplicationBarrier(new PrintWriter((Writer) new TeeWriter(new Writer[]{ActivityManagerDebugConfig.LOG_WRITER_INFO, printWriter})));
        return 0;
    }

    public int runWaitForBroadcastDispatch(PrintWriter printWriter) {
        try {
            this.mInternal.waitForBroadcastDispatch(new PrintWriter((Writer) new TeeWriter(new Writer[]{ActivityManagerDebugConfig.LOG_WRITER_INFO, printWriter})), makeIntent(-2));
            return 0;
        } catch (URISyntaxException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public int runSetIgnoreDeliveryGroupPolicy(PrintWriter printWriter) {
        this.mInternal.setIgnoreDeliveryGroupPolicy(getNextArgRequired());
        return 0;
    }

    public int runClearIgnoreDeliveryGroupPolicy(PrintWriter printWriter) {
        this.mInternal.clearIgnoreDeliveryGroupPolicy(getNextArgRequired());
        return 0;
    }

    public int runRefreshSettingsCache() {
        this.mInternal.refreshSettingsCache();
        return 0;
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x00e1  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x01db  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int runCompat(java.io.PrintWriter r18) {
        /*
            Method dump skipped, instructions count: 595
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.am.ActivityManagerShellCommand.runCompat(java.io.PrintWriter):int");
    }

    public final int runGetCurrentForegroundProcess(PrintWriter printWriter, IActivityManager iActivityManager, IActivityTaskManager iActivityTaskManager) {
        boolean z;
        ProcessObserver processObserver = new ProcessObserver(printWriter, iActivityManager, iActivityTaskManager, this.mInternal);
        iActivityManager.registerProcessObserver(processObserver);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getRawInputStream()));
        while (true) {
            try {
                try {
                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        break;
                    }
                    if (readLine.length() > 0) {
                        if ("q".equals(readLine) || "quit".equals(readLine)) {
                            break;
                        }
                        printWriter.println("Invalid command: " + readLine);
                        z = true;
                    } else {
                        z = false;
                    }
                    if (z) {
                        printWriter.println("");
                    }
                    printWriter.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                    printWriter.flush();
                }
            } finally {
                iActivityManager.unregisterProcessObserver(processObserver);
            }
        }
        return 0;
    }

    /* loaded from: classes.dex */
    public final class ProcessObserver extends IProcessObserver.Stub {
        public IActivityManager mIam;
        public IActivityTaskManager mIatm;
        public ActivityManagerService mInternal;
        public PrintWriter mPw;

        public void onForegroundServicesChanged(int i, int i2, int i3) {
        }

        public void onProcessDied(int i, int i2) {
        }

        public ProcessObserver(PrintWriter printWriter, IActivityManager iActivityManager, IActivityTaskManager iActivityTaskManager, ActivityManagerService activityManagerService) {
            this.mPw = printWriter;
            this.mIam = iActivityManager;
            this.mIatm = iActivityTaskManager;
            this.mInternal = activityManagerService;
        }

        public void onForegroundActivitiesChanged(int i, int i2, boolean z) {
            if (z) {
                try {
                    int uidProcessState = this.mIam.getUidProcessState(i2, "android");
                    ProcessRecord topApp = this.mInternal.getTopApp();
                    if (topApp == null) {
                        this.mPw.println("No top app found");
                    } else {
                        int pid = topApp.getPid();
                        if (uidProcessState == 2 && pid == i) {
                            this.mPw.println("New foreground process: " + i);
                        }
                    }
                    this.mPw.flush();
                } catch (RemoteException unused) {
                    this.mPw.println("Error occurred in binder call");
                    this.mPw.flush();
                }
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public final int runSetMemoryFactor(PrintWriter printWriter) {
        boolean z;
        String nextArgRequired = getNextArgRequired();
        nextArgRequired.hashCode();
        int i = 2;
        switch (nextArgRequired.hashCode()) {
            case -1986416409:
                if (nextArgRequired.equals("NORMAL")) {
                    z = false;
                    break;
                }
                z = -1;
                break;
            case -1560189025:
                if (nextArgRequired.equals("CRITICAL")) {
                    z = true;
                    break;
                }
                z = -1;
                break;
            case 75572:
                if (nextArgRequired.equals("LOW")) {
                    z = 2;
                    break;
                }
                z = -1;
                break;
            case 163769603:
                if (nextArgRequired.equals("MODERATE")) {
                    z = 3;
                    break;
                }
                z = -1;
                break;
            default:
                z = -1;
                break;
        }
        switch (z) {
            case false:
                i = 0;
                break;
            case true:
                i = 3;
                break;
            case true:
                break;
            case true:
                i = 1;
                break;
            default:
                try {
                    i = Integer.parseInt(nextArgRequired);
                } catch (NumberFormatException unused) {
                    i = -1;
                }
                if (i < 0 || i > 3) {
                    getErrPrintWriter().println("Error: Unknown level option: " + nextArgRequired);
                    return -1;
                }
        }
        this.mInternal.setMemFactorOverride(i);
        return 0;
    }

    public final int runShowMemoryFactor(PrintWriter printWriter) {
        int memoryTrimLevel = this.mInternal.getMemoryTrimLevel();
        if (memoryTrimLevel == -1) {
            printWriter.println("<UNKNOWN>");
        } else if (memoryTrimLevel == 0) {
            printWriter.println("NORMAL");
        } else if (memoryTrimLevel == 1) {
            printWriter.println("MODERATE");
        } else if (memoryTrimLevel == 2) {
            printWriter.println("LOW");
        } else if (memoryTrimLevel == 3) {
            printWriter.println("CRITICAL");
        }
        printWriter.flush();
        return 0;
    }

    public final int runResetMemoryFactor(PrintWriter printWriter) {
        this.mInternal.setMemFactorOverride(-1);
        return 0;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public final int runMemoryFactor(PrintWriter printWriter) {
        char c;
        this.mInternal.enforceCallingPermission("android.permission.WRITE_SECURE_SETTINGS", "runMemoryFactor()");
        String nextArgRequired = getNextArgRequired();
        nextArgRequired.hashCode();
        switch (nextArgRequired.hashCode()) {
            case 113762:
                if (nextArgRequired.equals("set")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 3529469:
                if (nextArgRequired.equals(KnoxCustomManagerService.SHOW)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 108404047:
                if (nextArgRequired.equals("reset")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                return runSetMemoryFactor(printWriter);
            case 1:
                return runShowMemoryFactor(printWriter);
            case 2:
                return runResetMemoryFactor(printWriter);
            default:
                getErrPrintWriter().println("Error: unknown command '" + nextArgRequired + "'");
                return -1;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public final int runServiceRestartBackoff(PrintWriter printWriter) {
        char c;
        this.mInternal.enforceCallingPermission("android.permission.SET_PROCESS_LIMIT", "runServiceRestartBackoff()");
        String nextArgRequired = getNextArgRequired();
        nextArgRequired.hashCode();
        switch (nextArgRequired.hashCode()) {
            case -1298848381:
                if (nextArgRequired.equals("enable")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 3529469:
                if (nextArgRequired.equals(KnoxCustomManagerService.SHOW)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 1671308008:
                if (nextArgRequired.equals("disable")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                this.mInternal.setServiceRestartBackoffEnabled(getNextArgRequired(), true, "shell");
                return 0;
            case 1:
                printWriter.println(this.mInternal.isServiceRestartBackoffEnabled(getNextArgRequired()) ? "enabled" : "disabled");
                return 0;
            case 2:
                this.mInternal.setServiceRestartBackoffEnabled(getNextArgRequired(), false, "shell");
                return 0;
            default:
                getErrPrintWriter().println("Error: unknown command '" + nextArgRequired + "'");
                return -1;
        }
    }

    public final int runGetIsolatedProcesses(PrintWriter printWriter) {
        this.mInternal.enforceCallingPermission("android.permission.DUMP", "getIsolatedProcesses()");
        List isolatedProcesses = this.mInternal.mInternal.getIsolatedProcesses(Integer.parseInt(getNextArgRequired()));
        printWriter.print("[");
        if (isolatedProcesses != null) {
            int size = isolatedProcesses.size();
            for (int i = 0; i < size; i++) {
                if (i > 0) {
                    printWriter.print(", ");
                }
                printWriter.print(isolatedProcesses.get(i));
            }
        }
        printWriter.println("]");
        return 0;
    }

    public final int runSetStopUserOnSwitch(PrintWriter printWriter) {
        this.mInternal.enforceCallingPermission("android.permission.INTERACT_ACROSS_USERS_FULL", "setStopUserOnSwitch()");
        String nextArg = getNextArg();
        if (nextArg == null) {
            Slogf.i("ActivityManager", "setStopUserOnSwitch(): resetting to default value");
            this.mInternal.setStopUserOnSwitch(-1);
            printWriter.println("Reset to default value");
            return 0;
        }
        boolean parseBoolean = Boolean.parseBoolean(nextArg);
        Slogf.i("ActivityManager", "runSetStopUserOnSwitch(): setting to %d (%b)", Integer.valueOf(parseBoolean ? 1 : 0), Boolean.valueOf(parseBoolean));
        this.mInternal.setStopUserOnSwitch(parseBoolean ? 1 : 0);
        printWriter.println("Set to " + parseBoolean);
        return 0;
    }

    public final int runSetBgAbusiveUids(PrintWriter printWriter) {
        String nextArg = getNextArg();
        AppBatteryTracker appBatteryTracker = (AppBatteryTracker) this.mInternal.mAppRestrictionController.getAppStateTracker(AppBatteryTracker.class);
        if (appBatteryTracker == null) {
            getErrPrintWriter().println("Unable to get bg battery tracker");
            return -1;
        }
        if (nextArg == null) {
            appBatteryTracker.clearDebugUidPercentage();
            return 0;
        }
        String[] split = nextArg.split(",");
        int[] iArr = new int[split.length];
        double[][] dArr = new double[split.length];
        for (int i = 0; i < split.length; i++) {
            try {
                String[] split2 = split[i].split("=");
                if (split2.length != 2) {
                    getErrPrintWriter().println("Malformed input");
                    return -1;
                }
                iArr[i] = Integer.parseInt(split2[0]);
                String[] split3 = split2[1].split(XmlUtils.STRING_ARRAY_SEPARATOR);
                if (split3.length != 5) {
                    getErrPrintWriter().println("Malformed input");
                    return -1;
                }
                dArr[i] = new double[split3.length];
                for (int i2 = 0; i2 < split3.length; i2++) {
                    dArr[i][i2] = Double.parseDouble(split3[i2]);
                }
            } catch (NumberFormatException unused) {
                getErrPrintWriter().println("Malformed input");
                return -1;
            }
        }
        appBatteryTracker.setDebugUidPercentage(iArr, dArr);
        return 0;
    }

    public final int runListBgExemptionsConfig(PrintWriter printWriter) {
        ArraySet arraySet = this.mInternal.mAppRestrictionController.mBgRestrictionExemptioFromSysConfig;
        if (arraySet != null) {
            int size = arraySet.size();
            for (int i = 0; i < size; i++) {
                printWriter.print((String) arraySet.valueAt(i));
                printWriter.print(' ');
            }
            printWriter.println();
        }
        return 0;
    }

    public final int restrictionNameToLevel(String str) {
        String lowerCase = str.toLowerCase();
        lowerCase.hashCode();
        char c = 65535;
        switch (lowerCase.hashCode()) {
            case -1502662066:
                if (lowerCase.equals("restricted_bucket")) {
                    c = 0;
                    break;
                }
                break;
            case -1126569803:
                if (lowerCase.equals("hibernation")) {
                    c = 1;
                    break;
                }
                break;
            case -775446516:
                if (lowerCase.equals("background_restricted")) {
                    c = 2;
                    break;
                }
                break;
            case 824339380:
                if (lowerCase.equals("unrestricted")) {
                    c = 3;
                    break;
                }
                break;
            case 1351638995:
                if (lowerCase.equals("adaptive_bucket")) {
                    c = 4;
                    break;
                }
                break;
            case 2052103358:
                if (lowerCase.equals("exempted")) {
                    c = 5;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                return 40;
            case 1:
                return 60;
            case 2:
                return 50;
            case 3:
                return 10;
            case 4:
                return 30;
            case 5:
                return 20;
            default:
                return 0;
        }
    }

    public int runSetBgRestrictionLevel(PrintWriter printWriter) {
        int i = -2;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption != null) {
                if (nextOption.equals("--user")) {
                    i = UserHandle.parseUserArg(getNextArgRequired());
                } else {
                    getErrPrintWriter().println("Error: Unknown option: " + nextOption);
                    return -1;
                }
            } else {
                String nextArgRequired = getNextArgRequired();
                int restrictionNameToLevel = restrictionNameToLevel(getNextArgRequired());
                if (restrictionNameToLevel == 0) {
                    printWriter.println("Error: invalid restriction level");
                    return -1;
                }
                try {
                    this.mInternal.setBackgroundRestrictionLevel(nextArgRequired, this.mInternal.mContext.getPackageManager().getPackageUidAsUser(nextArgRequired, PackageManager.PackageInfoFlags.of(4194304L), i), i, restrictionNameToLevel, 1024, 0);
                    return 0;
                } catch (PackageManager.NameNotFoundException unused) {
                    printWriter.println("Error: userId:" + i + " package:" + nextArgRequired + " is not found");
                    return -1;
                }
            }
        }
    }

    public int runGetBgRestrictionLevel(PrintWriter printWriter) {
        int i = -2;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption != null) {
                if (nextOption.equals("--user")) {
                    i = UserHandle.parseUserArg(getNextArgRequired());
                } else {
                    getErrPrintWriter().println("Error: Unknown option: " + nextOption);
                    return -1;
                }
            } else {
                printWriter.println(ActivityManager.restrictionLevelToName(this.mInternal.getBackgroundRestrictionLevel(getNextArgRequired(), i)));
                return 0;
            }
        }
    }

    public int runSetForegroundServiceDelegate(PrintWriter printWriter) {
        boolean z;
        int i = -2;
        while (true) {
            String nextOption = getNextOption();
            if (nextOption != null) {
                if (nextOption.equals("--user")) {
                    i = UserHandle.parseUserArg(getNextArgRequired());
                } else {
                    getErrPrintWriter().println("Error: Unknown option: " + nextOption);
                    return -1;
                }
            } else {
                String nextArgRequired = getNextArgRequired();
                String nextArgRequired2 = getNextArgRequired();
                if ("start".equals(nextArgRequired2)) {
                    z = true;
                } else {
                    if (!"stop".equals(nextArgRequired2)) {
                        printWriter.println("Error: action is either start or stop");
                        return -1;
                    }
                    z = false;
                }
                try {
                    this.mInternal.setForegroundServiceDelegate(nextArgRequired, this.mInternal.mContext.getPackageManager().getPackageUidAsUser(nextArgRequired, PackageManager.PackageInfoFlags.of(4194304L), i), z, 12, "FgsDelegate");
                    return 0;
                } catch (PackageManager.NameNotFoundException unused) {
                    printWriter.println("Error: userId:" + i + " package:" + nextArgRequired + " is not found");
                    return -1;
                }
            }
        }
    }

    public int runResetDropboxRateLimiter() {
        this.mInternal.resetDropboxRateLimiter();
        return 0;
    }

    public int runListDisplaysForStartingUsers(PrintWriter printWriter) {
        int[] displayIdsForStartingVisibleBackgroundUsers = this.mInterface.getDisplayIdsForStartingVisibleBackgroundUsers();
        printWriter.println((displayIdsForStartingVisibleBackgroundUsers == null || displayIdsForStartingVisibleBackgroundUsers.length == 0) ? "none" : Arrays.toString(displayIdsForStartingVisibleBackgroundUsers));
        return 0;
    }

    public final Resources getResources(PrintWriter printWriter) {
        Configuration configuration = this.mInterface.getConfiguration();
        if (configuration == null) {
            printWriter.println("Error: Activity manager has no configuration");
            return null;
        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
        displayMetrics.setToDefaults();
        return new Resources(AssetManager.getSystem(), displayMetrics, configuration);
    }

    public void onHelp() {
        dumpHelp(getOutPrintWriter(), this.mDumping);
    }

    @NeverCompile
    public static void dumpHelp(PrintWriter printWriter, boolean z) {
        if (z) {
            printWriter.println("Activity manager dump options:");
            printWriter.println("  [-a] [-c] [-p PACKAGE] [-h] [WHAT] ...");
            printWriter.println("  WHAT may be one of:");
            printWriter.println("    a[ctivities]: activity stack state");
            printWriter.println("    r[recents]: recent activities state");
            printWriter.println("    b[roadcasts] [PACKAGE_NAME] [history [-s]]: broadcast state");
            printWriter.println("    broadcast-stats [PACKAGE_NAME]: aggregated broadcast statistics");
            printWriter.println("    i[ntents] [PACKAGE_NAME]: pending intent state");
            printWriter.println("    p[rocesses] [PACKAGE_NAME]: process state");
            printWriter.println("    o[om]: out of memory management");
            printWriter.println("    perm[issions]: URI permission grant state");
            printWriter.println("    prov[iders] [COMP_SPEC ...]: content provider state");
            printWriter.println("    provider [COMP_SPEC]: provider client-side state");
            printWriter.println("    s[ervices] [COMP_SPEC ...]: service state");
            printWriter.println("    allowed-associations: current package association restrictions");
            printWriter.println("    as[sociations]: tracked app associations");
            printWriter.println("    exit-info [PACKAGE_NAME]: historical process exit information");
            printWriter.println("    lmk: stats on low memory killer");
            printWriter.println("    lru: raw LRU process list");
            printWriter.println("    binder-proxies: stats on binder objects and IPCs");
            printWriter.println("    settings: currently applied config settings");
            printWriter.println("    service [COMP_SPEC]: service client-side state");
            printWriter.println("    package [PACKAGE_NAME]: all state related to given package");
            printWriter.println("    all: dump all activities");
            printWriter.println("    top: dump the top activity");
            printWriter.println("    users: user state");
            printWriter.println("  WHAT may also be a COMP_SPEC to dump activities.");
            printWriter.println("  COMP_SPEC may be a component name (com.foo/.myApp),");
            printWriter.println("    a partial substring in a component name, a");
            printWriter.println("    hex object identifier.");
            printWriter.println("  -a: include all available server state.");
            printWriter.println("  -c: include client state.");
            printWriter.println("  -p: limit output to given package.");
            printWriter.println("  -d: limit output to given display.");
            printWriter.println("  --checkin: output checkin format, resetting data.");
            printWriter.println("  --C: output checkin format, not resetting data.");
            printWriter.println("  --proto: output dump in protocol buffer format.");
            printWriter.printf("  %s: dump just the DUMPABLE-related state of an activity. Use the %s option to list the supported DUMPABLEs\n", "--dump-dumpable", "--list-dumpables");
            printWriter.printf("  %s: show the available dumpables in an activity\n", "--list-dumpables");
            return;
        }
        printWriter.println("Activity manager (activity) commands:");
        printWriter.println("  help");
        printWriter.println("      Print this help text.");
        printWriter.println("  start-activity [-D] [-N] [-W] [-P <FILE>] [--start-profiler <FILE>]");
        printWriter.println("          [--sampling INTERVAL] [--clock-type <TYPE>] [--streaming]");
        printWriter.println("          [-R COUNT] [-S] [--track-allocation]");
        printWriter.println("          [--user <USER_ID> | current] [--suspend] <INTENT>");
        printWriter.println("      Start an Activity.  Options are:");
        printWriter.println("      -D: enable debugging");
        printWriter.println("      --suspend: debugged app suspend threads at startup (only with -D)");
        printWriter.println("      -N: enable native debugging");
        printWriter.println("      -W: wait for launch to complete");
        printWriter.println("      --start-profiler <FILE>: start profiler and send results to <FILE>");
        printWriter.println("      --sampling INTERVAL: use sample profiling with INTERVAL microseconds");
        printWriter.println("          between samples (use with --start-profiler)");
        printWriter.println("      --clock-type <TYPE>: type can be wall / thread-cpu / dual. Specify");
        printWriter.println("          the clock that is used to report the timestamps when profiling");
        printWriter.println("          The default value is dual. (use with --start-profiler)");
        printWriter.println("      --streaming: stream the profiling output to the specified file");
        printWriter.println("          (use with --start-profiler)");
        printWriter.println("      -P <FILE>: like above, but profiling stops when app goes idle");
        printWriter.println("      --attach-agent <agent>: attach the given agent before binding");
        printWriter.println("      --attach-agent-bind <agent>: attach the given agent during binding");
        printWriter.println("      -R: repeat the activity launch <COUNT> times.  Prior to each repeat,");
        printWriter.println("          the top activity will be finished.");
        printWriter.println("      -S: force stop the target app before starting the activity");
        printWriter.println("      --track-allocation: enable tracking of object allocations");
        printWriter.println("      --user <USER_ID> | current: Specify which user to run as; if not");
        printWriter.println("          specified then run as the current user.");
        printWriter.println("      --windowingMode <WINDOWING_MODE>: The windowing mode to launch the activity into.");
        printWriter.println("      --activityType <ACTIVITY_TYPE>: The activity type to launch the activity as.");
        printWriter.println("      --display <DISPLAY_ID>: The display to launch the activity into.");
        printWriter.println("      --splashscreen-icon: Show the splash screen icon on launch.");
        printWriter.println("  start-service [--user <USER_ID> | current] <INTENT>");
        printWriter.println("      Start a Service.  Options are:");
        printWriter.println("      --user <USER_ID> | current: Specify which user to run as; if not");
        printWriter.println("          specified then run as the current user.");
        printWriter.println("  start-foreground-service [--user <USER_ID> | current] <INTENT>");
        printWriter.println("      Start a foreground Service.  Options are:");
        printWriter.println("      --user <USER_ID> | current: Specify which user to run as; if not");
        printWriter.println("          specified then run as the current user.");
        printWriter.println("  stop-service [--user <USER_ID> | current] <INTENT>");
        printWriter.println("      Stop a Service.  Options are:");
        printWriter.println("      --user <USER_ID> | current: Specify which user to run as; if not");
        printWriter.println("          specified then run as the current user.");
        printWriter.println("  broadcast [--user <USER_ID> | all | current]");
        printWriter.println("          [--receiver-permission <PERMISSION>]");
        printWriter.println("          [--allow-background-activity-starts]");
        printWriter.println("          [--async] <INTENT>");
        printWriter.println("      Send a broadcast Intent.  Options are:");
        printWriter.println("      --user <USER_ID> | all | current: Specify which user to send to; if not");
        printWriter.println("          specified then send to all users.");
        printWriter.println("      --receiver-permission <PERMISSION>: Require receiver to hold permission.");
        printWriter.println("      --allow-background-activity-starts: The receiver may start activities");
        printWriter.println("          even if in the background.");
        printWriter.println("      --async: Send without waiting for the completion of the receiver.");
        printWriter.println("  compact [some|full] <process_name> [--user <USER_ID>]");
        printWriter.println("      Perform a single process compaction.");
        printWriter.println("      some: execute file compaction.");
        printWriter.println("      full: execute anon + file compaction.");
        printWriter.println("      system: system compaction.");
        printWriter.println("  compact system");
        printWriter.println("      Perform a full system compaction.");
        printWriter.println("  compact native [some|full] <pid>");
        printWriter.println("      Perform a native compaction for process with <pid>.");
        printWriter.println("      some: execute file compaction.");
        printWriter.println("      full: execute anon + file compaction.");
        printWriter.println("  freeze [--sticky] <processname> [--user <USER_ID>]");
        printWriter.println("      Freeze a process.");
        printWriter.println("        --sticky: persists the frozen state for the process lifetime or");
        printWriter.println("                  until an unfreeze is triggered via shell");
        printWriter.println("  unfreeze [--sticky] <processname> [--user <USER_ID>]");
        printWriter.println("      Unfreeze a process.");
        printWriter.println("        --sticky: persists the unfrozen state for the process lifetime or");
        printWriter.println("                  until a freeze is triggered via shell");
        printWriter.println("  instrument [-r] [-e <NAME> <VALUE>] [-p <FILE>] [-w]");
        printWriter.println("          [--user <USER_ID> | current]");
        printWriter.println("          [--no-hidden-api-checks [--no-test-api-access]]");
        printWriter.println("          [--no-isolated-storage]");
        printWriter.println("          [--no-window-animation] [--abi <ABI>] <COMPONENT>");
        printWriter.println("      Start an Instrumentation.  Typically this target <COMPONENT> is in the");
        printWriter.println("      form <TEST_PACKAGE>/<RUNNER_CLASS> or only <TEST_PACKAGE> if there");
        printWriter.println("      is only one instrumentation.  Options are:");
        printWriter.println("      -r: print raw results (otherwise decode REPORT_KEY_STREAMRESULT).  Use with");
        printWriter.println("          [-e perf true] to generate raw output for performance measurements.");
        printWriter.println("      -e <NAME> <VALUE>: set argument <NAME> to <VALUE>.  For test runners a");
        printWriter.println("          common form is [-e <testrunner_flag> <value>[,<value>...]].");
        printWriter.println("      -p <FILE>: write profiling data to <FILE>");
        printWriter.println("      -m: Write output as protobuf to stdout (machine readable)");
        printWriter.println("      -f <Optional PATH/TO/FILE>: Write output as protobuf to a file (machine");
        printWriter.println("          readable). If path is not specified, default directory and file name will");
        printWriter.println("          be used: /sdcard/instrument-logs/log-yyyyMMdd-hhmmss-SSS.instrumentation_data_proto");
        printWriter.println("      -w: wait for instrumentation to finish before returning.  Required for");
        printWriter.println("          test runners.");
        printWriter.println("      --user <USER_ID> | current: Specify user instrumentation runs in;");
        printWriter.println("          current user if not specified.");
        printWriter.println("      --no-hidden-api-checks: disable restrictions on use of hidden API.");
        printWriter.println("      --no-test-api-access: do not allow access to test APIs, if hidden");
        printWriter.println("          API checks are enabled.");
        printWriter.println("      --no-isolated-storage: don't use isolated storage sandbox and ");
        printWriter.println("          mount full external storage");
        printWriter.println("      --no-window-animation: turn off window animations while running.");
        printWriter.println("      --abi <ABI>: Launch the instrumented process with the selected ABI.");
        printWriter.println("          This assumes that the process supports the selected ABI.");
        printWriter.println("  trace-ipc [start|stop] [--dump-file <FILE>]");
        printWriter.println("      Trace IPC transactions.");
        printWriter.println("      start: start tracing IPC transactions.");
        printWriter.println("      stop: stop tracing IPC transactions and dump the results to file.");
        printWriter.println("      --dump-file <FILE>: Specify the file the trace should be dumped to.");
        printWriter.println("  profile start [--user <USER_ID> current]");
        printWriter.println("          [--clock-type <TYPE>]");
        printWriter.println("          [--sampling INTERVAL | --streaming] <PROCESS> <FILE>");
        printWriter.println("      Start profiler on a process.  The given <PROCESS> argument");
        printWriter.println("        may be either a process name or pid.  Options are:");
        printWriter.println("      --user <USER_ID> | current: When supplying a process name,");
        printWriter.println("          specify user of process to profile; uses current user if not");
        printWriter.println("          specified.");
        printWriter.println("      --clock-type <TYPE>: use the specified clock to report timestamps.");
        printWriter.println("          The type can be one of wall | thread-cpu | dual. The default");
        printWriter.println("          value is dual.");
        printWriter.println("      --sampling INTERVAL: use sample profiling with INTERVAL microseconds");
        printWriter.println("          between samples.");
        printWriter.println("      --streaming: stream the profiling output to the specified file.");
        printWriter.println("  profile stop [--user <USER_ID> current] <PROCESS>");
        printWriter.println("      Stop profiler on a process.  The given <PROCESS> argument");
        printWriter.println("        may be either a process name or pid.  Options are:");
        printWriter.println("      --user <USER_ID> | current: When supplying a process name,");
        printWriter.println("          specify user of process to profile; uses current user if not");
        printWriter.println("          specified.");
        printWriter.println("  dumpheap [--user <USER_ID> current] [-n] [-g] <PROCESS> <FILE>");
        printWriter.println("      Dump the heap of a process.  The given <PROCESS> argument may");
        printWriter.println("        be either a process name or pid.  Options are:");
        printWriter.println("      -n: dump native heap instead of managed heap");
        printWriter.println("      -g: force GC before dumping the heap");
        printWriter.println("      --user <USER_ID> | current: When supplying a process name,");
        printWriter.println("          specify user of process to dump; uses current user if not specified.");
        printWriter.println("  set-debug-app [-w] [--persistent] <PACKAGE>");
        printWriter.println("      Set application <PACKAGE> to debug.  Options are:");
        printWriter.println("      -w: wait for debugger when application starts");
        printWriter.println("      --persistent: retain this value");
        printWriter.println("  clear-debug-app");
        printWriter.println("      Clear the previously set-debug-app.");
        printWriter.println("  set-watch-heap <PROCESS> <MEM-LIMIT>");
        printWriter.println("      Start monitoring pss size of <PROCESS>, if it is at or");
        printWriter.println("      above <HEAP-LIMIT> then a heap dump is collected for the user to report.");
        printWriter.println("  clear-watch-heap");
        printWriter.println("      Clear the previously set-watch-heap.");
        printWriter.println("  clear-exit-info [--user <USER_ID> | all | current] [package]");
        printWriter.println("      Clear the process exit-info for given package");
        printWriter.println("  bug-report [--progress | --telephony]");
        printWriter.println("      Request bug report generation; will launch a notification");
        printWriter.println("        when done to select where it should be delivered. Options are:");
        printWriter.println("     --progress: will launch a notification right away to show its progress.");
        printWriter.println("     --telephony: will dump only telephony sections.");
        printWriter.println("  fgs-notification-rate-limit {enable | disable}");
        printWriter.println("     Enable/disable rate limit on FGS notification deferral policy.");
        printWriter.println("  force-stop [--user <USER_ID> | all | current] <PACKAGE>");
        printWriter.println("      Completely stop the given application package.");
        printWriter.println("  stop-app [--user <USER_ID> | all | current] <PACKAGE>");
        printWriter.println("      Stop an app and all of its services.  Unlike `force-stop` this does");
        printWriter.println("      not cancel the app's scheduled alarms and jobs.");
        printWriter.println("  crash [--user <USER_ID>] <PACKAGE|PID>");
        printWriter.println("      Induce a VM crash in the specified package or process");
        printWriter.println("  kill [--user <USER_ID> | all | current] <PACKAGE>");
        printWriter.println("      Kill all background processes associated with the given application.");
        printWriter.println("  kill-all");
        printWriter.println("      Kill all processes that are safe to kill (cached, etc).");
        printWriter.println("  make-uid-idle [--user <USER_ID> | all | current] <PACKAGE>");
        printWriter.println("      If the given application's uid is in the background and waiting to");
        printWriter.println("      become idle (not allowing background services), do that now.");
        printWriter.println("  set-deterministic-uid-idle [--user <USER_ID> | all | current] <true|false>");
        printWriter.println("      If true, sets the timing of making UIDs idle consistent and");
        printWriter.println("      deterministic. If false, the timing will be variable depending on");
        printWriter.println("      other activity on the device. The default is false.");
        printWriter.println("  monitor [--gdb <port>] [-p <TARGET>] [-s] [-c] [-k]");
        printWriter.println("      Start monitoring for crashes or ANRs.");
        printWriter.println("      --gdb: start gdbserv on the given port at crash/ANR");
        printWriter.println("      -p: only show events related to a specific process / package");
        printWriter.println("      -s: simple mode, only show a summary line for each event");
        printWriter.println("      -c: assume the input is always [c]ontinue");
        printWriter.println("      -k: assume the input is always [k]ill");
        printWriter.println("         -c and -k are mutually exclusive.");
        printWriter.println("  watch-uids [--oom <uid>] [--mask <capabilities integer>]");
        printWriter.println("      Start watching for and reporting uid state changes.");
        printWriter.println("      --oom: specify a uid for which to report detailed change messages.");
        printWriter.println("      --mask: Specify PROCESS_CAPABILITY_XXX mask to report. ");
        printWriter.println("              By default, it only reports FOREGROUND_LOCATION (1)");
        printWriter.println("              FOREGROUND_CAMERA (2), FOREGROUND_MICROPHONE (4)");
        printWriter.println("              and NETWORK (8). New capabilities added on or after");
        printWriter.println("              Android UDC will not be reported by default.");
        printWriter.println("  hang [--allow-restart]");
        printWriter.println("      Hang the system.");
        printWriter.println("      --allow-restart: allow watchdog to perform normal system restart");
        printWriter.println("  restart");
        printWriter.println("      Restart the user-space system.");
        printWriter.println("  idle-maintenance");
        printWriter.println("      Perform idle maintenance now.");
        printWriter.println("  screen-compat [on|off] <PACKAGE>");
        printWriter.println("      Control screen compatibility mode of <PACKAGE>.");
        printWriter.println("  package-importance <PACKAGE>");
        printWriter.println("      Print current importance of <PACKAGE>.");
        printWriter.println("  to-uri [INTENT]");
        printWriter.println("      Print the given Intent specification as a URI.");
        printWriter.println("  to-intent-uri [INTENT]");
        printWriter.println("      Print the given Intent specification as an intent: URI.");
        printWriter.println("  to-app-uri [INTENT]");
        printWriter.println("      Print the given Intent specification as an android-app: URI.");
        printWriter.println("  switch-user <USER_ID>");
        printWriter.println("      Switch to put USER_ID in the foreground, starting");
        printWriter.println("      execution of that user if it is currently stopped.");
        printWriter.println("  get-current-user");
        printWriter.println("      Returns id of the current foreground user.");
        printWriter.println("  start-user [-w] [--display DISPLAY_ID] <USER_ID>");
        printWriter.println("      Start USER_ID in background if it is currently stopped;");
        printWriter.println("      use switch-user if you want to start the user in foreground.");
        printWriter.println("      -w: wait for start-user to complete and the user to be unlocked.");
        printWriter.println("      --display <DISPLAY_ID>: starts the user visible in that display, which allows the user to launch activities on it.");
        printWriter.println("        (not supported on all devices; typically only on automotive builds where the vehicle has passenger displays)");
        printWriter.println("  unlock-user <USER_ID>");
        printWriter.println("      Unlock the given user.  This will only work if the user doesn't");
        printWriter.println("      have an LSKF (PIN/pattern/password).");
        printWriter.println("  stop-user [-w] [-f] <USER_ID>");
        printWriter.println("      Stop execution of USER_ID, not allowing it to run any");
        printWriter.println("      code until a later explicit start or switch to it.");
        printWriter.println("      -w: wait for stop-user to complete.");
        printWriter.println("      -f: force stop even if there are related users that cannot be stopped.");
        printWriter.println("  is-user-stopped <USER_ID>");
        printWriter.println("      Returns whether <USER_ID> has been stopped or not.");
        printWriter.println("  get-started-user-state <USER_ID>");
        printWriter.println("      Gets the current state of the given started user.");
        printWriter.println("  track-associations");
        printWriter.println("      Enable association tracking.");
        printWriter.println("  untrack-associations");
        printWriter.println("      Disable and clear association tracking.");
        printWriter.println("  get-uid-state <UID>");
        printWriter.println("      Gets the process state of an app given its <UID>.");
        printWriter.println("  attach-agent <PROCESS> <FILE>");
        printWriter.println("    Attach an agent to the specified <PROCESS>, which may be either a process name or a PID.");
        printWriter.println("  get-config [--days N] [--device] [--proto] [--display <DISPLAY_ID>]");
        printWriter.println("      Retrieve the configuration and any recent configurations of the device.");
        printWriter.println("      --days: also return last N days of configurations that have been seen.");
        printWriter.println("      --device: also output global device configuration info.");
        printWriter.println("      --proto: return result as a proto; does not include --days info.");
        printWriter.println("      --display: Specify for which display to run the command; if not ");
        printWriter.println("          specified then run for the default display.");
        printWriter.println("  supports-multiwindow");
        printWriter.println("      Returns true if the device supports multiwindow.");
        printWriter.println("  supports-split-screen-multi-window");
        printWriter.println("      Returns true if the device supports split screen multiwindow.");
        printWriter.println("  suppress-resize-config-changes <true|false>");
        printWriter.println("      Suppresses configuration changes due to user resizing an activity/task.");
        printWriter.println("  set-inactive [--user <USER_ID>] <PACKAGE> true|false");
        printWriter.println("      Sets the inactive state of an app.");
        printWriter.println("  get-inactive [--user <USER_ID>] <PACKAGE>");
        printWriter.println("      Returns the inactive state of an app.");
        printWriter.println("  set-standby-bucket [--user <USER_ID>] <PACKAGE> active|working_set|frequent|rare|restricted");
        printWriter.println("      Puts an app in the standby bucket.");
        printWriter.println("  get-standby-bucket [--user <USER_ID>] <PACKAGE>");
        printWriter.println("      Returns the standby bucket of an app.");
        printWriter.println("  send-trim-memory [--user <USER_ID>] <PROCESS>");
        printWriter.println("          [HIDDEN|RUNNING_MODERATE|BACKGROUND|RUNNING_LOW|MODERATE|RUNNING_CRITICAL|COMPLETE]");
        printWriter.println("      Send a memory trim event to a <PROCESS>.  May also supply a raw trim int level.");
        printWriter.println("  display [COMMAND] [...]: sub-commands for operating on displays.");
        printWriter.println("       move-stack <STACK_ID> <DISPLAY_ID>");
        printWriter.println("           Move <STACK_ID> from its current display to <DISPLAY_ID>.");
        printWriter.println("  stack [COMMAND] [...]: sub-commands for operating on activity stacks.");
        printWriter.println("       move-task <TASK_ID> <STACK_ID> [true|false]");
        printWriter.println("           Move <TASK_ID> from its current stack to the top (true) or");
        printWriter.println("           bottom (false) of <STACK_ID>.");
        printWriter.println("       list");
        printWriter.println("           List all of the activity stacks and their sizes.");
        printWriter.println("       info <WINDOWING_MODE> <ACTIVITY_TYPE>");
        printWriter.println("           Display the information about activity stack in <WINDOWING_MODE> and <ACTIVITY_TYPE>.");
        printWriter.println("       remove <STACK_ID>");
        printWriter.println("           Remove stack <STACK_ID>.");
        printWriter.println("  task [COMMAND] [...]: sub-commands for operating on activity tasks.");
        printWriter.println("       lock <TASK_ID>");
        printWriter.println("           Bring <TASK_ID> to the front and don't allow other tasks to run.");
        printWriter.println("       lock stop");
        printWriter.println("           End the current task lock.");
        printWriter.println("       resizeable <TASK_ID> [0|1|2|3]");
        printWriter.println("           Change resizeable mode of <TASK_ID> to one of the following:");
        printWriter.println("           0: unresizeable");
        printWriter.println("           1: crop_windows");
        printWriter.println("           2: resizeable");
        printWriter.println("           3: resizeable_and_pipable");
        printWriter.println("       resize <TASK_ID> <LEFT,TOP,RIGHT,BOTTOM>");
        printWriter.println("           Makes sure <TASK_ID> is in a stack with the specified bounds.");
        printWriter.println("           Forces the task to be resizeable and creates a stack if no existing stack");
        printWriter.println("           has the specified bounds.");
        printWriter.println("  update-appinfo <USER_ID> <PACKAGE_NAME> [<PACKAGE_NAME>...]");
        printWriter.println("      Update the ApplicationInfo objects of the listed packages for <USER_ID>");
        printWriter.println("      without restarting any processes.");
        printWriter.println("  write");
        printWriter.println("      Write all pending state to storage.");
        printWriter.println("  compat [COMMAND] [...]: sub-commands for toggling app-compat changes.");
        printWriter.println("         enable|disable [--no-kill] <CHANGE_ID|CHANGE_NAME> <PACKAGE_NAME>");
        printWriter.println("            Toggles a change either by id or by name for <PACKAGE_NAME>.");
        printWriter.println("            It kills <PACKAGE_NAME> (to allow the toggle to take effect) unless --no-kill is provided.");
        printWriter.println("         reset <CHANGE_ID|CHANGE_NAME> <PACKAGE_NAME>");
        printWriter.println("            Toggles a change either by id or by name for <PACKAGE_NAME>.");
        printWriter.println("            It kills <PACKAGE_NAME> (to allow the toggle to take effect).");
        printWriter.println("         enable-all|disable-all <targetSdkVersion> <PACKAGE_NAME>");
        printWriter.println("            Toggles all changes that are gated by <targetSdkVersion>.");
        printWriter.println("         reset-all [--no-kill] <PACKAGE_NAME>");
        printWriter.println("            Removes all existing overrides for all changes for ");
        printWriter.println("            <PACKAGE_NAME> (back to default behaviour).");
        printWriter.println("            It kills <PACKAGE_NAME> (to allow the toggle to take effect) unless --no-kill is provided.");
        printWriter.println("  memory-factor [command] [...]: sub-commands for overriding memory pressure factor");
        printWriter.println("         set <NORMAL|MODERATE|LOW|CRITICAL>");
        printWriter.println("            Overrides memory pressure factor. May also supply a raw int level");
        printWriter.println("         show");
        printWriter.println("            Shows the existing memory pressure factor");
        printWriter.println("         reset");
        printWriter.println("            Removes existing override for memory pressure factor");
        printWriter.println("  service-restart-backoff <COMMAND> [...]: sub-commands to toggle service restart backoff policy.");
        printWriter.println("         enable|disable <PACKAGE_NAME>");
        printWriter.println("            Toggles the restart backoff policy on/off for <PACKAGE_NAME>.");
        printWriter.println("         show <PACKAGE_NAME>");
        printWriter.println("            Shows the restart backoff policy state for <PACKAGE_NAME>.");
        printWriter.println("  get-isolated-pids <UID>");
        printWriter.println("         Get the PIDs of isolated processes with packages in this <UID>");
        printWriter.println("  set-stop-user-on-switch [true|false]");
        printWriter.println("         Sets whether the current user (and its profiles) should be stopped when switching to a different user.");
        printWriter.println("         Without arguments, it resets to the value defined by platform.");
        printWriter.println("  set-bg-abusive-uids [uid=percentage][,uid=percentage...]");
        printWriter.println("         Force setting the battery usage of the given UID.");
        printWriter.println("  set-bg-restriction-level [--user <USER_ID>] <PACKAGE> unrestricted|exempted|adaptive_bucket|restricted_bucket|background_restricted|hibernation");
        printWriter.println("         Set an app's background restriction level which in turn map to a app standby bucket.");
        printWriter.println("  get-bg-restriction-level [--user <USER_ID>] <PACKAGE>");
        printWriter.println("         Get an app's background restriction level.");
        printWriter.println("  list-displays-for-starting-users");
        printWriter.println("         Lists the id of displays that can be used to start users on background.");
        printWriter.println("  set-foreground-service-delegate [--user <USER_ID>] <PACKAGE> start|stop");
        printWriter.println("         Start/stop an app's foreground service delegate.");
        printWriter.println("  set-ignore-delivery-group-policy <ACTION>");
        printWriter.println("         Start ignoring delivery group policy set for a broadcast action");
        printWriter.println("  clear-ignore-delivery-group-policy <ACTION>");
        printWriter.println("         Stop ignoring delivery group policy set for a broadcast action");
        printWriter.println("  capabilities [--protobuf]");
        printWriter.println("         Output am supported features (text format). Options are:");
        printWriter.println("         --protobuf: format output using protobuffer");
        Intent.printIntentArgsHelp(printWriter, "");
    }
}
