package android.hardware.scontext;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@Deprecated
/* loaded from: classes2.dex */
public class SContextFlipCoverAction extends SContextEventContext {
    public static final Parcelable.Creator<SContextFlipCoverAction> CREATOR = new Parcelable.Creator<SContextFlipCoverAction>() { // from class: android.hardware.scontext.SContextFlipCoverAction.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public SContextFlipCoverAction createFromParcel(Parcel in) {
            return new SContextFlipCoverAction(in);
        }

        @Override // android.os.Parcelable.Creator
        public SContextFlipCoverAction[] newArray(int size) {
            return new SContextFlipCoverAction[size];
        }
    };
    private Bundle mContext;

    public SContextFlipCoverAction() {
        this.mContext = new Bundle();
    }

    SContextFlipCoverAction(Parcel src) {
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

    /* renamed from: android.hardware.scontext.SContextFlipCoverAction$1 */
    /* loaded from: classes2.dex */
    class AnonymousClass1 implements Parcelable.Creator<SContextFlipCoverAction> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public SContextFlipCoverAction createFromParcel(Parcel in) {
            return new SContextFlipCoverAction(in);
        }

        @Override // android.os.Parcelable.Creator
        public SContextFlipCoverAction[] newArray(int size) {
            return new SContextFlipCoverAction[size];
        }
    }
}
