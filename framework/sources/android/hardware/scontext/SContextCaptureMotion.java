package android.hardware.scontext;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@Deprecated
/* loaded from: classes2.dex */
public class SContextCaptureMotion extends SContextEventContext {
    public static final Parcelable.Creator<SContextCaptureMotion> CREATOR = new Parcelable.Creator<SContextCaptureMotion>() { // from class: android.hardware.scontext.SContextCaptureMotion.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextCaptureMotion createFromParcel(Parcel in) {
            return new SContextCaptureMotion(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextCaptureMotion[] newArray(int size) {
            return new SContextCaptureMotion[size];
        }
    };
    private Bundle mContext;

    /* JADX INFO: Access modifiers changed from: package-private */
    public SContextCaptureMotion() {
        this.mContext = new Bundle();
    }

    SContextCaptureMotion(Parcel src) {
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
}
