package android.hardware.radio.V1_5;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class ClosedSubscriberGroupInfo {
    public boolean csgIndication = false;
    public String homeNodebName = new String();
    public int csgIdentity = 0;

    public final boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }
        if (otherObject == null || otherObject.getClass() != ClosedSubscriberGroupInfo.class) {
            return false;
        }
        ClosedSubscriberGroupInfo other = (ClosedSubscriberGroupInfo) otherObject;
        if (this.csgIndication == other.csgIndication && HidlSupport.deepEquals(this.homeNodebName, other.homeNodebName) && this.csgIdentity == other.csgIdentity) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Boolean.valueOf(this.csgIndication))), Integer.valueOf(HidlSupport.deepHashCode(this.homeNodebName)), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.csgIdentity))));
    }

    public final String toString() {
        return "{.csgIndication = " + this.csgIndication + ", .homeNodebName = " + this.homeNodebName + ", .csgIdentity = " + this.csgIdentity + "}";
    }

    public final void readFromParcel(HwParcel parcel) {
        HwBlob blob = parcel.readBuffer(32L);
        readEmbeddedFromParcel(parcel, blob, 0L);
    }

    public static final ArrayList<ClosedSubscriberGroupInfo> readVectorFromParcel(HwParcel parcel) {
        ArrayList<ClosedSubscriberGroupInfo> _hidl_vec = new ArrayList<>();
        HwBlob _hidl_blob = parcel.readBuffer(16L);
        int _hidl_vec_size = _hidl_blob.getInt32(8L);
        HwBlob childBlob = parcel.readEmbeddedBuffer(_hidl_vec_size * 32, _hidl_blob.handle(), 0L, true);
        _hidl_vec.clear();
        for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
            ClosedSubscriberGroupInfo _hidl_vec_element = new ClosedSubscriberGroupInfo();
            _hidl_vec_element.readEmbeddedFromParcel(parcel, childBlob, _hidl_index_0 * 32);
            _hidl_vec.add(_hidl_vec_element);
        }
        return _hidl_vec;
    }

    public final void readEmbeddedFromParcel(HwParcel parcel, HwBlob _hidl_blob, long _hidl_offset) {
        this.csgIndication = _hidl_blob.getBool(_hidl_offset + 0);
        this.homeNodebName = _hidl_blob.getString(_hidl_offset + 8);
        parcel.readEmbeddedBuffer(r6.getBytes().length + 1, _hidl_blob.handle(), _hidl_offset + 8 + 0, false);
        this.csgIdentity = _hidl_blob.getInt32(_hidl_offset + 24);
    }

    public final void writeToParcel(HwParcel parcel) {
        HwBlob _hidl_blob = new HwBlob(32);
        writeEmbeddedToBlob(_hidl_blob, 0L);
        parcel.writeBuffer(_hidl_blob);
    }

    public static final void writeVectorToParcel(HwParcel parcel, ArrayList<ClosedSubscriberGroupInfo> _hidl_vec) {
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
        _hidl_blob.putBool(0 + _hidl_offset, this.csgIndication);
        _hidl_blob.putString(8 + _hidl_offset, this.homeNodebName);
        _hidl_blob.putInt32(24 + _hidl_offset, this.csgIdentity);
    }
}