package com.samsung.android.allshare;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.CalendarContract;
import android.security.keystore.KeyProperties;
import com.android.internal.widget.MessagingMessage;
import com.google.android.mms.ContentType;
import com.samsung.android.allshare.ItemCreator;
import com.samsung.android.wallpaperbackup.BnRConstants;
import com.sec.android.allshare.iface.IBundleHolder;
import com.sec.android.allshare.iface.message.AllShareKey;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

/* loaded from: classes5.dex */
public abstract class Item implements Parcelable {
    public abstract String getAlbumTitle();

    public abstract String getArtist();

    public abstract int getBitrate();

    public abstract ArrayList<Caption> getCaptionList();

    public abstract String getChannelNr();

    public abstract ContentBuildType getContentBuildType();

    @Deprecated
    public abstract Date getDate();

    public abstract long getDuration();

    public abstract String getExtension();

    public abstract long getFileSize();

    public abstract String getGenre();

    @Deprecated
    public abstract Location getLocation();

    public abstract String getMimetype();

    @Deprecated
    public abstract String getResolution();

    @Deprecated
    public abstract ArrayList<Resource> getResourceList();

    public abstract SeekMode getSeekMode();

    public abstract Uri getSubtitle();

    public abstract ArrayList<Subtitle> getSubtitleList();

    public abstract Uri getThumbnail();

    public abstract String getTitle();

    @Deprecated
    public abstract MediaType getType();

    public abstract Uri getURI();

    public abstract WebContentBuilder.DeliveryMode getWebContentDeliveryMode();

    @Deprecated
    public abstract WebContentBuilder.PlayMode getWebContentPlayMode();

    public abstract boolean isRootFolder();

    /* loaded from: classes5.dex */
    public enum MediaType {
        ITEM_FOLDER("ITEM_FOLDER"),
        ITEM_AUDIO("ITEM_AUDIO"),
        ITEM_IMAGE("ITEM_IMAGE"),
        ITEM_VIDEO("ITEM_VIDEO"),
        ITEM_UNKNOWN("ITEM_UNKNOWN");

        private final String enumString;

        MediaType(String enumStr) {
            this.enumString = enumStr;
        }

        public String enumToString() {
            return this.enumString;
        }

        public static MediaType stringToEnum(String enumStr) {
            if (enumStr == null) {
                return ITEM_UNKNOWN;
            }
            if (enumStr.equals("ITEM_AUDIO")) {
                return ITEM_AUDIO;
            }
            if (enumStr.equals("ITEM_FOLDER")) {
                return ITEM_FOLDER;
            }
            if (enumStr.equals("ITEM_IMAGE")) {
                return ITEM_IMAGE;
            }
            if (enumStr.equals("ITEM_UNKNOWN")) {
                return ITEM_UNKNOWN;
            }
            if (enumStr.equals("ITEM_VIDEO")) {
                return ITEM_VIDEO;
            }
            return ITEM_UNKNOWN;
        }
    }

    /* loaded from: classes5.dex */
    public enum ContentAttributeType {
        CONTENT_360_VIEW("360View"),
        CONTENT_UNKNOWN("UNKNOWN");

        private final String enumString;

        ContentAttributeType(String enumStr) {
            this.enumString = enumStr;
        }

        public String enumToString() {
            return this.enumString;
        }

        public static ContentAttributeType stringToEnum(String enumStr) {
            if (enumStr == null) {
                return CONTENT_UNKNOWN;
            }
            if (enumStr.equals("360View")) {
                return CONTENT_360_VIEW;
            }
            if (enumStr.equals("UNKNOWN")) {
                return CONTENT_UNKNOWN;
            }
            return CONTENT_UNKNOWN;
        }
    }

    /* loaded from: classes5.dex */
    public enum ContentBuildType {
        LOCAL(CalendarContract.ACCOUNT_TYPE_LOCAL),
        PROVIDER("PROVIDER"),
        WEB("WEB"),
        UNKNOWN("UNKNOWN");

        private final String enumString;

        ContentBuildType(String enumStr) {
            this.enumString = enumStr;
        }

        public String enumToString() {
            return this.enumString;
        }
    }

    /* loaded from: classes5.dex */
    public enum SeekMode {
        BYTE("BYTE"),
        TIME("TIME"),
        ANY("ANY"),
        NONE(KeyProperties.DIGEST_NONE),
        UNKNOWN("UNKNOWN");

        private final String enumString;

        SeekMode(String enumStr) {
            this.enumString = enumStr;
        }

        public String enumToString() {
            return this.enumString;
        }

        public static SeekMode stringToEnum(String enumStr) {
            if (enumStr == null) {
                return UNKNOWN;
            }
            if (enumStr.equals("ANY")) {
                return ANY;
            }
            if (enumStr.equals("BYTE")) {
                return BYTE;
            }
            if (enumStr.equals(KeyProperties.DIGEST_NONE)) {
                return NONE;
            }
            if (enumStr.equals("TIME")) {
                return TIME;
            }
            if (enumStr.equals("UNKNOWN")) {
                return UNKNOWN;
            }
            return UNKNOWN;
        }
    }

    /* loaded from: classes5.dex */
    public abstract class Resource implements Parcelable {
        public abstract int getBitrate();

        public abstract long getDuration();

        public abstract long getFileSize();

        public abstract String getMimetype();

        @Deprecated
        public abstract String getResolution();

        public abstract SeekMode getSeekMode();

        public abstract MediaType getType();

        @Deprecated
        public abstract Uri getURI();

        /* JADX INFO: Access modifiers changed from: protected */
        public Resource() {
        }
    }

    /* loaded from: classes5.dex */
    public static class LocalContentBuilder {
        private String mFilepath;
        private String mMimetype;
        private String mTitle = null;
        private String mSubtitlePath = null;
        private ArrayList<Caption> mCaptionList = new ArrayList<>();
        private ContentAttributeType mContentAttribute = ContentAttributeType.CONTENT_UNKNOWN;

        public LocalContentBuilder(String filepath, String mimeType) {
            this.mFilepath = null;
            this.mMimetype = null;
            this.mFilepath = filepath;
            this.mMimetype = mimeType;
        }

        public LocalContentBuilder setTitle(String title) {
            this.mTitle = title;
            return this;
        }

        public LocalContentBuilder setSubtitle(String filePath) {
            this.mSubtitlePath = filePath;
            return this;
        }

        public LocalContentBuilder setCaptionList(ArrayList<Caption> captionList) {
            this.mCaptionList = captionList;
            return this;
        }

        public LocalContentBuilder setContentAttribute(ContentAttributeType contentAttribute) {
            this.mContentAttribute = contentAttribute;
            return this;
        }

        public Item build() {
            if (!checkFilePathValid(this.mSubtitlePath)) {
                this.mSubtitlePath = null;
            }
            if (this.mFilepath.startsWith("content:")) {
                return new BuilderGeneratedItem(ItemCreator.ConstructorType.LOCAL_CONTENT, this.mFilepath, this.mTitle, this.mSubtitlePath, this.mCaptionList, this.mContentAttribute, this.mMimetype);
            }
            MediaType type = Item.convertItemTypeFromMimeType(this.mMimetype);
            switch (AnonymousClass1.$SwitchMap$com$samsung$android$allshare$Item$MediaType[type.ordinal()]) {
                case 1:
                case 2:
                case 3:
                    return new BuilderGeneratedItem(ItemCreator.ConstructorType.LOCAL_CONTENT, this.mFilepath, this.mTitle, this.mSubtitlePath, this.mCaptionList, this.mContentAttribute, this.mMimetype);
                default:
                    DLog.e_api("Item", "build error!");
                    return null;
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public static boolean checkFilePathValid(String filePath) {
            String absoluteFilePath;
            if (filePath == null || filePath.length() == 0) {
                DLog.e_api("Item", "[checkFilePathValid] filePath is null or length is 0");
                return false;
            }
            if (filePath.startsWith("file:")) {
                absoluteFilePath = filePath.substring("file://".length());
            } else {
                absoluteFilePath = filePath;
            }
            if (absoluteFilePath.startsWith("/data/data")) {
                return false;
            }
            File file = new File(absoluteFilePath);
            if (!file.exists()) {
                return false;
            }
            return true;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static class BuilderGeneratedItem extends Item implements IBundleHolder {
        public static final Parcelable.Creator<BuilderGeneratedItem> CREATOR = new Parcelable.Creator<BuilderGeneratedItem>() { // from class: com.samsung.android.allshare.Item.BuilderGeneratedItem.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public BuilderGeneratedItem createFromParcel(Parcel source) {
                return new BuilderGeneratedItem(source);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public BuilderGeneratedItem[] newArray(int size) {
                return new BuilderGeneratedItem[size];
            }
        };
        private String mAlbumTitle;
        private String mArtist;
        private ArrayList<Caption> mCaptionList;
        private ItemCreator.ConstructorType mConType;
        private ContentAttributeType mContentAttribute;
        private Date mDate;
        private WebContentBuilder.DeliveryMode mDeliveryMode;
        private long mDuration;
        private String mGenre;
        private String mItemFilepath;
        private String mItemMimetype;
        private String mItemTitle;
        private String mSubtitlePath;

        private BuilderGeneratedItem(ItemCreator.ConstructorType conType, String filepath, String title, String subtitlePath, ArrayList<Caption> captionList, ContentAttributeType contentAttr, String mimeType) {
            this.mConType = ItemCreator.ConstructorType.UNKNOWN;
            this.mDeliveryMode = WebContentBuilder.DeliveryMode.UNKNOWN;
            this.mCaptionList = null;
            this.mContentAttribute = null;
            this.mArtist = null;
            this.mAlbumTitle = null;
            this.mGenre = null;
            this.mDate = null;
            this.mDuration = -1L;
            this.mItemFilepath = filepath;
            this.mItemMimetype = mimeType;
            this.mItemTitle = title;
            this.mConType = conType;
            this.mDeliveryMode = WebContentBuilder.DeliveryMode.UNKNOWN;
            this.mSubtitlePath = subtitlePath;
            this.mCaptionList = captionList;
            this.mContentAttribute = contentAttr;
        }

        private BuilderGeneratedItem(ItemCreator.ConstructorType conType, Uri uri, String title, String mimeType, String subtitlePath, ArrayList<Caption> captionList, ContentAttributeType contentAttr, WebContentBuilder.DeliveryMode deliveryMode) {
            this.mConType = ItemCreator.ConstructorType.UNKNOWN;
            this.mDeliveryMode = WebContentBuilder.DeliveryMode.UNKNOWN;
            this.mCaptionList = null;
            this.mContentAttribute = null;
            this.mArtist = null;
            this.mAlbumTitle = null;
            this.mGenre = null;
            this.mDate = null;
            this.mDuration = -1L;
            this.mItemFilepath = uri.toString();
            this.mItemMimetype = mimeType;
            this.mItemTitle = title;
            this.mConType = conType;
            this.mDeliveryMode = deliveryMode;
            this.mSubtitlePath = subtitlePath;
            this.mCaptionList = captionList;
            this.mContentAttribute = contentAttr;
        }

        private BuilderGeneratedItem(ItemCreator.ConstructorType conType, Uri uri, String title, String mimeType, String subtitlePath, ArrayList<Caption> captionList, ContentAttributeType contentAttr, WebContentBuilder.DeliveryMode deliveryMode, String artist, String albumTitle, String genre, Date date, long duration) {
            this.mConType = ItemCreator.ConstructorType.UNKNOWN;
            this.mDeliveryMode = WebContentBuilder.DeliveryMode.UNKNOWN;
            this.mCaptionList = null;
            this.mContentAttribute = null;
            this.mArtist = null;
            this.mAlbumTitle = null;
            this.mGenre = null;
            this.mDate = null;
            this.mDuration = -1L;
            this.mItemFilepath = uri.toString();
            this.mItemMimetype = mimeType;
            this.mItemTitle = title;
            this.mConType = conType;
            this.mDeliveryMode = deliveryMode;
            this.mSubtitlePath = subtitlePath;
            this.mCaptionList = captionList;
            this.mContentAttribute = contentAttr;
            this.mArtist = artist;
            this.mAlbumTitle = albumTitle;
            this.mGenre = genre;
            this.mDate = date;
            this.mDuration = duration;
        }

        @Deprecated
        private BuilderGeneratedItem(ItemCreator.ConstructorType conType, Uri uri, String title, String mimeType, String subtitlePath, ArrayList<Caption> captionList, ContentAttributeType contentAttr, WebContentBuilder.PlayMode playMode) {
            this.mConType = ItemCreator.ConstructorType.UNKNOWN;
            this.mDeliveryMode = WebContentBuilder.DeliveryMode.UNKNOWN;
            this.mCaptionList = null;
            this.mContentAttribute = null;
            this.mArtist = null;
            this.mAlbumTitle = null;
            this.mGenre = null;
            this.mDate = null;
            this.mDuration = -1L;
            this.mItemFilepath = uri.toString();
            this.mItemMimetype = mimeType;
            this.mItemTitle = title;
            this.mConType = conType;
            this.mSubtitlePath = subtitlePath;
            this.mCaptionList = captionList;
            this.mContentAttribute = contentAttr;
            if (playMode == WebContentBuilder.PlayMode.REDIRECT) {
                this.mDeliveryMode = WebContentBuilder.DeliveryMode.REDIRECT;
            } else if (playMode == WebContentBuilder.PlayMode.RELAY) {
                this.mDeliveryMode = WebContentBuilder.DeliveryMode.RELAY;
            } else {
                this.mDeliveryMode = WebContentBuilder.DeliveryMode.UNKNOWN;
            }
        }

        @Override // com.samsung.android.allshare.Item
        public Date getDate() {
            return this.mDate;
        }

        @Override // com.samsung.android.allshare.Item
        public String getTitle() {
            return this.mItemTitle;
        }

        @Override // com.samsung.android.allshare.Item
        public MediaType getType() {
            return Item.convertItemTypeFromMimeType(this.mItemMimetype);
        }

        @Override // com.samsung.android.allshare.Item
        public Uri getURI() {
            String str = this.mItemFilepath;
            if (str == null) {
                return null;
            }
            try {
                Uri uri = Uri.parse(str);
                String scheme = uri.getScheme();
                if (scheme != null && !scheme.isEmpty()) {
                    return uri;
                }
                return Uri.fromFile(new File(this.mItemFilepath));
            } catch (Exception e) {
                return null;
            }
        }

        @Override // com.sec.android.allshare.iface.IBundleHolder
        public Bundle getBundle() {
            Bundle bundle = new Bundle();
            bundle.putString(AllShareKey.BUNDLE_STRING_ITEM_TYPE, getType().enumToString());
            bundle.putString(AllShareKey.BUNDLE_STRING_ITEM_TITLE, this.mItemTitle);
            bundle.putString(AllShareKey.BUNDLE_STRING_FILEPATH, this.mItemFilepath);
            bundle.putParcelable(AllShareKey.BUNDLE_PARCELABLE_ITEM_URI, getURI());
            bundle.putString(AllShareKey.BUNDLE_STRING_ITEM_MIMETYPE, this.mItemMimetype);
            bundle.putString(AllShareKey.BUNDLE_STRING_ITEM_CONSTRUCTOR_KEY, this.mConType.enumToString());
            bundle.putString(AllShareKey.BUNDLE_STRING_WEB_PLAY_MODE, this.mDeliveryMode.enumToString());
            bundle.putString(AllShareKey.BUNDLE_STRING_ITEM_SUBTITLE_PATH, this.mSubtitlePath);
            bundle.putParcelableArrayList(AllShareKey.BUNDLE_PARCELABLE_ITEM_CAPTION_LIST, this.mCaptionList);
            bundle.putString(AllShareKey.BUNDLE_STRING_CONTENT_ATTRIBUTE, this.mContentAttribute.enumToString());
            bundle.putString(AllShareKey.BUNDLE_STRING_ITEM_ARTIST, this.mArtist);
            bundle.putString(AllShareKey.BUNDLE_STRING_ITEM_ALBUM_TITLE, this.mAlbumTitle);
            bundle.putString(AllShareKey.BUNDLE_STRING_ITEM_GENRE, this.mGenre);
            Date date = this.mDate;
            bundle.putLong(AllShareKey.BUNDLE_DATE_ITEM_DATE, date != null ? date.getTime() : 0L);
            bundle.putLong(AllShareKey.BUNDLE_LONG_ITEM_DURATION, this.mDuration);
            return bundle;
        }

        @Override // com.samsung.android.allshare.Item
        public String getAlbumTitle() {
            String str = this.mAlbumTitle;
            return str == null ? "" : str;
        }

        @Override // com.samsung.android.allshare.Item
        public String getArtist() {
            String str = this.mArtist;
            return str == null ? "" : str;
        }

        @Override // com.samsung.android.allshare.Item
        public String getGenre() {
            String str = this.mGenre;
            return str == null ? "" : str;
        }

        @Override // com.samsung.android.allshare.Item
        public long getDuration() {
            return this.mDuration;
        }

        @Override // com.samsung.android.allshare.Item
        public boolean isRootFolder() {
            return false;
        }

        @Override // com.samsung.android.allshare.Item
        public Uri getThumbnail() {
            return null;
        }

        @Override // com.samsung.android.allshare.Item
        public String getResolution() {
            return "";
        }

        @Override // com.samsung.android.allshare.Item
        public Location getLocation() {
            return null;
        }

        @Override // com.samsung.android.allshare.Item
        public Uri getSubtitle() {
            String str = this.mSubtitlePath;
            if (str == null) {
                return null;
            }
            return Uri.parse(str);
        }

        @Override // com.samsung.android.allshare.Item
        public ArrayList<Caption> getCaptionList() {
            ArrayList<Caption> arrayList = this.mCaptionList;
            if (arrayList == null) {
                return new ArrayList<>();
            }
            return arrayList;
        }

        @Override // com.samsung.android.allshare.Item
        public long getFileSize() {
            return -1L;
        }

        @Override // com.samsung.android.allshare.Item
        public String getMimetype() {
            return this.mItemMimetype;
        }

        @Override // com.samsung.android.allshare.Item
        public String getExtension() {
            return "";
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.mItemFilepath);
            dest.writeString(this.mItemMimetype);
            dest.writeString(this.mItemTitle);
            dest.writeString(this.mConType.enumToString());
            dest.writeString(this.mDeliveryMode.enumToString());
            dest.writeString(this.mSubtitlePath);
        }

        private void readFromParcel(Parcel src) {
            this.mItemFilepath = src.readString();
            this.mItemMimetype = src.readString();
            this.mItemTitle = src.readString();
            String conType = src.readString();
            String deliveryMode = src.readString();
            this.mSubtitlePath = src.readString();
            this.mConType = ItemCreator.ConstructorType.stringToEnum(conType);
            this.mDeliveryMode = WebContentBuilder.DeliveryMode.stringToEnum(deliveryMode);
        }

        private BuilderGeneratedItem(Parcel src) {
            this.mConType = ItemCreator.ConstructorType.UNKNOWN;
            this.mDeliveryMode = WebContentBuilder.DeliveryMode.UNKNOWN;
            this.mCaptionList = null;
            this.mContentAttribute = null;
            this.mArtist = null;
            this.mAlbumTitle = null;
            this.mGenre = null;
            this.mDate = null;
            this.mDuration = -1L;
            readFromParcel(src);
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || !(o instanceof BuilderGeneratedItem)) {
                return false;
            }
            BuilderGeneratedItem item = (BuilderGeneratedItem) o;
            if (getURI() == null) {
                if (item.getURI() == null) {
                    return true;
                }
                return false;
            }
            return getURI().equals(item.getURI());
        }

        public int hashCode() {
            if (getURI() != null) {
                return getURI().hashCode();
            }
            return "".hashCode();
        }

        @Override // com.samsung.android.allshare.Item
        public ContentBuildType getContentBuildType() {
            switch (AnonymousClass1.$SwitchMap$com$samsung$android$allshare$ItemCreator$ConstructorType[this.mConType.ordinal()]) {
                case 1:
                    return ContentBuildType.LOCAL;
                case 2:
                    return ContentBuildType.PROVIDER;
                case 3:
                    return ContentBuildType.WEB;
                case 4:
                    return ContentBuildType.UNKNOWN;
                default:
                    return ContentBuildType.UNKNOWN;
            }
        }

        @Override // com.samsung.android.allshare.Item
        public WebContentBuilder.DeliveryMode getWebContentDeliveryMode() {
            return this.mDeliveryMode;
        }

        @Override // com.samsung.android.allshare.Item
        @Deprecated
        public WebContentBuilder.PlayMode getWebContentPlayMode() {
            if (this.mDeliveryMode == WebContentBuilder.DeliveryMode.REDIRECT) {
                return WebContentBuilder.PlayMode.REDIRECT;
            }
            if (this.mDeliveryMode == WebContentBuilder.DeliveryMode.RELAY) {
                return WebContentBuilder.PlayMode.RELAY;
            }
            return WebContentBuilder.PlayMode.UNKNOWN;
        }

        @Override // com.samsung.android.allshare.Item
        public ArrayList<Subtitle> getSubtitleList() {
            return new ArrayList<>();
        }

        @Override // com.samsung.android.allshare.Item
        public SeekMode getSeekMode() {
            return SeekMode.BYTE;
        }

        @Override // com.samsung.android.allshare.Item
        public int getBitrate() {
            return -1;
        }

        @Override // com.samsung.android.allshare.Item
        public ArrayList<Resource> getResourceList() {
            return new ArrayList<>();
        }

        @Override // com.samsung.android.allshare.Item
        public String getChannelNr() {
            return "";
        }
    }

    /* renamed from: com.samsung.android.allshare.Item$1, reason: invalid class name */
    /* loaded from: classes5.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$samsung$android$allshare$Item$MediaType;
        static final /* synthetic */ int[] $SwitchMap$com$samsung$android$allshare$ItemCreator$ConstructorType;

        static {
            int[] iArr = new int[ItemCreator.ConstructorType.values().length];
            $SwitchMap$com$samsung$android$allshare$ItemCreator$ConstructorType = iArr;
            try {
                iArr[ItemCreator.ConstructorType.LOCAL_CONTENT.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$samsung$android$allshare$ItemCreator$ConstructorType[ItemCreator.ConstructorType.MEDIA_SERVER.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$samsung$android$allshare$ItemCreator$ConstructorType[ItemCreator.ConstructorType.WEB_CONTENT.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$samsung$android$allshare$ItemCreator$ConstructorType[ItemCreator.ConstructorType.UNKNOWN.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            int[] iArr2 = new int[MediaType.values().length];
            $SwitchMap$com$samsung$android$allshare$Item$MediaType = iArr2;
            try {
                iArr2[MediaType.ITEM_AUDIO.ordinal()] = 1;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$samsung$android$allshare$Item$MediaType[MediaType.ITEM_IMAGE.ordinal()] = 2;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$samsung$android$allshare$Item$MediaType[MediaType.ITEM_VIDEO.ordinal()] = 3;
            } catch (NoSuchFieldError e7) {
            }
        }
    }

    /* loaded from: classes5.dex */
    public static class WebContentBuilder {
        private String mMimetype;
        private Uri mUri;
        private String mTitle = null;
        private DeliveryMode mDeliveryMode = null;
        private String mSubtitlePath = null;
        private ArrayList<Caption> mCaptionList = new ArrayList<>();
        private ContentAttributeType mContentAttribute = ContentAttributeType.CONTENT_UNKNOWN;
        private String mArtist = null;
        private String mAlbumTitle = null;
        private String mGenre = null;
        private Date mDate = null;
        private long mDuration = -1;

        /* loaded from: classes5.dex */
        public enum DeliveryMode {
            RELAY("RELAY"),
            REDIRECT("REDIRECT"),
            UNKNOWN("UNKNOWN");

            private final String enumString;

            DeliveryMode(String enumStr) {
                this.enumString = enumStr;
            }

            public String enumToString() {
                return this.enumString;
            }

            public static DeliveryMode stringToEnum(String enumStr) {
                if (enumStr == null) {
                    return UNKNOWN;
                }
                if (enumStr.equals("REDIRECT")) {
                    return REDIRECT;
                }
                if (enumStr.equals("RELAY")) {
                    return RELAY;
                }
                if (enumStr.equals("UNKNOWN")) {
                    return UNKNOWN;
                }
                return UNKNOWN;
            }
        }

        @Deprecated
        /* loaded from: classes5.dex */
        public enum PlayMode {
            RELAY,
            REDIRECT,
            UNKNOWN;

            public static PlayMode stringToEnum(String enumStr) {
                if (enumStr == null) {
                    return UNKNOWN;
                }
                if (enumStr.equals("REDIRECT")) {
                    return REDIRECT;
                }
                if (enumStr.equals("RELAY")) {
                    return RELAY;
                }
                if (enumStr.equals("UNKNOWN")) {
                    return UNKNOWN;
                }
                return UNKNOWN;
            }
        }

        public WebContentBuilder(Uri uri, String mimeType) {
            this.mUri = null;
            this.mMimetype = null;
            this.mUri = uri;
            this.mMimetype = mimeType;
        }

        public WebContentBuilder setTitle(String title) {
            this.mTitle = title;
            return this;
        }

        public WebContentBuilder setSubtitle(String filePath) {
            this.mSubtitlePath = filePath;
            return this;
        }

        public WebContentBuilder setCaptionList(ArrayList<Caption> captionList) {
            this.mCaptionList = captionList;
            return this;
        }

        public WebContentBuilder setContentAttribute(ContentAttributeType contentAttribute) {
            this.mContentAttribute = contentAttribute;
            return this;
        }

        public WebContentBuilder setDeliveryMode(DeliveryMode deliverymode) {
            this.mDeliveryMode = deliverymode;
            return this;
        }

        @Deprecated
        public WebContentBuilder setPlayMode(PlayMode playmode) {
            if (playmode == PlayMode.REDIRECT) {
                this.mDeliveryMode = DeliveryMode.REDIRECT;
            } else if (playmode == PlayMode.RELAY) {
                this.mDeliveryMode = DeliveryMode.RELAY;
            } else {
                this.mDeliveryMode = DeliveryMode.UNKNOWN;
            }
            return this;
        }

        public Item build() {
            if (this.mUri != null && this.mMimetype != null) {
                if (!checkSubtitlePathValid(this.mSubtitlePath)) {
                    this.mSubtitlePath = null;
                }
                if (this.mMimetype.equals(ContentType.VIDEO_UNSPECIFIED)) {
                    this.mMimetype = "video/mp4";
                }
                DLog.i_api("Item", "item build mime : " + this.mMimetype + " item build uri: " + this.mUri);
                if (this.mDeliveryMode == null) {
                    this.mDeliveryMode = DeliveryMode.UNKNOWN;
                }
                String scheme = this.mUri.getScheme();
                if (scheme != null && !scheme.contains("content")) {
                    if (!scheme.contains("file")) {
                        MediaType type = Item.convertItemTypeFromMimeType(this.mMimetype);
                        switch (AnonymousClass1.$SwitchMap$com$samsung$android$allshare$Item$MediaType[type.ordinal()]) {
                            case 1:
                            case 2:
                            case 3:
                                return new BuilderGeneratedItem(ItemCreator.ConstructorType.WEB_CONTENT, this.mUri, this.mTitle, this.mMimetype, this.mSubtitlePath, this.mCaptionList, this.mContentAttribute, this.mDeliveryMode, this.mArtist, this.mAlbumTitle, this.mGenre, this.mDate, this.mDuration);
                            default:
                                return null;
                        }
                    }
                }
                DLog.e_api("Item", "build error! scheme == null || scheme.contains(content) || scheme.contains(file)");
                return null;
            }
            DLog.e_api("Item", "build error! mUri == null || mMimetype == null");
            return null;
        }

        public WebContentBuilder setArtist(String artist) {
            this.mArtist = artist;
            return this;
        }

        public WebContentBuilder setAlbumTitle(String albumTitle) {
            this.mAlbumTitle = albumTitle;
            return this;
        }

        public WebContentBuilder setGenre(String genre) {
            this.mGenre = genre;
            return this;
        }

        public WebContentBuilder setDate(Date date) {
            this.mDate = date;
            return this;
        }

        public WebContentBuilder setDuration(long duration) {
            this.mDuration = duration;
            return this;
        }

        private boolean checkSubtitlePathValid(String subtitleFilePath) {
            String absoluteFilePath;
            if (subtitleFilePath == null || subtitleFilePath.length() == 0) {
                return false;
            }
            if (subtitleFilePath.startsWith("file:")) {
                absoluteFilePath = subtitleFilePath.substring("file://".length());
            } else {
                absoluteFilePath = subtitleFilePath;
            }
            if (absoluteFilePath.startsWith("/data/data")) {
                return false;
            }
            File file = new File(absoluteFilePath);
            if (!file.exists()) {
                return false;
            }
            return true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static MediaType convertItemTypeFromMimeType(String type) {
        if (type == null) {
            return MediaType.ITEM_UNKNOWN;
        }
        StringTokenizer st = new StringTokenizer(type, "/");
        if (st.hasMoreTokens()) {
            String type2 = st.nextToken();
            if (type2.equals("video")) {
                return MediaType.ITEM_VIDEO;
            }
            if (type2.equals("audio")) {
                return MediaType.ITEM_AUDIO;
            }
            if (!type2.equals("image")) {
                if (type.startsWith("application/x-dtcp1")) {
                    if (!type.contains(BnRConstants.VIDEO_DIR_PATH)) {
                        if (!type.contains("audio/")) {
                            if (type.contains(MessagingMessage.IMAGE_MIME_TYPE_PREFIX)) {
                                return MediaType.ITEM_IMAGE;
                            }
                        } else {
                            return MediaType.ITEM_AUDIO;
                        }
                    } else {
                        return MediaType.ITEM_VIDEO;
                    }
                }
                return MediaType.ITEM_UNKNOWN;
            }
            return MediaType.ITEM_IMAGE;
        }
        return MediaType.ITEM_UNKNOWN;
    }

    public String toString() {
        return "Title[" + getTitle() + "] Uri[" + getURI() + NavigationBarInflaterView.SIZE_MOD_END;
    }
}