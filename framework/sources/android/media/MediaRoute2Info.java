package android.media;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.android.internal.util.Preconditions;
import java.io.PrintWriter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

/* loaded from: classes2.dex */
public final class MediaRoute2Info implements Parcelable {
    public static final int CONNECTION_STATE_CONNECTED = 2;
    public static final int CONNECTION_STATE_CONNECTING = 1;
    public static final int CONNECTION_STATE_DISCONNECTED = 0;
    public static final Parcelable.Creator<MediaRoute2Info> CREATOR = new Parcelable.Creator<MediaRoute2Info>() { // from class: android.media.MediaRoute2Info.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MediaRoute2Info createFromParcel(Parcel in) {
            return new MediaRoute2Info(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MediaRoute2Info[] newArray(int size) {
            return new MediaRoute2Info[size];
        }
    };
    public static final String FEATURE_LIVE_AUDIO = "android.media.route.feature.LIVE_AUDIO";
    public static final String FEATURE_LIVE_VIDEO = "android.media.route.feature.LIVE_VIDEO";
    public static final String FEATURE_LOCAL_PLAYBACK = "android.media.route.feature.LOCAL_PLAYBACK";
    public static final String FEATURE_REMOTE_AUDIO_PLAYBACK = "android.media.route.feature.REMOTE_AUDIO_PLAYBACK";
    public static final String FEATURE_REMOTE_GROUP_PLAYBACK = "android.media.route.feature.REMOTE_GROUP_PLAYBACK";
    public static final String FEATURE_REMOTE_PLAYBACK = "android.media.route.feature.REMOTE_PLAYBACK";
    public static final String FEATURE_REMOTE_VIDEO_PLAYBACK = "android.media.route.feature.REMOTE_VIDEO_PLAYBACK";
    public static final int PLAYBACK_VOLUME_FIXED = 0;
    public static final int PLAYBACK_VOLUME_VARIABLE = 1;
    public static final String ROUTE_ID_DEFAULT = "DEFAULT_ROUTE";
    public static final String ROUTE_ID_DEVICE = "DEVICE_ROUTE";
    public static final int SUITABILITY_STATUS_NOT_SUITABLE_FOR_TRANSFER = 2;
    public static final int SUITABILITY_STATUS_SUITABLE_FOR_DEFAULT_TRANSFER = 0;
    public static final int SUITABILITY_STATUS_SUITABLE_FOR_MANUAL_TRANSFER = 1;
    public static final int TYPE_BLE_HEADSET = 26;
    public static final int TYPE_BLUETOOTH_A2DP = 8;
    public static final int TYPE_BUILTIN_SPEAKER = 2;
    public static final int TYPE_DOCK = 13;
    public static final int TYPE_GROUP = 2000;
    public static final int TYPE_HDMI = 9;
    public static final int TYPE_HDMI_ARC = 10;
    public static final int TYPE_HDMI_EARC = 29;
    public static final int TYPE_HEARING_AID = 23;
    public static final int TYPE_REMOTE_AUDIO_VIDEO_RECEIVER = 1003;
    public static final int TYPE_REMOTE_CAR = 1008;
    public static final int TYPE_REMOTE_COMPUTER = 1006;
    public static final int TYPE_REMOTE_GAME_CONSOLE = 1007;
    public static final int TYPE_REMOTE_SMARTPHONE = 1010;
    public static final int TYPE_REMOTE_SMARTWATCH = 1009;
    public static final int TYPE_REMOTE_SPEAKER = 1002;
    public static final int TYPE_REMOTE_SUBMIX = 25;
    public static final int TYPE_REMOTE_TABLET = 1004;
    public static final int TYPE_REMOTE_TABLET_DOCKED = 1005;
    public static final int TYPE_REMOTE_TV = 1001;
    public static final int TYPE_UNKNOWN = 0;
    public static final int TYPE_USB_ACCESSORY = 12;
    public static final int TYPE_USB_DEVICE = 11;
    public static final int TYPE_USB_HEADSET = 22;
    public static final int TYPE_WIRED_HEADPHONES = 4;
    public static final int TYPE_WIRED_HEADSET = 3;
    private final String mAddress;
    private final Set<String> mAllowedPackages;
    private final String mClientPackageName;
    private final int mConnectionState;
    private final Set<String> mDeduplicationIds;
    private final CharSequence mDescription;
    private final Bundle mExtras;
    private final List<String> mFeatures;
    private final Uri mIconUri;
    private final String mId;
    private final boolean mIsSystem;
    private final boolean mIsVisibilityRestricted;
    private final CharSequence mName;
    private final String mPackageName;
    private final String mProviderId;
    private final int mSuitabilityStatus;
    private final int mType;
    private final int mVolume;
    private final int mVolumeHandling;
    private final int mVolumeMax;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ConnectionState {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface PlaybackVolume {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SuitabilityStatus {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
    }

    MediaRoute2Info(Builder builder) {
        this.mId = builder.mId;
        this.mName = builder.mName;
        this.mFeatures = builder.mFeatures;
        this.mType = builder.mType;
        this.mIsSystem = builder.mIsSystem;
        this.mIconUri = builder.mIconUri;
        this.mDescription = builder.mDescription;
        this.mConnectionState = builder.mConnectionState;
        this.mClientPackageName = builder.mClientPackageName;
        this.mPackageName = builder.mPackageName;
        this.mVolumeHandling = builder.mVolumeHandling;
        this.mVolumeMax = builder.mVolumeMax;
        this.mVolume = builder.mVolume;
        this.mAddress = builder.mAddress;
        this.mDeduplicationIds = builder.mDeduplicationIds;
        this.mExtras = builder.mExtras;
        this.mProviderId = builder.mProviderId;
        this.mIsVisibilityRestricted = builder.mIsVisibilityRestricted;
        this.mAllowedPackages = builder.mAllowedPackages;
        this.mSuitabilityStatus = builder.mSuitabilityStatus;
    }

    MediaRoute2Info(Parcel in) {
        this.mId = in.readString();
        Preconditions.checkArgument(!TextUtils.isEmpty(this.mId));
        this.mName = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(in);
        this.mFeatures = in.createStringArrayList();
        this.mType = in.readInt();
        this.mIsSystem = in.readBoolean();
        this.mIconUri = (Uri) in.readParcelable(null, Uri.class);
        this.mDescription = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(in);
        this.mConnectionState = in.readInt();
        this.mClientPackageName = in.readString();
        this.mPackageName = in.readString();
        this.mVolumeHandling = in.readInt();
        this.mVolumeMax = in.readInt();
        this.mVolume = in.readInt();
        this.mAddress = in.readString();
        this.mDeduplicationIds = Set.of((Object[]) in.readStringArray());
        this.mExtras = in.readBundle();
        this.mProviderId = in.readString();
        this.mIsVisibilityRestricted = in.readBoolean();
        this.mAllowedPackages = Set.of((Object[]) in.createString8Array());
        this.mSuitabilityStatus = in.readInt();
    }

    public String getId() {
        if (!TextUtils.isEmpty(this.mProviderId)) {
            return MediaRouter2Utils.toUniqueId(this.mProviderId, this.mId);
        }
        return this.mId;
    }

    public CharSequence getName() {
        return this.mName;
    }

    public List<String> getFeatures() {
        return this.mFeatures;
    }

    public int getType() {
        return this.mType;
    }

    public boolean isSystemRoute() {
        return this.mIsSystem;
    }

    public Uri getIconUri() {
        return this.mIconUri;
    }

    public CharSequence getDescription() {
        return this.mDescription;
    }

    public int getConnectionState() {
        return this.mConnectionState;
    }

    public String getClientPackageName() {
        return this.mClientPackageName;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public int getVolumeHandling() {
        return this.mVolumeHandling;
    }

    public int getVolumeMax() {
        return this.mVolumeMax;
    }

    public int getVolume() {
        return this.mVolume;
    }

    public String getAddress() {
        return this.mAddress;
    }

    public Set<String> getDeduplicationIds() {
        return this.mDeduplicationIds;
    }

    public Bundle getExtras() {
        if (this.mExtras == null) {
            return null;
        }
        return new Bundle(this.mExtras);
    }

    public String getOriginalId() {
        return this.mId;
    }

    public String getProviderId() {
        return this.mProviderId;
    }

    public boolean hasAnyFeatures(Collection<String> features) {
        Objects.requireNonNull(features, "features must not be null");
        for (String feature : features) {
            if (getFeatures().contains(feature)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasAllFeatures(Collection<String> features) {
        Objects.requireNonNull(features, "features must not be null");
        for (String feature : features) {
            if (!getFeatures().contains(feature)) {
                return false;
            }
        }
        return true;
    }

    public boolean isValid() {
        if (TextUtils.isEmpty(getId()) || TextUtils.isEmpty(getName()) || TextUtils.isEmpty(getProviderId())) {
            return false;
        }
        return true;
    }

    public boolean isVisibleTo(String packageName) {
        return !this.mIsVisibilityRestricted || getPackageName().equals(packageName) || this.mAllowedPackages.contains(packageName);
    }

    public boolean isSystemRouteType() {
        switch (this.mType) {
            case 2:
            case 3:
            case 4:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 22:
            case 23:
            case 26:
            case 29:
                return true;
            default:
                return false;
        }
    }

    public int getSuitabilityStatus() {
        return this.mSuitabilityStatus;
    }

    public void dump(PrintWriter pw, String prefix) {
        pw.println(prefix + "MediaRoute2Info");
        String indent = prefix + "  ";
        pw.println(indent + "mId=" + this.mId);
        pw.println(indent + "mName=" + ((Object) this.mName));
        pw.println(indent + "mFeatures=" + this.mFeatures);
        pw.println(indent + "mType=" + getDeviceTypeString(this.mType));
        pw.println(indent + "mIsSystem=" + this.mIsSystem);
        pw.println(indent + "mIconUri=" + this.mIconUri);
        pw.println(indent + "mDescription=" + ((Object) this.mDescription));
        pw.println(indent + "mConnectionState=" + this.mConnectionState);
        pw.println(indent + "mClientPackageName=" + this.mClientPackageName);
        pw.println(indent + "mPackageName=" + this.mPackageName);
        dumpVolume(pw, indent);
        pw.println(indent + "mAddress=" + this.mAddress);
        pw.println(indent + "mDeduplicationIds=" + this.mDeduplicationIds);
        pw.println(indent + "mExtras=" + this.mExtras);
        pw.println(indent + "mProviderId=" + this.mProviderId);
        pw.println(indent + "mIsVisibilityRestricted=" + this.mIsVisibilityRestricted);
        pw.println(indent + "mAllowedPackages=" + this.mAllowedPackages);
        pw.println(indent + "mSuitabilityStatus=" + this.mSuitabilityStatus);
    }

    private void dumpVolume(PrintWriter pw, String prefix) {
        pw.println(prefix + getVolumeString(this.mVolume, this.mVolumeMax, this.mVolumeHandling));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MediaRoute2Info)) {
            return false;
        }
        MediaRoute2Info other = (MediaRoute2Info) obj;
        return Objects.equals(this.mId, other.mId) && Objects.equals(this.mName, other.mName) && Objects.equals(this.mFeatures, other.mFeatures) && this.mType == other.mType && this.mIsSystem == other.mIsSystem && Objects.equals(this.mIconUri, other.mIconUri) && Objects.equals(this.mDescription, other.mDescription) && this.mConnectionState == other.mConnectionState && Objects.equals(this.mClientPackageName, other.mClientPackageName) && Objects.equals(this.mPackageName, other.mPackageName) && this.mVolumeHandling == other.mVolumeHandling && this.mVolumeMax == other.mVolumeMax && this.mVolume == other.mVolume && Objects.equals(this.mAddress, other.mAddress) && Objects.equals(this.mDeduplicationIds, other.mDeduplicationIds) && Objects.equals(this.mProviderId, other.mProviderId) && this.mIsVisibilityRestricted == other.mIsVisibilityRestricted && Objects.equals(this.mAllowedPackages, other.mAllowedPackages) && this.mSuitabilityStatus == other.mSuitabilityStatus;
    }

    public int hashCode() {
        return Objects.hash(this.mId, this.mName, this.mFeatures, Integer.valueOf(this.mType), Boolean.valueOf(this.mIsSystem), this.mIconUri, this.mDescription, Integer.valueOf(this.mConnectionState), this.mClientPackageName, this.mPackageName, Integer.valueOf(this.mVolumeHandling), Integer.valueOf(this.mVolumeMax), Integer.valueOf(this.mVolume), this.mAddress, this.mDeduplicationIds, this.mProviderId, Boolean.valueOf(this.mIsVisibilityRestricted), this.mAllowedPackages, Integer.valueOf(this.mSuitabilityStatus));
    }

    public String toString() {
        return "MediaRoute2Info{ id=" + getId() + ", name=" + getName() + ", type=" + getDeviceTypeString(getType()) + ", isSystem=" + isSystemRoute() + ", features=" + getFeatures() + ", iconUri=" + getIconUri() + ", description=" + getDescription() + ", connectionState=" + getConnectionState() + ", clientPackageName=" + getClientPackageName() + ", " + getVolumeString(this.mVolume, this.mVolumeMax, this.mVolumeHandling) + ", address=" + getAddress() + ", deduplicationIds=" + String.join(",", getDeduplicationIds()) + ", providerId=" + getProviderId() + ", isVisibilityRestricted=" + this.mIsVisibilityRestricted + ", allowedPackages=" + String.join(",", this.mAllowedPackages) + ", suitabilityStatus=" + this.mSuitabilityStatus + " }";
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mId);
        TextUtils.writeToParcel(this.mName, dest, flags);
        dest.writeStringList(this.mFeatures);
        dest.writeInt(this.mType);
        dest.writeBoolean(this.mIsSystem);
        dest.writeParcelable(this.mIconUri, flags);
        TextUtils.writeToParcel(this.mDescription, dest, flags);
        dest.writeInt(this.mConnectionState);
        dest.writeString(this.mClientPackageName);
        dest.writeString(this.mPackageName);
        dest.writeInt(this.mVolumeHandling);
        dest.writeInt(this.mVolumeMax);
        dest.writeInt(this.mVolume);
        dest.writeString(this.mAddress);
        dest.writeStringArray((String[]) this.mDeduplicationIds.toArray(new String[this.mDeduplicationIds.size()]));
        dest.writeBundle(this.mExtras);
        dest.writeString(this.mProviderId);
        dest.writeBoolean(this.mIsVisibilityRestricted);
        dest.writeString8Array((String[]) this.mAllowedPackages.toArray(new String[0]));
        dest.writeInt(this.mSuitabilityStatus);
    }

    static String getVolumeString(int volume, int maxVolume, int volumeHandling) {
        String volumeHandlingName;
        switch (volumeHandling) {
            case 0:
                volumeHandlingName = "FIXED";
                break;
            case 1:
                volumeHandlingName = "VARIABLE";
                break;
            default:
                volumeHandlingName = "UNKNOWN";
                break;
        }
        return String.format(Locale.US, "volume(current=%d, max=%d, handling=%s(%d))", Integer.valueOf(volume), Integer.valueOf(maxVolume), volumeHandlingName, Integer.valueOf(volumeHandling));
    }

    private static String getDeviceTypeString(int deviceType) {
        switch (deviceType) {
            case 2:
                return "BUILTIN_SPEAKER";
            case 3:
                return "WIRED_HEADSET";
            case 4:
                return "WIRED_HEADPHONES";
            case 8:
                return "BLUETOOTH_A2DP";
            case 9:
                return "HDMI";
            case 10:
                return "HDMI_ARC";
            case 11:
                return "USB_DEVICE";
            case 12:
                return "USB_ACCESSORY";
            case 13:
                return "DOCK";
            case 22:
                return "USB_HEADSET";
            case 23:
                return "HEARING_AID";
            case 29:
                return "HDMI_EARC";
            case 1001:
                return "REMOTE_TV";
            case 1002:
                return "REMOTE_SPEAKER";
            case 1003:
                return "REMOTE_AUDIO_VIDEO_RECEIVER";
            case 1004:
                return "REMOTE_TABLET";
            case 1005:
                return "REMOTE_TABLET_DOCKED";
            case 1006:
                return "REMOTE_COMPUTER";
            case 1007:
                return "REMOTE_GAME_CONSOLE";
            case 1008:
                return "REMOTE_CAR";
            case 1009:
                return "REMOTE_SMARTWATCH";
            case 1010:
                return "REMOTE_SMARTPHONE";
            case 2000:
                return "GROUP";
            default:
                return TextUtils.formatSimple("UNKNOWN(%d)", Integer.valueOf(deviceType));
        }
    }

    public static final class Builder {
        private String mAddress;
        private Set<String> mAllowedPackages;
        private String mClientPackageName;
        private int mConnectionState;
        private Set<String> mDeduplicationIds;
        private CharSequence mDescription;
        private Bundle mExtras;
        private final List<String> mFeatures;
        private Uri mIconUri;
        private final String mId;
        private boolean mIsSystem;
        private boolean mIsVisibilityRestricted;
        private final CharSequence mName;
        private String mPackageName;
        private String mProviderId;
        private int mSuitabilityStatus;
        private int mType;
        private int mVolume;
        private int mVolumeHandling;
        private int mVolumeMax;

        public Builder(String id, CharSequence name) {
            this.mType = 0;
            this.mVolumeHandling = 0;
            if (TextUtils.isEmpty(id)) {
                throw new IllegalArgumentException("id must not be empty");
            }
            if (TextUtils.isEmpty(name)) {
                throw new IllegalArgumentException("name must not be empty");
            }
            this.mId = id;
            this.mName = name;
            this.mFeatures = new ArrayList();
            this.mDeduplicationIds = Set.of();
            this.mAllowedPackages = Set.of();
            this.mSuitabilityStatus = 0;
        }

        public Builder(MediaRoute2Info routeInfo) {
            this(routeInfo.mId, routeInfo);
        }

        public Builder(String id, MediaRoute2Info routeInfo) {
            this.mType = 0;
            this.mVolumeHandling = 0;
            if (TextUtils.isEmpty(id)) {
                throw new IllegalArgumentException("id must not be empty");
            }
            Objects.requireNonNull(routeInfo, "routeInfo must not be null");
            this.mId = id;
            this.mName = routeInfo.mName;
            this.mFeatures = new ArrayList(routeInfo.mFeatures);
            this.mType = routeInfo.mType;
            this.mIsSystem = routeInfo.mIsSystem;
            this.mIconUri = routeInfo.mIconUri;
            this.mDescription = routeInfo.mDescription;
            this.mConnectionState = routeInfo.mConnectionState;
            this.mClientPackageName = routeInfo.mClientPackageName;
            this.mPackageName = routeInfo.mPackageName;
            this.mVolumeHandling = routeInfo.mVolumeHandling;
            this.mVolumeMax = routeInfo.mVolumeMax;
            this.mVolume = routeInfo.mVolume;
            this.mAddress = routeInfo.mAddress;
            this.mDeduplicationIds = Set.copyOf(routeInfo.mDeduplicationIds);
            if (routeInfo.mExtras != null) {
                this.mExtras = new Bundle(routeInfo.mExtras);
            }
            this.mProviderId = routeInfo.mProviderId;
            this.mIsVisibilityRestricted = routeInfo.mIsVisibilityRestricted;
            this.mAllowedPackages = routeInfo.mAllowedPackages;
            this.mSuitabilityStatus = routeInfo.mSuitabilityStatus;
        }

        public Builder addFeature(String feature) {
            if (TextUtils.isEmpty(feature)) {
                throw new IllegalArgumentException("feature must not be null or empty");
            }
            this.mFeatures.add(feature);
            return this;
        }

        public Builder addFeatures(Collection<String> features) {
            Objects.requireNonNull(features, "features must not be null");
            for (String feature : features) {
                addFeature(feature);
            }
            return this;
        }

        public Builder clearFeatures() {
            this.mFeatures.clear();
            return this;
        }

        public Builder setType(int type) {
            this.mType = type;
            return this;
        }

        public Builder setSystemRoute(boolean isSystem) {
            this.mIsSystem = isSystem;
            return this;
        }

        public Builder setIconUri(Uri iconUri) {
            this.mIconUri = iconUri;
            return this;
        }

        public Builder setDescription(CharSequence description) {
            this.mDescription = description;
            return this;
        }

        public Builder setConnectionState(int connectionState) {
            this.mConnectionState = connectionState;
            return this;
        }

        public Builder setClientPackageName(String packageName) {
            this.mClientPackageName = packageName;
            return this;
        }

        public Builder setPackageName(String packageName) {
            this.mPackageName = packageName;
            return this;
        }

        public Builder setVolumeHandling(int volumeHandling) {
            this.mVolumeHandling = volumeHandling;
            return this;
        }

        public Builder setVolumeMax(int volumeMax) {
            this.mVolumeMax = volumeMax;
            return this;
        }

        public Builder setVolume(int volume) {
            this.mVolume = volume;
            return this;
        }

        public Builder setAddress(String address) {
            this.mAddress = address;
            return this;
        }

        public Builder setDeduplicationIds(Set<String> id) {
            this.mDeduplicationIds = Set.copyOf(id);
            return this;
        }

        public Builder setExtras(Bundle extras) {
            if (extras == null) {
                this.mExtras = null;
                return this;
            }
            this.mExtras = new Bundle(extras);
            return this;
        }

        public Builder setProviderId(String providerId) {
            if (TextUtils.isEmpty(providerId)) {
                throw new IllegalArgumentException("providerId must not be null or empty");
            }
            this.mProviderId = providerId;
            return this;
        }

        public Builder setVisibilityPublic() {
            this.mIsVisibilityRestricted = false;
            this.mAllowedPackages = Set.of();
            return this;
        }

        public Builder setVisibilityRestricted(Set<String> allowedPackages) {
            this.mIsVisibilityRestricted = true;
            this.mAllowedPackages = Set.copyOf(allowedPackages);
            return this;
        }

        public Builder setSuitabilityStatus(int suitabilityStatus) {
            this.mSuitabilityStatus = suitabilityStatus;
            return this;
        }

        public MediaRoute2Info build() {
            if (this.mFeatures.isEmpty()) {
                throw new IllegalArgumentException("features must not be empty!");
            }
            return new MediaRoute2Info(this);
        }
    }
}
