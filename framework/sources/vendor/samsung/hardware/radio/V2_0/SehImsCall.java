package vendor.samsung.hardware.radio.V2_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes6.dex */
public final class SehImsCall {
    public int state = 0;
    public int type = 0;
    public int isMt = 0;
    public int isMpty = 0;
    public String number = new String();

    public final boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }
        if (otherObject == null || otherObject.getClass() != SehImsCall.class) {
            return false;
        }
        SehImsCall other = (SehImsCall) otherObject;
        if (this.state == other.state && this.type == other.type && this.isMt == other.isMt && this.isMpty == other.isMpty && HidlSupport.deepEquals(this.number, other.number)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.state))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.type))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.isMt))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.isMpty))), Integer.valueOf(HidlSupport.deepHashCode(this.number)));
    }

    public final String toString() {
        return "{.state = " + this.state + ", .type = " + this.type + ", .isMt = " + this.isMt + ", .isMpty = " + this.isMpty + ", .number = " + this.number + "}";
    }

    public final void readFromParcel(HwParcel parcel) {
        HwBlob blob = parcel.readBuffer(32L);
        readEmbeddedFromParcel(parcel, blob, 0L);
    }

    public static final ArrayList<SehImsCall> readVectorFromParcel(HwParcel parcel) {
        ArrayList<SehImsCall> _hidl_vec = new ArrayList<>();
        HwBlob _hidl_blob = parcel.readBuffer(16L);
        int _hidl_vec_size = _hidl_blob.getInt32(8L);
        HwBlob childBlob = parcel.readEmbeddedBuffer(_hidl_vec_size * 32, _hidl_blob.handle(), 0L, true);
        _hidl_vec.clear();
        for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
            SehImsCall _hidl_vec_element = new SehImsCall();
            _hidl_vec_element.readEmbeddedFromParcel(parcel, childBlob, _hidl_index_0 * 32);
            _hidl_vec.add(_hidl_vec_element);
        }
        return _hidl_vec;
    }

    public final void readEmbeddedFromParcel(HwParcel parcel, HwBlob _hidl_blob, long _hidl_offset) {
        this.state = _hidl_blob.getInt32(_hidl_offset + 0);
        this.type = _hidl_blob.getInt32(_hidl_offset + 4);
        this.isMt = _hidl_blob.getInt32(_hidl_offset + 8);
        this.isMpty = _hidl_blob.getInt32(_hidl_offset + 12);
        this.number = _hidl_blob.getString(_hidl_offset + 16);
        parcel.readEmbeddedBuffer(r6.getBytes().length + 1, _hidl_blob.handle(), _hidl_offset + 16 + 0, false);
    }

    public final void writeToParcel(HwParcel parcel) {
        HwBlob _hidl_blob = new HwBlob(32);
        writeEmbeddedToBlob(_hidl_blob, 0L);
        parcel.writeBuffer(_hidl_blob);
    }

    public static final void writeVectorToParcel(HwParcel parcel, ArrayList<SehImsCall> _hidl_vec) {
        HwBlob _hidl_blob = new HwBlob(16);
        int _hidl_vec_size = _hidl_vec.size();
        _hidl_blob.putInt32(8L, _hidl_vec_size);
        _hidl_blob.putBool(12L, false);
        HwBlob childBlob = new HwBlob(_hidl_vec_size * 32);
        for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
            _hidl_vec.get(_hidl_index_0).writeEmbeddedToBlob(childBlob, _hidl_index_0 * 32);
        }
        _hidl_blob.putBlob(0L, childBlob);
        parcel.writeBuffer(_hidl_blob);
    }

    public final void writeEmbeddedToBlob(HwBlob _hidl_blob, long _hidl_offset) {
        _hidl_blob.putInt32(0 + _hidl_offset, this.state);
        _hidl_blob.putInt32(4 + _hidl_offset, this.type);
        _hidl_blob.putInt32(8 + _hidl_offset, this.isMt);
        _hidl_blob.putInt32(12 + _hidl_offset, this.isMpty);
        _hidl_blob.putString(16 + _hidl_offset, this.number);
    }
}