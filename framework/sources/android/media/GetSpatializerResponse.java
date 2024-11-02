package android.media;

import android.media.ISpatializer;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class GetSpatializerResponse implements Parcelable {
    public static final Parcelable.Creator<GetSpatializerResponse> CREATOR = new Parcelable.Creator<GetSpatializerResponse>() { // from class: android.media.GetSpatializerResponse.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public GetSpatializerResponse createFromParcel(Parcel _aidl_source) {
            GetSpatializerResponse _aidl_out = new GetSpatializerResponse();
            _aidl_out.readFromParcel(_aidl_source);
            return _aidl_out;
        }

        @Override // android.os.Parcelable.Creator
        public GetSpatializerResponse[] newArray(int _aidl_size) {
            return new GetSpatializerResponse[_aidl_size];
        }
    };
    public ISpatializer spatializer;

    /* renamed from: android.media.GetSpatializerResponse$1 */
    /* loaded from: classes2.dex */
    class AnonymousClass1 implements Parcelable.Creator<GetSpatializerResponse> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public GetSpatializerResponse createFromParcel(Parcel _aidl_source) {
            GetSpatializerResponse _aidl_out = new GetSpatializerResponse();
            _aidl_out.readFromParcel(_aidl_source);
            return _aidl_out;
        }

        @Override // android.os.Parcelable.Creator
        public GetSpatializerResponse[] newArray(int _aidl_size) {
            return new GetSpatializerResponse[_aidl_size];
        }
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel _aidl_parcel, int _aidl_flag) {
        int _aidl_start_pos = _aidl_parcel.dataPosition();
        _aidl_parcel.writeInt(0);
        _aidl_parcel.writeStrongInterface(this.spatializer);
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
            } else {
                this.spatializer = ISpatializer.Stub.asInterface(_aidl_parcel.readStrongBinder());
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
