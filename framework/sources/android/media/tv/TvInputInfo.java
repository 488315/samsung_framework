package android.media.tv;

import android.annotation.SystemApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.hardware.hdmi.HdmiControlManager;
import android.hardware.hdmi.HdmiDeviceInfo;
import android.hardware.hdmi.HdmiUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.util.Xml;
import com.android.internal.R;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes3.dex */
public final class TvInputInfo implements Parcelable {
    public static final Parcelable.Creator<TvInputInfo> CREATOR = new Parcelable.Creator<TvInputInfo>() { // from class: android.media.tv.TvInputInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TvInputInfo createFromParcel(Parcel in) {
            return new TvInputInfo(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TvInputInfo[] newArray(int size) {
            return new TvInputInfo[size];
        }
    };
    private static final boolean DEBUG = false;
    public static final String EXTRA_INPUT_ID = "android.media.tv.extra.INPUT_ID";
    private static final String TAG = "TvInputInfo";
    public static final int TYPE_COMPONENT = 1004;
    public static final int TYPE_COMPOSITE = 1001;
    public static final int TYPE_DISPLAY_PORT = 1008;
    public static final int TYPE_DVI = 1006;
    public static final int TYPE_HDMI = 1007;
    public static final int TYPE_OTHER = 1000;
    public static final int TYPE_SCART = 1003;
    public static final int TYPE_SVIDEO = 1002;
    public static final int TYPE_TUNER = 0;
    public static final int TYPE_VGA = 1005;
    private final boolean mCanPauseRecording;
    private final boolean mCanRecord;
    private final Bundle mExtras;
    private final int mHdmiConnectionRelativePosition;
    private final HdmiDeviceInfo mHdmiDeviceInfo;
    private final Icon mIcon;
    private final Icon mIconDisconnected;
    private final Icon mIconStandby;
    private Uri mIconUri;
    private final String mId;
    private final boolean mIsConnectedToHdmiSwitch;
    private final boolean mIsHardwareInput;
    private final CharSequence mLabel;
    private final int mLabelResId;
    private final String mParentId;
    private final ResolveInfo mService;
    private final String mSetupActivity;
    private final int mTunerCount;
    private final int mType;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
    }

    @SystemApi
    @Deprecated
    public static TvInputInfo createTvInputInfo(Context context, ResolveInfo service, HdmiDeviceInfo hdmiDeviceInfo, String parentId, String label, Uri iconUri) throws XmlPullParserException, IOException {
        TvInputInfo info = new Builder(context, service).setHdmiDeviceInfo(hdmiDeviceInfo).setParentId(parentId).setLabel(label).build();
        info.mIconUri = iconUri;
        return info;
    }

    @SystemApi
    @Deprecated
    public static TvInputInfo createTvInputInfo(Context context, ResolveInfo service, HdmiDeviceInfo hdmiDeviceInfo, String parentId, int labelRes, Icon icon) throws XmlPullParserException, IOException {
        return new Builder(context, service).setHdmiDeviceInfo(hdmiDeviceInfo).setParentId(parentId).setLabel(labelRes).setIcon(icon).build();
    }

    @SystemApi
    @Deprecated
    public static TvInputInfo createTvInputInfo(Context context, ResolveInfo service, TvInputHardwareInfo hardwareInfo, String label, Uri iconUri) throws XmlPullParserException, IOException {
        TvInputInfo info = new Builder(context, service).setTvInputHardwareInfo(hardwareInfo).setLabel(label).build();
        info.mIconUri = iconUri;
        return info;
    }

    @SystemApi
    @Deprecated
    public static TvInputInfo createTvInputInfo(Context context, ResolveInfo service, TvInputHardwareInfo hardwareInfo, int labelRes, Icon icon) throws XmlPullParserException, IOException {
        return new Builder(context, service).setTvInputHardwareInfo(hardwareInfo).setLabel(labelRes).setIcon(icon).build();
    }

    private TvInputInfo(ResolveInfo service, String id, int type, boolean isHardwareInput, CharSequence label, int labelResId, Icon icon, Icon iconStandby, Icon iconDisconnected, String setupActivity, boolean canRecord, boolean canPauseRecording, int tunerCount, HdmiDeviceInfo hdmiDeviceInfo, boolean isConnectedToHdmiSwitch, int hdmiConnectionRelativePosition, String parentId, Bundle extras) {
        this.mService = service;
        this.mId = id;
        this.mType = type;
        this.mIsHardwareInput = isHardwareInput;
        this.mLabel = label;
        this.mLabelResId = labelResId;
        this.mIcon = icon;
        this.mIconStandby = iconStandby;
        this.mIconDisconnected = iconDisconnected;
        this.mSetupActivity = setupActivity;
        this.mCanRecord = canRecord;
        this.mCanPauseRecording = canPauseRecording;
        this.mTunerCount = tunerCount;
        this.mHdmiDeviceInfo = hdmiDeviceInfo;
        this.mIsConnectedToHdmiSwitch = isConnectedToHdmiSwitch;
        this.mHdmiConnectionRelativePosition = hdmiConnectionRelativePosition;
        this.mParentId = parentId;
        this.mExtras = extras;
    }

    public String getId() {
        return this.mId;
    }

    public String getParentId() {
        return this.mParentId;
    }

    public ServiceInfo getServiceInfo() {
        return this.mService.serviceInfo;
    }

    public ComponentName getComponent() {
        return new ComponentName(this.mService.serviceInfo.packageName, this.mService.serviceInfo.name);
    }

    public Intent createSetupIntent() {
        if (!TextUtils.isEmpty(this.mSetupActivity)) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.setClassName(this.mService.serviceInfo.packageName, this.mSetupActivity);
            intent.putExtra(EXTRA_INPUT_ID, getId());
            return intent;
        }
        return null;
    }

    @Deprecated
    public Intent createSettingsIntent() {
        return null;
    }

    public int getType() {
        return this.mType;
    }

    public int getTunerCount() {
        return this.mTunerCount;
    }

    public boolean canRecord() {
        return this.mCanRecord;
    }

    public boolean canPauseRecording() {
        return this.mCanPauseRecording;
    }

    public Bundle getExtras() {
        return this.mExtras;
    }

    @SystemApi
    public HdmiDeviceInfo getHdmiDeviceInfo() {
        if (this.mType == 1007) {
            return this.mHdmiDeviceInfo;
        }
        return null;
    }

    public boolean isPassthroughInput() {
        return this.mType != 0;
    }

    @SystemApi
    public boolean isHardwareInput() {
        return this.mIsHardwareInput;
    }

    @SystemApi
    public boolean isConnectedToHdmiSwitch() {
        return this.mIsConnectedToHdmiSwitch;
    }

    public int getHdmiConnectionRelativePosition() {
        return this.mHdmiConnectionRelativePosition;
    }

    public boolean isHidden(Context context) {
        return TvInputSettings.isHidden(context, this.mId, UserHandle.myUserId());
    }

    public CharSequence loadLabel(Context context) {
        if (this.mLabelResId != 0) {
            return context.getPackageManager().getText(this.mService.serviceInfo.packageName, this.mLabelResId, null);
        }
        if (!TextUtils.isEmpty(this.mLabel)) {
            return this.mLabel;
        }
        return this.mService.loadLabel(context.getPackageManager());
    }

    public CharSequence loadCustomLabel(Context context) {
        return TvInputSettings.getCustomLabel(context, this.mId, UserHandle.myUserId());
    }

    public Drawable loadIcon(Context context) {
        if (this.mIcon != null) {
            return this.mIcon.loadDrawable(context);
        }
        if (this.mIconUri != null) {
            try {
                InputStream is = context.getContentResolver().openInputStream(this.mIconUri);
                try {
                    Drawable drawable = Drawable.createFromStream(is, null);
                    if (drawable != null) {
                        if (is != null) {
                            is.close();
                        }
                        return drawable;
                    }
                    if (is != null) {
                        is.close();
                    }
                } finally {
                }
            } catch (IOException e) {
                Log.w(TAG, "Loading the default icon due to a failure on loading " + this.mIconUri, e);
            }
        }
        return loadServiceIcon(context);
    }

    @SystemApi
    public Drawable loadIcon(Context context, int state) {
        if (state == 0) {
            return loadIcon(context);
        }
        if (state == 1) {
            if (this.mIconStandby != null) {
                return this.mIconStandby.loadDrawable(context);
            }
            return null;
        }
        if (state == 2) {
            if (this.mIconDisconnected != null) {
                return this.mIconDisconnected.loadDrawable(context);
            }
            return null;
        }
        throw new IllegalArgumentException("Unknown state: " + state);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int hashCode() {
        return this.mId.hashCode();
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TvInputInfo)) {
            return false;
        }
        TvInputInfo obj = (TvInputInfo) o;
        return Objects.equals(this.mService, obj.mService) && TextUtils.equals(this.mId, obj.mId) && this.mType == obj.mType && this.mIsHardwareInput == obj.mIsHardwareInput && TextUtils.equals(this.mLabel, obj.mLabel) && Objects.equals(this.mIconUri, obj.mIconUri) && this.mLabelResId == obj.mLabelResId && Objects.equals(this.mIcon, obj.mIcon) && Objects.equals(this.mIconStandby, obj.mIconStandby) && Objects.equals(this.mIconDisconnected, obj.mIconDisconnected) && TextUtils.equals(this.mSetupActivity, obj.mSetupActivity) && this.mCanRecord == obj.mCanRecord && this.mCanPauseRecording == obj.mCanPauseRecording && this.mTunerCount == obj.mTunerCount && Objects.equals(this.mHdmiDeviceInfo, obj.mHdmiDeviceInfo) && this.mIsConnectedToHdmiSwitch == obj.mIsConnectedToHdmiSwitch && this.mHdmiConnectionRelativePosition == obj.mHdmiConnectionRelativePosition && TextUtils.equals(this.mParentId, obj.mParentId) && Objects.equals(this.mExtras, obj.mExtras);
    }

    public String toString() {
        return "TvInputInfo{id=" + this.mId + ", pkg=" + this.mService.serviceInfo.packageName + ", service=" + this.mService.serviceInfo.name + "}";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        this.mService.writeToParcel(parcel, i);
        parcel.writeString(this.mId);
        parcel.writeInt(this.mType);
        parcel.writeByte(this.mIsHardwareInput ? (byte) 1 : (byte) 0);
        TextUtils.writeToParcel(this.mLabel, parcel, i);
        parcel.writeParcelable(this.mIconUri, i);
        parcel.writeInt(this.mLabelResId);
        parcel.writeParcelable(this.mIcon, i);
        parcel.writeParcelable(this.mIconStandby, i);
        parcel.writeParcelable(this.mIconDisconnected, i);
        parcel.writeString(this.mSetupActivity);
        parcel.writeByte(this.mCanRecord ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.mCanPauseRecording ? (byte) 1 : (byte) 0);
        parcel.writeInt(this.mTunerCount);
        parcel.writeParcelable(this.mHdmiDeviceInfo, i);
        parcel.writeByte(this.mIsConnectedToHdmiSwitch ? (byte) 1 : (byte) 0);
        parcel.writeInt(this.mHdmiConnectionRelativePosition);
        parcel.writeString(this.mParentId);
        parcel.writeBundle(this.mExtras);
    }

    private Drawable loadServiceIcon(Context context) {
        if (this.mService.serviceInfo.icon == 0 && this.mService.serviceInfo.applicationInfo.icon == 0) {
            return null;
        }
        return this.mService.serviceInfo.loadIcon(context.getPackageManager());
    }

    private TvInputInfo(Parcel in) {
        this.mService = ResolveInfo.CREATOR.createFromParcel(in);
        this.mId = in.readString();
        this.mType = in.readInt();
        this.mIsHardwareInput = in.readByte() == 1;
        this.mLabel = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(in);
        this.mIconUri = (Uri) in.readParcelable(null, Uri.class);
        this.mLabelResId = in.readInt();
        this.mIcon = (Icon) in.readParcelable(null, Icon.class);
        this.mIconStandby = (Icon) in.readParcelable(null, Icon.class);
        this.mIconDisconnected = (Icon) in.readParcelable(null, Icon.class);
        this.mSetupActivity = in.readString();
        this.mCanRecord = in.readByte() == 1;
        this.mCanPauseRecording = in.readByte() == 1;
        this.mTunerCount = in.readInt();
        this.mHdmiDeviceInfo = (HdmiDeviceInfo) in.readParcelable(null, HdmiDeviceInfo.class);
        this.mIsConnectedToHdmiSwitch = in.readByte() == 1;
        this.mHdmiConnectionRelativePosition = in.readInt();
        this.mParentId = in.readString();
        this.mExtras = in.readBundle();
    }

    public static final class Builder {
        private static final String DELIMITER_INFO_IN_ID = "/";
        private static final int LENGTH_HDMI_DEVICE_ID = 2;
        private static final int LENGTH_HDMI_PHYSICAL_ADDRESS = 4;
        private static final String PREFIX_HARDWARE_DEVICE = "HW";
        private static final String PREFIX_HDMI_DEVICE = "HDMI";
        private static final String XML_START_TAG_NAME = "tv-input";
        private static final SparseIntArray sHardwareTypeToTvInputType = new SparseIntArray();
        private Boolean mCanPauseRecording;
        private Boolean mCanRecord;
        private final Context mContext;
        private Bundle mExtras;
        private HdmiDeviceInfo mHdmiDeviceInfo;
        private Icon mIcon;
        private Icon mIconDisconnected;
        private Icon mIconStandby;
        private CharSequence mLabel;
        private int mLabelResId;
        private String mParentId;
        private final ResolveInfo mResolveInfo;
        private String mSetupActivity;
        private Integer mTunerCount;
        private TvInputHardwareInfo mTvInputHardwareInfo;

        static {
            sHardwareTypeToTvInputType.put(1, 1000);
            sHardwareTypeToTvInputType.put(2, 0);
            sHardwareTypeToTvInputType.put(3, 1001);
            sHardwareTypeToTvInputType.put(4, 1002);
            sHardwareTypeToTvInputType.put(5, 1003);
            sHardwareTypeToTvInputType.put(6, 1004);
            sHardwareTypeToTvInputType.put(7, 1005);
            sHardwareTypeToTvInputType.put(8, 1006);
            sHardwareTypeToTvInputType.put(9, 1007);
            sHardwareTypeToTvInputType.put(10, 1008);
        }

        public Builder(Context context, ComponentName component) {
            if (context == null) {
                throw new IllegalArgumentException("context cannot be null.");
            }
            Intent intent = new Intent(TvInputService.SERVICE_INTERFACE).setComponent(component);
            this.mResolveInfo = context.getPackageManager().resolveService(intent, 132);
            if (this.mResolveInfo == null) {
                throw new IllegalArgumentException("Invalid component. Can't find the service.");
            }
            this.mContext = context;
        }

        public Builder(Context context, ResolveInfo resolveInfo) {
            if (context == null) {
                throw new IllegalArgumentException("context cannot be null");
            }
            if (resolveInfo == null) {
                throw new IllegalArgumentException("resolveInfo cannot be null");
            }
            this.mContext = context;
            this.mResolveInfo = resolveInfo;
        }

        @SystemApi
        public Builder setIcon(Icon icon) {
            this.mIcon = icon;
            return this;
        }

        @SystemApi
        public Builder setIcon(Icon icon, int state) {
            if (state == 0) {
                this.mIcon = icon;
            } else if (state == 1) {
                this.mIconStandby = icon;
            } else if (state == 2) {
                this.mIconDisconnected = icon;
            } else {
                throw new IllegalArgumentException("Unknown state: " + state);
            }
            return this;
        }

        @SystemApi
        public Builder setLabel(CharSequence label) {
            if (this.mLabelResId != 0) {
                throw new IllegalStateException("Resource ID for label is already set.");
            }
            this.mLabel = label;
            return this;
        }

        @SystemApi
        public Builder setLabel(int resId) {
            if (this.mLabel != null) {
                throw new IllegalStateException("Label text is already set.");
            }
            this.mLabelResId = resId;
            return this;
        }

        @SystemApi
        public Builder setHdmiDeviceInfo(HdmiDeviceInfo hdmiDeviceInfo) {
            if (this.mTvInputHardwareInfo != null) {
                Log.w(TvInputInfo.TAG, "TvInputHardwareInfo will not be used to build this TvInputInfo");
                this.mTvInputHardwareInfo = null;
            }
            this.mHdmiDeviceInfo = hdmiDeviceInfo;
            return this;
        }

        @SystemApi
        public Builder setParentId(String parentId) {
            this.mParentId = parentId;
            return this;
        }

        @SystemApi
        public Builder setTvInputHardwareInfo(TvInputHardwareInfo tvInputHardwareInfo) {
            if (this.mHdmiDeviceInfo != null) {
                Log.w(TvInputInfo.TAG, "mHdmiDeviceInfo will not be used to build this TvInputInfo");
                this.mHdmiDeviceInfo = null;
            }
            this.mTvInputHardwareInfo = tvInputHardwareInfo;
            return this;
        }

        public Builder setTunerCount(int tunerCount) {
            this.mTunerCount = Integer.valueOf(tunerCount);
            return this;
        }

        public Builder setCanRecord(boolean canRecord) {
            this.mCanRecord = Boolean.valueOf(canRecord);
            return this;
        }

        public Builder setCanPauseRecording(boolean canPauseRecording) {
            this.mCanPauseRecording = Boolean.valueOf(canPauseRecording);
            return this;
        }

        public Builder setExtras(Bundle extras) {
            this.mExtras = extras;
            return this;
        }

        public TvInputInfo build() {
            String id;
            int type;
            ComponentName componentName = new ComponentName(this.mResolveInfo.serviceInfo.packageName, this.mResolveInfo.serviceInfo.name);
            boolean isHardwareInput = false;
            boolean isConnectedToHdmiSwitch = false;
            int hdmiConnectionRelativePosition = 0;
            if (this.mHdmiDeviceInfo != null) {
                id = generateInputId(componentName, this.mHdmiDeviceInfo);
                type = 1007;
                isHardwareInput = true;
                hdmiConnectionRelativePosition = getRelativePosition(this.mContext, this.mHdmiDeviceInfo);
                isConnectedToHdmiSwitch = hdmiConnectionRelativePosition == 2;
            } else if (this.mTvInputHardwareInfo != null) {
                id = generateInputId(componentName, this.mTvInputHardwareInfo);
                type = sHardwareTypeToTvInputType.get(this.mTvInputHardwareInfo.getType(), 0);
                isHardwareInput = true;
                if (this.mTvInputHardwareInfo.getType() == 9) {
                    this.mHdmiDeviceInfo = HdmiDeviceInfo.hardwarePort(65535, this.mTvInputHardwareInfo.getHdmiPortId());
                }
            } else {
                id = generateInputId(componentName);
                type = 0;
            }
            parseServiceMetadata(type);
            return new TvInputInfo(this.mResolveInfo, id, type, isHardwareInput, this.mLabel, this.mLabelResId, this.mIcon, this.mIconStandby, this.mIconDisconnected, this.mSetupActivity, this.mCanRecord == null ? false : this.mCanRecord.booleanValue(), this.mCanPauseRecording == null ? false : this.mCanPauseRecording.booleanValue(), this.mTunerCount != null ? this.mTunerCount.intValue() : 0, this.mHdmiDeviceInfo, isConnectedToHdmiSwitch, hdmiConnectionRelativePosition, this.mParentId, this.mExtras);
        }

        private static String generateInputId(ComponentName name) {
            return name.flattenToShortString();
        }

        private static String generateInputId(ComponentName name, HdmiDeviceInfo hdmiDeviceInfo) {
            return name.flattenToShortString() + String.format(Locale.ENGLISH, "/HDMI%04X%02X", Integer.valueOf(hdmiDeviceInfo.getPhysicalAddress()), Integer.valueOf(hdmiDeviceInfo.getId()));
        }

        private static String generateInputId(ComponentName name, TvInputHardwareInfo tvInputHardwareInfo) {
            return name.flattenToShortString() + DELIMITER_INFO_IN_ID + PREFIX_HARDWARE_DEVICE + tvInputHardwareInfo.getDeviceId();
        }

        private static int getRelativePosition(Context context, HdmiDeviceInfo info) {
            HdmiControlManager hcm = (HdmiControlManager) context.getSystemService(Context.HDMI_CONTROL_SERVICE);
            if (hcm == null) {
                return 0;
            }
            return HdmiUtils.getHdmiAddressRelativePosition(info.getPhysicalAddress(), hcm.getPhysicalAddress());
        }

        private void parseServiceMetadata(int inputType) {
            int type;
            ServiceInfo si = this.mResolveInfo.serviceInfo;
            PackageManager pm = this.mContext.getPackageManager();
            try {
                try {
                    XmlResourceParser parser = si.loadXmlMetaData(pm, TvInputService.SERVICE_META_DATA);
                    try {
                        if (parser == null) {
                            throw new IllegalStateException("No android.media.tv.input meta-data found for " + si.name);
                        }
                        Resources res = pm.getResourcesForApplication(si.applicationInfo);
                        AttributeSet attrs = Xml.asAttributeSet(parser);
                        do {
                            type = parser.next();
                            if (type == 1) {
                                break;
                            }
                        } while (type != 2);
                        String nodeName = parser.getName();
                        if (!XML_START_TAG_NAME.equals(nodeName)) {
                            throw new IllegalStateException("Meta-data does not start with tv-input tag for " + si.name);
                        }
                        TypedArray sa = res.obtainAttributes(attrs, R.styleable.TvInputService);
                        this.mSetupActivity = sa.getString(1);
                        if (this.mCanRecord == null) {
                            this.mCanRecord = Boolean.valueOf(sa.getBoolean(2, false));
                        }
                        if (this.mTunerCount == null && inputType == 0) {
                            this.mTunerCount = Integer.valueOf(sa.getInt(3, 1));
                        }
                        if (this.mCanPauseRecording == null) {
                            this.mCanPauseRecording = Boolean.valueOf(sa.getBoolean(4, false));
                        }
                        sa.recycle();
                        if (parser != null) {
                            parser.close();
                        }
                    } catch (Throwable th) {
                        if (parser != null) {
                            try {
                                parser.close();
                            } catch (Throwable th2) {
                                th.addSuppressed(th2);
                            }
                        }
                        throw th;
                    }
                } catch (IOException | XmlPullParserException e) {
                    throw new IllegalStateException("Failed reading meta-data for " + si.packageName, e);
                }
            } catch (PackageManager.NameNotFoundException e2) {
                throw new IllegalStateException("No resources found for " + si.packageName, e2);
            }
        }
    }

    @SystemApi
    public static final class TvInputSettings {
        private static final String CUSTOM_NAME_SEPARATOR = ",";
        private static final String TV_INPUT_SEPARATOR = ":";

        private TvInputSettings() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static boolean isHidden(Context context, String inputId, int userId) {
            return getHiddenTvInputIds(context, userId).contains(inputId);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static String getCustomLabel(Context context, String inputId, int userId) {
            return getCustomLabels(context, userId).get(inputId);
        }

        @SystemApi
        public static Set<String> getHiddenTvInputIds(Context context, int userId) {
            String hiddenIdsString = Settings.Secure.getStringForUser(context.getContentResolver(), Settings.Secure.TV_INPUT_HIDDEN_INPUTS, userId);
            Set<String> set = new HashSet<>();
            if (TextUtils.isEmpty(hiddenIdsString)) {
                return set;
            }
            String[] ids = hiddenIdsString.split(":");
            for (String id : ids) {
                set.add(Uri.decode(id));
            }
            return set;
        }

        @SystemApi
        public static Map<String, String> getCustomLabels(Context context, int userId) {
            String labelsString = Settings.Secure.getStringForUser(context.getContentResolver(), Settings.Secure.TV_INPUT_CUSTOM_LABELS, userId);
            Map<String, String> map = new HashMap<>();
            if (TextUtils.isEmpty(labelsString)) {
                return map;
            }
            String[] pairs = labelsString.split(":");
            for (String pairString : pairs) {
                String[] pair = pairString.split(",");
                map.put(Uri.decode(pair[0]), Uri.decode(pair[1]));
            }
            return map;
        }

        @SystemApi
        public static void putHiddenTvInputs(Context context, Set<String> hiddenInputIds, int userId) {
            StringBuilder builder = new StringBuilder();
            boolean firstItem = true;
            for (String inputId : hiddenInputIds) {
                ensureValidField(inputId);
                if (firstItem) {
                    firstItem = false;
                } else {
                    builder.append(":");
                }
                builder.append(Uri.encode(inputId));
            }
            Settings.Secure.putStringForUser(context.getContentResolver(), Settings.Secure.TV_INPUT_HIDDEN_INPUTS, builder.toString(), userId);
            TvInputManager tm = (TvInputManager) context.getSystemService(Context.TV_INPUT_SERVICE);
            Iterator<String> it = hiddenInputIds.iterator();
            while (it.hasNext()) {
                TvInputInfo info = tm.getTvInputInfo(it.next());
                if (info != null) {
                    tm.updateTvInputInfo(info);
                }
            }
        }

        @SystemApi
        public static void putCustomLabels(Context context, Map<String, String> customLabels, int userId) {
            StringBuilder builder = new StringBuilder();
            boolean firstItem = true;
            for (Map.Entry<String, String> entry : customLabels.entrySet()) {
                ensureValidField(entry.getKey());
                ensureValidField(entry.getValue());
                if (firstItem) {
                    firstItem = false;
                } else {
                    builder.append(":");
                }
                builder.append(Uri.encode(entry.getKey()));
                builder.append(",");
                builder.append(Uri.encode(entry.getValue()));
            }
            Settings.Secure.putStringForUser(context.getContentResolver(), Settings.Secure.TV_INPUT_CUSTOM_LABELS, builder.toString(), userId);
            TvInputManager tm = (TvInputManager) context.getSystemService(Context.TV_INPUT_SERVICE);
            for (String inputId : customLabels.keySet()) {
                TvInputInfo info = tm.getTvInputInfo(inputId);
                if (info != null) {
                    tm.updateTvInputInfo(info);
                }
            }
        }

        private static void ensureValidField(String value) {
            if (TextUtils.isEmpty(value)) {
                throw new IllegalArgumentException(value + " should not empty ");
            }
        }
    }
}
