package android.view.inputmethod;

import android.graphics.RectF;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class DeleteGesture extends PreviewableHandwritingGesture implements Parcelable {
    public static final Parcelable.Creator<DeleteGesture> CREATOR = new Parcelable.Creator<DeleteGesture>() { // from class: android.view.inputmethod.DeleteGesture.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public DeleteGesture createFromParcel(Parcel source) {
            return new DeleteGesture(source);
        }

        @Override // android.os.Parcelable.Creator
        public DeleteGesture[] newArray(int size) {
            return new DeleteGesture[size];
        }
    };
    private RectF mArea;
    private int mGranularity;

    /* synthetic */ DeleteGesture(int i, RectF rectF, String str, DeleteGestureIA deleteGestureIA) {
        this(i, rectF, str);
    }

    /* synthetic */ DeleteGesture(Parcel parcel, DeleteGestureIA deleteGestureIA) {
        this(parcel);
    }

    private DeleteGesture(int granularity, RectF area, String fallbackText) {
        this.mType = 4;
        this.mArea = area;
        this.mGranularity = granularity;
        this.mFallbackText = fallbackText;
    }

    private DeleteGesture(Parcel source) {
        this.mType = 4;
        this.mFallbackText = source.readString8();
        this.mGranularity = source.readInt();
        this.mArea = (RectF) source.readTypedObject(RectF.CREATOR);
    }

    public int getGranularity() {
        return this.mGranularity;
    }

    public RectF getDeletionArea() {
        return this.mArea;
    }

    /* loaded from: classes4.dex */
    public static final class Builder {
        private RectF mArea;
        private String mFallbackText;
        private int mGranularity;

        public Builder setGranularity(int granularity) {
            this.mGranularity = granularity;
            return this;
        }

        public Builder setDeletionArea(RectF area) {
            this.mArea = area;
            return this;
        }

        public Builder setFallbackText(String fallbackText) {
            this.mFallbackText = fallbackText;
            return this;
        }

        public DeleteGesture build() {
            RectF rectF = this.mArea;
            if (rectF == null || rectF.isEmpty()) {
                throw new IllegalArgumentException("Deletion area must be set.");
            }
            if (this.mGranularity <= 0) {
                throw new IllegalArgumentException("Deletion granularity must be set.");
            }
            return new DeleteGesture(this.mGranularity, this.mArea, this.mFallbackText);
        }
    }

    /* renamed from: android.view.inputmethod.DeleteGesture$1 */
    /* loaded from: classes4.dex */
    class AnonymousClass1 implements Parcelable.Creator<DeleteGesture> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public DeleteGesture createFromParcel(Parcel source) {
            return new DeleteGesture(source);
        }

        @Override // android.os.Parcelable.Creator
        public DeleteGesture[] newArray(int size) {
            return new DeleteGesture[size];
        }
    }

    public int hashCode() {
        return Objects.hash(this.mArea, Integer.valueOf(this.mGranularity), this.mFallbackText);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DeleteGesture)) {
            return false;
        }
        DeleteGesture that = (DeleteGesture) o;
        if (this.mGranularity == that.mGranularity && Objects.equals(this.mFallbackText, that.mFallbackText)) {
            return Objects.equals(this.mArea, that.mArea);
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
        dest.writeTypedObject(this.mArea, flags);
    }
}
