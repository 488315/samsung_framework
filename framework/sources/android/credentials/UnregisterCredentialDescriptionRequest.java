package android.credentials;

import android.annotation.NonNull;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.AnnotationValidations;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/* loaded from: classes.dex */
public final class UnregisterCredentialDescriptionRequest implements Parcelable {
    public static final Parcelable.Creator<UnregisterCredentialDescriptionRequest> CREATOR = new Parcelable.Creator<UnregisterCredentialDescriptionRequest>() { // from class: android.credentials.UnregisterCredentialDescriptionRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UnregisterCredentialDescriptionRequest createFromParcel(Parcel in) {
            return new UnregisterCredentialDescriptionRequest(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UnregisterCredentialDescriptionRequest[] newArray(int size) {
            return new UnregisterCredentialDescriptionRequest[size];
        }
    };
    private final List<CredentialDescription> mCredentialDescriptions;

    public UnregisterCredentialDescriptionRequest(CredentialDescription credentialDescription) {
        this.mCredentialDescriptions = Arrays.asList((CredentialDescription) Objects.requireNonNull(credentialDescription));
    }

    public UnregisterCredentialDescriptionRequest(Set<CredentialDescription> credentialDescriptions) {
        this.mCredentialDescriptions = new ArrayList((Collection) Objects.requireNonNull(credentialDescriptions));
    }

    private UnregisterCredentialDescriptionRequest(Parcel in) {
        ArrayList arrayList = new ArrayList();
        in.readTypedList(arrayList, CredentialDescription.CREATOR);
        this.mCredentialDescriptions = new ArrayList();
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) arrayList);
        this.mCredentialDescriptions.addAll(arrayList);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.mCredentialDescriptions, flags);
    }

    public Set<CredentialDescription> getCredentialDescriptions() {
        return new HashSet(this.mCredentialDescriptions);
    }
}
