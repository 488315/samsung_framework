package android.hardware.tv.tuner;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public final class FrontendBandwidth implements Parcelable {
    public static final Parcelable.Creator<FrontendBandwidth> CREATOR = new Parcelable.Creator<FrontendBandwidth>() { // from class: android.hardware.tv.tuner.FrontendBandwidth.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FrontendBandwidth createFromParcel(Parcel _aidl_source) {
            return new FrontendBandwidth(_aidl_source);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FrontendBandwidth[] newArray(int _aidl_size) {
            return new FrontendBandwidth[_aidl_size];
        }
    };
    public static final int atsc3 = 0;
    public static final int dtmb = 4;
    public static final int dvbc = 1;
    public static final int dvbt = 2;
    public static final int isdbt = 3;
    private int _tag;
    private Object _value;

    public @interface Tag {
        public static final int atsc3 = 0;
        public static final int dtmb = 4;
        public static final int dvbc = 1;
        public static final int dvbt = 2;
        public static final int isdbt = 3;
    }

    public FrontendBandwidth() {
        this._tag = 0;
        this._value = 0;
    }

    private FrontendBandwidth(Parcel _aidl_parcel) {
        readFromParcel(_aidl_parcel);
    }

    private FrontendBandwidth(int _tag, Object _value) {
        this._tag = _tag;
        this._value = _value;
    }

    public int getTag() {
        return this._tag;
    }

    public static FrontendBandwidth atsc3(int _value) {
        return new FrontendBandwidth(0, Integer.valueOf(_value));
    }

    public int getAtsc3() {
        _assertTag(0);
        return ((Integer) this._value).intValue();
    }

    public void setAtsc3(int _value) {
        _set(0, Integer.valueOf(_value));
    }

    public static FrontendBandwidth dvbc(int _value) {
        return new FrontendBandwidth(1, Integer.valueOf(_value));
    }

    public int getDvbc() {
        _assertTag(1);
        return ((Integer) this._value).intValue();
    }

    public void setDvbc(int _value) {
        _set(1, Integer.valueOf(_value));
    }

    public static FrontendBandwidth dvbt(int _value) {
        return new FrontendBandwidth(2, Integer.valueOf(_value));
    }

    public int getDvbt() {
        _assertTag(2);
        return ((Integer) this._value).intValue();
    }

    public void setDvbt(int _value) {
        _set(2, Integer.valueOf(_value));
    }

    public static FrontendBandwidth isdbt(int _value) {
        return new FrontendBandwidth(3, Integer.valueOf(_value));
    }

    public int getIsdbt() {
        _assertTag(3);
        return ((Integer) this._value).intValue();
    }

    public void setIsdbt(int _value) {
        _set(3, Integer.valueOf(_value));
    }

    public static FrontendBandwidth dtmb(int _value) {
        return new FrontendBandwidth(4, Integer.valueOf(_value));
    }

    public int getDtmb() {
        _assertTag(4);
        return ((Integer) this._value).intValue();
    }

    public void setDtmb(int _value) {
        _set(4, Integer.valueOf(_value));
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
                _aidl_parcel.writeInt(getAtsc3());
                break;
            case 1:
                _aidl_parcel.writeInt(getDvbc());
                break;
            case 2:
                _aidl_parcel.writeInt(getDvbt());
                break;
            case 3:
                _aidl_parcel.writeInt(getIsdbt());
                break;
            case 4:
                _aidl_parcel.writeInt(getDtmb());
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
            case 2:
                int _aidl_value3 = _aidl_parcel.readInt();
                _set(_aidl_tag, Integer.valueOf(_aidl_value3));
                return;
            case 3:
                int _aidl_value4 = _aidl_parcel.readInt();
                _set(_aidl_tag, Integer.valueOf(_aidl_value4));
                return;
            case 4:
                int _aidl_value5 = _aidl_parcel.readInt();
                _set(_aidl_tag, Integer.valueOf(_aidl_value5));
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
                return "atsc3";
            case 1:
                return "dvbc";
            case 2:
                return "dvbt";
            case 3:
                return "isdbt";
            case 4:
                return "dtmb";
            default:
                throw new IllegalStateException("unknown field: " + _tag);
        }
    }

    private void _set(int _tag, Object _value) {
        this._tag = _tag;
        this._value = _value;
    }
}
