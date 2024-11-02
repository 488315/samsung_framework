package android.hardware.scontext;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@Deprecated
/* loaded from: classes2.dex */
public class SContextActivityNotificationEx extends SContextEventContext {
    public static final Parcelable.Creator<SContextActivityNotificationEx> CREATOR = new Parcelable.Creator<SContextActivityNotificationEx>() { // from class: android.hardware.scontext.SContextActivityNotificationEx.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public SContextActivityNotificationEx createFromParcel(Parcel in) {
            return new SContextActivityNotificationEx(in);
        }

        @Override // android.os.Parcelable.Creator
        public SContextActivityNotificationEx[] newArray(int size) {
            return new SContextActivityNotificationEx[size];
        }
    };
    private Bundle mContext;

    public SContextActivityNotificationEx() {
        this.mContext = new Bundle();
    }

    SContextActivityNotificationEx(Parcel src) {
        readFromParcel(src);
    }

    public long getTimeStamp() {
        return this.mContext.getLong("TimeStamp");
    }

    public int getStatus() {
        return this.mContext.getInt("ActivityType");
    }

    public int getAccuracy() {
        return this.mContext.getInt("Accuracy");
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

    /* renamed from: android.hardware.scontext.SContextActivityNotificationEx$1 */
    /* loaded from: classes2.dex */
    class AnonymousClass1 implements Parcelable.Creator<SContextActivityNotificationEx> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public SContextActivityNotificationEx createFromParcel(Parcel in) {
            return new SContextActivityNotificationEx(in);
        }

        @Override // android.os.Parcelable.Creator
        public SContextActivityNotificationEx[] newArray(int size) {
            return new SContextActivityNotificationEx[size];
        }
    }
}
