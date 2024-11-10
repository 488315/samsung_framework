package com.att.iqi.lib.metrics.ea;

import android.os.Parcel;
import android.os.Parcelable;
import com.att.iqi.lib.Metric;
import java.nio.ByteBuffer;

/* loaded from: classes3.dex */
public class EA13 extends Metric {
    private final int lSerialNumber;
    private final int lServiceCategory;
    public static final Metric.ID ID = new Metric.ID("EA13");
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() { // from class: com.att.iqi.lib.metrics.ea.EA13.1
        @Override // android.os.Parcelable.Creator
        public EA13 createFromParcel(Parcel parcel) {
            return new EA13(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public EA13[] newArray(int i) {
            return new EA13[i];
        }
    };

    public EA13(Parcel parcel) {
        super(parcel);
        parcel.readInt();
        this.lServiceCategory = parcel.readInt();
        this.lSerialNumber = parcel.readInt();
    }

    public EA13(int i, int i2) {
        this.lServiceCategory = i;
        this.lSerialNumber = i2;
    }

    @Override // com.att.iqi.lib.Metric
    public int serialize(ByteBuffer byteBuffer) {
        byteBuffer.putInt(this.lServiceCategory);
        byteBuffer.putInt(this.lSerialNumber);
        return byteBuffer.position();
    }

    @Override // com.att.iqi.lib.Metric, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(this.lServiceCategory);
        parcel.writeInt(this.lSerialNumber);
    }
}
