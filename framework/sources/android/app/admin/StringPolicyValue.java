package android.app.admin;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

/* loaded from: classes.dex */
public final class StringPolicyValue extends PolicyValue<String> {
    public static final Parcelable.Creator<StringPolicyValue> CREATOR = new Parcelable.Creator<StringPolicyValue>() { // from class: android.app.admin.StringPolicyValue.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public StringPolicyValue createFromParcel(Parcel source) {
            return new StringPolicyValue(source);
        }

        @Override // android.os.Parcelable.Creator
        public StringPolicyValue[] newArray(int size) {
            return new StringPolicyValue[size];
        }
    };

    /* synthetic */ StringPolicyValue(Parcel parcel, StringPolicyValueIA stringPolicyValueIA) {
        this(parcel);
    }

    public StringPolicyValue(String value) {
        super(value);
    }

    private StringPolicyValue(Parcel source) {
        super(source.readString());
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        StringPolicyValue other = (StringPolicyValue) o;
        return Objects.equals(getValue(), other.getValue());
    }

    public int hashCode() {
        return Objects.hash(getValue());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String toString() {
        return "StringPolicyValue { " + getValue() + " }";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getValue());
    }

    /* renamed from: android.app.admin.StringPolicyValue$1 */
    /* loaded from: classes.dex */
    class AnonymousClass1 implements Parcelable.Creator<StringPolicyValue> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public StringPolicyValue createFromParcel(Parcel source) {
            return new StringPolicyValue(source);
        }

        @Override // android.os.Parcelable.Creator
        public StringPolicyValue[] newArray(int size) {
            return new StringPolicyValue[size];
        }
    }
}
