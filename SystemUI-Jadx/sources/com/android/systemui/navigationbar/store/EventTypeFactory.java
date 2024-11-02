package com.android.systemui.navigationbar.store;

import android.content.Context;
import android.content.res.Configuration;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.widgets.ConstraintWidget$$ExternalSyntheticOutline0;
import androidx.picker.model.viewdata.AppInfoViewData$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.settingslib.udfps.UdfpsOverlayParams$$ExternalSyntheticOutline0;
import com.android.systemui.navigationbar.NavigationBar;
import com.android.systemui.navigationbar.NavigationBarTransitions;
import com.android.systemui.navigationbar.NavigationBarView;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.LightBarController;
import com.samsung.android.desktopmode.SemDesktopModeState;
import com.samsung.systemui.splugins.navigationbar.BarLayoutParams;
import com.samsung.systemui.splugins.navigationbar.LayoutProviderContainer;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class EventTypeFactory {
    public static final Companion Companion = new Companion(null);
    public static volatile EventTypeFactory INSTANCE;
    public final Context context;
    public final List updatableEvents = new ArrayList();

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public interface EventType {

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes.dex */
        public final class GetBarLayoutParams implements EventType {
            public final int rotation;

            public GetBarLayoutParams(int i) {
                this.rotation = i;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if ((obj instanceof GetBarLayoutParams) && this.rotation == ((GetBarLayoutParams) obj).rotation) {
                    return true;
                }
                return false;
            }

            public final int hashCode() {
                return Integer.hashCode(this.rotation);
            }

            public final String toString() {
                return ConstraintWidget$$ExternalSyntheticOutline0.m(new StringBuilder("GetBarLayoutParams(rotation="), this.rotation, ")");
            }
        }

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes.dex */
        public final class GetDeadZoneSize implements EventType {
            public final boolean maxSize;

            public GetDeadZoneSize(boolean z) {
                this.maxSize = z;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if ((obj instanceof GetDeadZoneSize) && this.maxSize == ((GetDeadZoneSize) obj).maxSize) {
                    return true;
                }
                return false;
            }

            public final int hashCode() {
                boolean z = this.maxSize;
                if (z) {
                    return 1;
                }
                return z ? 1 : 0;
            }

            public final String toString() {
                return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(new StringBuilder("GetDeadZoneSize(maxSize="), this.maxSize, ")");
            }
        }

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes.dex */
        public final class GetDefaultLayout implements EventType {
            public final boolean noArguments;

            public GetDefaultLayout() {
                this(false, 1, null);
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if ((obj instanceof GetDefaultLayout) && this.noArguments == ((GetDefaultLayout) obj).noArguments) {
                    return true;
                }
                return false;
            }

            public final int hashCode() {
                boolean z = this.noArguments;
                if (z) {
                    return 1;
                }
                return z ? 1 : 0;
            }

            public final String toString() {
                return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(new StringBuilder("GetDefaultLayout(noArguments="), this.noArguments, ")");
            }

            public GetDefaultLayout(boolean z) {
                this.noArguments = z;
            }

            public /* synthetic */ GetDefaultLayout(boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
                this((i & 1) != 0 ? true : z);
            }
        }

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes.dex */
        public final class GetImeInsets implements EventType {
            public final boolean canMove;
            public final int insetHeight;
            public final int insetWidth;
            public final int rotation;

            public GetImeInsets(int i, int i2, int i3, boolean z) {
                this.insetHeight = i;
                this.insetWidth = i2;
                this.rotation = i3;
                this.canMove = z;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof GetImeInsets)) {
                    return false;
                }
                GetImeInsets getImeInsets = (GetImeInsets) obj;
                if (this.insetHeight == getImeInsets.insetHeight && this.insetWidth == getImeInsets.insetWidth && this.rotation == getImeInsets.rotation && this.canMove == getImeInsets.canMove) {
                    return true;
                }
                return false;
            }

            /* JADX WARN: Multi-variable type inference failed */
            public final int hashCode() {
                int m = AppInfoViewData$$ExternalSyntheticOutline0.m(this.rotation, AppInfoViewData$$ExternalSyntheticOutline0.m(this.insetWidth, Integer.hashCode(this.insetHeight) * 31, 31), 31);
                boolean z = this.canMove;
                int i = z;
                if (z != 0) {
                    i = 1;
                }
                return m + i;
            }

            public final String toString() {
                StringBuilder sb = new StringBuilder("GetImeInsets(insetHeight=");
                sb.append(this.insetHeight);
                sb.append(", insetWidth=");
                sb.append(this.insetWidth);
                sb.append(", rotation=");
                sb.append(this.rotation);
                sb.append(", canMove=");
                return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(sb, this.canMove, ")");
            }
        }

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes.dex */
        public final class GetInflateButtonWidth implements EventType {
            public final String buttonSpec;
            public final boolean landscape;

            public GetInflateButtonWidth(String str, boolean z) {
                this.buttonSpec = str;
                this.landscape = z;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof GetInflateButtonWidth)) {
                    return false;
                }
                GetInflateButtonWidth getInflateButtonWidth = (GetInflateButtonWidth) obj;
                if (Intrinsics.areEqual(this.buttonSpec, getInflateButtonWidth.buttonSpec) && this.landscape == getInflateButtonWidth.landscape) {
                    return true;
                }
                return false;
            }

            /* JADX WARN: Multi-variable type inference failed */
            public final int hashCode() {
                int hashCode = this.buttonSpec.hashCode() * 31;
                boolean z = this.landscape;
                int i = z;
                if (z != 0) {
                    i = 1;
                }
                return hashCode + i;
            }

            public final String toString() {
                return "GetInflateButtonWidth(buttonSpec=" + this.buttonSpec + ", landscape=" + this.landscape + ")";
            }
        }

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes.dex */
        public final class GetInflateLayoutID implements EventType {
            public final boolean vertical;

            public GetInflateLayoutID(boolean z) {
                this.vertical = z;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if ((obj instanceof GetInflateLayoutID) && this.vertical == ((GetInflateLayoutID) obj).vertical) {
                    return true;
                }
                return false;
            }

            public final int hashCode() {
                boolean z = this.vertical;
                if (z) {
                    return 1;
                }
                return z ? 1 : 0;
            }

            public final String toString() {
                return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(new StringBuilder("GetInflateLayoutID(vertical="), this.vertical, ")");
            }
        }

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes.dex */
        public final class GetMandatoryInsets implements EventType {
            public final boolean canMove;
            public final int rotation;

            public GetMandatoryInsets(int i, boolean z) {
                this.rotation = i;
                this.canMove = z;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof GetMandatoryInsets)) {
                    return false;
                }
                GetMandatoryInsets getMandatoryInsets = (GetMandatoryInsets) obj;
                if (this.rotation == getMandatoryInsets.rotation && this.canMove == getMandatoryInsets.canMove) {
                    return true;
                }
                return false;
            }

            /* JADX WARN: Multi-variable type inference failed */
            public final int hashCode() {
                int hashCode = Integer.hashCode(this.rotation) * 31;
                boolean z = this.canMove;
                int i = z;
                if (z != 0) {
                    i = 1;
                }
                return hashCode + i;
            }

            public final String toString() {
                return "GetMandatoryInsets(rotation=" + this.rotation + ", canMove=" + this.canMove + ")";
            }
        }

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes.dex */
        public final class GetNavBarInsets implements EventType {
            public final int insetHeight;
            public final int insetWidth;
            public final int rotation;

            public GetNavBarInsets(int i, int i2, int i3) {
                this.insetHeight = i;
                this.insetWidth = i2;
                this.rotation = i3;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof GetNavBarInsets)) {
                    return false;
                }
                GetNavBarInsets getNavBarInsets = (GetNavBarInsets) obj;
                if (this.insetHeight == getNavBarInsets.insetHeight && this.insetWidth == getNavBarInsets.insetWidth && this.rotation == getNavBarInsets.rotation) {
                    return true;
                }
                return false;
            }

            public final int hashCode() {
                return Integer.hashCode(this.rotation) + AppInfoViewData$$ExternalSyntheticOutline0.m(this.insetWidth, Integer.hashCode(this.insetHeight) * 31, 31);
            }

            public final String toString() {
                StringBuilder sb = new StringBuilder("GetNavBarInsets(insetHeight=");
                sb.append(this.insetHeight);
                sb.append(", insetWidth=");
                sb.append(this.insetWidth);
                sb.append(", rotation=");
                return ConstraintWidget$$ExternalSyntheticOutline0.m(sb, this.rotation, ")");
            }
        }

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes.dex */
        public final class GetNavBarLargeCoverScreenPadding implements EventType {
            public final int rotation;

            public GetNavBarLargeCoverScreenPadding(int i) {
                this.rotation = i;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if ((obj instanceof GetNavBarLargeCoverScreenPadding) && this.rotation == ((GetNavBarLargeCoverScreenPadding) obj).rotation) {
                    return true;
                }
                return false;
            }

            public final int hashCode() {
                return Integer.hashCode(this.rotation);
            }

            public final String toString() {
                return ConstraintWidget$$ExternalSyntheticOutline0.m(new StringBuilder("GetNavBarLargeCoverScreenPadding(rotation="), this.rotation, ")");
            }
        }

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes.dex */
        public final class GetNavBarSidePadding implements EventType {
            public final boolean landscape;

            public GetNavBarSidePadding(boolean z) {
                this.landscape = z;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if ((obj instanceof GetNavBarSidePadding) && this.landscape == ((GetNavBarSidePadding) obj).landscape) {
                    return true;
                }
                return false;
            }

            public final int hashCode() {
                boolean z = this.landscape;
                if (z) {
                    return 1;
                }
                return z ? 1 : 0;
            }

            public final String toString() {
                return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(new StringBuilder("GetNavBarSidePadding(landscape="), this.landscape, ")");
            }
        }

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes.dex */
        public final class MoveBottomGestureHintDistance implements EventType {
            public final int distanceX;
            public final int distanceY;
            public final long duration;
            public final int hintId;

            public MoveBottomGestureHintDistance(int i, int i2, int i3, long j) {
                this.hintId = i;
                this.distanceX = i2;
                this.distanceY = i3;
                this.duration = j;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof MoveBottomGestureHintDistance)) {
                    return false;
                }
                MoveBottomGestureHintDistance moveBottomGestureHintDistance = (MoveBottomGestureHintDistance) obj;
                if (this.hintId == moveBottomGestureHintDistance.hintId && this.distanceX == moveBottomGestureHintDistance.distanceX && this.distanceY == moveBottomGestureHintDistance.distanceY && this.duration == moveBottomGestureHintDistance.duration) {
                    return true;
                }
                return false;
            }

            public final int hashCode() {
                return Long.hashCode(this.duration) + AppInfoViewData$$ExternalSyntheticOutline0.m(this.distanceY, AppInfoViewData$$ExternalSyntheticOutline0.m(this.distanceX, Integer.hashCode(this.hintId) * 31, 31), 31);
            }

            public final String toString() {
                return "MoveBottomGestureHintDistance(hintId=" + this.hintId + ", distanceX=" + this.distanceX + ", distanceY=" + this.distanceY + ", duration=" + this.duration + ")";
            }
        }

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes.dex */
        public final class OnBarLayoutParamsProviderChanged implements EventType {
            public final BarLayoutParams layoutParamsProvider;

            public OnBarLayoutParamsProviderChanged(BarLayoutParams barLayoutParams) {
                this.layoutParamsProvider = barLayoutParams;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if ((obj instanceof OnBarLayoutParamsProviderChanged) && Intrinsics.areEqual(this.layoutParamsProvider, ((OnBarLayoutParamsProviderChanged) obj).layoutParamsProvider)) {
                    return true;
                }
                return false;
            }

            public final int hashCode() {
                BarLayoutParams barLayoutParams = this.layoutParamsProvider;
                if (barLayoutParams == null) {
                    return 0;
                }
                return barLayoutParams.hashCode();
            }

            public final String toString() {
                return "OnBarLayoutParamsProviderChanged(layoutParamsProvider=" + this.layoutParamsProvider + ")";
            }
        }

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes.dex */
        public final class OnBottomSensitivityChanged implements EventType {
            public final boolean noArguments;

            public OnBottomSensitivityChanged() {
                this(false, 1, null);
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if ((obj instanceof OnBottomSensitivityChanged) && this.noArguments == ((OnBottomSensitivityChanged) obj).noArguments) {
                    return true;
                }
                return false;
            }

            public final int hashCode() {
                boolean z = this.noArguments;
                if (z) {
                    return 1;
                }
                return z ? 1 : 0;
            }

            public final String toString() {
                return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(new StringBuilder("OnBottomSensitivityChanged(noArguments="), this.noArguments, ")");
            }

            public OnBottomSensitivityChanged(boolean z) {
                this.noArguments = z;
            }

            public /* synthetic */ OnBottomSensitivityChanged(boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
                this((i & 1) != 0 ? true : z);
            }
        }

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes.dex */
        public final class OnButtonOrderChanged implements EventType {
            public final boolean noArguments;

            public OnButtonOrderChanged() {
                this(false, 1, null);
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if ((obj instanceof OnButtonOrderChanged) && this.noArguments == ((OnButtonOrderChanged) obj).noArguments) {
                    return true;
                }
                return false;
            }

            public final int hashCode() {
                boolean z = this.noArguments;
                if (z) {
                    return 1;
                }
                return z ? 1 : 0;
            }

            public final String toString() {
                return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(new StringBuilder("OnButtonOrderChanged(noArguments="), this.noArguments, ")");
            }

            public OnButtonOrderChanged(boolean z) {
                this.noArguments = z;
            }

            public /* synthetic */ OnButtonOrderChanged(boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
                this((i & 1) != 0 ? true : z);
            }
        }

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes.dex */
        public final class OnButtonPositionChanged implements EventType {
            public final boolean noArguments;

            public OnButtonPositionChanged() {
                this(false, 1, null);
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if ((obj instanceof OnButtonPositionChanged) && this.noArguments == ((OnButtonPositionChanged) obj).noArguments) {
                    return true;
                }
                return false;
            }

            public final int hashCode() {
                boolean z = this.noArguments;
                if (z) {
                    return 1;
                }
                return z ? 1 : 0;
            }

            public final String toString() {
                return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(new StringBuilder("OnButtonPositionChanged(noArguments="), this.noArguments, ")");
            }

            public OnButtonPositionChanged(boolean z) {
                this.noArguments = z;
            }

            public /* synthetic */ OnButtonPositionChanged(boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
                this((i & 1) != 0 ? true : z);
            }
        }

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes.dex */
        public final class OnButtonToHideKeyboardChanged implements EventType {
            public final boolean noArguments;

            public OnButtonToHideKeyboardChanged() {
                this(false, 1, null);
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if ((obj instanceof OnButtonToHideKeyboardChanged) && this.noArguments == ((OnButtonToHideKeyboardChanged) obj).noArguments) {
                    return true;
                }
                return false;
            }

            public final int hashCode() {
                boolean z = this.noArguments;
                if (z) {
                    return 1;
                }
                return z ? 1 : 0;
            }

            public final String toString() {
                return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(new StringBuilder("OnButtonToHideKeyboardChanged(noArguments="), this.noArguments, ")");
            }

            public OnButtonToHideKeyboardChanged(boolean z) {
                this.noArguments = z;
            }

            public /* synthetic */ OnButtonToHideKeyboardChanged(boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
                this((i & 1) != 0 ? true : z);
            }
        }

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes.dex */
        public final class OnConfigChanged implements EventType {
            public final Configuration newConfig;

            public OnConfigChanged(Configuration configuration) {
                this.newConfig = configuration;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if ((obj instanceof OnConfigChanged) && Intrinsics.areEqual(this.newConfig, ((OnConfigChanged) obj).newConfig)) {
                    return true;
                }
                return false;
            }

            public final int hashCode() {
                return this.newConfig.hashCode();
            }

            public final String toString() {
                return "OnConfigChanged(newConfig=" + this.newConfig + ")";
            }
        }

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes.dex */
        public final class OnCoverRotationChanged implements EventType {
            public final int rotation;

            public OnCoverRotationChanged() {
                this(0, 1, null);
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if ((obj instanceof OnCoverRotationChanged) && this.rotation == ((OnCoverRotationChanged) obj).rotation) {
                    return true;
                }
                return false;
            }

            public final int hashCode() {
                return Integer.hashCode(this.rotation);
            }

            public final String toString() {
                return ConstraintWidget$$ExternalSyntheticOutline0.m(new StringBuilder("OnCoverRotationChanged(rotation="), this.rotation, ")");
            }

            public OnCoverRotationChanged(int i) {
                this.rotation = i;
            }

            public /* synthetic */ OnCoverRotationChanged(int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
                this((i2 & 1) != 0 ? 0 : i);
            }
        }

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes.dex */
        public final class OnDesktopModeChanged implements EventType {
            public final SemDesktopModeState state;

            public OnDesktopModeChanged(SemDesktopModeState semDesktopModeState) {
                this.state = semDesktopModeState;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if ((obj instanceof OnDesktopModeChanged) && Intrinsics.areEqual(this.state, ((OnDesktopModeChanged) obj).state)) {
                    return true;
                }
                return false;
            }

            public final int hashCode() {
                SemDesktopModeState semDesktopModeState = this.state;
                if (semDesktopModeState == null) {
                    return 0;
                }
                return semDesktopModeState.hashCode();
            }

            public final String toString() {
                return "OnDesktopModeChanged(state=" + this.state + ")";
            }
        }

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes.dex */
        public final class OnDeviceProvisionedChanged implements EventType {
            public final boolean provisioned;

            public OnDeviceProvisionedChanged(boolean z) {
                this.provisioned = z;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if ((obj instanceof OnDeviceProvisionedChanged) && this.provisioned == ((OnDeviceProvisionedChanged) obj).provisioned) {
                    return true;
                }
                return false;
            }

            public final int hashCode() {
                boolean z = this.provisioned;
                if (z) {
                    return 1;
                }
                return z ? 1 : 0;
            }

            public final String toString() {
                return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(new StringBuilder("OnDeviceProvisionedChanged(provisioned="), this.provisioned, ")");
            }
        }

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes.dex */
        public final class OnEdgeBackGestureDisabledByPolicyChanged implements EventType {
            public final boolean disabled;

            public OnEdgeBackGestureDisabledByPolicyChanged(boolean z) {
                this.disabled = z;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if ((obj instanceof OnEdgeBackGestureDisabledByPolicyChanged) && this.disabled == ((OnEdgeBackGestureDisabledByPolicyChanged) obj).disabled) {
                    return true;
                }
                return false;
            }

            public final int hashCode() {
                boolean z = this.disabled;
                if (z) {
                    return 1;
                }
                return z ? 1 : 0;
            }

            public final String toString() {
                return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(new StringBuilder("OnEdgeBackGestureDisabledByPolicyChanged(disabled="), this.disabled, ")");
            }
        }

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes.dex */
        public final class OnFakeNavBarEventOccurred implements EventType {
            public final boolean noArguments;

            public OnFakeNavBarEventOccurred() {
                this(false, 1, null);
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if ((obj instanceof OnFakeNavBarEventOccurred) && this.noArguments == ((OnFakeNavBarEventOccurred) obj).noArguments) {
                    return true;
                }
                return false;
            }

            public final int hashCode() {
                boolean z = this.noArguments;
                if (z) {
                    return 1;
                }
                return z ? 1 : 0;
            }

            public final String toString() {
                return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(new StringBuilder("OnFakeNavBarEventOccurred(noArguments="), this.noArguments, ")");
            }

            public OnFakeNavBarEventOccurred(boolean z) {
                this.noArguments = z;
            }

            public /* synthetic */ OnFakeNavBarEventOccurred(boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
                this((i & 1) != 0 ? true : z);
            }
        }

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes.dex */
        public final class OnFoldStateChanged implements EventType {
            public final boolean folded;

            public OnFoldStateChanged(boolean z) {
                this.folded = z;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if ((obj instanceof OnFoldStateChanged) && this.folded == ((OnFoldStateChanged) obj).folded) {
                    return true;
                }
                return false;
            }

            public final int hashCode() {
                boolean z = this.folded;
                if (z) {
                    return 1;
                }
                return z ? 1 : 0;
            }

            public final String toString() {
                return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(new StringBuilder("OnFoldStateChanged(folded="), this.folded, ")");
            }
        }

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes.dex */
        public final class OnHardKeyIntentPolicyChanged implements EventType {
            public final boolean intentStatus;

            public OnHardKeyIntentPolicyChanged(boolean z) {
                this.intentStatus = z;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if ((obj instanceof OnHardKeyIntentPolicyChanged) && this.intentStatus == ((OnHardKeyIntentPolicyChanged) obj).intentStatus) {
                    return true;
                }
                return false;
            }

            public final int hashCode() {
                boolean z = this.intentStatus;
                if (z) {
                    return 1;
                }
                return z ? 1 : 0;
            }

            public final String toString() {
                return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(new StringBuilder("OnHardKeyIntentPolicyChanged(intentStatus="), this.intentStatus, ")");
            }
        }

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes.dex */
        public final class OnInvalidateRemoteViews implements EventType {
            public final boolean noArguments;

            public OnInvalidateRemoteViews() {
                this(false, 1, null);
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if ((obj instanceof OnInvalidateRemoteViews) && this.noArguments == ((OnInvalidateRemoteViews) obj).noArguments) {
                    return true;
                }
                return false;
            }

            public final int hashCode() {
                boolean z = this.noArguments;
                if (z) {
                    return 1;
                }
                return z ? 1 : 0;
            }

            public final String toString() {
                return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(new StringBuilder("OnInvalidateRemoteViews(noArguments="), this.noArguments, ")");
            }

            public OnInvalidateRemoteViews(boolean z) {
                this.noArguments = z;
            }

            public /* synthetic */ OnInvalidateRemoteViews(boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
                this((i & 1) != 0 ? true : z);
            }
        }

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes.dex */
        public final class OnLayoutContainerChanged implements EventType {
            public final LayoutProviderContainer layoutProviderContainer;

            public OnLayoutContainerChanged(LayoutProviderContainer layoutProviderContainer) {
                this.layoutProviderContainer = layoutProviderContainer;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if ((obj instanceof OnLayoutContainerChanged) && Intrinsics.areEqual(this.layoutProviderContainer, ((OnLayoutContainerChanged) obj).layoutProviderContainer)) {
                    return true;
                }
                return false;
            }

            public final int hashCode() {
                return this.layoutProviderContainer.hashCode();
            }

            public final String toString() {
                return "OnLayoutContainerChanged(layoutProviderContainer=" + this.layoutProviderContainer + ")";
            }
        }

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes.dex */
        public final class OnLightBarControllerCreated implements EventType {
            public final LightBarController lightBarController;

            public OnLightBarControllerCreated(LightBarController lightBarController) {
                this.lightBarController = lightBarController;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if ((obj instanceof OnLightBarControllerCreated) && Intrinsics.areEqual(this.lightBarController, ((OnLightBarControllerCreated) obj).lightBarController)) {
                    return true;
                }
                return false;
            }

            public final int hashCode() {
                return this.lightBarController.hashCode();
            }

            public final String toString() {
                return "OnLightBarControllerCreated(lightBarController=" + this.lightBarController + ")";
            }
        }

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes.dex */
        public final class OnNavBarAttachedToWindow implements EventType {
            public final NavigationBarTransitions navbarTransitions;
            public final NavigationBarView navigationBarView;

            public OnNavBarAttachedToWindow(NavigationBarView navigationBarView, NavigationBarTransitions navigationBarTransitions) {
                this.navigationBarView = navigationBarView;
                this.navbarTransitions = navigationBarTransitions;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof OnNavBarAttachedToWindow)) {
                    return false;
                }
                OnNavBarAttachedToWindow onNavBarAttachedToWindow = (OnNavBarAttachedToWindow) obj;
                if (Intrinsics.areEqual(this.navigationBarView, onNavBarAttachedToWindow.navigationBarView) && Intrinsics.areEqual(this.navbarTransitions, onNavBarAttachedToWindow.navbarTransitions)) {
                    return true;
                }
                return false;
            }

            public final int hashCode() {
                return this.navbarTransitions.hashCode() + (this.navigationBarView.hashCode() * 31);
            }

            public final String toString() {
                return "OnNavBarAttachedToWindow(navigationBarView=" + this.navigationBarView + ", navbarTransitions=" + this.navbarTransitions + ")";
            }
        }

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes.dex */
        public final class OnNavBarButtonForcedVisibleChanged implements EventType {
            public final boolean noArguments;

            public OnNavBarButtonForcedVisibleChanged() {
                this(false, 1, null);
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if ((obj instanceof OnNavBarButtonForcedVisibleChanged) && this.noArguments == ((OnNavBarButtonForcedVisibleChanged) obj).noArguments) {
                    return true;
                }
                return false;
            }

            public final int hashCode() {
                boolean z = this.noArguments;
                if (z) {
                    return 1;
                }
                return z ? 1 : 0;
            }

            public final String toString() {
                return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(new StringBuilder("OnNavBarButtonForcedVisibleChanged(noArguments="), this.noArguments, ")");
            }

            public OnNavBarButtonForcedVisibleChanged(boolean z) {
                this.noArguments = z;
            }

            public /* synthetic */ OnNavBarButtonForcedVisibleChanged(boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
                this((i & 1) != 0 ? true : z);
            }
        }

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes.dex */
        public final class OnNavBarConfigChanged implements EventType {
            public boolean canMove;
            public final boolean imeDownButtonForAllRotation;
            public final int navigationMode;
            public boolean supportPhoneLayoutProvider;

            public OnNavBarConfigChanged() {
                this(false, false, false, 0, 15, null);
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof OnNavBarConfigChanged)) {
                    return false;
                }
                OnNavBarConfigChanged onNavBarConfigChanged = (OnNavBarConfigChanged) obj;
                if (this.canMove == onNavBarConfigChanged.canMove && this.supportPhoneLayoutProvider == onNavBarConfigChanged.supportPhoneLayoutProvider && this.imeDownButtonForAllRotation == onNavBarConfigChanged.imeDownButtonForAllRotation && this.navigationMode == onNavBarConfigChanged.navigationMode) {
                    return true;
                }
                return false;
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r0v1, types: [int] */
            /* JADX WARN: Type inference failed for: r0v7 */
            /* JADX WARN: Type inference failed for: r0v8 */
            /* JADX WARN: Type inference failed for: r2v0, types: [boolean] */
            public final int hashCode() {
                boolean z = this.canMove;
                int i = 1;
                ?? r0 = z;
                if (z) {
                    r0 = 1;
                }
                int i2 = r0 * 31;
                ?? r2 = this.supportPhoneLayoutProvider;
                int i3 = r2;
                if (r2 != 0) {
                    i3 = 1;
                }
                int i4 = (i2 + i3) * 31;
                boolean z2 = this.imeDownButtonForAllRotation;
                if (!z2) {
                    i = z2 ? 1 : 0;
                }
                return Integer.hashCode(this.navigationMode) + ((i4 + i) * 31);
            }

            public final String toString() {
                StringBuilder m = KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("OnNavBarConfigChanged(canMove=", this.canMove, ", supportPhoneLayoutProvider=", this.supportPhoneLayoutProvider, ", imeDownButtonForAllRotation=");
                m.append(this.imeDownButtonForAllRotation);
                m.append(", navigationMode=");
                return ConstraintWidget$$ExternalSyntheticOutline0.m(m, this.navigationMode, ")");
            }

            public OnNavBarConfigChanged(boolean z, boolean z2, boolean z3, int i) {
                this.canMove = z;
                this.supportPhoneLayoutProvider = z2;
                this.imeDownButtonForAllRotation = z3;
                this.navigationMode = i;
            }

            public /* synthetic */ OnNavBarConfigChanged(boolean z, boolean z2, boolean z3, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
                this((i2 & 1) != 0 ? true : z, (i2 & 2) != 0 ? true : z2, (i2 & 4) != 0 ? false : z3, (i2 & 8) != 0 ? 0 : i);
            }
        }

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes.dex */
        public final class OnNavBarCreated implements EventType {
            public final CentralSurfaces centralSurfaces;
            public final NavigationBar navigationBar;

            public OnNavBarCreated(CentralSurfaces centralSurfaces, NavigationBar navigationBar) {
                this.centralSurfaces = centralSurfaces;
                this.navigationBar = navigationBar;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof OnNavBarCreated)) {
                    return false;
                }
                OnNavBarCreated onNavBarCreated = (OnNavBarCreated) obj;
                if (Intrinsics.areEqual(this.centralSurfaces, onNavBarCreated.centralSurfaces) && Intrinsics.areEqual(this.navigationBar, onNavBarCreated.navigationBar)) {
                    return true;
                }
                return false;
            }

            public final int hashCode() {
                return this.navigationBar.hashCode() + (this.centralSurfaces.hashCode() * 31);
            }

            public final String toString() {
                return "OnNavBarCreated(centralSurfaces=" + this.centralSurfaces + ", navigationBar=" + this.navigationBar + ")";
            }
        }

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes.dex */
        public final class OnNavBarDetachedFromWindow implements EventType {
            public final boolean noArguments;

            public OnNavBarDetachedFromWindow() {
                this(false, 1, null);
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if ((obj instanceof OnNavBarDetachedFromWindow) && this.noArguments == ((OnNavBarDetachedFromWindow) obj).noArguments) {
                    return true;
                }
                return false;
            }

            public final int hashCode() {
                boolean z = this.noArguments;
                if (z) {
                    return 1;
                }
                return z ? 1 : 0;
            }

            public final String toString() {
                return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(new StringBuilder("OnNavBarDetachedFromWindow(noArguments="), this.noArguments, ")");
            }

            public OnNavBarDetachedFromWindow(boolean z) {
                this.noArguments = z;
            }

            public /* synthetic */ OnNavBarDetachedFromWindow(boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
                this((i & 1) != 0 ? true : z);
            }
        }

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes.dex */
        public final class OnNavBarIconHintChanged implements EventType {
            public final int iconHint;

            public OnNavBarIconHintChanged(int i) {
                this.iconHint = i;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if ((obj instanceof OnNavBarIconHintChanged) && this.iconHint == ((OnNavBarIconHintChanged) obj).iconHint) {
                    return true;
                }
                return false;
            }

            public final int hashCode() {
                return Integer.hashCode(this.iconHint);
            }

            public final String toString() {
                return ConstraintWidget$$ExternalSyntheticOutline0.m(new StringBuilder("OnNavBarIconHintChanged(iconHint="), this.iconHint, ")");
            }
        }

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes.dex */
        public final class OnNavBarIconMarquee implements EventType {
            public final boolean noArguments;

            public OnNavBarIconMarquee() {
                this(false, 1, null);
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if ((obj instanceof OnNavBarIconMarquee) && this.noArguments == ((OnNavBarIconMarquee) obj).noArguments) {
                    return true;
                }
                return false;
            }

            public final int hashCode() {
                boolean z = this.noArguments;
                if (z) {
                    return 1;
                }
                return z ? 1 : 0;
            }

            public final String toString() {
                return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(new StringBuilder("OnNavBarIconMarquee(noArguments="), this.noArguments, ")");
            }

            public OnNavBarIconMarquee(boolean z) {
                this.noArguments = z;
            }

            public /* synthetic */ OnNavBarIconMarquee(boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
                this((i & 1) != 0 ? true : z);
            }
        }

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes.dex */
        public final class OnNavBarKnoxPolicyChanged implements EventType {
            public final boolean noArguments;

            public OnNavBarKnoxPolicyChanged() {
                this(false, 1, null);
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if ((obj instanceof OnNavBarKnoxPolicyChanged) && this.noArguments == ((OnNavBarKnoxPolicyChanged) obj).noArguments) {
                    return true;
                }
                return false;
            }

            public final int hashCode() {
                boolean z = this.noArguments;
                if (z) {
                    return 1;
                }
                return z ? 1 : 0;
            }

            public final String toString() {
                return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(new StringBuilder("OnNavBarKnoxPolicyChanged(noArguments="), this.noArguments, ")");
            }

            public OnNavBarKnoxPolicyChanged(boolean z) {
                this.noArguments = z;
            }

            public /* synthetic */ OnNavBarKnoxPolicyChanged(boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
                this((i & 1) != 0 ? true : z);
            }
        }

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes.dex */
        public final class OnNavBarLargeCoverScreenVisibilityChanged implements EventType {
            public final boolean coverTask;
            public final boolean imeShown;

            public OnNavBarLargeCoverScreenVisibilityChanged(boolean z, boolean z2) {
                this.imeShown = z;
                this.coverTask = z2;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof OnNavBarLargeCoverScreenVisibilityChanged)) {
                    return false;
                }
                OnNavBarLargeCoverScreenVisibilityChanged onNavBarLargeCoverScreenVisibilityChanged = (OnNavBarLargeCoverScreenVisibilityChanged) obj;
                if (this.imeShown == onNavBarLargeCoverScreenVisibilityChanged.imeShown && this.coverTask == onNavBarLargeCoverScreenVisibilityChanged.coverTask) {
                    return true;
                }
                return false;
            }

            /* JADX WARN: Multi-variable type inference failed */
            public final int hashCode() {
                int i = 1;
                boolean z = this.imeShown;
                int i2 = z;
                if (z != 0) {
                    i2 = 1;
                }
                int i3 = i2 * 31;
                boolean z2 = this.coverTask;
                if (!z2) {
                    i = z2 ? 1 : 0;
                }
                return i3 + i;
            }

            public final String toString() {
                return "OnNavBarLargeCoverScreenVisibilityChanged(imeShown=" + this.imeShown + ", coverTask=" + this.coverTask + ")";
            }
        }

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes.dex */
        public final class OnNavBarStyleChanged implements EventType {
            public final boolean noArguments;

            public OnNavBarStyleChanged() {
                this(false, 1, null);
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if ((obj instanceof OnNavBarStyleChanged) && this.noArguments == ((OnNavBarStyleChanged) obj).noArguments) {
                    return true;
                }
                return false;
            }

            public final int hashCode() {
                boolean z = this.noArguments;
                if (z) {
                    return 1;
                }
                return z ? 1 : 0;
            }

            public final String toString() {
                return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(new StringBuilder("OnNavBarStyleChanged(noArguments="), this.noArguments, ")");
            }

            public OnNavBarStyleChanged(boolean z) {
                this.noArguments = z;
            }

            public /* synthetic */ OnNavBarStyleChanged(boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
                this((i & 1) != 0 ? true : z);
            }
        }

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes.dex */
        public final class OnNavBarTransitionModeChanged implements EventType {
            public final int transitionMode;

            public OnNavBarTransitionModeChanged(int i) {
                this.transitionMode = i;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if ((obj instanceof OnNavBarTransitionModeChanged) && this.transitionMode == ((OnNavBarTransitionModeChanged) obj).transitionMode) {
                    return true;
                }
                return false;
            }

            public final int hashCode() {
                return Integer.hashCode(this.transitionMode);
            }

            public final String toString() {
                return ConstraintWidget$$ExternalSyntheticOutline0.m(new StringBuilder("OnNavBarTransitionModeChanged(transitionMode="), this.transitionMode, ")");
            }
        }

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes.dex */
        public final class OnNavBarUpdateA11YService implements EventType {
            public final boolean clickable;
            public final boolean longClickable;

            public OnNavBarUpdateA11YService(boolean z, boolean z2) {
                this.clickable = z;
                this.longClickable = z2;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof OnNavBarUpdateA11YService)) {
                    return false;
                }
                OnNavBarUpdateA11YService onNavBarUpdateA11YService = (OnNavBarUpdateA11YService) obj;
                if (this.clickable == onNavBarUpdateA11YService.clickable && this.longClickable == onNavBarUpdateA11YService.longClickable) {
                    return true;
                }
                return false;
            }

            /* JADX WARN: Multi-variable type inference failed */
            public final int hashCode() {
                int i = 1;
                boolean z = this.clickable;
                int i2 = z;
                if (z != 0) {
                    i2 = 1;
                }
                int i3 = i2 * 31;
                boolean z2 = this.longClickable;
                if (!z2) {
                    i = z2 ? 1 : 0;
                }
                return i3 + i;
            }

            public final String toString() {
                return "OnNavBarUpdateA11YService(clickable=" + this.clickable + ", longClickable=" + this.longClickable + ")";
            }
        }

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes.dex */
        public final class OnNavBarWindowStateHidden implements EventType {
            public final boolean noArguments;

            public OnNavBarWindowStateHidden() {
                this(false, 1, null);
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if ((obj instanceof OnNavBarWindowStateHidden) && this.noArguments == ((OnNavBarWindowStateHidden) obj).noArguments) {
                    return true;
                }
                return false;
            }

            public final int hashCode() {
                boolean z = this.noArguments;
                if (z) {
                    return 1;
                }
                return z ? 1 : 0;
            }

            public final String toString() {
                return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(new StringBuilder("OnNavBarWindowStateHidden(noArguments="), this.noArguments, ")");
            }

            public OnNavBarWindowStateHidden(boolean z) {
                this.noArguments = z;
            }

            public /* synthetic */ OnNavBarWindowStateHidden(boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
                this((i & 1) != 0 ? true : z);
            }
        }

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes.dex */
        public final class OnNavBarWindowStateShowing implements EventType {
            public final boolean noArguments;

            public OnNavBarWindowStateShowing() {
                this(false, 1, null);
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if ((obj instanceof OnNavBarWindowStateShowing) && this.noArguments == ((OnNavBarWindowStateShowing) obj).noArguments) {
                    return true;
                }
                return false;
            }

            public final int hashCode() {
                boolean z = this.noArguments;
                if (z) {
                    return 1;
                }
                return z ? 1 : 0;
            }

            public final String toString() {
                return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(new StringBuilder("OnNavBarWindowStateShowing(noArguments="), this.noArguments, ")");
            }

            public OnNavBarWindowStateShowing(boolean z) {
                this.noArguments = z;
            }

            public /* synthetic */ OnNavBarWindowStateShowing(boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
                this((i & 1) != 0 ? true : z);
            }
        }

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes.dex */
        public final class OnNewDexModeChanged implements EventType {
            public final boolean enabled;

            public OnNewDexModeChanged(boolean z) {
                this.enabled = z;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if ((obj instanceof OnNewDexModeChanged) && this.enabled == ((OnNewDexModeChanged) obj).enabled) {
                    return true;
                }
                return false;
            }

            public final int hashCode() {
                boolean z = this.enabled;
                if (z) {
                    return 1;
                }
                return z ? 1 : 0;
            }

            public final String toString() {
                return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(new StringBuilder("OnNewDexModeChanged(enabled="), this.enabled, ")");
            }
        }

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes.dex */
        public final class OnOneHandModeChanged implements EventType {
            public final String info;

            public OnOneHandModeChanged(String str) {
                this.info = str;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if ((obj instanceof OnOneHandModeChanged) && Intrinsics.areEqual(this.info, ((OnOneHandModeChanged) obj).info)) {
                    return true;
                }
                return false;
            }

            public final int hashCode() {
                String str = this.info;
                if (str == null) {
                    return 0;
                }
                return str.hashCode();
            }

            public final String toString() {
                return AbstractResolvableFuture$$ExternalSyntheticOutline0.m(new StringBuilder("OnOneHandModeChanged(info="), this.info, ")");
            }
        }

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes.dex */
        public final class OnOpenThemeChanged implements EventType {
            public final boolean noArguments;

            public OnOpenThemeChanged() {
                this(false, 1, null);
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if ((obj instanceof OnOpenThemeChanged) && this.noArguments == ((OnOpenThemeChanged) obj).noArguments) {
                    return true;
                }
                return false;
            }

            public final int hashCode() {
                boolean z = this.noArguments;
                if (z) {
                    return 1;
                }
                return z ? 1 : 0;
            }

            public final String toString() {
                return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(new StringBuilder("OnOpenThemeChanged(noArguments="), this.noArguments, ")");
            }

            public OnOpenThemeChanged(boolean z) {
                this.noArguments = z;
            }

            public /* synthetic */ OnOpenThemeChanged(boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
                this((i & 1) != 0 ? true : z);
            }
        }

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes.dex */
        public final class OnPackageRemoved implements EventType {
            public final String packageName;

            public OnPackageRemoved(String str) {
                this.packageName = str;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if ((obj instanceof OnPackageRemoved) && Intrinsics.areEqual(this.packageName, ((OnPackageRemoved) obj).packageName)) {
                    return true;
                }
                return false;
            }

            public final int hashCode() {
                return this.packageName.hashCode();
            }

            public final String toString() {
                return AbstractResolvableFuture$$ExternalSyntheticOutline0.m(new StringBuilder("OnPackageRemoved(packageName="), this.packageName, ")");
            }
        }

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes.dex */
        public final class OnRotationChanged implements EventType {
            public final int rotation;

            public OnRotationChanged(int i) {
                this.rotation = i;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if ((obj instanceof OnRotationChanged) && this.rotation == ((OnRotationChanged) obj).rotation) {
                    return true;
                }
                return false;
            }

            public final int hashCode() {
                return Integer.hashCode(this.rotation);
            }

            public final String toString() {
                return ConstraintWidget$$ExternalSyntheticOutline0.m(new StringBuilder("OnRotationChanged(rotation="), this.rotation, ")");
            }
        }

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes.dex */
        public final class OnRotationLockedChanged implements EventType {
            public final boolean rotationLocked;

            public OnRotationLockedChanged(boolean z) {
                this.rotationLocked = z;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if ((obj instanceof OnRotationLockedChanged) && this.rotationLocked == ((OnRotationLockedChanged) obj).rotationLocked) {
                    return true;
                }
                return false;
            }

            public final int hashCode() {
                boolean z = this.rotationLocked;
                if (z) {
                    return 1;
                }
                return z ? 1 : 0;
            }

            public final String toString() {
                return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(new StringBuilder("OnRotationLockedChanged(rotationLocked="), this.rotationLocked, ")");
            }
        }

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes.dex */
        public final class OnSetDisableFlags implements EventType {
            public final int disable1;
            public final int disable2;

            public OnSetDisableFlags(int i, int i2) {
                this.disable1 = i;
                this.disable2 = i2;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof OnSetDisableFlags)) {
                    return false;
                }
                OnSetDisableFlags onSetDisableFlags = (OnSetDisableFlags) obj;
                if (this.disable1 == onSetDisableFlags.disable1 && this.disable2 == onSetDisableFlags.disable2) {
                    return true;
                }
                return false;
            }

            public final int hashCode() {
                return Integer.hashCode(this.disable2) + (Integer.hashCode(this.disable1) * 31);
            }

            public final String toString() {
                StringBuilder sb = new StringBuilder("OnSetDisableFlags(disable1=");
                sb.append(this.disable1);
                sb.append(", disable2=");
                return ConstraintWidget$$ExternalSyntheticOutline0.m(sb, this.disable2, ")");
            }
        }

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes.dex */
        public final class OnSetGestureHintVisibility implements EventType {
            public final boolean backVisible;
            public final boolean homeVisible;
            public final boolean recentVisible;

            public OnSetGestureHintVisibility(boolean z, boolean z2, boolean z3) {
                this.recentVisible = z;
                this.homeVisible = z2;
                this.backVisible = z3;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof OnSetGestureHintVisibility)) {
                    return false;
                }
                OnSetGestureHintVisibility onSetGestureHintVisibility = (OnSetGestureHintVisibility) obj;
                if (this.recentVisible == onSetGestureHintVisibility.recentVisible && this.homeVisible == onSetGestureHintVisibility.homeVisible && this.backVisible == onSetGestureHintVisibility.backVisible) {
                    return true;
                }
                return false;
            }

            /* JADX WARN: Multi-variable type inference failed */
            public final int hashCode() {
                int i = 1;
                boolean z = this.recentVisible;
                int i2 = z;
                if (z != 0) {
                    i2 = 1;
                }
                int i3 = i2 * 31;
                boolean z2 = this.homeVisible;
                int i4 = z2;
                if (z2 != 0) {
                    i4 = 1;
                }
                int i5 = (i3 + i4) * 31;
                boolean z3 = this.backVisible;
                if (!z3) {
                    i = z3 ? 1 : 0;
                }
                return i5 + i;
            }

            public final String toString() {
                StringBuilder sb = new StringBuilder("OnSetGestureHintVisibility(recentVisible=");
                sb.append(this.recentVisible);
                sb.append(", homeVisible=");
                sb.append(this.homeVisible);
                sb.append(", backVisible=");
                return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(sb, this.backVisible, ")");
            }
        }

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes.dex */
        public final class OnSetRemoteView implements EventType {
            public final int position;
            public final int priority;
            public final RemoteViews remoteViews;
            public final String requestClass;

            public OnSetRemoteView(String str, RemoteViews remoteViews, int i, int i2) {
                this.requestClass = str;
                this.remoteViews = remoteViews;
                this.position = i;
                this.priority = i2;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof OnSetRemoteView)) {
                    return false;
                }
                OnSetRemoteView onSetRemoteView = (OnSetRemoteView) obj;
                if (Intrinsics.areEqual(this.requestClass, onSetRemoteView.requestClass) && Intrinsics.areEqual(this.remoteViews, onSetRemoteView.remoteViews) && this.position == onSetRemoteView.position && this.priority == onSetRemoteView.priority) {
                    return true;
                }
                return false;
            }

            public final int hashCode() {
                int hashCode;
                int i = 0;
                String str = this.requestClass;
                if (str == null) {
                    hashCode = 0;
                } else {
                    hashCode = str.hashCode();
                }
                int i2 = hashCode * 31;
                RemoteViews remoteViews = this.remoteViews;
                if (remoteViews != null) {
                    i = remoteViews.hashCode();
                }
                return Integer.hashCode(this.priority) + AppInfoViewData$$ExternalSyntheticOutline0.m(this.position, (i2 + i) * 31, 31);
            }

            public final String toString() {
                return "OnSetRemoteView(requestClass=" + this.requestClass + ", remoteViews=" + this.remoteViews + ", position=" + this.position + ", priority=" + this.priority + ")";
            }
        }

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes.dex */
        public final class OnSettingsSoftReset implements EventType {
            public final boolean noArguments;

            public OnSettingsSoftReset() {
                this(false, 1, null);
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if ((obj instanceof OnSettingsSoftReset) && this.noArguments == ((OnSettingsSoftReset) obj).noArguments) {
                    return true;
                }
                return false;
            }

            public final int hashCode() {
                boolean z = this.noArguments;
                if (z) {
                    return 1;
                }
                return z ? 1 : 0;
            }

            public final String toString() {
                return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(new StringBuilder("OnSettingsSoftReset(noArguments="), this.noArguments, ")");
            }

            public OnSettingsSoftReset(boolean z) {
                this.noArguments = z;
            }

            public /* synthetic */ OnSettingsSoftReset(boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
                this((i & 1) != 0 ? true : z);
            }
        }

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes.dex */
        public final class OnShowA11YSwipeUpTipPopup implements EventType {
            public final boolean noArguments;

            public OnShowA11YSwipeUpTipPopup() {
                this(false, 1, null);
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if ((obj instanceof OnShowA11YSwipeUpTipPopup) && this.noArguments == ((OnShowA11YSwipeUpTipPopup) obj).noArguments) {
                    return true;
                }
                return false;
            }

            public final int hashCode() {
                boolean z = this.noArguments;
                if (z) {
                    return 1;
                }
                return z ? 1 : 0;
            }

            public final String toString() {
                return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(new StringBuilder("OnShowA11YSwipeUpTipPopup(noArguments="), this.noArguments, ")");
            }

            public OnShowA11YSwipeUpTipPopup(boolean z) {
                this.noArguments = z;
            }

            public /* synthetic */ OnShowA11YSwipeUpTipPopup(boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
                this((i & 1) != 0 ? true : z);
            }
        }

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes.dex */
        public final class OnTaskbarAttachedToWindow implements EventType {
            public final boolean noArguments;

            public OnTaskbarAttachedToWindow() {
                this(false, 1, null);
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if ((obj instanceof OnTaskbarAttachedToWindow) && this.noArguments == ((OnTaskbarAttachedToWindow) obj).noArguments) {
                    return true;
                }
                return false;
            }

            public final int hashCode() {
                boolean z = this.noArguments;
                if (z) {
                    return 1;
                }
                return z ? 1 : 0;
            }

            public final String toString() {
                return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(new StringBuilder("OnTaskbarAttachedToWindow(noArguments="), this.noArguments, ")");
            }

            public OnTaskbarAttachedToWindow(boolean z) {
                this.noArguments = z;
            }

            public /* synthetic */ OnTaskbarAttachedToWindow(boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
                this((i & 1) != 0 ? true : z);
            }
        }

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes.dex */
        public final class OnTaskbarDetachedFromWindow implements EventType {
            public final boolean noArguments;

            public OnTaskbarDetachedFromWindow() {
                this(false, 1, null);
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if ((obj instanceof OnTaskbarDetachedFromWindow) && this.noArguments == ((OnTaskbarDetachedFromWindow) obj).noArguments) {
                    return true;
                }
                return false;
            }

            public final int hashCode() {
                boolean z = this.noArguments;
                if (z) {
                    return 1;
                }
                return z ? 1 : 0;
            }

            public final String toString() {
                return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(new StringBuilder("OnTaskbarDetachedFromWindow(noArguments="), this.noArguments, ")");
            }

            public OnTaskbarDetachedFromWindow(boolean z) {
                this.noArguments = z;
            }

            public /* synthetic */ OnTaskbarDetachedFromWindow(boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
                this((i & 1) != 0 ? true : z);
            }
        }

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes.dex */
        public final class OnUpdateBackGestureActiveIndicatorParams implements EventType {
            public final float dampingRatio;
            public final float stiffness;

            public OnUpdateBackGestureActiveIndicatorParams(float f, float f2) {
                this.stiffness = f;
                this.dampingRatio = f2;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof OnUpdateBackGestureActiveIndicatorParams)) {
                    return false;
                }
                OnUpdateBackGestureActiveIndicatorParams onUpdateBackGestureActiveIndicatorParams = (OnUpdateBackGestureActiveIndicatorParams) obj;
                if (Float.compare(this.stiffness, onUpdateBackGestureActiveIndicatorParams.stiffness) == 0 && Float.compare(this.dampingRatio, onUpdateBackGestureActiveIndicatorParams.dampingRatio) == 0) {
                    return true;
                }
                return false;
            }

            public final int hashCode() {
                return Float.hashCode(this.dampingRatio) + (Float.hashCode(this.stiffness) * 31);
            }

            public final String toString() {
                return "OnUpdateBackGestureActiveIndicatorParams(stiffness=" + this.stiffness + ", dampingRatio=" + this.dampingRatio + ")";
            }
        }

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes.dex */
        public final class OnUpdateDarkIntensity implements EventType {
            public final float darkIntensity;

            public OnUpdateDarkIntensity(float f) {
                this.darkIntensity = f;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if ((obj instanceof OnUpdateDarkIntensity) && Float.compare(this.darkIntensity, ((OnUpdateDarkIntensity) obj).darkIntensity) == 0) {
                    return true;
                }
                return false;
            }

            public final int hashCode() {
                return Float.hashCode(this.darkIntensity);
            }

            public final String toString() {
                return "OnUpdateDarkIntensity(darkIntensity=" + this.darkIntensity + ")";
            }
        }

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes.dex */
        public final class OnUpdateNavBarVisibility implements EventType {
            public final int visibility;

            public OnUpdateNavBarVisibility(int i) {
                this.visibility = i;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if ((obj instanceof OnUpdateNavBarVisibility) && this.visibility == ((OnUpdateNavBarVisibility) obj).visibility) {
                    return true;
                }
                return false;
            }

            public final int hashCode() {
                return Integer.hashCode(this.visibility);
            }

            public final String toString() {
                return ConstraintWidget$$ExternalSyntheticOutline0.m(new StringBuilder("OnUpdateNavBarVisibility(visibility="), this.visibility, ")");
            }
        }

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes.dex */
        public final class OnUpdateRegionSamplingListener implements EventType {
            public final boolean registered;

            public OnUpdateRegionSamplingListener(boolean z) {
                this.registered = z;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if ((obj instanceof OnUpdateRegionSamplingListener) && this.registered == ((OnUpdateRegionSamplingListener) obj).registered) {
                    return true;
                }
                return false;
            }

            public final int hashCode() {
                boolean z = this.registered;
                if (z) {
                    return 1;
                }
                return z ? 1 : 0;
            }

            public final String toString() {
                return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(new StringBuilder("OnUpdateRegionSamplingListener(registered="), this.registered, ")");
            }
        }

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes.dex */
        public final class OnUpdateRemoteViewContainer implements EventType {
            public final boolean contextualButtonVisible;
            public final float darkIntensity;
            public final int displayId;
            public final LinearLayout leftContainer;
            public final LinearLayout rightContainer;

            public OnUpdateRemoteViewContainer(LinearLayout linearLayout, LinearLayout linearLayout2, boolean z, float f, int i) {
                this.leftContainer = linearLayout;
                this.rightContainer = linearLayout2;
                this.contextualButtonVisible = z;
                this.darkIntensity = f;
                this.displayId = i;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof OnUpdateRemoteViewContainer)) {
                    return false;
                }
                OnUpdateRemoteViewContainer onUpdateRemoteViewContainer = (OnUpdateRemoteViewContainer) obj;
                if (Intrinsics.areEqual(this.leftContainer, onUpdateRemoteViewContainer.leftContainer) && Intrinsics.areEqual(this.rightContainer, onUpdateRemoteViewContainer.rightContainer) && this.contextualButtonVisible == onUpdateRemoteViewContainer.contextualButtonVisible && Float.compare(this.darkIntensity, onUpdateRemoteViewContainer.darkIntensity) == 0 && this.displayId == onUpdateRemoteViewContainer.displayId) {
                    return true;
                }
                return false;
            }

            /* JADX WARN: Multi-variable type inference failed */
            public final int hashCode() {
                int hashCode;
                int i = 0;
                LinearLayout linearLayout = this.leftContainer;
                if (linearLayout == null) {
                    hashCode = 0;
                } else {
                    hashCode = linearLayout.hashCode();
                }
                int i2 = hashCode * 31;
                LinearLayout linearLayout2 = this.rightContainer;
                if (linearLayout2 != null) {
                    i = linearLayout2.hashCode();
                }
                int i3 = (i2 + i) * 31;
                boolean z = this.contextualButtonVisible;
                int i4 = z;
                if (z != 0) {
                    i4 = 1;
                }
                return Integer.hashCode(this.displayId) + UdfpsOverlayParams$$ExternalSyntheticOutline0.m(this.darkIntensity, (i3 + i4) * 31, 31);
            }

            public final String toString() {
                StringBuilder sb = new StringBuilder("OnUpdateRemoteViewContainer(leftContainer=");
                sb.append(this.leftContainer);
                sb.append(", rightContainer=");
                sb.append(this.rightContainer);
                sb.append(", contextualButtonVisible=");
                sb.append(this.contextualButtonVisible);
                sb.append(", darkIntensity=");
                sb.append(this.darkIntensity);
                sb.append(", displayId=");
                return ConstraintWidget$$ExternalSyntheticOutline0.m(sb, this.displayId, ")");
            }
        }

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes.dex */
        public final class OnUpdateSideBackGestureInsets implements EventType {
            public final int leftWidth;
            public final int rightWidth;

            public OnUpdateSideBackGestureInsets(int i, int i2) {
                this.leftWidth = i;
                this.rightWidth = i2;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof OnUpdateSideBackGestureInsets)) {
                    return false;
                }
                OnUpdateSideBackGestureInsets onUpdateSideBackGestureInsets = (OnUpdateSideBackGestureInsets) obj;
                if (this.leftWidth == onUpdateSideBackGestureInsets.leftWidth && this.rightWidth == onUpdateSideBackGestureInsets.rightWidth) {
                    return true;
                }
                return false;
            }

            public final int hashCode() {
                return Integer.hashCode(this.rightWidth) + (Integer.hashCode(this.leftWidth) * 31);
            }

            public final String toString() {
                StringBuilder sb = new StringBuilder("OnUpdateSideBackGestureInsets(leftWidth=");
                sb.append(this.leftWidth);
                sb.append(", rightWidth=");
                return ConstraintWidget$$ExternalSyntheticOutline0.m(sb, this.rightWidth, ")");
            }
        }

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes.dex */
        public final class OnUpdateSpayVisibility implements EventType {
            public final boolean showing;
            public final int width;

            public OnUpdateSpayVisibility(boolean z, int i) {
                this.showing = z;
                this.width = i;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof OnUpdateSpayVisibility)) {
                    return false;
                }
                OnUpdateSpayVisibility onUpdateSpayVisibility = (OnUpdateSpayVisibility) obj;
                if (this.showing == onUpdateSpayVisibility.showing && this.width == onUpdateSpayVisibility.width) {
                    return true;
                }
                return false;
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r0v1, types: [int] */
            /* JADX WARN: Type inference failed for: r0v3 */
            /* JADX WARN: Type inference failed for: r0v4 */
            public final int hashCode() {
                boolean z = this.showing;
                ?? r0 = z;
                if (z) {
                    r0 = 1;
                }
                return Integer.hashCode(this.width) + (r0 * 31);
            }

            public final String toString() {
                return "OnUpdateSpayVisibility(showing=" + this.showing + ", width=" + this.width + ")";
            }
        }

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes.dex */
        public final class OnUpdateSysUiStateFlag implements EventType {
            public final boolean noArguments;

            public OnUpdateSysUiStateFlag() {
                this(false, 1, null);
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if ((obj instanceof OnUpdateSysUiStateFlag) && this.noArguments == ((OnUpdateSysUiStateFlag) obj).noArguments) {
                    return true;
                }
                return false;
            }

            public final int hashCode() {
                boolean z = this.noArguments;
                if (z) {
                    return 1;
                }
                return z ? 1 : 0;
            }

            public final String toString() {
                return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(new StringBuilder("OnUpdateSysUiStateFlag(noArguments="), this.noArguments, ")");
            }

            public OnUpdateSysUiStateFlag(boolean z) {
                this.noArguments = z;
            }

            public /* synthetic */ OnUpdateSysUiStateFlag(boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
                this((i & 1) != 0 ? true : z);
            }
        }

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes.dex */
        public final class OnUpdateTaskbarAvailable implements EventType {
            public final boolean noArguments;

            public OnUpdateTaskbarAvailable() {
                this(false, 1, null);
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if ((obj instanceof OnUpdateTaskbarAvailable) && this.noArguments == ((OnUpdateTaskbarAvailable) obj).noArguments) {
                    return true;
                }
                return false;
            }

            public final int hashCode() {
                boolean z = this.noArguments;
                if (z) {
                    return 1;
                }
                return z ? 1 : 0;
            }

            public final String toString() {
                return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(new StringBuilder("OnUpdateTaskbarAvailable(noArguments="), this.noArguments, ")");
            }

            public OnUpdateTaskbarAvailable(boolean z) {
                this.noArguments = z;
            }

            public /* synthetic */ OnUpdateTaskbarAvailable(boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
                this((i & 1) != 0 ? true : z);
            }
        }

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes.dex */
        public final class OnUseThemeDefaultChanged implements EventType {
            public final boolean noArguments;

            public OnUseThemeDefaultChanged() {
                this(false, 1, null);
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if ((obj instanceof OnUseThemeDefaultChanged) && this.noArguments == ((OnUseThemeDefaultChanged) obj).noArguments) {
                    return true;
                }
                return false;
            }

            public final int hashCode() {
                boolean z = this.noArguments;
                if (z) {
                    return 1;
                }
                return z ? 1 : 0;
            }

            public final String toString() {
                return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(new StringBuilder("OnUseThemeDefaultChanged(noArguments="), this.noArguments, ")");
            }

            public OnUseThemeDefaultChanged(boolean z) {
                this.noArguments = z;
            }

            public /* synthetic */ OnUseThemeDefaultChanged(boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
                this((i & 1) != 0 ? true : z);
            }
        }

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes.dex */
        public final class OnUserSwitched implements EventType {
            public final boolean noArguments;

            public OnUserSwitched() {
                this(false, 1, null);
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if ((obj instanceof OnUserSwitched) && this.noArguments == ((OnUserSwitched) obj).noArguments) {
                    return true;
                }
                return false;
            }

            public final int hashCode() {
                boolean z = this.noArguments;
                if (z) {
                    return 1;
                }
                return z ? 1 : 0;
            }

            public final String toString() {
                return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(new StringBuilder("OnUserSwitched(noArguments="), this.noArguments, ")");
            }

            public OnUserSwitched(boolean z) {
                this.noArguments = z;
            }

            public /* synthetic */ OnUserSwitched(boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
                this((i & 1) != 0 ? true : z);
            }
        }

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes.dex */
        public final class ResetBottomGestureHintVI implements EventType {
            public final boolean noArguments;

            public ResetBottomGestureHintVI() {
                this(false, 1, null);
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if ((obj instanceof ResetBottomGestureHintVI) && this.noArguments == ((ResetBottomGestureHintVI) obj).noArguments) {
                    return true;
                }
                return false;
            }

            public final int hashCode() {
                boolean z = this.noArguments;
                if (z) {
                    return 1;
                }
                return z ? 1 : 0;
            }

            public final String toString() {
                return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(new StringBuilder("ResetBottomGestureHintVI(noArguments="), this.noArguments, ")");
            }

            public ResetBottomGestureHintVI(boolean z) {
                this.noArguments = z;
            }

            public /* synthetic */ ResetBottomGestureHintVI(boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
                this((i & 1) != 0 ? true : z);
            }
        }

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes.dex */
        public final class StartBottomGestureHintVI implements EventType {
            public final int hintId;

            public StartBottomGestureHintVI(int i) {
                this.hintId = i;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if ((obj instanceof StartBottomGestureHintVI) && this.hintId == ((StartBottomGestureHintVI) obj).hintId) {
                    return true;
                }
                return false;
            }

            public final int hashCode() {
                return Integer.hashCode(this.hintId);
            }

            public final String toString() {
                return ConstraintWidget$$ExternalSyntheticOutline0.m(new StringBuilder("StartBottomGestureHintVI(hintId="), this.hintId, ")");
            }
        }
    }

    public EventTypeFactory(Context context) {
        this.context = context;
    }
}
