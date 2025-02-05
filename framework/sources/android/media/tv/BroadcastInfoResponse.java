package android.media.tv;

import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes3.dex */
public abstract class BroadcastInfoResponse implements Parcelable {
    public static final Parcelable.Creator<BroadcastInfoResponse> CREATOR = new Parcelable.Creator<BroadcastInfoResponse>() { // from class: android.media.tv.BroadcastInfoResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BroadcastInfoResponse createFromParcel(Parcel source) {
            int type = source.readInt();
            switch (type) {
                case 1:
                    return TsResponse.createFromParcelBody(source);
                case 2:
                    return TableResponse.createFromParcelBody(source);
                case 3:
                    return SectionResponse.createFromParcelBody(source);
                case 4:
                    return PesResponse.createFromParcelBody(source);
                case 5:
                    return StreamEventResponse.createFromParcelBody(source);
                case 6:
                    return DsmccResponse.createFromParcelBody(source);
                case 7:
                    return CommandResponse.createFromParcelBody(source);
                case 8:
                    return TimelineResponse.createFromParcelBody(source);
                case 9:
                    return SignalingDataResponse.createFromParcelBody(source);
                default:
                    throw new IllegalStateException("Unexpected broadcast info response type (value " + type + ") in parcel.");
            }
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BroadcastInfoResponse[] newArray(int size) {
            return new BroadcastInfoResponse[size];
        }
    };
    public static final int RESPONSE_RESULT_CANCEL = 3;
    public static final int RESPONSE_RESULT_ERROR = 1;
    public static final int RESPONSE_RESULT_OK = 2;
    private final int mRequestId;
    private final int mResponseResult;
    private final int mSequence;
    private final int mType;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ResponseResult {
    }

    BroadcastInfoResponse(int type, int requestId, int sequence, int responseResult) {
        this.mType = type;
        this.mRequestId = requestId;
        this.mSequence = sequence;
        this.mResponseResult = responseResult;
    }

    BroadcastInfoResponse(int type, Parcel source) {
        this.mType = type;
        this.mRequestId = source.readInt();
        this.mSequence = source.readInt();
        this.mResponseResult = source.readInt();
    }

    public int getType() {
        return this.mType;
    }

    public int getRequestId() {
        return this.mRequestId;
    }

    public int getSequence() {
        return this.mSequence;
    }

    public int getResponseResult() {
        return this.mResponseResult;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mType);
        dest.writeInt(this.mRequestId);
        dest.writeInt(this.mSequence);
        dest.writeInt(this.mResponseResult);
    }
}
