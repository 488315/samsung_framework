package android.view.inputmethod;

import android.annotation.NonNull;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.inline.InlinePresentationSpec;
import com.android.internal.util.AnnotationValidations;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class InlineSuggestionInfo implements Parcelable {
    public static final Parcelable.Creator<InlineSuggestionInfo> CREATOR = new Parcelable.Creator<InlineSuggestionInfo>() { // from class: android.view.inputmethod.InlineSuggestionInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InlineSuggestionInfo[] newArray(int size) {
            return new InlineSuggestionInfo[size];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InlineSuggestionInfo createFromParcel(Parcel in) {
            return new InlineSuggestionInfo(in);
        }
    };
    public static final String SOURCE_AUTOFILL = "android:autofill";
    public static final String SOURCE_PLATFORM = "android:platform";
    public static final String TYPE_ACTION = "android:autofill:action";
    public static final String TYPE_SUGGESTION = "android:autofill:suggestion";
    private final String[] mAutofillHints;
    private final InlinePresentationSpec mInlinePresentationSpec;
    private final boolean mPinned;
    private final String mSource;
    private final InlineSuggestion mTooltip;
    private final String mType;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Source {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
    }

    public static InlineSuggestionInfo newInlineSuggestionInfo(InlinePresentationSpec presentationSpec, String source, String[] autofillHints, String type, boolean isPinned) {
        return new InlineSuggestionInfo(presentationSpec, source, autofillHints, type, isPinned, null);
    }

    public static InlineSuggestionInfo newInlineSuggestionInfo(InlinePresentationSpec presentationSpec, String source, String[] autofillHints, String type, boolean isPinned, InlineSuggestion tooltip) {
        return new InlineSuggestionInfo(presentationSpec, source, autofillHints, type, isPinned, tooltip);
    }

    public InlineSuggestionInfo(InlinePresentationSpec inlinePresentationSpec, String source, String[] autofillHints, String type, boolean pinned, InlineSuggestion tooltip) {
        this.mInlinePresentationSpec = inlinePresentationSpec;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mInlinePresentationSpec);
        this.mSource = source;
        if (!Objects.equals(this.mSource, SOURCE_AUTOFILL) && !Objects.equals(this.mSource, SOURCE_PLATFORM)) {
            throw new IllegalArgumentException("source was " + this.mSource + " but must be one of: SOURCE_AUTOFILL(" + SOURCE_AUTOFILL + "), SOURCE_PLATFORM(" + SOURCE_PLATFORM + NavigationBarInflaterView.KEY_CODE_END);
        }
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mSource);
        this.mAutofillHints = autofillHints;
        this.mType = type;
        if (!Objects.equals(this.mType, TYPE_SUGGESTION) && !Objects.equals(this.mType, TYPE_ACTION)) {
            throw new IllegalArgumentException("type was " + this.mType + " but must be one of: TYPE_SUGGESTION(" + TYPE_SUGGESTION + "), TYPE_ACTION(" + TYPE_ACTION + NavigationBarInflaterView.KEY_CODE_END);
        }
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mType);
        this.mPinned = pinned;
        this.mTooltip = tooltip;
    }

    public InlinePresentationSpec getInlinePresentationSpec() {
        return this.mInlinePresentationSpec;
    }

    public String getSource() {
        return this.mSource;
    }

    public String[] getAutofillHints() {
        return this.mAutofillHints;
    }

    public String getType() {
        return this.mType;
    }

    public boolean isPinned() {
        return this.mPinned;
    }

    public InlineSuggestion getTooltip() {
        return this.mTooltip;
    }

    public String toString() {
        return "InlineSuggestionInfo { inlinePresentationSpec = " + this.mInlinePresentationSpec + ", source = " + this.mSource + ", autofillHints = " + Arrays.toString(this.mAutofillHints) + ", type = " + this.mType + ", pinned = " + this.mPinned + ", tooltip = " + this.mTooltip + " }";
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        InlineSuggestionInfo that = (InlineSuggestionInfo) o;
        if (Objects.equals(this.mInlinePresentationSpec, that.mInlinePresentationSpec) && Objects.equals(this.mSource, that.mSource) && Arrays.equals(this.mAutofillHints, that.mAutofillHints) && Objects.equals(this.mType, that.mType) && this.mPinned == that.mPinned && Objects.equals(this.mTooltip, that.mTooltip)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int _hash = (1 * 31) + Objects.hashCode(this.mInlinePresentationSpec);
        return (((((((((_hash * 31) + Objects.hashCode(this.mSource)) * 31) + Arrays.hashCode(this.mAutofillHints)) * 31) + Objects.hashCode(this.mType)) * 31) + Boolean.hashCode(this.mPinned)) * 31) + Objects.hashCode(this.mTooltip);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        byte flg = this.mPinned ? (byte) (0 | 16) : (byte) 0;
        if (this.mAutofillHints != null) {
            flg = (byte) (flg | 4);
        }
        if (this.mTooltip != null) {
            flg = (byte) (flg | 32);
        }
        dest.writeByte(flg);
        dest.writeTypedObject(this.mInlinePresentationSpec, flags);
        dest.writeString(this.mSource);
        if (this.mAutofillHints != null) {
            dest.writeStringArray(this.mAutofillHints);
        }
        dest.writeString(this.mType);
        if (this.mTooltip != null) {
            dest.writeTypedObject(this.mTooltip, flags);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    InlineSuggestionInfo(Parcel in) {
        byte flg = in.readByte();
        boolean pinned = (flg & 16) != 0;
        InlinePresentationSpec inlinePresentationSpec = (InlinePresentationSpec) in.readTypedObject(InlinePresentationSpec.CREATOR);
        String source = in.readString();
        String[] autofillHints = (flg & 4) == 0 ? null : in.createStringArray();
        String type = in.readString();
        InlineSuggestion tooltip = (flg & 32) == 0 ? null : (InlineSuggestion) in.readTypedObject(InlineSuggestion.CREATOR);
        this.mInlinePresentationSpec = inlinePresentationSpec;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mInlinePresentationSpec);
        this.mSource = source;
        if (!Objects.equals(this.mSource, SOURCE_AUTOFILL) && !Objects.equals(this.mSource, SOURCE_PLATFORM)) {
            throw new IllegalArgumentException("source was " + this.mSource + " but must be one of: SOURCE_AUTOFILL(" + SOURCE_AUTOFILL + "), SOURCE_PLATFORM(" + SOURCE_PLATFORM + NavigationBarInflaterView.KEY_CODE_END);
        }
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mSource);
        this.mAutofillHints = autofillHints;
        this.mType = type;
        if (!Objects.equals(this.mType, TYPE_SUGGESTION) && !Objects.equals(this.mType, TYPE_ACTION)) {
            throw new IllegalArgumentException("type was " + this.mType + " but must be one of: TYPE_SUGGESTION(" + TYPE_SUGGESTION + "), TYPE_ACTION(" + TYPE_ACTION + NavigationBarInflaterView.KEY_CODE_END);
        }
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mType);
        this.mPinned = pinned;
        this.mTooltip = tooltip;
    }

    @Deprecated
    private void __metadata() {
    }
}
