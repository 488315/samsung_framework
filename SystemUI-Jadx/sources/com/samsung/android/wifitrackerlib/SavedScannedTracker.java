package com.samsung.android.wifitrackerlib;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.hotspot2.PasspointConfiguration;
import android.os.Handler;
import android.util.Pair;
import androidx.core.util.Preconditions;
import androidx.lifecycle.Lifecycle;
import com.android.wifitrackerlib.BaseWifiTracker;
import com.android.wifitrackerlib.PasspointWifiEntry;
import com.android.wifitrackerlib.ScanResultUpdater;
import com.android.wifitrackerlib.StandardWifiEntry;
import com.android.wifitrackerlib.WifiEntry;
import com.android.wifitrackerlib.WifiTrackerInjector;
import com.sec.ims.IMSParameter;
import java.time.Clock;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes3.dex */
public class SavedScannedTracker extends BaseWifiTracker {
    public WifiEntry mConnectedWifiEntry;
    public final Object mLock;
    public final Map mPasspointWifiEntryCache;
    public final List mSavedWifiEntries;
    public final List mStandardWifiEntryCache;
    public final List mSubscriptionWifiEntries;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes3.dex */
    public interface SavedScannedTrackerCallback extends BaseWifiTracker.BaseWifiTrackerCallback {
    }

    public SavedScannedTracker(Lifecycle lifecycle, Context context, WifiManager wifiManager, ConnectivityManager connectivityManager, Handler handler, Handler handler2, Clock clock, long j, long j2, SavedScannedTrackerCallback savedScannedTrackerCallback) {
        this(new WifiTrackerInjector(context), lifecycle, context, wifiManager, connectivityManager, handler, handler2, clock, j, j2, savedScannedTrackerCallback);
    }

    public final void conditionallyUpdateScanResults(boolean z) {
        WifiManager wifiManager = this.mWifiManager;
        if (wifiManager.getWifiState() == 1) {
            updateStandardWifiEntryScans(Collections.emptyList());
            updatePasspointWifiEntryScans(Collections.emptyList());
            return;
        }
        ScanResultUpdater scanResultUpdater = this.mScanResultUpdater;
        long j = this.mMaxScanAgeMillis;
        if (z) {
            scanResultUpdater.update(wifiManager.getScanResults());
        } else {
            j += this.mScanIntervalMillis;
        }
        updateStandardWifiEntryScans(scanResultUpdater.getScanResults(j));
        updatePasspointWifiEntryScans(scanResultUpdater.getScanResults(j));
    }

    public final List getAllWifiEntries() {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(this.mStandardWifiEntryCache);
        arrayList.addAll(this.mPasspointWifiEntryCache.values());
        return arrayList;
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleConfiguredNetworksChangedAction(Intent intent) {
        Preconditions.checkNotNull(intent, "Intent cannot be null!");
        WifiManager wifiManager = this.mWifiManager;
        updateStandardWifiEntryConfigs(wifiManager.getConfiguredNetworks());
        updatePasspointWifiEntryConfigs(wifiManager.getPasspointConfigurations());
        updateWifiEntries();
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleLinkPropertiesChanged(Network network, LinkProperties linkProperties) {
        WifiEntry wifiEntry = this.mConnectedWifiEntry;
        if (wifiEntry != null && wifiEntry.getConnectedState() == 2) {
            this.mConnectedWifiEntry.updateLinkProperties(network, linkProperties);
        }
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleNetworkCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
        Iterator it = ((ArrayList) getAllWifiEntries()).iterator();
        while (it.hasNext()) {
            WifiEntry wifiEntry = (WifiEntry) it.next();
            wifiEntry.onNetworkCapabilitiesChanged(network, networkCapabilities);
            if (wifiEntry.getConnectedState() != 0) {
                this.mConnectedWifiEntry = wifiEntry;
            }
        }
        updateWifiEntries();
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleNetworkLost(Network network) {
        Iterator it = ((ArrayList) getAllWifiEntries()).iterator();
        while (it.hasNext()) {
            ((WifiEntry) it.next()).onNetworkLost(network);
        }
        updateWifiEntries();
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleNetworkStateChangedAction(Intent intent) {
        WifiInfo connectionInfo = this.mWifiManager.getConnectionInfo();
        NetworkInfo networkInfo = (NetworkInfo) intent.getParcelableExtra(IMSParameter.GENERAL.NETWORK_INFO);
        if (connectionInfo != null && networkInfo != null) {
            Iterator it = ((ArrayList) getAllWifiEntries()).iterator();
            while (it.hasNext()) {
                ((WifiEntry) it.next()).onPrimaryWifiInfoChanged(connectionInfo, networkInfo);
            }
        }
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleOnStart() {
        this.mStandardWifiEntryCache.clear();
        this.mPasspointWifiEntryCache.clear();
        WifiManager wifiManager = this.mWifiManager;
        updateStandardWifiEntryConfigs(wifiManager.getConfiguredNetworks());
        updatePasspointWifiEntryConfigs(wifiManager.getPasspointConfigurations());
        this.mScanResultUpdater.update(wifiManager.getScanResults());
        conditionallyUpdateScanResults(true);
        Network currentNetwork = wifiManager.getCurrentNetwork();
        if (currentNetwork != null) {
            ConnectivityManager connectivityManager = this.mConnectivityManager;
            NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(currentNetwork);
            if (networkCapabilities != null) {
                handleNetworkCapabilitiesChanged(currentNetwork, new NetworkCapabilities.Builder(networkCapabilities).setTransportInfo(wifiManager.getConnectionInfo()).build());
            }
            LinkProperties linkProperties = connectivityManager.getLinkProperties(currentNetwork);
            if (linkProperties != null) {
                handleLinkPropertiesChanged(currentNetwork, linkProperties);
            }
        }
        updateWifiEntries();
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleScanResultsAvailableAction(Intent intent) {
        Preconditions.checkNotNull(intent, "Intent cannot be null!");
        conditionallyUpdateScanResults(intent.getBooleanExtra("resultsUpdated", true));
        updateWifiEntries();
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker
    public final void handleWifiStateChangedAction() {
        conditionallyUpdateScanResults(true);
        updateWifiEntries();
    }

    public final void updatePasspointWifiEntryConfigs(List list) {
        Preconditions.checkNotNull(list, "Config list should not be null!");
        Map map = (Map) list.stream().collect(Collectors.toMap(new SavedScannedTracker$$ExternalSyntheticLambda0(1), Function.identity()));
        HashMap hashMap = (HashMap) this.mPasspointWifiEntryCache;
        hashMap.entrySet().removeIf(new SavedScannedTracker$$ExternalSyntheticLambda2(0, map));
        for (Map.Entry entry : map.entrySet()) {
            hashMap.put((String) entry.getKey(), new PasspointWifiEntry(this.mInjector, this.mContext, this.mMainHandler, (PasspointConfiguration) map.get(entry.getKey()), this.mWifiManager, true));
        }
    }

    public final void updatePasspointWifiEntryScans(List list) {
        Map map;
        Preconditions.checkNotNull(list, "Scan Result list should not be null!");
        TreeSet treeSet = new TreeSet();
        Iterator it = this.mWifiManager.getAllMatchingWifiConfigs(list).iterator();
        while (true) {
            boolean hasNext = it.hasNext();
            map = this.mPasspointWifiEntryCache;
            if (!hasNext) {
                break;
            }
            Pair pair = (Pair) it.next();
            WifiConfiguration wifiConfiguration = (WifiConfiguration) pair.first;
            String uniqueIdToPasspointWifiEntryKey = PasspointWifiEntry.uniqueIdToPasspointWifiEntryKey(wifiConfiguration.getKey());
            treeSet.add(uniqueIdToPasspointWifiEntryKey);
            HashMap hashMap = (HashMap) map;
            if (hashMap.containsKey(uniqueIdToPasspointWifiEntryKey)) {
                ((PasspointWifiEntry) hashMap.get(uniqueIdToPasspointWifiEntryKey)).updateScanResultInfo(wifiConfiguration, (List) ((Map) pair.second).get(0), (List) ((Map) pair.second).get(1));
            }
        }
        for (PasspointWifiEntry passpointWifiEntry : ((HashMap) map).values()) {
            if (!treeSet.contains(passpointWifiEntry.mKey)) {
                passpointWifiEntry.updateScanResultInfo(null, null, null);
            }
        }
    }

    public final void updateStandardWifiEntryConfigs(List list) {
        Preconditions.checkNotNull(list, "Config list should not be null!");
        Map map = (Map) list.stream().filter(new SavedScannedTracker$$ExternalSyntheticLambda3()).collect(Collectors.groupingBy(new SavedScannedTracker$$ExternalSyntheticLambda0(2)));
        SavedScannedTracker$$ExternalSyntheticLambda2 savedScannedTracker$$ExternalSyntheticLambda2 = new SavedScannedTracker$$ExternalSyntheticLambda2(1, map);
        ArrayList arrayList = (ArrayList) this.mStandardWifiEntryCache;
        arrayList.removeIf(savedScannedTracker$$ExternalSyntheticLambda2);
        for (Map.Entry entry : map.entrySet()) {
            arrayList.add(new StandardWifiEntry(this.mInjector, this.mContext, this.mMainHandler, (StandardWifiEntry.StandardWifiEntryKey) entry.getKey(), (List) map.get(entry.getKey()), null, this.mWifiManager, true));
        }
    }

    public final void updateStandardWifiEntryScans(List list) {
        Preconditions.checkNotNull(list, "Scan Result list should not be null!");
        final Map map = (Map) list.stream().collect(Collectors.groupingBy(new SavedScannedTracker$$ExternalSyntheticLambda0(0)));
        ((ArrayList) this.mStandardWifiEntryCache).forEach(new Consumer() { // from class: com.samsung.android.wifitrackerlib.SavedScannedTracker$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                StandardWifiEntry standardWifiEntry = (StandardWifiEntry) obj;
                standardWifiEntry.updateScanResultInfo((List) map.get(standardWifiEntry.mKey.mScanResultKey));
            }
        });
    }

    public final void updateWifiEntries() {
        synchronized (this.mLock) {
            this.mConnectedWifiEntry = null;
            for (WifiEntry wifiEntry : this.mStandardWifiEntryCache) {
                if (wifiEntry.getConnectedState() != 0) {
                    this.mConnectedWifiEntry = wifiEntry;
                }
            }
            for (WifiEntry wifiEntry2 : this.mSubscriptionWifiEntries) {
                if (wifiEntry2.getConnectedState() != 0) {
                    this.mConnectedWifiEntry = wifiEntry2;
                }
            }
            this.mSavedWifiEntries.clear();
            this.mSavedWifiEntries.addAll(this.mStandardWifiEntryCache);
            List list = this.mSavedWifiEntries;
            Comparator comparator = WifiEntry.TITLE_COMPARATOR;
            Collections.sort(list, comparator);
            this.mSubscriptionWifiEntries.clear();
            this.mSubscriptionWifiEntries.addAll(this.mPasspointWifiEntryCache.values());
            Collections.sort(this.mSubscriptionWifiEntries, comparator);
            if (BaseWifiTracker.sVerboseLogging) {
                Arrays.toString(this.mSavedWifiEntries.toArray());
                Arrays.toString(this.mSubscriptionWifiEntries.toArray());
            }
        }
    }

    public SavedScannedTracker(WifiTrackerInjector wifiTrackerInjector, Lifecycle lifecycle, Context context, WifiManager wifiManager, ConnectivityManager connectivityManager, Handler handler, Handler handler2, Clock clock, long j, long j2, SavedScannedTrackerCallback savedScannedTrackerCallback) {
        super(wifiTrackerInjector, lifecycle, context, wifiManager, connectivityManager, handler, handler2, clock, j, j2, savedScannedTrackerCallback, "SavedScannedTracker");
        this.mLock = new Object();
        this.mSavedWifiEntries = new ArrayList();
        this.mSubscriptionWifiEntries = new ArrayList();
        this.mStandardWifiEntryCache = new ArrayList();
        this.mPasspointWifiEntryCache = new HashMap();
        handleOnStart();
    }
}
