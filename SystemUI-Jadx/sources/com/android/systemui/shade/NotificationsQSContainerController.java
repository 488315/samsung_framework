package com.android.systemui.shade;

import android.content.Context;
import android.view.DisplayCutout;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import com.android.app.animation.Interpolators;
import com.android.systemui.Dependency;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.fragments.FragmentHostManager;
import com.android.systemui.fragments.FragmentService;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.plugins.qs.QS;
import com.android.systemui.plugins.qs.QSContainerController;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.recents.OverviewProxyService;
import com.android.systemui.shade.ShadeHeaderController;
import com.android.systemui.shared.system.QuickStepContract;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.NotificationShelfManager;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.util.LargeScreenUtils;
import com.android.systemui.util.ViewController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import kotlin.jvm.internal.MutablePropertyReference0Impl;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class NotificationsQSContainerController extends ViewController implements QSContainerController, StatusBarStateController.StateListener {
    public final DelayableExecutor delayableExecutor;
    public final NotificationsQSContainerController$delayedInsetSetter$1 delayedInsetSetter;
    public int footerActionsOffset;
    public final FragmentService fragmentService;
    public boolean isQSCustomizerAnimating;
    public boolean isQSCustomizing;
    public boolean largeScreenShadeHeaderActive;
    public int largeScreenShadeHeaderHeight;
    public final NavigationModeController navigationModeController;
    public final OverviewProxyService overviewProxyService;
    public int panelMarginHorizontal;
    public boolean qsExpanded;
    public int scrimShadeBottomMargin;
    public final ShadeExpansionStateManager shadeExpansionStateManager;
    public final ShadeHeaderController shadeHeaderController;
    public int shadeHeaderHeight;
    public final NotificationsQSContainerController$shadeQsExpansionListener$1 shadeQsExpansionListener;
    public boolean splitShadeEnabled;
    public final StatusBarStateController statusBarStateController;
    public final NotificationsQSContainerController$taskbarVisibilityListener$1 taskbarVisibilityListener;
    public int windowVisibility;

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.shade.NotificationsQSContainerController$taskbarVisibilityListener$1] */
    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.shade.NotificationsQSContainerController$shadeQsExpansionListener$1] */
    public NotificationsQSContainerController(NotificationsQuickSettingsContainer notificationsQuickSettingsContainer, NavigationModeController navigationModeController, OverviewProxyService overviewProxyService, ShadeHeaderController shadeHeaderController, ShadeExpansionStateManager shadeExpansionStateManager, FragmentService fragmentService, DelayableExecutor delayableExecutor, NotificationShelfManager notificationShelfManager, StatusBarStateController statusBarStateController) {
        super(notificationsQuickSettingsContainer);
        this.navigationModeController = navigationModeController;
        this.overviewProxyService = overviewProxyService;
        this.shadeHeaderController = shadeHeaderController;
        this.shadeExpansionStateManager = shadeExpansionStateManager;
        this.fragmentService = fragmentService;
        this.delayableExecutor = delayableExecutor;
        this.statusBarStateController = statusBarStateController;
        this.largeScreenShadeHeaderActive = true;
        this.taskbarVisibilityListener = new OverviewProxyService.OverviewProxyListener() { // from class: com.android.systemui.shade.NotificationsQSContainerController$taskbarVisibilityListener$1
            @Override // com.android.systemui.recents.OverviewProxyService.OverviewProxyListener
            public final void onTaskbarStatusUpdated(boolean z, boolean z2) {
                NotificationsQSContainerController.this.getClass();
            }
        };
        this.shadeQsExpansionListener = new ShadeQsExpansionListener() { // from class: com.android.systemui.shade.NotificationsQSContainerController$shadeQsExpansionListener$1
            @Override // com.android.systemui.shade.ShadeQsExpansionListener
            public final void onQsExpansionChanged(boolean z) {
                NotificationsQSContainerController notificationsQSContainerController = NotificationsQSContainerController.this;
                if (notificationsQSContainerController.qsExpanded != z) {
                    notificationsQSContainerController.qsExpanded = z;
                    ((NotificationsQuickSettingsContainer) notificationsQSContainerController.mView).invalidate();
                }
            }
        };
        this.delayedInsetSetter = new NotificationsQSContainerController$delayedInsetSetter$1(this);
    }

    @Override // com.android.systemui.util.ViewController
    public final void onInit() {
        this.navigationModeController.addListener(new NavigationModeController.ModeChangedListener() { // from class: com.android.systemui.shade.NotificationsQSContainerController$onInit$currentMode$1
            @Override // com.android.systemui.navigationbar.NavigationModeController.ModeChangedListener
            public final void onNavigationModeChanged(int i) {
                QuickStepContract.isGesturalMode(i);
                NotificationsQSContainerController.this.getClass();
            }
        });
        boolean z = QuickStepContract.SYSUI_FORCE_SET_BACK_GESTURE_BY_SPLUGIN;
        this.shadeHeaderController.header.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.android.systemui.shade.NotificationsQSContainerController$onInit$1
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                NotificationsQSContainerController.this.updateConstraints();
            }
        });
        if (QpRune.QUICK_TABLET_BG) {
            ((NotificationsQuickSettingsContainer) this.mView).mStatusBarStateController = this.statusBarStateController;
        }
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() {
        updateResources();
        this.overviewProxyService.addCallback((OverviewProxyService.OverviewProxyListener) this.taskbarVisibilityListener);
        this.shadeExpansionStateManager.addQsExpansionListener(this.shadeQsExpansionListener);
        NotificationsQuickSettingsContainer notificationsQuickSettingsContainer = (NotificationsQuickSettingsContainer) this.mView;
        notificationsQuickSettingsContainer.mInsetsChangedListener = this.delayedInsetSetter;
        Consumer consumer = new Consumer() { // from class: com.android.systemui.shade.NotificationsQSContainerController$onViewAttached$1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((QS) obj).setContainerController(NotificationsQSContainerController.this);
            }
        };
        notificationsQuickSettingsContainer.mQSFragmentAttachedListener = consumer;
        QS qs = notificationsQuickSettingsContainer.mQs;
        if (qs != null) {
            consumer.accept(qs);
        }
        ((NotificationsQuickSettingsContainer) this.mView).mConfigurationChangedListener = new Consumer() { // from class: com.android.systemui.shade.NotificationsQSContainerController$onViewAttached$2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                NotificationsQSContainerController.this.updateResources();
            }
        };
        this.fragmentService.getFragmentHostManager(this.mView).addTagListener(QS.TAG, (FragmentHostManager.FragmentListener) this.mView);
        NotificationShadeWindowController notificationShadeWindowController = (NotificationShadeWindowController) Dependency.get(NotificationShadeWindowController.class);
        final NotificationsQSContainerController$onViewAttached$3 notificationsQSContainerController$onViewAttached$3 = new NotificationsQSContainerController$onViewAttached$3(this);
        NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl = (NotificationShadeWindowControllerImpl) notificationShadeWindowController;
        notificationShadeWindowControllerImpl.getClass();
        ViewGroup viewGroup = notificationShadeWindowControllerImpl.mNotificationShadeView;
        if (viewGroup != null) {
            ((NotificationShadeWindowView) viewGroup).mWindowVisibilityChangedListener = new IntConsumer() { // from class: com.android.systemui.shade.NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda5
                @Override // java.util.function.IntConsumer
                public final void accept(int i) {
                    NotificationsQSContainerController notificationsQSContainerController = NotificationsQSContainerController$onViewAttached$3.this.$tmp0;
                    notificationsQSContainerController.windowVisibility = i;
                    NotificationStackScrollLayout notificationStackScrollLayout = (NotificationStackScrollLayout) ((NotificationsQuickSettingsContainer) notificationsQSContainerController.mView).getViewById(R.id.notification_stack_scroller);
                    if (notificationsQSContainerController.windowVisibility == 0) {
                        notificationStackScrollLayout.updateVisibility();
                    } else {
                        notificationStackScrollLayout.setVisibility(8);
                    }
                }
            };
        }
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewDetached() {
        ((ArrayList) this.overviewProxyService.mConnectionCallbacks).remove(this.taskbarVisibilityListener);
        this.shadeExpansionStateManager.qsExpansionListeners.remove(this.shadeQsExpansionListener);
        NotificationsQuickSettingsContainer notificationsQuickSettingsContainer = (NotificationsQuickSettingsContainer) this.mView;
        notificationsQuickSettingsContainer.getClass();
        notificationsQuickSettingsContainer.mInsetsChangedListener = new NotificationsQuickSettingsContainer$$ExternalSyntheticLambda1(2);
        NotificationsQuickSettingsContainer notificationsQuickSettingsContainer2 = (NotificationsQuickSettingsContainer) this.mView;
        notificationsQuickSettingsContainer2.getClass();
        notificationsQuickSettingsContainer2.mQSFragmentAttachedListener = new NotificationsQuickSettingsContainer$$ExternalSyntheticLambda1(3);
        View view = this.mView;
        ((NotificationsQuickSettingsContainer) view).mConfigurationChangedListener = null;
        FragmentHostManager fragmentHostManager = this.fragmentService.getFragmentHostManager(view);
        FragmentHostManager.FragmentListener fragmentListener = (FragmentHostManager.FragmentListener) this.mView;
        HashMap hashMap = fragmentHostManager.mListeners;
        ArrayList arrayList = (ArrayList) hashMap.get(QS.TAG);
        if (arrayList != null && arrayList.remove(fragmentListener) && arrayList.size() == 0) {
            hashMap.remove(QS.TAG);
        }
    }

    @Override // com.android.systemui.plugins.qs.QSContainerController
    public final void setCustomizerAnimating(boolean z) {
        if (this.isQSCustomizerAnimating != z) {
            this.isQSCustomizerAnimating = z;
            ((NotificationsQuickSettingsContainer) this.mView).invalidate();
        }
    }

    @Override // com.android.systemui.plugins.qs.QSContainerController
    public final void setCustomizerShowing(boolean z) {
        QSContainerController.DefaultImpls.setCustomizerShowing(this, z);
    }

    public final void updateConstraints() {
        int i;
        int i2;
        int i3;
        int viewHeight;
        ConstraintSet.Layout layout;
        int i4;
        boolean z;
        int i5;
        DisplayCutout displayCutout;
        ViewGroup viewGroup = (ViewGroup) this.mView;
        int childCount = viewGroup.getChildCount();
        int i6 = 0;
        for (int i7 = 0; i7 < childCount; i7++) {
            View childAt = viewGroup.getChildAt(i7);
            if (childAt.getId() == -1) {
                childAt.setId(View.generateViewId());
            }
        }
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone((ConstraintLayout) this.mView);
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.status_view_margin_horizontal);
        constraintSet.setMargin(R.id.keyguard_status_view, 6, dimensionPixelSize);
        constraintSet.setMargin(R.id.keyguard_status_view, 7, dimensionPixelSize);
        boolean z2 = this.splitShadeEnabled;
        int i8 = R.id.qs_edge_guideline;
        if (z2) {
            i = R.id.qs_edge_guideline;
        } else {
            i = 0;
        }
        constraintSet.connect(R.id.qs_frame, 7, i, 7);
        if (this.splitShadeEnabled) {
            i2 = 0;
        } else {
            i2 = this.panelMarginHorizontal;
        }
        constraintSet.setMargin(R.id.qs_frame, 6, i2);
        if (this.splitShadeEnabled) {
            i3 = 0;
        } else {
            i3 = this.panelMarginHorizontal;
        }
        constraintSet.setMargin(R.id.qs_frame, 7, i3);
        boolean z3 = QpRune.QUICK_TABLET;
        ShadeHeaderController shadeHeaderController = this.shadeHeaderController;
        if (z3) {
            viewHeight = 0;
        } else {
            viewHeight = shadeHeaderController.getViewHeight();
        }
        constraintSet.setMargin(R.id.qs_frame, 3, viewHeight);
        constraintSet.setMargin(R.id.qs_frame, 4, ((SecQSPanelResourcePicker) Dependency.get(SecQSPanelResourcePicker.class)).getNavBarHeight(((NotificationsQuickSettingsContainer) this.mView).getContext()));
        ConstraintSet.Constraint constraint = constraintSet.getConstraint(R.id.qs_frame);
        if (constraint != null) {
            layout = constraint.layout;
        } else {
            layout = null;
        }
        if (layout != null) {
            SecQSPanelResourcePicker secQSPanelResourcePicker = (SecQSPanelResourcePicker) Dependency.get(SecQSPanelResourcePicker.class);
            Context context = ((NotificationsQuickSettingsContainer) this.mView).getContext();
            secQSPanelResourcePicker.getClass();
            layout.mWidth = SecQSPanelResourcePicker.getPanelWidth(context);
        }
        SecQSPanelResourcePicker secQSPanelResourcePicker2 = (SecQSPanelResourcePicker) Dependency.get(SecQSPanelResourcePicker.class);
        Context context2 = ((NotificationsQuickSettingsContainer) this.mView).getContext();
        secQSPanelResourcePicker2.getClass();
        constraintSet.constrainWidth(R.id.qs_frame, SecQSPanelResourcePicker.getPanelWidth(context2));
        if (!this.splitShadeEnabled) {
            i8 = 0;
        }
        constraintSet.connect(R.id.notification_stack_scroller, 6, i8, 6);
        if (this.splitShadeEnabled) {
            i4 = 0;
        } else {
            i4 = this.panelMarginHorizontal;
        }
        constraintSet.setMargin(R.id.notification_stack_scroller, 6, i4);
        constraintSet.setMargin(R.id.notification_stack_scroller, 7, this.panelMarginHorizontal);
        constraintSet.setMargin(R.id.notification_stack_scroller, 3, 0);
        int i9 = getResources().getConfiguration().orientation;
        int navBarHeight = ((SecQSPanelResourcePicker) Dependency.get(SecQSPanelResourcePicker.class)).getNavBarHeight(((NotificationsQuickSettingsContainer) this.mView).getContext());
        SecQSPanelResourcePicker secQSPanelResourcePicker3 = (SecQSPanelResourcePicker) Dependency.get(SecQSPanelResourcePicker.class);
        secQSPanelResourcePicker3.getClass();
        if (QpRune.PANEL_DATA_USAGE_LABEL && secQSPanelResourcePicker3.mDataUsageLabelVisible) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            i5 = getResources().getDimensionPixelSize(R.dimen.notification_panel_carrier_label_height);
        } else {
            i5 = 0;
        }
        int i10 = navBarHeight + i5;
        int dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.notification_shelf_bottom_space);
        if (i10 < dimensionPixelSize2) {
            i10 = dimensionPixelSize2;
        }
        constraintSet.setMargin(R.id.notification_stack_scroller, 4, i10);
        SecQSPanelResourcePicker secQSPanelResourcePicker4 = (SecQSPanelResourcePicker) Dependency.get(SecQSPanelResourcePicker.class);
        Context context3 = ((NotificationsQuickSettingsContainer) this.mView).getContext();
        secQSPanelResourcePicker4.getClass();
        constraintSet.constrainWidth(R.id.notification_stack_scroller, SecQSPanelResourcePicker.getPanelWidth(context3));
        if (this.largeScreenShadeHeaderActive) {
            WindowInsets rootWindowInsets = ((NotificationsQuickSettingsContainer) this.mView).getRootWindowInsets();
            if (rootWindowInsets != null && (displayCutout = rootWindowInsets.getDisplayCutout()) != null) {
                i6 = displayCutout.getBoundingRectTop().bottom;
            }
            if (i6 == 0 || z3) {
                if (z3) {
                    i6 = getResources().getDimensionPixelSize(R.dimen.shade_header_top_margin_tablet);
                } else {
                    i6 = getResources().getDimensionPixelSize(R.dimen.shade_header_no_cutout_top_margin);
                }
            }
            constraintSet.constrainHeight(R.id.split_shade_status_bar, this.largeScreenShadeHeaderHeight + i6);
            MotionLayout motionLayout = shadeHeaderController.header;
            motionLayout.setPaddingRelative(motionLayout.getPaddingLeft(), i6, motionLayout.getPaddingRight(), motionLayout.getPaddingBottom());
        } else {
            constraintSet.constrainHeight(R.id.split_shade_status_bar, this.shadeHeaderHeight);
        }
        SecQSPanelResourcePicker secQSPanelResourcePicker5 = (SecQSPanelResourcePicker) Dependency.get(SecQSPanelResourcePicker.class);
        Context context4 = ((NotificationsQuickSettingsContainer) this.mView).getContext();
        secQSPanelResourcePicker5.getClass();
        constraintSet.constrainWidth(R.id.split_shade_status_bar, SecQSPanelResourcePicker.getPanelWidth(context4));
        NotificationsQuickSettingsContainer notificationsQuickSettingsContainer = (NotificationsQuickSettingsContainer) this.mView;
        notificationsQuickSettingsContainer.getClass();
        constraintSet.applyTo(notificationsQuickSettingsContainer);
    }

    public final void updateResources() {
        int i;
        this.splitShadeEnabled = LargeScreenUtils.shouldUseSplitNotificationShade(getResources());
        this.largeScreenShadeHeaderActive = true;
        getResources().getDimensionPixelSize(R.dimen.notification_panel_margin_bottom);
        SecQSPanelResourcePicker secQSPanelResourcePicker = (SecQSPanelResourcePicker) Dependency.get(SecQSPanelResourcePicker.class);
        Context context = ((NotificationsQuickSettingsContainer) this.mView).getContext();
        secQSPanelResourcePicker.getClass();
        if (QpRune.QUICK_TABLET) {
            i = R.dimen.large_screen_shade_header_height_tablet;
        } else {
            i = R.dimen.large_screen_shade_header_height;
        }
        this.largeScreenShadeHeaderHeight = context.getResources().getDimensionPixelSize(i);
        this.shadeHeaderHeight = getResources().getDimensionPixelSize(R.dimen.qs_header_height);
        this.panelMarginHorizontal = getResources().getDimensionPixelSize(R.dimen.notification_panel_margin_horizontal);
        if (!this.largeScreenShadeHeaderActive) {
            getResources().getDimensionPixelSize(R.dimen.notification_panel_margin_top);
        }
        updateConstraints();
        MutablePropertyReference0Impl mutablePropertyReference0Impl = new MutablePropertyReference0Impl(this) { // from class: com.android.systemui.shade.NotificationsQSContainerController$updateResources$scrimMarginChanged$1
            @Override // kotlin.jvm.internal.MutablePropertyReference0Impl, kotlin.reflect.KProperty0
            public final Object get() {
                return Integer.valueOf(((NotificationsQSContainerController) this.receiver).scrimShadeBottomMargin);
            }

            @Override // kotlin.jvm.internal.MutablePropertyReference0Impl, kotlin.reflect.KMutableProperty0
            public final void set(Object obj) {
                ((NotificationsQSContainerController) this.receiver).scrimShadeBottomMargin = ((Number) obj).intValue();
            }
        };
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.split_shade_notifications_scrim_margin_bottom);
        ((Number) mutablePropertyReference0Impl.get()).intValue();
        mutablePropertyReference0Impl.set(Integer.valueOf(dimensionPixelSize));
        MutablePropertyReference0Impl mutablePropertyReference0Impl2 = new MutablePropertyReference0Impl(this) { // from class: com.android.systemui.shade.NotificationsQSContainerController$updateResources$footerOffsetChanged$1
            @Override // kotlin.jvm.internal.MutablePropertyReference0Impl, kotlin.reflect.KProperty0
            public final Object get() {
                return Integer.valueOf(((NotificationsQSContainerController) this.receiver).footerActionsOffset);
            }

            @Override // kotlin.jvm.internal.MutablePropertyReference0Impl, kotlin.reflect.KMutableProperty0
            public final void set(Object obj) {
                ((NotificationsQSContainerController) this.receiver).footerActionsOffset = ((Number) obj).intValue();
            }
        };
        int dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.qs_footer_actions_bottom_padding) + getResources().getDimensionPixelSize(R.dimen.qs_footer_action_inset);
        ((Number) mutablePropertyReference0Impl2.get()).intValue();
        mutablePropertyReference0Impl2.set(Integer.valueOf(dimensionPixelSize2));
    }

    @Override // com.android.systemui.plugins.qs.QSContainerController
    public final void setCustomizerShowing(boolean z, long j) {
        if (z != this.isQSCustomizing) {
            this.isQSCustomizing = z;
            ShadeHeaderController shadeHeaderController = this.shadeHeaderController;
            shadeHeaderController.header.animate().setDuration(j).alpha(z ? 0.0f : 1.0f).setInterpolator(z ? Interpolators.ALPHA_OUT : Interpolators.ALPHA_IN).setListener(new ShadeHeaderController.CustomizerAnimationListener(z)).start();
        }
    }

    @Override // com.android.systemui.plugins.qs.QSContainerController
    public final void setDetailShowing(boolean z) {
    }
}
