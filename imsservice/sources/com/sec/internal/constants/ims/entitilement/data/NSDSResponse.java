package com.sec.internal.constants.ims.entitilement.data;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

/* loaded from: classes.dex */
public class NSDSResponse implements Parcelable {

    @SerializedName("message-id")
    public int messageId;
    public String method;

    @SerializedName("response-code")
    public int responseCode;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public NSDSResponse(Parcel parcel) {
        this.messageId = parcel.readInt();
        this.responseCode = parcel.readInt();
        this.method = parcel.readString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.messageId);
        parcel.writeInt(this.responseCode);
        parcel.writeString(this.method);
    }
}
