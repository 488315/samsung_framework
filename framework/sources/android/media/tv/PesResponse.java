package android.media.tv;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public final class PesResponse extends BroadcastInfoResponse implements Parcelable {
    public static final Parcelable.Creator<PesResponse> CREATOR = new Parcelable.Creator<PesResponse>() { // from class: android.media.tv.PesResponse.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public PesResponse createFromParcel(Parcel source) {
            source.readInt();
            return PesResponse.createFromParcelBody(source);
        }

        @Override // android.os.Parcelable.Creator
        public PesResponse[] newArray(int size) {
            return new PesResponse[size];
        }
    };
    private static final int RESPONSE_TYPE = 4;
    private final String mSharedFilterToken;

    /* renamed from: android.media.tv.PesResponse$1 */
    /* loaded from: classes2.dex */
    class AnonymousClass1 implements Parcelable.Creator<PesResponse> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public PesResponse createFromParcel(Parcel source) {
            source.readInt();
            return PesResponse.createFromParcelBody(source);
        }

        @Override // android.os.Parcelable.Creator
        public PesResponse[] newArray(int size) {
            return new PesResponse[size];
        }
    }

    public static PesResponse createFromParcelBody(Parcel in) {
        return new PesResponse(in);
    }

    public PesResponse(int requestId, int sequence, int responseResult, String sharedFilterToken) {
        super(4, requestId, sequence, responseResult);
        this.mSharedFilterToken = sharedFilterToken;
    }

    PesResponse(Parcel source) {
        super(4, source);
        this.mSharedFilterToken = source.readString();
    }

    public String getSharedFilterToken() {
        return this.mSharedFilterToken;
    }

    @Override // android.media.tv.BroadcastInfoResponse, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.media.tv.BroadcastInfoResponse, android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.mSharedFilterToken);
    }
}
