package android.view.inputmethod;

import android.graphics.RectF;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class SelectRangeGesture extends PreviewableHandwritingGesture implements Parcelable {
    public static final Parcelable.Creator<SelectRangeGesture> CREATOR = new Parcelable.Creator<SelectRangeGesture>() { // from class: android.view.inputmethod.SelectRangeGesture.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SelectRangeGesture createFromParcel(Parcel source) {
            return new SelectRangeGesture(source);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SelectRangeGesture[] newArray(int size) {
            return new SelectRangeGesture[size];
        }
    };
    private RectF mEndArea;
    private int mGranularity;
    private RectF mStartArea;

    private SelectRangeGesture(int granularity, RectF startArea, RectF endArea, String fallbackText) {
        this.mType = 32;
        this.mStartArea = startArea;
        this.mEndArea = endArea;
        this.mGranularity = granularity;
        this.mFallbackText = fallbackText;
    }

    private SelectRangeGesture(Parcel source) {
        this.mType = 32;
        this.mFallbackText = source.readString8();
        this.mGranularity = source.readInt();
        this.mStartArea = (RectF) source.readTypedObject(RectF.CREATOR);
        this.mEndArea = (RectF) source.readTypedObject(RectF.CREATOR);
    }

    public int getGranularity() {
        return this.mGranularity;
    }

    public RectF getSelectionStartArea() {
        return this.mStartArea;
    }

    public RectF getSelectionEndArea() {
        return this.mEndArea;
    }

    public static final class Builder {
        private RectF mEndArea;
        private String mFallbackText;
        private int mGranularity;
        private RectF mStartArea;

        public Builder setGranularity(int granularity) {
            this.mGranularity = granularity;
            return this;
        }

        public Builder setSelectionStartArea(RectF startArea) {
            this.mStartArea = startArea;
            return this;
        }

        public Builder setSelectionEndArea(RectF endArea) {
            this.mEndArea = endArea;
            return this;
        }

        public Builder setFallbackText(String fallbackText) {
            this.mFallbackText = fallbackText;
            return this;
        }

        public SelectRangeGesture build() {
            if (this.mStartArea == null || this.mStartArea.isEmpty() || this.mEndArea == null || this.mEndArea.isEmpty()) {
                throw new IllegalArgumentException("Selection area must be set.");
            }
            if (this.mGranularity <= 0) {
                throw new IllegalArgumentException("Selection granularity must be set.");
            }
            return new SelectRangeGesture(this.mGranularity, this.mStartArea, this.mEndArea, this.mFallbackText);
        }
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mGranularity), this.mStartArea, this.mEndArea, this.mFallbackText);
    }

    public boolean equals(Object o) {
        if (!(o instanceof SelectRangeGesture)) {
            return false;
        }
        SelectRangeGesture that = (SelectRangeGesture) o;
        if (this.mGranularity == that.mGranularity && Objects.equals(this.mFallbackText, that.mFallbackText) && Objects.equals(this.mStartArea, that.mStartArea)) {
            return Objects.equals(this.mEndArea, that.mEndArea);
        }
        return false;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString8(this.mFallbackText);
        dest.writeInt(this.mGranularity);
        dest.writeTypedObject(this.mStartArea, flags);
        dest.writeTypedObject(this.mEndArea, flags);
    }
}
