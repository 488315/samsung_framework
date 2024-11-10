package com.att.iqi.lib.metrics.mm;

import android.os.Parcel;
import android.os.Parcelable;
import com.att.iqi.lib.Metric;
import java.nio.ByteBuffer;

/* loaded from: classes3.dex */
public class MM05 extends Metric {
    public static final byte IQ_SIP_CALL_STATE_ANSWERED = 8;
    public static final byte IQ_SIP_CALL_STATE_CONNECTED = 9;
    public static final byte IQ_SIP_CALL_STATE_DISCONNECTING = 11;
    public static final byte IQ_SIP_CALL_STATE_HELD = 10;
    public static final byte IQ_SIP_CALL_STATE_IDLE = 1;
    public static final byte IQ_SIP_CALL_STATE_INVITE = 2;
    public static final byte IQ_SIP_CALL_STATE_NEGOTIATING = 5;
    public static final byte IQ_SIP_CALL_STATE_PROGRESS = 4;
    public static final byte IQ_SIP_CALL_STATE_RINGING = 7;
    public static final byte IQ_SIP_CALL_STATE_TRYING = 3;
    public static final byte IQ_SIP_CALL_STATE_UNKNOWN = 0;
    public static final byte IQ_SIP_CALL_STATE_UPDATED = 6;
    private String m_szCallId;
    private byte m_ucCallState;
    public static final Metric.ID ID = new Metric.ID("MM05");
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() { // from class: com.att.iqi.lib.metrics.mm.MM05.1
        @Override // android.os.Parcelable.Creator
        public MM05 createFromParcel(Parcel parcel) {
            return new MM05(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public MM05[] newArray(int i) {
            return new MM05[i];
        }
    };

    public MM05() {
        reset();
    }

    public void reset() {
        this.m_ucCallState = (byte) 0;
        this.m_szCallId = "";
    }

    public MM05(Parcel parcel) {
        super(parcel);
        if (parcel.readInt() >= 1) {
            this.m_ucCallState = parcel.readByte();
            this.m_szCallId = parcel.readString();
        }
    }

    public MM05 setCallState(byte b) {
        this.m_ucCallState = b;
        return this;
    }

    public byte getRegState() {
        return this.m_ucCallState;
    }

    public MM05 setCallId(String str) {
        this.m_szCallId = str;
        return this;
    }

    public String getCallId() {
        return this.m_szCallId;
    }

    @Override // com.att.iqi.lib.Metric
    public int serialize(ByteBuffer byteBuffer) {
        byteBuffer.put(this.m_ucCallState);
        stringOut(byteBuffer, this.m_szCallId);
        return byteBuffer.position();
    }

    @Override // com.att.iqi.lib.Metric, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeByte(this.m_ucCallState);
        parcel.writeString(this.m_szCallId);
    }
}
