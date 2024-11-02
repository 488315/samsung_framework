package android.support.v4.media;

import android.graphics.Bitmap;
import android.media.MediaDescription;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class MediaDescriptionCompat implements Parcelable {
    public static final Parcelable.Creator<MediaDescriptionCompat> CREATOR = new Parcelable.Creator() { // from class: android.support.v4.media.MediaDescriptionCompat.1
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return MediaDescriptionCompat.fromMediaDescription(MediaDescription.CREATOR.createFromParcel(parcel));
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new MediaDescriptionCompat[i];
        }
    };
    public final CharSequence mDescription;
    public MediaDescription mDescriptionFwk;
    public final Bundle mExtras;
    public final Bitmap mIcon;
    public final Uri mIconUri;
    public final String mMediaId;
    public final Uri mMediaUri;
    public final CharSequence mSubtitle;
    public final CharSequence mTitle;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class Builder {
        public CharSequence mDescription;
        public Bundle mExtras;
        public Bitmap mIcon;
        public Uri mIconUri;
        public String mMediaId;
        public Uri mMediaUri;
        public CharSequence mSubtitle;
        public CharSequence mTitle;
    }

    public MediaDescriptionCompat(String str, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, Bitmap bitmap, Uri uri, Bundle bundle, Uri uri2) {
        this.mMediaId = str;
        this.mTitle = charSequence;
        this.mSubtitle = charSequence2;
        this.mDescription = charSequence3;
        this.mIcon = bitmap;
        this.mIconUri = uri;
        this.mExtras = bundle;
        this.mMediaUri = uri2;
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0061  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0064  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.support.v4.media.MediaDescriptionCompat fromMediaDescription(java.lang.Object r11) {
        /*
            r0 = 0
            if (r11 == 0) goto L82
            android.support.v4.media.MediaDescriptionCompat$Builder r1 = new android.support.v4.media.MediaDescriptionCompat$Builder
            r1.<init>()
            android.media.MediaDescription r11 = (android.media.MediaDescription) r11
            java.lang.String r2 = r11.getMediaId()
            r1.mMediaId = r2
            java.lang.CharSequence r2 = r11.getTitle()
            r1.mTitle = r2
            java.lang.CharSequence r2 = r11.getSubtitle()
            r1.mSubtitle = r2
            java.lang.CharSequence r2 = r11.getDescription()
            r1.mDescription = r2
            android.graphics.Bitmap r2 = r11.getIconBitmap()
            r1.mIcon = r2
            android.net.Uri r2 = r11.getIconUri()
            r1.mIconUri = r2
            android.os.Bundle r2 = r11.getExtras()
            if (r2 == 0) goto L38
            android.os.Bundle r2 = android.support.v4.media.session.MediaSessionCompat.unparcelWithClassLoader(r2)
        L38:
            java.lang.String r3 = "android.support.v4.media.description.MEDIA_URI"
            if (r2 == 0) goto L43
            android.os.Parcelable r4 = r2.getParcelable(r3)
            android.net.Uri r4 = (android.net.Uri) r4
            goto L44
        L43:
            r4 = r0
        L44:
            if (r4 == 0) goto L5c
            java.lang.String r5 = "android.support.v4.media.description.NULL_BUNDLE_FLAG"
            boolean r6 = r2.containsKey(r5)
            if (r6 == 0) goto L56
            int r6 = r2.size()
            r7 = 2
            if (r6 != r7) goto L56
            goto L5d
        L56:
            r2.remove(r3)
            r2.remove(r5)
        L5c:
            r0 = r2
        L5d:
            r1.mExtras = r0
            if (r4 == 0) goto L64
            r1.mMediaUri = r4
            goto L6a
        L64:
            android.net.Uri r0 = r11.getMediaUri()
            r1.mMediaUri = r0
        L6a:
            android.support.v4.media.MediaDescriptionCompat r0 = new android.support.v4.media.MediaDescriptionCompat
            java.lang.String r3 = r1.mMediaId
            java.lang.CharSequence r4 = r1.mTitle
            java.lang.CharSequence r5 = r1.mSubtitle
            java.lang.CharSequence r6 = r1.mDescription
            android.graphics.Bitmap r7 = r1.mIcon
            android.net.Uri r8 = r1.mIconUri
            android.os.Bundle r9 = r1.mExtras
            android.net.Uri r10 = r1.mMediaUri
            r2 = r0
            r2.<init>(r3, r4, r5, r6, r7, r8, r9, r10)
            r0.mDescriptionFwk = r11
        L82:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.media.MediaDescriptionCompat.fromMediaDescription(java.lang.Object):android.support.v4.media.MediaDescriptionCompat");
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final String toString() {
        return ((Object) this.mTitle) + ", " + ((Object) this.mSubtitle) + ", " + ((Object) this.mDescription);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        MediaDescription mediaDescription = this.mDescriptionFwk;
        if (mediaDescription == null) {
            MediaDescription.Builder builder = new MediaDescription.Builder();
            builder.setMediaId(this.mMediaId);
            builder.setTitle(this.mTitle);
            builder.setSubtitle(this.mSubtitle);
            builder.setDescription(this.mDescription);
            builder.setIconBitmap(this.mIcon);
            builder.setIconUri(this.mIconUri);
            builder.setExtras(this.mExtras);
            builder.setMediaUri(this.mMediaUri);
            mediaDescription = builder.build();
            this.mDescriptionFwk = mediaDescription;
        }
        mediaDescription.writeToParcel(parcel, i);
    }

    public MediaDescriptionCompat(Parcel parcel) {
        this.mMediaId = parcel.readString();
        this.mTitle = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mSubtitle = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mDescription = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        ClassLoader classLoader = MediaDescriptionCompat.class.getClassLoader();
        this.mIcon = (Bitmap) parcel.readParcelable(classLoader);
        this.mIconUri = (Uri) parcel.readParcelable(classLoader);
        this.mExtras = parcel.readBundle(classLoader);
        this.mMediaUri = (Uri) parcel.readParcelable(classLoader);
    }
}
