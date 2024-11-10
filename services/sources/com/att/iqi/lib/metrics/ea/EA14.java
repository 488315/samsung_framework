package com.att.iqi.lib.metrics.ea;

import android.os.Parcel;
import android.os.Parcelable;
import com.att.iqi.lib.Metric;
import java.nio.ByteBuffer;

/* loaded from: classes3.dex */
public class EA14 extends Metric {
    private final int lSerialNumber;
    private final int lServiceCategory;
    public static final Metric.ID ID = new Metric.ID("EA14");
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() { // from class: com.att.iqi.lib.metrics.ea.EA14.1
        @Override // android.os.Parcelable.Creator
        public EA14 createFromParcel(Parcel parcel) {
            return new EA14(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public EA14[] newArray(int i) {
            return new EA14[i];
        }
    };

    public EA14(Parcel parcel) {
        super(parcel);
        parcel.readInt();
        this.lServiceCategory = parcel.readInt();
        this.lSerialNumber = parcel.readInt();
    }

    public EA14(int i, int i2) {
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
