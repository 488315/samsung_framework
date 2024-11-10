package com.android.server.wm;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;
import android.os.Debug;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.Trace;
import android.util.ArraySet;
import android.util.Pair;
import android.util.Pools;
import android.util.RotationUtils;
import android.util.Slog;
import android.util.SparseArray;
import android.util.proto.ProtoOutputStream;
import android.view.DisplayInfo;
import android.view.InsetsFrameProvider;
import android.view.InsetsSource;
import android.view.InsetsState;
import android.view.MagnificationSpec;
import android.view.RemoteAnimationAdapter;
import android.view.RemoteAnimationDefinition;
import android.view.RemoteAnimationTarget;
import android.view.SurfaceControl;
import android.view.SurfaceControlViewHost;
import android.view.SurfaceSession;
import android.view.TaskTransitionSpec;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.window.IWindowContainerToken;
import android.window.WindowContainerToken;
import com.android.internal.graphics.ColorUtils;
import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl;
import com.android.internal.util.ToBooleanFunction;
import com.android.server.display.DisplayManagerService$$ExternalSyntheticLambda13;
import com.android.server.display.DisplayPowerController2;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.android.server.wm.BLASTSyncEngine;
import com.android.server.wm.RemoteAnimationController;
import com.android.server.wm.SurfaceAnimator;
import com.android.server.wm.SurfaceFreezer;
import com.android.server.wm.WindowContainer;
import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public abstract class WindowContainer extends ConfigurationContainer implements Comparable, SurfaceAnimator.Animatable, SurfaceFreezer.Freezable, InsetsControlTarget {
    private static final int INVALID_SYNC_ID = -1;
    static final int POSITION_BOTTOM = Integer.MIN_VALUE;
    static final int POSITION_TOP = Integer.MAX_VALUE;
    public static final int SYNC_STATE_NONE = 0;
    public static final int SYNC_STATE_READY = 2;
    public static final int SYNC_STATE_WAITING_FOR_DRAW = 1;
    private static final String TAG = "WindowManager";
    SurfaceControl mAnimationBoundsLayer;
    private SurfaceControl mAnimationLeash;
    private boolean mCommittedReparentToAnimationLeash;
    protected InsetsSourceProvider mControllableInsetProvider;
    DimAnimator mDimAnimator;
    protected DisplayContent mDisplayContent;
    private MagnificationSpec mLastMagnificationSpec;
    protected WindowContainer mLastOrientationSource;
    boolean mLaunchTaskBehind;
    boolean mNeedsAnimationBoundsLayer;
    boolean mNeedsZBoost;
    protected TrustedOverlayHost mOverlayHost;
    private final SurfaceControl.Transaction mPendingTransaction;
    boolean mReparenting;
    protected final SurfaceAnimator mSurfaceAnimator;
    protected SurfaceControl mSurfaceControl;
    final SurfaceFreezer mSurfaceFreezer;
    final SurfaceControl.Transaction mSyncTransaction;
    WindowContainerThumbnail mThumbnail;
    int mTransit;
    int mTransitFlags;
    final TransitionController mTransitionController;
    boolean mUseConfigurationInUdcCutout;
    boolean mUseLayoutInUdcCutout;
    protected boolean mVisibleRequested;
    protected final WindowManagerService mWmService;
    private WindowContainer mParent = null;
    SparseArray mLocalInsetsSources = null;
    protected SparseArray mInsetsSourceProviders = null;
    protected final WindowList mChildren = new WindowList();
    private int mOverrideOrientation = -1;
    private final Pools.SynchronizedPool mConsumerWrapperPool = new Pools.SynchronizedPool(3);
    private int mLastLayer = 0;
    private SurfaceControl mLastRelativeToLayer = null;
    final ArrayList mWaitingForDrawn = new ArrayList();
    private final ArraySet mSurfaceAnimationSources = new ArraySet();
    private final Point mTmpPos = new Point();
    protected final Point mLastSurfacePosition = new Point();
    protected int mLastDeltaRotation = 0;
    private int mTreeWeight = 1;
    int mSyncTransactionCommitCallbackDepth = 0;
    final Point mTmpPoint = new Point();
    protected final Rect mTmpRect = new Rect();
    final Rect mTmpPrevBounds = new Rect();
    private boolean mIsFocusable = true;
    RemoteToken mRemoteToken = null;
    BLASTSyncEngine.SyncGroup mSyncGroup = null;
    int mSyncState = 0;
    int mSyncMethodOverride = -1;
    int mSyncIdForReparentSurfaceControl = -1;
    private final List mListeners = new ArrayList();
    float mFreeformAlpha = 1.0f;
    private final LinkedList mTmpChain1 = new LinkedList();
    private final LinkedList mTmpChain2 = new LinkedList();

    /* loaded from: classes3.dex */
    public interface ConfigurationMerger {
        Configuration merge(Configuration configuration, Configuration configuration2);
    }

    /* loaded from: classes3.dex */
    public interface IAnimationStarter {
        void startAnimation(SurfaceControl.Transaction transaction, AnimationAdapter animationAdapter, boolean z, int i, AnimationAdapter animationAdapter2);
    }

    public static /* synthetic */ boolean lambda$getActivityAbove$1(ActivityRecord activityRecord) {
        return true;
    }

    public static /* synthetic */ boolean lambda$getActivityBelow$2(ActivityRecord activityRecord) {
        return true;
    }

    public static /* synthetic */ boolean lambda$getBottomMostActivity$3(ActivityRecord activityRecord) {
        return true;
    }

    public static /* synthetic */ boolean lambda$getBottomMostTask$11(Task task) {
        return true;
    }

    public static /* synthetic */ boolean lambda$getTaskAbove$9(Task task) {
        return true;
    }

    public static /* synthetic */ boolean lambda$getTaskBelow$10(Task task) {
        return true;
    }

    public static /* synthetic */ boolean lambda$getTopActivity$5(ActivityRecord activityRecord) {
        return true;
    }

    public static /* synthetic */ boolean lambda$getTopMostActivity$4(ActivityRecord activityRecord) {
        return true;
    }

    public static /* synthetic */ boolean lambda$getTopMostTask$12(Task task) {
        return true;
    }

    public boolean allowEdgeExtension() {
        return true;
    }

    public ActivityRecord asActivityRecord() {
        return null;
    }

    public DisplayArea asDisplayArea() {
        return null;
    }

    public DisplayContent asDisplayContent() {
        return null;
    }

    public RootDisplayArea asRootDisplayArea() {
        return null;
    }

    public Task asTask() {
        return null;
    }

    public TaskDisplayArea asTaskDisplayArea() {
        return null;
    }

    public TaskFragment asTaskFragment() {
        return null;
    }

    public TransientLaunchOverlayToken asTransientLaunchOverlay() {
        return null;
    }

    public WallpaperWindowToken asWallpaperToken() {
        return null;
    }

    public WindowState asWindowState() {
        return null;
    }

    public WindowToken asWindowToken() {
        return null;
    }

    public boolean canCreateRemoteAnimationTarget() {
        return false;
    }

    public boolean canCustomizeAppTransition() {
        return false;
    }

    public RemoteAnimationTarget createRemoteAnimationTarget(RemoteAnimationController.RemoteAnimationRecord remoteAnimationRecord) {
        return null;
    }

    public boolean fillsParent() {
        return false;
    }

    public long getProtoFieldId() {
        return 1146756268034L;
    }

    public RemoteAnimationDefinition getRemoteAnimationDefinition() {
        return null;
    }

    public int getWindowType() {
        return -1;
    }

    public boolean isEmbedded() {
        return false;
    }

    public boolean isOrganized() {
        return false;
    }

    public boolean isSplitEmbedded() {
        return false;
    }

    public boolean isWaitingForTransitionStart() {
        return false;
    }

    public void onChildPositionChanged(WindowContainer windowContainer) {
    }

    public boolean shouldUpdateSyncOnReparent() {
        return true;
    }

    public boolean showSurfaceOnCreation() {
        return true;
    }

    public boolean showToCurrentUser() {
        return true;
    }

    public WindowContainer(WindowManagerService windowManagerService) {
        this.mWmService = windowManagerService;
        this.mTransitionController = windowManagerService.mAtmService.getTransitionController();
        this.mPendingTransaction = (SurfaceControl.Transaction) windowManagerService.mTransactionFactory.get();
        SurfaceControl.Transaction transaction = (SurfaceControl.Transaction) windowManagerService.mTransactionFactory.get();
        this.mSyncTransaction = transaction;
        this.mSurfaceAnimator = new SurfaceAnimator(this, new SurfaceAnimator.OnAnimationFinishedCallback() { // from class: com.android.server.wm.WindowContainer$$ExternalSyntheticLambda4
            @Override // com.android.server.wm.SurfaceAnimator.OnAnimationFinishedCallback
            public final void onAnimationFinished(int i, AnimationAdapter animationAdapter) {
                WindowContainer.this.onAnimationFinished(i, animationAdapter);
            }
        }, windowManagerService);
        this.mSurfaceFreezer = new SurfaceFreezer(this, windowManagerService);
        if (CoreRune.FW_CUSTOM_BASIC_ANIM_WITH_DIM) {
            this.mDimAnimator = new DimAnimator(this);
        }
        transaction.mIsSyncTransaction = true;
    }

    public void updateAboveInsetsState(InsetsState insetsState, SparseArray sparseArray, ArraySet arraySet) {
        SparseArray createMergedSparseArray = createMergedSparseArray(sparseArray, this.mLocalInsetsSources);
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            ((WindowContainer) this.mChildren.get(size)).updateAboveInsetsState(insetsState, createMergedSparseArray, arraySet);
        }
    }

    public static SparseArray createMergedSparseArray(SparseArray sparseArray, SparseArray sparseArray2) {
        int size = sparseArray != null ? sparseArray.size() : 0;
        int size2 = sparseArray2 != null ? sparseArray2.size() : 0;
        SparseArray sparseArray3 = new SparseArray(size + size2);
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                sparseArray3.append(sparseArray.keyAt(i), sparseArray.valueAt(i));
            }
        }
        if (size2 > 0) {
            for (int i2 = 0; i2 < size2; i2++) {
                sparseArray3.put(sparseArray2.keyAt(i2), sparseArray2.valueAt(i2));
            }
        }
        return sparseArray3;
    }

    public void addLocalInsetsFrameProvider(InsetsFrameProvider insetsFrameProvider) {
        if (insetsFrameProvider == null) {
            throw new IllegalArgumentException("Insets type not specified.");
        }
        if (this.mDisplayContent == null) {
            Slog.w("WindowManager", "Can't add insets frame provider when detached. " + this);
            return;
        }
        if (this.mLocalInsetsSources == null) {
            this.mLocalInsetsSources = new SparseArray();
        }
        int id = insetsFrameProvider.getId();
        this.mLocalInsetsSources.get(id);
        InsetsSource insetsSource = new InsetsSource(id, insetsFrameProvider.getType());
        insetsSource.setFrame(insetsFrameProvider.getArbitraryRectangle());
        this.mLocalInsetsSources.put(id, insetsSource);
        this.mDisplayContent.getInsetsStateController().updateAboveInsetsState(true);
    }

    public void removeLocalInsetsFrameProvider(InsetsFrameProvider insetsFrameProvider) {
        if (insetsFrameProvider == null) {
            throw new IllegalArgumentException("Insets type not specified.");
        }
        if (this.mLocalInsetsSources == null) {
            return;
        }
        int id = insetsFrameProvider.getId();
        if (this.mLocalInsetsSources.get(id) == null) {
            return;
        }
        this.mLocalInsetsSources.remove(id);
        DisplayContent displayContent = this.mDisplayContent;
        if (displayContent != null) {
            displayContent.getInsetsStateController().updateAboveInsetsState(true);
        }
    }

    public void setControllableInsetProvider(InsetsSourceProvider insetsSourceProvider) {
        this.mControllableInsetProvider = insetsSourceProvider;
    }

    public InsetsSourceProvider getControllableInsetProvider() {
        return this.mControllableInsetProvider;
    }

    @Override // com.android.server.wm.ConfigurationContainer
    public final WindowContainer getParent() {
        return this.mParent;
    }

    @Override // com.android.server.wm.ConfigurationContainer
    public int getChildCount() {
        return this.mChildren.size();
    }

    @Override // com.android.server.wm.ConfigurationContainer
    public WindowContainer getChildAt(int i) {
        return (WindowContainer) this.mChildren.get(i);
    }

    @Override // com.android.server.wm.ConfigurationContainer
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        updateSurfacePositionNonOrganized();
        scheduleAnimation();
        TrustedOverlayHost trustedOverlayHost = this.mOverlayHost;
        if (trustedOverlayHost != null) {
            trustedOverlayHost.dispatchConfigurationChanged(getConfiguration());
        }
    }

    public void reparent(WindowContainer windowContainer, int i) {
        if (windowContainer == null) {
            throw new IllegalArgumentException("reparent: can't reparent to null " + this);
        }
        if (windowContainer == this) {
            throw new IllegalArgumentException("Can not reparent to itself " + this);
        }
        WindowContainer windowContainer2 = this.mParent;
        if (windowContainer2 == windowContainer) {
            throw new IllegalArgumentException("WC=" + this + " already child of " + this.mParent);
        }
        this.mTransitionController.collectReparentChange(this, windowContainer);
        DisplayContent displayContent = windowContainer2.getDisplayContent();
        DisplayContent displayContent2 = windowContainer.getDisplayContent();
        this.mReparenting = true;
        windowContainer2.removeChild(this);
        windowContainer.addChild(this, i);
        this.mReparenting = false;
        displayContent2.setLayoutNeeded();
        if (displayContent != displayContent2) {
            if (CoreRune.MW_CAPTION_SHELL_OPACITY) {
                this.mFreeformAlpha = 1.0f;
            }
            onDisplayChanged(displayContent2);
            displayContent.setLayoutNeeded();
        }
        onParentChanged(windowContainer, windowContainer2);
        onSyncReparent(windowContainer2, windowContainer);
    }

    public final void setParent(WindowContainer windowContainer) {
        DisplayContent displayContent;
        WindowContainer windowContainer2 = this.mParent;
        this.mParent = windowContainer;
        if (windowContainer != null) {
            windowContainer.onChildAdded(this);
        } else if (this.mSurfaceAnimator.hasLeash()) {
            this.mSurfaceAnimator.cancelAnimation();
        }
        if (this.mReparenting) {
            return;
        }
        onSyncReparent(windowContainer2, this.mParent);
        WindowContainer windowContainer3 = this.mParent;
        if (windowContainer3 != null && (displayContent = windowContainer3.mDisplayContent) != null && this.mDisplayContent != displayContent) {
            onDisplayChanged(displayContent);
        }
        onParentChanged(this.mParent, windowContainer2);
    }

    @Override // com.android.server.wm.ConfigurationContainer
    public void onParentChanged(ConfigurationContainer configurationContainer, ConfigurationContainer configurationContainer2) {
        onParentChanged(configurationContainer, configurationContainer2, null);
    }

    public void onParentChanged(ConfigurationContainer configurationContainer, ConfigurationContainer configurationContainer2, Runnable runnable) {
        super.onParentChanged(configurationContainer, configurationContainer2);
        if (this.mParent == null) {
            return;
        }
        if (this.mSurfaceControl == null) {
            createSurfaceControl(false);
        } else {
            reparentSurfaceControl(getSyncTransaction(), this.mParent.mSurfaceControl);
        }
        if (runnable != null) {
            runnable.run();
        }
        this.mParent.assignChildLayers();
    }

    public void createSurfaceControl(boolean z) {
        setInitialSurfaceControlProperties(makeSurface());
    }

    public void setInitialSurfaceControlProperties(SurfaceControl.Builder builder) {
        setSurfaceControl(builder.setCallsite("WindowContainer.setInitialSurfaceControlProperties").build());
        if (showSurfaceOnCreation()) {
            getSyncTransaction().show(this.mSurfaceControl);
        }
        updateSurfacePositionNonOrganized();
        if (this.mLastMagnificationSpec != null) {
            applyMagnificationSpec(getSyncTransaction(), this.mLastMagnificationSpec);
        }
    }

    public void migrateToNewSurfaceControl(SurfaceControl.Transaction transaction) {
        transaction.remove(this.mSurfaceControl);
        this.mLastSurfacePosition.set(0, 0);
        this.mLastDeltaRotation = 0;
        setInitialSurfaceControlProperties(this.mWmService.makeSurfaceBuilder(null).setContainerLayer().setName(getName()));
        SurfaceControl surfaceControl = this.mSurfaceControl;
        WindowContainer windowContainer = this.mParent;
        transaction.reparent(surfaceControl, windowContainer != null ? windowContainer.getSurfaceControl() : null);
        SurfaceControl surfaceControl2 = this.mLastRelativeToLayer;
        if (surfaceControl2 != null) {
            transaction.setRelativeLayer(this.mSurfaceControl, surfaceControl2, this.mLastLayer);
        } else {
            transaction.setLayer(this.mSurfaceControl, this.mLastLayer);
        }
        for (int i = 0; i < this.mChildren.size(); i++) {
            SurfaceControl surfaceControl3 = ((WindowContainer) this.mChildren.get(i)).getSurfaceControl();
            if (surfaceControl3 != null) {
                transaction.reparent(surfaceControl3, this.mSurfaceControl);
            }
        }
        TrustedOverlayHost trustedOverlayHost = this.mOverlayHost;
        if (trustedOverlayHost != null) {
            trustedOverlayHost.setParent(transaction, this.mSurfaceControl);
        }
        scheduleAnimation();
    }

    public void addChild(WindowContainer windowContainer, Comparator comparator) {
        int i;
        if (!windowContainer.mReparenting && windowContainer.getParent() != null) {
            throw new IllegalArgumentException("addChild: container=" + windowContainer.getName() + " is already a child of container=" + windowContainer.getParent().getName() + " can't add to container=" + getName());
        }
        if (comparator != null) {
            int size = this.mChildren.size();
            i = 0;
            while (i < size) {
                if (comparator.compare(windowContainer, (WindowContainer) this.mChildren.get(i)) < 0) {
                    break;
                } else {
                    i++;
                }
            }
        }
        i = -1;
        if (i == -1) {
            this.mChildren.add(windowContainer);
        } else {
            this.mChildren.add(i, windowContainer);
        }
        windowContainer.setParent(this);
    }

    public void addChild(WindowContainer windowContainer, int i) {
        if (!windowContainer.mReparenting && windowContainer.getParent() != null) {
            throw new IllegalArgumentException("addChild: container=" + windowContainer.getName() + " is already a child of container=" + windowContainer.getParent().getName() + " can't add to container=" + getName() + "\n callers=" + Debug.getCallers(15, KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE));
        }
        if ((i < 0 && i != POSITION_BOTTOM) || (i > this.mChildren.size() && i != Integer.MAX_VALUE)) {
            throw new IllegalArgumentException("addChild: invalid position=" + i + ", children number=" + this.mChildren.size());
        }
        if (i == Integer.MAX_VALUE) {
            i = this.mChildren.size();
        } else if (i == POSITION_BOTTOM) {
            i = 0;
        }
        this.mChildren.add(i, windowContainer);
        windowContainer.setParent(this);
    }

    public final void onChildAdded(WindowContainer windowContainer) {
        this.mTreeWeight += windowContainer.mTreeWeight;
        for (WindowContainer parent = getParent(); parent != null; parent = parent.getParent()) {
            parent.mTreeWeight += windowContainer.mTreeWeight;
        }
        onChildVisibleRequestedChanged(windowContainer);
        onChildPositionChanged(windowContainer);
    }

    public void removeChild(WindowContainer windowContainer) {
        if (this.mChildren.remove(windowContainer)) {
            onChildRemoved(windowContainer);
            if (windowContainer.mReparenting) {
                return;
            }
            windowContainer.setParent(null);
            return;
        }
        throw new IllegalArgumentException("removeChild: container=" + windowContainer.getName() + " is not a child of container=" + getName());
    }

    public final void onChildRemoved(WindowContainer windowContainer) {
        this.mTreeWeight -= windowContainer.mTreeWeight;
        for (WindowContainer parent = getParent(); parent != null; parent = parent.getParent()) {
            parent.mTreeWeight -= windowContainer.mTreeWeight;
        }
        onChildVisibleRequestedChanged(null);
        onChildPositionChanged(windowContainer);
    }

    public void removeImmediately() {
        DisplayContent displayContent = getDisplayContent();
        if (displayContent != null) {
            displayContent.mClosingChangingContainers.remove(this);
            this.mSurfaceFreezer.unfreeze(getSyncTransaction());
        }
        while (!this.mChildren.isEmpty()) {
            WindowContainer windowContainer = (WindowContainer) this.mChildren.peekLast();
            windowContainer.removeImmediately();
            if (this.mChildren.remove(windowContainer)) {
                onChildRemoved(windowContainer);
            }
        }
        if (CoreRune.FW_CUSTOM_BASIC_ANIM_WITH_DIM) {
            this.mDimAnimator.finishDimAnimation(0);
        }
        if (this.mSurfaceControl != null) {
            getSyncTransaction().remove(this.mSurfaceControl);
            setSurfaceControl(null);
            this.mLastSurfacePosition.set(0, 0);
            this.mLastDeltaRotation = 0;
            scheduleAnimation();
        }
        TrustedOverlayHost trustedOverlayHost = this.mOverlayHost;
        if (trustedOverlayHost != null) {
            trustedOverlayHost.release();
            this.mOverlayHost = null;
        }
        WindowContainer windowContainer2 = this.mParent;
        if (windowContainer2 != null) {
            windowContainer2.removeChild(this);
        }
        for (int size = this.mListeners.size() - 1; size >= 0; size--) {
            ((WindowContainerListener) this.mListeners.get(size)).onRemoved();
        }
    }

    public int getTreeWeight() {
        return this.mTreeWeight;
    }

    public int getPrefixOrderIndex() {
        WindowContainer windowContainer = this.mParent;
        if (windowContainer == null) {
            return 0;
        }
        return windowContainer.getPrefixOrderIndex(this);
    }

    public final int getPrefixOrderIndex(WindowContainer windowContainer) {
        WindowContainer windowContainer2;
        int i = 0;
        for (int i2 = 0; i2 < this.mChildren.size() && windowContainer != (windowContainer2 = (WindowContainer) this.mChildren.get(i2)); i2++) {
            i += windowContainer2.mTreeWeight;
        }
        WindowContainer windowContainer3 = this.mParent;
        if (windowContainer3 != null) {
            i += windowContainer3.getPrefixOrderIndex(this);
        }
        return i + 1;
    }

    public void removeIfPossible() {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            ((WindowContainer) this.mChildren.get(size)).removeIfPossible();
        }
    }

    public boolean hasChild(WindowContainer windowContainer) {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            WindowContainer windowContainer2 = (WindowContainer) this.mChildren.get(size);
            if (windowContainer2 == windowContainer || windowContainer2.hasChild(windowContainer)) {
                return true;
            }
        }
        return false;
    }

    public boolean isDescendantOf(WindowContainer windowContainer) {
        WindowContainer parent = getParent();
        if (parent == windowContainer) {
            return true;
        }
        return parent != null && parent.isDescendantOf(windowContainer);
    }

    public void positionChildAt(int i, WindowContainer windowContainer, boolean z) {
        if (windowContainer.getParent() != this) {
            throw new IllegalArgumentException("positionChildAt: container=" + windowContainer.getName() + " is not a child of container=" + getName() + " current parent=" + windowContainer.getParent());
        }
        if (i >= this.mChildren.size() - 1) {
            i = Integer.MAX_VALUE;
        } else if (i <= 0) {
            i = POSITION_BOTTOM;
        }
        if (i == POSITION_BOTTOM) {
            if (this.mChildren.peekFirst() != windowContainer) {
                this.mChildren.remove(windowContainer);
                this.mChildren.addFirst(windowContainer);
                onChildPositionChanged(windowContainer);
            }
            if (!z || getParent() == null) {
                return;
            }
            getParent().positionChildAt(POSITION_BOTTOM, this, true);
            return;
        }
        if (i == Integer.MAX_VALUE) {
            if (this.mChildren.peekLast() != windowContainer) {
                this.mChildren.remove(windowContainer);
                this.mChildren.add(windowContainer);
                onChildPositionChanged(windowContainer);
            }
            if (!z || getParent() == null) {
                return;
            }
            getParent().positionChildAt(Integer.MAX_VALUE, this, true);
            return;
        }
        if (this.mChildren.indexOf(windowContainer) != i) {
            this.mChildren.remove(windowContainer);
            this.mChildren.add(i, windowContainer);
            onChildPositionChanged(windowContainer);
        }
    }

    @Override // com.android.server.wm.ConfigurationContainer
    public void onRequestedOverrideConfigurationChanged(Configuration configuration) {
        int diffRequestedOverrideBounds = diffRequestedOverrideBounds(configuration.windowConfiguration.getBounds());
        super.onRequestedOverrideConfigurationChanged(configuration);
        WindowContainer windowContainer = this.mParent;
        if (windowContainer != null) {
            windowContainer.onDescendantOverrideConfigurationChanged();
        }
        if (diffRequestedOverrideBounds == 0) {
            return;
        }
        if ((diffRequestedOverrideBounds & 2) == 2) {
            onResize();
        } else {
            onMovedByResize();
        }
    }

    public void onDescendantOverrideConfigurationChanged() {
        WindowContainer windowContainer = this.mParent;
        if (windowContainer != null) {
            windowContainer.onDescendantOverrideConfigurationChanged();
        }
    }

    public void onDisplayChanged(DisplayContent displayContent) {
        SparseArray sparseArray;
        DisplayContent displayContent2 = this.mDisplayContent;
        boolean z = displayContent2 != displayContent;
        if (displayContent2 != null && displayContent2 != displayContent) {
            displayContent2.mClosingChangingContainers.remove(this);
            if (this.mDisplayContent.mChangingContainers.remove(this)) {
                this.mSurfaceFreezer.unfreeze(getSyncTransaction());
            }
        }
        this.mDisplayContent = displayContent;
        if (displayContent != null && displayContent != this) {
            displayContent.getPendingTransaction().merge(this.mPendingTransaction);
        }
        if (displayContent != this && (sparseArray = this.mLocalInsetsSources) != null && (!CoreRune.MW_CAPTION_SHELL_BUG_FIX || z)) {
            sparseArray.clear();
        }
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            ((WindowContainer) this.mChildren.get(size)).onDisplayChanged(displayContent);
        }
        for (int size2 = this.mListeners.size() - 1; size2 >= 0; size2--) {
            ((WindowContainerListener) this.mListeners.get(size2)).onDisplayChanged(displayContent);
        }
    }

    public boolean hasInsetsSourceProvider() {
        return this.mInsetsSourceProviders != null;
    }

    public SparseArray getInsetsSourceProviders() {
        if (this.mInsetsSourceProviders == null) {
            this.mInsetsSourceProviders = new SparseArray();
        }
        return this.mInsetsSourceProviders;
    }

    public DisplayContent getDisplayContent() {
        return this.mDisplayContent;
    }

    public DisplayArea getDisplayArea() {
        WindowContainer parent = getParent();
        if (parent != null) {
            return parent.getDisplayArea();
        }
        return null;
    }

    public RootDisplayArea getRootDisplayArea() {
        WindowContainer parent = getParent();
        if (parent != null) {
            return parent.getRootDisplayArea();
        }
        return null;
    }

    public TaskDisplayArea getTaskDisplayArea() {
        WindowContainer parent = getParent();
        if (parent != null) {
            return parent.getTaskDisplayArea();
        }
        return null;
    }

    public boolean isAttached() {
        WindowContainer parent = getParent();
        return parent != null && parent.isAttached();
    }

    public void setWaitingForDrawnIfResizingChanged() {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            ((WindowContainer) this.mChildren.get(size)).setWaitingForDrawnIfResizingChanged();
        }
    }

    public void onResize() {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            ((WindowContainer) this.mChildren.get(size)).onParentResize();
        }
    }

    public void onParentResize() {
        if (hasOverrideBounds()) {
            return;
        }
        onResize();
    }

    public void onMovedByResize() {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            ((WindowContainer) this.mChildren.get(size)).onMovedByResize();
        }
    }

    public void resetDragResizingChangeReported() {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            ((WindowContainer) this.mChildren.get(size)).resetDragResizingChangeReported();
        }
    }

    public final boolean isAnimating(int i, int i2) {
        return getAnimatingContainer(i, i2) != null;
    }

    @Deprecated
    public final boolean isAnimating(int i) {
        return isAnimating(i, -1);
    }

    public boolean isAppTransitioning() {
        return getActivity(new Predicate() { // from class: com.android.server.wm.WindowContainer$$ExternalSyntheticLambda15
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean isAnimating;
                isAnimating = ((ActivityRecord) obj).isAnimating(3);
                return isAnimating;
            }
        }) != null;
    }

    public boolean inTransitionSelfOrParent() {
        if (!this.mTransitionController.isShellTransitionsEnabled()) {
            return isAnimating(3, 9);
        }
        return inTransition();
    }

    public final boolean isAnimating() {
        return isAnimating(0);
    }

    public boolean isChangingAppTransition() {
        DisplayContent displayContent = this.mDisplayContent;
        return displayContent != null && displayContent.mChangingContainers.contains(this);
    }

    public boolean inTransition() {
        return this.mTransitionController.inTransition(this);
    }

    public boolean isExitAnimationRunningSelfOrChild() {
        if (!this.mTransitionController.isShellTransitionsEnabled()) {
            return isAnimating(5, 25);
        }
        if (this.mChildren.isEmpty() && inTransition()) {
            return true;
        }
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            if (((WindowContainer) this.mChildren.get(size)).isExitAnimationRunningSelfOrChild()) {
                return true;
            }
        }
        return false;
    }

    public void sendAppVisibilityToClients() {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            ((WindowContainer) this.mChildren.get(size)).sendAppVisibilityToClients();
        }
    }

    public boolean hasContentToDisplay() {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            if (((WindowContainer) this.mChildren.get(size)).hasContentToDisplay()) {
                return true;
            }
        }
        return false;
    }

    public boolean inTransition(int i) {
        if (!this.mTransitionController.isShellTransitionsEnabled()) {
            return isAnimating(i, 9);
        }
        if (inTransition()) {
            return true;
        }
        if ((i & 2) != 0) {
            for (WindowContainer parent = getParent(); parent != null; parent = parent.getParent()) {
                if (parent.inTransition(i)) {
                    return true;
                }
            }
        }
        if ((i & 4) != 0) {
            for (int i2 = 0; i2 < this.mChildren.size(); i2++) {
                if (((WindowContainer) this.mChildren.get(i2)).inTransition(i & (-3))) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isVisible() {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            if (((WindowContainer) this.mChildren.get(size)).isVisible()) {
                return true;
            }
        }
        return false;
    }

    public boolean isVisibleRequested() {
        return this.mVisibleRequested;
    }

    public boolean setVisibleRequested(boolean z) {
        if (this.mVisibleRequested == z) {
            return false;
        }
        this.mVisibleRequested = z;
        WindowContainer parent = getParent();
        if (parent != null) {
            parent.onChildVisibleRequestedChanged(this);
        }
        for (int size = this.mListeners.size() - 1; size >= 0; size--) {
            ((WindowContainerListener) this.mListeners.get(size)).onVisibleRequestedChanged(this.mVisibleRequested);
        }
        return true;
    }

    public boolean onChildVisibleRequestedChanged(WindowContainer windowContainer) {
        boolean z = false;
        boolean z2 = windowContainer != null && windowContainer.isVisibleRequested();
        boolean z3 = this.mVisibleRequested;
        if (!z2 || z3) {
            if (z2 || !z3) {
                z = z3;
            } else {
                for (int size = this.mChildren.size() - 1; size >= 0; size--) {
                    WindowContainer windowContainer2 = (WindowContainer) this.mChildren.get(size);
                    if (windowContainer2 == windowContainer || !windowContainer2.isVisibleRequested()) {
                    }
                }
            }
            return setVisibleRequested(z);
        }
        z = true;
        return setVisibleRequested(z);
    }

    public void onChildVisibilityRequested(boolean z) {
        if (!z) {
            if (!(asTaskFragment() != null ? asTaskFragment().setClosingChangingStartBoundsIfNeeded() : false)) {
                this.mSurfaceFreezer.unfreeze(getSyncTransaction());
            }
        }
        WindowContainer parent = getParent();
        if (parent != null) {
            parent.onChildVisibilityRequested(z);
        }
    }

    public boolean isClosingWhenResizing() {
        DisplayContent displayContent = this.mDisplayContent;
        return displayContent != null && displayContent.mClosingChangingContainers.containsKey(this);
    }

    public void writeIdentifierToProto(ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1120986464257L, System.identityHashCode(this));
        protoOutputStream.write(1120986464258L, -10000);
        protoOutputStream.write(1138166333443L, "WindowContainer");
        protoOutputStream.end(start);
    }

    public boolean isFocusable() {
        WindowContainer parent = getParent();
        return (parent == null || parent.isFocusable()) && this.mIsFocusable;
    }

    public boolean setFocusable(boolean z) {
        if (this.mIsFocusable == z) {
            return false;
        }
        this.mIsFocusable = z;
        return true;
    }

    public boolean isOnTop() {
        WindowContainer parent = getParent();
        return parent != null && parent.getTopChild() == this && parent.isOnTop();
    }

    public WindowContainer getTopChild() {
        return (WindowContainer) this.mChildren.peekLast();
    }

    public boolean handleCompleteDeferredRemoval() {
        boolean z = false;
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            z |= ((WindowContainer) this.mChildren.get(size)).handleCompleteDeferredRemoval();
            if (!hasChild()) {
                return false;
            }
        }
        return z;
    }

    public void checkAppWindowsReadyToShow() {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            ((WindowContainer) this.mChildren.get(size)).checkAppWindowsReadyToShow();
        }
    }

    public void onAppTransitionDone() {
        if (this.mSurfaceFreezer.hasLeash()) {
            this.mSurfaceFreezer.unfreeze(getSyncTransaction());
        }
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            ((WindowContainer) this.mChildren.get(size)).onAppTransitionDone();
        }
    }

    public boolean onDescendantOrientationChanged(WindowContainer windowContainer) {
        WindowContainer parent = getParent();
        if (parent == null) {
            return false;
        }
        return parent.onDescendantOrientationChanged(windowContainer);
    }

    public boolean handlesOrientationChangeFromDescendant(int i) {
        WindowContainer parent = getParent();
        return parent != null && parent.handlesOrientationChangeFromDescendant(i);
    }

    public int getRequestedConfigurationOrientation() {
        return getRequestedConfigurationOrientation(false);
    }

    public int getRequestedConfigurationOrientation(boolean z) {
        return getRequestedConfigurationOrientation(z, getOverrideOrientation());
    }

    public int getRequestedConfigurationOrientation(boolean z, int i) {
        RootDisplayArea rootDisplayArea = getRootDisplayArea();
        if (z && rootDisplayArea != null && rootDisplayArea.isOrientationDifferentFromDisplay()) {
            i = ActivityInfo.reverseOrientation(i);
        }
        if (i == 5) {
            DisplayContent displayContent = this.mDisplayContent;
            if (displayContent != null) {
                return displayContent.getNaturalOrientation();
            }
            return 0;
        }
        if (i == 14) {
            return getConfiguration().orientation;
        }
        if (ActivityInfo.isFixedOrientationLandscape(i)) {
            return 2;
        }
        return ActivityInfo.isFixedOrientationPortrait(i) ? 1 : 0;
    }

    public void setOrientation(int i) {
        setOrientation(i, null);
    }

    public void setOrientation(int i, WindowContainer windowContainer) {
        WindowContainer parent;
        if (getOverrideOrientation() == i) {
            return;
        }
        setOverrideOrientation(i);
        if ((CoreRune.FW_ORIENTATION_CONTROL_WITH_ASPECT_RATIO && this.mWmService.mAtmService.mExt.mOrientationController.interceptSetOrientationIfNeeded(asActivityRecord())) || (parent = getParent()) == null) {
            return;
        }
        if (getConfiguration().orientation != getRequestedConfigurationOrientation() && (inMultiWindowMode() || !handlesOrientationChangeFromDescendant(i))) {
            onConfigurationChanged(parent.getConfiguration());
        }
        onDescendantOrientationChanged(windowContainer);
    }

    public int getOrientation() {
        return getOrientation(getOverrideOrientation());
    }

    public int getOrientation(int i) {
        int adjustedOrientation;
        this.mLastOrientationSource = null;
        if (!providesOrientation()) {
            return -2;
        }
        if (getOverrideOrientation() != -2 && getOverrideOrientation() != -1) {
            this.mLastOrientationSource = this;
            return getOverrideOrientation();
        }
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            WindowContainer windowContainer = (WindowContainer) this.mChildren.get(size);
            if (CoreRune.FW_ORIENTATION_CONTROL && (adjustedOrientation = this.mWmService.mAtmService.mExt.mOrientationController.getAdjustedOrientation(windowContainer.asActivityRecord(), size, false, i)) != -2) {
                this.mLastOrientationSource = windowContainer;
                this.mDisplayContent.mLastOrientationControlSource = windowContainer;
                return adjustedOrientation;
            }
            int orientation = windowContainer.getOrientation(i == 3 ? 3 : -2);
            if (orientation == 3) {
                this.mLastOrientationSource = windowContainer;
                i = orientation;
            } else if (orientation != -2 && (windowContainer.providesOrientation() || orientation != -1)) {
                if (ProtoLogCache.WM_DEBUG_ORIENTATION_enabled) {
                    ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_ORIENTATION, -1108775960, 4, (String) null, new Object[]{String.valueOf(windowContainer.toString()), Long.valueOf(orientation), String.valueOf(ActivityInfo.screenOrientationToString(orientation))});
                }
                this.mLastOrientationSource = windowContainer;
                return orientation;
            }
        }
        return i;
    }

    public int getOverrideOrientation() {
        return this.mOverrideOrientation;
    }

    public void setOverrideOrientation(int i) {
        this.mOverrideOrientation = i;
    }

    public WindowContainer getLastOrientationSource() {
        WindowContainer lastOrientationSource;
        WindowContainer windowContainer = this.mLastOrientationSource;
        return (windowContainer == null || windowContainer == this || (lastOrientationSource = windowContainer.getLastOrientationSource()) == null) ? windowContainer : lastOrientationSource;
    }

    public boolean providesOrientation() {
        return fillsParent();
    }

    public static int computeScreenLayout(int i, int i2, int i3) {
        return Configuration.reduceScreenLayout(i & 63, Math.max(i2, i3), Math.min(i2, i3));
    }

    public void switchUser(int i) {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            ((WindowContainer) this.mChildren.get(size)).switchUser(i);
        }
    }

    public void forAllWindowContainers(Consumer consumer) {
        consumer.accept(this);
        int size = this.mChildren.size();
        for (int i = 0; i < size; i++) {
            ((WindowContainer) this.mChildren.get(i)).forAllWindowContainers(consumer);
        }
    }

    public boolean forAllWindows(ToBooleanFunction toBooleanFunction, boolean z) {
        if (z) {
            for (int size = this.mChildren.size() - 1; size >= 0; size--) {
                if (((WindowContainer) this.mChildren.get(size)).forAllWindows(toBooleanFunction, z)) {
                    return true;
                }
            }
        } else {
            int size2 = this.mChildren.size();
            for (int i = 0; i < size2; i++) {
                if (((WindowContainer) this.mChildren.get(i)).forAllWindows(toBooleanFunction, z)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void forAllWindows(Consumer consumer, boolean z) {
        ForAllWindowsConsumerWrapper obtainConsumerWrapper = obtainConsumerWrapper(consumer);
        forAllWindows(obtainConsumerWrapper, z);
        obtainConsumerWrapper.release();
    }

    public boolean forAllActivities(Predicate predicate) {
        return forAllActivities(predicate, true);
    }

    public boolean forAllActivities(Predicate predicate, boolean z) {
        if (z) {
            for (int size = this.mChildren.size() - 1; size >= 0; size--) {
                if (((WindowContainer) this.mChildren.get(size)).forAllActivities(predicate, z)) {
                    return true;
                }
            }
        } else {
            int size2 = this.mChildren.size();
            for (int i = 0; i < size2; i++) {
                if (((WindowContainer) this.mChildren.get(i)).forAllActivities(predicate, z)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void forAllActivities(Consumer consumer) {
        forAllActivities(consumer, true);
    }

    public void forAllActivities(Consumer consumer, boolean z) {
        if (z) {
            for (int size = this.mChildren.size() - 1; size >= 0; size--) {
                ((WindowContainer) this.mChildren.get(size)).forAllActivities(consumer, z);
            }
            return;
        }
        int size2 = this.mChildren.size();
        for (int i = 0; i < size2; i++) {
            ((WindowContainer) this.mChildren.get(i)).forAllActivities(consumer, z);
        }
    }

    public final boolean forAllActivities(Predicate predicate, WindowContainer windowContainer, boolean z, boolean z2) {
        return forAllActivities(predicate, windowContainer, z, z2, new boolean[1]);
    }

    public final boolean forAllActivities(Predicate predicate, WindowContainer windowContainer, boolean z, boolean z2, boolean[] zArr) {
        if (z2) {
            for (int size = this.mChildren.size() - 1; size >= 0; size--) {
                if (processForAllActivitiesWithBoundary(predicate, windowContainer, z, z2, zArr, (WindowContainer) this.mChildren.get(size))) {
                    return true;
                }
            }
        } else {
            int size2 = this.mChildren.size();
            for (int i = 0; i < size2; i++) {
                if (processForAllActivitiesWithBoundary(predicate, windowContainer, z, z2, zArr, (WindowContainer) this.mChildren.get(i))) {
                    return true;
                }
            }
        }
        return false;
    }

    public final boolean processForAllActivitiesWithBoundary(Predicate predicate, WindowContainer windowContainer, boolean z, boolean z2, boolean[] zArr, WindowContainer windowContainer2) {
        if (windowContainer2 == windowContainer) {
            zArr[0] = true;
            if (!z) {
                return false;
            }
        }
        if (zArr[0]) {
            return windowContainer2.forAllActivities(predicate, z2);
        }
        return windowContainer2.forAllActivities(predicate, windowContainer, z, z2, zArr);
    }

    public boolean hasActivity() {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            if (((WindowContainer) this.mChildren.get(size)).hasActivity()) {
                return true;
            }
        }
        return false;
    }

    public ActivityRecord getActivity(Predicate predicate) {
        return getActivity(predicate, true);
    }

    public ActivityRecord getActivity(Predicate predicate, boolean z) {
        return getActivity(predicate, z, null);
    }

    public ActivityRecord getActivity(Predicate predicate, boolean z, ActivityRecord activityRecord) {
        if (z) {
            for (int size = this.mChildren.size() - 1; size >= 0; size--) {
                WindowContainer windowContainer = (WindowContainer) this.mChildren.get(size);
                if (windowContainer == activityRecord) {
                    return activityRecord;
                }
                ActivityRecord activity = windowContainer.getActivity(predicate, z, activityRecord);
                if (activity != null) {
                    return activity;
                }
            }
            return null;
        }
        int size2 = this.mChildren.size();
        for (int i = 0; i < size2; i++) {
            WindowContainer windowContainer2 = (WindowContainer) this.mChildren.get(i);
            if (windowContainer2 == activityRecord) {
                return activityRecord;
            }
            ActivityRecord activity2 = windowContainer2.getActivity(predicate, z, activityRecord);
            if (activity2 != null) {
                return activity2;
            }
        }
        return null;
    }

    public final ActivityRecord getActivity(Predicate predicate, WindowContainer windowContainer, boolean z, boolean z2) {
        return getActivity(predicate, windowContainer, z, z2, new boolean[1]);
    }

    public final ActivityRecord getActivity(Predicate predicate, WindowContainer windowContainer, boolean z, boolean z2, boolean[] zArr) {
        if (z2) {
            for (int size = this.mChildren.size() - 1; size >= 0; size--) {
                ActivityRecord processGetActivityWithBoundary = processGetActivityWithBoundary(predicate, windowContainer, z, z2, zArr, (WindowContainer) this.mChildren.get(size));
                if (processGetActivityWithBoundary != null) {
                    return processGetActivityWithBoundary;
                }
            }
            return null;
        }
        int size2 = this.mChildren.size();
        for (int i = 0; i < size2; i++) {
            ActivityRecord processGetActivityWithBoundary2 = processGetActivityWithBoundary(predicate, windowContainer, z, z2, zArr, (WindowContainer) this.mChildren.get(i));
            if (processGetActivityWithBoundary2 != null) {
                return processGetActivityWithBoundary2;
            }
        }
        return null;
    }

    public int getDistanceFromTop(WindowContainer windowContainer) {
        int indexOf = this.mChildren.indexOf(windowContainer);
        if (indexOf < 0) {
            return -1;
        }
        return (this.mChildren.size() - 1) - indexOf;
    }

    public final ActivityRecord processGetActivityWithBoundary(Predicate predicate, WindowContainer windowContainer, boolean z, boolean z2, boolean[] zArr, WindowContainer windowContainer2) {
        if (windowContainer2 == windowContainer || windowContainer == null) {
            zArr[0] = true;
            if (!z) {
                return null;
            }
        }
        if (zArr[0]) {
            return windowContainer2.getActivity(predicate, z2);
        }
        return windowContainer2.getActivity(predicate, windowContainer, z, z2, zArr);
    }

    public ActivityRecord getActivityAbove(ActivityRecord activityRecord) {
        return getActivity(new Predicate() { // from class: com.android.server.wm.WindowContainer$$ExternalSyntheticLambda11
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$getActivityAbove$1;
                lambda$getActivityAbove$1 = WindowContainer.lambda$getActivityAbove$1((ActivityRecord) obj);
                return lambda$getActivityAbove$1;
            }
        }, activityRecord, false, false);
    }

    public ActivityRecord getActivityBelow(ActivityRecord activityRecord) {
        return getActivity(new Predicate() { // from class: com.android.server.wm.WindowContainer$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$getActivityBelow$2;
                lambda$getActivityBelow$2 = WindowContainer.lambda$getActivityBelow$2((ActivityRecord) obj);
                return lambda$getActivityBelow$2;
            }
        }, activityRecord, false, true);
    }

    public ActivityRecord getBottomMostActivity() {
        return getActivity(new Predicate() { // from class: com.android.server.wm.WindowContainer$$ExternalSyntheticLambda14
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$getBottomMostActivity$3;
                lambda$getBottomMostActivity$3 = WindowContainer.lambda$getBottomMostActivity$3((ActivityRecord) obj);
                return lambda$getBottomMostActivity$3;
            }
        }, false);
    }

    public ActivityRecord getTopMostActivity() {
        return getActivity(new Predicate() { // from class: com.android.server.wm.WindowContainer$$ExternalSyntheticLambda9
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$getTopMostActivity$4;
                lambda$getTopMostActivity$4 = WindowContainer.lambda$getTopMostActivity$4((ActivityRecord) obj);
                return lambda$getTopMostActivity$4;
            }
        }, true);
    }

    public ActivityRecord getTopActivity(boolean z, boolean z2) {
        if (z) {
            if (z2) {
                return getActivity(new Predicate() { // from class: com.android.server.wm.WindowContainer$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        boolean lambda$getTopActivity$5;
                        lambda$getTopActivity$5 = WindowContainer.lambda$getTopActivity$5((ActivityRecord) obj);
                        return lambda$getTopActivity$5;
                    }
                });
            }
            return getActivity(new Predicate() { // from class: com.android.server.wm.WindowContainer$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    boolean lambda$getTopActivity$6;
                    lambda$getTopActivity$6 = WindowContainer.lambda$getTopActivity$6((ActivityRecord) obj);
                    return lambda$getTopActivity$6;
                }
            });
        }
        if (z2) {
            return getActivity(new Predicate() { // from class: com.android.server.wm.WindowContainer$$ExternalSyntheticLambda2
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    boolean lambda$getTopActivity$7;
                    lambda$getTopActivity$7 = WindowContainer.lambda$getTopActivity$7((ActivityRecord) obj);
                    return lambda$getTopActivity$7;
                }
            });
        }
        return getActivity(new Predicate() { // from class: com.android.server.wm.WindowContainer$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$getTopActivity$8;
                lambda$getTopActivity$8 = WindowContainer.lambda$getTopActivity$8((ActivityRecord) obj);
                return lambda$getTopActivity$8;
            }
        });
    }

    public static /* synthetic */ boolean lambda$getTopActivity$6(ActivityRecord activityRecord) {
        return !activityRecord.isTaskOverlay();
    }

    public static /* synthetic */ boolean lambda$getTopActivity$7(ActivityRecord activityRecord) {
        return !activityRecord.finishing;
    }

    public static /* synthetic */ boolean lambda$getTopActivity$8(ActivityRecord activityRecord) {
        return (activityRecord.finishing || activityRecord.isTaskOverlay()) ? false : true;
    }

    public void forAllWallpaperWindows(Consumer consumer) {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            ((WindowContainer) this.mChildren.get(size)).forAllWallpaperWindows(consumer);
        }
    }

    public boolean forAllTasks(Predicate predicate) {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            if (((WindowContainer) this.mChildren.get(size)).forAllTasks(predicate)) {
                return true;
            }
        }
        return false;
    }

    public boolean forAllLeafTasks(Predicate predicate) {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            if (((WindowContainer) this.mChildren.get(size)).forAllLeafTasks(predicate)) {
                return true;
            }
        }
        return false;
    }

    public boolean forAllLeafTaskFragments(Predicate predicate) {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            if (((WindowContainer) this.mChildren.get(size)).forAllLeafTaskFragments(predicate)) {
                return true;
            }
        }
        return false;
    }

    public boolean forAllRootTasks(Predicate predicate) {
        return forAllRootTasks(predicate, true);
    }

    public boolean forAllRootTasks(Predicate predicate, boolean z) {
        int size = this.mChildren.size();
        if (z) {
            for (int i = size - 1; i >= 0; i--) {
                if (((WindowContainer) this.mChildren.get(i)).forAllRootTasks(predicate, z)) {
                    return true;
                }
            }
        } else {
            int i2 = 0;
            while (i2 < size) {
                if (((WindowContainer) this.mChildren.get(i2)).forAllRootTasks(predicate, z)) {
                    return true;
                }
                int size2 = this.mChildren.size();
                i2 = (i2 - (size - size2)) + 1;
                size = size2;
            }
        }
        return false;
    }

    public void forAllTasks(Consumer consumer) {
        forAllTasks(consumer, true);
    }

    public void forAllTasks(Consumer consumer, boolean z) {
        int size = this.mChildren.size();
        if (z) {
            for (int i = size - 1; i >= 0; i--) {
                ((WindowContainer) this.mChildren.get(i)).forAllTasks(consumer, z);
            }
            return;
        }
        for (int i2 = 0; i2 < size; i2++) {
            ((WindowContainer) this.mChildren.get(i2)).forAllTasks(consumer, z);
        }
    }

    public void forAllTaskFragments(Consumer consumer) {
        forAllTaskFragments(consumer, true);
    }

    public void forAllTaskFragments(Consumer consumer, boolean z) {
        int size = this.mChildren.size();
        if (z) {
            for (int i = size - 1; i >= 0; i--) {
                ((WindowContainer) this.mChildren.get(i)).forAllTaskFragments(consumer, z);
            }
            return;
        }
        for (int i2 = 0; i2 < size; i2++) {
            ((WindowContainer) this.mChildren.get(i2)).forAllTaskFragments(consumer, z);
        }
    }

    public void forAllLeafTasks(Consumer consumer, boolean z) {
        int size = this.mChildren.size();
        if (z) {
            for (int i = size - 1; i >= 0; i--) {
                ((WindowContainer) this.mChildren.get(i)).forAllLeafTasks(consumer, z);
            }
            return;
        }
        for (int i2 = 0; i2 < size; i2++) {
            ((WindowContainer) this.mChildren.get(i2)).forAllLeafTasks(consumer, z);
        }
    }

    public void forAllLeafTaskFragments(Consumer consumer, boolean z) {
        int size = this.mChildren.size();
        if (z) {
            for (int i = size - 1; i >= 0; i--) {
                ((WindowContainer) this.mChildren.get(i)).forAllLeafTaskFragments(consumer, z);
            }
            return;
        }
        for (int i2 = 0; i2 < size; i2++) {
            ((WindowContainer) this.mChildren.get(i2)).forAllLeafTaskFragments(consumer, z);
        }
    }

    public void forAllRootTasks(Consumer consumer) {
        forAllRootTasks(consumer, true);
    }

    public void forAllRootTasks(Consumer consumer, boolean z) {
        int size = this.mChildren.size();
        if (z) {
            for (int i = size - 1; i >= 0; i--) {
                ((WindowContainer) this.mChildren.get(i)).forAllRootTasks(consumer, z);
            }
            return;
        }
        int i2 = 0;
        while (i2 < size) {
            ((WindowContainer) this.mChildren.get(i2)).forAllRootTasks(consumer, z);
            int size2 = this.mChildren.size();
            i2 = (i2 - (size - size2)) + 1;
            size = size2;
        }
    }

    public Task getTaskAbove(Task task) {
        return getTask(new Predicate() { // from class: com.android.server.wm.WindowContainer$$ExternalSyntheticLambda13
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$getTaskAbove$9;
                lambda$getTaskAbove$9 = WindowContainer.lambda$getTaskAbove$9((Task) obj);
                return lambda$getTaskAbove$9;
            }
        }, task, false, false);
    }

    public Task getTaskBelow(Task task) {
        return getTask(new Predicate() { // from class: com.android.server.wm.WindowContainer$$ExternalSyntheticLambda10
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$getTaskBelow$10;
                lambda$getTaskBelow$10 = WindowContainer.lambda$getTaskBelow$10((Task) obj);
                return lambda$getTaskBelow$10;
            }
        }, task, false, true);
    }

    public Task getBottomMostTask() {
        return getTask(new Predicate() { // from class: com.android.server.wm.WindowContainer$$ExternalSyntheticLambda12
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$getBottomMostTask$11;
                lambda$getBottomMostTask$11 = WindowContainer.lambda$getBottomMostTask$11((Task) obj);
                return lambda$getBottomMostTask$11;
            }
        }, false);
    }

    public Task getTopMostTask() {
        return getTask(new Predicate() { // from class: com.android.server.wm.WindowContainer$$ExternalSyntheticLambda8
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$getTopMostTask$12;
                lambda$getTopMostTask$12 = WindowContainer.lambda$getTopMostTask$12((Task) obj);
                return lambda$getTopMostTask$12;
            }
        }, true);
    }

    public Task getTask(Predicate predicate) {
        return getTask(predicate, true);
    }

    public Task getTask(Predicate predicate, boolean z) {
        if (z) {
            for (int size = this.mChildren.size() - 1; size >= 0; size--) {
                Task task = ((WindowContainer) this.mChildren.get(size)).getTask(predicate, z);
                if (task != null) {
                    return task;
                }
            }
            return null;
        }
        int size2 = this.mChildren.size();
        for (int i = 0; i < size2; i++) {
            Task task2 = ((WindowContainer) this.mChildren.get(i)).getTask(predicate, z);
            if (task2 != null) {
                return task2;
            }
        }
        return null;
    }

    public final Task getTask(Predicate predicate, WindowContainer windowContainer, boolean z, boolean z2) {
        return getTask(predicate, windowContainer, z, z2, new boolean[1]);
    }

    public final Task getTask(Predicate predicate, WindowContainer windowContainer, boolean z, boolean z2, boolean[] zArr) {
        if (z2) {
            for (int size = this.mChildren.size() - 1; size >= 0; size--) {
                Task processGetTaskWithBoundary = processGetTaskWithBoundary(predicate, windowContainer, z, z2, zArr, (WindowContainer) this.mChildren.get(size));
                if (processGetTaskWithBoundary != null) {
                    return processGetTaskWithBoundary;
                }
            }
            return null;
        }
        int size2 = this.mChildren.size();
        for (int i = 0; i < size2; i++) {
            Task processGetTaskWithBoundary2 = processGetTaskWithBoundary(predicate, windowContainer, z, z2, zArr, (WindowContainer) this.mChildren.get(i));
            if (processGetTaskWithBoundary2 != null) {
                return processGetTaskWithBoundary2;
            }
        }
        return null;
    }

    public Task getRootTask(Predicate predicate) {
        return getRootTask(predicate, true);
    }

    public Task getRootTask(Predicate predicate, boolean z) {
        int size = this.mChildren.size();
        if (z) {
            for (int i = size - 1; i >= 0; i--) {
                Task rootTask = ((WindowContainer) this.mChildren.get(i)).getRootTask(predicate, z);
                if (rootTask != null) {
                    return rootTask;
                }
            }
            return null;
        }
        int i2 = 0;
        while (i2 < size) {
            Task rootTask2 = ((WindowContainer) this.mChildren.get(i2)).getRootTask(predicate, z);
            if (rootTask2 != null) {
                return rootTask2;
            }
            int size2 = this.mChildren.size();
            i2 = (i2 - (size - size2)) + 1;
            size = size2;
        }
        return null;
    }

    public final Task processGetTaskWithBoundary(Predicate predicate, WindowContainer windowContainer, boolean z, boolean z2, boolean[] zArr, WindowContainer windowContainer2) {
        if (windowContainer2 == windowContainer || windowContainer == null) {
            zArr[0] = true;
            if (!z) {
                return null;
            }
        }
        if (zArr[0]) {
            return windowContainer2.getTask(predicate, z2);
        }
        return windowContainer2.getTask(predicate, windowContainer, z, z2, zArr);
    }

    public TaskFragment getTaskFragment(Predicate predicate) {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            TaskFragment taskFragment = ((WindowContainer) this.mChildren.get(size)).getTaskFragment(predicate);
            if (taskFragment != null) {
                return taskFragment;
            }
        }
        return null;
    }

    public WindowState getWindow(Predicate predicate) {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            WindowState window = ((WindowContainer) this.mChildren.get(size)).getWindow(predicate);
            if (window != null) {
                return window;
            }
        }
        return null;
    }

    public void forAllDisplayAreas(Consumer consumer) {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            ((WindowContainer) this.mChildren.get(size)).forAllDisplayAreas(consumer);
        }
    }

    public boolean forAllTaskDisplayAreas(Predicate predicate, boolean z) {
        int size = this.mChildren.size();
        int i = z ? size - 1 : 0;
        while (i >= 0 && i < size) {
            int i2 = 1;
            if (((WindowContainer) this.mChildren.get(i)).forAllTaskDisplayAreas(predicate, z)) {
                return true;
            }
            if (z) {
                i2 = -1;
            }
            i += i2;
        }
        return false;
    }

    public boolean forAllTaskDisplayAreas(Predicate predicate) {
        return forAllTaskDisplayAreas(predicate, true);
    }

    public void forAllTaskDisplayAreas(Consumer consumer, boolean z) {
        int size = this.mChildren.size();
        int i = z ? size - 1 : 0;
        while (i >= 0 && i < size) {
            ((WindowContainer) this.mChildren.get(i)).forAllTaskDisplayAreas(consumer, z);
            i += z ? -1 : 1;
        }
    }

    public void forAllTaskDisplayAreas(Consumer consumer) {
        forAllTaskDisplayAreas(consumer, true);
    }

    public Object reduceOnAllTaskDisplayAreas(BiFunction biFunction, Object obj, boolean z) {
        int size = this.mChildren.size();
        int i = z ? size - 1 : 0;
        while (i >= 0 && i < size) {
            obj = ((WindowContainer) this.mChildren.get(i)).reduceOnAllTaskDisplayAreas(biFunction, obj, z);
            i += z ? -1 : 1;
        }
        return obj;
    }

    public Object reduceOnAllTaskDisplayAreas(BiFunction biFunction, Object obj) {
        return reduceOnAllTaskDisplayAreas(biFunction, obj, true);
    }

    public Object getItemFromDisplayAreas(Function function) {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            Object itemFromDisplayAreas = ((WindowContainer) this.mChildren.get(size)).getItemFromDisplayAreas(function);
            if (itemFromDisplayAreas != null) {
                return itemFromDisplayAreas;
            }
        }
        return null;
    }

    public Object getItemFromTaskDisplayAreas(Function function, boolean z) {
        int size = this.mChildren.size();
        int i = z ? size - 1 : 0;
        while (i >= 0 && i < size) {
            Object itemFromTaskDisplayAreas = ((WindowContainer) this.mChildren.get(i)).getItemFromTaskDisplayAreas(function, z);
            if (itemFromTaskDisplayAreas != null) {
                return itemFromTaskDisplayAreas;
            }
            i += z ? -1 : 1;
        }
        return null;
    }

    public Object getItemFromTaskDisplayAreas(Function function) {
        return getItemFromTaskDisplayAreas(function, true);
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x0053, code lost:
    
        if (r6 != r7) goto L25;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x005f, code lost:
    
        return -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0060, code lost:
    
        if (r6 != r8) goto L28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x006c, code lost:
    
        return r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x006d, code lost:
    
        r8 = r6.mChildren;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x007f, code lost:
    
        if (r8.indexOf(r0.peekLast()) <= r8.indexOf(r3.peekLast())) goto L31;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0082, code lost:
    
        r1 = -1;
     */
    @Override // java.lang.Comparable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int compareTo(com.android.server.wm.WindowContainer r8) {
        /*
            r7 = this;
            if (r7 != r8) goto L4
            r7 = 0
            return r7
        L4:
            com.android.server.wm.WindowContainer r0 = r7.mParent
            r1 = 1
            r2 = -1
            if (r0 == 0) goto L1d
            com.android.server.wm.WindowContainer r3 = r8.mParent
            if (r0 != r3) goto L1d
            com.android.server.wm.WindowList r0 = r0.mChildren
            int r7 = r0.indexOf(r7)
            int r8 = r0.indexOf(r8)
            if (r7 <= r8) goto L1b
            goto L1c
        L1b:
            r1 = r2
        L1c:
            return r1
        L1d:
            java.util.LinkedList r0 = r7.mTmpChain1
            java.util.LinkedList r3 = r7.mTmpChain2
            r7.getParents(r0)     // Catch: java.lang.Throwable -> La3
            r8.getParents(r3)     // Catch: java.lang.Throwable -> La3
            java.lang.Object r4 = r0.peekLast()     // Catch: java.lang.Throwable -> La3
            com.android.server.wm.WindowContainer r4 = (com.android.server.wm.WindowContainer) r4     // Catch: java.lang.Throwable -> La3
            java.lang.Object r5 = r3.peekLast()     // Catch: java.lang.Throwable -> La3
            com.android.server.wm.WindowContainer r5 = (com.android.server.wm.WindowContainer) r5     // Catch: java.lang.Throwable -> La3
            r6 = 0
        L34:
            if (r4 == 0) goto L51
            if (r5 == 0) goto L51
            if (r4 != r5) goto L51
            java.lang.Object r4 = r0.removeLast()     // Catch: java.lang.Throwable -> La3
            r6 = r4
            com.android.server.wm.WindowContainer r6 = (com.android.server.wm.WindowContainer) r6     // Catch: java.lang.Throwable -> La3
            r3.removeLast()     // Catch: java.lang.Throwable -> La3
            java.lang.Object r4 = r0.peekLast()     // Catch: java.lang.Throwable -> La3
            com.android.server.wm.WindowContainer r4 = (com.android.server.wm.WindowContainer) r4     // Catch: java.lang.Throwable -> La3
            java.lang.Object r5 = r3.peekLast()     // Catch: java.lang.Throwable -> La3
            com.android.server.wm.WindowContainer r5 = (com.android.server.wm.WindowContainer) r5     // Catch: java.lang.Throwable -> La3
            goto L34
        L51:
            if (r6 == 0) goto L84
            if (r6 != r7) goto L60
            java.util.LinkedList r8 = r7.mTmpChain1
            r8.clear()
            java.util.LinkedList r7 = r7.mTmpChain2
            r7.clear()
            return r2
        L60:
            if (r6 != r8) goto L6d
        L62:
            java.util.LinkedList r8 = r7.mTmpChain1
            r8.clear()
            java.util.LinkedList r7 = r7.mTmpChain2
            r7.clear()
            return r1
        L6d:
            com.android.server.wm.WindowList r8 = r6.mChildren     // Catch: java.lang.Throwable -> La3
            java.lang.Object r0 = r0.peekLast()     // Catch: java.lang.Throwable -> La3
            int r0 = r8.indexOf(r0)     // Catch: java.lang.Throwable -> La3
            java.lang.Object r3 = r3.peekLast()     // Catch: java.lang.Throwable -> La3
            int r8 = r8.indexOf(r3)     // Catch: java.lang.Throwable -> La3
            if (r0 <= r8) goto L82
            goto L62
        L82:
            r1 = r2
            goto L62
        L84:
            java.lang.IllegalArgumentException r8 = new java.lang.IllegalArgumentException     // Catch: java.lang.Throwable -> La3
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> La3
            r1.<init>()     // Catch: java.lang.Throwable -> La3
            java.lang.String r2 = "No in the same hierarchy this="
            r1.append(r2)     // Catch: java.lang.Throwable -> La3
            r1.append(r0)     // Catch: java.lang.Throwable -> La3
            java.lang.String r0 = " other="
            r1.append(r0)     // Catch: java.lang.Throwable -> La3
            r1.append(r3)     // Catch: java.lang.Throwable -> La3
            java.lang.String r0 = r1.toString()     // Catch: java.lang.Throwable -> La3
            r8.<init>(r0)     // Catch: java.lang.Throwable -> La3
            throw r8     // Catch: java.lang.Throwable -> La3
        La3:
            r8 = move-exception
            java.util.LinkedList r0 = r7.mTmpChain1
            r0.clear()
            java.util.LinkedList r7 = r7.mTmpChain2
            r7.clear()
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.WindowContainer.compareTo(com.android.server.wm.WindowContainer):int");
    }

    public final void getParents(LinkedList linkedList) {
        linkedList.clear();
        do {
            linkedList.addLast(this);
            this = this.mParent;
        } while (this != null);
    }

    public SurfaceControl.Builder makeSurface() {
        return getParent().makeChildSurface(this);
    }

    public SurfaceControl.Builder makeChildSurface(WindowContainer windowContainer) {
        return getParent().makeChildSurface(windowContainer).setParent(this.mSurfaceControl);
    }

    public SurfaceControl getParentSurfaceControl() {
        WindowContainer parent = getParent();
        if (parent == null) {
            return null;
        }
        return parent.getSurfaceControl();
    }

    public boolean shouldMagnify() {
        if (this.mSurfaceControl == null) {
            return false;
        }
        for (int i = 0; i < this.mChildren.size(); i++) {
            if (!((WindowContainer) this.mChildren.get(i)).shouldMagnify()) {
                return false;
            }
        }
        return true;
    }

    public SurfaceSession getSession() {
        if (getParent() != null) {
            return getParent().getSession();
        }
        return null;
    }

    public void assignLayer(SurfaceControl.Transaction transaction, int i) {
        assignLayer(transaction, i, false);
    }

    public void assignLayer(SurfaceControl.Transaction transaction, int i, boolean z) {
        if ((CoreRune.FW_CUSTOM_SHELL_TRANSITION_BUG_FIX && z) || this.mTransitionController.canAssignLayers(this)) {
            boolean z2 = (i == this.mLastLayer && this.mLastRelativeToLayer == null) ? false : true;
            if (this.mSurfaceControl == null || !z2) {
                return;
            }
            setLayer(transaction, i);
            this.mLastLayer = i;
            this.mLastRelativeToLayer = null;
        }
    }

    public void assignRelativeLayer(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl, int i, boolean z) {
        boolean z2 = (i == this.mLastLayer && this.mLastRelativeToLayer == surfaceControl) ? false : true;
        if (this.mSurfaceControl != null) {
            if (z2 || z) {
                setRelativeLayer(transaction, surfaceControl, i);
                this.mLastLayer = i;
                this.mLastRelativeToLayer = surfaceControl;
            }
        }
    }

    public void assignRelativeLayer(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl, int i) {
        assignRelativeLayer(transaction, surfaceControl, i, false);
    }

    public void setLayer(SurfaceControl.Transaction transaction, int i) {
        if (this.mSurfaceFreezer.hasLeash()) {
            this.mSurfaceFreezer.setLayer(transaction, i);
        } else {
            this.mSurfaceAnimator.setLayer(transaction, i);
        }
    }

    public int getLastLayer() {
        return this.mLastLayer;
    }

    public void setRelativeLayer(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl, int i) {
        if (this.mSurfaceFreezer.hasLeash()) {
            this.mSurfaceFreezer.setRelativeLayer(transaction, surfaceControl, i);
        } else {
            this.mSurfaceAnimator.setRelativeLayer(transaction, surfaceControl, i);
        }
    }

    public void reparentSurfaceControl(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl) {
        if (this.mSurfaceFreezer.hasLeash() || this.mSurfaceAnimator.hasLeash()) {
            return;
        }
        transaction.reparent(getSurfaceControl(), surfaceControl);
    }

    public void assignChildLayers(SurfaceControl.Transaction transaction) {
        int i = 0;
        for (int i2 = 0; i2 < this.mChildren.size(); i2++) {
            WindowContainer windowContainer = (WindowContainer) this.mChildren.get(i2);
            windowContainer.assignChildLayers(transaction);
            if (!windowContainer.needsZBoost()) {
                windowContainer.assignLayer(transaction, i);
                i++;
            }
        }
        for (int i3 = 0; i3 < this.mChildren.size(); i3++) {
            WindowContainer windowContainer2 = (WindowContainer) this.mChildren.get(i3);
            if (windowContainer2.needsZBoost()) {
                windowContainer2.assignLayer(transaction, i);
                i++;
            }
        }
        TrustedOverlayHost trustedOverlayHost = this.mOverlayHost;
        if (trustedOverlayHost != null) {
            trustedOverlayHost.setLayer(transaction, i);
        }
    }

    public void assignChildLayers() {
        assignChildLayers(getSyncTransaction());
        scheduleAnimation();
    }

    public boolean needsZBoost() {
        if (this.mNeedsZBoost) {
            return true;
        }
        for (int i = 0; i < this.mChildren.size(); i++) {
            if (((WindowContainer) this.mChildren.get(i)).needsZBoost()) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.server.wm.ConfigurationContainer
    public void dumpDebug(ProtoOutputStream protoOutputStream, long j, int i) {
        boolean isVisible = isVisible();
        if (i != 2 || isVisible) {
            long start = protoOutputStream.start(j);
            super.dumpDebug(protoOutputStream, 1146756268033L, i);
            protoOutputStream.write(1120986464258L, this.mOverrideOrientation);
            protoOutputStream.write(1133871366147L, isVisible);
            writeIdentifierToProto(protoOutputStream, 1146756268038L);
            if (this.mSurfaceAnimator.isAnimating()) {
                this.mSurfaceAnimator.dumpDebug(protoOutputStream, 1146756268036L);
            }
            SurfaceControl surfaceControl = this.mSurfaceControl;
            if (surfaceControl != null) {
                surfaceControl.dumpDebug(protoOutputStream, 1146756268039L);
            }
            for (int i2 = 0; i2 < getChildCount(); i2++) {
                long start2 = protoOutputStream.start(2246267895813L);
                WindowContainer childAt = getChildAt(i2);
                childAt.dumpDebug(protoOutputStream, childAt.getProtoFieldId(), i);
                protoOutputStream.end(start2);
            }
            protoOutputStream.end(start);
        }
    }

    public final ForAllWindowsConsumerWrapper obtainConsumerWrapper(Consumer consumer) {
        ForAllWindowsConsumerWrapper forAllWindowsConsumerWrapper = (ForAllWindowsConsumerWrapper) this.mConsumerWrapperPool.acquire();
        if (forAllWindowsConsumerWrapper == null) {
            forAllWindowsConsumerWrapper = new ForAllWindowsConsumerWrapper();
        }
        forAllWindowsConsumerWrapper.setConsumer(consumer);
        return forAllWindowsConsumerWrapper;
    }

    /* loaded from: classes3.dex */
    public final class ForAllWindowsConsumerWrapper implements ToBooleanFunction {
        public Consumer mConsumer;

        public ForAllWindowsConsumerWrapper() {
        }

        public void setConsumer(Consumer consumer) {
            this.mConsumer = consumer;
        }

        public boolean apply(WindowState windowState) {
            this.mConsumer.accept(windowState);
            return false;
        }

        public void release() {
            this.mConsumer = null;
            WindowContainer.this.mConsumerWrapperPool.release(this);
        }
    }

    public void applyMagnificationSpec(SurfaceControl.Transaction transaction, MagnificationSpec magnificationSpec) {
        if (shouldMagnify()) {
            SurfaceControl surfaceControl = this.mSurfaceControl;
            float f = magnificationSpec.scale;
            SurfaceControl.Transaction matrix = transaction.setMatrix(surfaceControl, f, DisplayPowerController2.RATE_FROM_DOZE_TO_ON, DisplayPowerController2.RATE_FROM_DOZE_TO_ON, f);
            SurfaceControl surfaceControl2 = this.mSurfaceControl;
            float f2 = magnificationSpec.offsetX;
            Point point = this.mLastSurfacePosition;
            matrix.setPosition(surfaceControl2, f2 + point.x, magnificationSpec.offsetY + point.y);
            this.mLastMagnificationSpec = magnificationSpec;
            return;
        }
        clearMagnificationSpec(transaction);
        for (int i = 0; i < this.mChildren.size(); i++) {
            ((WindowContainer) this.mChildren.get(i)).applyMagnificationSpec(transaction, magnificationSpec);
        }
    }

    public void clearMagnificationSpec(SurfaceControl.Transaction transaction) {
        if (this.mLastMagnificationSpec != null) {
            SurfaceControl.Transaction matrix = transaction.setMatrix(this.mSurfaceControl, 1.0f, DisplayPowerController2.RATE_FROM_DOZE_TO_ON, DisplayPowerController2.RATE_FROM_DOZE_TO_ON, 1.0f);
            SurfaceControl surfaceControl = this.mSurfaceControl;
            Point point = this.mLastSurfacePosition;
            matrix.setPosition(surfaceControl, point.x, point.y);
        }
        this.mLastMagnificationSpec = null;
        for (int i = 0; i < this.mChildren.size(); i++) {
            ((WindowContainer) this.mChildren.get(i)).clearMagnificationSpec(transaction);
        }
    }

    public void prepareSurfaces() {
        this.mCommittedReparentToAnimationLeash = this.mSurfaceAnimator.hasLeash();
        for (int i = 0; i < this.mChildren.size(); i++) {
            ((WindowContainer) this.mChildren.get(i)).prepareSurfaces();
        }
    }

    public boolean hasCommittedReparentToAnimationLeash() {
        return this.mCommittedReparentToAnimationLeash;
    }

    public void scheduleAnimation() {
        this.mWmService.scheduleAnimationLocked();
    }

    public SurfaceControl getSurfaceControl() {
        return this.mSurfaceControl;
    }

    public SurfaceControl.Transaction getSyncTransaction() {
        if (this.mSyncTransactionCommitCallbackDepth > 0) {
            return this.mSyncTransaction;
        }
        if (this.mSyncState != 0) {
            return this.mSyncTransaction;
        }
        return getPendingTransaction();
    }

    public SurfaceControl.Transaction getPendingTransaction() {
        DisplayContent displayContent = getDisplayContent();
        if (displayContent != null && displayContent != this) {
            return displayContent.getPendingTransaction();
        }
        return this.mPendingTransaction;
    }

    public void startAnimation(SurfaceControl.Transaction transaction, AnimationAdapter animationAdapter, boolean z, int i, SurfaceAnimator.OnAnimationFinishedCallback onAnimationFinishedCallback, Runnable runnable, AnimationAdapter animationAdapter2) {
        if (ProtoLogCache.WM_DEBUG_ANIM_enabled) {
            ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_ANIM, 385595355, 4, (String) null, new Object[]{String.valueOf(this), Long.valueOf(i), String.valueOf(animationAdapter)});
        }
        this.mSurfaceAnimator.startAnimation(transaction, animationAdapter, z, i, onAnimationFinishedCallback, runnable, animationAdapter2, this.mSurfaceFreezer);
    }

    public void startAnimation(SurfaceControl.Transaction transaction, AnimationAdapter animationAdapter, boolean z, int i, SurfaceAnimator.OnAnimationFinishedCallback onAnimationFinishedCallback) {
        startAnimation(transaction, animationAdapter, z, i, onAnimationFinishedCallback, null, null);
    }

    public void startAnimation(SurfaceControl.Transaction transaction, AnimationAdapter animationAdapter, boolean z, int i) {
        startAnimation(transaction, animationAdapter, z, i, null);
    }

    public void transferAnimation(WindowContainer windowContainer) {
        this.mSurfaceAnimator.transferAnimation(windowContainer.mSurfaceAnimator);
    }

    public void cancelAnimation() {
        cancelAnimation(null);
    }

    public void cancelAnimation(SurfaceControl.Transaction transaction) {
        doAnimationFinished(this.mSurfaceAnimator.getAnimationType(), this.mSurfaceAnimator.getAnimation());
        if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_BUG_FIX && transaction != null) {
            this.mSurfaceAnimator.cancelAnimation(transaction, false, true);
        } else {
            this.mSurfaceAnimator.cancelAnimation();
        }
        this.mSurfaceFreezer.unfreeze(getSyncTransaction());
    }

    public boolean canStartChangeTransition() {
        return (this.mWmService.mDisableTransitionAnimation || this.mDisplayContent == null || getSurfaceControl() == null || this.mDisplayContent.inTransition() || !isVisible() || !isVisibleRequested() || !okToAnimate() || inPinnedWindowingMode() || getParent() == null || getParent().inPinnedWindowingMode()) ? false : true;
    }

    public void initializeChangeTransition(Rect rect, SurfaceControl surfaceControl) {
        if (this.mDisplayContent.mTransitionController.isShellTransitionsEnabled()) {
            this.mDisplayContent.mTransitionController.collectVisibleChange(this);
            return;
        }
        this.mDisplayContent.prepareAppTransition(6);
        this.mDisplayContent.mChangingContainers.add(this);
        Rect bounds = getParent().getBounds();
        this.mTmpPoint.set(rect.left - bounds.left, rect.top - bounds.top);
        this.mSurfaceFreezer.freeze(getSyncTransaction(), rect, this.mTmpPoint, surfaceControl);
    }

    public void initializeChangeTransition(Rect rect) {
        initializeChangeTransition(rect, null);
    }

    public ArraySet getAnimationSources() {
        return this.mSurfaceAnimationSources;
    }

    public SurfaceControl getFreezeSnapshotTarget() {
        if (this.mDisplayContent.mAppTransition.containsTransitRequest(6) && this.mDisplayContent.mChangingContainers.contains(this)) {
            return getSurfaceControl();
        }
        return null;
    }

    public void onUnfrozen() {
        DisplayContent displayContent = this.mDisplayContent;
        if (displayContent != null) {
            displayContent.mChangingContainers.remove(this);
        }
    }

    public SurfaceControl.Builder makeAnimationLeash() {
        return makeSurface().setContainerLayer();
    }

    public SurfaceControl getAnimationLeashParent() {
        return getParentSurfaceControl();
    }

    public Rect getAnimationBounds(int i) {
        return getBounds();
    }

    public void getAnimationPosition(Point point) {
        getRelativePosition(point);
    }

    public boolean applyAnimation(WindowManager.LayoutParams layoutParams, int i, boolean z, boolean z2, ArrayList arrayList) {
        if (this.mWmService.mDisableTransitionAnimation) {
            if (ProtoLogCache.WM_DEBUG_APP_TRANSITIONS_ANIM_enabled) {
                ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS_ANIM, -33096143, 0, (String) null, new Object[]{String.valueOf(this)});
            }
            cancelAnimation();
            return false;
        }
        try {
            Trace.traceBegin(32L, "WC#applyAnimation");
            if (okToAnimate()) {
                if (ProtoLogCache.WM_DEBUG_APP_TRANSITIONS_ANIM_enabled) {
                    ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS_ANIM, -701167286, 12, (String) null, new Object[]{String.valueOf(AppTransition.appTransitionOldToString(i)), Boolean.valueOf(z), String.valueOf(this)});
                }
                applyAnimationUnchecked(layoutParams, z, i, z2, arrayList);
            } else {
                cancelAnimation();
            }
            Trace.traceEnd(32L);
            return isAnimating();
        } catch (Throwable th) {
            Trace.traceEnd(32L);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00f5  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00fa  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00e3  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.util.Pair getAnimationAdapter(android.view.WindowManager.LayoutParams r20, int r21, boolean r22, boolean r23) {
        /*
            Method dump skipped, instructions count: 504
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.WindowContainer.getAnimationAdapter(android.view.WindowManager$LayoutParams, int, boolean, boolean):android.util.Pair");
    }

    public void applyAnimationUnchecked(WindowManager.LayoutParams layoutParams, boolean z, int i, boolean z2, ArrayList arrayList) {
        TaskFragment organizedTaskFragment;
        Task task;
        int backgroundColor;
        Task asTask = asTask();
        if (asTask != null && !z && !asTask.isActivityTypeHomeOrRecents()) {
            boolean z3 = false;
            InsetsControlTarget imeTarget = this.mDisplayContent.getImeTarget(0);
            if (imeTarget != null && imeTarget.getWindow() != null && imeTarget.getWindow().getTask() == asTask) {
                z3 = true;
            }
            if (z3 && AppTransition.isTaskCloseTransitOld(i)) {
                this.mDisplayContent.showImeScreenshot();
            }
        }
        Pair animationAdapter = getAnimationAdapter(layoutParams, i, z, z2);
        AnimationAdapter animationAdapter2 = (AnimationAdapter) animationAdapter.first;
        AnimationAdapter animationAdapter3 = (AnimationAdapter) animationAdapter.second;
        if (animationAdapter2 != null) {
            if (arrayList != null) {
                this.mSurfaceAnimationSources.addAll(arrayList);
            }
            AnimationRunnerBuilder animationRunnerBuilder = new AnimationRunnerBuilder();
            if (AppTransition.isTaskTransitOld(i) && getWindowingMode() == 1) {
                animationRunnerBuilder.setTaskBackgroundColor(getTaskAnimationBackgroundColor());
                if (this.mWmService.mTaskTransitionSpec != null) {
                    animationRunnerBuilder.hideInsetSourceViewOverflows();
                }
            }
            ActivityRecord asActivityRecord = asActivityRecord();
            TaskFragment asTaskFragment = asTaskFragment();
            if (animationAdapter2.getShowBackground() && ((asActivityRecord != null && AppTransition.isActivityTransitOld(i)) || (asTaskFragment != null && asTaskFragment.isEmbedded() && AppTransition.isTaskFragmentTransitOld(i)))) {
                if (animationAdapter2.getBackgroundColor() != 0) {
                    backgroundColor = animationAdapter2.getBackgroundColor();
                } else {
                    if (asActivityRecord != null) {
                        organizedTaskFragment = asActivityRecord.getOrganizedTaskFragment();
                    } else {
                        organizedTaskFragment = asTaskFragment.getOrganizedTaskFragment();
                    }
                    if (organizedTaskFragment != null && organizedTaskFragment.getAnimationParams().getAnimationBackgroundColor() != 0) {
                        backgroundColor = organizedTaskFragment.getAnimationParams().getAnimationBackgroundColor();
                    } else {
                        if (asActivityRecord != null) {
                            task = asActivityRecord.getTask();
                        } else {
                            task = asTaskFragment.getTask();
                        }
                        backgroundColor = task.getTaskDescription().getBackgroundColor();
                    }
                }
                animationRunnerBuilder.setTaskBackgroundColor(ColorUtils.setAlphaComponent(backgroundColor, IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT));
            }
            animationRunnerBuilder.build().startAnimation(getPendingTransaction(), animationAdapter2, !isVisible(), 1, animationAdapter3);
            if (animationAdapter2.getShowWallpaper()) {
                getDisplayContent().pendingLayoutChanges |= 4;
            }
        }
    }

    public final int getTaskAnimationBackgroundColor() {
        int i;
        Context systemUiContext = this.mDisplayContent.getDisplayPolicy().getSystemUiContext();
        TaskTransitionSpec taskTransitionSpec = this.mWmService.mTaskTransitionSpec;
        int color = systemUiContext.getColor(17171165);
        if (taskTransitionSpec != null && (i = taskTransitionSpec.backgroundColor) != 0) {
            return i;
        }
        if (CoreRune.FW_CUSTOM_BASIC_ANIM && CoreRune.SAFE_DEBUG) {
            StringBuilder sb = new StringBuilder();
            sb.append("Default taskAnimationBackgroundColor is transparent(");
            sb.append(color == 0);
            sb.append("), wc=");
            sb.append(this);
            Slog.d("WindowManager", sb.toString());
        }
        return color;
    }

    public final SurfaceAnimationRunner getSurfaceAnimationRunner() {
        return this.mWmService.mSurfaceAnimationRunner;
    }

    public final Animation loadAnimation(WindowManager.LayoutParams layoutParams, int i, boolean z, boolean z2) {
        if (AppTransitionController.isTaskViewTask(this) || !(!isOrganized() || getWindowingMode() == 1 || getWindowingMode() == 5 || getWindowingMode() == 6)) {
            return null;
        }
        DisplayContent displayContent = getDisplayContent();
        DisplayInfo displayInfo = displayContent.getDisplayInfo();
        int i2 = displayInfo.appWidth;
        int i3 = displayInfo.appHeight;
        if (ProtoLogCache.WM_DEBUG_APP_TRANSITIONS_ANIM_enabled) {
            ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS_ANIM, 1584270979, 0, (String) null, new Object[]{String.valueOf(this)});
        }
        Rect rect = new Rect(0, 0, i2, i3);
        Rect rect2 = new Rect(0, 0, displayInfo.logicalWidth, displayInfo.logicalHeight);
        Rect rect3 = new Rect();
        Rect rect4 = new Rect();
        Rect rect5 = new Rect();
        getAnimationFrames(rect, rect3, rect4, rect5);
        boolean z3 = this.mLaunchTaskBehind ? false : z;
        if (ProtoLogCache.WM_FORCE_DEBUG_APP_TRANSITIONS_enabled) {
            ProtoLogImpl.d(ProtoLogGroup.WM_FORCE_DEBUG_APP_TRANSITIONS, -791461438, 12, "Loading animation for app transition. transit=%s enter=%b frame=%s insets=%s surfaceInsets=%s", new Object[]{String.valueOf(AppTransition.appTransitionOldToString(i)), Boolean.valueOf(z3), String.valueOf(rect), String.valueOf(rect3), String.valueOf(rect5)});
        }
        Configuration configuration = displayContent.getConfiguration();
        Animation loadAnimation = getDisplayContent().mAppTransition.loadAnimation(layoutParams, i, z3, configuration.uiMode, configuration.orientation, rect, rect2, rect3, rect5, rect4, z2, inFreeformWindowingMode(), this);
        if (loadAnimation != null) {
            loadAnimation.restrictDuration(3000L);
            if (ProtoLogImpl.isEnabled(ProtoLogGroup.WM_DEBUG_ANIM) && ProtoLogCache.WM_DEBUG_ANIM_enabled) {
                ProtoLogImpl.i(ProtoLogGroup.WM_DEBUG_ANIM, 769218938, 16, (String) null, new Object[]{String.valueOf(loadAnimation), String.valueOf(this), Long.valueOf(loadAnimation.getDuration()), String.valueOf(Debug.getCallers(20))});
            }
            if (asActivityRecord() != null && asActivityRecord().mPopOverState.isActivated() && (AppTransition.isTaskTransitOld(i) || AppTransition.isKeyguardGoingAwayTransitOld(i))) {
                Rect bounds = asActivityRecord().getTask().getBounds();
                loadAnimation.initialize(bounds.width(), bounds.height(), i2, i3);
            } else {
                loadAnimation.initialize(rect.width(), rect.height(), i2, i3);
            }
            loadAnimation.scaleCurrentDuration(this.mWmService.getTransitionAnimationScaleLocked());
        }
        return loadAnimation;
    }

    public boolean okToDisplay() {
        DisplayContent displayContent = getDisplayContent();
        return displayContent != null && displayContent.okToDisplay();
    }

    public boolean okToAnimate() {
        return okToAnimate(false, false);
    }

    public boolean okToAnimate(boolean z, boolean z2) {
        DisplayContent displayContent = getDisplayContent();
        return displayContent != null && displayContent.okToAnimate(z, z2);
    }

    public void commitPendingTransaction() {
        scheduleAnimation();
    }

    public void transformFrameToSurfacePosition(int i, int i2, Point point) {
        point.set(i, i2);
        WindowContainer parent = getParent();
        if (parent == null) {
            return;
        }
        Rect bounds = parent.getBounds();
        point.offset(-bounds.left, -bounds.top);
    }

    public void reassignLayer(SurfaceControl.Transaction transaction) {
        WindowContainer parent = getParent();
        if (parent != null) {
            parent.assignChildLayers(transaction);
        }
    }

    public void resetSurfacePositionForAnimationLeash(SurfaceControl.Transaction transaction) {
        transaction.setPosition(this.mSurfaceControl, DisplayPowerController2.RATE_FROM_DOZE_TO_ON, DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
        SurfaceControl.Transaction syncTransaction = getSyncTransaction();
        if (transaction != syncTransaction) {
            syncTransaction.setPosition(this.mSurfaceControl, DisplayPowerController2.RATE_FROM_DOZE_TO_ON, DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
        }
        this.mLastSurfacePosition.set(0, 0);
    }

    public void onAnimationLeashCreated(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl) {
        this.mLastLayer = -1;
        this.mAnimationLeash = surfaceControl;
        reassignLayer(transaction);
        resetSurfacePositionForAnimationLeash(transaction);
        if (CoreRune.FW_CUSTOM_BASIC_ANIM_WITH_DIM) {
            this.mDimAnimator.startDimAnimation(transaction);
        }
    }

    public void onAnimationLeashLost(SurfaceControl.Transaction transaction) {
        this.mLastLayer = -1;
        this.mWmService.mSurfaceAnimationRunner.onAnimationLeashLost(this.mAnimationLeash, transaction);
        this.mAnimationLeash = null;
        this.mNeedsZBoost = false;
        reassignLayer(transaction);
        updateSurfacePosition(transaction);
        if (CoreRune.FW_CUSTOM_BASIC_ANIM_WITH_DIM) {
            this.mDimAnimator.finishDimAnimation(0);
        }
    }

    public SurfaceControl getAnimationLeash() {
        return this.mAnimationLeash;
    }

    public final void doAnimationFinished(int i, AnimationAdapter animationAdapter) {
        for (int i2 = 0; i2 < this.mSurfaceAnimationSources.size(); i2++) {
            ((WindowContainer) this.mSurfaceAnimationSources.valueAt(i2)).onAnimationFinished(i, animationAdapter);
        }
        this.mSurfaceAnimationSources.clear();
        DisplayContent displayContent = this.mDisplayContent;
        if (displayContent != null) {
            displayContent.onWindowAnimationFinished(this, i);
        }
    }

    public void onAnimationFinished(int i, AnimationAdapter animationAdapter) {
        doAnimationFinished(i, animationAdapter);
        this.mWmService.onAnimationFinished();
        this.mNeedsZBoost = false;
    }

    public AnimationAdapter getAnimation() {
        return this.mSurfaceAnimator.getAnimation();
    }

    public WindowContainer getAnimatingContainer(int i, int i2) {
        if (isSelfAnimating(i, i2)) {
            return this;
        }
        if ((i & 2) != 0) {
            for (WindowContainer parent = getParent(); parent != null; parent = parent.getParent()) {
                if (parent.isSelfAnimating(i, i2)) {
                    return parent;
                }
            }
        }
        if ((i & 4) == 0) {
            return null;
        }
        for (int i3 = 0; i3 < this.mChildren.size(); i3++) {
            WindowContainer animatingContainer = ((WindowContainer) this.mChildren.get(i3)).getAnimatingContainer(i & (-3), i2);
            if (animatingContainer != null) {
                return animatingContainer;
            }
        }
        return null;
    }

    public boolean isSelfAnimating(int i, int i2) {
        if (!this.mSurfaceAnimator.isAnimating() || (i2 & this.mSurfaceAnimator.getAnimationType()) <= 0) {
            return (i & 1) != 0 && isWaitingForTransitionStart();
        }
        return true;
    }

    @Deprecated
    public final WindowContainer getAnimatingContainer() {
        return getAnimatingContainer(2, -1);
    }

    public void startDelayingAnimationStart() {
        this.mSurfaceAnimator.startDelayingAnimationStart();
    }

    public void endDelayingAnimationStart() {
        this.mSurfaceAnimator.endDelayingAnimationStart();
    }

    public int getSurfaceWidth() {
        return this.mSurfaceControl.getWidth();
    }

    public int getSurfaceHeight() {
        return this.mSurfaceControl.getHeight();
    }

    public void dump(PrintWriter printWriter, String str, boolean z) {
        if (this.mSurfaceAnimator.isAnimating()) {
            printWriter.print(str);
            printWriter.println("ContainerAnimator:");
            this.mSurfaceAnimator.dump(printWriter, str + "  ");
        }
        if (this.mLastOrientationSource != null && this == this.mDisplayContent) {
            printWriter.println(str + "mLastOrientationSource=" + this.mLastOrientationSource);
            printWriter.println(str + "deepestLastOrientationSource=" + getLastOrientationSource());
        }
        SparseArray sparseArray = this.mLocalInsetsSources;
        if (sparseArray != null && sparseArray.size() != 0) {
            printWriter.println(str + this.mLocalInsetsSources.size() + " LocalInsetsSources");
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append("  ");
            String sb2 = sb.toString();
            for (int i = 0; i < this.mLocalInsetsSources.size(); i++) {
                ((InsetsSource) this.mLocalInsetsSources.valueAt(i)).dump(sb2, printWriter);
            }
        }
        if (this.mUseLayoutInUdcCutout || this.mUseConfigurationInUdcCutout) {
            printWriter.print(str + "UdcCutoutInfo:");
            printWriter.print(" mUseLayoutInUdcCutout=" + this.mUseLayoutInUdcCutout);
            printWriter.print(" mUseConfigurationInUdcCutout=" + this.mUseConfigurationInUdcCutout);
            printWriter.println();
        }
    }

    public final void updateSurfacePositionNonOrganized() {
        if (isOrganized()) {
            return;
        }
        updateSurfacePosition(getSyncTransaction());
    }

    public void updateSurfacePosition(SurfaceControl.Transaction transaction) {
        if (this.mSurfaceControl == null || this.mSurfaceAnimator.hasLeash() || this.mSurfaceFreezer.hasLeash()) {
            return;
        }
        if (isClosingWhenResizing()) {
            getRelativePosition((Rect) this.mDisplayContent.mClosingChangingContainers.get(this), this.mTmpPos);
        } else {
            getRelativePosition(this.mTmpPos);
        }
        int relativeDisplayRotation = getRelativeDisplayRotation();
        if (this.mTmpPos.equals(this.mLastSurfacePosition) && relativeDisplayRotation == this.mLastDeltaRotation) {
            return;
        }
        SurfaceControl surfaceControl = this.mSurfaceControl;
        Point point = this.mTmpPos;
        transaction.setPosition(surfaceControl, point.x, point.y);
        Point point2 = this.mLastSurfacePosition;
        Point point3 = this.mTmpPos;
        point2.set(point3.x, point3.y);
        if (this.mTransitionController.isShellTransitionsEnabled() && !this.mTransitionController.useShellTransitionsRotation()) {
            if (relativeDisplayRotation != 0) {
                updateSurfaceRotation(transaction, relativeDisplayRotation, null);
                getPendingTransaction().setFixedTransformHint(this.mSurfaceControl, getWindowConfiguration().getDisplayRotation());
            } else if (relativeDisplayRotation != this.mLastDeltaRotation) {
                transaction.setMatrix(this.mSurfaceControl, 1.0f, DisplayPowerController2.RATE_FROM_DOZE_TO_ON, DisplayPowerController2.RATE_FROM_DOZE_TO_ON, 1.0f);
                getPendingTransaction().unsetFixedTransformHint(this.mSurfaceControl);
            }
        }
        this.mLastDeltaRotation = relativeDisplayRotation;
    }

    public void updateSurfaceRotation(SurfaceControl.Transaction transaction, int i, SurfaceControl surfaceControl) {
        RotationUtils.rotateSurface(transaction, this.mSurfaceControl, i);
        Point point = this.mTmpPos;
        Point point2 = this.mLastSurfacePosition;
        point.set(point2.x, point2.y);
        Rect bounds = getParent().getBounds();
        boolean z = i % 2 != 0;
        RotationUtils.rotatePoint(this.mTmpPos, i, z ? bounds.height() : bounds.width(), z ? bounds.width() : bounds.height());
        if (surfaceControl == null) {
            surfaceControl = this.mSurfaceControl;
        }
        Point point3 = this.mTmpPos;
        transaction.setPosition(surfaceControl, point3.x, point3.y);
    }

    public Point getLastSurfacePosition() {
        return this.mLastSurfacePosition;
    }

    public void getAnimationFrames(Rect rect, Rect rect2, Rect rect3, Rect rect4) {
        DisplayInfo displayInfo = getDisplayContent().getDisplayInfo();
        rect.set(0, 0, displayInfo.appWidth, displayInfo.appHeight);
        rect2.setEmpty();
        rect3.setEmpty();
        rect4.setEmpty();
    }

    public void getRelativePosition(Point point) {
        getRelativePosition(getBounds(), point);
    }

    public void getRelativePosition(Rect rect, Point point) {
        point.set(rect.left, rect.top);
        WindowContainer parent = getParent();
        if (parent != null) {
            Rect bounds = parent.getBounds();
            point.offset(-bounds.left, -bounds.top);
        }
    }

    public int getRelativeDisplayRotation() {
        WindowContainer parent = getParent();
        if (parent == null) {
            return 0;
        }
        return RotationUtils.deltaRotation(getWindowConfiguration().getDisplayRotation(), parent.getWindowConfiguration().getDisplayRotation());
    }

    public void waitForAllWindowsDrawn() {
        forAllWindows(new Consumer() { // from class: com.android.server.wm.WindowContainer$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                WindowContainer.this.lambda$waitForAllWindowsDrawn$13((WindowState) obj);
            }
        }, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$waitForAllWindowsDrawn$13(WindowState windowState) {
        windowState.requestDrawIfNeeded(this.mWaitingForDrawn);
    }

    public Dimmer getDimmer() {
        WindowContainer windowContainer = this.mParent;
        if (windowContainer == null) {
            return null;
        }
        return windowContainer.getDimmer();
    }

    public void setSurfaceControl(SurfaceControl surfaceControl) {
        this.mSurfaceControl = surfaceControl;
    }

    public boolean showWallpaper() {
        if (isVisibleRequested() && !inMultiWindowMode()) {
            for (int size = this.mChildren.size() - 1; size >= 0; size--) {
                if (((WindowContainer) this.mChildren.get(size)).showWallpaper()) {
                    return true;
                }
            }
        }
        return false;
    }

    public static WindowContainer fromBinder(IBinder iBinder) {
        return RemoteToken.fromBinder(iBinder).getContainer();
    }

    /* loaded from: classes3.dex */
    public class RemoteToken extends IWindowContainerToken.Stub {
        public final WeakReference mWeakRef;
        public WindowContainerToken mWindowContainerToken;

        public RemoteToken(WindowContainer windowContainer) {
            this.mWeakRef = new WeakReference(windowContainer);
        }

        public WindowContainer getContainer() {
            return (WindowContainer) this.mWeakRef.get();
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static RemoteToken fromBinder(IBinder iBinder) {
            return (RemoteToken) iBinder;
        }

        public WindowContainerToken toWindowContainerToken() {
            if (this.mWindowContainerToken == null) {
                this.mWindowContainerToken = new WindowContainerToken(this);
            }
            return this.mWindowContainerToken;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder(128);
            sb.append("RemoteToken{");
            sb.append(Integer.toHexString(System.identityHashCode(this)));
            sb.append(' ');
            sb.append(this.mWeakRef.get());
            sb.append('}');
            return sb.toString();
        }
    }

    public boolean onSyncFinishedDrawing() {
        if (this.mSyncState == 0) {
            return false;
        }
        this.mSyncState = 2;
        this.mSyncMethodOverride = -1;
        if (!ProtoLogCache.WM_DEBUG_SYNC_ENGINE_enabled) {
            return true;
        }
        ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_SYNC_ENGINE, -1918702467, 0, (String) null, new Object[]{String.valueOf(this)});
        return true;
    }

    public void setSyncGroup(BLASTSyncEngine.SyncGroup syncGroup) {
        if (ProtoLogCache.WM_DEBUG_SYNC_ENGINE_enabled) {
            ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_SYNC_ENGINE, 959486822, 1, (String) null, new Object[]{Long.valueOf(syncGroup.mSyncId), String.valueOf(this)});
        }
        BLASTSyncEngine.SyncGroup syncGroup2 = this.mSyncGroup;
        if (syncGroup2 != null && syncGroup2 != syncGroup) {
            throw new IllegalStateException("Can't sync on 2 groups simultaneously currentSyncId=" + this.mSyncGroup.mSyncId + " newSyncId=" + syncGroup.mSyncId + " wc=" + this);
        }
        this.mSyncGroup = syncGroup;
    }

    public BLASTSyncEngine.SyncGroup getSyncGroup() {
        BLASTSyncEngine.SyncGroup syncGroup = this.mSyncGroup;
        if (syncGroup != null) {
            return syncGroup;
        }
        for (WindowContainer windowContainer = this.mParent; windowContainer != null; windowContainer = windowContainer.mParent) {
            BLASTSyncEngine.SyncGroup syncGroup2 = windowContainer.mSyncGroup;
            if (syncGroup2 != null) {
                return syncGroup2;
            }
        }
        return null;
    }

    public boolean prepareSync() {
        if (this.mSyncState != 0) {
            return false;
        }
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            getChildAt(childCount).prepareSync();
        }
        this.mSyncState = 2;
        return true;
    }

    public boolean useBLASTSync() {
        return this.mSyncState != 0;
    }

    public void finishSync(SurfaceControl.Transaction transaction, BLASTSyncEngine.SyncGroup syncGroup, boolean z) {
        BLASTSyncEngine.SyncGroup syncGroup2;
        if (this.mSyncState == 0) {
            return;
        }
        BLASTSyncEngine.SyncGroup syncGroup3 = getSyncGroup();
        if (syncGroup3 == null || syncGroup == syncGroup3) {
            if (ProtoLogCache.WM_DEBUG_SYNC_ENGINE_enabled) {
                ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_SYNC_ENGINE, 371173718, 3, (String) null, new Object[]{Boolean.valueOf(z), String.valueOf(this)});
            }
            transaction.merge(this.mSyncTransaction);
            for (int size = this.mChildren.size() - 1; size >= 0; size--) {
                ((WindowContainer) this.mChildren.get(size)).finishSync(transaction, syncGroup, z);
            }
            if (z && (syncGroup2 = this.mSyncGroup) != null) {
                syncGroup2.onCancelSync(this);
            }
            this.mSyncState = 0;
            this.mSyncMethodOverride = -1;
            this.mSyncGroup = null;
            this.mSyncIdForReparentSurfaceControl = -1;
        }
    }

    public boolean isSyncFinished(BLASTSyncEngine.SyncGroup syncGroup) {
        if (!isVisibleRequested()) {
            return true;
        }
        if (this.mSyncState == 0 && getSyncGroup() != null) {
            Slog.i("WindowManager", "prepareSync in isSyncFinished: " + this);
            prepareSync();
        }
        if (this.mSyncState == 1) {
            return false;
        }
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            WindowContainer windowContainer = (WindowContainer) this.mChildren.get(size);
            boolean z = syncGroup.isIgnoring(windowContainer) || windowContainer.isSyncFinished(syncGroup);
            if (z && windowContainer.isVisibleRequested() && windowContainer.fillsParent()) {
                return true;
            }
            if (!z) {
                return false;
            }
        }
        return true;
    }

    public boolean allSyncFinished() {
        if (!isVisibleRequested()) {
            return true;
        }
        if (this.mSyncState != 2) {
            return false;
        }
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            if (!((WindowContainer) this.mChildren.get(size)).allSyncFinished()) {
                return false;
            }
        }
        return true;
    }

    public final void onSyncReparent(WindowContainer windowContainer, WindowContainer windowContainer2) {
        if (this.mSyncState != 0 && windowContainer != null && windowContainer2 != null && windowContainer.getDisplayContent() != null && windowContainer2.getDisplayContent() != null && windowContainer.getDisplayContent() != windowContainer2.getDisplayContent()) {
            this.mTransitionController.setReady(windowContainer.getDisplayContent());
        }
        if (windowContainer2 == null || windowContainer2.mSyncState == 0) {
            if (this.mSyncState == 0) {
                return;
            }
            if (windowContainer2 == null) {
                BLASTSyncEngine.SyncGroup syncGroup = getSyncGroup();
                if (windowContainer.mSyncState != 0) {
                    finishSync(windowContainer.mSyncTransaction, syncGroup, true);
                    return;
                }
                if (syncGroup != null) {
                    finishSync(syncGroup.getOrphanTransaction(), syncGroup, true);
                    return;
                }
                Slog.wtf("WindowManager", this + " is in sync mode without a sync group");
                finishSync(getPendingTransaction(), null, true);
                return;
            }
            if (this.mSyncGroup == null) {
                finishSync(getPendingTransaction(), getSyncGroup(), true);
                return;
            }
        }
        if (windowContainer == null || windowContainer2 == null || shouldUpdateSyncOnReparent()) {
            if (this.mTransitionController.isShellTransitionsEnabled()) {
                this.mSyncState = 0;
                this.mSyncMethodOverride = -1;
            }
            prepareSync();
        }
    }

    public void registerWindowContainerListener(WindowContainerListener windowContainerListener) {
        registerWindowContainerListener(windowContainerListener, true);
    }

    public void registerWindowContainerListener(WindowContainerListener windowContainerListener, boolean z) {
        if (this.mListeners.contains(windowContainerListener)) {
            return;
        }
        this.mListeners.add(windowContainerListener);
        registerConfigurationChangeListener(windowContainerListener, z);
        if (z) {
            windowContainerListener.onDisplayChanged(getDisplayContent());
        }
    }

    public void unregisterWindowContainerListener(WindowContainerListener windowContainerListener) {
        this.mListeners.remove(windowContainerListener);
        unregisterConfigurationChangeListener(windowContainerListener);
    }

    public static void overrideConfigurationPropagation(WindowContainer windowContainer, WindowContainer windowContainer2) {
        overrideConfigurationPropagation(windowContainer, windowContainer2, null);
    }

    public static WindowContainerListener overrideConfigurationPropagation(final WindowContainer windowContainer, final WindowContainer windowContainer2, final ConfigurationMerger configurationMerger) {
        final ConfigurationContainerListener configurationContainerListener = new ConfigurationContainerListener() { // from class: com.android.server.wm.WindowContainer.1
            @Override // com.android.server.wm.ConfigurationContainerListener
            public void onMergedOverrideConfigurationChanged(Configuration configuration) {
                Configuration configuration2;
                ConfigurationMerger configurationMerger2 = ConfigurationMerger.this;
                if (configurationMerger2 != null) {
                    configuration2 = configurationMerger2.merge(configuration, windowContainer.getRequestedOverrideConfiguration());
                } else {
                    configuration2 = windowContainer2.getConfiguration();
                }
                windowContainer.onRequestedOverrideConfigurationChanged(configuration2);
            }
        };
        windowContainer2.registerConfigurationChangeListener(configurationContainerListener);
        WindowContainerListener windowContainerListener = new WindowContainerListener() { // from class: com.android.server.wm.WindowContainer.2
            @Override // com.android.server.wm.WindowContainerListener
            public void onRemoved() {
                WindowContainer.this.unregisterWindowContainerListener(this);
                windowContainer2.unregisterConfigurationChangeListener(configurationContainerListener);
            }
        };
        windowContainer.registerWindowContainerListener(windowContainerListener);
        return windowContainerListener;
    }

    public boolean setCanScreenshot(SurfaceControl.Transaction transaction, boolean z) {
        SurfaceControl surfaceControl = this.mSurfaceControl;
        if (surfaceControl == null) {
            return false;
        }
        transaction.setSecure(surfaceControl, !z);
        return true;
    }

    /* loaded from: classes3.dex */
    public class AnimationRunnerBuilder {
        public final List mOnAnimationCancelled;
        public final List mOnAnimationFinished;

        public AnimationRunnerBuilder() {
            this.mOnAnimationFinished = new LinkedList();
            this.mOnAnimationCancelled = new LinkedList();
        }

        public final void setTaskBackgroundColor(int i) {
            final TaskDisplayArea taskDisplayArea = WindowContainer.this.getTaskDisplayArea();
            if (taskDisplayArea == null || i == 0) {
                return;
            }
            taskDisplayArea.setBackgroundColor(i);
            final AtomicInteger atomicInteger = new AtomicInteger(0);
            Runnable runnable = new Runnable() { // from class: com.android.server.wm.WindowContainer$AnimationRunnerBuilder$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    WindowContainer.AnimationRunnerBuilder.lambda$setTaskBackgroundColor$0(atomicInteger, taskDisplayArea);
                }
            };
            this.mOnAnimationFinished.add(runnable);
            this.mOnAnimationCancelled.add(runnable);
        }

        public static /* synthetic */ void lambda$setTaskBackgroundColor$0(AtomicInteger atomicInteger, TaskDisplayArea taskDisplayArea) {
            if (atomicInteger.getAndIncrement() == 0) {
                taskDisplayArea.clearBackgroundColor();
            }
        }

        public final void hideInsetSourceViewOverflows() {
            SparseArray sourceProviders = WindowContainer.this.getDisplayContent().getInsetsStateController().getSourceProviders();
            for (int size = sourceProviders.size(); size >= 0; size--) {
                final InsetsSourceProvider insetsSourceProvider = (InsetsSourceProvider) sourceProviders.valueAt(size);
                if (!insetsSourceProvider.getSource().insetsRoundedCornerFrame()) {
                    return;
                }
                insetsSourceProvider.setCropToProvidingInsetsBounds(WindowContainer.this.getPendingTransaction());
                this.mOnAnimationFinished.add(new Runnable() { // from class: com.android.server.wm.WindowContainer$AnimationRunnerBuilder$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        WindowContainer.AnimationRunnerBuilder.this.lambda$hideInsetSourceViewOverflows$1(insetsSourceProvider);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$hideInsetSourceViewOverflows$1(InsetsSourceProvider insetsSourceProvider) {
            insetsSourceProvider.removeCropToProvidingInsetsBounds(WindowContainer.this.getPendingTransaction());
        }

        public final IAnimationStarter build() {
            return new IAnimationStarter() { // from class: com.android.server.wm.WindowContainer$AnimationRunnerBuilder$$ExternalSyntheticLambda1
                @Override // com.android.server.wm.WindowContainer.IAnimationStarter
                public final void startAnimation(SurfaceControl.Transaction transaction, AnimationAdapter animationAdapter, boolean z, int i, AnimationAdapter animationAdapter2) {
                    WindowContainer.AnimationRunnerBuilder.this.lambda$build$4(transaction, animationAdapter, z, i, animationAdapter2);
                }
            };
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$build$4(SurfaceControl.Transaction transaction, AnimationAdapter animationAdapter, boolean z, int i, AnimationAdapter animationAdapter2) {
            WindowContainer windowContainer = WindowContainer.this;
            windowContainer.startAnimation(windowContainer.getPendingTransaction(), animationAdapter, !WindowContainer.this.isVisible(), i, new SurfaceAnimator.OnAnimationFinishedCallback() { // from class: com.android.server.wm.WindowContainer$AnimationRunnerBuilder$$ExternalSyntheticLambda3
                @Override // com.android.server.wm.SurfaceAnimator.OnAnimationFinishedCallback
                public final void onAnimationFinished(int i2, AnimationAdapter animationAdapter3) {
                    WindowContainer.AnimationRunnerBuilder.this.lambda$build$2(i2, animationAdapter3);
                }
            }, new Runnable() { // from class: com.android.server.wm.WindowContainer$AnimationRunnerBuilder$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    WindowContainer.AnimationRunnerBuilder.this.lambda$build$3();
                }
            }, animationAdapter2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$build$2(int i, AnimationAdapter animationAdapter) {
            this.mOnAnimationFinished.forEach(new DisplayManagerService$$ExternalSyntheticLambda13());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$build$3() {
            this.mOnAnimationCancelled.forEach(new DisplayManagerService$$ExternalSyntheticLambda13());
        }
    }

    public void addTrustedOverlay(SurfaceControlViewHost.SurfacePackage surfacePackage, WindowState windowState) {
        if (this.mOverlayHost == null) {
            this.mOverlayHost = new TrustedOverlayHost(this.mWmService);
        }
        this.mOverlayHost.addOverlay(surfacePackage, this.mSurfaceControl);
        try {
            surfacePackage.getRemoteInterface().onConfigurationChanged(getConfiguration());
        } catch (Exception unused) {
            if (ProtoLogCache.WM_DEBUG_ANIM_enabled) {
                ProtoLogImpl.e(ProtoLogGroup.WM_DEBUG_ANIM, -32102932, 0, (String) null, (Object[]) null);
            }
            removeTrustedOverlay(surfacePackage);
        }
        if (windowState != null) {
            try {
                surfacePackage.getRemoteInterface().onInsetsChanged(windowState.getInsetsState(), getBounds());
            } catch (Exception unused2) {
                if (ProtoLogCache.WM_DEBUG_ANIM_enabled) {
                    ProtoLogImpl.e(ProtoLogGroup.WM_DEBUG_ANIM, 1288920916, 0, (String) null, (Object[]) null);
                }
                removeTrustedOverlay(surfacePackage);
            }
        }
    }

    public void removeTrustedOverlay(SurfaceControlViewHost.SurfacePackage surfacePackage) {
        TrustedOverlayHost trustedOverlayHost = this.mOverlayHost;
        if (trustedOverlayHost == null || trustedOverlayHost.removeOverlay(surfacePackage)) {
            return;
        }
        this.mOverlayHost.release();
        this.mOverlayHost = null;
    }

    public void updateOverlayInsetsState(WindowState windowState) {
        WindowContainer parent = getParent();
        if (parent != null) {
            parent.updateOverlayInsetsState(windowState);
        }
    }

    public void waitForSyncTransactionCommit(ArraySet arraySet) {
        if (arraySet.contains(this)) {
            return;
        }
        this.mSyncTransactionCommitCallbackDepth++;
        arraySet.add(this);
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            ((WindowContainer) this.mChildren.get(size)).waitForSyncTransactionCommit(arraySet);
        }
    }

    public void onSyncTransactionCommitted(SurfaceControl.Transaction transaction) {
        int i = this.mSyncTransactionCommitCallbackDepth - 1;
        this.mSyncTransactionCommitCallbackDepth = i;
        if (i <= 0 && this.mSyncState == 0) {
            transaction.merge(this.mSyncTransaction);
        }
    }

    public boolean hasRelativeLayer() {
        WindowContainer windowContainer;
        return this.mLastRelativeToLayer != null || ((windowContainer = this.mParent) != null && windowContainer.hasRelativeLayer());
    }

    public final boolean inUdcCutoutMode() {
        UdcCutoutPolicy udcCutoutPolicy;
        DisplayContent displayContent = this.mDisplayContent;
        return (displayContent == null || (udcCutoutPolicy = displayContent.mUdcCutoutPolicy) == null || !udcCutoutPolicy.hasUdcCutout() || inMultiWindowMode()) ? false : true;
    }

    public boolean isLayoutNeededInUdcCutout() {
        return this.mUseLayoutInUdcCutout && inUdcCutoutMode();
    }

    public boolean isConfigurationNeededInUdcCutout() {
        return this.mUseConfigurationInUdcCutout && inUdcCutoutMode();
    }

    @Override // com.android.server.wm.ConfigurationContainer
    public void resolveOverrideConfiguration(Configuration configuration) {
        super.resolveOverrideConfiguration(configuration);
        if (!isConfigurationNeededInUdcCutout() || this.mDisplayContent.mUdcCutoutPolicy.mUdcConfiguration == null) {
            return;
        }
        getResolvedOverrideConfiguration().updateFrom(this.mDisplayContent.mUdcCutoutPolicy.mUdcConfiguration);
    }

    public boolean mergeAnimationIfNeeded(int i, RemoteAnimationController remoteAnimationController) {
        if (AppTransition.isKeyguardTransit(i) || this.mSurfaceAnimator.getAnimation() == null || !(this.mSurfaceAnimator.getAnimation() instanceof RemoteAnimationController.RemoteAnimationAdapterWrapper)) {
            return false;
        }
        final RemoteAnimationAdapter remoteAnimationAdapter = ((RemoteAnimationController.RemoteAnimationAdapterWrapper) this.mSurfaceAnimator.getAnimation()).mOuterRemoteAnimationAdapter;
        RemoteAnimationAdapter remoteAnimationAdapter2 = remoteAnimationController.mRemoteAnimationAdapter;
        Slog.d("WindowManager", "mergeAnimationIfNeeded, try not to use new remote anim, oldAnimPid=" + remoteAnimationAdapter.getCallingPid() + ", newAnimPid=" + remoteAnimationAdapter2.getCallingPid() + ", oldMergeCallback=" + remoteAnimationAdapter.getMergeCallback() + ", newMergeCallback=" + remoteAnimationAdapter2.getMergeCallback());
        if (remoteAnimationAdapter.getCallingPid() != remoteAnimationAdapter2.getCallingPid() || remoteAnimationAdapter.getMergeCallback() == null || remoteAnimationAdapter2.getMergeCallback() != null || remoteAnimationAdapter.mMergedState < 1) {
            return false;
        }
        Slog.d("WindowManager", "mergeAnimationIfNeeded, not to use new remote anim, container=" + this);
        if (remoteAnimationAdapter.mMergedState != 2) {
            remoteAnimationAdapter.mMergedState = 2;
            this.mWmService.mAnimator.addAfterPrepareSurfacesRunnable(new Runnable() { // from class: com.android.server.wm.WindowContainer$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    WindowContainer.lambda$mergeAnimationIfNeeded$14(remoteAnimationAdapter);
                }
            });
        }
        return true;
    }

    public static /* synthetic */ void lambda$mergeAnimationIfNeeded$14(RemoteAnimationAdapter remoteAnimationAdapter) {
        try {
            Slog.d("WindowManager", "onAnimationMerged is called");
            remoteAnimationAdapter.getMergeCallback().onAnimationMerged();
        } catch (RemoteException e) {
            Slog.e("WindowManager", "Failed to merge remote animation", e);
        }
    }

    public int getRequestedConfigurationOrientationInChildren() {
        int overrideOrientation = getOverrideOrientation();
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            WindowContainer windowContainer = (WindowContainer) this.mChildren.get(size);
            int orientation = windowContainer.getOrientation(overrideOrientation == 3 ? 3 : -2);
            if (orientation == 3) {
                overrideOrientation = orientation;
            } else if (orientation != -2 && (windowContainer.providesOrientation() || orientation != -1)) {
                overrideOrientation = orientation;
                break;
            }
        }
        return getRequestedConfigurationOrientation(false, overrideOrientation);
    }

    public int getCaptionHeight() {
        return getCaptionHeight(false, -1);
    }

    public int getCaptionHeight(boolean z) {
        return getCaptionHeight(z, -1);
    }

    public int getCaptionHeight(boolean z, int i) {
        DisplayContent displayContent = this.mDisplayContent;
        if (displayContent == null || displayContent.getDisplayPolicy() == null) {
            return 0;
        }
        return this.mWmService.mAtmService.mFreeformController.getDecorCaptionHeight(this.mDisplayContent.getDisplayPolicy().getCurrentUserResources(), this, z, i);
    }

    public int getFreeformThickness() {
        DisplayContent displayContent = this.mDisplayContent;
        if (displayContent == null || displayContent.getDisplayPolicy() == null) {
            return 0;
        }
        return this.mWmService.mAtmService.mFreeformController.getFreeformThickness(this.mDisplayContent.getDisplayPolicy().getCurrentUserResources());
    }

    public int getFreeformTopInset() {
        if (CoreRune.MW_CAPTION_SHELL) {
            if (CoreRune.MW_CAPTION_SHELL_DEX && isDexMode()) {
                return getCaptionHeight(true);
            }
            return getFreeformThickness() + getCaptionHeight(true);
        }
        return getFreeformThickness();
    }

    public boolean shouldSkipTransition() {
        return this.mWmService.mExt.mExtraDisplayPolicy.shouldSkipAppTransition(this.mDisplayContent.getDisplayId());
    }
}
