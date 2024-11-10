package com.att.iqi.lib.metrics.mm;

import android.os.Parcel;
import android.os.Parcelable;
import com.att.iqi.lib.Metric;
import java.nio.ByteBuffer;

/* loaded from: classes3.dex */
public class MM04 extends Metric {
    private String m_szCallId;
    private String m_szDialedString;
    private String m_szOriginatingUri;
    private String m_szTerminatingUri;
    public static final Metric.ID ID = new Metric.ID("MM04");
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() { // from class: com.att.iqi.lib.metrics.mm.MM04.1
        @Override // android.os.Parcelable.Creator
        public MM04 createFromParcel(Parcel parcel) {
            return new MM04(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public MM04[] newArray(int i) {
            return new MM04[i];
        }
    };

    public MM04() {
        reset();
    }

    public void reset() {
        this.m_szDialedString = "";
        this.m_szCallId = "";
        this.m_szOriginatingUri = "";
        this.m_szTerminatingUri = "";
    }

    public MM04(Parcel parcel) {
        super(parcel);
        if (parcel.readInt() >= 1) {
            this.m_szDialedString = parcel.readString();
            this.m_szCallId = parcel.readString();
            this.m_szOriginatingUri = parcel.readString();
            this.m_szTerminatingUri = parcel.readString();
        }
    }

    public MM04 setDialedString(String str) {
        this.m_szDialedString = str;
        return this;
    }

    public String getDialedString() {
        return this.m_szDialedString;
    }

    public MM04 setCallId(String str) {
        this.m_szCallId = str;
        return this;
    }

    public String getCallId() {
        return this.m_szCallId;
    }

    public MM04 setOriginatingUri(String str) {
        this.m_szOriginatingUri = str;
        return this;
    }

    public String getOriginatingUri() {
        return this.m_szOriginatingUri;
    }

    public MM04 setTerminatingUri(String str) {
        this.m_szTerminatingUri = str;
        return this;
    }

    public String getTerminatingUri() {
        return this.m_szTerminatingUri;
    }

    @Override // com.att.iqi.lib.Metric
    public int serialize(ByteBuffer byteBuffer) {
        stringOut(byteBuffer, this.m_szDialedString);
        stringOut(byteBuffer, this.m_szCallId);
        stringOut(byteBuffer, this.m_szOriginatingUri);
        stringOut(byteBuffer, this.m_szTerminatingUri);
        return byteBuffer.position();
    }

    @Override // com.att.iqi.lib.Metric, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(this.m_szDialedString);
        parcel.writeString(this.m_szCallId);
        parcel.writeString(this.m_szOriginatingUri);
        parcel.writeString(this.m_szTerminatingUri);
    }
}
