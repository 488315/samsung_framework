package android.hardware.contexthub;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public class HostEndpointInfo implements Parcelable {
    public static final Parcelable.Creator<HostEndpointInfo> CREATOR = new Parcelable.Creator<HostEndpointInfo>() { // from class: android.hardware.contexthub.HostEndpointInfo.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public HostEndpointInfo createFromParcel(Parcel _aidl_source) {
            HostEndpointInfo _aidl_out = new HostEndpointInfo();
            _aidl_out.readFromParcel(_aidl_source);
            return _aidl_out;
        }

        @Override // android.os.Parcelable.Creator
        public HostEndpointInfo[] newArray(int _aidl_size) {
            return new HostEndpointInfo[_aidl_size];
        }
    };
    public String attributionTag;
    public char hostEndpointId = 0;
    public String packageName;
    public int type;

    /* loaded from: classes.dex */
    public @interface Type {
        public static final int APP = 2;
        public static final int FRAMEWORK = 1;
        public static final int NATIVE = 3;
    }

    /* renamed from: android.hardware.contexthub.HostEndpointInfo$1 */
    /* loaded from: classes.dex */
    class AnonymousClass1 implements Parcelable.Creator<HostEndpointInfo> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public HostEndpointInfo createFromParcel(Parcel _aidl_source) {
            HostEndpointInfo _aidl_out = new HostEndpointInfo();
            _aidl_out.readFromParcel(_aidl_source);
            return _aidl_out;
        }

        @Override // android.os.Parcelable.Creator
        public HostEndpointInfo[] newArray(int _aidl_size) {
            return new HostEndpointInfo[_aidl_size];
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
        _aidl_parcel.writeInt(this.hostEndpointId);
        _aidl_parcel.writeInt(this.type);
        _aidl_parcel.writeString(this.packageName);
        _aidl_parcel.writeString(this.attributionTag);
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
            this.hostEndpointId = (char) _aidl_parcel.readInt();
            if (_aidl_parcel.dataPosition() - _aidl_start_pos >= _aidl_parcelable_size) {
                if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
                return;
            }
            this.type = _aidl_parcel.readInt();
            if (_aidl_parcel.dataPosition() - _aidl_start_pos >= _aidl_parcelable_size) {
                if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
                return;
            }
            this.packageName = _aidl_parcel.readString();
            if (_aidl_parcel.dataPosition() - _aidl_start_pos >= _aidl_parcelable_size) {
                if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
            } else {
                this.attributionTag = _aidl_parcel.readString();
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
