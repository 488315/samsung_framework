package android.app.contentsuggestions;

import android.annotation.SystemApi;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

@SystemApi
/* loaded from: classes.dex */
public final class ClassificationsRequest implements Parcelable {
    public static final Parcelable.Creator<ClassificationsRequest> CREATOR = new Parcelable.Creator<ClassificationsRequest>() { // from class: android.app.contentsuggestions.ClassificationsRequest.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public ClassificationsRequest createFromParcel(Parcel source) {
            return new ClassificationsRequest(source.createTypedArrayList(ContentSelection.CREATOR), source.readBundle());
        }

        @Override // android.os.Parcelable.Creator
        public ClassificationsRequest[] newArray(int size) {
            return new ClassificationsRequest[size];
        }
    };
    private final Bundle mExtras;
    private final List<ContentSelection> mSelections;

    /* synthetic */ ClassificationsRequest(List list, Bundle bundle, ClassificationsRequestIA classificationsRequestIA) {
        this(list, bundle);
    }

    private ClassificationsRequest(List<ContentSelection> selections, Bundle extras) {
        this.mSelections = selections;
        this.mExtras = extras;
    }

    public List<ContentSelection> getSelections() {
        return this.mSelections;
    }

    public Bundle getExtras() {
        Bundle bundle = this.mExtras;
        return bundle == null ? new Bundle() : bundle;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.mSelections);
        dest.writeBundle(this.mExtras);
    }

    /* renamed from: android.app.contentsuggestions.ClassificationsRequest$1 */
    /* loaded from: classes.dex */
    class AnonymousClass1 implements Parcelable.Creator<ClassificationsRequest> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public ClassificationsRequest createFromParcel(Parcel source) {
            return new ClassificationsRequest(source.createTypedArrayList(ContentSelection.CREATOR), source.readBundle());
        }

        @Override // android.os.Parcelable.Creator
        public ClassificationsRequest[] newArray(int size) {
            return new ClassificationsRequest[size];
        }
    }

    @SystemApi
    /* loaded from: classes.dex */
    public static final class Builder {
        private Bundle mExtras;
        private final List<ContentSelection> mSelections;

        public Builder(List<ContentSelection> selections) {
            this.mSelections = selections;
        }

        public Builder setExtras(Bundle extras) {
            this.mExtras = extras;
            return this;
        }

        public ClassificationsRequest build() {
            return new ClassificationsRequest(this.mSelections, this.mExtras);
        }
    }
}
