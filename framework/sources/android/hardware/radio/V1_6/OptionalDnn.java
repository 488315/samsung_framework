package android.hardware.radio.V1_6;

import android.internal.hidl.safe_union.V1_0.Monostate;
import android.media.MediaMetrics;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class OptionalDnn {
    private byte hidl_d = 0;
    private Object hidl_o;

    public OptionalDnn() {
        this.hidl_o = null;
        this.hidl_o = new Monostate();
    }

    public static final class hidl_discriminator {
        public static final byte noinit = 0;
        public static final byte value = 1;

        public static final String getName(byte value2) {
            switch (value2) {
                case 0:
                    return "noinit";
                case 1:
                    return "value";
                default:
                    return "Unknown";
            }
        }

        private hidl_discriminator() {
        }
    }

    public void noinit(Monostate noinit) {
        this.hidl_d = (byte) 0;
        this.hidl_o = noinit;
    }

    public Monostate noinit() {
        if (this.hidl_d != 0) {
            String className = this.hidl_o != null ? this.hidl_o.getClass().getName() : "null";
            throw new IllegalStateException("Read access to inactive union components is disallowed. Discriminator value is " + ((int) this.hidl_d) + " (corresponding to " + hidl_discriminator.getName(this.hidl_d) + "), and hidl_o is of type " + className + MediaMetrics.SEPARATOR);
        }
        if (this.hidl_o != null && !Monostate.class.isInstance(this.hidl_o)) {
            throw new Error("Union is in a corrupted state.");
        }
        return (Monostate) this.hidl_o;
    }

    public void value(String value) {
        this.hidl_d = (byte) 1;
        this.hidl_o = value;
    }

    public String value() {
        if (this.hidl_d != 1) {
            String className = this.hidl_o != null ? this.hidl_o.getClass().getName() : "null";
            throw new IllegalStateException("Read access to inactive union components is disallowed. Discriminator value is " + ((int) this.hidl_d) + " (corresponding to " + hidl_discriminator.getName(this.hidl_d) + "), and hidl_o is of type " + className + MediaMetrics.SEPARATOR);
        }
        if (this.hidl_o != null && !String.class.isInstance(this.hidl_o)) {
            throw new Error("Union is in a corrupted state.");
        }
        return (String) this.hidl_o;
    }

    public byte getDiscriminator() {
        return this.hidl_d;
    }

    public final boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }
        if (otherObject == null || otherObject.getClass() != OptionalDnn.class) {
            return false;
        }
        OptionalDnn other = (OptionalDnn) otherObject;
        if (this.hidl_d == other.hidl_d && HidlSupport.deepEquals(this.hidl_o, other.hidl_o)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(this.hidl_o)), Integer.valueOf(Objects.hashCode(Byte.valueOf(this.hidl_d))));
    }

    public final String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        switch (this.hidl_d) {
            case 0:
                builder.append(".noinit = ");
                builder.append(noinit());
                break;
            case 1:
                builder.append(".value = ");
                builder.append(value());
                break;
            default:
                throw new Error("Unknown union discriminator (value: " + ((int) this.hidl_d) + ").");
        }
        builder.append("}");
        return builder.toString();
    }

    public final void readFromParcel(HwParcel parcel) {
        HwBlob blob = parcel.readBuffer(24L);
        readEmbeddedFromParcel(parcel, blob, 0L);
    }

    public static final ArrayList<OptionalDnn> readVectorFromParcel(HwParcel parcel) {
        ArrayList<OptionalDnn> _hidl_vec = new ArrayList<>();
        HwBlob _hidl_blob = parcel.readBuffer(16L);
        int _hidl_vec_size = _hidl_blob.getInt32(8L);
        HwBlob childBlob = parcel.readEmbeddedBuffer(_hidl_vec_size * 24, _hidl_blob.handle(), 0L, true);
        _hidl_vec.clear();
        for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
            OptionalDnn _hidl_vec_element = new OptionalDnn();
            _hidl_vec_element.readEmbeddedFromParcel(parcel, childBlob, _hidl_index_0 * 24);
            _hidl_vec.add(_hidl_vec_element);
        }
        return _hidl_vec;
    }

    public final void readEmbeddedFromParcel(HwParcel parcel, HwBlob _hidl_blob, long _hidl_offset) {
        this.hidl_d = _hidl_blob.getInt8(_hidl_offset + 0);
        switch (this.hidl_d) {
            case 0:
                this.hidl_o = new Monostate();
                ((Monostate) this.hidl_o).readEmbeddedFromParcel(parcel, _hidl_blob, _hidl_offset + 8);
                return;
            case 1:
                this.hidl_o = new String();
                this.hidl_o = _hidl_blob.getString(_hidl_offset + 8);
                parcel.readEmbeddedBuffer(((String) this.hidl_o).getBytes().length + 1, _hidl_blob.handle(), _hidl_offset + 8 + 0, false);
                return;
            default:
                throw new IllegalStateException("Unknown union discriminator (value: " + ((int) this.hidl_d) + ").");
        }
    }

    public final void writeToParcel(HwParcel parcel) {
        HwBlob _hidl_blob = new HwBlob(24);
        writeEmbeddedToBlob(_hidl_blob, 0L);
        parcel.writeBuffer(_hidl_blob);
    }

    public static final void writeVectorToParcel(HwParcel parcel, ArrayList<OptionalDnn> _hidl_vec) {
        HwBlob _hidl_blob = new HwBlob(16);
        int _hidl_vec_size = _hidl_vec.size();
        _hidl_blob.putInt32(8L, _hidl_vec_size);
        _hidl_blob.putBool(12L, false);
        HwBlob childBlob = new HwBlob(_hidl_vec_size * 24);
        for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
            _hidl_vec.get(_hidl_index_0).writeEmbeddedToBlob(childBlob, _hidl_index_0 * 24);
        }
        _hidl_blob.putBlob(0L, childBlob);
        parcel.writeBuffer(_hidl_blob);
    }

    public final void writeEmbeddedToBlob(HwBlob _hidl_blob, long _hidl_offset) {
        _hidl_blob.putInt8(0 + _hidl_offset, this.hidl_d);
        switch (this.hidl_d) {
            case 0:
                noinit().writeEmbeddedToBlob(_hidl_blob, 8 + _hidl_offset);
                return;
            case 1:
                _hidl_blob.putString(8 + _hidl_offset, value());
                return;
            default:
                throw new Error("Unknown union discriminator (value: " + ((int) this.hidl_d) + ").");
        }
    }
}
