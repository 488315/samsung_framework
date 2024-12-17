package android.os;

import android.companion.virtualcamera.SupportedStreamConfiguration$$ExternalSyntheticOutline0;
import android.os.Parcelable;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public class CreateAppDataArgs implements Parcelable {
    public static final Parcelable.Creator CREATOR = new AnonymousClass1();
    public String packageName;
    public String seInfo;
    public String uuid;
    public int userId = 0;
    public int flags = 0;
    public int appId = 0;
    public int previousAppId = 0;
    public int targetSdkVersion = 0;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: android.os.CreateAppDataArgs$1, reason: invalid class name */
    public final class AnonymousClass1 implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            CreateAppDataArgs createAppDataArgs = new CreateAppDataArgs();
            createAppDataArgs.readFromParcel(parcel);
            return createAppDataArgs;
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new CreateAppDataArgs[i];
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public final void readFromParcel(Parcel parcel) {
        int dataPosition = parcel.dataPosition();
        int readInt = parcel.readInt();
        try {
            if (readInt < 4) {
                throw new BadParcelableException("Parcelable too small");
            }
            if (parcel.dataPosition() - dataPosition < readInt) {
                this.uuid = parcel.readString();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.packageName = parcel.readString();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.userId = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.flags = parcel.readInt();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.appId = parcel.readInt();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.previousAppId = parcel.readInt();
                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                        this.seInfo = parcel.readString();
                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                            this.targetSdkVersion = parcel.readInt();
                                            if (dataPosition > Integer.MAX_VALUE - readInt) {
                                                throw new BadParcelableException("Overflow in the size of parcelable");
                                            }
                                            parcel.setDataPosition(dataPosition + readInt);
                                            return;
                                        }
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
        } catch (Throwable th) {
            if (dataPosition > Integer.MAX_VALUE - readInt) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(dataPosition + readInt);
            throw th;
        }
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.uuid);
        parcel.writeString(this.packageName);
        parcel.writeInt(this.userId);
        parcel.writeInt(this.flags);
        parcel.writeInt(this.appId);
        parcel.writeInt(this.previousAppId);
        parcel.writeString(this.seInfo);
        int m = SupportedStreamConfiguration$$ExternalSyntheticOutline0.m(parcel, this.targetSdkVersion, dataPosition);
        SupportedStreamConfiguration$$ExternalSyntheticOutline0.m(m, dataPosition, parcel, m);
    }
}
