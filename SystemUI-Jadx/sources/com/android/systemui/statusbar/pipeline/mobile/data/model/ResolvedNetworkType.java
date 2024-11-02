package com.android.systemui.statusbar.pipeline.mobile.data.model;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.settingslib.SignalIcon$MobileIconGroup;
import com.android.settingslib.mobile.TelephonyIcons;
import com.android.systemui.log.table.Diffable;
import com.android.systemui.log.table.TableLogBuffer;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public interface ResolvedNetworkType extends Diffable {

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public abstract class DefaultImpls {
        public static void logDiffs(ResolvedNetworkType resolvedNetworkType, ResolvedNetworkType resolvedNetworkType2, TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
            if (!Intrinsics.areEqual(resolvedNetworkType2, resolvedNetworkType)) {
                tableRowLoggerImpl.logChange("networkType", resolvedNetworkType.toString());
            }
        }
    }

    String getLookupKey();

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public final class CarrierMergedNetworkType implements ResolvedNetworkType {
        public static final CarrierMergedNetworkType INSTANCE = new CarrierMergedNetworkType();
        public static final String lookupKey = "cwf";
        public static final SignalIcon$MobileIconGroup iconGroupOverride = TelephonyIcons.CARRIER_MERGED_WIFI;

        private CarrierMergedNetworkType() {
        }

        @Override // com.android.systemui.statusbar.pipeline.mobile.data.model.ResolvedNetworkType
        public final String getLookupKey() {
            return lookupKey;
        }

        @Override // com.android.systemui.log.table.Diffable
        public final void logDiffs(Diffable diffable, TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
            DefaultImpls.logDiffs(this, (ResolvedNetworkType) diffable, tableRowLoggerImpl);
        }

        public final String toString() {
            return "CarrierMerged";
        }

        @Override // com.android.systemui.log.table.Diffable
        public final void logFull(TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public final class DefaultNetworkType implements ResolvedNetworkType {
        public final String lookupKey;

        public DefaultNetworkType(String str) {
            this.lookupKey = str;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof DefaultNetworkType)) {
                return false;
            }
            if (Intrinsics.areEqual(this.lookupKey, ((DefaultNetworkType) obj).lookupKey)) {
                return true;
            }
            return false;
        }

        @Override // com.android.systemui.statusbar.pipeline.mobile.data.model.ResolvedNetworkType
        public final String getLookupKey() {
            return this.lookupKey;
        }

        public final int hashCode() {
            return this.lookupKey.hashCode();
        }

        @Override // com.android.systemui.log.table.Diffable
        public final void logDiffs(Diffable diffable, TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
            DefaultImpls.logDiffs(this, (ResolvedNetworkType) diffable, tableRowLoggerImpl);
        }

        public final String toString() {
            return AbstractResolvableFuture$$ExternalSyntheticOutline0.m(new StringBuilder("DefaultNetworkType(lookupKey="), this.lookupKey, ")");
        }

        @Override // com.android.systemui.log.table.Diffable
        public final void logFull(TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public final class OverrideNetworkType implements ResolvedNetworkType {
        public final String lookupKey;

        public OverrideNetworkType(String str) {
            this.lookupKey = str;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof OverrideNetworkType)) {
                return false;
            }
            if (Intrinsics.areEqual(this.lookupKey, ((OverrideNetworkType) obj).lookupKey)) {
                return true;
            }
            return false;
        }

        @Override // com.android.systemui.statusbar.pipeline.mobile.data.model.ResolvedNetworkType
        public final String getLookupKey() {
            return this.lookupKey;
        }

        public final int hashCode() {
            return this.lookupKey.hashCode();
        }

        @Override // com.android.systemui.log.table.Diffable
        public final void logDiffs(Diffable diffable, TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
            DefaultImpls.logDiffs(this, (ResolvedNetworkType) diffable, tableRowLoggerImpl);
        }

        public final String toString() {
            return AbstractResolvableFuture$$ExternalSyntheticOutline0.m(new StringBuilder("OverrideNetworkType(lookupKey="), this.lookupKey, ")");
        }

        @Override // com.android.systemui.log.table.Diffable
        public final void logFull(TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public final class UnknownNetworkType implements ResolvedNetworkType {
        public static final UnknownNetworkType INSTANCE = new UnknownNetworkType();
        public static final String lookupKey = Integer.toString(0);

        private UnknownNetworkType() {
        }

        @Override // com.android.systemui.statusbar.pipeline.mobile.data.model.ResolvedNetworkType
        public final String getLookupKey() {
            return lookupKey;
        }

        @Override // com.android.systemui.log.table.Diffable
        public final void logDiffs(Diffable diffable, TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
            DefaultImpls.logDiffs(this, (ResolvedNetworkType) diffable, tableRowLoggerImpl);
        }

        public final String toString() {
            return "Unknown";
        }

        @Override // com.android.systemui.log.table.Diffable
        public final void logFull(TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
        }
    }
}
