package android.view;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes4.dex */
public abstract class DisplayAddress implements Parcelable {
    public static Physical fromPhysicalDisplayId(long physicalDisplayId) {
        return new Physical(physicalDisplayId);
    }

    public static Physical fromPortAndModel(int port, Long model) {
        return new Physical(port, model);
    }

    public static Network fromMacAddress(String macAddress) {
        return new Network(macAddress);
    }

    /* loaded from: classes4.dex */
    public static final class Physical extends DisplayAddress {
        public static final Parcelable.Creator<Physical> CREATOR = new Parcelable.Creator<Physical>() { // from class: android.view.DisplayAddress.Physical.1
            AnonymousClass1() {
            }

            @Override // android.os.Parcelable.Creator
            public Physical createFromParcel(Parcel in) {
                return new Physical(in.readLong());
            }

            @Override // android.os.Parcelable.Creator
            public Physical[] newArray(int size) {
                return new Physical[size];
            }
        };
        private static final int MODEL_SHIFT = 8;
        private static final long UNKNOWN_MODEL = 0;
        private final long mPhysicalDisplayId;

        /* synthetic */ Physical(int i, Long l, PhysicalIA physicalIA) {
            this(i, l);
        }

        /* synthetic */ Physical(long j, PhysicalIA physicalIA) {
            this(j);
        }

        public long getPhysicalDisplayId() {
            return this.mPhysicalDisplayId;
        }

        public int getPort() {
            return (int) (this.mPhysicalDisplayId & 255);
        }

        public Long getModel() {
            long model = this.mPhysicalDisplayId >>> 8;
            if (model == 0) {
                return null;
            }
            return Long.valueOf(model);
        }

        public boolean equals(Object other) {
            return (other instanceof Physical) && this.mPhysicalDisplayId == ((Physical) other).mPhysicalDisplayId;
        }

        public String toString() {
            StringBuilder builder = new StringBuilder("{").append("port=").append(getPort());
            Long model = getModel();
            if (model != null) {
                builder.append(", model=0x").append(Long.toHexString(model.longValue()));
            }
            return builder.append("}").toString();
        }

        public int hashCode() {
            return Long.hashCode(this.mPhysicalDisplayId);
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel out, int flags) {
            out.writeLong(this.mPhysicalDisplayId);
        }

        private Physical(long physicalDisplayId) {
            this.mPhysicalDisplayId = physicalDisplayId;
        }

        private Physical(int port, Long model) {
            if (port < 0 || port > 255) {
                throw new IllegalArgumentException("The port should be in the interval [0, 255]");
            }
            this.mPhysicalDisplayId = Integer.toUnsignedLong(port) | (model == null ? 0L : model.longValue() << 8);
        }

        /* renamed from: android.view.DisplayAddress$Physical$1 */
        /* loaded from: classes4.dex */
        class AnonymousClass1 implements Parcelable.Creator<Physical> {
            AnonymousClass1() {
            }

            @Override // android.os.Parcelable.Creator
            public Physical createFromParcel(Parcel in) {
                return new Physical(in.readLong());
            }

            @Override // android.os.Parcelable.Creator
            public Physical[] newArray(int size) {
                return new Physical[size];
            }
        }
    }

    /* loaded from: classes4.dex */
    public static final class Network extends DisplayAddress {
        public static final Parcelable.Creator<Network> CREATOR = new Parcelable.Creator<Network>() { // from class: android.view.DisplayAddress.Network.1
            AnonymousClass1() {
            }

            @Override // android.os.Parcelable.Creator
            public Network createFromParcel(Parcel in) {
                return new Network(in.readString());
            }

            @Override // android.os.Parcelable.Creator
            public Network[] newArray(int size) {
                return new Network[size];
            }
        };
        private final String mMacAddress;

        /* synthetic */ Network(String str, NetworkIA networkIA) {
            this(str);
        }

        public boolean equals(Object other) {
            return (other instanceof Network) && this.mMacAddress.equals(((Network) other).mMacAddress);
        }

        public String toString() {
            return this.mMacAddress;
        }

        public int hashCode() {
            return this.mMacAddress.hashCode();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel out, int flags) {
            out.writeString(this.mMacAddress);
        }

        private Network(String macAddress) {
            this.mMacAddress = macAddress;
        }

        /* renamed from: android.view.DisplayAddress$Network$1 */
        /* loaded from: classes4.dex */
        class AnonymousClass1 implements Parcelable.Creator<Network> {
            AnonymousClass1() {
            }

            @Override // android.os.Parcelable.Creator
            public Network createFromParcel(Parcel in) {
                return new Network(in.readString());
            }

            @Override // android.os.Parcelable.Creator
            public Network[] newArray(int size) {
                return new Network[size];
            }
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
