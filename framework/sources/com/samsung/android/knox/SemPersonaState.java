package com.samsung.android.knox;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public enum SemPersonaState implements Parcelable {
    INVALID(-1),
    CREATING(1),
    ACTIVE(0),
    LOCKED(2),
    SUPER_LOCKED(-1),
    LICENSE_LOCKED(9),
    ADMIN_LOCKED(8),
    ADMIN_LICENSE_LOCKED(-1),
    TERMINUS(-1),
    DELETING(3),
    TIMA_COMPROMISED(7),
    CONTAINER_APPS_URGENT_UPDATE(-1);

    public static final Parcelable.Creator<SemPersonaState> CREATOR = new Parcelable.Creator<SemPersonaState>() { // from class: com.samsung.android.knox.SemPersonaState.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public SemPersonaState createFromParcel(Parcel source) {
            return SemPersonaState.valueOf(source.readString());
        }

        @Override // android.os.Parcelable.Creator
        public SemPersonaState[] newArray(int size) {
            return new SemPersonaState[size];
        }
    };
    private int knox2_0_state_id;

    SemPersonaState(int knox2_0_state_id) {
        this.knox2_0_state_id = -1;
        this.knox2_0_state_id = knox2_0_state_id;
    }

    public int getKnox2_0State() {
        return this.knox2_0_state_id;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name());
    }

    /* renamed from: com.samsung.android.knox.SemPersonaState$1 */
    /* loaded from: classes5.dex */
    class AnonymousClass1 implements Parcelable.Creator<SemPersonaState> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public SemPersonaState createFromParcel(Parcel source) {
            return SemPersonaState.valueOf(source.readString());
        }

        @Override // android.os.Parcelable.Creator
        public SemPersonaState[] newArray(int size) {
            return new SemPersonaState[size];
        }
    }
}
