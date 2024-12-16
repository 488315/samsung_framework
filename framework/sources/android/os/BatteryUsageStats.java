package android.os;

import android.database.CursorWindow;
import android.hardware.scontext.SContextConstants;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.AggregateBatteryConsumer;
import android.os.BatteryConsumer;
import android.os.BatteryStats;
import android.os.Parcelable;
import android.os.UidBatteryConsumer;
import android.os.UserBatteryConsumer;
import android.util.Range;
import android.util.SparseArray;
import android.util.proto.ProtoOutputStream;
import com.android.internal.content.NativeLibraryHelper;
import com.android.internal.os.BatteryStatsHistory;
import com.android.internal.os.BatteryStatsHistoryIterator;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import java.io.Closeable;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.ToDoubleFunction;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes3.dex */
public final class BatteryUsageStats implements Parcelable, Closeable {
    public static final int AGGREGATE_BATTERY_CONSUMER_SCOPE_ALL_APPS = 1;
    public static final int AGGREGATE_BATTERY_CONSUMER_SCOPE_COUNT = 2;
    public static final int AGGREGATE_BATTERY_CONSUMER_SCOPE_DEVICE = 0;
    private static final long BATTERY_CONSUMER_CURSOR_WINDOW_SIZE = 20000000;
    private static final int STATSD_PULL_ATOM_MAX_BYTES = 45000;
    private static final double WEIGHT_BACKGROUND_STATE = 8.333333333333333E-5d;
    private static final double WEIGHT_CONSUMED_POWER = 1.0d;
    private static final double WEIGHT_FOREGROUND_STATE = 2.777777777777778E-5d;
    static final String XML_ATTR_BATTERY_CAPACITY = "battery_capacity";
    static final String XML_ATTR_BATTERY_REMAINING = "battery_remaining";
    static final String XML_ATTR_CHARGE_REMAINING = "charge_remaining";
    static final String XML_ATTR_DISCHARGE_DURATION = "discharge_duration";
    static final String XML_ATTR_DISCHARGE_LOWER = "discharge_lower";
    static final String XML_ATTR_DISCHARGE_PERCENT = "discharge_pct";
    static final String XML_ATTR_DISCHARGE_UPPER = "discharge_upper";
    static final String XML_ATTR_DURATION = "duration";
    static final String XML_ATTR_END_TIMESTAMP = "end_timestamp";
    static final String XML_ATTR_HIGHEST_DRAIN_PACKAGE = "highest_drain_package";
    static final String XML_ATTR_ID = "id";
    static final String XML_ATTR_MODEL = "model";
    static final String XML_ATTR_POWER = "power";
    static final String XML_ATTR_POWER_STATE = "power_state";
    static final String XML_ATTR_PREFIX_CUSTOM_COMPONENT = "custom_component_";
    static final String XML_ATTR_PREFIX_INCLUDES_POWER_STATE_DATA = "includes_power_state_data";
    static final String XML_ATTR_PREFIX_INCLUDES_PROC_STATE_DATA = "includes_proc_state_data";
    static final String XML_ATTR_PREFIX_INCLUDES_SCREEN_STATE_DATA = "includes_screen_state_data";
    static final String XML_ATTR_PROCESS_STATE = "process_state";
    static final String XML_ATTR_SCOPE = "scope";
    static final String XML_ATTR_SCREEN_STATE = "screen_state";
    static final String XML_ATTR_START_TIMESTAMP = "start_timestamp";
    static final String XML_ATTR_TIME_IN_BACKGROUND = "time_in_background";
    static final String XML_ATTR_TIME_IN_FOREGROUND = "time_in_foreground";
    static final String XML_ATTR_TIME_IN_FOREGROUND_SERVICE = "time_in_foreground_service";
    static final String XML_ATTR_UID = "uid";
    static final String XML_ATTR_USER_ID = "user_id";
    static final String XML_TAG_AGGREGATE = "aggregate";
    static final String XML_TAG_BATTERY_USAGE_STATS = "battery_usage_stats";
    static final String XML_TAG_COMPONENT = "component";
    static final String XML_TAG_CUSTOM_COMPONENT = "custom_component";
    static final String XML_TAG_POWER_COMPONENTS = "power_components";
    static final String XML_TAG_UID = "uid";
    static final String XML_TAG_USER = "user";
    private final AggregateBatteryConsumer[] mAggregateBatteryConsumers;
    private final double mBatteryCapacityMah;
    private BatteryConsumer.BatteryConsumerDataLayout mBatteryConsumerDataLayout;
    private CursorWindow mBatteryConsumersCursorWindow;
    private double mBatteryRatedCapacityMah;
    private final BatteryStatsHistory mBatteryStatsHistory;
    private final long mBatteryTimeRemainingMs;
    private double mBatteryTypicalCapacityMah;
    private final long mChargeTimeRemainingMs;
    private final String[] mCustomPowerComponentNames;
    private final long mDischargeDurationMs;
    private final int mDischargePercentage;
    private final double mDischargedPowerLowerBound;
    private final double mDischargedPowerUpperBound;
    private final boolean mIncludesPowerModels;
    private final boolean mIncludesPowerStateData;
    private final boolean mIncludesProcessStateData;
    private final boolean mIncludesScreenStateData;
    private final long mStatsDurationMs;
    private final long mStatsEndTimestampMs;
    private final long mStatsStartTimestampMs;
    private final List<UidBatteryConsumer> mUidBatteryConsumers;
    private final List<UserBatteryConsumer> mUserBatteryConsumers;
    private static final int[] UID_USAGE_TIME_PROCESS_STATES = {1, 2, 3};
    public static final Parcelable.Creator<BatteryUsageStats> CREATOR = new Parcelable.Creator<BatteryUsageStats>() { // from class: android.os.BatteryUsageStats.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BatteryUsageStats createFromParcel(Parcel source) {
            return new BatteryUsageStats(source);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BatteryUsageStats[] newArray(int size) {
            return new BatteryUsageStats[size];
        }
    };

    @Retention(RetentionPolicy.SOURCE)
    public @interface AggregateBatteryConsumerScope {
    }

    private BatteryUsageStats(Builder builder) {
        this.mStatsStartTimestampMs = builder.mStatsStartTimestampMs;
        this.mStatsEndTimestampMs = builder.mStatsEndTimestampMs;
        this.mStatsDurationMs = builder.getStatsDuration();
        this.mBatteryCapacityMah = builder.mBatteryCapacityMah;
        this.mBatteryRatedCapacityMah = builder.mBatteryRatedCapacityMah;
        this.mBatteryTypicalCapacityMah = builder.mBatteryTypicalCapacityMah;
        this.mDischargePercentage = builder.mDischargePercentage;
        this.mDischargedPowerLowerBound = builder.mDischargedPowerLowerBoundMah;
        this.mDischargedPowerUpperBound = builder.mDischargedPowerUpperBoundMah;
        this.mDischargeDurationMs = builder.mDischargeDurationMs;
        this.mBatteryStatsHistory = builder.mBatteryStatsHistory;
        this.mBatteryTimeRemainingMs = builder.mBatteryTimeRemainingMs;
        this.mChargeTimeRemainingMs = builder.mChargeTimeRemainingMs;
        this.mCustomPowerComponentNames = builder.mCustomPowerComponentNames;
        this.mIncludesPowerModels = builder.mIncludePowerModels;
        this.mIncludesProcessStateData = builder.mIncludesProcessStateData;
        this.mIncludesScreenStateData = builder.mIncludesScreenStateData;
        this.mIncludesPowerStateData = builder.mIncludesPowerStateData;
        this.mBatteryConsumerDataLayout = builder.mBatteryConsumerDataLayout;
        this.mBatteryConsumersCursorWindow = builder.mBatteryConsumersCursorWindow;
        double totalPowerMah = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        int uidBatteryConsumerCount = builder.mUidBatteryConsumerBuilders.size();
        this.mUidBatteryConsumers = new ArrayList(uidBatteryConsumerCount);
        for (int i = 0; i < uidBatteryConsumerCount; i++) {
            UidBatteryConsumer.Builder uidBatteryConsumerBuilder = (UidBatteryConsumer.Builder) builder.mUidBatteryConsumerBuilders.valueAt(i);
            if (!uidBatteryConsumerBuilder.isExcludedFromBatteryUsageStats()) {
                UidBatteryConsumer consumer = uidBatteryConsumerBuilder.build();
                totalPowerMah += consumer.getConsumedPower();
                this.mUidBatteryConsumers.add(consumer);
            }
        }
        int userBatteryConsumerCount = builder.mUserBatteryConsumerBuilders.size();
        this.mUserBatteryConsumers = new ArrayList(userBatteryConsumerCount);
        for (int i2 = 0; i2 < userBatteryConsumerCount; i2++) {
            UserBatteryConsumer consumer2 = ((UserBatteryConsumer.Builder) builder.mUserBatteryConsumerBuilders.valueAt(i2)).build();
            totalPowerMah += consumer2.getConsumedPower();
            this.mUserBatteryConsumers.add(consumer2);
        }
        builder.getAggregateBatteryConsumerBuilder(1).setConsumedPower(totalPowerMah);
        this.mAggregateBatteryConsumers = new AggregateBatteryConsumer[2];
        for (int i3 = 0; i3 < 2; i3++) {
            this.mAggregateBatteryConsumers[i3] = builder.mAggregateBatteryConsumersBuilders[i3].build();
        }
    }

    public long getStatsStartTimestamp() {
        return this.mStatsStartTimestampMs;
    }

    public long getStatsEndTimestamp() {
        return this.mStatsEndTimestampMs;
    }

    public long getStatsDuration() {
        return this.mStatsDurationMs;
    }

    public double getConsumedPower() {
        return this.mAggregateBatteryConsumers[0].getConsumedPower();
    }

    public double getBatteryCapacity() {
        return this.mBatteryCapacityMah;
    }

    public double getBatteryRatedCapacity() {
        return this.mBatteryRatedCapacityMah;
    }

    public double getBatteryTypicalCapacity() {
        return this.mBatteryTypicalCapacityMah;
    }

    public int getDischargePercentage() {
        return this.mDischargePercentage;
    }

    public Range<Double> getDischargedPowerRange() {
        return Range.create(Double.valueOf(this.mDischargedPowerLowerBound), Double.valueOf(this.mDischargedPowerUpperBound));
    }

    public long getDischargeDurationMs() {
        return this.mDischargeDurationMs;
    }

    public long getBatteryTimeRemainingMs() {
        return this.mBatteryTimeRemainingMs;
    }

    public long getChargeTimeRemainingMs() {
        return this.mChargeTimeRemainingMs;
    }

    public AggregateBatteryConsumer getAggregateBatteryConsumer(int scope) {
        return this.mAggregateBatteryConsumers[scope];
    }

    public List<UidBatteryConsumer> getUidBatteryConsumers() {
        return this.mUidBatteryConsumers;
    }

    public List<UserBatteryConsumer> getUserBatteryConsumers() {
        return this.mUserBatteryConsumers;
    }

    public String[] getCustomPowerComponentNames() {
        return this.mCustomPowerComponentNames;
    }

    public boolean isProcessStateDataIncluded() {
        return this.mIncludesProcessStateData;
    }

    public BatteryStatsHistoryIterator iterateBatteryStatsHistory() {
        if (this.mBatteryStatsHistory == null) {
            throw new IllegalStateException("Battery history was not requested in the BatteryUsageStatsQuery");
        }
        return new BatteryStatsHistoryIterator(this.mBatteryStatsHistory, 0L, -1L);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private BatteryUsageStats(Parcel source) {
        this.mStatsStartTimestampMs = source.readLong();
        this.mStatsEndTimestampMs = source.readLong();
        this.mStatsDurationMs = source.readLong();
        this.mBatteryCapacityMah = source.readDouble();
        this.mDischargePercentage = source.readInt();
        this.mDischargedPowerLowerBound = source.readDouble();
        this.mDischargedPowerUpperBound = source.readDouble();
        this.mDischargeDurationMs = source.readLong();
        this.mBatteryTimeRemainingMs = source.readLong();
        this.mChargeTimeRemainingMs = source.readLong();
        this.mCustomPowerComponentNames = source.readStringArray();
        this.mIncludesPowerModels = source.readBoolean();
        this.mIncludesProcessStateData = source.readBoolean();
        this.mIncludesScreenStateData = source.readBoolean();
        this.mIncludesPowerStateData = source.readBoolean();
        this.mBatteryConsumersCursorWindow = CursorWindow.newFromParcel(source);
        this.mBatteryConsumerDataLayout = BatteryConsumer.createBatteryConsumerDataLayout(this.mCustomPowerComponentNames, this.mIncludesPowerModels, this.mIncludesProcessStateData, this.mIncludesScreenStateData, this.mIncludesPowerStateData);
        int numRows = this.mBatteryConsumersCursorWindow.getNumRows();
        this.mAggregateBatteryConsumers = new AggregateBatteryConsumer[2];
        this.mUidBatteryConsumers = new ArrayList(numRows);
        this.mUserBatteryConsumers = new ArrayList();
        for (int i = 0; i < numRows; i++) {
            BatteryConsumer.BatteryConsumerData data = new BatteryConsumer.BatteryConsumerData(this.mBatteryConsumersCursorWindow, i, this.mBatteryConsumerDataLayout);
            int consumerType = this.mBatteryConsumersCursorWindow.getInt(i, 0);
            switch (consumerType) {
                case 0:
                    AggregateBatteryConsumer consumer = new AggregateBatteryConsumer(data);
                    this.mAggregateBatteryConsumers[consumer.getScope()] = consumer;
                    break;
                case 1:
                    this.mUidBatteryConsumers.add(new UidBatteryConsumer(data));
                    break;
                case 2:
                    this.mUserBatteryConsumers.add(new UserBatteryConsumer(data));
                    break;
            }
        }
        if (source.readBoolean()) {
            this.mBatteryStatsHistory = BatteryStatsHistory.createFromBatteryUsageStatsParcel(source);
        } else {
            this.mBatteryStatsHistory = null;
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.mStatsStartTimestampMs);
        dest.writeLong(this.mStatsEndTimestampMs);
        dest.writeLong(this.mStatsDurationMs);
        dest.writeDouble(this.mBatteryCapacityMah);
        dest.writeInt(this.mDischargePercentage);
        dest.writeDouble(this.mDischargedPowerLowerBound);
        dest.writeDouble(this.mDischargedPowerUpperBound);
        dest.writeLong(this.mDischargeDurationMs);
        dest.writeLong(this.mBatteryTimeRemainingMs);
        dest.writeLong(this.mChargeTimeRemainingMs);
        dest.writeStringArray(this.mCustomPowerComponentNames);
        dest.writeBoolean(this.mIncludesPowerModels);
        dest.writeBoolean(this.mIncludesProcessStateData);
        dest.writeBoolean(this.mIncludesScreenStateData);
        dest.writeBoolean(this.mIncludesPowerStateData);
        this.mBatteryConsumersCursorWindow.writeToParcel(dest, flags);
        if (this.mBatteryStatsHistory != null) {
            dest.writeBoolean(true);
            this.mBatteryStatsHistory.writeToBatteryUsageStatsParcel(dest);
        } else {
            dest.writeBoolean(false);
        }
    }

    public byte[] getStatsProto() {
        int maxRawSize = 78750;
        for (int i = 0; i < 3; i++) {
            ProtoOutputStream proto = new ProtoOutputStream();
            writeStatsProto(proto, maxRawSize);
            int rawSize = proto.getRawSize();
            byte[] protoOutput = proto.getBytes();
            if (protoOutput.length <= STATSD_PULL_ATOM_MAX_BYTES) {
                return protoOutput;
            }
            maxRawSize = (int) (((rawSize * 45000) / protoOutput.length) - 1024);
        }
        ProtoOutputStream proto2 = new ProtoOutputStream();
        writeStatsProto(proto2, STATSD_PULL_ATOM_MAX_BYTES);
        return proto2.getBytes();
    }

    public void dumpToProto(FileDescriptor fd) {
        ProtoOutputStream proto = new ProtoOutputStream(fd);
        writeStatsProto(proto, Integer.MAX_VALUE);
        proto.flush();
    }

    private void writeStatsProto(ProtoOutputStream proto, int maxRawSize) {
        AggregateBatteryConsumer deviceBatteryConsumer = getAggregateBatteryConsumer(0);
        proto.write(1112396529665L, getStatsStartTimestamp());
        proto.write(1112396529666L, getStatsEndTimestamp());
        proto.write(1112396529667L, getStatsDuration());
        proto.write(1120986464262L, getDischargePercentage());
        proto.write(1112396529671L, getDischargeDurationMs());
        deviceBatteryConsumer.writeStatsProto(proto, 1146756268036L);
        if (this.mIncludesPowerModels) {
            deviceBatteryConsumer.writePowerComponentModelProto(proto);
        }
        writeUidBatteryConsumersProto(proto, maxRawSize);
    }

    private void writeUidBatteryConsumersProto(ProtoOutputStream proto, int maxRawSize) {
        List<UidBatteryConsumer> consumers;
        int size;
        UidBatteryConsumer consumer;
        int[] iArr;
        long fgMs;
        List<UidBatteryConsumer> consumers2 = getUidBatteryConsumers();
        consumers2.sort(Comparator.comparingDouble(new ToDoubleFunction() { // from class: android.os.BatteryUsageStats$$ExternalSyntheticLambda0
            @Override // java.util.function.ToDoubleFunction
            public final double applyAsDouble(Object obj) {
                double uidBatteryConsumerWeight;
                uidBatteryConsumerWeight = BatteryUsageStats.this.getUidBatteryConsumerWeight((UidBatteryConsumer) obj);
                return uidBatteryConsumerWeight;
            }
        }).reversed());
        int size2 = consumers2.size();
        int i = 0;
        while (i < size2) {
            UidBatteryConsumer consumer2 = consumers2.get(i);
            long fgMs2 = consumer2.getTimeInStateMs(0);
            long bgMs = consumer2.getTimeInStateMs(1);
            boolean hasBaseData = consumer2.hasStatsProtoData();
            if (fgMs2 != 0 || bgMs != 0 || hasBaseData) {
                long token = proto.start(2246267895813L);
                proto.write(1120986464257L, consumer2.getUid());
                if (hasBaseData) {
                    consumer2.writeStatsProto(proto, 1146756268034L);
                }
                proto.write(1112396529667L, fgMs2);
                proto.write(1112396529668L, bgMs);
                int[] iArr2 = UID_USAGE_TIME_PROCESS_STATES;
                int length = iArr2.length;
                int i2 = 0;
                while (i2 < length) {
                    List<UidBatteryConsumer> consumers3 = consumers2;
                    int processState = iArr2[i2];
                    int size3 = size2;
                    long timeInStateMillis = consumer2.getTimeInProcessStateMs(processState);
                    if (timeInStateMillis <= 0) {
                        consumer = consumer2;
                        iArr = iArr2;
                        fgMs = fgMs2;
                    } else {
                        consumer = consumer2;
                        iArr = iArr2;
                        fgMs = fgMs2;
                        long timeInStateToken = proto.start(2246267895813L);
                        proto.write(1159641169921L, processState);
                        proto.write(1112396529666L, timeInStateMillis);
                        proto.end(timeInStateToken);
                    }
                    i2++;
                    consumers2 = consumers3;
                    fgMs2 = fgMs;
                    size2 = size3;
                    consumer2 = consumer;
                    iArr2 = iArr;
                }
                consumers = consumers2;
                size = size2;
                proto.end(token);
                if (proto.getRawSize() >= maxRawSize) {
                    return;
                }
            } else {
                consumers = consumers2;
                size = size2;
            }
            i++;
            consumers2 = consumers;
            size2 = size;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public double getUidBatteryConsumerWeight(UidBatteryConsumer uidBatteryConsumer) {
        double consumedPower = uidBatteryConsumer.getConsumedPower();
        long timeInForeground = uidBatteryConsumer.getTimeInStateMs(0);
        long timeInBackground = uidBatteryConsumer.getTimeInStateMs(1);
        return (WEIGHT_CONSUMED_POWER * consumedPower) + (timeInForeground * WEIGHT_FOREGROUND_STATE) + (timeInBackground * WEIGHT_BACKGROUND_STATE);
    }

    public void dump(PrintWriter pw, String prefix) {
        int i;
        int i2;
        StringBuilder stateLabel;
        BatteryConsumer.Key[] keyArr;
        int i3;
        int screenState;
        int powerState;
        int componentId;
        int componentId2;
        BatteryConsumer deviceConsumer;
        Range<Double> dischargedPowerRange;
        BatteryConsumer appsConsumer;
        pw.print(prefix);
        pw.println("  Estimated power use (mAh):");
        pw.print(prefix);
        pw.print("    Capacity: ");
        pw.print(BatteryStats.formatCharge(getBatteryCapacity()));
        pw.print(", Rated: ");
        pw.print(BatteryStats.formatCharge(getBatteryRatedCapacity()));
        pw.print(", Typical: ");
        pw.print(BatteryStats.formatCharge(getBatteryTypicalCapacity()));
        pw.print(", Computed drain: ");
        pw.print(BatteryStats.formatCharge(getConsumedPower()));
        Range<Double> dischargedPowerRange2 = getDischargedPowerRange();
        pw.print(", actual drain: ");
        pw.print(BatteryStats.formatCharge(dischargedPowerRange2.getLower().doubleValue()));
        if (!dischargedPowerRange2.getLower().equals(dischargedPowerRange2.getUpper())) {
            pw.print(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
            pw.print(BatteryStats.formatCharge(dischargedPowerRange2.getUpper().doubleValue()));
        }
        pw.println();
        pw.println("    Global");
        BatteryConsumer deviceConsumer2 = getAggregateBatteryConsumer(0);
        BatteryConsumer appsConsumer2 = getAggregateBatteryConsumer(1);
        int componentId3 = 0;
        while (componentId3 < 19) {
            double devicePowerMah = deviceConsumer2.getConsumedPower(componentId3);
            double appsPowerMah = appsConsumer2.getConsumedPower(componentId3);
            if (devicePowerMah == SContextConstants.ENVIRONMENT_VALUE_UNKNOWN && appsPowerMah == SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) {
                componentId2 = componentId3;
                deviceConsumer = deviceConsumer2;
                dischargedPowerRange = dischargedPowerRange2;
                appsConsumer = appsConsumer2;
            } else {
                componentId2 = componentId3;
                deviceConsumer = deviceConsumer2;
                dischargedPowerRange = dischargedPowerRange2;
                appsConsumer = appsConsumer2;
                printPowerComponent(pw, prefix, BatteryConsumer.powerComponentIdToString(componentId3), devicePowerMah, appsPowerMah, 0, deviceConsumer2.getUsageDurationMillis(componentId3));
            }
            componentId3 = componentId2 + 1;
            appsConsumer2 = appsConsumer;
            deviceConsumer2 = deviceConsumer;
            dischargedPowerRange2 = dischargedPowerRange;
        }
        BatteryConsumer deviceConsumer3 = deviceConsumer2;
        BatteryConsumer appsConsumer3 = appsConsumer2;
        int componentId4 = 1000;
        while (componentId4 < this.mCustomPowerComponentNames.length + 1000) {
            double devicePowerMah2 = deviceConsumer3.getConsumedPowerForCustomComponent(componentId4);
            double appsPowerMah2 = appsConsumer3.getConsumedPowerForCustomComponent(componentId4);
            if (devicePowerMah2 == SContextConstants.ENVIRONMENT_VALUE_UNKNOWN && appsPowerMah2 == SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) {
                componentId = componentId4;
            } else {
                componentId = componentId4;
                printPowerComponent(pw, prefix, deviceConsumer3.getCustomPowerComponentName(componentId4), devicePowerMah2, appsPowerMah2, 0, deviceConsumer3.getUsageDurationForCustomComponentMillis(componentId4));
            }
            componentId4 = componentId + 1;
        }
        if (this.mIncludesScreenStateData || this.mIncludesPowerStateData) {
            String prefixPlus = prefix + "  ";
            StringBuilder stateLabel2 = new StringBuilder();
            int screenState2 = 0;
            int powerState2 = 0;
            BatteryConsumer.Key[] keyArr2 = this.mBatteryConsumerDataLayout.keys;
            int length = keyArr2.length;
            int i4 = 0;
            while (i4 < length) {
                BatteryConsumer.Key key = keyArr2[i4];
                if (key.processState == 0 && (key.screenState != 0 || key.powerState != 0)) {
                    double devicePowerMah3 = deviceConsumer3.getConsumedPower(key);
                    double appsPowerMah3 = appsConsumer3.getConsumedPower(key);
                    if (devicePowerMah3 != SContextConstants.ENVIRONMENT_VALUE_UNKNOWN || appsPowerMah3 != SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) {
                        if (key.screenState == screenState2 && key.powerState == powerState2) {
                            screenState = screenState2;
                            powerState = powerState2;
                            i3 = 0;
                        } else {
                            int screenState3 = key.screenState;
                            int powerState3 = key.powerState;
                            boolean empty = true;
                            i3 = 0;
                            stateLabel2.setLength(0);
                            stateLabel2.append("      (");
                            if (powerState3 != 0) {
                                stateLabel2.append(BatteryConsumer.powerStateToString(powerState3));
                                empty = false;
                            }
                            if (screenState3 != 0) {
                                if (!empty) {
                                    stateLabel2.append(", ");
                                }
                                stateLabel2.append("screen ").append(BatteryConsumer.screenStateToString(screenState3));
                                empty = false;
                            }
                            if (!empty) {
                                stateLabel2.append(NavigationBarInflaterView.KEY_CODE_END);
                                pw.println(stateLabel2);
                            }
                            screenState = screenState3;
                            powerState = powerState3;
                        }
                        int screenState4 = key.powerComponent;
                        String label = BatteryConsumer.powerComponentIdToString(screenState4);
                        i = i4;
                        i2 = length;
                        stateLabel = stateLabel2;
                        keyArr = keyArr2;
                        printPowerComponent(pw, prefixPlus, label, devicePowerMah3, appsPowerMah3, this.mIncludesPowerModels ? deviceConsumer3.getPowerModel(key) : i3, deviceConsumer3.getUsageDurationMillis(key));
                        screenState2 = screenState;
                        powerState2 = powerState;
                        i4 = i + 1;
                        stateLabel2 = stateLabel;
                        length = i2;
                        keyArr2 = keyArr;
                    }
                }
                i = i4;
                i2 = length;
                stateLabel = stateLabel2;
                keyArr = keyArr2;
                i4 = i + 1;
                stateLabel2 = stateLabel;
                length = i2;
                keyArr2 = keyArr;
            }
        }
        dumpSortedBatteryConsumers(pw, prefix, getUidBatteryConsumers());
        dumpSortedBatteryConsumers(pw, prefix, getUserBatteryConsumers());
        pw.println();
    }

    private void printPowerComponent(PrintWriter pw, String prefix, String label, double devicePowerMah, double appsPowerMah, int powerModel, long durationMs) {
        StringBuilder sb = new StringBuilder();
        sb.append(prefix).append("    ").append(label).append(": ").append(BatteryStats.formatCharge(devicePowerMah));
        if (powerModel != 0 && powerModel != 1) {
            sb.append(" [");
            sb.append(BatteryConsumer.powerModelToString(powerModel));
            sb.append(NavigationBarInflaterView.SIZE_MOD_END);
        }
        sb.append(" apps: ").append(BatteryStats.formatCharge(appsPowerMah));
        if (durationMs != 0) {
            sb.append(" duration: ");
            BatteryStats.formatTimeMs(sb, durationMs);
        }
        pw.println(sb);
    }

    private void dumpSortedBatteryConsumers(PrintWriter pw, String prefix, List<? extends BatteryConsumer> batteryConsumers) {
        batteryConsumers.sort(Comparator.comparingDouble(new ToDoubleFunction() { // from class: android.os.BatteryUsageStats$$ExternalSyntheticLambda1
            @Override // java.util.function.ToDoubleFunction
            public final double applyAsDouble(Object obj) {
                return ((BatteryConsumer) obj).getConsumedPower();
            }
        }).reversed());
        for (BatteryConsumer consumer : batteryConsumers) {
            if (consumer.getConsumedPower() != SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) {
                pw.print(prefix);
                pw.print("  ");
                consumer.dump(pw);
            }
        }
    }

    public void writeXml(TypedXmlSerializer serializer) throws IOException {
        serializer.startTag(null, XML_TAG_BATTERY_USAGE_STATS);
        for (int i = 0; i < this.mCustomPowerComponentNames.length; i++) {
            serializer.attribute(null, XML_ATTR_PREFIX_CUSTOM_COMPONENT + i, this.mCustomPowerComponentNames[i]);
        }
        serializer.attributeBoolean(null, XML_ATTR_PREFIX_INCLUDES_PROC_STATE_DATA, this.mIncludesProcessStateData);
        serializer.attributeBoolean(null, XML_ATTR_PREFIX_INCLUDES_SCREEN_STATE_DATA, this.mIncludesScreenStateData);
        serializer.attributeBoolean(null, XML_ATTR_PREFIX_INCLUDES_POWER_STATE_DATA, this.mIncludesPowerStateData);
        serializer.attributeLong(null, XML_ATTR_START_TIMESTAMP, this.mStatsStartTimestampMs);
        serializer.attributeLong(null, XML_ATTR_END_TIMESTAMP, this.mStatsEndTimestampMs);
        serializer.attributeLong(null, "duration", this.mStatsDurationMs);
        serializer.attributeDouble(null, XML_ATTR_BATTERY_CAPACITY, this.mBatteryCapacityMah);
        serializer.attributeInt(null, XML_ATTR_DISCHARGE_PERCENT, this.mDischargePercentage);
        serializer.attributeDouble(null, XML_ATTR_DISCHARGE_LOWER, this.mDischargedPowerLowerBound);
        serializer.attributeDouble(null, XML_ATTR_DISCHARGE_UPPER, this.mDischargedPowerUpperBound);
        serializer.attributeLong(null, XML_ATTR_DISCHARGE_DURATION, this.mDischargeDurationMs);
        serializer.attributeLong(null, XML_ATTR_BATTERY_REMAINING, this.mBatteryTimeRemainingMs);
        serializer.attributeLong(null, XML_ATTR_CHARGE_REMAINING, this.mChargeTimeRemainingMs);
        for (int scope = 0; scope < 2; scope++) {
            this.mAggregateBatteryConsumers[scope].writeToXml(serializer, scope);
        }
        for (UidBatteryConsumer consumer : this.mUidBatteryConsumers) {
            consumer.writeToXml(serializer);
        }
        for (UserBatteryConsumer consumer2 : this.mUserBatteryConsumers) {
            consumer2.writeToXml(serializer);
        }
        serializer.endTag(null, XML_TAG_BATTERY_USAGE_STATS);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static BatteryUsageStats createFromXml(TypedXmlPullParser parser) throws XmlPullParserException, IOException {
        boolean z;
        Builder builder = null;
        int eventType = parser.getEventType();
        while (true) {
            if (eventType == 1) {
                break;
            }
            if (eventType == 2 && parser.getName().equals(XML_TAG_BATTERY_USAGE_STATS)) {
                List<String> customComponentNames = new ArrayList<>();
                int i = 0;
                while (true) {
                    int index = parser.getAttributeIndex(null, XML_ATTR_PREFIX_CUSTOM_COMPONENT + i);
                    if (index == -1) {
                        break;
                    }
                    customComponentNames.add(parser.getAttributeValue(index));
                    i++;
                }
                boolean includesProcStateData = parser.getAttributeBoolean(null, XML_ATTR_PREFIX_INCLUDES_PROC_STATE_DATA, false);
                boolean includesScreenStateData = parser.getAttributeBoolean(null, XML_ATTR_PREFIX_INCLUDES_SCREEN_STATE_DATA, false);
                boolean includesPowerStateData = parser.getAttributeBoolean(null, XML_ATTR_PREFIX_INCLUDES_POWER_STATE_DATA, false);
                builder = new Builder((String[]) customComponentNames.toArray(new String[0]), true, includesProcStateData, includesScreenStateData, includesPowerStateData, SContextConstants.ENVIRONMENT_VALUE_UNKNOWN);
                builder.setStatsStartTimestamp(parser.getAttributeLong(null, XML_ATTR_START_TIMESTAMP));
                builder.setStatsEndTimestamp(parser.getAttributeLong(null, XML_ATTR_END_TIMESTAMP));
                builder.setStatsDuration(parser.getAttributeLong(null, "duration"));
                builder.setBatteryCapacity(parser.getAttributeDouble(null, XML_ATTR_BATTERY_CAPACITY));
                builder.setDischargePercentage(parser.getAttributeInt(null, XML_ATTR_DISCHARGE_PERCENT));
                builder.setDischargedPowerRange(parser.getAttributeDouble(null, XML_ATTR_DISCHARGE_LOWER), parser.getAttributeDouble(null, XML_ATTR_DISCHARGE_UPPER));
                builder.setDischargeDurationMs(parser.getAttributeLong(null, XML_ATTR_DISCHARGE_DURATION));
                builder.setBatteryTimeRemainingMs(parser.getAttributeLong(null, XML_ATTR_BATTERY_REMAINING));
                builder.setChargeTimeRemainingMs(parser.getAttributeLong(null, XML_ATTR_CHARGE_REMAINING));
                eventType = parser.next();
            } else {
                eventType = parser.next();
            }
        }
        if (builder == null) {
            throw new XmlPullParserException("No root element");
        }
        while (eventType != 1) {
            if (eventType == 2) {
                String name = parser.getName();
                switch (name.hashCode()) {
                    case 115792:
                        if (name.equals("uid")) {
                            z = true;
                            break;
                        }
                        z = -1;
                        break;
                    case 3599307:
                        if (name.equals("user")) {
                            z = 2;
                            break;
                        }
                        z = -1;
                        break;
                    case 175177151:
                        if (name.equals(XML_TAG_AGGREGATE)) {
                            z = false;
                            break;
                        }
                        z = -1;
                        break;
                    default:
                        z = -1;
                        break;
                }
                switch (z) {
                    case false:
                        AggregateBatteryConsumer.parseXml(parser, builder);
                        break;
                    case true:
                        UidBatteryConsumer.createFromXml(parser, builder);
                        break;
                    case true:
                        UserBatteryConsumer.createFromXml(parser, builder);
                        break;
                }
            }
            eventType = parser.next();
        }
        return builder.build();
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.mBatteryConsumersCursorWindow.close();
        this.mBatteryConsumersCursorWindow = null;
    }

    protected void finalize() throws Throwable {
        if (this.mBatteryConsumersCursorWindow != null) {
            this.mBatteryConsumersCursorWindow.close();
        }
        super.finalize();
    }

    public String toString() {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        dump(pw, "");
        pw.flush();
        return sw.toString();
    }

    public static final class Builder {
        private final AggregateBatteryConsumer.Builder[] mAggregateBatteryConsumersBuilders;
        private double mBatteryCapacityMah;
        private final BatteryConsumer.BatteryConsumerDataLayout mBatteryConsumerDataLayout;
        private final CursorWindow mBatteryConsumersCursorWindow;
        private double mBatteryRatedCapacityMah;
        private BatteryStatsHistory mBatteryStatsHistory;
        private long mBatteryTimeRemainingMs;
        private double mBatteryTypicalCapacityMah;
        private long mChargeTimeRemainingMs;
        private final String[] mCustomPowerComponentNames;
        private long mDischargeDurationMs;
        private int mDischargePercentage;
        private double mDischargedPowerLowerBoundMah;
        private double mDischargedPowerUpperBoundMah;
        private final boolean mIncludePowerModels;
        private final boolean mIncludesPowerStateData;
        private final boolean mIncludesProcessStateData;
        private final boolean mIncludesScreenStateData;
        private final double mMinConsumedPowerThreshold;
        private long mStatsDurationMs;
        private long mStatsEndTimestampMs;
        private long mStatsStartTimestampMs;
        private final SparseArray<UidBatteryConsumer.Builder> mUidBatteryConsumerBuilders;
        private final SparseArray<UserBatteryConsumer.Builder> mUserBatteryConsumerBuilders;

        public Builder(String[] customPowerComponentNames) {
            this(customPowerComponentNames, false, false, false, false, SContextConstants.ENVIRONMENT_VALUE_UNKNOWN);
        }

        public Builder(String[] customPowerComponentNames, boolean includePowerModels, boolean includeProcessStateData, boolean includeScreenStateData, boolean includesPowerStateData, double minConsumedPowerThreshold) {
            this.mStatsDurationMs = -1L;
            this.mBatteryTimeRemainingMs = -1L;
            this.mChargeTimeRemainingMs = -1L;
            this.mAggregateBatteryConsumersBuilders = new AggregateBatteryConsumer.Builder[2];
            this.mUidBatteryConsumerBuilders = new SparseArray<>();
            this.mUserBatteryConsumerBuilders = new SparseArray<>();
            this.mBatteryConsumersCursorWindow = new CursorWindow((String) null, BatteryUsageStats.BATTERY_CONSUMER_CURSOR_WINDOW_SIZE);
            this.mBatteryConsumerDataLayout = BatteryConsumer.createBatteryConsumerDataLayout(customPowerComponentNames, includePowerModels, includeProcessStateData, includeScreenStateData, includesPowerStateData);
            this.mBatteryConsumersCursorWindow.setNumColumns(this.mBatteryConsumerDataLayout.columnCount);
            this.mCustomPowerComponentNames = customPowerComponentNames;
            this.mIncludePowerModels = includePowerModels;
            this.mIncludesProcessStateData = includeProcessStateData;
            this.mIncludesScreenStateData = includeScreenStateData;
            this.mIncludesPowerStateData = includesPowerStateData;
            this.mMinConsumedPowerThreshold = minConsumedPowerThreshold;
            for (int scope = 0; scope < 2; scope++) {
                BatteryConsumer.BatteryConsumerData data = BatteryConsumer.BatteryConsumerData.create(this.mBatteryConsumersCursorWindow, this.mBatteryConsumerDataLayout);
                this.mAggregateBatteryConsumersBuilders[scope] = new AggregateBatteryConsumer.Builder(data, scope, this.mMinConsumedPowerThreshold);
            }
        }

        public boolean isProcessStateDataNeeded() {
            return this.mIncludesProcessStateData;
        }

        public boolean isScreenStateDataNeeded() {
            return this.mIncludesScreenStateData;
        }

        public boolean isPowerStateDataNeeded() {
            return this.mIncludesPowerStateData;
        }

        public boolean isSupportedCustomPowerComponent(int componentId) {
            return componentId >= 1000 && componentId < this.mBatteryConsumerDataLayout.customPowerComponentCount + 1000;
        }

        public BatteryUsageStats build() {
            return new BatteryUsageStats(this);
        }

        public Builder setBatteryCapacity(double batteryCapacityMah) {
            this.mBatteryCapacityMah = batteryCapacityMah;
            return this;
        }

        public Builder setBatteryRatedCapacity(double batteryRatedCapacityMah) {
            this.mBatteryRatedCapacityMah = batteryRatedCapacityMah;
            return this;
        }

        public Builder setBatteryTypicalCapacity(double batteryTypicalCapacityMah) {
            this.mBatteryTypicalCapacityMah = batteryTypicalCapacityMah;
            return this;
        }

        public Builder setStatsStartTimestamp(long statsStartTimestampMs) {
            this.mStatsStartTimestampMs = statsStartTimestampMs;
            return this;
        }

        public Builder setStatsEndTimestamp(long statsEndTimestampMs) {
            this.mStatsEndTimestampMs = statsEndTimestampMs;
            return this;
        }

        public Builder setStatsDuration(long statsDurationMs) {
            this.mStatsDurationMs = statsDurationMs;
            return this;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public long getStatsDuration() {
            if (this.mStatsDurationMs != -1) {
                return this.mStatsDurationMs;
            }
            return this.mStatsEndTimestampMs - this.mStatsStartTimestampMs;
        }

        public Builder setDischargePercentage(int dischargePercentage) {
            this.mDischargePercentage = dischargePercentage;
            return this;
        }

        public Builder setDischargedPowerRange(double dischargedPowerLowerBoundMah, double dischargedPowerUpperBoundMah) {
            this.mDischargedPowerLowerBoundMah = dischargedPowerLowerBoundMah;
            this.mDischargedPowerUpperBoundMah = dischargedPowerUpperBoundMah;
            return this;
        }

        public Builder setDischargeDurationMs(long durationMs) {
            this.mDischargeDurationMs = durationMs;
            return this;
        }

        public Builder setBatteryTimeRemainingMs(long batteryTimeRemainingMs) {
            this.mBatteryTimeRemainingMs = batteryTimeRemainingMs;
            return this;
        }

        public Builder setChargeTimeRemainingMs(long chargeTimeRemainingMs) {
            this.mChargeTimeRemainingMs = chargeTimeRemainingMs;
            return this;
        }

        public Builder setBatteryHistory(BatteryStatsHistory batteryStatsHistory) {
            this.mBatteryStatsHistory = batteryStatsHistory;
            return this;
        }

        public AggregateBatteryConsumer.Builder getAggregateBatteryConsumerBuilder(int scope) {
            return this.mAggregateBatteryConsumersBuilders[scope];
        }

        public UidBatteryConsumer.Builder getOrCreateUidBatteryConsumerBuilder(BatteryStats.Uid batteryStatsUid) {
            int uid = batteryStatsUid.getUid();
            UidBatteryConsumer.Builder builder = this.mUidBatteryConsumerBuilders.get(uid);
            if (builder == null) {
                BatteryConsumer.BatteryConsumerData data = BatteryConsumer.BatteryConsumerData.create(this.mBatteryConsumersCursorWindow, this.mBatteryConsumerDataLayout);
                UidBatteryConsumer.Builder builder2 = new UidBatteryConsumer.Builder(data, batteryStatsUid, this.mMinConsumedPowerThreshold);
                this.mUidBatteryConsumerBuilders.put(uid, builder2);
                return builder2;
            }
            return builder;
        }

        public UidBatteryConsumer.Builder getOrCreateUidBatteryConsumerBuilder(int uid) {
            UidBatteryConsumer.Builder builder = this.mUidBatteryConsumerBuilders.get(uid);
            if (builder == null) {
                BatteryConsumer.BatteryConsumerData data = BatteryConsumer.BatteryConsumerData.create(this.mBatteryConsumersCursorWindow, this.mBatteryConsumerDataLayout);
                UidBatteryConsumer.Builder builder2 = new UidBatteryConsumer.Builder(data, uid, this.mMinConsumedPowerThreshold);
                this.mUidBatteryConsumerBuilders.put(uid, builder2);
                return builder2;
            }
            return builder;
        }

        public UserBatteryConsumer.Builder getOrCreateUserBatteryConsumerBuilder(int userId) {
            UserBatteryConsumer.Builder builder = this.mUserBatteryConsumerBuilders.get(userId);
            if (builder == null) {
                BatteryConsumer.BatteryConsumerData data = BatteryConsumer.BatteryConsumerData.create(this.mBatteryConsumersCursorWindow, this.mBatteryConsumerDataLayout);
                UserBatteryConsumer.Builder builder2 = new UserBatteryConsumer.Builder(data, userId, this.mMinConsumedPowerThreshold);
                this.mUserBatteryConsumerBuilders.put(userId, builder2);
                return builder2;
            }
            return builder;
        }

        public SparseArray<UidBatteryConsumer.Builder> getUidBatteryConsumerBuilders() {
            return this.mUidBatteryConsumerBuilders;
        }

        public Builder add(BatteryUsageStats stats) {
            if (!Arrays.equals(this.mCustomPowerComponentNames, stats.mCustomPowerComponentNames)) {
                throw new IllegalArgumentException("BatteryUsageStats have different custom power components");
            }
            if (this.mIncludesProcessStateData && !stats.mIncludesProcessStateData) {
                throw new IllegalArgumentException("Added BatteryUsageStats does not include process state data");
            }
            if (this.mUserBatteryConsumerBuilders.size() != 0 || !stats.getUserBatteryConsumers().isEmpty()) {
                throw new UnsupportedOperationException("Combining UserBatteryConsumers is not supported");
            }
            this.mDischargedPowerLowerBoundMah += stats.mDischargedPowerLowerBound;
            this.mDischargedPowerUpperBoundMah += stats.mDischargedPowerUpperBound;
            this.mDischargePercentage += stats.mDischargePercentage;
            this.mDischargeDurationMs += stats.mDischargeDurationMs;
            this.mStatsDurationMs = getStatsDuration() + stats.getStatsDuration();
            if (this.mStatsStartTimestampMs == 0 || stats.mStatsStartTimestampMs < this.mStatsStartTimestampMs) {
                this.mStatsStartTimestampMs = stats.mStatsStartTimestampMs;
            }
            boolean addingLaterSnapshot = stats.mStatsEndTimestampMs > this.mStatsEndTimestampMs;
            if (addingLaterSnapshot) {
                this.mStatsEndTimestampMs = stats.mStatsEndTimestampMs;
            }
            for (int scope = 0; scope < 2; scope++) {
                getAggregateBatteryConsumerBuilder(scope).add(stats.mAggregateBatteryConsumers[scope]);
            }
            for (UidBatteryConsumer consumer : stats.getUidBatteryConsumers()) {
                getOrCreateUidBatteryConsumerBuilder(consumer.getUid()).add(consumer);
            }
            if (addingLaterSnapshot) {
                this.mBatteryCapacityMah = stats.mBatteryCapacityMah;
                this.mBatteryTimeRemainingMs = stats.mBatteryTimeRemainingMs;
                this.mChargeTimeRemainingMs = stats.mChargeTimeRemainingMs;
            }
            return this;
        }

        void dump(PrintWriter writer) {
            int numRows = this.mBatteryConsumersCursorWindow.getNumRows();
            int numColumns = this.mBatteryConsumerDataLayout.columnCount;
            for (int i = 0; i < numRows; i++) {
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < numColumns; j++) {
                    int type = this.mBatteryConsumersCursorWindow.getType(i, j);
                    switch (type) {
                        case 0:
                            sb.append("null, ");
                            break;
                        case 1:
                            sb.append(this.mBatteryConsumersCursorWindow.getInt(i, j)).append(", ");
                            break;
                        case 2:
                            sb.append(this.mBatteryConsumersCursorWindow.getFloat(i, j)).append(", ");
                            break;
                        case 3:
                            sb.append(this.mBatteryConsumersCursorWindow.getString(i, j)).append(", ");
                            break;
                        case 4:
                            sb.append("BLOB, ");
                            break;
                    }
                }
                int j2 = sb.length();
                sb.setLength(j2 - 2);
                writer.println(sb);
            }
        }
    }
}
