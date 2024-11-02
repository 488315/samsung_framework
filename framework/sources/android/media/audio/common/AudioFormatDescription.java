package android.media.audio.common;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class AudioFormatDescription implements Parcelable {
    public static final Parcelable.Creator<AudioFormatDescription> CREATOR = new Parcelable.Creator<AudioFormatDescription>() { // from class: android.media.audio.common.AudioFormatDescription.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public AudioFormatDescription createFromParcel(Parcel _aidl_source) {
            AudioFormatDescription _aidl_out = new AudioFormatDescription();
            _aidl_out.readFromParcel(_aidl_source);
            return _aidl_out;
        }

        @Override // android.os.Parcelable.Creator
        public AudioFormatDescription[] newArray(int _aidl_size) {
            return new AudioFormatDescription[_aidl_size];
        }
    };
    public String encoding;
    public byte type = 0;
    public byte pcm = 0;

    /* renamed from: android.media.audio.common.AudioFormatDescription$1 */
    /* loaded from: classes2.dex */
    class AnonymousClass1 implements Parcelable.Creator<AudioFormatDescription> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public AudioFormatDescription createFromParcel(Parcel _aidl_source) {
            AudioFormatDescription _aidl_out = new AudioFormatDescription();
            _aidl_out.readFromParcel(_aidl_source);
            return _aidl_out;
        }

        @Override // android.os.Parcelable.Creator
        public AudioFormatDescription[] newArray(int _aidl_size) {
            return new AudioFormatDescription[_aidl_size];
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
        _aidl_parcel.writeByte(this.type);
        _aidl_parcel.writeByte(this.pcm);
        _aidl_parcel.writeString(this.encoding);
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
            this.type = _aidl_parcel.readByte();
            if (_aidl_parcel.dataPosition() - _aidl_start_pos >= _aidl_parcelable_size) {
                if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
                return;
            }
            this.pcm = _aidl_parcel.readByte();
            if (_aidl_parcel.dataPosition() - _aidl_start_pos >= _aidl_parcelable_size) {
                if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
            } else {
                this.encoding = _aidl_parcel.readString();
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
        _aidl_sj.add("type: " + ((int) this.type));
        _aidl_sj.add("pcm: " + ((int) this.pcm));
        _aidl_sj.add("encoding: " + Objects.toString(this.encoding));
        return "android.media.audio.common.AudioFormatDescription" + _aidl_sj.toString();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || !(other instanceof AudioFormatDescription)) {
            return false;
        }
        AudioFormatDescription that = (AudioFormatDescription) other;
        if (Objects.deepEquals(java.lang.Byte.valueOf(this.type), java.lang.Byte.valueOf(that.type)) && Objects.deepEquals(java.lang.Byte.valueOf(this.pcm), java.lang.Byte.valueOf(that.pcm)) && Objects.deepEquals(this.encoding, that.encoding)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return Arrays.deepHashCode(Arrays.asList(java.lang.Byte.valueOf(this.type), java.lang.Byte.valueOf(this.pcm), this.encoding).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
