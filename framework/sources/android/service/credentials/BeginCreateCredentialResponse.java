package android.service.credentials;

import android.annotation.NonNull;
import android.content.pm.ParceledListSlice;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.AnnotationValidations;
import com.android.internal.util.Preconditions;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class BeginCreateCredentialResponse implements Parcelable {
    public static final Parcelable.Creator<BeginCreateCredentialResponse> CREATOR = new Parcelable.Creator<BeginCreateCredentialResponse>() { // from class: android.service.credentials.BeginCreateCredentialResponse.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public BeginCreateCredentialResponse createFromParcel(Parcel in) {
            return new BeginCreateCredentialResponse(in);
        }

        @Override // android.os.Parcelable.Creator
        public BeginCreateCredentialResponse[] newArray(int size) {
            return new BeginCreateCredentialResponse[size];
        }
    };
    private final ParceledListSlice<CreateEntry> mCreateEntries;
    private final RemoteEntry mRemoteCreateEntry;

    /* synthetic */ BeginCreateCredentialResponse(Parcel parcel, BeginCreateCredentialResponseIA beginCreateCredentialResponseIA) {
        this(parcel);
    }

    public BeginCreateCredentialResponse() {
        this((ParceledListSlice<CreateEntry>) new ParceledListSlice(new ArrayList()), (RemoteEntry) null);
    }

    private BeginCreateCredentialResponse(Parcel in) {
        this.mCreateEntries = (ParceledListSlice) in.readParcelable(null, ParceledListSlice.class);
        this.mRemoteCreateEntry = (RemoteEntry) in.readTypedObject(RemoteEntry.CREATOR);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.mCreateEntries, flags);
        dest.writeTypedObject(this.mRemoteCreateEntry, flags);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    /* renamed from: android.service.credentials.BeginCreateCredentialResponse$1 */
    /* loaded from: classes3.dex */
    class AnonymousClass1 implements Parcelable.Creator<BeginCreateCredentialResponse> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public BeginCreateCredentialResponse createFromParcel(Parcel in) {
            return new BeginCreateCredentialResponse(in);
        }

        @Override // android.os.Parcelable.Creator
        public BeginCreateCredentialResponse[] newArray(int size) {
            return new BeginCreateCredentialResponse[size];
        }
    }

    BeginCreateCredentialResponse(ParceledListSlice<CreateEntry> createEntries, RemoteEntry remoteCreateEntry) {
        this.mCreateEntries = createEntries;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) createEntries);
        this.mRemoteCreateEntry = remoteCreateEntry;
    }

    public List<CreateEntry> getCreateEntries() {
        return this.mCreateEntries.getList();
    }

    public RemoteEntry getRemoteCreateEntry() {
        return this.mRemoteCreateEntry;
    }

    /* loaded from: classes3.dex */
    public static final class Builder {
        private List<CreateEntry> mCreateEntries = new ArrayList();
        private RemoteEntry mRemoteCreateEntry;

        public Builder setCreateEntries(List<CreateEntry> createEntries) {
            Preconditions.checkCollectionNotEmpty(createEntries, "createEntries");
            this.mCreateEntries = (List) Preconditions.checkCollectionElementsNotNull(createEntries, "createEntries");
            return this;
        }

        public Builder addCreateEntry(CreateEntry createEntry) {
            this.mCreateEntries.add((CreateEntry) Objects.requireNonNull(createEntry));
            return this;
        }

        public Builder setRemoteCreateEntry(RemoteEntry remoteCreateEntry) {
            this.mRemoteCreateEntry = remoteCreateEntry;
            return this;
        }

        public BeginCreateCredentialResponse build() {
            return new BeginCreateCredentialResponse((ParceledListSlice<CreateEntry>) new ParceledListSlice(this.mCreateEntries), this.mRemoteCreateEntry);
        }
    }
}
