package vendor.samsung.hardware.radio.V2_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes6.dex */
public final class SehSignalBar {
    public int cdmaLevel = 0;
    public int evdoLevel = 0;
    public int gsmLevel = 0;
    public int wcdmaLevel = 0;
    public int tdscdmaLevel = 0;
    public int lteLevel = 0;
    public int nrLevel = 0;

    public final boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }
        if (otherObject == null || otherObject.getClass() != SehSignalBar.class) {
            return false;
        }
        SehSignalBar other = (SehSignalBar) otherObject;
        if (this.cdmaLevel == other.cdmaLevel && this.evdoLevel == other.evdoLevel && this.gsmLevel == other.gsmLevel && this.wcdmaLevel == other.wcdmaLevel && this.tdscdmaLevel == other.tdscdmaLevel && this.lteLevel == other.lteLevel && this.nrLevel == other.nrLevel) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.cdmaLevel))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.evdoLevel))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.gsmLevel))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.wcdmaLevel))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.tdscdmaLevel))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.lteLevel))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.nrLevel))));
    }

    public final String toString() {
        return "{.cdmaLevel = " + SehSignalLevel.toString(this.cdmaLevel) + ", .evdoLevel = " + SehSignalLevel.toString(this.evdoLevel) + ", .gsmLevel = " + SehSignalLevel.toString(this.gsmLevel) + ", .wcdmaLevel = " + SehSignalLevel.toString(this.wcdmaLevel) + ", .tdscdmaLevel = " + SehSignalLevel.toString(this.tdscdmaLevel) + ", .lteLevel = " + SehSignalLevel.toString(this.lteLevel) + ", .nrLevel = " + SehSignalLevel.toString(this.nrLevel) + "}";
    }

    public final void readFromParcel(HwParcel parcel) {
        HwBlob blob = parcel.readBuffer(28L);
        readEmbeddedFromParcel(parcel, blob, 0L);
    }

    public static final ArrayList<SehSignalBar> readVectorFromParcel(HwParcel parcel) {
        ArrayList<SehSignalBar> _hidl_vec = new ArrayList<>();
        HwBlob _hidl_blob = parcel.readBuffer(16L);
        int _hidl_vec_size = _hidl_blob.getInt32(8L);
        HwBlob childBlob = parcel.readEmbeddedBuffer(_hidl_vec_size * 28, _hidl_blob.handle(), 0L, true);
        _hidl_vec.clear();
        for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
            SehSignalBar _hidl_vec_element = new SehSignalBar();
            _hidl_vec_element.readEmbeddedFromParcel(parcel, childBlob, _hidl_index_0 * 28);
            _hidl_vec.add(_hidl_vec_element);
        }
        return _hidl_vec;
    }

    public final void readEmbeddedFromParcel(HwParcel parcel, HwBlob _hidl_blob, long _hidl_offset) {
        this.cdmaLevel = _hidl_blob.getInt32(0 + _hidl_offset);
        this.evdoLevel = _hidl_blob.getInt32(4 + _hidl_offset);
        this.gsmLevel = _hidl_blob.getInt32(8 + _hidl_offset);
        this.wcdmaLevel = _hidl_blob.getInt32(12 + _hidl_offset);
        this.tdscdmaLevel = _hidl_blob.getInt32(16 + _hidl_offset);
        this.lteLevel = _hidl_blob.getInt32(20 + _hidl_offset);
        this.nrLevel = _hidl_blob.getInt32(24 + _hidl_offset);
    }

    public final void writeToParcel(HwParcel parcel) {
        HwBlob _hidl_blob = new HwBlob(28);
        writeEmbeddedToBlob(_hidl_blob, 0L);
        parcel.writeBuffer(_hidl_blob);
    }

    public static final void writeVectorToParcel(HwParcel parcel, ArrayList<SehSignalBar> _hidl_vec) {
        HwBlob _hidl_blob = new HwBlob(16);
        int _hidl_vec_size = _hidl_vec.size();
        _hidl_blob.putInt32(8L, _hidl_vec_size);
        _hidl_blob.putBool(12L, false);
        HwBlob childBlob = new HwBlob(_hidl_vec_size * 28);
        for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
            _hidl_vec.get(_hidl_index_0).writeEmbeddedToBlob(childBlob, _hidl_index_0 * 28);
        }
        _hidl_blob.putBlob(0L, childBlob);
        parcel.writeBuffer(_hidl_blob);
    }

    public final void writeEmbeddedToBlob(HwBlob _hidl_blob, long _hidl_offset) {
        _hidl_blob.putInt32(0 + _hidl_offset, this.cdmaLevel);
        _hidl_blob.putInt32(4 + _hidl_offset, this.evdoLevel);
        _hidl_blob.putInt32(8 + _hidl_offset, this.gsmLevel);
        _hidl_blob.putInt32(12 + _hidl_offset, this.wcdmaLevel);
        _hidl_blob.putInt32(16 + _hidl_offset, this.tdscdmaLevel);
        _hidl_blob.putInt32(20 + _hidl_offset, this.lteLevel);
        _hidl_blob.putInt32(24 + _hidl_offset, this.nrLevel);
    }
}
