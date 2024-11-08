package android.app;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public final class BackgroundServiceStartNotAllowedException extends ServiceStartNotAllowedException implements Parcelable {
    public static final Parcelable.Creator<BackgroundServiceStartNotAllowedException> CREATOR = new Parcelable.Creator<BackgroundServiceStartNotAllowedException>() { // from class: android.app.BackgroundServiceStartNotAllowedException.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public BackgroundServiceStartNotAllowedException createFromParcel(Parcel source) {
            return new BackgroundServiceStartNotAllowedException(source);
        }

        @Override // android.os.Parcelable.Creator
        public BackgroundServiceStartNotAllowedException[] newArray(int size) {
            return new BackgroundServiceStartNotAllowedException[size];
        }
    };

    public BackgroundServiceStartNotAllowedException(String message) {
        super(message);
    }

    BackgroundServiceStartNotAllowedException(Parcel source) {
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

    /* renamed from: android.app.BackgroundServiceStartNotAllowedException$1 */
    /* loaded from: classes.dex */
    class AnonymousClass1 implements Parcelable.Creator<BackgroundServiceStartNotAllowedException> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public BackgroundServiceStartNotAllowedException createFromParcel(Parcel source) {
            return new BackgroundServiceStartNotAllowedException(source);
        }

        @Override // android.os.Parcelable.Creator
        public BackgroundServiceStartNotAllowedException[] newArray(int size) {
            return new BackgroundServiceStartNotAllowedException[size];
        }
    }
}
