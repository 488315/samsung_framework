package android.hardware.scontext;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class SContextMainScreenDetection extends SContextEventContext {
    public static final Parcelable.Creator<SContextMainScreenDetection> CREATOR = new Parcelable.Creator<SContextMainScreenDetection>() { // from class: android.hardware.scontext.SContextMainScreenDetection.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public SContextMainScreenDetection createFromParcel(Parcel in) {
            return new SContextMainScreenDetection(in);
        }

        @Override // android.os.Parcelable.Creator
        public SContextMainScreenDetection[] newArray(int size) {
            return new SContextMainScreenDetection[size];
        }
    };
    private Bundle mContext;

    public SContextMainScreenDetection() {
        this.mContext = new Bundle();
    }

    SContextMainScreenDetection(Parcel src) {
        readFromParcel(src);
    }

    public int getScreenStatus() {
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

    /* renamed from: android.hardware.scontext.SContextMainScreenDetection$1 */
    /* loaded from: classes2.dex */
    class AnonymousClass1 implements Parcelable.Creator<SContextMainScreenDetection> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public SContextMainScreenDetection createFromParcel(Parcel in) {
            return new SContextMainScreenDetection(in);
        }

        @Override // android.os.Parcelable.Creator
        public SContextMainScreenDetection[] newArray(int size) {
            return new SContextMainScreenDetection[size];
        }
    }
}
