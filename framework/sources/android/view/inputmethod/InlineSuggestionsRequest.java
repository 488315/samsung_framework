package android.view.inputmethod;

import android.annotation.NonNull;
import android.app.ActivityThread;
import android.app.compat.CompatChanges;
import android.os.Bundle;
import android.os.IBinder;
import android.os.LocaleList;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.inline.InlinePresentationSpec;
import com.android.internal.util.AnnotationValidations;
import com.android.internal.util.Preconditions;
import com.android.internal.widget.InlinePresentationStyleUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class InlineSuggestionsRequest implements Parcelable {
    public static final Parcelable.Creator<InlineSuggestionsRequest> CREATOR = new Parcelable.Creator<InlineSuggestionsRequest>() { // from class: android.view.inputmethod.InlineSuggestionsRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InlineSuggestionsRequest[] newArray(int size) {
            return new InlineSuggestionsRequest[size];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InlineSuggestionsRequest createFromParcel(Parcel in) {
            return new InlineSuggestionsRequest(in);
        }
    };
    private static final long IME_AUTOFILL_DEFAULT_SUPPORTED_LOCALES_IS_EMPTY = 169273070;
    public static final int SUGGESTION_COUNT_UNLIMITED = Integer.MAX_VALUE;
    private Bundle mExtras;
    private int mHostDisplayId;
    private IBinder mHostInputToken;
    private String mHostPackageName;
    private final List<InlinePresentationSpec> mInlinePresentationSpecs;
    private InlinePresentationSpec mInlineTooltipPresentationSpec;
    private final int mMaxSuggestionCount;
    private LocaleList mSupportedLocales;

    public void setHostInputToken(IBinder hostInputToken) {
        this.mHostInputToken = hostInputToken;
    }

    private boolean extrasEquals(Bundle extras) {
        return InlinePresentationStyleUtils.bundleEquals(this.mExtras, extras);
    }

    private void parcelHostInputToken(Parcel parcel, int flags) {
        parcel.writeStrongBinder(this.mHostInputToken);
    }

    private IBinder unparcelHostInputToken(Parcel parcel) {
        return parcel.readStrongBinder();
    }

    public void setHostDisplayId(int hostDisplayId) {
        this.mHostDisplayId = hostDisplayId;
    }

    private void onConstructed() {
        Preconditions.checkState(!this.mInlinePresentationSpecs.isEmpty());
        Preconditions.checkState(this.mMaxSuggestionCount >= this.mInlinePresentationSpecs.size());
    }

    public void filterContentTypes() {
        InlinePresentationStyleUtils.filterContentTypes(this.mExtras);
        for (int i = 0; i < this.mInlinePresentationSpecs.size(); i++) {
            this.mInlinePresentationSpecs.get(i).filterContentTypes();
        }
        if (this.mInlineTooltipPresentationSpec != null) {
            this.mInlineTooltipPresentationSpec.filterContentTypes();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int defaultMaxSuggestionCount() {
        return Integer.MAX_VALUE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String defaultHostPackageName() {
        return ActivityThread.currentPackageName();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static InlinePresentationSpec defaultInlineTooltipPresentationSpec() {
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static LocaleList defaultSupportedLocales() {
        if (CompatChanges.isChangeEnabled(IME_AUTOFILL_DEFAULT_SUPPORTED_LOCALES_IS_EMPTY)) {
            return LocaleList.getEmptyLocaleList();
        }
        return LocaleList.getDefault();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static IBinder defaultHostInputToken() {
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int defaultHostDisplayId() {
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Bundle defaultExtras() {
        return Bundle.EMPTY;
    }

    static abstract class BaseBuilder {
        abstract Builder setHostDisplayId(int i);

        abstract Builder setHostInputToken(IBinder iBinder);

        abstract Builder setHostPackageName(String str);

        abstract Builder setInlinePresentationSpecs(List<InlinePresentationSpec> list);

        BaseBuilder() {
        }
    }

    InlineSuggestionsRequest(int maxSuggestionCount, List<InlinePresentationSpec> inlinePresentationSpecs, String hostPackageName, LocaleList supportedLocales, Bundle extras, IBinder hostInputToken, int hostDisplayId, InlinePresentationSpec inlineTooltipPresentationSpec) {
        this.mMaxSuggestionCount = maxSuggestionCount;
        this.mInlinePresentationSpecs = inlinePresentationSpecs;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mInlinePresentationSpecs);
        this.mHostPackageName = hostPackageName;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mHostPackageName);
        this.mSupportedLocales = supportedLocales;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mSupportedLocales);
        this.mExtras = extras;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mExtras);
        this.mHostInputToken = hostInputToken;
        this.mHostDisplayId = hostDisplayId;
        this.mInlineTooltipPresentationSpec = inlineTooltipPresentationSpec;
        onConstructed();
    }

    public int getMaxSuggestionCount() {
        return this.mMaxSuggestionCount;
    }

    public List<InlinePresentationSpec> getInlinePresentationSpecs() {
        return this.mInlinePresentationSpecs;
    }

    public String getHostPackageName() {
        return this.mHostPackageName;
    }

    public LocaleList getSupportedLocales() {
        return this.mSupportedLocales;
    }

    public Bundle getExtras() {
        return this.mExtras;
    }

    public IBinder getHostInputToken() {
        return this.mHostInputToken;
    }

    public int getHostDisplayId() {
        return this.mHostDisplayId;
    }

    public InlinePresentationSpec getInlineTooltipPresentationSpec() {
        return this.mInlineTooltipPresentationSpec;
    }

    public String toString() {
        return "InlineSuggestionsRequest { maxSuggestionCount = " + this.mMaxSuggestionCount + ", inlinePresentationSpecs = " + this.mInlinePresentationSpecs + ", hostPackageName = " + this.mHostPackageName + ", supportedLocales = " + this.mSupportedLocales + ", extras = " + this.mExtras + ", hostInputToken = " + this.mHostInputToken + ", hostDisplayId = " + this.mHostDisplayId + ", inlineTooltipPresentationSpec = " + this.mInlineTooltipPresentationSpec + " }";
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        InlineSuggestionsRequest that = (InlineSuggestionsRequest) o;
        if (this.mMaxSuggestionCount == that.mMaxSuggestionCount && Objects.equals(this.mInlinePresentationSpecs, that.mInlinePresentationSpecs) && Objects.equals(this.mHostPackageName, that.mHostPackageName) && Objects.equals(this.mSupportedLocales, that.mSupportedLocales) && extrasEquals(that.mExtras) && Objects.equals(this.mHostInputToken, that.mHostInputToken) && this.mHostDisplayId == that.mHostDisplayId && Objects.equals(this.mInlineTooltipPresentationSpec, that.mInlineTooltipPresentationSpec)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int _hash = (1 * 31) + this.mMaxSuggestionCount;
        return (((((((((((((_hash * 31) + Objects.hashCode(this.mInlinePresentationSpecs)) * 31) + Objects.hashCode(this.mHostPackageName)) * 31) + Objects.hashCode(this.mSupportedLocales)) * 31) + Objects.hashCode(this.mExtras)) * 31) + Objects.hashCode(this.mHostInputToken)) * 31) + this.mHostDisplayId) * 31) + Objects.hashCode(this.mInlineTooltipPresentationSpec);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        int flg = this.mHostInputToken != null ? 0 | 32 : 0;
        if (this.mInlineTooltipPresentationSpec != null) {
            flg |= 128;
        }
        dest.writeInt(flg);
        dest.writeInt(this.mMaxSuggestionCount);
        dest.writeParcelableList(this.mInlinePresentationSpecs, flags);
        dest.writeString(this.mHostPackageName);
        dest.writeTypedObject(this.mSupportedLocales, flags);
        dest.writeBundle(this.mExtras);
        parcelHostInputToken(dest, flags);
        dest.writeInt(this.mHostDisplayId);
        if (this.mInlineTooltipPresentationSpec != null) {
            dest.writeTypedObject(this.mInlineTooltipPresentationSpec, flags);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    InlineSuggestionsRequest(Parcel in) {
        int flg = in.readInt();
        int maxSuggestionCount = in.readInt();
        ArrayList arrayList = new ArrayList();
        in.readParcelableList(arrayList, InlinePresentationSpec.class.getClassLoader());
        String hostPackageName = in.readString();
        LocaleList supportedLocales = (LocaleList) in.readTypedObject(LocaleList.CREATOR);
        Bundle extras = in.readBundle();
        IBinder hostInputToken = unparcelHostInputToken(in);
        int hostDisplayId = in.readInt();
        InlinePresentationSpec inlineTooltipPresentationSpec = (flg & 128) == 0 ? null : (InlinePresentationSpec) in.readTypedObject(InlinePresentationSpec.CREATOR);
        this.mMaxSuggestionCount = maxSuggestionCount;
        this.mInlinePresentationSpecs = arrayList;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mInlinePresentationSpecs);
        this.mHostPackageName = hostPackageName;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mHostPackageName);
        this.mSupportedLocales = supportedLocales;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mSupportedLocales);
        this.mExtras = extras;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mExtras);
        this.mHostInputToken = hostInputToken;
        this.mHostDisplayId = hostDisplayId;
        this.mInlineTooltipPresentationSpec = inlineTooltipPresentationSpec;
        onConstructed();
    }

    public static final class Builder extends BaseBuilder {
        private long mBuilderFieldsSet = 0;
        private Bundle mExtras;
        private int mHostDisplayId;
        private IBinder mHostInputToken;
        private String mHostPackageName;
        private List<InlinePresentationSpec> mInlinePresentationSpecs;
        private InlinePresentationSpec mInlineTooltipPresentationSpec;
        private int mMaxSuggestionCount;
        private LocaleList mSupportedLocales;

        public Builder(List<InlinePresentationSpec> inlinePresentationSpecs) {
            this.mInlinePresentationSpecs = inlinePresentationSpecs;
            AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mInlinePresentationSpecs);
        }

        public Builder setMaxSuggestionCount(int value) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 1;
            this.mMaxSuggestionCount = value;
            return this;
        }

        @Override // android.view.inputmethod.InlineSuggestionsRequest.BaseBuilder
        public Builder setInlinePresentationSpecs(List<InlinePresentationSpec> value) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 2;
            this.mInlinePresentationSpecs = value;
            return this;
        }

        public Builder addInlinePresentationSpecs(InlinePresentationSpec value) {
            if (this.mInlinePresentationSpecs == null) {
                setInlinePresentationSpecs(new ArrayList());
            }
            this.mInlinePresentationSpecs.add(value);
            return this;
        }

        @Override // android.view.inputmethod.InlineSuggestionsRequest.BaseBuilder
        Builder setHostPackageName(String value) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 4;
            this.mHostPackageName = value;
            return this;
        }

        public Builder setSupportedLocales(LocaleList value) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 8;
            this.mSupportedLocales = value;
            return this;
        }

        public Builder setExtras(Bundle value) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 16;
            this.mExtras = value;
            return this;
        }

        @Override // android.view.inputmethod.InlineSuggestionsRequest.BaseBuilder
        Builder setHostInputToken(IBinder value) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 32;
            this.mHostInputToken = value;
            return this;
        }

        @Override // android.view.inputmethod.InlineSuggestionsRequest.BaseBuilder
        Builder setHostDisplayId(int value) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 64;
            this.mHostDisplayId = value;
            return this;
        }

        public Builder setInlineTooltipPresentationSpec(InlinePresentationSpec value) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 128;
            this.mInlineTooltipPresentationSpec = value;
            return this;
        }

        public InlineSuggestionsRequest build() {
            checkNotUsed();
            this.mBuilderFieldsSet |= 256;
            if ((this.mBuilderFieldsSet & 1) == 0) {
                this.mMaxSuggestionCount = InlineSuggestionsRequest.defaultMaxSuggestionCount();
            }
            if ((this.mBuilderFieldsSet & 4) == 0) {
                this.mHostPackageName = InlineSuggestionsRequest.defaultHostPackageName();
            }
            if ((this.mBuilderFieldsSet & 8) == 0) {
                this.mSupportedLocales = InlineSuggestionsRequest.defaultSupportedLocales();
            }
            if ((this.mBuilderFieldsSet & 16) == 0) {
                this.mExtras = InlineSuggestionsRequest.defaultExtras();
            }
            if ((this.mBuilderFieldsSet & 32) == 0) {
                this.mHostInputToken = InlineSuggestionsRequest.defaultHostInputToken();
            }
            if ((this.mBuilderFieldsSet & 64) == 0) {
                this.mHostDisplayId = InlineSuggestionsRequest.defaultHostDisplayId();
            }
            if ((this.mBuilderFieldsSet & 128) == 0) {
                this.mInlineTooltipPresentationSpec = InlineSuggestionsRequest.defaultInlineTooltipPresentationSpec();
            }
            InlineSuggestionsRequest o = new InlineSuggestionsRequest(this.mMaxSuggestionCount, this.mInlinePresentationSpecs, this.mHostPackageName, this.mSupportedLocales, this.mExtras, this.mHostInputToken, this.mHostDisplayId, this.mInlineTooltipPresentationSpec);
            return o;
        }

        private void checkNotUsed() {
            if ((this.mBuilderFieldsSet & 256) != 0) {
                throw new IllegalStateException("This Builder should not be reused. Use a new Builder instance instead");
            }
        }
    }

    @Deprecated
    private void __metadata() {
    }
}
