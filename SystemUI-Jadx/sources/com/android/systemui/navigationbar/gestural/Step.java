package com.android.systemui.navigationbar.gestural;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class Step {
    public final float factor;
    public boolean hasCrossedUpperBoundAtLeastOnce;
    public final float lowerFactor;
    public final Object postThreshold;
    public final Object preThreshold;
    public Value previousValue;
    public Value startValue;
    public final float threshold;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class Value {
        public final boolean isNewState;
        public final Object value;

        public Value(Object obj, boolean z) {
            this.value = obj;
            this.isNewState = z;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Value)) {
                return false;
            }
            Value value = (Value) obj;
            if (Intrinsics.areEqual(this.value, value.value) && this.isNewState == value.isNewState) {
                return true;
            }
            return false;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final int hashCode() {
            int hashCode;
            Object obj = this.value;
            if (obj == null) {
                hashCode = 0;
            } else {
                hashCode = obj.hashCode();
            }
            int i = hashCode * 31;
            boolean z = this.isNewState;
            int i2 = z;
            if (z != 0) {
                i2 = 1;
            }
            return i + i2;
        }

        public final String toString() {
            return "Value(value=" + this.value + ", isNewState=" + this.isNewState + ")";
        }
    }

    public Step(float f, float f2, Object obj, Object obj2) {
        this.threshold = f;
        this.factor = f2;
        this.postThreshold = obj;
        this.preThreshold = obj2;
        this.lowerFactor = 2 - f2;
        this.hasCrossedUpperBoundAtLeastOnce = false;
        Value value = new Value(obj2, false);
        this.startValue = value;
        this.previousValue = value;
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x004d, code lost:
    
        if (r6 == null) goto L21;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.android.systemui.navigationbar.gestural.Step.Value get(float r6) {
        /*
            r5 = this;
            float r0 = r5.factor
            float r1 = r5.threshold
            float r0 = r0 * r1
            int r0 = (r6 > r0 ? 1 : (r6 == r0 ? 0 : -1))
            r2 = 0
            r3 = 1
            if (r0 <= 0) goto Ld
            r0 = r3
            goto Le
        Ld:
            r0 = r2
        Le:
            float r4 = r5.lowerFactor
            float r1 = r1 * r4
            int r6 = (r6 > r1 ? 1 : (r6 == r1 ? 0 : -1))
            if (r6 <= 0) goto L17
            r6 = r3
            goto L18
        L17:
            r6 = r2
        L18:
            if (r0 == 0) goto L28
            boolean r0 = r5.hasCrossedUpperBoundAtLeastOnce
            if (r0 != 0) goto L28
            r5.hasCrossedUpperBoundAtLeastOnce = r3
            com.android.systemui.navigationbar.gestural.Step$Value r6 = new com.android.systemui.navigationbar.gestural.Step$Value
            java.lang.Object r0 = r5.postThreshold
            r6.<init>(r0, r3)
            goto L50
        L28:
            r0 = 0
            if (r6 == 0) goto L3d
            com.android.systemui.navigationbar.gestural.Step$Value r6 = r5.previousValue
            if (r6 != 0) goto L30
            goto L31
        L30:
            r0 = r6
        L31:
            java.lang.Object r6 = r0.value
            r0.getClass()
            com.android.systemui.navigationbar.gestural.Step$Value r0 = new com.android.systemui.navigationbar.gestural.Step$Value
            r0.<init>(r6, r2)
        L3b:
            r6 = r0
            goto L50
        L3d:
            boolean r6 = r5.hasCrossedUpperBoundAtLeastOnce
            if (r6 == 0) goto L4b
            r5.hasCrossedUpperBoundAtLeastOnce = r2
            com.android.systemui.navigationbar.gestural.Step$Value r6 = new com.android.systemui.navigationbar.gestural.Step$Value
            java.lang.Object r0 = r5.preThreshold
            r6.<init>(r0, r3)
            goto L50
        L4b:
            com.android.systemui.navigationbar.gestural.Step$Value r6 = r5.startValue
            if (r6 != 0) goto L50
            goto L3b
        L50:
            r5.previousValue = r6
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.navigationbar.gestural.Step.get(float):com.android.systemui.navigationbar.gestural.Step$Value");
    }

    public /* synthetic */ Step(float f, float f2, Object obj, Object obj2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(f, (i & 2) != 0 ? 1.1f : f2, obj, obj2);
    }
}
