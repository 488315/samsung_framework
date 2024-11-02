package com.samsung.android.knox.net.firewall;

import com.samsung.android.knox.AppIdentity;
import com.samsung.android.knox.net.firewall.Firewall;
import com.samsung.android.knox.net.firewall.FirewallRule;
import com.sec.ims.settings.ImsProfile;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes3.dex */
public final class FirewallRuleTranslator {
    public static String[] networkInterfaceOptions = {ImsProfile.PDN_WIFI, "data", "*"};
    public static String[] portLocationOptions = {"remote", "local", "*"};

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* renamed from: com.samsung.android.knox.net.firewall.FirewallRuleTranslator$1, reason: invalid class name */
    /* loaded from: classes3.dex */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$samsung$android$knox$net$firewall$FirewallRule$RuleType;

        static {
            int[] iArr = new int[FirewallRule.RuleType.values().length];
            $SwitchMap$com$samsung$android$knox$net$firewall$FirewallRule$RuleType = iArr;
            try {
                iArr[FirewallRule.RuleType.ALLOW.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$samsung$android$knox$net$firewall$FirewallRule$RuleType[FirewallRule.RuleType.DENY.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$samsung$android$knox$net$firewall$FirewallRule$RuleType[FirewallRule.RuleType.REDIRECT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$samsung$android$knox$net$firewall$FirewallRule$RuleType[FirewallRule.RuleType.REDIRECT_EXCEPTION.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public static String convertNetworkInterface(Firewall.NetworkInterface networkInterface) {
        if (networkInterface == null) {
            return null;
        }
        if (networkInterface.equals(Firewall.NetworkInterface.WIFI_DATA_ONLY)) {
            return networkInterfaceOptions[0];
        }
        if (networkInterface.equals(Firewall.NetworkInterface.MOBILE_DATA_ONLY)) {
            return networkInterfaceOptions[1];
        }
        return networkInterfaceOptions[2];
    }

    public static String convertPortLocation(Firewall.PortLocation portLocation) {
        if (portLocation == null) {
            return null;
        }
        if (portLocation.equals(Firewall.PortLocation.REMOTE)) {
            return portLocationOptions[0];
        }
        if (portLocation.equals(Firewall.PortLocation.LOCAL)) {
            return portLocationOptions[1];
        }
        return portLocationOptions[2];
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0079  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0099  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.samsung.android.knox.net.firewall.FirewallRule translateAllowRule(java.lang.String r11) {
        /*
            r0 = 0
            if (r11 != 0) goto L4
            return r0
        L4:
            java.lang.String r1 = ";"
            java.lang.String[] r11 = r11.split(r1)
            int r1 = r11.length
            r2 = 2
            if (r1 < r2) goto Lba
            int r1 = r11.length
            r3 = 4
            if (r1 <= r3) goto L14
            goto Lba
        L14:
            com.samsung.android.knox.net.firewall.Firewall$PortLocation r1 = com.samsung.android.knox.net.firewall.Firewall.PortLocation.REMOTE
            com.samsung.android.knox.net.firewall.Firewall$NetworkInterface r1 = com.samsung.android.knox.net.firewall.Firewall.NetworkInterface.ALL_NETWORKS
            r3 = 0
            r4 = r11[r3]
            java.lang.String r5 = ":"
            int r4 = r4.lastIndexOf(r5)
            r5 = -1
            if (r4 != r5) goto L25
            return r0
        L25:
            r5 = r11[r3]
            java.lang.String r5 = r5.substring(r3, r4)
            r6 = r11[r3]
            r7 = 1
            int r4 = r4 + r7
            java.lang.String r4 = r6.substring(r4)
            java.lang.String[] r6 = com.samsung.android.knox.net.firewall.FirewallRuleTranslator.portLocationOptions
            r6 = r6[r3]
            r8 = r11[r7]
            boolean r6 = r6.equals(r8)
            if (r6 == 0) goto L42
            com.samsung.android.knox.net.firewall.Firewall$PortLocation r6 = com.samsung.android.knox.net.firewall.Firewall.PortLocation.REMOTE
            goto L53
        L42:
            java.lang.String[] r6 = com.samsung.android.knox.net.firewall.FirewallRuleTranslator.portLocationOptions
            r6 = r6[r7]
            r8 = r11[r7]
            boolean r6 = r6.equals(r8)
            if (r6 == 0) goto L51
            com.samsung.android.knox.net.firewall.Firewall$PortLocation r6 = com.samsung.android.knox.net.firewall.Firewall.PortLocation.LOCAL
            goto L53
        L51:
            com.samsung.android.knox.net.firewall.Firewall$PortLocation r6 = com.samsung.android.knox.net.firewall.Firewall.PortLocation.ALL
        L53:
            int r8 = r11.length
            r9 = 3
            if (r8 != r9) goto L75
            java.lang.String[] r8 = com.samsung.android.knox.net.firewall.FirewallRuleTranslator.networkInterfaceOptions
            r8 = r8[r3]
            r10 = r11[r2]
            boolean r8 = r8.equals(r10)
            if (r8 == 0) goto L66
            com.samsung.android.knox.net.firewall.Firewall$NetworkInterface r8 = com.samsung.android.knox.net.firewall.Firewall.NetworkInterface.WIFI_DATA_ONLY
            goto L76
        L66:
            java.lang.String[] r8 = com.samsung.android.knox.net.firewall.FirewallRuleTranslator.networkInterfaceOptions
            r8 = r8[r7]
            r10 = r11[r2]
            boolean r8 = r8.equals(r10)
            if (r8 == 0) goto L75
            com.samsung.android.knox.net.firewall.Firewall$NetworkInterface r8 = com.samsung.android.knox.net.firewall.Firewall.NetworkInterface.MOBILE_DATA_ONLY
            goto L76
        L75:
            r8 = r1
        L76:
            int r10 = r11.length
            if (r10 <= r9) goto L99
            r2 = r11[r2]
            java.lang.String[] r8 = com.samsung.android.knox.net.firewall.FirewallRuleTranslator.networkInterfaceOptions
            r3 = r8[r3]
            r8 = r11[r9]
            boolean r3 = r3.equals(r8)
            if (r3 == 0) goto L8a
            com.samsung.android.knox.net.firewall.Firewall$NetworkInterface r1 = com.samsung.android.knox.net.firewall.Firewall.NetworkInterface.WIFI_DATA_ONLY
            goto L9c
        L8a:
            java.lang.String[] r3 = com.samsung.android.knox.net.firewall.FirewallRuleTranslator.networkInterfaceOptions
            r3 = r3[r7]
            r11 = r11[r9]
            boolean r11 = r3.equals(r11)
            if (r11 == 0) goto L9c
            com.samsung.android.knox.net.firewall.Firewall$NetworkInterface r1 = com.samsung.android.knox.net.firewall.Firewall.NetworkInterface.MOBILE_DATA_ONLY
            goto L9c
        L99:
            java.lang.String r2 = "*"
            r1 = r8
        L9c:
            com.samsung.android.knox.net.firewall.FirewallRule r11 = new com.samsung.android.knox.net.firewall.FirewallRule
            com.samsung.android.knox.net.firewall.FirewallRule$RuleType r3 = com.samsung.android.knox.net.firewall.FirewallRule.RuleType.ALLOW
            com.samsung.android.knox.net.firewall.Firewall$AddressType r7 = com.samsung.android.knox.net.firewall.Firewall.AddressType.IPV4
            r11.<init>(r3, r7)
            com.samsung.android.knox.AppIdentity r3 = new com.samsung.android.knox.AppIdentity
            r3.<init>(r2, r0)
            r11.setIpAddress(r5)
            r11.setPortNumber(r4)
            r11.setPortLocation(r6)
            r11.setApplication(r3)
            r11.setNetworkInterface(r1)
            return r11
        Lba:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.knox.net.firewall.FirewallRuleTranslator.translateAllowRule(java.lang.String):com.samsung.android.knox.net.firewall.FirewallRule");
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0079  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0099  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.samsung.android.knox.net.firewall.FirewallRule translateDenyRule(java.lang.String r11) {
        /*
            r0 = 0
            if (r11 != 0) goto L4
            return r0
        L4:
            java.lang.String r1 = ";"
            java.lang.String[] r11 = r11.split(r1)
            int r1 = r11.length
            r2 = 2
            if (r1 < r2) goto Lba
            int r1 = r11.length
            r3 = 4
            if (r1 <= r3) goto L14
            goto Lba
        L14:
            com.samsung.android.knox.net.firewall.Firewall$PortLocation r1 = com.samsung.android.knox.net.firewall.Firewall.PortLocation.REMOTE
            com.samsung.android.knox.net.firewall.Firewall$NetworkInterface r1 = com.samsung.android.knox.net.firewall.Firewall.NetworkInterface.ALL_NETWORKS
            r3 = 0
            r4 = r11[r3]
            java.lang.String r5 = ":"
            int r4 = r4.lastIndexOf(r5)
            r5 = -1
            if (r4 != r5) goto L25
            return r0
        L25:
            r5 = r11[r3]
            java.lang.String r5 = r5.substring(r3, r4)
            r6 = r11[r3]
            r7 = 1
            int r4 = r4 + r7
            java.lang.String r4 = r6.substring(r4)
            java.lang.String[] r6 = com.samsung.android.knox.net.firewall.FirewallRuleTranslator.portLocationOptions
            r6 = r6[r3]
            r8 = r11[r7]
            boolean r6 = r6.equals(r8)
            if (r6 == 0) goto L42
            com.samsung.android.knox.net.firewall.Firewall$PortLocation r6 = com.samsung.android.knox.net.firewall.Firewall.PortLocation.REMOTE
            goto L53
        L42:
            java.lang.String[] r6 = com.samsung.android.knox.net.firewall.FirewallRuleTranslator.portLocationOptions
            r6 = r6[r7]
            r8 = r11[r7]
            boolean r6 = r6.equals(r8)
            if (r6 == 0) goto L51
            com.samsung.android.knox.net.firewall.Firewall$PortLocation r6 = com.samsung.android.knox.net.firewall.Firewall.PortLocation.LOCAL
            goto L53
        L51:
            com.samsung.android.knox.net.firewall.Firewall$PortLocation r6 = com.samsung.android.knox.net.firewall.Firewall.PortLocation.ALL
        L53:
            int r8 = r11.length
            r9 = 3
            if (r8 != r9) goto L75
            java.lang.String[] r8 = com.samsung.android.knox.net.firewall.FirewallRuleTranslator.networkInterfaceOptions
            r8 = r8[r3]
            r10 = r11[r2]
            boolean r8 = r8.equals(r10)
            if (r8 == 0) goto L66
            com.samsung.android.knox.net.firewall.Firewall$NetworkInterface r8 = com.samsung.android.knox.net.firewall.Firewall.NetworkInterface.WIFI_DATA_ONLY
            goto L76
        L66:
            java.lang.String[] r8 = com.samsung.android.knox.net.firewall.FirewallRuleTranslator.networkInterfaceOptions
            r8 = r8[r7]
            r10 = r11[r2]
            boolean r8 = r8.equals(r10)
            if (r8 == 0) goto L75
            com.samsung.android.knox.net.firewall.Firewall$NetworkInterface r8 = com.samsung.android.knox.net.firewall.Firewall.NetworkInterface.MOBILE_DATA_ONLY
            goto L76
        L75:
            r8 = r1
        L76:
            int r10 = r11.length
            if (r10 <= r9) goto L99
            r2 = r11[r2]
            java.lang.String[] r8 = com.samsung.android.knox.net.firewall.FirewallRuleTranslator.networkInterfaceOptions
            r3 = r8[r3]
            r8 = r11[r9]
            boolean r3 = r3.equals(r8)
            if (r3 == 0) goto L8a
            com.samsung.android.knox.net.firewall.Firewall$NetworkInterface r1 = com.samsung.android.knox.net.firewall.Firewall.NetworkInterface.WIFI_DATA_ONLY
            goto L9c
        L8a:
            java.lang.String[] r3 = com.samsung.android.knox.net.firewall.FirewallRuleTranslator.networkInterfaceOptions
            r3 = r3[r7]
            r11 = r11[r9]
            boolean r11 = r3.equals(r11)
            if (r11 == 0) goto L9c
            com.samsung.android.knox.net.firewall.Firewall$NetworkInterface r1 = com.samsung.android.knox.net.firewall.Firewall.NetworkInterface.MOBILE_DATA_ONLY
            goto L9c
        L99:
            java.lang.String r2 = "*"
            r1 = r8
        L9c:
            com.samsung.android.knox.net.firewall.FirewallRule r11 = new com.samsung.android.knox.net.firewall.FirewallRule
            com.samsung.android.knox.net.firewall.FirewallRule$RuleType r3 = com.samsung.android.knox.net.firewall.FirewallRule.RuleType.DENY
            com.samsung.android.knox.net.firewall.Firewall$AddressType r7 = com.samsung.android.knox.net.firewall.Firewall.AddressType.IPV4
            r11.<init>(r3, r7)
            com.samsung.android.knox.AppIdentity r3 = new com.samsung.android.knox.AppIdentity
            r3.<init>(r2, r0)
            r11.setIpAddress(r5)
            r11.setPortNumber(r4)
            r11.setPortLocation(r6)
            r11.setApplication(r3)
            r11.setNetworkInterface(r1)
            return r11
        Lba:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.knox.net.firewall.FirewallRuleTranslator.translateDenyRule(java.lang.String):com.samsung.android.knox.net.firewall.FirewallRule");
    }

    public static String translateFirewallRuleToOldFormat(FirewallRule firewallRule) {
        StringBuilder sb = new StringBuilder();
        int i = AnonymousClass1.$SwitchMap$com$samsung$android$knox$net$firewall$FirewallRule$RuleType[firewallRule.mRuleType.ordinal()];
        if (i != 1) {
            if (i != 2) {
                if (i != 3) {
                    if (i == 4) {
                        sb.append(firewallRule.mAddress);
                        sb.append(":");
                        sb.append(firewallRule.mPortNumber);
                    }
                } else {
                    sb.append(firewallRule.mAddress);
                    sb.append(":");
                    sb.append(firewallRule.mPortNumber);
                    sb.append(";");
                    sb.append(firewallRule.getTargetIpAddress());
                    sb.append(":");
                    sb.append(firewallRule.getTargetPortNumber());
                    if (!"*".equals(firewallRule.mAppIdentity.getPackageName()) || !firewallRule.mNetworkInterface.equals(Firewall.NetworkInterface.ALL_NETWORKS)) {
                        sb.append(";");
                        sb.append(firewallRule.mAppIdentity.getPackageName());
                        sb.append(";");
                        sb.append(convertNetworkInterface(firewallRule.mNetworkInterface));
                    }
                }
            } else {
                sb.append(firewallRule.mAddress);
                sb.append(":");
                sb.append(firewallRule.mPortNumber);
                sb.append(";");
                sb.append(convertPortLocation(firewallRule.getPortLocation()));
                if (!"*".equals(firewallRule.mAppIdentity.getPackageName()) || !firewallRule.mNetworkInterface.equals(Firewall.NetworkInterface.ALL_NETWORKS)) {
                    sb.append(";");
                    sb.append(firewallRule.mAppIdentity.getPackageName());
                    sb.append(";");
                    sb.append(convertNetworkInterface(firewallRule.mNetworkInterface));
                }
            }
        } else {
            sb.append(firewallRule.mAddress);
            sb.append(":");
            sb.append(firewallRule.mPortNumber);
            sb.append(";");
            sb.append(convertPortLocation(firewallRule.getPortLocation()));
            if (!firewallRule.mNetworkInterface.equals(Firewall.NetworkInterface.ALL_NETWORKS)) {
                sb.append(";");
                sb.append(convertNetworkInterface(firewallRule.mNetworkInterface));
            }
        }
        return sb.toString();
    }

    public static FirewallRule translateRedirectExceptionRule(String str) {
        int lastIndexOf;
        String str2;
        if (str == null) {
            return null;
        }
        String[] split = str.split(";");
        if (split.length < 1 || split.length > 2 || (lastIndexOf = split[0].lastIndexOf(":")) == -1) {
            return null;
        }
        String substring = split[0].substring(0, lastIndexOf);
        String substring2 = split[0].substring(lastIndexOf + 1);
        if (split.length == 2) {
            str2 = split[1];
        } else {
            str2 = "*";
        }
        FirewallRule firewallRule = new FirewallRule(FirewallRule.RuleType.REDIRECT_EXCEPTION, Firewall.AddressType.IPV4);
        AppIdentity appIdentity = new AppIdentity(str2, (String) null);
        firewallRule.setIpAddress(substring);
        firewallRule.setPortNumber(substring2);
        firewallRule.setApplication(appIdentity);
        return firewallRule;
    }

    public static FirewallRule translateRedirectRule(String str) {
        String str2;
        if (str == null) {
            return null;
        }
        String[] split = str.split(";");
        if (split.length != 2 && split.length != 4) {
            return null;
        }
        Firewall.NetworkInterface networkInterface = Firewall.NetworkInterface.ALL_NETWORKS;
        int lastIndexOf = split[0].lastIndexOf(":");
        if (lastIndexOf == -1) {
            return null;
        }
        String substring = split[0].substring(0, lastIndexOf);
        String substring2 = split[0].substring(lastIndexOf + 1);
        int lastIndexOf2 = split[1].lastIndexOf(":");
        if (lastIndexOf2 == -1) {
            return null;
        }
        String substring3 = split[1].substring(0, lastIndexOf2);
        String substring4 = split[1].substring(lastIndexOf2 + 1);
        if (split.length == 4) {
            str2 = split[2];
            if (networkInterfaceOptions[0].equals(split[3])) {
                networkInterface = Firewall.NetworkInterface.WIFI_DATA_ONLY;
            } else if (networkInterfaceOptions[1].equals(split[3])) {
                networkInterface = Firewall.NetworkInterface.MOBILE_DATA_ONLY;
            }
        } else {
            str2 = "*";
        }
        FirewallRule firewallRule = new FirewallRule(FirewallRule.RuleType.REDIRECT, Firewall.AddressType.IPV4);
        AppIdentity appIdentity = new AppIdentity(str2, (String) null);
        firewallRule.setIpAddress(substring);
        firewallRule.setPortNumber(substring2);
        firewallRule.setTargetIpAddress(substring3);
        firewallRule.setTargetPortNumber(substring4);
        firewallRule.setApplication(appIdentity);
        firewallRule.setNetworkInterface(networkInterface);
        return firewallRule;
    }

    public static FirewallRule translateRule(String str, FirewallRule.RuleType ruleType) {
        if (ruleType == FirewallRule.RuleType.ALLOW) {
            return translateAllowRule(str);
        }
        if (ruleType == FirewallRule.RuleType.DENY) {
            return translateDenyRule(str);
        }
        if (ruleType == FirewallRule.RuleType.REDIRECT) {
            return translateRedirectRule(str);
        }
        if (ruleType == FirewallRule.RuleType.REDIRECT_EXCEPTION) {
            return translateRedirectExceptionRule(str);
        }
        return null;
    }
}
