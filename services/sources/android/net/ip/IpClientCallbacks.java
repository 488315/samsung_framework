package android.net.ip;

import android.net.DhcpResultsParcelable;
import android.net.LinkProperties;
import android.net.networkstack.aidl.ip.ReachabilityLossInfoParcelable;
import java.util.List;

/* loaded from: classes.dex */
public class IpClientCallbacks {
    public void installPacketFilter(byte[] bArr) {
    }

    public void onIpClientCreated(IIpClient iIpClient) {
    }

    public void onLinkPropertiesChange(LinkProperties linkProperties) {
    }

    public void onNewDhcpResults(DhcpResultsParcelable dhcpResultsParcelable) {
    }

    public void onPostDhcpAction() {
    }

    public void onPreDhcpAction() {
    }

    public void onPreconnectionStart(List list) {
    }

    public void onProvisioningFailure(LinkProperties linkProperties) {
    }

    public void onProvisioningSuccess(LinkProperties linkProperties) {
    }

    public void onQuit() {
    }

    public void onReachabilityLost(String str) {
    }

    public void setFallbackMulticastFilter(boolean z) {
    }

    public void setMaxDtimMultiplier(int i) {
    }

    public void setNeighborDiscoveryOffload(boolean z) {
    }

    public void startReadPacketFilter() {
    }

    public void onReachabilityFailure(ReachabilityLossInfoParcelable reachabilityLossInfoParcelable) {
        onReachabilityLost(reachabilityLossInfoParcelable.message);
    }
}
