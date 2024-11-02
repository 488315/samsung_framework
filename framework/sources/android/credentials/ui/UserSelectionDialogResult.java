package android.credentials.ui;

import android.annotation.NonNull;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.AnnotationValidations;

/* loaded from: classes.dex */
public final class UserSelectionDialogResult extends BaseDialogResult implements Parcelable {
    public static final Parcelable.Creator<UserSelectionDialogResult> CREATOR = new Parcelable.Creator<UserSelectionDialogResult>() { // from class: android.credentials.ui.UserSelectionDialogResult.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public UserSelectionDialogResult createFromParcel(Parcel in) {
            return new UserSelectionDialogResult(in);
        }

        @Override // android.os.Parcelable.Creator
        public UserSelectionDialogResult[] newArray(int size) {
            return new UserSelectionDialogResult[size];
        }
    };
    private static final String EXTRA_USER_SELECTION_RESULT = "android.credentials.ui.extra.USER_SELECTION_RESULT";
    private final String mEntryKey;
    private final String mEntrySubkey;
    private final String mProviderId;
    private ProviderPendingIntentResponse mProviderPendingIntentResponse;

    public static UserSelectionDialogResult fromResultData(Bundle resultData) {
        return (UserSelectionDialogResult) resultData.getParcelable(EXTRA_USER_SELECTION_RESULT, UserSelectionDialogResult.class);
    }

    public static void addToBundle(UserSelectionDialogResult result, Bundle bundle) {
        bundle.putParcelable(EXTRA_USER_SELECTION_RESULT, result);
    }

    public UserSelectionDialogResult(IBinder requestToken, String providerId, String entryKey, String entrySubkey) {
        super(requestToken);
        this.mProviderId = providerId;
        this.mEntryKey = entryKey;
        this.mEntrySubkey = entrySubkey;
    }

    public UserSelectionDialogResult(IBinder requestToken, String providerId, String entryKey, String entrySubkey, ProviderPendingIntentResponse providerPendingIntentResponse) {
        super(requestToken);
        this.mProviderId = providerId;
        this.mEntryKey = entryKey;
        this.mEntrySubkey = entrySubkey;
        this.mProviderPendingIntentResponse = providerPendingIntentResponse;
    }

    public String getProviderId() {
        return this.mProviderId;
    }

    public String getEntryKey() {
        return this.mEntryKey;
    }

    public String getEntrySubkey() {
        return this.mEntrySubkey;
    }

    public ProviderPendingIntentResponse getPendingIntentProviderResponse() {
        return this.mProviderPendingIntentResponse;
    }

    protected UserSelectionDialogResult(Parcel in) {
        super(in);
        String providerId = in.readString8();
        String entryKey = in.readString8();
        String entrySubkey = in.readString8();
        this.mProviderId = providerId;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) providerId);
        this.mEntryKey = entryKey;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) entryKey);
        this.mEntrySubkey = entrySubkey;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) entrySubkey);
        this.mProviderPendingIntentResponse = (ProviderPendingIntentResponse) in.readTypedObject(ProviderPendingIntentResponse.CREATOR);
    }

    @Override // android.credentials.ui.BaseDialogResult, android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString8(this.mProviderId);
        dest.writeString8(this.mEntryKey);
        dest.writeString8(this.mEntrySubkey);
        dest.writeTypedObject(this.mProviderPendingIntentResponse, flags);
    }

    @Override // android.credentials.ui.BaseDialogResult, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    /* renamed from: android.credentials.ui.UserSelectionDialogResult$1 */
    /* loaded from: classes.dex */
    class AnonymousClass1 implements Parcelable.Creator<UserSelectionDialogResult> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public UserSelectionDialogResult createFromParcel(Parcel in) {
            return new UserSelectionDialogResult(in);
        }

        @Override // android.os.Parcelable.Creator
        public UserSelectionDialogResult[] newArray(int size) {
            return new UserSelectionDialogResult[size];
        }
    }
}
