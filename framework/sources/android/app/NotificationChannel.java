package android.app;

import android.annotation.SystemApi;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.VibrationEffect;
import android.os.vibrator.persistence.VibrationXmlParser;
import android.os.vibrator.persistence.VibrationXmlSerializer;
import android.provider.MediaStore;
import android.provider.Settings;
import android.service.notification.NotificationListenerService;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.util.Patterns;
import android.util.Slog;
import android.util.proto.ProtoOutputStream;
import com.android.internal.util.Preconditions;
import com.android.internal.util.XmlUtils;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Objects;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

/* loaded from: classes.dex */
public final class NotificationChannel implements Parcelable {
    public static final int ALLOW_BUBBLE_OFF = 0;
    public static final int ALLOW_BUBBLE_ON = 1;
    private static final String ATT_ALLOW_BUBBLE = "allow_bubbles";
    private static final String ATT_BLOCKABLE_SYSTEM = "blockable_system";
    private static final String ATT_CONTENT_TYPE = "content_type";
    private static final String ATT_CONVERSATION_ID = "conv_id";
    private static final String ATT_DELETED = "deleted";
    private static final String ATT_DELETED_TIME_MS = "del_time";
    private static final String ATT_DEMOTE = "dem";
    private static final String ATT_DESC = "desc";
    private static final String ATT_FG_SERVICE_SHOWN = "fgservice";
    private static final String ATT_FLAGS = "flags";
    private static final String ATT_GROUP = "group";
    private static final String ATT_ID = "id";
    private static final String ATT_IMPORTANCE = "importance";
    private static final String ATT_IMP_CONVERSATION = "imp_conv";
    private static final String ATT_LIGHTS = "lights";
    private static final String ATT_LIGHT_COLOR = "light_color";
    private static final String ATT_NAME = "name";
    private static final String ATT_ORIG_IMP = "orig_imp";
    private static final String ATT_PARENT_CHANNEL = "parent";
    private static final String ATT_PRIORITY = "priority";
    private static final String ATT_SHOW_BADGE = "show_badge";
    private static final String ATT_SOUND = "sound";
    private static final String ATT_USAGE = "usage";
    private static final String ATT_USER_LOCKED = "locked";
    private static final String ATT_VIBRATION = "vibration";
    private static final String ATT_VIBRATION_EFFECT = "vibration_effect";
    private static final String ATT_VIBRATION_ENABLED = "vibration_enabled";
    private static final String ATT_VISIBILITY = "visibility";
    public static final String CONVERSATION_CHANNEL_ID_FORMAT = "%1$s : %2$s";
    public static final int DEFAULT_ALLOW_BUBBLE = -1;
    public static final String DEFAULT_CHANNEL_ID = "miscellaneous";
    private static final boolean DEFAULT_DELETED = false;
    private static final long DEFAULT_DELETION_TIME_MS = -1;
    private static final int DEFAULT_IMPORTANCE = -1000;
    private static final int DEFAULT_LIGHT_COLOR = 0;
    private static final boolean DEFAULT_SHOW_BADGE = true;
    private static final int DEFAULT_VISIBILITY = -1000;
    private static final String DELIMITER = ",";
    public static final String EDIT_CONVERSATION = "conversation";
    public static final String EDIT_IMPORTANCE = "importance";
    public static final String EDIT_LAUNCHER = "launcher";
    public static final String EDIT_LOCKED_DEVICE = "locked";
    public static final String EDIT_SOUND = "sound";
    public static final String EDIT_VIBRATION = "vibration";
    public static final String EDIT_ZEN = "zen";
    public static final int MAX_TEXT_LENGTH = 1000;
    public static final int MAX_VIBRATION_LENGTH = 1000;
    public static final String PLACEHOLDER_CONVERSATION_ID = ":placeholder_id";
    private static final String TAG = "NotificationChannel";
    private static final String TAG_CHANNEL = "channel";
    public static final int USER_LOCKED_ALLOW_BUBBLE = 256;
    public static final int USER_LOCKED_APP_CHANGED = 512;
    public static final int USER_LOCKED_IMPORTANCE = 4;
    public static final int USER_LOCKED_LIGHTS = 8;
    public static final int USER_LOCKED_PRIORITY = 1;
    public static final int USER_LOCKED_SHOW_BADGE = 128;

    @SystemApi
    public static final int USER_LOCKED_SOUND = 32;
    public static final int USER_LOCKED_VIBRATION = 16;
    public static final int USER_LOCKED_VISIBILITY = 2;
    private int mAllowBubbles;
    private AudioAttributes mAudioAttributes;
    private boolean mBlockableSystem;
    private boolean mBypassDnd;
    private String mConversationId;
    private boolean mDeleted;
    private long mDeletedTime;
    private boolean mDemoted;
    private String mDesc;
    private String mGroup;
    private String mId;
    private int mImportance;
    private boolean mImportanceLockedByOEM;
    private boolean mImportanceLockedDefaultApp;
    private boolean mImportantConvo;
    private long mLastNotificationUpdateTimeMs;
    private int mLightColor;
    private boolean mLights;
    private int mLockscreenVisibility;
    private String mName;
    private int mOriginalImportance;
    private String mParentId;
    private boolean mShowBadge;
    private Uri mSound;
    private int mSoundMissingReason;
    private boolean mSoundRestored;
    private int mUserLockedFields;
    private boolean mUserVisibleTaskShown;
    private VibrationEffect mVibrationEffect;
    private boolean mVibrationEnabled;
    private long[] mVibrationPattern;
    public static final int[] LOCKABLE_FIELDS = {1, 2, 4, 8, 16, 32, 128, 256};
    public static final Parcelable.Creator<NotificationChannel> CREATOR = new Parcelable.Creator<NotificationChannel>() { // from class: android.app.NotificationChannel.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NotificationChannel createFromParcel(Parcel in) {
            return new NotificationChannel(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NotificationChannel[] newArray(int size) {
            return new NotificationChannel[size];
        }
    };

    public NotificationChannel(String id, CharSequence name, int importance) {
        this.mImportance = -1000;
        this.mOriginalImportance = -1000;
        this.mLockscreenVisibility = -1000;
        this.mSound = Settings.System.DEFAULT_NOTIFICATION_URI;
        this.mSoundRestored = false;
        this.mLightColor = 0;
        this.mShowBadge = true;
        this.mDeleted = false;
        this.mAudioAttributes = Notification.AUDIO_ATTRIBUTES_DEFAULT;
        this.mBlockableSystem = false;
        this.mAllowBubbles = -1;
        this.mParentId = null;
        this.mConversationId = null;
        this.mDemoted = false;
        this.mImportantConvo = false;
        this.mDeletedTime = -1L;
        this.mLastNotificationUpdateTimeMs = 0L;
        this.mSoundMissingReason = 0;
        this.mId = getTrimmedString(id);
        this.mName = name != null ? getTrimmedString(name.toString()) : null;
        this.mImportance = importance;
    }

    protected NotificationChannel(Parcel in) {
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        this.mImportance = -1000;
        this.mOriginalImportance = -1000;
        this.mLockscreenVisibility = -1000;
        this.mSound = Settings.System.DEFAULT_NOTIFICATION_URI;
        this.mSoundRestored = false;
        this.mLightColor = 0;
        this.mShowBadge = true;
        this.mDeleted = false;
        this.mAudioAttributes = Notification.AUDIO_ATTRIBUTES_DEFAULT;
        this.mBlockableSystem = false;
        this.mAllowBubbles = -1;
        this.mParentId = null;
        this.mConversationId = null;
        this.mDemoted = false;
        this.mImportantConvo = false;
        this.mDeletedTime = -1L;
        this.mLastNotificationUpdateTimeMs = 0L;
        this.mSoundMissingReason = 0;
        if (in.readByte() != 0) {
            this.mId = getTrimmedString(in.readString());
        } else {
            this.mId = null;
        }
        if (in.readByte() != 0) {
            this.mName = getTrimmedString(in.readString());
        } else {
            this.mName = null;
        }
        if (in.readByte() != 0) {
            this.mDesc = getTrimmedString(in.readString());
        } else {
            this.mDesc = null;
        }
        this.mImportance = in.readInt();
        if (in.readByte() == 0) {
            z = false;
        } else {
            z = true;
        }
        this.mBypassDnd = z;
        this.mLockscreenVisibility = in.readInt();
        if (in.readByte() != 0) {
            this.mSound = Uri.CREATOR.createFromParcel(in);
            this.mSound = Uri.parse(getTrimmedString(this.mSound.toString()));
        } else {
            this.mSound = null;
        }
        if (in.readByte() == 0) {
            z2 = false;
        } else {
            z2 = true;
        }
        this.mLights = z2;
        this.mVibrationPattern = in.createLongArray();
        if (this.mVibrationPattern != null && this.mVibrationPattern.length > 1000) {
            this.mVibrationPattern = Arrays.copyOf(this.mVibrationPattern, 1000);
        }
        if (Flags.notificationChannelVibrationEffectApi()) {
            this.mVibrationEffect = in.readInt() != 0 ? VibrationEffect.CREATOR.createFromParcel(in) : null;
        }
        this.mUserLockedFields = in.readInt();
        if (in.readByte() == 0) {
            z3 = false;
        } else {
            z3 = true;
        }
        this.mUserVisibleTaskShown = z3;
        if (in.readByte() == 0) {
            z4 = false;
        } else {
            z4 = true;
        }
        this.mVibrationEnabled = z4;
        if (in.readByte() == 0) {
            z5 = false;
        } else {
            z5 = true;
        }
        this.mShowBadge = z5;
        this.mDeleted = in.readByte() != 0;
        if (in.readByte() != 0) {
            this.mGroup = getTrimmedString(in.readString());
        } else {
            this.mGroup = null;
        }
        this.mAudioAttributes = in.readInt() > 0 ? AudioAttributes.CREATOR.createFromParcel(in) : null;
        this.mLightColor = in.readInt();
        this.mBlockableSystem = in.readBoolean();
        this.mImportanceLockedByOEM = in.readBoolean();
        this.mAllowBubbles = in.readInt();
        this.mOriginalImportance = in.readInt();
        this.mParentId = getTrimmedString(in.readString());
        this.mConversationId = getTrimmedString(in.readString());
        this.mDemoted = in.readBoolean();
        this.mImportantConvo = in.readBoolean();
        this.mDeletedTime = in.readLong();
        this.mImportanceLockedDefaultApp = in.readBoolean();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        if (this.mId != null) {
            parcel.writeByte((byte) 1);
            parcel.writeString(this.mId);
        } else {
            parcel.writeByte((byte) 0);
        }
        if (this.mName != null) {
            parcel.writeByte((byte) 1);
            parcel.writeString(this.mName);
        } else {
            parcel.writeByte((byte) 0);
        }
        if (this.mDesc != null) {
            parcel.writeByte((byte) 1);
            parcel.writeString(this.mDesc);
        } else {
            parcel.writeByte((byte) 0);
        }
        parcel.writeInt(this.mImportance);
        parcel.writeByte(this.mBypassDnd ? (byte) 1 : (byte) 0);
        parcel.writeInt(this.mLockscreenVisibility);
        if (this.mSound != null) {
            parcel.writeByte((byte) 1);
            this.mSound.writeToParcel(parcel, 0);
        } else {
            parcel.writeByte((byte) 0);
        }
        parcel.writeByte(this.mLights ? (byte) 1 : (byte) 0);
        parcel.writeLongArray(this.mVibrationPattern);
        if (Flags.notificationChannelVibrationEffectApi()) {
            if (this.mVibrationEffect != null) {
                parcel.writeInt(1);
                this.mVibrationEffect.writeToParcel(parcel, 0);
            } else {
                parcel.writeInt(0);
            }
        }
        parcel.writeInt(this.mUserLockedFields);
        parcel.writeByte(this.mUserVisibleTaskShown ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.mVibrationEnabled ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.mShowBadge ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.mDeleted ? (byte) 1 : (byte) 0);
        if (this.mGroup != null) {
            parcel.writeByte((byte) 1);
            parcel.writeString(this.mGroup);
        } else {
            parcel.writeByte((byte) 0);
        }
        if (this.mAudioAttributes != null) {
            parcel.writeInt(1);
            this.mAudioAttributes.writeToParcel(parcel, 0);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeInt(this.mLightColor);
        parcel.writeBoolean(this.mBlockableSystem);
        parcel.writeBoolean(this.mImportanceLockedByOEM);
        parcel.writeInt(this.mAllowBubbles);
        parcel.writeInt(this.mOriginalImportance);
        parcel.writeString(this.mParentId);
        parcel.writeString(this.mConversationId);
        parcel.writeBoolean(this.mDemoted);
        parcel.writeBoolean(this.mImportantConvo);
        parcel.writeLong(this.mDeletedTime);
        parcel.writeBoolean(this.mImportanceLockedDefaultApp);
    }

    public NotificationChannel copy() {
        NotificationChannel copy = new NotificationChannel(this.mId, this.mName, this.mImportance);
        copy.setDescription(this.mDesc);
        copy.setBypassDnd(this.mBypassDnd);
        copy.setLockscreenVisibility(this.mLockscreenVisibility);
        copy.setSound(this.mSound, this.mAudioAttributes);
        copy.setLightColor(this.mLightColor);
        copy.enableLights(this.mLights);
        copy.setVibrationPattern(this.mVibrationPattern);
        if (Flags.notificationChannelVibrationEffectApi()) {
            copy.setVibrationEffect(this.mVibrationEffect);
        }
        copy.lockFields(this.mUserLockedFields);
        copy.setUserVisibleTaskShown(this.mUserVisibleTaskShown);
        copy.enableVibration(this.mVibrationEnabled);
        copy.setShowBadge(this.mShowBadge);
        copy.setDeleted(this.mDeleted);
        copy.setGroup(this.mGroup);
        copy.setBlockable(this.mBlockableSystem);
        copy.setAllowBubbles(this.mAllowBubbles);
        copy.setOriginalImportance(this.mOriginalImportance);
        copy.setConversationId(this.mParentId, this.mConversationId);
        copy.setDemoted(this.mDemoted);
        copy.setImportantConversation(this.mImportantConvo);
        copy.setDeletedTimeMs(this.mDeletedTime);
        copy.setImportanceLockedByCriticalDeviceFunction(this.mImportanceLockedDefaultApp);
        copy.setLastNotificationUpdateTimeMs(this.mLastNotificationUpdateTimeMs);
        return copy;
    }

    public void lockFields(int field) {
        this.mUserLockedFields |= field;
    }

    public void unlockFields(int field) {
        this.mUserLockedFields &= ~field;
    }

    public void setUserVisibleTaskShown(boolean shown) {
        this.mUserVisibleTaskShown = shown;
    }

    public void setDeleted(boolean deleted) {
        this.mDeleted = deleted;
    }

    public void setDeletedTimeMs(long time) {
        this.mDeletedTime = time;
    }

    public void setImportantConversation(boolean importantConvo) {
        this.mImportantConvo = importantConvo;
    }

    public void setBlockable(boolean blockable) {
        this.mBlockableSystem = blockable;
    }

    public void setName(CharSequence name) {
        this.mName = name != null ? getTrimmedString(name.toString()) : null;
    }

    public void setDescription(String description) {
        this.mDesc = getTrimmedString(description);
    }

    private String getTrimmedString(String input) {
        if (input != null && input.length() > 1000) {
            return input.substring(0, 1000);
        }
        return input;
    }

    public void setId(String id) {
        this.mId = id;
    }

    public void setGroup(String groupId) {
        this.mGroup = groupId;
    }

    public void setShowBadge(boolean showBadge) {
        this.mShowBadge = showBadge;
    }

    public void setSound(Uri sound, AudioAttributes audioAttributes) {
        this.mSound = sound;
        this.mAudioAttributes = audioAttributes;
    }

    public void enableLights(boolean lights) {
        this.mLights = lights;
    }

    public void setLightColor(int argb) {
        this.mLightColor = argb;
    }

    public void enableVibration(boolean vibration) {
        this.mVibrationEnabled = vibration;
    }

    public void setVibrationPattern(long[] vibrationPattern) {
        this.mVibrationEnabled = vibrationPattern != null && vibrationPattern.length > 0;
        this.mVibrationPattern = vibrationPattern;
        if (Flags.notificationChannelVibrationEffectApi()) {
            try {
                this.mVibrationEffect = VibrationEffect.createWaveform(vibrationPattern, -1);
            } catch (IllegalArgumentException | NullPointerException e) {
                this.mVibrationEffect = null;
            }
        }
    }

    public void setVibrationEffect(VibrationEffect effect) {
        this.mVibrationEnabled = effect != null;
        this.mVibrationEffect = effect;
        this.mVibrationPattern = effect == null ? null : effect.computeCreateWaveformOffOnTimingsOrNull();
    }

    public void setImportance(int importance) {
        this.mImportance = importance;
    }

    public void setBypassDnd(boolean bypassDnd) {
        this.mBypassDnd = bypassDnd;
    }

    public void setLockscreenVisibility(int lockscreenVisibility) {
        this.mLockscreenVisibility = lockscreenVisibility;
    }

    public void setAllowBubbles(boolean z) {
        this.mAllowBubbles = z ? 1 : 0;
    }

    public void setAllowBubbles(int allowed) {
        this.mAllowBubbles = allowed;
    }

    public void setConversationId(String parentChannelId, String conversationId) {
        this.mParentId = parentChannelId;
        this.mConversationId = conversationId;
    }

    public String getId() {
        return this.mId;
    }

    public CharSequence getName() {
        return this.mName;
    }

    public String getDescription() {
        return this.mDesc;
    }

    public int getImportance() {
        return this.mImportance;
    }

    public boolean canBypassDnd() {
        return this.mBypassDnd;
    }

    public boolean isConversation() {
        return !TextUtils.isEmpty(getConversationId());
    }

    public boolean isImportantConversation() {
        return this.mImportantConvo;
    }

    public Uri getSound() {
        return this.mSound;
    }

    public AudioAttributes getAudioAttributes() {
        return this.mAudioAttributes;
    }

    public boolean shouldShowLights() {
        return this.mLights;
    }

    public int getLightColor() {
        return this.mLightColor;
    }

    public boolean shouldVibrate() {
        return this.mVibrationEnabled;
    }

    public long[] getVibrationPattern() {
        return this.mVibrationPattern;
    }

    public VibrationEffect getVibrationEffect() {
        return this.mVibrationEffect;
    }

    public int getLockscreenVisibility() {
        return this.mLockscreenVisibility;
    }

    public boolean canShowBadge() {
        return this.mShowBadge;
    }

    public String getGroup() {
        return this.mGroup;
    }

    public boolean canBubble() {
        return this.mAllowBubbles == 1;
    }

    public int getAllowBubbles() {
        return this.mAllowBubbles;
    }

    public String getParentChannelId() {
        return this.mParentId;
    }

    public String getConversationId() {
        return this.mConversationId;
    }

    @SystemApi
    public boolean isDeleted() {
        return this.mDeleted;
    }

    public long getDeletedTimeMs() {
        return this.mDeletedTime;
    }

    @SystemApi
    public int getUserLockedFields() {
        return this.mUserLockedFields;
    }

    public boolean isUserVisibleTaskShown() {
        return this.mUserVisibleTaskShown;
    }

    public boolean isBlockable() {
        return this.mBlockableSystem;
    }

    public void setImportanceLockedByOEM(boolean locked) {
        this.mImportanceLockedByOEM = locked;
    }

    public boolean isImportanceLockedByOEM() {
        return this.mImportanceLockedByOEM;
    }

    public void setImportanceLockedByCriticalDeviceFunction(boolean locked) {
        this.mImportanceLockedDefaultApp = locked;
    }

    public boolean isImportanceLockedByCriticalDeviceFunction() {
        return this.mImportanceLockedDefaultApp;
    }

    public int getOriginalImportance() {
        return this.mOriginalImportance;
    }

    public void setOriginalImportance(int importance) {
        this.mOriginalImportance = importance;
    }

    public void setDemoted(boolean demoted) {
        this.mDemoted = demoted;
    }

    public boolean isDemoted() {
        return this.mDemoted;
    }

    public boolean hasUserSetImportance() {
        return (this.mUserLockedFields & 4) != 0;
    }

    public boolean hasUserSetSound() {
        return (this.mUserLockedFields & 32) != 0;
    }

    public long getLastNotificationUpdateTimeMs() {
        return this.mLastNotificationUpdateTimeMs;
    }

    public void setLastNotificationUpdateTimeMs(long updateTimeMs) {
        this.mLastNotificationUpdateTimeMs = updateTimeMs;
    }

    public void populateFromXmlForRestore(XmlPullParser parser, boolean pkgInstalled, Context context) {
        populateFromXml(XmlUtils.makeTyped(parser), true, pkgInstalled, context);
    }

    @SystemApi
    public void populateFromXml(XmlPullParser parser) {
        populateFromXml(XmlUtils.makeTyped(parser), false, true, null);
    }

    private void populateFromXml(TypedXmlPullParser parser, boolean forRestore, boolean pkgInstalled, Context context) {
        VibrationEffect vibrationEffect;
        Preconditions.checkArgument((forRestore && context == null) ? false : true, "forRestore is true but got null context");
        setDescription(parser.getAttributeValue(null, ATT_DESC));
        setBypassDnd(safeInt(parser, "priority", 0) != 0);
        setLockscreenVisibility(safeInt(parser, "visibility", -1000));
        Uri sound = safeUri(parser, "sound");
        AudioAttributes audioAttributes = safeAudioAttributes(parser);
        int usage = audioAttributes.getUsage();
        setSound(forRestore ? restoreSoundUri(context, sound, pkgInstalled, usage) : sound, audioAttributes);
        enableLights(safeBool(parser, "lights", false));
        setLightColor(safeInt(parser, ATT_LIGHT_COLOR, 0));
        setVibrationPattern(safeLongArray(parser, "vibration", null));
        if (Flags.notificationChannelVibrationEffectApi() && (vibrationEffect = safeVibrationEffect(parser, ATT_VIBRATION_EFFECT)) != null) {
            setVibrationEffect(vibrationEffect);
        }
        enableVibration(safeBool(parser, ATT_VIBRATION_ENABLED, false));
        setShowBadge(safeBool(parser, ATT_SHOW_BADGE, false));
        setDeleted(safeBool(parser, "deleted", false));
        setDeletedTimeMs(XmlUtils.readLongAttribute(parser, ATT_DELETED_TIME_MS, -1L));
        setGroup(parser.getAttributeValue(null, ATT_GROUP));
        lockFields(safeInt(parser, "locked", 0));
        setUserVisibleTaskShown(safeBool(parser, ATT_FG_SERVICE_SHOWN, false));
        setBlockable(safeBool(parser, ATT_BLOCKABLE_SYSTEM, false));
        setAllowBubbles(safeInt(parser, ATT_ALLOW_BUBBLE, -1));
        setOriginalImportance(safeInt(parser, ATT_ORIG_IMP, -1000));
        setConversationId(parser.getAttributeValue(null, "parent"), parser.getAttributeValue(null, ATT_CONVERSATION_ID));
        setDemoted(safeBool(parser, ATT_DEMOTE, false));
        setImportantConversation(safeBool(parser, ATT_IMP_CONVERSATION, false));
    }

    public boolean isSoundRestored() {
        return this.mSoundRestored;
    }

    private Uri getCanonicalizedSoundUri(ContentResolver contentResolver, Uri uri) {
        if (Settings.System.DEFAULT_NOTIFICATION_URI.equals(uri)) {
            return uri;
        }
        if (ContentResolver.SCHEME_ANDROID_RESOURCE.equals(uri.getScheme())) {
            try {
                contentResolver.getResourceId(uri);
                return uri;
            } catch (FileNotFoundException e) {
                return null;
            }
        }
        if ("file".equals(uri.getScheme())) {
            return uri;
        }
        return contentResolver.canonicalize(uri);
    }

    private Uri getUncanonicalizedSoundUri(ContentResolver contentResolver, Uri uri, int usage) {
        int ringtoneType;
        if (Settings.System.DEFAULT_NOTIFICATION_URI.equals(uri) || ContentResolver.SCHEME_ANDROID_RESOURCE.equals(uri.getScheme()) || "file".equals(uri.getScheme())) {
            return uri;
        }
        if (4 == usage) {
            ringtoneType = 4;
        } else if (6 == usage) {
            ringtoneType = 1;
        } else {
            ringtoneType = 2;
        }
        try {
            return RingtoneManager.getRingtoneUriForRestore(contentResolver, uri.toString(), ringtoneType);
        } catch (Exception e) {
            Log.e(TAG, "Failed to uncanonicalized sound uri for " + uri + " " + e);
            return Settings.System.DEFAULT_NOTIFICATION_URI;
        }
    }

    public Uri restoreSoundUri(Context context, Uri uri, boolean pkgInstalled, int usage) {
        String volumeName;
        Cursor c;
        if (uri == null || Uri.EMPTY.equals(uri)) {
            return null;
        }
        ContentResolver contentResolver = context.getContentResolver();
        Uri canonicalizedUri = getCanonicalizedSoundUri(contentResolver, uri);
        if (canonicalizedUri == null) {
            if (!this.mSoundRestored && pkgInstalled) {
                this.mSoundRestored = true;
                this.mSoundMissingReason = 2;
                return Settings.System.DEFAULT_NOTIFICATION_URI;
            }
            this.mSoundRestored = false;
            return uri;
        }
        this.mSoundRestored = true;
        Uri unCanonicalizedUri = contentResolver.uncanonicalize(canonicalizedUri);
        if (unCanonicalizedUri == null) {
            try {
                volumeName = MediaStore.getVolumeName(canonicalizedUri);
                Uri audioUri = MediaStore.Audio.Media.getContentUri(volumeName);
                String[] projection = {"_id", "is_notification"};
                String title = canonicalizedUri.getQueryParameter("title");
                Bundle queryArgs = new Bundle();
                queryArgs.putString(ContentResolver.QUERY_ARG_SQL_SELECTION, "title='" + title + "'");
                c = contentResolver.query(audioUri, projection, queryArgs, null);
            } catch (IllegalArgumentException e) {
                Slog.e("NotiChannel", "This is not MediaSore uri : " + canonicalizedUri, e);
            }
            try {
                try {
                    if (c.moveToFirst()) {
                        long rowId = c.getLong(0);
                        Uri foundUri = MediaStore.Audio.Media.getContentUri(volumeName, rowId);
                        if (foundUri == null) {
                            this.mSoundMissingReason = 3;
                        }
                        if (c != null) {
                        }
                        return foundUri;
                    }
                    c.close();
                    if (c != null) {
                    }
                    this.mSoundMissingReason = 4;
                    return Settings.System.DEFAULT_NOTIFICATION_URI;
                } finally {
                }
            } finally {
                c.close();
            }
        } else {
            return unCanonicalizedUri;
        }
    }

    @SystemApi
    public void writeXml(XmlSerializer out) throws IOException {
        writeXml(XmlUtils.makeTyped(out), false, null);
    }

    public void writeXmlForBackup(XmlSerializer out, Context context) throws IOException {
        writeXml(XmlUtils.makeTyped(out), true, context);
    }

    private Uri getSoundForBackup(Context context) {
        Uri sound = getSound();
        if (sound == null || Uri.EMPTY.equals(sound)) {
            return null;
        }
        try {
            Uri canonicalSound = context.getContentResolver().canonicalize(sound);
            if (canonicalSound == null) {
                return Settings.System.DEFAULT_NOTIFICATION_URI;
            }
            return canonicalSound;
        } catch (SecurityException e) {
            Slog.e("NotiChannel", sound + " uri permission from App", e);
            return Settings.System.DEFAULT_NOTIFICATION_URI;
        }
    }

    private void writeXml(TypedXmlSerializer out, boolean forBackup, Context context) throws IOException {
        Preconditions.checkArgument((forBackup && context == null) ? false : true, "forBackup is true but got null context");
        out.startTag(null, "channel");
        out.attribute(null, "id", getId());
        if (getName() != null) {
            out.attribute(null, "name", getName().toString());
        }
        if (getDescription() != null) {
            out.attribute(null, ATT_DESC, getDescription());
        }
        if (getImportance() != -1000) {
            out.attributeInt(null, "importance", getImportance());
        }
        if (canBypassDnd()) {
            out.attributeInt(null, "priority", 2);
        }
        if (getLockscreenVisibility() != -1000) {
            out.attributeInt(null, "visibility", getLockscreenVisibility());
        }
        Uri sound = forBackup ? getSoundForBackup(context) : getSound();
        if (sound != null) {
            out.attribute(null, "sound", sound.toString());
        }
        if (getAudioAttributes() != null) {
            out.attributeInt(null, ATT_USAGE, getAudioAttributes().getUsage());
            out.attributeInt(null, ATT_CONTENT_TYPE, getAudioAttributes().getContentType());
            out.attributeInt(null, "flags", getAudioAttributes().getFlags());
        }
        if (shouldShowLights()) {
            out.attributeBoolean(null, "lights", shouldShowLights());
        }
        if (getLightColor() != 0) {
            out.attributeInt(null, ATT_LIGHT_COLOR, getLightColor());
        }
        if (shouldVibrate()) {
            out.attributeBoolean(null, ATT_VIBRATION_ENABLED, shouldVibrate());
        }
        if (getVibrationPattern() != null) {
            out.attribute(null, "vibration", longArrayToString(getVibrationPattern()));
        }
        if (getVibrationEffect() != null) {
            out.attribute(null, ATT_VIBRATION_EFFECT, vibrationToString(getVibrationEffect()));
        }
        if (getUserLockedFields() != 0) {
            out.attributeInt(null, "locked", getUserLockedFields());
        }
        if (isUserVisibleTaskShown()) {
            out.attributeBoolean(null, ATT_FG_SERVICE_SHOWN, isUserVisibleTaskShown());
        }
        if (canShowBadge()) {
            out.attributeBoolean(null, ATT_SHOW_BADGE, canShowBadge());
        }
        if (isDeleted()) {
            out.attributeBoolean(null, "deleted", isDeleted());
        }
        if (getDeletedTimeMs() >= 0) {
            out.attributeLong(null, ATT_DELETED_TIME_MS, getDeletedTimeMs());
        }
        if (getGroup() != null) {
            out.attribute(null, ATT_GROUP, getGroup());
        }
        if (isBlockable()) {
            out.attributeBoolean(null, ATT_BLOCKABLE_SYSTEM, isBlockable());
        }
        if (getAllowBubbles() != -1) {
            out.attributeInt(null, ATT_ALLOW_BUBBLE, getAllowBubbles());
        }
        if (getOriginalImportance() != -1000) {
            out.attributeInt(null, ATT_ORIG_IMP, getOriginalImportance());
        }
        if (getParentChannelId() != null) {
            out.attribute(null, "parent", getParentChannelId());
        }
        if (getConversationId() != null) {
            out.attribute(null, ATT_CONVERSATION_ID, getConversationId());
        }
        if (isDemoted()) {
            out.attributeBoolean(null, ATT_DEMOTE, isDemoted());
        }
        if (isImportantConversation()) {
            out.attributeBoolean(null, ATT_IMP_CONVERSATION, isImportantConversation());
        }
        out.endTag(null, "channel");
    }

    @SystemApi
    public JSONObject toJson() throws JSONException {
        JSONObject record = new JSONObject();
        record.put("id", getId());
        record.put("name", getName());
        record.put(ATT_DESC, getDescription());
        if (getImportance() != -1000) {
            record.put("importance", NotificationListenerService.Ranking.importanceToString(getImportance()));
        }
        if (canBypassDnd()) {
            record.put("priority", 2);
        }
        if (getLockscreenVisibility() != -1000) {
            record.put("visibility", Notification.visibilityToString(getLockscreenVisibility()));
        }
        if (getSound() != null) {
            record.put("sound", getSound().toString());
        }
        if (getAudioAttributes() != null) {
            record.put(ATT_USAGE, Integer.toString(getAudioAttributes().getUsage()));
            record.put(ATT_CONTENT_TYPE, Integer.toString(getAudioAttributes().getContentType()));
            record.put("flags", Integer.toString(getAudioAttributes().getFlags()));
        }
        record.put("lights", Boolean.toString(shouldShowLights()));
        record.put(ATT_LIGHT_COLOR, Integer.toString(getLightColor()));
        record.put(ATT_VIBRATION_ENABLED, Boolean.toString(shouldVibrate()));
        record.put("locked", Integer.toString(getUserLockedFields()));
        record.put(ATT_FG_SERVICE_SHOWN, Boolean.toString(isUserVisibleTaskShown()));
        record.put("vibration", longArrayToString(getVibrationPattern()));
        if (getVibrationEffect() != null) {
            record.put(ATT_VIBRATION_EFFECT, vibrationToString(getVibrationEffect()));
        }
        record.put(ATT_SHOW_BADGE, Boolean.toString(canShowBadge()));
        record.put("deleted", Boolean.toString(isDeleted()));
        record.put(ATT_DELETED_TIME_MS, Long.toString(getDeletedTimeMs()));
        record.put(ATT_GROUP, getGroup());
        record.put(ATT_BLOCKABLE_SYSTEM, isBlockable());
        record.put(ATT_ALLOW_BUBBLE, getAllowBubbles());
        return record;
    }

    private static AudioAttributes safeAudioAttributes(TypedXmlPullParser parser) {
        int usage = safeInt(parser, ATT_USAGE, 5);
        int contentType = safeInt(parser, ATT_CONTENT_TYPE, 4);
        int flags = safeInt(parser, "flags", 0);
        return new AudioAttributes.Builder().setUsage(usage).setContentType(contentType).setFlags(flags).build();
    }

    private static Uri safeUri(TypedXmlPullParser parser, String att) {
        String val = parser.getAttributeValue(null, att);
        if (val == null) {
            return null;
        }
        return Uri.parse(val);
    }

    private static String vibrationToString(VibrationEffect effect) {
        StringWriter writer = new StringWriter();
        try {
            VibrationXmlSerializer.serialize(effect, writer, 1);
        } catch (IOException e) {
            Log.e(TAG, "Unable to serialize vibration: " + effect, e);
        }
        return writer.toString();
    }

    private static VibrationEffect safeVibrationEffect(TypedXmlPullParser parser, String att) {
        String val = parser.getAttributeValue(null, att);
        if (val != null) {
            try {
                return VibrationXmlParser.parseVibrationEffect(new StringReader(val), 1);
            } catch (IOException e) {
                Log.e(TAG, "Unable to read serialized vibration effect", e);
            }
        }
        return null;
    }

    private static int safeInt(TypedXmlPullParser parser, String att, int defValue) {
        return parser.getAttributeInt(null, att, defValue);
    }

    private static boolean safeBool(TypedXmlPullParser parser, String att, boolean defValue) {
        return parser.getAttributeBoolean(null, att, defValue);
    }

    private static long[] safeLongArray(TypedXmlPullParser parser, String att, long[] defValue) {
        String attributeValue = parser.getAttributeValue(null, att);
        if (TextUtils.isEmpty(attributeValue)) {
            return defValue;
        }
        String[] values = attributeValue.split(",");
        long[] longValues = new long[values.length];
        for (int i = 0; i < values.length; i++) {
            try {
                longValues[i] = Long.parseLong(values[i]);
            } catch (NumberFormatException e) {
                longValues[i] = 0;
            }
        }
        return longValues;
    }

    private static String longArrayToString(long[] values) {
        StringBuilder sb = new StringBuilder();
        if (values != null && values.length > 0) {
            for (int i = 0; i < values.length - 1; i++) {
                sb.append(values[i]).append(",");
            }
            int i2 = values.length;
            sb.append(values[i2 - 1]);
        }
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        NotificationChannel that = (NotificationChannel) o;
        if (getImportance() == that.getImportance() && this.mBypassDnd == that.mBypassDnd && getLockscreenVisibility() == that.getLockscreenVisibility() && this.mLights == that.mLights && getLightColor() == that.getLightColor() && getUserLockedFields() == that.getUserLockedFields() && isUserVisibleTaskShown() == that.isUserVisibleTaskShown() && this.mVibrationEnabled == that.mVibrationEnabled && this.mShowBadge == that.mShowBadge && isDeleted() == that.isDeleted() && getDeletedTimeMs() == that.getDeletedTimeMs() && isBlockable() == that.isBlockable() && this.mAllowBubbles == that.mAllowBubbles && Objects.equals(getId(), that.getId()) && Objects.equals(getName(), that.getName()) && Objects.equals(this.mDesc, that.mDesc) && Objects.equals(getSound(), that.getSound()) && Arrays.equals(this.mVibrationPattern, that.mVibrationPattern) && Objects.equals(getVibrationEffect(), that.getVibrationEffect()) && Objects.equals(getGroup(), that.getGroup()) && Objects.equals(getAudioAttributes(), that.getAudioAttributes()) && isImportanceLockedByOEM() == that.isImportanceLockedByOEM() && this.mImportanceLockedDefaultApp == that.mImportanceLockedDefaultApp && this.mOriginalImportance == that.mOriginalImportance && Objects.equals(getParentChannelId(), that.getParentChannelId()) && Objects.equals(getConversationId(), that.getConversationId()) && isDemoted() == that.isDemoted() && isImportantConversation() == that.isImportantConversation()) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int result = Objects.hash(getId(), getName(), this.mDesc, Integer.valueOf(getImportance()), Boolean.valueOf(this.mBypassDnd), Integer.valueOf(getLockscreenVisibility()), getSound(), Boolean.valueOf(this.mLights), Integer.valueOf(getLightColor()), Integer.valueOf(getUserLockedFields()), Boolean.valueOf(isUserVisibleTaskShown()), Boolean.valueOf(this.mVibrationEnabled), Boolean.valueOf(this.mShowBadge), Boolean.valueOf(isDeleted()), Long.valueOf(getDeletedTimeMs()), getGroup(), getAudioAttributes(), Boolean.valueOf(isBlockable()), Integer.valueOf(this.mAllowBubbles), Boolean.valueOf(this.mImportanceLockedByOEM), Boolean.valueOf(this.mImportanceLockedDefaultApp), Integer.valueOf(this.mOriginalImportance), getVibrationEffect(), this.mParentId, this.mConversationId, Boolean.valueOf(this.mDemoted), Boolean.valueOf(this.mImportantConvo));
        return (result * 31) + Arrays.hashCode(this.mVibrationPattern);
    }

    public void dump(PrintWriter pw, String prefix, boolean redacted) {
        String redactedName = this.mName;
        if (redacted) {
            redactedName = (String) TextUtils.trimToLengthWithEllipsis(redactedName, 6);
        }
        String fixedId = getRedatedString(this.mId);
        String output = "NotificationChannel{mId='" + fixedId + DateFormat.QUOTE + ", mName=" + redactedName + getFieldsString() + '}';
        pw.println(prefix + output);
    }

    public String toString() {
        String fixedId = getRedatedString(this.mId);
        return "NotificationChannel{mId='" + fixedId + DateFormat.QUOTE + ", mName=" + this.mName + getFieldsString() + '}';
    }

    private String getRedatedString(String str) {
        if (isMatchPrivatePattern(str)) {
            return (String) TextUtils.trimToLengthWithEllipsis(str, 6);
        }
        return str;
    }

    private boolean isMatchPrivatePattern(String pattern) {
        if (pattern == null) {
            return false;
        }
        if (Patterns.PHONE.matcher(pattern).matches() || Patterns.WEB_URL.matcher(pattern).matches()) {
            return true;
        }
        boolean atFound = false;
        boolean commaFound = false;
        int i = 0;
        while (true) {
            if (i < pattern.length()) {
                if (pattern.charAt(i) == '@') {
                    atFound = true;
                }
                if (!atFound || pattern.charAt(i) != '.') {
                    i++;
                } else {
                    commaFound = true;
                    break;
                }
            } else {
                break;
            }
        }
        return commaFound;
    }

    private String getFieldsString() {
        String fixedId = getRedatedString(this.mGroup);
        return ", mDescription=" + (!TextUtils.isEmpty(this.mDesc) ? "hasDescription " : "") + ", mImportance=" + this.mImportance + ", mBypassDnd=" + this.mBypassDnd + ", mLockscreenVisibility=" + this.mLockscreenVisibility + ", mSound=" + this.mSound + ", mLights=" + this.mLights + ", mLightColor=" + this.mLightColor + ", mVibrationPattern=" + Arrays.toString(this.mVibrationPattern) + ", mVibrationEffect=" + (this.mVibrationEffect == null ? "null" : this.mVibrationEffect.toString()) + ", mUserLockedFields=" + Integer.toHexString(this.mUserLockedFields) + ", mUserVisibleTaskShown=" + this.mUserVisibleTaskShown + ", mVibrationEnabled=" + this.mVibrationEnabled + ", mShowBadge=" + this.mShowBadge + ", mDeleted=" + this.mDeleted + ", mDeletedTimeMs=" + this.mDeletedTime + ", mGroup='" + fixedId + DateFormat.QUOTE + ", mAudioAttributes=" + this.mAudioAttributes + ", mBlockableSystem=" + this.mBlockableSystem + ", mAllowBubbles=" + this.mAllowBubbles + ", mImportanceLockedByOEM=" + this.mImportanceLockedByOEM + ", mImportanceLockedDefaultApp=" + this.mImportanceLockedDefaultApp + ", mOriginalImp=" + this.mOriginalImportance + ", mParent=" + this.mParentId + ", mConversationId=" + this.mConversationId + ", mDemoted=" + this.mDemoted + ", mImportantConvo=" + this.mImportantConvo + ", mLastNotificationUpdateTimeMs=" + this.mLastNotificationUpdateTimeMs + ", mSoundMissingReason=" + this.mSoundMissingReason;
    }

    public void dumpDebug(ProtoOutputStream proto, long fieldId) {
        long token = proto.start(fieldId);
        proto.write(1138166333441L, this.mId);
        proto.write(1138166333442L, this.mName);
        proto.write(1138166333443L, this.mDesc);
        proto.write(1120986464260L, this.mImportance);
        proto.write(1133871366149L, this.mBypassDnd);
        proto.write(1120986464262L, this.mLockscreenVisibility);
        if (this.mSound != null) {
            proto.write(1138166333447L, this.mSound.toString());
        }
        proto.write(1133871366152L, this.mLights);
        proto.write(1120986464265L, this.mLightColor);
        if (this.mVibrationPattern != null) {
            for (long v : this.mVibrationPattern) {
                proto.write(NotificationChannelProto.VIBRATION, v);
            }
        }
        proto.write(1120986464267L, this.mUserLockedFields);
        proto.write(1133871366162L, this.mUserVisibleTaskShown);
        proto.write(1133871366156L, this.mVibrationEnabled);
        proto.write(1133871366157L, this.mShowBadge);
        proto.write(1133871366158L, this.mDeleted);
        proto.write(1138166333455L, this.mGroup);
        if (this.mAudioAttributes != null) {
            this.mAudioAttributes.dumpDebug(proto, 1146756268048L);
        }
        proto.write(1133871366161L, this.mBlockableSystem);
        proto.write(1133871366163L, this.mAllowBubbles);
        proto.end(token);
    }
}
