package com.android.systemui.navigationbar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.navigationbar.buttons.KeyButtonView;
import com.android.systemui.navigationbar.buttons.ReverseLinearLayout;
import com.android.systemui.navigationbar.store.EventTypeFactory;
import com.android.systemui.navigationbar.store.NavBarStateManager;
import com.android.systemui.navigationbar.store.NavBarStore;
import com.android.systemui.navigationbar.store.NavBarStoreImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class SamsungNavigationBarInflaterView extends NavigationBarInflaterView {
    public final int displayId;
    public final NavBarStateManager navBarStateManager;
    public final NavBarStore navBarStore;
    public static final Companion Companion = new Companion(null);
    public static final String space = "space";
    public static final String leftstr = "left";
    public static final String rightstr = "right";
    public static final String pin = "pin";
    public static final String navkey = "navkey";
    public static final String keymargin = "gap";
    public static final String extraBack = "extra_back";
    public static final String leftGestureHint = "hint_left";
    public static final String centerGestureHint = "hint_center";
    public static final String rightGestureHint = "hint_right";
    public static final String leftRemoteView = "left_remote_view";
    public static final String rightRemoteView = "right_remote_view";
    public static final String taskStack = "task_stack";
    public static final String buttonSpace = "button_space";

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public SamsungNavigationBarInflaterView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        int displayId = context.getDisplayId();
        this.displayId = displayId;
        NavBarStore navBarStore = (NavBarStore) Dependency.get(NavBarStore.class);
        this.navBarStore = navBarStore;
        this.navBarStateManager = ((NavBarStoreImpl) navBarStore).getNavStateManager(displayId);
    }

    public final void addSidePadding(View view, boolean z) {
        int intValue = ((Number) ((NavBarStoreImpl) this.navBarStore).handleEvent(this, new EventTypeFactory.EventType.GetNavBarSidePadding(z), this.displayId, 0)).intValue();
        view.setPadding(intValue, 0, intValue, 0);
    }

    @Override // com.android.systemui.navigationbar.NavigationBarInflaterView
    public final View createView(String str, ViewGroup viewGroup, LayoutInflater layoutInflater) {
        View createView;
        Integer num;
        String extractButton = NavigationBarInflaterView.extractButton(str);
        if ((!BasicRune.NAVBAR_MULTI_MODAL_ICON || (!Intrinsics.areEqual(extractButton, leftstr) && !Intrinsics.areEqual(extractButton, rightstr))) && (createView = super.createView(str, viewGroup, layoutInflater)) != null) {
            return createView;
        }
        if (Intrinsics.areEqual(extractButton, leftstr)) {
            num = Integer.valueOf(R.layout.samsung_left);
        } else if (Intrinsics.areEqual(extractButton, rightstr)) {
            num = Integer.valueOf(R.layout.samsung_right);
        } else if (Intrinsics.areEqual(extractButton, leftGestureHint)) {
            num = Integer.valueOf(R.layout.hint_left);
        } else if (Intrinsics.areEqual(extractButton, centerGestureHint)) {
            num = Integer.valueOf(R.layout.hint_center);
        } else if (Intrinsics.areEqual(extractButton, rightGestureHint)) {
            num = Integer.valueOf(R.layout.hint_right);
        } else if (Intrinsics.areEqual(extractButton, extraBack)) {
            num = Integer.valueOf(R.layout.back);
        } else if (Intrinsics.areEqual(extractButton, pin)) {
            num = Integer.valueOf(R.layout.navbar_pin);
        } else if (Intrinsics.areEqual(extractButton, keymargin)) {
            num = Integer.valueOf(R.layout.navbar_key_distance);
        } else if (Intrinsics.areEqual(extractButton, taskStack)) {
            num = Integer.valueOf(R.layout.navbar_task_stack);
        } else if (Intrinsics.areEqual(extractButton, buttonSpace)) {
            num = Integer.valueOf(R.layout.navbar_key_distance);
        } else if (extractButton.startsWith(navkey)) {
            num = Integer.valueOf(R.layout.navbar_custom);
        } else {
            num = null;
        }
        if (num == null) {
            return null;
        }
        View inflate = layoutInflater.inflate(num.intValue(), viewGroup, false);
        if (extractButton.startsWith(navkey)) {
            KeyButtonView keyButtonView = (KeyButtonView) inflate;
            keyButtonView.setId(Integer.parseInt(NavigationBarInflaterView.extractImage(extractButton)));
            keyButtonView.mCode = NavigationBarInflaterView.extractKeycode(extractButton);
        }
        return inflate;
    }

    @Override // com.android.systemui.navigationbar.NavigationBarInflaterView
    public final String getDefaultLayout() {
        return (String) ((NavBarStoreImpl) this.navBarStore).handleEvent(this, new EventTypeFactory.EventType.GetDefaultLayout(false, 1, null), this.displayId, ((FrameLayout) this).mContext.getString(R.string.config_navBarLayout));
    }

    @Override // com.android.systemui.navigationbar.NavigationBarInflaterView
    public final void inflateButton(String str, ViewGroup viewGroup, boolean z, boolean z2) {
        LayoutInflater layoutInflater;
        View view;
        if (z) {
            layoutInflater = this.mLandscapeInflater;
        } else {
            layoutInflater = this.mLayoutInflater;
        }
        View createView = createView(str, viewGroup, layoutInflater);
        if (createView == null) {
            return;
        }
        View applySize = applySize(createView, str, z, z2);
        applySize.getLayoutParams().width = ((Number) ((NavBarStoreImpl) this.navBarStore).handleEvent(this, new EventTypeFactory.EventType.GetInflateButtonWidth(NavigationBarInflaterView.extractButton(str), z), this.displayId, Integer.valueOf(getContext().getResources().getDimensionPixelSize(R.dimen.navigation_key_width)))).intValue();
        if (viewGroup != null) {
            viewGroup.addView(applySize);
        }
        addToDispatchers(applySize);
        if (z) {
            view = this.mLastLandscape;
        } else {
            view = this.mLastPortrait;
        }
        if (applySize instanceof ReverseLinearLayout.ReverseRelativeLayout) {
            applySize = ((ReverseLinearLayout.ReverseRelativeLayout) applySize).getChildAt(0);
        }
        if (view != null) {
            Intrinsics.checkNotNull(applySize);
            applySize.setAccessibilityTraversalAfter(view.getId());
        }
        if (z) {
            this.mLastLandscape = applySize;
        } else {
            this.mLastPortrait = applySize;
        }
    }

    @Override // com.android.systemui.navigationbar.NavigationBarInflaterView
    public final void inflateChildren() {
        removeAllViews();
        FrameLayout frameLayout = (FrameLayout) this.mLayoutInflater.inflate(((Number) ((NavBarStoreImpl) this.navBarStore).handleEvent(this, new EventTypeFactory.EventType.GetInflateLayoutID(false), this.displayId, Integer.valueOf(R.layout.navigation_layout))).intValue(), (ViewGroup) this, false);
        this.mHorizontal = frameLayout;
        addView(frameLayout);
        FrameLayout frameLayout2 = (FrameLayout) this.mLayoutInflater.inflate(((Number) ((NavBarStoreImpl) this.navBarStore).handleEvent(this, new EventTypeFactory.EventType.GetInflateLayoutID(true), this.displayId, Integer.valueOf(R.layout.navigation_layout_vertical))).intValue(), (ViewGroup) this, false);
        this.mVertical = frameLayout2;
        addView(frameLayout2);
        updateAlternativeOrder();
    }

    @Override // com.android.systemui.navigationbar.NavigationBarInflaterView
    public final void inflateLayout(String str) {
        super.inflateLayout(str);
        if (BasicRune.NAVBAR_REMOTEVIEW) {
            if (this.mVertical.findViewById(R.id.nav_bar_widget) != null && this.mHorizontal.findViewById(R.id.nav_bar_widget) != null) {
                if (BasicRune.NAVBAR_MULTI_MODAL_ICON_LARGE_COVER && this.displayId == 1 && this.navBarStateManager.states.rotation == 0) {
                    ViewGroup viewGroup = (ViewGroup) this.mHorizontal.findViewById(R.id.nav_bar_widget);
                    String str2 = space;
                    inflateButton(str2, viewGroup, false, true);
                    String str3 = leftRemoteView;
                    inflateRemoteViewButtons(str3, viewGroup, false);
                    String str4 = keymargin;
                    inflateButton(str4, viewGroup, false, true);
                    String str5 = rightRemoteView;
                    inflateRemoteViewButtons(str5, viewGroup, false);
                    inflateButton(str2, viewGroup, false, true);
                    ViewGroup viewGroup2 = (ViewGroup) this.mVertical.findViewById(R.id.nav_bar_widget);
                    inflateButton(str2, viewGroup2, true, true);
                    inflateRemoteViewButtons(str3, viewGroup2, false);
                    inflateButton(str4, viewGroup2, true, true);
                    inflateRemoteViewButtons(str5, viewGroup2, false);
                    inflateButton(str2, viewGroup2, true, true);
                } else {
                    String str6 = leftRemoteView;
                    inflateRemoteViewButtons(str6, (ViewGroup) this.mHorizontal.findViewById(R.id.nav_bar_widget), false);
                    addGravitySpacer((LinearLayout) this.mHorizontal.findViewById(R.id.nav_bar_widget));
                    String str7 = rightRemoteView;
                    inflateRemoteViewButtons(str7, (ViewGroup) this.mHorizontal.findViewById(R.id.nav_bar_widget), false);
                    inflateRemoteViewButtons(str6, (ViewGroup) this.mVertical.findViewById(R.id.nav_bar_widget), true);
                    addGravitySpacer((LinearLayout) this.mVertical.findViewById(R.id.nav_bar_widget));
                    inflateRemoteViewButtons(str7, (ViewGroup) this.mVertical.findViewById(R.id.nav_bar_widget), true);
                }
            }
            addSidePadding(this.mHorizontal.findViewById(R.id.nav_bar_widget), false);
            addSidePadding(this.mVertical.findViewById(R.id.nav_bar_widget), true);
        }
        if (BasicRune.NAVBAR_STABLE_LAYOUT) {
            addSidePadding(this.mHorizontal.findViewById(R.id.ends_group), false);
            addSidePadding(this.mVertical.findViewById(R.id.ends_group), true);
        }
    }

    public final void inflateRemoteViewButtons(String str, ViewGroup viewGroup, boolean z) {
        LayoutInflater layoutInflater;
        View view;
        View view2;
        if (z) {
            layoutInflater = this.mLandscapeInflater;
        } else {
            layoutInflater = this.mLayoutInflater;
        }
        if (Intrinsics.areEqual(leftRemoteView, str)) {
            view = layoutInflater.inflate(R.layout.navbar_remoteview_left, viewGroup, false);
        } else if (Intrinsics.areEqual(rightRemoteView, str)) {
            view = layoutInflater.inflate(R.layout.navbar_remoteview_right, viewGroup, false);
        } else {
            view = null;
        }
        if (view != null) {
            view.getLayoutParams().width = ((Number) ((NavBarStoreImpl) this.navBarStore).handleEvent(this, new EventTypeFactory.EventType.GetInflateButtonWidth(str, z), this.displayId, Integer.valueOf(getContext().getResources().getDimensionPixelSize(R.dimen.navigation_key_width)))).intValue();
            viewGroup.addView(view);
            if (z) {
                view2 = this.mLastLandscape;
            } else {
                view2 = this.mLastPortrait;
            }
            if (view instanceof ReverseLinearLayout.ReverseRelativeLayout) {
                view = ((ReverseLinearLayout.ReverseRelativeLayout) view).getChildAt(0);
            }
            if (view2 != null) {
                view.setAccessibilityTraversalAfter(view2.getId());
            }
            if (z) {
                this.mLastLandscape = view;
            } else {
                this.mLastPortrait = view;
            }
        }
    }

    @Override // com.android.systemui.navigationbar.NavigationBarInflaterView
    public final void updateLayoutProviderView() {
        inflateChildren();
        if (getParent() instanceof NavigationBarView) {
            ((NavigationBarView) getParent()).updateOrientationViews();
        }
        clearViews();
        inflateLayout(getDefaultLayout());
    }
}
