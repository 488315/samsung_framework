package com.samsung.android.sivs.ai.sdkcommon.tts;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import java.util.Objects;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes3.dex */
public class TtsSpeakerInfo implements Parcelable {
    public static final Parcelable.Creator<TtsSpeakerInfo> CREATOR = new Parcelable.Creator<TtsSpeakerInfo>() { // from class: com.samsung.android.sivs.ai.sdkcommon.tts.TtsSpeakerInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TtsSpeakerInfo createFromParcel(Parcel parcel) {
            return new TtsSpeakerInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TtsSpeakerInfo[] newArray(int i) {
            return new TtsSpeakerInfo[i];
        }
    };
    private final String displayName;
    private final String id;

    public TtsSpeakerInfo(String str, String str2) {
        this.id = str;
        this.displayName = str2;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof TtsSpeakerInfo)) {
            return this.id.equals(((TtsSpeakerInfo) obj).id);
        }
        return false;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public String getId() {
        return this.id;
    }

    public int hashCode() {
        return Objects.hash(this.id);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Speaker {id='");
        sb.append(this.id);
        sb.append("', Name='");
        return AbstractResolvableFuture$$ExternalSyntheticOutline0.m(sb, this.displayName, "'}");
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.id);
        parcel.writeString(this.displayName);
    }

    public TtsSpeakerInfo(Parcel parcel) {
        this.id = parcel.readString();
        this.displayName = parcel.readString();
    }
}
