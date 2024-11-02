package android.telephony.ims;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

@SystemApi
/* loaded from: classes3.dex */
public final class SipDialogState implements Parcelable {
    public static final Parcelable.Creator<SipDialogState> CREATOR = new Parcelable.Creator<SipDialogState>() { // from class: android.telephony.ims.SipDialogState.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public SipDialogState createFromParcel(Parcel in) {
            return new SipDialogState(in);
        }

        @Override // android.os.Parcelable.Creator
        public SipDialogState[] newArray(int size) {
            return new SipDialogState[size];
        }
    };
    public static final int STATE_CLOSED = 2;
    public static final int STATE_CONFIRMED = 1;
    public static final int STATE_EARLY = 0;
    private final int mState;

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes3.dex */
    public @interface SipDialogStateCode {
    }

    /* synthetic */ SipDialogState(Parcel parcel, SipDialogStateIA sipDialogStateIA) {
        this(parcel);
    }

    /* synthetic */ SipDialogState(Builder builder, SipDialogStateIA sipDialogStateIA) {
        this(builder);
    }

    /* loaded from: classes3.dex */
    public static final class Builder {
        private int mState;

        public Builder(int state) {
            this.mState = 0;
            this.mState = state;
        }

        public SipDialogState build() {
            return new SipDialogState(this);
        }
    }

    private SipDialogState(Builder builder) {
        this.mState = builder.mState;
    }

    private SipDialogState(Parcel in) {
        this.mState = in.readInt();
    }

    public int getState() {
        return this.mState;
    }

    /* renamed from: android.telephony.ims.SipDialogState$1 */
    /* loaded from: classes3.dex */
    class AnonymousClass1 implements Parcelable.Creator<SipDialogState> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public SipDialogState createFromParcel(Parcel in) {
            return new SipDialogState(in);
        }

        @Override // android.os.Parcelable.Creator
        public SipDialogState[] newArray(int size) {
            return new SipDialogState[size];
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mState);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SipDialogState sipDialog = (SipDialogState) o;
        if (this.mState == sipDialog.mState) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mState));
    }
}
