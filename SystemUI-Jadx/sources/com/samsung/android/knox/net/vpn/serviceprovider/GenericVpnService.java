package com.samsung.android.knox.net.vpn.serviceprovider;

import android.app.AppGlobals;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.IVpnManager;
import android.net.IpPrefix;
import android.net.LinkAddress;
import android.net.RouteInfo;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.system.OsConstants;
import android.util.Log;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.internal.net.NetworkUtilsInternal;
import com.android.internal.net.VpnConfig;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.container.RCPPolicy$$ExternalSyntheticOutline0;
import com.samsung.android.knox.license.EnterpriseLicenseManager;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes3.dex */
public final class GenericVpnService extends Service {
    public static final String SERVICE_INTERFACE = "android.net.VpnService";
    public static final boolean SYSTEM_VPN = true;
    public static final String TAG = "GenericVpnService";
    public static String mVpnProfileName;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes3.dex */
    public class Callback extends Binder {
        public /* synthetic */ Callback(GenericVpnService genericVpnService, int i) {
            this();
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            super.onTransact(i, parcel, parcel2, i2);
            if (i == 16777215) {
                GenericVpnService.this.stopSelf();
                return true;
            }
            return false;
        }

        private Callback() {
        }
    }

    public static void check(InetAddress inetAddress, int i) {
        if (!inetAddress.isLoopbackAddress()) {
            if (inetAddress instanceof Inet4Address) {
                if (i < 0 || i > 32) {
                    throw new IllegalArgumentException("Bad prefixLength");
                }
                return;
            } else {
                if (inetAddress instanceof Inet6Address) {
                    if (i >= 0 && i <= 128) {
                        return;
                    } else {
                        throw new IllegalArgumentException("Bad prefixLength");
                    }
                }
                throw new IllegalArgumentException("Unsupported family");
            }
        }
        throw new IllegalArgumentException("Bad address");
    }

    public static boolean checkIfAdminHasVpnPermission() {
        try {
            if (AppGlobals.getPackageManager().checkUidPermission(KnoxVpnPolicyConstants.NETWORK_TRAFFIC_AGENT_PERMISSION, Process.myUid()) == 0) {
                return true;
            }
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception: checkIfAdminHasVpnPermission "), TAG);
        }
        Log.d(TAG, "No permission grants found for UID " + Process.myUid());
        throw new SecurityException(Process.myUid() + " does not have com.samsung.android.knox.permission.KNOX_NETWORK_TRAFFIC_AGENT");
    }

    public static IVpnManager getService() {
        return IVpnManager.Stub.asInterface(ServiceManager.getService("vpn_management"));
    }

    public static Intent prepare(Context context, String str, boolean z, boolean z2) {
        checkIfAdminHasVpnPermission();
        EnterpriseLicenseManager.log(new ContextInfo(Process.myUid()), "GenericVpnService.prepare");
        StringBuilder sb = new StringBuilder("prepare is getting called ");
        sb.append(str);
        sb.append("isconnecting value is ");
        sb.append(z2);
        sb.append("type value is ");
        ActionBarContextView$$ExternalSyntheticOutline0.m(sb, z, TAG);
        if (context != null && str != null && (context instanceof GenericVpnContext)) {
            mVpnProfileName = str;
            boolean isMetaEnabled = ((GenericVpnContext) context).isMetaEnabled();
            if (z2) {
                return null;
            }
            try {
                if (getService().prepareEnterpriseVpnExt(str, isMetaEnabled)) {
                    return null;
                }
            } catch (Exception e) {
                Log.e(TAG, "Exception in prepare : " + Log.getStackTraceString(e));
            }
            if (!z) {
                return null;
            }
            return VpnConfig.getIntentForConfirmation();
        }
        Log.e(TAG, "Invalid profile name or context passed in prepare()");
        return null;
    }

    public final boolean addAddress(InetAddress inetAddress, int i) {
        checkIfAdminHasVpnPermission();
        EnterpriseLicenseManager.log(new ContextInfo(Process.myUid()), "GenericVpnService.addAddress");
        check(inetAddress, i);
        try {
            return getService().addVpnAddress(inetAddress.getHostAddress(), i);
        } catch (RemoteException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        if (intent != null && SERVICE_INTERFACE.equals(intent.getAction())) {
            return new Callback(this, 0);
        }
        return null;
    }

    public final void onRevoke() {
        stopSelf();
    }

    public final boolean protect(int i) {
        checkIfAdminHasVpnPermission();
        EnterpriseLicenseManager.log(new ContextInfo(Process.myUid()), "GenericVpnService.protect");
        try {
            if (getService() != null) {
                if (!getService().getChainingEnabledForProfile(Binder.getCallingUid())) {
                    return NetworkUtilsInternal.protectFromVpn(i);
                }
                Log.d(TAG, "protect is not going to be called for " + Binder.getCallingUid());
                return true;
            }
            return NetworkUtilsInternal.protectFromVpn(i);
        } catch (Exception unused) {
            return false;
        }
    }

    public final boolean removeAddress(InetAddress inetAddress, int i) {
        checkIfAdminHasVpnPermission();
        EnterpriseLicenseManager.log(new ContextInfo(Process.myUid()), "GenericVpnService.removeAddress");
        check(inetAddress, i);
        try {
            return getService().removeVpnAddress(inetAddress.getHostAddress(), i);
        } catch (RemoteException e) {
            throw new IllegalStateException(e);
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes3.dex */
    public class Builder {
        public final List<LinkAddress> mAddresses;
        public final VpnConfig mConfig;
        public final List<RouteInfo> mRoutes;

        public Builder() {
            VpnConfig vpnConfig = new VpnConfig();
            this.mConfig = vpnConfig;
            this.mAddresses = new ArrayList();
            this.mRoutes = new ArrayList();
            vpnConfig.user = GenericVpnService.this.getClass().getName();
        }

        public final Builder addAddress(InetAddress inetAddress, int i) {
            GenericVpnService.checkIfAdminHasVpnPermission();
            EnterpriseLicenseManager.log(new ContextInfo(Process.myUid()), "GenericVpnService.addAddress");
            GenericVpnService.check(inetAddress, i);
            if (!inetAddress.isAnyLocalAddress()) {
                this.mAddresses.add(new LinkAddress(inetAddress, i));
                return this;
            }
            throw new IllegalArgumentException("Bad address");
        }

        public final Builder addDnsServer(InetAddress inetAddress) {
            GenericVpnService.checkIfAdminHasVpnPermission();
            EnterpriseLicenseManager.log(new ContextInfo(Process.myUid()), "GenericVpnService.addDnsServer");
            if (!inetAddress.isLoopbackAddress() && !inetAddress.isAnyLocalAddress()) {
                VpnConfig vpnConfig = this.mConfig;
                if (vpnConfig.dnsServers == null) {
                    vpnConfig.dnsServers = new ArrayList();
                }
                this.mConfig.dnsServers.add(inetAddress.getHostAddress());
                return this;
            }
            throw new IllegalArgumentException("Bad address");
        }

        public final Builder addRoute(InetAddress inetAddress, int i) {
            GenericVpnService.checkIfAdminHasVpnPermission();
            EnterpriseLicenseManager.log(new ContextInfo(Process.myUid()), "GenericVpnService.addRoute");
            GenericVpnService.check(inetAddress, i);
            int i2 = i / 8;
            byte[] address = inetAddress.getAddress();
            if (i2 < address.length) {
                address[i2] = (byte) (address[i2] << (i % 8));
                while (i2 < address.length) {
                    if (address[i2] != 0) {
                        throw new IllegalArgumentException("Bad address");
                    }
                    i2++;
                }
            }
            this.mRoutes.add(new RouteInfo(new IpPrefix(inetAddress, i), null, null, 1));
            return this;
        }

        public final Builder addSearchDomain(String str) {
            GenericVpnService.checkIfAdminHasVpnPermission();
            EnterpriseLicenseManager.log(new ContextInfo(Process.myUid()), "GenericVpnService.addSearchDomain");
            VpnConfig vpnConfig = this.mConfig;
            if (vpnConfig.searchDomains == null) {
                vpnConfig.searchDomains = new ArrayList();
            }
            this.mConfig.searchDomains.add(str);
            return this;
        }

        public final Builder allowBypass() {
            GenericVpnService.checkIfAdminHasVpnPermission();
            EnterpriseLicenseManager.log(new ContextInfo(Process.myUid()), "GenericVpnService.allowBypass");
            this.mConfig.allowBypass = true;
            return this;
        }

        public final Builder allowFamily(int i) {
            GenericVpnService.checkIfAdminHasVpnPermission();
            EnterpriseLicenseManager.log(new ContextInfo(Process.myUid()), "GenericVpnService.allowFamily");
            if (i == OsConstants.AF_INET) {
                this.mConfig.allowIPv4 = true;
            } else if (i == OsConstants.AF_INET6) {
                this.mConfig.allowIPv6 = true;
            } else {
                throw new IllegalArgumentException(i + " is neither " + OsConstants.AF_INET + " nor " + OsConstants.AF_INET6);
            }
            return this;
        }

        public final ParcelFileDescriptor establish() {
            GenericVpnService.checkIfAdminHasVpnPermission();
            EnterpriseLicenseManager.log(new ContextInfo(Process.myUid()), "GenericVpnService.establish");
            StringBuilder sb = new StringBuilder("establish is getting called : mVpnProfileName value is ");
            sb.append(GenericVpnService.mVpnProfileName);
            sb.append("config session value is ");
            ExifInterface$$ExternalSyntheticOutline0.m(sb, this.mConfig.session, GenericVpnService.TAG);
            VpnConfig vpnConfig = this.mConfig;
            vpnConfig.addresses = this.mAddresses;
            vpnConfig.routes = this.mRoutes;
            try {
                if (GenericVpnService.getService() != null) {
                    return GenericVpnService.getService().establishVpn(this.mConfig);
                }
                return null;
            } catch (Exception unused) {
                throw new IllegalArgumentException("VPN establish failed");
            }
        }

        public final Builder setBlocking(boolean z) {
            GenericVpnService.checkIfAdminHasVpnPermission();
            EnterpriseLicenseManager.log(new ContextInfo(Process.myUid()), "GenericVpnService.setBlocking");
            this.mConfig.blocking = z;
            return this;
        }

        public final Builder setConfigureIntent(PendingIntent pendingIntent) {
            GenericVpnService.checkIfAdminHasVpnPermission();
            EnterpriseLicenseManager.log(new ContextInfo(Process.myUid()), "GenericVpnService.setConfigureIntent");
            this.mConfig.configureIntent = pendingIntent;
            return this;
        }

        public final Builder setMtu(int i) {
            GenericVpnService.checkIfAdminHasVpnPermission();
            EnterpriseLicenseManager.log(new ContextInfo(Process.myUid()), "GenericVpnService.setMtu");
            if (i > 0) {
                this.mConfig.mtu = i;
                return this;
            }
            throw new IllegalArgumentException("Bad mtu");
        }

        public final Builder setSession(String str) {
            GenericVpnService.checkIfAdminHasVpnPermission();
            EnterpriseLicenseManager.log(new ContextInfo(Process.myUid()), "GenericVpnService.setSession");
            this.mConfig.session = str;
            return this;
        }

        public final Builder addAddress(String str, int i) {
            return addAddress(InetAddress.parseNumericAddress(str), i);
        }

        public final Builder addDnsServer(String str) {
            return addDnsServer(InetAddress.parseNumericAddress(str));
        }

        public final Builder addRoute(String str, int i) {
            return addRoute(InetAddress.parseNumericAddress(str), i);
        }

        public final Builder addAllowedApplication(String str) {
            return this;
        }

        public final Builder addDisallowedApplication(String str) {
            return this;
        }
    }

    public final boolean protect(Socket socket) {
        return protect(socket.getFileDescriptor$().getInt$());
    }

    public final boolean protect(DatagramSocket datagramSocket) {
        return protect(datagramSocket.getFileDescriptor$().getInt$());
    }
}
