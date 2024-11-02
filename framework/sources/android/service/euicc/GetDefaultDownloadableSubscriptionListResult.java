package android.service.euicc;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.euicc.DownloadableSubscription;
import java.util.Arrays;
import java.util.List;

@SystemApi
/* loaded from: classes3.dex */
public final class GetDefaultDownloadableSubscriptionListResult implements Parcelable {
    public static final Parcelable.Creator<GetDefaultDownloadableSubscriptionListResult> CREATOR = new Parcelable.Creator<GetDefaultDownloadableSubscriptionListResult>() { // from class: android.service.euicc.GetDefaultDownloadableSubscriptionListResult.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public GetDefaultDownloadableSubscriptionListResult createFromParcel(Parcel in) {
            return new GetDefaultDownloadableSubscriptionListResult(in);
        }

        @Override // android.os.Parcelable.Creator
        public GetDefaultDownloadableSubscriptionListResult[] newArray(int size) {
            return new GetDefaultDownloadableSubscriptionListResult[size];
        }
    };
    private final DownloadableSubscription[] mSubscriptions;

    @Deprecated
    public final int result;

    /* synthetic */ GetDefaultDownloadableSubscriptionListResult(Parcel parcel, GetDefaultDownloadableSubscriptionListResultIA getDefaultDownloadableSubscriptionListResultIA) {
        this(parcel);
    }

    /* renamed from: android.service.euicc.GetDefaultDownloadableSubscriptionListResult$1 */
    /* loaded from: classes3.dex */
    class AnonymousClass1 implements Parcelable.Creator<GetDefaultDownloadableSubscriptionListResult> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public GetDefaultDownloadableSubscriptionListResult createFromParcel(Parcel in) {
            return new GetDefaultDownloadableSubscriptionListResult(in);
        }

        @Override // android.os.Parcelable.Creator
        public GetDefaultDownloadableSubscriptionListResult[] newArray(int size) {
            return new GetDefaultDownloadableSubscriptionListResult[size];
        }
    }

    public int getResult() {
        return this.result;
    }

    public List<DownloadableSubscription> getDownloadableSubscriptions() {
        DownloadableSubscription[] downloadableSubscriptionArr = this.mSubscriptions;
        if (downloadableSubscriptionArr == null) {
            return null;
        }
        return Arrays.asList(downloadableSubscriptionArr);
    }

    public GetDefaultDownloadableSubscriptionListResult(int result, DownloadableSubscription[] subscriptions) {
        this.result = result;
        if (result == 0) {
            this.mSubscriptions = subscriptions;
        } else {
            if (subscriptions != null) {
                throw new IllegalArgumentException("Error result with non-null subscriptions: " + result);
            }
            this.mSubscriptions = null;
        }
    }

    private GetDefaultDownloadableSubscriptionListResult(Parcel in) {
        this.result = in.readInt();
        this.mSubscriptions = (DownloadableSubscription[]) in.createTypedArray(DownloadableSubscription.CREATOR);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.result);
        dest.writeTypedArray(this.mSubscriptions, flags);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
