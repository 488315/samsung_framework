package com.android.internal.statusbar;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.ArrayMap;
import com.android.internal.view.AppearanceRegion;

/* loaded from: classes5.dex */
public final class RegisterStatusBarResult implements Parcelable {
    public static final Parcelable.Creator<RegisterStatusBarResult> CREATOR = new Parcelable.Creator<RegisterStatusBarResult>() { // from class: com.android.internal.statusbar.RegisterStatusBarResult.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public RegisterStatusBarResult createFromParcel(Parcel source) {
            ArrayMap<String, StatusBarIcon> icons = source.createTypedArrayMap(StatusBarIcon.CREATOR);
            int disabledFlags1 = source.readInt();
            int appearance = source.readInt();
            AppearanceRegion[] appearanceRegions = (AppearanceRegion[]) source.readParcelableArray(null, AppearanceRegion.class);
            int imeWindowVis = source.readInt();
            int imeBackDisposition = source.readInt();
            boolean showImeSwitcher = source.readBoolean();
            int disabledFlags2 = source.readInt();
            IBinder imeToken = source.readStrongBinder();
            boolean navbarColorManagedByIme = source.readBoolean();
            int behavior = source.readInt();
            int requestedVisibleTypes = source.readInt();
            String packageName = source.readString();
            int transientBarTypes = source.readInt();
            LetterboxDetails[] letterboxDetails = (LetterboxDetails[]) source.readParcelableArray(null, LetterboxDetails.class);
            return new RegisterStatusBarResult(icons, disabledFlags1, appearance, appearanceRegions, imeWindowVis, imeBackDisposition, showImeSwitcher, disabledFlags2, imeToken, navbarColorManagedByIme, behavior, requestedVisibleTypes, packageName, transientBarTypes, letterboxDetails);
        }

        @Override // android.os.Parcelable.Creator
        public RegisterStatusBarResult[] newArray(int size) {
            return new RegisterStatusBarResult[size];
        }
    };
    public final int mAppearance;
    public final AppearanceRegion[] mAppearanceRegions;
    public final int mBehavior;
    public final int mDisabledFlags1;
    public final int mDisabledFlags2;
    public final ArrayMap<String, StatusBarIcon> mIcons;
    public final int mImeBackDisposition;
    public final IBinder mImeToken;
    public final int mImeWindowVis;
    public final LetterboxDetails[] mLetterboxDetails;
    public final boolean mNavbarColorManagedByIme;
    public final String mPackageName;
    public final int mRequestedVisibleTypes;
    public final boolean mShowImeSwitcher;
    public final int mTransientBarTypes;

    public RegisterStatusBarResult(ArrayMap<String, StatusBarIcon> icons, int disabledFlags1, int appearance, AppearanceRegion[] appearanceRegions, int imeWindowVis, int imeBackDisposition, boolean showImeSwitcher, int disabledFlags2, IBinder imeToken, boolean navbarColorManagedByIme, int behavior, int requestedVisibleTypes, String packageName, int transientBarTypes, LetterboxDetails[] letterboxDetails) {
        this.mIcons = new ArrayMap<>(icons);
        this.mDisabledFlags1 = disabledFlags1;
        this.mAppearance = appearance;
        this.mAppearanceRegions = appearanceRegions;
        this.mImeWindowVis = imeWindowVis;
        this.mImeBackDisposition = imeBackDisposition;
        this.mShowImeSwitcher = showImeSwitcher;
        this.mDisabledFlags2 = disabledFlags2;
        this.mImeToken = imeToken;
        this.mNavbarColorManagedByIme = navbarColorManagedByIme;
        this.mBehavior = behavior;
        this.mRequestedVisibleTypes = requestedVisibleTypes;
        this.mPackageName = packageName;
        this.mTransientBarTypes = transientBarTypes;
        this.mLetterboxDetails = letterboxDetails;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedArrayMap(this.mIcons, flags);
        dest.writeInt(this.mDisabledFlags1);
        dest.writeInt(this.mAppearance);
        dest.writeParcelableArray(this.mAppearanceRegions, 0);
        dest.writeInt(this.mImeWindowVis);
        dest.writeInt(this.mImeBackDisposition);
        dest.writeBoolean(this.mShowImeSwitcher);
        dest.writeInt(this.mDisabledFlags2);
        dest.writeStrongBinder(this.mImeToken);
        dest.writeBoolean(this.mNavbarColorManagedByIme);
        dest.writeInt(this.mBehavior);
        dest.writeInt(this.mRequestedVisibleTypes);
        dest.writeString(this.mPackageName);
        dest.writeInt(this.mTransientBarTypes);
        dest.writeParcelableArray(this.mLetterboxDetails, flags);
    }

    /* renamed from: com.android.internal.statusbar.RegisterStatusBarResult$1 */
    /* loaded from: classes5.dex */
    class AnonymousClass1 implements Parcelable.Creator<RegisterStatusBarResult> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public RegisterStatusBarResult createFromParcel(Parcel source) {
            ArrayMap<String, StatusBarIcon> icons = source.createTypedArrayMap(StatusBarIcon.CREATOR);
            int disabledFlags1 = source.readInt();
            int appearance = source.readInt();
            AppearanceRegion[] appearanceRegions = (AppearanceRegion[]) source.readParcelableArray(null, AppearanceRegion.class);
            int imeWindowVis = source.readInt();
            int imeBackDisposition = source.readInt();
            boolean showImeSwitcher = source.readBoolean();
            int disabledFlags2 = source.readInt();
            IBinder imeToken = source.readStrongBinder();
            boolean navbarColorManagedByIme = source.readBoolean();
            int behavior = source.readInt();
            int requestedVisibleTypes = source.readInt();
            String packageName = source.readString();
            int transientBarTypes = source.readInt();
            LetterboxDetails[] letterboxDetails = (LetterboxDetails[]) source.readParcelableArray(null, LetterboxDetails.class);
            return new RegisterStatusBarResult(icons, disabledFlags1, appearance, appearanceRegions, imeWindowVis, imeBackDisposition, showImeSwitcher, disabledFlags2, imeToken, navbarColorManagedByIme, behavior, requestedVisibleTypes, packageName, transientBarTypes, letterboxDetails);
        }

        @Override // android.os.Parcelable.Creator
        public RegisterStatusBarResult[] newArray(int size) {
            return new RegisterStatusBarResult[size];
        }
    }
}
