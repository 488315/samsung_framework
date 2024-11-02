package com.android.systemui.volume.view.subscreen.full;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.Window;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.dynamicanimation.animation.SpringAnimation;
import com.android.keyguard.SecLockIconView$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.volume.VolumeDependencyBase;
import com.android.systemui.volume.store.StoreInteractor;
import com.android.systemui.volume.store.VolumePanelStore;
import com.android.systemui.volume.util.BlurEffect;
import com.android.systemui.volume.util.ContextUtils;
import com.android.systemui.volume.util.HandlerWrapper;
import com.android.systemui.volume.util.IDisplayManagerWrapper;
import com.android.systemui.volume.util.PluginAODManagerWrapper;
import com.android.systemui.volume.util.PowerManagerWrapper;
import com.android.systemui.volume.util.VibratorWrapper;
import com.android.systemui.volume.util.ViewUtil;
import com.android.systemui.volume.util.ViewVisibilityUtil;
import com.android.systemui.volume.view.VolumePanelViewExt;
import com.android.systemui.volume.view.context.ViewContext;
import com.android.systemui.volume.view.expand.VolumePanelExpandView$adjustTouchEventForOutsideTouch$1$$ExternalSyntheticOutline0;
import com.android.systemui.volume.view.warnings.WarningDialogController;
import com.samsung.systemui.splugins.extensions.VolumePanelRowExt;
import com.samsung.systemui.splugins.extensions.VolumePanelStateExt;
import com.samsung.systemui.splugins.volume.VolumeObserver;
import com.samsung.systemui.splugins.volume.VolumePanelAction;
import com.samsung.systemui.splugins.volume.VolumePanelRow;
import com.samsung.systemui.splugins.volume.VolumePanelState;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.collections.ReversedListReadOnly;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt__MathJVMKt;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class SubFullLayoutVolumePanelView extends FrameLayout implements VolumeObserver<VolumePanelState>, ViewContext {
    public int activeStream;
    public BlurEffect blurEffect;
    public int currentVolume;
    public Dialog dialog;
    public float downX;
    public float downY;
    public TextView dualViewTitle;
    public ImageView expandButton;
    public HandlerWrapper handlerWrapper;
    public IDisplayManagerWrapper iDisplayManagerWrapper;
    public boolean isDragging;
    public boolean isDualViewEnabled;
    public boolean isFirstTouch;
    public boolean isKeyDownAnimating;
    public boolean isLockscreen;
    public boolean isSeekBarTouching;
    public boolean isSwipe;
    public boolean isTouchDown;
    public SpringAnimation keyDownAnimation;
    public SpringAnimation keyUpAnimation;
    public final SubFullLayoutVolumePanelView$keyUpRunnable$1 keyUpRunnable;
    public VolumePanelState panelState;
    public PluginAODManagerWrapper pluginAODManagerWrapper;
    public PowerManagerWrapper powerManagerWrapper;
    public ViewGroup rowContainer;
    public boolean startProgress;
    public VolumePanelStore store;
    public final StoreInteractor storeInteractor;
    public float swipeDistance;
    public SpringAnimation touchDownAnimation;
    public boolean touchDownExpandButton;
    public SpringAnimation touchUpAnimation;
    public VibratorWrapper vibratorWrapper;
    public VolumeDependencyBase volDeps;
    public ViewGroup volumeAODPanelView;
    public ViewGroup volumePanelDualView;
    public SubFullLayoutVolumePanelMotion volumePanelMotion;
    public ViewGroup volumePanelView;
    public final Lazy warningDialogController$delegate;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[VolumePanelState.StateType.values().length];
            try {
                iArr[VolumePanelState.StateType.STATE_SEEKBAR_START_PROGRESS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_SEEKBAR_TOUCH_DOWN.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_SEEKBAR_TOUCH_UP.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_UPDATE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_SHOW_VOLUME_SAFETY_WARNING_DIALOG.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_SHOW_VOLUME_LIMITER_DIALOG.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_SHOW_VOLUME_CSD_100_WARNING_DIALOG.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_UPDATE_PROGRESS_BAR.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_KEY_EVENT.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumePanelView$keyUpRunnable$1] */
    public SubFullLayoutVolumePanelView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.storeInteractor = new StoreInteractor(this, null);
        this.keyUpRunnable = new Runnable() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumePanelView$keyUpRunnable$1
            @Override // java.lang.Runnable
            public final void run() {
                SubFullLayoutVolumePanelView subFullLayoutVolumePanelView = SubFullLayoutVolumePanelView.this;
                boolean z = false;
                subFullLayoutVolumePanelView.isKeyDownAnimating = false;
                SubFullLayoutVolumePanelMotion subFullLayoutVolumePanelMotion = subFullLayoutVolumePanelView.volumePanelMotion;
                SpringAnimation springAnimation = null;
                if (subFullLayoutVolumePanelMotion == null) {
                    subFullLayoutVolumePanelMotion = null;
                }
                SpringAnimation springAnimation2 = subFullLayoutVolumePanelView.keyUpAnimation;
                if (springAnimation2 == null) {
                    springAnimation2 = null;
                }
                SpringAnimation springAnimation3 = subFullLayoutVolumePanelView.keyDownAnimation;
                if (springAnimation3 == null) {
                    springAnimation3 = null;
                }
                subFullLayoutVolumePanelMotion.getClass();
                if (springAnimation3 != null) {
                    if (springAnimation3.mRunning && springAnimation3.canSkipToEnd()) {
                        z = true;
                    }
                    if (z) {
                        springAnimation = springAnimation3;
                    }
                    if (springAnimation != null) {
                        springAnimation.skipToEnd();
                    }
                }
                springAnimation2.animateToFinalPosition(1.0f);
            }
        };
        this.warningDialogController$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumePanelView$warningDialogController$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return new WarningDialogController(SubFullLayoutVolumePanelView.this);
            }
        });
    }

    public final void addVolumeRows(VolumePanelState volumePanelState) {
        HandlerWrapper handlerWrapper;
        SubFullLayoutVolumePanelMotion subFullLayoutVolumePanelMotion;
        ViewGroup viewGroup = this.rowContainer;
        if (viewGroup == null) {
            viewGroup = null;
        }
        viewGroup.removeAllViews();
        List<VolumePanelRow> volumeRowList = volumePanelState.getVolumeRowList();
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = volumeRowList.iterator();
        while (true) {
            boolean z = false;
            if (!it.hasNext()) {
                break;
            }
            Object next = it.next();
            VolumePanelRow volumePanelRow = (VolumePanelRow) next;
            if (VolumePanelStateExt.isActiveStream(volumePanelState, volumePanelRow.getStreamType()) || volumePanelRow.isVisible()) {
                z = true;
            }
            if (z) {
                arrayList.add(next);
            }
        }
        ReversedListReadOnly<VolumePanelRow> reversedListReadOnly = new ReversedListReadOnly(arrayList);
        Log.d("SubFullLayoutVolumePanelView", "addRows: rows=" + VolumePanelRowExt.listToString(reversedListReadOnly));
        for (VolumePanelRow volumePanelRow2 : reversedListReadOnly) {
            SubFullLayoutVolumeRowView subFullLayoutVolumeRowView = (SubFullLayoutVolumeRowView) LayoutInflater.from(getContext()).inflate(R.layout.sub_full_volume_row_view, (ViewGroup) null);
            VolumePanelStore store = getStore();
            HandlerWrapper handlerWrapper2 = this.handlerWrapper;
            if (handlerWrapper2 == null) {
                handlerWrapper = null;
            } else {
                handlerWrapper = handlerWrapper2;
            }
            SubFullLayoutVolumePanelMotion subFullLayoutVolumePanelMotion2 = this.volumePanelMotion;
            if (subFullLayoutVolumePanelMotion2 == null) {
                subFullLayoutVolumePanelMotion = null;
            } else {
                subFullLayoutVolumePanelMotion = subFullLayoutVolumePanelMotion2;
            }
            subFullLayoutVolumeRowView.initialize(store, handlerWrapper, volumePanelRow2, volumePanelState, subFullLayoutVolumePanelMotion, false);
            ViewGroup viewGroup2 = this.rowContainer;
            if (viewGroup2 == null) {
                viewGroup2 = null;
            }
            viewGroup2.addView(subFullLayoutVolumeRowView);
            if (VolumePanelStateExt.isActiveStream(volumePanelState, volumePanelRow2.getStreamType())) {
                this.activeStream = volumePanelRow2.getStreamType();
            }
            if (!this.isDualViewEnabled) {
                ViewVisibilityUtil viewVisibilityUtil = ViewVisibilityUtil.INSTANCE;
                View findViewById = subFullLayoutVolumeRowView.findViewById(R.id.volume_seekbar_outline_stroke);
                viewVisibilityUtil.getClass();
                findViewById.setVisibility(0);
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
        boolean z;
        VolumePanelAction.ActionType actionType;
        this.storeInteractor.sendAction(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_TOUCH_PANEL).isFromOutside(true).build(), false);
        int action = motionEvent.getAction();
        View view = null;
        if (action != 0) {
            if (action != 1) {
                if (action != 2) {
                    if (action != 3) {
                        if (action == 4) {
                            if (VolumePanelStateExt.isAODVolumePanel(getPanelState())) {
                                this.isTouchDown = false;
                                this.isDragging = false;
                            } else {
                                VolumePanelExpandView$adjustTouchEventForOutsideTouch$1$$ExternalSyntheticOutline0.m(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_TOUCH_OUTSIDE), true, this.storeInteractor, false);
                                this.touchDownExpandButton = false;
                            }
                            this.startProgress = false;
                            return true;
                        }
                    }
                } else {
                    float x = motionEvent.getX();
                    float f = x - this.downX;
                    if (VolumePanelStateExt.isAODVolumePanel(getPanelState())) {
                        float y = this.downY - motionEvent.getY();
                        if (this.isDragging) {
                            ViewUtil viewUtil = ViewUtil.INSTANCE;
                            ViewGroup viewGroup = this.rowContainer;
                            if (viewGroup != null) {
                                view = viewGroup;
                            }
                            float rawX = motionEvent.getRawX();
                            float rawY = motionEvent.getRawY();
                            viewUtil.getClass();
                            if (!ViewUtil.isTouched(view, rawX, rawY)) {
                                float f2 = 1500.0f;
                                float f3 = ((y / 300.0f) * 1500.0f) + this.currentVolume;
                                if (f3 < 0.0f) {
                                    f3 = 0.0f;
                                }
                                if (f3 <= 1500.0f) {
                                    f2 = f3;
                                }
                                int roundToInt = MathKt__MathJVMKt.roundToInt(f2);
                                if (this.isDragging) {
                                    this.storeInteractor.sendAction(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_UPDATE_PROGRESS_BAR).stream(this.activeStream).progress(roundToInt).isFromOutside(true).build(), false);
                                }
                            }
                        }
                    } else if (this.isTouchDown && Math.abs(f) > this.swipeDistance && !this.startProgress && !this.isLockscreen) {
                        this.isTouchDown = false;
                        this.isSwipe = true;
                        if (!BasicRune.VOLUME_LEFT_DISPLAY_VOLUME_DIALOG ? x > this.downX : x < this.downX) {
                            z = true;
                        } else {
                            z = false;
                        }
                        StoreInteractor storeInteractor = this.storeInteractor;
                        if (z) {
                            actionType = VolumePanelAction.ActionType.ACTION_SWIPE_COLLAPSED;
                        } else {
                            actionType = VolumePanelAction.ActionType.ACTION_SWIPE_PANEL;
                        }
                        VolumePanelExpandView$adjustTouchEventForOutsideTouch$1$$ExternalSyntheticOutline0.m(new VolumePanelAction.Builder(actionType), true, storeInteractor, false);
                    }
                }
            }
            if (!VolumePanelViewExt.isIconClickWillConsume(this, motionEvent)) {
                boolean z2 = this.startProgress;
                if (!z2 && this.touchDownExpandButton) {
                    ViewUtil viewUtil2 = ViewUtil.INSTANCE;
                    ImageView imageView = this.expandButton;
                    if (imageView != null) {
                        view = imageView;
                    }
                    float rawX2 = motionEvent.getRawX();
                    float rawY2 = motionEvent.getRawY();
                    viewUtil2.getClass();
                    if (ViewUtil.isTouched(view, rawX2, rawY2)) {
                        VolumePanelExpandView$adjustTouchEventForOutsideTouch$1$$ExternalSyntheticOutline0.m(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_EXPAND_BUTTON_CLICKED), true, this.storeInteractor, false);
                    }
                } else if (!this.isSwipe && !this.isSeekBarTouching && !z2 && !this.isDualViewEnabled && !VolumePanelStateExt.isAODVolumePanel(getPanelState())) {
                    VolumePanelExpandView$adjustTouchEventForOutsideTouch$1$$ExternalSyntheticOutline0.m(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_TOUCH_OUTSIDE), true, this.storeInteractor, false);
                    return true;
                }
                this.isDragging = false;
                this.isFirstTouch = false;
                this.touchDownExpandButton = false;
                this.startProgress = false;
                this.isSeekBarTouching = false;
                if (this.isSwipe) {
                    this.isSwipe = false;
                    return true;
                }
            }
        } else {
            if (VolumePanelStateExt.isAODVolumePanel(getPanelState())) {
                if (!this.isFirstTouch) {
                    ViewUtil viewUtil3 = ViewUtil.INSTANCE;
                    ViewGroup viewGroup2 = this.rowContainer;
                    if (viewGroup2 == null) {
                        viewGroup2 = null;
                    }
                    float rawX3 = motionEvent.getRawX();
                    float rawY3 = motionEvent.getRawY();
                    viewUtil3.getClass();
                    if (!ViewUtil.isTouched(viewGroup2, rawX3, rawY3)) {
                        VolumePanelExpandView$adjustTouchEventForOutsideTouch$1$$ExternalSyntheticOutline0.m(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_TOUCH_OUTSIDE), true, this.storeInteractor, false);
                        this.touchDownExpandButton = false;
                        this.startProgress = false;
                        return true;
                    }
                }
                this.isDragging = true;
            }
            if (!this.isLockscreen) {
                ViewUtil viewUtil4 = ViewUtil.INSTANCE;
                ImageView imageView2 = this.expandButton;
                if (imageView2 != null) {
                    view = imageView2;
                }
                float rawX4 = motionEvent.getRawX();
                float rawY4 = motionEvent.getRawY();
                viewUtil4.getClass();
                if (ViewUtil.isTouched(view, rawX4, rawY4)) {
                    this.touchDownExpandButton = true;
                }
            }
            this.downY = motionEvent.getY();
            this.downX = motionEvent.getX();
            this.isSwipe = false;
            this.isTouchDown = true;
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    @Override // com.android.systemui.volume.view.context.ViewContext
    public final VolumePanelState getPanelState() {
        VolumePanelState volumePanelState = this.panelState;
        if (volumePanelState != null) {
            return volumePanelState;
        }
        return null;
    }

    @Override // com.android.systemui.volume.view.context.ViewContext
    public final VolumePanelStore getStore() {
        VolumePanelStore volumePanelStore = this.store;
        if (volumePanelStore != null) {
            return volumePanelStore;
        }
        return null;
    }

    @Override // com.android.systemui.volume.view.context.ViewContext
    public final VolumeDependencyBase getVolDeps() {
        VolumeDependencyBase volumeDependencyBase = this.volDeps;
        if (volumeDependencyBase != null) {
            return volumeDependencyBase;
        }
        return null;
    }

    public final void initViewVisibility(VolumePanelState volumePanelState) {
        ImageView imageView = null;
        if (VolumePanelStateExt.isAODVolumePanel(volumePanelState)) {
            ViewVisibilityUtil viewVisibilityUtil = ViewVisibilityUtil.INSTANCE;
            ViewGroup viewGroup = this.volumePanelView;
            if (viewGroup == null) {
                viewGroup = null;
            }
            viewVisibilityUtil.getClass();
            viewGroup.setVisibility(0);
            ViewGroup viewGroup2 = this.volumePanelDualView;
            if (viewGroup2 == null) {
                viewGroup2 = null;
            }
            ViewVisibilityUtil.setGone(viewGroup2);
            ViewGroup viewGroup3 = this.volumeAODPanelView;
            if (viewGroup3 == null) {
                viewGroup3 = null;
            }
            viewGroup3.setVisibility(0);
        } else if (this.isDualViewEnabled) {
            ViewVisibilityUtil viewVisibilityUtil2 = ViewVisibilityUtil.INSTANCE;
            ViewGroup viewGroup4 = this.volumePanelView;
            if (viewGroup4 == null) {
                viewGroup4 = null;
            }
            viewVisibilityUtil2.getClass();
            ViewVisibilityUtil.setGone(viewGroup4);
            ViewGroup viewGroup5 = this.volumePanelDualView;
            if (viewGroup5 == null) {
                viewGroup5 = null;
            }
            viewGroup5.setVisibility(0);
            ViewGroup viewGroup6 = this.volumeAODPanelView;
            if (viewGroup6 == null) {
                viewGroup6 = null;
            }
            ViewVisibilityUtil.setGone(viewGroup6);
            if (volumePanelState.isLockscreen()) {
                TextView textView = this.dualViewTitle;
                if (textView == null) {
                    textView = null;
                }
                textView.setVisibility(0);
            } else {
                TextView textView2 = this.dualViewTitle;
                if (textView2 == null) {
                    textView2 = null;
                }
                ViewVisibilityUtil.setGone(textView2);
            }
            ViewGroup viewGroup7 = this.volumePanelDualView;
            if (viewGroup7 == null) {
                viewGroup7 = null;
            }
            ViewGroup viewGroup8 = (ViewGroup) viewGroup7.findViewById(R.id.volume_panel_expand_view_background_stroke);
            if (((SettingsHelper) Dependency.get(SettingsHelper.class)).isReduceTransparencyEnabled()) {
                ViewVisibilityUtil.setGone(viewGroup8);
            } else if (ContextUtils.isNightMode(getContext())) {
                ViewVisibilityUtil.setGone(viewGroup8);
            } else {
                viewGroup8.setVisibility(0);
            }
        } else {
            ViewVisibilityUtil viewVisibilityUtil3 = ViewVisibilityUtil.INSTANCE;
            ViewGroup viewGroup9 = this.volumePanelView;
            if (viewGroup9 == null) {
                viewGroup9 = null;
            }
            viewVisibilityUtil3.getClass();
            viewGroup9.setVisibility(0);
            ViewGroup viewGroup10 = this.volumePanelDualView;
            if (viewGroup10 == null) {
                viewGroup10 = null;
            }
            ViewVisibilityUtil.setGone(viewGroup10);
            ViewGroup viewGroup11 = this.volumeAODPanelView;
            if (viewGroup11 == null) {
                viewGroup11 = null;
            }
            ViewVisibilityUtil.setGone(viewGroup11);
        }
        if (volumePanelState.isLockscreen()) {
            ViewVisibilityUtil viewVisibilityUtil4 = ViewVisibilityUtil.INSTANCE;
            ImageView imageView2 = this.expandButton;
            if (imageView2 != null) {
                imageView = imageView2;
            }
            viewVisibilityUtil4.getClass();
            ViewVisibilityUtil.setGone(imageView);
            return;
        }
        ViewVisibilityUtil viewVisibilityUtil5 = ViewVisibilityUtil.INSTANCE;
        ImageView imageView3 = this.expandButton;
        if (imageView3 != null) {
            imageView = imageView3;
        }
        viewVisibilityUtil5.getClass();
        imageView.setVisibility(0);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.samsung.systemui.splugins.volume.VolumeObserver
    public final void onChanged(VolumePanelState volumePanelState) {
        VolumePanelRow findRow;
        boolean z;
        byte b;
        VolumePanelState volumePanelState2 = volumePanelState;
        this.panelState = volumePanelState2;
        boolean z2 = false;
        SpringAnimation springAnimation = null;
        SpringAnimation springAnimation2 = null;
        HandlerWrapper handlerWrapper = null;
        Dialog dialog = null;
        SpringAnimation springAnimation3 = null;
        switch (WhenMappings.$EnumSwitchMapping$0[volumePanelState2.getStateType().ordinal()]) {
            case 1:
                this.startProgress = true;
                return;
            case 2:
                this.isSeekBarTouching = true;
                if (this.isDualViewEnabled) {
                    updateVolumeTitleView(volumePanelState2, true);
                    return;
                }
                if (!VolumePanelStateExt.isAODVolumePanel(volumePanelState2)) {
                    SubFullLayoutVolumePanelMotion subFullLayoutVolumePanelMotion = this.volumePanelMotion;
                    if (subFullLayoutVolumePanelMotion == null) {
                        subFullLayoutVolumePanelMotion = null;
                    }
                    SpringAnimation springAnimation4 = this.touchDownAnimation;
                    if (springAnimation4 == null) {
                        springAnimation4 = null;
                    }
                    SpringAnimation springAnimation5 = this.touchUpAnimation;
                    if (springAnimation5 != null) {
                        springAnimation = springAnimation5;
                    }
                    subFullLayoutVolumePanelMotion.getClass();
                    SubFullLayoutVolumePanelMotion.startSeekBarTouchDownAnimation(springAnimation4, springAnimation, false);
                    return;
                }
                return;
            case 3:
                this.isSeekBarTouching = false;
                if (this.isDualViewEnabled) {
                    updateVolumeTitleView(volumePanelState2, false);
                    return;
                }
                if (!VolumePanelStateExt.isAODVolumePanel(volumePanelState2)) {
                    SubFullLayoutVolumePanelMotion subFullLayoutVolumePanelMotion2 = this.volumePanelMotion;
                    if (subFullLayoutVolumePanelMotion2 == null) {
                        subFullLayoutVolumePanelMotion2 = null;
                    }
                    SpringAnimation springAnimation6 = this.touchUpAnimation;
                    if (springAnimation6 == null) {
                        springAnimation6 = null;
                    }
                    SpringAnimation springAnimation7 = this.touchDownAnimation;
                    if (springAnimation7 != null) {
                        springAnimation3 = springAnimation7;
                    }
                    subFullLayoutVolumePanelMotion2.getClass();
                    SubFullLayoutVolumePanelMotion.startSeekBarTouchUpAnimation(springAnimation6, springAnimation3);
                    return;
                }
                return;
            case 4:
                Dialog dialog2 = this.dialog;
                if (dialog2 != null) {
                    dialog = dialog2;
                }
                if (dialog.isShowing() && !this.isDualViewEnabled && !VolumePanelStateExt.isActiveStream(volumePanelState2, this.activeStream)) {
                    addVolumeRows(volumePanelState2);
                    initViewVisibility(volumePanelState2);
                    return;
                }
                return;
            case 5:
                ((WarningDialogController) this.warningDialogController$delegate.getValue()).showVolumeSafetyWarningDialog();
                return;
            case 6:
                ((WarningDialogController) this.warningDialogController$delegate.getValue()).showVolumeLimiterDialog();
                return;
            case 7:
                ((WarningDialogController) this.warningDialogController$delegate.getValue()).showVolumeCSD100WarningDialog();
                return;
            case 8:
                if (VolumePanelStateExt.isAODVolumePanel(volumePanelState2) && (findRow = VolumePanelStateExt.INSTANCE.findRow(volumePanelState2, volumePanelState2.getActiveStream())) != null) {
                    this.currentVolume = findRow.getRealLevel();
                    return;
                }
                return;
            case 9:
                VolumePanelRow findRow2 = VolumePanelStateExt.INSTANCE.findRow(volumePanelState2, volumePanelState2.getActiveStream());
                if (findRow2 != null) {
                    z = findRow2.isSliderEnabled();
                } else {
                    z = false;
                }
                if (!this.isDualViewEnabled && !volumePanelState2.isExpanded() && z) {
                    b = true;
                } else {
                    b = false;
                }
                if (b != false) {
                    if (volumePanelState2.isKeyDown()) {
                        HandlerWrapper handlerWrapper2 = this.handlerWrapper;
                        if (handlerWrapper2 == null) {
                            handlerWrapper2 = null;
                        }
                        handlerWrapper2.remove(this.keyUpRunnable);
                        if (!this.isKeyDownAnimating) {
                            if (!volumePanelState2.isVibrating()) {
                                VibratorWrapper vibratorWrapper = this.vibratorWrapper;
                                if (vibratorWrapper == null) {
                                    vibratorWrapper = null;
                                }
                                vibratorWrapper.startKeyHaptic();
                            }
                            SubFullLayoutVolumePanelMotion subFullLayoutVolumePanelMotion3 = this.volumePanelMotion;
                            if (subFullLayoutVolumePanelMotion3 == null) {
                                subFullLayoutVolumePanelMotion3 = null;
                            }
                            SpringAnimation springAnimation8 = this.keyDownAnimation;
                            if (springAnimation8 == null) {
                                springAnimation8 = null;
                            }
                            SpringAnimation springAnimation9 = this.keyUpAnimation;
                            if (springAnimation9 == null) {
                                springAnimation9 = null;
                            }
                            subFullLayoutVolumePanelMotion3.getClass();
                            if (springAnimation9 != null) {
                                if (springAnimation9.mRunning && springAnimation9.canSkipToEnd()) {
                                    z2 = true;
                                }
                                if (z2) {
                                    springAnimation2 = springAnimation9;
                                }
                                if (springAnimation2 != null) {
                                    springAnimation2.skipToEnd();
                                }
                            }
                            springAnimation8.animateToFinalPosition(0.95f);
                        }
                        this.isKeyDownAnimating = true;
                        return;
                    }
                    if (this.isKeyDownAnimating) {
                        HandlerWrapper handlerWrapper3 = this.handlerWrapper;
                        if (handlerWrapper3 == null) {
                            handlerWrapper3 = null;
                        }
                        handlerWrapper3.remove(this.keyUpRunnable);
                        HandlerWrapper handlerWrapper4 = this.handlerWrapper;
                        if (handlerWrapper4 != null) {
                            handlerWrapper = handlerWrapper4;
                        }
                        handlerWrapper.postDelayed(100L, this.keyUpRunnable);
                        return;
                    }
                    return;
                }
                return;
            default:
                return;
        }
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.volumePanelView = (ViewGroup) findViewById(R.id.volume_panel_view);
        this.volumeAODPanelView = (ViewGroup) findViewById(R.id.volume_aod_panel_view);
        this.volumePanelDualView = (ViewGroup) findViewById(R.id.volume_panel_dual_view);
    }

    public final void startDismissAnimation() {
        final Runnable runnable = new Runnable() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumePanelView$startDismissAnimation$dismissRunnable$1
            @Override // java.lang.Runnable
            public final void run() {
                Dialog dialog = SubFullLayoutVolumePanelView.this.dialog;
                if (dialog == null) {
                    dialog = null;
                }
                dialog.dismiss();
            }
        };
        SpringAnimation springAnimation = null;
        Dialog dialog = null;
        Dialog dialog2 = null;
        if (VolumePanelStateExt.isAODVolumePanel(getPanelState())) {
            final SubFullLayoutVolumePanelMotion subFullLayoutVolumePanelMotion = this.volumePanelMotion;
            if (subFullLayoutVolumePanelMotion == null) {
                subFullLayoutVolumePanelMotion = null;
            }
            Dialog dialog3 = this.dialog;
            if (dialog3 != null) {
                dialog = dialog3;
            }
            Window window = dialog.getWindow();
            Intrinsics.checkNotNull(window);
            View decorView = window.getDecorView();
            subFullLayoutVolumePanelMotion.getClass();
            decorView.animate().alpha(0.0f).setDuration(100L).setInterpolator(SubFullLayoutVolumePanelMotion.HIDE_INTERPOLATOR).setListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumePanelMotion$startAODHideAnimation$1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    runnable.run();
                    SubFullLayoutVolumePanelMotion.this.storeInteractor.sendAction(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_ANIMATION_FINISHED).build(), true);
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationStart(Animator animator) {
                    SubFullLayoutVolumePanelMotion.this.storeInteractor.sendAction(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_ANIMATION_START).build(), true);
                }
            }).start();
            return;
        }
        if (this.isDualViewEnabled) {
            final SubFullLayoutVolumePanelMotion subFullLayoutVolumePanelMotion2 = this.volumePanelMotion;
            if (subFullLayoutVolumePanelMotion2 == null) {
                subFullLayoutVolumePanelMotion2 = null;
            }
            Dialog dialog4 = this.dialog;
            if (dialog4 != null) {
                dialog2 = dialog4;
            }
            Window window2 = dialog2.getWindow();
            Intrinsics.checkNotNull(window2);
            final View decorView2 = window2.getDecorView();
            subFullLayoutVolumePanelMotion2.getClass();
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(decorView2, "alpha", decorView2.getAlpha(), 0.0f);
            ofFloat.setDuration(200L);
            SecLockIconView$$ExternalSyntheticOutline0.m(0.33f, 0.0f, 0.67f, 1.0f, ofFloat);
            ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(decorView2, "scaleX", decorView2.getScaleX(), 0.9f);
            ofFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumePanelMotion$startVolumeDualViewHideAnimation$scaleAnimator$1$1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    decorView2.setScaleY(((Float) valueAnimator.getAnimatedValue()).floatValue());
                }
            });
            ofFloat2.setDuration(200L);
            ofFloat2.setInterpolator(new PathInterpolator(0.33f, 0.0f, 0.67f, 1.0f));
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(ofFloat);
            animatorSet.playTogether(ofFloat2);
            animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumePanelMotion$startVolumeDualViewHideAnimation$1$1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    runnable.run();
                    SubFullLayoutVolumePanelMotion.this.storeInteractor.sendAction(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_ANIMATION_FINISHED).build(), true);
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationStart(Animator animator) {
                    SubFullLayoutVolumePanelMotion.this.storeInteractor.sendAction(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_ANIMATION_START).build(), true);
                }
            });
            animatorSet.start();
            return;
        }
        final SubFullLayoutVolumePanelMotion subFullLayoutVolumePanelMotion3 = this.volumePanelMotion;
        if (subFullLayoutVolumePanelMotion3 == null) {
            subFullLayoutVolumePanelMotion3 = null;
        }
        Dialog dialog5 = this.dialog;
        if (dialog5 == null) {
            dialog5 = null;
        }
        Window window3 = dialog5.getWindow();
        Intrinsics.checkNotNull(window3);
        View decorView3 = window3.getDecorView();
        SpringAnimation springAnimation2 = subFullLayoutVolumePanelMotion3.singleShowSpringAnimation;
        if (springAnimation2 != null) {
            if (springAnimation2.mRunning) {
                springAnimation = springAnimation2;
            }
            if (springAnimation != null) {
                springAnimation.cancel();
            }
        }
        ViewPropertyAnimator animate = decorView3.animate();
        boolean z = BasicRune.VOLUME_LEFT_DISPLAY_VOLUME_DIALOG;
        int width = decorView3.getWidth();
        if (z) {
            width = -width;
        }
        animate.translationX(width).setDuration(350L).setInterpolator(SubFullLayoutVolumePanelMotion.HIDE_INTERPOLATOR).setListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumePanelMotion$startVolumePanelDismissAnimation$2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                runnable.run();
                SubFullLayoutVolumePanelMotion.this.storeInteractor.sendAction(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_ANIMATION_FINISHED).build(), true);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
                SubFullLayoutVolumePanelMotion.this.storeInteractor.sendAction(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_ANIMATION_START).build(), true);
            }
        }).start();
    }

    public final void updateVolumeTitle(VolumePanelState volumePanelState) {
        VolumePanelRow findRow = VolumePanelStateExt.INSTANCE.findRow(volumePanelState, volumePanelState.getStream());
        if (findRow != null) {
            TextView textView = this.dualViewTitle;
            if (textView == null) {
                textView = null;
            }
            textView.setText(VolumePanelRowExt.getStreamLabel(findRow, getContext()));
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v1, types: [android.widget.ImageView] */
    /* JADX WARN: Type inference failed for: r2v2, types: [android.widget.ImageView] */
    public final void updateVolumeTitleView(VolumePanelState volumePanelState, boolean z) {
        TextView textView = null;
        if (volumePanelState.isLockscreen()) {
            if (z) {
                updateVolumeTitle(volumePanelState);
                return;
            }
            TextView textView2 = this.dualViewTitle;
            if (textView2 != null) {
                textView = textView2;
            }
            textView.setText(getContext().getString(R.string.volume_panel_view_title));
            return;
        }
        if (z) {
            updateVolumeTitle(volumePanelState);
            ViewVisibilityUtil viewVisibilityUtil = ViewVisibilityUtil.INSTANCE;
            TextView textView3 = this.dualViewTitle;
            if (textView3 == null) {
                textView3 = null;
            }
            viewVisibilityUtil.getClass();
            textView3.setVisibility(0);
            ?? r2 = this.expandButton;
            if (r2 != 0) {
                textView = r2;
            }
            ViewVisibilityUtil.setGone(textView);
            return;
        }
        ViewVisibilityUtil viewVisibilityUtil2 = ViewVisibilityUtil.INSTANCE;
        TextView textView4 = this.dualViewTitle;
        if (textView4 == null) {
            textView4 = null;
        }
        viewVisibilityUtil2.getClass();
        ViewVisibilityUtil.setGone(textView4);
        ?? r22 = this.expandButton;
        if (r22 != 0) {
            textView = r22;
        }
        textView.setVisibility(0);
    }
}
