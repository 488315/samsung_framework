package com.android.wifitrackerlib;

import android.net.wifi.WifiConfiguration;
import android.net.wifi.hotspot2.PasspointConfiguration;
import android.util.ArrayMap;
import com.samsung.android.wifi.SemWifiConfiguration;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final /* synthetic */ class WifiPickerTracker$$ExternalSyntheticLambda8 implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ WifiPickerTracker f$0;
    public final /* synthetic */ Map f$1;

    public /* synthetic */ WifiPickerTracker$$ExternalSyntheticLambda8(WifiPickerTracker wifiPickerTracker, Map map, int i) {
        this.$r8$classId = i;
        this.f$0 = wifiPickerTracker;
        this.f$1 = map;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                WifiPickerTracker wifiPickerTracker = this.f$0;
                Map map = this.f$1;
                StandardWifiEntry standardWifiEntry = (StandardWifiEntry) obj;
                standardWifiEntry.updateConfig((List) ((ArrayMap) wifiPickerTracker.mSuggestedConfigCache).get(standardWifiEntry.mKey));
                standardWifiEntry.semUpdateSemWifiConfig(map);
                return !standardWifiEntry.isSuggestion();
            default:
                WifiPickerTracker wifiPickerTracker2 = this.f$0;
                Map map2 = this.f$1;
                wifiPickerTracker2.getClass();
                PasspointWifiEntry passpointWifiEntry = (PasspointWifiEntry) ((Map.Entry) obj).getValue();
                passpointWifiEntry.updatePasspointConfig((PasspointConfiguration) ((ArrayMap) wifiPickerTracker2.mPasspointConfigCache).get(passpointWifiEntry.mKey));
                synchronized (passpointWifiEntry) {
                    WifiConfiguration wifiConfiguration = passpointWifiEntry.mWifiConfig;
                    if (wifiConfiguration != null) {
                        passpointWifiEntry.semUpdateFlags((SemWifiConfiguration) map2.get(wifiConfiguration.getKey()));
                    }
                    passpointWifiEntry.notifyOnUpdated();
                }
                if (!passpointWifiEntry.isSubscription() && !passpointWifiEntry.isSuggestion()) {
                    return true;
                }
                return false;
        }
    }
}
