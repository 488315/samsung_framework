package android.hardware.tv.tuner;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public final class FrontendScanMessageStandard implements Parcelable {
    public static final Parcelable.Creator<FrontendScanMessageStandard> CREATOR = new Parcelable.Creator<FrontendScanMessageStandard>() { // from class: android.hardware.tv.tuner.FrontendScanMessageStandard.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FrontendScanMessageStandard createFromParcel(Parcel _aidl_source) {
            return new FrontendScanMessageStandard(_aidl_source);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FrontendScanMessageStandard[] newArray(int _aidl_size) {
            return new FrontendScanMessageStandard[_aidl_size];
        }
    };
    public static final int sStd = 0;
    public static final int sifStd = 2;
    public static final int tStd = 1;
    private int _tag;
    private Object _value;

    public @interface Tag {
        public static final int sStd = 0;
        public static final int sifStd = 2;
        public static final int tStd = 1;
    }

    public FrontendScanMessageStandard() {
        this._tag = 0;
        this._value = (byte) 0;
    }

    private FrontendScanMessageStandard(Parcel _aidl_parcel) {
        readFromParcel(_aidl_parcel);
    }

    private FrontendScanMessageStandard(int _tag, Object _value) {
        this._tag = _tag;
        this._value = _value;
    }

    public int getTag() {
        return this._tag;
    }

    public static FrontendScanMessageStandard sStd(byte _value) {
        return new FrontendScanMessageStandard(0, Byte.valueOf(_value));
    }

    public byte getSStd() {
        _assertTag(0);
        return ((Byte) this._value).byteValue();
    }

    public void setSStd(byte _value) {
        _set(0, Byte.valueOf(_value));
    }

    public static FrontendScanMessageStandard tStd(byte _value) {
        return new FrontendScanMessageStandard(1, Byte.valueOf(_value));
    }

    public byte getTStd() {
        _assertTag(1);
        return ((Byte) this._value).byteValue();
    }

    public void setTStd(byte _value) {
        _set(1, Byte.valueOf(_value));
    }

    public static FrontendScanMessageStandard sifStd(int _value) {
        return new FrontendScanMessageStandard(2, Integer.valueOf(_value));
    }

    public int getSifStd() {
        _assertTag(2);
        return ((Integer) this._value).intValue();
    }

    public void setSifStd(int _value) {
        _set(2, Integer.valueOf(_value));
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
                _aidl_parcel.writeByte(getSStd());
                break;
            case 1:
                _aidl_parcel.writeByte(getTStd());
                break;
            case 2:
                _aidl_parcel.writeInt(getSifStd());
                break;
        }
    }

    public void readFromParcel(Parcel _aidl_parcel) {
        int _aidl_tag = _aidl_parcel.readInt();
        switch (_aidl_tag) {
            case 0:
                byte _aidl_value = _aidl_parcel.readByte();
                _set(_aidl_tag, Byte.valueOf(_aidl_value));
                return;
            case 1:
                byte _aidl_value2 = _aidl_parcel.readByte();
                _set(_aidl_tag, Byte.valueOf(_aidl_value2));
                return;
            case 2:
                int _aidl_value3 = _aidl_parcel.readInt();
                _set(_aidl_tag, Integer.valueOf(_aidl_value3));
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
                return "sStd";
            case 1:
                return "tStd";
            case 2:
                return "sifStd";
            default:
                throw new IllegalStateException("unknown field: " + _tag);
        }
    }

    private void _set(int _tag, Object _value) {
        this._tag = _tag;
        this._value = _value;
    }
}
