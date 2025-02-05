package android.app.admin;

import android.app.admin.flags.Flags;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

/* loaded from: classes.dex */
public final class BundlePolicyValue extends PolicyValue<Bundle> {
    public static final Parcelable.Creator<BundlePolicyValue> CREATOR = new Parcelable.Creator<BundlePolicyValue>() { // from class: android.app.admin.BundlePolicyValue.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BundlePolicyValue createFromParcel(Parcel source) {
            return new BundlePolicyValue(source);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BundlePolicyValue[] newArray(int size) {
            return new BundlePolicyValue[size];
        }
    };

    public BundlePolicyValue(Bundle value) {
        super(value);
        if (Flags.devicePolicySizeTrackingInternalBugFixEnabled()) {
            PolicySizeVerifier.enforceMaxBundleFieldsLength(value);
        }
    }

    private BundlePolicyValue(Parcel source) {
        this(source.readBundle());
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BundlePolicyValue other = (BundlePolicyValue) o;
        return Objects.equals(getValue(), other.getValue());
    }

    public int hashCode() {
        return Objects.hash(getValue());
    }

    public String toString() {
        return "BundlePolicyValue { mValue= " + getValue() + " }";
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeBundle(getValue());
    }
}
