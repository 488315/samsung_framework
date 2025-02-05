package android.view.inputmethod;

import android.graphics.RectF;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class EditorBoundsInfo implements Parcelable {
    public static final Parcelable.Creator<EditorBoundsInfo> CREATOR = new Parcelable.Creator<EditorBoundsInfo>() { // from class: android.view.inputmethod.EditorBoundsInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EditorBoundsInfo createFromParcel(Parcel source) {
            return new EditorBoundsInfo(source);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EditorBoundsInfo[] newArray(int size) {
            return new EditorBoundsInfo[size];
        }
    };
    private final RectF mEditorBounds;
    private final RectF mHandwritingBounds;
    private final int mHashCode;

    private EditorBoundsInfo(Parcel source) {
        this.mHashCode = source.readInt();
        this.mEditorBounds = (RectF) source.readTypedObject(RectF.CREATOR);
        this.mHandwritingBounds = (RectF) source.readTypedObject(RectF.CREATOR);
    }

    public RectF getEditorBounds() {
        return this.mEditorBounds;
    }

    public RectF getHandwritingBounds() {
        return this.mHandwritingBounds;
    }

    public int hashCode() {
        return this.mHashCode;
    }

    public String toString() {
        return "EditorBoundsInfo{mEditorBounds=" + this.mEditorBounds + " mHandwritingBounds=" + this.mHandwritingBounds + "}";
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof EditorBoundsInfo)) {
            return false;
        }
        EditorBoundsInfo bounds = (EditorBoundsInfo) obj;
        return Objects.equals(bounds.mEditorBounds, this.mEditorBounds) && Objects.equals(bounds.mHandwritingBounds, this.mHandwritingBounds);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mHashCode);
        dest.writeTypedObject(this.mEditorBounds, flags);
        dest.writeTypedObject(this.mHandwritingBounds, flags);
    }

    public static final class Builder {
        private RectF mEditorBounds = null;
        private RectF mHandwritingBounds = null;

        public Builder setEditorBounds(RectF bounds) {
            this.mEditorBounds = bounds;
            return this;
        }

        public Builder setHandwritingBounds(RectF bounds) {
            this.mHandwritingBounds = bounds;
            return this;
        }

        public EditorBoundsInfo build() {
            return new EditorBoundsInfo(this);
        }
    }

    private EditorBoundsInfo(Builder builder) {
        this.mEditorBounds = builder.mEditorBounds;
        this.mHandwritingBounds = builder.mHandwritingBounds;
        int hash = Objects.hashCode(this.mEditorBounds);
        this.mHashCode = (hash * 31) + Objects.hashCode(this.mHandwritingBounds);
    }
}
