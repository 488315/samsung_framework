package android.app.admin;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

/* loaded from: classes.dex */
public final class LongPolicyValue extends PolicyValue<Long> {
    public static final Parcelable.Creator<LongPolicyValue> CREATOR = new Parcelable.Creator<LongPolicyValue>() { // from class: android.app.admin.LongPolicyValue.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public LongPolicyValue createFromParcel(Parcel source) {
            return new LongPolicyValue(source);
        }

        @Override // android.os.Parcelable.Creator
        public LongPolicyValue[] newArray(int size) {
            return new LongPolicyValue[size];
        }
    };

    /* synthetic */ LongPolicyValue(Parcel parcel, LongPolicyValueIA longPolicyValueIA) {
        this(parcel);
    }

    public LongPolicyValue(long value) {
        super(Long.valueOf(value));
    }

    private LongPolicyValue(Parcel source) {
        this(source.readLong());
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LongPolicyValue other = (LongPolicyValue) o;
        return Objects.equals(getValue(), other.getValue());
    }

    public int hashCode() {
        return Objects.hash(getValue());
    }

    public String toString() {
        return "LongPolicyValue { mValue= " + getValue() + " }";
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(getValue().longValue());
    }

    /* renamed from: android.app.admin.LongPolicyValue$1 */
    /* loaded from: classes.dex */
    class AnonymousClass1 implements Parcelable.Creator<LongPolicyValue> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public LongPolicyValue createFromParcel(Parcel source) {
            return new LongPolicyValue(source);
        }

        @Override // android.os.Parcelable.Creator
        public LongPolicyValue[] newArray(int size) {
            return new LongPolicyValue[size];
        }
    }
}
