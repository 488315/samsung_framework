package android.hardware.scontext;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@Deprecated
/* loaded from: classes2.dex */
public class SContextPutDownMotion extends SContextEventContext {
    public static final Parcelable.Creator<SContextPutDownMotion> CREATOR = new Parcelable.Creator<SContextPutDownMotion>() { // from class: android.hardware.scontext.SContextPutDownMotion.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public SContextPutDownMotion createFromParcel(Parcel in) {
            return new SContextPutDownMotion(in);
        }

        @Override // android.os.Parcelable.Creator
        public SContextPutDownMotion[] newArray(int size) {
            return new SContextPutDownMotion[size];
        }
    };
    private Bundle mContext;

    public SContextPutDownMotion() {
        this.mContext = new Bundle();
    }

    SContextPutDownMotion(Parcel src) {
        readFromParcel(src);
    }

    public int getAction() {
        return this.mContext.getInt("Action");
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

    /* renamed from: android.hardware.scontext.SContextPutDownMotion$1 */
    /* loaded from: classes2.dex */
    class AnonymousClass1 implements Parcelable.Creator<SContextPutDownMotion> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public SContextPutDownMotion createFromParcel(Parcel in) {
            return new SContextPutDownMotion(in);
        }

        @Override // android.os.Parcelable.Creator
        public SContextPutDownMotion[] newArray(int size) {
            return new SContextPutDownMotion[size];
        }
    }
}
