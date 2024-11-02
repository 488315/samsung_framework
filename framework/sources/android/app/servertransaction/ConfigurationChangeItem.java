package android.app.servertransaction;

import android.app.ClientTransactionHandler;
import android.content.res.CompatibilityInfo;
import android.content.res.Configuration;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

/* loaded from: classes.dex */
public class ConfigurationChangeItem extends ClientTransactionItem {
    public static final Parcelable.Creator<ConfigurationChangeItem> CREATOR = new Parcelable.Creator<ConfigurationChangeItem>() { // from class: android.app.servertransaction.ConfigurationChangeItem.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public ConfigurationChangeItem createFromParcel(Parcel in) {
            return new ConfigurationChangeItem(in);
        }

        @Override // android.os.Parcelable.Creator
        public ConfigurationChangeItem[] newArray(int size) {
            return new ConfigurationChangeItem[size];
        }
    };
    private Configuration mConfiguration;
    private int mDeviceId;

    /* synthetic */ ConfigurationChangeItem(Parcel parcel, ConfigurationChangeItemIA configurationChangeItemIA) {
        this(parcel);
    }

    @Override // android.app.servertransaction.BaseClientRequest
    public void preExecute(ClientTransactionHandler client, IBinder token) {
        CompatibilityInfo.applyOverrideScaleIfNeeded(this.mConfiguration);
        client.updatePendingConfiguration(this.mConfiguration);
    }

    @Override // android.app.servertransaction.BaseClientRequest
    public void execute(ClientTransactionHandler client, IBinder token, PendingTransactionActions pendingActions) {
        client.handleConfigurationChanged(this.mConfiguration, this.mDeviceId);
    }

    private ConfigurationChangeItem() {
    }

    public static ConfigurationChangeItem obtain(Configuration config, int deviceId) {
        ConfigurationChangeItem instance = (ConfigurationChangeItem) ObjectPool.obtain(ConfigurationChangeItem.class);
        if (instance == null) {
            instance = new ConfigurationChangeItem();
        }
        instance.mConfiguration = config;
        instance.mDeviceId = deviceId;
        return instance;
    }

    @Override // android.app.servertransaction.ObjectPoolItem
    public void recycle() {
        this.mConfiguration = null;
        this.mDeviceId = 0;
        ObjectPool.recycle(this);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedObject(this.mConfiguration, flags);
        dest.writeInt(this.mDeviceId);
    }

    private ConfigurationChangeItem(Parcel in) {
        this.mConfiguration = (Configuration) in.readTypedObject(Configuration.CREATOR);
        this.mDeviceId = in.readInt();
    }

    /* renamed from: android.app.servertransaction.ConfigurationChangeItem$1 */
    /* loaded from: classes.dex */
    class AnonymousClass1 implements Parcelable.Creator<ConfigurationChangeItem> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public ConfigurationChangeItem createFromParcel(Parcel in) {
            return new ConfigurationChangeItem(in);
        }

        @Override // android.os.Parcelable.Creator
        public ConfigurationChangeItem[] newArray(int size) {
            return new ConfigurationChangeItem[size];
        }
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ConfigurationChangeItem other = (ConfigurationChangeItem) o;
        if (Objects.equals(this.mConfiguration, other.mConfiguration) && this.mDeviceId == other.mDeviceId) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int result = (17 * 31) + this.mDeviceId;
        return (result * 31) + this.mConfiguration.hashCode();
    }

    public String toString() {
        return "ConfigurationChangeItem{deviceId=" + this.mDeviceId + ", config" + this.mConfiguration + "}";
    }
}
