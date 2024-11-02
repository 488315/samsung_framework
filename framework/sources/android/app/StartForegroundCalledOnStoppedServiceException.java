package android.app;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public final class StartForegroundCalledOnStoppedServiceException extends IllegalStateException implements Parcelable {
    public static final Parcelable.Creator<StartForegroundCalledOnStoppedServiceException> CREATOR = new Parcelable.Creator<StartForegroundCalledOnStoppedServiceException>() { // from class: android.app.StartForegroundCalledOnStoppedServiceException.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public StartForegroundCalledOnStoppedServiceException createFromParcel(Parcel source) {
            return new StartForegroundCalledOnStoppedServiceException(source);
        }

        @Override // android.os.Parcelable.Creator
        public StartForegroundCalledOnStoppedServiceException[] newArray(int size) {
            return new StartForegroundCalledOnStoppedServiceException[size];
        }
    };

    public StartForegroundCalledOnStoppedServiceException(String message) {
        super(message);
    }

    StartForegroundCalledOnStoppedServiceException(Parcel source) {
        super(source.readString());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getMessage());
    }

    /* renamed from: android.app.StartForegroundCalledOnStoppedServiceException$1 */
    /* loaded from: classes.dex */
    class AnonymousClass1 implements Parcelable.Creator<StartForegroundCalledOnStoppedServiceException> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public StartForegroundCalledOnStoppedServiceException createFromParcel(Parcel source) {
            return new StartForegroundCalledOnStoppedServiceException(source);
        }

        @Override // android.os.Parcelable.Creator
        public StartForegroundCalledOnStoppedServiceException[] newArray(int size) {
            return new StartForegroundCalledOnStoppedServiceException[size];
        }
    }
}
