package android.media.tv;

import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes3.dex */
public abstract class BroadcastInfoRequest implements Parcelable {
    public static final Parcelable.Creator<BroadcastInfoRequest> CREATOR = new Parcelable.Creator<BroadcastInfoRequest>() { // from class: android.media.tv.BroadcastInfoRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BroadcastInfoRequest createFromParcel(Parcel source) {
            int type = source.readInt();
            switch (type) {
                case 1:
                    return TsRequest.createFromParcelBody(source);
                case 2:
                    return TableRequest.createFromParcelBody(source);
                case 3:
                    return SectionRequest.createFromParcelBody(source);
                case 4:
                    return PesRequest.createFromParcelBody(source);
                case 5:
                    return StreamEventRequest.createFromParcelBody(source);
                case 6:
                    return DsmccRequest.createFromParcelBody(source);
                case 7:
                    return CommandRequest.createFromParcelBody(source);
                case 8:
                    return TimelineRequest.createFromParcelBody(source);
                case 9:
                    return SignalingDataRequest.createFromParcelBody(source);
                default:
                    throw new IllegalStateException("Unexpected broadcast info request type (value " + type + ") in parcel.");
            }
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BroadcastInfoRequest[] newArray(int size) {
            return new BroadcastInfoRequest[size];
        }
    };
    public static final int REQUEST_OPTION_AUTO_UPDATE = 1;
    public static final int REQUEST_OPTION_ONESHOT = 3;
    public static final int REQUEST_OPTION_ONEWAY = 2;
    public static final int REQUEST_OPTION_REPEAT = 0;
    private final int mOption;
    private final int mRequestId;
    private final int mType;

    @Retention(RetentionPolicy.SOURCE)
    public @interface RequestOption {
    }

    BroadcastInfoRequest(int type, int requestId, int option) {
        this.mType = type;
        this.mRequestId = requestId;
        this.mOption = option;
    }

    BroadcastInfoRequest(int type, Parcel source) {
        this.mType = type;
        this.mRequestId = source.readInt();
        this.mOption = source.readInt();
    }

    public int getType() {
        return this.mType;
    }

    public int getRequestId() {
        return this.mRequestId;
    }

    public int getOption() {
        return this.mOption;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mType);
        dest.writeInt(this.mRequestId);
        dest.writeInt(this.mOption);
    }
}
