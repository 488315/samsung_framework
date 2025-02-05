package android.telephony;

import android.annotation.SystemApi;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.ParcelUuid;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import com.android.internal.R;
import com.android.internal.telephony.util.TelephonyUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/* loaded from: classes4.dex */
public class SubscriptionInfo implements Parcelable {
    public static final Parcelable.Creator<SubscriptionInfo> CREATOR = new Parcelable.Creator<SubscriptionInfo>() { // from class: android.telephony.SubscriptionInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SubscriptionInfo createFromParcel(Parcel source) {
            return new Builder().setId(source.readInt()).setIccId(source.readString()).setSimSlotIndex(source.readInt()).setDisplayName(TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(source)).setCarrierName(TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(source)).setDisplayNameSource(source.readInt()).setIconTint(source.readInt()).setNumber(source.readString()).setDataRoaming(source.readInt()).setMcc(source.readString()).setMnc(source.readString()).setCountryIso(source.readString()).setEmbedded(source.readBoolean()).setNativeAccessRules((UiccAccessRule[]) source.createTypedArray(UiccAccessRule.CREATOR)).setCardString(source.readString()).setCardId(source.readInt()).setPortIndex(source.readInt()).setOpportunistic(source.readBoolean()).setGroupUuid(source.readString8()).setGroupDisabled(source.readBoolean()).setCarrierId(source.readInt()).setProfileClass(source.readInt()).setType(source.readInt()).setEhplmns(source.createStringArray()).setHplmns(source.createStringArray()).setGroupOwner(source.readString()).setCarrierConfigAccessRules((UiccAccessRule[]) source.createTypedArray(UiccAccessRule.CREATOR)).setUiccApplicationsEnabled(source.readBoolean()).setUsageSetting(source.readInt()).setOnlyNonTerrestrialNetwork(source.readBoolean()).setServiceCapabilities(SubscriptionManager.getServiceCapabilitiesSet(source.readInt())).setTransferStatus(source.readInt()).setSatelliteESOSSupported(source.readBoolean()).build();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SubscriptionInfo[] newArray(int size) {
            return new SubscriptionInfo[size];
        }
    };
    private static final int TEXT_SIZE = 16;
    private final boolean mAreUiccApplicationsEnabled;
    private final int mCardId;
    private final String mCardString;
    private final UiccAccessRule[] mCarrierConfigAccessRules;
    private final int mCarrierId;
    private final CharSequence mCarrierName;
    private final String mCountryIso;
    private final int mDataRoaming;
    private final CharSequence mDisplayName;
    private final int mDisplayNameSource;
    private final String[] mEhplmns;
    private final String mGroupOwner;
    private final ParcelUuid mGroupUuid;
    private final String[] mHplmns;
    private final String mIccId;
    private Bitmap mIconBitmap;
    private final int mIconTint;
    private final int mId;
    private final boolean mIsEmbedded;
    private final boolean mIsGroupDisabled;
    private final boolean mIsOnlyNonTerrestrialNetwork;
    private final boolean mIsOpportunistic;
    private final boolean mIsSatelliteESOSSupported;
    private final String mMcc;
    private final String mMnc;
    private final UiccAccessRule[] mNativeAccessRules;
    private final String mNumber;
    private final int mPortIndex;
    private final int mProfileClass;
    private final int mServiceCapabilities;
    private final int mSimSlotIndex;
    private final int mTransferStatus;
    private final int mType;
    private final int mUsageSetting;

    @Deprecated
    public SubscriptionInfo(int id, String iccId, int simSlotIndex, CharSequence displayName, CharSequence carrierName, int nameSource, int iconTint, String number, int roaming, Bitmap icon, String mcc, String mnc, String countryIso, boolean isEmbedded, UiccAccessRule[] nativeAccessRules, String cardString) {
        this(id, iccId, simSlotIndex, displayName, carrierName, nameSource, iconTint, number, roaming, icon, mcc, mnc, countryIso, isEmbedded, nativeAccessRules, cardString, -1, false, null, false, -1, -1, 0, null, null, true);
    }

    @Deprecated
    public SubscriptionInfo(int id, String iccId, int simSlotIndex, CharSequence displayName, CharSequence carrierName, int nameSource, int iconTint, String number, int roaming, Bitmap icon, String mcc, String mnc, String countryIso, boolean isEmbedded, UiccAccessRule[] nativeAccessRules, String cardString, boolean isOpportunistic, String groupUUID, int carrierId, int profileClass) {
        this(id, iccId, simSlotIndex, displayName, carrierName, nameSource, iconTint, number, roaming, icon, mcc, mnc, countryIso, isEmbedded, nativeAccessRules, cardString, -1, isOpportunistic, groupUUID, false, carrierId, profileClass, 0, null, null, true);
    }

    @Deprecated
    public SubscriptionInfo(int id, String iccId, int simSlotIndex, CharSequence displayName, CharSequence carrierName, int nameSource, int iconTint, String number, int roaming, Bitmap icon, String mcc, String mnc, String countryIso, boolean isEmbedded, UiccAccessRule[] nativeAccessRules, String cardString, int cardId, boolean isOpportunistic, String groupUUID, boolean isGroupDisabled, int carrierId, int profileClass, int subType, String groupOwner, UiccAccessRule[] carrierConfigAccessRules, boolean areUiccApplicationsEnabled) {
        this(id, iccId, simSlotIndex, displayName, carrierName, nameSource, iconTint, number, roaming, icon, mcc, mnc, countryIso, isEmbedded, nativeAccessRules, cardString, cardId, isOpportunistic, groupUUID, isGroupDisabled, carrierId, profileClass, subType, groupOwner, carrierConfigAccessRules, areUiccApplicationsEnabled, 0);
    }

    @Deprecated
    public SubscriptionInfo(int id, String iccId, int simSlotIndex, CharSequence displayName, CharSequence carrierName, int displayNameSource, int iconTint, String number, int roaming, Bitmap icon, String mcc, String mnc, String countryIso, boolean isEmbedded, UiccAccessRule[] nativeAccessRules, String cardString, int cardId, boolean isOpportunistic, String groupUUID, boolean isGroupDisabled, int carrierId, int profileClass, int subType, String groupOwner, UiccAccessRule[] carrierConfigAccessRules, boolean areUiccApplicationsEnabled, int portIndex) {
        this(id, iccId, simSlotIndex, displayName, carrierName, displayNameSource, iconTint, number, roaming, icon, mcc, mnc, countryIso, isEmbedded, nativeAccessRules, cardString, cardId, isOpportunistic, groupUUID, isGroupDisabled, carrierId, profileClass, subType, groupOwner, carrierConfigAccessRules, areUiccApplicationsEnabled, portIndex, 0);
    }

    @Deprecated
    public SubscriptionInfo(int id, String iccId, int simSlotIndex, CharSequence displayName, CharSequence carrierName, int nameSource, int iconTint, String number, int roaming, Bitmap icon, String mcc, String mnc, String countryIso, boolean isEmbedded, UiccAccessRule[] nativeAccessRules, String cardString, int cardId, boolean isOpportunistic, String groupUuid, boolean isGroupDisabled, int carrierId, int profileClass, int subType, String groupOwner, UiccAccessRule[] carrierConfigAccessRules, boolean areUiccApplicationsEnabled, int portIndex, int usageSetting) {
        this.mId = id;
        this.mIccId = iccId;
        this.mSimSlotIndex = simSlotIndex;
        this.mDisplayName = displayName;
        this.mCarrierName = carrierName;
        this.mDisplayNameSource = nameSource;
        this.mIconTint = iconTint;
        this.mNumber = number;
        this.mDataRoaming = roaming;
        this.mIconBitmap = icon;
        this.mMcc = TextUtils.emptyIfNull(mcc);
        this.mMnc = TextUtils.emptyIfNull(mnc);
        this.mHplmns = null;
        this.mEhplmns = null;
        this.mCountryIso = TextUtils.emptyIfNull(countryIso);
        this.mIsEmbedded = isEmbedded;
        this.mNativeAccessRules = nativeAccessRules;
        this.mCardString = TextUtils.emptyIfNull(cardString);
        this.mCardId = cardId;
        this.mIsOpportunistic = isOpportunistic;
        this.mGroupUuid = groupUuid != null ? ParcelUuid.fromString(groupUuid) : null;
        this.mIsGroupDisabled = isGroupDisabled;
        this.mCarrierId = carrierId;
        this.mProfileClass = profileClass;
        this.mType = subType;
        this.mGroupOwner = TextUtils.emptyIfNull(groupOwner);
        this.mCarrierConfigAccessRules = carrierConfigAccessRules;
        this.mAreUiccApplicationsEnabled = areUiccApplicationsEnabled;
        this.mPortIndex = portIndex;
        this.mUsageSetting = usageSetting;
        this.mIsOnlyNonTerrestrialNetwork = false;
        this.mServiceCapabilities = 0;
        this.mTransferStatus = 0;
        this.mIsSatelliteESOSSupported = false;
    }

    private SubscriptionInfo(Builder builder) {
        this.mId = builder.mId;
        this.mIccId = builder.mIccId;
        this.mSimSlotIndex = builder.mSimSlotIndex;
        this.mDisplayName = builder.mDisplayName;
        this.mCarrierName = builder.mCarrierName;
        this.mDisplayNameSource = builder.mDisplayNameSource;
        this.mIconTint = builder.mIconTint;
        this.mNumber = builder.mNumber;
        this.mDataRoaming = builder.mDataRoaming;
        this.mIconBitmap = builder.mIconBitmap;
        this.mMcc = builder.mMcc;
        this.mMnc = builder.mMnc;
        this.mEhplmns = builder.mEhplmns;
        this.mHplmns = builder.mHplmns;
        this.mCountryIso = builder.mCountryIso;
        this.mIsEmbedded = builder.mIsEmbedded;
        this.mNativeAccessRules = builder.mNativeAccessRules;
        this.mCardString = builder.mCardString;
        this.mCardId = builder.mCardId;
        this.mIsOpportunistic = builder.mIsOpportunistic;
        this.mGroupUuid = builder.mGroupUuid;
        this.mIsGroupDisabled = builder.mIsGroupDisabled;
        this.mCarrierId = builder.mCarrierId;
        this.mProfileClass = builder.mProfileClass;
        this.mType = builder.mType;
        this.mGroupOwner = builder.mGroupOwner;
        this.mCarrierConfigAccessRules = builder.mCarrierConfigAccessRules;
        this.mAreUiccApplicationsEnabled = builder.mAreUiccApplicationsEnabled;
        this.mPortIndex = builder.mPortIndex;
        this.mUsageSetting = builder.mUsageSetting;
        this.mIsOnlyNonTerrestrialNetwork = builder.mIsOnlyNonTerrestrialNetwork;
        this.mServiceCapabilities = builder.mServiceCapabilities;
        this.mTransferStatus = builder.mTransferStatus;
        this.mIsSatelliteESOSSupported = builder.mIsSatelliteESOSSupported;
    }

    public int getSubscriptionId() {
        return this.mId;
    }

    public String getIccId() {
        return this.mIccId;
    }

    public int getSimSlotIndex() {
        return this.mSimSlotIndex;
    }

    public int getCarrierId() {
        return this.mCarrierId;
    }

    public CharSequence getDisplayName() {
        return this.mDisplayName;
    }

    public CharSequence getCarrierName() {
        return this.mCarrierName;
    }

    public int getDisplayNameSource() {
        return this.mDisplayNameSource;
    }

    public Bitmap createIconBitmap(Context context) {
        if (this.mIconBitmap == null) {
            this.mIconBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_sim_card_multi_24px_clr);
        }
        int width = this.mIconBitmap.getWidth();
        int height = this.mIconBitmap.getHeight();
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        Bitmap workingBitmap = Bitmap.createBitmap(metrics, width, height, this.mIconBitmap.getConfig());
        Canvas canvas = new Canvas(workingBitmap);
        Paint paint = new Paint();
        paint.setColorFilter(new PorterDuffColorFilter(this.mIconTint, PorterDuff.Mode.SRC_ATOP));
        canvas.drawBitmap(this.mIconBitmap, 0.0f, 0.0f, paint);
        paint.setColorFilter(null);
        paint.setAntiAlias(true);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT_FAMILY, 0));
        paint.setColor(-1);
        paint.setTextSize(metrics.density * 16.0f);
        String index = TextUtils.formatSimple("%d", Integer.valueOf(this.mSimSlotIndex + 1));
        Rect textBound = new Rect();
        paint.getTextBounds(index, 0, 1, textBound);
        float xOffset = (width / 2.0f) - textBound.centerX();
        float yOffset = (height / 2.0f) - textBound.centerY();
        canvas.drawText(index, xOffset, yOffset, paint);
        return workingBitmap;
    }

    public int getIconTint() {
        return this.mIconTint;
    }

    @Deprecated
    public String getNumber() {
        return this.mNumber;
    }

    public int getDataRoaming() {
        return this.mDataRoaming;
    }

    @Deprecated
    public int getMcc() {
        try {
            return this.mMcc != null ? Integer.parseInt(this.mMcc) : 0;
        } catch (NumberFormatException e) {
            Log.w(SubscriptionInfo.class.getSimpleName(), "MCC string is not a number");
            return 0;
        }
    }

    @Deprecated
    public int getMnc() {
        try {
            return this.mMnc != null ? Integer.parseInt(this.mMnc) : 0;
        } catch (NumberFormatException e) {
            Log.w(SubscriptionInfo.class.getSimpleName(), "MNC string is not a number");
            return 0;
        }
    }

    public String getMccString() {
        return this.mMcc;
    }

    public String getMncString() {
        return this.mMnc;
    }

    public String getCountryIso() {
        return this.mCountryIso;
    }

    public boolean isEmbedded() {
        return this.mIsEmbedded;
    }

    public boolean isOpportunistic() {
        return this.mIsOpportunistic;
    }

    public boolean isActive() {
        return this.mSimSlotIndex >= 0 || this.mType == 1;
    }

    public ParcelUuid getGroupUuid() {
        return this.mGroupUuid;
    }

    public List<String> getEhplmns() {
        return Collections.unmodifiableList(this.mEhplmns == null ? Collections.emptyList() : Arrays.asList(this.mEhplmns));
    }

    public List<String> getHplmns() {
        return Collections.unmodifiableList(this.mHplmns == null ? Collections.emptyList() : Arrays.asList(this.mHplmns));
    }

    public String getGroupOwner() {
        return this.mGroupOwner;
    }

    @SystemApi
    public int getProfileClass() {
        return this.mProfileClass;
    }

    public int semGetProfileClass() {
        return getProfileClass();
    }

    public int getSubscriptionType() {
        return this.mType;
    }

    @Deprecated
    public boolean canManageSubscription(Context context) {
        return canManageSubscription(context, context.getPackageName());
    }

    @Deprecated
    public boolean canManageSubscription(Context context, String packageName) {
        List<UiccAccessRule> allAccessRules = getAccessRules();
        if (allAccessRules == null) {
            return false;
        }
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 134217728);
            for (UiccAccessRule rule : allAccessRules) {
                if (rule.getCarrierPrivilegeStatus(packageInfo) == 1) {
                    return true;
                }
            }
            return false;
        } catch (PackageManager.NameNotFoundException e) {
            Log.d("SubscriptionInfo", "canManageSubscription: Unknown package: " + packageName, e);
            return false;
        }
    }

    @SystemApi
    public List<UiccAccessRule> getAccessRules() {
        List<UiccAccessRule> merged = new ArrayList<>();
        if (this.mNativeAccessRules != null) {
            merged.addAll(Arrays.asList(this.mNativeAccessRules));
        }
        if (this.mCarrierConfigAccessRules != null) {
            merged.addAll(Arrays.asList(this.mCarrierConfigAccessRules));
        }
        if (merged.isEmpty()) {
            return null;
        }
        return Collections.unmodifiableList(merged);
    }

    public String getCardString() {
        return this.mCardString;
    }

    public int getCardId() {
        return this.mCardId;
    }

    public int getPortIndex() {
        return this.mPortIndex;
    }

    @SystemApi
    public boolean isGroupDisabled() {
        return this.mIsGroupDisabled;
    }

    @SystemApi
    public boolean areUiccApplicationsEnabled() {
        return this.mAreUiccApplicationsEnabled;
    }

    public boolean semAreUiccApplicationsEnabled() {
        return areUiccApplicationsEnabled();
    }

    public int getUsageSetting() {
        return this.mUsageSetting;
    }

    public boolean isOnlyNonTerrestrialNetwork() {
        return this.mIsOnlyNonTerrestrialNetwork;
    }

    public boolean isSatelliteESOSSupported() {
        return this.mIsSatelliteESOSSupported;
    }

    public Set<Integer> getServiceCapabilities() {
        return SubscriptionManager.getServiceCapabilitiesSet(this.mServiceCapabilities);
    }

    @SystemApi
    public int getTransferStatus() {
        return this.mTransferStatus;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mId);
        dest.writeString(this.mIccId);
        dest.writeInt(this.mSimSlotIndex);
        TextUtils.writeToParcel(this.mDisplayName, dest, 0);
        TextUtils.writeToParcel(this.mCarrierName, dest, 0);
        dest.writeInt(this.mDisplayNameSource);
        dest.writeInt(this.mIconTint);
        dest.writeString(this.mNumber);
        dest.writeInt(this.mDataRoaming);
        dest.writeString(this.mMcc);
        dest.writeString(this.mMnc);
        dest.writeString(this.mCountryIso);
        dest.writeBoolean(this.mIsEmbedded);
        dest.writeTypedArray(this.mNativeAccessRules, flags);
        dest.writeString(this.mCardString);
        dest.writeInt(this.mCardId);
        dest.writeInt(this.mPortIndex);
        dest.writeBoolean(this.mIsOpportunistic);
        dest.writeString8(this.mGroupUuid == null ? null : this.mGroupUuid.toString());
        dest.writeBoolean(this.mIsGroupDisabled);
        dest.writeInt(this.mCarrierId);
        dest.writeInt(this.mProfileClass);
        dest.writeInt(this.mType);
        dest.writeStringArray(this.mEhplmns);
        dest.writeStringArray(this.mHplmns);
        dest.writeString(this.mGroupOwner);
        dest.writeTypedArray(this.mCarrierConfigAccessRules, flags);
        dest.writeBoolean(this.mAreUiccApplicationsEnabled);
        dest.writeInt(this.mUsageSetting);
        dest.writeBoolean(this.mIsOnlyNonTerrestrialNetwork);
        dest.writeInt(this.mServiceCapabilities);
        dest.writeInt(this.mTransferStatus);
        dest.writeBoolean(this.mIsSatelliteESOSSupported);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static String getPrintableId(String id) {
        if (id == null) {
            return null;
        }
        if (id.length() > 9 && !TelephonyUtils.IS_DEBUGGABLE) {
            String idToPrint = id.substring(0, 9) + com.android.telephony.Rlog.pii(false, (Object) id.substring(9));
            return idToPrint;
        }
        return id;
    }

    public String toString() {
        String iccIdToPrint = getPrintableId(this.mIccId);
        String cardStringToPrint = getPrintableId(this.mCardString);
        return "[SubscriptionInfo: id=" + this.mId + " iccId=" + iccIdToPrint + " simSlotIndex=" + this.mSimSlotIndex + " portIndex=" + this.mPortIndex + " isEmbedded=" + this.mIsEmbedded + " carrierId=" + this.mCarrierId + " displayName=" + ((Object) this.mDisplayName) + " carrierName=" + ((Object) this.mCarrierName) + " isOpportunistic=" + this.mIsOpportunistic + " groupUuid=" + this.mGroupUuid + " groupOwner=" + this.mGroupOwner + " isGroupDisabled=" + this.mIsGroupDisabled + " displayNameSource=" + SubscriptionManager.displayNameSourceToString(this.mDisplayNameSource) + " iconTint=" + this.mIconTint + " number=" + com.android.telephony.Rlog.pii(TelephonyUtils.IS_DEBUGGABLE, this.mNumber) + " dataRoaming=" + this.mDataRoaming + " mcc=" + this.mMcc + " mnc=" + this.mMnc + " ehplmns=" + Arrays.toString(this.mEhplmns) + " hplmns=" + Arrays.toString(this.mHplmns) + " cardString=" + cardStringToPrint + " cardId=" + this.mCardId + " nativeAccessRules=" + Arrays.toString(this.mNativeAccessRules) + " carrierConfigAccessRules=" + Arrays.toString(this.mCarrierConfigAccessRules) + " countryIso=" + this.mCountryIso + " profileClass=" + this.mProfileClass + " mType=" + SubscriptionManager.subscriptionTypeToString(this.mType) + " areUiccApplicationsEnabled=" + this.mAreUiccApplicationsEnabled + " usageSetting=" + SubscriptionManager.usageSettingToString(this.mUsageSetting) + " isOnlyNonTerrestrialNetwork=" + this.mIsOnlyNonTerrestrialNetwork + " serviceCapabilities=" + SubscriptionManager.getServiceCapabilitiesSet(this.mServiceCapabilities).toString() + " transferStatus=" + this.mTransferStatus + " isSatelliteESOSSupported=" + this.mIsSatelliteESOSSupported + NavigationBarInflaterView.SIZE_MOD_END;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SubscriptionInfo that = (SubscriptionInfo) o;
        if (this.mId == that.mId && this.mSimSlotIndex == that.mSimSlotIndex && this.mDisplayNameSource == that.mDisplayNameSource && this.mIconTint == that.mIconTint && this.mDataRoaming == that.mDataRoaming && this.mIsEmbedded == that.mIsEmbedded && this.mIsOpportunistic == that.mIsOpportunistic && this.mCarrierId == that.mCarrierId && this.mProfileClass == that.mProfileClass && this.mType == that.mType && this.mAreUiccApplicationsEnabled == that.mAreUiccApplicationsEnabled && this.mPortIndex == that.mPortIndex && this.mUsageSetting == that.mUsageSetting && this.mCardId == that.mCardId && this.mIsGroupDisabled == that.mIsGroupDisabled && this.mIccId.equals(that.mIccId) && this.mDisplayName.equals(that.mDisplayName) && this.mCarrierName.equals(that.mCarrierName) && this.mNumber.equals(that.mNumber) && Objects.equals(this.mMcc, that.mMcc) && Objects.equals(this.mMnc, that.mMnc) && Arrays.equals(this.mEhplmns, that.mEhplmns) && Arrays.equals(this.mHplmns, that.mHplmns) && this.mCardString.equals(that.mCardString) && Arrays.equals(this.mNativeAccessRules, that.mNativeAccessRules) && Arrays.equals(this.mCarrierConfigAccessRules, that.mCarrierConfigAccessRules) && Objects.equals(this.mGroupUuid, that.mGroupUuid) && this.mCountryIso.equals(that.mCountryIso) && this.mGroupOwner.equals(that.mGroupOwner) && this.mIsOnlyNonTerrestrialNetwork == that.mIsOnlyNonTerrestrialNetwork && this.mServiceCapabilities == that.mServiceCapabilities && this.mTransferStatus == that.mTransferStatus && this.mIsSatelliteESOSSupported == that.mIsSatelliteESOSSupported) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int result = Objects.hash(Integer.valueOf(this.mId), this.mIccId, Integer.valueOf(this.mSimSlotIndex), this.mDisplayName, this.mCarrierName, Integer.valueOf(this.mDisplayNameSource), Integer.valueOf(this.mIconTint), this.mNumber, Integer.valueOf(this.mDataRoaming), this.mMcc, this.mMnc, Boolean.valueOf(this.mIsEmbedded), this.mCardString, Boolean.valueOf(this.mIsOpportunistic), this.mGroupUuid, this.mCountryIso, Integer.valueOf(this.mCarrierId), Integer.valueOf(this.mProfileClass), Integer.valueOf(this.mType), this.mGroupOwner, Boolean.valueOf(this.mAreUiccApplicationsEnabled), Integer.valueOf(this.mPortIndex), Integer.valueOf(this.mUsageSetting), Integer.valueOf(this.mCardId), Boolean.valueOf(this.mIsGroupDisabled), Boolean.valueOf(this.mIsOnlyNonTerrestrialNetwork), Integer.valueOf(this.mServiceCapabilities), Integer.valueOf(this.mTransferStatus), Boolean.valueOf(this.mIsSatelliteESOSSupported));
        return (((((((result * 31) + Arrays.hashCode(this.mEhplmns)) * 31) + Arrays.hashCode(this.mHplmns)) * 31) + Arrays.hashCode(this.mNativeAccessRules)) * 31) + Arrays.hashCode(this.mCarrierConfigAccessRules);
    }

    public static class Builder {
        private boolean mAreUiccApplicationsEnabled;
        private int mCardId;
        private String mCardString;
        private UiccAccessRule[] mCarrierConfigAccessRules;
        private int mCarrierId;
        private CharSequence mCarrierName;
        private String mCountryIso;
        private int mDataRoaming;
        private CharSequence mDisplayName;
        private int mDisplayNameSource;
        private String[] mEhplmns;
        private String mGroupOwner;
        private ParcelUuid mGroupUuid;
        private String[] mHplmns;
        private String mIccId;
        private Bitmap mIconBitmap;
        private int mIconTint;
        private int mId;
        private boolean mIsEmbedded;
        private boolean mIsGroupDisabled;
        private boolean mIsOnlyNonTerrestrialNetwork;
        private boolean mIsOpportunistic;
        private boolean mIsSatelliteESOSSupported;
        private String mMcc;
        private String mMnc;
        private UiccAccessRule[] mNativeAccessRules;
        private String mNumber;
        private int mPortIndex;
        private int mProfileClass;
        private int mServiceCapabilities;
        private int mSimSlotIndex;
        private int mTransferStatus;
        private int mType;
        private int mUsageSetting;

        public Builder() {
            this.mId = -1;
            this.mIccId = "";
            this.mSimSlotIndex = -1;
            this.mDisplayName = "";
            this.mCarrierName = "";
            this.mDisplayNameSource = -1;
            this.mIconTint = 0;
            this.mNumber = "";
            this.mDataRoaming = 0;
            this.mIconBitmap = null;
            this.mMcc = null;
            this.mMnc = null;
            this.mEhplmns = new String[0];
            this.mHplmns = new String[0];
            this.mCountryIso = "";
            this.mIsEmbedded = false;
            this.mNativeAccessRules = null;
            this.mCardString = "";
            this.mCardId = -2;
            this.mIsOpportunistic = false;
            this.mGroupUuid = null;
            this.mIsGroupDisabled = false;
            this.mCarrierId = -1;
            this.mProfileClass = -1;
            this.mType = 0;
            this.mGroupOwner = "";
            this.mCarrierConfigAccessRules = null;
            this.mAreUiccApplicationsEnabled = true;
            this.mPortIndex = -1;
            this.mUsageSetting = -1;
            this.mIsOnlyNonTerrestrialNetwork = false;
            this.mTransferStatus = 0;
            this.mServiceCapabilities = 0;
            this.mIsSatelliteESOSSupported = false;
        }

        public Builder(SubscriptionInfo info) {
            this.mId = -1;
            this.mIccId = "";
            this.mSimSlotIndex = -1;
            this.mDisplayName = "";
            this.mCarrierName = "";
            this.mDisplayNameSource = -1;
            this.mIconTint = 0;
            this.mNumber = "";
            this.mDataRoaming = 0;
            this.mIconBitmap = null;
            this.mMcc = null;
            this.mMnc = null;
            this.mEhplmns = new String[0];
            this.mHplmns = new String[0];
            this.mCountryIso = "";
            this.mIsEmbedded = false;
            this.mNativeAccessRules = null;
            this.mCardString = "";
            this.mCardId = -2;
            this.mIsOpportunistic = false;
            this.mGroupUuid = null;
            this.mIsGroupDisabled = false;
            this.mCarrierId = -1;
            this.mProfileClass = -1;
            this.mType = 0;
            this.mGroupOwner = "";
            this.mCarrierConfigAccessRules = null;
            this.mAreUiccApplicationsEnabled = true;
            this.mPortIndex = -1;
            this.mUsageSetting = -1;
            this.mIsOnlyNonTerrestrialNetwork = false;
            this.mTransferStatus = 0;
            this.mServiceCapabilities = 0;
            this.mIsSatelliteESOSSupported = false;
            this.mId = info.mId;
            this.mIccId = info.mIccId;
            this.mSimSlotIndex = info.mSimSlotIndex;
            this.mDisplayName = info.mDisplayName;
            this.mCarrierName = info.mCarrierName;
            this.mDisplayNameSource = info.mDisplayNameSource;
            this.mIconTint = info.mIconTint;
            this.mNumber = info.mNumber;
            this.mDataRoaming = info.mDataRoaming;
            this.mIconBitmap = info.mIconBitmap;
            this.mMcc = info.mMcc;
            this.mMnc = info.mMnc;
            this.mEhplmns = info.mEhplmns;
            this.mHplmns = info.mHplmns;
            this.mCountryIso = info.mCountryIso;
            this.mIsEmbedded = info.mIsEmbedded;
            this.mNativeAccessRules = info.mNativeAccessRules;
            this.mCardString = info.mCardString;
            this.mCardId = info.mCardId;
            this.mIsOpportunistic = info.mIsOpportunistic;
            this.mGroupUuid = info.mGroupUuid;
            this.mIsGroupDisabled = info.mIsGroupDisabled;
            this.mCarrierId = info.mCarrierId;
            this.mProfileClass = info.mProfileClass;
            this.mType = info.mType;
            this.mGroupOwner = info.mGroupOwner;
            this.mCarrierConfigAccessRules = info.mCarrierConfigAccessRules;
            this.mAreUiccApplicationsEnabled = info.mAreUiccApplicationsEnabled;
            this.mPortIndex = info.mPortIndex;
            this.mUsageSetting = info.mUsageSetting;
            this.mIsOnlyNonTerrestrialNetwork = info.mIsOnlyNonTerrestrialNetwork;
            this.mServiceCapabilities = info.mServiceCapabilities;
            this.mTransferStatus = info.mTransferStatus;
            this.mIsSatelliteESOSSupported = info.mIsSatelliteESOSSupported;
        }

        public Builder setId(int id) {
            this.mId = id;
            return this;
        }

        public Builder setIccId(String iccId) {
            this.mIccId = TextUtils.emptyIfNull(iccId);
            return this;
        }

        public Builder setSimSlotIndex(int simSlotIndex) {
            this.mSimSlotIndex = simSlotIndex;
            return this;
        }

        public Builder setDisplayName(CharSequence displayName) {
            this.mDisplayName = displayName == null ? "" : displayName;
            return this;
        }

        public Builder setCarrierName(CharSequence carrierName) {
            this.mCarrierName = carrierName == null ? "" : carrierName;
            return this;
        }

        public Builder setDisplayNameSource(int displayNameSource) {
            this.mDisplayNameSource = displayNameSource;
            return this;
        }

        public Builder setIconTint(int iconTint) {
            this.mIconTint = iconTint;
            return this;
        }

        public Builder setNumber(String number) {
            this.mNumber = TextUtils.emptyIfNull(number);
            return this;
        }

        public Builder setDataRoaming(int dataRoaming) {
            this.mDataRoaming = dataRoaming;
            return this;
        }

        public Builder setIcon(Bitmap iconBitmap) {
            this.mIconBitmap = iconBitmap;
            return this;
        }

        public Builder setMcc(String mcc) {
            this.mMcc = mcc;
            return this;
        }

        public Builder setMnc(String mnc) {
            this.mMnc = mnc;
            return this;
        }

        public Builder setEhplmns(String[] ehplmns) {
            this.mEhplmns = ehplmns == null ? new String[0] : ehplmns;
            return this;
        }

        public Builder setHplmns(String[] hplmns) {
            this.mHplmns = hplmns == null ? new String[0] : hplmns;
            return this;
        }

        public Builder setCountryIso(String countryIso) {
            this.mCountryIso = TextUtils.emptyIfNull(countryIso);
            return this;
        }

        public Builder setEmbedded(boolean isEmbedded) {
            this.mIsEmbedded = isEmbedded;
            return this;
        }

        public Builder setNativeAccessRules(UiccAccessRule[] nativeAccessRules) {
            this.mNativeAccessRules = nativeAccessRules;
            return this;
        }

        public Builder setCardString(String cardString) {
            this.mCardString = TextUtils.emptyIfNull(cardString);
            return this;
        }

        public Builder setCardId(int cardId) {
            this.mCardId = cardId;
            return this;
        }

        public Builder setOpportunistic(boolean isOpportunistic) {
            this.mIsOpportunistic = isOpportunistic;
            return this;
        }

        public Builder setGroupUuid(String groupUuid) {
            this.mGroupUuid = TextUtils.isEmpty(groupUuid) ? null : ParcelUuid.fromString(groupUuid);
            return this;
        }

        public Builder setGroupDisabled(boolean isGroupDisabled) {
            this.mIsGroupDisabled = isGroupDisabled;
            return this;
        }

        public Builder setCarrierId(int carrierId) {
            this.mCarrierId = carrierId;
            return this;
        }

        public Builder setProfileClass(int profileClass) {
            this.mProfileClass = profileClass;
            return this;
        }

        public Builder setType(int type) {
            this.mType = type;
            return this;
        }

        public Builder setGroupOwner(String groupOwner) {
            this.mGroupOwner = TextUtils.emptyIfNull(groupOwner);
            return this;
        }

        public Builder setCarrierConfigAccessRules(UiccAccessRule[] carrierConfigAccessRules) {
            this.mCarrierConfigAccessRules = carrierConfigAccessRules;
            return this;
        }

        public Builder setUiccApplicationsEnabled(boolean uiccApplicationsEnabled) {
            this.mAreUiccApplicationsEnabled = uiccApplicationsEnabled;
            return this;
        }

        public Builder setPortIndex(int portIndex) {
            this.mPortIndex = portIndex;
            return this;
        }

        public Builder setUsageSetting(int usageSetting) {
            this.mUsageSetting = usageSetting;
            return this;
        }

        public Builder setOnlyNonTerrestrialNetwork(boolean isOnlyNonTerrestrialNetwork) {
            this.mIsOnlyNonTerrestrialNetwork = isOnlyNonTerrestrialNetwork;
            return this;
        }

        public Builder setServiceCapabilities(Set<Integer> capabilities) {
            int combinedCapabilities = 0;
            Iterator<Integer> it = capabilities.iterator();
            while (it.hasNext()) {
                int capability = it.next().intValue();
                if (capability < 1 || capability > 3) {
                    throw new IllegalArgumentException("Invalid service capability value: " + capability);
                }
                combinedCapabilities |= SubscriptionManager.serviceCapabilityToBitmask(capability);
            }
            this.mServiceCapabilities = combinedCapabilities;
            return this;
        }

        public Builder setTransferStatus(int status) {
            this.mTransferStatus = status;
            return this;
        }

        public Builder setSatelliteESOSSupported(boolean isSatelliteESOSSupported) {
            this.mIsSatelliteESOSSupported = isSatelliteESOSSupported;
            return this;
        }

        public SubscriptionInfo build() {
            return new SubscriptionInfo(this);
        }
    }
}
