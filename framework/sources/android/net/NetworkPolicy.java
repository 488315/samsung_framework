package android.net;

import android.net.NetworkTemplate;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.BackupUtils;
import android.util.Log;
import android.util.Range;
import android.util.RecurrenceRule;
import com.android.internal.util.Preconditions;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

/* loaded from: classes3.dex */
public class NetworkPolicy implements Parcelable, Comparable<NetworkPolicy> {
    public static final int CYCLE_NONE = -1;
    private static final long DEFAULT_MTU = 1500;
    public static final long LIMIT_DISABLED = -1;
    public static final long SNOOZE_NEVER = -1;
    private static final int TEMPLATE_BACKUP_VERSION_1_INIT = 1;
    private static final int TEMPLATE_BACKUP_VERSION_2_UNSUPPORTED = 2;
    private static final int TEMPLATE_BACKUP_VERSION_3_SUPPORT_CARRIER_TEMPLATE = 3;
    private static final int TEMPLATE_BACKUP_VERSION_LATEST = 3;
    private static final int VERSION_INIT = 1;
    private static final int VERSION_RAPID = 3;
    private static final int VERSION_RULE = 2;
    public static final long WARNING_DISABLED = -1;
    public RecurrenceRule cycleRule;
    public boolean inferred;
    public long lastLimitSnooze;
    public long lastRapidSnooze;
    public long lastWarningSnooze;
    public long limitBytes;

    @Deprecated
    public boolean metered;
    public NetworkTemplate template;
    public long warningBytes;
    private static final String TAG = NetworkPolicy.class.getSimpleName();
    public static final Parcelable.Creator<NetworkPolicy> CREATOR = new Parcelable.Creator<NetworkPolicy>() { // from class: android.net.NetworkPolicy.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NetworkPolicy createFromParcel(Parcel in) {
            return new NetworkPolicy(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NetworkPolicy[] newArray(int size) {
            return new NetworkPolicy[size];
        }
    };

    public static RecurrenceRule buildRule(int cycleDay, ZoneId cycleTimezone) {
        if (cycleDay != -1) {
            return RecurrenceRule.buildRecurringMonthly(cycleDay, cycleTimezone);
        }
        return RecurrenceRule.buildNever();
    }

    @Deprecated
    public NetworkPolicy(NetworkTemplate template, int cycleDay, String cycleTimezone, long warningBytes, long limitBytes, boolean metered) {
        this(template, cycleDay, cycleTimezone, warningBytes, limitBytes, -1L, -1L, metered, false);
    }

    @Deprecated
    public NetworkPolicy(NetworkTemplate template, int cycleDay, String cycleTimezone, long warningBytes, long limitBytes, long lastWarningSnooze, long lastLimitSnooze, boolean metered, boolean inferred) {
        this(template, buildRule(cycleDay, ZoneId.of(cycleTimezone)), warningBytes, limitBytes, lastWarningSnooze, lastLimitSnooze, metered, inferred);
    }

    @Deprecated
    public NetworkPolicy(NetworkTemplate template, RecurrenceRule cycleRule, long warningBytes, long limitBytes, long lastWarningSnooze, long lastLimitSnooze, boolean metered, boolean inferred) {
        this(template, cycleRule, warningBytes, limitBytes, lastWarningSnooze, lastLimitSnooze, -1L, metered, inferred);
    }

    public NetworkPolicy(NetworkTemplate template, RecurrenceRule cycleRule, long warningBytes, long limitBytes, long lastWarningSnooze, long lastLimitSnooze, long lastRapidSnooze, boolean metered, boolean inferred) {
        this.warningBytes = -1L;
        this.limitBytes = -1L;
        this.lastWarningSnooze = -1L;
        this.lastLimitSnooze = -1L;
        this.lastRapidSnooze = -1L;
        this.metered = true;
        this.inferred = false;
        this.template = (NetworkTemplate) Preconditions.checkNotNull(template, "missing NetworkTemplate");
        this.cycleRule = (RecurrenceRule) Preconditions.checkNotNull(cycleRule, "missing RecurrenceRule");
        this.warningBytes = warningBytes;
        this.limitBytes = limitBytes;
        this.lastWarningSnooze = lastWarningSnooze;
        this.lastLimitSnooze = lastLimitSnooze;
        this.lastRapidSnooze = lastRapidSnooze;
        this.metered = metered;
        this.inferred = inferred;
    }

    private NetworkPolicy(Parcel source) {
        boolean z;
        this.warningBytes = -1L;
        this.limitBytes = -1L;
        this.lastWarningSnooze = -1L;
        this.lastLimitSnooze = -1L;
        this.lastRapidSnooze = -1L;
        this.metered = true;
        this.inferred = false;
        this.template = (NetworkTemplate) source.readParcelable(null, NetworkTemplate.class);
        this.cycleRule = (RecurrenceRule) source.readParcelable(null, RecurrenceRule.class);
        this.warningBytes = source.readLong();
        this.limitBytes = source.readLong();
        this.lastWarningSnooze = source.readLong();
        this.lastLimitSnooze = source.readLong();
        this.lastRapidSnooze = source.readLong();
        if (source.readInt() != 0) {
            z = true;
        } else {
            z = false;
        }
        this.metered = z;
        this.inferred = source.readInt() != 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.template, i);
        parcel.writeParcelable(this.cycleRule, i);
        parcel.writeLong(this.warningBytes);
        parcel.writeLong(this.limitBytes);
        parcel.writeLong(this.lastWarningSnooze);
        parcel.writeLong(this.lastLimitSnooze);
        parcel.writeLong(this.lastRapidSnooze);
        parcel.writeInt(this.metered ? 1 : 0);
        parcel.writeInt(this.inferred ? 1 : 0);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public Iterator<Range<ZonedDateTime>> cycleIterator() {
        return this.cycleRule.cycleIterator();
    }

    public boolean isOverWarning(long totalBytes) {
        return this.warningBytes != -1 && totalBytes >= this.warningBytes;
    }

    public boolean isOverLimit(long totalBytes) {
        return this.limitBytes != -1 && totalBytes + 3000 >= this.limitBytes;
    }

    public void clearSnooze() {
        this.lastWarningSnooze = -1L;
        this.lastLimitSnooze = -1L;
        this.lastRapidSnooze = -1L;
    }

    public boolean hasCycle() {
        return this.cycleRule.cycleIterator().hasNext();
    }

    @Override // java.lang.Comparable
    public int compareTo(NetworkPolicy another) {
        if (another == null || another.limitBytes == -1) {
            return -1;
        }
        if (this.limitBytes == -1 || another.limitBytes < this.limitBytes) {
            return 1;
        }
        return 0;
    }

    public int hashCode() {
        return Objects.hash(this.template, this.cycleRule, Long.valueOf(this.warningBytes), Long.valueOf(this.limitBytes), Long.valueOf(this.lastWarningSnooze), Long.valueOf(this.lastLimitSnooze), Long.valueOf(this.lastRapidSnooze), Boolean.valueOf(this.metered), Boolean.valueOf(this.inferred));
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof NetworkPolicy)) {
            return false;
        }
        NetworkPolicy other = (NetworkPolicy) obj;
        return this.warningBytes == other.warningBytes && this.limitBytes == other.limitBytes && this.lastWarningSnooze == other.lastWarningSnooze && this.lastLimitSnooze == other.lastLimitSnooze && this.lastRapidSnooze == other.lastRapidSnooze && this.metered == other.metered && this.inferred == other.inferred && Objects.equals(this.template, other.template) && Objects.equals(this.cycleRule, other.cycleRule);
    }

    public String toString() {
        return "NetworkPolicy{template=" + this.template + " cycleRule=" + this.cycleRule + " warningBytes=" + this.warningBytes + " limitBytes=" + this.limitBytes + " lastWarningSnooze=" + this.lastWarningSnooze + " lastLimitSnooze=" + this.lastLimitSnooze + " lastRapidSnooze=" + this.lastRapidSnooze + " metered=" + this.metered + " inferred=" + this.inferred + "}";
    }

    public byte[] getBytesForBackup() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        dataOutputStream.writeInt(3);
        dataOutputStream.write(getNetworkTemplateBytesForBackup());
        this.cycleRule.writeToStream(dataOutputStream);
        dataOutputStream.writeLong(this.warningBytes);
        dataOutputStream.writeLong(this.limitBytes);
        dataOutputStream.writeLong(this.lastWarningSnooze);
        dataOutputStream.writeLong(this.lastLimitSnooze);
        dataOutputStream.writeLong(this.lastRapidSnooze);
        dataOutputStream.writeInt(this.metered ? 1 : 0);
        dataOutputStream.writeInt(this.inferred ? 1 : 0);
        return byteArrayOutputStream.toByteArray();
    }

    public static NetworkPolicy getNetworkPolicyFromBackup(DataInputStream in) throws IOException, BackupUtils.BadVersionException {
        RecurrenceRule cycleRule;
        long lastRapidSnooze;
        int version = in.readInt();
        if (version < 1 || version > 3) {
            throw new BackupUtils.BadVersionException("Unknown backup version: " + version);
        }
        NetworkTemplate template = getNetworkTemplateFromBackup(in);
        if (version >= 2) {
            cycleRule = new RecurrenceRule(in);
        } else {
            int cycleDay = in.readInt();
            String cycleTimezone = BackupUtils.readString(in);
            cycleRule = buildRule(cycleDay, ZoneId.of(cycleTimezone));
        }
        long warningBytes = in.readLong();
        long limitBytes = in.readLong();
        long lastWarningSnooze = in.readLong();
        long lastLimitSnooze = in.readLong();
        if (version >= 3) {
            lastRapidSnooze = in.readLong();
        } else {
            lastRapidSnooze = -1;
        }
        boolean metered = in.readInt() == 1;
        boolean inferred = in.readInt() == 1;
        return new NetworkPolicy(template, cycleRule, warningBytes, limitBytes, lastWarningSnooze, lastLimitSnooze, lastRapidSnooze, metered, inferred);
    }

    private byte[] getNetworkTemplateBytesForBackup() throws IOException {
        if (!isTemplatePersistable(this.template)) {
            Log.wtf(TAG, "Trying to backup non-persistable template: " + this);
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(baos);
        out.writeInt(3);
        out.writeInt(this.template.getMatchRule());
        Set<String> subscriberIds = this.template.getSubscriberIds();
        BackupUtils.writeString(out, subscriberIds.isEmpty() ? null : subscriberIds.iterator().next());
        BackupUtils.writeString(out, this.template.getWifiNetworkKeys().isEmpty() ? null : (String) this.template.getWifiNetworkKeys().iterator().next());
        out.writeInt(this.template.getMeteredness());
        return baos.toByteArray();
    }

    private static NetworkTemplate getNetworkTemplateFromBackup(DataInputStream in) throws IOException, BackupUtils.BadVersionException {
        int version = in.readInt();
        int metered = 1;
        if (version < 1 || version > 3 || version == 2) {
            throw new BackupUtils.BadVersionException("Unknown Backup Serialization Version");
        }
        int matchRule = in.readInt();
        String subscriberId = BackupUtils.readString(in);
        String wifiNetworkKey = BackupUtils.readString(in);
        if (version >= 3) {
            metered = in.readInt();
        } else if (matchRule != 1 && matchRule != 10) {
            metered = -1;
        }
        try {
            NetworkTemplate.Builder builder = new NetworkTemplate.Builder(matchRule).setMeteredness(metered);
            if (subscriberId != null) {
                builder.setSubscriberIds(Set.of(subscriberId));
            }
            if (wifiNetworkKey != null) {
                builder.setWifiNetworkKeys(Set.of(wifiNetworkKey));
            }
            return builder.build();
        } catch (IllegalArgumentException e) {
            throw new BackupUtils.BadVersionException("Restored network template contains unknown match rule " + matchRule, e);
        }
    }

    public static boolean isTemplatePersistable(NetworkTemplate template) {
        switch (template.getMatchRule()) {
            case 1:
            case 10:
                return !template.getSubscriberIds().isEmpty() && template.getMeteredness() == 1;
            case 4:
                return (template.getWifiNetworkKeys().isEmpty() && template.getSubscriberIds().isEmpty()) ? false : true;
            case 5:
            case 8:
                return true;
            default:
                return false;
        }
    }
}
