package com.android.systemui.biometrics.udfps;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.picker.model.viewdata.AppInfoViewData$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public abstract class TouchProcessorResult {

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class Failure extends TouchProcessorResult {
        public final String reason;

        public Failure() {
            this(null, 1, 0 == true ? 1 : 0);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if ((obj instanceof Failure) && Intrinsics.areEqual(this.reason, ((Failure) obj).reason)) {
                return true;
            }
            return false;
        }

        public final int hashCode() {
            return this.reason.hashCode();
        }

        public final String toString() {
            return AbstractResolvableFuture$$ExternalSyntheticOutline0.m(new StringBuilder("Failure(reason="), this.reason, ")");
        }

        public Failure(String str) {
            super(null);
            this.reason = str;
        }

        public /* synthetic */ Failure(String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this((i & 1) != 0 ? "" : str);
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class ProcessedTouch extends TouchProcessorResult {
        public final InteractionEvent event;
        public final int pointerOnSensorId;
        public final NormalizedTouchData touchData;

        public ProcessedTouch(InteractionEvent interactionEvent, int i, NormalizedTouchData normalizedTouchData) {
            super(null);
            this.event = interactionEvent;
            this.pointerOnSensorId = i;
            this.touchData = normalizedTouchData;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ProcessedTouch)) {
                return false;
            }
            ProcessedTouch processedTouch = (ProcessedTouch) obj;
            if (this.event == processedTouch.event && this.pointerOnSensorId == processedTouch.pointerOnSensorId && Intrinsics.areEqual(this.touchData, processedTouch.touchData)) {
                return true;
            }
            return false;
        }

        public final int hashCode() {
            return this.touchData.hashCode() + AppInfoViewData$$ExternalSyntheticOutline0.m(this.pointerOnSensorId, this.event.hashCode() * 31, 31);
        }

        public final String toString() {
            return "ProcessedTouch(event=" + this.event + ", pointerOnSensorId=" + this.pointerOnSensorId + ", touchData=" + this.touchData + ")";
        }
    }

    private TouchProcessorResult() {
    }

    public /* synthetic */ TouchProcessorResult(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }
}
