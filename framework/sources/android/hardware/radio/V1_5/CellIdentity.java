package android.hardware.radio.V1_5;

import android.hardware.radio.V1_2.CellIdentityCdma;
import android.internal.hidl.safe_union.V1_0.Monostate;
import android.media.MediaMetrics;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class CellIdentity {
    private byte hidl_d = 0;
    private Object hidl_o;

    public CellIdentity() {
        this.hidl_o = null;
        this.hidl_o = new Monostate();
    }

    public static final class hidl_discriminator {
        public static final byte cdma = 4;
        public static final byte gsm = 1;
        public static final byte lte = 5;
        public static final byte noinit = 0;
        public static final byte nr = 6;
        public static final byte tdscdma = 3;
        public static final byte wcdma = 2;

        public static final String getName(byte value) {
            switch (value) {
                case 0:
                    return "noinit";
                case 1:
                    return "gsm";
                case 2:
                    return "wcdma";
                case 3:
                    return "tdscdma";
                case 4:
                    return "cdma";
                case 5:
                    return "lte";
                case 6:
                    return "nr";
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

    public void gsm(CellIdentityGsm gsm) {
        this.hidl_d = (byte) 1;
        this.hidl_o = gsm;
    }

    public CellIdentityGsm gsm() {
        if (this.hidl_d != 1) {
            String className = this.hidl_o != null ? this.hidl_o.getClass().getName() : "null";
            throw new IllegalStateException("Read access to inactive union components is disallowed. Discriminator value is " + ((int) this.hidl_d) + " (corresponding to " + hidl_discriminator.getName(this.hidl_d) + "), and hidl_o is of type " + className + MediaMetrics.SEPARATOR);
        }
        if (this.hidl_o != null && !CellIdentityGsm.class.isInstance(this.hidl_o)) {
            throw new Error("Union is in a corrupted state.");
        }
        return (CellIdentityGsm) this.hidl_o;
    }

    public void wcdma(CellIdentityWcdma wcdma) {
        this.hidl_d = (byte) 2;
        this.hidl_o = wcdma;
    }

    public CellIdentityWcdma wcdma() {
        if (this.hidl_d != 2) {
            String className = this.hidl_o != null ? this.hidl_o.getClass().getName() : "null";
            throw new IllegalStateException("Read access to inactive union components is disallowed. Discriminator value is " + ((int) this.hidl_d) + " (corresponding to " + hidl_discriminator.getName(this.hidl_d) + "), and hidl_o is of type " + className + MediaMetrics.SEPARATOR);
        }
        if (this.hidl_o != null && !CellIdentityWcdma.class.isInstance(this.hidl_o)) {
            throw new Error("Union is in a corrupted state.");
        }
        return (CellIdentityWcdma) this.hidl_o;
    }

    public void tdscdma(CellIdentityTdscdma tdscdma) {
        this.hidl_d = (byte) 3;
        this.hidl_o = tdscdma;
    }

    public CellIdentityTdscdma tdscdma() {
        if (this.hidl_d != 3) {
            String className = this.hidl_o != null ? this.hidl_o.getClass().getName() : "null";
            throw new IllegalStateException("Read access to inactive union components is disallowed. Discriminator value is " + ((int) this.hidl_d) + " (corresponding to " + hidl_discriminator.getName(this.hidl_d) + "), and hidl_o is of type " + className + MediaMetrics.SEPARATOR);
        }
        if (this.hidl_o != null && !CellIdentityTdscdma.class.isInstance(this.hidl_o)) {
            throw new Error("Union is in a corrupted state.");
        }
        return (CellIdentityTdscdma) this.hidl_o;
    }

    public void cdma(CellIdentityCdma cdma) {
        this.hidl_d = (byte) 4;
        this.hidl_o = cdma;
    }

    public CellIdentityCdma cdma() {
        if (this.hidl_d != 4) {
            String className = this.hidl_o != null ? this.hidl_o.getClass().getName() : "null";
            throw new IllegalStateException("Read access to inactive union components is disallowed. Discriminator value is " + ((int) this.hidl_d) + " (corresponding to " + hidl_discriminator.getName(this.hidl_d) + "), and hidl_o is of type " + className + MediaMetrics.SEPARATOR);
        }
        if (this.hidl_o != null && !CellIdentityCdma.class.isInstance(this.hidl_o)) {
            throw new Error("Union is in a corrupted state.");
        }
        return (CellIdentityCdma) this.hidl_o;
    }

    public void lte(CellIdentityLte lte) {
        this.hidl_d = (byte) 5;
        this.hidl_o = lte;
    }

    public CellIdentityLte lte() {
        if (this.hidl_d != 5) {
            String className = this.hidl_o != null ? this.hidl_o.getClass().getName() : "null";
            throw new IllegalStateException("Read access to inactive union components is disallowed. Discriminator value is " + ((int) this.hidl_d) + " (corresponding to " + hidl_discriminator.getName(this.hidl_d) + "), and hidl_o is of type " + className + MediaMetrics.SEPARATOR);
        }
        if (this.hidl_o != null && !CellIdentityLte.class.isInstance(this.hidl_o)) {
            throw new Error("Union is in a corrupted state.");
        }
        return (CellIdentityLte) this.hidl_o;
    }

    public void nr(CellIdentityNr nr) {
        this.hidl_d = (byte) 6;
        this.hidl_o = nr;
    }

    public CellIdentityNr nr() {
        if (this.hidl_d != 6) {
            String className = this.hidl_o != null ? this.hidl_o.getClass().getName() : "null";
            throw new IllegalStateException("Read access to inactive union components is disallowed. Discriminator value is " + ((int) this.hidl_d) + " (corresponding to " + hidl_discriminator.getName(this.hidl_d) + "), and hidl_o is of type " + className + MediaMetrics.SEPARATOR);
        }
        if (this.hidl_o != null && !CellIdentityNr.class.isInstance(this.hidl_o)) {
            throw new Error("Union is in a corrupted state.");
        }
        return (CellIdentityNr) this.hidl_o;
    }

    public byte getDiscriminator() {
        return this.hidl_d;
    }

    public final boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }
        if (otherObject == null || otherObject.getClass() != CellIdentity.class) {
            return false;
        }
        CellIdentity other = (CellIdentity) otherObject;
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
                builder.append(".gsm = ");
                builder.append(gsm());
                break;
            case 2:
                builder.append(".wcdma = ");
                builder.append(wcdma());
                break;
            case 3:
                builder.append(".tdscdma = ");
                builder.append(tdscdma());
                break;
            case 4:
                builder.append(".cdma = ");
                builder.append(cdma());
                break;
            case 5:
                builder.append(".lte = ");
                builder.append(lte());
                break;
            case 6:
                builder.append(".nr = ");
                builder.append(nr());
                break;
            default:
                throw new Error("Unknown union discriminator (value: " + ((int) this.hidl_d) + ").");
        }
        builder.append("}");
        return builder.toString();
    }

    public final void readFromParcel(HwParcel parcel) {
        HwBlob blob = parcel.readBuffer(168L);
        readEmbeddedFromParcel(parcel, blob, 0L);
    }

    public static final ArrayList<CellIdentity> readVectorFromParcel(HwParcel parcel) {
        ArrayList<CellIdentity> _hidl_vec = new ArrayList<>();
        HwBlob _hidl_blob = parcel.readBuffer(16L);
        int _hidl_vec_size = _hidl_blob.getInt32(8L);
        HwBlob childBlob = parcel.readEmbeddedBuffer(_hidl_vec_size * 168, _hidl_blob.handle(), 0L, true);
        _hidl_vec.clear();
        for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
            CellIdentity _hidl_vec_element = new CellIdentity();
            _hidl_vec_element.readEmbeddedFromParcel(parcel, childBlob, _hidl_index_0 * 168);
            _hidl_vec.add(_hidl_vec_element);
        }
        return _hidl_vec;
    }

    public final void readEmbeddedFromParcel(HwParcel parcel, HwBlob _hidl_blob, long _hidl_offset) {
        this.hidl_d = _hidl_blob.getInt8(0 + _hidl_offset);
        switch (this.hidl_d) {
            case 0:
                this.hidl_o = new Monostate();
                ((Monostate) this.hidl_o).readEmbeddedFromParcel(parcel, _hidl_blob, 8 + _hidl_offset);
                return;
            case 1:
                this.hidl_o = new CellIdentityGsm();
                ((CellIdentityGsm) this.hidl_o).readEmbeddedFromParcel(parcel, _hidl_blob, 8 + _hidl_offset);
                return;
            case 2:
                this.hidl_o = new CellIdentityWcdma();
                ((CellIdentityWcdma) this.hidl_o).readEmbeddedFromParcel(parcel, _hidl_blob, 8 + _hidl_offset);
                return;
            case 3:
                this.hidl_o = new CellIdentityTdscdma();
                ((CellIdentityTdscdma) this.hidl_o).readEmbeddedFromParcel(parcel, _hidl_blob, 8 + _hidl_offset);
                return;
            case 4:
                this.hidl_o = new CellIdentityCdma();
                ((CellIdentityCdma) this.hidl_o).readEmbeddedFromParcel(parcel, _hidl_blob, 8 + _hidl_offset);
                return;
            case 5:
                this.hidl_o = new CellIdentityLte();
                ((CellIdentityLte) this.hidl_o).readEmbeddedFromParcel(parcel, _hidl_blob, 8 + _hidl_offset);
                return;
            case 6:
                this.hidl_o = new CellIdentityNr();
                ((CellIdentityNr) this.hidl_o).readEmbeddedFromParcel(parcel, _hidl_blob, 8 + _hidl_offset);
                return;
            default:
                throw new IllegalStateException("Unknown union discriminator (value: " + ((int) this.hidl_d) + ").");
        }
    }

    public final void writeToParcel(HwParcel parcel) {
        HwBlob _hidl_blob = new HwBlob(168);
        writeEmbeddedToBlob(_hidl_blob, 0L);
        parcel.writeBuffer(_hidl_blob);
    }

    public static final void writeVectorToParcel(HwParcel parcel, ArrayList<CellIdentity> _hidl_vec) {
        HwBlob _hidl_blob = new HwBlob(16);
        int _hidl_vec_size = _hidl_vec.size();
        _hidl_blob.putInt32(8L, _hidl_vec_size);
        _hidl_blob.putBool(12L, false);
        HwBlob childBlob = new HwBlob(_hidl_vec_size * 168);
        for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
            _hidl_vec.get(_hidl_index_0).writeEmbeddedToBlob(childBlob, _hidl_index_0 * 168);
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
                gsm().writeEmbeddedToBlob(_hidl_blob, 8 + _hidl_offset);
                return;
            case 2:
                wcdma().writeEmbeddedToBlob(_hidl_blob, 8 + _hidl_offset);
                return;
            case 3:
                tdscdma().writeEmbeddedToBlob(_hidl_blob, 8 + _hidl_offset);
                return;
            case 4:
                cdma().writeEmbeddedToBlob(_hidl_blob, 8 + _hidl_offset);
                return;
            case 5:
                lte().writeEmbeddedToBlob(_hidl_blob, 8 + _hidl_offset);
                return;
            case 6:
                nr().writeEmbeddedToBlob(_hidl_blob, 8 + _hidl_offset);
                return;
            default:
                throw new Error("Unknown union discriminator (value: " + ((int) this.hidl_d) + ").");
        }
    }
}
