package android.hardware.gnss;

import android.hardware.scontext.SContextConstants;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class SatelliteVelocityEcef implements Parcelable {
    public static final Parcelable.Creator<SatelliteVelocityEcef> CREATOR = new Parcelable.Creator<SatelliteVelocityEcef>() { // from class: android.hardware.gnss.SatelliteVelocityEcef.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public SatelliteVelocityEcef createFromParcel(Parcel _aidl_source) {
            SatelliteVelocityEcef _aidl_out = new SatelliteVelocityEcef();
            _aidl_out.readFromParcel(_aidl_source);
            return _aidl_out;
        }

        @Override // android.os.Parcelable.Creator
        public SatelliteVelocityEcef[] newArray(int _aidl_size) {
            return new SatelliteVelocityEcef[_aidl_size];
        }
    };
    public double velXMps = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    public double velYMps = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    public double velZMps = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    public double ureRateMps = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;

    /* renamed from: android.hardware.gnss.SatelliteVelocityEcef$1 */
    /* loaded from: classes2.dex */
    class AnonymousClass1 implements Parcelable.Creator<SatelliteVelocityEcef> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public SatelliteVelocityEcef createFromParcel(Parcel _aidl_source) {
            SatelliteVelocityEcef _aidl_out = new SatelliteVelocityEcef();
            _aidl_out.readFromParcel(_aidl_source);
            return _aidl_out;
        }

        @Override // android.os.Parcelable.Creator
        public SatelliteVelocityEcef[] newArray(int _aidl_size) {
            return new SatelliteVelocityEcef[_aidl_size];
        }
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel _aidl_parcel, int _aidl_flag) {
        int _aidl_start_pos = _aidl_parcel.dataPosition();
        _aidl_parcel.writeInt(0);
        _aidl_parcel.writeDouble(this.velXMps);
        _aidl_parcel.writeDouble(this.velYMps);
        _aidl_parcel.writeDouble(this.velZMps);
        _aidl_parcel.writeDouble(this.ureRateMps);
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
            this.velXMps = _aidl_parcel.readDouble();
            if (_aidl_parcel.dataPosition() - _aidl_start_pos >= _aidl_parcelable_size) {
                if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
                return;
            }
            this.velYMps = _aidl_parcel.readDouble();
            if (_aidl_parcel.dataPosition() - _aidl_start_pos >= _aidl_parcelable_size) {
                if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
                return;
            }
            this.velZMps = _aidl_parcel.readDouble();
            if (_aidl_parcel.dataPosition() - _aidl_start_pos >= _aidl_parcelable_size) {
                if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
            } else {
                this.ureRateMps = _aidl_parcel.readDouble();
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
        return 0;
    }
}
