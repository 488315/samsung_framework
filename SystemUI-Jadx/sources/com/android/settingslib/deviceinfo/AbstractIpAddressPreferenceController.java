package com.android.settingslib.deviceinfo;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.LinkAddress;
import android.net.LinkProperties;
import com.android.settingslib.core.lifecycle.Lifecycle;
import java.util.Iterator;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public abstract class AbstractIpAddressPreferenceController extends AbstractConnectivityPreferenceController {
    public static final String[] CONNECTIVITY_INTENTS = {"android.net.conn.CONNECTIVITY_CHANGE", "android.net.wifi.LINK_CONFIGURATION_CHANGED", "android.net.wifi.STATE_CHANGE", "android.intent.action.ANY_DATA_STATE"};
    static final String KEY_IP_ADDRESS = "wifi_ip_address";
    public final ConnectivityManager mCM;

    public AbstractIpAddressPreferenceController(Context context, Lifecycle lifecycle) {
        super(context, lifecycle);
        this.mCM = (ConnectivityManager) context.getSystemService(ConnectivityManager.class);
    }

    @Override // com.android.settingslib.deviceinfo.AbstractConnectivityPreferenceController
    public final String[] getConnectivityIntents() {
        return CONNECTIVITY_INTENTS;
    }

    @Override // com.android.settingslib.deviceinfo.AbstractConnectivityPreferenceController
    public final void updateConnectivity() {
        String str;
        ConnectivityManager connectivityManager = this.mCM;
        LinkProperties linkProperties = connectivityManager.getLinkProperties(connectivityManager.getActiveNetwork());
        if (linkProperties != null) {
            Iterator it = linkProperties.getAllLinkAddresses().iterator();
            if (it.hasNext()) {
                StringBuilder sb = new StringBuilder();
                while (it.hasNext()) {
                    sb.append(((LinkAddress) it.next()).getAddress().getHostAddress());
                    if (it.hasNext()) {
                        sb.append("\n");
                    }
                }
                str = sb.toString();
                str.getClass();
                throw null;
            }
        }
        str = null;
        str.getClass();
        throw null;
    }
}
