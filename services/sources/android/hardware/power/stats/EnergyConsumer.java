package android.hardware.power.stats;

import android.companion.virtualcamera.SupportedStreamConfiguration$$ExternalSyntheticOutline0;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class EnergyConsumer implements Parcelable {
    public static final Parcelable.Creator CREATOR = new AnonymousClass1();
    public int id;
    public String name;
    public int ordinal;
    public byte type;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: android.hardware.power.stats.EnergyConsumer$1, reason: invalid class name */
    public final class AnonymousClass1 implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            EnergyConsumer energyConsumer = new EnergyConsumer();
            energyConsumer.id = 0;
            energyConsumer.ordinal = 0;
            energyConsumer.type = (byte) 0;
            int dataPosition = parcel.dataPosition();
            int readInt = parcel.readInt();
            try {
                if (readInt < 4) {
                    throw new BadParcelableException("Parcelable too small");
                }
                if (parcel.dataPosition() - dataPosition < readInt) {
                    energyConsumer.id = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        energyConsumer.ordinal = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            energyConsumer.type = parcel.readByte();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                energyConsumer.name = parcel.readString();
                                if (dataPosition > Integer.MAX_VALUE - readInt) {
                                    throw new BadParcelableException("Overflow in the size of parcelable");
                                }
                            } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                throw new BadParcelableException("Overflow in the size of parcelable");
                            }
                        } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return energyConsumer;
            } catch (Throwable th) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                throw th;
            }
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new EnergyConsumer[i];
        }
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.id);
        parcel.writeInt(this.ordinal);
        parcel.writeByte(this.type);
        parcel.writeString(this.name);
        int dataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(dataPosition);
        SupportedStreamConfiguration$$ExternalSyntheticOutline0.m(dataPosition2, dataPosition, parcel, dataPosition2);
    }
}
