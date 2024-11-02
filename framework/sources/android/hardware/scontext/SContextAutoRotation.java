package android.hardware.scontext;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@Deprecated
/* loaded from: classes2.dex */
public class SContextAutoRotation extends SContextEventContext {
    public static final Parcelable.Creator<SContextAutoRotation> CREATOR = new Parcelable.Creator<SContextAutoRotation>() { // from class: android.hardware.scontext.SContextAutoRotation.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public SContextAutoRotation createFromParcel(Parcel in) {
            return new SContextAutoRotation(in);
        }

        @Override // android.os.Parcelable.Creator
        public SContextAutoRotation[] newArray(int size) {
            return new SContextAutoRotation[size];
        }
    };
    private Bundle mContext;

    public SContextAutoRotation() {
        this.mContext = new Bundle();
    }

    SContextAutoRotation(Parcel src) {
        readFromParcel(src);
    }

    public int getAngle() {
        return this.mContext.getInt("Angle");
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

    /* renamed from: android.hardware.scontext.SContextAutoRotation$1 */
    /* loaded from: classes2.dex */
    class AnonymousClass1 implements Parcelable.Creator<SContextAutoRotation> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public SContextAutoRotation createFromParcel(Parcel in) {
            return new SContextAutoRotation(in);
        }

        @Override // android.os.Parcelable.Creator
        public SContextAutoRotation[] newArray(int size) {
            return new SContextAutoRotation[size];
        }
    }
}
