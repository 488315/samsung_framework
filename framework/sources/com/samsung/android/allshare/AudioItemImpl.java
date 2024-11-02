package com.samsung.android.allshare;

import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.samsung.android.allshare.Item;
import com.sec.android.allshare.iface.IBundleHolder;
import com.sec.android.allshare.iface.message.AllShareKey;
import java.util.ArrayList;
import java.util.Date;

/* loaded from: classes5.dex */
public final class AudioItemImpl extends Item implements IBundleHolder, Parcelable {
    public static final Parcelable.Creator<AudioItemImpl> CREATOR = new Parcelable.Creator<AudioItemImpl>() { // from class: com.samsung.android.allshare.AudioItemImpl.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public AudioItemImpl createFromParcel(Parcel src) {
            return new AudioItemImpl(src);
        }

        @Override // android.os.Parcelable.Creator
        public AudioItemImpl[] newArray(int size) {
            return new AudioItemImpl[size];
        }
    };
    private final ItemImpl mItemImpl;

    /* synthetic */ AudioItemImpl(Parcel parcel, AudioItemImplIA audioItemImplIA) {
        this(parcel);
    }

    public AudioItemImpl(Bundle bundle) {
        this.mItemImpl = new ItemImpl(bundle);
    }

    public Uri getAlbumArt() {
        return (Uri) (this.mItemImpl.getBundle() == null ? null : this.mItemImpl.getBundle().getParcelable(AllShareKey.BUNDLE_PARCELABLE_AUDIO_ITEM_ALBUMART));
    }

    @Override // com.samsung.android.allshare.Item
    public String getAlbumTitle() {
        return this.mItemImpl.getBundle() == null ? "" : this.mItemImpl.getBundle().getString(AllShareKey.BUNDLE_STRING_AUDIO_ITEM_ALBUM_TITLE);
    }

    @Override // com.samsung.android.allshare.Item
    public String getArtist() {
        return this.mItemImpl.getBundle() == null ? "" : this.mItemImpl.getBundle().getString(AllShareKey.BUNDLE_STRING_AUDIO_ITEM_ARTIST);
    }

    @Override // com.samsung.android.allshare.Item
    public long getDuration() {
        if (this.mItemImpl.getBundle() == null) {
            return -1L;
        }
        return this.mItemImpl.getBundle().getLong(AllShareKey.BUNDLE_LONG_AUDIO_ITEM_DURATION);
    }

    @Override // com.samsung.android.allshare.Item
    public String getMimetype() {
        return this.mItemImpl.getBundle() == null ? "" : this.mItemImpl.getBundle().getString(AllShareKey.BUNDLE_STRING_ITEM_MIMETYPE);
    }

    @Override // com.samsung.android.allshare.Item
    public String getExtension() {
        return this.mItemImpl.getBundle() == null ? "" : this.mItemImpl.getBundle().getString(AllShareKey.BUNDLE_STRING_ITEM_EXTENSION);
    }

    @Override // com.samsung.android.allshare.Item
    public Item.MediaType getType() {
        return Item.MediaType.ITEM_AUDIO;
    }

    @Override // com.samsung.android.allshare.Item
    public Date getDate() {
        return this.mItemImpl.getDate();
    }

    @Override // com.samsung.android.allshare.Item
    public long getFileSize() {
        return this.mItemImpl.getFileSize();
    }

    @Override // com.samsung.android.allshare.Item
    public String getTitle() {
        return this.mItemImpl.getTitle();
    }

    @Override // com.samsung.android.allshare.Item
    public Uri getURI() {
        return this.mItemImpl.getURI();
    }

    @Override // com.samsung.android.allshare.Item
    public String getGenre() {
        return this.mItemImpl.getBundle() == null ? "" : this.mItemImpl.getBundle().getString(AllShareKey.BUNDLE_STRING_AUDIO_ITEM_GENRE);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o != null && (o instanceof AudioItemImpl) && hashCode() == o.hashCode()) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        String objID;
        ItemImpl itemImpl = this.mItemImpl;
        if (itemImpl == null || (objID = itemImpl.getObjectID()) == null) {
            return -1;
        }
        return objID.hashCode();
    }

    @Override // com.sec.android.allshare.iface.IBundleHolder
    public Bundle getBundle() {
        return this.mItemImpl.getBundle();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeBundle(getBundle());
    }

    private AudioItemImpl(Parcel src) {
        Bundle bundle = src.readBundle(Bundle.class.getClassLoader());
        this.mItemImpl = new ItemImpl(bundle);
    }

    /* renamed from: com.samsung.android.allshare.AudioItemImpl$1 */
    /* loaded from: classes5.dex */
    class AnonymousClass1 implements Parcelable.Creator<AudioItemImpl> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public AudioItemImpl createFromParcel(Parcel src) {
            return new AudioItemImpl(src);
        }

        @Override // android.os.Parcelable.Creator
        public AudioItemImpl[] newArray(int size) {
            return new AudioItemImpl[size];
        }
    }

    @Override // com.samsung.android.allshare.Item
    public Location getLocation() {
        return null;
    }

    @Override // com.samsung.android.allshare.Item
    public String getResolution() {
        return "";
    }

    @Override // com.samsung.android.allshare.Item
    public Uri getSubtitle() {
        return null;
    }

    @Override // com.samsung.android.allshare.Item
    public Uri getThumbnail() {
        return (Uri) (this.mItemImpl.getBundle() == null ? null : this.mItemImpl.getBundle().getParcelable(AllShareKey.BUNDLE_PARCELABLE_AUDIO_ITEM_ALBUMART));
    }

    @Override // com.samsung.android.allshare.Item
    public boolean isRootFolder() {
        return false;
    }

    @Override // com.samsung.android.allshare.Item
    public Item.ContentBuildType getContentBuildType() {
        ItemImpl itemImpl = this.mItemImpl;
        if (itemImpl == null) {
            return Item.ContentBuildType.UNKNOWN;
        }
        return itemImpl.getContentBuildType();
    }

    @Override // com.samsung.android.allshare.Item
    public Item.WebContentBuilder.DeliveryMode getWebContentDeliveryMode() {
        ItemImpl itemImpl = this.mItemImpl;
        if (itemImpl == null) {
            return Item.WebContentBuilder.DeliveryMode.UNKNOWN;
        }
        return itemImpl.getWebContentDeliveryMode();
    }

    @Override // com.samsung.android.allshare.Item
    @Deprecated
    public Item.WebContentBuilder.PlayMode getWebContentPlayMode() {
        ItemImpl itemImpl = this.mItemImpl;
        if (itemImpl == null) {
            return Item.WebContentBuilder.PlayMode.UNKNOWN;
        }
        return itemImpl.getWebContentPlayMode();
    }

    @Override // com.samsung.android.allshare.Item
    public ArrayList<Subtitle> getSubtitleList() {
        ItemImpl itemImpl = this.mItemImpl;
        if (itemImpl == null) {
            return new ArrayList<>();
        }
        return itemImpl.getSubtitleList();
    }

    @Override // com.samsung.android.allshare.Item
    public Item.SeekMode getSeekMode() {
        ItemImpl itemImpl = this.mItemImpl;
        if (itemImpl == null) {
            return Item.SeekMode.UNKNOWN;
        }
        Bundle bundle = itemImpl.getBundle();
        if (bundle == null) {
            return Item.SeekMode.UNKNOWN;
        }
        String seekModeStr = bundle.getString(AllShareKey.BUNDLE_STRING_AUDIO_ITEM_SEEKMODE);
        if (seekModeStr == null) {
            return Item.SeekMode.UNKNOWN;
        }
        Item.SeekMode seekMode = Item.SeekMode.stringToEnum(seekModeStr);
        return seekMode;
    }

    @Override // com.samsung.android.allshare.Item
    public int getBitrate() {
        Bundle bundle;
        ItemImpl itemImpl = this.mItemImpl;
        if (itemImpl == null || (bundle = itemImpl.getBundle()) == null) {
            return -1;
        }
        return bundle.getInt(AllShareKey.BUNDLE_STRING_AUDIO_ITEM_BITRATE);
    }

    @Override // com.samsung.android.allshare.Item
    public ArrayList<Item.Resource> getResourceList() {
        ItemImpl itemImpl = this.mItemImpl;
        if (itemImpl == null) {
            return new ArrayList<>();
        }
        return itemImpl.getResourceList();
    }

    @Override // com.samsung.android.allshare.Item
    public String getChannelNr() {
        ItemImpl itemImpl = this.mItemImpl;
        if (itemImpl == null) {
            return "";
        }
        return itemImpl.getChannelNr();
    }

    @Override // com.samsung.android.allshare.Item
    public ArrayList<Caption> getCaptionList() {
        return null;
    }
}
