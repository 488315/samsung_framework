package android.hardware.scontext;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@Deprecated
/* loaded from: classes2.dex */
public class SContextApproach extends SContextEventContext {
    public static final Parcelable.Creator<SContextApproach> CREATOR = new Parcelable.Creator<SContextApproach>() { // from class: android.hardware.scontext.SContextApproach.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public SContextApproach createFromParcel(Parcel in) {
            return new SContextApproach(in);
        }

        @Override // android.os.Parcelable.Creator
        public SContextApproach[] newArray(int size) {
            return new SContextApproach[size];
        }
    };
    private Bundle mContext;

    public SContextApproach() {
        this.mContext = new Bundle();
    }

    SContextApproach(Parcel src) {
        readFromParcel(src);
    }

    public int getApproach() {
        return this.mContext.getInt("Proximity");
    }

    public int getUserID() {
        return this.mContext.getInt("UserID");
    }

    @Override // android.hardware.scontext.SContextEventContext, com.samsung.android.hardware.context.SemContextEventContext
    public void setValues(Bundle context) {
        this.mContext = context;
    }

    @Override // com.samsung.android.hardware.context.SemContextEventContext, android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeBundle(this.mContext);
    }

    private void readFromParcel(Parcel src) {
        this.mContext = src.readBundle();
    }

    /* renamed from: android.hardware.scontext.SContextApproach$1 */
    /* loaded from: classes2.dex */
    class AnonymousClass1 implements Parcelable.Creator<SContextApproach> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public SContextApproach createFromParcel(Parcel in) {
            return new SContextApproach(in);
        }

        @Override // android.os.Parcelable.Creator
        public SContextApproach[] newArray(int size) {
            return new SContextApproach[size];
        }
    }
}
