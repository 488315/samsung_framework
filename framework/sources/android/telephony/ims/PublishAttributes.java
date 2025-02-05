package android.telephony.ims;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@SystemApi
/* loaded from: classes4.dex */
public final class PublishAttributes implements Parcelable {
    public static final Parcelable.Creator<PublishAttributes> CREATOR = new Parcelable.Creator<PublishAttributes>() { // from class: android.telephony.ims.PublishAttributes.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PublishAttributes createFromParcel(Parcel source) {
            return new PublishAttributes(source);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PublishAttributes[] newArray(int size) {
            return new PublishAttributes[size];
        }
    };
    private List<RcsContactPresenceTuple> mPresenceTuples;
    private final int mPublishState;
    private SipDetails mSipDetails;

    public static final class Builder {
        private PublishAttributes mAttributes;

        public Builder(int publishState) {
            this.mAttributes = new PublishAttributes(publishState);
        }

        public Builder setSipDetails(SipDetails details) {
            this.mAttributes.mSipDetails = details;
            return this;
        }

        public Builder setPresenceTuples(List<RcsContactPresenceTuple> tuples) {
            this.mAttributes.mPresenceTuples = tuples;
            return this;
        }

        public PublishAttributes build() {
            return this.mAttributes;
        }
    }

    private PublishAttributes(int publishState) {
        this.mPublishState = publishState;
    }

    public int getPublishState() {
        return this.mPublishState;
    }

    public List<RcsContactPresenceTuple> getPresenceTuples() {
        if (this.mPresenceTuples == null) {
            return Collections.emptyList();
        }
        return this.mPresenceTuples;
    }

    public SipDetails getSipDetails() {
        return this.mSipDetails;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mPublishState);
        dest.writeList(this.mPresenceTuples);
        dest.writeParcelable(this.mSipDetails, 0);
    }

    private PublishAttributes(Parcel in) {
        this.mPublishState = in.readInt();
        this.mPresenceTuples = new ArrayList();
        in.readList(this.mPresenceTuples, null, RcsContactPresenceTuple.class);
        this.mSipDetails = (SipDetails) in.readParcelable(SipDetails.class.getClassLoader(), SipDetails.class);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PublishAttributes that = (PublishAttributes) o;
        if (this.mPublishState == that.mPublishState && Objects.equals(this.mPresenceTuples, that.mPresenceTuples) && Objects.equals(this.mSipDetails, that.mSipDetails)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mPublishState), this.mPresenceTuples, this.mSipDetails);
    }

    public String toString() {
        return "PublishAttributes { publishState= " + this.mPublishState + ", presenceTuples=[" + this.mPresenceTuples + "]SipDetails=" + this.mSipDetails + "}";
    }
}
