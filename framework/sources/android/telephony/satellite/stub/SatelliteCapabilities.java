package android.telephony.satellite.stub;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.satellite.AntennaPosition;

/* loaded from: classes3.dex */
public class SatelliteCapabilities implements Parcelable {
    public static final Parcelable.Creator<SatelliteCapabilities> CREATOR = new Parcelable.Creator<SatelliteCapabilities>() { // from class: android.telephony.satellite.stub.SatelliteCapabilities.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public SatelliteCapabilities createFromParcel(Parcel _aidl_source) {
            SatelliteCapabilities _aidl_out = new SatelliteCapabilities();
            _aidl_out.readFromParcel(_aidl_source);
            return _aidl_out;
        }

        @Override // android.os.Parcelable.Creator
        public SatelliteCapabilities[] newArray(int _aidl_size) {
            return new SatelliteCapabilities[_aidl_size];
        }
    };
    public int[] antennaPositionKeys;
    public AntennaPosition[] antennaPositionValues;
    public boolean isPointingRequired = false;
    public int maxBytesPerOutgoingDatagram = 0;
    public int[] supportedRadioTechnologies;

    /* renamed from: android.telephony.satellite.stub.SatelliteCapabilities$1 */
    /* loaded from: classes3.dex */
    class AnonymousClass1 implements Parcelable.Creator<SatelliteCapabilities> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public SatelliteCapabilities createFromParcel(Parcel _aidl_source) {
            SatelliteCapabilities _aidl_out = new SatelliteCapabilities();
            _aidl_out.readFromParcel(_aidl_source);
            return _aidl_out;
        }

        @Override // android.os.Parcelable.Creator
        public SatelliteCapabilities[] newArray(int _aidl_size) {
            return new SatelliteCapabilities[_aidl_size];
        }
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel _aidl_parcel, int _aidl_flag) {
        int _aidl_start_pos = _aidl_parcel.dataPosition();
        _aidl_parcel.writeInt(0);
        _aidl_parcel.writeIntArray(this.supportedRadioTechnologies);
        _aidl_parcel.writeBoolean(this.isPointingRequired);
        _aidl_parcel.writeInt(this.maxBytesPerOutgoingDatagram);
        _aidl_parcel.writeIntArray(this.antennaPositionKeys);
        _aidl_parcel.writeTypedArray(this.antennaPositionValues, _aidl_flag);
        int _aidl_end_pos = _aidl_parcel.dataPosition();
        _aidl_parcel.setDataPosition(_aidl_start_pos);
        _aidl_parcel.writeInt(_aidl_end_pos - _aidl_start_pos);
        _aidl_parcel.setDataPosition(_aidl_end_pos);
    }

    public final void readFromParcel(Parcel _aidl_parcel) {
        int _aidl_start_pos = _aidl_parcel.dataPosition();
        int _aidl_parcelable_size = _aidl_parcel.readInt();
        try {
            if (_aidl_parcelable_size < 4) {
                throw new BadParcelableException("Parcelable too small");
            }
            if (_aidl_parcel.dataPosition() - _aidl_start_pos >= _aidl_parcelable_size) {
                if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
                return;
            }
            this.supportedRadioTechnologies = _aidl_parcel.createIntArray();
            if (_aidl_parcel.dataPosition() - _aidl_start_pos >= _aidl_parcelable_size) {
                if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
                return;
            }
            this.isPointingRequired = _aidl_parcel.readBoolean();
            if (_aidl_parcel.dataPosition() - _aidl_start_pos >= _aidl_parcelable_size) {
                if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
                return;
            }
            this.maxBytesPerOutgoingDatagram = _aidl_parcel.readInt();
            if (_aidl_parcel.dataPosition() - _aidl_start_pos >= _aidl_parcelable_size) {
                if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
                return;
            }
            this.antennaPositionKeys = _aidl_parcel.createIntArray();
            if (_aidl_parcel.dataPosition() - _aidl_start_pos >= _aidl_parcelable_size) {
                if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
            } else {
                this.antennaPositionValues = (AntennaPosition[]) _aidl_parcel.createTypedArray(AntennaPosition.CREATOR);
                if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
            }
        } catch (Throwable th) {
            if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
            throw th;
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        int _mask = 0 | describeContents(this.antennaPositionValues);
        return _mask;
    }

    private int describeContents(Object _v) {
        if (_v == null) {
            return 0;
        }
        if (_v instanceof Object[]) {
            int _mask = 0;
            for (Object o : (Object[]) _v) {
                _mask |= describeContents(o);
            }
            return _mask;
        }
        if (!(_v instanceof Parcelable)) {
            return 0;
        }
        return ((Parcelable) _v).describeContents();
    }
}
