package android.security.metrics;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public final class KeystoreAtomPayload implements Parcelable {
    public static final Parcelable.Creator<KeystoreAtomPayload> CREATOR = new Parcelable.Creator<KeystoreAtomPayload>() { // from class: android.security.metrics.KeystoreAtomPayload.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KeystoreAtomPayload createFromParcel(Parcel _aidl_source) {
            return new KeystoreAtomPayload(_aidl_source);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KeystoreAtomPayload[] newArray(int _aidl_size) {
            return new KeystoreAtomPayload[_aidl_size];
        }
    };
    public static final int crashStats = 8;
    public static final int keyCreationWithAuthInfo = 2;
    public static final int keyCreationWithGeneralInfo = 1;
    public static final int keyCreationWithPurposeAndModesInfo = 3;
    public static final int keyOperationWithGeneralInfo = 6;
    public static final int keyOperationWithPurposeAndModesInfo = 5;
    public static final int keystore2AtomWithOverflow = 4;
    public static final int rkpErrorStats = 7;
    public static final int storageStats = 0;
    private int _tag;
    private Object _value;

    public @interface Tag {
        public static final int crashStats = 8;
        public static final int keyCreationWithAuthInfo = 2;
        public static final int keyCreationWithGeneralInfo = 1;
        public static final int keyCreationWithPurposeAndModesInfo = 3;
        public static final int keyOperationWithGeneralInfo = 6;
        public static final int keyOperationWithPurposeAndModesInfo = 5;
        public static final int keystore2AtomWithOverflow = 4;
        public static final int rkpErrorStats = 7;
        public static final int storageStats = 0;
    }

    public KeystoreAtomPayload() {
        this._tag = 0;
        this._value = null;
    }

    private KeystoreAtomPayload(Parcel _aidl_parcel) {
        readFromParcel(_aidl_parcel);
    }

    private KeystoreAtomPayload(int _tag, Object _value) {
        this._tag = _tag;
        this._value = _value;
    }

    public int getTag() {
        return this._tag;
    }

    public static KeystoreAtomPayload storageStats(StorageStats _value) {
        return new KeystoreAtomPayload(0, _value);
    }

    public StorageStats getStorageStats() {
        _assertTag(0);
        return (StorageStats) this._value;
    }

    public void setStorageStats(StorageStats _value) {
        _set(0, _value);
    }

    public static KeystoreAtomPayload keyCreationWithGeneralInfo(KeyCreationWithGeneralInfo _value) {
        return new KeystoreAtomPayload(1, _value);
    }

    public KeyCreationWithGeneralInfo getKeyCreationWithGeneralInfo() {
        _assertTag(1);
        return (KeyCreationWithGeneralInfo) this._value;
    }

    public void setKeyCreationWithGeneralInfo(KeyCreationWithGeneralInfo _value) {
        _set(1, _value);
    }

    public static KeystoreAtomPayload keyCreationWithAuthInfo(KeyCreationWithAuthInfo _value) {
        return new KeystoreAtomPayload(2, _value);
    }

    public KeyCreationWithAuthInfo getKeyCreationWithAuthInfo() {
        _assertTag(2);
        return (KeyCreationWithAuthInfo) this._value;
    }

    public void setKeyCreationWithAuthInfo(KeyCreationWithAuthInfo _value) {
        _set(2, _value);
    }

    public static KeystoreAtomPayload keyCreationWithPurposeAndModesInfo(KeyCreationWithPurposeAndModesInfo _value) {
        return new KeystoreAtomPayload(3, _value);
    }

    public KeyCreationWithPurposeAndModesInfo getKeyCreationWithPurposeAndModesInfo() {
        _assertTag(3);
        return (KeyCreationWithPurposeAndModesInfo) this._value;
    }

    public void setKeyCreationWithPurposeAndModesInfo(KeyCreationWithPurposeAndModesInfo _value) {
        _set(3, _value);
    }

    public static KeystoreAtomPayload keystore2AtomWithOverflow(Keystore2AtomWithOverflow _value) {
        return new KeystoreAtomPayload(4, _value);
    }

    public Keystore2AtomWithOverflow getKeystore2AtomWithOverflow() {
        _assertTag(4);
        return (Keystore2AtomWithOverflow) this._value;
    }

    public void setKeystore2AtomWithOverflow(Keystore2AtomWithOverflow _value) {
        _set(4, _value);
    }

    public static KeystoreAtomPayload keyOperationWithPurposeAndModesInfo(KeyOperationWithPurposeAndModesInfo _value) {
        return new KeystoreAtomPayload(5, _value);
    }

    public KeyOperationWithPurposeAndModesInfo getKeyOperationWithPurposeAndModesInfo() {
        _assertTag(5);
        return (KeyOperationWithPurposeAndModesInfo) this._value;
    }

    public void setKeyOperationWithPurposeAndModesInfo(KeyOperationWithPurposeAndModesInfo _value) {
        _set(5, _value);
    }

    public static KeystoreAtomPayload keyOperationWithGeneralInfo(KeyOperationWithGeneralInfo _value) {
        return new KeystoreAtomPayload(6, _value);
    }

    public KeyOperationWithGeneralInfo getKeyOperationWithGeneralInfo() {
        _assertTag(6);
        return (KeyOperationWithGeneralInfo) this._value;
    }

    public void setKeyOperationWithGeneralInfo(KeyOperationWithGeneralInfo _value) {
        _set(6, _value);
    }

    public static KeystoreAtomPayload rkpErrorStats(RkpErrorStats _value) {
        return new KeystoreAtomPayload(7, _value);
    }

    public RkpErrorStats getRkpErrorStats() {
        _assertTag(7);
        return (RkpErrorStats) this._value;
    }

    public void setRkpErrorStats(RkpErrorStats _value) {
        _set(7, _value);
    }

    public static KeystoreAtomPayload crashStats(CrashStats _value) {
        return new KeystoreAtomPayload(8, _value);
    }

    public CrashStats getCrashStats() {
        _assertTag(8);
        return (CrashStats) this._value;
    }

    public void setCrashStats(CrashStats _value) {
        _set(8, _value);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel _aidl_parcel, int _aidl_flag) {
        _aidl_parcel.writeInt(this._tag);
        switch (this._tag) {
            case 0:
                _aidl_parcel.writeTypedObject(getStorageStats(), _aidl_flag);
                break;
            case 1:
                _aidl_parcel.writeTypedObject(getKeyCreationWithGeneralInfo(), _aidl_flag);
                break;
            case 2:
                _aidl_parcel.writeTypedObject(getKeyCreationWithAuthInfo(), _aidl_flag);
                break;
            case 3:
                _aidl_parcel.writeTypedObject(getKeyCreationWithPurposeAndModesInfo(), _aidl_flag);
                break;
            case 4:
                _aidl_parcel.writeTypedObject(getKeystore2AtomWithOverflow(), _aidl_flag);
                break;
            case 5:
                _aidl_parcel.writeTypedObject(getKeyOperationWithPurposeAndModesInfo(), _aidl_flag);
                break;
            case 6:
                _aidl_parcel.writeTypedObject(getKeyOperationWithGeneralInfo(), _aidl_flag);
                break;
            case 7:
                _aidl_parcel.writeTypedObject(getRkpErrorStats(), _aidl_flag);
                break;
            case 8:
                _aidl_parcel.writeTypedObject(getCrashStats(), _aidl_flag);
                break;
        }
    }

    public void readFromParcel(Parcel _aidl_parcel) {
        int _aidl_tag = _aidl_parcel.readInt();
        switch (_aidl_tag) {
            case 0:
                StorageStats _aidl_value = (StorageStats) _aidl_parcel.readTypedObject(StorageStats.CREATOR);
                _set(_aidl_tag, _aidl_value);
                return;
            case 1:
                KeyCreationWithGeneralInfo _aidl_value2 = (KeyCreationWithGeneralInfo) _aidl_parcel.readTypedObject(KeyCreationWithGeneralInfo.CREATOR);
                _set(_aidl_tag, _aidl_value2);
                return;
            case 2:
                KeyCreationWithAuthInfo _aidl_value3 = (KeyCreationWithAuthInfo) _aidl_parcel.readTypedObject(KeyCreationWithAuthInfo.CREATOR);
                _set(_aidl_tag, _aidl_value3);
                return;
            case 3:
                KeyCreationWithPurposeAndModesInfo _aidl_value4 = (KeyCreationWithPurposeAndModesInfo) _aidl_parcel.readTypedObject(KeyCreationWithPurposeAndModesInfo.CREATOR);
                _set(_aidl_tag, _aidl_value4);
                return;
            case 4:
                Keystore2AtomWithOverflow _aidl_value5 = (Keystore2AtomWithOverflow) _aidl_parcel.readTypedObject(Keystore2AtomWithOverflow.CREATOR);
                _set(_aidl_tag, _aidl_value5);
                return;
            case 5:
                KeyOperationWithPurposeAndModesInfo _aidl_value6 = (KeyOperationWithPurposeAndModesInfo) _aidl_parcel.readTypedObject(KeyOperationWithPurposeAndModesInfo.CREATOR);
                _set(_aidl_tag, _aidl_value6);
                return;
            case 6:
                KeyOperationWithGeneralInfo _aidl_value7 = (KeyOperationWithGeneralInfo) _aidl_parcel.readTypedObject(KeyOperationWithGeneralInfo.CREATOR);
                _set(_aidl_tag, _aidl_value7);
                return;
            case 7:
                RkpErrorStats _aidl_value8 = (RkpErrorStats) _aidl_parcel.readTypedObject(RkpErrorStats.CREATOR);
                _set(_aidl_tag, _aidl_value8);
                return;
            case 8:
                CrashStats _aidl_value9 = (CrashStats) _aidl_parcel.readTypedObject(CrashStats.CREATOR);
                _set(_aidl_tag, _aidl_value9);
                return;
            default:
                throw new IllegalArgumentException("union: unknown tag: " + _aidl_tag);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        switch (getTag()) {
            case 0:
                int _mask = 0 | describeContents(getStorageStats());
                return _mask;
            case 1:
                int _mask2 = 0 | describeContents(getKeyCreationWithGeneralInfo());
                return _mask2;
            case 2:
                int _mask3 = 0 | describeContents(getKeyCreationWithAuthInfo());
                return _mask3;
            case 3:
                int _mask4 = 0 | describeContents(getKeyCreationWithPurposeAndModesInfo());
                return _mask4;
            case 4:
                int _mask5 = 0 | describeContents(getKeystore2AtomWithOverflow());
                return _mask5;
            case 5:
                int _mask6 = 0 | describeContents(getKeyOperationWithPurposeAndModesInfo());
                return _mask6;
            case 6:
                int _mask7 = 0 | describeContents(getKeyOperationWithGeneralInfo());
                return _mask7;
            case 7:
                int _mask8 = 0 | describeContents(getRkpErrorStats());
                return _mask8;
            case 8:
                int _mask9 = 0 | describeContents(getCrashStats());
                return _mask9;
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
                return "storageStats";
            case 1:
                return "keyCreationWithGeneralInfo";
            case 2:
                return "keyCreationWithAuthInfo";
            case 3:
                return "keyCreationWithPurposeAndModesInfo";
            case 4:
                return "keystore2AtomWithOverflow";
            case 5:
                return "keyOperationWithPurposeAndModesInfo";
            case 6:
                return "keyOperationWithGeneralInfo";
            case 7:
                return "rkpErrorStats";
            case 8:
                return "crashStats";
            default:
                throw new IllegalStateException("unknown field: " + _tag);
        }
    }

    private void _set(int _tag, Object _value) {
        this._tag = _tag;
        this._value = _value;
    }
}
