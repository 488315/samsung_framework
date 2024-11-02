package com.android.systemui.keyguard.ui.viewmodel;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.systemui.animation.Expandable;
import com.android.systemui.common.shared.model.Icon;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class KeyguardQuickAffordanceViewModel {
    public final boolean animateReveal;
    public final String configKey;
    public final Icon icon;
    public final boolean isActivated;
    public final boolean isClickable;
    public final boolean isDimmed;
    public final boolean isSelected;
    public final boolean isVisible;
    public final Function1 onClicked;
    public final String slotId;
    public final boolean useLongPress;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class OnClickedParameters {
        public final String configKey;
        public final Expandable expandable;
        public final String slotId;

        public OnClickedParameters(String str, Expandable expandable, String str2) {
            this.configKey = str;
            this.expandable = expandable;
            this.slotId = str2;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof OnClickedParameters)) {
                return false;
            }
            OnClickedParameters onClickedParameters = (OnClickedParameters) obj;
            if (Intrinsics.areEqual(this.configKey, onClickedParameters.configKey) && Intrinsics.areEqual(this.expandable, onClickedParameters.expandable) && Intrinsics.areEqual(this.slotId, onClickedParameters.slotId)) {
                return true;
            }
            return false;
        }

        public final int hashCode() {
            int hashCode;
            int hashCode2 = this.configKey.hashCode() * 31;
            Expandable expandable = this.expandable;
            if (expandable == null) {
                hashCode = 0;
            } else {
                hashCode = expandable.hashCode();
            }
            return this.slotId.hashCode() + ((hashCode2 + hashCode) * 31);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("OnClickedParameters(configKey=");
            sb.append(this.configKey);
            sb.append(", expandable=");
            sb.append(this.expandable);
            sb.append(", slotId=");
            return AbstractResolvableFuture$$ExternalSyntheticOutline0.m(sb, this.slotId, ")");
        }
    }

    public KeyguardQuickAffordanceViewModel(String str, boolean z, boolean z2, Icon icon, Function1 function1, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, String str2) {
        this.configKey = str;
        this.isVisible = z;
        this.animateReveal = z2;
        this.icon = icon;
        this.onClicked = function1;
        this.isClickable = z3;
        this.isActivated = z4;
        this.isSelected = z5;
        this.useLongPress = z6;
        this.isDimmed = z7;
        this.slotId = str2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof KeyguardQuickAffordanceViewModel)) {
            return false;
        }
        KeyguardQuickAffordanceViewModel keyguardQuickAffordanceViewModel = (KeyguardQuickAffordanceViewModel) obj;
        if (Intrinsics.areEqual(this.configKey, keyguardQuickAffordanceViewModel.configKey) && this.isVisible == keyguardQuickAffordanceViewModel.isVisible && this.animateReveal == keyguardQuickAffordanceViewModel.animateReveal && Intrinsics.areEqual(this.icon, keyguardQuickAffordanceViewModel.icon) && Intrinsics.areEqual(this.onClicked, keyguardQuickAffordanceViewModel.onClicked) && this.isClickable == keyguardQuickAffordanceViewModel.isClickable && this.isActivated == keyguardQuickAffordanceViewModel.isActivated && this.isSelected == keyguardQuickAffordanceViewModel.isSelected && this.useLongPress == keyguardQuickAffordanceViewModel.useLongPress && this.isDimmed == keyguardQuickAffordanceViewModel.isDimmed && Intrinsics.areEqual(this.slotId, keyguardQuickAffordanceViewModel.slotId)) {
            return true;
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final int hashCode() {
        int hashCode;
        String str = this.configKey;
        if (str == null) {
            hashCode = 0;
        } else {
            hashCode = str.hashCode();
        }
        int i = hashCode * 31;
        int i2 = 1;
        boolean z = this.isVisible;
        int i3 = z;
        if (z != 0) {
            i3 = 1;
        }
        int i4 = (i + i3) * 31;
        boolean z2 = this.animateReveal;
        int i5 = z2;
        if (z2 != 0) {
            i5 = 1;
        }
        int hashCode2 = (this.onClicked.hashCode() + ((this.icon.hashCode() + ((i4 + i5) * 31)) * 31)) * 31;
        boolean z3 = this.isClickable;
        int i6 = z3;
        if (z3 != 0) {
            i6 = 1;
        }
        int i7 = (hashCode2 + i6) * 31;
        boolean z4 = this.isActivated;
        int i8 = z4;
        if (z4 != 0) {
            i8 = 1;
        }
        int i9 = (i7 + i8) * 31;
        boolean z5 = this.isSelected;
        int i10 = z5;
        if (z5 != 0) {
            i10 = 1;
        }
        int i11 = (i9 + i10) * 31;
        boolean z6 = this.useLongPress;
        int i12 = z6;
        if (z6 != 0) {
            i12 = 1;
        }
        int i13 = (i11 + i12) * 31;
        boolean z7 = this.isDimmed;
        if (!z7) {
            i2 = z7 ? 1 : 0;
        }
        return this.slotId.hashCode() + ((i13 + i2) * 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("KeyguardQuickAffordanceViewModel(configKey=");
        sb.append(this.configKey);
        sb.append(", isVisible=");
        sb.append(this.isVisible);
        sb.append(", animateReveal=");
        sb.append(this.animateReveal);
        sb.append(", icon=");
        sb.append(this.icon);
        sb.append(", onClicked=");
        sb.append(this.onClicked);
        sb.append(", isClickable=");
        sb.append(this.isClickable);
        sb.append(", isActivated=");
        sb.append(this.isActivated);
        sb.append(", isSelected=");
        sb.append(this.isSelected);
        sb.append(", useLongPress=");
        sb.append(this.useLongPress);
        sb.append(", isDimmed=");
        sb.append(this.isDimmed);
        sb.append(", slotId=");
        return AbstractResolvableFuture$$ExternalSyntheticOutline0.m(sb, this.slotId, ")");
    }

    public /* synthetic */ KeyguardQuickAffordanceViewModel(String str, boolean z, boolean z2, Icon icon, Function1 function1, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, String str2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : str, (i & 2) != 0 ? false : z, (i & 4) != 0 ? false : z2, (i & 8) != 0 ? new Icon.Resource(0, null) : icon, (i & 16) != 0 ? new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordanceViewModel.1
            @Override // kotlin.jvm.functions.Function1
            public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
                return Unit.INSTANCE;
            }
        } : function1, (i & 32) != 0 ? false : z3, (i & 64) != 0 ? false : z4, (i & 128) != 0 ? false : z5, (i & 256) != 0 ? false : z6, (i & 512) != 0 ? false : z7, str2);
    }
}
