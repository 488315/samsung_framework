package android.hardware.radio.data;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public final class QosFilterTypeOfService implements Parcelable {
    public static final Parcelable.Creator<QosFilterTypeOfService> CREATOR = new Parcelable.Creator<QosFilterTypeOfService>() { // from class: android.hardware.radio.data.QosFilterTypeOfService.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public QosFilterTypeOfService createFromParcel(Parcel _aidl_source) {
            return new QosFilterTypeOfService(_aidl_source);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public QosFilterTypeOfService[] newArray(int _aidl_size) {
            return new QosFilterTypeOfService[_aidl_size];
        }
    };
    public static final int noinit = 0;
    public static final int value = 1;
    private int _tag;
    private Object _value;

    public @interface Tag {
        public static final int noinit = 0;
        public static final int value = 1;
    }

    public QosFilterTypeOfService() {
        this._tag = 0;
        this._value = false;
    }

    private QosFilterTypeOfService(Parcel _aidl_parcel) {
        readFromParcel(_aidl_parcel);
    }

    private QosFilterTypeOfService(int _tag, Object _value) {
        this._tag = _tag;
        this._value = _value;
    }

    public int getTag() {
        return this._tag;
    }

    public static QosFilterTypeOfService noinit(boolean _value) {
        return new QosFilterTypeOfService(0, Boolean.valueOf(_value));
    }

    public boolean getNoinit() {
        _assertTag(0);
        return ((Boolean) this._value).booleanValue();
    }

    public void setNoinit(boolean _value) {
        _set(0, Boolean.valueOf(_value));
    }

    public static QosFilterTypeOfService value(byte _value) {
        return new QosFilterTypeOfService(1, Byte.valueOf(_value));
    }

    public byte getValue() {
        _assertTag(1);
        return ((Byte) this._value).byteValue();
    }

    public void setValue(byte _value) {
        _set(1, Byte.valueOf(_value));
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
                _aidl_parcel.writeBoolean(getNoinit());
                break;
            case 1:
                _aidl_parcel.writeByte(getValue());
                break;
        }
    }

    public void readFromParcel(Parcel _aidl_parcel) {
        int _aidl_tag = _aidl_parcel.readInt();
        switch (_aidl_tag) {
            case 0:
                boolean _aidl_value = _aidl_parcel.readBoolean();
                _set(_aidl_tag, Boolean.valueOf(_aidl_value));
                return;
            case 1:
                byte _aidl_value2 = _aidl_parcel.readByte();
                _set(_aidl_tag, Byte.valueOf(_aidl_value2));
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

    public String toString() {
        switch (this._tag) {
            case 0:
                return "QosFilterTypeOfService.noinit(" + getNoinit() + NavigationBarInflaterView.KEY_CODE_END;
            case 1:
                return "QosFilterTypeOfService.value(" + ((int) getValue()) + NavigationBarInflaterView.KEY_CODE_END;
            default:
                throw new IllegalStateException("unknown field: " + this._tag);
        }
    }

    private void _assertTag(int tag) {
        if (getTag() != tag) {
            throw new IllegalStateException("bad access: " + _tagString(tag) + ", " + _tagString(getTag()) + " is available.");
        }
    }

    private String _tagString(int _tag) {
        switch (_tag) {
            case 0:
                return "noinit";
            case 1:
                return "value";
            default:
                throw new IllegalStateException("unknown field: " + _tag);
        }
    }

    private void _set(int _tag, Object _value) {
        this._tag = _tag;
        this._value = _value;
    }
}
