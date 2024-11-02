package android.view.inputmethod;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

/* loaded from: classes4.dex */
public final class CorrectionInfo implements Parcelable {
    public static final Parcelable.Creator<CorrectionInfo> CREATOR = new Parcelable.Creator<CorrectionInfo>() { // from class: android.view.inputmethod.CorrectionInfo.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public CorrectionInfo createFromParcel(Parcel source) {
            return new CorrectionInfo(source);
        }

        @Override // android.os.Parcelable.Creator
        public CorrectionInfo[] newArray(int size) {
            return new CorrectionInfo[size];
        }
    };
    private final CharSequence mNewText;
    private final int mOffset;
    private final CharSequence mOldText;

    /* synthetic */ CorrectionInfo(Parcel parcel, CorrectionInfoIA correctionInfoIA) {
        this(parcel);
    }

    public CorrectionInfo(int offset, CharSequence oldText, CharSequence newText) {
        this.mOffset = offset;
        this.mOldText = oldText;
        this.mNewText = newText;
    }

    private CorrectionInfo(Parcel source) {
        this.mOffset = source.readInt();
        this.mOldText = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(source);
        this.mNewText = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(source);
    }

    public int getOffset() {
        return this.mOffset;
    }

    public CharSequence getOldText() {
        return this.mOldText;
    }

    public CharSequence getNewText() {
        return this.mNewText;
    }

    public String toString() {
        return "CorrectionInfo{#" + this.mOffset + " \"" + ((Object) this.mOldText) + "\" -> \"" + ((Object) this.mNewText) + "\"}";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mOffset);
        TextUtils.writeToParcel(this.mOldText, dest, flags);
        TextUtils.writeToParcel(this.mNewText, dest, flags);
    }

    /* renamed from: android.view.inputmethod.CorrectionInfo$1 */
    /* loaded from: classes4.dex */
    class AnonymousClass1 implements Parcelable.Creator<CorrectionInfo> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public CorrectionInfo createFromParcel(Parcel source) {
            return new CorrectionInfo(source);
        }

        @Override // android.os.Parcelable.Creator
        public CorrectionInfo[] newArray(int size) {
            return new CorrectionInfo[size];
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
