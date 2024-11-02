package android.media.tv;

import android.annotation.SystemApi;
import android.content.ContentResolver;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

@SystemApi
/* loaded from: classes2.dex */
public final class TvContentRatingSystemInfo implements Parcelable {
    public static final Parcelable.Creator<TvContentRatingSystemInfo> CREATOR = new Parcelable.Creator<TvContentRatingSystemInfo>() { // from class: android.media.tv.TvContentRatingSystemInfo.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public TvContentRatingSystemInfo createFromParcel(Parcel in) {
            return new TvContentRatingSystemInfo(in);
        }

        @Override // android.os.Parcelable.Creator
        public TvContentRatingSystemInfo[] newArray(int size) {
            return new TvContentRatingSystemInfo[size];
        }
    };
    private final ApplicationInfo mApplicationInfo;
    private final Uri mXmlUri;

    /* synthetic */ TvContentRatingSystemInfo(Parcel parcel, TvContentRatingSystemInfoIA tvContentRatingSystemInfoIA) {
        this(parcel);
    }

    public static final TvContentRatingSystemInfo createTvContentRatingSystemInfo(int xmlResourceId, ApplicationInfo applicationInfo) {
        Uri uri = new Uri.Builder().scheme(ContentResolver.SCHEME_ANDROID_RESOURCE).authority(applicationInfo.packageName).appendPath(String.valueOf(xmlResourceId)).build();
        return new TvContentRatingSystemInfo(uri, applicationInfo);
    }

    private TvContentRatingSystemInfo(Uri xmlUri, ApplicationInfo applicationInfo) {
        this.mXmlUri = xmlUri;
        this.mApplicationInfo = applicationInfo;
    }

    public final boolean isSystemDefined() {
        return (this.mApplicationInfo.flags & 1) != 0;
    }

    public final Uri getXmlUri() {
        return this.mXmlUri;
    }

    /* renamed from: android.media.tv.TvContentRatingSystemInfo$1 */
    /* loaded from: classes2.dex */
    class AnonymousClass1 implements Parcelable.Creator<TvContentRatingSystemInfo> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public TvContentRatingSystemInfo createFromParcel(Parcel in) {
            return new TvContentRatingSystemInfo(in);
        }

        @Override // android.os.Parcelable.Creator
        public TvContentRatingSystemInfo[] newArray(int size) {
            return new TvContentRatingSystemInfo[size];
        }
    }

    private TvContentRatingSystemInfo(Parcel in) {
        this.mXmlUri = (Uri) in.readParcelable(null, Uri.class);
        this.mApplicationInfo = (ApplicationInfo) in.readParcelable(null, ApplicationInfo.class);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.mXmlUri, flags);
        dest.writeParcelable(this.mApplicationInfo, flags);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
