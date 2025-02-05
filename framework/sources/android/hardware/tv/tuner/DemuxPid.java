package android.hardware.tv.tuner;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public final class DemuxPid implements Parcelable {
    public static final Parcelable.Creator<DemuxPid> CREATOR = new Parcelable.Creator<DemuxPid>() { // from class: android.hardware.tv.tuner.DemuxPid.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DemuxPid createFromParcel(Parcel _aidl_source) {
            return new DemuxPid(_aidl_source);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DemuxPid[] newArray(int _aidl_size) {
            return new DemuxPid[_aidl_size];
        }
    };
    public static final int mmtpPid = 1;
    public static final int tPid = 0;
    private int _tag;
    private Object _value;

    public @interface Tag {
        public static final int mmtpPid = 1;
        public static final int tPid = 0;
    }

    public DemuxPid() {
        this._tag = 0;
        this._value = 0;
    }

    private DemuxPid(Parcel _aidl_parcel) {
        readFromParcel(_aidl_parcel);
    }

    private DemuxPid(int _tag, Object _value) {
        this._tag = _tag;
        this._value = _value;
    }

    public int getTag() {
        return this._tag;
    }

    public static DemuxPid tPid(int _value) {
        return new DemuxPid(0, Integer.valueOf(_value));
    }

    public int getTPid() {
        _assertTag(0);
        return ((Integer) this._value).intValue();
    }

    public void setTPid(int _value) {
        _set(0, Integer.valueOf(_value));
    }

    public static DemuxPid mmtpPid(int _value) {
        return new DemuxPid(1, Integer.valueOf(_value));
    }

    public int getMmtpPid() {
        _assertTag(1);
        return ((Integer) this._value).intValue();
    }

    public void setMmtpPid(int _value) {
        _set(1, Integer.valueOf(_value));
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel _aidl_parcel, int _aidl_flag) {
        _aidl_parcel.writeInt(this._tag);
        switch (this._tag) {
            case 0:
                _aidl_parcel.writeInt(getTPid());
                break;
            case 1:
                _aidl_parcel.writeInt(getMmtpPid());
                break;
        }
    }

    public void readFromParcel(Parcel _aidl_parcel) {
        int _aidl_tag = _aidl_parcel.readInt();
        switch (_aidl_tag) {
            case 0:
                int _aidl_value = _aidl_parcel.readInt();
                _set(_aidl_tag, Integer.valueOf(_aidl_value));
                return;
            case 1:
                int _aidl_value2 = _aidl_parcel.readInt();
                _set(_aidl_tag, Integer.valueOf(_aidl_value2));
                return;
            default:
                throw new IllegalArgumentException("union: unknown tag: " + _aidl_tag);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        getTag();
        return 0;
    }

    private void _assertTag(int tag) {
        if (getTag() != tag) {
            throw new IllegalStateException("bad access: " + _tagString(tag) + ", " + _tagString(getTag()) + " is available.");
        }
    }

    private String _tagString(int _tag) {
        switch (_tag) {
            case 0:
                return "tPid";
            case 1:
                return "mmtpPid";
            default:
                throw new IllegalStateException("unknown field: " + _tag);
        }
    }

    private void _set(int _tag, Object _value) {
        this._tag = _tag;
        this._value = _value;
    }
}
