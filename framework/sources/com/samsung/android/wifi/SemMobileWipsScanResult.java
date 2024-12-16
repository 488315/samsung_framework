package com.samsung.android.wifi;

import android.os.Parcel;
import android.os.Parcelable;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/* loaded from: classes6.dex */
public final class SemMobileWipsScanResult implements Parcelable {
    public static final int CHANNEL_WIDTH_160MHZ = 3;
    public static final int CHANNEL_WIDTH_20MHZ = 0;
    public static final int CHANNEL_WIDTH_40MHZ = 1;
    public static final int CHANNEL_WIDTH_80MHZ = 2;
    public static final int CHANNEL_WIDTH_80MHZ_PLUS_MHZ = 4;
    public static final int CIPHER_CCMP = 3;
    public static final int CIPHER_GCMP_256 = 4;
    public static final int CIPHER_NONE = 0;
    public static final int CIPHER_NO_GROUP_ADDRESSED = 1;
    public static final int CIPHER_SMS4 = 5;
    public static final int CIPHER_TKIP = 2;
    public static final Parcelable.Creator<SemMobileWipsScanResult> CREATOR = new Parcelable.Creator<SemMobileWipsScanResult>() { // from class: com.samsung.android.wifi.SemMobileWipsScanResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemMobileWipsScanResult createFromParcel(Parcel in) {
            SemMobileWipsWifiSsid wifiSsid = null;
            if (in.readInt() == 1) {
                SemMobileWipsWifiSsid wifiSsid2 = SemMobileWipsWifiSsid.CREATOR.createFromParcel(in);
                wifiSsid = wifiSsid2;
            }
            SemMobileWipsScanResult sr = new SemMobileWipsScanResult(wifiSsid, in.readString(), in.readString(), in.readLong(), in.readInt(), in.readString(), in.readInt(), in.readInt(), in.readLong(), in.readInt(), in.readInt(), in.readInt(), in.readInt(), in.readInt(), false);
            sr.mWifiStandard = in.readInt();
            sr.seen = in.readLong();
            sr.untrusted = in.readInt() != 0;
            sr.numUsage = in.readInt();
            sr.venueName = in.readString();
            sr.operatorFriendlyName = in.readString();
            sr.flags = in.readLong();
            sr.informationElements = (InformationElement[]) in.createTypedArray(InformationElement.CREATOR);
            int n = in.readInt();
            if (n != 0) {
                sr.anqpLines = new ArrayList();
                for (int i = 0; i < n; i++) {
                    sr.anqpLines.add(in.readString());
                }
            }
            int n2 = in.readInt();
            if (n2 != 0) {
                sr.anqpElements = new AnqpInformationElement[n2];
                for (int i2 = 0; i2 < n2; i2++) {
                    int vendorId = in.readInt();
                    int elementId = in.readInt();
                    int len = in.readInt();
                    byte[] payload = new byte[len];
                    in.readByteArray(payload);
                    sr.anqpElements[i2] = new AnqpInformationElement(vendorId, elementId, payload);
                }
            }
            int n3 = in.readInt();
            if (n3 != 0) {
                sr.radioChainInfos = new RadioChainInfo[n3];
                for (int i3 = 0; i3 < n3; i3++) {
                    sr.radioChainInfos[i3] = new RadioChainInfo();
                    sr.radioChainInfos[i3].id = in.readInt();
                    sr.radioChainInfos[i3].level = in.readInt();
                }
            }
            sr.ifaceName = in.readString();
            return sr;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemMobileWipsScanResult[] newArray(int size) {
            return new SemMobileWipsScanResult[size];
        }
    };
    public static final long FLAG_80211mc_RESPONDER = 2;
    public static final long FLAG_PASSPOINT_NETWORK = 1;
    public static final int KEY_MGMT_EAP = 2;
    public static final int KEY_MGMT_EAP_SHA256 = 6;
    public static final int KEY_MGMT_EAP_SUITE_B_192 = 10;
    public static final int KEY_MGMT_FILS_SHA256 = 15;
    public static final int KEY_MGMT_FILS_SHA384 = 16;
    public static final int KEY_MGMT_FT_EAP = 4;
    public static final int KEY_MGMT_FT_PSK = 3;
    public static final int KEY_MGMT_FT_SAE = 11;
    public static final int KEY_MGMT_NONE = 0;
    public static final int KEY_MGMT_OSEN = 7;
    public static final int KEY_MGMT_OWE = 9;
    public static final int KEY_MGMT_OWE_TRANSITION = 12;
    public static final int KEY_MGMT_PSK = 1;
    public static final int KEY_MGMT_PSK_SHA256 = 5;
    public static final int KEY_MGMT_SAE = 8;
    public static final int KEY_MGMT_WAPI_CERT = 14;
    public static final int KEY_MGMT_WAPI_PSK = 13;
    public static final int PROTOCOL_NONE = 0;
    public static final int PROTOCOL_OSEN = 3;
    public static final int PROTOCOL_RSN = 2;
    public static final int PROTOCOL_WAPI = 4;
    public static final int PROTOCOL_WPA = 1;
    public static final int UNSPECIFIED = -1;
    public static final int WIFI_STANDARD_11AC = 5;
    public static final int WIFI_STANDARD_11AX = 6;
    public static final int WIFI_STANDARD_11N = 4;
    public static final int WIFI_STANDARD_LEGACY = 1;
    public static final int WIFI_STANDARD_UNKNOWN = 0;
    public String BSSID;
    public String SSID;
    public int anqpDomainId;
    public AnqpInformationElement[] anqpElements;
    public List<String> anqpLines;
    public String capabilities;
    public int centerFreq0;
    public int centerFreq1;
    public int channelWidth;
    public int distanceCm;
    public int distanceSdCm;
    public long flags;
    public int frequency;
    public long hessid;
    public String ifaceName;
    public InformationElement[] informationElements;
    public boolean is80211McRTTResponder;
    public int level;
    private int mWifiStandard;
    public int numUsage;
    public CharSequence operatorFriendlyName;
    public RadioChainInfo[] radioChainInfos;
    public long seen;
    public long timestamp;
    public boolean untrusted;
    public CharSequence venueName;
    public SemMobileWipsWifiSsid wifiSsid;

    public SemMobileWipsScanResult(SemMobileWipsWifiSsid wifiSsid, String BSSID, long hessid, int anqpDomainId, byte[] osuProviders, String caps, int level, int frequency, long tsf) {
        this.wifiSsid = wifiSsid;
        this.SSID = wifiSsid != null ? wifiSsid.toString() : "<unknown ssid>";
        this.BSSID = BSSID;
        this.hessid = hessid;
        this.anqpDomainId = anqpDomainId;
        if (osuProviders != null) {
            this.anqpElements = new AnqpInformationElement[1];
            this.anqpElements[0] = new AnqpInformationElement(5271450, 8, osuProviders);
        }
        this.capabilities = caps;
        this.level = level;
        this.frequency = frequency;
        this.timestamp = tsf;
        this.distanceCm = -1;
        this.distanceSdCm = -1;
        this.channelWidth = -1;
        this.centerFreq0 = -1;
        this.centerFreq1 = -1;
        this.flags = 0L;
        this.radioChainInfos = null;
        this.mWifiStandard = 0;
    }

    public SemMobileWipsScanResult(SemMobileWipsWifiSsid wifiSsid, String BSSID, String caps, int level, int frequency, long tsf, int distCm, int distSdCm) {
        this.wifiSsid = wifiSsid;
        this.SSID = wifiSsid != null ? wifiSsid.toString() : "<unknown ssid>";
        this.BSSID = BSSID;
        this.capabilities = caps;
        this.level = level;
        this.frequency = frequency;
        this.timestamp = tsf;
        this.distanceCm = distCm;
        this.distanceSdCm = distSdCm;
        this.channelWidth = -1;
        this.centerFreq0 = -1;
        this.centerFreq1 = -1;
        this.flags = 0L;
        this.radioChainInfos = null;
        this.mWifiStandard = 0;
    }

    public SemMobileWipsScanResult(String Ssid, String BSSID, long hessid, int anqpDomainId, String caps, int level, int frequency, long tsf, int distCm, int distSdCm, int channelWidth, int centerFreq0, int centerFreq1, boolean is80211McRTTResponder) {
        this.SSID = Ssid;
        this.BSSID = BSSID;
        this.hessid = hessid;
        this.anqpDomainId = anqpDomainId;
        this.capabilities = caps;
        this.level = level;
        this.frequency = frequency;
        this.timestamp = tsf;
        this.distanceCm = distCm;
        this.distanceSdCm = distSdCm;
        this.channelWidth = channelWidth;
        this.centerFreq0 = centerFreq0;
        this.centerFreq1 = centerFreq1;
        if (is80211McRTTResponder) {
            this.flags = 2L;
        } else {
            this.flags = 0L;
        }
        this.radioChainInfos = null;
        this.mWifiStandard = 0;
    }

    public SemMobileWipsScanResult(SemMobileWipsWifiSsid wifiSsid, String Ssid, String BSSID, long hessid, int anqpDomainId, String caps, int level, int frequency, long tsf, int distCm, int distSdCm, int channelWidth, int centerFreq0, int centerFreq1, boolean is80211McRTTResponder) {
        this(Ssid, BSSID, hessid, anqpDomainId, caps, level, frequency, tsf, distCm, distSdCm, channelWidth, centerFreq0, centerFreq1, is80211McRTTResponder);
        this.wifiSsid = wifiSsid;
    }

    public SemMobileWipsScanResult(SemMobileWipsScanResult source) {
        if (source != null) {
            this.wifiSsid = source.wifiSsid;
            this.SSID = source.SSID;
            this.BSSID = source.BSSID;
            this.hessid = source.hessid;
            this.anqpDomainId = source.anqpDomainId;
            this.informationElements = source.informationElements;
            this.anqpElements = source.anqpElements;
            this.capabilities = source.capabilities;
            this.level = source.level;
            this.frequency = source.frequency;
            this.channelWidth = source.channelWidth;
            this.centerFreq0 = source.centerFreq0;
            this.centerFreq1 = source.centerFreq1;
            this.timestamp = source.timestamp;
            this.distanceCm = source.distanceCm;
            this.distanceSdCm = source.distanceSdCm;
            this.seen = source.seen;
            this.untrusted = source.untrusted;
            this.numUsage = source.numUsage;
            this.venueName = source.venueName;
            this.operatorFriendlyName = source.operatorFriendlyName;
            this.flags = source.flags;
            this.radioChainInfos = source.radioChainInfos;
            this.mWifiStandard = source.mWifiStandard;
            this.ifaceName = source.ifaceName;
        }
    }

    public SemMobileWipsScanResult() {
    }

    private static String wifiStandardToString(int standard) {
        switch (standard) {
            case 0:
                return "unknown";
            case 1:
                return "legacy";
            case 2:
            case 3:
            default:
                return null;
            case 4:
                return "11n";
            case 5:
                return "11ac";
            case 6:
                return "11ax";
        }
    }

    public static boolean is24GHz(int freq) {
        return freq > 2400 && freq < 2500;
    }

    public static boolean is5GHz(int freq) {
        return freq > 4900 && freq < 5900;
    }

    public static boolean is6GHz(int freq) {
        return freq > 5925 && freq < 7125;
    }

    public int getWifiStandard() {
        return this.mWifiStandard;
    }

    public void setWifiStandard(int standard) {
        this.mWifiStandard = standard;
    }

    public void setFlag(long flag) {
        this.flags |= flag;
    }

    public void clearFlag(long flag) {
        this.flags &= ~flag;
    }

    public boolean is80211mcResponder() {
        return (this.flags & 2) != 0;
    }

    public boolean isPasspointNetwork() {
        return (this.flags & 1) != 0;
    }

    public boolean is24GHz() {
        return is24GHz(this.frequency);
    }

    public boolean is5GHz() {
        return is5GHz(this.frequency);
    }

    public boolean is6GHz() {
        return is6GHz(this.frequency);
    }

    public List<InformationElement> getInformationElements() {
        return Collections.unmodifiableList(Arrays.asList(this.informationElements));
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("SSID: ").append(this.wifiSsid == null ? "<unknown ssid>" : this.wifiSsid).append(", BSSID: ").append(this.BSSID == null ? "<none>" : this.BSSID).append(", capabilities: ").append(this.capabilities == null ? "<none>" : this.capabilities).append(", level: ").append(this.level).append(", frequency: ").append(this.frequency).append(", timestamp: ").append(this.timestamp);
        sb.append(", distance: ").append(this.distanceCm != -1 ? Integer.valueOf(this.distanceCm) : "?").append("(cm)");
        sb.append(", distanceSd: ").append(this.distanceSdCm != -1 ? Integer.valueOf(this.distanceSdCm) : "?").append("(cm)");
        sb.append(", passpoint: ");
        sb.append((this.flags & 1) != 0 ? "yes" : "no");
        sb.append(", ChannelBandwidth: ").append(this.channelWidth);
        sb.append(", centerFreq0: ").append(this.centerFreq0);
        sb.append(", centerFreq1: ").append(this.centerFreq1);
        sb.append(", standard: ").append(wifiStandardToString(this.mWifiStandard));
        sb.append(", 80211mcResponder: ");
        sb.append((this.flags & 2) != 0 ? "is supported" : "is not supported");
        sb.append(", Radio Chain Infos: ").append(Arrays.toString(this.radioChainInfos));
        sb.append(", interface name: ").append(this.ifaceName);
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        if (this.wifiSsid != null) {
            parcel.writeInt(1);
            this.wifiSsid.writeToParcel(parcel, i);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeString(this.SSID);
        parcel.writeString(this.BSSID);
        parcel.writeLong(this.hessid);
        parcel.writeInt(this.anqpDomainId);
        parcel.writeString(this.capabilities);
        parcel.writeInt(this.level);
        parcel.writeInt(this.frequency);
        parcel.writeLong(this.timestamp);
        parcel.writeInt(this.distanceCm);
        parcel.writeInt(this.distanceSdCm);
        parcel.writeInt(this.channelWidth);
        parcel.writeInt(this.centerFreq0);
        parcel.writeInt(this.centerFreq1);
        parcel.writeInt(this.mWifiStandard);
        parcel.writeLong(this.seen);
        parcel.writeInt(this.untrusted ? 1 : 0);
        parcel.writeInt(this.numUsage);
        parcel.writeString(this.venueName != null ? this.venueName.toString() : "");
        parcel.writeString(this.operatorFriendlyName != null ? this.operatorFriendlyName.toString() : "");
        parcel.writeLong(this.flags);
        parcel.writeTypedArray(this.informationElements, i);
        if (this.anqpLines != null) {
            parcel.writeInt(this.anqpLines.size());
            for (int i2 = 0; i2 < this.anqpLines.size(); i2++) {
                parcel.writeString(this.anqpLines.get(i2));
            }
        } else {
            parcel.writeInt(0);
        }
        if (this.anqpElements != null) {
            parcel.writeInt(this.anqpElements.length);
            for (AnqpInformationElement anqpInformationElement : this.anqpElements) {
                parcel.writeInt(anqpInformationElement.getVendorId());
                parcel.writeInt(anqpInformationElement.getElementId());
                parcel.writeInt(anqpInformationElement.getPayload().length);
                parcel.writeByteArray(anqpInformationElement.getPayload());
            }
        } else {
            parcel.writeInt(0);
        }
        if (this.radioChainInfos != null) {
            parcel.writeInt(this.radioChainInfos.length);
            for (int i3 = 0; i3 < this.radioChainInfos.length; i3++) {
                parcel.writeInt(this.radioChainInfos[i3].id);
                parcel.writeInt(this.radioChainInfos[i3].level);
            }
        } else {
            parcel.writeInt(0);
        }
        parcel.writeString(this.ifaceName != null ? this.ifaceName.toString() : "");
    }

    public static class RadioChainInfo {
        public int id;
        public int level;

        public String toString() {
            return "RadioChainInfo: id=" + this.id + ", level=" + this.level;
        }

        public boolean equals(Object otherObj) {
            if (this == otherObj) {
                return true;
            }
            if (!(otherObj instanceof RadioChainInfo)) {
                return false;
            }
            RadioChainInfo other = (RadioChainInfo) otherObj;
            return this.id == other.id && this.level == other.level;
        }

        public int hashCode() {
            return Objects.hash(Integer.valueOf(this.id), Integer.valueOf(this.level));
        }
    }

    public static class AnqpInformationElement {
        public static final int ANQP_3GPP_NETWORK = 264;
        public static final int ANQP_CAPABILITY_LIST = 257;
        public static final int ANQP_CIVIC_LOC = 266;
        public static final int ANQP_DOM_NAME = 268;
        public static final int ANQP_EMERGENCY_ALERT = 269;
        public static final int ANQP_EMERGENCY_NAI = 271;
        public static final int ANQP_EMERGENCY_NUMBER = 259;
        public static final int ANQP_GEO_LOC = 265;
        public static final int ANQP_IP_ADDR_AVAILABILITY = 262;
        public static final int ANQP_LOC_URI = 267;
        public static final int ANQP_NAI_REALM = 263;
        public static final int ANQP_NEIGHBOR_REPORT = 272;
        public static final int ANQP_NWK_AUTH_TYPE = 260;
        public static final int ANQP_QUERY_LIST = 256;
        public static final int ANQP_ROAMING_CONSORTIUM = 261;
        public static final int ANQP_TDLS_CAP = 270;
        public static final int ANQP_VENDOR_SPEC = 56797;
        public static final int ANQP_VENUE_NAME = 258;
        public static final int HOTSPOT20_VENDOR_ID = 5271450;
        public static final int HS_CAPABILITY_LIST = 2;
        public static final int HS_CONN_CAPABILITY = 5;
        public static final int HS_FRIENDLY_NAME = 3;
        public static final int HS_ICON_FILE = 11;
        public static final int HS_ICON_REQUEST = 10;
        public static final int HS_NAI_HOME_REALM_QUERY = 6;
        public static final int HS_OPERATING_CLASS = 7;
        public static final int HS_OSU_PROVIDERS = 8;
        public static final int HS_QUERY_LIST = 1;
        public static final int HS_WAN_METRICS = 4;
        private final int mElementId;
        private final byte[] mPayload;
        private final int mVendorId;

        public AnqpInformationElement(int vendorId, int elementId, byte[] payload) {
            this.mVendorId = vendorId;
            this.mElementId = elementId;
            this.mPayload = payload;
        }

        public int getVendorId() {
            return this.mVendorId;
        }

        public int getElementId() {
            return this.mElementId;
        }

        public byte[] getPayload() {
            return this.mPayload;
        }
    }

    public static class InformationElement implements Parcelable {
        public static final Parcelable.Creator<InformationElement> CREATOR = new Parcelable.Creator<InformationElement>() { // from class: com.samsung.android.wifi.SemMobileWipsScanResult.InformationElement.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public InformationElement createFromParcel(Parcel in) {
                InformationElement informationElement = new InformationElement();
                informationElement.id = in.readInt();
                informationElement.idExt = in.readInt();
                informationElement.bytes = in.createByteArray();
                return informationElement;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public InformationElement[] newArray(int size) {
                return new InformationElement[size];
            }
        };
        public static final int EID_BSS_LOAD = 11;
        public static final int EID_ERP = 42;
        public static final int EID_EXTENDED_CAPS = 127;
        public static final int EID_EXTENDED_SUPPORTED_RATES = 50;
        public static final int EID_EXTENSION_PRESENT = 255;
        public static final int EID_EXT_HE_CAPABILITIES = 35;
        public static final int EID_EXT_HE_OPERATION = 36;
        public static final int EID_HT_CAPABILITIES = 45;
        public static final int EID_HT_OPERATION = 61;
        public static final int EID_INTERWORKING = 107;
        public static final int EID_ROAMING_CONSORTIUM = 111;
        public static final int EID_RSN = 48;
        public static final int EID_SSID = 0;
        public static final int EID_SUPPORTED_RATES = 1;
        public static final int EID_TIM = 5;
        public static final int EID_VHT_CAPABILITIES = 191;
        public static final int EID_VHT_OPERATION = 192;
        public static final int EID_VSA = 221;
        public byte[] bytes;
        public int id;
        public int idExt;

        public InformationElement() {
        }

        public InformationElement(InformationElement rhs) {
            if (rhs != null) {
                this.id = rhs.id;
                this.idExt = rhs.idExt;
                this.bytes = (byte[]) rhs.bytes.clone();
            }
        }

        public int getId() {
            return this.id;
        }

        public int getIdExt() {
            return this.idExt;
        }

        public ByteBuffer getBytes() {
            return ByteBuffer.wrap(this.bytes).asReadOnlyBuffer();
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeInt(this.idExt);
            dest.writeByteArray(this.bytes);
        }

        public boolean equals(Object that) {
            if (this == that) {
                return true;
            }
            if (!(that instanceof InformationElement)) {
                return false;
            }
            InformationElement thatIE = (InformationElement) that;
            return this.id == thatIE.id && this.idExt == thatIE.idExt && Arrays.equals(this.bytes, thatIE.bytes);
        }

        public int hashCode() {
            return Objects.hash(Integer.valueOf(this.id), Integer.valueOf(this.idExt), Integer.valueOf(Arrays.hashCode(this.bytes)));
        }
    }
}
