package android.hardware.scontext;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@Deprecated
/* loaded from: classes2.dex */
public class SContextMovement extends SContextEventContext {
    public static final Parcelable.Creator<SContextMovement> CREATOR = new Parcelable.Creator<SContextMovement>() { // from class: android.hardware.scontext.SContextMovement.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextMovement createFromParcel(Parcel in) {
            return new SContextMovement(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextMovement[] newArray(int size) {
            return new SContextMovement[size];
        }
    };
    private Bundle mContext;

    /* JADX INFO: Access modifiers changed from: package-private */
    public SContextMovement() {
        this.mContext = new Bundle();
    }

    SContextMovement(Parcel src) {
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