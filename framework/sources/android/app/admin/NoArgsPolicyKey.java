package android.app.admin;

import android.annotation.SystemApi;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@SystemApi
/* loaded from: classes.dex */
public final class NoArgsPolicyKey extends PolicyKey {
    public static final Parcelable.Creator<NoArgsPolicyKey> CREATOR = new Parcelable.Creator<NoArgsPolicyKey>() { // from class: android.app.admin.NoArgsPolicyKey.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NoArgsPolicyKey createFromParcel(Parcel source) {
            return new NoArgsPolicyKey(source);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NoArgsPolicyKey[] newArray(int size) {
            return new NoArgsPolicyKey[size];
        }
    };

    public NoArgsPolicyKey(String identifier) {
        super(identifier);
    }

    private NoArgsPolicyKey(Parcel source) {
        this(source.readString());
    }

    @Override // android.app.admin.PolicyKey
    public void writeToBundle(Bundle bundle) {
        bundle.putString(PolicyUpdateReceiver.EXTRA_POLICY_KEY, getIdentifier());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getIdentifier());
    }

    public String toString() {
        return "DefaultPolicyKey " + getIdentifier();
    }
}
