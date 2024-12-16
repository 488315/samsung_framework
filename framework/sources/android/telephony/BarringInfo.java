package android.telephony;

import android.annotation.SystemApi;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import android.security.keystore.KeyProperties;
import android.util.SparseArray;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class BarringInfo implements Parcelable {
    public static final int BARRING_SERVICE_TYPE_CS_FALLBACK = 5;
    public static final int BARRING_SERVICE_TYPE_CS_SERVICE = 0;
    public static final int BARRING_SERVICE_TYPE_CS_VOICE = 2;
    public static final int BARRING_SERVICE_TYPE_EMERGENCY = 8;
    public static final int BARRING_SERVICE_TYPE_MMTEL_VIDEO = 7;
    public static final int BARRING_SERVICE_TYPE_MMTEL_VOICE = 6;
    public static final int BARRING_SERVICE_TYPE_MO_DATA = 4;
    public static final int BARRING_SERVICE_TYPE_MO_SIGNALLING = 3;
    public static final int BARRING_SERVICE_TYPE_PS_SERVICE = 1;
    public static final int BARRING_SERVICE_TYPE_SMS = 9;
    private SparseArray<BarringServiceInfo> mBarringServiceInfos;
    private CellIdentity mCellIdentity;
    private static final BarringServiceInfo BARRING_SERVICE_INFO_UNKNOWN = new BarringServiceInfo(-1);
    private static final BarringServiceInfo BARRING_SERVICE_INFO_UNBARRED = new BarringServiceInfo(0);
    public static final Parcelable.Creator<BarringInfo> CREATOR = new Parcelable.Creator<BarringInfo>() { // from class: android.telephony.BarringInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BarringInfo createFromParcel(Parcel source) {
            return new BarringInfo(source);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BarringInfo[] newArray(int size) {
            return new BarringInfo[size];
        }
    };

    @Retention(RetentionPolicy.SOURCE)
    public @interface BarringServiceType {
    }

    public static final class BarringServiceInfo implements Parcelable {
        public static final int BARRING_TYPE_CONDITIONAL = 1;
        public static final int BARRING_TYPE_NONE = 0;
        public static final int BARRING_TYPE_UNCONDITIONAL = 2;
        public static final int BARRING_TYPE_UNKNOWN = -1;
        public static final Parcelable.Creator<BarringServiceInfo> CREATOR = new Parcelable.Creator<BarringServiceInfo>() { // from class: android.telephony.BarringInfo.BarringServiceInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public BarringServiceInfo createFromParcel(Parcel source) {
                return new BarringServiceInfo(source);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public BarringServiceInfo[] newArray(int size) {
                return new BarringServiceInfo[size];
            }
        };
        private final int mBarringType;
        private final int mConditionalBarringFactor;
        private final int mConditionalBarringTimeSeconds;
        private final boolean mIsConditionallyBarred;

        @Retention(RetentionPolicy.SOURCE)
        public @interface BarringType {
        }

        public BarringServiceInfo(int type) {
            this(type, false, 0, 0);
        }

        public BarringServiceInfo(int barringType, boolean isConditionallyBarred, int conditionalBarringFactor, int conditionalBarringTimeSeconds) {
            this.mBarringType = barringType;
            this.mIsConditionallyBarred = isConditionallyBarred;
            this.mConditionalBarringFactor = conditionalBarringFactor;
            this.mConditionalBarringTimeSeconds = conditionalBarringTimeSeconds;
        }

        public int getBarringType() {
            return this.mBarringType;
        }

        public boolean isConditionallyBarred() {
            return this.mIsConditionallyBarred;
        }

        public int getConditionalBarringFactor() {
            return this.mConditionalBarringFactor;
        }

        public int getConditionalBarringTimeSeconds() {
            return this.mConditionalBarringTimeSeconds;
        }

        public boolean isBarred() {
            if (this.mBarringType != 2) {
                return this.mBarringType == 1 && this.mIsConditionallyBarred;
            }
            return true;
        }

        public int hashCode() {
            return Objects.hash(Integer.valueOf(this.mBarringType), Boolean.valueOf(this.mIsConditionallyBarred), Integer.valueOf(this.mConditionalBarringFactor), Integer.valueOf(this.mConditionalBarringTimeSeconds));
        }

        public boolean equals(Object rhs) {
            if (!(rhs instanceof BarringServiceInfo)) {
                return false;
            }
            BarringServiceInfo other = (BarringServiceInfo) rhs;
            return this.mBarringType == other.mBarringType && this.mIsConditionallyBarred == other.mIsConditionallyBarred && this.mConditionalBarringFactor == other.mConditionalBarringFactor && this.mConditionalBarringTimeSeconds == other.mConditionalBarringTimeSeconds;
        }

        private static String barringTypeToString(int barringType) {
            switch (barringType) {
                case -1:
                    return "UNKNOWN";
                case 0:
                    return KeyProperties.DIGEST_NONE;
                case 1:
                    return "CONDITIONAL";
                case 2:
                    return "UNCONDITIONAL";
                default:
                    return "UNKNOWN(" + barringType + NavigationBarInflaterView.KEY_CODE_END;
            }
        }

        public String toString() {
            return "BarringServiceInfo {mBarringType=" + barringTypeToString(this.mBarringType) + ", mIsConditionallyBarred=" + this.mIsConditionallyBarred + ", mConditionalBarringFactor=" + this.mConditionalBarringFactor + ", mConditionalBarringTimeSeconds=" + this.mConditionalBarringTimeSeconds + "}";
        }

        public BarringServiceInfo(Parcel p) {
            this.mBarringType = p.readInt();
            this.mIsConditionallyBarred = p.readBoolean();
            this.mConditionalBarringFactor = p.readInt();
            this.mConditionalBarringTimeSeconds = p.readInt();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.mBarringType);
            dest.writeBoolean(this.mIsConditionallyBarred);
            dest.writeInt(this.mConditionalBarringFactor);
            dest.writeInt(this.mConditionalBarringTimeSeconds);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }
    }

    @SystemApi
    public BarringInfo() {
        this.mBarringServiceInfos = new SparseArray<>();
    }

    public BarringInfo(CellIdentity barringCellId, SparseArray<BarringServiceInfo> barringServiceInfos) {
        this.mCellIdentity = barringCellId;
        this.mBarringServiceInfos = barringServiceInfos;
    }

    public BarringServiceInfo getBarringServiceInfo(int service) {
        BarringServiceInfo bsi = this.mBarringServiceInfos.get(service);
        return bsi != null ? bsi : this.mBarringServiceInfos.size() > 0 ? BARRING_SERVICE_INFO_UNBARRED : BARRING_SERVICE_INFO_UNKNOWN;
    }

    @SystemApi
    public BarringInfo createLocationInfoSanitizedCopy() {
        return this.mCellIdentity == null ? this : new BarringInfo(this.mCellIdentity.sanitizeLocationInfo(), this.mBarringServiceInfos);
    }

    public BarringInfo(Parcel p) {
        this.mCellIdentity = (CellIdentity) p.readParcelable(CellIdentity.class.getClassLoader(), CellIdentity.class);
        this.mBarringServiceInfos = p.readSparseArray(BarringServiceInfo.class.getClassLoader(), BarringServiceInfo.class);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.mCellIdentity, flags);
        dest.writeSparseArray(this.mBarringServiceInfos);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int hashCode() {
        int hash = this.mCellIdentity != null ? this.mCellIdentity.hashCode() : 7;
        for (int i = 0; i < this.mBarringServiceInfos.size(); i++) {
            hash = hash + (this.mBarringServiceInfos.keyAt(i) * 15) + (this.mBarringServiceInfos.valueAt(i).hashCode() * 31);
        }
        return hash;
    }

    public boolean equals(Object rhs) {
        if (!(rhs instanceof BarringInfo)) {
            return false;
        }
        BarringInfo bi = (BarringInfo) rhs;
        if (hashCode() != bi.hashCode() || this.mBarringServiceInfos.size() != bi.mBarringServiceInfos.size()) {
            return false;
        }
        for (int i = 0; i < this.mBarringServiceInfos.size(); i++) {
            if (this.mBarringServiceInfos.keyAt(i) != bi.mBarringServiceInfos.keyAt(i) || !Objects.equals(this.mBarringServiceInfos.valueAt(i), bi.mBarringServiceInfos.valueAt(i))) {
                return false;
            }
        }
        return true;
    }

    public String toString() {
        return "BarringInfo {mCellIdentity=" + this.mCellIdentity + ", mBarringServiceInfos=" + this.mBarringServiceInfos + "}";
    }

    public CellIdentity getCellIdentity() {
        return this.mCellIdentity;
    }
}
