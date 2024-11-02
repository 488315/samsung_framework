package android.app.admin;

import android.net.wifi.WifiSsid;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.ArraySet;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;
import java.util.Set;

/* loaded from: classes.dex */
public final class WifiSsidPolicy implements Parcelable {
    public static final Parcelable.Creator<WifiSsidPolicy> CREATOR = new Parcelable.Creator<WifiSsidPolicy>() { // from class: android.app.admin.WifiSsidPolicy.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public WifiSsidPolicy createFromParcel(Parcel source) {
            return new WifiSsidPolicy(source);
        }

        @Override // android.os.Parcelable.Creator
        public WifiSsidPolicy[] newArray(int size) {
            return new WifiSsidPolicy[size];
        }
    };
    public static final int WIFI_SSID_POLICY_TYPE_ALLOWLIST = 0;
    public static final int WIFI_SSID_POLICY_TYPE_DENYLIST = 1;
    private int mPolicyType;
    private ArraySet<WifiSsid> mSsids;

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes.dex */
    public @interface WifiSsidPolicyType {
    }

    /* synthetic */ WifiSsidPolicy(Parcel parcel, WifiSsidPolicyIA wifiSsidPolicyIA) {
        this(parcel);
    }

    public WifiSsidPolicy(int policyType, Set<WifiSsid> ssids) {
        if (ssids.isEmpty()) {
            throw new IllegalArgumentException("SSID list cannot be empty");
        }
        if (policyType != 0 && policyType != 1) {
            throw new IllegalArgumentException("Invalid policy type");
        }
        this.mPolicyType = policyType;
        this.mSsids = new ArraySet<>(ssids);
    }

    private WifiSsidPolicy(Parcel in) {
        this.mPolicyType = in.readInt();
        this.mSsids = in.readArraySet(null);
    }

    public Set<WifiSsid> getSsids() {
        return this.mSsids;
    }

    public int getPolicyType() {
        return this.mPolicyType;
    }

    /* renamed from: android.app.admin.WifiSsidPolicy$1 */
    /* loaded from: classes.dex */
    class AnonymousClass1 implements Parcelable.Creator<WifiSsidPolicy> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public WifiSsidPolicy createFromParcel(Parcel source) {
            return new WifiSsidPolicy(source);
        }

        @Override // android.os.Parcelable.Creator
        public WifiSsidPolicy[] newArray(int size) {
            return new WifiSsidPolicy[size];
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mPolicyType);
        dest.writeArraySet(this.mSsids);
    }

    public boolean equals(Object thatObject) {
        if (this == thatObject) {
            return true;
        }
        if (!(thatObject instanceof WifiSsidPolicy)) {
            return false;
        }
        WifiSsidPolicy that = (WifiSsidPolicy) thatObject;
        return this.mPolicyType == that.mPolicyType && Objects.equals(this.mSsids, that.mSsids);
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mPolicyType), this.mSsids);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
