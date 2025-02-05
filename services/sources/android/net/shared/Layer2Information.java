package android.net.shared;

import android.net.Layer2InformationParcelable;
import android.net.MacAddress;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public class Layer2Information {
    public final MacAddress mBssid;
    public final String mCluster;
    public final String mL2Key;

    public Layer2Information(String str, String str2, MacAddress macAddress) {
        this.mL2Key = str;
        this.mCluster = str2;
        this.mBssid = macAddress;
    }

    public static Layer2Information fromStableParcelable(Layer2InformationParcelable layer2InformationParcelable) {
        if (layer2InformationParcelable == null) {
            return null;
        }
        return new Layer2Information(layer2InformationParcelable.l2Key, layer2InformationParcelable.cluster, layer2InformationParcelable.bssid);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Layer2Information)) {
            return false;
        }
        Layer2Information layer2Information = (Layer2Information) obj;
        return Objects.equals(this.mL2Key, layer2Information.mL2Key) && Objects.equals(this.mCluster, layer2Information.mCluster) && Objects.equals(this.mBssid, layer2Information.mBssid);
    }

    public int hashCode() {
        return Objects.hash(this.mL2Key, this.mCluster, this.mBssid);
    }

    public Layer2InformationParcelable toStableParcelable() {
        Layer2InformationParcelable layer2InformationParcelable = new Layer2InformationParcelable();
        layer2InformationParcelable.l2Key = this.mL2Key;
        layer2InformationParcelable.cluster = this.mCluster;
        layer2InformationParcelable.bssid = this.mBssid;
        return layer2InformationParcelable;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer("L2Key: ");
        stringBuffer.append(this.mL2Key);
        stringBuffer.append(", Cluster: ");
        stringBuffer.append(this.mCluster);
        stringBuffer.append(", bssid: ");
        stringBuffer.append(this.mBssid);
        return stringBuffer.toString();
    }
}
