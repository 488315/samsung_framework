package android.view.inputmethod;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes4.dex */
public class ExtractedTextRequest implements Parcelable {
    public static final Parcelable.Creator<ExtractedTextRequest> CREATOR = new Parcelable.Creator<ExtractedTextRequest>() { // from class: android.view.inputmethod.ExtractedTextRequest.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public ExtractedTextRequest createFromParcel(Parcel source) {
            ExtractedTextRequest res = new ExtractedTextRequest();
            res.token = source.readInt();
            res.flags = source.readInt();
            res.hintMaxLines = source.readInt();
            res.hintMaxChars = source.readInt();
            return res;
        }

        @Override // android.os.Parcelable.Creator
        public ExtractedTextRequest[] newArray(int size) {
            return new ExtractedTextRequest[size];
        }
    };
    public int flags;
    public int hintMaxChars;
    public int hintMaxLines;
    public int token;

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.token);
        dest.writeInt(this.flags);
        dest.writeInt(this.hintMaxLines);
        dest.writeInt(this.hintMaxChars);
    }

    /* renamed from: android.view.inputmethod.ExtractedTextRequest$1 */
    /* loaded from: classes4.dex */
    class AnonymousClass1 implements Parcelable.Creator<ExtractedTextRequest> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public ExtractedTextRequest createFromParcel(Parcel source) {
            ExtractedTextRequest res = new ExtractedTextRequest();
            res.token = source.readInt();
            res.flags = source.readInt();
            res.hintMaxLines = source.readInt();
            res.hintMaxChars = source.readInt();
            return res;
        }

        @Override // android.os.Parcelable.Creator
        public ExtractedTextRequest[] newArray(int size) {
            return new ExtractedTextRequest[size];
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
