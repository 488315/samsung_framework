package android.hardware.scontext;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@Deprecated
/* loaded from: classes2.dex */
public class SContextWakeUpVoice extends SContextEventContext {
    public static final Parcelable.Creator<SContextWakeUpVoice> CREATOR = new Parcelable.Creator<SContextWakeUpVoice>() { // from class: android.hardware.scontext.SContextWakeUpVoice.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public SContextWakeUpVoice createFromParcel(Parcel in) {
            return new SContextWakeUpVoice(in);
        }

        @Override // android.os.Parcelable.Creator
        public SContextWakeUpVoice[] newArray(int size) {
            return new SContextWakeUpVoice[size];
        }
    };
    private Bundle mContext;

    public SContextWakeUpVoice() {
        this.mContext = new Bundle();
    }

    SContextWakeUpVoice(Parcel src) {
        readFromParcel(src);
    }

    public int getMode() {
        return this.mContext.getInt("Mode");
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

    /* renamed from: android.hardware.scontext.SContextWakeUpVoice$1 */
    /* loaded from: classes2.dex */
    class AnonymousClass1 implements Parcelable.Creator<SContextWakeUpVoice> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public SContextWakeUpVoice createFromParcel(Parcel in) {
            return new SContextWakeUpVoice(in);
        }

        @Override // android.os.Parcelable.Creator
        public SContextWakeUpVoice[] newArray(int size) {
            return new SContextWakeUpVoice[size];
        }
    }
}
