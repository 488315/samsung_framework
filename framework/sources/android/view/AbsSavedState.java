package android.view;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes4.dex */
public abstract class AbsSavedState implements Parcelable {
    private final Parcelable mSuperState;
    public static final AbsSavedState EMPTY_STATE = new AbsSavedState() { // from class: android.view.AbsSavedState.1
        AnonymousClass1() {
        }
    };
    public static final Parcelable.Creator<AbsSavedState> CREATOR = new Parcelable.ClassLoaderCreator<AbsSavedState>() { // from class: android.view.AbsSavedState.2
        AnonymousClass2() {
        }

        @Override // android.os.Parcelable.Creator
        public AbsSavedState createFromParcel(Parcel in) {
            return createFromParcel(in, (ClassLoader) null);
        }

        @Override // android.os.Parcelable.ClassLoaderCreator
        public AbsSavedState createFromParcel(Parcel in, ClassLoader loader) {
            Parcelable superState = in.readParcelable(loader);
            if (superState != null) {
                throw new IllegalStateException("superState must be null");
            }
            return AbsSavedState.EMPTY_STATE;
        }

        @Override // android.os.Parcelable.Creator
        public AbsSavedState[] newArray(int size) {
            return new AbsSavedState[size];
        }
    };

    /* synthetic */ AbsSavedState(AbsSavedStateIA absSavedStateIA) {
        this();
    }

    /* renamed from: android.view.AbsSavedState$1 */
    /* loaded from: classes4.dex */
    class AnonymousClass1 extends AbsSavedState {
        AnonymousClass1() {
        }
    }

    private AbsSavedState() {
        this.mSuperState = null;
    }

    public AbsSavedState(Parcelable superState) {
        if (superState == null) {
            throw new IllegalArgumentException("superState must not be null");
        }
        this.mSuperState = superState != EMPTY_STATE ? superState : null;
    }

    public AbsSavedState(Parcel source) {
        this(source, null);
    }

    public AbsSavedState(Parcel source, ClassLoader loader) {
        Parcelable superState = source.readParcelable(loader);
        this.mSuperState = superState != null ? superState : EMPTY_STATE;
    }

    public final Parcelable getSuperState() {
        return this.mSuperState;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.mSuperState, flags);
    }

    /* renamed from: android.view.AbsSavedState$2 */
    /* loaded from: classes4.dex */
    class AnonymousClass2 implements Parcelable.ClassLoaderCreator<AbsSavedState> {
        AnonymousClass2() {
        }

        @Override // android.os.Parcelable.Creator
        public AbsSavedState createFromParcel(Parcel in) {
            return createFromParcel(in, (ClassLoader) null);
        }

        @Override // android.os.Parcelable.ClassLoaderCreator
        public AbsSavedState createFromParcel(Parcel in, ClassLoader loader) {
            Parcelable superState = in.readParcelable(loader);
            if (superState != null) {
                throw new IllegalStateException("superState must be null");
            }
            return AbsSavedState.EMPTY_STATE;
        }

        @Override // android.os.Parcelable.Creator
        public AbsSavedState[] newArray(int size) {
            return new AbsSavedState[size];
        }
    }
}
