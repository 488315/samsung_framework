package android.hardware.tv.tuner;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public final class DemuxIpFilterSettingsFilterSettings implements Parcelable {
    public static final Parcelable.Creator<DemuxIpFilterSettingsFilterSettings> CREATOR = new Parcelable.Creator<DemuxIpFilterSettingsFilterSettings>() { // from class: android.hardware.tv.tuner.DemuxIpFilterSettingsFilterSettings.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DemuxIpFilterSettingsFilterSettings createFromParcel(Parcel _aidl_source) {
            return new DemuxIpFilterSettingsFilterSettings(_aidl_source);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DemuxIpFilterSettingsFilterSettings[] newArray(int _aidl_size) {
            return new DemuxIpFilterSettingsFilterSettings[_aidl_size];
        }
    };
    public static final int bPassthrough = 2;
    public static final int noinit = 0;
    public static final int section = 1;
    private int _tag;
    private Object _value;

    public @interface Tag {
        public static final int bPassthrough = 2;
        public static final int noinit = 0;
        public static final int section = 1;
    }

    public DemuxIpFilterSettingsFilterSettings() {
        this._tag = 0;
        this._value = false;
    }

    private DemuxIpFilterSettingsFilterSettings(Parcel _aidl_parcel) {
        readFromParcel(_aidl_parcel);
    }

    private DemuxIpFilterSettingsFilterSettings(int _tag, Object _value) {
        this._tag = _tag;
        this._value = _value;
    }

    public int getTag() {
        return this._tag;
    }

    public static DemuxIpFilterSettingsFilterSettings noinit(boolean _value) {
        return new DemuxIpFilterSettingsFilterSettings(0, Boolean.valueOf(_value));
    }

    public boolean getNoinit() {
        _assertTag(0);
        return ((Boolean) this._value).booleanValue();
    }

    public void setNoinit(boolean _value) {
        _set(0, Boolean.valueOf(_value));
    }

    public static DemuxIpFilterSettingsFilterSettings section(DemuxFilterSectionSettings _value) {
        return new DemuxIpFilterSettingsFilterSettings(1, _value);
    }

    public DemuxFilterSectionSettings getSection() {
        _assertTag(1);
        return (DemuxFilterSectionSettings) this._value;
    }

    public void setSection(DemuxFilterSectionSettings _value) {
        _set(1, _value);
    }

    public static DemuxIpFilterSettingsFilterSettings bPassthrough(boolean _value) {
        return new DemuxIpFilterSettingsFilterSettings(2, Boolean.valueOf(_value));
    }

    public boolean getBPassthrough() {
        _assertTag(2);
        return ((Boolean) this._value).booleanValue();
    }

    public void setBPassthrough(boolean _value) {
        _set(2, Boolean.valueOf(_value));
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
                _aidl_parcel.writeTypedObject(getSection(), _aidl_flag);
                break;
            case 2:
                _aidl_parcel.writeBoolean(getBPassthrough());
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
                DemuxFilterSectionSettings _aidl_value2 = (DemuxFilterSectionSettings) _aidl_parcel.readTypedObject(DemuxFilterSectionSettings.CREATOR);
                _set(_aidl_tag, _aidl_value2);
                return;
            case 2:
                boolean _aidl_value3 = _aidl_parcel.readBoolean();
                _set(_aidl_tag, Boolean.valueOf(_aidl_value3));
                return;
            default:
                throw new IllegalArgumentException("union: unknown tag: " + _aidl_tag);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        switch (getTag()) {
            case 1:
                int _mask = 0 | describeContents(getSection());
                return _mask;
            default:
                return 0;
        }
    }

    private int describeContents(Object _v) {
        if (_v == null || !(_v instanceof Parcelable)) {
            return 0;
        }
        return ((Parcelable) _v).describeContents();
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
                return "section";
            case 2:
                return "bPassthrough";
            default:
                throw new IllegalStateException("unknown field: " + _tag);
        }
    }

    private void _set(int _tag, Object _value) {
        this._tag = _tag;
        this._value = _value;
    }
}
