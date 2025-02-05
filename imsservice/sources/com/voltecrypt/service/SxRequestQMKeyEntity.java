package com.voltecrypt.service;

import android.os.Parcel;
import android.os.Parcelable;
import com.sec.internal.log.IMSLog;
import java.util.Objects;

/* loaded from: classes.dex */
public class SxRequestQMKeyEntity implements Parcelable {
    public static final Parcelable.Creator<SxRequestQMKeyEntity> CREATOR = new Parcelable.Creator<SxRequestQMKeyEntity>() { // from class: com.voltecrypt.service.SxRequestQMKeyEntity.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SxRequestQMKeyEntity createFromParcel(Parcel parcel) {
            return new SxRequestQMKeyEntity(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SxRequestQMKeyEntity[] newArray(int i) {
            return new SxRequestQMKeyEntity[i];
        }
    };
    private String callId;
    private int keyLen;
    private String localPhoneNum;
    private String oppositePhoneNum;
    private String requestMark;
    private String sessionId;
    private long time;
    private int type;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SxRequestQMKeyEntity() {
        this.time = System.currentTimeMillis();
    }

    public SxRequestQMKeyEntity(String str, String str2, int i, String str3, int i2, String str4, String str5) {
        this.time = System.currentTimeMillis();
        this.localPhoneNum = str;
        this.oppositePhoneNum = str2;
        this.keyLen = i;
        this.sessionId = str3;
        this.type = i2;
        this.callId = str4;
        this.requestMark = str5;
    }

    public String getRequestMark() {
        return this.requestMark;
    }

    protected SxRequestQMKeyEntity(Parcel parcel) {
        this.time = System.currentTimeMillis();
        this.time = parcel.readLong();
        this.localPhoneNum = parcel.readString();
        this.oppositePhoneNum = parcel.readString();
        this.keyLen = parcel.readInt();
        this.sessionId = parcel.readString();
        this.type = parcel.readInt();
        this.callId = parcel.readString();
        this.requestMark = parcel.readString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.time);
        parcel.writeString(this.localPhoneNum);
        parcel.writeString(this.oppositePhoneNum);
        parcel.writeInt(this.keyLen);
        parcel.writeString(this.sessionId);
        parcel.writeInt(this.type);
        parcel.writeString(this.callId);
        parcel.writeString(this.requestMark);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        SxRequestQMKeyEntity sxRequestQMKeyEntity = (SxRequestQMKeyEntity) obj;
        return Objects.equals(Long.valueOf(this.time), Long.valueOf(sxRequestQMKeyEntity.time)) && Objects.equals(this.localPhoneNum, sxRequestQMKeyEntity.localPhoneNum) && Objects.equals(this.oppositePhoneNum, sxRequestQMKeyEntity.oppositePhoneNum) && Objects.equals(Integer.valueOf(this.keyLen), Integer.valueOf(sxRequestQMKeyEntity.keyLen)) && Objects.equals(this.sessionId, sxRequestQMKeyEntity.sessionId) && Objects.equals(Integer.valueOf(this.type), Integer.valueOf(sxRequestQMKeyEntity.type)) && Objects.equals(this.callId, sxRequestQMKeyEntity.callId) && Objects.equals(this.requestMark, sxRequestQMKeyEntity.requestMark);
    }

    public int hashCode() {
        return Objects.hash(Long.valueOf(this.time), this.localPhoneNum, this.oppositePhoneNum, Integer.valueOf(this.keyLen), this.sessionId, Integer.valueOf(this.type), this.callId, this.requestMark);
    }

    public String toString() {
        return "SxRequestQMKeyEntity{time='" + this.time + "', localPhoneNum='" + IMSLog.checker(this.localPhoneNum) + "', oppositePhoneNum='" + IMSLog.checker(this.oppositePhoneNum) + "', keyLen='" + this.keyLen + "', sessionId='" + IMSLog.checker(this.sessionId) + "', type='" + this.type + "', callId='" + IMSLog.checker(this.callId) + "', requestMark=" + this.requestMark + '}';
    }
}
