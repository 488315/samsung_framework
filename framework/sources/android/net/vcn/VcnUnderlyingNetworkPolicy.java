package android.net.vcn;

import android.net.NetworkCapabilities;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class VcnUnderlyingNetworkPolicy implements Parcelable {
    public static final Parcelable.Creator<VcnUnderlyingNetworkPolicy> CREATOR = new Parcelable.Creator<VcnUnderlyingNetworkPolicy>() { // from class: android.net.vcn.VcnUnderlyingNetworkPolicy.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VcnUnderlyingNetworkPolicy createFromParcel(Parcel in) {
            return new VcnUnderlyingNetworkPolicy((VcnNetworkPolicyResult) in.readParcelable(null, VcnNetworkPolicyResult.class));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VcnUnderlyingNetworkPolicy[] newArray(int size) {
            return new VcnUnderlyingNetworkPolicy[size];
        }
    };
    private final VcnNetworkPolicyResult mVcnNetworkPolicyResult;

    public VcnUnderlyingNetworkPolicy(boolean isTearDownRequested, NetworkCapabilities mergedNetworkCapabilities) {
        Objects.requireNonNull(mergedNetworkCapabilities, "mergedNetworkCapabilities must be nonnull");
        this.mVcnNetworkPolicyResult = new VcnNetworkPolicyResult(isTearDownRequested, mergedNetworkCapabilities);
    }

    private VcnUnderlyingNetworkPolicy(VcnNetworkPolicyResult vcnNetworkPolicyResult) {
        this.mVcnNetworkPolicyResult = (VcnNetworkPolicyResult) Objects.requireNonNull(vcnNetworkPolicyResult, "vcnNetworkPolicyResult");
    }

    public boolean isTeardownRequested() {
        return this.mVcnNetworkPolicyResult.isTeardownRequested();
    }

    public NetworkCapabilities getMergedNetworkCapabilities() {
        return this.mVcnNetworkPolicyResult.getNetworkCapabilities();
    }

    public int hashCode() {
        return Objects.hash(this.mVcnNetworkPolicyResult);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VcnUnderlyingNetworkPolicy)) {
            return false;
        }
        VcnUnderlyingNetworkPolicy that = (VcnUnderlyingNetworkPolicy) o;
        return this.mVcnNetworkPolicyResult.equals(that.mVcnNetworkPolicyResult);
    }

    public String toString() {
        return this.mVcnNetworkPolicyResult.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.mVcnNetworkPolicyResult, flags);
    }
}
