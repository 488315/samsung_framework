package android.view.inputmethod;

import android.graphics.PointF;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class JoinOrSplitGesture extends HandwritingGesture implements Parcelable {
    public static final Parcelable.Creator<JoinOrSplitGesture> CREATOR = new Parcelable.Creator<JoinOrSplitGesture>() { // from class: android.view.inputmethod.JoinOrSplitGesture.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public JoinOrSplitGesture createFromParcel(Parcel source) {
            return new JoinOrSplitGesture(source);
        }

        @Override // android.os.Parcelable.Creator
        public JoinOrSplitGesture[] newArray(int size) {
            return new JoinOrSplitGesture[size];
        }
    };
    private final PointF mPoint;

    /* synthetic */ JoinOrSplitGesture(PointF pointF, String str, JoinOrSplitGestureIA joinOrSplitGestureIA) {
        this(pointF, str);
    }

    /* synthetic */ JoinOrSplitGesture(Parcel parcel, JoinOrSplitGestureIA joinOrSplitGestureIA) {
        this(parcel);
    }

    private JoinOrSplitGesture(PointF point, String fallbackText) {
        this.mType = 16;
        this.mPoint = point;
        this.mFallbackText = fallbackText;
    }

    private JoinOrSplitGesture(Parcel source) {
        this.mType = 16;
        this.mPoint = (PointF) source.readTypedObject(PointF.CREATOR);
        this.mFallbackText = source.readString8();
    }

    public PointF getJoinOrSplitPoint() {
        return this.mPoint;
    }

    /* loaded from: classes4.dex */
    public static final class Builder {
        private String mFallbackText;
        private PointF mPoint;

        public Builder setJoinOrSplitPoint(PointF point) {
            this.mPoint = point;
            return this;
        }

        public Builder setFallbackText(String fallbackText) {
            this.mFallbackText = fallbackText;
            return this;
        }

        public JoinOrSplitGesture build() {
            if (this.mPoint == null) {
                throw new IllegalArgumentException("Point must be set.");
            }
            return new JoinOrSplitGesture(this.mPoint, this.mFallbackText);
        }
    }

    /* renamed from: android.view.inputmethod.JoinOrSplitGesture$1 */
    /* loaded from: classes4.dex */
    class AnonymousClass1 implements Parcelable.Creator<JoinOrSplitGesture> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public JoinOrSplitGesture createFromParcel(Parcel source) {
            return new JoinOrSplitGesture(source);
        }

        @Override // android.os.Parcelable.Creator
        public JoinOrSplitGesture[] newArray(int size) {
            return new JoinOrSplitGesture[size];
        }
    }

    public int hashCode() {
        return Objects.hash(this.mPoint, this.mFallbackText);
    }

    public boolean equals(Object o) {
        if (!(o instanceof JoinOrSplitGesture)) {
            return false;
        }
        JoinOrSplitGesture that = (JoinOrSplitGesture) o;
        return Objects.equals(this.mPoint, that.mPoint) && Objects.equals(this.mFallbackText, that.mFallbackText);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedObject(this.mPoint, flags);
        dest.writeString8(this.mFallbackText);
    }
}
