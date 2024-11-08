package com.android.ims.internal.uce.presence;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes4.dex */
public class PresSubscriptionState implements Parcelable {
    public static final Parcelable.Creator<PresSubscriptionState> CREATOR = new Parcelable.Creator<PresSubscriptionState>() { // from class: com.android.ims.internal.uce.presence.PresSubscriptionState.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public PresSubscriptionState createFromParcel(Parcel source) {
            return new PresSubscriptionState(source);
        }

        @Override // android.os.Parcelable.Creator
        public PresSubscriptionState[] newArray(int size) {
            return new PresSubscriptionState[size];
        }
    };
    public static final int UCE_PRES_SUBSCRIPTION_STATE_ACTIVE = 0;
    public static final int UCE_PRES_SUBSCRIPTION_STATE_PENDING = 1;
    public static final int UCE_PRES_SUBSCRIPTION_STATE_TERMINATED = 2;
    public static final int UCE_PRES_SUBSCRIPTION_STATE_UNKNOWN = 3;
    private int mPresSubscriptionState;

    /* synthetic */ PresSubscriptionState(Parcel parcel, PresSubscriptionStateIA presSubscriptionStateIA) {
        this(parcel);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mPresSubscriptionState);
    }

    /* renamed from: com.android.ims.internal.uce.presence.PresSubscriptionState$1 */
    /* loaded from: classes4.dex */
    class AnonymousClass1 implements Parcelable.Creator<PresSubscriptionState> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public PresSubscriptionState createFromParcel(Parcel source) {
            return new PresSubscriptionState(source);
        }

        @Override // android.os.Parcelable.Creator
        public PresSubscriptionState[] newArray(int size) {
            return new PresSubscriptionState[size];
        }
    }

    private PresSubscriptionState(Parcel source) {
        this.mPresSubscriptionState = 3;
        readFromParcel(source);
    }

    public void readFromParcel(Parcel source) {
        this.mPresSubscriptionState = source.readInt();
    }

    public PresSubscriptionState() {
        this.mPresSubscriptionState = 3;
    }

    public int getPresSubscriptionStateValue() {
        return this.mPresSubscriptionState;
    }

    public void setPresSubscriptionState(int nPresSubscriptionState) {
        this.mPresSubscriptionState = nPresSubscriptionState;
    }
}
