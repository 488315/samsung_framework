package android.hardware.scontext;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@Deprecated
/* loaded from: classes2.dex */
public class SContextAbnormalPressure extends SContextEventContext {
    public static final Parcelable.Creator<SContextAbnormalPressure> CREATOR = new Parcelable.Creator<SContextAbnormalPressure>() { // from class: android.hardware.scontext.SContextAbnormalPressure.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextAbnormalPressure createFromParcel(Parcel in) {
            return new SContextAbnormalPressure(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextAbnormalPressure[] newArray(int size) {
            return new SContextAbnormalPressure[size];
        }
    };
    private Bundle mContext;

    /* JADX INFO: Access modifiers changed from: package-private */
    public SContextAbnormalPressure() {
        this.mContext = new Bundle();
    }

    SContextAbnormalPressure(Parcel src) {
        readFromParcel(src);
    }

    public float getPressure() {
        return this.mContext.getFloat("barometer");
    }

    public float getAccX() {
        return this.mContext.getFloat("xaxis");
    }

    public float getAccY() {
        return this.mContext.getFloat("yaxis");
    }

    public float getAccZ() {
        return this.mContext.getFloat("zaxis");
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
