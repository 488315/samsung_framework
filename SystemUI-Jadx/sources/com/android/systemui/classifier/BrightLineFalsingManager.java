package com.android.systemui.classifier;

import android.net.Uri;
import android.util.IndentingPrintWriter;
import android.util.Log;
import android.view.accessibility.AccessibilityManager;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.classifier.FalsingClassifier;
import com.android.systemui.classifier.FalsingDataProvider;
import com.android.systemui.classifier.HistoryTracker;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.FeatureFlagsRelease;
import com.android.systemui.flags.Flags;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.statusbar.policy.BatteryControllerImpl;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.sec.ims.configuration.DATA;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.StringJoiner;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class BrightLineFalsingManager implements FalsingManager {
    public static final boolean DEBUG = Log.isLoggable("FalsingManager", 3);
    public static final Queue RECENT_INFO_LOG = new ArrayDeque(41);
    public static final Queue RECENT_SWIPES = new ArrayDeque(21);
    public final AccessibilityManager mAccessibilityManager;
    public final AnonymousClass2 mBeliefListener;
    public final Collection mClassifiers;
    public final FalsingDataProvider mDataProvider;
    public boolean mDestroyed;
    public final DoubleTapClassifier mDoubleTapClassifier;
    public final List mFalsingBeliefListeners = new ArrayList();
    public final List mFalsingTapListeners = new ArrayList();
    public final FeatureFlags mFeatureFlags;
    public final AnonymousClass3 mGestureFinalizedListener;
    public final HistoryTracker mHistoryTracker;
    public final KeyguardStateController mKeyguardStateController;
    public FalsingManager.ProximityEvent mLastProximityEvent;
    public final LongTapClassifier mLongTapClassifier;
    public final MetricsLogger mMetricsLogger;
    public int mPriorInteractionType;
    public Collection mPriorResults;
    public final AnonymousClass1 mSessionListener;
    public final SingleTapClassifier mSingleTapClassifier;
    public final boolean mTestHarness;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* renamed from: com.android.systemui.classifier.BrightLineFalsingManager$1, reason: invalid class name */
    /* loaded from: classes.dex */
    public final class AnonymousClass1 implements FalsingDataProvider.SessionListener {
        public AnonymousClass1() {
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* renamed from: com.android.systemui.classifier.BrightLineFalsingManager$2, reason: invalid class name */
    /* loaded from: classes.dex */
    public final class AnonymousClass2 implements HistoryTracker.BeliefListener {
        public AnonymousClass2() {
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* renamed from: com.android.systemui.classifier.BrightLineFalsingManager$3, reason: invalid class name */
    /* loaded from: classes.dex */
    public final class AnonymousClass3 {
        public AnonymousClass3() {
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class DebugSwipeRecord {
        public final int mInteractionType;
        public final boolean mIsFalse;
        public final List mRecentMotionEvents;

        public DebugSwipeRecord(boolean z, int i, List<XYDt> list) {
            this.mIsFalse = z;
            this.mInteractionType = i;
            this.mRecentMotionEvents = list;
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class XYDt {
        public final int mDT;
        public final int mX;
        public final int mY;

        public XYDt(int i, int i2, int i3) {
            this.mX = i;
            this.mY = i2;
            this.mDT = i3;
        }

        public final String toString() {
            return this.mX + "," + this.mY + "," + this.mDT;
        }
    }

    public BrightLineFalsingManager(FalsingDataProvider falsingDataProvider, MetricsLogger metricsLogger, Set<FalsingClassifier> set, SingleTapClassifier singleTapClassifier, LongTapClassifier longTapClassifier, DoubleTapClassifier doubleTapClassifier, HistoryTracker historyTracker, KeyguardStateController keyguardStateController, AccessibilityManager accessibilityManager, boolean z, FeatureFlags featureFlags) {
        AnonymousClass1 anonymousClass1 = new AnonymousClass1();
        this.mSessionListener = anonymousClass1;
        AnonymousClass2 anonymousClass2 = new AnonymousClass2();
        this.mBeliefListener = anonymousClass2;
        AnonymousClass3 anonymousClass3 = new AnonymousClass3();
        this.mGestureFinalizedListener = anonymousClass3;
        this.mPriorInteractionType = 7;
        this.mDataProvider = falsingDataProvider;
        this.mMetricsLogger = metricsLogger;
        this.mClassifiers = set;
        this.mSingleTapClassifier = singleTapClassifier;
        this.mLongTapClassifier = longTapClassifier;
        this.mDoubleTapClassifier = doubleTapClassifier;
        this.mHistoryTracker = historyTracker;
        this.mKeyguardStateController = keyguardStateController;
        this.mAccessibilityManager = accessibilityManager;
        this.mTestHarness = z;
        this.mFeatureFlags = featureFlags;
        ((ArrayList) falsingDataProvider.mSessionListeners).add(anonymousClass1);
        ((ArrayList) falsingDataProvider.mGestureFinalizedListeners).add(anonymousClass3);
        ((ArrayList) historyTracker.mBeliefListeners).add(anonymousClass2);
    }

    public static Collection getPassedResult(double d) {
        return Collections.singleton(FalsingClassifier.Result.passed(d));
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public final void addFalsingBeliefListener(FalsingManager.FalsingBeliefListener falsingBeliefListener) {
        ((ArrayList) this.mFalsingBeliefListeners).add(falsingBeliefListener);
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public final void addTapListener(FalsingManager.FalsingTapListener falsingTapListener) {
        ((ArrayList) this.mFalsingTapListeners).add(falsingTapListener);
    }

    public final void checkDestroyed() {
        if (this.mDestroyed) {
            Log.wtf("FalsingManager", "Tried to use FalsingManager after being destroyed!");
        }
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public final void cleanupInternal() {
        this.mDestroyed = true;
        FalsingDataProvider falsingDataProvider = this.mDataProvider;
        ((ArrayList) falsingDataProvider.mSessionListeners).remove(this.mSessionListener);
        ((ArrayList) falsingDataProvider.mGestureFinalizedListeners).remove(this.mGestureFinalizedListener);
        this.mClassifiers.forEach(new BrightLineFalsingManager$$ExternalSyntheticLambda0(1));
        ((ArrayList) this.mFalsingBeliefListeners).clear();
        ((ArrayList) this.mHistoryTracker.mBeliefListeners).remove(this.mBeliefListener);
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public final void dump(PrintWriter printWriter, String[] strArr) {
        int i;
        String str;
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "  ");
        indentingPrintWriter.println("BRIGHTLINE FALSING MANAGER");
        indentingPrintWriter.print("classifierEnabled=");
        indentingPrintWriter.println(1);
        indentingPrintWriter.print("mJustUnlockedWithFace=");
        FalsingDataProvider falsingDataProvider = this.mDataProvider;
        indentingPrintWriter.println(falsingDataProvider.mJustUnlockedWithFace ? 1 : 0);
        indentingPrintWriter.print("isDocked=");
        if (!((BatteryControllerImpl) falsingDataProvider.mBatteryController).mWirelessCharging) {
            falsingDataProvider.mDockManager.getClass();
            i = 0;
        } else {
            i = 1;
        }
        indentingPrintWriter.println(i);
        indentingPrintWriter.print("width=");
        indentingPrintWriter.println(falsingDataProvider.mWidthPixels);
        indentingPrintWriter.print("height=");
        indentingPrintWriter.println(falsingDataProvider.mHeightPixels);
        indentingPrintWriter.println();
        ArrayDeque arrayDeque = (ArrayDeque) RECENT_SWIPES;
        if (arrayDeque.size() != 0) {
            indentingPrintWriter.println("Recent swipes:");
            indentingPrintWriter.increaseIndent();
            Iterator it = arrayDeque.iterator();
            while (it.hasNext()) {
                DebugSwipeRecord debugSwipeRecord = (DebugSwipeRecord) it.next();
                debugSwipeRecord.getClass();
                StringJoiner stringJoiner = new StringJoiner(",");
                StringJoiner add = stringJoiner.add(Integer.toString(1));
                if (debugSwipeRecord.mIsFalse) {
                    str = "1";
                } else {
                    str = DATA.DM_FIELD_INDEX.PCSCF_DOMAIN;
                }
                add.add(str).add(Integer.toString(debugSwipeRecord.mInteractionType));
                Iterator it2 = debugSwipeRecord.mRecentMotionEvents.iterator();
                while (it2.hasNext()) {
                    stringJoiner.add(((XYDt) it2.next()).toString());
                }
                indentingPrintWriter.println(stringJoiner.toString());
                indentingPrintWriter.println();
            }
            indentingPrintWriter.decreaseIndent();
        } else {
            indentingPrintWriter.println("No recent swipes");
        }
        indentingPrintWriter.println();
        indentingPrintWriter.println("Recent falsing info:");
        indentingPrintWriter.increaseIndent();
        Iterator it3 = ((ArrayDeque) RECENT_INFO_LOG).iterator();
        while (it3.hasNext()) {
            indentingPrintWriter.println((String) it3.next());
        }
        indentingPrintWriter.println();
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public final boolean isClassifierEnabled() {
        return true;
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public final boolean isFalseDoubleTap() {
        checkDestroyed();
        if (skipFalsing(7)) {
            this.mPriorResults = getPassedResult(1.0d);
            return false;
        }
        HistoryTracker historyTracker = this.mHistoryTracker;
        historyTracker.falseBelief();
        historyTracker.falseConfidence();
        FalsingClassifier.Result calculateFalsingResult = this.mDoubleTapClassifier.calculateFalsingResult(7);
        this.mPriorResults = Collections.singleton(calculateFalsingResult);
        calculateFalsingResult.getReason();
        return calculateFalsingResult.mFalsed;
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public final boolean isFalseLongTap(int i) {
        List recentMotionEvents;
        checkDestroyed();
        if (skipFalsing(7)) {
            this.mPriorResults = getPassedResult(1.0d);
            return false;
        }
        double d = 0.0d;
        if (i != 0) {
            if (i != 1) {
                if (i != 2) {
                    if (i == 3) {
                        d = 0.6d;
                    }
                } else {
                    d = 0.3d;
                }
            } else {
                d = 0.1d;
            }
        }
        FalsingDataProvider falsingDataProvider = this.mDataProvider;
        if (falsingDataProvider.getRecentMotionEvents().isEmpty()) {
            recentMotionEvents = falsingDataProvider.mPriorMotionEvents;
        } else {
            recentMotionEvents = falsingDataProvider.getRecentMotionEvents();
        }
        FalsingClassifier.Result isTap = this.mLongTapClassifier.isTap(recentMotionEvents, d);
        this.mPriorResults = Collections.singleton(isTap);
        boolean z = isTap.mFalsed;
        if (!z) {
            if (falsingDataProvider.mJustUnlockedWithFace) {
                this.mPriorResults = getPassedResult(1.0d);
            } else {
                this.mPriorResults = getPassedResult(0.1d);
            }
            return false;
        }
        return z;
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0041  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0058  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x009b A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0044  */
    @Override // com.android.systemui.plugins.FalsingManager
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean isFalseTap(int r13) {
        /*
            r12 = this;
            r12.checkDestroyed()
            r0 = 7
            boolean r0 = r12.skipFalsing(r0)
            r1 = 4607182418800017408(0x3ff0000000000000, double:1.0)
            r3 = 0
            if (r0 == 0) goto L14
            java.util.Collection r13 = getPassedResult(r1)
            r12.mPriorResults = r13
            return r3
        L14:
            r4 = 4591870180066957722(0x3fb999999999999a, double:0.1)
            r0 = 1
            r6 = 0
            if (r13 == 0) goto L26
            if (r13 == r0) goto L34
            r8 = 2
            if (r13 == r8) goto L2e
            r8 = 3
            if (r13 == r8) goto L28
        L26:
            r8 = r6
            goto L35
        L28:
            r8 = 4603579539098121011(0x3fe3333333333333, double:0.6)
            goto L35
        L2e:
            r8 = 4599075939470750515(0x3fd3333333333333, double:0.3)
            goto L35
        L34:
            r8 = r4
        L35:
            com.android.systemui.classifier.FalsingDataProvider r13 = r12.mDataProvider
            java.util.List r10 = r13.getRecentMotionEvents()
            boolean r10 = r10.isEmpty()
            if (r10 == 0) goto L44
            java.util.List r10 = r13.mPriorMotionEvents
            goto L48
        L44:
            java.util.List r10 = r13.getRecentMotionEvents()
        L48:
            com.android.systemui.classifier.SingleTapClassifier r11 = r12.mSingleTapClassifier
            com.android.systemui.classifier.FalsingClassifier$Result r8 = r11.isTap(r10, r8)
            java.util.Set r9 = java.util.Collections.singleton(r8)
            r12.mPriorResults = r9
            boolean r8 = r8.mFalsed
            if (r8 != 0) goto L9b
            boolean r13 = r13.mJustUnlockedWithFace
            if (r13 == 0) goto L63
            java.util.Collection r13 = getPassedResult(r1)
            r12.mPriorResults = r13
            return r3
        L63:
            boolean r13 = r12.isFalseDoubleTap()
            if (r13 != 0) goto L6a
            return r3
        L6a:
            com.android.systemui.classifier.HistoryTracker r13 = r12.mHistoryTracker
            double r1 = r13.falseBelief()
            r8 = 4604480259023595110(0x3fe6666666666666, double:0.7)
            int r13 = (r1 > r8 ? 1 : (r1 == r8 ? 0 : -1))
            if (r13 <= 0) goto L94
            java.lang.String r13 = "BrightLineFalsingManager"
            java.lang.String r1 = "bad history"
            com.android.systemui.classifier.FalsingClassifier$Result r13 = com.android.systemui.classifier.FalsingClassifier.Result.falsed(r6, r13, r1)
            java.util.Set r13 = java.util.Collections.singleton(r13)
            r12.mPriorResults = r13
            java.util.List r12 = r12.mFalsingTapListeners
            com.android.systemui.classifier.BrightLineFalsingManager$$ExternalSyntheticLambda0 r13 = new com.android.systemui.classifier.BrightLineFalsingManager$$ExternalSyntheticLambda0
            r13.<init>(r3)
            java.util.ArrayList r12 = (java.util.ArrayList) r12
            r12.forEach(r13)
            return r0
        L94:
            java.util.Collection r13 = getPassedResult(r4)
            r12.mPriorResults = r13
            return r3
        L9b:
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.classifier.BrightLineFalsingManager.isFalseTap(int):boolean");
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public final boolean isFalseTouch(final int i) {
        checkDestroyed();
        this.mPriorInteractionType = i;
        if (skipFalsing(i)) {
            this.mPriorResults = getPassedResult(1.0d);
            return false;
        }
        final boolean[] zArr = {false};
        this.mPriorResults = (Collection) this.mClassifiers.stream().map(new Function() { // from class: com.android.systemui.classifier.BrightLineFalsingManager$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                BrightLineFalsingManager brightLineFalsingManager = BrightLineFalsingManager.this;
                int i2 = i;
                boolean[] zArr2 = zArr;
                HistoryTracker historyTracker = brightLineFalsingManager.mHistoryTracker;
                historyTracker.falseBelief();
                historyTracker.falseConfidence();
                FalsingClassifier.Result calculateFalsingResult = ((FalsingClassifier) obj).calculateFalsingResult(i2);
                zArr2[0] = zArr2[0] | calculateFalsingResult.mFalsed;
                return calculateFalsingResult;
            }
        }).collect(Collectors.toList());
        if (i == 18) {
            zArr[0] = isFalseTap(2) & zArr[0];
        }
        return zArr[0];
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public final boolean isProximityNear() {
        FalsingManager.ProximityEvent proximityEvent = this.mLastProximityEvent;
        if (proximityEvent != null && proximityEvent.getCovered()) {
            return true;
        }
        return false;
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public final boolean isReportingEnabled() {
        return false;
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public final boolean isSimpleTap() {
        checkDestroyed();
        this.mPriorResults = Collections.singleton(this.mSingleTapClassifier.isTap(this.mDataProvider.getRecentMotionEvents(), 0.0d));
        return !r0.mFalsed;
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public final boolean isUnlockingDisabled() {
        return false;
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public final void onProximityEvent(final FalsingManager.ProximityEvent proximityEvent) {
        this.mLastProximityEvent = proximityEvent;
        this.mClassifiers.forEach(new Consumer() { // from class: com.android.systemui.classifier.BrightLineFalsingManager$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((FalsingClassifier) obj).onProximityEvent(FalsingManager.ProximityEvent.this);
            }
        });
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public final void removeFalsingBeliefListener(FalsingManager.FalsingBeliefListener falsingBeliefListener) {
        ((ArrayList) this.mFalsingBeliefListeners).remove(falsingBeliefListener);
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public final void removeTapListener(FalsingManager.FalsingTapListener falsingTapListener) {
        ((ArrayList) this.mFalsingTapListeners).remove(falsingTapListener);
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public final Uri reportRejectedTouch() {
        return null;
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public final boolean shouldEnforceBouncer() {
        return false;
    }

    public final boolean skipFalsing(int i) {
        boolean z;
        boolean z2;
        if (i == 16 || !((KeyguardStateControllerImpl) this.mKeyguardStateController).mShowing || this.mTestHarness) {
            return true;
        }
        FalsingDataProvider falsingDataProvider = this.mDataProvider;
        if (falsingDataProvider.mJustUnlockedWithFace) {
            return true;
        }
        if (!((BatteryControllerImpl) falsingDataProvider.mBatteryController).mWirelessCharging) {
            falsingDataProvider.mDockManager.getClass();
            z = false;
        } else {
            z = true;
        }
        if (z || this.mAccessibilityManager.isTouchExplorationEnabled() || falsingDataProvider.mA11YAction) {
            return true;
        }
        if (((FeatureFlagsRelease) this.mFeatureFlags).isEnabled(Flags.FALSING_OFF_FOR_UNFOLDED)) {
            if (falsingDataProvider.mIsFoldableDevice && Boolean.FALSE.equals(falsingDataProvider.mFoldStateListener.getFolded())) {
                z2 = true;
            } else {
                z2 = false;
            }
            if (z2) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public final void onSuccessfulUnlock() {
    }
}
