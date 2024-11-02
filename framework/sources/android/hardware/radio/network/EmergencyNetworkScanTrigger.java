package android.hardware.radio.network;

import android.hardware.radio.AccessNetwork$$;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class EmergencyNetworkScanTrigger implements Parcelable {
    public static final Parcelable.Creator<EmergencyNetworkScanTrigger> CREATOR = new Parcelable.Creator<EmergencyNetworkScanTrigger>() { // from class: android.hardware.radio.network.EmergencyNetworkScanTrigger.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public EmergencyNetworkScanTrigger createFromParcel(Parcel _aidl_source) {
            EmergencyNetworkScanTrigger _aidl_out = new EmergencyNetworkScanTrigger();
            _aidl_out.readFromParcel(_aidl_source);
            return _aidl_out;
        }

        @Override // android.os.Parcelable.Creator
        public EmergencyNetworkScanTrigger[] newArray(int _aidl_size) {
            return new EmergencyNetworkScanTrigger[_aidl_size];
        }
    };
    public int[] accessNetwork;
    public int scanType;

    /* renamed from: android.hardware.radio.network.EmergencyNetworkScanTrigger$1 */
    /* loaded from: classes2.dex */
    class AnonymousClass1 implements Parcelable.Creator<EmergencyNetworkScanTrigger> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public EmergencyNetworkScanTrigger createFromParcel(Parcel _aidl_source) {
            EmergencyNetworkScanTrigger _aidl_out = new EmergencyNetworkScanTrigger();
            _aidl_out.readFromParcel(_aidl_source);
            return _aidl_out;
        }

        @Override // android.os.Parcelable.Creator
        public EmergencyNetworkScanTrigger[] newArray(int _aidl_size) {
            return new EmergencyNetworkScanTrigger[_aidl_size];
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
        _aidl_parcel.writeIntArray(this.accessNetwork);
        _aidl_parcel.writeInt(this.scanType);
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
            this.accessNetwork = _aidl_parcel.createIntArray();
            if (_aidl_parcel.dataPosition() - _aidl_start_pos >= _aidl_parcelable_size) {
                if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
            } else {
                this.scanType = _aidl_parcel.readInt();
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

    public String toString() {
        StringJoiner _aidl_sj = new StringJoiner(", ", "{", "}");
        _aidl_sj.add("accessNetwork: " + AccessNetwork$$.arrayToString(this.accessNetwork));
        _aidl_sj.add("scanType: " + EmergencyScanType$$.toString(this.scanType));
        return "android.hardware.radio.network.EmergencyNetworkScanTrigger" + _aidl_sj.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
