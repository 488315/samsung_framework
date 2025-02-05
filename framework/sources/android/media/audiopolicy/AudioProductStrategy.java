package android.media.audiopolicy;

import android.annotation.SystemApi;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.AudioAttributes;
import android.media.AudioSystem;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@SystemApi
/* loaded from: classes2.dex */
public final class AudioProductStrategy implements Parcelable {
    private static final int AUDIO_FLAGS_AFFECT_STRATEGY_SELECTION = 13;
    public static final int DEFAULT_GROUP = -1;
    private static final String TAG = "AudioProductStrategy";
    private static List<AudioProductStrategy> sAudioProductStrategies;
    private final AudioAttributesGroup[] mAudioAttributesGroups;
    private int mId;
    private final String mName;
    private static final Object sLock = new Object();
    public static final Parcelable.Creator<AudioProductStrategy> CREATOR = new Parcelable.Creator<AudioProductStrategy>() { // from class: android.media.audiopolicy.AudioProductStrategy.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioProductStrategy createFromParcel(Parcel in) {
            String name = in.readString();
            int id = in.readInt();
            int nbAttributesGroups = in.readInt();
            AudioAttributesGroup[] aag = new AudioAttributesGroup[nbAttributesGroups];
            for (int index = 0; index < nbAttributesGroups; index++) {
                aag[index] = AudioAttributesGroup.CREATOR.createFromParcel(in);
            }
            return new AudioProductStrategy(name, id, aag);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioProductStrategy[] newArray(int size) {
            return new AudioProductStrategy[size];
        }
    };
    private static final AudioAttributes DEFAULT_ATTRIBUTES = new AudioAttributes.Builder().build();

    private static native int native_list_audio_product_strategies(ArrayList<AudioProductStrategy> arrayList);

    public static List<AudioProductStrategy> getAudioProductStrategies() {
        if (sAudioProductStrategies == null) {
            synchronized (sLock) {
                if (sAudioProductStrategies == null) {
                    sAudioProductStrategies = initializeAudioProductStrategies();
                }
            }
        }
        return sAudioProductStrategies;
    }

    public static AudioProductStrategy getAudioProductStrategyWithId(int id) {
        synchronized (sLock) {
            if (sAudioProductStrategies == null) {
                sAudioProductStrategies = initializeAudioProductStrategies();
            }
            for (AudioProductStrategy strategy : sAudioProductStrategies) {
                if (strategy.getId() == id) {
                    return strategy;
                }
            }
            return null;
        }
    }

    @SystemApi
    public static AudioProductStrategy createInvalidAudioProductStrategy(int id) {
        return new AudioProductStrategy("dummy strategy", id, new AudioAttributesGroup[0]);
    }

    public static AudioAttributes getAudioAttributesForStrategyWithLegacyStreamType(int streamType) {
        for (AudioProductStrategy productStrategy : getAudioProductStrategies()) {
            AudioAttributes aa = productStrategy.getAudioAttributesForLegacyStreamType(streamType);
            if (aa != null) {
                return aa;
            }
        }
        return DEFAULT_ATTRIBUTES;
    }

    public static int getLegacyStreamTypeForStrategyWithAudioAttributes(AudioAttributes audioAttributes) {
        Objects.requireNonNull(audioAttributes, "AudioAttributes must not be null");
        for (AudioProductStrategy productStrategy : getAudioProductStrategies()) {
            if (productStrategy.supportsAudioAttributes(audioAttributes)) {
                int streamType = productStrategy.getLegacyStreamTypeForAudioAttributes(audioAttributes);
                if (streamType == -1) {
                    Log.w(TAG, "Attributes " + audioAttributes + " supported by strategy " + productStrategy.getId() + " have no associated stream type, therefore falling back to STREAM_MUSIC");
                    return 3;
                }
                if (streamType < AudioSystem.getNumStreamTypes()) {
                    return streamType;
                }
            }
        }
        return 3;
    }

    public static int getVolumeGroupIdForAudioAttributes(AudioAttributes attributes, boolean fallbackOnDefault) {
        Objects.requireNonNull(attributes, "attributes must not be null");
        int volumeGroupId = getVolumeGroupIdForAudioAttributesInt(attributes);
        if (volumeGroupId != -1) {
            return volumeGroupId;
        }
        if (!fallbackOnDefault) {
            return -1;
        }
        return getVolumeGroupIdForAudioAttributesInt(getDefaultAttributes());
    }

    private static List<AudioProductStrategy> initializeAudioProductStrategies() {
        ArrayList<AudioProductStrategy> apsList = new ArrayList<>();
        int status = native_list_audio_product_strategies(apsList);
        if (status != 0) {
            Log.w(TAG, ": initializeAudioProductStrategies failed");
        }
        return apsList;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AudioProductStrategy thatStrategy = (AudioProductStrategy) o;
        if (this.mId == thatStrategy.mId && Objects.equals(this.mName, thatStrategy.mName) && Arrays.equals(this.mAudioAttributesGroups, thatStrategy.mAudioAttributesGroups)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mId), this.mName, Integer.valueOf(Arrays.hashCode(this.mAudioAttributesGroups)));
    }

    private AudioProductStrategy(String name, int id, AudioAttributesGroup[] aag) {
        Objects.requireNonNull(name, "name must not be null");
        Objects.requireNonNull(aag, "AudioAttributesGroups must not be null");
        this.mName = name;
        this.mId = id;
        this.mAudioAttributesGroups = aag;
    }

    @SystemApi
    public int getId() {
        return this.mId;
    }

    @SystemApi
    public String getName() {
        return this.mName;
    }

    @SystemApi
    public AudioAttributes getAudioAttributes() {
        return this.mAudioAttributesGroups.length == 0 ? DEFAULT_ATTRIBUTES : this.mAudioAttributesGroups[0].getAudioAttributes();
    }

    public AudioAttributes getAudioAttributesForLegacyStreamType(int streamType) {
        for (AudioAttributesGroup aag : this.mAudioAttributesGroups) {
            if (aag.supportsStreamType(streamType)) {
                return aag.getAudioAttributes();
            }
        }
        return null;
    }

    public int getLegacyStreamTypeForAudioAttributes(AudioAttributes aa) {
        Objects.requireNonNull(aa, "AudioAttributes must not be null");
        for (AudioAttributesGroup aag : this.mAudioAttributesGroups) {
            if (aag.supportsAttributes(aa)) {
                return aag.getStreamType();
            }
        }
        return -1;
    }

    @SystemApi
    public boolean supportsAudioAttributes(AudioAttributes aa) {
        Objects.requireNonNull(aa, "AudioAttributes must not be null");
        for (AudioAttributesGroup aag : this.mAudioAttributesGroups) {
            if (aag.supportsAttributes(aa)) {
                return true;
            }
        }
        return false;
    }

    public int getVolumeGroupIdForLegacyStreamType(int streamType) {
        for (AudioAttributesGroup aag : this.mAudioAttributesGroups) {
            if (aag.supportsStreamType(streamType)) {
                return aag.getVolumeGroupId();
            }
        }
        return -1;
    }

    public int getVolumeGroupIdForAudioAttributes(AudioAttributes aa) {
        Objects.requireNonNull(aa, "AudioAttributes must not be null");
        for (AudioAttributesGroup aag : this.mAudioAttributesGroups) {
            if (aag.supportsAttributes(aa)) {
                return aag.getVolumeGroupId();
            }
        }
        return -1;
    }

    private static int getVolumeGroupIdForAudioAttributesInt(AudioAttributes attributes) {
        Objects.requireNonNull(attributes, "attributes must not be null");
        for (AudioProductStrategy productStrategy : getAudioProductStrategies()) {
            int volumeGroupId = productStrategy.getVolumeGroupIdForAudioAttributes(attributes);
            if (volumeGroupId != -1) {
                return volumeGroupId;
            }
        }
        return -1;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mName);
        dest.writeInt(this.mId);
        dest.writeInt(this.mAudioAttributesGroups.length);
        for (AudioAttributesGroup aag : this.mAudioAttributesGroups) {
            aag.writeToParcel(dest, flags);
        }
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("\n Name: ");
        s.append(this.mName);
        s.append(" Id: ");
        s.append(Integer.toString(this.mId));
        for (AudioAttributesGroup aag : this.mAudioAttributesGroups) {
            s.append(aag.toString());
        }
        return s.toString();
    }

    public static AudioAttributes getDefaultAttributes() {
        return DEFAULT_ATTRIBUTES;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean attributesMatches(AudioAttributes refAttr, AudioAttributes attr) {
        Objects.requireNonNull(refAttr, "reference AudioAttributes must not be null");
        Objects.requireNonNull(attr, "requester's AudioAttributes must not be null");
        String refFormattedTags = TextUtils.join(NavigationBarInflaterView.GRAVITY_SEPARATOR, refAttr.getTags());
        String cliFormattedTags = TextUtils.join(NavigationBarInflaterView.GRAVITY_SEPARATOR, attr.getTags());
        if (refAttr.equals(DEFAULT_ATTRIBUTES)) {
            return false;
        }
        if (refAttr.getSystemUsage() != 0 && attr.getSystemUsage() != refAttr.getSystemUsage()) {
            return false;
        }
        if (refAttr.getContentType() != 0 && attr.getContentType() != refAttr.getContentType()) {
            return false;
        }
        if ((refAttr.getAllFlags() & 13) == 0 || ((attr.getAllFlags() & 13) != 0 && (attr.getAllFlags() & refAttr.getAllFlags()) == refAttr.getAllFlags())) {
            return refFormattedTags.length() == 0 || refFormattedTags.equals(cliFormattedTags);
        }
        return false;
    }

    private static final class AudioAttributesGroup implements Parcelable {
        public static final Parcelable.Creator<AudioAttributesGroup> CREATOR = new Parcelable.Creator<AudioAttributesGroup>() { // from class: android.media.audiopolicy.AudioProductStrategy.AudioAttributesGroup.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public AudioAttributesGroup createFromParcel(Parcel in) {
                int volumeGroupId = in.readInt();
                int streamType = in.readInt();
                int nbAttributes = in.readInt();
                AudioAttributes[] aa = new AudioAttributes[nbAttributes];
                for (int index = 0; index < nbAttributes; index++) {
                    aa[index] = AudioAttributes.CREATOR.createFromParcel(in);
                }
                return new AudioAttributesGroup(volumeGroupId, streamType, aa);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public AudioAttributesGroup[] newArray(int size) {
                return new AudioAttributesGroup[size];
            }
        };
        private final AudioAttributes[] mAudioAttributes;
        private int mLegacyStreamType;
        private int mVolumeGroupId;

        AudioAttributesGroup(int volumeGroupId, int streamType, AudioAttributes[] audioAttributes) {
            this.mVolumeGroupId = volumeGroupId;
            this.mLegacyStreamType = streamType;
            this.mAudioAttributes = audioAttributes;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            AudioAttributesGroup thatAag = (AudioAttributesGroup) o;
            if (this.mVolumeGroupId == thatAag.mVolumeGroupId && this.mLegacyStreamType == thatAag.mLegacyStreamType && Arrays.equals(this.mAudioAttributes, thatAag.mAudioAttributes)) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(Integer.valueOf(this.mVolumeGroupId), Integer.valueOf(this.mLegacyStreamType), Integer.valueOf(Arrays.hashCode(this.mAudioAttributes)));
        }

        public int getStreamType() {
            return this.mLegacyStreamType;
        }

        public int getVolumeGroupId() {
            return this.mVolumeGroupId;
        }

        public AudioAttributes getAudioAttributes() {
            return this.mAudioAttributes.length == 0 ? AudioProductStrategy.DEFAULT_ATTRIBUTES : this.mAudioAttributes[0];
        }

        public boolean supportsAttributes(AudioAttributes attributes) {
            for (AudioAttributes refAa : this.mAudioAttributes) {
                if (refAa.equals(attributes) || AudioProductStrategy.attributesMatches(refAa, attributes)) {
                    return true;
                }
            }
            return false;
        }

        public boolean supportsStreamType(int streamType) {
            return this.mLegacyStreamType == streamType;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.mVolumeGroupId);
            dest.writeInt(this.mLegacyStreamType);
            dest.writeInt(this.mAudioAttributes.length);
            for (AudioAttributes attributes : this.mAudioAttributes) {
                attributes.writeToParcel(dest, flags | 1);
            }
        }

        public String toString() {
            StringBuilder s = new StringBuilder();
            s.append("\n    Legacy Stream Type: ");
            s.append(Integer.toString(this.mLegacyStreamType));
            s.append(" Volume Group Id: ");
            s.append(Integer.toString(this.mVolumeGroupId));
            for (AudioAttributes attribute : this.mAudioAttributes) {
                s.append("\n    -");
                s.append(attribute.toString());
            }
            return s.toString();
        }
    }
}
