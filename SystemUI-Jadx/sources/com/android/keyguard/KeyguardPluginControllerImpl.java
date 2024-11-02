package com.android.keyguard;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.android.internal.util.LatencyTracker;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.subscreen.SubScreenManager;
import com.android.systemui.util.DesktopManager;
import com.android.systemui.util.DesktopManagerImpl;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class KeyguardPluginControllerImpl {
    public final Context mContext;
    public final DesktopManager mDesktopManager;
    public final KeyguardSecurityCallback mKeyguardCallback;
    public final KeyguardTextBuilder mKeyguardTextBuilder;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final KnoxStateMonitor mKnoxStateMonitor;
    public final LatencyTracker mLatencyTracker;
    public final LockPatternUtils mLockPatternUtils;
    public AsyncTask mPendingLockCheck;
    public int mPromptReason;
    public String mStrongAuthPopupMessage;
    public final SubScreenManager mSubScreenManager;
    public final ViewMediatorCallback mViewMediatorCallback;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* renamed from: com.android.keyguard.KeyguardPluginControllerImpl$2, reason: invalid class name */
    /* loaded from: classes.dex */
    public abstract /* synthetic */ class AnonymousClass2 {
        public static final /* synthetic */ int[] $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode;

        static {
            int[] iArr = new int[KeyguardSecurityModel.SecurityMode.values().length];
            $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode = iArr;
            try {
                iArr[KeyguardSecurityModel.SecurityMode.PIN.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode[KeyguardSecurityModel.SecurityMode.Pattern.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode[KeyguardSecurityModel.SecurityMode.Password.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class Factory {
        public final Context mContext;
        public final DesktopManager mDesktopManager;
        public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
        public final LatencyTracker mLatencyTracker;
        public final LockPatternUtils mLockPatternUtils;
        public final SubScreenManager mSubScreenManager;
        public final ViewMediatorCallback mViewMediatorCallback;

        public Factory(Context context, ViewMediatorCallback viewMediatorCallback, DesktopManager desktopManager, SubScreenManager subScreenManager, LatencyTracker latencyTracker, LockPatternUtils lockPatternUtils, KeyguardUpdateMonitor keyguardUpdateMonitor) {
            this.mContext = context;
            this.mViewMediatorCallback = viewMediatorCallback;
            this.mDesktopManager = desktopManager;
            this.mSubScreenManager = subScreenManager;
            this.mLatencyTracker = latencyTracker;
            this.mLockPatternUtils = lockPatternUtils;
            this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:47:0x00af, code lost:
    
        if (r12 != 4) goto L40;
     */
    /* renamed from: -$$Nest$monPasswordChecked, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void m55$$Nest$monPasswordChecked(com.android.keyguard.KeyguardPluginControllerImpl r11, int r12, boolean r13, int r14) {
        /*
            Method dump skipped, instructions count: 306
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.keyguard.KeyguardPluginControllerImpl.m55$$Nest$monPasswordChecked(com.android.keyguard.KeyguardPluginControllerImpl, int, boolean, int):void");
    }

    public /* synthetic */ KeyguardPluginControllerImpl(Context context, ViewMediatorCallback viewMediatorCallback, DesktopManager desktopManager, SubScreenManager subScreenManager, KeyguardSecurityCallback keyguardSecurityCallback, LatencyTracker latencyTracker, LockPatternUtils lockPatternUtils, KeyguardUpdateMonitor keyguardUpdateMonitor, int i) {
        this(context, viewMediatorCallback, desktopManager, subScreenManager, keyguardSecurityCallback, latencyTracker, lockPatternUtils, keyguardUpdateMonitor);
    }

    public final void showWipeWarningDialog(String str) {
        boolean z;
        DesktopManager desktopManager = this.mDesktopManager;
        if (((DesktopManagerImpl) desktopManager).isDesktopMode()) {
            ((DesktopManagerImpl) desktopManager).notifyUpdateBouncerMessage(2, "", str);
        }
        if (LsRune.SUPPORT_LARGE_FRONT_SUB_DISPLAY && !((DisplayLifecycle) Dependency.get(DisplayLifecycle.class)).mIsFolderOpened) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            SubScreenManager subScreenManager = this.mSubScreenManager;
            if (subScreenManager.mSubScreenPlugin == null) {
                Log.w("SubScreenManager", "showWipeWarningDialog() no plugin");
            } else {
                KeyguardPluginControllerImpl$$ExternalSyntheticOutline0.m("showWipeWarningDialog() ", str, "SubScreenManager");
                subScreenManager.mSubScreenPlugin.showWipeWarningDialog(str);
            }
        }
    }

    private KeyguardPluginControllerImpl(Context context, ViewMediatorCallback viewMediatorCallback, DesktopManager desktopManager, SubScreenManager subScreenManager, KeyguardSecurityCallback keyguardSecurityCallback, LatencyTracker latencyTracker, LockPatternUtils lockPatternUtils, KeyguardUpdateMonitor keyguardUpdateMonitor) {
        this.mContext = context;
        this.mViewMediatorCallback = viewMediatorCallback;
        this.mDesktopManager = desktopManager;
        this.mSubScreenManager = subScreenManager;
        this.mKeyguardCallback = keyguardSecurityCallback;
        this.mLatencyTracker = latencyTracker;
        this.mLockPatternUtils = lockPatternUtils;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mKeyguardTextBuilder = KeyguardTextBuilder.getInstance(context);
        this.mKnoxStateMonitor = (KnoxStateMonitor) Dependency.get(KnoxStateMonitor.class);
    }
}
