package com.android.systemui.navigationbar.plugin;

import android.content.Context;
import android.os.Bundle;
import com.android.systemui.Dependency;
import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.basic.util.ModuleType;
import com.android.systemui.navigationbar.SamsungNavigationBarInflaterView;
import com.android.systemui.navigationbar.TaskbarDelegate;
import com.android.systemui.navigationbar.icon.NavBarIconResourceMapper;
import com.android.systemui.navigationbar.plugin.NavBarStoreAdapterImpl;
import com.android.systemui.navigationbar.store.NavBarStateManager;
import com.android.systemui.navigationbar.store.NavBarStore;
import com.android.systemui.navigationbar.store.NavBarStoreImpl;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.shared.navigationbar.NavBarEvents;
import com.android.systemui.shared.system.QuickStepContract;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.statusbar.policy.RotationLockController;
import com.samsung.systemui.splugins.navigationbar.BarLayoutParams;
import com.samsung.systemui.splugins.navigationbar.ButtonDispatcherProxyBase;
import com.samsung.systemui.splugins.navigationbar.ColorSetting;
import com.samsung.systemui.splugins.navigationbar.ExtendableBar;
import com.samsung.systemui.splugins.navigationbar.FeatureChecker;
import com.samsung.systemui.splugins.navigationbar.IconThemeBase;
import com.samsung.systemui.splugins.navigationbar.LayoutProvider;
import com.samsung.systemui.splugins.navigationbar.LayoutProviderContainer;
import com.samsung.systemui.splugins.navigationbar.NavBarStoreAdapter;
import com.samsung.systemui.splugins.navigationbar.TaskStackAdapterBase;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class SamsungPluginTaskBar implements ExtendableBar {
    public final TaskBarButtonDispatcherProxy buttonDispatcherProxy;
    public NavBarIconResourceMapper iconResourceMapper;
    public boolean isKeyguardShowing;
    public final SamsungPluginTaskBar$keyguardCallback$1 keyguardCallback;
    public final KeyguardStateController keyguardStateController;
    public final Context mainContext;
    public final int mainDisplayId;
    public final NavBarStateManager navBarStateManager;
    public final NavBarStore navBarStore;
    public final Bundle pluginBundle;
    public final RotationLockController rotationLockContainer;
    public TaskbarDelegate taskbarDelegate;

    /* JADX WARN: Type inference failed for: r3v10, types: [com.android.systemui.navigationbar.plugin.SamsungPluginTaskBar$keyguardCallback$1] */
    public SamsungPluginTaskBar(NavBarStore navBarStore, Context context) {
        this.navBarStore = navBarStore;
        this.mainContext = context;
        int displayId = context.getDisplayId();
        this.mainDisplayId = displayId;
        Bundle bundle = new Bundle();
        this.pluginBundle = bundle;
        this.navBarStateManager = ((NavBarStoreImpl) navBarStore).getNavStateManager(displayId);
        this.buttonDispatcherProxy = new TaskBarButtonDispatcherProxy(context, bundle);
        this.keyguardStateController = (KeyguardStateController) Dependency.get(KeyguardStateController.class);
        this.rotationLockContainer = (RotationLockController) Dependency.get(RotationLockController.class);
        this.keyguardCallback = new KeyguardStateController.Callback() { // from class: com.android.systemui.navigationbar.plugin.SamsungPluginTaskBar$keyguardCallback$1
            @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
            public final void onKeyguardShowingChanged() {
                SamsungPluginTaskBar samsungPluginTaskBar = SamsungPluginTaskBar.this;
                KeyguardStateController keyguardStateController = samsungPluginTaskBar.keyguardStateController;
                if (keyguardStateController != null) {
                    samsungPluginTaskBar.isKeyguardShowing = ((KeyguardStateControllerImpl) keyguardStateController).mShowing;
                }
            }
        };
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final void forceSetBackGesture(boolean z) {
        QuickStepContract.SYSUI_FORCE_SET_BACK_GESTURE_BY_SPLUGIN = z;
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final int getBarDisplayId() {
        return this.mainDisplayId;
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final BarLayoutParams getBarLayoutParamsProvider() {
        return (BarLayoutParams) ((NavBarStoreImpl) this.navBarStore).getProvider(2, this.mainDisplayId);
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final ButtonDispatcherProxyBase getButtonDispatcherProxy() {
        return this.buttonDispatcherProxy;
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final ColorSetting getDefaultColorProvider() {
        return (ColorSetting) ((NavBarStoreImpl) this.navBarStore).getProvider(0, this.mainDisplayId);
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final IconThemeBase getDefaultIconTheme() {
        NavBarIconResourceMapper navBarIconResourceMapper = this.iconResourceMapper;
        if (navBarIconResourceMapper != null) {
            return navBarIconResourceMapper.getDefaultIconTheme();
        }
        return null;
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final LayoutProviderContainer getDefaultLayoutProviderContainer() {
        return (LayoutProviderContainer) ((NavBarStoreImpl) this.navBarStore).getProvider(1, this.mainDisplayId);
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final int getDisabledFlags() {
        TaskbarDelegate taskbarDelegate = this.taskbarDelegate;
        if (taskbarDelegate != null) {
            return taskbarDelegate.mDisabledFlags;
        }
        return 0;
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final FeatureChecker getFeatureChecker() {
        return new BasicRuneFeatureChecker();
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final NavBarStoreAdapter getNavBarStoreAdapter() {
        NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) this.navBarStore;
        navBarStoreImpl.getClass();
        NavBarStoreAdapterImpl.Companion companion = NavBarStoreAdapterImpl.Companion;
        LogWrapper logWrapper = new LogWrapper(ModuleType.NAVBAR, null);
        companion.getClass();
        NavBarStoreAdapter navBarStoreAdapter = NavBarStoreAdapterImpl.INSTANCE;
        if (navBarStoreAdapter == null) {
            NavBarStoreAdapterImpl navBarStoreAdapterImpl = new NavBarStoreAdapterImpl(navBarStoreImpl, logWrapper);
            NavBarStoreAdapterImpl.INSTANCE = navBarStoreAdapterImpl;
            return navBarStoreAdapterImpl;
        }
        return navBarStoreAdapter;
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final Context getNavigationBarContext() {
        return this.mainContext;
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final TaskStackAdapterBase getTaskStackAdapter() {
        return null;
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final boolean isCoverDisplayNavBarEnabled() {
        return ExtendableBar.DefaultImpls.isCoverDisplayNavBarEnabled(this);
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final boolean isCoverLargeScreenTaskEnabled() {
        return ExtendableBar.DefaultImpls.isCoverLargeScreenTaskEnabled(this);
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final boolean isKeyguardShowing() {
        return this.isKeyguardShowing;
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final boolean isTaskbarEnabled() {
        int i = NavBarStateManager.$r8$clinit;
        return this.navBarStateManager.isTaskBarEnabled(false);
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final void notifyForceImmersiveStateChanged() {
        updatePluginBundle();
    }

    public final void parseAndUpdateBundle() {
        String defaultLayout;
        String substring;
        NavBarStateManager navBarStateManager = this.navBarStateManager;
        if (navBarStateManager.isGestureMode()) {
            boolean isBottomGestureMode = navBarStateManager.isBottomGestureMode(false);
            LayoutProvider layoutProvider = navBarStateManager.states.layoutProvider;
            Intrinsics.checkNotNull(layoutProvider);
            defaultLayout = layoutProvider.getGesturalLayout(isBottomGestureMode, !navBarStateManager.settingsHelper.isNavBarButtonOrderDefault());
            navBarStateManager.logNavBarStates(defaultLayout, "getGesturalLayout");
            Intrinsics.checkNotNull(defaultLayout);
        } else {
            defaultLayout = navBarStateManager.getDefaultLayout();
        }
        List split$default = StringsKt__StringsKt.split$default(defaultLayout, new String[]{";"}, 3, 2);
        if (split$default.size() != 3) {
            return;
        }
        List split$default2 = StringsKt__StringsKt.split$default((CharSequence) split$default.get(0), new String[]{","}, 0, 6);
        List split$default3 = StringsKt__StringsKt.split$default((CharSequence) split$default.get(1), new String[]{","}, 0, 6);
        List split$default4 = StringsKt__StringsKt.split$default((CharSequence) split$default.get(2), new String[]{","}, 0, 6);
        ArrayList<String> arrayList = new ArrayList<>();
        Bundle bundle = this.pluginBundle;
        bundle.putStringArrayList("order", arrayList);
        bundle.putBoolean("pin", false);
        Iterator it = ((ArrayList) CollectionsKt___CollectionsKt.plus((Iterable) split$default4, (Collection) CollectionsKt___CollectionsKt.plus((Iterable) split$default3, (Collection) split$default2))).iterator();
        int i = 0;
        while (it.hasNext()) {
            String str = (String) it.next();
            if (!str.equals("recent") && !str.equals(BcSmartspaceDataPlugin.UI_SURFACE_HOME_SCREEN) && !str.equals("back")) {
                SamsungNavigationBarInflaterView.Companion.getClass();
                if (str.startsWith(SamsungNavigationBarInflaterView.navkey)) {
                    if (!StringsKt__StringsKt.contains(str, "(", false)) {
                        substring = "1";
                    } else {
                        substring = str.substring(StringsKt__StringsKt.indexOf$default(str, "(", 0, false, 6) + 1, StringsKt__StringsKt.indexOf$default(str, ":", 0, false, 6));
                    }
                    ArrayList<String> stringArrayList = bundle.getStringArrayList("order");
                    if (stringArrayList != null) {
                        stringArrayList.add(i, substring);
                    }
                } else if (str.equals(SamsungNavigationBarInflaterView.pin)) {
                    bundle.putBoolean("pin", true);
                }
            } else {
                ArrayList<String> stringArrayList2 = bundle.getStringArrayList("order");
                if (stringArrayList2 != null) {
                    stringArrayList2.add(i, str);
                }
            }
            i++;
        }
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final void registerKeyguardStateCallback() {
        KeyguardStateController keyguardStateController = this.keyguardStateController;
        if (keyguardStateController != null) {
            ((KeyguardStateControllerImpl) keyguardStateController).addCallback(this.keyguardCallback);
        }
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final void setBarLayoutParamsProvider(BarLayoutParams barLayoutParams) {
        ((NavBarStoreImpl) this.navBarStore).setProvider(2, this.mainDisplayId, barLayoutParams);
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final void setColorProvider(ColorSetting colorSetting, boolean z) {
        ((NavBarStoreImpl) this.navBarStore).setProvider(0, this.mainDisplayId, colorSetting);
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final void setDefaultBarLayoutParamsProvider() {
        ((NavBarStoreImpl) this.navBarStore).setProvider(2, this.mainDisplayId, null);
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final void setDefaultIconTheme(IconThemeBase iconThemeBase) {
        NavBarIconResourceMapper navBarIconResourceMapper = this.iconResourceMapper;
        if (navBarIconResourceMapper != null) {
            navBarIconResourceMapper.setPreloadedIconSet(iconThemeBase);
        }
        TaskbarDelegate taskbarDelegate = this.taskbarDelegate;
        if (taskbarDelegate != null) {
            taskbarDelegate.updateTaskbarButtonIconsAndHints();
        }
        if (this.navBarStateManager.isGestureMode()) {
            updatePluginBundle();
        }
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final void setIconThemeAlpha(float f) {
        this.pluginBundle.putFloat("alpha", f);
        if (!this.navBarStateManager.isGestureMode()) {
            updatePluginBundle();
        }
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final void setLayoutProviderContainer(LayoutProviderContainer layoutProviderContainer) {
        ((NavBarStoreImpl) this.navBarStore).setProvider(1, this.mainDisplayId, layoutProviderContainer);
        parseAndUpdateBundle();
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final void setRotationLockAtAngle(boolean z, int i) {
        RotationLockController rotationLockController = this.rotationLockContainer;
        if (rotationLockController != null) {
            rotationLockController.setRotationLockedAtAngle(i, z);
        }
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final void setRotationLocked(boolean z) {
        RotationLockController rotationLockController = this.rotationLockContainer;
        if (rotationLockController != null) {
            rotationLockController.setRotationLocked(z);
        }
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final void unregisterKeyguardStateCallback() {
        KeyguardStateController keyguardStateController = this.keyguardStateController;
        if (keyguardStateController != null) {
            ((KeyguardStateControllerImpl) keyguardStateController).removeCallback(this.keyguardCallback);
        }
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final void updateActiveIndicatorSpringParams(float f, float f2) {
        ExtendableBar.DefaultImpls.updateActiveIndicatorSpringParams(this, f, f2);
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final void updateBackPanelColor(int i, int i2, int i3, int i4) {
        ExtendableBar.DefaultImpls.updateBackPanelColor(this, i, i2, i3, i4);
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final void updateIconsAndHints(boolean z) {
        ExtendableBar.DefaultImpls.updateIconsAndHints(this, z);
    }

    public final void updatePluginBundle() {
        NavBarEvents navBarEvents = new NavBarEvents(null, null, null, null, false, 0, false, false, 0, null, false, null, 4095, null);
        navBarEvents.eventType = NavBarEvents.EventType.ON_UPDATE_SPLUGIN_BUNDLE;
        navBarEvents.pluginBundle = this.pluginBundle;
        TaskbarDelegate taskbarDelegate = this.taskbarDelegate;
        if (taskbarDelegate != null) {
            taskbarDelegate.handleNavigationBarEvent(navBarEvents);
        }
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final void updateOpaqueColor(int i) {
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final void resetScheduleAutoHide() {
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final void setForceShowNavigationBarFlag(Context context, boolean z) {
    }
}
