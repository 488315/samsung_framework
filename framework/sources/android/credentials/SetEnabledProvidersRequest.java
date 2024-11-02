package android.credentials;

import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.Preconditions;
import java.util.List;
import java.util.Objects;

/* loaded from: classes.dex */
public final class SetEnabledProvidersRequest implements Parcelable {
    public static final Parcelable.Creator<SetEnabledProvidersRequest> CREATOR = new Parcelable.Creator<SetEnabledProvidersRequest>() { // from class: android.credentials.SetEnabledProvidersRequest.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public SetEnabledProvidersRequest createFromParcel(Parcel in) {
            return new SetEnabledProvidersRequest(in);
        }

        @Override // android.os.Parcelable.Creator
        public SetEnabledProvidersRequest[] newArray(int size) {
            return new SetEnabledProvidersRequest[size];
        }
    };
    private final List<String> mProviders;

    /* synthetic */ SetEnabledProvidersRequest(Parcel parcel, SetEnabledProvidersRequestIA setEnabledProvidersRequestIA) {
        this(parcel);
    }

    public SetEnabledProvidersRequest(List<String> providers) {
        Objects.requireNonNull(providers, "providers must not be null");
        Preconditions.checkCollectionElementsNotNull(providers, "providers");
        this.mProviders = providers;
    }

    private SetEnabledProvidersRequest(Parcel in) {
        this.mProviders = in.createStringArrayList();
    }

    /* renamed from: android.credentials.SetEnabledProvidersRequest$1 */
    /* loaded from: classes.dex */
    class AnonymousClass1 implements Parcelable.Creator<SetEnabledProvidersRequest> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public SetEnabledProvidersRequest createFromParcel(Parcel in) {
            return new SetEnabledProvidersRequest(in);
        }

        @Override // android.os.Parcelable.Creator
        public SetEnabledProvidersRequest[] newArray(int size) {
            return new SetEnabledProvidersRequest[size];
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(this.mProviders);
    }

    public List<String> getProviderComponentNames() {
        return this.mProviders;
    }
}
