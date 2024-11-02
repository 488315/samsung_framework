package com.samsung.systemui.splugins.navigationbar;

import android.content.Context;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes3.dex */
public interface ExtendableBar {
    void forceSetBackGesture(boolean z);

    int getBarDisplayId();

    BarLayoutParams getBarLayoutParamsProvider();

    ButtonDispatcherProxyBase getButtonDispatcherProxy();

    ColorSetting getDefaultColorProvider();

    IconThemeBase getDefaultIconTheme();

    LayoutProviderContainer getDefaultLayoutProviderContainer();

    int getDisabledFlags();

    FeatureChecker getFeatureChecker();

    NavBarStoreAdapter getNavBarStoreAdapter();

    Context getNavigationBarContext();

    TaskStackAdapterBase getTaskStackAdapter();

    boolean isCoverDisplayNavBarEnabled();

    boolean isCoverLargeScreenTaskEnabled();

    boolean isKeyguardShowing();

    boolean isTaskbarEnabled();

    void notifyForceImmersiveStateChanged();

    void registerKeyguardStateCallback();

    void resetScheduleAutoHide();

    void setBarLayoutParamsProvider(BarLayoutParams barLayoutParams);

    void setColorProvider(ColorSetting colorSetting, boolean z);

    void setDefaultBarLayoutParamsProvider();

    void setDefaultIconTheme(IconThemeBase iconThemeBase);

    void setForceShowNavigationBarFlag(Context context, boolean z);

    void setIconThemeAlpha(float f);

    void setLayoutProviderContainer(LayoutProviderContainer layoutProviderContainer);

    void setRotationLockAtAngle(boolean z, int i);

    void setRotationLocked(boolean z);

    void unregisterKeyguardStateCallback();

    void updateActiveIndicatorSpringParams(float f, float f2);

    void updateBackPanelColor(int i, int i2, int i3, int i4);

    void updateIconsAndHints(boolean z);

    void updateOpaqueColor(int i);

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes3.dex */
    public static final class DefaultImpls {
        public static boolean isCoverDisplayNavBarEnabled(ExtendableBar extendableBar) {
            return false;
        }

        public static boolean isCoverLargeScreenTaskEnabled(ExtendableBar extendableBar) {
            return false;
        }

        public static boolean isTaskbarEnabled(ExtendableBar extendableBar) {
            return false;
        }

        public static /* synthetic */ void updateBackPanelColor$default(ExtendableBar extendableBar, int i, int i2, int i3, int i4, int i5, Object obj) {
            if (obj == null) {
                if ((i5 & 1) != 0) {
                    i = 0;
                }
                if ((i5 & 2) != 0) {
                    i2 = 0;
                }
                if ((i5 & 4) != 0) {
                    i3 = 0;
                }
                if ((i5 & 8) != 0) {
                    i4 = 0;
                }
                extendableBar.updateBackPanelColor(i, i2, i3, i4);
                return;
            }
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: updateBackPanelColor");
        }

        public static void notifyForceImmersiveStateChanged(ExtendableBar extendableBar) {
        }

        public static void updateIconsAndHints(ExtendableBar extendableBar, boolean z) {
        }

        public static void updateActiveIndicatorSpringParams(ExtendableBar extendableBar, float f, float f2) {
        }

        public static void updateBackPanelColor(ExtendableBar extendableBar, int i, int i2, int i3, int i4) {
        }
    }
}
