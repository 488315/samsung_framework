package com.samsung.android.sdk.routines.automationservice.data;

import androidx.picker.model.AppInfo$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes3.dex */
public final class ConditionStatus {
    public static final Companion Companion = new Companion(null);
    public final long instanceId;
    public final boolean isEnabled;
    public final ParameterValues parameterValues;
    public final String tag;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes3.dex */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public /* synthetic */ ConditionStatus(long j, boolean z, String str, ParameterValues parameterValues, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, z, str, parameterValues);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ConditionStatus)) {
            return false;
        }
        ConditionStatus conditionStatus = (ConditionStatus) obj;
        if (this.instanceId == conditionStatus.instanceId && this.isEnabled == conditionStatus.isEnabled && Intrinsics.areEqual(this.tag, conditionStatus.tag) && Intrinsics.areEqual(this.parameterValues, conditionStatus.parameterValues)) {
            return true;
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final int hashCode() {
        int hashCode = Long.hashCode(this.instanceId) * 31;
        boolean z = this.isEnabled;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        return this.parameterValues.hashCode() + AppInfo$$ExternalSyntheticOutline0.m(this.tag, (hashCode + i) * 31, 31);
    }

    public final String toString() {
        return "ConditionStatus(instanceId=" + this.instanceId + ", isEnabled=" + this.isEnabled + ", tag=" + this.tag + ", parameterValues=" + this.parameterValues + ')';
    }

    private ConditionStatus(long j, boolean z, String str, ParameterValues parameterValues) {
        this.instanceId = j;
        this.isEnabled = z;
        this.tag = str;
        this.parameterValues = parameterValues;
    }
}
