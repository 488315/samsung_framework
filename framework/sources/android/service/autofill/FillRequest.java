package android.service.autofill;

import android.annotation.NonNull;
import android.content.IntentSender;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.inputmethod.InlineSuggestionsRequest;
import com.android.internal.util.AnnotationValidations;
import com.android.internal.util.BitUtils;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.function.IntFunction;

/* loaded from: classes3.dex */
public final class FillRequest implements Parcelable {
    public static final Parcelable.Creator<FillRequest> CREATOR = new Parcelable.Creator<FillRequest>() { // from class: android.service.autofill.FillRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FillRequest[] newArray(int size) {
            return new FillRequest[size];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FillRequest createFromParcel(Parcel in) {
            return new FillRequest(in);
        }
    };
    public static final int FLAG_COMPATIBILITY_MODE_REQUEST = 2;
    public static final int FLAG_IME_SHOWING = 128;
    public static final int FLAG_MANUAL_REQUEST = 1;
    public static final int FLAG_PASSWORD_INPUT_TYPE = 4;
    public static final int FLAG_PCC_DETECTION = 512;
    public static final int FLAG_RESET_FILL_DIALOG_STATE = 256;
    public static final int FLAG_SCREEN_HAS_CREDMAN_FIELD = 1024;
    public static final int FLAG_SUPPORTS_FILL_DIALOG = 64;
    public static final int FLAG_VIEW_NOT_FOCUSED = 16;
    public static final int FLAG_VIEW_REQUESTS_CREDMAN_SERVICE = 2048;
    public static final int INVALID_REQUEST_ID = Integer.MIN_VALUE;
    private final Bundle mClientState;
    private final IntentSender mDelayedFillIntentSender;
    private final List<FillContext> mFillContexts;
    private final int mFlags;
    private final List<String> mHints;
    private final int mId;
    private final InlineSuggestionsRequest mInlineSuggestionsRequest;

    @Retention(RetentionPolicy.SOURCE)
    public @interface RequestFlags {
    }

    private void onConstructed() {
        Preconditions.checkCollectionElementsNotNull(this.mFillContexts, "contexts");
        Preconditions.checkCollectionElementsNotNull(this.mHints, "hints");
    }

    public static String requestFlagsToString(int value) {
        return BitUtils.flagsToString(value, new IntFunction() { // from class: android.service.autofill.FillRequest$$ExternalSyntheticLambda0
            @Override // java.util.function.IntFunction
            public final Object apply(int i) {
                return FillRequest.singleRequestFlagsToString(i);
            }
        });
    }

    static String singleRequestFlagsToString(int value) {
        switch (value) {
            case 1:
                return "FLAG_MANUAL_REQUEST";
            case 2:
                return "FLAG_COMPATIBILITY_MODE_REQUEST";
            case 4:
                return "FLAG_PASSWORD_INPUT_TYPE";
            case 16:
                return "FLAG_VIEW_NOT_FOCUSED";
            case 64:
                return "FLAG_SUPPORTS_FILL_DIALOG";
            case 128:
                return "FLAG_IME_SHOWING";
            case 256:
                return "FLAG_RESET_FILL_DIALOG_STATE";
            case 512:
                return "FLAG_PCC_DETECTION";
            case 1024:
                return "FLAG_SCREEN_HAS_CREDMAN_FIELD";
            case 2048:
                return "FLAG_VIEW_REQUESTS_CREDMAN_SERVICE";
            default:
                return Integer.toHexString(value);
        }
    }

    public FillRequest(int id, List<FillContext> fillContexts, List<String> hints, Bundle clientState, int flags, InlineSuggestionsRequest inlineSuggestionsRequest, IntentSender delayedFillIntentSender) {
        this.mId = id;
        this.mFillContexts = fillContexts;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mFillContexts);
        this.mHints = hints;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mHints);
        this.mClientState = clientState;
        this.mFlags = flags;
        Preconditions.checkFlagsArgument(this.mFlags, 4055);
        this.mInlineSuggestionsRequest = inlineSuggestionsRequest;
        this.mDelayedFillIntentSender = delayedFillIntentSender;
        onConstructed();
    }

    public int getId() {
        return this.mId;
    }

    public List<FillContext> getFillContexts() {
        return this.mFillContexts;
    }

    public List<String> getHints() {
        return this.mHints;
    }

    public Bundle getClientState() {
        return this.mClientState;
    }

    public int getFlags() {
        return this.mFlags;
    }

    public InlineSuggestionsRequest getInlineSuggestionsRequest() {
        return this.mInlineSuggestionsRequest;
    }

    public IntentSender getDelayedFillIntentSender() {
        return this.mDelayedFillIntentSender;
    }

    public String toString() {
        return "FillRequest { id = " + this.mId + ", fillContexts = " + this.mFillContexts + ", hints = " + this.mHints + ", clientState = " + this.mClientState + ", flags = " + requestFlagsToString(this.mFlags) + ", inlineSuggestionsRequest = " + this.mInlineSuggestionsRequest + ", delayedFillIntentSender = " + this.mDelayedFillIntentSender + " }";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        byte flg = this.mClientState != null ? (byte) (0 | 8) : (byte) 0;
        if (this.mInlineSuggestionsRequest != null) {
            flg = (byte) (flg | 32);
        }
        if (this.mDelayedFillIntentSender != null) {
            flg = (byte) (flg | 64);
        }
        dest.writeByte(flg);
        dest.writeInt(this.mId);
        dest.writeParcelableList(this.mFillContexts, flags);
        dest.writeStringList(this.mHints);
        if (this.mClientState != null) {
            dest.writeBundle(this.mClientState);
        }
        dest.writeInt(this.mFlags);
        if (this.mInlineSuggestionsRequest != null) {
            dest.writeTypedObject(this.mInlineSuggestionsRequest, flags);
        }
        if (this.mDelayedFillIntentSender != null) {
            dest.writeTypedObject(this.mDelayedFillIntentSender, flags);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    FillRequest(Parcel in) {
        byte flg = in.readByte();
        int id = in.readInt();
        ArrayList arrayList = new ArrayList();
        in.readParcelableList(arrayList, FillContext.class.getClassLoader());
        List<String> hints = new ArrayList<>();
        in.readStringList(hints);
        Bundle clientState = (flg & 8) == 0 ? null : in.readBundle();
        int flags = in.readInt();
        InlineSuggestionsRequest inlineSuggestionsRequest = (flg & 32) == 0 ? null : (InlineSuggestionsRequest) in.readTypedObject(InlineSuggestionsRequest.CREATOR);
        IntentSender delayedFillIntentSender = (flg & 64) == 0 ? null : (IntentSender) in.readTypedObject(IntentSender.CREATOR);
        this.mId = id;
        this.mFillContexts = arrayList;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mFillContexts);
        this.mHints = hints;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mHints);
        this.mClientState = clientState;
        this.mFlags = flags;
        Preconditions.checkFlagsArgument(this.mFlags, 4055);
        this.mInlineSuggestionsRequest = inlineSuggestionsRequest;
        this.mDelayedFillIntentSender = delayedFillIntentSender;
        onConstructed();
    }

    @Deprecated
    private void __metadata() {
    }
}
