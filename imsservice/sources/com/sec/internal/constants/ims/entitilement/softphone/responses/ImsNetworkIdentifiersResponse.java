package com.sec.internal.constants.ims.entitilement.softphone.responses;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/* loaded from: classes.dex */
public class ImsNetworkIdentifiersResponse extends SoftphoneResponse {

    @SerializedName("identitiesResponse")
    public IdentitiesResponse mIdentitiesResponse;

    public static class IdentitiesResponse {

        @SerializedName("locations")
        public List<String> mLocations;

        @SerializedName("subscriberIdentities")
        public SubscriberIdentities mSubscriberIdentities;

        public static class SubscriberIdentities {

            @SerializedName("FQDN")
            public String mFQDN;

            @SerializedName("privateUserId")
            public String mPrivateUserId;

            @SerializedName("publicUserId")
            public String mPublicUserId;

            public String toString() {
                return "SubscriberIdentities [mPrivateUserId = " + this.mPrivateUserId + ", mPublicUserId = " + this.mPublicUserId + ", mFQDN = " + this.mFQDN + "]";
            }
        }

        public String toString() {
            return "IdentitiesResponse [mSubscriberIdentities = " + this.mSubscriberIdentities + ", mLocations = " + this.mLocations + "]";
        }
    }

    @Override // com.sec.internal.constants.ims.entitilement.softphone.responses.SoftphoneResponse
    public String toString() {
        return "ImsNetworkIdentifiersResponse [mIdentitiesResponse = " + this.mIdentitiesResponse + "]";
    }
}
