package com.android.keyguard;

import androidx.picker.model.viewdata.AppInfoViewData$$ExternalSyntheticOutline0;
import com.android.systemui.common.buffer.RingBuffer;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.samsung.android.knox.restriction.PhoneRestrictionPolicy;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.sequences.SequencesKt___SequencesKt;
import kotlin.sequences.TransformingSequence;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class KeyguardFingerprintListenModel extends KeyguardListenModel {
    public static final List TABLE_HEADERS;
    public boolean alternateBouncerShowing;
    public final Lazy asStringList$delegate;
    public boolean biometricEnabledForUser;
    public boolean bouncerIsOrWillShow;
    public boolean canSkipBouncer;
    public boolean credentialAttempted;
    public boolean deviceInteractive;
    public boolean dreaming;
    public boolean fingerprintDisabled;
    public boolean fingerprintLockedOut;
    public boolean goingToSleep;
    public boolean keyguardGoingAway;
    public boolean keyguardIsVisible;
    public boolean keyguardOccluded;
    public boolean listening;
    public boolean occludingAppRequestingFp;
    public boolean shouldListenForFingerprintAssistant;
    public boolean shouldListenSfpsState;
    public boolean strongerAuthRequired;
    public boolean switchingUser;
    public boolean systemUser;
    public long timeMillis;
    public boolean udfps;
    public boolean userDoesNotHaveTrust;
    public int userId;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class Buffer {
        public final RingBuffer buffer = new RingBuffer(20, new Function0() { // from class: com.android.keyguard.KeyguardFingerprintListenModel$Buffer$buffer$1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return new KeyguardFingerprintListenModel(0L, 0, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 16777215, null);
            }
        });

        public final List toList() {
            return SequencesKt___SequencesKt.toList(new TransformingSequence(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(this.buffer), new Function1() { // from class: com.android.keyguard.KeyguardFingerprintListenModel$Buffer$toList$1
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return (List) ((KeyguardFingerprintListenModel) obj).asStringList$delegate.getValue();
                }
            }));
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
        TABLE_HEADERS = CollectionsKt__CollectionsKt.listOf(PhoneRestrictionPolicy.TIMESTAMP, "time_millis", "userId", "listening", "alternateBouncerShowing", "biometricAllowedForUser", "bouncerIsOrWillShow", "canSkipBouncer", "credentialAttempted", "deviceInteractive", "dreaming", "fingerprintDisabled", "fingerprintLockedOut", "goingToSleep", "keyguardGoingAway", "keyguardIsVisible", "keyguardOccluded", "occludingAppRequestingFp", "shouldListenSidFingerprintState", "shouldListenForFingerprintAssistant", "strongAuthRequired", "switchingUser", "systemUser", "underDisplayFingerprint", "userDoesNotHaveTrust");
    }

    public KeyguardFingerprintListenModel() {
        this(0L, 0, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 16777215, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof KeyguardFingerprintListenModel)) {
            return false;
        }
        KeyguardFingerprintListenModel keyguardFingerprintListenModel = (KeyguardFingerprintListenModel) obj;
        if (this.timeMillis == keyguardFingerprintListenModel.timeMillis && this.userId == keyguardFingerprintListenModel.userId && this.listening == keyguardFingerprintListenModel.listening && this.alternateBouncerShowing == keyguardFingerprintListenModel.alternateBouncerShowing && this.biometricEnabledForUser == keyguardFingerprintListenModel.biometricEnabledForUser && this.bouncerIsOrWillShow == keyguardFingerprintListenModel.bouncerIsOrWillShow && this.canSkipBouncer == keyguardFingerprintListenModel.canSkipBouncer && this.credentialAttempted == keyguardFingerprintListenModel.credentialAttempted && this.deviceInteractive == keyguardFingerprintListenModel.deviceInteractive && this.dreaming == keyguardFingerprintListenModel.dreaming && this.fingerprintDisabled == keyguardFingerprintListenModel.fingerprintDisabled && this.fingerprintLockedOut == keyguardFingerprintListenModel.fingerprintLockedOut && this.goingToSleep == keyguardFingerprintListenModel.goingToSleep && this.keyguardGoingAway == keyguardFingerprintListenModel.keyguardGoingAway && this.keyguardIsVisible == keyguardFingerprintListenModel.keyguardIsVisible && this.keyguardOccluded == keyguardFingerprintListenModel.keyguardOccluded && this.occludingAppRequestingFp == keyguardFingerprintListenModel.occludingAppRequestingFp && this.shouldListenSfpsState == keyguardFingerprintListenModel.shouldListenSfpsState && this.shouldListenForFingerprintAssistant == keyguardFingerprintListenModel.shouldListenForFingerprintAssistant && this.strongerAuthRequired == keyguardFingerprintListenModel.strongerAuthRequired && this.switchingUser == keyguardFingerprintListenModel.switchingUser && this.systemUser == keyguardFingerprintListenModel.systemUser && this.udfps == keyguardFingerprintListenModel.udfps && this.userDoesNotHaveTrust == keyguardFingerprintListenModel.userDoesNotHaveTrust) {
            return true;
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final int hashCode() {
        int m = AppInfoViewData$$ExternalSyntheticOutline0.m(this.userId, Long.hashCode(this.timeMillis) * 31, 31);
        boolean z = this.listening;
        int i = 1;
        int i2 = z;
        if (z != 0) {
            i2 = 1;
        }
        int i3 = (m + i2) * 31;
        boolean z2 = this.alternateBouncerShowing;
        int i4 = z2;
        if (z2 != 0) {
            i4 = 1;
        }
        int i5 = (i3 + i4) * 31;
        boolean z3 = this.biometricEnabledForUser;
        int i6 = z3;
        if (z3 != 0) {
            i6 = 1;
        }
        int i7 = (i5 + i6) * 31;
        boolean z4 = this.bouncerIsOrWillShow;
        int i8 = z4;
        if (z4 != 0) {
            i8 = 1;
        }
        int i9 = (i7 + i8) * 31;
        boolean z5 = this.canSkipBouncer;
        int i10 = z5;
        if (z5 != 0) {
            i10 = 1;
        }
        int i11 = (i9 + i10) * 31;
        boolean z6 = this.credentialAttempted;
        int i12 = z6;
        if (z6 != 0) {
            i12 = 1;
        }
        int i13 = (i11 + i12) * 31;
        boolean z7 = this.deviceInteractive;
        int i14 = z7;
        if (z7 != 0) {
            i14 = 1;
        }
        int i15 = (i13 + i14) * 31;
        boolean z8 = this.dreaming;
        int i16 = z8;
        if (z8 != 0) {
            i16 = 1;
        }
        int i17 = (i15 + i16) * 31;
        boolean z9 = this.fingerprintDisabled;
        int i18 = z9;
        if (z9 != 0) {
            i18 = 1;
        }
        int i19 = (i17 + i18) * 31;
        boolean z10 = this.fingerprintLockedOut;
        int i20 = z10;
        if (z10 != 0) {
            i20 = 1;
        }
        int i21 = (i19 + i20) * 31;
        boolean z11 = this.goingToSleep;
        int i22 = z11;
        if (z11 != 0) {
            i22 = 1;
        }
        int i23 = (i21 + i22) * 31;
        boolean z12 = this.keyguardGoingAway;
        int i24 = z12;
        if (z12 != 0) {
            i24 = 1;
        }
        int i25 = (i23 + i24) * 31;
        boolean z13 = this.keyguardIsVisible;
        int i26 = z13;
        if (z13 != 0) {
            i26 = 1;
        }
        int i27 = (i25 + i26) * 31;
        boolean z14 = this.keyguardOccluded;
        int i28 = z14;
        if (z14 != 0) {
            i28 = 1;
        }
        int i29 = (i27 + i28) * 31;
        boolean z15 = this.occludingAppRequestingFp;
        int i30 = z15;
        if (z15 != 0) {
            i30 = 1;
        }
        int i31 = (i29 + i30) * 31;
        boolean z16 = this.shouldListenSfpsState;
        int i32 = z16;
        if (z16 != 0) {
            i32 = 1;
        }
        int i33 = (i31 + i32) * 31;
        boolean z17 = this.shouldListenForFingerprintAssistant;
        int i34 = z17;
        if (z17 != 0) {
            i34 = 1;
        }
        int i35 = (i33 + i34) * 31;
        boolean z18 = this.strongerAuthRequired;
        int i36 = z18;
        if (z18 != 0) {
            i36 = 1;
        }
        int i37 = (i35 + i36) * 31;
        boolean z19 = this.switchingUser;
        int i38 = z19;
        if (z19 != 0) {
            i38 = 1;
        }
        int i39 = (i37 + i38) * 31;
        boolean z20 = this.systemUser;
        int i40 = z20;
        if (z20 != 0) {
            i40 = 1;
        }
        int i41 = (i39 + i40) * 31;
        boolean z21 = this.udfps;
        int i42 = z21;
        if (z21 != 0) {
            i42 = 1;
        }
        int i43 = (i41 + i42) * 31;
        boolean z22 = this.userDoesNotHaveTrust;
        if (!z22) {
            i = z22 ? 1 : 0;
        }
        return i43 + i;
    }

    public final String toString() {
        return "KeyguardFingerprintListenModel(timeMillis=" + this.timeMillis + ", userId=" + this.userId + ", listening=" + this.listening + ", alternateBouncerShowing=" + this.alternateBouncerShowing + ", biometricEnabledForUser=" + this.biometricEnabledForUser + ", bouncerIsOrWillShow=" + this.bouncerIsOrWillShow + ", canSkipBouncer=" + this.canSkipBouncer + ", credentialAttempted=" + this.credentialAttempted + ", deviceInteractive=" + this.deviceInteractive + ", dreaming=" + this.dreaming + ", fingerprintDisabled=" + this.fingerprintDisabled + ", fingerprintLockedOut=" + this.fingerprintLockedOut + ", goingToSleep=" + this.goingToSleep + ", keyguardGoingAway=" + this.keyguardGoingAway + ", keyguardIsVisible=" + this.keyguardIsVisible + ", keyguardOccluded=" + this.keyguardOccluded + ", occludingAppRequestingFp=" + this.occludingAppRequestingFp + ", shouldListenSfpsState=" + this.shouldListenSfpsState + ", shouldListenForFingerprintAssistant=" + this.shouldListenForFingerprintAssistant + ", strongerAuthRequired=" + this.strongerAuthRequired + ", switchingUser=" + this.switchingUser + ", systemUser=" + this.systemUser + ", udfps=" + this.udfps + ", userDoesNotHaveTrust=" + this.userDoesNotHaveTrust + ")";
    }

    public /* synthetic */ KeyguardFingerprintListenModel(long j, int i, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8, boolean z9, boolean z10, boolean z11, boolean z12, boolean z13, boolean z14, boolean z15, boolean z16, boolean z17, boolean z18, boolean z19, boolean z20, boolean z21, boolean z22, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 0L : j, (i2 & 2) != 0 ? 0 : i, (i2 & 4) != 0 ? false : z, (i2 & 8) != 0 ? false : z2, (i2 & 16) != 0 ? false : z3, (i2 & 32) != 0 ? false : z4, (i2 & 64) != 0 ? false : z5, (i2 & 128) != 0 ? false : z6, (i2 & 256) != 0 ? false : z7, (i2 & 512) != 0 ? false : z8, (i2 & 1024) != 0 ? false : z9, (i2 & 2048) != 0 ? false : z10, (i2 & 4096) != 0 ? false : z11, (i2 & 8192) != 0 ? false : z12, (i2 & 16384) != 0 ? false : z13, (i2 & 32768) != 0 ? false : z14, (i2 & 65536) != 0 ? false : z15, (i2 & 131072) != 0 ? false : z16, (i2 & 262144) != 0 ? false : z17, (i2 & 524288) != 0 ? false : z18, (i2 & QuickStepContract.SYSUI_STATE_IME_SWITCHER_SHOWING) != 0 ? false : z19, (i2 & QuickStepContract.SYSUI_STATE_DEVICE_DOZING) != 0 ? false : z20, (i2 & QuickStepContract.SYSUI_STATE_BACK_DISABLED) != 0 ? false : z21, (i2 & QuickStepContract.SYSUI_STATE_BUBBLES_MANAGE_MENU_EXPANDED) != 0 ? false : z22);
    }

    public KeyguardFingerprintListenModel(long j, int i, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8, boolean z9, boolean z10, boolean z11, boolean z12, boolean z13, boolean z14, boolean z15, boolean z16, boolean z17, boolean z18, boolean z19, boolean z20, boolean z21, boolean z22) {
        super(null);
        this.timeMillis = j;
        this.userId = i;
        this.listening = z;
        this.alternateBouncerShowing = z2;
        this.biometricEnabledForUser = z3;
        this.bouncerIsOrWillShow = z4;
        this.canSkipBouncer = z5;
        this.credentialAttempted = z6;
        this.deviceInteractive = z7;
        this.dreaming = z8;
        this.fingerprintDisabled = z9;
        this.fingerprintLockedOut = z10;
        this.goingToSleep = z11;
        this.keyguardGoingAway = z12;
        this.keyguardIsVisible = z13;
        this.keyguardOccluded = z14;
        this.occludingAppRequestingFp = z15;
        this.shouldListenSfpsState = z16;
        this.shouldListenForFingerprintAssistant = z17;
        this.strongerAuthRequired = z18;
        this.switchingUser = z19;
        this.systemUser = z20;
        this.udfps = z21;
        this.userDoesNotHaveTrust = z22;
        this.asStringList$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.keyguard.KeyguardFingerprintListenModel$asStringList$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return CollectionsKt__CollectionsKt.listOf(KeyguardListenModelKt.DATE_FORMAT.format(Long.valueOf(KeyguardFingerprintListenModel.this.timeMillis)), String.valueOf(KeyguardFingerprintListenModel.this.timeMillis), String.valueOf(KeyguardFingerprintListenModel.this.userId), String.valueOf(KeyguardFingerprintListenModel.this.listening), String.valueOf(KeyguardFingerprintListenModel.this.alternateBouncerShowing), String.valueOf(KeyguardFingerprintListenModel.this.biometricEnabledForUser), String.valueOf(KeyguardFingerprintListenModel.this.bouncerIsOrWillShow), String.valueOf(KeyguardFingerprintListenModel.this.canSkipBouncer), String.valueOf(KeyguardFingerprintListenModel.this.credentialAttempted), String.valueOf(KeyguardFingerprintListenModel.this.deviceInteractive), String.valueOf(KeyguardFingerprintListenModel.this.dreaming), String.valueOf(KeyguardFingerprintListenModel.this.fingerprintDisabled), String.valueOf(KeyguardFingerprintListenModel.this.fingerprintLockedOut), String.valueOf(KeyguardFingerprintListenModel.this.goingToSleep), String.valueOf(KeyguardFingerprintListenModel.this.keyguardGoingAway), String.valueOf(KeyguardFingerprintListenModel.this.keyguardIsVisible), String.valueOf(KeyguardFingerprintListenModel.this.keyguardOccluded), String.valueOf(KeyguardFingerprintListenModel.this.occludingAppRequestingFp), String.valueOf(KeyguardFingerprintListenModel.this.shouldListenSfpsState), String.valueOf(KeyguardFingerprintListenModel.this.shouldListenForFingerprintAssistant), String.valueOf(KeyguardFingerprintListenModel.this.strongerAuthRequired), String.valueOf(KeyguardFingerprintListenModel.this.switchingUser), String.valueOf(KeyguardFingerprintListenModel.this.systemUser), String.valueOf(KeyguardFingerprintListenModel.this.udfps), String.valueOf(KeyguardFingerprintListenModel.this.userDoesNotHaveTrust));
            }
        });
    }
}
