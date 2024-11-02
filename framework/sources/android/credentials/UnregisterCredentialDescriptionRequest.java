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
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public UnregisterCredentialDescriptionRequest createFromParcel(Parcel in) {
            return new UnregisterCredentialDescriptionRequest(in);
        }

        @Override // android.os.Parcelable.Creator
        public UnregisterCredentialDescriptionRequest[] newArray(int size) {
            return new UnregisterCredentialDescriptionRequest[size];
        }
    };
    private final List<CredentialDescription> mCredentialDescriptions;

    /* synthetic */ UnregisterCredentialDescriptionRequest(Parcel parcel, UnregisterCredentialDescriptionRequestIA unregisterCredentialDescriptionRequestIA) {
        this(parcel);
    }

    public UnregisterCredentialDescriptionRequest(CredentialDescription credentialDescription) {
        this.mCredentialDescriptions = Arrays.asList((CredentialDescription) Objects.requireNonNull(credentialDescription));
    }

    public UnregisterCredentialDescriptionRequest(Set<CredentialDescription> credentialDescriptions) {
        this.mCredentialDescriptions = new ArrayList((Collection) Objects.requireNonNull(credentialDescriptions));
    }

    private UnregisterCredentialDescriptionRequest(Parcel in) {
        ArrayList arrayList = new ArrayList();
        in.readTypedList(arrayList, CredentialDescription.CREATOR);
        ArrayList arrayList2 = new ArrayList();
        this.mCredentialDescriptions = arrayList2;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) arrayList);
        arrayList2.addAll(arrayList);
    }

    /* renamed from: android.credentials.UnregisterCredentialDescriptionRequest$1 */
    /* loaded from: classes.dex */
    class AnonymousClass1 implements Parcelable.Creator<UnregisterCredentialDescriptionRequest> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public UnregisterCredentialDescriptionRequest createFromParcel(Parcel in) {
            return new UnregisterCredentialDescriptionRequest(in);
        }

        @Override // android.os.Parcelable.Creator
        public UnregisterCredentialDescriptionRequest[] newArray(int size) {
            return new UnregisterCredentialDescriptionRequest[size];
        }
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
