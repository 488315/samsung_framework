package android.content.pm;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.ArraySet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/* loaded from: classes.dex */
public final class CapabilityParams implements Parcelable {
    public static final Parcelable.Creator<CapabilityParams> CREATOR = new Parcelable.Creator<CapabilityParams>() { // from class: android.content.pm.CapabilityParams.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CapabilityParams[] newArray(int size) {
            return new CapabilityParams[size];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CapabilityParams createFromParcel(Parcel in) {
            return new CapabilityParams(in);
        }
    };
    private final List<String> mAliases;
    private final String mName;
    private final String mPrimaryValue;

    private CapabilityParams(String name, String primaryValue, Collection<String> aliases) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(primaryValue);
        this.mName = name;
        this.mPrimaryValue = primaryValue;
        this.mAliases = aliases == null ? Collections.emptyList() : Collections.unmodifiableList(new ArrayList(aliases));
    }

    CapabilityParams(CapabilityParams orig) {
        this(orig.mName, orig.mPrimaryValue, orig.mAliases);
    }

    private CapabilityParams(Builder builder) {
        this(builder.mKey, builder.mPrimaryValue, builder.mAliases);
    }

    private CapabilityParams(Parcel in) {
        this.mName = in.readString();
        this.mPrimaryValue = in.readString();
        List<String> values = new ArrayList<>();
        in.readStringList(values);
        this.mAliases = Collections.unmodifiableList(values);
    }

    public String getName() {
        return this.mName;
    }

    public String getValue() {
        return this.mPrimaryValue;
    }

    public List<String> getAliases() {
        return new ArrayList(this.mAliases);
    }

    List<String> getValues() {
        if (this.mAliases == null) {
            return new ArrayList(Collections.singletonList(this.mPrimaryValue));
        }
        List<String> ret = new ArrayList<>(this.mAliases.size() + 1);
        ret.add(this.mPrimaryValue);
        ret.addAll(this.mAliases);
        return ret;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof CapabilityParams)) {
            return false;
        }
        CapabilityParams target = (CapabilityParams) obj;
        return this.mName.equals(target.mName) && this.mPrimaryValue.equals(target.mPrimaryValue) && this.mAliases.equals(target.mAliases);
    }

    public int hashCode() {
        return Objects.hash(this.mName, this.mPrimaryValue, this.mAliases);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mName);
        dest.writeString(this.mPrimaryValue);
        dest.writeStringList(this.mAliases);
    }

    public static final class Builder {
        private Set<String> mAliases;
        private final String mKey;
        private final String mPrimaryValue;

        public Builder(String key, String value) {
            Objects.requireNonNull(key);
            if (TextUtils.isEmpty(value)) {
                throw new IllegalArgumentException("Primary value cannot be empty or null");
            }
            this.mPrimaryValue = value;
            this.mKey = key;
        }

        public Builder addAlias(String alias) {
            if (this.mAliases == null) {
                this.mAliases = new ArraySet(1);
            }
            this.mAliases.add(alias);
            return this;
        }

        public CapabilityParams build() {
            return new CapabilityParams(this);
        }
    }
}
